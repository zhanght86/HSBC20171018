package com.sinosoft.lis.tbgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCIssuePolDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.vschema.LCIssuePolSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: BL层业务逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @version 1.0
 * @date 2004-12-01
 */
public class UWQuestionBackBL {
private static Logger logger = Logger.getLogger(UWQuestionBackBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 往前面传输数据的容器 */
	private VData mResult = new VData();
	private TransferData mTransferData = new TransferData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 业务数据* */
	private String SerialNo;
	private String content;
	private String contno;

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	GlobalInput mGlobalInput = new GlobalInput();

	LCIssuePolSet tLCIssuePolSet = new LCIssuePolSet();

	// @Constructor
	public UWQuestionBackBL() {
	}

	/**
	 * 数据提交的公共方法
	 * 
	 * @param: cInputData 传入的数据 cOperate 数据操作字符串
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将传入的数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		// 将外部传入的数据分解到本类的属性中，准备处理
		if (!getInputData())
			return false;
		logger.debug("---getInputData---");

		// 校验传入的数据
		if (!checkData())
			return false;

		// 校验传入的数据
		if (!dealData())
			return false;

		return true;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		// 全局变量
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);

		SerialNo = (String) mInputData.get(1);
		if (SerialNo == null) {
			return false;
		}
		content = (String) mInputData.get(2);
		if (content == null) {
			return false;
		}
		contno = (String) mInputData.get(3);
		if (contno == null) {
			return false;
		}

		return true;
	}

	/**
	 * 校验传入的数据
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean checkData() {
		LCIssuePolDB tLCIssuePolDB = new LCIssuePolDB();
		tLCIssuePolDB.setSerialNo(SerialNo);
		tLCIssuePolSet = tLCIssuePolDB.query();
		if (tLCIssuePolSet == null || tLCIssuePolSet.size() != 1) {
			return false;
		}
		return true;
	}

	/**
	 * 取得操作结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	public boolean dealData() {
		tLCIssuePolSet.get(1).setReplyMan(mGlobalInput.Operator);
		tLCIssuePolSet.get(1).setReplyResult(content);
		tLCIssuePolSet.get(1).setProposalContNo(contno);
		MMap map = new MMap();
		map.put(tLCIssuePolSet, "UPDATE");
		mResult.add(map);
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mResult, "")) {
			return false;
		}
		return true;
	}

}
