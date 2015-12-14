package pomonitor.test;

import org.junit.Test;

import pomonitor.clawer.NewsCrawler;
import pomonitor.clawer.newsanalyse.*;

public class TestNewsCrawler {
	@Test
	public void testClawlerAll() {
		SinaAnalyse analyse = new SinaAnalyse("sina", true);
		RedNetAnalyse redAnalyse = new RedNetAnalyse("rednet", true);
		RedNetAnalyseF redAnalysef = new RedNetAnalyseF("rednetforum", true);
		ZhongXinAnalyse zxAnalyse = new ZhongXinAnalyse("zhongxin", true);
		NewsCrawler clawer = new NewsCrawler("E:/test2/");
		HuaShengAnalyse hsAnalyse = new HuaShengAnalyse("husheng", true);
		GuangMing gmAnalyse = new GuangMing("guangming", true);
		WeiXinAnalyse weixinAnalyse = new WeiXinAnalyse("weixin", true);
		XinHuaAnalyse xhAnalyse = new XinHuaAnalyse("xinhua", true);
		// clawer.addAnalyse(analyse);
		// clawer.addAnalyse(redAnalysef);
		// clawer.addAnalyse(redAnalyse);
		// clawer.addAnalyse(zxAnalyse);
		// clawer.addAnalyse(gmAnalyse);
		clawer.addAnalyse(hsAnalyse);
		// clawer.addAnalyse(weixinAnalyse);
		// clawer.addAnalyse(xhAnalyse);
		clawer.clawerAll("南华大学", false);
		clawer.start(10);

	}
}
