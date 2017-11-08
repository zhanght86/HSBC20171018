<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //程序名称：PDRiskFeeInput.jsp
 //程序功能：账户管理费定义
 //创建日期：2009-3-14
 //创建人  ：
 //更新记录：  更新人    更新日期     更新原因/内容
%>
<%

 String mRiskCode = request.getParameter("riskcode");
%>

<script>
	 var mJSRiskCode = '<%=mRiskCode%>';
</script>
	
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
  <script src="../common/javascript/MultiCom.js"></script>
   <%@include file="buttonshow.jsp"%>
 <SCRIPT src="PDRiskFee.js"></SCRIPT>
 <%@include file="PDRiskFeeInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDRiskFeeSave.jsp" method=post name=fm target="fraSubmit">
<table>
  <tr>
    <td class="titleImg" >已定义的账户管理费</td>
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

<table>
  <tr>
    <td class="titleImg" >账户管理费定义</td>
  </tr>
</table>
<table  class= common>
	<tr class= common>
        <TD  class= title>管理费编码</TD>
        <TD  class= input>
						<Input class=common name=FEECODE verify="管理费编码|NOTNUlL&LEN<=6" >
        </TD> 
        <TD  class= title>管理费名称</TD>
        <TD  class= input>
						<Input class=common name=FEENAME verify="管理费名称|NOTNUlL" >
						
        <TD  class= title>保险帐户号码</TD>
        <TD  class= input>
					<Input class="codeno" name=INSUACCNO readonly="readonly" verify="保险账户号码|NOTNUlL" ondblclick="return showCodeList('pd_riskinsuacc',[this,INSUACCNOName],[0,1],null,'<%=mRiskCode%>','<%=mRiskCode%>');" onkeyup="return showCodeListKey('pd_riskinsuacc',[this,INSUACCNOName],[0,1],null,'<%=mRiskCode%>','<%=mRiskCode%>');"><input class=codename name=INSUACCNOName readonly="readonly">

        </TD> 
        </tr>
        <tr class=common>
        <TD  class= title>交费/给付项编码</TD>
        <TD  class= input>
					<Input class=common name=PAYPLANCODE  verify="交费/给付项编码|NOTNUlL" elementtype="nacessary" >
					<input type=hidden name=PAYPLANCODEName readonly="readonly" >
        </TD>  

	
        <TD  class= title>关系说明</TD>
        <TD  class= input>
						<Input class=common name=PAYINSUACCNAME >
        </TD> 
        
        <TD  class= title STYLE="display:none;">管理费分类</TD>
        <TD  class= input STYLE="display:none;">
					<Input class="codeno" name=FEEKIND value="03" readonly="readonly" verify="管理费分类|NOTNUlL&CODE:pd_feekind" ondblclick="return showCodeList('pd_feekind',[this,FEEKINDName],[0,1]);" onkeyup="return showCodeListKey('pd_feekind',[this,FEEKINDName],[0,1]);"><input class=codename name=FEEKINDName value="个单管理费" readonly="readonly">
        </TD> 
        </tr> 
        <tr class= common>  
        <TD  class= title>费用项目分类</TD>
        <TD  class= input>
					<Input class="codeno" name=FEEITEMTYPE readonly="readonly" verify="费用项目分类|NOTNUlL&CODE:pd_feeitemtype" ondblclick="return showCodeList('pd_feeitemtype',[this,FEEITEMTYPEName],[0,1]);" onkeyup="return showCodeListKey('pd_feeitemtype',[this,FEEITEMTYPEName],[0,1]);"><input class=codename name=FEEITEMTYPEName readonly="readonly">
        </TD> 
        <TD  class= title>
费用收取位置
        </TD>
        <TD  class= input>
           <Input class="codeno" name=FEETAKEPLACE readonly="readonly" verify="费用收取位置|NOTNUlL&CODE:pd_feetakeplace" ondblclick="return showCodeList('pd_feetakeplace',[this,FEETAKEPLACEName],[0,1]);" onkeyup="return showCodeListKey('pd_feetakeplace',[this,FEETAKEPLACEName],[0,1]);"><input class=codename name=FEETAKEPLACEName readonly="readonly">
        </TD>
        <TD  class= title></TD>
        <TD  class= input></TD>
         </tr>
         <tr class=common>
         	<TD  class= title>
