package com.sinosoft.lis.taskservice.crontab;
import org.apache.log4j.Logger;

public class BaseField {
private static Logger logger = Logger.getLogger(BaseField.class);
	int start;
	int end;
	int step;

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public BaseField(int start, int end, int step) {
		this.start = start;
		this.end = end;
		this.step = step;
	}
}
