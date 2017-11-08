/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;

import com.sinosoft.lis.bl.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 理赔立案结论逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author zl
 * @version 1.0
 */
public class LLClaimRegisterConclusionBL {
private static Logger logger = Logger.getLogger(LLClaimRegisterConclusionBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	// 全局变量
	private MMap map = new MMap();
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	private GlobalInput mG = new GlobalInput();
	TransferData mTransferData = new TransferData();

	// 立案相关
	private LLRegisterSchema mLLRegisterSchema = new LLRegisterSchema();
	private LLClaimDetailSet mLLClaimDetailSet = new LLClaimDetailSet();
	private LLCaseSchema mLLCaseSchema = new LLCaseSchema();
	private LLClaimSchema mLLClaimSchema = new LLClaimSchema();
	private LLAppClaimReasonSchema mLLAppClaimReasonSchema = new LLAppClaimReasonSchema();
	private LLCaseSet mLLCaseSet = new LLCaseSet();
	private LLAffixSet mLLAffixSet = new LLAffixSet();
	private LLClaimPubFunBL mLLClaimPubFunBL = new LLClaimPubFunBL();

	private String mNoRgtReason = ""; // 不予立案原因
	private String mConclusionSave = "";
	private String mRptNo = "";
	private String mRgtState = "";
	private double mBeAdjSum = 0.0; // 调整金额
	private String mCusNo = ""; // 客户号码

	public LLClaimRegisterConclusionBL() {
	}

	public static void main(String[] args) {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("----------LLClaimRegisterConclusionBL begin submitData----------");
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("----------LLClaimRegisterConclusionBL after getInputData----------");

		// 检查数据合法性
		if (!checkInputData()) {
			return false;
		}
		logger.debug("----------LLClaimRegisterConclusionBL after checkInputData----------");
		// 进行业务处理
		if (!dealData(cOperate)) {
			return false;
		}
		logger.debug("----------LLClaimRegisterConclusionBL after dealData----------");
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("----------LLClaimRegisterConclusionBL after prepareOutputData----------");
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, cOperate)) {
			// @@错误处理
			CError.buildErr(this, "数据提交失败,"+tPubSubmit.mErrors.getLastError());
			return false;
		}
		mInputData = null;
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @return：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData() {
		logger.debug("---LLClaimRegisterConclusionBL start getInputData()...");
		// 获取页面报案信息
		mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0); // 按类取值
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mLLClaimDetailSet = (LLClaimDetailSet) mInputData
				.getObjectByObjectName("LLClaimDetailSet", 0);
		mRptNo = (String) mTransferData.getValueByName("RptNo");
		mNoRgtReason = (String) mTransferData.getValueByName("NoRgtReason");
		mConclusionSave = (String) mTransferData
				.getValueByName("RgtConclusion");
		mRgtState = (String) mTransferData.getValueByName("RgtState");
		
		String tBeAdjSum=(String)mTransferData.getValueByName("BeAdjSum");
		
		if(tBeAdjSum==null||tBeAdjSum.equals(""))
		{
			mBeAdjSum=0.0;
		}
		else
		{
			mBeAdjSum = Double.parseDouble(tBeAdjSum);
		}
		
		tBeAdjSum=null;

