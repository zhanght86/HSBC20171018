<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�LLRptMAffixListSave.jsp
//�����ܣ�������֤��Ϣ����
//�������ڣ�2005-05-25 12:06
//������  ����־��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.workflow.tb.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
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
        loggerDebug("LLRptMAffixListSave","��¼��Ϣû�л�ȡ!!!");
        return;
     } 
     else if (tOperate == null || tOperate == "") 
     {
        loggerDebug("LLRptMAffixListSave","û�л�ȡ������!!!");
        return;
    }

    String tCaseNo=request.getParameter("RptNo");
    String tCustomerNo=request.getParameter("customerNo");

    //׼������������Ϣ
    LLReportAffixSet tLLReportAffixSet=new LLReportAffixSet(); //����������
     //����MulLine��������ݼ���   
     String tGridNo[] = request.getParameterValues("AffixGridNO");  //�õ�����е�����ֵ
     String tGrid1[] = request.getParameterValues("AffixGrid1"); //�õ���1�е�����ֵ
     String tGrid2[] = request.getParameterValues("AffixGrid2"); //�õ���2�е�����ֵ
     String tGrid3[] = request.getParameterValues("AffixGrid3"); //�õ���2�е�����ֵ
     String tGrid4[] = request.getParameterValues("AffixGrid4"); //�õ���2�е�����ֵ
     String tGrid5[] = request.getParameterValues("AffixGrid5"); //�õ���2�е�����ֵ

     String tChk[] = request.getParameterValues("InpAffixGridChk");; //������ʽ=�� Inp+MulLine������+Chk��
    if (tChk!=null&&tChk.length>0)
    {
	    for(int index=0;index<tChk.length;index++)
	    {
	      if(tChk[index].equals("1")) 
	      {          
		      LLReportAffixSchema tLLReportAffixSchema=new LLReportAffixSchema();
	          tLLReportAffixSchema.setRptNo(tCaseNo); //�ⰸ��
	          tLLReportAffixSchema.setCustomerNo(tCustomerNo); //�ͻ���   
	          tLLReportAffixSchema.setAffixCode(tGrid1[index]); //��֤����
	          tLLReportAffixSchema.setAffixName(tGrid2[index]); //��֤����
	          tLLReportAffixSchema.setNeedFlag(tGrid3[index]); //�Ƿ�����־           
	          tLLReportAffixSchema.setReadyCount(tGrid4[index]); //��֤����  
	          tLLReportAffixSchema.setProperty(tGrid5[index]); //��֤���Ա�־���ύ��ʽ1--ԭ����2--��ӡ����
	          tLLReportAffixSet.add(tLLReportAffixSchema);              
	      }
	    }          	
    }
         
    try
    {
        // ׼���������� VData
        VData tVData = new VData();
        tVData.add( tG );
        tVData.add( tOperate );
        tVData.add(tLLReportAffixSet);

//        LLMAffixListUI tLLMAffixListUI = new LLMAffixListUI();
//       if (tLLMAffixListUI.submitData(tVData,tOperate) == false)
//        {
//            int n = tLLMAffixListUI.mErrors.getErrorCount();
//            for (int i = 0; i < n; i++)
//            {
//                Content = Content + "��֤��Ϣ�������ԭ����: " + tLLMAffixListUI.mErrors.getError(i).errorMessage;
//                FlagStr = "FAIL";
//            }
//        } 
		String busiName="LLMAffixListUI";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		
		if(!tBusinessDelegate.submitData(tVData,tOperate,busiName))
		    {    
		        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		        { 
		        	int n = tBusinessDelegate.getCErrors().getErrorCount();
			        for (int i = 0; i < n; i++)
			        {
			            //loggerDebug("LLRptMAffixListSave","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
			            Content = " ��֤��Ϣ�������ԭ����: " + tBusinessDelegate.getCErrors().getError(i).errorMessage;
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
        loggerDebug("LLRptMAffixListSave","----------LLRptMAffixListSave.jsp End----------");
    
%>
<html>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
