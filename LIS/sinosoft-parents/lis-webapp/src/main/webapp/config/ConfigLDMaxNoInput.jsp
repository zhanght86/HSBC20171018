<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2005-01-26 13:18:16
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <script src="../common/javascript/MultiCom.js"></script>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>  
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
 <SCRIPT src="../common/javascript/jquery-1.7.2.js"></SCRIPT> 

	<SCRIPT src="../common/javascript/jquery.easyui.min.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <link rel="stylesheet" type="text/css" href="../common/themes/default/easyui.css">
  <link rel="stylesheet" type="text/css" href="../common/themes/icon.css">
  <link href="../common/css/mulLine.css" rel="stylesheet" type="text/css">
  <%@include file="ConfigLDMaxNoInit.jsp"%>
  <SCRIPT src="ConfigLDMaxNoInput.js"></SCRIPT>
</head>
<body  onload="initForm();initElementtype();" >
 
  <form action="./ConfigLDMaxNoSave.jsp" method=post name=fm id=fm target="fraSubmit">
  <Div id="NativeConfig" height=500>
  <div class="maxbox1">
   <table  class=common border=0>
		<tr class="common">
			
				<td CLASS="title5">�Ŷα���</td>
			<td CLASS="input5">
				<input name="NoCodeQuery" id="NoCodeQuery"  class="common wid" >
			</td>
			<td class="title5">�Ŷ�����</td>
			<td class="input5">
				<input class="common wid" name="NoNameQuery" id="NoNameQuery">
			</td>
			
		</tr>
	</table></div>
	<INPUT class=cssButton name="addbutton" VALUE="��    ѯ"  TYPE=button onclick="query();">
	<INPUT class=cssButton name="addbutton" VALUE="ˢ    ��"  TYPE=button onclick="initForm();">
    <table>
    	<tr>
    		<td class=common>
    		     <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" >
    		</td>
    		 <td class= titleImg>
        		 �Ŷθ�ʽ����
       		 </td>   		 
    	</tr>
    </table>
		 <div id="divMulCalendar" style="display:''">
        <table class="common">
            <tr class="common">
                <td style="text-align:left" colSpan="1">
                    <span id="spanMaxNoGrid">
                    </span>
                </td>
            </tr>
        </table>
    </div>	

     
	
		 <table>
    	<tr>
    		<td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
    		 <td class= titleImg>
        		 ��ϸ��Ϣ����
       		 </td>   		 
    	</tr>
    </table>
<Div  id= "divPayPlan1" style= "display: ''" class="maxbox1">
	<table class="common" id="table2">
		<tr CLASS="common">
			<td CLASS="title5">�Ŷα���</td>
			<td CLASS="input5" COLSPAN="1">
				<input name="NoCode" id="NoCode"  class="common wid" elementtype=nacessary verify="�Ŷα���|notnull">
			</td>
			<td class="title5">�Ŷ�����</td>
			<td class="input5">
				<input class="common wid" elementtype=nacessary name="NoName" id="NoName" verify="�Ŷ�����|notnull"></td></tr>
             <tr CLASS="common">   
			<td CLASS="title5">����Ԥ��</td>
			<td CLASS="input5">
				<input NAME="ShowRule" id="ShowRule" class="common wid" readonly >
			</td>	
            <td CLASS="title5">����</td>
			<td CLASS="input5">
				
                <Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});"  elementtype=nacessary verify="����|notnull&Date" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
			</td>
		</tr>
		<tr class="common"> 
			
			<td class="title5">ֹ��</td>
			<td class="input5">
				
                <Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
			<td CLASS="title5"></td>
			<td CLASS="input5">
				<inputclass="common" readonly >
			</td>	
		</tr>
	</table>
</div>	
    <input type=hidden id="fmtransact" name="fmtransact">
    <input type=hidden id="fmAction" name="fmAction">
    
 <div id="w" class="easyui-window" title="�Ŷ����ɹ�������" iconCls="icon-save" style="width:850px;height:450px;padding:0px;">
				 <!--iframe id= "IbrmsDetail" src="./ConfigLDMaxNoRule.jsp" width=100% height=500 SCROLLING='No' >
				 </iframe--> 
</div>
	
</Div>
    <INPUT class=cssButton name="addbutton" VALUE="��    ��"  TYPE=button onclick="return submitForm();">
	<INPUT class=cssButton name="addbutton" disabled VALUE="ɾ    ��"  TYPE=button onclick="return submitForm();">
	<INPUT class=cssButton name="addbutton" VALUE="�Ŷι�������"  TYPE=button onclick="return configRule();">
	<INPUT class=cssButton name="addbutton" VALUE="�Ŷ�Լ������"  TYPE=button onclick="return configRuleLimit();">
  </form>
  <br /><br /><br /><br />
<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
