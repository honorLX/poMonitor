package pomonitor.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ÍøÕ¾×ÛºÏÆÀ²âÒ³Ãæ
 * 
 * @author hengyi
 * 
 */
public class SiteEvaluationServlet extends HttpServlet {

    public SiteEvaluationServlet() {
	super();
    }

    public void destroy() {
	super.destroy(); // Just puts "destroy" string in log
	// Put your code here
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {

    }

    public void init() throws ServletException {
	// Put your code here
    }

}
