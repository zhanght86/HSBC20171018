<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<%
//�������ƣ�EdorOperatorBillPrintInput.jsp
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
<SCRIPT src="./EdorOperatorBillPrint.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="EdorOperatorBillPrintInit.jsp"%>
<title>��ȫ��Աͳ�� </title>
</head>
<body  onload="initForm();" >
  <form  action="./EdorOperatorBillPrintSave.jsp" method=post name=fm id=fm target="fraSubmit">
<table >
  <tr>
    <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divInvAssBuildInfo);">
            </TD>
    <td class= titleImg>
    ��ȫ��Աͳ��
    </td> 
   </tr> 
 </table> 
     <div id="divInvAssBuildInfo">
       <div class="maxbox1" >
    <table  class= common align=center>
       <TR  class= common>
          <td class="title5">ͳ������</td>
          <td class="input5">
              <Input class="codeno" name=BillType  id=BillType 
              CodeData="0|^1|������ȫ^2|�ŵ���ȫ" 
              style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="showCodeListEx('BillType', [this,BillTypeName],[0,1])"
              onDblClick="showCodeListEx('BillType', [this,BillTypeName],[0,1])"
               onKeyUp="showCodeListKeyEx('BillType', [this,BillTypeName],[0,1])" ><input class="codename" name=BillTypeName readonly=true>
          </td>     
        <TD  class= title5>�������<font color=red> *</font></TD>
          <TD  class= input5>
          	<Input class= code name=ManageCom  id=ManageCom 
             style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="showCodeList('Station',[this],[0],null,codeSql,'1')"
            onDblClick="showCodeList('Station',[this],[0],null,codeSql,'1')"
             onKeyUp="showCodeListKey('Station',[this],[0],null,codeSql,'1')"></TD>
    
       </TR>
    </table>
<div id = "divDate" style= "display: ''">
 <table  class= common align=center>
       <TR>
          <TD  class= title5>ͳ������<font color=red> *</font></TD>
          <TD  class= input5><input class="coolDatePicker" dateFormat="short" id="StartDate"  name="StartDate" onClick="laydate
({elem:'#StartDate'});" > <span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img 
src="../common/laydate/skins/default/icon.png" /></a></span> </TD>
          <TD  class= title5>ͳ��ֹ��<font color=red> *</font></TD>
          <TD  class= input5> <input class="coolDatePicker" dateFormat="short" id="EndDate"  name="EndDate" onClick="laydate({elem:'#EndDate'});"> <span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span> </TD>
       </TR>
 </table>
</div>
</div></div>
   <!-- <p>
      <INPUT VALUE=" ��ӡ�嵥 " class= cssButton TYPE=button onClick="printBill();">
    </p>-->
     <a href="javascript:void(0);" class="button"onClick="printBill();">��ӡ�嵥</a>

    <input type="hidden" name="fmtransact">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
<script>
	<!--ѡ�������ֻ�ܲ�ѯ������¼�����-->
	var codeSql = "1  and code like #"+<%=tGI.ComCode%>+"%#";	
</script>
