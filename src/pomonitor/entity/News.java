package pomonitor.entity;

// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * News entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "news", catalog = "pomonitor")
public class News implements java.io.Serializable {

	// Fields

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "relId", unique = true, nullable = false)
	private Integer relId;

	private String id;
	private String title;
	private String url;
	private String content;
	private String web;
	private String time;
	private String allContent;
	private String keyWords;
	private String contentPath;
	private Integer failedCount;
	private Integer isFinsh;
	private Integer isFailed;
	private Integer isWorking;

	// Constructors

	/** default constructor */
	public News() {
	}

	/** minimal constructor */
	public News(Integer relId, String id, String title, String url,
			String content, String web, String time, String allContent,
			Integer failedCount, Integer isFinsh, Integer isFailed,
			Integer isWorking) {
		this.relId = relId;
		this.id = id;
		this.title = title;
		this.url = url;
		this.content = content;
		this.web = web;
		this.time = time;
		this.allContent = allContent;
		this.failedCount = failedCount;
		this.isFinsh = isFinsh;
		this.isFailed = isFailed;
		this.isWorking = isWorking;
	}

	/** full constructor */
	public News(Integer relId, String id, String title, String url,
			String content, String web, String time, String allContent,
			String keyWords, String contentPath, Integer failedCount,
			Integer isFinsh, Integer isFailed, Integer isWorking) {
		this.relId = relId;
		this.id = id;
		this.title = title;
		this.url = url;
		this.content = content;
		this.web = web;
		this.time = time;
		this.allContent = allContent;
		this.keyWords = keyWords;
		this.contentPath = contentPath;
		this.failedCount = failedCount;
		this.isFinsh = isFinsh;
		this.isFailed = isFailed;
		this.isWorking = isWorking;
	}

	// Property accessors

	public Integer getRelId() {
		return this.relId;
	}

	public void setRelId(Integer relId) {
		this.relId = relId;
	}

	@Column(name = "id", nullable = false, length = 50)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "title", nullable = false, length = 100)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "url", nullable = false)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "content", nullable = false, length = 65535)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "web", nullable = false)
	public String getWeb() {
		return this.web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	@Column(name = "time", nullable = false, length = 30)
	public String getTime() {
		return this.time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Column(name = "allContent", nullable = false, length = 65535)
	public String getAllContent() {
		return this.allContent;
	}

	public void setAllContent(String allContent) {
		this.allContent = allContent;
	}

	@Column(name = "keyWords")
	public String getKeyWords() {
		return this.keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	@Column(name = "contentPath")
	public String getContentPath() {
		return this.contentPath;
	}

	public void setContentPath(String contentPath) {
		this.contentPath = contentPath;
	}

	@Column(name = "failedCount")
	public Integer getFailedCount() {
		return this.failedCount;
	}

	public void setFailedCount(Integer failedCount) {
		this.failedCount = failedCount;
	}

	@Column(name = "isFinsh")
	public Integer getIsFinsh() {
		return this.isFinsh;
	}

	public void setIsFinsh(Integer isFinsh) {
		this.isFinsh = isFinsh;
	}

	@Column(name = "isFailed")
	public Integer getIsFailed() {
		return this.isFailed;
	}

	public void setIsFailed(Integer isFailed) {
		this.isFailed = isFailed;
	}

	@Column(name = "isWorking")
	public Integer getIsWorking() {
		return this.isWorking;
	}

	public void setIsWorking(Integer isWorking) {
		this.isWorking = isWorking;
	}

}