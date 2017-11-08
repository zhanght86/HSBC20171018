var showInfo;
var turnPage = new turnPageClass(); 
var turnPage1 = new turnPageClass(); 
var turnPage2 = new turnPageClass(); 
var turnPage3 = new turnPageClass(); 
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
function schemaSubmit(){
	fm.OperateType.value="SCHMINSERT";
	try {
//		if(verifyInput() == true) { 
//	if(verifyInputA1() == true&&checkPreceptState()){ 
	if(fm.PreceptState.value=="01"){//如果状态有效，要校验是否配置了累计方案算法
		//var tsql="select 'X' from riprecept where ripreceptno = '"+fm.RIPreceptNo.value+"' and arithmeticid is not null";
		var mySql100=new SqlClass();
			mySql100.setResourceName("reinsure.LRSchemaInputSql"); //指定使用的properties文件名
			mySql100.setSqlId("LRSchemaInputSql100");//指定使用的Sql的id
			mySql100.addSubPara(fm.RIPreceptNo.value);//指定传入的参数
	    var tsql=mySql100.getString();
		
		var result=easyExecSql(tsql);
		if(result==null){
			myAlert(""+"没有配置临分算法或方案未录入完成，不能置为有效状态!"+"");
			return false;
		}
	}
	if(verifyInputA1() == true){
			if (veriryInput1()==true){
		  	var i = 0; 
		  	var showStr=""+"正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
		  	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
				
				fm.action="./LRSchemaSave.jsp";
		  	fm.submit(); //提交
	  	}
	  }
  } catch(ex) 
  {
  	showInfo.close( );
  	myAlert(ex);
  }
}
//溢额线设置 ,分保比例线设置
function divComSubmit(){
	fm.OperateType.value="DIVCOMINSERT";
	try {
		if(verifyInputA1() == true  && verifyInputA2() == true ) {
			if (veriryInput3()==true){
				if (!confirm(""+"修改溢额线设置和分保公司设置数据，需要重新进行分保比例设置。确定吗?"+"")) 
				{
					return false;
				}
		  	var i = 0;
		  	var showStr=""+"正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
		  	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 				
				fm.action="./LRSchemaSave.jsp";
		  	fm.submit(); //提交
	  	}
	  }
  } catch(ex) {
  	showInfo.close( );
  	myAlert(ex);
  }
}
/**
	设置分保比例保存
*/
function scaleSubmit(){
	fm.OperateType.value="SCALEINSERT";
	try {
		if(verifyInput()== true){
			if (veriryInput4()==true){
		  	var i = 0;
		  	var showStr=""+"正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
		  	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
				
				fm.action="./LRSchemaSave.jsp";
		  	fm.submit(); //提交
	  	}
	  }
  } catch(ex) {
  	showInfo.close();
  	myAlert(ex);
  }
}
/**
	分保要素保存
*/
function factorSubmit(){
	fm.OperateType.value="FACTORINSERT";
	try { 
	if(verifyInputA1()==true  &&  verifyInputA2()==true  
	   && verifyInput()==true  && veriryInput4()==true
	   && verifyInputA3()==true  ){ 
			if (veriryInput7()==true){
		  	var i = 0;
		  	var showStr=""+"正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
		  	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
				
				fm.action="./LRSchemaSave.jsp";
		  	fm.submit(); //提交
	  	}
	  }
  } catch(ex) {
  	showInfo.close();
  	myAlert(ex);
  }
	return true;
}

/**
	设置分保费率佣金率
*/
function feerateSubmit(){
	fm.OperateType.value="FEERATEINSERT"; 
	try { 
		//var tSql="select 'X' from RIAssociateFeeTable where ripreceptno='"+fm.RIPreceptNo.value+"'";
		var mySql101=new SqlClass();
			mySql101.setResourceName("reinsure.LRSchemaInputSql"); //指定使用的properties文件名
			mySql101.setSqlId("LRSchemaInputSql101");//指定使用的Sql的id
			mySql101.addSubPara(fm.RIPreceptNo.value);//指定传入的参数
	    var tSql=mySql101.getString();
		var result=easyExecSql(tSql);
		//alert(result);
		if(result!=null){
			myAlert(""+"已经定义了分保方案险种费率表，不能再定义费率佣金表！"+"");
			return false;
		}
		if( verifyInput()){ 
			var i = 0; 
		  var showStr=""+"正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+""; 
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
		  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
			
			fm.action="./LRSchemaSave.jsp"; 
		  fm.submit(); //提交 
		} 
  } catch(ex) {
  	showInfo.close(); 
  	myAlert(ex);
  }
}

function veriryInput1(){
	return true;
}
//溢额线设置 ,分保比例线设置 校验
function veriryInput3()
{
	if(!ContCessGrid.checkValue("ContCessGrid")){return false;} //如果ContCessGrid没有数据，则返回假
	if(!ScaleLineGrid.checkValue("ScaleLineGrid")){return false;} 
	
	//var strSQL="select ComPanyNo,ComPanyName from RIComInfo where Remark='1'";
	//var strArray=easyExecSql(strSQL);
	//if(strArray!=null){
	//	strSQL="select Remark from RIComInfo where companyno='"+ScaleLineGrid.getRowColData(0,1)+"'";
	//	var strResult=easyExecSql(strSQL);
	//	if(strResult[0]!="1"){
	//		alert("分保比例线设置首行必须为本保险公司（"+strArray[0][1]+")");
	//		return false;
	//	}
	//}
	return true;
}

function veriryInput4(){
	var rowNum=CessScaleGrid. mulLineCount; 
	var colNum=CessScaleGrid.colCount;
	if(rowNum==0){
		myAlert(""+"分保比例设置列表为空"+"");
		return false;
	}
	for(var i=0;i<rowNum;i++){
		var amount=0.0;
		for(var j=2;j<colNum;j++){
			var amt=CessScaleGrid.getRowColData(i,j);
			amount=amount+parseFloat(amt);
		}
		if(amount>1){
			myAlert(""+(CessScaleGrid.getRowColData(i,1))+   "  "+"分保比例总和不能大于1"+"");
			return false;
		}
	}
	return true;
}
//Delete校验
function veriryInput5(){
	return true;
}

