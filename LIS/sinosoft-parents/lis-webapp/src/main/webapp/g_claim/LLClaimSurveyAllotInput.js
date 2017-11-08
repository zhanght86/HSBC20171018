/***************************************************************
 * <p>PreName：LLClaimSurveyAllotInput.js</p>
 * <p>Title：调查任务分配</p>
 * <p>Description：调查任务分配</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 高冬华
 * @version  : 8.0
 * @date     : 2014-02-21
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var str ="";
var str1="";
var str2=" 1 and comcode like #"+tManageCom+"%# ";

/**
 * 提交数据
 */
function submitForm() {
	
	var showStr = "正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面！";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.submit();
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
		//showDialogWindow(urlStr, 1);
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	
		showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
		//showDialogWindow(urlStr, 1);
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	
		showInfo.focus();
	}	
	initForm();
}


function SurveyTaskGridSelClick1(){
	
	var i = SurveyTask1Grid.getSelNo();
   if(i < 1)
    {
        alert("请选中一行记录！");
        return false;
     }
	  i = SurveyTask1Grid.getSelNo()-1;
    fm.InqNo.value=SurveyTask1Grid.getRowColData(i,1);
    fm.RgtNo.value=SurveyTask1Grid.getRowColData(i,2);
    fm.AcceptMngCom.value=SurveyTask1Grid.getRowColData(i,10);
    fm.InqCom.value= SurveyTask1Grid.getRowColData(i,11);
		str = "1 and comcode != #"+fm.InqCom.value.substring(0,4)+"#" +" and char_length(comcode)=#4# " ;
		divInqPer2.style.display = '';
	
}
function SurveyTaskGridSelClick(){
	
	var i = SurveyTaskGrid.getSelNo();
   if(i < 1)
    {
        alert("请选中一行记录！");
        return false;
     }
	  i = SurveyTaskGrid.getSelNo()-1;
    fm.InqNo.value=SurveyTaskGrid.getRowColData(i,1);
    fm.RgtNo.value=SurveyTaskGrid.getRowColData(i,2);
    fm.AcceptMngCom.value=SurveyTaskGrid.getRowColData(i,10);
    fm.InqCom.value= SurveyTaskGrid.getRowColData(i,12);
		str1="1 and a.SurveyFlag=#1# and exists (select 1 from ldusertocom c where c.managecom like #"+fm.InqCom.value.substring(0,4)+"%# and c.usercode=b.usercode) ";
		
		divInqPer.style.display = '';
	
}
/**查看机构权限**/
function limitSurveyDept(){
	
	if(tManageCom.length>4) {
		alert("三级机构没有调查分配的权限！") ;
		fm.SurveyDeptQuery.disabled=true;
	}
	
}
function SurveyTaskQueryClick(){
	
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimSurveyAllotSql");
	tSQLInfo.setSqlId("LLClaimSurveyAllotSql1");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.acceptNo.value);
	tSQLInfo.addSubPara(fm.appntName.value);
	tSQLInfo.addSubPara(fm.customerName.value);
	tSQLInfo.addSubPara(fm.idNo.value);
	tSQLInfo.addSubPara(fm.inqType.value);
	tSQLInfo.addSubPara(fm.applyStartdate.value);
	tSQLInfo.addSubPara(fm.applyEndDate.value);
	tSQLInfo.addSubPara(fm.initdept.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), SurveyTask1Grid);
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
	}
	
	
}
function querytoTaskInfo(){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimSurveyAllotSql");
	tSQLInfo.setSqlId("LLClaimSurveyAllotSql2");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.acceptNo1.value);
	tSQLInfo.addSubPara(fm.appntName1.value);
	tSQLInfo.addSubPara(fm.customerName1.value);
	tSQLInfo.addSubPara(fm.idNo1.value);
	tSQLInfo.addSubPara(fm.inqType1.value);
	tSQLInfo.addSubPara(fm.applyStartdate1.value);
	tSQLInfo.addSubPara(fm.applyEndDate1.value);
	tSQLInfo.addSubPara(fm.IsLoc.value);
	tSQLInfo.addSubPara(fm.Initdept1.value);
	tSQLInfo.addSubPara(fm.Initdept2.value);
	
	turnPage2.queryModal(tSQLInfo.getString(), SurveyTaskGrid);
	if (!turnPage2.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
	}
	
}
//分配机构
function Designate(){
	
	var i = SurveyTask1Grid.getSelNo();
   if(i < 1) {
        alert("请选中一行记录！");
        return false;
     }
	if(fm.InqDept.value==""){
		alert("指定调查机构不能为空");
		return false;
	}
	fm.Operate.value="CHMNGINQ";
	submitForm();
	
}
//分配任务
function Designate1(){
	var i = SurveyTaskGrid.getSelNo();
   if(i < 1)
    {
        alert("请选中一行记录！");
        return false;
     }
		if(fm.InqPer.value==""){
			alert("指定调查人不能为空");
			return false;
		}
		fm.Operate.value="CHTASKINQ";
		submitForm();
}