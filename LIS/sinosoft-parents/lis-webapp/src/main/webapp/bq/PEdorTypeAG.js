//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var theFirstValue="";
var theSecondValue="";
var str_sql = "",sql_id = "", my_sql = "";
//<!-- XinYQ added on 2005-12-08 : BGN -->
/*============================================================================*/

/**
 * showCodeList 的回调函数, 银行帐户信息录入层的显示或隐藏
 */
function afterCodeSelect(sCodeListType, oCodeListField)
{
    if (sCodeListType == "GetLocation")
    {
        try
        {
            if (oCodeListField.value == "0")
                document.all("BankInfo").style.display = "";
            else
                document.all("BankInfo").style.display = "none";
        }
        catch (ex)
        {
            alert("警告：银行帐户信息录入显示/隐藏出现异常！ ");
        }
    } //CodeListType == LiveInquiryResult
}

/*============================================================================*/

/**
 * 查询保单险种信息
 */
function queryPolInfo()
{
    var QuerySQL, arrResult;
//    QuerySQL = "select a.RiskCode, "
//             +        "(select RiskName "
//             +           "from LMRisk "
//             +          "where RiskCode = a.RiskCode), "
//             +        "a.CValiDate, "
//             +        "a.PayToDate, "
//             +        "a.Prem "
//             +   "from LCPol a "
//             +  "where 1 = 1 "
//             +  getWherePart("a.ContNo", "ContNo")
//             +  getWherePart("a.PolNo", "PolNo");
    //alert(QuerySQL);
    sql_id = "PEdorTypeAGSql1";
    my_sql = new SqlClass();
    my_sql.setResourceName("bq.PEdorTypeAGSql"); //指定使用的properties文件名
    my_sql.setSqlId(sql_id);//指定使用的Sql的id
    my_sql.addSubPara(window.document.getElementsByName("ContNo")[0].value);//指定传入的参数
    my_sql.addSubPara(window.document.getElementsByName("PolNo")[0].value);//指定传入的参数
    QuerySQL = my_sql.getString();
    try
    {
        arrResult = easyExecSql(QuerySQL, 1, 0);
    }
    catch (ex)
    {
        alert("警告：查询保单险种信息出现异常！ ");
        return;
    }
    if (arrResult != null)
    {
        try
        {
            document.getElementsByName("RiskCode")[0].value = arrResult[0][0];
            document.getElementsByName("RiskName")[0].value = arrResult[0][1];
            document.getElementsByName("CValiDate")[0].value = arrResult[0][2];
            document.getElementsByName("PayToDate")[0].value = arrResult[0][3];
            document.getElementsByName("Prem")[0].value = arrResult[0][4];
        }
        catch (ex) {}
    } //arrResult != null
}

/*============================================================================*/

function saveEdorTypeAG()
{
    //<!-- XinYQ added on 2005-12-19 : END -->
    if (!verifyInput2()) return;
    if (!checkBankInfo()) return;
    if (!checkAnnuityRisk())  return;
    //<!-- XinYQ added on 2005-12-19 : END -->


    //alert('尚未连接到后台程序......');
    if (PolGrid.mulLineCount == 0)
    {
        alert("目前还没有领取项目！ ");
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
	
    document.forms(0).action = "PEdorTypeAGSubmit.jsp";
    document.forms(0).submit();
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
			var iHeight=250;     //弹出窗口的高度; 
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
			var iHeight=250;     //弹出窗口的高度; 
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
            queryBackFee();
        }
        catch (ex) {}
    }
}


//重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm()
{
    try
    {
        initForm();
    }
    catch (ex) {}
}

//取消按钮对应操作
function cancelForm()
{
    showDiv(operateButton,"true");
    showDiv(inputButton,"false");
}

//提交前的校验、计算
function beforeSubmit()
{
  //添加操作
}

//Click事件，当点击增加图片时触发该函数
function addClick()
{
  //下面增加相应的代码
  showDiv(operateButton,"false");
  showDiv(inputButton,"true");
}

//Click事件，当点击“修改”图片时触发该函数
function updateClick()
{
  //下面增加相应的代码
  alert("update click");
}

