
<%@page import="com.sinosoft.lis.bq.CustomerBQSplit"%><%
//�������ƣ�PEdorAcptAppConfirmSubmit.jsp
//�����ܣ�
//�������ڣ�2002-07-19 16:49:22
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
//			zhangtao	2005-05-03
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.service.*" %>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<%@page contentType="text/html;charset=GBK" %>
<%
    GlobalInput tG = new GlobalInput();
    tG = (GlobalInput)session.getValue("GI");
    
    LPEdorAppSchema tLPEdorAppSchema   = new LPEdorAppSchema();
    tLPEdorAppSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
    tLPEdorAppSchema.setBankCode(request.getParameter("BankCode"));
    tLPEdorAppSchema.setBankAccNo(request.getParameter("BankAccNo"));
    tLPEdorAppSchema.setAccName(request.getParameter("AccName"));
    tLPEdorAppSchema.setGetForm(request.getParameter("GetPayForm"));
    tLPEdorAppSchema.setPayForm(request.getParameter("GetPayForm"));
	  tLPEdorAppSchema.setPayGetName(request.getParameter("PayGetName"));  //���˷���ȡ��
	  tLPEdorAppSchema.setPersonID(request.getParameter("PersonID"));  //���˷���ȡ�����֤��	
    ExeSQL yexeSQL = new ExeSQL();
    SSRS ySSRS = new SSRS();
    String sqlStr="";
    String sOtherNoType = request.getParameter("OtherNoType");
	  String first_EdorAcceptNo = request.getParameter("EdorAcceptNo");
    TransferData tTransferData = new TransferData();
    tTransferData.setNameAndValue("EdorAcceptNo",request.getParameter("EdorAcceptNo"));
    tTransferData.setNameAndValue("OtherNo", request.getParameter("OtherNo"));
    tTransferData.setNameAndValue("OtherNoType", request.getParameter("OtherNoType"));
    tTransferData.setNameAndValue("EdorAppName", request.getParameter("EdorAppName"));
    tTransferData.setNameAndValue("Apptype", request.getParameter("AppType")); 
    tTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    tTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
  	tTransferData.setNameAndValue("BusiType", "1002");
    tTransferData.setNameAndValue("ManageCom", tG.ManageCom);
