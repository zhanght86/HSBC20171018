<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
	//�������ƣ�FinInterfaceCheck.jsp
	//�����ܣ�ƾ֤�˶�
	//�������ڣ�2007-10-23 
	//������  ��m
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*"%>

<%
	//���ҳ��ؼ��ĳ�ʼ����
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput) session.getValue("GI");
%>
<script>	
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
	var comcode = "<%=tGI.ComCode%>";//��¼��½����
</script>
<head>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryPrint.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="FinInterfaceCheck.js"></SCRIPT>

<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="FinInterfaceCheckInit.jsp"%>
</head>

<body onload="initForm();">
<form method=post name=fm id=fm target="fraSubmit">
<!-- ��ˮ���� --> 
<input type=hidden name=serialNo id=serialNo>
<input type=hidden name=ExportExcelSQL id=ExportExcelSQL>
<input type=hidden name=ClassType id=ClassType>
  <table>
    	<tr> 
        	<td class=common><IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divSearch);"></td>
    		<td class= titleImg>�������ѯ����</td>   		 
    	</tr>
  </table> 
    
<Div id="divSearch" style="display: ''">
<div class="maxbox1">
	<table class=common>
	<TR class=common>
		<TD class=title>�˶�����</TD>
		<TD class=input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=checkType id=checkType
			CodeData="0|^1|��Ŀ|M^2|ҵ�����|M^3|ƾ֤����|M^4|ƾ֤����|M" verify="�˶�����|notnull"
			ondblclick="showCodeListEx('checkType',[this,checkTypeName],[0,1]);"
            onMouseDown="showCodeListEx('checkType',[this,checkTypeName],[0,1]);"
			onkeyup="showCodeListKeyEx('checkType',[this,checkTypeName],[0,1]);"><input
			class=codename name=checkTypeName id=checkTypeName readonly=true elementtype=nacessary><font color=red>*</font></TD>	
		<TD class=title></TD>	
		<TD class=input></TD>		
		<TD class=title></TD>	
		<TD class=input></TD>			
	</TR>
	</table>
    </div>	
</Div>
	
<Div id="divSearch1" style="display:none"> 	
	<table class=common>
	<TR class=common>
		<TD class=title>��Ŀ����</TD>
		<TD class=input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=accountCodeType id=accountCodeType
			CodeData="0|^1|�ʲ�|M^2|��ծ|M^3|����" verify="��Ŀ����|notnull"
			ondblclick="showCodeListEx('accountCodeType',[this,accountCodeTypeName],[0,1]);"
            onMouseDown="showCodeListEx('accountCodeType',[this,accountCodeTypeName],[0,1]);"
			onkeyup="showCodeListKeyEx('accountCodeType',[this,accountCodeTypeName],[0,1]);"><input
			class=codename name=accountCodeTypeName id=accountCodeTypeName readonly=true elementtype=nacessary></TD>	
		<TD  class= title>��Ŀ</TD>
    	<TD  class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=accountCode id=accountCode verify="��Ŀ|NOTNULL" 
      		ondblclick="showCodeList('accountcode',[this,accountName],[0,1],null,[fm.accountCodeType.value],['othersign'],'1',null);" 
            onMouseDown="showCodeList('accountcode',[this,accountName],[0,1],null,[fm.accountCodeType.value],['othersign'],'1',null);" 
      		onkeyup="return showCodeListKey('accountcode',[this,accountName],[0,1],null,[fm.accountCodeType.value],['othersign'],'1',null);"><input
      		class=codename name=accountName id=accountName readonly=true elementtype=nacessary></TD>	
		<TD class=title>�����־</TD>
		<TD class=input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=FinItemType id=FinItemType
			CodeData="0|^D|�跽|M^C|����|M" verify="��Ŀ����|notnull"
			ondblclick="showCodeListEx('FinItemType',[this,FinItemTypeName],[0,1],null,null,null,1,null);"
            onMouseDown="showCodeListEx('FinItemType',[this,FinItemTypeName],[0,1],null,null,null,1,null);"
			onkeyup="showCodeListKeyEx('FinItemType',[this,FinItemTypeName],[0,1],null,null,null,1,null);"><input
			class=codename name=FinItemTypeName id=FinItemTypeName readonly=true elementtype=nacessary></TD>	      			
	</TR>
	<TR class=common>
		<TD  class= title>�������</TD>
      	<TD  class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=ManageCom value="86" id=ManageCom verify="�������|NOTNULL" 
      		ondblclick="showCodeList('comcode',[this,PolicyComName],[0,1],null,null,null,'1',null);" 
            onMouseDown="showCodeList('comcode',[this,PolicyComName],[0,1],null,null,null,'1',null);"
      		onkeyup="return showCodeListKey('comcode',[this,PolicyComName],[0,1],null,null,null,'1',null);"><input
      		class=codename name=PolicyComName id=PolicyComName readonly=true elementtype=nacessary></TD>		
		<TD class=title>��ʼʱ��</TD>
		<TD class=input>
		<Input  class="wid" onClick="laydate({elem: '#StartDay'});" verify="��ʼʱ��|notnull&date" dateFormat="short" name=StartDay id="StartDay"><span class="icon"><a onClick="laydate({elem: '#StartDay'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
		<TD class=title>����ʱ��</TD>
		<TD class=input>
			<Input  class="wid" onClick="laydate({elem: '#EndDay'});" verify="����ʱ��|notnull&date" dateFormat="short" name=EndDay id="EndDay"><span class="icon"><a onClick="laydate({elem: '#EndDay'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            </TD>
	</TR>		
	</table>	
