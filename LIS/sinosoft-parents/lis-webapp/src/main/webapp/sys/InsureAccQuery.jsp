<%@page contentType="text/html;charset=GBK" %>
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
    String tIsCancelPolFlag = "";
	try
	{
		tPolNo = request.getParameter("PolNo");
		tIsCancelPolFlag = request.getParameter("IsCancelPolFlag");
	}
	catch( Exception e )
	{
		tPolNo = "";
		tIsCancelPolFlag = "";
	}
%>
<head >
<script> 
	var tPolNo = "<%=tPolNo%>";
	var tIsCancelPolFlag = "<%=tIsCancelPolFlag%>";
</script>

	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<SCRIPT src="InsureAccQuery.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="InsureAccQueryInit.jsp"%>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
	<title>帐户查询 </title>
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
	<Div  id= "divLCPol1" class="maxbox1" style= "display: ''" >
		<table  class= common align=center>
			<TR  class= common>
				<TD  class= title>
					保单险种号码
				</TD>
				<TD  class= input>
					<Input class= "readonly wid" name=PolNo id="PolNo" >
				</TD>
				<TD  class= title>
				</TD>
				<TD  class= input>
				</TD>
				<TD  class= title>
				</TD>
				<TD  class= input>
				</TD>
			</TR>
		</table>
	</Div>
  
  
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCInsureAcc1);">
    		</td>
    		<td class= titleImg>
    			 帐户信息
    		</td>
    	</tr>
    </table>
    
  
  	<Div  id= "divLCInsureAcc1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td style=" text-align: left" colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    
     <Div  id= "divButton" style= "display: ''" align = center>
      <INPUT VALUE="首页" class=cssButton90 TYPE=button onClick="turnPage.firstPage();"> 
      <INPUT VALUE="上一页" class=cssButton91 TYPE=button onClick="turnPage.previousPage();"> 					
      <INPUT VALUE="下一页" class=cssButton92 TYPE=button onClick="turnPage.nextPage();"> 
      <INPUT VALUE="尾页" class=cssButton93 TYPE=button onClick="turnPage.lastPage();">
    </Div>				
  	</div>
    <!--INPUT VALUE="个人保单明细" class = cssButton TYPE=button onclick="showPolDetail();"-->
    <INPUT class=cssButton VALUE=" 返回 " TYPE=button onClick="top.close();">	 
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
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
	  if(trim(tPolNo).substring(11,13)=='22')
	  {
//	  	strSQL = "select LCInsureAcc.PolNo,LCInsureAcc.RiskCode,lcpol.AppntName,LMRiskInsuAcc.InsuAccName,LCInsureAcc.ModifyDate,LCInsureAcc.SumPay,LCInsureAcc.SumPaym,(select sum(money) from lcinsureacctrace m where m.contno = LCInsureAcc.contno and insuaccno=LCInsureAcc.insuaccno),LCInsureAcc.InsuAccGetMoney,LCInsureAcc.FrozenMoney,LCInsureAcc.BalaDate from LCInsureAcc ,LMRiskInsuAcc, LCPol where LCInsureAcc.GrpPolNo='" + tPolNo + "' and LCInsureAcc.InsuAccNo = LMRiskInsuAcc.InsuAccNo and lcpol.POLNO = lcInsureAcc.POLNO and lcpol.grpcontno = lcInsureAcc.grpcontno";
	  	
	  	var sqlid1="InsureAccQuerySql1";
   	 	var mySql1=new SqlClass();
   	 	mySql1.setResourceName("sys.InsureAccQuerySql");
   	 	mySql1.setSqlId(sqlid1); //指定使用SQL的id
   	 	mySql1.addSubPara(tPolNo);//指定传入参数
   	    strSQL = mySql1.getString();
	  	
	  }
	  else
	  {
//	  	strSQL = "select LCInsureAcc.PolNo,LCInsureAcc.RiskCode,lcpol.AppntName,LMRiskInsuAcc.InsuAccName,LCInsureAcc.ModifyDate,LCInsureAcc.SumPay,LCInsureAcc.SumPaym,(select sum(money) from lcinsureacctrace m where m.contno = LCInsureAcc.contno and insuaccno=LCInsureAcc.insuaccno),LCInsureAcc.InsuAccGetMoney,LCInsureAcc.FrozenMoney,LCInsureAcc.BalaDate,LCInsureAcc.Currency from LCInsureAcc ,LMRiskInsuAcc, LCPol where LCInsureAcc.PolNo='" + tPolNo + "' and LCInsureAcc.InsuAccNo = LMRiskInsuAcc.InsuAccNo and lcpol.POLNO = lcInsureAcc.POLNO and lcpol.grpcontno = lcInsureAcc.grpcontno";			 
	  
	  	var sqlid2="InsureAccQuerySql2";
   	 	var mySql2=new SqlClass();
   	 	mySql2.setResourceName("sys.InsureAccQuerySql");
   	 	mySql2.setSqlId(sqlid2); //指定使用SQL的id
   	 	mySql2.addSubPara(tPolNo);//指定传入参数
   	    strSQL = mySql2.getString();
	  
	  }
	}
	else
	if(tIsCancelPolFlag=="1"){ //销户保单查询
//      strSQL = "select LCInsureAcc.PolNo,LCInsureAcc.RiskCode,lcpol.AppntName,LMRiskInsuAcc.InsuAccName,LCInsureAcc.ModifyDate,LCInsureAcc.SumPay,LCInsureAcc.SumPaym,(select sum(money) from lcinsureacctrace m where m.contno = LCInsureAcc.contno and insuaccno=LCInsureAcc.insuaccno),LCInsureAcc.InsuAccGetMoney,LCInsureAcc.FrozenMoney,LCInsureAcc.BalaDate from LCInsureAcc ,LMRiskInsuAcc, LCPol  where PolNo='" + tPolNo + "' and LCInsureAcc.InsuAccNo = LMRiskInsuAcc.InsuAccNo and lcpol.POLNO = lcInsureAcc.POLNO and lcpol.grpcontno = lcInsureAcc.grpcontno";			 
	
        var sqlid3="InsureAccQuerySql3";
 	 	var mySql3=new SqlClass();
 	 	mySql3.setResourceName("sys.InsureAccQuerySql");
 	 	mySql3.setSqlId(sqlid3); //指定使用SQL的id
 	 	mySql3.addSubPara(tPolNo);//指定传入参数
 	    strSQL = mySql3.getString();
	
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


