//               该文件中包含客户端需要处理的函数和事件

var mDebug="0";
var mOperate="";
var showInfo;
window.onfocus=myonfocus;
//使得从该窗口弹出的窗口能够聚焦
function myonfocus()
{
	if(showInfo!=null)
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

//提交，保存按钮对应操作
function submitForm()
{
	if (verifyInput())
	{
//	if(dateDiff(fm.StartDay.value,fm.EndDay.value,"D")!=1){
//		alert("起止时间不同，请确认。");
//		return;
//	}
	  
	  var i = 0;
	  var showStr="正在准备打印数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    //fm.hideOperate.value=mOperate;
	  //if (fm.hideOperate.value=="")
	  //{
	  //  alert("操作控制数据丢失！");
	  //}
	  //showSubmitFrame(mDebug);
	  document.getElementById("fm").submit(); //提交
	  setFormAllDisabled();
	  setTimeout(function(){afterSubmit("","");},3000)
	}
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
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  setFormAllEnabled();
}
//赔款支出
function PrintPKZC(){
	fm.action="../f1print/FinDayCheckSave.jsp";
	fm.target="../f1print";
	fm.fmtransact.value="YingFu"; //代表收费
    fm.Opt.value="PKZC";//操作的标志
    submitForm();
}
//保全应付
function PrintBQYF(){
	fm.action="../f1print/FinDayCheckSave.jsp";
	fm.target="../f1print";
	fm.fmtransact.value="YingFu"; //代表收费
    fm.Opt.value="BQYF";//操作的标志
    submitForm();
}
//领取给付
function PrintLQJF(){
	fm.action="../f1print/FinDayCheckSave.jsp";
	fm.target="../f1print";
	fm.fmtransact.value="YingFu"; //代表收费
 	fm.Opt.value="LQJF";//操作的标志
	submitForm();
}
//其它应付
function PrintQTYF(){
	fm.action="../f1print/FinDayCheckSave.jsp";
	fm.target="../f1print";
	fm.fmtransact.value="YingFu"; //代表收费
	fm.Opt.value="QTYF";//操作的标志
	submitForm();
}

//收费打印Pay
function ORPrint()
{
	fm.action="../f1print/FinDayCheckSave.jsp";
	fm.target="../f1print";
	fm.fmtransact.value="PRINTPAY"; //代表收费
	fm.Opt.value="Pay";//收费的类型是暂收
	submitForm();
}
//打印核保日结清单
function HeBao_Print()
{
  fm.action = "../f1print/FinDayCheckSave.jsp";
  fm.target="../f1print";
  fm.fmtransact.value = "PRINTPAY";
  fm.Opt.value="HeBao";//操作的标志
  submitForm();
}
/*******************************************************************************
 * name:YSPrint
 * type:function
 * date:2003-05-28
 * author:Liuyansong
 * function :预收日结打印
 */
function YSPrint()
{
  fm.action = "../f1print/FinDayCheckSave.jsp";
  fm.target="../f1print";
  fm.fmtransact.value = "PRINTPAY";
  fm.Opt.value = "YSPay";//收费类型是预收
  submitForm();
}
//付费打印Get
function OMPrint()
{
  fm.action="../f1print/FinDayCheckSave.jsp";
  fm.target="../f1print";
  fm.fmtransact.value="PRINTGET";//代表付费
  fm.Opt.value="Get";
  submitForm();
}
//实付日结单
function PrintYWSF()
{
  fm.action = "../f1print/FinDayCheckSave.jsp";
  fm.target="../f1print";
  fm.fmtransact.value = "PRINTGET";
  fm.Opt.value="YWSF";//操作的标志
  submitForm();
}
//保费收入日结单
function PrintBQSF()
{
  fm.action = "../f1print/FinDayCheckSave.jsp";
  fm.target="../f1print";
  fm.fmtransact.value = "PRINTPAY";
  fm.Opt.value="BQSF";//操作的标志
  submitForm();
}

//*********************************************************************

function PayModePrint()
{
  fm.action = "../f1print/FinDayCheckSave.jsp";
  fm.target="../f1print";
  fm.fmtransact.value = "PRINTPAY";
  fm.Opt.value="PayMode";//操作的标志
  submitForm();
}

function GetModePrint()
{
  fm.action = "../f1print/FinDayCheckSave.jsp";
  fm.target="../f1print";
  fm.fmtransact.value = "PRINTGET";
  fm.Opt.value="GetMode";//操作的标志
  submitForm();
}

//管理费收入日结单
function PrintGLFY(){
	fm.action="../f1print/FinDayCheckSave.jsp";
	fm.target="../f1print";
	fm.fmtransact.value="PRINTPAY"; //代表收费
	fm.Opt.value="GLFY";//操作的标志
	submitForm();
}
//航意险
function AirPrint()
{
	fm.action="../f1print/FinDayCheckSave.jsp";
	fm.target="../f1print";
	fm.fmtransact.value="PRINTPAY"; //代表收费
		fm.Opt.value="AirPay";//收费的类型是航意险
	
	submitForm();
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

//Click事件，当点击“删除”图片时触发该函数
function deleteClick()
{
  //下面增加相应的删除代码
  if (confirm("您确实想删除该记录吗?"))
  {
    mOperate="DELETE||MAIN";
    submitForm();
  }
  else
  {
    mOperate="";
    alert("您取消了删除操作！");
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

