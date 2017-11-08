<html>
 <%@page contentType="text/html;charset=GBK" %>
 <%@include file="../common/jsp/Log4jUI.jsp"%>  
 <!--用户校验类-->
 <%@page import = "com.sinosoft.utility.*"%>
 <%@page import = "com.sinosoft.lis.schema.*"%>
 <%@page import = "com.sinosoft.lis.vschema.*"%>
 <%@include file="../common/jsp/UsrCheck.jsp"%>
 
<head >
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="LLParaDeformity.js"></SCRIPT>
    <%@include file="LLParaDeformityInit.jsp"%>
</head>

<body  onload="initForm();">	
<form action="" method=post name=fm id=fm target="fraSubmit">   	
	<table>
	     <tr><td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,ParaDeformity);"></td>
	          <td class= titleImg>比例给付等级参数维护</td>
	     </tr>
	</table>
	<Div  id= "ParaDeformity" style= "display:''" class="maxbox1">
        <table  class= common>
            <tr class= common>
          
				<TD  class= title5>比例给付类型</TD>
				<TD  class= input5> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=DefoType id=DefoType ondblClick="showCodeList('lldefotype',[this,DefoTypeName],[0,1]);" onClick="showCodeList('lldefotype',[this,DefoTypeName],[0,1]);" onkeyup="showCodeListKey('lldefotype',[this,DefoTypeName],[0,1]);"><input class=codename name=DefoTypeName id=DefoTypeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                <TD  class= title5>比例给付级别</TD>        
				<TD  class= input5> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=DefoGrade id=DefoGrade ondblclick="respondDefoGrade();" onclick="respondDefoGrade();" onkeyup="respondDefoGrade();"><input class=codename name=DefoGradeName id=DefoGradeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>               

            	
          </tr>
                <tr class= common>
                <TD  class= title5>比例给付代码</TD>  
				<TD  class= input5> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=DefoCode id=DefoCode ondblclick="respondDefoCode();" onclick="respondDefoCode();" onkeyup="respondDefoCode();" onfocus="queryDeformityRate();"><input class=codename name=DefoCodeName id=DefoCodeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
				<TD  class= title5>残疾给付比例</TD>
				<!-- >TD  class= input> <Input class=common  name=DefoRate verify="残疾给付比例|num" onchange=verifyElementWrap(this.verify,this.value,this.form.name+"."+this.name)></TD -->
				<TD  class= input5> <Input class="wid" class=common  name=DefoRate  id=DefoRate verify="残疾给付比例|num" onchange=verifyElementWrap(this.verify,this.value,this.form.name+"."+this.name)></TD>   
            </tr>
        </table>
                
     </div> 
    
            <INPUT style="display:none" class=cssButton name="saveDeformityButton" VALUE=" 增 加 "  TYPE=button onclick="DeformityAddClick()" >
			<input style="display:none" class=cssButton name="editDeformityButton" value=" 修 改 " type=button onclick="DeformityEditClick()" disabled=true>
				<INPUT style="display:none" class=cssButton name="deleteDeformityButton" VALUE=" 删 除 "  TYPE=button onclick="DeformityDeleteClick()" disabled=true>                      
               <input style="display:none" class=cssButton name="resetDeformityButton" value=" 重 置 " type=button onclick="DeformityResetClick()" >
              <a href="javascript:void(0);" name="saveDeformityButton" class="button" onClick="DeformityAddClick();">增    加</a>
               <a href="javascript:void(0);" name="editDeformityButton" class="button" disabled=true onClick="DeformityEditClick();">修    改</a>
               <a href="javascript:void(0);" name="deleteDeformityButton" class="button" disabled=true onClick="DeformityDeleteClick();">删    除</a>
               <a href="javascript:void(0);" name="resetDeformityButton" class="button" onClick="DeformityResetClick();">重    置</a><br><br>
    <div id= "divLLInqCourse" style= "display:''">    
		<Table  class= common>
				<tr></tr>
		    <tr><td text-align: left colSpan=1>
		        <span id="spanParaDeformityGrid"></span> 
		    </td></tr>
		</Table>
		
	</div>
	
  <input type=hidden name=hideOperate value=''> 
  <input type=hidden id="fmtransact" name="fmtransact">        
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
