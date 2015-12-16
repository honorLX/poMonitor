package pomonitor.analyse.articletend;

import java.util.ArrayList;
import java.util.List;

import pomonitor.analyse.entity.Sentence;
import pomonitor.analyse.entity.TendAnalyseArticle;
import pomonitor.analyse.entity.TendWord;
import pomonitor.entity.NewsEntity;

/**
 * �����µ�Ԥ���?���룬�ִʣ�����һ��������article����
 * @author zhaolong
 * 2015��12��16�� ����5:15:09
 */
public class ArticlePreAnalyse {
	
	private NewsEntity news;
	
	private TendAnalyseArticle article;
	
	private SentenceSplier sentenceSplier;
	
	//��������з���
	public ArticlePreAnalyse(SentenceSplier sentenceSplier) {
		this.sentenceSplier=new SentenceSplier();
	}
	
	//��������,��ʼ�������
	private void init(NewsEntity news){
		this.news=news;
		article=new TendAnalyseArticle();
		article.setKeyWords(news.getKeywords());
		article.setTitle(news.getTitle());
	}
	
	//ƪ�·־�,�����article�ľ�������
	private void splitArticle(){
		String content=news.getAllContent();
		String[] sentenceStrs=content.split("。");
		List<Sentence> relSentences=new ArrayList<Sentence>();
		for(int i=0;i<sentenceStrs.length;i++){
			//���Ӵ���5��ȥ����
			String sentenceStr=sentenceStrs[i].trim();
			if(sentenceStr.length()>5){
				Sentence sentence=new Sentence();
				sentence.setId(i);
				//�Ծ��ӽ��зִ�
				List<TendWord> list=sentenceSplier.spil(sentenceStr);
				sentence.setWords(list);
				relSentences.add(sentence);
			}
		}
		article.setSentences(relSentences);
	}
	
	/**
	 * �������ţ�����һ��Ԥ�����Ľṹ����TendAnalyseArticle����
	 * @param news
	 * @return
	 */
	public TendAnalyseArticle getPreArticle(NewsEntity news){
		init(news);
		splitArticle();
		return article;
	}

}
