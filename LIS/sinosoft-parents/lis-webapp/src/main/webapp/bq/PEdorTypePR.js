//该文件中包含客户端需要处理的函数和事件

var showInfo;
var turnPage = new turnPageClass();
var turnPagePolGrid = new turnPageClass();
var addrFlag="MOD";
var sqlresourcename = "bq.PEdorTypePRInputSql";

function edorTypePRSave()
{
	//add by jiaqiangli 2009-04-28 增加提示：处于应交日
	var QuerySQL, arrResult;
//    QuerySQL = "select 'X' from lcpol WHERE contno='"+document.all('ContNo').value+"' and appflag='1' and paytodate<=to_date('"+document.all('EdorItemAppDate').value+"','YYYY-MM-DD') ";
    
	var sqlid1="PEdorTypePRInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(document.all('ContNo').value);//指定传入的参数
	mySql1.addSubPara(document.all('EdorItemAppDate').value);
	QuerySQL=mySql1.getString();
    
    arrResult = easyExecSql(QuerySQL, 1, 0);
    if (arrResult != null) {
    	if (window.confirm("提示：在应交日后申请迁移的，应当先交纳当期保费！") == false) {
    		return;
    	}
    }
	//add by jiaqiangli 2009-04-28 增加提示：处于应交日
	
    if (!isValidManageCom())    return;
    searchOtherContTheAppntAssociated();
    document.all('addrFlag').value=addrFlag;
    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    document.all('fmtransact').value="INSERT||GRPEDORTYPEPR";
    fm.submit();
}

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
            top.opener.getEdorItemGrid();
        }
        catch (ex) {}
    }
}

/**
 * 检查输入的管理机构是否合法
 * XinYQ added on 2007-03-02
 */
function isValidManageCom()
{
    if(!verifyInput())
    {
      return false;
    }
    var sManageCom;
    try
    {
        sManageCom = document.getElementsByName("ManageCom")[0].value;
    }
    catch (ex) {}
    if (sManageCom == null || sManageCom.trim() == "")
    {
        alert("迁至管理机构不能为空！ ");
        return false;
    }
    else
    {
        var QuerySQL, arrResult;
/*        QuerySQL = "select 'X' "
                 +   "from LDCom "
                 +  "where 1 = 1 "
                 +     getWherePart("ComCode", "ManageCom");
        //alert(QuerySQL);
*/        
    var sqlid2="PEdorTypePRInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	mySql2.addSubPara(fm.ManageCom.value);//指定传入的参数
	QuerySQL=mySql2.getString();
        
        try
        {
            arrResult = easyExecSql(QuerySQL, 1, 0);
        }
        catch (ex)
        {
            alert("警告：查询迁至管理机构信息出现异常！ ");
            return;
        }
        if (arrResult == null)
        {
            document.getElementsByName("ManageCom")[0].className = "warnno";
            alert("迁至管理机构不存在。请重新双击下拉选择！ ");
            document.getElementsByName("ManageCom")[0].value = "";
            document.getElementsByName("ManageCom")[0].className = "codeno";
            return false;
        }
    }
    return true;
}

function searchOtherContTheAppntAssociated()
{
   try
   {
         var i;
         var tFlag1 = 0;
         var tFlag2 = 0;
         var content1 = "以当前投保人为投保人的其它保单有：";
         var content2 = "以当前投保人为被保人的其它保单有：";
//         var tSql = "select a.contno from lccont a, lccont  b where a.appntno = b.appntno and a.contno <> b.contno and b.contno = '" + document.all('ContNo').value + "'";

	var tSql = "";
	var sqlid3="PEdorTypePRInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql3.setSqlId(sqlid3);//指定使用的Sql的id
	mySql3.addSubPara(document.all('ContNo').value);//指定传入的参数
	tSql=mySql3.getString();

       var arrResult1 = easyExecSql(tSql,1,0);
       if ((arrResult1!=null) && (arrResult1.length!=0))
       {
          tFlag1 = 1;
          for (i=0;i<arrResult1.length;i++)
          {
               content1 = content1 + arrResult1[i][0] + " ";
          }
       }
       var tFlag = 0;
       var content = "";
       if (tFlag1==1)
       {
          tFlag = 1;
          content = content1;
       }
          //alert(tFlag);
       if (tFlag==1)
       {
            alert(content);
         }
         return;
  }
  catch(ex)
  {
     alert("查询当前投保人与其它保单的关联时产生错误!");
     return;
  }
}

