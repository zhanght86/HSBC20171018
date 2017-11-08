var showInfo;
var mDebug="0";
var arrDataSet;
window.onfocus=myonfocus;

//使得从该窗口弹出的窗口能够聚焦
function myonfocus()
{
	if(showInfo!=null) //shwoInfo是什么？
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

//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,50,82,*";
  }
 	else 
 	{
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
}

function VersionQuery()
{
	  showInfo=window.open("./FrameVersionRuleQuery.jsp");
}

function DataJudge()
{
	
	if(!beforeSubmit())
	{
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
  fm.action = './FIRuleEngineServiceSave.jsp';
  document.getElementById("fm").submit(); //提交
  
}



function afterQuery( arrQueryResult )
{
	var arrResult = new Array();
		//alert(arrQueryResult);

	if( arrQueryResult != null )
	{
		arrResult = arrQueryResult;
		document.all('VersionNo').value = arrResult[0][0];
		document.all('VersionState').value = arrResult[0][5];
		document.all('StartDay').value = arrResult[0][1];
		document.all('EndDay').value = arrResult[0][2];
		document.all('VersionState2').value = arrResult[0][9];
		
		document.all('StartDate').value = '';
		document.all('EndDate').value = '';
		document.all('callpoint').value = '';
		document.all('callpointName').value = '';
		document.all('callpointB').value = '';
		document.all('callpointBName').value = '';
		
		//来自于Common\javascript\Common.js，根据代码选择的代码查找并显示名称
		showCodeName(); 

	}
}

function beforeSubmit()
{
	if((document.all('VersionNo').value=="")||(document.all('VersionNo').value==null))
	 {
	 	alert("请先通过查询取得版本编号！！！");
	 	return false;
	 }
	 if(document.all('VersionState').value=="02")
	 {
	 	alert("版本状态必须为正常状态！！！");
	 	return false;
	 }
	 if((document.all('StartDate').value=="")||(document.all('StartDate').value==null))
	 {
	 	alert("起始日期不得为空！！！");
	 	return false;
	 }
	 if((document.all('EndDate').value=="")||(document.all('EndDate').value==null))
	 {
	 	alert("结束日期不得为空！！！");
	 	return false;
	 }

	 if((document.all('StartDay').value >document.all('StartDate').value)||(document.all('EndDay').value <document.all('EndDate').value))
	 {
	 	alert("日期区间不在版本有效日期之内！！！");
	 	return false;
	 }
	 if((document.all('callpoint').value=="")||(document.all('callpoint').value==null))
	 {
	 	alert("事件点汇总不得为空！！！");
	 	return false;
	 }
	 if((document.all('EndDay').value=="")||(document.all('EndDay').value==null))
	 {
	 	alert("版本信息不完整，请确认所选定的版本是否正确！！！");
	 	return false;
	 }
	 if((document.all('VersionState').value=="")||(document.all('VersionState').value==null))
	 {
	 	alert("版本信息不完整，请确认所选定的版本是否正确！！！");
	 	return false;
	 }
	 return true;
}

function superaddClick()
{
	if((document.all('callpointB').value == null)||(document.all('callpointB').value == ''))
	{
		alert("该项不得为空，请重新填写！");
		document.all('callpointB').value = '';
		document.all('callpointBName').value = '';
		return false;
	}
	if((document.all('callpoint').value == null)||(document.all('callpoint').value == ''))
	{
		document.all('callpoint').value = document.all('callpointB').value;
		document.all('callpointName').value = document.all('callpointBName').value;
		document.all('callpointB').value = '';
		document.all('callpointBName').value = '';
	}
	else
	{
		document.all('callpoint').value = document.all('callpoint').value + "," +document.all('callpointB').value;
		document.all('callpointName').value = document.all('callpointName').value + "," +document.all('callpointBName').value;
		document.all('callpointB').value = '';
		document.all('callpointBName').value = '';
	}
}

function clearClick()
{
		document.all('callpoint').value = '';
		document.all('callpointBName').value = '';
		document.all('callpoint').value = '';
		document.all('callpointName').value = '';
}

function afterSubmit( FlagStr, content )
{
  showInfo.close();
  //释放“增加”按钮

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
    //resetForm();
  }
  else
  {
    //alert(content);
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

  }

}