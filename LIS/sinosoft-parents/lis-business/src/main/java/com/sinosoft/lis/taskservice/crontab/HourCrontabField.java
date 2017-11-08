package com.sinosoft.lis.taskservice.crontab;
import org.apache.log4j.Logger;

public class HourCrontabField extends CrontabField {
private static Logger logger = Logger.getLogger(HourCrontabField.class);
	public HourCrontabField(String str) {
		super(str);
	}

	public HourCrontabField(String str, boolean autofix) {
		super(str, autofix);
	}

	public int getMaxBoard() {
		return 23;
	}

	public int getMinBoard() {
		return 0;
	}
}
