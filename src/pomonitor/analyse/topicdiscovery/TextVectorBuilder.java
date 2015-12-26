package pomonitor.analyse.topicdiscovery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
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
	private List<TDArticle> globalArticleList;
	// 提取百分比
	private final double EXTRACT_PERCENT = 0.1;
	// body词的权重系数
	private final double BODY_WEIGHT = 1;
	// meta词的权重系数
	private final double META_WEIGHT = 3;
	// 总的特征集合
	public List<String> globalFeatureCollections = new ArrayList<String>();

	/**
	 * 根据总的特征集合和新闻文本对象集合，生成向量集合
	 * 
	 * @param topicDisArticleList
	 * @param globalFeatureCollections
	 * @return
	 */
	public List<TDArticle> buildVectors(List<TDArticle> topicDisArticleList) {
		globalArticleList = topicDisArticleList;
		// 生成每篇文章所有词项的权重信息
		for (TDArticle article : globalArticleList) {
			Map<String, Double> map = new HashMap<String, Double>();
			for (TDArticleTerm _term : article.getArticleAllTerms()) {
				map.put(_term.getvalue(), getWeight(article, _term));
			}
			article.setTermsWeights(map);
		}
		// 计算globalFeatureCollections 全局 特征词项集合
		globalFeatureCollections = getFeatureSet();
		// 计算每篇文本的向量模型
		for (TDArticle tdArticle : globalArticleList) {
			tdArticle = buildArticleVector(tdArticle);
		}
		return globalArticleList;
	}

	/**
	 * 计算该篇文本的向量模型
	 * 
	 * @param TDArticle
	 * @return TDArticle
	 */

	private TDArticle buildArticleVector(TDArticle article) {
		TDArticle resTdArticle = article;
		double[] vec = new double[globalFeatureCollections.size()];
		for (int i = 0; i < vec.length; i++) {
			if (article.getTermsWeights().containsKey(
					globalFeatureCollections.get(i)))
				vec[i] = article.getTermsWeights().get(
						globalFeatureCollections.get(i));
			else
				vec[i] = 0;
		}
		resTdArticle.vectorSpace = vec;
		return resTdArticle;
	}

	/**
	 * 计算某个词项的权重值，该权重值与下列值有关 :
	 * 1.该词项本身 (term)
	 * 2.包含该词项的文章 (article)
	 * 3.所有文章集合 (globalArticleList)
	 * 
	 * 该方法需要完善
	 * 
	 * @param article
	 * @param term
	 * @return
	 */
	private double getWeight(TDArticle article, TDArticleTerm term) {
		
		return findTFIDF(article, term);
	}

	/**
	 * 计算某个词的 tf-idf 值
	 * 
	 * @param article
	 * @param term
	 * @return
	 */
	private double findTFIDF(TDArticle article, TDArticleTerm term) {
		double tf = findTermFrequency(article, term.getvalue());
		double idf = findInverseDocumentFrequency(term.getvalue());
		return tf * idf;
	}

	/**
	 * 计算某个词在某篇文章中的 tf
	 * 
	 * @param article
	 * @param term
	 * @return
	 */
	private double findTermFrequency(TDArticle article, String term) {
		double termCount = 0;
		for (TDArticleTerm _term : article.getArticleAllTerms()) {
			if (term.equals(_term.getvalue()))
				termCount++;
		}
		return termCount / article.getArticleAllTerms().size();
	}

	/**
	 * 计算某个词的 idf
	 * 
	 * @param term
	 * @return
	 */
	private double findInverseDocumentFrequency(String term) {
		double count = 0;
		for (TDArticle article : globalArticleList) {
			if (article.getArticleAllTerms().contains(term))
				count++;
		}
		return Math.log((globalArticleList.size()) / (count + 0.001));
	}

	/**
	 * 根据新闻文本集合和指定的提取百分比，获得有效的特征项集合，缩短向量长度
	 * 
	 * @param topicDisArticleList
	 * @param percentage
	 * @return 总特征集合，其长度即为向量长度
	 */
	private List<String> getFeatureSet() {
		List<String> tmpGlobalFeatureCollections = new ArrayList<String>();

		for (TDArticle article : globalArticleList) {
			ArrayList<Map.Entry<String, Double>> descSortedList = DescSort(article
					.getTermsWeights());
			// 提取指定比例到全局特征向量中
			int extractsize = (int) (article.getTermsWeights().size() * EXTRACT_PERCENT);
			for (int i = 0; i < extractsize; i++) {
				tmpGlobalFeatureCollections.add(descSortedList.get(i).getKey());
			}
		}
		// 去重
		globalFeatureCollections = new ArrayList<String>(new HashSet<String>(
				tmpGlobalFeatureCollections));

		return globalFeatureCollections;
	}

	/**
	 * 将 Map 按照 value 降序排列，返回 key 的 ArrayList
	 * 
	 * @param map
	 * @return
	 */
	private ArrayList<Map.Entry<String, Double>> DescSort(
			Map<String, Double> map) {
		ArrayList<Map.Entry<String, Double>> mapList = new ArrayList<Map.Entry<String, Double>>(
				map.entrySet());
		Collections.sort(mapList, new Comparator<Map.Entry<String, Double>>() {
			@Override
			public int compare(Map.Entry<String, Double> o1,
					Map.Entry<String, Double> o2) {
				// 根据value降序排序
				if ((o2.getValue() - o1.getValue()) < 0)
					return -1;
				else if ((o2.getValue() - o1.getValue()) > 0)
					return 1;
				else
					return 0;
			}
		});
		return mapList;
	}
}
