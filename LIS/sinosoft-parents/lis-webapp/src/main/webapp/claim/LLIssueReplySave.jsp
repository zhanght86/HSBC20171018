<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�LLRgtMAffixListSave.jsp
//�����ܣ���֤����
//�������ڣ�2005-05-25 12:06
//������  ��yuejw
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.workflow.tb.*"%>
<%@page import="com.sinosoft.lis.cbcheck.*"%>
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
        //loggerDebug("LLIssueReplySave","��¼��Ϣû�л�ȡ!!!");
        return;
     } 
     else if (tOperate == null || tOperate == "") 
     {
        //loggerDebug("LLIssueReplySave","û�л�ȡ������!!!");
        return;
    }
    String tClmNo=request.getParameter("ClmNo");
    String tAutditno=request.getParameter("Autditno");
    String tIssueReplyConclusion=request.getParameter("IssueReplyConclusion");


   //�������
   LLRegisterIssueSchema tLLRegisterIssueSchema = new LLRegisterIssueSchema(); //���������
	
	 tLLRegisterIssueSchema.setRgtNo(tClmNo); //�ⰸ��
	 tLLRegisterIssueSchema.setAutditNo(tAutditno);
	 tLLRegisterIssueSchema.setIssueReplyConclusion(tIssueReplyConclusion); //�������������
	
         
    try
    {
        // ׼���������� VData
        VData tVData = new VData();
        tVData.add( tG );
        tVData.add( tOperate );
        tVData.add(tLLRegisterIssueSchema);

//        LLIssueReplyUI tLLIssueReplyUI = new LLIssueReplyUI();
//       if (tLLIssueReplyUI.submitData(tVData,tOperate) == false)
//        {
//            int n = tLLIssueReplyUI.mErrors.getErrorCount();
//            for (int i = 0; i < n; i++)
//            {
//                Content = Content + "��֤��Ϣ�������ԭ����: " + tLLIssueReplyUI.mErrors.getError(i).errorMessage;
//                FlagStr = "FAIL";
//            }
//        }
         String busiName="LLIssueReplyUI";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		
		if(!tBusinessDelegate.submitData(tVData,tOperate,busiName))
		    {    
		        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		        { 
		        	int n = tBusinessDelegate.getCErrors().getErrorCount();
			        for (int i = 0; i < n; i++)
			        {
			            //loggerDebug("LLIssueReplySave","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
			            Content = Content + "��֤��Ϣ�������ԭ����: " +tBusinessDelegate.getCErrors().getError(i).errorMessage;
			        }
		       		FlagStr = "Fail";				   
				}
				else
				{
				   Content = "����ʧ��";
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
