<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>

<html>
<%
  //�����¸���	
	String tContNo = "";
	String tFlag = request.getParameter("type");
	String tInputTime = request.getParameter("InputTime");
	loggerDebug("DSScanContInput","tInputTime: "+tInputTime);
  	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>	
	var contNo = "<%=tContNo%>";          //���˵��Ĳ�ѯ����.
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
	var type = "<%=tFlag%>";
	var comcode = "<%=tGI.ComCode%>"; //��¼��½����
	var InputTime = "<%=tInputTime%>";
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="DSScanCont.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="DSScanContInit.jsp"%>
  <title>ɨ��¼��</title>
</head>
<body  onload="initForm();" >
  <form action="./ScanContInputSave.jsp" method=post name=fm id="fm" target="fraSubmit">
    <!-- ������Ϣ���� -->
    <table class= common border=0 width=100%>
    	<tr>
        <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSearch);">
        </td>
			<td class= titleImg align= center>�������ѯ������</td>
		</tr>
    </table>
    <div class="maxbox1">
    <Div  id= "divSearch" style= "display: ''">
	    <table  class= common >   
	      	<TR  class= common>
	          <TD  class= title5>�������</TD>
	          <TD  class= input5>
	            <Input style="background:url(../common/images/select--bg_03.png)     no-repeat right center" class="codeno" name=ManageCom id="ManageCom" verify="�������|code:station" onclick="return showCodeList('station',[this,ManageComName],[0,1]);" ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName id="ManageComName" readonly=true>
	          </TD>      	
	          <TD  class= title5>ӡˢ��</TD>
	          <TD  class= input5><Input class= "common wid" name=PrtNo id="PrtNo"></TD>
	                           
	        </TR>  
	        <TR  class= common>
	          <TD  class= title5>ɨ������</TD>
	          <TD  class= input5> <Input class="coolDatePicker" onClick="laydate({elem: '#InputDate'});" verify="ɨ������|DATE" dateFormat="short" name=InputDate id="InputDate"><span class="icon"><a onClick="laydate({elem: '#InputDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD> 	
	          <TD  class= title5>��������</TD>
	          <TD  class= input5>
	          <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center"   class="code" name="state"  id="state"
	            CodeData="0|^35|����^16|����^21|�н�^51|��ͥ��^11|����^61|������"
	            onclick="showCodeListEx('ModeSelect_0',[this],[0,1]);"
               ondblClick="showCodeListEx('ModeSelect_0',[this],[0,1]);"
	            onkeyup="showCodeListKeyEx('ModeSelect_0',[this],[0,1]);">
	          </TD>              
	        </TR>      
	    </table>
    </div> 
    </div>
    <a href="javascript:void(0)" class=button onclick="easyQueryClick();">��  ѯ</a>
    <a href="javascript:void(0)" class=button id="riskbutton" onclick="ApplyUW();">��  ��</a>
    <!-- <INPUT VALUE="��  ѯ" class = cssButton TYPE=button onclick="easyQueryClick();"> 
    <INPUT class=cssButton id="riskbutton" VALUE="��  ��" TYPE=button onClick="ApplyUW();"> -->

    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divGrpGrid);">
    		</td>
    		<td class= titleImg>��������</td>
    	</tr>
    	<tr>
          <INPUT  type= "hidden" class= Common name= MissionID    id="MissionID" value= "">
          <INPUT  type= "hidden" class= Common name= SubMissionID id="SubMissionID" value= "">
          <INPUT  type= "hidden" class= Common name= ActivityID   id="ActivityID" value= "">        
          <INPUT  type= "hidden" class= Common name= InputTime    id="InputTime" value= "">        
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
      <INPUT CLASS=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT CLASS=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT CLASS=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT CLASS=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();">						
  	</div>
  	
  <br>

   <DIV id="DivLCContInfo" STYLE="display:''"> 
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSearch1);">
    		</td>
    		<td class= titleImg>���˹�����</td>
    	</tr>  	
    </table>
    <div class="maxbox1">
    <Div  id= "divSearch1" style= "display: ''">
	    <table  class= common >   
	      	<TR  class= common>
	          <TD  class= title5>�������</TD>
	          <TD  class= input5>
	            <Input style="background:url(../common/images/select--bg_03.png)     no-repeat right center" class="codeno" name=ManageCom1 id="ManageCom1" verify="�������|code:station" onclick="return showCodeList('station',[this,ManageComName1],[0,1]);" ondblclick="return showCodeList('station',[this,ManageComName1],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName1],[0,1]);"><input class=codename name=ManageComName1 id="ManageComName1" readonly=true>
	          </TD>      	
	          <TD  class= title5>ӡˢ��</TD>
	          <TD  class= input5><Input class= "common wid" name=PrtNo1 id="PrtNo1"></TD>
	                           
	        </TR>  
	        <TR  class= common>
	          <TD  class= title5>ɨ������</TD>
	          <TD  class= input5><Input class="coolDatePicker" onClick="laydate({elem: '#InputDate1'});" verify="ɨ������|DATE" dateFormat="short" name=InputDate1 id="InputDate1"><span class="icon"><a onClick="laydate({elem: '#InputDate1'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD> 	
	          <TD  class= title5>��������</TD>
	          <TD  class= input5>
	          <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name="state1" id="state1"
	            CodeData="0|^35|����^16|����^21|�н�^51|��ͥ��^11|����^61|������"
              onclick="showCodeListEx('ModeSelect_0',[this],[0,1]);"
	            ondblClick="showCodeListEx('ModeSelect_0',[this],[0,1]);"
	            onkeyup="showCodeListKeyEx('ModeSelect_0',[this],[0,1]);">
	          </TD>              
	        </TR>      
	    </table>
    </div> 
    </div>
    <div>
    <a href="javascript:void(0)" class=button onclick="easyQueryClickSelf();">��  ѯ</a>
    <!-- <INPUT VALUE="��  ѯ" class = cssButton TYPE=button onclick="easyQueryClickSelf();">  -->
    </div>
    <br>
  	<Div  id= "divSelfGrpGrid" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanSelfGrpGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <div style= "display: none">
         <INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onclick="turnPage2.firstPage();"> 
         <INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onclick="turnPage2.previousPage();"> 					
         <INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onclick="turnPage2.nextPage();"> 
         <INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="turnPage2.lastPage();"> 		
      </div>			
  	</div>
  </form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
