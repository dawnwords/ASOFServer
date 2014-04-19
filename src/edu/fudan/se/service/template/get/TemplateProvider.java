package edu.fudan.se.service.template.get;

import java.io.File;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.jar.Attributes.Name;

import edu.fudan.se.service.servlet.util.IOUtil;
import edu.fudan.se.service.servlet.util.Parameter;

public class TemplateProvider {
	private static final Name TEMPLATE_CLASS = new Name("Template-Class");

	public static Response getTemplate(String name) {
		Response response = new Response();
		String path = Parameter.TEMPLATE_DIR + File.separator + name;
		response.setFile(IOUtil.getBundleFile(path));
		response.setClazz(getClassFromManifest(path));
		return response;
	}

	private static String getClassFromManifest(String path) {
		JarFile template = null;
		try {
			template = new JarFile(path);
			Manifest manifest = template.getManifest();
			Attributes attrs = (Attributes) manifest.getMainAttributes();
			return attrs.getValue(TEMPLATE_CLASS);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtil.close(template);
		}
		return null;
	}
}
