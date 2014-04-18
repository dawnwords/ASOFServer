package edu.fudan.se.service.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.fudan.se.service.servlet.util.Parameter;

public abstract class JSONServlet extends HttpServlet{

	private static final long serialVersionUID = 1206630286809999652L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/x-json");
		PrintWriter writer = new PrintWriter(response.getOutputStream());
		writer.println(Parameter.getGson().toJson(getRsponseObject(request)));
		writer.flush();
	}
	
	protected abstract Object getRsponseObject(HttpServletRequest req);
}
