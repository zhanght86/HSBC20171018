//程序名称：PDUM.js
//程序功能：险种核保规则定义
//创建日期：2009-3-14
//该文件中包含客户端需要处理的函数和事件
var businesscode='businesscode';
var turnPage = new turnPageClass();
//定义sql配置文件
var tResourceName = "productdef.PDUMInputSql";
var showInfo;	function returnParent(){		top.opener.focus();		top.close();}
function setBusiness(){
	try{
	var code=fm.all("BusinessCode").value;
	//alert(code);
	if(code==null||code==''||code=='null')return;
	businesscode=code;
	}catch(ex){
		try{
			var code1=fm.all("STANDBYFLAG2").value;
			//alert(code);
			if(code1==null||code1==''||code1=='null')return;
			businesscode=code1;			
		}catch(ex){}
		
		
	}
}
function checkNullable()
{
  if(!verifyInput())
  {
  	return false;
  }
  
  return true;
}
function setCalCode(tCalCode)
{
	initCalCodeMain(document.getElementById('RiskCode').value,tCalCode);	
	document.getElementById('CALCODE').value = tCalCode;
}

function submitForm()
{
	setBusiness();
	if(fm.all("IsReadOnly").value == "1"){
		myAlert(""+"您无权执行此操作"+"");
		return;
	}
	if(!checkNullable())
	{
		return false;
	}
  //var showStr="正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
//var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
  lockPage(""+"正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"");
  fm.submit();
}

function afterSubmit( FlagStr, content )
{
	unLockPage();
 	// showInfo.close();
	if (FlagStr == "Fail" ){             
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+content;
	    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	}else{
		var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+content;
	    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	    if(fm.all("operator").value!="del"){
    		try{
			    if(businesscode!='businesscode'){InputCalCodeDefFace(businesscode);}
			    else if(mType==null||mType=='null'||mType==''){InputCalCodeDefFace('01');}
			    else if("TB"==mType){InputCalCodeDefFace('01');}
			    else if("EDOR"==mType){InputCalCodeDefFace('02');}   	
    		}catch(ex){InputCalCodeDefFace('01');}
    			var selNo = 0;
    	if(fm.all("operator").value=="save"){
   			Mulline10Grid.addOne();
   			selNo = Mulline10Grid.mulLineCount;
    	}
    	else if(fm.all("operator").value=="update"){
    		selNo = Mulline10Grid.getSelNo();
    	}
		Mulline10Grid.setRowColData(selNo-1,1,document.getElementById('UWCODE').value);
		Mulline10Grid.setRowColData(selNo-1,6,document.getElementById('RELAPOLTYPE').value);
		Mulline10Grid.setRowColData(selNo-1,7,document.getElementById('UWTYPE').value) ;
		Mulline10Grid.setRowColData(selNo-1,8,document.getElementById('STANDBYFLAG1').value) ;
		//Mulline10Grid.setRowColData(selNo-1,9,document.getElementById('STANDBYFLAG2').value) ;
		Mulline10Grid.setRowColData(selNo-1,4,document.getElementById('UWORDER').value) ;
		Mulline10Grid.setRowColData(selNo-1,5,document.getElementById('REMARK').value) ;
		Mulline10Grid.setRowColData(selNo-1,2,document.getElementById('CALCODE').value);
		Mulline10Grid.setRowColData(selNo-1,3,document.getElementById('RiskCode').value);
		try{
			if(Mulline10Grid.canSel==1&&Mulline10Grid.mulLineCount==1){
				//alert('@@');
				fm.all('Mulline10Grid').checked=true;
				//eval("fm.all('MultMainRiskGridSel')[" + MultMainRiskGrid.mulLineCount + "-1].checked=true");
 			}else{
 				if(fm.all("operator").value=="save"){
					eval("fm.all('Mulline10GridSel')[" + selNo + "-1].checked=true")
				}
 			}
 		}
 		catch(Ex){}
  		}
  		else{
  			initForm(); 
		}
		//initForm();    
	}
	initMulline10Grid();
	queryMulline10Grid();
	initInput();
}
var Mulline9GridTurnPage = new turnPageClass(); 
var Mulline10GridTurnPage = new turnPageClass(); 
function save()
{
	if(fm.all("UWCODE").value.length<2){
		myAlert(""+"核保编码应大于一位"+"");
		return;
		
		}
	document.getElementById('CALCODE').value='';
	 fm.all("operator").value="save";
	fm.all("tableName").value='PD_LMUW'; 
 try{
 if(fm.all("RELAPOLTYPE").value=='IF'||fm.all("RELAPOLTYPE").value=='SM'){
	 fm.all("tableName").value='PD_LMCheckField'; 
 }
 }catch(ex){
		fm.all("tableName").value='PD_LMUW';  
 }
 submitForm();
}
function update()
{
	var selNo = Mulline10Grid.getSelNo();
	if( selNo == 0 || selNo == null )
	{
		myAlert(""+"请选择一条记录!"+"");
		return;	
	}
	fm.all("operator").value="update";
	fm.all("tableName").value='PD_LMUW'; 
	if(fm.all("UWCODE").value.length<2){
		myAlert(""+"核保编码应大于一位"+"");
		return;
	}
	 try{
	 if(fm.all("RELAPOLTYPE").value=='IF'||fm.all("RELAPOLTYPE").value=='SM'){
		 fm.all("tableName").value='PD_LMCheckField'; 
	 }
	 }catch(ex){
			fm.all("tableName").value='PD_LMUW';  
	 }
 submitForm();
}
function del()
{
 var selNo = Mulline10Grid.getSelNo();
 if( selNo == 0 || selNo == null )
 {
 	myAlert(""+"请选择一条记录!"+"");
 	return;	
 }	
 fm.all("operator").value="del";
	fm.all("tableName").value='PD_LMUW'; 
	 try{
	 if(fm.all("RELAPOLTYPE").value=='IF'||fm.all("RELAPOLTYPE").value=='SM'){
		 fm.all("tableName").value='PD_LMCheckField'; 
	 }
	 }catch(ex){
			fm.all("tableName").value='PD_LMUW';  
	 }
 submitForm();
}
function button163()
{
  var selNo = Mulline10Grid.getSelNo();
	if(selNo == 0)
	{
		myAlert(""+"请选中算法行再点击"+"");
		return;
	}
	else
	{
		var name = Mulline10Grid.getRowColData(selNo-1,2);
		if(name == null || name =="")
		{
			myAlert(""+"请先录入算法编码"+"");
			return;
		}
	}
	
	showInfo = window.open("PDAlgoDefiMain.jsp?riskcode=" + fm.all("RiskCode").value+ "&AlgoCode=" + Mulline10Grid.getRowColData(selNo-1,2));

}	
		
function afterQueryAlgo(AlgoCode)
{
	var selNo = Mulline9Grid.getSelNo();
	if(selNo == 0)
	{
		myAlert(""+"选中行的焦点丢失"+"");
		return;
	}
	
	Mulline9Grid.setRowColDataCustomize(selNo-1,4,AlgoCode);
}
		
		
	  

function button164()
{
  showInfo = window.open("");
}
function button165()
{
  showInfo = window.open("");
}
function button169()
{
  showInfo = window.open("");
}

function queryMulline9Grid()
{
   var strSQL = "";
   var mySql=new SqlClass();
	 var sqlid = "PDUMInputSql1";
	 mySql.setResourceName(tResourceName); //指定使用的properties文件名
	 mySql.setSqlId(sqlid);//指定使用的Sql的id
	 mySql.addSubPara("sqlid");//指定传入的参数
   strSQL = mySql.getString();
   //tongmeng 2011-07-13 modify
   // 
   var arrInfo = easyExecSql(strSQL);
   if(arrInfo.length>0)
   {
   	for(i=0;i<arrInfo.length;i++)
   	{
   		try{
   		var tFieldName = arrInfo[i][1];
   		var tTitle = arrInfo[i][4];
   		if(tTitle!=null&&tTitle!='')
   		{
   			// tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "').setAttribute('title',\"" + newData + "\")";
   			document.getElementById(tFieldName).title = tTitle;
   		}
   		}
   		catch(Ex)
   		{
   			}
   	}
   }
   
   
   
 //  Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
}
function queryMulline10Grid()
{
   var strSQL = "";
   var mySql=new SqlClass();
	if(mType==null||mType=='')
   {
	 //var sqlid = "PDUMInputSql2";
	// mySql.setResourceName(tResourceName); //指定使用的properties文件名
	// mySql.setSqlId(sqlid);//指定使用的Sql的id
	// mySql.addSubPara(fm.RiskCode.value);//指定传入的参数
   //strSQL = mySql.getString();
  }
   else if(mType=='TB'||mType=='EDOR')
  {
  	 var sqlid = "PDUMInputSql3";
	 mySql.setResourceName(tResourceName); //指定使用的properties文件名
	 mySql.setSqlId(sqlid);//指定使用的Sql的id
	 mySql.addSubPara(fm.RiskCode.value);//指定传入的参数
	 
   strSQL = mySql.getString();
   Mulline10GridTurnPage.pageLineNum  =10;
   Mulline10GridTurnPage.queryModal(strSQL,Mulline10Grid);
  }
  else
  {
  	 var sqlid = "PDUMInputSql4";
	 mySql.setResourceName(tResourceName); //指定使用的properties文件名
	 mySql.setSqlId(sqlid);//指定使用的Sql的id
	  mySql.addSubPara(fm.QueRiskCode.value);//指定传入的参数
	 mySql.addSubPara(mEdorType);//指定传入的参数
   strSQL = mySql.getString();
   Mulline10GridTurnPage.pageLineNum  =10;
   Mulline10GridTurnPage.queryModal(strSQL,Mulline10Grid);
  }
  

}
function query()
{  	 var mySql=new SqlClass();
 	 var sqlid = "PDUMInputSql5";
	 mySql.setResourceName(tResourceName); //指定使用的properties文件名
	 mySql.setSqlId(sqlid);//指定使用的Sql的id
	 //指定传入的参数
	 
	 mySql.addSubPara(fm.all("UWCODE1").value);
	 mySql.addSubPara(fm.all("RELAPOLTYPE1").value);
	 mySql.addSubPara(fm.all("STANDBYFLAG11").value);
	 mySql.addSubPara(fm.all("STANDBYFLAG21").value);
	 mySql.addSubPara(fm.all("UWCODE1").value);
	 mySql.addSubPara(fm.all("RELAPOLTYPE1").value);
	 mySql.addSubPara(fm.all("STANDBYFLAG11").value);
	 mySql.addSubPara(fm.all("STANDBYFLAG21").value); 
	 mySql.addSubPara(fm.all("QueRiskCode").value); 
	 var strSQL = mySql.getString();
	 Mulline10GridTurnPage.pageLineNum  =10;
	 Mulline10GridTurnPage.queryModal(strSQL,Mulline10Grid);
}
function isshowbutton()
{   var value=getQueryState1();
	if(value=='0'||value==0){
		document.getElementById('savabuttonid').style.display = 'none';
		document.getElementById('checkFunc').style.display = '';
	}else{
		document.getElementById('savabuttonid').style.display = '';
		document.getElementById('checkFunc').style.display = 'none';
	}
}

//-------- add by jucy
function InputCalCodeDefFace2(){
	var selNo = Mulline10Grid.getSelNo();
	if(selNo == 0)
	{
		myAlert(""+"请先选择一条规则信息，再点击【查看规则内容】。"+"");
		return;
	}
	InputCalCodeDefFace();
}
//-------- end