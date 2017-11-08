//程序名称：EdorCancel.js
//程序功能：保全撤销
//创建日期：2005-05-08 09:20:22
//创建人  ：zhangtao
//更新记录：  更新人    更新日期     更新原因/内容

var showInfo;
var turnPage = new turnPageClass();


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
    //本文件的特殊处理
    if (DealFlag == "succ" || DealFlag == "success")
    {
        try
        {
            top.opener.querySelfGrid();
        }
        catch (ex) {}
    }
}

/*********************************************************************
 *  查询保全申请信息
 *  描述: 查询保全申请信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function initQuery()
{

      var EdorAcceptNo  = document.all('EdorAcceptNo').value;
      var MissionID         = document.all('MissionID').value
      var SubMissionID  = document.all('SubMissionID').value

      if(EdorAcceptNo == null || EdorAcceptNo == "")
      {
          alert("保全受理号为空！");
          return;
      }
      if(MissionID == null || MissionID == "")
      {
          alert("任务号为空！");
          return;
      }
      if(SubMissionID == null || SubMissionID == "")
      {
          alert("子任务号为空！");
          return;
      }

      var strSQL;

    //查询保全申请信息
    var sqlid902104137="DSHomeContSql902104137";
var mySql902104137=new SqlClass();
mySql902104137.setResourceName("bq.GEdorCancelInputSql");//指定使用的properties文件名
mySql902104137.setSqlId(sqlid902104137);//指定使用的Sql的id
mySql902104137.addSubPara(EdorAcceptNo);//指定传入的参数
strSQL=mySql902104137.getString();
    
//    strSQL =  " select OtherNo, (select trim(code)||'-'||trim(codename) from ldcode where codetype = 'gedornotype' and code = OtherNoType), "
//            + " GetMoney,EdorAppName, "
//            + " (select trim(code)||'-'||trim(codename) from ldcode where codetype = 'edorapptype' and code = Apptype), "
//            + " (select trim(code)||'-'||trim(codename) from ldcode where codetype = 'station' and code = ManageCom),edorstate,othernotype, "
//            + " GetInterest "
//            + " from LPEdorApp "
//            + " where EdorAcceptNo = '" + EdorAcceptNo + "' ";

    var brr = easyExecSql(strSQL);

    if ( brr )
    {
        brr[0][0]==null||brr[0][0]=='null'?'0':fm.OtherNo.value     = brr[0][0];
        brr[0][1]==null||brr[0][1]=='null'?'0':fm.OtherNoTypeName.value = brr[0][1];
        brr[0][2]==null||brr[0][2]=='null'?'0':fm.GetMoney.value    = brr[0][2];
        brr[0][3]==null||brr[0][3]=='null'?'0':fm.EdorAppName.value = brr[0][3];
        brr[0][4]==null||brr[0][4]=='null'?'0':fm.Apptype.value     = brr[0][4];
        brr[0][5]==null||brr[0][5]=='null'?'0':fm.ManageCom.value   = brr[0][5];
        brr[0][6]==null||brr[0][6]=='null'?'0':fm.EdorState.value   = brr[0][6];
        brr[0][7]==null||brr[0][7]=='null'?'0':fm.OtherNoType.value = brr[0][7];
        brr[0][8]==null||brr[0][8]=='null'?'0':fm.GetInterest.value = brr[0][8];
    }
    else
    {
        alert("保全申请查询失败！");
        return;
    }

    //showEdorMainList();    <!-- XinYQ commented on 2006-02-09 -->

}

/*********************************************************************
 *  查询保全项目列表
 *  描述: 查询保全项目列表
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
//function showEdorItemList()
//{
//  var tSel = EdorMainGrid.getSelNo() - 1;
//
//  var EdorNo = EdorMainGrid.getRowColData(tSel, 1);
//  var ContNo = EdorMainGrid.getRowColData(tSel, 2);
//  var EdorMainState = EdorMainGrid.getRowColData(tSel, 10);
//  document.all('EdorNo').value = EdorNo;
//  document.all('ContNo').value = ContNo;
//  document.all('EdorMainState').value = EdorMainState;
//
//  var strSQL;
//  strSQL =  " select EdorNo,"
//          + " (select distinct edorcode||'-'||edorname from lmedoritem m where  trim(m.edorcode) = trim(edortype)), "
//          + " ContNo,  InsuredNo, "
//          + " PolNo, EdorAppDate, EdorValiDate, "
//          + " nvl(ChgPrem, 0), nvl(ChgAmnt, 0), nvl(GetMoney, 0), nvl(GetInterest, 0),  "
//          + " (select c.codename from ldcode c where c.codetype = 'edorstate' and trim(c.code)=trim(EdorState)), "
//          + " EdorState, "
//          + " (select d.codename from ldcode d where d.codetype = 'edoruwstate' and trim(d.code)=trim(UWFlag)), "
//          + " UWFlag, makedate, maketime, edortype "
//          + " from LPEdorItem "
//          + " where LPEdorItem.EdorNo = '" + EdorNo + "'" ;
//  var drr = easyExecSql(strSQL);
//  if ( !drr )
//  {
//      document.all('DelFlag').value = "EdorMain";
//      initEdorItemGrid();
//      divEdorItemInfo.style.display='none';
//      //alert("申请批单下没有保全项目！");
//      return;
//  }
//  else
//  {
//      //document.all('DelFlag').value = "EdorItem";
//      initEdorItemGrid();
//      turnPage.queryModal(strSQL, EdorItemGrid);
//      divEdorItemInfo.style.display='';
//  }
//}

/*********************************************************************
 *  查询保全批单列表
 *  描述: 查询保全批单列表
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
//function showEdorMainList()
//{
//  var EdorAcceptNo    = document.all('EdorAcceptNo').value;
//
//  //查询保全批改信息
//  strSQL =  " select a.EdorNo, a.ContNo, "
//              + " (select paytodate from lcpol p where p.contno = a.contno and p.polno = p.mainpolno), "
//              + " a.EdorAppDate, a.EdorValiDate, "
//              + " nvl(a.ChgPrem, 0), nvl(a.ChgAmnt, 0), nvl(a.GetMoney, 0), nvl(a.GetInterest, 0), "
//              + " (select c.codename from ldcode c where c.codetype = 'edorstate' and trim(c.code)=trim(a.EdorState)), "
//              + " a.EdorState, "
//              + " (select d.codename from ldcode d where d.codetype = 'edoruwstate' and trim(d.code)=trim(a.uwstate)), "
//              + " a.UWState "
//              + " from LPEdorMain a , LCCont b "
//              + " where 1=1 and a.ContNo  = b.ContNo "
//              + " and a.EdorAcceptNo = '" + EdorAcceptNo + "' ";
//  var crr = easyExecSql(strSQL);
//  if ( !crr )
//  {
//      document.all('DelFlag').value = "EdorApp";
//      initEdorMainGrid();
//      divEdorMainInfo.style.display='none';
//      //alert("保全申请下没有批单，可以直接撤销保全申请！");
//      return;
//  }
//  else
//  {
//      document.all('DelFlag').value = "EdorMain";
//      initEdorMainGrid();
//      turnPage.queryModal(strSQL, EdorMainGrid);
//      divEdorMainInfo.style.display='';
//  }
//}

/*********************************************************************
 *  取出保全项目信息
 *  描述: 取出保全项目信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
//function getEdorItemInfo()
//{
//  var tSel = EdorItemGrid.getSelNo() - 1;
//  document.all('DelFlag').value = "EdorItem";
//
//  fm.EdorNo.value         = EdorItemGrid.getRowColData(tSel, 1);
//  fm.EdorType.value       = EdorItemGrid.getRowColData(tSel, 18);
//  fm.ContNo.value         = EdorItemGrid.getRowColData(tSel, 3);
//  fm.InsuredNo.value      = EdorItemGrid.getRowColData(tSel, 4);
//  fm.PolNo.value          = EdorItemGrid.getRowColData(tSel, 5);
//  fm.EdorItemState.value  = EdorItemGrid.getRowColData(tSel, 13);
//  fm.MakeDate.value       = EdorItemGrid.getRowColData(tSel, 16);
//  fm.MakeTime.value       = EdorItemGrid.getRowColData(tSel, 17);
//
//  //alert(fm.EdorNo.value);
//  //alert(fm.ContNo.value);
//  //alert(fm.EdorType.value);
//  //alert(fm.InsuredNo.value);
//  //alert(fm.PolNo.value);
//}


//<!-- XinYQ added on 2006-02-09 : 按需求修改为批单项目信息一起显示 : BGN -->
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

/*
 * 查询保全批单和批改项目信息
 */
