package com.olakandayi.olakanhttpd.sample;

import com.olakandayi.olakanhttpd.OlkMiddleWare;
import com.olakandayi.olakanhttpd.OlkReqest;
import com.olakandayi.olakanhttpd.OlkResponse;

public class AuthenticateMiddleWare extends OlkMiddleWare {
    public AuthenticateMiddleWare(OlkReqest reqest, OlkResponse response) {
        super(reqest, response);

    }

    @Override
    public void process() {

    }

}
