//该文件中包含客户端需要处理的函数和事件
var mDebug="0";
var mOperate="";
var showInfo;
var arrDataSet;
var turnPage = new turnPageClass();
var QueryResult="";
var QueryCount = 0;
var mulLineCount = 0;
var QueryWhere="";
window.onfocus=myonfocus;

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

//数据提交（保存）
function submitForm(){
	//alert("submitForm start");
	AscriptionRuleNewGrid.delBlankLine("AscriptionRuleGrid");
	//alert(AscriptionRuleNewGrid.mulLineCount);
	if(AscriptionRuleNewGrid.mulLineCount==0){
		alert("没有数据信息！");
		return false;
	}

	if (!beforeSubmit()){
		return false;
	}
	document.all('mOperate').value = "INSERT||MAIN";
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	document.getElementById("fm").submit(); //提交
	initAscriptionRuleGrid();
}

//返回上一步
function returnparent(){
	window.location.href = "GrpFeeInput.jsp?ProposalGrpContNo="+GrpContNo+"&LoadFlag="+LoadFlag;
}

//数据提交（删除）
function DelContClick(){
	if (confirm("确定要删除此归属规则吗？") == true)
    {
    	AscriptionRuleNewGrid.delBlankLine("AscriptionRuleGrid");
    //	alert(AscriptionRuleNewGrid.mulLineCount);
    	if(AscriptionRuleNewGrid.mulLineCount==0){
    		alert("没有数据信息！");
    		return false;
    	}
    
    	document.all('mOperate').value = "DELETE||MAIN";
    	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    	showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
    	document.getElementById("fm").submit(); //提交
    }
}

//数据校验
function beforeSubmit(){
	if(document.all('AscriptionRuleCode').value.length==0)
	{
		alert("请输入员工类别");
		return false;
	}
	//判断缴费规则的所需参数是否完整
	//得到Muillen的行数和列数
	var rowNum=AscriptionRuleNewGrid.mulLineCount ; //行数
        var colNum=AscriptionRuleNewGrid.colCount ;     //列数
        //alert(rowNum);
        //alert(colNum);
	for(var i=0;i<rowNum;i++)
	{
		for(var j=1;j<=6;j++)
		{
			
			//alert("("+i+","+j+")  "+AscriptionRuleNewGrid.getRowColData(i,j));
			if(AscriptionRuleNewGrid.getRowColData(i,j)=="")
			{
				alert("数据信息不完整,请确认数据的完整性！");
				return false;
			}
		}
		
		var varParam=AscriptionRuleNewGrid.getRowColData(i,6);
		//alert("varParam"+varParam);
		var strParam=new String(varParam);
		var iArray = new Array();
		iArray=strParam.split(',');
		//alert(iArray[0]);
		//alert(iArray[1]);
		//alert(iArray[2]);
		for(var p=0;p<iArray.length-1;p++)
		{
			strTemp=new String(iArray[p]);
			for(var k=0;k<strTemp.length;k++)
			{
				var aa=strTemp.charAt(k);
				//alert(aa);
				if(!(aa>=0&&aa<=9))
				{
					alert("数据信息不完整,请检查数据格式是否正确！");
					return false;
				}
			}
		}
		if(!(parseInt(iArray[0])<parseInt(iArray[1])))
		{
			alert("要素值输入错误,请检查数据确！");
			return false;			
		}
		
		if(!(iArray[2]>=0 && iArray[2]<=1))
		{
			alert("归属比例不能小于0大于1，请正确输入！");
			return false;	
		}

	}
	return true;
}

function GrpPerPolDefine(){
	// 初始化表格
	//alert("GrpPerPolDefine 1");
	var str = "";
	var tGrpContNo = GrpContNo;

	var tImpAscriptionRuleCode = initAscriptionRuleNew(tGrpContNo);
	//alert(tImpAscriptionRuleCode);
	//初始化数组
	initAscriptionRuleNewGrid(tImpAscriptionRuleCode);
       // alert("GrpPerPolDefine 3");
	divAscriptionRule.style.display= "";
	var strSQL = "";

		  var sqlid53="ContQuerySQL53";
		var mySql53=new SqlClass();
		mySql53.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
		mySql53.setSqlId(sqlid53);//指定使用的Sql的id
		mySql53.addSubPara(GrpContNo);//指定传入的参数
	    strSQL=mySql53.getString();

	//strSQL = "select GrpContNo,ProposalGrpContNo,ManageCom,AppntNo,GrpName from LCGrpCont where GrpContNo = '" +GrpContNo+ "'";
	turnPage.strQueryResult = easyQueryVer3(strSQL, 1, 0, 1);
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	document.all('GrpContNo').value = turnPage.arrDataCacheSet[0][0];
	document.all('ProposalGrpContNo').value = turnPage.arrDataCacheSet[0][1];
	document.all('ManageCom').value = turnPage.arrDataCacheSet[0][2];
	document.all('AppntNo').value = turnPage.arrDataCacheSet[0][3];
	document.all('GrpName').value = turnPage.arrDataCacheSet[0][4];
	return ;
}

