/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.taskservice;
import org.apache.log4j.Logger;

/**
 * <p>
 * Title: 没有任务计划异常
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author ZhangRong
 * @version 1.0
 */

public class NoTaskPlanException extends Exception {
private static Logger logger = Logger.getLogger(NoTaskPlanException.class);
	private static final long serialVersionUID = 1L;

	public NoTaskPlanException() {
	}
}
