<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�LLSecondManuUWChk.jsp
//�����ܣ���������˹��˱�-----��ͬ�򱣵��˱����������ύ
//�������ڣ�2005-01-19 11:10:36
//������  ��zhangxing
//���¼�¼��  ������ yuejw   ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
    //׼��ͨ�ò���
    CErrors tError = null;
    String FlagStr = "FAIL";
    String Content = "";
    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI");
    String transact = request.getParameter("fmtransact");    
    if(tG == null) 
    {
        loggerDebug("LLSecondManuUWSave","��¼��Ϣû�л�ȡ!!!");
        return;
     }
    LLCUWMasterSchema tLLCUWMasterSchema = new LLCUWMasterSchema();
	tLLCUWMasterSchema.setCaseNo(request.getParameter("tCaseNo"));
	tLLCUWMasterSchema.setBatNo(request.getParameter("tBatNo"));
	tLLCUWMasterSchema.setContNo(request.getParameter("tContNo"));	
	tLLCUWMasterSchema.setPassFlag(request.getParameter("uwState"));
	tLLCUWMasterSchema.setUWIdea(request.getParameter("UWIdea"));
	tLLCUWMasterSchema.setClaimRelFlag(request.getParameter("tClaimRelFlag"));	
	
	
	try
	{
        // ׼���������� VData
		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(transact);
		tVData.add(tLLCUWMasterSchema);
       /* LLSecondManuUWUI tLLSecondManuUWUI = new LLSecondManuUWUI();
       if (tLLSecondManuUWUI.submitData(tVData,transact) == false)
        {
          Content = Content + "��Ϣ����ʧ�ܣ�ԭ����: " + tLLSecondManuUWUI.mErrors.getError(0).errorMessage;
          FlagStr = "FAIL";
        } 
        else 
        {
            Content = " ����ɹ�! ";
            FlagStr = "SUCC";
        }		*/
        String busiName="LLSecondManuUWUI";
      	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
      	  if(!tBusinessDelegate.submitData(tVData,transact,busiName))
      	  {    
      	       if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
      	       { 
      				Content = "��Ϣ����ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
      				FlagStr = "FAIL";
      		   }
      		   else
      		   {
      				Content = "��Ϣ����ʧ��";
      				FlagStr = "FAIL";				
      		   }
      	  }
      	  else
      	  {
      	     	Content = "����ɹ�! ";
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
