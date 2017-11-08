var showInfo;
var mDebug="1";
var Operatetype="";


function displayNumberInfo()
{
	var vHaveNumber = fm.HaveNumber.value;
	if( vHaveNumber == 'Y' ) {
		fm.CertifyLength.disabled = false;
	} else {
		fm.CertifyLength.value = 18;
		fm.CertifyLength.disabled = true;
	}
}

function displayMulLine()
{
  var displayType = fm.CertifyClass.value;

  document.all('divShow').style.display="";
  document.all('divCardRisk').style.display="";

}


//提交，保存按钮对应操作
function submitForm()
{
	if(vertifyInput() == false)
	{
		return;
	}

  //新增时需要根据界面输入的保费和险种编码去校验数据库中是否已存在相同的记录
  fm.HiddenRiskCode.value=fm.Riskcode.value;
  fm.HiddenPrem.value=fm.Prem.value;
  
  fm.Operatetype.value = "INSERT";
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
  fm.action = './RateCardSave.jsp';
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
    initForm();
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
    alert("初始化页面错误，重置出错");
  }
}

//取消按钮对应操作
function cancelForm()
{
    showDiv(operateButton,"true");
    showDiv(inputButton,"false");
}

//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
      parent.fraMain.rows = "0,0,0,0,*";
  }
   else
   {
      parent.fraMain.rows = "0,0,0,0,*";
   }
}

//Click事件，当点击增加图片时触发该函数
function addClick()
{
  showDiv(operateButton,"false");
  showDiv(inputButton,"true");
}

//Click事件，当点击“修改”图片时触发该函数
function updateClick()
{
  if (confirm("您确实想修改该记录吗?"))
   {
		if(vertifyInput() == false)
		{
			return;
		}
    fm.Operatetype.value = "UPDATE";
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
    showSubmitFrame(mDebug);
    
    fm.action = './RateCardSave.jsp';
    document.getElementById("fm").submit(); //提交
  }
  else
  {
    fm.Operatetype.value = "";
    alert("您取消了修改操作！");
  }
}
//Click事件，当点击“查询”图片时触发该函数
function queryClick()
{
	initForm();					//如果不初始化form的话，当第一次查询出定额单了，再次查询的时候下面的div显示就不会消除
	//showDiv(divShow,"");
	//showDiv(divCardRisk,"");
	
  //fm.Operatetype.value="QUERY";
  mOperate="QUERY||MAIN";
  showInfo=window.open("./FrameRateCardQuery.jsp");
}

function deleteClick()
{
  alert("您无法执行删除操作！！！！");
  return ;
}


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

//校验必须是数字函数
function doCheckNumber(str)
{ 
    //alert(str);
	for(var i=0; i<str.length; i++)
	{
		if(str.charAt(i)<'0' || str.charAt(i)>'9')
		{
			return false;
		}
	}
	
	return true;
}

//校验输入
function vertifyInput()
{
  if((fm.Riskcode.value==null)||(fm.Riskcode.value==""))
  {
    alert("请您录入险种编码！！！");
    return false;
  }

  
  if((fm.ProductPlan.value==null)||(fm.ProductPlan.value==""))
  {
    alert("请您录入产品计划！！！");
    return false;
  }
  
  if((fm.InsuYear.value==null)||(fm.InsuYear.value==""))
  {
    alert("请您录入保险年期！！！");
    return false;
  }
  
  if(!doCheckNumber(document.all('InsuYear' ).value))
  {
	alert("保险年期必须是数字");
	return false;
  }

  
  if((fm.InsuYearFlag.value==null)||(fm.InsuYearFlag.value==""))
  {
    alert("请您录入保险年期单位！！！");
    return false;
  }
  
  if((fm.Prem.value==null)||(fm.Prem.value==""))
  {
    alert("请您录入保费！！！");
    return false;
  }

  return;
}