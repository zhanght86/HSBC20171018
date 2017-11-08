<%@include file="../i18n/language.jsp"%>
<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //程序名称：PDUMInput.jsp
 //程序功能：险种核保规则定义
 //创建日期：2009-3-14
 //创建人  ：
 //更新记录：  更新人    更新日期     更新原因/内容
%>

<head>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>  
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <link rel="stylesheet" type="text/css" href="../common/themes/default/easyui.css">
  <link rel="stylesheet" type="text/css" href="../common/themes/icon.css">
 
  <%@include file="buttonshow.jsp"%> 
 <SCRIPT src="PDUM.js"></SCRIPT>
 <script src="../common/javascript/jquery.js"></script>
 <script src="../common/javascript/jquery.easyui.min.js"></script>
 <%@include file="PDUMInit.jsp"%>
 <%
	String tType = request.getParameter("Type");
	String tEdorType = request.getParameter("EdorType");
	System.out.println("tType:"+tType+":tEdorType:"+tEdorType);
%>

<script>
	var mType = '<%=tType%>';
	if(mType==null||mType==''||mType=='null')
	{
		mType  = null;
	}
	var mEdorType = '<%=tEdorType%>';
	<%session.setAttribute("LoadFlagButton1","1");
	System.out.println("loadFlagButton1:"+session.getAttribute("LoadFlagButton1"));
	%>
</script>	


<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDUMSave.jsp" method=post name=fm target="fraSubmit">
<table class=common>
	<tr class=common>
	<!--  
		<td class=title5>险种代码</td>-->
		
		<!--td class=title5>险种名称</td>
		<td class=input5>
			<input class=common name="RiskName" readonly="readonly" >
		</td-->
	</tr>
</table>
<table>
  <tr>
    <td class="titleImg" >核保规则查询:</td>
  </tr>
</table>
<table  class= common>
	<tr class= common>
    	 <!--TD  class= title>险种编码</TD>
        <TD  class= input>
            <Input class="codeno" name=RiskCode readonly="readonly" ondblclick="return showCodeList('RiskCode',[this,RiskCodeName],[0,1]);" onkeyup="return showCodeListKey('RiskCode',[this,RiskCodeName],[0,1]);"><input class=codename name=RiskCodeName readonly="readonly">
        </TD--> 
        <TD  class= title>核保编码</TD>
        <TD  class= input>
		<Input class=common name=UWCODE1 verify="核保编码|LEN<=10" >
        </TD> 
        <TD  class= title>险种代码</TD>
        <TD  class= input>
			<input class=common name="QueRiskCode"  >
        </TD>
        <TD  class= title>
关联保单类型
        </TD>
        <TD  class= input>
           <Input class="codeno" name=RELAPOLTYPE1 readonly="readonly"  ondblclick="return showCodeList('pd_relapoltype',[this,RELAPOLTYPEName1],[0,1],null,'EDOR','ZT',1);" onkeyup="return showCodeListKey('pd_relapoltype',[this,RELAPOLTYPEName1],[0,1],null,'EDOR','ZT',1);"><input class=codename name=RELAPOLTYPEName1 readonly="readonly">
        </TD>
     </tr>
	<tr class= common>
		<TD  class= title>
业务模块
        </TD>
        <TD  class= input>
          <input class="codeno" name=STANDBYFLAG21  ondblclick="return showCodeList('ibrmsbusinessrule',[this,STANDBYFLAG2NAME1],[0,1]);" onkeyup="return showCodeListKey('ibrmsbusinessrule',[this,STANDBYFLAG2NAME1],[0,1]);"><input class="codename" name=STANDBYFLAG2NAME1 readonly="readonly">        
        </TD>
		<TD  class= title>
适用系统
        </TD>
        <TD  class= input>
          <input class="codeno" name=STANDBYFLAG11  ondblclick="return showCodeList('pd_systype',[this,STANDBYFLAG1NAME1],[0,1]);" onkeyup="return showCodeListKey('pd_systype',[this,STANDBYFLAG1NAME1],[0,1]);"><input class="codename" name=STANDBYFLAG1NAME1 readonly="readonly">        
        </TD>
	</tr>
</table>
<div align=left>
<input value="查  询" type=button  onclick="query()" class="cssButton" type="button" >	
</div>
<table>
  <tr>
    <td class="titleImg" >已有核保规则：</td>
  </tr>
</table>
<table  class= common>
   <tr  class= common>
      <td style="text-align:left;" colSpan=1>
     <span id="spanMulline10Grid" >
     </span> 
      </td>
   </tr>
</table>
<INPUT CLASS=cssbutton VALUE="首  页" TYPE=button onclick="Mulline10GridTurnPage.firstPage();"> 
<INPUT CLASS=cssbutton VALUE="上一页" TYPE=button onclick="Mulline10GridTurnPage.previousPage();">      
<INPUT CLASS=cssbutton VALUE="下一页" TYPE=button onclick="Mulline10GridTurnPage.nextPage();"> 
<INPUT CLASS=cssbutton VALUE="尾  页" TYPE=button onclick="Mulline10GridTurnPage.lastPage();">
</BR></BR>
<!--input value="进入算法定义" type=button  onclick="button163( )" class="cssButton" type="button" -->

