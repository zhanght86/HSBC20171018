<%
/***************************************************************
 * <p>ProName��EdorUWGErrInput.jsp</p>
 * <p>Title����ȫ�˱�����</p>
 * <p>Description����ȫ�˱�����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-04-22
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	String tGrpPropNo = request.getParameter("GrpPropNo");
	String tEdorAppNo = request.getParameter("EdorAppNo");
	String tEdorNo =  request.getParameter("EdorNo");
%>
<script>
	var tManageCom = "<%=tGI.ManageCom%>";
	var tOperator = "<%=tGI.Operator%>";
	var tGrpPropNo = "<%=tGrpPropNo%>";
	var tEdorNo =  "<%=tEdorNo%>";
	var tEdorAppNo =  "<%=tEdorAppNo%>";
		
</script>
<html>
<head >
	<title>�˱�����</title>
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
	<script src="./EdorUWGErrInput.js"></script>
	<%@include file="./EdorUWGErrInit.jsp"%>

<script type="text/javascript">	
	var tManageCom = "<%=tGI.ManageCom%>";
	var tOperator = "<%=tGI.Operator%>";
	
	function setTab(m,n){
		var menu=document.getElementById("tab"+m).getElementsByTagName("li");
	
		for(i=0;i<2;i++)
		{
		   menu[i].className=i==n?"now":""; 
			if (i==n) {
				document.getElementById("tablistdiv"+i).style.display = "block";
			} else {
				document.getElementById("tablistdiv"+i).style.display = "none";
			}
		}		
		var tFlag = n+1;
		initForm();
	}

	function setTabOver(m,n){
		var menu=document.getElementById("tab"+m).getElementsByTagName("li");
		menu[n].className="lobutton";
	}
	
	function setTabOut(m,n){
		var menu=document.getElementById("tab"+m).getElementsByTagName("li");
		menu[n].className="libutton";
	}
</script>	
	
</head>
<body onload="initForm(); initElementtype();">
	<div id="tab1">
	<ul>
		<li onclick="setTab(1,0)" class="now">�����б���ʾ</li>
		<li onclick="setTab(1,1)">���������б���ʾ</li>
	</ul>
	</div>
	<div id="tablist1">
			<form name=fm id=fm method=post action="" target=fraSubmit>
				<div id="tablistdiv0" class="tablist block" >
				<!--�����б���ʾ-->
				<table>
					<tr>
						<td class=common>
							<img  src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divUWGErr);">
						</td>
						<td class=titleImg>�����Զ��˱���Ϣ</td>
					</tr>
				</table>
				<div id="divUWGErr" style="display: ''">
					<table class=common>
						<tr>
							<td colSpan=1>
								<span id="spanUWGCErrGrid"></span>
							</td>
				  	</tr>
			 		</table>
			 		<center>
						<input value="��  ҳ" class=cssButton90 type=button onclick="turnPage1.firstPage();">
						<input value="��һҳ" class=cssButton91 type=button onclick="turnPage1.previousPage();">
						<input value="��һҳ" class=cssButton92 type=button onclick="turnPage1.nextPage();">
						<input value="β  ҳ" class=cssButton93 type=button onclick="turnPage1.lastPage();">
					</center>
					<input class=cssButton type=button value="��  ��" onclick="top.close();">
				</div>
				<br/>
				<!--���˵��㼶��Ϣ-->
				<div id="divUWGErr1" style="display: none">
					<table>
					<tr>
						<td class=common>
							<img  src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divUWGErr1);">
						</td>
						<td class=titleImg>����������Ϣ</td>
					</tr>
				</table>
				<table class=common>
						<tr>
							<td colSpan=1>
								<span id="spanUWContErrGrid"></span>
							</td>
						</tr>
					</table>
					<center>
						<input value="��  ҳ" class=cssButton90 type=button onclick="turnPage2.firstPage();">
						<input value="��һҳ" class=cssButton91 type=button onclick="turnPage2.previousPage();">
						<input value="��һҳ" class=cssButton92 type=button onclick="turnPage2.nextPage();">
						<input value="β  ҳ" class=cssButton93 type=button onclick="turnPage2.lastPage();">
					</center>
				</div>
				<!--�ŵ��㼶��Ϣ-->
				<div id="divUWGErr2" style="display: none">
					<table>
						<tr>
							<td class=common>
								<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divUWGErr2);">
							</td>
							<td class=titleImg>���屣���˱�����</td>
						</tr>
					</table>
					<div class=maxbox1>
					<table class=common>
						<tr class=common>
							<td class=title>�˱�����</td>
							<td class=input><input class=codeno name=UWConclusion id=UWConclusion style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('grpcontuwclu',[this,UWConclusionName],[0,1],null,null,null,1,null);" onkeyup="return showCodeListKey('grpcontuwclu',[this,UWConclusionName],[0,1],null,null,null,1,null);"><input class=codename name=UWConclusionName elementtype=nacessary></td>
							<td class=title></td>
							<td class=input></td>
							<td class=title></td>
							<td class=input></td>
						</tr>
						<tr class=common>
							<td class=title>�˱����</td>
							<td class=input colspan=5><textarea name="UWOption" id=UWOption cols=76 rows=2 class=common></textarea></td>
						</tr>
					</table>
					<input class=cssButton type=button value="����˱�����" onclick="saveUWGrpclu();">
					</div>
				</div>
			</div>
			
			<!--���������б���ʾ -->
			<div id="tablistdiv1" class="tablist" style="display: none">
				<table>
					<tr>
						<td class=common>
							<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divCUWMaster);">
							</td>
							<td class=titleImg>�������ѯ����</td>
					</tr>
				</table>
				<div id="divCUWMaster" class=maxbox1 style="display:''">
					<table class=common>
						<tr>
							<td class=title>������������</td>
							<td class=input><input class="wid common" name=insuredName id=insuredName></td>
							<td class=title>֤������</td>
							<td class=input><input class="wid common" name=idNo id=idNo></td>
							<td class=title>���շ���</td>
							<td class=input><Input class="codeno" name="ContPlanCode" id=ContPlanCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showContPlanCode(this,ContPlanCodeName,sysPlanCode);" onkeyup="showContPlanCodeName(this,ContPlanCodeName,sysPlanCode);"><input class=codename name=ContPlanCodeName ><input type=hidden name=sysPlanCode ></td>
						</tr>
						<tr>
							<td class=title>�Ƿ�ͨ���Զ��˱�</td>
							<td class=input><input class=codeno name="FlagState" id=FlagState value="0" style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="showCodeList('trueflag',[this,FlagStateName],[0,1],null,null,null,1,null);" onkeyup="return showCodeListKey('trueflag',[this,FlagStateName],[0,1],null,null,null,1,null);" ><input class=codename name=FlagStateName value="��" readonly=true ></td>
							<td class=title></td>
							<td class=input></td>
							<td class=title></td>
							<td class=input></td>
						</tr>
					</table>
						<input class=cssButton value="��  ѯ" type=button onclick="queryCGUWMaster()">
						<input class=cssButton value="����ͨ��" type=button onclick="passUWMaster()">
						<input class=cssButton value="�����ѯ����" type=button onclick="initQuery()">
					</div>
					
					<!--����������Ϣ-->
					<table>
						<tr>
							<td class=common>
								<img  src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divCUW);">
							</td>
							<td class=titleImg>����������Ϣ</td>
						</tr>
					</table>
					<div id="divCUW" style="display:''">
						<table class=common>
							<tr class=common>
								<td text-align: left colSpan=1>
									<span id="spanUWGErrGrid"></span>
								</td>
							</tr>
						</table>
						<center>
							<input value="��  ҳ" class=cssButton90 type=button onclick="turnPage3.firstPage();">
							<input value="��һҳ" class=cssButton91 type=button onclick="turnPage3.previousPage();">
							<input value="��һҳ" class=cssButton92 type=button onclick="turnPage3.nextPage();">
							<input value="β  ҳ" class=cssButton93 type=button onclick="turnPage3.lastPage();">
						</center>
					</div>
					
					<!--���˺˱���Ϣ-->
					<table>
						<tr>
							<td class=common>
								<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divCCErr);">
								</td>
							<td class=titleImg>���˺˱���Ϣ</td>
						</tr>
				</table>
				<div id="divCCErr" style="display:''">
					<table class=common>
						<tr class=common>
							<td text-align: left colSpan=1>
								<span id="spanCCErrGrid"></span>
							</td>
						</tr>
					</table>
					<center>
						<input value="��  ҳ" class=cssButton90 type=button onclick="turnPage4.firstPage();">
						<input value="��һҳ" class=cssButton91 type=button onclick="turnPage4.previousPage();">
						<input value="��һҳ" class=cssButton92 type=button onclick="turnPage4.nextPage();">
						<input value="β  ҳ" class=cssButton93 type=button onclick="turnPage4.lastPage();">
					</center>	
				</div>
				
				<!--����������Ϣ-->
				<div id="divPerPol01" style="display:none">
						<table>
							<tr>
								<td class=common>
									<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divCpolErr);">
								</td>
								<td class=titleImg>��������������Ϣ</td>
							</tr>
						</table>
						<div id="divCpolErr" style="display:''">
							<table class=common>
								<tr class=common>
									<td text-align: left colSpan=1>
										<span id="spanCpolErrGrid"></span>
									</td>
								</tr>
							</table>
							<input class=cssButton value="��  ��" type=button onclick="savePremAdjust()">	
						</div>
				</div>
				
				<div id="divPerPol02" style="display:none">
						<table>
							<tr>
								<td class=common>
									<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divCpolErr);">
								</td>
								<td class=titleImg>��������������Ϣ</td>
							</tr>
						</table>
						<div id="divCpolErr" style="display:''">
							<table class=common>
								<tr class=common>
									<td text-align: left colSpan=1>
										<span id="spanCpolTErrGrid"></span>
									</td>
								</tr>
							</table>
							<!--<input class=cssButton value="��ӡ������֪֪ͨ��" type=button onclick="">	-->
						</div>
				</div>
				
				<!--���˺˱�����-->
				<table>
					<tr>
						<td class=common>
							<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divUWConclu);">
						</td>
						<td class=titleImg>���˺˱�����</td>
					</tr>
				</table>
				<div id="divUWConclu" class=maxbox1 style="display: ''">
					<table class=common>
						<tr class=common id="showReins" style="display: none" >
							<td class=title>�����ٱ�����</td>
							<td class=input><input class=codeno name=ReinsType id=ReinsType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick=" showCodeList('reinsarrange',[this,ReinsTypeName],[0,1],null,null,null,1,null);" onkeyup="return showCodeListKey('reinsarrange',[this,ReinsTypeName],[0,1],null,null,null,1,null);"><input class=codename name=ReinsTypeName elementtype=nacessary></td>
							<td class=title></td>
							<td class=input></td>
							<td class=title></td>
							<td class=input></td>
						</tr>
						<tr class=common>
							<td class=title>���˺˱�����</td>
							<td class=input><input class=codeno name=UWConclu id=UWConclu verify="���˺˱�����|NOTNULL" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick=" showCodeList('contuwclu',[this,UWConcluName],[0,1],null,null,null,1,null);" onkeyup="return showCodeListKey('contuwclu',[this,UWConcluName],[0,1],null,null,null,1,null);"><input class=codename name=UWConcluName elementtype=nacessary></td>
							<td class=title></td>
							<td class=input></td>
							<td class=title></td>
							<td class=input></td>
					</tr>
					<tr class=common>
						<td class=title>���˺˱����</td>
						<td class=input colspan=5>
							<textarea cols=76 rows=2 class=common name=UWIdea id=UWIdea></textarea>
						</td>
					</tr>
				</table>
				<input class=cssButton value="������˺˱�����" type=button onclick="saveUWConclu()">
				<!--<input class=cssButton value="����������˺˱�����" type=button onclick="saveUWContAllclu()">-->
				<input class=cssButton value="��  ��" type=button onclick="top.close();">
			</div>
		</div>
		
		<!--��������-->
		<input type=hidden name=Operate>
		<input type=hidden name=GrpPropNo value="<%=tGrpPropNo%>">
		<input type=hidden name=EdorNo value="<%=tEdorNo%>">
		<input type=hidden name=EdorAppNo value="<%=tEdorAppNo%>">
		<input type=hidden name=ReinsFlag>
		</form>
		<Br /><Br /><Br /><Br />
	</div>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
