<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //�������ƣ�PDTestDeployInput.jsp
 //�����ܣ���Ʒ�����뷢��
 //�������ڣ�2009-3-18
 //������  ��
 //���¼�¼��  ������    ��������     ����ԭ��/����
%>

<head>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>  
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
 <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
 <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

 <script src="PDCommonJS.js"></script> 
 <SCRIPT src="PublicInfoDeploy.js"></SCRIPT>
 <%@include file="PublicInfoDeployInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();">
<form action="./PublicInfoDeploySave.jsp" method=post name=fm target="fraSubmit">
 <!--  
<table>
  <tr>
    <td class="titleImg" >��ѡ����Ҫ����������:</td>
  </tr>
</table>
-->
<div style="display:none">
<table  class= common>
   <tr>
      <td>
     <span id="spanMulline9Grid" >
     </span> 
      </td>
   </tr>
</table>
</div><!--  
<hr>

</BR></BR>
<input value="���ݷ���" type=button  onclick="" class="cssButton" type="button" >
<br><br>
-->
<div>
<table>
  <tr>
    <td class="titleImg" >�����ѯ����:</td>
  </tr>
</table>
<table  class= common>
	<tr class= common>
    	 <TD  class= title>���ֱ���</TD>
        <TD  class= input>
            <Input class=common name=RiskCode verify="���ֱ���|LEN<=8"  >
        </TD> 
        <TD  class= title>�������</TD>
        <TD  class= input>
						<Input class=common name=UWCODE verify="�������|LEN<=10" >
        </TD> 
        <TD  class= title>
������������
        </TD>
        <TD  class= input>
           <Input class="codeno" name=RELAPOLTYPE readonly="readonly"  ondblclick="return showCodeList('pd_relapoltype',[this,RELAPOLTYPEName],[0,1],null,'EDOR','ZT',1);" onkeyup="return showCodeListKey('pd_relapoltype',[this,RELAPOLTYPEName],[0,1],null,'EDOR','ZT',1);"><input class=codename name=RELAPOLTYPEName readonly="readonly">
        </TD>
        </tr>
       <tr class= common>
          <TD  class= title>
�˱�����
        </TD>
        <TD  class= input>
           <Input class="codeno" name=UWTYPE readonly="readonly"   ondblclick="return showCodeList('pd_uwtype',[this,UWTYPEName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('pd_uwtype',[this,UWTYPEName],[0,1],null,null,null,1);"><input class=codename name=UWTYPEName readonly="readonly">

        </TD>
        <TD  class= title>
ҵ��ģ��
        </TD>
        <TD  class= input>
          <input class="codeno" name=STANDBYFLAG2  ondblclick="return showCodeList('ibrmsbusinessrule',[this,STANDBYFLAG2NAME],[0,1]);" onkeyup="return showCodeListKey('ibrmsbusinessrule',[this,STANDBYFLAG2NAME],[0,1]);"><input class="codename" name=STANDBYFLAG2NAME readonly="readonly">        
        </TD>
        <TD  class= title>
����ϵͳ
        </TD>
        <TD  class= input>
          <input class="codeno" name=STANDBYFLAG1  ondblclick="return showCodeList('pd_systype',[this,STANDBYFLAG1NAME],[0,1]);" onkeyup="return showCodeListKey('pd_systype',[this,STANDBYFLAG1NAME],[0,1]);"><input class="codename" name=STANDBYFLAG1NAME readonly="readonly">        
        </TD>
         </tr>

</table>
<br>
<input value="��  ѯ" type=button  onclick="queryrule()" class="cssButton" type="button" >
<input value="��  ��" type=reset  class="cssButton" type="button" >
<br><br>
<table>
  <tr>
    <td class="titleImg" >�����ѯ���:</td>
  </tr>
</table>

<div>
<table  class= common>
   <tr>
      <td>
     <span id="spanMulline8Grid" >
     </span> 
      </td>
   </tr>
</table>
</div>
<INPUT CLASS=cssbutton VALUE="��  ҳ" TYPE=button onclick="Mulline8GridTurnPage.firstPage();"> 
<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button onclick="Mulline8GridTurnPage.previousPage();">      
<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button onclick="Mulline8GridTurnPage.nextPage();"> 
<INPUT CLASS=cssbutton VALUE="β  ҳ" TYPE=button onclick="Mulline8GridTurnPage.lastPage();">
<hr>
<br> 
<table>
  <tr>
    <td class="titleImg" >ѡ�񷢲���ƽ̨</td>
  </tr>
</table>
<div>
<table  class= common>
   <tr>
      <td>
     <span id="spanMulline10Grid" >
     </span> 
      </td>
   </tr>
</table>
</div>
<input value="ѡ�������ݷ���" type=button  onclick="uwDeploy();" class="cssButton" type="button" >
<input value="���������ݷ���" type=button  onclick="uwDeploySelect();" class="cssButton" type="button" >
<br><br>

</div>
<input type=hidden name="operator">
<input type=hidden name=RuleType>
<input type=hidden name='ReleasePlatform'>
<input type=hidden name='SysType'>
<input type=hidden name='EnvType'>
<input type=hidden name='SQL'>
<input type=hidden name='IsDeleteCoreDataBeforeInsert' value='1'>
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
