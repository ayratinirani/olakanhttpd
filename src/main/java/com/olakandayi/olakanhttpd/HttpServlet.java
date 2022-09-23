package com.olakandayi.olakanhttpd;
public  abstract class HttpServlet
{
	public abstract Response get();
	public abstract Response post();
	public abstract Response patch();
	public abstract Response put();
	public abstract Response delete();
	 
	private Response response;
	private HReqest request;
	public HttpServlet(HReqest request,Response response){
		this.response=response;
		this.request=request;
	}
}
