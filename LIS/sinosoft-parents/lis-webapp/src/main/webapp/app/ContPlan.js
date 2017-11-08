//该文件中包含客户端需要处理的函数和事件
var mDebug="0";
var mOperate="";
var showInfo;
var arrDataSet;
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();
var QueryResult="";
var QueryCount = 0;
var mulLineCount = 0;
var QueryWhere="";
var tSearch = 0;
window.onfocus=myonfocus;
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";

function afterCodeSelect( cCodeName, Field ){
	//判定双击操作执行的是什么查询
	if (cCodeName=="GrpRisk"){
		var tRiskFlag = document.all('RiskFlag').value;
		//由于附加险不带出主险录入框，因此判定当主附险为S的时候隐藏
		if (tRiskFlag!="S"){
			divmainriskname.style.display = 'none';
			divmainrisk.style.display = 'none';
			document.all('MainRiskCode').value = document.all('RiskCode').value;
		}
		 else{
		 	
		var sqlid39="ContQuerySQL39";
		var mySql39=new SqlClass();
		mySql39.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
		mySql39.setSqlId(sqlid39);//指定使用的Sql的id
		mySql39.addSubPara(fm.ProposalGrpContNo.value);//指定传入的参数
		mySql39.addSubPara(fm.RiskCode.value);//指定传入的参数
	    var strSQL=mySql39.getString();	
			
//			var strSQL="select * from lcgrppol where grpcontno='"+fm.ProposalGrpContNo.value+"' and riskcode in"
//			+"(select riskcode from lmriskrela where relariskcode = '"+fm.RiskCode.value+"')";
			turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
			if(!turnPage.strQueryResult)
			{
				alert("该险种主险未添加,不能定制该险种保险计划");
				document.all('MainRiskCode').value = "";
				return false;
			}
		else
			{
			divmainriskname.style.display = '';
			divmainrisk.style.display = '';
			document.all('MainRiskCode').value = "";
		  }
	}}
	//判断是否选择保险套餐
	if (cCodeName=="RiskPlan1"){
		var tRiskPlan1 = document.all('RiskPlan1').value;
		if (tRiskPlan1!="0"){
			divriskcodename.style.display = 'none';
			divriskcode.style.display = 'none';
			divcontplanname.style.display = '';
			divcontplan.style.display = '';
			ContPlanGrid.lock();
		}
		else{
			divriskcodename.style.display = '';
			divriskcode.style.display = '';
			divcontplanname.style.display = 'none';
			divcontplan.style.display = 'none';
			ContPlanGrid.unLock();
		}
	}
	//将保险套餐数据带入界面
	if (cCodeName=="RiskPlan"){
		var tRiskPlan = document.all('RiskPlan').value;
		if (tRiskPlan!=null&&tRiskPlan!=""){
			showPlan();
		}
	}
}

function CheckRisk(mIsShow)
{
	
			var sqlid40="ContQuerySQL40";
		var mySql40=new SqlClass();
		mySql40.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
		mySql40.setSqlId(sqlid40);//指定使用的Sql的id
		mySql40.addSubPara(document.all('RiskCode').value);//指定传入的参数
	    var strSQL=mySql40.getString();	
	
   // var strSQL="select SubRiskFlag from lmriskapp where riskcode='"+document.all('RiskCode').value+"'";
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
	    if (mIsShow == "1")
	    {
			
			var sqlid41="ContQuerySQL41";
			var mySql41=new SqlClass();
			mySql41.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
			mySql41.setSqlId(sqlid41);//指定使用的Sql的id
			mySql41.addSubPara(fm.ProposalGrpContNo.value);//指定传入的参数
			mySql41.addSubPara(fm.RiskCode.value);//指定传入的参数
		    var strSQL=mySql41.getString();	
			
//    		var strSQL="select * from lcgrppol where grpcontno='"+fm.ProposalGrpContNo.value+"' and riskcode in"
//    		+"(select riskcode from lmriskrela where relariskcode = '"+fm.RiskCode.value+"')";
    		turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
    		if(!turnPage.strQueryResult)
    		{
    			alert("该险种主险未添加,不能定制该险种保险计划");
    			document.all('MainRiskCode').value = "";
    			return false;
    		}
	    }
    	divmainriskname.style.display = '';
    	divmainrisk.style.display = '';
	 }
}

