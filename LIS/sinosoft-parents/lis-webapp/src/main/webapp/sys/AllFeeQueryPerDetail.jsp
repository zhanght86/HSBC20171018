<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//程序名称：
//程序功能：
//创建日期：2009-04-21 11:13
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%
	String tPayNo = "";
	
	try
	{
		tPayNo = request.getParameter("PayNo");
	}
	catch( Exception e )
	{
		tPayNo = "";
	}
	String tSumActuPayMoney = "";
	try 
	{
		tSumActuPayMoney = request.getParameter("SumActuPayMoney");
		loggerDebug("AllFeeQueryPerDetail",tSumActuPayMoney);
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
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="AllFeeQueryPerDetailInit.jsp"%>
	
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<title>交费明细查询 </title>
</head>

<body  onload="initForm();easyQueryClick();" >
  <form  name=fm id=fm >
  
  <Div  id= "divLJAPPersonHidden" style= "display: ''">
  
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
	<Div  id= "divLJAPay1" style= "display: ''" class=maxbox1>
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title5>
            合同号码
          </TD>
          <TD  class= input5>
            <Input class= "common wid" name=PolNo id=PolNo >
          </TD>
          <TD  class= title5>
            集体合同号码
          </TD>
          <TD  class= input5>
            <Input class= "common wid" name=GrpPolNo id=GrpPolNo >
          </TD>
        </TR>
    <TR  class= common>
          <TD  class= title5>
            交费收据号
          </TD>
          <TD  class= input5>
            <Input class= "common wid" name=PayNo id=PayNo >
          </TD>
          <TD  class= title5>
            总实交金额
          </TD>
          <TD  class= input5>
            <Input class= "common wid" name=SumActuPayMoney id=SumActuPayMoney >
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
  	<Div  id= "divLJAPPerson1" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	<br>
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
	/*strSQL = "select PayNo,PayAimClass,SumActuPayMoney,PayIntv,MakeDate,PayType,CurPayToDate,paycount,contno,grpcontno from LJAPayPerson where payno='" + tPayNo + "' order by paycount";		
	*/mySql = new SqlClass();
	mySql.setResourceName("sys.AllFeeQueryPerDetailSql");
	mySql.setSqlId("AllFeeQueryPerDetailSql1");
	mySql.addSubPara(tPayNo); 
	//查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
  	PolGrid.clearData();
  	alert("该收据对应的保费收入明细不存在，请核实！");
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
  var tGrpPolNo=PolGrid. getRowColData(0,10);
	var tContNo=PolGrid. getRowColData(0,9);
  fm.GrpPolNo.value = tGrpPolNo;
	fm.PolNo.value = tContNo;
}


</script>
</html>


