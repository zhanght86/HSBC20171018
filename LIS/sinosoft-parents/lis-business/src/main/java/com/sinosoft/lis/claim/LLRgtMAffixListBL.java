/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LLAffixDB;
import com.sinosoft.lis.db.LLCaseDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LLAffixSchema;
import com.sinosoft.lis.schema.LLCaseSchema;
import com.sinosoft.lis.schema.LLRegisterIssueSchema;
import com.sinosoft.lis.vschema.LLAffixSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:单证回销提交信息类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 */

public class LLRgtMAffixListBL {
private static Logger logger = Logger.getLogger(LLRgtMAffixListBL.class);
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
	private LLAffixSchema tLLAffixSchema = new LLAffixSchema();
	private LLAffixDB tLLAffixDB = new LLAffixDB();

	public LLRgtMAffixListBL() {
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
		logger.debug("----------LLRgtMAffixListBL Begin----------");
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}

		// 检查数据合法性
		logger.debug("----------LLRgtMAffixListBL checkData begin----------");

		if (!checkData()) {
			return false;
		}
		logger.debug("----------LLRgtMAffixListBL checkData end----------");

		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("----------LLRgtMAffixListBL dealData end----------");

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		// 进行数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			// @@错误处理
			CError.buildErr(this, "数据提交失败,"+tPubSubmit.mErrors.getLastError());
			return false;
		}
		mInputData = null;

		logger.debug("----------LLRgtMAffixListBL End----------");
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData() {
		logger.debug("----------LLRgtMAffixListBL getInputData begin----------");

		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);// 按类取值
		mLLAffixSet = (LLAffixSet) mInputData.getObjectByObjectName(
				"LLAffixSet", 0);
		logger.debug("----------LLRgtMAffixListBL getInputData end----------");
		return true;
	}

	/**
	 * 校验传入的信息是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {

		if (mGlobalInput == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLRgtMAffixListBL";
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
			tError.moduleName = "LLRgtMAffixListBL";
			tError.functionName = "checkData";
			tError.errorMessage = "前台传输全局公共数据[操作员编码]失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if ((mOperate.equals("Rgt||UPDATE") && mLLAffixSet == null)) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLRgtMAffixListBL";
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
		logger.debug("----------LLRgtMAffixListBL dealData begin----------");

		boolean tReturn = false;
		if (this.mOperate.equals("Rgt||UPDATE")) {
			try {

				logger.debug("准备回销的单证数目==" + mLLAffixSet.size());
				if (mLLAffixSet.size() > 0) {
					for (int i = 1; i <= mLLAffixSet.size(); i++) {
						// tLLAffixDB=null;
						tLLAffixDB = new LLAffixDB();
						mLLAffixSchema = mLLAffixSet.get(i);
						tLLAffixDB.setRgtNo(mLLAffixSchema.getRgtNo());
						tLLAffixDB
								.setCustomerNo(mLLAffixSchema.getCustomerNo());
						tLLAffixDB.setAffixNo(mLLAffixSchema.getAffixNo());// 附件号码
						tLLAffixDB.setAffixCode(mLLAffixSchema.getAffixCode());// 单证代码
						if (tLLAffixDB.getInfo() == false) {
							// @@错误处理
							CError tError = new CError();
							tError.moduleName = "LLRgtMAffixListBL";
							tError.functionName = "checkData";
							tError.errorMessage = "接受的Set信息全部为空!";
							this.mErrors.addOneError(tError);
							return false;

						}
						tLLAffixSchema = new LLAffixSchema();
						tLLAffixSchema.setSchema(tLLAffixDB.getSchema());
						tLLAffixSchema.setReadyCount(mLLAffixSchema
								.getReadyCount());// 单证件数
						tLLAffixSchema.setShortCount(mLLAffixSchema
								.getShortCount());// 缺少件数
						tLLAffixSchema
								.setProperty(mLLAffixSchema.getProperty());// 提交形式
						tLLAffixSchema.setAffixConclusion(mLLAffixSchema
								.getAffixConclusion());// 齐全标志
						tLLAffixSchema.setAffixReason(mLLAffixSchema
								.getAffixReason());// 不齐全原因
						tLLAffixSchema.setReturnFlag(mLLAffixSchema
								.getReturnFlag());// 是否退还原件
						tLLAffixSchema.setSubFlag(mLLAffixSchema.getSubFlag());// 是否提交
						tLLAffixSchema.setSupplyStage(mLLAffixSchema.getSupplyStage());
						tLLAffixSchema.setReAffixDate(mLLAffixSchema.getReAffixDate());
						tLLAffixSchema.setAffixState(mLLAffixSchema.getAffixState());
						tLLAffixSchema.setModifyDate(CurrentDate);
						tLLAffixSchema.setModifyTime(CurrentTime);
						tLLAffixSet.add(tLLAffixSchema);
					}
					map.put(tLLAffixSet, "UPDATE");
					tReturn = true;
				}
				// 检测如果单证已经全部提交,修改立案分案（LLCase）表中
				// 修改单证检查结论（AffixConclusion=0-齐全）（此段程序2005-7-13 添加）
				// 检查该（赔案，客户）的所有单证是否齐全，如果齐全则在立案分案（LLCase）表中
				// 修改单证检查结论（AffixConclusion=0）
				String strSQL = "select * from llaffix where  1=1 and "
						+ "rgtno='" + "?rgtno?" + "' and "
						+ "customerno='" + "?customerno?"
						+ "' and " + "(SubFlag is null or SubFlag='1')";
				logger.debug("--strSQL=" + strSQL);
				SQLwithBindVariables sqlbv = new SQLwithBindVariables();
				sqlbv.sql(strSQL);
				sqlbv.put("rgtno", mLLAffixSet.get(1).getRgtNo());
				sqlbv.put("customerno", mLLAffixSet.get(1).getCustomerNo());
				LLAffixSet ttLLAffixSet = ((new LLAffixDB())
						.executeQuery(sqlbv));
				logger.debug("-----未提交单证数目== " + ttLLAffixSet.size());
				if (ttLLAffixSet.size() == mLLAffixSet.size()) {
					logger.debug("---------单证已全部提交----------");
					LLCaseSchema tLLCaseSchema = new LLCaseSchema();
					LLCaseDB tLLCaseDB = new LLCaseDB();
					tLLCaseDB.setCaseNo(mLLAffixSet.get(1).getRgtNo());
					tLLCaseDB.setCustomerNo(mLLAffixSet.get(1).getCustomerNo());
					tLLCaseDB.getInfo();
					tLLCaseSchema.setSchema(tLLCaseDB.getSchema());
					tLLCaseSchema.setAffixConclusion("1");// 1--齐全、0--不齐全
					map.put(tLLCaseSchema, "DELETE&INSERT");
				}

				tReturn = true;
			} catch (Exception ex) {
				logger.debug(ex.toString());
				return false;
			}

			tReturn = true;
		}
		logger.debug("----------LLRgtMAffixListBL dealData end----------");

		return tReturn;
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
