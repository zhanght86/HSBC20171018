<%
//程序名称：PEdorTypeSCSubmit.jsp
//程序功能：
//创建日期：2007-12-10 16:49:22
//创建人  ：PST
//更新记录：  更新人    更新日期     更新原因/内容

%>
<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.bq.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.service.*" %> 

<%

CErrors tError = null;
String tRela  = "";
String FlagStr = "";
String Content = "";
String transact = "";

GlobalInput tGlobalInput = (GlobalInput)session.getValue("GI");

transact = request.getParameter("fmtransact");
String edorNo = request.getParameter("EdorNo");
String edorType = request.getParameter("EdorType");
String contNo = request.getParameter("ContNo");   
String polNo = request.getParameter("PolNo");
String edoracceptNo = request.getParameter("EdorAcceptNo");
String tSpecContent=request.getParameter("Speccontent");
String grpContNo = request.getParameter("GrpContNo");
String appObj = request.getParameter("AppObj");

String tSpecType = request.getParameter("SpecType");

// 准备传输数据 VData
LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
tLPEdorItemSchema.setEdorNo(edorNo);
tLPEdorItemSchema.setContNo(contNo);
tLPEdorItemSchema.setPolNo(polNo);
tLPEdorItemSchema.setEdorAcceptNo(edoracceptNo);
tLPEdorItemSchema.setEdorType(edorType);

String tChk[] = request.getParameterValues("InpLCCSpecGridSel"); 
//String tPolNo[] = request.getParameterValues("LCCSpecGrid2");
String tSNNo[] = request.getParameterValues("LCCSpecGrid5");  
LPCSpecSchema tLPCSpecSchema = new LPCSpecSchema();   
if(tChk!=null&&("modify".equals(transact) || "delete".equals(transact)))
{         
for(int index=0;index<tChk.length;index++)
{
      if(tChk[index].equals("1"))
       {       
           //修改特约 or 删除特约
    	  tLPCSpecSchema.setContNo(contNo);
    	  tLPCSpecSchema.setEdorNo(edorNo);
    	  tLPCSpecSchema.setEdorType(edorType);
    	  tLPCSpecSchema.setSpecContent(tSpecContent);
    	  tLPCSpecSchema.setSerialNo(tSNNo[index]);
    	  
       }
}  
}else
{         //新增特约
    	  tLPCSpecSchema.setContNo(contNo);
    	  tLPCSpecSchema.setEdorNo(edorNo);
    	  tLPCSpecSchema.setEdorType(edorType);
    	  tLPCSpecSchema.setSpecType(tSpecType);
    	  tLPCSpecSchema.setSpecContent(tSpecContent);
    	  tLPCSpecSchema.setGrpContNo("00000000000000000000");
}    




VData tVData = new VData();
tVData.add(tGlobalInput);
tVData.add(tLPEdorItemSchema);
tVData.add(tLPCSpecSchema);
//EdorDetailUI tEdorDetailUI = new EdorDetailUI();
	 String busiName="EdorDetailUI";
	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	 
System.out.println("--=="+  tSpecContent +"  ==--");
//if (!tEdorDetailUI.submitData(tVData, transact))
if (!tBusinessDelegate.submitData(tVData, transact ,busiName))
{
//       VData rVData = tEdorDetailUI.getResult();
       VData rVData = tBusinessDelegate.getResult();
//       Content = "保存失败，原因是:" + tEdorDetailUI.mErrors.getFirstError();
       Content = "保存失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError(); 
       FlagStr = "Fail";
 }
 else
 {
       Content = "保存成功";
       FlagStr = "Success";
  }

%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

