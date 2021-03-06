<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.sys.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.workflow.bq.*"%>
  <%@page import="com.sinosoft.service.*" %>

<%
  String busiName="EdorWorkFlowUI";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  
  //接收信息，并作校验处理。
  LPEdorAppSchema tLPEdorAppSchema = new LPEdorAppSchema();

  //输出参数
  String FlagStr = "";
  String Content = "";
  GlobalInput tGI = new GlobalInput(); 
  tGI=(GlobalInput)session.getValue("GI");  
  String Operator  = tGI.Operator ;  //保存登陆管理员账号
  String ManageCom = tGI.ComCode  ; //保存登陆区站,ManageCom内存中登陆区站代码
  CErrors tError = null;
  String delReason = ""; //删除原因
  String reasonCode = ""; //原因编号


  TransferData tTransferData = new TransferData();
  VData tVData = new VData();

	//撤销保全申请
	tLPEdorAppSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));     
	tLPEdorAppSchema.setEdorState(request.getParameter("EdorState"));
	
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");		
	
	tTransferData.setNameAndValue("MissionID",tMissionID);
	tTransferData.setNameAndValue("SubMissionID",tSubMissionID);		
	tTransferData.setNameAndValue("DelReason", delReason);
	tTransferData.setNameAndValue("ReasonCode", reasonCode);
	
	//EdorWorkFlowUI tEdorWorkFlowUI = new EdorWorkFlowUI();
	
	try
	{
    	// 准备传输数据 VData
     	tVData.addElement(tGI);
     	tVData.addElement(tLPEdorAppSchema);
		tVData.addElement(tTransferData);

		tBusinessDelegate.submitData(tVData, "0000008098",busiName);	

    }
	catch(Exception ex)
	{
	      Content = "删除失败，原因是:" + ex.toString();
	      FlagStr = "Fail";
	}
	if ("".equals(FlagStr))
	{
		tError = tBusinessDelegate.getCErrors();
		if (!tError.needDealError())
		{                          
			Content ="删除成功！";
			FlagStr = "Succ";
		}
		else                                                                           
		{
			Content = "删除失败，原因是:" + tError.getFirstError();
			FlagStr = "Fail";
		}
	}  

%>                                       
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

