package pomonitor.test;

import java.util.ArrayList;
import java.util.List;

import pomonitor.entity.EntityManagerHelper;
import pomonitor.entity.Sensword;
import pomonitor.entity.SenswordDAO;
import pomonitor.entity.User;
import pomonitor.entity.UserDAO;

public class TestUserAndSensword {

	public static void main(String[] args) {
		UserDAO ud = new UserDAO();
		User root = ud.findById(1);

		// 添加敏感词
		String[] list_A = new String[] { "贪腐", "南华大学", "黑幕", "跳楼", "压迫", "反抗",
				"喜讯", "获奖" };
		String[] list_B = new String[] { "垃圾", "脏", "乱", "差", "坑" };
		String[] list_C = new String[] { "红包", "女学生" };
		List<String[]> lists = new ArrayList<String[]>();
		lists.add(list_A);
		lists.add(list_B);
		lists.add(list_C);
		SenswordDAO sd = new SenswordDAO();
		EntityManagerHelper.beginTransaction();

		for (int i = 0; i < 3; i++) {
			for (String string : lists.get(i)) {
//				Sensword sens = new Sensword(root, (3-i)+"", string);
//				sd.save(sens);
			}
		}
		EntityManagerHelper.commit();
	}
}
