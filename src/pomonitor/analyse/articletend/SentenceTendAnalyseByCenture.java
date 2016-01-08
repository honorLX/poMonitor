package pomonitor.analyse.articletend;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import pomonitor.analyse.entity.TendSentence;
import pomonitor.analyse.entity.TendWord;
import pomonitor.entity.Emotionalword;
import pomonitor.entity.LeverWord;
import pomonitor.util.EmotionalDictionary;
import pomonitor.util.LeverWordDictionary;
import pomonitor.util.NegWordDictionary;

/**
 * 自定义的句子倾向性分析，中心分析法，正式版本
 * 
 * @author zhaolong 2015年12月23日 下午7:09:30
 */
public class SentenceTendAnalyseByCenture implements ISentenceTendAnalyse {
    // 情感词字典
    private EmotionalDictionary emotionalDictionary;

    // 程度词字典
    private LeverWordDictionary levelDictionary;

    // 否定词字典
    private NegWordDictionary negDictionary;

    // 否定处理
    private final float neg = -0.2f;

    // 当前处理的句子
    private TendSentence sentence;

    // 当前所处理的句子的词列表
    private List<TendWord> wordsList;

    /**
     * 构造方法初始化一些基本信息
     */
    public SentenceTendAnalyseByCenture() {
	emotionalDictionary = new EmotionalDictionary();
	levelDictionary = new LeverWordDictionary();
	negDictionary = new NegWordDictionary();
    }

    @Override
    public float analyseSentenceTend(TendSentence sentence) {
	this.sentence = sentence;
	wordsList = sentence.getWords();
	int id = getId();
	LevelAndEmotion result = getTendScore(id);
	// 将当前的结果分数保存到sentence里面去
	sentence.setTendScore(result.emotion);
	return result.emotion;
    }

    /**
     * 获取当前句子核心的Id
     * 
     * @return
     */
    private int getId() {
	int id = -1;
	for (TendWord td : wordsList) {
	    if (td.getParent() == -1) {
		id = td.getId();
		System.out.println("获取到的核心词：" + td.getCont());
	    }
	}
	return id;
    }

    /**
     * 计算当前中心节点的情感状态
     * 
     * @param id
     * @return LevelAndEmotion
     */
    private LevelAndEmotion getTendScore(int id) {
	System.out.println();
	System.out.println();

	// 保存当前节点的计算结果
	LevelAndEmotion le = new LevelAndEmotion();

	// 当前词的具体引用
	TendWord tendWord = wordsList.get(id);

	// 计算当前词的emotion
	float emotion = findEmotionValue(tendWord.getCont());

	// 计算当前词的level
	float level = findLevelValue(tendWord.getCont());
	le.emotion = emotion;
	le.level = level;
	// 计算当前词的孩子id
	List<Integer> childrenIdList = findChildId(id);
	// 若无孩子节点则返回当前节点
	if (childrenIdList.size() == 0) {
	    System.out.println("当前词 " + tendWord.getCont() + "  le.emotion: "
		    + le.emotion + "    le.level: " + le.level);
	    return le;
	} else {
	    System.out.println(tendWord.getCont() + "  拥有 "
		    + +childrenIdList.size() + " 个孩子 ");
	    // 接收当前词其孩子词的计算结果
	    for (Integer childId : childrenIdList) {
		LevelAndEmotion lae = getTendScore(childId);
		// 增加此查找只为了测试，以后效率可以省去
		String nowWord = wordsList.get(childId).getCont();
		if (Math.abs(lae.emotion) > 0) {
		    System.out.println(childId + "  加分  " + lae.emotion);
		    le.emotion += lae.emotion;
		    System.out.println("当前词：" + tendWord.getCont() + " 的情感被 "
			    + nowWord + " 增加到 " + le.emotion);
		} else {
		    le.level *= lae.level;
		    System.out.println("当前词：" + tendWord.getCont() + " 的强度被 "
			    + nowWord + " 增加到 " + le.level);
		}
	    }
	    le.emotion *= le.level;
	    System.out.println("当前词：" + tendWord.getCont() + " 的情感最终为 "
		    + le.emotion);
	}

	return le;
    }

    /**
     * 查找当前节点的孩子Id
     * 
     * @param id
     * @return
     */
    private List<Integer> findChildId(int id) {
	List<Integer> childrenIdList = new ArrayList<>();
	for (TendWord td : wordsList) {
	    if (td.getParent() == id) {
		childrenIdList.add(td.getId());
	    }
	}
	return childrenIdList;
    }

    /**
     * 查找并计算当前词的情感分数
     * 
     * @param word
     * @return float
     */
    private float findEmotionValue(String word) {
	System.out.println("当前词：" + word);
	float score = 0;
	// 查找情感词典
	System.out.println(emotionalDictionary);
	Emotionalword eword = emotionalDictionary.getWord(word);
	if (eword != null) {
	    System.out.println(eword.getId());
	    System.out.println(eword.getPolarity() + "~~~~~~~");
	    System.out.println(eword.getStrength() + "~~~~~~~~~");
	    // 计算分数
	    score = eword.getPolarity() * (float) eword.getStrength() / 10;
	} else {
	    // 此处查找近义词典计算分数
	}
	System.out.println("情感词 " + word + " 得分：" + score);
	return score;
    }

    /**
     * 查找并计算当前词的强度分数
     * 
     * @param word
     * @return float
     */
    private float findLevelValue(String word) {
	float score = 1;
	LeverWord lvWord = levelDictionary.getWord(word);
	if (lvWord != null) {
	    score *= (lvWord.getScore() / 10 + 1);
	} else {
	    // 如果包含于否定词库，则降级取反
	    if (negDictionary.map.containsKey(word)) {
		score *= neg;
	    }
	}
	System.out.println("强度词 " + word + " 得分：" + score);
	return score;
    }

    /**
     * 一个用于保存每次计算状态的类
     * 
     * @author zhaolong 2015年12月23日 下午7:18:17
     */
    class LevelAndEmotion {
	boolean isEmotion = false;
	float level = 1;
	float emotion = 0;
	String count;
    }

    @Test
    public void test() {
	SentenceSplier splier = new SentenceSplier();
	String testStr = "你这个非常笨的笨蛋";
	List<TendWord> list = splier.spil(testStr);
	TendSentence sentence = new TendSentence();
	sentence.setWords(list);
	SentenceTendAnalyseByCenture stab = new SentenceTendAnalyseByCenture();
	float score = stab.analyseSentenceTend(sentence);
	System.out.println(score);
    }
}
