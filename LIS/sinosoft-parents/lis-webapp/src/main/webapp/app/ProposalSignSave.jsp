<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�ProposalSignSave.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��HST
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.workflow.tb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
	//�������
	CErrors tError = null;
	String FlagStr = "Succ";
	String Content = "";
	GlobalInput tG = new GlobalInput();
	tG=(GlobalInput)session.getValue("GI");
	LCContSet tLCContSet = new LCContSet();
	String tContent_final  = "";
	try
	{
	  	//������Ϣ
	  	// Ͷ�����б�
		LCPolSet tLCPolSet = new LCPolSet();
		TransferData tTransferData = new TransferData();
		LCContSchema tLCContSchema = null;
		String tProposalNo[] = request.getParameterValues("PublicWorkPoolGrid6");
		String tPtrNo[] = request.getParameterValues("PublicWorkPoolGrid1");
		String tChk[] = request.getParameterValues("InpPublicWorkPoolGridChk");
		String tMissionID[] = request.getParameterValues("PublicWorkPoolGrid9");   
		String tSubMissionID[] = request.getParameterValues("PublicWorkPoolGrid10");    
		String tActivityID[] = request.getParameterValues("PublicWorkPoolGrid12");
		boolean flag = false;
		int proposalCount = tProposalNo.length;
		int j=0;		

	   loggerDebug("ProposalSignSave","proposalCount:"+proposalCount);
	 
		for (int i = 0; i < proposalCount; i++)
		{
		 	flag = false;
			if (tProposalNo[i] != null && tChk[i].equals("1"))
			{			 
			  
				loggerDebug("ProposalSignSave","select contno:" +  tProposalNo[i]);
			 	tLCContSchema = new LCContSchema ();
			 	tLCContSchema.setContNo( tProposalNo[i] );
			 	tLCContSchema.setPrtNo( tPtrNo[i] );
			 	tLCContSet.clear();
		   	tLCContSet.add( tLCContSchema );
		   	tTransferData = new TransferData();
		   	tTransferData.setNameAndValue("MissionID",tMissionID[i] );		   		
	     	tTransferData.setNameAndValue("SubMissionID",tSubMissionID[i] );
	     	tTransferData.setNameAndValue("ContNo",tProposalNo[i]);
	     	loggerDebug("ProposalSignSave","MissionID:" +  tMissionID[i]);
	     	loggerDebug("ProposalSignSave","subMissionId:" +  tSubMissionID[i]);
	   		//tTransferData.setNameAndValue("LCContSet",tLCContSet );
	      j++;
			  flag = true;
			  loggerDebug("ProposalSignSave","flag:::::::::" + flag );
			}

			Content="";
	  	if (flag == true)
	  	{
			  // ׼���������� VData
			  VData tVData = new VData();
			  tVData.add( tG );
			  tVData.add( tLCContSet );
			  tTransferData.setNameAndValue("ActivityID", tActivityID[i]);
			  tVData.add( tTransferData );
			  
		    
			  String busiName="WorkFlowUI";
			  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			  if ( tBusinessDelegate.submitData( tVData, "execute",busiName)== false )
			  {
			  	loggerDebug("ProposalSignSave","Content>>>>>>0:" + Content );
			  	Content = "Ͷ����"+tProposalNo[i]+"ǩ��ʧ�� " ;
			  	FlagStr = "Fail";
			  }
			  
			  int n = tBusinessDelegate.getCErrors().getErrorCount();
			  loggerDebug("ProposalSignSave","flag==="+FlagStr);
			  if( n == 0 && FlagStr.equals("Succ"))
			  {    
		                
		      	Content = "ǩ���ɹ���";
			     	FlagStr = "Succ";			   	
			     	
			     	tContent_final = tContent_final + "\\n"+Content+" ";
		      }
		      else
		      {
			  	String strErr = "\\n";
			  	int err = n; 
			  	if(n==1)
			  	{
			  		 err = n;
			  	}
			  	else
			  	{
			  	   err = n-1 ;
			  	}
			  	for (int t = 0; t < err; t++)
			  	{
			  		strErr += (t+1) + ": " + tBusinessDelegate.getCErrors().getError(t).errorMessage ;
			  	}
			  	if ( FlagStr.equals("Succ"))
			  	{
			  		Content = "����Ͷ����ǩ��ʧ�ܣ�ԭ����: " + strErr;
			  		FlagStr = "Fail";
			  	}
			  	else
			  	{
			  	  Content +="������ǩ����Ϣ:"+ strErr;
			  	}
			  	tContent_final = tContent_final + "\\n"+Content+" ";
			  	
			  } // end of if
			  
			  loggerDebug("ProposalSignSave","Fail:::::::" + FlagStr );
				//tongmeng 2008-01-16 modify
				//��ProposalSignAfterInitService�д���ǩ��������־.
				/*
				if(FlagStr.equals("Fail"))
				{
				  VData mVData = new VData();
				  mVData.add(tG);
					mVData.add(tLCContSet);
					mVData.add(Content);
				  LCSignLogBL tLCSignLogBL = new LCSignLogBL();
				  if(tLCSignLogBL.submitData(mVData,""))
				  {
				    loggerDebug("ProposalSignSave","��־����ɹ�!!");
				  }
				}*/
		  } // end of if
		}
		  loggerDebug("ProposalSignSave","########################");
		    loggerDebug("ProposalSignSave","########################");
			  loggerDebug("ProposalSignSave","########################");
			  loggerDebug("ProposalSignSave","j=="+j);
				
//			  loggerDebug("ProposalSignSave","tContent_final:"+tContent_final);
	    if(j==0){
			  Content="��ѡ��һ��Ͷ������";
			  FlagStr = "Fail";

	    }
	//    Content = tContent_final;
			//tContent_final = Content;
loggerDebug("ProposalSignSave","tContent_final:"+tContent_final);
	
  //end for contset 
  //	String tContent = PubFun.changForJS(tContent_final);
  //	tContent_final = tContent;
 // 	loggerDebug("ProposalSignSave","tContent_final:"+tContent_final);

	} // end of try
	catch ( Exception e1 )
	{
	 e1.printStackTrace();
		Content = "Ͷ����ǩ��ʧ�ܣ�ԭ����: " + e1.toString();
//		Content = "Ͷ����ǩ��ʧ�ܣ�ԭ����: " ;
		FlagStr = "Fail";
	}
	
%>                      
<html>
<script language="javascript">
 // alert("<%=tContent_final%>");  
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=tContent_final%>");


</script>
</html>
