<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<html> 
<%
//�������ƣ�WorkAchieveStatInput.jsp
//�����ܣ�������Чͳ���嵥
//�������ڣ�2005-11-29 17:20:22
//������  ��liurx
//���¼�¼��  ������    ��������      ����ԭ��/���� 
%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var managecom = "<%=tGI.ManageCom%>"; //��¼�������
	var comcode = "<%=tGI.ComCode%>"; //��¼��½����
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT> 
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="./WorkAchieveStat.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="WorkAchieveStatInit.jsp"%>
<title>������Чͳ�ƴ�ӡ </title>   
</head>
<body  onload="initForm();" >
  <form  action="./WorkAchieveStatSave.jsp" method=post name=fm id=fm target="fraSubmit">
    <!-- ��ѯ���� -->
    <table class= common border=0 width=100%>
    	<tr>
		    <td class=common>
                 <img  src= "../common/images/butExpand.gif" style= "cursor:hand;" ></img>
			</td>
			<td class=titleImg align= center>�������ѯ������</td>
	</tr>
	</table>
	<div class=maxbox1>
    <table  class=common>
       <TR>
          <TD  class="title5">�������<font color=red> *</font></TD>
          <TD  class="input5"><Input class= code name=ManageCom id=ManageCom style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('ComCode',[this]);" onkeyup="return showCodeListKey('ComCode',[this]);"></TD>
          <TD  class="title5"></TD>
          <TD  class="input5"></TD> 
       </TR>
       <TR> 
          <TD  class="title5">ͳ������<font color=red> *</font></TD>
          <TD  class="input5"><input class="coolDatePicker" dateFormat="short" name="StartDate" onClick="laydate({elem: '#StartDate'});" id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>	</TD>
          <TD  class="title5">ͳ��ֹ��<font color=red> *</font></TD>
          <TD  class="input5"><input class="coolDatePicker" dateFormat="short" name="EndDate" onClick="laydate({elem: '#EndDate'});" id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
       </TR>
       <TR  class= common>
 		  <td class="title5">�û�����</td>
		  <td class="input5">
			  <Input class="codeno" name=UsrType id=UsrType CodeData="0|^1|������^2|������^0|ȫ��" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeListEx('UsrType', [this,UsrTypeName],[0,1]);" onkeyup="return showCodeListKeyEx('UsrType', [this,UsrTypeName],[0,1]);" >
			  <input class="codename" name=UsrTypeName id=UsrTypeName readonly=true>
          </td>        
          <TD  class="title5">�û�����</TD>
		  <td class="input5" COLSPAN="1">
			  <input NAME="UsrCode" id=UsrCode VALUE="" MAXLENGTH="10" CLASS="code" verifyorder="1" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return queryUsr();" >
          </td>     
      </TR>
       <TR  class= common>
 		  <td class="title5">����״̬</td>
		  <td class="input5">
			  <Input class="codeno" name=EdorState id=EdorState CodeData="0|^1|��Ч^2|δ��Ч^0|ȫ��" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeListEx('EdorState', [this,EdorStateName],[0,1]);" onkeyup="return showCodeListKeyEx('EdorState', [this,EdorStateName],[0,1]);" >
			  <input class="codename" name=EdorStateName id=EdorStateName readonly=true>
          </td>        
          <TD  class="title5">��ȫ��Ŀ</TD>
          <TD  class="input5"><Input class= codeno name=EdorType id=EdorType style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="return showCodeListEx('EdorType', [this,EdorTypeName],[0,1],null,null,null,null,'200');" onkeyup="return showCodeListKeyEx('EdorType', [this,EdorTypeName],[0,1],null,null,null,null,'200');">
		  <input class="codename" name=EdorTypeName id=EdorTypeName readonly=true></TD>    
      </TR>
    </table>
	</div>
<br>
  	<p>
      <INPUT VALUE="��  ӡ" class= cssButton TYPE=button onclick="printBill();"> 
  	</p>
  	<input type=hidden id="fmtransact" name="fmtransact">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html> 
