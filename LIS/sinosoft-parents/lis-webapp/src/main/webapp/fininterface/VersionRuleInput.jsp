<html>
<%
//�������� :VersionRuleInput.jsp
//������ :�������汾����
//������ :���
//�������� :2008-08-21
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

<SCRIPT src = "VersionRuleInput.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="VersionRuleInputInit.jsp"%>
</head>
<body  onload="initForm();initElementtype();">
  <form action="./VersionRuleSave.jsp" method=post name=fm id=fm target="fraSubmit">
  	  <Div id= "divVersionRule" style= "display: ''">
  <table>
    	<tr>
        	<td class="common">
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divVer);">
                </td>
    		 <td class= titleImg>
        		�������汾����
       		 </td>   		 
    	</tr>
    </table>
    
     <Div id= "divVer" style= "display: ''"><div class="maxbox1">
  
<table class= common border=0 width=100%>
  <table class= common>
	<tr class= common>
				<TD class= title5>
					  �汾���
				</TD>
				<TD class=input5>
				 	<input class=wid name=VersionNo id=VersionNo readonly=true>
				</TD>
				<TD  class= title5>
          ��Ч����
        </TD>
        <TD  class= input5>
          
         <!-- <Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=StartDate id="StartDate elementtype=nacessary"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>-->
          <Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </TD>
	</tr>
	<tr class= common>
				<TD class= title5>
					  �汾����
				</TD>
				<TD class=input5>
				 	<input class=wid name=VersionReMark id=VersionReMark elementtype=nacessary>
				</TD>
	</tr>	
	</table>
   	</div>
    </div>
    <!--<INPUT class=cssButton name="querybutton" id="querybutton" VALUE="�汾��Ϣ��ѯ"  TYPE=button onclick="return RulesVersionQuery();">
	<INPUT VALUE="��Ӱ汾" TYPE=button class= cssButton name= addVersionbutton id= addVersionbutton onclick="addVersion()">
	<INPUT VALUE="ɾ���汾" TYPE=button class= cssButton name= deleteVersionbutton id= deleteVersionbutton onclick="deleteVersion()">-->
    <a href="javascript:void(0);" name="querybutton" id="querybutton" class="button" onClick="return RulesVersionQuery();">�汾��Ϣ��ѯ</a>
    <a href="javascript:void(0);" name= addVersionbutton id= addVersionbutton class="button" onClick="addVersion();">��Ӱ汾</a>
    <a href="javascript:void(0);" name= deleteVersionbutton id= deleteVersionbutton class="button" onClick="deleteVersion();">ɾ���汾</a><br><br>
    
	
    <div class="maxbox1">
	
	<table class= common>
		<tr class= common>
				<TD class= title5>
		   	   ά�����
		    </TD>
		    <TD class= input5>
		  	<Input class=wid name=Maintenanceno id=Maintenanceno readonly=true>
		    </TD>
		    <TD class= title5>
		   	   ά������
		    </TD>
		    <TD class= input5>
		  	<Input class=wid name=MaintenanceReMark id=MaintenanceReMark elementtype=nacessary>
		    </TD>
		</tr>
  </table>
</table>
<!--<INPUT class=cssButton name="querybutton" id="querybutton" VALUE="�������汾ά���켣��ѯ"  TYPE=button onclick="return RulesVersionTraceQuery();">
<INPUT VALUE="�����޸�" TYPE=button class= cssButton name= applyAmendbutton id= applyAmendbutton onclick="applyAmend()">
<INPUT VALUE="�޸����" TYPE=button class= cssButton name= CompleteAmendbutton id= CompleteAmendbutton onclick="CompleteAmend()">
<INPUT VALUE="��������" TYPE=button class= cssButton name= cancelAmendbutton id= cancelAmendbutton onclick="cancelAmend()">--><br><br>
<a href="javascript:void(0);" name="querybutton" id="querybutton" class="button" onClick="return RulesVersionTraceQuery();">�������汾ά���켣��ѯ</a>
<a href="javascript:void(0);" name= applyAmendbutton id= applyAmendbutton class="button" onClick="applyAmend();">�����޸�</a>
<a href="javascript:void(0);" name= CompleteAmendbutton id= CompleteAmendbutton class="button" onClick="CompleteAmend();">�޸����</a>
<a href="javascript:void(0);" name= cancelAmendbutton id= cancelAmendbutton class="button" onClick="cancelAmend();">��������</a>
</Div>
</div>
 <input type=hidden id="OperateType" name="OperateType">
 <input type=hidden name=EndDate id=EndDate>
 <input type=hidden name=VersionState id=VersionState>
 <input type=hidden name=MaintenanceState id=MaintenanceState>
 <input type=hidden name=TraceVersionNo id=TraceVersionNo>
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br><br><br><br>
</body>
</html>
