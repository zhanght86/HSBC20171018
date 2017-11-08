

//程序名称：PDSugIncomeData.js
//程序功能：收益数据算法定义
//创建日期：2011-10-13
var turnPage = new turnPageClass();
var Mulline10GridTurnPage = new turnPageClass();

function profitHeadPreview(){
	if(fm.all("TYPE").value ==null||fm.all("TYPE").value==""){
		myAlert(""+"请选择表头类型!"+"");
		fm.all("TYPE").focus();
		return;
	}
	dialogURL="./PDSugRiskProfitPreviewMain.jsp?riskcode="+riskCode+"&type="+fm.all("TYPE").value;  
	showModalDialog(dialogURL,window,"status:no;help:0;close:0;dialogWidth:1000px;dialogHeight:500px");
}
function typeManage(){
	showInfo = window.open("PDSugTabHeadTypeMain.jsp");
}
function tabHeadQueryGrid(){
    var mySql = new SqlClass();
    mySql.setResourceName("productdef.PDSugTabHeadConfInputSql");
    mySql.setSqlId("PDSugTabHeadConfInputSql1");
    mySql.addSubPara(riskCode);
    //turnPage.pageLineNum  = 10;
    turnPage.queryModal(mySql.getString(), TabHeadConfGrid);
}

function queryChangedGrid(){
	if(document.getElementById("TYPE").value ==""||document.getElementById("TYPE").value==null){
		myAlert(""+"请选择表头类型!"+"");
		document.getElementById("TYPE").focus();
		return;
	}
	var strSQL = "";
    var mySql=new SqlClass();
	var sqlid = "PDSugTabHeadConfInputSql2";
	mySql.setResourceName("productdef.PDSugTabHeadConfInputSql"); //指定使用的properties文件名
	mySql.setSqlId(sqlid);//指定使用的Sql的id
	mySql.addSubPara(riskCode);//指定传入的参数
	mySql.addSubPara((document.getElementById("TYPE").value==""||document.getElementById("TYPE").value==null)?"":document.getElementById("TYPE").value);
	strSQL = mySql.getString();
	Mulline10GridTurnPage.pageLineNum  = 9999;
    Mulline10GridTurnPage.queryModal(strSQL,Mulline10Grid);
    
}
function update(){
	var selNo = Mulline10Grid.getSelNo();
	if( selNo == 0 || selNo == null ){
		myAlert(""+"请选择一条记录!"+"");
	 	return;	
	}
    var itemcode = document.getElementById('hiddenItemCode').value;
    var calelement = document.getElementById('hiddenCalElement').value;
	showInfo = window.open("PDSugIncomeFactorMain.jsp?riskcode="+riskcode+"&itemcode="+itemcode+"&calelement="+calelement+"&operator=update");
}
function del(){
	var selNo = Mulline10Grid.getSelNo();
	if( selNo == 0 || selNo == null )
	{
		myAlert(""+"请选择一条记录!"+"");
		return;	
	}	

	fm.all("tablename").value="PD_CalcuteElemet";
	submitForm();
}

function returnParent(){
	top.opener.focus();
	top.close();
}
 
function save(){
	if(TabHeadConfGrid.getRowColData(0, 1) == null || TabHeadConfGrid.getRowColData(0, 1) == ""){
		myAlert(""+"没有数据"+","+"请添加数据！"+"");
		return;
	}
	SaveTabHeadConfGrid.clearData();
	var isCheck = "";
    var iCount = 0;
    var rowNum = TabHeadConfGrid.mulLineCount;   
    var arr = new Array();
     for (var i = 0; i < rowNum; i++){
        isCheck = TabHeadConfGrid.getChkNo(i); 
        if (isCheck){
            arr[iCount] = TabHeadConfGrid.getRowColData(i, 1);
            iCount = iCount + 1;
        }
    }
    if (iCount == 0){
        myAlert(""+"请选择！"+"");
        return;
    }
    var tSelfCount = SaveTabHeadConfGrid.mulLineCount; 
    var Count = iCount + tSelfCount;
	for (var i = tSelfCount; i < Count; i++){
		var j = i - tSelfCount;
		SaveTabHeadConfGrid.addOne();
		SaveTabHeadConfGrid.setRowColData(j, 1, arr[j]);
	}
	submitForm();
}
function submitForm(){
	if(fm.all("IsReadOnly").value == "1"){
	  	myAlert(""+"您无权执行此操作"+"");
	  	return;
	}
	if(!checkNullable()){
		return false;
	}
	lockPage(""+"正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"");
	fm.submit();
}
function checkNullable()
{
  if(!verifyInput())
  {
  	return false;
  }
  
  return true;
}
function afterSubmit( FlagStr, content ){
	unLockPage();
	if (FlagStr == "Fail" ){             
	    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
	    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	}
	else{
  	    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
    	showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
		if(document.getElementById("operator").value =='del' ){
			init();
		}else{
			initForm();
			queryChangedGrid();
		}
		
	}
}

