<%
/***************************************************************
 * <p>ProName��LSQuotProjBasicInput.jsp</p>
 * <p>Title����Ŀѯ�ۻ�����Ϣ¼��</p>
 * <p>Description����Ŀѯ�ۻ�����Ϣ¼��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-03-26
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getAttribute("GI");
	String tCurrentDate= PubFun.getCurrentDate();
	String tOperator = tGI.Operator;
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tActivityID = request.getParameter("ActivityID");
	String tQuotNo = request.getParameter("QuotNo");
	String tQuotBatNo = request.getParameter("QuotBatNo");
	String tQuotType = request.getParameter("QuotType");
%>
<script>
	var tMissionID = "<%=tMissionID%>";
	var tSubMissionID = "<%=tSubMissionID%>";
	var tActivityID = "<%=tActivityID%>";
	var tQuotNo = "<%=tQuotNo%>";
	var tQuotBatNo = "<%=tQuotBatNo%>";
	var tQuotType = "<%=tQuotType%>";
	var tOperator = "<%=tOperator%>";
	var tCurrentDate = "<%=tCurrentDate%>";
</script>
<html>
<head>
	<title>��Ŀѯ��¼��</title>
	<script src="../common/javascript/Common.js"></script>
	<script src="../common/cvar/CCodeOperate.js"></script>
	<script src="../common/javascript/MulLine.js"></script>
	<script src="../common/javascript/EasyQuery.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryCache.js"></script>
	<script src="../common/javascript/VerifyInput.js"></script>
	<script src="../common/laydate/laydate.js"></script>
	<link href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<link href="../common/css/tab.css" rel=stylesheet type=text/css>
	<script src="./LSQuotCommonInput.js"></script>
	<script src="./LSQuotPubBasic.js"></script>
	<script src="./LSQuotProjBasicInput.js"></script>
	<%@include file="./LSQuotProjBasicInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<!-- ��¼��ѯ�۲�ѯ���� -->
<form name=fm id=fm method=post action="" target=fraSubmit>
	<div id=tab1 name=tab1>
		<ul>
		   <li id=liBasic name=liBasic onclick="goToStep(1)" class="now">1��������Ϣ</li>
		   <li id=liPlan name=liPlan onclick="goToStep(2)">2��������Ϣ</li>
		   <li id=liSubmit name=liSubmit onclick="goToStep(3)">3��ѯ���ύ</li>
		   <li onmouseover="setTabOver(1,3)" onmouseout="setTabOut(1,3)" onclick="showAttachment();" class="libutton">��������</li>
		   <li onmouseover="setTabOver(1,4)" onmouseout="setTabOut(1,4)" onclick="showQuesnaire();" class="libutton">�ʾ����</li>
		   <li onmouseover="setTabOver(1,5)" onmouseout="setTabOut(1,5)" onclick="showPast();" class="libutton">������Ϣ</li>
		   <li onmouseover="setTabOver(1,6)" onmouseout="setTabOut(1,6)" onclick="showFeeInfo();" class="libutton">������Ϣ</li>
		   <li onmouseover="setTabOver(1,7)" onmouseout="setTabOut(1,7)" onclick="showRequest();" class="libutton">ҵ������</li>
		</ul>
	</div>
</form>
<div class="tablist block">
	<form name=fm2 id=fm2 method=post action="" target=fraSubmit>
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo1);">
				</td>
				<td class=titleImg>ѯ�ۻ�����Ϣ</td>
			</tr>
		</table>
		<div id=divInfo1 name=divInfo1 class=maxbox style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>ѯ�ۺ�</td>
					<td class=input><input class="wid readonly" name=QuotNo id=QuotNo readonly></td>
					<td class=title>���κ�</td>
					<td class=input><input class="wid readonly" name=QuotBatNo id=QuotBatNo readonly></td>
					<td class=title>��Ŀ����</td>
					<td class=input><input class="wid common" name=ProjName id=ProjName verify="��Ŀ����|NOTNULL&LEN<=60" elementtype=nacessary></td>
				</tr>
				<tr class=common>
					<td class=title>Ŀ��ͻ�</td>
					<td class=input><input class="wid common" name=TargetCust id=TargetCust verify="Ŀ��ͻ�|NOTNULL&LEN<=10" elementtype=nacessary></td>
					<td class=title>������������</td>
					<td class=input><input class="wid common" name=NumPeople id=NumPeople verify="������������|NOTNULL&INT&VALUE>=0" elementtype=nacessary></td>
					<td class=title>ҵ���ģ(Ԫ)</td>
					<td class=input><input class="wid common" name=PrePrem id=PrePrem verify="ҵ���ģ(Ԫ)|NOTNULL&NUM&VALUE>=0" elementtype=nacessary></td>
				</tr>
				<tr class=common>
					<td class=title>Ԥ���⸶��(%)</td>
					<td class=input><input class="wid common" name=PreLossRatio id=PreLossRatio verify="Ԥ���⸶��(%)|NOTNULL&NUM&VALUE>=0" elementtype=nacessary></td>
					<td class=title>������</td>
					<td class=input><input class="wid common" name=Partner id=Partner verify="������|NOTNULL&LEN<=100" elementtype=nacessary></td>
					<td class=title>��Чֹ��</td>
					<td class=input><input class=coolDatePicker dateFormat=short name=ExpiryDate id=ExpiryDate verify="��Чֹ��|NOTNULL&DATE" elementtype=nacessary></td>
				</tr>
				<tr class=common>
					<td class=title>��Ʒ����</td>
					<td class=input colspan=5><input class=codeno name=ProdType id=ProdType verify="��Ʒ����|NOTNULL" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('prodtype', [this,ProdTypeName], [0,1]);" onkeyup="return returnShowCodeListKey('prodtype', [this,ProdTypeName], [0,1]);" readonly><input class=codename name=ProdTypeName id=ProdTypeName elementtype=nacessary>
					<span style="color: red">����ͨ���ְ�����ͳ�ռ������ؼ��գ����������ְ��������ռ������տɴ�������֡�¼�뷽����Ϣ�󣬲�Ʒ���Ͳ��ɸ��ġ���</span></td>
				</tr>
				<tr class=common>
					<td class=title>�Ƿ�Ϊͳ����</td>
					<td class=input><input class=codeno name=BlanketFlag id=BlanketFlag value="0" verify="�Ƿ�Ϊͳ����|NOTNULL" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('trueflag', [this, BlanketFlagName], [0, 1], null, null, null, '1', null);" onkeyup="return showCodeListKey('trueflag', [this, BlanketFlagName], [0, 1], null, null, null, '1', null);"><input class=codename name=BlanketFlagName id=BlanketFlagName value="��"  elementtype=nacessary readonly></td>
					<td class=title id=td1 name=td1 style="display: none">�Ƿ�Ϊ���Լƻ�</td>
					<td class=input id=td2 name=td2 style="display: none"><input class=codeno name=ElasticFlag id=ElasticFlag style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('trueflag', [this,ElasticFlagName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('trueflag', [this,ElasticFlagName], [0,1], null, null, null, '1', null);" readonly><input class=codename name=ElasticFlagName id=ElasticFlagName readonly elementtype=nacessary></td>
					<td class=title id=td3 name=td3 style="display: ''"></td>
					<td class=input id=td4 name=td4 style="display: ''"></td>
					<td class=title id=td5 name=td5 style="display: ''"></td>
					<td class=input id=td6 name=td6 style="display: ''"></td>
				</tr>
			</table>
			<div class=common id=divPlanDiv name=divPlanDiv><!-- ���ϲ㼶���ֱ�׼ -->
			</div>
			<table class=common>
				<tr class=common>
					<td class=title></td>
					<td class=input></td>
					<td class=title></td>
					<td class=input></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
				<tr class=common>
					<td class=title>��Ŀ˵��</td>
					<td class=input colspan=5><textarea cols=80 rows=2 name=ProjDesc id=ProjDesc verify="��Ŀ˵��|NOTNULL&LEN<=1500" elementtype=nacessary></textarea><input class=cssButton type=button value="��������" onclick="showAttachment();"></td>
				</tr>
			</table>
			<div class=common id=divPayIntvDiv name=divPayIntvDiv></div><!-- �ɷѷ�ʽ-->
			<table class=common>
				<tr class=common><!-- ���û�������-->
					<td class=title colspan=6>
						<div id=divAppOrgCode name=divAppOrgCode style="display: ''">
							<table class=common>
								<tr class=common>
									<td text-align: left colSpan=1>
										<span id="spanAppOrgCodeGrid"></span>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
				
				<tr class=common>
					<td class=title></td>
					<td class=input></td>
					<td class=title></td>
					<td class=input></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
			</table>
			<div class=common id=divSaleChnlDiv name=divSaleChnlDiv><!-- �������� -->
			</div>
			<table class=common>
				<tr class=common><!-- �н��������-->
					<td class=title colspan=6>
						<div id=divAgencyInfo name=divAgencyInfo style="display: none">
							<table class=common>
								<tr class=common>
									<td text-align: left colSpan=1>
										<span id="spanAgencyNameGrid"></span>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
				
				<tr class=common>
					<td class=input colspan=6><input class=checkbox type=checkbox name=LinkInquiryNo id=LinkInquiryNo onclick="showRelaQuot();">����������Ŀѯ�ۺż�����Ϣ</td>
				</tr>
				<tr class=common >
					<td class=title colspan=6><!-- ����ѯ�ۺ�-->
						<div  id=divLinkInquiryNo name=divLinkInquiryNo style="display: none">
							<table class=common>
								<tr class=common>
									<td text-align: left colSpan=1>
										<span id="spanLinkInquiryNoGrid" ></span>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</table>
	
			<input class=cssButton type=button value="������Ϣ����" onclick="basicSubmit();">
		</div>
	</form>

	<form name=fmOther id=fmOther method=post action="" target=fraSubmit>
		<div name=otherDiv>
			<hr class="line"/>
			<center>
				<input class=cssButton type=button value="��һ��" onclick="nextStep();">
				<input class=cssButton type=button value="��Ŀ¼" onclick="ReturnList('01');">
				<input class=cssButton type=button id=enterQuest name=enterQuest value="�������ѯ" onclick="goToQuestion('0');" style="display: none">
			</center>
		<div>
	</form>
</div>
<form name=fmPub id=fmPub method=post action="" target=fraSubmit>
	<div style="display: none">
		<input type=hidden name=Operate id=Operate>
		<input type=hidden name=HiddenCodeType id=HiddenCodeType>
	</div>
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
