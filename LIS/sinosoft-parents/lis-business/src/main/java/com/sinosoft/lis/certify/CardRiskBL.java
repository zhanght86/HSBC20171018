package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LMCardRiskDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LMCardRiskSchema;
import com.sinosoft.lis.schema.LMCertifyDesSchema;
import com.sinosoft.lis.vschema.LMCardRiskSet;
import com.sinosoft.lis.vschema.LMCertifyDesSet;
import com.sinosoft.lis.vschema.LMCertifyDesTraceSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 定额单险种定义
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author HST
 * @version 1.0
 */
public class CardRiskBL {
private static Logger logger = Logger.getLogger(CardRiskBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private MMap map = new MMap();

	private VData mResult = new VData();

	private GlobalInput mGlobalInput = new GlobalInput();

	private LMCardRiskSet mLMCardRiskSet = new LMCardRiskSet();

	private String mOperateType = "";

	public CardRiskBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperateType = cOperate;

		if (!getInputData())
			return false;

		// 数据操作业务处理
		if (mOperateType.equals("INSERT")) {
			if (!dealData())
				return false;
		}

		if (mOperateType.equals("UPDATE")) {
			if (!dealData())
				return false;
		}

		if (mOperateType.equals("DELETE")) {
			if (!dealData())
				return false;
		}

		if (!prepareOutputData()) {
			return false;
		}

		// 数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mResult, "")) {
			buildError("submitData", "数据提交失败!");
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			return false;
		}
		return true;
	}

	/**
	 * 接收传递的数据
	 */
	private boolean getInputData() {
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);
		mLMCardRiskSet = ((LMCardRiskSet) mInputData.getObjectByObjectName("LMCardRiskSet", 0));
		return true;
	}

	/**
	 * 校验传入的暂交费收据号是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	// 校验新增和修改时的数据，单证的号码不能长于20
	private boolean dealData() {
		if (mOperateType.equals("INSERT")) {
			for (int i = 1; i <= mLMCardRiskSet.size(); i++) {
				LMCardRiskSchema tLMCardRiskSchema = mLMCardRiskSet.get(i);
				String tCertifyCode = tLMCardRiskSchema.getCertifyCode();
				String tRiskCode = tLMCardRiskSchema.getRiskCode();
				String tPrem = String.valueOf(tLMCardRiskSchema.getPrem());
				String tPremProp = tLMCardRiskSchema.getPremProp();

				if (tCertifyCode == null || tCertifyCode.equals("")) {
					CError tError = new CError();
					tError.moduleName = "CertifyDescBL";
					tError.functionName = "checkData";
					tError.errorMessage = "在第" + i + "条记录中的单证编码不能是空！";
					this.mErrors.addOneError(tError);
					return false;
				}
				if (tRiskCode == null || tRiskCode.equals("")) {
					CError tError = new CError();
					tError.moduleName = "CertifyDescBL";
					tError.functionName = "checkData";
					tError.errorMessage = "在第" + i + "条记录中的险种编码不能是空！";
					this.mErrors.addOneError(tError);
					return false;
				}
				if (tPrem == null || tPrem.equals("")) {
					CError tError = new CError();
					tError.moduleName = "CertifyDescBL";
					tError.functionName = "checkData";
					tError.errorMessage = "在第" + i + "条记录中的保费不能为空！";
					this.mErrors.addOneError(tError);
					return false;
				}
				if (tPremProp == null || tPremProp.equals("")) {
					CError tError = new CError();
					tError.moduleName = "CertifyDescBL";
					tError.functionName = "checkData";
					tError.errorMessage = "在第" + i + "条记录中的保费特性不能是空！";
					this.mErrors.addOneError(tError);
					return false;
				}

				if("1".equals(tLMCardRiskSchema.getPortfolioFlag()))
				{
					//校验组合产品是否存在
				 String zSql="select distinct contplancode from ldplan where contplancode='"+"?riskcode?"+"' and portfolioflag='1' ";
				 SQLwithBindVariables sqlbv = new SQLwithBindVariables();
				 sqlbv.sql(zSql);
				 sqlbv.put("riskcode", tRiskCode);
				 SSRS zSSRS = new ExeSQL().execSQL(sqlbv);
				 if(zSSRS==null || zSSRS.MaxRow<=0)
				 {
					 CError.buildErr(this, "相应的产品代码不存在，请核实");
					 return false;
				 }
				 //校验相应的单证信息是否存在
				 String cSql ="select distinct certifycode from lmcertifydes where certifycode='"+"?certifycode?"+"' ";
				 SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				 sqlbv1.sql(cSql);
				 sqlbv1.put("certifycode", tCertifyCode);
				 SSRS cSSRS = new ExeSQL().execSQL(sqlbv1);
				 if(cSSRS==null || cSSRS.MaxRow<=0)
				 {
					 CError.buildErr(this, "相应的单证编码不存在，请核实");
					 return false;
				 }
					//查询组合产品的保费
				 String pSql = "select (case when sum(calfactorvalue) is null then 0 else sum(calfactorvalue) end) from ldplandutyparam where contplancode='"+"?riskcode?"+"' and calfactor='Prem' and portfolioflag='1'";
				 SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
				 sqlbv2.sql(pSql);
				 sqlbv2.put("riskcode", tRiskCode);
				 SSRS ssrs = new ExeSQL().execSQL(sqlbv2);
				 double planprem;
			     if(ssrs == null || ssrs.getMaxRow() <= 0)
			    	 planprem=0.00;
			      else
			    	  planprem= Double.parseDouble(ssrs.GetText(1,1));
			     
				 double cardprem = Double.parseDouble(tPrem);
				 if(Math.abs((cardprem - planprem)) >= 0.001 || Math.abs((planprem-cardprem)) >= 0.001 )
				 {
					 CError.buildErr(this, "保费请与产品组合的总保费保持一致");
					 return false;
				 }

				}
				
				LMCardRiskDB tLMCardRiskDB = new LMCardRiskDB();
				tLMCardRiskDB.setCertifyCode(tCertifyCode);
				tLMCardRiskDB.setRiskCode(tRiskCode);
				if (tLMCardRiskDB.getInfo()) {
					this.mErrors.copyAllErrors(tLMCardRiskDB.mErrors);
					CError tError = new CError();
					tError.moduleName = "CertifyDescBL";
					tError.functionName = "checkData";
					tError.errorMessage = "单证" + tCertifyCode + "已经定义过险种信息，不能重复定义！";
					this.mErrors.addOneError(tError);
					return false;
				}
			}
			map.put(mLMCardRiskSet, "INSERT");
		}

		if (mOperateType.equals("UPDATE")) {
		}
		if (mOperateType.equals("DELETE")) {
		}
		return true;
	}

	private boolean prepareOutputData() {
		try {
			mResult.clear();
			mResult.add(map);
		} catch (Exception ex) {
			buildError("prepareData", "在准备往后层处理所需要的数据时出错。");
			return false;
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "CardPlanBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
		String tOperateType = "QUERY";
		String tCertifyCode = "04";
		String tCertifyCode_1 = "04";
		String tCertifyClass = "Y";
		LMCertifyDesSchema yLMCertifyDesSchema = new LMCertifyDesSchema();
		LMCertifyDesSet yLMCertifyDesSet = new LMCertifyDesSet();
		LMCardRiskSet yLMCardRiskSet = new LMCardRiskSet();

		LMCardRiskSchema yLMCardRiskSchema = new LMCardRiskSchema();
		yLMCardRiskSchema.setCertifyCode("1");
		yLMCardRiskSchema.setRiskCode("1");
		yLMCardRiskSchema.setPrem("1");
		yLMCardRiskSchema.setPremProp("1");
		yLMCardRiskSet.add(yLMCardRiskSchema);

		yLMCertifyDesSchema.setCertifyCode(tCertifyCode);
		yLMCertifyDesSchema.setRiskVersion(tCertifyCode_1);
		yLMCertifyDesSet.add(yLMCertifyDesSchema);

		VData tVData = new VData();

		tVData.addElement(tOperateType);
		tVData.add("Y");
		tVData.add(yLMCertifyDesSet);

		CertifyDescUI tCertifyDescUI = new CertifyDescUI();
		tCertifyDescUI.submitData(tVData, "QUERY");
	}
}
