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
		if( verifyInput2() == true && RelateGrid.checkValue("RelateGrid")) 
		{
			if (veriryInput3()==true){
		  	var i = 0;
		  	var showStr=""+"正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
		  	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
				fm.action="./LRAccRDSave.jsp";
		  	fm.submit(); //提交
	  	}
	  	else{
	  	}
	  }
  } catch(ex){
  	showInfo.close( );
  	myAlert(ex);
  }
}

function queryClick(){
  fm.OperateType.value="QUERY";
  
  window.open("./FrameAccRDQuery.jsp?Serial=");
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
  }
}

function updateClick(){
	fm.OperateType.value="UPDATE";
	if(!confirm(""+"你确定要修改累计方案吗？"+"")){
		return false;
	}
	try {
		if( verifyInput() == true && RelateGrid.checkValue("RelateGrid")) {	
			if (veriryInput3()==true){
		  	var i = 0;
		  	var showStr=""+"正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
		  	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
				
				fm.action="./LRAccRDSave.jsp";
		  	fm.submit(); //提交
	  	}
	  	else{
	  	}
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
			fm.action="./LRAccRDSave.jsp";
		  fm.submit(); //提交
	  }else{
	  	
	  }
  } catch(ex){
  	showInfo.close( );
  	myAlert(ex);
  }
}

function configRelation(){
	var accumulateno=fm.AccumulateDefNO.value;
	SQL="select * from RIAccumulateDef where accumulatedefno='"+accumulateno+"'";
	var strArray = easyExecSql(SQL);
  	if (strArray==null){
  		myAlert(""+"请先保存指定的累计风险！！"+"")
  		return;
  	}
	window.open("./FrameConfigRelation.jsp?accumulatedefno="+accumulateno,true);
}

function veriryInput3(){
	var tGridLen ;
	if(fm.DeTailFlag.value=="01")
	{
		for(var i=0;i<RelateGrid.mulLineCount;i++) 
		{ 
			//var tSQL = "select AccumulateDefNO,AssociatedName from RIAccumulateRDCode where 1=1 and AssociatedCode='"+RelateGrid.getRowColData(i,1)+"' and AccumulateDefNO<>'"+fm.AccumulateDefNO.value+"'";
			
			var mySql1=new SqlClass();
				mySql1.setResourceName("reinsure.LRAccRDInputSql"); //指定使用的properties文件名
				mySql1.setSqlId("LRAccRDInputSql001");//指定使用的Sql的id
			    mySql1.addSubPara(RelateGrid.getRowColData(i,1));//指定传入的参数
			    mySql1.addSubPara(fm.AccumulateDefNO.value);//指定传入的参数
			tSQL=mySql1.getString();			
			
			
			var result = easyExecSql(tSQL);	
			if(result!=null&&result!="")
			{		
				if (!confirm(""+"险种："+""+RelateGrid.getRowColData(i,1)+""+"已经在累计风险"+""+result[0][1]+""+"中定义，是否继续？"+"")) 
				{
					return false;
				}
			}

		}
	}else{
		for(var i=0;i<DutyGrid.mulLineCount;i++) 
		{ 
			//var tSQL = "select AccumulateDefNO,AssociatedName from RIAccumulateRDCode where 1=1 and AssociatedCode='"+DutyGrid.getRowColData(i,1)+"' and AccumulateDefNO<>'"+fm.AccumulateDefNO.value+"'";
			
			var mySql1=new SqlClass();
				mySql1.setResourceName("reinsure.LRAccRDInputSql"); //指定使用的properties文件名
				mySql1.setSqlId("LRAccRDInputSql002");//指定使用的Sql的id
			    mySql1.addSubPara(DutyGrid.getRowColData(i,1));//指定传入的参数
			    mySql1.addSubPara(fm.AccumulateDefNO.value);//指定传入的参数
			tSQL=mySql1.getString();	
						
			var result = easyExecSql(tSQL);
			if(result!=null&&result!="")
			{
				if (!confirm(""+"责任："+""+RelateGrid.getRowColData(i,1)+""+"已经在累计风险"+""+result[0][1]+""+"中定义，是否继续？"+"")) 
				{
					return false;
				}
			}
		}
	}
	return true;
}
function veriryInput4(){
	return true;
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
