package pomonitor.analyse.segment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.CRF.CRFSegment;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;

/**
 * 
 * @author luoxu 2015年12月15日 下午1:44:49 分词，得到term term由 单词名称，词性构成。
 */
public class GenerateTerms {
	/*
	 * 生成Term
	 */
	public List<Term> getTerms(String Content) {
		Segment segment = new CRFSegment();
		KeywordExtractor keywordExtractor = new KeywordExtractor();

		segment.enablePartOfSpeechTagging(true);
		segment.enableAllNamedEntityRecognize(true);
		List<Term> termList = segment.seg(Content);

		termList = keywordExtractor.wipeoffWords(termList);
		return termList;
	}
}
