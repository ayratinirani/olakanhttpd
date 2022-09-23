package com.olakandayi.olakanhttpd;
import java.io.File;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.HashMap;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
public class Response
{
	ResponseBody responseBody;
	OutputStream os;
	HashMap<String,String> Headers=new HashMap();



	private File bodyFileBinary;
	public boolean bodyFileText=false;
	int status;
	String statustext;

	public String textBody="";
	private String contentType;
	public ResponseBody body;

	public Response(OutputStream oos){
		this.os=oos;
		
	}
	public void send(){
		try{
		printResponseLine(status,statustext);
		printHeaders();
		printBody();
		os.flush();
		os.close();
		}catch(Exception e){
			
		}
	}
	
	
	private void setCookie(String key ,String cookie ,Date expires,String Domain ,String path){
		
		String a="";
		a+=key+"="+cookie+";";
		a+="Domain="+Domain+"; ";
		a+="Path="+path+";";
		final Date currentTime = new Date(System.currentTimeMillis()+(1000*366*60*60*24L));

		final SimpleDateFormat sdf =
			new SimpleDateFormat("EEE, MMM d, yyyy hh:mm:ss a z");

// Give it to me in GMT time.
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		a+= "Expires="+sdf.format(currentTime)+"; ";
		Headers.put("Set-Cookie",a);
	}
	
	private void printBody() throws IOException {

		os.write("\r\n".getBytes(StandardCharsets.US_ASCII));
		body = new ResponseBody();
		if(bodyFileText){
			os.write(textBody.getBytes(StandardCharsets.UTF_8));
		}else{
			body.fileToResponse(bodyFileBinary,os);
		}
	}
	
	private void printResponseLine(int status , String stattxt ){
		try {
			os.write(("HTTP/1.1 " + status + " " + stattxt + " \r\n").getBytes("ASCII"));
		} catch (IOException e) {

		}

	}
	
	
	public void printHeaders()
	{
		String a="";
		for (Map.Entry<String,String> mapElement : Headers.entrySet()) {

            String key = mapElement.getKey();


            String value = (mapElement.getValue());


            a+=key + ": " + value+"\r\n";

        }
	
		try {
			//System.out.println("headers: "+a);
			os.write(a.getBytes("US-ASCII"));
		} catch (IOException e) {
			printResponseLine(500,"INTERNAL ERROR");
		}
	}
	
	public String getHeader(String key){
		
		return Headers.get(key);
	}
	
	public void addHeader(String key, String value){
		Headers.put(key,value);
	}
	
	public void setTextBody() throws IOException {
		bodyFileText=true;

		if(textBody.startsWith("<!DOCTYPE html>")){
			this.contentType="text/html";
		} else if (textBody.startsWith("{")||textBody.startsWith("{")) {
			this.contentType="application/json";
		}else {
			this.contentType="application/json";
		}
		addHeader("Content-Type",contentType);

	}

	public void setBodyFileBinary(File bodyFileBinary) {
		this.bodyFileBinary = bodyFileBinary;
		responseBody=new ResponseBody();

		this.contentType=responseBody.getMimeType(bodyFileBinary);
		addHeader("Content-Type",contentType);
	}
}
