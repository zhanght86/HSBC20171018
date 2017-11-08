/***************************************************************
 * <p>ProName：EdorBCInput.js</p>
 * <p>Title：受益人维护</p>
 * <p>Description：受益人维护</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-06-13
 ****************************************************************/
 
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var turnPage4 = new turnPageClass();
var mOperate = "";//操作状态
var tSQLInfo = new SqlClass();


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
		
		if(mOperate == "DELETE" ||mOperate =="INSERT||UPDATE" ){			
			initOldInsuredInfoGrid();
			initUpdateInsuredInfoGrid();
			initBnfUpdateInfoGrid();
			queryOldClick();
			queryUpdateClick(2);	
		}
	}	
}

/**
 * 保存
 */
 
 function addRecord(){
 	
 	if (!verifyInput2()) {
			return false;
	}
	
	var tSelNoOld = OldInsuredInfoGrid.getSelNo();
	var tSelNoUpdate = UpdateInsuredInfoGrid.getSelNo();
	if(tSelNoOld=='0' && tSelNoUpdate=='0'){		
		alert("请选中一条原被保人信息或修改后的被保人信息进行保存！");
		return false;		
	}
	
	// 校验银行信息
	BnfUpdateInfoGrid.delBlankLine();
	if(!valBnfInfo()){
			return false;	
	}	
	
	mOperate = "INSERT||UPDATE";
	submitForm();
 }
 
  /**
 * 撤销
 */
 function deleteRecord(){
 	
 	var tSelNo = UpdateInsuredInfoGrid.getSelNo();  
 	if(tSelNo=='0'){
 		alert("请选择一条修改过的被保险人信息进行撤销！");
 	  return false;
 	}

 	mOperate = "DELETE";
	submitForm();	
 }


/**
 *校验受益人信息
 */
