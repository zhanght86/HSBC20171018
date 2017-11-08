<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>

<%
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : ����ǿ <XinYQ@sinosoft.com.cn>
 * @version  : 1.00
 * @date     : 2005-11-14
 * @direction: �����Զ��潻ʵ��ҵ����
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>

<%@ page import="com.sinosoft.utility.*" %>
<%@ page import="com.sinosoft.lis.pubfun.*" %>
<%@ page import="com.sinosoft.lis.schema.*" %>
<%@ page import="com.sinosoft.lis.vschema.*" %>
<%@ page import="com.sinosoft.lis.bq.*" %>
<%@page import="com.sinosoft.service.*" %>

<%
    //�ı���
    String sEdorAcceptNo = request.getParameter("EdorAcceptNo");
    String sEdorType = request.getParameter("EdorType");
    String sContNo = request.getParameter("ContNo");
    //������
    String sEdorNo = request.getParameter("EdorNo");
    String sPolNo = request.getParameter("PolNo");
    String sInsuredNo = request.getParameter("InsuredNo");
    //�潻
    String sAutoPayFlag = request.getParameter("AutoPayFlag");
    

    ////����У��
    //if (sEdorAcceptNo == null || sEdorAcceptNo.equals("") || sEdorType == null || sEdorType.equals("") || sContNo == null || sContNo.equals(""))
    //{
    //    out.println("<script language='JavaScript'>");
    //    out.println("  alert('�������鱣ȫ����š��������͡��������Ƿ�Ϊ�գ� ');");
    //    out.println("  window.close();");
    //    out.println("</script>");
    //}
    //if (sEdorNo == null || sEdorNo.equals("") || sPolNo == null || sPolNo.equals("") || sInsuredNo == null || sInsuredNo.equals(""))
    //{
    //    out.println("<script language='JavaScript'>");
    //    out.println("  alert('�����޷���ȡ��ȫ�����š����ֺš������˿ͻ��ţ� ');");
    //    out.println("  window.close();");
    //    out.println("</script>");
    //}
    //if (sAutoPayFlag == null || sAutoPayFlag == "")
    //{
    //    out.println("<script language='JavaScript'>");
    //    out.println("  alert('�����������Ƿ��Զ��潻��ǣ� ');");
    //    out.println("  window.close();");
    //    out.println("</script>");
    //}

    //�������
    String FlagStr = "";
    String Content = "";
    CErrors tErrors = new CErrors();

    //ҵ����
    GlobalInput tGlobalInput = new GlobalInput();
    tGlobalInput = (GlobalInput)session.getValue("GI");

    LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
    tLPEdorItemSchema.setOperator(tGlobalInput.Operator);
    tLPEdorItemSchema.setEdorAcceptNo(sEdorAcceptNo);
    tLPEdorItemSchema.setEdorType(sEdorType);
    tLPEdorItemSchema.setContNo(sContNo);
    tLPEdorItemSchema.setEdorNo(sEdorNo);
	tLPEdorItemSchema.setPolNo(sPolNo);
	tLPEdorItemSchema.setInsuredNo(sInsuredNo);
    //tLPEdorItemSchema.setEdorReason("");

    TransferData tTransferData = new TransferData();
    tTransferData.setNameAndValue("AutoPayFlag", sAutoPayFlag);

    //��������
    VData tVData = new VData();
    tVData.addElement(tGlobalInput);
    tVData.addElement(tLPEdorItemSchema);
    tVData.addElement(tTransferData);

    //�ύ����
//    EdorDetailUI tEdorDetailUI = new EdorDetailUI();
	 String busiName="EdorDetailUI";
	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
//    if (!tEdorDetailUI.submitData(tVData, "OPERATE"))
    if (!tBusinessDelegate.submitData(tVData, "OPERATE",busiName))
    {
        System.out.println("\t@> PEdorTypeAPSubmit.jsp : ���� EdorDetailUI.submitData() �ύ����ʧ��");
//        tErrors.copyAllErrors(tEdorDetailUI.mErrors);
        tErrors.copyAllErrors(tBusinessDelegate.getCErrors());
        FlagStr = "Fail";
        Content = "����ʧ��, ԭ���ǣ�" + tErrors.getFirstError();
    }
    if (FlagStr.equals("") && !tErrors.needDealError())
    {
        System.out.println("\t@> PEdorTypeAPSubmit.jsp : �����Զ��潻����ɹ�");
        FlagStr = "Success";
        Content = "�����ɹ���";
    }
%>


<html>
<head>
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
