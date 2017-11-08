<html>
 <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
 <!--用户校验类-->
 <%@page import = "com.sinosoft.utility.*"%>
 <%@page import = "com.sinosoft.lis.schema.*"%>
 <%@page import = "com.sinosoft.lis.vschema.*"%>
 <%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：LLGrpClaimUserInput.jsp
//程序功能：团险理赔用户管理保存
//创建日期：2005-7-11 
//创建人  ：曲洋
//更新记录：  更新人 yuejw    更新日期     更新原因/内容
//            zhouming        2006/04/30   添加在岗状态
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
    <!-- 理赔用户管理-->    
                                    
	<Table>
	     <TR>
	     	<TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,ClaimUser);"></TD>
	        <TD class= titleImg>理赔用户查询</TD>
	     </TR>
	</Table>  
	<Div  id= "ClaimUser" style= "display:''" class="maxbox">
	    <Table  class= common>
          <TR class= common>  
				<TD  class= title>用户编码</TD>
				<TD  class= input> <input class="wid" class=common name=UserCodeQ id=UserCodeQ></TD>
	        	<TD  class= title>用户姓名</TD>      
	        	<TD  class= input> <input class="wid" class=common name=UserNameQ id=UserNameQ></TD>
                <TD  class= title>管理机构</TD>            
        		<TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=ComCodeQ id=ComCodeQ ondblclick="return showCodeList('station',[this,ComCodeQName],[0,1]);" onclick="return showCodeList('station',[this,ComCodeQName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ComCodeQName],[0,1]);"><input class="codename" name="ComCodeQName" id="ComCodeQName" readonly></TD>
                </TR>
                <TR class= common>
	        	   
                <TD  class= title>权限级别</TD>
			<TD  class= input> <Input  style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=JobCategoryQ id=JobCategoryQ readonly ondblclick="return showCodeList('grpjobcategory',[this,JobCategoryNameQ],[0,1]);" onclick="return showCodeList('grpjobcategory',[this,JobCategoryNameQ],[0,1]);" onkeyup="return showCodeListKey('grpjobcategory',[this,JobCategoryNameQ],[0,1]);"><input class="codename" name="JobCategoryNameQ" id="JobCategoryNameQ" readonly></TD> 
            <TD  class= title>在岗状态</TD>            
            <TD class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly name=RgtFlagQ id=RgtFlagQ ondblclick="return showCodeList('llclaimuserflag',[this,RgtFlagQName],[0,1]);" onclick="return showCodeList('llclaimuserflag',[this,RgtFlagQName],[0,1]);" onkeyup="return showCodeListKey('llclaimuserflag',[this,RgtFlagQName],[0,1]);"><input class=codename name=RgtFlagQName id=RgtFlagQName readonly=true></TD> 
			<TD  class= title></TD>
			<TD  class= input> </TD>      
	       </TR>   
	       
	    </Table>  	</Div>   

				<input value="查  询" class= cssButton type=button onclick="InitQueryClick();">
				<input value="重  填" class= cssButton type=button onclick="ClearQuerydataClick();"> 
                <!--<a href="javascript:void(0);" class="button" onClick="InitQueryClick();">查    询</a>
                <a href="javascript:void(0);" class="button" onClick="ClearQuerydataClick();">重    填</a>-->
      	 
    

	<Table>
	     <TR><TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivGrpClaimUserGrid);"></TD>
	          <TD class= titleImg>理赔用户列表</TD>
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
                <TD><INPUT class=cssButton VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"></TD>
                <TD><INPUT class=cssButton VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"></TD>
                <TD><INPUT class=cssButton VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"></TD>
                <TD><INPUT class=cssButton VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();"></TD>
            </TR>
        </Table>-->
	</Div>
    
	<Table>
	     <TR><TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLLClaimUserInfo);"></TD>
	          <TD class= titleImg>理赔用户权限信息</TD>
	     </TR>
	</Table>  
    <Div id= "DivLLClaimUserInfo" style= "display:''" class="maxbox">       
           
        <Table  class= common>
            <TR class= common>   
				<TD  class= title>用户编码</TD>
				<TD  class= input> <input class="readonly wid"  name=UserCode id=UserCode></TD>
                <TD  class= title>用户姓名</TD>      
                <TD  class= input> <input class="readonly wid"  name=UserName id=UserName></TD>
                <TD  class= title>管理机构</TD>            
                <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=ComCode id=ComCode ondblclick="return showCodeList('station',[this,ComCodeName],[0,1]);" onclick="return showCodeList('station',[this,ComCodeName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ComCodeName],[0,1]);"><input class="codename" name="ComCodeName" id="ComCodeName" readonly></TD>
                 </TR>
               <TR class= common>      
                
                <TD  class= title>上级用户编码</TD>
                <TD  class= input> <input class="wid" class=common name=UpUserCode  id=UpUserCode ></TD>
                <TD  class= title>上级用户名称</TD>
                <TD  class= input> <input class="wid" class=common name=UpUserName ></TD>
                <TD  class= title>权限级别</TD>
				<TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=JobCategory id=JobCategory readonly ondblclick="return showCodeList('grpjobcategory',[this,JobCategoryName],[0,1]);" onclick="return showCodeList('grpjobcategory',[this,JobCategoryName],[0,1]);" onkeyup="return showCodeListKey('grpjobcategory',[this,JobCategoryName],[0,1]);"><input class="codename" name="JobCategoryName" id="JobCategoryName" readonly></TD>          
		     </TR>
            
                <!--  TD  class= title>有效标识</TD>
				<TD  class= input><input class=codeno readonly name=StateFlag CodeData="0|3^0|无效^1|有效" ondblclick="return showCodeListEx('StateFlag', [this,StateFlagName],[0,1],'','','','',150);"onkeyup="return showCodeListKeyEx('StateFlag', [this,StateFlagName],[0,1],'','','','',150);"><input class=codename name=StateFlagName readonly=true></TD>  -->
				
				   				
           
            <TR class= common>    
                <TD  class= title>报案权限</TD>
                <TD  class= input><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly name=ReportFlag id=ReportFlag CodeData="0|3^0|无报案权限^1|有报案权限" ondblclick="return showCodeListEx('ReportFlag', [this,ReportFlagName],[0,1],'','','','',150)" onclick="return showCodeListEx('ReportFlag', [this,ReportFlagName],[0,1],'','','','',150)" onkeyup="return showCodeListKeyEx('ReportFlag', [this,ReportFlagName],[0,1],'','','','',150);"><input class=codename name=ReportFlagName id=ReportFlagName readonly=true></TD>
				<TD  class= title>立案权限</TD>
                <TD  class= input> <input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly name=RegisterFlag id=RegisterFlag CodeData="0|3^0|无立案权限^1|有立案权限" ondblclick="return showCodeListEx('RegisterFlag', [this,RegisterFlagName],[0,1],'','','','',150);" onclick="return showCodeListEx('RegisterFlag', [this,RegisterFlagName],[0,1],'','','','',150);"onkeyup="return showCodeListKeyEx('RegisterFlag', [this,RegisterFlagName],[0,1],'','','','',150);"><input class=codename name=RegisterFlagName id=RegisterFlagName readonly=true></TD>  
                <TD  class= title>呈报权限</TD>
                <TD  class= input> <input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly name=SubmitFlag id=SubmitFlag CodeData="0|3^0|无呈报权限^1|有呈报权限" ondblclick="return showCodeListEx('SubmitFlag', [this,SubmitFlagName],[0,1],'','','','',150);" onclick="return showCodeListEx('SubmitFlag', [this,SubmitFlagName],[0,1],'','','','',150);"onkeyup="return showCodeListKeyEx('SubmitFlag', [this,SubmitFlagName],[0,1],'','','','',150);"><input class=codename name=SubmitFlagName id=SubmitFlagName readonly=true></TD>   
                </TR>
               <TR class= common>             
				
                <TD  class= title>调查权限</TD>
                <TD  class= input><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly name=SurveyFlag id=SurveyFlag CodeData="0|3^0|无调查权限^1|有调查权限" ondblclick="return showCodeListEx('SurveyFlag', [this,SurveyFlagName],[0,1],'','','','',150);" onclick="return showCodeListEx('SurveyFlag', [this,SurveyFlagName],[0,1],'','','','',150);"onkeyup="return showCodeListKeyEx('SurveyFlag', [this,SurveyFlagName],[0,1],'','','','',150);"><input class=codename name=SurveyFlagName id=SurveyFlagName readonly=true></TD>  
                <TD  class= title>预付权限</TD>
				<TD  class= input> <input style="background:url(../common/images/select--bg_03.png) no-repeat right center"class=codeno readonly name=PrepayFlag id=PrepayFlag CodeData="0|3^0|无预付权限^1|有预付权限" ondblclick="return showCodeListEx('PrepayFlag', [this,PrepayFlagName],[0,1],'','','','',150);" onclick="return showCodeListEx('PrepayFlag', [this,PrepayFlagName],[0,1],'','','','',150);"onkeyup="return showCodeListKeyEx('PrepayFlag', [this,PrepayFlagName],[0,1],'','','','',150);"><input class=codename name=PrepayFlagName id=PrepayFlagName readonly=true></TD>
				<TD  class= title>简易案件权限</TD>
				<TD  class= input> <input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno  readonly name=SimpleFlag id=SimpleFlag CodeData="0|3^0|无简易案件权限^1|有简易案件权限" ondblclick="return showCodeListEx('SimpleFlag',[this,SimpleFlagName],[0,1],'','','','',150);" onclick="return showCodeListEx('SimpleFlag',[this,SimpleFlagName],[0,1],'','','','',150);" onkeyup="return showCodeListKeyEx('SimpleFlag',[this,SimpleFlagName],[0,1],'','','','',150);"><input class=codename name=SimpleFlagName id=SimpleFlagName readonly=true></TD>            
            </TR>
			
			<TR class= common>    
				<TD  class= title>单证扫描权限</TD>
				<TD  class= input> <input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly name=CertifyScan id=CertifyScan CodeData="0|3^0|无单证扫描权限^1|有单证扫描权限" ondblclick="return showCodeListEx('CertifyScan', [this,CertifyScanName],[0,1],'','','','',150);" onclick="return showCodeListEx('CertifyScan', [this,CertifyScanName],[0,1],'','','','',150);"onkeyup="return showCodeListKeyEx('CertifyScan', [this,CertifyScanName],[0,1],'','','','',150);"><input class=codename name=CertifyScanName id=CertifyScanName readonly=true></TD>
				<TD  class= title>任务分配权限</TD>
				<TD  class= input> <input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno  readonly name=TaskAssign id=TaskAssign CodeData="0|3^0|无任务分配权限^1|有任务分配权限" ondblclick="return showCodeListEx('TaskAssign',[this,TaskAssignName],[0,1],'','','','',150);" onclick="return showCodeListEx('TaskAssign',[this,TaskAssignName],[0,1],'','','','',150);" onkeyup="return showCodeListKeyEx('TaskAssign',[this,TaskAssignName],[0,1],'','','','',150);"><input class=codename name=TaskAssignName id=TaskAssignName readonly=true></TD>
                <TD  class= title>审核权限</TD>
				<TD  class= input> <input  style="background:url(../common/images/select--bg_03.png) no-repeat right center"class=codeno readonly name=CheckFlag id=CheckFlag CodeData="0|3^0|无审核权限^1|有审核权限" ondblclick="return showCodeListEx('CheckFlag', [this,CheckFlagName],[0,1],'','','','',150);" onclick="return showCodeListEx('CheckFlag', [this,CheckFlagName],[0,1],'','','','',150);"onkeyup="return showCodeListKeyEx('CheckFlag', [this,CheckFlagName],[0,1],'','','','',150);"><input class=codename name=CheckFlagName id=CheckFlagName readonly=true></TD>
                  </TR>
                 <TR class= common>
				
                <TD  class= title>审批权限</TD>
                <TD  class= input> <input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly name=UWFlag id=UWFlag CodeData="0|3^0|无审批权限^1|有审批权限" ondblclick="return showCodeListEx('UWFlag', [this,UWFlagName],[0,1],'','','','',150);"  onclick="return showCodeListEx('UWFlag', [this,UWFlagName],[0,1],'','','','',150);"onkeyup="return showCodeListKeyEx('UWFlag', [this,UWFlagName],[0,1],'','','','',150);"><input class=codename name=UWFlagName id=UWFlagName readonly=true></TD>  
                <TD  class= title>豁免权限</TD>
				<TD  class= input> <input  style="background:url(../common/images/select--bg_03.png) no-repeat right center"class=codeno readonly name=ExemptFlag id=ExemptFlag CodeData="0|3^0|无豁免权限^1|有豁免权限" ondblclick="return showCodeListEx('ExemptFlag', [this,ExemptFlagName],[0,1],'','','','',150);" onclick="return showCodeListEx('ExemptFlag', [this,ExemptFlagName],[0,1],'','','','',150);"onkeyup="return showCodeListKeyEx('ExemptFlag', [this,ExemptFlagName],[0,1],'','','','',150);"><input class=codename name=ExemptFlagName id=ExemptFlagName readonly=true></TD>
				<TD  class= title>在岗状态</TD>
				<TD  class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly name=RgtFlag id=RgtFlag ondblclick="return showCodeList('llclaimuserflag',[this,RgtFlagName],[0,1]);" onclick="return showCodeList('llclaimuserflag',[this,RgtFlagName],[0,1]);" onkeyup="return showCodeListKey('llclaimuserflag',[this,RgtFlagName],[0,1]);"><input class=codename name=RgtFlagName id=RgtFlagName readonly=true></TD>
			</TR>            
            <TR class= common>  
				
				   		  
				<!-- <TD  class= title>审核级别</TD>
				<TD  class= input><Input class=codeno readonly name=CheckLevel ondblclick="return showCodeList('popedomlevel',[this,CheckLevelName],[0,1],null,fm.all('tCheckLevel').value,'popedomlevel','1');" onkeyup="return showCodeListKey('popedomlevel',[this,CheckLevelName],[0,1],null,fm.all('tCheckLevel').value,'popedomlevel','1');"><input class=codename name=CheckLevelName readonly=true></TD>				
				<TD  class= title>审批级别</TD>  
				<TD  class= input><Input class=codeno readonly name=UWLevel ondblclick="return showCodeList('popedomlevel',[this,UWLevelName],[0,1],null,fm.all('tUWLevel').value,'popedomlevel','1');" onkeyup="return showCodeListKey('popedomlevel',[this,UWLevelName],[0,1],null,fm.all('tUWLevel').value,'popedomlevel','1');"><input class=codename name=UWLevelName readonly=true></TD> -->		 
            </TR>
            <TR class= common>   
 				<TD  class= title>工作电话</TD>
                <TD  class= input> <input class="wid" class=common name=RelPhone id=RelPhone ></TD> 	
				<TD  class= title></TD>
                <TD  class= input></TD> <TD  class= title></TD>
                <TD  class= input></TD> 	 	
			</TR>

		</Table>                             
    </Div>   
