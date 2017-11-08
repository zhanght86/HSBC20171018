//该文件中包含客户端需要处理的函数和事件
var mDebug="0";
var mOperate="";
var showInfo;
var arrDataSet;
var QueryCount = 0;
var mulLineCount = 0;
var tSearch = 0;
var turnPage = new turnPageClass();
window.onfocus=myonfocus;
//使得从该窗口弹出的窗口能够聚焦
function myonfocus(){
	if(showInfo!=null){
		try{
			showInfo.focus();
		}
		catch(ex){
			showInfo=null;
		}
	}
}

//数据提交（修改）
function UptContClick(){
//	if (tSearch == 0){
//		alert("请先查询要险种信息！");
//		return false;
//	}

	fm.all('mOperate').value = "UPDATE||MAIN";
	if (!beforeSubmit()){
		return false;
	}
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	QueryCount = 0;	//重新初始化查询次数
	fm.submit(); //提交
}

function afterCodeSelect( cCodeName, Field ){
	//判定双击操作执行的是什么查询
	if (cCodeName=="GrpRisk"){
		var tRiskFlag = fm.all('RiskFlag').value;
		//由于附加险不带出主险录入框，因此判定当主附险为S的时候隐藏
		if (tRiskFlag!="S"){
			divmainriskname.style.display = 'none';
			divmainrisk.style.display = 'none';
			fm.all('MainRiskCode').value = fm.all('RiskCode').value;
		}
		else{
			divmainriskname.style.display = '';
			divmainrisk.style.display = '';
			fm.all('MainRiskCode').value = "";
		}
	}
}

//提交，保存按钮对应操作
function submitForm(){
	//alert(fm.all('mOperate').value);
	if (!beforeSubmit()){
		return false;
	}

	if (!confirm('默认值下的全部险种要素信息是否已录入完毕，您是否要确认操作？')){
		return false;
	}
	
	if (fm.all('mOperate').value == ""){
		fm.all('mOperate').value = "INSERT||MAIN";
	}

	var i = 0;
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	fm.submit(); //提交
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content ){
	showInfo.close();
	if( FlagStr == "Fail" ){
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
	else{
		content = "保存成功！";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		fm.all('mOperate').value = "";
	initForm();
	//alert(fm.all('mOperate').value);
	QueryCount = 0;	//重新初始化查询次数
	tSearch = 0;
	}
	//alert(fm.all('mOperate').value);
	
}

//重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm(){
	try{
		initForm();
	}
	catch(re){
		alert("在LAAgent.js-->resetForm函数中发生异常:初始化界面错误!");
	}
}

//取消按钮对应操作
function cancelForm(){
	showDiv(operateButton,"true");
	showDiv(inputButton,"false");
}

