package com.olakandayi.olakanhttpd;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Router
{
	String ServletsBasePackage;
	private  HReqest request;
	private Response response;
	private final String path;
	
	HashMap <String,String>schemas=new HashMap<>();
	public Router(HReqest request,Response response){
		this.request=request;
		this.response=response;
		this.path=request.getPath();
		parseSchema();

	}
	
	
	public void route(){

		for (Map.Entry<String,String> mapElement : schemas.entrySet()) {
            String key = mapElement.getKey();


            if(path.startsWith(key)){
				String classname = (mapElement.getValue());

				try {
					Class<?> clzz=Class.forName(classname);
					Constructor<?>constructor=clzz.getConstructors()[0];
					Object[]args=new Object[]{request,response};

					Object a=constructor.newInstance(args);
					switch (request.Method) {
						case "GET" -> {
							Method getMethod = a.getClass().getMethod("get", HReqest.class);
							 getMethod.invoke(a, request);

						}
						case "POST" -> {
							Method getMethod = a.getClass().getMethod("post", HReqest.class);
							response = (Response) getMethod.invoke(a, request);

						}
						case "DELETE" -> {
							Method getMethod = a.getClass().getMethod("delete", HReqest.class);
							response = (Response) getMethod.invoke(a, request);

						}
						case "PATCH" -> {
							Method getMethod = a.getClass().getMethod("patch", HReqest.class);
							response = (Response) getMethod.invoke(a, request);

						}
						case "PUT" -> {
							Method getMethod = a.getClass().getMethod("put", HReqest.class);
							response = (Response) getMethod.invoke(a, request);

						}

					}
					response.send();
					break;
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
						 InvocationTargetException | NoSuchMethodException e) {
					throw new RuntimeException(e);
				}

			}

        }

			response.status=404;
			response.statustext="Not Found";
			response.bodyFileText=true;
			response.textBody="not found";
			response.send();

	}
	
	private void parseSchema(){
		File routeFile= new File(ServerD.BASE_DIR+"/route.txt");
		try {
		String line;
			Scanner scanner=new Scanner(routeFile);
			line=scanner.nextLine();
			this.ServletsBasePackage= line.split(":")[1];

			while (scanner.hasNextLine()){
				line=scanner.nextLine();
				schemas.put(line.split(":")[0],this.ServletsBasePackage+"."+line.split(":")[1]);
			}


		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
}
