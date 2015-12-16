package pomonitor.analyse.articletend;

import java.util.List;

import com.alibaba.fastjson.JSON;

import pomonitor.analyse.entity.TendWord;
import pomonitor.util.JsonContentGetter;
import pomonitor.util.SomeStaticValues;

/**
 * �����з���
 * @author zhaolong
 * 2015��12��16�� ����5:01:51
 */
public class SentenceSplier {
	/**
	 * 
	 * @param sentenc �ַ���ʽ��һ�仰
	 * @return List �ֺôʵ�һ�仰
	 */
	public List<TendWord> spil(String sentenc){
		String urlStr=""+SomeStaticValues.url+sentenc+"。";
		String jsonStr=JsonContentGetter.getJsonContent(urlStr);
		//��ȡ�ɿ��Խ���ת���ַ�
		jsonStr =jsonStr.substring(4, jsonStr.length()-3);
		//����ת��
		List<TendWord> list=JSON.parseArray(jsonStr, TendWord.class);
		for(TendWord tw:list){
			System.out.println("id:"+tw.getId());
			System.out.println("cont:"+tw.getCont());
			System.out.println("ne:"+tw.getNe());
			System.out.println("parent:"+tw.getParent());
			System.out.println("pos:"+tw.getPos());
		}
		return list;
	}
}
