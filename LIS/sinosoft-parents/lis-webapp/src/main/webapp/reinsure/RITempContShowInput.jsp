<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>


<%
 //�������ƣ�RITempContShowInput.jsp
 //�����ܣ��ٷ���˲�ѯ
 //�������ڣ�2011-11-09
 //������  ��
 //���¼�¼��  ������    ��������     ����ԭ��/����
%>

<head>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT><SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT><SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css><LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
 
  <SCRIPT src="RITempContShow.js"></SCRIPT>
  <%@include file="RITempContShowInit.jsp"%>
</head>
<body  onload="initElementtype();initForm();" >
<form method=post name=fm target="fraSubmit" action= "" >
<%@include file="../common/jsp/InputButton.jsp"%>
  <div style="width:200">
      <table class="common">
        <tr class="common">
          <td class="common">
          	<img src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCessGetData);">
          </td>
          <td class="titleImg">�ٷֱ�����ѯ</td>
        </tr>
      </table>
  </div>
	<Div  id= "divCessGetData" style= "display: ''" >
		<Table class= common >
			<TR  class= common>
      			<td class="title5">�ٷֱ�����</td>
	  	    	<td class="input5">
	  	    		<Input class="common" name="Polno" >
	  	    	</td>
	  	    	<td class="input5">
	  	    		<input value="��ѯ"  onclick="button134()" class="cssButton" type="button" >
	  	    	</td>
	  	    	<td class="input5">
	  	    	</td>
	  	    	<td class="input5">
	  	    	</td>
	       </TR>
       </Table>
  <!--���ձ�����Ϣ-->
  <Div  id='divIndTempInsuList' style= "display: ''">
  	<table>
			<tr>
		    	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divIndTempListGrid);">
				</td>
				<td class= titleImg>
���ձ�����Ϣ
				</td>
			</tr>
  	</table>
  	<Div  id='divIndTempToalListGrid' style= "display: ''">
  		<table  class= common>
  		 	<tr  class= common>
  		 		<td style="text-align:left;" colSpan=1 >
						<span id="spanIndTempToalListGrid" >
						</span> 
					</td> 
				</tr> 
			</table> 
		</Div>
	</Div>
	<hr><br>
	<table>
			<tr>
		    	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divIndTempListGrid);">
				</td>
				<td class= titleImg>
���ձ�����ϸ��Ϣ
				</td>
			</tr>
  	</table>
  	<Div  id='divIndTempListGrid' style= "display: ''">
  		<table  class= common>
  		 	<tr  class= common>
  		 		<td style="text-align:left;" colSpan=1 >
						<span id="spanIndTempListGrid" >
						</span> 
					</td> 
				</tr> 
			</table> 
		</Div>
	</Div>
	
  
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
