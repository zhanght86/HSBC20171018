/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LLReportAffixSchema;
import com.sinosoft.lis.vschema.LLReportAffixSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
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

public class LLMAffixListBL {
private static Logger logger = Logger.getLogger(LLMAffixListBL.class);
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
	private LLReportAffixSet mLLReportAffixSet = new LLReportAffixSet();
	private LLReportAffixSet tLLReportAffixSet = new LLReportAffixSet();

	private LLReportAffixSchema mLLReportAffixSchema = new LLReportAffixSchema();
	private int mSetSize = 0;
	String tAffixCodeTemp = "";
	String tRptNo = "";
	String tCustomerNo = "";
	String tAffixNo = "";// 生成LReportAffix表中的AffixNo字段

	public LLMAffixListBL() {
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
		logger.debug("----------LLMAffixListBL Begin----------");
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}

		// 检查数据合法性
		logger.debug("----------LLMAffixListBL checkData begin----------");

		if (!checkData()) {
			return false;
		}
		logger.debug("----------LLMAffixListBL checkData end----------");

		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("----------LLMAffixListBL dealData end----------");

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
			tError.moduleName = "LLMAffixListBL";
			tError.functionName = "PubSubmit.submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mInputData = null;

		logger.debug("----------LLMAffixListBL End----------");
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData() {
		logger.debug("----------LLMAffixListBL getInputData begin----------");

		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);// 按类取值
		mLLReportAffixSet = (LLReportAffixSet) mInputData
				.getObjectByObjectName("LLReportAffixSet", 0);
		mSetSize = mLLReportAffixSet.size();
		logger.debug("----------LLMAffixListBL getInputData end----------");
		return true;
	}

	/**
	 * 校验传入的信息是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {

		if (mGlobalInput == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLMAffixListBL";
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
			tError.moduleName = "LLMAffixListBL";
			tError.functionName = "checkData";
			tError.errorMessage = "前台传输全局公共数据[操作员编码]失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if ((mOperate.equals("Rpt||INSERT") && mLLReportAffixSet == null)) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLMAffixListBL";
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
		logger.debug("----------LLMAffixListBL dealData begin----------");

		boolean tReturn = false;
		// 进行报案单证信息的录入
		if (this.mOperate.equals("Rpt||INSERT")) {
			if (!affixInsert()) {
				return false;
			}
			tReturn = true;
		}
		logger.debug("----------LLMAffixListBL dealData end----------");

		return tReturn;
	}

	/**
	 * 进行报案单证数据的保存
	 * 
	 * @return
	 */
	private boolean affixInsert() {
		logger.debug("----------LLMAffixListBL affixInsert begin----------");

		boolean tReturn = false;
		try {
			// 如有单证记录则删除
			if (mLLReportAffixSet.size() > 0) {
				for (int i = 1; i <= mLLReportAffixSet.size(); i++) {
					tRptNo = mLLReportAffixSet.get(i).getRptNo();
					tCustomerNo = mLLReportAffixSet.get(i).getCustomerNo();
					String strSql = "delete from LLReportAffix where RptNo='"
							+ "?RptNo?" + "' and CustomerNo='" + "?CustomerNo?"
							+ "' and affixcode='"
							+ "?affixcode?" + "'";
					SQLwithBindVariables sqlbv = new SQLwithBindVariables();
					sqlbv.sql(strSql);
					sqlbv.put("RptNo", tRptNo);
					sqlbv.put("CustomerNo", tCustomerNo);
					sqlbv.put("affixcode", mLLReportAffixSet.get(i).getAffixCode());
					map.put(sqlbv, "DELETE");
				}
			}

			for (int i = 1; i <= mSetSize; i++) {
				LLReportAffixSchema tLLReportAffixSchema = new LLReportAffixSchema();
				mLLReportAffixSchema = mLLReportAffixSet.get(i);
				tLLReportAffixSchema.setRptNo(mLLReportAffixSchema.getRptNo());
				tLLReportAffixSchema.setCustomerNo(mLLReportAffixSchema
						.getCustomerNo());
				tLLReportAffixSchema.setAffixCode(mLLReportAffixSchema
						.getAffixCode());
				tLLReportAffixSchema.setAffixName(mLLReportAffixSchema
						.getAffixName());
				tLLReportAffixSchema.setReadyCount(mLLReportAffixSchema
						.getReadyCount());
				tLLReportAffixSchema.setProperty(mLLReportAffixSchema
						.getProperty());// 单证属性标志
				tLLReportAffixSchema.setNeedFlag(mLLReportAffixSchema
						.getNeedFlag());// 是否必需标志
				// 生成AffixNo
				tAffixNo = PubFun1.CreateMaxNo("AffixNo", 10); // 生成AffixNo号
				tLLReportAffixSchema.setAffixNo(tAffixNo);
				tLLReportAffixSchema.setOperator(mGlobalInput.Operator);
				tLLReportAffixSchema.setMngCom(mGlobalInput.ManageCom);
				tLLReportAffixSchema.setMakeDate(CurrentDate);
				tLLReportAffixSchema.setMakeTime(CurrentTime);
				tLLReportAffixSchema.setModifyDate(CurrentDate);
				tLLReportAffixSchema.setModifyTime(CurrentTime);
				tLLReportAffixSet.add(tLLReportAffixSchema);
			}
			map.put(tLLReportAffixSet, "INSERT");
			tReturn = true;
		} catch (Exception ex) {
			logger.debug(ex.toString());
			return false;
		}
		return tReturn;
	}

	/**
	 * 进行立案补充单证数据的保存
	 * 
	 * @return
	 */

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

	/**
	 * 随机获取AffixCode
	 * 
	 * @return
	 */

	/**
	 * 用于测试
	 * 
	 * @return
	 */
	public static void main(String[] args) {
	}

}
