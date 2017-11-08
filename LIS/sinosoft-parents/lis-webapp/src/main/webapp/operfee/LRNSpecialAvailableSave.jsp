<%@ page contentType="text/html; charset=gb2312" language="java"
	errorPage=""%>

<%
	 /*******************************************************************************
	 * <p>Title: Lis 6.0</p>
	 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
	 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
	 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
	 * <p>WebSite: http://www.sinosoft.com.cn</p>
	 *
	 * @author   : ����ǿ <XinYQ@sinosoft.com.cn>
	 * @version  : 1.00, 1.01
	 * @date     : 2005-11-21, 2006-02-15
	 * @direction: �������⸴Ч¼���ύ����
	 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp"%>

<%@ page import="com.sinosoft.lis.bq.*"%>
<%@ page import="com.sinosoft.lis.operfee.*"%>
<%@ page import="com.sinosoft.lis.pubfun.*"%>
<%@ page import="com.sinosoft.lis.schema.*"%>
<%@ page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.*" %>  

<%
	//�������ݱ���
	String sContNo = request.getParameter("ContNo");
    String sPayToDate = request.getParameter("LastPayToDate");
	String sInvalidReason = request.getParameter("InvalidReason");
	String sRemark = request.getParameter("Remark");

	//��¼�ֶε�У��
	if (sContNo == null || sContNo.equals("") || sInvalidReason == null
			|| sInvalidReason.equals("")) {
		out.println("<script language=JavaScript>");
		out.println("    alert('��ʾ�������μ���ͬ���롢ʧЧԭ���Ƿ�Ϊ�գ� ');");
		out.println("    window.close();");
		out.println("</script>");
	} else {
		sContNo = sContNo.trim();
		sInvalidReason = sInvalidReason.trim();
	}
	//��ѡ�ֶε�У��
	if (sRemark != null)
		sRemark = sRemark.trim();

	//System.out.println("\t@> LRNSpecialAvailableSave.jsp : �������⸴Ч¼��, ׼���ύ");
	GlobalInput tGlobalInput = new GlobalInput();
	tGlobalInput = (GlobalInput) session.getValue("GI");
	
	//TransferData
	TransferData tTransferData = new TransferData();
	tTransferData.setNameAndValue("ContNo", sContNo);
	tTransferData.setNameAndValue("PayToDate", sPayToDate);
	tTransferData.setNameAndValue("InvalidReason", sInvalidReason);
	if (sRemark != null && !sRemark.equals(""))
		tTransferData.setNameAndValue("Remark", sRemark);
	
	//VData
	VData tVData = new VData();
	tVData.addElement(tGlobalInput);
	tVData.addElement(tTransferData);

	//��̨������
	String FlagStr = "";
	String Content = "";
	CErrors tErrors = new CErrors();

	//���ú�̨����
	//RnSpecialAvailiable tLRNSpecialAvailableBL = new RnSpecialAvailiable();
	
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	
	
	if (!tBusinessDelegate.submitData(tVData, "OPERATE","RnSpecialAvailiable")) {
		//System.out.println("\t@> LRNSpecialAvailableSave.jsp : ���� LRNSpecialAvailableBL.submitData() �ύ����ʧ��");
		tErrors.copyAllErrors(tBusinessDelegate.getCErrors());
		FlagStr = "Fail";
		Content = "����ʧ��, ԭ���ǣ�" + tErrors.getFirstError();
	}
	//ҳ�淴����Ϣ
	if (!tErrors.needDealError()) {
		//System.out.println("\t@> LRNSpecialAvailableSave.jsp : �������⸴Ч¼��, ����ɹ�");
		FlagStr = "Success";
		Content = "��Ч�����ɹ����������Ͻ��й��ڱ������գ�";
	}
%>

<html>
 <script language="JavaScript">
   try
	{
    	parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');
	}
   catch (ex)
    {
    	alert('<%=Content%>');
    }
 </script>
</html>
