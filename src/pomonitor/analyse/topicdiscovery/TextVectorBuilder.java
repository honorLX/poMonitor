package pomonitor.analyse.topicdiscovery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import pomonitor.analyse.entity.TDArticle;
import pomonitor.analyse.entity.TDArticleTerm;
import pomonitor.analyse.entity.TDPosition;

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
	// body词的权重系数
	private final double BODY_WEIGHT=1;
	// meta词的权重系数
	private final double META_WEIGHT=3;
	// 总的特征集合
	List<String> globalFeatureCollections = null;
	//含有此词汇文档的频数
	Map<String, Double> globalDocumentFrequency=null;
	/**
	 * 根据新闻文本集合和指定的提取百分比，获得有效的特征项集合，缩短向量长度
	 * 
	 * @param topicDisArticleList
	 * @param percentage
	 * @return 总特征集合，其长度即为向量长度
	 */
	private List<String> getFeatureSet(
			List<TDArticle> topicDisArticleList, double percentage) {
		//降序排序每篇文章的权值
		for(TDArticle article :topicDisArticleList){
			List<TDArticleTerm> allTerms=article.getArticleAllTerms();
			Collections.sort(allTerms,new Comparator<TDArticleTerm>(){
				public int compare(TDArticleTerm o1, TDArticleTerm o2) {
					double o1w=o1.getweight(),o2w=o2.getweight();
					if(o1w>o2w)return -1;
					if(o1w<o2w)return 1;
					return 0;
				}
			});
			//提取指定比例到全局特征向量中
			int extractsize= (int) (allTerms.size()*EXTRACT_PERCENT);
			globalFeatureCollections=new ArrayList<String>();
			for(int i=0;i<extractsize;i++){
				globalFeatureCollections.add(allTerms.get(i).getvalue());
			}
		}
		//去重
		globalFeatureCollections= new ArrayList<String>(new HashSet<String>(globalFeatureCollections));

		return globalFeatureCollections;
	}

	/**
	 * 根据总的特征集合和新闻文本对象集合，生成向量集合
	 * 
	 * @param topicDisArticleList
	 * @param globalFeatureCollections
	 * @return
	 */
	public List<TDArticle> buildVectors(
			List<TDArticle> topicDisArticleList) {
		//计算globalDocumentFrequency
		globalDocumentFrequency=new HashMap<String, Double>();
		for(TDArticle article :topicDisArticleList){
			for(TDArticleTerm term:article.getArticleAllTerms()){
				String termValue=term.getvalue();
				if(!globalDocumentFrequency.containsKey(termValue)){
					globalDocumentFrequency.put(termValue, (double) 0);
				} 
				globalDocumentFrequency.put(termValue,globalDocumentFrequency.get(termValue)+1);
			}
		}
		//调用buildArticleVector，生成每篇文章所有词的权重
		for(TDArticle article :topicDisArticleList){
			buildArticleVector(article);
		}
		//计算globalFeatureCollections 全局特征向量
		globalFeatureCollections = getFeatureSet(topicDisArticleList,
				EXTRACT_PERCENT);
		return topicDisArticleList;
	}
	
	/**
	 * 计算一篇文章中所有词项的tf-idf(局部)
	 * @param TDArticle
	 * @return TDArticle
	 */
	public TDArticle buildArticleVector(TDArticle article){
		//所有文章总数
		int sumArticle=topicDisArticleList.size();   
		//文章单词放入map中
		Map<String, Double> tf=new HashMap<String,Double>(); 
		//这篇文章单词总数
		int sumTerm=article.getArticleAllTerms().size();  
		//计算词频值
		for(TDArticleTerm term:article.getArticleAllTerms()){ 
			String termValue=term.getvalue();
			if(!tf.containsKey(termValue)){
				tf.put(termValue, (double) 0);
			} 
			if(term.getposition()==TDPosition.META){
				tf.put(termValue,tf.get(termValue)+META_WEIGHT);
			}
			else if(term.getposition()==TDPosition.BODY){
				tf.put(termValue,tf.get(termValue)+BODY_WEIGHT);
			}
		}
		//得到tf值
		for(Map.Entry<String, Double> tfentry : tf.entrySet()){
			tfentry.setValue(tfentry.getValue()/sumArticle);
		}
		
		//给每个单词赋相应weight,并且去重
		List<TDArticleTerm> articleAllTerms=article.getArticleAllTerms();
		Iterator<TDArticleTerm> iter = articleAllTerms.iterator();  
		while(iter.hasNext()){
			TDArticleTerm term=iter.next();
			String termValue=term.getvalue();
			//去重
			if(!tf.containsKey(termValue))
				iter.remove();
			else{
				double idf=Math.log(sumArticle/globalDocumentFrequency.get(termValue)+0.01);
				term.setweight(tf.get(termValue)*(idf));  
				tf.remove(termValue);
			}
		}
		return article;
	}
}