function queryAppItemGrid()
{
    var sqlid902104251="DSHomeContSql902104251";
var mySql902104251=new SqlClass();
mySql902104251.setResourceName("bq.GEdorCancelInputSql");//指定使用的properties文件名
mySql902104251.setSqlId(sqlid902104251);//指定使用的Sql的id
mySql902104251.addSubPara(fm.EdorAcceptNo.value);//指定传入的参数
var QuerySQL=mySql902104251.getString();
    
//    var QuerySQL =  " select EdorNo, "
//                     +  " (select distinct edorcode||'-'||edorname from lmedoritem m where m.appobj = 'G' and  trim(m.edorcode) = trim(edortype)), "
//                     +  " GrpContNo, "
//                 +  " EdorValiDate, nvl(GetMoney,0.00), nvl(GetInterest,0.00), "
//                 +  " (select c.codename from ldcode c where c.codetype = 'edorstate' and trim(c.code)=trim(EdorState)), "
//                 +  " (select CodeName "
//                 +           "from LDCode "
//                 +          "where CodeType = 'edoruwstate' and trim(code) = UWFlag) "
//                 +  " from LPGrpEdorItem "
//                 +  " where EdorAcceptNo = '" + fm.EdorAcceptNo.value + "'" ;
    try
    {
        turnPage.queryModal(QuerySQL, EdorItemGrid);
    }
    catch (ex)
    {
        alert("警告：查询保全批单和批改项目信息出现异常！ ");
        return;
    }
}

