<%
//�������ƣ�LDExOtherRateQueryInput.jsp
//�����ܣ�
//�������ڣ�2009-10-13 10:57:48
//������  ��ZhanPeng���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="./LDExOtherRateQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="./LDExOtherRateQueryInit.jsp"%>
  <title> </title>
</head>
<body  onload="initForm();" >
  <form  name=fm id=fm target="fraSubmit">
  <div class="maxbox">
  <table  class= common>
  <TR  class= common>
    <TD  class= title5>                                                                                                                                                                                                                                               
      �����ִ���
    </TD>
    <TD  class= input5>
      <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class='codeno' name=CurrCode id=CurrCode ondblclick="return showCodeList('currcode',[this,CurrCodeName],[0,1]);"  onclick="return showCodeList('currcode',[this,CurrCodeName],[0,1]);" 
      onkeyup="return showCodeListKey('currcode',[this,CurrCodeName],[0,1]);"  verify="�����ִ���|notnull&currcode"><input name=CurrCodeName id=CurrCodeName  class=codename elementtype=nacessary  readonly=true>
    </TD>
    <TD  class= title5>
      ������λ
    </TD>
    <TD  class= input5>
      <Input class="wid" class= common class="wid" name=Per id=Per >
    </TD>
  </TR>
  <TR  class= common>
    <TD  class= title5>
      Ŀ����ִ���
    </TD>
    <TD  class= input5>
    <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class='codeno' name=DestCurrCode id=DestCurrCode ondblclick="return showCodeList('currcode',[this,DestCodeName],[0,1]);"
     onclick="return showCodeList('currcode',[this,DestCodeName],[0,1]);" 
      onkeyup="return showCodeListKey('currcode',[this,DestCodeName],[0,1]);"  verify="Ŀ����ִ���|notnull&currcode"><input name=DestCodeName id=DestCodeName  class=codename elementtype=nacessary  readonly=true>
    </TD>
    <TD  class= title5>
      ����Ԫ��������
    </TD>
    <TD  class= input5>
      <Input class="wid" class= common class="wid" name=ExchRate id=ExchRate >
    </TD>
  </TR>
  <TR  class= common>
    <TD  class= title5>
      ��������
    </TD>
    <TD  class= input5>
      <!--<Input class="coolDatePicker" dateFormat="short" name=StartDate >-->
      <Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
    </TD>
    <TD  class= title5>
      ͣ������
    </TD>
    <TD  class= input5>
      <!--<Input class="coolDatePicker" dateFormat="short" name=EndDate >-->
      <Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
    </TD>
  </TR>
</table>
</div>
          <!--<INPUT VALUE="��  ѯ" TYPE=button class=cssButton onclick="return easyQueryClick();"> 
          <INPUT VALUE="��  ��" TYPE=button class=cssButton onclick="returnParent();"> 	-->
          <a href="javascript:void(0);" class="button" onClick="return easyQueryClick();">��    ѯ</a>
          				
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCodeGrid);">
    		</td>
    		<td class= titleImg>
    			�����ֶ���Ԫ�����ʲ�ѯ
    		</td>
    	</tr>
    </table>
  	<Div  id= "divCodeGrid" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td style=" text-align: left" colSpan=1>
  					<span id="spanCodeGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
     		<center>
     		<INPUT class = cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();">
     		<INPUT class = cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();">
     		<INPUT class = cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();">
     		<INPUT class = cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();"></center>
  								
  	</div>
    <a href="javascript:void(0);" class="button" onClick="returnParent();">��    ��</a>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
