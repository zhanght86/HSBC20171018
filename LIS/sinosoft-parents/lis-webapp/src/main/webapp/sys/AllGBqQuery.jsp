<%@ include file="../common/jsp/UsrCheck.jsp" %>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
/*******************************************************************************
* <p>Title: 综合查询-集体保全查询</p>
* <p>Description: 集体保全查询-INPUT主界面</p>
* <p>Copyright: Copyright (c) 2006 Sinosoft, Co.,Ltd. All Rights Reserved</p>
* <p>Company: 中科软科技股份有限公司</p>
* <p>WebSite: http://www.sinosoft.com.cn</p>
*
* @todo     : 保全-VIP客户查询
* @author   : liuxiaosong
* @version  : 1.00
* @date     : 2006-10-16
* 更新记录：  更新人    更新日期     更新原因/内容
******************************************************************************/
%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<!-- 公共引用样式 -->
		<link href="../common/css/Project.css" type="text/css" rel="stylesheet">
        <link href="../common/css/Project3.css" type="text/css" rel="stylesheet">
		<link href="../common/css/mulLine.css" type="text/css" rel="stylesheet">
		<!-- 公共引用脚本 -->
		 <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
		<script language="JavaScript" src="../common/javascript/Common.js"></script>
		<script language="JavaScript" src="../common/cvar/CCodeOperate.js"></script>
		<script language="JavaScript" src="../common/javascript/MulLine.js"></script>
		<script language="JavaScript" src="../common/javascript/EasyQuery.js"></script>
		<script language="JavaScript" src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
		<script language="JavaScript" src="../common/easyQueryVer3/EasyQueryCache.js"></script>
		<script language="JavaScript" src="../common/javascript/VerifyInput.js"></script>
		<!--私有JS脚本-->
		<SCRIPT src="AllGBqQuery.js"></SCRIPT>
		<%@include file="../sys/AllGBqQueryInit.jsp"%>

		<title>团体保全查询</title>
	</head>

	<body  onload="initForm();">
		<form action="./AllGBqQuerySubmit.jsp" method=post name=fm id=fm target="fraSubmit" >

			<table class= common border=0 width=100%>
				<tr>
                <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage

(this,divInvAssBuildInfo);">
            </TD>
					<td class= titleImg align= center>
						请输入团体保全查询条件：
					</td>
				</tr>
			</table>
 <div id="divInvAssBuildInfo">
       <div class="maxbox1" >
			<table  class= common>
				<TR  class= common>
					<td class=title5>
						保全受理号
					</td>
					<td class= input5>
						<Input type="input" class="common wid" name="EdorAcceptNo">
					</td>
					<td class=title5 >号码类型</td>
					<td class= input5><Input class="codeno" name="OtherNoType"  id="OtherNoType" 
                    style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
                    onclick="showCodeList('gedornotype',[this,OtherNoName],[0,1])" 
                    onDblClick="showCodeList('gedornotype',[this,OtherNoName],[0,1])" 
                    onKeyUp="showCodeListKey('gedornotype',[this,OtherNoName],[0,1])"><input class=codename name=OtherNoName readonly=true></td>
					</TR>
				<TR  class= common>
                    <td class=title5>
						客户/保单号
					</td>
					<td class= input5>
						<Input class="common wid"  name="OtherNo">
					</td>
				
					<td class=title5>
						申请人姓名
					</td>
					<td class= input5>
						<Input type="input" class="common wid" name="EdorAppName">
					</td>
                    </TR>
				<TR  class= common>
					<td class=title5>
						申请方式
					</td>
					<td class= input5 ><Input class="codeno" name="AppType" id="AppType"
                    style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
                    onclick="showCodeList('edorapptype',[this,AppTypeName],[0,1])" 
                    onDblClick="showCodeList('edorapptype',[this,AppTypeName],[0,1])"
                     onKeyUp="showCodeListKey('edorapptype',[this,AppTypeName],[0,1])"><input class=codename name=AppTypeName readonly=true></td>
					<TD  class= title5>保全申请日期</TD>
					<TD  class= input5><Input class="coolDatePicker"onClick="laydate({elem: '#EdorAppDate'});"dateFormat="short" name="EdorAppDate" id="EdorAppDate"><span class="icon"><a onClick="laydate({elem: '#EdorAppDate'});"><img 
src="../common/laydate/skins/default/icon.png" /></a></span>
					</TD>
				</TR>
				<TR  class= common>
					<td class=title5>
						批单号
					</td>
					<td class= input5>
						<Input type="input" class="common wid" name="EdorNo">
					</td>
					<TD  class= title5>   管理机构  </TD>
					<TD  class= input5><Input class= codeno name="ManageCom"  id="ManageCom" 
                    style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
                     onclick="showCodeList('station',[this,ManageComName],[0,1])"
                    onDblClick="showCodeList('station',[this,ManageComName],[0,1])"
                     onKeyUp="showCodeListKey('station',[this,ManageComName],[0,1])"><input class="codename" name="ManageComName" readonly=true></TD>
					
				</TR>


			</table>
</div>
</div>
         <a href="javascript:void(0);" class="button"onClick="grpBqQuery();">查   询</a>
			<!--<INPUT VALUE=" 查 询 " class=cssButton TYPE=button onClick="grpBqQuery();">-->

			<table>
				<tr>
					<td class=common>
						<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLPEdorMain1);">
					</td>
					<td class= titleImg>
						团体保全查询结果：
					</td>
				</tr>
			</table>
			<Div  id= "divLPEdorMain1" style= "display: ''" align = center>
				<table  class= common>
					<tr  class= common>
						<td text-align: left colSpan=1>
							<span id="spanPolGrid" >
							</span>
						</td>
					</tr>
				</table>
				<INPUT VALUE="首  页" class=cssButton90 TYPE=button onClick="turnPage.firstPage()">
				<INPUT VALUE="上一页" class=cssButton91 TYPE=button onClick="turnPage.previousPage()">
				<INPUT VALUE="下一页" class=cssButton92 TYPE=button onClick="turnPage.nextPage()">
				<INPUT VALUE="尾  页" class=cssButton93 TYPE=button onClick="turnPage.lastPage()">

				<input type=hidden name=Transact >
			</div>
			<br>
            <a href="javascript:void(0);" class="button"onClick="MissionQuery()">保全操作轨迹</a>
            <a href="javascript:void(0);" class="button"onClick="showNotePad()">记事本查看 </a>
            <a href="javascript:void(0);" class="button"onClick="scanDetail()">保全影像查询</a>
            <a href="javascript:void(0);" class="button"onClick="scanDetail()">保全影像查询</a>
            <a href="javascript:void(0);" class="button" onClick="PrtEdor();">明细查询</a>
            <a href="javascript:void(0);" class="button"onClick="PrtEdorBill();">查询人名清单</a>
			<!--<INPUT class=cssButton TYPE=button VALUE="保全操作轨迹" onClick="MissionQuery()">
			<INPUT class=cssButton TYPE=button VALUE=" 记事本查看 " onClick="showNotePad()">
			<input class=cssButton type=button value="保全影像查询" onClick="scanDetail()">
			<INPUT class= cssButton VALUE=" 明细查询 " TYPE=button onClick="PrtEdor();">
			<INPUT class= cssButton VALUE=" 查询人名清单 " TYPE=button onClick="PrtEdorBill();">-->
			<!-- 获取数据的隐藏域 -->
			<input type="hidden" name="LoginManageCom">
            <br><br><br><br>
		</form>
		<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
	</body>
</html>
