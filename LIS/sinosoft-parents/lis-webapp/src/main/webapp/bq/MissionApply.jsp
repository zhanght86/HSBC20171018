<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�MissionApply.jsp
//�����ܣ���ȫ��������������
//�������ڣ�2005-06-29 11:10:36
//������  ��zhangtao
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.service.*" %>  
  
<%
  //�������
  BusinessDelegate tBusinessDelegate;
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
 
  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
  if(tG == null) 
  {
		loggerDebug("MissionApply.jsp","session has expired");
		
		return;
  }

  String sMissionID = request.getParameter("MissionID");
  String sSubMissionID = request.getParameter("SubMissionID");
  String sActivityID = request.getParameter("ActivityID");
 String ConfirmFlag=request.getParameter("Confirm");
  VData tVData ;
  TransferData tTransferData=new TransferData();

  String xEdorAcceptNo = null;
  try
  {
	   //����Ǳ�ȫȷ�����룬���Ӽ������ƣ��Է��Զ����к��ֹ����벢��
	   String sucflag="0";
	   /***��Ϊ��ȫȥ���ñ����ȷ����missionprop1��js����**/
	  // if("0000000009".equals(sActivityID))
		if("1".equals(ConfirmFlag))
	   {	   
	    
        /*String sql1 ="select a.missionprop1 from lwmission a where a.missionid='"+sMissionID+"' and activityid = '"+sActivityID+"' ";
        
        tTransferData.setNameAndValue("SQL", sql1);
        tVData = new VData();
        tVData.add(tTransferData);
        tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
        if(tBusinessDelegate.submitData(tVData, "getOneValue", "ExeSQLUI"))
        {
            xEdorAcceptNo=(String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0);
        }		*/   
        xEdorAcceptNo=request.getParameter("EdorAcceptNo");
        tTransferData=new TransferData();
        tTransferData.setNameAndValue("OperatedNo", xEdorAcceptNo);
        tTransferData.setNameAndValue("LockModule", "BQCA01");
        tTransferData.setNameAndValue("Operator",  tG.Operator);
        tVData = new VData();
        tVData.add(tTransferData);			
        tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			  if(!tBusinessDelegate.submitData(tVData, "lock3String", "PubConcurrencyLockUI"))
				{
					  Content = "����ʧ�ܣ�ԭ����:"+tBusinessDelegate.getCErrors().getFirstError();
				    FlagStr = "Fail";
				    sucflag = "1";
				} 
 
	   }
		  // ׼���������� VData
		  tVData = new VData();
      //tTransferData.setNameAndValue("ApplyType", sApplyType);	
      tTransferData.setNameAndValue("MissionID", sMissionID);
      tTransferData.setNameAndValue("SubMissionID", sSubMissionID);
      tTransferData.setNameAndValue("ActivityID", sActivityID);		
		  
		  tVData.add( tTransferData );
		  tVData.add( tG );
		
		// ���ݴ���
 		
		if("0".equals(sucflag))
		{
         String busiName="cbcheckUWApplyUI";
         tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
         if(!tBusinessDelegate.submitData(tVData,"",busiName))
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
				    Content ="����ɹ���";
		    	  FlagStr = "Succ";					     
		     }	
		     
		}

  }
  catch(Exception e)
  {
	   e.printStackTrace();
	   Content = Content.trim()+".��ʾ���쳣��ֹ!";
  }
  finally
  {
	  
	  	if(xEdorAcceptNo != null && !xEdorAcceptNo.equals("")){
			  TransferData lockTransferData=new TransferData();
			  VData lockVData = new VData();
			 		

			  lockTransferData.setNameAndValue("OperatedNo", xEdorAcceptNo);
			  lockTransferData.setNameAndValue("LockModule", "BQCA01");

			  lockVData.add(lockTransferData);	
		      tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			  if(!tBusinessDelegate.submitData(lockVData, "unLockJSP", "PubConcurrencyLockUI"))
			  {
				 Content = "����ʧ�ܣ�ԭ����:"+ tBusinessDelegate.getCErrors().getFirstError();
				 FlagStr = "Fail";
			  }
	  	}

  }
%>                      
<html>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
