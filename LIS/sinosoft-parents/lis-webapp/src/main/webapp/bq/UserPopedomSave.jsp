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
 * @date     : 2006-12-23
 * @direction: �û���ȫȨ�޼����Ʊ���
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>

<%@ page import="com.sinosoft.lis.pubfun.*" %>
<%@ page import="com.sinosoft.lis.schema.*" %>
<%@ page import="com.sinosoft.lis.vschema.*" %>
<%@page import="com.sinosoft.service.*" %>
<%@ page import="com.sinosoft.utility.*" %>

<%
    //�������ݱ���
    String sUserCode = request.getParameter("UserCode");
    String sEdorPopedom = request.getParameter("EdorPopedom");
    String sGEdorPopedom = request.getParameter("GEdorPopedom");

    //�ռ���������
    //GlobalInput
    GlobalInput tGlobalInput = new GlobalInput();
    tGlobalInput = (GlobalInput)session.getValue("GI");
    
		TransferData mTransferData = new TransferData();
		mTransferData.setNameAndValue("UserCode", sUserCode);
		mTransferData.setNameAndValue("EdorPopedom", sEdorPopedom);
		mTransferData.setNameAndValue("GEdorPopedom", sGEdorPopedom);
    
    
    //VData
    VData tVData = new VData();
    tVData.add(tGlobalInput);
    tVData.add(mTransferData);

    //��̨������
    String FlagStr = new String("");
    String Content = new String("");
    CErrors tErrors = new CErrors();

   String sOperate = "OPERATION"; 
   String busiName="UserEdorPopedomUI";
   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
   if(!tBusinessDelegate.submitData(tVData,sOperate,busiName))
   {    
        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
        { 
				   Content = "����ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
				   FlagStr = "Fail";
				}
				else
				{
				   Content = "����ʧ��";
				   FlagStr = "Fail";				
				}
   }
   else
   {
      	Content = " �����ɹ�! ";
       	FlagStr = "Succ";  
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
