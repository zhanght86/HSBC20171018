<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
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
	String tPolNo = "";
	try
	{
		tPolNo = request.getParameter("PolNo");
	}
	catch( Exception e )
	{
		tPolNo = "";
	}
%>
<head >
<script> 
	var tPolNo = "<%=tPolNo%>";  
</script>

	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT><SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT><SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="RePolQuery.js"></SCRIPT>
	<SCRIPT src="AllProposalQuery.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css><LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="RePolDetailQueryInit.jsp"%>
	
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<title>保单查询</title>
</head>

<body  onload="initForm();easyQueryClick();" >
  <form  name=fm >  
    <table>
    	<tr>
        <td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPol1);">
    		</td>
    		<td class= titleImg>
保单信息
    		</td>
    	</tr>
    </table>
  	<Div  id= "divPol1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td style="text-align:left;" colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	<table>
              <tr>		
			            <td><INPUT class=common VALUE="保全变更查询" TYPE=button onclick="FeeQueryClick();"> </td>
			            <td><INPUT class=common VALUE="理赔摊回查询" TYPE=button onclick="GetQueryClick();"> </td>
						
				        <td><INPUT class=common VALUE="返回" TYPE=button onclick="GoBack();"> </td>
				        </tr>
  		</table>		
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
	var strSQL = "";
	 strSQL = "select LRPol.ReinsurItem,LRPol.PolNo,LRPol.InsuredName,LRPol.RiskCode,LRPol.Prem ,LRPol.RiskAmnt ,LRPol.CValiDate,LRPol.InsuredYear from LRPol where PolNo='" + tPolNo + "' order by LRPol.CessStart";			 
	//查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
  	PolGrid.clearData();
  	myAlert("数据库中没有满足条件的数据！");
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


