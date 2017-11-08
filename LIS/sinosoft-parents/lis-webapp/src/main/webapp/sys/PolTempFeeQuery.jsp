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
	String tContNo = "";
	String tIsCancelPolFlag = "";
	try
	{
		tContNo = request.getParameter("ContNo");
		tIsCancelPolFlag = request.getParameter("IsCancelPolFlag");
	}
	catch( Exception e )
	{
		tContNo = "";
		tIsCancelPolFlag = "";
	}
%>
<head >
<script> 
	var tContNo = "<%=tContNo%>";
	var tIsCancelPolFlag = "<%=tIsCancelPolFlag%>";
</script>

	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
	<SCRIPT src="TempFeeQuery.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="PolTempFeeQueryInit.jsp"%>
	
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<title>暂交费查询 </title>
</head>

<body  onload="initForm();easyQueryClick();" >
  <form  name=fm id=fm >
  <table >
    	<tr>
    	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    	</td>
			<td class= titleImg>
				合同信息
			</td>
		</tr>
	</table>
	<Div  id= "divLCPol1" style= "display: ''">
    <div class="maxbox1" >
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title5>
            合同号码
          </TD>
          <TD  class= input5>
            <Input class="common wid" name=ContNo id=ContNo>
          </TD>
          <TD  class= title5> </TD>
          <TD  class= input5> </TD>
	  </TR>
     </table>
  </Div>
    </Div>
  
  
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLJTempFee1);">
    		</td>
    		<td class= titleImg>
    			 暂交费信息
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLJTempFee1" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td style=" text-align: left" colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE="首  页" class = cssButton90 TYPE=button onClick="turnPage.firstPage();"> 
      <INPUT VALUE="上一页" class = cssButton91 TYPE=button onClick="turnPage.previousPage();"> 					
      <INPUT VALUE="下一页" class = cssButton92 TYPE=button onClick="turnPage.nextPage();"> 
      <INPUT VALUE="尾  页" class = cssButton93 TYPE=button onClick="turnPage.lastPage();">				
  	</div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
<SCRIPT>
var turnPage = new turnPageClass(); 
function easyQueryClick()
{
	// 初始化表格
	initPolGrid();
	
	// 书写SQL语句
	var strSQL = "";

	if( tIsCancelPolFlag == "0"){
// 		strSQL = "select a.TempFeeNo,a.TempFeeType,a.RiskCode,a.PayMoney, c.CodeName, a.PayDate,a.AgentCode,a.ConfDate,a.AppntName,a.Operator,a.Currency "+
// 		         "  from LJTempFee a,LJTempFeeClass b,LDCode c"+
// 		         " where a.TempFeeNo = b.TempFeeNo "+
// 		         "   and b.PayMode = c.code "+
// 		         //"   and a.OtherNo='" + tContNo + "'"+
// 		         //"   and a.OtherNoType in ('2','3')"+
// 		         "   and a.OtherNo in (select contno from lccont where contno='" + tContNo + "'   union  select familycontno from lccont where contno='" + tContNo + "' )"+
// 		         "   and a.OtherNoType in ('0','3')"+ //0-个人单     3-家庭单大合同号
// 		         "   and c.CodeType = 'paymode'";
		
		var sqlid1="PolTempFeeQuerySql1";
   	 	var mySql1=new SqlClass();
   	 	mySql1.setResourceName("sys.PolTempFeeQuerySql");
   	 	mySql1.setSqlId(sqlid1); //指定使用SQL的id
   	 	mySql1.addSubPara(tContNo);//指定传入参数
   	    mySql1.addSubPara(tContNo);
   	 	strSQL = mySql1.getString();
		
	}
	else
	if(tIsCancelPolFlag=="1"){ //销户保单查询
// 		strSQL = "select a.TempFeeNo,a.TempFeeType,a.RiskCode,a.PayMoney, c.CodeName, a.PayDate,a.AgentCode,a.ConfDate,a.AppntName,a.Operator  "+
// 		         "  from LJTempFee a,LJTempFeeClass b,LDCode c"+
// 		         " where a.TempFeeNo = b.TempFeeNo "+
// 		         "   and b.PayMode = c.code "+
// 		         "   and a.OtherNo='" + tContNo + "' "+
// 		         "   and a.OtherNoType in ('0','1')"+
// 		         "   and c.CodeType = 'paymode'";
	
		var sqlid2="PolTempFeeQuerySql2";
   	 	var mySql2=new SqlClass();
   	 	mySql2.setResourceName("sys.PolTempFeeQuerySql");
   	 	mySql2.setSqlId(sqlid2); //指定使用SQL的id
   	 	mySql2.addSubPara(tContNo);//指定传入参数
   	    mySql2.addSubPara(tContNo);
   	 	strSQL = mySql2.getString();
	
	}
	else {
	  alert("保单类型传输错误!");
	  return;
	  }
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


</SCRIPT>
</html>
