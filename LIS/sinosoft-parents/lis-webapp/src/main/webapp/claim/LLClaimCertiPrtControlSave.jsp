 <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�LLClaimCertiPrtControlSave.jsp
//�ļ����ܣ����ⵥ֤��ӡ����
//�������ڣ�2005-10-15  ������ ��                   
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
    //׼��ͨ�ò���
    CErrors tError = null;
    String FlagStr = "FAIL";
    String Content = "";
    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI");
    String tOperate = request.getParameter("Operate");
    if(tG == null)
    {
        //loggerDebug("LLClaimCertiPrtControlSave","��¼��Ϣû�л�ȡ!!!");
        return;
     } 
     //����MulLine��������ݼ���   
     LOPRTManagerSet tLOPRTManagerSet=new LOPRTManagerSet(); //��ӡ�����
     String tGridNo[] = request.getParameterValues("ClaimCertiGridNO");  //�õ�����е�����ֵ
     String tGrid1[] = request.getParameterValues("ClaimCertiGrid1"); //ӡˢ��ˮ��
     
     String tChk[] = request.getParameterValues("InpClaimCertiGridChk");; //������ʽ=�� Inp+MulLine������+Chk��
    if (tChk!=null&&tChk.length>0)
    {
	    for(int index=0;index<tChk.length;index++)
	    {
	      if(tChk[index].equals("1")) 
	      {          
              LOPRTManagerSchema tLOPRTManagerSchema=new LOPRTManagerSchema();
	          tLOPRTManagerSchema.setPrtSeq(tGrid1[index]); //��֤����
	          tLOPRTManagerSet.add(tLOPRTManagerSchema);              
	      }
	    } 	        
    }
         
    try
    {
        // ׼���������� VData
        VData tVData = new VData();
        tVData.add(tLOPRTManagerSet);

//        LLClaimCertiPrtControlUI tLLClaimCertiPrtControlUI = new LLClaimCertiPrtControlUI();
//       if (tLLClaimCertiPrtControlUI.submitData(tVData,tOperate) == false)
//        {
//			Content = "��Ϣ�ύ����ԭ����: " + tLLClaimCertiPrtControlUI.mErrors.getError(0).errorMessage;
//			FlagStr = "FAIL";
//        } 
String busiName="LLClaimCertiPrtControlUI";
   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
   if(!tBusinessDelegate.submitData(tVData,tOperate,busiName))
   {    
        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
        { 
				   Content = "��Ϣ�ύ����ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
				   FlagStr = "Fail";
		}
		else
		{
				   Content = "��Ϣ�ύ����";
				   FlagStr = "Fail";				
		}
   }

        else 
        {
            Content = " ����ɹ�! ";
            FlagStr = "SUCC";
        }

    }
    catch(Exception e)
    {
        e.printStackTrace();
        Content = Content.trim()+".��ʾ���쳣��ֹ!";
    }
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>    
