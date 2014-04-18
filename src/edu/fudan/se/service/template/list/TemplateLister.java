package edu.fudan.se.service.template.list;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.Attributes.Name;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import edu.fudan.se.service.servlet.util.IOUtil;
import edu.fudan.se.service.servlet.util.Parameter;

public class TemplateLister {
	private static final Name TEMPLATE_DESCRIPTION = new Name(
			"Template-Description");
	private static final Name TEMPLATE_NAME = new Name("Template-Name");

	public static List<Response> listTemplates() {
		List<Response> templates = new ArrayList<Response>();
		File templateDir = new File(Parameter.TEMPLATE_DIR);
		for (File file : templateDir.listFiles(Parameter.JAR_FILTER)) {
			templates.add(getResponseByFile(file));
		}
		return templates;
	}

	private static Response getResponseByFile(File file) {
		JarFile template = null;
		Response result = null;
		try {
			template = new JarFile(file);
			Manifest manifest = template.getManifest();
			Attributes attrs = (Attributes) manifest.getMainAttributes();

			String description = attrs.getValue(TEMPLATE_DESCRIPTION);
			String templateName = attrs.getValue(TEMPLATE_NAME);

			result = new Response(file, templateName, description);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtil.close(template);
		}

		return result;
	}

}
