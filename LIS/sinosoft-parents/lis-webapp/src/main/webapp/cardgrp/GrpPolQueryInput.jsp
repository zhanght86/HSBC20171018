<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>

<%
	String tContNo = "";
	String tFlag ="";
	try
	{
		tContNo = request.getParameter( "ContNo" );
		tFlag = request.getParameter("flag");
		
		//Ĭ�������Ϊ���屣��
		if( tContNo == null || tContNo.equals( "" ))
			tContNo = "00000000000000000000";
	}
	
	
	catch( Exception e1 )
	{
		tContNo = "00000000000000000000";
			loggerDebug("GrpPolQueryInput","---contno:"+tContNo);

	}
	loggerDebug("GrpPolQueryInput","---contno:"+tContNo);
        String tDisplay = "";
	try
	{
		tDisplay = request.getParameter("display");
		if(tDisplay == null || tDisplay.equals( "" ))
		 { tDisplay = "0";}
	}
	catch( Exception e )
	{
		tDisplay = "";
	}
%>

<%
   GlobalInput tG = new GlobalInput();
   tG=(GlobalInput)session.getValue("GI"); //���ҳ��ؼ��ĳ�ʼ����
   loggerDebug("GrpPolQueryInput","�������-----"+tG.ComCode);
%>   
<script>
	var contNo = "<%=tContNo%>";  //���˵��Ĳ�ѯ����.
	var comCode = "<%=tG.ComCode%>";
	var tDisplay = "<%=tDisplay%>";
	var flag ="<%=tFlag%>";
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="GrpPolQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

  <%@include file="GrpPolQueryInit.jsp"%>
  <title>����
  ������ѯ </title>
</head>
<body  onload="initForm();" >
  <form method=post name=fm  id=fm target="fraSubmit">
    <!-- ������Ϣ���� -->
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
          <%if("1".equals(tFlag)){ %>
          <TD  class= title5> �������κ� </TD>
          <TD  class= input5> <Input class="common wid" name=GrpContNo >  </TD>
        <%}else{ %>
          <TD  class= title5> ���屣������ </TD>
          <TD  class= input5> <Input class="common wid" name=GrpContNo >  </TD>
        <%} %>
        </TR>
        <TR  class= common>
          <TD  class= title5>  Ͷ�������� </TD>
          <TD  class= input5>  <Input class="common wid" name=PrtNo ></TD>
          <TD  class= title5> ������� </TD>
          <TD  class= input5> <Input class="code" name=ManageCom  id=ManageCom
          style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
          onclick="return showCodeList('station',[this]);" 
          onDblClick="return showCodeList('station',[this]);" 
          onKeyUp="return showCodeListKey('station',[this]);"> </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5> �����˱��� </TD>
        <TD  class= input5> <input NAME="AgentCode" id="AgentCode"  VALUE="" MAXLENGTH="10"class= "common wid" 
        elementtype=nacessary verifyorder="1" Aonkeyup="return queryAgent();"
         onBlur="return queryAgent();" onDblClick="return queryAgent();" ></TD> 
          <!--<TD  class= input> <Input class="code" name=AgentCode ondblclick="return showCodeList('AgentCode',[this]);" onkeyup="return showCodeListKey('AgentCode',[this]);"> </TD>          -->
          <TD  class= title5> ��������� </TD>
          <TD  class= input5> <Input class="common wid" name=AgentGroup id=AgentGroup 
          style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
          onclick="return showCodeList('AgentGroup',[this]);" 
          onDblClick="return showCodeList('AgentGroup',[this]);" 
          onKeyUp="return showCodeListKey('AgentGroup',[this]);"> </TD>
        <%if(!"1".equals(tFlag)){ %>
          <TD  class= title5> ��λ�ͻ����� </TD>
          <TD  class= input5> <Input class="common wid" name=CustomerNo > </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5> ��λ���� </TD>
          <TD  class= input5> <Input class="common wid" name=GrpName > </TD>
          <TD  class= title5> ��λ��ַ </TD>
          <TD  class= input5> <Input class="common wid" name=GrpAddress> </TD>
          
          <TD  class= title5> ��ϵ�� </TD>
          <TD  class= input5> <Input class="common wid" name=LinkMan1> </TD>
        </TR>
        <%} %>
        <TR>
		  <td class="title5">���ִ���</td>
          <td class="input5"><input type="text" class="codeno" name="RiskCode" verify="���ִ���|Code:riskgrp" 
          style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
            onclick="showCodeList('riskgrp',[this,RiskCodeName],[0,1])"
          onDblClick="showCodeList('riskgrp',[this,RiskCodeName],[0,1])"
           onKeyUp="showCodeListKey('riskgrp',[this,RiskCodeName],[0,1])"><input type="text" class="codename" name="RiskCodeName" readonly></td>
		</TR>
        
    </table>
    </div>
    </div>
     <a href="javascript:void(0);" class="button" onClick="easyQueryClick();">��   ѯ</a>
     
          <!--<INPUT VALUE="��  ѯ" class = cssButton TYPE=button onClick="easyQueryClick();"> -->
         
          <INPUT VALUE="��  ��" name=Return class = cssButton TYPE=button STYLE="display:none" onClick="returnParentBQ();">
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
  	<Div  id= "divLCPol1" style= "display: ''" align = center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanGrpPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE="��  ҳ" class = cssButton90 TYPE=button onClick="getFirstPage();"> 
      <INPUT VALUE="��һҳ" class = cssButton91 TYPE=button onClick="getPreviousPage();"> 					
      <INPUT VALUE="��һҳ" class = cssButton92 TYPE=button onClick="getNextPage();"> 
      <INPUT VALUE="β  ҳ" class = cssButton93 TYPE=button onClick="getLastPage();"> 					
  	</div>
  	<P>
    <a href="javascript:void(0);" class="button"onClick="returnParent();">��֤������ϸ</a>

 <!-- 	 <INPUT VALUE="��֤������ϸ" class = cssButton TYPE=button onClick="returnParent();"> 	-->				
  	</P>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
