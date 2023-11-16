package edu.whu.Reader;

import edu.whu.HmacgReader;
import org.apache.commons.lang3.builder.ToStringExclude;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class test {

    HmacgReader reader = new HmacgReader();
    @Test
    public void connectTest() {

        Assertions.assertNotNull(reader.getConnection());
    }

    @Test
    public void insertTest() {
        Long id = reader.selectCategory("test");
        Assertions.assertNotEquals(0, id);
    }

    @Test
    public void ReadTest() {
        reader.readExcelSheet1("F:\\202310v3.21\\2023年10月新番表v3.21 byHazx.xlsx", 0, 4, 0);
        Assertions.assertEquals(89, reader.getMetaList().size());
    }

//    @Test
//    public void ReadTest2() {
//        reader.readExcelSheet1("F:\\202310v3.21\\2023年10月新番表v3.21 byHazx.xlsx", 0, 4, 0);
//        reader.readExcelSheet2("F:\\202310v3.21\\2023年10月新番表v3.21 byHazx.xlsx", 0, 4, 4 + 6*reader.getMetaList().size());
//        Assertions.assertEquals(89, reader.getInfoMap().size());
//    }
}
