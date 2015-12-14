package pomonitor.analyse.articlesubanalyse;

import java.util.ArrayList;
import java.util.List;

import pomonitor.analyse.articletendanalyse.entity.Article;
import pomonitor.analyse.articletendanalyse.entity.Sentence;

/**
 * 获取主题句的类
 * @author Administrator
 *
 */

public class SubSentenceGet {
	
	
	public void addScoreAdder(ISubScoreAdd adder){
		adderList.add(adder);
	}
	
	public SubSentenceGet(){
		adderList=new ArrayList<ISubScoreAdd>();
	}
	
	public List<ISubScoreAdd> adderList;
	
	//计算文章每一个句子的主题分数
	public void countSubScore(Article article){
		for(Sentence sentence:article.getSentences()){
			for(ISubScoreAdd add:adderList){
				add.add(article, sentence);
			}
		}
	}
	
	
	
	//获得每一篇文章中能代表主题的前几个句子
	public void getSubSentence(Article article ,int outCount){
		List<Sentence> sentences =  article.getSentences();
		List<Sentence> subSentences =new ArrayList<Sentence>();
		int count = 0;
		int index;
		for(int i=0;i<sentences.size();i++){
			if(count>outCount){
				break;
			}
			count++;
			index=i;
			for(int j=i;j<sentences.size()-1;j++){
				if(sentences.get(j).getTendScore()>
				sentences.get(i).getTendScore()){
					index=j;
				}
				
			}
			subSentences.add(sentences.get(index));
		}
		article.setSubSentences(subSentences);
	}

}
