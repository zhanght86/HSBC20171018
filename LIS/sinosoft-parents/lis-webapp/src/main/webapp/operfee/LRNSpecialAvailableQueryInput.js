/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : 辛玉强 <XinYQ@sinosoft.com.cn>
 * @version  : 1.00, 1.01
 * @date     : 2005-12-03, 2006-02-15
 * @direction: 保单特殊复效查询主脚本
 ******************************************************************************/

//<!-- JavaScript Document BGN -->


/*============================================================================*/

var showInfo;                          //全局变量, 弹出的提示窗口, 必须有
var turnPage = new turnPageClass();    //全局变量, 翻页必须有

/*============================================================================*/


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
}

/*============================================================================*/

/**
 * 如果清空了下拉列表代码, 则同时清除已显示的下拉列表代码对应的名称
 */
function clearEmptyCode(objCodeList, objCodeListName)
{
    var sCodeList = document.getElementsByName(objCodeList.name)[0].value;
    if (sCodeList == null || sCodeList == "")
    {
        try { document.getElementsByName(objCodeListName.name)[0].value = ""; } catch (ex) {}
    }
}

/*============================================================================*/


//转换并校验日期格式
function formatDate(ObjTextInput)
{
    var ObjTextValue;
    var sErrorMsg = "对不起, 您输入的日期格式有误！ \n\n日期格式必须是 yyyymmdd 或 yyyy-mm-dd 格式！";
    try
    {
        ObjTextValue = document.getElementsByName(ObjTextInput.name)[0].value;
        ObjTextValue = ObjTextValue.replace(/^\s*|\s*$/g, "");
    }
    catch (ex)
    {
        return;
    }
    if (ObjTextValue.length == 0)
        return;
    else if (ObjTextValue.length == 8)
    {
        if (ObjTextValue.indexOf('-') == -1)
        {
            var nYear  = ObjTextValue.substring(0, 4);
            var nMonth = ObjTextValue.substring(4, 6);
            var nDay   = ObjTextValue.substring(6, 8);
            if (nYear == "0000" || nMonth == "00" || nDay == "00")
            {
                alert(sErrorMsg);
                document.getElementsByName(ObjTextInput.name)[0].value = "";
                return;
            }
            else
                document.getElementsByName(ObjTextInput.name)[0].value = nYear + "-" + nMonth + "-" + nDay;
        }
    } //ObjTextValue.length == 8
    else if (ObjTextValue.length == 10)
    {
        var nYear  = ObjTextValue.substring(0, 4);
        var nMonth = ObjTextValue.substring(5, 7);
        var nDay   = ObjTextValue.substring(8, 10);
        if (nYear == "0000" || nMonth == "00" || nDay == "00")
        {
            alert(sErrorMsg);
            document.getElementsByName(ObjTextInput.name)[0].value = "";
            return;
        }
    } //ObjTextValue.length == 10
    else
    {
        alert(sErrorMsg);
        document.getElementsByName(ObjTextInput.name)[0].value = "";
        return;
    } //ObjTextValue.length != 8 && ObjTextValue.length != 10
}

/*============================================================================*/

/**
 * 查询特殊复效合同信息和轨迹
 */
function queryRevalidateTrack()
{
    //常规数据校验
    if (!verifyInput2()) return;
    //检查附加条件
    var sStartDate, sEndDate;
    try
    {
        sStartDate = document.getElementsByName("StartDate")[0].value;
        sEndDate = document.getElementsByName("EndDate")[0].value;
    }
    catch (ex) {}
    //拼写SQL语句
    //var QuerySQL = "select a.ContNo, "
    //            +        "a.RevalidateTimes, "
    //            +        "a.InvalidReason , "
    //            +        "trim(a.ManageCom) || '-' || "
    //            +        "(select Name from LDCom where ComCode = a.ManageCom), "
    //            +        "a.Remark, "
    //            +        "a.Operator, "
    //            +        "a.MakeDate, "
    //            +        "a.MakeTime "
    //            +   "from LPRevalidateTrack a "
    //            +  "where 1 = 1 "
    //            +  getWherePart("a.ContNo", "ContNo")
    //            +  getWherePart("a.ManageCom", "ManageCom", "like") + " "
    //            +  getWherePart("a.InvalidReason", "InvalidReason")
    //            +  getWherePart("a.Operator", "Operator");
    var QuerySQL = wrapSql('LPRevalidateTrack1',[fm.ContNo.value,fm.ManageCom.value,fm.InvalidReason.value,fm.Operator.value,trim(sStartDate),trim(sEndDate)]);
    
    //if (sStartDate != null && trim(sStartDate) != "")
    //    QuerySQL = QuerySQL + "and a.MakeDate >= '" + trim(sStartDate) + "' ";
    //if (sEndDate != null && trim(sEndDate) != "")
    //    QuerySQL = QuerySQL + "and a.MakeDate <= '" + trim(sEndDate) + "' ";
    //QuerySQL = QuerySQL + "order by a.ContNo, a.RevalidateTimes asc";
    try
    {
        turnPage.queryModal(QuerySQL, QueryGrid);
    }
    catch (ex)
    {
        alert("警告：查询复效合同信息出现异常！ ");
        return;
    }
    if (QueryGrid.mulLineCount == 0) alert("没有符合条件的复效合同纪录！ ");
}

/*============================================================================*/

/**
 * 打印查询出来特殊复效合同信息和轨迹
 */
function printRevalidateTrack()
{
    //if (QueryGrid.mulLineCount == 0)
    //{
    //    alert("没有需要打印的复效合同纪录。请先查询！ ");
    //    return;
    //}
    document.forms(0).action = "LRNSpecialAvailableQueryPrint.jsp";
    document.forms(0).submit();
}

/*============================================================================*/

/**
	mysql工厂，根据传入参数生成Sql字符串
	
	sqlId:页面中某条sql的唯一标识
	param:数组类型,sql中where条件里面的参数
**/
function wrapSql(sqlId,param){
	
	var mysql=new SqlClass();
	
	mysql.setResourceName("operfee.LRNSpecialAvailableQueryInput");
	mysql.setSqlId(sqlId);
	
	for(i=0;i<param.length;i++){
		 mysql.addSubPara(param[i]);
	}
	
	return mysql.getString();
	
}



//<!-- JavaScript Document END -->