/*============================================================================*/
//<!-- XinYQ added on 2006-02-09 : 按需求修改为批单项目信息一起显示 : END -->

/*********************************************************************
 *  保全撤销提交
 *  描述: 保全撤销提交
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function cSubmit()
{

    var DelFlag = document.all('DelFlag').value;
    if (DelFlag == "EdorApp")
    {
        if (fm.EdorAcceptNo.value == "" )
        {
            alert("保全申请号为空！ ");
            return;
        }
    }
    //else if (DelFlag == "EdorMain")
    //{
    //  if (fm.EdorNo.value == null || fm.EdorNo.value == "" ||
    //      fm.ContNo.value == null || fm.ContNo.value == "" )
    //  {
    //      alert("请选择要撤销的申请批单！");
    //      return false;
    //  }
    //}
    //else if (DelFlag == "EdorItem")
    //{
    //  if (fm.EdorNo.value == null     || fm.EdorNo.value == "" ||
    //      fm.EdorType.value == null   || fm.EdorType.value == "" ||
    //      fm.ContNo.value == null     || fm.ContNo.value == "" ||
    //      fm.InsuredNo.value == null  || fm.InsuredNo.value == "" ||
    //      fm.PolNo.value == null      || fm.PolNo.value == "" )
    //  {
    //      if(window.confirm("确定要删除该批单下所有保全项目吗?"))
    //      {
    //          document.all('DelFlag').value = "EdorMain";
    //      }
    //      else
    //      {
    //          return false;
    //      }
    //
    //  }
    //}

    var tCancelReasonCode         = fm.CancelReasonCode.value;
    var tCancelReasonContent  = fm.CancelReasonContent.value;
    //tCancelReasonCode = "t";
    //tCancelReasonContent = "t";
    if(tCancelReasonCode == null || tCancelReasonCode == "")
    {
      alert("请选择撤销原因！ ");
      return ;
    }

    if(tCancelReasonContent == null || tCancelReasonContent == "")
    {
        alert("请录入撤销详细情况！ ");
        return ;
    }


    if (!verifyInput2()) return;    //XinYQ added on 2006-02-09
      var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
      var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
      //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
      document.all('ActionFlag').value = "CSUBMIT";
      fm.action = "./PEdorAppCancelSubmit.jsp";
      document.getElementById("fm").submit();
}

/*********************************************************************
 *  保全撤销取消
 *  描述: 保全撤销取消
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function cCancel()
{
      document.all('CancelReasonCode').value = "";
      document.all('CancelReasonContent').value = "";
}

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
