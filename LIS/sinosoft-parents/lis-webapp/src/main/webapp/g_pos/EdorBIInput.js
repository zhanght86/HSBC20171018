/***************************************************************
 * <p>ProName:EdorBIInput.js</p>
 * <p>Title:  银行信息变更</p>
 * <p>Description:银行信息变更</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : caiyc
 * @version  : 8.0
 * @date     : 2014-06-17
 ****************************************************************/
 
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var mOperate = "";//操作状态
var tSQLInfo = new SqlClass();
var StringFlag;

function initDivDisplay() {
	
	divEdorTypeDetail.style.display = '';
}

function showFS(o) {
	
	if('0' == o){
		if (fm.FS2.checked = true) {
			fm.FS2.checked = false;
			divFS.style.display = 'none';
		}
	}else if('1' == o) {
		fm.FS1.checked = false;
		var tUpRow = UpdateInsuredInfoGrid.getSelNo()-1;
		var tOldRow = OldInsuredInfoGrid.getSelNo()-1;
		if(tOldRow>-1&&StringFlag==0){
			
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_pos.EdorBISql");
			tSQLInfo.setSqlId("EdorBISql3");
			tSQLInfo.addSubPara(tGrpContNo);
			tSQLInfo.addSubPara(fm.HInsuredNo.value);
			
			turnPage3.queryModal(tSQLInfo.getString(), FSGrid, 1, 1);
			var arrRsult = easyExecSql(tSQLInfo.getString(), 1, 0, 1); 
			if(arrRsult != null){
				fm.MainInsuredNames.value = OldInsuredInfoGrid.getRowColData(tOldRow,2);
				fm.MainInsuredIdNos.value = OldInsuredInfoGrid.getRowColData(tOldRow,8);
			}
			
			if(!turnPage3.strQueryResult){
				alert("未查询到符合条件的附属被保险人!");
				fm.FS2.checked=false;
				return false;
			}	
		}else if(tUpRow>-1&&StringFlag==1){
			
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_pos.EdorBISql");
			tSQLInfo.setSqlId("EdorBISql4");
			tSQLInfo.addSubPara(tGrpContNo);
			tSQLInfo.addSubPara(fm.InsuredNames.value);
			tSQLInfo.addSubPara(fm.InsuredIdNos.value);
			
			turnPage3.queryModal(tSQLInfo.getString(), FSGrid, 1, 1);
			if(!turnPage3.strQueryResult){
				alert("未查询到符合条件的附属被保险人!");
				fm.FS2.checked=false;
				return false;
			}
		}	
		if (divFS.style.display == '') {
			divFS.style.display = 'none';
		} else {
			divFS.style.display = '';
		}
	}
}
/**
 * 选择城市前必须先选择省份
 */
function checkProvince(){
	
	if(fm.BankProvince.value == ""){
		alert("请先选择省份");
		fm.BankCity.value = "";
		fm.BankCityName.value = "";
	}
}

function submitFunc(){
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
		
		queryOldClick();
		queryUpClick(2);
		clearPage();
		initFSGrid();
		fm.FS1.checked = false;
		fm.FS2.checked = false;
		divFS.style.display='none';
	}
}
/**
*查询原被保险人信息
*/
function queryOldClick(){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorBISql");
	tSQLInfo.setSqlId("EdorBISql1");
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(fm.OldInsuredName.value);
	tSQLInfo.addSubPara(fm.OldInsuredIDNo.value);
	tSQLInfo.addSubPara(fm.OldInsuredNo.value);
	tSQLInfo.addSubPara(tEdorAppNo);
	
	initOldInsuredInfoGrid();
	initUpdateInsuredInfoGrid();
	clearPage();
	fm.SerialNo.value="";
	turnPage1.queryModal(tSQLInfo.getString(), OldInsuredInfoGrid, 1, 1);
		
	if(!turnPage1.strQueryResult){
		alert("未查询到符合条件的查询结果!");
		return false;
	}
}

