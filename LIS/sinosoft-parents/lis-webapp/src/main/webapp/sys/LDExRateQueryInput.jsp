<%
//�������ƣ�LDExRateQueryInput.jsp
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
  <SCRIPT src="./LDExRateQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="./LDExRateQueryInit.jsp"%>
  <title> </title>
</head>
<body  onload="initForm();" >
  <form  name=fm id=fm target="fraSubmit">
  <div class="maxbox1">
  <table  class= common>
        <TR  class= common>
          <TD  class= title5>                                                                                                                                                                                                                                               
            ��ұ���
          </TD>
          <TD  class= input5>
            <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class='codeno' name=CurrCode id=CurrCode ondblclick="return showCodeList('currcode',[this,CurrCodeName],[0,1]);"  onclick="return showCodeList('currcode',[this,CurrCodeName],[0,1]);" 
            onkeyup="return showCodeListKey('currcode',[this,CurrCodeName],[0,1]);"  verify="��ұ��ִ���|currcode"><input name=CurrCodeName id=CurrCodeName  class=codename readonly=true>
          </TD>
          <TD  class= title5>
            ���ұ���
          </TD>
          <TD  class= input5>
          <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class='codeno' name=DestCode id=DestCode ondblclick="return showCodeList('currcode',[this,DestCodeName],[0,1]);"  onclick="return showCodeList('currcode',[this,DestCodeName],[0,1]);" 
            onkeyup="return showCodeListKey('currcode',[this,DestCodeName],[0,1]);"  verify="���ұ��ִ���|currcode"><input name=DestCodeName id=DestCodeName  class=codename  readonly=true>
          </TD>
        </TR>
      

    </table></div>
          <INPUT VALUE="��  ѯ" TYPE=button class=cssButton onclick="return easyQueryClick();"> 
          					
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCodeGrid);">
    		</td>
    		<td class= titleImg>
    			ÿ�ռ�������Ƽ۲�ѯ
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
        <table align="center">
     	<TR>	
     		<td><INPUT class = cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"></td>
     		<td><INPUT class = cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"></td>
     		<td><INPUT class = cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"></td>
     		<td><INPUT class = cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();"></td>
  		</TR>	</table>					
  	</div><INPUT VALUE="��  ��" TYPE=button class=cssButton onclick="returnParent();"> 
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
