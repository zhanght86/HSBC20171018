<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");
	
/*******************************************************************************
 * <p>Title       : ��ֵ˰�ʲ�ѯ</p>
 * <p>Description : ��ֵ˰�ʲ�ѯ</p>
 * <p>Copyright   : Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company     : �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite     : http://www.sinosoft.com.cn</p>
 * @author        : 
 * @version       : 1.00
 * @date          : 2016-5-17
 ******************************************************************************/
%>
<script>

</script>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="./FindVATRate.js"></SCRIPT>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <script src="../common/javascript/MultiCom.js"></script>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>  
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
 <SCRIPT src="../common/javascript/jquery-1.7.2.js"></SCRIPT> 

	<SCRIPT src="../common/javascript/jquery.easyui.min.js"></SCRIPT>
  <link rel="stylesheet" type="text/css" href="../common/themes/default/easyui.css">
  <link rel="stylesheet" type="text/css" href="../common/themes/icon.css">
 
  <%@include file="./FindVATRateInit.jsp"%>
  <title>��ֵ˰�ʲ�ѯ</title>
</head>
<body  onload="initForm();initElementtype();" >
  <form action="./FindVATRateSave.jsp" method=post name=fm id=fm target="fraSubmit">
   <Div id="NativeConfig" height=500>
  <table>
    <tr>
      <td class=common>
    		     <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divQuery);">
    	</td>
      <td class= titleImg>
    	       �������ѯ����
      </td>
    </tr>
   </table>
   <Div  id= "divQuery" style= "display: ''">
   <div class="maxbox1">
   <table class=common>
   <TR>
   	   <TD class=title5>��������</TD>
	       <TD class=input5>
               <Input class="codeno" name=FeeTypeCode  id=FeeTypeCode 
               style="background:url(../common/images/select--bg_03.png)	no-repeat right center" 
               onclick="return showCodeList('LDVATFeeTypeCodeConfig',[this,FeeTypeName],[0,1],null,null,null,true);"
               onDblClick="return showCodeList('LDVATFeeTypeCodeConfig',[this,FeeTypeName],[0,1],null,null,null,true);" 
               onKeyUp="return showCodeListKey('LDVATFeeTypeCodeConfig',[this,FeeTypeName],[0,1],null,null,null,true);">
               <input class="codename" name=FeeTypeName id=FeeTypeName readonly=true>
	      </TD>	
	      	  
      </TR> 
    </Table>
    </div>
      </Div>
 
    <br> 
    <a href="javascript:void(0);" class="button"onClick="query()">��   ѯ</a>

 
    <table>
    <tr>
      <td class=common>
    		     <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divMul);">
    	</td>
      <td class= titleImg>
    	        ����������Ϣ
      </td>
    </tr>
    </table>
    <Div  id= "divMul" style= "display: ''">
     <table  class= common>
        <tr>
    	  	<td text-align: left colSpan=1>
	         <span id="spanWorkFlowGrid" ></span>
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
				 <Input class="codeno" name=FeeTypeCode1  id=FeeTypeCode1 
               style="background:url(../common/images/select--bg_03.png)	no-repeat right center" 
               onclick="return showCodeList('LDVATFeeTypeCodeConfig',[this,FeeTypeName1],[0,1],null,null,null,true);"
               onDblClick="return showCodeList('LDVATFeeTypeCodeConfig',[this,FeeTypeName1],[0,1],null,null,null,true);" 
               onKeyUp="return showCodeListKey('LDVATFeeTypeCodeConfig',[this,FeeTypeName1],[0,1],null,null,null,true);">
               <input class="codename" name="FeeTypeName1" id="FeeTypeName1" elementtype="nacessary" readonly="true">
               <font id="StartDaten" color="red">&nbsp;*</font>
			</td>
			<td CLASS="title5">�Ƿ�Ӧ˰</td>
			<td CLASS="input5">
				<Input class="codeno" name=Taxible  id=Taxible 
               style="background:url(../common/images/select--bg_03.png)	no-repeat right center" 
               onclick="return showCodeList('yesnoen',[this,TaxibleName],[0,1],null,null,null,true);"
               onDblClick="return showCodeList('yesnoen',[this,TaxibleName],[0,1],null,null,null,true);" 
               onKeyUp="return showCodeListKey('yesnoen',[this,TaxibleName],[0,1],null,null,null,true);">
               <input class="codename" name=TaxibleName id=TaxibleName readonly=true>
               <font id="StartDaten" color="red">&nbsp;*</font>
			</td>	
				
    </tr>
    <tr CLASS="common">   
			
            <td CLASS="title5">�Ƿ��������</td>
			<td CLASS="input5">
				<Input class="codeno" name=RiskRele  id=RiskRele 
               style="background:url(../common/images/select--bg_03.png)	no-repeat right center; with:150px" 
               onclick="return showCodeList('yesnoen',[this,RiskReleName],[0,1],null,null,null,true);"
               onDblClick="return showCodeList('yesnoen',[this,RiskReleName],[0,1],null,null,null,true);" 
               onKeyUp="return showCodeList('yesnoen',[this,RiskReleName],[0,1],null,null,null,true);">
               <input class="codename" name=RiskReleName id=RiskReleName readonly=true>
               <font id="StartDaten" color="red">&nbsp;*</font>
		</tr>
		
    <tr CLASS="common"> 
         <td CLASS="title5"><input type=hidden  id="RecordID" name="RecordID"  >
         </td>
         
        
    </tr>
		
	</table>
</div>
	<input type=hidden id="fmtransact" name="fmtransact">
    <input type=hidden id="fmAction" name="fmAction">
</Div>
       
        
<a href="javascript:void(0);" class="button"onClick="newClick()">��  ��</a>
<a href="javascript:void(0);" class="button"onClick="updateClick()">��  ��</a>
<!-- <a href="javascript:void(0);" class="button"onClick="updateClick()">��  ��</a> -->
<a href="javascript:void(0);" class="button"onClick="deleteClick()">ɾ  ��</a>
<a href="javascript:void(0);" class="button"onClick="return configTaxRate()">��������</a> 
<a href="javascript:void(0);" class="button"onClick="return configRiskGrp()">����������</a> 

  <br><br><br><br>      

  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
