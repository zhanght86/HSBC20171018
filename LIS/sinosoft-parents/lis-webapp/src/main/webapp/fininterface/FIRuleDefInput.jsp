<html>
<%
//�������� :FIRuleDefInput.jsp
//������ :У�������
//������ :���
//�������� :2008-09-17
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
<SCRIPT src = "FIRuleDefInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="FIRuleDefInputInit.jsp"%>
</head>
<body  onload="initForm();initElementtype();">
  <form action="./FIRuleDefSave.jsp" method=post name=fm id=fm target="fraSubmit">
  	  
  <table>
    	<tr> 
        	 <td class=common><IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divFIRuleDef);"></td>
    		 <td class= titleImg>
        		У�������
       		 </td>   		 
    	</tr>
    </table>
    <Div id= "divFIRuleDef" style= "display: ''"><div class="maxbox1">
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
	
	
    <table>
    	<tr> 
        	 <td class=common><IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divFIRul);"></td>
    		 <td class= titleImg>
        		У�����
       		 </td>   		 
    	</tr>
    </table>
    <Div id= "divFIRul" style= "display: ''"><div class="maxbox">
	<table class= common>
		<tr class= common>
				 <TD class= title>
					  У��������
				 </TD>
				 <TD class=input5>
				 	 <Input class="wid" class=readonly name=RuleID id=RuleID readonly=true >
				</TD>
				<TD class= title5>
					  У���������
				 </TD>
				 <TD class=input5>
				 	<Input class="wid" class=common name=RuleName id=RuleName >
				</TD>
		</tr>
		<tr class= common>
				<TD class= title5>
					  У�鴦��ʽ
				 </TD>
				 <TD class=input5>
				 	 <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=RuleDealMode id=RuleDealMode readonly=true verify="У�鴦��ʽ|NOTNULL"  CodeData="0|^1|�ദ��^2|SQL���� " ondblClick="showCodeListEx('RuleDealMode',[this,RuleDealModeName],[0,1],null,null,null,[1]);" onMouseDown="showCodeListEx('RuleDealMode',[this,RuleDealModeName],[0,1],null,null,null,[1]);" onkeyup="showCodeListKeyEx('RuleDealMode',[this,RuleDealModeName],[0,1],null,null,null,[1]);"><input class=codename name=RuleDealModeName id=RuleDealModeName readonly=true  >
				</TD>
	 </tr>
	 <table>
	 <tr  class= common>
				<TD  class= common>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;У�鷵������(���������Ե��Ƿ��ص�Ϊ�����ϵ�����
�����������ǲ����ϵ�ԭ��
���� �˺�Ϊ��/�˺Ų����ڵ���Ϣ
ֱ����Ϊ������Դ��Ϣ����)</TD>
			</tr>
			<tr  class= common>
				<TD  class= common style="padding-left:10px; padding-top:6px">
					<textarea name="RuleReturnMean" id="RuleReturnMean" verify="У�鷵������|len<4000" verifyorder="1" cols="170%" rows="4" witdh=50% class="common" ></textarea>
				</TD>
			</tr>
		</table>
	</table>
	</div>
    </div>
    <!--<INPUT VALUE="У�������Ϣ��ѯ" TYPE=button class= cssButton onclick="queryClick()">-->
    <a href="javascript:void(0);" class="button" onClick="queryClick();">У�������Ϣ��ѯ</a><br><br>
<div class="maxbox1">
	
	<Div  id= "classdiv" style= "display: none" align=left>
	<table class=common>
	 <tr class= common>
				<TD class= title5>
					  ����������
				 </TD>
				 <TD class=input5>
				 	 <Input class="wid" class=common name=RuleDealClass id=RuleDealClass >
				</TD> 
				<TD class=title5></TD>	
				<TD class=input5></TD>		
	</tr>
 </table> 	  
</div>	
<Div  id= "sqldiv" style= "display: none" align=left>
	<table class=common>
			<tr  class= common>
				<TD  class= common>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;У������SQL</TD>
			</tr>
			<tr  class= common>
				<TD  class= common style="padding-left:10px; padding-top:6px">
					<textarea name="RuleDealSQL" verify="У������SQL|len<4000" verifyorder="1" cols="170%" rows="4" witdh=50% class="common" ></textarea>
				</TD>
			</tr>
			
		</table>
</div>	
	<INPUT VALUE="���" TYPE=button class= cssButton name= addbutton onclick="submitForm()">   
  <INPUT VALUE="�޸�" TYPE=button class= cssButton name= updatebutton onclick="updateClick()">
  <INPUT VALUE="ɾ��" TYPE=button class= cssButton name= deletebutton onclick="deleteClick()">
  <INPUT VALUE="����" TYPE=button class= cssButton name= resetbutton onclick="resetAgain()">
  <!--<a href="javascript:void(0);" class="button" name="addbutton" onClick="submitForm();">��    ��</a>
    <a href="javascript:void(0);" class="button" name="updatebutton" onClick="updateClick();">��    ��</a>
    <a href="javascript:void(0);" class="button" name="deletebutton" onClick="deleteClick();">ɾ    ��</a>
    <a href="javascript:void(0);" class="button" name="resetbutton" onClick="resetAgain();">��    ��</a>-->
	</table>
</Div>
</div>
 <input type=hidden id="OperateType" name="OperateType">
 <input type=hidden name="Sequence" value=''>
 <INPUT type=hidden name=VersionState value=''>
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br><br><br><br>
</body>
</html>
