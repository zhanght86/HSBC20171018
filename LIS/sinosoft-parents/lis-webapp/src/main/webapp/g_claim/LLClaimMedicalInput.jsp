<%
/***************************************************************
 * <p>ProName��LLClaimMedicalInput.jsp</p>
 * <p>Title��ҽ���˵�¼��ҳ��</p>
 * <p>Description��ҽ���˵�¼��ҳ��</p>
 * <p>Copyright��Copyright (c) 2014</p>
 * <p>Company��Sinosoft</p>
 * @author   : lixf
 * @version  : 8.0
 * @date     : 2014-04-17
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");	
	String tManageCom = request.getParameter("ManageCom");
	String tOperator = tGI.Operator;
	String mCurrentDate = PubFun.getCurrentDate();
  String tCaseSource = request.getParameter("CaseSource"); 
  String tRgtNo = request.getParameter("RgtNo"); 
  String tCaseNo= request.getParameter("CaseNo"); 
  String tCustomerNo = request.getParameter("CustomerNo"); //�����˱���
  String tMode = request.getParameter("Mode"); //�����˱���
  String BussNo = request.getParameter("BussNo");
	String BussType = request.getParameter("BussType");
	String SubType = request.getParameter("SubType");
%>
<script>
	var tManageCom = "<%=tManageCom%>"; //��¼��½����
	var tOperator = "<%=tOperator%>";  //��¼������
	var tCurrentDate = "<%=mCurrentDate%>"; 	//ϵͳʱ��
	var tRgtNo ="<%=tRgtNo%>";
	var tCaseSource ="<%=tCaseSource%>";
	var tCustomerNo ="<%=tCustomerNo%>";
	var tCaseNo ="<%=tCaseNo%>";
	var tMode ="<%=tMode%>";
	var tBussNo ="<%=BussNo%>";
	var tBussType ="<%=BussType%>";
	var tSubType ="<%=SubType%>";
</script>
<html>
<head>
	<title>ҽ���˵�¼��</title>
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
	<link rel="stylesheet" type="text/css" href="../common/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../common/themes/icon.css">
	<link rel="stylesheet" href="../common/css/jquery.Jcrop.css" type="text/css" />
	<script src="../common/javascript/jquery-1.7.2.js"></script>
	<script src="../common/javascript/jquery.js"></script>
	<script src="../common/javascript/jquery.easyui.min.js"></script><!-- jQuery EasyUI��һ�����JQuery��UI���ٴ��� �����EXTJS�е�����-->
	<script src="../common/javascript/jquery.Jcrop.min.js"></script><!-- Jcropһ��Queryʵ��ͼƬ�ü��Ĳ�� -->
	<script src="../common/javascript/jquery.rotate.js"></script><!-- ʵ��ͼƬ��ת���� -->
	<script src="../common/javascript/jquery.imageView.js"></script><!-- �����϶��鿴��ͼ��jQuery���-->
	<script src="../g_easyscan/ScanServo.js"></script>
	<script>window.document.onkeydown = document_onkeydown;</script>
	<script src="./LLClaimMedicalInput.js"></script>
	<%@include file="LLClaimMedicalInit.jsp"%> 
</head>
<body onload="initForm();initElementtype();">
	<form action="" method=post name=fm id=fm target="fraSubmit">
		
	<div id="ClinicBill" style="display:''">	
		<table>
			<tr>
				<td class=common><img src= "../common/images/butExpand.gif" style="cursor:hand;" onclick= "showPage(this, divClinic);"></td>
				<td class=titleImg>�����˵�¼�롪������Ϣ </ td>
			</tr>
		</table>
				
		<div id="divClinic" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanClinicGrid"></span>
					</td>
				</tr>				
			</table>
			<center>
				<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage1.firstPage();">
				<input class=cssButton91 type=button value="��һҳ" onclick="turnPage1.previousPage();">
				<input class=cssButton92 type=button value="��һҳ" onclick="turnPage1.nextPage();">
				<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage1.lastPage();">
			</center>			
		
						
			<table class=common> 
				<tr class=common>
					<td class=title>��Ʊ��</td>
					<td class=input><input class="wid common" name=ClinicBillNo elementtype=nacessary></td>
					<td class=title>����ҽԺ</td>
					<td class=input><input class=codeno name=ClinicHosID readonly><input class=codename name=ClinicHosName  onkeydown="queryHospInfo(ClinicHosID,ClinicHosName);"  elementtype=nacessary ></td>
					<td class=title>�˵��������</td>
					<td class=input><input class="wid common" name=ClinicBillMoney onchange="checkMoney(fm.ClinicBillMoney);" elementtype=nacessary></td>
				</tr>
				<tr class=common>			  	
					<td class=title>��������</td>
					<td class=input><input class="coolDatePicker" name="ClinicStartdate" elementtype=nacessary onClick="laydate({elem: '#ClinicStartdate'});" id="ClinicStartdate"><span class="icon"><a onClick="laydate({elem: '#ClinicStartdate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
					<td class=title></td>
					<td class=input></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
				<tr class=common>
					<td class=title>��Ҫ���(ICD10)</td>
					<td class=input><input class=codeno name=ClinicICDNo readonly><input class=codename name=ClinicICDName onkeydown="queryICDInfo(ClinicICDNo,ClinicICDName,ClinicICDDetail,ClinicICDDetailName);" elementtype=nacessary ></td> 
					<td class=title>�������(ICD10)</td>
					<td class=input><input class=codeno name=ClinicICDDetail style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('diseasecode',[this, ClinicICDDetailName],[0,1],null,fm.ClinicICDNo.value,'upicdcode',1);" onkeyup="return showCodeListKey('diseasecode',[this, ClinicICDDetailName],[0,1],null,fm.ClinicICDNo.value,'upicdcode',1);"readonly><input class=codename name=ClinicICDDetailName onkeydown="queryICDDetailInfo(ClinicICDDetail,ClinicICDDetailName,ClinicICDNo);"></td> 
					<td class=title>Ӱ�������</td>
					<td class=input><input class="wid common" name=ClinicScanNum></td>
				</tr>
			</table>
	
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1><span id="spanClinicBillItemGrid"></span></td>
				</tr>
			</table>
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
				<td class=title>����</td>
				<td class=input><input class="wid common" name=ClinicArea ></td>
				<td class=title>��������</td>
				<td class=input><input class="coolDatePicker" name="Clinicdate" onClick="laydate({elem: '#Clinicdate'});" id="Clinicdate"><span class="icon"><a onClick="laydate({elem: '#Clinicdate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>��Ʒ/ҩƷ����</td>
				<td class=input><input class="wid common" name=ClinicMediName></td>
			</tr>
		
			</table>
			<input class=cssButton type=button name=ClinicDeductQuery value="��  ѯ" type=button onclick=DeductQuery();>
			<input class=cssButton type=button name=ClinicDeductQuery value="��  ��" type=button onclick=clinicDeductConfirm(1);>
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
					<span id="spanDrugsGrid"></span>
					</td>
				</tr>
			</table>
			<center>
				<input class=cssbutton90 value="��  ҳ" type=button onclick="turnPage7.firstPage();"> 
				<input class= cssbutton91 value="��һҳ" type=button onclick="turnPage7.previousPage();">                   
				<input class= cssbutton92 value="��һҳ" type=button onclick="turnPage7.nextPage();"> 
				<input class= cssbutton93 value="β  ҳ" type=button onclick="turnPage7.lastPage();">  
			</center>
			<table class=common>
				<tr class=common>
					<td class=title>����˵��</td>
				</tr>
				<br/>
				<tr class=common>
					<td class=input><span id=ClinicRemark style="display:''"><textarea name=ClinicRemark id=ClinicRemark cols="100" rows="2" witdh=25% class="common"></textarea></span></td>
				</tr>
			</table>	
			<Input type=hidden	name=tSerialNo1>
			<Input type=hidden  name=tCaseNo1>
		</div>
		<input class=cssButton type=button id=addClinicBill1 value="��  ��" onclick=addClinicBill();>
		<input class=cssButton type=button id=modifyClinicBill1 value="��  ��" onclick=modifyClinicBill();>
		<input class=cssButton type=button id=deleteClinicBill1 value="ɾ  ��" onclick=deleteClinicBill();>
		<input class=cssButton type=button id=initClinicBill1 value="��  ��" onclick=resClinicBillInfo();>
		<input class=cssButton type=button id=closePage1 value="��  ��" onclick=closePage();>
	</div>
	<hr class=line>
	<div id="HospBill" style="display:''">	
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this, divHospBill);">
				</td>
				<td class=titleImg>�����˵�¼�롪סԺ��Ϣ </ td>
			</tr>
		</table>
		
		<div id="divHospBill" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
					<span id="spanHospBillGrid"></span>
					</td>
				</tr>
			</table>			
			<center>
				<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage2.firstPage();">
				<input class=cssButton91 type=button value="��һҳ" onclick="turnPage2.previousPage();">
				<input class=cssButton92 type=button value="��һҳ" onclick="turnPage2.nextPage();">
				<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage2.lastPage();">
			</center>			
			
			<table class=common> 
				<tr class=common>
					<td class=title>��Ʊ��</td>
					<td class=input><input class="wid common" name=HospBillNo elementtype=nacessary></td>
					<td class=title>����ҽԺ</td>
					<td class=input><input class=codeno name=HospID readonly><input class=codename name=HospIDName  onkeydown="queryHospInfo(HospID,HospIDName);"  elementtype=nacessary ></td> 
					<td class=title>�˵��������</td>
					<td class=input><input class="wid common" name=HospBillMoney onchange="checkMoney(fm.HospBillMoney);" elementtype=nacessary></td>										
				</tr>
				<tr class=common>         				  	
					<td class=title>��Ժ����</td>
					<td class=input><input class="coolDatePicker" name=HospStartdate  onblur="calculateDate(1);" elementtype=nacessary onClick="laydate({elem: '#HospStartdate'});" id="HospStartdate"><span class="icon"><a onClick="laydate({elem: '#HospStartdate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
					<td class=title>��Ժ����</td>
					<td class=input><input class="coolDatePicker" name=HospEnddate  onblur="calculateDate(1);" elementtype=nacessary onClick="laydate({elem: '#HospEnddate'});" id="HospEnddate"><span class="icon"><a onClick="laydate({elem: '#HospEnddate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
					<td class=title>סԺ����</td>
					<td class=input><input class="wid readonly" name=HospDays readonly></td>
				</tr>
				<tr class=common>
					<td class=title>��Ҫ���(ICD10)</td>
					<td class=input><input class=codeno name=HospICDNo readonly><input class=codename name=HospICDNoName  onkeydown="queryICDInfo(HospICDNo,HospICDNoName,HospICDDetail,HospICDDetailName);" elementtype=nacessary></td> 
					<td class=title>�������(ICD10)</td>
					<td class=input> <input class=codeno name=HospICDDetail ondblclick="return showCodeList('diseasecode',[this, HospICDDetailName],[0,1],null,fm.HospICDNo.value,'upicdcode',1);" onkeyup="return showCodeListKey('diseasecode',[this, HospICDDetailName],[0,1],null,fm.HospICDNo.value,'upicdcode',1);" readonly><input class=codename name=HospICDDetailName onkeydown="queryICDDetailInfo(HospICDDetail,HospICDDetailName,HospICDNo);"></td> 
					<td class=title>Ӱ�������</td>
					<td class=input><input class="wid common" name=HospScanNum></td>
				</tr>  
			</table>
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanHospBillItemGrid"></span>
					</td>
				</tr>
			</table>
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
					<td class=title>����</td>
					<td class=input><input class="wid common" name=ClinicArea1 ></td>
					<td class=title>��������</td>
					<td class=input><input class="coolDatePicker" name="Clinicdate1" onClick="laydate({elem: '#Clinicdate1'});" id="Clinicdate1"><span class="icon"><a onClick="laydate({elem: '#Clinicdate1'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
					<td class=title>��Ʒ/ҩƷ����</td>
					<td class=input><input class=common name=ClinicMediName1></td>
				</tr>
			</table>
			<input class=cssButton type=button name=ClinicDeductQuery value="��  ѯ" type=button onclick=DeductQuery1();>
			<input class=cssButton type=button name=ClinicDeductQuery value="��  ��" type=button onclick=clinicDeductConfirm(2);>
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
					<span id="spanDrugs1Grid"></span>
					</td>
				</tr>
			</table>
			<center>
				<input class=	cssbutton90 value="��  ҳ" type=button onclick="turnPage8.firstPage();"> 
				<input class= cssbutton91 value="��һҳ" type=button onclick="turnPage8.previousPage();">                   
				<input class= cssbutton92 value="��һҳ" type=button onclick="turnPage8.nextPage();"> 
				<input class= cssbutton93 value="β  ҳ" type=button onclick="turnPage8.lastPage();">  
			</center>
			<table class=common>
				<tr class=common>
					<td class=title> ����˵�� </td>
				</tr>
				<br/>
				<tr class common>
					<td class=input> 
						<span id="HospRemark" style= "display: ''">
							<textarea name="HospRemark" id=HospRemark cols="100" rows="2" witdh=25% class="common"></textarea>
						</span>
					</td>
				</tr>
			</table>	
			<Input type=hidden	name=tSerialNo2>
			<Input type=hidden  name=tCaseNo2>
			</div>
			<input class=cssButton type=button id=addHospBill2 value="��  ��" onclick=addHospBill();>
			<input class=cssButton type=button id=modifyHospBill2 value="��  ��" onclick=modifyHospBill();>
			<input class=cssButton type=button id=deleteHospBill2 value="ɾ  ��" onclick=deleteHospBill();>
			<input class=cssButton type=button id=initClinicBill12 value="��  ��" onclick=resHospBillInfo();>
			<input class=cssButton type=button id=closePage2 value="��  ��" onclick=closePage();>
		</div>
		<hr class=line>
		<div id="divSpecialClinc" style="display:''">
			<table>
				<tr>
					<td class=common><img src="../common/images/butExpand.gif" style= "cursor:hand;" onclick="showPage(this,divSpecialClinicBill);"></ td>
					<td class= titleImg>�����˵�¼�롪������Ϣ<font color="red" size="2">�����ڸ߶�ҽ�ơ���Ԯ��ר��ҽ�Ʋ�Ʒ</font></ td>
				</tr>
			</table>
			<div id="divSpecialClinicBill" style="display:''">
				<table>
					<tr>
						<td text-align: left colSpan=1>
							<span id="spanSpecialClinicBillGrid"></span>
						</td>
					</tr>
				</Table>      
				<center>
					<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage3.firstPage();">
					<input class=cssButton91 type=button value="��һҳ" onclick="turnPage3.previousPage();">
					<input class=cssButton92 type=button value="��һҳ" onclick="turnPage3.nextPage();">
					<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage3.lastPage();">
				</center>				
	
			<table class=common>
				<tr class=common>
					<td class=title>��Ʊ��</td>
					<td class=input><input class="wid common" name=SpecialClinicNo elementtype=nacessary></td>
					<td class=title>����ҽԺ</td>
					<td class=input><input class=codeno name=SpecialClinicHospID readonly><input class=codename name=SpecialClinicHospIDName onkeydown="queryHospInfo(SpecialClinicHospID,SpecialClinicHospIDName);"  elementtype=nacessary ></td> 
					<td class=title>�˵��������</td>
					<td class=input><input class="wid common" name=SpecialClinicMoney onchange="checkMoney(fm.SpecialClinicMoney);" elementtype=nacessary></td>
				</tr>
				<tr class=common>         				  	
					<td class=title>��������</td>
					<td class=input><input class="coolDatePicker" name="SpecialClinicStart" elementtype=nacessary onClick="laydate({elem: '#SpecialClinicStart'});" id="SpecialClinicStart"><span class="icon"><a onClick="laydate({elem: '#SpecialClinicStart'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
					<td class=title></td>
					<td class=input></td>
					<td class=title></td>
					<td class=input></td>  
				</tr>
				<tr class=common>
					<td class=title>��Ҫ���(ICD10)</td>
					<td class=input><input class=codeno name=SpecialClinicICD readonly><input class=codename name=SpecialClinicICDName  onkeydown="queryICDInfo(SpecialClinicICD,SpecialClinicICDName,SpecialClinicICDDetail,SpecialClinicICDDetailName);" elementtype=nacessary ></td> 
					<td class=title>�������(ICD10)</td>
					<td class=input><input class=codeno name=SpecialClinicICDDetail style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('diseasecode',[this, SpecialClinicICDDetailName],[0,1],null,fm.SpecialClinicICD.value,'upicdcode',1);" onkeyup="return showCodeListKey('diseasecode',[this, SpecialClinicICDDetailName],[0,1],null,fm.SpecialClinicICD.value,'upicdcode',1);" readonly><input class=codename name=SpecialClinicICDDetailName  onkeydown="queryICDDetailInfo(SpecialClinicICDDetail,SpecialClinicICDDetailName,SpecialClinicICD);" ></td> 
					<td class=title>Ӱ�������</td>
					<td class=input><input class="wid common" name=SpecialClinicScanNum></td>
				</tr>
			</table>
			<div id="divSpecialClinicItem" >
				<table class=common>
					<tr class=common>
						<td text-align: left colSpan=1>
							<span id="spanSpecialClinicItemGrid"></span>
						</td>
					</tr>
				</table>
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
					<td class=title colspan=6>����˵��</td>
				</tr>
				<tr class=common id=SpecailClinicRemark>
					<td class=input colspan=6> 
							<textarea name=SpecailClinicRemark id=SpecailClinicRemark cols="100" rows="2"></textarea>
					</td>
				</tr>
				<Input type=hidden	name=tSerialNo3>
				<Input type=hidden  name=tCaseNo3>
			</table>
			</div>
			<input class=cssButton type=button id=addSpeicalClinic3 value="��  ��" onclick=addSpeicalClinic();>
			<input class=cssButton type=button id=modifySpecialClinic3 value="��  ��" onclick=modifySpecialClinic();>
			<input class=cssButton type=button id=deleteSpecialClinic3 value="ɾ  ��" onclick=deleteSpecialClinic();>
			<input class=cssButton type=button id=initClinicBill13 value="��  ��" onclick=resSpecialClinicBillInfo();>
					<input class=cssButton type=button id=closePage3 value="��  ��" onclick=closePage();>			
		</div>
		<hr class=line>
		<div id="divSpecialHosp" style="display:''">
			<table>
				<tr>
				<td class=common><img src="../common/images/butExpand.gif" style= "cursor:hand;" onclick="showPage(this,divSpecialHosp);"></ td>
				<td class= titleImg>�����˵�¼�롪סԺ��Ϣ<font color="red" size="2">�����ڸ߶�ҽ�ơ���Ԯ��ר��ҽ�Ʋ�Ʒ</font></ td>
				</tr>
			</table>
			<div id=divSpecialHosp style= "display:''">
				<table class= common>
					<tr>
						<td text-align: left colSpan=1><span id="spanSpecialHospGrid"></span></td>
					</tr>
				</table>
				<center>
					<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage4.firstPage();">
					<input class=cssButton91 type=button value="��һҳ" onclick="turnPage4.previousPage();">
					<input class=cssButton92 type=button value="��һҳ" onclick="turnPage4.nextPage();">
					<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage4.lastPage();">
				</center>				
				
				<table class=common>
					<tr class=common>
						<td class=title>��Ʊ��</td>
						<td class=input><input class="wid common" name=SpecialHospBillNo elementtype=nacessary></td>
						<td class=title>����ҽԺ</td>
						<td class=input><input class=codeno name=SpecialHospID readonly><input class=codename name=SpecialHospIDName onkeydown="queryHospInfo(SpecialHospID,SpecialHospIDName);" elementtype=nacessary ></td> 
						<td class=title>�˵��������</td>
						<td class=input><input class="wid common" name=SpecialHospBillMoney  onchange="checkMoney(fm.SpecialHospBillMoney);"  elementtype=nacessary></td>
					</tr>
					<tr class=common>  
						<td class=title>��Ժ����</td>
						<td class=input><input class=coolDatePicker name=SpecialHospStart onblur="calculateDate(2);" elementtype=nacessary onClick="laydate({elem: '#SpecialHospStart'});" id="SpecialHospStart"><span class="icon"><a onClick="laydate({elem: '#SpecialHospStart'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
						<td class=title>��Ժ����</td>
						<td class=input><input class=coolDatePicker name=SpecialHospEnd onblur="calculateDate(2);" elementtype=nacessary onClick="laydate({elem: '#SpecialHospEnd'});" id="SpecialHospEnd"><span class="icon"><a onClick="laydate({elem: '#SpecialHospEnd'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>       				  	
						<td class=title>סԺ����</td>
						<td class=input><input class="wid readonly" name=SpecialHospNum readonly></td>		
					</tr>
					<tr class=common>
						<td class=title>��Ҫ���(ICD10)</td>
						<td class=input><input class=codeno name=SpecialHospICD readonly><input class=codename name=SpecialHospICDName elementtype=nacessary  onkeydown="queryICDInfo(SpecialHospICD,SpecialHospICDName,SpecialHospICDDetail,SpecialHospICDDetailName);"></td> 
						<td class=title>�������(ICD10)</td>
						<td class=input><input class=codeno name=SpecialHospICDDetail  ondblclick="return showCodeList('diseasecode',[this, SpecialHospICDDetailName],[0,1],null,fm.SpecialHospICD.value,'upicdcode',1);" onkeyup="return showCodeListKey('diseasecode',[this, SpecialHospICDDetailName],[0,1],null,fm.SpecialHospICD.value,'upicdcode',1);" readonly><input class=codename name=SpecialHospICDDetailName   onkeydown="queryICDDetailInfo(SpecialHospICDDetail,SpecialHospICDDetailName,SpecialHospICD);" ></td> 
						<td class=title>Ӱ�������</td>
						<td class=input><input class="wid common" name=SpecialHospScanNum></td>
					</tr>        			
				</table>  
				<table class=common>
					<tr class=common>
						<td text-align: left colSpan=1>
							<span id="spanSpecialHospItemGrid"></span>
						</td>
					</tr>
				</table>

				<table class=common>
					<tr class=common>
						<td class=title>����˵��</TD>
					</tr>
					<tr class=common>
						<td class=input><span id=SpecialHospRemark style="display:''"><textarea name=SpecialHospRemark id=SpecialHospRemark cols="100" rows="2" witdh=25% class="common"></textarea></span></td>
					</tr>
				</table>
				<input class=cssButton TYPE=button id=addSpecialHospBill4 value="��  ��" onclick="addSpecialHospBill();">
				<input class=cssButton type=button id=modifySpecialHospBill4 value="��  ��" onclick="modifySpecialHospBill();">
				<input class=cssButton TYPE=button id=deleteSpecialHospBill4 value="ɾ  ��" onclick="deleteSpecialHospBill();">
				<input class=cssButton type=button id=initClinicBill14 value="��  ��" onclick=resSpecialHospBillInfo();>				
				<input class=cssButton type=button id=closePage4 value="��  ��" onclick=closePage();>
			</div>
			<Input type=hidden	name=tSerialNo4>
			<Input type=hidden  name=tCaseNo4>
			<input class=cssButton type=button id=closePage5 value="��  ��" onclick=closePage();>
    </div>
	
	<Input type=hidden  name=Operate> 	 	 <!--��������-->
	<Input type=hidden  name=tCustomerNo>
	<Input type=hidden  name=tRgtNo>
	<Input type=hidden  name=tCaseNo>
	<Input type=hidden  name=currentInput>
	<Input type=hidden  name=ClinicBillNo1>
	<Input type=hidden  name=HospBillNo1>
	<Input type=hidden  name=SpecialClinicBill1>
	<Input type=hidden  name=SpecialHospBillNo1>
	<Input type=hidden	name=CaseSource>
	<Input type=hidden	name=tempHosp>
	<Input type=hidden	name=tempHospStart>
	<Input type=hidden	name=tempHospEnd>
	<Input type=hidden	name=tempResult1>
 	<input type=hidden name=SelNo>				<!--�б����-->
 	<input type=hidden name=PageIndex>		<!--ҳ��-->		
	<br /><br /><br /><br />
</form>	
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
