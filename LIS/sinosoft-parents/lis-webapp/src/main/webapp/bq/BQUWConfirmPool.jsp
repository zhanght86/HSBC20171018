<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<html>
<%
  //�����¸���  
  GlobalInput tGI = new GlobalInput();
  tGI = (GlobalInput)session.getValue("GI");
%>
<script>  
  var operator = "<%=tGI.Operator%>";   //��¼����Ա
  var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
  var comcode = "<%=tGI.ComCode%>"; //��¼��½����
  var curDay = "<%=PubFun.getCurrentDate()%>"; 
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/jquery.workpool.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <SCRIPT src="BQUWConfirmPool.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="BQUWConfirmPoolInit.jsp"%>
  <title>��ȫ�˹��˱�ȷ��</title>
</head>
<body  onload="initForm();" >
  <form action="./ScanContInputSave.jsp" method=post name=fm id=fm target="fraSubmit">
  <div id="BQUWConfirmPool"></div>
    <!-- ������Ϣ���� -->
  <!-- <table class= common border=0 width=100%>
      <tr>
      <td class= titleImg align= center>�������ѯ������</td>
    </tr>
    </table>
    <Div  id= "divSearch" style= "display: ''">
      <table  class= common >   
          <TR  class= common>
            <TD  class= title>�������</TD>
            <TD  class= input>
              <Input class="codeno" name=ManageCom verify="�������|code:station" ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName readonly=true>
            </TD>        
            <TD  class= title>������</TD>
            <TD  class= input><Input class= common name=ContNo ></TD>
                             
          </TR>  
          <TR  class= common>
            <TD  class= title>������</TD>
            <TD  class= input><Input class="common" name=EdorNo > </TD>   
            <TD  class= title>ҵ�����</TD>
            <td class="input"><Input class= "codeno" name=EdorType verify="��������|notnull&code:EdorCode" ondblclick="return showCodeList('EdorCode', [this,EdorTypeName], [0,1], null,0, 0);" onkeyup="return showCodeListKey('EdorCode', [this,EdorTypeName], [0,1], null, 0, 0);"><input class=codename name=EdorTypeName readonly=true></TD>            
          </TR>      
      </table>

    </div> 
    <INPUT VALUE="��  ѯ" class = cssButton TYPE=button onclick="easyQueryClick();"> 
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divGrpGrid);">
    		</td>
    		<td class= titleImg>��������</td>
    	</tr>
    	<tr>
          <INPUT  type= "hidden" class= Common name= MissionID  value= "">
          <INPUT  type= "hidden" class= Common name= SubMissionID  value= "">
          <INPUT  type= "hidden" class= Common name= ActivityID  value= "">        
          <INPUT  type= "hidden" class= Common name= InputTime  value= "">        
    	</tr>
    </table>
  	<Div  id= "divGrpGrid" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanGrpGrid" ></span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT CLASS=cssButton VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();HighlightAllRow()"> 
      <INPUT CLASS=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();HighlightAllRow()"> 					
      <INPUT CLASS=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();HighlightAllRow()"> 
      <INPUT CLASS=cssButton VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();HighlightAllRow()">						
  	</div>
  	
  <br>
   <INPUT class=cssButton id="riskbutton" VALUE="��  ��" TYPE=button onClick="ApplyUW();">

   <DIV id=DivLCContInfo STYLE="display:''"> 
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSelfGrpGrid);">
    		</td>
    		<td class= titleImg>���˹�����</td>
    	</tr>  	
    </table>
  	<Div  id= "divSelfGrpGrid" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanSelfGrpGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
         <INPUT VALUE="��  ҳ" class=cssButton TYPE=button onclick="turnPage2.firstPage();HighlightSelfRow();"> 
         <INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage2.previousPage();HighlightSelfRow();"> 					
         <INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage2.nextPage();HighlightSelfRow();"> 
         <INPUT VALUE="β  ҳ" class=cssButton TYPE=button onclick="turnPage2.lastPage();HighlightSelfRow();"> 					
  	</div> -->  
 	      <INPUT  type= "hidden" class= "Common wid" name= MissionID id=MissionID  value= "">
          <INPUT  type= "hidden" class= "Common wid" name= SubMissionID id=SubMissionID  value= "">
          <INPUT  type= "hidden" class= "Common wid" name= ActivityID id=ActivityID value= "">   
          <INPUT  type= "hidden" class= "Common wid" name= InputTime id=InputTime value= "">  
  	<p>
  	</p>

  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
