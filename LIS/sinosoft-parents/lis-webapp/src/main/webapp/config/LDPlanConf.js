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

function afterCodeSelect( cCodeName, Field ){
	//判定双击操作执行的是什么查询
	if (cCodeName=="RiskCode1"){
		var tRiskFlag = document.all('RiskFlag').value;
		//由于附加险不带出主险录入框，因此判定当主附险为S的时候隐藏
		if (tRiskFlag!="S"){
			//divmainriskname.style.display = '';
			//divmainrisk.style.display = '';
			document.all('MainRiskCode').value = document.all('RiskCode').value;
		}
		else{
			//divmainriskname.style.display = '';
			//divmainrisk.style.display = '';
			document.all('MainRiskCode').value = "";
		}
	}
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

//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug){
	if(cDebug=="1"){
		parent.fraMain.rows = "0,0,0,0,*";
	}
	else {
		parent.fraMain.rows = "0,0,0,82,*";
	}
	parent.fraMain.rows = "0,0,0,0,*";
}

//数据查询
function AddContClick(){
	if (document.all('ContPlanCode').value == ""){
		alert("请输入套餐代码！");
		document.all('ContPlanCode').focus();
		return false;
	}
	if(document.all('RiskCode').value ==""){
		alert("请选择险种！");
		document.all('RiskCode').focus();
		return false;
	}
  else{
//  	var risksql="select SubRiskFlag from lmriskapp where riskcode='"+document.all('RiskCode').value+"'"
  	
	var sqlid0="LDPlanConfSql0";
	var mySql0=new SqlClass();
	mySql0.setResourceName("config.LDPlanConfSql"); //指定使用的properties文件名
	mySql0.setSqlId(sqlid0);//指定使用的Sql的id
	mySql0.addSubPara(document.all('RiskCode').value);//指定传入的参数
	var risksql=mySql0.getString();

	  var arrRiskFlag = easyExecSql(risksql);
	  document.all('RiskFlag').value=arrRiskFlag[0][0];
	  if(document.all('RiskFlag').value=="M")
	  {
	  	document.all('MainRiskCode').value=document.all('RiskCode').value;
	  }
  }
	if(document.all('MainRiskCode').value ==""){
		alert("请输入主险信息！");
		//document.all('MainRiskCode').focus();
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
			alert("已添加过该险种保险套餐要素！");
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
	//alert(MainRiskCode);
	// 书写SQL语句
	var strSQL = "";
	
	if (QueryCount == 0){
		//查询该险种下的险种计算要素
//		strSQL = "select b.RiskName,a.RiskCode,a.DutyCode,c.DutyName,a.CalFactor,a.FactorName,a.FactorNoti,case a.CalFactorType when '1' then a.CalSql else '' end,'',b.RiskVer,'','"+MainRiskCode+"',a.CalFactorType,c.CalMode "
//			+ "from LMRiskDutyFactor a, LMRisk b, LMDuty c "
//			+ "where a.RiskCode = b.RiskCode and a.DutyCode = c.DutyCode "
//			+ "and a.DutyCode in "+ getWhere + " and a.ChooseFlag in ('0','2') "
//			+ "and  a.RiskCode = '"+document.all('RiskCode').value+"' order by a.RiskCode,a.DutyCode,a.FactorOrder";
		
		var sqlid1="LDPlanConfSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("config.LDPlanConfSql"); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(MainRiskCode);//指定传入的参数
		mySql1.addSubPara(getWhere);//指定传入的参数
		mySql1.addSubPara(document.all('RiskCode').value);//指定传入的参数
		strSQL=mySql1.getString();
		
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
//		strSQL = "select b.RiskName,a.RiskCode,a.DutyCode,c.DutyName,a.CalFactor,a.FactorName,a.FactorNoti,case a.CalFactorType when '1' then a.CalSql else '' end,'',b.RiskVer,'','',a.CalFactorType,c.CalMode "
//			+ "from LMRiskDutyFactor a, LMRisk b, LMDuty c "
//			+ "where a.RiskCode = b.RiskCode and a.DutyCode = c.DutyCode  "
//			+ "and a.DutyCode in "+ getWhere + " and a.ChooseFlag in ('0','2') "
//			+ " and a.RiskCode = '"+document.all('RiskCode').value+"' order by a.RiskCode,a.DutyCode,a.FactorOrder";
		
		var sqlid2="LDPlanConfSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("config.LDPlanConfSql"); //指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
		mySql2.addSubPara(getWhere);//指定传入的参数
		mySql2.addSubPara(document.all('RiskCode').value);//指定传入的参数
		strSQL=mySql2.getString();
		
		//document.all('ContPlanName').value = strSQL;
		turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
		//alert(strSQL);
		//alert(turnPage.strQueryResult);

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
function submitForm(){
	if (!beforeSubmit()){
		return false;
	}
	if(checkexists()==false) return false;
	if(checkCalFactor()==false) return false;
	document.all('mOperate').value = "INSERT||MAIN";
	if (document.all('mOperate').value == "INSERT||MAIN"){
		if (!confirm('套餐 '+document.all('ContPlanCode').value+' 下的全部险种要素信息是否已录入完毕，您是否要确认操作？')){
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
function returnparent(){
	parent.close();
}

function checkexists(){
   var tContPlanCode = fm.ContPlanCode.value;
//   var tSql ="select count(1) from ldplan where ContPlanCode='"+tContPlanCode+"'";
   
	var sqlid3="LDPlanConfSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName("config.LDPlanConfSql"); //指定使用的properties文件名
	mySql3.setSqlId(sqlid3);//指定使用的Sql的id
	mySql3.addSubPara(tContPlanCodes);//指定传入的参数
	var tSql=mySql3.getString();
   
   var tCount = easyExecSql(tSql);
   if(tCount>0){
       alert("编码为["+tContPlanCode+"]的套餐已经存在,请查看“已存在的保险套餐”！");
       return false;
   }
   return true;
}

//数据提交（删除）
function DelContClick(){
	if (document.all('ContPlanCode').value == ""){
		alert("请选则要删除的保险套餐！");
		document.all('ContPlanCode').focus();
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

	showInfo.focus();
	QueryCount = 0;	//重新初始化查询次数
	document.getElementById("fm").submit(); //提交
}

//数据提交（修改）
function UptContClick(){
	if (tSearch == 0){
		alert("请先查询要修改的保险套餐！");
		return false;
	}
	
	document.all('mOperate').value = "UPDATE||MAIN";
	if (!beforeSubmit()){
		return false;
	}
	if(checkCalFactor()==false) return false;
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

function getRiskCount(){
    var j=0;
    var k=0;
    var tRiskCode=new Array(); 
    for(i=0;i<ContPlanGrid.mulLineCount;i++)
    {
        var tGetCode = ContPlanGrid.getRowColData(i,2);
        if(tRiskCode[k]==tGetCode){
             continue;
        }else{
           tRiskCode[j]=tGetCode;
           k=j;
           j=j+1;
        }
    }
    return tRiskCode;
}
//数据校验
function beforeSubmit(){
	if (document.all('ContPlanCode').value == ""){
		alert("请输入套餐代码！");
		document.all('ContPlanCode').focus();
		return false;
	}
	if (document.all('mOperate').value != "UPDATE||MAIN"){
		if (document.all('MainRiskCode').value == ""){
			alert("请输入主险编码！");
			//document.all('MainRiskCode').focus();
			return false;
		}
	}
	if (ContPlanGrid.mulLineCount == 0){
		alert("请输入保险套餐详细信息");
		return false;
	}
	if  (document.all('ManageCom').value == ""){
	    alert("请选择管理机构");
	    return false;
	    }
	var lineCount = ContPlanGrid.mulLineCount;
	var sValue;
	var sCalMode;
	//添加要素值信息校验
	for(var i=0;i<lineCount;i++){
      	sValue = ContPlanGrid.getRowColData(i,8);
      	sCalMode = ContPlanGrid.getRowColData(i,5);
      	
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
	}
	return true;
}



function initFactoryType(tRiskCode)
{
	// 书写SQL语句
	var k=0;
	var strSQL = "";
//	strSQL = "select distinct a.FactoryType,b.FactoryTypeName,concat(a.FactoryType,"+tRiskCode+") from LMFactoryMode a ,LMFactoryType b  where 1=1 "
//		   + " and a.FactoryType= b.FactoryType "
//		   + " and (RiskCode = '"+tRiskCode+"' or RiskCode ='000000' )";
	
	var sqlid4="LDPlanConfSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName("config.LDPlanConfSql"); //指定使用的properties文件名
	mySql4.setSqlId(sqlid4);//指定使用的Sql的id
	mySql4.addSubPara(tRiskCode);//指定传入的参数
	mySql4.addSubPara(tRiskCode);//指定传入的参数
	strSQL=mySql4.getString();
	
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
	var newWindow = window.open("../app/ContPlanNextInput.jsp?GrpContNo="+document.all('GrpContNo').value+"&LoadFlag="+LoadFlag,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

}

function afterSubmit(FlagStr,content){
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
		content = "操作成功！";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
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
		alert("请输入套餐代码！");
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
//	strSQL = "select distinct a.DutyCode,b.DutyName,a.ChoFlag,case a.ChoFlag when 'M' then '必选' when 'B' then '备用' else '可选' end ChoFlagName "
//		+ "from LMRiskDuty a, LMDuty b ,LMRiskDutyFactor c "
//		+ "where a.DutyCode = b.DutyCode and a.RiskCode = c.RiskCode and a.DutyCode = c.DutyCode "
//		+ "and a.RiskCode = '"+document.all('RiskCode').value+"' order by a.DutyCode";
	
	var sqlid5="LDPlanConfSql5";
	var mySql5=new SqlClass();
	mySql5.setResourceName("config.LDPlanConfSql"); //指定使用的properties文件名
	mySql5.setSqlId(sqlid5);//指定使用的Sql的id
	mySql5.addSubPara(document.all('RiskCode').value);//指定传入的参数
	strSQL=mySql5.getString();
	
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
	  
		var sqlid6="LDPlanConfSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName("config.LDPlanConfSql"); //指定使用的properties文件名
		mySql6.setSqlId(sqlid6);//指定使用的Sql的id
		mySql6.addSubPara(document.all('RiskCode').value);//指定传入的参数
		mySql6.addSubPara(cDutyCode);//指定传入的参数
		tSql=mySql6.getString();
	  
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
//	strSQL = "select ContPlanCode,ContPlanName,PlanSql,Peoples3,remark,PlanKind1,PlanKind2,PlanKind3,managecom "
//		+ "from LDPlan "
//		+ "where 1=1 "
//		+ " and ContPlanCode <> '00' "
//		+ " and (managecom='86' or( managecom like concat('"+tManageCom+"','%')) or '"+tManageCom+"' like concat(managecom,'%'))"
//		+ " order by ContPlanCode";
	
	var sqlid7="LDPlanConfSql7";
	var mySql7=new SqlClass();
	mySql7.setResourceName("config.LDPlanConfSql"); //指定使用的properties文件名
	mySql7.setSqlId(sqlid7);//指定使用的Sql的id
	mySql7.addSubPara(tManageCom);//指定传入的参数
	mySql7.addSubPara(tManageCom);//指定传入的参数
	strSQL=mySql7.getString();
		
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
}

//单选框点击触发事件
function ShowContPlan(parm1,parm2){
	
	var selectRowNum = parm1.replace(/spanContPlanCodeGrid/g,"");
	
	if(document.all('InpContPlanCodeGridSel'+selectRowNum).value == '1' ){
		//当前行第1列的值设为：选中
		var cContPlanCode = document.all('ContPlanCodeGrid1'+'r'+selectRowNum).value;	//计划编码
		var cContPlanName = document.all('ContPlanCodeGrid2'+'r'+selectRowNum).value;	//计划名称
		var cPlanSql = document.all('ContPlanCodeGrid3'+'r'+selectRowNum).value;	//分类说明
		var cPeoples3 = document.all('ContPlanCodeGrid4'+'r'+selectRowNum).value;	//可保人数
		var cRemark=document.all('ContPlanCodeGrid5'+'r'+selectRowNum).value;//特别约定
		var cPlanKind1=document.all('ContPlanCodeGrid6'+'r'+selectRowNum).value;//套餐层级1
		var cPlanKind2=document.all('ContPlanCodeGrid7'+'r'+selectRowNum).value;//套餐层级2		
        var cPlanKind3=document.all('ContPlanCodeGrid8'+'r'+selectRowNum).value;//套餐层级3
        var cManageCom=document.all('ContPlanCodeGrid9'+'r'+selectRowNum).value;//管理机构
		document.all('ContPlanCode').value = cContPlanCode;
		document.all('ContPlanName').value = cContPlanName;
		document.all('PlanSql').value = cPlanSql;
		document.all('Peoples3').value = cPeoples3;
		document.all('Remark').value=cRemark;
		document.all('PlanKind1').value=cPlanKind1;
		document.all('PlanKind2').value=cPlanKind2;
		document.all('PlanKind3').value=cPlanKind3;
		document.all('ManageCom').value=cManageCom;
		//alert(cContPlanCode);

		//查询该险种下的险种计算要素
//		strSQL = "select b.RiskName,a.RiskCode,a.DutyCode,c.DutyName,a.CalFactor,a.FactorName,a.FactorNoti,d.CalFactorValue,d.Remark,b.RiskVer,'',d.MainRiskCode,d.CalFactorType,c.CalMode "
//			+ "from LMRiskDutyFactor a, LMRisk b, LMDuty c, LDPlanDutyParam d "
//			+ "where a.RiskCode = b.RiskCode and a.DutyCode = c.DutyCode and a.RiskCode = d.RiskCode "
//			+ "and a.DutyCode = d.DutyCode and a.CalFactor = d.CalFactor and b.RiskVer = d.RiskVersion "
//			+ "and d.ContPlanCode = '"+document.all('ContPlanCode').value+"'"
//			+ "  order by a.RiskCode,d.MainRiskCode,a.DutyCode";
		
		var sqlid8="LDPlanConfSql8";
		var mySql8=new SqlClass();
		mySql8.setResourceName("config.LDPlanConfSql"); //指定使用的properties文件名
		mySql8.setSqlId(sqlid8);//指定使用的Sql的id
		mySql8.addSubPara(document.all('ContPlanCode').value);//指定传入的参数
		strSQL=mySql8.getString();
		
		//document.all('PlanSql').value = strSQL;
		
		// Update by YaoYi for bug.
		turnPage.queryModal(strSQL, ContPlanGrid);
		
		

		//原则上不会失败，嘿嘿
//		turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
//		if (!turnPage.strQueryResult) {
//			alert("查询失败！");
//			return false;
//		}
		//查询成功则拆分字符串，返回二维数组
//		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		//设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
//		turnPage.pageDisplayGrid = ContPlanGrid;
		//保存SQL语句
//		turnPage.strQuerySql = strSQL;
		//设置查询起始位置
//		turnPage.pageIndex = 0;
		//在查询结果数组中取出符合页面显示大小设置的数组
		//调用MULTILINE对象显示查询结果
//		displayMultiline(turnPage.arrDataCacheSet, turnPage.pageDisplayGrid);
		QueryCount = 1;
		tSearch = 1;
	}
}

function checkCalFactor(){
   //校验计划要素的值是否符合后台计算要求
   var tCalRule="";
   var tCalRuleInfo="";//判断计算规则是否可录
   var tFloatRate="";
   var tFloatRateInfo="";//判断费率是否可录
   var tAmnt="";
   var tAmntInfo="";//判断保额是否可录
   var tPrem="";
   var tPremInfo="";//判断保费是否可录
   var tCalFactor="";
   var tRiskCode=getRiskCount();//得到计划有多少险种组成 多个险种的保障计划要分别对每个险种的要素进行校验
   for(j=0;j<tRiskCode.length;j++){
        var tNowRiskCode = tRiskCode[j];
   		var tRiskName = "";
  		 for(i=0;i<ContPlanGrid.mulLineCount;i++)
  		 {
  		  if(tNowRiskCode==ContPlanGrid.getRowColData(i,2)){
  		  tRiskName = ContPlanGrid.getRowColData(i,1);
  		    tCalFactor = ContPlanGrid.getRowColData(i,5);
  	    //alert("tCalFactor:"+tCalFactor);
  		      if(tCalFactor=="CalRule")
   		      {
      		       tCalRule = ContPlanGrid.getRowColData(i,8);
      		       tCalRuleInfo = ContPlanGrid.getRowColData(i,7);
      		       //alert("tCalRule:"+tCalRule);
             //alert("tCalRuleInfo:"+tCalRuleInfo);
     		    }
     		    if(tCalFactor=="Prem")
    		     {
   		          tPrem = ContPlanGrid.getRowColData(i,8);
    		         tPremInfo = ContPlanGrid.getRowColData(i,6);
    		         //alert("tPrem:"+tPrem);
    		     }
    		     if(tCalFactor=="Amnt")
    		     {
    		         tAmnt = ContPlanGrid.getRowColData(i,8);
     	        tAmntInfo = ContPlanGrid.getRowColData(i,6);
             //alert("tAmnt:"+tAmnt);
      		   }
     		    if(tCalFactor=="FloatRate")
      		   {
     		        tFloatRate = ContPlanGrid.getRowColData(i,8);
     		        tFloatRateInfo = ContPlanGrid.getRowColData(i,6);
            // alert("tFloatRate:"+tFloatRate);
      		   }
      
   		 }
   		}
  		//0-表定费率 1-约定费率 2-表定费率折扣 3-约定保费保额
   		if(tCalRuleInfo!=""){
  		 if(tCalRule==""){
  		   alert(tRiskName+"!请录入计算规则！“"+tCalRuleInfo+"”");
   		   return false;
		   }else{
		     if(tCalRule=="1")
		     {
      //约定费率  保费=保额*浮动费率
      		if(tAmntInfo!=""&&tPremInfo!=""&&tFloatRateInfo!=""){
        //能够录入保额、保费、费率时才对这种计算规则做如下校验
      			if(tAmnt==""||tPrem==""||tFloatRate==""){
     			    alert(tRiskName+"!请录入保费、保额、浮动费率！");
     			    return false;
     			 }else{
     			    var tCalPrem=tAmnt*tFloatRate;
    			     if(tCalPrem!=tPrem){
    			          alert(tRiskName+"!录入的保费"+tPrem+"与计算出来的保费"+tCalPrem+"不等,请重新录入！");
    			          return false;         
    			     }
    		 	 }
    		 	}
   			 }else if(tCalRule=="2"){
   		     //表定费率折扣 只录入保额、浮动费率
   			     if(tAmntInfo!=""&&tPremInfo!=""&&tFloatRateInfo!=""){
   	     //能够录入保额、保费、费率时才对这种计算规则做如下校验
   			     if(tPrem==""){
   	 		      if(tAmnt==""||tFloatRate==""){
   	  		         alert(tRiskName+"!请录入保额、浮动费率！");
   	 		          return false;
   	 		      }
   	 		    }else{
   	 		       alert(tRiskName+"!“表定费率折扣”规则不能录入保费！");
   	 	       return false;
   			     }
   			    }
  		 	 }else if(tCalRule=="3"){
  		 	 if(tAmntInfo!=""&&tPremInfo!=""&&tFloatRateInfo!=""){
   	 //能够录入保额、保费、费率时才对这种计算规则做如下校验
   			      if(tFloatRate==""){
   	 		       if(tAmnt==""||tPrem==""){
   	 		          alert(tRiskName+"!请录入保费、保额！");
   	 		          return false;
   	 		       }
   	 		     }else{
   			        alert(tRiskName+"!“约定保费保额”规则不能录入浮动费率！");
   	 		       return false;
   			      }
   	 		    }
 		  	 }else if(tCalRule=="0"){
 		  	 if(tFloatRateInfo!=""){
 	  	 //能够录入费率时在会做如下校验
 		  	      if(tFloatRate!=""){
  		 	        alert(tRiskName+"!“表定费率”规则不能录入浮动费率！");
  		 	        return false;
  		 	      }
  		 	      if(tAmnt==""&&tPrem==""){
  		 	        alert(tRiskName+"!“表定费率”保额、保费不能同时为空！");
  		 	        return false;
  		 	      }
  		 	     }
 		  	 }else{
  		 	     alert(tRiskName+"!录入的计算规则不合法,请重新录入！“"+tCalRuleInfo+"”");
  		 	     return false;
 		  	 }
 		  }
 		  //清空所有校验内容 校验其他险种要素
 		  var tCalRule="";
   		  var tCalRuleInfo="";//判断计算规则是否可录
          var tFloatRate="";
          var tFloatRateInfo="";//判断费率是否可录
          var tAmnt="";
          var tAmntInfo="";//判断保额是否可录
          var tPrem="";
          var tPremInfo="";//判断保费是否可录
          var tCalFactor="";
  		 }else{
  		   //校验221702、221703附加团体每日住院给付保险
  		   if(tNowRiskCode!=""&&(tNowRiskCode=="221702"||tNowRiskCode=="221703")){
  		     var tMult=document.all('InsuMult').value;
  		     if(tMult!=""&&tMult=="1"){
  		       //一份时 保额必须是30或50
  		       if(tAmnt!="30"&&tAmnt!="50"){
  		         alert(tRiskName+"!当投保份数为 1 时“保险金额”只能为30或50");
  		         return false;
  		       }
  		     }else{
  		       //为多份时 校验保险金额是否为30或50 的整数倍
  		       if(Math.round(tAmnt/tMult) == tAmnt/tMult  )
   				{
   					if(tAmnt/tMult == 30  || tAmnt/tMult == 50)
   					{
   					alert(tRiskName+"!您录入的保额"+tAmnt+"是总保额,单份保额为"+tAmnt/tMult+ "元");
					//if( !confirm(tRiskName+"您录入的保额"+tAmnt+"是总保额,单份保额为"+tAmnt/tMult+ "元,是否保存!" ))
					 // return false;
   		 		   }else{
   		  		  	alert(tRiskName+"!保险金额不是30元或50元的整数倍,请检查!");
						return false;
   		 		   }
   		    
   				}
   				else{
   					alert(tRiskName+"!保险金额不是30元或50元的整数倍,请检查!");
					return false;
   				}
  		     }
  		   }
  		 }
   }
   return true;
}

function getcodedata2()
{
//	var sql="select RiskCode, RiskName from LMRiskApp where enddate is null  and riskprop='G' "
//	         +" union select riskcode,(select riskname from lmrisk where riskcode=lmriskcomctrl.riskcode) from LMRiskComCtrl where "
//	         +" enddate is null  "
//	         +"  and (select distinct(riskprop) from lmriskapp where riskcode =lmriskcomctrl.riskcode)='G' order by RiskCode";
	
//	var sql="select RiskCode, RiskName from LMRiskApp where (enddate is null or enddate>'"+fm.sysdate.value+"') and riskprop in ('G','D') "
//    +" union select riskcode,(select riskname from lmrisk where riskcode=lmriskcomctrl.riskcode) from LMRiskComCtrl where "
//    +" startdate<='"+fm.sysdate.value+"' and (enddate is null or enddate>'"+fm.sysdate.value+"') and ManageComGrp='"+tManageCom+"' "
//    +"  and (select distinct(riskprop) from lmriskapp where riskcode =lmriskcomctrl.riskcode) in ('G','D') order by RiskCode";
	
	
	var sqlid9="LDPlanConfSql9";
	var mySql9=new SqlClass();
	mySql9.setResourceName("config.LDPlanConfSql"); //指定使用的properties文件名
	mySql9.setSqlId(sqlid9);//指定使用的Sql的id
	mySql9.addSubPara(fm.sysdate.value);//指定传入的参数
	mySql9.addSubPara(fm.sysdate.value);//指定传入的参数
	mySql9.addSubPara(fm.sysdate.value);//指定传入的参数
	mySql9.addSubPara(tManageCom);//指定传入的参数
	var sql=mySql9.getString();
	
	var tCodeData = "0|";
	turnPage.strQueryResult  = easyQueryVer3(sql, 1, 0, 1);
	//prompt("查询险种代码:",sql);
    if (turnPage.strQueryResult != "")
    {
    	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    	m = turnPage.arrDataCacheSet.length;
    	for (i = 0; i < m; i++)
    	{
    		j = i + 1;
    		tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
    	}
    }
    //alert(tCodeData);
    document.all("RiskCode").CodeData=tCodeData;
	
}

//计划变更则修改查询状态变量
function ChangePlan(){
	QueryCount = 0;
    initContPlanDutyGrid();
    initContPlanGrid();
}