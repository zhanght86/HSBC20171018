<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//程序名称：
//程序功能：
//创建日期：2002-1-21
//创建人  ：lh
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%
	String tEdorAcceptNo = "";
	String tPolNo = "";
	try
	{
		tEdorAcceptNo = request.getParameter("EdorAcceptNo");
		tPolNo = request.getParameter("PolNo");
		loggerDebug("AllPBqItemQuery","号码"+tEdorAcceptNo);
	}
	catch( Exception e )
	{
		tEdorAcceptNo = "";
	}
	String tSumGetMoney = "";
	try 
	{
		tSumGetMoney = request.getParameter("SumGetMoney");
		loggerDebug("AllPBqItemQuery","金额"+tSumGetMoney);
	}
	catch( Exception e )
	{
		tSumGetMoney = "";
	}
%>
<head >
<script>
	var tEdorAcceptNo = "<%=tEdorAcceptNo%>"; 
	var tPolNo = "<%=tPolNo%>";   
	var tSumGetMoney = "<%=tSumGetMoney%>"; 
</script>

	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<!--SCRIPT src="AllPBqItemQuery.js"></SCRIPT-->
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="AllPBqItemQueryInit.jsp"%>
	
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<title>个人保全明细查询 </title>
</head>

<body  onload="initForm();easyQueryClick();" >
  <form  name=fm id=fm>
  <table >
    	<tr>
    	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLPEdorMain1);">
    	</td>
			<td class= titleImg>
				项目明细信息
			</td>
		</tr>
	</table>
	<Div  id= "divLPEdorMain1" style= "display: ''" class=maxbox1>
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title5>
            保全受理号码
          </TD>
          <TD  class= input5>
            <Input class= "common wid" name=EdorAcceptNo id=EdorAcceptNo >
          </TD>
          <TD  class= title5>
            总补退费金额
          </TD>
          <TD  class= input5>
            <Input class= "common wid" name=SumGetMoney id=SumGetMoney >
          </TD>
        </TR>
  </table>
  </Div>
  
  
      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLPEdorMain2);">
    		</td>
    		<td class= titleImg>
    			 项目详细信息
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLPEdorMain2" style= "display: '';text-align:center">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE="首页"  class = cssButton90 TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT VALUE="上一页" class = cssButton91 TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT VALUE="下一页" class = cssButton92 TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT VALUE="尾页" class = cssButton93 TYPE=button onclick="turnPage.lastPage();">				
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
	
	//strSQL = "select EdorNo,EdorType,GetMoney,EdorValiDate,EdorAppDate,decode(EdorState,'0','保全确认','1','正在申请','2','申请确认'),Operator from LPEdorMain where EdorAcceptNo='" + tEdorAcceptNo + "' order by makedate,maketime ";			 
	var sqlresourcename = "sys.AllPBqItemQuerySql";
	var sqlid1="AllPBqItemQuerySql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(tEdorAcceptNo);
	//查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(mySql1.getString(), 1, 0, 1);  
  
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


