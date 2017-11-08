//该文件中包含客户端需要处理的函数和事件 

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
var sqlresourcename = "bq.PEdorTypePUInputSql";

function edorTypePTReturn()
{
		initForm();
}

function edorTypePTSave()
{ 
  if (document.all('CalMode').value == 'O') {
  	var nRemainMulti = parseFloat(document.all('RemainMulti').value);
  	var nMulti = parseFloat(document.all('Multi').value);
  	if (nRemainMulti > nMulti) {
  		alert("份数必须小于原份数。");
  		return;
  	}
  	if (nRemainMulti <= 0 ) {
  		alert("余留份数必须大于0。");
  		return;
    }  		
  } else {
    var nRemainAmnt = parseInt(document.all('RemainAmnt').value);
    var nAmnt = parseInt(document.all('Amnt').value);
  	//if (nRemainAmnt > nAmnt) {
  	//	alert("保额余数必须小于原保额。");
  	//	return;
  	//}
  	if (nRemainAmnt <= 0) {
  		alert("保额余数必须大于0。");
  		return;
  	}  	
  }  
  
  //团险保全复用，第一次进入时提供录入功能
	if (typeof(top.opener.GrpBQ)=="boolean" && top.opener.GrpBQ==false) {
	  //接收录入数据
	  top.opener.GTArr.push(fm.RemainAmnt.value);

	  
	  top.opener.GrpBQ = true;
	  top.opener.pEdorMultiDetail();
	  top.close();
	} 
	//**************************************
	else {
    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    
    document.all('fmtransact').value = "INSERT||MAIN";  	  
    
    fm.submit();
  }

}

function customerQuery()
{	
//	window.open("./LCAppntIndQuery.html");
	window.open("./LCAppntIndQuery.html","",sFeatures);
}

//提交，保存按钮对应操作
function submitForm()
{
  var i = 0;
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	
  initLCAppntIndGrid();
 //  showSubmitFrame(mDebug);
  fm.submit(); //提交
}


////提交后操作,服务器数据返回后执行的操作
//function afterSubmit( FlagStr, content,Result )
//{
//  showInfo.close();
//  if (FlagStr == "Fail" )
//  {             
//    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
//  }
//  else
//  { 
//  	var tTransact=document.all('fmtransact').value;
////  	alert(tTransact);
//		if (tTransact=="QUERY||MAIN")
//		{
//			var iArray;
//			//清空数据容器，两个不同查询共用一个turnPage对象时必须使用，最好加上，容错
//  		    turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
//			//保存查询结果字符串
// 		 	turnPage.strQueryResult  = Result;
//    
// 	 		//查询成功则拆分字符串，返回二维数组
//	  		var tArr   = decodeEasyQueryResult(turnPage.strQueryResult,0);
////	  		alert(tArr[0]);
////	  		alert(tArr[0].length);
//	  		document.all('Amnt').value = tArr[0][45];
//	  		document.all('Prem').value = tArr[0][42];
//	  		document.all('AppntNo').value = tArr[0][28];
//	  		document.all('AppntName').value = tArr[0][29];
//	  		document.all('Multi').value = tArr[0][40]; 		
//	  		document.all('CalMode').value = tArr[0][134];	  		
//
//	  		//calMode是付加在保单的字符串传过来的，并放在了最后
//	  		var calMode = tArr[0][134];	 
//	  		//alert(tArr[0][tArr[0].length-2]); 		
//
//	  		if (calMode == 'P') {	  		
//	  			var urlStr="../common/jsp/MessagePage.jsp?picture=S&content= 此保单无法进行部分退保计算" ;  
//			    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
//	   			initForm();
//	   			return;
//  			} else if (calMode == 'O') {   //利用分数变化进行计算
//  			
//  			    //document.all('RemainMulti').value = tArr[0][132];
//  				RemainAmntDiv.style.display = "none";
//  				RemainMultiDiv.style.display = "";  				
//  			} else {                      //利用保额变化进行计算
//  				
//  				//document.all('RemainAmnt').value = tArr[0][132];
//  				RemainMultiDiv.style.display = 'none';	
//  				RemainAmntDiv.style.display = '';					
//  			}
//			
//				//团险保全复用，自动填写数值，并提交
//	    	if (typeof(top.opener.GrpBQ)=="boolean" && top.opener.GrpBQ==true) {
//	    	  fm.RemainAmnt.value = top.opener.GTArr.pop();
//	    	  
//	    	  edorTypePTSave();
//	     	}
//     		//***********************************
//
//		  }
//		  else {
//      
//      //团险保全复用，提交成功后，再次调用，以循环
//   		if (typeof(top.opener.GrpBQ)=="boolean" && top.opener.GrpBQ==true) {
//			  top.opener.PEdorDetail();
//			  top.close();
//			}
//			else {
//			  var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
//    	  showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
//   		  initForm();
//   		}
//  	}
//  }
//}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  try { showInfo.close(); } catch (e) {}

  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
}

