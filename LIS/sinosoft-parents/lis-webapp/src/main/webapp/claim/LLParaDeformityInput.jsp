<html>
 <%@page contentType="text/html;charset=GBK" %>
 <%@include file="../common/jsp/Log4jUI.jsp"%>  
 <!--�û�У����-->
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
	          <td class= titleImg>���������ȼ�����ά��</td>
	     </tr>
	</table>
	<Div  id= "ParaDeformity" style= "display:''" class="maxbox1">
        <table  class= common>
            <tr class= common>
          
				<TD  class= title5>������������</TD>
				<TD  class= input5> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=DefoType id=DefoType ondblClick="showCodeList('lldefotype',[this,DefoTypeName],[0,1]);" onClick="showCodeList('lldefotype',[this,DefoTypeName],[0,1]);" onkeyup="showCodeListKey('lldefotype',[this,DefoTypeName],[0,1]);"><input class=codename name=DefoTypeName id=DefoTypeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                <TD  class= title5>������������</TD>        
				<TD  class= input5> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=DefoGrade id=DefoGrade ondblclick="respondDefoGrade();" onclick="respondDefoGrade();" onkeyup="respondDefoGrade();"><input class=codename name=DefoGradeName id=DefoGradeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>               

            	
          </tr>
                <tr class= common>
                <TD  class= title5>������������</TD>  
				<TD  class= input5> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=DefoCode id=DefoCode ondblclick="respondDefoCode();" onclick="respondDefoCode();" onkeyup="respondDefoCode();" onfocus="queryDeformityRate();"><input class=codename name=DefoCodeName id=DefoCodeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
				<TD  class= title5>�м���������</TD>
				<!-- >TD  class= input> <Input class=common  name=DefoRate verify="�м���������|num" onchange=verifyElementWrap(this.verify,this.value,this.form.name+"."+this.name)></TD -->
				<TD  class= input5> <Input class="wid" class=common  name=DefoRate  id=DefoRate verify="�м���������|num" onchange=verifyElementWrap(this.verify,this.value,this.form.name+"."+this.name)></TD>   
            </tr>
        </table>
                
     </div> 
    
            <INPUT style="display:none" class=cssButton name="saveDeformityButton" VALUE=" �� �� "  TYPE=button onclick="DeformityAddClick()" >
			<input style="display:none" class=cssButton name="editDeformityButton" value=" �� �� " type=button onclick="DeformityEditClick()" disabled=true>
				<INPUT style="display:none" class=cssButton name="deleteDeformityButton" VALUE=" ɾ �� "  TYPE=button onclick="DeformityDeleteClick()" disabled=true>                      
               <input style="display:none" class=cssButton name="resetDeformityButton" value=" �� �� " type=button onclick="DeformityResetClick()" >
              <a href="javascript:void(0);" name="saveDeformityButton" class="button" onClick="DeformityAddClick();">��    ��</a>
               <a href="javascript:void(0);" name="editDeformityButton" class="button" disabled=true onClick="DeformityEditClick();">��    ��</a>
               <a href="javascript:void(0);" name="deleteDeformityButton" class="button" disabled=true onClick="DeformityDeleteClick();">ɾ    ��</a>
               <a href="javascript:void(0);" name="resetDeformityButton" class="button" onClick="DeformityResetClick();">��    ��</a><br><br>
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
