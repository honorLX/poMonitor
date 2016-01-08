package pomonitor.analyse.entity;

import java.util.Date;

/**
 * 展示列表用的文本对象，在视图上表现为一条信息（包含后面的tag）
 * 
 * @author caihengyi 2015年12月15日 上午10:29:25
 */
public class ArticleShow implements Comparable<ArticleShow> {

    private String title;
    private String url;
    private String description;
    private Date timestamp;
    private Attitude attitude;// 新闻倾向性
    private ArticleDegree degree;// 倾向性分级
    private String comeFrom;// 来源网站的名字
    public Double heat = 0.0;// 该条新闻的热度

    // 文章分级
    public enum ArticleDegree {
	A("一级"), B("二级"), C("三级");// 级别越高越严重

	private String degree;

	// 枚举对象构造函数
	private ArticleDegree(String degree) {
	    this.degree = degree;
	}

	// 枚举对象获取褒贬态度的方法
	public String getDegree() {
	    return this.degree;
	}
    }

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

    public Date getTimestamp() {
	return timestamp;
    }

    public void setTimestamp(Date timestamp) {
	this.timestamp = timestamp;
    }

    public Attitude getAttitude() {
	return attitude;
    }

    public void setAttitude(Attitude attitude) {
	this.attitude = attitude;
    }

    public ArticleDegree getDegree() {
	return degree;
    }

    public void setDegree(ArticleDegree degree) {
	this.degree = degree;
    }

    public String getComeFrom() {
	return comeFrom;
    }

    public void setComeFrom(String comeFrom) {
	this.comeFrom = comeFrom;
    }

    public Double getHeat() {
	return heat;
    }

    public void setHeat(Double heat) {
	this.heat = heat;
    }

    @Override
    public int compareTo(ArticleShow o) {
	if (this.heat < o.heat)
	    return -1;
	if (this.heat > o.heat)
	    return 1;
	return 0;
    }
}
