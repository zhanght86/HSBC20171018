  <%@page contentType="text/html;charset=GBK" %>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
   <%@page import="com.sinosoft.service.*" %> 


  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>

<html>
<script language="javascript">
  //����jsp�Ķ�ά����
  var tMulArray = new Array();

<%
  //����Ҫִ�еĶ�������ѯ
  	CErrors tError = null;
  	String FlagStr = "";
  	String Content = "";
  	String tMulLineArray[][];
  	String tRequitalDate = "";
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");
  	//���˱���������Ϣ
  	LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
  	tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
  	tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
    tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
    tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
    tLPEdorItemSchema.setInsuredNo(request.getParameter("InsuredNo"));
  	tLPEdorItemSchema.setPolNo(request.getParameter("PolNo"));
    tLPEdorItemSchema.setEdorAppDate(request.getParameter("EdorAppDate"));
    tLPEdorItemSchema.setEdorValiDate(request.getParameter("EdorValiDate"));
 	 String busiName="PEdorLNDetailUI";
 	 String transact = "QUERY||MAIN";
	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  try
  {
    // ׼���������� VData
    VData tVData = new VData();
    tVData.add(tG);
    tVData.add(tLPEdorItemSchema);
    tBusinessDelegate.submitData(tVData,transact,busiName);
  } catch(Exception ex){
    Content = "��ѯ������Ϣ��������:" + ex.toString();
    FlagStr = "Fail";
  }

    tError = tBusinessDelegate.getCErrors(); 
    if (!tError.needDealError()){
        FlagStr = "Success";
        VData tResult = new VData();
        tResult = tBusinessDelegate.getResult();
          if (tResult==null)
          {
                FlagStr = "Fail";
                Content = "��ȡ�������ִ�����Ϣʧ�ܣ�";
          }
        tMulLineArray = (String[][])tResult.get(0);
        for (int i=0;i<tMulLineArray.length;i++){
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
    }
    else {
        Content = tError.getFirstError();
        FlagStr = "Fail";
    }

%>

    parent.fraInterface.afterCount("<%=FlagStr%>", "<%=Content%>", tMulArray);
</script>
</html>

