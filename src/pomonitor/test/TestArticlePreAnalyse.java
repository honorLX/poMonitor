package pomonitor.test;

import org.junit.Test;

import pomonitor.analyse.articlesubanalyse.SentenceSubCountByWeightAverage;
import pomonitor.analyse.articlesubanalyse.SubScoreAddPos;
import pomonitor.analyse.articlesubanalyse.SubScoreAddThink;
import pomonitor.analyse.articlesubanalyse.SubScoreAddWord;
import pomonitor.analyse.articlesubanalyse.SubSentenceGet;
import pomonitor.analyse.articletend.ArticlePreAnalyse;
import pomonitor.analyse.articletend.ArticleSplier;
import pomonitor.analyse.entity.TendAnalyseArticle;
import pomonitor.entity.News;
import pomonitor.entity.NewsDAO;
import pomonitor.entity.NewsEntity;
import pomonitor.util.NewsAndNewsEnriryTran;

public class TestArticlePreAnalyse {
	@Test
	public void testGetPreArticle() {
		ArticlePreAnalyse apa = new ArticlePreAnalyse(new ArticleSplier());
		NewsDAO newsDao = new NewsDAO();
		News news = newsDao.findById(8);
		NewsEntity newsEntity = NewsAndNewsEnriryTran.newsToNewsEntity(news);
		TendAnalyseArticle article = apa.getPreArticle(newsEntity);
		System.out.println(article.getSentences());
		System.out.println("title和keyword提取实词之后：" + article.getSet());
	}

	@Test
	public void testSubSetenceGet() {
		ArticlePreAnalyse apa = new ArticlePreAnalyse(new ArticleSplier());
		NewsDAO newsDao = new NewsDAO();
		News news = newsDao.findById(9);
		NewsEntity newsEntity = NewsAndNewsEnriryTran.newsToNewsEntity(news);
		TendAnalyseArticle article = apa.getPreArticle(newsEntity);
		System.out.println(article.getSentences());
		System.out.println("title和keyword提取实词之后：" + article.getSet());
		SubSentenceGet ssg = new SubSentenceGet(
				new SentenceSubCountByWeightAverage(), article);
		ssg.addScoreAdder(new SubScoreAddPos());
		ssg.addScoreAdder(new SubScoreAddThink());
		ssg.addScoreAdder(new SubScoreAddWord());
		ssg.countSubScore();
		ssg.getSubSentence(4);
		for (int i = 0; i < 4; i++) {
			System.out.println("~~~~~~~~~~");
			System.out.println(article.getSubSentences().get(i).getPosScore());
			System.out
					.println(article.getSubSentences().get(i).getThinkScore());
			System.out
					.println(article.getSubSentences().get(i).getTitleScore());
			System.out.println(article.getSubSentences().get(i)
					.getSubjectScore());
		}
	}
}
