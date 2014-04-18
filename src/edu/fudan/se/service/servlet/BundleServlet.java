package edu.fudan.se.service.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.fudan.se.service.bundle.chooser.BundleChooser;
import edu.fudan.se.service.bundle.chooser.BundleChooserHolder;
import edu.fudan.se.service.bundle.message.Response;
import edu.fudan.se.service.bundle.message.ServiceDescription;
import edu.fudan.se.service.servlet.util.Util;

/**
 * Servlet implementation class BundleServlet
 */
@WebServlet("/BundleServlet")
public class BundleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BundleServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ServiceDescription serviceDescription = getServiceDescriptionFromRequest(request);
		BundleChooser bundleChooser = BundleChooserHolder
				.getDefaultBundleChooser();
		Response bundleDescription = bundleChooser
				.getResponseByDescription(serviceDescription);

		System.out.println("Download Bundle " + bundleDescription.getName());
		generateResponse(response, bundleDescription);
	}

	private ServiceDescription getServiceDescriptionFromRequest(
			HttpServletRequest request) {
		return Util.getGson().fromJson(
				request.getParameter("service_description"),
				ServiceDescription.class);
	}

	private void generateResponse(HttpServletResponse response,
			Response bundleDescription) throws IOException {
		response.setContentType("application/x-json");
		PrintWriter writer = new PrintWriter(response.getOutputStream());
		writer.println(Util.getGson().toJson(bundleDescription));
		writer.flush();
	}

}