//Update校验
function veriryInput6(){	
	return true;
}
//要素校验
function veriryInput7()
{
	//校验不能录入重复合同要素
	for(var n=0;n<FactorGrid.mulLineCount;n++) 
	{ 
	   for(var m=n+1;m<FactorGrid.mulLineCount;m++) 
	   { 
	     if(FactorGrid.getRowColData(n,1)==FactorGrid.getRowColData(m,1)) 
	     {
	         myAlert(""+"不能录入重复要素!"+"");	
	         return false; 
	     }
	   }
	}    
	for(var n=0;n<FactorGrid.mulLineCount;n++) { 
	   for(var m=n+1;m<FactorGrid.mulLineCount;m++) { 
	     if(FactorGrid.getRowColData(n,2)==FactorGrid.getRowColData(m,2)) {
	         myAlert(""+"不能录入重复要素!"+"");	
	         return false; 
	     }
	   }
	}  
	isNull=false; //检查是否录入要素值标记
	for(var i=0;i<FactorGrid.mulLineCount;i++) {
		if(FactorGrid.getRowColData(i,3)==null||FactorGrid.getRowColData(i,3)==""){
			isNull=true;
		}
	}
	if(isNull){
		myAlert(""+"必须录入要素值!"+"");
		return false;
	}
	return true;
}

function queryClick(){
  fm.OperateType.value="QUERY";
 /**
  var strSQL="select a.RIContNo A1,a.RIPreceptNo A2,a.RIPreceptName A3,a.AccumulateDefNO A4,"
  + " (select AccumulateDefName from RIAccumulateDef where a.AccumulateDefNO= AccumulateDefNO) A5, "
  + " a.CompanyNum A6,a.DivisionNum A7, "
  + " a.arithmeticid A8,"
  + " (select arithmeticdefname from ricaldef where arithmeticdefid=a.arithmeticid) A9,"
  + " a.PreceptType A10, "
  + " (case when a.PreceptType='01' or a.PreceptType is null or a.PreceptType='' then '正常再保方案' else '临时分保方案' end) A11,"
  + " '' A12,"
  + " '' A13, "
  + " '' A14,"
  + " '' A15, "
  + " (case a.state when '01' then '有效' when '02' then '未生效' else '未生效' end) A16,a.state A17 ,standbystring2 A18," 
  + " standbystring1 A19,(select codename from ldcode where code = a.standbystring1 and codetype = 'reinsuresubtype') A20, "
  + " standbystring3 A21,(select codename from ldcode where code = a.standbystring3 and codetype = 'hierarchynumtype') A22 "
  + " from RIPrecept a where a.RIContNo='"+fm.RIContNo.value+"' order by a.accumulatedefno ,a.ripreceptno ";
  */
  var mySql102=new SqlClass();
	  mySql102.setResourceName("reinsure.LRSchemaInputSql"); //指定使用的properties文件名
	  mySql102.setSqlId("LRSchemaInputSql102");//指定使用的Sql的id
	  mySql102.addSubPara(fm.RIContNo.value);//指定传入的参数
  var strSQL=mySql102.getString();
  turnPage.queryModal(strSQL,PreceptSearchGrid);
}

