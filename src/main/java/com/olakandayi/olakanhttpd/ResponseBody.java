package com.olakandayi.olakanhttpd;

import java.io.*;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class ResponseBody {
    boolean isText=true;
    String MimeTYpe;
    byte[]bytes=new byte[1024*64];
    public void fileToResponse(File file,OutputStream out) throws IOException {
        FileInputStream ins=new FileInputStream(file);
        int i;
        while ((i=ins.read(bytes))>-1){
            out.write(bytes);
        }
        ins.close();
        out.close();
    }
    public void textToresponse(String text,OutputStream out) throws IOException {
        out.write(text.getBytes(StandardCharsets.UTF_8));
        out.close();
    }

    public String getMimeTypes(File file) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String mimeType = fileNameMap.getContentTypeFor(file.getName());
        return mimeType;
    }
}
