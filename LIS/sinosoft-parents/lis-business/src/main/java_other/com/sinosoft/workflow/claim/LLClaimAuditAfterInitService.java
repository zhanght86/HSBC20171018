package com.sinosoft.workflow.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.claim.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.LJSGetSet;
import com.sinosoft.utility.*;
import com.sinosoft.workflowengine.AfterInitService;

/**
 * <p>
 * Title: 审核确认服务类
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author zl
 * @version 1.0
 */

public class LLClaimAuditAfterInitService implements AfterInitService {
private static Logger logger = Logger.getLogger(LLClaimAuditAfterInitService.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 提交数据容器 */
	private MMap map = new MMap();
	// private VData mIputData = new VData();
	private TransferData mTransferData = new TransferData();

	/** 数据操作字符串 */
	private String mOperater;
	private String mManageCom;
	private String mOperate;
	private String mMissionID;
	private String mSubMissionID;
	private String mClmNo = "";
	private String mIsRunBL = "";
	private String mMaxLevel = ""; // 最高权限
	private String mComFlag = "0"; // 权限越界标志
	private String mUser = ""; // 主任核赔人

	public LLClaimAuditAfterInitService() {
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
//		if (!checkData())
//			return false;

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
		
		/**
		 * 2009-06-25 zhangzheng 
		 * 校验受益人分配产生的应付子表和总表金额必须保持一致
		 */
		LJSGetSet tLJSGetSet=new LJSGetSet();
		LJSGetDB tLJSGetDB= new LJSGetDB();
		tLJSGetDB.setOtherNo(mClmNo);
		tLJSGetSet=tLJSGetDB.query();
		
		String sql="";
		SSRS tSSRS= null;
		ExeSQL tExeSQL = new ExeSQL();
		
		if(tLJSGetSet.size()==0)
		{
			CError.buildErr(this, "请进行受益人分配!");
			tExeSQL=null;
			tLJSGetDB=null;
			tLJSGetSet=null;
			return false;
		}
		
		for(int i=1;i<=tLJSGetSet.size();i++)
		{
			sql = "select sum(pay) from ljsgetclaim where getnoticeno='"+"?getnoticeno?"+"'";
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(sql);
			sqlbv.put("getnoticeno", tLJSGetSet.get(i).getGetNoticeNo());
			tSSRS = new SSRS();
			
			tSSRS = tExeSQL.execSQL(sqlbv);
			
			if(tSSRS.getMaxRow()==0)
			{
				tSSRS=null;
				sql=null;
				CError.buildErr(this, "给付通知书号:"+tLJSGetSet.get(i).getGetNoticeNo()+"查询不到子表的应付信息,不能审核确认,请先查明原因!");
				return false;
			}
			
			if(tLJSGetSet.get(i).getSumGetMoney()!=Double.parseDouble(tSSRS.GetText(1,1)))
			{
				tSSRS=null;
				sql=null;
				CError.buildErr(this, "给付通知书号:"+tLJSGetSet.get(i).getGetNoticeNo()+"的主子表金额不一致,不能审核确认,请先查明原因!");
				return false;
			}
			
			tSSRS=null;
			sql=null;
		}
		
		tLJSGetSet=null;
		tExeSQL=null;
		tLJSGetDB=null;
		
		
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
		mIsRunBL = (String) mTransferData.getValueByName("IsRunBL"); // 是否运行BL

		mInputData = cInputData;
		mOperate = cOperate;

		if (mGlobalInput == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据失败!");
			return false;
		}

		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if (mOperater == null || mOperater.trim().equals("")) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据Operater失败!");
			return false;
		}

		// 获得当前工作任务的任务ID
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		if (mMissionID == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据Operater失败!");
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，调用BL进行逻辑处理，返回处理完数据 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {

		/*******************合并预付处理*******************************/
		String tBudgetFlag = (String) mTransferData.getValueByName("BudgetFlag"); 
		String tPrepayFlag = (String) mTransferData.getValueByName("PrepayFlag"); // 预付标志
		// 查询审核结论
		String strSQL = "";
		strSQL = " select AuditConclusion from LLClaimUWMain where "
							+ " ClmNO='" + "?ClmNO?" + "'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(strSQL);
		sqlbv1.put("ClmNO", mClmNo);
		ExeSQL exesql = new ExeSQL();
		String tResult = exesql.getOneValue(sqlbv1);
		//增加审核结论要素，用于工作流流转
		mTransferData.setNameAndValue("AuditConclusion", tResult);
		if (tResult.equals("0") || tResult.equals("1")|| tBudgetFlag.equals("1")) {
			if(tResult.equals("0")|| tPrepayFlag.equals("1") || tResult.equals("1"))//如果是给付则进行受益人的处理
			{
				TransferData tTransferData = new TransferData();
				tTransferData.setNameAndValue("RptNo", mClmNo);
				tTransferData.setNameAndValue("PrepayFlag", tPrepayFlag); // 是否预付，0-非预付，1-预付
				VData mVData = new VData();
				mVData.add(mGlobalInput);
				mVData.add(tTransferData);
				LLBnfGatherBL tLLBnfGatherBL = new LLBnfGatherBL();
				if (!tLLBnfGatherBL.submitData(mVData, "")) {
					 //@@错误处理
					CError.buildErr(this, "预付处理失败,"+tLLBnfGatherBL.mErrors.getLastError());
					return false;
				}else{
					logger.debug("汇总受益人信息成功----------------------------");
					VData tempVData = new VData();
					tempVData = tLLBnfGatherBL.getResult();
					if ((tempVData != null) && (tempVData.size() > 0)) {
						MMap tmap = new MMap();
						tmap = (MMap) tempVData.getObjectByObjectName("MMap", 0);
						map.add(tmap);
					}
				}
			}
		}
		/********************end**************************************/
		// 计算审批权限
		LLClaimPopedomSetBL tLLClaimPopedomSetBL = new LLClaimPopedomSetBL();
		if (!tLLClaimPopedomSetBL.submitData(mInputData, "")) {
			// @@错误处理
			//CError.buildErr(this, "确定审批人失败,"+tLLClaimPopedomSetBL.mErrors.getLastError());
			//return false;
		} 
		else
		{
			logger.debug("-----start Service getData from LLClaimPopedomSetBL");
			VData tVData = new VData();
			MMap tMap = new MMap();
			tVData = tLLClaimPopedomSetBL.getResult();
			tMap = (MMap) tVData.getObjectByObjectName("MMap", 0);
			TransferData tTransferData = new TransferData();
			tTransferData = (TransferData) tVData.getObjectByObjectName(
					"TransferData", 0);
			mMaxLevel = (String) tTransferData.getValueByName("Popedom");
			mComFlag = (String) tTransferData.getValueByName("ComFlag"); // 2005-8-14
																			// 14:52
			//tongmeng 2012-05-07 为了演示需要,本处写死审批用户为002
			mUser = "002";
			//mUser = (String) tTransferData.getValueByName("User");
			logger.debug("确定审批人是" + mUser);

			// 为公共传输数据集合中添加工作流下一节点属性字段数据
			mTransferData.setNameAndValue("Popedom", mMaxLevel); // 案件审批级别
			mTransferData.setNameAndValue("ComFlag", mComFlag); // 权限越界标志
			mTransferData.setNameAndValue("ExamPer", mUser); // 审批人

			map.add(tMap);


//			// 如果有预付，则审核通过后直接打到主任核赔人工作队列
//			String tFlag = (String) mTransferData
//					.getValueByName("PrepayFlag");
//
//			if (tFlag.equals("1") && mComFlag.equals("0")) // 有预付且不跨级
//			{
//				LLClaimPubFunBL tPub = new LLClaimPubFunBL();
//				mUser = tPub.getClaimUserMain(mGlobalInput.ManageCom);
//			}
		}
		
		
		if (mIsRunBL.equals("1")) {
			logger.debug("----------Service dealData BEGIN----------");

			// 提交业务处理
			LLClaimAuditAfterDealBL tLLClaimAuditAfterDealBL = new LLClaimAuditAfterDealBL();
			if (!tLLClaimAuditAfterDealBL.submitData(mInputData, "INSERT")) {
				// @@错误处理
				CError.buildErr(this, "审核确认失败,"+tLLClaimAuditAfterDealBL.mErrors.getLastError());
				mResult.clear();
				mInputData = null;
				return false;
			} else {
				VData tVData = new VData();
				MMap tMap = new MMap();
				tVData = tLLClaimAuditAfterDealBL.getResult();
				tMap = (MMap) tVData.getObjectByObjectName("MMap", 0);
				map.add(tMap);
				// 赔案状态已在LLClaimAuditAfterDealBL中处理
			}
			
		} 
		else {
			
			logger.debug("------#生成预付节点,哈哈哈哈哈哈#-----");
			
			/**
			 * 2008-12-27 zhangzheng
			 * 新增预付管理状态,ClmState:35
			 * 更改赔案状态为预付
			 */
			String sql1 = " update LLRegister set ClmState = '35' where"
					+ " RgtNo = '" + "?clmno?" + "'";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(sql1);
			sqlbv2.put("clmno", mClmNo);
			map.put(sqlbv2, "UPDATE");
			
			String sql2 = " update llclaim set ClmState = '35' where"
					+ " clmno = '" + "?clmno?" + "'";
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(sql2);
			sqlbv3.put("clmno", mClmNo);
			map.put(sqlbv3, "UPDATE");
			
			String sql3 = " update llclaimpolicy set ClmState = '35' where"
				+ " clmno = '" + "?clmno?" + "'";
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(sql3);
			sqlbv4.put("clmno", mClmNo);
			map.put(sqlbv4, "UPDATE");
		}

		// 为公共传输数据集合中添加工作流下一节点属性字段数据
//		mTransferData.setNameAndValue("Operator", mUser); // 下一节点操作人
		mTransferData.setNameAndValue("DefaultOperator", mUser); // 下一节点操作人

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
	 * @return
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
