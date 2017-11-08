//Creator :范昕	
//Date :2008-08-18

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


function VersionStateQuery()
{
	window.open("./FrameVersionRuleQuery.jsp");
}
function queryClick()
{
	var VersionNo = document.all('VersionNo').value;
	if (VersionNo == null||VersionNo == '')
  {
  	alert("请先进行版本信息查询！");
  	return;
  }
  showInfo=window.open("./FrameFIRuleDefQueryInput.jsp?VersionNo=" + VersionNo );
}

function submitForm()
{ 
	if(!beforeSubmit2())
	{
		return false;
	}
  fm.OperateType.value = "INSERT||MAIN";
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
  fm.action = './FIRuleDefSave.jsp';
  document.getElementById("fm").submit(); //提交
  //alert(1)
}

//Click事件，当点击“修改”图片时触发该函数
function updateClick()
{
	if((document.all('RuleID').value=="")||(document.all('RuleID').value==null))
	 {
	 	alert("请确定要删除的校验规则编号！！！");
	 	return false;
	 }
	 if((document.all('RuleName').value=="")||(document.all('RuleName').value=="null"))
	 {
	 	alert("校验规则名称不得为空！！！");
	 	return false;
	 }
	 if((document.all('RuleDealMode').value=="")||(document.all('RuleDealMode').value=="null"))
	 {
	 	alert("校验规则名称不得为空！！！");
	 	return false;
	 }
	 if((document.all('RuleReturnMean').value=="")||(document.all('RuleReturnMean').value=="null"))
	 {
	 	alert("校验返回含义不得为空！！！");
	 	return false;
	 }
	 if(document.all('RuleDealMode').value=="1")
	 {
	 	if((document.all('RuleDealClass').value =="")||(document.all('RuleDealClass').value ==null))
	 	{
	 		alert("处理类名称不得为空！");
	 		return false;
	 	}
	 }
	 if(document.all('RuleDealMode').value=="2")
	 {
	 	if((document.all('RuleDealSQL').value=="")||(document.all('RuleDealSQL').value==null))
	 	{
	 		alert("校验规则SQL不得为空！");
	 		return false;
	 	}
	 }
  if (confirm("您确实想修改该记录吗?"))
  {
    fm.OperateType.value = "UPDATE||MAIN";
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

    fm.action = './FIRuleDefSave.jsp';
    document.getElementById("fm").submit(); //提交

  }
  else
  {
    fm.OperateType.value = "";
    alert("您取消了修改操作！");
  }
}

//Click事件，当点击“删除”图片时触发该函数
function deleteClick()
{
	if((document.all('RuleID').value=="")||(document.all('RuleID').value==null))
	 {
	 	alert("请确定要删除的校验规则编号！！！");
	 	return false;
	 }
  if (confirm("您确实要删除该记录吗？"))
  {
    fm.OperateType.value = "DELETE||MAIN";
    var i = 0;
    var showStr="正在删除数据，请您稍候并且不要修改屏幕上的值或连接其他的界面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    fm.action = './FIRuleDefSave.jsp';
    document.getElementById("fm").submit();//提交

  }
}

function afterQuery1( arrQueryResult1 )
{
	var arrResult1 = new Array();
	if( arrQueryResult1 != null )
	{

		arrResult1 = arrQueryResult1;
		arrResult1[0][6] = arrResult1[0][6].split("@@").join("||");
		arrResult1[0][7] = arrResult1[0][7].split("@@").join("||");
		arrResult1[0][6] = arrResult1[0][6].split("@").join("|");
		arrResult1[0][7] = arrResult1[0][7].split("@").join("|");
		document.all('RuleID').value = arrResult1[0][1];
		document.all('RuleName').value = arrResult1[0][2];
		document.all('RuleDealMode').value = arrResult1[0][3];
		document.all('RuleDealClass').value = arrResult1[0][4];
		document.all('RuleDealSQL').value = arrResult1[0][6];
		document.all('RuleReturnMean').value = arrResult1[0][7];
		
    if(fm.RuleDealMode.value=='1')
	{
		 classdiv.style.display='';
		 sqldiv.style.display='none'; 
		 document.all('RuleDealSQL').value = '';
	}
		if(fm.RuleDealMode.value=='2')
	{
		 classdiv.style.display='none';
		 sqldiv.style.display=''; 
		 document.all('RuleDealClass').value = '';
	}			
	
		document.all('addbutton').disabled = false;
		document.all('updatebutton').disabled = false;		
		document.all('deletebutton').disabled = false;
		
		if (document.all('VersionState').value == "01"||document.all('VersionState').value == "03"||document.all('VersionState').value == ""||document.all('VersionState').value == null)
		{
			document.all('addbutton').disabled = true;				
			document.all('updatebutton').disabled = true;		
			document.all('deletebutton').disabled = true;	
		}
		showCodeName(); 		
	}
}

