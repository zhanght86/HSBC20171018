<%
/***************************************************************
 * <p>ProName��LSQuotETSubmitInput.jsp</p>
 * <p>Title��ѯ���ύ</p>
 * <p>Description��ѯ���ύ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-04-24
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
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
	var tTranProdType = "";//��Ʒ���͸�Ϊҳ���ʼ��
</script>
<html>
<head>
	<title>ѯ���ύ</title>
	<script src="../common/javascript/Common.js"></script>
	<script src="../common/cvar/CCodeOperate.js"></script>
	<script src="../common/javascript/MulLine.js"></script>
	<script src="../common/javascript/EasyQuery.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryCache.js"></script>
	<script src="../common/javascript/VerifyInput.js"></script>
	<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<link href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<link href="../common/css/tab.css" rel=stylesheet type=text/css>
	<script src="./LSQuotCommonInput.js"></script>
	<script src="./LSQuotPubBasic.js"></script>
	<script src="./LSQuotPubPlan.js"></script>
	<script src="./LSQuotETSubmitInput.js"></script>
	<%@include file="./LSQuotETSubmitInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<!-- ��¼��ѯ�۲�ѯ���� -->
<form name=fm id=fm method=post action="" target=fraSubmit>
	<div id="tab1">
		<ul>
		   <li id=liBasic name=liBasic onclick="goToStep(1);" >1��������Ϣ</li>
		   <li id=liPlan name=liPlan onclick="goToStep(2);">2��������Ϣ</li>
		   <li id=liSubmit name=liSubmit onclick="goToStep(3);" class="now">3��ѯ���ύ</li>
		   <!--<li onmouseover="setTabOver(1,3)" onmouseout="setTabOut(1,3)" onclick="showRateCount();" class="libutton">�����ղ���</li>-->
		   <li onmouseover="setTabOver(1,4)" onmouseout="setTabOut(1,3)" onclick="showCoinsurance();" class="libutton">��������</li>
		   <li onmouseover="setTabOver(1,5)" onmouseout="setTabOut(1,4)" onclick="showAttachment();" class="libutton">��������</li>
		   <li onmouseover="setTabOver(1,6)" onmouseout="setTabOut(1,5)" onclick="showQuesnaire();" class="libutton">�ʾ����</li>
		   <li onmouseover="setTabOver(1,7)" onmouseout="setTabOut(1,6)" onclick="showPast();" class="libutton">������Ϣ</li>
		   <li onmouseover="setTabOver(1,8)" onmouseout="setTabOut(1,7)" onclick="showFeeInfo();" class="libutton">������Ϣ</li>
		   <li onmouseover="setTabOver(1,9)" onmouseout="setTabOut(1,8)" onclick="showRequest();" class="libutton">ҵ������</li>
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
		<div id="divInfo1" class=maxbox style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>ѯ�ۺ�</td>
					<td class=input><input class="wid readonly" name=QuotNo id=QuotNo readonly></td>
					<td class=title>���κ�</td>
					<td class=input><input class="wid readonly" name=QuotBatNo id=QuotBatNo readonly></td>
					<td class=title>׼�ͻ�����</td>
					<td class=input><input class="wid common" name=PreCustomerNo id=PreCustomerNo style="display: none"><input class="wid readonly" name=PreCustomerName id=PreCustomerName readonly></td>
				</tr>
				<tr class=common>
					<td class=title>֤������</td>
					<td class=input><input class="wid common" name=IDType id=IDType style="display: none"><input class="wid readonly" name=IDTypeName id=IDTypeName readonly></td>
					<td class=title>֤������</td>
					<td class=input><input class="wid readonly" name=IDNo  id=IDNo readonly></td>
					<td class=title>��λ����</td>
					<td class=input><input class="wid common" name=GrpNature id=GrpNature style="display: none"><input class="wid readonly"name=GrpNatureName id=GrpNatureName readonly></td>
				</tr>
				<tr class=common>
					<td class=title>��ҵ����</td>
					<td class=input><input class="wid common" name=BusiCategory id=BusiCategory style="display: none"><input class="wid readonly" name=BusiCategoryName id=BusiCategoryName readonly></td>
					<td class=title>��ַ</td>
					<td class=input colspan=3><input class="wid readonly" name=Address id=Address readonly></td>
				</tr>
				<tr class=common>
					<td class=title>��Ʒ����</td>
					<td class=input><input class="wid common" name=ProdType id=ProdType style="display: none"><input class="wid readonly" name=ProdTypeName id=ProdTypeName readonly></td>
					<td class=title colspan=4></td>
				</tr>
				<tr class=common>
					<td class=title>��������</td>
					<td class=input><input class="wid common" name=SaleChannel id=SaleChannel style="display: none"><input class="wid readonly" name=SaleChannelName id=SaleChannelName readonly></td>
					<td class=title>���ѷ�̯��ʽ</td>
					<td class=input><input class="wid common" name=PremMode id=PremMode style="display: none"><input class="wid readonly" name=PremModeName id=PremModeName readonly></td>
					<td class=title>Ԥ�Ʊ��ѹ�ģ(Ԫ)</td>
					<td class=input><input class="wid readonly" name=PrePrem id=PrePrem ></td>
				</tr>
				<tr class=common>
					<td class=title>�Ƿ�Ϊ����ҵ��</td>
					<td class=input><input class="wid common" name=RenewFlag id=RenewFlag style="display: none"><input class="wid readonly" name=RenewFlagName id=RenewFlagName readonly></td>
					<td class=title>�Ƿ�Ϊͳ����</td>
					<td class=input><input class="wid common" name=BlanketFlag id=BlanketFlag style="display: none"><input class="wid readonly" name=BlanketFlagName id=BlanketFlagName readonly></td>
					<td class=title id=td1 name=td1 style="display: none">�Ƿ�Ϊ����ѯ��</td>
					<td class=input id=td2 name=td2 style="display: none"><input class="wid readonly" name=ElasticFlag id=ElasticFlag type=hidden readonly><input class="wid readonly" name=ElasticFlagName id=ElasticFlagName readonly></td>
					<td class=title id=td3 name=td3></td>
					<td class=input id=td4 name=td4></td>
				</tr>
				<tr class=common>
					<td class=title>��������</td>
					<td class=input><input class=codeno  name=InsuPeriod id=InsuPeriod><input type=hidden name=InsuPeriodFlag id=InsuPeriodFlag type><input class=codename name=InsuPeriodFlagName id=InsuPeriodFlagName readonly></td>
					<td class=title>��Լ��Ч������</td>
					<td class=input><input class="wid common" name=ValDateType id=ValDateType style="display: none"><input class="wid readonly" name=ValDateTypeName id=ValDateTypeName readonly></td>
					<td class=title id=tdValDate1 name=tdValDate1 style="display: none">��Ч����</td>
					<td class=input id=tdValDate2 name=tdValDate2 style="display: none"><input class="wid readonly" name=AppointValDate id=AppointValDate></td>
					<td class=title id=tdValDate3 name=tdValDate3></td>
					<td class=input id=tdValDate4 name=tdValDate4></td>
				</tr>
				<tr class=common>
					<td class=title>�ɷѷ�ʽ</td>
					<td class=input><input class="wid common" name=PayIntv id=PayIntv style="display: none"><input class="wid readonly" name=PayIntvName readonly></td>
					<td class=title>�Ƿ񹲱�</td>
					<td class=input><input class="wid common" name=Coinsurance style="display: none"><input class="wid readonly" name=CoinsuranceName readonly></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
			</table>
			<div class=common id=divPlanDiv>
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
					<td class=title>��˾���</td>
					<td class=input colspan=5><textarea cols=80 rows=3 name=CustomerIntro readonly></textarea></td>
				</tr>
				<tr class=common>
					<td class=title colspan=6>
						<div id="divAgencyInfo" name="divAgencyInfo" style="display: none">
							<table class=common>
								<tr class=common>
									<td text-align: left colSpan=1>
										<span id="spanAgencyListGrid" ></span>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
				<tr class=common>
					<td class=title colspan=6><input class=checkbox type=checkbox disabled name=RelaCustomerFlag>�Ƿ��������׼�ͻ�</td>
				</tr>
				<tr class=common>
					<td class=title colspan=6>
						<div id="divRelaCustInfo" style="display: none">
							<table class=common>
								<tr class=common>
									<td text-align: left colSpan=1>
										<span id="spanRelaCustListGrid"></span>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<br/>
		<div id="divEngin" name=divEngin style="display: none">
			<table>
				<tr>
					<td class=common>
						<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divEnginInfo);">
					</td>
					<td class=titleImg>������Ϣ</td>
				</tr>
			</table>
			<div id="divEnginInfo" class=maxbox>
				<table class=common>
					<tr class=common>
						<td class=title>��������</td>
						<td class=input colspan=5><input class="wid readonly" name=EnginName id=EnginName style="width: 500px"></td>
					</tr>
					<tr class=common>
						<td class=title>��������</td>
						<td class=input><input class="wid readonly" name=EnginType id=EnginType style="display: none"><input class="wid readonly" name=EnginTypeName id=EnginTypeName readonly></td>
						<td class=title>�������(ƽ����)</td>
						<td class=input><input class="wid readonly" name=EnginArea id=EnginArea readonly></td>
						<td class=title>�������(Ԫ)</td>
						<td class=input><input class="wid readonly" name=EnginCost id=EnginCost readonly></td>
					</tr>
				</table>
				<div id=divEnginFactor name=divEnginFactor>
				</div>
				<table class=common>
					<tr class=common>
						<td class=title>���̵ص�</td>
						<td class=input><input class="wid readonly" name=EnginPlace id=EnginPlace readonly></td>
						<td class=title>��������</td>
						<td class=input><input class="wid readonly" name=EnginStartDate id=EnginStartDate readonly></td>
						<td class=title>����ֹ��</td>
						<td class=input><input class="wid readonly" name=EnginEndDate id=EnginEndDate readonly></td>
					</tr>
					<tr class=common>
						<td class=title>���̸���</td>
						<td class=input colspan=5><textarea cols=80 rows=3 name=EnginDesc readonly></textarea></td>
					</tr>
					<tr class=common id=trEnginCondition name=trEnginCondition style="display: none">
						<td class=title>����״��˵��</td>
						<td class=input colspan=5><textarea cols=80 rows=3 name=EnginCondition readonly></textarea></td>
					</tr>
					<tr class=common>
						<td class=title>��ע</td>
						<td class=input colspan=5><textarea cols=80 rows=3 name=Remark readonly></textarea></td>
					</tr>
					<tr class=common>
						<td class=title>�а�������</td>
						<td class=input><input class="wid readonly" name=InsurerName id=InsurerName></td>
						<td class=title>�а�������</td>
						<td class=input><input class="wid readonly" name=InsurerType id=InsurerType style="display: none"><input class="wid readonly" name=InsurerTypeName id=InsurerTypeName readonly></td>
						<td class=title></td>
						<td class=input></td>
					</tr>
					<tr class=common>
						<td class=title>ʩ��������</td>
						<td class=input><input class="wid readonly" name=ContractorName id=ContractorName readonly></td>
						<td class=title>ʩ��������</td>
						<td class=input><input class="wid readonly" name=ContractorType id=ContractorType style="display: none"><input class="wid readonly" name=ContractorTypeName id=ContractorTypeName readonly></td>
						<td class=title></td>
						<td class=input></td>
					</tr>
				</table>
				<br/>
			</div> 
		</div>
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
		<div id="divShowAllPlan">
		</div>
		<div id="divTurnPage" style="display: '';text-align:center;">
			<table >
				<input class=cssButton90 type=button value="��  ҳ" onclick="firstPage();">
				<input class=cssButton91 type=button value="��һҳ" onclick="previousPage();">
				<input class=cssButton92 type=button value="��һҳ" onclick="nextPage();">
				<input class=cssButton93 type=button value="β  ҳ" onclick="lastPage();">
				<input class=readonly name=PageInfo readonly value="" style="width:80px">
			</table>
		</div>
	</form>
	
	<form name=fmOther method=post action="" target=fraSubmit>
		<div name=otherDiv>
			<hr class="line" />
			<center>
				<input class=cssButton type=button value="��һ��" onclick="previousStep();">
				<input class=cssButton type=button value="ѯ����Ϣ�ύ" onclick="quotInfoSubmit();">
				<input class=cssButton type=button value="��Ŀ¼" onclick="ReturnList('00');">
				<input class=cssButton type=button id=enterQuest name=enterQuest value="�������ѯ" onclick="goToQuestion('0');" style="display: none">
			</center>
		<div>
	</form>
</div>
<form name=fmPub method=post action="" target=fraSubmit>
	<div style="display: none">
		<input type=hidden name=Operate>
	</div>
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br/>
<br/>
<br/>
<br/>
</body>
</html>
