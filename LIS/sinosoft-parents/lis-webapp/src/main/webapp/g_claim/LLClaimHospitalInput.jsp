<%
/***************************************************************
 * <p>ProName：LLClaimHospitalInput.jsp</p>
 * <p>Title：医院信息维护</p>
 * <p>Description：医院信息维护</p>
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
	var tCondition=" 1  and comcode like #"+tManageCom+"%#";
</script>
<html>
<head>
	<title>医院信息维护</title>
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
	<script src="./LLClaimHospitalInput.js"></script>
	<%@include file="./LLClaimHospitalInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
	<form name=fm id=fm method=post action="./LLClaimHospitalSave.jsp" target=fraSubmit>
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,QueryHospital);">
				</td>
				<td class=titleImg>查询条件</td>
			</tr>
		</table> 
		<div  id= "QueryHospital" class=maxbox1 style= "display:''">
			<table  class= common>
				<tr class= common>  
					<td  class= title>医院代码</td>
					<td  class= input> <input class="wid common" name=HospitalCodeQ></td>
					<td  class= title>医院名称</td>
					<td  class= input> <input class="wid common" name=HospitalNameQ></td>
					<td  class= title>医院状态</td>      
					<td  class= input><input class=codeno  name=HosStateQ style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('stateflag',[this,HosStateNameQ],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('stateflag',[this,HosStateNameQ],[0,1],null,null,null,'1',null);"><input class=codename name=HosStateNameQ readonly ></td>
				</tr>
				<tr class= common>
					<td  class= title>医院等级</td>      
					<td class= input><Input class=codeno  name="HosGradeQ" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('llhosgrade',[this,HosGradeNameQ],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('llhosgrade',[this,HosGradeNameQ],[0,1],null,null,null,'1',null);"><input class=codename name=HosGradeNameQ readonly=true ></td> 	                 
					<td  class= title></td>
					<td  class= input></td>
					<td  class= title></td>
					<td  class= input></td>
				</tr>
			</table>  
			<input value="查  询" class= cssButton type=button onclick="QueryHospitalInfo();">
		</div>
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divCustomerInfo);">
				</td>
				<td class=titleImg>医院信息列表</td>
			</tr>
		</table>  
		<div id= "DivLLCommendHospitalGrid" style= "display:''">    
			<table  class= common>
				<tr>
					<td text-align: left colSpan=1><span id="spanLLCommendHospitalGrid"></span></td>
				</tr>
			</table>
			<center>
				<input class=cssButton90 type=button value="首  页" onclick="turnPage1.firstPage();">
				<input class=cssButton91 type=button value="上一页" onclick="turnPage1.previousPage();">
				<input class=cssButton92 type=button value="下一页" onclick="turnPage1.nextPage();">
				<input class=cssButton93 type=button value="尾  页" onclick="turnPage1.lastPage();">
				<input class=cssButton type=button value="导出数据" onclick="exportData();">
			</center>
		</div>
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divCustomerInfo);">
				</td>
				<td class=titleImg>医院信息</td>
			</tr>
		</table>  
		<div id= "DivLLCommendHospitalInfo" class=maxbox1 style= "display:''">       
		<div>
			<input class=cssButton name="saveHospitalButton"   value="新  增"  	type=button onclick="HospitalAddClick();">
			<input class=cssButton name="editHospitalButton"   value="修  改"   type=button onclick="HospitalEditClick();">
			<input class=cssButton name="deleteHospitalButton" value="删  除"   type=button onclick="HospitalDeleteClick();">
			<input class=cssButton name="HospitalTurnBackButton" value="重  置"   type=button onclick="HospitalTurnBack();">
		</div>    
		<table  class= common>
			<tr class= common>   
				<td  class= title>医院代码</td>
				<td  class= input><input class="wid common" name=HospitalCode elementtype=nacessary></td>
				<td  class= title>医院名称</td>      
				<td  class= input><input class="wid common" name=HospitalName elementtype=nacessary></td>
				<td  class= title>医院状态</td>      
				<td class= input><Input class=codeno  name=HosState style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('stateflag',[this,HosStateName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('stateflag',[this,HosStateName],[0,1],null,null,null,'1',null);"><input class=codename name=HosStateName readonly elementtype=nacessary></td> 	                 
			</tr>
			<tr class= common>
				<td  class= title>医院等级</td>      
				<td class= input><Input class=codeno  name=HosGrade style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('llhosgrade',[this,HosGradeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('llhosgrade',[this,HosGradeName],[0,1],null,null,null,'1',null);"><input class=codename name=HosGradeName readonly=true elementtype=nacessary></td> 	                 
				<td class=title>医院电话</td>
				<td class=input><input class="wid common" name=HosPhone></td>
				<td  class= title></td>      
				<td  class= input></td>
			</tr>
			<tr class=common>
        <td class=title>医院地址</td>
				<td  class=input colspan=5><input class=common name=HosAddress id=HosAddress style="width:480px"></td>
			</tr>
		</table>
	</div>
    <!--隐藏表单区域-->	
	<input type=hidden id="Operate" name=Operate >
	<input type=hidden id="fmtransact" name="fmtransact">	
</form>
<form method=post name=uploadfm target=fraSubmit ENCTYPE="multipart/form-data">	
	<input type=hidden  name=ImpOperate >
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divImportGrid);">
			</td>
			<td class=titleImg>医院信息批量导入</td>
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
					<input class=cssButton type=button value="清单导入" onclick="submitImport()"/>&nbsp;&nbsp;
					<a href='../template/LLClaimHospImp.xlsx' style="color:black;">导入模板下载</a>
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
	<br /><br /><br /><br />
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>  
</body>
</html>