//Click事件，当点击“查询”图片时触发该函数
function queryClick()
{
  //下面增加相应的代码
    alert("query click");
      //查询命令单独弹出一个模态对话框，并提交，和其它命令是不同的
  //因此，表单中的活动名称也可以不用赋值的
}

//Click事件，当点击“删除”图片时触发该函数
function deleteClick()
{
  //下面增加相应的代码
  alert("delete click");
}
//---------------------------
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
function showBankDiv()
{
    if(document.all('GoonGetMethod').value=='0')
    {
        BankInfo.style.display = "";
        //document.all('Bank').value="";
        //document.all('Account').value="";
        //document.all('AccName').value="";
    }
    if(document.all('GoonGetMethod').value=='1' || document.all('GoonGetMethod').value=='2' )
    {
        BankInfo.style.display = "none";

    }
}


//<!-- XinYQ modified on 2005-11-08 : BGN -->
/*============================================================================*/

/*
 * 续期领取形式-银行转帐-银行账户信息查询
 */
function queryBankInfo()
{
    var QuerySQL, arrResult;
//    QuerySQL = "select GetForm, GetBankCode, GetBankAccNo, GetAccName "
//             +   "from LPPol "
//             +  "where 1 = 1 "
//             +  getWherePart("EdorType", "EdorType")
//             +  getWherePart("EdorNo", "EdorNo")
//             +  getWherePart("ContNo", "ContNo")
//             +  getWherePart("PolNo", "PolNo");
    sql_id = "PEdorTypeAGSql2";
    my_sql = new SqlClass();
    my_sql.setResourceName("bq.PEdorTypeAGSql"); //指定使用的properties文件名
    my_sql.setSqlId(sql_id);//指定使用的Sql的id
    my_sql.addSubPara(window.document.getElementsByName("EdorType")[0].value);//指定传入的参数
    my_sql.addSubPara(window.document.getElementsByName("EdorNo")[0].value);//指定传入的参数
    my_sql.addSubPara(window.document.getElementsByName("ContNo")[0].value);//指定传入的参数
    my_sql.addSubPara(window.document.getElementsByName("PolNo")[0].value);//指定传入的参数
    QuerySQL = my_sql.getString();
    try
    {
        arrResult = easyExecSql(QuerySQL, 1, 0);
    }
    catch (ex)
    {
        alert("警告：查询客户银行账户信息出现异常！ ");
        return;
    }
    if (arrResult != null && trim(arrResult[0][0]) != "")
    {
        try
        {
            //领取形式
            document.getElementsByName("GoonGetMethod")[0].value = arrResult[0][0];
            showOneCodeName('GetLocation','GoonGetMethod','GoonGetMethodName');
            //开户银行
            document.getElementsByName("GetBankCode")[0].value = arrResult[0][1];
            showOneCodeName('Bank', 'GetBankCode', 'BankName');
            //银行帐户
            document.getElementsByName("GetBankAccNo")[0].value = arrResult[0][2];
            //帐户名
            if (trim(arrResult[0][3]) != "")
                document.getElementsByName("GetAccName")[0].value = arrResult[0][3];
            else
                document.getElementsByName("GetAccName")[0].value = document.getElementsByName("InsuredName")[0].value;
        }
        catch (ex)
        {
            alert("错误：查询客户银行账户信息成功, 但显示到页面失败！ ");
            return;
        }
    } //arrResult[0][1]) != ""
    //查询 LCPol
    else
    {
//        QuerySQL = "select GetForm, GetBankCode, GetBankAccNo, GetAccName "
//                 +   "from LCPol "
//                 +  "where 1 = 1 "
//                 +  getWherePart("ContNo", "ContNo")
//                 +  getWherePart("PolNo", "PolNo");
        sql_id = "PEdorTypeAGSql3";
        my_sql = new SqlClass();
        my_sql.setResourceName("bq.PEdorTypeAGSql"); //指定使用的properties文件名
        my_sql.setSqlId(sql_id);//指定使用的Sql的id
        my_sql.addSubPara(window.document.getElementsByName("ContNo")[0].value);//指定传入的参数
        my_sql.addSubPara(window.document.getElementsByName("PolNo")[0].value);//指定传入的参数
        QuerySQL = my_sql.getString();
        try
        {
            arrResult = easyExecSql(QuerySQL, 1, 0);
        }
        catch (ex)
        {
            alert("警告：查询客户银行账户信息出现异常！ ");
            return;
        }
        try
        {
            //领取形式
            document.getElementsByName("GoonGetMethod")[0].value = arrResult[0][0];
            showOneCodeName('GetLocation','GoonGetMethod','GoonGetMethodName');
            //开户银行
            document.getElementsByName("GetBankCode")[0].value = arrResult[0][1];
            showOneCodeName('Bank', 'GetBankCode', 'BankName');
            //银行帐户
            document.getElementsByName("GetBankAccNo")[0].value = arrResult[0][2];
            //帐户名
            if (trim(arrResult[0][3]) != "")
                document.getElementsByName("GetAccName")[0].value = arrResult[0][3];
            else
                document.getElementsByName("GetAccName")[0].value = document.getElementsByName("InsuredName")[0].value;
        }
        catch (ex)
        {
            alert("错误：查询客户银行账户信息成功, 但显示到页面失败！ ");
            return;
        }
    } //arrResult[0][1]) == ""
    //层的显示
    if (arrResult != null && trim(arrResult[0][0]) == "0")
        try { document.all("BankInfo").style.display = ""; } catch (ex) {}
    else
        try { document.all("BankInfo").style.display = "none"; } catch (ex) {}
}