//提交前的校验、计算
function beforeSubmit(){
	if(ContPlanGrid.mulLineCount == 0){
		alert("请选择险种责任！");
		return false;
	}
	
	var lineCount = ContPlanGrid.mulLineCount;
	var sValue;
	var sCalMode;
	//
	var dutycode;
	var hierarhy;
	var flag109;//是否存在109险种
	var flagerror;//如果存在109险种可选责任的档次大于基本责任则为1
	var duty1;
	var duty2;
	var duty3;
	var duty4;
	//添加要素值信息校验
	for(var i=0;i<lineCount;i++){
      	sValue = ContPlanGrid.getRowColData(i,8);
      	sCalMode = ContPlanGrid.getRowColData(i,5);
      	dutycode=ContPlanGrid.getRowColData(i,3);
      	hierarhy=ContPlanGrid.getRowColData(i,8);
      	//alert(dutycode);
      	//return;
      	//增加对109的特殊校验
      	//alert("ok");
      	if(dutycode.substring(0,3)=="109")
      	{
      		flag109="1";
      	}
      	if(dutycode=="109001" && sCalMode=="StandbyFlag1")
      	{
      		duty1=hierarhy;
      		//alert(duty1);
      	}else if(dutycode=="109002" && sCalMode=="StandbyFlag1")
      		{
      			duty2=hierarhy;
      			//alert(duty2);
      		}else if(dutycode=="109003" && sCalMode=="StandbyFlag1")
      			{
      				duty3=hierarhy;
      				//alert(duty3);
      			}else if(dutycode=="109004" && sCalMode=="StandbyFlag1")
      				{
      					duty4=hierarhy;
      					//alert(duty4);
      				}
      	//alert(sCalMode);
      	//互算校验
      	if (sCalMode == "Amnt" || sCalMode == "FloatRate" || sCalMode == "InsuYear" || sCalMode == "Mult" || sCalMode == "Prem" || sCalMode == "PayEndYear" || sCalMode == "Count" || sCalMode == "GetLimit" || sCalMode == "GetRate" || sCalMode == "PeakLine")
      	{
			if (sValue!=""){
				if (!isNumeric(sValue)){
					alert("请录入数字！");
					ContPlanGrid.setFocus(i,8);
					return false;
				}
			}
		}
		
		if (sCalMode == "CalRule")
      	{
			if (sValue!=""){
				if (sValue!="1" && sValue!="2" && sValue!="3" && sValue!="0"){
					alert("请录入正确的计算规则！");
					ContPlanGrid.setFocus(i,8);
					return false;
				}
			}
		}
		
		if (sCalMode == "PayIntv")
      	{
			if (sValue!=""){
				if (sValue!="1" && sValue!="3" && sValue!="6" && sValue!="0" && sValue!="12"){
					alert("请录入正确的交费方式！");
					ContPlanGrid.setFocus(i,8);
					return false;
				}
			}
		}
		
		if (sCalMode == "EndDate")
      	{
			if (sValue!=""){
				if (!isDate(sValue)){
					alert("请录入正确的终止日期！");
					ContPlanGrid.setFocus(i,8);
					return false;
				}
			}
		}
		//保费算保额校验
		//alert(sCalMode);
//      	if (sCalMode == "P"){
//			if (sValue==""){
//				alert("请录入保费！");
//				ContPlanGrid.setFocus(i,8);
//				return false;
//			}
//			if (!isNumeric(sValue)){
//				alert("请录入数字！");
//				ContPlanGrid.setFocus(i,8);
//				return false;
//			}
//			if(sValue=0){
//				alert("保费不能为0！");
//				ContPlanGrid.setFocus(i,8);
//				return false;
//			}
//		}
		//保额算保费校验
//      	if (sCalMode == "G"){
//			if (sValue==""){
//				alert("请录入数字！");
//				ContPlanGrid.setFocus(i,8);
//				return false;
//			}
//			if (!isNumeric(sValue)){
//				alert("请录入数字！");
//				ContPlanGrid.setFocus(i,8);
//				return false;
//			}
//			if(sValue==0){
//				alert("保额不能为0！");
//				ContPlanGrid.setFocus(i,8);
//				return false;
//			}
//		}
		//其他因素算保费保额校验
//      	if (sCalMode == "O"){
//			if (sValue==""){
//				alert("请录入要素值！");
//				ContPlanGrid.setFocus(i,8);
//				return false;
//			}
//			if (!isNumeric(sValue)){
//				alert("请录入数字！");
//				ContPlanGrid.setFocus(i,8);
//				return false;
//			}
//			if(sValue==0){
//				alert("要素值不能为0！");
//				ContPlanGrid.setFocus(i,8);
//				return false;
//			}
//		}
		//录入保费保额校验
//      	if (sCalMode == "I"){
//			if (sValue!=""){
//				if (!isNumeric(sValue)){
//					alert("请录入数字！");
//					ContPlanGrid.setFocus(i,8);
//					return false;
//				}
//			}
//		}
	}
	//增加对109的处理
	if(duty1!="")
	{
		if(duty2!="")
		{
			if(duty2>duty1)
			{
				flagerror="1";
			}
		}
		if(duty3!="")
		{
			if(duty3>duty1)
			{
				flagerror="1";
			}
		}
		if(duty4!="")
		{
			if(duty4>duty1)
			{
				flagerror="1";
			}
		}
	}
	if(flagerror=="1")
	{
		alert("109险种的可选责任的档次不能大于基本责任!");
		return false;
	}
	return true;
}

