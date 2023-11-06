package whu.edu.aria2rpc.service;

import whu.edu.aria2rpc.entity.Aria2Enum;
import whu.edu.aria2rpc.entity.DownloadInfo;
import whu.edu.aria2rpc.entity.ReqRes;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface IAria2Service {

    ReqRes requestDownload(String downloadUrl, String title, String saveDir);



}
