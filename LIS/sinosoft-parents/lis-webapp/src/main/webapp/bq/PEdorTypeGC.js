//该文件中包含客户端需要处理的函数和事件

var showInfo;
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var turnPageNewBnfGrid = new turnPageClass();      //全局变量, 新受益人查询结果翻页
var sqlresourcename = "bq.PEdorTypeGCInputSql";

function saveEdorTypeGC()
{

//校验受益比例 jiaqiangli-check the rule of bnf-lot
        var sumLiveBnf = new Array();
        var sumDeadBnf = new Array();
        
        var nNewBnfCount = NewBnfGrid.mulLineCount;
        
	    if (nNewBnfCount <= 0) {
	       alert("受益人分配信息不允许为空！");
	       return false;
	    }

        for(var i=0;i<nNewBnfCount;i++)
        {
               if (typeof(sumLiveBnf[parseInt(NewBnfGrid.getRowColData(i,9))]) == "undefined")
               sumLiveBnf[parseInt(NewBnfGrid.getRowColData(i,9))] = 0;
               sumLiveBnf[parseInt(NewBnfGrid.getRowColData(i,9))] = sumLiveBnf[parseInt(NewBnfGrid.getRowColData(i,9))] + parseFloat(NewBnfGrid.getRowColData(i, 10))*100;
        }
        for (i=0; i<sumLiveBnf.length; i++)
      {
        if (typeof(sumLiveBnf[i])!="undefined"){sumLiveBnf[i]=parseFloat(sumLiveBnf[i])/100;}
        if (typeof(sumLiveBnf[i])!="undefined" && sumLiveBnf[i]>1)
        {
            alert("注意：生存受益人受益顺序 " + i + " 的受益比例和为：" + sumLiveBnf[i]+ " 。大于100%，不能提交！");
            return false;
        }
        else if (typeof(sumLiveBnf[i])!="undefined" && sumLiveBnf[i]<1)
        {
            alert("注意：生存受益人受益顺序 " + i + " 的受益比例和为：" + sumLiveBnf[i] + " 。小于100%，不能提交！");
            return false;
        }
      }
//校验受益比例 jiaqiangli-check the rule of bnf-lot
//add by jiaqiangli 银行转帐授权要求与银行相关的信息不允许为空
if(document.all("GetForm").value == "0") {
  for (var i=0;i<nNewBnfCount;i++) {
    if (NewBnfGrid.getRowColData(i, 17)==null || NewBnfGrid.getRowColData(i, 17)==""
       || NewBnfGrid.getRowColData(i, 18)==null || NewBnfGrid.getRowColData(i, 18)==""
       || NewBnfGrid.getRowColData(i, 19)==null || NewBnfGrid.getRowColData(i, 19)=="") {
       alert("受益人分配中第"+(i+1)+"行的银行转帐授权的银行信息不允许为空");
       return false;
    }
    //add by jiaqiangli 2009-03-16 增加户名与受益人姓名一致的校验
    if (NewBnfGrid.getRowColData(i, 4) != NewBnfGrid.getRowColData(i, 19) ) {
    	alert("第"+(i+1)+"行受益人姓名必须与银行帐户名一致");
       return false;
    }
    //add by jiaqiangli 2009-03-16 增加户名与受益人姓名一致的校验
  }
}
//add by jiaqiangli 银行转帐授权要求与银行相关的信息不允许为空

    //<!-- XinYQ added on 2005-12-19 : END -->
    if (!verifyInput2()) return;
    //已经放在mulline中校验了
    //if (!checkBankInfo()) return;
    //<!-- XinYQ added on 2005-12-19 : END -->
    
    //add by jiaqiangli 2009-04-08 mutline的verify校验
     if(!NewBnfGrid.checkValue2())
  	{
  		return;
  	 }
    //add by jiaqiangli 2009-04-08 mutline的verify校验

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
  document.all('fmtransact').value = "UPDATE||MAIN";
  fm.submit();

}

/**
 * 记事本查看
 */
