<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//EdorApproveSave.jsp
//�����ܣ���ȫ����
//�������ڣ�2005-05-08 15:59:52
//������  ��sinosoft
//���¼�¼��  ������    ��������     ����ԭ��/����
//      
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
    <%@page import="com.sinosoft.lis.vschema.*"%>
    <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.bq.*"%>
<%

  //�������
  String FlagStr = "";
  String Content = ""; 
  String Flag = "0";
  
  CErrors tError = null;
  
  GlobalInput tG = new GlobalInput(); 
  tG = (GlobalInput) session.getValue("GI");
  
  VData tVData = new VData();
  TransferData tTransferData = new TransferData();
  
  String tMissionID 	= request.getParameter("MissionID");
  String tSubMissionID 	= request.getParameter("SubMissionID");  
  String tApproveFlag 	= request.getParameter("ApproveFlag");
  String tApproveContent= request.getParameter("ApproveContent");
  
  String tEdorPopedom	= request.getParameter("EdorPopedom");
  String tEdorAcceptNo	= request.getParameter("EdorAcceptNo");
  
  String first_EdorAcceptNo = request.getParameter("EdorAcceptNo");
  String sActivityID="";
  String tOtherNo		= request.getParameter("OtherNo");
  String tOtherNoType	= request.getParameter("OtherNoType");
  String tEdorAppName	= request.getParameter("EdorAppName");
  String tApptype		= request.getParameter("Apptype");
  String tManageCom		= request.getParameter("ManageCom");
  String tAppntName		= request.getParameter("AppntName");
  String tPaytoDate		= request.getParameter("PaytoDate");
  String sModifyReason  = request.getParameter("ModifyReason");    //XinYQ addedon 2005-11-28
  String sEdorAppDate  = request.getParameter("EdorAppDate");    //XinYQ addedon 2005-11-28
  
  String tOperator = request.getParameter("operator");  //add by wk
  String tErrorFlag = request.getParameter("ErrorChkFlag"); //add by sunsx 
  String sUpReport="" ;
  tTransferData.setNameAndValue("MissionID", tMissionID);
  tTransferData.setNameAndValue("SubMissionID", tSubMissionID);
  
  //������ȫȷ�ϡ������޸Ľڵ������Ԫ��
  tTransferData.setNameAndValue("EdorAcceptNo", tEdorAcceptNo);
  tTransferData.setNameAndValue("OtherNo", tOtherNo);
  tTransferData.setNameAndValue("OtherNoType", tOtherNoType);
  tTransferData.setNameAndValue("EdorAppName", tEdorAppName);
  tTransferData.setNameAndValue("Apptype", tApptype);
  tTransferData.setNameAndValue("ManageCom", tManageCom);
  tTransferData.setNameAndValue("AppntName", tAppntName);
  tTransferData.setNameAndValue("PaytoDate", tPaytoDate);
  tTransferData.setNameAndValue("ApproveFlag", tApproveFlag);
  tTransferData.setNameAndValue("ApproveContent", tApproveContent);    //������
  tTransferData.setNameAndValue("CheckFlag", "N");
  tTransferData.setNameAndValue("ErrorChkFlag", tErrorFlag); //add by sunsx 
  tTransferData.setNameAndValue("EdorAppDate", sEdorAppDate); //add by sunsx 

  if(tApproveFlag.equals("2")) //�����޸� ��¼ԭ�����˱��� add by wk
  {
  	tTransferData.setNameAndValue("DefaultOperator",tOperator);
  	tTransferData.setNameAndValue("ICFlag","");
  	
  }
  if (sModifyReason != null) tTransferData.setNameAndValue("ModifyReason", sModifyReason);    //XinYQ addedon 2005-11-28
  
  LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
  tLPEdorAppDB.setEdorAcceptNo(tEdorAcceptNo);
  tLPEdorAppDB.getInfo() ;
  LPEdorAppSchema tLPEdorAppSchema =new LPEdorAppSchema();
  tLPEdorAppSchema.setSchema(tLPEdorAppDB.getSchema());
  
  ExeSQL tExeSQL = new ExeSQL();
  
  //add by jiaqiangli 2008-12-29�Խ�������������
  //������� ȡljsgetendorse
  String tEdorTypeSQL = "select edortype from lpedoritem where edoracceptno = '"+tLPEdorAppSchema.getEdorAcceptNo()+"'";
  String tEodeType = tExeSQL.getOneValue(tEdorTypeSQL);
  String tGetMoneySQL = "select abs((case when sum(getmoney*(case getflag when '1' then 1 else -1 end)) is not null then sum(getmoney*(case getflag when '1' then 1 else -1 end)) else 0 end)) from ljsgetendorse where endorsementno in "
	  +"(select edorno from lpedoritem where edoracceptno='"+tLPEdorAppSchema.getEdorAcceptNo()+"') and feeoperationtype = '"+tEodeType+"' and contno = '"+tLPEdorAppSchema.getOtherNo()+"' ";
  String tGetMoney = tExeSQL.getOneValue(tGetMoneySQL);
  tTransferData.setNameAndValue("GetMoney",tGetMoney);
  //�޲��˷�Ϊ�ո�
  String tGetFlag = "";
  if (Double.parseDouble(tGetMoney) > 0) tGetFlag = "1";
  else tGetFlag = "0";
  
  tTransferData.setNameAndValue("GetFlag",tGetFlag);
  //tTransferData.setNameAndValue("CurEdorPopedom",tEdorPopedom);
  //add by jiaqiangli 2008-12-29�Խ�������������
  
  //add by jiaqiangli 2009-01-24 ��ȫ����Ȩ�����ָ�����������
  //String tMainRiskCodeSQL = "select riskcode from lcpol where polno=mainpolno and contno = (select distinct contno from lpedoritem where edoracceptno='"+tLPEdorAppSchema.getEdorAcceptNo()+"')";
  String tMainRiskCodeSQL = "select bqcsiyrisk('"+tLPEdorAppSchema.getOtherNo()+"') from dual";
  //String tMainRiskCode = tExeSQL.getOneValue(tMainRiskCodeSQL);
	//String tRiskProp = "";
	String tRiskProp = tExeSQL.getOneValue(tMainRiskCodeSQL);
	//String[] tYDRisk = {"312201","312202","312203","312204","312205","312206"};
	//for (int i=0;i<tYDRisk.length;i++) {
		//if (tMainRiskCode != null && tMainRiskCode.equals(tYDRisk[i])) {
			//tRiskProp = "Y";
			//break;
		//}
	//}
	//tRiskProp = "I";
	tTransferData.setNameAndValue("ContNo",tLPEdorAppSchema.getOtherNo());
	tTransferData.setNameAndValue("RiskProp",tRiskProp);
  //add by jiaqiangli 2009-01-24 ��ȫ����Ȩ�����ָ�����������
  
  //�Ƿ���Ҫ�ϼ����� start
  String tSQL="   select edorpopedom,usertype from ldedoruser where usercode='"+tG.Operator+"' and usertype='1' ";
  String tedorpopedom=tExeSQL.getOneValue(tSQL);
  if("".equals(tedorpopedom))
  {
  	Content = "��ѯ����Ա����ʧ��";
	FlagStr = "Fail";
  }
  else
  {
	  //�˴�Ӧ���ǵ�ǰ�û���Ȩ��tedorpopedom����tEdorPopedom
	  tTransferData.setNameAndValue("CurEdorPopedom",tedorpopedom);
  //�Ƿ���Ҫ�ϼ����� end
 // EdorWorkFlowUI tEdorWorkFlowUI = new EdorWorkFlowUI(); 
    
   
  tTransferData.setNameAndValue("ActivityID", request.getParameter("ActivityID"));
  tTransferData.setNameAndValue("BusiType", "1002");
  tVData.add(tG);		
  tVData.add(tTransferData);
	String ErrorMessage = "";
  //String busiName="tWorkFlowUI";
  String busiName="WorkFlowUI";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  if(!tBusinessDelegate.submitData(tVData,"create",busiName))
  {    
        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
        { 
           ErrorMessage=tBusinessDelegate.getCErrors().getError(0).moduleName;
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
				if ("1".equals(tApproveFlag) )  
        {				        
        			Content = "���˳ɹ���";
        			FlagStr = "Succ";
        			//Content += "���ӡ����֪ͨ�飬��ȥ���񽻷�";               			
        }
       else if(tApproveFlag.equals("2")){
        			Content = "����ɹ�!";
        			FlagStr = "Succ";
        			Content += "�뷵��¼����������޸ģ�";                   			
        }
       else if(tApproveFlag.equals("3")){
        		 Content = "����ɹ�!";
        		 FlagStr = "Succ";
        	 	 Content += "�뷵�أ�";                   			
        }
       else
        {
        			Content = "���˳ɹ���";
        			FlagStr = "Succ";
        			//Content += "������ȫȷ��";    
        }
	}	 
	 
	

	//��������Ϊͨ�����Ҹ��ϳɹ��������Զ��˱�
	if(false)
	//if("Succ".equals(FlagStr) && "1".equals(tApproveFlag))
		{
    	//����׼���Զ��˱��ڵ��������ID������Ԫ��
	    String sOtherNoType = request.getParameter("OtherNoType");
		if (sOtherNoType != null && sOtherNoType.trim().equals("3"))
		{
			tTransferData.removeByName("AppntName");
			tTransferData.removeByName("PaytoDate");
			tTransferData.setNameAndValue("AppntName", request.getParameter("AppntName"));
			tTransferData.setNameAndValue("PaytoDate", request.getParameter("tPaytoDate"));
		}
		else
		{
			tTransferData.removeByName("AppntName");
			tTransferData.removeByName("PaytoDate");
			tTransferData.setNameAndValue("AppntName", "");
			tTransferData.setNameAndValue("PaytoDate", "");
		}
		String sqlstr="select activityid from lwactivity where functionid='10020004'";
		ExeSQL yexeSQL = new ExeSQL();
		SSRS ySSRS = new SSRS();
  	ySSRS = yexeSQL.execSQL(sqlstr);
  	if(ySSRS.MaxRow==0)
  	{}
  	else
  	{
      	sActivityID = ySSRS.GetText(1,1);
  	}
	    //	add by xiongzh ���ӶԿͻ��㱣ȫ��Ĵ���
	    if (sOtherNoType != null && sOtherNoType.trim().equals("3")) //�����㱣ȫά�ֲ���
	    {	
	       String sql = " select submissionid from lwmission " +
	                   " where activityid = '"+sActivityID+"' " +
	                   " and missionid = '" + 
	                   request.getParameter("MissionID") + 
	                   "'";

	       ExeSQL exeSQL = new ExeSQL();
	       String sNewSubMissionID = exeSQL.getOneValue(sql);
	      
	       //add by jiaqiangli 2008-12-29 ֻ��Ťת��0000000004��continue 0000000007��ȫ����ʱ���������ıհ��������ڵ�
	       if (!sNewSubMissionID.equals("")) 
	       {
	    	  
		      tTransferData.removeByName("SubMissionID");
			  tTransferData.setNameAndValue("SubMissionID", sNewSubMissionID);
			  
			  //LPEdorAppSchema tLPEdorAppSchema= new LPEdorAppSchema();
			 
			  //LPEdorAppDB tLPEdorAppDB= new LPEdorAppDB();
			  //tLPEdorAppDB.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));		  
			  //tLPEdorAppSchema=tLPEdorAppDB.query().get(1);
			    tTransferData.removeByName("ActivityID");
			  	tTransferData.setNameAndValue("ActivityID", sActivityID);
  				tTransferData.setNameAndValue("BusiType", "1002");
	        tVData.clear();
	  		  tVData.addElement(tLPEdorAppSchema);
	        tVData.addElement(tTransferData);           
	        tVData.addElement(tG); 
	       // busiName="tWorkFlowUI";
	        busiName="WorkFlowUI";
  			  BusinessDelegate hBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  			  if(!hBusinessDelegate.submitData(tVData,"create",busiName)){
		          Content = "����ȷ�ϳɹ��������Զ��˱�ʧ�ܣ�ԭ����:" 
		                  + hBusinessDelegate.getCErrors().getFirstError();
		          FlagStr = "Fail";
		      }
		      else
		      {
		          VData mResult = new VData();
		          mResult = hBusinessDelegate.getResult();
		          try
		          {
		              //VData mNData = 
		                 //(VData)
		                 // mResult.getObjectByObjectName("VData", 0);
		                  
		              TransferData mNTransferData = 
		                  (TransferData)
		                  mResult.getObjectByObjectName("TransferData", 0);
		                  
		              String tUWState = 
		                  (String)
		                  mNTransferData.getValueByName("UWFlag");
		                  
		              if ("5".equals(tUWState))
		              {
		                  Content = "����ȷ�ϳɹ�������û��ͨ���Զ��˱�,��Ҫ�����˹��˱�";
		                  FlagStr = "Succ";
		              }
		              else
		              {
		                  Content = "����ȷ�ϳɹ�����ͨ���Զ��˱���������ȫȷ��";
		                  FlagStr = "Succ";
		              }
		                
		          }
		          catch (Exception ex)
		          {
		              Content = "����ȷ�ϳɹ������ǻ���Զ��˱����ʧ�ܣ�" + ex.toString();
		          }
		      }
	      } //end add by jiaqiangli 
	    } //�������Ժ˴������
	    if (sOtherNoType != null && sOtherNoType.trim().equals("1")) //�����ͻ��㱣ȫ
        {
        	//�ͻ��㱣ȫ�������Ժ�ǰ��Ҫ��������ŵĲ��
        	CustomerBQSplit tCustomerBQSplit = new CustomerBQSplit();
        	loggerDebug("EdorApproveSave","��ʼ���пͻ��㱣ȫ�����Ժ�ǰ������Ų�ֶ�����");
        	if (!tCustomerBQSplit.submitData(tVData,""))
            {
                Content = "����ȷ�ϳɹ������ǿͻ��㱣ȫ�Զ��˱�ǰ����Ų��ʧ�ܣ�ԭ����:" 
                        + tBusinessDelegate.getCErrors().getFirstError();
                FlagStr = "Fail";
            }
        	else
        	{
        		String xContent_sub1="";
        		String xContent="";
        		int false_count=0;
        		LPEdorAppSet xLPEdorAppSet = new LPEdorAppSet();
        		LPEdorAppDB xLPEdorAppDB = new LPEdorAppDB();   	
        		xLPEdorAppSet=xLPEdorAppDB.executeQuery("select a.* from lpedorapp a,lpedoritem b,lpconttempinfo c where a.edoracceptno=b.edoracceptno and b.edorno=c.edorno "
            			+" and c.icedoracceptno=a.edoracceptno and c.edoracceptno='"+first_EdorAcceptNo+"'");
        		for(int x=1;x<=xLPEdorAppSet.size();x++) //��ֺ���Զ��������Ժ�
        		{
        			LPEdorAppSchema xLPEdorAppSchema = new LPEdorAppSchema();
        			xLPEdorAppSchema=xLPEdorAppSet.get(x);
                    //��Ҫ���²�ѯ�Զ��˱��ڵ��������ID
    			    String sql = " select MissionID,submissionid from lwmission where activityid = '"+sActivityID+"' " +
    			                   " and MissionProp1 = '"+ xLPEdorAppSchema.getEdorAcceptNo()+ "'";
    	
    			    ExeSQL exeSQL = new ExeSQL();
    			    SSRS xSSRS = new SSRS();
    			    xSSRS = exeSQL.execSQL(sql);
    			    if(xSSRS.MaxRow==0)
    			    {
    			    	false_count++;
    			    	xContent_sub1=xContent_sub1+xLPEdorAppSchema.getEdorAcceptNo()+",";
    			    	xContent = "����ȷ�ϳɹ�����������ţ�"+xContent_sub1+"�ͻ��㱣ȫ�Զ��˱�ǰ��ȡ������IDʧ��" ; 
    			    	Content = Content+xContent;
	                    FlagStr = "Fail";
    			    }
    			    else
    			    {
    			    		String xMissionID = xSSRS.GetText(1,1);
        			    String xSubMissionID = xSSRS.GetText(1,2);
        			    tTransferData = new TransferData();
        			    tTransferData.setNameAndValue("EdorAcceptNo",xLPEdorAppSchema.getEdorAcceptNo());
        			    tTransferData.setNameAndValue("OtherNo", request.getParameter("OtherNo"));
        			    tTransferData.setNameAndValue("OtherNoType", request.getParameter("OtherNoType"));
        			    tTransferData.setNameAndValue("EdorAppName", request.getParameter("EdorAppName"));
        			    tTransferData.setNameAndValue("Apptype", request.getParameter("AppType")); 
        			    tTransferData.setNameAndValue("MissionID",xMissionID);
        			    tTransferData.setNameAndValue("SubMissionID",xSubMissionID);
        			    tTransferData.setNameAndValue("ManageCom", tG.ManageCom);
        			    tTransferData.setNameAndValue("AppntName", "");
        				  tTransferData.setNameAndValue("PaytoDate", "");	 
        				  tTransferData.setNameAndValue("ActivityID", sActivityID);
  								tTransferData.setNameAndValue("BusiType", "1002");
        	        tVData.clear();
        	        tVData.addElement(xLPEdorAppSchema); 
        	        tVData.addElement(tTransferData);           
        	        tVData.addElement(tG); 
        	       // busiName="tWorkFlowUI";
        	        busiName="WorkFlowUI";
  			  				BusinessDelegate dBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  			  				if(!dBusinessDelegate.submitData(tVData,"create",busiName))
        	        {
        	                Content = "����ȷ�ϳɹ��������Զ��˱�ʧ�ܣ�ԭ����:" 
        	                        + dBusinessDelegate.getCErrors().getFirstError();
        	                FlagStr = "Fail";
        	         }
        	         else
        	         {
        	                VData mResult = new VData();
        	                mResult = dBusinessDelegate.getResult();
        	                try
        	                {
        	                    VData mNData = 
        	                        (VData)
        	                        mResult.getObjectByObjectName("VData", 0);
        	                        
        	                    TransferData mNTransferData = 
        	                        (TransferData)
        	                        mNData.getObjectByObjectName("TransferData", 0);
        	                        
        	                    String tUWState = 
        	                        (String)
        	                        mNTransferData.getValueByName("UWFlag");
        	                        
        	                    if (!"9".equals(tUWState))
        	                    {
        	                        Content = "����ȷ�ϳɹ�������ͨ�������Զ��˱�������û��ͨ���Զ��˱�,��Ҫ�����˹��˱�";
        	                        FlagStr = "Succ";
        	                    }
        	                    else
        	                    {
        	                        Content = "����ȷ�ϳɹ�����ͨ�������Զ��˱����Զ��˱�";
        	                        FlagStr = "Succ";
        	                    }
        	                      
        	                }
        	                catch (Exception ex)
        	                {
        	                    Content = "����ȷ�ϳɹ������ǻ���Զ��˱����ʧ�ܣ�" + ex.toString();
        	                }
        	            }
    			    }
        		}
        	}
        }
    
   }
 }
%>
   
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=Flag%>");
</script>
</html>
   
   
   
 
