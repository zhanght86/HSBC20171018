<%@include file="/i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
//�������ƣ�LRSearchInput.jsp
//�����ܣ�
//�������ڣ�2007-2-7
//������  ��zhangbin
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%> 

<head>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT><SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT><SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css><LINK href="../common/css/Project3.css" rel=stylesheet type=text/css> 
	<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
	
	<SCRIPT src="RIDataImportInput.js"></SCRIPT>
  <%@include file="./RIDataImportInit.jsp"%>
  
</head>
<%
	String currentdate=PubFun.getCurrentDate();
%>
<script type="text/javascript">
var currentDate="<%=currentdate%>"
</script>
<body onload="initElementtype();initForm();">    
  <form action="RIDataImportSave.jsp" method=post name=fm target="fraSubmit" >
    <div style="width:200">
      <table class="common">
        <tr class="common">
          <td class="common"><img  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCessGetData);"></td>
          <td class="titleImg">�ٱ�����Ǩ��</td>
        </tr>
      </table>
  	</div>
    <br>
    <Div  id= "divCessGetData" style= "display: ''" >
	    <table class= common border=0 width=100%>
	      	<TR  class= common>
	          <TD  class= title5>
��������
	          </TD>
	          <TD class= input5 >
	       		<input class=codeno readonly="readonly" name="ProcessType" CodeData="0|^21|ҵ�����ݵ���|^22|�ֱ����ݵ���|^31|ҵ������ɾ��|^32|�ֱ�����ɾ��|" 
          	   ondblclick="return showCodeListEx('state', [this,ProcessTypeName],[0,1],null,null,null,1);" 
          	   onkeyup="return showCodeListKeyEx('state', [this,ProcessTypeName],[0,1],null,null,null,1);" verify="��������|NOTNULL"><input 
          	   class=codename name=ProcessTypeName readonly="readonly" elementtype=nacessary>
              </TD>
              <TD  class= title5>
	             <Div  id= "divTitle" style= "display:''">��ֹ����</Div>
	          </TD>
	          <TD  class= input5>
	            <Div  id= "divEndDate" style= "display:''">
	               <Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" dateFormat="short" name=EndDate value =<%=currentdate%>  id="EndDate">		<span class="icon"><a onClick="laydate({elem: '#EndDate'});">		<img src="../common/laydate/skins/default/icon.png" /></a></span>
	            </Div>
	          </TD>
              <TD></TD>
            </TR>
	  	</table>
	  	<br>
	  	<INPUT  class=cssButton VALUE="ִ  ��" TYPE=Button onclick="SubmitForm();">
			&nbsp;&nbsp;
		<Div id="divList" style= "display:none;">
	  		<table>
	  	  	<tr>
	  	    	<td class=common><IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divTaskList);"></td>
	  				<td class= titleImg>��������</td></tr>
	  		</table>
	  		<Div id= "divAccmulate" style= "display:none;">
					<table class="common">		
						<tr class="common">
							<td style="text-align:left;" colSpan=1><span id="spanAccmulateGrid"></span></td>
						</tr>						
					</table>
	  		</Div>
	  		<Div id= "divTaskList" style= "display:none;">
				<table class="common">		
					<tr class="common">
						<td style="text-align:left;" colSpan=1><span id="spanTaskListGrid"></span></td>
					</tr>						
				</table>
	 			<table class="common">	
	 				<TR>			
						<TD  width='10%' style="font:9pt">ѡ������ļ���</TD>     
					    <TD  width='30%'>
					      	<Input  type="file"��width="80%" name=FileName class= common>
					     	<INPUT VALUE="���ظ���" class=cssButton TYPE=hidden onclick="ReInsureUpload();">
					    </TD>
					    <td class= input5></td>
					    <td class= input5></td>
				   </TR> 						
				</table>  				
	  		</Div>
		</Div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html> 
