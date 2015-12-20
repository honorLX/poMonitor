package pomonitor.analyse.articlesubanalyse;

import java.util.Set;

import pomonitor.analyse.entity.TendAnalyseArticle;
import pomonitor.analyse.entity.TendSentence;
import pomonitor.analyse.entity.TendWord;

/**
 * 文章标题加分，此处将其与关键字加分合并处理，以后在做细化
 * 
 * @author Administrator
 * 
 */
public class SubScoreAddTitle implements ISubScoreAdd {

	@Override
	public TendSentence add(TendAnalyseArticle article, TendSentence sentence) {
		// 出现实词的数目
		int count = 0;
		// title所包含的实词
		Set<String> set = article.getSet();
		for (TendWord td : sentence.getWords()) {
			// 如果此句话包含title中和keyWords中的词则加分
			if (set.contains(td.getCont())) {
				count++;
			}
		}
		// 还是取用百分比的计算方法，后期有待改进
		float titleAndKeyScore = (float) count / set.size();
		sentence.setTitleScore(titleAndKeyScore);

		return sentence;
	}

}
