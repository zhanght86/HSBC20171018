<html>
 <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
 <!--�û�У����-->
 <%@page import = "com.sinosoft.utility.*"%>
 <%@page import = "com.sinosoft.lis.schema.*"%>
 <%@page import = "com.sinosoft.lis.vschema.*"%>
 <%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�LLGrpClaimUserInput.jsp
//�����ܣ����������û�������
//�������ڣ�2005-7-11 
//������  ������
//���¼�¼��  ������ yuejw    ��������     ����ԭ��/����
//            zhouming        2006/04/30   ����ڸ�״̬
%> 
<head>
	<%
    GlobalInput tGlobalInput = new GlobalInput();
    tGlobalInput = (GlobalInput)session.getValue("GI");
    %>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
    <SCRIPT src="LLGrpClaimUser.js"></SCRIPT>
    <%@include file="LLGrpClaimUserInit.jsp"%>
</head>

<body  onload="initForm();">	
<form action="" method=post name=fm id=fm target="fraSubmit">   	
    <!-- �����û�����-->    
                                    
	<Table>
	     <TR>
	     	<TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,ClaimUser);"></TD>
	        <TD class= titleImg>�����û���ѯ</TD>
	     </TR>
	</Table>  
	<Div  id= "ClaimUser" style= "display:''" class="maxbox">
	    <Table  class= common>
          <TR class= common>  
				<TD  class= title>�û�����</TD>
				<TD  class= input> <input class="wid" class=common name=UserCodeQ id=UserCodeQ></TD>
	        	<TD  class= title>�û�����</TD>      
	        	<TD  class= input> <input class="wid" class=common name=UserNameQ id=UserNameQ></TD>
                <TD  class= title>�������</TD>            
        		<TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=ComCodeQ id=ComCodeQ ondblclick="return showCodeList('station',[this,ComCodeQName],[0,1]);" onclick="return showCodeList('station',[this,ComCodeQName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ComCodeQName],[0,1]);"><input class="codename" name="ComCodeQName" id="ComCodeQName" readonly></TD>
                </TR>
                <TR class= common>
	        	   
                <TD  class= title>Ȩ�޼���</TD>
			<TD  class= input> <Input  style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=JobCategoryQ id=JobCategoryQ readonly ondblclick="return showCodeList('grpjobcategory',[this,JobCategoryNameQ],[0,1]);" onclick="return showCodeList('grpjobcategory',[this,JobCategoryNameQ],[0,1]);" onkeyup="return showCodeListKey('grpjobcategory',[this,JobCategoryNameQ],[0,1]);"><input class="codename" name="JobCategoryNameQ" id="JobCategoryNameQ" readonly></TD> 
            <TD  class= title>�ڸ�״̬</TD>            
            <TD class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly name=RgtFlagQ id=RgtFlagQ ondblclick="return showCodeList('llclaimuserflag',[this,RgtFlagQName],[0,1]);" onclick="return showCodeList('llclaimuserflag',[this,RgtFlagQName],[0,1]);" onkeyup="return showCodeListKey('llclaimuserflag',[this,RgtFlagQName],[0,1]);"><input class=codename name=RgtFlagQName id=RgtFlagQName readonly=true></TD> 
			<TD  class= title></TD>
			<TD  class= input> </TD>      
	       </TR>   
	       
	    </Table>  	</Div>   

				<input value="��  ѯ" class= cssButton type=button onclick="InitQueryClick();">
				<input value="��  ��" class= cssButton type=button onclick="ClearQuerydataClick();"> 
                <!--<a href="javascript:void(0);" class="button" onClick="InitQueryClick();">��    ѯ</a>
                <a href="javascript:void(0);" class="button" onClick="ClearQuerydataClick();">��    ��</a>-->
      	 
    

	<Table>
	     <TR><TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivGrpClaimUserGrid);"></TD>
	          <TD class= titleImg>�����û��б�</TD>
	     </TR>
	</Table>  
    <Div id= "DivGrpClaimUserGrid" style= "display:''">    
		<Table  class= common>
		    <TR>
		    	<TD text-align: left colSpan=1><span id="spanLLGrpClaimUserGrid"></span></TD>
		    </TR>
		</Table>
		<!--<Table>
            <TR>
                <TD><INPUT class=cssButton VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"></TD>
                <TD><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"></TD>
                <TD><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"></TD>
                <TD><INPUT class=cssButton VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();"></TD>
            </TR>
        </Table>-->
	</Div>
    
	<Table>
	     <TR><TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLLClaimUserInfo);"></TD>
	          <TD class= titleImg>�����û�Ȩ����Ϣ</TD>
	     </TR>
	</Table>  
    <Div id= "DivLLClaimUserInfo" style= "display:''" class="maxbox">       
           
        <Table  class= common>
            <TR class= common>   
				<TD  class= title>�û�����</TD>
				<TD  class= input> <input class="readonly wid"  name=UserCode id=UserCode></TD>
                <TD  class= title>�û�����</TD>      
                <TD  class= input> <input class="readonly wid"  name=UserName id=UserName></TD>
                <TD  class= title>�������</TD>            
                <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=ComCode id=ComCode ondblclick="return showCodeList('station',[this,ComCodeName],[0,1]);" onclick="return showCodeList('station',[this,ComCodeName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ComCodeName],[0,1]);"><input class="codename" name="ComCodeName" id="ComCodeName" readonly></TD>
                 </TR>
               <TR class= common>      
                
                <TD  class= title>�ϼ��û�����</TD>
                <TD  class= input> <input class="wid" class=common name=UpUserCode  id=UpUserCode ></TD>
                <TD  class= title>�ϼ��û�����</TD>
                <TD  class= input> <input class="wid" class=common name=UpUserName ></TD>
                <TD  class= title>Ȩ�޼���</TD>
				<TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=JobCategory id=JobCategory readonly ondblclick="return showCodeList('grpjobcategory',[this,JobCategoryName],[0,1]);" onclick="return showCodeList('grpjobcategory',[this,JobCategoryName],[0,1]);" onkeyup="return showCodeListKey('grpjobcategory',[this,JobCategoryName],[0,1]);"><input class="codename" name="JobCategoryName" id="JobCategoryName" readonly></TD>          
		     </TR>
            
                <!--  TD  class= title>��Ч��ʶ</TD>
				<TD  class= input><input class=codeno readonly name=StateFlag CodeData="0|3^0|��Ч^1|��Ч" ondblclick="return showCodeListEx('StateFlag', [this,StateFlagName],[0,1],'','','','',150);"onkeyup="return showCodeListKeyEx('StateFlag', [this,StateFlagName],[0,1],'','','','',150);"><input class=codename name=StateFlagName readonly=true></TD>  -->
				
				   				
           
            <TR class= common>    
                <TD  class= title>����Ȩ��</TD>
                <TD  class= input><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly name=ReportFlag id=ReportFlag CodeData="0|3^0|�ޱ���Ȩ��^1|�б���Ȩ��" ondblclick="return showCodeListEx('ReportFlag', [this,ReportFlagName],[0,1],'','','','',150)" onclick="return showCodeListEx('ReportFlag', [this,ReportFlagName],[0,1],'','','','',150)" onkeyup="return showCodeListKeyEx('ReportFlag', [this,ReportFlagName],[0,1],'','','','',150);"><input class=codename name=ReportFlagName id=ReportFlagName readonly=true></TD>
				<TD  class= title>����Ȩ��</TD>
                <TD  class= input> <input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly name=RegisterFlag id=RegisterFlag CodeData="0|3^0|������Ȩ��^1|������Ȩ��" ondblclick="return showCodeListEx('RegisterFlag', [this,RegisterFlagName],[0,1],'','','','',150);" onclick="return showCodeListEx('RegisterFlag', [this,RegisterFlagName],[0,1],'','','','',150);"onkeyup="return showCodeListKeyEx('RegisterFlag', [this,RegisterFlagName],[0,1],'','','','',150);"><input class=codename name=RegisterFlagName id=RegisterFlagName readonly=true></TD>  
                <TD  class= title>�ʱ�Ȩ��</TD>
                <TD  class= input> <input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly name=SubmitFlag id=SubmitFlag CodeData="0|3^0|�޳ʱ�Ȩ��^1|�гʱ�Ȩ��" ondblclick="return showCodeListEx('SubmitFlag', [this,SubmitFlagName],[0,1],'','','','',150);" onclick="return showCodeListEx('SubmitFlag', [this,SubmitFlagName],[0,1],'','','','',150);"onkeyup="return showCodeListKeyEx('SubmitFlag', [this,SubmitFlagName],[0,1],'','','','',150);"><input class=codename name=SubmitFlagName id=SubmitFlagName readonly=true></TD>   
                </TR>
               <TR class= common>             
				
                <TD  class= title>����Ȩ��</TD>
                <TD  class= input><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly name=SurveyFlag id=SurveyFlag CodeData="0|3^0|�޵���Ȩ��^1|�е���Ȩ��" ondblclick="return showCodeListEx('SurveyFlag', [this,SurveyFlagName],[0,1],'','','','',150);" onclick="return showCodeListEx('SurveyFlag', [this,SurveyFlagName],[0,1],'','','','',150);"onkeyup="return showCodeListKeyEx('SurveyFlag', [this,SurveyFlagName],[0,1],'','','','',150);"><input class=codename name=SurveyFlagName id=SurveyFlagName readonly=true></TD>  
                <TD  class= title>Ԥ��Ȩ��</TD>
				<TD  class= input> <input style="background:url(../common/images/select--bg_03.png) no-repeat right center"class=codeno readonly name=PrepayFlag id=PrepayFlag CodeData="0|3^0|��Ԥ��Ȩ��^1|��Ԥ��Ȩ��" ondblclick="return showCodeListEx('PrepayFlag', [this,PrepayFlagName],[0,1],'','','','',150);" onclick="return showCodeListEx('PrepayFlag', [this,PrepayFlagName],[0,1],'','','','',150);"onkeyup="return showCodeListKeyEx('PrepayFlag', [this,PrepayFlagName],[0,1],'','','','',150);"><input class=codename name=PrepayFlagName id=PrepayFlagName readonly=true></TD>
				<TD  class= title>���װ���Ȩ��</TD>
				<TD  class= input> <input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno  readonly name=SimpleFlag id=SimpleFlag CodeData="0|3^0|�޼��װ���Ȩ��^1|�м��װ���Ȩ��" ondblclick="return showCodeListEx('SimpleFlag',[this,SimpleFlagName],[0,1],'','','','',150);" onclick="return showCodeListEx('SimpleFlag',[this,SimpleFlagName],[0,1],'','','','',150);" onkeyup="return showCodeListKeyEx('SimpleFlag',[this,SimpleFlagName],[0,1],'','','','',150);"><input class=codename name=SimpleFlagName id=SimpleFlagName readonly=true></TD>            
            </TR>
			
			<TR class= common>    
				<TD  class= title>��֤ɨ��Ȩ��</TD>
				<TD  class= input> <input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly name=CertifyScan id=CertifyScan CodeData="0|3^0|�޵�֤ɨ��Ȩ��^1|�е�֤ɨ��Ȩ��" ondblclick="return showCodeListEx('CertifyScan', [this,CertifyScanName],[0,1],'','','','',150);" onclick="return showCodeListEx('CertifyScan', [this,CertifyScanName],[0,1],'','','','',150);"onkeyup="return showCodeListKeyEx('CertifyScan', [this,CertifyScanName],[0,1],'','','','',150);"><input class=codename name=CertifyScanName id=CertifyScanName readonly=true></TD>
				<TD  class= title>�������Ȩ��</TD>
				<TD  class= input> <input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno  readonly name=TaskAssign id=TaskAssign CodeData="0|3^0|���������Ȩ��^1|���������Ȩ��" ondblclick="return showCodeListEx('TaskAssign',[this,TaskAssignName],[0,1],'','','','',150);" onclick="return showCodeListEx('TaskAssign',[this,TaskAssignName],[0,1],'','','','',150);" onkeyup="return showCodeListKeyEx('TaskAssign',[this,TaskAssignName],[0,1],'','','','',150);"><input class=codename name=TaskAssignName id=TaskAssignName readonly=true></TD>
                <TD  class= title>���Ȩ��</TD>
				<TD  class= input> <input  style="background:url(../common/images/select--bg_03.png) no-repeat right center"class=codeno readonly name=CheckFlag id=CheckFlag CodeData="0|3^0|�����Ȩ��^1|�����Ȩ��" ondblclick="return showCodeListEx('CheckFlag', [this,CheckFlagName],[0,1],'','','','',150);" onclick="return showCodeListEx('CheckFlag', [this,CheckFlagName],[0,1],'','','','',150);"onkeyup="return showCodeListKeyEx('CheckFlag', [this,CheckFlagName],[0,1],'','','','',150);"><input class=codename name=CheckFlagName id=CheckFlagName readonly=true></TD>
                  </TR>
                 <TR class= common>
				
                <TD  class= title>����Ȩ��</TD>
                <TD  class= input> <input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly name=UWFlag id=UWFlag CodeData="0|3^0|������Ȩ��^1|������Ȩ��" ondblclick="return showCodeListEx('UWFlag', [this,UWFlagName],[0,1],'','','','',150);"  onclick="return showCodeListEx('UWFlag', [this,UWFlagName],[0,1],'','','','',150);"onkeyup="return showCodeListKeyEx('UWFlag', [this,UWFlagName],[0,1],'','','','',150);"><input class=codename name=UWFlagName id=UWFlagName readonly=true></TD>  
                <TD  class= title>����Ȩ��</TD>
				<TD  class= input> <input  style="background:url(../common/images/select--bg_03.png) no-repeat right center"class=codeno readonly name=ExemptFlag id=ExemptFlag CodeData="0|3^0|�޻���Ȩ��^1|�л���Ȩ��" ondblclick="return showCodeListEx('ExemptFlag', [this,ExemptFlagName],[0,1],'','','','',150);" onclick="return showCodeListEx('ExemptFlag', [this,ExemptFlagName],[0,1],'','','','',150);"onkeyup="return showCodeListKeyEx('ExemptFlag', [this,ExemptFlagName],[0,1],'','','','',150);"><input class=codename name=ExemptFlagName id=ExemptFlagName readonly=true></TD>
				<TD  class= title>�ڸ�״̬</TD>
				<TD  class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly name=RgtFlag id=RgtFlag ondblclick="return showCodeList('llclaimuserflag',[this,RgtFlagName],[0,1]);" onclick="return showCodeList('llclaimuserflag',[this,RgtFlagName],[0,1]);" onkeyup="return showCodeListKey('llclaimuserflag',[this,RgtFlagName],[0,1]);"><input class=codename name=RgtFlagName id=RgtFlagName readonly=true></TD>
			</TR>            
            <TR class= common>  
				
				   		  
				<!-- <TD  class= title>��˼���</TD>
				<TD  class= input><Input class=codeno readonly name=CheckLevel ondblclick="return showCodeList('popedomlevel',[this,CheckLevelName],[0,1],null,fm.all('tCheckLevel').value,'popedomlevel','1');" onkeyup="return showCodeListKey('popedomlevel',[this,CheckLevelName],[0,1],null,fm.all('tCheckLevel').value,'popedomlevel','1');"><input class=codename name=CheckLevelName readonly=true></TD>				
				<TD  class= title>��������</TD>  
				<TD  class= input><Input class=codeno readonly name=UWLevel ondblclick="return showCodeList('popedomlevel',[this,UWLevelName],[0,1],null,fm.all('tUWLevel').value,'popedomlevel','1');" onkeyup="return showCodeListKey('popedomlevel',[this,UWLevelName],[0,1],null,fm.all('tUWLevel').value,'popedomlevel','1');"><input class=codename name=UWLevelName readonly=true></TD> -->		 
            </TR>
            <TR class= common>   
 				<TD  class= title>�����绰</TD>
                <TD  class= input> <input class="wid" class=common name=RelPhone id=RelPhone ></TD> 	
				<TD  class= title></TD>
                <TD  class= input></TD> <TD  class= title></TD>
                <TD  class= input></TD> 	 	
			</TR>

		</Table>                             
    </Div>   
