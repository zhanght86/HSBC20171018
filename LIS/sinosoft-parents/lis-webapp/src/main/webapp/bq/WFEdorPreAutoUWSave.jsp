<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%
//WFEdorAutoUWSave.jsp
//�����ܣ���ȫ������-�Զ��˱�
//�������ڣ�2005-04-30 14:02:52
//������  ��zhangtao
//���¼�¼��  ������    ��������     ����ԭ��/����
//      
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%

  //�������
  String FlagStr = "";
  String Content = ""; 
  CErrors tError = null;
  BusinessDelegate tBusinessDelegate;
 
  LPEdorAppSchema tLPEdorAppSchema;
  TransferData tTransferData;
  TransferData tResultData;
  GlobalInput tG = new GlobalInput(); 
  tG = (GlobalInput) session.getValue("GI");
	  //===================
	  String tEdorAcceptNo[]  = request.getParameterValues("PrivateWorkPoolGrid1");
	  String tOtherNo[]       = request.getParameterValues("PrivateWorkPoolGrid2");
	  String tOtherNoType[]   = request.getParameterValues("PrivateWorkPoolGrid9");
	  String tEdorAppName[]   = request.getParameterValues("PrivateWorkPoolGrid10");
	  String tApptype[]       = request.getParameterValues("PrivateWorkPoolGrid11");  
	  String tManageCom[]     = request.getParameterValues("PrivateWorkPoolGrid12");
	  String tAppntName[]     = request.getParameterValues("PrivateWorkPoolGrid4");
	  String tPaytoDate[]     = request.getParameterValues("PrivateWorkPoolGrid13");
	  //===================
	  String tMissionID[]     = request.getParameterValues("PrivateWorkPoolGrid15");
	  String tSubMissionID[]  = request.getParameterValues("PrivateWorkPoolGrid16");
	  String tChk[] = request.getParameterValues("InpPrivateWorkPoolGridChk");
    System.out.println("1111");
    int n = tEdorAcceptNo.length;

    System.out.println(tEdorAcceptNo);
for (int i = 0; i < n; i++)
{
    if (!tChk[i].equals("1"))
    {
        continue;
    }
    //������Ϣ
    tTransferData = new TransferData();
    tTransferData.setNameAndValue("MissionID", tMissionID[i]);
    tTransferData.setNameAndValue("SubMissionID", tSubMissionID[i]);
    tTransferData.setNameAndValue("EdorAcceptNo", tEdorAcceptNo[i]);
    tTransferData.setNameAndValue("OtherNo", tOtherNo[i]);
    tTransferData.setNameAndValue("OtherNoType", tOtherNoType[i]);
    tTransferData.setNameAndValue("EdorAppName", tEdorAppName[i]);
    tTransferData.setNameAndValue("Apptype", tApptype[i]); 
    tTransferData.setNameAndValue("ManageCom", tManageCom[i]);
    tTransferData.setNameAndValue("AppntName", tAppntName[i]);
    tTransferData.setNameAndValue("PaytoDate", tPaytoDate[i]);
        
    //
    tLPEdorAppSchema = new LPEdorAppSchema();
    tLPEdorAppSchema.setEdorAcceptNo(tEdorAcceptNo[i]); 

 		// ExeSQL yExeSQL = new ExeSQL();
   // SSRS ySSRS = new SSRS();
   // String sqlstr="select activityid from lwactivity where functionid='10020033'";
   // ySSRS = yExeSQL.execSQL(sqlstr);
   String xActivityID="";
   // if(ySSRS.MaxRow==0)
   // {}
   // else
   // {
   //     xActivityID = ySSRS.GetText(1,1);
  	//}
  	xActivityID =  request.getParameter("ActivityID");
  	tTransferData.setNameAndValue("ActivityID",xActivityID);
  	System.out.println(xActivityID);
  	
  	tTransferData.setNameAndValue("BusiType", "1002");
    VData tVData = new VData();

    String busiName="WorkFlowUI";
   //  String busiName="tWorkFlowUI";
     /**
     tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
        if(!tBusinessDelegate.submitData(tVData,"create",busiName))
     {    
         if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
         { 
		          Content = "���κ˱�ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
		          FlagStr = "Fail";
		      }
		
	  }
	  else
	  {
          TransferData mNTransferData = (TransferData)tBusinessDelegate.getResult().getObjectByObjectName("TransferData", 0);
              
          String tUWState = (String)mNTransferData.getValueByName("PreUWFlag");
          String tPreUWGrade = (String) mNTransferData.getValueByName("PreUWGrade");
 
          if (!(tUWState.equals("9")))
          {
              Content = "�����Ϊ"+ tEdorAcceptNo[i] + "�ı�ȫ��ҵ��Ҫ����!";
              FlagStr = "Fail";
              continue;
          }   
          else
          {
              Content ="���κ˱��ɹ���";
              FlagStr = "Succ";               
          }                              
	  }	 			
	  **/		
		/**
 
    if ("Succ".equals(FlagStr))
    {
  */
 
        	//��Ҫ���²�ѯ�Զ��˱��ڵ��������ID
		String xMissionID = request.getParameter("MissionID");
		String sql = " select submissionid from lwmission " +
		              " where activityid = '"+xActivityID+"' " +
		              " and missionid = '" + xMissionID + 
		              "'";
		System.out.println(xMissionID);
		String sNewSubMissionID ="";
         tTransferData.setNameAndValue("SQL", sql);
         tVData = new VData();
         tVData.add(tTransferData);
         tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
         if(tBusinessDelegate.submitData(tVData, "getOneValue", "ExeSQLUI"))
         {
             sNewSubMissionID=(String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0);
         }		   		      
		      
		     tTransferData.removeByName("SubMissionID");
			   tTransferData.setNameAndValue("SubMissionID", sNewSubMissionID);		 


  			tTransferData.setNameAndValue("ActivityID",xActivityID);
  		  tTransferData.setNameAndValue("BusiType", "1002");
         tVData.clear();
         tVData.addElement(tLPEdorAppSchema); 
         tVData.addElement(tTransferData);           
         tVData.addElement(tG); 

         //String busiName="EdorWorkFlowUI";
         tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
         if(!tBusinessDelegate.submitData(tVData,"create",busiName))
         {    
             if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
             { 
		              Content = "��ȫ����"+ tEdorAcceptNo[i] + "���κ˱��ɹ��������Զ��˱�ʧ�ܣ�ԭ����:" +tBusinessDelegate.getCErrors().getFirstError();
		              FlagStr = "Fail";
		              continue;
		          }
		          else
		          {
		              Content = "�Զ��˱�ʧ��";
		              FlagStr = "Fail";				
		          }		
	       }
	       else
	       {
 
                 TransferData mNTransferData = (TransferData)tBusinessDelegate.getResult().getObjectByObjectName("TransferData", 0);
                    
                 String tUWFlag = (String)mNTransferData.getValueByName("UWFlag");
                     
                 if (!"9".equals(tUWFlag))
                 {
                     Content = "ͨ�������Զ��˱�������û��ͨ���Զ��˱�,��Ҫ�����˹��˱�";
                     FlagStr = "Succ";
                 }
                 else
                 {
                     Content = "ͨ�������Զ��˱����Զ��˱�";
                     FlagStr = "Succ";
                 }
         }
            
    
}   

%>
   
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
   
   
   
 