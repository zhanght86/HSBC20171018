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
  <%@page import="com.sinosoft.workflow.tb.*"%>
  <%@page import="com.sinosoft.workflowengine.*" %>
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
  String tUWFlag 	   = request.getParameter("UWFlag");
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
   //��ڵ� lzf
  String tActivityID = request.getParameter("ActivityID");
  loggerDebug("UWManuHealthQSave","tActivityID : "+tActivityID);
  
 loggerDebug("UWManuHealthQSave","ContNo : "+tContNo);
  loggerDebug("UWManuHealthQSave","PrtSeq : "+tPrtSeq);
  loggerDebug("UWManuHealthQSave","MissionID : "+tMissionID);
  loggerDebug("UWManuHealthQSave","SubMissionID : "+tSubMissionID);
  loggerDebug("UWManuHealthQSave","PrtNo : "+tPrtNo);
  loggerDebug("UWManuHealthQSave","PEAddress : "+tPEAddress);
  loggerDebug("UWManuHealthQSave","AccName : "+tAccName);
  loggerDebug("UWManuHealthQSave","PEDate : "+tPEDate);
  loggerDebug("UWManuHealthQSave","Note : "+tNote);
  loggerDebug("UWManuHealthQSave","Result : "+tResult);
  loggerDebug("UWManuHealthQSave","OtherPEItem : "+tOtherPEItemDes);
  loggerDebug("UWManuHealthQSave","OtherPEItemRes : "+tOtherPEItemDes);
  loggerDebug("UWManuHealthQSave","OtherPEItemDes : "+tOtherPEItemDes);
  loggerDebug("UWManuHealthQSave","PastDiseaseRes : "+tPastDiseaseRes);
  loggerDebug("UWManuHealthQSave","PastDiseaseDes : "+tPastDiseaseDes);
  
 //�����Ŀ���
 /**
  LCPENoticeItemSchema tLCPENoticeItemSchema;
  LCPENoticeItemSet tLCPENoticeItemSet = new LCPENoticeItemSet();
  LCPENoticeItemSet tLCPENoticeItemSet3 = new LCPENoticeItemSet();
  String count[] = request.getParameterValues("HealthGridNo");
  if(count != null)
  {
  	String[] tHealthCode   = request.getParameterValues("HealthGrid1");
  	String[] tHealthname   = request.getParameterValues("HealthGrid2");
  	      String[] tHealthResult   = request.getParameterValues("HealthGrid3");
          String[] tHealthMark = request.getParameterValues("HealthGrid4");
          for(int i = 0;i<count.length;i++)
          {
          	      tLCPENoticeItemSchema = new   LCPENoticeItemSchema();
                  tLCPENoticeItemSchema.setContNo(tContNo);
                  tLCPENoticeItemSchema.setPrtSeq(tPrtSeq);
                  tLCPENoticeItemSchema.setProposalContNo(tProposalContNo);
                  tLCPENoticeItemSchema.setPEItemCode(tHealthCode[i]);
                  tLCPENoticeItemSchema.setPEItemName(tHealthname[i]);
                  tLCPENoticeItemSchema.setFreePE(tHealthResult[i]);
                  tLCPENoticeItemSchema.setPEItemResult(tHealthMark[i]);
                  tLCPENoticeItemSet.add(tLCPENoticeItemSchema);
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
		  	    LCPENoticeItemSchema aLCPENoticeItemSchema = new LCPENoticeItemSchema();
		  		
					 	aLCPENoticeItemSchema.setPEItemCode( aHealthCode[j]);
					 	aLCPENoticeItemSchema.setPEItemName( aHealthname[j]);
					 	aLCPENoticeItemSchema.setFreePE(aHealthResult[j]);
					 	aLCPENoticeItemSchema.setPEItemResult(aHealthMark[j]);
					
			    	tLCPENoticeItemSet3.add( aLCPENoticeItemSchema );		   			   		
				    }
			 }

 }
  else
  {
  }
**/

	LCPENoticeReplySchema tLCPENoticeReplySchema;
	LCPENoticeReplySet tLCPENoticeReplySet = new LCPENoticeReplySet();
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
	        	tLCPENoticeReplySchema = new LCPENoticeReplySchema();
	        	tLCPENoticeReplySchema.setContNo(tContNo);
	        	tLCPENoticeReplySchema.setPrtSeq(tPrtSeq);
	        	tLCPENoticeReplySchema.setProposalContNo(tProposalContNo);
	        	tLCPENoticeReplySchema.setPEItemCode(tPEItemCode[i]);
	        	tLCPENoticeReplySchema.setPEItemName(tPEItemName[i]);
	        	tLCPENoticeReplySchema.setPEItemDivCode(tPEItemDivCode[i]);
	        	tLCPENoticeReplySchema.setPEItemDivName(tPEItemDivName[i]);
	        	tLCPENoticeReplySchema.setPEItemDivRes(tPEItemDivRes[i]);
	        	tLCPENoticeReplySchema.setPEItemDivDes(tPEItemDivDes[i]);
	        	tLCPENoticeReplySchema.setPEItemDivNor(tPEItemDivNor[i]);
	        	tLCPENoticeReplySchema.setFreePE("0");
	        	
	        	tLCPENoticeReplySet.add(tLCPENoticeReplySchema);
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
	        	tLCPENoticeReplySchema = new LCPENoticeReplySchema();
	        	tLCPENoticeReplySchema.setContNo(tContNo);
	        	tLCPENoticeReplySchema.setPrtSeq(tPrtSeq);
	        	tLCPENoticeReplySchema.setProposalContNo(tProposalContNo);
	        	tLCPENoticeReplySchema.setPEItemCode(tPEItemCode[i]);
	        	tLCPENoticeReplySchema.setPEItemName(tPEItemName[i]);
	        	tLCPENoticeReplySchema.setPEItemDivCode(tPEItemDivCode[i]);
	        	tLCPENoticeReplySchema.setPEItemDivName(tPEItemDivName[i]);
	        	tLCPENoticeReplySchema.setPEItemDivRes(tPEItemDivRes[i]);
	        	tLCPENoticeReplySchema.setPEItemDivDes(tPEItemDivDes[i]);
	        	tLCPENoticeReplySchema.setPEItemDivNor(tPEItemDivNor[i]);
	        	tLCPENoticeReplySchema.setFreePE("1");
	        	
	        	tLCPENoticeReplySet.add(tLCPENoticeReplySchema);
	        }
	}
	//���������Ŀ����
	  if(!(tOtherPEItemName.equals("null")||tOtherPEItemName.equals("")))
	  {
		  tLCPENoticeReplySchema = new LCPENoticeReplySchema();
      	tLCPENoticeReplySchema.setContNo(tContNo);
      	tLCPENoticeReplySchema.setPrtSeq(tPrtSeq);
      	tLCPENoticeReplySchema.setProposalContNo(tProposalContNo);
      	tLCPENoticeReplySchema.setPEItemCode(tOtherPEItemCode);
      	tLCPENoticeReplySchema.setPEItemName(tOtherPEItemName);
      	tLCPENoticeReplySchema.setPEItemDivCode(tOtherPEItemCode);
      	tLCPENoticeReplySchema.setPEItemDivName(tOtherPEItemName);
      	tLCPENoticeReplySchema.setPEItemDivRes(tOtherPEItemRes);
      	tLCPENoticeReplySchema.setPEItemDivDes(tOtherPEItemDes);
      	tLCPENoticeReplySchema.setPEItemDivNor("");
      	
      	tLCPENoticeReplySet.add(tLCPENoticeReplySchema);
	  }	            
	
