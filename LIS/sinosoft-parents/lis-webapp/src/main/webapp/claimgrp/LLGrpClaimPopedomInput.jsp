

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
    <SCRIPT src="LLGrpClaimPopedom.js"></SCRIPT>
    <SCRIPT src="LLClaimPubFun.js"></SCRIPT>
    <%@include file="LLGrpClaimPopedomInit.jsp"%>
</head>

<body  onload="initForm();">	
<form action="" method=post name=fm target="fraSubmit">   	
               
    <!--=========================================================================
        ����Ȩ��¼����Ϣ
        =========================================================================
    -->
                                     
	<Table>
	     <TR>
	     	<TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,QueryPopedom);"></TD>
	        <TD class= titleImg>����Ȩ����Ϣ��ѯ</TD>
	     </TR>
	</Table>  
	<Div  id= "QueryPopedom" style= "display:''" class="maxbox1">
	    <Table  class= common>
            <TR class= common>  
					<TD  class= title5>Ȩ�޼���</TD>
					<TD  class= input5> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=JobCategoryQ id=JobCategoryQ readonly ondblclick="return showCodeList('grpjobcategory',[this,JobCategoryNameQ],[0,1]);" onclick="return showCodeList('grpjobcategory',[this,JobCategoryNameQ],[0,1]);" onkeyup="return showCodeListKey('grpjobcategory',[this,JobCategoryNameQ],[0,1]);"><input class="codename" name="JobCategoryNameQ" id="JobCategoryNameQ" readonly></TD>             				
	                <TD  class= title5>��������</TD>      
	                <TD  class= input5> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=CaseCategoryQ id=CaseCategoryQ  readonly  ondblclick="return showCodeList('grpcasecategory',[this,CaseCategoryNameQ],[0,1]);" onclick="return showCodeList('grpcasecategory',[this,CaseCategoryNameQ],[0,1]);" onkeyup="return showCodeListKey('grpcasecategory',[this,CaseCategoryNameQ],[0,1]);"><input class="codename" name="CaseCategoryNameQ" id="CaseCategoryNameQ" readonly></TD>             				

	       </TR>    
	    </Table>  
	</Div>      
	  <input value="��  ѯ" class= cssButton type=button onclick="InitQueryClick();">
      <!--<a href="javascript:void(0);" class="button" onClick="InitQueryClick();">��    ѯ</a>-->	 
	

	<Table>
	     <TR><TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivClaimPopedomGrid);"></TD>
	          <TD class= titleImg>����Ȩ���б�</TD>
	     </TR>
	</Table>  
    <Div id= "DivClaimPopedomGrid" style= "display:''">    
		<Table  class= common>
		    <TR>
		    	<TD text-align: left colSpan=1><span id="spanClaimPopedomGrid"></span></TD>
		    </TR>
		</Table>
		 
	</Div>
	    
                                    
	<table>
	     <tr><td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,ClaimPopedom);"></td>
	          <td class= titleImg>����Ȩ��ά��</td>
	     </tr>
	</table>
  
        
	<Div  id= "ClaimPopedom" style= "display:''" class="maxbox">
       
        
        
        
        <table  class= common>
            <tr class= common>
				<TD  class= title5>Ȩ�޼���</TD>
				<TD  class= input5> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=JobCategory id=JobCategory readonly ondblclick="return showCodeList('grpjobcategory',[this,JobCategoryName],[0,1]);" onclick="return showCodeList('grpjobcategory',[this,JobCategoryName],[0,1]);" onkeyup="return showCodeListKey('vjobcategory',[this,JobCategoryName],[0,1]);"><input class="codename" name="JobCategoryName" id="JobCategoryName" readonly><font size=1 color='#ff0000'><b>*</b></font></TD>     
				<TD  class= title5>��������</TD>      
	            <TD  class= input5> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=CaseCategory id=CaseCategory readonly ondblclick="return showCodeList('grpcasecategory',[this,CaseCategoryName],[0,1]);" onclick="return showCodeList('grpcasecategory',[this,CaseCategoryName],[0,1]);" onkeyup="return showCodeListKey('grpcasecategory',[this,CaseCategoryName],[0,1]);"><input class="codename" name="CaseCategoryName" id="CaseCategoryName" readonly><font size=1 color='#ff0000'><b>*</b></font></TD>             	</tr>
                 <tr class= common>			       				
				<TD  class= title5>�������</TD>
 				<TD  class= input5> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=MngCom id=MngCom ondblclick="return showCodeList('station',[this,MngComName],[0,1]);" onclick="return showCodeList('station',[this,MngComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,MngComName],[0,1]);"><input class="codename" name="MngComName" id="MngComName" readonly><font size=1 color='#ff0000'><b>*</b></font></TD>
                <TD  class= title5>��С���</TD>
                <TD  class= input5> <Input class="wid" class=common  name=BaseMin  id=BaseMin verify="��С���|num" onchange=verifyElementWrap(this.verify,this.value,this.form.name+"."+this.name)><font size=1 color='#ff0000'><b>*</b></font></TD>
            </tr>
            <tr class= common>    
                
				<TD  class= title5>�����</TD>
                <TD  class= input5> <Input class="wid" class=common  name=BaseMax  id=BaseMax verify="�����|num" onchange=verifyElementWrap(this.verify,this.value,this.form.name+"."+this.name)><font size=1 color='#ff0000'><b>*</b></font></TD>    
				<TD  class= title5>��������</TD>       
              <td class="input5"> <Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="��������|date&NOTNULL" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span><font size=1 color='#ff0000'><b>*</b></font></TD>                       
            </tr>
            
            <tr class= common>
                <TD  class= title5>��������</TD>
                <TD  class= input5>  <Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="��������|date&NOTNULL" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>                      
                <TD  class= title5>����Ա</TD>
                <TD  class= input5> <Input class="readonly wid" readonly name=Operator id=Operator ></TD>   
                </tr>
                <tr class= common>         
                <TD  class= title5>�������</TD>       
                <TD  class= input5> <Input class="readonly wid" readonly name=MakeDate  id=MakeDate ></TD>  
                <TD  class= title5>���ʱ��</TD>
              <TD  class= input5> <Input class="readonly wid" readonly name=MakeTime id=MakeTime ></TD>    
            </tr>
            
           <tr class= common>
               
          	  <TD  class= title5>���һ���޸�����</TD>
              <TD  class= input5> <Input class="readonly wid" readonly name=ModifyDate id=ModifyDate ></TD>  
              <TD  class= title5>���һ���޸�ʱ��</TD>
              <TD  class= input5> <Input class="readonly wid" readonly name=ModifyTime id=ModifyTime ></TD>                
          </tr>
        </table>
                
     </div>   
 				<INPUT class=cssButton name="savePopedomButton" VALUE=" �� �� "  TYPE=button onclick="PopedomAddClick()" >
                <input class=cssButton name="editPopedomButton" value=" �� �� " type=button onclick="PopedomEditClick()" disabled=true>
                <INPUT class=cssButton name="deletePopedomButton" VALUE=" ɾ �� "  TYPE=button onclick="PopedomDeleteClick()" disabled=true>
                <input class=cssButton name="resetPopedomButton" value=" �� �� " type=button onclick="PopedomResetClick()" >
              <!-- <a href="javascript:void(0);" name="savePopedomButton" class="button" onClick="PopedomAddClick();">��    ��</a>
               <a href="javascript:void(0);" name="editPopedomButton" class="button" disabled=true onClick="PopedomEditClick();">��    ��</a>
               <a href="javascript:void(0);" name="deletePopedomButton" class="button" disabled=true onClick="PopedomDeleteClick();">ɾ    ��</a>
               <a href="javascript:void(0);" name="resetPopedomButton" class="button" onClick="PopedomResetClick();">��    ��</a>
	-->
  	<input type=hidden name=hideOperate value=''> 
  	<input type=hidden id="fmtransact" name="fmtransact">        
	<input type=hidden id="ClaimCheckUwFlag" name=ClaimCheckUwFlag > 
	<input type=hidden id="ClaimType" name=ClaimType > 
	<input type=hidden id="OriJobCategory" name=OriJobCategory > 
	<input type=hidden id="OriCaseCategory" name=OriCaseCategory > 
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
