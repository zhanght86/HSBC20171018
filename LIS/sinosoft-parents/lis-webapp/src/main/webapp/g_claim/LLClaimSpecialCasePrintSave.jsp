<%
/***************************************************************
 * <p>ProName：LLClaimSpecialCasePrintSave.jsp</p>
 * <p>Title：特殊立案打印</p>
 * <p>Description：特殊打印</p>
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
				
			LLRegisterSet tLLRegisterSet = new LLRegisterSet();

			String tRaidoNo[] = request.getParameterValues("InpCustomerListGridChk");  //得到序号列的所有值
			String tGrid1 [] = request.getParameterValues("CustomerListGrid1"); //得到第3列的所有值 批次号
			String tGrid2 [] = request.getParameterValues("CustomerListGrid2"); //得到第5列的所有值 赔案号
			String tGrid3 [] = request.getParameterValues("CustomerListGrid4"); //客户号

			tTransferData.setNameAndValue("Type","2");//1外包打印、2普通立案打印和特殊立案打印
			if("BatchPrint".equals(tOperate)){
				
				String tGrpRgtNo=request.getParameter("tGrpRgtNo");
				tTransferData.setNameAndValue("GrpRgtNo",tGrpRgtNo);
			}else{
				for(int index=0;index<tRaidoNo.length;index++){
					if(tRaidoNo[index].equals("1")) {
						LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema();
						tLLRegisterSchema.setGrpRgtNo(tGrid1[index]);
						tLLRegisterSchema.setRgtNo(tGrid2[index]);
						tLLRegisterSchema.setCustomerNo(tGrid3[index]);
						tLLRegisterSet.add(tLLRegisterSchema);
					}
				}
		}
		String tempFilePath = request.getRealPath("");
		String tempImagePath = request.getRealPath("");
		tempFilePath=tempFilePath.replaceAll("\\\\","/");
		tempImagePath=tempImagePath.replaceAll("\\\\","/");
		tempFilePath+="/temp/claim/";
		tempImagePath+="/common/images/image004.png";
		tTransferData.setNameAndValue("tempFilePath",tempFilePath);
		tTransferData.setNameAndValue("tempImagePath",tempImagePath);
		
	
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLLRegisterSet);
			tVData.add(tTransferData);

			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LLClaimOutCasePrintUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tContent = "处理成功！";
				tFlagStr = "Succ";
				if("BatchPrint".equals(tOperate)||"Print".equals(tOperate)){
					
					TransferData trd = (TransferData)tBusinessDelegate.getResult().getObjectByObjectName("TransferData", 0);
					tFilePath1 = (String) trd.getValueByName("FilePath");
					tFileName = (String) trd.getValueByName("FileName");
					
				}
			}
		} catch (Exception ex) {
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