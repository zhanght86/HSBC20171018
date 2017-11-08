<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�LLMAffixListSave.jsp
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
<%@page import="com.sinosoft.lis.cbcheck.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>
<%@page import="java.util.*"%>

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
        loggerDebug("LLMAffixListSave","��¼��Ϣû�л�ȡ!!!");
        return;
     } else if (tOperate == null || tOperate == "") {
        loggerDebug("LLMAffixListSave","û�л�ȡ������!!!");
        return;
    }

    String tCaseNo=request.getParameter("CaseNo");
    String tCustomerNo=request.getParameter("whoNo");
    int tCols=Integer.parseInt(request.getParameter("cols"));
    String[] tAffixGridNo=request.getParameterValues("AffixGridNo");
    int tRows=tAffixGridNo.length;

    Vector tRowsChk=new Vector();
    String tChk[] = request.getParameterValues("InpAffixGridChk");
    if (tChk!=null&&tChk.length>0){
      for(int index=0;index<tChk.length;index++){
        if(tChk[index].equals("1")){
	  tRowsChk.add(new Integer(index));
    	}
      }

      loggerDebug("LLMAffixListSave","Cols:"+tCols+"||Rows:"+tRowsChk.size());

      //�õ�ǰ̨ѡ����Ŀ���������
      String[] tAffixGridColValue=new String[tCols];
      String[][] tAffixGridValues=new String[tRows][tCols];
      for (int i=1;i<=tCols;i++){
        tAffixGridColValue=request.getParameterValues("AffixGrid"+i);
        for(int j=0;j<tRows;j++){
      	  if (tRowsChk.indexOf(new Integer(j))!=-1){
            tAffixGridValues[j][i-1]=StrTool.unicodeToGBK(tAffixGridColValue[j]);
            loggerDebug("LLMAffixListSave","Col:"+i+";Row:"+j+";Value:"+StrTool.unicodeToGBK(tAffixGridColValue[j]));
      	  }
        }
      }
    }

    //׼������������Ϣ
    TransferData tTransferData = new TransferData();

    LLReportAffixSet tLLReportAffixSet=new LLReportAffixSet();

    LLAffixSet tLLAffixSet=new LLAffixSet();

    int tRow=0;
    //LLFeeMainSchema tLLFeeMainSchema = new LLFeeMainSchema();
    //LLCaseReceiptSchema tLLCaseReceiptSchema = new LLCaseReceiptSchema();

    //��֤��Ϣ

    if (tOperate.equals("Rpt||INSERT"))		//�����׶����ɵ�֤
    {
	//׼����̨����
	for(int i=0;i<tRowsChk.size();i++){
	   LLReportAffixSchema tLLReportAffixSchema=new LLReportAffixSchema();
          tLLReportAffixSchema.setRptNo(tCaseNo); //�ⰸ��
          tLLReportAffixSchema.setCustomerNo(tCustomerNo); //�ͻ���
          tRow=((Integer)tRowsChk.get(i)).intValue();
          tLLReportAffixSchema.setAffixNo(tAffixGridValues[tRow][8]); //��֤����
          tLLReportAffixSchema.setAffixCode(tAffixGridValues[tRow][0]); //��֤����
          tLLReportAffixSchema.setNeedFlag(tAffixGridValues[tRow][3]); //�Ƿ�����־
          tLLReportAffixSchema.setProperty(tAffixGridValues[tRow][6]); //��֤���Ա�־
          tLLReportAffixSchema.setAffixName(tAffixGridValues[tRow][1]); //��֤����
          tLLReportAffixSchema.setReadyCount(tAffixGridValues[tRow][4]); //��֤����
          tLLReportAffixSet.add(tLLReportAffixSchema);
	}
	tTransferData.setNameAndValue("RptNo",tCaseNo);

    }
    else if (tOperate.equals("Rgt||UPDATE"))	//�����׶ε�֤����
    {
	//׼����̨����
	for(int i=0;i<tRows;i++){
	   LLAffixSchema tLLAffixSchema=new LLAffixSchema();
          tLLAffixSchema.setRgtNo(tCaseNo); //�ⰸ��
          tLLAffixSchema.setCustomerNo(tCustomerNo); //�ͻ���
          tLLAffixSchema.setAffixNo(tAffixGridValues[i][8]); //��֤����
          tLLAffixSchema.setAffixCode(tAffixGridValues[i][0]); //��֤����
          tLLAffixSchema.setSubFlag(tAffixGridValues[i][2]); //�Ƿ����ύ��־
          tLLAffixSchema.setReturnFlag(tAffixGridValues[i][7]); //�Ƿ��˻�ԭ����־
          /*tLLAffixSchema.setAffixName(tAffixGridValues[tRow][1]); //��֤����
          tLLAffixSchema.setCount(tAffixGridValues[tRow][3]); //��֤����*/
          tLLAffixSet.add(tLLAffixSchema);
	}
	tTransferData.setNameAndValue("RgtNo",tCaseNo);
    }
    else if (tOperate.equals("RgtAdd||INSERT"))	//�����׶β��䵥֤
    {
	//׼����̨����
	for(int i=0;i<tRowsChk.size();i++){
	   LLAffixSchema tLLAffixSchema=new LLAffixSchema();
          tLLAffixSchema.setRptNo(tCaseNo); //�ⰸ��
          tLLAffixSchema.setCustomerNo(tCustomerNo); //�ͻ���
          tRow=((Integer)tRowsChk.get(i)).intValue();
          tLLAffixSchema.setAffixNo(tAffixGridValues[tRow][8]); //��֤����
          tLLAffixSchema.setAffixCode(tAffixGridValues[tRow][0]); //��֤����
          tLLAffixSchema.setNeedFlag(tAffixGridValues[tRow][3]); //�Ƿ�����־
          tLLAffixSchema.setProperty(tAffixGridValues[tRow][6]); //��֤���Ա�־
          tLLAffixSchema.setAffixName(tAffixGridValues[tRow][1]); //��֤����
          tLLAffixSchema.setReadyCount(tAffixGridValues[tRow][4]); //��֤����
          tLLAffixSchema.setSubFlag(tAffixGridValues[i][2]); //�Ƿ����ύ��־
          tLLAffixSchema.setReturnFlag(tAffixGridValues[i][7]); //�Ƿ��˻�ԭ����־
          tLLAffixSet.add(tLLAffixSchema);
	}
	tTransferData.setNameAndValue("RgtNo",tCaseNo);
    }

    tTransferData.setNameAndValue("CustomerNo",tCustomerNo);
