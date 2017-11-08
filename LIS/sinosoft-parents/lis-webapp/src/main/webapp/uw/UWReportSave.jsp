<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//WorkFlowNotePadSave.jsp
//程序功能：
//创建日期：2005-04-22 14:49:52
//创建人  ：zhangtao
//更新记录：  更新人    更新日期     更新原因/内容
//      
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%

  //输出参数
  String FlagStr = "";
  String Content = ""; 
   
  CErrors tError = null;
  
  GlobalInput tG = new GlobalInput(); 
  tG = (GlobalInput) session.getValue("GI");
  VData tVData = new VData();
  
	String strReportCont = request.getParameter("Content");
    String strContNo = request.getParameter("ContNo");
    String strNoType = request.getParameter("NoType");
    String strOperatePos = request.getParameter("OperatePos");
    // 修复部分地方传值为"null"字符串引发问题
    if("null".equals(strOperatePos)){
    	strOperatePos = "";
    }
    String strBussFlagSave = request.getParameter("BussFlagSave");
  	String strClmNo = request.getParameter("ClmNo");
    
    
    LCUWReportSchema tLCUWReportSchema = new LCUWReportSchema();
    if(strClmNo==null||"".equals(strClmNo)||"null".equals(strClmNo)){
    	tLCUWReportSchema.setOtherNo(strContNo);
    }else{
    	tLCUWReportSchema.setOtherNo(strClmNo);
    }
    tLCUWReportSchema.setOtherNoType(strNoType); 
    tLCUWReportSchema.setOperatePos(strOperatePos);
    tLCUWReportSchema.setReportCont(strReportCont);
    
    LCContSchema tLCContSchema = new LCContSchema();
    tLCContSchema.setContNo(strContNo);
    tLCContSchema.setBussFlag(strBussFlagSave); //2009-1-7 ln add --商业因素标记
     
    //UWReportInputUI tUWReportInputUI = new UWReportInputUI();
    
	try
	{	
		tVData.add(tG);		
		tVData.add(tLCUWReportSchema);   
		tVData.add(tLCContSchema); 
		//tUWReportInputUI.submitData(tVData, "REPORT|INSERT");
		String busiName="tbUWReportInputUI";
	  	String mDescType="保存";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		  if(!tBusinessDelegate.submitData(tVData,"REPORT|INSERT",busiName))
		  {    
		       if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		       { 
					Content = mDescType+"失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
					FlagStr = "Fail";
			   }
			   else
			   {
					Content = mDescType+"失败";
					FlagStr = "Fail";				
			   }
		  }
		  else
		  {
		     	Content = mDescType+"成功! ";
		      	FlagStr = "Succ";  
		  }
	}
	catch(Exception ex)
	{
	      Content = "保存失败，原因是:" + ex.toString();
	      loggerDebug("UWReportSave","aaaa"+ex.toString());
	      FlagStr = "Fail";
	}
	/*if ("".equals(FlagStr))
	{
		    tError = tUWReportInputUI.mErrors;
		    if (!tError.needDealError())
		    {                          
		      Content ="保存成功！";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	Content = "保存失败，原因是:" + tError.getFirstError();
		    	FlagStr = "Fail";
		    }
	}  */ 
  %>
   
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
   
   
   
 
