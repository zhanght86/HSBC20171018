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
  <SCRIPT src="GroupRiskInput.js"></SCRIPT>
  <%@include file="GroupRiskInit.jsp"%>
</head>
<body  onload="initForm();" >
<Form action="./GroupRiskSave.jsp" method=post name=fm target="fraSubmit">
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divGroupPol1);">
    		</td>
    		<td class= titleImg>
    			 集体保单险种信息
    		</td>
    	</tr>
    </table>
    <table  class= common>
        <TR  class= common>
         <TD  class= title>
            险种投保单编码
          </TD>
          <TD  class= input>
            <Input class="readonly" readonly name=GrpProposalNo >
          </TD>
          <TD  class= title>
            集体投保单号码
          </TD>
          <TD  class= input>
            <Input class="readonly" readonly name=GrpContNo  >
          </TD>
         
          <TD  class= title>
            印刷号码
          </TD>
          <TD  class= input>
            <Input class="readonly" readonly name=PrtNo verify="印刷号码|notnull" >
          </TD>
        </TR>
        </table>
        <Div  id= "divTempFeeInput" style= "display: ''">
  <!--录入的暂交费表部分 -->
    <Table  class= common>
      	<tr>
    	 <td text-align: left colSpan=1>
	 <span id="spanRiskGrid" >
	 </span> 
	</td>
       </tr>
    </Table>
    </div>
    <table>
    <table>
    <TR >     
     <TD >
      <input type =button class=cssButton value="添加险种" onclick="addRecord();">      
     </TD>
     <TD >
      <input type =button class=cssButton value="删除险种" onclick="deleteRecord();">      
     </TD>
    </TR> 
    </table> 
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
      <TD  class= title>
            *险种编码
          </TD>
           <TD  class= input>
            <Input class="code" name=RiskCode ondblclick="return showCodeList('RiskGrp',[this]);" onkeyup="return showCodeListKey('RiskGrp',[this]);">
          </TD>
          </TR>
        <TR  class= common>
          <TD  class= title>
            *保单生效日期 
          </TD>
          <TD  class= input>
            <Input class="coolDatePicker" dateFormat="short" name=CValiDate >
          </TD>    
          <TD  class= title>
            *交费周期
          </TD>
          <TD  class= input>
            <Input class="code" name=PayIntv ondblclick="return showCodeList('PayIntv',[this]);">
          </TD>     
        </TR>
        </Div>
       
     
  <table class=common>
   <TR  class= common> 
      <TD  class= title> 备注 </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
      <textarea name="Remark" cols="120" rows="3" class="common" >
      </textarea></TD>
    </TR>
  </table>  
   
    </Div>
   
    <Div  id= "divHidden" style= "display: 'none'">
      <TR  class= common>          
          <TD  class= title>
            封顶线
          </TD>
          <TD  class= input>
            <Input class= common name=PeakLine >
          </TD>
        </TR>
        <TR  class= common>          
          <TD  class= title>
            医疗费用限额
          </TD>
          <TD  class= input>
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
       
      <INPUT class=cssButton VALUE="进入管理费"  TYPE=button onclick="grpFeeInput()">     
     </TD>
     
    </TR> 
    <input type=hidden id="fmAction" name="fmAction">
    </table> 
    </Form> 
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
    </body>
</html>
