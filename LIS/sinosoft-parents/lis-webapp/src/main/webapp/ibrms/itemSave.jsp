<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
	//�������ƣ�itemSave.jsp
	//�����ܣ���ӣ��޸Ĵ���
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
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<SCRIPT src="./bomAddInput.js"></SCRIPT>
<%
	//�������
	LRBOMItemSchema tLRBOMItemSchema = new LRBOMItemSchema();
	//LDItemUI tLDItemUI = new LDItemUI();
	//�������
	CErrors tError = null;
	String tBmCert = "";
	//����Ҫִ�еĶ�������ӣ��޸�
	//����Ҫִ�еĶ�������ӣ��޸�
	String transact = request.getParameter("Transact");
	loggerDebug("itemSave","transact:" + transact);

	String tRela = "";
	String FlagStr = "";
	String Content = "";
	//    if(request.getParameter("ItemEName").length()>0)
	tLRBOMItemSchema.setName(request.getParameter("ItemEName").trim());
	//    if(request.getParameter("cName").length()>0)
	tLRBOMItemSchema.setCNName(request.getParameter("CNName").trim());
	//    if(request.getParameter("bomName").length()>0)
	tLRBOMItemSchema.setBOMName(request.getParameter("bomName").trim());
	//    if(request.getParameter("IsHierarchical").length()>0)
	tLRBOMItemSchema.setIsHierarchical(request.getParameter(
			"IsHierarchical").trim());
	//  if(request.getParameter("Connector").length()>0)
	tLRBOMItemSchema.setConnector(request.getParameter("Connector")
			.trim());
	//    if(request.getParameter("IsBase").length()>0)
	tLRBOMItemSchema.setIsBase(request.getParameter("IsBase").trim());
	//    if(request.getParameter("SourceType").length()>0)
	tLRBOMItemSchema.setSourceType(request.getParameter("SourceType")
			.trim());
	//    if(request.getParameter("Source").length()>0)
	tLRBOMItemSchema.setSource(request.getParameter("Source").trim());
	//    if(request.getParameter("CommandType").length()>0)
	tLRBOMItemSchema.setCommandType(request.getParameter("CommandType")
			.trim());
	//    if(request.getParameter("PreCheck").length()>0)
	tLRBOMItemSchema.setPreCheck(request.getParameter("PreCheck")
			.trim());
	//    if(request.getParameter("Description").length()>0)
	tLRBOMItemSchema.setDescription(request.getParameter("Description")
			.trim());
	//    if(request.getParameter("Valid").length()>0)
	tLRBOMItemSchema.setValid(request.getParameter("Valid").trim());

	// ׼���������� VData
	VData tVData = new VData();
	tVData.addElement(tLRBOMItemSchema);

	//ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
		String busiName="LDItemUI";
    	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	if (!tBusinessDelegate.submitData(tVData, transact,busiName)) {
		tError = tBusinessDelegate.getCErrors();
		if (tError.needDealError()) {
			loggerDebug("itemSave","ʧ��");
			Content = transact + " ʧ�ܣ�ԭ����:" + tError.getFirstError();
			FlagStr = "Fail";
		}
	} else {
		loggerDebug("itemSave","�ɹ�");
		Content = transact + " �ɹ�";
		FlagStr = "Succ";
	}
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

