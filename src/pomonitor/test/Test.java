package pomonitor.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import pomonitor.analyse.articletend.ArticleSplier;
import pomonitor.analyse.entity.TendWord;
import pomonitor.entity.EntityManagerHelper;
import pomonitor.entity.News;
import pomonitor.entity.NewsDAO;
import pomonitor.util.JsonContentGetter;
import pomonitor.util.SomeStaticValues;
import pomonitor.util.UrlSender;

public class Test {
	@org.junit.Test
	public void test() {
		NewsDAO nd = new NewsDAO();
		News news = new News();
		// news.setAllContent("jJJJJJJ");
		news.setId("!!!!!!!!!!");
		news.setTime(new Date());
		news.setKeyWords("DDDDDDDD");
		news.setWeb("web");
		news.setUrl("!!!!!!!");
		news.setTitle("!!!!!!!!!!");
		news.setContent("vvvvvv");
		EntityManagerHelper.beginTransaction();
		nd.save(news);
		EntityManagerHelper.commit();
	}

	@org.junit.Test
	public void testJsion() {
		// String
		// jsonStr=UrlSender.sendGet("http://api.ltp-cloud.com/analysis/?api_key=15k332P7iaVazlMV1ZFXUyqyoMmP7PVcgQICeDTc&text=%E6%88%91%E6%98%AF%E4%B8%AD%E5%9B%BD%E4%BA%BA%E3%80%82&pattern=all&format=json");
		String jsonStr = getJsonContent("http://api.ltp-cloud.com/analysis/?api_key=15k332P7iaVazlMV1ZFXUyqyoMmP7PVcgQICeDTc&text=%E6%88%91%E6%98%AF%E4%B8%AD%E5%9B%BD%E4%BA%BA%E3%80%82&pattern=all&format=json");
		System.out.println(jsonStr);
		jsonStr = jsonStr.substring(4, jsonStr.length() - 3);
		System.out.println("~~~~~" + jsonStr + "~~~");

		List<TendWord> list = JSON.parseArray(jsonStr, TendWord.class);

		// List list2=JSON.parseArray(jsonStr);

		System.out.println(list.get(1).getArg().get(1).getBeg());
		System.out.println(list.get(1).getArg().get(1).getEnd());

	}

	public static String getJsonContent(String urlStr) {
		try {// 获取HttpURLConnection连接对象
			URL url = new URL(urlStr);
			HttpURLConnection httpConn = (HttpURLConnection) url
					.openConnection();
			// 设置连接属性
			httpConn.setConnectTimeout(3000);
			httpConn.setDoInput(true);
			httpConn.setRequestMethod("GET");
			// 获取相应码
			int respCode = httpConn.getResponseCode();
			if (respCode == 200) {
				return ConvertStream2Json(httpConn.getInputStream());
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	private static String ConvertStream2Json(InputStream inputStream) {
		String jsonStr = "";
		// ByteArrayOutputStream相当于内存输出流
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		// 将输入流转移到内存输出流中
		try {
			while ((len = inputStream.read(buffer, 0, buffer.length)) != -1) {
				out.write(buffer, 0, len);
			}
			// 将内存流转换为字符串
			jsonStr = new String(out.toByteArray(), "utf-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonStr;
	}
	
	@org.junit.Test
	public  void testDate(){
		Date date=new Date();
		System.out.println(date.toGMTString());
		System.out.println(date.toLocaleString());
		System.out.println(date.toString());
	}
	
	@org.junit.Test
	public void testJson(){
		NewsDAO nd=new NewsDAO();
		News news=nd.findById(1);
		String url=SomeStaticValues.url;
		String content=news.getAllContent();
		try {
			content=URLEncoder.encode(content, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String jsonStrResult=JsonContentGetter.getJsonContent(url+content);
		System.out.println(jsonStrResult);
		JSONArray rootList=JSON.parseArray(jsonStrResult);
		
		
		JSONArray fatherList=rootList.getJSONArray(0);
		
		JSONArray thisList=fatherList.getJSONArray(0);
		System.out.println("当前jsonStr:"+thisList.toJSONString());
	}
	
	@org.junit.Test
	public void testArticleSple(){
		NewsDAO nd=new NewsDAO();
		News news=nd.findById(1);
		String content=news.getAllContent();
		ArticleSplier splier=new ArticleSplier();
		splier.spil(content);
	}

}
