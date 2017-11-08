//程序名称：EdorCancel.js
//程序功能：保全撤销
//创建日期：2005-05-08 09:20:22
//创建人  ：sinosoft
//更新记录：  更新人    更新日期     更新原因/内容

var showInfo;
var turnPage = new turnPageClass();
var reasonatrr=""  ;
var sqlresourcename = "bq.EdorCancelInputSql";

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
            if (document.all('ActionFlag').value == "CSUBMIT")
            {
                var DelFlag = fm.DelFlag.value;
                //人工核保确认
                if (DelFlag == "EdorApp")
                {
                    //申请级 DoNothing
                    returnParent();
                }
                else if (DelFlag == "EdorMain")
                {
                    //批单级
                    divEdorMainInfo.style.display='none';
                    divEdorItemInfo.style.display='none';
                    showEdorMainList();

                }
                else if (DelFlag == "EdorItem")
                {
                    //保全项目级
                    showEdorItemList();
                    if (EdorItemGrid.mulLineCount == 0)
                    {
                        showEdorMainList();
                    }
                }
            }
            top.opener.jquerySelfGrid();
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

    var EdorAcceptNo    = document.all('EdorAcceptNo').value;
    var MissionID       = document.all('MissionID').value
    var SubMissionID    = document.all('SubMissionID').value

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
/*    strSQL =  " select OtherNo, (select trim(code)||'-'||trim(codename) from ldcode where codetype = 'edornotype' and code = OtherNoType), "
            + " GetMoney,EdorAppName, "
            + " (select trim(code)||'-'||trim(codename) from ldcode where codetype = 'edorapptype' and code = Apptype), "
            + " (select trim(code)||'-'||trim(codename) from ldcode where codetype = 'station' and code = ManageCom),edorstate,othernotype, "
            + " GetInterest "
            + " from LPEdorApp "
            + " where EdorAcceptNo = '" + EdorAcceptNo + "' ";
*/
	var sqlid1="EdorCancelInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(EdorAcceptNo);//指定传入的参数
	strSQL=mySql1.getString();

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

/*
 * 查询保全批单和批改项目信息
 */
