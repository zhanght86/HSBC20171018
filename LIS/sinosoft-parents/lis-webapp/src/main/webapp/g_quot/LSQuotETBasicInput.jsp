<%
/***************************************************************
 * <p>ProName��LSQuotETBasicInput.jsp</p>
 * <p>Title��һ��ѯ�ۻ�����Ϣ¼��</p>
 * <p>Description��һ��ѯ�ۻ�����Ϣ¼��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ������
 * @version  : 8.0
 * @date     : 2014-03-14
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%	
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getAttribute("GI");
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tActivityID = request.getParameter("ActivityID");
	String tQuotNo = request.getParameter("QuotNo");
	String tQuotBatNo = request.getParameter("QuotBatNo");
	String tQuotType = request.getParameter("QuotType");
	String tOperator = tGI.Operator;
%>
<script>
	var tMissionID = "<%=tMissionID%>";
	var tSubMissionID = "<%=tSubMissionID%>";
	var tActivityID = "<%=tActivityID%>";
	var tQuotNo = "<%=tQuotNo%>";
	var tQuotBatNo = "<%=tQuotBatNo%>";
	var tQuotType = "<%=tQuotType%>";
	var tOperator = "<%=tOperator%>";//��ǰ��¼��
</script>
<html>
<head>
	<title>һ��ѯ��¼��</title>
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
	<script src="./LSQuotETBasicInput.js"></script>
	<%@include file="./LSQuotETBasicInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<!-- ��¼��ѯ�۲�ѯ���� -->
<form name=fm  id=fm method=post action="" target=fraSubmit>
	<div id="tab1">
		<ul>
		   <li id=liBasic name=liBasic onclick="goToStep(1);" class="now">1��������Ϣ</li>
		   <li id=liPlan name=liPlan onclick="goToStep(2);">2��������Ϣ</li>
		   <li id=liSubmit name=liSubmit onclick="goToStep(3);">3��ѯ���ύ</li>
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
		<div id="divInfo1" class=maxbox1 style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>ѯ�ۺ�</td>
					<td class=input><input class="wid readonly" name=QuotNo  id=QuotNo readonly></td>
					<td class=title>���κ�</td>
					<td class=input><input class="wid readonly" name=QuotBatNo  id=QuotBatNo readonly></td>
					<td class=title>׼�ͻ�����</td>
					<td class=input><input class=codeno name=PreCustomerNo id=PreCustomerNo verify="׼�ͻ�����|notnull" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="selectPreCustomer(PreCustomerNo,PreCustomerName)" onkeyup="return returnShowCodeListKey('precustomerno',[this,PreCustomerName],[0,1]);" readonly><input class=codename name=PreCustomerName id=PreCustomerName onkeydown="fuzzyPreCustomerName(PreCustomerNo,PreCustomerName)" elementtype=nacessary></td>
				</tr>
				<tr class=common>
					<td class=title>֤������</td>
					<td class=input><input class="wid common" name=IDType id=IDType style="display: none"><input class="wid readonly" name=IDTypeName id=IDTypeName readonly></td>
					<td class=title>֤������</td>
					<td class=input><input class="wid readonly" name=IDNo id=IDNo readonly></td>
					<td class=title>��λ����</td>
					<td class=input><input class="wid common" name=GrpNature id=GrpNature style="display: none"><input class="wid readonly" name=GrpNatureName  id=GrpNatureName readonly></td>
				</tr>
				<tr class=common>
					<td class=title>��ҵ����</td>
					<td class=input><input class="wid common" name=BusiCategory id=BusiCategory style="display: none"><input class="wid readonly" name=BusiCategoryName id=BusiCategoryName readonly></td>
					<td class=title>��ַ</td>
					<td class=input colspan=3><input class="wid readonly" name=Address id=Address readonly></td>
				</tr>
				<tr class=common>
					<td class=title>��Ʒ����</td>
					<td class=input colspan=5 nowrap><input class=codeno name=ProdType id=ProdType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('prodtype', [this,ProdTypeName], [0,1]);" onkeyup="return returnShowCodeListKey('prodtype', [this,ProdTypeName], [0,1]);" readonly><input class=codename name=ProdTypeName id=ProdTypeName readonly elementtype=nacessary>
					<span style="color: red">����ͨ���ְ�����ͳ�ռ������ؼ��գ������հ��������ռ��ɴ������֣��˻������ְ���ҽ�ƻ��������յȡ�¼�뷽����Ϣ�󣬲�Ʒ���Ͳ��ɸ��ġ���</span></td>
				</tr>
				<tr class=common>
					<td class=title>��������</td>
					<td class=input><input class=codeno name=SaleChannel id=SaleChannel style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="return showCodeList('salechannel', [this,SaleChannelName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('salechannel', [this,SaleChannelName], [0,1], null, null, null, '1', null);" readonly><input class=codename name=SaleChannelName id=SaleChannelName readonly elementtype=nacessary></td>
					<td class=title>���ѷ�̯��ʽ</td>
					<td class=input><input class=codeno name=PremMode id=PremMode style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="return showCodeList('premmode', [this,PremModeName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('premmode', [this,PremModeName], [0,1], null, null, null, '1', null);" readonly><input class=codename name=PremModeName id=PremModeName readonly elementtype=nacessary></td>
					<td class=title>Ԥ�Ʊ��ѹ�ģ(Ԫ)</td>
					<td class=input><input class="wid common" name=PrePrem id=PrePrem verify="Ԥ�Ʊ��ѹ�ģ|NUM&VALUE>=0" elementtype=nacessary></td>
				</tr>
				<tr class=common>
					<td class=title>�Ƿ�Ϊ����ҵ��</td>
					<td class=input><input class=codeno name=RenewFlag id=RenewFlag style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="return showCodeList('trueflag', [this,RenewFlagName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('trueflag', [this,RenewFlagName], [0,1], null, null, null, '1', null);" readonly><input class=codename name=RenewFlagName id=RenewFlagName readonly elementtype=nacessary></td>
					<td class=title>�Ƿ�Ϊͳ����</td>
					<td class=input><input class=codeno name=BlanketFlag id=BlanketFlag style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="return showCodeList('trueflag', [this,BlanketFlagName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('trueflag', [this,BlanketFlagName], [0,1], null, null, null, '1', null);" readonly><input class=codename name=BlanketFlagName id=BlanketFlagName readonly elementtype=nacessary></td>
					<td class=title id=td1 name=td1 style="display: none">�Ƿ�Ϊ���Լƻ�</td>
					<td class=input id=td2 name=td2 style="display: none"><input class=codeno name=ElasticFlag id=ElasticFlag style="background:url(../common/images/select--bg_03.png) no-repeat right center"ondblclick="return showCodeList('trueflag', [this,ElasticFlagName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('trueflag', [this,ElasticFlagName], [0,1], null, null, null, '1', null);" readonly><input class=codename name=ElasticFlagName  id=ElasticFlagName readonly elementtype=nacessary></td>
					<td class=title id=td3 name=td3></td>
					<td class=input id=td4 name=td4></td>
				</tr>
				<tr class=common>
					<td class=title>��������</td>
					<td class=input><input class="codename"  name=InsuPeriod id=InsuPeriod verify="��������|INT&value>0"><input type=hidden name=InsuPeriodFlag id=InsuPeriodFlag type><input class=codeno name=InsuPeriodFlagName id=InsuPeriodFlagName verify="�����ڼ䵥λ|notnull" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('insuperiodflag', [this,InsuPeriodFlag], [1,0], null, null, null, '1', null);" onkeyup="return showCodeListKey('insuperiodflag', [this,InsuPeriodFlag], [1,0], null, null, null, '1', null);" elementtype=nacessary readonly></td>
					<td class=title>��Լ��Ч������</td>
					<td class=input><input class=codeno name=ValDateType id=ValDateType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('valdatetype', [this,ValDateTypeName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('valdatetype', [this,ValDateTypeName], [0,1], null, null, null, '1', null);" readonly><input class=codename name=ValDateTypeName id=ValDateTypeName readonly elementtype=nacessary></td>
					<td class=title id=tdValDate1 name=tdValDate1 style="display: none">��Ч����</td>
					<td class=input id=tdValDate2 name=tdValDate2 style="display: none"><input class="coolDatePicker" dateFormat="short" name=AppointValDate  id=AppointValDate verify="Ԥ������|date" elementtype=nacessary></td>
					<td class=title id=tdValDate3 name=tdValDate3></td>
					<td class=input id=tdValDate4 name=tdValDate4></td>
				</tr>
				<tr class=common>
					<td class=title>�ɷѷ�ʽ</td>
					<td class=input><input class=codeno name=PayIntv id=PayIntv style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('payintv', [this,PayIntvName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('payintv', [this,PayIntvName], [0,1], null, null, null, '1', null);" readonly><input class=codename name=PayIntvName id=PayIntvName readonly elementtype=nacessary></td>
					<td class=title>�Ƿ񹲱�</td>
					<td class=input><input class=codeno name=Coinsurance id=Coinsurance style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('trueflag', [this,CoinsuranceName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('trueflag', [this,CoinsuranceName], [0,1], null, null, null, '1', null);" readonly><input class=codename name=CoinsuranceName  id=CoinsuranceName readonly elementtype=nacessary></td>
					<!--  td class=title><input class=cssButton type=button value="��������" onclick="showCoinsurance();"></td>
					<td class=input></td-->
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
					<td class=input colspan=5><textarea cols=80 rows=3 name=CustomerIntro id=CustomerIntro></textarea></td>
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
					<td class=title colspan=6><input class=checkbox type=checkbox name=RelaCustomerFlag  id=RelaCustomerFlag onclick="relaCustClick();">�Ƿ��������׼�ͻ�</td>
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
			<input class=cssButton type=button value="������Ϣ����" onclick="basicSubmit();">
		</div>
	</form>
	
	<form name=fmOther  id=fmOther method=post action="" target=fraSubmit>
		<div name=otherDiv>
			<hr class="line"/>
			<center>
				<input class=cssButton type=button value="��һ��" onclick="nextStep();">
				<input class=cssButton type=button value="��Ŀ¼" onclick="ReturnList('00');">
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
