/***************************************************************
 * <p>ProName：LLClaimMaimRateInput.js</p>
 * <p>Title：伤残比例维护</p>
 * <p>Description：伤残比例维护</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 高冬华
 * @version  : 8.0
 * @date     : 2014-02-26
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var mOperate = "";//操作状态
var tSQLInfo = new SqlClass();

function QueryDefoInfo(){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.QueryDefoInfoSql");
	tSQLInfo.setSqlId("DefoInfoSql1");
	tSQLInfo.addSubPara(fm.DefoTypeCodeQ.value);
	tSQLInfo.addSubPara(fm.DefoClassQ.value);
	tSQLInfo.addSubPara(fm.DefoClassNameQ.value);
	tSQLInfo.addSubPara(fm.DefoGradeQ.value);
	tSQLInfo.addSubPara(fm.DefoGradeNameQ.value);
	tSQLInfo.addSubPara(fm.DefoCodeQ.value);
	tSQLInfo.addSubPara(fm.DefoCodeNameQ.value);
	
	turnPage2.queryModal(tSQLInfo.getString(), DefoGrid,"2");
	if (!turnPage2.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
	}
}
/**点击触发方法**/
function showDefoInfo(){
	
	 var i = DefoGrid.getSelNo();
   if(i < 1)
    {
        alert("请选中一行记录！");
        return;
     }
	  i = DefoGrid.getSelNo()-1;
    fm.DefoTypeCode.value=DefoGrid.getRowColData(i,1);
    fm.DefoTypeName.value=DefoGrid.getRowColData(i,2);
    fm.DefoClass.value=DefoGrid.getRowColData(i,3);
    fm.DefoClassName.value=DefoGrid.getRowColData(i,4);
		fm.DefoGrade.value=DefoGrid.getRowColData(i,5);
		fm.DefoGradeName.value=DefoGrid.getRowColData(i,6);
		fm.DefoCode.value=DefoGrid.getRowColData(i,7);
		fm.DefoName.value=DefoGrid.getRowColData(i,8);
		fm.DefoRate.value=DefoGrid.getRowColData(i,9);
		fm.tDefoType.value=DefoGrid.getRowColData(i,1);
		fm.tDefoCode.value=DefoGrid.getRowColData(i,7);
		fm.DInsert.disabled=true;
		fm.DModify.disabled=false;
		fm.DDelete.disabled=false;
		
		if(fm.DefoTypeCode.value=="3"){
			
			document.getElementById("DefoClass1").style.display="";
			document.getElementById("DefoClass2").style.display="";
			document.getElementById("DefoClassName1").style.display="";
			document.getElementById("DefoClassName2").style.display="";						
			
		}else{
			
			document.getElementById("DefoClass1").style.display="none";
			document.getElementById("DefoClass2").style.display="none";
			document.getElementById("DefoClassName1").style.display="none";
			document.getElementById("DefoClassName2").style.display="none";				
		}
		
}
/**重 置**/
function DefoTurnBack(){
	
	  fm.DefoTypeCode.value="";
    fm.DefoTypeName.value="";
    fm.DefoClass.value="";
    fm.DefoClassName.value="";
		fm.DefoGrade.value="";
		fm.DefoGradeName.value="";
		fm.DefoCode.value="";
		fm.DefoName.value="";
		fm.DefoRate.value="";
		fm.tDefoType.value="";
		fm.tDefoCode.value="";
		fm.Operate.value="";
		fm.DInsert.disabled=false;
		fm.DModify.disabled=true;
		fm.DDelete.disabled=true;
		
		
		document.getElementById("DefoClass1").style.display="none";
		document.getElementById("DefoClass2").style.display="none";
		document.getElementById("DefoClassName1").style.display="none";
		document.getElementById("DefoClassName2").style.display="none";			
}
/**新 增**/
function DefoInsert(){
	if(trim(fm.DefoTypeCode.value)==""){
		
		alert("伤残类型不能为空");
		return false;
	}
	
	if(fm.DefoType.value=="3"){
		if(trim(fm.DefoClass.value)==""){
			
			alert("当伤残类型为【伤残等级(10级281项)】时，伤残分类不能为空");
			return false;
		}
		
		if(trim(fm.DefoClassName.value)==""){
			
			alert("当伤残类型为【伤残等级(10级281项)】时，伤残分类名称不能为空");	
			return false;
		}
	}
	
	if(trim(fm.DefoGrade.value=="")){
		
			alert("伤残级别不能为空");
			return false;
		
	}
	
	if(trim(fm.DefoGradeName.value=="")){
			alert("伤残级别名称不能为空");
			return false;
			
	}
	
	if(trim(fm.DefoCode.value=="")){
		alert("伤残代码不能为空");
		return false;
	}
	
	if(trim(fm.DefoName.value=="")){
		alert("伤残代码名称不能为空");
		return false;
	}
	
	if(trim(fm.DefoRate.value=="")){
		alert("伤残比例不能为空");
		return false;
	}
	
	fm.Operate.value="INSERT";
	fm.action="./LLClaimMaimRateSave.jsp";
	submitForm();
}

