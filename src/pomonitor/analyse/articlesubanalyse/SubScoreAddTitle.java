package pomonitor.analyse.articlesubanalyse;

import pomonitor.analyse.entity.TendAnalyseArticle;
import pomonitor.analyse.entity.TendSentence;

/**
 * 文章标题加分，此处将其与关键字加分合并处理，以后在做细化
 * 
 * @author Administrator
 * 
 */
public class SubScoreAddTitle implements ISubScoreAdd {

	// 需要加分的词性
	private String propertys[] = { "a", "i", "j", "k", "m", "n", "nd", "nh",
			"ni", "nl", "ns", "nt", "nz", "v", "ws" };

	@Override
	public TendSentence add(TendAnalyseArticle article, TendSentence sentence) {
		return null;
	}

}
