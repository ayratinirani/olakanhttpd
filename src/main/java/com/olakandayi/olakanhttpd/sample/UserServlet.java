package com.olakandayi.olakanhttpd.sample;

import com.olakandayi.olakanhttpd.HReqest;
import com.olakandayi.olakanhttpd.HttpServlet;
import com.olakandayi.olakanhttpd.Response;

public class UserServlet extends HttpServlet {
    public UserServlet(HReqest request, Response response) {
        super(request, response);
    }

    @Override
    public Response get() {
        return null;
    }

    @Override
    public Response post() {
        return null;
    }

    @Override
    public Response patch() {
        return null;
    }

    @Override
    public Response put() {
        return null;
    }

    @Override
    public Response delete() {
        return null;
    }
}
