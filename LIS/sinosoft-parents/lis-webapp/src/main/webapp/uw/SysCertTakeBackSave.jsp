<%
//�������ƣ�SysCertTakeBackSave.jsp
//�����ܣ�
//�������ڣ�2002-10-25
//������  ����ƽ
//���¼�¼��  ������    ��������     ����ԭ��/����
//
%>
<%@page import="com.sinosoft.workflow.bq.EdorManuDataDeal"%>
<%@page import="com.sinosoft.lis.xb.RnewCommonDataPut"%>
<SCRIPT src="./CQueryValueOperate.js"></SCRIPT>
<SCRIPT src="IndiDunFeeInput.js"></SCRIPT>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
    <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.certify.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.tb.*"%>
  <%@page import="com.sinosoft.workflowengine.*"%>
<%@page import="com.sinosoft.service.*" %>
<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%!
	String buildMsg(boolean bFlag, String strMsg) {
		String strReturn = "";
		
		strReturn += "<html><script language=\"javascript\">";
		
		if( bFlag ) {
			strReturn += "  parent.fraInterface.afterSubmit('Succ', '�����ɹ����');";
		} else {
			strReturn += "  parent.fraInterface.afterSubmit('Fail','" + strMsg + "');";
		}
		strReturn += "</script></html>";
		
		return strReturn;
	}
%>

