<%
//**************************************************************************************************
//Name：LPStateQueryInput.jsp
//Function：赔案状态查询页面
//Author： wangjm
//Date: 2006-4-7
//Desc: 
//**************************************************************************************************
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
  GlobalInput tGI = new GlobalInput();
  tGI = (GlobalInput)session.getValue("GI");
%>
<head>
<script>
    //个人单的查询条件
var operator = "<%=tGI.Operator%>";   //记录操作员
var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
</script>
<title>理赔状态查询</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
   <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <script src="LLStateQuery.js"></script>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="LLStateQueryInit.jsp"%>
</head>
<body  onload="initForm();">
<!--登录画面表格-->
 <form  name=fm id=fm target=fraSubmit method=post action="LPStateQueryInput.jsp" >

    <table class= common border=0 width=100%>
    <tr>
    <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
    <td class= titleImg>请输入查询条件：</td>
    </tr>
    </table>
<Div  id= "divPayPlan1" style= "display: ''" class="maxbox">
    <table  class= common>
       <TR  class= common>
       <TD  class= title5>  立案号 </TD>
       <TD  class= input5>
       <Input class="common wid"  name=ClmNo  id=ClmNo ></font>
       </TD>    
       <TD  class= title5>  团体保单号 </TD>
       <TD  class= input5>  <Input class="wid" class= common name=GrpContNo id=GrpContNo > </TD></Tr>
       <TR  class= common>
       <TD  class= title5>  客户号 </TD>
       <TD  class= input5>  <Input class="wid" class= common name=CustomerNo id=CustomerNo > </TD>
       <TD  class= title5>  客户姓名 </TD>
       <TD  class= input5> <Input class="wid" class= common name=CustomerName id=CustomerName >  </TD>
       </TR>           
       <TR  class= common>
       
      <TD class=title5>管理机构</TD>
      <TD  class= input5 ><Input class="wid" class="code" name=ManageCom id=ManageCom  readonly >
  
 <!--      <TD class=title>管理机构</TD>
      <TD  class= input ><Input class="code" name=ManageCom ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);">
  -->   
      <TD  class= title5>出险日期 </TD>
      <TD  class= input5><!--<Input class="coolDatePicker"  dateFormat="short" name=RiskDate >-->
      <Input class="coolDatePicker" onClick="laydate({elem: '#RiskDate'});" verify="有效开始日期|DATE" dateFormat="short" name=RiskDate id="RiskDate"><span class="icon"><a onClick="laydate({elem: '#RiskDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
      </TD>
     </TR> 
     <TR  class= common>
      <TD  class= title5>赔案状态 </TD>
      <TD  class= input5>
      	<select name="clmstate">
			      <OPTION SELECTED  value="0">==请选类型==</OPTION>	      
			      <OPTION  value="10">报案</OPTION>
			      <OPTION  value="20">立案</OPTION>
			      <OPTION  value="30">审核</OPTION>
			      <OPTION  value="40">审批</OPTION>
			      <OPTION  value="11">结案</OPTION>
			    </select></TD>
     </TR>      

   </Table> </div>
   <!--<INPUT class=cssButton VALUE="查  询" TYPE=button onclick="easyQueryClick();">--> 
   <a href="javascript:void(0);" class="button" onClick="easyQueryClick();">查    询</a>
    <Table>
    <TR>
    <TD class=common>
     <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLDPerson1);">
     </TD>
      <TD class= titleImg>
        理赔信息
      </TD>
     </TR>
    </Table>    
 <Div  id= "divLDPerson1" style= "display: ''">
   <Table  class= common>
       <TR  class= common>
        <TD text-align: left colSpan=1>
            <span id="spanClaimGrid" ></span> 
  </TD>
      </TR>
    </Table>
<center>    
   <table> 
    <tr>
    <td>
      <INPUT class=cssButton90 VALUE="首  页" TYPE=button onclick="getFirstPage();"> 
    </td>
    <td>
      <INPUT class=cssButton91 VALUE="上一页" TYPE=button onclick="getPreviousPage();"> 
    </td>
    <td>  
      <INPUT class=cssButton92 VALUE="下一页" TYPE=button onclick="getNextPage();"> 
    </td>
    <td>  
      <INPUT class=cssButton93 VALUE="尾  页" TYPE=button onclick="getLastPage();"> 
    </td>
    </tr> 
    
   </table> 
</center>     

 </Div>
  
  <table>
      <tr>
       <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLDPerson2);">
        </td>
        <td class= titleImg >
        理赔状态信息
    </td>
    </tr>
    </table>

 <Div  id= "divLDPerson2" style= "display: ''">
   <Table  class= common>
       <TR  class= common>
        <TD text-align: left colSpan=1>
            <span id="spanClaimStateGrid" ></span> 
     </TD>
      </TR>
  </Table>
 
 </Div>	
 <br>
<!--<INPUT class=cssButton VALUE="报案信息查询" TYPE=button onclick="ReportQueryClick();">
<INPUT class=cssButton VALUE="立案信息查询" TYPE=button onclick="RegisterQueryClick();">-->
<a href="javascript:void(0);" class="button" onClick="ReportQueryClick();">报案信息查询</a>
<a href="javascript:void(0);" class="button" onClick="RegisterQueryClick();">立案信息查询</a>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</Form>
</body>
</html>