//Click事件，当点击增加图片时触发该函数
function addClick(){
	//下面增加相应的代码
	if (fm.all('initOperate').value == 'INSERT'){
		mOperate="INSERT||MAIN";
		showDiv(operateButton,"false");
		showDiv(inputButton,"true");
		//fm.all('AgentCode').value = '';
		if (fm.all('AgentCode').value !='')
			resetForm();
	}
	else
		alert('在此不能新增！');
}

//Click事件，当点击“修改”图片时触发该函数
function updateClick(){
}

//Click事件，当点击“查询”图片时触发该函数
function queryClick(){
}

//Click事件，当点击“删除”图片时触发该函数
function deleteClick(){
}

//显示div，第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
function showDiv(cDiv,cShow){
	if (cShow=="true"){
		cDiv.style.display="";
	}
	else{
		cDiv.style.display="none";
	}
}

function RiskAddClick(){
	if(fm.all('MainRiskCode').value ==""){
		alert("请输入主险信息！");
		fm.all('MainRiskCode').focus();
		return false;
	}
	var lineCount = 0;
	ContPlanDutyGrid.delBlankLine("ContPlanDutyGrid");
	lineCount = ContPlanDutyGrid.mulLineCount;
	var GrpContNo = fm.all('GrpContNo').value;
	//如果录入过该险种信息，则提示不可再次录入
	for (i=0;i<ContPlanGrid.mulLineCount;i++){
		sValue=ContPlanGrid.getRowColData(i,2)
		if (sValue == fm.all('RiskCode').value){
			alert("已添加过该险种保险计划要素！");
			return false;
		}
	}

	var getWhere = "(";
	for (i=0;i<lineCount;i++){
		if (ContPlanDutyGrid.getChkNo(i)){
			//alert(ContPlanDutyGrid.getRowColData(i,1));
			getWhere = getWhere + "'"+ContPlanDutyGrid.getRowColData(i,1)+"',"
		}
	}
	if (getWhere == "("){
		alert("请选则责任信息");
		return false;
	}
	getWhere = getWhere.substring(0,getWhere.length-1) + ")"
	var MainRiskCode = fm.all('MainRiskCode').value;

	var strSQL = "";

	if (QueryCount == 0){	// 书写SQL语句
		strSQL = "select b.RiskName,a.RiskCode,a.DutyCode,c.DutyName,a.CalFactor,a.FactorName,a.FactorNoti,case a.CalFactorType when '1' then a.CalSql else '' end,'',b.RiskVer,d.GrpPolNo,'"+MainRiskCode+"',a.CalFactorType "
			+ "from LMRiskDutyFactor a, LMRisk b, LMDuty c, LCGrpPol d "
			+ "where a.RiskCode = b.RiskCode and a.DutyCode = c.DutyCode and a.RiskCode = d.RiskCode "
			+ "and a.DutyCode in "+ getWhere + " and a.ChooseFlag in ('1','2') "
			+ "and GrpContNO = '"+GrpContNo+"' and a.RiskCode = '"+fm.all('RiskCode').value+"' order by a.DutyCode,FactorOrder";
		turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
		//判断是否查询成功
		if (!turnPage.strQueryResult) {
			alert("没有该险种责任下要素信息！");
			return false;
		}
		QueryCount = 1;
		//查询成功则拆分字符串，返回二维数组
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		//设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
		turnPage.pageDisplayGrid = ContPlanGrid;
		//保存SQL语句
		turnPage.strQuerySql = strSQL;
		//设置查询起始位置
		turnPage.pageIndex = 0;
		//在查询结果数组中取出符合页面显示大小设置的数组
		//调用MULTILINE对象显示查询结果
		displayMultiline(turnPage.arrDataCacheSet, turnPage.pageDisplayGrid);
	}
	else{
		strSQL = "select b.RiskName,a.RiskCode,a.DutyCode,c.DutyName,a.CalFactor,a.FactorName,a.FactorNoti,case a.CalFactorType when '1' then a.CalSql else '' end,'',b.RiskVer,d.GrpPolNo,'',a.CalFactorType "
			+ "from LMRiskDutyFactor a, LMRisk b, LMDuty c, LCGrpPol d "
			+ "where a.RiskCode = b.RiskCode and a.DutyCode = c.DutyCode and a.RiskCode = d.RiskCode "
			+ "and a.DutyCode in "+ getWhere + " and a.ChooseFlag in ('1','2') "
			+ "and GrpContNO = '"+GrpContNo+"' and a.RiskCode = '"+fm.all('RiskCode').value+"' order by a.DutyCode,FactorOrder";
		turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
		if (!turnPage.strQueryResult) {
			alert("没有该险种责任下要素信息！");
			return false;
		}
		//查询成功则拆分字符串，返回二维数组
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		mulLineCount = ContPlanGrid.mulLineCount;
		//alert(mulLineCount);
		for(i=0; i<turnPage.arrDataCacheSet.length; i++){
			ContPlanGrid.addOne("ContPlanGrid");
			ContPlanGrid.setRowColData(mulLineCount+i,1,turnPage.arrDataCacheSet[i][0]);
			ContPlanGrid.setRowColData(mulLineCount+i,2,turnPage.arrDataCacheSet[i][1]);
			ContPlanGrid.setRowColData(mulLineCount+i,3,turnPage.arrDataCacheSet[i][2]);
			ContPlanGrid.setRowColData(mulLineCount+i,4,turnPage.arrDataCacheSet[i][3]);
			ContPlanGrid.setRowColData(mulLineCount+i,5,turnPage.arrDataCacheSet[i][4]);
			ContPlanGrid.setRowColData(mulLineCount+i,6,turnPage.arrDataCacheSet[i][5]);
			ContPlanGrid.setRowColData(mulLineCount+i,7,turnPage.arrDataCacheSet[i][6]);
			ContPlanGrid.setRowColData(mulLineCount+i,8,turnPage.arrDataCacheSet[i][7]);
			ContPlanGrid.setRowColData(mulLineCount+i,10,turnPage.arrDataCacheSet[i][9]);
			ContPlanGrid.setRowColData(mulLineCount+i,11,turnPage.arrDataCacheSet[i][10]);
			ContPlanGrid.setRowColData(mulLineCount+i,12,MainRiskCode);
			ContPlanGrid.setRowColData(mulLineCount+i,13,turnPage.arrDataCacheSet[i][12]);
		}
	}
	//initContPlanDutyGrid();
}

