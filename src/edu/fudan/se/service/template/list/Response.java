package edu.fudan.se.service.template.list;

import java.io.File;

public class Response {
	private String file,template,description;
	
	public Response() {
	}

	public Response(File file, String template, String description) {
		this.file = file.getName();
		this.template = template;
		this.description = description;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
