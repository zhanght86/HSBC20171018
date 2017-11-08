//程序名称：EdorApprove.js
//程序功能：保全复核
//创建日期：2005-05-08 15:20:22
//创建人  ：sinosoft
//更新记录：  更新人    更新日期     更新原因/内容

var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();


/**
*  查询保全申请信息
*  描述: 查询保全申请信息
*/
function initQuery()
{
    var EdorAcceptNo    = document.all('EdorAcceptNo').value;

    if (EdorAcceptNo == null || EdorAcceptNo == "")
    {
        alert("保全受理号为空！");
        return;
    }
    
 
    mySql=new SqlClass();
    mySql.setResourceName("bq.EdorNotice");
    mySql.setSqlId("EdorNoticeSql1");
    mySql.addSubPara(EdorAcceptNo); 
    
    var brr = easyExecSql(mySql.getString(),1,0,1);
    if ( brr )
    {
        brr[0][0]==null||brr[0][0]=='null'?'0':fm.OtherNo.value     = brr[0][0];
        brr[0][1]==null||brr[0][1]=='null'?'0':fm.OtherNoTypeName.value = brr[0][1];
        brr[0][3]==null||brr[0][2]=='null'?'0':fm.EdorAppName.value = brr[0][2];
        brr[0][4]==null||brr[0][3]=='null'?'0':fm.ApptypeName.value     = brr[0][3];
        brr[0][5]==null||brr[0][4]=='null'?'0':fm.ManageComName.value   = brr[0][4];
        brr[0][6]==null||brr[0][5]=='null'?'0':fm.EdorState.value   = brr[0][5];
        brr[0][7]==null||brr[0][6]=='null'?'0':fm.OtherNoType.value = brr[0][6];
        brr[0][9]==null||brr[0][7]=='null'?'0':fm.Apptype.value     = brr[0][7];
        brr[0][10]==null||brr[0][8]=='null'?'0':fm.ManageCom.value   = brr[0][8];

    }
    else
    {
        alert("保全申请查询失败！");
        return;
    }

    showEdorItemList(EdorAcceptNo);
}


/**
*  查询保全项目列表
*  描述: 查询保全项目列表
*/
function showEdorItemList(tEdorAcceptNo)
{
 
    mySql=new SqlClass();
    mySql.setResourceName("bq.EdorNotice");
    mySql.setSqlId("EdorNoticeSql3");
    mySql.addSubPara(tEdorAcceptNo);     
    
    var drr = easyExecSql(mySql.getString(), 1, 0,"","",1);
    if ( !drr )
    {
        alert("申请批单下没有保全项目！");
        return;
    }
    else
    {
        turnPage2.pageDivName = "divTurnPageEdorItemGrid";
        turnPage2.queryModal(mySql.getString(), EdorItemGrid);
    }
}

/**
*  取出保全项目信息
*  描述: 取出保全项目信息
*/
function getEdorItemInfo()
{
    var tSel = EdorItemGrid.getSelNo() - 1;

    fm.EdorNo.value         = EdorItemGrid.getRowColData(tSel, 1);
    fm.EdorType.value       = EdorItemGrid.getRowColData(tSel, 14);
    fm.ContNo.value         = EdorItemGrid.getRowColData(tSel, 3);
    fm.InsuredNo.value      = EdorItemGrid.getRowColData(tSel, 4);
    fm.PolNo.value          = EdorItemGrid.getRowColData(tSel, 5);
    fm.EdorItemAppDate.value       = EdorItemGrid.getRowColData(tSel, 6);
    fm.EdorAppDate.value           = EdorItemGrid.getRowColData(tSel, 6);
    fm.EdorValiDate.value          = EdorItemGrid.getRowColData(tSel, 7);
    fm.EdorItemState.value  = EdorItemGrid.getRowColData(tSel, 11);
    fm.MakeDate.value       = EdorItemGrid.getRowColData(tSel, 12);
    fm.MakeTime.value       = EdorItemGrid.getRowColData(tSel, 13);
 
}



/**
 * 提交后操作, 服务器数据返回后执行的操作
 */
function afterSubmit(DealFlag, MsgContent, OtherFlag)
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
    //本文件的特殊处理
    if (DealFlag == "succ" || DealFlag == "success")
    {
        try
        {
            checkEdorPrint();
            top.opener.easyQueryClickSelf();
        }
        catch (ex) {}
    }
}

/*============================================================================*/

/**
* showCodeList 的回调函数
*/
function afterCodeSelect(sCodeListType, oCodeListField)
{
    sCodeListType = sCodeListType.toLowerCase();
    if (sCodeListType == "edornoticeidea")
    {
        if (oCodeListField.value == "2")
        {
            try
            {

                document.all("divPayNotice").style.display = "";
                    document.all('ApproveContent').value = "";
            }
            catch (ex) {}
        }
        else
        {
            try
            {
                document.all("divPayNotice").style.display = "none";
            }
            catch (ex) {}
        }
         if (oCodeListField.value == "1")
        {
            try
            {

                document.all("divPayPassNotice").style.display = "";
                    document.all('ApproveContent').value = "";
            }
            catch (ex) {}
        }
        else
        {
            try
            {
                document.all("divPayPassNotice").style.display = "none";
            }
            catch (ex) {}
        }
    } //EdorApproveReason
}

