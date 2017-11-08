<%
/***************************************************************
 * <p>ProName：LLClaimDataSave.jsp</p>
 * <p>Title：索赔金额</p>
 * <p>Description：索赔金额</p>
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
	
	String tFilePath = "";
	String tFileName = "";	
	
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "页面失效,请重新登陆";
	} else {
		
			try {
				
			String tOperate = request.getParameter("Operate");
			String tRptNo = request.getParameter("RptNo");
			String tCustomerNo = request.getParameter("CustomerNo");
			TransferData tTransferData = new TransferData();
	 		tTransferData.setNameAndValue("RptNo", tRptNo);			
	 		tTransferData.setNameAndValue("CustomerNo", tCustomerNo);			
	
			//准备数据容器信息
			LLReportAffixSet tLLReportAffixSet = new LLReportAffixSet(); //报案附件表
	
			//接收MulLine表格中数据集合 
			String tGridNo[] = request.getParameterValues("DocumentListGridNO");  //得到序号列的所有值
			String tAffixCode[] = request.getParameterValues("DocumentListGrid1"); //得到第1列的所有值
			String tAffixName[] = request.getParameterValues("DocumentListGrid2"); //得到第2列的所有值
			String tReasonCode[] = request.getParameterValues("DocumentListGrid3"); //得到第3列的所有值
			String tReasonName[] = request.getParameterValues("DocumentListGrid4"); //得到第4列的所有值
			String tProperty[] = request.getParameterValues("DocumentListGrid6"); //得到第6列的所有值
			String tAffixNo[] = request.getParameterValues("DocumentListGrid8"); //得到第8列的所有值
	
			String tSel[] = request.getParameterValues("InpDocumentListGridChk"); //参数格式=” Inp+MulLine对象名+Chk”
			  
		  for(int index=0;index<tSel.length;index++) {
			  
				LLReportAffixSchema tLLReportAffixSchema = new LLReportAffixSchema();
				if(tSel[index].equals("1")) {
				
					tLLReportAffixSchema.setRptNo(tRptNo);//赔案号
					tLLReportAffixSchema.setRptNo(tRptNo);//赔案号
					tLLReportAffixSchema.setCustomerNo(tCustomerNo);//客户号   
					tLLReportAffixSchema.setAffixCode(tAffixCode[index]);//单证代码
					tLLReportAffixSchema.setAffixName(tAffixName[index]);//单证名称
					tLLReportAffixSchema.setReasonCode(tReasonCode[index]);//单证类别对应出险类型
					tLLReportAffixSchema.setReasonName(tReasonName[index]);//单证类别名称
					tLLReportAffixSchema.setProperty(tProperty[index]);//原件复件标识(0-原件；1-复件)		  
					tLLReportAffixSchema.setAffixNo(tAffixNo[index]);//单证序号
					tLLReportAffixSchema.setReadyCount(1);
					tLLReportAffixSet.add(tLLReportAffixSchema);				
				}
		  }
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLLReportAffixSet);
			tVData.add(tTransferData);
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LLClaimDataUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tContent = "处理成功！";
				tFlagStr = "Succ";
				
				TransferData trd = (TransferData)tBusinessDelegate.getResult().getObjectByObjectName("TransferData", 0);
				
				tFilePath = (String) trd.getValueByName("FilePath");
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
	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>","<%=tFilePath%>","<%=tFileName%>");
</script>
</html>

