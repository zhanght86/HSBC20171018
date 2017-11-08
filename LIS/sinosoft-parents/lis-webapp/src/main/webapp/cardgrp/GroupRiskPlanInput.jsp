    <%
//程序名称：TempFeeInput.jsp
//程序功能：财务收费的输入
//创建日期：2002-07-12 
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html> 
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="GroupRiskPlanInput.js"></SCRIPT>
  <%@include file="GroupRiskPlanInit.jsp"%>
</head>
<body  onload="initForm();" >
<Form action="./ProposalSave.jsp" method=post name=fm target="fraSubmit">
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divGroupPol1);">
    		</td>
    		<td class= titleImg>
    			 集体保险计划信息
    		</td>
    	</tr>
    </table>
    <table  class= common>
        <TR  class= common>
         <TD  class= title8>
            险种投保单编码
          </TD>
          <TD  class= input8>
            <Input class="readonly" readonly name=GrpProposalNo >
          </TD>
          <TD  class= title8>
            集体投保单号码
          </TD>
          <TD  class= input8>
            <Input class="readonly" readonly name=ContNo  >
          </TD>
         
          <TD  class= title8>
            印刷号码
          </TD>
          <TD  class= input8>
            <Input class="readonly" readonly name=PrtNo verify="印刷号码|notnull" >
          </TD>
        </TR>
        </table>
       
    <table>
    	<tr>
        	<td class=common>
		   <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divGroupPol3);">
    		</td>
    		<td class= titleImg>
    			 险种信息
    		</td>
    	</tr>
    </table>
    <Div  id= "divGroupPol3" style= "display: ''">
      <table  class= common>
      <TR class=common>
      <TD  class= title8>
            *险种编码
          </TD>
           <TD  class= input8>
            <Input class="code8" name=RiskCode ondblclick="return showCodeList('RiskGrp',[this]);" onkeyup="return showCodeListKey('RiskGrp',[this]);">
          </TD>
          </TR>
        <TR  class= common>
          <TD  class= title8>
            投保申请日期 
          </TD>
          <TD  class= input8>
            <Input class="coolDatePicker" dateFormat="short" name=PolApplyDate >
          </TD> 
          <TD  class= title8>
            *保单生效日期 
          </TD>
          <TD  class= input8>
            <Input class="coolDatePicker" dateFormat="short" name=CValiDate >
          </TD>    
          <TD  class= title8>
            *交费周期
          </TD>
          <TD  class= input8>
            <Input class="code8" name=PayIntv ondblclick="return showCodeList('PayIntv',[this]);">
          </TD>     
        </TR>
        
        <TR  class= common>
          
          <TD  class= title8>
            保险期间
          </TD>
          <TD  class= input8>
             <Input class=common name=InsuYear value="0">
          </TD>
             <TD  class= title8>
            *浮动费率
          </TD>
          <TD  class= input8>
             <Input class=common name=FloatRate value="0">
          </TD>
             <TD  class= title8>
            *计算方向
          </TD>
          <TD  class= input8>
             <Input class="code8"  CodeData="0|^P|保额算保费|^A|保费算保额|^O|其他算保费" name=CalDirect ondblClick="showCodeListEx('CalDirect',[this],[0]);" onkeyup="showCodeListKeyEx('CalDirect',[this],[0]);" value="P">
          </TD>
          
          
        </TR>
        <tr class=common>
        <TD  class= title8>
            雇员自付比例
          </TD>
          <TD  class= input8>
            <Input class= common name=EmployeeRate verify="雇员自付比例|num&len<=5">
          </TD>
          <TD  class= title8>
            家属自付比例
          </TD>
          <TD  class= input8>
            <Input class= common name=FamilyRate verify="家属自付比例|num&len<=80">
          </TD>
          </tr>
        </Table>
        </Div>
       
     
  <table class=common>
   <TR  class= common> 
      <TD  class= title8> 计划描述 </TD>
    </TR>
    <TR  class= common>
      <TD  class= title8>
      <textarea name="GrpSpec" cols="120" rows="3" class="common" >
      </textarea></TD>
    </TR>
  </table>  
   <table>
    <TR >     
     <TD >
      <input type =button class=cssButton value="添加定制保险计划" onclick="addRecord();">      
     </TD>
     <TD >
      <input type =button class=cssButton value="删除定制保险计划" onclick="deleteRecord();">      
     </TD>
    </TR> 
    </table> 
    </Div>
    <Div  id= "divTempFeeInput" style= "display: ''">
  <!--录入的暂交费表部分 -->
    <Table  class= common>
      	<tr>
    	 <td text-align: left colSpan=1>
	 <span id="spanTempGrid" >
	 </span> 
	</td>
       </tr>
    </Table>
    </div>
    <Div  id= "divHidden" style= "display: 'none'">
      <TR  class= common>          
          <TD  class= title8>
            封顶线
          </TD>
          <TD  class= input8>
            <Input class= common name=PeakLine >
          </TD>
        </TR>
        <TR  class= common>          
          <TD  class= title8>
            医疗费用限额
          </TD>
          <TD  class= input8>
            <Input class= common name=MaxMedFee >
          </TD>
        </TR>
    </Div>
    <table>
    <TR class=common> 
    <TD align =right class=common>
      <input type =button class=cssButton value="上 一 步" onclick="returnparent();">      
     </TD>    
     <TD  align =right class=common>
      <input type =button class=cssButton value="进入保单被保人信息" onclick="grpInsuInfo();">      
     </TD>
     
    </TR> 
    </table> 
    </Form> 
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
    </body>
</html>
