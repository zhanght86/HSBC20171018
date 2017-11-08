
<%@ page language="java" import="java.util.*"%>
<!-- %@include file="../common/jsp/UsrCheck.jsp"%-->
<%@ page import="com.sinosoft.lis.pubfun.GlobalInput"%>

<%
	GlobalInput tG1 = (GlobalInput) session.getValue("GI");
	String tComCode = tG1.ComCode;
%>
<script>
	<!--选择机构：只能查询本身和下级机构-->
	var comCode = "<%=tComCode%>";
</script>

<html>
	<head>
		<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
		<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
		 <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
		<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
		<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
		<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
		<SCRIPT src="BankPayList.js"></SCRIPT>
		<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
        <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
		<%@include file="BankPayListInit.jsp"%>

	</head>

	<body onload='initInpBox()'>
		<form method=post name=fm id=fm action="./BankPayListSave.jsp">
             <div class="maxbox1" >
			<table class=common align=center>
				<TR class="common">
					<TD  class= title5>
		            管理机构
		          </TD>
				  <TD class=input5>
					 <Input class=codeno name=ManageCom id=ManageCom verify="管理机构|notnull&code:station" 
                     style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
                     onclick="return showCodeList('Station',[this,sManageComName],[0,1]);" 

                     onDblClick="return showCodeList('Station',[this,sManageComName],[0,1]);" 
                     onKeyUp="return showCodeListKey('comcode',[this,sManageComName],[0,1]);"><input class=codename name=sManageComName readonly=true>
				  </TD>
					<TD  class= title5>
				        银行代码
				    </TD>
				    <TD  class= input5>
						 <Input class=codeno name=BankCode  id=BankCode 
                         style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="return showCodeList('bank',[this,BankName],[0,1],null,'1 and comcode like #'+comCode+'%#','1');"  

                         onDblClick="return showCodeList('bank',[this,BankName],[0,1],null,'1 and comcode like #'+comCode+'%#','1');" 
                          onkeyup="return showCodeListKey('bank',[this,BankName],[0,1],null,'1 and comcode like #'+comCode+'%#','1');" 
                          verify="银行代码|notnull&code:bank"><input class=codename name=BankName readonly=true>
				    </TD>
				</TR>
				<TR class="common">
					<TD class=title5>
						起始日期
					</TD>
					<TD class=input5>
						<input class="coolDatePicker" dateFormat="short" id="StartDate"  name="StartDate" onClick="laydate
({elem:'#StartDate'});" verify="提数日期|Date"> <span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img 
src="../common/laydate/skins/default/icon.png" /></a></span>
					</TD>
					<TD class=title5>
						结束日期
					</TD>
					<TD class=input5>
						<input class="coolDatePicker" dateFormat="short" id="EndDate"  name="EndDate" onClick="laydate({elem:'#EndDate'});" verify="提数日期|Date"> <span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
					</TD>
				</TR>
                
			
			</TABLE>
            </div>
             <a href="javascript:void(0);" class="button"onClick="submitForm()">提   数</a>
		</form>
		<span id="spanCode" style="display: none; position:absolute; slategray"></span>
	</body>
</html>
