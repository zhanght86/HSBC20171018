<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�ManuHealthQChk.jsp
//�����ܣ��˹��˱�������ϲ�ѯ
//�������ڣ�2005-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������ :ln   ��������  :2008-11-7   ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.xb.*"%>
<%
  //�������
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";

	GlobalInput tG = new GlobalInput();

	tG=(GlobalInput)session.getValue("GI");

  	if(tG == null) {
		out.println("session has expired");
		return;
	}

 	String tContNo       = request.getParameter("ContNo");
  String tPrtSeq       = request.getParameter("PrtSeq");
  String tMissionID    = request.getParameter("MissionID");
  String tSubMissionID = request.getParameter("SubMissionID");
  String tPrtNo        = request.getParameter("PrtNo");
  String tProposalContNo =request.getParameter("ContNo");
 	String tCustomerNo   =request.getParameter("CustomerNo");
 	String tPEName   =request.getParameter("PEName");
  String tPEHospital    = request.getParameter("PEHospital"); //2009-01-04 ln add ���ҽԺ����
  String tPEAddress    = request.getParameter("PEAddress");
 // String tPEDoctor     = request.getParameter("PEDoctor");
  String tAccName     = request.getParameter("AccName"); //�ֶθ����������
  String tPEDate       = request.getParameter("PEDate");
 // String tREPEDate     = request.getParameter("REPEDate");
  String tNote         = request.getParameter("Note"); //������Ϣ
 // String tMasculineFlag= request.getParameter("MasculineFlag");
  //������
  String tPEResult= request.getParameter("PEResult"); //���ҽԺ���ս���
  String tPEResultDes= request.getParameter("PEResultDes"); //���ҽԺ���ս�����ϸ��Ϣ
  String tResult= tPEResult + ":" + tPEResultDes + ":" + tNote;