function showNotePad()
{
    var sMissionID, sSubMissionID, sOtherNo;
    try
    {
        sMissionID = top.opener.document.getElementsByName("MissionID")[0].value;
        sSubMissionID = top.opener.document.getElementsByName("SubMissionID")[0].value;
        sOtherNo = top.opener.document.getElementsByName("OtherNo")[0].value;
    }
    catch (ex) {}
    if (sMissionID == null || trim(sMissionID) == "" || sSubMissionID == null || trim(sSubMissionID) == "" || sOtherNo == null || trim(sOtherNo) == "")
    {
        alert("警告：无法获取工作流任务节点任务号。查看记事本失败！ ");
        return;
    }
    var sOpenWinURL = "../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp";
    var sParameters = "MissionID="+ sMissionID + "&SubMissionID="+ sSubMissionID + "&ActivityID=0000000003&PrtNo="+ sOtherNo + "&NoType=1";
    OpenWindowNew(sOpenWinURL + "&" + sParameters, "工作流记事本查看", "left");
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
 * showCodeList 的回调函数, 银行帐户信息录入层的显示或隐藏
 */
 /*
function afterCodeSelect(sCodeListType, oCodeListField)
{
    if (sCodeListType == "GetLocation")
    {
        try
        {
            if (oCodeListField.value == "0")
            {
                document.all("BankInfo").style.display = "";
            }
            else
            {
                document.all("BankInfo").style.display = "none";
            }
        }
        catch (ex)
        {
            alert("警告：银行帐户信息录入显示/隐藏出现异常！ ");
        }
    } //CodeListType == EdorGetPayForm
}
*/

//<!-- XinYQ modified on 2005-11-08 : BGN -->
/*============================================================================*/

/*
 * 领取形式-银行转帐-银行账户信息查询
 */
function queryBankInfo()
{
    var QuerySQL, arrResult;
/*    QuerySQL = "select GetForm, GetBankCode, GetBankAccNo, GetAccName "
             +   "from LPPol "
             +  "where 1 = 1 "
             +     getWherePart("EdorType", "EdorType")
             +     getWherePart("EdorNo", "EdorNo")
             +     getWherePart("ContNo", "ContNo")
             +     getWherePart("PolNo", "PolNo");
    //alert(QuerySQL);
*/    
	var sqlid1="PEdorTypeGCInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(fm.EdorType.value);//指定传入的参数
	mySql1.addSubPara(fm.EdorNo.value);
	mySql1.addSubPara(fm.ContNo.value);
	mySql1.addSubPara(fm.PolNo.value);
	QuerySQL=mySql1.getString();
    
    try
    {
        arrResult = easyExecSql(QuerySQL, 1, 0);
    }
    catch (ex)
    {
        alert("警告：查询客户银行账户信息出现异常！ ");
        return;
    }
    if (arrResult != null && trim(arrResult[0][1]) != "")
    {
        try
        {
            //领取形式
            document.getElementsByName("GetForm")[0].value = arrResult[0][0];
            showOneCodeName('GetLocation','GetForm','GetFormName');
            //开户银行
            document.getElementsByName("GetBankCode")[0].value = arrResult[0][1];
            showOneCodeName('Bank', 'GetBankCode', 'BankName');
            //银行帐户
            document.getElementsByName("GetBankAccNo")[0].value = arrResult[0][2];
            //帐户名
            if (trim(arrResult[0][3]) != "")
            {
                document.getElementsByName("GetAccName")[0].value = arrResult[0][3];
            }
            else
            {
            		if (fm.AppObj.value == 'G')
            		{
            			document.getElementsByName("GetAccName")[0].value = PolGrid.getRowColData(0, 3);
            		}    
            		else
            		{
            			document.getElementsByName("GetAccName")[0].value = PolGrid.getRowColData(1, 3);
        				}
        		}
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
/*        QuerySQL = "select GetForm, GetBankCode, GetBankAccNo, GetAccName "
                 +   "from LCPol "
                 +  "where 1 = 1 "
                 +     getWherePart("ContNo", "ContNo")
                 +     getWherePart("PolNo", "PolNo");
*/       
    var sqlid2="PEdorTypeGCInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	mySql2.addSubPara(fm.ContNo.value);//指定传入的参数
	mySql2.addSubPara(fm.PolNo.value);
	QuerySQL=mySql2.getString();
       
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
            document.getElementsByName("GetForm")[0].value = arrResult[0][0];
            showOneCodeName('GetLocation','GetForm','GetFormName');
            //开户银行
            document.getElementsByName("GetBankCode")[0].value = arrResult[0][1];
            showOneCodeName('Bank', 'GetBankCode', 'BankName');
            //银行帐户
            document.getElementsByName("GetBankAccNo")[0].value = arrResult[0][2];
            //帐户名
            if (trim(arrResult[0][3]) != "")
            {
                document.getElementsByName("GetAccName")[0].value = arrResult[0][3];
            }
            else
            {
                //团单没有投保人，所以第一行即为被保人，没有第二行数据 zhangtao 2007-02-05
                if (fm.AppObj.value == 'G')
                {
                	document.getElementsByName("GetAccName")[0].value = PolGrid.getRowColData(0, 3);
                }
                else
                {
                	document.getElementsByName("GetAccName")[0].value = PolGrid.getRowColData(1, 3);
								}	
            }
        }
        catch (ex)
        {
            alert("错误：查询客户银行账户信息成功, 但显示到页面失败！ ");
            return;
        }
    }
    //层的显示
    if (arrResult != null && trim(arrResult[0][0]) == "0")
    {
        try
        {
            document.all("BankInfo").style.display = "";
        }
        catch (ex) {}
    }
    else
    {
        try
        {
            document.all("BankInfo").style.display = "none";
        }
        catch (ex) {}
    }
}

/*============================================================================*/

/*
 * 领取形式-银行转帐-银行账户信息必录校验
 */
function checkBankInfo()
{
    var sGetForm = document.getElementsByName("GetForm")[0].value;
    var sGetBankCode = document.getElementsByName("GetBankCode")[0].value;
    var sGetBankAccNo = document.getElementsByName("GetBankAccNo")[0].value;
    var sGetAccName = document.getElementsByName("GetAccName")[0].value;
    if (sGetForm == null)
    {
        alert("警告：无法获取领取形式信息！ ");
        return false;
    }
    else if (sGetForm == "0")
    {
        if (sGetBankCode == null || trim(sGetBankCode) == "" || sGetBankAccNo == null || trim(sGetBankAccNo) == "" || sGetAccName == null || trim(sGetAccName) == "")
        {
            alert("领取形式为银行转帐, 必须录入开户银行、银行帐户、帐户名！ ");
            return false;
        }
    }
    return true;
}

/*============================================================================*/
//<!-- XinYQ modified on 2005-11-08 : END -->


//获取首领取形式信息
//function initBankInfo()
//{
//  alert(0);
//   var tEdorNo = document.all('EdorNo').value;
//   var tPolNo = document.all('PolNo').value;
//   var showStr = "正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
//   var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//   showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
//   var strSql = "Select getform,getbankcode,getbankaccno,getaccname From lppol where 1 =1 and PolNo='"+tPolNo+"' and EdorNo='"+tEdorNo+"'";
//   alert(strSql);
//
//   var arrResult = easyExecSql(strSql, 1, 0);
//   if (arrResult == null)
//   {
//       alert("没有相应的保单信息");
//       showInfo.close();
//         document.all('GetBankCode').value = "";
//         document.all('GetBankAccNo').value = "";
//         document.all('GetAccName').value = "";
//         document.all('GetForm').value = "";
//   }
//   else
//   {
//              BankInfo.style.display = "";
//          document.all('GetBankCode').value = arrResult[0][1];
//              document.all('GetBankAccNo').value = arrResult[0][2];
//              document.all('GetAccName').value = arrResult[0][3];
//              document.all('GetForm').value = arrResult[0][0];
//        showInfo.close();
//   }
//   //turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);
//   //document.all('fmtransact').value = "QUERY||PayLocation";
//   //fm.submit();
//      //document.all('GetAccName').value=strSql;
//}

//校验部分
function verify() {
  //if (trim(fm.BankAccNo.value)!=trim(fm.BankAccNoAgain.value)) {
   // alert("两个账号不一致，请确认！");
    //return false;
  //}
  if (trim(fm.GetBankCode.value)=="0101") {
    if (trim(fm.GetBankCode.value).length!=19) {
      alert("工商银行的账号必须是19位的数字，最后一个星号（*）不要！");
      return false;
    }
  }
  //if(trim(document.all('AppntName').value)!=trim(document.all('AccName').value))
  //{
  //        alert("帐户名与投保人姓名不相同!");
  //return false;
  //    }

 if(trim(document.all('AppntName').value)!=trim(document.all('GetAccName').value)){
    if (!confirm("账户名称和投保人名称不一样！按确定继续提交，按取消则返回更改！"))
      return false;
  }

  return true;
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

function Edorquery()
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
  var tContNo=top.opener.document.all('ContNo').value;
    var strSQL="";
    if(tContNo!=null || tContNo !='')
      {
/*      strSQL = " Select a.appntno,'投保人',a.appntname,a.appntsex||'-'||sex.codename,a.appntbirthday,a.idtype||'-'||x.codename,a.idno From lcappnt a "
             + " Left Join (Select code,codename From ldcode Where codetype='idtype') x On x.code = a.idtype "
             + " Left Join (Select code,codename From ldcode Where codetype='sex') sex On sex.code = a.appntsex  Where contno='"+tContNo+"'"
             + " Union"
             + " Select i.insuredno,'被保人',i.name,i.Sex||'-'||sex.codename,i.Birthday,i.IDType||'-'||xm.codename,i.IDNo From lcinsured i "
             + " Left Join (Select code,codename From ldcode Where codetype='idtype') xm On xm.code = i.idtype "
             + " Left Join (Select code,codename From ldcode Where codetype='sex') sex On sex.code = i.sex Where contno='"+tContNo+"'";
*/    
    var sqlid3="PEdorTypeGCInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql3.setSqlId(sqlid3);//指定使用的Sql的id
	mySql3.addSubPara(tContNo);//指定传入的参数
	mySql3.addSubPara(tContNo);
	strSQL=mySql3.getString();
    
    }
    else
    {
        alert('没有相应的投保人或被保人信息！');
    }
    //prompt("strSQL",strSQL);
    var arrSelected = new Array();
    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);

    //判断是否查询成功
    if (!turnPage.strQueryResult) {
  //alert("没有相应的投保人或被保人信息！");
        return false;
    }

    //清空数据容器，两个不同查询共用一个turnPage对象时必须使用，最好加上，容错
    turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
    //查询成功则拆分字符串，返回二维数组
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
    turnPage.pageDisplayGrid = PolGrid;
    //保存SQL语句
    turnPage.strQuerySql = strSQL;
    //设置查询起始位置
    turnPage.pageIndex = 0;
    //在查询结果数组中取出符合页面显示大小设置的数组
    arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
    //调用MULTILINE对象显示查询结果
    displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}

