package com.olakandayi.olakanhttpd;

import java.io.*;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class ResponseBody {
    boolean isText=true;
    String MimeTYpe;
    byte[]bytes=new byte[1024*64*4];
    public void fileToResponse(File file, OutputStream out) throws IOException {
        FileInputStream s=new FileInputStream(file);
        if(file.getName().endsWith(".txt")){
            FileReader reader=new FileReader(file);
            String g="";
            int i=0;
            char charx;
            while ((i=reader.read())>-1){
                g+=(char)i;
            }
            out.write(g.getBytes(StandardCharsets.UTF_8));

            s.close();
            out.close();
            return;
        }
        int i;
        while ((i=s.read(bytes))>0){
            out.write(bytes);
            bytes = new byte[1024*64*4];
        }
        s.close();
        out.close();
    }
    public void textToresponse(String text,OutputStream out) throws IOException {
        out.write(text.getBytes(StandardCharsets.UTF_8));
        out.close();
    }

    public String getMimeType(File file) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String mimeType = fileNameMap.getContentTypeFor(file.getName());
        //System.out.println("mime is"+mimeType);
        return mimeType;
    }
}
