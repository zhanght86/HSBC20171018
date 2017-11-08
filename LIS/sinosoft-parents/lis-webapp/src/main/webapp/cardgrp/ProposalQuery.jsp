<%
//程序名称：ProposalQuery.jsp
//程序功能：复核不通过修改
//创建日期：2002-11-23 17:06:57
//创建人  ：胡博
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html>
<%
  //个人下个人
	String tGrpPolNo = "00000000000000000000";
	
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var grpPolNo = "<%=tGrpPolNo%>";      //个人单的查询条件.
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  
  <SCRIPT src="ProposalQuery.js"></SCRIPT>
  <%@include file="ProposalQueryInit.jsp"%>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <title>保单查询 </title>
</head>
<body  onload="initForm();" >
  <form method=post name=fm target="fraTitle">
    <!-- 保单信息部分 -->
    <table class= common border=0 width=100%>
    	<tr>
			<td class= titleImg align= center>请输入查询条件：</td>
		</tr>
	</table>
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title> 投保单号码  </TD>
          <TD  class= input> <Input class= common name=ContNo > </TD>
          <!--
          <TD  class= Common> 印刷号码 </TD> 
          -->
          
          <TD  class= title> 管理机构 </TD>
          <TD  class= input> <Input class="code" name=ManageCom ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);"> </TD>          
        </TR>
        <TR  class= common>
          <TD  class= title>  投保人姓名  </TD>
          <TD  class= input> <Input class= common name=AppntName > </TD>
          <TD  class= title>  被保人姓名</TD>
          <TD  class= input> <Input class= common name=InsuredName > </TD>
          <TD  class= title> 被保人性别  </TD>
          <TD class=input>  <Input class= code name=InsuredSex verify="性别|NOTNULL" CodeData="0|^0|男^1|女^2|不详" ondblClick="showCodeListEx('SexRegister',[this],[0,1,2,3]);" onkeyup="showCodeListKeyEx('SexObjRegister',[this],[0,1,2,3]);" >  </TD>        
        </TR>
        <TR  class= common>
          <TD  class= title>  核保结论</TD>
          <td class=input>
                    <Input class=codeno name=uwState ondblclick="return showCodeList('contuwstate',[this,uwStatename],[0,1]);" onkeyup="return showCodeListKey('contuwstate',[this,uwStatename],[0,1]);"><Input class=codename name=uwStatename readonly ></td>
          <TD  class= title> 业务员编码  </TD>
          <TD  class= input>  <Input class="code" name=AgentCode ondblclick="return showCodeList('AgentCode',[this, AgentGroup], [0, 2]);" onkeyup="return showCodeListKey('AgentCode', [this, AgentGroup], [0, 2]);">  </TD>
          <TD  class= title> 业务员组别 </TD>
          <TD  class= input><Input class= common name=AgentGroup > </TD>
          <Input type= "hidden" class= common name=PrtNo >
        </TR>               
    </table>
    
          <INPUT class=cssButton VALUE="查询投保单" TYPE=button onclick="easyQueryClick();"> 
          <INPUT class=cssButton VALUE="投保单明细" TYPE=button onclick="queryDetailClick();"> 	
          <INPUT class=cssButton VALUE="扫描件查询" TYPE=button onclick="ScanQuery();">
          <INPUT class=cssButton VALUE="操作履历查询" TYPE=button onclick="OperationQuery();">
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>
    			 投保单信息
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLCPol1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	<center>
      <Div  id= "divPage" align=center style= "display: 'none' ">
      <INPUT CLASS=cssButton VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT CLASS=cssButton VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT CLASS=cssButton VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT CLASS=cssButton VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();">
      </Div> 					
  	</div>
		<input type=hidden id="GrpPolNo" name="GrpPolNo">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
