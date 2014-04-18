package edu.fudan.se.service.template.get;

import java.io.File;

import edu.fudan.se.service.servlet.util.IOUtil;
import edu.fudan.se.service.servlet.util.Parameter;

public class TemplateProvider {
	public static Response getTemplate(String name) {
		Response response = new Response();
		response.setFile(IOUtil.getBundleFile(Parameter.TEMPLATE_DIR
				+ File.separator + name));
		return response;
	}
}
