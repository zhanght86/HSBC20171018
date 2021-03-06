<%
/***************************************************************
* <p>ProName：EdorUWGErrSave.jsp</p>
 * <p>Title：核保处理</p>
 * <p>Description：核保处理</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-0-05-06
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%

	String tFlagStr = "Fail";
	String tContent = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "页面失效,请重新登陆";
	} else {
		
		try {
		
			// 再保处理			
			String tOperate = request.getParameter("Operate");	
			String tGrpPropNo = request.getParameter("GrpPropNo");
			String tUWNo = request.getParameter("UWNo");
			String tReinsType = request.getParameter("ReinsType"); // 个人再保结论
			String tUWConclu = request.getParameter("UWConclu"); // 个人核保结论
			String tUWIdea = request.getParameter("UWIdea"); // 个人核保意见	
			String tContno=  request.getParameter("ContNo");
			String tInsuredno = request.getParameter("InsuredNo"); 
			
			// 团单结论
			String tUWConclusion = request.getParameter("UWConclusion");// 团单核保结论
			String tUWOption = request.getParameter("UWOption"); // 团单核保意见
			String tRulecode = request.getParameter("Rulecode");
			String tRulevel = request.getParameter("Rulevel");
			String tRiskcode = request.getParameter("Riskcode");
			String tEdorNo = request.getParameter("EdorNo");
			String tEdorType =  request.getParameter("EdorType");
		
			LPUWMasterSet  tLPUWMasterSet = new LPUWMasterSet();
			LPUWMasterSchema tLPUWMasterSchema = new LPUWMasterSchema();	
			LPGUWTraceSchema  tLPGUWTraceSchema = new LPGUWTraceSchema();
			
			if("INSERT||Person".equals(tOperate)){	
				tLPUWMasterSchema.setEdorNo(tEdorNo);
				tLPUWMasterSchema.setEdorType(tEdorType);
				tLPUWMasterSchema.setGrpContNo(tGrpPropNo);
				tLPUWMasterSchema.setContNo(tContno);
				tLPUWMasterSchema.setInsuredNo(tInsuredno);
				tLPUWMasterSchema.setUWNo(tUWNo);
				tLPUWMasterSchema.setUWConclu(tUWConclu);
				tLPUWMasterSchema.setUWIdea(tUWIdea);
							
			}else if("INSERT||AllPerson".equals(tOperate)){
				String[] tErrNum= request.getParameterValues("InpUWGErrGridChk");
				String tContNo[] = request.getParameterValues("UWGErrGrid2");//保单号
				String tInsuredNo[] = request.getParameterValues("UWGErrGrid1");//客户号
				String tUWNO[] = request.getParameterValues("UWGErrGrid3");//核保序号
				String tEdorTy[] =  request.getParameterValues("UWGErrGrid4");
				
				int chkCount = 0;
				for(int index=0;index<tErrNum.length;index++) {
					if(tErrNum[index].equals("1"))	{
						chkCount = chkCount + 1;
					}
				}	
				if( chkCount <= 0 )	{
					tContent = "请至少选中一条被保险人信息！";
					tFlagStr  = "Fail";
				}else{
					for(int index=0;index<tErrNum.length;index++) {
						if(tErrNum[index].equals("1"))	{
							LPUWMasterSchema aLPUWMasterSchema = new LPUWMasterSchema();
							aLPUWMasterSchema.setEdorNo(tEdorNo);
							aLPUWMasterSchema.setEdorType(tEdorTy[index]);
							aLPUWMasterSchema.setGrpContNo(tGrpPropNo);	
							aLPUWMasterSchema.setContNo(tContNo[index]);
							aLPUWMasterSchema.setUWNo(tUWNO[index]);
							aLPUWMasterSchema.setInsuredNo(tInsuredNo[index]);
							aLPUWMasterSchema.setUWConclu(tUWConclu);
							aLPUWMasterSchema.setUWIdea(tUWIdea);
							tLPUWMasterSet.add(aLPUWMasterSchema);
						}
					}	
				}
			}else if("INSERT||GRP".equals(tOperate)){
				
				tLPGUWTraceSchema.setEdorNo(tEdorNo);
				tLPGUWTraceSchema.setEdorType(tEdorType);
				tLPGUWTraceSchema.setGrpContNo(tGrpPropNo);
				tLPGUWTraceSchema.setUWConclu(tUWConclusion);
				tLPGUWTraceSchema.setUWIdea(tUWOption);
				tLPGUWTraceSchema.setUWNo(tUWNo);
				tLPGUWTraceSchema.setRuleCode(tRulecode);
				tLPGUWTraceSchema.setRuleLevel(tRulevel);
				tLPGUWTraceSchema.setRiskCode(tRiskcode);			
			}
			
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("GrpPropNo",tGrpPropNo);
			tTransferData.setNameAndValue("ReinsType",tReinsType);
			tTransferData.setNameAndValue("EdorNo",tEdorNo);
			tTransferData.setNameAndValue("EdorType",tEdorType);
			tTransferData.setNameAndValue("UWConclu",tUWConclu);
			tTransferData.setNameAndValue("UWIdea",tUWIdea);
			
			VData tVData = new VData();		
			tVData.add(tGI);
			tVData.add(tTransferData);
			tVData.add(tLPUWMasterSchema);
			tVData.add(tLPGUWTraceSchema);
			tVData.add(tLPUWMasterSet);
		
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "EdorUWGErrUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				
				if ("INSERT||Person".equals(tOperate)) {
				
					tContent = "个人核保结论保存成功！";
				} else if("INSERT||AllPerson".equals(tOperate)) {
					
					tContent = "批量保存核保结论成功！";
				} else if("INSERT||GRP".equals(tOperate)) {
					
					tContent = "核保结论保存成功！";
				} else if("INSERT||ALL".equals(tOperate)) {
					
					tContent = "整单核保结论保存成功！";
				}	
				tFlagStr = "Succ";
			}
		} catch (Exception ex) {
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
