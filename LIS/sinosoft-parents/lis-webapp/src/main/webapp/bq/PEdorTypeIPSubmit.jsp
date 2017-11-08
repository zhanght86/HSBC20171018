<%
//程序名称：PEdorTypeIPSubmit.jsp
//程序功能：
//创建日期：2007-04-16
//创建人  ：tp
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.service.*" %>
<%
 //接收信息，并作校验处理。
  //输入参数
  //个人批改信息
  
  
  CErrors tError = null;
  String FlagStr = "";
  String Content = "";
  String transact = "";
  
  //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
  	GlobalInput tGlobalInput = new GlobalInput();
	tGlobalInput = (GlobalInput)session.getValue("GI");

	transact = request.getParameter("fmtransact");
	String edorAcceptNo = request.getParameter("EdorAcceptNo");
	String edorNo = request.getParameter("EdorNo");
	String edorType = request.getParameter("EdorType");
	String contNo = request.getParameter("ContNo");
    LPPerInvestPlanSet tLPPerInvestPlanSet = new LPPerInvestPlanSet();  
    boolean ret = true;
	if(transact.equals("EDORITEM|INPUT")){
  		int lineCount = 0;
		String tInsuAccNo[] = request.getParameterValues("InsuAccGrid5");
		String tPayPlanCode[] = request.getParameterValues("InsuAccGrid3");
		String tInvestMoney[] = request.getParameterValues("InsuAccGrid13");
		String RiskCode[] = request.getParameterValues("InsuAccGrid1");
		String tChk[] = request.getParameterValues("InpInsuAccGridChk");
		String tPolNo[] = request.getParameterValues("InsuAccGrid9");
		lineCount = tChk.length; //行数
		for(int i=0;i<lineCount;i++){
		if (tChk[i].equals("1")) {
           LPPerInvestPlanSchema tLPPerInvestPlanSchema = new LPPerInvestPlanSchema();
           tLPPerInvestPlanSchema.setEdorNo(edorNo);
           tLPPerInvestPlanSchema.setEdorType(edorType);
           tLPPerInvestPlanSchema.setContNo(contNo);
           tLPPerInvestPlanSchema.setPolNo(tPolNo[i]);
           tLPPerInvestPlanSchema.setInputMode("1");
           tLPPerInvestPlanSchema.setGrpContNo("00000000000000000000");
           tLPPerInvestPlanSchema.setGrpPolNo("00000000000000000000");
           tLPPerInvestPlanSchema.setInsuredNo(tInsuAccNo[i]);
           tLPPerInvestPlanSchema.setRiskCode(RiskCode[i]);
	       tLPPerInvestPlanSchema.setInsuAccNo(tInsuAccNo[i]);
           tLPPerInvestPlanSchema.setPayPlanCode( tPayPlanCode[i]);
           if(!tInvestMoney[i].equals("")) {
                  if(!PubFun.isNumeric(tInvestMoney[i]))
                  {
                	  ret = false;
                    FlagStr="Fail";
                    Content = " 操作失败，原因是:追加金额的录入格式有误";
                  }else{
                    tLPPerInvestPlanSchema.setInvestMoney(tInvestMoney[i]);
                  }
            }else{
                  tLPPerInvestPlanSchema.setInvestMoney(0);
            }

			tLPPerInvestPlanSet.add(tLPPerInvestPlanSchema);
		}
	}
}
	
if(transact.equals("EDORITEM|DELETE")){
  		int lineCount = 0;
		String tInsuAccNo[] = request.getParameterValues("PolGridb5");
		String tPayPlanCode[] = request.getParameterValues("PolGridb3");
		String tPolNo[] = request.getParameterValues("PolGridb9");
		String tChk[] = request.getParameterValues("InpPolGridbChk");
		lineCount = tChk.length; //行数
		for(int i=0;i<lineCount;i++){
		if (tChk[i].equals("1")) {
           LPPerInvestPlanSchema tLPPerInvestPlanSchema = new LPPerInvestPlanSchema();
           tLPPerInvestPlanSchema.setPolNo(tPolNo[i]);
	       tLPPerInvestPlanSchema.setInsuAccNo(tInsuAccNo[i]);
           tLPPerInvestPlanSchema.setPayPlanCode(tPayPlanCode[i]);
		   tLPPerInvestPlanSet.add(tLPPerInvestPlanSchema);
		}
	}
}
	if(ret){
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		tLPEdorItemSchema.setEdorAcceptNo(edorAcceptNo);
		tLPEdorItemSchema.setEdorNo(edorNo);
		tLPEdorItemSchema.setContNo(contNo);
		tLPEdorItemSchema.setEdorType(edorType);
		VData tVData = new VData();

		try{		   
			tVData.add(tGlobalInput);
			tVData.add(tLPEdorItemSchema);
			tVData.add(tLPPerInvestPlanSet);
		
		 	String busiName="PEdorIPDetailBL";
		 	BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
  		 	tBusinessDelegate.submitData(tVData,transact,busiName);
  		 	tError = tBusinessDelegate.getCErrors(); 
  		 	if (tError.needDealError()){
  	  			Content = " 保存失败，原因是:" + tError.getFirstError();
  	  			FlagStr = "Fail";
  	  	 	}
		} catch(Exception ex) {
	      	Content = "保存失败，原因是:" + ex.toString();
	      	FlagStr = "Fail";
		}
	}

  	if (!FlagStr.equals("Fail")){
      		Content = " 保存成功! ";
      		FlagStr = "Succ";

  	}
 

%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

