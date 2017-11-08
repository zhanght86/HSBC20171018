//               该文件中包含客户端需要处理的函数和事件
//window.onfocus=myonfocus;
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var myCheckInsuAccNo = "";
var myCheckDate = "";
var sqlresourcename = "acc.PriceInfoInputInputSql";

//提交，保存按钮对应操作
function submitFrom()
{
	QueryCompanyUnitCount();
  if(beforeSubmit())
  {
	var mySql=new SqlClass();
        mySql.setResourceName(sqlresourcename);
        mySql.setSqlId("PriceInfoInputInputSql0");
        mySql.addSubPara(document.all('InsuAccNo').value);
        mySql.addSubPara(document.all('StartDate').value);
	var  oldarrResult =new Array;
	oldInsuAccNo= document.all('InsuAccNo').value;
	oldStartDate= document.all('StartDate').value;

	oldarrResult=easyExecSql(mySql.getString());
            
	  if (oldarrResult>0)
	  {
	     alert ("该数据已存在，请选择其它操作！");	
	     return false;
	  } 
  var i = 0;
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
   document.all('Transact').value ="INSERT";
  document.getElementById("fm").submit(); //提交 
  }
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit1( FlagStr, content )
{
//  alert(FlagStr);
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  else
  { 
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
  }
  resetForm();
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
  	alert("toulian.js-->resetForm函数中发生异常:初始化界面错误!");
  }
} 

//取消按钮对应操作
function cancelForm()
{
//  window.location="../common/html/Blank.html";
}

var oldInsuAccNo;
var oldStartDate;
 function updateClick()
{
  //下面增加相应的代码
  //表单中的隐藏字段"活动名称"赋为UPDATE 
  if(beforeSubmit())
  { 	
  	if(document.all('State').value!='1')
	{
	  	alert("该记录不处于录入状态，不能修改！");
	  	return false;
	 }
    if(parseFloat(fm.CompanyUnitCount.value)+parseFloat(fm.ComChgUnitCount.value)<0)
    {
  		alert("变动后公司投资单位数不能为负！");
  		return false;
    }
    document.all('Transact').value ="UPDATE";
    var i = 0;
    var showStr="正在更新数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	document.getElementById("fm").submit(); //提交 
  }
}       
//提交前的校验、计算  
function beforeSubmit()
{
  //添加操作
  //个人信息表中不能为空的字段检验,包括2部分
  //页面显示控件中要输入的字段(CustomerNo,Name,Sex,Birthday,Operator)	
  //隐藏的字段(MakeDate,MakeTime,ModifyDate,ModifyTime),在ClientConjoinQueryUI中输入
    if(document.all('InsuAccNo').value == '')
    {
    	alert("保险帐户号码不能为空!");
    	return false;
    	}
    if(document.all('StartDate').value == '')
    {
    	alert("计价日期不能为空!");
    	return false;
    	}
   if(!isDate(document.all('StartDate').value))
    {
    	alert("计价日期输入有误!");
    	return false;
    	}  
    	
    if(document.all('SKFlag').value == '')
    {
    	alert("收缩扩张标志不能为空!");
    	return false;
    	}

//    if(!isDate(document.all('SRateDate').value))
//    {
//    	alert("价格应公布日期输入有误!");
//    	return false;
//    	}  
//    if(!isDate(document.all('ARateDate').value))
//    {
//    	alert("价格实际公布日期输入有误!");
//    	return false;
//    	}  
    	
    	
    if(document.all('InsuTotalMoney').value == '')
    {
    	alert("尚未填写账户总资产!");
    	return false;
    }else{
    	if(!isNumeric(document.all('InsuTotalMoney').value))
    	{
    		alert("财产总资产的输入格式出错!");
    		return false;
    	}
    }
    if(document.all('Liabilities').value == '')
    {
    	alert("尚未填写负债!");
    	return false;
    }else{
    	if(!isNumeric(document.all('Liabilities').value))
    	{
    		alert("负债的输入格式出错!");
    		return false;
    	}
    }	
    if(document.all('CompanyUnitCount').value == '')
    {
    	alert("公司投资单位数不能为空!");
    	return false;
    }	
    if(document.all('CustomersUnitCount').value =='')
    {
    	alert("客户投资单位数不能为空!");
    	return false;
    }	
    if(document.all('ComChgUnitCount').value == '')
    {
    	alert("尚未填写本次变动单位数!");
    	return false;
    }else{
    	if(!isNumeric(document.all('ComChgUnitCount').value))
    	{
    		alert("本次变动单位数的输入格式出错!");
    		return false;
    	}
    }	
    	
   	if(document.all( 'StateName' ).value!='1-录入')
   	{
   		alert("非录入状态，请点击“重置”或者刷新页面!");
    	return false;
   		}
    	if(document.all('State').value == '')
    {
    	alert("记录状态不能为空!");
    	return false;
    	}	
    return true;
}           

