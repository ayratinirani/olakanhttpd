package com.olakandayi.olakanhttpd;

import java.io.*;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class OlkServerD {
	private  String session="";
    int number =0;
    ServerSocket sv=null;
	boolean stopped=false;
	int port;
	public static final String BASE_DIR="resources";
	private ResponseBody body;
	private OlkResponse response;
	private Router router;
	private OlkReqest request;
	private Socket s;

	public OlkServerD(int port) {
		this.port=port;
		System.err.print("server starts ");
		start();
	}

	@Override
	public String toString() {
		return this.sv.getInetAddress().getCanonicalHostName();
	}

	public void start(){


		try {

		this.sv = new ServerSocket(port);
			DatagramSocket uds=new DatagramSocket();
			uds.connect(InetAddress.getByName("8.8.8.8"),80);
			String host=uds.getLocalAddress().toString();
		System.err.println("on  http:/"+host+":"+port+"  "+new Date()+"  requests are: \n");
		}
		catch (Exception e) {

		System.out.println("from class "+this.getClass().getName()+e.getMessage());
		}
		
		while(!stopped){
			new Thread(
					() -> {
						try{
							 s=sv.accept();
							number++;
							//setSession();
							//System.out.println(number);
							InputStream i=s.getInputStream();
							StringBuilder headers= new StringBuilder();
							int input;
							StringBuilder firstLine= new StringBuilder();
							char charx;
							while ((input=i.read())>-1) {
								charx = (char) input;
								firstLine.append(charx);

								if (charx == '\n') {
									break;
								}
							}
							firstLine = new StringBuilder(firstLine.toString().trim());
							while ((input=i.read())>-1) {
								charx = (char) input;
								headers.append(charx);
								if((headers.length()>6&&headers.charAt(headers.length()-2)=='\n'&&charx=='\r')){
									break;
								}
							}


							headers = new StringBuilder(headers.substring(0, headers.length() - 2));
							//noinspection ResultOfMethodCallIgnored
							i.read();
							//making request object
							 request=new OlkReqest(firstLine.toString(), headers.toString(),i);
							setSession();

							//logging request
							System.out.println(number +" "+new Date()+"   "+"  "+s.getRemoteSocketAddress()+"  "+request.Method+" "+request.Path);
							  // making response
							 body=new ResponseBody();

							OutputStream io=s.getOutputStream();
							 response=new OlkResponse(io);
							 router=new Router(request,response);
							////sendResponse
								response.setCookie("olk-session",session,"","");

							 if(request.Path.equals("/favicon.ico")){
								io.write("HTTP/1.1 200 OK\r\n".getBytes(StandardCharsets.US_ASCII));
								io.write("Content-Type:image/ico".getBytes(StandardCharsets.US_ASCII));
								io.write("\r\n".getBytes(StandardCharsets.US_ASCII));
								io.write("\r\n".getBytes(StandardCharsets.US_ASCII));

								body.fileToResponse(new File(BASE_DIR+"/favicon.ico"),io);
							}
							else {

								router.route();
									}
							io.flush();
							io.close();
							s.close();


						}catch(Exception e){
						//	System.out.println(" fromClass "+this.getClass().getName()+e.getLocalizedMessage());

						}
					}

			).start();


		}

	}

	private void setSession(){
		if (request.cookies.isEmpty()){
			System.out.println("nosession");
			session=  String.valueOf(System.currentTimeMillis());
			response.setCookie("olk-session",session,"","");
			return;
		}
		for (Map.Entry<String,String> mapElement : request.cookies.entrySet()) {

			String key = mapElement.getKey();


			String value = (mapElement.getValue());

			if(key.equals("olk-session")){
				session= request.cookies.get(key);
				System.out.println("session:  "+session);
				return;
			}

		}
		//session=  UUID.randomUUID().toString();
	}


}
