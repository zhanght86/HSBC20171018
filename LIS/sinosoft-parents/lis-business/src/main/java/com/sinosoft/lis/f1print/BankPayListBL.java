package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.AbstractBL;
import com.sinosoft.lis.pubfun.ReportPubFun;
import com.sinosoft.lis.pubfun.ZHashReport;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.XmlExport;

public class BankPayListBL extends AbstractBL {
private static Logger logger = Logger.getLogger(BankPayListBL.class);

	private String mStartDate;
	private String mEndDate;
	private String mBankCode;
	private String mManageCom;

	protected boolean dealData() {
		String sql="select distinct p.managecom,p.bankcode,p.bankaccno,a.appntname,a.idno,p.enteraccdate,p.sumactupaymoney " 
		+"  from ljapay p,lcappnt a " 
		+" where p.appntno = a.appntno and p.bankaccno is not null";
		sql+=ReportPubFun.getWherePart("p.enteraccdate",ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),1);
		sql+=ReportPubFun.getWherePart("p.bankcode", ReportPubFun.getParameterStr(mBankCode,"?mBankCode?"));
		sql+=" and p.managecom like concat('?mManageCom?','%') ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("mStartDate", mStartDate);
		sqlbv.put("mEndDate", mEndDate);
		sqlbv.put("mBankCode", mBankCode);
		sqlbv.put("mManageCom", mManageCom);
		ZHashReport rep=new ZHashReport(sqlbv);
		for (int i = 0; i < 7; i++) {
			rep.setColumnType(i,rep.StringType);
		}
		rep.addValue("Title","银行代收成功清单");
		rep.addValue("StartDate","开始时间"+mStartDate);
		rep.addValue("EndDate","结束时间"+mEndDate);
		rep.addValue("ManageCom","管理机构"+mManageCom);
		XmlExport export= rep.createXml("BankPayList.vts");
		if(rep.getRowCount()>8000){
			CError.buildErr(this,"数据量过大(超过8000条)，为减轻服务器负担，请缩小查询范围");
			return false;
		}
		mResult.add(export);
		
		return true;
	}

	protected boolean getInputData() {
		TransferData data = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mStartDate=(String) data.getValueByName("StartDate");
		mEndDate=(String) data.getValueByName("EndDate");
		mBankCode=(String) data.getValueByName("BankCode");
		mManageCom=(String) data.getValueByName("ManageCom");
		return true;
	}

}
