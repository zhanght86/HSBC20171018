/***************************************************************
 * <p>ProName：LCBnfQueryInput.js</p>
 * <p>Title：受益人信息维护</p>
 * <p>Description：受益人信息维护</p>
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
		getBnfInfo();
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


function bnfSave(){
	
	BnfGrid.delBlankLine();		
	//校验受益人信息
	if(valBnfInfo() == false){
		return false;	
	}
	mOperate="INSERT";
	submitForm();
}


function getBnfInfo(){
	
	//查询受益人信息	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCBnfSql");
	tSQLInfo.setSqlId("LCBnfSql1");
	tSQLInfo.addSubPara(fm.mContNo.value);
	tSQLInfo.addSubPara(fm.InsuredSeqNo.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), BnfGrid, 1, 1);
}


/**
 *校验受益人信息
 */
function valBnfInfo(){
	
	var tCount = BnfGrid.mulLineCount;
	var sumRate = 0;
	
	if(parseInt(tCount)==0){
		alert("请录入受益人信息!");
		return false;
	}
	if(parseInt(tCount)>0){
		if(!BnfGrid.checkValue("BnfGrid")){
			return false;
		}
		
		for (var i=0; i<tCount;i++){
			if(BnfGrid.getRowColData(i,8) !=''){
				if(BnfGrid.getRowColData(i,8)!='0' ){	
					if(BnfGrid.getRowColData(i,5)==null || BnfGrid.getRowColData(i,5) =='' && BnfGrid.getRowColData(i,6)==null || BnfGrid.getRowColData(i,6) =='' ){
						alert("请输入受益人性别/出生日期！");
						return false;
					}
				}else if(BnfGrid.getRowColData(i,8)=='0'){
					if(BnfGrid.getRowColData(i,9).length!='15' && BnfGrid.getRowColData(i,9).length!='18'){
						alert("受益人身份证号码必须为15或者18位！");
						return false;
					}
				}
			}
		}
	}
		
	return true;
}
