<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%
//�������ƣ�AllPBqQuerySubmit.jsp
//�����ܣ���ȫ��������

%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>

<%
  //������Ϣ������У�鴦��
  LPEdorAppSchema tLPEdorAppSchema = new LPEdorAppSchema();
 
  BusinessDelegate tBusinessDelegate;
  ExeSQL tExeSQL = new ExeSQL();
  //�������
  String FlagStr = "";
  String Content = "";
  GlobalInput tGI = new GlobalInput();
  tGI=(GlobalInput)session.getValue("GI");
  String Operator  = tGI.Operator ;  //�����½����Ա�˺�
  String ManageCom = tGI.ComCode  ; //�����½��վ,ManageCom�ڴ��е�½��վ����
  CErrors tError = null;
  SSRS BQMissionData = null;
  //����Ҫִ�еĶ�������ӣ��޸ģ�ɾ��
  String transact = request.getParameter("Transact");
  String DelFlag =  request.getParameter("DelFlag");
  System.out.println("**********ɾ���ı�־�ǣ�"+DelFlag);
  String tEdorAcceptNo=request.getParameter("EdorAcceptNo");
  //����ԭ������
	String CancelReasonContent = request.getParameter("CancelReasonContent");
	//ԭ����
	String 	SCanclReason = request.getParameter("CancelReasonCode");
	TransferData tTransferData = new TransferData();
	VData tVData = new VData();
	tTransferData.setNameAndValue("CancelReasonContent", CancelReasonContent);
	tTransferData.setNameAndValue("SCanclReason", SCanclReason);
	//add by sunsx ���屣ȫ����ʱ�ڲ��õĳ���ԭ��ʹ���͸��ղ�һ����
	tTransferData.setNameAndValue("DelReason", CancelReasonContent);
	tTransferData.setNameAndValue("ReasonCode", SCanclReason);
	String xEdorAcceptNo="";
  //LB0001 ��ȫ���������д��յ���䲢������ 
	try
	{
	    //add by xiongzh ���ӿͻ��㱣ȫ��������
	    //�ж��Ƿ��ǿͻ��㱣ȫ
	    boolean BFControl= true;
	    String mCustomerBQFlag = "0"; //0-�ǿͻ��㣻1-�ͻ��㱣ȫ
		
		
      String sql1 ="select EdorAcceptNo from lpedorapp where othernotype='1' and EdorAcceptNo ='"+tEdorAcceptNo+"'";
      tTransferData=new TransferData();
      tTransferData.setNameAndValue("SQL", sql1);
      tVData = new VData();
      tVData.add(tTransferData);
      tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
      if(tBusinessDelegate.submitData(tVData, "getOneValue", "ExeSQLUI"))
      {
          xEdorAcceptNo=(String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0);
      }	

		
		if(xEdorAcceptNo!=null&&!xEdorAcceptNo.equals("")&&DelFlag.equals("EdorApp")) //�ͻ��㱣ȫ,��������֮����Ҫѭ���Ը�������ż���
		{
			mCustomerBQFlag="1";
			String mEdorAcceptNo ="";
			
			String sql2=" select edoracceptno from lpconttempinfo where icedoracceptno='"+xEdorAcceptNo+"'" ;
      tTransferData=new TransferData();
      tTransferData.setNameAndValue("SQL", sql2);
      tVData = new VData();
      tVData.add(tTransferData);
      tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
      if(tBusinessDelegate.submitData(tVData, "getOneValue", "ExeSQLUI"))
      {
          mEdorAcceptNo=(String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0);
      }		
				
			String sql3="select icedoracceptno from lpconttempinfo "+" where edoracceptno='"+mEdorAcceptNo+"'  union select '"+xEdorAcceptNo+"' from dual ";
      tTransferData=new TransferData();
      tTransferData.setNameAndValue("SQL", sql3);
      tVData = new VData();
      tVData.add(tTransferData);
      tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
      if(tBusinessDelegate.submitData(tVData, "execSQL", "ExeSQLUI"))
      {
          BQMissionData=(SSRS)tBusinessDelegate.getResult().getObjectByObjectName("SSRS", 0);
      }	

			if(BQMissionData.MaxRow==0)
			{
				  BFControl= false;
				  Content = "����ʧ�ܣ�ԭ����:�ͻ��㱣ȫ��ѯ����������ʧ��!" ;
			    FlagStr = "Fail";
			}
			for(int i=1;i<=BQMissionData.MaxRow;i++)
			{
        tTransferData=new TransferData();
        tTransferData.setNameAndValue("OperatedNo", BQMissionData.GetText(i,1));
        tTransferData.setNameAndValue("LockModule", "LB0001");
        tTransferData.setNameAndValue("Operator",  tGI.Operator);
        tVData = new VData();
        tVData.add(tTransferData);			
        tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			  if(!tBusinessDelegate.submitData(tVData, "lock3String", "PubConcurrencyLockUI"))
				{
					  BFControl= false;
					  Content = "����ʧ�ܣ�ԭ����:�ͻ��㱣ȫ�������:"+BQMissionData.GetText(i,1)+"�������Ƽ���ʧ��!" ;
				    FlagStr = "Fail";
				}
			}
		}
		else //һ�㳷��
		{
        	tTransferData=new TransferData();
        	tTransferData.setNameAndValue("OperatedNo", tEdorAcceptNo);
        	tTransferData.setNameAndValue("LockModule", "LB0001");
        	tTransferData.setNameAndValue("Operator",  tGI.Operator);
        	tVData = new VData();
        	tVData.add(tTransferData);					
		    tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			BFControl=tBusinessDelegate.submitData(tVData, "lock3String", "PubConcurrencyLockUI");
		}
		//LB0001 ��ȫ���������д��յ���䲢������ 
		if (BFControl == true) 
		{
			   
//			���˱�ȫ����
			if(DelFlag.equals("EdorItem"))
			{
				  //ɾ����ȫ��Ŀ
				  LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
				  tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
				  tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
				  tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
				  tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
				  tLPEdorItemSchema.setEdorState(request.getParameter("EdorItemState"));
				  tLPEdorItemSchema.setInsuredNo(request.getParameter("InsuredNo"));
				  tLPEdorItemSchema.setPolNo(request.getParameter("PolNo"));
				  tLPEdorItemSchema.setMakeDate(request.getParameter("MakeDate"));
				  tLPEdorItemSchema.setMakeTime(request.getParameter("MakeTime"));
          
				  tTransferData.setNameAndValue("EdorAcceptNo", request.getParameter("EdorAcceptNo"));

					// ׼���������� VData
					tVData.addElement(tGI);
					tVData.addElement(tLPEdorItemSchema);
					tVData.addElement(tTransferData);

          String busiName="PGrpEdorCancelUI";
          tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
          if(!tBusinessDelegate.submitData(tVData,"I&EDORITEM",busiName))
          {    
              if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
              { 
				          Content = "����ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
				          FlagStr = "Fail";
				      }
				      else
				      {
				          Content = "����ʧ��";
				          FlagStr = "Fail";				
				      }		
	 
	       }
	       else
	       {
				    Content ="�����ɹ���";
		    	  FlagStr = "Succ";	
	       }	 					
			}

			if (DelFlag.equals("EdorMain"))
			{
				//ɾ������
				LPEdorMainSchema tLPEdorMainSchema = new LPEdorMainSchema();
				tLPEdorMainSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
				tLPEdorMainSchema.setEdorNo(request.getParameter("EdorNo"));
				tLPEdorMainSchema.setEdorState(request.getParameter("EdorMainState"));
				tLPEdorMainSchema.setContNo(request.getParameter("ContNo"));

				tTransferData.setNameAndValue("EdorAcceptNo", request.getParameter("EdorAcceptNo"));

 
				// ׼���������� VData
				tVData.addElement(tGI);
				tVData.addElement(tLPEdorMainSchema);
				tVData.addElement(tTransferData);

        String busiName="PGrpEdorCancelUI";
        tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
        if(!tBusinessDelegate.submitData(tVData,"I&EDORMAIN",busiName))
        {    
            if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
            { 
				        Content = "����ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
				        FlagStr = "Fail";
				    }
				    else
				    {
				        Content = "����ʧ��";
				        FlagStr = "Fail";				
				    }		
	 
	       }
	       else
	       {
				    Content ="�����ɹ���";
		    	  FlagStr = "Succ";	
	       }	 					
			}

			if (DelFlag.equals("EdorApp") || DelFlag.equals("GEdorApp"))
			{
				//������ȫ����		
				tLPEdorAppSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
				tLPEdorAppSchema.setEdorState(request.getParameter("EdorState"));

			//	String Activityid = "0000000008";
			 
 			 	tTransferData.setNameAndValue("ActivityID",request.getParameter("ActivityID"));
 			 	tTransferData.setNameAndValue("BusiType", "1002");
 			 	String xActivityID;
				if(DelFlag.equals("GEdorApp"))
				{
				    xActivityID = "0000008008";
				    tTransferData.setNameAndValue("ActivityID",xActivityID);
				}

				String tMissionID = request.getParameter("MissionID");
				String tSubMissionID = request.getParameter("SubMissionID");

				tTransferData.setNameAndValue("MissionID",tMissionID);
				tTransferData.setNameAndValue("SubMissionID",tSubMissionID);

				tTransferData.setNameAndValue("EdorAcceptNo", request.getParameter("EdorAcceptNo"));
 
			  // ׼���������� VData
			  tVData.addElement(tGI);
			  tVData.addElement(tLPEdorAppSchema);
				tVData.addElement(tTransferData);

       // String busiName="tWorkFlowUI";
        String busiName="WorkFlowUI";
        tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
        if(!tBusinessDelegate.submitData(tVData,"create",busiName))
        {    
            if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
            { 
				        Content = "����ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
				        FlagStr = "Fail";
				    }
				    else
				    {
				        Content = "����ʧ��";
				        FlagStr = "Fail";				
				    }		
	 
	       }
	       else
	       {
				    Content ="�����ɹ���";
		    	  FlagStr = "Succ";	
	       }	 			
			}

//			���屣ȫ����

			if(DelFlag.equals("GrpEdorItem"))
			{
				//ɾ�����屣ȫ��Ŀ
				LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
				tLPGrpEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
				tLPGrpEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
				tLPGrpEdorItemSchema.setGrpContNo(request.getParameter("GrpContNo"));
				tLPGrpEdorItemSchema.setEdorType(request.getParameter("EdorType"));
 
					// ׼���������� VData
				tVData.addElement(tGI);
				tVData.addElement(tLPGrpEdorItemSchema);
				tVData.addElement(tTransferData);
 
        String busiName="PGrpEdorCancelUI";
        tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
        if(!tBusinessDelegate.submitData(tVData,"G&EDORITEM",busiName))
        {    
            if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
            { 
				        Content = "����ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
				        FlagStr = "Fail";
				    }
				    else
				    {
				        Content = "����ʧ��";
				        FlagStr = "Fail";				
				    }		
	 
	       }
	       else
	       {
				    Content ="�����ɹ���";
		    	  FlagStr = "Succ";	
	       }	 					
			}

			if(DelFlag.equals("GEdorItem"))
			{
				//ɾ�����屣ȫ��Ŀ�¸���
				LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
				tLPGrpEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
				tLPGrpEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
				tLPGrpEdorItemSchema.setGrpContNo(request.getParameter("GrpContNo"));
				tLPGrpEdorItemSchema.setEdorType(request.getParameter("EdorType"));

				LPEdorItemSet mLPEdorItemSet = new LPEdorItemSet();

				LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
				tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
				tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
				tLPEdorItemSchema.setGrpContNo(request.getParameter("GrpContNo"));
				tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
				tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
				tLPEdorItemSchema.setInsuredNo(request.getParameter("CustomerNo"));
				tLPEdorItemSchema.setPolNo("000000");

				mLPEdorItemSet.add(tLPEdorItemSchema);
 
				// ׼���������� VData
				tVData.addElement(tGI);
				tVData.addElement(tLPGrpEdorItemSchema);
				tVData.addElement(mLPEdorItemSet);
				tVData.addElement(tTransferData);
 
        String busiName="PGrpEdorCancelUI";
        tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
        if(!tBusinessDelegate.submitData(tVData,"G&I&EDORITEM",busiName))
        {    
            if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
            { 
				        Content = "����ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
				        FlagStr = "Fail";
				    }
				    else
				    {
				        Content = "����ʧ��";
				        FlagStr = "Fail";				
				    }		
	 
	       }
	       else
	       {
				    Content ="�����ɹ���";
		    	  FlagStr = "Succ";	
	       }	 					
			}


//			<!-- XinYQ added on 2006-04-24 : ��ȫ��Ŀ�������ֲ��� : BGN -->
//			==========================================================================

//			ɾ�����屣ȫ��Ŀ������
			if (DelFlag.equalsIgnoreCase("GEdorRisk"))
			{
			    //LPGrpEdorItemSchema
				LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
				tLPGrpEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
				tLPGrpEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
				tLPGrpEdorItemSchema.setEdorType(request.getParameter("EdorType"));
				tLPGrpEdorItemSchema.setGrpContNo(request.getParameter("GrpContNo"));
				//TransferData
			    tTransferData.setNameAndValue("EdorAcceptNo", request.getParameter("EdorAcceptNo"));
			    tTransferData.setNameAndValue("EdorNo", request.getParameter("EdorNo"));
			    tTransferData.setNameAndValue("EdorType", request.getParameter("EdorType"));
			    tTransferData.setNameAndValue("GrpContNo", request.getParameter("GrpContNo"));
			    tTransferData.setNameAndValue("GrpPolNo", request.getParameter("GrpPolNo"));

				//VData
				tVData.addElement(tGI);
				tVData.addElement(tLPGrpEdorItemSchema);
				tVData.addElement(tTransferData);
				
        String busiName="PGrpEdorCancelUI";
        tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
        if(!tBusinessDelegate.submitData(tVData,"G&EdorRisk",busiName))
        {    
            if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
            { 
				        Content = "����ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
				        FlagStr = "Fail";
				    }
				    else
				    {
				        Content = "����ʧ��";
				        FlagStr = "Fail";				
				    }		
	      }
	       else
	       {
				    Content ="�����ɹ���";
		    	  FlagStr = "Succ";	
		    	  out.println("<script language='JavaScript'>");
			      out.println("    try { parent.fraInterface.queryBothGrpRiskGrid() } catch (ex) {};");
			      out.println("</script>");
	       }	 					 

			}

//			==========================================================================
//			<!-- XinYQ added on 2006-04-24 : ��ȫ��Ŀ�������ֲ��� : BGN -->


			if(DelFlag.equals("PolEdorItem"))
			{
				//ɾ�����屣ȫ��Ŀ�¸���
				LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
				tLPGrpEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
				tLPGrpEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
				tLPGrpEdorItemSchema.setGrpContNo(request.getParameter("GrpContNo"));
				tLPGrpEdorItemSchema.setEdorType(request.getParameter("EdorType"));

				LPEdorItemSet mLPEdorItemSet = new LPEdorItemSet();

				LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
				tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
				tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
				tLPEdorItemSchema.setGrpContNo(request.getParameter("GrpContNo"));
				tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
				tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
				tLPEdorItemSchema.setInsuredNo(request.getParameter("CustomerNo"));
				tLPEdorItemSchema.setPolNo(request.getParameter("PolNo"));

				mLPEdorItemSet.add(tLPEdorItemSchema);

 
				// ׼���������� VData
				tVData.addElement(tGI);
				tVData.addElement(tLPGrpEdorItemSchema);
				tVData.addElement(mLPEdorItemSet);
				tVData.addElement(tTransferData);
 
        String busiName="PGrpEdorCancelUI";
        tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
        if(!tBusinessDelegate.submitData(tVData,"G&I&EDORITEM",busiName))
        {    
            if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
            { 
				        Content = "����ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
				        FlagStr = "Fail";
				    }
				    else
				    {
				        Content = "����ʧ��";
				        FlagStr = "Fail";				
				    }		
	 
	       }
	       else
	       {
				    Content ="�����ɹ���";
		    	  FlagStr = "Succ";	
	       }	 					
			}			
			
		}
		else
	 {
			if("".equals(Content))
			{
			  Content = "�������ڽ������д��գ��뷵��";
			}
		    FlagStr = "Fail";
		}

	}
	finally 
	{
		//ע�����
		if(xEdorAcceptNo!=null&&!xEdorAcceptNo.equals("")&&DelFlag.equals("EdorApp")) //�ͻ��㱣ȫ,��������֮����Ҫѭ���Ը�������ż���
		{
			if(BQMissionData.MaxRow==0){
				Content = "����ʧ�ܣ�ԭ����:�ͻ��㱣ȫ��ѯ����������ʧ��!" ;
			    FlagStr = "Fail";
			}
			for(int i=1;i<=BQMissionData.MaxRow;i++)
			{
        		tTransferData=new TransferData();
        		tTransferData.setNameAndValue("OperatedNo", BQMissionData.GetText(i,1));
        		tTransferData.setNameAndValue("LockModule", "LB0001");
        		tTransferData.setNameAndValue("Operator",  tGI.Operator);
        		tVData = new VData();
        		tVData.add(tTransferData);			
        		tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			  	if(!tBusinessDelegate.submitData(tVData, "unLockJSP", "PubConcurrencyLockUI"))
				{
					Content = "����ʧ�ܣ�ԭ����:�ͻ��㱣ȫ�������:"+BQMissionData.GetText(i,1)+"�������ƽ���ʧ��!" ;
				    FlagStr = "Fail";
				}
			}
			

			}else{
	        	tTransferData=new TransferData();
	        	tVData = new VData();
        		tTransferData.setNameAndValue("OperatedNo", tEdorAcceptNo);
        		tTransferData.setNameAndValue("LockModule", "LB0001");
        		tTransferData.setNameAndValue("Operator",  tGI.Operator);
	        	tVData.add(tTransferData);
	        	tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
				if(!tBusinessDelegate.submitData(tVData, "unLockJSP", "PubConcurrencyLockUI"))
				{
					Content = "����ʧ�ܣ�ԭ����:"+ tBusinessDelegate.getCErrors().getFirstError();
					FlagStr = "Fail";
				}
			}
		}
%>

<html>
<head>
    <script language="JavaScript">
        try
        {
            parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');
        }
        catch (ex)
        {
            alert('<%=Content%>');
        }
    </script>
</html>
