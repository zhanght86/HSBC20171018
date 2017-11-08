<%
//程序名称：PEdorTypeTRCount.jsp
//程序功能：
//创建日期：2005-07-04
//创建人  ：Nicholas
//说明    ：本页面作用是初始化MulLine，其中信息必须从后台查出
//更新记录：  更新人    更新日期     更新原因/内容
%>


  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page contentType="text/html;charset=GBK" %>

<html>
<script language="javascript">
  //产生jsp的二维数组
  var tMulArray = new Array();

<%
  //后面要执行的动作：查询
  CErrors tError = null;                 
  String FlagStr = "";
  String Content = "";
  Double tWholeMoney = new Double(0.0);
  Double tWholeMoneyIntrest = new Double(0.0);
  Double tSumWholeMoney = new Double(0.0);
  //Integer tArrayRows = new Integer(0);
  //VData tMulLineCon = new VData();
  String tMulLineArray[][];


	
  //个人保单批改信息
  LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
  tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
  tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
	tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
	tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
	tLPEdorItemSchema.setInsuredNo(request.getParameter("InsuredNo"));
	tLPEdorItemSchema.setEdorAppDate(request.getParameter("EdorAppDate")); 
	tLPEdorItemSchema.setEdorValiDate(request.getParameter("EdorValiDate")); 
	
	String tisCalInterest = request.getParameter("isCalInterest");
	System.out.println("==> CallInterestFlag is " + tisCalInterest);
	
	PEdorTRDetailBLF tPEdorTRDetailBLF = new PEdorTRDetailBLF();
  try 
  {
    // 准备传输数据 VData
    VData tVData = new VData();  
    tVData.add(tLPEdorItemSchema);
    tVData.add(tisCalInterest);
    tPEdorTRDetailBLF.submitData(tVData,"QUERY||MAIN");

  } 
  catch(Exception ex) 
  {
    Content = "查询自垫信息产生错误:" + ex.toString();
    FlagStr = "Fail";
	}			
	
  //如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr.equals("")) 
  {
  	tError = tPEdorTRDetailBLF.mErrors;
  	if (!tError.needDealError()) 
  	{
  		FlagStr = "Success";

  		if (tPEdorTRDetailBLF.getResult()!=null && tPEdorTRDetailBLF.getResult().size()>0)
  	  {
  	  	VData tResult = new VData();
  	  	tResult = tPEdorTRDetailBLF.getResult();
  		  if (tResult==null) 
  		  {
  				FlagStr = "Fail"; 
  				Content = "获取自垫信息失败！";
  		  }
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
			  	<%
			  }
  	  }
    } 
    else  
    {
  		Content = tError.getFirstError();
  		FlagStr = "Fail";
  	}
	}
%>
	parent.fraInterface.afterCount("<%=FlagStr%>", tMulArray, "<%=Content%>", "<%=String.valueOf(tWholeMoney.doubleValue())%>","<%=String.valueOf(tWholeMoneyIntrest.doubleValue())%>","<%=String.valueOf(tSumWholeMoney.doubleValue())%>");
</script>
</html>

