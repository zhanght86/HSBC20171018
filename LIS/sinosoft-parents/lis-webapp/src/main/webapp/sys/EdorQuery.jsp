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
	try
	{
		tPolNo = request.getParameter("PolNo");
	}
	catch( Exception e )
	{
		tPolNo = "";
	}
	String tRiskCode = "";
	try
	{
		tRiskCode = request.getParameter("RiskCode");		
	}
	catch( Exception e )
	{
		tRiskCode = "";
	}
		String tInsuredName = "";
	try
	{
		tInsuredName = request.getParameter("InsuredName");
		tInsuredName = new String(tInsuredName.getBytes("ISO-8859-1"), "GBK");
	}
	catch( Exception e )
	{
		tInsuredName = "";
	}
		String tAppntName = "";
	try
	{
		tAppntName = request.getParameter("AppntName");
		tAppntName = new String(tAppntName.getBytes("ISO-8859-1"), "GBK");
	}
	catch( Exception e )
	{
		tAppntName = "";
	}
%>
<head >
<script> 
	var tPolNo = "<%=tPolNo%>";
	var tRiskCode = "<%=tRiskCode%>";
	var tInsuredName = "<%=tInsuredName%>";
	var tAppntName = "<%=tAppntName%>";
</script>

	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="EdorQuery.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="EdorQueryInit.jsp"%>
	
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<title>给付查询 </title>
</head>

<body  onload="initForm();easyQueryClick();" >
  <form  name=fm id=fm>
  <table >
    	<tr>
    	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    	</td>
			<td class= titleImg>
				保单信息
			</td>
		</tr>
	</table>
	<Div  id= "divLCPol1" style= "display: ''" class=maxbox1>
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title5>
            保单号码
          </TD>
          <TD  class= input5>
          	<Input class= "readonly wid" readonly name=PolNo  id=PolNo>
          </TD>
          <TD  class= title5>
            险种编码
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=RiskCode id=RiskCode >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>
            被保人姓名
          </TD>
          <TD  class= input5>
          	<Input class= "readonly wid" readonly name=InsuredName id=InsuredName >
          </TD>
          <TD  class= title5>
            投保人姓名
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=AppntName  id=AppntName>
          </TD>
        </TR>
     </table>
  </Div>  
      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divEdor1);">
    		</td>
    		<td class= titleImg>
    			 保全缴费信息
    		</td>
    	</tr>
    </table>
  	<Div  id= "divEdor1" style= "display: ''; text-align:center">
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
function easyQueryClick()
{
	
	//alert("here");
	// 初始化表格
	initPolGrid();
	
	// 书写SQL语句
	var strSQL = "";

	var sqlid42="ProposalQuerySql42";
	var mySql42=new SqlClass();
	mySql42.setResourceName("sys.ProposalQuerySql"); //指定使用的properties文件名
	mySql42.setSqlId(sqlid42); //指定使用的Sql的id
	mySql42.addSubPara(comCode.substring(0,6) ); //指定传入的参数
	strSQL=mySql42.getString();
	
	//strSQL = "select ActuGetNo,EndorsementNo,FeeOperationType,FeeFinaType,GetDate,GetMoney,RiskCode,ManageCom,AgentCom,GrpName,PolType,GetFlag from LJAGetEndorse where PolNo='" + tPolNo + "'";			 
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
</script>
</html>


