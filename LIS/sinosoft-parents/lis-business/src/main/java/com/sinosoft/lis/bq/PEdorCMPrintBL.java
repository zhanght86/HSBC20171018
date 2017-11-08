package com.sinosoft.lis.bq;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPPersonDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPPersonSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title: 客户资料变更批单数据生成
 * </p>
 * <p>
 * Description: 生成客户资料批单打印所需要的数据
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * </p>
 * 
 * @author wangyan
 * @version 1.0
 */

public class PEdorCMPrintBL implements EdorPrint {
	private static Logger logger = Logger.getLogger(PEdorCMPrintBL.class);

	// 公共数据
	private VData mResult = new VData();

	// public CErrors mErrors = new CErrors();

	// 全局变量
	private String mOperate;

	private VData mInputData = new VData();

	@SuppressWarnings("unused")
	private GlobalInput mGlobalInput = new GlobalInput();

	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	private LPEdorAppSchema mLPEdorAppSchema = new LPEdorAppSchema();

	private XmlExportNew xmlexport = new XmlExportNew();

	private TextTag mTextTag = new TextTag();
	// public static final String FormulaOneVTS = "PrtEndorsement.vts";
	public PEdorCMPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		// logger.debug("Start preparing the data to print ====>" +
		// mOperate);

		// 数据传输
		if (!getInputData()) {
			mErrors.addOneError("CM数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			mErrors.addOneError("CM传入数据无效!");
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			mErrors.addOneError("CM数据打印失败!");
			return false;
		}

		// 准备数据
		if (!prepareData()) {
			mErrors.addOneError("CM准备数据失败!");
			return false;
		}

		logger.debug("End preparing the data to print ====>" + mOperate);
		return true;
	}

	private boolean getInputData() {
		mLPEdorItemSchema = (LPEdorItemSchema) mInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mLPEdorAppSchema = (LPEdorAppSchema) mInputData.getObjectByObjectName(
				"LPEdorAppSchema", 0);
		xmlexport = (XmlExportNew) mInputData
				.getObjectByObjectName("XmlExportNew", 0);
		if (mLPEdorItemSchema == null || xmlexport == null) {
			return false;
		}
		return true;
	}