管理费计算方式
        </TD>
        <TD  class= input>
           <Input class="codeno" name=FEECALMODE readonly="readonly" verify="管理费计算方式|NOTNUlL&CODE:pd_feecalmode" ondblclick="return showCodeList('pd_feecalmode',[this,FEECALMODEName],[0,1]);" onkeyup="return showCodeListKey('pd_feecalmode',[this,FEECALMODEName],[0,1]);"><input class=codename name=FEECALMODEName readonly="readonly">
        </TD>
        <TD  class= title STYLE="display:none;">
计算类型
        </TD>
        <TD  class= input STYLE="display:none;">
           <Input class="codeno" name=FEECALMODETYPE readonly="readonly" value="1" verify="计算类型|NOTNUlL&CODE:pd_feecalmodetype" ondblclick="return showCodeList('pd_feecalmodetype',[this,FEECALMODETYPEName],[0,1]);" onkeyup="return showCodeListKey('pd_feecalmodetype',[this,FEECALMODETYPEName],[0,1]);"><input class=codename name=FEECALMODETYPEName value="直接取值" readonly="readonly">
        </TD>
        	<TD  class= title>
扣除管理费周期
        </TD>
        <TD  class= input>
           <Input class="codeno" name=FEEPERIOD readonly="readonly" verify="扣除管理费周期|CODE:pd_feeperiod" ondblclick="return showCodeList('pd_feeperiod',[this,FEEPERIODName],[0,1]);" onkeyup="return showCodeListKey('pd_feeperiod',[this,FEEPERIODName],[0,1]);"><input class=codename name=FEEPERIODName readonly="readonly">
        </TD>
        <TD  class= title>
扣除管理费起始时间
        </TD>
       <TD  class= input>
           <Input class="coolDatePicker" name=FEESTARTDATE  verify="扣除管理费起始时间|NOTNUlL" dateFormat="short" id="FEESTARTDATE" onClick="laydate({elem:'#FEESTARTDATE'});"><span class="icon"><a onClick="laydate({elem: '#FEESTARTDATE'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </TD> 
         </tr>
         <tr class=common>
    
        <TD  class= title>
管理费计算公式
        </TD>
        <TD  class= input>
          <input class="common"    name="FEECALCODE" verify="管理费计算公式|LEN>=6&LEN<=10">
        </TD>
    	</tr>
</table>
<!--算法定义引用页-->
<jsp:include page="CalCodeDefMain.jsp"/>
<hr>


<div align=right id=savabuttonid>
<input value="重  置" type=button  onclick="initDetail()" class="cssButton" type="button" >
<input value="保  存" type=button  onclick="save()" class="cssButton" type="button" >
<input value="修  改" type=button  onclick="update()" class="cssButton" type="button" >
<input value="删  除" type=button  onclick="del()" class="cssButton" type="button" >
<input value="返  回" type=button  onclick="returnParent( )" class="cssButton" type="button" >
</div>
<div align=left id=checkFunc>
<input value="查看算法内容" type=button  onclick="InputCalCodeDefFace2()" class="cssButton" type="button" >
</div>
<br>

<!--table  class= common>
   <tr  class= common>
      <td style="text-align:left;" colSpan=1>
     <span id="spanMulline9Grid" >
     </span> 
      </td>
   </tr>
</table>
<INPUT CLASS=cssbutton VALUE="首  页" TYPE=button onclick="Mulline9GridTurnPage.firstPage();"> 
<INPUT CLASS=cssbutton VALUE="上一页" TYPE=button onclick="Mulline9GridTurnPage.previousPage();">      
<INPUT CLASS=cssbutton VALUE="下一页" TYPE=button onclick="Mulline9GridTurnPage.nextPage();"> 
<INPUT CLASS=cssbutton VALUE="尾  页" TYPE=button onclick="Mulline9GridTurnPage.lastPage();"-->
</BR></BR>

<!--input value="管理费计算算法定义" type=button  onclick="button179( )" class="cssButton" type="button" -->
<br><br>

<br><br>

<input type=hidden name="RiskCode">
<input type=hidden name="operator">
<input type=hidden name="tableName" value="PD_LMRiskFee">
<input type=hidden name=IsReadOnly>
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
