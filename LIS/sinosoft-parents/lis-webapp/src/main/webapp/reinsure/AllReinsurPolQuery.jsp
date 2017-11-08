<%@include file="/i18n/language.jsp"%>

<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
   GlobalInput tG = new GlobalInput();
   tG=(GlobalInput)session.getAttribute("GI"); //添加页面控件的初始化。
   System.out.println("管理机构-----"+tG.ComCode);
   
   	String tDisplay = "";
	try
	{
		tDisplay = request.getParameter("display");
	}
	catch( Exception e )
	{
		tDisplay = "";
	}
%>   

<script>
  var comCode = <%=tG.ComCode%>
</script>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT><SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT><SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryPrint.js"></SCRIPT>
  <SCRIPT src="AllReinsurPolQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css><LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="AllReinsurPolQueryInit.jsp"%>
  
  <title>分保保单查询</title>
</head>
<body  onload="initForm();" >
  <form method=post name=fm target="fraSubmit">
    <!-- 保单信息部分 -->
    <table class= common border=0 width=100%>
    	<tr>
			<td class= titleImg align= center>请输入分保保单查询条件：</td>
		</tr>
	</table>
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title5>保单号码</TD>
          <TD  class= input5> <Input class= common name=PolNo > </TD>
          <TD  class= title5>集体保单号码</TD>
          <TD  class= input5> <Input class= "code" name=GrpPolNo CodeData="0|^00000000000000000000|非集体单下的个人单" ondblclick="showCodeListEx('GrpPolNo',[this],[0]);" onkeyup="showCodeListKeyEx('GrpPolNo',[this],[0]);"> </TD>
          <TD  class= title5>险种编码</TD>
          <TD  class= input5> <Input class="code" name=RiskCode ondblclick="return showCodeList('RiskCode',[this]);" onkeyup="return showCodeListKey('RiskCode',[this]);"></TD>

        </TR>
        <TR  class= common>
          <TD  class= title5>分保日期</TD>
          <TD  class= input5> <Input class= common name=CessStart ></TD>
          <TD  class= title5>再保项目</TD>
          <TD  class= input5> <Input class= code name=ReinsurItem CodeData="0|^L|法定分保|^C|商业分保" ondblclick="showCodeListEx('ReinsurItem',[this],[0]);" onkeyup="showCodeListKeyEx('ReinsurItem',[this],[0]);"> </TD>
          <TD  class= title5>协议类型</TD>
          <TD  class= input5> <Input class= code name=ProtItem CodeData="0|^T|合同分保|^F|临时分保" ondblclick="showCodeListEx('ProtItem',[this],[0]);" onkeyup="showCodeListKeyEx('ProtItem',[this],[0]);"> </TD>
        </TR>
       <TR  class= common>
          <TD  class= title5>被保人姓名</TD>
          <TD  class= input5><Input class= common name=InsuredName ></TD>
          <TD  class= title5>被保人客户号</TD>
          <TD  class= input5> <Input class= common name=InsuredNo > </TD>
          <TD  class= title5>被保人出生日期</TD>
          <TD  class= input5><Input class= common name=InsuredBirthday ></TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>保单年度</TD>
          <TD  class= input5><Input class= common name=InsuredYear ></TD>
        </TR>
    </table>
          <INPUT VALUE="查询"             class = common TYPE=button onclick="easyQueryClick();"> 
          <!--INPUT VALUE="保单明细" TYPE=button onclick="getQueryDetail();"--> 					
          <INPUT VALUE="保单保全理赔信息" class = common TYPE=button onclick="PolClick();">
          <INPUT VALUE="返回" name=Return class = common TYPE=button STYLE="display:none" onclick="returnParent();"> 					
          <INPUT VALUE="打印"             class = common TYPE=button onclick="easyQueryPrint(2,'PolGrid','turnPage');">
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
  	<Div  id= "divLCPol1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td style="text-align:left;" colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>

      <INPUT VALUE="首页" class = common TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT VALUE="上一页" class = common TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT VALUE="下一页" class = common TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT VALUE="尾页" class = common TYPE=button onclick="turnPage.lastPage();">				
  	</div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
