<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//�������ƣ�PolStatus.jsp
//�����ܣ����屣��״̬��ѯ
//�������ڣ�2002-06-19 11:10:36
//������  ��zhangxing
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<html>
<%
  
	
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	    //���˵��Ĳ�ѯ����.
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="GrpPolStatus.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="GrpPolStatusInit.jsp"%>
  <title>����״̬��ѯ </title>
</head>
<body  onload="initForm();" >
  <form method=post name=fm id="fm" target="fraSubmit" action= "./GrpPolstatusChk.jsp">
    <!-- ���屣����Ϣ���� -->
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
          <TD  class= title5>Ͷ��������</TD>
          <TD  class= input5>
            <Input class= "common wid" name=ProposalGrpContNo id="ProposalGrpContNo">
          </TD>
            <Input  type="hidden" class= common name=PrtNo id="PrtNo">
          <TD  class= title5></TD>
          <TD  class= input5></TD>         
        </TR>
    </table>
  </Div>
  </div>
  <a href="javascript:void(0)" class=button onclick="easyQueryClick();">��  ѯ</a>
  <!-- <INPUT VALUE="��  ѯ" class= cssButton TYPE=button onclick="easyQueryClick();">  -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>
    			 ���屣����Ϣ
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLCPol1" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE="��  ҳ" class= cssButton90 TYPE=Button onclick="getFirstPage();"> 
      <INPUT VALUE="��һҳ" class= cssButton91 TYPE=Button onclick="getPreviousPage();"> 					
      <INPUT VALUE="��һҳ" class= cssButton92 TYPE=Button onclick="getNextPage();"> 
      <INPUT VALUE="β  ҳ" class= cssButton93 TYPE=Button onclick="getLastPage();"> 					
  	</div>
  	<p>
      <a href="javascript:void(0)" class=button onclick="getStatus();">����״̬��ϸ</a>
      <!-- <INPUT VALUE="����״̬��ϸ" class= cssButton TYPE=Button onclick= "getStatus();">  -->
  	</p>
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol2);">
    		</td>
    		<td class= titleImg>
    			 Ͷ������Ϣ
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLCPol2" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  				<span id="spanPolStatuGrid" >
  				</span> 
  			</td>
  		</tr>
    	</table>
  </form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
