<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�ManuUWCho.jsp
//�����ܣ����������˹��˱�
//�������ڣ�2002-09-24 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%> 
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page import="com.sinosoft.lis.cbcheck.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.workflow.tb.*"%>
<%@page import="com.sinosoft.workflowengine.*"%>
<%
//1-�õ����м�¼����ʾ��¼ֵ
int index=0;
int TempCount=0;
//�������
CErrors tError = null;
String FlagStr = "Succ";
String Content = "";

GlobalInput tGlobalInput = new GlobalInput();

tGlobalInput=(GlobalInput)session.getValue("GI");	  
if(tGlobalInput == null) {
	out.println("session has expired");
	return;
}
loggerDebug("GroupPolQuery","GlobalInput.Operator:"+tGlobalInput.Operator);

String tRadio[] = request.getParameterValues("InpPolAddGridSel");  
String tTempClassNum[] = request.getParameterValues("PolAddGridNo");
String tPrtNo[] = request.getParameterValues("PolAddGrid3");
String tPolCode[] = request.getParameterValues("PolAddGrid1");
String tMissionID = request.getParameter("MissionID");
String tSubMissionID = request.getParameter("SubMissionID");

int temp = tRadio.length;
loggerDebug("GroupPolQuery","radiolength:"+temp);
loggerDebug("GroupPolQuery","MissionID:"+tMissionID);

