<html>
<%@page import="com.sinosoft.lis.certify.*"%>
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2002-10-14 10:20:43
//������  ��kevin
//���¼�¼��  ������    ��������     ����ԭ��/����

  GlobalInput tG = (GlobalInput)session.getValue("GI");
  String Branch =tG.ComCode;
	String strCertifyClass = (String)session.getAttribute("CertifyClass");
	
	session.removeAttribute("CertifyClass");
	
	if( strCertifyClass == null || strCertifyClass.equals("") ) {
		strCertifyClass = CertifyFunc.CERTIFY_CLASS_CERTIFY;
	}
	
	String strCertifyCode = "";
	String codeSql = "1";
	if(!Branch.equals("86"))
	{
		codeSql = "1  and ( managecom = '86' or managecom like '"+ Branch +"%') ";
	}

	if( strCertifyClass.equals(	CertifyFunc.CERTIFY_CLASS_CERTIFY ) ) {
		strCertifyCode = "<input class=\"code\" name=\"CertifyCode\" " +
											" ondblclick=\"return showCodeList('CertifyCode',[this],null,null,codeSql,'1',null,250);\" " +
											" onkeyup=\"return showCodeList('CertifyCode',[this],null,null,codeSql,'1',null,250);\" " +
											" verify=\"��֤����|NOTNULL\">";
	} else {
		strCertifyCode = "<input class=\"code\" name=\"CertifyCode\" " +
											" ondblclick=\"return showCodeList('CardCode', [this],null,null,codeSql,'1',null,250);\" " +
											" onkeyup=\"return showCodeList('CardCode', [this],null,null,codeSql,'1',null,250);\"" +
											" verify=\"��֤����|NOTNULL\">";
	}
	loggerDebug("CertifyPrintInput",strCertifyCode);
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="CertifyPrintInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <%@include file="CertifyPrintInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form action="./CertifyPrintSave.jsp" method=post name=fm id=fm target="fraSubmit">
    
    <table class="common">
	    <tr class="common">
		    <td class=common>
                 <img  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCertifyPrintList);"></img>
			</td>
			<td class=titleImg>�������ѯ������</td>
		</tr>
	</table>
    <div id= "divCertifyPrintList" class=maxbox style= "display: ''">
      <table class="common">
        <tr class="common">
          <td class="title5">ӡˢ���κ�</td>
          <td class="input5" title="���ѯ��Ҫ�ᵥ��ӡˢ���κ�">
          	<input class="readonly wid" readonly name="PrtNo" id=PrtNo>
          	<input class="cssButton" type="button" value="?" style="padding-right:10px" onclick="query()" style="width:20"></td>
          
          <td class="title5"></td>
          <td class="input5"></td>
          
        <tr class="common">
          <td class="title5">��֤����</td>
          <td class="input5"><%= strCertifyCode %></td>     
          
          <td class="title5">�������</td>
          <td class="input5"><input class="coolDatePicker wid" name="MaxDate" id=MaxDate verify="�������|DATE&NOTNULL"></td></tr>
          
        <tr class="common">
          <td class="title5">��֤�۸�</td>
          <td class="input5"><input class="common wid" name="CertifyPrice" id=CertifyPrice verify="��֤�۸�|NUM&NOTNULL"></td>
          
          <td class="title5">�������</td>
          <td class="input5"><input class="readonly wid" readonly name="ManageCom" id=ManageCom></td>
<%
				if(Branch.equals("86"))
				{
%>				
        <tr class="common">
          <td class="title5">��Ȩ�ӹ�˾ӡˢ</td>
          <td class= input5><Input class="code" name=subManageCom id=subManageCom style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
          		ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);"></td>
          <td></td></tr>
<%
				}
%>        
        
        <tr class="common">
          <td class="title5">��ʼ��</td>
          <td class="input5"><input class="common wid" name="StartNo" id=StartNo></td>
          
          <td class="title5">��ֹ��</td>
          <td class="input5"><input class="common wid" name="EndNo" id=EndNo></td></tr>
          
        <tr class="common">
          <td class="title5">����</td>
          <td class="input5"><input class="common wid" name="SumCount" id=SumCount></td>
          
          <td class="title5">״̬</td>
          <td class="input5"><input class="readonly wid" readonly name="State" id=State></td></tr>

        <tr class="common" height=20><td></td></tr>
          
        <tr class="common">
          <td class="title5">���̱���</td>
          <td class="input5"><input class="common wid" name="ComCode" id=ComCode></td>
          
          <td class="title5">ӡˢ����</td>
          <td class="input5"><input class="common wid" MAXLENGTH=25 name="Phone" id=Phone verify="ӡˢ����|NOTNULL"></td></tr>

        <tr class="common">
          <td class="title5">��������Ա</td>
          <td class="input5"><input class="readonly wid" readonly name="OperatorInput" id=OperatorInput></td>
          
          <td class="title5">��ϵ��</td>
          <td class="input5"><input class="common wid" name="LinkMan" id=LinkMan></td></tr>
          
        <tr class="common">
          <td class="title5">��������</td>
          <td class="input5"><input class="coolDatePicker wid" name="InputDate" id=InputDate verify="��������|DATE&NOTNULL"></td>

          <td class="title5">������������</td>
          <td class="input5"><input class="readonly wid" readonly name="InputMakeDate" id=InputMakeDate></td></tr>
          
        <tr class="common" height=20><td></td></tr>
          
        <tr class="common">
          <td class="title5">�ᵥ����Ա</td>
          <td class="input5"><input class="readonly wid" readonly name="OperatorGet" id=OperatorGet></td>
          
          <td class="title5">�ᵥ��</td>
          <td class="input5"><input class="common wid" name="GetMan" id=GetMan></td></tr>
          
        <tr class="common">
          <td class="title5">�ᵥ����</td>
          <td class="input5"><input class="coolDatePicker wid" name="GetDate" id=GetDate></td>
          
          <td class="title5">�ᵥ��������</td>
          <td class="input5"><input class="readonly wid" readonly name="GetMakeDate" id=GetMakeDate></td></tr>
          
      </table>
	  <table class="common">
    	<tr class="common">
    		<input class="cssButton" type="button" value="����" onclick="requestClick()" >
    		<input class="cssButton" type="button" value="�ᵥ" onclick="confirmClick()" >
    		<input class="cssButton" type="button" value="��ѯ" onclick="queryClick()" >
		</tr>
    
      </table>
    </div>
    <input type=hidden name=hideOperate id=hideOperate value=''>
    <input type=hidden name=sql_where id=sql_where value=''>
    <input type=hidden name=CertifyClass id=CertifyClass value='<%= strCertifyClass %>'>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
<script>
	<!--ѡ�������ֻ�ܲ�ѯ������¼�����-->
	var codeSql = "1";
	if('<%=Branch%>' != '86')
	{
		var codeSql = "1  and ( managecom = #86# or managecom like #"+<%=Branch%>+"%#) ";
	}
</script>
