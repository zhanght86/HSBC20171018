<%
/***************************************************************
 * <p>ProName��PreCustomerManageInput.jsp</p>
 * <p>Title��׼�ͻ�ά������</p>
 * <p>Description��׼�ͻ�ά������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-14
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.ExeSQL"%>
<%
	String tFlag = request.getParameter("Flag");
	String tPreCustomerNo = request.getParameter("PreCustomerNo");
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput) session.getValue("GI");
	ExeSQL	tExeSQL = new ExeSQL();
	String tComGrade = tExeSQL.getOneValue("select a.comgrade from ldcom a where 1=1 and a.comcode='"+tGI.ManageCom+"'");
%>
<script>
	var tFlag = "<%=tFlag%>";
	var tPreCustomerNo = "<%=tPreCustomerNo%>";
	var tComGrade = "<%=tComGrade%>";
</script>
<html>
<head >
	<title>׼�ͻ�ά��</title>
	<script src="../common/javascript/Common.js"></script>
	<script src="../common/cvar/CCodeOperate.js"></script>
	<script src="../common/javascript/MulLine.js"></script>
	<script src="../common/javascript/EasyQuery.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryCache.js"></script>
	<script src="../common/javascript/VerifyInput.js"></script>
	<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<link href="../common/css/Project.css" rel=stylesheet type=text/css>
	<link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<script src="./PreCustomerManageInput.js"></script>
	<%@include file="./PreCustomerManageInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="./PreCustomerManageSave.jsp" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divPreCustomer);">
			</td>
			<td class=titleImg>׼�ͻ�������Ϣ</td>
		</tr>
	</table>
	<div id="divPreCustomer" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>׼�ͻ�����</td>
				<td class=input><input class="wid readonly" name=PreCustomerNo id=PreCustomerNo readonly></td>
				<td class=title>׼�ͻ�����</td>
				<td class=input colspan=3><input class="wid common" name=PreCustomerName id=PreCustomerName verify="׼�ͻ�����|notnull&len<60" maxlength=60 style="width:555px" elementtype=nacessary></td>
			</tr>
			<tr class=common>
				<td class=title>֤������</td>
				<td class=input><input class=codeno name=IDType id=IDType readonly verify="֤������|notnull"
					ondblclick="return showCodeList('gidtype',[this, IDTypeName],[0, 1],null,null,null,'1',180);" 
					onkeyup="return showCodeListKey('gidtype',[this, IDTypeName],[0, 1],null,null,null,'1',180);"><input class=codename name=IDTypeName  id=IDTypeName readonly elementtype=nacessary></td>
				<td class=title>֤������</td>
				<td class=input><input class="wid common" name=IDNo id=IDNo verify="֤������|notnull&len<30" maxlength=30 elementtype=nacessary></td>
				<td class=title>��λ����</td>
				<td class=input><input class=codeno name=GrpNature id=GrpNature readonly verify="��λ����|notnull"
					ondblclick="return showCodeList('grpnature',[this, GrpNatureName],[0, 1],null,null,null,'1',180);" 
					onkeyup="return showCodeListKey('grpnature',[this, GrpNatureName],[0, 1],null,null,null,'1',180);"><input class=codename name=GrpNatureName id=GrpNatureName readonly elementtype=nacessary></td>
			</tr>
			<tr class=common>
				<td class=title>��ҵ����</td>
				<td class=input><input class=codeno name=BusiCategory id=BusiCategory readonly verify="��ҵ����|notnull"
					style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="return showCodeList('category',[this, BusiCategoryName],[0, 1],null,null,null,'1',null);" 
					onkeyup="return showCodeListKey('category',[this, BusiCategoryName],[0, 1],null,null,null,'1',null);"><input class=codename name=BusiCategoryName id=BusiCategoryName readonly elementtype=nacessary></td>
				<td class=title>��λ������</td>
				<td class=input><input class="wid common" name=SumNumPeople id=SumNumPeople verify="��λ������|INT&VALUE>=0"></td>
				<td class=title>Ԥ��Ͷ��������</td>
				<td class=input><input class="wid common" name=PreSumPeople id=PreSumPeople verify="Ԥ��Ͷ��������|INT&VALUE>=0"></td>
			</tr>
			<tr class=common>
				<td class=title>Ԥ�Ʊ��ѹ�ģ(Ԫ)</td>
				<td class=input><input class="wid common" name=PreSumPrem id=PreSumPrem verify="Ԥ�Ʊ��ѹ�ģ|NUM&VALUE>=0"></td>
				<td class=title>�����ϼ��ͻ�</td>
				<td class=input colspan=4><input class=codeno name=UpCustomerNo id=UpCustomerNo style="width:80px"><input class=codename name=UpCustomerName id=UpCustomerName onkeydown="queryUpCustomerNo(UpCustomerNo,this,PreCustomerNo);" style="width:290px"><span style="color: red">��¼���ϼ��ͻ������ûس�ģ����ѯ��</span></td>
				</tr>
			<tr class=common>
				<td class=title>��������</td>
				<td class=input><input class=codeno name=SaleChannel id=SaleChannel readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"
					ondblclick="return showCodeList('salechannel',[this, SaleChannelName],[0, 1],null,null,null,'1',180);" 
					onkeyup="return showCodeListKey('salechannel',[this, SaleChannelName],[0, 1],null,null,null,'1',180);"><input class=codename name=SaleChannelName id=SaleChannelName readonly></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title>��λ��ַ</td>
				<td class=input colspan=5>
					<input class=codeno name=ProvinceName id=ProvinceName style="width:60px" ondblclick="return showCodeList('province',[this, ProvinceCode],[1,0],null,null,null,'1',180);" onkeydown="fuzzyQueryProvince(ProvinceCode,ProvinceName)"><input type=hidden name=ProvinceCode id=ProvinceCode readonly>ʡ
					<input class=codeno name=CityName id=CityName style="width:60px" ondblclick="return returnShowCodeList('city',[this, CityCode],[1,0]);"  onkeydown="fuzzyQueryCity(CityCode,CityName,ProvinceCode)"><input type=hidden name=CityCode id=CityCode readonly>��
					<input class=codeno name=CountyName  id=CountyName style="width:60px" ondblclick="return returnShowCodeList('county',[this, CountyCode],[1,0]);"  onkeydown="fuzzyQueryCounty(CountyCode,CountyName,ProvinceCode,CityCode)"><input type=hidden name=CountyCode id=CountyCode readonly>��/��
					<input class=common name=DetailAddress id=DetailAddress verify="��λ��ϸ��ַ|len<100" maxlength=100 style="width:300px">
				</td>
			</tr>
			<tr class=common>
				<td class=title>��˾���</td>
				<td class=input colspan=5><textarea cols=76 rows=3 name=CustomerIntro id=CustomerIntro></textarea></td>
			</tr>
		</table>
	</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divLink);">
			</td>
			<td class=titleImg>��Ҫ��ϵ����Ϣ<font color=red>����ϵ���ֻ����칫�绰����¼��һ����</font></td>
		</tr>
	</table>
	<div id="divLink" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>��ϵ������</td>
				<td class=input><input class="wid common" name=LinkMan id=LinkMan verify="��ϵ������|notnull" maxlength=30 elementtype=nacessary></td>
				<td class=title>��ϵ���ֻ�</td>
				<td class=input><input class="wid common" name=Mobile id=Mobile verify="��ϵ���ֻ�|PHONE" maxlength=15></td>
				<td class=title>�칫�绰</td>
				<td class=input><input class="wid common" name=Phone id=Phone maxlength=20></td>
			</tr>
			<tr class=common>
				<td class=title>��ϵ�˲���</td>
				<td class=input><input class="wid common" name=Depart id=Depart maxlength=25></td>
				<td class=title>��ϵ��ְ��</td>
				<td class=input><input class="wid common" name=Post id=Post maxlength=25></td>
				<td class=title>��ϵ������</td>
				<td class=input><input class="wid common" name=Email id=Email verify="��ϵ������|EMAIL" maxlength=50></td>
			</tr>
		</table>
	</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divSaler);">
			</td>
			<td class=titleImg>�����ͻ�������Ϣ<font color=red>����ͻ�����ʱ��¼�������ͻ�������Ϣ��</font></td>
		</tr>
	</table>
	<div id="divSaler" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanSalerGrid"></span>
				</td>
			</tr>
		</table>
	</div>
	
	<div>
		<input class=cssButton type=button value="����׼�ͻ�" name="AddButton" id=AddButton onclick="addClick();">
		<input class=cssButton type=button value="�޸�׼�ͻ�" name="ModifyButton" id=ModifyButton onclick="modifyClick();">
		<input class=cssButton type=button value="ɾ��׼�ͻ�" name="DeleteButton" id=DeleteButton onclick="deleteClick();">
		<input class=cssButton type=button value="�޸Ĺ켣��ѯ" name="ModifyTraceButton" id=ModifyTraceButton onclick="TraceQuery();">
		<input class=cssButton type=button value="��  ��" onclick="returnBack();">
	</div>
	
	<div id="divAppQuot" style="display: none">
		<hr class="line"/>
		<input class=cssButton type=button value="����ѯ��" name="AppButton" id=AppButton onclick="applyQuotClick();">
	</div>
	
	<input type=hidden name=Operate id=Operate>
	<input type=hidden name=HiddenPreCustomerNo id=HiddenPreCustomerNo>
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
