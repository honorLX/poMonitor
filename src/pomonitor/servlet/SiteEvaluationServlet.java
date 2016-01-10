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

import com.alibaba.fastjson.JSON;

/**
 * 网站综合评测页面
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
		String start_time = request.getParameter("start_time");
		String end_time = request.getParameter("end_time");
		String method = request.getParameter("method");
		String userID = request.getParameter("userID");
		String resJson = "";
		switch (method) {
		case "getWebTend":
			resJson = getWebTend(start_time, end_time, userID);
			break;
		default:
			break;
		}
		System.out.println(resJson);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(resJson);
	}

	public void init() throws ServletException {
		// Put your code here
	}

	/**
	 * 获取各网站的倾向性
	 * 
	 * getWebTend
	 * 
	 * @param start_time
	 * @param end_time
	 * @param userID
	 * @return
	 */
	private String getWebTend(String start_time, String end_time, String userID) {
		String resJson = "";
		ArticleTendAnalyseRealize articleTendAnalyse = new ArticleTendAnalyseRealize();
		ArticleTendAnalyse.tendAnalyse(start_time, end_time, userID);
		HashMap<String, Float> hashMap = ArticleTendAnalyse.showWebTend(
				start_time, end_time, userID);
		List<GeneratePara> testJSons = new ArrayList<>();
		for (Iterator iterator = hashMap.keySet().iterator(); iterator
				.hasNext();) {
			String webName = (String) iterator.next();
			Float score = hashMap.get(webName);
			webName = ArticleTendAnalyse.EnglishWebNameToChinese(webName);
			String polarity = "";
			if (score > -1 && score < 1) {
				polarity = "客观";
			} else if (score > 1) {
				polarity = "正面";
			} else {
				polarity = "负面";
			}
			Series series = new Series(score, polarity);
			GeneratePara json = new GeneratePara(webName, series);
			testJSons.add(json);
		}
		resJson = JSON.toJSONString(testJSons);
		return resJson;
	}

	/**
	 * 构造生成Json的内部类
	 * 
	 * @author xiaoyulun 2016年1月7日 上午10:30:53
	 */

	class Series {
		private Float data;
		private String name;

		public Series(Float data, String name) {
			this.data = data;
			this.name = name;

		}

		public Float getData() {
			return this.data;
		}

		public String getName() {
			return this.name;
		}
	}

	class GeneratePara {
		private String xAxis;
		private Series series;

		public GeneratePara(String xAxis, Series series) {
			this.xAxis = xAxis;
			this.series = series;
		}

		public String getXAxis() {
			return this.xAxis;
		}

		public Series getSeries() {
			return this.series;
		}
	}
}