//返回集体合同录入界面
function returnparent()
{
	
	var tGrpContNo=fm.all("GrpContNo").value;
	var arrResult = easyExecSql("select * from LCGrpCont where Grpcontno= '" + tGrpContNo + "'", 1, 0);                        
            if (arrResult != null) {
            tGrpContNo=arrResult[0][1];
            }
	//if(LoadFlag=="2"||LoadFlag=="4"||LoadFlag=="13")
	//{
	if(LoadFlag=="16")
	{
		top.close();
}else{
  	location.href="ContPolInput.jsp?LoadFlag="+ LoadFlag + "&polNo=" + tGrpContNo;
  }
	  return;
        //}

}

function QueryDutyClick(){
	if(fm.all('RiskCode').value ==""){
		alert("请选择险种！");
		fm.all('RiskCode').focus();
		return false;
	}
	initContPlanDutyGrid();
	//查询该险种下的险种计算要素
	strSQL = "select distinct a.DutyCode,b.DutyName,a.ChoFlag,case a.ChoFlag when 'M' then '必选' when 'B' then '备用' else '可选' end ChoFlagName "
		+ "from LMRiskDuty a, LMDuty b ,LMRiskDutyFactor c "
		+ "where a.DutyCode = b.DutyCode and a.RiskCode = c.RiskCode and a.DutyCode = c.DutyCode "
		+ "and a.RiskCode = '"+fm.all('RiskCode').value+"' order by a.DutyCode";
	//fm.all('ContPlanName').value = strSQL;
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	if (!turnPage.strQueryResult) {
		alert("没有该险种下的责任信息！");
		return false;
	}
	//QueryCount = 1;
	//查询成功则拆分字符串，返回二维数组
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	//tArr = decodeEasyQueryResult(turnPage.strQueryResult);
	//turnPage.arrDataCacheSet = chooseArray(tArr,[0,1,2,3,4,5]);
	//设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
	turnPage.pageDisplayGrid = ContPlanDutyGrid;

	//保存SQL语句
	turnPage.strQuerySql = strSQL;

	//设置查询起始位置
	turnPage.pageIndex = 0;

	//在查询结果数组中取出符合页面显示大小设置的数组
	//arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
	//var tArr = new Array();
	//tArr = chooseArray(arrDataSet,[2,3,4,6,8,18,9,10,11]);
	//调用MULTILINE对象显示查询结果
	displayMultiline(turnPage.arrDataCacheSet, turnPage.pageDisplayGrid);
	var cDutyCode="";
	var tSql="";
	for(var i=0;i<=ContPlanDutyGrid.mulLineCount-1;i++){
	  cDutyCode=ContPlanDutyGrid.getRowColData(i,1);
	  tSql="select choflag from lmriskduty where riskcode='"+fm.all('RiskCode').value+"' and dutycode='"+cDutyCode+"'";
	  var arrResult=easyExecSql(tSql,1,0);
	  //alert("ChoFlag:"+arrResult[0]);
	  if(arrResult[0]=="M"){
	  	 ContPlanDutyGrid.checkBoxSel(i+1);
	  }	
	} 
}

