<%
//�������ƣ�ProposalQuery.jsp
//�����ܣ����˲�ͨ���޸�
//�������ڣ�2002-11-23 17:06:57
//������  ������
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html>
<%
  //�����¸���
	String tGrpPolNo = "00000000000000000000";
	
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var grpPolNo = "<%=tGrpPolNo%>";      //���˵��Ĳ�ѯ����.
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  
  <SCRIPT src="ProposalQuery.js"></SCRIPT>
  <%@include file="ProposalQueryInit.jsp"%>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <title>������ѯ </title>
</head>
<body  onload="initForm();" >
  <form method=post name=fm target="fraTitle">
    <!-- ������Ϣ���� -->
    <table class= common border=0 width=100%>
    	<tr>
			<td class= titleImg align= center>�������ѯ������</td>
		</tr>
	</table>
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title> Ͷ��������  </TD>
          <TD  class= input> <Input class= common name=ContNo > </TD>
          <!--
          <TD  class= Common> ӡˢ���� </TD> 
          -->
          
          <TD  class= title> ������� </TD>
          <TD  class= input> <Input class="code" name=ManageCom ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);"> </TD>          
        </TR>
        <TR  class= common>
          <TD  class= title>  Ͷ��������  </TD>
          <TD  class= input> <Input class= common name=AppntName > </TD>
          <TD  class= title>  ����������</TD>
          <TD  class= input> <Input class= common name=InsuredName > </TD>
          <TD  class= title> �������Ա�  </TD>
          <TD class=input>  <Input class= code name=InsuredSex verify="�Ա�|NOTNULL" CodeData="0|^0|��^1|Ů^2|����" ondblClick="showCodeListEx('SexRegister',[this],[0,1,2,3]);" onkeyup="showCodeListKeyEx('SexObjRegister',[this],[0,1,2,3]);" >  </TD>        
        </TR>
        <TR  class= common>
          <TD  class= title>  �˱�����</TD>
          <td class=input>
                    <Input class=codeno name=uwState ondblclick="return showCodeList('contuwstate',[this,uwStatename],[0,1]);" onkeyup="return showCodeListKey('contuwstate',[this,uwStatename],[0,1]);"><Input class=codename name=uwStatename readonly ></td>
          <TD  class= title> ҵ��Ա����  </TD>
          <TD  class= input>  <Input class="code" name=AgentCode ondblclick="return showCodeList('AgentCode',[this, AgentGroup], [0, 2]);" onkeyup="return showCodeListKey('AgentCode', [this, AgentGroup], [0, 2]);">  </TD>
          <TD  class= title> ҵ��Ա��� </TD>
          <TD  class= input><Input class= common name=AgentGroup > </TD>
          <Input type= "hidden" class= common name=PrtNo >
        </TR>               
    </table>
    
          <INPUT class=cssButton VALUE="��ѯͶ����" TYPE=button onclick="easyQueryClick();"> 
          <INPUT class=cssButton VALUE="Ͷ������ϸ" TYPE=button onclick="queryDetailClick();"> 	
          <INPUT class=cssButton VALUE="ɨ�����ѯ" TYPE=button onclick="ScanQuery();">
          <INPUT class=cssButton VALUE="����������ѯ" TYPE=button onclick="OperationQuery();">
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>
    			 Ͷ������Ϣ
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLCPol1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	<center>
      <Div  id= "divPage" align=center style= "display: 'none' ">
      <INPUT CLASS=cssButton VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT CLASS=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT CLASS=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT CLASS=cssButton VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();">
      </Div> 					
  	</div>
		<input type=hidden id="GrpPolNo" name="GrpPolNo">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
