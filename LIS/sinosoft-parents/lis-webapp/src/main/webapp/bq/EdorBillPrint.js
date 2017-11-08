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
  var StartDate = fm.StartDate.value;
  var EndDate = fm.EndDate.value;
  var EdorDate = fm.EdorDate.value;
  var BillType = fm.BillType.value;
  var ManageCom = fm.ManageCom.value;
  var SaleChnl = fm.SaleChnl.value;
  if(SaleChnl == "")
  {
       showInfo.close();
       alert("请选择渠道！");
       fm.SaleChnl.focus();
       return;
  }
  if(BillType == "")
  {
       showInfo.close();
       alert("请选择清单类型！");
       fm.BillType.focus();
       return;
  }
  if(StartDate == "" || EndDate == "")
  {
        showInfo.close();
        alert("请输入起始日期和结束日期!");
        return;
  }
  if(!checkDate(StartDate)||!checkDate(EndDate))
  {
    showInfo.close();
    alert("非有效的日期格式！");
    return;
  }
  if(fm.CTFlag.value == "1")
  {
    if(fm.DateType.value == "")
    {
       showInfo.close();
       alert("请选择统计起止期的类型！");
       fm.DateType.focus();
       return ;
    }
    if(fm.DateType.value != "1"&&fm.DateType.value != "2")
    {
        showInfo.close();
        alert("统计起止期的类型有误！");
        fm.DateType.focus();
        return ;
    }
  }
  //峰值统计清单
  if(SaleChnl == "0" && BillType == "006")
  {
     if(EdorDate == "")
     {
        showInfo.close();
        alert("请输入统计日期!");
        return;
     }
     if(!checkDate(EdorDate))
     {
       showInfo.close();
       alert("非有效的日期格式！");
       return;
     }
  }
  var startValue=StartDate.split("-");
  var dateStartDate = new Date(startValue[0],startValue[1]-1,startValue[2]);
  var endValue=EndDate.split("-");
  var dateEndDate = new Date(endValue[0],endValue[1]-1,endValue[2]);
  if(dateStartDate.getTime() > dateEndDate.getTime())
  {
        showInfo.close();
        alert("统计起期不能晚于统计止期！");
        return;
  }
  else
  {
        //
        fm.fmtransact.value = "PRINT";
        fm.target = "f1print";
        fm.submit();
        showInfo.close();
  }
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
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();


  }
}

function showQuery()
{
    var tBillType = fm.BillType.value;
    var tCodeType = fm.ChnlType.value;

    //初始化所有块
    divDate.style.display = '';
    divBank.style.display = 'none';
    divDateType.style.display = 'none';
    divErrorReportLayer.style.display = 'none';
    divPayGet.style.display = 'none';
    divEdorTypeTitle.style.display = 'none';
    divEdorType.style.display = 'none';
    divMaxBill.style.display = 'none';
    fm.CTFlag.value = "0";

    if(tBillType!=null && tBillType!="")
    {
//        var strSql = "select trim(othersign) from ldcode "
//                      + " where codetype = '"+tCodeType+"' "
//                      + " and code = '"+tBillType+"'";
//        
        var sqlid1="EdorBillPrintSql1";
     	var mySql1=new SqlClass();
     	mySql1.setResourceName("bq.EdorBillPrintSql");
     	mySql1.setSqlId(sqlid1); //指定使用SQL的id
     	mySql1.addSubPara(tCodeType);//指定传入参数
     	mySql1.addSubPara(tBillType);
     	var strSql = mySql1.getString();
        
        var brr = easyExecSql(strSql);
        if(brr)
        {
            //ldcode表中othersign:
            //为1的需要填写银行网点信息
            //为2的需要选择日期类型
            //为3的都需要
            if(brr[0][0]=="1")
            {
                divBank.style.display = '';
            }
            else if(brr[0][0]=="2")
            {
                divDateType.style.display = '';
                fm.CTFlag.value = "1";
                fm.DateType.value = "1";
                fm.DateTypeName.value = "确认日期－日结";
            }else if(brr[0][0]=="3")
            {
                divBank.style.display = '';
                divDateType.style.display = '';
                fm.CTFlag.value = "1";
                fm.DateType.value = "1";
                fm.DateTypeName.value = "确认日期－日结";
            }
        }
    }

    //<!-- XinYQ added on 2006-04-03 : 保全差错率统计清单打印 : BGN -->
    var sSaleChnl, sBillType;
    try
    {
        sSaleChnl = document.getElementsByName("SaleChnl")[0].value;
        sBillType = document.getElementsByName("BillType")[0].value;
    }
    catch (ex) {}

    if (sSaleChnl != null && trim(sSaleChnl) == "0" && sBillType != null && trim(sBillType) == "004")
    {
        try { document.all("divErrorReportLayer").style.display = ""; } catch (ex) {}
        divEdorTypeTitle.style.display = '';
        divEdorType.style.display = '';
    }
    //<!-- XinYQ added on 2006-04-05 : 保全差错率统计清单打印 : END -->
    //收付费业务统计
    if (sSaleChnl != null && trim(sSaleChnl) == "0" && sBillType != null && trim(sBillType) == "005")
    {
        try { document.all("divPayGet").style.display = ""; } catch (ex) {}
        divEdorTypeTitle.style.display = '';
        divEdorType.style.display = '';
    }
    //峰值统计－按日
    if (sSaleChnl != null && trim(sSaleChnl) == "0" && sBillType != null && trim(sBillType) == "006")
    {
        divDate.style.display = 'none';
        divEdorTypeTitle.style.display = '';
        divEdorType.style.display = '';
        divMaxBill.style.display = '';
        fm.EdorDate.value = getCurrentDate('-');//默认为当天
    }
    //峰值统计－按月
    if (sSaleChnl != null && trim(sSaleChnl) == "0" && sBillType != null && trim(sBillType) == "007")
    {
        divEdorTypeTitle.style.display = '';
        divEdorType.style.display = '';
    }
}