//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
  if(cDebug=="1") {
	parent.fraMain.rows = "0,0,50,82,*";
  } else {
  	parent.fraMain.rows = "0,0,0,82,*";
  }
}

function queryValidCertify()
{
  alert("queryValidCertify");
  // submitForm();  
}    

function queryInvalidCertify( )
{
  alert("queryInvalidCertify");
}       

function queryClientClick()
{
  //下面增加相应的代码
//  window.showModalDialog("./ClientConjoinQueryQuery.html",window,"status:0;help:0;edge:sunken;dialogHide:0");
  //查询命令单独弹出一个模态对话框，并提交，和其它命令是不同的
  //因此，表单中的活动名称也可以不用赋值的
  alert("./PriceInfoQuery.html");
  window.open("./PriceInfoQuery.html");
  
}           
function queryClick()
{
	var loadFlag = "0";

	try
	{
		if( top.opener.mShowCustomerDetail == "GROUPPOL" ) loadFlag = "1";
	}
	catch(ex){}
	
	if( loadFlag == "1" )
		parent.fraInterface.window.location = "./PriceInfoQuery.jsp";
	else
    	window.open("./PriceInfoQuery.html");
  
}           

//显示div，第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
function showDiv(cDiv,cShow)
{
  if (cShow=="true")
  {
    cDiv.style.display="";
  }
  else
  {
    cDiv.style.display="none";  
  }
}
function deleteClick()
{
  //下面增加相应的代码
//  alert("delete");
  //尚未考虑全部为空的情况
 if(document.all('InsuAccNo').value == '')
  {
   alert("保险帐户号码不能为空!");
   return false;
  }else if(document.all('StartDate').value == '')
  {
   alert("计价日期不能为空!");
   return false;
  } else
  {
   if(document.all('State').value!='1')
  {
  	alert("该记录不处于录入状态，不能删除！");
  	return false;
  }
    document.all('Transact').value ="DELETE";
    var i = 0;
    var showStr="正在删除数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    document.getElementById("fm").submit(); //提交 
    //initForm();
  }
  
}          
function afterQuery(arrQueryResult)
{
 
	var arrResult = new Array();
	if( arrQueryResult != null )
	{
		arrResult = arrQueryResult;
		document.all( 'InsuAccNo' ).value = arrResult[0][0];
		oldInsuAccNo= arrResult[0][0];
		document.all( 'StartDate' ).value = arrResult[0][1];
		oldStartDate = arrResult[0][1];
		document.all( 'EndDate' ).value = arrResult[0][2];
		document.all( 'InvestFlag' ).value = arrResult[0][3];
		document.all( 'SRateDate' ).value = arrResult[0][4];
		document.all( 'ARateDate' ).value = arrResult[0][5];
		document.all( 'InsuTotalMoney' ).value = arrResult[0][6];
		document.all( 'Liabilities' ).value = arrResult[0][7];
		document.all( 'RedeemRate' ).value = arrResult[0][8];
		document.all( 'State' ).value = arrResult[0][9];
			alert("hehe--State="+document.all( 'State' ).value);
		if(document.all( 'State' ).value=='0')
		{
				alert("hehe");
				document.all( 'StateName' ).value='0-生效'
		}
		if(document.all( 'State' ).value=='1')
		{
			document.all( 'StateName' ).value='1-录入'
		}
		if(document.all( 'State' ).value=='2')
		{
			document.all( 'StateName' ).value='2-复核正确'
		}
		if(document.all( 'State' ).value=='3')
		{
			document.all( 'StateName' ).value='3-复核错误'
		}
		if(document.all( 'State' ).value=='4')
		{
			document.all( 'StateName' ).value='4-确认正确'
		}
		if(document.all( 'State' ).value=='5')
		{
			document.all( 'StateName' ).value='5-确认错误'
		}
		document.all( 'RedeemMoney' ).value = arrResult[0][10];
	}
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  else
  { 
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }

  	fm.QueryState.value = "1";
  	easyQueryClick();
	document.all( 'InsuAccNo' ).value = '';
	document.all('InsuAccNoName').value = '';
	document.all( 'StartDate' ).value = '';
	document.all( 'InsuTotalMoney' ).value = '';
	document.all( 'Liabilities' ).value = '';
	document.all( 'State' ).value = '1';
	document.all( 'StateName' ).value='1-录入';
	document.all('CompanyUnitCount').value = '';
	document.all('ComChgUnitCount').value = '0';
	document.all('CustomersUnitCount').value = '';
	document.all('SKFlag').value = '';
   	document.all('SKFlagName').value = '';
	myCheckDate = '';
	myCheckInsuAccNo = '';
    document.all('Transact').value = '';
}

