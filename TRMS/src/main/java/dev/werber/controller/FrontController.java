package dev.werber.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.catalina.servlets.DefaultServlet;

import dev.werber.delegates.FrontControllerDelegate;

/**
 * Servlet implementation class FrontController
 */
public class FrontController extends DefaultServlet {
	private static final long serialVersionUID = 1L;
    private RequestHandler rh = new RequestHandler();
    /**
     * @see DefaultServlet#DefaultServlet()
     */
    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		FrontControllerDelegate fcd = rh.handle(req, resp);
		
		if (req.getRequestURI().substring(req.getContextPath().length()).startsWith("/static")) {
			super.doGet(req,resp);
		} else {
			if (fcd != null)
				fcd.process(req, resp);
			else
				resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}


    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		process(request, response);
	}

	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		process(request, response);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}


}
