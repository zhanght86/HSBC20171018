package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import java.util.Set;

import com.sinosoft.lis.pubfun.ContHangUpBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContHangUpStateSchema;
import com.sinosoft.lis.vschema.LCContHangUpStateSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 续期挂起公共处理类
 * </p>
 * <p>
 * Description: 对于理赔,保全的挂起与解挂进行操作
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: ChinaSoft
 * </p>
 * 
 * @author HYQ MOD yyf
 * @version 1.0
 */
public class RNHangUp extends ContHangUpBL {
private static Logger logger = Logger.getLogger(RNHangUp.class);
	/** 全局变量 */
	private GlobalInput tGI = new GlobalInput();

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往前面传输数据的容器 */
	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 数据操作字符串 */
	private String mOperate;

	/** 操作位 */
	private String mOutOperate;

	/** 创建公共挂起类的变量 * */
	ContHangUpBL mContHangUpBL = null;

	public RNHangUp(GlobalInput tGI) {

		super(tGI);
	}

	/**
	 * 检查是否挂起
	 * 
	 * @return boolean
	 */
	public boolean checkHangUP(String tContNo) {
		if (!checkHangUpState(tContNo, "3")) // 3-续期，RNFlag
		{
			logger.debug("保单号"+tContNo + "被别的操作把续期挂起。");
			return false;
		} else {
			logger.debug("保单号"+tContNo + "未被挂起 ");
			return true;
		}
	}

	/**
	 * 挂起的操作是将此保单直接插入到挂起表中 挂起
	 * 
	 * @return boolean
	 */
	public LCContHangUpStateSchema hangUp(String tContNo) {
		mOutOperate = "INSERT";
		/** 按照公共类的要求将tLCContHangUpStateSchema的值全部准备完毕 */
		LCContHangUpStateSchema tLCContHangUpStateSchema = new

		LCContHangUpStateSchema();
		tLCContHangUpStateSchema.setContNo(tContNo);
		tLCContHangUpStateSchema.setHangUpType("3");
		tLCContHangUpStateSchema.setPosFlag("0"); // 挂起保全
		tLCContHangUpStateSchema.setClaimFlag("1"); // 挂起理赔
		tLCContHangUpStateSchema.setAgentFlag("1"); // 挂起续期

		MMap tMMap = new MMap();

		tMMap.add(hangUpCont(tLCContHangUpStateSchema));
		/** 得到公共类中返回的方法的有效值，以便进行提交 */
		LCContHangUpStateSchema tRLCContHangUpStateSchema = null;
		// 得到返回有效的MMap中的Schema的值
		Object o = null; // Schema或Set对象
		Set set = tMMap.keySet();
		o = set.toArray()[0];
		tRLCContHangUpStateSchema = (LCContHangUpStateSchema) o;
		return tRLCContHangUpStateSchema;

	}

	/**
	 * 解挂的操作是在解挂时将此保单在挂起表中的数据删除 解挂
	 * 
	 * @return LCContHangUpStateSchema
	 */
	public LCContHangUpStateSchema undoHangUp(String tContNo) {

		LCContHangUpStateSchema tLCContHangUpStateSchema = new LCContHangUpStateSchema();
		tLCContHangUpStateSchema.setContNo(tContNo);
		tLCContHangUpStateSchema.setPolNo("000000");
		tLCContHangUpStateSchema.setInsuredNo("000000");
		tLCContHangUpStateSchema.setHangUpType("3");
		return tLCContHangUpStateSchema;
	}

	/**
	 * 
	 * @param szFunc
	 *            String
	 * @param szErrMsg
	 *            String
	 */
	public String getOperate() {
		return mOutOperate;
	}

	/**
	 * 错误构建方法
	 * 
	 * @param szFunc
	 *            String
	 * @param szErrMsg
	 *            String
	 */
	private void buildError(String szFunc, String szErrMsg) {
		// CError cError = new CError();
		//
		// cError.moduleName = "RNHangUp";
		// cError.functionName = szFunc;
		// cError.errorMessage = szErrMsg;
		// this.mErrors.addOneError(cError);
	}

	public static void main(String aug[]) {
		GlobalInput tGI = new GlobalInput();

		tGI.Operator = "001";
		tGI.ManageCom = "8632";
		tGI.ComCode = "8632";
		RNHangUp tRNHangUp = new RNHangUp(tGI);
		if (!tRNHangUp.checkHangUP("HB010221351000029")) {
			logger.debug("被续期挂起 ");

		}

		MMap tMMap = new MMap();
		PubSubmit tPubSubmit = new PubSubmit();
		VData mInputData = new VData();
		/** 转化的例子，目的：从MMap中得到相应的Schema */
		// String str = "select * from lccont";
		// Object o = null; //Schema或Set对象
		// tMMap.put(str,"SELECT");
		// Set set = tMMap.keySet();
		//     
		// o = set.toArray()[0];
		// logger.debug((String)o);
		//     	
		/** 目的：将此保单在挂起表中挂起 */
		//
		LCContHangUpStateSet updateLCContHangUpStateSet = new LCContHangUpStateSet();

		updateLCContHangUpStateSet.add(tRNHangUp.hangUp("HB010221351000029"));
		tMMap.put(updateLCContHangUpStateSet, tRNHangUp.getOperate());

		/** 目的：将此保单在挂起表中解挂 */
		// tMMap.put(tRNHangUp.undoHangUp("HB010221351000029"), "DELETE");
		/** ***** 提交 ****** */
		mInputData.add(tMMap);
		if (!tPubSubmit.submitData(mInputData, "")) {

		}
	}
}
