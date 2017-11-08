<%
//程序名称：PEdorTypeMRSubmit.jsp
//程序功能：
//创建日期：2005-04-13 16:49:22
//创建人  ：liuzhao
//更新记录：  更新人    更新日期     更新原因/内容
// modify by LH 2008-10-30
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.bq.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
    //接收信息，并作校验处理
    System.out.println("-----Detail submit---");                 
    String FlagStr = "";
    String Content = "";
    String transact = "";
    GlobalInput tG = new GlobalInput();
    tG = (GlobalInput)session.getValue("GI");
    transact=(String)request.getParameter("fmtransact");
    CErrors tErrors = new CErrors();          
    LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema(); 
    //保存个人保单信息(保全)	
     tLPEdorItemSchema.setPolNo("000000");
	 tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
	 tLPEdorItemSchema.setInsuredNo("000000");
	 tLPEdorItemSchema.setEdorAppDate(request.getParameter("EdorAppDate"));
     tLPEdorItemSchema.setEdorValiDate(request.getParameter("EdorValiDate"));
	 tLPEdorItemSchema.setEdorReason(request.getParameter("AppReason"));
	 tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
     tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
	 tLPEdorItemSchema.setEdorType(request.getParameter("EdorType")); 
	 tLPEdorItemSchema.setGetMoney(request.getParameter("AddPrem")); //追加保费金额
    // 准备传输数据 VData
    VData tVData = new VData();  
    tVData.addElement(tG);	 	
    tVData.addElement(tLPEdorItemSchema);	
    //删除记录不需要经过校验规则
    if(transact.equals("EDORITEM|DELETE"))
    {
        try
        {
            PEdorWPDetailBL tPEdorWPDetailBL = new PEdorWPDetailBL();
           if (!tPEdorWPDetailBL.submitData(tVData,transact))
           {
               tErrors.copyAllErrors(tPEdorWPDetailBL.mErrors);
           }
        }catch(Exception ex)
        {
            FlagStr = "Fail";
            Content = "操作失败，原因是:";
        }
    }
    else
    {
       
       try
        {
    	   PEdorWPDetailBL tPEdorWPDetailBL = new PEdorWPDetailBL();
           if (!tPEdorWPDetailBL.submitData(tVData,""))
           {
               tErrors.copyAllErrors(tPEdorWPDetailBL.mErrors);
           }
        }catch(Exception ex)
        {
            FlagStr = "Fail";
            Content = "操作失败，原因是:";
        }
    }
  //页面反馈信息
  if (!tErrors.needDealError())
  {
      FlagStr = "Success";
      Content = "操作成功！";
  }
  else
  {
      FlagStr = "Fail";
      Content = "操作失败，原因是：" + tErrors.getLastError();
  }
  tErrors = null;
  
%>        
              
<html>
<head>
    <script language="JavaScript">
        try
        {
            parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');
        }
        catch (ex)
        {
            alert('<%=Content%>');
        }
    </script>
</html>
