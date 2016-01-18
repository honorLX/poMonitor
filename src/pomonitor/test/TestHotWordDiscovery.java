package pomonitor.test;

import java.util.List;
import java.util.Map;

import pomonitor.analyse.HotWordDiscoveryAnalyse;
import pomonitor.analyse.entity.TDArticle;
import pomonitor.analyse.entity.TDCentroid;
import pomonitor.analyse.hotworddiscovery.HotWordDiscovery;
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
		List<TDArticle> lists = tda.getArticlesBetweenDate("2013-09-10",
				"2014-7-10");
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

		HotWordDiscovery hotWordDiscovery = new HotWordDiscovery();
		HotWordDiscoveryAnalyse hwAnalyse=new HotWordDiscoveryAnalyse();
		//测试向量
		for(String s:tvb.globalFeatureCollections)
			System.out.print(s+" ");
		System.out.println("");
		
//		for(TDArticle t:tvb.globalArticleList){
//			for(Map.Entry<String, Double> m:t.getTermsWeights().entrySet())
//				System.out.print(m.getKey()+" "+m.getValue()+"  ");
//			System.out.println("");
//			//for(int i=0;i<t.vectorSpace.length;i++){
//				//System.out.print(t.vectorSpace[i]+" ");
//			//}
//			System.out.println("\n\n");
//		}		
		
			
				
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