/**修 改**/
function DefoModify(){
	
	if(trim(fm.DefoTypeCode.value)==""){
		
		alert("伤残类型不能为空");
		return false;
	}
	
	if(fm.DefoType.value=="3"){
		if(trim(fm.DefoClass.value)==""){
			
			alert("当伤残类型为【伤残等级(10级281项)】时，伤残分类不能为空");
			return false;
		}
		
		if(trim(fm.DefoClassName.value)==""){
			
			alert("当伤残类型为【伤残等级(10级281项)】时，伤残分类名称不能为空");	
			return false;
		}
	}
	
	if(trim(fm.DefoGrade.value=="")){
		
			alert("伤残级别不能为空");
			return false;
		
	}
	
	if(trim(fm.DefoGradeName.value=="")){
			alert("伤残级别名称不能为空");
			return false;
			
	}
	
	if(trim(fm.DefoCode.value=="")){
		alert("伤残代码不能为空");
		return false;
	}
	
	if(trim(fm.DefoName.value=="")){
		alert("伤残代码名称不能为空");
		return false;
	}
	
	if(trim(fm.DefoRate.value=="")){
		alert("伤残比例不能为空");
		return false;
	}
	
	fm.Operate.value="UPDATE";
	fm.action="./LLClaimMaimRateSave.jsp";
	submitForm();
}
function DefoDelete(){
	
	if (confirm("您确实想删除该记录吗?")){	
		
		fm.Operate.value="DELETE";
		fm.action="./LLClaimMaimRateSave.jsp";
		submitForm();
    }else{
			fm.Operate.value="";
			return false;
    }
	
}
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
	}	
	DefoTurnBack();
	initDefoGrid();
}
/**下拉处理**/
function afterCodeSelect( cCodeName, Field ) {
	if(cCodeName=="defotype") {
		if(fm.DefoTypeCode.value=="3") {
			
			document.getElementById("DefoClass1").style.display="";
			document.getElementById("DefoClass2").style.display="";
			document.getElementById("DefoClassName1").style.display="";
			document.getElementById("DefoClassName2").style.display="";			

		}else {
			document.getElementById("DefoClass1").style.display="none";
			document.getElementById("DefoClass2").style.display="none";
			document.getElementById("DefoClassName1").style.display="none";
			document.getElementById("DefoClassName2").style.display="none";			
		}
	}
}
//数据导出
function exportData() {
	
	if (!confirm("确认要导出数据？")) {
		return false;
	}
	
	//报表标题
	var tTitle = "伤残类型编码^|伤残类型^|伤残分类^|伤残分类名称^|伤残级别^|伤残级别名称^|"
							+"伤残代码^|伤残代码名称^|伤残给付比例";
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.QueryDefoInfoSql");
	tSQLInfo.setSqlId("DefoInfoSql1");
	tSQLInfo.addSubPara(fm.DefoTypeCodeQ.value);
	tSQLInfo.addSubPara(fm.DefoClassQ.value);
	tSQLInfo.addSubPara(fm.DefoClassNameQ.value);
	tSQLInfo.addSubPara(fm.DefoGradeQ.value);
	tSQLInfo.addSubPara(fm.DefoGradeNameQ.value);
	tSQLInfo.addSubPara(fm.DefoCodeQ.value);
	tSQLInfo.addSubPara(fm.DefoCodeNameQ.value);
	
	var tQuerySQL = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryDataExport.jsp?Title="+ tTitle +"&QuerySQL="+ tQuerySQL;
	
	fm.submit();
}
