//该文件中包含客户端需要处理的函数和事件
var mDebug="0";
var mOperate="";
var showInfo;
var arrDataSet;
var QueryCount = 0;
var mulLineCount = 0;
var sqlresourcename = "appgry.OutRiskFeeInputSql";
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

function checkmulLineInfo(){
  for(i=0;i<RiskFeeGrid.mulLineCount;i++)
	   {
	   //计算方式
	    var tCalMode = RiskFeeGrid.getRowColData(i,8);
	    //var tSql = "select count(*) from ldcode where codetype = 'feecalmode' and code = '"+tCalMode+"'";
	    	
	    var sqlid1="OutRiskFeeInputSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(tCalMode);
		
	    var tIsFlag = easyExecSql(mySql1.getString());
	    if(tIsFlag == 0){
	       alert("第"+(i+1)+"行输入的计算方式有误,请重新选择！");
	       return false;
	      }
	   //交费账户
	      if(RiskFeeGrid.getRowColData(i,9)!="00"&&RiskFeeGrid.getRowColData(i,9)!="01")
	      {
	         alert("第"+(i+1)+"行的[缴费帐户]有误,请重新选择！");
	         return false;
	      }
	   //固定值/固定比例
	      var tValue = RiskFeeGrid.getRowColData(i,10);
	      var tMode =RiskFeeGrid.getRowColData(i,8);
	      if(tMode =="02")
	      {
	         if(tValue<0||tValue>1||isNaN(tValue))
	      		{
	        		 alert("第"+(i+1)+"行的[固定值/固定比例]有误,应为>0且<1的数字请重新录入！");
	        		 return false;
	     		}
	      }
	     // else{
	   	//	   if(tValue<0||tValue>100||isNaN(tValue))
	    //		  {
	    //		     alert("第"+(i+1)+"行的[固定值/固定比例]有误,应为>0且<100的数字请重新录入！");
	   	//	      	 return false;
	   	//	 	  }
	         
	    //  }
	   }
	return true;
}

//提交，保存按钮对应操作
function submitForm(){
   // if(RiskFeeGrid.checkValue2(RiskFeeGrid.name,RiskFeeGrid)== false)return false;
    if(checkmulLineInfo()==false)return false;
	//if (!beforeSubmit()){
	//	return false;
	//}

	if (!confirm('确认您的操作')){
		return false;
	}

	document.all('mOperate').value = "INSERT||MAIN";
	var i = 0;
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	document.getElementById("fm").submit(); //提交
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
		if (RiskFeeGrid.getSelNo(i)){
			return true;
		}
	}
	alert("请选择险种管理费用！");
	return false;
}