//  tTransferData.setNameAndValue("DefaultOperator",tG.Operator);
	  if (sOtherNoType != null && sOtherNoType.trim().equals("3"))
	  {
	   	tTransferData.setNameAndValue("AppntName", request.getParameter("AppntName"));
		  tTransferData.setNameAndValue("PaytoDate", request.getParameter("MainPolPaytoDate"));
  	}
	  else
	  {
		  tTransferData.setNameAndValue("AppntName", "");
		  tTransferData.setNameAndValue("PaytoDate", "");
	  }
 	  sqlStr = " select submissionid,activityid from lwmission " +
			           " where activityid in (select activityid from lwactivity where functionid in('10020001','10020002')) " +
			           " and missionid = '" + request.getParameter("MissionID") + 
			           "'";
 		ySSRS = yexeSQL.execSQL(sqlStr);
  	tTransferData.setNameAndValue("ActivityID",ySSRS.GetText(1,2));
    BusinessDelegate tBusinessDelegate;  
    VData tVData = new VData();   
    CErrors tError = null;   
    String FlagStr = "";
    String Content = "";
    String transact = "";
    String tOperate = "";     
	  String sLoadFlag = request.getParameter("LoadFlag");
	 // if (sLoadFlag.equals("edorApp") || sLoadFlag.equals("scanApp") || sLoadFlag.equals("approveModify"))
	 // {
	 // 	transact = "0000000003";  //����ȷ��
	//  }

     tVData.addElement(tLPEdorAppSchema);            
     tVData.addElement(tG);                
     tVData.addElement(tTransferData);  
     String busiName = "WorkFlowUI";
    // String busiName="tWorkFlowUI";
     tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
     if(!tBusinessDelegate.submitData(tVData,"create",busiName))
     {    
         if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
         { 
		          Content = "����ȷ��ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
		          FlagStr = "Fail";
		      }
		      else
		      {
		          Content = "����ȷ��ʧ��";
		          FlagStr = "Fail";				
		      }		
	  }

	else{
     
		// tError = tBusinessDelegate.getCErrors();
//      //����ɹ�����������������ӡ���ݣ��ٵ����Զ��˱�����
//      if (!tError.needDealError())
//      {   
//      	  //add by xiongzh 09-10-24 ���ӿͻ��㱣ȫ����
//      	  if (sOtherNoType != null && sOtherNoType.trim().equals("3")) //�����㱣ȫά�ֲ���
//      	  {
//      	  
//      	   
//	          	//��Ҫ���²�ѯ�Զ��˱��ڵ��������ID
//			        String sql = " select submissionid from lwmission " +
//			                   " where activityid in (select activityid from lwactivity where functionid='10020009') " +
//			                   " and missionid = '" + request.getParameter("MissionID") + 
//			                   "'";
//	          String sNewSubMissionID;
//	          TransferData sTransferData=new TransferData();
//	          sTransferData.setNameAndValue("SQL", sql);
//            tVData = new VData();
//            tVData.add(sTransferData);
//            tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
//            if(tBusinessDelegate.submitData(tVData, "getOneValue", "ExeSQLUI"))
//            {
//                sNewSubMissionID=(String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0);
//			      tTransferData.removeByName("SubMissionID");
//				  tTransferData.setNameAndValue("SubMissionID", sNewSubMissionID);		 
//            }
//			   
//            sqlStr = " select activityid from lwmission where MissionID = '"+request.getParameter("MissionID")+"'  and activityid in(select activityid from lwactivity where functionid='10020003') and missionprop1='"+request.getParameter("EdorAcceptNo")+"'";
//
//	 	   	  ySSRS = yexeSQL.execSQL(sqlStr);
//	 	   			tTransferData.removeByName("ActivityID");
//	 	   			tTransferData.removeByName("DefaultOperator");
//	 	   			tTransferData.setNameAndValue("BackDate","");
//	 	   			tTransferData.setNameAndValue("BackTime","");
//	 	   			//tTransferData.setNameAndValue("DefaultOperator",tG.Operator);
//		   			tTransferData.setNameAndValue("ActivityID",ySSRS.GetText(1,1));
//					  tTransferData.setNameAndValue("BusiType", "1002");
//	            tVData.clear();
//	            tVData.addElement(tLPEdorAppSchema); 
//	            tVData.addElement(tTransferData);           
//	            tVData.addElement(tG); 
//            BusinessDelegate hBusinessDelegate=BusinessDelegate.getBusinessDelegate();
//            if(!hBusinessDelegate.submitData(tVData,"create",busiName))
//            {
//                if(hBusinessDelegate.getCErrors()!=null&&hBusinessDelegate.getCErrors().getErrorCount()>0)
//                {
//		                   Content = "ȷ�ϳɹ������ǳ����Զ��˱�ʧ�ܣ�ԭ����:" + hBusinessDelegate.getCErrors().getFirstError();
//		                   FlagStr = "Fail";
//		               }
//		               else
//		               {
//		                   Content = "ȷ�ϳɹ������ǳ����Զ��˱�ʧ��";
//		                   FlagStr = "Fail";				
//		               }
//	            }
//            else
//            {	
//                       
//                 TransferData mNTransferData = (TransferData)hBusinessDelegate.getResult().getObjectByObjectName("TransferData", 0);
//                     
//                 String tUWState = (String)mNTransferData.getValueByName("UWFlag");
//                 
//                 
//                 if (!(tUWState.equals("9")))
//                 {
//                     Content = "ȷ�ϳɹ�������ͨ�������Զ��˱�������û��ͨ���Զ��˱�,��Ҫ�����˹��˱�!";
//                     FlagStr = "Succ";
//                 }   
//                 else
//                 {
//                     Content ="ȷ�ϳɹ�����ͨ�������Զ��˱����Զ��˱�";
//                     FlagStr = "Succ";               
//                 }    	                        
//
//	            }
//          } //�����㱣ȫ�Ժ˴������ 
//          if (sOtherNoType != null && sOtherNoType.trim().equals("1")) //�����ͻ��㱣ȫ
//          {
//          	//�ͻ��㱣ȫ�������Ժ�ǰ��Ҫ��������ŵĲ��
//          	CustomerBQSplit tCustomerBQSplit = new CustomerBQSplit();
//          	System.out.println("��ʼ���пͻ��㱣ȫ�����Ժ�ǰ������Ų�ֶ�����");
//          	if (!tCustomerBQSplit.submitData(tVData,""))
//	            {
//	                Content = "ȷ�ϳɹ������ǿͻ��㱣ȫ�Զ��˱�ǰ����Ų��ʧ�ܣ�ԭ����:" 
//	                        + tCustomerBQSplit.mErrors.getFirstError();
//	                FlagStr = "Fail";
//	            }
//          	else
//          	{
//          		String xContent_sub1="";
//          		String xContent="";
//          		int false_count=0;
//          		LPEdorAppSet xLPEdorAppSet = new LPEdorAppSet();
//          		LPEdorAppDB xLPEdorAppDB = new LPEdorAppDB();
//          		xLPEdorAppSet=xLPEdorAppDB.executeQuery("select a.* from lpedorapp a,lpedoritem b,lpconttempinfo c where a.edoracceptno=b.edoracceptno and b.edorno=c.edorno "
//          			+" and c.icedoracceptno=a.edoracceptno and c.edoracceptno='"+first_EdorAcceptNo+"'");
//          		for(int x=1;x<=xLPEdorAppSet.size();x++) //��ֺ���Զ��������Ժ�
//          		{
//          		
//          			LPEdorAppSchema xLPEdorAppSchema = new LPEdorAppSchema();
//          			xLPEdorAppSchema=xLPEdorAppSet.get(x);
//                      //��Ҫ���²�ѯ�Զ��˱��ڵ��������ID
//      			    String sql = " select MissionID,submissionid from lwmission where activityid in(select activityid from lwactivity where functionid='10020003') " +
//      			                   " and MissionProp1 = '"+ xLPEdorAppSchema.getEdorAcceptNo()+ "'";
//      	
//      			    ExeSQL exeSQL = new ExeSQL();
//      			    SSRS xSSRS = new SSRS();
//      			    xSSRS = exeSQL.execSQL(sql);
//      			    if(xSSRS.MaxRow==0)
//      			    {
//      			    	false_count++;
//      			    	xContent_sub1=xContent_sub1+xLPEdorAppSchema.getEdorAcceptNo()+",";
//      			    	xContent = "ȷ�ϳɹ�����������ţ�"+xContent_sub1+"�ͻ��㱣ȫ�Զ��˱�ǰ��ȡ������IDʧ��" ; 
//      			    	Content = Content+xContent;
//  	                    FlagStr = "Fail";
//      			    }
//      			    else
//      			    {
//      			    	String xMissionID = xSSRS.GetText(1,1);
//	        			    String xSubMissionID = xSSRS.GetText(1,2);
//	        			    tTransferData = new TransferData();
//	        			    tTransferData.setNameAndValue("EdorAcceptNo",xLPEdorAppSchema.getEdorAcceptNo());
//	        			    tTransferData.setNameAndValue("OtherNo", request.getParameter("OtherNo"));
//	        			    tTransferData.setNameAndValue("OtherNoType", request.getParameter("OtherNoType"));
//	        			    tTransferData.setNameAndValue("EdorAppName", request.getParameter("EdorAppName"));
//	        			    tTransferData.setNameAndValue("Apptype", request.getParameter("AppType")); 
//	        			    tTransferData.setNameAndValue("MissionID",xMissionID);
//	        			    tTransferData.setNameAndValue("SubMissionID",xSubMissionID);
//	        			    tTransferData.setNameAndValue("ManageCom", tG.ManageCom);
//	        			    tTransferData.setNameAndValue("AppntName", "");
//	        					tTransferData.setNameAndValue("PaytoDate", "");	 
//	        	
//	        	        tVData.clear();
//	        	        tVData.addElement(xLPEdorAppSchema); 
//	        	        tVData.addElement(tTransferData);           
//	        	        tVData.addElement(tG); 
//	        	       // busiName="tWorkFlowUI";
//	        	        busiName="WorkFlowUI";
//
//	        	        ExeSQL yExeSQL = new ExeSQL();
//
//  							
//									tTransferData.setNameAndValue("ActivityID",request.getParameter("ActivityID"));
//	        	        tTransferData.setNameAndValue("BusiType", "1002");
//	        	            
//	        	        tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
//	        	        if (!tBusinessDelegate.submitData(tVData,"create",busiName))
//	        	        {
//	        	            Content = "ȷ�ϳɹ��������Զ��˱�ʧ�ܣ�ԭ����:" 
//	        	                        + tBusinessDelegate.getCErrors().getFirstError();
//	        	            FlagStr = "Fail";
//	        	        }
//	        	        else
//	        	        {
//	        	            	
//	        	            VData mResult = new VData();
//	        	            mResult = tBusinessDelegate.getResult();
//	        	            try
//	        	            {
//	        	                    VData mNData = 
//	        	                        (VData)
//	        	                        mResult.getObjectByObjectName("VData", 0);
//	        	                        
//	        	                    TransferData mNTransferData = 
//	        	                        (TransferData)
//	        	                        mNData.getObjectByObjectName("TransferData", 0);
//	        	                        
//	        	                    String tUWState = 
//	        	                        (String)
//	        	                        mNTransferData.getValueByName("UWFlag");
//	        	                        
//	        	                    if (!"9".equals(tUWState))
//	        	                    {
//	        	                        Content = "ȷ�ϳɹ�������ͨ�������Զ��˱�������û��ͨ���Զ��˱�,��Ҫ�����˹��˱�";
//	        	                        FlagStr = "Succ";
//	        	                    }
//	        	                    else
//	        	                    {
//	        	                        Content = "ȷ�ϳɹ�����ͨ�������Զ��˱����Զ��˱�";
//	        	                        FlagStr = "Succ";
//	        	                    }
//	        	                      
//	        	             }catch (Exception ex)
//	        	                {
//	        	                    Content = "ȷ�ϳɹ������ǻ���Զ��˱����ʧ�ܣ�" + ex.toString();
//	        	                }
//	        	            }
//      			    }
//          		}
//          	}
//          	
//          		
//          }
//      }
//      else 
//      {
//          Content = "ȷ��ʧ�ܣ�ԭ����:" + tError.getFirstError();
//          FlagStr = "Fail";
//      }

	   Content ="ȷ�ϳɹ�";
       FlagStr = "Succ";     
	 
    }

%>     
<html>                 
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

