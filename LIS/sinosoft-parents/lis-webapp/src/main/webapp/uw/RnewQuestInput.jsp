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
  <SCRIPT src="RnewQuestInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>�˱������¼��</title>
  <%@include file="RnewQuestInputInit.jsp"%>
<script language="javascript">
	var LoadFlag ="<%=tFlag%>"; //�жϴӺδ����뱣��¼�����,�ñ�����Ҫ�ڽ����ʼ��ǰ����
  var tMissionID = "<%=tMissionID%>";
  var tSubMissionID = "<%=tSubMissionID%>";
  var tActivityID = "<%=tActivityID%>";
  var tContNo ="<%=tContNo%>";
  var tFlag ="<%=tFlag%>";
</script>  
</head>
<body  onload="initForm('<%=tContNo%>','<%=tFlag%>');" >
  <form method=post name=fm id="fm" target="fraSubmit" action= "./RnewQuestInputChk.jsp">
  <table >
		<tr>
			<td class=common>
				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;">
			</td>
			<td class= titleImg>�������������Ϣ</td>
		</tr>
	</table>
	<table>
 <Div  id= "divUWSpec1"  style= "display: " align=center>
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
      <input CLASS=cssButton93 VALUE="β  ҳ" TYPE=button onClick="turnPage.lastPage();">
    </center>
    </div>
  </table>		
  <table >
		<tr>
			<td class=common>
				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;">
			</td>
			<td class= titleImg>�������Ϣ</td>
		</tr>
	</table>
    <div class="maxbox">
	<table class = common>
	        <TD  class= title5>
            ���Ͷ���  
          </TD>
          <TD class="input5">
          &nbsp;&nbsp;&nbsp;<Input class= "codeno" id="BackObj" name = BackObj id="BackObj" style="background: url(../common/images/select--bg_03.png) no-repeat center right; "  CodeData=""; onClick="return showCodeList('rnbackobj',[this,BackObjName],[0,1]);" onDblClick="return showCodeList('rnbackobj',[this,BackObjName],[0,1]);" onKeyUp="return showCodeListKey('rnbackobj',[this,BackObjName],[0,1]);"><Input class = codename name=BackObjName id="BackObjName" readonly = true>
          </TD> 
          
          <TD  class= title5>
    	  		�Ƿ��·�
    			</TD>
        	<TD class="input5" >
            <Input class="codeno" name=NeedPrintFlag id="NeedPrintFlag" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onClick="showCodeListEx('IFNeedFlag',[this,IFNeedFlagName],[0,1]);" CodeData = "0|^Y|�·�^N|���·�" ondblclick= "showCodeListEx('IFNeedFlag',[this,IFNeedFlagName],[0,1]);" onKeyUp="showCodeListKeyEx('IFNeedFlag',[this,IFNeedFlagName],[0,1]);" ><Input class=codename  name=IFNeedFlagName id="IFNeedFlagName" readonly = true>
        	</TD>
        	<!-- <TD  class= title>
    	  		�������
    			</TD> -->
        	<TD  class= input><Input type=hidden class="hidden" id="ErrorType" name=ErrorType  CodeData = "0|^1|�����뿼��^2|���Ѽ�������^3|������Ͷ������^4|��֪©��^5|��������^6|�ƻ���д^7|ǩ����Ϣ^8|���֤��^9|ְҵ��д^10|����" ondblclick= "showCodeListEx('ErrorType',[this,ErrorTypeName],[0,1]);" onKeyUp="showCodeListKeyEx('ErrorType',[this,ErrorTypeName],[0,1]);" ><Input class=hidden type=hidden id="ErrorTypeName" name=ErrorTypeName readonly ="readonly" >
        	</TD>
      
        </table>
		<div id= "divCustomerqustion" style= "display: none" >
    <table class = common >
    	<TR  class= common>
    		  
          
          <TD class=title> ���ն��� </TD>  
          <TD  ><Input class= "codeno" name = QuestionObj id="QuestionObj" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onClick="return showCodeList('question',[this,QuestionObjName],[0,1]);" onDblClick="return showCodeList('question',[this,QuestionObjName],[0,1]);" onKeyUp="return showCodeListKey('question',[this,QuestionObjName],[0,1]);" ><Input class = codename name=QuestionObjName id="QuestionObjName" readonly = true> </TD>
                     
          <TD  class = title> �ͻ����� </TD>
          <TD  ><Input class="common wid"  name=CustomerNo id="CustomerNo" ></TD>
           
          <TD  class = title>	�ͻ����� </TD>
          <TD  ><Input class="common wid"  name=CustomerName id="CustomerName" ></TD>
          </TR>     
          
     </table>
    </div>
    
    
   <table class = common>
    <tr class =common>
        <TD  class= title5>
    	  �����ѡ��
    	</TD>
        <TD class="input5"><Input class="code wid" name=Quest id="Quest" readonly  style="background: url(../common/images/guanliyuan-bg.png) no-repeat center right;" onClick="getClickIssueCode(Quest,Content);"
        ondblclick= "getClickIssueCode(Quest,Content);" onKeyUp="getKeyUpIssueCode(Quest,Content);" >
        </TD>
        <TD  class= title5></TD>
        <TD class="input5"></TD>
      </tr>
    </table>
    <div id ="divQuestionnaireFlag" style ="display :none">
         <tr class=common>
        	<TD  class= title>
    	 	 �Ƿ�¼���ʾ�
    		</TD><TD  class= input><Input class="codeno" name=Questionnaire id="Questionnaire" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " value= "N"   CodeData = "0|^Y|��^N|��" onClick="showCodeListEx('Questionnaire',[this,QuestionnaireName],[0,1]);" ondblclick= "showCodeListEx('Questionnaire',[this,QuestionnaireName],[0,1]);" onKeyUp="showCodeListKeyEx('Questionnaire',[this,QuestionnaireName],[0,1]);" ><Input class=codename  name=QuestionnaireName id="QuestionnaireName" value="��" readonly ="readonly" >
        	</TD>
         </tr>
        </div>
      <hr class="line">
  
    <div id="divQuestionnaire" style="display:none">
    <table class=common>
  <jsp:include page="Questionnaire.jsp"/>
  </table>
  </div>
  <table class = common align = center width="121%" height="37%">
    <TR  class= common> 
      <TD width="100%" height="13%"  class= title> ��������� </TD>
    </TR>
    <TR  class= common>
      <TD height="87%"  class= title><textarea name="Content" cols="135" rows="10" class="common"></textarea></TD>
    </TR>
  </table>
  <p> 
    <!--��ȡ��Ϣ-->
    <input type= "hidden" id="Flag" name= "Flag" value="">
    <input type= "hidden" id="ContNo" name= "ContNo" value= "">
    <input type= "hidden" id="MissionID" name= "MissionID" value= "">
    <input type= "hidden" id="SubMissionID" name= "SubMissionID" value= "">
    <input type= "hidden" id="ActivityID" name= "ActivityID" value= "">
    <input type= "hidden" id="hiddenQuestionOperator" name= "hiddenQuestionOperator" value="">
    <input type= "hidden" id="hiddenQuestionSeq" name= "hiddenQuestionSeq" value="">
    <input type= "hidden" id="hiddenProposalContNo" name= "hiddenProposalContNo" value="">
    <input type= "hidden" id="hiddenBackObj" name= "hiddenBackObj" value="">
    <input type= "hidden" id="hiddenQuestionPrint" name= "hiddenQuestionPrint" value="">
    <input type= "hidden" id="hiddenQuestionState" name= "hiddenQuestionState" value="">
    <input type= "hidden" id="FinanceO" name= "FinanceO" value="">
    <input type= "hidden" id="Occupation" name= "Occupation" value="">
    <input type= "hidden" id="HealthCheck" name= "HealthCheck" value="">
     
  </p>
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span> 
<input type= "button" id="sure" name= "sure" class= cssButton  value="�� ��" onClick="submitForm('0')">
<!--input type= "button" name= "sure" class= cssButton  value="ɾ ��" onClick="submitForm('1')"-->
<input type= "button" id="sure" name= "sure" class= cssButton  value="�޸��·����" onClick="submitForm('2')">
  <br><br><br><br><br>

</body>
</html>
