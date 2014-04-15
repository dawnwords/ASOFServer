package edu.fudan.se.service.bundle.message;

public class Response {
	private int[] inputMatch;
	private int[] outputMatch;
	private byte[] bundleFile;
	private String name;

	public Response() {
	}

	public Response(int[] inputMatch, int[] outputMatch, byte[] bundleFile,
			String name) {
		this.inputMatch = inputMatch;
		this.outputMatch = outputMatch;
		this.bundleFile = bundleFile;
		this.name = name;
	}

	public int[] getInputMatch() {
		return inputMatch;
	}

	public void setInputMatch(int[] inputMatch) {
		this.inputMatch = inputMatch;
	}

	public int[] getOutputMatch() {
		return outputMatch;
	}

	public void setOutputMatch(int[] outputMatch) {
		this.outputMatch = outputMatch;
	}

	public byte[] getBundleFile() {
		return bundleFile;
	}

	public void setBundleFile(byte[] bundleFile) {
		this.bundleFile = bundleFile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
