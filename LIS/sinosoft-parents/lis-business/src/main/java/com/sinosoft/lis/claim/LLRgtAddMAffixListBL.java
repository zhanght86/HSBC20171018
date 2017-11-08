/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LLCaseDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LLAffixSchema;
import com.sinosoft.lis.schema.LLCaseSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LLAffixSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:提交理赔报案所需单证信息类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author:续涛
 * @version 1.0
 */

public class LLRgtAddMAffixListBL {
private static Logger logger = Logger.getLogger(LLRgtAddMAffixListBL.class);
	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private String mOperate;
	/** 数据操作字符串 */
	private MMap map = new MMap();
	/** 往后面传输的数据库操作 */
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();

	private GlobalInput mGlobalInput = new GlobalInput();
	/** 全局数据 */
	private LLAffixSet mLLAffixSet = new LLAffixSet();
	private LLAffixSet tLLAffixSet = new LLAffixSet();
	private LLAffixSchema mLLAffixSchema = new LLAffixSchema();
	TransferData mTransferData = new TransferData();

	private int mSetSize = 0;
	String tCustomerNo;
	String tRgtNo;
	String tAffixNo = "";// 生成LLAffix表中的AffixNo字段

	public LLRgtAddMAffixListBL() {
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
		logger.debug("----------LLRgtAddMAffixListBL Begin----------");
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}

		// 检查数据合法性
		logger.debug("----------LLRgtAddMAffixListBL checkData begin----------");

		if (!checkData()) {
			return false;
		}
		logger.debug("----------LLRgtAddMAffixListBL checkData end----------");

		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("----------LLRgtAddMAffixListBL dealData end----------");

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		// 进行数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLRgtAddMAffixListBL";
			tError.functionName = "PubSubmit.submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mInputData = null;

		logger.debug("----------LLRgtAddMAffixListBL End----------");
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData() {
		logger.debug("----------LLRgtAddMAffixListBL getInputData begin----------");

		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);// 按类取值
		mLLAffixSet = (LLAffixSet) mInputData.getObjectByObjectName(
				"LLAffixSet", 0);
		mSetSize = mLLAffixSet.size();
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		tRgtNo = (String) mTransferData.getValueByName("RgtNo");
		logger.debug("----------tRgtNo==" + tRgtNo);

		tCustomerNo = (String) mTransferData.getValueByName("CustomerNo");
		logger.debug("----------tCustomerNo==" + tCustomerNo);