//    String tTest1 = request.getParameter("ClinicMedFeeTypeName");
//    String tTest2 = new String(tTest1.getBytes("ISO-8859-1"),"GB2312");
    loggerDebug("LLMAffixListSave","LLMAffixListSave.jsp����--"+tOperate);


    try
    {
        // ׼���������� VData
        VData tVData = new VData();

        tVData.add( tG );
        tVData.add( tTransferData );
	if (tOperate.equals("Rpt||INSERT"))		//�����׶����ɵ�֤
        {
          tVData.add(tLLReportAffixSet);
        }
        else if (tOperate.equals("Rgt||UPDATE"))	//�����׶ε�֤����
        {
          tVData.add(tLLAffixSet);
        }
	else if (tOperate.equals("RgtAdd||INSERT"))	//�����׶β��䵥֤
	{

	}
        LLMAffixListUI tLLMAffixListUI = new LLMAffixListUI();
       if (tLLMAffixListUI.submitData(tVData,tOperate) == false)
        {
            int n = tLLMAffixListUI.mErrors.getErrorCount();
            for (int i = 0; i < n; i++)
            {
                Content = Content + "��֤��Ϣ�������ԭ����: " + tLLMAffixListUI.mErrors.getError(i).errorMessage;
                FlagStr = "FAIL";
            }
        } else {
            Content = " ����ɹ�! ";
            FlagStr = "SUCC";
        }

    }
    catch(Exception e)
    {
        e.printStackTrace();
        Content = Content.trim()+".��ʾ���쳣��ֹ!";
    }

//    loggerDebug("LLMAffixListSave","LLMedicalFeeInpSave����--"+FlagStr);

%>
<html>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
