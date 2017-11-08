/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.LLReportReasonSet;
import com.sinosoft.utility.*;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 团体报案登记业务逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author zhangzheng
 * @version 1.0
 */
public class LLGrpReportBL {
private static Logger logger = Logger.getLogger(LLGrpReportBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	private MMap map = new MMap();

	private LLAccidentSchema mLLAccidentSchema = new LLAccidentSchema();

	private LLAccidentSubSchema mLLAccidentSubSchema = new LLAccidentSubSchema();

	private LLReportSchema mLLReportSchema = new LLReportSchema();

	private LLSubReportSchema mLLSubReportSchema = new LLSubReportSchema();

	private LLReportRelaSchema mLLReportRelaSchema = new LLReportRelaSchema();

	private LLCaseRelaSchema mLLCaseRelaSchema = new LLCaseRelaSchema();

	private LLReportReasonSchema mLLReportReasonSchema = new LLReportReasonSchema();

	private LLReportReasonSet mLLReportReasonSet = new LLReportReasonSet();

	private GlobalInput mG = new GlobalInput();

	private TransferData mTransferData = new TransferData();

	private String CurrentDate = PubFun.getCurrentDate();

	private String CurrentTime = PubFun.getCurrentTime();

	private boolean mIsReportExist;

	private boolean mIsAccExist;

	public LLGrpReportBL() {
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
		logger.debug("----------LLGrpReportBL begin submitData----------");
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("----------LLGrpReportBL after getInputData----------");

		// 检查数据合法性
		if (!checkInputData()) {
			return false;
		}
		logger.debug("----------LLGrpReportBL after checkInputData----------");
		// 进行业务处理
		if (!dealData(cOperate)) {
			return false;
		}
		logger.debug("----------LLGrpReportBL after dealData----------");

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		logger.debug("----------LLGrpReportBL after prepareOutputData----------");

		mInputData = null;
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData() {
		logger.debug("--start getInputData()");
		// 获取页面报案信息
		mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0); // 按类取值
		mTransferData = (TransferData) mInputData.getObjectByObjectName("TransferData", 0);
		mLLAccidentSchema = (LLAccidentSchema) mInputData.getObjectByObjectName("LLAccidentSchema", 0);
		mLLReportSchema = (LLReportSchema) mInputData.getObjectByObjectName("LLReportSchema", 0);
		mLLSubReportSchema = (LLSubReportSchema) mInputData.getObjectByObjectName("LLSubReportSchema", 0);
		mLLReportReasonSet = (LLReportReasonSet) mInputData.getObjectByObjectName("LLReportReasonSet", 0);

		if (mLLAccidentSchema == null && mLLReportSchema == null && mLLSubReportSchema == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLReportBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接受的信息全部为空!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 校验传入的报案信息是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkInputData() {
		logger.debug("----------begin checkInputData----------");
		try {
			// 非空字段检验
			if (mLLAccidentSchema.getAccDate() == null && mLLSubReportSchema.getCustomerNo() == null) { // 意外事故发生日期
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LLGrpReportBL";
				tError.functionName = "checkInputData";
				tError.errorMessage = "接受的意外事故发生日期为空!";
				this.mErrors.addOneError(tError);
				return false;
			}
			// if (mLLSubReportSchema.getCustomerNo() == null) { //出险人编码
			// // @@错误处理
			// CError tError = new CError();
			// tError.moduleName = "LLReportBL";
			// tError.functionName = "checkInputData";
			// tError.errorMessage = "接受的出险人编码为空!";
			// this.mErrors.addOneError(tError);
			// return false;
			// }
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLGrpReportBL";
			tError.functionName = "checkInputData";
			tError.errorMessage = "在校验输入的数据时出错!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData(String cOperate) {
		logger.debug("----------BL start dealData----------");
		boolean tReturn = false;
		String deleteSql = "";

		// 流水号类型,详细见SysMaxNo
		String RptNo = "";
		String AccNo = "";
		if (mLLAccidentSchema.getAccNo().equals("") || mLLAccidentSchema.getAccNo() == null) {
			// 事件不存在
			mIsAccExist = false;

			AccNo = PubFun1.CreateMaxNo("ACCNO", 10); // 生成事件号流水号
			AccNo = "8" + AccNo;
			logger.debug("-----生成事件号= " + AccNo);
		} else {
			// 事件存在
			mIsAccExist = true;
			AccNo = mLLAccidentSchema.getAccNo();
			logger.debug("-----已有事件号= " + AccNo);
		}
		// 生成赔案号
		if (mLLReportSchema.getRptNo().equals("") || mLLReportSchema.getRptNo() == null) {
			// 报案不存在
			mIsReportExist = false;

			String strSql = "select managecom from lcgrpcont where appntno = '"
					+ mLLReportSchema.getAppntNo() + "'";
			if (mLLReportSchema.getGrpContNo() != null && !mLLReportSchema.getGrpContNo().equals("")) {
				strSql += " and grpcontno = '" + mLLReportSchema.getGrpContNo() + "' ";
			}
			strSql += " and rownum=1";
			ExeSQL exesql = new ExeSQL();
			// String tResult = exesql.getOneValue(strSql);

			// RptNo = PubFun1.CreateMaxNo("RPTONLYNO", 10); //生成赔案号流水号
			// RptNo = "9" + RptNo;
			// 修改报案号生成规则为与个险相同
			String strlimit = PubFun.getNoLimit(mG.ManageCom);
			logger.debug("strlimit=" + strlimit);
			RptNo = PubFun1.CreateMaxNo("GRPRPTNO", strlimit);
			logger.debug("-----生成报案号= " + RptNo);
			// 取当前年度号
			// String tCurrentDate = PubFun.getCurrentDate().substring(0, 4);
			// RptNo = tResult+tCurrentDate;
			// RptNo = PubFun1.CreateMaxNo("RPTONLYNO", tResult); //生成赔案号流水号
			logger.debug("-----生成赔案号= " + RptNo);
		} else {
			// 报案存在
			mIsReportExist = true;
			RptNo = mLLReportSchema.getRptNo();
			logger.debug("-----已有赔案号= " + RptNo);
		}

		logger.debug("cOperate=" + cOperate);
		if (cOperate.equals("REPORTCUSTOMER")) {
			// 事件表
			mLLAccidentSchema.setAccNo(AccNo); // 事件号
			mLLAccidentSchema.setOperator(mG.Operator);
			mLLAccidentSchema.setMngCom(mG.ManageCom);
			mLLAccidentSchema.setMakeDate(CurrentDate);
			mLLAccidentSchema.setMakeTime(CurrentTime);
			mLLAccidentSchema.setModifyDate(CurrentDate);
			mLLAccidentSchema.setModifyTime(CurrentTime);

			// 分事件表
			mLLAccidentSubSchema.setAccNo(mLLAccidentSchema.getAccNo()); // 事件号
			mLLAccidentSubSchema.setCustomerNo(mLLSubReportSchema.getCustomerNo()); // 出险人编码

			// 报案表
			mLLReportSchema.setRptNo(RptNo); // 报案号=赔案号
			mLLReportSchema.setRgtObjNo(mLLReportSchema.getRptNo());
			mLLReportSchema.setRptDate(CurrentDate); // 报案受理日期
			mLLReportSchema.setAvaiFlag("10"); // 案件有效标志10表示保存未确认
			mLLReportSchema.setOperator(mG.Operator);
			mLLReportSchema.setMngCom(mG.ManageCom);
			mLLReportSchema.setMakeDate(CurrentDate);
			mLLReportSchema.setMakeTime(CurrentTime);
			mLLReportSchema.setModifyDate(CurrentDate);
			mLLReportSchema.setModifyTime(CurrentTime);

			// 分案表
			mLLSubReportSchema.setSubRptNo(mLLReportSchema.getRptNo()); // 分案号=赔案号
			mLLSubReportSchema.setOperator(mG.Operator);
			mLLSubReportSchema.setMngCom(mG.ManageCom);
			mLLSubReportSchema.setMakeDate(CurrentDate);
			mLLSubReportSchema.setMakeTime(CurrentTime);
			mLLSubReportSchema.setModifyDate(CurrentDate);
			mLLSubReportSchema.setModifyTime(CurrentTime);

			// 报案分案关联表
			mLLReportRelaSchema.setRptNo(mLLReportSchema.getRptNo()); // 分案号=赔案号
			mLLReportRelaSchema.setSubRptNo(mLLReportSchema.getRptNo()); // 分案号=赔案号

			// 分案事件关联表
			mLLCaseRelaSchema.setCaseRelaNo(mLLAccidentSubSchema.getAccNo()); // 事件号
			mLLCaseRelaSchema.setCaseNo(mLLReportSchema.getRptNo()); // 分案号=赔案号
			mLLCaseRelaSchema.setSubRptNo(mLLReportSchema.getRptNo()); // 分案号=赔案号

			// 理赔类型表(多条添加)
			for (int i = 1; i <= mLLReportReasonSet.size(); i++) {
				mLLReportReasonSet.get(i).setRpNo(mLLReportSchema.getRptNo()); // 分案号=赔案号
				mLLReportReasonSet.get(i).setOperator(mG.Operator);
				mLLReportReasonSet.get(i).setMngCom(mG.ManageCom);
				mLLReportReasonSet.get(i).setMakeDate(CurrentDate);
				mLLReportReasonSet.get(i).setMakeTime(CurrentTime);
				mLLReportReasonSet.get(i).setModifyDate(CurrentDate);
				mLLReportReasonSet.get(i).setModifyTime(CurrentTime);
			}

			map.put(mLLAccidentSchema, "DELETE&INSERT");
			map.put(mLLAccidentSubSchema, "DELETE&INSERT");
			map.put(mLLReportSchema, "DELETE&INSERT");
			map.put(mLLSubReportSchema, "DELETE&INSERT");
			map.put(mLLReportRelaSchema, "DELETE&INSERT");
			map.put(mLLCaseRelaSchema, "DELETE&INSERT");
			map.put(mLLReportReasonSet, "DELETE&INSERT");
			tReturn = true;
		}
		// 添加纪录
		if (cOperate.equals("INSERT") || cOperate.equals("9999999999") || cOperate.equals("9899999999")) {
			logger.debug("----------INSERT dealData----------");
			// 报案不存在
			if (mIsReportExist == false) {
				logger.debug("----go to false deal;");

				// 事件表
				mLLAccidentSchema.setAccNo(AccNo); // 事件号
				mLLAccidentSchema.setOperator(mG.Operator);
				mLLAccidentSchema.setMngCom(mG.ManageCom);
				mLLAccidentSchema.setMakeDate(CurrentDate);
				mLLAccidentSchema.setMakeTime(CurrentTime);
				mLLAccidentSchema.setModifyDate(CurrentDate);
				mLLAccidentSchema.setModifyTime(CurrentTime);
				// 分事件表
				mLLAccidentSubSchema.setAccNo(mLLAccidentSchema.getAccNo()); // 事件号
				mLLAccidentSubSchema.setCustomerNo(mLLSubReportSchema.getCustomerNo()); // 出险人编码
				// 报案表
				mLLReportSchema.setRptNo(RptNo); // 报案号=赔案号
				mLLReportSchema.setRgtObj("2");
				mLLReportSchema.setRgtObjNo(mLLReportSchema.getRptNo());
				mLLReportSchema.setRptDate(CurrentDate); // 报案受理日期
				mLLReportSchema.setRgtClass("2");// 申请类型
				mLLReportSchema.setAvaiFlag("10"); // 案件有效标志10表示保存未确认
				mLLReportSchema.setOperator(mG.Operator);
				mLLReportSchema.setMngCom(mG.ManageCom);
				mLLReportSchema.setMakeDate(CurrentDate);
				mLLReportSchema.setMakeTime(CurrentTime);
				mLLReportSchema.setModifyDate(CurrentDate);
				mLLReportSchema.setModifyTime(CurrentTime);
				// 分案表
				mLLSubReportSchema.setSubRptNo(mLLReportSchema.getRptNo()); // 分案号=赔案号
				mLLSubReportSchema.setOperator(mG.Operator);
				mLLSubReportSchema.setMngCom(mG.ManageCom);
				mLLSubReportSchema.setMakeDate(CurrentDate);
				mLLSubReportSchema.setMakeTime(CurrentTime);
				mLLSubReportSchema.setModifyDate(CurrentDate);
				mLLSubReportSchema.setModifyTime(CurrentTime);
				// 报案分案关联表
				mLLReportRelaSchema.setRptNo(mLLReportSchema.getRptNo()); // 分案号=赔案号
				mLLReportRelaSchema.setSubRptNo(mLLReportSchema.getRptNo()); // 分案号=赔案号
				// 分案事件关联表
				mLLCaseRelaSchema.setCaseRelaNo(mLLAccidentSchema.getAccNo()); // 事件号
				mLLCaseRelaSchema.setCaseNo(mLLReportSchema.getRptNo()); // 分案号=赔案号
				mLLCaseRelaSchema.setSubRptNo(mLLReportSchema.getRptNo()); // 分案号=赔案号
				// 理赔类型表(多条添加)
				for (int i = 1; i <= mLLReportReasonSet.size(); i++) {
					mLLReportReasonSet.get(i).setRpNo(mLLReportSchema.getRptNo()); // 分案号=赔案号
					mLLReportReasonSet.get(i).setOperator(mG.Operator);
					mLLReportReasonSet.get(i).setMngCom(mG.ManageCom);
					mLLReportReasonSet.get(i).setMakeDate(CurrentDate);
					mLLReportReasonSet.get(i).setMakeTime(CurrentTime);
					mLLReportReasonSet.get(i).setModifyDate(CurrentDate);
					mLLReportReasonSet.get(i).setModifyTime(CurrentTime);
					// 如有伤残,则生成伤残打印数据
					String tCode = mLLReportReasonSet.get(i).getReasonCode().substring(1, 3);
					if (tCode.equals("01")) {
						// 生成打印数据--伤残鉴定通知书
						if (!insertLOPRTManager("PCT001")) {
							return false;
						}
					}
				}
				// 生成打印数据--单证通知书
				// if (!insertLOPRTManager("PCT002")) {
				// return false;
				// }
				tReturn = true;
			} else {
				logger.debug("-----go to true deal;");
				// 报案已存在,增加出险人，只需更新分案表
				if (mLLSubReportSchema.getSubRptNo() != null) {

					// 事件表
					mLLAccidentSchema.setAccNo(AccNo); // 事件号
					mLLAccidentSchema.setOperator(mG.Operator);
					mLLAccidentSchema.setMngCom(mG.ManageCom);
					mLLAccidentSchema.setMakeDate(CurrentDate);
					mLLAccidentSchema.setMakeTime(CurrentTime);
					mLLAccidentSchema.setModifyDate(CurrentDate);
					mLLAccidentSchema.setModifyTime(CurrentTime);

					// 分事件表
					mLLAccidentSubSchema.setAccNo(mLLAccidentSchema.getAccNo()); // 事件号
					mLLAccidentSubSchema.setCustomerNo(mLLSubReportSchema.getCustomerNo()); // 出险人编码

					// 报案表
					mLLReportSchema.setRptNo(RptNo); // 报案号=赔案号
					mLLReportSchema.setRptDate(CurrentDate); // 报案受理日期
					mLLReportSchema.setAvaiFlag("10"); // 案件有效标志10表示保存未确认
					mLLReportSchema.setOperator(mG.Operator);
					mLLReportSchema.setMngCom(mG.ManageCom);
					mLLReportSchema.setMakeDate(CurrentDate);
					mLLReportSchema.setMakeTime(CurrentTime);
					mLLReportSchema.setModifyDate(CurrentDate);
					mLLReportSchema.setModifyTime(CurrentTime);
					// 分案表
					mLLSubReportSchema.setSubRptNo(mLLReportSchema.getRptNo()); // 分案号=赔案号
					mLLSubReportSchema.setOperator(mG.Operator);
					mLLSubReportSchema.setMngCom(mG.ManageCom);
					mLLSubReportSchema.setMakeDate(CurrentDate);
					mLLSubReportSchema.setMakeTime(CurrentTime);
					mLLSubReportSchema.setModifyDate(CurrentDate);
					mLLSubReportSchema.setModifyTime(CurrentTime);
					// 报案分案关联表
					mLLReportRelaSchema.setRptNo(mLLReportSchema.getRptNo()); // 分案号=赔案号
					mLLReportRelaSchema.setSubRptNo(mLLReportSchema.getRptNo()); // 分案号=赔案号
					// 分案事件关联表
					mLLCaseRelaSchema.setCaseRelaNo(mLLAccidentSchema.getAccNo()); // 事件号
					mLLCaseRelaSchema.setCaseNo(mLLReportSchema.getRptNo()); // 分案号=赔案号
					mLLCaseRelaSchema.setSubRptNo(mLLReportSchema.getRptNo()); // 分案号=赔案号
					// 理赔类型表(多条添加)
					for (int i = 1; i <= mLLReportReasonSet.size(); i++) {
						mLLReportReasonSet.get(i).setRpNo(mLLReportSchema.getRptNo()); // 分案号=赔案号
						mLLReportReasonSet.get(i).setOperator(mG.Operator);
						mLLReportReasonSet.get(i).setMngCom(mG.ManageCom);
						mLLReportReasonSet.get(i).setMakeDate(CurrentDate);
						mLLReportReasonSet.get(i).setMakeTime(CurrentTime);
						mLLReportReasonSet.get(i).setModifyDate(CurrentDate);
						mLLReportReasonSet.get(i).setModifyTime(CurrentTime);
						// 如有伤残,则生成伤残打印数据
						String tCode = mLLReportReasonSet.get(i).getReasonCode().substring(1, 3);
						if (tCode.equals("01")) {
							// 生成打印数据--伤残鉴定通知书
							if (!insertLOPRTManager("PCT001")) {
								return false;
							}
						}
					}

					tReturn = true;
				}
			}

			// 生成打印数据--单证通知书
			if (!insertLOPRTManager("PCT002")) {
				return false;
			}

			map.put(mLLAccidentSchema, "DELETE&INSERT");
			map.put(mLLAccidentSubSchema, "DELETE&INSERT");
			map.put(mLLReportSchema, "DELETE&INSERT");
			map.put(mLLSubReportSchema, "DELETE&INSERT");
			map.put(mLLReportRelaSchema, "DELETE&INSERT");
			map.put(mLLCaseRelaSchema, "DELETE&INSERT");
			map.put(mLLReportReasonSet, "DELETE&INSERT");
		}

		// 更新纪录
		if (cOperate.equals("UPDATE")) {
			logger.debug("----------UPDATE dealData----------");
			// 事件表(不更新只同步数据用)
			LLAccidentDB tLLAccidentDB = new LLAccidentDB();
			tLLAccidentDB.setAccNo(mLLAccidentSchema.getAccNo());
			if (tLLAccidentDB.getInfo() == false) {
				// @@错误处理
				CError.buildErr(this, "查询事件信息失败!");
				return false;
			} else {

				mLLAccidentSchema = tLLAccidentDB.getSchema();
				logger.debug("----------mLLAccidentSchema" + mLLAccidentSchema.getMakeDate());
				mLLAccidentSchema.setAccPlace(mLLReportSchema.getAccidentSite());
				mLLAccidentSchema.setAccDesc(mLLSubReportSchema.getAccDesc());
				mLLAccidentSchema.setModifyDate(PubFun.getCurrentDate());
				mLLAccidentSchema.setModifyTime(PubFun.getCurrentTime());

				// 分事件表(不更新只同步数据用)
				LLAccidentSubDB tLLAccidentSubDB = new LLAccidentSubDB();
				tLLAccidentSubDB.setAccNo(mLLAccidentSchema.getAccNo());
				tLLAccidentSubDB.setCustomerNo(mLLSubReportSchema.getCustomerNo());
				tLLAccidentSubDB.getInfo();
				mLLAccidentSubSchema = tLLAccidentSubDB.getSchema();
			}

			// 更新报案信息
			LLReportDB tLLReportDB = new LLReportDB();
			tLLReportDB.setRptNo(mLLReportSchema.getRptNo());
			if (tLLReportDB.getInfo() == false) {
				// @@错误处理
				CError.buildErr(this, "查询报案信息失败!");
				return false;
			} else {
				mLLReportSchema.setRgtObj(tLLReportDB.getRgtObj());
				mLLReportSchema.setRgtObjNo(tLLReportDB.getRgtObjNo());
				mLLReportSchema.setRgtClass(tLLReportDB.getRgtClass());
				mLLReportSchema.setRgtState(tLLReportDB.getRgtState()); // *案件类型*
				mLLReportSchema.setRptDate(tLLReportDB.getRptDate()); // 报案受理日期Modify
				// by
				// zhaorx
				// 2006-12-14QC8085
				mLLReportSchema.setAvaiFlag("10"); // 案件有效标志10表示保存未确认
				mLLReportSchema.setRgtFlag("10"); // 立案标志为10表示未立案
				mLLReportSchema.setMngCom(tLLReportDB.getMngCom());
				mLLReportSchema.setOperator(tLLReportDB.getOperator());
				mLLReportSchema.setMakeDate(tLLReportDB.getMakeDate());
				mLLReportSchema.setMakeTime(tLLReportDB.getMakeTime());
				mLLReportSchema.setModifyDate(CurrentDate);
				mLLReportSchema.setModifyTime(CurrentTime);
				mLLReportSchema.setPeoples2(tLLReportDB.getPeoples2());
			}

			// 更新分案信息表的字段()
			LLSubReportDB tLLSubReportDB = new LLSubReportDB();
			tLLSubReportDB.setSubRptNo(mLLSubReportSchema.getSubRptNo());
			tLLSubReportDB.setCustomerNo(mLLSubReportSchema.getCustomerNo());
			if (tLLSubReportDB.getInfo() == false) {
				// @@错误处理
				CError.buildErr(this, "查询分案信息失败!");
				return false;
			} else {
				
	            mLLSubReportSchema.setBnfIDType(tLLSubReportDB.getBnfIDType());//受益人证件类型
	            mLLSubReportSchema.setBnfSex(tLLSubReportDB.getBnfSex());//受益人性别
	            mLLSubReportSchema.setBirthday(tLLSubReportDB.getBirthday());//受益人出生日期
	            mLLSubReportSchema.setBnfName(tLLSubReportDB.getBnfName());
	            
	            mLLSubReportSchema.setBnfIDNo(tLLSubReportDB.getBnfIDNo());// 受益人证件号码
	            mLLSubReportSchema.setBankAccNo(tLLSubReportDB.getBankAccNo());
				mLLSubReportSchema.setBnfAccName(tLLSubReportDB.getBnfAccName());//受益人银行账户名
				
	            mLLSubReportSchema.setBankCode(tLLSubReportDB.getBankCode());
	            mLLSubReportSchema.setRelationToInsured(tLLSubReportDB.getRelationToInsured());// 与被保险人关系
	            mLLSubReportSchema.setCaseGetMode(tLLSubReportDB.getCaseGetMode());// 领取方式

				mLLSubReportSchema.setMngCom(tLLSubReportDB.getMngCom());
				mLLSubReportSchema.setOperator(tLLSubReportDB.getOperator());
				mLLSubReportSchema.setMakeDate(tLLSubReportDB.getMakeDate());
				mLLSubReportSchema.setMakeTime(tLLSubReportDB.getMakeTime());
				mLLSubReportSchema.setModifyDate(CurrentDate);
				mLLSubReportSchema.setModifyTime(CurrentTime);
			}

			// 报案分案关联表(不更新只同步数据用)
			LLReportRelaDB tLLReportRelaDB = new LLReportRelaDB();
			tLLReportRelaDB.setRptNo(mLLReportSchema.getRptNo());
			tLLReportRelaDB.setSubRptNo(mLLSubReportSchema.getSubRptNo());
			if (tLLSubReportDB.getInfo() == false) {
				// @@错误处理
				CError.buildErr(this, "查询分案关联信息失败!");
				return false;
			} else {
				mLLReportRelaSchema = tLLReportRelaDB.getSchema();
			}

			// 分案事件关联表(不更新只同步数据用)
			LLCaseRelaDB tLLCaseRelaDB = new LLCaseRelaDB();
			tLLCaseRelaDB.setCaseNo(mLLReportSchema.getRptNo());
			tLLCaseRelaDB.setCaseRelaNo(mLLAccidentSchema.getAccNo());
			tLLCaseRelaDB.setSubRptNo(mLLSubReportSchema.getSubRptNo());
			if (tLLCaseRelaDB.getInfo() == false) {
				// @@错误处理
				CError.buildErr(this, "查询分案事件关联信息失败!");
				return false;
			} else {
				mLLCaseRelaSchema = tLLCaseRelaDB.getSchema();
			}

			// ------------------------------------------------------------------BEG
			// 理赔类型表,首先删除所有该分案下的所有理赔类型,然后再添加前台更改后数据
			// ------------------------------------------------------------------
			deleteSql = " delete from LLReportReason where " + " RpNo = '" + mLLSubReportSchema.getSubRptNo()
					+ "'" + " and CustomerNo = '" + mLLSubReportSchema.getCustomerNo() + "'";
			map.put(deleteSql, "DELETE");

			for (int i = 1; i <= mLLReportReasonSet.size(); i++) {
				LLReportReasonDB tLLReportReasonDB = new LLReportReasonDB();
				tLLReportReasonDB.setRpNo(mLLReportReasonSet.get(i).getRpNo());
				tLLReportReasonDB.setCustomerNo(mLLReportReasonSet.get(i).getCustomerNo());
				tLLReportReasonDB.setReasonCode(mLLReportReasonSet.get(i).getReasonCode());

				tLLReportReasonDB.getInfo();

				if (tLLReportReasonDB.getMakeDate() == null) {
					mLLReportReasonSet.get(i).setOperator(mG.Operator);
					mLLReportReasonSet.get(i).setMngCom(mG.ManageCom);
					mLLReportReasonSet.get(i).setMakeDate(CurrentDate);
					mLLReportReasonSet.get(i).setMakeTime(CurrentTime);
					mLLReportReasonSet.get(i).setModifyDate(CurrentDate);
					mLLReportReasonSet.get(i).setModifyTime(CurrentTime);

					// 如有伤残,则生成伤残打印数据
					String tCode = mLLReportReasonSet.get(i).getReasonCode().substring(1, 3);
					if (tCode.equals("01")) {
						// 先删除伤残打印数据
						String tsql = "delete from LOPRTManager where " + " otherno = '"
								+ mLLReportSchema.getRptNo() + "'" + " and code = 'PCT001'";
						map.put(tsql, "DELETE");

						// 生成打印数据--伤残鉴定通知书
						if (!insertLOPRTManager("PCT001")) {
							return false;
						}
					}
				} else {
					// mLLReportReasonSet.get(i).setSchema(tLLReportReasonDB.getSchema());
					mLLReportReasonSet.get(i).setMngCom(mG.ManageCom);
					mLLReportReasonSet.get(i).setOperator(mG.Operator);
					mLLReportReasonSet.get(i).setMakeDate(tLLReportReasonDB.getMakeDate());
					mLLReportReasonSet.get(i).setMakeTime(tLLReportReasonDB.getMakeTime());
					mLLReportReasonSet.get(i).setModifyDate(CurrentDate);
					mLLReportReasonSet.get(i).setModifyTime(CurrentTime);
				}
			}

			map.put(mLLAccidentSchema, "DELETE&INSERT");
			map.put(mLLAccidentSubSchema, "DELETE&INSERT");
			map.put(mLLReportSchema, "DELETE&INSERT");
			map.put(mLLSubReportSchema, "DELETE&INSERT");
			map.put(mLLReportRelaSchema, "DELETE&INSERT");
			map.put(mLLCaseRelaSchema, "DELETE&INSERT");
			map.put(mLLReportReasonSet, "DELETE&INSERT");
			// ------------------------------------------------------------------End
			tReturn = true;
		}

		// 删除数据
		if (cOperate.equals("DELETE")) {
			String CaseNo = mLLSubReportSchema.getSubRptNo();
			String CustomerNo = mLLSubReportSchema.getCustomerNo();
			String deleteSq2 = " delete  from LLReportAffix where " + " RptNo = '" + CaseNo + "'"
					+ " and CustomerNo = '" + CustomerNo + "'";
			String deleteSq3 = " delete  from LLReportReason where " + " RpNo = '" + CaseNo + "'"
					+ " and CustomerNo = '" + CustomerNo + "'";
			String deleteSq4 = " delete  from LLSubReport where " + " SubRptNo = '" + CaseNo + "'"
					+ " and CustomerNo = '" + CustomerNo + "'";
			String deleteSq5 = "delete from LOPRTManager a where a.code = 'PCT002' and a.othernotype = '05' and a.otherno = '"
					+ CaseNo + "' and a.standbyflag4 = '" + CustomerNo + "'";
			String deleteSq6 = "delete from LOPRTManager a where a.code = 'PCT001' and a.othernotype = '05' and a.otherno = '"
				+ CaseNo + "' and a.standbyflag4 = '" + CustomerNo + "'";
			map.put(deleteSq2, "DELETE");
			map.put(deleteSq3, "DELETE");
			map.put(deleteSq4, "DELETE");
			map.put(deleteSq5, "DELETE");
			map.put(deleteSq6, "DELETE");
			tReturn = true;
		}
		/**
		 * 对于"删除"以下数据不用提交，所以去掉，并把一下内容分别放到了各自的if块里面 map.put(mLLAccidentSchema,
		 * "DELETE&INSERT"); map.put(mLLAccidentSubSchema, "DELETE&INSERT");
		 * map.put(mLLReportSchema, "DELETE&INSERT");
		 * map.put(mLLSubReportSchema, "DELETE&INSERT");
		 * map.put(mLLReportRelaSchema, "DELETE&INSERT");
		 * map.put(mLLCaseRelaSchema, "DELETE&INSERT");
		 * map.put(mLLReportReasonSet, "DELETE&INSERT");
		 * 
		 */

		// 更新mTransferData中的值
		if (!perpareMissionProp()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLReportBL";
			tError.functionName = "perpareMissionProp";
			tError.errorMessage = "为工作流准备数据失败!";
			this.mErrors.addOneError(tError);
			tReturn = false;
		}
		logger.debug(tReturn);
		return tReturn;
	}

	/**
	 * 更新mTransferData中的值，为工作流准备数据
	 * 
	 * @return boolean
	 */
	private boolean perpareMissionProp() {
		if (mTransferData != null) {
			logger.debug("mTransferData.findIndexByName(RptNo)="
					+ mTransferData.findIndexByName("RptNo"));
			if (mTransferData.findIndexByName("RptNo") != -1) {
				mTransferData.removeByName("RptNo");
			}
			mTransferData.setNameAndValue("RptNo", mLLReportSchema.getRptNo());
		}
		return true;
	}

	/**
	 * 添加打印数据 2005-8-16 14:49
	 * 
	 * @return boolean
	 */
	private boolean insertLOPRTManager(String tCode) {
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		String tPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", 10); // 生成印刷流水号
		tLOPRTManagerSchema.setPrtSeq(tPrtSeq); // 印刷流水号
		tLOPRTManagerSchema.setOtherNo(mLLReportSchema.getRptNo()); // 对应其它号码
		tLOPRTManagerSchema.setOtherNoType("05"); // 其它号码类型--05赔案号
		tLOPRTManagerSchema.setCode(tCode); // 单据编码
		tLOPRTManagerSchema.setManageCom(mG.ManageCom); // 管理机构
		tLOPRTManagerSchema.setReqCom(mG.ComCode); // 发起机构
		tLOPRTManagerSchema.setReqOperator(mG.Operator); // 发起人
		tLOPRTManagerSchema.setPrtType("0"); // 打印方式
		tLOPRTManagerSchema.setStateFlag("0"); // 打印状态
		tLOPRTManagerSchema.setMakeDate(CurrentDate); // 入机日期(报案日期)
		tLOPRTManagerSchema.setMakeTime(CurrentTime); // 入机时间
		tLOPRTManagerSchema.setPatchFlag("0"); // 补打标志
		tLOPRTManagerSchema.setStandbyFlag1(CurrentDate); // 批打检索日期
		tLOPRTManagerSchema.setStandbyFlag4(mLLSubReportSchema.getCustomerNo()); // 客户号码
		// tLOPRTManagerSchema.setStandbyFlag5(CurrentDate); //立案日期
		tLOPRTManagerSchema.setStandbyFlag6("10"); // 赔案状态
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
			mResult.add(map);
			mResult.add(mLLReportSchema);
			mResult.add(mLLAccidentSchema);
			mResult.add(mTransferData);
			mResult.add(mG);
			// mInputData.clear();
			// mInputData.add(map);
			// mResult.add(mLLAccidentSchema);
			// mResult.add(mLLAccidentSubSchema);
			// mResult.add(mLLReportSchema);
			// mResult.add(mLLSubReportSchema);
			// mResult.add(mLLReportRelaSchema);
			// mResult.add(mLLCaseRelaSchema);
			// mResult.add(mLLReportReasonSet);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLReportBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

}
