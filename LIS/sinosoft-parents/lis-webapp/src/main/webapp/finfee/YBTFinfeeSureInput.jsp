<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//�������ƣ�YBTFinfeeSureInput.jsp
//�����ܣ��ʱ�ͨ����
//�������ڣ�
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<html>
<%
  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput)session.getValue("GI");
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="YBTFinfeeSure.js"></SCRIPT>
  <%@include file="YBTFinfeeInit.jsp"%>
  <title>�ʱ�ͨ����</title>
</head>
<body onLoad="initForm();">
  <form ENCTYPE="multipart/form-data"  method=post  name=fm id="fm" target="fraSubmit" action="./YBTFinfeeSureSave.jsp">
  <table class= common border=0 width=100%>
    <tr>
            <td class=common>
              <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,maxbox);">
            </td>
			<td class= titleImg align= center>�����뽻�ѽ��ϴ������嵥��</td>
		</tr>
	</table>
  <div class="maxbox1" id="maxbox">
  <TABLE class=common align=center>
    <TR class=common>
      <TD class=title5>
        �����ܽ��
      </TD>
      <TD class=input5>
        <input class="wid" name=FeeSum id=FeeSum><font color=red>*</font>
      </TD>
      <TD class=title5>
        �տ�����
      </TD>
      <TD class=input5>
        <input class="codeno" name=BankCode id="BankCode" style="background: url(../common/images/select--bg_03.png) no-repeat center right;" onMouseDown="checkCom();return showCodeList('FINAbank',[this,BankCodeName],[0,1],null,checkComCode,1,1);" onDblClick="checkCom();return showCodeList('FINAbank',[this,BankCodeName],[0,1],null,checkComCode,1,1);" onKeyUp="checkCom();return showCodeListKey('FINAbank',[this,BankCodeName],[0,1],null,checkComCode,1,1);" readonly>
		<input class=codename name=BankCodeName id=BankCodeName readonly><font color=red>*</font>
      </TD>
    </TR>
    <TR class=common>
      <TD class=title5>
	        �ļ���ַ��
	    </TD>
	    <TD  class=input5>
	      <Input  class = "wid1" type="file" name=FileName id="FileName">
      </TD>	 
    </TR>
  </table>
  
  <!--<table>
    <TR>
      <TD><IMG src="../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divContext);">
      </TD>
      <TD class=titleImg>���ϴ������嵥</TD>
    </TR>
  </TABLE>
  <Div id="divContext" class="maxbox" style="display:''">
    <table class=common>
    <TR class=common>
      <TD class = title>
	        �ļ���ַ��
	    </TD>
	    <TD  >
	      <Input  class = "wid1" type="file" name=FileName>
      </TD>	 
    </TR>
  </table>-->
  </Div>
  <input class=cssButton value="����Ӧ�ձ���" name=conf id=conf  type=button onClick="submitForm();">
  

  </form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
<script>
function checkCom()
{
	var Com = '<%=tG.ComCode%>' ;
	var bankCom="" ;
	if(Com.length >=4)
	{
		bankCom =Com.substr(0,4);
  }
	else
	{
		bankCom=Com;		
	}
	checkComCode="1 and comcode like #"+bankCom+"%#";
}
</script>
