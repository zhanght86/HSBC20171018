package com.sinosoft.lis.taskservice.crontab;
import org.apache.log4j.Logger;

public class MinuteCrontabField extends CrontabField {
private static Logger logger = Logger.getLogger(MinuteCrontabField.class);
	public MinuteCrontabField(String str) {
		super(str);
	}

	public MinuteCrontabField(String str, boolean autofix) {
		super(str, autofix);
	}

	public int getMaxBoard() {
		return 59;
	}

	public int getMinBoard() {
		return 0;
	}
}
