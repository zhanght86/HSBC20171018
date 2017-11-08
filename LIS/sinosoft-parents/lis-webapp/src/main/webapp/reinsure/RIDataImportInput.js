//该文件中包含客户端需要处理的函数和事件
var showInfo;
var turnPage = new turnPageClass(); 
function QueryAccmulate(){

	//var strSQL = " select a.accumulatedefno,a.accumulatedefname,a.arithmeticid,a.state from riaccumulatedef a where a.state='01' order by a.accumulatedefno";
	var mySql100=new SqlClass();
	 	mySql100.setResourceName("reinsure.RIDataImportInputSql"); //指定使用的properties文件名
	  	mySql100.setSqlId("RIDataImportInputSql100");//指定使用的Sql的id
		mySql100.addSubPara("1");
	var strSQL=mySql100.getString();
	
	var arrResult = easyExecSql(strSQL);
	displayMultiline(arrResult,AccmulateGrid);
	return true;
}

function QueryTaskList(){

	//var strSQL = " select a.arithmeticid,a.arithmeticname,a.arithmetictype,a.calitemorder,a.itemcaltype,a.calclass from riitemcal a where a.arithmeticdefid='impt000001' and a.arithmetictype='22'";
	var mySql101=new SqlClass();
	 	mySql101.setResourceName("reinsure.RIDataImportInputSql"); //指定使用的properties文件名
	  	mySql101.setSqlId("RIDataImportInputSql101");//指定使用的Sql的id
		mySql101.addSubPara("1");
	var strSQL=mySql101.getString();
	var arrResult = easyExecSql(strSQL);
	displayMultiline(arrResult,TaskListGrid);
	return true;
}

function afterCodeSelect( codeName, Field )
{   //业务数据导入
	if(Field.value=='21'){
		divTitle.style.display='';
		divEndDate.style.display='';
		divList.style.display='';
		divAccmulate.style.display='';
		divTaskList.style.display='none';
		QueryAccmulate();
	}
	//分保数据导入
	if(Field.value=='22'){
		divTitle.style.display='none';
		divEndDate.style.display='none';
		divList.style.display='';
		divAccmulate.style.display='none';
		divTaskList.style.display='';
		QueryTaskList();
	}
	//业务数据删除
	if(Field.value=='31'){
		divTitle.style.display='none';
		divEndDate.style.display='none';
		divList.style.display='none';
	}
	//分保数据删除
	if(Field.value=='32'){
		divTitle.style.display='none';
		divEndDate.style.display='none';		
		divList.style.display='none';
	}

}

function SubmitForm(){
	var endDate = fm.EndDate.value;
	var processType = fm.ProcessType.value;
	
	if(processType=='21'){
		var chkFlag=false; 
		if(endDate==''){
			myAlert(""+"请选择截止日期！"+"");
			return false;
		}
		if(processType==''){
			myAlert(""+"请选择导入类型！"+"");
			return false;
		}
	}
	if(processType=='21'){
		var selCount=AccmulateGrid.mulLineCount;
		for (i=0;i<selCount;i++){
			if(AccmulateGrid.getChkNo(i)==true){
				chkFlag=true;
			}
		}
		if(!chkFlag){
			myAlert(""+"请选择要进行业务数据提取的累计风险!"+"");
			return false;
		}
	}
	if(processType=='22'){
		var selCount=TaskListGrid.mulLineCount;
		for (i=0;i<selCount;i++){
			if(TaskListGrid.getChkNo(i)==true){
				chkFlag=true;
			}
		}
		if(!chkFlag){
			myAlert(""+"请选择要进行再保数据提取的任务!"+"");
			return false;
		}
	}
	try {
		var i = 0;
		var showStr=""+"正在进行业务数据导入操作"+" "+"，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
		showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");  
		fm.submit();
    
	}catch(ex){
		showInfo.close();
		myAlert(ex);
	}
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content ){
	showInfo.close();
	if (FlagStr == "Fail" ){
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(content));
		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	}else{
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+encodeURI(encodeURI(content));
		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	}
	initForm();
}
