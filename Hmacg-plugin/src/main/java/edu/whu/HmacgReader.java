package edu.whu;

import cn.hutool.core.util.StrUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

public class HmacgReader {

    public HmacgReader() {
        metaList = new ArrayList<>();
        infoMap = new HashMap<>();
        connection =Connect();
    }


    private Connection connection;

    private List<HmacgMeta> metaList;

    private Map<String, String> infoMap;
    /**
     * 判断指定的单元格是否是合并单元格
     *
     * @param sheet
     * @param row    行下标
     * @param column 列下标
     * @return
     */
    private static boolean isMergedRegion(Sheet sheet, int row, int column) {
        //获取该sheet所有合并的单元格

        int sheetMergeCount = sheet.getNumMergedRegions();
        //循环判断 该单元格属于哪个合并单元格， 如果能找到对应的，就表示该单元格是合并单元格
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if (row >= firstRow && row <= lastRow) {
                if (column >= firstColumn && column <= lastColumn) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取合并单元格的值
     *
     * @param sheet  sheet索引 从0开始
     * @param row    行索引 从0开始
     * @param column 列索引  从0开始
     * @return
     */
    public static String getMergedRegionValue(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();
            if (row >= firstRow && row <= lastRow) {
                if (column >= firstColumn && column <= lastColumn) {
                    Row fRow = sheet.getRow(firstRow);
                    Cell fCell = fRow.getCell(firstColumn);
                    return getCellValue(fCell);
                }
            }
        }
        return null;
    }

    /**
     * 获取单元格的值  先确定单元格的类型，然后根据类型 取值
     *
     * @param cell 单元格
     * @return
     */
    public static String getCellValue(Cell cell) {
        if (cell == null) return "";
        if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
            return cell.getCellFormula();
        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            return String.valueOf(cell.getNumericCellValue());
        }
        return "";
    }

