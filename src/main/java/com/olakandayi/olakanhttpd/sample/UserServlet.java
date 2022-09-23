package com.olakandayi.olakanhttpd.sample;

import com.olakandayi.olakanhttpd.HReqest;
import com.olakandayi.olakanhttpd.HttpServlet;
import com.olakandayi.olakanhttpd.Response;
import com.olakandayi.olakanhttpd.ServerD;

import java.io.File;
import java.io.IOException;

public class UserServlet extends HttpServlet {
    public UserServlet(HReqest request, Response response) {
        super(request, response);

    }

    @Override
    public void get(HReqest request) throws IOException {
//
        response.bodyFileText=true;
        System.out.println("you are in getusers");
            this.response.textBody="<!DOCTYPE html> <html>" +
                        "<head>" +
                        "<title>users</title>" +
                        "</head>" +
                        "<body>"+
                        "<h1>api</>" +
                    "<h1>users</>" +
                    "<h1>users</>" +
                        "</body>" +
                        "</html>";
       // response.textBody="vuvbibvigbuiuiuihbuiujbj";
        response.setTextBody();
        response.send();
    }

    @Override
    public void post(HReqest request) {
        response.bodyFileText=false;
      File myFile=new File(ServerD.BASE_DIR+"/ax.jpg");
       response.setBodyFileBinary(myFile);

      response.send();
    }

    @Override
    public void patch(HReqest request) {
    }

    @Override
    public void put(HReqest request) {

    }

    @Override
    public void delete(HReqest request) {

    }
}
