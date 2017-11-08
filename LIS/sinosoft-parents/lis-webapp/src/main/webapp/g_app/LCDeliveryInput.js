/***************************************************************
 * <p>ProName：LCDeliveryInput.js</p>
 * <p>Title：递送登记</p>
 * <p>Description：递送登记</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-05-07
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();

var tSQLInfo = new SqlClass();
var mOperate = '';


//查询保单
function queryClick(){
	if(""!=fm.PrintStartDate.value && ""!=fm.PrintEndDate.value){
		
		if(fm.PrintStartDate.value>fm.PrintEndDate.value){
			alert("打印起期应早于打印止期!");
			return false;
		}
	}
	var mManageCom=fm.ManageCom.value;
	var mGrpPropNo=fm.GrpPropNo.value;
	var mGrpContNo=fm.GrpContNo.value;
	var mGrpName=fm.GrpName.value;
	var mContState=fm.ContState.value;
	if(mContState==null||mContState==''){
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_app.LCPostalInfoSql");
		if(_DBT==_DBO){
			tSQLInfo.setSqlId("LCPostalInfoSql1");
	   }else if(_DBT==_DBM)
	   {
		   tSQLInfo.setSqlId("LCPostalInfoSql11");
	   }
		tSQLInfo.addSubPara(tManageCom);
		tSQLInfo.addSubPara(mManageCom);
		tSQLInfo.addSubPara(mGrpPropNo);
		tSQLInfo.addSubPara(mGrpContNo);
		tSQLInfo.addSubPara(mGrpName);
		tSQLInfo.addSubPara(fm.PrintStartDate.value);
		tSQLInfo.addSubPara(fm.PrintEndDate.value);
		turnPage1.queryModal(tSQLInfo.getString(), ContInfoGrid, 1, 1);
		if(!turnPage1.strQueryResult){
			alert("未查到符合条件的结果!");
			initContInfoGrid();
		}
	}else if(mContState=='0'){
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_app.LCPostalInfoSql");
		if(_DBT==_DBO){
			tSQLInfo.setSqlId("LCPostalInfoSql2");
	   }else if(_DBT==_DBM)
	   {
			tSQLInfo.setSqlId("LCPostalInfoSql21");
	   }
		tSQLInfo.addSubPara(tManageCom);
		tSQLInfo.addSubPara(mManageCom);
		tSQLInfo.addSubPara(mGrpPropNo);
		tSQLInfo.addSubPara(mGrpContNo);
		tSQLInfo.addSubPara(mGrpName);
		tSQLInfo.addSubPara(fm.PrintStartDate.value);
		tSQLInfo.addSubPara(fm.PrintEndDate.value);
		turnPage1.queryModal(tSQLInfo.getString(), ContInfoGrid, 1, 1);
		if(!turnPage1.strQueryResult){
			alert("未查到符合条件的结果!");
			initContInfoGrid();
		}		
	}else if(mContState=='1'){
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_app.LCPostalInfoSql");
		
		if(_DBT==_DBO){
			tSQLInfo.setSqlId("LCPostalInfoSql3");
	   }else if(_DBT==_DBM)
	   {
		   tSQLInfo.setSqlId("LCPostalInfoSql31");
	   }
		tSQLInfo.addSubPara(tManageCom);
		tSQLInfo.addSubPara(mManageCom);
		tSQLInfo.addSubPara(mGrpPropNo);
		tSQLInfo.addSubPara(mGrpContNo);
		tSQLInfo.addSubPara(mGrpName);
		tSQLInfo.addSubPara(fm.PrintStartDate.value);
		tSQLInfo.addSubPara(fm.PrintEndDate.value);
		turnPage1.queryModal(tSQLInfo.getString(), ContInfoGrid, 1, 1);
		if(!turnPage1.strQueryResult){
			alert("未查到符合条件的结果!");
			initContInfoGrid();
		}
	}
}

/**
 * 保存录入结论信息
 */
function saveClick() {
	
	if(!beforeSub()){
		return false;
	}
	if(!verifyInput2()){
		return false;
	}
	if(fm.RegisterPassFlag.value=='1'){
		if(fm.Reason.value==null||fm.Reason.value==''){
			alert("不合格原因不能为空!");
			return false;
		}
	}
	if(fm.RegisterPassFlag.value=="0"){
		if(fm.TransferType.value==null || fm.TransferType.value==""){
			alert("请选择交接方式!");
			return false;
		}
		if(fm.TransferDate.value==null || fm.TransferDate.value==""){
			alert("请录入交接日期!");
			return false;
		}
	}
	
	if(fm.RegisterPassFlag.value=="0" && fm.TransferType.value=="2"){
		if(fm.ExpressCorpName.value==null || fm.ExpressCorpName.value=="" || fm.ExpressNo.value==null || fm.ExpressNo.value==""){
			alert("快递交接，快递公司和快递单号不能为空!");
			return false;
		}
	}
	fm.action = "./LCDeliverySave.jsp?Operate=INSERT";
	submitForm(fm,"INSERT");	
}


function submitFunc()
{
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
}
/**
 * 提交数据后返回操作
 */
function afterSubmit(FlagStr, content) {
	
	if (typeof(showInfo)=="object" && typeof(showInfo)!="unknown") {
		showInfo.close();
	}
	
	if (FlagStr=="Fail") {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		queryClick();
		divQuery1Info.style.display='none';
		divQuery2Info.style.display='none';
		fm.RegisterPassFlag.value='';
		fm.RegisterPassFlagName.value='';
	}	
}
function submitForm(obj, tOperate) {
	
	submitFunc();
	mOperate = tOperate;
	obj.submit(); //提交
}

function afterCodeSelect(o, p){	
	if(o=='registerclu'){
		if(p.value=='0'){
			divQuery1Info.style.display='';
			divQuery2Info.style.display='none';
		}else{
			divQuery1Info.style.display='none';
			divQuery2Info.style.display='';
		}
	}		
}
function beforeSub(){
	var tSelNo = ContInfoGrid.getChkCount();
	if (tSelNo<1) {
		alert("请选中一条投保信息");
		return false;
	}
	if(fm.RegisterPassFlag.value=='0'){
		if(fm.TransferDate.value>fm.ExpressDate.value){
			alert("交接日期应早于当前日期!");
			return false;
		}
	}
	return true;
}
