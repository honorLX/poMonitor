package pomonitor.test;

import java.util.List;

import pomonitor.analyse.TopicDiscoveryAnalyse;
import pomonitor.analyse.entity.TDArticle;
import pomonitor.analyse.entity.TDCentroid;
import pomonitor.analyse.topicdiscovery.KmeansCluster;
import pomonitor.analyse.topicdiscovery.TextVectorBuilder;
import pomonitor.analyse.topicdiscovery.TopicDiscovery;

public class TestTopicDiscovery {
	public static void main(String[] args) {
		TopicDiscoveryAnalyse tda = new TopicDiscoveryAnalyse();
		List<TDArticle> lists = tda.getArticlesBetweenDate("2012-09-10",
				"2013-02-10");
		TextVectorBuilder tvb = new TextVectorBuilder();

		lists = tvb.buildVectors(lists);
		List<String> baseStr = tvb.globalFeatureCollections;
		List<TDCentroid> resCluster = KmeansCluster.ArticleCluster(15, lists);
		//TopicDiscovery td = new TopicDiscovery();
		System.out
				.println("全局的特征项集合大小是:" + tvb.globalFeatureCollections.size());
		for (TDCentroid tdCentroid : resCluster) {/*
												 * List<Topic> topicList =
												 * td.getTopicFromCentroid
												 * (tdCentroid,
												 * tvb.globalFeatureCollections);
												 * Collections.sort(topicList,
												 * new Comparator<Topic>() {
												 * 
												 * @Override
												 * public int compare(Topic o1,
												 * Topic o2) {
												 * if (o1.weight > o2.weight)
												 * return -1;
												 * if (o1.weight < o2.weight)
												 * return 1;
												 * return 0;
												 * }
												 * });
												 * for (int i = 0; i <
												 * topicList.size(); i++) {
												 * System.out.println(topicList.get
												 * (i).getContent() + "-->"
												 * +
												 * topicList.get(i).getWeight()
												 * );
												 * }
												 * System.out.println(
												 * "***************************************"
												 * );
												 */
			double[] _vec = tdCentroid.GroupedArticle.get(0).vectorSpace;
			double _maxVar = getMax(_vec);
			for (int i = 0; i < _vec.length; i++) {
				if (_vec[i] > 0.1 * _maxVar) {
					System.out.println(baseStr.get(i) + "-->" + _vec[i]);
				}
			}
			System.out.println("**********************");
		}
	}

	private static double getMax(double[] arr) {
		double maxVar = arr[0];
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] > maxVar)
				maxVar = arr[i];
		}
		return maxVar;
	}

	public static String vectorToString(double[] arr) {
		String str = "";
		for (double d : arr) {
			str += d + "->";
		}
		return str;
	}
}
