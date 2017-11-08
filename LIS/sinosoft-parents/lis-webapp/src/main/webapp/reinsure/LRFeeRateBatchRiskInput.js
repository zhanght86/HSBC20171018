var showInfo; 
var turnPage = new turnPageClass(); 
window.onfocus=myonfocus; 
var ImportPath; 

function myonfocus()
{
	if(showInfo!=null)
	{
	  try
	  {
	    showInfo.focus();  
	  }
	  catch(ex)
	  {
	    showInfo=null;
	  }
	}
}

function queryFeeRateBatch()
{
	var mySql100=new SqlClass();
		mySql100.setResourceName("reinsure.LRFeeRateBatchRiskInputSql"); //指定使用的properties文件名
		mySql100.setSqlId("LRFeeRateBatchRiskInputSql100");//指定使用的Sql的id
		mySql100.addSubPara(fm.RIPreceptNo.value);//指定传入的参数
		mySql100.addSubPara(fm.AreaId.value);//指定传入的参数
	var tSQL=mySql100.getString();
	
	//var tSQL = " select count(*) from RIAssociateFeeTable a where a.ripreceptno='"+fm.RIPreceptNo.value+"' and a.areaid='"+fm.AreaId.value+"' ";
	var result = easyExecSql(tSQL); 
	//alert(result);
	if(result=='0'){
		var mySql101=new SqlClass();
			mySql101.setResourceName("reinsure.LRFeeRateBatchRiskInputSql"); //指定使用的properties文件名
			mySql101.setSqlId("LRFeeRateBatchRiskInputSql101");//指定使用的Sql的id
			mySql101.addSubPara(fm.RIPreceptNo.value);//指定传入的参数
			mySql101.addSubPara(fm.AreaId.value);//指定传入的参数
			mySql101.addSubPara(fm.IncomeCompanyNo.value);//指定传入的参数
			mySql101.addSubPara(fm.IncomeCompanyNo.value);//指定传入的参数	
			mySql101.addSubPara(fm.UpperLimit.value);//指定传入的参数	
			mySql101.addSubPara(fm.LowerLimit.value);//指定传入的参数	
			mySql101.addSubPara(fm.AccumulateDefNO.value);//指定传入的参数	
			mySql101.addSubPara(fm.RIPreceptNo.value);//指定传入的参数	
			mySql101.addSubPara(fm.AreaId.value);//指定传入的参数
		var tSQL=mySql101.getString();
		/**
		tSQL = " select count(*) from (select a.AccumulateDefNO, b.DeTailFlag,decode(b.DeTailFlag,'01','险种级别','02','险种级别'), '"+fm.RIPreceptNo.value+"', a.associatedcode, '"+fm.AreaId.value+"', '"+fm.IncomeCompanyNo.value+"',"
		+ " (select ComPanyName from RIComInfo r where r.ComPanyNo='"+fm.IncomeCompanyNo.value+"'), '"+fm.UpperLimit.value+"', '"+fm.LowerLimit.value+"','','','','' "
		+ " from RIAccumulateRDCode a,RIAccumulateDef b where a.accumulatedefno = b.accumulatedefno and a.accumulatedefno='" + fm.AccumulateDefNO.value + "' "
		+ " and a.AssociatedCode not in (select AssociateCode from RIAssociateFeeTable ass where ass.RIPreceptNo='"+fm.RIPreceptNo.value+"' and ass.AreaID='"+fm.AreaId.value+"') ) x "
		;
		*/
		var result = easyExecSql(tSQL); 
		var mySql102=new SqlClass();
			mySql102.setResourceName("reinsure.LRFeeRateBatchRiskInputSql"); //指定使用的properties文件名
			mySql102.setSqlId("LRFeeRateBatchRiskInputSql102");//指定使用的Sql的id
			mySql102.addSubPara(fm.RIPreceptNo.value);//指定传入的参数
			mySql102.addSubPara(fm.AreaId.value);//指定传入的参数
			mySql102.addSubPara(fm.IncomeCompanyNo.value);//指定传入的参数
			mySql102.addSubPara(fm.IncomeCompanyNo.value);//指定传入的参数	
			mySql102.addSubPara(fm.UpperLimit.value);//指定传入的参数	
			mySql102.addSubPara(fm.LowerLimit.value);//指定传入的参数	
			mySql102.addSubPara(fm.AccumulateDefNO.value);//指定传入的参数	
			mySql102.addSubPara(fm.RIPreceptNo.value);//指定传入的参数	
			mySql102.addSubPara(fm.AreaId.value);//指定传入的参数
		    tSQL=mySql102.getString();
		
		/**
		tSQL = " select a.AccumulateDefNO, b.DeTailFlag,decode(b.DeTailFlag,'01','险种级别','02','险种级别'), '"+fm.RIPreceptNo.value+"', a.associatedcode, '"+fm.AreaId.value+"', '"+fm.IncomeCompanyNo.value+"',"
		+ " (select ComPanyName from RIComInfo r where r.ComPanyNo='"+fm.IncomeCompanyNo.value+"'), '"+fm.UpperLimit.value+"', '"+fm.LowerLimit.value+"','','','','' "
		+ " from RIAccumulateRDCode a,RIAccumulateDef b where a.accumulatedefno = b.accumulatedefno and a.accumulatedefno='" + fm.AccumulateDefNO.value + "' "
		+ " and a.AssociatedCode not in (select AssociateCode from RIAssociateFeeTable ass where ass.RIPreceptNo='"+fm.RIPreceptNo.value+"' and ass.AreaID='"+fm.AreaId.value+"') "
		;
		*/
		
		turnPage.queryModal(tSQL, FeeRateBatchGrid,"","",result); 
	}else{
	var param1="a.ripreceptno="+fm.RIPreceptNo.value+" and a.areaid="+fm.AreaId.value; 
	var param2=fm.RIPreceptNo.value+"', a.associatedcode,"+fm.AreaId.value+"', '"+fm.IncomeCompanyNo.value;
	var mySql103=new SqlClass();
			mySql103.setResourceName("reinsure.LRFeeRateBatchRiskInputSql"); //指定使用的properties文件名
			mySql103.setSqlId("LRFeeRateBatchRiskInputSql103");//指定使用的Sql的id
			mySql103.addSubPara(fm.AreaId.value);//指定传入的参数
			mySql103.addSubPara(param1);//指定传入的参数
			mySql103.addSubPara(param2);//指定传入的参数	
			mySql103.addSubPara(fm.IncomeCompanyNo.value);//指定传入的参数	
			mySql103.addSubPara(fm.UpperLimit.value);//指定传入的参数	
			mySql103.addSubPara(fm.LowerLimit.value);//指定传入的参数
			mySql103.addSubPara(fm.AccumulateDefNO.value);//指定传入的参数
			mySql103.addSubPara(fm.RIPreceptNo.value);//指定传入的参数
			mySql103.addSubPara(fm.AreaId.value);//指定传入的参数
		    tSQL=mySql103.getString();
	
	/**
		tSQL = " select count(*) from (select a.AccumulateDefNO,a.DeTailFlag,decode(a.DeTailFlag,'01','险种级别','02','险种级别'),a.RIPreceptNo,a.AssociateCode,'"+fm.AreaId.value+"',a.IncomeCompanyNo, "
		+ " (select ComPanyName from RIComInfo r where r.ComPanyNo=a.IncomeCompanyNo), a.UpperLimit||'', a.LowerLimit||'', " 
		+ " a.PremFeeTableNo, (select FeeTableName from RIFeeRateTableDef f where f.feetableno=a.PremFeeTableNo) ,a.ComFeeTableNo ,(select FeeTableName from RIFeeRateTableDef f where f.feetableno=a.ComFeeTableNo) "
		+ " from RIAssociateFeeTable a where a.ripreceptno='"+fm.RIPreceptNo.value+"' and a.areaid='"+fm.AreaId.value+"' " 
		+ " union all " 
		+ " select a.AccumulateDefNO,b.DeTailFlag,decode(b.DeTailFlag,'01','险种级别','02','险种级别'), '"+fm.RIPreceptNo.value+"', a.associatedcode, '"+fm.AreaId.value+"', '"+fm.IncomeCompanyNo.value+ "'," 
		+ " (select ComPanyName from RIComInfo r where r.ComPanyNo='"+fm.IncomeCompanyNo.value+"'), '"+fm.UpperLimit.value+"', '"+fm.LowerLimit.value+"','','','','' "
		+ " from RIAccumulateRDCode a,RIAccumulateDef b where a.accumulatedefno = b.accumulatedefno and a.accumulatedefno='" + fm.AccumulateDefNO.value + "' "
		+ " and a.AssociatedCode not in (select AssociateCode from RIAssociateFeeTable ass where ass.RIPreceptNo='"+fm.RIPreceptNo.value+"' and ass.AreaID='"+fm.AreaId.value+"') ) x "
		;
		*/
		
		var result = easyExecSql(tSQL); 
		var param3="a.ripreceptno="+fm.RIPreceptNo.value+" and a.areaid="+fm.AreaId.value;
		var param4=fm.RIPreceptNo.value+", a.associatedcode, "+fm.AreaId.value+","+fm.IncomeCompanyNo.value;
		var mySql104=new SqlClass();
			mySql104.setResourceName("reinsure.LRFeeRateBatchRiskInputSql"); //指定使用的properties文件名
			mySql104.setSqlId("LRFeeRateBatchRiskInputSql104");//指定使用的Sql的id
			mySql104.addSubPara(fm.AreaId.value);//指定传入的参数
			mySql104.addSubPara(param3);//指定传入的参数
			mySql104.addSubPara(param4);//指定传入的参数		
			mySql104.addSubPara(fm.IncomeCompanyNo.value);//指定传入的参数		
			mySql104.addSubPara(fm.UpperLimit.value);//指定传入的参数	
			mySql104.addSubPara(fm.LowerLimit.value);//指定传入的参数	
			mySql104.addSubPara(fm.AccumulateDefNO.value);//指定传入的参数	
			mySql104.addSubPara(fm.RIPreceptNo.value);//指定传入的参数
			mySql104.addSubPara(fm.AreaId.value);//指定传入的参数
		    tSQL=mySql104.getString();
		/**
		tSQL = "select a.AccumulateDefNO,a.DeTailFlag,decode(a.DeTailFlag,'01','险种级别','02','险种级别'),a.RIPreceptNo,a.AssociateCode,'"+fm.AreaId.value+"',a.IncomeCompanyNo, "
		+ " (select ComPanyName from RIComInfo r where r.ComPanyNo=a.IncomeCompanyNo), a.UpperLimit||'', a.LowerLimit||'', "
		+ " a.PremFeeTableNo, (select FeeTableName from RIFeeRateTableDef f where f.feetableno=a.PremFeeTableNo) ,a.ComFeeTableNo ,(select FeeTableName from RIFeeRateTableDef f where f.feetableno=a.ComFeeTableNo) "
		+ " from RIAssociateFeeTable a where a.ripreceptno='"+fm.RIPreceptNo.value+"' and a.areaid='"+fm.AreaId.value+"' "
		+ " union all "
		+ " select a.AccumulateDefNO,b.DeTailFlag,decode(b.DeTailFlag,'01','险种级别','02','险种级别'), '"+fm.RIPreceptNo.value+"', a.associatedcode, '"+fm.AreaId.value+"', '"+fm.IncomeCompanyNo.value+"',"
		+ " (select ComPanyName from RIComInfo r where r.ComPanyNo='"+fm.IncomeCompanyNo.value+"'), '"+fm.UpperLimit.value+"', '"+fm.LowerLimit.value+"','','','','' "
		+ " from RIAccumulateRDCode a,RIAccumulateDef b where a.accumulatedefno = b.accumulatedefno and a.accumulatedefno='" + fm.AccumulateDefNO.value + "' "
		+ " and a.AssociatedCode not in (select AssociateCode from RIAssociateFeeTable ass where ass.RIPreceptNo='"+fm.RIPreceptNo.value+"' and ass.AreaID='"+fm.AreaId.value+"') "
		;
		*/
		turnPage.queryModal(tSQL, FeeRateBatchGrid,"","",result); 
	}
}


