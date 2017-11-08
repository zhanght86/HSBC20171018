//程序名称：PDRequRisk.js
//程序功能：产品申请与查询
//创建日期：2009-3-12
//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();
var Mulline9GridTurnPage = new turnPageClass();
var RiskGridTurnPage = new turnPageClass();
//定义sql配置文件
var tResourceName = "productdef.PDRiskQueryInputSql";
function afterSubmit(FlagStr, content) {
	showInfo.close();
	if (FlagStr == "Fail") {
var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+content;
		showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	} else {
var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+content;
		showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		initForm();
		queryDefining();
	}
}
function queryDefining() {
	var mySql=new SqlClass();
	var sqlid = "PDRiskQueryInputSql1";
	mySql.setResourceName(tResourceName); //指定使用的properties文件名
	mySql.setSqlId(sqlid);//指定使用的Sql的id
	mySql.addSubPara(fm.RiskCode.value);//指定传入的参数
	mySql.addSubPara(fm.RequDate.value);//指定传入的参数
	var sql = mySql.getString();
	Mulline9GridTurnPage.pageLineNum = 10;
	Mulline9GridTurnPage.queryModal(sql, Mulline9Grid);
}
function ApplyNewRisk() {
	if (fm.RiskCode.value == "undefined" || fm.RiskCode.value == null || fm.RiskCode.value == "") {
		myAlert("\u7533\u8bf7\u65b0\u9669\u79cd\u5fc5\u987b\u5f55\u5165\u9669\u79cd\u4ee3\u7801");
		return;
	}
	var tSQL = "select count(*) from LWMission where processid = 'pd00000011' and missionprop2 = '" + fm.RiskCode.value + "'";
	tnumber = easyExecSql(tSQL);
	if (tnumber == "1") {
		myAlert("\u7cfb\u7edf\u4e2d\u5df2\u5b58\u5728\u8be5\u9669\u79cd\u7f16\u7801\uff0c\u4e0d\u5141\u8bb8\u91cd\u590d\u7533\u8bf7\uff01");
		return;
	}
	var currentDate = getCurrentDate("-");
	if (compareDate(fm.all("RequDate").value, currentDate) != 0) {
		myAlert("\u7533\u8bf7\u65e5\u671f\u5e94\u4e3a\u4eca\u5929");
		return;
	}
	submitForm();
}
function submitForm() {
	if (!verifyInput2()) {
		return;
	}
	var showStr = "\u6b63\u5728\u5904\u7406\u6570\u636e\uff0c\u8bf7\u60a8\u7a0d\u5019\u5e76\u4e14\u4e0d\u8981\u4fee\u6539\u5c4f\u5e55\u4e0a\u7684\u503c\u6216\u94fe\u63a5\u5176\u4ed6\u9875\u9762";
var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
	showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	fm.action = "./PDRequRiskSave.jsp";
	fm.submit();
}

