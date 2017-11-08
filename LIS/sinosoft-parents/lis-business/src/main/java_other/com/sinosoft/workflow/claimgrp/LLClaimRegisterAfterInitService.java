package com.sinosoft.workflow.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.claim.LLClaimPubFunBL;
import com.sinosoft.lis.claim.LLLcContHangUpBL;
import com.sinosoft.lis.claim.LLLcContReleaseBL;
import com.sinosoft.lis.claim.LLLcContSuspendBL;
import com.sinosoft.lis.claimgrp.LLClaimPopedomSetBL;
import com.sinosoft.lis.claimgrp.LLClaimSimpleSetBL;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LLAppClaimReasonDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterInitService;

/**
 * <p>Title: 立案确认服务类 </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: SinoSoft</p>
 * @author zl
 * @version 1.0
 */

public class LLClaimRegisterAfterInitService implements AfterInitService {
private static Logger logger = Logger.getLogger(LLClaimRegisterAfterInitService.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 提交数据容器 */
	private MMap map = new MMap();
	// private VData mIputData = new VData();
	private TransferData mTransferData = new TransferData();

	private VData tVData = new VData();
	private MMap tMap = new MMap();
	private TransferData tTransferData = new TransferData();
	private String CurrentDate = PubFun.getCurrentDate();
	private LLRegisterSchema mLLRegisterSchema=new LLRegisterSchema();;
	private LLClaimPubFunBL mLLClaimPubFunBL = new LLClaimPubFunBL();

	/** 数据操作字符串 */
	private String mOperater;
	private String mManageCom;
	private String mOperate;
	private String mMissionID;
	private String mSubMissionID;
	private String mRgtConclusion = "";
	private String mClmNo = "";
	private String mSimpleFlag = "";
	private String mMaxLevel = ""; // 最高权限
	private String mComFlag = "0"; // 权限越界标志
	private String mUser = ""; // 审核人
	
	public LLClaimRegisterAfterInitService() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate))
			return false;

		// 校验传入数据
		if (!checkData())
			return false;

		logger.debug("Start  dealData...");

		// 进行业务处理
		if (!dealData())
			return false;

		logger.debug("dealData successful!");

		// 为工作流下一节点属性字段准备数据
		if (!prepareTransferData())
			return false;

		// 准备往后台的数据
		if (!prepareOutputData())
			return false;

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
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		mClmNo = (String) mTransferData.getValueByName("RptNo");
		mRgtConclusion = (String) mTransferData.getValueByName("RgtConclusion"); // 立案结论

		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "AskUWRegisterAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if (mOperater == null || mOperater.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "LLClaimRegisterAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据Operate失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得当前工作任务的任务ID
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		if (mMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "LLClaimRegisterAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		
		mLLRegisterSchema=mLLClaimPubFunBL.getLLRegister(this.mClmNo);
		
		return true;
	}

	/**
	 * 根据前面的输入数据，调用BL进行逻辑处理，返回处理完数据 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		logger.debug("----------Service dealData BEGIN----------");
		//01-立案通过
		if (mRgtConclusion.equals("01")) {
			
			/**
			 * 2009-04-28 zhangzheng
			 * 团险理赔立案确认不判断简易案件
			 */
