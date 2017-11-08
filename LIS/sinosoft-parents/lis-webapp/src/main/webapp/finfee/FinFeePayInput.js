  //               该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var showInfo;
var mDebug="0";
var tResourceName="finfee.FinFeePayInputSql";

//提交，保存按钮对应操作
function submitForm()
{
	//校验异地付费机构
		//	if (!CheckeinputCertify())
		//	{
		//		return false;
		//	}
	//限制付费管理机构代码必须大于六位		
	//alert (document.all('ManageCom').value);
	//alert ((document.all('ManageCom').value).length);    		
		//	if((document.all('PolicyCom').value).length<6) 
     // {
     // 	alert("业务员所在单位不是付费机构。付费机构代码必须大于六位");
     //   return false;	
    //  }
      	
  //首先检验录入框
  //alert("交费方式========="+fmSave.PayMode').value);
  if(!verifyInput()) return false;
  if(!checkValue()) return false; //附加检验

//  fmSave.ActuGetNo.value=document.all('ActuGetNo').value;
  
  var AccDate = fmSave.EnterAccDate.value;
	var now = getCurrentDate('-');
  if(fmSave.ActuGetNo.value!="")
  {

  //	fmSave.btnSave').disabled=true;
//zy 2009-04-09 16:44 增加对到账日期的提示
	  if(fmSave.FManageCom.value.length<8)
	  {
	  	if (window.confirm("付费机构不为四级机构，您确定继续付费吗?"))
	  	{
	  		if(compareDate(AccDate,now)!=0)
	  		{
	  			if (window.confirm("财务到账日期不为当日，您确定继续付费吗?"))
	  			{
			  		fmSave.btnSave.disabled=true;
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
					  document.getElementById("fmSave").submit(); //提交
					 	initInpBox();
				  }
			  }
			  else
			  {
			  		fmSave.btnSave.disabled=true;
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
					  document.getElementById("fmSave").submit(); //提交
					 	initInpBox();
			  }
	  	}
	  }
	  else
	  {
	  		if(compareDate(AccDate,now)!=0)
	  		{
	  			if (window.confirm("财务到账日期不为当日，您确定继续付费吗?"))
	  			{
			  		fmSave.btnSave.disabled=true;
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
					  document.getElementById("fmSave").submit(); //提交
					 	initInpBox();
				  }
			  }
			  else
			  {
			  		fmSave.btnSave.disabled=true;
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
					  document.getElementById("fmSave").submit(); //提交
					 	initInpBox();
					 	initInput(); //清空之前录入的内容
			  }
		}
  }
  else
   alert("请先查询！");
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
//  alert(FlagStr);

  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  else
  { 
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
  	//parent.fraInterface.initForm();
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    //showDiv(operateButton,"true"); 
    //showDiv(inputButton,"false"); 
    //执行下一步操作
  }
}

//提交后操作,服务器数据返回后执行的操作
function afterQuery( FlagStr, content )
{
  alert(FlagStr);
  showInfo.close();
	 
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  else
  { 
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
  	//parent.fraInterface.initForm();
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    showDiv(operateButton,"true"); 
    showDiv(inputButton,"false"); 
    //执行下一步操作
  }
}

//重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm()
{
  try
  {
//    showDiv(operateButton,"true"); 
//    showDiv(inputButton,"false"); 
	  initForm();
  }
  catch(re)
  {
  	alert("在LLReport.js-->resetForm函数中发生异常:初始化界面错误!");
  }
} 

//取消按钮对应操作
function cancelForm()
{
//  window.location="../common/html/Blank.html";
    showDiv(operateButton,"true"); 
    showDiv(inputButton,"false"); 
}
 
//提交前的校验、计算  
function beforeSubmit()
{
  //添加操作
	
}           


//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,50,82,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
}


//Click事件，当点击增加图片时触发该函数
function addClick()
{
  //下面增加相应的代码
  showDiv(operateButton,"false"); 
  showDiv(inputButton,"true"); 
}           

//Click事件，当点击“修改”图片时触发该函数
function updateClick()
{
  //下面增加相应的代码
  alert("没有该功能！");
}           

