<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>

<html>
<%
	String GrpContNo= "";
	String RiskCode= "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	try
	{
		GrpContNo = request.getParameter( "GrpContNo" );
		RiskCode= request.getParameter( "RiskCode" );
		loggerDebug("LMChargeRateInput","=========>"+GrpContNo+"<==========="+RiskCode);
		if( GrpContNo == null || GrpContNo.equals( "" ))
			GrpContNo= "00000000000000000000";
		if( RiskCode == null || RiskCode.equals( "" ))
			RiskCode= "00000000000000000000";
			
	}
	catch( Exception e1 )
	{
		GrpContNo= "00000000000000000000";
		RiskCode= "00000000000000000000";
	}
%>

  <SCRIPT> 
       var GrpContNo ="<%=request.getParameter("GrpContNo")%>";
       var RiskCode ="<%=request.getParameter("RiskCode")%>";
  </SCRIPT>

<head>
 
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="LMChargeRate.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="LMChargeRateInit.jsp"%>
<title>�����Ѷ��� </title>
</head>

         
<body onload="initForm();">
<form method=post name=fm id=fm target="fraSubmit" action="LMChargeRateSave.jsp">
    <table>
    	<tr>
    		<td class=common>
    			<IMG src="../common/images/butExpand.gif" style="cursor:hand;">
    		</td>
    		<td class=titleImg align=center>��ͬ������Ϣ</td>
    	</tr>
    </table>
	<div class=maxbox1>	                             
    <table class=common align=center>
        <TR class=common>
        	<TD class=title5>�����ͬ��</TD>
        	<TD class=input5>
        		<Input class="readonly wid" readonly name=ProposalGrpContNo id=ProposalGrpContNo value="">
        		<input type=hidden name=GrpContNo id=GrpContNo value="">
        		<input type=hidden name=mOperate id=mOperate>
        	</TD>
			<TD class=title5></TD>
        	<TD class=input5></td>
        </tr>
    		
        <tr class=common>
            <TD class=title5>���ֱ���</TD>
            <TD class=input5>
			    <Input class=code name=RiskCode id=RiskCode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
				ondblclick="return showCodeList('GrpRisk',[this],null,null,fm.GrpContNo.value,'b.GrpContNo',1);"
				onkeyup="return showCodeListKey('GrpRisk',[this],null,null,fm.GrpContNo.value,'b.GrpContNo',1);">
			</TD>
            <TD id=divmainriskname style="display:none" class=title>���ձ���</TD>
            <TD id=divmainrisk style="display:none" class=input>
			    <Input class=code maxlength=6 name=MainRiskCode id=MainRiskCode
				style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
				ondblclick="return showCodeList('GrpMainRisk',[this],null,null,fm.GrpContNo.value,'b.GrpContNo',1);" 
				onkeyup="return showCodeListKey('GrpMainRisk',[this],null,null,fm.GrpContNo.value,'b.GrpContNo',1);">
			</TD>   
			<TD class=title5></TD>
        	<TD class=input5></td>
        </TR>
    </table>
    </div>	
    <table>
		<tr>
    		<td class=common><IMG src="../common/images/butExpand.gif" style="cursor:hand;"></td>
    		<td class=titleImg align=center>�����ѱ���¼��</td>	
		</tr>
   </table>
   <div class=maxbox1>
   <table class=common>
        <TR  class= common>
            <TD class=title5> �����ѱ��� </TD>
        	<TD class=input5><Input class="common wid" name=ChargeRate verify="�����ѱ���|value>=0&value<=1&notnull"></TD>
			<TD class=title5></TD>
        	<TD class=input5></td>
        </TR>
   </table>          
                  	
    <Div  id= "divRiskSave" style= "display: ''" align= right>  
        <tr>
            <INPUT VALUE="��  ��" class=cssButton TYPE=button onclick="saveLMChargeRate();"> 
            <INPUT VALUE="��  ��" class=cssButton TYPE=button onclick="updateLMChargeRate();"> 	
            <INPUT VALUE="ɾ  ��" class=cssButton TYPE=button onclick="deleteLMChargeRate();"> 
            <INPUT VALUE="��  ��" class=cssButton TYPE=button onclick="returnLMChargeRate();"> 
        </tr>     
    </Div>  
	
    <hr class=line>
    <Div  id= "divRiskSave" style= "display: ''" align= left>  
        <tr>	
            <INPUT VALUE="��  ѯ" class=cssButton TYPE=button onclick="selectLMChargeRate();"> 	
        </tr>     
    </Div>          
          
    <table>
        <tr>
        	<td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);"></td>
        	<td class= titleImg>�����Ѷ�����Ϣ</td>
        </tr>
    </table>
    <Div  id= "divLCPol1" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanCaseGrid" >
  					</span> 
  			  	</td>
  			</tr>
        </table>
        <INPUT VALUE="��  ҳ" TYPE=button class=cssButton90  onclick="getFirstPage();"> 
        <INPUT VALUE="��һҳ" TYPE=button class=cssButton91  onclick="getPreviousPage();"> 					
        <INPUT VALUE="��һҳ" TYPE=button class=cssButton92  onclick="getNextPage();"> 
        <INPUT VALUE="β  ҳ" TYPE=button class=cssButton93  onclick="getLastPage();"> 
    </DIV>       
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
