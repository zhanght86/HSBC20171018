<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2002-08-21 09:25:18
//������ ��HST
//���¼�¼�� ������  ��������   ����ԭ��/����
%>

<%
//==================WANZH=====BGN========================
  String tFlag = request.getParameter("Flag");  //������ʶ
  loggerDebug("GrpCustomerDiskInput","tFlag:"+tFlag);
//==================WANZH=====END========================
//==================wood=====BGN========================
  String tRgtNo = request.getParameter("RgtNo");  //������ʶ
//==================WANZH=====END========================
%>
<head>
<%
    GlobalInput tGlobalInput = new GlobalInput();
    tGlobalInput = (GlobalInput)session.getValue("GI");
%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="GrpCustomerDiskInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="GrpCustomerDiskInit.jsp"%>
</head>
<body onload="initForm();">
  <form action="./GrpCustomerDiskSave.jsp" method=post name=fm target="fraSubmit" ENCTYPE="multipart/form-data">  
    <table class=common>
      <TR class=common>
        <TD class=title>�ļ���ַ:</TD>
        <TD class=common width=30%>
          
          <Input type="file" name=FileName size=30>
          <input name=ImportPath type= hidden>
          <input name=RgtNo type=hidden >
          <Input type=hidden name="Operator" >
          <input name=Flag type=hidden value="<%=tFlag%>">
          <INPUT class=cssButton VALUE="��  ��" TYPE=button onclick="submitForm();">
          <INPUT class=cssButton VALUE="��  ��" TYPE=reset>
        </TD>
      </TR>
      <input type=hidden name=ImportFile>
    </table>

  </form>
  <form name=fmquery action=./DiskErrList.jsp target=fraSubmit method=post> 

    <table class=common border=0 width=100%>
      <tr>
        <td class=titleImg align=center>��������˵��������Ϣ��ѯ���������ѯ����:</td>
      </tr>
    </table>
     <table class=common align=center>
      <TR class=common>
        <TD class=title>�����</TD>
        <TD class=input>
          <Input class=common name=tRgtNo>
        </TD>
        <TD class=title>�ͻ���</TD>
        <TD class=input>
          <Input class=common name=tCustomerNo>
        </TD>
        <TD class=title>�ͻ�����</TD>
        <TD class=input>
          <Input class=common name=tCustomerName>
        </TD>
        </TD>
      </TR>
    </Table> 
    <INPUT VALUE="��  ѯ" class=cssButton TYPE=button onclick="easyQueryClick();">
    <INPUT VALUE="ɾ  ��" class=cssButton TYPE=button onclick="deleteInsured();">
    <INPUT VALUE="ȫ��ɾ��" class=cssButton TYPE=button onclick="deleteAll();">
    <Table>
    <Div id="divDiskErr" style="display: ''" align = center>
      <table class=common>
        <TR class=common>
          <TD text-align: left colSpan=1>
            <span id="spanDiskErrQueryGrid"></span>
          </TD>
        </TR>
      </Table>
      <Table align = center>
      <INPUT VALUE="��  ҳ" class=cssButton TYPE=button onclick="turnPage.firstPage();">
      <INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage.previousPage();">
      <INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage.nextPage();">
      <INPUT VALUE="β  ҳ" class=cssButton TYPE=button onclick="turnPage.lastPage();">
      </Table>
    </Div>
  <span id="spanCode" style="display: none; position:absolute; slategray"></span>
  </form>
</body>
</html>
