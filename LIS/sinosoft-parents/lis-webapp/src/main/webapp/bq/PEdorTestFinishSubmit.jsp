<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//程序名称：PEdorTestFinishSubmit.jsp
//程序功能：保全试算完毕处理
//创建日期：2005-11-10
//创建人  ：zhangtao
//更新记录：  更新人    更新日期     更新原因/内容
//
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%> 
  <%@page import="com.sinosoft.lis.pubfun.*"%>
 <%@page import="com.sinosoft.service.BusinessDelegate"%>

<%
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
	
	
	try
	{
    	// 准备传输数据 VData
    tVData.addElement(tGI);
    tVData.addElement(tLPEdorAppSchema);
		tVData.addElement(tTransferData);

 
    String busiName="EdorWorkFlowUI";
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    if(!tBusinessDelegate.submitData(tVData,"0000000098",busiName))
    {    
        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
        { 
				   Content = "删除失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
				   FlagStr = "Fail";
				}
				else
				{
				   Content = "删除失败";
				   FlagStr = "Fail";				
				}		
	 
	 }
	 else
	 {
				  Content ="删除成功！";
		    	FlagStr = "Succ";	
	 }
	}
    catch(Exception ex){
		   Content = "删除失败";
		   FlagStr = "Fail";	
    }
%>                                       
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

