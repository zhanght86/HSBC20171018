<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%
//WFEdorNoscanAppSave.jsp
//程序功能：保全无扫描申请创建开始节点
//创建日期：2005-04-27 15:59:52
//创建人  ：zhangtao
//更新记录：  更新人    更新日期     更新原因/内容
//      
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%

  //输出参数
  String FlagStr = "";
  String Content = ""; 
  String sOperate = "9999999999";  //创建开始节点分支参数
  String rEdorAcceptNo = "";
  CErrors tError = null;
  
  GlobalInput tG = new GlobalInput(); 
  tG = (GlobalInput) session.getValue("GI");
  
  VData tVData = new VData();
  TransferData tTransferData = new TransferData();
  tTransferData.setNameAndValue("ICFlag", request.getParameter("ICFlag"));
  tTransferData.setNameAndValue("BusiType", "1002");
  tTransferData.setNameAndValue("ActivityID","");
  String strLimit = PubFun.getNoLimit(tG.ManageCom);
	String strEdorAcceptNo = PubFun1.CreateMaxNo("EdorAcceptNo",strLimit);
  tTransferData.setNameAndValue("EdorAcceptNo", strEdorAcceptNo);
  tTransferData.setNameAndValue("ManageCom",tG.ManageCom);
	tTransferData.setNameAndValue("AppntName", "");
	tTransferData.setNameAndValue("PaytoDate", "");
	tTransferData.setNameAndValue("OtherNo", "");
	tTransferData.setNameAndValue("OtherNoType", "");
	tTransferData.setNameAndValue("EdorAppName", "");
	tTransferData.setNameAndValue("Apptype", "");
	tTransferData.setNameAndValue("EdorAppDate", "");
	tTransferData.setNameAndValue("DefaultOperator",tG.Operator);
  
 
		tVData.add(tG);		
		tVData.add(tTransferData);
		String busiName = "WorkFlowUI";
		BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
		
	/*	
    String busiName="tWorkFlowUI";
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    */
    if(!tBusinessDelegate.submitData(tVData,"create",busiName))
    {    
        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
        { 
				   Content = "申请失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
				   FlagStr = "Fail";
				}
				else
				{
				   Content = "操作失败";
				   FlagStr = "Fail";				
				}
		}
		else
		{
				  VData mResult;
 
				  TransferData rTransferData;	
						
					mResult = tBusinessDelegate.getResult();
 
          rTransferData = (TransferData)mResult.getObjectByObjectName("TransferData", 0);
          
          rEdorAcceptNo = (String)rTransferData.getValueByName("EdorAcceptNo");		
				  Content ="申请成功！保全受理号：" + rEdorAcceptNo;
		    	FlagStr = "Succ";							
							
		}
%>
   
<html>
<script language="javascript">
	parent.fraInterface.fm.EdorAcceptNo.value= "<%=rEdorAcceptNo%>";
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
   
   
   
 