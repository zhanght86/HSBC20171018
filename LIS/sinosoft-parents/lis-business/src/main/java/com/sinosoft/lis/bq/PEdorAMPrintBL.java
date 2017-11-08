package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCAddressDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LPAddressDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LPAddressSchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title:客户基本信息变更批单数据生成
 * </p>
 * 
 * <p>
 * Description:生成保全项目客户联系方式基本信息变更的批单数据
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author pst
 * 
 * @version 1.0
 */

public class PEdorAMPrintBL implements EdorPrint {
@SuppressWarnings("unused")
private static Logger logger = Logger.getLogger(PEdorAMPrintBL.class);

	// 公共数据
	private VData mResult = new VData();
	//public CErrors mErrors = new CErrors();

	// 全局变量
	private String mOperate;
	private VData mInputData = new VData();
	@SuppressWarnings("unused")
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private LPEdorItemSet mLPEdorItemSet = new LPEdorItemSet();
	private LCContSchema tLCContSchema = new LCContSchema();
	private LPAddressSchema mLPAddressSchema = new LPAddressSchema();
	private LCAddressSchema mLCAddressSchema = new LCAddressSchema();	
	private LPEdorAppSchema mLPEdorAppSchema =new LPEdorAppSchema();
	private XmlExportNew xmlexport = new XmlExportNew();
	private TextTag mTextTag = new TextTag();
	
	/**客户身份标志，投保人还是被保人，0，投保人，1，被保人，2，两种兼之*/
	private String tAppntIsInsuredFlag = "";

	/**本客户原来的地址编号*/
	private String tAddressNo="";
	
	/**新地址编号*/
	private  String aAddressNo = "";

	public PEdorAMPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 数据传输
		if (!getInputData()) {
			CError.buildErr(this, "AM数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			CError.buildErr(this, "AM数据提取失败!");
			return false;
		}

		// 准备数据
		if (!prepareData()) {
			CError.buildErr(this, "AM数据生成失败!");
			return false;
		}
		return true;
	}

	private boolean getInputData() {
		mLPEdorItemSchema = (LPEdorItemSchema) mInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
	    mLPEdorAppSchema = (LPEdorAppSchema) mInputData.getObjectByObjectName (
	            "LPEdorAppSchema",0) ;
		xmlexport = (XmlExportNew) mInputData
				.getObjectByObjectName("XmlExportNew", 0);
		if (mLPEdorItemSchema == null || xmlexport == null) {
			return false;
		}
		return true;
	}

