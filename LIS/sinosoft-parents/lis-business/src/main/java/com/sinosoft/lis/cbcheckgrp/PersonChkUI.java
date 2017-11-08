package com.sinosoft.lis.cbcheckgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCIssuePolSchema;
import com.sinosoft.lis.schema.LCPENoticeItemSchema;
import com.sinosoft.lis.schema.LCPENoticeSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LJAPayPersonSchema;
import com.sinosoft.lis.schema.LJTempFeeClassSchema;
import com.sinosoft.lis.vschema.LCIssuePolSet;
import com.sinosoft.lis.vschema.LCPENoticeItemSet;
import com.sinosoft.lis.vschema.LCPENoticeSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LDPersonSet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

import com.sinosoft.service.BusinessService;
/**
 * <p>
 * Title: Web业务系统相同客户查询部分
 * </p>
 * <p>
 * Description:接口功能类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author WHN
 * @version 1.0
 */
public class PersonChkUI  implements BusinessService{
private static Logger logger = Logger.getLogger(PersonChkUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public PersonChkUI() {
	}

	// @Main
	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";

		LCPolSchema p = new LCPolSchema();
		p.setProposalNo("86110020030110001045");
		p.setPolNo("86110020030110001045");
		p.setAppFlag("A");

		LCPolSet tLCPolSet = new LCPolSet();
		tLCPolSet.add(p);

		//
		LCPENoticeSchema tLCPENoticeSchema = new LCPENoticeSchema();

		tLCPENoticeSchema.setContNo("00010120020110000007");
		tLCPENoticeSchema.setCustomerNo("33");
		tLCPENoticeSchema.setPEAddress("001000000");
		tLCPENoticeSchema.setPEDate("2002-10-31");
		tLCPENoticeSchema.setPEBeforeCond("0");
		tLCPENoticeSchema.setRemark("TEST");

		LCPENoticeSet tLCPENoticeSet = new LCPENoticeSet();
		tLCPENoticeSet.add(tLCPENoticeSchema);

		//
		LCPENoticeItemSchema tLCPENoticeItemSchema = new LCPENoticeItemSchema();

		tLCPENoticeItemSchema.setPEItemCode("001");
		tLCPENoticeItemSchema.setPEItemName("普通体检");

		LCPENoticeItemSet tLCPENoticeItemSet = new LCPENoticeItemSet();
		tLCPENoticeItemSet.add(tLCPENoticeItemSchema);

		tLCPENoticeItemSchema = new LCPENoticeItemSchema();
		tLCPENoticeItemSchema.setPEItemCode("002");
		tLCPENoticeItemSchema.setPEItemName("X光");
		tLCPENoticeItemSet.add(tLCPENoticeItemSchema);

		LCIssuePolSchema tLCIssuePolSchema = new LCIssuePolSchema();

		tLCIssuePolSchema.setBackObjType("2");
		tLCIssuePolSchema.setIssueCont("1234567");
		tLCIssuePolSchema.setOperatePos("1");
		// tLCIssuePolSchema.setProposalNo("86110020020110000015");
		tLCIssuePolSchema.setIssueType("001");

		LCIssuePolSet tLCIssuePolSet = new LCIssuePolSet();
		tLCIssuePolSet.add(tLCIssuePolSchema);

		// 暂交费关联表
		LJTempFeeClassSchema tLJTempFeeClassSchema = new LJTempFeeClassSchema();
		LJTempFeeClassSet tLJTempFeeClassSet = new LJTempFeeClassSet();
		tLJTempFeeClassSchema.setTempFeeNo("1234");
		tLJTempFeeClassSchema.setPayMode("1");

		tLJTempFeeClassSet.add(tLJTempFeeClassSchema);

		// 个人实交表
		LJAPayPersonSchema tLJAPayPersonSchema = new LJAPayPersonSchema();
		tLJAPayPersonSchema.setPayNo("86110020030310000064");

		// 校验重复客户

		VData tVData = new VData();
		// tVData.add( tLCPolSet );
		// tVData.add( tLCPENoticeSet);
		// tVData.add( tLCPENoticeItemSet);
		// tVData.add( tLCIssuePolSet);
		tVData.add(p);
		tVData.add(tG);
		PersonChkUI ui = new PersonChkUI();
		if (ui.submitData(tVData, "") == true) {
			logger.debug("---OK---");
		} else {
			logger.debug("---NO---");
		}

		VData tVdata = new VData();
		tVdata = ui.getResult();

		LDPersonSet tLDPersonSet = new LDPersonSet();
		tLDPersonSet = (LDPersonSet) tVdata.getObjectByObjectName(
				"LDPersonSet", 0);
		logger.debug("ss:" + tLDPersonSet.size());
		int n = ui.mErrors.getErrorCount();
		for (int i = 0; i < n; i++) {
			logger.debug("Error: " + ui.mErrors.getError(i).errorMessage);
		}

	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		PersonChkBL tPersonChkBL = new PersonChkBL();
		logger.debug("size0:" + mResult.size());
		logger.debug("---PersonChkBL UI BEGIN2---");
		if (tPersonChkBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPersonChkBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "PersonChkUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据查询失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			return false;
		}

		mResult.clear();
		mResult = tPersonChkBL.getResult();
		logger.debug("size:" + mResult.size());
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

}
