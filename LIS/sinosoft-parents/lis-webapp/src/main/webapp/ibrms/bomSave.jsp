<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	//�������ƣ�bomSave.jsp
	//�����ܣ���ӣ��޸�BOM����
	//�������ڣ� 
	//������  �� 
	//���¼�¼��
	//
%>
<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.ibrms.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
	//������Ϣ������У�鴦��
	//�������
	LRBOMSchema tLRBOMSchema = new LRBOMSchema();
	//LDBomUI tLDBomUI = new LDBomUI();
	//�������
	CErrors tError = null;
	String tBmCert = "";
	//����Ҫִ�еĶ�������ӣ��޸�
	String transact = request.getParameter("Transact");
	loggerDebug("bomSave","transact:"+transact);
	
	String tRela = "";
	String FlagStr = "";
	String Content = "";
	//    if(request.getParameter("eName").length()>0)
	tLRBOMSchema.setName(request.getParameter("eName").trim());
	//    if(request.getParameter("cName").length()>0)
	tLRBOMSchema.setCNName(request.getParameter("cName").trim());
	tLRBOMSchema.setFBOM(request.getParameter("fBOM"));
	tLRBOMSchema.setLocalItem(request.getParameter("localItem").trim());
	tLRBOMSchema.setFatherItem(request.getParameter("fatherItem").trim());
	tLRBOMSchema.setBOMLevel(request.getParameter("bomLevel").trim());
	//    if(request.getParameter("Business").length()>0)
	tLRBOMSchema.setBusiness(request.getParameter("Business").trim());
	//    if(request.getParameter("Description").length()>0)
	tLRBOMSchema.setDiscription(request.getParameter("Description").trim());
	//    if(request.getParameter("Valid").length()>0)
	tLRBOMSchema.setSource("com.sinosoft.ibrms.bom."+request.getParameter("eName").trim());	
	tLRBOMSchema.setValid(request.getParameter("Valid").trim());
	
		// ׼���������� VData
		VData tVData = new VData();
		tVData.addElement(tLRBOMSchema);

		//ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
		String busiName="LDBomUI";
    	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		if(!tBusinessDelegate.submitData(tVData, transact,busiName))
		{
			if (FlagStr == "") {
				tError = tBusinessDelegate.getCErrors();
				if (!tError.needDealError()) {
					loggerDebug("bomSave","�ɹ�");
					Content = transact + " �ɹ�";
					FlagStr = "Succ";
					
				} else {
					loggerDebug("bomSave","ʧ��");
					Content = transact + " ʧ�ܣ�ԭ����:" + tError.getFirstError();
					FlagStr = "Fail";
				}
			}	
		}			
	loggerDebug("bomSave","FlagStr:"+FlagStr);
%>

<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
