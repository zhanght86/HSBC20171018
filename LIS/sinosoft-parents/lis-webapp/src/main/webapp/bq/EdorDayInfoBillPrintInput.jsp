<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<%
//�������ƣ�EdorDayInfoBillPrintInput.jsp
//�����ܣ���ȫ�嵥���ߴ�ӡ����̨
//�������ڣ�2005-08-08 16:20:22
//������  ��liurx
//���¼�¼��  ������    ��������      ����ԭ��/����
%>
<%
    GlobalInput tGI = new GlobalInput();
    tGI = (GlobalInput)session.getValue("GI");
%>
<script>
    var managecom = "<%=tGI.ManageCom%>"; //��¼�������
    var comcode = "<%=tGI.ComCode%>"; //��¼��½����
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
 <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
<SCRIPT src="./EdorDayInfoBillPrint.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="EdorDayInfoBillPrintInit.jsp"%>
<title>��ȫҵ���սᣨ�嵥�� </title>
</head>
<body  onload="initForm();" >
  <form  action="./EdorDayInfoBillPrintSave.jsp" method=post name=fm  id=fm target="fraSubmit">
    <!-- ��ѯ���� -->
<table >
  <tr>
    <td class="common">
      <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" >
   </td>
    <td class= titleImg>
    ��ȫҵ���սᣨ�嵥��
    </td> 
   </tr> 
 </table> 
    <table class= common border=0 width=100%>
        <tr>
         <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divInvAssBuildInfo);">
            </TD>

            <td class= titleImg align= center>�������ѯ������</td>
    </tr>
    </table>
     <div id="divInvAssBuildInfo">
       <div class="maxbox1" >
    <table  class= common align=center>
       <TR  class= common>
          <TD  class= title5>��ȫȷ��ʱ��</TD>
          <TD  class= input5>
           <input class="coolDatePicker" dateFormat="short" id="MakeDate"  name="MakeDate" onClick="laydate({elem:'#EndDate'});"> <span class="icon"><a onClick="laydate({elem: '#MakeDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>

           </TD>
        <td class="title5">��������</td>
          <td class="input5">
          	<input type="text" class="codeno" name="EdorType"  id="EdorType" CodeData="0|^0|����^1|����^2|����"
            style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="showCodeListEx('edortypedoc',[this,EdorTypeName],[0,1])"
             onDblClick="showCodeListEx('edortypedoc',[this,EdorTypeName],[0,1])"
              onKeyUp="showCodeListKeyEx('edortypedoc',[this,EdorTypeName],[0,1])"><input type="text" class="codename" name="EdorTypeName" readonly>
          </td>
       </TR>
    </table>
 <table  class= common align=center>
       <TR>
          <TD  class= title5>�������<font color=red> *</font></TD>
          <TD  class= input5>
          	<Input class= code name=ManageCom 
             style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="showCodeList('Station',[this],[0],null,codeSql,'1')"
            onDblClick="showCodeList('Station',[this],[0],null,codeSql,'1')"
             onKeyUp="showCodeListKey('Station',[this],[0],null,codeSql,'1')"></TD>
          <TD  class= title5>����Ա</TD>
          <TD  class= input5><input type="text" class="common wid" name="UserCode" id="UserCode"></TD>
  
       </TR>
</table>
</div></div>
<br>
   <!-- <p>
      <INPUT VALUE=" ��ӡ�嵥 " class= cssButton TYPE=button onClick="printBill();">
    </p>-->
    <a href="javascript:void(0);" class="button"onclick="easyQuery();">��ӡ�嵥</a>
    <input type="hidden" name="fmtransact">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
<script>
	<!--ѡ�������ֻ�ܲ�ѯ������¼�����-->
	var codeSql = "1  and code like #"+<%=tGI.ComCode%>+"%#";
</script>
