<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<%
//�������ƣ�EdorAllCTBillPrintInput.jsp
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
<SCRIPT src="./EdorAllCTBillPrint.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="EdorAllCTBillPrintInit.jsp"%>
<title>��ȫ�˱�ͳ�Ʊ� </title>
</head>
<body  onload="initForm();" >
  <form  action="./EdorAllCTBillPrintSave.jsp" method=post name=fm id=fm target="fraSubmit">
    <!-- ��ѯ���� -->

    <table class= common border=0 width=100%>
        <tr>
        <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" >
            </TD>
            <td class= titleImg align= center>�������ѯ������</td>
    </tr>
    </table>
    <div id="divInvAssBuildInfo">
       <div class="maxbox1" >
    <table  class= common align=center>
       <TR  class= common>
          <td class="title5">����</td>
          <td class="input5">
              <Input class="codeno" name=SaleChnl id=SaleChnl
               CodeData="0|^02|����Ӫ��^03|���д���^05|רҵ����^07|�������^10|��չҵ��" 
               style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="showCodeListEx('SaleChnl', [this,SaleChnlName],[0,1])"
               onDblClick="showCodeListEx('SaleChnl', [this,SaleChnlName],[0,1])"
                onKeyUp="showCodeListKeyEx('SaleChnl', [this,SaleChnlName],[0,1])" ><input class="codename" name=SaleChnlName id=SaleChnlName readonly=true>
          </td>
          <td class="title5">��ȫ��Ŀ</td>
          <td class="input5"><input type="text" class="codeno" name="EdorType"   id="EdorType"
             style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="showCodeList('PEdorType',[this,EdorTypeName],[0,1],null,SQL,1,null)"
          ondblclick="showCodeList('PEdorType',[this,EdorTypeName],[0,1],null,SQL,1,null)"
           onKeyUp="showCodeListKey('PEdorType',[this,EdorTypeName],[0,1],null,SQL,1,null)"><input type="text" class="codename" name="EdorTypeName" id=EdorTypeName readonly></td>
 
       </TR>
    </table>
<div id = "divDate" style= "display: ''">
 <table  class= common align=center>
       <TR>
          <TD  class= title5>ͳ������<font color=red> *</font></TD>
          <TD  class= input5><input class="coolDatePicker" dateFormat="short" id="StartDate"  name="StartDate" onClick="laydate
({elem:'#StartDate'});" > <span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img 
src="../common/laydate/skins/default/icon.png" /></a></span>
 </TD>
          <TD  class= title5>ͳ��ֹ��<font color=red> *</font></TD>
          <TD  class= input5> <input class="coolDatePicker" dateFormat="short" id="EndDate"  name="EndDate" onClick="laydate({elem:'#EndDate'});"> <span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
   </TD>
       </TR>
 </table>
</div>
 <table  class= common align=center>
       <TR>
          <TD  class= title5>�������<font color=red> *</font></TD>
          <TD  class= input5>
          	<Input class= code name=ManageCom  id=ManageCom 
             style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="showCodeList('Station',[this],[0],null,codeSql,'1')" 
            onDblClick="showCodeList('Station',[this],[0],null,codeSql,'1')" 
            onKeyUp="showCodeListKey('Station',[this],[0],null,codeSql,'1')"></TD>
          <TD  class= title5>��������<font color=red> *</font></TD>
          <td class="input5">
              <Input class="codeno" name=NewRePortFlag id=NewRePortFlag
              CodeData="0|^1|�˱��ſ�^2|�����˱��ſ�^3|Э���˱�ԭ��^4|Э���˱���ֵ�Ƚ�^5|Э���˱����ֲ�ֵ�Ƚ�^6|������^7|Э���˱���ֵ��ϸ"  
             style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="showCodeListEx('NewRePortFlag', [this,NewRePortFlagName],[0,1])"
              onDblClick="showCodeListEx('NewRePortFlag', [this,NewRePortFlagName],[0,1])"
               onKeyUp="showCodeListKeyEx('NewRePortFlag', [this,NewRePortFlagName],[0,1])" ><input class="codename" name=NewRePortFlagName id=NewRePortFlagName readonly=true>
          </td>		 
       </TR>
      
</table>
</div></div>

   <!-- <p>
      <INPUT VALUE=" ��ӡ�嵥 " class= cssButton TYPE=button onClick="printBill();">
    </p>-->
    <a href="javascript:void(0);" class="button"onClick="printBill();">��ӡ�嵥</a>
    <input type="hidden" name="fmtransact" id=fmtransact>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
<script>
	<!--ѡ�������ֻ�ܲ�ѯ������¼�����-->
	var codeSql = "1  and code like #"+<%=tGI.ComCode%>+"%#";
	var SQL = "1  and edorcode in (select code from ldcode where codetype=#edortype# and code in (#CT#,#XT#,#WT#,#PT#,#XS#))";
</script>
