//该文件中包含客户端需要处理的函数和事件

//程序名称：EdorBillPrint.js
//程序功能：清单打印
//创建日期：2005-08-08 15:39:06
//创建人  ：liurx
//更新记录：  更新人    更新日期     更新原因/内容


var showInfo;
var turnPage = new turnPageClass();


//<!-- XinYQ added on 2006-04-03 : 保全差错率统计清单打印 : BGN -->
/*============================================================================*/

/**
 * 弹出通用保全用户查询
 */
function queryEdorUser()
{
    var sManageCom, sUserCode;
    try
    {
        sManageCom = document.getElementsByName("ManageCom")[0].value;
        sUserCode = document.getElementsByName("UserCode")[0].value;
    }
    catch (ex) {}
    //弹出页面显示
    var sOpenWinURL = "../sys/UsrCommonQueryMain.jsp";
    var sParameters = "ManageCom=" + sManageCom + "&UserCode=" + sUserCode;
    OpenWindowNew(sOpenWinURL + "?" + sParameters, "保全用户查询", "left");
}

/*============================================================================*/

/**
 * 弹出通用保全用户查询后点返回赋值
 */
function afterQuery(arrResult)
{
    if(arrQueryResult != null)
    {
        try { document.getElementsByName("UserCode")[0].value = arrResult[0][0]; } catch(ex) {}
    }
}

/*============================================================================*/
//<!-- XinYQ added on 2006-04-03 : 保全差错率统计清单打印 : END -->


//提交，保存按钮对应操作
function printBill()
{
  var i = 0;

  var MakeDate = fm.MakeDate.value;
  var ManageCom = fm.ManageCom.value;

  if(MakeDate == "" || MakeDate == null)
  {
        alert("请保全确认日期!");
        return;
  }

  if(ManageCom == "")
  {
       alert("请选择机构!");
       return;
  }

//  if(!checkDate(StartDate)||!checkDate(EndDate))
//  {
//    showInfo.close();
//    alert("非有效的日期格式！");
//    return;
//  }
//
       var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
       var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	   var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
  
        fm.fmtransact.value = "PRINT";
        fm.target = "f1print";
        document.getElementById("fm").submit();showInfo.close();
}



//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  //showInfo.close();
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
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();


  }
}




/**
 * 日期校验函数
 × 囊括了Common.js中对三种日期格式的校验，并且针对往往有人会直接把"2006-05-31"改为"2006-04-31"来查询,加上对天数不能超过月份长度的校验。
 * 参数：日期字符串
 * added by liurx 2006-05-25
 */
function checkDate(tDate)
{
    var dateValue;
    var tYear;
    var tMonth;
    var tDay;
    if(isDate(tDate))
    {
        dateValue = tDate.split("-");
        tYear = eval(dateValue[0]);
        tMonth = eval(dateValue[1]);
        tDay = eval(dateValue[2]);
        if(tDay > getMonthLength(tYear,tMonth))
        {
            return false;
        }
        return true;
    }
    else if(isDateN(tDate))
    {
        dateValue = new Array();
        dateValue[0]=tDate.substring(0, 4);
        dateValue[1]=tDate.substring(4, 6);
        dateValue[2]=tDate.substring(6, 8);

        tYear = eval(dateValue[0]);
        tMonth = eval(dateValue[1]);
        tDay = eval(dateValue[2]);
        if(tDay > getMonthLength(tYear,tMonth))
        {
            return false;
        }
        return true;
    }
    else if(isDateI(tDate))
    {
        dateValue = tDate.split("/");
        tYear = eval(dateValue[0]);
        tMonth = eval(dateValue[1]);
        tDay = eval(dateValue[2]);
        if(tDay > getMonthLength(tYear,tMonth))
        {
            return false;
        }
        return true;
    }
    else
    {
        return false;
    }
}
