<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
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
			tContNo = "";
	}
	
	catch( Exception e1 )
	{
		tContNo = "";
			loggerDebug("AppGrpPolQueryInput","---contno:"+tContNo);

	}
	loggerDebug("AppGrpPolQueryInput","---contno:"+tContNo);
%>
<script>
	var contNo = "<%=tContNo%>";  //���˵��Ĳ�ѯ����.
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>

  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <script src="../common/javascript/MultiCom.js"></script>
    
   <SCRIPT src="AppGrpPolQuery.js"></SCRIPT>
  <%@include file="AppGrpPolQueryInit.jsp"%>
  <title>����Ͷ������ѯ </title>
</head>
<body  onload="initForm();" >
  <form method=post name=fm id="fm" target="fraSubmit">
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
          <TD  class= title> Ͷ������</TD>
          <TD  class= input>
            <Input class= "common wid" name=GrpProposalNo id="GrpProposalNo">
          </TD>
            <Input type="hidden" class= "common wid" name=PrtNo id="PrtNo">
          <TD  class= title>��������</TD>
          <TD  class= input>
            <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name=SaleChnl id="SaleChnl" onclick="return showCodeList('AgentType',[this]);" ondblclick="return showCodeList('AgentType',[this]);" onkeyup="return showCodeListKey('AgentType',[this]);">
          </TD>
          <TD class=title>�������</TD>
				  <TD class=input>
					  <Input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" class=codeno name=ManageCom id="ManageCom" verify="�������|code:comcode&notnull" onclick="return showCodeList('comcode',[this,ManageComName],[0,1],null,'#1#','1');" ondblclick="return showCodeList('comcode',[this,ManageComName],[0,1],null,'#1#','1');" onkeyup="return showCodeListKey('comcode',[this,ManageComName],[0,1],null,'#1#','1');"><input class=codename name=ManageComName id="ManageComName" readonly=true elementtype=nacessary>
				  </TD>
        </TR>
        <tr class=common>
          <td class=title>���ֱ���</td>
          <td class=input>
            <input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" class=codeno name=RiskCode id="RiskCode" onclick="getcodedata2();return showCodeListEx('RiskGrpWithOutEC',[this,RiskCodeName],[0,1],null,null,null,true,'400');" ondblclick="getcodedata2();return showCodeListEx('RiskGrpWithOutEC',[this,RiskCodeName],[0,1],null,null,null,true,'400');" onkeyup="getcodedata2();return showCodeListEx('RiskGrpWithOutEC',[this,RiskCodeName],[0,1],null,null,null,1,'400');"><input class=codename name=RiskCodeName id="RiskCodeName" readonly=true elementtype=nacessary>                                                
          </td>
          <TD  class= title>�ͻ��������</TD>
          <TD  class= input>
            <Input class= "common wid" name=CManagerCode id="CManagerCode">
          </TD>
        </tr>      
    </table>
  </Div>
  </div>
  <a href="javascript:void(0)" class=button onclick="easyQueryClick();">��  ѯ</a>
  <!-- <INPUT VALUE="��  ѯ" Class=cssButton TYPE=button onclick="easyQueryClick();">  -->
          		
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
  	<Div  id= "divLCPol1" style= "display: ''" >
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanGrpPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <div style= "display: none" align=center>
      <INPUT VALUE="��  ҳ" Class=cssButton90 TYPE=button onclick="getFirstPage();"> 
      <INPUT VALUE="��һҳ" Class=cssButton91 TYPE=button onclick="getPreviousPage();"> 					
      <INPUT VALUE="��һҳ" Class=cssButton92 TYPE=button onclick="getNextPage();"> 
      <INPUT VALUE="β  ҳ" Class=cssButton93 TYPE=button onclick="getLastPage();"> 	
      </div>				
  	</div>
  	<P>
      <a href="javascript:void(0)" class=button onclick="returnParent();">Ͷ������ϸ</a>
      <a href="javascript:void(0)" class=button onclick="ScanQuery();">����ɨ�����ѯ</a>
  	  <!-- <INPUT VALUE="Ͷ������ϸ" Class=cssButton TYPE=button onclick="returnParent();"> 
      <INPUT VALUE="����ɨ�����ѯ"    Class=cssButton TYPE=button onclick="ScanQuery();"> --> 
          <%
          String today=PubFun.getCurrentDate();
          
          %>
          <input type=hidden id="sysdate" name="sysdate" value="<%=today%>">			
  	</P>
  </form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