//			/**
//			 * -----------------------------------------------------------------BEG
//			 * No.1： 判断是否为简易案件 所需变量： mInputData 更新变量：
//			 * -----------------------------------------------------------------
//			 */
//			LLClaimSimpleSetBL tLLClaimSimpleSetBL = new LLClaimSimpleSetBL();
//			if (!tLLClaimSimpleSetBL.submitData(mInputData, "")) {
//				// @@错误处理
//				CError.buildErr(this, "判断案件是否为简易案件失败,"+tLLClaimSimpleSetBL.mErrors.getLastError());
//				return false;
//			} else {
//				System.out
//						.println("-----start Service getData from LLClaimSimpleSetBL");
//				VData tVData = new VData();
//				MMap tMap = new MMap();
//				tVData = tLLClaimSimpleSetBL.getResult();
//				tMap = (MMap) tVData.getObjectByObjectName("MMap", 0);
//				TransferData tTransferData = new TransferData();
//				tTransferData = (TransferData) tVData.getObjectByObjectName(
//						"TransferData", 0);
//				mSimpleFlag = (String) tTransferData
//						.getValueByName("SimpleFlag");
//				// 更改转移条件
//				mTransferData.removeByName("SimpleFlag");
//				mTransferData.setNameAndValue("SimpleFlag", mSimpleFlag);// 审核模块权限
//				map.add(tMap);
//			}
			
			//zhangzheng 默认为0,非简易案件
			mSimpleFlag="0";
			mTransferData.removeByName("SimpleFlag");
			mTransferData.setNameAndValue("SimpleFlag", "0");// 审核模块权限
			// ------------------------------------------------------------------END

			/**
			 * -----------------------------------------------------------------BEG
			 * No.2： 提交立案通过处理类 所需变量： mInputData 更新变量：
			 * -----------------------------------------------------------------
			 */
			if (mSimpleFlag.equals("0")) {
				/**
				 * -------------------------------------------------------------BEG
				 * No.2.1 计算审核权限
				 * -------------------------------------------------------------
				 */
				/**
				 * zhangzheng 2009-02-11 
				 * 权限管理未定,暂时封住一段逻辑
				 */
				LLClaimPopedomSetBL tLLClaimPopedomSetBL = new LLClaimPopedomSetBL();
				if (!tLLClaimPopedomSetBL.submitData(mInputData, "")) {
					// @@错误处理
					CError.buildErr(this, "计算审核权限失败,"+tLLClaimPopedomSetBL.mErrors.getLastError());
					return false;
				} else {
					logger.debug("-----start Service getData from LLClaimPopedomSetBL");
					VData tVData = new VData();
					MMap tMap = new MMap();
					tVData = tLLClaimPopedomSetBL.getResult();
					tMap = (MMap) tVData.getObjectByObjectName("MMap", 0);
					TransferData tTransferData = new TransferData();
					tTransferData = (TransferData) tVData
							.getObjectByObjectName("TransferData", 0);
					mMaxLevel = (String) tTransferData
							.getValueByName("Popedom");
					mComFlag = (String) tTransferData.getValueByName("ComFlag"); // 2005-8-14
					
					mUser = (String) tTransferData.getValueByName("User");
					logger.debug("确定审核人是" + mUser);
																					// 14:52
					// 为公共传输数据集合中添加工作流下一节点属性字段数据
					mTransferData.setNameAndValue("Popedom", mMaxLevel); // 审核模块权限
					mTransferData.setNameAndValue("ComFlag", mComFlag); // 权限越界标志
					mTransferData.setNameAndValue("Operator", mUser); // 下一节点操作人
					mTransferData.removeByName("Auditer");
				    mTransferData.setNameAndValue("Auditer", mUser);  //审核操作人,为审批不通过时返回个人工作队列用
					map.add(tMap);
				}
				// --------------------------------------------------------------END

				//只有普通案件才执行保单挂起
				if(mLLRegisterSchema.getRgtState().trim().equals("11"))
				{
					/**
					 * -----------------------------------------------------------BEG
					 * No.2.2 保单挂起（包含死亡案件保单挂起） 1 在立案通过时，与赔案相关的保单全部挂起。 2
					 * 在立案通过时，出险人作为被保人，与赔案无关的保单全部挂起。 注：作为第二被保人的合同不被挂起
					 * -----------------------------------------------------------
					 */
					LLLcContHangUpBL tLLLcContHangUpBL = new LLLcContHangUpBL();
					if (!tLLLcContHangUpBL.submitData(mInputData, "")) {
						// @@错误处理
						CError.buildErr(this, "保单挂起处理失败,"+tLLLcContHangUpBL.mErrors.getLastError());
						return false;
					} 
					else 
					{
						logger.debug("-----start Service getData from LLLcContHangUpBL");
						VData tempVData = new VData();
						MMap tMap = new MMap();
						tempVData = tLLLcContHangUpBL.getResult();
						tMap = (MMap) tempVData.getObjectByObjectName("MMap", 0);
						map.add(tMap);
					}
				}
			}

			/**
			 * -----------------------------------------------------------------BEG
			 * No.3： 更改状态 所需变量： 更新变量：
			 * -----------------------------------------------------------------
			 */
			// 更改赔案状态为审核,无论是简易案件还是审核
			String sql1 = " update LLRegister set ClmState = '30',RgtConclusion='01' where"
					+ " RgtNo = '" + mClmNo + "'";
			map.put(sql1, "UPDATE");
			
			String sql3 = " update llclaim set ClmState = '30' where"
					+ " clmno = '" + mClmNo + "'";
			map.put(sql3, "UPDATE");
			
			String sqlP = " update llclaimpolicy set ClmState = '30' where"
				+ " clmno = '" + mClmNo + "'";
			map.put(sqlP, "UPDATE");

			// 更改案件类型
			if (mSimpleFlag.equals("0")) {
				// 普通案件
				String sql2 = " update LLRegister set RgtState = '"+mTransferData.getValueByName("RgtState")+"',RgtConclusion='01' where "
						+ " RgtNo = '" + mClmNo + "'" + " and RgtState is null";
				map.put(sql2, "UPDATE");
			} 
			else 
			{
				// 普通案件
				String sql2 = " update LLRegister set RgtState = '01',RgtConclusion='01' where"
						+ " RgtNo = '" + mClmNo + "'";
				map.put(sql2, "UPDATE");

				// //更改分案
				// String sql4 = " update llcase set rgttype = '01' where"
				// + " caseno = '" + mClmNo + "'";
				// map.put(sql4, "UPDATE");

			}
			// ----------------------------------------------------------------------END
		} else {
			if 
			(mRgtConclusion.equals("02")) 
			{// Modify by zhaorx 2006-11-30
				
				String tSQLF = " update llregister set clmstate='70',EndCaseFlag='1',EndCaseDate='"
						+ CurrentDate + "' " + " where rgtno='" + mClmNo + "'";
				map.put(tSQLF, "UPDATE");
				
				String tSQLS = " update llclaim set clmstate='70',EndCaseFlag='1',EndCaseDate='"
						+ CurrentDate + "' " + " where clmno='" + mClmNo + "'";
				map.put(tSQLS, "UPDATE");
				
				String tSQLP = " update llclaimpolicy set clmstate='70',EndCaseDate='"
					+ CurrentDate + "' " + " where clmno='" + mClmNo + "'";
				map.put(tSQLP, "UPDATE");
				
				logger.debug("------立案结论为不予立案的" + mClmNo
						+ "赔案，立案确认时赔案状态修改为70成功！----------------");
			}
			// 提交不予立案处理类
			// 解除保单挂起
			LLLcContReleaseBL tLLLcContReleaseBL = new LLLcContReleaseBL();
			if (!tLLLcContReleaseBL.submitData(mInputData, "")) {
				// @@错误处理
				CError.buildErr(this, "解除保单挂起失败,"+tLLLcContReleaseBL.mErrors.getLastError());
				return false;
			} else {
				VData tempVData = new VData();
				tempVData = tLLLcContReleaseBL.getResult();
				MMap tMap = new MMap();
				tMap = (MMap) tempVData.getObjectByObjectName("MMap", 0);
				map.add(tMap);
			}
		}
		return true;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		mResult.add(map);
		return true;
	}

	/**
	 * 为公共传输数据集合中添加工作流下一节点属性字段数据
	 * 
	 * @return true or false
	 */
	private boolean prepareTransferData() {
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public TransferData getReturnTransferData() {
		return mTransferData;
	}

	public CErrors getErrors() {
		return mErrors;
	}

}
