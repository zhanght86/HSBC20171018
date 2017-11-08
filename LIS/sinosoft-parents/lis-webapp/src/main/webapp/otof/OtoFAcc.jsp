<%
//程序名称：OtoFAcc.jsp
//程序功能：万能险账户信息
//创建日期：2008-01-02 
//创建人  ：zy
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
    GlobalInput tG1 = (GlobalInput)session.getValue("GI");
    String Branch = tG1.ComCode;
    loggerDebug("OtoFAcc","Branch:"+Branch);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>  
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT> 
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryPrint.js"></SCRIPT> 
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="OtoFAcc.js"></SCRIPT>
  <%@include file="OtoFAccInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form action="./OtoFAccSave.jsp" method=post name=fm id=fm target="fraSubmit">
    <table class=common >
    	<tr>
        <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
        <td class= titleImg>请输入查询条件：</td></tr>
		</table>
        <Div  id= "divPayPlan1" style= "display: ''" class="maxbox1">
    <table  class= common>
      	<TR  class= common>
          <TD  class= title5>起始日期</TD>
          <TD  class= input5>
          <Input class="coolDatePicker" onClick="laydate({elem: '#bDate'});" verify="有效开始日期|DATE" dateFormat="short" name=bDate id="bDate"><span class="icon"><a onClick="laydate({elem: '#bDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD  class= title5>终止日期</TD>
          <TD  class= input5>
          <Input class="coolDatePicker" onClick="laydate({elem: '#eDate'});" verify="有效开始日期|DATE" dateFormat="short" name=eDate id="eDate"><span class="icon"><a onClick="laydate({elem: '#eDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
        </TR>
       <TR  class= common>
	        <TD  class= title5>险种编码</TD>
	        <TD  class= input5><Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class=code name=RiskCode id=RiskCode ondblclick="return showCodeList('RiskCode',[this,RiskCodeName],[0,1]);" onclick="return showCodeList('RiskCode',[this,RiskCodeName],[0,1]);" onkeyup="return showCodeListKey('RiskCode',[this,RiskCodeName],[0,1]);"></TD>
          <TD  class= title5>险种名称</TD>
          <TD  class= input5><input class="wid" class=common name=RiskCodeName id=RiskCodeName readonly=true></TD>
       </TR>  
       <TR  class= common>
          <TD  class= title5>管理机构</TD>
          <TD  class= input5><Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class="code"  name=ManageCom id=ManageCom ondblclick="return showCodeList('station',[this]);" onclick="return showCodeList('station',[this]);"onkeyup="return showCodeListKey('station',[this]);"></TD>
       </TR>        
       <!--TR class= common>
        <TD  class= title>凭证序号</TD>
        <TD  class= input><Input class= common name=VouchNo value="14" readonly></TD>
        <TD  class= title>记帐日期</TD>
        <TD  class= input><Input class="coolDatePicker" dateFormat="short" name=AccountDate></TD>
	    </TR-->     
    </table>
		</Div>

    	<!--TD class= common>  <INPUT VALUE="凭证提取"  name="distill" class= common TYPE=button onclick="SubmitForm();"></TD-->
    	    	    	    	
     <!--<INPUT VALUE="打    印" class= cssButton TYPE=button onclick="easyprint();">-->
<a href="javascript:void(0);" class="button" onClick="easyprint();">打    印</a>
  	</div>
   <INPUT type= "hidden" name= "fmAction" value= ""> 
   <INPUT type= "hidden" name= "ComCode" value= ""> 
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
