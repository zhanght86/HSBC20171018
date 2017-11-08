<%
//**************************************************************************************************
//Name：LLRegisterSave.jsp
//Function：立案信息提交
//Author：zhoulei
//Date: 2005-6-15 16:28
//修改：niuzj,2006-01-13,立案登记时需要将LLRegister表中的领取方式GetMode字段默认置成1
//**************************************************************************************************
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.LWMissionDB"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.workflow.claim.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
loggerDebug("LLClaimRegisterSave","######################LLClaimRegisterSave.jsp start#############################");

//输入参数
LLAccidentSchema tLLAccidentSchema = new LLAccidentSchema(); //事件表
LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema(); //立案表
LLCaseSchema tLLCaseSchema = new LLCaseSchema(); //分立案表
LLAppClaimReasonSchema tLLAppClaimReasonSchema = new LLAppClaimReasonSchema(); //理赔类型表
LLAppClaimReasonSet tLLAppClaimReasonSet = new LLAppClaimReasonSet(); //理赔类型集合


//输出参数
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput();
tGI=(GlobalInput)session.getValue("GI");
//ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI(); //理赔工作流引擎
String busiName="WorkFlowUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();



if(tGI == null)
{
    FlagStr = "Fail";
    Content = "页面失效,请重新登陆";
    loggerDebug("LLClaimRegisterSave","页面失效,请重新登陆");
}
else //页面有效
{

	String Operator  = tGI.Operator ; //保存登陆管理员账号
    String ManageCom = tGI.ManageCom  ; //保存登陆区站,ManageCom=内存中登陆区站代码

    //获取操作符（insert||first、insert||customer、update）
    String transact = request.getParameter("fmtransact");
    
    //理赔类型
    String tClaimType[] = request.getParameterValues("claimType");
    //出险原因
    String toccurReason = request.getParameter("occurReason");
    //工作流操作型别，根据此值检索活动ID，取出服务类执行具体业务逻辑
    String wFlag = request.getParameter("WorkFlowFlag");

    //获取报案页面信息
    tLLAccidentSchema.setAccNo(request.getParameter("AccNo")); //事件号
    tLLAccidentSchema.setAccDate(request.getParameter("AccidentDate")); //意外事故发生日期
    tLLAccidentSchema.setAccType(request.getParameter("occurReason")); //事故类型===出险原因
    tLLAccidentSchema.setAccPlace(request.getParameter("AccidentSite")); //出险地点
    tLLAccidentSchema.setAccDesc(request.getParameter("AccDesc")); //事故描述
    
    tLLRegisterSchema.setRgtNo(request.getParameter("RptNo")); //报案号=赔案号
    tLLRegisterSchema.setRgtantName(request.getParameter("RptorName")); //报案人姓名
    tLLRegisterSchema.setRgtantPhone(request.getParameter("RptorPhone")); //报案人电话
    tLLRegisterSchema.setRgtantMobile(request.getParameter("RptorMoPhone")); //申请人手机号码 
    tLLRegisterSchema.setRgtantAddress(request.getParameter("RptorAddress")); //报案人通讯地址
    tLLRegisterSchema.setPostCode(request.getParameter("AppntZipCode")); //申请人邮编
    tLLRegisterSchema.setRelation(request.getParameter("Relation")); //报案人与事故人关系
    tLLRegisterSchema.setRgtState("11"); //案件类型:普通案件
    
    tLLRegisterSchema.setAccidentSite(tLLAccidentSchema.getAccPlace()); //出险地点
    tLLRegisterSchema.setAccidentDate(tLLAccidentSchema.getAccDate()); //意外事故发生日期
    tLLRegisterSchema.setAccidentReason(tLLAccidentSchema.getAccType()); //出险原因
    
    tLLRegisterSchema.setAssigneeType(request.getParameter("AssigneeType")); //受托人类型
    tLLRegisterSchema.setAssigneeCode(request.getParameter("AssigneeCode")); //受托人代码
    tLLRegisterSchema.setAssigneeName(request.getParameter("AssigneeName")); //受托人姓名
    tLLRegisterSchema.setAssigneeSex(request.getParameter("AssigneeSex")); //受托人性别
    tLLRegisterSchema.setAssigneePhone(request.getParameter("AssigneePhone")); //受托人电话
    tLLRegisterSchema.setAssigneeAddr(request.getParameter("AssigneeAddr")); //受托人地址
    tLLRegisterSchema.setAssigneeZip(request.getParameter("AssigneeZip")); //受托人邮编
    tLLRegisterSchema.setAcceptedDate(request.getParameter("AcceptedDate")); //受理日期
    tLLRegisterSchema.setApplyDate(request.getParameter("ApplyDate")); //客户申请日期
    
    tLLRegisterSchema.setRgtClass("1"); //个单团单 1：个险
    tLLRegisterSchema.setGetMode("1");  //领取方式，默认值为1
    tLLRegisterSchema.setRemark(request.getParameter("Remark")); //备注
    tLLRegisterSchema.setPeoples2("1"); //报案人数
    tLLRegisterSchema.setCasePayType("1"); //案件给付类型 1--一般给付件

    tLLCaseSchema.setCaseNo(tLLRegisterSchema.getRgtNo()); //分案号=报案号=赔案号
    tLLCaseSchema.setCustomerNo(request.getParameter("customerNo")); //出险人编码
    tLLCaseSchema.setCustomerName(request.getParameter("customerName")); //出险人姓名
    tLLCaseSchema.setCustomerAge(request.getParameter("customerAge")); //出险人年龄
    tLLCaseSchema.setCustomerSex(request.getParameter("customerSex")); //出险人性别
    
    //tLLCaseSchema.setVIPFlag(request.getParameter("IsVip")); //vip标志
    tLLCaseSchema.setAccDate(request.getParameter("OtherAccidentDate")); //其他出险日期
    tLLCaseSchema.setMedAccDate(request.getParameter("MedicalAccidentDate")); //医疗出险日期
    tLLCaseSchema.setAccidentDetail(request.getParameter("accidentDetail")); //出险细节
    
    tLLCaseSchema.setCureDesc(request.getParameter("cureDesc")); //治疗情况
    tLLCaseSchema.setAccResult1(request.getParameter("AccResult1")); //ICD10主代码
    tLLCaseSchema.setAccResult2(request.getParameter("AccResult2")); //ICD10子代码
    tLLCaseSchema.setAccdentDesc(request.getParameter("AccDesc")); //事故描述
    tLLCaseSchema.setHospitalCode(request.getParameter("hospital")); //治疗医院编码
    tLLCaseSchema.setHospitalName(request.getParameter("TreatAreaName")); //治疗医院名称
    tLLCaseSchema.setRemark(tLLRegisterSchema.getRemark()); //备注
    tLLCaseSchema.setSeqNo("1"); //序号

	for (int i = 0; i < tClaimType.length; i++)
	{
	    tClaimType[i] = toccurReason + tClaimType[i];
	    tLLAppClaimReasonSchema = new LLAppClaimReasonSchema();
	    
	    tLLAppClaimReasonSchema.setCaseNo(tLLRegisterSchema.getRgtNo()); //报案号=赔案号,在立案保存后会被替换成立案号
	 	tLLAppClaimReasonSchema.setRgtNo(tLLRegisterSchema.getRgtNo()); //报案号=赔案号,不替换
	 	tLLAppClaimReasonSchema.setCustomerNo(tLLCaseSchema.getCustomerNo()); //出险人编码
	 	tLLAppClaimReasonSchema.setReasonCode(tClaimType[i]); //理赔类型
	 	
	   	tLLAppClaimReasonSet.add(tLLAppClaimReasonSchema);
	}
    
    String ActivityID = new ExeSQL().getOneValue("select activityid from lwactivity where functionid='10030003'");

    //String使用TransferData打包后提交
    TransferData mTransferData = new TransferData();
    //工作流相关
    mTransferData.setNameAndValue("RgtNo", tLLRegisterSchema.getRgtNo());
    mTransferData.setNameAndValue("RptorState", "20");
    mTransferData.setNameAndValue("CustomerNo", tLLCaseSchema.getCustomerNo());
    mTransferData.setNameAndValue("CustomerName", tLLCaseSchema.getCustomerName());
    mTransferData.setNameAndValue("CustomerSex", tLLCaseSchema.getCustomerSex());
    mTransferData.setNameAndValue("AccDate", tLLAccidentSchema.getAccDate());
    mTransferData.setNameAndValue("OtherOperator", "");
    mTransferData.setNameAndValue("OtherFlag", "0");
    mTransferData.setNameAndValue("ScanFlag", "0");//无扫
    mTransferData.setNameAndValue("MngCom", ManageCom);
    mTransferData.setNameAndValue("RptNo", tLLRegisterSchema.getRgtNo());

    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
    mTransferData.setNameAndValue("ActivityID",ActivityID);
    mTransferData.setNameAndValue("AcceptedDate",tLLRegisterSchema.getAcceptedDate());
    mTransferData.setNameAndValue("BusiType", "1003");
  
    
    //准备传输数据 VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(mTransferData);
    tVData.add(tLLAccidentSchema);
    tVData.add(tLLRegisterSchema);
    tVData.add(tLLCaseSchema);
    tVData.add(tLLAppClaimReasonSet);

        try
        {
            //数据提交
            loggerDebug("LLClaimRegisterSave","---LLClaimRegisterBLF submitData and transact="+transact);

            if(transact.equals("insert||customer")||transact.equals("insert||first")){
            	if(!tBusinessDelegate.submitData(tVData,"create",busiName)){
            		if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
    		        { 
    						   Content = "保存失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
    						   FlagStr = "Fail";
    				}
    				else
    				{
    						   Content = "保存失败";
    						   FlagStr = "Fail";				
    				}
            	}else{
            		 Content = " 数据提交成功！";
                     FlagStr = "Succ";
     		    	//从结果集中取出前台需要数据
                     tVData.clear();
                     tVData = tBusinessDelegate.getResult();               
                     loggerDebug("LLClaimRegisterSave","tVData="+tVData);
                     //**********************************************************************************Beg
                     //取得处理后的节点参数,目前方法采用根据传入参数查询新增的工作流节点：
                     //**********************************************************************************
                     TransferData tTransferData = new TransferData();
                     tTransferData = (TransferData) tVData.getObjectByObjectName("TransferData", 0);
                     String tRptNo = (String) tTransferData.getValueByName("RptNo");
                     loggerDebug("LLClaimRegisterSave","tRptNo="+tRptNo);
                     String tRgtNo = (String) tTransferData.getValueByName("RgtNo");
                     loggerDebug("LLClaimRegisterSave","tRgtNo="+tRgtNo);
                     
                     String sql = "select MissionID,SubMissionID,MissionProp1,activityid from LWMission where"
                                + " activityid in(select activityid from lwactivity where functionid='10030003') "
                                + " and  missionprop1 = '" +tRgtNo+"'";
                     loggerDebug("LLClaimRegisterSave","Start query mission:" + sql);
                  
                     TransferData sqlTransferData = new TransferData();
   				  VData sqlVData = new VData();
   			    sqlTransferData.setNameAndValue("SQL",sql);
   			    sqlVData.add(sqlTransferData);
   			  	BusinessDelegate tsqlBusinessDelegate=BusinessDelegate.getBusinessDelegate();
   			  	  if(!tsqlBusinessDelegate.submitData(sqlVData,"execSQL","ExeSQLUI"))
   			  	  {    
   			  	       if(tsqlBusinessDelegate.getCErrors()!=null&&tsqlBusinessDelegate.getCErrors().getErrorCount()>0)
   			  	       { 
   			  				loggerDebug("LLClaimRegisterSave","查询失败，原因是:" + tsqlBusinessDelegate.getCErrors().getFirstError());
   			  		   }
   			  		   else
   			  		   {
   			  			 	loggerDebug("LLClaimRegisterSave","查询失败");				
   			  		   }
   			  	  }
   			  	  else
   			  	  {			  	  
   			  			SSRS tSSRS=(SSRS)tsqlBusinessDelegate.getResult().get(0);
   		                if (tSSRS == null || tSSRS.getMaxRow()<1)
   		                {
   		                    Content = "查询工作流节点失败!" ;
   		                    FlagStr = "Fail";
   		                }
   		                else
   		                {
   		                    loggerDebug("LLClaimRegisterSave","tSSRS.getMaxRow()=" + tSSRS.getMaxRow());
   		                    String MissionID = tSSRS.GetText(1,1);
   		                    String SubMissionID = tSSRS.GetText(1,2);
   		                    String RptNo = tSSRS.GetText(1,3);
   		                    loggerDebug("LLClaimRegisterSave","立案号是"+RptNo);
   		                    loggerDebug("LLClaimRegisterSave","---返回新建工作流节点="+MissionID);
   %>
   <script language="javascript">
       parent.fraInterface.document.all("MissionID").value="<%=MissionID%>";
       parent.fraInterface.document.all("SubMissionID").value="<%=SubMissionID%>";
       parent.fraInterface.document.all("RptNo").value="<%=RptNo%>";
       parent.fraInterface.document.all("ActivityID").value="<%=ActivityID%>";
   </script>
   <%
   	                    Content = "数据提交成功";
   	                    FlagStr = "Succ";
   	                }
   			  	  }
     			  	  
            	}
            }else if(transact.equals("UPDATE")){
            	 String busiName1="LLClaimRegisterBLF";
     			BusinessDelegate tBusinessDelegate1=BusinessDelegate.getBusinessDelegate();
     			if(!tBusinessDelegate1.submitData(tVData,transact,busiName1))
     		   {    
     		        if(tBusinessDelegate1.getCErrors()!=null&&tBusinessDelegate1.getCErrors().getErrorCount()>0)
     		        { 
     						   Content = "保存失败，原因是:" + tBusinessDelegate1.getCErrors().getFirstError();
     						   FlagStr = "Fail";
     				}
     				else
     				{
     						   Content = "保存失败";
     						   FlagStr = "Fail";				
     				}
     		   }else{
     			  Content = " 数据提交成功！";
                  FlagStr = "Succ";
     		   }
            }else if(!"insert||first".equals(transact))
            {
                Content = "操作符错误！" ;
                FlagStr = "Fail";
                return;
            }
		    
        }
        catch(Exception ex)
        {
            Content = "数据提交LLClaimRegisterUI失败，原因是:" + ex.toString();
            FlagStr = "Fail";
        }
}   
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
