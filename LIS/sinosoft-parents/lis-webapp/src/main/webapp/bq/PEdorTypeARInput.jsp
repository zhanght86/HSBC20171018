<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<% 
//�������ƣ�PEdorTypeAR.jsp
//�����ܣ��˻����
//�������ڣ�2007-05-23 16:49:22
//������  ��ck
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<html> 
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT> 
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT> 
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT> 
  <SCRIPT src="./PEdor.js"></SCRIPT>

  <SCRIPT src="./PEdorTypeAR.js"></SCRIPT>
  <%@include file="PEdorTypeARInit.jsp"%>
  
  <title>������ȡ</title> 
</head>

<body  onload="initForm();" >
  <form action="" method=post name=fm id=fm target="fraSubmit">    
    <div class=maxbox1>
    <table class=common>
      <TR  class= common> 
        <TD  class= title > ������</TD>
        <TD  class= input > 
          <input class="readonly wid" readonly name=EdorNo id=EdorNo >
        </TD>
        <TD class = title > �������� </TD>
        <TD class = input >
        <Input class=codeno  readonly name=EdorType id=EdorType><input class=codename name=EdorTypeName id=EdorTypeNamex readonly=true>
        </TD>
       
        <TD class = title > ���˱����� </TD>
        <TD class = input >
        	<input class="readonly wid" readonly name=ContNo id=ContNo>
        </TD>   
      </TR>
      <TR class=common>
    	<TD class =title>��������</TD>
    	<TD class = input>    		
    		<Input class="coolDatePicker" readonly name=EdorItemAppDate onClick="laydate({elem: '#EdorItemAppDate'});" id="EdorItemAppDate"><span class="icon"><a onClick="laydate({elem: '#EdorItemAppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
    	</TD>
    	<TD class =title>��Ч����</TD>
    	<TD class = input>
    		<Input class="coolDatePicker" readonly name=EdorValiDate onClick="laydate({elem: '#EdorValiDate'});" id="EdorValiDate"><span class="icon"><a onClick="laydate({elem: '#EdorValiDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
    	</TD>
    	<TD class = title></TD>
    	<TD class = input></TD>
    </TR>
    </TABLE> 
    </div>
        <table>
      <tr>
        <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLPInsuAcc);">
        </td>
        <td class= titleImg>
          ѡ��ת������
        </td>
      </tr>      
    </table>
    <div class=maxbox1>
    <table class=common>
    <TR  class= common> 
        <TD  class= title > �������ֺ�</TD>
        <TD  class= input > 
          <input class="common wid" name=PolNo id=PolNo >
        </TD>
        <TD class = title > �����˿ͻ��� </TD>
        <TD class = input >
        	<input class = "common wid" name=InsuredNo id=InsuredNo>
        </TD>  
        <TD class = title > ���� </TD>
        <TD class = input >
        <Input class="common wid"  name=InsuredName id=InsuredName>
        </TD> 
     </TR>
     <TR  class= common> 
      <TD  class= title > ֤������</TD>
        <TD  class= input > 
          <input class="codeno" name=IDType id=IDType style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblClick="showCodeList('IDType',[this,IDTypeName],[0,1]);" onkeyup="showCodeListKeyEx('IDType',[this,IDTypeName],[0,1]);">
          <input class=codename name=IDTypeName id=IDTypeName readonly=true>
        </TD>
        <TD class = title > ֤������ </TD>
        <TD class = input >
        	<input class = "common wid" name=IDNo id=IDNo>
        </TD>  
        <TD class = title > �Ա� </TD>
        <TD class = input >
        <Input class="codeno"  name=Sex id=Sex style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblClick="showCodeList('Sex',[this,SexName],[0,1]);" onkeyup="showCodeListKeyEx('Sex',[this,SexName],[0,1]);">
        <input class=codename name=SexName id=SexName readonly=true>
        </TD> 
     </TR>
     <TR  class= common> 
     <TD  class= title > ��������</TD>
        <TD  class= input > 
          <input name=Birthday class="coolDatePicker" dateFormat="short" onClick="laydate({elem: '#Birthday'});" id="Birthday"><span class="icon"><a onClick="laydate({elem: '#Birthday'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </TD>
		<TD class = title ></TD>
        <TD class = input ></TD>
		<TD class = title ></TD>
        <TD class = input ></TD>
     </TR>
    </TABLE> 
	</div>
    <INPUT class=cssButton id="riskbutton" VALUE="��ѯ������Ϣ" TYPE=button onClick="queryPol();">
	<Div  id= "divLPInsuAcc" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  	<td text-align: left colSpan=1>
  					<span id="spanLCInsuAccGrid" >
  					</span> 
  			</td>
  			</tr>
    	</table>
    	 </Div>
    	<Div  id= "divPage2" align=center style= "display: none ">
        <INPUT CLASS=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage2.firstPage();"> 
        <INPUT CLASS=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage2.previousPage();"> 					
        <INPUT CLASS=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage2.nextPage();"> 
        <INPUT CLASS=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage2.lastPage();">					
  	</div>
	<br>  
	  <Input type=Button value="�����˻���ȡ" class = cssButton onclick="EdorDetail()">
	  <Input type=Button value="��  ��" class = cssButton onclick="returnParent()">
	  
		<table>
	      <tr>
	        <td class=common>
	          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divDelete);">
	        </td>
	        <td class= titleImg>
	          ��ʷ������Ϣ
	        </td>
	      </tr>      
	 </table>  
	  	<Div  id= "divLPInsuAcc" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  	<td text-align: left colSpan=1>
  					<span id="spanLPInsuAccGrid" >
  					</span> 
  			</td>
  			</tr>
    	</table>
    	 </Div>
    	<Div  id= "divPage3" align=center style= "display: none ">
        <INPUT CLASS=cssButton90 VALUE="��  ҳ" TYPE=button onclick="divPage3.firstPage();"> 
        <INPUT CLASS=cssButton91 VALUE="��һҳ" TYPE=button onclick="divPage3.previousPage();"> 					
        <INPUT CLASS=cssButton92 VALUE="��һҳ" TYPE=button onclick="divPage3.nextPage();"> 
        <INPUT CLASS=cssButton93 VALUE="β  ҳ" TYPE=button onclick="divPage3.lastPage();">					
  	</div>
	  <Input type=Button value="���׳���" class = cssButton onclick="cancelGEdor()">  
	  
    <input type=hidden id="EdorAcceptNo" name="EdorAcceptNo">
    <input type=hidden id="CustomerNo" name="CustomerNo">
    <input type=hidden id="ContNoBak" name="ContNoBak">
    <input type=hidden id="CustomerNoBak" name="CustomerNoBak">
    <input type=hidden id="ContType" name="ContType">
    <input type=hidden id="Transact" name="Transact">
    <input type=hidden id="EdorTypeCal" name="EdorTypeCal">
	  
  </form>
  
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br /><br /><br /><br />
</body>
</html>

<script>
  window.focus();
</script>
