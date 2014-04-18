package edu.fudan.se.service.bundle.message;

import java.util.Arrays;

public class ServiceDescription {
	private String description;
	private String[] input;
	private String[] output;

	public ServiceDescription() {
	}

	public ServiceDescription(String description, String[] input, String[] output) {
		this.description = description;
		this.input = input;
		this.output = output;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String[] getInput() {
		return input;
	}

	public void setInput(String[] input) {
		this.input = input;
	}

	public String[] getOutput() {
		return output;
	}

	public void setOutput(String[] output) {
		this.output = output;
	}

	@Override
	public String toString() {
		return "ServiceDescription [description=" + description + ", input="
				+ Arrays.toString(input) + ", output="
				+ Arrays.toString(output) + "]";
	}
}