//  String tManageCom= request.getParameter("ManageCom");
  //���������Ϣ
  String tOtherPEItemCode= "Other";
  String tOtherPEItemName= request.getParameter("OtherPEItem");
  String tOtherPEItemRes= request.getParameter("OtherPEItemRes");
  String tOtherPEItemDes= request.getParameter("OtherPEItemDes");
  //������ʷ
  String tPastDiseaseRes= request.getParameter("PastDiseaseRes");
  String tPastDiseaseDes= request.getParameter("PastDiseaseDes");
  
 loggerDebug("RnewManuHealthQSave","ContNo : "+tContNo);
  loggerDebug("RnewManuHealthQSave","PrtSeq : "+tPrtSeq);
  loggerDebug("RnewManuHealthQSave","MissionID : "+tMissionID);
  loggerDebug("RnewManuHealthQSave","SubMissionID : "+tSubMissionID);
  loggerDebug("RnewManuHealthQSave","PrtNo : "+tPrtNo);
  loggerDebug("RnewManuHealthQSave","PEAddress : "+tPEAddress);
  loggerDebug("RnewManuHealthQSave","AccName : "+tAccName);
  loggerDebug("RnewManuHealthQSave","PEDate : "+tPEDate);
  loggerDebug("RnewManuHealthQSave","Note : "+tNote);
  loggerDebug("RnewManuHealthQSave","Result : "+tResult);
  loggerDebug("RnewManuHealthQSave","OtherPEItem : "+tOtherPEItemDes);
  loggerDebug("RnewManuHealthQSave","OtherPEItemRes : "+tOtherPEItemDes);
  loggerDebug("RnewManuHealthQSave","OtherPEItemDes : "+tOtherPEItemDes);
  loggerDebug("RnewManuHealthQSave","PastDiseaseRes : "+tPastDiseaseRes);
  loggerDebug("RnewManuHealthQSave","PastDiseaseDes : "+tPastDiseaseDes);
  
 //�����Ŀ���
 /**
  RnewPENoticeItemSchema tRnewPENoticeItemSchema;
  RnewPENoticeItemSet tRnewPENoticeItemSet = new RnewPENoticeItemSet();
  RnewPENoticeItemSet tRnewPENoticeItemSet3 = new RnewPENoticeItemSet();
  String count[] = request.getParameterValues("HealthGridNo");
  if(count != null)
  {
  	String[] tHealthCode   = request.getParameterValues("HealthGrid1");
  	String[] tHealthname   = request.getParameterValues("HealthGrid2");
  	      String[] tHealthResult   = request.getParameterValues("HealthGrid3");
          String[] tHealthMark = request.getParameterValues("HealthGrid4");
          for(int i = 0;i<count.length;i++)
          {
          	      tRnewPENoticeItemSchema = new   RnewPENoticeItemSchema();
                  tRnewPENoticeItemSchema.setContNo(tContNo);
                  tRnewPENoticeItemSchema.setPrtSeq(tPrtSeq);
                  tRnewPENoticeItemSchema.setProposalContNo(tProposalContNo);
                  tRnewPENoticeItemSchema.setPEItemCode(tHealthCode[i]);
                  tRnewPENoticeItemSchema.setPEItemName(tHealthname[i]);
                  tRnewPENoticeItemSchema.setFreePE(tHealthResult[i]);
                  tRnewPENoticeItemSchema.setPEItemResult(tHealthMark[i]);
                  tRnewPENoticeItemSet.add(tRnewPENoticeItemSchema);
          }
  }
  else
  {
  }
  //add by zhangxing
  String ChkCount[] = request.getParameterValues("OtherHealthGridNo"); 
  if (ChkCount != null)
	{
	      String[] aHealthCode   = request.getParameterValues("OtherHealthGrid1");	    
  	    String[] aHealthname   = request.getParameterValues("OtherHealthGrid2");
  	    String[] aHealthResult = request.getParameterValues("OtherHealthGrid3");
  	    String[] aHealthMark = request.getParameterValues("OtherHealthGrid4");
			  for (int j = 0; j < ChkCount.length; j++)
			  {			     
				    if (!aHealthCode[j].equals(""))
				    {
		  	    RnewPENoticeItemSchema aRnewPENoticeItemSchema = new RnewPENoticeItemSchema();
		  		
					 	aRnewPENoticeItemSchema.setPEItemCode( aHealthCode[j]);
					 	aRnewPENoticeItemSchema.setPEItemName( aHealthname[j]);
					 	aRnewPENoticeItemSchema.setFreePE(aHealthResult[j]);
					 	aRnewPENoticeItemSchema.setPEItemResult(aHealthMark[j]);
					
			    	tRnewPENoticeItemSet3.add( aRnewPENoticeItemSchema );		   			   		
				    }
			 }

 }
  else
  {
  }
**/

	RnewPENoticeReplySchema tRnewPENoticeReplySchema;
	RnewPENoticeReplySet tRnewPENoticeReplySet = new RnewPENoticeReplySet();
	//��ѡ�����Ŀ����
	String count[] = request.getParameterValues("HealthGridNo");
	if(count != null)
	{
		String[] tPEItemCode   = request.getParameterValues("HealthGrid1");
		String[] tPEItemName   = request.getParameterValues("HealthGrid2");
		String[] tPEItemDivCode   = request.getParameterValues("HealthGrid3");
		String[] tPEItemDivName   = request.getParameterValues("HealthGrid4");
		String[] tPEItemDivRes   = request.getParameterValues("HealthGrid5");
		String[] tPEItemDivDes   = request.getParameterValues("HealthGrid6");
		String[] tPEItemDivNor   = request.getParameterValues("HealthGrid7");
        
	        for(int i = 0;i<count.length;i++)
	        {
	        	tRnewPENoticeReplySchema = new RnewPENoticeReplySchema();
	        	tRnewPENoticeReplySchema.setContNo(tContNo);
	        	tRnewPENoticeReplySchema.setPrtSeq(tPrtSeq);
	        	tRnewPENoticeReplySchema.setProposalContNo(tProposalContNo);
	        	tRnewPENoticeReplySchema.setPEItemCode(tPEItemCode[i]);
	        	tRnewPENoticeReplySchema.setPEItemName(tPEItemName[i]);
	        	tRnewPENoticeReplySchema.setPEItemDivCode(tPEItemDivCode[i]);
	        	tRnewPENoticeReplySchema.setPEItemDivName(tPEItemDivCode[i]);
	        	tRnewPENoticeReplySchema.setPEItemDivRes(tPEItemDivRes[i]);
	        	tRnewPENoticeReplySchema.setPEItemDivDes(tPEItemDivDes[i]);
	        	tRnewPENoticeReplySchema.setPEItemDivNor(tPEItemDivNor[i]);
	        	tRnewPENoticeReplySchema.setFreePE("0");
	        	
	        	tRnewPENoticeReplySet.add(tRnewPENoticeReplySchema);
	        }
	}
	//��ѡ�����Ŀ����
	String count1[] = request.getParameterValues("HealthOtherGridNo");
	if(count1 != null)
	{
		String[] tPEItemCode   = request.getParameterValues("HealthOtherGrid1");
		String[] tPEItemName   = request.getParameterValues("HealthOtherGrid2");
		String[] tPEItemDivCode   = request.getParameterValues("HealthOtherGrid3");
		String[] tPEItemDivName   = request.getParameterValues("HealthOtherGrid4");
		String[] tPEItemDivRes   = request.getParameterValues("HealthOtherGrid5");
		String[] tPEItemDivDes   = request.getParameterValues("HealthOtherGrid6");
		String[] tPEItemDivNor   = request.getParameterValues("HealthOtherGrid7");
        
	        for(int i = 0;i<count1.length;i++)
	        {
	        	tRnewPENoticeReplySchema = new RnewPENoticeReplySchema();
	        	tRnewPENoticeReplySchema.setContNo(tContNo);
	        	tRnewPENoticeReplySchema.setPrtSeq(tPrtSeq);
	        	tRnewPENoticeReplySchema.setProposalContNo(tProposalContNo);
	        	tRnewPENoticeReplySchema.setPEItemCode(tPEItemCode[i]);
	        	tRnewPENoticeReplySchema.setPEItemName(tPEItemName[i]);
	        	tRnewPENoticeReplySchema.setPEItemDivCode(tPEItemDivCode[i]);
	        	tRnewPENoticeReplySchema.setPEItemDivName(tPEItemDivCode[i]);
	        	tRnewPENoticeReplySchema.setPEItemDivRes(tPEItemDivRes[i]);
	        	tRnewPENoticeReplySchema.setPEItemDivDes(tPEItemDivDes[i]);
	        	tRnewPENoticeReplySchema.setPEItemDivNor(tPEItemDivNor[i]);
	        	tRnewPENoticeReplySchema.setFreePE("1");
	        	
	        	tRnewPENoticeReplySet.add(tRnewPENoticeReplySchema);
	        }
	}
	//���������Ŀ����
	  if(!(tOtherPEItemName.equals("null")||tOtherPEItemName.equals("")))
	  {
		  tRnewPENoticeReplySchema = new RnewPENoticeReplySchema();
      	tRnewPENoticeReplySchema.setContNo(tContNo);
      	tRnewPENoticeReplySchema.setPrtSeq(tPrtSeq);
      	tRnewPENoticeReplySchema.setProposalContNo(tProposalContNo);
      	tRnewPENoticeReplySchema.setPEItemCode(tOtherPEItemCode);
      	tRnewPENoticeReplySchema.setPEItemName(tOtherPEItemName);
      	tRnewPENoticeReplySchema.setPEItemDivCode(tOtherPEItemCode);
      	tRnewPENoticeReplySchema.setPEItemDivName(tOtherPEItemName);
      	tRnewPENoticeReplySchema.setPEItemDivRes(tOtherPEItemRes);
      	tRnewPENoticeReplySchema.setPEItemDivDes(tOtherPEItemDes);
      	tRnewPENoticeReplySchema.setPEItemDivNor("");
      	
      	tRnewPENoticeReplySet.add(tRnewPENoticeReplySchema);
	  }	            
	