//管理费定义
function GrpRiskFeeInfo(){
	parent.fraInterface.window.location = "./RiskFeeInput.jsp?GrpContNo=" + fm.all('GrpContNo').value+"&LoadFlag="+LoadFlag;
}

//缴费规则定义
function PayRuleInfo(){
	parent.fraInterface.window.location = "./PayRuleInput.jsp?GrpContNo=" + fm.all('GrpContNo').value+"&LoadFlag="+LoadFlag;
}

//数据提交（删除）
function DelContClick(){
	fm.all('mOperate').value = "DELETE||MAIN";
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	QueryCount = 0;	//重新初始化查询次数
	fm.submit(); //提交
}

function RiskQueryClick(){
	strSQL = "select b.RiskName,a.RiskCode,a.DutyCode,c.DutyName,a.CalFactor,a.FactorName,a.FactorNoti,d.CalFactorValue,d.Remark,b.RiskVer,d.GrpPolNo,d.MainRiskCode,d.MainRiskVersion "
		+ "from LMRiskDutyFactor a, LMRisk b, LMDuty c, LCContPlanDutyParam d "
		+ "where a.RiskCode = b.RiskCode and a.DutyCode = c.DutyCode and a.RiskCode = d.RiskCode "
		+ "and a.DutyCode = d.DutyCode and a.CalFactor = d.CalFactor and b.RiskVer = d.RiskVersion "
		+ "and d.ContPlanCode = '00' and GrpContNO = '"+fm.all('GrpContNo').value+"' order by a.RiskCode,d.MainRiskCode,a.DutyCode,a.FactorOrder";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	//判断是否查询成功
	if (!turnPage.strQueryResult) {
		alert("目前没有任何险种特别信息！");
		return false;
	}
	//查询成功则拆分字符串，返回二维数组
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	//tArr = decodeEasyQueryResult(turnPage.strQueryResult);
	//turnPage.arrDataCacheSet = chooseArray(tArr,[0,1,2,3,4,5]);
	//设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
	turnPage.pageDisplayGrid = ContPlanGrid;
	//保存SQL语句
	turnPage.strQuerySql = strSQL;
	//设置查询起始位置
	turnPage.pageIndex = 0;
	//在查询结果数组中取出符合页面显示大小设置的数组
	//调用MULTILINE对象显示查询结果
	displayMultiline(turnPage.arrDataCacheSet, turnPage.pageDisplayGrid);
	QueryCount = 1;
	tSearch = 1;
}