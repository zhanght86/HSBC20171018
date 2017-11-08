//               该文件中包含客户端需要处理的函数和事件
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量

// 查询按钮
function easyQueryClick() {
	initForm();
	if (fm.PolNo2.value == ""&&fm.ContNo.value == ""&&fm.AppntName.value == "")
	{
	   alert("保单号，险种号，投保人姓名不能同时为空");
       return false;
	} 
	// 书写SQL语句，如果是抵交下棋保费也允许退余额，如：保单出险的情况。
	var strSql = "select PolNo, riskcode, LeavingMoney, prem, amnt, SignDate, PayLocation "
	         + " from lcpol where appflag='1' and LeavingMoney<>0 "
	         + " and not exists (select PolNo from LCRnewStateHistory where contno=LCPol.contno and riskcode=LCPol.riskcode and state!='5') "
             ; 
    if(document.all('BankCode').value!="")
    {
       strSql= strSql+" and exists (select 1 from lccont where contno=lcpol.contno and bankcode='"+document.all('BankCode').value+"') "
    }
    strSql= strSql
      + getWherePart("ContNo", "ContNo")
      + getWherePart("PolNo", "PolNo2")
      + getWherePart("RiskCode", "RiskCode")
      + getWherePart("AppntName", "AppntName")
      +" and managecom like '"+comcode+"%'"
      ;
  if (fm.AppntName.value != "") 
  strSql = strSql + " and appntno in (select c.customerno from ldperson c where name='" + fm.AppntName.value + "')"; 
  strSql = strSql + " Order by SignDate";
				     
	//查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("没有要退余额的保单！");
    return "";
  }
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = BankGrid;    
  //保存SQL语句
  turnPage.strQuerySql     = strSql; 
  //设置查询起始位置
  turnPage.pageIndex       = 0;    
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);			     

  fm.PolNo.value = fm.PolNo2.value;
}

//提交，保存按钮对应操作
function submitForm()
{
  if(!checkData())	return false;
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
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
    initForm();
  }
} 

function checkData()
{
  if(document.all('PolNo').value==null||document.all('PolNo').value=='')
   {
   	alert("保单号码不能为空");
    return false;
   }
  if(document.all('LeavingMoney').value==null||document.all('LeavingMoney').value=='')
   {
    alert("实退余额不能为空");
    return false;
   }  
  // var tSql = "select ProPosalNo,leavingmoney,contno from LCPol where PolNo='"+document.all('PolNo').value+"'";
    var  sqlid1="NewPolFeeWithDrowSql0";
	var  mySql1=new SqlClass();
	mySql1.setResourceName("finfee.NewPolFeeWithDrowSql"); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(document.all('PolNo').value);//指定传入的参数
	tSql=mySql1.getString();
   var tResult = easyExecSql(tSql, 1, 1, 1);
   if(!tResult)
   {
     alert("没有查到对应的保单！");	
     return false;
   }
   if(tResult[0][1]==0)
   {
	alert("该保单无可退余额!");	
        return false;	
   }
   if(eval(document.all('LeavingMoney').value)<=0)
   {
     alert("输入的退费金额必须大于零!");	
     return false;	
   }
   if(eval(document.all('LeavingMoney').value)>tResult[0][1])
   {
     alert("输入的退费金额大于可退余额!");	
     return false;	
   }
  document.all('ProPosalNo').value=tResult[0][0];
  document.all('ContNo').value=tResult[0][2];
  document.all('PolNo2').value=document.all('PolNo').value;
  return true;
}

function showOne(parm1, parm2) {
  //判断该行是否确实被选中
	if(document.all(parm1).all('InpBankGridSel').value == '1' ) {
	  var index = (document.all(parm1).all('BankGridNo').value - 1) % (turnPage.blockPageNum * turnPage.pageLineNum);
	  fm.PolNo.value = turnPage.arrDataCacheSet[index][0];
	  fm.LeavingMoney.value=turnPage.arrDataCacheSet[index][2];
  }
	//document.all('divPayMoney').style.display = "";
}
