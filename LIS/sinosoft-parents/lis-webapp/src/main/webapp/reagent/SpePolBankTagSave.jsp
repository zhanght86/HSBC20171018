<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：LRAscriptionSave.jsp
//程序功能：
//创建日期：2002-08-16 15:12:33
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.wagecal.*"%>
  <%@page import="com.sinosoft.lis.reagent.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*"%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
    //接收信息，并作校验处理。
    //输入参数
    LJSPaySchema tLJSPaySchema;
    LJSPaySet tLJSPaySet = new LJSPaySet();
    
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		
    
    
        
    //SpePolBankTagUI tSpePolBankTagUI  = new SpePolBankTagUI();

    //输出参数
    CErrors tError = null;
    String tOperate="INSERT||MAIN";
    String tRela  = "";
    String FlagStr = "Fail";
    String Content = "";

    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI");

    loggerDebug("SpePolBankTagSave","begin tSpePolBankTagUI schema...");
    //取得授权信息
    int lineCount = 0;
    String tChk[] = request.getParameterValues("InpAscriptionGridChk");
    loggerDebug("SpePolBankTagSave","22@@"+tChk.length);
    int number = 0; 
    for(int index=0;index<tChk.length;index++) {
        if(tChk[index].equals("1"))           
           number++;
    }
    loggerDebug("SpePolBankTagSave","number"+number);    
    if(number==0) {
        Content = " 失败，原因:没有选择要调整的保单！";
       	FlagStr = "Fail";		
	  }else {
	      String tContNo[] = request.getParameterValues("AscriptionGrid3");      
	      lineCount = tChk.length; //行数 
		
	      for(int i=0;i<lineCount;i++) {	        
	         if(tChk[i].trim().equals("1"))
	       {  
	           loggerDebug("SpePolBankTagSave","*********"+tContNo[i].trim());  
              loggerDebug("SpePolBankTagSave","###"+i);
              tLJSPaySchema = new LJSPaySchema();
              tLJSPaySchema.setOtherNo(tContNo[i]);
              tLJSPaySet.add(tLJSPaySchema);
	         } 
	      } 
	      loggerDebug("SpePolBankTagSave","end 归属信息...");
	  
	      // 准备传输数据 VData
        VData tVData = new VData();
        
        FlagStr=""; 
        tVData.addElement(tLJSPaySet); 
        tVData.add(tG);  
        boolean t=false;
        try { 
            
           //t = tSpePolBankTagUI.submitData(tVData,tOperate); 
             t = tBusinessDelegate.submitData(tVData,tOperate,"SpePolBankTagUI");
           loggerDebug("SpePolBankTagSave","weikai 测试！！！");
        } catch(Exception ex) {
           Content = "保存失败，原因是:" + ex.toString();
           FlagStr = "Fail";
        } 
        if (!FlagStr.equals("Fail")) {
           //tError = tSpePolBankTagUI.mErrors;
           tError =  tBusinessDelegate.getCErrors(); 
           if( t )
           {
               if ( !tError.needDealError()) 
               {
        	        Content = " 保存成功! ";
        	        FlagStr = "Succ";
               }
               else
               {
                   Content = "注意个别保单没有处理成功，原因如： " +tError.getFirstError();
                   FlagStr="Succ";
               }
           }
           else 
           {
    	        Content = " 保存失败，原因是:" + tError.getFirstError();
    	        FlagStr = "Fail";
           } 
        } 
	  }
    
  
  
  //添加各种预处理

%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

