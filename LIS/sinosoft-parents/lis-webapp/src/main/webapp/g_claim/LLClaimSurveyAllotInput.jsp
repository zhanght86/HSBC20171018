<%
/***************************************************************
 * <p>PreName��LLClaimSurveyAllotInput.jsp</p>
 * <p>Title�������������</p>
 * <p>Description�������������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �߶���
 * @version  : 8.0
 * @date     : 2014-02-21
 ****************************************************************/
%>
 <%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");	
	String tManageCom =tGI.ManageCom;
	String tOperator = tGI.Operator;
	
%>
<script>
	var tManageCom = "<%=tManageCom%>"; //��¼��½����
	var tOperator = "<%=tOperator%>";  //��¼������
</script>
<html>
<head>
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
	<script src="../common/javascript/jquery-1.7.2.js"></script>
 	<script src="./LLClaimSurveyAllotInput.js"></script>
 	<%@include file="./LLClaimSurveyAllotInit.jsp"%>
 		<title>�����������</title>
</head>
<body onload="initForm();initElementtype();">
	<form name=fm id=fm method=post action="./LLClaimSurveyAllotSave.jsp" target=fraSubmit>	
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQueryCondition);">
				</td>
				<td class=titleImg>��ѯ����</td>
			</tr>
		</table>
		<div id="divQueryCondition" class=maxbox1 style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>������</td>
					<td class=input><input class="wid common" name=acceptNo ></td>
					<td class=title>Ͷ��������</td>
					<td class=input><input class="wid common" name=appntName ></td>
					<td class=title></td>
					<td class=input></td> 
				</tr>
				<tr  class=common>
					<td class=title>������������</td>
					<td class=input><input class="wid common" name=customerName></td>
					<td class=title>֤������</td>
					<td class=input><input class="wid common" name=idNo></td>
					<td class=title>��������</td>
					<td class=input><Input class=codeno  name=inqType ondblclick="return showCodeList('inqtype',[this,inqTypeName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('inqtype',[this,inqTypeName],[0,1],null,null,null,1);" ><input class=codename name="inqTypeName" readonly></td>
				</tr>
				<tr  class=common>
					<td class=title>���鷢������</td>
					<td class=input><input class='coolDatePicker' dateFormat="Short" name=applyStartdate onClick="laydate({elem: '#applyStartdate'});" id="applyStartdate"><span class="icon"><a onClick="laydate({elem: '#applyStartdate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td> 
					<td class=title>���鷢��ֹ��</td>
					<td class=input><input class='coolDatePicker' dateFormat="Short" name=applyEndDate onClick="laydate({elem: '#applyEndDate'});" id="applyEndDate"><span class="icon"><a onClick="laydate({elem: '#applyEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
					<td class=title>���鷢�����</td>
					<td class=input><Input class=codeno name=initdept style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('conditioncomcode',[this,InitdeptName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('conditioncomcode',[this,InitdeptName],[0,1],null,null,null,1);"><input class=codename name="InitdeptName" readonly=true></td>
				</tr>
      </table>
    </div>
		<input class=cssButton VALUE="��ѯ��������" TYPE=button name=SurveyDeptQuery onclick="SurveyTaskQueryClick();"> 
		 
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divSurveyTaskGrid);">
				</td>
				<td class=titleImg>��������������б�<font color="#FF0000">��ֻ��ԡ���ص��顿����</font></td>
			</tr>
		</table>   
		<div  id="divSurveyTaskGrid" align=left style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1><span id="spanSurveyTask1Grid" ></span></td>
				</tr>
			</table>
			<center>
		    <input class=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage1.firstPage();">
		    <input class=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage1.previousPage();">
		    <input class=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage1.nextPage();">
		    <input class=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage1.lastPage();">		    
			</center>
    </div>
    <div id= "divInqPer2" style= "display: none" >
			<table class = common >
				<tr  class=common>
					<td class=title> ָ��������� </td> 
					<td class=input><Input class=codeno name=InqDept  style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('conditioncomcode',[this,InqDeptName],[0,1],null,str,'1',1);" onkeyup="return showCodeListKey('conditioncomcode',[this,InqDeptName],[0,1],null,str,'1',1);"><input class=codename name="InqDeptName" readonly=true elementtype=nacessary></td>    
					
					<td class=title></td>
					<td class=input></td>
					<td class=title></td>
					<td class=input></td>
				</tr>          
			</table>   
			<input class=cssButton value="ָ��ȷ��"  TYPE=button onclick="Designate()";>
    </div>     	 	 		 	
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQueryCondition1);">
				</td>
				<td class=titleImg>��ѯ����</td>
			</tr>
		</table>  
		<div id="divQueryCondition1" class=maxbox1 style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>������</td>
					<td class=input><input class="wid common" name=acceptNo1 ></td>
					<td class=title>Ͷ��������</td>
					<td class=input><input class="wid common" name=appntName1 ></td>
					 <td class=title></td>
					<td class=input></td> 
				</tr>
				<tr>
					<td class=title>������������</td>
					<td class=input><input class="wid common" name=customerName1></td>
					<td class=title>֤������</td>
					<td class=input><input class="wid common" name=idNo1></td>
					<td class=title>��������</td>
					<td class=input><Input class=codeno readonly name=inqType1 style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('inqtype',[this,inqTypeName1],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('inqtype',[this,inqTypeName1],[0,1],null,null,null,1);" ><input class=codename name="inqTypeName1" readonly></td>        
	      </tr>
				<tr>
					<td class=title>���鷢������</td>
					<td class=input><input class='coolDatePicker' dateFormat="Short" name=applyStartdate1 onClick="laydate({elem: '#applyStartdate1'});" id="applyStartdate1"><span class="icon"><a onClick="laydate({elem: '#applyStartdate1'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td> 
					<td class=title>���鷢��ֹ��</td>
					<td class=input><input class='coolDatePicker' dateFormat="Short" name=applyEndDate1 onClick="laydate({elem: '#applyEndDate1'});" id="applyEndDate1"><span class="icon"><a onClick="laydate({elem: '#applyEndDate1'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
					<td class=title>�Ƿ���ص���</td>
					<td class=input><Input class=codeno name=IsLoc ondblclick="return showCodeList('trueflag',[this,IsLocName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('trueflag',[this,IsLocName],[0,1],null,null,null,1);"><input class=codename name="IsLocName" readonly=true></td> 
				</tr>
				<tr>	
					<td class=title>���鷢�����</td>
					<td class=input><Input class=codeno name=Initdept1 style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('conditioncomcode',[this,InitdeptName1],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('conditioncomcode',[this,InitdeptName1],[0,1],null,null,null,1);"><input class=codename name="InitdeptName1" readonly=true></td>
				    <td class=title>�������</td>
					<td class=input><Input class=codeno name=Initdept2 style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('conditioncomcode',[this,InitdeptName2],[0,1],null,str2,'1',1);" onkeyup="return showCodeListKey('conditioncomcode',[this,InitdeptName2],[0,1],null,str2,'1',1);"><input class=codename name="InitdeptName2" readonly=true></td>
				    <td class=title></td>
					<td class=input></td> 
				</tr>
      </table>
    </div>
    
    <input class=cssButton VALUE="��ѯ��������" TYPE=button onclick="querytoTaskInfo();">   
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divSurveyTaskGrid);">
				</td>
				<td class=titleImg>�������������б�</td>
			</tr>
		</table>     
		<div  id="divSurveyTaskGrid" align=left style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanSurveyTaskGrid" ></span>
					</td>
				</tr>
	   	</table>
			<center>
		    <input class=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage1.firstPage();">
		    <input class=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage1.previousPage();">
		    <input class=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage1.nextPage();">
		    <input class=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage1.lastPage();">		    
	    </center>
    </div>
    <div id= "divInqPer" style= "display:none" >
      <table class = common >
        <tr  class=common>
					<td class=title> ָ�������� </td> 
					<td class=input><Input class=codeno  name=InqPer style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('adjustper',[this,InqPerName],[0,1],null,str1,'1',1);" onkeyup="return showCodeListKey('adjustper',[this,InqPerName],[0,1],null,str1,'1',1);"><input class=codename name=InqPerName readonly=true elementtype=nacessary></td>    
					<td class=title></td> 
					<td class=input></td> 
					<td class=title></td> 
					<td class=input></td> 
				</tr>          
       </table>   
			<input class=cssButton value="ָ��ȷ��"  TYPE=button onclick="Designate1()";>
    </div>  
     <!--������-->
		<Input type=hidden  name=Operate> 	 	 <!--��������-->
		<Input type=hidden  name=CustomerNo>
		<Input type=hidden  name=InitPhase>
		<Input type=hidden  name=AcceptMngCom>
		<Input type=hidden  name=InqCom>
		<Input type=hidden  name=LocFlag><!--���ر�ʶ-->
		<Input type=hidden  name=InqNo><!--�������-->
	  <Input type=hidden  name=BatNO><!--���κ�-->
	  <Input type=hidden	name=RgtNo>
	  <Input type=hidden	name=GrpRgtNo>
	  <Input type=hidden	name=InqFlowState>
	  <Input type=hidden	name=InitiationType>
	  <Br /> <Br /> <Br /> <Br />
		</form>	
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>  
</body>
</html>
