//               该文件中包含客户端需要处理的函数和事件
//window.onfocus=myonfocus;
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var sqlresourcename = "acc.CheckPriceInfoInputInputSql";
//提交，保存按钮对应操作

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

 function updateClick0()
 {
 	  if(document.all('State').value=='0')
 	  {
 	   alert("记录状态已处于有效状态！");
 	   initForm();
 	  	return false;
 	 }else{

 	  	document.all('State').value=0;
 	  	document.all( 'StateName' ).value='0-生效';
 	    updateClick();

  }
 }
  function updateClick2()
  {
  	if(document.all('State').value=='1')
  	{
  	document.all('State').value=2;
  	updateClick();
  			document.all( 'StateName' ).value='2-复核正确';
    }else{
    		alert("记录状态不处于录入状态，不能进行此操作！");
 	  	return false;
    }
  }
   function updateClick3()
   {
   	  if(document.all('State').value=='1')
   	  {
     	document.all('State').value=3;
     	updateClick();
     	document.all( 'StateName' ).value='3-复核错误';
       }else{
    		alert("记录状态不处于录入状态，不能进行此操作！");
 	    	return false;
        }
   }
    function updateClick4()
    {
    		if(document.all('State').value=='2')
    		{
       	document.all('State').value=4;
       	updateClick();
       			document.all( 'StateName' ).value='4-确认正确';
         }else{
    		alert("记录状态不处于复核正确状态，不能进行此操作！");
 	     	return false;
         }
    }
    function updateClick5()
    {
    	//if(document.all('State').value=='3')
    	//	{
       	document.all('State').value=5;
       	updateClick();
       		document.all( 'StateName' ).value='5-确认错误';
      //   }else{
    	//	alert("记录状态不处于复核错误状态，不能进行此操作！");
 	     //	return false;
       //  }
    }


