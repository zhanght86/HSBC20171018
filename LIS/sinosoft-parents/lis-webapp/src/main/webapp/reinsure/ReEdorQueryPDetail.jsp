<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
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
	String tPolNo = "";
	String tReinsurItem="";
	String tInsuredYear="";
	tPolNo = request.getParameter("PolNo");
	tReinsurItem = request.getParameter("ReinsurItem");
	tInsuredYear = request.getParameter("InsuredYear");
	System.out.println("tPolNo:" + request.getParameter("PolNo"));
  
%>
<head >

	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT><SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT><SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<!--SCRIPT src="AllFeeQueryPDetail.js"></SCRIPT-->
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css><LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="ReEdorQueryPDetailInit.jsp"%>
	
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<title>交费明细查询</title>
</head>

<body  onload="initForm();easyQueryClick();" >
  <form  name=fm >
  
  <Div  id= "divLJAPPersonHidden" style= "display:none;">
  
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
	<Div  id= "divLJAPay1" style= "display: ''">
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title5>
保单号码
          </TD>
          <TD  class= input5>
            <Input class= common name=PolNo >
          </TD>
        </TR>
      	<TR  class= common>
          <TD  class= title5>
集体单号码
          </TD>
          <TD  class= input5>
            <Input class= common name=GrpPolNo >
          </TD>
          <TD  class= title5>
总单/合同单号码
          </TD>
          <TD  class= input5>
            <Input class= common name=ContNo >
          </TD>
        </TR>
  </table>
  </Div>
  
  
    <TR  class= common>
          <TD  class= title5>
交费收据号
          </TD>
          <TD  class= input5>
            <Input class= common name=PayNo >
          </TD>
          <TD  class= title5>
总实交金额
          </TD>
          <TD  class= input5>
            <Input class= common name=SumActuPayMoney >
          </TD>
        </TR>
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
  	<Div  id= "divLJAPPerson1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td style="text-align:left;" colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	<br>
      <INPUT VALUE="首页" class= common TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT VALUE="上一页" class= common TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT VALUE="下一页" class= common TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT VALUE="尾页" class= common TYPE=button onclick="turnPage.lastPage();">				
  	</div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
<script>
var turnPage = new turnPageClass(); 
function easyQueryClick()
{
	var tPolNo = "<%=tPolNo%>";
	var tReinsurItem = "<%=tReinsurItem%>";
	var tInsuredYear = "<%=tInsuredYear%>";
	//alert("here");
	// 初始化表格
	initPolGrid();
	
	// 书写SQL语句
	var strSQL = "";

	strSQL = "select polno,edortype,edorno,ChgCessAmt,ShRePrem,ShReComm from LREdorMain where PolNo='" + tPolNo + "' and ReinsurItem='"+tReinsurItem+"' and InsuredYear='"+tInsuredYear+"' ";		
	
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
//  var tGrpPolNo=PolGrid. getRowColData(0,8);
//	var tContNo=PolGrid. getRowColData(0,9);
//	var tPolNo = PolGrid. getRowColData(0,7);
//  fm.GrpPolNo.value = tGrpPolNo;
//	fm.ContNo.value = tContNo;
//	fm.PolNo.value = tPolNo;
}
</script>
</html>


