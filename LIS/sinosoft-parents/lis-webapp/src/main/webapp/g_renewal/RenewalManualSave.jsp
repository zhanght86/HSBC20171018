<%@page import="com.sinosoft.lis.vschema.LPRenewalSet"%>
<%@page import="com.sinosoft.lis.schema.LPRenewalSchema"%>
<%
/***************************************************************
 * <p>ProName：RenewalManualSave.jsp</p>
 * <p>Title：续期催收</p>
 * <p>Description：续期催收</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-06-17
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.VData"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.utility.TransferData"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	String tOperate = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	TransferData tTransferData = new TransferData();
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "页面失效,请重新登陆";
	} else {
		try {
			//获取操作类型的前台参数
			tOperate = request.getParameter("Operate");
			VData tVData = new VData();
			tVData.add(tGI);
			/** 单条核销
			String tPayCount = request.getParameter("PayCount");
			String tGrpContNo =  request.getParameter("GrpContNo");
			**/
			LPRenewalSet tLPRenewalSet = new LPRenewalSet();
			String tCheckChk[] = request.getParameterValues("InpContInfoGridSel");//获取待抽检信息
			System.out.println("***************************"+tCheckChk.length);
			if (tCheckChk!=null && tCheckChk.length>0) {
				
				String tGrpContNo[] = request.getParameterValues("ContInfoGrid3");//保单号
				String tPayCount[] = request.getParameterValues("ContInfoGrid10");//续期期数
				String tMoney[] = request.getParameterValues("ContInfoGrid14");//抽检金额
				
				for (int i=0; i<tCheckChk.length; i++) {
					
					if ("1".equals(tCheckChk[i])) {
					
						LPRenewalSchema tLPRenewalSchema = new LPRenewalSchema();
						tLPRenewalSchema.setGrpContNo(tGrpContNo[i]);
						tLPRenewalSchema.setPayCount(tPayCount[i]);
						tLPRenewalSchema.setGrpContNo(tGrpContNo[i]);
						
						tLPRenewalSet.add(tLPRenewalSchema);
					}
				}
			}
			String tRenewalType =  request.getParameter("RenewalType");
			
			tTransferData.setNameAndValue("RenewalType",tRenewalType);
			tVData.add(tLPRenewalSet);
			tVData.add(tTransferData);
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "RenewalManualUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tFlagStr = "Succ";
				tContent = "处理成功！";
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
	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>");
</script>
</html>
