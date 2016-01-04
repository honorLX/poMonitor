package pomonitor.analyse.topicdiscovery;

import java.util.ArrayList;
import java.util.List;

import pomonitor.analyse.entity.ArticleShow;
import pomonitor.analyse.entity.Attitude;
import pomonitor.analyse.entity.TDArticle;
import pomonitor.analyse.entity.TDCentroid;
import pomonitor.analyse.entity.Topic;
import pomonitor.entity.Sensword;

/**
 * 话题发现
 * 
 * @author caihengyi 2015年12月14日 下午9:33:43
 */
public class TopicDiscovery {
	private List<String> mBaseStrings;
	private final int k = 13;

	// 根据新闻文本集合和用户的敏感词库，提取话题
	public List<Topic> getTopics(List<TDArticle> articleLists,
			List<Sensword> sensitiveDict) {
		TextVectorBuilder tvb = new TextVectorBuilder();
		List<TDArticle> tdArticlesWithVector = tvb.buildVectors(articleLists);
		mBaseStrings = tvb.globalFeatureCollections;
		// 对文本向量进行聚类
		List<TDCentroid> resTDCentroid = KmeansCluster.ArticleCluster(k,
				tdArticlesWithVector);
		// 对聚类结果进行处理
		List<Topic> topics=new ArrayList<Topic>();
		for (TDCentroid tdCentroid : resTDCentroid){
			Topic tp=new Topic();
			String content="";
			double[] _vec = tdCentroid.GroupedArticle.get(0).vectorSpace;
			double _maxVar = getMax(_vec);
			for (int i = 0; i < _vec.length; i++) {
				if (_vec[i] > 0.1 * _maxVar) {
					content+=mBaseStrings.get(i)+" ";
				}
			}
			tp.setContent(content);
			topics.add(tp);
		}
		// 对话题结果按照 敏感词库 再次进行加权

		return topics;
	}

	public List<Topic> getTopicFromCentroid(TDCentroid tdc,
			List<String> baseSring) {
		List<Topic> topicList = new ArrayList<Topic>(baseSring.size());
		for (int i = 0; i < baseSring.size(); i++) {
			Topic t = new Topic();
			t.articleViews = new ArrayList<>();
			t.weight = 0.0;
			t.setAttitude(Attitude.NEUTRAL);
			t.setContent("");
			t.setSensitiveWords(false);
			topicList.add(t);
		}
		double[] base = tdc.GroupedArticle.get(0).vectorSpace;
		for (int i = 1; i < tdc.GroupedArticle.size(); i++) {
			ArticleShow as = new ArticleShow();
			/****** 每条向量代表一篇文章(ArticleShow 的倾向性属性在这里没有设置) *****/
			as.setComeFrom(tdc.GroupedArticle.get(i).getComeFrom());
			as.setDescription(tdc.GroupedArticle.get(i).getDescription());
			as.setTimestamp(tdc.GroupedArticle.get(i).getTimestamp());
			as.setTitle(tdc.GroupedArticle.get(i).getTitle());
			as.setUrl(tdc.GroupedArticle.get(i).getUrl());
			/*****************************************************************/
			for (int j = 0; j < base.length; j++) {
				if (tdc.GroupedArticle.get(i).vectorSpace[j] > base[j])
					as.heat += tdc.GroupedArticle.get(i).vectorSpace[j];
			}
			for (int j = 0; j < base.length; j++) {
				if (tdc.GroupedArticle.get(i).vectorSpace[j] > base[j]) {
					topicList.get(j).articleViews.add(as);
					topicList.get(j).weight += tdc.GroupedArticle.get(i).vectorSpace[j];
					topicList.get(j).setContent(baseSring.get(j));
					// 该话题是否是敏感词，褒贬信息，没有设置
				}
			}

		}
		return topicList;
	}
	
	
	
	public static double getMax(double[] arr) {
		double maxVar = arr[0];
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] > maxVar)
				maxVar = arr[i];
		}
		return maxVar;
	}
	public static double getMin(double[] arr) {
		double minVar = arr[0];
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] < minVar)
				minVar = arr[i];
		}
		return minVar;
	}
	public static String vectorToString(double[] arr) {
		String str = "";
		for (double d : arr) {
			str += d + "->";
		}
		return str;
	}
}