//������ 
LCPolSchema mLCPolSchema = new LCPolSchema();
LCUWMasterSchema mLCUWMasterSchema = new LCUWMasterSchema();
if(tTempClassNum!=null) //������ǿռ�¼	
{
  	TempCount = tTempClassNum.length; //��¼��      
   	loggerDebug("GroupPolQuery","Start query Count="+TempCount);   
  	while(index<TempCount)
  	{
  		loggerDebug("GroupPolQuery","PolNo:"+tPolCode[index]);
  		loggerDebug("GroupPolQuery","radio:"+tRadio[index]);
  		if (!tPolCode[index].equals("")&&tRadio[index].equals("1"))
  		{
  			loggerDebug("GroupPolQuery","GridNO="+tTempClassNum[index]);
  			loggerDebug("GroupPolQuery","Grid 1="+tPolCode[index]);
  			loggerDebug("GroupPolQuery","Radio:"+tRadio[index]);
  			loggerDebug("GroupPolQuery","PrtNo:"+tPrtNo[index]);
  			//��ѯ����ʾ������Ϣ
    		LCPolSchema tLCPolSchema = new LCPolSchema();
    		tLCPolSchema.setPolNo(tPolCode[index]);
    		
    		// ׼���������� VData2
			VData tVData2 = new VData();
		
			tVData2.addElement(tLCPolSchema);
			// ׼���������� VData
			VData tVData = new VData();
			tVData.addElement(tLCPolSchema);
			// ���ݴ���
  			ProposalQueryUI tProposalQueryUI   = new ProposalQueryUI();
  			LCUWMasterQueryUI tLCUWMasterQueryUI = new LCUWMasterQueryUI();  
			if (!tProposalQueryUI.submitData(tVData,"QUERY||MAIN"))
			{
				Content = " �����˹��˱���ѯʧ�ܣ�ԭ����: " + tProposalQueryUI.mErrors.getError(0).errorMessage;
      			FlagStr = "Fail";
			}
			else if (!tLCUWMasterQueryUI.submitData(tVData2,"QUERY||MAIN"))
			 {
      			Content = " �����˹��˱���ѯʧ�ܣ�ԭ����: " + tLCUWMasterQueryUI.mErrors.getError(0).errorMessage;
      			FlagStr = "Fail2";
      			loggerDebug("GroupPolQuery","��ѯʧ��tLCUWMasterQueryUI");
			 }
			else
			{
			     loggerDebug("GroupPolQuery","after all !!!!");			
				tVData.clear();
				tVData = tProposalQueryUI.getResult();
		        tVData2 = tLCUWMasterQueryUI.getResult();
				// ��ʾ
				LCPolSet mLCPolSet = new LCPolSet(); 
				LCUWMasterSet mLCUWMasterSet = new LCUWMasterSet();
				mLCPolSet.set((LCPolSet)tVData.getObjectByObjectName("LCPolSet",0));
				mLCUWMasterSet.set((LCUWMasterSet)tVData2.getObjectByObjectName("LCUWMasterSet",0));				
				if (mLCPolSet.size() == 1)
				{
					mLCPolSchema = mLCPolSet.get(1);
					loggerDebug("GroupPolQuery","---LCPolSchema ok ---");
				}
				loggerDebug("GroupPolQuery","---LCUWMasterSet.size()---"+mLCUWMasterSet.size());
				if (mLCUWMasterSet.size() == 1)
				{
					mLCUWMasterSchema = mLCUWMasterSet.get(1);
					loggerDebug("GroupPolQuery","---LCUWMasterSchema ok---");
				}
				loggerDebug("GroupPolQuery","proposalno:"+mLCPolSchema.getProposalNo().trim());
				loggerDebug("GroupPolQuery","mainpolno:"+mLCPolSchema.getMainPolNo());
				
				if(mLCPolSchema.getPolNo().equals(mLCPolSchema.getMainPolNo()))
				{
%>
<html>
<head>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="ManuUW.js"></SCRIPT>
</head>
<script language="javascript">
//showAddButton();
</script>
</html>			
<%
				}
				else
				{
%>
<html>
<head>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="GroupPolQuery.js"></SCRIPT>
</head>
<script language="javascript">
//hideAddButton();
</script>
</html>				
<%
				}									
%>
<script language="javascript">
parent.fraInterface.fm.ProposalNo.value="<%=mLCPolSchema.getPolNo()%>";
parent.fraInterface.fm.RiskCode.value="<%=mLCPolSchema.getRiskCode()%>";
parent.fraInterface.fm.RiskVersion.value="<%=mLCPolSchema.getRiskVersion()%>";
parent.fraInterface.fm.InsuredNo.value="<%=mLCPolSchema.getInsuredNo()%>";
parent.fraInterface.fm.InsuredName.value="<%=mLCPolSchema.getInsuredName()%>";
parent.fraInterface.fm.InsuredSex.value="<%=mLCPolSchema.getInsuredSex()%>";
parent.fraInterface.fm.Mult.value="<%=mLCPolSchema.getMult()%>";
parent.fraInterface.fm.Prem.value="<%=mLCPolSchema.getPrem()%>";
parent.fraInterface.fm.Amnt.value="<%=mLCPolSchema.getAmnt()%>";
parent.fraInterface.fm.PolNo.value="<%=mLCPolSchema.getPolNo()%>";
//parent.fraInterface.fm.PrtNoHide.value="<%=mLCPolSchema.getPrtNo()%>";
//parent.fraInterface.fm.MainPolNoHide.value="<%=mLCPolSchema.getMainPolNo()%>";
//parent.fraInterface.fm.UWGrade.value="<%=mLCUWMasterSchema.getUWGrade()%>";
//parent.fraInterface.fm.AppGrade.value="<%=mLCUWMasterSchema.getAppGrade()%>";
</script>
<%
			} // end of if
  			loggerDebug("GroupPolQuery","---2---"+FlagStr);
			//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
			if (!FlagStr.equals("Succ"))
			{   
			    loggerDebug("GroupPolQuery","---3---"+FlagStr);
				if(FlagStr.equals("Fail") )
				   tError = tProposalQueryUI.mErrors;
				if(FlagStr.equals("Fail2") )
				   tError = tLCUWMasterQueryUI.mErrors;
				
				if (!tError.needDealError())
				{                          
			    		Content = " ��ѯ�ɹ�! ";
			    		FlagStr = "Succ";
				}		
			 	else                                                                           
 			 	{
 			   		Content = " ��ѯʧ�ܣ�ԭ����:" + tError.getFirstError();
  			  		FlagStr = "Fail";
  			 	}
 			} 			
    			if(tRadio[index].equals("1"))
      				loggerDebug("GroupPolQuery","the "+index+" line is checked!");
    			else
      				loggerDebug("GroupPolQuery","the "+index+" line is not checked!");
      		}    		
    		index=index+1;
    		loggerDebug("GroupPolQuery","index:"+index);          
	}
}   
%> 
<html>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html> 
