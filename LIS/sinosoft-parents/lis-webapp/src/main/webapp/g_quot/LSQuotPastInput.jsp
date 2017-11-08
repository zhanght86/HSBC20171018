<%
/***************************************************************
 * <p>ProName：LSQuotPastInput.jsp</p>
 * <p>Title：既往信息</p>
 * <p>Description：既往信息</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-20
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	String tQuotType = request.getParameter("QuotType");
	String tQuotNo = request.getParameter("QuotNo");
	String tQuotBatNo = request.getParameter("QuotBatNo");
	String tActivityID = request.getParameter("ActivityID");
	String tFlag = request.getParameter("Flag");
%>
<script>
	var tQuotType = "<%=tQuotType%>";
	var tQuotNo = "<%=tQuotNo%>";
	var tQuotBatNo = "<%=tQuotBatNo%>";
	var tActivityID = "<%=tActivityID%>";
	var tFlag = "<%=tFlag%>";
</script>
<html>
<head >
	<title>既往信息</title>
	<script src="../common/javascript/Common.js"></script>
	<script src="../common/cvar/CCodeOperate.js"></script>
	<script src="../common/javascript/MulLine.js"></script>
	<script src="../common/javascript/EasyQuery.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryCache.js"></script>
	<script src="../common/javascript/VerifyInput.js"></script>
	<script src="../common/laydate/laydate.js"></SCRIPT>
	<link href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<link href="../common/css/tab.css" rel=stylesheet type=text/css>
	<script src="./LSQuotPastInput.js"></script>
	<script src="./LSQuotCommonInput.js"></script>
	<%@include file="./LSQuotPastInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<div id="tab1">
	<ul>
		<li onclick="setTab(1,0)" class="now">既往信息</li>
		<li onclick="setTab(1,1)">个人核保</li>
	</ul>
</div>
<div id="tablist1">
	<div id="tablistdiv0" class="tablist block" >
		<form name=fm id=fm method=post action="./LSQuotPastSave.jsp" target=fraSubmit>
			<input class=cssButton type=button value="汇总计算" name="QueryAllCal" id=QueryAllCal style="display: none" onclick="queryAllCal();">
			<table>
				<tr>
					<td class=common>
						<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divPastTotal);">
					</td>
					<td class=titleImg>既往信息汇总（前三年）</td>
				</tr>
			</table>
			<div id="divPastTotal" style="display: '';text-align:center;">
				<table class=common>
					<tr class=common>
						<td text-align: left colSpan=1>
							<span id="spanPastTotalGrid"></span>
						</td>
					</tr>
				</table>
				<center>		
					<input class=cssButton90 type=button value="首  页" onclick="turnPage1.firstPage();">
					<input class=cssButton91 type=button value="上一页" onclick="turnPage1.previousPage();">
					<input class=cssButton92 type=button value="下一页" onclick="turnPage1.nextPage();">
					<input class=cssButton93 type=button value="尾  页" onclick="turnPage1.lastPage();">
				</center>
			</div>
			<br>
			<div>
				<table class=common>
					<tr class=common>
						<td class=title>保单年度</td>
						<td class=input><input class="wid common" name=InsuYear id=InsuYear verify="保单年度|INT&LEN=4" maxlength=4 elementtype=nacessary></td>
						<td class=title>保险公司名称</td>
						<td class=input><input class=codeno name=InsuranceCom id=InsuranceCom readonly 
							ondblclick="return showCodeList('insurancecom',[this, InsuranceComName],[0, 1],null,null,null,'1',180);" 
							onkeyup="return showCodeListKey('insurancecom',[this, InsuranceComName],[0, 1],null,null,null,'1',180);"><input class=codename name=InsuranceComName id=InsuranceComName readonly></td>
						<td class=title>总保费</td>
						<td class=input><input class="wid common" name=SumPrem id=SumPrem verify="总保费|NUM" maxlength=15></td>
					</tr>
					<tr class=common>
						<td class=title>总赔付</td>
						<td class=input><input class="wid common" name=SumLoss id=SumLoss verify="总赔付|NUM" maxlength=15></td>
						<td class=title>总出险人数</td>
						<td class=input><input class="wid common" name=SumClaimPeople id=SumClaimPeople verify="总出险人数|INT" maxlength=15></td>
						<td class=title>男女比例</td>
						<td class=input><input class="wid common" style="width:50px" name=MaleRate id=MaleRate verify="男性比例|INT" maxlength=6><b>：</b><input class="wid common" style="width:50px" name=FemaleRate id=FemaleRate verify="女性比例|INT" maxlength=6><font color=red> (如 2:3)</font></td>
					</tr>
				</table>
			</div>
			
			<div>
				<input class=cssButton type=button value="汇总信息增加" name="PastTotalAddButton" id=PastTotalAddButton onclick="addPastTotal();">
				<input class=cssButton type=button value="汇总信息修改" name="PastTotalModifyButton" id=PastTotalModifyButton onclick="modifyPastTotal();">
				<input class=cssButton type=button value="汇总信息删除" name="PastTotalDeleteButton" id=PastTotalDeleteButton onclick="deletePastTotal();">
			</div>
			
			<div id="divPastDetailTitle" style="display:'none'">
				<table>
					<tr>
						<td class=common>
							<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divPastDetail);">
						</td>
						<td class=titleImg>既往信息明细（前三年）</td>
					</tr>
				</table>
				<div id="divPastDetail" style="display: '';text-align:center;">
					<table class=common>
						<tr class=common>
							<td text-align: left colSpan=1>
								<span id="spanPastDetailGrid"></span>
							</td>
						</tr>
					</table>
					<center>		
						<input class=cssButton90 type=button value="首  页" onclick="turnPage2.firstPage();">
						<input class=cssButton91 type=button value="上一页" onclick="turnPage2.previousPage();">
						<input class=cssButton92 type=button value="下一页" onclick="turnPage2.nextPage();">
						<input class=cssButton93 type=button value="尾  页" onclick="turnPage2.lastPage();">
					</center>
				</div>
				<br>
				<div>
					<table class=common>
						<tr class=common>
							<td class=title>保单年度</td>
							<td class=input><input class="wid readonly" name=DetailInsuYear id=DetailInsuYear readonly></td>
							<td class=title>保险公司名称</td>
							<td class=input><input class="wid readonly" name=DetailInsuranceComName id=DetailInsuranceComName readonly></td>
							<td class=title>保单号码</td>
							<td class=input><input class="wid common" name=GrpContNo id=GrpContNo maxlength=18></td>
						</tr>
						<tr class=common>
							<td class=title>保单生效日期</td>
							<td class=input><input class="coolDatePicker" dateFormat="short" verify="保单生效日期|DATE" name=ValDate id=ValDate></td>
							<td class=title>保单终止日期</td>
							<td class=input><input class="coolDatePicker" dateFormat="short" verify="保单终止日期|DATE" name=EndDate id=EndDate></td>
							<td class=title></td>
							<td class=input></td>
						</tr>
						<tr class=common>
							<td class=title>非医疗险总保费(元)</td>
							<td class=input><input class="wid common" name=NonMedSumPrem id=NonMedSumPrem verify="非医疗险总保费|NUM&VALUE>=0" maxlength=15></td>
							<td class=title>非医疗险总赔付(元)</td>
							<td class=input><input class="wid common" name=NonMedSumLoss id=NonMedSumLoss verify="非医疗险总赔付|NUM&VALUE>=0" maxlength=15></td>
							<td class=title>非医疗险被保险人数</td>
							<td class=input><input class="wid common" name=NonMedPeople id=NonMedPeople verify="非医疗险被保险人数|INT&VALUE>=0" maxlength=15></td>
						</tr>
						<tr class=common>
							<td class=title>非医疗险出险人数</td>
							<td class=input><input class="wid common" name=NonMedClmPeople id=NonMedClmPeople verify="非医疗险出险人数|INT&VALUE>=0" maxlength=15></td>
							<td class=title>医疗险总保费(元)</td>
							<td class=input><input class="wid common" name=MedSumPrem id=MedSumPrem verify="医疗险总保费|NUM&VALUE>=0" maxlength=15></td>
							<td class=title>医疗险总赔付(元)</td>
							<td class=input><input class="wid common" name=MedSumLoss id=MedSumLoss verify="医疗险总赔付|NUM&VALUE>=0" maxlength=15></td>
						</tr>
						<tr class=common>
							<td class=title>医疗险被保险人人数</td>
							<td class=input><input class="wid common" name=MedPeople id=MedPeople verify="医疗险被保险人人数|INT&VALUE>=0" maxlength=15></td>
							<td class=title>医疗险出险人数</td>
							<td class=input><input class="wid common" name=MedClmPeople id=MedClmPeople verify="医疗险出险人数|INT&VALUE>=0" maxlength=15></td>
							<td class=title></td>
							<td class=input></td>
						</tr>
					</table>
					
					<input class=cssButton type=button value="明细信息增加" name="PastDetailAddButton" id=PastDetailAddButton onclick="addPastDetail();">
					<input class=cssButton type=button value="明细信息修改" name="PastDetailModifyButton" id=PastDetailModifyButton onclick="modifyPastDetail();">
					<input class=cssButton type=button value="明细信息删除" name="PastDetailDeleteButton" id=PastDetailDeleteButton onclick="deletePastDetail();">
					<input class=cssButton type=button value="关闭明细信息" name="PastDetailCloseButton"  id=PastDetailCloseButton onclick="closePastDetail();">
				</div>
			</div>
			<div>
				<input class=cssButton type=button value="关  闭" onclick="top.close();">
			</div>
			
			<input type=hidden name=Operate id=Operate>
			<input type=hidden name=TotalSerialNo id=TotalSerialNo>
			<input type=hidden name=DetailSerialNo id=DetailSerialNo>
		</form>
	</div>
	
	<div id="tablistdiv1" class="tablist" style="display: none">
		<form name=fm2 id=fm2 method=post action="./LSQuotPastSave.jsp" target=fraSubmit>
			<table>
				<tr>
					<td class=common>
						<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divPersonUW);">
					</td>
					<td class=titleImg>个人核保信息</td>
				</tr>
			</table>
			<div id="divPersonUW" style="display: '';text-align:center;">
				<table class=common>
					<tr class=common>
						<td text-align: left colSpan=1>
							<span id="spanPersonUWGrid"></span>
						</td>
					</tr>
				</table>
				<center>		
					<input class=cssButton90 type=button value="首  页" onclick="turnPage3.firstPage();">
					<input class=cssButton91 type=button value="上一页" onclick="turnPage3.previousPage();">
					<input class=cssButton92 type=button value="下一页" onclick="turnPage3.nextPage();">
					<input class=cssButton93 type=button value="尾  页" onclick="turnPage3.lastPage();">
				</center>
			</div>
			<br>
			<div>
				<table class=common>
					<tr class=common>
						<td class=title>保单号</td>
						<td class=input><input class="wid common" name=ContNo id=ContNo maxlength=15></td>
						<td class=title>单位名称</td>
						<td class=input><input class="wid common" name=GrpName id=GrpName maxlength=60 elementtype=nacessary></td>
						<td class=title>姓名</td>
						<td class=input><input class="wid common" name=InsuredName id=InsuredName maxlength=15 elementtype=nacessary></td>
					</tr>
					<tr class=common>
						<td class=title>证件类型</td>
						<td class=input><input class=codeno name=IDType id=IDType readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"
							ondblclick="return showCodeList('idtype',[this, IDTypeName],[0, 1],null,null,null,'1',180);" 
							onkeyup="return showCodeListKey('idtype',[this, IDTypeName],[0, 1],null,null,null,'1',180);"><input class=codename name=IDTypeName id=IDTypeName readonly></td>
						<td class=title>证件号码</td>
						<td class=input><input class="wid common" name=IDNo id=IDNo onblur="checkidtype(fm2);getBirthdaySexByIDNo(this.value,fm2);" maxlength=30></td>
						<td class=title>性别</td>
						<td class=input><input class=codeno name=Gender id=Gender readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
							ondblclick="return showCodeList('sex',[this, GenderName],[0, 1],null,null,null,'1',180);" 
							onkeyup="return showCodeListKey('sex',[this, GenderName],[0, 1],null,null,null,'1',180);"><input class=codename name=GenderName id=GenderName readonly></td>
					</tr>
					<tr class=common>
						<td class=title>出生日期</td>
						<td class=input><input class="coolDatePicker" dateFormat="short" verify="出生日期|DATE" name=Birthday id=Birthday></td>
						<td class=title>年龄</td>
						<td class=input><input class="wid common" name=Age id=Age verify="年龄|INT&VALUE>=0" maxlength=3 elementtype=nacessary></td>
						<td class=title>赔付险种</td>
						<td class=input><input class="wid common" name=RiskName id=RiskName maxlength=30 elementtype=nacessary></td>
					</tr>
					<tr class=common>
						<td class=title>赔付金额(元)</td>
						<td class=input><input class="wid common" name=LossAmount id=LossAmount verify="赔付金额|NUM&VALUE>=0" maxlength=15 elementtype=nacessary></td>
						<td class=title></td>
						<td class=input></td>
						<td class=title></td>
						<td class=input></td>
					</tr>
					<tr class=common>
						<td class=title>关注原因</td>
						<td class=input colspan=5><textarea cols=76 rows=3 name=Reason id=Reason elementtype=nacessary></textarea></td>
					</tr>
					<tr class=common id="divRemark" style="display: none">
						<td class=title>备注</td>
						<td class=input colspan=5><textarea cols=76 rows=3 name=Remark id=Remark></textarea><font color=red>[业务来源为"系统抽取"的数据，只允许修改备注信息]</font></td>
					</tr>
					<tr class=common id="divUWOpinion" style="display: none">
						<td class=title>核保意见</td>
						<td class=input><input class=codeno name=UWOpinion id=UWOpinion readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
							ondblclick="return showCodeList('peruwopinion',[this, UWOpinionName],[0, 1],null,null,null,'1',180);" 
							onkeyup="return showCodeListKey('peruwopinion',[this, UWOpinionName],[0, 1],null,null,null,'1',180);"><input class=codename name=UWOpinionName id=UWOpinionName readonly elementtype=nacessary></td>
						<td class=title></td>
						<td class=input></td>
						<td class=title></td>
						<td class=input></td>
					</tr>
					<tr class=common id="divUWDesc" style="display: none">
						<td class=title>核保描述</td>
						<td class=input colspan=5><textarea cols=76 rows=3 name=UWDesc id=UWDesc elementtype=nacessary></textarea></td>
					</tr>
				</table>
			</div>
			
			<div>
				<input class=cssButton type=button value="增  加" name="PerUWAddButton" onclick="addPerUW();">
				<input class=cssButton type=button value="修  改" name="PerUWModifyButton" onclick="modifyPerUW();">
				<input class=cssButton type=button value="删  除" name="PerUWDeleteButton" onclick="deletePerUW();">
				<input class=cssButton type=button value="核保意见保存" name="PerUWSaveButton" onclick="savePerUW();">
			</div>
			
			<input type=hidden name=PerUWOperate id=PerUWOperate>
			<input type=hidden name=PerUWSerialNo id=PerUWSerialNo>
		</form>
		<form name=fmupload id=fmupload method=post action="" ENCTYPE="multipart/form-data" target=fraSubmit>
			<div id="divAttachmentUpload" style="display: ''">
				<table class=common>
					<tr class=common>
						<td class=title>附件上载</td>
						<td class=input nowrap><input class="wid common" type=file name=UploadPath id=UploadPath style="width:300px"><input class=cssButton type=button name=UploadButton id=UploadButton value="上传附件" onclick="upLoadFile();"></td>
						<td class=title nowrap><font color="#FF0000">（仅支持jpg,gif格式的文件上载）</font></td>
						<td class=title></td>
						<td class=input></td>
					</tr>
				</table>
			</div>
		</form>
		<div>
			<input class=cssButton type=button value="关  闭" onclick="top.close();">
		</div>
	</div>
</div>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
