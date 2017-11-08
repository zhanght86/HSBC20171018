package com.sinosoft.lis.bq;

/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : caojx 
 * @version  : 1.00
 * @date     : 2008-9-4
 * @direction: 万能险追加保费批单打印保存
 ******************************************************************************/

import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LPAppntDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPInsuredDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LPAppntSchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPInsuredSchema;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;


// ******************************************************************************

public class PEdorWPPrintBL implements EdorPrint
{
    // public PEdorAMPrintExBL() {}

    // ==========================================================================

    // 错误处理类, 使用 CError.buildErr 名称、作用域不可更改
    public CErrors mErrors = new CErrors();
    // 输入数据
    // 输出数据
    @SuppressWarnings("unused")
	private String mOperate;
	private VData mInputData = new VData();
	@SuppressWarnings("unused")
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private LPEdorItemSet mLPEdorItemSet = new LPEdorItemSet();
	private LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
	private LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
	private LPAppntSchema tLPAppntSchema = new LPAppntSchema();
	private LCAppntSchema tLCAppntSchema = new LCAppntSchema();
	private LPEdorAppSchema mLPEdorAppSchema =new LPEdorAppSchema();
	private XmlExportNew xmlexport = new XmlExportNew();
	private TextTag mTextTag = new TextTag();
	private String tAppntIsInsuredFlag = "";
	private VData mResult = new VData();

    // ==========================================================================

