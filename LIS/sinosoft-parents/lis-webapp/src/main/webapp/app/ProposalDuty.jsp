<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��HST
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="ProposalDutyInit.jsp"%>
  <title>����������Ϣ </title>
</head>
<body  onload="initForm('<%=tPolNo%>');" >
  <form method=post name=fm id=fm target="fraSubmit">
    <%@include file="../common/jsp/InputButton.jsp"%>
    <!-- ������Ϣ���֣��б� -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCDuty1);">
    		</td>
    		<td class= titleImg>
    			 ������Ϣ
    		</td>
    	</tr>
    </table>
	<Div  id= "divLCDuty1"  style= "display: ''">
    	<table  class= common>
        	<tr  class= common>
    	  		<td style="text-align: left" colSpan=1 >
					<span id="spanDutyGrid" >
					</span> 
				</td>
			</tr>
		</table>
	</div>
    <!-- ��������Ϣ���֣��б� -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPrem1);">
    		</td>
    		<td class= titleImg>
    			 ��������Ϣ
    		</td>
    	</tr>
    </table>
	<Div  id= "divLCPrem1" style= "display: ''">
    	<table  class= common>
        	<tr  class= common>
    	  		<td style="text-align: left" colSpan=1>
					<span id="spanPremGrid" >
					</span> 
				</td>
			</tr>
		</table>
	</div>
    <!-- ��ȡ����Ϣ���֣��б� -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCGet1);">
    		</td>
    		<td class= titleImg>
    			 ��ȡ����Ϣ
    		</td>
    	</tr>
    </table>
	<Div  id= "divLCGet1" style= "display: ''">
    	<table  class= common>
        	<tr  class= common>
    	  		<td style="text-align: left" colSpan=1>
					<span id="spanGetGrid" >
					</span> 
				</td>
			</tr>
		</table>
	</div>
          <INPUT VALUE="����" TYPE=button onclick="window.close();"> 					
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  
</body>

</html>
