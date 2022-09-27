package com.olakandayi.olakanhttpd;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class OlkReqest {
    public HashMap<String,String>cookies=new HashMap<>();
    String[] pathaparams;
    InputStream stream=null;
	String headLine;
    ArrayList<String>Lines=new ArrayList();
	HashMap<String,String>queryparams=new HashMap<>();
	String Method;
	String Path;
	String firstLine;
    HashMap<String,String>Headers=new HashMap<String,String>();
	private String Version;
	HashMap<String,String>bodyParams=new HashMap<String,String>();
	InputStream inputStream;
	private String hashpart;

	public OlkReqest(String firstline, String headerline, InputStream inputStream){
		this.stream=inputStream;
		this.firstLine=firstline;
		this.headLine=headerline;
		parsReqline();
		parsHeaders();
		//parseBody();
	}
	public String fisrlinel(){
		return fisrlinel();
	}


	private void parseBodyText() {

		if(Method.equals("GET")){
			bodyParams=null;
			return;
		}
		else{
			String bodyall="";
			int i;

				try {
					while (!((i=inputStream.read())>-1)){

						bodyall+=(char)i;
					}
				} catch (IOException e) {
					throw new RuntimeException(e);
				}


			parsParams(bodyall);
		}

	}

	private void parsHeaders() {
		String line="";
	    String[]headerlinesarray=headLine.split("\n");
		
		for(int i=1;i<headerlinesarray.length;i++){
			if(headerlinesarray[i].equals("")){
				break;
			}
			line=headerlinesarray[i].trim();
			//System.out.println(line);
			String key=line.split(":")[0].trim();
			String val=line.split(":")[1].trim();
			Headers.put(key,val);
		}
	}
	private void parsParams(String paramsAll) {
		String line="";
		String[]paramsArray=paramsAll.split("&");

		for(int i=1;i<paramsArray.length;i++){
			if(paramsArray[i].equals("")){
				break;
			}
			line=paramsArray[i].trim();
			//System.out.println(line);
			String key=line.split("=")[0].trim();
			String val=line.split("=")[1].trim();
			bodyParams.put(key,val);
		}
	}

	private void parsReqline() {
		String []parts=firstLine.split(" ");
		this.Method=parts[0];
		this.Path=parts[1];
		this.Version=parts[2];
	}
    
	public HashMap getHeasers(){
		
		return this.Headers;
	}
	public String getVersion(){
		return this.Version;
	}
	
	public Map<String, String> getBodyParams()
	{
		return this.bodyParams;
	}

	
	public String getPath(){
		
		return this.Path;
	}
	private void parseQueryParams(){
		String[]parts;
		String queris="";
		if(Path.contains("?")){
			parts=Path.split("/?");
			if(parts[1].contains("#")){
				queris=parts[1].split("#")[0];
				this.hashpart=Path.split("#")[1];
			};
			String line="";
			String[]paramsArray=queris.split("&");

			for(int i=1;i<paramsArray.length;i++){
				if(paramsArray[i].equals("")){
					break;
				}
				line=paramsArray[i].trim();
				//System.out.println(line);
				String key=line.split("=")[0].trim();
				String val=line.split("=")[1].trim();
				queryparams.put(key,val);
			}
		}
	}
	
}
