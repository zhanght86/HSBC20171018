<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//程序名称：
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%
	String tPayNo = "";
	try
	{
		tPayNo = request.getParameter("PayNo");
		loggerDebug("AllFeeQueryEDetail","号码"+tPayNo);
	}
	catch( Exception e )
	{
		tPayNo = "";
	}
	String tSumActuPayMoney = "";
	try 
	{
		tSumActuPayMoney = request.getParameter("SumActuPayMoney");
		loggerDebug("AllFeeQueryEDetail","金额"+tSumActuPayMoney);
	}
	catch( Exception e )
	{
		tSumActuPayMoney = "";
	}
%>
<head >
<script>
	var tPayNo = "<%=tPayNo%>";  
	var tSumActuPayMoney = "<%=tSumActuPayMoney%>";
</script>

	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<!--SCRIPT src="AllFeeQueryEDetail.js"></SCRIPT-->
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="AllFeeQueryEDetailInit.jsp"%>
	
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<title>交费明细查询 </title>
</head>

<body  onload="initForm();easyQueryClick();" >
  <form  name=fm  id=fm>
  <table >
    	<tr>
    	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLJAPay1);">
    	</td>
			<td class= titleImg>
				费用整体信息
			</td>
		</tr>
	</table>
	<Div  id= "divLJAPay1" class=maxbox style= "display: ''">
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title5>
            交费收据号码
          </TD>
          <TD  class= input5>
            <Input class="wid common" name=PayNo id=PayNo >
          </TD>
          <TD  class= title5>
            总实交金额
          </TD>
          <TD  class= input5>
            <Input class="wid common" name=SumActuPayMoney id=SumActuPayMoney >
          </TD>
        </TR>
  </table>
  </Div>
  
  
      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLJAPPerson1);">
    		</td>
    		<td class= titleImg>
    			 费用详细信息
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLJAPPerson1" style= "display: ;text-align:center">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT class=cssButton90 VALUE="首页" TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT class=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT class=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT class=cssButton93 VALUE="尾页" TYPE=button onclick="turnPage.lastPage();">				
  	</div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
<script>
var turnPage = new turnPageClass(); 
function easyQueryClick()
{
	
	//alert("here");
	// 初始化表格
	initPolGrid();
	
	// 书写SQL语句
	var strSQL = "";

	var sqlid34="ProposalQuerySql34";
	var mySql34=new SqlClass();
	mySql34.setResourceName("sys.ProposalQuerySql"); //指定使用的properties文件名
	mySql34.setSqlId(sqlid34); //指定使用的Sql的id
	mySql34.addSubPara(tPayNo); //指定传入的参数
	strSQL=mySql34.getString();
	
	//strSQL = "select ActuGetNo,EndorsementNo,FeeOperationType,FeeFinaType,ContNo,GrpPolNo,PolNo,GetDate,GetMoney,RiskCode,GrpName,PolType,GetFlag from LJAGetEndorse where ActuGetNo='" + tPayNo + "'";			 
	//strSQL = "select PolNo,GrpPolNo,ContNo,PayNo,PayAimClass,SumActuPayMoney,PayIntv,PayDate,PayType,CurPayToDate from LJAPayPerson";			 
	//alert(strSQL);
	
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


