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

//提交，保存按钮对应操作
function submitForm()
{
	fm.OperateType.value="INSERT";
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
				
				fm.action="./LRFeeRateSave.jsp?OPER=";
		  	fm.submit(); //提交
	  	}
	  }
  } catch(ex) 
  {
  	showInfo.close( );
  	myAlert(ex);
  }
}

//insert
function veriryInput3()
{
	var rowNum=TableClumnDefGrid.mulLineCount ; //行数 
	if(rowNum==0){
		myAlert(""+"请录入费率表字段定义"+"");
		return false;
	}
	for(var i=0;i<rowNum;i++)
	{
		num=i+1;
		if(TableClumnDefGrid.getRowColData(i,1)==null||TableClumnDefGrid.getRowColData(i,1)==""){
			myAlert(""+"第"+""+num+""+"行费率表字段不能为空"+"");
			return false;
		}
		if(TableClumnDefGrid.getRowColData(i,2)==null||TableClumnDefGrid.getRowColData(i,2)==""){
			myAlert(""+"第"+""+num+""+"行接口表字段名不能为空"+"");
			return false;
		}
		if(TableClumnDefGrid.getRowColData(i,4)==null||TableClumnDefGrid.getRowColData(i,4)==""){
			myAlert(""+"第"+""+num+""+"行费率表字段类型名不能为空"+"");
			return false;
		}
		if(TableClumnDefGrid.getRowColData(i,4).substring(1,3)=="01"){
			if(TableClumnDefGrid.getRowColData(i,5)==null||TableClumnDefGrid.getRowColData(i,5)==""){
				myAlert(""+"第"+""+num+""+"行固定值映射字段不能为空"+"");
				return false;
			}
			if(TableClumnDefGrid.getRowColData(i,7)!=null&&TableClumnDefGrid.getRowColData(i,7)!=""){
				myAlert(""+"第"+""+num+""+"行区间上限字段映射不能有值"+"");
				return false;
			}
			if(TableClumnDefGrid.getRowColData(i,9)!=null&&TableClumnDefGrid.getRowColData(i,9)!=""){
				myAlert(""+"第"+""+num+""+"行区间下限字段映射不能有值"+"");
				return false;
			}
		}
		
		if(TableClumnDefGrid.getRowColData(i,4).substring(1,3)=="02"){
			if(TableClumnDefGrid.getRowColData(i,7)==null||TableClumnDefGrid.getRowColData(i,7)==""){
				myAlert(""+"第"+""+num+""+"行区间上限字段映射不能为空"+"");
				return false;
			}
			if(TableClumnDefGrid.getRowColData(i,9)==null||TableClumnDefGrid.getRowColData(i,9)==""){
				myAlert(""+"第"+""+num+""+"行区间下限字段映射不能为空"+"");
				return false;
			}
			if(TableClumnDefGrid.getRowColData(i,5)!=null&&TableClumnDefGrid.getRowColData(i,5)!=""){
				myAlert(""+"第"+""+num+""+"行固定值映射字段不能有值"+"");
				return false;
			}
			if(TableClumnDefGrid.getRowColData(i,7)==TableClumnDefGrid.getRowColData(i,9)) 
			{
			    myAlert(""+"第"+""+num+""+"行区间上限和区间下限映射字段相同"+"");	
			    return false; 
			}
			if(TableClumnDefGrid.getRowColData(i,4).substring(0,1)=="A"){
			//字符型区间区间上下限定义不能为数值型
				if(TableClumnDefGrid.getRowColData(i,7).substring(0,1)=="N"||TableClumnDefGrid.getRowColData(i,9).substring(0,1)=="N"){
					myAlert(""+"字符型区间上下限定义不能为数值型字段"+"");
					return false;
				}
			}
			if(TableClumnDefGrid.getRowColData(i,4).substring(0,1)=="N"){
			//数值型区间区间上下限定义不能为字符型
				if(TableClumnDefGrid.getRowColData(i,7).substring(0,1)=="S"||TableClumnDefGrid.getRowColData(i,9).substring(0,1)=="S"){
					myAlert(""+"数值型区间上下限定义不能为字符型字段"+"");
					return false;
				}
			}
		}
		
			//校验不能录入重复合同要素
		for(var n=0;n<TableClumnDefGrid.mulLineCount;n++) 
		{ 
		   for(var m=n+1;m<TableClumnDefGrid.mulLineCount;m++) 
		   { 
		   		var n1=n+1;
		   		var m1=m+1;
		     	if(TableClumnDefGrid.getRowColData(n,1)==TableClumnDefGrid.getRowColData(m,1)) 
		     	{
		     	    myAlert(""+"不能录入重复费率表字段!"+"");	
		     	    return false; 
		     	}
		     	if(TableClumnDefGrid.getRowColData(n,2)==TableClumnDefGrid.getRowColData(m,2)) 
		     	{
		     	    myAlert(""+"不能录入重复接口表字段名!"+"");	
		     	    return false; 
		     	}
		     	if(TableClumnDefGrid.getRowColData(n,4).substring(1,3)=="01"&&TableClumnDefGrid.getRowColData(m,4).substring(1,3)=="01"){
		     		if(TableClumnDefGrid.getRowColData(n,5)==TableClumnDefGrid.getRowColData(m,5)) 
		     		{
		     		    myAlert(""+"第"+""+n1+""+"行和第"+""+m1+""+"行有重复映射字段!"+"");	
		     		    return false; 
		     		}
		    	}
		    	if(TableClumnDefGrid.getRowColData(n,4).substring(1,3)=="01"&&TableClumnDefGrid.getRowColData(m,4).substring(1,3)=="02"){
		     		if(TableClumnDefGrid.getRowColData(n,5)==TableClumnDefGrid.getRowColData(m,7)||TableClumnDefGrid.getRowColData(n,5)==TableClumnDefGrid.getRowColData(m,7)||TableClumnDefGrid.getRowColData(n,5)==TableClumnDefGrid.getRowColData(m,9)) 
		     		{
		     		    myAlert(""+"第"+""+n1+""+"行和第"+""+m1+""+"行有重复映射字段!"+"");	
		     		    return false; 
		     		}
		    	}
		    	if(TableClumnDefGrid.getRowColData(n,4).substring(1,3)=="02"&&TableClumnDefGrid.getRowColData(m,4).substring(1,3)=="01"){
		     		if(TableClumnDefGrid.getRowColData(n,7)==TableClumnDefGrid.getRowColData(m,5)||TableClumnDefGrid.getRowColData(n,9)==TableClumnDefGrid.getRowColData(m,5)) 
		     		{
		     		    myAlert(""+"第"+""+n1+""+"行和第"+""+m1+""+"行有重复映射字段!"+"");	
		     		    return false; 
		     		}
		    	}
		    	if(TableClumnDefGrid.getRowColData(n,4).substring(1,3)=="02"&&TableClumnDefGrid.getRowColData(m,4).substring(1,3)=="02"){
		     		if(TableClumnDefGrid.getRowColData(n,7)==TableClumnDefGrid.getRowColData(m,5)||TableClumnDefGrid.getRowColData(n,5)==TableClumnDefGrid.getRowColData(m,7)||TableClumnDefGrid.getRowColData(n,9)==TableClumnDefGrid.getRowColData(m,5)) 
		     		{
		     		    myAlert(""+"第"+""+n1+""+"行和第"+""+m1+""+"行有重复映射字段!"+"");	
		     		    return false; 
		     		}
		    	}
		   }
		}   
	}
	return true;
}