function showChnlType()
{
    var SaleChnl = fm.SaleChnl.value;
    var ChnlSel = fm.ChnlSel.value;
    if(SaleChnl!=ChnlSel)
    {
        fm.ChnlSel.value = SaleChnl;    //记录每次所选渠道，如果更改渠道则清空清单类型
        document.all('BillType').value = '';
        document.all('BillTypeName').value = '';
    }

    switch(SaleChnl)
    {
        case "1":  fm.ChnlType.value = "bqbillperson";
                    break;
        case "2":  fm.ChnlType.value = "bqbillgrp";
                    break;
        case "3":  fm.ChnlType.value = "bqbillbank";
                    break;
        case "5":  fm.ChnlType.value = "bqbillphone";
                    break;
        case "0":  fm.ChnlType.value = "bqbillall";
                    break;
        default:    fm.ChnlType.value = "";
        break;
    }
}
function initBillType(cObjCode,cObjName)
{
    var ChnlType = fm.ChnlType.value;
    if(ChnlType == null||ChnlType == "")
    {
        alert("请先选择渠道，再选择清单！");
        fm.SaleChnl.focus();
        return;
    }
    showCodeList(ChnlType,[cObjCode,cObjName],[0,1]);
}
function onKeyUpBillType(cObjCode,cObjName)
{
    var ChnlType = fm.ChnlType.value;
    if(ChnlType == null||ChnlType == "")
    {
        alert("请先选择渠道，再选择清单！");
        fm.SaleChnl.focus();
        return;
    }
    showCodeListKey(ChnlType,[cObjCode,cObjName],[0,1]);
}
//改为后台取默认起止期，此函数废弃不用
function initDate()
{
        var today = new Date();
        var thisDay = 25;
        var preDay = 26;
        var tYear = today.getYear();
        var preYear = tYear;
        var thisMonth = today.getMonth()+1;
        var preMonth = thisMonth-1;
        if(thisMonth == 1)
        {
           preMonth = thisMonth;
           preDay = 1;
        }
        if(thisMonth == 12)
        {
           thisDay = 31;
        }
        document.all('StartDate').value = preYear+"-"+preMonth+"-"+preDay;
        document.all('EndDate').value = tYear+"-"+thisMonth+"-"+thisDay;

}

function isLeap(tYear)
{
    return (tYear%4)==0 ? ((tYear%100)==0?((tYear%400)==0?true:false):true):false;
}
function getMonthLength(tYear,tMonth)
{
    if(tMonth > 12 || tMonth < 1)
        return 0;
    var MONTH_LENGTH = new Array(31,28,31,30,31,30,31,31,30,31,30,31);
    var LEAP_MONTH_LENGTH = new Array(31,29,31,30,31,30,31,31,30,31,30,31);

    return isLeap(tYear) ? LEAP_MONTH_LENGTH[tMonth-1] : MONTH_LENGTH[tMonth-1];
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