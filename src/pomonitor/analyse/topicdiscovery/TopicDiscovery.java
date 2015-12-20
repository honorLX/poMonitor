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
	private List<Map<String, Double>> _articleVectors;

	// 聚类之后的结果集合
	private List<List<Map<String, Double>>> _articleClusterResults;

	// 对结果集合处理之后的话题集合
	private List<Topic> _topics;

	// 根据新闻文本集合和用户的敏感词库，提取话题
	public List<Topic> getTopics(List<TDArticle> articleLists,
			List<Sensword> sensitiveDict) {

		return null;
	}
}
