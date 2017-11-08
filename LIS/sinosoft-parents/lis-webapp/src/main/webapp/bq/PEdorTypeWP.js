//该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var selno; 
var mySql1=new SqlClass();

function edorTypePTReturn()
{
		initForm();
}
function getCustomerGrid()
{
    var tContNo=document.all("ContNo").value;
    if(tContNo!=null&&tContNo!="")
    {
		var strSQL = "";
		var sqlid1="PEdorTypeIPInputInputSql8";
	
		var sqlresourcename = "bq.PEdorTypeIPInputInputSql";  
		mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(tContNo);
		mySql1.addSubPara(tContNo);
		strSQL=mySql1.getString();
          
        arrResult = easyExecSql(strSQL);
        if (arrResult)
        {
            displayMultiline(arrResult,CustomerGrid);
        }
    }
}
/***********************************************************************
 *   对一个附加险种进行加保
 ***********************************************************************/
function addpolamnt(){
	  selno = LCInsuredGrid.getSelNo()-1;    
	  if (selno <0)
	  {
	      alert("请选择要加保的险种！");
	      return;
	  }
	  document.all("fmtransact").value = "INSERT||EDORAPPCONFIRM";
    if (window.confirm("是否理算本次保全申请?"))
    {

        var showStr="正在计算数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
        //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();

        fm.action= "./PEdorTypeAASubmit.jsp";
        fm.submit();
    }
}

function edorTypeMRSave()
{
    var nPayMoney = document.getElementsByName('AddPrem')[0].value;
    if(nPayMoney == null || nPayMoney == '')
    {
        alert("请输入追加金额！");
        return false;
     }
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
    fm.submit();
}

function customerQuery()
{	
	window.open("./LCAppntIndQuery.html");
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
  fm.submit(); //提交
}
function updatepol(){
	 mySql.setSqlId("PEdorTypeWPInputSql_1");
   mySql.addPara('tContNo',document.all("EdorNo").value);
	 var arrResult = easyExecSql(mySql.getSQL());
	 if(arrResult == null){
	 	  //alert("更新数据失败!");
	 	  return false;
	 }
	 LCInsuredGrid.setRowColData(selno, 6, arrResult[0][0]);
	 LCInsuredGrid.setRowColData(selno, 7, arrResult[0][1]);
	 LCInsuredGrid.setRowColData(selno, 8, arrResult[0][2]);
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
	try
    {
        top.close();
        top.opener.focus();
    }
    catch (ex) {}
}

function personQuery()
{
    //window.open("./LCPolQuery.html");
    window.open("./LPTypeIAPersonQuery.html");
}

function afterPersonQuery(arrResult)
{
    if (arrResult == null ||arrResult[0] == null || arrResult[0][0] == "" )
        return;

    //选择了一个投保人,显示详细细节
    document.all("QueryCustomerNo").value = arrResult[0][0];
    
    var strSql = "select * from ldperson where customerNo = " + arrResult[0][0];
    
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
    	alert("在PEdorTypeWP.js-->fillPersonDetail函数中发生异常:初始化界面错误!");
  	}      
}
function showNotePad()
{
	var MissionID = top.opener.document.all("MissionID").value;
	var SubMissionID = top.opener.document.all("SubMissionID").value;
	var ActivityID = '0000000003';
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
		mySql.setSqlId("PEdorTypeIPInputSql_2");
    mySql.addPara('tEdorType',tEdortype);
    }
    else
	{
		alert('未查询到保全批改项目信息！');
	}
	turnPage.strQueryResult  = easyQueryVer3(mySql.getSQL(), 1, 0, 1);	
	var arrSelected = new Array();	
	turnPage.strQueryResult  = easyQueryVer3(mySql.getSQL(), 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
    try {document.all('EdorType').value = arrSelected[0][0];} catch(ex) { }; //职业类别
    try {document.all('EdorTypeName').value = arrSelected[0][1];} catch(ex) { }; //职业类别
}

function Edorquery()
{
  var ButtonFlag = top.opener.document.all('ButtonFlag').value;
  if(ButtonFlag!=null && ButtonFlag=="1")
  {
     DivEdorSaveButton.style.display = "none";
     DivEdorCancelButton.style.display = "none";
  }
  else
  {
 	 DivEdorSaveButton.style.display = "";
 	 DivEdorCancelButton.style.display = "";
  }
}

function getAddFee()
{
mySql.setSqlId("PEdorTypeWPInputSql_3");
mySql.addPara('tEdorAcceptNo',top.opener.document.all('EdorAcceptNo').value);
    
var arrResult = easyExecSql(mySql.getSQL());
if (arrResult != null)
{
   fm.AddPrem.value = arrResult[0][0];

}
else
{
   fm.OccupationType.value = '';
}
}

/**
 *查询追加记录
 */
function queryNoteGrid()
{
    var EdorAppDate = document.getElementsByName('EdorItemAppDate')[0].value;
    var sContNo=document.getElementsByName('ContNo')[0].value;
    
	var sqlid1="PEdorTypeIPInputInputSql8";
	
	var sqlresourcename = "bq.PEdorTypeIPInputInputSql";  
	mySql.setResourceName(sqlresourcename); //指定使用的properties文件名
    mySql.setSqlId("PEdorTypeWPInputSql_4");
    mySql.addPara('tContNo',sContNo);
    mySql.addPara('tEdorAcceptNo',document.all('EdorAcceptNo').value);
    try
    {
        var arrResult = easyExecSql(mySql.getSQL());
        if (arrResult)
        {
            displayMultiline(arrResult,NoteGrid);
            //document.all("DivAddMoneyInfo").style.display = "none";
        }
    }catch( ex )
    {}
}

//删除追加记录
function deleteRecord()
{
    var sSelNo = NoteGrid.getSelNo() - 1;
    if (sSelNo < 0)
    {
       alert("请选择需要删除的纪录!");
       return;
    }
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
    fm.fmtransact.value = "EDORITEM|DELETE";
    fm.submit();
}

/**
 * 提交后操作, 服务器数据返回后执行的操作
 */
function afterSubmit(DealFlag, MsgContent)
{
    try { showInfo.close(); } catch (ex) {}
    DealFlag = DealFlag.toLowerCase();
    var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=";
    switch (DealFlag)
    {
        case "fail":
            MsgPageURL = MsgPageURL + "F&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=250px");
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
        case "succ":
        case "success":
            MsgPageURL = MsgPageURL + "S&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=350px");
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=350;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
        default:
            MsgPageURL = MsgPageURL + "C&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=300;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
    }
    //本文件的特殊处理
    if (DealFlag == "succ" || DealFlag == "success")
    {
        var sfmtransact = document.getElementsByName("fmtransact")[0].value;
        
        if (sfmtransact == "EDORITEM|DELETE" )
        {
            try
            {
               //document.all("DivAddMoneyInfo").style.display = "";
               document.getElementsByName("AddPrem")[0].value = '';
               NoteGrid.clearData();
               queryNoteGrid();
            }catch( ex ){}
        }else{
        	queryNoteGrid();
        }
        try
        {
            queryBackFee();
            top.opener.getEdorItemGrid();
            document.getElementsByName("fmtransact")[0].value = "";

        }
        catch (ex) {}

    }
}