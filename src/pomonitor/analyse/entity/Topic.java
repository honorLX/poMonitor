package pomonitor.analyse.entity;

import java.util.List;
import pomonitor.view.entity.ArticleView;

/**
 * 话题
 * 
 * @author caihengyi 2015年12月15日 上午10:17:13
 */
public class Topic {

	private String content;// 话题内容
	private Double weight;// 话题权重
	private boolean isSensitiveWords;// 该话题是否是敏感词（或其近义词）
	private List<ArticleView> articleViews;// 包含该话题的文章集合
	private Attitude attitude;// 该话题的褒贬态度信息

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public boolean isSensitiveWords() {
		return isSensitiveWords;
	}

	public void setSensitiveWords(boolean isSensitiveWords) {
		this.isSensitiveWords = isSensitiveWords;
	}

	public List<ArticleView> getArticleViews() {
		return articleViews;
	}

	public void setArticleViews(List<ArticleView> articleViews) {
		this.articleViews = articleViews;
	}

	public Attitude getAttitude() {
		return attitude;
	}

	public void setAttitude(Attitude attitude) {
		this.attitude = attitude;
	}

}