function GrpPerPolDefineOld(){
	// 初始化表格
	var str = "";
	var tGrpContNo = GrpContNo;
	var tImpAscriptionRuleOldCode = initAscriptionRuleOld(tGrpContNo);
	//alert("is me");
	initAscriptionRuleOldGrid(tImpAscriptionRuleOldCode);
	//alert("not here");

	divAscriptionRuleOld.style.display= "";
	var strSQL = "";


		  var sqlid54="ContQuerySQL54";
		var mySql54=new SqlClass();
		mySql54.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
		mySql54.setSqlId(sqlid54);//指定使用的Sql的id
		mySql54.addSubPara(tGrpContNo);//指定传入的参数
	    strSQL=mySql54.getString();

	//strSQL = "select distinct Ascriptionrulecode,Ascriptionrulename from lcAscriptionrulefactory a where a.GrpContNo='"+tGrpContNo+"' order by Ascriptionrulecode"; 
	turnPage = new turnPageClass();
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	if (turnPage.strQueryResult) {
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		turnPage.pageDisplayGrid = AscriptionRuleOldGrid;
		turnPage.strQuerySql     = strSQL;
		turnPage.pageIndex = 0;
		arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
		displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
	}
        return ;
}


function initAscriptionRuleNew(tGrpContNo){
	// 书写SQL语句
	var k=0;
	var strSQL = "";

	//strSQL = "select distinct a.FactoryType,b.FactoryTypeName,a.FactoryType||"+tGrpContNo+" from LMFactoryMode a ,LMFactoryType b  where 1=1 "
	//+ " and a.FactoryType= b.FactoryType "
	//+ " and (RiskCode =('"+tGrpContNo+"' ) or RiskCode ='000000' )";
	
	//alert(GrpContNo);
	
			  var sqlid55="ContQuerySQL55";
		var mySql55=new SqlClass();
		mySql55.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
		mySql55.setSqlId(sqlid55);//指定使用的Sql的id
		mySql55.addSubPara(GrpContNo);//指定传入的参数
	    strSQL=mySql55.getString();
	
	//strSQL = "select a.RiskCode,b.RiskName,a.GrpPolNo from LCGrpPol a,LMRisk b where a.GrpContNo='"+GrpContNo+"' and a.RiskCode = b.RiskCode";
	//document.all('ff').value=strSQL;
	var str  = easyQueryVer3(strSQL, 1, 0, 1);
	//alert(str);
    return str;
}
function initAscriptionRuleOld(tGrpContNo){
	// 书写SQL语句
	var k=0;
	var strSQL = "";

		var sqlid56="ContQuerySQL56";
		var mySql56=new SqlClass();
		mySql56.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
		mySql56.setSqlId(sqlid56);//指定使用的Sql的id
		mySql56.addSubPara(tGrpContNo);//指定传入的参数
	    strSQL=mySql56.getString();

	//strSQL = "select Ascriptionrulecode,Ascriptionrulename from lcAscriptionrulefactory where lcAscriptionrulefactory.GrpContNo='tGrpContNo'";
	var str  = easyQueryVer3(strSQL, 1, 0, 1);
	//alert(str);
    return str;
}
//单选框点击触发事件
function ShowAscriptionRule(parm1,parm2){
	//当前行第1列的值设为：选中
	var tGrpContNo= GrpContNo;
	//alert(GrpContNo);
	var cAscriptionRuleCode = document.all(parm1).all('AscriptionRuleOldGrid1').value;	//计划编码
	document.all('AscriptionRuleCode').value = cAscriptionRuleCode;
	document.all('AscriptionRuleName').value = document.all(parm1).all('AscriptionRuleOldGrid2').value;
	document.all('AscriptionRuleCodeOld').value = cAscriptionRuleCode;
	document.all('AscriptionRuleNameOld').value = document.all(parm1).all('AscriptionRuleOldGrid2').value;
	//alert("cAscriptionRuleCode");
    var strSQL = "";
     
	 		var sqlid57="ContQuerySQL57";
		var mySql57=new SqlClass();
		mySql57.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
		mySql57.setSqlId(sqlid57);//指定使用的Sql的id
		mySql57.addSubPara( document.all('GrpContNo').value);//指定传入的参数
		mySql57.addSubPara( cAscriptionRuleCode);//指定传入的参数
	    strSQL=mySql57.getString();
	        
//	strSQL = "select riskcode,factorytype,otherno,factorycode||to_char(factorysubcode),calremark,params,factoryname,trim(FactoryType)||trim(RiskCode),grppolno from lcascriptionrulefactory "
//		   + "where grpcontno='"
//		   + document.all('GrpContNo').value + "' "
//		   + "and ascriptionrulecode='" + cAscriptionRuleCode + "'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	if (turnPage.strQueryResult) {
	         // alert("result is right");
		//查询成功则拆分字符串，返回二维数组
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		//设置初始化过的MULTILINE对象
		turnPage.pageDisplayGrid = AscriptionRuleNewGrid;
		//保存SQL语句
		turnPage.strQuerySql = strSQL;
		//设置查询起始位置
		turnPage.pageIndex = 0;
		//在查询结果数组中取出符合页面显示大小设置的数组
		arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
		//调用MULTILINE对象显示查询结果
		displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
		QueryCount = 1;
		tSearch = 1;
	}
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
		initForm();
	}
}

function updateClick(){
	AscriptionRuleNewGrid.delBlankLine("AscriptionRuleGrid");
	if(AscriptionRuleNewGrid.mulLineCount == 0)
	{
		alert("没有数据信息！");
		return false;
	}

	if (!beforeSubmit()){
		return false;
	}
	document.all('mOperate').value = "UPDATE||MAIN";
	var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	document.getElementById("fm").submit(); //提交
	initAscriptionRuleGrid();
}