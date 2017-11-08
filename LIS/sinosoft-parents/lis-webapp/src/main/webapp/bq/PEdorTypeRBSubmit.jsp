<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>

<%
//程序名称：PEdorTypeRBSubmit.jsp
//程序功能：
//创建日期：2005-09-20 16:49:22
//创建人  ：zhangtao程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.sys.*"%>
<%@page import="com.sinosoft.lis.bq.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>


<%
    CErrors tError = null;
    //后面要执行的动作：添加，修改
    String tRela  = "";
    String FlagStr = "";
    String Content = "";
    String transact = "";
    LPEdorItemSet bLPEdorItemSet = new LPEdorItemSet();

    //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
    GlobalInput tGlobalInput = new GlobalInput();
    tGlobalInput = (GlobalInput)session.getValue("GI");

    transact = request.getParameter("fmtransact");
    String edorAcceptNo = request.getParameter("EdorAcceptNo");
    String edorNo = request.getParameter("EdorNo");
    String edorType = request.getParameter("EdorType");
    String contNo = request.getParameter("ContNo");
    String sEdorReasonCode = request.getParameter("EdorReason");
    String sEdorReason = request.getParameter("Remark");

    String bEdorAcceptNo[]= request.getParameterValues("LastEdorItemGrid1");
    String bEdorNo[]= request.getParameterValues("LastEdorItemGrid2");
    String bContNo[]= request.getParameterValues("LastEdorItemGrid6");
    String bEdorType[]= request.getParameterValues("LastEdorItemGrid20");
    String bInsuredNo[]= request.getParameterValues("LastEdorItemGrid7");
    String bPolNo[]= request.getParameterValues("LastEdorItemGrid8");

    //String tChk[] = request.getParameterValues("InpPolGridChk");
    for (int i=0; i<bEdorNo.length; i++)
    {
        //if (tChk[i].equals("1"))
        //{
            LPEdorItemSchema bLPEdorItemSchema = new LPEdorItemSchema();
            bLPEdorItemSchema.setEdorAcceptNo(bEdorAcceptNo[i]);
            bLPEdorItemSchema.setEdorNo(bEdorNo[i]);
            bLPEdorItemSchema.setContNo(bContNo[i]);
            bLPEdorItemSchema.setEdorType(bEdorType[i]);
            bLPEdorItemSchema.setInsuredNo(bInsuredNo[i]);
            bLPEdorItemSchema.setPolNo(bPolNo[i]);
            bLPEdorItemSet.add(bLPEdorItemSchema);
        //}
    }
    if (bLPEdorItemSet == null || bLPEdorItemSet.size() < 1)
    {
          Content = "没有可以回退的保全!";
          FlagStr = "Fail";
    }

    LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
    tLPEdorItemSchema.setEdorAcceptNo(edorAcceptNo);
    tLPEdorItemSchema.setEdorNo(edorNo);
    tLPEdorItemSchema.setContNo(contNo);
    tLPEdorItemSchema.setEdorType(edorType);
    tLPEdorItemSchema.setInsuredNo("000000");
    tLPEdorItemSchema.setPolNo("000000");
    tLPEdorItemSchema.setEdorReasonCode(sEdorReasonCode);
    tLPEdorItemSchema.setEdorReason(sEdorReason);

    // 准备传输数据 VData

    VData tVData = new VData();
//    EdorDetailUI tEdorDetailUI   = new EdorDetailUI();
     String busiName="EdorDetailUI";
	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    try
    {
        tVData.add(tGlobalInput);
        tVData.add(tLPEdorItemSchema);
        tVData.add(bLPEdorItemSet);
//        tEdorDetailUI.submitData(tVData, "EDORITEM|INPUT");
        tBusinessDelegate.submitData(tVData, "EDORITEM|INPUT" ,busiName);
    }
    catch(Exception ex)
    {
          Content = "保存失败，原因是:" + ex.toString();
          FlagStr = "Fail";
    }
    if ("".equals(FlagStr))
    {
//            tError = tEdorDetailUI.mErrors;
            tError = tBusinessDelegate.getCErrors();
            if (!tError.needDealError())
            {
                Content ="保存成功！";
                FlagStr = "Succ";
            }
            else
            {
                Content = "保存失败，原因是:" + tError.getFirstError();
                FlagStr = "Fail";
            }
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
