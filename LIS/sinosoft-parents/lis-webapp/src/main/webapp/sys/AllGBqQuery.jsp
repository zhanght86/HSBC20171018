<%@ include file="../common/jsp/UsrCheck.jsp" %>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
/*******************************************************************************
* <p>Title: �ۺϲ�ѯ-���屣ȫ��ѯ</p>
* <p>Description: ���屣ȫ��ѯ-INPUT������</p>
* <p>Copyright: Copyright (c) 2006 Sinosoft, Co.,Ltd. All Rights Reserved</p>
* <p>Company: �п���Ƽ��ɷ����޹�˾</p>
* <p>WebSite: http://www.sinosoft.com.cn</p>
*
* @todo     : ��ȫ-VIP�ͻ���ѯ
* @author   : liuxiaosong
* @version  : 1.00
* @date     : 2006-10-16
* ���¼�¼��  ������    ��������     ����ԭ��/����
******************************************************************************/
%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<!-- ����������ʽ -->
		<link href="../common/css/Project.css" type="text/css" rel="stylesheet">
        <link href="../common/css/Project3.css" type="text/css" rel="stylesheet">
		<link href="../common/css/mulLine.css" type="text/css" rel="stylesheet">
		<!-- �������ýű� -->
		 <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
		<script language="JavaScript" src="../common/javascript/Common.js"></script>
		<script language="JavaScript" src="../common/cvar/CCodeOperate.js"></script>
		<script language="JavaScript" src="../common/javascript/MulLine.js"></script>
		<script language="JavaScript" src="../common/javascript/EasyQuery.js"></script>
		<script language="JavaScript" src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
		<script language="JavaScript" src="../common/easyQueryVer3/EasyQueryCache.js"></script>
		<script language="JavaScript" src="../common/javascript/VerifyInput.js"></script>
		<!--˽��JS�ű�-->
		<SCRIPT src="AllGBqQuery.js"></SCRIPT>
		<%@include file="../sys/AllGBqQueryInit.jsp"%>

		<title>���屣ȫ��ѯ</title>
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
						���������屣ȫ��ѯ������
					</td>
				</tr>
			</table>
 <div id="divInvAssBuildInfo">
       <div class="maxbox1" >
			<table  class= common>
				<TR  class= common>
					<td class=title5>
						��ȫ�����
					</td>
					<td class= input5>
						<Input type="input" class="common wid" name="EdorAcceptNo">
					</td>
					<td class=title5 >��������</td>
					<td class= input5><Input class="codeno" name="OtherNoType"  id="OtherNoType" 
                    style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
                    onclick="showCodeList('gedornotype',[this,OtherNoName],[0,1])" 
                    onDblClick="showCodeList('gedornotype',[this,OtherNoName],[0,1])" 
                    onKeyUp="showCodeListKey('gedornotype',[this,OtherNoName],[0,1])"><input class=codename name=OtherNoName readonly=true></td>
					</TR>
				<TR  class= common>
                    <td class=title5>
						�ͻ�/������
					</td>
					<td class= input5>
						<Input class="common wid"  name="OtherNo">
					</td>
				
					<td class=title5>
						����������
					</td>
					<td class= input5>
						<Input type="input" class="common wid" name="EdorAppName">
					</td>
                    </TR>
				<TR  class= common>
					<td class=title5>
						���뷽ʽ
					</td>
					<td class= input5 ><Input class="codeno" name="AppType" id="AppType"
                    style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
                    onclick="showCodeList('edorapptype',[this,AppTypeName],[0,1])" 
                    onDblClick="showCodeList('edorapptype',[this,AppTypeName],[0,1])"
                     onKeyUp="showCodeListKey('edorapptype',[this,AppTypeName],[0,1])"><input class=codename name=AppTypeName readonly=true></td>
					<TD  class= title5>��ȫ��������</TD>
					<TD  class= input5><Input class="coolDatePicker"onClick="laydate({elem: '#EdorAppDate'});"dateFormat="short" name="EdorAppDate" id="EdorAppDate"><span class="icon"><a onClick="laydate({elem: '#EdorAppDate'});"><img 
src="../common/laydate/skins/default/icon.png" /></a></span>
					</TD>
				</TR>
				<TR  class= common>
					<td class=title5>
						������
					</td>
					<td class= input5>
						<Input type="input" class="common wid" name="EdorNo">
					</td>
					<TD  class= title5>   �������  </TD>
					<TD  class= input5><Input class= codeno name="ManageCom"  id="ManageCom" 
                    style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
                     onclick="showCodeList('station',[this,ManageComName],[0,1])"
                    onDblClick="showCodeList('station',[this,ManageComName],[0,1])"
                     onKeyUp="showCodeListKey('station',[this,ManageComName],[0,1])"><input class="codename" name="ManageComName" readonly=true></TD>
					
				</TR>


			</table>
</div>
</div>
         <a href="javascript:void(0);" class="button"onClick="grpBqQuery();">��   ѯ</a>
			<!--<INPUT VALUE=" �� ѯ " class=cssButton TYPE=button onClick="grpBqQuery();">-->

			<table>
				<tr>
					<td class=common>
						<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLPEdorMain1);">
					</td>
					<td class= titleImg>
						���屣ȫ��ѯ�����
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
				<INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onClick="turnPage.firstPage()">
				<INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onClick="turnPage.previousPage()">
				<INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onClick="turnPage.nextPage()">
				<INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onClick="turnPage.lastPage()">

				<input type=hidden name=Transact >
			</div>
			<br>
            <a href="javascript:void(0);" class="button"onClick="MissionQuery()">��ȫ�����켣</a>
            <a href="javascript:void(0);" class="button"onClick="showNotePad()">���±��鿴 </a>
            <a href="javascript:void(0);" class="button"onClick="scanDetail()">��ȫӰ���ѯ</a>
            <a href="javascript:void(0);" class="button"onClick="scanDetail()">��ȫӰ���ѯ</a>
            <a href="javascript:void(0);" class="button" onClick="PrtEdor();">��ϸ��ѯ</a>
            <a href="javascript:void(0);" class="button"onClick="PrtEdorBill();">��ѯ�����嵥</a>
			<!--<INPUT class=cssButton TYPE=button VALUE="��ȫ�����켣" onClick="MissionQuery()">
			<INPUT class=cssButton TYPE=button VALUE=" ���±��鿴 " onClick="showNotePad()">
			<input class=cssButton type=button value="��ȫӰ���ѯ" onClick="scanDetail()">
			<INPUT class= cssButton VALUE=" ��ϸ��ѯ " TYPE=button onClick="PrtEdor();">
			<INPUT class= cssButton VALUE=" ��ѯ�����嵥 " TYPE=button onClick="PrtEdorBill();">-->
			<!-- ��ȡ���ݵ������� -->
			<input type="hidden" name="LoginManageCom">
            <br><br><br><br>
		</form>
		<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
	</body>
</html>
