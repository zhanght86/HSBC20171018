<%
//�������ƣ�SysCertTakeBackQueryInput.jsp
//�����ܣ�
//�������ڣ�2002-10-14 10:20:49
//������  ��kevin
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="./GrpSysCertTakeBackQuery.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="./GrpSysCertTakeBackQueryInit.jsp"%>
<title>ϵͳ��֤�� </title>
</head>
<body  onload="initForm();" >
  <form action="./SysCertTakeBackQuerySubmit.jsp" method=post name=fm id=fm target="fraSubmit">

    <!-- ��֤����;������� -->
    <div class="maxbox" style="margin-top:4px;">
    <table class="common">
    	<tr class="common">
    		<td class="title5" width="25%">��֤����</td>
           <td class="input5">
           	<input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name="CertifyCode" id="CertifyCode" CodeData="0|^9994|���屣��" value='9994' ondblclick="return showCodeListEx('SysCertCode', [this,CertifyCodeName],[0,1],null,null,null,1)" onMouseDown="return showCodeListEx('SysCertCode', [this,CertifyCodeName],[0,1],null,null,null,1)" onkeyup="return showCodeListKeyEx('SysCertCode', [this,CertifyCodeName],[0,1],null,null,null,1)"><input class=codename name=CertifyCodeName id=CertifyCodeName readonly=true value='���˼���������'>
           </td>
        </tr>
        <tr class="common">
        <td class="title5" width="25%">��֤����</td>
        <td class="input5" width="25%"><input class="wid" class="common" name="CertifyNo" id="CertifyNo"></td>

        <td class="title5" width="25%">��Ч����</td>
        <td class="input5" width="25%"><!--<input class="coolDatePicker" dateFormat="short" name="ValidDate">-->
        <Input class="coolDatePicker" onClick="laydate({elem: '#ValidDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=ValidDate id="ValidDate"><span class="icon"><a onClick="laydate({elem: '#ValidDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </td></tr>

      <tr class="common">
        <td class="title5">���Ż���</td>
        <td class="input5"><input class="wid" class="common" name="SendOutCom" id="SendOutCom"></td>

        <td class="title5">���ջ���</td>
        <td class="input5"><input class="wid" class="common" name="ReceiveCom" id="ReceiveCom"></td></tr>

      <tr class="common">
        <td class="title5">������</td>
        <td class="input5"><input class="wid" class="common" name="Handler" id="Handler"></td>

        <td class="title5">��������</td>
        <td class="input5"><!--<input class="coolDatePicker" dateFormat="short" name="HandleDate">-->
        <Input class="coolDatePicker" onClick="laydate({elem: '#HandleDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=HandleDate id="HandleDate"><span class="icon"><a onClick="laydate({elem: '#HandleDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </td></tr>
      
    </table>

    </table>
          <!--INPUT VALUE="��ѯ" class="common" TYPE=button onclick="submitForm();return false;"--> 
          <!--<INPUT VALUE="��  ѯ" class="cssButton" TYPE=button onclick="QueryClick();">--> 
          <a href="javascript:void(0);" class="button" onClick="QueryClick();">��    ѯ</a>
         </div> 					
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCardPrintGrid);">
    		</td>
    		<td class= titleImg>
    			 ��֤ӡˢ����
    		</td>
    	</tr>
    </table>
  	<Div  id= "divSysCertifyGrid" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanSysCertifyGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
        
        <!--<div align="left"><INPUT VALUE="��  ��" class="cssButton" TYPE=button onclick="returnParent();"> </div>-->
        
       <center> 
      <INPUT VALUE="��  ҳ" class="cssButton90" TYPE="button" onclick="turnPage.firstPage();"> 
      <INPUT VALUE="��һҳ" class="cssButton91" TYPE="button" onclick="turnPage.previousPage();"> 					
      <INPUT VALUE="��һҳ" class="cssButton92" TYPE="button" onclick="turnPage.nextPage();"> 
      <INPUT VALUE="β  ҳ" class="cssButton93" TYPE="button" onclick="turnPage.lastPage();"> </center>					

      <input type="hidden" name="sql_where">
  	</div><a href="javascript:void(0);" class="button" onClick="returnParent();">��    ��</a>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
