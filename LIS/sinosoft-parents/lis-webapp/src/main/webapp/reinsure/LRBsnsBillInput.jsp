<%@include file="/i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
//�������ƣ�LRBsnsBillInput.jsp
//�����ܣ�
//�������ڣ�2007-2-8
//������  ��zhangbin
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.lis.pubfun.*" %> 

<head>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT><SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT><SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
	<SCRIPT src="LRBsnsBillInput.js"></SCRIPT> 
	<%@include file="LRBsnsBillInit.jsp"%>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css><LINK href="../common/css/Project3.css" rel=stylesheet type=text/css> 
</head>

<body onload="initElementtype();initForm();">    
  <form action="" method=post name=fm target="fraSubmit" >
    <div style="width:200">
      <table class="common">
        <tr class="common">
          <td class="common"><img  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCessGetData);"></td>
          <td class="titleImg">ҵ���ʵ���������</td>
        </tr>
      </table>
  	</div>
    <br>
    <Div  id= "divCessGetData"  style= "display: ''" >
		<table class= common border=0 width=100%>
			<TR  class= common>
				<TD  class= title5>��ʼ����</TD>
	        	<TD  class= input5> 
	          		<Input name=StartDate class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" dateFormat='short'  id="StartDate">		<span class="icon"><a onClick="laydate({elem: '#StartDate'});">		<img src="../common/laydate/skins/default/icon.png" /></a></span></TD> 
				<TD  class= title5>��ֹ����</TD>
				<TD  class= input5> 
					<Input name=EndDate class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" dateFormat='short'   id="EndDate">		<span class="icon"><a onClick="laydate({elem: '#EndDate'});">		<img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
				</TD>
		        <TD  class= title5 ></TD>
		        <TD  class= input5 ></TD> 	 				
			</TR> 			
			<TR  class= common>
		      	<TD  class=title >�ֱ���ͬ</TD>
		        <TD  class=input > 
		        	<Input class="codeno" name="ContNo" 
				      ondblClick="showCodeList('lrcontno',[this,ContName],[0,1],null,null,null,1,260);"
				      onkeyup="showCodeListKey('lrcontno',[this,ContName],[0,1],null,null,null,1,260);" ><Input 
				      class= codename name='ContName' >
		        </TD> 					
		      	<TD  class= title5 >�ٱ���˾</TD>
		        <TD  class= input5 > 
		        	<Input class="codeno" name="RIcomCode" 
				      ondblClick="showCodeList('lrcompanysta',[this,RIcomName],[0,1],null,null,fm.ContNo.value,1,250);"
				      onkeyup="showCodeListKey('lrcompanysta',[this,RIcomName],[0,1],null,null,fm.ContNo.value,1,250);" ><Input 
				      class= codename name= 'RIcomName' >
		        </TD>
		        <TD  class= title5 ></TD>
		        <TD  class= input5 ></TD> 	        
			</TR>
	    </table>
	    <br>
	    <hr>
	    <br>	    	   
		<INPUT class=cssButton  VALUE="��������" TYPE=button onClick="StatisticData();">
		<INPUT class=cssButton  VALUE="����" TYPE=button onClick="ResetForm();">				

	</Div>
	<br>	 
    <input type="hidden" name=OperateType value="">
    <input type="hidden" name="Operator" value="<%=Operator%>">
    
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html> 