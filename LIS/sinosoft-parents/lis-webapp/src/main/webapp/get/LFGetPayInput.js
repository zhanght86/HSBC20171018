//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="1";
var submitFlag="0";
var queryFlag="0";
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();

var tResourceName="get.LFGetPayInputSql";
var tResourceSQL1="LFGetPayInputSql1"; 
var tResourceSQL2="LFGetPayInputSql2"; 
var tResourceSQL3="LFGetPayInputSql3"; 
var tResourceSQL4="LFGetPayInputSql4"; 


//提交，保存按钮对应操作
function submitForm()
{                              
  if (!window.confirm("是否确认本次申请?"))
    return;         
    
  if(document.all('CommitFlag').value!="1")
  {                                  
    alert("上次请求未处理完毕,请耐心等待!");
    return;
  }
  document.all('CommitFlag').value = "0";
  var i = 0;
  var tSelNum = SubPayGrid.mulLineCount;
	var tFlag = false;
	//alert('aaa'+tSelNum);
	for (i=0;i<=tSelNum-1;i++)
	{
		if (SubPayGrid.getChkNo(i))
		{
			tFlag = true;
		}
	}	
	if (tFlag)
	{
		submitFlag="1";
	  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	  //showSubmitFrame(mDebug);
	  fm.action="./LFGetPaySave.jsp"
	  fm.submit(); //提交
	}
	else
	{
		alert("请选择给付信息!");
		return;
	}
}

function easyQueryClick()
{
  // 初始化表格
   
  if(fm.GrpContNo.value == null ||trim(fm.GrpContNo.value) =="")
  {
  	alert("请录入团体保单号");
  	return;
  }
  divLCGet.style.display ='';
	initLJSGetGrid(); 
  // 书写SQL语句
 // var strSQL = "";	
 // strSQL = "select ljsget.getnoticeno,ljsget.otherno,LCCont.GrpContNo,LCCont.insuredname,ljsget.sumgetmoney,ljsget.getdate,ljsget.makedate,ljsget.operator "
 //        + "from ljsget,LCCont "
 //        + "where ljsget.otherno=LCCont.ContNo and ljsget.othernotype='2' and ljsget.managecom like '"+manageCom+"%%' and LCCont.GrpContNo!='00000000000000000000'"
 //        + getWherePart( 'LCCont.GrpContNo','GrpContNo' )
 //        + " order by ljsget.getnoticeno";
         
	//strSQL = "select LCCont.GrpContNo,ljsget.otherno,LCCont.insuredname,sum(ljsget.sumgetmoney),ljsget.operator "
  //       + "from ljsget,LCCont "
  //       + "where ljsget.otherno=LCCont.ContNo and ljsget.othernotype='2' and ljsget.getdate <= sysdate and ljsget.managecom like '"+manageCom+"%%' and LCCont.GrpContNo!='00000000000000000000'"
  //       + getWherePart( 'LCCont.GrpContNo','GrpContNo' )
  //       + " group by LCCont.GrpContNo,ljsget.otherno,LCCont.insuredname,ljsget.operator order by ljsget.otherno";
  
  
  strSQL = wrapSql(tResourceName,tResourceSQL1,[fm.manageCom.value,fm.GrpContNo.value]);       
  
  
  //查询SQL，返回结果字符串
  turnPage2.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  

  //判断是否查询成功
  if (!turnPage2.strQueryResult) {
    alert("查询失败,没有符合条件的数据!");
    divLCGet.style.display ='none';
    divDiskApp.style.display ='none';
    return false;
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage2.arrDataCacheSet = decodeEasyQueryResult(turnPage2.strQueryResult);
  
  //设置初始化过的MULTILINE对象
  turnPage2.pageDisplayGrid = LJSGetGrid;    
          
  //保存SQL语句
  turnPage2.strQuerySql = strSQL; 
  
  //设置查询起始位置
  turnPage2.pageIndex = 0;  

  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet = turnPage2.getData(turnPage2.arrDataCacheSet, turnPage2.pageIndex, MAXSCREENLINES);
  arrGrid = arrDataSet;
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage2.pageDisplayGrid);
 divGetDrawInfo.style.display='none';
  fm.PayMode.value = "";
	fm.PayModeName.value = "";
	fm.Drawer.value = "";
	fm.DrawerIDNo.value = "";
	fm.BankCode.value = "";
	fm.BankName.value = "";
	fm.BankAccNo.value = "";
	fm.AccName.value = "";
	divGetInfo.style.display="";
	var rowNum= LJSGetGrid.mulLineCount;
	if(rowNum<1){
		alert("暂无可领取信息");
		fm.GrpContNoBak.value="";
		divLCGet.style.display ='none';
	}else{
		fm.GrpContNoBak.value=fm.GrpContNo.value;
		divLCGet.style.display ='';
	}
}

