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
  String sOperate = "9999999992";  //创建开始节点分支参数
  String rEdorAcceptNo = "";
  CErrors tError = null;
  
  GlobalInput tG = new GlobalInput(); 
  tG = (GlobalInput) session.getValue("GI");
  
  VData tVData = new VData();
  TransferData tTransferData = new TransferData();
  
		tVData.add(tG);		
		tVData.add(tTransferData);
    String busiName="EdorWorkFlowUI";
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    if(!tBusinessDelegate.submitData(tVData,sOperate,busiName))
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
				VData mINResult;
				TransferData rTransferData;	
						
					mResult = tBusinessDelegate.getResult();

          //mINResult = (VData)mResult.getObjectByObjectName("VData", 0);

         // rTransferData = (TransferData)mINResult.getObjectByObjectName("TransferData", 0);
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
   
   
   
 