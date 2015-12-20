package pomonitor.analyse.articlesubanalyse;

import pomonitor.analyse.entity.TendAnalyseArticle;
import pomonitor.analyse.entity.TendSentence;
import pomonitor.analyse.entity.TendWord;
import pomonitor.util.IdeaWordDictionary;

/**
 * 主张类词语加分
 * 
 * @author Administrator
 * 
 */
public class SubScoreAddThink implements ISubScoreAdd {
	private IdeaWordDictionary ideaWordDictionary;

	public SubScoreAddThink() {
		ideaWordDictionary = new IdeaWordDictionary();
	}

	@Override
	public TendSentence add(TendAnalyseArticle article, TendSentence sentence) {
		// 主张类词语出现的数量
		int count = 0;
		for (TendWord tw : sentence.getWords()) {
			String nowWord = tw.getCont();
			if (ideaWordDictionary.map.containsKey(nowWord)) {
				count++;
			}
		}
		float thinkScore = (float) count / sentence.getWords().size();
		sentence.setThinkScore(thinkScore);
		return sentence;
	}
}
