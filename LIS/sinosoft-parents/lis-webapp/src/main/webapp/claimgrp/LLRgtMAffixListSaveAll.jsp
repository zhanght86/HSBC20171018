<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�LLRgtMAffixListSaveAll.jsp
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
<%@page import="com.sinosoft.lis.claimgrp.*"%>
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
        //loggerDebug("LLRgtMAffixListSaveAll","��¼��Ϣû�л�ȡ!!!");
        return;
     } 
     else if (tOperate == null || tOperate == "") 
     {
        //loggerDebug("LLRgtMAffixListSaveAll","û�л�ȡ������!!!");
        return;
    }
    String tClmNo=request.getParameter("ClmNo");
    loggerDebug("LLRgtMAffixListSaveAll",tClmNo);
    String tCustomerNo=request.getParameter("CustomerNo");


 String sql = "select customerno, affixno,affixcode,affixname,subflag, needflag ,readycount,shortcount, property , returnflag , affixconclusion ,affixreason from llaffix where  rgtno='"+ tClmNo + "' and (SubFlag is null or SubFlag='1')";
            ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sql);
			int m = tSSRS.getMaxRow();
    //׼������������Ϣ
        LLAffixSet tLLAffixSet=new LLAffixSet(); //������
    
  

    
	    for(int index=1;index<=m;index++)
	    {
	            
              LLAffixSchema tLLAffixSchema=new LLAffixSchema();
		      tLLAffixSchema.setRgtNo(tClmNo); //�ⰸ��
	          tLLAffixSchema.setCustomerNo(tSSRS.GetText(index, 1)); //�ͻ���   	 
		      tLLAffixSchema.setAffixNo(tSSRS.GetText(index, 2)); //��������
		      tLLAffixSchema.setAffixCode(tSSRS.GetText(index, 3)); //��֤����
		      tLLAffixSchema.setReadyCount(tSSRS.GetText(index, 7)); //��֤����
		      tLLAffixSchema.setShortCount(tSSRS.GetText(index, 8)); //ȱ�ټ���
		      tLLAffixSchema.setProperty(tSSRS.GetText(index, 9)); //�ύ��ʽ
		      tLLAffixSchema.setReturnFlag(tSSRS.GetText(index, 10)); //�Ƿ��˻�ԭ��
		      tLLAffixSchema.setAffixConclusion(tSSRS.GetText(index,11)); //��ȫ��־
		      tLLAffixSchema.setAffixReason(tSSRS.GetText(index, 12)); //����ȫԭ��
		      tLLAffixSchema.setSubFlag(tSSRS.GetText(index, 5)); //�Ƿ��ύ     
	          tLLAffixSet.add(tLLAffixSchema);              
	         	
    }
         
    try
    {
        // ׼���������� VData
        VData tVData = new VData();
        tVData.add( tG );
        tVData.add( tOperate );
        tVData.add(tLLAffixSet);

//        LLRgtMAffixListUI tLLRgtMAffixListUI = new LLRgtMAffixListUI();
//       if (tLLRgtMAffixListUI.submitData(tVData,tOperate) == false)
//        {
//            int n = tLLRgtMAffixListUI.mErrors.getErrorCount();
//            for (int i = 0; i < n; i++)
//            {
//                Content = Content + "��֤��Ϣ�������ԭ����: " + tLLRgtMAffixListUI.mErrors.getError(i).errorMessage;
//                FlagStr = "FAIL";
//            }
//        } 
String busiName="grpLLRgtMAffixListUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

if(!tBusinessDelegate.submitData(tVData,tOperate,busiName))
    {    
        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
        { 
        	int n = tBusinessDelegate.getCErrors().getErrorCount();
	        for (int i = 0; i < n; i++)
	        {
	            //loggerDebug("LLRgtMAffixListSaveAll","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
	            Content =  Content + "��֤��Ϣ�������ԭ����: " + tBusinessDelegate.getCErrors().getError(i).errorMessage;
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
parent.fraInterface.afterSubmit4("<%=FlagStr%>","<%=Content%>");
</script>
</html>    
