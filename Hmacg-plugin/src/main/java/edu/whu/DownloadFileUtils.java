package edu.whu;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadFileUtils {

    /**
     * 从网络Url中下载文件
     * @param urlStr url的路径
     * @throws IOException
     */
    public static void  downLoadByUrl(String urlStr,String savePath) {

        try {
            String fileName = getFileName(urlStr);
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            //设置超时间为5秒
            conn.setConnectTimeout(5*1000);
            //防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            //得到输入流
            InputStream inputStream = conn.getInputStream();
            //获取自己数组
            byte[] getData = readInputStream(inputStream);
            //文件保存位置
            File saveDir = new File(savePath);
            if(!saveDir.exists()){ // 没有就创建该文件
                saveDir.mkdir();
            }
            File file = new File(saveDir+File.separator+fileName);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(getData);

            fos.close();
            inputStream.close();
            System.out.println("the file: "+url+" download success");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 从输入流中获取字节数组
     * @param inputStream
     * @return
     * @throws IOException
     */
    private static  byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[4*1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    public static void main(String[] args) {
        try{
            String filePath = "https://xf.hmacg.cn/xfb-dl.php?dl=202310&type=all&ext=xlsx";
            downLoadByUrl(filePath,"F:/temp/download");

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从src文件路径获取文件名
     * @param srcRealPath src文件路径
     * @return 文件名
     */
    private static String getFileName(String srcRealPath){
        return StringUtils.substringAfterLast(srcRealPath,"/");
    }
}
