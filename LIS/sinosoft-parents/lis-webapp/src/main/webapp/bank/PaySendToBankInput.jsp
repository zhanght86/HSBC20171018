<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html> 
<%
//程序名称：PaySendToBankInput.jsp
//程序功能：
//创建日期：2002-11-18 11:10:36
//创建人  ：胡 博
//更新记录：  更新人    更新日期     更新原因/内容
%>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>

  <SCRIPT src="PaySendToBankInput.js"></SCRIPT>
  <%@include file="PaySendToBankInit.jsp"%>
  
  <title>银行代付 </title>
</head>

<%
  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput)session.getValue("GI");
%>

<script>
  comCode = "<%=tGlobalInput.ComCode%>";
</script>

<body  onload="initForm();" >
  <form action="./PaySendToBankSave.jsp" method=post name=fm   id=fm target="fraTitle">
    <%@include file="../common/jsp/InputButton.jsp"%>
    <!-- 银行代付 fraSubmit-->
    <table class= common border=0 width=100%>
    	<tr>
        <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divInvAssBuildInfo);">
            </TD>
			<td class= titleImg align= center>请选择银行，并输入代付日期区间：</td>
  		</tr>
  	</table>
  	 <div id="divInvAssBuildInfo">
       <div class="maxbox1" >
    <table  class= common>
    <TR  class= common>
      <TD  class= title5>
        银行代码
      </TD>
      <TD  class= input5>
          	 <Input class=codeno name=BankCode  id=BankCode 
             style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
            onclick="return showCodeList('bank',[this,BankName],[0,1],null,'1 and comcode like #'+comCode+'%#','1');"
             onDblClick="return showCodeList('bank',[this,BankName],[0,1],null,'1 and comcode like #'+comCode+'%#','1');" 
              onkeyup="return showCodeListKey('bank',[this,BankName],[0,1],null,'1 and comcode like #'+comCode+'%#','1');" verify="银行代码|notnull&code:bank"><input class=codename name=BankName readonly=true>
      </TD>
      </TR>
      <TR  class= common>
      <TD  class= title5>
        起始日期
      </TD>
      <TD  class= input5>
       
        <Input class="multiDatePicker laydate-icon"   onClick="laydate({elem: '#StartDate'});"dateFormat="short" name=StartDate id=StartDate verify="起始日期|date"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>

      </TD>
      <TD  class= title5>
        终止日期
      </TD>
      <TD  class= input5>
      
        <Input class="multiDatePicker laydate-icon"   onClick="laydate({elem: '#EndDate'});"dateFormat="short"  name=EndDate  id=EndDate verify="终止日期|date"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
      </TD>
    </TR>
    </table>
    </div>
    </div>
    <br>
    
  <!--  <INPUT VALUE="银行代付" class= cssButton TYPE=button onClick="submitForm()">-->
  <a href="javascript:void(0);" class="button"onClick="submitForm()">银行代付</a>
           										
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  
</body>
</html>