function initSelQuery()
{
     var tEdorNo=top.opener.document.all('EdorNo').value;
     var tEdorType=top.opener.document.all('EdorType').value;
     var tPolNo=top.opener.document.all('PolNo').value;
//     var strSql = "Select AppntNo,AppntName,Prem,Amnt,PayMode,GetBankCode,GetBankAccNo,GetAccName,PayLocation,(select codename from ldcode where codetype = 'getlocation' and trim(code) = trim(getForm)),insuredname From lcpol where 1 =1 and PolNo='"+tPolNo+"'";
   
    var strSql = "";
    var sqlid4="PEdorTypeGCInputSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql4.setSqlId(sqlid4);//指定使用的Sql的id
	mySql4.addSubPara(tPolNo);//指定传入的参数
	strSql=mySql4.getString();
   
   var arrResult = easyExecSql(strSql, 1, 0);
   
   //prompt("strSql",strSql);

   if (arrResult == null)
   {
       alert("没有相应的保单信息");
   }
   else
   {
       displayInfo(arrResult);
   }
}

function displayInfo(arrResult)
{
 try{
   document.all('AppntNo').value = arrResult[0][0];
   document.all('AppntName').value = arrResult[0][1];
   document.all('Prem').value = arrResult[0][2];
   document.all('Amnt').value = arrResult[0][3];
   document.all('PayMode').value = arrResult[0][4];
   document.all('BankCode_S').value = arrResult[0][5];
   document.all('BankAccNo_S').value = arrResult[0][6];
   document.all('AccName_S').value = arrResult[0][7];
   //document.all('GetAccName').value = arrResult[0][10];
   document.all('PayLocation_S').value = arrResult[0][8];
   document.all('GerForm_S').value = arrResult[0][9];
   }catch(ex){
        alert("error!");
   }
}

