package edu.fudan.se.service.servlet.util;

import com.google.gson.Gson;

public class Util {
	private static Gson gson = new Gson();
	
	public static Gson getGson(){
		return gson;
	}
}
