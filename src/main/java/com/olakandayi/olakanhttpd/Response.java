package com.olakandayi.olakanhttpd;
import java.io.OutputStream;
import java.util.Map;
import java.util.HashMap;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.TimeZone;public class Response      
{
	OutputStream os;
	PrintWriter printwriter;
	HashMap<String,String> Headers=new HashMap();
	byte[]body;
	boolean bodyFileText=true;
	int status;
	String statustext;
	public Response(OutputStream oos){
		this.os=oos;
		//this.printwriter=new PrintWriter(os);
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
	
	private void printBody(){
		try {
			if(bodyFileText){

			}
			os.write(body);
		} catch (IOException e) {

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


            // Adding some bonus marks to all the students

            String value = (mapElement.getValue());


            // Printing above marks corresponding to

            // students names

            a+=key + " : " + value+"\r\n";

        }
	
		try {
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
}
