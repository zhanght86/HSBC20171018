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
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.service.*"%>
<%
  //�������
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  String Flag = "";
  
  String tPolNo = "";
  loggerDebug("UWErrSave","----");
  //���ֺ˱�������Ϣ��
  //���� ProposalNo,uwno,SerialNo
  LCUWErrorSet tLCUWErrorSet = new LCUWErrorSet();
//�����˺˱�������Ϣ��
  //���� uwno,SerialNo
  LCIndUWErrorSet tLCIndUWErrorSet = new LCIndUWErrorSet();
  //��ͬ�˱�������Ϣ��
  //���� ProposalContNo,uwno,SerialNo
  LCCUWErrorSet tLCCUWErrorSet = new LCCUWErrorSet();
	//��ȡ����ѡ�������
	String[] tChk = request.getParameterValues("InpUWErrGridChk"); 
	//��ͬ��,������,���ֱ���,��������,�˱���Ϣ,�޸�ʱ��,�Ƿ�����,Ͷ������,��ˮ��,
	//�˱����к�,��ͬ���ֱ��,Ͷ������
	String[] tRiskFlag = request.getParameterValues("UWErrGrid12"); 
	String[] tSerialNo = request.getParameterValues("UWErrGrid10"); 
	String[] tUWNo = request.getParameterValues("UWErrGrid11"); 
	String[] tProposalNo = request.getParameterValues("UWErrGrid13"); 
	String[] tContno = request.getParameterValues("UWErrGrid1"); 
	for(int index=0;index<tChk.length;index++)
  	{
      loggerDebug("UWErrSave","index:"+index+":"+tChk[index]);

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
             LCIndUWErrorSchema tempLCIndUWErrorSchema = new LCIndUWErrorSchema();
             tempLCIndUWErrorSchema.setSerialNo(tSerialNo[index]);
             tempLCIndUWErrorSchema.setUWNo(tUWNo[index]);
             tLCIndUWErrorSet.add(tempLCIndUWErrorSchema);
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
            loggerDebug("UWErrSave","����δ��ѡ��");
      }
   	}               



  // ׼���������� VData
   VData tVData = new VData();

	tVData.addElement(tLCUWErrorSet);
	tVData.addElement(tLCIndUWErrorSet);
	tVData.addElement(tLCCUWErrorSet);

  // ���ݴ���
  Flag = "QUERY||LCUWERROR";
//  UWErrUI tUWErrUI  = new UWErrUI();
     String busiName="tUWErrUI";
	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();   
//	if (!tUWErrUI.submitData(tVData,Flag))
	if (!tBusinessDelegate.submitData(tVData,Flag,busiName))
	{
 //    		Content = " ����ʧ�ܣ�ԭ����: " + tUWErrUI.mErrors.getError(0).errorMessage;
      		Content = " ����ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
      		FlagStr = "Fail";
	}
	else
	{
		
	} // end of if
  
  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr == "Fail")
  {
 //   tError = tUWErrUI.mErrors;
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
loggerDebug("UWErrSave","------end------");
loggerDebug("UWErrSave",FlagStr);
loggerDebug("UWErrSave",Content);
%>
<html>
<script language="javascript">
	//parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
	parent.fraInterface.releaseAutoUWButton("<%=FlagStr%>","<%=Content%>");
</script>
</html>