		return true;
	}

	/**
	 * 校验传入的报案信息是否合法
	 * 
	 * @return：如果发生错误则返回false,否则返回true
	 */
	private boolean checkInputData() {
		
		logger.debug("----------begin checkInputData----------");
		
		 if (mConclusionSave.equals("01")) // 只有立案通过时才进行校验
		 {
			 try
			 {
				 if(mBeAdjSum<=0)
				 {
					 //2009-04-03 豁免的理算金额为0 增加对是否为豁免的校验，如果是豁免则对理算金额是否小于0进行校验
					 boolean release = false;
					 for(int i=1;i<=mLLClaimDetailSet.size();i++){
						 if("09".equals(mLLClaimDetailSet.get(i).getGetDutyKind().substring(1))){
							 release = true;
						 }
					 }
					 if(release){
						 if(mBeAdjSum<0){
							 CError.buildErr(this, "案件理算金额小于0,不能保存案件结论,请查询原因后再行保存!");
							 return false;
						 }
					 }else{
						 CError.buildErr(this, "案件理算金额小于等于0,不能保存案件结论,请查询原因后再行保存!");
						 return false;
					 }
				 }
				 
				 //2009-05-13 zhangzheng 将风险保额校验挪到审核结论保存处
				 /**
				 if(!mLLClaimPubFunBL.getCheckAmnt(mLLClaimDetailSet)){			 
					 // @@错误处理
					 CError.buildErr(this, "在校验输入的数据时出错,"+mLLClaimPubFunBL.mErrors.getLastError());
					 return false;
				 }
				 				  * 
				  */
			 }
			 catch (Exception ex)
			 {
				 // @@错误处理
				 CError.buildErr(this, "在校验输入的数据时出错!");
				 return false;
			 }
		 }
		 
		 
		logger.debug("----------end checkInputData----------");
		         
		return true;
	}

	/**
	 * 数据操作类业务处理
	 * 
	 * @return：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData(String cOperate) {
		logger.debug("---LLClaimRegisterConclusionBL start dealData()...");
		boolean tReturn = false;

		// ----------------------------------------------------------------------BEG
		// 处理1: 更新立案结论,更改保项结论
		// ----------------------------------------------------------------------
		if (cOperate.equals("UPDATE")) {
			// 更新立案表立案结论字段
			LLRegisterDB tLLRegisterDB = new LLRegisterDB();
			tLLRegisterDB.setRgtNo(mRptNo);
			tLLRegisterDB.getInfo();
			mLLRegisterSchema = tLLRegisterDB.getSchema();
			mLLRegisterSchema.setRgtConclusion(mConclusionSave);
			mLLRegisterSchema.setNoRgtReason(mNoRgtReason);
			mLLRegisterSchema.setRgtState(mRgtState); // 案件类型
			mLLRegisterSchema.setBeAdjSum(mBeAdjSum); // 调整后金额
			// 如果不予立案,则设置结案标志和日期
			if (mConclusionSave.equals("02")) {
				// mLLRegisterSchema.setEndCaseFlag("1");
				// mLLRegisterSchema.setEndCaseDate(CurrentDate);
				// mLLRegisterSchema.setClmState("70");//Modify by zhaorx
				// 2006-11-29
			} else {
				mLLRegisterSchema.setEndCaseFlag("");
				mLLRegisterSchema.setEndCaseDate("");
				mLLRegisterSchema.setClmState("20");
				mLLRegisterSchema.setNoRgtReason("");
			}
			
			mLLRegisterSchema.setCasePayType(mLLClaimPubFunBL.getCheckCasePayType(mRptNo));
			
			//99代表案件超过某种理赔类型的最大给付金,需要发起强制调查,但案件类型依然是一般给付件
			if(mLLRegisterSchema.getCasePayType().equals("99"))
			{
				mLLRegisterSchema.setCasePayType("0");
			}
			
			mLLRegisterSchema.setModifyDate(CurrentDate);
			mLLRegisterSchema.setModifyTime(CurrentTime);

			map.put(mLLRegisterSchema, "DELETE&INSERT");
			
			/**
			 * -----------------------------------------------------------------BEG
			 * No： 更新案件类型
			 * -----------------------------------------------------------------
			 */
			String sql1 = " update llclaim set CasePayType = '"+"?CasePayType?"+"' where"
					+ " clmno = '" + "?clmno?" + "'";
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(sql1);
			sqlbv.put("clmno", mRptNo);
			sqlbv.put("CasePayType", mLLRegisterSchema.getCasePayType());
			map.put(sqlbv, "UPDATE");
			
			String sql2 = " update llclaimpolicy set CasePayType = '"+"?CasePayType?"+"' where"
				+ " clmno = '" + "?clmno?" + "'";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(sql2);
			sqlbv1.put("clmno", mRptNo);
			sqlbv1.put("CasePayType", mLLRegisterSchema.getCasePayType());
			map.put(sqlbv1, "UPDATE");
			

			// //更新分案表立案结论字段
			// LLCaseDB tLLCaseDB = new LLCaseDB();
			// tLLCaseDB.setRgtNo(mRptNo);
			// LLCaseSet tLLCaseSet = new LLCaseSet();
			// tLLCaseSet = tLLCaseDB.query();
			// if (tLLCaseSet == null && tLLCaseSet.size() == 0)
			// {
			// //@@错误处理
			// CError tError = new CError();
			// tError.moduleName = "LLClaimRegisterConclusionBL";
			// tError.functionName = "dealData";
			// tError.errorMessage = "查询分案信息失败!";
			// this.mErrors.addOneError(tError);
			// return false;
			// }
			// else
			// {
			// for (int j = 1; j <= tLLCaseSet.size(); j++)
			// {
			// LLCaseSchema tLLCaseSchema = new LLCaseSchema();
			// tLLCaseSchema.setSchema(tLLCaseSet.get(j));
			// tLLCaseSchema.setRgtType(mRgtState);//案件类型
			// mLLCaseSet.add(tLLCaseSchema);
			// }
			// }
			// map.put(mLLCaseSet, "DELETE&INSERT");

			// 更改给付结论
			if (mLLClaimDetailSet.size() >= 1) {
				for (int i = 1; i <= mLLClaimDetailSet.size(); i++) {

					String strSql = "";

					// 续涛,2005-08-08加入,如果给付编码为2,代表该保项不给,但是不属于拒付
					if (mLLClaimDetailSet.get(i).getGiveType().equals("2")) {
						strSql = " update llclaimdetail" + " set givetype = '"
								+ "?givetype?" + "',"
								+ " StandPay = 0 ," + " RealPay = 0 "
								+ " where clmno = '"
								+ "?clmno?" + "'"
								+ " and caseno = '"
								+ "?caseno?" + "'"
								+ " and polno = '"
								+ "?polno?" + "'"
								+ " and dutycode = '"
								+ "?dutycode?" + "'"
								+ " and getdutykind = '"
								+ "?getdutykind?"
								+ "'" + " and getdutycode ='"
								+ "?getdutycode?"
								+ "'" + " and caserelano = '"
								+ "?caserelano?"
								+ "'";
					} else {
						strSql = " update llclaimdetail" + " set givetype = '"
								+ "?givetype?" + "'"
								+ " where clmno = '"
								+ "?clmno?" + "'"
								+ " and caseno = '"
								+ "?caseno?" + "'"
								+ " and polno = '"
								+ "?polno?" + "'"
								+ " and dutycode = '"
								+ "?dutycode?" + "'"
								+ " and getdutykind = '"
								+ "?getdutykind?"
								+ "'" + " and getdutycode ='"
								+ "?getdutycode?"
								+ "'" + " and caserelano = '"
								+ "?caserelano?"
								+ "'";
					}

					logger.debug("------------------------------------------------------");
					logger.debug("--LLClaimRegisterConclusionBL--更新立案保项结论!"
									+ strSql);
					logger.debug("------------------------------------------------------");
					
					SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
					sqlbv3.sql(strSql);
					sqlbv3.put("givetype", mLLClaimDetailSet.get(i).getGiveType());
					sqlbv3.put("clmno", mLLClaimDetailSet.get(i).getClmNo());
					sqlbv3.put("caseno", mLLClaimDetailSet.get(i).getCaseNo());
					sqlbv3.put("polno", mLLClaimDetailSet.get(i).getPolNo());
					sqlbv3.put("dutycode", mLLClaimDetailSet.get(i).getDutyCode());
					sqlbv3.put("getdutykind", mLLClaimDetailSet.get(i).getGetDutyKind());
					sqlbv3.put("getdutycode", mLLClaimDetailSet.get(i).getGetDutyCode());
					sqlbv3.put("caserelano", mLLClaimDetailSet.get(i).getCaseRelaNo());
					map.put(sqlbv3, "UPDATE");
				}
			}
		}
		// ----------------------------------------------------------------------END

		// ----------------------------------------------------------------------BEG
		// 处理2: 数据插入到打印管理表
		// ----------------------------------------------------------------------
		// 首先删除
