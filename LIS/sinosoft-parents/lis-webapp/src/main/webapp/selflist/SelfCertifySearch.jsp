<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�SelfCertifySearch.jsp
//�����ܣ�����������ӡ�嵥
//�������ڣ�2008-03-13 
//������  ��zz
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="SelfCertifySearch.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="SelfCertifySearchInit.jsp"%>
  <title>������ӡ�嵥</title>
</head>
<body  onload="initForm();" >
  <form action="./SelfCertifySearchOut.jsp" method=post name=fm id="fm" target="fraSubmit">
    <!-- ��֤��Ϣ���� -->
    <table class="common" border=0 width=100%>
      <tr>
        <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
        </td>
        <td class="titleImg" align="center">�������ѯ������</td>
      </tr>
	  </table>
    <div class="maxbox">
      <Div  id= "divFCDay" style= "display: ''"> 
    <table class="common" align="center" id="tbInfo" name="tbInfo">
      <tr class="common">
        <td class="title5">��֤����</td>
        <td class="input5">
          <input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name="CertifyCode" id="CertifyCode"
            onclick="return showCodeList('CertifyCode', [this],[0,1],null,null,null,1);"
            ondblclick="return showCodeList('CertifyCode', [this],[0,1],null,null,null,1);" 
            onkeyup="return showCodeListKey('CertifyCode', [this],[0,1],null,null,null,1);"></td> 
            
        <td class="title5">����Ա</td>
        <td class="input5"><input class="common wid" name="Operator" id="Operator"></td>
        
      </tr>

      <tr class="common">
        <td class="title5">������</td>
        <td class="input5"><input class="common wid" name="SendOutCom" id="SendOutCom"></td>

        <td class="title5">������</td>
        <td class="input5"><input class="common wid" name="ReceiveCom" id="ReceiveCom"></td>
      </tr>
        

      <tr class="common">
        <td class="title5">������ڣ���ʼ��</td>
        <td class="input5"><Input class="coolDatePicker" onClick="laydate({elem: '#MakeDateB'});" verify="������ڣ���ʼ��|NOTNULL" dateFormat="short" name=MakeDateB id="MakeDateB"><span class="icon"><a onClick="laydate({elem: '#MakeDateB'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
        
        <td class="title5">������ڣ�������</td>
        <td class="input5"><Input class="coolDatePicker" onClick="laydate({elem: '#MakeDateE'});" verify="������ڣ�������|NOTNULL" dateFormat="short" name=MakeDateE id="MakeDateE"><span class="icon"><a onClick="laydate({elem: '#MakeDateE'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td></tr>       
      <tr class="common">
        <td class="title5">��ʼ��</td>
        <td class="input5"><input class="common wid" name="StartNo" id="StartNo"></td>
        
        <td class="title5">��ֹ��</td>
        <td class="input5"><input class="common wid" name="EndNo" id="EndNo"></td></tr>
       
     <tr class="common">
        <td class="title5">�ɷ����ͱ���</td>
        <td class="input5"><input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name="feetypecode" id="feetypecode"
            onclick="return showCodeList('feetype', [this, FeeTypeName],[0, 1]);" 
            ondblclick="return showCodeList('feetype', [this, FeeTypeName],[0, 1]);" 
            onkeyup="return showCodeListKey('feetype', [this, FeeTypeName],[0, 1]);"></td>
        
        <td class="title5">�ɷ���������</td>
        <TD  class= input5>
			<Input class="common wid" name=FeeTypeName id="FeeTypeName" readonly> 
		</TD>
		</tr>   
    </table>
  </Div>
    </div>
    <br>
    <a href="javascript:void(0)" class=button onclick="searchState();">��ѯ״̬</a>
    <a href="javascript:void(0)" class=button onclick="putList();">�������</a>
    <a href="javascript:void(0)" class=button onclick="printList();">��  ӡ</a>
    <a href="javascript:void(0)" class=button onclick="initInpBox(); initCardInfo();">��  ��</a>
    <!-- <input value="��ѯ״̬" type="button" class="cssButton" onclick="searchState();">
    <input value="�������" type="button" class="cssButton" onclick="putList();">

    <input value="��    ӡ" type="button" class="cssButton" onclick="printList();">
    <input value="��    ��" type="button" class="cssButton" onclick="initInpBox(); initCardInfo();"> -->
    
    <input type="hidden" name="State">

    <table>
      <tr>
      <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this, divCardInfo);"></td>
    	<td class= titleImg>��֤��Ϣ</td>
     </tr>
    </table>
    
  	<div id="divCardInfo" style="display: ''" align=center>
      <table class="common">
        <tr class="common">
      	  <td style=" text-align: left" colSpan=1><span id="spanCardInfo"></span></td></tr>
      </table>
      
      <input VALUE="��  ҳ" TYPE="button" class="cssButton90" onclick="turnPage.firstPage();"> 
      <input VALUE="��һҳ" TYPE="button" class="cssButton91" onclick="turnPage.previousPage();"> 					
      <input VALUE="��һҳ" TYPE="button" class="cssButton92" onclick="turnPage.nextPage();"> 
      <input VALUE="β  ҳ" TYPE="button" class="cssButton93" onclick="turnPage.lastPage();"> 					
  	</div>

	  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  	
  </form>

  <form action="./SelfCertifySearchPrint.jsp" method=post name="fm_print" id="fm_print" target="_blank">
    <table>
      <tr>
        <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this, divCardListInfo);"></td>
    	<td class= titleImg>��ӡ��Ϣ</td></tr>
    </table>
    
  	<div id="divCardListInfo" style="display: ''">
      <table class="common">
        <tr class="common">
      	  <td style=" text-align: left" colSpan=1><span id="spanCardListInfo"></span></td></tr>
      </table>
  	</div>
  	
  	<input type="hidden" name="hiddenFeeType" id="hiddenFeeType">
    <br>
    <br>
    <br>
    <br>
	</form>
</body>
</html>
