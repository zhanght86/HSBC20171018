<html>
<%
//�������� :FIRulePlanDefInput.jsp
//������ :У��ƻ�����
//������ :���
//�������� :2008-09-16
//
%>


	<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
	<!--�û�У����-->
	<%@page import = "com.sinosoft.utility.*"%>
	<%@page import = "com.sinosoft.lis.schema.*"%>
	<%@page import = "com.sinosoft.lis.vschema.*"%>
	<%@page import = "com.sinosoft.lis.pubfun.*"%>
	<%@include file="../common/jsp/UsrCheck.jsp"%>
	<%
	
  GlobalInput tGI1 = new GlobalInput();
  tGI1=(GlobalInput)session.getValue("GI");//���ҳ��ؼ��ĳ�ʼ����
  
 	%>
<script>
  var comcode = "<%=tGI1.ComCode%>";
</script>
<head >
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT> 
<SCRIPT src = "FIRulePlanDefInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="FIRulePlanDefInputInit.jsp"%>
</head>
<body  onload="initForm();initElementtype();">
  <form action="./FIRulePlanDefSave.jsp" method=post name=fm id=fm target="fraSubmit">
  	  <Div id= "divFIRulePlanDef" style= "display: ''">
  <table>
    	<tr>
        	 <td class=common><IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,misad);"></td>
    		 <td class= titleImg>
        		У��ƻ�����
       		 </td>   		 
    	</tr>
    </table>
    <Div id="misad" style="display: ''"><div class="maxbox1">
    <td class=button width="10%" align=right>
				
	</td>
<table class= common border=0 width=100%>
  <table class= common>
	<tr class= common>
				 <TD class= title5>
					  �汾���
				 </TD>
				 <TD class=input5>
				 	<input class="wid" class=readonly name=VersionNo id=VersionNo readonly=true>
				</TD>
				<TD class= title5>
		   	   	�汾״̬
		    	</TD>
		    	<TD class= input5>
		    	<input class="wid" class=readonly name=VersionState2 id=VersionState2 readonly=true>
		   		 </TD>
	</tr>
	</table>
    </div>
    </div>
   
	<!--<INPUT class=cssButton name="querybutton" VALUE="�汾��Ϣ��ѯ"  TYPE=button onclick="return VersionStateQuery();">-->
	<a href="javascript:void(0);" name="querybutton" class="button" onClick="return VersionStateQuery();">�汾��Ϣ��ѯ</a><br><br>
    <div class="maxbox1">
	<table class= common>
		<tr class= common>
				 <TD class= title5>
					  У��ƻ�����
				 </TD>
				 <TD class=input5>
				 	 <Input class="wid" class=readonly name=RulesPlanID id=RulesPlanID readonly=true>
				</TD>
				<TD class= title5>
					  У��ƻ�����
				 </TD>
				 <TD class=input5>
				 	<Input class="wid" class=common name=RulesPlanName id=RulesPlanName >
				</TD>
		</tr>
		<tr class= common>
				<TD class= title5>
					  У��ƻ�״̬
				 </TD>
				 <TD class=input5>
				 	 <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=RulePlanState id=RulePlanState verify=У��ƻ�״̬|NOTNULL" readonly=true  CodeData="0|^0|δ����^1|���� " ondblClick="showCodeListEx('RulePlanState',[this,RulePlanStateName],[0,1],null,null,null,[1]);" onMouseDown="showCodeListEx('RulePlanState',[this,RulePlanStateName],[0,1],null,null,null,[1]);" onkeyup="showCodeListKeyEx('RulePlanState',[this,RulePlanStateName],[0,1],null,null,null,[1]);"><input class=codename name=RulePlanStateName id=RulePlanStateName readonly=true elementtype = nacessary >
				</TD>
				<TD class= title5>
					  ���ýڵ�ID
				 </TD>
				 <TD class=input5>
		     	 	<Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=CallPointID id=CallPointID verify="���ýڵ�ID|NOTNULL"  ondblClick="showCodeList('callpoint',[this,CallPointIDName],[0,1],null,'CallPointID','codetype',[1]);" onMouseDown="showCodeList('callpoint',[this,CallPointIDName],[0,1],null,'CallPointID','codetype',[1]);" onkeyup="showCodeListKey('callpoint',[this,CallPointIDName],[0,1],null,'CallPointID','codetype',[1]);" ><input class=codename name=CallPointIDName id=CallPointIDName readonly=true elementtype=nacessary>					
		    	</TD>
	 </tr>
	 
	 <tr class= common>
    <TD class= title5>
					  ����
				 </TD>
				 <TD class=input5>
				 	 <Input class="wid" class=common name=MarkInfo idMarkInfo >
				</TD>
   </tr>
	</table>
		 
	</table>
    <INPUT VALUE="У��ƻ���Ϣ��ѯ" TYPE=button class= cssButton onclick="queryClick()">
	<INPUT VALUE="���" TYPE=button class= cssButton name= addbutton onclick="submitForm()">   
  <INPUT VALUE="�޸�" TYPE=button class= cssButton name= updatebutton onclick="updateClick()">
  <INPUT VALUE="ɾ��" TYPE=button class= cssButton name= deletebutton onclick="deleteClick()">
  <INPUT VALUE="����" TYPE=button class= cssButton name= resetbutton onclick="resetAgain()">
  <br><br>
   <!-- <a href="javascript:void(0);" class="button" onClick="queryClick();">У��ƻ���Ϣ��ѯ</a>
    <a href="javascript:void(0);" class="button" name="addbutton" onClick="submitForm();">��    ��</a>
    <a href="javascript:void(0);" class="button" name="updatebutton" onClick="updateClick();">��    ��</a>
    <a href="javascript:void(0);" class="button" name="deletebutton" onClick="deleteClick();">ɾ    ��</a>
    <a href="javascript:void(0);" class="button" name="resetbutton" onClick="resetAgain();">��    ��</a>-->
</Div>
</div>
<hr class="line"></hr>
			 <!--<INPUT class=cssButton name="FIRulePlanDefDetailInputbutton" VALUE="У��ƻ���ϸ����"  TYPE=button onclick="return FIRulePlanDefDetailInputClick();">-->
             <a href="javascript:void(0);" name="FIRulePlanDefDetailInputbutton" class="button" onClick="return FIRulePlanDefDetailInputClick();">У��ƻ���ϸ����</a>
 <input type=hidden id="OperateType" name="OperateType">
 <input type=hidden name="Sequence" value=''>
 <INPUT type=hidden name=VersionState value=''>
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