var oldRiskCode;
var oldInsuAccNo;
var oldStartDate;
var oldEndDate;
var oldInvestFlag;
var oldSRateDate;
var oldARateDate;
var oldUnitPriceBuy;
var oldUnitPriceSell;
var oldRedeemRate;
var oldRedeemMoney;
 function updateClick()
{
  //下面增加相应的代码
  //表单中的隐藏字段"活动名称"赋为UPDATE


  document.all('DoBatch').value = '';

   if(document.all('InsuAccNo').value == '')
    {
    alert("请先选择要置为有效的记录!");
    initForm();
    	return false;
    	}
    if(document.all('StartDate').value == '')
    {
    	alert("请先选择要置为有效的记录!");
    	initForm();
    	return false;
    	}
   if(oldInsuAccNo!=document.all('InsuAccNo').value)
  {
  	alert("不能修改数据2！");
  	return false;
  }
  //alert(document.all('StartDate').value);
   if(oldStartDate!=document.all('StartDate').value)
  {
  	alert("不能修改数据3！");
  	return false;
  }

//  if(oldEndDate!=document.all('EndDate').value)
//  {
//  	alert("不能修改数据4！");
//  	return false;
//  }
//  if(oldInvestFlag!=document.all('InvestFlag').value)
//  {
//  	alert("不能修改数据5！");
//  	return false;
//  }
//  if(oldSRateDate!=document.all('SRateDate').value)
//  {
//  	alert("不能修改数据6！");
//  	return false;
//  }
//  if(oldARateDate!=document.all('ARateDate').value)
//  {
//  	//alert("不能修改数据7！");
//  	//alert(oldARateDate+";;;"+document.all('ARateDate').value);
//  	//return false;
//  }
//
//  if(oldUnitPriceBuy!=document.all('UnitPriceBuy').value)
//  {
//  	alert("不能修改数据8！");
//  	return false;
//  }
//  if(oldUnitPriceSell!=document.all('UnitPriceSell').value)
//  {
//  	alert("不能修改数据9！");
//  	return false;
//  }
//  if(oldRedeemRate!=document.all('RedeemRate').value)
//  {
//  	alert("不能修改数据10！");
//  	return false;
//  }
//  if(oldRedeemMoney!=document.all('RedeemMoney').value)
//  {
//  	alert("不能修改数据11！");
//  	return false;
//  }

    document.all('Transact').value ="CONFIRM";
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
   //initForm();
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

    if(document.all('SRateDate').value == '')
    {
    	alert("价格应公布日期不能为空!");
    	return false;
    	}

    if(!isDate(document.all('SRateDate').value))
    {
    	alert("价格应公布日期输入有误!");
    	return false;
    	}
    if(!isDate(document.all('ARateDate').value))
    {
    	alert("价格实际公布日期输入有误!");
    	return false;
    	}


    if(document.all('UnitPriceBuy').value == '')
    {
    	alert("单位买入价格不能为空!");
    	return false;
    	}
    if(document.all('UnitPriceSell').value == '')
    {
    	alert("单位卖出价格不能为空!");
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


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
	//alert("afterSubmit-begin");
  showInfo.close();
  //alert("afterSubmit-begin");
  if (FlagStr == "Fail" )
  {  //alert("afterSubmit-01");
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
  { //alert("afterSubmit-02");
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
//    initForm();
  }
  //alert("afterSubmit-03");
  easyQueryClick();
}

function easyQueryClick()
{
	// 初始化表格
	initCollectivityGrid();

	// 书写SQL语句
	var mySql=new SqlClass();
	    mySql.setResourceName(sqlresourcename);
	    mySql.setSqlId("CheckPriceInfoInputInputSql1");
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
		   mySql.setSqlId("CheckPriceInfoInputInputSql4");
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
    	//alert(arrResult);
   		document.all( 'RiskCode' ).value = arrResult[0][0];
		oldRiskCode=arrResult[0][0];
		//InputRiskCodeName();
		document.all( 'InsuAccNo' ).value = arrResult[0][1];
		oldInsuAccNo= arrResult[0][1];
		InputInsuAccNoName();
		document.all( 'StartDate' ).value = arrResult[0][2];
		oldStartDate = arrResult[0][2];
		document.all( 'EndDate' ).value = arrResult[0][3];
		document.all( 'InvestFlag' ).value = arrResult[0][4];
		//if(document.all( 'InvestFlag' ).value=='1')
		//{
		//	document.all( 'InvestFlagName' ).value='金额类型';
		//}
		//if(document.all( 'InvestFlag' ).value=='2')
		//{
		//	document.all( 'InvestFlagName' ).value='份额类型';
		//}
		document.all( 'SRateDate' ).value = arrResult[0][5];
		document.all( 'ARateDate' ).value = arrResult[0][6];
		document.all( 'InsuTotalMoney' ).value = arrResult[0][7];
		document.all( 'Liabilities' ).value = arrResult[0][8];
		//document.all( 'RedeemRate' ).value = arrResult[0][9];
		//if(document.all( 'RedeemRate' ).value=='null')
		//{
		//	document.all( 'RedeemRate' ).value='';
		//}
		//alert("Flag");
		document.all( 'State' ).value = arrResult[0][10];
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
		//document.all( 'RedeemMoney' ).value = arrResult[0][11];
		//if(document.all( 'RedeemMoney' ).value =='null')
		//{
		//	document.all( 'RedeemMoney' ).value ='';
		//}  
		document.all( 'CompanyUnitCount' ).value = arrResult[0][12];
		document.all( 'ComChgUnitCount' ).value = arrResult[0][13];
		document.all('CustomersUnitCount').value = arrResult[0][14];
		document.all('SKFlag').value = arrResult[0][15];
//		if(document.all('SKFlag').value=='0')
//		{
//			document.all('SKFlagName').value='扩张期';
//		}
//		else
//		{
//			document.all('SKFlagName').value='收缩期';
//		}
		document.all('UnitPriceBuy').value = CollectivityGrid.getRowColData(tSel-1,3);
		document.all('UnitPriceSell').value = CollectivityGrid.getRowColData(tSel-1,3);
	}
}
 function InputRiskCodeName()
 {
 	var mySql=new SqlClass();
       mySql.setResourceName(sqlresourcename);
	   mySql.setSqlId("CheckPriceInfoInputInputSql9");
	   mySql.addSubPara(document.all('RiskCode').value);
	   var tDateResult = easyExecSql(mySql.getString());

     if(tDateResult!=null)
     { 
     	document.all( 'RiskCodeName' ).value=tDateResult[0][1];
     }
 }
 function InputInsuAccNoName()
 {
      var mySql=new SqlClass();
	       mySql.setResourceName(sqlresourcename);
		   mySql.setSqlId("CheckPriceInfoInputInputSql8");
		   mySql.addSubPara(document.all('RiskCode').value);
		   mySql.addSubPara(document.all('InsuAccNo').value);
 	   var tDateResult = easyExecSql(mySql.getString());

       if(tDateResult!=null)
       { 
       	document.all( 'InsuAccNoName' ).value=tDateResult[0][1];
       }
 }

 function BatchDeal()
 {
 	   if(!BatchDealCheck())
 	   {
 	   	   return ;
 	   }
     document.all('DoBatch').value = 'OK';
 	   //location="./BatchDealSave.jsp?&DealDate="+document.all('DealDate').value;
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
 	     //location="./CheckPriceInfoInput.jsp";
 	      document.getElementById("fm").submit();
}
function findriskcode()
{
	 if(document.all('QureyRiskCode').value == '')
 	   {
 	   	alert("请先选择险种编码！");
 	   	return false;
 	  }
}
function findriskcode2()
{
	 if(document.all('RiskCode').value == '')
 	   {
 	   	alert("请先选择险种编码！");
 	   	return false;
 	  }
}
function BatchDealCheck()
{
	   if(document.all('DealDate').value == '')
 	   {
 	   	  alert("计价日期不能空！");
 	   	  return false;
 	   }
 	   if(document.all('NextPriceDate').value == '')
 	   {
 	   	  alert("下个计价日不能空！");
 	   	  return false;
 	   }
 	   var mySql=new SqlClass();
	       mySql.setResourceName(sqlresourcename);
		   mySql.setSqlId("CheckPriceInfoInputInputSql7");
		   mySql.addSubPara(document.all( 'DealDate' ).value);
 	   var arr = easyExecSql(mySql.getString());

    // mySql.setSqlId("CheckPriceInfoInputInputSql_5");
     if(arr[0][0]=='0')
     {
     	   if(!confirm("本次批处理未对所有投资帐户进行计价，是否继续？"))
     	   {
             return false;
         }
     }
     if(document.all('DealDate').value!=document.all('OldDealDate').value)
     {
     	   if(!confirm("与上次约定的计价日不同，是否继续？"))
     	   {
             return false;
         }
     }
     if(document.all('DealDate').value!=document.all('OldDealDate').value || document.all('NextPriceDate').value!=document.all('OldNextPriceDate').value)
     {
     	   var tdate = document.all('NextPriceDate').value
     	   var tNextPriceDate = new Date(tdate.substr(0,4),tdate.substr(5,2)-1,tdate.substr(8,2));
     	   if(tNextPriceDate.getDay()==0 || tNextPriceDate.getDay()==6 )
     	   {
     	       if(!confirm("下个计价日为周六日，是否继续？"))
     	       {
                 return false;
             }
     	   }
     	 var mySql2=new SqlClass();
	     mySql2.setResourceName(sqlresourcename);
         mySql2.setSqlId("CheckPriceInfoInputInputSql6");
         mySql2.addSubPara(document.all('NextPriceDate').value);
         mySql2.addSubPara(document.all('DealDate').value);
         var brr = easyExecSql(mySql2.getString());
         var interval =brr[0][0];
         if(interval<=0)
         {
         	   alert("输入的下个计价日应该在本次计价日之后！")
         	   return false;
         }
         if(interval>1)
         {
    	        if(!confirm("下次计价日与本次计价日相隔大于一天，是否将下个计价日设为此日？"))
    	        {
                 return false;
                }
         }
     }
     return true;
}
function initPiceDate()
{
 	 var mySql=new SqlClass();
 	 mySql.setResourceName(sqlresourcename);
	 mySql.setSqlId("CheckPriceInfoInputInputSql10");
     var arr=easyExecSql(mySql.getString());
     if(arr!=null)
     {
        fm.DealDate.value=arr[0][0];
        fm.OldDealDate.value=arr[0][0];
        var tdate = arr[0][1];
     	  var tNextPriceDate = new Date(tdate.substr(0,4),tdate.substr(5,2)-1,tdate.substr(8,2));
     	  var interval=0;
     	  var taday = new Date();
     	  if(tNextPriceDate.getDay()==0)
     	  {
     	  	  interval = 1;
     	  }
     	  if(tNextPriceDate.getDay()==6)
     	  {
     	  	  interval = 2;
     	  }
     	  mySql.setSqlId("CheckPriceInfoInputInputSql11");
     	  mySql.addSubPara(tdate);
     	  mySql.addSubPara(interval);
     	  var brr=easyExecSql(mySql.getString());
     	  if(brr!=null)
     	  {
     	  	  fm.NextPriceDate.value = brr[0][0];
              fm.OldNextPriceDate.value = brr[0][0];
     	  }

     }
}