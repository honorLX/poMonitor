package pomonitor.test;

import pomonitor.entity.EntityManagerHelper;
import pomonitor.entity.News;
import pomonitor.entity.NewsDAO;

public class Test {
	@org.junit.Test
	public void test() {
		NewsDAO nd = new NewsDAO();
		News news = new News();
		// news.setAllContent("jJJJJJJ");
		news.setId("!!!!!!!!!!");
		news.setTime("1111");
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
	public void testJsion(){
		
	}

}
