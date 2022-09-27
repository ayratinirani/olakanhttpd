package com.olakandayi.olakanhttpd;

public abstract class OlkMiddleWare {

    private final OlkResponse response;
    private final OlkReqest request;

    public OlkMiddleWare(OlkReqest reqest, OlkResponse response){
        this.response=response;
        this.request=reqest;
    }

    public abstract void process();

    public OlkResponse getResponse() {
        return response;
    }
}
