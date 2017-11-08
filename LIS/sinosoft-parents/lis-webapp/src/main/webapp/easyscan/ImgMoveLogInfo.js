/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : 辛玉强 <XinYQ@sinosoft.com.cn>
 * @version  : 1.00
 * @date     : 2006-12-26
 * @direction: 影像迁移日志查询主脚本
 * Modified By QianLy on 2007-01-26
 ******************************************************************************/

//<!-- JavaScript Document BGN -->


/*============================================================================*/

var turnPage = new turnPageClass();                 //全局变量, 查询结果翻页, 必须有
var turnPageTaskLogGrid = new turnPageClass();      //全局变量, 迁移批次查询结果翻页
var turnPageMoveErrorGrid = new turnPageClass();    //全局变量, 错误日志查询结果翻页

/*============================================================================*/

var showInfo;   //全局变量, 弹出提示窗口, 必须有

/*============================================================================*/

/**
 * 提交后操作, 服务器数据返回后执行的操作
 */
function afterSubmit(DealFlag, MsgContent)
{
    
    try { 
        showInfo.close();
        document.all("DocID").value = "";//点击之后清空DocID,防止重复(恶意)点击
        } catch (ex) {}
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

/**
 * 查询迁移批次信息
 */
function queryTaskLogGrid()
{
//    var QuerySQL = "select MoveID, "
//                 +        "ManageCom, "
//                 +        "ToManageCom, "
//                 +        "StartDate, "
//                 +        "EndDate, "
//                 +        "TaskNumber, "
//                 +        "SuccNumber, "
//                 +        "case when tasktype = '0' then '迁移完成'"
//                 +        "     when tasktype = '1' then '正在迁移'"
//                 +        "     when tasktype = '2' then '重传完成' end "
//                 +   "from ES_DOCMOVE_TASK "
//                 +  "where 1 = 1 "
//                 +     getWherePart("ManageCom", "OldManageCom", "like")
//                 +     getWherePart("ToManageCom", "NewManageCom", "like")
//                 +     getWherePart("StartDate", "StartDate")
//                 +     getWherePart("EndDate", "EndDate")
//                 +  "order by MoveID desc";
    
  	var  OldManageCom0 = window.document.getElementsByName(trim("OldManageCom"))[0].value;
  	var  NewManageCom0 = window.document.getElementsByName(trim("NewManageCom"))[0].value;
  	var  StartDate0 = window.document.getElementsByName(trim("StartDate"))[0].value;
  	var  EndDate0 = window.document.getElementsByName(trim("EndDate"))[0].value;
	var sqlid0="ImgMoveLogInfoSql0";
	var mySql0=new SqlClass();
	mySql0.setResourceName("easyscan.ImgMoveLogInfoSql"); //指定使用的properties文件名
	mySql0.setSqlId(sqlid0);//指定使用的Sql的id
	mySql0.addSubPara(OldManageCom0);//指定传入的参数
	mySql0.addSubPara(NewManageCom0);//指定传入的参数
	mySql0.addSubPara(StartDate0);//指定传入的参数
	mySql0.addSubPara(EndDate0);//指定传入的参数
	var QuerySQL=mySql0.getString();
    
    //alert(QuerySQL);
    try
    {
        turnPageTaskLogGrid.pageDivName = "divTurnPageTaskLogGrid";
        turnPageTaskLogGrid.queryModal(QuerySQL, TaskLogGrid);
    }
    catch (ex)
    {
        alert("警告：查询迁移批次信息出现异常！ ");
    }
}

/*============================================================================*/

/**
 * 查询批次错误信息
 * 并添加对OldManageCom,NewManageCom,StartDate,DocID和EndDate的初始化,为了支持重传
 */
function queryMoveErrorGrid()
{
    var nSelNo;
    try
    {
        nSelNo = TaskLogGrid.getSelNo() - 1;
    }
    catch (ex) {}
    if (nSelNo != null && nSelNo >= 0)
    {
        var sMoveID;
        try
        {
            sMoveID = TaskLogGrid.getRowColData(nSelNo, 1);
            document.all("OldManageCom").value = TaskLogGrid.getRowColData(nSelNo, 2);
            document.all("NewManageCom").value = TaskLogGrid.getRowColData(nSelNo, 3);
            document.all("StartDate").value = TaskLogGrid.getRowColData(nSelNo, 4);
            document.all("EndDate").value = TaskLogGrid.getRowColData(nSelNo, 5);
            document.all("DocID").value = "";
        }
        catch (ex) {}
        if (sMoveID != null && sMoveID.trim() != "")
        {
//            var QuerySQL = "select MoveID, "
//                         +        "DocID, "
//                         +        "FileDate, "
//                         +        "replace(Filepath, '\', '\\') "
//                         +   "from ES_DOCMOVE_LOG a "
//                         +  "where 1 = 1 "
//                         +    "and MoveID = '" + sMoveID + "' "
//                         +    "and Flag = '1' "//Flag 1:错误 0:重传后正确
//                         //是否是重传之后成功了,如果有成功记录就不再显示出来,但是以后如果多次传输错误或正确则不支持了,需要修改条件
//                         +    "and not exists (select 'X' from ES_DOCMOVE_LOG where docid = a.docid and flag = '0')"
//                         +     getWherePart("ManageCom", "OldManageCom", "like")
//                         +     getWherePart("ToManageCom", "NewManageCom", "like")
//                         +  "order by MoveID asc";
            
          	var  OldManageCom1 = window.document.getElementsByName(trim("OldManageCom"))[0].value;
          	var  NewManageCom1 = window.document.getElementsByName(trim("NewManageCom"))[0].value;
        	var sqlid1="ImgMoveLogInfoSql1";
        	var mySql1=new SqlClass();
        	mySql1.setResourceName("easyscan.ImgMoveLogInfoSql"); //指定使用的properties文件名
        	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
        	mySql1.addSubPara(sMoveID);//指定传入的参数
        	mySql1.addSubPara(OldManageCom1);//指定传入的参数
        	mySql1.addSubPara(NewManageCom1);//指定传入的参数
        	var QuerySQL=mySql1.getString();
        	
            //alert(QuerySQL);
            try
            {
                turnPageMoveErrorGrid.pageDivName = "divTurnPageMoveErrorGrid";
                turnPageMoveErrorGrid.queryModal(QuerySQL, MoveErrorGrid);
                divTurnPageResend.style.display = "";
            }
            catch (ex)
            {
                alert("警告：查询批次错误信息出现异常！ ");
            }
        }
    } //nSelNo >= 0
}

/**
 * 初始化要重传的DocID号
 */
function initDocID()
{
    var nSelNo;
    try
    {
        nSelNo = MoveErrorGrid.getSelNo() - 1;
    }
    catch (ex) {}
    if (nSelNo != null && nSelNo >= 0)
    {
        try
        {
            document.all("DocID").value = MoveErrorGrid.getRowColData(nSelNo, 2);
        }
        catch (ex) {}
    }
}

/**
 * 提交重传数据
 */
function resend()
{
    var docid = document.all("DocID").value;
    if(docid == null || docid ==""){
        return;
    }
    var MsgContent = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=C&content=" + MsgContent;
    //showInfo = showModelessDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=300;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    document.forms(0).action = "ImgTransferMoveSave.jsp";
    document.forms(0).submit();
}

/*============================================================================*/


//<!-- JavaScript Document END -->