function valBnfInfo(){
	
	var tCount = BnfUpdateInfoGrid.mulLineCount;
	var sumRate = 0;
	
	if(parseInt(tCount)>0){
		if(!BnfUpdateInfoGrid.checkValue("BnfUpdateInfoGrid")){
			return false;
		}
		
		for (var i=0; i<tCount;i++){
			for(var j=i+1;j<tCount;j++){
				if(BnfUpdateInfoGrid.getRowColData(i,2)!=BnfUpdateInfoGrid.getRowColData(j,2)){
					alert("受益人类别只能录入一种！");
					return false;
				}
			}
			if(BnfUpdateInfoGrid.getRowColData(i,4)==''){
				if(BnfUpdateInfoGrid.getRowColData(i,2)!='00'||BnfUpdateInfoGrid.getRowColData(i,5)!=''||
					BnfUpdateInfoGrid.getRowColData(i,7)!=''||
					BnfUpdateInfoGrid.getRowColData(i,8)!=''||
					BnfUpdateInfoGrid.getRowColData(i,10)!=''||
					BnfUpdateInfoGrid.getRowColData(i,11)!=''||
					BnfUpdateInfoGrid.getRowColData(i,13)!=''||
					//BnfUpdateInfoGrid.getRowColData(i,14)!=''||
					BnfUpdateInfoGrid.getRowColData(i,16)!=''||
					BnfUpdateInfoGrid.getRowColData(i,17)!=''||
					BnfUpdateInfoGrid.getRowColData(i,18)!=''||
					BnfUpdateInfoGrid.getRowColData(i,20)!=''||
					BnfUpdateInfoGrid.getRowColData(i,22)!=''||
					BnfUpdateInfoGrid.getRowColData(i,23)!=''){
					alert("受益人顺序不能为空!");
					return false;
				}
			}else{
				if(BnfUpdateInfoGrid.getRowColData(i,5)==''){
					alert("受益人姓名不能为空!");
					return false;
				}
				if(BnfUpdateInfoGrid.getRowColData(i,2)!='02'){
			
				if(BnfUpdateInfoGrid.getRowColData(i,10)==''){
					alert("证件类型不能为空!");
					return false;
				}
				if(BnfUpdateInfoGrid.getRowColData(i,11)==''){
					alert("证件号码不能为空!");
					return false;
				}
				if(BnfUpdateInfoGrid.getRowColData(i,10) !=''){
					if(BnfUpdateInfoGrid.getRowColData(i,10)!='0' ){	
						if(BnfUpdateInfoGrid.getRowColData(i,7)==null || BnfUpdateInfoGrid.getRowColData(i,7) =='' && BnfUpdateInfoGrid.getRowColData(i,8)==null || BnfUpdateInfoGrid.getRowColData(i,8) =='' ){
							alert("请输入受益人性别/出生日期！");
							return false;
						}
					}else if(BnfUpdateInfoGrid.getRowColData(i,10)=='0'){
						if(BnfUpdateInfoGrid.getRowColData(i,11).length!='15' && BnfUpdateInfoGrid.getRowColData(i,11).length!='18'){
							alert("受益人身份证号码必须为15或者18位！");
							return false;
						}
					}
				}
				if(BnfUpdateInfoGrid.getRowColData(i,13)==''){
					alert("与被保险人关系不能为空!");
					return false;
				}
			}
			
			if(BnfUpdateInfoGrid.getRowColData(i,14)==''){
				alert("受益比例不能为空!");
				return false;
			}
			var tHeadBankCode = BnfUpdateInfoGrid.getRowColData(i,16);
			var tAccname = BnfUpdateInfoGrid.getRowColData(i,17); 
			var tBankaccno = BnfUpdateInfoGrid.getRowColData(i,18); 
			var tBankprovince = BnfUpdateInfoGrid.getRowColData(i,20);
			var tBankcity = BnfUpdateInfoGrid.getRowColData(i,22);
			
			// 校验银行信息
			var tflag1 = true;
			var bankFlag = 0;
			if(tHeadBankCode!=null || tHeadBankCode!=""){
				tSQLInfo = new SqlClass();
				tSQLInfo.setResourceName("g_app.LCContCommonSql");
				tSQLInfo.setSqlId("LCContCommonSql9");
				tSQLInfo.addSubPara(tHeadBankCode);
				bankFlag = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			}
			if(bankFlag =='1'){
				if(tHeadBankCode==''&& tBankprovince==''&& tBankcity=='' && tAccname=='' && trim(tBankaccno)==''){
					tflag1= true;
				}else if(tHeadBankCode !=''&& tBankprovince!=''&& tBankcity!='' && tAccname!='' && trim(tBankaccno)!=''){
					tflag1= true;
				}else{
					tflag1=false;
				}
			
			} else {
				if(tHeadBankCode=='' && tAccname=='' && trim(tBankaccno)==''){
					tflag1= true;
				}else if(tHeadBankCode !='' && tAccname!='' && trim(tBankaccno)!=''){
					tflag1= true;
				}else{
					tflag1=false;
				}
			}
			if(tflag1==false){
				alert("请填写银行相关信息！");
				return false;
			
			}
			if(tHeadBankCode != ''){
				tSQLInfo = new SqlClass();
				tSQLInfo.setResourceName("g_pos.EdorCommonSql");
				tSQLInfo.setSqlId("EdorCommonSql7");
				tSQLInfo.addSubPara(tHeadBankCode);
	
				var arrReslut = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
				if(arrReslut==null){
					alert("请选择有效的开户银行 ！");
					return false;
				}
			}
			if(tBankprovince != ''){
				tSQLInfo = new SqlClass();
				tSQLInfo.setResourceName("g_pos.EdorCommonSql");
				tSQLInfo.setSqlId("EdorCommonSql8");
				tSQLInfo.addSubPara(tBankprovince);
	
				var arrReslut = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
				if(arrReslut==null){
					alert("请选择有效的开户行所在省！");
					return false;
				}
			}
			if(tBankcity != ''){
				tSQLInfo = new SqlClass();
				tSQLInfo.setResourceName("g_pos.EdorCommonSql");
				tSQLInfo.setSqlId("EdorCommonSql9");
				tSQLInfo.addSubPara(tBankprovince);
				tSQLInfo.addSubPara(tBankcity);
				var arrReslut = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
				if(arrReslut==null){
					alert("请选择有效的开户行所在市！");
					return false;
				}
			}
		}
	}
}
		
	return true;
}

/**
 * 调用保险方案的方法
 */
function showContPlanCode(cObj,cObjName,cObjCode){
	return showCodeList('contplan',[cObj,cObjName,cObjCode],[0,1,2],null,fm.GrpPropNo.value,'grpcontno',1,null);
}

function showContPlanCodeName(cObj1,cObjName1,cObjCode1){
	return showCodeListKey('contplan',[cObj1,cObjName1,cObjCode1],[0,1,2],null,fm.GrpPropNo.value,'grpcontno',1,null);
}


/**
 * 查询原被保人信息
 */
