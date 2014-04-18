package edu.fudan.se.service.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import edu.fudan.se.service.bundle.chooser.BundleChooserHolder;
import edu.fudan.se.service.bundle.message.Response;
import edu.fudan.se.service.bundle.message.ServiceDescription;
import edu.fudan.se.service.servlet.util.Parameter;

/**
 * Servlet implementation class BundleServlet
 */
@WebServlet("/BundleServlet")
public class BundleServlet extends JSONServlet {

	private static final long serialVersionUID = 889846797074888178L;

	@Override
	protected Object getRsponseObject(HttpServletRequest request) {
		ServiceDescription serviceDescription = getServiceDescriptionFromRequest(request);
		Response bundleDescription = BundleChooserHolder
				.getDefaultBundleChooser().getResponseByDescription(
						serviceDescription);
		System.out.println("Download Bundle " + bundleDescription.getName());
		return bundleDescription;
	}

	private ServiceDescription getServiceDescriptionFromRequest(
			HttpServletRequest request) {
		return Parameter.getGson().fromJson(
				request.getParameter("service_description"),
				ServiceDescription.class);
	}
}
