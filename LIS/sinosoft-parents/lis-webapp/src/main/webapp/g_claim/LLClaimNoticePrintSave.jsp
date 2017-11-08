<%
/***************************************************************
 * <p>ProName：LLClaimNoticePrintSave.jsp</p>
 * <p>Title：通知书打印</p>
 * <p>Description：通知书打印</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 高冬华
 * @version  : 8.0
 * @date     : 2012-01-01
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>

<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	String tFileName="";
	String tFilePath1="";
	GlobalInput tGI = new GlobalInput();
	TransferData tTransferData=new TransferData();
	tGI = (GlobalInput)session.getValue("GI");
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "页面失效,请重新登陆";
	} else {
		
		try {
			System.out.println("开始");
			String tOperate = request.getParameter("Operate");
			String tGrpRgtNo =request.getParameter("GrpRgtNo");	
			System.out.println(tOperate);
			
			System.out.println("========="+tGrpRgtNo);
			LLRegisterSet tLLRegisterSet = new LLRegisterSet();
			
			String tRaidoNo[] = request.getParameterValues("InpCaseGridChk");  //得到序号列的所有值
			String tGrpRgtNos [] = request.getParameterValues("CaseGrid1"); //得到第1列的所有值 批次号
			String tRgtNos [] = request.getParameterValues("CaseGrid2"); //得到第2列的所有值 赔案号
			String tCustomerNos [] = request.getParameterValues("CaseGrid3"); //得到第2列的所有值 赔案号

			tTransferData.setNameAndValue("Type","2");//1外包打印、2普通立案打印和特殊立案打印
			if("BATCHENDPRINT".equals(tOperate)){
				
				tTransferData.setNameAndValue("GrpRgtNo",tGrpRgtNo);
			}else{
				
				for(int index=0;index<tRaidoNo.length;index++){
					if(tRaidoNo[index].equals("1")) {
						LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema();
						tLLRegisterSchema.setGrpRgtNo(tGrpRgtNos[index]);
						tLLRegisterSchema.setRgtNo(tRgtNos[index]);
						tLLRegisterSchema.setCustomerNo(tCustomerNos[index]);
						tLLRegisterSet.add(tLLRegisterSchema);
					}
				}
			}								

			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLLRegisterSet);
			tVData.add(tTransferData);

			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LLClaimNoticePrintUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tContent = "处理成功！";
				tFlagStr = "Succ";
				TransferData trd = (TransferData)tBusinessDelegate.getResult().getObjectByObjectName("TransferData", 0);
				
				tFilePath1 = (String) trd.getValueByName("FilePath");
				tFileName = (String) trd.getValueByName("FileName");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			tContent = tFlagStr + "处理异常，请联系系统运维人员！";
			tFlagStr = "Fail";
		}
	}
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>","<%=tFilePath1%>","<%=tFileName%>");
</script>
</html>
