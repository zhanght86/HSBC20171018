<%
/***************************************************************
 * <p>ProName：LSQuotPrintInquirySave.jsp</p>
 * <p>Title：询价单打印</p>
 * <p>Description：询价单打印</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-08-18
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<%@page import="com.sinosoft.lis.schema.LWMissionSchema"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	String tFilePath = "";
	
	ExeSQL tExeSQL = new ExeSQL();
	
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "页面失效,请重新登陆";
	} else {
		
		try {
			String tDate = PubFun.getCurrentDate();
			String tYear = tDate.substring(0,4);
			String tMonth = tDate.substring(5,7);
			String tDay = tDate.substring(8);
			
			tFilePath = tExeSQL.getOneValue("select sysvarvalue from ldsysvar where sysvar = 'PrintPath'"); // /app/print/
			tFilePath = tFilePath + "quot/inquiry/"+tYear+"/"+tMonth+"/"+tDay+"/";
			String tOperate = request.getParameter("Operate");
			
			String tQuotNo = request.getParameter("QuotNo");
			String tQuotBatNo = request.getParameter("QuotBatNo");
			String tQuotType = request.getParameter("QuotType");
			String tProdType = request.getParameter("ProdType");
			String tMissionID = request.getParameter("MissionID");
			String tSubMissionID = request.getParameter("SubMissionID");
			String tActivityID = request.getParameter("ActivityID");
			String tPrintType = request.getParameter("PrintType");
			
			TransferData nTransferData = new TransferData();
			nTransferData.setNameAndValue("QuotNo", tQuotNo);
			nTransferData.setNameAndValue("QuotBatNo", tQuotBatNo);
			nTransferData.setNameAndValue("QuotType", tQuotType);
			nTransferData.setNameAndValue("ProdType", tProdType);
			nTransferData.setNameAndValue("FilePath",tFilePath);
			nTransferData.setNameAndValue("PrintType",tPrintType);
			
			LWMissionSchema tLWMissionSchema = new LWMissionSchema();
			tLWMissionSchema.setMissionID(tMissionID);
			tLWMissionSchema.setSubMissionID(tSubMissionID);
			tLWMissionSchema.setActivityID(tActivityID);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(nTransferData);
			tVData.add(tLWMissionSchema);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LSQuotPrintInquiryUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				TransferData tTransferData = (TransferData)tBusinessDelegate.getResult().getObjectByObjectName("TransferData", 0);
				String tFileName =(String)tTransferData.getValueByName("FileName");
				
				tFlagStr = "Succ";
				tContent = tFilePath + tFileName;
			}
		} catch (Exception ex) {
			tContent = tFlagStr + "处理异常，请联系系统运维人员！";
			tFlagStr = "Fail";
		}
	}
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>");
</script>
</html>
