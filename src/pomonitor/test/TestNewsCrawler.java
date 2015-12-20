package pomonitor.test;

import org.junit.Test;

import pomonitor.clawer.NewsCrawler;
import pomonitor.clawer.newsanalyse.*;

public class TestNewsCrawler {
	@Test
	public void testClawlerAll() {
		NewsCrawler clawer = new NewsCrawler("E:/test/");
		SinaAnalyse analyse = new SinaAnalyse("新浪", true);
		RedNetAnalyse redAnalyse = new RedNetAnalyse("红网", true);
		RedNetAnalyseF redAnalysef = new RedNetAnalyseF("红网论坛", false);
		ZhongXinAnalyse zxAnalyse = new ZhongXinAnalyse("中新网", true);

		HuaShengAnalyse hsAnalyse = new HuaShengAnalyse("华声在线", true);
		GuangMing gmAnalyse = new GuangMing("光明网", true);
		WeiXinAnalyse weixinAnalyse = new WeiXinAnalyse("微信", true);
		XinHuaAnalyse xhAnalyse = new XinHuaAnalyse("新华网", true);
		WangYi wangyi = new WangYi("网易", true);
		TengXunAnalyse tengXun = new TengXunAnalyse("腾讯", true);
		FenghuangAnalyse fenghuang = new FenghuangAnalyse("凤凰", true);
		SouHuAnalyse souhu = new SouHuAnalyse("搜狐", true);
		clawer.addAnalyse(analyse);
		// clawer.addAnalyse(redAnalysef);
		// clawer.addAnalyse(redAnalyse);
		// 失败 clawer.addAnalyse(zxAnalyse);
		// clawer.addAnalyse(gmAnalyse);
		// clawer.addAnalyse(hsAnalyse);
		// 有错误 clawer.addAnalyse(weixinAnalyse);
		// clawer.addAnalyse(xhAnalyse);
		// clawer.addAnalyse(wangyi);
		// clawer.addAnalyse(fenghuang);
		// clawer.addAnalyse(souhu);
		// clawer.addAnalyse(tengXun);

		clawer.clawerAll("南华大学", false);
		clawer.start(10);

	}
}
