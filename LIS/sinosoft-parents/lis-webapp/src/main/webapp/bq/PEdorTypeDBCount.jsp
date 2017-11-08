<!--用户校验类-->
 <%@page import="com.sinosoft.utility.*"%>
 <%@page import="com.sinosoft.lis.schema.*"%>
 <%@page import="com.sinosoft.lis.db.*"%>
 <%@page import="com.sinosoft.lis.vschema.*"%>
 <%@page import="com.sinosoft.lis.bq.*"%>
 <%@page import="com.sinosoft.lis.pubfun.*"%>
 <%@include file="../common/jsp/UsrCheck.jsp"%>
 <%@page contentType="text/html;charset=GBK" %>
<html>
<script language="javascript">
  var tMulArray = new Array();
<%

    GlobalInput tG = new GlobalInput();   
    LPEdorItemSchema tLPEdorItemSchema   = new LPEdorItemSchema();
    CErrors tErrors = new CErrors();
    //后面要执行的动作：添加，修改
    String tRela  = "";
    String FlagStr = "";
    String Content = "";
    String transact = "";
    String tMulLineArray[][];
    String ContNo = request.getParameter("ContNo");
    String EdorNo = request.getParameter("EdorNo");
    tG = (GlobalInput)session.getValue("GI");  
        //个人批改信息
        tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
        tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
        tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
        tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
        tLPEdorItemSchema.setPolNo("000000");
        tLPEdorItemSchema.setInsuredNo("000000");

        VData tVData = new VData();
        tVData.addElement(tG);
        tVData.addElement(tLPEdorItemSchema);
            PEdorDBDetailUI tPEdorDBDetailUI = new PEdorDBDetailUI();
            if (!tPEdorDBDetailUI.submitData(tVData, "EdorQuery"))
            {
                tErrors.copyAllErrors(tPEdorDBDetailUI.mErrors);
            }
            VData tResult = new VData();
            tResult = tPEdorDBDetailUI.getResult();
            tMulLineArray = (String[][])tResult.get(0);
             for (int i=0;i<tMulLineArray.length;i++)
              {
                %>
                    tMulArray[<%=i%>]=new Array();
                tMulArray[<%=i%>][0]="<%=tMulLineArray[i][0]%>";
                tMulArray[<%=i%>][1]="<%=tMulLineArray[i][1]%>";
                tMulArray[<%=i%>][2]="<%=tMulLineArray[i][2]%>";
                tMulArray[<%=i%>][3]="<%=tMulLineArray[i][3]%>";
                tMulArray[<%=i%>][4]="<%=tMulLineArray[i][4]%>";
                tMulArray[<%=i%>][5]="<%=tMulLineArray[i][5]%>";
                tMulArray[<%=i%>][6]="<%=tMulLineArray[i][6]%>";
                tMulArray[<%=i%>][7]="<%=tMulLineArray[i][7]%>";
                tMulArray[<%=i%>][8]="<%=tMulLineArray[i][8]%>";
                tMulArray[<%=i%>][9]="<%=tMulLineArray[i][9]%>";
                <%
              }           

    //页面反馈信息
    if (!tErrors.needDealError())
    {
        FlagStr = "Success";
        Content = "操作成功！";
    }
    else
    {
        FlagStr = "Fail";
        Content = "操作失败，原因是：" + tErrors.getFirstError();
    }
    tErrors = null;
%>

parent.fraInterface.afterCount("<%=FlagStr%>", "<%=Content%>", tMulArray)
    </script>
</html>