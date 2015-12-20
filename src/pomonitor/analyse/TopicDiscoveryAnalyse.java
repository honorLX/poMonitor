package pomonitor.analyse;

import java.util.ArrayList;
import java.util.List;

import pomonitor.analyse.entity.TDArticle;
import pomonitor.analyse.entity.TDArticleTerm;
import pomonitor.analyse.entity.TDPosition;
import pomonitor.analyse.entity.Topic;
import pomonitor.analyse.segment.TermsGenerator;
import pomonitor.analyse.topicdiscovery.TopicDiscovery;
import pomonitor.entity.News;
import pomonitor.entity.NewsDAO;
import pomonitor.entity.SenswordDAO;

import com.hankcs.hanlp.seg.common.Term;

/**
 * 话题发现模块, 介于Controller与具体的话题发现模块之间;向上对controller服务; 向下调用话题发现具体分析包提供的功能
 * 
 * @author caihengyi 2015年12月15日 下午4:12:07
 */
public class TopicDiscoveryAnalyse {

	/**
	 * 根据特定用户的敏感词库，获取一段时间内新闻文本的话题集合
	 * 
	 * @param startDateStr
	 * @param endDateStr
	 * @param userId
	 * @return
	 */
	public List<Topic> DiscoverTopics(String startDateStr, String endDateStr,
			int userId) {
		// 根据起止时间获取数据库中的新闻文本
		NewsDAO nd = new NewsDAO();
		List<News> newsList = nd.findBetweenDate(startDateStr, endDateStr);
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
			tmpArt.setArticleTerms(tmpTDArtTerms);
			tmpArt.setComeFrom(news.getWeb());
			tmpArt.setDescription(news.getContent());
			tmpArt.setTimestamp(news.getTime());
			tmpArt.setTitle(news.getTitle());
			tmpArt.setUrl(news.getUrl());
			tdArticleList.add(tmpArt);
		}
		// 调用话题发现功能模块，返回话题集合
		TopicDiscovery td = new TopicDiscovery();
		SenswordDAO sd = new SenswordDAO();
		return td.getTopics(tdArticleList, sd.findByProperty("userid", userId));
	}
}
