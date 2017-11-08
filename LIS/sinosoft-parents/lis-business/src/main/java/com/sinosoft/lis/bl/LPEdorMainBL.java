/*
 * <p>ClassName: LLClaimBL </p>
 * <p>Description: LLClaimSchemaBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔
 * @CreateDate：2002-07-25
 */
package com.sinosoft.lis.bl;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LPEdorMainSchema;
import com.sinosoft.lis.schema.LPGrpEdorMainSchema;
import com.sinosoft.lis.schema.LPPolSchema;

public class LPEdorMainBL extends LPEdorMainSchema {
private static Logger logger = Logger.getLogger(LPEdorMainBL.class);
	// @Constructor
	public LPEdorMainBL() {
	}

	public void setDefaultFields() {
		this.setMakeDate(PubFun.getCurrentDate());
		this.setMakeTime(PubFun.getCurrentTime());
		this.setModifyDate(PubFun.getCurrentDate());
		this.setModifyTime(PubFun.getCurrentTime());
	}

	public void setUpdateFields() {
		this.setModifyDate(PubFun.getCurrentDate());
		this.setModifyTime(PubFun.getCurrentTime());
	}

	public void initLPEdorMainSchema(LPPolSchema aLPPolSchema,
			LPGrpEdorMainSchema mLPGrpEdorMainSchema, GlobalInput mGlobalInput) {
		// this.setEdorType(mLPGrpEdorMainSchema.getEdorType());
		// this.setGrpPolNo(mLPGrpEdorMainSchema.getGrpPolNo());
		this.setEdorValiDate(mLPGrpEdorMainSchema.getEdorValiDate());
		this.setEdorAppDate(mLPGrpEdorMainSchema.getEdorAppDate());
		// this.setPolNo(aLPPolSchema.getPolNo());
		this.setEdorNo(mLPGrpEdorMainSchema.getEdorNo());
		this.setEdorAppNo(mLPGrpEdorMainSchema.getEdorNo());
		this.setContNo(aLPPolSchema.getContNo());
		// this.setInsuredNo(aLPPolSchema.getInsuredNo());
		// this.setInsuredName(aLPPolSchema.getInsuredName());
		// this.setPaytoDate(aLPPolSchema.getPaytoDate());
		// this.setSumPrem(aLPPolSchema.getSumPrem());
		this.setEdorState("1");
		this.setUWState("0");
		this.setChgPrem("0");
		this.setChgAmnt("0");
		// this.setChgGetAmnt("0");
		this.setGetMoney("0");
		this.setGetInterest("0");
		this.setOperator(mGlobalInput.Operator);
		this.setManageCom(aLPPolSchema.getManageCom());

		setDefaultFields();
	}

}
