package pomonitor.analyse.entity;

/**
 * 话题发现模块中，词项实体
 * 
 * @author caihengyi 2015年12月15日 下午2:40:53
 */
public class TDArticleTerm {
	private String value;// 词项内容
	private TDPosition position;// 该词项在该文本的位置
	private int documentFrequency;// 该词项在所有文档中出现的频数
	private int termfrequency; // 该词项在该文本里出现的频数

	public String getvalue() {
		return value;
	}

	public void setvalue(String value) {
		this.value = value;
	}

	public TDPosition getposition() {
		return position;
	}

	public void setposition(TDPosition position) {
		this.position = position;
	}

	public int getdocumentFrequency() {
		return documentFrequency;
	}

	public void termfrequency(int documentFrequency) {
		this.documentFrequency = documentFrequency;
	}

	public int gettermfrequency() {
		return termfrequency;
	}

	public void settermfrequency(int termfrequency) {
		this.termfrequency = termfrequency;
	}
}