//显示再保方案
function showPrecept(){
  var tSel = PreceptSearchGrid.getSelNo();	
  if(PreceptSearchGrid.getRowColData(tSel-1,10)=='02'){
  	fm.RelaTempPolBut.style.display='';
  	fm.RelaGrpTempPolBut.style.display='none';
  	divArithmetic.style.display='';
  	fm.ArithmeticID.style.display='';
  	fm.ArithmeticName.style.display='';
  	
  }else{
  	fm.RelaTempPolBut.style.display='none'; 
  	fm.RelaGrpTempPolBut.style.display='none';
  	divArithmetic.style.display='none'; 
  	fm.ArithmeticID.style.display='none'; 
  	fm.ArithmeticName.style.display='none'; 
  	
  	fm.CalFeeType.value='';
  	fm.CalFeeTypeName.value='';
  	fm.CalFeeTerm.value='';
  	fm.CalFeeTermName.value='';  	
  	fm.ArithmeticID.value=''; 
  	fm.ArithmeticName.value='';
	}
	fm.all('RIContNo').value 		 	= PreceptSearchGrid.getRowColData(tSel-1,1);
	fm.all('RIPreceptNo').value	 		= PreceptSearchGrid.getRowColData(tSel-1,2);
	fm.all('RIPreceptName').value	    = PreceptSearchGrid.getRowColData(tSel-1,3);
	fm.all('AccumulateDefNO').value 	= PreceptSearchGrid.getRowColData(tSel-1,4);
	fm.all('AccumulateDefName').value	= PreceptSearchGrid.getRowColData(tSel-1,5);
	fm.all('CompanyNum').value  			= PreceptSearchGrid.getRowColData(tSel-1,6);
 	fm.all('DivisionNum').value		 		= PreceptSearchGrid.getRowColData(tSel-1,7);
 	fm.all('ArithmeticID').value			= PreceptSearchGrid.getRowColData(tSel-1,8); 
 	fm.all('ArithmeticName').value		= PreceptSearchGrid.getRowColData(tSel-1,9);
 	fm.all('PreceptType').value				= PreceptSearchGrid.getRowColData(tSel-1,10);
	fm.all('PreceptTypeName').value   = PreceptSearchGrid.getRowColData(tSel-1,11);
  
 	fm.all('CompanyNumBackup').value  = PreceptSearchGrid.getRowColData(tSel-1,6);
 	fm.all('DivisionNumBackup').value	= PreceptSearchGrid.getRowColData(tSel-1,7);
 	fm.all('PreceptState').value = PreceptSearchGrid.getRowColData(tSel-1,17);
 	fm.all('ReinsuranceType').value = PreceptSearchGrid.getRowColData(tSel-1,18);
 	
 	fm.all('ReinsuranceSubType').value = PreceptSearchGrid.getRowColData(tSel-1,19);
	fm.all('ReinsuranceSubTypeName').value = PreceptSearchGrid.getRowColData(tSel-1,20); 	
 	
 	fm.all('HierarchyNumType').value = PreceptSearchGrid.getRowColData(tSel-1,21);
	fm.all('HierarchyNumTypeName').value = PreceptSearchGrid.getRowColData(tSel-1,22);
 
 
 	
 	
	if(fm.ReinsuranceType.value =="01")
	{
		fm.ReinsuranceTypeName.value= ''+"比例分保"+'';
	}
	else if(fm.ReinsuranceType.value =="02")
	{
	    fm.ReinsuranceTypeName.value= ''+"非比例分保"+'';
	}
 	if(fm.PreceptState.value=="01"){
 		fm.PreceptStateName.value=""+"有效"+"";
 	}else{
 		fm.PreceptStateName.value=""+"未生效"+"";
 	}
  //var strSQL="select AccumulateDefName from RIAccumulateDef where AccumulateDefNO = '"+fm.all('AccumulateDefNO').value+"'";
 	var mySql103=new SqlClass();
	  	mySql103.setResourceName("reinsure.LRSchemaInputSql"); //指定使用的properties文件名
	  	mySql103.setSqlId("LRSchemaInputSql103");//指定使用的Sql的id
	  	mySql103.addSubPara(fm.all('AccumulateDefNO').value);//指定传入的参数
  	var strSQL=mySql103.getString();
  var strArray = easyExecSql(strSQL);
	fm.all('AccumulateDefName').value = strArray;
	
	if(PreceptSearchGrid.getRowColData(tSel-1,10)=="02"){
		/**
		strSQL = " select distinct a.calfeeterm,(case a.calfeeterm when '01' then '按月计算' when '02' then '按年计算' end),a.calfeetype,(case a.calfeetype when '01' then '按保费计算' when '02' then '按保额计算' end) from RICalDef a where trim(a.arithmeticdefid)=(select trim(c.arithmeticid) from riprecept c where c.ripreceptno='"+fm.all('RIPreceptNo').value+"') "
		;
		*/
		var mySql104=new SqlClass();
	  		mySql104.setResourceName("reinsure.LRSchemaInputSql"); //指定使用的properties文件名
	  		mySql104.setSqlId("LRSchemaInputSql104");//指定使用的Sql的id
	  		mySql104.addSubPara(fm.all('RIPreceptNo').value);//指定传入的参数
  			strSQL=mySql104.getString();
		var strArray = easyExecSql(strSQL);
		if(strArray!=null&&strArray!=''){
			fm.all('CalFeeTerm').value				= strArray[0][0];
 			fm.all('CalFeeTermName').value		= strArray[0][1];
 			fm.all('CalFeeType').value				= strArray[0][2];
  		fm.all('CalFeeTypeName').value		= strArray[0][3];
  	}
	}
  var riPreceptNo=fm.all('RIPreceptNo').value;
  /**
  var strSQL1="select '第'||DivisionLineOrder||'层',(select codename from ldcode where code = StandByOne and codetype = 'hierarchynumtype'),DivisionLineValue,"
	+ " (case DivisionLineType when '01' then '自留额' when '02' then '层次线' when '03' then '临分限额' when '04' then '最低分出额' when '05' then '最大限额' end),DivisionLineType,divisionfactor "
	+ " from RIDivisionLineDef where RIPreceptNo='"+riPreceptNo+"'";
	*/
	var mySql105=new SqlClass();
	  	mySql105.setResourceName("reinsure.LRSchemaInputSql"); //指定使用的properties文件名
	  	mySql105.setSqlId("LRSchemaInputSql105");//指定使用的Sql的id
	  	mySql105.addSubPara(riPreceptNo);//指定传入的参数
  	var strSQL1=mySql105.getString();
	/**
	strSQL2="select IncomeCompanyNo,(select companyname from RIComInfo where companyno=IncomeCompanyNo),"
		+" (case IncomeCompanyType when '01' then '计算分出 ' when '02' then '不计算分出' end),IncomeCompanyType"
		+" from RIIncomeCompany  where RIPreceptNo='"+riPreceptNo+"'";
	*/
	var mySql106=new SqlClass();
	  	mySql106.setResourceName("reinsure.LRSchemaInputSql"); //指定使用的properties文件名
	  	mySql106.setSqlId("LRSchemaInputSql106");//指定使用的Sql的id
	  	mySql106.addSubPara(riPreceptNo);//指定传入的参数
  		strSQL2=mySql106.getString();	
	//strSQL3=" select a.IncomeCompanyNo,(select ComPanyName from RIComInfo b where a.IncomeCompanyNo=b.ComPanyNo),a.upperlimit,a.lowerlimit,a.areaid,a.PremFeeTableNo,(select c.FeeTableName from RIFeeRateTableDef c where a.PremFeeTableNo=c.FeeTableNo),a.comfeetableno,(select c.FeeTableName from RIFeeRateTableDef c where a.comfeetableno=c.FeeTableNo) from RIRiskDivide a where a.possessscale<>0 and exists (select * from RIIncomeCompany b where a.IncomeCompanyNo=b.IncomeCompanyNo and b.IncomeCompanyType='01') and a.ripreceptno='"+riPreceptNo+"' order by a.AreaID";
	var mySql107=new SqlClass();
	  	mySql107.setResourceName("reinsure.LRSchemaInputSql"); //指定使用的properties文件名
	  	mySql107.setSqlId("LRSchemaInputSql107");//指定使用的Sql的id
	  	mySql107.addSubPara(riPreceptNo);//指定传入的参数
  		strSQL3=mySql107.getString();	
	var strResult1=easyExecSql(strSQL1);
	var strResult2=easyExecSql(strSQL2);
	if(strResult1==null||strResult2==null){
		ContCessGrid.clearData(); 
		ScaleLineGrid.clearData();
	  addScale(riPreceptNo);
	  FeeRateGrid.clearData();
	}else{
		ContCessGrid.clearData(); 
		turnPage1.queryModal(strSQL1,ContCessGrid); 
		ScaleLineGrid.clearData(); 
		turnPage2.queryModal(strSQL2,ScaleLineGrid); 
		turnPage3.queryModal(strSQL3,FeeRateGrid); 
	}
	//显示分保比例设置
	addCessScale(riPreceptNo,"SHOW");
	//显示分保要素
	addFactor(riPreceptNo);
	
}