<table>
  <tr>
    <td class="titleImg" >核保规则定义:</td>
  </tr>
</table>
<table  class= common>
	<tr class= common>
    	 <!--TD  class= title>险种编码</TD>
        <TD  class= input>
            <Input class="codeno" name=RiskCode readonly="readonly" ondblclick="return showCodeList('RiskCode',[this,RiskCodeName],[0,1]);" onkeyup="return showCodeListKey('RiskCode',[this,RiskCodeName],[0,1]);"><input class=codename name=RiskCodeName readonly="readonly">
        </TD--> 
        <TD  class= title>核保编码</TD>
        <TD  class= input>
						<Input class=common name=UWCODE verify="核保编码|NOTNUlL&LEN<=10&LEN>=6" >
        </TD> 
        <TD  class= title>险种代码</TD>
        <TD  class= input>
			<input class=common name="RiskCode"  >
        </TD>
        <TD  class= title>
关联保单类型
        </TD>
        <TD  class= input>
           <Input class="codeno" name=RELAPOLTYPE readonly="readonly" verify="关联保单类型|NOTNUlL" ondblclick="return showCodeList('pd_relapoltype',[this,RELAPOLTYPEName],[0,1],null,'EDOR','ZT',1);" onkeyup="return showCodeListKey('pd_relapoltype',[this,RELAPOLTYPEName],[0,1],null,'EDOR','ZT',1);"><input class=codename name=RELAPOLTYPEName readonly="readonly">
        </TD>
      </tr>
      <tr class=common>
         <TD  class= title>
核保类型
        </TD>
        <TD  class= input>
           <Input class="codeno" name=UWTYPE readonly="readonly"   ondblclick="return showCodeList('pd_uwtype',[this,UWTYPEName],[0,1],null,'<%=tType%>','<%=tEdorType%>',1);" onkeyup="return showCodeListKey('pd_uwtype',[this,UWTYPEName],[0,1],null,'<%=tType%>','<%=tEdorType%>',1);"><input class=codename name=UWTYPEName readonly="readonly">

        </TD>
      	<TD  class= title>
核保顺序号
        </TD>
        <TD  class= input>
          <input class="common"    name="UWORDER"  verify="核保顺序号|NOTNUlL&NUM">
        </TD>
        
    	 <TD  class= title>提示信息
        </TD>
        <TD  class= input>
        	<input class="common"   name="REMARK">
        
        </TD>
    	</tr>
    	
      <tr class=common>
            	 <TD  class= title>校验提醒
        </TD>
        <TD  class= input>
        	<input class="common"   name="VALIFLAG">
        </TD>
        <!--  
        <TD  class= title>
          算法编码
        </TD>
        <TD  class= input>
          <input class="common"    name="CALCODE" verify="算法编码|LEN>=6">
        </TD>-->
                <TD  class= title>
业务模块
        </TD>
        <TD  class= input>
          <input class="codeno" name=STANDBYFLAG2  ondblclick="return showCodeList('ibrmsbusinessrule',[this,STANDBYFLAG2NAME],[0,1]);" onkeyup="return showCodeListKey('ibrmsbusinessrule',[this,STANDBYFLAG2NAME],[0,1]);"><input class="codename" name=STANDBYFLAG2NAME readonly="readonly">        
        </TD> 
      <TD  class= title>
适用系统
        </TD>
        <TD  class= input>
          <input class="codeno" name=STANDBYFLAG1 verify="适用系统|NOTNUlL" ondblclick="return showCodeList('pd_systype',[this,STANDBYFLAG1NAME],[0,1]);" onkeyup="return showCodeListKey('pd_systype',[this,STANDBYFLAG1NAME],[0,1]);"><input class="codename" name=STANDBYFLAG1NAME readonly="readonly">        
        </TD>
        </tr>
</table>
<!--算法定义引用页-->
<jsp:include page="CalCodeDefMain.jsp?RuleType=0"/>

<table  class= common>
   <tr  class= common>
      <td style="text-align:left;" colSpan=1>
     <span id="spanMulline9Grid" >
     </span> 
      </td>
   </tr>
</table>
	<br>
	<div align=center>
		<input value="重  置" type=button  onclick="initForm()" class="cssButton" type="button" >	
		<input value="保  存" type=button  onclick="save()" class="cssButton" type="button" >
		<input value="修  改" type=button  onclick="update()" class="cssButton" type="button" >
		<input value="删  除" type=button  onclick="del()" class="cssButton" type="button" >
	</div>
	
	<input value="记事本" type=hidden  onclick="button164( )" class="cssButton" type="button" >
	<input value="问题件查询" type=hidden  onclick="button165( )" class="cssButton" type="button" >
	<!--input value="返  回" type=button  onclick="returnParent( )" class="cssButton" type="button" -->
	<br>
	<br>
	
	<input  type=hidden   name="CALCODE">
	<input type=hidden name="operator">
	<input type=hidden name="tableName">
	<input type=hidden name=IsReadOnly>
</form>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
