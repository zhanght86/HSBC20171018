<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>

<html>
<%
  //�����¸���
	//String tGrpPolNo = "00000000000000000000";
	String tContNo = "00000000000000000000";
	
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var contNo = "<%=tContNo%>";          //���˵��Ĳ�ѯ����.
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
</script>

<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="GroupContDelete.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="GroupContDeleteInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form method=post name=fm id="fm" target="fraSubmit">
    <!-- ������ѯ���� -->
    <table class= common border=0 width=100%>
    	<tr>
        <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
        </td>
		    <td class= titleImg align= center>�������ѯ������</td>
	    </tr>
    </table>
    <div class="maxbox1">
    <Div  id= "divFCDay" style= "display: ''"> 
    <table  class= common align=center>
        <TR  class= common>
          <TD  class= title5>Ͷ������</TD>
          <TD  class= input5>
            <Input class="common wid" name=QGrpProposalNo id="QGrpProposalNo">
          </TD>
          <TD  class= title5></TD>
          <TD  class= input5></TD>  
		    </TR>
        <TR  class= common>
          <TD  class= title5>
            <!-- ����״̬ -->
          </TD>
          <TD  class= input5>
            <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center"  class="code" type = "hidden" readonly name=QState id="QState" value= "0" CodeData= "0|^0|δǩ��^1|��ǩ��" onclick="showCodeListEx('State',[this,''],[1,0],'', '', '', 1);" ondblClick="showCodeListEx('State',[this,''],[1,0],'', '', '', 1);" onkeyup="showCodeListKeyEx('State',[this,''],[1,0],'', '', '', 1);">
          </TD>     
        </TR> 
    </table>
  </Div>
  </div>
  <a href="javascript:void(0)" class=button onclick="querygrp();">��  ѯ</a>
       <!-- <INPUT VALUE="��  ѯ" Class="cssButton" TYPE=button onclick="querygrp();"> -->
       <INPUT type= "hidden" name= "Operator" id="Operator" value= "">
       <INPUT type= "hidden" name= "GrpContNo" id="GrpContNo" value= "">
       <INPUT type= "hidden" name= "PrtNo" id="PrtNo" value= "">
    <!-- ��ѯδ�����嵥���б� -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>
    			 ����Ͷ������ѯ���
    		</td>
    	</tr>
    </table>
    
  	<Div  id= "divLCPol1" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanGrpGrid">
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      	<table  class= common>
      		<INPUT VALUE="��  ҳ" Class="cssButton90" TYPE=button onclick="getFirstPage();"> 
      		<INPUT VALUE="��һҳ" Class="cssButton91" TYPE=button onclick="getPreviousPage();"> 					
      		<INPUT VALUE="��һҳ" Class="cssButton92" TYPE=button onclick="getNextPage();"> 
      		<INPUT VALUE="β  ҳ" Class="cssButton93" TYPE=button onclick="getLastPage();">    	
    	</table>
	</Div>
  <a href="javascript:void(0)" class=button onclick="deleteGrpCont();">�ŵ�����ɾ��</a>
  <a href="javascript:void(0)" class=button onclick="deleteCont();">ɾ����������</a>
  <!-- <INPUT VALUE="�ŵ�����ɾ��" Class="cssButton" TYPE=button onclick="deleteGrpCont();"> 
  <INPUT VALUE="ɾ����������" Class="cssButton" TYPE=button onclick="deleteCont();"> --> 
  </form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <script>changecolor(); </script>
</body>
</html>
