package edu.fudan.se.service.bundle.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import edu.fudan.se.service.bundle.chooser.BundleChooser;
import edu.fudan.se.service.bundle.chooser.BundleChooserHolder;
import edu.fudan.se.service.bundle.message.Response;
import edu.fudan.se.service.bundle.message.ServiceDescription;

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
		JSONObject requestJson = JSONObject.fromObject(request
				.getParameter("service_description"));
		return (ServiceDescription) JSONObject.toBean(requestJson,
				ServiceDescription.class);
	}

	private void generateResponse(HttpServletResponse response,
			Response bundleDescription) throws IOException {
		JSONObject json = JSONObject.fromObject(bundleDescription);
		response.setContentType("application/x-json");
		PrintWriter writer = new PrintWriter(response.getOutputStream());
		writer.println(json.toString());
		writer.flush();
	}

}
