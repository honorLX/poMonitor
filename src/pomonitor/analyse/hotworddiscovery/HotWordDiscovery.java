package pomonitor.analyse.hotworddiscovery;

import java.util.ArrayList;
import java.util.List;

import pomonitor.analyse.entity.ArticleShow;
import pomonitor.analyse.entity.Attitude;
import pomonitor.analyse.entity.TDArticle;
import pomonitor.analyse.entity.TDCentroid;
import pomonitor.analyse.entity.HotWord;
import pomonitor.entity.Emotionalword;
import pomonitor.entity.Sensword;
import pomonitor.entity.SenswordDAO;
import pomonitor.util.EmotionalDictionary;

/**
 * 话题发现
 * 
 * @author caihengyi 2015年12月14日 下午9:33:43
 */
public class HotWordDiscovery {
    private List<String> mBaseStrings;
    private final int k = 14;

    // 根据新闻文本集合和用户的敏感词库，提取热词
    public List<HotWord> getHotWords(List<TDArticle> articleLists,
	    List<Sensword> sensitiveDict) {
	TextVectorBuilder tvb = new TextVectorBuilder();
	List<TDArticle> tdArticlesWithVector = tvb.buildVectors(articleLists);
	mBaseStrings = tvb.globalFeatureCollections;
	// 对文本向量进行聚类
	List<TDCentroid> resTDCentroid = KmeansCluster.ArticleCluster(k,
		tdArticlesWithVector);
	// 对聚类结果进行处理得到HotWord
	List<List<HotWord>> hotWords =new ArrayList<List<HotWord>>();
	for (TDCentroid tdCentroid : resTDCentroid) {
		List<HotWord> t = getHotWordsFromCentroid(tdCentroid,sensitiveDict);
	    hotWords.add(t);
	}
	// 对话题结果按照 敏感词库 再次进行加权
	

	
	List<HotWord> sumHotWords=new ArrayList<HotWord>();
	return sumHotWords;
    }
    /**
     * 
     * @param tdc
     * @param sensitiveDict
     * @return
     */
    public List<HotWord> getHotWordsFromCentroid(TDCentroid tdc,
    		 List<Sensword> sensitiveDict) {
	List<HotWord> hotWordsList = new ArrayList<HotWord>(mBaseStrings.size());
	for (int i = 0; i < mBaseStrings.size(); i++) {
	    HotWord t = new HotWord();
	    t.articleViews = new ArrayList<>();
	    t.weight = 0.0;
	    t.setAttitude(Attitude.NEUTRAL);
	    t.setContent("");
	    t.setSensitiveWords(false);
	    hotWordsList.add(t);
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
		    hotWordsList.get(j).articleViews.add(as);
		    hotWordsList.get(j).weight += tdc.GroupedArticle.get(i).vectorSpace[j];
		    hotWordsList.get(j).setContent(mBaseStrings.get(j));
		    // 该话题是否是敏感词，褒贬信息，没有设置
			}
	    }
		}
		return hotWordsList;
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
