<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%> 
<%
//�������ƣ�QuestInput.jsp
//�����ܣ������¼��
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
   <%@page import="com.sinosoft.lis.vschema.*"%>
   <%@page import="com.sinosoft.lis.db.*"%>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="BQQuestInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>�˱������¼��</title>
  <%@include file="BQQuestInputInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form method=post name=fm id=fm target="fraSubmit" action= "./BQQuestInputChk.jsp">
  <table >
		<tr>
			<td class=common>
				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,asd);">
			</td>
			<td class= titleImg>�������������Ϣ</td>
		</tr>
	</table>
	<table>
 <Div  id= "asd" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  				<span id="spanQuestGrid">
  				</span> 
  		  	</td>
  		</tr>
    	</table>
        <center>
    	<input CLASS=cssButton90 VALUE="��  ҳ" TYPE=button onClick="turnPage.firstPage();"> 
      <input CLASS=cssButton91 VALUE="��һҳ" TYPE=button onClick="turnPage.previousPage();"> 					
      <input CLASS=cssButton92 VALUE="��һҳ" TYPE=button onClick="turnPage.nextPage();"> 
      <input CLASS=cssButton93 VALUE="β  ҳ" TYPE=button onClick="turnPage.lastPage();"></center>
    </div>
  </table>		
  <table  class=common >
		<tr class=common>
			<td class=common>
				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);">
			</td>
			<td class= titleImg>�������Ϣ</td>
		</tr>
	</table>
    <Div  id= "divPayPlan1" style= "display: ''" class="maxbox1">
	<table class = common>
    <tr class = common>
	        <TD class= title>
            ���Ͷ���  
          </TD>
          <TD class="input">
          <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= "codeno" name = BackObj id = BackObj readonly onDblClick="return showCodeList('bqbackobj',[this,BackObjName],[0,1]);" onClick="return showCodeList('bqbackobj',[this,BackObjName],[0,1]);" onKeyUp="return showCodeListKey('bqbackobj',[this,BackObjName],[0,1]);"><Input class = codename name=BackObjName id=BackObjName readonly = true>
          </TD> 
          
          <TD class= title>
    	  		�Ƿ��·�
    			</TD>
        	<TD class="input" >
            <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=NeedPrintFlag id=NeedPrintFlag CodeData = "0|^Y|�·�^N|���·�" ondblclick= "showCodeListEx('IFNeedFlag',[this,IFNeedFlagName],[0,1]);" onclick= "showCodeListEx('IFNeedFlag',[this,IFNeedFlagName],[0,1]);" onKeyUp="showCodeListKeyEx('IFNeedFlag',[this,IFNeedFlagName],[0,1]);" ><Input class=codename  name=IFNeedFlagName  id=IFNeedFlagName readonly = true>
        	</TD>
        	<!-- <TD  class= title>
    	  		�������
    			</TD> 
        	<TD   class= input>--><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" type=hidden class="hidden" name=ErrorType id=ErrorType  CodeData = "0|^1|�����뿼��^2|���Ѽ�������^3|������Ͷ������^4|��֪©��^5|��������^6|�ƻ���д^7|ǩ����Ϣ^8|���֤��^9|ְҵ��д^10|����" ondblclick= "showCodeListEx('ErrorType',[this,ErrorTypeName],[0,1]);" onclick= "showCodeListEx('ErrorType',[this,ErrorTypeName],[0,1]);" onKeyUp="showCodeListKeyEx('ErrorType',[this,ErrorTypeName],[0,1]);" ><Input class=hidden type=hidden name=ErrorTypeName id=ErrorTypeName readonly ="readonly" >
          <TD class=title> ���ն��� </TD>  
          <TD class="input"  ><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= "codeno" name = QuestionObj id=QuestionObj onDblClick="return showCodeList('question',[this,QuestionObjName],[0,1]);" onClick="return showCodeList('question',[this,QuestionObjName],[0,1]);" onKeyUp="return showCodeListKey('question',[this,QuestionObjName],[0,1]);" ><Input class = codename name=QuestionObjName id=QuestionObjName readonly = true> </TD></tr>
        </table>
        </Div>
		<div id= "divCustomerqustion" style= "display: none" >
    <table class = common >
    	<TR  class= common>
    		  
          
         
                     
          <TD  class = title> �ͻ����� </TD>
          <TD class="input"  ><Input class="wid" class=common  name=CustomerNo id=CustomerNo ></TD>
           
          <TD  class = title>	�ͻ����� </TD>
          <TD class="input"  ><Input class="wid" class=common  name=CustomerName id=CustomerName ></TD>
          <TD  class= title>
    	  �����ѡ��
    	</TD>
        <TD class="input" ><Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat center rihgt" class=code name=Quest id=Quest readonly 
        	ondblclick= "getClickIssueCode(Quest,Content);" onclick= "getClickIssueCode(Quest,Content);" onKeyUp="getKeyUpIssueCode(Quest,Content);" >
        </TD>
          </TR>     
          
     </table>
    </div>
    
    
   
    <div id ="divQuestionnaireFlag" style ="display :none">
     <table class = common >
         <tr class=common>
        	<TD  class= title>
    	 	 �Ƿ�¼���ʾ�
    		</TD><TD  class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=Questionnaire id=Questionnaire  CodeData = "0|^Y|��^N|��" ondblclick= "showCodeListEx('Questionnaire',[this,QuestionnaireName],[0,1]);" onclick= "showCodeListEx('Questionnaire',[this,QuestionnaireName],[0,1]);" onKeyUp="showCodeListKeyEx('Questionnaire',[this,QuestionnaireName],[0,1]);" ><Input class=codename  name=QuestionnaireName id=QuestionnaireName readonly ="readonly" >
        	</TD>
            <TD  class= title></TD>
            <TD  class= input></TD><TD  class= title></TD>
            <TD  class= input></TD>
         </tr>
        </div>
     
    <div id="divQuestionnaire" style="display:none">
    <table class=common>
  <jsp:include page="Questionnaire.jsp"/>
  </table>
  </div>
  <table class = common>
    <TR  class= common> 
      <TD  class= title> ��������� </TD>
    </TR>
    <TR  class= common>
      <TD colspan="6" style="padding-left:16px"><textarea name="Content" id="Content" cols="200" rows="4" class="common"></textarea></TD>
    </TR>
  </table>
  <p> 
    <!--��ȡ��Ϣ-->
    <input type= "hidden" id="Flag" name= "Flag" value="">
    <input type= "hidden" id="ContNo" name= "ContNo" value= "">
    <input type= "hidden" id="MissionID" name= "MissionID" value= "">
    <input type= "hidden" id="SubMissionID" name= "SubMissionID" value= "">
    <input type= "hidden" id="hiddenQuestionOperator" name= "hiddenQuestionOperator" value="">
    <input type= "hidden" id="hiddenQuestionSeq" name= "hiddenQuestionSeq" value="">
    <input type= "hidden" id="hiddenProposalContNo" name= "hiddenProposalContNo" value="">
    <input type= "hidden" id="hiddenBackObj" name= "hiddenBackObj" value="">
    <input type= "hidden" id="hiddenQuestionPrint" name= "hiddenQuestionPrint" value="">
    <input type= "hidden" id="hiddenQuestionState" name= "hiddenQuestionState" value="">
    <input type= "hidden" id="FinanceO" name= "FinanceO" value="">
    <input type= "hidden" id="Occupation" name= "Occupation" value="">
    <input type= "hidden" id="HealthCheck" name= "HealthCheck" value="">
    <input type= "hidden" id="EdorNo" name= "EdorNo" value="">
    <input type= "hidden" id="EdorType" name= "EdorType" value="">
     
  </p>
</form>
  
<span id="spanCode"  style="display: none; position:absolute; slategray"></span> 
<input type= "button" id="sure" name= "sure" class= cssButton  value="�� ��" onClick="submitForm('0')">
<!--input type= "button" name= "sure" class= cssButton  value="ɾ ��" onClick="submitForm('1')"-->
<input type= "button" id="sure" name= "sure" class= cssButton  value="�޸��·����" onClick="submitForm('2')">
<input type= "button" id="sure" name= "sure" class= cssButton  value="�� ��" onClick="top.close();"><br><br><br><br>
</body>
</html>