function GetNotice()
{
  
   
   var tResult = document.all('PrtSeq').value;
   if(tResult == null || tResult == ""){
       alert("查询领取清单信息失败！");
       return;
   }
   //alert(document.all('PrtSeq').value);
   fm.action = "../bq/EdorNoticePrintSave.jsp";
   fm.target = "f1print";
   fm.submit();
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content,PrtSeq )
{
  showInfo.close();
  document.all('CommitFlag').value = "1";
  if (FlagStr == "Fail")
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
	   	//initForm();

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
		
    //执行下一步操作
    if(fm.fmtransact.value=="DISKINPUT")
    {
    	fm.GrpContNo.value=fm.GrpContNoBak.value;
    	easyQueryClick();
    }
    if(fm.fmtransact.value=="GETMONEY")
    {
    	divLCGet.style.display='none';
    	divGetDrawInfo.style.display='none';
    	divGetInfo.style.display='none';
    	divGetNotice.style.display = '';
    	//alert(PrtSeq);
    	document.all('PrtSeq').value = PrtSeq;
    }
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
  	alert("在LJSGetDraw.js-->resetForm函数中发生异常:初始化界面错误!");
  }
} 

//取消按钮对应操作
function cancelForm()
{
//  window.location="../common/html/Blank.html";
    //showDiv(operateButton,"true"); 
    //showDiv(inputButton,"false"); 
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
  //showDiv(operateButton,"false"); 
  //showDiv(inputButton,"true"); 
}           

//Click事件，当点击“修改”图片时触发该函数
function updateClick()
{
  //下面增加相应的代码
  alert("update click");
}           

//Click事件，当点击“查询”图片时触发该函数
function queryClick()
{
  //下面增加相应的代码
  var tNoticeNo = document.all('bmtz').value;
  var tPolNo = document.all('bmcert').value;
  if ((tNoticeNo==null||tNoticeNo=='')&&(tPolNo==null||tPolNo==''))
  {
  	alert("请录入保单号或通知书号查询!");
  	return;
  }	
	submitFlag="0";
  var i = 0;
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
 // showSubmitFrame(mDebug);
  fm.action="./LFGetPayQueryOut.jsp"
  fm.submit(); //提交
 }           