/**
*查询修改后被保人信息
*/
function queryUpClick(o){
	
	divButton01.style.display='none';
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorBISql");
	tSQLInfo.setSqlId("EdorBISql2");
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(tEdorAppNo);
	tSQLInfo.addSubPara(tEdorType);
	tSQLInfo.addSubPara(NullToEmpty(tEdorNo));
	tSQLInfo.addSubPara(fm.InsuredName.value);
	tSQLInfo.addSubPara(fm.InsuredIDNo.value);
	tSQLInfo.addSubPara(fm.BatchNo.value);
	
	initOldInsuredInfoGrid();
	initUpdateInsuredInfoGrid();
	clearPage();
	turnPage2.queryModal(tSQLInfo.getString(), UpdateInsuredInfoGrid, 1, 1);
		
	if(o=='1'){
		if(!turnPage2.strQueryResult){
			alert("未查询到符合条件的查询结果!");
			return false;
		}
	}
}

/**
*展示修改后被保险人银行
*/
function showBankInfo(){
	
	divButton01.style.display='';
	StringFlag=0;
	var tRow = OldInsuredInfoGrid.getSelNo()-1;
	var tContNo = OldInsuredInfoGrid.getRowColData(tRow,1);
	var tInsuredNo = OldInsuredInfoGrid.getRowColData(tRow,12);
	var tName = OldInsuredInfoGrid.getRowColData(tRow,2);
	var tIDNo = OldInsuredInfoGrid.getRowColData(tRow,8);
	
	fm.InsuredNames.value = tName;
	fm.InsuredIdNos.value = tIDNo;
	fm.HContNo.value = tContNo;
	fm.HInsuredNo.value =tInsuredNo; 
	
	fm.HeadBankCode.value = OldInsuredInfoGrid.getRowColData(tRow,14);
	fm.HeadBankName.value = OldInsuredInfoGrid.getRowColData(tRow,15);
	fm.AccName.value =  OldInsuredInfoGrid.getRowColData(tRow,16);
	fm.BankAccNo.value = OldInsuredInfoGrid.getRowColData(tRow,17);
	fm.BankProvince.value = OldInsuredInfoGrid.getRowColData(tRow,18);
	fm.BankProvinceName.value = OldInsuredInfoGrid.getRowColData(tRow,19);
	fm.BankCity.value = OldInsuredInfoGrid.getRowColData(tRow,20); 
	fm.BankCityName.value = OldInsuredInfoGrid.getRowColData(tRow,21); 
	fm.Mobile.value = OldInsuredInfoGrid.getRowColData(tRow,22);  
	
	divFS.style.display='none';
	fm.FS2.checked=false;
}


function showUpInfo(){
	
	divButton01.style.display='none';
	StringFlag=1;
	var tRow = UpdateInsuredInfoGrid.getSelNo()-1;
	var tSerialNo = UpdateInsuredInfoGrid.getRowColData(tRow,1);
	var tName = UpdateInsuredInfoGrid.getRowColData(tRow,2);
	var tInsuredIDNO =  UpdateInsuredInfoGrid.getRowColData(tRow,3);
	
	fm.InsuredNames.value = tName;
	fm.InsuredIdNos.value = tInsuredIDNO;
	fm.SerialNo.value = tSerialNo;
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorBISql");
	tSQLInfo.setSqlId("EdorBISql6");
	tSQLInfo.addSubPara(tSerialNo);
	
	var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tPropEntry==null) {
			return false;
		} else {
			fm.HeadBankCode.value=tPropEntry[0][0];
			fm.HeadBankName.value=tPropEntry[0][1];
			fm.AccName.value=tPropEntry[0][2];
			fm.BankAccNo.value=tPropEntry[0][3];
			fm.BankProvince.value=tPropEntry[0][4];
			fm.BankProvinceName.value=tPropEntry[0][5];
			fm.BankCity.value=tPropEntry[0][6];
			fm.BankCityName.value=tPropEntry[0][7];
			fm.Mobile.value=tPropEntry[0][8];
			fm.EdorValDate.value=tPropEntry[0][9];;
		}
		divFS.style.display='none';
		fm.FS2.checked=false;	
}

