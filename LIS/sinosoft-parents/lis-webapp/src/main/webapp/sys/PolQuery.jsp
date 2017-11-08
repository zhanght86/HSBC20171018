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
	String tManageCom = "";
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
	//添加页面控件的初始化。
	GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
  if(tG == null) {
    out.println("session has expired");
    return;
  }
  tManageCom = tG.ManageCom;
  
	
	//String strOperator = globalInput.Operator;
	//loggerDebug("PolQuery","1:"+strOperator);
%>
  
<head >
<script> 
	var tCustomerNo = "<%=tCustomerNo%>";
	var tName = "<%=tName%>";
	var tManageCom = "<%=tManageCom%>";
	var tFlag = "<%=tFlag%>";
	var tIsCancelPolFlag = "0" ;
	
</script>

	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="PolQuery.js"></SCRIPT>
	<SCRIPT src="AllProposalQuery.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="PolQueryInit.jsp"%>
	
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<title>保单查询 </title>
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
	if (tFlag.equals("Customer")||tFlag.equals("Customer2"))
		{
%>
          <TD  class= title5>
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
	if (tFlag.equals("Customer")||tFlag.equals("Customer2"))
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
		<td> 
        <INPUT class=cssButton VALUE="保单明细查询" TYPE=button onClick="PolClick1();"> 	</td>
   		</tr>
  </table>
  </Div>
  
  
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
      	  		<td style=" text-align: left" colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
<center>    	
      <INPUT VALUE="首  页" class=cssButton90 TYPE=button onClick="turnPage.firstPage();"> 
      <INPUT VALUE="上一页" class=cssButton91  TYPE=button onClick="turnPage.previousPage();"> 					
      <INPUT VALUE="下一页" class=cssButton92  TYPE=button onClick="turnPage.nextPage();"> 
      <INPUT VALUE="尾  页" class=cssButton93 TYPE=button onClick="turnPage.lastPage();">				
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

var sqlid7="ProposalQuerySql7";
var mySql7=new SqlClass();
mySql7.setResourceName("sys.ProposalQuerySql"); //指定使用的properties文件名
mySql7.setSqlId(sqlid7); //指定使用的Sql的id

mySql7.addSubPara(tCustomerNo); //指定传入的参数
mySql7.addSubPara(tManageCom); //指定传入的参数
mySql7.addSubPara(tCustomerNo); //指定传入的参数
mySql7.addSubPara(tManageCom); //指定传入的参数

mySql7.addSubPara(tCustomerNo); //指定传入的参数
mySql7.addSubPara(tManageCom); //指定传入的参数
mySql7.addSubPara(tCustomerNo); //指定传入的参数
strSQL=mySql7.getString();

	//strSQL = "select GrpContNo,ContNo,PrtNo,InsuredName,AppntName,'被保险人'  from LCCont where InsuredNo='" + tCustomerNo + "' and AppFlag='1' and ManageCom like '" + tManageCom + "%%'"
	 //         + "union select GrpContNo,ContNo,PrtNo,InsuredName,AppntName,'投保人' from LCCont where AppntNo='" + tCustomerNo +"' and AppFlag='1' and ManageCom like '" + tManageCom + "%%'"
	 //         + "union select GrpContNo,ContNo,PrtNo,InsuredName,AppntName,'连带被保险人' from LCCont where InsuredNo in(select MainCustomerNo from LCInsuredRelated where CustomerNo = '" + tCustomerNo + "' and AppFlag='1' and ManageCom like '" + tManageCom + "%%') and AppFlag='1' "
	 //         + "union select GrpContNo,ContNo,PrtNo,InsuredName,AppntName,'受益人' from LCCont where InsuredNo in(select InsuredNo from LCBnf where CustomerNo = '" + tCustomerNo + "') and AppFlag='1' ";			 
	//alert(strSQL);                                                                                                                                        
<%
		}
	else if (tFlag.equals("Customer2"))
		{
%>	

var sqlid8="ProposalQuerySql8";
var mySql8=new SqlClass();
mySql8.setResourceName("sys.ProposalQuerySql"); //指定使用的properties文件名
mySql8.setSqlId(sqlid8); //指定使用的Sql的id

mySql8.addSubPara(tCustomerNo); //指定传入的参数
mySql8.addSubPara(tManageCom); //指定传入的参数
mySql8.addSubPara(tCustomerNo); //指定传入的参数
mySql8.addSubPara(tManageCom); //指定传入的参数

mySql8.addSubPara(tCustomerNo); //指定传入的参数
mySql8.addSubPara(tManageCom); //指定传入的参数
mySql8.addSubPara(tCustomerNo); //指定传入的参数
strSQL=mySql8.getString();

	/*strSQL = "select GrpContNo,ContNo,PrtNo,InsuredName,AppntName,'被保险人' from LCCont where InsuredNo='" + tCustomerNo + "' and AppFlag='0' and ManageCom like '" + tManageCom + "%%' "
	        + "union select GrpContNo,ContNo,PrtNo,InsuredName,AppntName,'投保人' from LCCont where AppntNo='" + tCustomerNo +"' and AppFlag='0' and ManageCom like '" + tManageCom + "%%'"
	        + "union select GrpContNo,ContNo,PrtNo,InsuredName,AppntName,'连带被保险人' from LCCont where InsuredNo in(select MainCustomerNo from LCInsuredRelated where CustomerNo = '" + tCustomerNo + "' and AppFlag='0' and ManageCom like '" + tManageCom + "%%') and AppFlag='0' "
	        + "union select GrpContNo,ContNo,PrtNo,InsuredName,AppntName,'受益人' from LCCont where InsuredNo in(select InsuredNo from LCBnf where CustomerNo = '" + tCustomerNo + "') and AppFlag='0' ";
	   */     		 			 
	//alert(strSQL);
<%
		}
	else if (tFlag.equals("Agent"))
		{
%>	

var sqlid9="ProposalQuerySql9";
var mySql9=new SqlClass();
mySql9.setResourceName("sys.ProposalQuerySql"); //指定使用的properties文件名
mySql9.setSqlId(sqlid9); //指定使用的Sql的id

mySql9.addSubPara(tCustomerNo); //指定传入的参数
mySql9.addSubPara(tManageCom); //指定传入的参数

strSQL=mySql9.getString();

	//strSQL = "select GrpContNo,ContNo,PrtNo,InsuredName,AppntName from LCCont where AgentCode='" + tCustomerNo + "' and AppFlag='1' and ManageCom like '" + tManageCom + "%%' ";		
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


