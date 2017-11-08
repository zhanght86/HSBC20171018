<%
/***************************************************************
 * <p>ProName��LCPropPrintSave.jsp</p>
 * <p>Title��Ͷ��������</p>
 * <p>Description��Ͷ��������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���ƴ�
 * @version  : 8.0
 * @date     : 2014-04-22
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.utility.VData"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.utility.TransferData"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.schema.LCPrintAppntSchema"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	String tOperate = "";
	String tGrpPropNo = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	LCPrintAppntSchema mLCPrintAppntSchema = null;
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "ҳ��ʧЧ,�����µ�½";
	} else {
		
		try {
			//��ȡ�������͵�ǰ̨����
			tOperate = request.getParameter("Operate");
			VData tVData = new VData();
			TransferData mTransferData = new TransferData();
			tVData.add(tGI);
			if("CREATE".equals(tOperate)){
				String mOfferListNo = request.getParameter("OfferListNo");
				mLCPrintAppntSchema = new LCPrintAppntSchema();
				mLCPrintAppntSchema.setOfferListNo(mOfferListNo);
				mTransferData.setNameAndValue("GrpIDType","");
				mTransferData.setNameAndValue("GrpID","");
				mTransferData.setNameAndValue("GrpName","");
				tVData.add(mLCPrintAppntSchema);
				tVData.add(mTransferData);
				BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
				if (!tBusinessDelegate.submitData(tVData, tOperate, "LCPrintAppntUI")) {
					tContent = tBusinessDelegate.getCErrors().getFirstError();
					tFlagStr = "Fail";
				} else {
					tGrpPropNo = (String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0, 1);
					tFlagStr = "Succ";
					tContent = "����ɹ���";
				}
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
	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>","<%=tGrpPropNo%>");
</script>
</html>
