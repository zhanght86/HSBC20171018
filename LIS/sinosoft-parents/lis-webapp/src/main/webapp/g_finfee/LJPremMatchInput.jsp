<%
/***************************************************************
 * <p>ProName��LJMatchApplyInput.jsp</p>
 * <p>Title������ƥ�����</p>
 * <p>Description������ƥ�����</p>
 * <p>Copyright��Copyright (c) 2013</p>
 * <p>Company��Sinosoft</p>
 * @author   : ������
 * @version  : 8.0
 * @date     : 2014-06-09
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput) session.getValue("GI");
	String tCurrentDate = PubFun.getCurrentDate();
	String tMatchSerialNo = request.getParameter("MatchSerialNo");
%>
<script>
	var tManageCom = "<%=tGI.ManageCom%>";//��¼��½����
	var tOperator = "<%=tGI.Operator%>";//��¼������
	var tCurrentDate = "<%=tCurrentDate%>";
	var tMatchSerialNo = "<%=tMatchSerialNo%>";
</script>
<html>
<head>
	<title>����ƥ��</title>
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
	<script src="./LJPremMatchInput.js"></script>
	<script src="./LJFinfeeCommon.js"></script>
	<%@include file="./LJPremMatchInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=commontop>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divFeePremInfo);">
			</td>
			<td class=titleImgtop>�����շ��뱣����ɽ��</td>
		</tr>
	</table>
	
	<div id="divFeePremInfo" class=maxbox style="display:''">
		<table class=common>
			<tr class=common>
				<td><b>�������ѯ����</b></td>
				<td></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td><b>�������ѯ����</b></td>
				<td></td>
			</tr>
			<tr class=common>
				<td valign="top">
					<table class=common>
						<tr class=common>
							<td class=title>�������</td>
							<td class=input><input class=codeno name=FeeManageCom id=FeeManageCom ondblclick="return showCodeList('conditioncomcode', [this,FeeManageComName], [0,1], null, null, null, 1);" onkeyup="return showCodeListKey('conditioncomcode', [this,FeeManageComName], [0,1], null, null, null, 1);" readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"><input class=codename name=FeeManageComName id=FeeManageComName  readonly></td>
						</tr>						
						<tr class=common>
							<td class=title>��������</td>
							<td class=input><input class=coolDatePicker name=FeeStartDate elementtype=nacessary dateFormat="short" onClick="laydate({elem: '#FeeStartDate'});" id="FeeStartDate"><span class="icon"><a onClick="laydate({elem: '#FeeStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
						</tr>				
					</table>
				</td>
				<td valign="top">
					<table class=common>
						<tr class=common>
							<td class=title>�˻�����</td>
							<td class=input><input class="wid common" name=FeeCustAccName></td>
						</tr>
						<tr class=common>
							<td class=title>����ֹ��</td>
							<td class=input><input class=coolDatePicker name=FeeEndDate elementtype=nacessary dateFormat="short" onClick="laydate({elem: '#FeeEndDate'});" id="FeeEndDate"><span class="icon"><a onClick="laydate({elem: '#FeeEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
						</tr>
					</table>
				</td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td valign="top">
					<table class=common>
						<tr class=common>
							<td class=title>Ͷ����λ����</td>
							<td class=input><input class="wid common" name=OutPayGrpName id=OutPayGrpName></td>
						</tr>
						<tr class=common>
							<td class=title>������</td>
							<td class=input><input class="wid common" name=OutPayGrpContNo id=OutPayGrpContNo></td>
						</tr>
						<tr class=common>
							<td class=title>ҵ������</td>
							<td class=input><input class=codeno name=OutPayBussType id=OutPayBussType ondblclick="return returnShowCodeList('finbusstype', [this,OutPayBussTypeName], [0,1]);" onkeyup="return returnShowCodeListKey('finbusstype', [this,OutPayBussTypeName], [0,1]);" value="" readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"><input class=codename name=OutPayBussTypeName id=OutPayBussTypeName value="" readonly></td>
						</tr>
					</table>
				</td>
				<td valign="top">
					<table class=common>
						<tr class=common>
							<td class=title>�������</td>
							<td class=input><input class=codeno name=OutPayManageCom id=OutPayManageCom ondblclick="return showCodeList('conditioncomcode', [this,OutPayManageComName], [0,1], null, null, null, 1);" onkeyup="return showCodeListKey('conditioncomcode', [this,OutPayManageComName], [0,1], null, null, null, 1);" readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"><input class=codename name=OutPayManageComName id=OutPayManageComName readonly></td>
						</tr>
						<tr class=common>
							<td class=title>Ͷ������</td>
							<td class=input><input class="wid common" name=OutPayPrtNo id=OutPayPrtNo></td>
						</tr>
						<tr class=common>
							<td class=title>ҵ�����</td>
							<td class=input><input class="wid common" name=OutPayBussNo id=OutPayBussNo></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr class=common>
				<td valign="top">
					<input class=cssButton type=button value="��  ѯ" onclick="queryFee();">
					<input class=cssButton type=button id=OptionFeeButton name=OptionFeeButton value="ѡ  ��" onclick="optionFee();">
				</td>
				<td></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td valign="top">
					<input class=cssButton type=button value="��  ѯ" onclick="queryOutPay();">
					<input class=cssButton type=button id=OptionOutPayButton name=OptionOutPayButton value="ѡ  ��" onclick="optionOutPay();">
				</td>
				<td></td>
			</tr>
			<tr class=common>
				<td>&nbsp;</td>
			</tr>
			<tr class=common>
				<td><b>�����շ�����</b></td>
				<td></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td><b>�����������</b></td>
				<td></td>
			</tr>
			<tr class=common>
				<td colSpan=2 valign="top"><span id="spanFinDataGrid"></span></td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td colSpan=2 valign="top"><span id="spanOverDataGrid"></span></td>
			</tr>
			<tr class=common>
				<td colSpan=2>
					<center>		
						<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage1.firstPage();">
						<input class=cssButton91 type=button value="��һҳ" onclick="turnPage1.previousPage();">
						<input class=cssButton92 type=button value="��һҳ" onclick="turnPage1.nextPage();">
						<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage1.lastPage();">
					</center>
				</td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td colSpan=2>
					<center>		
						<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage2.firstPage();">
						<input class=cssButton91 type=button value="��һҳ" onclick="turnPage2.previousPage();">
						<input class=cssButton92 type=button value="��һҳ" onclick="turnPage2.nextPage();">
						<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage2.lastPage();">
					</center>
				</td>
			</tr>
		</table>
	</div>
</form>
<form name=fm1 id=fm1 method=post action="./PremMatchingSave.jsp" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divWaitMatchingInfo);">
			</td>
			<td class=titleImg>��ѡ������</td>
		</tr>
	</table>
	<div id=divWaitMatchingInfo style="display:''">
		<table class=common >
			<tr class=common>
				<td text-align: left colSpan=1><span id="spanChoosedDataGrid"></span></td>
			</tr>
		</table>
	</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divWaitMatchingInfo1);">
			</td>
			<td class=titleImg>ҵ��Ӧ������</td>
		</tr>
	</table>
	<div id=divWaitMatchingInfo1 styel="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>�������</td>
				<td class=input><input class=codeno name=DuePayManageCom id=DuePayManageCom ondblclick="return showCodeList('conditioncomcode', [this,DuePayManageComName], [0,1], null, null, null, 1);" onkeyup="return showCodeListKey('conditioncomcode', [this,DuePayManageComName], [0,1], null, null, null, 1);" readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"><input class=codename name=DuePayManageComName id=DuePayManageComName readonly></td>
				<td class=title>Ͷ��������</td>
				<td class=input><input class="wid common" name=DuePayGrpName id=DuePayGrpName></td>
				<td class=title>������</td>
				<td class=input><input class="wid common" name=DuePayGrpContNo id=DuePayGrpContNo></td>
			</tr>
			<tr class=common>
				<td class=title>ҵ������</td>
				<td class=input><input class=codeno name=DuePayBussType id=DuePayBussType ondblclick="return returnShowCodeList('finbusstype1', [this,DuePayBussTypeName], [0,1]);" onkeyup="return returnShowCodeListKey('finbusstype1', [this,DuePayBussTypeName], [0,1]);" readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"><input class=codename name=DuePayBussTypeName id=DuePayBussTypeName readonly></td>
				<td class=title>ҵ�����</td>
				<td class=input><input class="wid common" name=DuePayBussNo id=DuePayBussNo></td>
				<td class=title>�����������</td>
				<td class=input><input class="wid common" name=DuePayAgencyName id=DuePayAgencyName></td>
			</tr>
			<tr class=common>
				<td class=title>ҵ������</td>
				<td class=input><input class=coolDatePicker dateFormat="short" name=DuePayBussStartDate verify="ҵ������|DATE" onClick="laydate({elem: '#DuePayBussStartDate'});" id="DuePayBussStartDate"><span class="icon"><a onClick="laydate({elem: '#DuePayBussStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>ҵ��ֹ��</td>
				<td class=input><input class=coolDatePicker dateFormat="short" name=DuePayBussEndDate verify="ҵ��ֹ��|DATE" onClick="laydate({elem: '#DuePayBussEndDate'});" id="DuePayBussEndDate"><span class="icon"><a onClick="laydate({elem: '#DuePayBussEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>������˾����</td>
				<td class=input><input class="wid common" name=DuePayCoinsurance id=DuePayCoinsurance></td>
			</tr>
		</table>
		<input class=cssButton type=button value="��  ѯ" onclick="queryBuss();">
	</div>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divWaitMatchingInfo2);">
			</td>
			<td class=titleImg>ҵ��Ӧ��������Ϣ�б�</td>
		</tr>
	</table>
	<div id=divWaitMatchingInfo2 style="display:''">
		<table class=common >
			<tr class=common>
				<td text-align: left colSpan=1><span id="spanBusinessDataGrid"></span></td>
			</tr>
		</table>
		<center>		
			<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage3.firstPage();">
			<input class=cssButton91 type=button value="��һҳ" onclick="turnPage3.previousPage();">
			<input class=cssButton92 type=button value="��һҳ" onclick="turnPage3.nextPage();">
			<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage3.lastPage();">
		</center>
	</div>
	<input class="cssButton" type=button id=PremMatchButton name=PremMatchButton value="����ƥ��" onclick="premMatchClick()">
</form>

<form name=fm3 id=fm3 method=post action="" target=fraSubmit>
	<table class=common>
		<tr class=common>
			<td class=title>ƥ�����</td>
			<td class=input><input class=codeno name=InputConclusion id=InputConclusion ondblclick="return showCodeList('inputconclusion', [this,InputConclusionName], [0,1], null, null, null, 1);" onkeyup="return showCodeListKey('inputconclusion', [this,InputConclusionName], [0,1], null, null, null, 1);" readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"><input class=codename name=InputConclusionName id=InputConclusionName readonly elementtype=nacessary></td>
			<td class=title></td>
			<td class=input></td>
			<td class=title></td>
			<td class=input></td>
		</tr>
		<tr class=common>
			<td class=title>��������</td>
			<td class=input colspan=5><textarea cols=80 rows=3 name=InputDesc></textarea></td>
		</tr>
	</table>
	<input class=cssButton type=button value="ƥ���ύ" onclick="matchConfirmClick();">
</form>

<form name=fm2 id=fm2 method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=commontop>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divMatchingConfirmGrid);">
			</td>
			<td class=titleImgtop>����������ȷ������</td>
		</tr>
	</table>
	<div id=divMatchingConfirmGrid style="display:''">
		<table class=common >
			<tr class=common>
				<td text-align: left colSpan=1><span id="spanMatchingConfirmGrid"></span></td>
			</tr>
		</table>
		<div id= "divMatchingConfirmGridPage" style= "display: ''">
			<table align=center>
				<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage5.firstPage();">
				<input class=cssButton91 type=button value="��һҳ" onclick="turnPage5.previousPage();">
				<input class=cssButton92 type=button value="��һҳ" onclick="turnPage5.nextPage();">
				<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage5.lastPage();">
			</table>
		</div>
		<div id="c1" style="display:none">
			<table class=common >
				<tr class=common>
					<td text-align: left colSpan=1><span id="spanChoosedData1Grid"></span></td>
				</tr>
			</table>
			<center>
				<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage6.firstPage();">
				<input class=cssButton91 type=button value="��һҳ" onclick="turnPage6.previousPage();">
				<input class=cssButton92 type=button value="��һҳ" onclick="turnPage6.nextPage();">
				<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage6.lastPage();">
			</center>
		</div>
		<br/>
		<div id="c2" style="display:none">
			<table class=common >
				<tr class=common>
					<td text-align: left colSpan=1><span id="spanBusinessData1Grid"></span></td>
				</tr>
			</table>     
		</div>
		
		<input class=cssButton type=button id=CancelButton name=CancelButton value="������������" onclick="cancelClick();">
		<input class=cssButton type=button id=ReturnMenu name=ReturnMenu value="��  ��" onclick="returnMenu();">
	</div>
</form>
<div id=divUploadInfo style="display:none">
	<form name=fm4 method=post action="" target=fraSubmit>
		<table>
			<tr>
				<td class=commontop>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divUploadGrid);">
				</td>
				<td class=titleImgtop>�����ϴ�</td>
			</tr>
		</table>
		<div id=divUploadGrid style="display:''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1><span id="spanUploadFileGrid"></span></td>
				</tr>
			</table>
			<center>
				<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage8.firstPage();">
				<input class=cssButton91 type=button value="��һҳ" onclick="turnPage8.previousPage();">
				<input class=cssButton92 type=button value="��һҳ" onclick="turnPage8.nextPage();">
				<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage8.lastPage();">
			</center>
		</div>
	</form>
</div>
<form name=fmPub id=fmPub method=post action="./PremMatchingSave.jsp" target=fraSubmit>
	<!--������-->
	<input type=hidden name=Operate>
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