//		String tDSql = "delete from LOPRTManager where 1=1 "
//				+ " and OtherNo = '"
//				+ mRptNo
//				+ "'"
//				+ " and (Code = 'PCT003' or code = 'PCT004' or code = 'PCT007')";
//		map.put(tDSql, "DELETE");

		// 查询出险人
		String tSSql = "select customerno from llcase where 1=1 "
				+ " and caseno = '" + "?caseno?" + "'";
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(tSSql);
		sqlbv4.put("caseno", mRptNo);
		ExeSQL tExeSQL = new ExeSQL();
		mCusNo = tExeSQL.getOneValue(sqlbv4);

		// if (!insertLOPRTManager("PCT004","3")) //插入单证签收清单
		// {
		// return false;
		// }
		if (mConclusionSave.equals("02")) // 不予立案
		{
			String tDSql = "delete from LOPRTManager where 1=1 "
				+ " and OtherNo = '"
				+ "?OtherNo?"
				+ "'"
				+ " and code = 'PCT007'";
			SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
			sqlbv5.sql(tDSql);
			sqlbv5.put("OtherNo", mRptNo);
		     map.put(sqlbv5, "DELETE");
		
			if (!insertLOPRTManager("PCT007", "0")) // 理赔决定通知书－拒付[不予立案]
			{
				return false;
			}
		} else if (mConclusionSave.equals("03")) // 延迟立案
		{
			String sDSql = "delete from LOPRTManager where 1=1 "
				+ " and OtherNo = '"
				+ "?OtherNo?"
				+ "'"
				+ " and Code = 'PCT003'";
			SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
			sqlbv6.sql(sDSql);
			sqlbv6.put("OtherNo", mRptNo);
		    map.put(sqlbv6, "DELETE");
			if (!insertLOPRTManager("PCT003", "3")) // 单证补充通知单[问题件]
			{
				return false;
			}
		}

		// ----------------------------------------------------------------------END

		return true;
	}

	/**
	 * 添加打印数据 2005-8-16 14:49
	 * 
	 * @return boolean
	 */
	private boolean insertLOPRTManager(String tCode, String tStateFlag) {
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();

		// 插入新值
		String strNolimit = PubFun.getNoLimit(mG.ManageCom);
		logger.debug("strlimit=" + strNolimit);
		String tPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNolimit); // 生成印刷流水号
		logger.debug("-----生成的LOPRTManager的印刷流水号= " + tPrtSeq);

		tLOPRTManagerSchema.setPrtSeq(tPrtSeq); // 印刷流水号
		tLOPRTManagerSchema.setOtherNo(mRptNo); // 对应其它号码
		tLOPRTManagerSchema.setOtherNoType("05"); // 其它号码类型--05赔案号
		tLOPRTManagerSchema.setCode(tCode); // 单据编码
		tLOPRTManagerSchema.setManageCom(mG.ManageCom); // 管理机构
		tLOPRTManagerSchema.setReqCom(mG.ComCode); // 发起机构
		tLOPRTManagerSchema.setReqOperator(mG.Operator); // 发起人
		tLOPRTManagerSchema.setPrtType("0"); // 打印方式
		tLOPRTManagerSchema.setStateFlag(tStateFlag); // 打印状态
		tLOPRTManagerSchema.setMakeDate(CurrentDate); // 入机日期
		tLOPRTManagerSchema.setMakeTime(CurrentTime); // 入机时间
		tLOPRTManagerSchema.setPatchFlag("0"); // 补打标志
		tLOPRTManagerSchema.setStandbyFlag1(CurrentDate);
		tLOPRTManagerSchema.setStandbyFlag4(mCusNo); // 客户号码
		tLOPRTManagerSchema.setStandbyFlag5(CurrentDate); // 立案日期
		tLOPRTManagerSchema.setStandbyFlag6("20"); // 赔案状态

		map.put(tLOPRTManagerSchema, "INSERT");
		return true;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(map);
			mResult.add(mLLRegisterSchema);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimRegisterConclusionBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}
}
