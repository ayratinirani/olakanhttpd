package com.olakandayi.olakanhttpd;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.security.KeyStore;
import java.util.Arrays;
import java.util.Date;

public class ServerD {
    int number =0;
    ServerSocket sv=null;
	boolean stopped=false;
	int port;
	public static final String BASE_DIR="resources";
	public ServerD(int port) {
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
		System.err.println("on  http://localhost:"+port+"  "+new Date()+"  requests are: \n");
		}
		catch (IOException e) {

		System.out.println("from class "+this.getClass().getName()+e.getMessage());
		}
		
		while(!stopped){
			new Thread(
					() -> {
						try{
							Socket s=sv.accept();
							number++;
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
							HReqest request=new HReqest(firstLine.toString(), headers.toString(),i);


							//logging request
							System.out.println(number +" "+new Date()+"   "+"  "+s.getRemoteSocketAddress()+"  "+request.Method+" "+request.Path);
							  // making response
							ResponseBody body=new ResponseBody();

							OutputStream io=s.getOutputStream();
							Response response=new Response(io);
							Router router=new Router(request,response);
							////sendResponse


							if(request.Path.equals("/ax")){
								io.write("HTTP/1.1 200 OK\r\n".getBytes(StandardCharsets.US_ASCII));
								io.write("Content-Type:image/jpeg".getBytes(StandardCharsets.US_ASCII));
								io.write("\r\n".getBytes(StandardCharsets.US_ASCII));
								io.write("\r\n".getBytes(StandardCharsets.US_ASCII));

								body.fileToResponse(new File(BASE_DIR+"/ax.jpg"),io);
								//System.out.println(new File(BASE_DIR+"/ax.jpg").getAbsolutePath());
							}else if(request.Path.equals("/favicon.ico")){
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




}
