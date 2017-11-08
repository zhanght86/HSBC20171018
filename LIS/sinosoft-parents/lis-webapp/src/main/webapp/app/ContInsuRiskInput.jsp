    <%
//程序名称：TempFeeInput.jsp
//程序功能：财务收费的输入
//创建日期：2002-07-12 
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html> 
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine3.css" rel=stylesheet type=text/css>
	<SCRIPT src="ContInsuRiskInput.js"></SCRIPT>
<%@include file="ContInsuRiskInit.jsp"%>
  
</head>
<body  onload="initForm();" >
<form action="./ProposalSave.jsp" method=post name=fm id=fm target="fraSubmit">
<br /><br />
<DIV id=DivRiskHidden class=maxbox1 STYLE="display: ">
<TABLE class=common>

  <TR CLASS=common>
    <TD CLASS=title>
      投保单号码 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class="readonly wid" readonly NAME=ContNo id=ContNo>
    </TD>
    <TD CLASS=title>
      管理机构 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class="readonly wid" readonly NAME=AgentCom id=AgentCom>
    </TD>
    <TD CLASS=title>
      被保人
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class="readonly wid" readonly NAME=Handler id=Handler>
    </TD>
  </TR>

</TABLE>

 

<DIV id=DivLCInsuredPolButton STYLE="display: ">
<TABLE >
<tr>
<td class=common>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCInsuredPol);">
</td>
<td class= titleImg>
查询被保人险种信息
 </TD>
  </TR>
  
</TABLE>
</DIV>

<Div  id= "DivLCInsuredPol" style= "display:  ">
  <!--录入的暂交费表部分 -->
    <Table  class= common>
      	<tr>
    	 <td style="text-align: left" colSpan=1>
	 <span id="spanLCInsuredPolGrid" >
	 </span> 
	</td>
       </tr>
    </Table>
    <table>
    <TR class=common> 
    <TD   class=common>
      <input type =button class=cssButton value="上 一 步" onclick="returnparent();">   
           </TD>
     <TD   class=common>
      <input type =button class=cssButton value="修改被保人险种信息" onclick="InputPolicy();">      
     </TD>
     <TD   class=common>
      <input type =button class=cssButton value="删除被保人险种信息" onclick="deleteRecord();">      
     </TD>
     
    </TR> 
    </table>
    </div>
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>




