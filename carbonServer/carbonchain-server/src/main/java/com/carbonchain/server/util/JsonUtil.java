package com.carbonchain.server.util;

import java.lang.reflect.Type;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;

public class JsonUtil {
	
	private static Gson gson = null;
	static{
		GsonBuilder gb = new GsonBuilder();
		gb.setLongSerializationPolicy(LongSerializationPolicy.STRING);	
		gson = gb.create();
	}
	private JsonUtil() {
	}
	
	public static String obj2Json(Object o) {
		if(o == null){
			return "";
		}
		return gson.toJson(o);
	}
	
	public static <T> T json2Obj(String json, Class<T> clazz) {
		if(json == null){
			return null;
		}
		return gson.fromJson(json, clazz);
	}
	
	public static <T> List<T> fromJsonArray(String json,Type type){
		return gson.fromJson(json, type);
	}
}