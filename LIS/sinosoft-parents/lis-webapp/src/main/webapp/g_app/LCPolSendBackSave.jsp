<%
/***************************************************************
 * <p>ProName��LCPolSendBackSave.jsp</p>
 * <p>Title����ִ����</p>
 * <p>Description����ִ����</p>
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
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.utility.TransferData"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.schema.LCPostalInfoSchema"%>
<%@page import="com.sinosoft.lis.vschema.LCPostalInfoSet"%>//���͵ǼǱ�
<%@page import="com.sinosoft.lis.schema.LCGrpContSchema"%>
<%@page import="com.sinosoft.lis.vschema.LCGrpContSet"%>//���屣����

<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	String tOperate = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	LCPostalInfoSet mLCPostalInfoSet = null;
	LCGrpContSet mLCGrpContSet = null;
	LCPostalInfoSchema mLCPostalInfoSchema = null;
	LCGrpContSchema mLCGrpContSchema = null;
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "ҳ��ʧЧ,�����µ�½";
	} else {
		
		try {
			mLCPostalInfoSet = new  LCPostalInfoSet();
			mLCGrpContSet = new LCGrpContSet();
			//��ȡ�������͵�ǰ̨����
			tOperate = request.getParameter("Operate");
			System.out.println("tOperate="+tOperate);
			
			VData tVData = new VData();
			tVData.add(tGI);
			
			if("INSERT".equals(tOperate)){
								
				String tGrid3 [] = request.getParameterValues("ContInfoGrid3"); //�õ���1�е�����ֵ 
				String tGrid10 [] = request.getParameterValues("ContInfoGrid10");
				String tChk[]=request.getParameterValues("InpContInfoGridChk");
				for(int index=0;index<tChk.length;index++){
					if(tChk[index].equals("1")){
					mLCPostalInfoSchema = new LCPostalInfoSchema();
	
					mLCPostalInfoSchema.setGrpContNo(tGrid3[index]);
					mLCPostalInfoSchema.setCustomerGetPolDate(tGrid10[index]);
					mLCPostalInfoSet.add(mLCPostalInfoSchema);
					}
				}	
				for(int index=0;index<tChk.length;index++){
					if(tChk[index].equals("1")){
					mLCGrpContSchema = new LCGrpContSchema();
					
					mLCGrpContSchema.setGrpContNo(tGrid3[index]);
					mLCGrpContSchema.setCustomGetPolDate(tGrid10[index]);
					mLCGrpContSet.add(mLCGrpContSchema);
					}
				}
			}
			
			tVData.add(mLCPostalInfoSet);
			tVData.add(mLCGrpContSet);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LCPolSendBackUI")) {
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
