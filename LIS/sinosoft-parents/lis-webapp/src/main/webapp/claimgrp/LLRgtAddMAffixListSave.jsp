<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�LLRgtAddMAffixListSave.jsp
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
        //loggerDebug("LLRgtAddMAffixListSave","��¼��Ϣû�л�ȡ!!!");
        return;
     } 
     else if (tOperate == null || tOperate == "") 
     {
        //loggerDebug("LLRgtAddMAffixListSave","û�л�ȡ������!!!");
        return;
    }
    String tClmNo=request.getParameter("ClmNo");
    String tCustomerNo=request.getParameter("CustomerNo");

    //׼������������Ϣ

     TransferData tTransferData = new TransferData();
	 tTransferData.setNameAndValue("RgtNo",tClmNo);
	 tTransferData.setNameAndValue("CustomerNo",tCustomerNo);
	 
     //����MulLine��������ݼ���   
     LLAffixSet tLLAffixSet=new LLAffixSet(); //������
     String tGridNo[] = request.getParameterValues("AffixGridNO");  //�õ�����е�����ֵ
     String tGrid1[] = request.getParameterValues("AffixGrid1"); //��֤����
     String tGrid2[] = request.getParameterValues("AffixGrid2"); //��֤����     
     String tGrid3[] = request.getParameterValues("AffixGrid3"); //�Ƿ����ύ
     String tGrid4[] = request.getParameterValues("AffixGrid4"); //�Ƿ����
     String tGrid5[] = request.getParameterValues("AffixGrid5"); //��֤����
     String tGrid6[] = request.getParameterValues("AffixGrid6"); //�ύ��ʽ
     String tGrid7[] = request.getParameterValues("AffixGrid7"); //ȱ�ټ���    
     String tGrid8[] = request.getParameterValues("AffixGrid8"); //�Ƿ��˻�ԭ��
     String tGrid9[] = request.getParameterValues("AffixGrid9"); //��������
     
     String tChk[] = request.getParameterValues("InpAffixGridChk");; //������ʽ=�� Inp+MulLine������+Chk��
    if (tChk!=null&&tChk.length>0)
    {
	    for(int index=0;index<tChk.length;index++)
	    {
	      if(tChk[index].equals("1")) 
	      {          
              LLAffixSchema tLLAffixSchema=new LLAffixSchema();
		      tLLAffixSchema.setRgtNo(tClmNo); //�ⰸ��
		      tLLAffixSchema.setCustomerNo(tCustomerNo); //�ͻ��� 
	          tLLAffixSchema.setAffixCode(tGrid1[index]); //��֤����
	          tLLAffixSchema.setAffixName(tGrid2[index]); //��֤����
	          tLLAffixSchema.setSubFlag(tGrid3[index]); //�Ƿ����ύ��־	          	          
	          tLLAffixSchema.setNeedFlag(tGrid4[index]); //�Ƿ�����־
	          tLLAffixSchema.setReadyCount(tGrid5[index]); //��֤����	          
	          tLLAffixSchema.setProperty(tGrid6[index]); //��֤���Ա�־
             //ȱ�ټ���  
	          tLLAffixSchema.setReturnFlag(tGrid8[index]); //�Ƿ��˻�ԭ����־
		      tLLAffixSchema.setAffixNo(tGrid9[index]); //��������
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
        tVData.add( tTransferData );
        tVData.add(tLLAffixSet);

//        LLRgtAddMAffixListUI tLLRgtAddMAffixListUI = new LLRgtAddMAffixListUI();
//       if (tLLRgtAddMAffixListUI.submitData(tVData,tOperate) == false)
//        {
//            int n = tLLRgtAddMAffixListUI.mErrors.getErrorCount();
//            for (int i = 0; i < n; i++)
//            {
//                Content = Content + "��֤��Ϣ�������ԭ����: " + tLLRgtAddMAffixListUI.mErrors.getError(i).errorMessage;
//                FlagStr = "FAIL";
//            }
//        } 
String busiName="grpLLRgtAddMAffixListUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

if(!tBusinessDelegate.submitData(tVData,tOperate,busiName))
    {    
        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
        { 
        	int n = tBusinessDelegate.getCErrors().getErrorCount();
	        for (int i = 0; i < n; i++)
	        {
	            //loggerDebug("LLRgtAddMAffixListSave","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
	            Content = Content + "��֤��Ϣ�������ԭ����: " + tBusinessDelegate.getCErrors().getError(i).errorMessage;
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
