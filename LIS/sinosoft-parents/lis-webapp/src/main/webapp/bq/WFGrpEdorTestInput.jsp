<html> 

<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*"%>  


<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@include file="../common/jsp/AccessCheck.jsp"%>
 
<%
	//���ҳ��ؼ��ĳ�ʼ����
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput) session.getValue("GI");  
%>   
<script>	
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
	var comcode = "<%=tGI.ComCode%>"; //��¼��½����
</script> 
<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT> 
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT> 
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>  
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <SCRIPT src="WFGrpEdorTest.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>  
  <%@include file="WFGrpEdorTestInit.jsp"%>
  
  <title>�ŵ���ȫ��������</title>

</head>

<body  onload="initForm();" >
  <form action="./WFGrpEdorTestSave.jsp" method=post name=fm target="fraSubmit">
  
<Div  id= "divSearchAll" style= "display: none">  
    <table class= common border=0 width=100%>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSearch);">
    		</td>
			<td class= titleImg align= center>�������ѯ������</td>
		</tr>
    </table>
    <Div  id= "divSearch" style= "display: ''">
    <div class="maxbox">
	    <table  class= common >   
	      	<TR  class= common>
		        <td class=title> ��ȫ����� </td>
		        <td class= input><Input class="wid" class="common" name=EdorAcceptNo_ser id=EdorAcceptNo_ser></td>
	          	<TD class= title> �ͻ�/���屣���� </TD>
	          	<td class= input><Input class="wid" class="common" name=OtherNo id=OtherNo></td>
	          	<TD class= title> �������� </TD>
	          	<td class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=OtherNoType id=OtherNoType ondblclick="showCodeList('edornotype',[this, OtherNoName], [0, 1]);" onMouseDown="showCodeList('edornotype',[this, OtherNoName], [0, 1]);" onkeyup="showCodeListKey('edornotype', [this, OtherNoName], [0, 1]);" onkeydown="QueryOnKeyDown();" ><input class=codename name=OtherNoName id=OtherNoName readonly=true ></td>            	                          
	        </TR>
	      	<TR  class= common>
                <td class=title> ������ </td>
                <td class=input><Input class="wid" type="input" class="common" name=EdorAppName id=EdorAppName></td>             
                <td class=title> ���뷽ʽ </td>
                <td class= input ><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=AppType id=AppType ondblclick="showCodeList('edorapptype',[this, AppTypeName], [0, 1]);" onMouseDown="showCodeList('edorapptype',[this, AppTypeName], [0, 1]);" onkeyup="showCodeListKey('edorapptype', [this, AppTypeName], [0, 1]);"><input class=codename name=AppTypeName id=AppTypeName readonly=true></td>                          
				<TD class=title> ������� </TD>
	          	<TD class=input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=ManageCom_ser id=ManageCom_ser ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);" onMouseDown="return showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName id=ManageComName readonly=true></TD>      	               
	        </TR> 
	      	<TR  class= common>
	            <TD class= title> ¼������ </TD>
	            <TD class= input><!--<Input class= "multiDatePicker" dateFormat="short" name=MakeDate >-->
                <Input class="coolDatePicker" onClick="laydate({elem: '#MakeDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=MakeDate id="MakeDate"><span class="icon"><a onClick="laydate({elem: '#MakeDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD> 
	            <TD class= title>  </TD>
	            <TD class= title>  </TD>
	            <TD class= title>  </TD>
	            <TD class= title>  </TD>
	        </TR> 	        
	    </table>
    </div> 
    
    </div> 
    </Div>
		<INPUT VALUE="��  ѯ" class = cssButton TYPE=hidden onclick="easyQueryClickSelf();">
		<!--<INPUT class=cssButton id="riskbutton" VALUE="�ŵ���������" TYPE=button onClick="applyMission();">-->
        <a href="javascript:void(0);" id="riskbutton" class="button" onClick="applyMission();">�ŵ���������</a>
	
	
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSelfGrid);">
    		</td>
    		<td class= titleImg>
    			 ��������
    		</td>
    	</tr>  	
    </table>
  	<Div  id= "divSelfGrid" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanSelfGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
		<!--<INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onclick="turnPage2.firstPage();"> 
		<INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onclick="turnPage2.previousPage();"> 					
		<INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onclick="turnPage2.nextPage();"> 
		<INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="turnPage2.lastPage();"> 	-->				
  	</div>
  	
	<!--<INPUT class= cssButton TYPE=button VALUE="�ŵ���ȫ����" onclick="GoToBusiDeal();"> 
	<INPUT class= cssButton TYPE=button VALUE="�ŵ��������" onclick="EdorTestFinish();">-->
    <a href="javascript:void(0);" class="button" onClick="GoToBusiDeal();">�ŵ���ȫ����</a>
    <a href="javascript:void(0);" class="button" onClick="EdorTestFinish();">�ŵ��������</a>
	
		<!-- �����򲿷� -->
			<INPUT  type= "hidden" class= Common name= EdorAcceptNo  value= "">
			<INPUT  type= "hidden" class= Common name= MissionID  value= "">
          	<INPUT  type= "hidden" class= Common name= SubMissionID  value= "">
          	<INPUT  type= "hidden" class= Common name= ActivityID  value= "">  
          	<INPUT  type= "hidden" class= Common name= Transact  value= "">  
          	        	
  </form>
  
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br><br><br><br>
</body>
</html>