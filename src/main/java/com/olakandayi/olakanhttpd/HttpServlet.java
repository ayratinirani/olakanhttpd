package com.olakandayi.olakanhttpd;

import java.io.IOException;

public  abstract class HttpServlet
{
	public abstract void get(HReqest request) throws IOException;
	public abstract void post(HReqest request);
	public abstract void patch(HReqest request);
	public abstract void put(HReqest request);
	public abstract void delete(HReqest request);
	 
	 protected Response response;
	 HReqest request;
	public HttpServlet(HReqest request,Response response){
		this.response=response;
		this.request=request;
	}
}