/*********************************************************************
 *  显示frmSubmit框架，用来调试
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showSubmitFrame(cDebug)
{
    if( cDebug == "1" )
        parent.fraMain.rows = "0,0,50,82,*";
    else
        parent.fraMain.rows = "0,0,0,72,*";
}

function showNotePad()
{
    var MissionID = top.opener.document.all("MissionID").value;
    var SubMissionID = top.opener.document.all("SubMissionID").value;;
    var ActivityID = "0000000003";
    var OtherNo = top.opener.document.all("OtherNo").value;;

    var OtherNoType = "1";
    if(MissionID == null || MissionID == "")
    {
        alert("MissionID为空！");
        return;
    }
    var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
    var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface= ../uw/WorkFlowNotePadInput.jsp" + varSrc,"工作流记事本查看","left");
}

function queryPolInfo()
{
      var tContno=document.all('ContNo').value;
    //alert(tContNo);
    //var tContNo;
    // 书写SQL语句
//    var strSQL ="select InsuredNo,InsuredName,RiskCode,(select RiskName from LMRisk where LMRisk.RiskCode = LCPol.RiskCode),Prem,Amnt,CValiDate,contno,grpcontno from LCPol where ContNo='"+tContno+"'";
  //alert("15663100154");
	var strSQL = "";
	var sqlid4="PEdorTypePRInputSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql4.setSqlId(sqlid4);//指定使用的Sql的id
	mySql4.addSubPara(tContno);//指定传入的参数
	strSQL=mySql4.getString();
	
    //alert(strSQL);
    try
    {
        turnPagePolGrid.queryModal(strSQL, PolGrid);
    }
    catch (ex)
    {
        alert("警告：查询保单险种信息出现异常！ ");
        return;
    }
}

function edorQuery()
{
    var ButtonFlag = top.opener.document.all('ButtonFlag').value;
    if(ButtonFlag!=null && ButtonFlag=="1")
    {
       divEdorquery.style.display = "none";
    }
    else
    {
        divEdorquery.style.display = "";
    }
}

function queryCustomerInfo()
{
//	alert('$$')
    var tContNo=document.all('ContNo').value;
    var strSQL=""
    if(tContNo!=null || tContNo !='')
      {
/*      strSQL = "SELECT APPNTNAME,APPNTIDTYPE,APPNTIDNO,INSUREDNAME,INSUREDIDTYPE,INSUREDIDNO FROM LCCONT WHERE 1=1 AND "
                            +"CONTNO='"+tContNo+"'";
*/    
	var sqlid5="PEdorTypePRInputSql5";
	var mySql5=new SqlClass();
	mySql5.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql5.setSqlId(sqlid5);//指定使用的Sql的id
	mySql5.addSubPara(tContNo);//指定传入的参数
	strSQL=mySql5.getString();
    
    }
    else
    {
        alert('没有客户信息！');
    }
   // alert('$$1'+strSQL)
    var arrSelected = new Array();
    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
    //arrResult = easyExecSql("select * from LDGrp where GrpNo = '" + arrQueryResult[0][0] + "'", 1, 0);
  if(!turnPage.strQueryResult){
        alert("查询失败");
    }
    else
    {
    	//alert('$$22')
  arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
  try {document.all('AppntName').value = arrSelected[0][0];} catch(ex) { }; //投保人姓名
  try {document.all('AppntIDType').value = arrSelected[0][1];} catch(ex) { }; //投保人证件类型
  try {document.all('AppntIDNo').value = arrSelected[0][2];} catch(ex) { }; //投保人证件号码
  try {document.all('InsuredName').value = arrSelected[0][3];} catch(ex) { }; //被保人名称
  try {document.all('InsuredIDType').value = arrSelected[0][4];} catch(ex) { }; //被保人证件类型
  try {document.all('InsuredIDNo').value = arrSelected[0][5];} catch(ex) { }; //被保人证件号码

  showOneCodeName('idtype','AppntIDType','AppntIDTypeName');
  showOneCodeName('idtype','InsuredIDType','InsuredIDTypeName');
   }
}