<%
  //�������
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  boolean bContinue = true;

    GlobalInput tGI = new GlobalInput(); 
    tGI=(GlobalInput)session.getValue("GI");  
    
   String tCertifyNo = request.getParameter("CertifyNo"); 
   String tCertifyCode = request.getParameter("CertifyCode"); 
   String tContNo = request.getParameter("ContNo"); 
   String tMissionID = request.getParameter("MissionID"); 
   String tSubMissionID = request.getParameter("SubMissionID"); 
   String tTakeBackOperator=  request.getParameter("TakeBackOperator") ;
   String tTakeBackMakeDate = request.getParameter("TakeBackMakeDate") ;
   String tSendOutCom = request.getParameter("SendOutCom") ;
   String tReceiveCom = request.getParameter("ReceiveCom") ;
   String tActivityID = request.getParameter("ActivityID") ;
   //����֪ͨ�������ֶ�code
   String tCodeType= request.getParameter("CodeType");
      
   loggerDebug("SysCertTakeBackSave","tCertifyCode:"+tCertifyCode);
   loggerDebug("SysCertTakeBackSave","tCertifyNo:"+tCertifyNo);
   loggerDebug("SysCertTakeBackSave","tMissionID:"+tMissionID);
   loggerDebug("SysCertTakeBackSave","tContNo:"+tContNo);
   loggerDebug("SysCertTakeBackSave","tTakeBackOperator:"+tTakeBackOperator);
   loggerDebug("SysCertTakeBackSave","TakeBackMakeDate:"+tTakeBackMakeDate);
   loggerDebug("SysCertTakeBackSave","tTakeBackOperator:"+tTakeBackOperator);
   loggerDebug("SysCertTakeBackSave","tSendOutCom:"+tSendOutCom);
   loggerDebug("SysCertTakeBackSave","tReceiveCom:"+tReceiveCom);
  if( tGI == null ) {
  	out.println( buildMsg(false, "��ҳ��ʱ����û�в���Ա��Ϣ") );
  	return;
  } else {
  }
  //���ݴ����
	//try {
  	LZSysCertifySchema tLZSysCertifySchema = new LZSysCertifySchema();
  	tLZSysCertifySchema.setCertifyCode( tCertifyCode );
		tLZSysCertifySchema.setCertifyNo( tCertifyNo );
		tLZSysCertifySchema.setTakeBackOperator( tTakeBackOperator );
		tLZSysCertifySchema.setTakeBackMakeDate( tTakeBackMakeDate );
		tLZSysCertifySchema.setSendOutCom( tSendOutCom );
		tLZSysCertifySchema.setReceiveCom( tReceiveCom );
					
	  // ׼���������� VData
	    String tOperate = new String();
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("CertifyNo",tCertifyNo);
		tTransferData.setNameAndValue("CertifyCode",tCertifyCode) ;
		tTransferData.setNameAndValue("ContNo",tContNo) ;
		tTransferData.setNameAndValue("GrpContNo",tContNo) ;
		tTransferData.setNameAndValue("MissionID",tMissionID);
		tTransferData.setNameAndValue("SubMissionID",tSubMissionID);
		tTransferData.setNameAndValue("TakeBackOperator",tTakeBackOperator);
		tTransferData.setNameAndValue("TakeBackMakeDate",tTakeBackMakeDate);
		tTransferData.setNameAndValue("CodeType",tCodeType);
		loggerDebug("SysCertTakeBackSave","============================tCertifyCode:" + tCertifyCode);
	   if(tCertifyCode.trim().equals("8888") )	
	      //tOperate = "0000001111"; //�������֪ͨ������ڵ����
	      tOperate=tActivityID;
	   if(tCertifyCode.trim().equals("9999"))	
	      //tOperate = "0000001112"; //���պ˱�֪ͨ������ڵ����    
	      tOperate=tActivityID;
	   if(tCertifyCode.trim().equals("9996"))	
	   //tongmeng 2007-11-23 modify
	   //�޸Ĺ��������
	     // tOperate = "0000001019"; //����ҵ��Ա֪ͨ������ڵ����
	     tOperate=tActivityID;
	    if(tCertifyCode.trim().equals("9986"))	
	      tOperate = "0000001260"; //����ҵ��Ա֪ͨ������ڵ����  	
	   	if(tCertifyCode.trim().equals("1113") || tCertifyCode.trim().equals("4404"))	
	       tOperate = "0000001113"; //����ҵ��Ա֪ͨ������ڵ����    
	    if(tCertifyCode.trim().equals("7777"))	
	       tOperate = "0000001025"; //����ҵ��Ա֪ͨ������ڵ����  
	    if(tCertifyCode.trim().equals("0054")) //add by yaory	
	       tOperate = "0000002250"; //�����ŵ������֪ͨ������ڵ����      
	    if(tCertifyCode.equals("0000") || tCertifyCode.equals("0006") || 
	       tCertifyCode.equals("0081") || tCertifyCode.equals("0082") || 
	       tCertifyCode.equals("0083") || tCertifyCode.equals("0084") ||
	       tCertifyCode.equals("0085") || tCertifyCode.equals("0086") ||
	       tCertifyCode.equals("0087") || tCertifyCode.equals("0088") ||
	       tCertifyCode.equals("0089") ||
	       tCertifyCode.equals("4481") || tCertifyCode.equals("4489") ||
	       tCertifyCode.equals("2001") || tCertifyCode.equals("2002") ||
	       tCertifyCode.equals("2003") || tCertifyCode.equals("2004") ||
	       tCertifyCode.equals("2005") || tCertifyCode.equals("2006") ||
	       tCertifyCode.equals("2007") || tCertifyCode.equals("2008")|| tCertifyCode.equals("2009"))
	       tOperate = "0000001300"; //����ȫ��˱�֪ͨ�� 
	   	if(tCertifyCode == "0076" || tCertifyCode.trim().equals("0076"))	
	       tOperate = "0000002111"; //�����ŵ��˱�����֪ͨ������ڵ����   
	    	if(tCertifyCode == "0075" || tCertifyCode.trim().equals("0075"))	
	       tOperate = "0000002311"; //�����ŵ��˱�Ҫ��֪ͨ������ڵ����       
	    if(tCertifyCode.trim().equals("2000"))	
	       tOperate = "0000000303"; //���ձ�ȫ���֪ͨ��
	    if(tCertifyCode.trim().equals("2010"))	
		   tOperate = "0000000314"; //���ձ�ȫ����֪ͨ��
		if(tCertifyCode.trim().equals("2011"))	
			   tOperate = "0000000322"; //���ձ��˱�֪ͨ��
		if(tCertifyCode.trim().equals("2012"))	
			 tOperate = "0000000353"; //���ղ������ϱ�֪ͨ��
		if(tCertifyCode.trim().equals("7009"))	
			   tOperate = "0000007009"; //���������������֪ͨ��
		if(tCertifyCode.trim().equals("7012"))	
			   tOperate = "0000007012"; //����������������֪ͨ��
		if(tCertifyCode.trim().equals("7006"))	
			   tOperate = "0000007006"; //�����������˺˱�֪ͨ��
	    if(tCertifyCode.trim().equals("4403") || tCertifyCode.trim().equals("4490")){
	    	String activitySQL="select activityid from lwmission where missionid='"+tMissionID+"' and missionprop3='"+tCertifyNo+"' and activityid in(select activityid from lwactivity where functionid in('10030023','10030031'))";
			  ExeSQL tExeSQL=new ExeSQL();
			  SSRS tSSRS=tExeSQL.execSQL(activitySQL);
			  if(tSSRS!=null && tSSRS.getMaxRow()>0){
				tOperate=tSSRS.GetText(1, 1);
			  }
	    	//tOperate = "0000005533";
	    }
	    //if(tCertifyCode.trim().equals("4490")){
	    //	tOperate = "0000005553";
	    //}
	    if(tCertifyCode.trim().equals("4499")){
	    	tOperate = "0000005563";
	    }


	
			//������Լ������δ����������ȫ������֪ͨ����ʱ�����⴦��������ñ���һ�£�ɾ����
					LWActivityDB tLWActivityDB=new LWActivityDB();
					tLWActivityDB.setActivityID(tOperate);
					LWActivitySet tLWActivitySet=tLWActivityDB.query();
					String ErrorMessage = "";
					/****����Լ����������,�뱣ȫ����������һ�µ���workflowBL  modify by LIJIAN  at  20130308*****/
				 loggerDebug("SysCertTakeBackSave","tOperate:"+tOperate);
				 tTransferData.setNameAndValue("ActivityID", tOperate);
				 tTransferData.setNameAndValue("BusiType", tLWActivitySet.get(1).getBusiType());
				 //�ж��������պ�ۺϵ��˹��˱��ڵ�
				 if(tCertifyCode.trim().equals("2010")){
					  EdorManuDataDeal tEdorManuDataDeal=new EdorManuDataDeal();
					  if(!tEdorManuDataDeal.submitData(tTransferData))
					  {
						  Content = "����ʧ�ܣ�ԭ����:" + tEdorManuDataDeal.getErrors().getFirstError();
					      FlagStr = "Fail";
					  }
					  tTransferData=tEdorManuDataDeal.getTransferData();
				 }
				 if(tCertifyCode.trim().equals("7012")||tCertifyCode.trim().equals("7006")){
					  RnewCommonDataPut tRnewCommonDataPut=new RnewCommonDataPut();
					  if(!tRnewCommonDataPut.submitData(tTransferData))
					  {
						  Content = "����ʧ�ܣ�ԭ����:" + tRnewCommonDataPut.getErrors().getFirstError();
					      FlagStr = "Fail";
					  }
					  tTransferData=tRnewCommonDataPut.getTransferData();
				 } 
				 VData tVData = new VData();
  			 	 tVData.add(tTransferData);
  			 	 tVData.add(tGI);			
				// String busiName="tWorkFlowUI";
				String busiName="WorkFlowUI";
				 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
				 if(!tBusinessDelegate.submitData(tVData,"execute",busiName))
					 {
   							 if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
    						{ 
       							ErrorMessage=tBusinessDelegate.getCErrors().getError(0).moduleName;
			   							Content = "���´�ӡ����ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
			   							FlagStr = "Fail";
			   							 out.println( buildMsg(false, "����ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError()));
			   							 return;
			   							
									}
									else
									{
			   							Content = "���´�ӡ����ʧ��";
			   							FlagStr = "Fail";		
			   							out.println( buildMsg(false, "����ʧ��"));		
			   							return;
									}		
 
 							}
							else
 							{
			  					 out.println( buildMsg(true, "") );  
 							}
				
%>
