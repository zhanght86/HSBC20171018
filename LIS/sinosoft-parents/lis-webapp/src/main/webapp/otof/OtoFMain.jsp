<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//��½����
GlobalInput tGlobalInput = new GlobalInput();
tGlobalInput = (GlobalInput)session.getValue("GI");
%>
<script>
  var comCode = "<%=tGlobalInput.ComCode%>";
</script>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="OtoF.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>

  <title>����ӿ�</title>
</head>
<body>
  <form method=post name=fm id=fm action= "./OtoF.jsp" target="fraSubmit">
    <table class= common border=0 width=100%>
    	<tr>
        <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
			<td class= titleImg>����¼�����ڣ�</td>
		</tr>
	</table>
    <Div  id= "divPayPlan1" style= "display: ''" class="maxbox1">
    <table  class= common>
      	
      	<TR  class= common>
          <TD  class= title5>
            ��ʼ����
            <font size=1 color='#ff0000'><b>*</b></font>
          </TD>
          <TD  class= input5>
            
            <Input class="coolDatePicker" onClick="laydate({elem: '#Bdate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=Bdate id="Bdate"><span class="icon"><a onClick="laydate({elem: '#Bdate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD  class= title5>
            ��ֹ����
            <font size=1 color='#ff0000'><b>*</b></font>
          </TD>
          <TD  class= input5>
           <Input class="coolDatePicker" onClick="laydate({elem: '#Edate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=Edate id="Edate"><span class="icon"><a onClick="laydate({elem: '#Edate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
        </TR>
          
          <TR class= common>
          <TD  class= title5>
           ƾ֤���
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=VouchNo id=VouchNo>
          </TD>

        <TD  class= title5>
           ��������
        </TD>
        <TD  class= input5>
           
           <Input class="coolDatePicker" onClick="laydate({elem: '#AccountDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=AccountDate id="AccountDate"><span class="icon"><a onClick="laydate({elem: '#AccountDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </TD>
	    </TR>
	    <TR>
	      <TD class = title5>
	        �������
	        <font size=1 color='#ff0000'><b>*</b></font>
	      </TD>
	        <TD CLASS=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno NAME=ManageCom id=ManageCom VALUE="" MAXLENGTH=10  ondblclick="return showCodeList('comcode',[this,ManageComName],[0,1],null,codeSql,'1',null,250);" onclick="return showCodeList('comcode',[this,ManageComName],[0,1],null,codeSql,'1',null,250);" onkeyup="return showCodeListKey('comcode',[this,ManageComName],[0,1],null,codeSql,'1',null,250);" verify="�������|code:comcode&notnull" ><input class=codename name=ManageComName id=ManageComName readonly=true></TD>
	    </TR>
    </table>
   </Div>
    <input type=hidden name="Opt">
    <!--<INPUT  class=cssButton VALUE="��    ��"  name="distill" TYPE=button onclick="SubmitForm();">-->
    <a href="javascript:void(0);" name="distill" class="button" onClick="SubmitForm();">��    ��</a>


      </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
<script>
	<!--ѡ�������ֻ�ܲ�ѯ������¼�����-->
	var codeSql = "1  and comcode like #"+<%=tGlobalInput.ComCode%>+"%# ";
</script>
