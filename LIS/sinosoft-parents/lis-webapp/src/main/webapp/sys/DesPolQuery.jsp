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
	String tCustomerNo = "";
	try
	{
		tCustomerNo = request.getParameter("CustomerNo");
	}
	catch( Exception e )
	{
		tCustomerNo = "";
	}
	String tName = "";
	try
	{
		tName = request.getParameter("Name");
		tName = new String(tName.getBytes("ISO-8859-1"), "GBK");
	}
	catch( Exception e )
	{
		tName = "";
	}
	String tFlag = "";
	try
	{
		tFlag = request.getParameter("Flag");
	}
	catch( Exception e )
	{
		tFlag = "";
	}
%>
<head >
<script> 
	var tCustomerNo = "<%=tCustomerNo%>";
	var tName = "<%=tName%>";
	var tIsCancelPolFlag = "1" ;
</script>

	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="DesPolQuery.js"></SCRIPT>
	<SCRIPT src="AllProposalQuery.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="DesPolQueryInit.jsp"%>
	
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<title>销户保单查询 </title>
</head>

<body  onload="initForm();easyQueryClick();" >
  <form  name=fm id="fm" >
  <table >
    	<tr>
    	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLDPerson1);">
    	</td>
			<td class= titleImg>
				客户信息
			</td>
		</tr>
	</table>
	<Div  id= "divLDPerson1" class="maxbox1" style= "display: ''">
    <table  class= common align=center>
      	<TR  class= common>
<% 
	if (tFlag.equals("Customer"))
		{
%>
          <TD  class= title5>
            客户号码
          </TD>
<%
		}
	else if (tFlag.equals("AppntCustomer"))
		{
%>
					<TD  class= title5>
            投保人编码
          </TD>
<%
		}
	else if (tFlag.equals("Agent"))
		{
%>
					<TD  class= title5>
            代理人编码
          </TD>
<%
		}
%>
          <TD  class= input5>
          	<Input class= "readonly wid" readonly name=CustomerNo id="CustomerNo" >
          </TD>
  <% 
	if (tFlag.equals("Customer"))
		{
  %>
          <TD  class= title5>
            客户姓名
          </TD>
   <%
		}
	else if (tFlag.equals("AppntCustomer"))
		{
%>       
         <TD  class= title5>
            投保人姓名
          </TD>
   <%
		}
	else if (tFlag.equals("Agent"))
		{
%>       
         <TD  class= title5>
            代理人姓名
          </TD>
          <%
		}
%>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=Name id="Name" >
          </TD>
        </TR>
	</table>
	<table>
        <tr>		
		<!--<td> <INPUT class=cssButton VALUE="交费查询" TYPE=button onclick="FeeQueryClick();"> </td> -->
		<!--<td> <INPUT class=cssButton VALUE="给付查询" TYPE=button onclick="GetQueryClick();"> 	</td>-->
		<!--<td> <INPUT class=cssButton VALUE="批改补退费查询" TYPE=button onclick="EdorQueryClick();"> </td>-->
		<td> 
        <INPUT class=cssButton VALUE="保单明细查询" TYPE=button onClick="PolClick_B();"> </td>
		<!--<td> <INPUT class=cssButton VALUE="扫描件查询" TYPE=button onclick="ScanQuery();"> 	</td> </tr>-->
  </table>
  </Div>

      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divDesPol1);">
    		</td>
    		<td class= titleImg>
    			 销户保单信息
    		</td>
    	</tr>
    </table>
  	<Div  id= "divDesPol1" style= "display: '';text-align:center">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT class=cssButton90 VALUE="首页" class=common TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT class=cssButton91 VALUE="上一页" class=common TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT class=cssButton92 VALUE="下一页" class=common TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT class=cssButton93 VALUE="尾页" class=common TYPE=button onclick="turnPage.lastPage();">				
  	</div>
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
<% 
	if (tFlag.equals("Customer"))
		{
%>		

var sqlid22="ProposalQuerySql22";
var mySql22=new SqlClass();
mySql22.setResourceName("sys.ProposalQuerySql"); //指定使用的properties文件名
mySql22.setSqlId(sqlid22); //指定使用的Sql的id
mySql22.addSubPara(tCustomerNo ); //指定传入的参数
strSQL=mySql22.getString();

	//strSQL = "select GrpPolNo,PolNo,PrtNo,RiskCode,InsuredName,AppntName from LBPol where InsuredNo='" + tCustomerNo + "' and mainpolno = polno ";			 
	//alert(strSQL);
<%
		}
	else if (tFlag.equals("AppntCustomer"))
		{
%>

var sqlid23="ProposalQuerySql23";
var mySql23=new SqlClass();
mySql23.setResourceName("sys.ProposalQuerySql"); //指定使用的properties文件名
mySql23.setSqlId(sqlid23); //指定使用的Sql的id
mySql23.addSubPara(tCustomerNo ); //指定传入的参数
strSQL=mySql23.getString();

	//strSQL = "select GrpPolNo,PolNo,PrtNo,RiskCode,InsuredName,AppntName from LBPol where AppntNo='" + tCustomerNo + "' and mainpolno = polno ";			 
	//alert(strSQL);
<%
		}
	else if (tFlag.equals("Agent"))
		{
%>	
var sqlid24="ProposalQuerySql24";
var mySql24=new SqlClass();
mySql24.setResourceName("sys.ProposalQuerySql"); //指定使用的properties文件名
mySql24.setSqlId(sqlid24); //指定使用的Sql的id
mySql24.addSubPara(tCustomerNo ); //指定传入的参数
strSQL=mySql24.getString();

	//strSQL = "select GrpPolNo ,PolNo,PrtNo,RiskCode,InsuredName,AppntName from LBPol where AgentCode='" + tCustomerNo + "' and mainpolno = polno";		
//	alert(strSQL);
<%
		}
%>		
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