<input class=cssButton name="saveUserButton"   value="��  ��"  type=button onclick="UserAddClick();">
				<input class=cssButton name="editUserButton"   value="��  ��" disabled type=button onclick="UserEditClick();">
				<input class=cssButton name="deleteUserButton" value="ɾ  ��" disabled type=button onclick="UserDeleteClick();">                     
               <input class=cssButton name="resetUserButton"  value="��  ��" type=button onclick="UserResetClick();">               
                <input class=cssButton name="queryUserButton"  value="�û���ѯ" type=button onclick="userQueryClick();">
                
                <!--<a href="javascript:void(0);" name="saveUserButton" class="button" disabled onClick="UserAddClick();">��    ��</a>
                <a href="javascript:void(0);" name="editUserButton" class="button" disabled onClick="UserEditClick();">��    ��</a>
                <a href="javascript:void(0);" name="deleteUserButton" class="button" disabled onClick="UserDeleteClick();">ɾ    ��</a>
                <a href="javascript:void(0);" name="resetUserButton" class="button" disabled onClick="UserResetClick();">��    ��</a>
                <a href="javascript:void(0);" name="queryUserButton" class="button" disabled onClick="userQueryClick();">�û���ѯ</a>-->
    <!--���ر�����-->	
    <input type=hidden id="tCheckLevel" name=tCheckLevel value="A%" > <!-- ��˼���,���ڲ�ѯ-->
	<input type=hidden id="tUWLevel" name=tUWLevel value="B%" >  <!-- ��������,���ڲ�ѯ-->

	<input type=hidden id="tOperator" name=tOperator > 
	<input type=hidden id="CheckLevel" name=CheckLevel > 
.	<input type=hidden id="CheckLevelName" name=CheckLevelName > 
	<input type=hidden id="UWLevel" name=UWLevel > 
	<input type=hidden id="UWLevelName" name=UWLevelName > 
	<input type=hidden id="tComCode" name=tComCode > 	
	<input type=hidden id="fmtransact" name="fmtransact">        
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