/**
* 关联临分保单
*/
function RelaTempPol(){
	//if(fm.PreceptState.value=='02'){
	//	alert("临分方案未生效，不能关联临分保单");
	//	return false;
	//}
	var tSel=PreceptSearchGrid.getSelNo();
	var state = PreceptSearchGrid.getRowColData(tSel-1,17);
	var PreceptType = PreceptSearchGrid.getRowColData(tSel-1,10);
	if(tSel==0){
		myAlert(""+"请选择再保方案"+"");
		return false;
	}
	if(state=='02'&& PreceptType=='02'){
	  myAlert(""+"该方案未生效"+","+"不能关联保单!"+"");
	  return false;
	}
	var tRIContNo 	= PreceptSearchGrid.getRowColData(tSel-1,1);
	var tRIPreceptNo= PreceptSearchGrid.getRowColData(tSel-1,2);
	var tCalFeeTerm = fm.CalFeeTerm.value;
	var tCalFeeType = fm.CalFeeType.value;
	var tOperateNo = fm.OperateNo.value;
	var tCodeType = "01";
  window.open("./FrameRelaTempPol.jsp?RIContNo="+tRIContNo+"&RIPreceptNo="+tRIPreceptNo+"&CalFeeType="+tCalFeeType+"&CalFeeTerm="+tCalFeeTerm+"&OperateNo="+tOperateNo+"&CodeType="+tCodeType+"&SerialNo="+fm.SerialNo.value,"Temp1"); 
}

/**
* 关联团险临分保单
*/
function RelaGrpTempPol(){
	var tSel=PreceptSearchGrid.getSelNo();
	if(tSel==0){
		myAlert(""+"请选择再保方案"+"");
		return false;
	}
	var tRIContNo 	= PreceptSearchGrid.getRowColData(tSel-1,1);
	var tRIPreceptNo= PreceptSearchGrid.getRowColData(tSel-1,2);
	var tCalFeeTerm = fm.CalFeeTerm.value;
	var tCalFeeType = fm.CalFeeType.value;
	var tOperateNo = fm.OperateNo.value;
	var tCodeType = "05";
  window.open("./FrameRelaTempPol.jsp?RIContNo="+tRIContNo+"&RIPreceptNo="+tRIPreceptNo+"&CalFeeType="+tCalFeeType+"&CalFeeTerm="+tCalFeeTerm+"&OperateNo="+tOperateNo+"&CodeType="+tCodeType,"Temp1"); 
}

function addFeeRate()
{
  var riPreceptNo=fm.all('RIPreceptNo').value;
  //var strSQL=" select a.IncomeCompanyNo,(select ComPanyName from RIComInfo b where a.IncomeCompanyNo=b.ComPanyNo),a.upperlimit,a.lowerlimit,a.areaid,a.PremFeeTableNo,(select c.FeeTableName from RIFeeRateTableDef c where a.PremFeeTableNo=c.FeeTableNo),a.comfeetableno,(select c.FeeTableName from RIFeeRateTableDef c where a.PremFeeTableNo=c.FeeTableNo) from RIRiskDivide a where a.possessscale<>0 and exists (select * from RIIncomeCompany b where a.IncomeCompanyNo=b.IncomeCompanyNo and b.IncomeCompanyType='01') and a.ripreceptno='"+riPreceptNo+"'";
	var mySql108=new SqlClass();
	  	mySql108.setResourceName("reinsure.LRSchemaInputSql"); //指定使用的properties文件名
	  	mySql108.setSqlId("LRSchemaInputSql108");//指定使用的Sql的id
	  	mySql108.addSubPara(riPreceptNo);//指定传入的参数
  	var strSQL=mySql108.getString();
	turnPage3.queryModal(strSQL,FeeRateGrid);	   
}

