/***************************************************************
 * <p>ProName：LCGrpUWInput.js</p>
 * <p>Title：人工核保</p>
 * <p>Description：人工核保</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-04-22
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var mOperate = "";//操作状态
var tSQLInfo = new SqlClass();

/**
 * 提交数据后返回操作

 */
function afterSubmit(tFlagStr, tContent) {

	if (typeof(showInfo) == "object" && typeof(showInfo) != "unknown") {

		showInfo.close();
	}

	if (tFlagStr == "Fail") {

		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + tContent ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + tContent ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		if("UWCONFIRM"==fm.Operate.value ){
			goBack();
		}
	}
}

/**
 * 提交
 */
function submitForm() {

	var i = 0;
	var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr;
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.Operate.value = mOperate;
	fm.submit();
}


/**
 * 初始化页面信息

 */
function initElements()
{
	var arrResult = new Array();
	var querySql = "";
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCGroupUWSql");
	tSQLInfo.setSqlId("LCGroupUWSql1");
	tSQLInfo.addSubPara(fm.GrpPropNo.value);

	arrResult =  easyExecSql(tSQLInfo.getString());
	if(arrResult==null || arrResult.length==0){
			return ;
	}

	fm.ManageCom.value=arrResult[0][0];
	fm.GrpName.value=arrResult[0][1];
	fm.SaleChnl.value=arrResult[0][2];
	fm.ApplyDate.value=arrResult[0][3];
	fm.ValDate.value=arrResult[0][4];
	fm.AppntNum.value=arrResult[0][5];
	fm.ShouldPrem.value=arrResult[0][6];
	fm.ValDateType.value=arrResult[0][7];

	if("0"==arrResult[0][7]){
		divVal.style.display="none";
		divValS.style.display="";
	}else{
		divVal.style.display="";
		divValS.style.display="none";
	}
}


/**
 * 查询人员清单
 */

 function insuredQuery(){

 	var tGrpPropNo = fm.GrpPropNo.value;
 	window.open("./UWInsuredQueryMain.jsp?GrpPropNo="+tGrpPropNo,"查询人员清单",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
 }

 /**
 * 查询人员分布
 */
 function insuredStatistic(){
 	var tGrpPropNo = fm.GrpPropNo.value;
 	window.open("./UWPersonQueryMain.jsp?GrpPropNo="+tGrpPropNo,"查询分部清单",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
 }

 /**
 * 核保处理
 */
 function goToGErr(){

 	var tGrpPropNo = fm.GrpPropNo.value;
 	window.open("./UWGErrMain.jsp?GrpPropNo="+tGrpPropNo,"核保处理",'width=1600,height=620,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
 }

 /**
 * 再保处理
 */
 function gotoReins(){

 	var tGrpPropNo = fm.GrpPropNo.value;
 	window.open("../reinsure/RIFaculDealMain.jsp?GrpPropNo="+tGrpPropNo+"&Flag=NB","再保处理",'width=1600,height=620,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
 }
  /**
 * 返回
 */
 function goBack(){

 	window.location="./LCGrpUWListInput.jsp"

 }
 /**
  * 核保确认
  */
function uwConfirm(){
	if(!verifyInput2()){
		return false;
	}
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCGroupUWSql");
	tSQLInfo.setSqlId("LCGroupUWSql2");
	tSQLInfo.addSubPara(tActivityID);
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	arrResult =  easyExecSql(tSQLInfo.getString());
	if(arrResult!=null && arrResult.length >0){
		if(confirm("保单有【"+arrResult[0][3]+"】问题件，退回是否清除再保结论？")){
			fm.ReinsFlag.value= "1";
		}else{
			fm.ReinsFlag.value= "0";
			return false;
		}
	}

	mOperate = "UWCONFIRM";
	fm.Operate.value= "UWCONFIRM";

	fm.action = "./LCGrpUWSave.jsp";
	submitForm();
}
/**
	* 理赔责任控制
*/
function claimDutyControl(){
	var strUrl="../g_claim/LLCLaimControlMain.jsp?BussType=NB&Flag=1&BussNo="+ fm.GrpPropNo.value;

	window.open(strUrl,"理赔责任控制",'width=1600,height=620,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
* 方案查询
*/

function showPlanQuery(){

	window.open("./LCContPlanQueryMain.jsp?ContPlanType="+tContPlanType+"&PolicyNo="+tGrpPropNo ,"方案查询",'width=1366,height=768,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
/**
* 方案查询
*/

function policyQuery(){
	window.open("./LCBussQueryMain.jsp?Page=./LCGrpContPolInput.jsp&ContPlanType="+tContPlanType+"&GrpPropNo="+tGrpPropNo ,"投保单查询",'width=1366,height=768,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