//提交，保存按钮对应操作
function submitForm()
{
	var preceptno=FeeRateBatchGrid.getRowColData(1,4);
	var areaid=FeeRateBatchGrid.getRowColData(1,6);
	var mySql105=new SqlClass();
		mySql105.setResourceName("reinsure.LRFeeRateBatchRiskInputSql"); //指定使用的properties文件名
		mySql105.setSqlId("LRFeeRateBatchRiskInputSql105");//指定使用的Sql的id
		mySql105.addSubPara(preceptno);//指定传入的参数
		mySql105.addSubPara(areaid);//指定传入的参数
	var tSql=mySql105.getString();
	//var tSql="select 'X' from RIRiskDivide where ripreceptno='"+preceptno+"' and areaid='"+areaid+"' and (premfeetableno is not null or comfeetableno is not null)";
	var result=easyExecSql(tSql);
	//alert(result);
	if(result!=null){
		myAlert(""+"已经定义了方案级的费率佣金率，不能定义险种级的费率佣金率"+"");
		return false;
	}
	fm.OperateType.value="INSERT";
	
	var rowNum=FeeRateBatchGrid.mulLineCount ;
	                                            
  for(var i=0;i<rowNum;i++)
  {                                                        
		num=i+1;	
		//alert(FeeRateBatchGrid.getRowColData(i,11)+"||"+FeeRateBatchGrid.getRowColData(i,13));	                                                                       
    if(FeeRateBatchGrid.getRowColData(i,11)=="")           
		{                                                                                  
			myAlert(""+"第"+""+num+""+"行分保费率表不能为空！"+"");                         
			return false;                                                                    
		}		                                                                               
   }

	try 
	{
		var i = 0;
		var showStr=""+"正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
		showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
				
	  fm.action="./LRFeeRateBatchRiskSave.jsp?";
		fm.submit(); //提交
	  	
	  
  } catch(ex) 
  {
  	showInfo.close( );
  	myAlert(ex);
  }
}

