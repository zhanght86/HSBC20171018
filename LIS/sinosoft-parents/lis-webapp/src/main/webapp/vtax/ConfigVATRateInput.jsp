<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%> 
<%
GlobalInput tG = new GlobalInput();
tG = (GlobalInput)session.getValue("GI");
 //�������ƣ�ConfigVATRateMain.jsp
 //�����ܣ���ֵ˰�ʶ������
 //�������ڣ�
 //������  ��
 //���¼�¼��  ������    ��������     ����ԭ��/����
  
%>
<script>

</script>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <script src="../common/javascript/MultiCom.js"></script>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>  
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>  
 <SCRIPT src="../common/javascript/jquery-1.7.2.js"></SCRIPT> 
 
  <SCRIPT src="./ConfigVATRate.js"></SCRIPT>
 

	<SCRIPT src="../common/javascript/jquery.easyui.min.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <link rel="stylesheet" type="text/css" href="../common/themes/default/easyui.css">
  <link rel="stylesheet" type="text/css" href="../common/themes/icon.css">
  <link href="../common/css/mulLine.css" rel="stylesheet" type="text/css">

<%@include file="./ConfigVATRateInit.jsp"%>

<title>��ֵ˰������</title>
<script language="javascript">
	
	/* var intPageWidth=screen.availWidth;
	var intPageHeight=screen.availHeight;
	window.moveTo(-1, -1);
	window.resizeTo(intPageWidth,intPageHeight); 
	//window.focus();
	window.onunload = afterInput;
	

function afterInput() {
	try {
	
}
	catch(e) {}
	window.focus();
} 
 */

</script>

</head>

 <body onload="initForm();initElementtype();">
 <form action="./ConfigVATRateSave.jsp" method=post name=fm id=fm target="fraSubmit">
   <Div id="NativeConfig" height=500>
 <!--  <table>
    <tr>
      <td class=common>
    		     <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divQuery);">
    	</td>
      <td class= titleImg>
    	       �������ѯ����
      </td>
    </tr>
   </table> -->
   <!-- <Div  id= "divQuery" style= "display: ''">
   <div class="maxbox1">
  <table class=common>
   <TR>
   	   <TD class=title5>��������</TD>
	       <TD class=input5>
               <Input class=codeno name=BusiTypeCode  id=BusiTypeCode 
               style="background:url(../common/images/select--bg_03.png)	no-repeat right center" 
               onclick="return showCodeList('LDVATConfig',[this,BusiTypeName],[0,1]);"
               onDblClick="return showCodeList('LDVATConfig',[this,BusiTypeName],[0,1]);" 
               onKeyUp="return showCodeList('LDVATConfig',[this,BusiTypeName],[0,1]);">
               <input class=codename name=BusiTypeName readonly=true>
	      </TD>	
	   </TD>	   	  
    </TR> 
  </Table>
    </div>
      </Div> -->
 
    <!-- <br> 
    <a href="javascript:void(0);" class="button"onClick="query()">��   ѯ</a> -->

 
    <table>
    <tr>
      <td class=common>
    		     <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divMul);">
    	</td>
      <td class= titleImg>
    	        ������Ϣ
      </td>
    </tr>
    </table>
    <Div  id= "divMul" style= "display: ''">
     <table  class= common>
        <tr>
    	  	<td text-align: left colSpan=1>
	         <span id="spanTaxRateGrid" ></span>
	       	</td>
	      </tr>
     </table>  
  </div>
<br>

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
			<td CLASS="title5">�������ͱ���</td>
			<td CLASS="input5" COLSPAN="1">
				 <Input class="codeno" name=TFeeTypeCode  id=TFeeTypeCode readonly=true>
                 <input class="codename" name=TFeeTypeName id=TFeeTypeName readonly=true>
			</td>
			<td CLASS="title5">˰��</td>
			 <td CLASS="input5">
			 	<input id="TaxRate" name="TaxRate" class="common" elementtype=nacessary verify="˰��|notnull">%<font id="StartDaten" color="red">&nbsp;*</font>
			 	
			 </td>
	</tr>	
			
	<tr CLASS="common">
			<td CLASS="title5">������</td>
			<td CLASS="input5">
				 <Input class="common" name="RiskGrpCode"  id="RiskGrpCode" 
               style="background:url(../common/images/select--bg_03.png)	no-repeat right center" 
               onclick="return showCodeList('RiskGrpCode',[this,FeeTypeName],[0,1],null,null,null,true);"
               onDblClick="return showCodeList('RiskGrpCode',[this,FeeTypeName],[0,1],null,null,null,true);" 
               onKeyUp="return showCodeListKey('RiskGrpCode',[this,FeeTypeName],[0,1],null,null,null,true);">
			</td>
            <td CLASS="title5">����</td>
			<td CLASS="input5">
			  <Input class="common" name=ManageCom  id=ManageCom 
               style="background:url(../common/images/select--bg_03.png)	no-repeat right center" 
               onclick="return showCodeList('managecom',[this,FeeTypeName],[0,1],null,null,null,true);"
               onDblClick="return showCodeList('managecom',[this,FeeTypeName],[0,1],null,null,null,true);" 
               onKeyUp="return showCodeListKey('managecom',[this,FeeTypeName],[0,1],null,null,null,true);" >
               <font id="StartDaten" color="red" >&nbsp;*</font>
			 </td>
	</tr>
	<tr CLASS="common"> 
			 <td CLASS="title5">����</td>
			<td CLASS="input5">
				<Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});"  elementtype=nacessary verify="����|notnull&Date" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
				</td>
			 <td class="title5">ֹ��</td>
			<td class="input5">				
                <Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});"    elementtype=nacessary  verify="��Ч��ʼ����|DATE" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
			</td>
			 
   </tr>
   <tr CLASS="common"> 
			 <td CLASS="title5"><input  CLASS="input5" type=hidden  id="ConfigID" name="ConfigID"  >
			  </td>
			 <td CLASS="title5"><input  CLASS="input5" type=hidden  id="TaxID" name="TaxID"  >
			 </td>
   </tr>
		
	</table>
</div>
	<input type=hidden id="fmtransact" name="fmtransact">
    <input type=hidden id="fmAction" name="fmAction">
</Div>
       
        
<a href="javascript:void(0);" class="button"onClick="newClick()">��  ��</a>
<a href="javascript:void(0);" class="button"onClick="updateClick()">��  ��</a>
<a href="javascript:void(0);" class="button"onClick="deleteClick()">ɾ  ��</a>
<!-- <a href="javascript:void(0);" class="button"onClick="return configRiskGrp()">����������</a>  -->

  <br><br><br><br>      

  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
 </body>
</html>

