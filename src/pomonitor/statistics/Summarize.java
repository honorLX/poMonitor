package pomonitor.statistics;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityManager;

import com.alibaba.fastjson.JSON;

import pomonitor.entity.EntityManagerHelper;
import pomonitor.entity.NewsTend;
import pomonitor.entity.NewsTendDAO;

public class Summarize {
	public String getTendency(String startTime, String endTime)
			throws ParseException {
		NewsTendDAO newsTendDAO = new NewsTendDAO();
		List<NewsTend> newsTends = new ArrayList<>();
		EntityManagerHelper.beginTransaction();
		newsTends = newsTendDAO.findBetweenDate(startTime, endTime);
		EntityManagerHelper.commit();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date start = simpleDateFormat.parse(startTime);
		Date end = simpleDateFormat.parse(endTime);
		Calendar calendar = Calendar.getInstance();
		List<Date> dates = new ArrayList<>();
		HashMap<Date, Integer> allNews = new HashMap<>();
		HashMap<Date, Integer> negNews = new HashMap<>();
		while (start.getTime() <= end.getTime()) {
			allNews.put(start, 0);
			negNews.put(start, 0);
			dates.add(start);
			calendar.setTime(start);
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			start = calendar.getTime();
		}
		for (int i = 0; i < newsTends.size(); i++) {
			Date date = newsTends.get(i).getDate();
			int allNewsKey = allNews.get(date);
			allNewsKey++;
			allNews.put(date, allNewsKey);
			int tend = newsTends.get(i).getTendclass();
			if (tend < 0) {
				int negNewsKey = negNews.get(date);
				negNewsKey++;
				negNews.put(date, negNewsKey);
			}
		}
		Series series = new Series(dates, allNews, negNews);
		JSONData json = new JSONData(series);

		return JSON.toJSONString(json);
	}

	class Series {

		public Series(List<Date> dates, HashMap<Date, Integer> allNews,
				HashMap<Date, Integer> negNews) {
			all = new ArrayList<>();
			neg = new ArrayList<>();
			for (int i = 0; i < dates.size(); i++) {
				Date date = dates.get(i);
				all.add(allNews.get(date));
				neg.add(negNews.get(date));
			}
		}

		public ArrayList<Integer> neg;
		public ArrayList<Integer> all;
	}

	class JSONData {
		public Series series;
		public String message;
		public int status;

		public JSONData(Series series) {
			this.series = series;
			this.message = "Query success!";
			this.status = 0;
		}

	}
}