/*============================================================================*/

/*
 * 续期领取形式-银行转帐-银行账户信息必录校验
 */
function checkBankInfo()
{
    var sGetForm, sGetBankCode, sGetBankAccNo, sGetAccName;
    try
    {
        sGetForm = document.getElementsByName("GoonGetMethod")[0].value;
        sGetBankCode = document.getElementsByName("GetBankCode")[0].value;
        sGetBankAccNo = document.getElementsByName("GetBankAccNo")[0].value;
        sGetAccName = document.getElementsByName("GetAccName")[0].value;
    }
    catch (ex)
    {
        alert("警告：无法获取续期领取形式和银行账户信息！ ");
        return false;
    }
    if (sGetForm == "0")
    {
        if (sGetBankCode == null || trim(sGetBankCode) == "" || sGetBankAccNo == null || trim(sGetBankAccNo) == "" || sGetAccName == null || trim(sGetAccName) == "")
        {
            alert("续期领取形式为银行转帐, 必须录入开户银行、银行帐户、帐户名！ ");
            return false;
        }
    }
    return true;
}

/*============================================================================*/

/*
 * 年金险必须录入续期领取形式
 */
function checkAnnuityRisk()
{
    var CanBeBlank = true;
    var sGetForm;
    try
    {
        sGetForm = document.getElementsByName("GoonGetMethod")[0].value;
    }
    catch (ex) {}
    if (sGetForm == null || trim(sGetForm) == "")
    {
        var nMulLineCount;
        try
        {
            nMulLineCount = PolGrid.mulLineCount;
        }
        catch (ex) {}
        if (nMulLineCount == null || nMulLineCount <= 0)
        {
            //alert("没有领取项目！ ");
            return false;
        }
        else
        {
            var sContNo = document.getElementsByName("ContNo")[0].value;
            for (var i = 0; i < nMulLineCount; i++)
            {
                var sGetDutyType = PolGrid.getRowColData(i, 7);
                if (sGetDutyType == "1")    //是年金
                {
                    //按照需求修改为校验年金的非最后一期
                    var sGetDutyCode = PolGrid.getRowColData(i, 6);
//                    var QuerySQL = "select 'X' "
//                                 +   "from LCGet "
//                                 +  "where ContNo = '" + sContNo + "' "
//                                 +    "and GetIntv <> 0 "
//                                 +    "and GetToDate < add_months(GetEndDate, 11) "
//                                 +    "and GetDutyCode = '" + sGetDutyCode + "'";
                    var QuerySQL = "";
                    sql_id = "PEdorTypeAGSql4";
                    my_sql = new SqlClass();
                    my_sql.setResourceName("bq.PEdorTypeAGSql"); //指定使用的properties文件名
                    my_sql.setSqlId(sql_id);//指定使用的Sql的id
                    my_sql.addSubPara(sContNo);//指定传入的参数
                    my_sql.addSubPara(sGetDutyCode);//指定传入的参数
                    QuerySQL = my_sql.getString();
                    var arrResult;
                    try { arrResult = easyExecSql(QuerySQL, 1, 0); }
                    catch (ex)
                    {
                        alert("警告：查询保单是否年金出现异常！ ");
                        return false;
                    }
                    if (arrResult != null)
                    {
                        CanBeBlank = false;
                        break;
                    }
                } //sGetDutyType == "1"
            } //loop for
        } //nMulLineCount > 0
    } //sGetForm == null
    if (!CanBeBlank)
    {
        try { document.getElementsByName("GoonGetMethod")[0].className = "warnno" } catch (ex) {}
        alert("非最后一期的年金必须录入续期领取形式！ ");
        return false;
    }
    //如果是满期金或者不是年金险的最后一期
    try { document.getElementsByName("GoonGetMethod")[0].className = "codeno" } catch (ex) {}
    return true;
}

