<%
//�������ƣ�GEdorAcptAppConfirmSubmit.jsp
//�����ܣ�����ȷ��
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.sys.*"%>
<%@page import="com.sinosoft.lis.bq.*"%>

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
    String busiName="EdorWorkFlowUI";
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    
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
 
    String sOtherNoType = request.getParameter("OtherNoType");
    TransferData tTransferData = new TransferData();
    tTransferData.setNameAndValue("EdorAcceptNo",request.getParameter("EdorAcceptNo"));
    tTransferData.setNameAndValue("OtherNo", request.getParameter("OtherNo"));
    tTransferData.setNameAndValue("OtherNoType", request.getParameter("OtherNoType"));
    tTransferData.setNameAndValue("EdorAppName", request.getParameter("EdorAppName"));
    tTransferData.setNameAndValue("Apptype", request.getParameter("AppType")); 
    tTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    tTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
    tTransferData.setNameAndValue("ManageCom", tG.ManageCom);
	tTransferData.setNameAndValue("AppntName", request.getParameter("GrpName"));
	
	//tTransferData.setNameAndValue("PaytoDate", request.getParameter("PaytoDate"));

	tTransferData.setNameAndValue("PrtNo", request.getParameter("PrtNo")); //�˱�֪ͨ��ӡˢ��
    
    
  
    VData tVData = new VData();   
    CErrors tError = null;   
    String FlagStr = "";
    String Content = "";
    String transact = "";
    String tOperate = "";     

	String sLoadFlag = request.getParameter("LoadFlag");
	if (sLoadFlag.equals("edorApp") || sLoadFlag.equals("scanApp"))
	{
		transact = "0000008003";  //����ȷ��
	}
	if (sLoadFlag.equals("approveModify"))
	{
		transact = "0000008010";  //�����޸�
	}
	loggerDebug("GEdorAcptAppConfirmSubmit","===LoadFlag====" + sLoadFlag);	
	loggerDebug("GEdorAcptAppConfirmSubmit","===transact====" + transact);
    try
    {              
        tVData.addElement(tLPEdorAppSchema);            
        tVData.addElement(tG);                
        tVData.addElement(tTransferData);  
           
        tBusinessDelegate.submitData(tVData,transact,busiName);
                   
    }
    catch(Exception ex)
    {
        ex.printStackTrace();
        Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
        FlagStr = "Fail";
    }           
    
    //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
    if (FlagStr.equals(""))
    {
        tError = tBusinessDelegate.getCErrors();
        //����ɹ�����������������ӡ���ݣ��ٵ����Զ��˱�����
        if (!tError.needDealError())
        {           	
        	
        	//��Ҫ���²�ѯ�Զ��˱��ڵ��������ID
        	
		      String sql = " select submissionid from lwmission " +
		                   " where activityid = '0000008004' " +
		                   " and missionid = '" + 
		                   request.getParameter("MissionID") + 
		                   "'";
		     String sNewSubMissionID = "";         
		      
		    String busiNameExeSQL="ExeSQLUI";
		    BusinessDelegate tBusinessDelegateExeSQL=BusinessDelegate.getBusinessDelegate();
		    VData cInputDataExeSQL = new VData();
		    TransferData tTransferDataExeSQL = new TransferData();
		   
		    tTransferDataExeSQL.setNameAndValue("SQL",sql);
		    cInputDataExeSQL.add(tTransferDataExeSQL);
		    boolean dealFlag = tBusinessDelegateExeSQL.submitData(cInputDataExeSQL,"getOneValue",busiNameExeSQL);
		    if(dealFlag){
		    	VData responseVData = tBusinessDelegateExeSQL.getResult();
		    	sNewSubMissionID =(String) responseVData.getObjectByObjectName("String", 0);
		    	
		    }             

		      //ExeSQL exeSQL = new ExeSQL();
		      //String sNewSubMissionID = exeSQL.getOneValue(sql);
		      tTransferData.removeByName("SubMissionID");
			  tTransferData.setNameAndValue("SubMissionID", sNewSubMissionID);

            tVData.clear();
            tVData.addElement(tLPEdorAppSchema); 
            tVData.addElement(tTransferData);           
            tVData.addElement(tG); 


            if (!tBusinessDelegate.submitData(tVData,"0000008004",busiName))
            {
                Content = "�����ɹ��������Զ��˱�ʧ�ܣ�ԭ����:" 
                        + tBusinessDelegate.getCErrors().getFirstError();
                FlagStr = "Fail";
            }
            else
            {
                VData mResult = new VData();
                mResult = tBusinessDelegate.getResult();
                try
                {
                    
                        
                    TransferData mNTransferData = 
                        (TransferData)
                        mResult.getObjectByObjectName("TransferData", 0);
                        
                    String tUWState = 
                        (String)
                        mNTransferData.getValueByName("UWFlag");
                        
                    if ("5".equals(tUWState))
                    {
                        Content = "ȷ�ϳɹ�������û��ͨ���Զ��˱�,��Ҫ�����˹��˱�";
                        FlagStr = "Succ";
                    }
                    else
                    {
                        Content = "ȷ�ϳɹ���ͨ���Զ��˱�";
                        FlagStr = "Succ";
                    }
                      
                }
                catch (Exception ex)
                {
                    Content = "ȷ�ϳɹ������ǻ���Զ��˱����ʧ�ܣ�" + ex.toString();
                }
            }
            
        }
        else 
        {
            Content = "ȷ��ʧ�ܣ�ԭ����:" + tError.getFirstError();
            FlagStr = "Fail";
        }
    }

%>     
<html>                 
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

