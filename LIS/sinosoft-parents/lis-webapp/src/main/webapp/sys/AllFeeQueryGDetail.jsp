<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//程序名称：
//程序功能：
//创建日期：2002-12-19 11:10:36
//创建人  ：lh
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
		loggerDebug("AllFeeQueryGDetail",tSumActuPayMoney);
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
		<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="AllFeeQueryGDetail.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="AllFeeQueryGDetailInit.jsp"%>
	<title>交费明细查询 </title>
</head>

<body  onload="initForm();easyQueryClick();" >
  <form  name=fm id="fm">
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
  <div class="maxbox1">
	<Div  id= "divLJAPay1" style= "display: ''">
    <table  class= common align=center>
      	<TR>
        <TD  class= title5>
            集体保单号码
          </TD>
          <TD  class= input5>
            <Input class="common wid"  name=GrpPolNo >
          </TD>
          <TD  class= title5>
            交费收据号码
          </TD>
          <TD  class= input5>
            <Input class="common wid" name=PayNo >
          </TD>
          </TR>
      	<TR  class= common>
          
          <TD  class= title5>
            总实交金额
          </TD>
          <TD  class= input5>
            <Input class="common wid" name=SumActuPayMoney >
          </TD>
        </TR>
     </table>
  </Div>
  </div>
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
      <INPUT VALUE="首  页" TYPE=button class="cssButton90"  onclick="turnPage.firstPage();"> 
      <INPUT VALUE="上一页" TYPE=button class="cssButton91"  onclick="turnPage.previousPage();"> 					
      <INPUT VALUE="下一页" TYPE=button class="cssButton92"  onclick="turnPage.nextPage();"> 
      <INPUT VALUE="尾  页" TYPE=button class="cssButton93"  onclick="turnPage.lastPage();">				
  	</div>
  </form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
<script>
<!--
function easyQueryClick()
{
	// 初始化表格
	initPolGrid();
	
	// 书写SQL语句
	var strSQL = "";

	var sqlid26="ProposalQuerySql26";
	var mySql26=new SqlClass();
	mySql26.setResourceName("sys.ProposalQuerySql"); //指定使用的properties文件名
	mySql26.setSqlId(sqlid26); //指定使用的Sql的id
	mySql26.addSubPara(tPayNo ); //指定传入的参数
	strSQL=mySql26.getString();
	
	//strSQL = "select contno,PayAimClass,SumActuPayMoney,PayIntv,PayDate,CurPayToDate,PayType,ContNo,GrpContNo from LJAPayPerson where PayNo='" + tPayNo + "'";			 
	//alert(strSQL);
	
	//查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
  	PolGrid.clearData();
  	alert("该收据对应的保费收入明细不存在，请核实！");
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
  
	//对上述控件名称进行附值
	var tNo =PolGrid.mulLineCount;
	if(tNo>=1)
	{
		var tGrpPolNo=PolGrid. getRowColData(0,9);
		var tContNo=PolGrid. getRowColData(0,8);
		
		fm.GrpPolNo.value = tGrpPolNo;
	}

	
	}

</script>
</html>


