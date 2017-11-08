<%
/***************************************************************
 * <p>ProName��BalanceAppSave.jsp</p>
 * <p>Title����������</p>
 * <p>Description����������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���ƴ�
 * @version  : 8.0
 * @date     : 2014-06-17
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.VData"%>
<%@page import="com.sinosoft.lis.schema.LPBalanceAppSchema"%>
<%@page import="com.sinosoft.lis.vschema.LPBalanceAppSet"%>
<%@page import="com.sinosoft.lis.schema.LPBalanceRelaSchema"%>
<%@page import="com.sinosoft.lis.vschema.LPBalanceRelaSet"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	String tOperate = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "ҳ��ʧЧ,�����µ�½";
	} else {
		try {
			//��ȡ�������͵�ǰ̨����
			tOperate = request.getParameter("Operate");
			VData tVData = new VData();
			tVData.add(tGI);
			LPBalanceAppSchema mLPBalanceAppSchema = new LPBalanceAppSchema();
			LPBalanceRelaSet mLPBalanceRelaSet = new LPBalanceRelaSet();
			if(tOperate.equals("INSERT")){
			
				String tMoney = request.getParameter("SelectPosMoney");
				String tGrpContNo = request.getParameter("GrpContNoQ");
				String tGrid1 [] = request.getParameterValues("PosInfoGrid1");
				String tGrid2 [] = request.getParameterValues("PosInfoGrid2");
				String tGrid5 [] = request.getParameterValues("PosInfoGrid5");
				String tChk[]=request.getParameterValues("InpPosInfoGridChk");
				 
				mLPBalanceAppSchema.setGrpContNo(tGrpContNo);
				mLPBalanceAppSchema.setGetMoney(tMoney);
				mLPBalanceAppSchema.setState("0");
				 
				for(int index=0;index<tChk.length;index++){
					if(tChk[index].equals("1")){
						LPBalanceRelaSchema mLPBalanceRelaSchema = new LPBalanceRelaSchema();
						
						mLPBalanceRelaSchema.setGrpContNo(tGrid1 [index]);
						mLPBalanceRelaSchema.setEdorAppNo(tGrid2 [index]);
						mLPBalanceRelaSchema.setBalanceType("");
						mLPBalanceRelaSchema.setBalanceRelaSum(tGrid5 [index]);
						mLPBalanceRelaSchema.setBalanceRelaState("0");
						mLPBalanceRelaSet.add(mLPBalanceRelaSchema);
					}
			} 
		}
		tVData.add(mLPBalanceAppSchema);
		tVData.add(mLPBalanceRelaSet);
		BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
		if (!tBusinessDelegate.submitData(tVData, tOperate, "BalanceAppUI")) {
			tContent = tBusinessDelegate.getCErrors().getFirstError();
			tFlagStr = "Fail";
		} else {
			tFlagStr = "Succ";
			tContent = "����ɹ���";
		}
	} catch (Exception ex) {
		ex.printStackTrace();
		tContent = tFlagStr + "�����쳣������ϵϵͳ��ά��Ա��";
		tFlagStr = "Fail";
	}
}
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>");
</script>
</html>
