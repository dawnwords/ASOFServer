package edu.fudan.se.service.servlet.util;

import java.io.File;
import java.io.FilenameFilter;

import com.google.gson.Gson;

public class Parameter {
	private static final String ASOF_BASE_DIR = "D:" + File.separator
			+ "Development" + File.separator + "Workspace" + File.separator
			+ "ASOF" + File.separator;

	public static final String BUNDLE_DIR = ASOF_BASE_DIR + "bundleDir";
	public static final String TEMPLATE_DIR = ASOF_BASE_DIR + "templateDir";

	private static Gson gson = new Gson();

	public static Gson getGson() {
		return gson;
	}

	public static final FilenameFilter JAR_FILTER = new FilenameFilter() {
		@Override
		public boolean accept(File dir, String name) {
			return name.toLowerCase().endsWith(JAR_EXT);
		}
	};

	public static final String JAR_EXT = ".jar";

	public static final int BUFFER_SIZE = 4096;

}
