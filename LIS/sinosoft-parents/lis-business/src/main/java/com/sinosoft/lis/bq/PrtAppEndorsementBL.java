package com.sinosoft.lis.bq;
import java.io.InputStream;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDCode1DB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPEdorPrintSchema;
import com.sinosoft.lis.vschema.LDCode1Set;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

public class PrtAppEndorsementBL {
private static Logger logger = Logger.getLogger(PrtAppEndorsementBL.class);
	public PrtAppEndorsementBL() {
	}


	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	// 保全受理号 By WangJH
	private String mEdorAcceptNo = "";

	// 批单号
	private String mEdorNo = "";




	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private MMap mMap = new MMap();
	@SuppressWarnings("unused")
	private String mGetNoticeNo = "";

	// By WangJH
	public PrtAppEndorsementBL(String aEdorAcceptNo, String aGetNoticeNo) {
		mEdorAcceptNo = aEdorAcceptNo;
		mGetNoticeNo = aGetNoticeNo;
	}

	// By Lizhuo
	// public PrtAppEndorsementBL(String aEdorNo) {
	// mEdorNo = aEdorNo;
	// }

	// By lizhuo

	public PrtAppEndorsementBL(String aEdorAcceptNo) {
		mEdorAcceptNo = aEdorAcceptNo;
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
		tLPEdorItemDB.setEdorAcceptNo(mEdorAcceptNo);
		tLPEdorItemSet = tLPEdorItemDB.query();
		mEdorNo = tLPEdorItemSet.get(1).getEdorNo();
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据，将数据备份到本类中
		logger.debug("start getinputdata");

		if (!getInputData(cInputData)) {
			return false;
		}

		logger.debug("End getInputData");

		if (!this.dealData()) {
			return false;
		}

		logger.debug("End dealData");

		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorICDetailBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);

		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	private boolean dealData() {
		logger.debug("\n\nStart Write Print Data\n\n");
		logger.debug("EdorAcceptNo ================= > " + mEdorAcceptNo);
		logger.debug("EdorNo ================= > " + mEdorNo);

		// 按不同项目内容进行打印（一个批单号可打印）
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		ExeSQL aExeSQL = new ExeSQL();
		SSRS aSSRS = new SSRS();
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql("select contno from lpedoritem where edoracceptno = '?mEdorAcceptNo?' group by contno");
		sqlbv.put("mEdorAcceptNo", mEdorAcceptNo);
		aSSRS = aExeSQL
				.execSQL(sqlbv);
		mResult.clear();
		for (int j = 1; j <= aSSRS.getMaxRow(); j++) {
			// 最好紧接着就初始化xml文档
			xmlexport.createDocument("PrtAppEndorsement.vts", "printer");
			// 一个保单出一张批单
			tLPEdorItemDB.setContNo(aSSRS.GetText(j, 1));
			tLPEdorItemDB.setEdorAcceptNo(mEdorAcceptNo);
			tLPEdorItemSet = tLPEdorItemDB.query();
			if (tLPEdorItemSet.size() < 1) {
				CError.buildErr(this, "查询保全项目表失败，未找到保全项目!");
				return false;
			}
			boolean mClassflag = true;
			for (int i = 1; i <= tLPEdorItemSet.size(); i++) {
				// 批单打印有各个项目的单独生成打印数据
				tLPEdorItemSchema = tLPEdorItemSet.get(i);
				ExeSQL tExeSQL = new ExeSQL();
				SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
				sqlbv1.sql("select edorname from lmedoritem where edorcode = '?edorcode?'");
				sqlbv1.put("edorcode", tLPEdorItemSchema.getEdorType());
				String EdorName = tExeSQL
						.getOneValue(sqlbv1);
				try {
					Class tClass = Class.forName("com.sinosoft.lis.bq.PEdor"
							+ tLPEdorItemSchema.getEdorType() + "PrintBL");
					EdorPrint tEdorPrint = (EdorPrint) tClass.newInstance();
					VData aVData = new VData();
					aVData.add(tLPEdorItemSchema);
					aVData.add(xmlexport);
					aVData.add(mGlobalInput);
					if (!tEdorPrint.submitData(aVData, "PRINT"
							+ tLPEdorItemSchema.getEdorType())) {
						mErrors.copyAllErrors(tEdorPrint.mErrors);
						mErrors.addOneError("保全项目" + EdorName + "打印处理失败!");
						return false;
					}
					VData cVData = new VData();
					cVData = tEdorPrint.getResult();
					xmlexport = (XmlExport) cVData.getObjectByObjectName(
							"XmlExport", 0);
				} catch (ClassNotFoundException ex) {
					mClassflag = false;
					logger.debug("未找到" + EdorName + "保全项目打印处理!");
				} catch (Exception ex) {
					CError.buildErr(this, "保全项目" + EdorName + "打印处理失败!");
					return false;
				}
				logger.debug("成功完成" + EdorName + "保全项目打印处理!");
			}
			// 不在此文件中生成各个项目的打印数据
			// ----------- Delete by Lizhuo-----------Begin----2005.6.8
			// //从2层查询变更为3层查询--By WangJH
			// //增加了保全受理号，提升到定层的保全申请主表LPEdorApp开始查找
			// //保全申请主表LPEdorApp->个险保全批改表LPEdorMain->个险保全项目表LPEdorItem
			// LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
			// tLPEdorAppDB.setEdorAcceptNo(mEdorAcceptNo);
			// LPEdorAppSet tLPEdorAppSet = new LPEdorAppSet();
			// tLPEdorAppSet.set(tLPEdorAppDB.query());
			// if (tLPEdorAppSet == null || tLPEdorAppSet.size() < 1) {
			// buildError("dealData", "在LPEdorApp中无相关保全受理号的数据");
			// return false;
			// }
			// mLPEdorAppSchema = tLPEdorAppSet.get(1);
			//
			// xmlexport.addDisplayControl("displayHead");
			//
			// String tCustomerNo = "";
			// String sql = "";
			// textTag.add("EdorAcceptNo", mLPEdorAppSchema.getEdorAcceptNo());
			// textTag.add("BarCode1", mLPEdorAppSchema.getEdorAcceptNo());
			// textTag.add("BarCodeParam1",
			// "BarHeight=25&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");
			// textTag.add("AppDate", mLPEdorAppSchema.getEdorAppDate());
			// textTag.add("ConfDate", mLPEdorAppSchema.getMakeDate());
			// textTag.add("Operator", mLPEdorAppSchema.getOperator());
			// textTag.add("PrintDate", StrTool.replace(StrTool.getDate(), "/",
			// "-"));
			// if (mLPEdorAppSchema.getOtherNoType().equals("1")) {
			// tCustomerNo = mLPEdorAppSchema.getOtherNo();
			// sql = "select * from LPAppnt where edorno in (select edorno from
			// lpedormain where edoracceptno='" +
			// mLPEdorAppSchema.getEdorAcceptNo() + "')";
			// LPAppntDB tLPAppntDB = new LPAppntDB();
			// LPAppntSet tLPAppntSet = tLPAppntDB.executeQuery(sql);
			// if (tLPAppntSet != null && tLPAppntSet.size() > 0) {
			// tCustomerNo = tLPAppntSet.get(1).getAppntNo();
			// }
			// textTag.add("CustomerNo", tCustomerNo);
			// } else if (mLPEdorAppSchema.getOtherNoType().equals("3")) {
			// sql = "select * from lpcont where contno='" +
			// mLPEdorAppSchema.getOtherNo() +
			// "' and edorno in (select edorno from lpedormain where
			// edoracceptno='" +
			// mLPEdorAppSchema.getEdorAcceptNo() + "')";
			// LPContDB tLPContDB = new LPContDB();
			// LPContSet tLPContSet = tLPContDB.executeQuery(sql);
			// if (tLPContSet == null || tLPContSet.size() < 1) {
			// LCContDB tLCContDB = new LCContDB();
			// tLCContDB.setContNo(mLPEdorAppSchema.getOtherNo());
			// if (!tLCContDB.getInfo()) {
			// buildError("dealData", "保全申请的合同不存在");
			// return false;
			// }
			// tCustomerNo = tLCContDB.getAppntNo();
			// } else {
			// tCustomerNo = tLPContSet.get(1).getAppntNo();
			// }
			// textTag.add("CustomerNo", tCustomerNo);
			// }
			// sql = "select * from LPPerson where CustomerNo='" + tCustomerNo +
			// "' and edorno in (select edorno from lpedormain where
			// edoracceptno='" +
			// mLPEdorAppSchema.getEdorAcceptNo() + "')";
			// LPPersonSchema tLPPersonSchema = new LPPersonSchema();
			// LPPersonDB tLPPersonDB = new LPPersonDB();
			// LPPersonSet tLPPersonSet = tLPPersonDB.executeQuery(sql);
			// if (tLPPersonSet == null || tLPPersonSet.size() < 1) {
			// LDPersonDB tLDPersonDB = new LDPersonDB();
			// tLDPersonDB.setCustomerNo(tCustomerNo);
			// if (!tLDPersonDB.getInfo()) {
			// CError tError = new CError();
			// tError.moduleName = "PrtAppEndorsementBL";
			// tError.functionName = "getInsuredCM";
			// tError.errorMessage = "LDPersonDB不存在相应记录!";
			// this.mErrors.addOneError(tError);
			// return false;
			// }
			// LDPersonSchema tLDPersonSchema = tLDPersonDB.getSchema();
			// Reflections aReflections = new Reflections();
			// aReflections.transFields(tLPPersonSchema, tLDPersonSchema);
			// } else {
			// tLPPersonSchema = tLPPersonSet.get(1).getSchema();
			// }
			// textTag.add("EdorAppName", tLPPersonSchema.getName());
			// textTag.add("AppntName", tLPPersonSchema.getName());
			//
			// sql = "select * from LPAddress where CustomerNo='" + tCustomerNo
			// +
			// "' and edorno in (select edorno from lpedormain where
			// edoracceptno='" +
			// mLPEdorAppSchema.getEdorAcceptNo() + "')";
			// LPAddressSchema tLPAddressSchema = new LPAddressSchema();
			// LPAddressDB tLPAddressDB = new LPAddressDB();
			// LPAddressSet tLPAddressSet = tLPAddressDB.executeQuery(sql);
			// if (tLPAddressSet == null || tLPAddressSet.size() < 1) {
			// LCAddressDB tLCAddressDB = new LCAddressDB();
			// tLCAddressDB.setCustomerNo(tCustomerNo);
			// LCAddressSet tLCAddressSet = tLCAddressDB.query();
			// if (tLCAddressSet == null || tLCAddressSet.size() < 1) {
			// CError tError = new CError();
			// tError.moduleName = "PrtAppEndorsementBL";
			// tError.functionName = "getInsuredCM";
			// tError.errorMessage = "LCAddressDB不存在相应记录!";
			// this.mErrors.addOneError(tError);
			// return false;
			// }
			// LCAddressSchema tLCAddressSchema =
			// tLCAddressSet.get(1).getSchema();
			// Reflections aReflections = new Reflections();
			// aReflections.transFields(tLPAddressSchema, tLCAddressSchema);
			// } else {
			// tLPAddressSchema = tLPAddressSet.get(1).getSchema();
			// }
			// textTag.add("EdorAppZipCode", tLPAddressSchema.getZipCode());
			// textTag.add("EdorAppAddress",
			// tLPAddressSchema.getPostalAddress());
			//
			// //查询个险保全项目表中保全受理号为mEdorAcceptNo的记录
			// String AppSQL = "select * from LPEdorMain where EdorAcceptNo ='"
			// +
			// mEdorAcceptNo +
			// "'order by MakeDate,MakeTime";
			// //--End
			//
			// LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
			// //tLPEdorMainDB.setEdorNo(mEdorNo); //By WangJH
			// LPEdorMainSet tLPEdorMainSet = new LPEdorMainSet();
			// //tLPEdorMainSet.set(tLPEdorMainDB.query()); //By WangJH
			// tLPEdorMainSet = tLPEdorMainDB.executeQuery(AppSQL); //By WangJH
			// if (0 == tLPEdorMainSet.size()) {
			// buildError("dealData", "在LPEdorMain中无相关批单号的数据");
			// return false;
			// }
			//
			// //增加了一层循环 By WangJH
			// for (int i = 1; i <= tLPEdorMainSet.size(); i++) {
			// LPEdorMainSchema tLPEdorMainSchema = tLPEdorMainSet.get(i);
			// mContNo = tLPEdorMainSchema.getContNo(); //取得保全记录的合同号
			//
			// mEdorNo = tLPEdorMainSchema.getEdorNo(); //取得保全记录的批单号 By WangJH
			// //查询个险保全项目表中批单号为mEdorNo的记录
			// sql = "select * from LPEdorItem where edorno='" + mEdorNo +
			// "' order by InsuredNo,EdorType";
			// LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
			// LPEdorItemSet tLPEdorItemSet = new LPEdorItemDBSet();
			// tLPEdorItemSet = tLPEdorItemDB.executeQuery(sql);
			// if (0 == tLPEdorItemSet.size()) {
			// buildError("dealData", "保全项目表中无相关批单号的数据");
			// return false;
			// }
			//
			// LCContSchema tLCContSchema = new LCContSchema();
			// LCContDB tLCContDB = new LCContDB();
			// tLCContDB.setContNo(mContNo);
			// if (!tLCContDB.getInfo()) {
			// LBContDB tLBContDB = new LBContDB();
			// tLBContDB.setContNo(mContNo);
			// if (!tLBContDB.getInfo()) {
			// return false;
			// }
			// Reflections aReflections = new Reflections();
			// aReflections.transFields(tLCContSchema, tLBContDB.getSchema());
			// } else {
			// tLCContSchema.setSchema(tLCContDB.getSchema());
			// }
			//
			// for (int j = 1; j <= tLPEdorItemSet.size(); j++) {
			// LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
			// tLPEdorItemSchema = tLPEdorItemSet.get(j);
			//
			// if (tLPEdorItemSchema.getEdorType().equals("LR")) { //遗失补发--By
			// WangJH
			// xmlexport.addDisplayControl("displayLR");
			// if (!this.getDetailLR(tLPEdorItemSchema)) {
			// return false;
			// }
			// tFlag = true;
			// } else if (tLPEdorItemSchema.getEdorType().equals("WT")) {
			// //犹豫退保--By WangJH
			// xmlexport.addDisplayControl("displayWT");
			// if (!this.getDetailWT(tLPEdorItemSchema)) {
			// return false;
			// }
			//
			// tFlag = true;
			// } else if (tLPEdorItemSchema.getEdorType().equals("AD")) {
			// //犹豫退保--By WangJH
			// xmlexport.addDisplayControl("displayAD");
			// if (!this.getDetailAD(tLPEdorItemSchema)) {
			// return false;
			// }
			//
			// tFlag = true;
			// } else if (tLPEdorItemSchema.getEdorType().equals("AC")) {
			// //投保人资料变更
			// xmlexport.addDisplayControl("displayAC");
			// if (!this.getDetailAC(tLPEdorItemSchema)) {
			// return false;
			// }
			//
			// tFlag = true;
			// } else if (tLPEdorItemSchema.getEdorType().equals("CM")) {
			// //客户基本资料变更--By WangJH
			// if (mSameCM == 0) { //有多条CM项目时只进入getDetailCM一次
			// xmlexport.addDisplayControl("displayCM");
			// if (!this.getDetailCM(tLPEdorItemSchema)) {
			// return false;
			// }
			// tFlag = true;
			// mSameCM++;
			// }
			// }
			//
			// else if (tLPEdorItemSchema.getEdorType().equals("CT")) { //中止合同
			// xmlexport.addDisplayControl("displayCT");
			// if (!this.getDetailCT(tLPEdorItemSchema)) {
			// return false;
			// }
			// tFlag = true;
			// }
			// // else if (tLPEdorItemSchema.getEdorType().equals("BB"))
			// // { //投保人变更
			// // xmlexport.addDisplayControl("displayBB");
			// // if (!this.getDetailBB(tLPEdorItemSchema))
			// // {
			// // return false;
			// // }
			// // tFlag = true;
			// // }
			// // else if (tLPEdorItemSchema.getEdorType().equals("IC"))
			// // { //被保人重要信息变更
			// // xmlexport.addDisplayControl("displayIC");
			// // if (!this.getDetailIC(tLPEdorItemSchema))
			// // {
			// // return false;
			// // }
			// // tFlag = true;
			// // }
			//
			// else { //对没有的保全项目类型生成空打印数据
			// if (!this.getDetailForBlankType(tLPEdorItemSchema)) {
			// return false;
			// }
			// tFlag = true;
			// }
			// }
			// } //增加的一层循环 By WangJH
			//
			// if (!tFlag) {
			// buildError("dealData", "发生一个未知错误使主批单不能打印！");
			// return false;
			// //return true;
			// }
			// //收费明细
			// getPayPrintData(mEdorAcceptNo);
			//
			// if (textTag.size() > 0) {
			// myDocument = xmlexport.addTextTag(textTag);
			// }
			// ----------- Delete by Lizhuo-----------End------2005.6.8

			if (mClassflag) {
				// 生成主打印批单schema
				LPEdorPrintSchema tLPEdorAppPrintSchemaMain = new LPEdorPrintSchema();
				tLPEdorAppPrintSchemaMain.setEdorNo(tLPEdorItemSchema
						.getEdorNo());
				tLPEdorAppPrintSchemaMain.setManageCom(mGlobalInput.ManageCom);
				tLPEdorAppPrintSchemaMain.setPrtFlag("N");
				tLPEdorAppPrintSchemaMain.setPrtTimes(0);
				tLPEdorAppPrintSchemaMain.setMakeDate(PubFun.getCurrentDate());
				tLPEdorAppPrintSchemaMain.setMakeTime(PubFun.getCurrentTime());
				tLPEdorAppPrintSchemaMain.setOperator(mGlobalInput.Operator);
				tLPEdorAppPrintSchemaMain
						.setModifyDate(PubFun.getCurrentDate());
				tLPEdorAppPrintSchemaMain
						.setModifyTime(PubFun.getCurrentTime());
				InputStream ins = xmlexport.getInputStream();

				// 生成主打印批单schema
				// LPEdorAppPrintSchema tLPEdorAppPrintSchemaMain = new
				// LPEdorAppPrintSchema();
				// tLPEdorAppPrintSchemaMain.setEdorAcceptNo(mEdorNo); //走批单号
				// tLPEdorAppPrintSchemaMain.setManageCom(mGlobalInput.ManageCom);
				// tLPEdorAppPrintSchemaMain.setPrtFlag("N");
				// tLPEdorAppPrintSchemaMain.setPrtTimes(0);
				// tLPEdorAppPrintSchemaMain.setMakeDate(PubFun.getCurrentDate());
				// tLPEdorAppPrintSchemaMain.setMakeTime(PubFun.getCurrentTime());
				// tLPEdorAppPrintSchemaMain.setOperator(mGlobalInput.Operator);
				// tLPEdorAppPrintSchemaMain.setModifyDate(PubFun.getCurrentDate());
				// tLPEdorAppPrintSchemaMain.setModifyTime(PubFun.getCurrentTime());
				// InputStream ins = xmlexport.getInputStream();
				// xmlexport.outputDocumentToFile("c:\\", "xmlexport");
				tLPEdorAppPrintSchemaMain.setEdorInfo(ins);
				SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
				sqlbv2.sql("delete from LPEdorPrint where EdorNo='?EdorNo?'");
				sqlbv2.put("EdorNo", tLPEdorItemSchema.getEdorNo());
				mMap.put(sqlbv2, "DELETE");
				mMap.put(tLPEdorAppPrintSchemaMain, "BLOBINSERT");

				mResult.addElement(mMap);
			}
			// try {
			// ins.close();
			// } catch (Exception e) {
			// e.printStackTrace();
			// } Modified by lizhuo
		}
		return true;
	}

	private String getFeeFinaName(String strFeeFinaType) {
		logger.debug(strFeeFinaType);

		LDCode1DB tLDCode1DB = new LDCode1DB();
		tLDCode1DB.setCode(strFeeFinaType);

		LDCode1Set tLDCode1Set = tLDCode1DB.query();

		if ((tLDCode1Set != null) && (tLDCode1Set.size() > 0)) {
			return tLDCode1Set.get(1).getCodeAlias();
		} else {
			return strFeeFinaType;
		}
	}




	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		PrtAppEndorsementBL tPrtAppEndorsementBL = new PrtAppEndorsementBL(
				"6120050923000005");
		VData tVData = new VData();
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.ManageCom = "86";
		tGlobalInput.Operator = "bq";
		tVData.addElement(tGlobalInput);
		tPrtAppEndorsementBL.submitData(tVData, "");
		@SuppressWarnings("unused")
		VData vdata = tPrtAppEndorsementBL.getResult();
	}
}
