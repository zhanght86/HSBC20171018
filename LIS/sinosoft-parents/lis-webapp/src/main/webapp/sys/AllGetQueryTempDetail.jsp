<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//程序名称：
//程序功能：
//创建日期：2002-12-20 
//创建人  ：lh
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%
	String tActuGetNo = "";
	try
	{
		tActuGetNo = request.getParameter("ActuGetNo");
	}
	catch( Exception e )
	{
		tActuGetNo = "";
	}
	String tSumGetMoney = "";
	try 
	{
		tSumGetMoney = request.getParameter("SumGetMoney");
		loggerDebug("AllGetQueryTempDetail",tSumGetMoney);
	}
	catch( Exception e )
	{
		tSumGetMoney = "";
	}
%>
<head >
<script> 
	var tActuGetNo = "<%=tActuGetNo%>";  
	var tSumGetMoney = "<%=tSumGetMoney%>";
</script>

	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<!--SCRIPT src="AllGetQueryTempDetail.js"></SCRIPT-->
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="AllGetQueryTempDetailInit.jsp"%>
	
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<title>给付明细查询 </title>
</head>

<body  onload="initForm();easyQueryClick();" >
  <form  name=fm id=fm>
  <table >
    	<tr>
    	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLJAGet1);">
    	</td>
			<td class= titleImg>
				给付整体信息
			</td>
		</tr>
	</table>
	<Div  id= "divLJAGet1" style= "display: ''" class=maxbox1>
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title5>
            实付号码
          </TD>
          <TD  class= input5>
            <Input class= "common wid" name=ActuGetNo id=ActuGetNo >
          </TD>
          <TD  class= title5>
            总给付金额
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
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLJAGetTempFee1);">
    		</td>
    		<td class= titleImg>
    			 给付详细信息
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLJAGetTempFee1" style= "display: ''"  align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE="首页" class=cssButton90 TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT VALUE="上一页" class=cssButton91 TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT VALUE="下一页" class=cssButton92 TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT VALUE="尾页" class=cssButton93 TYPE=button onclick="turnPage.lastPage();">				
  	</div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  
<br/><br/><br/><br/>
</body>
<script>
var turnPage = new turnPageClass(); 
var mySql = new SqlClass();
function easyQueryClick()
{
	
	//alert("here");
	// 初始化表格
	initPolGrid();
	
	// 书写SQL语句
	var strSQL = "";
	
	//strSQL = "select ActuGetNo,TempFeeNo,(select codename from ldcode where codetype='tempfeetype' and code=TempFeeType),(select codename from ldcode where codetype='paymode' and code=PayMode),GetMoney,GetDate,GetReasonCode,'暂收退费','暂收退费',RiskCode from LJAGetTempFee where ActuGetNo='" + tActuGetNo + "'";			 	 
	mySql = new SqlClass();
	mySql.setResourceName("sys.AllGetQueryTempDetailSql");
	mySql.setSqlId("AllGetQueryTempDetailSql1");
	mySql.addSubPara(tActuGetNo); 
	
	//查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
  	PolGrid.clearData();
  	alert("该实付号对应的给付明细不存在，请核实！");
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