//提交后操作,服务器数据返回后执行的操作
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
    fm.OperateType.value="";
    //resetForm();
  }
  else
  {
    //alert(content);
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
  	//parent.fraInterface.initForm();
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

		if(fm.OperateType.value == "DELETE||MAIN")
		{
			document.all('RuleID').value = '';
    	document.all('RuleName').value = '';
    	document.all('RuleDealMode').value = '';
    	document.all('RuleDealModeName').value = '';
    	document.all('RuleReturnMean').value = '';
    	document.all('RuleDealClass').value = '';
    	document.all('RuleDealSQL').value = '';
    	
    	document.all('updatebutton').disabled = true;	
    	document.all('deletebutton').disabled = true;
    	classdiv.style.display='none';
		 	sqldiv.style.display='none';	
		}
		if(fm.OperateType.value == "INSERT||MAIN")
		{
			document.all('RuleID').value = '';
    	document.all('RuleName').value = '';
    	document.all('RuleDealMode').value = '';
    	document.all('RuleDealModeName').value = '';
    	document.all('RuleReturnMean').value = '';
    	document.all('RuleDealClass').value = '';
    	document.all('RuleDealSQL').value = '';
    	
    	document.all('updatebutton').disabled = true;	
    	document.all('deletebutton').disabled = true;
    	classdiv.style.display='none';
		 	sqldiv.style.display='none';	
		}
    fm.OperateType.value="";
    
  }

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
		document.all('VersionState2').value = arrResult[0][9];
		
			document.all('RuleID').value = '';
    	document.all('RuleName').value = '';
    	document.all('RuleDealMode').value = '';
    	document.all('RuleDealModeName').value = '';
    	document.all('RuleReturnMean').value = '';
    	document.all('RuleDealClass').value = '';
    	document.all('RuleDealSQL').value = '';
		
    	classdiv.style.display='none';
		 	sqldiv.style.display='none';	
    //当版本状态不为02-维护的时候，增删改按钮为灰色		
		if (arrResult[0][5] == "01"||arrResult[0][5] == "03"||arrResult[0][5] == ""||arrResult[0][5] == null)
		{
			document.all('addbutton').disabled = true;				
			document.all('updatebutton').disabled = true;		
			document.all('deletebutton').disabled = true;	
		}
		if (arrResult[0][5] == "02")
		{
			document.all('addbutton').disabled = false;				
			document.all('updatebutton').disabled = true;		
			document.all('deletebutton').disabled = true;	
		}
		 	
	}
}

function afterCodeSelect( cName, Filed)
{   
	if(fm.RuleDealMode.value=='1')
	{
		 classdiv.style.display='';
		 sqldiv.style.display='none'; 
		 document.all('RuleDealSQL').value = '';
	}
		if(fm.RuleDealMode.value=='2')
	{
		 classdiv.style.display='none';
		 sqldiv.style.display=''; 
		 document.all('RuleDealClass').value = '';
	}			

}
  
  
  function beforeSubmit2()
  {
  	if((document.all('VersionNo').value=="")||(document.all('VersionNo').value=="null"))
	 {
	 	alert("请先通过查询取得版本编号！！！");
	 	return false;
	 }
	 if((document.all('RuleID').value!=""))
	 {
	 	alert("校验规则编码已经存在，请刷新页面后再进行此操作！！！");
	 	return false;
	 }
	 if((document.all('RuleName').value=="")||(document.all('RuleName').value=="null"))
	 {
	 	alert("校验规则名称不得为空！！！");
	 	return false;
	 }
	 if((document.all('RuleDealMode').value=="")||(document.all('RuleDealMode').value=="null"))
	 {
	 	alert("校验规则名称不得为空！！！");
	 	return false;
	 }
	 if((document.all('RuleReturnMean').value=="")||(document.all('RuleReturnMean').value=="null"))
	 {
	 	alert("校验返回含义不得为空！！！");
	 	return false;
	 }
	 if(document.all('RuleDealMode').value=="1")
	 {
	 	if((document.all('RuleDealClass').value =="")||(document.all('RuleDealClass').value ==null))
	 	{
	 		alert("处理类名称不得为空！");
	 		return false;
	 	}
	 }
	 if(document.all('RuleDealMode').value=="2")
	 {
	 	if((document.all('RuleDealSQL').value=="")||(document.all('RuleDealSQL').value==null))
	 	{
	 		alert("校验规则SQL不得为空！");
	 		return false;
	 	}
	 }
	 return true;
  }
  
  function resetAgain()
{
			document.all('RuleID').value = '';
    	document.all('RuleName').value = '';
    	document.all('RuleDealMode').value = '';
    	document.all('RuleDealModeName').value = '';
    	document.all('RuleReturnMean').value = '';
    	document.all('RuleDealClass').value = '';
    	document.all('RuleDealSQL').value = '';
    	
    	document.all('updatebutton').disabled = true;	
    	document.all('deletebutton').disabled = true;
    	classdiv.style.display='none';
		 	sqldiv.style.display='none';	
}