<html> 
<% 
//�������ƣ�WFEdorConfirmInput.jsp
//�����ܣ���ȫ������-�����޸�
//
%>

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
  <SCRIPT src="WFGrpEdorApproveModify.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>  
  <%@include file="WFGrpEdorApproveModifyInit.jsp"%>
  
  <title>�����޸�</title>

</head>

<body  onload="initForm();" >
  <form action="./WFGrpEdorApproveModifySave.jsp" method=post name=fm id=fm target="fraSubmit">

    <table class= common border=0 width=100%>
    	<tr>
        	<td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divSearch)"></td>
			<td class= titleImg align= center>�������ѯ������</td>
		</tr>
    </table>
    <Div  id= "divSearch" style= "display: ''">
    <div class="maxbox">
	    <table  class= common >   
	      	<TR  class= common>
		        <td class=title> ��ȫ����� </td>
		        <td class= input><Input class="wid" class="common" name=EdorAcceptNoSearch id=EdorAcceptNoSearch></td>
	          	<TD class= title> �������� </TD>
	          	<td class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=OtherNoType id=OtherNoType ondblclick="showCodeList('gedornotype',[this, OtherNoName], [0, 1]);" onMouseDown="showCodeList('gedornotype',[this, OtherNoName], [0, 1]);" onkeyup="showCodeListKey('edornotype', [this, OtherNoName], [0, 1]);" onkeydown="QueryOnKeyDown();" ><input class=codename name=OtherNoName id=OtherNoName readonly=true ></td>            	                          
	          	<TD class= title> ���屣���� </TD>
	          	<td class= input><Input class="wid" class="common" name=OtherNo id=OtherNo></td>
	        </TR>
	      	<TR  class= common>
                <td class=title> ������ </td>
                <td class=input><Input class="wid" type="input" class="common" name=EdorAppName id=EdorAppName></td>             
                <td class=title> ���뷽ʽ </td>
                <td class= input ><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=AppType id=AppType ondblclick="showCodeList('edorapptype',[this, AppTypeName], [0, 1]);" onMouseDown="showCodeList('edorapptype',[this, AppTypeName], [0, 1]);" onkeyup="showCodeListKey('edorapptype', [this, AppTypeName], [0, 1]);"><input class=codename name=AppTypeName id=AppTypeName readonly=true></td>                          
	            <TD class= title> ¼������ </TD>
	            <TD class= input><!--<Input class= "multiDatePicker" dateFormat="short" name=MakeDate >-->
                <Input class="coolDatePicker" onClick="laydate({elem: '#MakeDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=MakeDate id="MakeDate"><span class="icon"><a onClick="laydate({elem: '#MakeDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD> 
	        </TR> 
	      	<TR  class= common>
				<TD class=title> ������� </TD>
	          	<TD class=input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=ManageCom id=ManageCom ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);" onMouseDown="return showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName id=ManageComName readonly=true></TD>      	               
	            <TD class= title>  </TD>
	            <TD class= title>  </TD>
	            <TD class= title>  </TD>
	            <TD class= title>  </TD>
	        </TR> 	        
	    </table>
    </div> 
    </Div>
	<!--<INPUT VALUE="��  ѯ" class = cssButton TYPE=button onclick="easyQueryClickAll();"> -->
     <a href="javascript:void(0);" class="button" onClick="easyQueryClickAll();">��    ѯ</a>
	
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAllGrid);">
    		</td>
    		<td class= titleImg>
    			 ��������
    		</td>
    	</tr>
    </table>
  	<Div  id= "divAllGrid" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanAllGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
		<INPUT CLASS=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"> 
      	<INPUT CLASS=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
      	<INPUT CLASS=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
      	<INPUT CLASS=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();">						
  	</div>
  	
	
		<!--<INPUT class=cssButton id="riskbutton" VALUE="��  ��" TYPE=button onClick="applyMission();">-->
		<a href="javascript:void(0);" id="riskbutton" class="button" onClick="applyMission();">��    ��</a>
	
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSelfGrid);">
    		</td>
    		<td class= titleImg>
    			 �ҵ�����
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
		<!--<INPUT VALUE="��  ҳ" class=cssButton TYPE=button onclick="turnPage2.firstPage();"> 
		<INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage2.previousPage();"> 					
		<INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage2.nextPage();"> 
		<INPUT VALUE="β  ҳ" class=cssButton TYPE=button onclick="turnPage2.lastPage();"> 	-->				
  	</div>
  	<br>
	<!--<INPUT class= cssButton TYPE=button VALUE=" �����޸� " onclick="GoToBusiDeal();"> 
	<INPUT class= cssButton TYPE=button VALUE="���±��鿴" onclick="showNotePad();">-->
     <a href="javascript:void(0);" class="button" onClick="GoToBusiDeal();">�����޸�</a>
    <a href="javascript:void(0);" class="button" onClick="showNotePad();">���±��鿴</a>
	
		<!-- �����򲿷� -->
			<INPUT  type= "hidden" class= Common name= MissionID  value= "">
          	<INPUT  type= "hidden" class= Common name= SubMissionID  value= "">
          	<INPUT  type= "hidden" class= Common name= ActivityID  value= "">
          	<INPUT  type= "hidden" class= Common name= EdorAcceptNo  value= "">
          	<INPUT  type= "hidden" class= Common name= fmtransact  value= "">
          	 
  </form>
  
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br><br><br><br>
</body>
</html>
