<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�CustomerCertifyQuery.jsp
//�����ܣ��ͻ���Ϣ��ѯ
//�������ڣ�2008-03-17
//������  ��zz
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryPrint.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="CustomerCertifyQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="CustomerCertifyQueryInit.jsp"%>
  <title>�ͻ���Ϣ��ѯ</title>
</head>
<body  onload="initForm();" >
  <form action="" method=post name=fm id="fm" target="fraSubmit">
    <!-- ������Ϣ���� -->
    <table class="common" border=0 width=100%>
      <tr>
        <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
        </td>
        <td class="titleImg" align="center">�������ѯ����:(�����ϵ��������ڰ�����ѯ����е����ھ�Ĭ���ǵ�ĳ���0ʱ)</td>
      </tr>
	  </table>
    <div class="maxbox1">
      <Div  id= "divFCDay" style= "display: ''">
    <table class="common" align="center" id="tbInfo" name="tbInfo">
      <tr class="common">
        <td class="title5">����</td>
        <td class="input5"><input class= "common wid" name="CertifyNo" id="CertifyNo"></td>
        
        <td class="title5"></td>
        <td class="input5"></td>
      </tr>
      <TR  class= common>
          <TD  class= title5>  �ͻ����룺 </TD>
          <TD  class= input5> <Input class= "common wid" name=CustomerNo id="CustomerNo"> </TD>
          <TD  class= title5> ������ </TD>
          <TD  class= input5> <Input class= "common wid" name=Name id="Name"> </TD>
      </tr>
      <TR  class= common>
          <TD  class= title5> �Ա�  </TD>
          <TD  class= input5> <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" name=Sex id="Sex" class="code" MAXLENGTH=1 onclick="return showCodeList('Sex',[this]);" onkeyup="return showCodeListKey('Sex',[this]);" ondblclick="return showCodeList('Sex',[this]);" onkeyup="return showCodeListKey('Sex',[this]);" > </TD>       
          <TD  class= title5> �������ڣ�  </TD>
          <TD  class= input5>
            <Input class="coolDatePicker" onClick="laydate({elem: '#Birthday'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=Birthday id="Birthday"><span class="icon"><a onClick="laydate({elem: '#Birthday'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>              </TD>
      </tr>
      <TR  class= common>
          <TD  class= title5> ֤�����ͣ�  </TD>
          <TD  class= input5> <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name=IDType id="IDType" onclick="return showCodeList('IDType',[this]);"  ondblclick="return showCodeList('IDType',[this]);">              </TD>
          <TD  class= title5> ֤�����룺  </TD>
          <TD  class= input5>  <Input class= "common wid" name=IDNo id="IDNo">   </TD>
       </TR>
    </table>
  </Div>
    </div>
    <a href="javascript:void(0)" class=button onclick="easyQueryClick();">��  ѯ</a>
    <a href="javascript:void(0)" class=button onclick="PolClick();">������ϸ</a>
    <!-- <input value="��  ѯ" type="button" class="cssButton" onclick="easyQueryClick();"> 	
    <INPUT VALUE="������ϸ" class = cssButton TYPE=button onclick="PolClick();"> -->
    <table>
      <tr>
        <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this, divCertifyInfo);"></td>
    	<td class= titleImg>�ͻ���Ϣ</td></tr>
    </table>
    
  	<div id="divCertifyInfo" style="display: ''" align=center>
      <table class="common">
        <tr class="common">
      	  <td style=" text-align: left" colSpan=1><span id="spanCardInfo"></span></td></tr>
      </table>
      
      <input VALUE=" ��ҳ " TYPE="button" class="cssButton90" onclick="turnPage.firstPage();"> 
      <input VALUE="��һҳ" TYPE="button" class="cssButton91" onclick="turnPage.previousPage();"> 					
      <input VALUE="��һҳ" TYPE="button" class="cssButton92" onclick="turnPage.nextPage();"> 
      <input VALUE=" βҳ " TYPE="button" class="cssButton93" onclick="turnPage.lastPage();"> 					
  	</div>
    <br>
    <br>
    <br>
    <br>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
