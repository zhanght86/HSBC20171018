//该文件中包含客户端需要处理的函数和事件
var mDebug="0";
var mOperate="";
var showInfo;
var arrDataSet;
var QueryCount = 0;
var mulLineCount = 0;

var turnPage = new turnPageClass();
window.onfocus=myonfocus;
//使得从该窗口弹出的窗口能够聚焦
function myonfocus(){
	if(showInfo!=null){
		try{
			//showInfo.focus();
		}
		catch(ex){
			showInfo=null;
		}
	}
}

//提交，保存按钮对应操作
function submitForm(){
	//if (!beforeSubmit())
	//{
	//	return false;
	//}

  if(!verifyInput2())
  {
  	return false;
  }

	if (!confirm('确认您的操作')){
		return false;
	}

	fm.all('mOperate').value = "INSERT||MAIN";
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
function afterSubmit( FlagStr, content )
{
	showInfo.close();
	//Modify by lujun 20060816  
	/*if(typeof(showInfo)=="object")
	{
		showInfo.close();
		if(typeof(showInfo.parent)=="object")
		{
			showInfo.parent.focus();
			if(typeof(showInfo.parent.parent)=="object")
			{
				showInfo.parent.parent.blur();
			}
		}
	}*/
	
	if( FlagStr == "Fail" ){
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
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
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
	easyQueryClick();
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
	//检查险种管理费明细
	var lineCount = 0;
	RiskFeeGrid.delBlankLine("RiskFeeGrid");
	lineCount = RiskFeeGrid.mulLineCount;
	for (i=0;i<lineCount;i++){
		//alert(RiskFeeGrid.getSelNo(i));
		if (RiskFeeGrid.getSelNo(i))
		{
			return true;
		}
	}
	alert("请选择险种管理费用！");
	return false;
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
	//if (!beforeSubmit())
	//{
		//return false;
	//}
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
	fm.submit(); //提交
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

function easyQueryClick(){
	divRiskFeeParam.style.display='none';	
	initRiskFeeGrid();
	if (fm.all('RiskCode').value ==""){
		alert("请选择险种信息！");
		fm.all('RiskCode').focus();
		return false;
	}
	
	var strSQL = "";
	var rs=fm.all('RiskCode').value;
	if(rs=="139")
	{
		strSQL = "select b.InsuAccNo,d.InsuAccName,b.PayPlanCode,c.PayPlanName,b.FeeCode,b.FeeName, "
		+ "b.PayInsuAccName,a.FeeCalMode,a.FeeCalCode,a.FeeValue,a.CompareValue,b.FeeCalModeType,b.FeePeriod,b.MaxTime,b.DefaultFlag,'已存' "
		+ "from LCGrpFee a,LMRiskFee b,LMDutyPay c,LMRiskToAcc d "
		+ "where a.RiskCode = d.RiskCode and b.PayPlanCode = c.PayPlanCode and b.InsuAccNo = d.InsuAccNo "
		+ "and a.FeeCode = b.FeeCode and a.InsuAccNo = b.InsuAccNo and a.PayPlanCode = b.PayPlanCode "
		+ "and a.GrpPolNO = '"+fm.all('GrpPolNo').value+"' "
		+ "and b.payplancode in (select payplancode from lmdutypayrela where dutycode in "
		+ "(select dutycode from lmriskduty where riskcode = '"+fm.all('RiskCode').value+"')) "
		//+ " and b.feecode='390002'"
		;
}else{
	strSQL = "select b.InsuAccNo,d.InsuAccName,b.PayPlanCode,c.PayPlanName,b.FeeCode,b.FeeName, "
		+ "b.PayInsuAccName,a.FeeCalMode,a.FeeCalCode,a.FeeValue,a.CompareValue,b.FeeCalModeType,b.FeePeriod,b.MaxTime,b.DefaultFlag,'已存' "
		+ "from LCGrpFee a,LMRiskFee b,LMDutyPay c,LMRiskToAcc d "
		+ "where a.RiskCode = d.RiskCode and b.PayPlanCode = c.PayPlanCode and b.InsuAccNo = d.InsuAccNo "
		+ "and a.FeeCode = b.FeeCode and a.InsuAccNo = b.InsuAccNo and a.PayPlanCode = b.PayPlanCode  "
		+ "and a.GrpPolNO = '"+fm.all('GrpPolNo').value+"' "
		+ "and b.payplancode in (select payplancode from lmdutypayrela where dutycode in "
		+ "(select dutycode from lmriskduty where riskcode = '"+fm.all('RiskCode').value+"'))";
	}
	//prompt("已存管理费查询",strSQL);
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	//判断是否查询成功
	if (!turnPage.strQueryResult) {
		//alert("险种管理费明细查询失败！");
		//return false;
	}
	else{
	//	fm.save.disabled=true;
		//查询成功则拆分字符串，返回二维数组
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		//设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
		turnPage.pageDisplayGrid = RiskFeeGrid;
		//保存SQL语句
		turnPage.strQuerySql = strSQL;
		//设置查询起始位置
		turnPage.pageIndex = 0;
		//在查询结果数组中取出符合页面显示大小设置的数组
		//调用MULTILINE对象显示查询结果
		displayMultiline(turnPage.arrDataCacheSet, turnPage.pageDisplayGrid);
	}
  if(rs=="139"){
	strSQL = "select b.InsuAccNo,d.InsuAccName,b.PayPlanCode,c.PayPlanName,b.FeeCode,b.FeeName, "
		+ "b.PayInsuAccName,b.FeeCalMode,b.FeeCalCode,b.FeeValue,case when b.CompareValue is null then 0 else b.CompareValue end,b.FeeCalModeType,b.FeePeriod,b.MaxTime,b.DefaultFlag,'未存' "
		+ "from LCGrpPol a,LMRiskFee b,LMDutyPay c,LMRiskToAcc d "
		+ "where a.RiskCode = d.RiskCode and b.PayPlanCode = c.PayPlanCode and b.InsuAccNo = d.InsuAccNo "
		+ "and a.GrpPolNo = '"+fm.all('GrpPolNo').value+"' and b.payplancode not in "
		+ "(select payplancode from LCGrpFee where GrpPolNo = '"+fm.all('GrpPolNo').value+"')"
		+ "and b.payplancode in (select payplancode from lmdutypayrela where dutycode in "
		+ "(select dutycode from lmriskduty where riskcode = '"+fm.all('RiskCode').value+"')) "
		//+ " and b.feecode='390002'"
		;
	}else{
	strSQL = "select b.InsuAccNo,d.InsuAccName,b.PayPlanCode,c.PayPlanName,b.FeeCode,b.FeeName, "
		+ "b.PayInsuAccName,b.FeeCalMode,b.FeeCalCode,b.FeeValue,case when b.CompareValue is null then 0 else b.CompareValue end,b.FeeCalModeType,b.FeePeriod,b.MaxTime,b.DefaultFlag,'未存' "
		+ "from LCGrpPol a,LMRiskFee b,LMDutyPay c,LMRiskToAcc d "
		+ "where a.RiskCode = d.RiskCode and b.PayPlanCode = c.PayPlanCode and b.InsuAccNo = d.InsuAccNo "
		//+ " and b.FeeCode = '000001' "
		+ "and a.GrpPolNo = '"+fm.all('GrpPolNo').value+"' and b.InsuAccNo not in "
		+ "(select InsuAccNo from LCGrpFee where GrpPolNo = '"+fm.all('GrpPolNo').value+"')"
		+ "and b.payplancode in (select payplancode from lmdutypayrela where dutycode in "
		+ "(select dutycode from lmriskduty where riskcode = '"+fm.all('RiskCode').value+"'))";
}
	//prompt("未存管理费查询",strSQL);
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	if (!turnPage.strQueryResult) {
	}
	else{
	//	fm.save.disabled=false;
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		mulLineCount = RiskFeeGrid.mulLineCount;
		for(i=0; i<turnPage.arrDataCacheSet.length; i++){
			RiskFeeGrid.addOne("RiskFeeGrid");
			RiskFeeGrid.setRowColData(mulLineCount+i,1,turnPage.arrDataCacheSet[i][0]);
			RiskFeeGrid.setRowColData(mulLineCount+i,2,turnPage.arrDataCacheSet[i][1]);
			RiskFeeGrid.setRowColData(mulLineCount+i,3,turnPage.arrDataCacheSet[i][2]);
			RiskFeeGrid.setRowColData(mulLineCount+i,4,turnPage.arrDataCacheSet[i][3]);
			RiskFeeGrid.setRowColData(mulLineCount+i,5,turnPage.arrDataCacheSet[i][4]);
			RiskFeeGrid.setRowColData(mulLineCount+i,6,turnPage.arrDataCacheSet[i][5]);
			RiskFeeGrid.setRowColData(mulLineCount+i,7,turnPage.arrDataCacheSet[i][6]);
			RiskFeeGrid.setRowColData(mulLineCount+i,8,turnPage.arrDataCacheSet[i][7]);
			RiskFeeGrid.setRowColData(mulLineCount+i,9,turnPage.arrDataCacheSet[i][8]);
			RiskFeeGrid.setRowColData(mulLineCount+i,10,turnPage.arrDataCacheSet[i][9]);
			RiskFeeGrid.setRowColData(mulLineCount+i,11,turnPage.arrDataCacheSet[i][10]);
			RiskFeeGrid.setRowColData(mulLineCount+i,12,turnPage.arrDataCacheSet[i][11]);
			RiskFeeGrid.setRowColData(mulLineCount+i,13,turnPage.arrDataCacheSet[i][12]);
			RiskFeeGrid.setRowColData(mulLineCount+i,14,turnPage.arrDataCacheSet[i][13]);
			RiskFeeGrid.setRowColData(mulLineCount+i,15,turnPage.arrDataCacheSet[i][14]);
			RiskFeeGrid.setRowColData(mulLineCount+i,16,turnPage.arrDataCacheSet[i][15]);
		}
	}
}

function returnparent(){
	//Modify By Xuys. 增加续期缴费页面的调用
	if (LoadFlag == '99') //定义为由续期调用
	{
		var tGrpContNo = fm.all('GrpContNo').value;
		var strSQL="select GrpPolNo, RiskCode from LCGrpPol where GrpContNo='" + tGrpContNo + "'";
		var result = easyExecSql(strSQL);		
		var tGrpPolNo = result[0][0];
		var tRiskCode = result[0][1];		
		window.location.href = "../acc/EIRenewalInput.jsp?GrpContNo="+GrpContNo+"&RiskCode="+tRiskCode+"&GrpPolNo=" + tGrpPolNo;
	}
	else
	{	
	//txh add 初始化上一步界面；
		var tGrpContNo = fm.all('GrpContNo').value ;
		var strSQL="select RiskCode from LCGrpPol where GrpContNo='" + tGrpContNo + "'";
	  var tempRiskCode = easyExecSql(strSQL);
		window.location.href = "GrpFeeInput.jsp?ProposalGrpContNo="+GrpContNo+"&LoadFlag="+LoadFlag+ "&RiskCode=" + tempRiskCode;
	}
}

function QueryRiskFeeParam(parm1,parm2){
	if(fm.all(parm1).all('InpRiskFeeGridSel').value == '1'){
		//当前行第1列的值设为：选中
		var cFeeCalMode = fm.all(parm1).all('RiskFeeGrid8').value;	//计算方式
		var cFeeCode = fm.all(parm1).all('RiskFeeGrid5').value;
		var cInsuAccNo = fm.all(parm1).all('RiskFeeGrid1').value;
		var cPayPlanCode = fm.all(parm1).all('RiskFeeGrid3').value;
		/*
		如果交费方式
		07-分档计算
		08-累计分档计算
		需要初始化参数录入界面
		*/
		if(cFeeCalMode == '07' || cFeeCalMode == '08'){
			divRiskFeeParam.style.display='';
			initRiskFeeParamGrid();
			var strSQL = "";
			strSQL = "select FeeMin,FeeMax,FeeRate,FeeID,'已存' "
				+ "from LCGrpFeeParam "
				+ "where 1=1 "
				+ "and FeeCode = '"+cFeeCode+"' and InsuAccNo = '"+cInsuAccNo+"' "
				+ "and PayPlanCode = '"+cPayPlanCode+"' "
				+ "and GrpPolNo = '"+fm.all('GrpPolNo').value+"'";
			turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
			//判断是否查询成功
			if (!turnPage.strQueryResult) {
			}
			else{
				//查询成功则拆分字符串，返回二维数组
				turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
				//设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
				turnPage.pageDisplayGrid = RiskFeeParamGrid;
				//保存SQL语句
				turnPage.strQuerySql = strSQL;
				//设置查询起始位置
				turnPage.pageIndex = 0;
				//在查询结果数组中取出符合页面显示大小设置的数组
				//调用MULTILINE对象显示查询结果
				displayMultiline(turnPage.arrDataCacheSet, turnPage.pageDisplayGrid);
			}

			strSQL = "select FeeMin,FeeMax,FeeRate,FeeID,'未存' "
				+ "from LMRiskFeeParam "
				+ "where 1=1 "
				+ "and FeeCode = '"+cFeeCode+"' and InsuAccNo = '"+cInsuAccNo+"' "
				+ "and PayPlanCode = '"+cPayPlanCode+"' and FeeCalMode = '"+cFeeCalMode+"' "
				+ "and FeeID not in (select FeeID from LCGrpFeeParam where GrpPolNo = '"+fm.all('GrpPolNo').value+"')";
			turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
			if (!turnPage.strQueryResult) {
			}
			else{
				turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
				mulLineCount = RiskFeeParamGrid.mulLineCount;
				for(i=0; i<turnPage.arrDataCacheSet.length; i++){
					RiskFeeParamGrid.addOne("RiskFeeParamGrid");
					RiskFeeParamGrid.setRowColData(mulLineCount+i,1,turnPage.arrDataCacheSet[i][0]);
					RiskFeeParamGrid.setRowColData(mulLineCount+i,2,turnPage.arrDataCacheSet[i][1]);
					RiskFeeParamGrid.setRowColData(mulLineCount+i,3,turnPage.arrDataCacheSet[i][2]);
					RiskFeeParamGrid.setRowColData(mulLineCount+i,4,turnPage.arrDataCacheSet[i][3]);
					RiskFeeParamGrid.setRowColData(mulLineCount+i,5,turnPage.arrDataCacheSet[i][4]);
				}
			}
		}
		else{
			divRiskFeeParam.style.display='none';
			initRiskFeeParamGrid();
		}
	}
}