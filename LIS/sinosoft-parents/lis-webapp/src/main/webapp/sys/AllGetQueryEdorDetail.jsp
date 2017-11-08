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
		loggerDebug("AllGetQueryEdorDetail",tSumGetMoney);
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
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<!--SCRIPT src="AllGetQueryEdorDetail.js"></SCRIPT-->
	<%@include file="AllGetQueryEdorDetailInit.jsp"%>
	
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<title>给付明细查询 </title>
</head>

<body  onload="initForm();easyQueryClick();" >
  <form  name=fm id=fm >
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
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLJAGetEndorse1);">
    		</td>
    		<td class= titleImg>
    			 给付详细信息
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLJAGetEndorse1" style= "display: ''"  align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE="首  页" TYPE=button class=cssButton90 onclick="turnPage.firstPage();"> 
      <INPUT VALUE="上一页" TYPE=button class=cssButton91 onclick="turnPage.previousPage();"> 					
      <INPUT VALUE="下一页" TYPE=button class=cssButton92 onclick="turnPage.nextPage();"> 
      <INPUT VALUE="尾  页" TYPE=button class=cssButton93 onclick="turnPage.lastPage();">				
  	</div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
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


	var sqlid39="ProposalQuerySql39";
	var mySql39=new SqlClass();
	mySql39.setResourceName("sys.ProposalQuerySql"); //指定使用的properties文件名
	mySql39.setSqlId(sqlid39); //指定使用的Sql的id
	mySql39.addSubPara(tActuGetNo); //指定传入的参数
	strSQL=mySql39.getString();
	
	//strSQL = "select ActuGetNo,EndorsementNo,contno,PolType,GetMoney,GetDate,GetFlag,(select codename from ldcode where codetype='edortype' and code=FeeOperationType and rownum=1),(select codename from ldcode where codetype='feefinatype' and code=FeeFinaType and rownum=1),RiskCode,GrpName from LJAGetEndorse where ActuGetNo='" + tActuGetNo + "'";			 	 

	
	//查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
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


