package whu.edu.aria2rpc.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface IAria2Service {

    String requestDownload(String downloadUrl,String title,String saveDir) throws Exception;

    String requestStatus(String GID) throws IOException;
}
