package pomonitor.analyse.entity;

import java.util.Date;
import java.util.List;

/**
 * 话题发现模块中从数据库得到新闻文本对象之后， 重新封装的用于话题发现的新闻文本对象
 * 
 * @author caihengyi 2015年12月15日 下午2:22:32
 */
public class TDArticle {

	private String title;
	private String url;
	private String description;
	private List<String> keyWords;
	private Date timestamp;
	private String comeFrom;// 来源网站的名字
	private List<TDArticleTerm> articleTerms;// 该文章包括的词项集合

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(List<String> keyWords) {
		this.keyWords = keyWords;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getComeFrom() {
		return comeFrom;
	}

	public void setComeFrom(String comeFrom) {
		this.comeFrom = comeFrom;
	}

	public List<TDArticleTerm> getArticleTerms() {
		return articleTerms;
	}

	public void setArticleTerms(List<TDArticleTerm> articleTerms) {
		this.articleTerms = articleTerms;
	}

}
