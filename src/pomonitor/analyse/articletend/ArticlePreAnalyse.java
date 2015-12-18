package pomonitor.analyse.articletend;

import java.util.List;

import pomonitor.analyse.entity.TendAnalyseArticle;
import pomonitor.analyse.entity.TendSentence;
import pomonitor.entity.NewsEntity;

/**
 * 文章预处理器，将新闻字符串对象预处理为需要分析的Article对象
 * 
 * @author zhaolong 2015年12月16日 下午9:31:20
 */
public class ArticlePreAnalyse {

	private NewsEntity news;

	private TendAnalyseArticle article;

	// 句子分析器
	private SentenceSplier sentenceSplier;

	// 文章分析器
	private ArticleSplier articleSplier;

	public ArticlePreAnalyse(ArticleSplier articleSplier) {
		this.articleSplier = new ArticleSplier();
		sentenceSplier = new SentenceSplier();
	}

	/**
	 * 初始化一篇文章，加载基本参数
	 * 
	 * @param news
	 */
	private void init(NewsEntity news) {
		this.news = news;
		article = new TendAnalyseArticle();
		article.setKeyWords(news.getKeywords());
		article.setTitle(news.getTitle());
	}

	/**
	 * 断句并且分依每一句，主要处理文章正文
	 */

	private void splitArticle() {
		String content = news.getAllContent();
		List<TendSentence> relSentences = articleSplier.spil(content);
		article.setSentences(relSentences);
	}

	/**
	 * 向外提供的文章预处理方法，格式化处理好的文章以待分析
	 * 
	 * @param news
	 * @return TendAnalyseArticle
	 */
	public TendAnalyseArticle getPreArticle(NewsEntity news) {
		init(news);
		splitArticle();
		return article;
	}

}
