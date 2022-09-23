package com.olakandayi.olakanhttpd;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;public class Router  
{
	private HReqest request;
	private Response response;
	private String path;
	
	HashMap <String,String>schemas=new HashMap();
	public Router(HReqest request,Response response){
		this.request=request;
		this.response=response;
		this.path=request.getPath();
	}
	
	
	public void route(){
		for (Map.Entry<String,String> mapElement : schemas.entrySet()) {
            String key = mapElement.getKey();

            String classname = (mapElement.getValue());

            if(path.matches(key)){
				try {
					Class<?> clss=Class.forName(classname);
					Constructor<?>consta=clss.getConstructors()[0];
					Object[]args=new Object[]{request,response};

					Object a=consta.newInstance(args);

				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
						 InvocationTargetException e) {}
			}

        }
		
	}
	
	private void parseSchema(){
		
	}
	
}
