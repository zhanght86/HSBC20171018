<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//�������ƣ�GroupUWAuto.jsp
//�����ܣ������Զ��˱�
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<html>
<%
  //�����¸���	
	String tContNo = "";
	
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>	
	var contNo = "<%=tContNo%>";          //���˵��Ĳ�ѯ����.
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
	var comcode = "<%=tGI.ComCode%>"; //��¼��½����
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="GroupUWAuto.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="GroupUWAutoInit.jsp"%>
  <title>�Զ��˱� </title>
</head>
<body  onload="initForm();" >
  <form action="./GroupUWAutoChk.jsp" method=post name=fm target="fraSubmit">
    <!-- ������Ϣ���� -->
    <table class= common border=0 width=100%>
    	<tr>
		<td class= titleImg align= center>�������ѯ����������</td>
	</tr>
    </table>
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title>
            ����Ͷ������
          </TD>
          <TD  class= input>
            <Input class= common name=ProposalGrpContNo >
          </TD>
          <TD  class= title>
            �������
          </TD>
          <TD  class= input>
            <Input class="code" name=ManageCom ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);">
            <input class=codename name=ManageComName readonly=true>
          </TD>
          <TD  class= title>
            Ͷ����λ�ͻ���
          </TD>
          <TD  class= input>
            <Input class= common name=GrpNo >
          </TD>
        </TR>
        <TR  class= common>
          
          <TD  class= title>
            Ͷ����λ����
          </TD>
          <TD  class= input>
            <Input class= common name=Name >
          </TD>
           <TD  class= title>
            �����˱���
          </TD>
          <TD  class= input>
            <Input class="code" name=AgentCode ondblclick="return showCodeList('AgentCode',[this,AgentCodeName],[0,1]);" onkeyup="return showCodeListKey('AgentCode',[this,AgentCodeName],[0,1]);">
            <input class=codename name=AgentCodeName readonly=true>
          </TD>
          <TD  class= title>
            ӡˢ��
          </TD>
          <TD  class= input>
            <Input class=common name=PrtNo>
          </TD>
        </TR>
       
    </table>
          <INPUT VALUE="��  ѯ" class = cssButton TYPE=button onclick="easyQueryClick();"> 
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCGrp1);">
    		</td>
    		<td class= titleImg>
    			 ����Ͷ������Ϣ
    		</td>
    	</tr>
    	<tr>
          <INPUT  type= "hidden" class= Common name= MissionID  value= "">
          <INPUT  type= "hidden" class= Common name= SubMissionID  value= "">
          <INPUT  type= "hidden" class= Common name= ProposalGrpContNoHidden  value= "">
    	</tr>
    </table>
  	<Div  id= "divLCGrp1" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanGrpGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE="��  ҳ"  class =  cssButton TYPE=button onclick="getFirstPage();"> 
      <INPUT VALUE="��һҳ" class = cssButton TYPE=button onclick="getPreviousPage();"> 					
      <INPUT VALUE="��һҳ" class = cssButton TYPE=button onclick="getNextPage();"> 
      <INPUT VALUE="β  ҳ"  class =  cssButton TYPE=button onclick="getLastPage();"> 					
  	</div>
  	<p>
      <INPUT VALUE="�Զ��˱�" class = cssButton TYPE=button onclick="GrpUWAutoMakeSure();"> 
      <!--INPUT VALUE="���������־" class = cssButton TYPE=button onclick="SetSpecialFlag();"--> 
  	</p>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