//Click事件，当点击“查询”图片时触发该函数
function queryClick()
{
  window.open("./FinFeePayQueryLJFIGet.html");
}           

//Click事件，当点击“删除”图片时触发该函数
function deleteClick()
{
  //下面增加相应的代码
  alert("没有该功能！");
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

function queryLJAGet()
{
   if(document.getElementById('ActuGetNo').value!="")
   {
   	document.all('aquery').disabled=true;
    var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    document.getElementById("fm").submit();
   }
//  else if(document.all('OtherNo').value!="")
//   {
//    var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
//    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//    var iWidth=550;      //弹出窗口的宽度; 
    //var iHeight=250;     //弹出窗口的高度; 
//    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
//    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
//    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
//    showInfo.focus();      
//    document.getElementById("fm").submit();  		
//   }
   else
   {
    //alert("实付号码不能为空！"); return ;	
    window.open("./FinFeePayQueryLJAGetMain.jsp","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
   }	
}

function checkValue()
{
  //如果付费方式是支票类
  if(fmSave.PayMode.value=='2'||fmSave.PayMode.value=='3')
  {
    if(fmSave.BankCode2.value==''||fmSave.ChequeNo.value=='') 	
      {
      	alert("付费方式是支票:付款银行和票据号码不能为空！");
      	return false;
      }
  }
  // zy 2009-05-25 11:36 增加网银代付
  if(fmSave.PayMode.value=='9')
  {
    if(fmSave.BankCode9.value==''||fmSave.BankAccNo9.value=='' ||fmSave.BankAccName9.value=='') 	
      {
      	alert("付费方式是网银代付:付款银行、银行账号、银行户名不能为空！");
      	return false;
      }
  }
  //如果付费方式是银行转账
//if(fmSave.PayMode').value=='4')
//  {
//    if(fmSave.BankCode').value==''||fmSave.BankAccNo').value==''||fmSave.BankAccName').value=='') 	
//      {
//      	alert("付费方式是银行转账:银行编码、银行账号、银行户名不能为空！");
//      	return false;
//      }      
//  }
//银行转账不允许在付费功能进行确认操作 zy 2009-04-02 19:35
  if(!(fmSave.PayMode.value=='1' || fmSave.PayMode.value=='2' || fmSave.PayMode.value=='3' || fmSave.PayMode.value=='9'))
  {
  	  alert("目前只支持现金、支票、网银代付四种付费方式的付费确认，请核实!");	
  	  return false;
  }
  return true;
	
} 
  
//如果付费方式选择“转账支票”，则显示银行账号
function showBankAccNo()
{ 
  if(fmSave.PayMode.value =="2"||fmSave.PayMode.value =="3")
  {
    divChequeNo.style.display="";	
    divBankAccNo.style.display="none";
    divNetBankAccNo.style.display="none";
  }
 // else if (fmSave.PayMode').value=="4")
 // {
 //   divChequeNo.style.display="none";  	
 // 	divBankAccNo.style.display="";	
//  }
// zy 2009-05-25 11:20 增加网银代付的付费方式，如果为网银代付，则显示银行编码和银行账号
else if(fmSave.PayMode.value=="9")
	{
		divNetBankAccNo.style.display="";
		divBankAccNo.style.display="none";	
    divChequeNo.style.display="none";
	}
  else  
  {
    divBankAccNo.style.display="none";	
    divChequeNo.style.display="none";
    divNetBankAccNo.style.display="none";
  }	

}
//打印付费凭证
function Print()
{
  //if(!verifyInput()) return false;
  //if(!checkValue()) return false; //附加检验
  if(document.all('ActuGetNo').value==null)
  {
  	alert("请输入实付号码！");
  	return false;
  	}
  fmprint.all('ActuGetNo1').value=document.all('ActuGetNo').value;
  if(fmprint.all('ActuGetNo1').value==document.all('ActuGetNo').value)
  {
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
  fmprint.target="f1print";   
  document.getElementById("fmprint").submit(); //提交
  showInfo.close();
 	initInpBox();
  }
  else
   alert("请先录入实收号码！");  	

}

function afterCodeSelect(cCodeName, Field){
  showBankAccNo();	
  if(cCodeName=="Mode"){
     initInput();
  	}
  	if(cCodeName == "FINAbank" ){
			checkBank(fmSave.BankCode9,fmSave.BankAccNo9);
 		}
}
function initInput(){
   try{
  	  fmSave.BankCode.value ='';
  	  fmSave.BankAccName.value ='';
  	  fmSave.BankName.value ='';
  	  fmSave.BankName2.value ='';
  	  fmSave.BankAccNo.value ='';
  	  fmSave.BankCode2.value ='';
  	  fmSave.ChequeNo.value ='';
  	  fmSave.BankCode9.value ='';
  	  fmSave.BankName9.value ='';
  	  fmSave.BankAccNo9.value ='';
  	  fmSave.BankAccName9.value ='';
  	  }catch(ex){}
}

function easyQueryClick() {
	
	if((document.all('ActuGetNo').value==null||document.all('ActuGetNo').value=="")&&(document.all('OtherNo').value==null||document.all('OtherNo').value==""))
  {
  	alert("请录入实付号码/保全受理号/赔案号/投保单印刷号（其之中之一）");
    return false;
  }
 //if((document.all('PolicyCom').value).length<6) 
 //     {
  //    	alert("业务员所在单位不是付费机构。付费机构代码必须大于六位");
  //      return false;	
   //   }
  //查询付费机构
  //var ManageComSql = "select ManageCom from LJAGet where 1=1 and ConfDate is null and EnterAccDate is null and (bankonthewayflag='0' or bankonthewayflag is null) and paymode<>'4' "
	//           + getWherePart( 'ActuGetNo' )
	//           + getWherePart( 'OtherNo' )
	var ManageComSql =wrapSql(tResourceName,"LJAGet1",[document.all('ActuGetNo').value,document.all('OtherNo').value]);
	           			 
	var ManageComResult = easyExecSql(ManageComSql);
	document.all('ManageCom').value = ManageComResult;
	if(ManageComResult!=null&&ManageComResult!="")
	{
		if(trim(comCode).length >trim(document.all('ManageCom').value).length )
	 {
		alert("您登录机构的权限不够，请以交费机构或者上级机构登录");
		return false;
	}

     if(document.all('ManageCom').value.substring(0,trim(comCode).length)!= trim(comCode))
    {
    	alert("您登录机构的权限不够，请重新登录");
    	return false;
    	}
		
	}

    	
    	
  //var ModeSql = "select PayMode,bankonthewayflag from ljaget where 1=1 and ConfDate is null and EnterAccDate is null and rownum=1 "
	//           + getWherePart( 'ActuGetNo' )
	//           + getWherePart( 'OtherNo' )        
	
	var ModeSql =warpSql('LJAGet2',[document.all('ActuGetNo').value,document.all('OtherNo').value]);
	
	var TarrResult = easyQueryVer3(ModeSql, 1, 1, 1);
	var ArrData = decodeEasyQueryResult(TarrResult);
	if (TarrResult!=null&&TarrResult!="")
 	  {
   	    if (ArrData[0][0]=='4'&&ArrData[0][1]=='1')
   	    {
   	      alert("该笔付费的付费方式为银行划款，不能在此操作，且已经在送银行途中");	
   	      return false;
   	    }
   	    else if (ArrData[0][0]=='4')
   	    {
   	      alert("该笔付费的付费方式为银行划款，不能在此操作");	
   	      return false;   	    	
   	    }  	
  	}
	
  // 拼SQL语句，从页面采集信息
  //var strSql = "select ActuGetNo, OtherNo, OtherNoType,PayMode,currency,SumGetMoney,EnterAccDate,Drawer,DrawerID from LJAGet where 1=1 and ConfDate is null and EnterAccDate is null and (bankonthewayflag='0' or bankonthewayflag is null) and paymode<>'4' "
	//           + getWherePart( 'ActuGetNo' )
	//           + getWherePart( 'OtherNo' )

  var strSql =warpSql('LJAGet3',[document.all('ActuGetNo').value,document.all('OtherNo').value]);
  //查询付费机构sql,返回结果
                   	                
	//查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);  
                  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {  
    //清空MULTILINE，使用方法见MULTILINE使用说明 
    QueryLJAGetGrid.clearData('QueryLJAGetGrid');  
    alert("没有查询到数据！");
    return false;
  }
  turnPage.pageLineNum = 5;
  //清空数据容器，两个不同查询共用一个turnPage对象时必须使用，最好加上，容错
	turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = QueryLJAGetGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSql; 
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, turnPage.pageLineNum);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  
  //控制是否显示翻页按钮
  if (turnPage.queryAllRecordCount > turnPage.pageLineNum) {
    try { window.divPage.style.display = ""; } catch(ex) { }
  } else {
    try { window.divPage.style.display = "none"; } catch(ex) { }
  }

    	
	//校验异地付费机构
	//if (!CheckeinputCertify())
//	{
	//	return false;
//	}

  
}
function GetRecord()
{
  var arrReturn = new Array();
	var tSel = QueryLJAGetGrid.getSelNo();	
	if( tSel == 0 || tSel == null )
	{
		alert( "请先选择一条记录，再点击返回按钮。" );
	}
		

	else 
	{
		//alert("11111111111111111");
    try
		{
     document.all('ActuGetNo').value=QueryLJAGetGrid.getRowColData(tSel-1,1);           
     fmSave.PolNo.value=QueryLJAGetGrid.getRowColData(tSel-1,2);           
     fmSave.PayMode.value=QueryLJAGetGrid.getRowColData(tSel-1,4);
fmSave.Currency.value=QueryLJAGetGrid.getRowColData(tSel-1,5);         
     fmSave.GetMoney.value=QueryLJAGetGrid.getRowColData(tSel-1,6);         
     fmSave.SDrawer.value=QueryLJAGetGrid.getRowColData(tSel-1,8);         
     fmSave.SDrawerID.value=QueryLJAGetGrid.getRowColData(tSel-1,9);
      
	  }
	  	catch(ex)
		{
				alert(ex);
		}
	}  
	   
	   
}

// 异地付费操作的切换函数
function inputCertify(objCheck)
{
	if (document.all('PolicyCom').value==document.all('ManageCom').value.substring(0, document.all('PolicyCom').value.length))
	{
		if(objCheck.checked == true)
  	{	
  		alert("此次操作不属于异地付费！");
  		return;	
  	}
	}
	if(objCheck.checked == true) {
		
		showDiv(PolicyComInPut,"true");
		
	} else {
		
		showDiv(PolicyComInPut,"false");
	}
}    

//异地付费校验
function CheckeinputCertify()
{
  //if (document.all('PolicyCom').value != document.all('ManageCom').value)
  
  //alert("document.all('PolicyCom').value==========="+document.all('PolicyCom').value);
  //alert("document.all('PolicyCom').value.length==========="+document.all('PolicyCom').value.length);
  //alert("document.all('ManageCom').value==========="+document.all('ManageCom').value);
  //alert("document.all('ManageCom').value.substring(0, document.all('PolicyCom').value.length)==========="+document.all('PolicyCom').value.substring(0, document.all('PolicyCom').value.length));
  
  if (document.all('ManageCom').value.substring(0,6)!=document.all('PolicyCom').value.substring(0,6))
  {
  	if(fm.chkPrtNo.checked == true)
  	{
  		if((document.all('PolicyCom').value==null||document.all('PolicyCom').value==""))
		  {
		  	alert("请输入付费机构");
		    return false;
		  }
  		return true;
  	}else{
  		alert("此次付费属于异地付费，请选择异地付费后再操作");
  		return false;
  	}
  }else{
  	if(fm.chkPrtNo.checked == true)
  	{
  		alert("此次操作不属于异地付费");
  		showDiv(PolicyComInPut,"false");
  		fm.chkPrtNo.checked == none;
  	}
  	return true;
  }
}    

function checkBank(tBankCode,tBankAccNo){
	if(tBankCode.value.length>0 && tBankAccNo.value.length>0){
		if(!checkBankAccNo(tBankCode.value,tBankAccNo.value)){
			
			tBankAccNo.value = "";
			return false;
		}
	}
}