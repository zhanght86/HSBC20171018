<%
//**************************************************************************************************
//Name：LLClaimConfirmSave.jsp
//Function：审批管理提交
//Author：zhoulei
//Date:
//**************************************************************************************************
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
 
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.LWMissionDB"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>
<%@page import="com.sinosoft.workflow.claimgrp.*"%>
<%@page import="com.sinosoft.service.*" %>

<%

//输入参数
LLClaimUWMainSchema tLLClaimUWMainSchema = new LLClaimUWMainSchema(); //案件核赔表

//输出参数
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput(); 
tGI=(GlobalInput)session.getValue("GI");  
String DecisionSP=request.getParameter("DecisionSP");

//页面有效
if(tGI == null)
{
    FlagStr = "Fail";
    Content = "页面失效,请重新登陆";
    loggerDebug("LLClaimConfirmSave","页面失效,请重新登陆");    
}
else
{
    //获取操作insert||update
    String transact = request.getParameter("fmtransact");
    //工作流操作型别，根据此值检索活动ID，取出服务类执行具体业务逻辑
    String wFlag = request.getParameter("WorkFlowFlag");
    String mRptNo = request.getParameter("RptNo");

    //获取报案页面信息
    tLLClaimUWMainSchema.setClmNo(request.getParameter("RptNo")); //赔案号
    tLLClaimUWMainSchema.setExamConclusion(request.getParameter("DecisionSP")); //审批结论
    tLLClaimUWMainSchema.setExamIdea(request.getParameter("RemarkSP")); //审批意见
    tLLClaimUWMainSchema.setExamNoPassReason(request.getParameter("ExamNoPassReason")); //不通过原因
    tLLClaimUWMainSchema.setExamNoPassDesc(request.getParameter("ExamNoPassDesc")); //不通过依据
    
    //String使用TransferData打包后提交
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("RptNo", request.getParameter("RptNo"));
    mTransferData.setNameAndValue("RptorState", "50");
    mTransferData.setNameAndValue("CustomerNo", request.getParameter("customerNo"));
    mTransferData.setNameAndValue("CustomerName", request.getParameter("customerName"));
    mTransferData.setNameAndValue("CustomerSex", request.getParameter("customerSex"));
    mTransferData.setNameAndValue("AccDate", request.getParameter("AccidentDate2"));
    mTransferData.setNameAndValue("RgtClass", "2");  //申请类型
    mTransferData.setNameAndValue("RgtObj", "1");  //号码类型
    mTransferData.setNameAndValue("RgtObjNo", request.getParameter("RptNo"));  //其他号码
    mTransferData.setNameAndValue("RgtState", request.getParameter("RgtState")); //案件类型
    
    mTransferData.setNameAndValue("MngCom", request.getParameter("ManageComAll"));  //机构
    loggerDebug("LLClaimConfirmSave","ManageComAll +++++++++++++++ "+request.getParameter("ManageComAll"));   
    mTransferData.setNameAndValue("Popedom",request.getParameter("AuditPopedom")); //审核权限
    mTransferData.setNameAndValue("PrepayFlag", request.getParameter("BudgetFlag")); //预付标志
    mTransferData.setNameAndValue("Auditer", request.getParameter("Auditer")); //审核人
    
    //转移条件
    mTransferData.setNameAndValue("Reject", request.getParameter("DecisionSP"));  //审批是否通过
    
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));


    //准备传输数据 VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(mTransferData);  
    tVData.add(tLLClaimUWMainSchema); 
    boolean IFDSFlag = false;
  //09-04-09  增加双岗审批的流程，如果审批人是总公司团险签批人则需要走双岗审批的流程
    String tSql = "select code from ldcode where codetype='lldsflag'";
    ExeSQL tExeSQL = new ExeSQL();
	String tResult = tExeSQL.getOneValue(tSql);
	//1-双岗审批有效
	//判断当前操作员是否是总公司签批人   如果是总公司签批人则判断目前是第几岗 区分是否该走第二岗的条件是
	//DSClaimFlag不等于1
	if("1".equals(tResult)){
		//查询当前操作员是否为总公司签批人  如果是DSFlag=1
		String tDSFlagSql = "select distinct dsflag from llgrpclaimpopedom "
				+ "where jobcategory = "
				+ "(select jobcategory from llgrpclaimuser where usercode='"+tGI.Operator+"')";
		String tDSFlag = tExeSQL.getOneValue(tDSFlagSql);
		if("1".equals(tDSFlag)){
			String tDSClaimFlagSql = "select DSClaimFlag from LLClaimUWMain where clmno='"
					+ mRptNo + "'";
			String tDSClaimFlag = tExeSQL.getOneValue(tDSClaimFlagSql);
			//tDSClaimFlag不为1  走双岗审批的流程
			if(!"1".equals(tDSClaimFlag)){
				IFDSFlag = true;
			}
		}
	}
	//如果一审结论为不通过,则直接回退到审核人,不用走双审,只有审批结论为0-通过时才走双审
	if(IFDSFlag==true&&tLLClaimUWMainSchema.getExamConclusion().trim().equals("0")){
		//双岗审批
		//QueryDefaultOperator tQueryDefaultOperator = new QueryDefaultOperator();
		String busiName="grpQueryDefaultOperator";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		
		try{
//			if(!tQueryDefaultOperator.submitData(tVData,"")){
//				Content = " 审批确认失败，原因是: " + tQueryDefaultOperator.mErrors.getError(0).errorMessage;
//	            FlagStr = "Fail";
//			}
			if(!tBusinessDelegate.submitData(tVData,"",busiName))
			{    
			    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
			    { 
					Content = "审批确认失败，原因是:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
					FlagStr = "Fail";
				}
				else
				{
					Content = "审批确认失败";
					FlagStr = "Fail";				
				}
			}

			else{
				Content = " 审批确认成功！";
	            FlagStr = "Succ";
	            if (DecisionSP.equals("0"))
	            {
	            	//tongmeng 2009-01-06 modify
	            	//注销掉再保处理
	            	/*
	                 //加入再保险处理     sujd   2007-12-17
	                 ReLifeNeed tReLifeNeed=new ReLifeNeed();
	                 VData tmpvdata=new VData();
	                 
	                 TransferData tmpTransferData = new TransferData();
	                 tmpTransferData.setNameAndValue("ManageCom",tGI.ManageCom);
	                 tmpTransferData.setNameAndValue("Operator",tGI.Operator);
	                 tmpTransferData.setNameAndValue("OtherNo",request.getParameter("RptNo"));
	                 
	                 tmpvdata.add(tmpTransferData);
	                 
	                 if (!tReLifeNeed.submitdata(tmpvdata,"2"))
	                 {
	                      Content=Content+";再保险处理失败,请联系信息技术部人员(案件需要结案)!";
	                      FlagStr = "Fail";
	                 }
	                 else
	                 {
	                     Content=Content+";再保险处理成功!";           
	                 }
	                 loggerDebug("LLClaimConfirmSave","==================再保批处理结束=================");
	                 */
	            }                        
	       
	            
	        }
	             /*解锁，防止中间点2次结案按钮*/             
			MMap tempMMap=new MMap();
			ExeSQL ttExeSQL=new ExeSQL();
			SSRS tSSRS=new SSRS();
			String ttsql="select getdutykind from llclaim where clmno='"+mRptNo+"' and getdutykind is not null ";
			tSSRS=ttExeSQL.execSQL(ttsql);
			if(tSSRS.MaxRow>0 && tSSRS.GetText(1,1).equals("1")) {
				tempMMap.put("update llclaim set getdutykind='' where clmno='"+mRptNo+"' ","UPDATE");
				PubSubmit ttempSubmit = new PubSubmit();
		        VData temVData = new VData();
		        temVData.add(tempMMap);
		        if (!ttempSubmit.submitData(temVData, null)) {
		        	CError.buildErr(this, "理赔解锁失败！");                
		        }
			}          
	        loggerDebug("LLClaimConfirmSave","-------------------end workflow---------------------");
		}catch(Exception ex){
			Content = "审批确认失败，原因是:" + ex.toString();
	        FlagStr = "Fail";
		}
	}else{
	    try
	    {  
	        //提交数据
	        loggerDebug("LLClaimConfirmSave","-------------------start workflow---------------------");
	        wFlag = "0000009055";
//	        ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI();
//	        if(!tClaimWorkFlowUI.submitData(tVData,wFlag))
//	        {
//	            Content = " 审批确认失败，原因是: " + tClaimWorkFlowUI.mErrors.getError(0).errorMessage;
//	            FlagStr = "Fail";
//	        }
			String busiName1="grpClaimWorkFlowUI";
			BusinessDelegate tBusinessDelegate1=BusinessDelegate.getBusinessDelegate();
			if(!tBusinessDelegate1.submitData(tVData,wFlag,busiName1))
			{    
			    if(tBusinessDelegate1.getCErrors()!=null&&tBusinessDelegate1.getCErrors().getErrorCount()>0)
			    { 
					Content = "审批确认失败，原因是:" + tBusinessDelegate1.getCErrors().getError(0).errorMessage;
					FlagStr = "Fail";
				}
				else
				{
					Content = "审批确认失败";
					FlagStr = "Fail";				
				}
			}

	        else
	        {
	            Content = " 审批确认成功！";
	            FlagStr = "Succ";
	            if (DecisionSP.equals("0"))
	            {
	            	//tongmeng 2009-01-06 modify
	            	//注销掉再保处理
	            	/*
	                 //加入再保险处理     sujd   2007-12-17
	                 ReLifeNeed tReLifeNeed=new ReLifeNeed();
	                 VData tmpvdata=new VData();
	                 
	                 TransferData tmpTransferData = new TransferData();
	                 tmpTransferData.setNameAndValue("ManageCom",tGI.ManageCom);
	                 tmpTransferData.setNameAndValue("Operator",tGI.Operator);
	                 tmpTransferData.setNameAndValue("OtherNo",request.getParameter("RptNo"));
	                 
	                 tmpvdata.add(tmpTransferData);
	                 
	                 if (!tReLifeNeed.submitdata(tmpvdata,"2"))
	                 {
	                      Content=Content+";再保险处理失败,请联系信息技术部人员(案件需要结案)!";
	                      FlagStr = "Fail";
	                 }
	                 else
	                 {
	                     Content=Content+";再保险处理成功!";           
	                 }
	                 loggerDebug("LLClaimConfirmSave","==================再保批处理结束=================");
	                 */
	            }                        
	       
	            
	        }
	             /*解锁，防止中间点2次结案按钮*/             
			         MMap tempMMap=new MMap();
			         ExeSQL ttExeSQL=new ExeSQL();
			         SSRS tSSRS=new SSRS();
			         String ttsql="select getdutykind from llclaim where clmno='"+mRptNo+"' and getdutykind is not null ";
			         tSSRS=ttExeSQL.execSQL(ttsql);
			         if(tSSRS.MaxRow>0 && tSSRS.GetText(1,1).equals("1"))
			         {
			           tempMMap.put("update llclaim set getdutykind='' where clmno='"+mRptNo+"' ","UPDATE");
			           PubSubmit tempSubmit = new PubSubmit();
	               VData temVData = new VData();
	               temVData.add(tempMMap);
	               if (!tempSubmit.submitData(temVData, null)) 
	               {
	                 CError.buildErr(this, "理赔解锁失败！");                
	               }
			         }          
	        
	        loggerDebug("LLClaimConfirmSave","-------------------end workflow---------------------");
	    }
	    catch(Exception ex)
	    {
	        Content = "审批确认失败，原因是:" + ex.toString();
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
