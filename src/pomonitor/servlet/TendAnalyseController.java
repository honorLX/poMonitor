package pomonitor.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pomonitor.analyse.ArticleTendAnalyse;
import pomonitor.analyse.articletend.ArticleTendAnalyseRealize;
import pomonitor.analyse.entity.Series;
import pomonitor.analyse.entity.TestJSon;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @author xiaoyulun 2016年1月5日 上午11:44:26
 */
public class TendAnalyseController extends HttpServlet {

    /**
     * Constructor of the object.
     */
    public TendAnalyseController() {
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
     * @param request
     *            the request send by the client to the server
     * @param response
     *            the response send by the server to the client
     * @throws ServletException
     *             if an error occurred
     * @throws IOException
     *             if an error occurred
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	this.doPost(request, response);
    }

    /**
     * The doPost method of the servlet. <br>
     * 
     * This method is called when a form has its tag value method equals to
     * post.
     * 
     * @param request
     *            the request send by the client to the server
     * @param response
     *            the response send by the server to the client
     * @throws ServletException
     *             if an error occurred
     * @throws IOException
     *             if an error occurred
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {

	ArticleTendAnalyseRealize articleTendAnalyse = new ArticleTendAnalyseRealize();
	String start_time = request.getParameter("start_time");
	String end_time = request.getParameter("end_time");
	String userID = request.getParameter("userID");
	HashMap<String, Float> hashMap = ArticleTendAnalyse.tendAnalyse(
		start_time, end_time, userID);
	List<TestJSon> testJSons = new ArrayList<>();
	for (Iterator iterator = hashMap.keySet().iterator(); iterator
		.hasNext();) {
	    String webName = (String) iterator.next();
	    Float score = hashMap.get(webName);
	    String polarity = "";
	    if (score > -1 && score < 1) {
		polarity = "客观";
	    } else if (score > 1) {
		polarity = "正面";
	    } else {
		polarity = "负面";
	    }
	    Series series = new Series();
	    series.setData(score);
	    series.setPolarity(polarity);
	    TestJSon json = new TestJSon();
	    json.setWebName(webName);
	    json.setSeries(series);
	    testJSons.add(json);
	}
	String jsonString = JSON.toJSONString(testJSons);
	response.setContentType("application/json");
	response.setCharacterEncoding("UTF-8");
	response.getWriter().write(jsonString);
    }

    /**
     * Initialization of the servlet. <br>
     * 
     * @throws ServletException
     *             if an error occurs
     */
    public void init() throws ServletException {
	// Put your code here
    }

}
