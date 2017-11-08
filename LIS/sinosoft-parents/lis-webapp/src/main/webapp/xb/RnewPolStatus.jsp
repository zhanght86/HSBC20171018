<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//程序名称：PolStatus.jsp
//程序功能：保单状态查询
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<html>
<%
  //个人下个人


  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script type="">

	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
	var PrtNo = "<%=request.getParameter("PrtNo")%>";
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" type="" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js" type=""></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js" type=""></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js" type=""></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js" type=""></SCRIPT>
  <SCRIPT src="RnewPolStatus.js" type=""></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="RnewPolStatusInit.jsp"%>
  <title>保单状态查询 </title>
</head>
<body  onload="initForm();" >
  <form method=post name=fm id="fm" target="fraSubmit" action= "./RnewPolStatusChk.jsp">
    <!-- 保单信息部分 -->
    <table class= common border=0 width=100%>
    	<tr>
        <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
        </td>
			  <td class= titleImg align= center>请输入查询条件：</td>
		  </tr>
	  </table>
    <div class="maxbox1">
    <Div  id= "divFCDay" style= "display: ''">
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title5>保单号</TD>
          <TD  class= input5>
            <Input class= "common wid" name=ProposalContNo >
          </TD>
          <TD class= title5>险种代码</TD>
				  <TD class= input5>
					  <Input style="background:url(../common/images/select--bg_03.png)     no-repeat right center" class=codeno name=RiskCode CodeData ="" onclick="getcodedata2();return showCodeListEx('PolRisk',[this,'RiskCodeName'],[0,1],null,null,null,1);" ondblclick="getcodedata2();return showCodeListEx('PolRisk',[this,'RiskCodeName'],[0,1],null,null,null,1);" onkeyup="getcodedata2(); return showCodeListKey('PolRisk',[this,'RiskCodeName'],[0,1],null,null,null,1);"><Input class=codename name="RiskCodeName" readonly=true><font color=red>*</font>
				  </TD>
          <Input type= "hidden" class= common name=PrtNo >
        </TR>
    </table>
  </Div>
  </div>
  <a href="javascript:void(0)" class=button onclick="easyQueryClick();">查  询</a>
          <!-- <INPUT VALUE="查  询" class= cssButton TYPE=button onclick="easyQueryClick();"> -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>
    			 保单信息
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLCPol1" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td style=" text-align: left" colSpan=1>
  					<span id="spanPolGrid" >
  					</span>
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE="首  页" class= cssButton90 TYPE=button onclick="getFirstPage();">
      <INPUT VALUE="上一页" class= cssButton91 TYPE=button onclick="getPreviousPage();">
      <INPUT VALUE="下一页" class= cssButton92 TYPE=button onclick="getNextPage();">
      <INPUT VALUE="尾  页" class= cssButton93 TYPE=button onclick="getLastPage();">
  	</div>
    <div>
      <a href="javascript:void(0)" class=button onclick="getStatus();">保单状态明细</a>
    </div>
    <br>
      <!-- <INPUT VALUE="保单状态明细" class= cssButton TYPE=button onclick= "getStatus();"> -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol2);" alt="">
    		</td>
    		<td class= titleImg>
    			 保单信息
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLCPol2" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td style=" text-align: left" colSpan=1>
  				<span id="spanPolStatuGrid" >
  				</span>
  			</td>
  		</tr>
    	</table>
  </form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
