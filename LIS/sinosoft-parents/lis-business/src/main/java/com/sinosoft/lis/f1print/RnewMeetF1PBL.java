/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.f1print;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.f1j.ss.BookModelImpl;
import com.f1j.ss.ReadParams;
import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LABranchGroupDB;
import com.sinosoft.lis.db.LAComDB;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCGrpAppntDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.db.RnewRReportDB;
import com.sinosoft.lis.db.RnewRReportPrtDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LAComSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.RnewRReportSchema;
import com.sinosoft.lis.vschema.RnewRReportPrtSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author zhangxing
 * @version 1.0
 */
public class RnewMeetF1PBL {
private static Logger logger = Logger.getLogger(RnewMeetF1PBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private String mSql = "";
	private VData mResult = new VData();
	// 保费保额计算出来后的精确位数
	private String FORMATMODOL = "0.00";
	// 数字转换对象
	private DecimalFormat mDecimalFormat = new DecimalFormat(FORMATMODOL);
	String mSumPrem = "";
	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private LCContSchema mLCContSchema = new LCContSchema();
	private LAAgentSchema mLAAgentSchema = new LAAgentSchema();

	private String mOperate = "";
	private String mPrtSeq = "";
	private String mCode = "";
	private String mAgentCode = "";
	boolean MEETFlag = false;
	private String tWebPath = "";
	private String mOldPrtSeq = "";

	public RnewMeetF1PBL() {
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
		mOperate = cOperate;
		try {

			if (!cOperate.equals("CONFIRM") && !cOperate.equals("PRINT")
					&& !cOperate.equals("PRINT1") && !cOperate.equals("PRINT2")
					&& !cOperate.equals("PRINT3") && !cOperate.equals("PRINT4")
					&& !cOperate.equals("PRINTa") && !cOperate.equals("PRINTb")
					&& !cOperate.equals("PRINTc") && !cOperate.equals("PRINTd")
					&& !cOperate.equals("PRINTe") && !cOperate.equals("PRINTf")
					&& !cOperate.equals("PRINTg") && !cOperate.equals("PRINTh")
					&& !cOperate.equals("PRINTi") && !cOperate.equals("PRINTj")
					&& !cOperate.equals("PRINTk") && !cOperate.equals("PRINTl")
					&& !cOperate.equals("PRINTm") && !cOperate.equals("PRINTn")
					&& !cOperate.equals("PRINTo") && !cOperate.equals("PRINTp")
					&& !cOperate.equals("PRINTq") && !cOperate.equals("PRINTr")
					&& !cOperate.equals("PRINTs") && !cOperate.equals("PRINTt")) {
				buildError("submitData", "不支持的操作字符串");
				return false;
			}

			// 得到外部传入的数据，将数据备份到本类中
			if (!getInputData(cInputData)) {
				return false;
			}

			if (cOperate.equals("PRINT")) {

				mResult.clear();

				// 准备所有要打印的数据
				getPrintData();

			}
			if (cOperate.equals("PRINT1")) {

				mResult.clear();

				// 准备所有要打印的数据
				getPrintData2();

			}
			if (cOperate.equals("PRINT2")) {

				mResult.clear();

				// 准备所有要打印的数据
				getPrintData3();

			}
			if (cOperate.equals("PRINT3")) {

				mResult.clear();

				// 准备所有要打印的数据
				getPrintDataMeet();
				// getPrintData1();

			}
			if (cOperate.equals("PRINT4")) {

				mResult.clear();

				// 准备所有要打印的数据
				getPrintData4();

			}
			if (cOperate.equals("PRINTa")) {

				mResult.clear();

				// 准备所有要打印的数据
				getPrintDataa();

			}
			if (cOperate.equals("PRINTb")) {

				mResult.clear();

				// 准备所有要打印的数据
				getPrintDatab();

			}
			if (cOperate.equals("PRINTc")) {

				mResult.clear();

				// 准备所有要打印的数据
				getPrintDatac();

			}
			if (cOperate.equals("PRINTd")) {

				mResult.clear();

				// 准备所有要打印的数据
				getPrintDatad();

			}
			if (cOperate.equals("PRINTe")) {

				mResult.clear();

				// 准备所有要打印的数据
				getPrintDatae();

			}
			if (cOperate.equals("PRINTf")) {

				mResult.clear();

				// 准备所有要打印的数据
				getPrintDataf();

			}
			if (cOperate.equals("PRINTg")) {

				mResult.clear();

				// 准备所有要打印的数据
				getPrintDatag();

			}
			if (cOperate.equals("PRINTh")) {

				mResult.clear();

				// 准备所有要打印的数据
				getPrintDatah();

			}
			if (cOperate.equals("PRINTi")) {

				mResult.clear();

				// 准备所有要打印的数据
				getPrintDatai();

			}
			if (cOperate.equals("PRINTj")) {

				mResult.clear();

				// 准备所有要打印的数据
				getPrintDataj();

			}
			if (cOperate.equals("PRINTk")) {

				mResult.clear();

				// 准备所有要打印的数据
				getPrintDatak();

			}
			if (cOperate.equals("PRINTl")) {

				mResult.clear();

				// 准备所有要打印的数据
				getPrintDatal();

			}
			if (cOperate.equals("PRINTm")) {

				mResult.clear();

				// 准备所有要打印的数据
				getPrintDatam();

			}
			if (cOperate.equals("PRINTn")) {

				mResult.clear();

				// 准备所有要打印的数据
				getPrintDatan();

			}
			if (cOperate.equals("PRINTo")) {

				mResult.clear();

				// 准备所有要打印的数据
				getPrintDatao();

			}
			if (cOperate.equals("PRINTp")) {

				mResult.clear();

				// 准备所有要打印的数据
				getPrintDatap();

			}
			if (cOperate.equals("PRINTq")) {

				mResult.clear();

				// 准备所有要打印的数据
				getPrintDataq();

			}
			if (cOperate.equals("PRINTr")) {

				mResult.clear();

				// 准备所有要打印的数据
				getPrintDatar();

			}
			if (cOperate.equals("PRINTs")) {

				mResult.clear();

				// 准备所有要打印的数据
				getPrintDatas();

			}
			if (cOperate.equals("PRINTt")) {

				mResult.clear();

				// 准备所有要打印的数据
				getPrintDatat();

			}

			return true;

		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("submitData", ex.toString());
			return false;
		}
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mLOPRTManagerSchema.setSchema((LOPRTManagerSchema) cInputData
				.getObjectByObjectName("LOPRTManagerSchema", 0));

		if (mGlobalInput == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}
		tWebPath = (String) cInputData.getObjectByObjectName("String", 0);
		if (tWebPath == null || tWebPath.trim().equals("")) {
			ExeSQL exeSql2 = new ExeSQL();
			SSRS tSSRS = new SSRS();
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql("select sysvarvalue From ldsysvar where sysvar='DataPrintCombine'");
			tSSRS = exeSql2.execSQL(sqlbv1);
			tWebPath = tSSRS.GetText(1, 1);
		}
		logger.debug("在后台中的路径======" + tWebPath);
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "RefuseAppF1PBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	// //////////////////////////为了实现多个VTS合并分别生成带有数据的VTS-1：MEET.VTS/////
	private void getPrintData1() throws Exception {
		mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		LCContDB tLCContDB = new LCContDB();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);

		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}

		// 需要判断是否已经打印？！
		if (tLOPRTManagerDB.getStateFlag().equals("0")) {
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema(); // get all
																// message！

			// 打印时传入的是主险投保单的投保单号
			tLCContDB.setContNo(mLOPRTManagerSchema.getOtherNo());
			logger.debug("test by yaory:"
					+ mLOPRTManagerSchema.getOtherNo());
			if (!tLCContDB.getInfo()) {
				mErrors.copyAllErrors(tLCContDB.mErrors);
				throw new Exception("在获取保单信息时出错！");
			}
			mLCContSchema = tLCContDB.getSchema();

			// 查询打印队列的信息
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();

			mOldPrtSeq = mLOPRTManagerSchema.getOldPrtSeq();

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("getprintData", "无效的打印状态");
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("getprintData", "该打印请求不是在请求状态");
			}
			if (!mLOPRTManagerSchema.getCode().equals("")
					&& !mLOPRTManagerSchema.getCode().equals(null)
					&& mLOPRTManagerSchema.getCode().equals("G04")) {

			} else {
				// 投保人地址和邮编
				LCAppntDB tLCAppntDB = new LCAppntDB();
				LCGrpAppntDB tLCGrpAppntDB = new LCGrpAppntDB();// Modify by
																// zhaorx
																// 2006-08-11
				tLCAppntDB.setContNo(mLCContSchema.getContNo());
				tLCGrpAppntDB.setGrpContNo(mLCContSchema.getGrpContNo());
				if (mLCContSchema.getContType().equals("1")) {
					if (tLCAppntDB.getInfo() == false) {
						mErrors.copyAllErrors(tLCAppntDB.mErrors);

						throw new Exception("在取得打印队列中数据时发生错误");
					}
				} else {
					if (tLCGrpAppntDB.getInfo() == false) {
						mErrors.copyAllErrors(tLCGrpAppntDB.mErrors);

						throw new Exception("在取得打印队列中数据时发生错误");
					}
				}
			}
			mAgentCode = mLCContSchema.getAgentCode();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLAAgentDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLABranchGroupDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}

			// 2-1 查询体检子表
			String[] MeetTitle = new String[2];
			MeetTitle[0] = "ID";
			MeetTitle[1] = "ITEM";
			ListTable tListTable = new ListTable();
			String str[] = null;
			tListTable.setName("WJ"); // 对应模版体检部分的行对象名

			RnewRReportPrtSet tRnewRReportPrtSet = new RnewRReportPrtSet();
			RnewRReportPrtDB tRnewRReportPrtDB = new RnewRReportPrtDB();
			/**
			 * @todo 如果OldPrtSeq有值则用OldPrtSeq查询否则用prtseq查询
			 */
			if (mOldPrtSeq != null && !mOldPrtSeq.equals("")) {
				tRnewRReportPrtDB.setPrtSeq(mOldPrtSeq);
			} else {
				tRnewRReportPrtDB.setPrtSeq(mPrtSeq);
			}
			tRnewRReportPrtSet = tRnewRReportPrtDB.query();

			for (int i = 1; i <= tRnewRReportPrtSet.size(); i++) {

				str = new String[2];
				str[0] = (new Integer(i)).toString(); // 序号
				str[1] = getAskName(tRnewRReportPrtSet.get(i).getAskCode()); // 序号对应的内容
				tListTable.add(str);
			}

			// VtsFileCombine vtsfilecombine = new VtsFileCombine();
			// BookModelImpl tb =
			// vtsfilecombine.dataCombine("D:/lis/ui/f1print/NCLtemplate/try1.vts",
			// "D:/lis/ui/f1print/NCLtemplate/try.vts");
			// vtsfilecombine.write(tb,
			// "D:/lis/ui/f1print/NCLtemplate/new.vts");

			String remark = "";
			// remark="new.vts";
			remark = "HbWjTzs.vts";

			// ////////////////////end write by yaory-2007-7-4-16:39////
			XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例

			// xmlExport.createDocument("Meet.vts", ""); //最好紧接着就初始化xml文档
			xmlExport.createDocument(remark, ""); // 最好紧接着就初始化xml文档
			TextTag texttag = new TextTag();
			String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
					+ StrTool.getDay() + "日";
			if (mLOPRTManagerSchema.getCode() != null
					&& mLOPRTManagerSchema.getCode().equals("89")) {
				texttag.add("BarCode1", "UN025");
			} else if (mLOPRTManagerSchema.getCode() != null
					&& mLOPRTManagerSchema.getCode().equals("LP89")) // 2006-9-8
																		// 周磊
																		// 修改理赔核保通知编码
			{
				texttag.add("BarCode1", "UN027");
			} else {
				texttag.add("BarCode1", "UN012");
			}
			/**
			 * ==========================================================================
			 * 修改人 ： 万泽辉 修改时间： 2005/12/07 ManageComName: 取6位编码的中支机构
			 * LABranchGroup.Name：取8位编码的营业销售部
			 * ==========================================================================
			 */
			// 中支机构名称
			String tManageComCode = mLCContSchema.getManageCom()
					.substring(0, 6);
			String tManageComName = getComName(tManageComCode);
			// 营业销售部名称
			String tBranchGroupCode = mLCContSchema.getManageCom();
			String tBranchGroupName = getComName(tBranchGroupCode);
			mSumPrem = "￥ " + mDecimalFormat.format(mLCContSchema.getPrem());

			texttag
					.add(
							"BarCodeParam1",
							"BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");
			texttag.add("BarCode2", mLOPRTManagerSchema.getPrtSeq());
			texttag
					.add(
							"BarCodeParam1",
							"BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");

			texttag.add("LCCont.ProposalContNo", mLCContSchema.getContNo());
			texttag.add("ManageComName", tManageComName);
			texttag.add("LCCont.AppntName", mLCContSchema.getAppntName());
			texttag.add("AppntName", mLCContSchema.getAppntName());
			texttag.add("LCCont.InsuredName", mLCContSchema.getInsuredName());
			if (!mLOPRTManagerSchema.getCode().equals("89")
					&& !mLOPRTManagerSchema.getCode().equals("LP89")) {
				RnewRReportDB tRnewRReportDB = new RnewRReportDB();
				RnewRReportSchema tRnewRReportSchema = new RnewRReportSchema();
				tRnewRReportDB.setContNo(mLCContSchema.getContNo());
				tRnewRReportDB.setPrtSeq(mLOPRTManagerSchema.getOldPrtSeq());
				tRnewRReportSchema = tRnewRReportDB.query().get(1);

				texttag.add("CustomerName", tRnewRReportSchema.getName());
			}

