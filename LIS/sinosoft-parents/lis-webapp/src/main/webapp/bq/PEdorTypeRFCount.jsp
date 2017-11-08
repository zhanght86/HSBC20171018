<%
//程序名称：PEdorTypeRFCount.jsp
//程序功能：
//创建日期：2005-07-09
//创建人  ：Nicholas
//说明    ：本页面作用是初始化界面，其中信息必须从后台查出
//更新记录：  更新人    更新日期     更新原因/内容
%>

  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %> 
  <%@page contentType="text/html;charset=GBK" %>
<html>
<script language="javascript">
	  var tMulArray = new Array();
<%
  //后面要执行的动作：查询
  CErrors tError = new CErrors();                 
  String FlagStr = "";
  String Content = "";
  String tMulLineArray[][];
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");	
  //个人保单批改信息
   LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
   tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
   tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
	tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
	tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
	tLPEdorItemSchema.setInsuredNo(request.getParameter("InsuredNo"));
    tLPEdorItemSchema.setPolNo(request.getParameter("PolNo"));
	tLPEdorItemSchema.setEdorAppDate(request.getParameter("EdorAppDate")); 
	tLPEdorItemSchema.setEdorValiDate(request.getParameter("EdorValiDate")); 
	
	 String busiName="PEdorRFDetailUI";
 	 String transact = "QUERY||MAIN";
	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  try
  {
    // 准备传输数据 VData
    VData tVData = new VData();
    tVData.add(tG);
    tVData.add(tLPEdorItemSchema);
    tBusinessDelegate.submitData(tVData,transact,busiName);
  } catch(Exception ex){
    Content = "查询贷款信息产生错误:" + ex.toString();
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
                Content = "获取保单险种贷款信息失败！";
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