function easyQueryClick()
{
	// 初始化表格
	initCollectivityGrid();

	// 书写SQL语句
	var mySql=new SqlClass();
	    mySql.setResourceName(sqlresourcename);
	    mySql.setSqlId("PriceInfoInputInputSql1");
	    mySql.addSubPara(fm.QureyInsuAccNo.value);
	    mySql.addSubPara(fm.QueryStartDate.value);
	    mySql.addSubPara(fm.QueryState.value);

	turnPage.queryModal(mySql.getString(), CollectivityGrid);
	if(CollectivityGrid.mulLineCount <= 0){
		alert("没有符合条件的数据！");
		return false;
	}
}

 function ShowPlan()
{
 	var arrResult = new Array();
	var tSel = CollectivityGrid.getSelNo();	
	if( tSel == 0 || tSel == null )
	{
		alert( "请先选择一条记录!" );
		return;
	}			
	else
	{
	//设置需要返回的数组
		var strSQL = "";
		var qqq1 = CollectivityGrid.getRowColData(tSel-1,1);
		var qqq2 = CollectivityGrid.getRowColData(tSel-1,2);
		var mySql=new SqlClass();
	       mySql.setResourceName(sqlresourcename);
		   mySql.setSqlId("PriceInfoInputInputSql4");
		   mySql.addSubPara(qqq1);
		   mySql.addSubPara(qqq2);
		strSQL = mySql.getString();
		turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
		if (!turnPage.strQueryResult) {    //判断是否查询成功
		    alert("查询失败！");
		    return false;
	    }
    
	//查询成功则拆分字符串，返回二维数组
     arrResult = decodeEasyQueryResult(turnPage.strQueryResult); 
		document.all( 'InsuAccNo' ).value = arrResult[0][0];
		oldInsuAccNo= arrResult[0][0];
		InputInsuAccNoName();
		document.all( 'StartDate' ).value = arrResult[0][1];
		oldStartDate = arrResult[0][1];
		//document.all( 'EndDate' ).value = arrResult[0][2];
		//document.all( 'InvestFlag' ).value = arrResult[0][3];
		//if(document.all( 'InvestFlag' ).value=='1')
		//{
		//	document.all( 'InvestFlagName' ).value='金额类型';
		//}
		//if(document.all( 'InvestFlag' ).value=='2')
		//{
		//	document.all( 'InvestFlagName' ).value='份额类型';
		//}
		//document.all( 'SRateDate' ).value = arrResult[0][4];
		//document.all( 'ARateDate' ).value = arrResult[0][5];
		document.all( 'InsuTotalMoney' ).value = arrResult[0][6];
		document.all( 'Liabilities' ).value = arrResult[0][7];
		//document.all( 'RedeemRate' ).value = arrResult[0][8];
		//if(document.all( 'RedeemRate' ).value=='null')
		//{
		//	document.all( 'RedeemRate' ).value='';
		//}
		//alert("Flag");
		document.all( 'State' ).value = arrResult[0][9];
			//alert("hehe--State="+document.all( 'State' ).value);
		if(document.all( 'State' ).value=='0')
		{
			//	alert("hehe");
			document.all( 'StateName' ).value='0-生效'
		}
		if(document.all( 'State' ).value=='1')
		{
			document.all( 'StateName' ).value='1-录入'
		}
		if(document.all( 'State' ).value=='2')
		{
			document.all( 'StateName' ).value='2-复核正确'
		}
		if(document.all( 'State' ).value=='3')
		{
			document.all( 'StateName' ).value='3-复核错误'
		}
		if(document.all( 'State' ).value=='4')
		{
			document.all( 'StateName' ).value='4-确认正确'
		}
		if(document.all( 'State' ).value=='5')
		{
			document.all( 'StateName' ).value='5-确认错误'
		}
		//document.all( 'RedeemMoney' ).value = arrResult[0][10];
		//if(document.all( 'RedeemMoney' ).value =='null')
		//{
		//	document.all( 'RedeemMoney' ).value ='';
		//}  
		document.all( 'CompanyUnitCount' ).value = arrResult[0][11];
		document.all( 'ComChgUnitCount' ).value = arrResult[0][12];
		document.all('CustomersUnitCount').value = arrResult[0][13];
		document.all('SKFlag').value = arrResult[0][14];
		if(document.all('SKFlag').value=='0')
		{
			document.all('SKFlagName').value='扩张期';
		}
		else
		{
			document.all('SKFlagName').value='收缩期';
		}
	}
	 
}

 function InputInsuAccNoName()
 {
      var mySql=new SqlClass();
	       mySql.setResourceName(sqlresourcename);
		   mySql.setSqlId("PriceInfoInputInputSql8");
		   mySql.addSubPara(document.all('InsuAccNo').value);
 	   var tDateResult = easyExecSql(mySql.getString());

       if(tDateResult!=null)
       { 
       	document.all( 'InsuAccNoName' ).value=tDateResult[0][1];
       }
 }

