package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 公司解约回退确认BL
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Nicholas
 * @version 1.0
 */

public class PEdorEABackConfirmBL extends PEdorCTBackConfirmBL implements
		EdorBack {
private static Logger logger = Logger.getLogger(PEdorEABackConfirmBL.class);

	public PEdorEABackConfirmBL() {
	}

	public static void main(String[] args) {
		PEdorEABackConfirmBL tPEdorEABackConfirmBL = new PEdorEABackConfirmBL();
	}
}
