<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%

GlobalInput tGI = new GlobalInput();
tGI= (GlobalInput)session.getValue("GI");

	String tContNo = "";
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
	ComCode="<%=tGI.ComCode%>";   //��¼��½���� 
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="ReGroupPolPrint.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="ReGroupPolPrintInit.jsp"%>
  <title>�ش��ŵ� </title>
</head>
<body  onload="initForm();" >
  <form action="./ReGroupPolPrintSave.jsp" method=post name=fm id="fm" target="fraSubmit">
    <!-- ������Ϣ���� -->
    <table class= common border=0 width=100%>
    	<tr>
        <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
        </td>
			  <td class= titleImg align= center>�������ѯ������</td>
		  </tr>
	</table>
  <div class="maxbox">
  <Div  id= "divFCDay" style= "display: ''">
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title5>���屣������</TD>
          <TD  class= input5><Input class= "common wid" name=GrpContNo id="GrpContNo"></TD>
          <TD  class= title5>ӡˢ����</TD>
          <TD  class= input5><Input class= "common wid" name=PrtNo id="PrtNo"></TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>�������ֱ���</TD>
          <TD class= input5> <Input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" type="text" class="codeno" name=RiskCode id="RiskCode" onclick="return showCodeList('RiskCode',[this,RiskCodeName],[0,1],null,codeRiskSql);" ondblclick="return showCodeList('RiskCode',[this,RiskCodeName],[0,1],null,codeRiskSql);" onkeyup="return showCodeListKey('RiskCode',[this,RiskCodeName],[0,1],null,codeRiskSql);"><input class="codename" name="RiskCodeName" id="RiskCodeName"> </TD>
          <TD  class= title5>���ְ汾</TD>
          <td class=input5><Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center"  type="text" class= "code" CodeData="" name=RiskVersion id="RiskVersion" onclick="showCodeListEx('RiskVer', [this]);" ondblclick="showCodeListEx('RiskVer', [this]);" onkeyup="showCodeListKeyEx('RiskVer', [this]);"></TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>���������</TD>
          <TD  class= input5>
            <Input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" type="text" class="codeno"  name=AgentGroup id="AgentGroup" onclick="return showCodeList('AgentGroup',[this,AgentGroupName],[0,1]);" ondblclick="return showCodeList('AgentGroup',[this,AgentGroupName],[0,1]);" onkeyup="return showCodeListKey('AgentGroup',[this,AgentGroupName],[0,1]);"><input class="codename" name="AgentGroupName" id="AgentGroupName"> 
          </TD>
          <TD  class= title5>�����˱���</TD>
          <TD  class= input5>
            <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" MAXLENGTH=10 name=AgentCode id="AgentCode" onclick="return queryAgent(ComCode);" ondblclick="return queryAgent(ComCode);" onkeyup="return queryAgent2();">
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>�������</TD>
          <TD class= input5><Input style="background:url(../common/images/select--bg_03.png)     no-repeat right center" type="text" class="codeno" name=ManageCom id="ManageCom" onclick="return showCodeList('station',[this,ManageComName],[0,1],null,codeSql);" ondblclick="return showCodeList('station',[this,ManageComName],[0,1],null,codeSql);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input class="codename" name="ManageComName" id="ManageComName"> </TD>
          <TD  class= title5>Ͷ�������� </TD>
          <TD  class= input5>
            <Input class= "common wid" name=GrpName id="GrpName">
          </TD>
        </TR>
    </table>
  </Div>
  </div>
  <div>
    <a href="javascript:void(0)" class=button onclick="easyQueryClick();">��ѯ����</a>
  </div>
  <br>
  <!-- <INPUT VALUE=" ��ѯ���� " class="cssButton" TYPE=button  onclick="easyQueryClick();"> -->
</form>
<form action="./ReGroupPolPrintSave.jsp" method=post name=fmSave id="fmSave" target="fraSubmit">
	<!-- <input value=" �ύ���� " class="cssButton" TYPE=button  onclick="printGroupPol();"> -->
	<!-- <input VALUE="yes" name = "" type="checkbox" onclick="dataConfirm(this);">      		
      		�������ɴ�ӡ����  --> 
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>
    			 ��ͬ��Ϣ
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLCPol1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanGrpPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE="��ҳ" class="cssButton90" TYPE=button onclick="getFirstPage();"> 
      <INPUT VALUE="��һҳ" class="cssButton91" TYPE=button onclick="getPreviousPage();"> 					
      <INPUT VALUE="��һҳ" class="cssButton92" TYPE=button onclick="getNextPage();"> 
      <INPUT VALUE="βҳ" class="cssButton93" TYPE=button onclick="getLastPage();"> 					
  	</div>
  	<input type=hidden id="fmtransact" name="fmtransact">
    <div>
    <a href="javascript:void(0)" class=button onclick="printGroupPol();">�ύ����</a>
    <input VALUE="yes" name = "" type="checkbox" onclick="dataConfirm(this);">          
          �������ɴ�ӡ����  
  </div>
  </form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
<script>
	<!--ѡ�������ֻ�ܲ�ѯ������¼�����-->
	var codeSql = "1  and code like #"+<%=tGI.ComCode%>+"%#";
	var codeRiskSql = "1  and RiskProp in (#G#,#A#,#B#,#D#)";
</script>