function BeginDefine() {
	var selNo = Mulline9Grid.getSelNo();
	if (selNo == 0 || selNo == null) {
		myAlert("\u8bf7\u5148\u9009\u62e9\u4e00\u6761\u8bb0\u5f55\uff0c\u518d\u70b9\u51fb\u5f00\u59cb\u5b9a\u4e49\u6309\u94ae\uff01");
		return;
	}
	fm.action = "PDSetSession.jsp?IsReadOnly=0&Operator=define";
	fm.submit();
}
function definAfterSetSession(FlagStr, content) {
	if (FlagStr == "Fail") {
var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+content;
		showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		return;
	}
	var selNo = Mulline9Grid.getSelNo();
	var url = "PDRiskDefiMain.jsp?riskcode=" + Mulline9Grid.getRowColData(selNo - 1, 1) + "&requdate=" + Mulline9Grid.getRowColData(selNo - 1, 3) + "&missionid=" + Mulline9Grid.getRowColData(selNo - 1, 5) + "&submissionid=" + Mulline9Grid.getRowColData(selNo - 1, 6) + "&activityid=" + Mulline9Grid.getRowColData(selNo - 1, 7);
	window.open(url);
}
function queryDefined() {
	var mySql=new SqlClass();
	var sqlid = "PDRiskQueryInputSql2";
	mySql.setResourceName(tResourceName); //指定使用的properties文件名
	mySql.setSqlId(sqlid);//指定使用的Sql的id
	mySql.addSubPara(fm.RiskCodeUsing.value);//指定传入的参数
	mySql.addSubPara(fm.MakeDateB.value);//指定传入的参数
	mySql.addSubPara(fm.MakeDateC.value);//指定传入的参数
	var sql = mySql.getString();
	Mulline10GridTurnPage.pageLineNum = 10;
	Mulline10GridTurnPage.queryModal(sql, Mulline10Grid);
}
function BeginReview() {
	var selNo = Mulline10Grid.getSelNo();
	if (selNo == 0 || selNo == null) {
		myAlert("\u8bf7\u5148\u9009\u62e9\u4e00\u6761\u8bb0\u5f55\uff0c\u518d\u70b9\u51fb\u67e5\u770b\u6309\u94ae\uff01");
		return;
	}
	fm.action = "PDSetSession.jsp?IsReadOnly=1&Operator=query";
//	fm.action = "PDSetSession.jsp";//?IsReadOnly=1&Operator=query";
	fm.submit();
}


function queryAfterSetSession(FlagStr, content) {
	if (FlagStr == "Fail") {
var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+content;
		showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		return;
	}
	fm.ViewMode.value = "view";
	var selNo = Mulline10Grid.getSelNo();
	var url = "PDRiskDefiMain.jsp?riskcode=" + Mulline10Grid.getRowColData(selNo - 1, 1) + "&requdate=" + Mulline10Grid.getRowColData(selNo - 1, 3) + "&missionid=" + Mulline10Grid.getRowColData(selNo - 1, 5) + "&submissionid=" + Mulline10Grid.getRowColData(selNo - 1, 6) + "&activityid=" + Mulline10Grid.getRowColData(selNo - 1, 7);
	window.open(url);
}


function riskQuery() {
	//fm.all('divQuery').style.display = "none";
	var activityId='';
	var riskState  = fm.all('RiskState').value;
	if(riskState == '1'){
		activityId ="pd00000003";
	}else if(riskState == '2'){
		activityId = "pd00000004";
	}else if(riskState == '0'){
		activityId = "pd00000002','pd00000001','pd00000000";
	}
	
	//得到指定<input>方式
	var mySql=new SqlClass();
	var sqlid = "PDRiskQueryInputSql3";
	mySql.setResourceName(tResourceName); //指定使用的properties文件名
	mySql.setSqlId(sqlid);//指定使用的Sql的id
	mySql.addSubPara(fm.RiskCode.value);//指定传入的参数
	mySql.addSubPara(fm.beginDate.value);//指定传入的参数
	mySql.addSubPara(fm.endDate.value);//指定传入的参数
	mySql.addSubPara(activityId);//指定传入的参数
	var sql = mySql.getString();
	Mulline9GridTurnPage.pageLineNum = 10;
	Mulline9GridTurnPage.queryModal(sql, Mulline9Grid);
}







function backToParent(){
	var rowNum = Mulline9Grid.getSelNo()-1;
	if(rowNum == -1 ){
		myAlert(''+"请选择一个已上线产品"+'');
		return;
		}
	top.opener.focus();
	top.opener.document.getElementById('RiskCode2').value=Mulline9Grid.getRowColData(rowNum,1);
	top.opener.document.getElementById('RiskName2').value=Mulline9Grid.getRowColData(rowNum,2);
	top.close();
}

function issueQuery(){
	var selNo = Mulline9Grid.getSelNo();
	if(selNo < 1){
			myAlert(''+"请先选择一条记录"+'');
			return;
	}
	var URL = "PDIssueQueryMain.jsp?riskcode="+Mulline9Grid.getRowColData(selNo-1,1)+"&postcode=00&issuetype=0&findflag=1";
	window.open(URL);
}


