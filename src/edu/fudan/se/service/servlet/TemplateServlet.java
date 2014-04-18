package edu.fudan.se.service.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import edu.fudan.se.service.template.get.TemplateProvider;

/**
 * Servlet implementation class TemplateServlet
 */
@WebServlet("/TemplateServlet")
public class TemplateServlet extends JSONServlet {
	private static final long serialVersionUID = 5511197801840205440L;

	@Override
	protected Object getRsponseObject(HttpServletRequest request) {
		String template = request.getParameter("template");
		return TemplateProvider.getTemplate(template);
	}
}
