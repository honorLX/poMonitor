package pomonitor.analyse.segment;

import java.util.List;

import com.hankcs.hanlp.seg.common.Term;

/**
 * 测试分词和去杂效果
 * 
 * @author luoxu 2015年12月15日 下午3:07:41
 */
public class Test {

	public static void main(String[] args) {
		String text = ReadData.getContentFromText();
		GenerateTerms generateTerms = new GenerateTerms();
		List<Term> termList = generateTerms.getTerms(text);
		for (Term term : termList)
			System.out.println(term.word);
		// System.out.println(termList);
	}

}
