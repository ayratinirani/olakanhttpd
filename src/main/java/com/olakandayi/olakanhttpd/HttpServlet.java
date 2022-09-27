package com.olakandayi.olakanhttpd;

import java.io.IOException;

public  abstract class HttpServlet
{
	public abstract void get(OlkReqest request) throws IOException;
	public abstract void post(OlkReqest request);
	public abstract void patch(OlkReqest request);
	public abstract void put(OlkReqest request);
	public abstract void delete(OlkReqest request);
	 
	 protected OlkResponse response;
	 OlkReqest request;
	public HttpServlet(OlkReqest request, OlkResponse response){
		this.response=response;
		this.request=request;
	}
}
