/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.f1printgrp;
import org.apache.log4j.Logger;

import com.f1j.ss.BookModelImpl;
import com.f1j.ss.ReadParams;
import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LABranchGroupDB;
import com.sinosoft.lis.db.LAComDB;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCRReportPrtDB;
import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LAComSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LCRReportPrtSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
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
public class MeetF1PBL {
private static Logger logger = Logger.getLogger(MeetF1PBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private String mSql = "";
	private VData mResult = new VData();

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

	public MeetF1PBL() {
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
				getPrintData1();

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
			tSSRS = exeSql2
					.execSQL("select sysvarvalue From ldsysvar where sysvar='DataPrintCombine'");
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

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("getprintData", "无效的打印状态");
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("getprintData", "该打印请求不是在请求状态");
			}

			// 投保人地址和邮编
			LCAppntDB tLCAppntDB = new LCAppntDB();
			tLCAppntDB.setContNo(mLCContSchema.getContNo());
			if (tLCAppntDB.getInfo() == false) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");
			}

			mAgentCode = mLCContSchema.getAgentCode();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}

			// 2-1 查询体检子表
			String[] MeetTitle = new String[2];
			MeetTitle[0] = "ID";
			MeetTitle[1] = "ITEM";
			ListTable tListTable = new ListTable();
			String str[] = null;
			tListTable.setName("WJ"); // 对应模版体检部分的行对象名

			LCRReportPrtSet tLCRReportPrtSet = new LCRReportPrtSet();
			LCRReportPrtDB tLCRReportPrtDB = new LCRReportPrtDB();
			tLCRReportPrtDB.setPrtSeq(mPrtSeq);
			tLCRReportPrtSet = tLCRReportPrtDB.query();

			for (int i = 1; i <= tLCRReportPrtSet.size(); i++) {

				str = new String[2];
				str[0] = (new Integer(i)).toString(); // 序号
				str[1] = getAskName(tLCRReportPrtSet.get(i).getAskCode()); // 序号对应的内容
				tListTable.add(str);
			}

			// VtsFileCombine vtsfilecombine = new VtsFileCombine();
			// BookModelImpl tb =
			// vtsfilecombine.dataCombine("D:/lis/ui/f1print/NCLtemplate/try1.vts",
			// "D:/lis/ui/f1print/NCLtemplate/try.vts");
			// vtsfilecombine.write(tb, "D:/lis/ui/f1print/NCLtemplate/new.vts");

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
				texttag.add("BarCode1", "UN011");
			} else {
				texttag.add("BarCode1", "UN012");
			}

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
			texttag.add("ManageComName", getComName(mLCContSchema
					.getManageCom()));
			texttag.add("LCCont.AppntName", mLCContSchema.getAppntName());
			texttag.add("AppntName", mLCContSchema.getAppntName());
			texttag.add("LCCont.InsuredName", mLCContSchema.getInsuredName());
			texttag.add("LABranchGroup.Name", tLABranchGroupDB.getName());
			texttag.add("LAAgent.Name", mLAAgentSchema.getName());
			texttag.add("LAAgent.AgentCode", mLAAgentSchema.getAgentCode());
			texttag.add("LCContl.UWOperator", mLCContSchema.getUWOperator());
			texttag.add("LOPRTManager.Remark", mLOPRTManagerSchema.getRemark());
			texttag.add("SumPrem", mLCContSchema.getPrem());
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

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("getprintData", "无效的打印状态");
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("getprintData", "该打印请求不是在请求状态");
			}

			// 投保人地址和邮编
			LCAppntDB tLCAppntDB = new LCAppntDB();
			tLCAppntDB.setContNo(mLCContSchema.getContNo());
			if (tLCAppntDB.getInfo() == false) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");
			}

			mAgentCode = mLCContSchema.getAgentCode();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}

			// 2-1 查询体检子表
			String[] MeetTitle = new String[2];
			MeetTitle[0] = "ID";
			MeetTitle[1] = "ITEM";
			ListTable tListTable = new ListTable();
			String str[] = null;
			tListTable.setName("WJ"); // 对应模版体检部分的行对象名

			LCRReportPrtSet tLCRReportPrtSet = new LCRReportPrtSet();
			LCRReportPrtDB tLCRReportPrtDB = new LCRReportPrtDB();
			tLCRReportPrtDB.setPrtSeq(mPrtSeq);
			tLCRReportPrtSet = tLCRReportPrtDB.query();

			for (int i = 1; i <= tLCRReportPrtSet.size(); i++) {

				str = new String[2];
				str[0] = (new Integer(i)).toString(); // 序号
				str[1] = getAskName(tLCRReportPrtSet.get(i).getAskCode()); // 序号对应的内容
				tListTable.add(str);
			}

			// VtsFileCombine vtsfilecombine = new VtsFileCombine();
			// BookModelImpl tb =
			// vtsfilecombine.dataCombine("D:/lis/ui/f1print/NCLtemplate/try1.vts",
			// "D:/lis/ui/f1print/NCLtemplate/try3.vts");
			// vtsfilecombine.write(tb, "D:/lis/ui/f1print/NCLtemplate/new1.vts");
			//
			// String remark="";
			// remark="new1.vts";//这些是测试用的

			// /////////////////////进行合并 write by yaory -2007-7-4-16:39////
			// first query how many questionaires
			ExeSQL exeSql = new ExeSQL();
			SSRS testSSRS = new SSRS();
			// testSSRS = exeSql.execSQL("select sysvarvalue From ldsysvar
			// where sysvar='DataPrintCombine'");
			// String TemplatePath=testSSRS.GetText(1,1);
			String TemplatePath = tWebPath;
			logger.debug("路径-模板-in combine===" + TemplatePath);
			logger.debug("呵呵，很高兴=====" + mLOPRTManagerSchema.getPrtSeq());

			testSSRS = exeSql
					.execSQL("select askcode from LCRReportPrt where prtseq='"
							+ mLOPRTManagerSchema.getPrtSeq() + "'");
			String remark = "";

			if (testSSRS.MaxRow == 0) {
				remark = "HbWjTzs.vts";
			} else {
				VtsFileCombine vtsfilecombine = new VtsFileCombine();
				int array = testSSRS.getMaxRow();
				String[] paths = new String[array + 1];

				for (int i = 1; i <= testSSRS.MaxRow; i++) {
					if (testSSRS.GetText(i, 1).equals("87"))
						paths[i - 1] = TemplatePath + "liveData.vts";
					else if (testSSRS.GetText(i, 1).equals("88"))
						paths[i - 1] = TemplatePath + "financeData.vts";
					else if (testSSRS.GetText(i, 1).equals("89"))
						paths[i - 1] = TemplatePath + "DXData.vts";
					else if (testSSRS.GetText(i, 1).equals("90"))
						paths[i - 1] = TemplatePath + "XCData.vts";
					else if (testSSRS.GetText(i, 1).equals("91"))
						paths[i - 1] = TemplatePath + "TNBData.vts";
					else if (testSSRS.GetText(i, 1).equals("92"))
						paths[i - 1] = TemplatePath + "JZXData.vts";
					else if (testSSRS.GetText(i, 1).equals("93"))
						paths[i - 1] = TemplatePath + "XTData.vts";
					else if (testSSRS.GetText(i, 1).equals("94"))
						paths[i - 1] = TemplatePath + "XTData.vts";
					else if (testSSRS.GetText(i, 1).equals("95"))
						paths[i - 1] = TemplatePath + "NXJKBCWJData.vts";
					else if (testSSRS.GetText(i, 1).equals("96"))
						paths[i - 1] = TemplatePath + "GYData.vts";
					else if (testSSRS.GetText(i, 1).equals("97"))
						paths[i - 1] = TemplatePath + "HXDData.vts";
					else if (testSSRS.GetText(i, 1).equals("98"))
						paths[i - 1] = TemplatePath + "XHXTData.vts";
					else if (testSSRS.GetText(i, 1).equals("99"))
						paths[i - 1] = TemplatePath + "YYEJKWJData.vts";
					else if (testSSRS.GetText(i, 1).equals("100"))
						paths[i - 1] = TemplatePath + "sportdcbData.vts";
					else if (testSSRS.GetText(i, 1).equals("101"))
						paths[i - 1] = TemplatePath + "CZWYWJData.vts";
					else if (testSSRS.GetText(i, 1).equals("102"))
						paths[i - 1] = TemplatePath + "BSWJData.vts";
					else if (testSSRS.GetText(i, 1).equals("103"))
						paths[i - 1] = TemplatePath + "JbwjGxaData.vts";
					else if (testSSRS.GetText(i, 1).equals("104"))
						paths[i - 1] = TemplatePath + "JszzzyzwjData.vts";
					else if (testSSRS.GetText(i, 1).equals("105"))
						paths[i - 1] = TemplatePath + "WcrydcwjData.vts";
					else if (testSSRS.GetText(i, 1).equals("106"))
						paths[i - 1] = TemplatePath + "WdrstbwjData.vts";
					else if (testSSRS.GetText(i, 1).equals("107"))
						paths[i - 1] = TemplatePath + "JbwjXhxkyData.vts";
					else if (testSSRS.GetText(i, 1).equals("108"))
						paths[i - 1] = TemplatePath + "YhyycyrywjData.vts";
				}

				paths[array] = TemplatePath + "announceData.vts";
				BookModelImpl tb = vtsfilecombine.dataCombine(paths);
				vtsfilecombine.write(tb, TemplatePath + "new.vts");
				remark = "new.vts";
			}
			// else if(testSSRS.MaxRow==1)
			// {
			// VtsFileCombine vtsfilecombine = new VtsFileCombine();
			// if(testSSRS.GetText(1,1).equals("87"))
			// {
			// // BookModelImpl tb = vtsfilecombine.dataCombine(TemplatePath+"liveData.vts",
			// TemplatePath+"announceData.vts");
			// // vtsfilecombine.write(tb, TemplatePath+"NCLtemplate/new.vts");
			// // remark="new.vts";
			// String[] paths =
			// {TemplatePath+"liveData.vts",TemplatePath+"announceData.vts"};
			// BookModelImpl tb = vtsfilecombine.dataCombine(paths);
			// vtsfilecombine.write(tb, TemplatePath+"new.vts");
			// remark="new.vts";
			//
			// }
			// else{
			// // BookModelImpl tb =
			// vtsfilecombine.dataCombine(TemplatePath+"financeData.vts",
			// TemplatePath+"announceData.vts");
			// // vtsfilecombine.write(tb, TemplatePath+"NCLtemplate/new.vts");
			// // remark="new.vts";
			// String[] paths =
			// {TemplatePath+"financeData.vts",TemplatePath+"announceData.vts"};
			// BookModelImpl tb = vtsfilecombine.dataCombine(paths);
			// vtsfilecombine.write(tb, TemplatePath+"new.vts");
			// remark="new.vts";
			//
			// }
			// }
			// else if(testSSRS.MaxRow>=2)
			// {
			// VtsFileCombine vtsfilecombine = new VtsFileCombine();
			// // BookModelImpl tb =
			// vtsfilecombine.dataCombine(TemplatePath+"financeData.vts",
			// TemplatePath+"liveData.vts");
			// // vtsfilecombine.write(tb, TemplatePath+"finalive.vts");
			// // tb = vtsfilecombine.dataCombine(TemplatePath+"finalive.vts",
			// TemplatePath+"announceData.vts");
			// //以上存在的原因是没有在VTSFILECOMBINE中增加新方法
			// logger.debug("模板路径===="+TemplatePath);
			// String[] paths =
			// {TemplatePath+"financeData.vts",TemplatePath+"liveData.vts",TemplatePath+"DXData.vts",TemplatePath+"announceData.vts"};
			// BookModelImpl tb = vtsfilecombine.dataCombine(paths);
			// vtsfilecombine.write(tb, TemplatePath+"new.vts");
			// remark="new.vts";
			// }

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

			texttag.add("LCCont.ContNo", mLCContSchema.getContNo());
			texttag.add("LCCont.AppntName", mLCContSchema.getAppntName());
			texttag.add("LCCont.InsuredName", mLCContSchema.getInsuredName());
			texttag.add("LABranchGroup.Name", tLABranchGroupDB.getName());
			texttag.add("LAAgent.Name", mLAAgentSchema.getName());
			texttag.add("LCCont.AgentCode", mLAAgentSchema.getAgentCode());
			texttag.add("LCCont.UWOperator", mLCContSchema.getUWOperator());
			texttag.add("LOPRTManager.Remark", mLOPRTManagerSchema.getRemark());
			texttag.add("SumPrem", mLCContSchema.getPrem());
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

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("getprintData", "无效的打印状态");
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("getprintData", "该打印请求不是在请求状态");
			}

			// 投保人地址和邮编
			LCAppntDB tLCAppntDB = new LCAppntDB();
			tLCAppntDB.setContNo(mLCContSchema.getContNo());
			if (tLCAppntDB.getInfo() == false) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");
			}

			mAgentCode = mLCContSchema.getAgentCode();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}

			// 2-1 查询体检子表
			String[] MeetTitle = new String[2];
			MeetTitle[0] = "ID";
			MeetTitle[1] = "ITEM";
			ListTable tListTable = new ListTable();
			String str[] = null;
			tListTable.setName("WJ"); // 对应模版体检部分的行对象名

			LCRReportPrtSet tLCRReportPrtSet = new LCRReportPrtSet();
			LCRReportPrtDB tLCRReportPrtDB = new LCRReportPrtDB();
			tLCRReportPrtDB.setPrtSeq(mPrtSeq);
			tLCRReportPrtSet = tLCRReportPrtDB.query();

			for (int i = 1; i <= tLCRReportPrtSet.size(); i++) {

				str = new String[2];
				str[0] = (new Integer(i)).toString(); // 序号
				str[1] = getAskName(tLCRReportPrtSet.get(i).getAskCode()); // 序号对应的内容
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
			texttag.add("AgentName", mLAAgentSchema.getAgentCode());
			texttag.add("BranchGroupName", tLABranchGroupDB.getName());
			texttag.add("Department", "运营部");
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

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("getprintData", "无效的打印状态");
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("getprintData", "该打印请求不是在请求状态");
			}

			// 投保人地址和邮编
			LCAppntDB tLCAppntDB = new LCAppntDB();
			tLCAppntDB.setContNo(mLCContSchema.getContNo());
			if (tLCAppntDB.getInfo() == false) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");
			}

			mAgentCode = mLCContSchema.getAgentCode();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}

			// 2-1 查询体检子表
			String[] MeetTitle = new String[2];
			MeetTitle[0] = "ID";
			MeetTitle[1] = "ITEM";
			ListTable tListTable = new ListTable();
			String str[] = null;
			tListTable.setName("WJ"); // 对应模版体检部分的行对象名

			LCRReportPrtSet tLCRReportPrtSet = new LCRReportPrtSet();
			LCRReportPrtDB tLCRReportPrtDB = new LCRReportPrtDB();
			tLCRReportPrtDB.setPrtSeq(mPrtSeq);
			tLCRReportPrtSet = tLCRReportPrtDB.query();

			for (int i = 1; i <= tLCRReportPrtSet.size(); i++) {

				str = new String[2];
				str[0] = (new Integer(i)).toString(); // 序号
				str[1] = getAskName(tLCRReportPrtSet.get(i).getAskCode()); // 序号对应的内容
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
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema(); // get all message！

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

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("getprintData", "无效的打印状态");
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("getprintData", "该打印请求不是在请求状态");
			}

			// 投保人地址和邮编
			LCAppntDB tLCAppntDB = new LCAppntDB();
			tLCAppntDB.setContNo(mLCContSchema.getContNo());
			if (tLCAppntDB.getInfo() == false) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");
			}

			mAgentCode = mLCContSchema.getAgentCode();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}

			// 2-1 查询体检子表
			String[] MeetTitle = new String[2];
			MeetTitle[0] = "ID";
			MeetTitle[1] = "ITEM";
			ListTable tListTable = new ListTable();
			String str[] = null;
			tListTable.setName("WJ"); // 对应模版体检部分的行对象名

			LCRReportPrtSet tLCRReportPrtSet = new LCRReportPrtSet();
			LCRReportPrtDB tLCRReportPrtDB = new LCRReportPrtDB();
			tLCRReportPrtDB.setPrtSeq(mPrtSeq);
			tLCRReportPrtSet = tLCRReportPrtDB.query();

			for (int i = 1; i <= tLCRReportPrtSet.size(); i++) {

				str = new String[2];
				str[0] = (new Integer(i)).toString(); // 序号
				str[1] = getAskName(tLCRReportPrtSet.get(i).getAskCode()); // 序号对应的内容
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
			testSSRS = exeSql
					.execSQL("select askcode from LCRReportPrt where contno='"
							+ mLOPRTManagerSchema.getOtherNo() + "'");
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
			texttag.add("SumPrem", mLCContSchema.getPrem());
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

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("getprintData", "无效的打印状态");
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("getprintData", "该打印请求不是在请求状态");
			}

			// 投保人地址和邮编
			LCAppntDB tLCAppntDB = new LCAppntDB();
			tLCAppntDB.setContNo(mLCContSchema.getContNo());
			if (tLCAppntDB.getInfo() == false) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");
			}

			mAgentCode = mLCContSchema.getAgentCode();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

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
			texttag.add("Department", "运营部");
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

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("getprintData", "无效的打印状态");
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("getprintData", "该打印请求不是在请求状态");
			}

			// 投保人地址和邮编
			LCAppntDB tLCAppntDB = new LCAppntDB();
			tLCAppntDB.setContNo(mLCContSchema.getContNo());
			if (tLCAppntDB.getInfo() == false) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");
			}

			mAgentCode = mLCContSchema.getAgentCode();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

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
			texttag.add("Department", "运营部");
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

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("getprintData", "无效的打印状态");
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("getprintData", "该打印请求不是在请求状态");
			}

			// 投保人地址和邮编
			LCAppntDB tLCAppntDB = new LCAppntDB();
			tLCAppntDB.setContNo(mLCContSchema.getContNo());
			if (tLCAppntDB.getInfo() == false) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");
			}

			mAgentCode = mLCContSchema.getAgentCode();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

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
			texttag.add("Department", "运营部");
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

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("getprintData", "无效的打印状态");
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("getprintData", "该打印请求不是在请求状态");
			}

			// 投保人地址和邮编
			LCAppntDB tLCAppntDB = new LCAppntDB();
			tLCAppntDB.setContNo(mLCContSchema.getContNo());
			if (tLCAppntDB.getInfo() == false) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");
			}

			mAgentCode = mLCContSchema.getAgentCode();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

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
			texttag.add("Department", "运营部");
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

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("getprintData", "无效的打印状态");
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("getprintData", "该打印请求不是在请求状态");
			}

			// 投保人地址和邮编
			LCAppntDB tLCAppntDB = new LCAppntDB();
			tLCAppntDB.setContNo(mLCContSchema.getContNo());
			if (tLCAppntDB.getInfo() == false) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");
			}

			mAgentCode = mLCContSchema.getAgentCode();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

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
			texttag.add("Department", "运营部");
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

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("getprintData", "无效的打印状态");
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("getprintData", "该打印请求不是在请求状态");
			}

			// 投保人地址和邮编
			LCAppntDB tLCAppntDB = new LCAppntDB();
			tLCAppntDB.setContNo(mLCContSchema.getContNo());
			if (tLCAppntDB.getInfo() == false) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");
			}

			mAgentCode = mLCContSchema.getAgentCode();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

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
			texttag.add("Department", "运营部");
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

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("getprintData", "无效的打印状态");
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("getprintData", "该打印请求不是在请求状态");
			}

			// 投保人地址和邮编
			LCAppntDB tLCAppntDB = new LCAppntDB();
			tLCAppntDB.setContNo(mLCContSchema.getContNo());
			if (tLCAppntDB.getInfo() == false) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");
			}

			mAgentCode = mLCContSchema.getAgentCode();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

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
			texttag.add("Department", "运营部");
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

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("getprintData", "无效的打印状态");
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("getprintData", "该打印请求不是在请求状态");
			}

			// 投保人地址和邮编
			LCAppntDB tLCAppntDB = new LCAppntDB();
			tLCAppntDB.setContNo(mLCContSchema.getContNo());
			if (tLCAppntDB.getInfo() == false) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");
			}

			mAgentCode = mLCContSchema.getAgentCode();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

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
			texttag.add("Department", "运营部");
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

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("getprintData", "无效的打印状态");
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("getprintData", "该打印请求不是在请求状态");
			}

			// 投保人地址和邮编
			LCAppntDB tLCAppntDB = new LCAppntDB();
			tLCAppntDB.setContNo(mLCContSchema.getContNo());
			if (tLCAppntDB.getInfo() == false) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");
			}

			mAgentCode = mLCContSchema.getAgentCode();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

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
			texttag.add("Department", "运营部");
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

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("getprintData", "无效的打印状态");
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("getprintData", "该打印请求不是在请求状态");
			}

			// 投保人地址和邮编
			LCAppntDB tLCAppntDB = new LCAppntDB();
			tLCAppntDB.setContNo(mLCContSchema.getContNo());
			if (tLCAppntDB.getInfo() == false) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");
			}

			mAgentCode = mLCContSchema.getAgentCode();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

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
			texttag.add("Department", "运营部");
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

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("getprintData", "无效的打印状态");
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("getprintData", "该打印请求不是在请求状态");
			}

			// 投保人地址和邮编
			LCAppntDB tLCAppntDB = new LCAppntDB();
			tLCAppntDB.setContNo(mLCContSchema.getContNo());
			if (tLCAppntDB.getInfo() == false) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");
			}

			mAgentCode = mLCContSchema.getAgentCode();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

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
			texttag.add("Department", "运营部");
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

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("getprintData", "无效的打印状态");
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("getprintData", "该打印请求不是在请求状态");
			}

			// 投保人地址和邮编
			LCAppntDB tLCAppntDB = new LCAppntDB();
			tLCAppntDB.setContNo(mLCContSchema.getContNo());
			if (tLCAppntDB.getInfo() == false) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");
			}

			mAgentCode = mLCContSchema.getAgentCode();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

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
			texttag.add("Department", "运营部");
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

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("getprintData", "无效的打印状态");
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("getprintData", "该打印请求不是在请求状态");
			}

			// 投保人地址和邮编
			LCAppntDB tLCAppntDB = new LCAppntDB();
			tLCAppntDB.setContNo(mLCContSchema.getContNo());
			if (tLCAppntDB.getInfo() == false) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");
			}

			mAgentCode = mLCContSchema.getAgentCode();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

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
			texttag.add("Department", "运营部");
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

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("getprintData", "无效的打印状态");
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("getprintData", "该打印请求不是在请求状态");
			}

			// 投保人地址和邮编
			LCAppntDB tLCAppntDB = new LCAppntDB();
			tLCAppntDB.setContNo(mLCContSchema.getContNo());
			if (tLCAppntDB.getInfo() == false) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");
			}

			mAgentCode = mLCContSchema.getAgentCode();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

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
			texttag.add("Department", "运营部");
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

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("getprintData", "无效的打印状态");
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("getprintData", "该打印请求不是在请求状态");
			}

			// 投保人地址和邮编
			LCAppntDB tLCAppntDB = new LCAppntDB();
			tLCAppntDB.setContNo(mLCContSchema.getContNo());
			if (tLCAppntDB.getInfo() == false) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");
			}

			mAgentCode = mLCContSchema.getAgentCode();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

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
			texttag.add("Department", "运营部");
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

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("getprintData", "无效的打印状态");
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("getprintData", "该打印请求不是在请求状态");
			}

			// 投保人地址和邮编
			LCAppntDB tLCAppntDB = new LCAppntDB();
			tLCAppntDB.setContNo(mLCContSchema.getContNo());
			if (tLCAppntDB.getInfo() == false) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");
			}

			mAgentCode = mLCContSchema.getAgentCode();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

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
			texttag.add("Department", "运营部");
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

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("getprintData", "无效的打印状态");
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("getprintData", "该打印请求不是在请求状态");
			}

			// 投保人地址和邮编
			LCAppntDB tLCAppntDB = new LCAppntDB();
			tLCAppntDB.setContNo(mLCContSchema.getContNo());
			if (tLCAppntDB.getInfo() == false) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");
			}

			mAgentCode = mLCContSchema.getAgentCode();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

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
			texttag.add("Department", "运营部");
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

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("getprintData", "无效的打印状态");
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("getprintData", "该打印请求不是在请求状态");
			}

			// 投保人地址和邮编
			LCAppntDB tLCAppntDB = new LCAppntDB();
			tLCAppntDB.setContNo(mLCContSchema.getContNo());
			if (tLCAppntDB.getInfo() == false) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");
			}

			mAgentCode = mLCContSchema.getAgentCode();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

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
			texttag.add("Department", "运营部");
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

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("getprintData", "无效的打印状态");
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("getprintData", "该打印请求不是在请求状态");
			}

			// 投保人地址和邮编
			LCAppntDB tLCAppntDB = new LCAppntDB();
			tLCAppntDB.setContNo(mLCContSchema.getContNo());
			if (tLCAppntDB.getInfo() == false) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");
			}

			mAgentCode = mLCContSchema.getAgentCode();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

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
			texttag.add("Department", "运营部");
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

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("getprintData", "无效的打印状态");
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("getprintData", "该打印请求不是在请求状态");
			}

			// 投保人地址和邮编
			LCAppntDB tLCAppntDB = new LCAppntDB();
			tLCAppntDB.setContNo(mLCContSchema.getContNo());
			if (tLCAppntDB.getInfo() == false) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");
			}

			mAgentCode = mLCContSchema.getAgentCode();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);

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
			texttag.add("Department", "运营部");
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

	private String getAskName(String strComCode) {
		mSql = "select CodeName from LDcode where Code = '" + strComCode
				+ "' and CodeType = 'rreporttype'";
		ExeSQL tExeSQL = new ExeSQL();
		return tExeSQL.getOneValue(mSql);
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

	private String getComName(String strComCode) {
		LDComDB tLDComDB = new LDComDB();

		tLDComDB.setComCode(strComCode);
		if (!tLDComDB.getInfo()) {
			mErrors.copyAllErrors(tLDComDB.mErrors);
			buildError("outputXML", "在取得Ldcom的数据时发生错误");
		}
		return tLDComDB.getName();
	}

}