    /**
     * 判断一个对象的属性是否都为空，
     *
     * @param obj 对象
     * @return false : 至少有一个属性不为空， true: 该对象的属性全为空
     */
    public boolean allFieldIsNULL(Object obj) {
        Boolean flag = true;//都为空
        if (null == obj || "".equals(obj)) return flag;
        try {
            Field[] declaredFields = obj.getClass().getDeclaredFields();
            for (Field field : declaredFields) { // 循环该类，取出类中的每个属性
                field.setAccessible(true);// 把该类中的所有属性设置成 public
                Object object = field.get(obj); // object 是对象中的属性
                if(!(object == null)) {
                    flag = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;//false:不都为空
    }

    /**
     * 读取excel文件sheet1
     *
     * @param
     * @param sheetIndex    sheet页下标：从0开始
     * @param startReadLine 开始读取的行:从0开始
     * @param tailLine      结束行
     */
    public void readExcelSheet1(String path, int sheetIndex, int startReadLine, int tailLine) {
        Workbook wb = null;
        try {
            wb = WorkbookFactory.create(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        //读取excel表中的sheet, 参数为sheet的索引值（从0开始）
        Sheet sheet = wb.getSheetAt(sheetIndex);
        Row row = null;
        Boolean save = false;
        //获取该sheet的最后一行数据的索引
        int lastRowNum = sheet.getLastRowNum();
        //外循环是循环行，内循环是循环每行的单元格
        for (int i = startReadLine; i <= lastRowNum; i++) {
            HmacgMeta meta = new HmacgMeta();
            //根据行索引获取行对象（单元格集合）
            row = sheet.getRow(i);
            if(row == null) continue;

            try {
                //遍历行的单元格，并解析
                for (Cell c : row) {
                    String returnStr = "";
                    String trim = "";
                    //设置该单元格的数据的类型为String
                    c.setCellType(Cell.CELL_TYPE_STRING);
                    boolean isMerge = isMergedRegion(sheet, i, c.getColumnIndex());
                    // 判断是否具有合并单元格

                    if (isMerge) {
                        break;
                        //如果是合并单元格，就获取合并单元格的值
                        // returnStr = getMergedRegionValue(sheet, row.getRowNum(), c.getColumnIndex()).toString();
                    } else {
                        //不是合并单元格直接获取单元格的值
                        returnStr = getCellValue(c).toString();
                    }
                    if (Objects.nonNull(returnStr) && StringUtils.isNotEmpty(returnStr)) {
                        trim = returnStr.trim();
                        //封装结果集，一行数据封装为一个对象
                        if (c.getColumnIndex() == 2) {
                            meta.setName(trim);
                        } else if (c.getColumnIndex() == 3) {
                            meta.setUpdateTime(trim);
                        } else if (c.getColumnIndex() == 4) {
                            meta.setUpdateTime(meta.getUpdateTime() + trim);
                        } else if (c.getColumnIndex() == 5) {
                            meta.setPlatform(trim);
                        } else if (c.getColumnIndex() == 9) {
                            meta.setUniquePlatform(trim);
                        } else if (c.getColumnIndex() == 10) {
                            meta.setInitTime(trim);
                        } else if (c.getColumnIndex() == 11) {
                            meta.setTotalEpisodes(trim);
                        } else if (c.getColumnIndex() == 12) {
                            meta.setTips(trim);
                        } else if (c.getColumnIndex() == 13) {
                            meta.setTags(trim);
                        }
                    }
                }
                //判断一个对象的属性是否都为空， true:都为空  ， false: 不都为空
                if (!allFieldIsNULL(meta) && meta.getName() != null) {
                    //该对象不都为空的情况下，添加到集合中
                    metaList.add(meta);
                    System.out.println(meta);
                }
            }
            catch (NullPointerException e) {

            }


            //一次保存25条数据，最后一次数据不够25条也进行保存
//            if (list.size() == 25 || i == lastRowNum) {
//                //save = this.iCmsIndexCategoryService.saveBatch(list);
//                System.out.println("==================================================第------" + (i + 1) + "---------行保存结果为======================================== " + save);
//                list.clear();
//            }
        }
    }


    /**
     * 读取excel文件sheet1
     *
     * @param
     * @param sheetIndex    sheet页下标：从0开始
     * @param startReadLine 开始读取的行:从0开始
     * @param tailLine      结束行
     */
    public void readExcelSheet2(String path, int sheetIndex, int startReadLine, int tailLine) {
        Workbook wb = null;
        try {
            wb = WorkbookFactory.create(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        //读取excel表中的sheet, 参数为sheet的索引值（从0开始）
        Sheet sheet = wb.getSheetAt(sheetIndex);
        Row row = null;
        Boolean save = false;
        //获取该sheet的最后一行数据的索引
        int lastRowNum = sheet.getLastRowNum();
        //外循环是循环行，内循环是循环每行的单元格
        for (int i = startReadLine; i < tailLine; i+=6) {
            //根据行索引获取行对象（单元格集合）
            System.out.println(i);
            row = sheet.getRow(i);
            if(row == null) continue;
            String name = null;
            String description = null;
            try {
                //遍历行的单元格，并解析
                for (Cell c : row) {
                    String returnStr = "";
                    String trim = "";
                    //设置该单元格的数据的类型为String
                    c.setCellType(Cell.CELL_TYPE_STRING);
                    boolean isMerge = isMergedRegion(sheet, i, c.getColumnIndex());
                    // 判断是否具有合并单元格

                    if (isMerge) {
                        returnStr = getMergedRegionValue(sheet, row.getRowNum(), c.getColumnIndex()).toString();
                    } else {
                        //不是合并单元格直接获取单元格的值
                        returnStr = getCellValue(c).toString();
                    }
                    if (Objects.nonNull(returnStr) && StringUtils.isNotEmpty(returnStr)) {
                        trim = returnStr.trim();
                        //封装结果集，一行数据封装为一个对象
                        if (c.getColumnIndex() == 3) {
                            name = trim;
                        } else if (c.getColumnIndex() == 4) {
                            description = trim;
                        }
                    }
                }
                //判断一个对象的属性是否都为空， true:都为空  ， false: 不都为空
                if (name != null && description != null) {
                    infoMap.put(name, description);
                    System.out.println(name+":"+description);
                }
            }
            catch (NullPointerException e) {

            }




            //一次保存25条数据，最后一次数据不够25条也进行保存
//            if (list.size() == 25 || i == lastRowNum) {
//                //save = this.iCmsIndexCategoryService.saveBatch(list);
//                System.out.println("==================================================第------" + (i + 1) + "---------行保存结果为======================================== " + save);
//                list.clear();
//            }
        }


    }

    /**
     * Map集合模糊匹配
     * @param map  map集合
     * @param keyLike  模糊key
     * @return
     */
    private List<String> getLikeByMap(Map<String, String> map, String keyLike){
        List<String> list=new ArrayList<>();
        for (Map.Entry<String, String> entity : map.entrySet()) {
            if(entity.getKey().indexOf(keyLike)>-1){
                list.add(entity.getValue());
            }

        }

        return list;
    }
    private Connection Connect(){
        Connection c = null;
        try {

            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://lzynb.com.cn:15432/yhy-yyds",
                            "course-design", "course-design2023");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
        return c; //记得返回一下这个对象，后面一直在用
    }

    private void insert() {
        String animeTemplate = "INSERT INTO anime(name, description, ani_initial_date, ani_total_episodes, ani_season, " +
                "download_info_id, filter_id) VALUES ('{}', '{}', '{}', {}, {}, {}, {});";
        String categoryTemplate = "INSERT INTO map VALUES ({}, {});";
        Long AnimeId = null;
        for (HmacgMeta meta: metaList) {
            Integer episode = null, season = 1;
            try {
                episode = Integer.parseInt(meta.getTotalEpisodes());
            }
            catch (Exception e) {
                episode = null;
            }
            try{
                List<String> res = getLikeByMap(infoMap, meta.getName());
                String desc = res.size()>0? res.get(0): "暂无";
                meta.setName(StrUtil.replace(meta.getName(), "'", "’"));
                desc = StrUtil.replace(desc, "'", "‘");
                String sql = StrUtil.format(animeTemplate,meta.getName(),
                        desc, meta.getInitTime(), (episode != null?episode.toString(): "null"),
                        season.toString(), "null", "null" );
                connection.setAutoCommit(true);
                Statement stmt = connection.createStatement();
                int count = stmt.executeUpdate(sql);
                String template1 = "SELECT id FROM anime WHERE name = '" + meta.getName() + "'";
                Statement stmt1 = connection.createStatement();
                ResultSet rs = stmt.executeQuery(template1);
                if(rs.next()) {
                    AnimeId = rs.getLong("id");
                }
                List<String> cateList = StrUtil.split(meta.getTags(), '/');
                for(String cate:cateList) {
                    Long cateId = selectCategory(cate);
                    Statement stmt2 = connection.createStatement();
                    String sql2 = StrUtil.format(categoryTemplate, AnimeId, cateId);
                    int count2 = stmt.executeUpdate(sql2);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private Long selectCategory(String cateName) {
        String template1 = "SELECT id FROM category WHERE name = '" +cateName + "'";
        System.out.println(template1);
        String template2 = "INSERT INTO category(name, description) VALUES ('{}', '{}')";
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(template1);
            if(rs.next()) {
                return rs.getLong("id");
            }
            else {
                connection.setAutoCommit(true);
                String sql = StrUtil.format(template2, cateName, cateName);
                //4、执行添加操作
                int count = stmt.executeUpdate(sql);
                return selectCategory(cateName);
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        if(args.length < 1) {
            System.out.println("please input file path");
            return;
        }
        HmacgReader reader = new HmacgReader();
        reader.readExcelSheet1(args[0], 0, 4, 0);
        reader.readExcelSheet2(args[0], 1, 4, 4 + 6*reader.metaList.size());
        reader.insert();
    }


}

