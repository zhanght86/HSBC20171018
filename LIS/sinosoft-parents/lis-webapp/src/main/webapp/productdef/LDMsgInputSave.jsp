<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
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
<%@page import="com.sinosoft.service.*" %>
<%
	//������Ϣ������У�鴦��
	//�������
	LDMsgInfoSchema tLDMsgInfoSchema = new LDMsgInfoSchema();
	//LDMsgInfoUI tLDMsgInfoUI = new LDMsgInfoUI();
	//�������
	CErrors tError = null;
	String tBmCert = "";
	//����Ҫִ�еĶ�������ӣ��޸�
	String transact = request.getParameter("hiddenAction");
	System.out.println("transact:"+transact);
	
	String tRela = "";
	String FlagStr = "";
	String Content = "";
	//    if(request.getParameter("eName").length()>0)
	tLDMsgInfoSchema.setLanguage(request.getParameter("MsgLan").trim());
	//    if(request.getParameter("cName").length()>0)
	tLDMsgInfoSchema.setKeyID(request.getParameter("hiddenKeyID").trim());
	tLDMsgInfoSchema.setMsg(request.getParameter("MsgDetail"));
	tLDMsgInfoSchema.setMsgType(request.getParameter("MsgType"));
	
		// ׼���������� VData
		VData tVData = new VData();
		tVData.addElement(tLDMsgInfoSchema);
		tVData.addElement(transact);
		//ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
		
		String busiName="LDMsgUI";
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	//tBusinessDelegate.submitData(tVData, "",busiName);
	
		//if(!tLDMsgInfoUI.submitData(tVData, "MSGBOM"))
			if(!tBusinessDelegate.submitData(tVData, "MSG",busiName))
		{
			if (FlagStr == "") {
				tError = tBusinessDelegate.getCErrors();
				if (!tError.needDealError()) {
					System.out.println("�ɹ�");
					Content = ""+"����ɹ�"+"";
					FlagStr = "Succ";
					
				} else {
					System.out.println("ʧ��");
					Content = transact + ""+"����ʧ�ܣ�ԭ����:"+"" + tError.getFirstError();
					FlagStr = "Fail";
				}
			}	
		}			
	System.out.println("FlagStr:"+FlagStr);
%>

<script type="text/javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>