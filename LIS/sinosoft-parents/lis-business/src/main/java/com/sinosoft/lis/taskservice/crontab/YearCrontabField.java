package com.sinosoft.lis.taskservice.crontab;
import org.apache.log4j.Logger;

public class YearCrontabField extends CrontabField {
private static Logger logger = Logger.getLogger(YearCrontabField.class);
	public YearCrontabField(String str) {
		super(str);
	}

	public YearCrontabField(String str, boolean autofix) {
		super(str, autofix);
	}

	public int getMaxBoard() {
		return 2100;
	}

	public int getMinBoard() {
		return 1;
	}
}
