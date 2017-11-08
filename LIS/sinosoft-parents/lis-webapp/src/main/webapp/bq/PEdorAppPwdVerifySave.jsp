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
 * @date     : 2006-11-06
 * @direction: ��ȫ�������������ĿУ�鱣�����뱣��
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>

<%@ page import="com.sinosoft.lis.bq.*" %>
<%@ page import="com.sinosoft.lis.pubfun.*" %>
<%@ page import="com.sinosoft.lis.schema.*" %>
<%@ page import="com.sinosoft.lis.vschema.*" %>
<%@ page import="com.sinosoft.utility.*" %>

<%
    //�������ݱ���
    String sOtherNo = request.getParameter("OtherNo");
    String sOtherNoType = request.getParameter("OtherNoType");

    //����������Ϣ
    String arrGridNo[] = request.getParameterValues("ContPwdGridNo");
    String arrContNo[] = request.getParameterValues("ContPwdGrid1");
    String arrPassword[] = request.getParameterValues("ContPwdGrid7");

    //��̨������
    String FlagStr = new String("");
    String Content = new String("");
    CErrors tErrors = new CErrors();

    //�ռ���������
    //GlobalInput
    GlobalInput tGlobalInput = new GlobalInput();
    tGlobalInput = (GlobalInput)session.getValue("GI");

    //LCContSet
    LCContSet tLCContSet = new LCContSet();
    if (arrGridNo != null)
    {
        for (int i = 0; i < arrGridNo.length; i++)
        {
            LCContSchema tLCContSchema = new LCContSchema();
            tLCContSchema.setContNo(arrContNo[i]);
            tLCContSchema.setPassword(arrPassword[i]);
            tLCContSet.add(tLCContSchema);
            tLCContSchema = null;
        }
    }

    //VData
    VData tVData = new VData();
    tVData.add(tGlobalInput);
    tVData.add(tLCContSet);
    //��������
    tGlobalInput = null;
    tLCContSet = null;

    //���ú�̨����
    //PEdorContPwdBL
    PEdorContPwdBL tPEdorContPwdBL = new PEdorContPwdBL();
    if (!tPEdorContPwdBL.submitData(tVData, "verify"))
    {
        tErrors.copyAllErrors(tPEdorContPwdBL.getErrors());
    }
    tPEdorContPwdBL = null;
    tVData = null;

    //ҳ�淴����Ϣ
    if (!tErrors.needDealError())
    {
        FlagStr = "Success";
        Content = "�����ɹ���";
    }
    else
    {
        FlagStr = "Fail";
        Content = "����ʧ�ܣ�ԭ���ǣ�" + tErrors.getFirstError();
    }
    tErrors = null;
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