//Click事件，当点击“删除”图片时触发该函数
function deleteClick()
{
  //下面增加相应的代码
  alert("delete click");
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

//计算合计金额
function CalSumMoney(parm1,parm2)
{
	var tSelNum = SubPayGrid.mulLineCount;
	var i;
	var aGetMoney=0;
	//alert('aaa'+tSelNum);
	for (i=0;i<=tSelNum-1;i++)
	{
		if (SubPayGrid.getChkNo(i))
		{
			var tMoney =0;
			if (SubPayGrid.getRowColData(i,7)!=null&&SubPayGrid.getRowColData(i,7)!='')
				tMoney = parseFloat(SubPayGrid.getRowColData(i,7));
			aGetMoney = aGetMoney + tMoney;
				
		}
	}
	aGetMoney=Math.round(aGetMoney*100)/100;
	document.all('SumGetMoney').value = aGetMoney;
		
}

function reportDetailClick()
{
	divGetDrawInfo.style.display='';
	var tSelNo = LJSGetGrid.getSelNo()-1;
	var tGrpContNo = LJSGetGrid.getRowColData(tSelNo,1); 
	var tContNo = LJSGetGrid.getRowColData(tSelNo,2); 
	var strSQL = "";	
  //strSQL = "select a.getnoticeno,b.contno,b.insuredname,(select dutyname from lmduty where dutycode = a.dutycode),"
  //			 + " decode(a.getdutykind,'1','一次性领取养老金','2','年领定额养老金','3','月领定额养老金','4','年领十年固定定额养老金','5','月领十年固定定额养老金','6','年领算术增额型养老金','7','月领算术增额型养老金','8','年领几何增额型养老金','9','月领几何增额型养老金','其它'),"
  //			 + " a.LastGettoDate,a.CurGetToDate,a.GetMoney "
  //       + "from ljsgetdraw a ,LCCont b "
  //       + "where a.ContNo=b.ContNo and a.managecom like '"+manageCom+"%' and b.GrpContNo='"+tGrpContNo+"'"
	//			 + " and a.ContNo = '"+tContNo+"' and a.GetDate <=sysdate "
  //       + " order by a.getnoticeno";
  
  strSQL = wrapSql(tResourceName,tResourceSQL2,[fm.ManageCom.value,tGrpContNo,tContNo]); 
	
        
  turnPage1.queryModal(strSQL, SubPayGrid);
  fm.ContNo.value=tContNo;
  divGetDrawInfo.style.display='';
  
  //strSQL = "select distinct paymode,decode(paymode,'1','现金','4','银行转帐','其它'),drawer,drawerid,BankCode,(SELECT BankName FROM LDBank WHERE BankCode = LjsGet.BankCode),BankAccNo,AccName "
  //			 + " from ljsget where getdate <= sysdate and othernotype = '2' and otherno = '"+tContNo+"'";
  
  strSQL = wrapSql(tResourceName,tResourceSQL3,[tContNo]);   
  
  var tArr = easyExecSql(strSQL, 1, 0, 1);
	
	if (tArr != null)
	{
		 divGetInfo.style.display="";
		 try
  	 {
		 		fm.PayMode.value = tArr[0][0];
		 		fm.PayModeName.value = tArr[0][1];
		 		fm.Drawer.value = tArr[0][2];
		 		fm.DrawerIDNo.value = tArr[0][3];
		 		fm.BankCode.value = tArr[0][4];
		 		fm.BankName.value = tArr[0][5];
		 		fm.BankAccNo.value = tArr[0][6];
		 		fm.AccName.value = tArr[0][7];
		 }  catch(ex){}
  }
} 
  
function updateSubAcc()
{
		var tSelNo = LJSGetGrid.getSelNo()-1;
		//alert(tSelNo);
    if (tSelNo < 0)
    {
        alert("请选择需要修改的给付记录！");
        return;
    }
    
		fm.ContNo.value = LJSGetGrid.getRowColData(tSelNo, 2);
		//fm.InsuredNo.value = SubAccGrid.getRowColData(tSelNo, 2);
		var tPayMode = fm.PayMode.value;
		var tDrawer = fm.Drawer.value;
		var tDrawerIDNo = fm.DrawerIDNo.value;
		if(tPayMode == null || trim(tPayMode) == "" || (tPayMode != "1" && tPayMode != "4"))
		{
			alert("请选择或录入正确的领取方式!");
			return
		}
		if(tDrawer == null || trim(tDrawer) == "")
		{
			alert("请录入领取人!");
			return
		}
		if(tDrawerIDNo == null || trim(tDrawerIDNo) == "")
		{
			alert("请录入领取人身份证号!");
			return
		}
		if(!checkIdCard(tDrawerIDNo))
		{
			return;
		}
		if(tPayMode == "4" || tPayMode=="7")
		{
			//银行转账
			var tBankCode = fm.BankCode.value;
			var tBankAccNo = fm.BankAccNo.value;
			var tAccName = fm.AccName.value;
			if(tBankCode == null || trim(tBankCode) == "")
			{
				alert("请选择开户银行!");
				return
			}
			if(tBankAccNo == null || trim(tBankAccNo) == "")
			{
				alert("请录入银行账户!");
				return
			}
			if(tAccName == null || trim(tAccName) == "")
			{
				alert("请录入账户名!");
				return
			}
		}else {
				fm.BankCode.value == "";
				fm.BankAccNo.value == "";
				fm.AccName.value == "";
		}
		fm.fmtransact.value = "UPDATEPAYMODE";
		var showStr = "正在保存，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action = "LFGetPaySave.jsp";
    fm.submit();
}

function confirmGet()
{
	//alert(fm.GrpContNoBak.value);
	//先校验是否全部领取信息都录入完整
	
	if(trim(fm.GrpContNoBak.value)=="")
	{
		alert("请录入团体保单号");
		return;
	}
	if(trim(fm.LFAppName.value)=="")
	{
		alert("请添写申请人姓名");
		return;
	}
	fm.fmtransact.value = "GETMONEY";
	var showStr = "正在保存，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action = "LFGetPaySave.jsp";
    fm.submit();
}  
 
function diskInput()
{
	
	if ( fm.GrpContNoBak.value == "")
	{
		alert("请先查询保单信息");
		return ;
	}

	  var tGrpContNo = fm.GrpContNoBak.value;


		var tFileName=fm2.all('FileName').value;
		//alert(tFileName);
		if ( tFileName.indexOf("\\")>0 ) tFileName=tFileName.substring(tFileName.lastIndexOf("\\")+1);
		if ( tFileName.indexOf("/")>0 ) tFileName=tFileName.substring(tFileName.lastIndexOf("/")+1);
		if ( tFileName.indexOf("_")>0) tFileName=tFileName.substring( 0,tFileName.indexOf("_"));
		if ( tFileName.indexOf(".")>0) tFileName=tFileName.substring( 0,tFileName.indexOf("."));
		if(tFileName != tGrpContNo)
		{
			alert("文件名与团体保单号不一致,请检查文件名!");
			return ;
		}
   //alert("成功导入");	
    //document.all("Transact").value = "IMPORT||EDOR"

    fm.fmtransact.value = "DISKINPUT";
    var showStr = "正在保存，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm2.action = "./LFGetPayLoad.jsp?LGrpContNo="+tGrpContNo+"";
    fm2.submit();
} 
  