function basicInfoQuery(){
		var selNo = Mulline9Grid.getSelNo();
		if(selNo < 1){
			myAlert(''+"请先选择一条记录"+'');
			return;
	}
		var riskcode = Mulline9Grid.getRowColData(selNo-1,1);
		var URL = "PDRiskDefiMain.jsp?riskcode="+riskcode+"&pdflag=0&requdate="+Mulline9Grid.getRowColData(selNo-1,3);
		window.open(URL);
		
}




function ContInfoQuery(){
			var selNo = Mulline9Grid.getSelNo();
		if(selNo < 1){
			myAlert(''+"请先选择一条记录"+'');
			return;
	}
			var riskcode = Mulline9Grid.getRowColData(selNo-1,1);
			var URL = "PDContDefiEntryMain.jsp?riskcode="+riskcode+"&pdflag=0&requdate="+Mulline9Grid.getRowColData(selNo-1,3);
		  open(URL);
}


function EndorseInfoQuery(){
		var selNo = Mulline9Grid.getSelNo();
		if(selNo < 1){
			myAlert(''+"请先选择一条记录"+'');
			return;
			}
			var riskcode = Mulline9Grid.getRowColData(selNo-1,1);
			var riskname = Mulline9Grid.getRowColData(selNo-1,2);
			var URL = "PDEdorDefiEntryMain.jsp?riskcode="+riskcode+"&pdflag=0&requdate="+Mulline9Grid.getRowColData(selNo-1,3)+"&riskname=" + riskname;
		  open(URL);
}

function ClaimInfoQuery(){
	var selNo = Mulline9Grid.getSelNo();
		if(selNo < 1){
			myAlert(''+"请先选择一条记录"+'');
			return;
			}
			var riskcode = Mulline9Grid.getRowColData(selNo-1,1);
			var riskname = Mulline9Grid.getRowColData(selNo-1,2);
			var URL = "PDClaimDefiEntryMain.jsp?riskcode="+riskcode+"&pdflag=0&requdate="+Mulline9Grid.getRowColData(selNo-1,3)+"&riskname="+riskname;
		  open(URL);
}

function LFRiskInfoQuery(){
	var selNo = Mulline9Grid.getSelNo();
		if(selNo < 1){
			myAlert(''+"请先选择一条记录"+'');
			return;
			}
			var riskcode = Mulline9Grid.getRowColData(selNo-1,1);
			var URL = "PDLFRiskMain.jsp?riskcode="+riskcode+"&requdate="+Mulline9Grid.getRowColData(selNo-1,3);
		  open(URL);
	
}


function onLineRiskQuery(){
	var mySql=new SqlClass();
	var sqlid = "PDRiskQueryInputSql4";
	mySql.setResourceName(tResourceName); //指定使用的properties文件名
	mySql.setSqlId(sqlid);//指定使用的Sql的id
	mySql.addSubPara(fm.RiskCode.value);//指定传入的参数
	mySql.addSubPara(fm.beginDate.value);//指定传入的参数
	mySql.addSubPara(fm.endDate.value);//指定传入的参数
	var sql = mySql.getString();
	
	Mulline9GridTurnPage.pageLineNum = 10;
	Mulline9GridTurnPage.queryModal(sql, Mulline9Grid);
	
	
}

//查  询按钮
function QueryRisk()
{
	 var mySql=new SqlClass();
	 var sqlid = "PDRiskQueryInputSql5";
	 mySql.setResourceName(tResourceName); //指定使用的properties文件名
	 mySql.setSqlId(sqlid);//指定使用的Sql的id
	 mySql.addSubPara(fm.queryRiskCode.value);//指定传入的参数
   var strSQL = mySql.getString();
   RiskGridTurnPage.queryModal(strSQL,RiskGrid);
}

