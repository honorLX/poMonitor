package pomonitor.analyse.topicdiscovery;

import java.util.List;
import java.util.Map;

import pomonitor.analyse.entity.TDArticle;
import pomonitor.analyse.entity.Topic;
import pomonitor.entity.Sensword;

/**
 * 话题发现
 * 
 * @author caihengyi 2015年12月14日 下午9:33:43
 */
public class TopicDiscovery {
	// 各个新闻文本向量
	private List<Map<String, Double>> mArticleVectors;

	// 聚类之后的结果集合
	private List<List<Map<String, Double>>> mArticleClusterResults;

	// 对结果集合处理之后的话题集合
	private List<Topic> mTopics;

	// 根据新闻文本集合和用户的敏感词库，提取话题
	public List<Topic> getTopics(List<TDArticle> articleLists,
			List<Sensword> sensitiveDict) {
		TextVectorBuilder tvb = new TextVectorBuilder();
		List<TDArticle> tdArticlesWithVector = tvb.buildVectors(articleLists);
		// 对文本向量进行聚类

		// 对聚类结果进行处理

		// 对话题结果按照 敏感词库 再次进行加权

		return mTopics;
	}

}