<input class=cssButton name="saveUserButton"   value="增  加"  type=button onclick="UserAddClick();">
				<input class=cssButton name="editUserButton"   value="修  改" disabled type=button onclick="UserEditClick();">
				<input class=cssButton name="deleteUserButton" value="删  除" disabled type=button onclick="UserDeleteClick();">                     
               <input class=cssButton name="resetUserButton"  value="重  置" type=button onclick="UserResetClick();">               
                <input class=cssButton name="queryUserButton"  value="用户查询" type=button onclick="userQueryClick();">
                
                <!--<a href="javascript:void(0);" name="saveUserButton" class="button" disabled onClick="UserAddClick();">增    加</a>
                <a href="javascript:void(0);" name="editUserButton" class="button" disabled onClick="UserEditClick();">修    改</a>
                <a href="javascript:void(0);" name="deleteUserButton" class="button" disabled onClick="UserDeleteClick();">删    除</a>
                <a href="javascript:void(0);" name="resetUserButton" class="button" disabled onClick="UserResetClick();">重    置</a>
                <a href="javascript:void(0);" name="queryUserButton" class="button" disabled onClick="userQueryClick();">用户查询</a>-->
    <!--隐藏表单区域-->	
    <input type=hidden id="tCheckLevel" name=tCheckLevel value="A%" > <!-- 审核级别,用于查询-->
	<input type=hidden id="tUWLevel" name=tUWLevel value="B%" >  <!-- 审批级别,用于查询-->

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
