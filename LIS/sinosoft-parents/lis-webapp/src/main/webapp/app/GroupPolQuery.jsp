<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html>
<%
	String tContNo = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	try
	{
		tContNo = request.getParameter( "ContNo" );
		
		//Ĭ�������Ϊ����Ͷ����
		if( tContNo == null || tContNo.equals( "" ))
			tContNo = "00000000000000000000";
	}
	catch( Exception e1 )
	{
		tContNo = "00000000000000000000";
	}
%>
<script>
	var contNo = "<%=tContNo%>";  //���˵��Ĳ�ѯ����.
	var comcode = "<%=tGI.ComCode%>"; //��¼��½����
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="GroupPolQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="GroupPolQueryInit.jsp"%>
  <title>����Ͷ������ѯ </title>
</head>
<body  onload="initForm();" >
  <form method=post name=fm id="fm" target="fraSubmit">
    <!-- ������Ϣ���� -->
    <table class= common border=0 width=100%>
    	<tr>
			<td class= titleImg align= center>�������ѯ������</td>
		</tr>
	</table>
	<div class="maxbox">
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title>
            ���屣������
          </TD>
          <TD  class= input>
            <Input class="common wid" name=GrpProposalNo id="GrpProposalNo" >
          </TD>
          <TD  class= title>
            ӡˢ����
          </TD>
          <TD  class= input>
            <Input class="common wid"n name=PrtNo id="PrtNo" >
          </TD>
          <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
            <Input class="code" name=SaleChnl id="SaleChnl" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('SaleChnl',[this]);" onkeyup="return showCodeListKey('SaleChnl',[this]);">
          </TD>
        </TR>
        <TR  class= common>
          
          <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
            <Input class="code" name=ManageCom id="ManageCom" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);">
          </TD>
          <TD  class= title>
            ҵ��Ա
          </TD>
          <TD  class= input>
            <Input class="code" name=AgentCode id="AgentCode" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('AgentCode',[this]);" onkeyup="return showCodeListKey('AgentCode',[this]);">
          </TD>
          <TD  class= title>
            ҵ��Ա���
          </TD>
          <TD  class= input>
            <Input class="code" name=AgentGroup id="AgentGroup" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('AgentGroup',[this]);" onkeyup="return showCodeListKey('AgentGroup',[this]);">
          </TD>     
       
    </table>
          <INPUT VALUE="��  ѯ" class=cssButton TYPE=button onclick="easyQueryClick();"> 
          <INPUT VALUE="��  ��" class=cssButton TYPE=button onclick="returnParent();"> 	
    </div>		  
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>
    			 ������Ϣ
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLCPol1" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanGrpPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE="��  ҳ" TYPE=button class="cssButton90"  onclick="getFirstPage();"> 
      <INPUT VALUE="��һҳ" TYPE=button class= "cssButton91"  onclick="getPreviousPage();"> 					
      <INPUT VALUE="��һҳ" TYPE=button class= "cssButton92"  onclick="getNextPage();"> 
      <INPUT VALUE="β  ҳ" TYPE=button class= "cssButton93"  onclick="getLastPage();"> 					
  	</div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>