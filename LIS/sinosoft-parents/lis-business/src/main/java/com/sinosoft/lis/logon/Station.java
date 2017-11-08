package com.sinosoft.lis.logon;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LDUserTraceSchema;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.db.LDUserTraceDB;
public class Station implements BusinessService{	
private static Logger logger = Logger.getLogger(Station.class);
	private String ls;
	private String logonStr;
	private GlobalInput tG;
	private String nodecode;
	private String ip;
	private VData mResult = new VData();
	private CErrors mErrors = new CErrors();

	private void dealData()
	{
		ExeSQL tExeSQL = new ExeSQL();
		if ((nodecode == null) || (nodecode == "")) {
			ls = "现在的位置：首页";
		} else {
			if (nodecode.equals("60001")) {
				ls = "现在的位置：密码修改";
			} else {
				MenuShow menuShow = new MenuShow();
				// log.debug("Begin...");
				ls = menuShow.getStation(nodecode);
				// log.debug("End...");
				ls = ls.substring(0, ls.length() - 3);

				// GlobalInput tG = (GlobalInput)session.getValue("GI");
				LDUserTraceSchema tLDUserTraceSchema = new LDUserTraceSchema();
				if(!"ORACLE".equalsIgnoreCase(SysConst.DBTYPE)){
					tLDUserTraceSchema.setTRACENO(PubFun1.CreateMaxNo("TraceNo", 10));
				}
				tLDUserTraceSchema.setTraceType("00");
				tLDUserTraceSchema.setTraceContent(ls.substring(6));
				tLDUserTraceSchema.setClientIP(ip);
				tLDUserTraceSchema.setMakeDate(PubFun.getCurrentDate());
				tLDUserTraceSchema.setMakeTime(PubFun.getCurrentTime());
				tLDUserTraceSchema.setManageCom( tG.ComCode);
				tLDUserTraceSchema.setOperator(tG.Operator);
				
				LDUserTraceDB tLDUserTraceDB = tLDUserTraceSchema.getDB();
				tLDUserTraceDB.insert();
			}
		}

		String ManageCom = tG.ComCode;
		String Operator = tG.Operator;
		// tExeSQL
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql("select concat(concat(trim(ComCode),'--'),trim(Name)) from ldcom where ComCode='?ManageCom?'");
		sqlbv.put("ManageCom", ManageCom);
		String StationStr = tExeSQL
				.getOneValue(sqlbv);
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql("select concat(concat(trim(UserCode),'--'),trim(UserName)) from lduser where  UserCode='?Operator?'");
		sqlbv1.put("Operator", Operator);
		String OperatorStr = tExeSQL
				.getOneValue(sqlbv1);

		// 显示登录区站及用户姓名
		logonStr = "登录区站：" + StationStr + "          登录用户：" + OperatorStr;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	public VData getResult() {
		return mResult;
	}

	public boolean submitData(VData data, String Operater) {
		tG = (GlobalInput)data.getObjectByObjectName("GlobalInput", 0);
		TransferData tTransferData = (TransferData)data.getObjectByObjectName("TransferData", 0);
		nodecode = (String)tTransferData.getValueByName("nodecode");
		ip = (String)tTransferData.getValueByName("Ip");
		dealData();
		tTransferData = new TransferData();
		tTransferData.setNameAndValue("ls", ls);
		tTransferData.setNameAndValue("logonStr", logonStr);
		mResult.add(tTransferData);
		return true;
	}
}