/*============================================================================*/

/**
 * 返回主界面
 */
function returnParent()
{
    try
    {
        top.close();
        top.opener.focus();
    }
    catch (ex) {}
}



function InvaliNotice()
{
    var tContNo    = document.all('OtherNo').value;
    var EdorAcceptNo    = document.all('EdorAcceptNo').value;
     mySql=new SqlClass();
    mySql.setResourceName("bq.EdorNotice");
    mySql.setSqlId("EdorNoticeSql5");
    mySql.addSubPara(tContNo); 
    mySql.addSubPara(EdorAcceptNo);     
    var sResult;
    sResult = easyExecSql(mySql.getString(), 1, 0,"","",1);
    if(sResult == null){
        alert("查询保全拒绝通知书信息失败,请先选择函件类型以及给出函件内容");
        return;
    }
    document.all('PrtSeq').value = sResult[0][0];
    fm.action = "./EdorNoticePrintSave.jsp?";
    fm.target = "f1print";
    fm.submit();
}

function InvaliPassNotice()
{
    var tContNo    = document.all('OtherNo').value;
    var EdorAcceptNo    = document.all('EdorAcceptNo').value;
     mySql=new SqlClass();
    mySql.setResourceName("bq.EdorNotice");
    mySql.setSqlId("EdorNoticeSql4");
    mySql.addSubPara(tContNo); 
    mySql.addSubPara(EdorAcceptNo);       
   
    var sResult;
    sResult = easyExecSql(mySql.getString(), 1, 0,"","",1);
    if(sResult == null){
        alert("查询保全审核通知书信息失败,请先选择函件类型以及给出函件内容");
        return;
    }
    document.all('PrtSeq').value = sResult[0][0];
    fm.action = "./EdorNoticePrintSave.jsp?";
    fm.target = "f1print";
    fm.submit();
}

function ApproveCancel()
{
    document.all('ApproveFlag').value = "";
    document.all('ApproveContent').value = "";
}

/**
*  保全复核提交
*  描述: 保全撤销提交
*/
function ApproveSubmit()
{
    if (fm.EdorAcceptNo.value == "" )
    {
        alert("无法获取保全受理号码信息。保全函件处理失败！");
        return;
    }
    var tApproveFlag        = fm.ApproveFlag.value;
    var tApproveContent     = fm.ApproveContent.value;


     if ((tApproveFlag == "" || tApproveFlag == null))
    {
        alert("请选择函件类型!");
        return;
    }
          
     if ((tApproveFlag == "2" || tApproveFlag == "1") && (tApproveContent == null || tApproveContent.trim() == ""))
    {
        alert("请录入函件内容！ ");
        return;
    }
    //逻辑
     mySql=new SqlClass();
    mySql.setResourceName("bq.EdorNotice");
    mySql.setSqlId("EdorNoticeSql6");
    mySql.addSubPara(fm.OtherNo.value);    
    mySql.addSubPara(fm.EdorAcceptNo.value);  
    var sResult;
    sResult = easyExecSql(mySql.getString(), 1, 0,"","",1);
    if(sResult&&document.all('ApproveFlag').value=='1' ){
        alert("此项目已经保全下发审核结论，不可以重复下发，请返回");
        return;
    }else if(sResult&&document.all('ApproveFlag').value=='2')
    	{
        alert("此项目有尚未回收的函件，不可以保全拒绝，请返回");
        return;   		    		
    	}
    	
    //BQ33 保全终止通知书，BQ37 保全审核通知书
    mySql=new SqlClass();
    mySql.setResourceName("bq.EdorNotice");
    mySql.setSqlId("EdorNoticeSql7");
    mySql.addSubPara(fm.OtherNo.value);    
    mySql.addSubPara(fm.EdorAcceptNo.value);     
    sResult = easyExecSql(mySql.getString(), 1, 0,"","",1);
    if(sResult&&document.all('ApproveFlag').value=='1' ){
        alert("此项目已经保全终止，不可以在进行审核，请返回");
        return;
    }else if(sResult&&document.all('ApproveFlag').value=='2')
    {
    	  alert("此项目已经保全下发审核结论，不可以重复下发，请返回");
        return;   	
    }
    if(document.all('ApproveFlag').value=='2')
    {
    	if(!confirm("下发保全拒绝通知书将会使本次项目完全终止，是否继续"))
    	{
    		  return;   	
    		}
    }
               

    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
   // showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action = "./EdorNoticeSave.jsp";
    fm.target="fraSubmit";  //提交给制定的框架
    fm.submit();
}

function InitApproveContent()
{
	  var tContNo    = document.all('OtherNo').value;
    var EdorAcceptNo    = document.all('EdorAcceptNo').value;
     mySql=new SqlClass();
    mySql.setResourceName("bq.EdorNotice");
    mySql.setSqlId("EdorNoticeSql2");
    mySql.addSubPara(tContNo);
    mySql.addSubPara(EdorAcceptNo);    
    var sResult;
    sResult = easyExecSql(mySql.getString(), 1, 0,"","",1);
    if(sResult){
       fm.ApproveContent.value=sResult[0][0];
    }	
	
	}