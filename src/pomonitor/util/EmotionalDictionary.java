package pomonitor.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.persistence.internal.jpa.metadata.structures.ArrayAccessor;

import pomonitor.entity.Emotionalword;
import pomonitor.entity.EmotionalwordDAO;

/**
 * 
 * @author xiaoyulun 2015年12月14日 下午3:26:33
 */
public class EmotionalDictionary {
	private HashMap<String, Emotionalword> hashMap;
	private EmotionalwordDAO emotionalwordDAO;

	public EmotionalDictionary() {
		hashMap = new HashMap<String, Emotionalword>();
		emotionalwordDAO = new EmotionalwordDAO();
	}

	// 初始化情感词字典
	public void initDictionary() {
		List<Emotionalword> list = emotionalwordDAO.findAll();
		for (Emotionalword emotionalword : list) {
			hashMap.put(emotionalword.getWord(), emotionalword);
		}
	}

	// 获取情感词类
	public Emotionalword getWord(String word) {
		return hashMap.get(word);
	}

}
