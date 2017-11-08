package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCContHangUpStateDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LLCUWBatchDB;
import com.sinosoft.lis.db.LLClaimPolicyDB;
import com.sinosoft.lis.operfee.IndiDueFeeCancelBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.LCContHangUpBL;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LCContHangUpStateSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LLCUWBatchSchema;
import com.sinosoft.lis.schema.LLUWPremMasterSchema;
import com.sinosoft.lis.vschema.LCContHangUpStateSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LLCUWBatchSet;
import com.sinosoft.lis.vschema.LLClaimPolicySet;
import com.sinosoft.lis.vschema.LLUWPremMasterSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 提起二次核保
 * </p>
 * <p>
 * Description: 提起二次核保
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author zhangxing
 * @version 1.0
 */

public class LLSecondUWBL {
private static Logger logger = Logger.getLogger(LLSecondUWBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	/** 往工作流引擎中传输数据的容器 */
	private VData mResult = new VData();
	private GlobalInput mG = new GlobalInput();
	private TransferData mTransferData = new TransferData();
	private LLCUWBatchSet mLLCUWBatchSet = new LLCUWBatchSet();
	private LLCUWBatchSchema mLLCUWBatchSchema = new LLCUWBatchSchema();
	private LLUWPremMasterSet mLLUWPremMasterSet = new LLUWPremMasterSet();
	private Reflections mReflections = new Reflections();
	private MMap map = new MMap();
	/** 数据操作字符串 */
	private String mOperator;
	private String mManageCom;
	private String mOperate;
	private String mClmNo;
	private String mBatNo;
	private String mContNo;
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	public LLSecondUWBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		// 校验是否有未打印的体检通知书
		if (!checkData()) {
			return false;
		}

		logger.debug("Start  dealData...");

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		logger.debug("dealData successful!");

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		logger.debug("Start  Submit...");
		return true;
	}

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		// 从输入数据中得到所有对象
		// 获得全局公共数据
		mG = (GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0);
		mLLCUWBatchSet = (LLCUWBatchSet) cInputData.getObjectByObjectName(
				"LLCUWBatchSet", 0);
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		// mInputData = cInputData;
		if (mG == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据失败!");
			return false;
		}