//������ս��۱���
  LCPENoticeSchema tLCPENoticeSchema = new LCPENoticeSchema();
  LCPENoticeSet tLCPENoticeSet = new LCPENoticeSet();
  tLCPENoticeSchema.setContNo(tContNo);
  tLCPENoticeSchema.setPrtSeq(tPrtSeq);
  tLCPENoticeSchema.setCustomerNo(tCustomerNo);
  tLCPENoticeSchema.setPEResult(tResult);
  tLCPENoticeSchema.setHospitCode(tPEHospital); //2009-01-04 ln add ���ҽԺ����
  tLCPENoticeSchema.setPEAddress(tPEAddress);
  tLCPENoticeSchema.setAgentName(tAccName); //�����ڼ�¼�����
  tLCPENoticeSchema.setREPEDate(tPEDate); //ʵ���������
 // tLCPENoticeSchema.setREPEDate(tREPEDate);
 // tLCPENoticeSchema.setMasculineFlag(tMasculineFlag);
  tLCPENoticeSet.add(tLCPENoticeSchema);


//������ʷ����
 	LCPENoticeResultSchema tLCPENoticeResultSchema ;
 	LCPENoticeResultSet tLCPENoticeResultSet = new LCPENoticeResultSet();
/**
	int lineCount = 0;
	String arrCount[] = request.getParameterValues("DisDesbGridNo");

	if(arrCount!=null)
	{
		//String tDisDesb[]=request.getParameterValues("DisDesbGrid1");
		String tDisResult[]=request.getParameterValues("DisDesbGrid2");
		String tICDCode[]=request.getParameterValues("DisDesbGrid3");

		lineCount = arrCount.length;
		loggerDebug("UWManuHealthQSave","lineCount="+lineCount);
		for(int i = 0;i<lineCount;i++)
		{
			tLCPENoticeResultSchema = new LCPENoticeResultSchema();
			tLCPENoticeResultSchema.setContNo(tContNo);
			tLCPENoticeResultSchema.setProposalContNo(tProposalContNo);
			tLCPENoticeResultSchema.setPrtSeq(tPrtSeq);
			tLCPENoticeResultSchema.setCustomerNo(tCustomerNo);				
		 
		  tLCPENoticeResultSchema.setDisDesb("��");	
		  if(tDisResult[i].equals(""))
		  {tLCPENoticeResultSchema.setDisResult("��");}
		  else
		  {tLCPENoticeResultSchema.setDisResult(tDisResult[i]);}
		  if(tICDCode[i].equals(""))
		  {tLCPENoticeResultSchema.setICDCode("��");}
		  else
		  {tLCPENoticeResultSchema.setICDCode(tICDCode[i]);} 
		
			loggerDebug("UWManuHealthQSave","tDisResult="+tDisResult[i]);
			loggerDebug("UWManuHealthQSave","tICDCode="+tICDCode[i]);		
			tLCPENoticeResultSet.add(tLCPENoticeResultSchema);
		}
	}
	else
	{
	}
**/
	tLCPENoticeResultSchema = new LCPENoticeResultSchema();
	tLCPENoticeResultSchema.setContNo(tContNo);
	tLCPENoticeResultSchema.setProposalContNo(tProposalContNo);
	tLCPENoticeResultSchema.setPrtSeq(tPrtSeq);
	tLCPENoticeResultSchema.setCustomerNo(tCustomerNo);	
	tLCPENoticeResultSchema.setName(tPEName);	
	tLCPENoticeResultSchema.setDisDesb(tPastDiseaseDes);	
	{tLCPENoticeResultSchema.setDisResult(tPastDiseaseRes);}
		
	tLCPENoticeResultSet.add(tLCPENoticeResultSchema);


	// ׼���������� VData
	VData tVData = new VData();
	FlagStr="";

	tVData.add(tLCPENoticeReplySet);
	tVData.add(tLCPENoticeSet);
	tVData.add(tLCPENoticeResultSet);
	