//录入新方案
function inputPrecept(){
	fm.all('RIPreceptNo').value 		= '';    
	fm.all('RIPreceptName').value   = '';
  fm.all('AccumulateDefNO').value = '';    //
  fm.all('AccumulateDefName').value = '';  //
  fm.all('DivisionNum').value 		= '';    //溢额层次
  fm.all('CompanyNum').value 		 	= '';    //分保公司数
  
  fm.all('PreceptType').value 		= '';    
  fm.all('PreceptTypeName').value = '';  
  fm.all('ArithmeticID').value 		= '';    
  fm.all('ArithmeticName').value 	= '';  
  
  fm.all('CalFeeTerm').value 			= '';  
  fm.all('CalFeeTermName').value 	= '';  
  fm.all('CalFeeType').value 			= '';  
  fm.all('CalFeeTypeName').value 	= ''; 
  fm.all('PreceptState').value 	= ''; 
  fm.PreceptState.value="02";
  fm.PreceptStateName.value=""+"未生效"+"";
  
  fm.RelaTempPolBut.style.display='none';
  fm.RelaGrpTempPolBut.style.display='none';
  divArithmetic.style.display='none';
  fm.ArithmeticID.style.display='none';
  fm.ArithmeticName.style.display='none';
  //divCalType.style.display='none';
  //fm.CalFeeType.style.display='none';
  //fm.CalFeeTypeName.style.display='none';
  editForm();       //显示操作按钮
  ScaleLineGrid.clearData();               //分保比例线设置 里的值清空
  ContCessGrid.clearData();                //溢额线设置 里的值清空
  CessScaleGrid.clearData();                //分保比例设置 里的值清空
  FactorGrid.clearData();                   //合同分保信息里的值清空
  FeeRateGrid.clearData();
  addCessScale("0");
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content, result, CertifyCode){	
  showInfo.close();
  if (FlagStr == "Fail" ) {             
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(content));
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
    
  } else {   	
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+encodeURI(encodeURI(content));
	  showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	  
	  if(fm.OperateType.value=="SCHMINSERT"){
	  	fm.RIPreceptNo.value=result;
	  	queryClick();
	  	addScale(result); //溢额线设置,分保比例线设置
	  }
	  if(fm.OperateType.value=="DIVCOMINSERT"){
	  	fm.RIPreceptNo.value=result;
	  	addCessScale(result,"SHOW"); //分保比例设置
	  	addFeeRate();
	  }
	  if(fm.OperateType.value=="SCALEINSERT"){
	  	fm.RIPreceptNo.value=result;
	  	addCessScale(result,"SHOW"); //分保比例设置
	  	addFeeRate();
	  }
	  if (fm.OperateType.value=="DELETE"){
	  	resetForm();
	  }
	  if (fm.OperateType.value=="UPDATE"){
	  	queryClick();
	  	if(fm.UpDe.value=="1"){
//	  		resetForm();
	  		addScale(result);
	  		addFeeRate();
	  	}
	  }
  }
}
//溢额线设置与分保比例线设置 mulLine addOne
function addScale(result){
	ContCessGrid.clearData();
	ScaleLineGrid.clearData();
	
	//var strSQL="select divisionnum,companynum from RIPrecept where RIPreceptNo='"+result+"'"
	var mySql109=new SqlClass();
	  	mySql109.setResourceName("reinsure.LRSchemaInputSql"); //指定使用的properties文件名
	  	mySql109.setSqlId("LRSchemaInputSql109");//指定使用的Sql的id
	  	mySql109.addSubPara(result);//指定传入的参数
  	var strSQL=mySql109.getString();
	var strArray=easyExecSql(strSQL);
	var divNum=parseInt(strArray[0][0]);
	var companyNum=parseInt(strArray[0][1]);     
	for(var i=0;i<divNum;i++){
		ContCessGrid.addOne();
		ContCessGrid.setRowColData(i,1,""+"第"+""+(i+1)+""+"层"+"");
	}     
	for(var i=0;i<companyNum;i++){
		
		ScaleLineGrid.addOne();
	}

	addCessScale(result);
}

/**分保比例设置 mulLine addOne
	result:RIPreceptNo
	flag: 是否用于显示
*/
function addCessScale(result,flag){
	//重置
	/**
	strSQL="select DivisionLineValue from RIDivisionLineDef where ripreceptno='"+ result + "' and divisionlinetype<>'04' order by DivisionLineOrder "
	;
	*/
	var mySql110=new SqlClass();
	  	mySql110.setResourceName("reinsure.LRSchemaInputSql"); //指定使用的properties文件名
	  	mySql110.setSqlId("LRSchemaInputSql110");//指定使用的Sql的id
	  	mySql110.addSubPara(result);//指定传入的参数
  	    strSQL=mySql110.getString();
	var strArray=easyExecSql(strSQL);
	strArray=easyExecSql(strSQL);
	if(strArray==null){
		x=0;
	}else{
		x=strArray.length;
	}
	//重置line
	line=new Array(x);
	if(strArray!=null){
		for(var i=0;i<strArray.length;i++){
			line[i]=strArray[i];
		}
	}
	/**
	strSQL=" select (select companyname from RIComInfo where companyno=a.IncomeCompanyNo) from RIIncomeCompany a where ripreceptno='"+result+"' order by a.IncomeCompanyOrder "
	;
	*/
	var mySql111=new SqlClass();
	  	mySql111.setResourceName("reinsure.LRSchemaInputSql"); //指定使用的properties文件名
	  	mySql111.setSqlId("LRSchemaInputSql111");//指定使用的Sql的id
	  	mySql111.addSubPara(result);//指定传入的参数
  	    strSQL=mySql111.getString();
	strArray=easyExecSql(strSQL);
	if(strArray==null){
		y=0;
	}else{
		y=strArray.length;
	}
	//重置com
	com=new Array(y);
	if(strArray!=null){
		for(var i=0;i<strArray.length;i++){
			com[i]=strArray[i];
		}
	}
	CessScaleGrid.clearData();
	initCessScaleGrid();
	
	for(var i=0;i<line.length;i++){ //溢额线设置
		var level=line.length-i;
		CessScaleGrid.addOne("CessScaleGrid");
		CessScaleGrid.setRowColData(i,1,""+"第"+""+level+""+"层:"+" "+line[level-1]+"");
	
	}
	fm.Line.value=x; //“行数”
	fm.Com.value=y;  //“列数”
	if(flag=="SHOW"){
		//strSQL="select possessscale from RIRiskDivide where RIPreceptNo='"+result+"' order by areaid";
		var mySql112=new SqlClass();
	  		mySql112.setResourceName("reinsure.LRSchemaInputSql"); //指定使用的properties文件名
	  		mySql112.setSqlId("LRSchemaInputSql112");//指定使用的Sql的id
	  		mySql112.addSubPara(result);//指定传入的参数
  	    	strSQL=mySql112.getString();
		var strResult=easyExecSql(strSQL);
		var k=0;
		if(strResult!=null&&strResult!="null"){
			for(var i=x-1;i>=0;i--){
				for(var j=0;j<y;j++){
					CessScaleGrid.setRowColData(i,j+2,strResult[k]+"");
					
					k++;
				} 
			}
		}
	}
}