function queryOldClick(){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorBCSql");
	tSQLInfo.setSqlId("EdorBCSql1");
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	tSQLInfo.addSubPara(fm.OldInsuredName.value);
	tSQLInfo.addSubPara(fm.OldInsuredIDNo.value);
	tSQLInfo.addSubPara(fm.OldInsuredNo.value);
	tSQLInfo.addSubPara(fm.ContPlanCodeOld.value);
	tSQLInfo.addSubPara(tEdorAppNo);
		
	initOldInsuredInfoGrid();
	initUpdateInsuredInfoGrid();
	initBnfUpdateInfoGrid();
	fm.edorValDate.value ="";
	fm.SerialNo.value ="";
	turnPage1.queryModal(tSQLInfo.getString(), OldInsuredInfoGrid, 1, 1,10);
	
	if (!turnPage1.strQueryResult) {
			alert("未查询到符合条件的查询结果！");
		}
}

 /**
 * 查询修改后被保人信息
 */
function queryUpdateClick(o){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorBCSql");
	tSQLInfo.setSqlId("EdorBCSql3");
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	tSQLInfo.addSubPara(fm.EdorAppNo.value);
	tSQLInfo.addSubPara(fm.EdorType.value);
	tSQLInfo.addSubPara(NullToEmpty(fm.EdorNo.value));
	tSQLInfo.addSubPara(fm.InsuredName.value);
	tSQLInfo.addSubPara(fm.InsuredIDNo.value);
	tSQLInfo.addSubPara(fm.BatchNo.value);
	
	initOldInsuredInfoGrid();
	initUpdateInsuredInfoGrid();
	initBnfUpdateInfoGrid();
	fm.edorValDate.value ="";
	turnPage2.queryModal(tSQLInfo.getString(), UpdateInsuredInfoGrid, 1, 1,10);
	
	if(o=='1'){
		if (!turnPage2.strQueryResult) {
			alert("未查询到符合条件的查询结果！");
		}
	}	
}

 /**
 * 展示原被保人受益人信息
 */
function showOldBnfList(){
	
	var tSelNo = OldInsuredInfoGrid.getSelNo();
	var tContNo = OldInsuredInfoGrid.getRowColData(tSelNo-1,1);
	var tInsuredNo =  OldInsuredInfoGrid.getRowColData(tSelNo-1,12);
	var tOldInsuredName = OldInsuredInfoGrid.getRowColData(tSelNo-1,2);	
	var tOldIdno = OldInsuredInfoGrid.getRowColData(tSelNo-1,8);
	
	fm.InsuredOldName.value = tOldInsuredName;
	fm.IdOldNo.value = tOldIdno;
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorBCSql");
	tSQLInfo.setSqlId("EdorBCSql2");
	tSQLInfo.addSubPara(tContNo);
	tSQLInfo.addSubPara(tInsuredNo);
	
	initBnfUpdateInfoGrid();
	turnPage4.queryModal(tSQLInfo.getString(), BnfUpdateInfoGrid, 1, 1,10);	
}

 /**
 * 展示修改后被保人受益人信息
 */
function showUpdateInsuredList(){
	
	var tSelNo = UpdateInsuredInfoGrid.getSelNo();
	var tSerialNo = UpdateInsuredInfoGrid.getRowColData(tSelNo-1,1);
	var tOldInsuredName = UpdateInsuredInfoGrid.getRowColData(tSelNo-1,2);	
	var tOldIdno = UpdateInsuredInfoGrid.getRowColData(tSelNo-1,3);	
	var tEdorValdate =  UpdateInsuredInfoGrid.getRowColData(tSelNo-1,4);	
	
	fm.edorValDate.value = tEdorValdate;
	fm.InsuredOldName.value = tOldInsuredName;
	fm.IdOldNo.value = tOldIdno;
	fm.SerialNo.value = tSerialNo;
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorBCSql");
	tSQLInfo.setSqlId("EdorBCSql4");
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	tSQLInfo.addSubPara(fm.EdorAppNo.value);
	tSQLInfo.addSubPara(fm.EdorType.value);
	tSQLInfo.addSubPara(tSerialNo); 
	tSQLInfo.addSubPara(fm.BatchNo.value);

	initBnfUpdateInfoGrid();
	turnPage4.queryModal(tSQLInfo.getString(), BnfUpdateInfoGrid, 1, 1,10);
}
/**
 * 下拉框选择后处理
 */
function afterCodeSelect(tVal, tObj) {

	if(tVal=='province'){
		if(tObj.name == "BnfUpdateInfoGrid19"){
			var rowNumber = BnfUpdateInfoGrid.lastFocusRowNo;
			BnfUpdateInfoGrid.setRowColData(rowNumber, 21, "");
			BnfUpdateInfoGrid.setRowColData(rowNumber, 22, "");
		}
	}
	if(tVal=='bnftype'){
		if(tObj.value == "法定受益人"){
			var rowNumber = BnfUpdateInfoGrid.lastFocusRowNo;
			BnfUpdateInfoGrid.setRowColData(rowNumber, 14, "1");
		}
	}
}