function queryClick()
{
  fm.OperateType.value="QUERY";
  window.open("./FrameLRFeeRateQuery.jsp?FeeTableNo="+fm.FeeTableNo.value,"true1");
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content, ReComCode, CertifyCode)
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
	  }
  }
}

function updateClick()
{
	fm.OperateType.value="UPDATE";
	if(!confirm(""+"你确定要修改该合同吗？"+""))
	{
		return false;
	}
	
	if(fm.State.value=='02'||fm.State.value==''||fm.State.value==null)
	{
	
     var mySql100=new SqlClass();
		mySql100.setResourceName("reinsure.LRFeeRateInputSql"); //指定使用的properties文件名
		mySql100.setSqlId("LRFeeRateInputSql100");//指定使用的Sql的id
		mySql100.addSubPara(fm.FeeTableNo.value);//指定传入的参数
	var strSQL12=mySql100.getString();
    
     // var strSQL12="select count(*) from RIFeeRateTableTrace where FeeTableNo='"+fm.FeeTableNo.value+"'";
      var arrResult=easyExecSql(strSQL12);
      
      for(i=0;i<arrResult;i++)
      {
      	var strSQL11 = "";
        var mySql101=new SqlClass();
		mySql101.setResourceName("reinsure.LRFeeRateInputSql"); //指定使用的properties文件名
		mySql101.setSqlId("LRFeeRateInputSql101");//指定使用的Sql的id
		mySql101.addSubPara(fm.FeeTableNo.value);//指定传入的参数
	 	strSQL11=mySql101.getString();
        //strSQL11 = "select state from RIFeeRateTableTrace where feetableno='"+fm.FeeTableNo.value+"'";
        turnPage.strQueryResult  = easyQueryVer3(strSQL11, 1, 1, 1);
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        
        if(turnPage.arrDataCacheSet[i][0]=='01')
        {
      	   myAlert(""+"费率表批次状态为有效，不能修改！"+"");
      	   return false;
        }
      }     
   }
	
	try {
		if( verifyInput() == true ) 
		{
			if (veriryInput3()==true)
			{
		  	var i = 0;
		  	var showStr=""+"正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
		  	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");  
				fm.action="./LRFeeRateSave.jsp";
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

function veriryInput4() //UPDATE 校验
{
	return true;
}

function deleteClick()
{
	var typeRadio="";
	var deleteType = "";
	fm.OperateType.value="DELETE";
	try 
	{
		if( verifyInput() == true ) 
		{
			if (veriryInput5()==true)
			{
		  	var i = 0;
		  	var showStr=""+"正在删除数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
		  	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
				fm.action="./LRFeeRateSave.jsp?DeleteType="+deleteType; 
		  	fm.submit(); 
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

function veriryInput5(){
	
	return true;
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
  fmImport.action = "./LRFeeRateImportSave.jsp?ImportPath="+ImportPath+"&FeeTableNo="+fm.FeeTableNo.value;
  fmImport.submit(); //提交
}

function nextStep()
{
	if (fm.FeeTableNo.value==''||fm.FeeTableNo.value==null)
	{
		myAlert(""+"请先查询费率表定义信息"+"");
		return false;
	}
	
	var mySql102=new SqlClass();
		mySql102.setResourceName("reinsure.LRFeeRateInputSql"); //指定使用的properties文件名
		mySql102.setSqlId("LRFeeRateInputSql102");//指定使用的Sql的id
		mySql102.addSubPara(fm.FeeTableNo.value);//指定传入的参数
	var	strSQL=mySql102.getString();
	//var strSQL="select count(*) from RIFeeRateTableDef where FeeTableNo='"+fm.FeeTableNo.value+"'";
	var arrResult=easyExecSql(strSQL);
	if (arrResult==null||arrResult=="")
	{
		myAlert(""+"费率表查询失败"+"");
		return false;
	}
	if(arrResult[0][0]=='0'){
		myAlert(""+"费率表查询失败"+"");
		return false;
	}
  var varSrc="&FeeTableNo='"+ fm.FeeTableNo.value+"' " ;
  window.open("./FrameMainFeeRateBatch.jsp?Interface=LRFeeRateBatchInput.jsp"+varSrc,"true1"); //+varSrc,
}

function getImportPath(){
  var strSQL = "";
  var mySql103=new SqlClass();
		mySql103.setResourceName("reinsure.LRFeeRateInputSql"); //指定使用的properties文件名
		mySql103.setSqlId("LRFeeRateInputSql103");//指定使用的Sql的id
		mySql103.addSubPara("1");
		strSQL=mySql103.getString();
  
  //strSQL = "select sysvarvalue from ldsysvar where sysvar='RIXmlPath'";
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

function verifyFeeRateTableImp(){
	
	var mySql104=new SqlClass();
		mySql104.setResourceName("reinsure.LRFeeRateInputSql"); //指定使用的properties文件名
		mySql104.setSqlId("LRFeeRateInputSql104");//指定使用的Sql的id
		mySql104.addSubPara("1");
	var strSQL=mySql104.getString();
	//var strSQL = " select count(*) from RIFeeRateTableTrace a where not exists (select * from RIFeeRateTable b where a.feetableno=b.feetableno and a.batchno=b.batchno)";
	var arrResult=easyExecSql(strSQL);
	if(arrResult[0][0]!='0'){
		myAlert(""+"存在未导入费率表的费率表批次，请及时导入费率表！"+"");
	}
	return true;
}

function afterQuery()
{
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

//提交前的校验、计算  
function beforeSubmit()
{
  //添加操作	
}           


