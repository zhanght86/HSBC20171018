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
<SCRIPT src="./EdorThousandTF.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="EdorThousandTFInit.jsp"%>
<title>��ȫϵͳ�����������嵥 </title>
</head>
<body  onload="initForm();" >
  <form  action="./EdorThousandTFSave.jsp" method=post name=fm  id=fm target="fraSubmit">
<Div id=DivFileDownload style="display:'none'">
      <A id=fileUrl href=""></A>
</Div>
    <!-- ��ѯ���� -->
<table >
  <tr>
    <td class="common">
      <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" >
   </td>
    <td class= titleImg>
��ȫϵͳ�����������嵥
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
          <TD  class= title5>�������<font color=red> *</font></TD>
          <TD  class= input5>
          	<Input class="common wid" name=ManageCom id=ManageCom verify="�������|notnull"
            style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="showCodeList('Station',[this],[0],null,codeSql,'1')" 
            onDblClick="showCodeList('Station',[this],[0],null,codeSql,'1')"
             onKeyUp="showCodeListKey('Station',[this],[0],null,codeSql,'1')"></TD>
          <td class="title5">����</td>
          <td class="input5">
              <Input class="codeno" name=SaleChnl id=SaleChnl CodeData="0|^02|����Ӫ��^03|���д���^05|רҵ����^07|�������^10|��չҵ��" 
              style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="showCodeListEx('SaleChnl', [this,SaleChnlName],[0,1])" 
              onDblClick="showCodeListEx('SaleChnl', [this,SaleChnlName],[0,1])" 
              onKeyUp="showCodeListKeyEx('SaleChnl', [this,SaleChnlName],[0,1])" ><input class="codename" name=SaleChnlName readonly=true>
          </td>
 
       </TR>
    </table>
<div id = "divDate" style= "display: ''">
 <table  class= common align=center>
        <TR  class= common>   
          		<TD  class= title5 width="25%">ͳ������</TD>
          		<TD  class= input5 width="25%">
                <input class="coolDatePicker" dateFormat="short" id="StartDate"  name="StartDate" onClick="laydate
({elem:'#StartDate'});" verify="ͳ������|notnull&Date"> <span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img 
src="../common/laydate/skins/default/icon.png" /></a></span>

                
                 </TD>      
          		<TD  class= title5 width="25%">ͳ��ֹ��</TD>
          		<TD  class= input5 width="25%">
                 <input class="coolDatePicker" dateFormat="short" id="EndDate"  name="EndDate" onClick="laydate({elem:'#EndDate'});" verify="ͳ��ֹ��|notnull&Date"> <span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>  
      	 </TR>
 </table>
</div>
</div>
</div>
 


    <!-- ȷ�϶Ի��� ��ʼ��Ϊ0 ��ȷ�Ͽ��ȷ���޸�Ϊ1 ������Ϻ����³�ʼ��Ϊ0 --> 
	<INPUT VALUE="0" TYPE=hidden name=myconfirm> 
	<INPUT VALUE="" TYPE=hidden name=FileName> 
	<INPUT VALUE="" TYPE=hidden name=Url> 
	<input type=hidden id="fmtransact" name="fmtransact"> 
	<!--<INPUT VALUE="�����嵥" class=cssButton name=compute TYPE=button onClick="return fmsubmit()"> 
	<INPUT VALUE="�����嵥" class=cssButton name=download TYPE=button onClick="fileDownload()">-->
    <a href="javascript:void(0);" class="button"onClick="return fmsubmit()">�����嵥</a>
    <a href="javascript:void(0);" class="button"onClick="fileDownload()">�����嵥</a>



  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
<script>
	<!--ѡ�������ֻ�ܲ�ѯ������¼�����-->
	var codeSql = "1  and code like #"+<%=tGI.ComCode%>+"%#";
	var SQL = "1  and edorcode in (select code from ldcode where codetype=#edortype# and code in (#CT#,#XT#,#WT#,#PT#,#XS#))";
</script>
