package whu.edu.aria2rpc;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import whu.edu.aria2rpc.entity.Aria2Enum;
import whu.edu.aria2rpc.entity.DownloadInfo;
import whu.edu.aria2rpc.entity.ReqRes;
import whu.edu.aria2rpc.service.IAria2Service;
import whu.edu.aria2rpc.service.IDownInfoService;

@SpringBootTest(classes = Aria2RpcApplication.class)
class Aria2RpcApplicationTests {

    @Autowired
    private IAria2Service IAria2Service;

    @Autowired
    private IDownInfoService IDownInfoService;

    @Test
    void testRequestDownload() {
        ReqRes reqRes = IAria2Service.requestDownload("https://www.voidtools.com/Everything-1.4.1.1024.x64.zip","EveryThing",".\\res");
        Assertions.assertNotNull(reqRes.getGID());
    }

    @Test
    void testRequestDownload_NoSuchFile() {
        ReqRes reqRes = IAria2Service.requestDownload("https://www.voidtools.com/Everything-1.4.1.1024.x641.zip","EveryThing",".\\res");
        Assertions.assertNotNull(reqRes.getGID());
    }

    @Test
    void testRequestDownload_DefaultNameAndDir() {
        ReqRes reqRes = IAria2Service.requestDownload("https://www.voidtools.com/Everything-1.4.1.1024.x641.zip",null,null);
        Assertions.assertNotNull(reqRes.getGID());
        DownloadInfo downloadInfo = IDownInfoService.queryDetail(reqRes.getGID());
        Assertions.assertEquals(downloadInfo.getTitle(),"Untitled");
        Assertions.assertEquals(downloadInfo.getDir(),"./res");
    }

    @Test
    void testRequestCompleteStatus() {
        Aria2Enum status = IDownInfoService.requestStatus("b4efba622d52d0e7");
        Assertions.assertEquals(status,Aria2Enum.DOWNLOAD_COMPLETE);
    }

    @Test
    void testRequestErrorStatus() {
        Aria2Enum status = IDownInfoService.requestStatus("d0b644b3649f21af");
        Assertions.assertEquals(status,Aria2Enum.DOWNLOAD_ERROR);
    }

    @Test
    void testRequestStatus_NoGID() {
        Aria2Enum status = IDownInfoService.requestStatus("d0b644b3649f21a1");
        Assertions.assertEquals(status,Aria2Enum.DECODE_ERROR);
    }

    @Test
    void testQueryPage(){
        Assertions.assertEquals(IDownInfoService.queryInfoList(2,1,3).getTotal(),3);
        Assertions.assertEquals(IDownInfoService.queryInfoList(3,1,3).getTotal(),4);
        Assertions.assertEquals(IDownInfoService.queryInfoList(4,1,3).getTotal(),7);
        Assertions.assertEquals(IDownInfoService.queryInfoList(0,1,3).getTotal(),7);
    }

    @Test
    void testQueryDetail(){
        DownloadInfo info = IDownInfoService.queryDetail("05154485ff16e7bc");
        Assertions.assertEquals(info.getGID(),"05154485ff16e7bc");
    }

    @Test
    void testQueryDetail_NoGID(){
        DownloadInfo info = IDownInfoService.queryDetail("05154485ff16e7bc1");
        Assertions.assertNull(info);
    }

}
