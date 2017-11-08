<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：
//程序功能：
//创建日期：2002-07-22
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@page import="java.lang.*"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.finfee.*"%>
<%@page import="com.sinosoft.lis.operfee.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
CErrors tError = null;
String FlagStr = "";
String Content = "";
//处理类
//Modify by lujun 2006-10-18 11:50 改为可以一次处理多条退费记录
//Modify by liuxj 2007-12-29 该为处理一条记录，但是可以修改银行的信息
//NewGrpContFeeWithdrawBL tNewGrpContFeeWithdrawBL ;
try
{
	//session
	GlobalInput tGI = new GlobalInput();
	tGI=(GlobalInput)session.getValue("GI");

	//合同号
	String tGrpContNo[] = request.getParameterValues("FinFeeWithDrawGrid1");
	String tPayNo[] = request.getParameterValues("FinFeeWithDrawGrid7"); //交费号
	
	//add by liuxj 增加修改银行信息的功能，并修改为一次处理一条
	String tBankCode = request.getParameter("BankCode");
	String tBankAccNo = request.getParameter("BankAccNo");
	String tRadio[] = request.getParameterValues("InpFinFeeWithDrawGridSel");  
  int index;                  
  for (index=0; index< tRadio.length;index++)
    {
        if(tRadio[index].equals("1"))
           break;
        if(tRadio[index].equals("0"))
           continue;
       }
	

			//tNewGrpContFeeWithdrawBL = new NewGrpContFeeWithdrawBL();
			
			LCGrpContSchema tLCGrpContSchema =new LCGrpContSchema();
			tLCGrpContSchema.setGrpContNo(tGrpContNo[index]);
			
			String busiName="finfeeNewGrpContFeeWithdrawBL";
			BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			
			
			LCGrpContSet tLCGrpContSet =new LCGrpContSet();
			tLCGrpContSet.add(tLCGrpContSchema);
		
			TransferData tTransferData= new TransferData();
			tTransferData.setNameAndValue("SpecWithDraw","1");
			tTransferData.setNameAndValue("PayNo",tPayNo[index]);
			tTransferData.setNameAndValue("BankCode",tBankCode);
			tTransferData.setNameAndValue("BankAccNo",tBankAccNo);
		
			VData tVData = new VData();
			tVData.addElement(tLCGrpContSet);
			tVData.addElement(tGI);
			tVData.addElement(tTransferData);
		
			if(!tBusinessDelegate.submitData(tVData,"",busiName)){
				tError = tBusinessDelegate.getCErrors();
				Content =" 退费处理失败，原因是:" + tError.getFirstError();
				FlagStr = "Fail";
			}else{
				Content = " 退费处理完成";
				FlagStr = "Succ";
			}

}
catch(Exception ex)
{
	Content = "退费处理失败，原因是:" + ex.toString();
	FlagStr = "Fail";
}

%>
<html>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