//数据查询
function AddContClick(){
	if (document.all('ContPlanCode').value == ""){
		alert("请输入保障级别！");
		document.all('ContPlanCode').focus();
		return false;
	}
	if(document.all('RiskCode').value ==""){
		alert("请选择险种！");
		document.all('RiskCode').focus();
		return false;
	}
	if(document.all('MainRiskCode').value ==""){
		alert("请输入主险信息！");
		document.all('MainRiskCode').focus();
		return false;
	}
	var lineCount = 0;
	var MainRiskCode = document.all('MainRiskCode').value
	var sRiskCode ="";
	var sMainRiskCode="";
	ContPlanDutyGrid.delBlankLine("ContPlanDutyGrid");
	lineCount = ContPlanDutyGrid.mulLineCount;
	//还需要添加一个校验，不过比较麻烦，暂时先作了
	for (i=0;i<ContPlanGrid.mulLineCount;i++){
		sRiskCode=ContPlanGrid.getRowColData(i,2);
		sMainRiskCode=ContPlanGrid.getRowColData(i,12);
		//主要是考虑附险会挂在不同的主险下，因此需要双重校验
		if (sRiskCode == document.all('RiskCode').value && sMainRiskCode == MainRiskCode){
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
	//alert(getWhere);

	// 书写SQL语句
	var strSQL = "";

	if (QueryCount == 0){
		//查询该险种下的险种计算要素
		
			var sqlid42="ContQuerySQL42";
			var mySql42=new SqlClass();
			mySql42.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
			mySql42.setSqlId(sqlid42);//指定使用的Sql的id
			mySql42.addSubPara(MainRiskCode);//指定传入的参数
			mySql42.addSubPara(getWhere);//指定传入的参数
			mySql42.addSubPara(GrpContNo);//指定传入的参数
			mySql42.addSubPara(fm.RiskCode.value);//指定传入的参数
		    strSQL=mySql42.getString();	
		
//		strSQL = "select b.RiskName,a.RiskCode,a.DutyCode,c.DutyName,a.CalFactor,a.FactorName,a.FactorNoti,case a.CalFactorType when '1' then a.CalSql else '' end,'',b.RiskVer,d.GrpPolNo,'"+MainRiskCode+"',a.CalFactorType,c.CalMode "
//			+ "from LMRiskDutyFactor a, LMRisk b, LMDuty c, LCGrpPol d "
//			+ "where a.RiskCode = b.RiskCode and a.DutyCode = c.DutyCode and a.RiskCode = d.RiskCode "
//			+ "and a.DutyCode in "+ getWhere + " and a.ChooseFlag in ('0','2') "
//			+ "and GrpContNO = '"+GrpContNo+"' and a.RiskCode = '"+document.all('RiskCode').value+"' order by a.RiskCode,a.DutyCode,a.FactorOrder";
		//document.all('PlanSql').value = strSQL;
		//alert(strSQL);
		turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
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
		
			var sqlid43="ContQuerySQL43";
			var mySql43=new SqlClass();
			mySql43.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
			mySql43.setSqlId(sqlid43);//指定使用的Sql的id
			mySql43.addSubPara(getWhere);//指定传入的参数
			mySql43.addSubPara(GrpContNo);//指定传入的参数
			mySql43.addSubPara(fm.RiskCode.value);//指定传入的参数
		    strSQL=mySql43.getString();	
		
//		strSQL = "select b.RiskName,a.RiskCode,a.DutyCode,c.DutyName,a.CalFactor,a.FactorName,a.FactorNoti,case a.CalFactorType when '1' then a.CalSql else '' end,'',b.RiskVer,d.GrpPolNo,'',a.CalFactorType,c.CalMode "
//			+ "from LMRiskDutyFactor a, LMRisk b, LMDuty c, LCGrpPol d "
//			+ "where a.RiskCode = b.RiskCode and a.DutyCode = c.DutyCode and a.RiskCode = d.RiskCode "
//			+ "and a.DutyCode in "+ getWhere + " and a.ChooseFlag in ('0','2') "
//			+ "and GrpContNO = '"+GrpContNo+"' and a.RiskCode = '"+document.all('RiskCode').value+"' order by a.RiskCode,a.DutyCode,a.FactorOrder";
		//document.all('ContPlanName').value = strSQL;
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

//数据提交（保存）
function submitForm()
{
	if (!beforeSubmit())
	{
		return false;
	}
	document.all('mOperate').value = "INSERT||MAIN";
	if (document.all('mOperate').value == "INSERT||MAIN")
	{
		if (!confirm('计划 '+document.all('ContPlanCode').value+' 下的全部险种要素信息是否已录入完毕，您是否要确认操作？'))
		{
			return false;
		}
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
	document.getElementById("fm").submit(); //提交
}

//返回上一步
function returnparent()
{
	parent.close();
}

//数据提交（删除）
function DelContClick(){
	//此方法得到的行数需要-1处理
	var line = ContPlanCodeGrid.getSelNo();
	if (line == 0)
	{
		alert("请选择要删除的计划！");
		document.all('ContPlanCode').value = "";
		return false;
	}
	else
	{
		document.all('ContPlanCode').value = ContPlanCodeGrid.getRowColData(line-1,1);
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

	showInfo.focus();
	QueryCount = 0;	//重新初始化查询次数
	document.getElementById("fm").submit(); //提交
}

//数据提交（修改）
function UptContClick(){
	if (tSearch == 0){
		alert("请先查询要修改的保障计划！");
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
	document.getElementById("fm").submit(); //提交
}

//数据校验
function beforeSubmit(){
	if (document.all('ContPlanCode').value == ""){
		alert("请输入保障级别！");
		document.all('ContPlanCode').focus();
		return false;
	}
	if(document.all('ContPlanCode').value == "00")
	{
		alert("保障级别不能为00");
		document.all('ContPlanCode').focus();
		return false;
	}
	if (document.all('mOperate').value != "UPDATE||MAIN"){
		if (document.all('MainRiskCode').value == ""){
			alert("请输入主险编码！");
			document.all('MainRiskCode').focus();
			return false;
		}
	}
	if (ContPlanGrid.mulLineCount == 0){
		alert("请输入保险计划详细信息");
		return false;
	}
	var lineCount = ContPlanGrid.mulLineCount;
	var sValue;
	var sCalMode;
	var sCalFactor;
	var sRiskCode;
	var sPrem = "";
	var sAmnt = "";
	var sCount = 0;
	//添加要素值信息校验
	for(var i=0;i<lineCount;i++){
      	sValue = ContPlanGrid.getRowColData(i,8);
      	sCalMode = ContPlanGrid.getRowColData(i,14);
      	sCalFactor = ContPlanGrid.getRowColData(i,5);
      	sRiskCode = ContPlanGrid.getRowColData(i,2);
      	if (sCalFactor != "PayRuleCode" && sCalFactor != "AscriptionRuleCode")
		{
          	//互算校验
          	if (sCalMode == "A"){
          	    if (sCalFactor == "Prem")
          	    {
          	        sCount = sCount + 1;
          	        sPrem = sValue;
          	    }
          	    if (sCalFactor == "Amnt")
          	    {
          	        sCount = sCount + 1;
          	        sAmnt = sValue;
          	    }
          	    if (sCount > 1 && (sPrem == "" || sPrem == 0) && (sAmnt == "" || sAmnt == 0))
          	    {
          	        alert("请录入保额或者保费！");
          	        ContPlanGrid.setFocus(i,8);
          	        return false;
          	    }
    			if (sValue!=""){    			    
    				if (!isNumeric(sValue)){
    					alert("请录入数字！");
    					ContPlanGrid.setFocus(i,8);
    					return false;
    				}
    			}
    			
    		}
    		//保费算保额校验
          	if (sCalMode == "P"){
          	    if (sCalFactor == "Prem")
          	    {
        			if (sValue==""){
        				alert("请录入保费！");
        				ContPlanGrid.setFocus(i,8);
        				return false;
        			}
        			if(sValue=0){
        				alert("保费不能为0！");
        				ContPlanGrid.setFocus(i,8);
        				return false;
        			}
    		    }    		    
    			if (!isNumeric(sValue)){
    				alert("请录入数字！");
    				ContPlanGrid.setFocus(i,8);
    				return false;
    			}
    			
    		}
    		//保额算保费校验
          	if (sCalMode == "G"){
          	    if (sCalFactor == "Amnt")
          	    {
          	    	if (sValue==""){
        				alert("请录入保额！");
        				ContPlanGrid.setFocus(i,8);
        				return false;
        			}        			
        			if(sValue==0){
        				alert("保额不能为0！");
        				ContPlanGrid.setFocus(i,8);
        				return false;
        			}
        		}
        		if (!isNumeric(sValue)){
    				alert("请录入数字！");
    				ContPlanGrid.setFocus(i,8);
    				return false;
    			}
    		}
    		//其他因素算保费保额校验
          	if (sCalMode == "O"){
    			if (sValue==""){
    				alert("请录入要素值！");
    				ContPlanGrid.setFocus(i,8);
    				return false;
    			}
    			if (!isNumeric(sValue)){
    				alert("请录入数字！");
    				ContPlanGrid.setFocus(i,8);
    				return false;
    			}
    			if(sValue==0){
    				alert("要素值不能为0！");
    				ContPlanGrid.setFocus(i,8);
    				return false;
    			}
    		}
    		//录入保费保额校验
          	if (sCalMode == "I"){
    			if (sValue!=""){
    				if (!isNumeric(sValue)){
    					alert("请录入数字！");
    					ContPlanGrid.setFocus(i,8);
    					return false;
    				}
    			}
    		}
    	}
        else{
            if (sValue==""){
				alert("请录入要素值！");
				ContPlanGrid.setFocus(i,8);
				return false;
    		}
            if (sCalFactor == "PayRuleCode")
            {
                if (CheckPayRuleCode(sRiskCode,sValue) == false)
                {
                    ContPlanGrid.setFocus(i,8);
                    return false;
                }
            }
            if (sCalFactor == "AscriptionRuleCode")
            {
                if (CheckAscriptionRuleCode(sRiskCode,sValue) == false)
                {
                    ContPlanGrid.setFocus(i,8);
                    return false;
                }
            }
        }
	}
	return true;
}

function initFactoryType(tRiskCode)
{
	// 书写SQL语句
	var k=0;
	var strSQL = "";
	
			var sqlid44="ContQuerySQL44";
			var mySql44=new SqlClass();
			mySql44.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
			mySql44.setSqlId(sqlid44);//指定使用的Sql的id
			mySql44.addSubPara(tRiskCode);//指定传入的参数
		    strSQL=mySql44.getString();	
	
//	strSQL = "select distinct a.FactoryType,b.FactoryTypeName,a.FactoryType||"+tRiskCode+" from LMFactoryMode a ,LMFactoryType b  where 1=1 "
//		   + " and a.FactoryType= b.FactoryType "
//		   + " and (RiskCode = '"+tRiskCode+"' or RiskCode ='000000' )";
    var str  = easyQueryVer3(strSQL, 1, 0, 1);
    return str;
}

/*********************************************************************
 *  Click事件，当点击“保险计划要约录入”按钮时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function nextstep()
{
	var newWindow = window.open("../app/ContPlanNextInput.jsp?GrpContNo="+document.all('GrpContNo').value+"&LoadFlag="+LoadFlag,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0;'+sFeatures);
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
	}
	else{
		content = "操作成功！";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	    initContPlanCodeGrid();
	    initContPlanDutyGrid();
	    initContPlanGrid();
	    easyQueryClick();
	    tSearch = 0;
	    QueryCount = 0;
	}
	document.all('mOperate').value = "";
}

function QueryDutyClick(){
	if (document.all('ContPlanCode').value == ""){
		alert("请输入保障级别！");
		document.all('ContPlanCode').focus();
		return false;
	}
	if(document.all('RiskCode').value ==""){
		alert("请选择险种！");
		document.all('RiskCode').focus();
		return false;
	}

	initContPlanDutyGrid();

	//查询该险种下的险种计算要素
	
			var sqlid45="ContQuerySQL45";
			var mySql45=new SqlClass();
			mySql45.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
			mySql45.setSqlId(sqlid45);//指定使用的Sql的id
			mySql45.addSubPara(document.all('RiskCode').value);//指定传入的参数
		    strSQL=mySql45.getString();	
	
//	strSQL = "select distinct a.DutyCode,b.DutyName,a.ChoFlag,case a.ChoFlag when 'M' then '必选' when 'B' then '备用' else '可选' end ChoFlagName "
//		+ "from LMRiskDuty a, LMDuty b ,LMRiskDutyFactor c "
//		+ "where a.DutyCode = b.DutyCode and a.RiskCode = c.RiskCode and a.DutyCode = c.DutyCode "
//		+ "and a.RiskCode = '"+document.all('RiskCode').value+"' order by a.DutyCode";
		
	//document.all('ContPlanName').value = strSQL;
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	if (!turnPage.strQueryResult) {
		alert("没有该险种下的责任信息！");
		return false;
	}
	//查询成功则拆分字符串，返回二维数组
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	//设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
	turnPage.pageDisplayGrid = ContPlanDutyGrid;
	//保存SQL语句
	turnPage.strQuerySql = strSQL;
	//设置查询起始位置
	turnPage.pageIndex = 0;
	//在查询结果数组中取出符合页面显示大小设置的数组
	//调用MULTILINE对象显示查询结果
	displayMultiline(turnPage.arrDataCacheSet, turnPage.pageDisplayGrid);
	var cDutyCode="";
	var tSql="";
	for(var i=0;i<=ContPlanDutyGrid.mulLineCount-1;i++){
	  cDutyCode=ContPlanDutyGrid.getRowColData(i,1);
//	  tSql="select choflag from lmriskduty where riskcode='"+document.all('RiskCode').value+"' and dutycode='"+cDutyCode+"'";
	  
		var sqlid0="ContPlanSql0";
		var mySql0=new SqlClass();
		mySql0.setResourceName("app.ContPlanSql"); //指定使用的properties文件名
		mySql0.setSqlId(sqlid0);//指定使用的Sql的id
		mySql0.addSubPara(document.all('RiskCode').value);//指定传入的参数
		mySql0.addSubPara(cDutyCode);//指定传入的参数
		tSql=mySql0.getString();	
	  
	  var arrResult=easyExecSql(tSql,1,0);
	  //alert("ChoFlag:"+arrResult[0]);
	  if(arrResult[0]=="M"){
	  	 ContPlanDutyGrid.checkBoxSel(i+1);
	  }
	}
}

function easyQueryClick(){
	initContPlanCodeGrid();

	//查询该险种下的险种计算要素
	
			var sqlid46="ContQuerySQL46";
			var mySql46=new SqlClass();
			mySql46.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
			mySql46.setSqlId(sqlid46);//指定使用的Sql的id
			mySql46.addSubPara(document.all('GrpContNo').value);//指定传入的参数
		    strSQL=mySql46.getString();	
	
//	strSQL = "select ContPlanCode,ContPlanName,PlanSql,Peoples3,GrpContNo "
//		+ "from LCContPlan "
//		+ "where 1=1 "
//		+ "and GrpContNo = '"+document.all('GrpContNo').value+"' and ContPlanCode <> '00'"
//		+" order by ContPlanCode";
	//document.all('ContPlanName').value = strSQL;
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	//如果没数据也无异常
	if (!turnPage.strQueryResult) {
		//return false;
	}
	else{
		//QueryCount = 1;
		//查询成功则拆分字符串，返回二维数组
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		//设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
		turnPage.pageDisplayGrid = ContPlanCodeGrid;
		//保存SQL语句
		turnPage.strQuerySql = strSQL;
		//设置查询起始位置
		turnPage.pageIndex = 0;
		//在查询结果数组中取出符合页面显示大小设置的数组
		//调用MULTILINE对象显示查询结果
		displayMultiline(turnPage.arrDataCacheSet, turnPage.pageDisplayGrid);
	}
	//不管前面的查询什么结果，都执行下面的查询
	//如果发生参数错误，导致查询失败，这里程序会出错
	
				var sqlid47="ContQuerySQL47";
			var mySql47=new SqlClass();
			mySql47.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
			mySql47.setSqlId(sqlid47);//指定使用的Sql的id
			mySql47.addSubPara(GrpContNo);//指定传入的参数
		    strSQL=mySql47.getString();	
	
	//strSQL = "select GrpContNo,ProposalGrpContNo,ManageCom,AppntNo,GrpName from LCGrpCont where GrpContNo = '" +GrpContNo+ "'";
	turnPage.strQueryResult = easyQueryVer3(strSQL, 1, 0, 1);

  if(!turnPage.strQueryResult)
    {
    }
  else
  	{
  	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	  document.all('GrpContNo').value = turnPage.arrDataCacheSet[0][0];
	  document.all('ProposalGrpContNo').value = turnPage.arrDataCacheSet[0][1];
	  document.all('ManageCom').value = turnPage.arrDataCacheSet[0][2];
	  document.all('AppntNo').value = turnPage.arrDataCacheSet[0][3];
	  document.all('GrpName').value = turnPage.arrDataCacheSet[0][4];
  }
}

//单选框点击触发事件
function ShowContPlan(parm1,parm2){
	if(document.all(parm1).all('InpContPlanCodeGridSel').value == '1'){
		//当前行第1列的值设为：选中
		var cContPlanCode = document.all(parm1).all('ContPlanCodeGrid1').value;	//计划编码
		var cContPlanName = document.all(parm1).all('ContPlanCodeGrid2').value;	//计划名称
		var cPlanSql = document.all(parm1).all('ContPlanCodeGrid3').value;	//分类说明
		var cPeoples3 = document.all(parm1).all('ContPlanCodeGrid4').value;	//可保人数
		var cGrpContNo = document.all(parm1).all('ContPlanCodeGrid5').value;	//保单号
		document.all('ContPlanCode').value = cContPlanCode;
		document.all('ContPlanCode').readOnly=true;
		document.all('ContPlanName').value = cContPlanName;
		document.all('PlanSql').value = cPlanSql;
		document.all('Peoples3').value = cPeoples3;
		//alert(cContPlanCode);
		//var cGrpContNo = document.all('GrpContNo').value;

		//查询该险种下的险种计算要素
		
			var sqlid48="ContQuerySQL48";
			var mySql48=new SqlClass();
			mySql48.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
			mySql48.setSqlId(sqlid48);//指定使用的Sql的id
			mySql48.addSubPara(document.all('ContPlanCode').value);//指定传入的参数
			mySql48.addSubPara(cGrpContNo);//指定传入的参数
		    strSQL=mySql48.getString();	
		
//		strSQL = "select b.RiskName,a.RiskCode,a.DutyCode,c.DutyName,"
//		    + "a.CalFactor,a.FactorName,a.FactorNoti,d.CalFactorValue,d.Remark,"
//		    + "b.RiskVer,d.GrpPolNo,d.MainRiskCode,d.CalFactorType,c.CalMode "
//			+ "from LMRiskDutyFactor a, LMRisk b, LMDuty c, LCContPlanDutyParam d "
//			+ "where a.RiskCode = b.RiskCode and a.DutyCode = c.DutyCode and a.RiskCode = d.RiskCode "
//			+ "and a.DutyCode = d.DutyCode and a.CalFactor = d.CalFactor and b.RiskVer = d.RiskVersion "
//			+ "and ContPlanCode = '"+document.all('ContPlanCode').value+"'"
//			+ "and GrpContNO = '"+cGrpContNo+"' order by a.RiskCode,d.MainRiskCode,a.DutyCode";
		//document.all('PlanSql').value = strSQL;
		var arrResult=easyExecSql(strSQL);
		if(arrResult!=null)
		{
		    document.all('RiskCode').value=arrResult[0][1];
			document.all('RiskCodeName').value=arrResult[0][0];
			document.all('MainRiskCode').value=arrResult[0][11];
		}
		turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
		//原则上不会失败，嘿嘿
		if (!turnPage.strQueryResult) {
			alert("查询失败！");
			return false;
		}
	  
		//查询成功则拆分字符串，返回二维数组
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult,0,1);
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
		CheckRisk("0");
	}
}

//选择保险套餐
function showPlan(){
	var arrResult  = new Array();
	
				var sqlid48="ContQuerySQL48";
			var mySql48=new SqlClass();
			mySql48.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
			mySql48.setSqlId(sqlid48);//指定使用的Sql的id
			mySql48.addSubPara(document.all('RiskPlan').value);//指定传入的参数
		    strSQL=mySql48.getString();	
	
	//strSQL = "select a.ContPlanCode,a.ContPlanName,a.PlanSql,a.Peoples3,b.RiskCode,b.MainRiskCode from LDPlan a,LDPlanRisk b where a.ContPlanCode = b.ContPlanCode and a.ContPlanCode='"+document.all("RiskPlan").value+"'";

	var cGrpContNo = document.all('GrpContNo').value;

	arrResult =  decodeEasyQueryResult(easyQueryVer3(strSQL, 1, 0, 1));
	if(arrResult==null){
		alert("查询保险套餐数据失败！");
	}else{
		document.all('ContPlanCode').value = arrResult[0][0];
		document.all('ContPlanName').value = arrResult[0][1];
		document.all('PlanSql').value = arrResult[0][2];
		document.all('Peoples3').value = arrResult[0][3];
		document.all('RiskCode').value = arrResult[0][4];
		document.all('MainRiskCode').value = arrResult[0][5];
	}
	//查询该险种下的险种计算要素
	
			var sqlid49="ContQuerySQL49";
			var mySql49=new SqlClass();
			mySql49.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
			mySql49.setSqlId(sqlid49);//指定使用的Sql的id
			mySql49.addSubPara(document.all('RiskPlan').value);//指定传入的参数
			mySql49.addSubPara(cGrpContNo);//指定传入的参数
		    strSQL=mySql49.getString();	
	
//	strSQL = "select b.RiskName,a.RiskCode,a.DutyCode,c.DutyName,a.CalFactor,a.FactorName,a.FactorNoti,d.CalFactorValue,d.Remark,b.RiskVer,e.GrpPolNo,d.MainRiskCode,d.CalFactorType,c.CalMode "
//		+ "from LMRiskDutyFactor a, LMRisk b, LMDuty c, LDPlanDutyParam d,LCGrpPol e "
//		+ "where a.RiskCode = b.RiskCode and a.DutyCode = c.DutyCode and a.RiskCode = d.RiskCode "
//		+ "and a.DutyCode = d.DutyCode and a.CalFactor = d.CalFactor and b.RiskVer = d.RiskVersion "
//		+ "and ContPlanCode = '"+document.all('RiskPlan').value+"' "
//		+ "and e.GrpContNO = '"+cGrpContNo+"' and e.RiskCode = d.RiskCode  order by a.RiskCode,d.MainRiskCode,a.DutyCode";

	//document.all('PlanSql').value = strSQL;
	//alert(strSQL);
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	//原则上不会失败，嘿嘿
	if (!turnPage.strQueryResult) {
		alert("查询失败！");
		return false;
	}
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
	QueryCount = 1;
	tSearch = 1;
}

//计划变更则修改查询状态变量
function ChangePlan(){
	if(document.all('ContPlanCode').value=="00")
	  {
	  	alert("保障级别不能为00");
      document.all('ContPlanCode').focus();
	  	return false;
	  }
	  if(ContPlanCodeGrid.getSelNo()-1>=0)
	  {return true;}
	else
		{
	   QueryCount = 0;
     initContPlanDutyGrid();
     initContPlanGrid();
    }
}

function QueryAskPlanClick()
{
    initContPlanCodeGrid();

	//查询该保单的询价保单保险计划
	
				var sqlid50="ContQuerySQL50";
			var mySql50=new SqlClass();
			mySql50.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
			mySql50.setSqlId(sqlid50);//指定使用的Sql的id
			mySql50.addSubPara(document.all('GrpContNo').value);//指定传入的参数
		    strSQL=mySql50.getString();	
	
//	strSQL = "select ContPlanCode,ContPlanName,PlanSql,Peoples3,GrpContNo "
//		+ "from LCContPlan "
//		+ "where 1=1 "
//		+ "and GrpContNo ="
//		+ "(select ReportNo from LCGrpCont where GrpContNo='" + document.all('GrpContNo').value
//		+ "') and ContPlanCode <> '00'"
//		+" order by ContPlanCode";
	turnPage.queryModal(strSQL,ContPlanCodeGrid);	
}

function CheckPayRuleCode(tRiskCode,tPayRuleCode)
{
	
			var sqlid51="ContQuerySQL51";
			var mySql51=new SqlClass();
			mySql51.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
			mySql51.setSqlId(sqlid51);//指定使用的Sql的id
			mySql51.addSubPara(document.all('GrpContNo').value);//指定传入的参数
			mySql51.addSubPara(tRiskCode);//指定传入的参数
			mySql51.addSubPara(tPayRuleCode);//指定传入的参数
		    tStrSQL=mySql51.getString();	
	
//    tStrSQL = "select count(1) from lcpayrulefactory where GrpContNo='" + document.all('GrpContNo').value
//            + "' and RiskCode='" + tRiskCode + "' and PayRuleCode='" + tPayRuleCode + "'";
  	var arrResult = easyExecSql(tStrSQL,1,0);
  	if(!arrResult)
  	{
  	  	alert("所输入险种的缴费规则不存在，请先定义缴费规则！");
  	  	return false;
  	}
  	var tCount=arrResult[0][0];
  	if (tCount == '0')
  	{
  	    alert("所输入险种的缴费规则不存在，请先定义缴费规则！");
  	  	return false;
  	}
    return true;       
}

function CheckAscriptionRuleCode(tRiskCode,tAscriptionRuleCode)
{
	
				var sqlid52="ContQuerySQL52";
			var mySql52=new SqlClass();
			mySql52.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
			mySql52.setSqlId(sqlid52);//指定使用的Sql的id
			mySql52.addSubPara(document.all('GrpContNo').value);//指定传入的参数
			mySql52.addSubPara(tRiskCode);//指定传入的参数
			mySql52.addSubPara(tAscriptionRuleCode);//指定传入的参数
		    tStrSQL=mySql52.getString();	
	
//    tStrSQL = "select count(1) from lcascriptionrulefactory where GrpContNo='" + document.all('GrpContNo').value
//            + "' and RiskCode='" + tRiskCode + "' and AscriptionRuleCode='" + tAscriptionRuleCode + "'";
  	var arrResult = easyExecSql(tStrSQL,1,0);
  	if(!arrResult)
  	{
  	  	alert("所输入险种的归属规则不存在，请先定义归属规则！");
  	  	return false;
  	}
  	var tCount=arrResult[0][0];
  	if (tCount == '0')
  	{
  	    alert("所输入险种的归属规则不存在，请先定义归属规则！");
  	  	return false;
  	}
    return true;       
}