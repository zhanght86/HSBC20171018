<%
/***************************************************************
 * <p>ProName��LCDeliverySave.jsp</p>
 * <p>Title�����͵Ǽ�</p>
 * <p>Description�����͵Ǽ�</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���ƴ�
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
<%@page import="com.sinosoft.lis.vschema.LCPostalInfoSet"%>//���͵ǼǱ�

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
		tContent = "ҳ��ʧЧ,�����µ�½";
	} else {
		
		try {
			mLCPostalInfoSet = new  LCPostalInfoSet();
			//��ȡ�������͵�ǰ̨����
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
				tContent = "����ɹ���";
			}
		} 
		}catch (Exception ex) {
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
