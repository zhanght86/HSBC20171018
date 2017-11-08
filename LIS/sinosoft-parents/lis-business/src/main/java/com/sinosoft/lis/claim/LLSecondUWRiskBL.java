package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LLUWSubDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LLUWMasterSchema;
import com.sinosoft.lis.schema.LLUWSubSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LLUWSubSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 理赔人工核保险种结论
 * </p>
 * <p>
 * Description: 人工核保险种结论保存
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author 张星 modify by wanzh 2005/12/15 19:30
 * @version 1.0
 */

public class LLSecondUWRiskBL {
private static Logger logger = Logger.getLogger(LLSecondUWRiskBL.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private GlobalInput tGI = new GlobalInput();
	private MMap map = new MMap();
	/** 业务操作类 */
	private LLUWMasterSchema mLLUWMasterSchema = new LLUWMasterSchema();
	/** 业务操作字符串 */
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	public LLSecondUWRiskBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		logger.debug("----------After getinputdata----------------");

		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("-----------------After dealData！-----------------");
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("----------------After prepareOutputData----------------");
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mResult, cOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLSecondUWRiskBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		logger.debug("----------PubSubmit end------------");
		return true;
	}

	/**
	 * getInputData **
	 * 
	 * @param cInputData
	 *            VData *
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {
		// 公用变量
		tGI = (GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0);
		mLLUWMasterSchema = (LLUWMasterSchema) cInputData
				.getObjectByObjectName("LLUWMasterSchema", 0);
		if (tGI == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "LLSecondUWRiskBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据tGI失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (mLLUWMasterSchema == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "LLSecondUWRiskBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输数据LLUWMasterSchema失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * dealData
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		// [个人险种表---（LCPol）]
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(mLLUWMasterSchema.getPolNo());
		if (!tLCPolDB.getInfo()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLSecondUWRiskBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询险种保单表失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		LLUWMasterSchema tLLUWMasterSchema = new LLUWMasterSchema();
		tLLUWMasterSchema.setSchema(mLLUWMasterSchema);
		// 分案号<CaseNo>、批次号<BatNo>、保单号码<PolNo>、险种核保结论《PassFlag》 、核保意见《UWIdea》
		// 由前台传入
		// 计算核保顺序号
		// 个人理陪险种核保核保轨迹表<LLUWSub>
		// 校验险种结论
		String tPassFlag = tLLUWMasterSchema.getPassFlag();// 核保结论标识
		// 验证已经做过[加费承保录入],但是下其他的核保结论
		if (CheckLLUWAddFee(tLLUWMasterSchema)
				&& !CheckLLUWSpec(tLLUWMasterSchema)) {
			if (!tPassFlag.equals("3")) {
				mErrors.addOneError("已经做过[加费承保录入]，应该对险种下[加费承保]结论!!!");
				return false;
			}
		}
		// 验证已经做过[特约承保录入],但是下其他的核保结论
		if (CheckLLUWSpec(tLLUWMasterSchema)
				&& !CheckLLUWAddFee(tLLUWMasterSchema)) {
			if (!tPassFlag.equals("4")) {
				mErrors.addOneError("已经做过[特约承保录入]，应该对险种下[特约承保]结论!!!");
				return false;
			}

		}
		// 验证已经做过[加费承保录入]和[特约承保录入],但是下其他的核保结论
		if (CheckLLUWSpec(tLLUWMasterSchema)
				&& CheckLLUWAddFee(tLLUWMasterSchema)) {
			if (!tPassFlag.equals("3") && !tPassFlag.equals("4")) {
				mErrors
						.addOneError("已经做过[加费承保录入]和[特约承保录入]，应该对险种下[加费承保]或[特约承保]结论!!!");
				return false;
			}

		}
		// 验证没有做过[加费承保录入],但是下[加费承保]结论，允许通过 2006-02-07 modify by zhaorx
		// if(!CheckLLUWAddFee(tLLUWMasterSchema)&&!CheckLLUWSpec(tLLUWMasterSchema)&&
		// tPassFlag.equals("3"))
		// {
		// if(tPassFlag.equals("3"))
		// {
		// mErrors.addOneError("没有做过[加费承保录入]，不能对险种下[加费承保]结论!!!");
		// return false;
		// }
		// }

		// 验证没有做过[特约承保录入],但是下[特约承保]结论
		if (!CheckLLUWSpec(tLLUWMasterSchema)
				&& !CheckLLUWAddFee(tLLUWMasterSchema) && tPassFlag.equals("4")) {
			if (tPassFlag.equals("4")) {
				mErrors.addOneError("没有做过[特约承保录入]，不能对险种下[特约承保]结论!!!");
				return false;
			}

		}

		LLUWSubSet tLLUWSubSet = new LLUWSubSet();
		LLUWSubDB tLLUWSubDB = new LLUWSubDB();
		tLLUWSubDB.setPolNo(mLLUWMasterSchema.getPolNo()); // 合同号码
		tLLUWSubDB.setCaseNo(mLLUWMasterSchema.getCaseNo()); // 分案号
		tLLUWSubDB.setBatNo(mLLUWMasterSchema.getBatNo()); // 批次号

		tLLUWSubSet.set(tLLUWSubDB.query());
		if (tLLUWSubSet.size() == 0) {
			tLLUWMasterSchema.setUWNo("1"); // 核保顺序
		} else {
			int tUWNo = tLLUWSubSet.size() + 1;
			tLLUWMasterSchema.setUWNo(tUWNo); // 核保顺序
		}

		tLLUWMasterSchema.setGrpContNo(tLCPolDB.getGrpContNo());
		tLLUWMasterSchema.setContNo(tLCPolDB.getContNo());
		tLLUWMasterSchema.setProposalContNo(tLCPolDB.getProposalNo());
		tLLUWMasterSchema.setPolNo(tLCPolDB.getPolNo());
		tLLUWMasterSchema.setProposalNo(tLCPolDB.getProposalNo());
		tLLUWMasterSchema.setInsuredNo(tLCPolDB.getInsuredNo());
		tLLUWMasterSchema.setInsuredName(tLCPolDB.getInsuredName());
		tLLUWMasterSchema.setAppntNo(tLCPolDB.getAppntNo());
		tLLUWMasterSchema.setAppntName(tLCPolDB.getAppntName());
		tLLUWMasterSchema.setAgentCode(tLCPolDB.getAgentCode());
		tLLUWMasterSchema.setAgentGroup(tLCPolDB.getAgentGroup());
		tLLUWMasterSchema.setManageCom(tGI.ManageCom);
		tLLUWMasterSchema.setOperator(tGI.Operator);
		tLLUWMasterSchema.setMakeDate(mCurrentDate);
		tLLUWMasterSchema.setMakeTime(mCurrentTime);
		tLLUWMasterSchema.setModifyDate(mCurrentDate);
		tLLUWMasterSchema.setModifyTime(mCurrentTime);
		map.put(tLLUWMasterSchema, "DELETE&INSERT");
		/**
		 * =============================================================
		 * 修改原因：需求变动，在对附加险下“不自动续保”结论时候，发出“不自动续保” 通知书，加到[综合打印]中的打印队列 修改人 ： 万泽辉
		 * 修改时间：2005/12/15 19:30
		 * ==============================================================
		 */
		String tContNo = tLCPolDB.getContNo(); // 保单号
		String tPolNo = tLCPolDB.getPolNo(); // 险种号
		String tClmNo = mLLUWMasterSchema.getCaseNo(); // 赔案号
		if (tPassFlag.equals("b")) {

			String tSQL = "select managecom,agentcode from lccont where contno = '"
					+ "?contno?" + "'";
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(tSQL);
			sqlbv.put("contno", tContNo);
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv);
			String mPrtSeq = "";
			if (!(tSSRS.GetText(1, 1).equals("") || tSSRS.GetText(1, 1) == null)) {
				String strNoLimit = PubFun.getNoLimit(tSSRS.GetText(1, 1));
				mPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNoLimit);
			} else {
				String strNoLimit = PubFun.getNoLimit(tGI.ManageCom);
				mPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNoLimit);
			}

			if (mPrtSeq == "" || mPrtSeq == null || mPrtSeq.equals("")) {
				buildError("outputXML", "在生成打印流水号时发生错误,发出打印失败。对险种下核保结论失败！");
				return false;
			}

			// 准备打印数据
			LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
			mLOPRTManagerSchema.setPrtSeq(mPrtSeq);
			mLOPRTManagerSchema.setOtherNo(tContNo);
			mLOPRTManagerSchema.setCode("LP88");
			mLOPRTManagerSchema.setStandbyFlag1(tClmNo);
			mLOPRTManagerSchema.setStandbyFlag2(tPolNo); // 为删除时查询使用 add by
			// zhoulei
			mLOPRTManagerSchema.setOtherNoType("02");
			mLOPRTManagerSchema.setManageCom(tSSRS.GetText(1, 1));
			mLOPRTManagerSchema.setAgentCode(tSSRS.GetText(1, 2));
			mLOPRTManagerSchema.setReqCom(tGI.ManageCom);
			mLOPRTManagerSchema.setReqOperator(tGI.Operator);
			mLOPRTManagerSchema.setExeCom(tGI.ManageCom);
			mLOPRTManagerSchema.setExeOperator(tGI.Operator);
			mLOPRTManagerSchema.setPrtType("0");
			mLOPRTManagerSchema.setStateFlag("0");
			mLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
			mLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
			mLOPRTManagerSchema.setOldPrtSeq(mPrtSeq);
			map.put(mLOPRTManagerSchema, "DELETE&INSERT");
		} else {
			// 由于核保结论可下多次,为防止冗余数据每次删除上次记录,add by zhoulei
			LOPRTManagerSet tLOPRTManagerSet = new LOPRTManagerSet();
			LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
			tLOPRTManagerDB.setCode("LP88");
			tLOPRTManagerDB.setOtherNo(tContNo);
			tLOPRTManagerDB.setStandbyFlag1(tClmNo);
			tLOPRTManagerDB.setStandbyFlag2(tPolNo);
			tLOPRTManagerDB.setOtherNoType("02");
			tLOPRTManagerSet = tLOPRTManagerDB.query();
			if (tLOPRTManagerDB.mErrors.needDealError()) {
				mErrors.addOneError("查询打印数据失败!");
				return false;
			}
			if (tLOPRTManagerSet != null) {
				map.put(tLOPRTManagerSet, "DELETE");
			}

		}
		// 个人理陪险种核保核保轨迹表<LLUWSub>
		LLUWSubSchema tLLUWSubSchema = new LLUWSubSchema();
		Reflections tRef = new Reflections(); // [声明转移表的数据的对象]
		tRef.transFields(tLLUWSubSchema, tLLUWMasterSchema);
		map.put(tLLUWSubSchema, "INSERT");
		return true;
	}

	/**
	 * prepareOutputData
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		try {
			mResult.clear();
			mResult.add(tGI);
			mResult.add(map);
			return true;
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLSecondUWRiskBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}

	}

	/**
	 * 返回错误信息
	 * 
	 * @return CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	}

	/**
	 * 错误构建
	 * 
	 * @param szFunc
	 *            String
	 * @param szErrMsg
	 *            String
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "SpecPrintBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * 判断是否做过加费承保录入
	 */
	private boolean CheckLLUWAddFee(LLUWMasterSchema tLLUWMaster) {
		String cContNo = tLLUWMaster.getContNo(); // 保单号
		String tPolNo = tLLUWMaster.getPolNo(); // 险种号
		String tSql = "";
		ExeSQL tExecSql = new ExeSQL();
		SSRS tSsrs = new SSRS();
		tSql = "select * from lluwpremmaster where contno = '" + "?contno?"
				+ "' and polno = '" + "?polno?" + "'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tSql);
		sqlbv1.put("contno", cContNo);
		sqlbv1.put("polno", tPolNo);
		tSsrs = tExecSql.execSQL(sqlbv1);
		if (tSsrs.getMaxRow() < 1) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 判断是否做过特约承保录入
	 */
	private boolean CheckLLUWSpec(LLUWMasterSchema tLLUWMaster) {
		String cContNo = tLLUWMaster.getContNo(); // 保单号
		String tPolNo = tLLUWMaster.getPolNo(); // 险种号
		String tSql = "";
		ExeSQL tExecSql = new ExeSQL();
		SSRS tSsrs = new SSRS();
		tSql = "select * from LLUWSpecMaster where contno = '" + "?contno?"
				+ "'";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tSql);
		sqlbv2.put("contno", cContNo);
		sqlbv2.put("polno", tPolNo);
		tSsrs = tExecSql.execSQL(sqlbv2);
		if (tSsrs.getMaxRow() < 1) {
			return false;
		} else {
			return true;
		}
	}

}
