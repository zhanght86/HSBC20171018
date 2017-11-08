package com.sinosoft.lis.taskservice.crontab;
import org.apache.log4j.Logger;


public class MonthCrontabField extends CrontabField {
private static Logger logger = Logger.getLogger(MonthCrontabField.class);

	public MonthCrontabField(String str) {
		super(str);
	}

	public MonthCrontabField(String str, boolean autofix) {
		super(str, autofix);
	}

	public int getMaxBoard() {
		return 12;
	}

	public int getMinBoard() {
		return 1;
	}
}
