package pomonitor.analyse.topicdiscovery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pomonitor.analyse.entity.TDArticle;
import pomonitor.analyse.entity.TDArticleTerm;

/**
 * 以向量空间模型表示新闻文本
 * 
 * @author caihengyi 2015年12月14日 下午5:26:03
 */
public class TextVectorBuilder {

	// 新闻文本集合
	private List<TDArticle> topicDisArticleList = null;
	// 提取百分比
	private final double EXTRACT_PERCENT = 0.15;
	// 总的特征集合
	Map<String, Double> globalFeatureCollections = null;
	//含有此词汇文档的频数
	Map<String, Double> globalDocumentFrequency=null;
	/**
	 * 根据新闻文本集合和指定的提取百分比，获得有效的特征项集合，缩短向量长度
	 * 
	 * @param topicDisArticleList
	 * @param percentage
	 * @return 总特征集合，其长度即为向量长度
	 */
	private Map<String, Double> getFeatureSet(
			List<TDArticle> topicDisArticleList, double percentage) {
		
		// 计算完每篇新闻中词项的综合权值之后，赋值给this.newsList;

		return null;
	}

	/**
	 * 根据总的特征集合和新闻文本对象集合，生成向量集合
	 * 
	 * @param topicDisArticleList
	 * @param globalFeatureCollections
	 * @return
	 */
	public List<TDArticleTerm> buildVectors(
			List<TDArticle> topicDisArticleList) {
		globalFeatureCollections = getFeatureSet(topicDisArticleList,
				EXTRACT_PERCENT);

		// 利用this.newsList去生成向量

		
		return null;
	}
	
	/**
	 * 计算一篇文章的tf-idf 
	 * @param TDArticle
	 * @return Map<String, Double> 每篇文章特征词权重
	 * 未规范化，不定长;长度为提取百分比 ：EXTRACT_PERCENT
	 */
	public TDArticleTerm buildArticleVector(TDArticle article){
		Map<String, Double> articleVector=new HashMap<String,Double>();
		
		
		return null;
	}
}
