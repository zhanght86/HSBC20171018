package com.sinosoft.lis.taskservice.crontab;

import org.apache.log4j.Logger;

import java.util.Date;

public class DayCrontabField extends CrontabField {
	private static Logger logger = Logger.getLogger(DayCrontabField.class);

	Date dm;

	public DayCrontabField(String str, Date dm) {
		super(str);
		this.dm = dm;
	}

	public DayCrontabField(String str, Date dm, boolean autofix) {
		super(str, autofix);
		this.dm = dm;
	}

	public int getMaxBoard() {
		return 6;
	}

	public int getMinBoard() {
		return 0;
	}

	public int calCurrentAvailable(int current) {
		int n = super.calCurrentAvailable(current);
		if (n == -1) {
			n = 7 - current + getMin() + dm.getDate();
		} else {
			n = n - current + dm.getDate();
		}
		if (n > getMaxDate())
			return -1;
		else
			return n;
	}

	private int getMaxDate() {
		int[] d1 = new int[] { 1, 3, 5, 7, 8, 10, 12 };
		for (int i = 0; i < d1.length; i++) {
		    //修正日期类型相差一个月的错误
//            if (d1[i] == dm.getMonth())
			if (d1[i] == dm.getMonth()+1)
				return 31;
		}
//        if (dm.getMonth() == 2) {
//            if (dm.getYear() % 400 == 0)
		if (dm.getMonth()+1 == 2) {
		    //闰年
			if ((((dm.getYear()+1900) % 400 == 0) | ((dm.getYear()+1900) % 100 != 0) & ((dm.getYear()+1900) % 4 == 0)))
				return 29;
			return 28;
		}

		return 30;
	}

	public int calNext(int current) {
		int n = super.calCurrentAvailable(current);
		if (n == -1) {
			n = 7 - current + getMin() + dm.getDate();
		} else {
			n = n - current + dm.getDate();
		}
		if (n > getMax())
			return -1;
		else
			return n;
	}
}

