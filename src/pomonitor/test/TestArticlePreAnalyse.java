package pomonitor.test;

import org.junit.Test;

import pomonitor.analyse.articletend.ArticlePreAnalyse;
import pomonitor.analyse.articletend.SentenceSplier;
import pomonitor.analyse.entity.TendAnalyseArticle;
import pomonitor.entity.News;
import pomonitor.entity.NewsDAO;
import pomonitor.entity.NewsEntity;
import pomonitor.util.NewsAndNewsEnriryTran;

public class TestArticlePreAnalyse {
	@Test
	public void testGetPreArticle(){
		ArticlePreAnalyse apa=new ArticlePreAnalyse(new SentenceSplier());
		NewsDAO newsDao=new NewsDAO();
		News news=newsDao.findById(19);
		NewsEntity newsEntity=NewsAndNewsEnriryTran.newsToNewsEntity(news);
		TendAnalyseArticle article=apa.getPreArticle(newsEntity);
		System.out.println(article.getSentences());
	}
	

}
