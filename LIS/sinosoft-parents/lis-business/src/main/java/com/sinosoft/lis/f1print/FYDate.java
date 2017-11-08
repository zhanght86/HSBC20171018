package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FYDate {
private static Logger logger = Logger.getLogger(FYDate.class);
	private Calendar calendar = null;
	private final String pattern = "yyyy-MM-dd";
	private final String pattern1 = "yyyyMMdd";
	private SimpleDateFormat df;
	private SimpleDateFormat df1;
	private int mTimeDate = 0;
	private StringBuffer mStringBuffer = new StringBuffer();
	private String mMaxDate = "";
	private String mMinDate = "";
	private String[] mSaveDate = null;

	// 空的构造函数，得到的是系统的时间
	public FYDate() {
		calendar = Calendar.getInstance();
		Date trialTime = new Date();
		calendar.setTime(trialTime);

	}

	public static String CurrentDate() {
		FYDate f = new FYDate();
		return f.getOracleDate();
	}

	// 带有参数的构造函数，可以把传来的String转换成时间格式可以有 yyyy-MM-dd ，，yyyyMMdd 格式
	public FYDate(String mDate) {
		// df = new SimpleDateFormat(pattern);
		// df1 = new SimpleDateFormat(pattern1);
		calendar = Calendar.getInstance();
		calendar.setTime(getDate(mDate));
	}

	public FYDate(Date tDate) {
		calendar = Calendar.getInstance();
		calendar.setTime(tDate);
	}

	public Date getDate(String dateString) {
		df = new SimpleDateFormat(pattern);
		df1 = new SimpleDateFormat(pattern1);
		Date tDate = null;
		try {
			if (dateString.indexOf("-") != -1) {
				tDate = df.parse(dateString);
			} else {
				tDate = df1.parse(dateString);
			}
		} catch (Exception e) {
			// @@错误处理
			logger.debug("错误的数据格式");
		}

		return tDate;
	}

	public void addDate(Date tDate) {
		add(String.valueOf(tDate));
	}

	// 将给定的日期放入一个StringBuffer
	public void add(String tStringDate) {
		logger.debug("================================" + tStringDate);
		if (tStringDate != null && !tStringDate.equals("null")
				&& !tStringDate.equals("")) {
			if (mTimeDate == 0) {
				mStringBuffer.append(tStringDate);
			} else {
				mStringBuffer.append(",");
				mStringBuffer.append(tStringDate);
			}
			mTimeDate++;
		}
	}

	// 对给定的所有日期进行排序
	private void taxis() {
		mSaveDate = new String[mTimeDate];
		mSaveDate = String.valueOf(mStringBuffer).split(",");
		String tTemp = "";
		for (int i = 0; i < mSaveDate.length; i++) {
			for (int j = i + 1; j < mSaveDate.length; j++) {
				if (mSaveDate[i].compareTo(mSaveDate[j]) > 0) {
					tTemp = mSaveDate[i];
					mSaveDate[i] = mSaveDate[j];
					mSaveDate[j] = tTemp;

				}
			}
		}
		mMinDate = mSaveDate[0];
		mMaxDate = mSaveDate[mTimeDate - 1];

	}

	// 返回一个最小最大的时间日期；
	public String getMaxDate() {
		taxis();
		return mMaxDate;
	}

	public String getMinDate() {
		taxis();
		return mMinDate;
	}

	// 分别得到比较后最小的和最大的年月
	public String getMinYear() {
		FYDate mMy = new FYDate(getMinDate());
		return mMy.getYear();
	}

	public String getMaxYear() {
		FYDate msy = new FYDate(getMaxDate());
		return msy.getYear();
	}

	public String getMinMonth() {
		FYDate mmth = new FYDate(getMinDate());
		return mmth.getMonth();
	}

	public String getMaxMonth() {
		FYDate mMax = new FYDate(getMaxDate());
		return mMax.getMonth();
	}

	// 得到提表的时日，比如几个日期的月是相同，就显示一个月份，如果不同反之
	public String getTableMonth() {
		if (getMaxMonth().equals(getMinMonth())) {
			return getMaxMonth();
		} else {
			return getMinMonth() + "至" + getMaxMonth();
		}

	}

	// 得到时间的函数。
	public int getYearInt() {
		return calendar.get(Calendar.YEAR);
	}

	public String getYear() {
		return String.valueOf(calendar.get(Calendar.YEAR));
	}

	public int getMonthInt() {
		return calendar.get(Calendar.MONTH) + 1;
	}

	public String getMonth() {
		return String.valueOf(calendar.get(Calendar.MONTH) + 1);
	}

	public int getDateOfMonthInt() {
		return calendar.get(Calendar.DAY_OF_MONTH);

	}

	public String getDateOfMonth() {
		return String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
	}

	public int getDayOfWeekInt() {
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	public String getDayOfWeek() {
		int m = getDayOfWeekInt() - 1;
		String[] day = new String[] { "日", "一", "二", "三", "四", "五", "六" };
		return "星期" + day[m];
	}

	public String getDateMDY() {
		return String.valueOf(getMonthInt() + "月 " + getDateOfMonth() + "日 "
				+ getYear() + "年");
	}

	public String getOracleDate() {
		return String.valueOf(getYear() + "-" + getMonthInt() + "-"
				+ getDateOfMonth());
	}

	public int getHourInt() {
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	public String getHour() {
		return String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
	}

	public int getMinute() {
		return calendar.get(Calendar.MINUTE);
	}

	public int getSecond() {
		return calendar.get(Calendar.SECOND);
	}

	public String getTime() {
		return String.valueOf(getHourInt() + ": " + getMinute() + ": "
				+ getSecond());

	}

	public static void main(String arg[]) {
		// FYDate tFYDate = new FYDate();
		// tFYDate.add("1900-01-20");
		// tFYDate.add("1900-05-20");
		// tFYDate.add("1900-02-20");
		// tFYDate.add("1900-03-20");
		// tFYDate.add("1900-04-20");
		// tFYDate.add("1900-05-20");
		// tFYDate.add("1900-06-20");
		// tFYDate.add("null");
		// logger.debug(tFYDate.getTableMonth());
		String ds = new FYDate("1900-05-20").getMonth();
		logger.debug(ds);
		// FYDate ate = new FYDate(tFYDate);
		// logger.debug(ate.getMonth());
		// FYDate ae = new FYDate(tFYDate.getMinDate());
		// logger.debug(ae.getYear());

	}
}
