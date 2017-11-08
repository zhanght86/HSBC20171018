<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>

<%
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : 辛玉强 <XinYQ@sinosoft.com.cn>
 * @version  : 1.00
 * @date     : 2006-12-23
 * @direction: 用户保全权限级别定制保存
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>

<%@ page import="com.sinosoft.lis.pubfun.*" %>
<%@ page import="com.sinosoft.lis.schema.*" %>
<%@ page import="com.sinosoft.lis.vschema.*" %>
<%@page import="com.sinosoft.service.*" %>
<%@ page import="com.sinosoft.utility.*" %>

<%
    //接收数据变量
    String sUserCode = request.getParameter("UserCode");
    String sEdorPopedom = request.getParameter("EdorPopedom");
    String sGEdorPopedom = request.getParameter("GEdorPopedom");

    //收集整理数据
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

    //后台处理标记
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
				   Content = "操作失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
				   FlagStr = "Fail";
				}
				else
				{
				   Content = "操作失败";
				   FlagStr = "Fail";				
				}
   }
   else
   {
      	Content = " 操作成功! ";
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
