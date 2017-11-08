<%
//程序名称：CountPrintSave.jsp
//程序功能：
//创建日期：2003-10-25
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
   <%@page import="com.sinosoft.service.*" %>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%!
	InputStream ins = null;
	LCPolSet tLCPolSet = null;
	String handleFunction(HttpSession session, HttpServletRequest request) {
	int nIndex = 0;
	String tLCPolGrids[] = request.getParameterValues("PolGridNo");
	String tLCPolNos[] = request.getParameterValues("PolGrid1");	
	String tRadio[] = request.getParameterValues("InpPolGridSel");  

	GlobalInput globalInput = new GlobalInput();
	
	if( (GlobalInput)session.getValue("GI") == null ) {
		return "网页超时或者是没有操作员信息，请重新登录";
	} else {
		globalInput.setSchema((GlobalInput)session.getValue("GI"));
	}
	
	
		
	if( tLCPolGrids == null ) {
		return "没有输入需要的打印参数";
	}
	tLCPolSet = null;
	tLCPolSet = new LCPolSet();
//	tLCPolSet.clear();

	//LCPolF1PUI tLCPolF1PUI = new LCPolF1PUI();
	
	 String busiName="f1printLCPolF1PUI";
	   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	
	
	for(nIndex = 0; nIndex < tRadio.length; nIndex++ )
        {
		// If this line isn't selected, continue
		loggerDebug("CountPrintSave",tRadio[nIndex]);
		if( tRadio[nIndex].equals("0") ) {
		  continue;
		}
		
		if( tLCPolNos[nIndex] == null || tLCPolNos[nIndex].equals("") ) {
		  return "请输入保单号的信息";
		}

		LCPolSchema tLCPolSchema = new LCPolSchema();
		loggerDebug("CountPrintSave","保单号："+tLCPolNos[nIndex]);
		tLCPolSchema.setPolNo( tLCPolNos[nIndex] );

		tLCPolSet.add(tLCPolSchema);
	}
	
	
	
	VData vData = new VData();
	vData.addElement(tLCPolSet);
	vData.add(globalInput);
	
	VData mResult=new VData();
	try
        {
		if( !tBusinessDelegate.submitData(vData, "PRINTEX",busiName) )
	        {
	   	      if ( tBusinessDelegate.getCErrors().needDealError() )
	   	      {
	   		  return tBusinessDelegate.getCErrors().getFirstError();
		      } 
		      else
		      {
		  	  return "保存失败，但是没有详细的原因";
		      }
		      
		      
		} else {
			// mResult = tLCPolF1PUI.getResult();
			ins = (InputStream)(tBusinessDelegate.getResult().get(0));
		}
	} 
	catch (Exception ex)
         {
		ex.printStackTrace();
		return ex.getMessage();
	}
	
      
       return ""; 	  
  }
%>
<%  
	String Content = "";
	String FlagStr = "";
	
	//String result = "";
	
	Content = handleFunction(session, request);
	
	loggerDebug("CountPrintSave","Err : ");
	loggerDebug("CountPrintSave",Content);
	
	// InputStream ins =(InputStream)mResult.get(0);
	if (ins==null)
        {
	  loggerDebug("CountPrintSave","null");
	  FlagStr = "Fail";
	  //result="没有相关的打印数据";
	}else{
	session.setAttribute("PrintStream",ins);
	session.putValue("PolNo_PrintEx",tLCPolSet.get(1).getPolNo());
	
	//add by yt 20040426,出现简易投保单重复打印的问题,此处必须对PrintNo设置为空，否则可能出现其他打印页面中的Session没有清空，导致后面的operPrintTable.jsp的判断出现问题。
	//session.putValue("PrintNo",null );
	//add by Minim at 2004-05-27, PrintNo这个session值被多个页面重复使用，很容易造成冲突
	session.putValue("PrintNoEx", "EX");
	//柜面打印增加回收套打单证功能，save页面增加初入标识EasyPrintFlag=1
	session.putValue("EasyPrintFlag","1");
	FlagStr = "Succ";
	//result = "操作成功完成";
	response.sendRedirect("GetF1Print.jsp");
	}
%>

<html>
<script language="javascript">
//	if("<%=FlagStr%>"=="Fail"){
//	parent.fraInterface.afterSubmit('<%=FlagStr%>','<%=Content%>');
//}
alert('<%=Content%>');
window.close();
</script>
</html>
       
                     

