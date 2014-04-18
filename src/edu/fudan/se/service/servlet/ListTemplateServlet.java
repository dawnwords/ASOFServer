package edu.fudan.se.service.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import edu.fudan.se.service.template.list.TemplateLister;

/**
 * Servlet implementation class ListTemplateServlet
 */
@WebServlet("/ListTemplateServlet")
public class ListTemplateServlet extends JSONServlet {

	private static final long serialVersionUID = -8251034719742720575L;

	@Override
	protected Object getRsponseObject(HttpServletRequest req) {
		return TemplateLister.listTemplates();
	}

}
