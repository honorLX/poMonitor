package pomonitor.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pomonitor.analyse.HotWordDiscoveryAnalyse;
import pomonitor.analyse.entity.HotWord;
import pomonitor.analyse.entity.HotWordResponse;
import pomonitor.analyse.entity.HotWordResult;
import pomonitor.analyse.entity.RetHotWord;
import pomonitor.analyse.entity.RetLink;

/**
 * 每一个servlet对应于前端的一个逻辑页面，前端可见的只是和当前页面相关的若干个请求接口， 每一个servlet不需要和后台的功能模块一一对应，特此说明
 * 
 * 当前servlet对应于热词发现页面（包括热词线球图和新闻列表）
 * 
 * @author hengyi
 * 
 */
public class HotWordsServlet extends HttpServlet {

	public HotWordsServlet() {
		super();
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * doGet或者doPost在这里相当于转发器，根据method参数调用对应方法，统一向外返回resultJson(不同分支赋予不同内容)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String methodName = request.getParameter("method");
		String resultJson = "";
		// 根据请求的方法，返回对应信息 resultJson
		switch (methodName) {
		case "getHotWords":
			// 返回热词集合
			String startDateStr = request.getParameter("startTime");
			String endDateStr = request.getParameter("endTime");
			int userId = Integer.parseInt(request.getParameter("userId"));
			resultJson = getHotWords(startDateStr, endDateStr, userId);
			break;
		case "getNewsByTopic":
			// 根据热词编号返回新闻列表
			resultJson = getNewsByTopic();
			break;
		default:
			break;
		}

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(resultJson);
	}

	/**
	 * 返回热词集合
	 * 
	 * 问题：话题下巨多的新闻列表信息，需要延迟加载
	 * 
	 * @param startDateStr
	 * @param endDateStr
	 * @param userId
	 * @return
	 */
	private String getHotWords(String startDateStr, String endDateStr,
			int userId) {
		HotWordDiscoveryAnalyse tdDiscovery = new HotWordDiscoveryAnalyse();
		List<HotWord> hotwords = tdDiscovery.discoverHotWords(startDateStr,
				endDateStr, userId);
		String resJSON = "";
		/******************* 将话题列表处理为JSON格式 *****************************/
		ArrayList<RetHotWord> retNodes = tdDiscovery.getRetHotWords();
		double[][] relevanceMat = tdDiscovery.getRelevanceMat();
		ArrayList<RetLink> retLinks = new ArrayList<RetLink>();
		for (int i = 0; i < relevanceMat.length; i++) {
			for (int j = 0; j < relevanceMat.length; j++) {
				RetLink _link = new RetLink();
				_link.setSource(i);
				_link.setTarget(j);
				_link.setWeight(relevanceMat[i][j]);
				retLinks.add(_link);
			}
		}
		HotWordResult results = new HotWordResult();
		results.setLinks(retLinks);
		results.setNodes(retNodes);
		HotWordResponse hotWordResponse = new HotWordResponse();
		hotWordResponse.setResults(results);
		/***********************************************************************/
		return resJSON;
	}

	/**
	 * 根据指定话题返回相关的新闻列表
	 * 
	 * @return
	 */
	private String getNewsByTopic() {
		String resJSON = "";
		return resJSON;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void init() throws ServletException {
		// Put your code here
	}

}
