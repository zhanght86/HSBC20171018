<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<%
//�������ƣ�EdorLNLXBillPrintInput.jsp
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
<SCRIPT src="./EdorLNLXBillPrint.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="EdorLNLXBillPrintInit.jsp"%>
<title>�����Ϣ����ͳ�� </title>
</head>
<body  onload="initForm();" >
  <form  action="./EdorLNLXBillPrintSave.jsp" method=post name=fm id=fm  target="fraSubmit">
    <!-- ��ѯ���� -->

    <table class= common border=0 width=100%>
        <tr>
         <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divDate);">
            </TD>
            <td class= titleImg align= center>�������ѯ������</td>
    </tr>
    </table>
<div id = "divDate" style= "display: ''">
 <div class="maxbox1" >
 <table  class= common align=center>
       <TR>
          <TD  class= title5>ͳ������<font color=red> *</font></TD>
          <TD  class= input5><input class="coolDatePicker" dateFormat="short" id="StartDate"  name="StartDate" onClick="laydate
({elem:'#StartDate'});" > <span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img 
src="../common/laydate/skins/default/icon.png" /></a></span></TD>
          <TD  class= title5>ͳ��ֹ��<font color=red> *</font></TD>
          <TD  class= input5><input class="coolDatePicker" dateFormat="short" id="EndDate"  name="EndDate" onClick="laydate({elem:'#EndDate'});"> <span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>   </TD>
       </TR>
 </table>

 <table  class= common align=center>
       <TR>
          <TD  class= title5>�������<font color=red> *</font></TD>
          <TD  class= input5>
          	<Input class="common wid" name=ManageCom  id=ManageCom
            style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="showCodeList('Station',[this])" 
            onDblClick="showCodeList('Station',[this])"
             onKeyUp="showCodeListKey('Station',[this])"></TD>
          <TD  class= title5></TD>
          <TD  class= input5></TD>
  
       </TR>
</table>
</div></div>
<br>
   <!-- <p>
      <INPUT VALUE=" ���ɱ��� " class= cssButton TYPE=button onClick="printBill();">
      
      
      
      
      <INPUT VALUE=" ���ر��� " class= cssButton TYPE=button onClick="GetPrint();">
    </p>-->
    <a href="javascript:void(0);" class="button"onClick="printBill();">���ɱ���</a>
    <a href="javascript:void(0);" class="button"onClick="GetPrint();">���ر���</a>
    <input type="hidden" name="fmtransact">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
<script>
	<!--ѡ�������ֻ�ܲ�ѯ������¼�����-->
	var SQL = "1  and edorcode in (select code from ldcode where codetype=#edortype# and othersign=#1#)";
</script>
