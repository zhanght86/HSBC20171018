<!--�û�У����-->
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
    //����Ҫִ�еĶ�������ӣ��޸�
    String tRela  = "";
    String FlagStr = "";
    String Content = "";
    String transact = "";
    String tMulLineArray[][];
    String ContNo = request.getParameter("ContNo");
    String EdorNo = request.getParameter("EdorNo");
    tG = (GlobalInput)session.getValue("GI");  
        //����������Ϣ
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

parent.fraInterface.afterCount("<%=FlagStr%>", "<%=Content%>", tMulArray)
    </script>
</html>