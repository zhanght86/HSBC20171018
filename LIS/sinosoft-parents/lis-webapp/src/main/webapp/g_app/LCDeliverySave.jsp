<%
/***************************************************************
 * <p>ProName：LCDeliverySave.jsp</p>
 * <p>Title：递送登记</p>
 * <p>Description：递送登记</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-05-07
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.utility.VData"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.lis.schema.LCPostalInfoSchema"%>
<%@page import="com.sinosoft.lis.vschema.LCPostalInfoSet"%>//递送登记表

<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	String tOperate = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	LCPostalInfoSet mLCPostalInfoSet = null;
	
	LCPostalInfoSchema mLCPostalInfoSchema = null;
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "页面失效,请重新登陆";
	} else {
		
		try {
			mLCPostalInfoSet = new  LCPostalInfoSet();
			//获取操作类型的前台参数
			tOperate = request.getParameter("Operate");
			
			VData tVData = new VData();
			tVData.add(tGI);
			
			if("INSERT".equals(tOperate)){
				String mRegisterPassFlag = request.getParameter("RegisterPassFlag");
				String mTransferType = request.getParameter("TransferType");
				String mExpressCorpName = request.getParameter("ExpressCorpName");
				String mExpressNo = request.getParameter("ExpressNo");
				String mTransferDate = request.getParameter("TransferDate");
				String mRegister = request.getParameter("Register");
				String mExpressDate = request.getParameter("ExpressDate");
				String mReason = request.getParameter("Reason");
				
				String tGrid2 [] = request.getParameterValues("ContInfoGrid2");
				String tGrid3 [] = request.getParameterValues("ContInfoGrid3");
				String tChk[]=request.getParameterValues("InpContInfoGridChk");
				for(int index=0;index<tChk.length;index++){
					if(tChk[index].equals("1")){
			
						mLCPostalInfoSchema = new LCPostalInfoSchema();
						mLCPostalInfoSchema.setGrpContNo(tGrid2[index]);
						mLCPostalInfoSchema.setGrpPropNo(tGrid3[index]);
						mLCPostalInfoSchema.setRegisterPassFlag(mRegisterPassFlag);
						mLCPostalInfoSchema.setTransferType(mTransferType);
						mLCPostalInfoSchema.setExpressCorpName(mExpressCorpName);
						mLCPostalInfoSchema.setExpressNo(mExpressNo);
						mLCPostalInfoSchema.setTransferDate(mTransferDate);
						mLCPostalInfoSchema.setRegister(mRegister);
						mLCPostalInfoSchema.setExpressDate(mExpressDate);
						if(mRegisterPassFlag.equals("1")){
							mLCPostalInfoSchema.setReason(mReason);		
						}
						
						mLCPostalInfoSet.add(mLCPostalInfoSchema);
					}										
			} 
			
			tVData.add(mLCPostalInfoSet);
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LCPostalInfoUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tFlagStr = "Succ";
				tContent = "处理成功！";
			}
		} 
		}catch (Exception ex) {
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