//������ս��۱���
  RnewPENoticeSchema tRnewPENoticeSchema = new RnewPENoticeSchema();
  RnewPENoticeSet tRnewPENoticeSet = new RnewPENoticeSet();
  tRnewPENoticeSchema.setContNo(tContNo);
  tRnewPENoticeSchema.setPrtSeq(tPrtSeq);
  tRnewPENoticeSchema.setCustomerNo(tCustomerNo);
  tRnewPENoticeSchema.setPEResult(tResult);
  tRnewPENoticeSchema.setHospitCode(tPEHospital); //2009-01-04 ln add ���ҽԺ����
  tRnewPENoticeSchema.setPEAddress(tPEAddress);
  tRnewPENoticeSchema.setAgentName(tAccName); //�����ڼ�¼�����
  tRnewPENoticeSchema.setREPEDate(tPEDate); //ʵ���������
 // tRnewPENoticeSchema.setREPEDate(tREPEDate);
 // tRnewPENoticeSchema.setMasculineFlag(tMasculineFlag);
  tRnewPENoticeSet.add(tRnewPENoticeSchema);


//������ʷ����
 	RnewPENoticeResultSchema tRnewPENoticeResultSchema ;
 	RnewPENoticeResultSet tRnewPENoticeResultSet = new RnewPENoticeResultSet();
