<%@include file="/i18n/language.jsp"%>
<html>
<%
//name :LRContInput.jsp
//function :ReComManage
//Creator :
//date :2006-08-14
%>
	<%@page contentType="text/html;charset=GBK" %>
	<%@page import = "com.sinosoft.utility.*"%>
	<%@page import = "com.sinosoft.lis.schema.*"%>
	<%@page import = "com.sinosoft.lis.vschema.*"%>
	<%@page import = "com.sinosoft.lis.reinsure.*"%>
	<%@include file="../common/jsp/UsrCheck.jsp"%>
	
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT><SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT><SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css><LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>

<SCRIPT src = "./LRContInput.js"></SCRIPT> 
<%@include file="./LRContInit.jsp"%>
</head>
<body  onload="initElementtype();initForm()" >
  <form action="./LRContSave.jsp" method=post name=fm target="fraSubmit">
  <%@include file="../common/jsp/OperateButton.jsp"%>
  <%@include file="../common/jsp/InputButton.jsp"%>
  
   <table>
   	  <tr>
        <td class=common><IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" 
        	OnClick= "showPage(this,divLRCont1);"></td>
    	<td class= titleImg>基本信息</td></tr>
    </table>
  
  <Div id= "divLRCont1" style= "display: ''" >
	<br>
		<Div id= "divTable1" style= "display: ''" >
   		<Table class= common>
   			<TR class= common>
   				<TD class= title5>
合同编号
   				</TD>
   				<TD class= input5>
   					<Input class= common name="RIContNo" id="ReContCodeId" elementtype=nacessary verify="合同编号|NOTNULL"> 
   				</TD>
   				<TD class= title5>
合同名称
   				</TD>
   				<TD class= input5>
   					<Input class= common  name="RIContName" id="ReContNameId" elementtype=nacessary verify="合同名称|NOTNULL"> 
   				</TD>   			
   				<TD class= title5>
合同状态
    	    </TD>
    	    <TD class= input5>
    	    	<Input class=codeno readonly="readonly" NAME=ContState VALUE="" CodeData="0|^0|未生效|^1|有效" 
    	    	ondblClick="showCodeListEx('',[this,ContstateName],[0,1],null,null,null,1);" 
    	    	onkeyup="showCodeListKeyEx('',[this,ContstateName],[0,1],null,null,null,1);"  verify="合同状态|NOTNULL"><input class=codename name=ContstateName readonly="readonly"  elementtype=nacessary>
   			</TD>
    	  </TR> 
    	  <TR class= common>
    	  	<TD class= title5>
起始日期
   				</TD>
   					<TD class= input5><Input name=RValidate class="coolDatePicker" onClick="laydate({elem: '#RValidate'});" dateFormat='short'  id="RValidate">
<span class="icon"><a onClick="laydate({elem: '#RValidate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
   				<TD class= title5>
终止日期
    	    </TD>
    	    <TD class= input5><Input name=RInvalidate class="coolDatePicker" onClick="laydate({elem: '#RInvalidate'});" dateFormat='short'  id="RInvalidate">
<span class="icon"><a onClick="laydate({elem: '#RInvalidate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
    	    <TD class= title5>
