package pomonitor.analyse.articlesubanalyse;

import pomonitor.analyse.entity.Article;
import pomonitor.analyse.entity.Sentence;

/**
 * 对于文章的每一句分析其倾向性的接口
 * 
 * @author zhaolong
 * 
 */
public interface ISubScoreAdd {
	/**
	 * 
	 * @param article
	 * @param sentence
	 * @return sentence
	 */
	public Sentence add(Article article, Sentence sentence);

}