</Div>	
 

<Div id="divSearch2" style="display:none"> 	
	<table class=common>  
	<TR class=common>
		<TD class=title>ҵ���������</TD>
		<TD class=input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=ContType id=ContType
			CodeData="0|^1|����Ͷ��ӡˢ��|M^3|���˱�����|M^5|�ݽ��Ѻ�|M^6|ʵ����|M^7|ʵ�պ�|M^8|���ձ�ȫ������
|M^10|�ⰸ��|M" verify="ҵ���������|notnull"
			ondblclick="showCodeListEx('ContType',[this,ContTypeName],[0,1],null,null,null,1,null);"
            onMouseDown="showCodeListEx('ContType',[this,ContTypeName],[0,1],null,null,null,1,null);"
			onkeyup="showCodeListKeyEx('ContType',[this,ContTypeName],[0,1],null,null,null,1,null);"><input
			class=codename name=ContTypeName id=ContTypeName readonly=true elementtype=nacessary></TD>		
        <TD  class= title>ҵ�����</TD>
        <TD  class= input><Input class= common name=tNo id=tNo></TD> 
        <TD  class= title></TD>
        <TD  class= input></TD>        			
	</TR>
	</table>	
</Div>

<Div id="divSearch3" style="display:none"> 	
	<table class=common>  
	<TR class=common>
		<TD class=title>ƾ֤����</TD>
		<TD  class= input>
      <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name="SClassType" id="SClassType"
      		ondblclick="showCodeListEx('classtype',[this,SClassTypeName],[0,1],null,null,null,1,null);" 
            onMouseDown="showCodeListEx('classtype',[this,SClassTypeName],[0,1],null,null,null,1,null);"
      		onkeyup="return showCodeListKeyEx('classtype',[this,SClassTypeName],[0,1],null,null,null,1,null);"><input
      		class=codename name=SClassTypeName id=SClassTypeName readonly=true elementtype=nacessary></TD>		
    </TD>	
    <TD class=title></TD>	
		<TD class=input></TD>		
		<TD class=title></TD>	
		<TD class=input></TD>	
  </TR>
  <TR> 
		<TD  class= title>�������</TD>
    <TD  class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno value="86" name=ManageCom1 id=ManageCom1 verify="�������|NOTNULL"  
      	 ondblclick="showCodeList('comcode',[this,PolicyComName],[0,1],null,null,null,'1',null);" 
         onMouseDown="showCodeList('comcode',[this,PolicyComName],[0,1],null,null,null,'1',null);" 
      	 onkeyup="return showCodeListKey('comcode',[this,PolicyComName],[0,1],null,null,null,'1',null);"><input
      	 class=codename name=PolicyComName1 id=PolicyComName1 readonly=true elementtype=nacessary>
    </TD>	  	 
		<TD class=title>��ʼ����</TD>
		<TD  class= input>
      <Input class="wid" onClick="laydate({elem: '#SDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=SDate id="SDate"><span class="icon"><a onClick="laydate({elem: '#SDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
    </TD>
    <TD class=title>��ֹ����</TD>
		<TD  class= input>
      <Input class="wid" onClick="laydate({elem: '#EDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=EDate id="EDate"><span class="icon"><a onClick="laydate({elem: '#EDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
    </TD>
	</TR>
	</table>	
</Div>
<Div id="divSearch4" style="display:none"> 	
	<table class=common>  
	<TR class=common>	
        <TD  class= title>ƾ֤����</TD>
        <TD  class= input><Input class= common name=AccountID id=AccountID></TD> 
        <TD  class= title></TD>
        <TD  class= input></TD>        		
        <TD  class= title></TD>
        <TD  class= input></TD>  	
	</TR>
	</table>	
</Div>

<!--<INPUT VALUE="�����ѯ" class=cssButton TYPE=button onclick="CheckQueryData();">
--><a href="javascript:void(0);" class="button" onClick="CheckQueryData();">�����ѯ</a>

<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divAllGrid);"></td>
		<td class=titleImg>��ѯ���</td>
	</tr>
</table>

<Div id="divAllGrid" style="display: ''" align=center>
<table class=common>
	<tr class=common>
		<td text-align: left colSpan=1><!-- �ս��������ɵ����� --> <span id="spanCheckQueryDataGrid"> </span></td>
	</tr>
</table>
<INPUT CLASS=cssButton90 VALUE="��  ҳ" TYPE=button
	onclick="turnPage.firstPage();"> <INPUT CLASS=cssButton91
	VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> <INPUT
	CLASS=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();">
<INPUT CLASS=cssButton93 VALUE="β  ҳ" TYPE=button
	onclick="turnPage.lastPage();">
</div>

<br>
<!--<INPUT VALUE="����Excel" class=cssButton TYPE=button onclick="ToExcel();">-->
<a href="javascript:void(0);" class="button" onClick="ToExcel();">����Excel</a>

<!--  <INPUT VALUE="��ӡ" class=cssButton TYPE=button onclick="printFinInterface();"> -->
<br>
<br>

</form>

<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br><br><br><br>
</body>
</html>