	private boolean checkData() {
		if (!mOperate.equals("PRINTAM")) {
			return false;
		}
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
		mLPEdorItemSet = tLPEdorItemDB.query();
		if (mLPEdorItemSet.size() < 1) {
			return false;
		}


		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			CError.buildErr(this, "打印生成数据时，取保单信息失败!");
			return false;
		}
		tLCContSchema = tLCContDB.getSchema();
		return true;
	}

	private boolean getAllSchema() {
		// 保单信息


		
		for(int k=1;k<=mLPEdorItemSet.size();k++)
		{
			tAppntIsInsuredFlag=mLPEdorItemSet.get(k).getStandbyFlag1();
			tAddressNo=mLPEdorItemSet.get(k).getStandbyFlag2();
			aAddressNo=mLPEdorItemSet.get(k).getStandbyFlag3();

			xmlexport.addDisplayControl("displayAMHome"+k);		
			LPAddressDB tLPAddressDB = new LPAddressDB();
			tLPAddressDB.setEdorNo(mLPEdorItemSet.get(k).getEdorNo());
			tLPAddressDB.setEdorType(mLPEdorItemSet.get(k).getEdorType());
			tLPAddressDB.setAddressNo(aAddressNo);
			tLPAddressDB.setCustomerNo(mLPEdorItemSet.get(k).getInsuredNo());
			
			mLPAddressSchema=tLPAddressDB.query().get(1);
			
			
         	// 客户旧信息
			LCAddressDB tLCAddressDB = new LCAddressDB();
			tLCAddressDB.setAddressNo(tAddressNo);
			tLCAddressDB.setCustomerNo(mLPEdorItemSet.get(k).getInsuredNo());
			
			mLCAddressSchema=tLCAddressDB.query().get(1);
			
			if("0".equals(tAppntIsInsuredFlag) || "2".equals(tAppntIsInsuredFlag) )
			{		
				mTextTag.add("AMLPInsured.NativeName"+k, tLCContSchema.getAppntName());
			}else
			{
				mTextTag.add("AMLPInsured.NativeName"+k, tLCContSchema.getInsuredName());	
			}

			
			mTextTag.add("AMLPInsured.CustomerNo"+k, mLPAddressSchema.getCustomerNo());
			
			
			if (!StrTool.compareString(mLPAddressSchema.getPostalAddress(),
					mLCAddressSchema.getPostalAddress())
					/*&& !StrTool.cTrim(mLPAddressSchema.getPostalAddress()).equals("")*/) {
				mTextTag.add("AMLPInsured.PostalAddress_New"+k, mLPAddressSchema.getPostalAddress());
				mTextTag.add("AMLPInsured.PostalAddress"+k, mLCAddressSchema.getPostalAddress());
				xmlexport.addDisplayControl("displayAMPostalAddress"+k);
			}

			if (!StrTool.compareString(mLPAddressSchema.getZipCode(),
					mLCAddressSchema.getZipCode())
					/*&& !StrTool.cTrim(mLPAddressSchema.getZipCode()).equals("")*/) {
				mTextTag.add("AMLPInsured.ZipCode_New"+k, mLPAddressSchema.getZipCode());
				mTextTag.add("AMLPInsured.ZipCode"+k, mLCAddressSchema.getZipCode());
				xmlexport.addDisplayControl("displayAMZipCode"+k);
			}

			if (!StrTool.compareString(mLPAddressSchema.getHomeAddress(),
					mLCAddressSchema.getHomeAddress())
					/*&& !StrTool.cTrim(mLPAddressSchema.getHomeAddress()).equals("")*/) {
				mTextTag.add("AMLPInsured.HomeAddress_New"+k, mLPAddressSchema.getHomeAddress());
				mTextTag.add("AMLPInsured.HomeAddress"+k, mLCAddressSchema.getHomeAddress());
				xmlexport.addDisplayControl("displayAMHomeAddress"+k);
			}

			if (!StrTool.compareString(mLPAddressSchema.getHomeZipCode(),
					mLCAddressSchema.getHomeZipCode())
					/*&& !StrTool.cTrim(mLPAddressSchema.getHomeZipCode()).equals("")*/) {
				mTextTag.add("AMLPInsured.HomeZipCode_New"+k, mLPAddressSchema.getHomeZipCode());
				mTextTag.add("AMLPInsured.HomeZipCode"+k, mLCAddressSchema.getHomeZipCode());
				xmlexport.addDisplayControl("displayAMHomeZipCode"+k);
			}
			
			if (!StrTool.compareString(mLPAddressSchema.getMobile(),
					mLCAddressSchema.getMobile())
					/*&& !StrTool.cTrim(mLPAddressSchema.getMobile()).equals("")*/) {
				mTextTag.add("AMLPInsured.Mobile_New"+k, mLPAddressSchema.getMobile());
				mTextTag.add("AMLPInsured.Mobile"+k, mLCAddressSchema.getMobile());
				xmlexport.addDisplayControl("displayAMMobile"+k);
			}
			if (!StrTool.compareString(mLPAddressSchema.getPhone(),
					mLCAddressSchema.getPhone())
					/*&& !StrTool.cTrim(mLPAddressSchema.getCompanyPhone()).equals("")*/) {
				mTextTag.add("AMLPInsured.GrpPhone_New"+k, mLPAddressSchema.getPhone());
				mTextTag.add("AMLPInsured.GrpPhone"+k, mLCAddressSchema.getPhone());
				xmlexport.addDisplayControl("displayAMGrpPhone"+k);
			}

			if (!StrTool.compareString(mLPAddressSchema.getEMail(),
					mLCAddressSchema.getEMail())
					/*&& !StrTool.cTrim(mLPAddressSchema.getEMail()).equals("")*/) {
				mTextTag.add("AMLPInsured.EMail_New"+k, mLPAddressSchema.getEMail());
				mTextTag.add("AMLPInsured.EMail"+k, mLCAddressSchema.getEMail());
				xmlexport.addDisplayControl("displayAMEMail"+k);
			}
			if (!StrTool.compareString(mLPAddressSchema.getGrpName(),
					mLCAddressSchema.getGrpName())
					/*&& !StrTool.cTrim(mLPAddressSchema.getGrpName()).equals("")*/) {
				mTextTag.add("AMLPInsured.GrpName_New"+k, mLPAddressSchema.getGrpName());
				mTextTag.add("AMLPInsured.GrpName"+k, mLCAddressSchema.getGrpName());
				xmlexport.addDisplayControl("displayAMGrpName"+k);
			}
			
		}
		

		return true;
	}

	private boolean dealData() {
		//xmlexport.addDisplayControl("displayHead1");
BqNameFun.AddEdorHead(mLPEdorItemSchema, mLPEdorAppSchema, xmlexport);
		xmlexport.addDisplayControl("displayAM");


       BqNameFun.AddEdorHeadInfo(mLPEdorAppSchema,mTextTag); ; //申请人的姓名
			
		

		if (!getAllSchema()) {
			return false;
		}
		mLPEdorItemSchema.setPolNo("000000");
	    //BqNameFun.AddEdorValiDatePart(mLPEdorItemSchema,xmlexport);
         BqNameFun.AddEdorValiDatePart(mLPEdorItemSchema, mLPEdorAppSchema, xmlexport);

		return true;
	}
  

	@SuppressWarnings("unchecked")
	private boolean prepareData() {
		if (mTextTag.size() < 1) {
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