签订日期
    	    </TD>
    	    <TD class= input5>
    	    	<Input class= "coolDatePicker" name= RSignDate verify="合同签订日期|DATE">
    	    </TD>
    	  </TR>
    	 <TR class= common> 
    	    <TD class= title5>账单周期</TD>			
			<TD class= input5>
    	    	<Input class=codeno readonly="readonly" NAME=BillingCycle VALUE="" CodeData="0|^1|月度|^3|季度|^12|年度" 
    	    	ondblClick="showCodeListEx('BillingCycle',[this,BillingCycleName],[0,1],null,null,null,1);" 
    	    	onkeyup="showCodeListKeyEx('BillingCycle',[this,BillingCycleName],[0,1],null,null,null,1);"  verify="合同状态|NOTNULL"><input class=codename name=BillingCycleName readonly="readonly"  elementtype=nacessary>
   			</TD>
    	 </TR>
    	</Table>
  	</Div>
  	
  	<Div id= "divTable3" style= "display: ''" >
    	<Table class= common>
    	  <TR class= common>
    	  	<TD class= title5>
    	      <Div id= "divTitle1" style= "display:none;" >保单类型</Div>
    	    </TD>
   				<TD class="input5">
   					<Div id= "divInput1" style= "display:none;" >
    	    		<input type="radio" name="ContModType"  value="0" OnClick="ChooseOldCont();" checked>新建合同
			        <input type="radio" name="ContModType"  value="1" OnClick="ChooseOldCont();">合同修改
    	    	</Div>
    	    </TD>
    	    <TD class= title5>
    	    	
    	    	<Div id= "divTitle2" style= "display:none;" >修改合同编码</Div>
    	    </TD>
    	    <TD class= input5>
    	    	
    	    	<Div id= "divInput2" style= "display:none;" >
    	    		<Input class= common name="ModRIContNo" id="ModReContCodeId" elementtype=nacessary> 
    	      </Div>
    	    </TD>
    	    
    	    <TD class= title5>
    	    	<Div id= "divTitle3" style= "display:none;" >修改合同名称</Div>
    	    </TD>
    	    <TD class= input5>
    	    	<Div id= "divInput3" style= "display:none;" >
    	    		<Input class= common  name="ModRIContName" id="ModReContNameId" > 
    	      </Div>
    	    </TD>
    	    
    	  </TR>
    	  <tr>
    	  	<TD class= title5>
    	    	<div id= "divTitle1" style= "display:none;" >分保类型</div>
    	    </TD>
    	    <TD class= input5>
    	    	<div id= "divInpu1" style= "display:none;" >
	   					<Input class="codeno" readonly="readonly" name= "ReInsuranceType" CodeData="0|^01|成数分保^02|溢额分保^03|成数溢额分保" 
	    	      ondblClick="showCodeListEx('DiskKind',[this,ReInsuranceTypeName],[0,1],null,null,null,1);"
	    	      onkeyup="showCodeListKeyEx('DiskKind',[this,ReInsuranceTypeName],[0,1],null,null,null,1);" ><Input 
	    	      class= codename name='ReInsuranceTypeName' readonly="readonly" >
    	    	</div>
    	    	
    	    	
    	    </TD>
    	   </tr>
   		</Table>
   	</Div>
   	
  	<Div id= "divTable2" style= "display:none;" >
    	<Table class= common>
    	  <TR class= common>
    	  	<TD class= title5>
    	      <Div id= "divTitle4" style= "display: ''" >临分合同标志</Div>
    	    </TD>
   				<TD class="input5">
   					<Div id= "divInput4" style= "display: ''" >
    	      	<input class=codeno readonly="readonly" name="ContType" CodeData="0|^01|正常合同|^02|临分合同|^03|团体临分|" 
    	      	ondblclick="return showCodeListEx('State', [this,ContTypeName],[0,1],null,null,null,1);" 
    	      	onkeyup="return showCodeListKeyEx('State', [this,ContTypeName],[0,1],null,null,null,1);"><input 
    	      	class=codename name=ContTypeName readonly="readonly">
    	    	</Div>
    	    </TD>
    	    <TD class= title5>
    	    	<Div id= "divTitle5" style= "display: ''" >合同险类类型</Div>
    	    </TD>
    	    <TD class= input5>
    	    	<Div id= "divInput5" style= "display: ''" >
    	    		<input class=codeno readonly="readonly" name="RiskType" CodeData="0|^01|意外险|^02|医疗险|^03|重疾险|" 
    	      		ondblclick="return showCodeListEx('State', [this,RiskTypeName],[0,1],null,null,null,1);" 
    	      		onkeyup="return showCodeListKeyEx('State', [this,RiskTypeName],[0,1],null,null,null,1);" ><input 
    	      		class=codename name=RiskTypeName readonly="readonly" elementtype=nacessary>
    	      </Div>
    	    </TD>
    	    <TD class= title5>
    	    	<Div id= "divTitle6" style= "display: ''" ></Div>
    	    </TD>
    	    <TD class= input5>
    	    	<Div id= "divInput6" style= "display: ''" >
    	      </Div>
    	    </TD>
    	  </TR>
   		</Table>
   	</Div>
  </Div>
  <br>
  <div id="divHidden" style= "display:none;">
	  <table>
	    <tr>
	      <td class=common><IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" 
	      	OnClick= "showPage(this,divLRCont2);"></td>
	  	<td class= titleImg>合同分保信息</td></tr>
	  </table>
	  <Div id= "divLRCont2" style= "display: ''">
			  <table class="common">		
					<tr class="common">
						<td style="text-align:left;" colSpan=1><span id="spanContCessGrid"></span></td>
					</tr>
				</table>
		    </div>
	  </Div>
  <br>
    <table>
   	  <tr>
        <td class=common><IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" 
        	OnClick= "showPage(this,divCertifyType);"></td>
    	<td class= titleImg>合同签定人</td></tr>
    </table>
    
   	<Div  id= "divCertifyType" style= "display: ''">
      <table  class= common>
          <tr  class= common>
            <td text-align:left colSpan=1>
          		<span id="spanSignerGrid" ></span>
        		</td>
      		</tr>
    	</table>
	</div>
	<br>
	
  <Div id= "divdivLRCont4" style= "display:none;">
     <table>
    	  <tr>
         <td class=common><IMG src= "../common/images/butExpand.gif" style= "cursor:hand;"
         	OnClick= "showPage(this,divdivLRCont3);"></td>
     	<td class= titleImg>合同级分保信息</td></tr>
     </table>
    	<Div  id= "divdivLRCont3" style= "display: ''">
      	<table  class= common>
      	     <tr  class= common>
      	       <td text-align:left colSpan=1>
      	     		<span id="spanFactorGrid" ></span>
      	   		</td>
      	 		</tr>
     		</table>
   		</div>
  </Div> 
  <br>
   
  <input class="cssButton" type="button" value="下一步" onclick="nextStep()">
  <input type="hidden" name="OperateType" >
  
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>