/*************************************	
  tVData.add(tLCPENoticeItemSet);
  tVData.add(tLCPENoticeSet);
  tVData.add(tLCPENoticeResultSet);
***************************************/
  TransferData tTransferData = new TransferData();
  //tTransferData.setNameAndValue("LCPENoticeItemSet",tLCPENoticeItemSet);
  //add by zhangxing 
 // tTransferData.setNameAndValue("LCPENoticeItemSet3",tLCPENoticeItemSet3);
  //tTransferData.setNameAndValue("LCPENoticeSet",tLCPENoticeSet);
  //tTransferData.setNameAndValue("LCPENoticeResultSet",tLCPENoticeResultSet);
  tTransferData.setNameAndValue("MissionID",tMissionID);
  tTransferData.setNameAndValue("SubMissionID",tSubMissionID);
  tTransferData.setNameAndValue("ActivityID",tActivityID);//lzf
  tTransferData.setNameAndValue("PrtNo",tPrtNo);
  tTransferData.setNameAndValue("ContNo",tContNo);
  tTransferData.setNameAndValue("PrtSeq",tPrtSeq);
  tTransferData.setNameAndValue("CustomerNo",tCustomerNo);
  tTransferData.setNameAndValue("CustomerNo",tCustomerNo);
  tTransferData.setNameAndValue("UWFlag",tUWFlag);
  tTransferData.setNameAndValue("PEName",tPEName);
 // tTransferData.setNameAndValue("ManageCom",tManageCom);
//  tTransferData.setNameAndValue("MakeDate",MakeDate);

  tVData.add(tTransferData);
  tVData.add(tG);
	//��������Լ�˹��˱�ʱ����ֱ���޸�������Ĺ��� 
  if("1".equals(tUWFlag)){
		  InputHealthReportResultAfterInitService tInputHealthReportResultAfterInitService = new InputHealthReportResultAfterInitService();
		  if(tInputHealthReportResultAfterInitService.submitData(tVData,tActivityID)){
				
				Content = " ����ɹ�! ";
				FlagStr = "Succ";
		
		}
		else{
			Content = "����ʧ�ܣ�ԭ����:" + tInputHealthReportResultAfterInitService.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
  }else{  
        loggerDebug("UWManuHealthQSave","here");
  
        //lzf
	    //TbWorkFlowUI tTbWorkFlowUI = new TbWorkFlowUI();
        WorkFlowUI tWorkFlowUI = new WorkFlowUI();
		loggerDebug("UWManuHealthQSave","this will save the data!!!");
        if(tWorkFlowUI.submitData(tVData,"execute")){
	
			Content = " ����ɹ�! ";
			FlagStr = "Succ";
	
	     }
	     else{
			Content = "����ʧ�ܣ�ԭ����:" + tWorkFlowUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
	}

  }
/*****************************************************
	DisDesbUI tDisDesbUI = new DisDesbUI();
	try{
		loggerDebug("UWManuHealthQSave","this will save the data!!!");
		tDisDesbUI.submitData(tVData,"");
	}
	catch(Exception ex){
		Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
		FlagStr = "Fail";
	}
******************************************************/

%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

