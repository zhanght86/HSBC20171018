<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>

<%
	String GrpContNo = "";
	try
	{
		GrpContNo = request.getParameter( "GrpContNo" );
		
		loggerDebug("GrpPolSingleQuery","---GrpContNo:"+GrpContNo);
	}
	
	
	catch( Exception e1 )
	{
		GrpContNo = "00000000000000000000";
			loggerDebug("GrpPolSingleQuery","---contno:"+GrpContNo);

	}
 %>
<%@include file="GrpPolSingleQueryInit.jsp"%>
<script>
	var tGrpContNo = "<%=GrpContNo%>";  //���˵��Ĳ�ѯ����.

</script>
<head onload="initForm();">
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <SCRIPT src="GrpPolSingleQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>


  <title>�����¸��˲�ѯ </title>
</head>
<body onload="initForm();easyQueryClick();">
  <form method=post name=fm id=fm target="fraSubmit">
    <div class="maxbox">
    <table  class= common> 
      	<TR  class= common>
          <TD  class= title> 
            ���ֱ��� 
          </TD>
          <TD  class= input>
          	<input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly name=RiskCode id=RiskCode ondblclick="return showCodeListEx('RiskCode',[this,RiskCodeName],[0,1],'','','',true,'400');" onclick="return showCodeListEx('RiskCode',[this,RiskCodeName],[0,1],'','','',true,'400');" onkeyup="return showCodeListEx('RiskCode',[this,RiskCodeName],[0,1],'','','',true,'400');"><input class=codename name=RiskCodeName id=RiskCodeName readonly=true> 
          </TD>
          <TD  class= title>  ���˱����� </TD>
          <TD  class= input>  <Input class="wid" class= common name=ContNo id=ContNo ></TD>
          <TD  class= title> ���˿ͻ��� </TD>
          <TD  class= input> <Input class="wid" class=common name=InsuredNo id=InsuredNo ></TD>
        </TR>
        <TR  class= common>
           <TD  class= title>����״̬</TD>
          <TD  class= input><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=AppFlag id=AppFlag CodeData="0|^0|Ͷ��δ��Ч^1|��Ч^2|����/��������δ��Ч^4|ʧЧ" ondblclick="return showCodeListEx('AppFlag', [this,AppFlagName],[0,1]);" onclick="return showCodeListEx('AppFlag', [this,AppFlagName],[0,1]);" onkeyup="return showCodeListKeyEx('AppFlag', [this,AppFlagName],[0,1]);" ><input class="codename" name=AppFlagName id=AppFlagName readonly=true></TD>
         <TD  class= title>����</TD>
          <TD  class= input> <Input class="wid" class= common name=Name id=Name ></TD>
          <TD  class= title>�Ա�</TD>
          <TD  class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=Sex id=Sex  ondblclick="return showCodeList('Sex',[this,SexName],[0,1]);" onclick="return showCodeList('Sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('Sex',[this,SexName],[0,1]);"><input class=codename name=SexName id=SexName readonly=true></TD>
        </TR>
         <TR  class= common>
         	 <TD  class= title>����</TD>
          <TD  class= input> <Input class="wid" class="coolDatePicker" dateFormat="short" name=BirthDay id=BirthDay ></TD>
         	<!--<TD  class= input>
         		<INPUT VALUE="��  ѯ" class = cssButton TYPE=button onclick="easyQueryClick();"> 
         	</TD>-->
        </TR>
    	</table></div>
        <a href="javascript:void(0);" class="button" onClick="easyQueryClick();">��    ѯ</a>
  	<table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>
    			 ���˱�����Ϣ
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
      <INPUT VALUE="��  ҳ" class = cssButton90 TYPE=button onclick="getFirstPage();"> 
      <INPUT VALUE="��һҳ" class = cssButton91 TYPE=button onclick="getPreviousPage();"> 					
      <INPUT VALUE="��һҳ" class = cssButton92 TYPE=button onclick="getNextPage();"> 
      <INPUT VALUE="β  ҳ" class = cssButton93 TYPE=button onclick="getLastPage();"> 					
  	</Div>
  	  
  	 <!--<INPUT VALUE="�ʻ���ѯ" class = cssButton TYPE=button onclick="returnParent();">
  	 <INPUT VALUE="����Ѳ�ѯ" class = cssButton TYPE=button onclick="LCInsureAccFeeQuery();">
  	 <INPUT class=cssButton VALUE="��  ��" TYPE=button onclick="GoBack();">-->
     <a href="javascript:void(0);" class="button" onClick="returnParent();">�ʻ���ѯ</a>
     <a href="javascript:void(0);" class="button" onClick="LCInsureAccFeeQuery();">����Ѳ�ѯ</a>
     <a href="javascript:void(0);" class="button" onClick="GoBack();">��    ��</a>
     
  	 <span id="spanCode"  style="display: 'none'; position:absolute; slategray"></span> 	 
  	<Div id= "divSingleInfo" style= "display: 'none'" align = center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanSingleInfoGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>				
  	</Div>
    <div id="divTurnPageSingleInfoGrid" align="center" style= "display:'none'">
         <input type="button" class="cssButton90" value="��  ҳ" onclick="turnPage.firstPage()">
         <input type="button" class="cssButton91" value="��һҳ" onclick="turnPage.previousPage()">
         <input type="button" class="cssButton92" value="��һҳ" onclick="turnPage.nextPage()">
         <input type="button" class="cssButton93" value="β  ҳ" onclick="turnPage.lastPage()">
    </div> 
</form>
  	
</body>
</html>
