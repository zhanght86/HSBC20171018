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
  <SCRIPT src="RnewPolStatus.js" type=""></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="RnewPolStatusInit.jsp"%>
  <title>����״̬��ѯ </title>
</head>
<body  onload="initForm();" >
  <form method=post name=fm id="fm" target="fraSubmit" action= "./RnewPolStatusChk.jsp">
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
          <TD  class= title5>������</TD>
          <TD  class= input5>
            <Input class= "common wid" name=ProposalContNo >
          </TD>
          <TD class= title5>���ִ���</TD>
				  <TD class= input5>
					  <Input style="background:url(../common/images/select--bg_03.png)     no-repeat right center" class=codeno name=RiskCode CodeData ="" onclick="getcodedata2();return showCodeListEx('PolRisk',[this,'RiskCodeName'],[0,1],null,null,null,1);" ondblclick="getcodedata2();return showCodeListEx('PolRisk',[this,'RiskCodeName'],[0,1],null,null,null,1);" onkeyup="getcodedata2(); return showCodeListKey('PolRisk',[this,'RiskCodeName'],[0,1],null,null,null,1);"><Input class=codename name="RiskCodeName" readonly=true><font color=red>*</font>
				  </TD>
          <Input type= "hidden" class= common name=PrtNo >
        </TR>
    </table>
  </Div>
  </div>
  <a href="javascript:void(0)" class=button onclick="easyQueryClick();">��  ѯ</a>
          <!-- <INPUT VALUE="��  ѯ" class= cssButton TYPE=button onclick="easyQueryClick();"> -->
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
      	  		<td style=" text-align: left" colSpan=1>
  					<span id="spanPolGrid" >
  					</span>
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE="��  ҳ" class= cssButton90 TYPE=button onclick="getFirstPage();">
      <INPUT VALUE="��һҳ" class= cssButton91 TYPE=button onclick="getPreviousPage();">
      <INPUT VALUE="��һҳ" class= cssButton92 TYPE=button onclick="getNextPage();">
      <INPUT VALUE="β  ҳ" class= cssButton93 TYPE=button onclick="getLastPage();">
  	</div>
    <div>
      <a href="javascript:void(0)" class=button onclick="getStatus();">����״̬��ϸ</a>
    </div>
    <br>
      <!-- <INPUT VALUE="����״̬��ϸ" class= cssButton TYPE=button onclick= "getStatus();"> -->
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
      	  		<td style=" text-align: left" colSpan=1>
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