/**
	int lineCount = 0;
	String arrCount[] = request.getParameterValues("DisDesbGridNo");

	if(arrCount!=null)
	{
		//String tDisDesb[]=request.getParameterValues("DisDesbGrid1");
		String tDisResult[]=request.getParameterValues("DisDesbGrid2");
		String tICDCode[]=request.getParameterValues("DisDesbGrid3");

		lineCount = arrCount.length;
		loggerDebug("RnewManuHealthQSave","lineCount="+lineCount);
		for(int i = 0;i<lineCount;i++)
		{
			tRnewPENoticeResultSchema = new RnewPENoticeResultSchema();
			tRnewPENoticeResultSchema.setContNo(tContNo);
			tRnewPENoticeResultSchema.setProposalContNo(tProposalContNo);
			tRnewPENoticeResultSchema.setPrtSeq(tPrtSeq);
			tRnewPENoticeResultSchema.setCustomerNo(tCustomerNo);				
		 
		  tRnewPENoticeResultSchema.setDisDesb("��");	
		  if(tDisResult[i].equals(""))
		  {tRnewPENoticeResultSchema.setDisResult("��");}
		  else
		  {tRnewPENoticeResultSchema.setDisResult(tDisResult[i]);}
		  if(tICDCode[i].equals(""))
		  {tRnewPENoticeResultSchema.setICDCode("��");}
		  else
		  {tRnewPENoticeResultSchema.setICDCode(tICDCode[i]);} 
		
			loggerDebug("RnewManuHealthQSave","tDisResult="+tDisResult[i]);
			loggerDebug("RnewManuHealthQSave","tICDCode="+tICDCode[i]);		
			tRnewPENoticeResultSet.add(tRnewPENoticeResultSchema);
		}
	}
	else
	{
	}
**/
	tRnewPENoticeResultSchema = new RnewPENoticeResultSchema();
	tRnewPENoticeResultSchema.setContNo(tContNo);
	tRnewPENoticeResultSchema.setProposalContNo(tProposalContNo);
	tRnewPENoticeResultSchema.setPrtSeq(tPrtSeq);
	tRnewPENoticeResultSchema.setCustomerNo(tCustomerNo);	
	tRnewPENoticeResultSchema.setName(tPEName);	
	tRnewPENoticeResultSchema.setDisDesb(tPastDiseaseDes);	
	{tRnewPENoticeResultSchema.setDisResult(tPastDiseaseRes);}
		
	tRnewPENoticeResultSet.add(tRnewPENoticeResultSchema);


	// ׼���������� VData
	VData tVData = new VData();
	FlagStr="";

	tVData.add(tRnewPENoticeReplySet);
	tVData.add(tRnewPENoticeSet);
	tVData.add(tRnewPENoticeResultSet);
	