function updateClick(){
	fm.OperateType.value="UPDATE";
	if(fm.State.value=='01')
	{
      var strSQL = "";
      var mySql106=new SqlClass();
		mySql106.setResourceName("reinsure.LRFeeRateBatchRiskInputSql"); //指定使用的properties文件名
		mySql106.setSqlId("LRFeeRateBatchRiskInputSql106");//指定使用的Sql的id
		mySql106.addSubPara(fm.FeeTableNo.value);//指定传入的参数
	    strSQL=mySql106.getString();
      //strSQL = "select state from RIFeeRateTableDef where feetableno='"+fm.FeeTableNo.value+"'";
      turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);
      turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
      var tState=turnPage.arrDataCacheSet[0][0];
      if(tState=='02'||tState==''||tState==null)
      {
      	myAlert(""+"费率表状态为无效，批次状态需置为无效！"+"");
      	return false;
      }
  }    
	try 
	{
		if( verifyInput() == true) 
		{
			if (veriryInput3()==true)
			{
		  	var i = 0;
		  	var showStr=""+"正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
		  	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
				fm.action="./LRFeeRateBatchSave.jsp";
		  	fm.submit(); //提交
	  	}
	  }
  }catch(ex) {
  	showInfo.close( );
  	myAlert(ex);
  }
}