//Click事件，当点击增加图片时触发该函数
function addClick(){
	//下面增加相应的代码
	if (document.all('initOperate').value == 'INSERT'){
		mOperate="INSERT||MAIN";
		showDiv(operateButton,"false");
		showDiv(inputButton,"true");
		//document.all('AgentCode').value = '';
		if (document.all('AgentCode').value !='')
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
	if (!beforeSubmit()){
		return false;
	}
	document.all('mOperate').value = "DELETE||MAIN";
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();s
	document.getElementById("fm").submit(); //提交
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
	// var tSql="select risktype6 from lmriskapp where riskcode='"+fm.RiskCode.value+"'";
	   var sqlid2="OutRiskFeeInputSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(fm.RiskCode.value);
	var arrResult= easyExecSql(mySql2.getString(), 1, 0);
	if(arrResult==null){
	    	alert("未查到险种类型信息");
	    }
	divRiskFeeParam.style.display='none';
	if(arrResult=="4")//188,198
	{
		initWRiskFeeGrid();
  }else{	
	initRiskFeeGrid();
  }
	if (document.all('RiskCode').value ==""){
		alert("请选择险种信息！");
		document.all('RiskCode').focus();
		return false;
	}
	
	var strSQL = "";
	var rs=document.all('RiskCode').value;
	if(arrResult=="1")//139,151
	{/*
		//alert(rs);
		strSQL = "select b.InsuAccNo,d.InsuAccName,b.PayPlanCode,c.PayPlanName,b.FeeCode,b.FeeName, "
		+ "b.PayInsuAccName,a.FeeCalMode,a.FeeCalCode,a.FeeValue,a.CompareValue,b.FeeCalModeType,b.FeePeriod,b.MaxTime,b.DefaultFlag,'已存' "
		+ "from LCGrpFee a,LMRiskFee b,LMDutyPay c,LMRiskToAcc d "
		+ "where a.RiskCode = d.RiskCode and b.PayPlanCode = c.PayPlanCode and b.InsuAccNo = d.InsuAccNo "
		+ "and a.FeeCode = b.FeeCode and a.InsuAccNo = b.InsuAccNo and a.PayPlanCode = b.PayPlanCode "
		+ "and a.GrpPolNO = '"+document.all('GrpPolNo').value+"' "
		+ "and b.payplancode in (select payplancode from lmdutypayrela where dutycode in "
		+ "(select dutycode from lmriskduty where riskcode = '"+document.all('RiskCode').value+"')) "
		//tongmeng 2008-09-09 取消下面的限制
		;
		*/
		var sqlid3="OutRiskFeeInputSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName(sqlresourcename);
		mySql3.setSqlId(sqlid3);
		mySql3.addSubPara(document.all('GrpPolNo').value);
		mySql3.addSubPara(document.all('RiskCode').value);
		
		strSQL = mySql3.getString();
		
		//and b.feecode like '%%002'";
}else{
/*
	strSQL = "select b.InsuAccNo,d.InsuAccName,b.PayPlanCode,c.PayPlanName,b.FeeCode,b.FeeName, "
		+ "b.PayInsuAccName,a.FeeCalMode,a.FeeCalCode,a.FeeValue,a.CompareValue,b.FeeCalModeType,b.FeePeriod,b.MaxTime,b.DefaultFlag,'已存' "
		+ "from LCGrpFee a,LMRiskFee b,LMDutyPay c,LMRiskToAcc d "
		+ "where (b.feetakeplace<>'04' and b.feetakeplace<>'03') and a.RiskCode = d.RiskCode and b.PayPlanCode = c.PayPlanCode and b.InsuAccNo = d.InsuAccNo "
		+ "and a.FeeCode = b.FeeCode and a.InsuAccNo = b.InsuAccNo and a.PayPlanCode = b.PayPlanCode "
		+ "and a.GrpPolNO = '"+document.all('GrpPolNo').value+"' "
		+ "and b.payplancode in (select payplancode from lmdutypayrela where dutycode in "
		+ "(select dutycode from lmriskduty where riskcode = '"+document.all('RiskCode').value+"'))";
	*/	
		var sqlid4="OutRiskFeeInputSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName(sqlresourcename);
		mySql4.setSqlId(sqlid4);
		mySql4.addSubPara(document.all('GrpPolNo').value);
		mySql4.addSubPara(document.all('RiskCode').value);
		
		strSQL = mySql4.getString();
	}
	//alert(strSQL);
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	//判断是否查询成功
	if (!turnPage.strQueryResult) {
		//alert("险种管理费明细查询失败！");
		//return false;
	}
	else{
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
  if(arrResult=="1"){//139,151
  /*
	strSQL = "select b.InsuAccNo,d.InsuAccName,b.PayPlanCode,c.PayPlanName,b.FeeCode,b.FeeName, "
		+ "b.PayInsuAccName,b.FeeCalMode,b.FeeCalCode,case when b.FeeValue is null then 0 else b.FeeValue end,case when b.CompareValue is null then 0 else b.CompareValue end,b.FeeCalModeType,b.FeePeriod,b.MaxTime,b.DefaultFlag,'未存' "
		+ "from LCGrpPol a,LMRiskFee b,LMDutyPay c,LMRiskToAcc d "
		+ "where a.RiskCode = d.RiskCode and b.PayPlanCode = c.PayPlanCode and b.InsuAccNo = d.InsuAccNo "
		+ "and a.GrpPolNo = '"+document.all('GrpPolNo').value+"' and b.payplancode not in "
		+ "(select payplancode from LCGrpFee where GrpPolNo = '"+document.all('GrpPolNo').value+"')"
		+ "and b.payplancode in (select payplancode from lmdutypayrela where dutycode in "
		+ "(select dutycode from lmriskduty where riskcode = '"+document.all('RiskCode').value+"')) "
		//tongmeng 2008-09-09 取消下面的限制
		//and b.feecode like '%%002'"
		
		;
		*/
		var sqlid5="OutRiskFeeInputSql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName(sqlresourcename);
		mySql5.setSqlId(sqlid5);
		mySql5.addSubPara(document.all('GrpPolNo').value);
		mySql5.addSubPara(document.all('GrpPolNo').value);
		mySql5.addSubPara(document.all('RiskCode').value);
		
		strSQL = mySql5.getString();
		
		
	}else{
	/*
	strSQL = "select b.InsuAccNo,d.InsuAccName,b.PayPlanCode,c.PayPlanName,b.FeeCode,b.FeeName, "
		+ "b.PayInsuAccName,b.FeeCalMode,b.FeeCalCode,case when b.FeeValue is null then 0 else b.FeeValue end,case when b.CompareValue is null then 0 else b.CompareValue end,b.FeeCalModeType,b.FeePeriod,b.MaxTime,b.DefaultFlag,'未存' "
		+ "from LCGrpPol a,LMRiskFee b,LMDutyPay c,LMRiskToAcc d "
		+ "where (b.feetakeplace<>'04' and b.feetakeplace<>'03') and a.RiskCode = d.RiskCode and b.PayPlanCode = c.PayPlanCode and b.InsuAccNo = d.InsuAccNo "
		+ "and a.GrpPolNo = '"+document.all('GrpPolNo').value+"' and b.InsuAccNo not in "
		+ "(select InsuAccNo from LCGrpFee where GrpPolNo = '"+document.all('GrpPolNo').value+"')"
		+ "and b.payplancode in (select payplancode from lmdutypayrela where dutycode in "
		+ "(select dutycode from lmriskduty where riskcode = '"+document.all('RiskCode').value+"'))";
		*/
		var sqlid6="OutRiskFeeInputSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName(sqlresourcename);
		mySql6.setSqlId(sqlid6);
		mySql6.addSubPara(document.all('GrpPolNo').value);
		mySql6.addSubPara(document.all('GrpPolNo').value);
		mySql6.addSubPara(document.all('RiskCode').value);
		
		strSQL = mySql6.getString();
		
}


	//alert(strSQL);
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	if (!turnPage.strQueryResult) {
	}
	else{
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
	window.location.href = "GrpFeeInput.jsp?ProposalGrpContNo="+GrpContNo+"&LoadFlag="+LoadFlag;
}

function QueryRiskFeeParam(parm1,parm2){
	if(document.all(parm1).all('InpRiskFeeGridSel').value == '1'){
		//当前行第1列的值设为：选中
		var cFeeCalMode = document.all(parm1).all('RiskFeeGrid8').value;	//计算方式
		var cFeeCode = document.all(parm1).all('RiskFeeGrid5').value;
		var cInsuAccNo = document.all(parm1).all('RiskFeeGrid1').value;
		var cPayPlanCode = document.all(parm1).all('RiskFeeGrid3').value;
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
			/*
			strSQL = "select FeeMin,FeeMax,FeeRate,FeeID,'已存' "
				+ "from LCGrpFeeParam "
				+ "where 1=1 "
				+ "and FeeCode = '"+cFeeCode+"' and InsuAccNo = '"+cInsuAccNo+"' "
				+ "and PayPlanCode = '"+cPayPlanCode+"' "
				+ "and GrpPolNo = '"+document.all('GrpPolNo').value+"'";
			*/	
				var sqlid7="OutRiskFeeInputSql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName(sqlresourcename);
		mySql7.setSqlId(sqlid7);
		mySql7.addSubPara(cFeeCode);
		mySql7.addSubPara(cInsuAccNo);
		mySql7.addSubPara(cPayPlanCode);
		mySql7.addSubPara(document.all('GrpPolNo').value);
				
			turnPage.strQueryResult  = easyQueryVer3(mySql7.getString(), 1, 0, 1);
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
/*
			strSQL = "select FeeMin,FeeMax,FeeRate,FeeID,'未存' "
				+ "from LMRiskFeeParam "
				+ "where 1=1 "
				+ "and FeeCode = '"+cFeeCode+"' and InsuAccNo = '"+cInsuAccNo+"' "
				+ "and PayPlanCode = '"+cPayPlanCode+"' and FeeCalMode = '"+cFeeCalMode+"' "
				+ "and InsuAccNo not in (select InsuAccNo from LCGrpFeeParam where GrpPolNo = '"+document.all('GrpPolNo').value+"')";
	*/		
		var sqlid8="OutRiskFeeInputSql8";
		var mySql8=new SqlClass();
		mySql8.setResourceName(sqlresourcename);
		mySql8.setSqlId(sqlid8);
		mySql8.addSubPara(cFeeCode);
		mySql8.addSubPara(cInsuAccNo);
		mySql8.addSubPara(cPayPlanCode);
		mySql8.addSubPara(document.all('GrpPolNo').value);
				
			
			
			turnPage.strQueryResult  = easyQueryVer3(mySql8.getString(), 1, 0, 1);
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