function newGetType()
{
//   var strsql = "select GetForm,GetBankCode,GetBankAccNo,GetAccName From lppol where 1 =1 and PolNo='"+top.opener.document.all('PolNo').value+"' and EdorNo = '"+top.opener.document.all('EdorNo').value+"' and EdorType = '"+top.opener.document.all('EdorType').value+"'";
   
    var strSql = "";
    var sqlid5="PEdorTypeGCInputSql5";
	var mySql5=new SqlClass();
	mySql5.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql5.setSqlId(sqlid5);//指定使用的Sql的id
	
	mySql5.addSubPara(top.opener.document.all('PolNo').value);//指定传入的参数
	mySql5.addSubPara(top.opener.document.all('EdorNo').value);
	mySql5.addSubPara(top.opener.document.all('EdorType').value);
	strSql=mySql5.getString();
	//begin zbx 20110513
   var aResult = easyExecSql(strSql,1,0);
   //end zbx 20110513
   if(aResult != null){
      document.all('GetForm').value = aResult[0][0];
      //if(aResult[0][0] == "0"){
         //BankInfo.style.display = "";
         //document.all('GetBankCode').value = aResult[0][1];
         //document.all('GetBankAccNo').value = aResult[0][2];
         //document.all('GetAccName').value = aResult[0][3];
      //}
   }
}

