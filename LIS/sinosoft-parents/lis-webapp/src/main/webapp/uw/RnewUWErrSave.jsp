<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWErrSave.jsp
//�����ܣ��Զ��˱����Ĺ���
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.xbcheck.*"%>

<%
  //�������
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  String Flag = "";
  
  String tPolNo = "";
  loggerDebug("RnewUWErrSave","----");
  //���ֺ˱�������Ϣ��
  //���� ProposalNo,uwno,SerialNo
  LCUWErrorSet tLCUWErrorSet = new LCUWErrorSet();
//�����˺˱�������Ϣ��
  //���� uwno,SerialNo
  RnewIndUWErrorSet tRnewIndUWErrorSet = new RnewIndUWErrorSet();
  //��ͬ�˱�������Ϣ��
  //���� ProposalContNo,uwno,SerialNo
  LCCUWErrorSet tLCCUWErrorSet = new LCCUWErrorSet();
	//��ȡ����ѡ�������
	String[] tChk = request.getParameterValues("InpUWErrGridChk"); 
	//��ͬ��,������,���ֱ���,��������,�˱���Ϣ,�޸�ʱ��,�Ƿ�����,Ͷ������,��ˮ��,
	//�˱����к�,��ͬ���ֱ��,Ͷ������
	String[] tRiskFlag = request.getParameterValues("UWErrGrid11"); 
	String[] tSerialNo = request.getParameterValues("UWErrGrid9"); 
	String[] tUWNo = request.getParameterValues("UWErrGrid10"); 
	String[] tProposalNo = request.getParameterValues("UWErrGrid12"); 
	String[] tContno = request.getParameterValues("UWErrGrid1"); 
	for(int index=0;index<tChk.length;index++)
  	{
      loggerDebug("RnewUWErrSave","index:"+index+":"+tChk[index]);

      if(tChk[index].equals("1"))           
      {
           //ѡ��
           //�ж��Ǻ�ͬ�㻹�����ֲ�
           String tFlag = tRiskFlag[index];
           if(tFlag.equals("risk"))
           {
             //���ֺ˱���Ϣ
             LCUWErrorSchema tempLCUWErrorSchema = new LCUWErrorSchema();
             tempLCUWErrorSchema.setSerialNo(tSerialNo[index]);
             tempLCUWErrorSchema.setUWNo(tUWNo[index]);
             tempLCUWErrorSchema.setProposalNo(tProposalNo[index]);
             tLCUWErrorSet.add(tempLCUWErrorSchema);
           }
           else if(tFlag.equals("insured"))
           {
             //�����˺˱���Ϣ
             RnewIndUWErrorSchema tempRnewIndUWErrorSchema = new RnewIndUWErrorSchema();
             tempRnewIndUWErrorSchema.setSerialNo(tSerialNo[index]);
             tempRnewIndUWErrorSchema.setUWNo(tUWNo[index]);
             tRnewIndUWErrorSet.add(tempRnewIndUWErrorSchema);
           }
           else
           {
             //��ͬ�˱���Ϣ
             LCCUWErrorSchema tempLCCUWErrorSchema = new LCCUWErrorSchema();
             tempLCCUWErrorSchema.setProposalContNo(tContno[index]);
             tempLCCUWErrorSchema.setSerialNo(tSerialNo[index]);
             tempLCCUWErrorSchema.setUWNo(tUWNo[index]);
             tLCCUWErrorSet.add(tempLCCUWErrorSchema);
           }
      }
      else
      {      
            loggerDebug("RnewUWErrSave","����δ��ѡ��");
      }
   	}               



  // ׼���������� VData
   VData tVData = new VData();

	tVData.addElement(tLCUWErrorSet);
	tVData.addElement(tRnewIndUWErrorSet);
	tVData.addElement(tLCCUWErrorSet);

  // ���ݴ���
  Flag = "QUERY||LCUWERROR";
  /*
  RnewUWErrUI tRnewUWErrUI  = new RnewUWErrUI();
	if (!tRnewUWErrUI.submitData(tVData,Flag))
	{
      		Content = " ����ʧ�ܣ�ԭ����: " + tRnewUWErrUI.mErrors.getError(0).errorMessage;
      		FlagStr = "Fail";
	}
	else
	{
		
	} // end of if
  */
  String busiName="RnewUWErrUI";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  if(!tBusinessDelegate.submitData(tVData,Flag,busiName)){
	  Content = " ����ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
	  FlagStr = "Fail";
  }else
  {
		
  }// end of if
  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr == "Fail")
  {
    tError = tBusinessDelegate.getCErrors();
    if (!tError.needDealError())
    {                          
    	Content = " ����ɹ�! ";
    	FlagStr = "Succ";
    }
    else                                                                           
    {
    	Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }
loggerDebug("RnewUWErrSave","------end------");
loggerDebug("RnewUWErrSave",FlagStr);
loggerDebug("RnewUWErrSave",Content);
%>
<html>
<script language="javascript">
	//parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
	parent.fraInterface.releaseAutoUWButton();
</script>
</html>