function QueryCompanyUnitCount()
{
	var insuaccno = document.all('InsuAccNo').value;
	var mydate = document.all('StartDate').value;
	var mySql=new SqlClass();
	    mySql.setResourceName(sqlresourcename);
	    mySql.setSqlId("PriceInfoInputInputSql5");
	    mySql.addSubPara(insuaccno);
	    mySql.addSubPara(mydate);
	    mySql.addSubPara(insuaccno);
    var strSQL = mySql.getString();
    var result = easyExecSql(strSQL); 
    if(result!=null)
    {
    	document.all('CompanyUnitCount').value = result[0][0];
    }
  
	var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
	    mySql2.setSqlId("PriceInfoInputInputSql6");
	    mySql2.addSubPara(insuaccno);
    var strSQL2 = mySql2.getString();
    var result2 = easyExecSql(strSQL2); 
    if(result2[0][0]=='null')
    {
    	document.all('CustomersUnitCount').value = '';
	}
    else
    {
    	document.all('CustomersUnitCount').value = result2[0][0];
    }
}

function CheckPriceDate()
{
	if(document.all('InsuAccNo').value=="")
	{
		alert("请先输入投资帐户号码！");
		document.all('StartDate').value = "";
		myCheckDate = "";
		document.all('CompanyUnitCount').value = "";
		document.all('CustomersUnitCount').value = "";
		return false;
	}
	else
	{
		var maxPriceDate = document.all('StartDate').value;
		var mySql=new SqlClass();
			mySql.setResourceName(sqlresourcename);
		    mySql.setSqlId("PriceInfoInputInputSql7");
		    mySql.addSubPara(document.all('InsuAccNo').value);
		var strSQL = mySql.getString();
		var result = easyExecSql(strSQL); 
		if(result[0][0]>=maxPriceDate)
		{
			//alert(maxPriceDate);
			alert(result[0][0]+"已经有"+document.all('InsuAccNo').value+"("+document.all('InsuAccNoName').value+")的已经生效的单位价格记录，且在您输入的日期之后，请重新输入资产评估日期，不能早于"+result[0][0]+"！");
			document.all('StartDate').value = "";
			myCheckDate = "";
			document.all('CompanyUnitCount').value = "";
			document.all('CustomersUnitCount').value = "";
			return false;
		}
   }
  return true;
}

function myCheckFiled()
{
//	if(CollectivityGrid.getSelNo()==0)
//	{
	if(document.all('StateName').value == '1-录入')
	{
		if(myCheckInsuAccNo!=document.all('InsuAccNo').value)
		{
			if(document.all('StartDate').value!="")
			{
			QueryCompanyUnitCount();
			myCheckInsuAccNo=document.all('InsuAccNo').value;
		  }
		}
		if(myCheckDate!=document.all('StartDate').value)
		{
			if(CheckPriceDate())
			{
			QueryCompanyUnitCount();
			myCheckDate = document.all('StartDate').value;
		  }
		}
	}
//}
}