function getInsuredCodeList()
{
    var sInsuredCodeList = "";
/*    var QuerySQL = "select * "
                 //被保人
                 +   "from (select a.InsuredNo as CustomerNo, "
                 +                "a.Name as CustomerName "
                 +           "from LCInsured a "
                 +          "where 1 = 1 "
                 +             getWherePart("a.ContNo", "ContNo")
                 +            "and a.InsuredNo = "
                 +                "(select InsuredNo "
                 +                   "from LCPol "
                 +                  "where 1 = 1 "
                 +                     getWherePart("ContNo", "ContNo")
                 +                     getWherePart("PolNo", "PolNo")
                 +                ") "
                 //第二被保人
                 +         "union "
                 +         "select b.InsuredNo as CustomerNo, "
                 +                "b.Name as CustomerName "
                 +           "from LCInsured b "
                 +          "where 1 = 1 "
                 +             getWherePart("b.ContNo", "ContNo")
                 +            "and b.InsuredNo in "
                 +                "(select c.CustomerNo "
                 +                   "from LCInsuredRelated c "
                 +                  "where 1 = 1 "
                 +                     getWherePart("c.PolNo", "PolNo")
                 +                   "and c.MainCustomerNo = "
                 +                       "(select InsuredNo "
                 +                          "from LCPol "
                 +                         "where 1 = 1 "
                 +                            getWherePart("ContNo", "ContNo")
                 +                            getWherePart("PolNo", "PolNo")
                 +                       "))) "
                 //或者是这样查询第二被保人
                 //+         "union "
                 //+         "select b.CustomerNo as CustomerNo, "
                 //+                "b.Name as CustomerName "
                 //+           "from LCInsuredRelated b "
                 //+          "where 1 = 1 "
                 //+             getWherePart("b.PolNo", "PolNo")
                 //+            "and b.MainCustomerNo = "
                 //+                "(select InsuredNo "
                 //+                   "from LCPol "
                 //+                  "where 1 = 1 "
                 //+                     getWherePart("ContNo", "ContNo")
                 //+                     getWherePart("PolNo", "PolNo")
                 //+                ")) "
                 +  "order by CustomerNo asc";
    //alert(QuerySQL);
*/    
    var QuerySQL = "";
    var sqlid6="PEdorTypeGCInputSql6";
	var mySql6=new SqlClass();
	mySql6.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql6.setSqlId(sqlid6);//指定使用的Sql的id
	mySql6.addSubPara(fm.ContNo.value);//指定传入的参数
	mySql6.addSubPara(fm.ContNo.value);
	mySql6.addSubPara(fm.PolNo.value);
	mySql6.addSubPara(fm.ContNo.value);
	mySql6.addSubPara(fm.PolNo.value);
	mySql6.addSubPara(fm.ContNo.value);
	mySql6.addSubPara(fm.PolNo.value);
	QuerySQL=mySql6.getString();
    
    try
    {
        sInsuredCodeList = easyQueryVer3(QuerySQL, 1, 0);
    }
    catch (ex)
    {
        alert("警告：查询保单被保人信息出现异常！ ");
        return;
    }
    return sInsuredCodeList;
}

//add by jiaqiangli 2008-08-21
/**
 * 查询生存受益人信息
 */
