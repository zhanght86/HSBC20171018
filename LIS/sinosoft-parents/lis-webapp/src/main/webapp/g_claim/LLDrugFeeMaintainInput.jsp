<%
/***************************************************************
 * <p>ProName：LLDrugFeeMaintainInput.jsp</p>
 * <p>Title：药品信息维护</p>
 * <p>Description：药品信息维护</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 高冬华
 * @version  : 8.0
 * @date     : 2014-02-26
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");	
	String tManageCom = tGI.ManageCom;
	String tOperator = tGI.Operator;
	
%>
<script>
	var tManageCom = "<%=tManageCom%>"; //记录登陆机构
	var tOperator = "<%=tOperator%>";  //记录操作人
	var tCondition=" 1  and managecom like #"+tManageCom+"%#";
</script>
<html>
<head>
	<title>药品信息维护</title>
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
	<script src="./LLDrugFeeMaintainInput.js"></script>
	<%@include file="./LLDrugFeeMaintainInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
	<form name=fm id=fm method=post action="./LLDrugFeeMaintainSave.jsp" target=fraSubmit>
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQueryInfo10);">
				</td>
				<td class=titleImg>查询条件</td>
			</tr>
		</table> 
		<div id="divQueryInfo10" class=maxbox1>
			<table class = common>
				<tr class = common>
					<td class= title>地区</td>
  				<td class= input><input class=codeno name="areaCodeQ" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('city',[this,areaNameQ],[0,1],null,'1','1',1);" onkeyup="return showCodeListKey('city',[this,areaNameQ],[0,1],null,'1','1',1);"><input class=codename name="areaNameQ" ></td>
	    		<td class= title>更新起期</td>
					<td class= Input ><Input class="coolDatePicker" dateFormat="short"  name=StartdateQ onClick="laydate({elem: '#StartdateQ'});" id="StartdateQ"><span class="icon"><a onClick="laydate({elem: '#StartdateQ'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
					<td class= title>更新止期</td>
					<td class= Input ><Input class="coolDatePicker" dateFormat="short"  name=EndDateQ onClick="laydate({elem: '#EndDateQ'});" id="EndDateQ"><span class="icon"><a onClick="laydate({elem: '#EndDateQ'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				</tr>	
				<tr class = common>
  				<td class= title>药品名称</td>		
  				<td class= input><input class="wid common" name="drugFeeMaintainNameQ"></td>
	    		<td class= title>商品名称</td>
  				<td class= input><input class="wid common" name="bussinessNameQ"></td>
					<td class= title></td>
  				<td class= input></td>
				</tr>	
			</table>	
		</div>
		<input class=cssButton type=button value="查  询" onclick="queryPermissionInfo();">

		<table>
			<tr>
	    	<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQueryInfo1);">
				</td>
				<td class=titleImg>药品列表</td>
			</tr>
		</table> 
		<div  id= "divQueryInfo1" style= "display: ''">
			<table  class= common>
				<tr  class= common>
					<td text-align: left colSpan=1>
						<span id="spanDrugFeeMaintainGrid"></span> 
					</td>
				</tr>
			</table>
		</div>
		<center>
			<input class=cssbutton90 value="首  页" type=button onclick="turnPage2.firstPage();"> 
			<input class= cssbutton91 value="上一页" type=button onclick="turnPage2.previousPage();">                   
			<input class= cssbutton92 value="下一页" type=button onclick="turnPage2.nextPage();"> 
			<input class= cssbutton93 value="尾  页" type=button onclick="turnPage2.lastPage();">  
			<input class= cssbutton value="导出数据" type=button onclick="exportData();">  
		</center>
		<table>
			<tr>
	    	<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQueryInfo00);">
				</td>
				<td class=titleImg>理赔药品信息</td>
			</tr>
		</table> 
		
		<div id="divQueryInfo00" class=maxbox1>
			<input class=	cssbutton value="新  增" type=button name=addPer onclick="addDrugFee();"> 
	    <input class= cssbutton value="修  改" type=button name=modifyPer onclick="modifyDrugFee();">                   
	    <input class= cssbutton value="删  除" type=button name=deletePer onclick="deleteDrugFee();"> 
	    <input class= cssbutton value="重  置" type=button name=RestPer onclick="restartDrugFee();"> 
	   <br/>
		<table class= common>
			<tr class= common>
  			<td class= title>地区</td>
  			<td class= input><input class=codeno name="areaCode" verify="地区|NOTNULL" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('city',[this,areaName],[0,1],null,'1','1',1);" onkeyup="return showCodeListKey('city',[this,areaName],[0,1],null,'1','1',1);"><input class=codename name="areaName" elementtype=nacessary ></td>
    		<td class= title>更新日期</td>
 				<td class= Input ><Input class="coolDatePicker" dateFormat="short" verify="更新日期|notnull" name=UpdateDate elementtype=nacessary onClick="laydate({elem: '#UpdateDate'});" id="UpdateDate"><span class="icon"><a onClick="laydate({elem: '#UpdateDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
  			<td class= title></td>
  			<td class= input></td>
			</tr>
			<tr class= common>
  			<td class= title>药品名称</td>
  			<td class= input><input class="wid common" name="drugFeeMaintainName"></td>
  			<td class= title>商品名称</td>
  			<td class= input><input class="wid common" name="bussinessName" ></td>
				<td class= title>医保类型</td>
  			<td class= input><input class=codeno name="medicalInsuranceCode" verify="医保类型|NOTNULL" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('madisontype',[this,medicalInsurance],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('madisontype',[this,medicalInsurance],[0,1],null,null,null,1);"><input class=codename name="medicalInsurance"  elementtype=nacessary></td>
			</tr>
			<tr class= common>
				<td class= title>剂型</td>
  			<td class= input><input class="wid common" name="drugForm"  verify="剂型|NOTNULL" elementtype=nacessary></td>
  			<td class= title>规格</td>
  			<td class= input><input class="wid common" name="format"></td>
  			<td class= title>价格</td>
  			<td class= input><input class="wid common" name="price"></td>
			</tr>
				<tr class= common>
				<td class= title>自付比例</td>
  			<td class= input><input class="wid common" name="selfRate" verify="自付比例|NOTNULL" elementtype=nacessary></td>
  			<td class= title>限制内容</td>
  			<td class= input><input class="wid common" name="restrictions"></td>
  			<td class= title></td>
  			<td class= input></td>
			</tr>
		</table>	
		<!--隐藏表单区域-->	
		<input type=hidden  name=Operate > 
		<input type=hidden  name=tDrugsSerialNo > 
		
	</div>
</form>
<form method=post name=uploadfm target=fraSubmit ENCTYPE="multipart/form-data">	
	<input type=hidden  name=ImpOperate >
	<table>
		<tr>
	    <td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divImportGrid);">
			</td>
			<td class=titleImg>药品信息批量导入</td>
		</tr>
	</table> 
	<div id="divImportGrid" class=maxbox1 style="text-align:left;">
		<table class=common>
			<tr class=common>
				<td class= title></td>
				<td class= input></td>
				<td class= title></td>
				<td class= input></td>
				<td class= title></td>
				<td class= input></td>
			</tr>
			<tr class=common>
				<td class=title>附件上载</td>
				<td class=input colspan=5>
					<input class=common name=UploadPath  type="file" style="width:380px" >&nbsp;&nbsp;&nbsp;
					<input class=cssButton type=button value="清单导入" onclick="medicalImport()"/>&nbsp;&nbsp;
					<a href='../template/LLClaimDrugImp.xlsx'>导入模板下载</a>
				</td>
			</tr>
			<tr class=common>
				<td class=title>导入错误原因</td>
				<td class=input rowspan="2" colspan="5"><textarea class=common name=ErrorMessage id=ErrorMessage readonly cols="67" rows="4"></textarea></td>
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
  </form>
  <Br /><Br /><Br /><Br />
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>  
</body>
</html>
