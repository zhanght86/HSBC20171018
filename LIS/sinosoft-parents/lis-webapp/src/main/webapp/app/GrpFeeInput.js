//该文件中包含客户端需要处理的函数和事件
var mDebug="0";
var mOperate="";
var showInfo;
var arrDataSet;
var QueryCount = 0;
var mulLineCount = 0;
var tSearch = 0;
var turnPage = new turnPageClass();
var queryPubAccFlag = false;
window.onfocus=myonfocus;
var mSwitch = parent.VD.gVSwitch;
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
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
	if (tSearch == 0){
		alert("请先查询要险种信息！");
		return false;
	}

	document.all('mOperate').value = "UPDATE||MAIN";
	if (!beforeSubmit()){
		return false;
	}
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	QueryCount = 0;	//重新初始化查询次数
	fm.action = "GrpFeeSave.jsp";
	document.getElementById("fm").submit(); //提交
}

function afterCodeSelect( cCodeName, Field ){
	//判定双击操作执行的是什么查询
	if (cCodeName=="GrpRisk"){
	    initContPlanGrid();
	    initContPlanDutyGrid();
		var tRiskFlag = document.all('RiskFlag').value;
		//由于附加险不带出主险录入框，因此判定当主附险为S的时候隐藏
		if (tRiskFlag!="S"){
			divmainriskname.style.display = 'none';
			divmainrisk.style.display = 'none';
			document.all('MainRiskCode').value = document.all('RiskCode').value;
		}
	    else
	    {
			
			var sqlid65="ContQuerySQL65";
		    var mySql65=new SqlClass();
		    mySql65.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
		    mySql65.setSqlId(sqlid65);//指定使用的Sql的id
		    mySql65.addSubPara(fm.ProposalGrpContNo.value);//指定传入的参数
		    mySql65.addSubPara(fm.RiskCode.value);//指定传入的参数
	        var strSQL=mySql65.getString();	
			
//			var strSQL="select * from lcgrppol where grpcontno='"+fm.ProposalGrpContNo.value+"' and riskcode in"
//			+"(select riskcode from lmriskrela where relariskcode = '"+fm.RiskCode.value+"')";
			turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
			if(!turnPage.strQueryResult)
			{
				alert("该险种主险信息未录入,故不能进行该险种信息录入");
				document.all('MainRiskCode').value = "";
				return false;
			}
		    else
			{
			    divmainriskname.style.display = '';
			    divmainrisk.style.display = '';
			    document.all('MainRiskCode').value = "";
		    }
		}
	}
}
function CheckRisk()
  {
  	
			var sqlid66="ContQuerySQL66";
		    var mySql66=new SqlClass();
		    mySql66.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
		    mySql66.setSqlId(sqlid66);//指定使用的Sql的id
		    mySql66.addSubPara(fm.RiskCode.value);//指定传入的参数
	        var strSQL=mySql66.getString();	
	
  	//var strSQL="select subriskflag from lmriskapp where riskcode='"+document.all('RiskCode').value+"'";
  	var arrResult = easyExecSql(strSQL,1,0);
  	if(!arrResult)
  	  {
  	  	return false;
  	  }
  	var tRiskFlag=arrResult[0][0];
  	if (tRiskFlag!="S")
  	{
			divmainriskname.style.display = 'none';
			divmainrisk.style.display = 'none';
			document.all('MainRiskCode').value = document.all('RiskCode').value;
		}
	 else{
	 	
			var sqlid67="ContQuerySQL67";
		    var mySql67=new SqlClass();
		    mySql67.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
		    mySql67.setSqlId(sqlid67);//指定使用的Sql的id
		    mySql67.addSubPara(fm.ProposalGrpContNo.value);//指定传入的参数
		    mySql67.addSubPara(fm.RiskCode.value);//指定传入的参数
	        var strSQL=mySql67.getString();	
		
//			var strSQL="select * from lcgrppol where grpcontno='"+fm.ProposalGrpContNo.value+"' and riskcode in"
//			+"(select riskcode from lmriskrela where relariskcode = '"+fm.RiskCode.value+"')";
			turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
			if(!turnPage.strQueryResult)
			{
				alert("该险种主险信息未录入,故不能进行该险种信息录入");
				document.all('MainRiskCode').value = "";
				return false;
			}
		else
			{
			divmainriskname.style.display = '';
			divmainrisk.style.display = '';
			document.all('MainRiskCode').value = "";
		  }
		}
  }