/*************************************	
  tVData.add(tRnewPENoticeItemSet);
  tVData.add(tRnewPENoticeSet);
  tVData.add(tRnewPENoticeResultSet);
***************************************/
  TransferData tTransferData = new TransferData();
  //tTransferData.setNameAndValue("RnewPENoticeItemSet",tRnewPENoticeItemSet);
  //add by zhangxing 
 // tTransferData.setNameAndValue("RnewPENoticeItemSet3",tRnewPENoticeItemSet3);
  //tTransferData.setNameAndValue("RnewPENoticeSet",tRnewPENoticeSet);
  //tTransferData.setNameAndValue("RnewPENoticeResultSet",tRnewPENoticeResultSet);
  tTransferData.setNameAndValue("MissionID",tMissionID);
  tTransferData.setNameAndValue("SubMissionID",tSubMissionID);
  tTransferData.setNameAndValue("PrtNo",tPrtNo);
  tTransferData.setNameAndValue("ContNo",tContNo);
  tTransferData.setNameAndValue("PrtSeq",tPrtSeq);
  tTransferData.setNameAndValue("CustomerNo",tCustomerNo);
 // tTransferData.setNameAndValue("ManageCom",tManageCom);
//  tTransferData.setNameAndValue("MakeDate",MakeDate);
  tTransferData.setNameAndValue("BusiType", "1004");
  ExeSQL yExeSQL = new ExeSQL();
	SSRS ySSRS = new SSRS();
  String sqlstr="select activityid from lwactivity where functionid='10047013'";
	ySSRS = yExeSQL.execSQL(sqlstr);
	String xActivityID="";
	if(ySSRS.MaxRow==0)
	{}
	else
	{
		 xActivityID = ySSRS.GetText(1,1);
	}
  tTransferData.setNameAndValue("ActivityID", xActivityID);
  //�������������ص������˱���ʼ�ڵ������20130923
  RnewCommonDataPut tRnewCommonDataPut = new RnewCommonDataPut();
  if(!tRnewCommonDataPut.submitData(tTransferData))
  {
	  Content = "����ʧ�ܣ�ԭ����:" + tRnewCommonDataPut.getErrors().getFirstError();
      FlagStr = "Fail";
  }
  tTransferData=tRnewCommonDataPut.getTransferData();
  tVData.add(tTransferData);
  tVData.add(tG);

loggerDebug("RnewManuHealthQSave","here");

String busiName="WorkFlowUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
System.out.println("��ʼִ�У�tBusinessDelegate");
if(tBusinessDelegate.submitData(tVData,"execute",busiName)){
	Content = " ����ɹ�! ";
	FlagStr = "Succ";
}else{
	Content = "����ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
	FlagStr = "Fail";
}

	//RnewWorkFlowUI tRnewWorkFlowUI = new RnewWorkFlowUI();

		loggerDebug("RnewManuHealthQSave","this will save the data!!!");
		/*
  if(tRnewWorkFlowUI.submitData(tVData,"0000007013")){
	
			Content = " ����ɹ�! ";
			FlagStr = "Succ";
	
	}
	else{
		Content = "����ʧ�ܣ�ԭ����:" + tRnewWorkFlowUI.mErrors.getError(0).errorMessage;
		FlagStr = "Fail";
	}
*/

/*****************************************************
	DisDesbUI tDisDesbUI = new DisDesbUI();
	try{
		loggerDebug("RnewManuHealthQSave","this will save the data!!!");
		tDisDesbUI.submitData(tVData,"");
	}
	catch(Exception ex){
		Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
		FlagStr = "Fail";
	}
******************************************************/

%>
<%@page import="com.sinosoft.lis.xb.RnewCommonDataPut"%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

