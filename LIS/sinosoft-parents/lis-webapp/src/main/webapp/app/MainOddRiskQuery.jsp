<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//程序名称：
//程序功能：
//创建日期：2003-1-22 
//创建人  ：lh
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%
	String tPrtNo = "";
	try
	{
		tPrtNo = request.getParameter("PrtNo");
	}
	catch( Exception e )
	{
		tPrtNo = "";
	}
	String tMainPolNo = "";
	try
	{
		tMainPolNo = request.getParameter("MainPolNo");
	}
	catch( Exception e )
	{
		tMainPolNo = "";
	}
%>
<head >
<script> 
	var tPrtNo = "<%=tPrtNo%>";
	var tMainPolNo = "<%=tMainPolNo%>";
</script>

	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<SCRIPT src="ProposalQuery.js"></SCRIPT>
	<%@include file="MainOddRiskQueryInit.jsp"%>
	
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<title>主附险查询 </title>
</head>

<body  onload="initForm();easyQueryClick();" >
  <form  name=fm id="fm" >  
    <table>
    	<tr>
        <td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>
    			 保单信息
    		</td>
    	</tr>
    </table>
    <Div  id= "divLCPol1" class="maxbox1" style= "display:  ;text-align:left">
	    <table  class= common>
	      	<TR  class= common>
	          <TD  class= title5>
	            主险保单号码
	          </TD>
	          <TD class= input5>
	          	<Input class= "readonly wid" readonly name=MainPolNo id="MainPolNo" >
	          </TD>
	          <TD  class= title5>
	            印刷号码
	          </TD>
	          <TD  class= input5>
	          	<Input class= "readonly wid" readonly name=PrtNo id="PrtNo" >
	          </TD>
	        </TR>
	     </table>
	     <INPUT class=cssButton VALUE="投保单明细" TYPE=button onclick="queryDetailClick();"> 	
	  </Div>
	  <table>
    	<tr>
        <td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divMainOddRisk1);">
    		</td>
    		<td class= titleImg>
    			 主附险信息
    		</td>
    	</tr>
    </table>        
  	<Div  id= "divMainOddRisk1" style= "display:  ">
      	<table  class= common >
       		<tr  class= common>
      	  		<td style="text-align: left" colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>	
      <INPUT VALUE="首页" TYPE=button class=cssButton90 onclick="turnPage.firstPage();"> 
      <INPUT VALUE="上一页" TYPE=button class=cssButton91 onclick="turnPage.previousPage();"> 					
      <INPUT VALUE="下一页" TYPE=button class=cssButton92 onclick="turnPage.nextPage();"> 
      <INPUT VALUE="尾页" TYPE=button class=cssButton93 onclick="turnPage.lastPage();">				
  	</Div>
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
	//var strSQL = "";
	
	//strSQL = "select PolNo,RiskCode,InsuredName,AppntName from LCPol where MainPolNo='" + tMainPolNo + "'";			 
	//alert(strSQL);
	var sqlid1="MainOddRiskQuerySql0";
	var mySql1=new SqlClass();	
	mySql1.setResourceName("app.MainOddRiskQuerySql"); //指定使用的properties文件   
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id	
	mySql1.addSubPara(tMainPolNo);//指定传入的参数	
	var strSQL=mySql1.getString();
	
	
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


