//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
    var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=350;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

  }
}

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

function ShowCaseDetail()
{
	fm.action = "./ShowCaseDetail.jsp";
	fm.submit();	
}

function SecondUW()
{
	var varSrc = "&CaseNo=" + fm.CaseNo.value;
  varSrc += "&InsuredNo=" + fm.InsuredNo.value;
  varSrc += "&CustomerName=" + fm.CustomerName.value;
  varSrc += "&RgtNo=" + fm.RgtNo.value;
  varSrc += "&Type=1";
  var newWindow = window.open("../case/FraimSecondUW.jsp?Interface=QuerySecondUWInput.jsp"+varSrc,"SecondUWInput",'width=700,height=500,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=0,status=0');
}


function EndAgreement()
{
	var varSrc = "&CaseNo=" + fm.CaseNo.value;
  varSrc += "&InsuredNo=" + fm.InsuredNo.value;
  varSrc += "&CustomerName=" + fm.CustomerName.value;
  varSrc += "&RgtNo=" + fm.RgtNo.value;
  varSrc += "&Type=1";
  var newWindow = window.open("../case/FraimEndAgreement.jsp?Interface=QueryEndAgreementInput.jsp"+varSrc,"EndAgreementInput",'width=700,height=500,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=0,status=0');
}