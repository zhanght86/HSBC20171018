<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：LLClaimRegisterInitMatchCalSave.jsp
//程序功能：审核时自动匹配并理算保险责任匹配保存
//创建日期：2009-01-13 11:10:36
//创建人  ：zhangzheng
//更新记录：  更新人    更新日期     更新原因/内容
%>

<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.workflow.tb.*"%>
<%@page import="com.sinosoft.lis.cbcheck.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.service.*" %>


<%
    //准备通用参数
    CErrors tError = null;
    String FlagStr = "FAIL";
    String Content = "";
    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI");
    String tOperate = request.getParameter("hideOperate");    
    if(tG == null) 
    {
        loggerDebug("LLClaimRegisterInitMatchCalSave","登录信息没有获取!!!");
        return;
     } else if (tOperate == null || tOperate == "") {
        loggerDebug("LLClaimRegisterInitMatchCalSave","没有获取操作符!!!");
        return;   
    }
    
    
    
    //准备数据容器信息
    String tAccNo = request.getParameter("AccNo");
    String tClmNo = request.getParameter("RptNo");
    String tAccDate = request.getParameter("AccidentDate");
    String tCusNo = request.getParameter("WhoNo");
    
    String tContType = request.getParameter("RgtClass");
    String tClmState = request.getParameter("clmState");
             
    TransferData tTransferData = new TransferData();    
    tTransferData.setNameAndValue("AccNo",tAccNo);
    tTransferData.setNameAndValue("ClmNo",tClmNo);
    tTransferData.setNameAndValue("AccDate",tAccDate);    
    tTransferData.setNameAndValue("ContType",tContType);   //总单类型,1-个人总投保单,2-集体总单        
    tTransferData.setNameAndValue("ClmState",tClmState);   //赔案状态，20立案，30审核
        
        
    loggerDebug("LLClaimRegisterInitMatchCalSave","LLClaimRegisterMatchCalSave.jsp测试--"+tClmNo);
    
    LLCaseSchema tLLCaseSchema = new LLCaseSchema(); //分立案表
    
    tLLCaseSchema.setCustomerNo(request.getParameter("WhoNo")); //出险人编码 
    tLLCaseSchema.setCustomerAge(request.getParameter("WhoAge")); //出险人年龄
    tLLCaseSchema.setCustomerSex(request.getParameter("WhoSex")); //出险人性别
    tLLCaseSchema.setAccidentDate(request.getParameter("AccidentDate2")); //出险日期   
    tLLCaseSchema.setAccidentDetail(request.getParameter("WhoDesc")); //出险细节
    tLLCaseSchema.setDieFlag(request.getParameter("IsDead")); //死亡标志  
    tLLCaseSchema.setCureDesc(request.getParameter("WhoTreatDesc")); //治疗情况
    
    
    //loggerDebug("LLClaimRegisterInitMatchCalSave","LLMedicalFeeInpSave.jsp测试--"+tOperate);
  
    
    try
    {    
        // 准备传输数据 VData
        VData tVData = new VData();
        
        tVData.add( tG );        
        tVData.add( tTransferData );        
        tVData.add( tLLCaseSchema );


//       LLClaimCalPortalBL tLLClaimCalPortalBL = new LLClaimCalPortalBL();
//       boolean tbl = tLLClaimCalPortalBL.submitData(tVData,tOperate);
//        
//
//       int n = tLLClaimCalPortalBL.mErrors.getErrorCount();
//       
//       for (int i = 0; i < n; i++)
//        {
//            Content = Content + "提示信息: " + tLLClaimCalPortalBL.mErrors.getError(i).errorMessage;
//            FlagStr = "FAIL";
//
//        }
//
//                     
//       if (n == 0 )
//        {
//            Content = " 保存成功! ";
//            FlagStr = "SUCC";
//        }

		String busiName="LLClaimCalPortalBL";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		
		if(!tBusinessDelegate.submitData(tVData,tOperate,busiName))
		    {    
		        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		        { 
		        	int n = tBusinessDelegate.getCErrors().getErrorCount();
			        for (int i = 0; i < n; i++)
			        {
			            //loggerDebug("LLClaimRegisterInitMatchCalSave","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
			            Content = " 提示信息: " + tBusinessDelegate.getCErrors().getError(i).errorMessage;
			        }
		       		FlagStr = "Fail";				   
				}
				else
				{
				   Content = "提示信息";
				   FlagStr = "Fail";				
				} 
		}                     
       else
        {
            Content = " 保存成功! ";
            FlagStr = "SUCC";
        }
    
        //loggerDebug("LLClaimRegisterInitMatchCalSave","LLMedicalFeeInpSave测试--"+FlagStr);
    }
    catch(Exception e)
    {
        e.printStackTrace();
        Content = Content.trim()+".提示：异常终止!";
    }
    
    //loggerDebug("LLClaimRegisterInitMatchCalSave",FlagStr+"try结束--"+Content);
  
%>                      
<html>
<script language="javascript">
parent.fraInterface.afterafterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
