/**
 * 
 */
package com.sinosoft.lis.taskservice.crontab;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class CrontabField {
private static Logger logger = Logger.getLogger(CrontabField.class);
	int min = Integer.MAX_VALUE;
	int max = -1;
	boolean autofix = true;

	public abstract int getMaxBoard();

	public abstract int getMinBoard();

	protected CrontabField() {

	}

	public CrontabField(String str) {
		parseValue(str);
	}

	public CrontabField(String str, boolean autofix) {
		this.autofix = autofix;
		parseValue(str);
	}

	ArrayList list = new ArrayList();

	public int calNext(int current) {
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			BaseField f = (BaseField) iterator.next();
			if (f.end <= current)
				continue;
			if (current < f.start) {
				return f.start;
			}

			int n = f.start
					+ ((int) Math.ceil((current - f.start + 1)
							/ (double) f.step)) * f.step;
			if (n <= f.end)
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
			if (start < getMinBoard()) {
				if (autofix)
					start = getMinBoard();
				else
					throw new IllegalArgumentException(this.getClass()
							.getName()
							+ "的" + start + "小于于最小值" + getMinBoard());
			}
			if (end > getMaxBoard()) {
				if (autofix)
					end = getMaxBoard();
				else
					throw new IllegalArgumentException(this.getClass()
							.getName()
							+ "的" + end + "大于最大值" + getMaxBoard());
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

	public int calCurrentAvailable(int current) {
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			BaseField f = (BaseField) iterator.next();
			if (f.end < current)
				continue;
			while (current < f.start) {
				current += f.step;
			}
			if (current <= f.end)
				return current;
		}
		return -1;
	}

	public int getMin() {
		return min;
	}

	public int getMax() {
		return max;
	}

	public boolean isAutofix() {
		return autofix;
	}

	public void setAutofix(boolean autofix) {
		this.autofix = autofix;
	}
}