		// 获得操作员编码
		mOperator = mG.Operator;
		if (mOperator == null || mOperator.trim().equals("")) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据mOperator失败!");
			return false;
		}

		// 获得管理机构编码
		mManageCom = mG.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据mOperator失败!");
			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		
		// 判断该批次里是否存在合同已经发起二核但还未完成，如果存在则不允许该合同发起二核
		if (!JugeCont()) {
			return false;
		}
		
		// 在发起时同时将涉及的合同挂起<除理赔挂起外全部挂起>
		if (!LLCUWContSuspend()) {
			return false;
		}

		// 往“个人理赔合同批次表”写入发起二核记录
		if (!prepareLLCUW()) {
			return false;
		}
		
		return true;
	}

	// 判断该批次里是否存在合同已经发起二核但还未完成，如果存在则不允许该合同发起二核
	private boolean JugeCont() {
		
		for (int j = 1; j <= mLLCUWBatchSet.size(); j++) {
			
			String tCaseNo = mLLCUWBatchSet.get(j).getCaseNo();
			String tContNo = mLLCUWBatchSet.get(j).getContNo();

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No6.0 判断该合同是否被挂起
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
//			LCContHangUpBL tLCContHangUpBL = new LCContHangUpBL(mG, tCaseNo,
//					"4", tContNo);
//
//			if (!tLCContHangUpBL.queryHungUpForContNo()) {
//				CError.buildErr(this, "查询合同是否挂起信息失败,"+tLCContHangUpBL.mErrors.getLastError());
//				return false;
//			}

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No7.0
			 * 判断该合同的核保处理是否完成
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			LLCUWBatchDB tLLCUWBatchDB = new LLCUWBatchDB();
			tLLCUWBatchDB.setCaseNo(tCaseNo);
			tLLCUWBatchDB.setContNo(tContNo);
			LLCUWBatchSet tLLCUWBatchSet = tLLCUWBatchDB.query();

			for (int m = 1; m <= tLLCUWBatchSet.size(); m++) {
				LLCUWBatchSchema tLLCUWBatchSchema = tLLCUWBatchSet.get(m);
				String tState = StrTool.cTrim(tLLCUWBatchSchema.getState());
				String tInEffectFlag = StrTool.cTrim(tLLCUWBatchSchema
						.getInEffectFlag());

				// 0--未完成,1--已完成
				if (tState.equals("0")) {
					CError.buildErr(this, "合同[" + tContNo
							+ "]已经发起二核，且还未完成，不允许再次提起二核!!!");
					return false;
				}

				// 0--未处理,1--生效,2--不生效
				if (tInEffectFlag.equals("0") || tInEffectFlag.equals("")) {
					CError.buildErr(this, "合同[" + tContNo
							+ "]没有下是否生效结论，请先下结论，再提起二核!!!");
					return false;
				}
			}

		}
		return true;
	}

	// 在发起时同时将涉及的合同挂起<除理赔挂起外全部挂起>
	private boolean LLCUWContSuspend() {
		LCContHangUpStateSet tLCContHangUpStateSaveSet = new LCContHangUpStateSet();

		for (int j = 1; j <= mLLCUWBatchSet.size(); j++) {
			String tCaseNo = mLLCUWBatchSet.get(j).getCaseNo();
			String tContNo = mLLCUWBatchSet.get(j).getContNo();

			if("".equals(tCaseNo) || "".equals(tContNo))
			{
				CError.buildErr(this, "核赔表对应的案件号或者合同同为空");
				return false;
			}
			//zy 2010-02-26 处理二核保单挂起有问题，此处应该只挂起二核、保全和续期，理赔的挂起则需要判断
//			LCContHangUpBL tLCContHangUpBL = new LCContHangUpBL(mG, tCaseNo,
//					"4", tContNo);
//			tLCContHangUpBL.setPosFlag("1");
//			tLCContHangUpBL.setRnFlag("1");
//			tLCContHangUpBL.setClmFlag("1");
//			LCContHangUpStateSchema tLCContHangUpStateSchema = tLCContHangUpBL
//					.getHungUp();
//			tLCContHangUpStateSchema.setStandFlag1("CLAIMUW");//二核挂起
//			tLCContHangUpStateSaveSet.add(tLCContHangUpStateSchema);
			LCContHangUpStateDB tLCContHangUpStateDB = new LCContHangUpStateDB();
			tLCContHangUpStateDB.setHangUpType("4"); // 存储挂起类型
			tLCContHangUpStateDB.setHangUpNo(tCaseNo); // 存储受理号	
			tLCContHangUpStateDB.setContNo(tContNo);// 存储保单号，理赔解挂时为空	
			LCContHangUpStateSet tLCContHangUpStateSet = tLCContHangUpStateDB.query();
			if (tLCContHangUpStateDB.mErrors.needDealError()) {
				mErrors.addOneError("挂起类型[" + 4 + "],受理号[" + mClmNo	+ "]的查询出现问题!!!");
				return false;
			}
			if(tLCContHangUpStateSet.size()>0)
			{
				for(int m=1;m<=tLCContHangUpStateSet.size();m++)
				{
					LCContHangUpStateSchema tLCContHangUpStateSchema =tLCContHangUpStateSet.get(m);
					tLCContHangUpStateSchema.setStandFlag1("CLAIMUW");//二核挂起
					tLCContHangUpStateSaveSet.add(tLCContHangUpStateSchema);
				}
			}
			else
			{
				LCContHangUpBL tLCContHangUpBL = new LCContHangUpBL(mG, tCaseNo,
				"4", tContNo);
				tLCContHangUpBL.setPosFlag("1");
				tLCContHangUpBL.setRnFlag("1");
				LCContHangUpStateSchema tLCContHangUpStateSchema = tLCContHangUpBL
						.getHungUp();
				tLCContHangUpStateSchema.setStandFlag1("CLAIMUW");//二核挂起
				tLCContHangUpStateSaveSet.add(tLCContHangUpStateSchema);
			}
			
		}
		map.put(tLCContHangUpStateSaveSet, "DELETE&INSERT");
		return true;
	}

	// 往“个人理赔合同批次表”写入发起二核记录
	private boolean prepareLLCUW() {
		// 生成批次号<方法待定>
		// logger.debug(mLLCUWBatchSet.size());
		String tLimit = PubFun.getNoLimit(mG.ManageCom);
		String tBatNo = "6" + PubFun1.CreateMaxNo("LLUWBatNo", "10");
		mBatNo = tBatNo;
		logger.debug("------生成批次号--tBatNo---------" + tBatNo);
		for (int j = 1; j <= mLLCUWBatchSet.size(); j++) {
			// 查询 集体合同号码、总单投保单号码，
			LCContDB tLCContDB = new LCContDB();
			tLCContDB.setContNo(mLLCUWBatchSet.get(j).getContNo());
			
			if (!tLCContDB.getInfo()) {
				CError.buildErr(this, "查询不到"+mLLCUWBatchSet.get(j).getContNo()+"的合同信息!");
				return false;
			}
			
			mLLCUWBatchSet.get(j).setGrpContNo(tLCContDB.getGrpContNo());// 集体合同号码
			mLLCUWBatchSet.get(j).setProposalContNo(tLCContDB.getProposalContNo());// 总单投保单号码
			
			// 查询是合同否与当前赔案相关
			LLClaimPolicyDB tLLClaimPolicyDB = new LLClaimPolicyDB();
			LLClaimPolicySet tLLClaimPolicySet = new LLClaimPolicySet();
			tLLClaimPolicyDB.setCaseNo(mLLCUWBatchSet.get(j).getCaseNo());
			tLLClaimPolicyDB.setContNo(mLLCUWBatchSet.get(j).getContNo());
			tLLClaimPolicySet.set(tLLClaimPolicyDB.query());
			if (tLLClaimPolicySet == null || tLLClaimPolicySet.size() == 0) {
				mLLCUWBatchSet.get(j).setClaimRelFlag("1");// 不相关标志
			} else {
				mLLCUWBatchSet.get(j).setClaimRelFlag("0");// 相关标志
			}

			// //赔案号、被保人客户号、合同号码 由前台传入
			mLLCUWBatchSet.get(j).setBatNo(tBatNo);// 批次号
			mLLCUWBatchSet.get(j).setUWNo(1);// 核保顺序号
			mLLCUWBatchSet.get(j).setState("0");// 状态--未完成
			mLLCUWBatchSet.get(j).setOperator(mG.Operator);
			mLLCUWBatchSet.get(j).setUWOperator(mG.Operator);//二核发起人
			mLLCUWBatchSet.get(j).setMakeDate(mCurrentDate);
			mLLCUWBatchSet.get(j).setMakeTime(mCurrentTime);
			mLLCUWBatchSet.get(j).setModifyDate(mCurrentDate);
			mLLCUWBatchSet.get(j).setModifyTime(mCurrentTime);
			mLLCUWBatchSet.get(j).setManageCom(mG.ManageCom);
			mLLCUWBatchSet.get(j).setInEffectFlag("0");
			//将承保时的加费数据导入到理赔核保
			if(!dealAddFee(mLLCUWBatchSet.get(j).getCaseNo(),mLLCUWBatchSet.get(j).getContNo())){
				return false;
			}
		}
		map.put(mLLCUWBatchSet, "INSERT");
		map.put(mLLUWPremMasterSet, "INSERT");

		mTransferData.removeByName("BatNo");
		mTransferData.setNameAndValue("BatNo", tBatNo);// 批次号

		return true;
	}

	// 2005-11-16修改，所有合同只生成一条相关记录。
	private boolean prepareNodeData() {
		// 修改及判断工作任务生成方法 0---相关，1---无关，2----都存在
		String m = "0"; // 判断是否存在 0---相关，[m=0不存在；m=1不存在]
		String n = "0"; // 判断是否存在 1---无关，[n=0不存在；n=1不存在]
		String tClaimRelFlag = "0"; // 赔案相关标志<默认相关>
		// for (int j = 1; j <= mLLCUWBatchSet.size(); j++)
		// {
		// if (mLLCUWBatchSet.get(j).getClaimRelFlag() == "0") {m ="1";}
		// if (mLLCUWBatchSet.get(j).getClaimRelFlag() == "1") {n ="1";}
		// }
		// if (m == "1" && n == "0")
		// { //
		// tClaimRelFlag = "0"; ////只有0---相关的合同号
		// }
		// else if (m == "0" && n == "1")
		// { //
		// tClaimRelFlag = "1"; //只有1---无关的合同号
		// }
		// else if (m == "1" && n == "1")
		// { //
		// tClaimRelFlag = "2"; //“0---相关”“1---无关”都存在
		// }
		// else
		// {
		// CError tError = new CError();
		// tError.moduleName = "LLSecondUWBL";
		// tError.functionName = "getInputData";
		// tError.errorMessage = "前台传输数据相关标志失败!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		mTransferData.removeByName("ClaimRelFlag");
		mTransferData.setNameAndValue("ClaimRelFlag", tClaimRelFlag);// 赔案相关标志
		return true;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		if (!prepareNodeData()) {
			CError.buildErr(this, "准备节点数据失败!");
			return false;
		}
		mResult.clear();
		mResult.add(mG);
		mResult.add(map);// 业务数据包
		mResult.add(mTransferData);// 工作流数据
		return true;
	}

	/**
	 * 将承保的加费数据导入  LCPrem的加费信息---lluwpremmaster
	 * */
	private boolean dealAddFee(String tCaseNo,String tContNo){
		LCPremSet tLCPremSet = new LCPremSet();
		LCPremDB tLCPremDB = new LCPremDB();
		String tAddFeeSql = "select * from lcprem where contno='"+"?contno?"+"' and payplancode like '000000%'";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tAddFeeSql);
		sqlbv.put("contno", tContNo);
		tLCPremSet = tLCPremDB.executeQuery(sqlbv);
		for(int i=1;i<=tLCPremSet.size();i++){
			LCPremSchema tLCPremSchema = new LCPremSchema();
			LLUWPremMasterSchema tLLUWPremMasterSchema = new LLUWPremMasterSchema();
			tLCPremSchema = tLCPremSet.get(i);
			mReflections.transFields(tLLUWPremMasterSchema, tLCPremSchema);
			tLLUWPremMasterSchema.setClmNo(tCaseNo);
			tLLUWPremMasterSchema.setBatNo(mBatNo);
			tLLUWPremMasterSchema.setModifyDate(mCurrentDate);
			tLLUWPremMasterSchema.setModifyTime(mCurrentTime);
			tLLUWPremMasterSchema.setMakeDate(mCurrentDate);
			tLLUWPremMasterSchema.setMakeTime(mCurrentTime);
			tLLUWPremMasterSchema.setOperator(mG.Operator);
			mLLUWPremMasterSet.add(tLLUWPremMasterSchema);
		}
		return true;
	}
	
	/**
	 * 返回结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回错误
	 * 
	 * @return VData
	 */
	public CErrors getErrors() {
		return mErrors;
	}

}
