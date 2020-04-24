//package com.tancheng.p2p;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileReader;
//import java.io.InputStreamReader;
//import java.io.Reader;
//import java.lang.reflect.Type;
//import java.util.List;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.google.gson.LongSerializationPolicy;
//
//public class JsonUtil {
//	private static Gson gson = null;
//	static{
//		GsonBuilder gb = new GsonBuilder();
//		gb.setLongSerializationPolicy(LongSerializationPolicy.STRING);
//		gson = gb.create();
//	}
//	private JsonUtil() {
//	}
//
//	public static String obj2Json(Object o) {
//		if(o == null){
//			return "";
//		}
//		return gson.toJson(o);
//	}
//
//	public static <T> T json2Obj(String json, Class<T> clazz) {
//		if(json == null){
//			return null;
//		}
//		return gson.fromJson(json, clazz);
//	}
//
//	public static <T> List<T> fromJsonArray(String json,Type type){
//		return gson.fromJson(json, type);
//	}
//
//	public static String readJsonFile(File jsonFile) {
//        String jsonStr = "";
//        try {
//            FileReader fileReader = new FileReader(jsonFile);
//            Reader reader = new InputStreamReader(new FileInputStream(jsonFile), "utf-8");
//            int ch = 0;
//            StringBuffer sb = new StringBuffer();
//            while ((ch = reader.read()) != -1) {
//                sb.append((char) ch);
//            }
//            fileReader.close();
//            reader.close();
//            jsonStr = sb.toString();
//            return jsonStr;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//}