/**
* 保存
*/
function saveBank(){
	var tOldRow = OldInsuredInfoGrid.getSelNo();
	var tUPRow = UpdateInsuredInfoGrid.getSelNo();
	
	if(tOldRow=='0' && tUPRow=='0'){
		alert("请选择一条被保人信息进行操作!");
		return false;	
	}
		
	if(fm.FS2.checked){	
		var rowNum = FSGrid.mulLineCount ;
		var tRow = 0;
		for(var index=0;index<rowNum;index++){
			if(FSGrid.getChkNo(index)){
				tRow=1;
			}
		}
		if(tRow==0){
			alert("请选择需要调整的附属被保人信息!");
			return false;
		}	
	}
	
	if(!verifyInput()){
		return false;
	}
	
	// 校验银行信息
	var tflag1 = true;
	var bankFlag = 0;
	if(fm.HeadBankCode.value!=null || fm.HeadBankCode.value!=""){
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_app.LCContCommonSql");
		tSQLInfo.setSqlId("LCContCommonSql9");
		tSQLInfo.addSubPara(fm.HeadBankCode.value);
		bankFlag = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	}
	if(bankFlag =='1'){
		if(fm.HeadBankCode.value==''&& fm.BankProvince.value==''&& fm.BankCity.value=='' && fm.AccName.value=='' && trim(fm.BankAccNo.value)==''){
			tflag1= true;
		}else if(fm.HeadBankCode.value !=''&& fm.BankProvince.value!=''&& fm.BankCity.value!='' && fm.AccName.value!='' && trim(fm.BankAccNo.value)!=''){
			tflag1= true;
		}else{
			tflag1=false;
		}
	} else {
		if(fm.HeadBankCode.value=='' && fm.AccName.value=='' && trim(fm.BankAccNo.value)==''){
			tflag1= true;
		}else if(fm.HeadBankCode.value !='' && fm.AccName.value!='' && trim(fm.BankAccNo.value)!=''){
			tflag1= true;
		}else{
			tflag1=false;
		}
	}
	if(tflag1==false){
		alert("请填写银行相关信息！");
		return false;
	
	}
	mOperate="INSERT";
	fm.action = "./EdorBISave.jsp?Operate="+ mOperate+"&EdorType="+tEdorType+"&EdorAppNo="+tEdorAppNo+"&GrpContNo="+tGrpContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
	submitFunc();

}

/**
* 保存
*/
function deleteOperate(){
	var tUpRow = UpdateInsuredInfoGrid.getSelNo()-1;
	
	if(tUpRow<0){
		alert("请选择一条删除!");
		return false;
	}
	var tSerialNo = UpdateInsuredInfoGrid.getRowColData(tUpRow,1);
	mOperate="DELETE";
	fm.action = "./EdorBISave.jsp?Operate="+ mOperate+"&SerialNo="+tSerialNo+"&EdorType="+tEdorType+"&EdorAppNo="+tEdorAppNo+"&GrpContNo="+tGrpContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
	submitFunc();
}

/**
* 清空页面要素
*/
function clearPage(){
	
	fm.EdorValDate.value ="";
	fm.HeadBankCode.value ="";
	fm.HeadBankName.value ="";
	fm.AccName.value ="";
	fm.BankAccNo.value ="";
	fm.BankProvince.value ="";
	fm.BankProvinceName.value ="";
	fm.BankCity.value ="";
	fm.BankCityName.value ="";
	fm.Mobile.value ="";
}

/**
 * 下拉框选择后处理
 */
function afterCodeSelect(tVal, tObj) {

	if(tVal=='province'){
		fm.BankCity.value="";
		fm.BankCityName.value="";
	}
}
