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
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="GroupRiskPlanInput.js"></SCRIPT>
  <%@include file="GroupRiskPlanInit.jsp"%>
</head>
<body  onload="initForm();" >
<Form action="./ProposalSave.jsp" method=post name=fm id=fm target="fraSubmit">
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
	<div class=maxbox1>
    <table  class= common>
        <TR  class= common>
         <TD  class= title>
            险种投保单编码
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name=GrpProposalNo id=GrpProposalNo>
          </TD>
          <TD  class= title>
            集体投保单号码
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name=ContNo id=ContNo >
          </TD>
         
          <TD  class= title>
            印刷号码
          </TD>
          <TD  class=input>
            <Input class="readonly wid" readonly name=PrtNo id=PrtNo verify="印刷号码|notnull" >
          </TD>
        </TR>
        </table>
    </div>
	
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
    <Div  id= "divGroupPol3" class=maxbox style= "display: ''">
      <table  class= common>
      <TR class=common>
      <TD  class= title>
            *险种编码
          </TD>
           <TD  class= input>
            <Input class="code" name=RiskCode id=RiskCode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('RiskGrp',[this]);" onkeyup="return showCodeListKey('RiskGrp',[this]);">
          </TD>
          </TR>
        <TR  class= common>
          <TD  class= title>
            投保申请日期 
          </TD>
          <TD  class= input>
            <Input class="coolDatePicker wid" dateFormat="short" name=PolApplyDate id=PolApplyDate>
          </TD> 
          <TD  class= title>
            *保单生效日期 
          </TD>
          <TD  class= input>
            <Input class="coolDatePicker wid" dateFormat="short" name=CValiDate id=CValiDate>
          </TD>    
          <TD  class= title>
            *交费周期
          </TD>
          <TD  class= input>
            <Input class="code wid" name=PayIntv id=PayIntv style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('PayIntv',[this]);">
          </TD>     
        </TR>
        
        <TR  class= common>
          
          <TD  class= title>
            保险期间
          </TD>
          <TD  class= input>
             <Input class="common wid" name=InsuYear id=InsuYearInsuYear value="0">
          </TD>
             <TD  class= title>
            *浮动费率
          </TD>
          <TD  class= input>
             <Input class="common wid" name=FloatRate id=FloatRate value="0">
          </TD>
             <TD  class= title>
            *计算方向
          </TD>
          <TD  class= input>
             <Input class="code"  CodeData="0|^P|保额算保费|^A|保费算保额|^O|其他算保费" name=CalDirect id=CalDirect style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblClick="showCodeListEx('CalDirect',[this],[0]);" onkeyup="showCodeListKeyEx('CalDirect',[this],[0]);" value="P">
          </TD>
          
          
        </TR>
        <tr class=common>
        <TD  class= title>
            雇员自付比例
          </TD>
          <TD  class= input>
            <Input class="common wid" name=EmployeeRate id=EmployeeRate verify="雇员自付比例|num&len<=5">
          </TD>
          <TD  class= title>
            家属自付比例
          </TD>
          <TD  class= input>
            <Input class="common wid" name=FamilyRate id=FamilyRate verify="家属自付比例|num&len<=80">
          </TD>
		  <TD  class= title></TD>
          <TD  class= input></TD>
          </tr>
        </Table>
        </Div>
</div>       
     
  <table class=common>
   <TR  class=common> 
      <TD  class= title> 计划描述 </TD>
    </TR>
    <TR  class=common>
      <TD  class= title>
      <textarea name="GrpSpec" id=GrpSpec cols="120" rows="3" class="common wid1" >
      </textarea>
	  </TD>
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
    	 <td style="text-align: left" colSpan=1>
	 <span id="spanTempGrid" >
	 </span> 
	</td>
       </tr>
    </Table>
    </div>
    <Div  id= "divHidden" style= "display: none">
       <TR class=common>          
          <TD  class= title>
            封顶线
          </TD>
          <TD class= input>
            <Input class="common wid" name=PeakLine id=PeakLine >
          </TD>
        </TR>
        <TR  class=common>          
          <TD  class= title>
            医疗费用限额
          </TD>
          <TD  class= input>
            <Input class="common wid" name=MaxMedFee id=MaxMedFee >
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
	<br /><br /><br /><br />
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
    </body>
</html>
