<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//程序名称：
//程序功能：
//创建日期：2002-12-24 11:10:36
//创建人  ：lh
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%
	String tPolNo = "";
	try
	{
		tPolNo = request.getParameter("PolNo");
	}
	catch( Exception e )
	{
		tPolNo = "";
	}
	String tRiskCode = "";
	try
	{
		tRiskCode = request.getParameter("RiskCode");		
	}
	catch( Exception e )
	{
		tRiskCode = "";
	}
		String tInsuredName = "";
	try
	{
		tInsuredName = request.getParameter("InsuredName");
		tInsuredName = new String(tInsuredName.getBytes("ISO-8859-1"), "GBK");
	}
	catch( Exception e )
	{
		tInsuredName = "";
	}
		String tAppntName = "";
	try
	{
		tAppntName = request.getParameter("AppntName");
		tAppntName = new String(tAppntName.getBytes("ISO-8859-1"), "GBK");
	}
	catch( Exception e )
	{
		tAppntName = "";
	}
	String tIsCancelPolFlag = "";
	try
	{
		tIsCancelPolFlag = request.getParameter("IsCancelPolFlag");
	}
	catch( Exception e )
	{
		tIsCancelPolFlag = "";
	}
%>
<head >
<script> 
	var tPolNo = "<%=tPolNo%>";
	var tRiskCode = "<%=tRiskCode%>";
	var tInsuredName = "<%=tInsuredName%>";
	var tAppntName = "<%=tAppntName%>";
	var tIsCancelPolFlag = "<%=tIsCancelPolFlag%>";
</script>

	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
	<SCRIPT src="RelFeeQuery.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="RelFeeQueryInit.jsp"%>
	
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<title>交费查询 </title>
</head>

<body  onload="initForm();easyQueryClick();" >
  <form  name=fm >
  <table >
    	<tr>
    	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    	</td>
			<td class= titleImg>
				保单信息
			</td>
		</tr>
	</table>
	<Div  id= "divLCPol1" style= "display: ''" class=maxbox1>
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title5>
            保单号码
          </TD>
          <TD  class= input5>
          	<Input class= "readonly wid" readonly name=PolNo id=PolNo >
          </TD>
          <TD  class= title5>
            险种编码
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=RiskCode id=RiskCode >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>
            被保人姓名
          </TD>
          <TD  class= input5>
          	<Input class= "readonly wid" readonly name=InsuredName id=InsuredName >
          </TD>
          <TD  class= title5>
            投保人姓名
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=AppntName id=AppntName >
          </TD>
        </TR>
        <tr>
        	<td>
        		<INPUT class=cssButton VALUE="费用明细" TYPE=button onclick="getQueryDetail();"> 					
        	</td>
        </tr>		
     </table>
  </Div>
  
  
      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLJAPay1);">
    		</td>
    		<td class= titleImg>
    			 交费信息
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLJAPay1" style= "display: ; text-align:center">
      	<table  class= common>
       		<tr  class= common>
      	  		<td style=" text-align: left" colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE="首页" class =cssButton90 TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT VALUE="上一页" class =cssButton91 TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT VALUE="下一页" class =cssButton92 TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT VALUE="尾页" class =cssButton93 TYPE=button onclick="turnPage.lastPage();">				
  	</div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br/><br/><br/><br/>
</body>
<script>
var turnPage = new turnPageClass(); 
function easyQueryClick()
{
	// 初始化表格
	initPolGrid();
	
	// 书写SQL语句
	var strSQL = "";
	if( tIsCancelPolFlag == "0"){

		var sqlid25="ProposalQuerySql25";
		var mySql25=new SqlClass();
		mySql25.setResourceName("sys.ProposalQuerySql"); //指定使用的properties文件名
		mySql25.setSqlId(sqlid25); //指定使用的Sql的id
		mySql25.addSubPara(tPolNo ); //指定传入的参数
		strSQL=mySql25.getString();
		
	   //strSQL = "select PayNo,IncomeNo,IncomeType,SumActuPayMoney,PayDate,ConfDate,Operator,ManageCom,AgentCode from LJAPay where IncomeNo='" + tPolNo + "' ";			 
	}
	else
	  if(tIsCancelPolFlag == "1") { //销户保单

			var sqlid26="ProposalQuerySql26";
			var mySql26=new SqlClass();
			mySql26.setResourceName("sys.ProposalQuerySql"); //指定使用的properties文件名
			mySql26.setSqlId(sqlid26); //指定使用的Sql的id
			mySql26.addSubPara(tPolNo ); //指定传入的参数
			strSQL=mySql26.getString();
		  
	   //strSQL = "select PayNo,IncomeNo,IncomeType,SumActuPayMoney,PayDate,ConfDate,Operator,ManageCom,AgentCode from LJAPay where IncomeNo='" + tPolNo + "' ";			 
	  }
	  else {
	  alert("保单类型传输错误!");
	  return;
	  }
	
	//查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
  	PolGrid.clearData();
  	alert("数据库中没有满足条件的数据！");
    //alert("查询失败！");
    return false;
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象
  turnPage.pageDisplayGrid = PolGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage.pageIndex = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  
}
</script>
</html>


