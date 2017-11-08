/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LLAffixDB;
import com.sinosoft.lis.db.LLCaseDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LLAffixSchema;
import com.sinosoft.lis.schema.LLCaseSchema;
import com.sinosoft.lis.schema.LLRegisterIssueSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.vschema.LLAffixSet;
import com.sinosoft.lis.vschema.LLRegisterIssueSet;
import com.sinosoft.lis.db.LLRegisterIssueDB;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
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

public class LLIssueReplyBL {
private static Logger logger = Logger.getLogger(LLIssueReplyBL.class);
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
	private LLRegisterIssueSchema mLLRegisterIssueSchema = new LLRegisterIssueSchema();
	private LLAffixSet tLLAffixSet = new LLAffixSet();
	private LLAffixSchema mLLAffixSchema = new LLAffixSchema();
	private LLAffixSchema tLLAffixSchema = new LLAffixSchema();
	private LLAffixDB tLLAffixDB = new LLAffixDB();

	public LLIssueReplyBL() {
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
		logger.debug("----------LLIssueReplyBL Begin----------");
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}

		// 检查数据合法性
		logger.debug("----------LLIssueReplyBL checkData begin----------");

		if (!checkData()) {
			return false;
		}
		logger.debug("----------LLIssueReplyBL checkData end----------");

		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("----------LLIssueReplyBL dealData end----------");

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

		logger.debug("----------LLIssueReplyBL End----------");
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData() {
		logger.debug("----------LLIssueReplyBL getInputData begin----------");

		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);// 按类取值
        mLLRegisterIssueSchema = (LLRegisterIssueSchema) mInputData.getObjectByObjectName(
                "LLRegisterIssueSchema", 0);
		logger.debug("----------LLIssueReplyBL getInputData end----------");
		return true;
	}

	/**
	 * 校验传入的信息是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {

		if (mGlobalInput == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLIssueReplyBL";
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
			tError.moduleName = "LLIssueReplyBL";
			tError.functionName = "checkData";
			tError.errorMessage = "前台传输全局公共数据[操作员编码]失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if ((mOperate.trim().equals("")) || mOperate == null){
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLIssueReplyBL";
			tError.functionName = "checkData";
			tError.errorMessage = "接受的mOperate为空!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		logger.debug("----------LLIssueReplyBL dealData begin----------");

		boolean tReturn = false;
		if (this.mOperate.equals("Issue||UPDATE")) {
			try {

				String Rgtno = mLLRegisterIssueSchema.getRgtNo();
				String Autditno = mLLRegisterIssueSchema.getAutditNo();
				
				LLRegisterIssueDB tLLRegisterIssueDB = new LLRegisterIssueDB();
				tLLRegisterIssueDB.setRgtNo(Rgtno);
				tLLRegisterIssueDB.setAutditNo(Autditno);
				tLLRegisterIssueDB.setIssueConclusion("03");
				tLLRegisterIssueDB.setIssueReplyDate("");

				if (tLLRegisterIssueDB.getInfo() == false) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LLIssueReplyBL";
					tError.functionName = "checkData";
					tError.errorMessage = "接受的Set信息全部为空!";
					this.mErrors.addOneError(tError);
					return false;
				}
				LLRegisterIssueSchema tLLRegisterIssueSchema = new LLRegisterIssueSchema();
				tLLRegisterIssueSchema.setSchema(tLLRegisterIssueDB.getSchema());
				tLLRegisterIssueSchema.setIssueReplyConclusion(mLLRegisterIssueSchema.getIssueReplyConclusion());
				tLLRegisterIssueSchema.setIssueReplyer(mGlobalInput.Operator);
				tLLRegisterIssueSchema.setIssueReplyCom(mGlobalInput.ManageCom);
				tLLRegisterIssueSchema.setIssueReplyDate(CurrentDate);
				tLLRegisterIssueSchema.setIssueReplyTime(CurrentTime);
				tLLRegisterIssueSchema.setModifyDate(CurrentDate);
				tLLRegisterIssueSchema.setModifyTime(CurrentTime);
				
			    map.put(tLLRegisterIssueSchema, "UPDATE");
				tReturn = true;
			} catch (Exception ex) {
				logger.debug(ex.toString());
				return false;
			}

			tReturn = true;
		}
		logger.debug("----------LLIssueReplyBL dealData end----------");

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
