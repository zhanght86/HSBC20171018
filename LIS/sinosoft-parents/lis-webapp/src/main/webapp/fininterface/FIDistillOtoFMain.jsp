<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>    
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="FIDistillOtoF.js"></SCRIPT>
  <%@include file="FIDistillOtoFInit.jsp"%>
  <title>����ӿ���ȡ����</title>
</head>
<body onload="initForm();initElementtype();">
  <form action= "./FIDistillOtoF.jsp" method=post name=fm id=fm target="fraSubmit">  	
      <table class= common border=0 width=100%>
        <tr>
        	<td class=common><IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,dvS);"></td>
            <td class= titleImg align= center>���������ڣ�</td>
        </tr>
	</table>
    <Div id="dvS" style="display: ''"><div class=" maxbox1">
      <table  class= common align=center>
      	
      	<TR  class= common>
          <TD  class= title5>
            ��ʼ����
          </TD>
          <TD  class= input5>
            <!--<Input class="coolDatePicker" dateFormat="short"  elementtype = nacessary  name=Bdate  >-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#Bdate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=Bdate id="Bdate"><span class="icon"><a onClick="laydate({elem: '#Bdate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD  class= title5>
            ��ֹ����
          </TD>
          <TD  class= input5>
            <!--<Input class="coolDatePicker" dateFormat="short"   elementtype = nacessary name=Edate >-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#Edate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=Edate id="Edate"><span class="icon"><a onClick="laydate({elem: '#Edate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
        </TR>
        
        <TR  class= common>
	  <TD  class= title5>ƾ֤���ͱ���</TD>     
	  <TD  class= input5 >
	     <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=CertificateId  ondblclick=" showCodeList('CertificateId',[this,CertificateIdName],[0,1]);" onMouseDown="showCodeList('CertificateId',[this,CertificateIdName],[0,1]);" onkeyup="return showCodeList('CertificateId',[this,CertificateIdName],[0,1]);" ><input class=codename name=CertificateIdName readonly=true>
             (��ѡ��Ϊȫ������)
	  </TD>          		 
        </TR>               

	<TR  class= common>
            <TD>
             
            </TD>
        </TR>
    </table>
    </div>
    </div>
    <!--<INPUT  class=cssButton VALUE="�� ȡ �� ��" TYPE=Button onclick="SubmitForm();">-->
    <a href="javascript:void(0);" class="button" onClick="SubmitForm();">��ȡ����</a><br><br>
 <div class="maxbox1">
  
     <table  class= common align=center>
        <TR  class= common>
          <TD  class= title5>
            ��ʼ����
          </TD>
          <TD  class= input5>
            <!--<Input class="coolDatePicker" dateFormat="short"   name=Bdate2  >-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#Bdate2'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=Bdate2 id="Bdate2"><span class="icon"><a onClick="laydate({elem: '#Bdate2'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD  class= title5>
            ��ֹ����
          </TD>
          <TD  class= input5>
            <!--<Input class="coolDatePicker" dateFormat="short"   name=Edate2 >-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#Edate2'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=Edate2 id="Edate2"><span class="icon"><a onClick="laydate({elem: '#Edate2'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
        </TR>
    </table>
    </div>
    <!--<INPUT  class=cssButton VALUE="��������־��ѯ" TYPE=Button onclick="easyQueryClick();">-->
    <a href="javascript:void(0);" class="button" onClick="easyQueryClick();">��������־��ѯ</a><br><br>
 

      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  				<span id="spanRBResultGrid" >
  				</span> 
  			</td>
  		</tr>
    	</table>
    	<p></p>
        <div align="center">
      <INPUT VALUE="��  ҳ" class =  cssButton90 TYPE=button onclick="getFirstPage();"> 
      <INPUT VALUE="��һҳ" class = cssButton91 TYPE=button onclick="getPreviousPage();"> 					
      <INPUT VALUE="��һҳ" class = cssButton92 TYPE=button onclick="getNextPage();"> 
      <INPUT VALUE="β  ҳ" class =  cssButton93 TYPE=button onclick="getLastPage();"> 					
		</div>
  	
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
<br><br><br><br>
</body>

</html>


