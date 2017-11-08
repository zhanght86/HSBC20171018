package com.sinosoft.workflow.claimgrp;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;

import com.sinosoft.workflowengine.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.claimgrp.LLClaimPubFunBL;
import com.sinosoft.lis.claimgrp.LLClaimSimpleSetBL;
import com.sinosoft.lis.claimgrp.LLLcContReleaseBL;
import com.sinosoft.lis.claimgrp.LLReportBL;
import com.sinosoft.lis.claimgrp.LLClaimAuditBL;
import com.sinosoft.lis.claimgrp.LLClaimAuditAfterDealBL;
import com.sinosoft.lis.claimgrp.LLClaimPopedomSetBL;

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

	private LLClaimUWMainSchema mLLClaimUWMainSchema = new LLClaimUWMainSchema();

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
	private String mUser = ""; // 审批人
	private String mAuditConclusion = "";

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
		// 校验是否为简易案件，团体理赔只有为简易案件时审核结论才能为0
		LLClaimUWMainSchema mLLClaimUWMainSchema = new LLClaimUWMainSchema();
		LLClaimUWMainDB mLLClaimUWMainDB = new LLClaimUWMainDB();
		mLLClaimUWMainDB.setClmNo(mClmNo);
		if (!mLLClaimUWMainDB.getInfo()) {
			CError.buildErr(this, "查询案件核赔表出错,赔案表无此立案信息!");
			return false;
		} else {
			mLLClaimUWMainSchema = mLLClaimUWMainDB.getSchema();
		}
		
		double tRealPay=0.0;

		if ("0".equals(mLLClaimUWMainSchema.getAuditConclusion())) {// 0-给付通过
			

			LLClaimSimpleSetBL tLLClaimSimpleSetBL = new LLClaimSimpleSetBL();
			if (!tLLClaimSimpleSetBL.submitData(mInputData, "")) {
				CError.buildErr(this, "判断案件是否为简易案件失败," + tLLClaimSimpleSetBL.mErrors.getLastError());
				return false;
			} else {
				logger.debug("-----start Service getData from LLClaimSimpleSetBL");
				VData tVData = new VData();
				tVData = tLLClaimSimpleSetBL.getResult();

				TransferData tTransferData = new TransferData();
				tTransferData = (TransferData) tVData.getObjectByObjectName("TransferData", 0);

				String mSimpleFlag = (String) tTransferData.getValueByName("SimpleFlag");
				logger.debug("案件:" + mClmNo + "判断简易案件条件结果:" + mSimpleFlag);

				// 更新立案表的案件类型:01-简易案件
				if ("1".equals(mSimpleFlag)) {
					map.put("update LLRegister a set a.rgtstate = '01' where a.rgtno = '" + mClmNo + "'",
							"UPDATE");
				} else {
					CError.buildErr(this, "该案件不是简易案件，审核结论不能为给付或部分给付");
					return false;
				}
			}
		} 
		else 
		{
			
			if ("5".equals(mLLClaimUWMainSchema.getAuditConclusion())) {// 5-案件回退
				
				map.put("delete from llbnf where  clmno = '" + mClmNo + "'", "DELETE");
				map.put("delete from llbnfGather where  clmno = '" + mClmNo + "'", "DELETE");
				map.put("delete from ljsget where  otherno = '" + mClmNo + "'", "DELETE");
				map.put("delete from ljsgetclaim where  otherno = '" + mClmNo + "'", "DELETE");
			}
		}
		
		// 赔案表,取核赔赔付金额RealPay
		LLClaimDB tLLClaimDB = new LLClaimDB();
		tLLClaimDB.setClmNo(mClmNo);
		if (!tLLClaimDB.getInfo()) {
			CError.buildErr(this, "查询赔案表出错,赔案表无此立案信息!");
			return false;
		} else {
			tRealPay = tLLClaimDB.getRealPay();
			mTransferData.setNameAndValue("adjpay", Double.toString(tRealPay));
		}
		
		/**
		 * 2009-06-25 zhangzheng 
		 * 校验受益人分配产生的应付子表和总表金额必须保持一致
		 */
		/**LJSGetSet tLJSGetSet=new LJSGetSet();
		LJSGetDB tLJSGetDB= new LJSGetDB();
		tLJSGetDB.setOtherNo(mClmNo);
		tLJSGetSet=tLJSGetDB.query();
		
		String sql="";
		SSRS tSSRS= null;
		ExeSQL tExeSQL = new ExeSQL();
		double sumljsPay=0.0;
		
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
			sql = "select sum(pay) from ljsgetclaim where getnoticeno='"+tLJSGetSet.get(i).getGetNoticeNo()+"'";
			
			tSSRS = new SSRS();
			
			tSSRS = tExeSQL.execSQL(sql);
			
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
			
			sumljsPay= sumljsPay + tLJSGetSet.get(i).getSumGetMoney();
			
			tSSRS=null;
			sql=null;
		}
		
		tExeSQL=null;
		tLJSGetDB=null;
		tLJSGetSet=null;
		

		sumljsPay = Double.parseDouble(new DecimalFormat("0.00")
				.format(sumljsPay));*/
		/**
		 * 2009-06-29 zhangzheng 
		 * 受益人分配金额必须和理算金额保持一致的
		 */
		/**logger.debug("案件:"+mClmNo+":的理算金额:"+tRealPay);
		logger.debug("案件:"+mClmNo+":的应付子表总金额:"+sumljsPay);
		
		if(tRealPay!=sumljsPay)
		{	
			CError.buildErr(this, "案件理算金额和应付金额不一致,不能审核确认,请查明原因!");
			return false;
		}
		*/

		
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		// 从输入数据中得到所有对象
		// 获得全局公共数据
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
		mTransferData = (TransferData) cInputData.getObjectByObjectName("TransferData", 0);
		mClmNo = (String) mTransferData.getValueByName("RptNo");
		mIsRunBL = (String) mTransferData.getValueByName("IsRunBL"); // 是否运行BL
		mAuditConclusion = (String) mTransferData.getValueByName("AuditConclusion"); // 是否运行BL

		mInputData = cInputData;
		mOperate = cOperate;

		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "AskUWConfirmAfterInitService";
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
			tError.moduleName = "LLClaimAuditAfterInitService";
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
			tError.moduleName = "LLClaimAuditAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		
		if (mAuditConclusion == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "LLClaimAuditAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中AuditConclusion失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，调用BL进行逻辑处理，返回处理完数据 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		if (mIsRunBL.equals("1")) {
			//查询ldcode描述表确认目前双岗审批流程是否有效 1-有效  
//			String tSql = "select code from ldcode where codetype='lldsflag'";
//			ExeSQL tExeSQL = new ExeSQL();
//			String tResult = tExeSQL.getOneValue(tSql);
//			if("1".equals(tResult)){
//				
//			}else{
				if(!commonDeal()){
					CError.buildErr(this, "执行afterInit->commonDeal()函数失败!");
					return false;
				}
//			}
			logger.debug("----------Service dealData BEGIN----------");

			
		} else {
			logger.debug("------#生成预付节点,不执行BL#-----");
			return true;
		}
		return true;
	}

	/**
	 * 针对死亡案件更改死亡日期和标志 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean dealDeath() {
		LLCaseDB tLLCaseDB = new LLCaseDB();
		tLLCaseDB.setCaseNo(mClmNo);
		LLCaseSet tLLCaseSet = tLLCaseDB.query();
		if (tLLCaseSet == null && tLLCaseSet.size() < 1) {
			CError tError = new CError();
			tError.moduleName = "LLClaimConfirmAfterInitService";
			tError.functionName = "dealData";
			tError.errorMessage = "查询分案信息失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		for (int i = 1; i <= tLLCaseSet.size(); i++) {
			String tCNo = tLLCaseSet.get(i).getCustomerNo();
			LLAppClaimReasonDB tLLAppClaimReasonDB = new LLAppClaimReasonDB();
			tLLAppClaimReasonDB.setCaseNo(mClmNo);
			tLLAppClaimReasonDB.setRgtNo(mClmNo);
			tLLAppClaimReasonDB.setCustomerNo(tCNo);
			LLAppClaimReasonSet tLLAppClaimReasonSet = tLLAppClaimReasonDB.query();
			if (tLLAppClaimReasonSet == null && tLLAppClaimReasonSet.size() < 1) {
				CError tError = new CError();
				tError.moduleName = "LLClaimConfirmAfterInitService";
				tError.functionName = "dealData";
				tError.errorMessage = "查询赔案理赔类型信息失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			for (int j = 1; j <= tLLAppClaimReasonSet.size(); j++) {
				String tCode = tLLAppClaimReasonSet.get(j).getReasonCode().substring(2, 3);
				if (tCode.equals("02")) // 死亡
				{
					// 更改立案分案表
					String sql3 = " update llcase set DieFlag = '1'," + " DeathDate = AccDate where"
							+ " CaseNo = '" + mClmNo + "'" + " and CustomerNo = '" + tCNo + "'";
					map.put(sql3, "UPDATE");
					// 更改报案分案表
					String sql4 = " update LLSubReport set DieFlag = '1'," + " DieDate = AccDate where"
							+ " CaseNo = '" + mClmNo + "'" + " and CustomerNo = '" + tCNo + "'";
					map.put(sql4, "UPDATE");
					// 更改客户表
					String sql5 = " update LDPerson set DeathDate = to_date('"
							+ tLLCaseSet.get(i).getAccDate() + "','yyyy-mm-dd') where" + " CustomerNo = '"
							+ tCNo + "'";
					map.put(sql5, "UPDATE");

					break;
				}
			}
		}
		return true;
	}
	
	/**
	 * 正常流程(不需要判断是否走双岗审批)
	 * */
	private boolean commonDeal(){
		/**
		 * zhangzheng 2009-02-10 这些处理工作在保存审核结论时已经处理了,这里不再处理
		 */

		//
		// //提交业务处理
		// LLClaimAuditAfterDealBL tLLClaimAuditAfterDealBL = new
		// LLClaimAuditAfterDealBL();
		// if (!tLLClaimAuditAfterDealBL.submitData(mInputData, "INSERT"))
		// {
		// // @@错误处理
		// this.mErrors.copyAllErrors(tLLClaimAuditAfterDealBL.mErrors);
		// CError tError = new CError();
		// tError.moduleName = "LLClaimAuditAfterInitService";
		// tError.functionName = "submitData";
		// tError.errorMessage = "数据提交失败!";
		// this.mErrors.addOneError(tError);
		// mResult.clear();
		// mInputData = null;
		// return false;
		// }
		// else
		// {
		// VData tVData = new VData();
		// MMap tMap = new MMap();
		// tVData = tLLClaimAuditAfterDealBL.getResult();
		// tMap = (MMap) tVData.getObjectByObjectName("MMap", 0);
		// map.add(tMap);
		// //赔案状态已在LLClaimAuditAfterDealBL中处理
		// }
		// 计算审批权限
		//团险理赔计算审核权限  09-04-09
		//只有结论为4-审批管理时才确定审核人  09-04-20
		if("4".equals(mAuditConclusion)||"1".equals(mAuditConclusion)){
			 LLClaimPopedomSetBL tLLClaimPopedomSetBL = new LLClaimPopedomSetBL();
			 if (!tLLClaimPopedomSetBL.submitData(mInputData, "")) {
				 // @@错误处理
				 this.mErrors.copyAllErrors(tLLClaimPopedomSetBL.mErrors);
				 CError tError = new CError();
				 tError.moduleName = "LLClaimRegisterAfterInitService";
				 tError.functionName = "dealData";
				 tError.errorMessage = "数据提交失败!";
				 this.mErrors.addOneError(tError);
				 return false;
			 } else {
				 logger.debug("-----start Service getData from LLClaimPopedomSetBL");
				 VData tVData = new VData();
				 MMap tMap = new MMap();
				 tVData = tLLClaimPopedomSetBL.getResult();
				 tMap = (MMap) tVData.getObjectByObjectName("MMap", 0);
				 TransferData tTransferData = new TransferData();
				 tTransferData = (TransferData) tVData.getObjectByObjectName(
				 "TransferData", 0);
				 mMaxLevel = (String) tTransferData.getValueByName("Popedom");
				 mComFlag = (String) tTransferData.getValueByName("ComFlag");
				 
				 mUser = (String) tTransferData.getValueByName("User");
				 logger.debug("确定审批人是" + mUser);
				 //2005-8-14 14:52
				 //为公共传输数据集合中添加工作流下一节点属性字段数据
				 // mTransferData.setNameAndValue("Popedom", mMaxLevel); //审核模块权限
				 // mTransferData.setNameAndValue("ComFlag", mComFlag); //权限越界标志
				 mTransferData.setNameAndValue("Popedom", "C3"); //审核模块权限
				 mTransferData.setNameAndValue("ComFlag", "0"); //权限越界标志
				 mTransferData.setNameAndValue("Operator", mUser); //为工作流准备属性字段用于指定审批人
				 map.add(tMap);
			 }
		}
		// mTransferData.setNameAndValue("Popedom", "C3"); //审核模块权限
		// mTransferData.setNameAndValue("ComFlag", "0"); //权限越界标志
		/**
		 * 2009-02-11 zhangzheng 增加对审核结案直接流转到理赔结案的处理
		 * 
		 */

		// 案件核赔表
		LLClaimUWMainDB tLLClaimUWMainDB = new LLClaimUWMainDB();
		tLLClaimUWMainDB.setClmNo(mClmNo);
		if (tLLClaimUWMainDB.getInfo()) {

			if (tLLClaimUWMainDB.getAuditConclusion().equals("0")
					|| tLLClaimUWMainDB.getAuditConclusion().equals("1")) {
				// 更改赔案状态为结案
				if (tLLClaimUWMainDB.getAuditConclusion().equals("1"))
				 {
					String sql1 = " update LLRegister set ClmState = '40' ,EndCaseFlag = '1' , EndCaseDate = '"
						+ PubFun.getCurrentDate() + "' where" + " RgtNo = '" + mClmNo + "'";
				    map.put(sql1, "UPDATE");

				    String sql2 = " update llclaim set ClmState = '40' , EndCaseDate = '"
						+ PubFun.getCurrentDate() + "',ClmUWer = '" + mOperater + "'"
						+ " where clmno = '" + mClmNo + "'";
				    map.put(sql2, "UPDATE");

				    String sql33 = " update llclaimpolicy set ClmState = '40' , EndCaseDate = '"
						+ PubFun.getCurrentDate() + "' " + " where clmno = '" + mClmNo + "'";
				    map.put(sql33, "UPDATE");
						}
						
				else if (tLLClaimUWMainDB.getAuditConclusion().equals("0"))
				 {
					
				    String sql1 = " update LLRegister set ClmState = '50' ,EndCaseFlag = '1' , EndCaseDate = '"
						+ PubFun.getCurrentDate() + "' where" + " RgtNo = '" + mClmNo + "'";
				    map.put(sql1, "UPDATE");

				    String sql2 = " update llclaim set ClmState = '50' , EndCaseDate = '"
						+ PubFun.getCurrentDate() + "',ClmUWer = '" + mOperater + "'"
						+ " where clmno = '" + mClmNo + "'";
				    map.put(sql2, "UPDATE");

				    String sql33 = " update llclaimpolicy set ClmState = '50' , EndCaseDate = '"
						+ PubFun.getCurrentDate() + "' " + " where clmno = '" + mClmNo + "'";
				     map.put(sql33, "UPDATE");
				
				 }
				
				// 删除账单表帐户理赔生成的mainfeeno = '0000000000'的数据
				String sql7 = "delete llcasereceipt where mainfeeno = '0000000000' and clmno = '"
						+ mClmNo + "'";
				String sql8 = "delete llfeemain where mainfeeno = '0000000000' and clmno = '" + mClmNo
						+ "'";
				map.put(sql7, "DELETE");
				map.put(sql8, "DELETE");

				// 解除保单挂起
				LLLcContReleaseBL tLLLcContReleaseBL = new LLLcContReleaseBL();
				if (!tLLLcContReleaseBL.submitData(mInputData, "")) {
					// @@错误处理
					this.mErrors.copyAllErrors(tLLLcContReleaseBL.mErrors);
					CError tError = new CError();
					tError.moduleName = "LLClaimConfirmAfterInitService";
					tError.functionName = "dealData";
					tError.errorMessage = "解除保单挂起失败!";
					this.mErrors.addOneError(tError);
					return false;
				} else {
					VData tempVData = new VData();
					tempVData = tLLLcContReleaseBL.getResult();
					MMap tMap = new MMap();
					tMap = (MMap) tempVData.getObjectByObjectName("MMap", 0);
					map.add(tMap);
				}

				// 更改死亡标志
				if (!dealDeath()) {
					return false;
				}

			} 
			else if (tLLClaimUWMainDB.getAuditConclusion() != null
					&& (tLLClaimUWMainDB.getAuditConclusion().equals("2") || tLLClaimUWMainDB
							.getAuditConclusion().equals("3"))) {

				// 更改赔案状态为撤消
				String sql3 = " update LLRegister set ClmState = '70' , EndCaseFlag = '1', EndCaseDate = '"
						+ PubFun.getCurrentDate() + "'" // 撤消日期
						+ " where RgtNo = '" + mClmNo + "'";
				map.put(sql3, "UPDATE");

				String sql4 = " update llclaim set ClmState = '70' , EndCaseDate = '"
						+ PubFun.getCurrentDate() + "'" // 撤消日期
						+ " , GiveType = '" + tLLClaimUWMainDB.getAuditConclusion() + "'" // 赔付结论
						+ " , ClmUWer = '" + mGlobalInput.Operator + "'" // 理赔员
						+ " where clmno = '" + mClmNo + "'";
				map.put(sql4, "UPDATE");

				String sql5 = " update llclaimpolicy set ClmState = '70' , EndCaseDate = '"
						+ PubFun.getCurrentDate() + "'" // 撤消日期
						+ " , GiveType = '" + tLLClaimUWMainDB.getAuditConclusion() + "'" // 赔付结论
						+ " , ClmUWer = '" + mGlobalInput.Operator + "'" // 理赔员
						+ " where clmno = '" + mClmNo + "'";
				map.put(sql5, "UPDATE");

				// 解除保单挂起
				LLLcContReleaseBL tLLLcContReleaseBL = new LLLcContReleaseBL();
				if (!tLLLcContReleaseBL.submitData(mInputData, "")) {
					// @@错误处理
					this.mErrors.copyAllErrors(tLLLcContReleaseBL.mErrors);
					CError tError = new CError();
					tError.moduleName = "LLClaimConfirmAfterInitService";
					tError.functionName = "dealData";
					tError.errorMessage = "解除保单挂起失败!";
					this.mErrors.addOneError(tError);
					return false;
				} else {
					VData tempVData = new VData();
					tempVData = tLLLcContReleaseBL.getResult();
					MMap tMap = new MMap();
					tMap = (MMap) tempVData.getObjectByObjectName("MMap", 0);
					map.add(tMap);
				}

				// 更改死亡标志
				if (!dealDeath()) {
					return false;
				}

			} else if (tLLClaimUWMainDB.getAuditConclusion() != null
					&& tLLClaimUWMainDB.getAuditConclusion().equals("5")) {
				// map.put(mLLClaimUWMainSchema, "DELETE&INSERT");
				// 更改赔案状态为立案
				String sql3 = " update LLRegister set ClmState = '20'" + " where RgtNo = '" + mClmNo
						+ "'";
				map.put(sql3, "UPDATE");

				String sql4 = " update llclaim set ClmState = '20'" + " , GiveType = '"
						+ mLLClaimUWMainSchema.getAuditConclusion() + "'" // 赔付结论
						+ " , ClmUWer = '" + mGlobalInput.Operator + "'" // 理赔员
						+ " where clmno = '" + mClmNo + "'";
				map.put(sql4, "UPDATE");

				String sql5 = " update llclaimpolicy set ClmState = '20'" + " , GiveType = '"
						+ mLLClaimUWMainSchema.getAuditConclusion() + "'" // 赔付结论
						+ " , ClmUWer = '" + mGlobalInput.Operator + "'" // 理赔员
						+ " where clmno = '" + mClmNo + "'";
				map.put(sql4, "UPDATE");

				String sql6 = " update LLClaimUWMain set ClmDecision=nvl(ClmDecision,0)+1 "
						+ " where ClmNo = '" + mClmNo + "'";
				map.put(sql6, "UPDATE"); // ClmDecision存案件回退次数。
				
				String tUserSql = "select defaultoperator from lbmission where missionprop1='"
								+ mClmNo + "' and activityid='0000009015'";
				ExeSQL tExeSql = new ExeSQL();
				String tUser = tExeSql.getOneValue(tUserSql);
				logger.debug("审核案件回退确定的立案操作员是========> "+tUser+" <==========");
				mTransferData.setNameAndValue("Operator", tUser); //为工作流准备属性字段用于指定立案操作员

			}

			// 写入销售渠道统计表 2006-02-17 P.D
			if (tLLClaimUWMainDB.getAuditConclusion() != null
					&& !tLLClaimUWMainDB.getAuditConclusion().equals("5")
					&& !tLLClaimUWMainDB.getAuditConclusion().equals("4")
					&& !tLLClaimUWMainDB.getAuditConclusion().equals("6")) {
				LLClaimPubFunBL tLLClaimPubFunBL = new LLClaimPubFunBL();
				LLClaimSaleChnlSet tLLClaimSaleChnlSet = new LLClaimSaleChnlSet();
				tLLClaimSaleChnlSet = tLLClaimPubFunBL.insertLLClaimSaleChnl(mClmNo,
						mGlobalInput.Operator, mGlobalInput.ManageCom);
				if (tLLClaimSaleChnlSet.size() > 0) {
					map.put(tLLClaimSaleChnlSet, "DELDTE&INSERT");
				} else {
					// @@错误处理
					this.mErrors.copyAllErrors(tLLClaimPubFunBL.mErrors);
					CError tError = new CError();
					tError.moduleName = "LLClaimConfirmAfterInitService";
					tError.functionName = "dealData";
					tError.errorMessage = "写入销售渠道统计表失败!";
					this.mErrors.addOneError(tError);
					return false;
				}
			}

			if (tLLClaimUWMainDB.getAuditConclusion() != null
					&& tLLClaimUWMainDB.getAuditConclusion().equals("4")) {
				String sql1 = " update LLRegister set ClmState = '40' where" + " RgtNo = '" + mClmNo
						+ "'";
				map.put(sql1, "UPDATE");

				String sql2 = " update llclaim set ClmState = '40' " + " where clmno = '" + mClmNo + "'";
				map.put(sql2, "UPDATE");

				String sql33 = " update llclaimpolicy set ClmState = '40'  " + " where clmno = '"
						+ mClmNo + "'";
				map.put(sql33, "UPDATE");
			}

			// 为公共传输数据集合中添加工作流下一节点属性字段数据,结案要回到立案机构
			String sql3 = " select MngCom from LLRegister where" + " RgtNo = '" + mClmNo + "'";
			ExeSQL tExeSQL = new ExeSQL();
			String tMngCom = tExeSQL.getOneValue(sql3);

			if (tMngCom != null) {
				mTransferData.removeByName("MngCom");
				mTransferData.setNameAndValue("MngCom", tMngCom);
			}
		} else {
			CError.buildErr(this, "查询不到案件" + mClmNo + "的审核结论");
		}

		return true;
	}
	
	private boolean DSDeal(){
		
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
