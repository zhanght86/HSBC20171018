 <html>
<%
//�������� :FIRuleDefQueryInput.jsp
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
  var VersionNo = <%=request.getParameter("VersionNo")%>;
</script>
<head >
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT> 
<SCRIPT src = "FIRuleDefQueryInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="FIRuleDefQueryInputInit.jsp"%>
</head>
<body  onload="initForm();initElementtype();">
  <form action="" method=post name=fm id=fm target="fraSubmit">
  	<table>
    <tr class=common>
    <td class=common>
    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFIRuleDefQuery);">
    </IMG>
    <td class=titleImg>
      ��ѯ����
      </td>
    </tr>
  </table>
  
  <Div id= "divFIRuleDefQuery" style= "display: ''">
  <div class="maxbox">
 		<table class= common>
 			<tr class= common>
				 <TD class= title5>
					  �汾���
				 </TD>
				 <TD class=input5>
				 	 <Input class="wid" class=readonly name=VersionNo id=VersionNo readonly=true>
				</TD>
		</tr>
		<tr class= common>
				 <TD class= title5>
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
				 	 <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=RuleDealMode id=RuleDealMode verify="У�鴦��ʽ|NOTNULL"  CodeData="0|^1|�ദ��^2|SQL���� " ondblClick="showCodeListEx('RuleDealMode',[this,RuleDealModeName],[0,1],null,null,null,[1]);" onMouseDown="showCodeListEx('RuleDealMode',[this,RuleDealModeName],[0,1],null,null,null,[1]);" onkeyup="showCodeListKeyEx('RuleDealMode',[this,RuleDealModeName],[0,1],null,null,null,[1]);"><input class=codename name=RuleDealModeName id=RuleDealModeName readonly=true  >
				</TD>
	 </tr>	 
	</table>
		 <!--<input class="cssButton" type=button value="��  ѯ" onclick="QueryForm()">-->
   	<a href="javascript:void(0);" class="button" onClick="QueryForm();">��    ѯ</a> 
	</div>
	</Div>
  	<table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick="showPage(this,divFIRuleDefQueryGrid);">
    		</td>
    		<td class= titleImg>
    			 У�������Ϣ��ѯ���
    		</td>
    	</tr>
    </table>
		<Div  id= "divFIRuleDefQueryGrid" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  	<td text-align: left colSpan=1>
  					<span id="spanFIRuleDefQueryGrid" >
  					</span>
  			  	</td>
  			</tr>
    	</table>
        
        <!--<div align="left"><input class="cssButton" type=button value="��  ��" onclick="ReturnData()"></div>-->
       <center> 
      <INPUT VALUE="��  ҳ" TYPE=Button onclick="turnPage.firstPage();" class="cssButton90">
      <INPUT VALUE="��һҳ" TYPE=Button onclick="turnPage.previousPage();" class="cssButton91">
      <INPUT VALUE="��һҳ" TYPE=Button onclick="turnPage.nextPage();" class="cssButton92">
      <INPUT VALUE="β  ҳ" TYPE=Button onclick="turnPage.lastPage();" class="cssButton93"></center>
   </Div>
   <a href="javascript:void(0);" class="button" onClick="ReturnData();">��    ��</a>
   <input type=hidden id="OperateType" name="OperateType">
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