function queryNewBnfGrid()
{
/*    var QuerySQL = "select a.InsuredNo, "
                 +        "(select Name "
                 +           "from LCInsured "
                 +          "where 1 = 1 "
                 +            "and ContNo = a.ContNo "
                 +            "and InsuredNo = a.InsuredNo), "
                 +        "a.BnfType, "
                 +        "a.Name, "
                 +        "'', "
                 +        "a.IDType, "
                 +        "a.IDNo, "
                 +        "a.RelationToInsured, "
                 +        "a.BnfGrade, "
                 +        "a.BnfLot, "
                 +        "'', "    //速填使用一列
                 +        "a.sex, "
                 +        "a.birthday, "
                 +        "a.postaladdress, "
                 +        "a.zipcode, "
                 +        "a.remark,"
                 //add by jiaqiangli 2008-08-25 增加三列
                 +        "a.bankcode,"
                 +        "a.bankaccno,"
                 +        "a.accname "
                 +   "from LPBnf a "
                 +  "where 1 = 1 and bnftype = '0' "
                 +     getWherePart("a.EdorNo", "EdorNo")
                 +     getWherePart("a.EdorType", "EdorType")
                 +     getWherePart("a.ContNo", "ContNo")
                 +     getWherePart("a.PolNo", "PolNo");
    //prompt("QuerySQL",QuerySQL);
*/    
    var QuerySQL = "";
    var sqlid7="PEdorTypeGCInputSql7";
	var mySql7=new SqlClass();
	mySql7.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql7.setSqlId(sqlid7);//指定使用的Sql的id
	mySql7.addSubPara(fm.EdorNo.value);//指定传入的参数
	mySql7.addSubPara(fm.EdorType.value);
	mySql7.addSubPara(fm.ContNo.value);
	mySql7.addSubPara(fm.PolNo.value);
	QuerySQL=mySql7.getString();
    
    try
    {
        turnPageNewBnfGrid.pageDivName = "divTurnPageNewBnfGrid";
        turnPageNewBnfGrid.queryModal(QuerySQL, NewBnfGrid);
    }
    catch (ex)
    {
        alert("警告：查询受益人信息出现异常！ ");
        return;
    }
    //先查lpbnf的信息后查询lcbnf的信息
    if (NewBnfGrid.mulLineCount <= 0) {
/*    	QuerySQL = "select a.InsuredNo, "
                 +        "(select Name "
                 +           "from LCInsured "
                 +          "where 1 = 1 "
                 +            "and ContNo = a.ContNo "
                 +            "and InsuredNo = a.InsuredNo), "
                 +        "a.BnfType, "
                 +        "a.Name, "
                 +        "'', "
                 +        "a.IDType, "
                 +        "a.IDNo, "
                 +        "a.RelationToInsured, "
                 +        "a.BnfGrade, "
                 +        "a.BnfLot, "
                 +        "'', "    //速填使用一列
                 +        "a.sex, "
                 +        "a.birthday, "
                 +        "a.postaladdress, "
                 +        "a.zipcode, "
                 +        "a.remark,"
                 //add by jiaqiangli 2008-08-25 增加三列
                 +        "a.bankcode,"
                 +        "a.bankaccno,"
                 +        "a.accname "
                 +   "from LcBnf a "
                 +  "where 1 = 1 and bnftype = '0' "
                 +     getWherePart("a.ContNo", "ContNo")
                 +     getWherePart("a.PolNo", "PolNo");
	    //prompt("QuerySQL",QuerySQL);
*/	    
    var sqlid8="PEdorTypeGCInputSql8";
	var mySql8=new SqlClass();
	mySql8.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql8.setSqlId(sqlid8);//指定使用的Sql的id
	mySql8.addSubPara(fm.ContNo.value);//指定传入的参数
	mySql8.addSubPara(fm.PolNo.value);
	QuerySQL=mySql8.getString();
	    
	    try
	    {
	        turnPageNewBnfGrid.pageDivName = "divTurnPageNewBnfGrid";
	        turnPageNewBnfGrid.queryModal(QuerySQL, NewBnfGrid);
	    }
	    catch (ex)
	    {
	        alert("警告：查询受益人信息出现异常！ ");
	        return;
	    }       
    }
}
//add by jiaqiangli 2008-08-21



function confirmSecondInput1(aObject,aEvent)
{
 {
  if(theFirstValue!="")
  {
   theSecondValue = aObject.value;
   if(theSecondValue=="")
   {
    alert("请再次录入！");
    aObject.value="";
    aObject.focus();
    return;
   }
   if(theSecondValue==theFirstValue)
   {
    aObject.value = theSecondValue;
    theSecondValue="";
    theFirstValue="";
    return;
   }
   else
   {
    alert("两次录入结果不符，请重新录入！");
    theFirstValue="";
    theSecondValue="";
    aObject.value="";
    aObject.focus();
    return;
   }
  }
  else
  {
   theFirstValue = aObject.value;
   theSecondValue="";
   if(theFirstValue=="")
   {
    return;
   }
   aObject.value="";
   aObject.focus();
   return;
  }
 }
}