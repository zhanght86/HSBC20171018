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
        //loggerDebug("LLRgtMAffixListIssueSave","��¼��Ϣû�л�ȡ!!!");
        return;
     } 
     else if (tOperate == null || tOperate == "") 
     {
        //loggerDebug("LLRgtMAffixListIssueSave","û�л�ȡ������!!!");
        return;
    }
    String tClmNo=request.getParameter("ClmNo");
    String tReplyDate=request.getParameter("ReplyDate");


  //�������
	LLRegisterIssueSchema tLLRegisterIssueSchema = new LLRegisterIssueSchema(); //���������
	
	 tLLRegisterIssueSchema.setRgtNo(tClmNo); //�ⰸ��
	
    //׼������������Ϣ
        LLAffixSet tLLAffixSet=new LLAffixSet(); //������
     //����MulLine��������ݼ���   
     String tGridNo[] = request.getParameterValues("AffixGridNO");  //�õ�����е�����ֵ
     String tGrid1[] = request.getParameterValues("AffixGrid1"); //��������
     String tGrid2[] = request.getParameterValues("AffixGrid2"); //��֤����
     String tGrid4[] = request.getParameterValues("AffixGrid4"); //�Ƿ��ύ       
     String tGrid6[] = request.getParameterValues("AffixGrid6"); //��֤����
     String tGrid7[] = request.getParameterValues("AffixGrid7"); //ȱ�ټ���
     String tGrid8[] = request.getParameterValues("AffixGrid8"); //�ύ��ʽ
     String tGrid9[] = request.getParameterValues("AffixGrid9"); //�Ƿ��˻�ԭ��
     String tGrid10[] = request.getParameterValues("AffixGrid10"); //��ȫ��־
     String tGrid11[] = request.getParameterValues("AffixGrid11"); //����ȫԭ��
     String tGrid12[] = request.getParameterValues("AffixGrid12"); //����׶�
  

     String tChk[] = request.getParameterValues("InpAffixGridChk");; //������ʽ=�� Inp+MulLine������+Chk��
    if (tChk!=null&&tChk.length>0)
    {
	    for(int index=0;index<tChk.length;index++)
	    {
	      if(tChk[index].equals("1")) 
	      {          
              LLAffixSchema tLLAffixSchema=new LLAffixSchema();
		      tLLAffixSchema.setRgtNo(tClmNo); //�ⰸ��
	          tLLAffixSchema.setCustomerNo(tClmNo); //�ͻ���   	 
		      tLLAffixSchema.setAffixNo(tGrid1[index]); //��������
		      tLLAffixSchema.setAffixCode(tGrid2[index]); //��֤����
		      tLLAffixSchema.setReadyCount(tGrid6[index]); //��֤����
		      tLLAffixSchema.setShortCount(tGrid7[index]); //ȱ�ټ���
		      tLLAffixSchema.setProperty(tGrid8[index]); //�ύ��ʽ
		      tLLAffixSchema.setReturnFlag(tGrid9[index]); //�Ƿ��˻�ԭ��
		      tLLAffixSchema.setAffixConclusion(tGrid10[index]); //��ȫ��־
		      tLLAffixSchema.setAffixReason(tGrid11[index]); //����ȫԭ��
		      tLLAffixSchema.setSubFlag(tGrid4[index]); //�Ƿ��ύ     
		      tLLAffixSchema.setSupplyStage(tGrid12[index]); //����׶�
		      tLLAffixSchema.setReAffixDate(tReplyDate); //���������������
		      tLLAffixSchema.setAffixState("03"); //�����׶�
	          tLLAffixSet.add(tLLAffixSchema);              
	      }
	    }          	
    }
         
    try
    {
        // ׼���������� VData
        VData tVData = new VData();
        tVData.add( tG );
        tVData.add( tOperate );
        tVData.add(tLLAffixSet);
        tVData.add(tLLRegisterIssueSchema);

//        LLRgtMAffixListIssueUI tLLRgtMAffixListIssueUI = new LLRgtMAffixListIssueUI();
//       if (tLLRgtMAffixListIssueUI.submitData(tVData,tOperate) == false)
//        {
//            int n = tLLRgtMAffixListIssueUI.mErrors.getErrorCount();
//            for (int i = 0; i < n; i++)
//            {
//                Content = Content + "��֤��Ϣ�������ԭ����: " + tLLRgtMAffixListIssueUI.mErrors.getError(i).errorMessage;
//                FlagStr = "FAIL";
//            }
//        } 
String busiName="LLRgtMAffixListIssueUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

if(!tBusinessDelegate.submitData(tVData,tOperate,busiName))
    {    
        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
        { 
        	int n = tBusinessDelegate.getCErrors().getErrorCount();
	        for (int i = 0; i < n; i++)
	        {
	            //loggerDebug("LLRgtMAffixListIssueSave","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
	            Content = Content + "��֤��Ϣ�������ԭ����: "+ tBusinessDelegate.getCErrors().getError(i).errorMessage;
	        }
       		FlagStr = "Fail";				   
		}
		else
		{
		   Content = "��֤��Ϣ�������";
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
