//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var theFirstValue="";
var theSecondValue="";
function edorTypeAGReturn()
{
	top.close();
}
function edorTypeWTQuery()
{
	alert("Wait...");
}
function edorTypeAGSave()
{
	//alert('尚未连接到后台程序......');
	if (PolGrid.mulLineCount==0)
	{
		alert('目前没有领取项目！');
		return;
	}
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
  document.all('fmtransact').value = "INSERT||MAIN";

 	//showSubmitFrame(mDebug);
  fm.submit();

}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content,Result )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {
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
    //刷新页面
    window.location.reload();
  }
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
  	alert("在Proposal.js-->resetForm函数中发生异常:初始化界面错误!");
  }
}

//取消按钮对应操作
function cancelForm()
{
    showDiv(operateButton,"true");
    showDiv(inputButton,"false");
}

//提交前的校验、计算
function beforeSubmit()
{
  //添加操作
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
  alert("update click");
}

//Click事件，当点击“查询”图片时触发该函数
function queryClick()
{
  //下面增加相应的代码
	alert("query click");
	  //查询命令单独弹出一个模态对话框，并提交，和其它命令是不同的
  //因此，表单中的活动名称也可以不用赋值的
}

//Click事件，当点击“删除”图片时触发该函数
function deleteClick()
{
  //下面增加相应的代码
  alert("delete click");
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
function showBankDiv()
{
	if(document.all('GoonGetMethod').value=='0')
	{
		BankInfo.style.display = "";
		//document.all('Bank').value="";
		//document.all('Account').value="";
		//document.all('AccName').value="";
	}
	if(document.all('GoonGetMethod').value=='1' || document.all('GoonGetMethod').value=='2' )
	{
		BankInfo.style.display = "none";

	}
}

//验证文本框中两次输入的值是否相等
function confirmSecondInput1(aObject,aEvent){
	if(aEvent=="onkeyup"){
	  var theKey = window.event.keyCode;
	  if(theKey=="13"){
	    if(theFirstValue!=""){
      	theSecondValue = aObject.value;
	      if(theSecondValue==""){
	      	alert("请再次录入！");
	        aObject.value="";
	        aObject.focus();
	        return;
	      }
	      if(theSecondValue==theFirstValue){
	        aObject.value = theSecondValue;
	        return;
	      }
	      else{
	        alert("两次录入结果不符，请重新录入！");
	        theFirstValue="";
	        theSecondValue="";
	        aObject.value="";
	        aObject.focus();
	        return;
	      }
	    }
      else{
      	theFirstValue = aObject.value;
      	if(theFirstValue==""){
      		theSecondValue="";
      	  alert("请录入内容！");
      	  return;
      	}
      	aObject.value="";
      	aObject.focus();
        return;
      }
    }
  }
  else if(aEvent=="onblur"){
	  //alert("theFirstValue="+theFirstValue);
	  if(theFirstValue!=""){
    	theSecondValue = aObject.value;
	    if(theSecondValue==""){
	    	alert("请再次录入！");
	      aObject.value="";
	      aObject.focus();
	      return;
	    }
	    if(theSecondValue==theFirstValue){
	      aObject.value = theSecondValue;
	      theSecondValue="";
	      theFirstValue="";
	      return;
	    }
	    else{
	      alert("两次录入结果不符，请重新录入！");
	      theFirstValue="";
	      theSecondValue="";
	      aObject.value="";
	      aObject.focus();
	      return;
	    }
	  }
    else{
    	theFirstValue = aObject.value;
     	theSecondValue="";
    	if(theFirstValue==""){
      	//alert("aa");
    	  return;
    	}
    	aObject.value="";
    	aObject.focus();
      return;
    }
  }
}
function showNotePad()
{
	var MissionID = top.opener.document.all("MissionID").value;
	var SubMissionID = top.opener.document.all("SubMissionID").value;;
	var ActivityID = "0000000003";
	var OtherNo = top.opener.document.all("OtherNo").value;;

	var OtherNoType = "1";
	if(MissionID == null || MissionID == "")
	{
		alert("MissionID为空！");
		return;
	}
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface= ../uw/WorkFlowNotePadInput.jsp" + varSrc,"工作流记事本查看","left");
}
function returnParent()
{
 //top.opener.initEdorItemGrid();
 //top.opener.getEdorItemGrid();
 top.close();
}

//<!-- XinYQ modified on 2005-11-08 : BGN -->
/*============================================================================*/

/*
 * 续期领取形式-银行转帐-银行账户信息查询
 */
function queryBankInfo()
{
    var QuerySQL, arrResult;
   /* QuerySQL = "select GetForm, GetBankCode, GetBankAccNo, GetAccName "
             +   "from LCPol "
             +  "where 1 = 1 "
             +  getWherePart("ContNo", "ContNo")
             +  getWherePart("PolNo", "PolNo");
    */
    
    var sqlid1 = "PEdorTypeAGSql1";
  	var mySql1 = new SqlClass();
  	mySql1.setResourceName("bqs.PEdorTypeAGSql"); // 指定使用的properties文件名
  	mySql1.setSqlId(sqlid1);// 指定使用的Sql的id
  	mySql1.addSubPara(window.document.getElementsByName(trim('ContNo'))[0].value);// 指定传入的参数
  	mySql1.addSubPara(window.document.getElementsByName(trim('PolNo'))[0].value);// 指定传入的参数
    QuerySQL = mySql1.getString();   
    try
    {
        arrResult = easyExecSql(QuerySQL, 1, 0);
    }
    catch (ex)
    {
        alert("警告：查询客户银行账户信息出现异常！ ");
        return;
    }
    if (arrResult != null)
    {
        try
        {
            //领取形式
            document.getElementsByName("GoonGetMethod")[0].value = arrResult[0][0];
            showOneCodeName('GetLocation','GoonGetMethod','GoonGetMethodName');
            //开户银行
            document.getElementsByName("GetBankCode")[0].value = arrResult[0][1];
            showOneCodeName('Bank', 'GetBankCode', 'BankName');
            //银行帐户
            document.getElementsByName("GetBankAccNo")[0].value = arrResult[0][2];
            //帐户名
            if (trim(arrResult[0][3]) != "")
                document.getElementsByName("GetAccName")[0].value = arrResult[0][3];
            else
                document.getElementsByName("GetAccName")[0].value = document.getElementsByName("InsuredName")[0].value;
        }
        catch (ex)
        {
            alert("错误：查询客户银行账户信息成功, 但显示到页面失败！ ");
            return;
        }
    }
    //层的显示
    if (arrResult != null && trim(arrResult[0][0]) == "0")
        try { document.all("BankInfo").style.display = ""; } catch (ex) {}
    else
        try { document.all("BankInfo").style.display = "none"; } catch (ex) {}
}

/*============================================================================*/

//获得领取方式，如果是银行则将帐户信息带出
//function formatGetModeAbout()
//{
//	var tSql="SELECT GetForm,GetBankCode,GetBankAccNo,GetAccName"
//				+ " FROM ((select '1' as flag,GetForm,GetBankCode,GetBankAccNo,GetAccName"
//		 					 + " from LPPol"
//		 					 + " where EdorNo='" + document.all('EdorNo').value + "' and EdorType='" + document.all('EdorType').value + "' and PolNo='" + document.all('PolNo').value + "')"
//		 					+ " union"
//							+ " (select '2' as flag,GetForm,GetBankCode,GetBankAccNo,GetAccName"
//		 					 + " from LCPol"
//		 					 + " where PolNo='" + document.all('PolNo').value + "'))"
//				+ " WHERE rownum=1"
//				+ " ORDER BY flag";
//	//alert(tSql);
//	var arrResult=easyExecSql(tSql,1,0);
//	try{document.all('GoonGetMethod').value= arrResult[0][0];}catch(ex){};
//	showOneCodeName('getlocation','GoonGetMethod','GoonGetMethodName');
//	try{document.all('GetBankCode').value= arrResult[0][1];}catch(ex){};
//	showOneCodeName('bank','GetBankCode','BankName');
//	try{document.all('GetBankAccNo').value= arrResult[0][2];}catch(ex){};
//	try{document.all('GetAccName').value= arrResult[0][3];}catch(ex){};
//	if (document.all('GoonGetMethod').value == '0')
//	{
//		BankInfo.style.display = "";
//	}
//	else
//	{
//		BankInfo.style.display = "none";
//	}
//}

function QueryEdorInfo()
{
	var tEdortype=top.opener.document.all('EdorType').value;
	var strSQL;
	if(tEdortype!=null || tEdortype !='')
	{
	   //strSQL="select distinct edorcode, edorname from lmedoritem where edorcode = '" + tEdortype + "'";
	    var sqlid2 = "PEdorTypeAGSql2";
	  	var mySql2 = new SqlClass();
	  	mySql2.setResourceName("bqs.PEdorTypeAGSql"); // 指定使用的properties文件名
	  	mySql2.setSqlId(sqlid2);// 指定使用的Sql的id
	  	mySql2.addSubPara(tEdortype);// 指定传入的参数
	  	strSQL = mySql2.getString();   
	   
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