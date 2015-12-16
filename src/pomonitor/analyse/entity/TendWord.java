package pomonitor.analyse.entity;

public class TendWord {
	// 分词的序号
	private int id;
	// 分词的内容
	private String cont;

	// semparent 与 semrelate 成对出现
	// 语义依存分析的父亲结点id 号
	private String semparent;

	// 相对应的关系
	private String semrelate;

	private String parent;
	private String relate;

}
