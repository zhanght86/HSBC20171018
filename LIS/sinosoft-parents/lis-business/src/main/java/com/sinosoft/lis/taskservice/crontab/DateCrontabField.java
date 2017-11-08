package com.sinosoft.lis.taskservice.crontab;
import org.apache.log4j.Logger;

import java.util.Iterator;

public class DateCrontabField extends CrontabField {
private static Logger logger = Logger.getLogger(DateCrontabField.class);
	private DateModel dm;

	public DateCrontabField(String str, DateModel currentDate) {
		super();
		this.dm = currentDate;
		parseValue(str);
	}

	public DateCrontabField(String str, DateModel currentDate, boolean autofix) {
		this.dm = currentDate;
		super.autofix = autofix;
		parseValue(str);
	}

	public int getMax() {
		int m = super.getMax();
		if (m > getMaxBoard())
			return getMaxBoard();
		else
			return m;
	}

	public int getMin() {
		int m = super.getMin();
		if (m < getMinBoard())
			m = getMinBoard();
		if (m > getMax())
			m = getMax();
		return m;
	}

	public int getMaxBoard() {
		int[] d1 = new int[] { 1, 3, 5, 7, 8, 10, 12 };
		for (int i = 0; i < d1.length; i++) {
			if (d1[i] == dm.getMonth())
				return 31;
		}
		if (dm.getMonth() == 2) {
			if (dm.getYear() % 4 == 0)
				return 29;
			return 28;
		}

		return 30;
	}

	public int getMinBoard() {
		return 1;
	}

	public int calCurrentAvailable(int current) {
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			BaseField f = (BaseField) iterator.next();
			int end = f.end;
			if (end > getMaxBoard())
				end = getMaxBoard();
			int start = f.start;
			if (start < getMinBoard())
				start = getMinBoard();
			if (start > end)
				start = end;
			if (end < current)
				continue;
			while (current < start) {
				current += f.step;
			}
			if (current <= end)
				return current;
		}
		return -1;
	}

	public int calNext(int current) {
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			BaseField f = (BaseField) iterator.next();
			int end = f.end;
			if (end > getMaxBoard())
				end = getMaxBoard();
			if (end <= current)
				continue;
			if (current < f.start) {
				return f.start;
			}

			int n = f.start
					+ ((int) Math.ceil((current - f.start + 1)
							/ (double) f.step)) * f.step;
			if (n <= end)
				return n;
		}
		return -1;
	}

	public void parseValue(String str) {
		String[] ss0 = str.split(",");
		for (int i = 0; i < ss0.length; i++) {
			String[] ss = ss0[i].split("/");
			int step = 1;
			if (ss.length > 2)
				throw new IllegalArgumentException("应只有一个/");
			if (ss.length == 2) {
				step = Integer.parseInt(ss[1]);
			}
			String[] ss1 = ss[0].split("-");
			if (ss1.length > 2)
				throw new IllegalArgumentException("应只有一个-");
			int start, end;
			if (ss1.length == 2) {
				start = Integer.parseInt(ss1[0]);
				end = Integer.parseInt(ss1[1]);
			} else if (ss1[0].equals("*")) {
				start = getMinBoard();
				end = getMaxBoard();
			} else {
				start = end = Integer.parseInt(ss1[0]);
			}
			if (start > end)
				start = end;
			if (min > start)
				min = start;
			if (max < end)
				max = end;
			BaseField f = new BaseField(start, end, step);
			list.add(f);
		}
	}
	
	public void setCurrentDate(DateModel currentDate){
		this.dm=currentDate;
	}
}
