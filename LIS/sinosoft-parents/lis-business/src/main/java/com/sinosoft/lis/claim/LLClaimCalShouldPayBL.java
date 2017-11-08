package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LLAccidentDB;
import com.sinosoft.lis.db.LLAppealDB;
import com.sinosoft.lis.db.LLClaimDetailDB;
import com.sinosoft.lis.db.LLRegisterDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LLAppealSchema;
import com.sinosoft.lis.schema.LLBalanceSchema;
import com.sinosoft.lis.schema.LLClaimDetailSchema;
import com.sinosoft.lis.schema.LLReportSchema;
import com.sinosoft.lis.vschema.LLAccidentSet;
import com.sinosoft.lis.vschema.LLAppealSet;
import com.sinosoft.lis.vschema.LLBalanceSet;
import com.sinosoft.lis.vschema.LLClaimDetailSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:理算步骤八，产生赔付应付数据
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author 续涛，2005.05.24--2005.05.24
 * @version 1.0
 */
public class LLClaimCalShouldPayBL {
private static Logger logger = Logger.getLogger(LLClaimCalShouldPayBL.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private String mOperate;
	/** 数据操作字符串 */
	private MMap mMMap = new MMap();

	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();

	private GlobalInput mGlobalInput = new GlobalInput();
	/** 全局数据 */
	private TransferData mTransferData = new TransferData();
	private LLClaimPubFunBL mLLClaimPubFunBL = new LLClaimPubFunBL();

	private String mClmNo = ""; // 赔案号

	public LLClaimCalShouldPayBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {

		logger.debug("----------理算步骤八-----生成结算数据-----LLClaimCalShouldPayBL测试-----开始----------");
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		if (!dealData()) {
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}

		if (!pubSubmit()) {
			return false;
		}

		logger.debug("----------理算步骤八-----生成结算数据-----LLClaimCalShouldPayBL测试-----结束----------");
		return true;
	}

	/**
	 * 取传入参数信息
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData, String cOperate) {

		mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		// this.mAccNo = (String) mTransferData.getValueByName("AccNo"); //事件号
		// this.mAccDate = (String) mTransferData.getValueByName("AccDate");
		// //意外事故发生日期

		this.mClmNo = (String) mTransferData.getValueByName("ClmNo"); // 赔案号

		return true;
	}

	private boolean dealData() {

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 先删除已经计算过的赔付应付数据信息
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tSql1 = "delete from LLBalance where ClmNo='"
				+ "?clmno?"
				+ "' and substr(FeeOperationType,1,1) in ( 'A','C','D')  and substr(FeeOperationType,1,3) not in ('C05','C30')";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tSql1);
		sqlbv.put("clmno", this.mClmNo);
		mMMap.put(sqlbv, "DELETE");

		String tSql2 = "delete from LLBnf where ClmNo='" + "?clmno?"
				+ "' and BnfKind = 'A'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tSql2);
		sqlbv1.put("clmno", this.mClmNo);
		mMMap.put(sqlbv1, "DELETE");

		String tSql3 = "delete from LJSGet where OtherNo='" + "?clmno?"
				+ "' and  OtherNoType='5' ";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tSql3);
		sqlbv2.put("clmno", this.mClmNo);
		mMMap.put(sqlbv2, "DELETE");

		String tSql4 = "delete from LJSGetClaim where OtherNo='" + "?clmno?"
				+ "' and  OtherNoType='5' ";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(tSql4);
		sqlbv3.put("clmno", this.mClmNo);
		mMMap.put(sqlbv3, "DELETE");

		String tSql5 = "update LLClaim set ConBalFlag='0',ConDealFlag='0',CasePayType='0' where ClmNo='"
				+ "?clmno?" + "'";
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(tSql5);
		sqlbv4.put("clmno", this.mClmNo);
		mMMap.put(sqlbv4, "UPDATE");
		
		String tSql6 = "delete from LLBnfGather where ClmNo='" + "?clmno?"
		+ "' and BnfKind = 'A'";
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql(tSql6);
		sqlbv5.put("clmno", this.mClmNo);
		mMMap.put(sqlbv5, "DELETE");
		
		String tSql7 = "update llregister set CasePayType='0' where RgtNo='"
			+ "?clmno?" + "'";
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql(tSql7);
		sqlbv6.put("clmno", this.mClmNo);
		mMMap.put(sqlbv6, "UPDATE");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.0 得到结算数据
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (!dealBalanceData()) {
			return false;
		}

		return true;
	}

	/**
	 * 判断该赔案是正常案件,还是申诉案件
	 * 
	 * @return boolean
	 */
	private boolean dealBalanceData() {

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 判断该案件是否是申诉案件
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		String tSql = "";
		tSql = "select * from LLAppeal where 1=1 " + " and AppealNo = '"
				+ "?clmno?" + "'";
		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		sqlbv7.sql(tSql);
		sqlbv7.put("clmno", this.mClmNo);
		LLAppealDB tLLAppealDB = new LLAppealDB();
		LLAppealSet tLLAppealSet = tLLAppealDB.executeQuery(sqlbv7);

		if (tLLAppealDB.mErrors.needDealError() == true) {
			this.mErrors.addOneError("在申诉信息中查找到相关的数据发生错误，不能理算!");
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0
		 * 如果是申诉案件,执行申诉案件的理赔结算数据的生成
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (tLLAppealSet != null && tLLAppealSet.size() == 1) {
			this.mErrors.addOneError("在申诉信息中查找到相关的数据发生错误，不能理算!");
			return false;

			// if ( !getLLAppeal(tLLAppealSet.get(1)) )
			// {
			// return false;
			// }
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.0
		 * 如果是正常案件,执行正常案件的理赔结算数据的生成
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		else {

			if (!getLLBalance()) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 进行正常案件的理赔结算数据的生成
	 * 
	 * @return
	 */
	private boolean getLLBalance() {

		LLClaimDetailDB tLLClaimDetailDB = new LLClaimDetailDB();
		tLLClaimDetailDB.setClmNo(this.mClmNo);
		tLLClaimDetailDB.setGiveType("0"); // 赔付类型为给付
		LLClaimDetailSet tLLClaimDetailSet = tLLClaimDetailDB.query();
		if (tLLClaimDetailDB.mErrors.needDealError()) {
			this.mErrors.addOneError("生成赔案的数据时,没有找到赔案明细信息!!!");
			return false;
		}

		LLBalanceSet tLLBalanceSaveSet = new LLBalanceSet();

		for (int i = 1; i <= tLLClaimDetailSet.size(); i++) {

			//查询不到险种信息不生成结算数据
			LCPolSchema tLCPolSchema = mLLClaimPubFunBL.getLCPol(tLLClaimDetailSet.get(i).getPolNo());
			if (tLCPolSchema == null) {
				mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
				return false;
			}
			
			//获得对应的财务类型
			String tFeeFinaType=mLLClaimPubFunBL.getFeeFinaType(tLCPolSchema.getRiskCode(),
											tLLClaimDetailSet.get(i).getGetDutyKind());
			
			if(tFeeFinaType==null){
				CError.buildErr(this, "理算出错,无法确定财务类型!");
				return false;
			}

			LLBalanceSchema tLLBalanceSchema = setLLBalance("A", tLLClaimDetailSet.get(i).getGetDutyKind(),
					tFeeFinaType, tLLClaimDetailSet.get(i).getDutyCode(), tLLClaimDetailSet.get(i).getGetDutyKind(),
					      tLLClaimDetailSet.get(i).getGetDutyCode(), tLLClaimDetailSet.get(i).getRealPay(),tLLClaimDetailSet.get(i).getCurrency(),
					tLCPolSchema,tLLClaimDetailSet.get(i).getCustomerNo());
			tLLBalanceSaveSet.add(tLLBalanceSchema);
			
			tLCPolSchema=null;
			tFeeFinaType=null;
		}
		
		/**
		 * zhangzheng 2009-03-10
		 * 如果所有的保项信息都被拒付,则同样要生成结算信息
		 */
		if(tLLBalanceSaveSet.size()==0)
		{
			tLLClaimDetailDB.setClmNo(this.mClmNo);
			tLLClaimDetailDB.setGiveType("1"); // 赔付类型为拒付
			tLLClaimDetailSet = tLLClaimDetailDB.query();
			if (tLLClaimDetailDB.mErrors.needDealError()) {
				this.mErrors.addOneError("生成赔案的数据时,没有找到赔案明细信息!!!");
				return false;
			}
			
			for (int i = 1; i <= tLLClaimDetailSet.size(); i++) {

				//豁免责任不生成结算数据
				//zy 2010-01-04 MS规则为豁免拒付也需要进行受益人分配，即告知受益人给付为0
//				if (tLLClaimDetailSet.get(i).getGetDutyKind().substring(2, 3).equals("9")) {
//					continue;
//				}

				//给付金额为0时不生成结算数据 2009-03-10 zhangzheng 结算金额为0也要生成结算数据
//				if (tLLClaimDetailSet.get(i).getRealPay()== 0) {
//					continue;
//				}

				//查询不到险种信息不生成结算数据
				LCPolSchema tLCPolSchema = mLLClaimPubFunBL.getLCPol(tLLClaimDetailSet.get(i).getPolNo());
				if (tLCPolSchema == null) {
					mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
					return false;
				}
				
				//获得对应的财务类型
				String tFeeFinaType=mLLClaimPubFunBL.getFeeFinaType(tLCPolSchema.getRiskCode(),
												tLLClaimDetailSet.get(i).getGetDutyKind());
				
				if(tFeeFinaType==null){
					CError.buildErr(this, "理算出错,无法确定财务类型!");
					return false;
				}

				LLBalanceSchema tLLBalanceSchema = setLLBalance("A", tLLClaimDetailSet.get(i).getGetDutyKind(),
						tFeeFinaType, tLLClaimDetailSet.get(i).getDutyCode(), tLLClaimDetailSet.get(i).getGetDutyKind(),
						      tLLClaimDetailSet.get(i).getGetDutyCode(), tLLClaimDetailSet.get(i).getRealPay(),tLLClaimDetailSet.get(i).getCurrency(),
						tLCPolSchema,tLLClaimDetailSet.get(i).getCustomerNo());
				tLLBalanceSaveSet.add(tLLBalanceSchema);
				
				tLCPolSchema=null;
				tFeeFinaType=null;
			}
		}

		mMMap.put(tLLBalanceSaveSet, "DELETE&INSERT");
		return true;
	}
	
	



	/**
	 * 设置结算信息
	 * 
	 * @param pFeeOperationType:业务类型
	 * @param pSubFeeOperationType:子业务类型
	 * @param pFeeFinaType:财务类型
	 * @param pCalValue:计算金额
	 * @param pLCPolSchema:
	 * @return
	 */
	private LLBalanceSchema setLLBalance(String pFeeType, String pSubFeeType,
			String pFeeFinaType, String pDutyCode, String pGetDutyKind,
			String pGetDutyCode, double pCalValue, String pCurrency, LCPolSchema pLCPolSchema,String pCustomerNo) {

		LLBalanceSchema tLLBalanceSchema = new LLBalanceSchema();
		tLLBalanceSchema.setClmNo(this.mClmNo);
		tLLBalanceSchema.setFeeOperationType(pFeeType); // 业务类型
		tLLBalanceSchema.setSubFeeOperationType(pSubFeeType); // 子业务类型
		tLLBalanceSchema.setFeeFinaType(pFeeFinaType); // 财务类型

		tLLBalanceSchema.setBatNo("0"); // 批次号
		tLLBalanceSchema.setOtherNo("0"); // 其它号码
		tLLBalanceSchema.setOtherNoType("0"); // 财务类型

		tLLBalanceSchema.setGrpContNo(pLCPolSchema.getGrpContNo()); // 集体合同号码
		tLLBalanceSchema.setContNo(pLCPolSchema.getContNo()); // 合同号码
		tLLBalanceSchema.setGrpPolNo(pLCPolSchema.getGrpPolNo()); // 集体保单号码
		tLLBalanceSchema.setPolNo(pLCPolSchema.getPolNo()); // 保单号码

		tLLBalanceSchema.setDutyCode(pDutyCode); // 责任编码
		tLLBalanceSchema.setGetDutyKind(pGetDutyKind); // 给付责任类型
		tLLBalanceSchema.setGetDutyCode(pGetDutyCode); // 给付责任编码

		tLLBalanceSchema.setKindCode(pLCPolSchema.getKindCode()); // 险类编码
		tLLBalanceSchema.setRiskCode(pLCPolSchema.getRiskCode()); // 险种编码
		tLLBalanceSchema.setRiskVersion(pLCPolSchema.getRiskVersion()); // 险种版本

		tLLBalanceSchema.setSaleChnl(pLCPolSchema.getSaleChnl()); // 销售渠道
		tLLBalanceSchema.setAgentCode(pLCPolSchema.getAgentCode()); // 代理人编码
		tLLBalanceSchema.setAgentGroup(pLCPolSchema.getAgentGroup()); // 代理人组别

		tLLBalanceSchema.setGetDate(this.CurrentDate); // 给付日期
		tLLBalanceSchema.setCurrency(pCurrency);
		tLLBalanceSchema.setPay(pCalValue); // 赔付金额

		tLLBalanceSchema.setPayFlag("0"); // 支付标志,0未支付,1已支付
		tLLBalanceSchema.setState("0"); // 状态,0有效
		tLLBalanceSchema.setDealFlag("0"); // 处理标志,0未处理

		tLLBalanceSchema.setManageCom(pLCPolSchema.getManageCom()); // 管理机构
		tLLBalanceSchema.setAgentCom(pLCPolSchema.getAgentCom()); // 代理机构
		tLLBalanceSchema.setAgentType(pLCPolSchema.getAgentType()); // 代理机构内部分类

		tLLBalanceSchema.setOperator(mGlobalInput.Operator); // 操作员
		tLLBalanceSchema.setMakeDate(this.CurrentDate); // 入机日期
		tLLBalanceSchema.setMakeTime(this.CurrentTime); // 入机时间
		tLLBalanceSchema.setModifyDate(this.CurrentDate); // 入机日期
		tLLBalanceSchema.setModifyTime(this.CurrentTime); // 入机时间

		tLLBalanceSchema.setOriPay(pCalValue); // 原始金额
		tLLBalanceSchema.setCustomerNo(pCustomerNo);//客户号

		return tLLBalanceSchema;

	}

	/**
	 * 进行申诉案件的理赔结算数据的生成
	 * 
	 * @return
	 */
	private boolean getLLAppeal(LLAppealSchema pLLAppealSchema) {

		String tOldClmNo = pLLAppealSchema.getCaseNo(); /* 得到旧的赔案号 */

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 根据申诉的赔案号进行保单明细的查询
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		LLClaimDetailDB tLLClaimDetailNewDB = new LLClaimDetailDB();
		tLLClaimDetailNewDB.setClmNo(this.mClmNo);
		tLLClaimDetailNewDB.setGiveType("0"); // 赔付类型为给付
		LLClaimDetailSet tLLClaimDetailNewSet = tLLClaimDetailNewDB.query();
		if (tLLClaimDetailNewDB.mErrors.needDealError()) {
			this.mErrors.addOneError("没有查询到赔案明细信息!!!"
					+ tLLClaimDetailNewDB.mErrors.getError(0).errorMessage);
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 根据申诉的赔案号进行保单明细的循环处理
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLBalanceSet tLLBalanceSaveSet = new LLBalanceSet();
		for (int i = 1; i <= tLLClaimDetailNewSet.size(); i++) {
			LLClaimDetailSchema tLLClaimDetailNewSchema = tLLClaimDetailNewSet
					.get(i);

			String tPolNo = StrTool.cTrim(tLLClaimDetailNewSchema.getPolNo());
			String tDutyCode = StrTool.cTrim(tLLClaimDetailNewSchema
					.getDutyCode());
			String tGetDutyKind = StrTool.cTrim(tLLClaimDetailNewSchema
					.getGetDutyKind());
			String tGetDutyCode = StrTool.cTrim(tLLClaimDetailNewSchema
					.getGetDutyCode());

			if (tGetDutyKind.substring(2, 3).equals("9")) {
				continue;
			}

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.2
			 * 根据申诉的原赔案号进行原保项明细的查询
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */

			LLClaimDetailDB tLLClaimDetaiOldlDB = new LLClaimDetailDB();
			tLLClaimDetaiOldlDB.setClmNo(tOldClmNo);
			tLLClaimDetaiOldlDB.setPolNo(tPolNo);
			tLLClaimDetaiOldlDB.setDutyCode(tDutyCode);
			tLLClaimDetaiOldlDB.setGetDutyKind(tGetDutyKind);
			tLLClaimDetaiOldlDB.setGetDutyCode(tGetDutyCode);

			LLClaimDetailSet tLLClaimDetailOldSet = tLLClaimDetaiOldlDB.query();

			if (tLLClaimDetaiOldlDB.mErrors.needDealError()) {
				this.mErrors.addOneError("没有查询到原赔案保项信息,原赔案号为[" + tOldClmNo
						+ "]");
				return false;
			}

			if (tLLClaimDetailOldSet.size() != 1) {
				this.mErrors.addOneError("查询到原赔案保项信息不唯一,原赔案号为[" + tOldClmNo
						+ "]");
				return false;
			}

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.3 循环原保项明细信息
			 * 如果原保项为拒付，则将其金额置0 如果原保项为给付，则将其金额不变
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			double tNewPay = tLLClaimDetailNewSchema.getRealPay();
			double tOldPay = 0; // 原赔案的赔付金额

			for (int j = 1; j <= tLLClaimDetailOldSet.size(); j++) {
				LLClaimDetailSchema tLLClaimDetailSchema = tLLClaimDetailOldSet
						.get(j);
				String tGiveType = StrTool.cTrim(tLLClaimDetailSchema
						.getGiveType());
				if (tGiveType.equals("0")) {
					tOldPay = tOldPay + tLLClaimDetailSchema.getRealPay();
				}
			}

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.4
			 * 准备写入结算信息的金额，用于受益人分配
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			double tCalValue = tNewPay - tOldPay; //

			if (tCalValue < 0) {
				this.mErrors.addOneError("生成赔付金额为" + tCalValue
						+ "，申诉案件不能做减少赔款处理");
				return false;
			}

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.1 根据险种保单号得到LCPol信息
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			String tPol = tLLClaimDetailNewSchema.getPolNo();
			LCPolSchema tLCPolSchema = mLLClaimPubFunBL.getLCPol(tPol);
			if (tLCPolSchema == null) {
				return false;
			}

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.2 进行申诉案件结算信息的添加
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			LLBalanceSchema tLLBalanceSchema = setLLBalance("A", tGetDutyKind,
					"PK", tDutyCode, tGetDutyKind, tGetDutyCode, tCalValue,tLLClaimDetailNewSchema.getCurrency(),
					tLCPolSchema,tLLClaimDetailNewSchema.getCustomerNo());
			tLLBalanceSaveSet.add(tLLBalanceSchema);
		}

		mMMap.put(tLLBalanceSaveSet, "DELETE&INSERT");
		return true;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {

		mInputData.clear();
		mInputData.add(mMMap);
		return true;
	}

	/**
	 * 提交数据
	 * 
	 * @return
	 */
	private boolean pubSubmit() {
		// 进行数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimCalDutyKindBL";
			tError.functionName = "PubSubmit.submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;

	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {

		LLReportSchema tLLReportSchema = new LLReportSchema();

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 * 
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tClmNo = "90000001898";

		String tSql = "select * from LLAccident where AccNo in "
				+ " (select CaseRelaNo from LLCaseRela where CaseNo = '"
				+ "?clmno?" + "')";
		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		sqlbv8.sql(tSql);
		sqlbv8.put("clmno", tClmNo);
		LLAccidentDB tLLAccidentDB = new LLAccidentDB();
		LLAccidentSet tLLAccidentSet = tLLAccidentDB.executeQuery(sqlbv8);

		if (tLLAccidentDB.mErrors.needDealError()) {
			logger.debug("------------------------------------------------------");
			logger.debug("--LLClaimCalMatchBL.getPosEdorNo()--获取保全处理的批改生效日期发生错误!"
							+ tSql);
			logger.debug("------------------------------------------------------");
		}
		if (tLLAccidentSet.size() == 0) {
			logger.debug("----------------------无事故信息-----------------------");
			return;
		}

		String tAccNo = tLLAccidentSet.get(1).getAccNo();
		String tAccDate = tLLAccidentSet.get(1).getAccDate().substring(0, 10);

		LLRegisterDB tLLRegisterDB = new LLRegisterDB();
		tLLRegisterDB.setRgtNo(tClmNo);
		if (tLLRegisterDB.getInfo() == false) {
			logger.debug("------------------------------------------------------");
			logger.debug("--查询立案信息失败!");
			logger.debug("------------------------------------------------------");
		}

		String tClmState = tLLRegisterDB.getClmState();
		String tRgtClass = tLLRegisterDB.getRgtClass();

		GlobalInput tG = new GlobalInput();
		tG.Operator = tLLRegisterDB.getOperator();
		tG.ManageCom = tLLRegisterDB.getMngCom();
		tG.ComCode = tLLRegisterDB.getMngCom();

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("AccNo", tAccNo);
		tTransferData.setNameAndValue("ClmNo", tClmNo);
		tTransferData.setNameAndValue("AccDate", tAccDate);
		tTransferData.setNameAndValue("ContType", tRgtClass); // 总单类型,1-个人总投保单,2-集体总单
		tTransferData.setNameAndValue("ClmState", tClmState); // 赔案状态，20立案，30审核

		VData tVData = new VData();
		tVData.addElement(tG);
		tVData.addElement(tTransferData);

		LLClaimCalShouldPayBL tLLClaimCalShouldPayBL = new LLClaimCalShouldPayBL();
		tLLClaimCalShouldPayBL.submitData(tVData, "");
		int n = tLLClaimCalShouldPayBL.mErrors.getErrorCount();

	}

}
