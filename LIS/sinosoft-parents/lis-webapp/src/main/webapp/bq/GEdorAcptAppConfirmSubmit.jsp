<%
//程序名称：GEdorAcptAppConfirmSubmit.jsp
//程序功能：申请确认
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
	tLPEdorAppSchema.setPayGetName(request.getParameter("PayGetName"));  //补退费领取人
	tLPEdorAppSchema.setPersonID(request.getParameter("PersonID"));  //补退费领取人身份证号
 
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

	tTransferData.setNameAndValue("PrtNo", request.getParameter("PrtNo")); //核保通知书印刷号
    
    
  
    VData tVData = new VData();   
    CErrors tError = null;   
    String FlagStr = "";
    String Content = "";
    String transact = "";
    String tOperate = "";     

	String sLoadFlag = request.getParameter("LoadFlag");
	if (sLoadFlag.equals("edorApp") || sLoadFlag.equals("scanApp"))
	{
		transact = "0000008003";  //申请确认
	}
	if (sLoadFlag.equals("approveModify"))
	{
		transact = "0000008010";  //复核修改
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
        Content = "保存失败，原因是:" + ex.toString();
        FlagStr = "Fail";
    }           
    
    //如果在Catch中发现异常，则不从错误类中提取错误信息
    if (FlagStr.equals(""))
    {
        tError = tBusinessDelegate.getCErrors();
        //如果成功先生成申请批单打印数据，再调用自动核保功能
        if (!tError.needDealError())
        {           	
        	
        	//需要重新查询自动核保节点的子任务ID
        	
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
                Content = "操作成功，但是自动核保失败，原因是:" 
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
                        Content = "确认成功，但是没有通过自动核保,需要进行人工核保";
                        FlagStr = "Succ";
                    }
                    else
                    {
                        Content = "确认成功，通过自动核保";
                        FlagStr = "Succ";
                    }
                      
                }
                catch (Exception ex)
                {
                    Content = "确认成功，但是获得自动核保结果失败！" + ex.toString();
                }
            }
            
        }
        else 
        {
            Content = "确认失败，原因是:" + tError.getFirstError();
            FlagStr = "Fail";
        }
    }

%>     
<html>                 
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

