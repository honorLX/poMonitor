package pomonitor.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pomonitor.analyse.TopicDiscoveryAnalyse;
import pomonitor.analyse.entity.Topic;

import com.alibaba.fastjson.JSON;

public class TopicDiscoveryController extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public TopicDiscoveryController() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		  doPost(request, response); 
	}

	/**
	 * @return ·µ»ØTopic¼¯ºÏjson
	 *
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String startDateStr=request.getParameter("startTime");
		String endDateStr=request.getParameter("endTime");
		int userId=Integer.parseInt(request.getParameter("userId"));
		
		TopicDiscoveryAnalyse tdDiscovery=new TopicDiscoveryAnalyse();
		List<Topic> topics=tdDiscovery.DiscoverTopics(startDateStr, endDateStr, userId);
		String json =JSON.toJSONString(topics);
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(json);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}
}
