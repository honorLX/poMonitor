package pomonitor.analyse;

import java.util.HashMap;
import java.util.List;

import pomonitor.analyse.articletend.ArticleTendAnalyseRealize;
import pomonitor.analyse.entity.TendAnalyseArticle;
import pomonitor.entity.News;
import pomonitor.entity.NewsDAO;

/**
 * 
 * @author xiaoyulun 2016年1月5日 上午11:44:52
 */
public class ArticleTendAnalyse {
    public static HashMap<String, Float> tendAnalyse(String start_time,
	    String end_time, String UserId) {
	NewsDAO newsDAO = new NewsDAO();
	List<News> newsList = newsDAO.findBetweenDate(start_time, end_time);
	HashMap<String, Float> hashMap = new HashMap<>();
	ArticleTendAnalyseRealize analyseRealize = new ArticleTendAnalyseRealize();
	for (News news : newsList) {
	    TendAnalyseArticle tendArticle = new TendAnalyseArticle();

	    try {
		tendArticle = analyseRealize.analyseArticleTend(news);
	    } catch (Exception e) {
		// TODO: handle exception
		System.out.println("文本过长");
		continue;
	    }
	    String webName = tendArticle.getWeb();
	    Float score = tendArticle.getTendScore();
	    if (hashMap.get(webName) == null) {
		hashMap.put(webName, score);
	    } else {
		hashMap.put(webName, hashMap.get(webName) + score);
	    }
	}
	return hashMap;
    }
}
