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
	PayRuleNewGrid.delBlankLine("PayRuleGrid");
	if(PayRuleNewGrid.mulLineCount==0){
		alert("没有数据信息！");
		return false;
	}

	if (!beforeSubmit()){
		return false;
	}
	document.all('mOperate').value = "INSERT||MAIN";
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
	initPayRuleGrid();
}

//返回上一步
function returnparent(){
	if (AskFlag == 0)
    {
	    window.location.href = "GrpFeeInput.jsp?ProposalGrpContNo="+GrpContNo+"&LoadFlag="+LoadFlag;
	}
    else if (AskFlag == 1)
    {
        //询价保单缴费定义
        window.location.href = "../askapp/AskGrpFeeInput.jsp?ProposalGrpContNo="+GrpContNo+"&LoadFlag="+LoadFlag;
    }
}

//数据提交（删除）
function DelContClick(){
    if (confirm("确定要删除此缴费规则吗？") == true)
    {
    	PayRuleNewGrid.delBlankLine("PayRuleGrid");
    //	alert(PayRuleNewGrid.mulLineCount);
    	if(PayRuleNewGrid.mulLineCount==0){
    		alert("没有数据信息！");
    		return false;
    	}
    	
    	var tSelNo = PayRuleOldGrid.getSelNo();
    	if (tSelNo <= 0)
    	{
    	    alert("请选择需要删除的缴费规则！");
    	    return;    
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
    	document.getElementById("fm").submit(); //提交
    }
}

//数据校验
function beforeSubmit(){
	if(document.all('PayRuleCode').value.length==0)
	{
		alert("请输入员工类别");
		return false;
	}
	//判断缴费规则的所需参数是否完整
	//得到Muillen的行数和列数
	var rowNum=PayRuleNewGrid.mulLineCount ; //行数
        var colNum=PayRuleNewGrid.colCount ;     //列数
        //alert(rowNum);
        //alert(colNum);
	for(var i=0;i<rowNum;i++)
	{
		for(var j=1;j<=6;j++)
		{
			
			if(PayRuleNewGrid.getRowColData(i,j)=="")
			{			
				alert("数据信息不完整,请确认数据的完整性！");
				return false;
			}
		}

		var varParam=PayRuleNewGrid.getRowColData(i,6);
		
		var strParam=new String(varParam);
		var iArray = new Array();
		iArray=strParam.split(',');	
		for(var p=0;p<iArray.length-1;p++)
		{
			strTemp=new String(iArray[p]);
			for(var k=0;k<strTemp.length;k++)
			{
				var aa=strTemp.charAt(k);
				
				if(!(aa>=0&&aa<=9))
				{
					alert("数据信息不完整,请检查数据格式是否正确！");
					return false;
				}
			}
		}
		if(!(parseInt(iArray[0])<parseInt(iArray[1])))
		{
			alert("数据信息不完整,请检查数据格式是否正确！");
			return false;			
		}
		if(!(iArray[2]>=0))
		{
			alert("数据信息不完整,请检查数据格式是否正确！");
			return false;	
		}

	}
	return true;
}

function GrpPerPolDefine(){
	// 初始化表格
	var str = "";
	var tGrpContNo = GrpContNo;

	var tImpPayRuleCode = initPayRuleNew(tGrpContNo);
	//初始化数组
	initPayRuleNewGrid(tImpPayRuleCode);

	divPayRule.style.display= "";
	var strSQL = "";

	//strSQL = "select RiskCode,FactoryType,OtherNo,FactoryCode||to_char(FactorySubCode),CalRemark,Params,FactoryName,trim(FactoryType)||trim(RiskCode),GrpPolNo " 
	//	+ "from LCPayRuleFactory where 1=1 "
	//	+ "and GrpContNo='" +tGrpContNo+ "' "
	//	+ "order by FactoryType, OtherNo,FactoryCode,FactorySubCode ";
	//document.write(strSQL);
	//turnPage = new turnPageClass();
	//turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);

	//判断是否查询成功
	if (turnPage.strQueryResult) {
		//查询成功则拆分字符串，返回二维数组
		//turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		//设置初始化过的MULTILINE对象
		//turnPage.pageDisplayGrid = PayRuleGrid;
		//保存SQL语句
		//turnPage.strQuerySql     = strSQL;
		//设置查询起始位置
		//turnPage.pageIndex = 0;
		//在查询结果数组中取出符合页面显示大小设置的数组
		//arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
		//调用MULTILINE对象显示查询结果
		//displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

		//strSQL = "select PayRuleCode,PayRuleName from LCPayRuleFactory where GrpContNo = '" +GrpContNo+ "'";
		//turnPage.strQueryResult = easyQueryVer3(strSQL, 1, 0, 1);
		//turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		//document.all('PayRuleCode').value = turnPage.arrDataCacheSet[0][0];
		//document.all('PayRuleName').value = turnPage.arrDataCacheSet[0][1];
	}

		 var sqlid89="ContQuerySQL89";
		var mySql89=new SqlClass();
		mySql89.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
		mySql89.setSqlId(sqlid89);//指定使用的Sql的id
		mySql89.addSubPara(GrpContNo);//指定传入的参数
	    strSQL=mySql89.getString();	


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
	var tImpPayRuleOldCode = initPayRuleOld(tGrpContNo);
	//alert("is me");
	initPayRuleOldGrid(tImpPayRuleOldCode);
	//alert("not here");

	divPayRuleOld.style.display= "";
	var strSQL = "";

		 var sqlid90="ContQuerySQL90";
		var mySql90=new SqlClass();
		mySql90.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
		mySql90.setSqlId(sqlid90);//指定使用的Sql的id
		mySql90.addSubPara(GrpContNo);//指定传入的参数
	    strSQL=mySql90.getString();	

	//strSQL = "select distinct payrulecode,payrulename from lcpayrulefactory a where a.GrpContNo='"+tGrpContNo+"' order by payrulecode"; 
	turnPage = new turnPageClass();
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	if (turnPage.strQueryResult) {
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		turnPage.pageDisplayGrid = PayRuleOldGrid;
		turnPage.strQuerySql     = strSQL;
		turnPage.pageIndex = 0;
		arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
		displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
	}
        return ;
}


function initPayRuleNew(tGrpContNo){
	// 书写SQL语句
	var k=0;
	var strSQL = "";

	/*strSQL = "select distinct a.FactoryType,b.FactoryTypeName,a.FactoryType||"+tGrpContNo+" from LMFactoryMode a ,LMFactoryType b  where 1=1 "
	+ " and a.FactoryType= b.FactoryType "
	+ " and (RiskCode =('"+tGrpContNo+"' ) or RiskCode ='000000' )";
	*/
	//alert(GrpContNo);
	
		var sqlid91="ContQuerySQL91";
		var mySql91=new SqlClass();
		mySql91.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
		mySql91.setSqlId(sqlid91);//指定使用的Sql的id
		mySql91.addSubPara(GrpContNo);//指定传入的参数
	    strSQL=mySql91.getString();	
	
	//strSQL = "select a.RiskCode,b.RiskName,a.GrpPolNo from LCGrpPol a,LMRisk b where a.GrpContNo='"+GrpContNo+"' and a.RiskCode = b.RiskCode";
	//document.all('ff').value=strSQL;
	var str  = easyQueryVer3(strSQL, 1, 0, 1);
	//alert(str);
    return str;
}
function initPayRuleOld(tGrpContNo){
	// 书写SQL语句
	var k=0;
	var strSQL = "";

		var sqlid92="ContQuerySQL92";
		var mySql92=new SqlClass();
		mySql92.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
		mySql92.setSqlId(sqlid92);//指定使用的Sql的id
		mySql92.addSubPara(tGrpContNo);//指定传入的参数
	    strSQL=mySql92.getString();	


	//strSQL = "select payrulecode,payrulename from lcpayrulefactory where lcpayrulefactory.GrpContNo='tGrpContNo'";
	var str  = easyQueryVer3(strSQL, 1, 0, 1);
	//alert(str);
    return str;
}
//单选框点击触发事件
function ShowPayRule(parm1,parm2){
		//当前行第1列的值设为：选中
		//alert("not me");
		var tGrpContNo= GrpContNo;
		//alert(GrpContNo);
		var cPayRuleCode = document.all(parm1).all('PayRuleOldGrid1').value;	//计划编码
		//alert("cPayRuleCode");
	document.all('PayRuleCode').value = cPayRuleCode;
	document.all('PayRuleName').value = document.all(parm1).all('PayRuleOldGrid2').value;
	document.all('PayRuleCodeOld').value = cPayRuleCode;
	document.all('PayRuleNameOld').value = document.all(parm1).all('PayRuleOldGrid2').value;
               var strSQL="";
         /*
                      strSQL="select PayRulecode,payRulename,(select payplanname from lmdutypay where payplancode=lcpayrulefactory.otherno),Calremark,params from lcpayrulefactory where payrulecode='"+cPayRuleCode+"' and lcpayrulefactory.GrpContNo='"+tGrpContNo+"'";
             
                   //   document.write(strSQL);
                      	turnPage = new turnPageClass();
	        turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	//alert("not me");
        
	//判断是否查询成功
	if (turnPage.strQueryResult) {
	         // alert("result is right");
		//查询成功则拆分字符串，返回二维数组
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		//设置初始化过的MULTILINE对象
		turnPage.pageDisplayGrid = PayRuleGrid;
		//保存SQL语句
		turnPage.strQuerySql     = strSQL;
		//设置查询起始位置
		turnPage.pageIndex = 0;
		//在查询结果数组中取出符合页面显示大小设置的数组
		arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
		//调用MULTILINE对象显示查询结果
		displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
		QueryCount = 1;
		tSearch = 1;
	}
	*/
	
			var sqlid93="ContQuerySQL93";
		var mySql93=new SqlClass();
		mySql93.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
		mySql93.setSqlId(sqlid93);//指定使用的Sql的id
		mySql93.addSubPara(document.all('GrpContNo').value);//指定传入的参数
		mySql93.addSubPara(cPayRuleCode);//指定传入的参数
	    strSQL=mySql93.getString();	
	
//	strSQL="select riskcode,factorytype,otherno,factorycode||to_char(factorysubcode),calremark,params,factoryname,trim(FactoryType)||trim(RiskCode),grppolno from lcpayrulefactory "
//		+ "where grpcontno='"+document.all('GrpContNo').value+"' "
//		+ "and payrulecode='"+cPayRuleCode+"'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	if (turnPage.strQueryResult) {
	         // alert("result is right");
		//查询成功则拆分字符串，返回二维数组
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		//设置初始化过的MULTILINE对象
		turnPage.pageDisplayGrid = PayRuleNewGrid;
		//保存SQL语句
		turnPage.strQuerySql     = strSQL;
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
		initForm();
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	
		showInfo.focus();
	}
}

function updateClick(){
	PayRuleNewGrid.delBlankLine("PayRuleGrid");
	if(PayRuleNewGrid.mulLineCount==0){
		alert("没有数据信息！");
		return false;
	}

	if (!beforeSubmit()){
		return false;
	}
	document.all('mOperate').value = "UPDATE||MAIN";
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
	initPayRuleGrid();
}