function ShowDetail()
{
	try{
	divQuery.style.display="";
}
catch(e)
{
	}
	//var checkFlag = 0;
	//checkFlag = Mulline9Grid.getSelNo();
	var selNo = Mulline9Grid.getSelNo();
	//alert(selNo);
		if(selNo < 1){
			//alert('请先选择一条记录');
			return;
	}
		var RiskNo = Mulline9Grid.getRowColData(selNo-1,1);
			initRiskState("RiskStateGrid",RiskNo);
		/*
	if (checkFlag>0){
		var RiskNo = Mulline9Grid.getRowColData(checkFlag-1, 1);
	//	alert(RiskNo);
		if(RiskNo == null || RiskNo == "" ){
			alert("请先选择一条个人险种信息!");
			return false;
		}
	
	

	}else{
		alert("请先选择一条个人险种信息!");
		return false;
	}	*/
}
function initRiskState(tGrid,RiskNo)
{
	try{
		document.getElementById(tGrid).innerHTML = "";
		
		var showHTML = "<table  cellpadding='0px' cellspacing='0px' >";
		var mySql=new SqlClass();
		var sqlid = "PDRiskQueryInputSql6";
		mySql.setResourceName(tResourceName); //指定使用的properties文件名
		mySql.setSqlId(sqlid);//指定使用的Sql的id
		mySql.addSubPara(RiskNo);//指定传入的参数
		var strSQL = mySql.getString();
		var titleResult = easyExecSql(strSQL);
		
		var titleHTML = "<tr>";
		var colCount = titleResult.length;
		for(var i=0;i<colCount;i++){
			titleHTML = titleHTML + "<td>";
			titleHTML = titleHTML + "<input type=\"checkbox\" id='c" + titleResult[i][0] + "' name='c" + titleResult[i][0] + "' disabled=true style=\"display:none;width:0px;boder:0px\">&nbsp;" ;
			titleHTML = titleHTML + "<input class=readonly4 id=t" + titleResult[i][0] + " name=t" + titleResult[i][0] + " readonly='readonly' value='"+speedConvert(titleResult[i][2])+"' style=\"width: 120px;font-weight: bold;height:30px;border:0px;text-align:center;line-height:30px\">";
			titleHTML = titleHTML + "</td>";
		}
		titleHTML = titleHTML + "</tr>";
		var mySql2=new SqlClass();
		var sqlid2 = "PDRiskQueryInputSql7";
		mySql2.setResourceName(tResourceName); //指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
		mySql2.addSubPara(RiskNo);//指定传入的参数
		strSQL = mySql2.getString();
		var rowResult = easyExecSql(strSQL);
		var rowCount = rowResult[0][0];
		for(var i=0;i<rowCount;i++){
			titleHTML = titleHTML + "<tr>";
			for(var j=0;j<colCount;j++){
				titleHTML = titleHTML + "<td>";
				titleHTML = titleHTML + "<input type=\"checkbox\" id='c" + titleResult[j][0] + "" + (i+1) + "' name='c" + titleResult[j][0] + "" + (i+1) + "' disabled=true  style=\"display:none;width:0px;boder:0px\">&nbsp;" ;
				titleHTML = titleHTML + "<input class=readonly4 id=t" + titleResult[j][0]+ "" + (i+1) + " name=t" + titleResult[j][0]+ "" + (i+1) + " onclick='' readonly='readonly' value='' disabled=true style=\"width:120px;display:none;text-align:center;border:0px;height:35px;line-height:22px\">";
				titleHTML = titleHTML + "</td>";
			}
			titleHTML = titleHTML + "</tr>";
		}
	
		showHTML = showHTML + titleHTML  +"</table>";
		document.getElementById(tGrid).innerHTML = showHTML;
		
		var mySql3=new SqlClass();
		var sqlid3 = "PDRiskQueryInputSql8";
		mySql3.setResourceName(tResourceName); //指定使用的properties文件名
		mySql3.setSqlId(sqlid3);//指定使用的Sql的id
		mySql3.addSubPara(RiskNo);//指定传入的参数
		var strSQL = mySql3.getString();
		var arrResult = easyExecSql(strSQL);
		for(var i=0;i<arrResult.length;i++){
			document.getElementById("c"+arrResult[i][1] + "" + arrResult[i][6]).style.display = "";
			document.getElementById("t"+arrResult[i][1] + "" + arrResult[i][6]).style.display = "";
			document.getElementById("t"+arrResult[i][1] + "" + arrResult[i][6]).value = speedConvert(arrResult[i][2]) ;
			//完成状态
			if(arrResult[i][4] == "1"){
				document.getElementById("c"+arrResult[i][1] + "" + arrResult[i][6]).checked = true ;
				document.getElementById("t"+arrResult[i][1] + "" + arrResult[i][6]).disabled = false;
//				document.getElementById("t"+arrResult[i][1] + "" + arrResult[i][6]).style.backgroundImage = "url(../common/images/v-b22.png)";
				if( i == arrResult.length -1 || (i< arrResult.length -1 && arrResult[i][1] != arrResult[i+1][1]) ){
					document.getElementById("t"+arrResult[i][1] + "" + arrResult[i][6]).style.backgroundImage = "url(../common/images/juxing2.png)";
					document.getElementById("t"+arrResult[i][1] + "" + arrResult[i][6]).style.backgroundRepeat = "no-repeat";
				} else {
					document.getElementById("t"+arrResult[i][1] + "" + arrResult[i][6]).style.backgroundImage = "url(../common/images/v-b22.png)";
				}
			} else {
				
				if( i == arrResult.length -1 || (i< arrResult.length -1 && arrResult[i][1] != arrResult[i+1][1])){
//					alert("t"+arrResult[i][1] + "" + arrResult[i][6]);
					document.getElementById("t"+arrResult[i][1] + "" + arrResult[i][6]).style.backgroundImage = "url(../common/images/juxing1.png)";
					document.getElementById("t"+arrResult[i][1] + "" + arrResult[i][6]).style.backgroundRepeat = "no-repeat";
				} else {
					document.getElementById("t"+arrResult[i][1] + "" + arrResult[i][6]).style.backgroundImage = "url(../common/images/v-b2.png)";
				}
//				document.getElementById("t"+arrResult[i][1] + "" + arrResult[i][6]).style.backgroundImage = "url(../common/images/v-b2.png)";
				if(arrResult[i][3] != null && arrResult[i][3] != ""){
					document.getElementById("t"+arrResult[i][1] + "" + arrResult[i][6]).disabled = false;
					document.getElementById("t"+arrResult[i][1] + "" + arrResult[i][6]).setAttribute("NodeUrl",arrResult[i][3]);
					document.getElementById("t"+arrResult[i][1] + "" + arrResult[i][6]).onclick = function(){OpenRisk(this);};   	
				}	
			}
		}		
		for(var i=0;i<rowResult.length;i++){
			var flag = true;
			for(var j=0;j<rowResult[i][0];j++){
				if(document.getElementById("c"+rowResult[i][1] + "" + (j+1)).checked == false){
					flag = false;
					break;
				}
			}	
			
			//设置标题行的背景
			if(flag == true){
				document.getElementById("c"+rowResult[i][1]).checked = true;
//				document.getElementById("t"+rowResult[i][1]).style.backgroundImage = "url(../common/images/h-b2.png)";
			}else{
//				alert(rowResult[i][1]);
			}
			document.getElementById("t"+rowResult[i][1]).style.backgroundImage = "url(../common/images/h-w2.png)";
//			document.getElementById("t契约业务控制").style.backgroundImage = "url(../common/images/h-b2.png)";
		}
	}catch(re){
		//alert(re);
	}
}
/*
function initRiskState(tGrid,RiskNo)
{
	try{
		var showHTML = "<table>";
		var strSQL = "select NodeCode,ParentNodeCode,NodeName,NodeUrl,NodeState,NodeOrder from RiskState where 1=1 and ParentNodeCode = '0' and RiskNo='" + RiskNo + "' order by NodeOrder";
		var titleResult = easyExecSql(strSQL);
		
		var titleHTML = "<tr class=common>";
		var colCount = titleResult.length;
		for(var i=0;i<colCount;i++){
			titleHTML = titleHTML + "<td>";
			titleHTML = titleHTML + "<input type=\"checkbox\" name='c" + titleResult[i][0] + "' disabled=true >&nbsp;" ;
			titleHTML = titleHTML + "<input class=readonly4 name=t" + titleResult[i][0] + " readonly='readonly' value='"+titleResult[i][2]+"' style=\"width: 120px;font-weight: bold;\">";
			titleHTML = titleHTML + "</td>";
		}
		titleHTML = titleHTML + "</tr>";
	
		strSQL = "select count(1) pc,ParentNodeCode from riskstate where RiskNo='" + RiskNo + "' and ParentNodeCode <> '0' group by parentnodecode order by pc desc";
		var rowResult = easyExecSql(strSQL);
		
		var rowCount = rowResult[0][0];
		for(var i=0;i<rowCount;i++){
			titleHTML = titleHTML + "<tr class=common>";
			for(var j=0;j<colCount;j++){
				titleHTML = titleHTML + "<td>";
				titleHTML = titleHTML + "<input type=\"checkbox\" name='c" + titleResult[j][0] + "" + (i+1) + "' disabled=true  style=\"display:none\">&nbsp;" ;
				titleHTML = titleHTML + "<input type=button class=readonly4 name=t" + titleResult[j][0]+ "" + (i+1) + " readonly='readonly' value='' disabled=true style=\"width: 120px;display:none;text-align:left\">";
				titleHTML = titleHTML + "</td>";
			}
			titleHTML = titleHTML + "</tr>";
		}
	
		showHTML = showHTML + titleHTML  +"</table>";
		document.getElementById(tGrid).innerHTML = showHTML;
		
		var strSQL = "select NodeCode,ParentNodeCode,NodeName,NodeUrl,NodeState,NodeOrder,to_char(row_number() over(partition by ParentNodeCode order by NodeOrder))  "
			+" from RiskState where 1=1 and ParentNodeCode <> '0' and RiskNo='" + RiskNo + "' order by  ParentNodeCode ,NodeOrder desc";
		var arrResult = easyExecSql(strSQL);
		for(var i=0;i<arrResult.length;i++){
			document.getElementById("c"+arrResult[i][1] + "" + arrResult[i][6]).style.display = "";
			document.getElementById("t"+arrResult[i][1] + "" + arrResult[i][6]).style.display = "";
			document.getElementById("t"+arrResult[i][1] + "" + arrResult[i][6]).value = "  " + arrResult[i][2] ;
			if(arrResult[i][4] == "1"){
				document.getElementById("c"+arrResult[i][1] + "" + arrResult[i][6]).checked = true ;
			} else {
				if(arrResult[i][3] != null && arrResult[i][3] != ""){
					document.getElementById("t"+arrResult[i][1] + "" + arrResult[i][6]).disabled = false;
					document.getElementById("t"+arrResult[i][1] + "" + arrResult[i][6]).setAttribute("NodeUrl",arrResult[i][3]);
					document.getElementById("t"+arrResult[i][1] + "" + arrResult[i][6]).onclick = function(){OpenRisk(this);};
				}	
			}
		}		
		
		for(var i=0;i<rowResult.length;i++){
			var flag = true;
			for(var j=0;j<rowResult[i][0];j++){
				if(document.getElementById("c"+rowResult[i][1] + "" + (j+1)).checked == false){
					flag = false;
					break;
				}
			}	
			
			if(flag == true){
				document.getElementById("c"+rowResult[i][1]).checked = true;
			}	
		}
	}catch(re){
		alert(re);
	}
}*/
function OpenRisk(Node)
{
	//window.open(Node.getAttribute("NodeUrl"), "");
}

function ShowRiskInfo()
{
	//fm.all('divQuery').style.display = "";
	var selno = Mulline9Grid.getSelNo();
	fm.queryRiskCode.value = Mulline9Grid.getRowColData(selno-1,1);
	QueryRisk();
}
