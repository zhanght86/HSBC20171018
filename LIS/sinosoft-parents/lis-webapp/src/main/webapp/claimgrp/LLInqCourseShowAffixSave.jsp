<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�DataIntoLACommisionSave.jsp
//�����ܣ�
//�������ڣ�2003-06-24
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
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
  // �������
  CErrors tError = null;
  String FlagStr = "";
  String Content = "";

  GlobalInput tGI = new GlobalInput(); //repair:
  tGI=(GlobalInput)session.getValue("GI");  //�μ�loginSubmit.jsp

  if(tGI==null)
  {
    loggerDebug("LLInqCourseShowAffixSave","ҳ��ʧЧ,�����µ�½");
    FlagStr = "Fail";
    Content = "ҳ��ʧЧ,�����µ�½";
  }
  else //ҳ����Ч
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
    
   
         //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
 
  try
  { 
//   if(!tLLInqCourseAffixDoBL.submitData(tVData,"SHOW"))
//   {
//      Content = " ��ѯʧ�ܣ�ԭ����: " + tLLInqCourseAffixDoBL.mErrors.getError(0).errorMessage;
//      FlagStr = "Fail";
//   }
if(!tBusinessDelegate.submitData(tVData,"SHOW",busiName))
{    
    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
    { 
		Content = "��ѯʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
		FlagStr = "Fail";
	}
	else
	{
		Content = "��ѯʧ��";
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
                    parent.fraInterface.divlink.innerHTML="��    ����    <a/ href='<%=url%>'><%=nLLSubAffixSchema.getAffixName()%></a>";
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
    Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
    FlagStr = "Fail";
  }
 } 
    %>
<html>
<script language="javascript">
       parent.fraInterface.afterSubmit('<%=FlagStr%>','<%=Content%>');
</script>
</html>
