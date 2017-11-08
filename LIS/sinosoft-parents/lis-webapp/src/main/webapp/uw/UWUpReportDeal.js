//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug = "0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();
//使用翻页功能，必须建立为全局变量
var turnPage2 = new turnPageClass();

//modify by lzf
function ApplyUW()
{

    var selno = PublicWorkPoolGrid.getSelNo() - 1;
    if (selno < 0)
    {
        alert("请选择要申请的投保单！");
        return;
    }
    fm.MissionID.value = PublicWorkPoolGrid.getRowColData(selno, 10);
    fm.SubMissionID.value = PublicWorkPoolGrid.getRowColData(selno, 11);
    fm.ActivityID.value = PublicWorkPoolGrid.getRowColData(selno, 13);

    var i = 0;
    var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

   showInfo.focus();

    fm.action = "../uw/ManuUWAllChk.jsp";
    document.getElementById("fm").submit();
    //提交
    showApproveDetail();
}

function showApproveDetail() {

    var selno = PublicWorkPoolGrid.getSelNo() - 1;
    if (selno < 0)
    {
        alert("请选择要复核的投保单！");
        return;
    }
    var tMissionID = PublicWorkPoolGrid.getRowColData(selno, 10);
    var tSubMissionID = PublicWorkPoolGrid.getRowColData(selno, 11);
    var tActivityID = PublicWorkPoolGrid.getRowColData(selno, 13);
    var tPreSeq = PublicWorkPoolGrid.getRowColData(selno, 4);
    var polNo = PublicWorkPoolGrid.getRowColData(selno, 1);
    
    var sqlid901100508="DSHomeContSql901100508";
	var mySql901100508=new SqlClass();
	mySql901100508.setResourceName("uw.UWUpReportDealInputSql");//指定使用的properties文件名
	mySql901100508.setSqlId(sqlid901100508);//指定使用的Sql的id
	mySql901100508.addSubPara(polNo);//指定传入的参数
	var strSql=mySql901100508.getString();
    
    var arrResult = easyExecSql(strSql);
    var urlStr = "../common/jsp/UnLockTable.jsp?PrtNo=" + polNo + "&CreatePos=承保复核&PolState=1003&Action=INSERT";
    showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;resizable:1");

    //配合以前实现过的页面功能，源于ProposalMain.jsp
    mSwitch.deleteVar("PolNo");
    mSwitch.addVar("PolNo", "", polNo);
    mSwitch.updateVar("PolNo", "", polNo);

    mSwitch.deleteVar("ApprovePolNo");
    mSwitch.addVar("ApprovePolNo", "", polNo);
    mSwitch.updateVar("ApprovePolNo", "", polNo);
    easyScanWin = window.open("./UWUpReportDealEachMain.jsp?ContNo=" + polNo + "&MissionID=" + tMissionID + "&SubMissionID=" + tSubMissionID + "&ActivityID="+ tActivityID +"&PrtSeq=" + tPreSeq, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");

}


function afterSubmit(FlagStr, content)
{
    showInfo.close();

    //解除印刷号的锁定
  //  var prtNo = PolGrid.getRowColData(PolGrid.getSelNo() - 1, 2);
    //var urlStr = "../common/jsp/UnLockTable.jsp?PrtNo=" + prtNo + "&CreatePos=承保复核&PolState=1003&Action=DELETE";
   // showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;resizable:1");

    if (FlagStr == "Fail")
    {
        var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    }

    // 刷新查询结果
    jQuery("#privateSearch").click();
    jQuery("#publicSearch").click();
}

function InitshowApproveDetail() {

    var selno = PrivateWorkPoolGrid.getSelNo() - 1;
    if (selno < 0)
    {
        alert("请选择要复核的投保单！");
        return;
    }
   
    var tMissionID = PrivateWorkPoolGrid.getRowColData(selno, 10);
    var tSubMissionID = PrivateWorkPoolGrid.getRowColData(selno, 11);
    var tActivityID = PrivateWorkPoolGrid.getRowColData(selno, 13);
    var tPrtSeq = PrivateWorkPoolGrid.getRowColData(selno, 4);
    var polNo = PrivateWorkPoolGrid.getRowColData(selno, 1);
   // alert("tActivityID=="+tActivityID);
		
	var sqlid901100628="DSHomeContSql901100628";
	var mySql901100628=new SqlClass();
	mySql901100628.setResourceName("uw.UWUpReportDealInputSql");//指定使用的properties文件名
	mySql901100628.setSqlId(sqlid901100628);//指定使用的Sql的id
	mySql901100628.addSubPara(polNo);//指定传入的参数
	var strSql=mySql901100628.getString();		
    var arrResult = easyExecSql(strSql);

    //配合以前实现过的页面功能，源于ProposalMain.jsp
    mSwitch.deleteVar("PolNo");
    mSwitch.addVar("PolNo", "", polNo);
    mSwitch.updateVar("PolNo", "", polNo);
     mSwitch.deleteVar("ApprovePolNo");
    mSwitch.addVar("ApprovePolNo", "", polNo);
    mSwitch.updateVar("ApprovePolNo", "", polNo);
   // alert(mSwitch.getVar("ApprovePolNo"));

    easyScanWin = window.open("./UWUpReportDealEachMain.jsp?ContNo=" + polNo + "&MissionID=" + tMissionID + "&SubMissionID=" + tSubMissionID + "&ActivityID="+ tActivityID + "&PrtSeq=" + tPrtSeq);
    //easyScanWin = window.open("./RReportDealEachMain.jsp?ContNo="+polNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID);   

}

function queryAgent(gridName,row,col){
	if (gridName.indexOf("Public") != -1) {
		flag = "public";
	} else if (gridName.indexOf("Private") != -1) {
		flag = "private";
	}
	if (document.all('AgentCode').value == "") {
		var newWindow = window
				.open(
						"../sys/AgentCommonQueryMain.jsp?queryflag=" + flag
								+ "&ManageCom=" + document.all('ManageCom').value
								+ "&row="+row+"&col="+col  );
	}
}

	function afterQuery2(arrResult, queryFlag,row,col) {
		if (arrResult != null) {
			if (queryFlag == "public") {
				PublicWorkPoolQueryGrid.setRowColData(row, col, arrResult[0][0]);
			} else if (queryFlag == "private") {
				PrivateWorkPoolQueryGrid.setRowColData(row, col, arrResult[0][0]);
			}
		}
	}

//end by lzf
/*********************************************************************
 *  进行投保单复核提交
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function approvePol1()
{
    var tSel = PolGrid.getSelNo();
    if (tSel == null || tSel == 0)
        alert("请选择一张投保单后，再进行复核操作");
    else
    {
        var i = 0;
        var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
        var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
        //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	   showInfo.focus();

        showSubmitFrame(mDebug);
        document.getElementById("fm").submit();
        //提交
    }
}

/*********************************************************************
 *  投保单复核的提交后的操作,服务器数据返回后执行的操作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
/*function afterSubmit(FlagStr, content)
{
    showInfo.close();

    //解除印刷号的锁定
  //  var prtNo = PolGrid.getRowColData(PolGrid.getSelNo() - 1, 2);
    //var urlStr = "../common/jsp/UnLockTable.jsp?PrtNo=" + prtNo + "&CreatePos=承保复核&PolState=1003&Action=DELETE";
   // showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;resizable:1");

    if (FlagStr == "Fail")
    {
        var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    }

    // 刷新查询结果
    easyQueryClick();
    easyQueryClickSelf();
}

/*********************************************************************
 *  显示div
 *  参数  ：  第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
 *  返回值：  无
 *********************************************************************
 */
function showDiv(cDiv, cShow)
{
    if (cShow == "true")
        cDiv.style.display = "";
    else
        cDiv.style.display = "none";
}

/*********************************************************************
 *  显示frmSubmit框架，用来调试
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showSubmitFrame(cDebug)
{
    if (cDebug == "1")
        parent.fraMain.rows = "0,0,50,82,*";
    else
        parent.fraMain.rows = "0,0,0,72,*";
}

/*********************************************************************
 *  显示投保单明细信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showPolDetail()
{
    var i = 0;
    var checkFlag = 0;

    for (i = 0; i < PolGrid.mulLineCount; i++) {
        if (PolGrid.getSelNo(i)) {
            checkFlag = PolGrid.getSelNo();
            break;
        }
    }

    if (checkFlag) {
        var cPolNo = PolGrid.getRowColData(checkFlag - 1, 1);

        mSwitch.deleteVar("PolNo");
        mSwitch.addVar("PolNo", "", cPolNo);

        window.open("./ProposalMain.jsp?LoadFlag=6", "window1","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
    }
    else {
        alert("请先选择一条保单信息！");
    }
}

/*********************************************************************
*  调用EasyQuery查询保单
*  参数  ：  无
*  返回值：  无
*********************************************************************
*/

function easyQueryClick()
{
    // 初始化表格
    initPolGrid();

    // 书写SQL语句
    var strSql = "";

    //strSql = "select lwmission.missionprop1,'',lwmission.missionprop2,lwmission.makedate,lwmission.Missionid,lwmission.submissionid,lwmission.activityid,lwmission.missionprop8,lwmission.missionprop6 from lwmission where 1=1"
//    strSql = "select (select prtno from lccont where contno=LCReinsureReport.contno),"
//            + " '',"
//            + " lwmission.missionprop2,"
//            + " lwmission.makedate,"
//            + " lwmission.Missionid,"
//            + " lwmission.submissionid,"
//            + " lwmission.activityid,"
//            + " LCReinsureReport.usercode,"
//            + " lwmission.missionprop6,"            
//            + " (select insuredname from lccont where contno=LCReinsureReport.contno),"
//           // + " (select riskcode from lcpol where polno=mainpolno and prtno=LCReinsureReport.contno),"
//            + "'',"
//            + " (select decode(min(c.makedate),null,'无扫描件',to_char(min(c.makedate),'yyyy-mm-dd')) from es_doc_main c where c.doccode=(select prtno from lccont where contno=LCReinsureReport.contno)) ScanDate,"//扫描日期
//            + " lwmission.missionprop1"
//            + " from lwmission, LCReinsureReport"
//            + " where 1 = 1"
//            + " and lwmission.missionprop1 = trim(LCReinsureReport.contno)"
//            + " and lwmission.activityid = '0000001140'"
//            + " and lwmission.processid = '0000000003'"
//           // + getWherePart('lwmission.MissionProp1', 'ProposalNo')                       
//            + getWherePart('lwmission.MissionProp2', 'AgentCode')
//    //+ getWherePart('lwmission.MissionProp3','AgentCode')
//            + getWherePart('lwmission.MissionProp6', 'ManageCom')
//            + getWherePart('lwmission.MakeDate', 'MakeDate', '=')
//            + " and LWMission.MissionProp6 like '" + ComCode + "%%'"  //集中权限管理体现
//            + " and lwmission.defaultoperator is null ";
//            if(fm.PrtNo.value!=null && fm.PrtNo.value!='')
//              strSql = strSql+ " and exists(select 1 from lccont where prtno='"+fm.PrtNo.value+"' and contno=LCReinsureReport.contno)";
//            strSql = strSql+ " order by lwmission.modifydate asc,lwmission.modifytime asc";

    //SQL改造
    if(fm.PrtNo.value!=null && fm.PrtNo.value!=''){
    	var sqlid901095819="DSHomeContSql901095819";
		var mySql901095819=new SqlClass();
		mySql901095819.setResourceName("uw.UWUpReportDealInputSql");//指定使用的properties文件名
		mySql901095819.setSqlId(sqlid901095819);//指定使用的Sql的id
		mySql901095819.addSubPara(fm.AgentCode.value );//指定传入的参数
		mySql901095819.addSubPara(fm.ManageCom.value );//指定传入的参数
		mySql901095819.addSubPara(fm.MakeDate.value);//指定传入的参数
		mySql901095819.addSubPara(ComCode+"%%");//指定传入的参数
		mySql901095819.addSubPara(fm.PrtNo.value);//指定传入的参数
		strSql=mySql901095819.getString();
    }else{
    	var sqlid901100219="DSHomeContSql901100219";
		var mySql901100219=new SqlClass();
		mySql901100219.setResourceName("uw.UWUpReportDealInputSql");//指定使用的properties文件名
		mySql901100219.setSqlId(sqlid901100219);//指定使用的Sql的id
		mySql901100219.addSubPara(fm.AgentCode.value );//指定传入的参数
		mySql901100219.addSubPara(fm.ManageCom.value );//指定传入的参数
		mySql901100219.addSubPara(fm.MakeDate.value);//指定传入的参数
		mySql901100219.addSubPara(ComCode+"%%");//指定传入的参数
		strSql=mySql901100219.getString();
    }
    
    turnPage.queryModal(strSql, PolGrid);
}

function easyQueryClickSelf()
{

    initSelfPolGrid();
    var strSql = "";
    //strSql = "select lwmission.missionprop1,'',lwmission.missionprop2,lwmission.makedate,lwmission.Missionid,lwmission.submissionid,lwmission.activityid,lwmission.missionprop7,lwmission.missionprop6 from lwmission where 1=1"
    
    var sqlid901100348="DSHomeContSql901100348";
	var mySql901100348=new SqlClass();
	mySql901100348.setResourceName("uw.UWUpReportDealInputSql");//指定使用的properties文件名
	mySql901100348.setSqlId(sqlid901100348);//指定使用的Sql的id
	mySql901100348.addSubPara(Operator);//指定传入的参数
	strSql=mySql901100348.getString();
    
//    strSql = "select (select prtno from lccont where contno=LCReinsureReport.contno),"
//            + " '',"
//            + " lwmission.missionprop2,"
//            + " lwmission.makedate,"
//            + " lwmission.Missionid,"
//            + " lwmission.submissionid,"
//            + " lwmission.activityid,"
//            + " LCReinsureReport.usercode,"
//            + " lwmission.missionprop6,"
//          //  + " '',''"
//            + " (select insuredname from lccont where contno=LCReinsureReport.contno),"
//          //  + " (select riskcode from lcpol where polno=mainpolno and prtno=LCReinsureReport.contno),"
//            + "'',"
//            + " (select decode(min(c.makedate),null,'无扫描件',to_char(min(c.makedate),'yyyy-mm-dd')) from es_doc_main c where c.doccode=(select prtno from lccont where contno=LCReinsureReport.contno)) ScanDate,"//扫描日期
//            + " lwmission.missionprop1"
//            + " from lwmission, LCReinsureReport"
//            + " where 1 = 1"
//            + " and lwmission.missionprop1 = trim(LCReinsureReport.contno)"
//            + " and lwmission.activityid = '0000001140'"
//            + " and lwmission.processid = '0000000003'"
//            + " and lwmission.defaultoperator ='" + Operator + "'"
//            + " order by lwmission.modifydate asc,lwmission.modifytime asc";

   // alert(strSql);
    turnPage2.queryModal(strSql, SelfPolGrid);
}
/*********************************************************************
 *  显示EasyQuery的查询结果
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function displayEasyResult(arrResult)
{
    var i, j, m, n;

    if (arrResult == null)
        alert("没有找到相关的数据!");
    else
    {
        // 初始化表格
        initPolGrid();
        //HZM 到此修改
        PolGrid.recordNo = (currBlockIndex - 1) * MAXMEMORYPAGES * MAXSCREENLINES + (currPageIndex - 1) * MAXSCREENLINES;
        PolGrid.loadMulLine(PolGrid.arraySave);
        //HZM 到此修改

        arrGrid = arrResult;
        // 显示查询结果
        n = arrResult.length;
        for (i = 0; i < n; i++)
        {
            m = arrResult[i].length;
            for (j = 0; j < m; j++)
            {
                PolGrid.setRowColData(i, j + 1, arrResult[i][j]);
            }
            // end of for
        }
        // end of for
        //alert("result:"+arrResult);

        PolGrid.delBlankLine();
    }
    // end of if
}

/*function ApplyUW()
{

    var selno = PolGrid.getSelNo() - 1;
    if (selno < 0)
    {
        alert("请选择要申请的投保单！");
        return;
    }

    fm.MissionID.value = PolGrid.getRowColData(selno, 5);
    fm.SubMissionID.value = PolGrid.getRowColData(selno, 6);
    fm.ActivityID.value = PolGrid.getRowColData(selno, 7);

    var i = 0;
    var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

    fm.action = "../uw/ManuUWAllChk.jsp";
    document.getElementById("fm").submit();
    //提交
    showApproveDetail();
}

function showApproveDetail() {

    var selno = PolGrid.getSelNo() - 1;
    if (selno < 0)
    {
        alert("请选择要复核的投保单！");
        return;
    }
    var tMissionID = PolGrid.getRowColData(selno, 6);
    var tSubMissionID = PolGrid.getRowColData(selno, 7);
    var tPreSeq = PolGrid.getRowColData(selno, 8);
    var tCustomerNo = PolGrid.getRowColData(selno, 2);
    var polNo = PolGrid.getRowColData(selno, 1);

    
    var sqlid901100508="DSHomeContSql901100508";
var mySql901100508=new SqlClass();
mySql901100508.setResourceName("uw.UWUpReportDealInputSql");//指定使用的properties文件名
mySql901100508.setSqlId(sqlid901100508);//指定使用的Sql的id
mySql901100508.addSubPara(polNo);//指定传入的参数
var strSql=mySql901100508.getString();
    
//    var strSql = "select * from ldsystrace where PolNo='" + prtNo + "' and (CreatePos='承保录单' or CreatePos='承保复核') and (PolState='1002' or PolState='1003')";
    var arrResult = easyExecSql(strSql);
    //if (arrResult!=null && arrResult[0][1]!=Operator) {
    //  alert("该印刷号的投保单已经被操作员（" + arrResult[0][1] + "）在（" + arrResult[0][5] + "）位置锁定！您不能操作，请选其它的印刷号！");
    //  return;
    //}
    //锁定该印刷号
    var urlStr = "../common/jsp/UnLockTable.jsp?PrtNo=" + polNo + "&CreatePos=承保复核&PolState=1003&Action=INSERT";
    showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;resizable:1");


    //配合以前实现过的页面功能，源于ProposalMain.jsp
    mSwitch.deleteVar("PolNo");
    mSwitch.addVar("PolNo", "", polNo);
    mSwitch.updateVar("PolNo", "", polNo);


    mSwitch.deleteVar("ApprovePolNo");
    mSwitch.addVar("ApprovePolNo", "", polNo);
    mSwitch.updateVar("ApprovePolNo", "", polNo);


    easyScanWin = window.open("./UWUpReportDealEachMain.jsp?ContNo=" + polNo + "&MissionID=" + tMissionID + "&SubMissionID=" + tSubMissionID + "&CustomerNo=" + tCustomerNo + "&PrtSeq" + tPreSeq, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");

}

/*function InitshowApproveDetail() {

    var selno = SelfPolGrid.getSelNo() - 1;
    if (selno < 0)
    {
        alert("请选择要复核的投保单！");
        return;
    }
    var tMissionID = SelfPolGrid.getRowColData(selno, 5);
    var tSubMissionID = SelfPolGrid.getRowColData(selno, 6);
    var tCustomerNo = SelfPolGrid.getRowColData(selno, 2);
    var tPrtSeq = SelfPolGrid.getRowColData(selno, 8);

    var polNo = SelfPolGrid.getRowColData(selno, 1);
		
		var sqlid901100628="DSHomeContSql901100628";
var mySql901100628=new SqlClass();
mySql901100628.setResourceName("uw.UWUpReportDealInputSql");//指定使用的properties文件名
mySql901100628.setSqlId(sqlid901100628);//指定使用的Sql的id
mySql901100628.addSubPara(polNo);//指定传入的参数
var strSql=mySql901100628.getString();
		
		
//    var strSql = "select * from ldsystrace where PolNo='" + polNo + "' and (CreatePos='承保录单' or CreatePos='承保复核') and (PolState='1002' or PolState='1003')";
    var arrResult = easyExecSql(strSql);
    // if (arrResult!=null && arrResult[0][1]!=Operator) {
    //   alert("该投保单已经被操作员（" + arrResult[0][1] + "）在（" + arrResult[0][5] + "）位置锁定！您不能操作，请选其它的印刷号！");
    //   return;
    // }
    //锁定该印刷号
   // var urlStr = "../common/jsp/UnLockTable.jsp?PolNo=" + polNo + "&CreatePos=承保复核&PolState=1003&Action=INSERT";
  //  showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;resizable:1");


    //配合以前实现过的页面功能，源于ProposalMain.jsp
    mSwitch.deleteVar("PolNo");
    mSwitch.addVar("PolNo", "", polNo);
    mSwitch.updateVar("PolNo", "", polNo);
    //alert(polNo);

    mSwitch.deleteVar("ApprovePolNo");
    mSwitch.addVar("ApprovePolNo", "", polNo);
    mSwitch.updateVar("ApprovePolNo", "", polNo);
    //alert(mSwitch.getVar("ApprovePolNo"));

    easyScanWin = window.open("./UWUpReportDealEachMain.jsp?ContNo=" + polNo + "&MissionID=" + tMissionID + "&SubMissionID=" + tSubMissionID + "&CustomerNo=" + tCustomerNo + "&PrtSeq=" + tPrtSeq);
    //easyScanWin = window.open("./RReportDealEachMain.jsp?ContNo="+polNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID);   

}

/*********************************************************************
 *  进行投保单复核提交
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function passApprovePol() {
    var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

   showInfo.focus();

    //提交
    var polNo = mSwitch.getVar("ApprovePolNo");
    //alert(polNo);
    window.top.fraSubmit.window.location = "./ProposalApproveSave.jsp?polNo=" + polNo + "&approveFlag=9";
}

function refuseApprovePol() {
    var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

   showInfo.focus();

    showSubmitFrame(mDebug);
    //提交
    var polNo = mSwitch.getVar("ApprovePolNo");
    window.top.fraSubmit.window.location = "./ProposalApproveSave.jsp?polNo=" + polNo + "&approveFlag=1";
}

//************************
var cflag = "5";
//问题件操作位置 5.复核

function QuestInput()
{
    cProposalNo = PolGrid.getRowColData(PolGrid.getSelNo() - 1, 1);
    //保单号码

    //showModalDialog("./QuestInputMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cFlag,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
    window.open("../uw/QuestInputMain.jsp?ContNo=" + cProposalNo + "&Flag=" + cflag, "window1");

    //initInpBox();
    //initPolBox();
    //initPolGrid();

}

function QuestReply()
{
    cProposalNo = PolGrid.getRowColData(PolGrid.getSelNo() - 1, 1);
    //保单号码

    //showModalDialog("./QuestInputMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cFlag,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
    window.open("../uw/QuestReplyMain.jsp?ProposalNo1=" + cProposalNo + "&Flag=" + cflag, "window1");

    //initInpBox();
    //initPolBox();
    //initPolGrid();

}

function QuestQuery()
{
    cProposalNo = PolGrid.getRowColData(PolGrid.getSelNo() - 1, 1);
    //保单号码

    //showModalDialog("./QuestInputMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cFlag,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
    window.open("../uw/QuestQueryMain.jsp?ProposalNo1=" + cProposalNo + "&Flag=" + cflag, "window1");

    //initInpBox();
    //initPolBox();
    //initPolGrid();

}


function returnparent()
{
    var backstr = document.all("ContNo").value;
    //alert(backstr+"backstr");
    mSwitch.deleteVar("ContNo");
    mSwitch.addVar("ContNo", "", backstr);
    mSwitch.updateVar("ContNo", "", backstr);
    location.href = "ContInsuredInput.jsp?LoadFlag=5&ContType=" + ContType;
}


/*function queryAgent()
{
    var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom=" + document.all('ManageCom').value + "&branchtype=1", "AgentCommonQueryMain", 'width=' + screen.availWidth + ',height=' + screen.availHeight + ',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

function afterQuery2(arrResult)
{

    if (arrResult != null)
    {
        fm.AgentCode.value = arrResult[0][0];
    }
}*/