//提交前的校验、计算  
function beforeSubmit()
{
  //添加操作	
}           
       
//---------------------------
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

/*********************************************************************
 *  显示frmSubmit框架，用来调试
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showSubmitFrame(cDebug)
{
	if( cDebug == "1" )
		parent.fraMain.rows = "0,0,50,82,*";
	else 
		parent.fraMain.rows = "0,0,0,72,*";
}

/*********************************************************************
 *  查询返回明细信息时执行的函数,查询返回一个2维数组，数组下标从[0][0]开始
 *  参数  ：  查询返回的二维数组
 *  返回值：  无
 *********************************************************************
 */

function afterQuery( arrQueryResult )
{

}

function returnParent()
{
 top.opener.initEdorItemGrid();
 top.opener.getEdorItemGrid();
 top.close();
 top.opener.focus();
}

function personQuery()
{
    //window.open("./LCPolQuery.html");
//    window.open("./LPTypeIAPersonQuery.html");
    window.open("./LPTypeIAPersonQuery.html","",sFeatures);
}

function afterPersonQuery(arrResult)
{
    if (arrResult == null ||arrResult[0] == null || arrResult[0][0] == "" )
        return;

    //选择了一个投保人,显示详细细节
    document.all("QueryCustomerNo").value = arrResult[0][0];
    
//    var strSql = "select * from ldperson where customerNo = " + arrResult[0][0];
    
    var strSql = "";
	var sqlid1="PEdorTypePUInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(arrResult[0][0]);//指定传入的参数
	strSql=mySql1.getString();
    
	//查询SQL，返回结果字符串
    turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);  

 //   alert(turnPage.strQueryResult);
    //判断是否查询成功
    if (!turnPage.strQueryResult) {  
    	//清空MULTILINE，使用方法见MULTILINE使用说明 
      	VarGrid.clearData('VarGrid');  
      	alert("查询失败！");
      	return false;
  	}

  
 	 //清空数据容器，两个不同查询共用一个turnPage对象时必须使用，最好加上，容错
  	turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
  
    //查询成功则拆分字符串，返回二维数组
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
    fillPersonDetail();
    
  divLPAppntIndDetail.style.display = "";
    
    	
	
}