//提交，保存按钮对应操作
function submitForm(){
	//alert(document.all('mOperate').value);
	if (!beforeSubmit()){
		return false;
	}

	if (!confirm('默认值下的全部险种要素信息是否已录入完毕，您是否要确认操作？')){
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
    fm.action = "GrpFeeSave.jsp";
	document.getElementById("fm").submit(); //提交	
}

//提交后操作,服务器数据返回后执行的操作
function Submit( FlagStr, content ){
	showInfo.close();
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
	//alert(document.all('mOperate').value);
	document.all('mOperate').value = "";
	initForm();
	//alert(document.all('mOperate').value);
	QueryCount = 0;	//重新初始化查询次数
	tSearch = 0;
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
	var sInsuYearFlag = "";
	var sInsuYear = "";
	var sPremToAmnt = "";
	var sCalRule = "";
	var sFloatRate = "";
	//添加要素类型所需校验信息
	for(var i=0;i<lineCount;i++){
      	sValue = ContPlanGrid.getRowColData(i,5);
      	if (sValue == "InsuYearFlag"){
      		sInsuYearFlag = ContPlanGrid.getRowColData(i,8);
      	}
      	if (sValue == "InsuYear"){
      		if (sInsuYearFlag != ""){
      			sInsuYear = ContPlanGrid.getRowColData(i,8);
      			if (!isNumeric(sInsuYear)){
      				alert("保险期间录入有误，应该填写数字！");
      				ContPlanGrid.setFocus(i,8);
      				return false;
      			}
      			sInsuYearFlag = "";
      		}
      	}
      	if (sValue == "CalRule"){
      		sCalRule = ContPlanGrid.getRowColData(i,8);      		
      		if (sCalRule != ""){
          		for(var j=0;j<lineCount;j++){
          		    tValue = ContPlanGrid.getRowColData(j,5);
          		    if (tValue == "FloatRate"){          		        		
        	  			tFloatRate = ContPlanGrid.getRowColData(j,8);
        	      		if (sCalRule == "0"||sCalRule == "3"){
        	      			if (tFloatRate != "" && tFloatRate != null && tFloatRate != "null"){
        	      				alert("当前计算规则，不应该录入费率/折扣信息！");
        	      				ContPlanGrid.setFocus(j + 1,8);
        	      				return false;
        	      			}
        	      		}
        	 			else{
        	      			if (!isNumeric(tFloatRate)){
        	      				alert("请录入费率/折扣信息！");
        	      				ContPlanGrid.setFocus(j + 1,8);
        	      				return false;
        	      			}
        	      			if (parseFloat(tFloatRate)>1 || parseFloat(tFloatRate) <= 0){
        	      				alert("费率/折扣信息录入有误，请录入0－1的数字！");
        	      				ContPlanGrid.setFocus(j + 1,8);
        	      				return false;
        	      			}
        	  			}
        	  			sCalRule = "";
        	  			break;
    	  		    }
          	    }
          	}
      	}
	}
	
	
	
	
	
	
	
		
	
	//如果在LMDuty表中定义了该责任的险种的保额、保费需要通过程序计算而不是直接录入，则校验不让同时录入该责任的保费和保额
	//遍历整个ContPlanGrid
	//记录当前检验的责任的编码
	var curDutyCode=ContPlanGrid.getRowColData(0,3);
	
	var curDutyName=ContPlanGrid.getRowColData(0,4);
	
	//记录当前检验的责任是否录入了保费
	var inputedPrem=false;
	//记录当前校验的责任是否录入了保额
	var inputedAmnt=false;
	//记录是否在责任中定义了保额、保额
	var canDefinePremAmnt=false;

	
	for(var i=0;i<lineCount;i++){

	//查询其LMDuty表
	
			var sqlid68="ContQuerySQL68";
		    var mySql68=new SqlClass();
		    mySql68.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
		    mySql68.setSqlId(sqlid68);//指定使用的Sql的id
		    mySql68.addSubPara(ContPlanGrid.getRowColData(i,3));//指定传入的参数
	        var strQuerySql=mySql68.getString();	
	
	//var strQuerySql=" select calmode from lmduty where dutycode='"+ContPlanGrid.getRowColData(i,3)+"'";
	var resultSet=easyExecSql(strQuerySql,1,0,1);
	
	//看其是否能同时录入保额保费，如果能同时录入，循环往前直到下一个责任
	if(resultSet=="I"){
	while(ContPlanGrid.getRowColData(++i,3)==curDutyCode){
	}
	//上层for循环中也对i加1操作，此初减去1使得每行都被遍历到
	i--;
	}
	
	//检验是否录入了保额、保费
	if(trim(ContPlanGrid.getRowColData(i,5))=="Prem"||trim(ContPlanGrid.getRowColData(i,6))=="保费"){
		canDefinePremAmnt=true;
		if(ContPlanGrid.getRowColData(i,8)){
		inputedPrem=true;
	}
 }
	else if(trim(ContPlanGrid.getRowColData(i,5))=="Amnt"||trim(ContPlanGrid.getRowColData(i,6))=="保额"){
		canDefinePremAmnt=true;
		if(ContPlanGrid.getRowColData(i,8)){
		inputedAmnt=true;
	}
 }
	
	if((i<lineCount-1&&(ContPlanGrid.getRowColData(i,3)!=ContPlanGrid.getRowColData(i+1,3)))||(i==lineCount-1)){
	//执行校验
	if(canDefinePremAmnt&&((inputedPrem&&inputedAmnt)||(!inputedPrem&&!inputedAmnt))){
	alert(curDutyName+"责任项中的保费保额必须录入且只能录入一项！");
	return false;
	}
	inputedAmnt=false;
	inputedPrem=false;
	canDefinePremAmnt=false;
	curDutyCode=ContPlanGrid.getRowColData(i,3);
	}
}
	
	
	
	
	
	
	
	
		

	
	
	
	return true;
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
	if(document.all('MainRiskCode').value =="")
	{
		alert("请输入主险信息！");
		document.all('MainRiskCode').focus();
		return false;
	}
	var lineCount = 0;
	ContPlanDutyGrid.delBlankLine("ContPlanDutyGrid");
	lineCount = ContPlanDutyGrid.mulLineCount;
	var GrpContNo = document.all('GrpContNo').value;
	//如果录入过该险种信息，则提示不可再次录入
	for (i=0;i<ContPlanGrid.mulLineCount;i++)
	{
		sValue=ContPlanGrid.getRowColData(i,2)
		if (sValue == document.all('RiskCode').value)
		{
			alert("已添加过该险种保险计划要素！");
			return false;
		}
	}

	var getWhere = "(";
	for (i=0;i<lineCount;i++)
	{
		if (ContPlanDutyGrid.getChkNo(i))
		{
			//alert(ContPlanDutyGrid.getRowColData(i,1));
			getWhere = getWhere + "'"+ContPlanDutyGrid.getRowColData(i,1)+"',"
		}
	}
	if (getWhere == "(")
	{
		alert("请选则责任信息");
		return false;
	}
	getWhere = getWhere.substring(0,getWhere.length-1) + ")"
	var MainRiskCode = document.all('MainRiskCode').value;

	var strSQL = "";
	
				var sqlid69="ContQuerySQL69";
		    var mySql69=new SqlClass();
		    mySql69.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
		    mySql69.setSqlId(sqlid69);//指定使用的Sql的id
		    mySql69.addSubPara(GrpContNo);//指定传入的参数
		    mySql69.addSubPara(fm.RiskCode.value);//指定传入的参数
	        strSQL=mySql69.getString();	
	
//	strSQL = "select payintv from lcgrppol where grpcontno='" + GrpContNo 
//	       + "' and riskcode='" + fm.RiskCode.value + "'";
	var tPayIntv = easyExecSql(strSQL);
	if (tPayIntv == null || tPayIntv.length == 0)
	{
	    alert("集体险种查询失败！");
	    return false;
	}
	
	strSQL = "";
	if (QueryCount == 0)
	{	// 书写SQL语句
	
			var sqlid70="ContQuerySQL70";
		    var mySql70=new SqlClass();
		    mySql70.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
		    mySql70.setSqlId(sqlid70);//指定使用的Sql的id
		    mySql70.addSubPara(MainRiskCode);//指定传入的参数
		    mySql70.addSubPara(getWhere);//指定传入的参数
		    mySql70.addSubPara(GrpContNo);//指定传入的参数
		    mySql70.addSubPara(fm.RiskCode.value);//指定传入的参数
	        strSQL=mySql70.getString();	
	
//		strSQL = "select b.RiskName,a.RiskCode,a.DutyCode,c.DutyName,a.CalFactor,a.FactorName,a.FactorNoti,"
//		    + "case a.CalFactorType when '1' then a.CalSql else '' end,'',b.RiskVer,d.GrpPolNo,'"+MainRiskCode+"',a.CalFactorType "
//			+ "from LMRiskDutyFactor a, LMRisk b, LMDuty c, LCGrpPol d "
//			+ "where a.RiskCode = b.RiskCode and a.DutyCode = c.DutyCode and a.RiskCode = d.RiskCode "
//			+ "and a.DutyCode in "+ getWhere + " and a.ChooseFlag in ('1','2') "
//			+ "and GrpContNO = '"+GrpContNo+"' and a.RiskCode = '"+document.all('RiskCode').value+"' order by a.DutyCode,FactorOrder";
		turnPage.queryModal(strSQL,ContPlanGrid);
		//判断是否查询成功
		if (ContPlanGrid.mulLineCount <= 0) 
		{
			alert("没有该险种责任下要素信息！");
			return false;
		}
	}
	else
	{
		
		    var sqlid71="ContQuerySQL71";
		    var mySql71=new SqlClass();
		    mySql71.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
		    mySql71.setSqlId(sqlid71);//指定使用的Sql的id
		    mySql71.addSubPara(getWhere);//指定传入的参数
		    mySql71.addSubPara(GrpContNo);//指定传入的参数
		    mySql71.addSubPara(fm.RiskCode.value);//指定传入的参数
	        strSQL=mySql71.getString();	
		
//		strSQL = "select b.RiskName,a.RiskCode,a.DutyCode,c.DutyName,a.CalFactor,a.FactorName,a.FactorNoti,case a.CalFactorType when '1' then a.CalSql else '' end,'',b.RiskVer,d.GrpPolNo,'',a.CalFactorType "
//			+ "from LMRiskDutyFactor a, LMRisk b, LMDuty c, LCGrpPol d "
//			+ "where a.RiskCode = b.RiskCode and a.DutyCode = c.DutyCode and a.RiskCode = d.RiskCode "
//			+ "and a.DutyCode in "+ getWhere + " and a.ChooseFlag in ('1','2') "
//			+ "and GrpContNO = '"+GrpContNo+"' and a.RiskCode = '"+document.all('RiskCode').value+"' order by a.DutyCode,FactorOrder";
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
	
	
	//如果是附加险则取主险保险期间要素值，如主险险种信息没有录入，取数据库中默认值	
	var tInsuYear = "";
	
    var tInsuYearFlag = "";

	if (document.all('RiskFlag').value == "S"){
	
			 var sqlid72="ContQuerySQL72";
		    var mySql72=new SqlClass();
		    mySql72.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
		    mySql72.setSqlId(sqlid72);//指定使用的Sql的id
		    mySql72.addSubPara(GrpContNo);//指定传入的参数
		    mySql72.addSubPara(fm.MainRiskCode.value);//指定传入的参数
	         var tStrSql=mySql72.getString();	
	
//        var tStrSql = "select calfactorvalue from lccontplandutyparam where grpcontno='"
//	
//         + GrpContNo + "' and riskcode='" 
//	
//         + document.all('MainRiskCode').value + "' and calfactor='InsuYear'";
	
        tResult = easyExecSql(tStrSql, 1, 0);
	
        if (tResult != null && tResult.length > 0){
	
            tInsuYear = tResult[0][0];
	
        }
		
        	var sqlid73="ContQuerySQL73";
		    var mySql73=new SqlClass();
		    mySql73.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
		    mySql73.setSqlId(sqlid73);//指定使用的Sql的id
		    mySql73.addSubPara(GrpContNo);//指定传入的参数
		    mySql73.addSubPara(fm.MainRiskCode.value);//指定传入的参数
	         var tStrSql=mySql73.getString();	
	
//        var tStrSql = "select calfactorvalue from lccontplandutyparam where grpcontno='"
//	
//         + GrpContNo + "' and riskcode='" 
//	
//         + document.all('MainRiskCode').value + "' and calfactor='InsuYearFlag'";
	
        tResult = easyExecSql(tStrSql, 1, 0);
	
        if (tResult != null && tResult.length > 0){
	
            tInsuYearFlag = tResult[0][0];
	
        }
	
    }
	
	//循环责任要素项，对保险期间、缴费方式赋值
	lineCount = ContPlanGrid.mulLineCount;
    
	var sValue;
   
	for(var i=0;i<lineCount;i++){
    
      	sValue = ContPlanGrid.getRowColData(i,5);
  
      	if (tInsuYear != null && tInsuYear != "" && tInsuYear != "null")
      	{
          	if (sValue == "InsuYear"){
    
          		ContPlanGrid.setRowColData(i,8,tInsuYear);
    
          	}

        }  
        if (tInsuYearFlag != null && tInsuYearFlag != "" && tInsuYearFlag != "null")
      	{  
          	if (sValue == "InsuYearFlag"){
    
          		ContPlanGrid.setRowColData(i,8,tInsuYearFlag);
    
          	}
 
        }
      	
      	if (sValue == "PayIntv"){
 
      		ContPlanGrid.setRowColData(i,8,tPayIntv[0][0]);
   
     	}
     
    }
	
	//initContPlanDutyGrid();
	//initContPlanDutyGrid();
}

//返回集体合同录入界面
function returnparent()
{
	
	var tGrpContNo=document.all("GrpContNo").value;
//	var arrResult = easyExecSql("select * from LCGrpCont where Grpcontno= '" + tGrpContNo + "'", 1, 0);                        
//    if (arrResult != null) {
//        tGrpContNo=arrResult[0][1];
//    }
    //alert("A" + LoadFlag + "A");
    if (LoadFlag=="c")
    {
    	parent.close();
    }
    else
  	{
    	//if(LoadFlag=="2"||LoadFlag=="4"||LoadFlag=="13")
    	//{
  	    location.href="ContPolInput.jsp?LoadFlag="+ LoadFlag + "&polNo=" + tGrpContNo+"&scantype="+scantype;
	    return;
        //}
    }

}

function QueryDutyClick(){
	if(document.all('RiskCode').value ==""){
		alert("请选择险种！");
		document.all('RiskCode').focus();
		return false;
	}
	initContPlanDutyGrid();
	//查询该险种下的险种计算要素
	
	        var sqlid74="ContQuerySQL74";
		    var mySql74=new SqlClass();
		    mySql74.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
		    mySql74.setSqlId(sqlid74);//指定使用的Sql的id
		    mySql74.addSubPara(fm.RiskCode.value);//指定传入的参数
	       strSQL=mySql74.getString();	
	
//	strSQL = "select distinct a.DutyCode,b.DutyName,a.ChoFlag,case a.ChoFlag when 'M' then '必选' when 'B' then '备用' else '可选' end ChoFlagName "
//		+ "from LMRiskDuty a, LMDuty b ,LMRiskDutyFactor c "
//		+ "where a.DutyCode = b.DutyCode and a.RiskCode = c.RiskCode and a.DutyCode = c.DutyCode "
//		+ "and a.RiskCode = '"+document.all('RiskCode').value+"' order by a.DutyCode";
	//document.all('ContPlanName').value = strSQL;
	turnPage.queryModal(strSQL,ContPlanDutyGrid);
	if (ContPlanDutyGrid.mulLineCount <= 0) 
	{
		alert("没有该险种下的责任信息！");
		return false;
	}

	var cDutyCode = "";
	var tSql = "";
	for(var i = 0;i <= ContPlanDutyGrid.mulLineCount-1; i++)
	{
	    cDutyCode=ContPlanDutyGrid.getRowColData(i,1);
		
			var sqlid75="ContQuerySQL75";
		    var mySql75=new SqlClass();
		    mySql75.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
		    mySql75.setSqlId(sqlid75);//指定使用的Sql的id
		    mySql75.addSubPara(fm.RiskCode.value);//指定传入的参数
		    mySql75.addSubPara(cDutyCode);//指定传入的参数
	        tSql=mySql75.getString();	
		
	   // tSql="select choflag from lmriskduty where riskcode='"+document.all('RiskCode').value+"' and dutycode='"+cDutyCode+"'";
	    var arrResult=easyExecSql(tSql,1,0);
	    //alert("ChoFlag:"+arrResult[0]);
	    if(arrResult[0]=="M")
	    {
	  	    ContPlanDutyGrid.checkBoxSel(i+1);
	    }	
	} 
}

//管理费定义
function GrpRiskFeeInfo(){
	parent.fraInterface.window.location = "./RiskFeeInput.jsp?GrpContNo=" + document.all('GrpContNo').value+"&LoadFlag="+LoadFlag;
}

//缴费规则定义
function PayRuleInfo(){
	parent.fraInterface.window.location = "./PayRuleInput.jsp?GrpContNo=" + document.all('GrpContNo').value+"&LoadFlag="+LoadFlag;
}

//数据提交（删除）
function DelContClick(){
    if (confirm("确定要删除此险种信息吗？") == true)
    {
    	document.all('mOperate').value = "DELETE||MAIN";
    	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    	QueryCount = 0;	//重新初始化查询次数
    	fm.action = "GrpFeeSave.jsp";
    	document.getElementById("fm").submit(); //提交
    }
}

function RiskQueryClick(){
    if(document.all('RiskCode').value =="" || document.all('RiskCode').value=="null"){
		alert("请选择险种！");
		document.all('RiskCode').focus();
		return false;
	}
	var resultFlag = 0;
	
			var sqlid76="ContQuerySQL76";
		    var mySql76=new SqlClass();
		    mySql76.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
		    mySql76.setSqlId(sqlid76);//指定使用的Sql的id
		    mySql76.addSubPara(fm.GrpContNo.value);//指定传入的参数
		    mySql76.addSubPara(fm.RiskCode.value);//指定传入的参数
	        strSQL=mySql76.getString();	
	
//	strSQL = "select b.RiskName,a.RiskCode,a.DutyCode,c.DutyName,a.CalFactor,a.FactorName,a.FactorNoti,d.CalFactorValue,d.Remark,b.RiskVer,d.GrpPolNo,d.MainRiskCode,d.MainRiskVersion "
//		+ "from LMRiskDutyFactor a, LMRisk b, LMDuty c, LCContPlanDutyParam d "
//		+ "where a.RiskCode = b.RiskCode and a.DutyCode = c.DutyCode and a.RiskCode = d.RiskCode "
//		+ "and a.DutyCode = d.DutyCode and a.CalFactor = d.CalFactor and b.RiskVer = d.RiskVersion "
//		+ "and d.ContPlanCode = '00' and GrpContNO = '"+document.all('GrpContNo').value + "' "
//		+ "and d.RiskCode='" + document.all('RiskCode').value
//		+ "' order by a.RiskCode,d.MainRiskCode,a.DutyCode,a.FactorOrder";
	turnPage.pageLineNum = 20;
	turnPage.queryModal(strSQL,ContPlanGrid);
	//判断是否查询成功
	if (!ContPlanGrid.mullLineCount <=0) {
		//resultFlag = 1;
		alert("目前没有任何险种特别信息！");
		return false;
	}
	QueryCount = 1;
	tSearch = 1;
	//divPubAccGrid.style.display="none";
}


function afterSubmit(FlagStr,content){
	showInfo.close();
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
	    RiskQueryClick();
	}
	else{
		content = "操作新契约成功！";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	    //RiskQueryClick();
	    if (document.all('mOperate').value == "DELETE||MAIN")
	    {
	       initContPlanGrid(); 
	    }        
	}	
	//initForm();
	if (fm.mOperate.value == "DELETE||PUBACC")
	{
	    divPubAccGrid.style.display="none";
	}
	document.all('mOperate').value = "";	
	divPubAmntGrid.style.display="none";
	//initContPlanGrid();
}

//-----------------------------------------Beg
//---------帐户设置-----------
//---------Add By：ChenRong
//---------Date:2006.5.17 11:45

//初始化公共帐户信息
function ShowPubAcc()
{
	if(document.all('RiskCode').value =="")
	{
		alert("请选择险种！");
		document.all('RiskCode').focus();
		return false;
	}
	showDivPubAccGrid(document.all('RiskCode').value);
}

function showDivPubAccGrid(tRiskCode)
{
    PubAccGrid.clearData();
    //先查询已保存的公共帐户信息，如果有则提示 查询公共帐户
	
				var sqlid77="ContQuerySQL77";
		    var mySql77=new SqlClass();
		    mySql77.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
		    mySql77.setSqlId(sqlid77);//指定使用的Sql的id
		    mySql77.addSubPara(fm.GrpContNo.value);//指定传入的参数
		    mySql77.addSubPara(fm.RiskCode.value);//指定传入的参数
	         var tSql =mySql77.getString();	
	
//    var tSql = "select s.riskname,lmduty.dutyname,p.Prem,"
//             + "s.riskcode,d.dutycode,p.InsuredNo,p.ContNo,p.GrpContNo,i.Name,"
//             + "p.amnt from lcinsured i,lccont c,lcpol p,lmrisk s,lcduty d,lmduty,LMRiskDuty "
//             + "where c.GrpContNo = '"+document.all('GrpContNo').value
//             + "'and poltype='2' and c.contno=p.contno and p.riskcode=s.riskcode "
//             + "and lmduty.dutycode=d.dutycode and c.contno=d.contno and i.contno=d.contno "
//             + "and p.riskcode= '"+document.all('RiskCode').value
//             + "'  and d.dutycode=LMRiskDuty.dutycode  and LMRiskDuty.specflag='Y'";
    turnPage.strQueryResult = easyQueryVer3(tSql, 1, 0, 1);
    
    //alert(turnPage.strQueryResult);
	//判断是否查询成功
    if(turnPage.strQueryResult)
    {
    	alert("您已经定义过该险种下的公共帐户信息，请选择查询公共帐户功能!");
    	return false;
    }
	
			var sqlid78="ContQuerySQL78";
		    var mySql78=new SqlClass();
		    mySql78.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
		    mySql78.setSqlId(sqlid78);//指定使用的Sql的id
		    mySql78.addSubPara(fm.RiskCode.value);//指定传入的参数
	         var strSql =mySql78.getString();	
	
//    var strSql = "select distinct LMrisk.RiskName,b.DutyName,'',0,"
//        + "LMrisk.RiskCode,a.DutyCode "
//		+ "from LMrisk,LMRiskDuty a, LMDuty b "
//		+ "where a.DutyCode = b.DutyCode and a.specflag='Y'"//加判断公共帐户的条件
//		+ "and LMrisk.RiskCode = '"+document.all('RiskCode').value
//		+"' and a.RiskCode = LMrisk.RiskCode order by a.DutyCode";

    turnPage.queryModal(strSql, PubAccGrid);
    if (PubAccGrid.mulLineCount <= 0) 
    {
    	alert("该险种没有需要定义的公共帐户信息！");
    	return false;
    }
    for(var i = 0; i < PubAccGrid.mulLineCount; i++)  
    {
        PubAccGrid.setRowColData(i,3,"公共帐户");    //公共帐户名称
        PubAccGrid.setRowColData(i,7,"Y");
    }	
    divPubAccGrid.style.display="";
}

//查询公共帐户信息
function QureyPubAcc()
{
	PubAccQueryClick();
}

function PubAccQueryClick()
{
    if(document.all('RiskCode').value =="")
	{
		alert("请选择险种！");
		document.all('RiskCode').focus();
		return false;
	}
    initPubAccGrid();
    //PubAccGrid.delBlankLine();
    var tResultFlag = 0;
	
				var sqlid79="ContQuerySQL79";
		    var mySql79=new SqlClass();
		    mySql79.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
		    mySql79.setSqlId(sqlid79);//指定使用的Sql的id
		    mySql79.addSubPara(fm.GrpContNo.value);//指定传入的参数
		    mySql79.addSubPara(fm.RiskCode.value);//指定传入的参数
	        var tSql =mySql79.getString();	
	
//    var tSql = "select s.riskname,lmduty.dutyname,i.Name,p.Prem,"
//               + "s.riskcode,d.dutycode,p.AutoPubAccFlag,p.InsuredNo,"
//               + "p.ContNo,p.GrpContNo,"
//               + "'',p.polno from lcinsured i,lccont c,lcpol p,"
//               + "lmrisk s,lcduty d,lmduty,LMRiskDuty "
//               + "where c.GrpContNo = '"+document.all('GrpContNo').value
//               + "'and poltype='2' and c.contno=p.contno and p.riskcode=s.riskcode"
//               + " and lmduty.dutycode=d.dutycode and c.contno=d.contno"
//               + " and i.contno=d.contno "
//               + "and p.riskcode= '"+document.all('RiskCode').value
//               + "' and d.dutycode=LMRiskDuty.dutycode and LMRiskDuty.specflag='Y'";
    turnPage.pageLineNum = 10;
    turnPage.queryModal(tSql,PubAccGrid);
    
	//判断是否查询成功
	if(PubAccGrid.mulLineCount <= 0)
	{
		alert("没有相应的公共帐户信息!");
		return false;
	}
	queryPubAccFlag = true;
	
	divPubAccGrid.style.display="";   
    
    if(this.LoadFlag=="4"||this.LoadFlag=="16")
    {
        PubAccSave.style.display="none";
    }
    else
    {
        PubAccSave.style.display=""; 
    }
    
}

//试算帐户余额
function CompPubAcc()
{
	//添加校验信息
    if(queryPubAccFlag == false)
    {
    	alert("请先查询公共帐户！");
    }

	var tResultFlag = 0;
	
			var sqlid80="ContQuerySQL80";
		    var mySql80=new SqlClass();
		    mySql80.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
		    mySql80.setSqlId(sqlid80);//指定使用的Sql的id
		    mySql80.addSubPara(fm.GrpContNo.value);//指定传入的参数
		    mySql80.addSubPara(fm.RiskCode.value);//指定传入的参数
	        var tSql =mySql80.getString();	
	
//        var tSql = "select s.riskname,lmduty.dutyname,p.Prem,'0',s.riskcode,d.dutycode,p.InsuredNo,p.ContNo,p.GrpContNo,i.Name,p.amnt,p.polno from lcinsured i,lccont c,lcpol p,lmrisk s,lcduty d,lmduty,LMRiskDuty "
//                 + "where c.GrpContNo = '"+document.all('GrpContNo').value+"'and poltype='2' and c.contno=p.contno and p.riskcode=s.riskcode and lmduty.dutycode=d.dutycode and c.contno=d.contno and i.contno=d.contno "
//                 + "and p.riskcode= '"+document.all('RiskCode').value+"' and d.dutycode=LMRiskDuty.dutycode and LMRiskDuty.specflag='Y'";
    turnPage.strQueryResult = easyQueryVer3(tSql, 1, 0, 1);
    //alert(turnPage.strQueryResult);
    if(!turnPage.strQueryResult)
    {
    	alert("没有公共帐户信息，请先定义公共帐户！");
    	return false;
    }
	if (!PubAccGrid.getSelNo())
	{
		alert("请选择险种公共帐户！");
	        return false;
	}
	else
	{
		var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
		showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
        fm.action="CompPubAccSave.jsp";
        document.getElementById("fm").submit(); //提交
	}
}

//提交，保存公共帐户按钮对应操作
function SavePubAcc()
{    
	if (!beforeSubmit2()){
		return false;
	}
	//alert(riskcode);
	var tRow = PubAccGrid.getSelNo();
	//先查询已保存的公共帐户信息，如果有则提示查询公共帐户
	
			var sqlid81="ContQuerySQL81";
		    var mySql81=new SqlClass();
		    mySql81.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
		    mySql81.setSqlId(sqlid81);//指定使用的Sql的id
		    mySql81.addSubPara(fm.GrpContNo.value);//指定传入的参数
		    mySql81.addSubPara(PubAccGrid.getRowColData(tRow-1,5));//指定传入的参数
		    mySql81.addSubPara(PubAccGrid.getRowColData(tRow-1,8));//指定传入的参数
		    mySql81.addSubPara(PubAccGrid.getRowColData(tRow-1,9));//指定传入的参数
		    mySql81.addSubPara(PubAccGrid.getRowColData(tRow-1,12));//指定传入的参数
	        var tSql =mySql81.getString();	
	
//    var tSql = "select p.Prem,"
//             + "p.InsuredNo,p.ContNo,p.GrpContNo,"
//             + "p.amnt from lcpol p,LMRiskDuty "
//             + "where p.GrpContNo = '"+document.all('GrpContNo').value
//             + "' and poltypeflag='2' "
//             + "and LMRiskDuty.riskcode=p.riskcode and LMRiskDuty.specflag='Y'"
//             + "and p.riskcode= '"+PubAccGrid.getRowColData(tRow-1,5)
//             + "' and p.InsuredNo='" + PubAccGrid.getRowColData(tRow-1,8)
//             + "' and p.contno='" + PubAccGrid.getRowColData(tRow-1,9)
//             + "' and p.polno='" +  PubAccGrid.getRowColData(tRow-1,12) + "'";
    turnPage.pageLineNum = 10;
    turnPage.strQueryResult = easyQueryVer3(tSql, 1, 0, 1);
    //alert(turnPage.strQueryResult);
	//判断是否查询成功
    if(turnPage.strQueryResult)
    {
    	alert("您已经定义过该险种下的公共帐户信息，请选择修改或删除功能!");
    	return false;
    }
    
	if (!confirm('公共帐户的全部信息是否已录入完毕，您是否要确认操作？'))
	{
		return;
	}
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
    fm.mOperate.value="INSERT||PUBACC";   
    fm.action="PubAccSave.jsp"; 
	document.getElementById("fm").submit(); //提交
}

//提交，保存公共帐户按钮对应操作
function UpdatePubAcc()
{    
	if (!beforeSubmit2()){
		return false;
	}
    
	if (!confirm('公共帐户的全部信息是否已录入完毕，您是否要确认操作？'))
	{
		return;
	}	    
    fm.action="PubAccSave.jsp";
    //alert(fm.AppntNo.value);   
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
    fm.mOperate.value="UPDATE||PUBACC";
    fm.action="PubAccSave.jsp";        
	document.getElementById("fm").submit(); //提交
}


function beforeSubmit2()
{
	//添加校验信息
	PubAccGrid.delBlankLine("PubAccGrid");
	var sValue;
	var lineCount = PubAccGrid.mulLineCount;
	var riskcode = document.all('RiskCode').value;
	//alert(riskcode);
	var tRow = PubAccGrid.getSelNo();

	//alert(PubAccGrid.getSelNo(i));
	if( tRow == 0 || tRow == null )
	{
		alert("请选择险种公共帐户！");
        return false;
	}
	
	sValue = PubAccGrid.getRowColData(tRow-1,4);
    if (!isNumeric(sValue))
    {
        alert("缴费金额录入有误，应该填写数字！");
        PubAccGrid.setFocus(tRow-1,4);
        return false;
    }
    sValue = PubAccGrid.getRowColData(tRow-1,7);
    if(riskcode=="221704")
    {
    	if (sValue!="Y"&&sValue!="N")
        {
           	alert("请选择自动应用公共帐户标记！");
            PubAccGrid.setFocus(tRow-1,7);
            return false;
        }
    }
    return true;
	
}

//删除所选公共帐户
function DelPubAcc()
{
	if(!confirm("确定删除？"))
	{
	    return ;
	}
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.mOperate.value = "DELETE||PUBACC";
	fm.action="PubAccDelSave.jsp";
	document.getElementById("fm").submit();
}

//初始化公共保额列表
function ShowPubAmnt()
{
	if(document.all('RiskCode').value =="")
	{
		alert("请选择险种！");
		document.all('RiskCode').focus();
		return false;
	}
	showDivPubAmntGrid(document.all('RiskCode').value);
}

function showDivPubAmntGrid(tRiskCode)
{
    initPubAmntGrid();
    PubAmntGrid.clearData();
    //先查询已保存的公共保额信息，如果有则提示 查询公共保额
	
				var sqlid82="ContQuerySQL82";
		    var mySql82=new SqlClass();
		    mySql82.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
		    mySql82.setSqlId(sqlid82);//指定使用的Sql的id
		    mySql82.addSubPara(fm.GrpContNo.value);//指定传入的参数
		    mySql82.addSubPara(fm.RiskCode.value);//指定传入的参数
	        var tSql =mySql82.getString();	
	
//    var tSql = "select s.riskname,lmduty.dutyname,p.Prem,s.riskcode,d.dutycode,p.InsuredNo,p.ContNo,p.GrpContNo,i.Name,p.amnt from lcinsured i,lccont c,lcpol p,lmrisk s,lcduty d,lmduty,LMRiskDuty "
//             + "where c.GrpContNo = '"+document.all('GrpContNo').value+"'and poltype='2' and c.contno=p.contno and p.riskcode=s.riskcode and lmduty.dutycode=d.dutycode and c.contno=d.contno "
//             + "and i.contno=d.contno and p.riskcode= '" +document.all('RiskCode').value+ "' and LMRiskDuty.specflag='A' and LMRiskDuty.dutycode=d.dutycode";
    turnPage.pageLineNum = 10;
    turnPage.strQueryResult = easyQueryVer3(tSql, 1, 0, 1);

	//判断是否查询成功
    if(turnPage.strQueryResult)
    {
    	alert("您已经定义过该险种下的公共保额信息，请选择 查询公共保额 功能!");
    	return false;
    }
	
			var sqlid83="ContQuerySQL83";
		    var mySql83=new SqlClass();
		    mySql83.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
		    mySql83.setSqlId(sqlid83);//指定使用的Sql的id
		    mySql83.addSubPara(fm.RiskCode.value);//指定传入的参数
	        var tSql =mySql83.getString();	
	
//    var strSql = "select distinct LMrisk.RiskName,b.DutyName,b.DutyName,'0','0',LMrisk.RiskCode,a.DutyCode,a.ChoFlag,case a.ChoFlag when 'M' then '必选' when 'B' then '备用' else '可选' end ChoFlagName"
//		+ "from LMrisk,LMRiskDuty a, LMDuty b ,LMRiskDutyFactor c "
//		+ "where a.DutyCode = b.DutyCode and a.RiskCode = c.RiskCode and a.DutyCode = c.DutyCode and a.specflag='A'"//加判断公共保额的条件
//		+ "and LMrisk.RiskCode = '"+document.all('RiskCode').value
//		+ "' and a.RiskCode = LMrisk.RiskCode order by a.DutyCode";

    turnPage.pageLineNum = 10;
    turnPage.queryModal(strSql,PubAmntGrid);
    
    if (PubAmntGrid.mulLineCount <= 0) 
    {
    	alert("该险种没有需要定义的公共保额信息！");
    	return false;
    }
    divPubAmntGrid.style.display="";
}

//查询已定义的公共保额
function PubAmntQueryClick()
{
	if(document.all('RiskCode').value =="")
	{
		alert("请选择险种！");
		document.all('RiskCode').focus();
		return false;
	}
    initPubAmntGrid();
    PubAmntGrid.delBlankLine();
    var tResultFlag = 0;
	
			var sqlid84="ContQuerySQL84";
		    var mySql84=new SqlClass();
		    mySql84.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
		    mySql84.setSqlId(sqlid84);//指定使用的Sql的id
		    mySql84.addSubPara(fm.RiskCode.value);//指定传入的参数
	        var tSql =mySql84.getString();	
	
//    var tSql = "select s.riskname,lmduty.dutyname,i.Name,p.Amnt,p.Prem,s.riskcode,d.dutycode,p.InsuredNo,p.ContNo,p.GrpContNo from lcinsured i,lccont c,lcpol p,lmrisk s,lcduty d,lmduty,LMRiskDuty "
//               + "where c.GrpContNo = '"+document.all('GrpContNo').value+"'and poltype='2' and c.contno=p.contno and p.riskcode=s.riskcode and lmduty.dutycode=d.dutycode and c.contno=d.contno "
//               + "and i.contno=d.contno and p.riskcode= '"+document.all('RiskCode').value+"' and LMRiskDuty.specflag='A' and d.dutycode=LMRiskDuty.dutycode";
    turnPage.pageLineNum = 10;
    turnPage.queryModal(tSql,PubAmntGrid);

	//判断是否查询成功
	if(PubAmntGrid.mulLineCount <= 0)
	{
		alert("没有相应的公共保额信息!");
		return false;
	}
    divPubAmntGrid.style.display="";
    if(this.LoadFlag=="4"||this.LoadFlag=="16")
    {
        PubAmntSave.style.display="none";
    }
    else
    {
        PubAmntSave.style.display="";
    }
    
}

//提交，保存公共帐户按钮对应操作
function SavePubAmnt(){
	if (!beforeSubmit3()){
		return false;
	}
	if (!confirm('公共保额的全部信息是否已录入完毕，您是否要确认操作？'))
	{
		return;
	}

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
    fm.action="PubAmntSave.jsp";
        
	document.getElementById("fm").submit(); //提交
	//PubAccQueryClick();
}
function beforeSubmit3()
{
	//添加校验信息
	var lineCount = 0;
	var sValue;
	PubAmntGrid.delBlankLine("PubAmntGrid");
	lineCount = PubAmntGrid.mulLineCount;
	var tRow = PubAmntGrid.getSelNo();
	//alert(PubAmntGrid.getSelNo(i));
	if (tRow == 0 || tRow == null)
	{
		alert("请选择险种公共保额！");
        return false;
	}
	
	sValue = PubAmntGrid.getRowColData(tRow - 1,4);
	//alert(sValue);
    if (sValue!=""&&!isNumeric(sValue))
    {
        alert("保额录入有误，应该填写数字！");
        PubAmntGrid.setFocus(tRow - 1,4);
        return false;
    }
	sValue = PubAmntGrid.getRowColData(tRow - 1,5);
	if (sValue!=""&&!isNumeric(sValue))
    {
        alert("保费录入有误，应该填写数字！");
        PubAmntGrid.setFocus(tRow - 1,5);
        return false;
	}

    return true;
}

//删除所选公共保额
function DelPubAmnt()
{
	var lineCount = 0;
	PubAmntGrid.delBlankLine("PubAmntGrid");
	lineCount = PubAmntGrid.mulLineCount;
	var tRow = PubAmntGrid.getSelNo();
			//alert(PubAmntGrid.getSelNo(i));
	if (tRow == 0 || tRow == null)
	{
		alert("请选择要删除的险种公共保额！");
                return false;
	}

	if(!confirm("确定删除？"))
	{
	    return ;
	}
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.action="PubAmntDelSave.jsp";
	document.getElementById("fm").submit();
}

function feeRecord()
{
    showInfo = window.open("./LMChargeRatemain.jsp?Interface=LMChargeRateInput.jsp?GrpContNo="+fm.GrpContNo.value+"&RiskCode="+fm.RiskCode.value,"",'top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0;'+sFeatures);
}

function AscriptionRuleInfo()
{
	parent.fraInterface.window.location = "./AscriptionRuleInput.jsp?GrpContNo=" + document.all('GrpContNo').value+"&LoadFlag="+LoadFlag;
}

//账户触发器
function GrpAccTrigger()
{
	parent.fraInterface.window.location= "./LCGrpAccTriggerInput.jsp?GrpContNo=" + document.all('GrpContNo').value + "&LoadFlag=" + LoadFlag;
}

function afterAccSubmit( FlagStr, content ){
	showInfo.close();
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
	//alert(document.all('mOperate').value);	
	//initForm();
	document.all('mOperate').value = "";
	fm.action = "";
	PubAccQueryClick();
} 

function afterAmntSubmit( FlagStr, content ){
	showInfo.close();
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
	//alert(document.all('mOperate').value);
	initForm();
	document.all('mOperate').value = "";
	PubAmntQueryClick();
}
	

function afterCompAcc( FlagStr, content, SumAccBala){
	showInfo.close();
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
		content = "计算成功！";
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
	for(var i=0;i<PubAccGrid.mulLineCount;i++)
    {
    	//alert(PubAccGrid.getSelNo(i));
    	if (PubAccGrid.getSelNo(i))
    	{
            PubAccGrid.setRowColData(PubAccGrid.getSelNo(i)-1,11,SumAccBala);//帐户余额
        }
    }
}

function grpRiskPlanInfo()
{
	if (fm.ProposalGrpContNo.value=="")
	{
	    alert("必须保存合同信息才能进行〔保险计划制定〕！");  
	    return false;  
	}
	var newWindow = window.open("../app/ContPlan.jsp?GrpContNo="+fm.GrpContNo.value+"&LoadFlag="+LoadFlag,"",sFeatures);
}

function grpInsuList()
{  
	
//alert(prtNo);
//var prtNo ="<%=request.getParameter("prtNo")%>";
//var polNo ="<%=request.getParameter("polNo")%>";
//var scantype ="<%=request.getParameter("scantype")%>";
//var MissionID ="<%=request.getParameter("MissionID")%>";
//var ManageCom ="<%=request.getParameter("ManageCom")%>";
//var SubMissionID ="<%=request.getParameter("SubMissionID")%>";
//var ActivityID = "<%=request.getParameter("ActivityID")%>";
//var NoType = "<%=request.getParameter("NoType")%>";
//var GrpContNo ="<%=request.getParameter("GrpContNo")%>";
	var tSQL="select * from lccontplanrisk where proposalgrpcontno='"+prtNo+"'";
	var arrResult = easyExecSql(tSQL);
	//alert(tSQL);
	if(arrResult==null)
	{
	alert("必须录入险种信息后才能进入(被保人清单〕信息界面!");
	return false;
	}
	
			var sqlid85="ContQuerySQL85";
		    var mySql85=new SqlClass();
		    mySql85.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
		    mySql85.setSqlId(sqlid85);//指定使用的Sql的id
		    mySql85.addSubPara(prtNo);//指定传入的参数
	        var tSQL1 =mySql85.getString();	
	
	//var tSQL1="select * from LCGrpAppnt where prtno='"+prtNo+"'";
	var arrResult1 = easyExecSql(tSQL1);
	if(arrResult1==null)
	{
		alert("合同信息尚未保存，不允许进入（被保人清单）信息界面!");
		return false;
		}
	try { mSwitch.addVar('ProposalGrpContNo','',fm.ProposalGrpContNo.value); } catch(ex) { };
	try { mSwitch.addVar('ManageCom','',fm.ManageCom.value); } catch(ex) { };
	try { mSwitch.addVar('GrpContNo','',fm.GrpContNo.value); } catch(ex) { };
  //delGrpVar();
  //addGrpVar();
  parent.fraInterface.window.location = "../app/ContGrpInsuredInput.jsp?LoadFlag="+LoadFlag+"&scantype="+scantype+"&prtNo="+prtNo+"&polNo="+polNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID;    
    
}
//-----------------------------------------End