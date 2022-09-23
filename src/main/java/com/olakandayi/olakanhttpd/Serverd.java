package com.olakandayi.olakanhttpd;
import javax.swing.plaf.IconUIResource;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class Serverd {
    int numberr=0;
    ServerSocket sv=null;
	boolean stopped=false;
	int port;
	public Serverd(int port) {
		this.port=port;

		System.out.println(numberr);
		System.out.print("server starts");
		start();
	}
	public void start(){


		try {

		this.sv = new ServerSocket(port);
		System.out.print("on"+sv.getInetAddress()+":"+port+"\n");
		}
		catch (IOException e) {

		System.out.println("from class "+this.getClass().getName()+e.getMessage());
		}
		
		while(!stopped){
			new Thread(
					new Runnable() {
						@Override
						public void run() {
							try{
								Socket s=sv.accept();


								InputStream i=s.getInputStream();
								String headers="";
								int input;
								String firstline="";
								char chare;
								int counter;
								while ((input=i.read())>-1) {
									chare = (char) input;
									firstline += chare;

									if (chare == '\n') {
										break;
									}
								}
								firstline=firstline.trim();
								while ((input=i.read())>-1) {
									chare = (char) input;
									headers += chare;
									if((headers.length()>6&&headers.charAt(headers.length()-2)=='\n'&&chare=='\r')){
										break;
									}
								}
								headers=headers.substring(0,headers.length()-1);
								i.read();
								//making request objest
								HReqest reqest=new HReqest(firstline,headers,i);


								//logging request
								System.out.println(new Date().toString()+"   "+reqest.Method+" "+reqest.Path);
  								// making response
								ResponseBody body=new ResponseBody();

								byte[]bbody=(reqest.Method+reqest.Path+(System.currentTimeMillis()-1663866900000L)).getBytes("UTF8");
								OutputStream io=s.getOutputStream();
								Response response=new Response(io);

								////sendresponse
								io.write("HTTP/1.1 200 OK\r\n".getBytes("ASCII"));

								if(reqest.Path.equals("/ax")){
									io.write("Contet-Type:image/jpeg".getBytes("ASCII"));
									io.write("\r\n".getBytes("ASCII"));
									io.write("\r\n".getBytes("ASCII"));
									body.fileToResponse(new File("C:\\Users\\haji\\Desktop\\olakanhttpd\\src\\main\\resources\\ax.jpg"),io);
								}else {
									io.write("Contet-Type:text/plain;charset=UTF8".getBytes("ASCII"));
									io.write("\r\n".getBytes("ASCII"));
									io.write("\r\n".getBytes("ASCII"));
									body.textToresponse(reqest.Method + reqest.Path + (System.currentTimeMillis() - 1663866900000L), io);
								}
								io.flush();
								io.close();
								s.close();


							}catch(Exception e){
								System.out.println(" fromclass "+this.getClass().getName()+e.getMessage());
							}
						}
					}

			).start();


		}

	}
	//
	/*
	private static SSLContext getSslContext(Path keyStorePath, char[] keyStorePassword)
			throws Exception {

		KeyStore keyStore = KeyStore.getInstance("JKS");
		keyStore.load(new FileInputStream(keyStorePath.toFile()), keyStorePassword);

		KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
		keyManagerFactory.init(keyStore, keyStorePassword);

		SSLContext sslContext = SSLContext.getInstance("TLS");
		// Null means using default implementations for TrustManager and SecureRandom
		sslContext.init(keyManagerFactory.getKeyManagers(), null, null);
		return sslContext;
	}

	private static ServerSocket getSslSocket(InetSocketAddress address)
			throws Exception {

		// Backlog is the maximum number of pending connections on the socket, 0 means an
		// implementation-specific default is used
		int backlog = 0;

		Path keyStorePath = Path.of("./tls/keystore.jks");
		char[] keyStorePassword = "pass_for_self_signed_cert".toCharArray();

		// Bind the socket to the given port and address
		ServerSocket serverSocket = getSslContext(keyStorePath, keyStorePassword)
				.getServerSocketFactory()
				.createServerSocket(address.getPort(), backlog, address.getAddress());

		// We don't need the password anymore → Overwrite it
		Arrays.fill(keyStorePassword, '0');

		return serverSocket;
	}
   */
}
