package pomonitor.analyse;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

import pomonitor.analyse.articletend.ArticleTendAnalyseRealize;
import pomonitor.analyse.entity.TendAnalyseArticle;
import pomonitor.entity.EntityManagerHelper;
import pomonitor.entity.News;
import pomonitor.entity.NewsDAO;
import pomonitor.entity.NewsTend;
import pomonitor.entity.NewsTendDAO;

/**
 * 
 * @author xiaoyulun 2016年1月5日 上午11:44:52
 */
public class ArticleTendAnalyse {

    public static void tendAnalyse(String start_time, String end_time,
	    String UserId) {
	NewsDAO newsDAO = new NewsDAO();
	List<News> newsList = newsDAO.findBetweenDate(start_time, end_time);
	HashMap<String, Float> hashMap = new HashMap<>();
	ArticleTendAnalyseRealize analyseRealize = new ArticleTendAnalyseRealize();
	for (News news : newsList) {
	    if (news.getIsFinsh() == 0) {
		continue;
	    }
	    TendAnalyseArticle tendArticle = new TendAnalyseArticle();

	    try {
		tendArticle = analyseRealize.analyseArticleTend(news);
	    } catch (Exception e) {
		// TODO: handle exception
		System.out.println("文本过长");
		continue;
	    }
	    String webName = tendArticle.getWeb();
	    Float score = tendArticle.getTendScore();
	    InsertNewsTend(news, newsDAO, score);
	}
    }

    /**
     * 从newstend表中返回数据 showWebTend
     * 
     * @param start_time
     * @param end_time
     * @param UserId
     * @return
     */
    public static HashMap<String, Float> showWebTend(String start_time,
	    String end_time, String UserId) {
	NewsTendDAO newsTendDAO = new NewsTendDAO();
	List<NewsTend> newsList = newsTendDAO.findBetweenDate(start_time,
		end_time);
	HashMap<String, Float> hashMap = new HashMap<>();
	for (NewsTend news : newsList) {
	    String webName = news.getWeb();
	    Float score = news.getTendscore();

	    if (hashMap.get(webName) == null) {
		hashMap.put(webName, score);
	    } else {
		hashMap.put(webName, hashMap.get(webName) + score);
	    }
	}
	return hashMap;
    }

    /**
     * 将计算完后的新闻，插入newstend表中,并更改news的IsFinished字段 InsertNewsTend
     * 
     * @param news
     * @param newsDAO
     * @param tendScore
     */
    private static void InsertNewsTend(News news, NewsDAO newsDAO,
	    Float tendScore) {
	// news.setIsFinsh(0);
	NewsTend newsTend = new NewsTend();
	newsTend.setDate(news.getTime());
	try {
	    newsTend.setWeb(new String(gbk2utf8(news.getWeb()), "utf-8"));
	    System.out.println(newsTend.getWeb()
		    + "*****************************88888");
	} catch (UnsupportedEncodingException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}
	newsTend.setTendscore(tendScore);
	newsTend.setNewsId(news.getRelId());
	newsTend.setTendclass(0);
	NewsTendDAO newsTendDAO = new NewsTendDAO();
	try {
	    EntityManagerHelper.beginTransaction();
	    newsTendDAO.save(newsTend);
	    EntityManagerHelper.commit();

	    // EntityManagerHelper.beginTransaction();
	    // newsDAO.update(news);
	    // EntityManagerHelper.commit();

	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    public static byte[] gbk2utf8(String chenese) {

	// Step 1: 得到GBK编码下的字符数组，一个中文字符对应这里的一个c[i]
	char c[] = chenese.toCharArray();

	// Step 2: UTF-8使用3个字节存放一个中文字符，所以长度必须为字符的3倍
	byte[] fullByte = new byte[3 * c.length];

	// Step 3: 循环将字符的GBK编码转换成UTF-8编码
	for (int i = 0; i < c.length; i++) {

	    // Step 3-1：将字符的ASCII编码转换成2进制值
	    int m = (int) c[i];
	    String word = Integer.toBinaryString(m);
	    // System.out.println(word);

	    // Step 3-2：将2进制值补足16位(2个字节的长度)
	    StringBuffer sb = new StringBuffer();
	    int len = 16 - word.length();
	    for (int j = 0; j < len; j++) {
		sb.append("0");
	    }
	    // Step 3-3：得到该字符最终的2进制GBK编码
	    // 形似：1000 0010 0111 1010
	    sb.append(word);

	    // Step 3-4：最关键的步骤，根据UTF-8的汉字编码规则，首字节
	    // 以1110开头，次字节以10开头，第3字节以10开头。在原始的2进制
	    // 字符串中插入标志位。最终的长度从16--->16+3+2+2=24。
	    sb.insert(0, "1110");
	    sb.insert(8, "10");
	    sb.insert(16, "10");
	    // System.out.println(sb.toString());

	    // Step 3-5：将新的字符串进行分段截取，截为3个字节
	    String s1 = sb.substring(0, 8);
	    String s2 = sb.substring(8, 16);
	    String s3 = sb.substring(16);

	    // Step 3-6：最后的步骤，把代表3个字节的字符串按2进制的方式
	    // 进行转换，变成2进制的整数，再转换成16进制值
	    byte b0 = Integer.valueOf(s1, 2).byteValue();
	    byte b1 = Integer.valueOf(s2, 2).byteValue();
	    byte b2 = Integer.valueOf(s3, 2).byteValue();

	    // Step 3-7：把转换后的3个字节按顺序存放到字节数组的对应位置
	    byte[] bf = new byte[3];
	    bf[0] = b0;
	    bf[1] = b1;
	    bf[2] = b2;

	    fullByte[i * 3] = bf[0];
	    fullByte[i * 3 + 1] = bf[1];
	    fullByte[i * 3 + 2] = bf[2];

	    // Step 3-8：返回继续解析下一个中文字符
	}
	return fullByte;
    }
}