function fillPersonDetail()
{
	try {
		document.all("AppntCustomerNo").value = turnPage.arrDataCacheSet[0][0];
		document.all("AppntName").value = turnPage.arrDataCacheSet[0][2];
		document.all("AppntSex").value = turnPage.arrDataCacheSet[0][3];
		document.all("AppntBirthday").value = turnPage.arrDataCacheSet[0][4];
		
		document.all("AppntIDType").value = turnPage.arrDataCacheSet[0][16];
		document.all("AppntIDNo").value = turnPage.arrDataCacheSet[0][18];
		document.all("AppntNativePlace").value = turnPage.arrDataCacheSet[0][5];
		
		document.all("AppntPostalAddress").value = turnPage.arrDataCacheSet[0][24];
		document.all("AppntZipCode").value = turnPage.arrDataCacheSet[0][25];
		document.all("AppntHomeAddress").value = turnPage.arrDataCacheSet[0][23];
		document.all("AppntHomeZipCode").value = turnPage.arrDataCacheSet[0][22];
		
		document.all("AppntPhone").value = turnPage.arrDataCacheSet[0][26];
		document.all("AppntPhone2").value = turnPage.arrDataCacheSet[0][56];
		document.all("AppntMobile").value = turnPage.arrDataCacheSet[0][28];
		document.all("AppntEMail").value = turnPage.arrDataCacheSet[0][29];
		document.all("AppntGrpName").value = turnPage.arrDataCacheSet[0][38];
		
		document.all("AppntWorkType").value = turnPage.arrDataCacheSet[0][48];
		document.all("AppntPluralityType").value = turnPage.arrDataCacheSet[0][49];
		document.all("AppntOccupationCode").value = turnPage.arrDataCacheSet[0][50];
		document.all("AppntOccupationType").value = turnPage.arrDataCacheSet[0][9];
    } catch(ex) {
    	alert("在PEdorTypePT.js-->fillPersonDetail函数中发生异常:初始化界面错误!");
  	}      
}
function edorTypePUSave()
{
	if (document.all('PageIsFormating').value == "Y")
	{
		alert("界面正在刷新，请稍等！");
		return;
	}
	
	//界面校验
	if (!verifyInput2())
	{
		return false;
	}
	
	if (document.all('IninSuccessFlag').value != "1")
	{
		if (document.all('IninSuccessFlag').value == "0")
		{
			alert("页面初始化未完成，不能保存！");
		}
		if (document.all('IninSuccessFlag').value == "2")
		{
			alert("减额缴清计算失败，不能保存！");
		}
		return;
	}
	
	document.all('fmtransact').value ="INSERT||MAIN";  
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面！";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");  
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.action="./PEdorTypePUSubmit.jsp"
	fm.submit();
}
function showNotePad()
{
	var MissionID = top.opener.document.all("MissionID").value;
	var SubMissionID = top.opener.document.all("SubMissionID").value;
	var ActivityID = "0000000003";
	var OtherNo = top.opener.document.all("OtherNo").value;

	var OtherNoType = "1";
	if(MissionID == null || MissionID == "")
	{
		alert("MissionID为空！");
		return;
	}		
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface= ../uw/WorkFlowNotePadInput.jsp" + varSrc,"工作流记事本查看","left");	
}
function QueryEdorInfo()
{
	var tEdortype=top.opener.document.all('EdorType').value;
	if(tEdortype!=null || tEdortype !='')
	{
//	var strSQL="select distinct edorcode, edorname from lmedoritem where edorcode = '" + tEdortype + "'";
    
    var strSQL = "";
	var sqlid2="PEdorTypePUInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	mySql2.addSubPara(tEdortype);//指定传入的参数
	strSQL=mySql2.getString();
    
    }
    else
	{
		alert('未查询到保全批改项目信息！');
	}
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);	
	var arrSelected = new Array();	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
    try {document.all('EdorType').value = arrSelected[0][0];} catch(ex) { }; //职业类别
    try {document.all('EdorTypeName').value = arrSelected[0][1];} catch(ex) { }; //职业类别
}

function initGetDutyCode(cObjCode,cObjName)
{
    condition = " 1 and RiskCode = #00145000#" ;
    showCodeList('getdutykind',[cObjCode,cObjName],[0,1], null, condition, "1");
}

function keyUpGetDutyCode(cObjCode,cObjName)
{
    condition = " 1 and RiskCode = #00145000#" ;
    showCodeListKey('getdutykind',[cObjCode,cObjName],[0,1], null, condition, "1");
}

function initInsuYear(cObjCode,cObjName)
{
		if (document.all('RiskCode').value == "00607000" || document.all('RiskCode').value == "00627000")
		{
      condition = " 1 and RiskCode = #00147000#" ;
    }
    else
  	{
  		condition = " 1 and RiskCode = #00147000# and ParamsCode>=10" ;
  	}
    showCodeList('insuyear',[cObjCode,cObjName],[0,1], null, condition, "1");
}

function keyUpInsuYear(cObjCode,cObjName)
{
		if (document.all('RiskCode').value == "00607000" || document.all('RiskCode').value == "00627000")
		{
      condition = " 1 and RiskCode = #00147000#" ;
    }
    else
  	{
  		condition = " 1 and RiskCode = #00147000# and ParamsCode>=10" ;
  	}
    showCodeListKey('insuyear',[cObjCode,cObjName],[0,1], null, condition, "1");
}

function afterCodeSelect(cCodeName, Field)
{
	try 
	{
		document.all('PageIsFormating').value = "Y";
		queryNewData();
	} 
	catch(ex) 
	{
		alert("在afterCodeSelect中发生异常！");
	}
}

