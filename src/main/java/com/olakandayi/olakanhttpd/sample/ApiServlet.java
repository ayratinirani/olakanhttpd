package com.olakandayi.olakanhttpd.sample;

import com.olakandayi.olakanhttpd.OlkReqest;
import com.olakandayi.olakanhttpd.HttpServlet;
import com.olakandayi.olakanhttpd.OlkResponse;
import com.olakandayi.olakanhttpd.OlkServerD;

import java.io.File;
import java.io.IOException;

public class ApiServlet extends HttpServlet {
    public ApiServlet(OlkReqest request, OlkResponse response) {
        super(request, response);

    }

    @Override
    public void get(OlkReqest request) throws IOException {
////
//        response.bodyFileText=true;
//        System.out.println("you are in getusers");
//            this.response.textBody="<!DOCTYPE html> <html>" +
//                        "<head>" +
//                        "<title>api</title>" +
//                        "</head>" +
//                        "<body>"+
//                        "<h1>api</>" +
//                    "<h1>api</>" +
//                    "<h1>api</>" +
//                        "</body>" +
//                        "</html>";
//       // response.textBody="vuvbibvigbuiuiuihbuiujbj";
//        response.setTextBody();
//        response.send();

        response.bodyFileText=false;
        File myFile=new File(OlkServerD.BASE_DIR+"/sslcode.txt");
        response.setBodyFileBinary(myFile);

        response.send();
    }

    @Override
    public void post(OlkReqest request) {
        response.bodyFileText=false;
      File myFile=new File(OlkServerD.BASE_DIR+"/sslcode.txt");
       response.setBodyFileBinary(myFile);

      response.send();
    }

    @Override
    public void patch(OlkReqest request) {
    }

    @Override
    public void put(OlkReqest request) {

    }

    @Override
    public void delete(OlkReqest request) {

    }
}
