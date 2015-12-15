package pomonitor.analyse.entity;

import java.util.List;

/**
 * 每篇文章所分的句子对象
 * 
 * @author Administrator
 * 
 */
public class Sentence {
	// 句子在文章中的序号
	private int id;
	// 句子包含的实词集合
	private List<String> words;

	// 倾向性分数
	private float tendScore;

	// 主题分数
	private float subjectScore;

	// 词贡献的句子主题分数
	private float wordSubScore;

	// 标题贡献的句子主题分数
	private float titleScore;

	// 句子位置贡献的句子分数
	private float posScore;

	// 主张词贡献的句子分数
	private float thinkScore;

	public float getTendScore() {
		return tendScore;
	}

	public void setTendScore(float tendScore) {
		this.tendScore = tendScore;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<String> getWords() {
		return words;
	}

	public void setWords(List<String> words) {
		this.words = words;
	}

}
