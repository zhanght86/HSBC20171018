var showInfo;

var turnPage = new turnPageClass(); 
window.onfocus=myonfocus;

function myonfocus(){
	if(showInfo!=null){
	  try{
	    showInfo.focus();  
	  }
	  catch(ex){
	    showInfo=null;
	  }
	}
}

//提交，保存按钮对应操作
function submitForm(){
	fm.OperateType.value="INSERT";
	try {
		if(  verifyInput() == true&&RGrid.checkValue("RGrid")) 
		{
			
		  	var i = 0;
		  	var showStr=""+"正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
		  	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
				fm.action="./LRConfigRelaSave.jsp";
		  	fm.submit(); //提交
	  	
	  }
  } catch(ex){
  	myAlert(ex);
  	showInfo.close( );
  	
  }
}
function qeuryGrid(){
	var tAccumulateno=fm.AccumulateDefNO.value;
	var mySql100=new SqlClass();
		mySql100.setResourceName("reinsure.LRConfigRelaInputSql"); //指定使用的properties文件名
		mySql100.setSqlId("LRConfigRelaInputSql100");//指定使用的Sql的id
		mySql100.addSubPara(tAccumulateno);//指定传入的参数
	    sql=mySql100.getString();
	
	//sql="select distinct a.ACCUMULATEDEFNO,a.UNIONACCNO from RIUnionAccumulate a where a.ACCUMULATEDEFNO='"+tAccumulateno+"'";

	turnPage.queryModal(sql,RelateGrid);

	
}

function queryClick(){
  fm.OperateType.value="QUERY";
	
  
  window.open("./FrameLRConfigRelaQuery.jsp?Serial=");
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content, ReComCode, CertifyCode ){
  showInfo.close();
  if (FlagStr == "Fail" ) {             
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(content));
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
  } else { 
	  //content="保存成功！";
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+encodeURI(encodeURI(content));
	  showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	  if(fm.OperateType.value=="DELETE"){
	  	resetForm();
	  }
	  initRelateGrid();
    qeuryGrid();
  }
}

function updateClick(){
	fm.OperateType.value="UPDATE";
	if(!confirm(""+"你确定要修改累计方案吗？"+"")){
		return false;
	}
	try {
		if( verifyInput() == true && RelateGrid.checkValue("RelateGrid")) {	
			
		  	var i = 0;
		  	var showStr=""+"正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
		  	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
				
				fm.action="./LRConfigRelaSave.jsp";
		  	fm.submit(); //提交
	  	
	  }
  } catch(ex) 
  {
  	showInfo.close( );
  	myAlert(ex);
  }
}

function deleteClick(){
	fm.OperateType.value="DELETE";
	if(!confirm(""+"你确定要删除该累计方案吗？"+"")){
		return false;
	}
	try {
		if( verifyInput() == true && RelateGrid.checkValue("RelateGrid")) {
			var i = 0;
		  var showStr=""+"正在删除数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
		  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
			fm.action="./LRConfigRelaSave.jsp";
		  fm.submit(); //提交
	  }else{
	  	
	  }
  } catch(ex){
  	showInfo.close( );
  	myAlert(ex);
  }
}

function configRelation(){
	window.open("./FrameConfigRelation.jsp?Serial=");
}


function veriryInput4(){
	return true;
}
function inputRela(){
	fm.UNIONACCNO.value="";
	RGrid.clearData();
}
function afterQuery(){
}

//重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm(){
  try{
	  initForm();
  }
  catch(re){
  	myAlert(""+"在CertifySendOutInput.js"+"-->"+"resetForm函数中发生异常:初始化界面错误!"+"");
  }
} 

//提交前的校验、计算  
function beforeSubmit(){
  //添加操作	
}   

function afterCodeSelect( cCodeName, Field ){
	//选择了处理
	if( cCodeName == "detailflag"){
		switch(fm.all('DeTailFlag').value){
			case "01":
			//risk
			divCertifyType1.style.display='';
			divCertifyType2.style.display='none';
			fm.DeTailType.value="RISK";
			break;
			
			case "02":
			//duty
			divCertifyType1.style.display='none';
			divCertifyType2.style.display='';
			fm.DeTailType.value="DUTY";
			break;
		}
	}
}
function showRela(){

	var tRow=RelateGrid.getSelNo();
	fm.UNIONACCNO.value=RelateGrid.getRowColData(tRow-1,2);
	var mySql101=new SqlClass();
		mySql101.setResourceName("reinsure.LRConfigRelaInputSql"); //指定使用的properties文件名
		mySql101.setSqlId("LRConfigRelaInputSql101");//指定使用的Sql的id
		mySql101.addSubPara(RelateGrid.getRowColData(tRow-1,1));//指定传入的参数
		mySql101.addSubPara(RelateGrid.getRowColData(tRow-1,2));//指定传入的参数
	var strSQL=mySql101.getString();
	//var strSQL="select a.associatedcode,b.associatedname from RIUnionAccumulate a,RIAccumulateRDCode b where a.accumulatedefno=b.accumulatedefno and a.associatedcode=b.associatedcode and a.accumulatedefno='"+RelateGrid.getRowColData(tRow-1,1)+"' and a.unionaccno='"+RelateGrid.getRowColData(tRow-1,2)+"'";
	strArray = easyExecSql(strSQL);
	if(strArray!=null){
		RGrid.clearData();
		for (var k=0;k<strArray.length;k++){
					RGrid.addOne("RGrid");
					RGrid.setRowColData(k,1,strArray[k][0]);
					RGrid.setRowColData(k,2,strArray[k][1]);
					
				}
	}
	
	
	
}
function ClosePage(){
	top.close();
}
