package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.f1print.PrintService;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LLAffixSchema;
import com.sinosoft.lis.schema.LLClaimUserSchema;
import com.sinosoft.lis.schema.LLRegisterSchema;
import com.sinosoft.lis.vschema.LLAffixSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 单证打印：自定义索赔文件签收清单--PCT002,SpwjqsqdGrC000100.vts
 * </p>
 * <p>
 * 返还给客户的资料签收清单
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author 续涛，2005.07.28--2005.07.28
 * @version 1.0
 */
public class LLPRTUserDefinedBillBL implements PrintService {
private static Logger logger = Logger.getLogger(LLPRTUserDefinedBillBL.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private MMap mMMap = new MMap();
	private GlobalInput mG = new GlobalInput();
	private LLAffixSet mLLAffixSet = new LLAffixSet(); // 报案附件表
	private TransferData mTransferData = new TransferData();
	private String mClmNo = ""; // 赔案号
	private String mCusNo = ""; // 客户号

	public LLPRTUserDefinedBillBL() {
	}

	/*
	 * *************加入数据，用于调试********
	 */
	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.ComCode = "86";
		tG.ManageCom = "86";
		tG.Operator = "001";
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ClmNo", "90000001455");
		tTransferData.setNameAndValue("CustNo", "0000531640");

		LLAffixSet tLLAffixSet = new LLAffixSet(); // 报案附件表
		LLAffixSchema tLLAffixSchema = new LLAffixSchema();
		tLLAffixSchema.setAffixCode("CLM003");// 单证代码
		tLLAffixSchema.setAffixName("有关合同变更的批单(如合同曾作变更处理请提供)");// 单证名称
		tLLAffixSchema.setSubFlag("0");// 是否提交
		tLLAffixSchema.setReadyCount("1");// 页数
		tLLAffixSchema.setProperty("0");// 单证属性标志
		tLLAffixSchema.setReturnFlag("0");// 是否退还原件
		tLLAffixSet.add(tLLAffixSchema);

		// // 准备传输数据 VData
		// VData tVData = new VData();
		// tVData.add(tG);
		// tVData.add(tTransferData);
		// tVData.add(tLLAffixSet);
		//
		// LLPRTUserDefinedBillBL tLLPRTUserDefinedBillBL = new
		// LLPRTUserDefinedBillBL();
		// if (tLLPRTUserDefinedBillBL.submitData(tVData, "") == false)
		// {
		// logger.debug("#######----索赔文件签收清单打印出错----##############");
		// }

	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */

	public boolean submitData(VData cInputData, String cOperate) {

		logger.debug("----------索赔文件签收清单-----LLPRTUserDefinedBillBL测试-----开始----------");

		// 得到外部传入的数据,将数据备份到本类中
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
		logger.debug("----------索赔文件签收清单-----LLPRTUserDefinedBillBL测试-----结束----------");
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
		mInputData = cInputData;
		mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);
		mLLAffixSet = (LLAffixSet) mInputData.getObjectByObjectName(
				"LLAffixSet", 0);
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mClmNo = (String) mTransferData.getValueByName("ClmNo");
		mCusNo = (String) mTransferData.getValueByName("CustNo");

		if (mG == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLPRTUserDefinedBillBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接受的mG信息全部为空!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (mLLAffixSet == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLPRTUserDefinedBillBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接受的单证信息全部为空!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mClmNo == null || mCusNo == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLPRTUserDefinedBillBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接受的赔案号 或 出险人号 信息全部为空!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		XmlExport tXmlExport = new XmlExport(); // 新建一个XmlExport的实例
		tXmlExport.createDocument("SpwjqsqdGrC000100.vts", "");

		LLPRTPubFunBL tLLPRTPubFunBL = new LLPRTPubFunBL();

		// 理赔类型
		String tClaimType = tLLPRTPubFunBL.getSLLReportReason(mClmNo, mCusNo);
		// 出险人姓名
		String tSql = "select a.name from ldperson a where "
				+ "a.customerno = '" + "?cusno?" + "'";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tSql);
		sqlbv.put("cusno", mCusNo);
		ExeSQL tExeSQL = new ExeSQL();
		String tCustName = tExeSQL.getOneValue(sqlbv);
		// 申请人
		LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema();
		tLLRegisterSchema = tLLPRTPubFunBL.getLLRegister(mClmNo);
		String tRgtName = tLLRegisterSchema.getRgtantName();
		if (tRgtName == null) {
			tRgtName = "";
		}
		// 当前时间-----签收日期：
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";

		LLClaimUserSchema tLLClaimUserSchema = new LLClaimUserSchema();
		tLLClaimUserSchema = tLLPRTPubFunBL.getLLClaimUser(mG.Operator);
		String tOperator = tLLClaimUserSchema.getUserName();

		logger.debug("################################################");
		logger.debug("**********以下为《TextTag》变量赋值***************");
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		tTextTag.add("ClaimNo", mClmNo); // 陪案号
		tTextTag.add("ClaimType", tClaimType); // 理赔类型
		tTextTag.add("AccName", tCustName); // 出险人
		tTextTag.add("AppName", tRgtName); // 申请人
		tTextTag.add("ClaimTag", "返还");// 用于返还给客户的资料签收清单
		tTextTag.add("HangUp", tOperator);// 提交人应该是操作员
		tTextTag.add("Operator", "");// 签收人那里应该是空白，给客户签字
		tTextTag.add("SysDate", SysDate); // 签收日期

		logger.debug("**********以下为《TextTag》变量赋值，准备打印赔案号条码***************");
		tTextTag.add("BarCode1", "CA041");
		tTextTag
				.add(
						"BarCodeParam1",
						"BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");
		tTextTag.add("BarCode2", mClmNo);
		tTextTag
				.add(
						"BarCodeParam2",
						"BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");
		logger.debug("------赔案号条码打印成功-------");

		logger.debug("################################################");
		logger.debug("**********以下为《ListTable》变量赋值***************");
		ListTable tListTable = new ListTable();
		tListTable.setName("GRID");
		String[] Title = new String[5];
		Title[0] = "";
		Title[1] = "";
		Title[2] = "";
		Title[3] = "";
		Title[4] = "";

		int n = mLLAffixSet.size();
		for (int i = 1; i <= n; i++) {
			LLAffixSchema ttLLAffixSchema = new LLAffixSchema();
			ttLLAffixSchema = mLLAffixSet.get(i);
			int tReadCont = ttLLAffixSchema.getReadyCount();// 页数
			String tSubFlag = ttLLAffixSchema.getSubFlag();// 是否提交
			String tProperty = ttLLAffixSchema.getProperty();// 提交形式
			String tReturnFlag = ttLLAffixSchema.getReturnFlag();// 是否退还原件

			String[] Stra = new String[5];
			Stra[0] = i + "、" + ttLLAffixSchema.getAffixName();// 文件名称
			// 是否提交
			if (tSubFlag.equals("0")) {
				Stra[1] = "是";
			} else {
				Stra[1] = "否";
			}

			// 页数
			Stra[2] = String.valueOf(tReadCont);

			// 提交形式
			if (tProperty.equals("0")) {
				Stra[3] = "原件";
			} else {
				Stra[3] = "复印件";
			}
			// 是否退还原件
			if (tReturnFlag.equals("0")) {
				Stra[4] = "是";
			} else {
				Stra[4] = "否";
			}

			tListTable.add(Stra);
		}

		logger.debug("################################################");
		logger.debug("**********以下将标签 和 列表 添加到 tXmlExport 实例*****");
		// 添加动态文本标签的数组，参数为一个TextTag
		if (tTextTag.size() > 0) {
			tXmlExport.addTextTag(tTextTag);
		}
		// 添加一个列表，参数为ListTag和动态列表的表头数组
		tXmlExport.addListTable(tListTable, Title);

		logger.debug("################################################");
		logger.debug("**********以下将tXmlExport实例打包，准备返回*****");
		mResult.clear();
		mResult.add(tXmlExport);
		logger.debug("xmlexport=" + tXmlExport);

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
			tError.moduleName = "LLClaimCalCheckBL";
			tError.functionName = "PubSubmit.submitData";
			tError.errorMessage = "数据提交失败!";
			//
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;

	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

}