function Edorquery()
{
	var ButtonFlag = top.opener.document.all('ButtonFlag').value;
	if(ButtonFlag!=null && ButtonFlag=="1")
    {
       divEdorquery.style.display = "none";
    }
    else
 	{
 		divEdorquery.style.display = "";
 	}
}


function queryNewData()
{
	try
	{
		if (document.all('SpecialRiskFlag').value == "Y")
		{
			if (document.all('InsuYear').value == null || document.all('InsuYear').value == "")
			{
				if (document.all('GetDutyKind').value == null || document.all('GetDutyKind').value == "")
				{
					//return;
					//证明是特殊险种的界面初始化查询，告诉后台只查询险种信息
					document.all('SpecialRiskInitFlag').value = "1";
				}
			}
		}
		
    var showStr="正在进行计算，请您稍候并且不要修改屏幕上的值或链接其他页面！";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");  
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

		fm.action="./PEdorTypePUCount.jsp";
		fm.submit();
  }
  catch(ex)
  {
    alert(ex);
  }
}

//查询后初始化MulLine
function afterCount(tFlagStr,tContent)
{
	try { showInfo.close(); } catch (e) {}
		//初始化MulLine
		if (tFlagStr == "Success")
		{
			document.all('IninSuccessFlag').value = "1";
			initPolGrid();
			displayPolGrid();
			if (PolGrid.mulLineCount > 1) //多于一行才显示
			{
				divMultiPol.style.display = ''; 
			}
		    else
		    {
		    	divMultiPol.style.display = 'none'; 
		    }
		    		
			getPolInfo(0); //默认显示第一行（主险）
    	}
	    else  //计算失败
	    {
	    	divMultiPol.style.display = 'none'; 
	    	document.all('tatolMoney').value = "";
	    	document.all('NewRiskCode').value = "";
	    	document.all('NewRiskName').value = "";
	    	document.all('NewAmnt').value = "";
	    	document.all('FinaleBonus').value = "";
	    	document.all('AddUpBonus').value = "";
	    	document.all('IninSuccessFlag').value = "2";

		    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + tContent ;  
		    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:300px");   
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
    	}
    document.all('PageIsFormating').value = "";
    document.all('SpecialRiskInitFlag').value = "";
}

function displayPolGrid()
{

	if (fm.StrPolGrid.value == null || fm.StrPolGrid.value.trim == "null")
	{
		return;
	}
	turnPage.strQueryResult = fm.StrPolGrid.value;
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    //设置初始化过的MULTILINE对象
    turnPage.pageDisplayGrid = PolGrid;    
    turnPage.pageIndex = 0;  
    //在查询结果数组中取出符合页面显示大小设置的数组
    arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
    //调用MULTILINE对象显示查询结果
    displayMultiline(arrDataSet, turnPage.pageDisplayGrid);	
}

function getSelNo()
{
    var tSelNo = PolGrid.getSelNo()-1;
    getPolInfo(tSelNo);
}

function getPolInfo(tSelNo)
{
	document.all('RiskCode').value = PolGrid.getRowColData(tSelNo, 2);
	document.all('RiskName').value = PolGrid.getRowColData(tSelNo, 3);
	
	document.all('PaytoDate').value = PolGrid.getRowColData(tSelNo, 10);
	document.all('Prem').value = PolGrid.getRowColData(tSelNo, 11);
	document.all('Amnt').value = PolGrid.getRowColData(tSelNo, 12);
	document.all('Mult').value = PolGrid.getRowColData(tSelNo, 13);
	
	document.all('AddUpBonus').value = PolGrid.getRowColData(tSelNo, 6);
	document.all('FinaleBonus').value = PolGrid.getRowColData(tSelNo, 7);
	document.all('tatolMoney').value = PolGrid.getRowColData(tSelNo, 8);
	
	if (document.all('SpecialRiskInitFlag').value != "1")//不是特殊险种的可以直接计算出结果
	{
		document.all('NewRiskCode').value = PolGrid.getRowColData(tSelNo, 4);
		document.all('NewRiskName').value = PolGrid.getRowColData(tSelNo, 5);
		document.all('NewAmnt').value = PolGrid.getRowColData(tSelNo, 9);
	}
    		
}