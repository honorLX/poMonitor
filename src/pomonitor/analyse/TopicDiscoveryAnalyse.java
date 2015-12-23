package pomonitor.analyse;

import java.util.Date;
import java.util.List;

import pomonitor.analyse.entity.Topic;

/**
 * 话题发现模块, 介于Controller与具体的话题发现模块之间;向上对controller服务; 向下调用话题发现具体分析包提供的功能
 * 
 * @author caihengyi 2015年12月15日 下午4:12:07
 */
public class TopicDiscoveryAnalyse {

	public List<Topic> DiscoverTopics(Date startDate, Date endDate, int userId) {
		// 根据起止时间获取数据库中的新闻文本

		// 作分词，过滤预处理

		// 调用话题发现功能模块，返回话题集合

		return null;
	}
}