function queryAppItemGrid()
{
/*    var QuerySQL = "select a.EdorNo, "
                 +        "a.ContNo, "
                 +        "a.InsuredNo, "
                 +        "a.PolNo, "
                 +        "a.EdorType || '-' || "
                 +        "(select distinct EdorName "
                 +           "from LMEdorItem "
                 +          "where 1 = 1 "
                 +            "and EdorCode = a.EdorType "
                 +            "and AppObj in ('I', 'B')), "
                 +        "a.EdorAppDate, "
                 +        "a.GetMoney, "
                 +        "(select CodeName "
                 +           "from LDCode "
                 +          "where CodeType = 'edorstate' and trim(code) = a.EdorState), "
                 +        "(select CodeName "
                 +           "from LDCode "
                 +          "where CodeType = 'edoruwstate' and trim(code) = a.UWFlag) "
                 +   "from LPEdorItem a "
                 +  "where 1 = 1 "
                 +  getWherePart("a.EdorAcceptNo", "EdorAcceptNo")
                 +  "order by a.EdorNo asc, a.EdorAppDate asc";
*/    
    var QuerySQL = "";
    var sqlid2="EdorCancelInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	mySql2.addSubPara(fm.EdorAcceptNo.value);//指定传入的参数
	QuerySQL=mySql2.getString();
    
    try
    {
        turnPage.queryModal(QuerySQL, AppItemGrid);
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
    reasonatrr="" ;
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

    //var tCancelReasonCode         = fm.CancelReasonCode.value;
    //var tCancelReasonContent  = fm.CancelReasonContent.value;
    //tCancelReasonCode = "t";
    //tCancelReasonContent = "t";
    //if(tCancelReasonCode == null || tCancelReasonCode == "")
    //{
    //  alert("请选择撤销原因！ ");
    //  return ;
    //}
/*
    if(tCancelReasonContent == null || tCancelReasonContent == "")
    {
        alert("请录入撤销备注！ ");
        return ;
    }
*/
    if (!verifyInput2()) return;    //XinYQ added on 2006-02-09
    if(!verifyFormElements())return;
    //return;
    //G．	其他（可支持录入）,备注信息必录项校验
    if(fm.MCanclReason.value==null || fm.MCanclReason.value =="")
    {
      alert("请选择的撤销原因");
      return ;
    	
    	}
    
    if(fm.MCanclReason.value=="G" && fm.CancelReasonContent.value=="")
    {
    	alert("选择的撤销原因为其它，请录入撤销备注！ ");
      return ;
    }
    //alert(fm.ReasonAtrr.value) ;
    //return ;
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


/**
 * 直接进入保全项目明细
 */
function EdorDetailQuery()
{
    var nSelNo, sEdorType, sEdorState,sEdorNo;
    try
    {
        nSelNo = AppItemGrid.getSelNo() - 1;
    }
    catch (ex) {}
    if (nSelNo == null || nSelNo < 0)
    {
        alert("请先选择您要查看的申请项目！ ");
        return;
    }
    else
    {
    	  sEdorNo = AppItemGrid.getRowColData(nSelNo, 1);
//    	  var strSql = "select edorstate,edortype,contno,EdorAppDate,EdorValiDate from lpedoritem where edorno='"+sEdorNo+"'  and EdorAcceptNo='"+fm.EdorAcceptNo.value+"'";
        
    var strSql = "";
    var sqlid3="EdorCancelInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql3.setSqlId(sqlid3);//指定使用的Sql的id
	mySql3.addSubPara(sEdorNo);//指定传入的参数
	mySql3.addSubPara(fm.EdorAcceptNo.value);
	strSql=mySql3.getString();
        
        var arrResult = easyExecSql(strSql);
        if(arrResult==null || arrResult.length<=0)
        {
        	 alert("此次申请没有保存过信息！！ ");
           return;
        }
        try
        {
            sEdorType = arrResult[0][1];
            sEdorState = arrResult[0][0];
            if(sEdorState=="3")
            {
            	alert("此次申请没有保存过信息！！ ");
              return;
            }
            
            document.all('InsuredNo').value = AppItemGrid.getRowColData(nSelNo, 3);
            document.all('EdorNo').value = sEdorNo;
            document.all('ContNo').value = arrResult[0][2];
            document.all('EdorMainState').value = sEdorState;
            document.all('EdorType').value = sEdorType;
            document.all('EdorState').value = sEdorState;
            document.all('EdorAppDate').value = arrResult[0][3];
            document.all('EdorItemAppDate').value = arrResult[0][3];
            document.all('EdorValiDate').value = arrResult[0][4];
    
        }
        catch (ex) {}
        if (sEdorType == null || sEdorType.trim() == "")
        {
            alert("警告：无法获取保全批改项目类型信息。查询保全明细失败！ ");
            return;
        }
        //alert(sEdorType);
        if (sEdorState != null && trim(sEdorState) == "0")
        {
            OpenWindowNew("../bqs/PEdorType" + sEdorType.trim() + ".jsp", "保全项目明细查询", "left");    //bqs
        }
        else
        {
            OpenWindowNew("../bq/PEdorType" + sEdorType.trim() + ".jsp", "保全项目明细查询", "left");    //bq
        }
    }
}

function afterCodeSelect(sCodeListType, oCodeListField)
{
    sCodeListType = sCodeListType.toLowerCase();
    if (sCodeListType == "edorcancelmreason")
    {
    	
    	
    	      try
            {
            	   var str = new Array('A','B','C','D','E','F','G');
                //str=('A','B','C','D','E','F','G');
                //alert(str);
                for(var t=0;t<str.length;t++)
                {
                	//alert("divAppCancel"+str[t]);
                	if(str[t]!=oCodeListField.value)
                	{
                		 document.all("divAppCancel"+str[t]).style.display = "none";
                	}
               }
                document.all("divAppCancel"+oCodeListField.value).style.display = "";
                
            }
            catch (ex) {}

    } //EdorApproveReason
}


function verifyFormElements()
{
	
	  var selectedIndex = -1;
    var i = 0;
    
    for (i=0; i<fm.SCanclReason.length; i++)
    {
        if (fm.SCanclReason[i].checked)
        {
            selectedIndex = i;
            //alert("您选择项的 value 是：" + fm.SCanclReason[i].value);
            fm.CancelReasonCode.value=fm.SCanclReason[i].value;
            return true;
        }
    }    
    if (selectedIndex < 0)
    {
        alert("您没有选择任何撤销原因");
        return false ;
    }

}      
 