function addFactor(result){
	//strSQL="select factorname,factorcode,factorvalue from RICalFactorValue where RIPreceptNo='"+result+"'";
  	var mySql113=new SqlClass();
	  	mySql113.setResourceName("reinsure.LRSchemaInputSql"); //指定使用的properties文件名
	  	mySql113.setSqlId("LRSchemaInputSql113");//指定使用的Sql的id
	  	mySql113.addSubPara(result);//指定传入的参数
  	    strSQL=mySql113.getString();
  strArray=easyExecSql(strSQL);
  FactorGrid.clearData();
  
	if (strArray!=null){
		for (var k=0;k<strArray.length;k++){
			FactorGrid.addOne("FactorGrid");
			FactorGrid.setRowColData(k,1,strArray[k][0]);
			FactorGrid.setRowColData(k,2,strArray[k][1]);
			FactorGrid.setRowColData(k,3,strArray[k][2]);
		}
	}
}

function updateClick()
{
	if(fm.PreceptState.value=="01"){//如果状态有效，要校验是否配置了累计方案算法
		//var tsql="select 'X' from riprecept where ripreceptno = '"+fm.RIPreceptNo.value+"' and arithmeticid is not null";
		var mySql114=new SqlClass();
	  		mySql114.setResourceName("reinsure.LRSchemaInputSql"); //指定使用的properties文件名
	  		mySql114.setSqlId("LRSchemaInputSql114");//指定使用的Sql的id
	  		mySql114.addSubPara(fm.RIPreceptNo.value);//指定传入的参数
  	    var tsql=mySql114.getString();
		var result=easyExecSql(tsql);
		if(result==null){
			myAlert(""+"没有配置再保方案算法，不能置为有效状态!"+"");
			return false;
		}
	}
	fm.UpDe.value='';
	fm.OperateType.value="UPDATE";
	
	if(fm.all('CompanyNumBackup').value!=fm.all('CompanyNum').value || fm.all('DivisionNumBackup').value!=fm.all('DivisionNum').value){
		fm.UpDe.value='1';
	}
	if(fm.UpDe.value=='1'){
		if(!confirm(""+"你已经对溢额层次数或分保公司数进行了修改"+","+"保存后需重新设置溢额线和分保比例线，确定要修改该方案吗？"+"")){
			return false;
		}
	}else{
		if(!confirm(""+"你确定要修改该方案吗？"+"")){
			return false;
		}
	}
	try {
	if(verifyInputA1() == true){
			if (veriryInput6()==true){
		  	var i = 0;
		  	var showStr=""+"正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
		  	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
				fm.action="./LRSchemaSave.jsp";
		  	fm.submit(); //提交
	  	}
	  	else
	  	{
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
	if(!confirm(""+"你确定要删除该再保方案吗？"+"")){
		return false;
	}
	try {
//		if(verifyInput() == true){
				if(verifyInputA1() == true){ 
			if (veriryInput5()==true){
		  	var i = 0;
		  	var showStr=""+"正在删除数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
		  	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
				
				fm.action="./LRSchemaSave.jsp";
		  	fm.submit(); //提交
	  	}
	  	else{
	  	}
	  }
  } catch(ex) {
  	showInfo.close( );
  	myAlert(ex);
  }
}

function afterQuery(){
}

//重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm(){
  try{
  	queryClick();
	  inputPrecept();
  }
  catch(re)
  {
  	myAlert(""+"在CertifySendOutInput.js"+"-->"+"resetForm函数中发生异常:初始化界面错误!"+"");
  }
} 

//提交前的校验、计算
function beforeSubmit(){
  //添加操作	
} 

function ClosePage(){
	top.close();
} 


function verifyInputA1(){	
	if((fm.RIPreceptNo.value=="")||(fm.RIPreceptNo.value==null))                                                                                                                                                   
    {myAlert(""+"累计方案编号"+" "+"不能为空"+"");                                    
  	 return false;                                                      
    }                                                                   
 if((fm.AccumulateDefNO.value=="")||(fm.AccumulateDefNO.value==null)) 
   {myAlert(""+"累计方案编号"+" "+"不能为空"+"");                               
 	 return false;                                                 
   }                                                                                                                                        
	if((fm.DivisionNum.value=="")||(fm.DivisionNum.value==null))                            
	 {myAlert(""+"溢额层次数"+" "+"不能为空"+"");                                                    
		return false;                                                                    
	 }                                                                                 
	if(fm.CompanyNum.value=="" ||fm.CompanyNum.value==null)
	 {myAlert(""+"分保公司数"+" "+"不能为空"+""); 
	 return false;  
	 }
	 if(fm.PreceptType.value=='02'){
	 	//if(fm.ArithmeticID.value=="" ||fm.ArithmeticID.value==null){
	 	//	alert("分保算法不能为空");
	 	//	return false;
	 	//}
	 	//if(fm.CalFeeType.value=="" ||fm.CalFeeType.value==null){
	 	//	alert("计算方式不能为空");
	 	//	return false;
	 	//}
	 }
	return true;
}

function verifyInputA2(){
	//溢额线设置 mulline 
	var rowNum=ContCessGrid. mulLineCount ; //行数 
	for(var i=0;i<rowNum;i++){  
		num=i+1;
		if(ContCessGrid.getRowColData(i,1)==''||ContCessGrid.getRowColData(i,1)==null)
		{
			myAlert(""+"溢额线设置"+","+"第"+""+num+""+"行"+","+"层级"+" "+"不能为空！"+""); 
			return false; 
		}
		
		if(ContCessGrid.getRowColData(i,2)==''||ContCessGrid.getRowColData(i,2)==null)    
		{
			myAlert(""+"溢额线设置"+","+"第"+""+num+""+"行"+","+"数值"+" "+"不能为空！"+""); 
			return false; 
		}
		
		if(ContCessGrid.getRowColData(i,3)==''||ContCessGrid.getRowColData(i,3)==null)      
		{                                                                                  
			myAlert(""+"溢额线设置"+","+"第"+""+num+""+"行"+","+"溢额线类型"+" "+"不能为空！"+"");                           
			return false;                                                                    
		}		                                                                         
	} 
	//检查有没有相同的 数值
	for(var n=0;n<ContCessGrid.mulLineCount;n++)                            
	{                                                                     
	   for(var m=n+1;m<ContCessGrid.mulLineCount;m++)                       
	   {                                                                  
	     if(ContCessGrid.getRowColData(n,3)==ContCessGrid.getRowColData(m,3)) 
	     {                                                                
	         myAlert(""+"溢额线设置"+" "+"第"+""+(n+1)+""+"层的数值不能等于第"+""+(m+1)+""+"层的数值!"+"");	                                
	         return false;                                                
	     } 
	     if(parseFloat(ContCessGrid.getRowColData(n,3))>parseFloat(ContCessGrid.getRowColData(m,3)))
	     {                                                                   
	         myAlert(""+"溢额线设置"+" "+"第"+""+(n+1)+""+"层的数值不能大于第"+""+(m+1)+""+"层的数值"+" !");	      
	         return false;                                                   
	     }                                                                 	              	                                                                    
	   }                                                                  
	}                                                                                                                                                   
  //分保公司设置                                                                       
  var rowNum=ScaleLineGrid. mulLineCount ; //行数 	                                   
	for(var i=0;i<rowNum;i++){                                                           
		num=i+1;		                                                                       
		if(ScaleLineGrid.getRowColData(i,1)==0||ScaleLineGrid.getRowColData(i,1)==null)    
		{                                                                                  
			myAlert(""+"分保公司设置"+","+"第"+""+num+""+"行"+","+"公司代码"+" "+"不能为空！"+"");                           
			return false;                                                                    
		}		                                                                               
		if(ScaleLineGrid.getRowColData(i,2)==0||ScaleLineGrid.getRowColData(i,2)==null)    
		{                                                                                  
			myAlert(""+"分保公司设置"+","+"第"+""+num+""+"行"+","+"公司名称"+" "+"不能为空！"+"");                           
			return false;                                                                    
		}		                                                                               
		if(ScaleLineGrid.getRowColData(i,3)==0||ScaleLineGrid.getRowColData(i,3)==null)    
		{                                                                                  
			myAlert(""+"分保公司设置"+","+"第"+""+num+""+"行"+","+"分保公司类型"+" "+"不能为空！"+"");                       
			return false;                                                                    
		}					                                                                         
	} 
	//不能有相同的再保公司
	for(var n=0;n<ScaleLineGrid.mulLineCount;n++)                              
	{                                                                         
	   for(var m=n+1;m<ScaleLineGrid.mulLineCount;m++)                         
	   {                                                                      
	     if(ScaleLineGrid.getRowColData(n,1)==ScaleLineGrid.getRowColData(m,1)) 
	     {                                                                    
	         myAlert(""+"分保公司设置"+" "+"不同"+" "+"层"+" "+"不能录入相同的"+" "+"再保公司"+" !");	                      
	         return false;                                                    
	     }                                                                    
	   }                                                                      
	}                                                                       		                                                                                   
 return true;                                                                          
}                                                                                      
	                                                                                     
	                                                                                   
function verifyInputA3(){ 
	//方案级分保信息
	var rowNum=FactorGrid. mulLineCount ;
	for(var i=0;i<rowNum;i++){
		num=i+1;
		if(FactorGrid.getRowColData(i,1)==''||FactorGrid.getRowColData(i,1)==null)          
		{
			myAlert(""+"方案级分保信息"+","+"第"+""+num+""+"行"+","+"要素名称"+" "+"不能为空！"+"");                         
			return false;                                                                    
		}
		if(FactorGrid.getRowColData(i,2)==''||FactorGrid.getRowColData(i,2)==null)          
		{
			myAlert(""+"方案级分保信息"+","+"第"+""+num+""+"行"+","+"要素代码"+" "+"不能为空！"+"");                         
			return false;                                                                    
		}
		if(FactorGrid.getRowColData(i,3)==''||FactorGrid.getRowColData(i,3)==null)          
		{
			myAlert(""+"方案级分保信息"+","+"第"+""+num+""+"行"+","+"要素值"+" "+"不能为空！"+"");                           
			return false;
		}					                                                                         
	}                                                                                    
 return true;                                                                          
}                                                                                      
                                                                                   
function verifyInputA4(){                                                              
   var rowNum=CessScaleGrid. mulLineCount ;                                            
   for(var i=0;i<rowNum;i++){                                                          
		num=i+1;		                                                                       
   if(CessScaleGrid.getRowColData(i,1)==null)           
		{                                                                                  
			myAlert(""+"分保比例设置"+","+"第"+""+num+""+"行"+","+"层级"+" "+"不能为空！"+"");                         
			return false;                                                                    
		}		                                                                               
   }                                                                                   
  for(var i=0;i<rowNum;i++){                                                           
  	num=i+1;	                                                                         
   for (var j=1;j<=com.length;j++){                                                    
   	if(CessScaleGrid.getRowColData(i,j+1)==null)      
		{                                                                                  
			myAlert(","+"第"+""+num+""+"行"+","+"第"+""+(j+1)+""+"列"+" "+"不能为空！"+"");                                  
			return false;                                                                    
		}		                                                                               
   }                                                                                   
 }                                                                                     
}

function checkPreceptState(){
	var tRIContNo=fm.RIContNo.value;
	var tPreceptState=fm.PreceptState.value;
	if(tPreceptState=="01"){
		//var tSql="select count(*) from RIBarGainInfo where ricontno='"+tRIContNo+"' and state='0'";
		var mySql115=new SqlClass();
	  		mySql115.setResourceName("reinsure.LRSchemaInputSql"); //指定使用的properties文件名
	  		mySql115.setSqlId("LRSchemaInputSql115");//指定使用的Sql的id
	  		mySql115.addSubPara(tRIContNo);//指定传入的参数
  	    var tSql=mySql115.getString();
		var  result = easyExecSql(tSql, 1, 0, 1);
		
		if(result[0][0]!="0"){
			myAlert(""+"再保合同未生效，方案不能选择生效状态"+"");
			return false;
		}
	}
	return true;
	
}
function accuRiskInfo(){
	var tSel = PreceptSearchGrid.getSelNo();	
	if( tSel == 0 || tSel == null )
	{
		myAlert( ""+"请先选择再保方案！"+"" );
		return false;
	}
  var accumulateNo = PreceptSearchGrid.getRowColData(tSel-1,4);
	window.open("./FrameAccRiskQuery.jsp?AccumulateNo="+accumulateNo); 
}

function afterCodeSelect(codeName,Field)
{ 
	if(codeName=="precepttype")
	{
		if(Field.value=="02")
		{
  		fm.RelaTempPolBut.style.display='';
  		fm.RelaGrpTempPolBut.style.display='none';
  		divArithmetic.style.display='';
  		fm.ArithmeticID.style.display='';
  		fm.ArithmeticName.style.display='';
  		
  	}else{
  		fm.RelaTempPolBut.style.display='none'; 
  		fm.RelaGrpTempPolBut.style.display='none';
  		divArithmetic.style.display='none'; 
  		fm.ArithmeticID.style.display='none'; 
  		fm.ArithmeticName.style.display='none'; 
  		
		}
	}else if(codeName=="lrarithmetic"){
		//var tSQL = " select a.CalFeeTerm,(case a.CalFeeTerm when '01' then '按保费计算' when '02' then '按保额计算' end),a.CalFeeType,(case a.CalFeeType when '01' then '按月计算' when '02' then '按年计算' end) from RICalDef a where a.ArithmeticDefID='"+fm.ArithmeticID.value+"' " ;
		var mySql116=new SqlClass();
	  		mySql116.setResourceName("reinsure.LRSchemaInputSql"); //指定使用的properties文件名
	  		mySql116.setSqlId("LRSchemaInputSql116");//指定使用的Sql的id
	  		mySql116.addSubPara(fm.ArithmeticID.value);//指定传入的参数
  	    var tSQL=mySql116.getString();
		var arrResult = easyExecSql(tSQL);
	  	fm.CalFeeTerm.value=arrResult[0][0];
	  	fm.CalFeeTermName.value=arrResult[0][1];
	  	fm.CalFeeType.value=arrResult[0][2];
	  	fm.CalFeeTypeName.value=arrResult[0][3];
	}else if(codeName=="lrcontno"){
		fm.all('RIContNo').value=fm.all('RIContNo1').value;
		queryClick();
	}else if(codeName== "IncomeCompanyType")
	{
		if(Field.value == ""+"最大限额"+"")
		{
			ContCessGrid.setRowColData(ContCessGrid.mulLineCount-1,3,"999999999");
		}
		ContCessGrid.setRowColData(ContCessGrid.mulLineCount-1,2,fm.HierarchyNumTypeName.value);
	}
	if(codeName== "reinsuresubtype")
	{
		if(Field.value == '06')
		{
			fm.HierarchyNumType.value = "03";
			fm.HierarchyNumTypeName.value = ""+"赔付率"+"";
		}else if(Field.value == '04')
		{
			fm.HierarchyNumType.value = "02";
			fm.HierarchyNumTypeName.value = ""+"赔付额"+"";		
		}
		else if(Field.value == '05')
		{
			fm.HierarchyNumType.value = "02";
			fm.HierarchyNumTypeName.value = ""+"赔付额"+"";		
		}else
		{
			fm.HierarchyNumType.value = "01";
			fm.HierarchyNumTypeName.value = ""+"风险保额"+"";			
		}	
	}
}

function nextStep()
{
	var tSel = FeeRateGrid.getSelNo();
	//var tSql="select 'X' from RIRiskDivide where ripreceptno='"+fm.RIPreceptNo.value+"' and areaid='"+FeeRateGrid.getRowColData(tSel-1,5)+"' and (premfeetableno is not null or comfeetableno is not null)";
	var mySql117=new SqlClass();
	  	mySql117.setResourceName("reinsure.LRSchemaInputSql"); //指定使用的properties文件名
	  	mySql117.setSqlId("LRSchemaInputSql117");//指定使用的Sql的id
	  	mySql117.addSubPara(fm.RIPreceptNo.value);//指定传入的参数
	  	mySql117.addSubPara(FeeRateGrid.getRowColData(tSel-1,5));//指定传入的参数
   var tSql=mySql117.getString();
	var result=easyExecSql(tSql);
	//alert(result);
	var varSrc="&AccumulateDefNO='"+ fm.AccumulateDefNO.value+"'&RIPreceptNo='"+fm.RIPreceptNo.value+"'&IncomeCompanyNo='"+FeeRateGrid.getRowColData(tSel-1,1)+"'&AreaId='"+FeeRateGrid.getRowColData(tSel-1,5)+"'&UpperLimit='"+FeeRateGrid.getRowColData(tSel-1,3)+"'&LowerLimit='"+FeeRateGrid.getRowColData(tSel-1,4)+"'";
	if(result==null){
		window.open("./FrameMainLRFeeRateBatch.jsp?Interface=LRFeeRateBatchRiskInput.jsp"+varSrc,"true1"); //+varSrc,
	} 
}
//浏览
function browseForm(){
	fm.edit.style.display='';
	fm.browse.style.display='none';
	divEdit1.style.display='none';
	divEdit2.style.display='none';
	divEdit3.style.display='none';
	divEdit4.style.display='none';
	divEdit5.style.display='none';
}
//编辑
function editForm(){
	fm.edit.style.display='none';
	fm.browse.style.display='';
	divEdit1.style.display='';
	divEdit2.style.display='';
	divEdit3.style.display='';
	divEdit4.style.display='';
	divEdit5.style.display='';
}
