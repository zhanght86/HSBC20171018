<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�AlreadyActivateCertifyQuery.jsp
//�����ܣ��Ѽ������ѯ
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
  <SCRIPT src="AlreadyActivateCertifyQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="AlreadyActivateCertifyQueryInit.jsp"%>
  <title>�Ѽ������ѯ</title>
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
    <div class="maxbox" >
      <Div  id= "divFCDay" style= "display: ''">
    <table class="common" align="center" id="tbInfo" name="tbInfo">
      <tr class="common">
        <td class="title5">����</td>
        <td class="input5"><input class="common wid" name="CertifyNo" id="CertifyNo"></td>
        
        <td class="title5"></td>
        <td class="input5"></td>
      <tr class="common">
        <td class="title5">���ִ���</td>
        <td class="input5"><input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" class="codeno"  name="RiskCode" id="RiskCode" onClick="return showCodeList('riskcode', [this,RiskCodeName],[0,1],null,null,null,1);" onDblClick="return showCodeList('riskcode', [this,RiskCodeName],[0,1],null,null,null,1);">
          <input class="codename" name="RiskCodeName" id="RiskCodeName" readonly elementtype=nacessary></td></td>
        <td class="title5">��Ʒ����</td> 
        <td class="input5"><input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" class="codeno" name="Portfolio" id="Portfolio" onClick="return showCodeList('portfolio', [this,PlanCodeName],[0,1],null,null,null,1);" onDblClick="return showCodeList('portfolio', [this,PlanCodeName],[0,1],null,null,null,1);"><input class="codename" name="PlanCodeName" id="PlanCodeName" readonly elementtype=nacessary></td>
      <tr class="common5">
        <td class="title5">�������ڣ���ʼ��</td>
        <td class="input5"><Input class="coolDatePicker" onClick="laydate({elem: '#MakeDateB'});" verify="������ڣ���ʼ��|NOTNULL" dateFormat="short" name=MakeDateB  id="MakeDateB"><span class="icon"><a onClick="laydate({elem: '#MakeDateB'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
        
        <td class="title5">�������ڣ�������</td>
        <td class="input5"><Input class="coolDatePicker" onClick="laydate({elem: '#MakeDateE'});" verify="������ڣ�������|NOTNULL" dateFormat="short" name=MakeDateE id="MakeDateE"><span class="icon"><a onClick="laydate({elem: '#MakeDateE'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
     </tr>
           <TR><TD colSpan="4"  class= title5><font color=red>(ע�⣺�ɷ���ֹ�ڼ��������ʮ���ڣ���ֹ����������)</font> </TD></TR>
    </table>
  </Div>
    </div>
    <a href="javascript:void(0)" class=button onClick="easyQueryClick();">��  ѯ</a>
    <!-- <input value="��  ѯ" type="button" class="cssButton" onclick="easyQueryClick();"> 	 -->
    <table>
      <tr>
        <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this, divCertifyInfo);"></td>
    	<td class= titleImg>������Ϣ</td></tr>
    </table>
	<div id="divCertifyInfo">    
  	<div id="divCardInfo" style="display: ''" align=center>
      <table class="common">
        <tr class="common">
      	  <td style=" text-align: left" colSpan=1><span id="spanCardInfo"></span></td></tr>
      </table>
      <input VALUE=" ��ҳ " TYPE="button" class="cssButton90" onClick="turnPage.firstPage();"> 
      <input VALUE="��һҳ" TYPE="button" class="cssButton91" onClick="turnPage.previousPage();"> 					
      <input VALUE="��һҳ" TYPE="button" class="cssButton92" onClick="turnPage.nextPage();"> 
      <input VALUE=" βҳ " TYPE="button" class="cssButton93" onClick="turnPage.lastPage();"> 					
  	</div>
  	
  	 	<div id="divFamilyCardInfo" style="display: none" align=center>
      <table class="common">
        <tr class="common">
      	  <td style=" text-align: left" colSpan=1><span id="spanFamilyCardInfo"></span></td></tr>
      </table>
      
      <input VALUE=" ��ҳ " TYPE="button" class="cssButton90" onClick="turnPage.firstPage();"> 
      <input VALUE="��һҳ" TYPE="button" class="cssButton91" onClick="turnPage.previousPage();"> 					
      <input VALUE="��һҳ" TYPE="button" class="cssButton92" onClick="turnPage.nextPage();"> 
      <input VALUE=" βҳ " TYPE="button" class="cssButton93" onClick="turnPage.lastPage();"> 					

  	</div>
	<div id="divContInfo" style="display: none" align=center>
       <table class="common">
        <tr class="common">
      	  <td style=" text-align: left" colSpan=1><span id="spanContInfoGrid"></span></td></tr>
      </table>
    </div>
    </div>
    <br>
    <br>
    <br>
    <br>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
