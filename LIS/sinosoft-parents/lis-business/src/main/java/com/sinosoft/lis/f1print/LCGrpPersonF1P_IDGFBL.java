package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.tb.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.vschema.*;

import com.sinosoft.utility.*;

import org.jdom.*;

import org.jdom.input.*;

import org.jdom.output.*;

import java.io.*;

import java.sql.*;

import java.text.*;

import java.util.*;
import com.sinosoft.service.BusinessService;
/**
 * <p>Title: 团单个人凭证打印</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @version 1.0
 */

public class LCGrpPersonF1P_IDGFBL implements BusinessService{
private static Logger logger = Logger.getLogger(LCGrpPersonF1P_IDGFBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	private String mOperate = "";

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();

	private LCContSet mLCContSet = new LCContSet();

	private LDComSchema mLDComSchema = new LDComSchema(); //保单管理机构

	private LDComSchema mLDComSchema2 = new LDComSchema(); //二级管理机构

	private LMRiskAppSchema mLMRiskAppSchema = new LMRiskAppSchema();

	private XMLDatasets mXMLDatasets = null;
	
	private String tRiskCode="";
	private String tCurMakeDate = PubFun.getCurrentDate();

	private String tCurMakeTime = PubFun.getCurrentTime();

	public LCGrpPersonF1P_IDGFBL() {
	}

	public VData getResult() {
		return this.mResult;
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		try {
			if (!cOperate.equals("PRINT||ALLGRPPERSON")
					&& !cOperate.equals("PRINT||GRPPERSON")) {
				CError.buildErr(this, "不支持的操作字符串!");
				return false;
			}

			mOperate = cOperate;

			// 得到外部传入的数据，将数据备份到本类中
			if (!getInputData(cInputData)) {
				return false;
			}

			if (mOperate.equals("PRINT||ALLGRPPERSON")) {
				// 准备所有要打印的数据
				mXMLDatasets = new XMLDatasets();
				mXMLDatasets.createDocument();
				getPrintDataForGrp();
			}

			if (mOperate.equals("PRINT||GRPPERSON")) {
				mXMLDatasets = new XMLDatasets();
				mXMLDatasets.createDocument();
				getPrintDataForPerson();
			}

			//mXMLDatasets.output("F:\\Work_J\\test\\a.txt");

			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			CError.buildErr(this, "准备团单个人凭证打印数据异常!");

			return false;
		}
	}

	/**
	 * 从输入数据中得到所有对象
	 * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		if (mOperate.equals("PRINT||ALLGRPPERSON")) {
			//打印团单下所有个人凭证
			mGlobalInput.setSchema((GlobalInput) cInputData
					.getObjectByObjectName("GlobalInput", 0));
			mLCGrpContSchema = (LCGrpContSchema) cInputData
					.getObjectByObjectName("LCGrpContSchema", 0);
		}

		if (mOperate.equals("PRINT||GRPPERSON")) {
			//打印团单下选中个人凭证
			mGlobalInput.setSchema((GlobalInput) cInputData
					.getObjectByObjectName("GlobalInput", 0));
			mLCContSet = (LCContSet) cInputData.getObjectByObjectName("LCContSet",
					0);
		}

		return true;
	}

	private boolean  getPrintDataForGrp()  {
		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(mLCGrpContSchema.getGrpContNo());
		if (!tLCGrpContDB.getInfo()) {
			CError.buildErr(this, "获取团单信息失败!");
			return false;
		}
		mLCGrpContSchema = tLCGrpContDB.getSchema();

		if (!prepareData()) {
			CError.buildErr(this, "准备数据失败!");
			return false;
		}

		String strSQL = "select * from lcCont where grpcontno='"
				+ mLCGrpContSchema.getGrpContNo()
				+ "' ";
		LCContDB tLCContDB = new LCContDB();
		mLCContSet = tLCContDB.executeQuery(strSQL);
		if (mLCContSet == null || mLCContSet.size() <= 0) {
			CError.buildErr(this, "获取团单下个人保单信息失败!");
			return false;
		}

		for (int i = 1; i <= mLCContSet.size(); i++) {
			if(!getPrintData(mLCContSet.get(i)))
			{
				insertErrLog(mErrors.getFirstError(), mLCContSet.get(i));
				return false;
			}
		}

		mResult.clear();
		logger.debug("add inputstream to mResult");
		mResult.add(mXMLDatasets.getInputStream());
		return true;
	}

	private boolean getPrintDataForPerson() throws Exception {
		for (int i = 1; i <= mLCContSet.size(); i++) {
			mErrors.clearErrors();
			LCContDB tLCContDB = new LCContDB();
			tLCContDB.setContNo(mLCContSet.get(i).getContNo());
			if (!tLCContDB.getInfo()) {
				CError.buildErr(this, "获取团单下个人保单信息失败!");
				return false;
			}
			if (i == 1) {
				LCGrpContDB tLCGrpContDB = new LCGrpContDB();
				tLCGrpContDB.setGrpContNo(tLCContDB.getGrpContNo());
				if (!tLCGrpContDB.getInfo()) {
					CError.buildErr(this, "获取团单信息失败!");
					return false;
				}
				mLCGrpContSchema = tLCGrpContDB.getSchema();

				if (!prepareData()) {
					insertErrLog(mErrors.getFirstError(), mLCContSet.get(i));
					return false;
				}
			}
			if(!getPrintData(tLCContDB.getSchema()))
			{				
				insertErrLog(mErrors.getFirstError(), mLCContSet.get(i));
				return false;
			}

		}

		mResult.clear();
		logger.debug("add inputstream to mResult");
		mResult.add(mXMLDatasets.getInputStream());
		return true;
	}

	//准备共用信息
	private boolean prepareData() {
		
		ExeSQL tExeSQL = new ExeSQL();
		String tMainRiskSQL="select riskcode from lcgrppol where grpcontno='"+mLCGrpContSchema.getGrpContNo()+"' and riskcode in "
		                     +" (select riskcode from lmriskapp where SubRiskFlag='M') ";
		//获取主险
		tRiskCode=tExeSQL.getOneValue(tMainRiskSQL);
		if("".equals(tRiskCode))
		{
			CError.buildErr(this, "获取主险编码信息失败!");
			return false;	
		}
		LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
		tLMRiskAppDB.setRiskCode(tRiskCode);
		if (!tLMRiskAppDB.getInfo()) {
			CError.buildErr(this, "获取险种信息失败!");
			return false;
		}
		mLMRiskAppSchema = tLMRiskAppDB.getSchema();

		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(mLCGrpContSchema.getManageCom());
		if (!tLDComDB.getInfo()) {
			CError.buildErr(this, "获取团单管理机构失败!");
			return false;
		}
		mLDComSchema = tLDComDB.getSchema();

		tLDComDB = new LDComDB();
		tLDComDB.setComCode(mLCGrpContSchema.getManageCom().substring(0, 4));
		if (!tLDComDB.getInfo()) {
			CError.buildErr(this, "获取团单二级管理机构失败!");
			return false;
		}
		mLDComSchema2 = tLDComDB.getSchema();

		return true;
	}

	//准备团单下所有个人凭证信息
	private boolean getPrintData(LCContSchema aLCContSchema) {
		XMLDataset xmlDataset = mXMLDatasets.createDataset();

		xmlDataset.addDataObject(new XMLDataTag("BranchCode", mLCGrpContSchema
				.getManageCom().substring(0, 4))); //机构代码
		xmlDataset.addDataObject(new XMLDataTag("ComName", mLDComSchema2
				.getName())); //二级机构名称
		xmlDataset.addDataObject(new XMLDataTag("PolicyType", "grpperson")); //打印类型

		xmlDataset.addDataObject(new XMLDataTag("PrtNo", aLCContSchema
				.getPrtNo()));
		//
		xmlDataset.addDataObject(new XMLDataTag("RiskCode", mLMRiskAppSchema
				.getRiskCode()));
		xmlDataset.addDataObject(new XMLDataTag("RiskName", mLMRiskAppSchema
				.getRiskName()));
		xmlDataset.addDataObject(new XMLDataTag("GrpPolNo", aLCContSchema
				.getGrpContNo()));
		xmlDataset.addDataObject(new XMLDataTag("GrpName", mLCGrpContSchema
				.getGrpName()));
		
		LCGrpAddressSchema tLCGrpAddressSchema = new LCGrpAddressSchema();
		LCGrpAddressDB tLCGrpAddressDB = new LCGrpAddressDB();
		tLCGrpAddressDB.setAddressNo(mLCGrpContSchema.getAddressNo());
		tLCGrpAddressDB.setCustomerNo(mLCGrpContSchema.getAppntNo());
		tLCGrpAddressSchema=tLCGrpAddressDB.query().get(1);
		
		xmlDataset.addDataObject(new XMLDataTag("Phone", tLCGrpAddressSchema
				.getPhone1()));
		xmlDataset.addDataObject(new XMLDataTag("Address", tLCGrpAddressSchema
				.getGrpAddress()));
		xmlDataset.addDataObject(new XMLDataTag("ZipCode", tLCGrpAddressSchema
				.getGrpZipCode()));


		xmlDataset.addDataObject(new XMLDataTag("InsuredName", aLCContSchema
				.getInsuredName()));

		LCPolSchema tLCPolSchema = new LCPolSchema();
		LCPolDB rLCPolDB = new LCPolDB();
		rLCPolDB.setContNo(aLCContSchema.getContNo());
		rLCPolDB.setRiskCode(tRiskCode);
        //获取主险保单数据
		tLCPolSchema=rLCPolDB.query().get(1);
		if(tLCPolSchema==null )
		{
			CError.buildErr(this, "获取主险保单数据失败!");
			return false;			
		}
		
		xmlDataset.addDataObject(new XMLDataTag("PolNo", tLCPolSchema
				.getPolNo()));
		//获取被保人信息
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		tLCInsuredDB.setContNo(aLCContSchema.getContNo());
		tLCInsuredDB.setInsuredNo(aLCContSchema.getInsuredNo());
		if (!tLCInsuredDB.getInfo()) {
			CError.buildErr(this, "获取团单下个人保单被保人信息失败!");
			return false;
		}
		
		xmlDataset.addDataObject(new XMLDataTag("InsuredIDNo", tLCInsuredDB
				.getIDNo()));

		// 获取身故受益人信息
		Element tableBnf = new Element("BnfList");
		xmlDataset.getRealElement().addContent(tableBnf);

		LCBnfDB tLCBnfDB = new LCBnfDB();
		tLCBnfDB.setContNo(aLCContSchema.getContNo());
		tLCBnfDB.setBnfType("1");
		LCBnfSet tLCBnfSet = tLCBnfDB.query();

		for (int i = 1; i <= tLCBnfSet.size(); i++) {
			tableBnf.addContent(new Element("Name").addContent(tLCBnfSet.get(i)
					.getName()));
		}

		xmlDataset.addDataObject(new XMLDataTag("Amnt", Double
				.toString(tLCPolSchema.getAmnt())));
		xmlDataset.addDataObject(new XMLDataTag("Prem", Double
				.toString(aLCContSchema.getPrem())));
		xmlDataset.addDataObject(new XMLDataTag("PayIntv", aLCContSchema
				.getPayIntv()));

		//生存金领取类型
		String strGetDutyName = new ExeSQL()
				.getOneValue("select b.getdutyname "
						+ "from lcget a,lmdutygetalive b where a.getdutycode=b.getdutycode "
						+ "and a.getdutykind=b.getdutykind and Contno='"
						+ aLCContSchema.getContNo() + "' and livegettype='0'");
		xmlDataset.addDataObject(new XMLDataTag("GetDutyName", strGetDutyName));

		//生存金领取方式
		xmlDataset.addDataObject(new XMLDataTag("LiveGetMode", tLCPolSchema
				.getLiveGetMode()));

		//生效日期和责任止期
		xmlDataset.addDataObject(new XMLDataTag("CValidate", aLCContSchema
				.getCValiDate()));
		xmlDataset.addDataObject(new XMLDataTag("EndDate", PubFun.calDate(
				tLCPolSchema.getEndDate(), -1, "D", "")));

		//备注
		xmlDataset.addDataObject(new XMLDataTag("Remark", aLCContSchema
				.getRemark()));

		//管理机构及电话
		xmlDataset.addDataObject(new XMLDataTag("ManageCom", mLDComSchema
				.getComCode()));
		xmlDataset.addDataObject(new XMLDataTag("MgePhone", mLDComSchema
				.getPhone()));

		//附加险列表
		Element tableRisk = new Element("SubRiskList");
		xmlDataset.getRealElement().addContent(tableRisk);
		
		String tSQL="select * from lcpol where contno='"
			+ aLCContSchema.getContNo()
			+ "' and riskcode in (select riskcode from lmriskapp where SubRiskFlag='S' ) ";
		logger.debug("执行的SQL"+tSQL);
		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSet tLCPolSet = tLCPolDB
				.executeQuery(tSQL);
		
		for (int i = 1; i <= tLCPolSet.size(); i++) {
			Element row = new Element("ROW");
			tableRisk.addContent(row);
			row.addContent(new Element("PolNo").addContent(tLCPolSet.get(i)
					.getPolNo()));
			row.addContent(new Element("RiskCode").addContent(tLCPolSet.get(i)
					.getRiskCode()));
			row.addContent(new Element("RiskName").addContent(BqNameFun.getRiskShortName(tLCPolSet.get(i)
			.getRiskCode())));
			row.addContent(new Element("Amnt").addContent(tLCPolSet.get(i)
					.getAmnt()
					+ ""));
			row.addContent(new Element("Prem").addContent(tLCPolSet.get(i)
					.getPrem()
					+ ""));
			row.addContent(new Element("CValiDate").addContent(tLCPolSet.get(i)
					.getCValiDate()));
		}
		return true;
	}

	/**
	 * 纪录错误信息
	 * @param tSerialNo
	 * @param tPolNo
	 * @param errDescribe
	 * @return 
	 */
	public boolean insertErrLog(String errDescribe, LCContSchema mLCContSchema) {
		String tLimit = PubFun.getNoLimit(mLCContSchema.getManageCom());
		String tSerialNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);//产生流水号码
		LDSysErrLogDB tLDSysErrLogDB = new LDSysErrLogDB();
		tLDSysErrLogDB.setSerialNo(tSerialNo);
		tLDSysErrLogDB.setPolNo("00000000000000000000");
		tLDSysErrLogDB.setModule("GCB"); //团单承保
		tLDSysErrLogDB.setSubModule("GCBPP");//团单保单打印
		tLDSysErrLogDB.setGrpContNo(mLCContSchema.getGrpContNo());
		tLDSysErrLogDB.setContNo(mLCContSchema.getContNo());
		tLDSysErrLogDB.setManageCom(mLCContSchema.getManageCom());
		tLDSysErrLogDB.seterrMsg(errDescribe);
		tLDSysErrLogDB.setMakeDate(tCurMakeDate);
		tLDSysErrLogDB.setMakeTime(tCurMakeTime);
		if (tLDSysErrLogDB.insert() == false) {
			CError tError = new CError();
			tError.moduleName = "LCPolF1P_IDGFBL";
			tError.functionName = "insertErrLog";
			tError.errorMessage = "纪录错误日志时发生错误："
					+ tLDSysErrLogDB.mErrors.getFirstError()
					+ "；解决该问题后，打印此保单";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}
	// 测试
	public static void main(String[] args) {
		LCPolSchema tLCPolSchema = new LCPolSchema();
		tLCPolSchema.setPolNo("86110020070210000761");
		LCPolSet tLCPolSet = new LCPolSet();
		tLCPolSet.add(tLCPolSchema);

		VData tVData = new VData();
		tVData.add(tLCPolSet);
		tVData.add(new GlobalInput());

		LCGrpPersonF1P_IDGFBL tLCGrpPersonF1P_IDGFBL = new LCGrpPersonF1P_IDGFBL();
		tLCGrpPersonF1P_IDGFBL.submitData(tVData, "PRINT||GRPPERSON");
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}
}