	private boolean checkData() {
		if (!mOperate.equals("PRINTCM")) {
			return false;
		}
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
		tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemSet.size() < 1) {
			return false;
		}
		mLPEdorItemSchema.setSchema(tLPEdorItemSet.get(1));
		return true;
	}

	private boolean dealData() {
		BqNameFun.AddEdorHead(mLPEdorItemSchema, mLPEdorAppSchema, xmlexport);
		xmlexport.addDisplayControl("displayCM");
		xmlexport.addDisplayControl("displayCMTailRemark");

		// 取出所有的保单
		String tSumConNo = "";
		LCContDB tLCContDB = new LCContDB();
		LCContSet tLCContSet = new LCContSet();
		String tConSQL = "select * from lpcont where (InsuredNo='?InsuredNo?' or appntno='?InsuredNo?') and edorno='?edorno?' and edortype='CM' and appflag='1' ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tConSQL);
		sqlbv.put("InsuredNo", mLPEdorItemSchema.getInsuredNo());
		sqlbv.put("edorno", mLPEdorItemSchema.getEdorNo());
		tLCContSet = tLCContDB.executeQuery(sqlbv);
		if (tLCContSet.size() > 0) {
			for (int k = 1; k <= tLCContSet.size(); k++) {
				if (!mLPEdorItemSchema.getContNo().equals(
						tLCContSet.get(k).getContNo())) {
					tSumConNo += tLCContSet.get(k).getContNo() + ",";
				}

			}
		}
		// else
		// {
		// mErrors.addOneError("获取客户保单信息失败！");
		// return false;
		// }

		if (!"".equals(tSumConNo)) {
			tSumConNo = tSumConNo.substring(0, tSumConNo.lastIndexOf(","));
			xmlexport.addDisplayControl("displayCMTail");
			mTextTag.add("SumContNo", tSumConNo);
		}

		// 兹经***（保全申请资格人）申请， 并经本公司审核同意将客户：崔建平（客户编码29392）的重要资料更正如下：
		// BqNameFun.AddEdorHeadInfo(mLPEdorAppSchema,mTextTag); ; //申请人的姓名

		mTextTag.add("AppName", "    兹经" + mLPEdorAppSchema.getEdorAppName()
				+ "申请， 并经本公司审核同意将");

		LPPersonDB tLPPersonDB = new LPPersonDB();
		LPPersonSchema tLPPersonSchema = new LPPersonSchema();
		tLPPersonDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPersonDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPPersonDB.setCustomerNo(mLPEdorItemSchema.getInsuredNo());
		if (!tLPPersonDB.getInfo()) {
			mErrors.addOneError("打印生成数据时，取客户新信息失败!");
			return false;
		}
		tLPPersonSchema = tLPPersonDB.getSchema();

		LDPersonDB tLDPersonDB = new LDPersonDB();
		LDPersonSchema tLDPersonSchema = new LDPersonSchema();
		tLDPersonDB.setCustomerNo(mLPEdorItemSchema.getInsuredNo());
		if (!tLDPersonDB.getInfo()) {
			mErrors.addOneError("打印生成数据时，取客户原信息失败!");
			return false;
		}
		tLDPersonSchema = tLDPersonDB.getSchema();
		mTextTag.add("BBLPInsured.CustomerNo", tLDPersonSchema.getCustomerNo());
		mTextTag.add("BBLPInsured.Name", tLDPersonSchema.getName());

		if (!StrTool.compareString(tLPPersonSchema.getName(), tLDPersonSchema
				.getName())
		/* && !StrTool.cTrim(tLPPersonSchema.getName()).equals("") */) {
			mTextTag.add("BBLPInsured.NameNew", tLPPersonSchema.getName());
			mTextTag.add("BBLPInsured.Name", tLDPersonSchema.getName());
			xmlexport.addDisplayControl("displayCMName");
		}

		if (!StrTool.compareString(tLPPersonSchema.getSex(), tLDPersonSchema
				.getSex())
		/* && !StrTool.cTrim(tLPPersonSchema.getSex()).equals("") */) {
			mTextTag.add("BBLPInsured.SexNew", BqNameFun.getCodeName(
					tLPPersonSchema.getSex(), "Sex"));
			mTextTag.add("BBLPInsured.Sex", BqNameFun.getCodeName(
					tLDPersonSchema.getSex(), "Sex"));
			xmlexport.addDisplayControl("displayCMSex");
		}

		if (!StrTool.compareString(tLPPersonSchema.getBirthday(),
				tLDPersonSchema.getBirthday())
		/* && !StrTool.cTrim(tLPPersonSchema.getBirthday()).equals("") */) {
			mTextTag.add("BBLPInsured.BirthdayNew", tLPPersonSchema
					.getBirthday());
			mTextTag.add("BBLPInsured.Birthday", tLDPersonSchema.getBirthday());
			xmlexport.addDisplayControl("displayCMBirthday");
		}

		if (!StrTool.compareString(tLPPersonSchema.getIDNo(), tLDPersonSchema
				.getIDNo())
		/* && !StrTool.cTrim(tLPPersonSchema.getIDNo()).equals("") */) {
			mTextTag.add("BBLPInsured.IDNoNew", tLPPersonSchema.getIDNo());
			mTextTag.add("BBLPInsured.IDNo", tLDPersonSchema.getIDNo());
			xmlexport.addDisplayControl("displayCMIDNo");
		}
		if (!StrTool.compareString(tLPPersonSchema.getIDType(), tLDPersonSchema
				.getIDType())
		/* && !StrTool.cTrim(tLPPersonSchema.getIDType()).equals("") */) {
			mTextTag.add("BBLPInsured.IDTypeNew", BqNameFun.getCodeName(
					tLPPersonSchema.getIDType(), "IDType"));
			mTextTag.add("BBLPInsured.IDType", BqNameFun.getCodeName(
					tLDPersonSchema.getIDType(), "IDType"));
			xmlexport.addDisplayControl("displayCMIDType");
		}

		// BqNameFun.AddEdorValiDatePart(mLPEdorItemSchema,xmlexport);
		BqNameFun.AddEdorValiDatePart(mLPEdorItemSchema, mLPEdorAppSchema,
				xmlexport);

		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean prepareData() {
		if (mTextTag.size() < 1) {
			mErrors.addOneError("生成数据失败!");
			return false;
		}
		xmlexport.addTextTag(mTextTag);

		mResult.add(xmlexport);
		return true;
	}

	public VData getResult() {
		return mResult;
	}
}