		logger.debug("----------LLRgtAddMAffixListBL getInputData end----------");
		return true;
	}

	/**
	 * 校验传入的信息是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {

		if (mGlobalInput == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLRgtAddMAffixListBL";
			tError.functionName = "checkData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得操作员编码
		String tOperator = mGlobalInput.Operator;
		if (tOperator == null || tOperator.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLRgtAddMAffixListBL";
			tError.functionName = "checkData";
			tError.errorMessage = "前台传输全局公共数据[操作员编码]失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if ((mOperate.equals("RgtAdd||Insert") && mLLAffixSet == null)) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLRgtAddMAffixListBL";
			tError.functionName = "checkData";
			tError.errorMessage = "接受的Set信息全部为空!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		logger.debug("----------LLRgtAddMAffixListBL dealData begin----------");

		boolean tReturn = false;
		if (this.mOperate.equals("RgtAdd||Insert")) {
			try {
				if (mSetSize > 0) {
					// 删除原来未回销的记录
					String strSql = "delete from LLAffix where RgtNo='"
							+ "?RgtNo?" + "' and CustomerNo='" + "?CustomerNo?"
							+ "' and (SubFlag is null or SubFlag='1')";
					
					for (int i = 1; i <= mSetSize; i++) {
						// tLLAffixSchema=null;
						mLLAffixSchema = mLLAffixSet.get(i);
						// tLLAffixSchema = mLLAffixSchema;
						LLAffixSchema tLLAffixSchema = new LLAffixSchema();
						tLLAffixSchema.setRgtNo(mLLAffixSchema.getRgtNo());// 赔案号
						tLLAffixSchema.setCustomerNo(mLLAffixSchema
								.getCustomerNo()); // 客户号
						tLLAffixSchema.setAffixCode(mLLAffixSchema
								.getAffixCode()); // 单证代码
						tLLAffixSchema.setAffixName(mLLAffixSchema
								.getAffixName()); // 单证名称
						tLLAffixSchema.setSubFlag(mLLAffixSchema.getSubFlag()); // 是否已提交标志
						tLLAffixSchema
								.setNeedFlag(mLLAffixSchema.getNeedFlag()); // 是否必需标志
						tLLAffixSchema.setReadyCount(mLLAffixSchema
								.getReadyCount()); // 单证件数
						tLLAffixSchema
								.setProperty(mLLAffixSchema.getProperty()); // 单证属性标志
						// 缺少件数
						tLLAffixSchema.setReturnFlag(mLLAffixSchema
								.getReturnFlag()); // 是否退还原件标志
						// tLLAffixSchema.setAffixNo(tGrid9[index]); //附件号码
						if (mLLAffixSchema.getAffixNo().equals("")
								|| mLLAffixSchema.getAffixNo().equals(null)) {
							// 生成AffixNo
							tAffixNo = PubFun1.CreateMaxNo("AffixNo", 10); // 生成AffixNo号
							// 附件号码
							logger.debug("----------tAffixNo======="
									+ tAffixNo);
							tLLAffixSchema.setAffixNo(tAffixNo);
						} else {
							tLLAffixSchema.setAffixNo(mLLAffixSchema
									.getAffixNo()); // 附件号码
						}
						tLLAffixSchema.setSupplyStage(mLLAffixSchema.getSupplyStage()); //补充材料发起阶段  01-立案初审核 03-无扫描立案录入
						tLLAffixSchema.setAffixState(mLLAffixSchema.getAffixState());//所处阶段 01-发起补充材料 02-补充材料打印完毕 03-补充材料回销完毕
						tLLAffixSchema.setOperator(this.mGlobalInput.Operator);
						tLLAffixSchema.setMngCom(this.mGlobalInput.ManageCom);
						tLLAffixSchema.setMakeDate(this.CurrentDate);
						tLLAffixSchema.setMakeTime(this.CurrentTime);
						tLLAffixSchema.setModifyDate(this.CurrentDate);
						tLLAffixSchema.setModifyTime(this.CurrentTime);
						tLLAffixSet.add(tLLAffixSchema);
					}
					SQLwithBindVariables sqlbv = new SQLwithBindVariables();
					sqlbv.sql(strSql);
					sqlbv.put("RgtNo", tRgtNo);
					sqlbv.put("CustomerNo", tCustomerNo);
					map.put(sqlbv, "DELETE");
					map.put(tLLAffixSet, "INSERT");

					// [补充单证时]修改立案分案（LLCase）表中
					// 修改单证检查结论（AffixConclusion=0-齐全）（此段程序2005-7-22 添加）
					LLCaseSchema tLLCaseSchema = new LLCaseSchema();
					LLCaseDB tLLCaseDB = new LLCaseDB();
					tLLCaseDB.setCaseNo(tRgtNo);
					tLLCaseDB.setCustomerNo(tCustomerNo);
					tLLCaseDB.getInfo();
					tLLCaseSchema.setSchema(tLLCaseDB.getSchema());
					tLLCaseSchema.setAffixConclusion("0"); // 1--齐全、0--不齐全
					map.put(tLLCaseSchema, "DELETE&INSERT");

					// 单证补充通知单[问题件]
					if (!insertLOPRTManager("PCT003", "3")) {
						return false;
					}

					tReturn = true;
				}
				tReturn = true;
			} catch (Exception ex) {
				logger.debug(ex.toString());
				return false;
			}

			tReturn = true;
		}

		return tReturn;
	}

	/**
	 * 添加打印数据 2005-8-16 14:49
	 * 
	 * @return boolean
	 */
	private boolean insertLOPRTManager(String tCode, String tStateFlag) {
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();

		// 插入新值,生成印刷流水号
		String strNolimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
		logger.debug("strlimit=" + strNolimit);
		String tPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNolimit);
		logger.debug("-----生成的LOPRTManager的印刷流水号= " + tPrtSeq);

		tLOPRTManagerSchema.setPrtSeq(tPrtSeq); // 印刷流水号
		tLOPRTManagerSchema.setOtherNo(tRgtNo); // 对应其它号码
		tLOPRTManagerSchema.setOtherNoType("05"); // 其它号码类型--05赔案号
		tLOPRTManagerSchema.setCode(tCode); // 单据编码
		tLOPRTManagerSchema.setManageCom(mGlobalInput.ManageCom); // 管理机构
		tLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode); // 发起机构
		tLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator); // 发起人
		tLOPRTManagerSchema.setPrtType("0"); // 打印方式
		tLOPRTManagerSchema.setStateFlag(tStateFlag); // 打印状态
		tLOPRTManagerSchema.setMakeDate(CurrentDate); // 入机日期
		tLOPRTManagerSchema.setMakeTime(CurrentTime); // 入机时间
		tLOPRTManagerSchema.setPatchFlag("0"); // 补打标志
		tLOPRTManagerSchema.setStandbyFlag1(CurrentDate);
		tLOPRTManagerSchema.setStandbyFlag4(tCustomerNo); // 客户号码
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

	/**
	 * 提供返回前台界面的数据
	 * 
	 * @return
	 */
	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
	}

}
