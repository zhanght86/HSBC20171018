<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：DataIntoLACommisionSave.jsp
//程序功能：
//创建日期：2003-06-24
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="java.lang.*"%>
  <%@page import="java.util.*"%>
  <%@page import="java.text.SimpleDateFormat"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.claimgrp.*"%>
  <%@page import="com.sinosoft.service.*" %>
    <SCRIPT src="LLInqCourseShowAffix.js"></SCRIPT>
<%
  // 输出参数
  CErrors tError = null;
  String FlagStr = "";
  String Content = "";

  GlobalInput tGI = new GlobalInput(); //repair:
  tGI=(GlobalInput)session.getValue("GI");  //参见loginSubmit.jsp

  if(tGI==null)
  {
    loggerDebug("LLInqCourseShowAffixSave","页面失效,请重新登陆");
    FlagStr = "Fail";
    Content = "页面失效,请重新登陆";
  }
  else //页面有效
  {
    String ClmNo = request.getParameter("ClmNo");
    loggerDebug("LLInqCourseShowAffixSave","\\\\\\\\"+ClmNo);
    LLInqApplySchema tLLInqApplySchema = new LLInqApplySchema();
    tLLInqApplySchema.setClmNo(ClmNo);
  
    VData tVData = new VData();
    tVData.addElement(tGI);
    tVData.addElement(tLLInqApplySchema);

    //LLInqCourseAffixDoBL tLLInqCourseAffixDoBL = new LLInqCourseAffixDoBL();
    String busiName="grpLLInqCourseAffixDoBL";
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    
   
         //如果在Catch中发现异常，则不从错误类中提取错误信息
 
  try
  { 
//   if(!tLLInqCourseAffixDoBL.submitData(tVData,"SHOW"))
//   {
//      Content = " 查询失败，原因是: " + tLLInqCourseAffixDoBL.mErrors.getError(0).errorMessage;
//      FlagStr = "Fail";
//   }
if(!tBusinessDelegate.submitData(tVData,"SHOW",busiName))
{    
    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
    { 
		Content = "查询失败，原因是:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
		FlagStr = "Fail";
	}
	else
	{
		Content = "查询失败";
		FlagStr = "Fail";				
	}
}

   else 
   {
      tVData.clear();
      LLSubAffixSet tLLSubAffixSet=new LLSubAffixSet();
      LLSubAffixSchema nLLSubAffixSchema = new LLSubAffixSchema();
      //tVData = tLLInqCourseAffixDoBL.getResult();
      tVData = tBusinessDelegate.getResult();
      tLLSubAffixSet.set((LLSubAffixSet)tVData.getObjectByObjectName("LLSubAffixSet",0));
      loggerDebug("LLInqCourseShowAffixSave","-----------"+tLLSubAffixSet.size()+"-----------------");
      
      if(tLLSubAffixSet.size() > 0)
      {
            nLLSubAffixSchema=tLLSubAffixSet.get(1);
             loggerDebug("LLInqCourseShowAffixSave","-----------"+nLLSubAffixSchema.getAffix()+"-----------------");
             loggerDebug("LLInqCourseShowAffixSave","-----------"+nLLSubAffixSchema.getAffixName()+"-----------------");
            if(nLLSubAffixSchema.getAffix()!=null)
            {
                  String url=request.getHeader("referer").substring(0,request.getHeader("referer").indexOf("/claim"))+"/HRAffix/"+nLLSubAffixSchema.getAffix();
                  loggerDebug("LLInqCourseShowAffixSave",url);
                  //loggerDebug("LLInqCourseShowAffixSave",request.getHeader("referer"));
                  //loggerDebug("LLInqCourseShowAffixSave",application.getRealPath(""));
                  %>
                  <script language="javascript">
                     //showDiv(parent.fraInterface.divlink,"true");
                    parent.fraInterface.divlink.innerHTML="附    件：    <a/ href='<%=url%>'><%=nLLSubAffixSchema.getAffixName()%></a>";
                    parent.fraInterface.divlink.style.display="";
                    
                  </script>
                   <%            
            }
       }
       else
       {
            
       }
     }
  }
  catch(Exception ex)
  {
    Content = "保存失败，原因是:" + ex.toString();
    FlagStr = "Fail";
  }
 } 
    %>
<html>
<script language="javascript">
       parent.fraInterface.afterSubmit('<%=FlagStr%>','<%=Content%>');
</script>
</html>