/*============================================================================*/
//<!-- XinYQ modified on 2005-11-08 : END -->


//验证文本框中两次输入的值是否相等
function confirmSecondInput1(aObject,aEvent){
    if(aEvent=="onkeyup"){
      var theKey = window.event.keyCode;
      if(theKey=="13"){
        if(theFirstValue!=""){
        theSecondValue = aObject.value;
          if(theSecondValue==""){
            alert("请再次录入！");
            aObject.value="";
            aObject.focus();
            return;
          }
          if(theSecondValue==theFirstValue){
            aObject.value = theSecondValue;
            return;
          }
          else{
            alert("两次录入结果不符，请重新录入！");
            theFirstValue="";
            theSecondValue="";
            aObject.value="";
            aObject.focus();
            return;
          }
        }
      else{
        theFirstValue = aObject.value;
        if(theFirstValue==""){
            theSecondValue="";
          alert("请录入内容！");
          return;
        }
        aObject.value="";
        aObject.focus();
        return;
      }
    }
  }
  else if(aEvent=="onblur"){
      //alert("theFirstValue="+theFirstValue);
      if(theFirstValue!=""){
        theSecondValue = aObject.value;
        if(theSecondValue==""){
            alert("请再次录入！");
          aObject.value="";
          aObject.focus();
          return;
        }
        if(theSecondValue==theFirstValue){
          aObject.value = theSecondValue;
          theSecondValue="";
          theFirstValue="";
          return;
        }
        else{
          alert("两次录入结果不符，请重新录入！");
          theFirstValue="";
          theSecondValue="";
          aObject.value="";
          aObject.focus();
          return;
        }
      }
    else{
        theFirstValue = aObject.value;
        theSecondValue="";
        if(theFirstValue==""){
        //alert("aa");
          return;
        }
        aObject.value="";
        aObject.focus();
      return;
    }
  }
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

//获得领取方式，如果是银行则将帐户信息带出
//function formatGetModeAbout()
//{
//  var tSql="SELECT GetForm,GetBankCode,GetBankAccNo,GetAccName"
//              + " FROM ((select '1' as flag,GetForm,GetBankCode,GetBankAccNo,GetAccName"
//                           + " from LPPol"
//                           + " where EdorNo='" + document.all('EdorNo').value + "' and EdorType='" + document.all('EdorType').value + "' and PolNo='" + document.all('PolNo').value + "')"
//                          + " union"
//                          + " (select '2' as flag,GetForm,GetBankCode,GetBankAccNo,GetAccName"
//                           + " from LCPol"
//                           + " where PolNo='" + document.all('PolNo').value + "'))"
//              + " WHERE rownum=1"
//              + " ORDER BY flag";
//  //alert(tSql);
//  var arrResult=easyExecSql(tSql,1,0);
//  try{document.all('GoonGetMethod').value= arrResult[0][0];}catch(ex){};
//  showOneCodeName('getlocation','GoonGetMethod','GoonGetMethodName');
//  try{document.all('GetBankCode').value= arrResult[0][1];}catch(ex){};
//  showOneCodeName('bank','GetBankCode','BankName');
//  try{document.all('GetBankAccNo').value= arrResult[0][2];}catch(ex){};
//  try{document.all('GetAccName').value= arrResult[0][3];}catch(ex){};
//  if (document.all('GoonGetMethod').value == '0')
//  {
//      BankInfo.style.display = "";
//  }
//  else
//  {
//      BankInfo.style.display = "none";
//  }
//}

function QueryEdorInfo()
{
    var tEdortype=top.opener.document.all('EdorType').value;
    var strSQL;
    if(tEdortype!=null || tEdortype !='')
    {
//       strSQL="select distinct edorcode, edorname from lmedoritem where edorcode = '" + tEdortype + "'";
       sql_id = "PEdorTypeAGSql5";
       my_sql = new SqlClass();
       my_sql.setResourceName("bq.PEdorTypeAGSql"); //指定使用的properties文件名
       my_sql.setSqlId(sql_id);//指定使用的Sql的id
       my_sql.addSubPara(tEdortype);//指定传入的参数
       strSQL = my_sql.getString();
    }
    else
    {
        alert('未查询到保全批改项目信息！');
    }
    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
    var arrSelected = new Array();
    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
    arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
    try {document.all('EdorType').value = arrSelected[0][0];} catch(ex) { }; //职业类别
    try {document.all('EdorTypeName').value = arrSelected[0][1];} catch(ex) { }; //职业类别
}

function Edorquery()
{
   try{
    var ButtonFlag = top.opener.document.all('ButtonFlag').value;
    if(ButtonFlag!=null && ButtonFlag=="1")
    {
       divEdorquery.style.display = "none";
    }

    var ButtonFlag2 = top.opener.top.opener.document.all('ButtonFlag').value;
    if(ButtonFlag2!=null && ButtonFlag2=="1")
    {
       divEdorquery.style.display = "none";
    }
    else
    {
        divEdorquery.style.display = "";
    }
  }catch(ex){};
}

function queryCustomerInfo()
{
    var tContNo=document.all('ContNo').value;
    var strSQL=""
    //alert("------"+tContNo+"---------");
    if(tContNo!=null || tContNo !='')
      {
//      strSQL = "SELECT APPNTNAME,APPNTIDTYPE,APPNTIDNO,INSUREDNAME,INSUREDIDTYPE,INSUREDIDNO FROM LCCONT WHERE 1=1 AND "
//                            +"CONTNO='"+tContNo+"'";
      sql_id = "PEdorTypeAGSql6";
      my_sql = new SqlClass();
      my_sql.setResourceName("bq.PEdorTypeAGSql"); //指定使用的properties文件名
      my_sql.setSqlId(sql_id);//指定使用的Sql的id
      my_sql.addSubPara(tContNo);//指定传入的参数
      strSQL = my_sql.getString();
    //alert("-----------"+strSQL+"------------");
    }
    else
    {
        alert('没有客户信息！');
    }
    var arrSelected = new Array();
    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
    //arrResult = easyExecSql("select * from LDGrp where GrpNo = '" + arrQueryResult[0][0] + "'", 1, 0);
    if(!turnPage.strQueryResult)
    {
        alert("查询失败");
    }
    else
    {
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

function queryPolGrid()
{
    //显示符合标准的领取项信息
//    var strSql="SELECT distinct (select distinct GetDutyName from LMDutyGetAlive where GetDutyCode=b.GetDutyCode and GetDutyKind=b.GetDutyKind),"
//                    + " (select count(*) + 1 from LJAGetDraw where PolNo=b.PolNo and DutyCode=b.DutyCode and GetDutyKind=b.GetDutyKind and GetDutyCode=b.GetDutyCode),"
//                    + " b.GetMoney,b.GetNoticeNo,b.DutyCode,b.GetDutyCode,b.GetDutyKind,b.RiskCode,"
//                    + " (case when (select (case GetType1 when '0' then '满期金' when '1' then '年金' else '未知' end) from LMDutyGet where GetDutyCode=b.GetDutyCode) is not null then (select (case GetType1 when '0' then '满期金' when '1' then '年金' else '未知' end) from LMDutyGet where GetDutyCode=b.GetDutyCode) else '无记录' end)"
//            + " FROM LJSGetDraw b"
//            + " WHERE b.PolNo='" + document.all('PolNo').value + "'"
//            + " and (b.RReportFlag='1' or b.ComeFlag='1')"
//            + " and b.GetDate<=to_date('" + document.all('EdorValiDate').value + "','YYYY-MM-DD')"
//            //+ " and not exists(select 'X' from LCPol where PolNo=b.PolNo and GetForm='1')"
//                        + " and not exists(select 'A' from LCContState where ContNo=b.ContNo and StateType='Loan' and State='1' and EndDate is null and StartDate<=b.GetDate)"
//                        + " and not exists(select 'B' from LCContState where PolNo='" + document.all('PolNo').value + "' and StateType='PayPrem' and State='1' and EndDate is null and StartDate<=b.GetDate)";
    var strSql="";
    sql_id = "PEdorTypeAGSql7";
    my_sql = new SqlClass();
    my_sql.setResourceName("bq.PEdorTypeAGSql"); //指定使用的properties文件名
    my_sql.setSqlId(sql_id);//指定使用的Sql的id
    my_sql.addSubPara(document.all('PolNo').value);//指定传入的参数
    my_sql.addSubPara(document.all('EdorValiDate').value);//指定传入的参数
    my_sql.addSubPara(document.all('PolNo').value);//指定传入的参数
    strSql = my_sql.getString();
    //alert(strSql);
    turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1);
    //判断是否查询成功
    if (!turnPage.strQueryResult)
    {
        return false;
    }
    //查询成功则拆分字符串，返回二维数组
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    //设置初始化过的MULTILINE对象
    turnPage.pageDisplayGrid = PolGrid;
    //保存SQL语句
    turnPage.strQuerySql = strSQL;
    //设置查询起始位置
    turnPage.pageIndex = 0;
    //在查询结果数组中取出符合页面显示大小设置的数组
    arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
    //调用MULTILINE对象显示查询结果
    displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
    //计算领取金额
    var sumGetMoney = 0; //领取金额的和
    for (var i = 0; i < PolGrid.mulLineCount; i++)
    {
        sumGetMoney = sumGetMoney + eval(PolGrid.getRowColData(i, 3));
    }
    document.all('MoneyAdd').value = sumGetMoney;
}

function queryBonusGrid()
{
    //显示符合标准的领取项信息
//    var strSql="SELECT  b.GetDate,b.BonusYear,b.GetMoney,'红利'"
//            + " FROM ljabonusget b"
//            + " WHERE b.otherno='" + document.all('PolNo').value + "'"
//            + " and b.GetDate<=to_date('" + document.all('EdorValiDate').value + "','YYYY-MM-DD')"
//            + " and Enteraccdate is null and Confdate is null and state='0'";
    //alert(strSql);
    var strSql="";
    sql_id = "PEdorTypeAGSql8";
    my_sql = new SqlClass();
    my_sql.setResourceName("bq.PEdorTypeAGSql"); //指定使用的properties文件名
    my_sql.setSqlId(sql_id);//指定使用的Sql的id
    my_sql.addSubPara(document.all('PolNo').value);//指定传入的参数
    my_sql.addSubPara(document.all('EdorValiDate').value);//指定传入的参数
    strSql = my_sql.getString();
    turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1);
    //判断是否查询成功
    if (!turnPage.strQueryResult)
    {
        return false;
    }
    //查询成功则拆分字符串，返回二维数组
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    //设置初始化过的MULTILINE对象
    turnPage.pageDisplayGrid = BonusGrid;
    //保存SQL语句
    turnPage.strQuerySql = strSQL;
    //设置查询起始位置
    turnPage.pageIndex = 0;
    //在查询结果数组中取出符合页面显示大小设置的数组
    arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
    //调用MULTILINE对象显示查询结果
    displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

    //计算领取金额
    var sumGetBonusMoney = 0; //领取金额的和
    for (var i = 0; i < BonusGrid.mulLineCount; i++)
    {
            sumGetBonusMoney = sumGetBonusMoney + eval(BonusGrid.getRowColData(i, 3));
    }
    document.all('MoneyAdd').value = parseFloat(document.all('MoneyAdd').value) + sumGetBonusMoney;
    if (BonusGrid.mulLineCount > 0)
    {
        try
        {
            document.all("BonusInfo").style.display = "";
        }
        catch (ex) {}
    }
    else
    {
        try
        {
            document.all("BonusInfo").style.display = "none";
        }
        catch (ex) {}
    }
}