/**
 * 查询保单迁移之前原始管理机构
 * XinYQ rewrote on 2006-09-07
 */
function queryOldManageCom()
{
    var QuerySQL, arrResult;
/*    QuerySQL = "select a.ManageCom, "
             +        "(select Name from LDCom where ComCode = a.ManageCom) "
             +   "from LCCont a "
             +  "where 1 = 1 "
             +  getWherePart("a.ContNo", "ContNo");
    //alert(QuerySQL);
*/    
    var sqlid6="PEdorTypePRInputSql6";
	var mySql6=new SqlClass();
	mySql6.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql6.setSqlId(sqlid6);//指定使用的Sql的id
	mySql6.addSubPara(fm.ContNo.value);//指定传入的参数
	QuerySQL=mySql6.getString();
    
    try
    {
        arrResult = easyExecSql(QuerySQL, 1, 0);
    }
    catch (ex)
    {
        alert("警告：查询保单迁移之后新的管理机构出现异常！ ");
        return;
    }
    if (arrResult != null)
    {
        try
        {
            document.getElementsByName("OldManageCom")[0].value = arrResult[0][0];
            document.getElementsByName("OldManageComName")[0].value = arrResult[0][1];
        }
        catch (ex) {}
    }
}

/**
 * 查询保单迁移之后新的管理机构
 * XinYQ rewrote on 2006-08-30
 */
function queryNewManageCom()
{
    var QuerySQL, arrResult;
/*    QuerySQL = "select a.ManageCom, "
             +        "(select Name from LDCom where ComCode = a.ManageCom) "
             +   "from LPCont a "
             +  "where 1 = 1 "
             +  getWherePart("a.EdorNo", "EdorNo")
             +  getWherePart("a.EdorType", "EdorType")
             +  getWherePart("a.ContNo", "ContNo");
    //alert(QuerySQL);
*/    
    var sqlid7="PEdorTypePRInputSql7";
	var mySql7=new SqlClass();
	mySql7.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql7.setSqlId(sqlid7);//指定使用的Sql的id
	mySql7.addSubPara(fm.EdorNo.value);//指定传入的参数
	mySql7.addSubPara(fm.EdorType.value);
	mySql7.addSubPara(fm.ContNo.value);
	QuerySQL=mySql7.getString();
    
    try
    {
        arrResult = easyExecSql(QuerySQL, 1, 0);
    }
    catch (ex)
    {
        alert("警告：查询保单迁移之后新的管理机构出现异常！ ");
        return;
    }
    if (arrResult != null)
    {
        try
        {
            document.getElementsByName("ManageCom")[0].value = arrResult[0][0];
            document.getElementsByName("ManageComName")[0].value = arrResult[0][1];
        }
        catch (ex) {}
    }
}

/**
 * 查询保单迁移管理机构
 * XinYQ rewrote on 2006-08-30
 */
function initComCode()
{
    var QuerySQL, strResult;
    //modify by jiaqiangli 2008-10-09 MS支持到8位管理机构
//    QuerySQL = "select ComCode, Name from LDCom where length(trim(ComCode)) = 8 and comcode <> '"+document.all('OldManageCom').value+"' order by ComCode asc";
    //alert(QuerySQL);
    
    var sqlid8="PEdorTypePRInputSql8";
	var mySql8=new SqlClass();
	mySql8.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql8.setSqlId(sqlid8);//指定使用的Sql的id
	mySql8.addSubPara(document.all('OldManageCom').value);//指定传入的参数
	QuerySQL=mySql8.getString();
    
    try
    {
        strResult = easyQueryVer3(QuerySQL, 1, 0, 1, 0, 1);
    }
    catch (ex)
    {
        alert("警告：查询保单迁移管理机构信息出现异常！ ");
        return;
    }
    if (strResult != null && trim(strResult) != "")
    {
        try
        {
            document.getElementsByName("ManageCom")[0].CodeData = strResult;
        }
        catch (ex) {}
    }
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
