<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//�������ƣ�PolStatus.jsp
//�����ܣ�����״̬��ѯ
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<html>
<%
  //�����¸���


  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script type="">

	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
	var PrtNo = "<%=request.getParameter("PrtNo")%>";
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" type="" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js" type=""></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js" type=""></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js" type=""></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js" type=""></SCRIPT>
  <SCRIPT src="PolStatus.js" type=""></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="PolStatusInit.jsp"%>
  <title>����״̬��ѯ </title>
</head>
<body  onload="initForm();" >
  <form method=post name=fm id="fm" target="fraSubmit" action= "./PolStatusChk.jsp">
    <!-- ������Ϣ���� -->
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
          <TD  class= title5>ӡˢ��</TD>
          <TD  class= input5>
            <Input class= "common wid" name=ProposalContNo id="ProposalContNo">
          </TD>
		      <td class= title5></td>
		      <td class= input5></td>
          <Input type= "hidden" class= common name=PrtNo id="PrtNo">
        </TR>
    </table>
  </Div>
  </div>
   <INPUT VALUE="��  ѯ" class= cssButton TYPE=button onclick="easyQueryClick();"> 
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
  					<span id="spanPolGrid" >
  					</span>
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE="��  ҳ" class= cssButton90 TYPE=button onClick="getFirstPage();">
      <INPUT VALUE="��һҳ" class= cssButton91 TYPE=button onClick="getPreviousPage();">
      <INPUT VALUE="��һҳ" class= cssButton92 TYPE=button onClick="getNextPage();">
      <INPUT VALUE="β  ҳ" class= cssButton93 TYPE=button onClick="getLastPage();">
  	</div>
       <INPUT VALUE="����״̬��ϸ" class= cssButton TYPE=button onclick= "getStatus();"> 
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol2);" alt="">
    		</td>
    		<td class= titleImg>
    			 ������Ϣ
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLCPol2" style= "display: ''">
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