			else {
				texttag.add("CustomerName", mLCContSchema.getInsuredName());
			}

			texttag.add("LABranchGroup.Name", getComName(mLCContSchema
					.getManageCom())
					+ " " + getUpComName(mLAAgentSchema.getBranchCode()));
			texttag.add("LAAgent.Name", mLAAgentSchema.getName());
			texttag.add("LAAgent.AgentCode", mLAAgentSchema.getAgentCode());
			texttag.add("LCContl.UWOperator", mLOPRTManagerSchema
					.getReqOperator());
			texttag.add("LOPRTManager.Remark", mLOPRTManagerSchema.getRemark());
			texttag.add("SumPrem", mSumPrem);
			texttag.add("SysDate", SysDate);

			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}
			xmlExport.addDisplayControl("displayResult");
			xmlExport.addListTable(tListTable, MeetTitle); // 保存问题信息
			// xmlExport.outputDocumentToFile("D:/lis/ui/f1print/NCLtemplate/",
			// "testHZ"); //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlExport);

		} else {
			buildError("MeetF1BL", "已经打印面见通知书！");

		}

	}

	// //////////////////////////为了实现多个VTS合并分别生成带有数据的VTS-1：组合/////
	private void getPrintData4() throws Exception {
		mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		LCContDB tLCContDB = new LCContDB();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);

		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}
		// 需要判断是否已经打印？！
		if (tLOPRTManagerDB.getStateFlag().equals("0")) {
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema(); // get all
																// message！

			// 打印时传入的是主险投保单的投保单号
			tLCContDB.setContNo(mLOPRTManagerSchema.getOtherNo());
			logger.debug("test by yaory:"
					+ mLOPRTManagerSchema.getOtherNo());
			if (!tLCContDB.getInfo()) {
				mErrors.copyAllErrors(tLCContDB.mErrors);
				throw new Exception("在获取保单信息时出错！");
			}
			mLCContSchema = tLCContDB.getSchema();

			// 查询打印队列的信息
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
			mOldPrtSeq = mLOPRTManagerSchema.getOldPrtSeq();

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("getprintData", "无效的打印状态");
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("getprintData", "该打印请求不是在请求状态");
			}
			if (!mLOPRTManagerSchema.getCode().equals("")
					&& !mLOPRTManagerSchema.getCode().equals(null)
					&& mLOPRTManagerSchema.getCode().equals("G04")) {
			} else {
				// 投保人地址和邮编
				LCAppntDB tLCAppntDB = new LCAppntDB();
				tLCAppntDB.setContNo(mLCContSchema.getContNo());
				if (tLCAppntDB.getInfo() == false) {
					mErrors.copyAllErrors(tLCAppntDB.mErrors);

					throw new Exception("在取得打印队列中数据时发生错误");
				}
			}
			mAgentCode = mLCContSchema.getAgentCode();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLAAgentDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLABranchGroupDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}

			// 2-1 查询体检子表
			String[] MeetTitle = new String[2];
			MeetTitle[0] = "ID";
			MeetTitle[1] = "ITEM";
			ListTable tListTable = new ListTable();
			String str[] = null;
			tListTable.setName("WJ"); // 对应模版体检部分的行对象名

			RnewRReportPrtSet tRnewRReportPrtSet = new RnewRReportPrtSet();
			RnewRReportPrtDB tRnewRReportPrtDB = new RnewRReportPrtDB();
			/**
			 * @todo 如果OldPrtSeq有值则用OldPrtSeq查询否则用prtseq查询
			 */
			if (mOldPrtSeq != null && !mOldPrtSeq.equals("")) {
				tRnewRReportPrtDB.setPrtSeq(mOldPrtSeq);
			} else {
				tRnewRReportPrtDB.setPrtSeq(mPrtSeq);
			}
			tRnewRReportPrtSet = tRnewRReportPrtDB.query();

			for (int i = 1; i <= tRnewRReportPrtSet.size(); i++) {

				str = new String[2];
				str[0] = (new Integer(i)).toString(); // 序号
				str[1] = getAskName(tRnewRReportPrtSet.get(i).getAskCode()); // 序号对应的内容
				tListTable.add(str);
			}

			// VtsFileCombine vtsfilecombine = new VtsFileCombine();
			// BookModelImpl tb =
			// vtsfilecombine.dataCombine("D:/lis/ui/f1print/NCLtemplate/try1.vts",
			// "D:/lis/ui/f1print/NCLtemplate/try3.vts");
			// vtsfilecombine.write(tb,
			// "D:/lis/ui/f1print/NCLtemplate/new1.vts");
			//
			// String remark="";
			// remark="new1.vts";//这些是测试用的

			// /////////////////////进行合并 write by yaory -2007-7-4-16:39////
			// first query how many questionaires
			ExeSQL exeSql = new ExeSQL();
			SSRS testSSRS = new SSRS();
			// testSSRS = exeSql.execSQL("select sysvarvalue From ldsysvar where
			// sysvar='DataPrintCombine'");
			// String TemplatePath=testSSRS.GetText(1,1);
			String TemplatePath = tWebPath;
			logger.debug("路径-模板-in combine===" + TemplatePath);
			logger.debug("呵呵，很高兴=====" + mLOPRTManagerSchema.getPrtSeq());
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql("select askcode from RnewRReportPrt where prtseq='"
					+ "?prtseq?" + "'");
			sqlbv2.put("prtseq", mLOPRTManagerSchema.getPrtSeq());
			testSSRS = exeSql.execSQL(sqlbv2);
			String remark = "";

			if (testSSRS.MaxRow == 0) {
				remark = "HbWjTzs.vts";
			} else {
				VtsFileCombine vtsfilecombine = new VtsFileCombine();
				int array = testSSRS.getMaxRow();
				String[] paths = new String[array + 1];

				for (int i = 1; i <= testSSRS.MaxRow; i++) {
					if (testSSRS.GetText(i, 1).equals("87")) {
						paths[i - 1] = TemplatePath + "liveData.vts";
					} else if (testSSRS.GetText(i, 1).equals("88")) {
						paths[i - 1] = TemplatePath + "financeData.vts";
					} else if (testSSRS.GetText(i, 1).equals("89")) {
						paths[i - 1] = TemplatePath + "DXData.vts";
					} else if (testSSRS.GetText(i, 1).equals("90")) {
						paths[i - 1] = TemplatePath + "XCData.vts";
					} else if (testSSRS.GetText(i, 1).equals("91")) {
						paths[i - 1] = TemplatePath + "TNBData.vts";
					} else if (testSSRS.GetText(i, 1).equals("92")) {
						paths[i - 1] = TemplatePath + "JZXData.vts";
					} else if (testSSRS.GetText(i, 1).equals("93")) {
						paths[i - 1] = TemplatePath + "XTData.vts";
					} else if (testSSRS.GetText(i, 1).equals("94")) {
						paths[i - 1] = TemplatePath + "XTData.vts";
					} else if (testSSRS.GetText(i, 1).equals("95")) {
						paths[i - 1] = TemplatePath + "NXJKBCWJData.vts";
					} else if (testSSRS.GetText(i, 1).equals("96")) {
						paths[i - 1] = TemplatePath + "GYData.vts";
					} else if (testSSRS.GetText(i, 1).equals("97")) {
						paths[i - 1] = TemplatePath + "HXDData.vts";
					} else if (testSSRS.GetText(i, 1).equals("98")) {
						paths[i - 1] = TemplatePath + "XHXTData.vts";
					} else if (testSSRS.GetText(i, 1).equals("99")) {
						paths[i - 1] = TemplatePath + "YYEJKWJData.vts";
					} else if (testSSRS.GetText(i, 1).equals("100")) {
						paths[i - 1] = TemplatePath + "sportdcbData.vts";
					} else if (testSSRS.GetText(i, 1).equals("101")) {
						paths[i - 1] = TemplatePath + "CZWYWJData.vts";
					} else if (testSSRS.GetText(i, 1).equals("102")) {
						paths[i - 1] = TemplatePath + "BSWJData.vts";
					} else if (testSSRS.GetText(i, 1).equals("103")) {
						paths[i - 1] = TemplatePath + "JbwjGxaData.vts";
					} else if (testSSRS.GetText(i, 1).equals("104")) {
						paths[i - 1] = TemplatePath + "JszzzyzwjData.vts";
					} else if (testSSRS.GetText(i, 1).equals("105")) {
						paths[i - 1] = TemplatePath + "WcrydcwjData.vts";
					} else if (testSSRS.GetText(i, 1).equals("106")) {
						paths[i - 1] = TemplatePath + "WdrstbwjData.vts";
					} else if (testSSRS.GetText(i, 1).equals("107")) {
						paths[i - 1] = TemplatePath + "JbwjXhxkyData.vts";
					} else if (testSSRS.GetText(i, 1).equals("108")) {
						paths[i - 1] = TemplatePath + "YhyycyrywjData.vts";
					}
				}

				paths[array] = TemplatePath + "announceData.vts";
				BookModelImpl tb = vtsfilecombine.dataCombine(paths);
				vtsfilecombine.write(tb, TemplatePath + "new.vts");
				remark = "new.vts";
			}
			XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例

			// xmlExport.createDocument("Meet.vts", ""); //最好紧接着就初始化xml文档
			xmlExport.createDocument(remark, ""); // 最好紧接着就初始化xml文档
			TextTag texttag = new TextTag();
			String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
					+ StrTool.getDay() + "日";
			/**
			 * ==========================================================================
			 * 修改人 ： 万泽辉 修改时间： 2005/12/07 ManageComName: 取6位编码的中支机构
			 * LABranchGroup.Name：取8位编码的营业销售部 SumPrem：取标准的形式：0.00
			 * ==========================================================================
			 */
			// 改为标准的数据形式:0.00
			mSumPrem = mDecimalFormat.format(mLCContSchema.getPrem());

			texttag.add("BarCode1", mLOPRTManagerSchema.getCode());
			texttag
					.add(
							"BarCodeParam1",
							"BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");
			texttag.add("BarCode2", mLOPRTManagerSchema.getPrtSeq());
			texttag
					.add(
							"BarCodeParam1",
							"BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");

			texttag.add("LCCont.ContNo", mLCContSchema.getContNo());
			texttag.add("LCCont.AppntName", mLCContSchema.getAppntName());
			texttag.add("LCCont.InsuredName", mLCContSchema.getInsuredName());
			texttag.add("LABranchGroup.Name", tLABranchGroupDB.getName());
			texttag.add("LAAgent.Name", mLAAgentSchema.getName());
			texttag.add("LCCont.AgentCode", mLAAgentSchema.getAgentCode());
			texttag.add("LCCont.UWOperator", mLCContSchema.getUWOperator());
			texttag.add("LOPRTManager.Remark", mLOPRTManagerSchema.getRemark());
			texttag.add("SumPrem", mSumPrem);
			texttag.add("SysDate", SysDate);

			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}
			xmlExport.addListTable(tListTable, MeetTitle); // 保存问题信息
			// xmlExport.outputDocumentToFile("D:/lis/ui/f1print/NCLtemplate/",
			// "testHZ"); //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlExport);

		} else {
			buildError("MeetF1BL", "已经打印面见通知书！");

		}

	}

	// //////////////////////////为了实现多个VTS合并分别生成带有数据的VTS-1：scdcb.vts/////
	private void getPrintData2() throws Exception {
		mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		LCContDB tLCContDB = new LCContDB();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);

		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}
		// 需要判断是否已经打印？！
		if (tLOPRTManagerDB.getStateFlag().equals("0")) {
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema(); // get all
																// message！

			// 打印时传入的是主险投保单的投保单号
			tLCContDB.setContNo(mLOPRTManagerSchema.getOtherNo());
			logger.debug("test by yaory:"
					+ mLOPRTManagerSchema.getOtherNo());
			if (!tLCContDB.getInfo()) {
				mErrors.copyAllErrors(tLCContDB.mErrors);
				throw new Exception("在获取保单信息时出错！");
			}
			mLCContSchema = tLCContDB.getSchema();

			// 查询打印队列的信息
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();

			mOldPrtSeq = mLOPRTManagerSchema.getOldPrtSeq();

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("getprintData", "无效的打印状态");
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("getprintData", "该打印请求不是在请求状态");
			}

			if (!mLOPRTManagerSchema.getCode().equals("")
					&& !mLOPRTManagerSchema.getCode().equals(null)
					&& mLOPRTManagerSchema.getCode().equals("G04")) {

			} else {
				// 投保人地址和邮编
				LCAppntDB tLCAppntDB = new LCAppntDB();
				tLCAppntDB.setContNo(mLCContSchema.getContNo());
				if (tLCAppntDB.getInfo() == false) {
					mErrors.copyAllErrors(tLCAppntDB.mErrors);

					throw new Exception("在取得打印队列中数据时发生错误");
				}
			}
			mAgentCode = mLCContSchema.getAgentCode();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLAAgentDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLABranchGroupDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}

			// 2-1 查询体检子表
			String[] MeetTitle = new String[2];
			MeetTitle[0] = "ID";
			MeetTitle[1] = "ITEM";
			ListTable tListTable = new ListTable();
			String str[] = null;
			tListTable.setName("WJ"); // 对应模版体检部分的行对象名

			RnewRReportPrtSet tRnewRReportPrtSet = new RnewRReportPrtSet();
			RnewRReportPrtDB tRnewRReportPrtDB = new RnewRReportPrtDB();

			/**
			 * @todo 如果OldPrtSeq有值则用OldPrtSeq查询否则用prtseq查询
			 */
			if (mOldPrtSeq != null && !mOldPrtSeq.equals("")) {
				tRnewRReportPrtDB.setPrtSeq(mOldPrtSeq);
			} else {
				tRnewRReportPrtDB.setPrtSeq(mPrtSeq);
			}

			tRnewRReportPrtSet = tRnewRReportPrtDB.query();

			for (int i = 1; i <= tRnewRReportPrtSet.size(); i++) {

				str = new String[2];
				str[0] = (new Integer(i)).toString(); // 序号
				str[1] = getAskName(tRnewRReportPrtSet.get(i).getAskCode()); // 序号对应的内容
				tListTable.add(str);
			}

			String remark = "";

			remark = "scdcb.vts";
			logger.debug("测试");

			// ////////////////////end write by yaory-2007-7-4-16:39////
			XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例

			// xmlExport.createDocument("Meet.vts", ""); //最好紧接着就初始化xml文档
			xmlExport.createDocument(remark, ""); // 最好紧接着就初始化xml文档
			TextTag texttag = new TextTag();
			String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
					+ StrTool.getDay() + "日";

			texttag.add("BarCode1", mLOPRTManagerSchema.getCode());
			texttag
					.add(
							"BarCodeParam1",
							"BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");
			texttag.add("BarCode2", mLOPRTManagerSchema.getPrtSeq());
			texttag
					.add(
							"BarCodeParam1",
							"BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");

			texttag.add("AppntName", mLCContSchema.getAppntName());
			texttag.add("LCCont.InsuredName", mLCContSchema.getInsuredName());
			texttag.add("AgentName", mLAAgentSchema.getName());
			texttag.add("BranchGroupName", tLABranchGroupDB.getName());
			texttag.add("Department", getUpComName(mLAAgentSchema
					.getBranchCode()));
			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}
			xmlExport.addListTable(tListTable, MeetTitle); // 保存问题信息
			// xmlExport.outputDocumentToFile("D:/lis/ui/f1print/NCLtemplate/",
			// "testHZ"); //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlExport);

		} else {
			buildError("MeetF1BL", "已经打印面见通知书！");

		}
	}

	// //////////////////////////为了实现多个VTS合并分别生成带有数据的VTS-1：FinanceQuetion.vts/////
	private void getPrintData3() throws Exception {
		mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		LCContDB tLCContDB = new LCContDB();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);

		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}
		// 需要判断是否已经打印？！
		if (tLOPRTManagerDB.getStateFlag().equals("0")) {
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema(); // get all
																// message！

			// 打印时传入的是主险投保单的投保单号
			tLCContDB.setContNo(mLOPRTManagerSchema.getOtherNo());
			logger.debug("test by yaory:"
					+ mLOPRTManagerSchema.getOtherNo());
			if (!tLCContDB.getInfo()) {
				mErrors.copyAllErrors(tLCContDB.mErrors);
				throw new Exception("在获取保单信息时出错！");
			}
			mLCContSchema = tLCContDB.getSchema();

			// 查询打印队列的信息
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
			mOldPrtSeq = mLOPRTManagerSchema.getOldPrtSeq();

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("getprintData", "无效的打印状态");
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("getprintData", "该打印请求不是在请求状态");
			}
			if (!mLOPRTManagerSchema.getCode().equals("")
					&& !mLOPRTManagerSchema.getCode().equals(null)
					&& mLOPRTManagerSchema.getCode().equals("G04")) {

			} else {
				// 投保人地址和邮编
				LCAppntDB tLCAppntDB = new LCAppntDB();
				tLCAppntDB.setContNo(mLCContSchema.getContNo());
				if (tLCAppntDB.getInfo() == false) {
					mErrors.copyAllErrors(tLCAppntDB.mErrors);

					throw new Exception("在取得打印队列中数据时发生错误");
				}
			}
			mAgentCode = mLCContSchema.getAgentCode();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLAAgentDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLABranchGroupDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}

			// 2-1 查询体检子表
			String[] MeetTitle = new String[2];
			MeetTitle[0] = "ID";
			MeetTitle[1] = "ITEM";
			ListTable tListTable = new ListTable();
			String str[] = null;
			tListTable.setName("WJ"); // 对应模版体检部分的行对象名

			RnewRReportPrtSet tRnewRReportPrtSet = new RnewRReportPrtSet();
			RnewRReportPrtDB tRnewRReportPrtDB = new RnewRReportPrtDB();
			/**
			 * @todo 如果OldPrtSeq有值则用OldPrtSeq查询否则用prtseq查询
			 */
			if (mOldPrtSeq != null && !mOldPrtSeq.equals("")) {
				tRnewRReportPrtDB.setPrtSeq(mOldPrtSeq);
			} else {
				tRnewRReportPrtDB.setPrtSeq(mPrtSeq);
			}
			tRnewRReportPrtSet = tRnewRReportPrtDB.query();

			for (int i = 1; i <= tRnewRReportPrtSet.size(); i++) {

				str = new String[2];
				str[0] = (new Integer(i)).toString(); // 序号
				str[1] = getAskName(tRnewRReportPrtSet.get(i).getAskCode()); // 序号对应的内容
				tListTable.add(str);
			}

			String remark = "";

			remark = "FinanceQuetion.vts";

			// ////////////////////end write by yaory-2007-7-4-16:39////
			XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例

			// xmlExport.createDocument("Meet.vts", ""); //最好紧接着就初始化xml文档
			xmlExport.createDocument(remark, ""); // 最好紧接着就初始化xml文档
			TextTag texttag = new TextTag();
			String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
					+ StrTool.getDay() + "日";

			texttag.add("BarCode1", mLOPRTManagerSchema.getCode());
			texttag
					.add(
							"BarCodeParam1",
							"BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");
			texttag.add("BarCode2", mLOPRTManagerSchema.getPrtSeq());
			texttag
					.add(
							"BarCodeParam1",
							"BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");

			texttag.add("AppntName", mLCContSchema.getAppntName());
			texttag.add("LCCont.InsuredName", mLCContSchema.getInsuredName());
			texttag.add("AgentName", mLAAgentSchema.getAgentCode());
			texttag.add("ContNo", mLCContSchema.getContNo());

			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}
			xmlExport.addListTable(tListTable, MeetTitle); // 保存问题信息
			// xmlExport.outputDocumentToFile("D:/lis/ui/f1print/NCLtemplate/",
			// "testHZ"); //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlExport);

		} else {
			buildError("MeetF1BL", "已经打印面见通知书！");

		}

	}

	private void getPrintData() throws Exception {
		mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		LCContDB tLCContDB = new LCContDB();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);

		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}
		// 需要判断是否已经打印？！
		if (tLOPRTManagerDB.getStateFlag().equals("0")) {
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema(); // get all
																// message！

			// 打印时传入的是主险投保单的投保单号
			tLCContDB.setContNo(mLOPRTManagerSchema.getOtherNo());
			logger.debug("test by yaory:"
					+ mLOPRTManagerSchema.getOtherNo());
			if (!tLCContDB.getInfo()) {
				mErrors.copyAllErrors(tLCContDB.mErrors);
				throw new Exception("在获取保单信息时出错！");
			}
			mLCContSchema = tLCContDB.getSchema();

			// 查询打印队列的信息
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();

			mOldPrtSeq = mLOPRTManagerSchema.getOldPrtSeq();

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("getprintData", "无效的打印状态");
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("getprintData", "该打印请求不是在请求状态");
			}
			if (!mLOPRTManagerSchema.getCode().equals("")
					&& !mLOPRTManagerSchema.getCode().equals(null)
					&& mLOPRTManagerSchema.getCode().equals("G04")) {

			} else {
				// 投保人地址和邮编
				LCAppntDB tLCAppntDB = new LCAppntDB();
				tLCAppntDB.setContNo(mLCContSchema.getContNo());
				if (tLCAppntDB.getInfo() == false) {
					mErrors.copyAllErrors(tLCAppntDB.mErrors);

					throw new Exception("在取得打印队列中数据时发生错误");
				}
			}
			mAgentCode = mLCContSchema.getAgentCode();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLAAgentDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLABranchGroupDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}

			// 2-1 查询体检子表
			String[] MeetTitle = new String[2];
			MeetTitle[0] = "ID";
			MeetTitle[1] = "ITEM";
			ListTable tListTable = new ListTable();
			String str[] = null;
			tListTable.setName("WJ"); // 对应模版体检部分的行对象名

			RnewRReportPrtSet tRnewRReportPrtSet = new RnewRReportPrtSet();
			RnewRReportPrtDB tRnewRReportPrtDB = new RnewRReportPrtDB();
			/**
			 * @todo 如果OldPrtSeq有值则用OldPrtSeq查询否则用prtseq查询
			 */
			if (mOldPrtSeq != null && !mOldPrtSeq.equals("")) {
				tRnewRReportPrtDB.setPrtSeq(mOldPrtSeq);
			} else {
				tRnewRReportPrtDB.setPrtSeq(mPrtSeq);
			}
			tRnewRReportPrtSet = tRnewRReportPrtDB.query();

			for (int i = 1; i <= tRnewRReportPrtSet.size(); i++) {

				str = new String[2];
				str[0] = (new Integer(i)).toString(); // 序号
				str[1] = getAskName(tRnewRReportPrtSet.get(i).getAskCode()); // 序号对应的内容
				tListTable.add(str);
			}
			// /////////////////////进行合并 write by yaory -2007-7-4-16:39////
			// first query how many questionaires
			ExeSQL exeSql = new ExeSQL();
			SSRS testSSRS = new SSRS();
			// testSSRS = exeSql.execSQL("select sysvarvalue From ldsysvar where
			// sysvar='DataPrintCombine'");
			// String TemplatePath=testSSRS.GetText(1,1);
			String TemplatePath = tWebPath;
			logger.debug("路径-模板===" + TemplatePath);
			if (mOldPrtSeq != null && !mOldPrtSeq.equals("")) {
				SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
				sqlbv3.sql("select askcode from RnewRReportPrt where contno='"
						+ "?mOldPrtSeq?" + "'");
				sqlbv3.put("mOldPrtSeq", mOldPrtSeq);
				testSSRS = exeSql.execSQL(sqlbv3);
			} else {
				SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
				sqlbv4.sql("select askcode from RnewRReportPrt where contno='"
						+ "?mPrtSeq?" + "'");
				sqlbv4.put("mPrtSeq", mPrtSeq);
				testSSRS = exeSql.execSQL(sqlbv4);
			}
			String remark = "";
			if (testSSRS.MaxRow == 0) {
				remark = "HbWjTzs.vts";
			} else if (testSSRS.MaxRow == 1) {
				VtsFileCombine vtsfilecombine = new VtsFileCombine();
				if (testSSRS.GetText(1, 1).equals("87")) {
					BookModelImpl tb1 = new BookModelImpl();
					tb1.read(TemplatePath + "HbWjTzs.vts", new ReadParams());
					BookModelImpl tb2 = new BookModelImpl();
					tb2.read(TemplatePath + "scdcb.vts", new ReadParams());

					BookModelImpl tb = vtsfilecombine.templateCombine(tb1, tb2);
					vtsfilecombine.write(tb, TemplatePath + "new.vts");
					remark = "new.vts";
				} else {
					BookModelImpl tb1 = new BookModelImpl();
					tb1.read(TemplatePath + "HbWjTzs.vts", new ReadParams());
					BookModelImpl tb2 = new BookModelImpl();
					tb2.read(TemplatePath + "FinanceQuetion.vts",
							new ReadParams());

					BookModelImpl tb = vtsfilecombine.templateCombine(tb1, tb2);
					vtsfilecombine.write(tb, TemplatePath + "new.vts");
					remark = "new.vts";
				}

			} else if (testSSRS.MaxRow == 2) {
				VtsFileCombine vtsfilecombine = new VtsFileCombine();
				BookModelImpl tb1 = new BookModelImpl();
				tb1.read(TemplatePath + "scdcb.vts", new ReadParams());
				BookModelImpl tb2 = new BookModelImpl();
				tb2.read(TemplatePath + "FinanceQuetion.vts", new ReadParams());

				BookModelImpl tb = vtsfilecombine.templateCombine(tb1, tb2);
				vtsfilecombine.write(tb, TemplatePath + "new.vts");

				tb1.read(TemplatePath + "HbWjTzs.vts", new ReadParams());
				tb2.read(TemplatePath + "new.vts", new ReadParams());

				tb = vtsfilecombine.templateCombine(tb1, tb2);
				vtsfilecombine.write(tb, TemplatePath + "new.vts");
				remark = "new.vts";

			}

			// ////////////////////end write by yaory-2007-7-4-16:39////
			XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例

			// xmlExport.createDocument("Meet.vts", ""); //最好紧接着就初始化xml文档
			xmlExport.createDocument(remark, ""); // 最好紧接着就初始化xml文档
			TextTag texttag = new TextTag();
			String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
					+ StrTool.getDay() + "日";

			if (mLCContSchema.getSaleChnl().equals("3")) {
				LAComSchema mLAComSchema = new LAComSchema();
				LAComDB mLAComDB = new LAComDB();
				mLAComDB.setAgentCom(mLCContSchema.getAgentCom());
				if (!mLAComDB.getInfo()) {
					mErrors.copyAllErrors(mLAComDB.mErrors);
					buildError("outputXML", "在取得LACom的数据时发生错误");
					return;
				}
				mLAComSchema = mLAComDB.getSchema(); // 保存银行代码信息

				texttag.add("LCCont.BankCode", mLAComSchema.getName()); // 代理机构
				texttag.add("LCCont.AgentType", tLABranchGroupDB.getName()); // 业务分布及业务组.
			}
			/**
			 * ==========================================================================
			 * 修改人 ： 万泽辉 修改时间： 2005/12/07 ManageComName: 取6位编码的中支机构
			 * LABranchGroup.Name：取8位编码的营业销售部 SumPrem：取标准的形式：0.00
			 * ==========================================================================
			 */
			// 改为标准的数据形式:0.00
			mSumPrem = mDecimalFormat.format(mLCContSchema.getPrem());

			texttag.add("BarCode1", mLOPRTManagerSchema.getCode());
			texttag
					.add(
							"BarCodeParam1",
							"BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");
			texttag.add("BarCode2", mLOPRTManagerSchema.getPrtSeq());
			texttag
					.add(
							"BarCodeParam1",
							"BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");

			texttag.add("LCCont.ContNo", mLCContSchema.getContNo());
			texttag.add("LCCont.AppntName", mLCContSchema.getAppntName());
			texttag.add("LCCont.InsuredName", mLCContSchema.getInsuredName());
			texttag.add("LABranchGroup.Name", tLABranchGroupDB.getName());
			texttag.add("LAAgent.Name", mLAAgentSchema.getName());
			texttag.add("LCCont.AgentCode", mLAAgentSchema.getAgentCode());
			texttag.add("LCCont.UWOperator", mLCContSchema.getUWOperator());
			texttag.add("LOPRTManager.Remark", mLOPRTManagerSchema.getRemark());
			texttag.add("SumPrem", mSumPrem);
			texttag.add("SysDate", SysDate);

			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}
			xmlExport.addListTable(tListTable, MeetTitle); // 保存问题信息
			// xmlExport.outputDocumentToFile("D:/lis/ui/f1print/NCLtemplate/",
			// "testHZ"); //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlExport);

		} else {
			buildError("RnewMeetF1BL", "已经打印面见通知书！");

		}

	}

	// //////////////////////////为了实现多个VTS合并分别生成带有数据的VTS-1：DiseaseQuestionDX.vts/////
	private void getPrintDataa() throws Exception {
		mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		LCContDB tLCContDB = new LCContDB();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);

		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}
		// 需要判断是否已经打印？！
		if (tLOPRTManagerDB.getStateFlag().equals("0")) {
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema(); // get all
																// message！

			// 打印时传入的是主险投保单的投保单号
			tLCContDB.setContNo(mLOPRTManagerSchema.getOtherNo());
			logger.debug("test by yaory:"
					+ mLOPRTManagerSchema.getOtherNo());
			if (!tLCContDB.getInfo()) {
				mErrors.copyAllErrors(tLCContDB.mErrors);
				throw new Exception("在获取保单信息时出错！");
			}
			mLCContSchema = tLCContDB.getSchema();

			// 查询打印队列的信息
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
			mOldPrtSeq = mLOPRTManagerSchema.getOldPrtSeq();

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("getprintData", "无效的打印状态");
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("getprintData", "该打印请求不是在请求状态");
			}
			if (!mLOPRTManagerSchema.getCode().equals("")
					&& !mLOPRTManagerSchema.getCode().equals(null)
					&& mLOPRTManagerSchema.getCode().equals("G04")) {

			} else {
				// 投保人地址和邮编
				LCAppntDB tLCAppntDB = new LCAppntDB();
				tLCAppntDB.setContNo(mLCContSchema.getContNo());
				if (tLCAppntDB.getInfo() == false) {
					mErrors.copyAllErrors(tLCAppntDB.mErrors);

					throw new Exception("在取得打印队列中数据时发生错误");
				}
			}
			mAgentCode = mLCContSchema.getAgentCode();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLAAgentDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLABranchGroupDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			String remark = "";
			remark = "DiseaseQuestionDX.vts";
			logger.debug("测试");

			// ////////////////////end write by yaory-2007-7-4-16:39////
			XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例

			// xmlExport.createDocument("Meet.vts", ""); //最好紧接着就初始化xml文档
			xmlExport.createDocument(remark, ""); // 最好紧接着就初始化xml文档
			TextTag texttag = new TextTag();
			String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
					+ StrTool.getDay() + "日";

			texttag.add("LCCont.ProposalContNo", mLCContSchema
					.getProposalContNo());
			texttag.add("LCCont.AppntName", mLCContSchema.getAppntName());
			texttag.add("LCCont.InsuredName", mLCContSchema.getInsuredName());
			texttag.add("LAAgent.AgentCode", mLAAgentSchema.getAgentCode());
			texttag.add("LAAgent.Name", mLAAgentSchema.getName());
			texttag.add("LABranchGroup.Name", tLABranchGroupDB.getName());
			texttag.add("Department", getUpComName(mLAAgentSchema
					.getBranchCode()));
			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}

			// xmlExport.outputDocumentToFile("D:/lis/ui/f1print/NCLtemplate/",
			// "testHZ"); //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlExport);

		} else {
			buildError("MeetF1BL", "已经打印面见通知书！");

		}
	}

	private void getPrintDatab() throws Exception {
		mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		LCContDB tLCContDB = new LCContDB();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);

		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}
		// 需要判断是否已经打印？！
		if (tLOPRTManagerDB.getStateFlag().equals("0")) {
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema(); // get all
																// message！

			// 打印时传入的是主险投保单的投保单号
			tLCContDB.setContNo(mLOPRTManagerSchema.getOtherNo());
			logger.debug("test by yaory:"
					+ mLOPRTManagerSchema.getOtherNo());
			if (!tLCContDB.getInfo()) {
				mErrors.copyAllErrors(tLCContDB.mErrors);
				throw new Exception("在获取保单信息时出错！");
			}
			mLCContSchema = tLCContDB.getSchema();

			// 查询打印队列的信息
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
			mOldPrtSeq = mLOPRTManagerSchema.getOldPrtSeq();

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("getprintData", "无效的打印状态");
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("getprintData", "该打印请求不是在请求状态");
			}
			if (!mLOPRTManagerSchema.getCode().equals("")
					&& !mLOPRTManagerSchema.getCode().equals(null)
					&& mLOPRTManagerSchema.getCode().equals("G04")) {

			} else {
				// 投保人地址和邮编
				LCAppntDB tLCAppntDB = new LCAppntDB();
				tLCAppntDB.setContNo(mLCContSchema.getContNo());
				if (tLCAppntDB.getInfo() == false) {
					mErrors.copyAllErrors(tLCAppntDB.mErrors);

					throw new Exception("在取得打印队列中数据时发生错误");
				}
			}
			mAgentCode = mLCContSchema.getAgentCode();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLAAgentDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLABranchGroupDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			String remark = "";
			remark = "DiseaseQuestionXC.vts";
			logger.debug("测试");

			// ////////////////////end write by yaory-2007-7-4-16:39////
			XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例

			// xmlExport.createDocument("Meet.vts", ""); //最好紧接着就初始化xml文档
			xmlExport.createDocument(remark, ""); // 最好紧接着就初始化xml文档
			TextTag texttag = new TextTag();
			String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
					+ StrTool.getDay() + "日";

			texttag.add("LCCont.ProposalContNo", mLCContSchema
					.getProposalContNo());
			texttag.add("LCCont.AppntName", mLCContSchema.getAppntName());
			texttag.add("LCCont.InsuredName", mLCContSchema.getInsuredName());
			texttag.add("LAAgent.AgentCode", mLAAgentSchema.getAgentCode());
			texttag.add("LAAgent.Name", mLAAgentSchema.getName());
			texttag.add("LABranchGroup.Name", tLABranchGroupDB.getName());
			texttag.add("Department", getUpComName(mLAAgentSchema
					.getBranchCode()));
			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}

			// xmlExport.outputDocumentToFile("D:/lis/ui/f1print/NCLtemplate/",
			// "testHZ"); //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlExport);

		} else {
			buildError("MeetF1BL", "已经打印面见通知书！");

		}
	}

	private void getPrintDatac() throws Exception {
		mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		LCContDB tLCContDB = new LCContDB();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);

		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}
		// 需要判断是否已经打印？！
		if (tLOPRTManagerDB.getStateFlag().equals("0")) {
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema(); // get all
																// message！

			// 打印时传入的是主险投保单的投保单号
			tLCContDB.setContNo(mLOPRTManagerSchema.getOtherNo());
			logger.debug("test by yaory:"
					+ mLOPRTManagerSchema.getOtherNo());
			if (!tLCContDB.getInfo()) {
				mErrors.copyAllErrors(tLCContDB.mErrors);
				throw new Exception("在获取保单信息时出错！");
			}
			mLCContSchema = tLCContDB.getSchema();

			// 查询打印队列的信息
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
			mOldPrtSeq = mLOPRTManagerSchema.getOldPrtSeq();

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("getprintData", "无效的打印状态");
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("getprintData", "该打印请求不是在请求状态");
			}
			if (!mLOPRTManagerSchema.getCode().equals("")
					&& !mLOPRTManagerSchema.getCode().equals(null)
					&& mLOPRTManagerSchema.getCode().equals("G04")) {
			} else {
				// 投保人地址和邮编
				LCAppntDB tLCAppntDB = new LCAppntDB();
				tLCAppntDB.setContNo(mLCContSchema.getContNo());
				if (tLCAppntDB.getInfo() == false) {
					mErrors.copyAllErrors(tLCAppntDB.mErrors);

					throw new Exception("在取得打印队列中数据时发生错误");
				}
			}
			mAgentCode = mLCContSchema.getAgentCode();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLAAgentDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLABranchGroupDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			String remark = "";
			remark = "DiseaseQuestionTNB.vts";
			logger.debug("测试");

			// ////////////////////end write by yaory-2007-7-4-16:39////
			XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例

			// xmlExport.createDocument("Meet.vts", ""); //最好紧接着就初始化xml文档
			xmlExport.createDocument(remark, ""); // 最好紧接着就初始化xml文档
			TextTag texttag = new TextTag();
			String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
					+ StrTool.getDay() + "日";

			texttag.add("LCCont.ProposalContNo", mLCContSchema
					.getProposalContNo());
			texttag.add("LCCont.AppntName", mLCContSchema.getAppntName());
			texttag.add("LCCont.InsuredName", mLCContSchema.getInsuredName());
			texttag.add("LAAgent.AgentCode", mLAAgentSchema.getAgentCode());
			texttag.add("LAAgent.Name", mLAAgentSchema.getName());
			texttag.add("LABranchGroup.Name", tLABranchGroupDB.getName());
			texttag.add("Department", getUpComName(mLAAgentSchema
					.getBranchCode()));
			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}

			// xmlExport.outputDocumentToFile("D:/lis/ui/f1print/NCLtemplate/",
			// "testHZ"); //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlExport);

		} else {
			buildError("MeetF1BL", "已经打印面见通知书！");

		}
	}

	private void getPrintDatad() throws Exception {
		mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		LCContDB tLCContDB = new LCContDB();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);

		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}
		// 需要判断是否已经打印？！
		if (tLOPRTManagerDB.getStateFlag().equals("0")) {
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema(); // get all
																// message！

			// 打印时传入的是主险投保单的投保单号
			tLCContDB.setContNo(mLOPRTManagerSchema.getOtherNo());
			logger.debug("test by yaory:"
					+ mLOPRTManagerSchema.getOtherNo());
			if (!tLCContDB.getInfo()) {
				mErrors.copyAllErrors(tLCContDB.mErrors);
				throw new Exception("在获取保单信息时出错！");
			}
			mLCContSchema = tLCContDB.getSchema();

			// 查询打印队列的信息
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
			mOldPrtSeq = mLOPRTManagerSchema.getOldPrtSeq();

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("getprintData", "无效的打印状态");
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("getprintData", "该打印请求不是在请求状态");
			}
			if (!mLOPRTManagerSchema.getCode().equals("")
					&& !mLOPRTManagerSchema.getCode().equals(null)
					&& mLOPRTManagerSchema.getCode().equals("G04")) {
			} else {
				// 投保人地址和邮编
				LCAppntDB tLCAppntDB = new LCAppntDB();
				tLCAppntDB.setContNo(mLCContSchema.getContNo());
				if (tLCAppntDB.getInfo() == false) {
					mErrors.copyAllErrors(tLCAppntDB.mErrors);

					throw new Exception("在取得打印队列中数据时发生错误");
				}
			}
			mAgentCode = mLCContSchema.getAgentCode();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLAAgentDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLABranchGroupDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			String remark = "";
			remark = "DiseaseQuestionJZX.vts";
			logger.debug("测试");

			// ////////////////////end write by yaory-2007-7-4-16:39////
			XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例

			// xmlExport.createDocument("Meet.vts", ""); //最好紧接着就初始化xml文档
			xmlExport.createDocument(remark, ""); // 最好紧接着就初始化xml文档
			TextTag texttag = new TextTag();
			String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
					+ StrTool.getDay() + "日";

			texttag.add("LCCont.ProposalContNo", mLCContSchema
					.getProposalContNo());
			texttag.add("LCCont.AppntName", mLCContSchema.getAppntName());
			texttag.add("LCCont.InsuredName", mLCContSchema.getInsuredName());
			texttag.add("LAAgent.AgentCode", mLAAgentSchema.getAgentCode());
			texttag.add("LAAgent.Name", mLAAgentSchema.getName());
			texttag.add("LABranchGroup.Name", tLABranchGroupDB.getName());
			texttag.add("Department", getUpComName(mLAAgentSchema
					.getBranchCode()));
			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}

			// xmlExport.outputDocumentToFile("D:/lis/ui/f1print/NCLtemplate/",
			// "testHZ"); //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlExport);

		} else {
			buildError("MeetF1BL", "已经打印面见通知书！");

		}
	}

	private void getPrintDatae() throws Exception {
		mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		LCContDB tLCContDB = new LCContDB();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);

		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}
		// 需要判断是否已经打印？！
		if (tLOPRTManagerDB.getStateFlag().equals("0")) {
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema(); // get all
																// message！

			// 打印时传入的是主险投保单的投保单号
			tLCContDB.setContNo(mLOPRTManagerSchema.getOtherNo());
			logger.debug("test by yaory:"
					+ mLOPRTManagerSchema.getOtherNo());
			if (!tLCContDB.getInfo()) {
				mErrors.copyAllErrors(tLCContDB.mErrors);
				throw new Exception("在获取保单信息时出错！");
			}
			mLCContSchema = tLCContDB.getSchema();

			// 查询打印队列的信息
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
			mOldPrtSeq = mLOPRTManagerSchema.getOldPrtSeq();

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("getprintData", "无效的打印状态");
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("getprintData", "该打印请求不是在请求状态");
			}

			if (!mLOPRTManagerSchema.getCode().equals("")
					&& !mLOPRTManagerSchema.getCode().equals(null)
					&& mLOPRTManagerSchema.getCode().equals("G04")) {
			} else {
				// 投保人地址和邮编
				LCAppntDB tLCAppntDB = new LCAppntDB();
				tLCAppntDB.setContNo(mLCContSchema.getContNo());
				if (tLCAppntDB.getInfo() == false) {
					mErrors.copyAllErrors(tLCAppntDB.mErrors);

					throw new Exception("在取得打印队列中数据时发生错误");
				}
			}
			mAgentCode = mLCContSchema.getAgentCode();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLAAgentDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLABranchGroupDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			String remark = "";
			remark = "DiseaseQuestionXT.vts";
			logger.debug("测试");

			// ////////////////////end write by yaory-2007-7-4-16:39////
			XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例

			// xmlExport.createDocument("Meet.vts", ""); //最好紧接着就初始化xml文档
			xmlExport.createDocument(remark, ""); // 最好紧接着就初始化xml文档
			TextTag texttag = new TextTag();
			String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
					+ StrTool.getDay() + "日";

			texttag.add("LCCont.ProposalContNo", mLCContSchema
					.getProposalContNo());
			texttag.add("LCCont.AppntName", mLCContSchema.getAppntName());
			texttag.add("LCCont.InsuredName", mLCContSchema.getInsuredName());
			texttag.add("LAAgent.AgentCode", mLAAgentSchema.getAgentCode());
			texttag.add("LAAgent.Name", mLAAgentSchema.getName());
			texttag.add("LABranchGroup.Name", tLABranchGroupDB.getName());
			texttag.add("Department", getUpComName(mLAAgentSchema
					.getBranchCode()));
			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}

			// xmlExport.outputDocumentToFile("D:/lis/ui/f1print/NCLtemplate/",
			// "testHZ"); //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlExport);

		} else {
			buildError("MeetF1BL", "已经打印面见通知书！");

		}
	}

	private void getPrintDataf() throws Exception {
		mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		LCContDB tLCContDB = new LCContDB();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);

		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}
		// 需要判断是否已经打印？！
		if (tLOPRTManagerDB.getStateFlag().equals("0")) {
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema(); // get all
																// message！

			// 打印时传入的是主险投保单的投保单号
			tLCContDB.setContNo(mLOPRTManagerSchema.getOtherNo());
			logger.debug("test by yaory:"
					+ mLOPRTManagerSchema.getOtherNo());
			if (!tLCContDB.getInfo()) {
				mErrors.copyAllErrors(tLCContDB.mErrors);
				throw new Exception("在获取保单信息时出错！");
			}
			mLCContSchema = tLCContDB.getSchema();

			// 查询打印队列的信息
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
			mOldPrtSeq = mLOPRTManagerSchema.getOldPrtSeq();

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("getprintData", "无效的打印状态");
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("getprintData", "该打印请求不是在请求状态");
			}
			if (!mLOPRTManagerSchema.getCode().equals("")
					&& !mLOPRTManagerSchema.getCode().equals(null)
					&& mLOPRTManagerSchema.getCode().equals("G04")) {
			} else {
				// 投保人地址和邮编
				LCAppntDB tLCAppntDB = new LCAppntDB();
				tLCAppntDB.setContNo(mLCContSchema.getContNo());
				if (tLCAppntDB.getInfo() == false) {
					mErrors.copyAllErrors(tLCAppntDB.mErrors);

					throw new Exception("在取得打印队列中数据时发生错误");
				}
			}
			mAgentCode = mLCContSchema.getAgentCode();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLAAgentDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLABranchGroupDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			String remark = "";
			remark = "DiseaseQuestionTTTY.vts";
			logger.debug("测试");

			// ////////////////////end write by yaory-2007-7-4-16:39////
			XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例

			// xmlExport.createDocument("Meet.vts", ""); //最好紧接着就初始化xml文档
			xmlExport.createDocument(remark, ""); // 最好紧接着就初始化xml文档
			TextTag texttag = new TextTag();
			String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
					+ StrTool.getDay() + "日";

			texttag.add("LCCont.ProposalContNo", mLCContSchema
					.getProposalContNo());
			texttag.add("LCCont.AppntName", mLCContSchema.getAppntName());
			texttag.add("LCCont.InsuredName", mLCContSchema.getInsuredName());
			texttag.add("LAAgent.AgentCode", mLAAgentSchema.getAgentCode());
			texttag.add("LAAgent.Name", mLAAgentSchema.getName());
			texttag.add("LABranchGroup.Name", tLABranchGroupDB.getName());
			texttag.add("Department", getUpComName(mLAAgentSchema
					.getBranchCode()));
			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}

			// xmlExport.outputDocumentToFile("D:/lis/ui/f1print/NCLtemplate/",
			// "testHZ"); //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlExport);

		} else {
			buildError("MeetF1BL", "已经打印面见通知书！");

		}
	}

	private void getPrintDatag() throws Exception {
		mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		LCContDB tLCContDB = new LCContDB();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);

		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}
		// 需要判断是否已经打印？！
		if (tLOPRTManagerDB.getStateFlag().equals("0")) {
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema(); // get all
																// message！

			// 打印时传入的是主险投保单的投保单号
			tLCContDB.setContNo(mLOPRTManagerSchema.getOtherNo());
			logger.debug("test by yaory:"
					+ mLOPRTManagerSchema.getOtherNo());
			if (!tLCContDB.getInfo()) {
				mErrors.copyAllErrors(tLCContDB.mErrors);
				throw new Exception("在获取保单信息时出错！");
			}
			mLCContSchema = tLCContDB.getSchema();

			// 查询打印队列的信息
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
			mOldPrtSeq = mLOPRTManagerSchema.getOldPrtSeq();

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("getprintData", "无效的打印状态");
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("getprintData", "该打印请求不是在请求状态");
			}
			if (!mLOPRTManagerSchema.getCode().equals("")
					&& !mLOPRTManagerSchema.getCode().equals(null)
					&& mLOPRTManagerSchema.getCode().equals("G04")) {
			} else {
				// 投保人地址和邮编
				LCAppntDB tLCAppntDB = new LCAppntDB();
				tLCAppntDB.setContNo(mLCContSchema.getContNo());
				if (tLCAppntDB.getInfo() == false) {
					mErrors.copyAllErrors(tLCAppntDB.mErrors);

					throw new Exception("在取得打印队列中数据时发生错误");
				}
			}
			mAgentCode = mLCContSchema.getAgentCode();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLAAgentDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLABranchGroupDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			String remark = "";
			remark = "DiseaseQuestionNXJKBCWJ.vts";
			logger.debug("测试");

			// ////////////////////end write by yaory-2007-7-4-16:39////
			XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例

			// xmlExport.createDocument("Meet.vts", ""); //最好紧接着就初始化xml文档
			xmlExport.createDocument(remark, ""); // 最好紧接着就初始化xml文档
			TextTag texttag = new TextTag();
			String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
					+ StrTool.getDay() + "日";

			texttag.add("LCCont.ProposalContNo", mLCContSchema
					.getProposalContNo());
			texttag.add("LCCont.AppntName", mLCContSchema.getAppntName());
			texttag.add("LCCont.InsuredName", mLCContSchema.getInsuredName());
			texttag.add("LAAgent.AgentCode", mLAAgentSchema.getAgentCode());
			texttag.add("LAAgent.Name", mLAAgentSchema.getName());
			texttag.add("LABranchGroup.Name", tLABranchGroupDB.getName());
			texttag.add("Department", getUpComName(mLAAgentSchema
					.getBranchCode()));
			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}

			// xmlExport.outputDocumentToFile("D:/lis/ui/f1print/NCLtemplate/",
			// "testHZ"); //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlExport);

		} else {
			buildError("MeetF1BL", "已经打印面见通知书！");

		}
	}

	private void getPrintDatah() throws Exception {
		mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		LCContDB tLCContDB = new LCContDB();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);

		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}
		// 需要判断是否已经打印？！
		if (tLOPRTManagerDB.getStateFlag().equals("0")) {
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema(); // get all
																// message！

			// 打印时传入的是主险投保单的投保单号
			tLCContDB.setContNo(mLOPRTManagerSchema.getOtherNo());
			logger.debug("test by yaory:"
					+ mLOPRTManagerSchema.getOtherNo());
			if (!tLCContDB.getInfo()) {
				mErrors.copyAllErrors(tLCContDB.mErrors);
				throw new Exception("在获取保单信息时出错！");
			}
			mLCContSchema = tLCContDB.getSchema();

			// 查询打印队列的信息
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
			mOldPrtSeq = mLOPRTManagerSchema.getOldPrtSeq();

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("getprintData", "无效的打印状态");
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("getprintData", "该打印请求不是在请求状态");
			}
			if (!mLOPRTManagerSchema.getCode().equals("")
					&& !mLOPRTManagerSchema.getCode().equals(null)
					&& mLOPRTManagerSchema.getCode().equals("G04")) {
			} else {
				// 投保人地址和邮编
				LCAppntDB tLCAppntDB = new LCAppntDB();
				tLCAppntDB.setContNo(mLCContSchema.getContNo());
				if (tLCAppntDB.getInfo() == false) {
					mErrors.copyAllErrors(tLCAppntDB.mErrors);

					throw new Exception("在取得打印队列中数据时发生错误");
				}
			}
			mAgentCode = mLCContSchema.getAgentCode();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLAAgentDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLABranchGroupDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			String remark = "";
			remark = "DiseaseQuestionGY.vts";
			logger.debug("测试");

			// ////////////////////end write by yaory-2007-7-4-16:39////
			XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例

			// xmlExport.createDocument("Meet.vts", ""); //最好紧接着就初始化xml文档
			xmlExport.createDocument(remark, ""); // 最好紧接着就初始化xml文档
			TextTag texttag = new TextTag();
			String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
					+ StrTool.getDay() + "日";

			texttag.add("LCCont.ProposalContNo", mLCContSchema
					.getProposalContNo());
			texttag.add("LCCont.AppntName", mLCContSchema.getAppntName());
			texttag.add("LCCont.InsuredName", mLCContSchema.getInsuredName());
			texttag.add("LAAgent.AgentCode", mLAAgentSchema.getAgentCode());
			texttag.add("LAAgent.Name", mLAAgentSchema.getName());
			texttag.add("LABranchGroup.Name", tLABranchGroupDB.getName());
			texttag.add("Department", getUpComName(mLAAgentSchema
					.getBranchCode()));
			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}

			// xmlExport.outputDocumentToFile("D:/lis/ui/f1print/NCLtemplate/",
			// "testHZ"); //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlExport);

		} else {
			buildError("MeetF1BL", "已经打印面见通知书！");

		}
	}

	private void getPrintDatai() throws Exception {
		mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		LCContDB tLCContDB = new LCContDB();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);

		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}
		// 需要判断是否已经打印？！
		if (tLOPRTManagerDB.getStateFlag().equals("0")) {
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema(); // get all
																// message！

			// 打印时传入的是主险投保单的投保单号
			tLCContDB.setContNo(mLOPRTManagerSchema.getOtherNo());
			logger.debug("test by yaory:"
					+ mLOPRTManagerSchema.getOtherNo());
			if (!tLCContDB.getInfo()) {
				mErrors.copyAllErrors(tLCContDB.mErrors);
				throw new Exception("在获取保单信息时出错！");
			}
			mLCContSchema = tLCContDB.getSchema();

			// 查询打印队列的信息
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
			mOldPrtSeq = mLOPRTManagerSchema.getOldPrtSeq();

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("getprintData", "无效的打印状态");
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("getprintData", "该打印请求不是在请求状态");
			}
			if (!mLOPRTManagerSchema.getCode().equals("")
					&& !mLOPRTManagerSchema.getCode().equals(null)
					&& mLOPRTManagerSchema.getCode().equals("G04")) {
			} else {
				// 投保人地址和邮编
				LCAppntDB tLCAppntDB = new LCAppntDB();
				tLCAppntDB.setContNo(mLCContSchema.getContNo());
				if (tLCAppntDB.getInfo() == false) {
					mErrors.copyAllErrors(tLCAppntDB.mErrors);

					throw new Exception("在取得打印队列中数据时发生错误");
				}
			}
			mAgentCode = mLCContSchema.getAgentCode();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLAAgentDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLABranchGroupDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			String remark = "";
			remark = "DiseaseQuestionHXD.vts";
			logger.debug("测试");

			// ////////////////////end write by yaory-2007-7-4-16:39////
			XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例

			// xmlExport.createDocument("Meet.vts", ""); //最好紧接着就初始化xml文档
			xmlExport.createDocument(remark, ""); // 最好紧接着就初始化xml文档
			TextTag texttag = new TextTag();
			String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
					+ StrTool.getDay() + "日";

			texttag.add("LCCont.ProposalContNo", mLCContSchema
					.getProposalContNo());
			texttag.add("LCCont.AppntName", mLCContSchema.getAppntName());
			texttag.add("LCCont.InsuredName", mLCContSchema.getInsuredName());
			texttag.add("LAAgent.AgentCode", mLAAgentSchema.getAgentCode());
			texttag.add("LAAgent.Name", mLAAgentSchema.getName());
			texttag.add("LABranchGroup.Name", tLABranchGroupDB.getName());
			texttag.add("Department", getUpComName(mLAAgentSchema
					.getBranchCode()));
			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}

			// xmlExport.outputDocumentToFile("D:/lis/ui/f1print/NCLtemplate/",
			// "testHZ"); //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlExport);

		} else {
			buildError("MeetF1BL", "已经打印面见通知书！");

		}
	}

	private void getPrintDataj() throws Exception {
		mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		LCContDB tLCContDB = new LCContDB();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);

		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}
		// 需要判断是否已经打印？！
		if (tLOPRTManagerDB.getStateFlag().equals("0")) {
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema(); // get all
																// message！

			// 打印时传入的是主险投保单的投保单号
			tLCContDB.setContNo(mLOPRTManagerSchema.getOtherNo());
			logger.debug("test by yaory:"
					+ mLOPRTManagerSchema.getOtherNo());
			if (!tLCContDB.getInfo()) {
				mErrors.copyAllErrors(tLCContDB.mErrors);
				throw new Exception("在获取保单信息时出错！");
			}
			mLCContSchema = tLCContDB.getSchema();

			// 查询打印队列的信息
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
			mOldPrtSeq = mLOPRTManagerSchema.getOldPrtSeq();

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("getprintData", "无效的打印状态");
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("getprintData", "该打印请求不是在请求状态");
			}

			if (!mLOPRTManagerSchema.getCode().equals("")
					&& !mLOPRTManagerSchema.getCode().equals(null)
					&& mLOPRTManagerSchema.getCode().equals("G04")) {
			} else {
				// 投保人地址和邮编
				LCAppntDB tLCAppntDB = new LCAppntDB();
				tLCAppntDB.setContNo(mLCContSchema.getContNo());
				if (tLCAppntDB.getInfo() == false) {
					mErrors.copyAllErrors(tLCAppntDB.mErrors);

					throw new Exception("在取得打印队列中数据时发生错误");
				}
			}
			mAgentCode = mLCContSchema.getAgentCode();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLAAgentDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLABranchGroupDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			String remark = "";
			remark = "DiseaseQuestionXHXT.vts";
			logger.debug("测试");

			// ////////////////////end write by yaory-2007-7-4-16:39////
			XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例

			// xmlExport.createDocument("Meet.vts", ""); //最好紧接着就初始化xml文档
			xmlExport.createDocument(remark, ""); // 最好紧接着就初始化xml文档
			TextTag texttag = new TextTag();
			String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
					+ StrTool.getDay() + "日";

			texttag.add("LCCont.ProposalContNo", mLCContSchema
					.getProposalContNo());
			texttag.add("LCCont.AppntName", mLCContSchema.getAppntName());
			texttag.add("LCCont.InsuredName", mLCContSchema.getInsuredName());
			texttag.add("LAAgent.AgentCode", mLAAgentSchema.getAgentCode());
			texttag.add("LAAgent.Name", mLAAgentSchema.getName());
			texttag.add("LABranchGroup.Name", tLABranchGroupDB.getName());
			texttag.add("Department", getUpComName(mLAAgentSchema
					.getBranchCode()));
			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}

			// xmlExport.outputDocumentToFile("D:/lis/ui/f1print/NCLtemplate/",
			// "testHZ"); //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlExport);

		} else {
			buildError("MeetF1BL", "已经打印面见通知书！");

		}
	}

	private void getPrintDatak() throws Exception {
		mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		LCContDB tLCContDB = new LCContDB();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);

		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}
		// 需要判断是否已经打印？！
		if (tLOPRTManagerDB.getStateFlag().equals("0")) {
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema(); // get all
																// message！

			// 打印时传入的是主险投保单的投保单号
			tLCContDB.setContNo(mLOPRTManagerSchema.getOtherNo());
			logger.debug("test by yaory:"
					+ mLOPRTManagerSchema.getOtherNo());
			if (!tLCContDB.getInfo()) {
				mErrors.copyAllErrors(tLCContDB.mErrors);
				throw new Exception("在获取保单信息时出错！");
			}
			mLCContSchema = tLCContDB.getSchema();

			// 查询打印队列的信息
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
			mOldPrtSeq = mLOPRTManagerSchema.getOldPrtSeq();

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("getprintData", "无效的打印状态");
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("getprintData", "该打印请求不是在请求状态");
			}
			if (!mLOPRTManagerSchema.getCode().equals("")
					&& !mLOPRTManagerSchema.getCode().equals(null)
					&& mLOPRTManagerSchema.getCode().equals("G04")) {
			} else {
				// 投保人地址和邮编
				LCAppntDB tLCAppntDB = new LCAppntDB();
				tLCAppntDB.setContNo(mLCContSchema.getContNo());
				if (tLCAppntDB.getInfo() == false) {
					mErrors.copyAllErrors(tLCAppntDB.mErrors);

					throw new Exception("在取得打印队列中数据时发生错误");
				}
			}
			mAgentCode = mLCContSchema.getAgentCode();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLAAgentDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLABranchGroupDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			String remark = "";
			remark = "DiseaseQuestionYYEJKWJ.vts";
			logger.debug("测试");

			// ////////////////////end write by yaory-2007-7-4-16:39////
			XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例

			// xmlExport.createDocument("Meet.vts", ""); //最好紧接着就初始化xml文档
			xmlExport.createDocument(remark, ""); // 最好紧接着就初始化xml文档
			TextTag texttag = new TextTag();
			String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
					+ StrTool.getDay() + "日";

			texttag.add("LCCont.ProposalContNo", mLCContSchema
					.getProposalContNo());
			texttag.add("LCCont.AppntName", mLCContSchema.getAppntName());
			texttag.add("LCCont.InsuredName", mLCContSchema.getInsuredName());
			texttag.add("LAAgent.AgentCode", mLAAgentSchema.getAgentCode());
			texttag.add("LAAgent.Name", mLAAgentSchema.getName());
			texttag.add("LABranchGroup.Name", tLABranchGroupDB.getName());
			texttag.add("Department", getUpComName(mLAAgentSchema
					.getBranchCode()));
			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}

			// xmlExport.outputDocumentToFile("D:/lis/ui/f1print/NCLtemplate/",
			// "testHZ"); //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlExport);

		} else {
			buildError("MeetF1BL", "已经打印面见通知书！");

		}
	}

	private void getPrintDatal() throws Exception {
		mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		LCContDB tLCContDB = new LCContDB();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);

		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}
		// 需要判断是否已经打印？！
		if (tLOPRTManagerDB.getStateFlag().equals("0")) {
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema(); // get all
																// message！

			// 打印时传入的是主险投保单的投保单号
			tLCContDB.setContNo(mLOPRTManagerSchema.getOtherNo());
			logger.debug("test by yaory:"
					+ mLOPRTManagerSchema.getOtherNo());
			if (!tLCContDB.getInfo()) {
				mErrors.copyAllErrors(tLCContDB.mErrors);
				throw new Exception("在获取保单信息时出错！");
			}
			mLCContSchema = tLCContDB.getSchema();

			// 查询打印队列的信息
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
			mOldPrtSeq = mLOPRTManagerSchema.getOldPrtSeq();

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("getprintData", "无效的打印状态");
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("getprintData", "该打印请求不是在请求状态");
			}
			if (!mLOPRTManagerSchema.getCode().equals("")
					&& !mLOPRTManagerSchema.getCode().equals(null)
					&& mLOPRTManagerSchema.getCode().equals("G04")) {
			} else {
				// 投保人地址和邮编
				LCAppntDB tLCAppntDB = new LCAppntDB();
				tLCAppntDB.setContNo(mLCContSchema.getContNo());
				if (tLCAppntDB.getInfo() == false) {
					mErrors.copyAllErrors(tLCAppntDB.mErrors);

					throw new Exception("在取得打印队列中数据时发生错误");
				}
			}
			mAgentCode = mLCContSchema.getAgentCode();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLAAgentDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLABranchGroupDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			String remark = "";
			remark = "sportdcb.vts";
			logger.debug("测试");

			// ////////////////////end write by yaory-2007-7-4-16:39////
			XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例

			// xmlExport.createDocument("Meet.vts", ""); //最好紧接着就初始化xml文档
			xmlExport.createDocument(remark, ""); // 最好紧接着就初始化xml文档
			TextTag texttag = new TextTag();
			String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
					+ StrTool.getDay() + "日";

			texttag.add("LCCont.ProposalContNo", mLCContSchema
					.getProposalContNo());
			texttag.add("AppntName", mLCContSchema.getAppntName());
			texttag.add("LCCont.InsuredName", mLCContSchema.getInsuredName());
			texttag.add("AgentCode", mLAAgentSchema.getAgentCode());
			texttag.add("AgentName", mLAAgentSchema.getName());
			texttag.add("BranchGroupName", tLABranchGroupDB.getName());
			texttag.add("Department", getUpComName(mLAAgentSchema
					.getBranchCode()));
			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}

			// xmlExport.outputDocumentToFile("D:/lis/ui/f1print/NCLtemplate/",
			// "testHZ"); //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlExport);

		} else {
			buildError("MeetF1BL", "已经打印面见通知书！");

		}
	}

	private void getPrintDatam() throws Exception {
		mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		LCContDB tLCContDB = new LCContDB();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);

		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}
		// 需要判断是否已经打印？！
		if (tLOPRTManagerDB.getStateFlag().equals("0")) {
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema(); // get all
																// message！

			// 打印时传入的是主险投保单的投保单号
			tLCContDB.setContNo(mLOPRTManagerSchema.getOtherNo());
			logger.debug("test by yaory:"
					+ mLOPRTManagerSchema.getOtherNo());
			if (!tLCContDB.getInfo()) {
				mErrors.copyAllErrors(tLCContDB.mErrors);
				throw new Exception("在获取保单信息时出错！");
			}
			mLCContSchema = tLCContDB.getSchema();

			// 查询打印队列的信息
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
			mOldPrtSeq = mLOPRTManagerSchema.getOldPrtSeq();

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("getprintData", "无效的打印状态");
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("getprintData", "该打印请求不是在请求状态");
			}
			if (!mLOPRTManagerSchema.getCode().equals("")
					&& !mLOPRTManagerSchema.getCode().equals(null)
					&& mLOPRTManagerSchema.getCode().equals("G04")) {
			} else {
				// 投保人地址和邮编
				LCAppntDB tLCAppntDB = new LCAppntDB();
				tLCAppntDB.setContNo(mLCContSchema.getContNo());
				if (tLCAppntDB.getInfo() == false) {
					mErrors.copyAllErrors(tLCAppntDB.mErrors);

					throw new Exception("在取得打印队列中数据时发生错误");
				}
			}
			mAgentCode = mLCContSchema.getAgentCode();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLAAgentDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLABranchGroupDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			String remark = "";
			remark = "CZWYWJ.vts";
			logger.debug("测试");

			// ////////////////////end write by yaory-2007-7-4-16:39////
			XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例

			// xmlExport.createDocument("Meet.vts", ""); //最好紧接着就初始化xml文档
			xmlExport.createDocument(remark, ""); // 最好紧接着就初始化xml文档
			TextTag texttag = new TextTag();
			String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
					+ StrTool.getDay() + "日";

			texttag.add("ContNo", mLCContSchema.getProposalContNo());
			texttag.add("LCCont.AppntName", mLCContSchema.getAppntName());
			texttag.add("LCCont.InsuredName", mLCContSchema.getInsuredName());
			texttag.add("AgentCode", mLAAgentSchema.getAgentCode());
			texttag.add("AgentName", mLAAgentSchema.getName());
			texttag.add("BranchGroupName", tLABranchGroupDB.getName());
			texttag.add("Department", getUpComName(mLAAgentSchema
					.getBranchCode()));
			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}

			// xmlExport.outputDocumentToFile("D:/lis/ui/f1print/NCLtemplate/",
			// "testHZ"); //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlExport);

		} else {
			buildError("MeetF1BL", "已经打印面见通知书！");

		}
	}

	private void getPrintDatan() throws Exception {
		mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		LCContDB tLCContDB = new LCContDB();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);

		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}
		// 需要判断是否已经打印？！
		if (tLOPRTManagerDB.getStateFlag().equals("0")) {
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema(); // get all
																// message！

			// 打印时传入的是主险投保单的投保单号
			tLCContDB.setContNo(mLOPRTManagerSchema.getOtherNo());
			logger.debug("test by yaory:"
					+ mLOPRTManagerSchema.getOtherNo());
			if (!tLCContDB.getInfo()) {
				mErrors.copyAllErrors(tLCContDB.mErrors);
				throw new Exception("在获取保单信息时出错！");
			}
			mLCContSchema = tLCContDB.getSchema();

			// 查询打印队列的信息
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
			mOldPrtSeq = mLOPRTManagerSchema.getOldPrtSeq();

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("getprintData", "无效的打印状态");
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("getprintData", "该打印请求不是在请求状态");
			}
			if (!mLOPRTManagerSchema.getCode().equals("")
					&& !mLOPRTManagerSchema.getCode().equals(null)
					&& mLOPRTManagerSchema.getCode().equals("G04")) {
			} else {
				// 投保人地址和邮编
				LCAppntDB tLCAppntDB = new LCAppntDB();
				tLCAppntDB.setContNo(mLCContSchema.getContNo());
				if (tLCAppntDB.getInfo() == false) {
					mErrors.copyAllErrors(tLCAppntDB.mErrors);

					throw new Exception("在取得打印队列中数据时发生错误");
				}
			}
			mAgentCode = mLCContSchema.getAgentCode();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLAAgentDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLABranchGroupDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			String remark = "";
			remark = "BSWJ.vts";
			logger.debug("测试");

			// ////////////////////end write by yaory-2007-7-4-16:39////
			XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例

			// xmlExport.createDocument("Meet.vts", ""); //最好紧接着就初始化xml文档
			xmlExport.createDocument(remark, ""); // 最好紧接着就初始化xml文档
			TextTag texttag = new TextTag();
			String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
					+ StrTool.getDay() + "日";

			texttag.add("ContNo", mLCContSchema.getProposalContNo());
			texttag.add("LCCont.AppntName", mLCContSchema.getAppntName());
			texttag.add("LCCont.InsuredName", mLCContSchema.getInsuredName());
			texttag.add("AgentCode", mLAAgentSchema.getAgentCode());
			texttag.add("AgentName", mLAAgentSchema.getName());
			texttag.add("BranchGroupName", tLABranchGroupDB.getName());
			texttag.add("Department", getUpComName(mLAAgentSchema
					.getBranchCode()));
			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}

			// xmlExport.outputDocumentToFile("D:/lis/ui/f1print/NCLtemplate/",
			// "testHZ"); //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlExport);

		} else {
			buildError("MeetF1BL", "已经打印面见通知书！");

		}
	}

	private void getPrintDatao() throws Exception {
		mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		LCContDB tLCContDB = new LCContDB();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);

		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}
		// 需要判断是否已经打印？！
		if (tLOPRTManagerDB.getStateFlag().equals("0")) {
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema(); // get all
																// message！

			// 打印时传入的是主险投保单的投保单号
			tLCContDB.setContNo(mLOPRTManagerSchema.getOtherNo());
			logger.debug("test by yaory:"
					+ mLOPRTManagerSchema.getOtherNo());
			if (!tLCContDB.getInfo()) {
				mErrors.copyAllErrors(tLCContDB.mErrors);
				throw new Exception("在获取保单信息时出错！");
			}
			mLCContSchema = tLCContDB.getSchema();

			// 查询打印队列的信息
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
			mOldPrtSeq = mLOPRTManagerSchema.getOldPrtSeq();

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("getprintData", "无效的打印状态");
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("getprintData", "该打印请求不是在请求状态");
			}
			if (!mLOPRTManagerSchema.getCode().equals("")
					&& !mLOPRTManagerSchema.getCode().equals(null)
					&& mLOPRTManagerSchema.getCode().equals("G04")) {
			} else {
				// 投保人地址和邮编
				LCAppntDB tLCAppntDB = new LCAppntDB();
				tLCAppntDB.setContNo(mLCContSchema.getContNo());
				if (tLCAppntDB.getInfo() == false) {
					mErrors.copyAllErrors(tLCAppntDB.mErrors);

					throw new Exception("在取得打印队列中数据时发生错误");
				}
			}
			mAgentCode = mLCContSchema.getAgentCode();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLAAgentDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLABranchGroupDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			String remark = "";
			remark = "JbwjGxa.vts";
			logger.debug("测试");

			// ////////////////////end write by yaory-2007-7-4-16:39////
			XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例

			// xmlExport.createDocument("Meet.vts", ""); //最好紧接着就初始化xml文档
			xmlExport.createDocument(remark, ""); // 最好紧接着就初始化xml文档
			TextTag texttag = new TextTag();
			String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
					+ StrTool.getDay() + "日";

			texttag.add("LCCont.ProposalContNo", mLCContSchema
					.getProposalContNo());
			texttag.add("LCCont.AppntName", mLCContSchema.getAppntName());
			texttag.add("LCCont.InsuredName", mLCContSchema.getInsuredName());
			texttag.add("LAAgent.AgentCode", mLAAgentSchema.getAgentCode());
			texttag.add("LAAgent.Name", mLAAgentSchema.getName());
			texttag.add("LABranchGroup.Name", tLABranchGroupDB.getName());
			texttag.add("Department", getUpComName(mLAAgentSchema
					.getBranchCode()));
			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}

			// xmlExport.outputDocumentToFile("D:/lis/ui/f1print/NCLtemplate/",
			// "testHZ"); //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlExport);

		} else {
			buildError("MeetF1BL", "已经打印面见通知书！");

		}
	}

	private void getPrintDatap() throws Exception {
		mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		LCContDB tLCContDB = new LCContDB();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);

		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}
		// 需要判断是否已经打印？！
		if (tLOPRTManagerDB.getStateFlag().equals("0")) {
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema(); // get all
																// message！

			// 打印时传入的是主险投保单的投保单号
			tLCContDB.setContNo(mLOPRTManagerSchema.getOtherNo());
			logger.debug("test by yaory:"
					+ mLOPRTManagerSchema.getOtherNo());
			if (!tLCContDB.getInfo()) {
				mErrors.copyAllErrors(tLCContDB.mErrors);
				throw new Exception("在获取保单信息时出错！");
			}
			mLCContSchema = tLCContDB.getSchema();

			// 查询打印队列的信息
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
			mOldPrtSeq = mLOPRTManagerSchema.getOldPrtSeq();

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("getprintData", "无效的打印状态");
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("getprintData", "该打印请求不是在请求状态");
			}
			if (!mLOPRTManagerSchema.getCode().equals("")
					&& !mLOPRTManagerSchema.getCode().equals(null)
					&& mLOPRTManagerSchema.getCode().equals("G04")) {
			} else {
				// 投保人地址和邮编
				LCAppntDB tLCAppntDB = new LCAppntDB();
				tLCAppntDB.setContNo(mLCContSchema.getContNo());
				if (tLCAppntDB.getInfo() == false) {
					mErrors.copyAllErrors(tLCAppntDB.mErrors);

					throw new Exception("在取得打印队列中数据时发生错误");
				}
			}
			mAgentCode = mLCContSchema.getAgentCode();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLAAgentDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLABranchGroupDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			String remark = "";
			remark = "Jszzzyzwj.vts";
			logger.debug("测试");

			// ////////////////////end write by yaory-2007-7-4-16:39////
			XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例

			// xmlExport.createDocument("Meet.vts", ""); //最好紧接着就初始化xml文档
			xmlExport.createDocument(remark, ""); // 最好紧接着就初始化xml文档
			TextTag texttag = new TextTag();
			String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
					+ StrTool.getDay() + "日";

			texttag.add("LCCont.ProposalContNo", mLCContSchema
					.getProposalContNo());
			texttag.add("LCCont.AppntName", mLCContSchema.getAppntName());
			texttag.add("LCCont.InsuredName", mLCContSchema.getInsuredName());
			texttag.add("LAAgent.AgentCode", mLAAgentSchema.getAgentCode());
			texttag.add("LAAgent.Name", mLAAgentSchema.getName());
			texttag.add("LABranchGroup.Name", tLABranchGroupDB.getName());
			texttag.add("Department", getUpComName(mLAAgentSchema
					.getBranchCode()));
			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}

			// xmlExport.outputDocumentToFile("D:/lis/ui/f1print/NCLtemplate/",
			// "testHZ"); //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlExport);

		} else {
			buildError("MeetF1BL", "已经打印面见通知书！");

		}
	}

	private void getPrintDataq() throws Exception {
		mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		LCContDB tLCContDB = new LCContDB();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);

		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}
		// 需要判断是否已经打印？！
		if (tLOPRTManagerDB.getStateFlag().equals("0")) {
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema(); // get all
																// message！

			// 打印时传入的是主险投保单的投保单号
			tLCContDB.setContNo(mLOPRTManagerSchema.getOtherNo());
			logger.debug("test by yaory:"
					+ mLOPRTManagerSchema.getOtherNo());
			if (!tLCContDB.getInfo()) {
				mErrors.copyAllErrors(tLCContDB.mErrors);
				throw new Exception("在获取保单信息时出错！");
			}
			mLCContSchema = tLCContDB.getSchema();

			// 查询打印队列的信息
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
			mOldPrtSeq = mLOPRTManagerSchema.getOldPrtSeq();

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("getprintData", "无效的打印状态");
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("getprintData", "该打印请求不是在请求状态");
			}
			if (!mLOPRTManagerSchema.getCode().equals("")
					&& !mLOPRTManagerSchema.getCode().equals(null)
					&& mLOPRTManagerSchema.getCode().equals("G04")) {
			} else {
				// 投保人地址和邮编
				LCAppntDB tLCAppntDB = new LCAppntDB();
				tLCAppntDB.setContNo(mLCContSchema.getContNo());
				if (tLCAppntDB.getInfo() == false) {
					mErrors.copyAllErrors(tLCAppntDB.mErrors);

					throw new Exception("在取得打印队列中数据时发生错误");
				}
			}
			mAgentCode = mLCContSchema.getAgentCode();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLAAgentDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLAAgentDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			String remark = "";
			remark = "Wcrydcwj.vts";
			logger.debug("测试");

			// ////////////////////end write by yaory-2007-7-4-16:39////
			XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例

			// xmlExport.createDocument("Meet.vts", ""); //最好紧接着就初始化xml文档
			xmlExport.createDocument(remark, ""); // 最好紧接着就初始化xml文档
			TextTag texttag = new TextTag();
			String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
					+ StrTool.getDay() + "日";

			texttag.add("LCCont.ProposalContNo", mLCContSchema
					.getProposalContNo());
			texttag.add("LCCont.AppntName", mLCContSchema.getAppntName());
			texttag.add("LCCont.InsuredName", mLCContSchema.getInsuredName());
			texttag.add("LAAgent.AgentCode", mLAAgentSchema.getAgentCode());
			texttag.add("LAAgent.Name", mLAAgentSchema.getName());
			texttag.add("LABranchGroup.Name", tLABranchGroupDB.getName());
			texttag.add("Department", getUpComName(mLAAgentSchema
					.getBranchCode()));
			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}

			// xmlExport.outputDocumentToFile("D:/lis/ui/f1print/NCLtemplate/",
			// "testHZ"); //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlExport);

		} else {
			buildError("MeetF1BL", "已经打印面见通知书！");

		}
	}

	private void getPrintDatar() throws Exception {
		mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		LCContDB tLCContDB = new LCContDB();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);

		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}
		// 需要判断是否已经打印？！
		if (tLOPRTManagerDB.getStateFlag().equals("0")) {
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema(); // get all
																// message！

			// 打印时传入的是主险投保单的投保单号
			tLCContDB.setContNo(mLOPRTManagerSchema.getOtherNo());
			logger.debug("test by yaory:"
					+ mLOPRTManagerSchema.getOtherNo());
			if (!tLCContDB.getInfo()) {
				mErrors.copyAllErrors(tLCContDB.mErrors);
				throw new Exception("在获取保单信息时出错！");
			}
			mLCContSchema = tLCContDB.getSchema();

			// 查询打印队列的信息
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
			mOldPrtSeq = mLOPRTManagerSchema.getOldPrtSeq();

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("getprintData", "无效的打印状态");
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("getprintData", "该打印请求不是在请求状态");
			}
			if (!mLOPRTManagerSchema.getCode().equals("")
					&& !mLOPRTManagerSchema.getCode().equals(null)
					&& mLOPRTManagerSchema.getCode().equals("G04")) {
			} else {
				// 投保人地址和邮编
				LCAppntDB tLCAppntDB = new LCAppntDB();
				tLCAppntDB.setContNo(mLCContSchema.getContNo());
				if (tLCAppntDB.getInfo() == false) {
					mErrors.copyAllErrors(tLCAppntDB.mErrors);

					throw new Exception("在取得打印队列中数据时发生错误");
				}
			}
			mAgentCode = mLCContSchema.getAgentCode();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLAAgentDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLABranchGroupDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			String remark = "";
			remark = "Wdrstbwj.vts";
			logger.debug("测试");

			// ////////////////////end write by yaory-2007-7-4-16:39////
			XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例

			// xmlExport.createDocument("Meet.vts", ""); //最好紧接着就初始化xml文档
			xmlExport.createDocument(remark, ""); // 最好紧接着就初始化xml文档
			TextTag texttag = new TextTag();
			String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
					+ StrTool.getDay() + "日";

			texttag.add("LCCont.ProposalContNo", mLCContSchema
					.getProposalContNo());
			texttag.add("LCCont.AppntName", mLCContSchema.getAppntName());
			texttag.add("LCCont.InsuredName", mLCContSchema.getInsuredName());
			texttag.add("LAAgent.AgentCode", mLAAgentSchema.getAgentCode());
			texttag.add("LAAgent.Name", mLAAgentSchema.getName());
			texttag.add("LABranchGroup.Name", tLABranchGroupDB.getName());
			texttag.add("Department", getUpComName(mLAAgentSchema
					.getBranchCode()));
			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}

			// xmlExport.outputDocumentToFile("D:/lis/ui/f1print/NCLtemplate/",
			// "testHZ"); //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlExport);

		} else {
			buildError("MeetF1BL", "已经打印面见通知书！");

		}
	}

	private void getPrintDatas() throws Exception {
		mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		LCContDB tLCContDB = new LCContDB();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);

		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}
		// 需要判断是否已经打印？！
		if (tLOPRTManagerDB.getStateFlag().equals("0")) {
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema(); // get all
																// message！

			// 打印时传入的是主险投保单的投保单号
			tLCContDB.setContNo(mLOPRTManagerSchema.getOtherNo());
			logger.debug("test by yaory:"
					+ mLOPRTManagerSchema.getOtherNo());
			if (!tLCContDB.getInfo()) {
				mErrors.copyAllErrors(tLCContDB.mErrors);
				throw new Exception("在获取保单信息时出错！");
			}
			mLCContSchema = tLCContDB.getSchema();

			// 查询打印队列的信息
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
			mOldPrtSeq = mLOPRTManagerSchema.getOldPrtSeq();

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("getprintData", "无效的打印状态");
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("getprintData", "该打印请求不是在请求状态");
			}
			if (!mLOPRTManagerSchema.getCode().equals("")
					&& !mLOPRTManagerSchema.getCode().equals(null)
					&& mLOPRTManagerSchema.getCode().equals("G04")) {
			} else {
				// 投保人地址和邮编
				LCAppntDB tLCAppntDB = new LCAppntDB();
				tLCAppntDB.setContNo(mLCContSchema.getContNo());
				if (tLCAppntDB.getInfo() == false) {
					mErrors.copyAllErrors(tLCAppntDB.mErrors);

					throw new Exception("在取得打印队列中数据时发生错误");
				}
			}
			mAgentCode = mLCContSchema.getAgentCode();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLAAgentDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLABranchGroupDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			String remark = "";
			remark = "JbwjXhxky.vts";
			logger.debug("测试");

			// ////////////////////end write by yaory-2007-7-4-16:39////
			XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例

			// xmlExport.createDocument("Meet.vts", ""); //最好紧接着就初始化xml文档
			xmlExport.createDocument(remark, ""); // 最好紧接着就初始化xml文档
			TextTag texttag = new TextTag();
			String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
					+ StrTool.getDay() + "日";

			texttag.add("LCCont.ProposalContNo", mLCContSchema
					.getProposalContNo());
			texttag.add("LCCont.AppntName", mLCContSchema.getAppntName());
			texttag.add("LCCont.InsuredName", mLCContSchema.getInsuredName());
			texttag.add("LAAgent.AgentCode", mLAAgentSchema.getAgentCode());
			texttag.add("LAAgent.Name", mLAAgentSchema.getName());
			texttag.add("LABranchGroup.Name", tLABranchGroupDB.getName());
			texttag.add("Department", getUpComName(mLAAgentSchema
					.getBranchCode()));
			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}

			// xmlExport.outputDocumentToFile("D:/lis/ui/f1print/NCLtemplate/",
			// "testHZ"); //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlExport);

		} else {
			buildError("MeetF1BL", "已经打印面见通知书！");

		}
	}

	private void getPrintDatat() throws Exception {
		mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		LCContDB tLCContDB = new LCContDB();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);

		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}
		// 需要判断是否已经打印？！
		if (tLOPRTManagerDB.getStateFlag().equals("0")) {
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema(); // get all
																// message！

			// 打印时传入的是主险投保单的投保单号
			tLCContDB.setContNo(mLOPRTManagerSchema.getOtherNo());
			logger.debug("test by yaory:"
					+ mLOPRTManagerSchema.getOtherNo());
			if (!tLCContDB.getInfo()) {
				mErrors.copyAllErrors(tLCContDB.mErrors);
				throw new Exception("在获取保单信息时出错！");
			}
			mLCContSchema = tLCContDB.getSchema();

			// 查询打印队列的信息
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
			mOldPrtSeq = mLOPRTManagerSchema.getOldPrtSeq();

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("getprintData", "无效的打印状态");
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("getprintData", "该打印请求不是在请求状态");
			}
			if (!mLOPRTManagerSchema.getCode().equals("")
					&& !mLOPRTManagerSchema.getCode().equals(null)
					&& mLOPRTManagerSchema.getCode().equals("G04")) {
			} else {
				// 投保人地址和邮编
				LCAppntDB tLCAppntDB = new LCAppntDB();
				tLCAppntDB.setContNo(mLCContSchema.getContNo());
				if (tLCAppntDB.getInfo() == false) {
					mErrors.copyAllErrors(tLCAppntDB.mErrors);

					throw new Exception("在取得打印队列中数据时发生错误");
				}
			}
			mAgentCode = mLCContSchema.getAgentCode();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLAAgentDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLABranchGroupDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			String remark = "";
			remark = "Yhyycyrywj.vts";
			logger.debug("测试");

			// ////////////////////end write by yaory-2007-7-4-16:39////
			XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例

			// xmlExport.createDocument("Meet.vts", ""); //最好紧接着就初始化xml文档
			xmlExport.createDocument(remark, ""); // 最好紧接着就初始化xml文档
			TextTag texttag = new TextTag();
			String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
					+ StrTool.getDay() + "日";

			texttag.add("LCCont.ProposalContNo", mLCContSchema
					.getProposalContNo());
			texttag.add("LCCont.AppntName", mLCContSchema.getAppntName());
			texttag.add("LCCont.InsuredName", mLCContSchema.getInsuredName());
			texttag.add("LAAgent.AgentCode", mLAAgentSchema.getAgentCode());
			texttag.add("LAAgent.Name", mLAAgentSchema.getName());
			texttag.add("LABranchGroup.Name", tLABranchGroupDB.getName());
			texttag.add("Department", getUpComName(mLAAgentSchema
					.getBranchCode()));
			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}

			// xmlExport.outputDocumentToFile("D:/lis/ui/f1print/NCLtemplate/",
			// "testHZ"); //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlExport);

		} else {
			buildError("MeetF1BL", "已经打印面见通知书！");

		}
	}

	private void getPrintDataMeet() throws Exception {
		XmlExport xmlExport = new XmlExport();// 新建一个XmlExport的实例
		xmlExport.createDocument("RnewMeetNotice_MS.vts", "");// 最好紧接着就初始化xml文档

		LCPolDB tLCPolDB = new LCPolDB();
		LCContDB tLCContDB = new LCContDB();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();

		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);// 将prtseq传给DB，目的查找所有相关信息，然后还要返回给schema
		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}
		// 需要判断是否已经打印？！
		if (tLOPRTManagerDB.getStateFlag().equals("0")) {

			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();// get all
																// message！

			// 打印时传入的是主险投保单的投保单号
			tLCContDB.setContNo(mLOPRTManagerSchema.getOtherNo());

			if (!tLCContDB.getInfo()) {
				mErrors.copyAllErrors(tLCContDB.mErrors);
				throw new Exception("在获取保单信息时出错！");
			}
			String tSQL = "";
			tSQL = "select riskcode from lcpol where contno='"
					+ "?contno?" + "' and mainpolno=polno";
			SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
			sqlbv5.sql(tSQL);
			sqlbv5.put("contno", tLCContDB.getContNo());
			String tRiskCode = "";
			ExeSQL tExeSQL = new ExeSQL();
			tRiskCode = tExeSQL.getOneValue(sqlbv5);
			if (tRiskCode == null || tRiskCode.equals("")) {
				buildError("outputXML", "查询合同主险编码出错");
			}
			// String strPolNo = tLCContDB.getPolNo();
			/*
			 * if( !tLCPolDB.getMainPolNo().equals(strPolNo) ) { throw new
			 * Exception("传入的号码不是主险投保单号码"); }
			 */

			// 查询打印队列的信息
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("getprintData", "无效的打印状态");
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("getprintData", "该打印请求不是在请求状态");
			}
			// 调用打印服务

			mLOPRTManagerSchema.setDoneDate(PubFun.getCurrentDate());
			mLOPRTManagerSchema.setDoneTime(PubFun.getCurrentTime());

			tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);
			if (tLOPRTManagerDB.getInfo() == false) {
				mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
				buildError("outputXML", "在取得打印队列中数据时发生错误");
			}

			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(tLCContDB.getAgentCode());
			tLAAgentDB.getInfo();
			String agentphone = "";
			if(tLAAgentDB.getSchema().getPhone()==null||tLAAgentDB.getSchema().getPhone().equals(""))
			{
				agentphone=tLAAgentDB.getSchema().getMobile();
			}
			else
			{
				agentphone=tLAAgentDB.getSchema().getPhone();
			}
			TextTag texttag = new TextTag();
			texttag.add("BarCode1", mLOPRTManagerSchema.getPrtSeq());
			texttag.add("AppntName", tLCContDB.getAppntName());
			texttag.add("PolNo", tLCContDB.getContNo());
			texttag.add("ContNo", tLCContDB.getContNo());
			texttag.add("PrtNo", tLCContDB.getPrtNo());
			texttag.add("AgentName", getAgentName(tLCContDB.getAgentCode()));
			texttag.add("agentphone", agentphone);	
			texttag.add("AgentCode", tLCContDB.getAgentCode());
			texttag.add("ManageCom", tLCContDB.getManageCom());
			texttag.add("RiskName", getRiskName(tRiskCode));
			texttag.add("InsuredName", tLCContDB.getInsuredName());
            //核保师代码 核保<--核保师代码
			ExeSQL xxExeSQL = new ExeSQL();
			SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
			String tSql="";
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				tSql = " select operator from LCUWMaster where contno='" + "?contno?" + "' and uwtype='1' and rownum=1 ";
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				tSql = " select operator from LCUWMaster where contno='" + "?contno?" + "' and uwtype='1' limit 0,1 ";
			}
			sqlbv6.sql(tSql);
			sqlbv6.put("contno", tLCContDB.getContNo());
			String xxOperator=xxExeSQL.getOneValue(sqlbv6);
			texttag.add("UWOperator", xxOperator);
			texttag.add("PrtSeq", mLOPRTManagerSchema.getPrtSeq());

			SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
			texttag.add("Today", df.format(new Date()));

			if (texttag.size() > 0)
				xmlExport.addTextTag(texttag);

			mResult.clear();
			mResult.addElement(xmlExport);
		}

	}

	private String getRiskName(String strRiskCode) throws Exception {
		LMRiskDB tLMRiskDB = new LMRiskDB();
		tLMRiskDB.setRiskCode(strRiskCode);
		if (!tLMRiskDB.getInfo()) {
			mErrors.copyAllErrors(tLMRiskDB.mErrors);
			throw new Exception("在取得主险LMRisk的数据时发生错误");
		}

		return tLMRiskDB.getRiskName();
	}

	private String getAgentName(String strAgentCode) throws Exception {
		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(strAgentCode);
		if (!tLAAgentDB.getInfo()) {
			mErrors.copyAllErrors(tLAAgentDB.mErrors);
			throw new Exception("在取得LAAgent的数据时发生错误");
		}
		return tLAAgentDB.getName();
	}

	private String getAskName(String strComCode) {
		mSql = "select CodeName from LDcode where Code = '" + "?strComCode?"
				+ "' and CodeType = 'rreporttype'";
		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		sqlbv7.sql(mSql);
		sqlbv7.put("strComCode", strComCode);
		ExeSQL tExeSQL = new ExeSQL();
		return tExeSQL.getOneValue(sqlbv7);
		// LDCodeDB tLDCodeDB = new LDCodeDB();
		// tLDCodeDB.setCode(strComCode);
		// tLDCodeDB.setCodeType("station");
		// if (!tLDCodeDB.getInfo())
		// {
		// mErrors.copyAllErrors(tLDCodeDB.mErrors);
		// return "";
		// }
		// return tLDCodeDB.getCodeName();
	}

	public String getUpComName(String comcode) {
		LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
		LABranchGroupDB ttLABranchGroupDB = new LABranchGroupDB();
		tLABranchGroupDB.setAgentGroup(comcode);
		if (!tLABranchGroupDB.getInfo()) {
			this.buildError("getUpComName", comcode + "机构不存在！");
			return null;
		} else {
			ttLABranchGroupDB.setAgentGroup(tLABranchGroupDB.getUpBranch());
			if (!ttLABranchGroupDB.getInfo()) {
				this.buildError("getUpComName", "在查找comcode所属的营业部时出错！");
				return null;
			} else {
				return ttLABranchGroupDB.getName();
			}
		}
	}

	private String getComName(String strComCode) {
		LDComDB tLDComDB = new LDComDB();

		tLDComDB.setComCode(strComCode);
		if (!tLDComDB.getInfo()) {
			mErrors.copyAllErrors(tLDComDB.mErrors);
			buildError("outputXML", "在取得Ldcom的数据时发生错误");
		}
		return tLDComDB.getShortName();
	}

}
