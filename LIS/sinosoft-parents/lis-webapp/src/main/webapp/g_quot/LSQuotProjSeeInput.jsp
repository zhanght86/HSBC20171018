<%
/***************************************************************
 * <p>ProName��LSQuotETSeeInput.jsp</p>
 * <p>Title���鿴���۵�(��Ŀѯ��)</p>
 * <p>Description���鿴���۵�(��Ŀѯ��)</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-05-19
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
	String tManageCom = tGI.ManageCom;
	String tOfferListNo = request.getParameter("OfferListNo");
	String tQuotNo = request.getParameter("QuotNo");
	String tQuotBatNo = request.getParameter("QuotBatNo");
	String tQuotType = request.getParameter("QuotType");
	String tReturnFlag = request.getParameter("ReturnFlag");
	String tQuotQuery = request.getParameter("QuotQuery");
%>
<script>
	var tOfferListNo = "<%=tOfferListNo%>";
	var tQuotNo = "<%=tQuotNo%>";
	var tQuotBatNo = "<%=tQuotBatNo%>";
	var tQuotType = "<%=tQuotType%>";
	var tOperator = "<%=tOperator%>";
	var tManageCom = "<%=tManageCom%>";
	var tCurrentDate = "<%=tCurrentDate%>";
	var tReturnFlag = "<%=tReturnFlag%>";
	var tTranProdType = "";//��Ʒ����
	var tMissionID = "";
	var tSubMissionID = ""; 
	var tActivityID = "";//����Ϊ�գ�ֻ�����ϱ߰�ťչʾʱ��
	var tQuotQuery = "<%=tQuotQuery%>";
</script>
<html>
<head>
	<title>�鿴���۵�</title>
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
	<script src="./LSQuotPubPlan.js"></script>
	<script src="./LSQuotProjSeeInput.js"></script>
	<%@include file="./LSQuotProjSeeInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<div id="tab1">
		<ul>
			<li id=id1 name=name1></li>
			<li id=id2 name=name2 ></li>
			<li id=id3 name=name3></li>
			<li onmouseover="setTabOver(1,3)" onmouseout="setTabOut(1,3)" onclick="showAttachment();" class="libutton">��������</li>
			<li onmouseover="setTabOver(1,4)" onmouseout="setTabOut(1,4)" onclick="showQuesnaire();" class="libutton">�ʾ����</li>
			<li onmouseover="setTabOver(1,5)" onmouseout="setTabOut(1,5)" onclick="showPast();" class="libutton">������Ϣ</li>
			<li onmouseover="setTabOver(1,6)" onmouseout="setTabOut(1,6)" onclick="showFeeInfo();" class="libutton">������Ϣ</li>
			<li onmouseover="setTabOver(1,7)" onmouseout="setTabOut(1,7)" onclick="onlyShowRequest();" class="libutton">ҵ������</li>
		</ul>
	</div>
</form>
<div class="tablist block">
	<form name=fm2 id=fm2 method=post action="" target=fraSubmit>
		<!--ѯ�ۻ�����Ϣ -->
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo1);">
				</td>
				<td class=titleImg>ѯ�ۻ�����Ϣ</td>
			</tr>
		</table>
		<div id="divInfo1" class=maxbox style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>ѯ�ۺ�</td>
					<td class=input><input class="wid readonly" name=QuotNo id=QuotNo readonly></td>
					<td class=title>���κ�</td>
					<td class=input><input class="wid readonly" name=QuotBatNo id=QuotBatNo readonly></td>
					<td class=title>��Ŀ����</td>
					<td class=input><input class="wid readonly" name=ProjName id=ProjName readonly></td>
				</tr>
				<tr class=common>
					<td class=title>Ŀ��ͻ�</td>
					<td class=input><input class="wid readonly" name=TargetCust id=TargetCust readonly></td>
					<td class=title>������������</td>
					<td class=input><input class="wid readonly" name=NumPeopleA id=NumPeopleA readonly></td>
					<td class=title>ҵ���ģ(Ԫ)</td>
					<td class=input><input class="wid readonly" name=PrePrem id=PrePrem readonly></td>
				</tr>
				<tr class=common>
					<td class=title>Ԥ���⸶��(%)</td>
					<td class=input><input class="wid readonly" name=PreLossRatio id=PreLossRatio readonly></td>
					<td class=title>������</td>
					<td class=input><input class="wid readonly" name=Partner id=Partner readonly></td>
					<td class=title>��Чֹ��</td>
					<td class=input><input class="wid readonly" name=ExpiryDate id=ExpiryDate readonly></td>
				</tr>
				<tr class=common>
					<td class=title>��Ʒ����</td>
					<td class=input><input class="wid common" name=ProdType id=ProdType style="display: none"><input class="wid readonly" name=ProdTypeName id=ProdTypeName readonly></td>
					<td class=title colspan=4></td>
				</tr>
				<tr class=common>
					<td class=title>�Ƿ�Ϊͳ����</td>
					<td class=input><input class="wid common" name=BlanketFlag id=BlanketFlag style="display: none"><input class="wid readonly" name=BlanketFlagName id=BlanketFlagName readonly></td>
					<td class=title id=tdElasticFlag1 name=tdElasticFlag1 style="display: none">�Ƿ�Ϊ���Լƻ�</td>
					<td class=input id=tdElasticFlag2 name=tdElasticFlag2 style="display: none"><input class="wid readonly" name=ElasticFlag id=ElasticFlag type=hidden><input class="wid readonly" name=ElasticFlagName id=ElasticFlagName readonly></td>
					<td class=title id=tdElasticFlag3 name=tdElasticFlag3 style="display: ''"></td>
					<td class=input id=tdElasticFlag4 name=tdElasticFlag4 style="display: ''"></td>
					<td class=title id=tdElasticFlag5 name=tdElasticFlag5 style="display: ''"></td>
					<td class=input id=tdElasticFlag6 name=tdElasticFlag6 style="display: ''"></td>

				</tr>
			</table>
			<div class=common id=divPlanDiv><!-- ���ϲ㼶���ֱ�׼ -->
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
					<td class=input colspan=5><textarea cols=80 rows=2 name=ProjDesc id=ProjDesc readonly></textarea></td>
				</tr>
			</table>
			<div class=common id=divPayIntvDiv></div><!-- �ɷѷ�ʽ-->
			<table class=common>
				<tr class=common><!-- ���û�������-->
					<td class=title colspan=6>
						<div id="divAppOrgCode" style="display: ''">
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
			<div class=common id=divSaleChnlDiv><!-- �������� -->
			</div>
			<table class=common>
				<tr class=common><!-- �н��������-->
					<td class=title colspan=6>
						<div id="divAgencyInfo" style="display: none">
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
					<td class=input colspan=6><input class=checkbox type=checkbox disabled name=LinkInquiryNo id=LinkInquiryNo onclick="showRelaQuot();">����������Ŀѯ�ۺż�����Ϣ</td>
				</tr>
				<tr class=common >
					<td class=title colspan=6><!-- ����ѯ�ۺ�-->
						<div  id="divLinkInquiryNo" style="display: none">
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
		</div>
		<br/>
		
		<!-- ѯ�۷�����Ϣ -->
		<div id="divPlanInfo" style="display: ''">
			<table>
				<tr>
					<td class=common>
						<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divQuotPlan);">
					</td>
					<td class=titleImg>ѯ�۷�����Ϣ</td>
				</tr>
			</table>
			<div id="divQuotPlan" class=maxbox1 style="display: ''">
				<table class=common>
					<tr class=common>
						<td text-align: left colSpan=1>
							<span id="spanPlanInfoGrid" ></span>
						</td>
					</tr>
				</table>
				<center>		
					<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage1.firstPage();">
					<input class=cssButton91 type=button value="��һҳ" onclick="turnPage1.previousPage();">
					<input class=cssButton92 type=button value="��һҳ" onclick="turnPage1.nextPage();">
					<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage1.lastPage();">
				</center>			
			</div>
		</div>
		<!-- ������ϸ��Ϣ -->
		<div id="divInfo2" style="display: none">
			<table class=common>
				<tr class=common>
					<td class=title>��������</td>
					<td class=input><input class="wid readonly" name=PlanDesc id=PlanDesc></td>
					<td class=title>��������</td>
					<td class=input><input class="wid readonly" name=PlanCode id=PlanCode readonly></td>
					<td class=title style="display: none">ϵͳ��������</td>
					<td class=input style="display: none"><input class="wid readonly" name=SysPlanCode id=SysPlanCode readonly></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
				<tr class=common>
					<td class=title>��������</td>
					<td class=input><input class="wid readonly"  name=InsuPeriod id=InsuPeriod ><input type=hidden name=InsuPeriodFlag id=InsuPeriodFlag type><input class="wid readonly" name=InsuPeriodFlagName id=InsuPeriodFlagName></td>
					<td class=title id=tdPlan5 name=tdPlan5 style="display: none">��������</td>
					<td class=input id=tdPlan6 name=tdPlan6 style="display: none"><input class="wid readonly" name=PlanType id=PlanType style="display: none"><input class="wid readonly" name=PlanTypeName id=PlanTypeName></td>
					<td class=title id=tdPlan8 name=tdPlan8 style="display: none">������ʶ</td>
					<td class=input id=tdPlan9 name=tdPlan9 style="display: none"><input class="wid readonly" name=PlanFlag id=PlanFlag style="display: none"><input class="wid readonly" name=PlanFlagName id=PlanFlagName></td>
				</tr>
                <tr class=common>
                    <td class=title id=tdPlan10 name=tdPlan10 style="display: none">���Ѽ��㷽ʽ</td>
					<td class=input id=tdPlan11 name=tdPlan11 style="display: none"><input class="wid readonly" name=PremCalType id=PremCalType style="display: none"><input class="wid readonly" name=PremCalTypeName id=PremCalTypeName></td>
					<td class=title id=tdPlan12 name=tdPlan12 style="display: none">��������</td>
					<td class=input id=tdPlan13 name=tdPlan13 style="display: none"><input class="wid readonly" name=PlanPeople id=PlanPeople></td>
					<td class=title id=tdPlan14 name=tdPlan14 style="display: none">��͹������(Ԫ)</td>
					<td class=input id=tdPlan15 name=tdPlan15 style="display: none"><input class="wid readonly" name=EnginCost id=EnginCost></td>
				</tr>
                <tr class=common>
                    <td class=title id=tdPlan16 name=tdPlan16 style="display: none">��͹������(ƽ����)</td>
					<td class=input id=tdPlan17 name=tdPlan17 style="display: none"><input class="wid readonly" name=EnginArea id=EnginArea></td>
					<td class=title id=tdPlan1 name=tdPlan1 style="display: ''"></td>
					<td class=input id=tdPlan2 name=tdPlan2 style="display: ''"></td>
					<td class=title id=tdPlan3 name=tdPlan3 style="display: ''"></td>
					<td class=input id=tdPlan4 name=tdPlan4 style="display: ''"></td>
				<tr>
				<tr class=common id=trPlan1 name=trPlan1 style="display: none">
					<td class=title>ְҵ����</td>
					<td class=input colspan=5><input class=radio type="radio" id=OccupTypeRadio1 name=OccupTypeRadio onclick="chooseOccupFlag('1');">��һְҵ���<input class=radio type="radio" id=OccupTypeRadio2 name=OccupTypeRadio onclick="chooseOccupFlag('2');">��ְҵ���<input type=hidden name=OccupTypeFlag id=OccupTypeFlag></td>
				</tr>
				<tr class=common id=trOccupType1 name=trOccupType1>
					<td class=title>ְҵ���</td>
					<td class=input><input class="wid readonly" name=OccupType id=OccupType style="display: none"><input class="wid readonly" name=OccupTypeName id=OccupTypeName></td>
					<td class=title>ְҵ�з���</td>
					<td class=input><input class="wid readonly" name=OccupMidType id=OccupMidType style="display: none"><input class="wid readonly" name=OccupMidTypeName id=OccupMidTypeName><span id=spanOccupMid name=spanOccupMid style="display: none;color: red"></span></td>
					<td class=title>����</td>
					<td class=input><input class="wid readonly" name=OccupCode id=OccupCode style="display: none"><input class="wid readonly" name=OccupCodeName id=OccupCodeName><span id=spanOccupCode name=spanOccupCode style="display: none;color: red"></span></td>
				</tr>
				<tr class=common id=trOccupType2 name=trOccupType2>
					<td class=title>���ְҵ���</td>
					<td class=input><input class="wid readonly" name=MinOccupType id=MinOccupType style="display: none"><input class="wid readonly" name=MinOccupTypeName id=MinOccupTypeName></td>
					<td class=title>���ְҵ���</td>
					<td class=input><input class="wid readonly" name=MaxOccupType id=MaxOccupType style="display: none"><input class="wid readonly" name=MaxOccupTypeName id=MaxOccupTypeName></td>
					<td class=title>ְҵ����</td>
					<td class=input><input class="wid readonly" name=OccupRate id=OccupRate></td>
				</tr>
				<tr class=common id=trPlan2 name=trPlan2 style="display: none">
					<td class=title>�������(��)</td>
					<td class=input><input class="wid readonly" name=MinAge id=MinAge></td>
					<td class=title>�������(��)</td>
					<td class=input><input class="wid readonly" name=MaxAge id=MaxAge></td>
					<td class=title>ƽ������(��)</td>
					<td class=input><input class="wid readonly" name=AvgAge id=AvgAge></td>
				</tr>
				<tr class=common id=trPlan3 name=trPlan3 style="display: none">
					<td class=title>����</td>
					<td class=input><input class="wid readonly" name=NumPeople id=NumPeople></td>
					<td class=title>�μ��籣ռ��</td>
					<td class=input><input class="wid readonly" name=SocialInsuRate id=SocialInsuRate></td>
					<td class=title>��Ů����</td>
					<td class=input><input class="wid readonly" name=MaleRate id=MaleRate style="display: none"><input class="wid readonly" name=FemaleRate id=FemaleRate style="display: none"><input class="wid readonly" name=SexRate id=SexRate></td>
				</tr>
				<tr class=common id=trPlan4 name=trPlan4 style="display: none">
					<td class=title>����ռ��</td>
					<td class=input><input class="wid readonly" name=RetireRate id=RetireRate></td>
					<td class=title>���ѷ�̯��ʽ</td>
					<td class=input><input class="wid readonly" name=PremMode id=PremMode style="display: none"><input class="wid readonly" name=PremModeName id=PremModeName></td>
					<td class=title>��ҵ����ռ��</td>
					<td class=input><input class="wid readonly" name=EnterpriseRate id=EnterpriseRate></td>
				</tr>
				<tr class=common id=trPlan5 name=trPlan5 style="display: none">
					<td class=title>�����н(Ԫ)</td>
					<td class=input><input class="wid readonly" name=MinSalary id=MinSalary></td>
					<td class=title>�����н(Ԫ)</td>
					<td class=input><input class="wid readonly" name=MaxSalary id=MaxSalary></td>
					<td class=title>ƽ����н(Ԫ)</td>
					<td class=input><input class="wid readonly" name=AvgSalary id=AvgSalary></td>
				</tr>
				<tr class=common id=trPlan6 name=trPlan6 style="display: none">
					<td class=title id=tdEngin1 name=tdEngin1 style="display: none">��߹������(Ԫ)</td>
					<td class=input id=tdEngin2 name=tdEngin2 style="display: none"><input class="wid readonly" name=MaxEnginCost id=MaxEnginCost></td>
					<td class=title id=tdEngin3 name=tdEngin3 style="display: none">��߹������(ƽ����)</td>
					<td class=input id=tdEngin4 name=tdEngin4 style="display: none"><input class="wid readonly" name=MaxEnginArea id=MaxEnginArea></td>
					<td class=title>��������</td>
					<td class=input><input class="wid readonly" name=EnginType id=EnginType style="display: none"><input class="wid readonly" name=EnginTypeName id=EnginTypeName></td>
				</tr>
                <tr class=common>
                    <td class=title>ʩ������</td>
					<td class=input><input class="wid readonly" name=EnginDays id=EnginDays></td>
					<td class=title id=tdEngin5 name=tdEngin5 style="display: ''"></td>
					<td class=input id=tdEngin6 name=tdEngin6 style="display: ''"></td>
				</tr>
			</table>
			<div id=divEnginFactor>
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
				<tr class=common id=trPlan7 name=trPlan7 style="display: none">
					<td class=title>���̸���</td>
					<td class=input colspan=5><textarea cols=80 rows=3 name=EnginDesc id=EnginDesc readonly></textarea></td>
				</tr>
				<tr class=common id=trEnginCondition name=trEnginCondition style="display: none">
					<td class=title>����״��˵��</td>
					<td class=input colspan=5><textarea cols=80 rows=3 name=EnginCondition id=EnginCondition readonly></textarea></td>
				</tr>
				<tr class=common>
					<td class=title>����˵��</td>
					<td class=input colspan=5><textarea class=common cols=80 rows=2 name=OtherDesc id=OtherDesc readonly></textarea></td>
				</tr>
			</table>
			<input class=cssButton type=button value="�رշ�����ϸ" onclick="closePlanDetail();">
		</div>
		<input class=cssButton type=button value="ѡ�񱨼۷���" onclick="selectQuotPlan();">
		<input class=cssButton type=button value="���۷�����ϸ" onclick="offerPalnDetail();">
		<input class=cssButton type=button value="��  ��" onclick="returnPrint();">
		
		<div id="divInfo3" style="display: ''">
			<table>
				<tr>
					<td class=common>
						<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divUnderwriting);">
					</td>
					<td class=titleImg>�˱�����</td>
				</tr>
			</table>
			<div id="divUnderwriting" class=maxbox1 style="display: ''">
				<table class=common>
					<tr class=common>
						<td class=title>���պ˱����</td>
						<td class=input colspan=11><textarea class=readonly cols=70 rows=2 name=FinalOpinion id=FinalOpinion readonly></textarea></td>
					</tr>
					<tr class=common>
						<td class=title>���պ˱�����</td>
						<td class=input><input class="wid readonly" name=FinalConclu id=FinalConclu style="display: none"><input class="wid readonly" name=FinalConcluName id=FinalConcluName></td>
						<td class=title></td>
						<td class=input></td>
						<td class=title></td>
						<td class=input></td>
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
			</div>
		</div>
		<input type=hidden name=Operate>
	</form>
</div>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
