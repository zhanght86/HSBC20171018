

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.reinsure;

import java.util.Date;

import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LLCaseDB;
import com.sinosoft.lis.db.LLClaimPolicyDB;
import com.sinosoft.lis.db.LLRegisterDB;
import com.sinosoft.lis.db.LRClaimPolicyDB;
import com.sinosoft.lis.db.LRPolDB;
import com.sinosoft.lis.db.LRRiskDB;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.ReportPubFun;
import com.sinosoft.lis.schema.LLClaimPolicySchema;
import com.sinosoft.lis.schema.LRClaimPolicySchema;
import com.sinosoft.lis.schema.LRPolSchema;
import com.sinosoft.lis.schema.LRRiskSchema;
import com.sinosoft.lis.vschema.LLClaimPolicySet;
import com.sinosoft.lis.vschema.LRClaimPolicySet;
import com.sinosoft.lis.vschema.LRPolSet;
import com.sinosoft.lis.vschema.LRRiskSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class CalClaimRetentBL {

	public CErrors mErrors = new CErrors();
	private GlobalInput mGlobalInput = new GlobalInput();
	private VData mInputData;
	private String mOperate;
	private LRClaimPolicySet mLRClaimPolicySet = new LRClaimPolicySet();
	private VData mResult = new VData();
	private String mStartDate = "";
	private String mEndDate = "";
	private String mToday = "";
//	private String mReinsureCom = "";
	private LRRiskSet mLRRiskSet = new LRRiskSet();
	private String mRiskCode = "";
    private String mContNo = "";                            //计算单张保单时使用

	public CalClaimRetentBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		System.out.println("beging bl.......");
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		try {
			if (!mOperate.equals("CalClaim")) {
				buildError("submitData", "不支持的操作字符串");
				return false;
			}

			// 得到外部传入的数据，将数据备份到本类中
			if (!getInputData(cInputData)) {
				return false;
			}
			LRRiskDB tLRRiskDB = new LRRiskDB();
			this.mLRRiskSet = tLRRiskDB.executeQuery("select * from lrrisk");
			this.mLRClaimPolicySet.clear();
			mResult.clear();
			FDate chgdate = new FDate();
			Date dbdate = chgdate.getDate(mStartDate);
			Date dedate = chgdate.getDate(mEndDate);

			while (dbdate.compareTo(dedate) <= 0) {
				mToday = chgdate.getString(dbdate);
				if (this.mOperate.equals("CalClaim")) {
					if (!getClaimData()) {
						buildError("submitData", "错误");
						return false;
					}
				}
				dbdate = PubFun.calDate(dbdate, 1, "D", null);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("submit", "发生异常");
			return false;
		}
		return true;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "CalEdorRetentBL";

		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	private boolean getInputData(VData cInputData) {
		mGlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));
        TransferData mTransferData = (TransferData)cInputData.getObjectByObjectName("TransferData",0);

        if( mTransferData == null )
        {
            buildError("getInputData", "没有得到足够的信息！");
            return false;
        }

        mStartDate = (String)mTransferData.getValueByName("StartDate");
        mEndDate = (String)mTransferData.getValueByName("EndDate");
//        mReinsureCom = (String)mTransferData.getValueByName("ReinsureCom");
        mContNo = (String)mTransferData.getValueByName("ContNo");
        mRiskCode = (String)mTransferData.getValueByName("RiskCode");

		if (mStartDate.equals("")) {
			buildError("getInputData", "没有起始日期!");
			return false;
		}

		if (mEndDate.equals("")) {
			buildError("getInputData", "没有终止日期!");
			return false;
		}

		if (mGlobalInput == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}
		return true;
	}
	
	  /**
	   * 获取团单特殊标志（用于判断是否临分）
	   * @param cGrppolNo 团单号
	   * @return 1：其他（一般是合同分保）；2：临分；3：自留
	   */
	  private String getSpecFlag(String cGrppolNo)
	  {
	      if(cGrppolNo.equals("00000000000000000000"))         //个单，返回空
	          return "";
	      else
	      {
	          String sql = "select specflag from lcgrppol "
	                     + "where grppolno = '" + cGrppolNo + "'";
	          SSRS tssrs = new ExeSQL().execSQL(sql);

	          if(tssrs != null && tssrs.getMaxRow() > 0)
	              return tssrs.GetText(1,1);
	      }
	      return "";
	  }

	// 保单发生理赔的处理
	private boolean getClaimData() {
		// 保全事件处理
		this.mLRClaimPolicySet = new LRClaimPolicySet();
		String tSql = "select * from llclaimpolicy l where EndCaseDate='"
				+ this.mToday + "' and exists(select 1 from lrpol where polno=l.polno)"
				+ " and not exists(select 1 from lrclaimpolicy where clmno=l.clmno and polno=l.polno and getdutykind=l.getdutykind)";
		tSql+=ReportPubFun.getWherePart("contno", mContNo)
			+ ReportPubFun.getWherePart("riskcode", mRiskCode);
		System.out.println(tSql);
		LLClaimPolicyDB tLLClaimPolicyDB = new LLClaimPolicyDB();
		LLClaimPolicySet tLLClaimPolicySet = new LLClaimPolicySet();
		tLLClaimPolicySet = tLLClaimPolicyDB.executeQuery(tSql);
			
		for (int i = 1; i <= tLLClaimPolicySet.size(); i++) {
			LLClaimPolicySchema tLLClaimPolicySchema = new LLClaimPolicySchema();
			tLLClaimPolicySchema = tLLClaimPolicySet.get(i);
			// 查询保单信息
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setPolNo(tLLClaimPolicySchema.getPolNo());
			if (!tLCPolDB.getInfo()) {
				continue;
			}
			// 计算出险时的保单年度
			LLRegisterDB tLLRegisterDB = new LLRegisterDB();
			tLLRegisterDB.setRgtNo(tLLClaimPolicySchema.getRgtNo());
			if (!tLLRegisterDB.getInfo())
				continue;
			if (tLLRegisterDB.getAccidentDate() == null)
				continue;
			int tInsureYear = PubFun.calInterval(tLCPolDB.getCValiDate(),tLLRegisterDB.getAccidentDate(),"Y")+1;
			if (tLLClaimPolicySchema.getRealPay() != 0) {
				// 查询保全对应的保单年度是否有分保
				Reflections tReflections = new Reflections();
				LRPolDB tLRPolDB = new LRPolDB();
				tLRPolDB = new LRPolDB();
				tLRPolDB.setPolNo(tLLClaimPolicySchema.getPolNo());
				tLRPolDB.setInsuredYear(tInsureYear);
//				tLRPolDB.setReinsureCom(this.mReinsureCom);

				LRPolSet tLRPolSet = new LRPolSet();
				tLRPolSet = tLRPolDB.query();
				int tReinsureCount = tLRPolSet.size();
				// 如果没有分保，由于无分保比例，所以不用考虑
				if (tReinsureCount == 0) {
					continue;
				}
				LLCaseDB tLLCaseDB = new LLCaseDB ();
				tLLCaseDB.setCaseNo(tLLClaimPolicySchema.getCaseNo());
				if("121301".equals(tLLClaimPolicySchema.getRiskCode())){
					tLLCaseDB.setCustomerNo(tLLClaimPolicySchema.getAppntNo());
				}else{
					tLLCaseDB.setCustomerNo(tLLClaimPolicySchema.getInsuredNo());
				}
				String ACCRESULT2="";
				if(tLLCaseDB.getInfo())
					ACCRESULT2=tLLCaseDB.getAccResult2();
				for (int j = 1; j <= tLRPolSet.size(); j++) {
					LRPolSchema tLRPolSchema = new LRPolSchema();
					tLRPolSchema = tLRPolSet.get(j);
					LRClaimPolicySchema tLRClaimPolicySchema = new LRClaimPolicySchema();
					if(!checkDutyCode(tLRPolSchema, tLLClaimPolicySchema))
						continue;
					tReflections.transFields(tLRClaimPolicySchema,
							tLLClaimPolicySchema);

					double tRRP1 = getClaimPay(tLLClaimPolicySchema,
							tLRPolSchema);
					// double tRRP1=tLLClaimPolicySchema.getRealPay();
					double tRRP2 = tRRP1 * tLRPolSchema.getCessionRate();

					System.out.println("tRRP2===" + tRRP2);
					if (tRRP2 <= 0) {
						continue;
					}
					tLRClaimPolicySchema.setAccidentDate(tLLRegisterDB.getAccidentDate());
					tLRClaimPolicySchema.setReturnPay(tRRP2);
					tLRClaimPolicySchema.setReinsureCom(tLRPolSchema
							.getReinsureCom());
					tLRClaimPolicySchema.setReinsurItem(tLRPolSchema
							.getReinsurItem());
					tLRClaimPolicySchema.setRiskCalSort(tLRPolSchema
							.getRiskCalSort());
					tLRClaimPolicySchema.setInsuredYear(tLRPolSchema
							.getInsuredYear());
					tLRClaimPolicySchema.setCessStart(tLRPolSchema
							.getCessStart());
					tLRClaimPolicySchema.setCessEnd(tLRPolSchema.getCessEnd());
					tLRClaimPolicySchema.setCessionRate(tLRPolSchema
							.getCessionRate());
					tLRClaimPolicySchema.setCessionAmount(tLRPolSchema
							.getCessionAmount());
					tLRClaimPolicySchema.setAccResult2(ACCRESULT2);
					tLRClaimPolicySchema.setMakeDate(PubFun.getCurrentDate());
					tLRClaimPolicySchema.setMakeTime(PubFun.getCurrentTime());
					tLRClaimPolicySchema.setModifyDate(PubFun.getCurrentDate());
					tLRClaimPolicySchema.setModifyTime(PubFun.getCurrentTime());
					tLRClaimPolicySchema.setProtItem(tLRPolSchema.getProtItem());         //分保协议类型
					tLRClaimPolicySchema.setSpecFlag(getSpecFlag(tLRPolSchema.getGrpPolNo()));      //团单特殊标志
					tLRClaimPolicySchema.setTransSaleChnl(tLRPolSchema.getTransSaleChnl());
					LRClaimPolicyDB tLRClaimPolicyDB = new LRClaimPolicyDB();
					tLRClaimPolicyDB.setSchema(tLRClaimPolicySchema);
					if (!tLRClaimPolicyDB.getInfo()) {
						this.mLRClaimPolicySet.add(tLRClaimPolicySchema);
					}
				}
			}
			if (this.mLRClaimPolicySet.size() > 1000) {
				System.out.println("记录数：" + this.mLRClaimPolicySet.size());
				this.prepareData();
				CalClaimRetentBLS tCalClaimRetentBLS = new CalClaimRetentBLS();
				if (!tCalClaimRetentBLS.submitData(mInputData, this.mOperate)) {
					System.out.println("tCalClaimRetentBLS error out");
					this.mErrors.copyAllErrors(tCalClaimRetentBLS.mErrors);
					CError tError = new CError();
					tError.moduleName = "CalClaimRetentBL";
					tError.functionName = "submitData";
					tError.errorMessage = "数据提交失败!";
					this.mErrors.addOneError(tError);
					return false;
				}
				mLRClaimPolicySet.clear();
			}
		}
		System.out.println("记录数：" + this.mLRClaimPolicySet.size());
		if (this.mLRClaimPolicySet.size() > 0) {
			this.prepareData();
			CalClaimRetentBLS tCalClaimRetentBLS = new CalClaimRetentBLS();
			if (!tCalClaimRetentBLS.submitData(mInputData, this.mOperate)) {
				System.out.println("tCalClaimRetentBLS error out");
				this.mErrors.copyAllErrors(tCalClaimRetentBLS.mErrors);
				CError tError = new CError();
				tError.moduleName = "CalClaimRetentBL";
				tError.functionName = "submitData";
				tError.errorMessage = "数据提交失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}

		return true;
	}

	private boolean checkDutyCode(LRPolSchema tLRPolSchema, LLClaimPolicySchema tLLClaimPolicySchema) {
		if (tLRPolSchema.getDutyCode().equals("000000"))
			return true;
		String sql = "select sum(standpay),sum(realpay) from llclaimdetail where clmno='"
				+ tLLClaimPolicySchema.getClmNo() + "' and polno='"
				+ tLLClaimPolicySchema.getPolNo()
				+ "' and getdutykind='"
				+ tLLClaimPolicySchema.getGetDutyKind()
				+ "' and caserelano='"
				+ tLLClaimPolicySchema.getCaseRelaNo()
				+ "' and caseno='"
				+ tLLClaimPolicySchema.getCaseNo() 
				+ "' and dutycode='" + tLRPolSchema.getDutyCode()+ "'";
		SSRS tSSRS=new ExeSQL().execSQL(sql);
		if(tSSRS.getMaxRow()<=0)//不应该呀
			return false;
		tLLClaimPolicySchema.setStandPay(tSSRS.GetText(1, 1));
		tLLClaimPolicySchema.setRealPay(tSSRS.GetText(1, 2));
		return true;
	}

	// 取得分保保费
	private double getClaimPay(LLClaimPolicySchema aLLClaimPolicySchema,
			LRPolSchema aLRPolSchema) {
		double tClaimPay = 0;
		LRRiskSchema aLRRiskSchema = new LRRiskSchema();
		for (int i = 1; i <= this.mLRRiskSet.size(); i++) {
			LRRiskSchema tLRRiskSchema = new LRRiskSchema();
			tLRRiskSchema = this.mLRRiskSet.get(i);
			if (tLRRiskSchema.getRiskCode().equals(
					aLLClaimPolicySchema.getRiskCode())
					&& tLRRiskSchema.getReinsurItem().equals(
							aLRPolSchema.getReinsurItem())
					&& tLRRiskSchema.getRiskCalSort().equals(
							aLRPolSchema.getRiskCalSort())) {
				aLRRiskSchema = tLRRiskSchema;
				break;
			}
		}
		if (aLRRiskSchema.getReturnPayCalCode() != null
				&& !aLRRiskSchema.getReturnPayCalCode().equals("")) {
			Calculator mCalculator = new Calculator();
			mCalculator.setCalCode(aLRRiskSchema.getReturnPayCalCode());
			// 增加基本要素
			// 实际赔付金额
			mCalculator.addBasicFactor("RealPay", String
					.valueOf(aLLClaimPolicySchema.getRealPay()));
			// 保单号
			mCalculator.addBasicFactor("PolNo", String
					.valueOf(aLLClaimPolicySchema.getPolNo()));
			// 立案号
			mCalculator.addBasicFactor("RgtNo", String
					.valueOf(aLLClaimPolicySchema.getRgtNo()));
			// 险类
			mCalculator.addBasicFactor("RiskCalSort", String
					.valueOf(aLRRiskSchema.getRiskCalSort()));

			String tStr = mCalculator.calculate();
			if (tStr == null || tStr.trim().equals("")) {
				tClaimPay = 0;
			} else {
				tClaimPay = Double.parseDouble(tStr);
			}
		} else {
			System.out.println("无计算编码");
			tClaimPay = 0;
		}
		return tClaimPay;
	}

	private void prepareData() {
		this.mInputData.clear();
		this.mResult.clear();
		this.mInputData.add(this.mLRClaimPolicySet);
		this.mResult.addElement(this.mLRClaimPolicySet);
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		CalClaimRetentUI tCalClaimRetentUI = new CalClaimRetentUI();
		VData vData = new VData();
		GlobalInput tG = new GlobalInput();
		String tt = "2003-09-07";
		String t1 = tt.substring(5, 7);
		String t2 = tt.substring(8, 10);

		tG.Operator = "001";
		tG.ManageCom = "86";
		String bdate = "2003-12-16";
		String edate = "2003-12-16";
		vData.addElement(tG);
		vData.addElement(bdate);
		vData.addElement(edate);
		vData.addElement("1001");
		tCalClaimRetentUI.submitData(vData, "CalClaim");

	}
}
