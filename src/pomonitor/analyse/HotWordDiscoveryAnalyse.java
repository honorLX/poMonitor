package pomonitor.analyse;

import java.util.ArrayList;
import java.util.List;

import pomonitor.analyse.entity.TDArticle;
import pomonitor.analyse.entity.TDArticleTerm;
import pomonitor.analyse.entity.TDPosition;
import pomonitor.analyse.entity.HotWord;
import pomonitor.analyse.hotworddiscovery.HotWordDiscovery;
import pomonitor.analyse.segment.TermsGenerator;
import pomonitor.entity.News;
import pomonitor.entity.NewsDAO;
import pomonitor.entity.SenswordDAO;
import pomonitor.util.ConsoleLog;

import com.hankcs.hanlp.seg.common.Term;

/**
 * 话题发现模块, 介于Controller与具体的话题发现模块之间;向上对controller服务; 向下调用话题发现具体分析包提供的功能
 * 
 * @author caihengyi 2015年12月15日 下午4:12:07
 */
public class HotWordDiscoveryAnalyse {

    /**
     * 根据特定用户的敏感词库，获取一段时间内新闻文本的话题集合
     * 
     * @param startDateStr
     * @param endDateStr
     * @param userId
     * @return
     */
    public List<HotWord> DiscoverTopics(String startDateStr, String endDateStr,
	    int userId) {
	// 调用话题发现功能模块，返回话题集合
	HotWordDiscovery td = new HotWordDiscovery();
	SenswordDAO sd = new SenswordDAO();
	return td.getTopics(getArticlesBetweenDate(startDateStr, endDateStr),
		sd.findByProperty("userid", userId));
    }

    public List<TDArticle> getArticlesBetweenDate(String startDateStr,
	    String endDateStr) {
	// 根据起止时间获取数据库中的新闻文本
	NewsDAO nd = new NewsDAO();
	long start = System.currentTimeMillis();
	List<News> newsList = nd.findBetweenDate(startDateStr, endDateStr);
	long end = System.currentTimeMillis();
	ConsoleLog.PrintInfo(HotWordDiscoveryAnalyse.class, "从数据库中取出"
		+ startDateStr + "到" + endDateStr + "的文本，花费时间为" + (end - start)
		+ "毫秒");
	// 作分词，过滤预处理
	List<TDArticle> tdArticleList = new ArrayList<TDArticle>();
	TermsGenerator generateTerms = new TermsGenerator();
	for (News news : newsList) {
	    TDArticle tmpArt = new TDArticle();
	    List<TDArticleTerm> tmpTDArtTerms = new ArrayList<TDArticleTerm>();

	    List<Term> tmpTermList_allcontent = generateTerms.getTerms(news
		    .getAllContent());
	    List<Term> tmpTermList_keyword = generateTerms.getTerms(news
		    .getKeyWords());
	    List<Term> tmpTermList_title = generateTerms.getTerms(news
		    .getTitle());
	    for (Term term : tmpTermList_allcontent) {
		TDArticleTerm tmpArtTerm = new TDArticleTerm();
		tmpArtTerm.setposition(TDPosition.BODY);
		tmpArtTerm.setvalue(term.word);
		tmpTDArtTerms.add(tmpArtTerm);
	    }
	    for (Term term : tmpTermList_keyword) {
		TDArticleTerm tmpArtTerm = new TDArticleTerm();
		tmpArtTerm.setposition(TDPosition.META);
		tmpArtTerm.setvalue(term.word);
		tmpTDArtTerms.add(tmpArtTerm);
	    }
	    for (Term term : tmpTermList_title) {
		TDArticleTerm tmpArtTerm = new TDArticleTerm();
		tmpArtTerm.setposition(TDPosition.TITLE);
		tmpArtTerm.setvalue(term.word);
		tmpTDArtTerms.add(tmpArtTerm);
	    }
	    tmpArt.setArticleAllTerms(tmpTDArtTerms);
	    tmpArt.setComeFrom(news.getWeb());
	    tmpArt.setDescription(news.getContent());
	    tmpArt.setTimestamp(news.getTime());
	    tmpArt.setTitle(news.getTitle());
	    tmpArt.setUrl(news.getUrl());
	    tdArticleList.add(tmpArt);
	}
	return tdArticleList;
    }

}
