package edu.fudan.se.service.bundle.message;

import java.io.File;

public class BundleDescription extends ServiceDescription {
	private String path;

	public BundleDescription() {
	}

	public BundleDescription(String path, String description, String[] input,
			String[] output) {
		super(description, input, output);
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return path.substring(path.lastIndexOf(File.separator) + 1);
	}
}