    /**
     * 外部调用本类的业务处理接口
     * 
     * @param VData
     * @param String
     * @return boolean
     */
    public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 数据传输
		if (!getInputData()) {
			CError.buildErr(this, "BB数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			CError.buildErr(this, "BB数据提取失败!");
			return false;
		}

		// 准备数据
		if (!prepareData()) {
			CError.buildErr(this, "BB数据生成失败!");
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
		return true;
	}

	private boolean getAllSchema() {
		// 保单信息

		
		for(int k=1;k<=mLPEdorItemSet.size();k++)
		{
			tAppntIsInsuredFlag=mLPEdorItemSet.get(k).getStandbyFlag1();

			// 客户新信息
			if("1".equals(tAppntIsInsuredFlag) || "2".equals(tAppntIsInsuredFlag) )
			{	
			xmlexport.addDisplayControl("displayBBHome"+k);		
			LPInsuredDB tLPInsuredDB = new LPInsuredDB();
			tLPInsuredDB.setEdorNo(mLPEdorItemSet.get(k).getEdorNo());
			tLPInsuredDB.setEdorType(mLPEdorItemSet.get(k).getEdorType());
			tLPInsuredDB.setContNo(mLPEdorItemSet.get(k).getContNo());
			tLPInsuredDB.setInsuredNo(mLPEdorItemSet.get(k).getInsuredNo());
			if (!tLPInsuredDB.getInfo()) {
				CError.buildErr(this, "打印生成数据时，取受保人新信息失败!");
				return false;
			}
			tLPInsuredSchema = tLPInsuredDB.getSchema();
			// 客户旧信息
			LCInsuredDB tLCInsuredDB = new LCInsuredDB();
			tLCInsuredDB.setContNo(mLPEdorItemSet.get(k).getContNo());
			tLCInsuredDB.setInsuredNo(mLPEdorItemSet.get(k).getInsuredNo());
			if (!tLCInsuredDB.getInfo()) {
				CError.buildErr(this, "打印生成数据时，取受保人旧信息失败!");
				return false;
			}
			tLCInsuredSchema = tLCInsuredDB.getSchema();

			mTextTag.add("BBLPInsured.CustomerNo"+k, tLPInsuredSchema.getInsuredNo());
			if (!StrTool.compareString(tLPInsuredSchema.getName(),
					tLCInsuredSchema.getName())
					/*&& !StrTool.cTrim(tLPInsuredSchema.getName()).equals("")*/) {
				mTextTag.add("BBLPInsured.NativeNameNew"+k, tLPInsuredSchema.getName());
				mTextTag.add("BBLPInsured.NativeName"+k, tLCInsuredSchema.getName());
				xmlexport.addDisplayControl("displayBBName"+k);
			}
			
			if (!StrTool.compareString(tLPInsuredSchema.getNativePlace(),
					tLCInsuredSchema.getNativePlace())
					/*&& !StrTool.cTrim(tLPInsuredSchema.getNativePlace()).equals("")*/) {
				mTextTag.add("BBLPInsured.NativePlaceNew"+k, BqNameFun.getCodeName(tLPInsuredSchema.getNativePlace(),"NativePlace"));
				mTextTag.add("BBLPInsured.NativePlace"+k, BqNameFun.getCodeName(tLCInsuredSchema.getNativePlace(),"NativePlace"));
				xmlexport.addDisplayControl("displayBBNativePlace"+k);
			}
			
			if (!StrTool.compareString(tLPInsuredSchema.getMarriage(),
					tLCInsuredSchema.getMarriage())
					/*&& !StrTool.cTrim(tLPInsuredSchema.getMarriage()).equals("")*/) {
				mTextTag.add("BBLPInsured.MarriageNew"+k, BqNameFun.getCodeName(tLPInsuredSchema.getMarriage(),"Marriage"));
				mTextTag.add("BBLPInsured.Marriage"+k, BqNameFun.getCodeName(tLCInsuredSchema.getMarriage(),"Marriage"));
				xmlexport.addDisplayControl("displayBBMarriage"+k);
			}
			if (!StrTool.compareString(tLPInsuredSchema.getRgtAddress(),
					tLCInsuredSchema.getRgtAddress())
					/*&& !StrTool.cTrim(tLPInsuredSchema.getRgtAddress()).equals("")*/) {
				mTextTag.add("BBLPInsured.RgtAddressNew"+k, tLPInsuredSchema.getRgtAddress());
				mTextTag.add("BBLPInsured.RgtAddress"+k, tLCInsuredSchema.getRgtAddress());
				xmlexport.addDisplayControl("displayBBRgtAddress"+k);
			}
			if (!StrTool.compareString(tLPInsuredSchema.getSocialInsuFlag(),
					tLCInsuredSchema.getSocialInsuFlag())
					/*&& !StrTool.cTrim(tLPInsuredSchema.getSocialInsuFlag()).equals("")*/) {
				String tPFlag="";
				if("0".equals(tLPInsuredSchema.getSocialInsuFlag()) || "".equals(tLPInsuredSchema.getSocialInsuFlag()))
				{
					tPFlag="无";
				}else
				{
					tPFlag="有";					
				}
				String tCFlag="";
				if("0".equals(tLCInsuredSchema.getSocialInsuFlag()) || "".equals(tLCInsuredSchema.getSocialInsuFlag()))
				{
					tCFlag="无";
				}else
				{
					tCFlag="有";					
				}				
				mTextTag.add("BBLPInsured.SocialInsuFlagNew"+k, tPFlag);
				mTextTag.add("BBLPInsured.SocialInsuFlag"+k, tCFlag);
				xmlexport.addDisplayControl("displayBBSocialInsuFlag"+k);
			}
			}

			// 客户新信息
			if("0".equals(tAppntIsInsuredFlag) || "2".equals(tAppntIsInsuredFlag) )
			{				
			xmlexport.addDisplayControl("displayBBHome"+k);	
			LPAppntDB tLPAppntDB = new LPAppntDB();
			tLPAppntDB.setEdorNo(mLPEdorItemSet.get(k).getEdorNo());
			tLPAppntDB.setEdorType(mLPEdorItemSet.get(k).getEdorType());
			tLPAppntDB.setContNo(mLPEdorItemSet.get(k).getContNo());
			tLPAppntDB.setAppntNo(mLPEdorItemSet.get(k).getInsuredNo());
			if (!tLPAppntDB.getInfo()) {
				CError.buildErr(this, "打印生成数据时，取受保人新信息失败!");
				return false;
			}
			tLPAppntSchema = tLPAppntDB.getSchema();
			// 客户旧信息
			LCAppntDB tLCAppntDB = new LCAppntDB();
			tLCAppntDB.setContNo(mLPEdorItemSet.get(k).getContNo());
			tLCAppntDB.setAppntNo(mLPEdorItemSet.get(k).getInsuredNo());
			if (!tLCAppntDB.getInfo()) {
				CError.buildErr(this, "打印生成数据时，取受保人旧信息失败!");
				return false;
			}
			tLCAppntSchema = tLCAppntDB.getSchema();
			
			mTextTag.add("BBLPInsured.CustomerNo"+k, tLPAppntSchema.getAppntNo());
			
			if (!StrTool.compareString(tLPAppntSchema.getAppntName(),
					tLCAppntSchema.getAppntName())
					/*&& !StrTool.cTrim(tLPAppntSchema.getAppntName()).equals("")*/) {
				mTextTag.add("BBLPInsured.NativeNameNew"+k, tLPAppntSchema.getAppntName());
				mTextTag.add("BBLPInsured.NativeName"+k, tLCAppntSchema.getAppntName());
				xmlexport.addDisplayControl("displayBBName"+k);
			}
			
			if (!StrTool.compareString(tLPAppntSchema.getNativePlace(),
					tLCAppntSchema.getNativePlace())
					/*&& !StrTool.cTrim(tLPAppntSchema.getNativePlace()).equals("")*/) {
				mTextTag.add("BBLPInsured.NativePlaceNew"+k, BqNameFun.getCodeName(tLPAppntSchema.getNativePlace(),"NativePlace"));
				mTextTag.add("BBLPInsured.NativePlace"+k, BqNameFun.getCodeName(tLCAppntSchema.getNativePlace(),"NativePlace"));
				xmlexport.addDisplayControl("displayBBNativePlace"+k);
			}
			
			if (!StrTool.compareString(tLPAppntSchema.getMarriage(),
					tLCAppntSchema.getMarriage())
					/*&& !StrTool.cTrim(tLPAppntSchema.getMarriage()).equals("")*/) {
				mTextTag.add("BBLPInsured.MarriageNew"+k, BqNameFun.getCodeName(tLPAppntSchema.getMarriage(),"Marriage"));
				mTextTag.add("BBLPInsured.Marriage"+k, BqNameFun.getCodeName(tLCAppntSchema.getMarriage(),"Marriage"));
				xmlexport.addDisplayControl("displayBBMarriage"+k);
			}
			if (!StrTool.compareString(tLPAppntSchema.getRgtAddress(),
					tLCAppntSchema.getRgtAddress())
					/*&& !StrTool.cTrim(tLPAppntSchema.getRgtAddress()).equals("")*/) {
				mTextTag.add("BBLPInsured.RgtAddressNew"+k, tLPAppntSchema.getRgtAddress());
				mTextTag.add("BBLPInsured.RgtAddress"+k, tLCAppntSchema.getRgtAddress());
				xmlexport.addDisplayControl("displayBBRgtAddress"+k);
			}	
			}
		}
		

		return true;
	}

	private boolean dealData() {
		//xmlexport.addDisplayControl("displayHead1");
BqNameFun.AddEdorHead(mLPEdorItemSchema, mLPEdorAppSchema, xmlexport);
		xmlexport.addDisplayControl("displayBB");

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

    // ==========================================================================

} // class PEdorAMPrintExBL end
