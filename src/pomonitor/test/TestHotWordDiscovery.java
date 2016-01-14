package pomonitor.test;

import java.util.List;

import pomonitor.analyse.HotWordDiscoveryAnalyse;
import pomonitor.analyse.entity.TDArticle;
import pomonitor.analyse.entity.TDCentroid;
import pomonitor.analyse.hotworddiscovery.KmeansCluster;
import pomonitor.analyse.hotworddiscovery.TextVectorBuilder;
import pomonitor.util.ConsoleLog;

/**
 * 测试热词发现的相关功能
 * 
 * @author hengyi
 * 
 */
public class TestHotWordDiscovery {
	public static void main(String[] args) {
		HotWordDiscoveryAnalyse tda = new HotWordDiscoveryAnalyse();
		List<TDArticle> lists = tda.getArticlesBetweenDate("2008-09-10",
				"2009-10-10");
		TextVectorBuilder tvb = new TextVectorBuilder();

		long start = System.currentTimeMillis();
		lists = tvb.buildVectors(lists);
		long end = System.currentTimeMillis();
		ConsoleLog.PrintInfo(TestHotWordDiscovery.class, "构建文本向量的花费时间为"
				+ (end - start) + "毫秒");
		List<String> baseStr = tvb.globalFeatureCollections;
		long startClus = System.currentTimeMillis();
		List<TDCentroid> resCluster = KmeansCluster.ArticleCluster(15, lists);
		long endClus = System.currentTimeMillis();
		ConsoleLog.PrintInfo(TestHotWordDiscovery.class, "聚类花费的时间为"
				+ (endClus - startClus) + "毫秒");
		// TopicDiscovery td = new TopicDiscovery();
		System.out
				.println("全局的特征项集合大小是:" + tvb.globalFeatureCollections.size());
		/*
		 * // 输出文本向量
		 * for (TDArticle _tda : lists) {
		 * System.out.println(vectorToString(_tda.vectorSpace));
		 * }
		 */
		// 输出聚类文本
		/*
		 * for (TDCentroid tdCentroid : resCluster) {
		 * for (TDArticle _tda : tdCentroid.GroupedArticle) {
		 * System.out.println(_tda.getTitle() + "###"
		 * + _tda.getDescription());
		 * }
		 * System.out
		 * .println("#####################类间分隔符##########################");
		 * System.out.println();
		 * System.out.println();
		 * }
		 */

		// 输出热词结果
		for (TDCentroid tdCentroid : resCluster) {
			double[] _vec = tdCentroid.GroupedArticle.get(0).vectorSpace;
			double _maxVar = getMax(_vec);
			for (int i = 0; i < _vec.length; i++) {
				if (_vec[i] > 0.3 * _maxVar) {
					System.out.println(baseStr.get(i) + "-->" + _vec[i] * 100);
				}
			}
			System.out.println("**********************");
		}

	}

	/**
	 * 求得一个数组的最大值
	 * 
	 * @param arr
	 * @return
	 */
	private static double getMax(double[] arr) {
		double maxVar = arr[0];
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] > maxVar)
				maxVar = arr[i];
		}
		return maxVar;
	}

	/**
	 * 将数组以字符串的形式表示
	 * 
	 * @param arr
	 * @return
	 */
	public static String vectorToString(double[] arr) {
		String str = "";
		for (double d : arr) {
			str += d + "->";
		}
		return str;
	}
}
