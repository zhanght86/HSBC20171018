    <%
//�������ƣ�TempFeeInput.jsp
//�����ܣ������շѵ�����
//�������ڣ�2002-07-12 
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html> 
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<SCRIPT src="ContInsuRiskInput.js"></SCRIPT>
<%@include file="ContInsuRiskInit.jsp"%>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
</head>
<body  onload="initForm();" >
<form action="./ProposalSave.jsp" method=post name=fm target="fraSubmit">

<DIV id=DivRiskHidden STYLE="display:''">
<TABLE class=common>

  <TR CLASS=common>
    <TD CLASS=title>
      Ͷ�������� 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class="readonly" readonly NAME=ContNo>
    </TD>
    <TD CLASS=title>
      ������� 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class="readonly" readonly NAME=AgentCom>
    </TD>
    <TD CLASS=title>
      ������
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class="readonly" readonly NAME=Handler>
    </TD>
  </TR>

</TABLE>

 

<DIV id=DivLCInsuredPolButton STYLE="display:''">
<TABLE >
<tr>
<td>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCInsuredPol);">
</td>
<td class= titleImg>
��ѯ������������Ϣ
 </TD>
  </TR>
  
</TABLE>
</DIV>

<Div  id= "DivLCInsuredPol" style= "display: ''">
  <!--¼����ݽ��ѱ��� -->
    <Table  class= common>
      	<tr>
    	 <td text-align: left colSpan=1>
	 <span id="spanLCInsuredPolGrid" >
	 </span> 
	</td>
       </tr>
    </Table>
    <table>
    <TR class=common> 
    <TD   class=common>
      <input type =button class=cssButton value="�� һ ��" onclick="returnparent();">   
           </TD>
     <TD   class=common>
      <input type =button class=cssButton value="�޸ı�����������Ϣ" onclick="InputPolicy();">      
     </TD>
     <TD   class=common>
      <input type =button class=cssButton value="ɾ��������������Ϣ" onclick="deleteRecord();">      
     </TD>
     
    </TR> 
    </table>
    </div>
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>