function deleteClick(){
	fm.OperateType.value="DELETE";
	if(!confirm(""+"你确定要删除该费率表批次吗？"+"")){
		return false;
	}
	try {
		if(verifyInput() == true){
		  	var i = 0;
		  	var showStr=""+"正在删除数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
		  	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
				
				fm.action="./LRFeeRateBatchRiskSave.jsp";
		  	fm.submit(); //提交
	  }
  } catch(ex) {
  	showInfo.close( );
  	myAlert(ex);
  }
}

function veriryInput3()
{
	return true;
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content)
{
  showInfo.close();
  if (FlagStr == "Fail" ) {             
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(content));
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
    
  } else { 
	  //content="保存成功！";
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+encodeURI(encodeURI(content));
	  showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	  if (fm.OperateType.value=="DELETE")
	  {
	  	resetForm();
	  }else{
	  	queryFeeRateBatch();
	  }
  }
}

function ShowBatch()
{
	var tSel = FeeRateBatchGrid.getSelNo();
	fm.BatchNo.value= FeeRateBatchGrid.getRowColData(tSel-1,3);
	fm.InureDate.value= FeeRateBatchGrid.getRowColData(tSel-1,4);
	fm.LapseDate.value= FeeRateBatchGrid.getRowColData(tSel-1,5);
	fm.SaveDataName.value= FeeRateBatchGrid.getRowColData(tSel-1,6);
	fm.State.value= FeeRateBatchGrid.getRowColData(tSel-1,8);
	fm.SaveDataNameName.value=""+"通用费率表"+"";
	
}

function inputFeeRateBatch(){
	fm.all('BatchNo').value 	= '';    
	fm.all('InureDate').value  = '';
	fm.all('LapseDate').value 	= '';    
	fm.all('SaveDataName').value  = '';
	fm.all('SaveDataNameName').value  = '';
	fm.all('State').value  = '';
	fm.all('stateName').value  = '';
}
function FeeRateTableImp(){
	if(fmImport.FileName.value==""||fmImport.FileName.value==null)
	{
		myAlert(""+"录入导入文件路径！"+"");
		return false;
	}
	var i = 0;
  getImportPath();
  
  ImportFile = fmImport.all('FileName').value;  
  
  var showStr=""+"正在上载数据……"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  fmImport.action = "./LRFeeRateImportSave.jsp?ImportPath="+ImportPath+"&FeeTableNo="+fm.FeeTableNo.value+"&BatchNo="+fm.BatchNo.value;
  fmImport.submit(); //提交
}

function getImportPath(){
  var strSQL = "";
 
 var mySql107=new SqlClass();
		mySql107.setResourceName("reinsure.LRFeeRateBatchRiskInputSql"); //指定使用的properties文件名
		mySql107.setSqlId("LRFeeRateBatchRiskInputSql107");//指定使用的Sql的id
	    mySql107.addSubPara("1");
	    strSQL=mySql107.getString();
 // strSQL = "select sysvarvalue from ldsysvar where sysvar='RIXmlPath'";
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    myAlert(""+"未找到上传路径"+"");
    return;
  }
  //清空数据容器，两个不同查询共用一个turnPage对象时必须使用，最好加上，容错
  turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);

  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

  ImportPath = turnPage.arrDataCacheSet[0][0];
}


//重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm()
{
  try
  {
	  initForm();
  }
  catch(re)
  {
  	myAlert(""+"在CertifySendOutInput.js"+"-->"+"resetForm函数中发生异常:初始化界面错误!"+"");
  }
} 

function ClosePage()
{
	top.close();
} 
       
function alink(){
	window.location.href="./LRFeeRateImport.xls";
}

