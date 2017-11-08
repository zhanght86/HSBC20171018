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
    var tIsCancelPolFlag = "0" ;
</script>

	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="PolQuery.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="ProposalQueryInit.jsp"%>
	
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<title>投保单查询 </title>
</head>

<body  onload="initForm();easyQueryClick();" >
  <form  name=fm  id="fm">
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
          <TD  class= title>
            客户号码
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
	else if (tFlag.equals("Agent"))
		{
%>       
         <TD  class= title>
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
  </Div>
  
  <table>
        <tr>		
		<td>
         <INPUT class=cssButton VALUE="投保单明细查询" TYPE=button onClick="PolClick1();"> 	</td>
   		</tr>
  </table>
      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divProposal1);">
    		</td>
    		<td class= titleImg>
    			 投保单信息
    		</td>
    	</tr>
    </table>
  	<Div  id= "divProposal1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
<center>    	
    	<table>	
    		<tr>
	    	<td> <INPUT class=cssButton90 VALUE="首页" TYPE=button onClick="turnPage.firstPage();"> </td>
	      	<td>  <INPUT class=cssButton91 VALUE="上一页" TYPE=button onClick="turnPage.previousPage();"> 					</td>
	      	<td>  <INPUT class=cssButton92 VALUE="下一页" TYPE=button onClick="turnPage.nextPage();"> </td>
	      	<td> <INPUT class=cssButton93 VALUE="尾页" TYPE=button onClick="turnPage.lastPage();">			</td>
      	</tr>
      	
  		</table>
</center>  				
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

var sqlid1="ProposalQuerySql1";
var mySql1=new SqlClass();
mySql1.setResourceName("sys.ProposalQuerySql"); //指定使用的properties文件名
mySql1.setSqlId(sqlid1); //指定使用的Sql的id
mySql1.addSubPara(tCustomerNo); //指定传入的参数
strSQL=mySql1.getString();

	//strSQL = "select GrpContNo,ContNo,PrtNo,InsuredName,AppntName from LCCont where AppntNo='" + tCustomerNo + "' and AppFlag='0'";			 
//	alert(strSQL);
	
<%
		}
	else if (tFlag.equals("Agent"))
		{
%>	
var sqlid2="ProposalQuerySql2";
var mySql2=new SqlClass();
mySql2.setResourceName("sys.ProposalQuerySql"); //指定使用的properties文件名
mySql2.setSqlId(sqlid2); //指定使用的Sql的id
mySql2.addSubPara(tCustomerNo); //指定传入的参数
strSQL=mySql2.getString();

	//strSQL = "select GrpContNo,ContNo,PrtNo,InsuredName,AppntName from LCCont where AgentCode='" + tCustomerNo + "' and AppFlag='0'";		
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


