package pomonitor.analyse.hotworddiscovery;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import pomonitor.analyse.entity.ArticleShow;
import pomonitor.analyse.entity.HotWord;
import pomonitor.util.ConsoleLog;
import pomonitor.util.PropertiesReader;

/**
 * 
 * @author luoxu
 *         2016年1月14日 下午4:34:21
 */
public class DistanceMatrix {
	private double[][] classDistMat;
	private double[][] overlapMat;
	private final double[][] globalTDCentroidDist;
	private final double sameClassVal;
	private final double classDistWeight;
	private final double overlapDistWeight;
	private List<HotWord> hotWords;

	public DistanceMatrix(List<HotWord> sumHotWords,
			double[][] globalTDCentroidDist) {
		PropertiesReader propertiesReader = new PropertiesReader();
		this.sameClassVal = Double.parseDouble(propertiesReader
				.getPropertyByName("SameClassVal"));
		this.classDistWeight = Double.parseDouble(propertiesReader
				.getPropertyByName("ClassDistWeight"));
		this.overlapDistWeight = Double.parseDouble(propertiesReader
				.getPropertyByName("OverlapDistWeight"));
		this.hotWords = sumHotWords;
		this.globalTDCentroidDist = globalTDCentroidDist;
	}

	public double[][] getRelevanceMat() {
		double[][] classDistMat = this.calClassDistMat();
		double[][] overlapMat = this.calOverlapMat();
		int len = hotWords.size();
		double[][] relevanceMat = new double[len][len];
		for (int i = 0; i < len; i++)
			for (int j = 0; j < len; j++) {
				relevanceMat[i][j] = classDistMat[i][j] * classDistWeight
						+ overlapMat[i][j] * overlapDistWeight;
			}
		/******************* 输出新闻同现率距离和类别距离矩阵 **********************/
		int alignWidth = 20;
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("E:\\RelevanceMat.txt", "UTF-8");
			writer.println("新闻同现率矩阵：");
			writer.println(ConsoleLog.StringMulti(
					ConsoleLog.AlignStrWithPlaceholder("*", "*", alignWidth),
					len));
			writer.print(ConsoleLog.AlignStrWithPlaceholder(" ", " ",
					alignWidth));
			for (int i = 0; i < len; i++) {
				writer.print(ConsoleLog.AlignHanZiWithPlaceholder(
						hotWords.get(i).getContent(), " ", alignWidth));
			}
			writer.println();
			for (int i = 0; i < len; i++) {
				writer.print(ConsoleLog.AlignHanZiWithPlaceholder(
						hotWords.get(i).getContent(), " ", alignWidth));
				for (int j = 0; j < len; j++) {
					writer.print(ConsoleLog.AlignStrWithPlaceholder(
							overlapMat[i][j] + "", " ", alignWidth));
				}
				writer.println();
			}
			writer.println(ConsoleLog.StringMulti(
					ConsoleLog.AlignStrWithPlaceholder("*", "*", alignWidth),
					len));

			writer.println("类别距离矩阵：");
			writer.println(ConsoleLog.StringMulti(
					ConsoleLog.AlignStrWithPlaceholder("*", "*", alignWidth),
					len));
			writer.print(ConsoleLog.AlignStrWithPlaceholder(" ", " ",
					alignWidth));
			for (int i = 0; i < len; i++) {
				writer.print(ConsoleLog.AlignHanZiWithPlaceholder(
						hotWords.get(i).getContent(), " ", alignWidth));
			}
			writer.println();
			for (int i = 0; i < len; i++) {
				writer.print(ConsoleLog.AlignHanZiWithPlaceholder(
						hotWords.get(i).getContent(), " ", alignWidth));
				for (int j = 0; j < len; j++) {
					writer.print(ConsoleLog.AlignStrWithPlaceholder(
							classDistMat[i][j] + "", " ", alignWidth));
				}
				writer.println();
			}
			writer.println(ConsoleLog.StringMulti(
					ConsoleLog.AlignStrWithPlaceholder("*", "*", alignWidth),
					len));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			writer.close();
		}
		/*********************************************************************/
		return relevanceMat;
	}

	/**
	 * 类别距离矩阵
	 * 
	 * @param globalTDCentroidDist
	 * @return
	 */
	private double[][] calClassDistMat() {
		int len = hotWords.size();
		classDistMat = new double[len][len];
		for (int i = 0; i < len; i++) {
			for (int j = 0; j < len; j++) {
				HotWord word1 = hotWords.get(i), word2 = hotWords.get(j);
				if (word1.getBelongto() != word2.getBelongto()) {
					classDistMat[i][j] = globalTDCentroidDist[word1
							.getBelongto()][word2.getBelongto()];
				} else
					classDistMat[i][j] = sameClassVal;
			}
		}
		// 归一化
		classDistMat = normalizeMat(classDistMat, len);
		return classDistMat;
	}

	/**
	 * 新闻同现率距离矩阵
	 * 
	 * @return
	 */
	private double[][] calOverlapMat() {
		int len = hotWords.size();
		overlapMat = new double[len][len];
		for (int i = 0; i < len; i++)
			for (int j = 0; j < len; j++) {
				double overlap = 0;
				// 两个热词代表的新闻集合，求交集
				List<ArticleShow> articlesi = hotWords.get(i).articleViews;
				List<ArticleShow> articlesj = hotWords.get(j).articleViews;
				double sum = articlesi.size() + articlesj.size();
				for (ArticleShow si : articlesi) {
					for (ArticleShow sj : articlesj) {
						if (si.equals(sj)) {
							overlap++;
							sum--;
						}
					}
				}
				overlapMat[i][j] = overlap / sum;
			}
		// 归一化
		overlapMat = normalizeMat(overlapMat, len);
		return overlapMat;
	}

	/**
	 * 矩阵归一化 [0,1]
	 * 
	 * @param mat
	 * @param len
	 * @return
	 */
	private double[][] normalizeMat(double[][] mat, int len) {

		double maxVal = mat[0][0], minVal = mat[0][0];
		for (int i = 0; i < len; i++) {
			for (int j = 0; j < len; j++) {
				maxVal = Math.max(maxVal, mat[i][j]);
				minVal = Math.min(minVal, mat[i][j]);
			}
		}
		for (int i = 0; i < len; i++) {
			for (int j = 0; j < len; j++) {
				mat[i][j] = (mat[i][j] - minVal) / (maxVal - minVal);
				mat[i][j] = round(mat[i][j], 3);
			}
		}
		return mat;
	}

	public static double round(double value, int places) {
		if (places < 0)
			return 0;
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

}
