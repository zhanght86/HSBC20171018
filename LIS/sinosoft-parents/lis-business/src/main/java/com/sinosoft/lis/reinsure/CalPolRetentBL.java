

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.reinsure;

import java.util.Date;

import com.sinosoft.lis.bq.EdorCalZT;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LRPolDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.ReportPubFun;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LRPolSchema;
import com.sinosoft.lis.vschema.LRPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class CalPolRetentBL {

	public CErrors mErrors = new CErrors();
	private GlobalInput mGlobalInput = new GlobalInput();
	private VData mInputData;
	private String mOperate;
	private LRPolSet mLRPolSet = new LRPolSet();
	private VData mResult = new VData();
	private String mStartDate = "";
	private String mEndDate = "";
	private String mToday = "";
//    private String mReinsureCom="";
	private String mRiskcode = "";
    private String mContNo = "";                            //计算单张保单时使用

	public CalPolRetentBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
        System.out.println("start CalPolRetentBL.......");
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		try {
			if (!mOperate.equals("CalLegal") && !mOperate.equals("CalComm")) {
				buildError("submitData", "不支持的操作字符串");
				return false;
			}

			// 得到外部传入的数据，将数据备份到本类中
			if (!getInputData(cInputData)) {
				return false;
			}
			mLRPolSet.clear();
			mResult.clear();
			FDate chgdate = new FDate();
			Date dbdate = chgdate.getDate(mStartDate);
			Date dedate = chgdate.getDate(mEndDate);

			while (dbdate.compareTo(dedate) <= 0) {
				mToday = chgdate.getString(dbdate);
				// 提取分保数据
				if (!getCessData()) {
					buildError("submitData", "错误");
					return false;
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
		cError.moduleName = "CalLegalRetentBL";

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
		mRiskcode = (String) mTransferData.getValueByName("RiskCode");

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

	private boolean getCessData() // 法定分保保单年度处理
	{
		String tSql = "";
		String tItemFlag = "";
		if (this.mOperate.equals("CalLegal")) {
			tItemFlag = "L";
		} else {
			tItemFlag = "C";
		}
		mLRPolSet = new LRPolSet();
		// 查询所有对应保单周年的保单
		tSql = "select polno from LCPol where to_char(signdate,'MM')='"
				+ this.mToday.substring(5, 7)
				+ "' and to_char(signdate,'DD')='"
				+ this.mToday.substring(8, 10) + "' and ManageCom like '"
				+ mGlobalInput.ManageCom + "%' and signdate<='"
				+ this.mToday
				+ "' and riskcode in(select riskcode from lrrisk) "
				+ " and not exists (select 1 from lrpol where polno=lcpol.polno " 
				+ " and cessstart>=trunc(DATE '"+ mToday +"','YEAR')) "
            	+ ReportPubFun.getWherePart("contno",mContNo)
				+ ReportPubFun.getWherePart("riskcode", mRiskcode)
				//+ " order by signdate,makedate,maketime"
				;

		System.out.println(tSql);
		SSRS tSSRS=new ExeSQL().execSQL(tSql);
		for (int i = 1; i <= tSSRS.MaxRow; i++) {
			String polno=tSSRS.GetText(i, 1);
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setPolNo(polno);
			if(!tLCPolDB.getInfo()){
				new Exception(i+" Can't find lcpol with polno:"+polno).printStackTrace();
				continue;
			}
			LCPolSchema tLCPolSchema = tLCPolDB.getSchema();
			// 判断该保单是否失效
			if ("1".equals(tLCPolSchema.getAppFlag())) {
				if (EdorCalZT.JudgeLapse(tLCPolSchema.getRiskCode(),
						tLCPolSchema.getPaytoDate(), this.mToday, String
								.valueOf(tLCPolSchema.getPayIntv()),
						tLCPolSchema.getEndDate())) {
					// dealing with the policies whose signdate is not earlier than
					// their insure end date(the policies of travel Accident, for
					// instance).
					FDate tFDate = new FDate();
					Date tDate = tFDate.getDate(tLCPolSchema.getSignDate());
					Date tDate1 = tFDate.getDate(tLCPolSchema.getEndDate());
					if (tDate.before(tDate1)) {
						continue;
					}
				}
			} else {
				// 处理责任终止保单
				if (tLCPolSchema.getEndDate().equals(this.mToday)) {
					continue;
				}
				FDate tFDate = new FDate();
				Date tDate = tFDate.getDate(tLCPolSchema.getModifyDate());
				Date tDate1 = tFDate.getDate(this.mToday);
				if (tDate.before(tDate1)) // 如果销户日期早于当前计算日期，则不应该分保
				{
					continue;
				}
			}

			VData tVData = new VData();
			tVData.addElement(tLCPolSchema);
			tVData.addElement(tItemFlag);
//			tVData.addElement(this.mReinsureCom);
			tVData.addElement(mToday);
			tVData.add(this.mLRPolSet);
			tVData.add(Boolean.FALSE);

			CalRetentBL tCalRetentBL = new CalRetentBL();
			if (tCalRetentBL.submitData(tVData, this.mOperate)) {
				VData tResult = new VData();
				tResult = tCalRetentBL.getResult();
				LRPolSet tLRPolSet = new LRPolSet();
				tLRPolSet = (LRPolSet) tResult.getObjectByObjectName(
						"LRPolSet", 0);
				for (int LRPolCount = 1; LRPolCount <= tLRPolSet.size(); LRPolCount++) {
					LRPolSchema ttLRPolSchema = new LRPolSchema();
					ttLRPolSchema = tLRPolSet.get(LRPolCount);
					LRPolDB tLRPolDB = new LRPolDB();
					tLRPolDB.setSchema(ttLRPolSchema);
					System.out.println("Checking the insurance policy:"
							+ ttLRPolSchema.getPolNo());
					if (!tLRPolDB.getInfo()) {
						this.mLRPolSet.add(ttLRPolSchema);
					}
				}
			}
			if(mLRPolSet.size()>1000){
				System.out.println("记录数：" + this.mLRPolSet.size());
				this.prepareData();
				CalPolRetentBLS tCalPolRetentBLS = new CalPolRetentBLS();
				if (!tCalPolRetentBLS.submitData(mInputData, this.mOperate)) {
					System.out.println("tCalPolRetentBLS error out");
					this.mErrors.copyAllErrors(tCalPolRetentBLS.mErrors);
					CError tError = new CError();
					tError.moduleName = "CalRetentBL";
					tError.functionName = "submitData";
					tError.errorMessage = "数据提交失败!";
					this.mErrors.addOneError(tError);
					return false;
				}
				mLRPolSet.clear();
			}
		}

		System.out.println("记录数：" + this.mLRPolSet.size());
		this.prepareData();
		CalPolRetentBLS tCalPolRetentBLS = new CalPolRetentBLS();
		if (!tCalPolRetentBLS.submitData(mInputData, this.mOperate)) {
			System.out.println("tCalPolRetentBLS error out");
			this.mErrors.copyAllErrors(tCalPolRetentBLS.mErrors);
			CError tError = new CError();
			tError.moduleName = "CalRetentBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	private void prepareData() {
		this.mInputData.clear();
		this.mResult.clear();
		this.mInputData.add(this.mLRPolSet);
		this.mResult.addElement(this.mLRPolSet);
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		CalPolRetentBL tCalPolRetentBL = new CalPolRetentBL();
		VData vData = new VData();
		GlobalInput tG = new GlobalInput();

		tG.Operator = "001";
		tG.ManageCom = "86";
		String bdate = "2003-08-30";
		String edate = "2003-08-30";
		vData.addElement(tG);
		vData.addElement(bdate);
		vData.addElement(edate);
		vData.addElement("1001");
		tCalPolRetentBL.submitData(vData, "CalComm");
		// tCalPolRetentBL.submitData(vData, "CalLegal");
	}
}
