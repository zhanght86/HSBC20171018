package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author 刘岩松
 * function :print InputEfficiency or print CheckEfficiency Business Logic layer
 * @version 1.0
 * @date 2003-04-04
 */

import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

public class GrpNFeeBL {
private static Logger logger = Logger.getLogger(GrpNFeeBL.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/*
	 * @define globe variable
	 */
	private String strMngCom;

	private String strFeeDate;

	// private String strEndDate;

	public GrpNFeeBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		if (!cOperate.equals("PRINT")) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}

		if (!getInputData(cInputData)) {
			return false;
		}
		mResult.clear();

		if (!getPrintData()) {
			return false;
		}
		return true;
	}

	/***************************************************************************
	 * Name : getInputData() function :receive data from jsp and check date
	 * check :judge whether startdate begin enddate return :true or false
	 */
	private boolean getInputData(VData cInputData) {
		strMngCom = (String) cInputData.get(0);
		strFeeDate = (String) cInputData.get(1);
		// strEndDate = (String) cInputData.get(2);
		// tUWStatType = (String) cInputData.get(3);

		logger.debug("strFeeDate:" + strFeeDate);
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	/***************************************************************************
	 * name : buildError function : Prompt error message return :error message
	 */

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "GrpNFeeBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/***************************************************************************
	 * name : getPrintData() function :get print data parameter : null return
	 * :true or false
	 */

	private boolean getPrintData() {
		XmlExport xmlexport = new XmlExport(); // Create a XmlExport instance
		xmlexport.createDocument("GrpNFee.vts", "printer");
		ListTable alistTable = new ListTable();
		alistTable.setName("List");
		String t_sql = "select name from ldcom where comcode ='" + strMngCom
				+ "'";
		ExeSQL exeSQL1 = new ExeSQL();
		SSRS ssrs1 = exeSQL1.execSQL(t_sql);
		String name1 = ssrs1.GetText(1, 1);
		logger.debug("开始执行sql操作");
		// t_sql = "select a.proposalcontno,a.appntname from lccont a,ljtempfee
		// b where a.proposalcontno=b.otherno and a.managecom='"+strMngCom+"'
		// and b.paydate='"+strFeeDate+"' ";
		// String strSQL="select a.missionprop1,a.missionprop7,(select
		// sum(c.paymoney) from ljtempfee c where
		// trim(c.otherno)=trim(a.missionprop1))"
		// +",((select sum(b.standprem) from lcpol b where
		// trim(b.grpcontno)=trim(a.missionprop1))-(select sum(c.paymoney) from
		// ljtempfee c where trim(c.otherno)=trim(a.missionprop1))),"
		// +"a.missionprop5,(select d.name from laagent d where
		// d.agentcode=a.missionprop5),(select e.name from labranchgroup e where
		// trim(e.agentgroup)=trim(a.missionprop6)),(select f.modifydate from
		// lcgcuwmaster f where trim(f.grpcontno)=trim(a.missionprop1))"
		// +" from lwmission a where a.processid = '0000000004' and a.activityid
		// = '0000002006'and a.missionprop4='"+strMngCom+"' and (select
		// sum(b.standprem) from lcpol b where
		// trim(b.grpcontno)=trim(a.missionprop1))-(select sum(c.paymoney) from
		// ljtempfee c where trim(c.otherno)=trim(a.missionprop1))<>0";
		String strSQL = "select a.missionprop1, a.missionprop7,(select nvl(sum(b.prem),0)from lcpol b"
				+ " where trim(b.grpcontno) = trim(a.missionprop1)),((select nvl(sum(b.prem),0)"
				+ " from lcpol b"
				+ " where trim(b.grpcontno) = trim(a.missionprop1)) -(select nvl(sum(c.paymoney),0)"
				+ " from ljtempfee c"
				+ " where trim(c.otherno) = trim(a.missionprop1))),"
				+ " a.missionprop5,(select d.name from laagent d where d.agentcode = a.missionprop5),"
				+ " (select e.name from labranchgroup e "
				+ " where trim(e.agentgroup) = trim(a.missionprop6)),(select f.modifydate from lcgcuwmaster f"
				+ " where trim(f.grpcontno) = trim(a.missionprop1))"
				+ " from lwmission a"
				+ " where a.processid = '0000000004'"
				+ " and a.activityid = '0000002006'"
				+ " and a.missionprop4 like '"
				+ strMngCom
				+ "%'"
				+ " and (select nvl(sum(b.prem),0)"
				+ " from lcpol b"
				+ " where trim(b.grpcontno) = trim(a.missionprop1)) -(select nvl(sum(c.paymoney),0)"
				+ " from ljtempfee c"
				+ " where trim(c.otherno) = trim(a.missionprop1)) > 0";
		logger.debug("您的sql的执行结果是" + strSQL);
		ExeSQL exeSQL = new ExeSQL();
		SSRS ssrs = exeSQL.execSQL(strSQL);
		int count1 = ssrs.getMaxRow();
		double Sum = 0;

		// String name=ComCode_ssrs.GetText(1, 1);
		// UpComCode = ComCode_ssrs.GetText(1, 2);
		// logger.debug("UpComCode"+UpComCode);
		// String sql="select name from ldcom where comcode='"+UpComCode+"'";
		// ExeSQL exeSQL2 = new ExeSQL();
		// SSRS ComCode2_ssrs = exeSQL2.execSQL(sql);

		if (count1 == 0) {
			CError tError = new CError();
			tError.moduleName = "ForeceitPrintBL";
			tError.functionName = "getDutyGetClmInfo";
			tError.errorMessage = "在该天没有待收费团单！！！";
			this.mErrors.addOneError(tError);
			return false;

		} else {
			for (int i = 1; i <= count1; i++) {
				String[] col = new String[8];
				col[0] = ssrs.GetText(i, 1);
				col[1] = ssrs.GetText(i, 2);
				col[2] = ssrs.GetText(i, 3);
				col[3] = ssrs.GetText(i, 4);
				col[4] = ssrs.GetText(i, 5);
				col[5] = ssrs.GetText(i, 6);
				col[6] = ssrs.GetText(i, 7);
				col[7] = ssrs.GetText(i, 8);
				Sum = Sum + Double.parseDouble(ssrs.GetText(i, 4));
				alistTable.add(col);
			}
		}
		logger.debug("sum" + Sum);

		String[] cols = new String[8];
		xmlexport.addListTable(alistTable, cols);
		TextTag texttag = new TextTag();
		texttag.add("SysDate", PubFun.getCurrentDate());
		texttag.add("ManageCom", name1);
		texttag.add("Num", count1);
		texttag.add("Sum", Sum);

		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}
		// xmlexport.outputDocumentToFile("e:\\","GrpNFee");//输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);
		return true;
	}

}
