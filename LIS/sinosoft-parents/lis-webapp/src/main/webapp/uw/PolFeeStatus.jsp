<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//�������ƣ�PolFeeStatus.jsp
//�����ܣ�Ͷ��������״̬��ѯ
//�������ڣ�2003-07-7 11:10:36
//������  ��SXY
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<html>
<%
  //�����¸���
	String tGrpPolNo = "00000000000000000000";
	String tContNo = "00000000000000000000";
	
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var grpPolNo = "<%=tGrpPolNo%>";      //���˵��Ĳ�ѯ����.
	var contNo = "<%=tContNo%>";          //���˵��Ĳ�ѯ����.
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
  <SCRIPT src="PolFeeStatus.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="PolFeeStatusInit.jsp"%>
  <title>Ͷ��������״̬��ѯ </title>
</head>
<body  onload="initForm();" >
  <form method=post name=fm id=fm target="fraSubmit" action= "./PolFeeStatusChk.jsp">
    <!-- Ͷ������Ϣ���� -->
    <table class= common border=0 width=100%>
    	<tr>
         <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage

(this,divInvAssBuildInfo);">
            </TD>
			<td class= titleImg align= center>�������ѯ������</td>
		</tr>
	</table>
     <div id="divInvAssBuildInfo">
       <div class="maxbox1" >
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title5>
            ӡˢ��
          </TD>
          <TD  class= input5>
            <Input class= "common wid" id="PrtNo" name=PrtNo >
          </TD>
          
          <TD  class= title5>
            Ͷ��������
          </TD>
          <TD  class= input5>
            <!--Input type=hidden class= common name=ProposalNo -->
            <Input class= "common wid" id="ProposalNo" name=ProposalNo >
          </TD>
          
        </TR>
    </table>
    </div>
    </div>
    <br>

    <INPUT VALUE="��    ѯ" class= cssButton TYPE=button onClick="easyQueryClick();">
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
  	<Div  id= "divLCPol1" style= "display: ''" align =center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanPolFeeGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE="��  ҳ" class= cssButton90 TYPE=button onClick="getFirstPage();"> 
      <INPUT VALUE="��һҳ" class= cssButton91 TYPE=button onClick="getPreviousPage();"> 					
      <INPUT VALUE="��һҳ" class= cssButton92 TYPE=button onClick="getNextPage();"> 
      <INPUT VALUE="β  ҳ" class= cssButton93 TYPE=button onClick="getLastPage();"> 					
  	</div>
  	<p>

    <INPUT VALUE="Ͷ��������״̬��ϸ" class= cssButton TYPE=button onclick= "getStatus();"> 
  	</p>
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol2);">
    		</td>
    		<td class= titleImg>
    			 Ͷ����������ϸ
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLCPol2" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  				<span id="spanPolFeeStatuGrid" >
  				</span> 
  			</td>
  		</tr>
    	</table>
        </Div>
        <br><br><br><br>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
