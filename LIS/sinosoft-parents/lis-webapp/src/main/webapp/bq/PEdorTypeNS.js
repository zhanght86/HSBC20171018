//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="1";
var arrResult;
var arrResult1;
var aResult;//add by lizhuo for bq
var aPolNo;//add by lizhuo
var turnPage = new turnPageClass();
var mturnPage = new turnPageClass();
var addrFlag="MOD";
/*保存后面页面的缓存数据*/
var mSwitch;
/*保存前面页面的缓存数据*/
var tPreSwitch;
/*是否已经新增了附件险标记*/
var tFlag=false;
var sqlresourcename = "bq.PEdorTypeNSInputSql";

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
            getNewPolInfo();
            queryBackFee();
            top.opener.getEdorItemGrid();
        }
        catch (ex) {}
    }
    //document.getElementById("DetailButton").disabled=true; 
    showAddNewTypeInfo();
}

function queryCustomerInfo()
{
    var tContNo=top.opener.document.all('ContNo').value;
    var strSQL=""
    if(tContNo!=null || tContNo !='')
    {
/*      strSQL = "SELECT APPNTNAME,APPNTIDTYPE,APPNTIDNO,INSUREDNAME,INSUREDIDTYPE,INSUREDIDNO FROM LCCONT WHERE 1=1 AND "
                            +"CONTNO='"+tContNo+"'";
*/                            
	var sqlid1="PEdorTypeNSInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(tContNo);//指定传入的参数
	strSQL=mySql1.getString();
                            
    }
    else
    {
        alert('没有客户信息！');
    }
    //var str = "select polno from lcpol where 1=1 and contno ='"+tContNo+"' and polno = mainpolno";
    //var cResult = easyExecSql(str);
    //if(cResult == null){
         //alert("查询主险信息失败！");
         //return false;
    //}
    //aPolNo = cResult[0][0];
    var arrSelected = new Array();
    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
  //if(!turnPage.strQueryResult)
  //{
        //alert("查询失败");
    //}
    //else
    //{
    arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
    try {document.all('AppntName').value = arrSelected[0][0];} catch(ex) { }; //投保人姓名
    try {document.all('AppntIDType').value = arrSelected[0][1];} catch(ex) { }; //投保人证件类型
    try {document.all('AppntIDNo').value = arrSelected[0][2];} catch(ex) { }; //投保人证件号码
    try {document.all('InsuredName').value = arrSelected[0][3];} catch(ex) { }; //被保人名称
    try {document.all('InsuredIDType').value = arrSelected[0][4];} catch(ex) { }; //被保人证件类型
    try {document.all('InsuredIDNo').value = arrSelected[0][5];} catch(ex) { }; //被保人证件号码
    showOneCodeName('idtype','AppntIDType','AppntIDTypeName');
    showOneCodeName('idtype','InsuredIDType','InsuredIDTypeName');
    //try {
        //mSwitch = parent.VD.gVSwitch;
    //}
    //catch(ex){
      //alert(mSwitch);
    //}
  }
//}

function getPolInfo()
{
    PolGrid.clearData();
//    var strSQL ="select InsuredNo,InsuredName,RiskCode,(select RiskName from LMRisk where LMRisk.RiskCode = LCPol.RiskCode),Prem,Amnt,CValiDate,contno,grpcontno,Years,PayYears,paytodate from LCPol where Appflag = '1' and ContNo='"+document.all('ContNo').value+"'";
    
    var strSQL ="";
    var sqlid2="PEdorTypeNSInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	mySql2.addSubPara(document.all('ContNo').value);//指定传入的参数
	strSQL=mySql2.getString();
    
    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
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
}

function getNewPolInfo()
{
    NewPolGrid.clearData();
/*    var strSQL = "select RiskCode,(select RiskName from LMRisk where LMRisk.RiskCode = a.RiskCode), "
               //XinYQ modified on 2007-04-25
               + "(case "
               +   "when exists (select 'X' "
               +           "from LJSGetEndorse "
               +          "where 1 = 1 "
               +            getWherePart("EndorsementNo", "EdorNo")
               +            getWherePart("FeeOperationType", "EdorType")
               +            "and ContNo = a.ContNo "
               +            "and PolNo = a.PolNo) then "
               +    "(nvl((select sum(GetMoney) "
               +           "from LJSGetEndorse "
               +          "where 1 = 1 "
               +            getWherePart("EndorsementNo", "EdorNo")
               +            getWherePart("FeeOperationType", "EdorType")
               +            "and ContNo = a.ContNo "
               +            "and PolNo = a.PolNo), "
               +         "0)) "
               +   "else "
               +    "a.prem "
               + "end), "
               + "Amnt,CValiDate,contno,grpcontno,polno,Years,PayYears,paytodate from LCPol a where Appflag = '2' and ContNo='"+document.all('ContNo').value+"' and mainpolno='"+document.all('PolNo').value+"'";
*/    
    var strSQL ="";
    var sqlid3="PEdorTypeNSInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql3.setSqlId(sqlid3);//指定使用的Sql的id
	mySql3.addSubPara(document.all('EdorNo').value);//指定传入的参数
	mySql3.addSubPara(document.all('EdorType').value);
	mySql3.addSubPara(document.all('EdorNo').value);
	mySql3.addSubPara(document.all('EdorType').value);
	mySql3.addSubPara(document.all('ContNo').value);
	mySql3.addSubPara(document.all('PolNo').value);
	strSQL=mySql3.getString();
    
    mturnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
    if (!mturnPage.strQueryResult)
    {
        return false;
    }
    //查询成功则拆分字符串，返回二维数组
    mturnPage.arrDataCacheSet = decodeEasyQueryResult(mturnPage.strQueryResult);
    //设置初始化过的MULTILINE对象
    mturnPage.pageDisplayGrid = NewPolGrid;
    //保存SQL语句
    mturnPage.strQuerySql = strSQL;
    //设置查询起始位置
    mturnPage.pageIndex = 0;
    //在查询结果数组中取出符合页面显示大小设置的数组
    marrDataSet = mturnPage.getData(mturnPage.arrDataCacheSet, mturnPage.pageIndex, MAXSCREENLINES);
    //调用MULTILINE对象显示查询结果
    displayMultiline(marrDataSet, mturnPage.pageDisplayGrid);
}

function getRiskByGrpPolNo(GrpContNo,LoadFlag)
{
    //alert("1");
    var i = 0;
    var j = 0;
    var m = 0;
    var n = 0;
    var strsql = "";
    var tCodeData = "0|";
//    strsql = "select riskcode,riskname from lmriskapp where riskcode in (select riskcode from LCGrpPol where GrpContNo='"+GrpContNo+"')" ;
    //alert("strsql :" + strsql);
    
    var sqlid4="PEdorTypeNSInputSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql4.setSqlId(sqlid4);//指定使用的Sql的id
	mySql4.addSubPara(GrpContNo);//指定传入的参数
	strsql=mySql4.getString();
    
    turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);
    if (turnPage.strQueryResult != "")
    {
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        m = turnPage.arrDataCacheSet.length;
        for (i = 0; i < m; i++)
        {
            j = i + 1;
//          tCodeData = tCodeData + "^" + j + "|" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
            tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
        }
    }
    //alert ("tcodedata : " + tCodeData);

    return tCodeData;
}
function getRiskByGrpAll()
{
    var i = 0;
    var j = 0;
    var m = 0;
    var n = 0;
    var strsql = "";
    var tCodeData = "0|";
 //   strsql = "select RiskCode, RiskName from LMRiskApp where RiskProp in ('G','A','B','D') order by RiskCode" ;
    //alert("strsql :" + strsql);
    
    var sqlid5="PEdorTypeNSInputSql5";
	var mySql5=new SqlClass();
	mySql5.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql5.setSqlId(sqlid5);//指定使用的Sql的id
	mySql5.addSubPara("1");//指定传入的参数
	strsql=mySql5.getString();
    
    turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);
    if (turnPage.strQueryResult != "")
    {
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        m = turnPage.arrDataCacheSet.length;
        for (i = 0; i < m; i++)
        {
            j = i + 1;
//          tCodeData = tCodeData + "^" + j + "|" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
            tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
        }
    }
    //alert ("tcodedata : " + tCodeData);

    return tCodeData;
}
function getRisk()
{
    var i = 0;
    var j = 0;
    var m = 0;
    var n = 0;
    var strsql = "";
    var tCodeData = "0|";
/*    strsql = "select RiskCode, RiskName from LMRiskApp where RiskProp in ('I','A','C','D')"
           + " order by RiskCode";;
*/    
    var sqlid6="PEdorTypeNSInputSql6";
	var mySql6=new SqlClass();
	mySql6.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql6.setSqlId(sqlid6);//指定使用的Sql的id
	mySql6.addSubPara("1");//指定传入的参数
	strsql=mySql6.getString();
    
    turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);
    if (turnPage.strQueryResult != "")
    {
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        m = turnPage.arrDataCacheSet.length;
        for (i = 0; i < m; i++)
        {
            j = i + 1;
//          tCodeData = tCodeData + "^" + j + "|" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
            tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
        }
    }
    return tCodeData;
}

function getRiskByContPlan(GrpContNo,ContPlanCode)
{
    var i = 0;
    var j = 0;
    var m = 0;
    var n = 0;
    var strsql = "";
    var tCodeData = "0|";
//    strsql = "select b.RiskCode, b.RiskName from LCContPlanRisk a,LMRiskApp b where  a.GrpContNo='"+GrpContNo+"' and a.ContPlanCode='"+ContPlanCode+"' and a.riskcode=b.riskcode";
    //alert(strsql);
    
    var sqlid7="PEdorTypeNSInputSql7";
	var mySql7=new SqlClass();
	mySql7.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql7.setSqlId(sqlid7);//指定使用的Sql的id
	mySql7.addSubPara(GrpContNo);//指定传入的参数
	mySql7.addSubPara(ContPlanCode);
	strsql=mySql7.getString();
    
    turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);
    if (turnPage.strQueryResult != "")
    {
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        m = turnPage.arrDataCacheSet.length;
        for (i = 0; i < m; i++)
        {
            j = i + 1;
//          tCodeData = tCodeData + "^" + j + "|" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
            tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
        }
    }
    //alert("tCodeData:"+tCodeData);
    return tCodeData;
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


function edorTypeNSSave()
{
     if (!CheckTempfee())
     {
        return;
     }
     //var acontno = document.all("ContNo").value;
     //var aDate = document.all("EdorValiDate").value;
     //var str1 = "select * from ljspayperson where contno = '" + acontno + "' and lastpaytodate <= to_date('"+aDate+"','YYYY-MM-DD')";
     //var aResult = easyExecSql(str1);
     //if(aResult != null){
     //     alert("存在续期未缴费数据，请先去缴纳续期费用");
     //     return false;
     //}

   /*由于disabled后取不到值，则提交前先放开*/
   document.getElementById("NewCvaliDate").disabled=false;   
   document.getElementById("NewAddType").disabled=false;     
   document.getElementById("NewAddTypeName").disabled=false;  
    
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
   document.all('fmtransact').value = "";
   fm.submit();
   /*由于disabled后取不到值，则提交后又屏蔽*/
   document.getElementById("NewCvaliDate").disabled=true;   
   document.getElementById("NewAddType").disabled=true;     
   document.getElementById("NewAddTypeName").disabled=true; 
   
}

function CheckTempfee()
{
/*    var CheckStr = "select 'A' from ljtempfee where 1=1 "
                 + " and otherno = '" + fm.ContNo.value + "'"
                 + " and tempfeeno = ( select getnoticeno from ljspayperson where ContNo = '" + fm.ContNo.value + "' and rownum = 1)";
*/    
    var CheckStr = "";
    var sqlid8="PEdorTypeNSInputSql8";
	var mySql8=new SqlClass();
	mySql8.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql8.setSqlId(sqlid8);//指定使用的Sql的id
	mySql8.addSubPara(fm.ContNo.value);//指定传入的参数
	mySql8.addSubPara(fm.ContNo.value);
	CheckStr=mySql8.getString();
    
    var tResult = easyExecSql(CheckStr);
    if(tResult != null){
        alert("存在续期暂交费记录，暂时不能新增附加险！");
        return false;
    }
    return true;
}

function deleteapp()
{
     var selno = NewPolGrid.getSelNo()-1;
     if (selno <0)
     {
         alert("请选择要删除的险种！");
         return;
     }

    parent.VD.gVSwitch.deleteVar("NewAddTypeName");  
    parent.VD.gVSwitch.deleteVar("NewAddType");      
    parent.VD.gVSwitch.deleteVar("NewCvaliDate");  
    top.opener.parent.VD.gVSwitch.deleteVar("NewAddTypeName");  
    top.opener.parent.VD.gVSwitch.deleteVar("NewAddType");      
    top.opener.parent.VD.gVSwitch.deleteVar("NewCvaliDate"); 
    document.getElementById("NewCvaliDate").disabled=false;   
    document.getElementById("NewAddType").disabled=false;     
    document.getElementById("NewAddTypeName").disabled=false; 
   
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
   document.all('fmtransact').value = "DELETE";
   fm.submit();
   
}

function gotoRisk()
{
     //var acontno = document.all("ContNo").value;
     //var aDate = document.all("EdorValiDate").value;
     //var str1 = "select * from ljspayperson where contno = '" + acontno + "' and lastpaytodate <= to_date('"+aDate+"','YYYY-MM-DD') and polno = '"+document.all('PolNo').value+"'";
     //var aResult = easyExecSql(str1);
     // if(aResult != null){
     //       alert("存在续期未缴费数据，请先去缴纳续期费用");
     //       return false;
     // }
     //把合同信息和被保人信息放入内存
   mSwitch = parent.VD.gVSwitch;  //桢容错
   tPreSwitch=top.opener.parent.VD.gVSwitch;

   	 if(fm.NewCvaliDate.value=='' || fm.NewCvaliDate.value==null)
 {
 	   alert("请选择新增附加险的类型");
     return false;
 }
 if(fm.NewAddType.value==2 && ( fm.NewCvaliDate.value==null|| fm.NewCvaliDate.value==''))
 {
 	   	  alert("即时新增，请指定生效日");
        return false;
 } 
  if(fm.NewAddType.value==2 && ( dateDiff(fm.EdorItemAppDate.value,fm.NewCvaliDate.value,'D')!=0))
 {
 	   	  alert("即时新增，生效日与保全柜面受理日期一致，如需修改，请修改保全柜面受理日期");
        return false;
 } 
   	
//    var str = "select mainpolno,CValiDate from lcpol where 1=1 and contno ='"+document.all('ContNo').value+"' and polno = '"+document.all('PolNo').value+"'";
     
    var str = "";
    var sqlid9="PEdorTypeNSInputSql9";
	var mySql9=new SqlClass();
	mySql9.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql9.setSqlId(sqlid9);//指定使用的Sql的id
	mySql9.addSubPara(document.all('ContNo').value);//指定传入的参数
	mySql9.addSubPara(document.all('PolNo').value);
	str=mySql9.getString();
     
     var cResult = easyExecSql(str);
     if(cResult == null){
          alert("查询主险信息失败！");
          return;
     }
     //aPolNo = cResult[0][0];
     var tMainPolNoCvalidate=cResult[0][1];
     if(dateDiff(fm.NewCvaliDate.value,tMainPolNoCvalidate,'D')>0)
     {    	
          alert("新增险种生效日不能指定到主险之前，请重新选择！");
          return;	     	
     	}
     putCont();
     delInsuredVar();
     addInsuredVar();


     var selno = NewPolGrid.getSelNo()-1;
     var tFlag="1";
     if(selno>=0){
      aPolNo = NewPolGrid.getRowColData(selno, 8);
   	 //判断险种信息是否在明细界面保存，如果保全后则不再允许修改了
//     var strp = "select 'X' from lppol where  contno ='"+document.all('ContNo').value+"' and Appflag=2 and  polno = '"+aPolNo+"'";
     
    var strp = "";
    var sqlid10="PEdorTypeNSInputSql10";
	var mySql10=new SqlClass();
	mySql10.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql10.setSqlId(sqlid10);//指定使用的Sql的id
	mySql10.addSubPara(document.all('ContNo').value);//指定传入的参数
	mySql10.addSubPara(aPolNo);
	strp=mySql10.getString();
     
     var cResultp = easyExecSql(strp);
     if(cResultp != null){
      tFlag="0";
     } 	
     }

     try{mSwitch.deleteVar('SelPolNo');}catch(ex){}
     try{mSwitch.addVar('SelPolNo','',aPolNo);}catch(ex){} //选择险种单进入险种界面带出已保存的信息

     try{mSwitch.addVar('ContNo','',document.all('ContNo').value);}catch(ex){}
     try{mSwitch.updateVar('ContNo','',document.all('ContNo').value);}catch(ex){}
     try{mSwitch.deleteVar('mainRiskPolNo');}catch(ex){}
     try{mSwitch.addVar('mainRiskPolNo','',cResult[0][0]);}catch(ex){}
     try{mSwitch.deleteVar('CValiDate');}catch(ex){}
     //传入新的生效日期  
     
    try{mSwitch.addVar('CValiDate','',document.all('NewCvaliDate').value);}catch(ex){} 
    try{mSwitch.deleteVar('NewAddTypeName');}catch(ex){} 
    try{mSwitch.addVar('NewAddTypeName','',document.all('NewAddTypeName').value);}catch(ex){}  
    try{mSwitch.deleteVar('NewAddType');}catch(ex){} 
    try{mSwitch.addVar('NewAddType','',document.all('NewAddType').value);}catch(ex){}  
    try{mSwitch.deleteVar('NewCvaliDate');}catch(ex){} 
    try{mSwitch.addVar('NewCvaliDate','',document.all('NewCvaliDate').value);}catch(ex){}  
    try{tPreSwitch.deleteVar('NewAddTypeName');}catch(ex){} 
    try{tPreSwitch.addVar('NewAddTypeName','',document.all('NewAddTypeName').value);}catch(ex){}  
    try{tPreSwitch.deleteVar('NewAddType');}catch(ex){} 
    try{tPreSwitch.addVar('NewAddType','',document.all('NewAddType').value);}catch(ex){}  
    try{tPreSwitch.deleteVar('NewCvaliDate');}catch(ex){} 
    try{tPreSwitch.addVar('NewCvaliDate','',document.all('NewCvaliDate').value);}catch(ex){}                     
    parent.fraInterface.window.location = "../app/NSProposalInput.jsp?LoadFlag=8&ContType=1&scantype=null&MissionID=null&SubMissionID=null&BQFlag=1&EdorType=NS&checktype=1&ScanFlag=0&PolSaveFlag="+tFlag;   
    
}

/*********************************************************************
 *  把集体信息从变量中删除
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function delContVar()
{
     try { mSwitch.deleteVar( "intoPolFlag" ); } catch(ex) { };
     // body信息
     try { mSwitch.deleteVar( "BODY" ); } catch(ex) { };
     // 集体信息
     //由"./AutoCreatLDGrpInit.jsp"自动生成
   try { mSwitch.deleteVar('ContNo'); } catch(ex) { };
   try { mSwitch.deleteVar('ProposalContNo'); } catch(ex) { };
   try { mSwitch.deleteVar('PrtNo'); } catch(ex) { };
   try { mSwitch.deleteVar('GrpContNo'); } catch(ex) { };
   try { mSwitch.deleteVar('ContType'); } catch(ex) { };
   try { mSwitch.deleteVar('FamilyType'); } catch(ex) { };
   try { mSwitch.deleteVar('PolType'); } catch(ex) { };
   try { mSwitch.deleteVar('CardFlag'); } catch(ex) { };
   try { mSwitch.deleteVar('ManageCom'); } catch(ex) { };
   try { mSwitch.deleteVar('AgentCom'); } catch(ex) { };
   try { mSwitch.deleteVar('AgentCode'); } catch(ex) { };
   try { mSwitch.deleteVar('AgentGroup'); } catch(ex) { };
   try { mSwitch.deleteVar('AgentCode1'); } catch(ex) { };
   try { mSwitch.deleteVar('AgentType'); } catch(ex) { };
   try { mSwitch.deleteVar('SaleChnl'); } catch(ex) { };
   try { mSwitch.deleteVar('Handler'); } catch(ex) { };
   try { mSwitch.deleteVar('Password'); } catch(ex) { };
   try { mSwitch.deleteVar('AppntNo'); } catch(ex) { };
   try { mSwitch.deleteVar('AppntName'); } catch(ex) { };
   try { mSwitch.deleteVar('AppntSex'); } catch(ex) { };
   try { mSwitch.deleteVar('AppntBirthday'); } catch(ex) { };
   try { mSwitch.deleteVar('AppntIDType'); } catch(ex) { };
   try { mSwitch.deleteVar('AppntIDNo'); } catch(ex) { };
   try { mSwitch.deleteVar('InsuredNo'); } catch(ex) { };
   try { mSwitch.deleteVar('InsuredNam'); } catch(ex) { };
   try { mSwitch.deleteVar('InsuredSex'); } catch(ex) { };
   try { mSwitch.deleteVar('InsuredBirthday'); } catch(ex) { };
   try { mSwitch.deleteVar('InsuredIDType'); } catch(ex) { };
   try { mSwitch.deleteVar('InsuredIDNo'); } catch(ex) { };
   try { mSwitch.deleteVar('PayIntv'); } catch(ex) { };
   try { mSwitch.deleteVar('PayMode'); } catch(ex) { };
   try { mSwitch.deleteVar('PayLocation'); } catch(ex) { };
   try { mSwitch.deleteVar('DisputedFlag'); } catch(ex) { };
   try { mSwitch.deleteVar('OutPayFlag'); } catch(ex) { };
   try { mSwitch.deleteVar('GetPolMode'); } catch(ex) { };
   try { mSwitch.deleteVar('SignCom'); } catch(ex) { };
   try { mSwitch.deleteVar('SignDate'); } catch(ex) { };
   try { mSwitch.deleteVar('SignTime'); } catch(ex) { };
   try { mSwitch.deleteVar('ConsignNo'); } catch(ex) { };
   try { mSwitch.deleteVar('BankCode'); } catch(ex) { };
   try { mSwitch.deleteVar('BankAccNo'); } catch(ex) { };
   try { mSwitch.deleteVar('AccName'); } catch(ex) { };
   try { mSwitch.deleteVar('PrintCount'); } catch(ex) { };
   try { mSwitch.deleteVar('LostTimes'); } catch(ex) { };
   try { mSwitch.deleteVar('Lang'); } catch(ex) { };
   try { mSwitch.deleteVar('Currency'); } catch(ex) { };
   try { mSwitch.deleteVar('Remark'); } catch(ex) { };
   try { mSwitch.deleteVar('Peoples'); } catch(ex) { };
   try { mSwitch.deleteVar('Mult'); } catch(ex) { };
   try { mSwitch.deleteVar('Prem'); } catch(ex) { };
   try { mSwitch.deleteVar('Amnt'); } catch(ex) { };
   try { mSwitch.deleteVar('SumPrem'); } catch(ex) { };
   try { mSwitch.deleteVar('Dif'); } catch(ex) { };
   try { mSwitch.deleteVar('PaytoDate'); } catch(ex) { };
   try { mSwitch.deleteVar('FirstPayDate'); } catch(ex) { };
   try { mSwitch.deleteVar('CValiDate'); } catch(ex) { };
   try { mSwitch.deleteVar('InputOperator'); } catch(ex) { };
   try { mSwitch.deleteVar('InputDate'); } catch(ex) { };
   try { mSwitch.deleteVar('InputTime'); } catch(ex) { };
   try { mSwitch.deleteVar('ApproveFlag'); } catch(ex) { };
   try { mSwitch.deleteVar('ApproveCode'); } catch(ex) { };
   try { mSwitch.deleteVar('ApproveDate'); } catch(ex) { };
   try { mSwitch.deleteVar('ApproveTime'); } catch(ex) { };
   try { mSwitch.deleteVar('UWFlag'); } catch(ex) { };
   try { mSwitch.deleteVar('UWOperator'); } catch(ex) { };
   try { mSwitch.deleteVar('UWDate'); } catch(ex) { };
   try { mSwitch.deleteVar('UWTime'); } catch(ex) { };
   try { mSwitch.deleteVar('AppFlag'); } catch(ex) { };
   try { mSwitch.deleteVar('PolApplyDate'); } catch(ex) { };
   try { mSwitch.deleteVar('GetPolDate'); } catch(ex) { };
   try { mSwitch.deleteVar('GetPolTime'); } catch(ex) { };
   try { mSwitch.deleteVar('CustomGetPolDate'); } catch(ex) { };
   try { mSwitch.deleteVar('State'); } catch(ex) { };
   try { mSwitch.deleteVar('Operator'); } catch(ex) { };
   try { mSwitch.deleteVar('MakeDate'); } catch(ex) { };
   try { mSwitch.deleteVar('MakeTime'); } catch(ex) { };
   try { mSwitch.deleteVar('ModifyDate'); } catch(ex) { };
   try { mSwitch.deleteVar('ModifyTime'); } catch(ex) { };

    //新的删除数据处理
   try { mSwitch.deleteVar  ('AppntNo'); } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntName'); } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntSex'); } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntBirthday'); } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntIDType'); } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntIDNo'); } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntPassword'); } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntNativePlace'); } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntNationality'); } catch(ex) { };
   try {mSwitch.deleteVar('AddressNo');}catch(ex){};
   try { mSwitch.deleteVar  ('AppntRgtAddress'); } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntMarriage'); } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntMarriageDate'); } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntHealth'); } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntStature'); } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntAvoirdupois'); } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntDegree'); } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntCreditGrade'); } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntOthIDType'); } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntOthIDNo'); } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntICNo'); } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntGrpNo'); } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntJoinCompanyDate'); } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntStartWorkDate'); } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntPosition') } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntSalary'); } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntOccupationType'); } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntOccupationCode'); } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntWorkType'); } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntPluralityType');} catch(ex) { };
   try { mSwitch.deleteVar  ('AppntDeathDate'); } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntSmokeFlag'); } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntBlacklistFlag'); } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntProterty'); } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntRemark'); } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntState'); } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntOperator'); } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntMakeDate'); } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntMakeTime'); } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntModifyDate'); } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntModifyTime'); } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntHomeAddress'); } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntHomeZipCode'); } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntHomePhone'); } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntHomeFax'); } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntPostalAddress'); } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntZipCode'); } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntPhone'); } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntFax'); } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntMobile'); } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntEMail'); } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntGrpName'); } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntGrpPhone'); } catch(ex) { };
   try { mSwitch.deleteVar  ('CompanyAddress'); } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntGrpZipCode'); } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntGrpFax'); } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntBankAccNo'); } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntBankCode'); } catch(ex) { };
   try { mSwitch.deleteVar  ('AppntAccName'); } catch(ex) { };
}

/*********************************************************************
 *  把合同信息放入内存
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function putCont()
{
     delContVar();
     addIntoCont();
}

/*********************************************************************
 *  把合同信息放入加到变量中
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function addIntoCont()
{
      var aContNo = top.opener.document.all("ContNo").value;
//      var strsql = "select * from LCCont where contno = '"+top.opener.document.all("ContNo").value+"'";
      
    var strsql = "";
    var sqlid11="PEdorTypeNSInputSql11";
	var mySql11=new SqlClass();
	mySql11.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql11.setSqlId(sqlid11);//指定使用的Sql的id
	mySql11.addSubPara(top.opener.document.all("ContNo").value);//指定传入的参数
	strsql=mySql11.getString();
      
      aResult = easyExecSql(strsql);
      //try { mSwitch.addVar( "intoPolFlag", "", "GROUPPOL" ); } catch(ex) { };
      // body信息
      //try { mSwitch.addVar( "BODY", "", window.document.body.innerHTML ); } catch(ex) { };
      // 集体信息
      //由"./AutoCreatLDGrpInit.jsp"自动生成
    try{mSwitch.addVar('ContNo','',aResult[0][1]);}catch(ex){};
    try{mSwitch.addVar('ProposalContNo','',aResult[0][2]);}catch(ex){};
    try{mSwitch.addVar('PrtNo','',aResult[0][3]);}catch(ex){};
    try{mSwitch.addVar('GrpContNo','',aResult[0][0]);}catch(ex){};
    try{mSwitch.addVar('ContType','',aResult[0][4]);}catch(ex){};
    try{mSwitch.addVar('FamilyType','',aResult[0][5]);}catch(ex){};
    try{mSwitch.addVar('PolType','',aResult[0][7]);}catch(ex){};
    try{mSwitch.addVar('CardFlag','',aResult[0][8]);}catch(ex){};
    try{mSwitch.addVar('ManageCom','',aResult[0][9]);}catch(ex){};
    try{mSwitch.addVar('AgentCom','',aResult[0][11]);}catch(ex){};
    try{mSwitch.addVar('AgentCode','',aResult[0][12]);}catch(ex){};
    try{mSwitch.addVar('AgentGroup','',aResult[0][13]);}catch(ex){};
    try{mSwitch.addVar('AgentCode1','',aResult[0][14]);}catch(ex){};
    try{mSwitch.addVar('AgentType','',aResult[0][15]);}catch(ex){};
    try{mSwitch.addVar('SaleChnl','',aResult[0][16]);}catch(ex){};
    try{mSwitch.addVar('Handler','',aResult[0][17]);}catch(ex){};
    try{mSwitch.addVar('Password','',aResult[0][18]);}catch(ex){};
    try{mSwitch.addVar('AppntNo','',aResult[0][19]);}catch(ex){};
    try{mSwitch.addVar('AppntName','',aResult[0][20]);}catch(ex){};
    try{mSwitch.addVar('AppntSex','',aResult[0][21]);}catch(ex){};
    try{mSwitch.addVar('AppntBirthday','',aResult[0][22]);}catch(ex){};
    try{mSwitch.addVar('AppntIDType','',aResult[0][23]);}catch(ex){};
    try{mSwitch.addVar('AppntIDNo','',aResult[0][24]);}catch(ex){};

    try{mSwitch.addVar('InsuredNo','',aResult[0][25]);}catch(ex){};
    try{mSwitch.addVar('InsuredName','',aResult[0][26]);}catch(ex){};
    try{mSwitch.addVar('InsuredSex','',aResult[0][27]);}catch(ex){};
    try{mSwitch.addVar('InsuredBirthday','',aResult[0][28]);}catch(ex){};
    try{mSwitch.addVar('InsuredIDType','',aResult[0][29]);}catch(ex){};
    try{mSwitch.addVar('InsuredIDNo','',aResult[0][30]);}catch(ex){};
    try{mSwitch.addVar('PayIntv','',aResult[0][31]);}catch(ex){};
    try{mSwitch.addVar('PayMode','',aResult[0][32]);}catch(ex){};
    try{mSwitch.addVar('PayLocation','',aResult[0][33]);}catch(ex){};
    try{mSwitch.addVar('DisputedFlag','',aResult[0][34]);}catch(ex){};
    try{mSwitch.addVar('OutPayFlag','',aResult[0][35]);}catch(ex){};
    try{mSwitch.addVar('GetPolMode','',aResult[0][36]);}catch(ex){};
    try{mSwitch.addVar('SignCom','',aResult[0][37]);}catch(ex){};
    try{mSwitch.addVar('SignDate','',aResult[0][38]);}catch(ex){};
    try{mSwitch.addVar('SignTime','',aResult[0][39]);}catch(ex){};
    try{mSwitch.addVar('ConsignNo','',aResult[0][40]);}catch(ex){};
    try{mSwitch.addVar('BankCode','',aResult[0][41]);}catch(ex){};
    try{mSwitch.addVar('BankAccNo','',aResult[0][42]);}catch(ex){};
    try{mSwitch.addVar('AccName','',aResult[0][43]);}catch(ex){};
    try{mSwitch.addVar('PrintCount','',aResult[0][44]);}catch(ex){};
    try{mSwitch.addVar('LostTimes','',aResult[0][45]);}catch(ex){};
    try{mSwitch.addVar('Lang','',aResult[0][46]);}catch(ex){};
    try{mSwitch.addVar('Currency','',aResult[0][47]);}catch(ex){};
    try{mSwitch.addVar('Remark','',aResult[0][48]);}catch(ex){};
    try{mSwitch.addVar('Peoples','',aResult[0][49]);}catch(ex){};
    try{mSwitch.addVar('Mult','',aResult[0][50]);}catch(ex){};
    try{mSwitch.addVar('Prem','',aResult[0][51]);}catch(ex){};
    try{mSwitch.addVar('Amnt','',aResult[0][52]);}catch(ex){};
    try{mSwitch.addVar('SumPrem','',aResult[0][53]);}catch(ex){};
    try{mSwitch.addVar('Dif','',aResult[0][54]);}catch(ex){};
    try{mSwitch.addVar('PaytoDate','',aResult[0][55]);}catch(ex){};
    try{mSwitch.addVar('FirstPayDate','',aResult[0][56]);}catch(ex){};
    try{mSwitch.addVar('CValiDate','',aResult[0][57]);}catch(ex){};
    try{mSwitch.addVar('InputOperator','',aResult[0][58]);}catch(ex){};
    try{mSwitch.addVar('InputDate','',aResult[0][59]);}catch(ex){};
    try{mSwitch.addVar('InputTime','',aResult[0][60]);}catch(ex){};
    try{mSwitch.addVar('ApproveFlag','',aResult[0][61]);}catch(ex){};
    try{mSwitch.addVar('ApproveCode','',aResult[0][62]);}catch(ex){};
    try{mSwitch.addVar('ApproveDate','',aResult[0][63]);}catch(ex){};
    try{mSwitch.addVar('ApproveTime','',aResult[0][64]);}catch(ex){};
    try{mSwitch.addVar('UWFlag','',aResult[0][65]);}catch(ex){};
    try{mSwitch.addVar('UWOperator','',aResult[0][66]);}catch(ex){};
    try{mSwitch.addVar('UWDate','',aResult[0][67]);}catch(ex){};
    try{mSwitch.addVar('UWTime','',aResult[0][68]);}catch(ex){};
    try{mSwitch.addVar('AppFlag','',aResult[0][69]);}catch(ex){};
    try{mSwitch.addVar('PolApplyDate','',aResult[0][70]);}catch(ex){};
    try{mSwitch.addVar('GetPolDate','',aResult[0][71]);}catch(ex){};
    try{mSwitch.addVar('GetPolTime','',aResult[0][72]);}catch(ex){};
    try{mSwitch.addVar('CustomGetPolDate','',aResult[0][73]);}catch(ex){};
    try{mSwitch.addVar('State','',aResult[0][74]);}catch(ex){};
    try{mSwitch.addVar('Operator','',aResult[0][75]);}catch(ex){};
    try{mSwitch.addVar('MakeDate','',aResult[0][76]);}catch(ex){};
    try{mSwitch.addVar('MakeTime','',aResult[0][77]);}catch(ex){};
    try{mSwitch.addVar('ModifyDate','',aResult[0][78]);}catch(ex){};
    try{mSwitch.addVar('ModifyTime','',aResult[0][79]);}catch(ex){};

//   var strsql2 = "select * from lcappnt where contno = '"+aContNo+"'";
   
    var strsql2 = "";
    var sqlid12="PEdorTypeNSInputSql12";
	var mySql12=new SqlClass();
	mySql12.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql12.setSqlId(sqlid12);//指定使用的Sql的id
	mySql12.addSubPara(aContNo);//指定传入的参数
	strsql2=mySql12.getString();
   
   var tResult = easyExecSql(strsql2);
   //新的数据处理
   try { mSwitch.addVar('AppntNo','',tResult[0][33]); } catch(ex) { };
   try { mSwitch.addVar('AppntName','',tResult[0][35]); } catch(ex) { };
   try { mSwitch.addVar('AppntSex','',tResult[0][36]); } catch(ex) { };
   try { mSwitch.addVar('AppntBirthday','',tResult[0][37]); } catch(ex) { };
   try { mSwitch.addVar('AppntIDType','',tResult[0][40]); } catch(ex) { };
   try { mSwitch.addVar('AppntIDNo','',tResult[0][41]); } catch(ex) { };
   //try { mSwitch.addVar('AppntPassword','','000000'); } catch(ex) { };
   try { mSwitch.addVar('AppntNativePlace','',tResult[0][42]); } catch(ex) { };
   try { mSwitch.addVar('AppntNationality','',tResult[0][0]); } catch(ex) { };
   try { mSwitch.addVar('AddressNo','',tResult[0][39]); } catch(ex) { };
   //try { mSwitch.addVar('AppntRgtAddress','',tResult[0][]); } catch(ex) { };
   try { mSwitch.addVar('AppntMarriage','',tResult[0][2]); } catch(ex) { };
   try { mSwitch.addVar('AppntMarriageDate','',tResult[0][3]); } catch(ex) { };
   try { mSwitch.addVar('AppntHealth','',tResult[0][4]); } catch(ex) { };
   try { mSwitch.addVar('AppntStature','',tResult[0][5]); } catch(ex) { };
   try { mSwitch.addVar('AppntAvoirdupois','',tResult[0][6]); } catch(ex) { };
   try { mSwitch.addVar('AppntDegree','',tResult[0][7]); } catch(ex) { };
   try { mSwitch.addVar('AppntCreditGrade','',tResult[0][8]); } catch(ex) { };
   //try { mSwitch.addVar('AppntOthIDType','',tResult[0][]); } catch(ex) { };
   //try { mSwitch.addVar('AppntOthIDNo','',tResult[0][]); } catch(ex) { };
   //try { mSwitch.addVar('AppntICNo','',tResult[0][]); } catch(ex) { };
   //try { mSwitch.addVar('AppntGrpNo','',tResult[0][]); } catch(ex) { };
   try { mSwitch.addVar('AppntJoinCompanyDate','',tResult[0][12]); } catch(ex) { };
   try { mSwitch.addVar('AppntStartWorkDate','',tResult[0][13]); } catch(ex) { };
   try { mSwitch.addVar('AppntPosition','',tResult[0][14]); } catch(ex) { };
   try { mSwitch.addVar('AppntSalary','',tResult[0][15]); } catch(ex) { };
   try { mSwitch.addVar('AppntOccupationType','',tResult[0][16]); } catch(ex) { };
   try { mSwitch.addVar('AppntOccupationCode','',tResult[0][17]); } catch(ex) { };
   try { mSwitch.addVar('AppntWorkType','',tResult[0][18]); } catch(ex) { };
   try { mSwitch.addVar('AppntPluralityType','',tResult[0][19]); } catch(ex) { };
   //try { mSwitch.addVar('AppntDeathDate','',tResult[0][]); } catch(ex) { };
   try { mSwitch.addVar('AppntSmokeFlag','',tResult[0][20]); } catch(ex) { };
   //try { mSwitch.addVar('AppntBlacklistFlag','',tResult[0][]); } catch(ex) { };
   //try { mSwitch.addVar('AppntProterty','',tResult[0][]); } catch(ex) { };
   //try { mSwitch.addVar('AppntRemark','',tResult[0][]); } catch(ex) { };
   //try { mSwitch.addVar('AppntState','',tResult[0][]); } catch(ex) { };
   try { mSwitch.addVar('AppntOperator','',tResult[0][21]); } catch(ex) { };
   try { mSwitch.addVar('AppntMakeDate','',tResult[0][23]); } catch(ex) { };
   try { mSwitch.addVar('AppntMakeTime','',tResult[0][24]); } catch(ex) { };
   try { mSwitch.addVar('AppntModifyDate','',tResult[0][25]); } catch(ex) { };
   try { mSwitch.addVar('AppntModifyTime','',tResult[0][26]); } catch(ex) { };
   /*
   try { mSwitch.addVar('AppntHomeAddress','',tResult[0][]); } catch(ex) { };
   try { mSwitch.addVar('AppntHomeZipCode','',tResult[0][]); } catch(ex) { };
   try { mSwitch.addVar('AppntHomePhone','',tResult[0][]); } catch(ex) { };
   try { mSwitch.addVar('AppntHomeFax','',tResult[0][]); } catch(ex) { };
   try { mSwitch.addVar('AppntGrpName','',tResult[0][]); } catch(ex) { };
   try { mSwitch.addVar('AppntGrpPhone','',tResult[0][]); } catch(ex) { };
   try { mSwitch.addVar('CompanyAddress','',tResult[0][]); } catch(ex) { };
   try { mSwitch.addVar('AppntGrpZipCode','',tResult[0][]); } catch(ex) { };
   try { mSwitch.addVar('AppntGrpFax','',tResult[0][]); } catch(ex) { };
   try { mSwitch.addVar('AppntPostalAddress','',tResult[0][]); } catch(ex) { };
   try { mSwitch.addVar('AppntZipCode','',tResult[0][]); } catch(ex) { };
   try { mSwitch.addVar('AppntPhone','',tResult[0][]); } catch(ex) { };
   try { mSwitch.addVar('AppntMobile','',tResult[0][]); } catch(ex) { };
   try { mSwitch.addVar('AppntFax','',tResult[0][]); } catch(ex) { };
   try { mSwitch.addVar('AppntEMail','',tResult[0][]); } catch(ex) { };
   try { mSwitch.addVar('AppntBankAccNo','',tResult[0][]); } catch(ex) { };
   try { mSwitch.addVar('AppntBankCode','',tResult[0][]); } catch(ex) { };
   try { mSwitch.addVar('AppntAccName','',tResult[0][]); } catch(ex) { };
   */
}

/*********************************************************************
 *  删除缓存中被保险人信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function delInsuredVar()
{
    try{mSwitch.deleteVar('ContNo');}catch(ex){};
    try{mSwitch.deleteVar('InsuredNo');}catch(ex){};
    try{mSwitch.deleteVar('PrtNo');}catch(ex){};
    try{mSwitch.deleteVar('GrpContNo');}catch(ex){};
 //   try{mSwitch.deleteVar('AppntNo');}catch(ex){};
 //   try{mSwitch.deleteVar('ManageCom');}catch(ex){};
    try{mSwitch.deleteVar('ExecuteCom');}catch(ex){};
    try{mSwitch.deleteVar('FamilyType');}catch(ex){};
    try{mSwitch.deleteVar('RelationToMainInsure');}catch(ex){};
    try{mSwitch.deleteVar('RelationToAppnt');}catch(ex){};
    try{mSwitch.deleteVar('AddressNo');}catch(ex){};
    try{mSwitch.deleteVar('SequenceNo');}catch(ex){};
    try{mSwitch.deleteVar('Name');}catch(ex){};
    try{mSwitch.deleteVar('Sex');}catch(ex){};
    try{mSwitch.deleteVar('Birthday');}catch(ex){};
    try{mSwitch.deleteVar('IDType');}catch(ex){};
    try{mSwitch.deleteVar('IDNo');}catch(ex){};
    try{mSwitch.deleteVar('RgtAddress');}catch(ex){};
    try{mSwitch.deleteVar('Marriage');}catch(ex){};
    try{mSwitch.deleteVar('MarriageDate');}catch(ex){};
    try{mSwitch.deleteVar('Health');}catch(ex){};
    try{mSwitch.deleteVar('Stature');}catch(ex){};
    try{mSwitch.deleteVar('Avoirdupois');}catch(ex){};
    try{mSwitch.deleteVar('Degree');}catch(ex){};
    try{mSwitch.deleteVar('CreditGrade');}catch(ex){};
    try{mSwitch.deleteVar('BankCode');}catch(ex){};
    try{mSwitch.deleteVar('BankAccNo');}catch(ex){};
    try{mSwitch.deleteVar('AccName');}catch(ex){};
    try{mSwitch.deleteVar('JoinCompanyDate');}catch(ex){};
    try{mSwitch.deleteVar('StartWorkDate');}catch(ex){};
    try{mSwitch.deleteVar('Position');}catch(ex){};
    try{mSwitch.deleteVar('Salary');}catch(ex){};
    try{mSwitch.deleteVar('OccupationType');}catch(ex){};
    try{mSwitch.deleteVar('OccupationCode');}catch(ex){};
    try{mSwitch.deleteVar('WorkType');}catch(ex){};
    try{mSwitch.deleteVar('PluralityType');}catch(ex){};
    try{mSwitch.deleteVar('SmokeFlag');}catch(ex){};
    try{mSwitch.deleteVar('ContPlanCode');}catch(ex){};
    try{mSwitch.deleteVar('Operator');}catch(ex){};
    try{mSwitch.deleteVar('MakeDate');}catch(ex){};
    try{mSwitch.deleteVar('MakeTime');}catch(ex){};
    try{mSwitch.deleteVar('ModifyDate');}catch(ex){};
    try{mSwitch.deleteVar('ModifyTime');}catch(ex){};
}

/*********************************************************************
 *  将被保险人信息加入到缓存中
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function addInsuredVar()
{
//    var strsql = "select * from lcinsured where contno = '"+aResult[0][1]+"' and insuredno = '"+aResult[0][25]+"'";
    //alert(strsql);
    
    var strsql = "";
    var sqlid13="PEdorTypeNSInputSql13";
	var mySql13=new SqlClass();
	mySql13.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql13.setSqlId(sqlid13);//指定使用的Sql的id
	mySql13.addSubPara(aResult[0][1]);//指定传入的参数
	mySql13.addSubPara(aResult[0][25]);
	strsql=mySql13.getString();
    
    var tResult = easyExecSql(strsql);
    //alert(tResult);
    try{mSwitch.addVar('ContNo','',tResult[0][1]);}catch(ex){};
    //alert("ContNo:"+fm.ContNo.value);
    try{mSwitch.addVar('InsuredNo','',tResult[0][2]);}catch(ex){};
    try{mSwitch.addVar('PrtNo','',tResult[0][3]);}catch(ex){};
    try{mSwitch.addVar('GrpContNo','',tResult[0][0]);}catch(ex){};
    //try{mSwitch.addVar('AppntNo','',fm.AppntNo.value);}catch(ex){};
    //try{mSwitch.addVar('ManageCom','',fm.ManageCom.value);}catch(ex){};
    try{mSwitch.addVar('ExecuteCom','',tResult[0][6]);}catch(ex){};
    //try{mSwitch.addVar('FamilyType','',tResult[0][]);}catch(ex){};
    try{mSwitch.addVar('RelationToMainInsure','',tResult[0][8]);}catch(ex){};
    try{mSwitch.addVar('RelationToAppnt','',tResult[0][9]);}catch(ex){};

    try{mSwitch.addVar('AddressNo','',tResult[0][10]);}catch(ex){};
    try{mSwitch.addVar('SequenceNo','',tResult[0][11]);}catch(ex){};
    try{mSwitch.addVar('Name','',tResult[0][12]);}catch(ex){};
    try{mSwitch.addVar('Sex','',tResult[0][13]);}catch(ex){};
    try{mSwitch.addVar('Birthday','',tResult[0][14]);}catch(ex){};
    try{mSwitch.addVar('IDType','',tResult[0][15]);}catch(ex){};
    try{mSwitch.addVar('IDNo','',tResult[0][16]);}catch(ex){};
    try{mSwitch.addVar('RgtAddress','',tResult[0][19]);}catch(ex){};
    try{mSwitch.addVar('Marriage','',tResult[0][20]);}catch(ex){};
    try{mSwitch.addVar('MarriageDate','',tResult[0][21]);}catch(ex){};
    try{mSwitch.addVar('Health','',tResult[0][22]);}catch(ex){};
    try{mSwitch.addVar('Stature','',tResult[0][23]);}catch(ex){};
    try{mSwitch.addVar('Avoirdupois','',tResult[0][24]);}catch(ex){};
    try{mSwitch.addVar('Degree','',tResult[0][25]);}catch(ex){};
    try{mSwitch.addVar('CreditGrade','',tResult[0][26]);}catch(ex){};
    try{mSwitch.addVar('BankCode','',tResult[0][27]);}catch(ex){};
    try{mSwitch.addVar('BankAccNo','',tResult[0][28]);}catch(ex){};
    try{mSwitch.addVar('AccName','',tResult[0][29]);}catch(ex){};
    try{mSwitch.addVar('JoinCompanyDate','',tResult[0][30]);}catch(ex){};
    try{mSwitch.addVar('StartWorkDate','',tResult[0][31]);}catch(ex){};
    try{mSwitch.addVar('Position','',tResult[0][32]);}catch(ex){};
    try{mSwitch.addVar('Salary','',tResult[0][33]);}catch(ex){};
    try{mSwitch.addVar('OccupationType','',tResult[0][34]);}catch(ex){};
    try{mSwitch.addVar('OccupationCode','',tResult[0][35]);}catch(ex){};
    try{mSwitch.addVar('WorkType','',tResult[0][36]);}catch(ex){};
    try{mSwitch.addVar('PluralityType','',tResult[0][37]);}catch(ex){};
    try{mSwitch.addVar('SmokeFlag','',tResult[0][38]);}catch(ex){};
    try{mSwitch.addVar('ContPlanCode','',tResult[0][39]);}catch(ex){};
    try{mSwitch.addVar('Operator','',tResult[0][40]);}catch(ex){};
    try{mSwitch.addVar('MakeDate','',tResult[0][42]);}catch(ex){};
    try{mSwitch.addVar('MakeTime','',tResult[0][43]);}catch(ex){};
    try{mSwitch.addVar('ModifyDate','',tResult[0][44]);}catch(ex){};
    try{mSwitch.addVar('ModifyTime','',tResult[0][45]);}catch(ex){};

}
function Edorquery()
{
  var ButtonFlag = top.opener.document.all('ButtonFlag').value;
  if(ButtonFlag!=null && ButtonFlag=="1")
  {
     divEdorquery.style.display = 'none';
  }
  else
  {
     divEdorquery.style.display = '';
  }
}

function showimpart(){
     var tEdorNo = document.all('EdorNo').value;
     var tContNo = document.all('ContNo').value;

    var strsqlNS = "";
    var sqlid14="PEdorTypeNSInputSql14";
	var mySql14=new SqlClass();
	mySql14.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql14.setSqlId(sqlid14);//指定使用的Sql的id
	mySql14.addSubPara(tContNo);//指定传入的参数
	strsqlNS=mySql14.getString();
     
//     var aResult = easyExecSql("select insuredno from lcpol where contno = '" + tContNo +"' and mainpolno = polno and appflag = '1'");
     var aResult = easyExecSql(strsqlNS);
     if(aResult == null){
           alert("查询主险信息失败！");
           return false;
     }
/*     var Strvar = "select impartver,impartcode,trim(impartcontent),impartparammodle from lpcustomerimpart where edortype = 'NS' and edorno = '"
         + tEdorNo + "' and customerno = '" + aResult[0][0] + "'";
*/     
    var Strvar = "";
    var sqlid15="PEdorTypeNSInputSql15";
	var mySql15=new SqlClass();
	mySql15.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql15.setSqlId(sqlid15);//指定使用的Sql的id
	mySql15.addSubPara(tEdorNo);//指定传入的参数
	mySql15.addSubPara(aResult[0][0]);
	Strvar=mySql15.getString();
     
     var arrSelected = new Array();
     turnPage.strQueryResult  = easyQueryVer3(Strvar, 1, 0, 1);
     //判断是否查询成功
     //alert(turnPage.strQueryResult);
     if (!turnPage.strQueryResult) {
          return false;
     }
     //清空数据容器，两个不同查询共用一个turnPage对象时必须使用，最好加上，容错
     turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
     //查询成功则拆分字符串，返回二维数组
     turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
     //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
     turnPage.pageDisplayGrid = ImpartGrid;
     //保存SQL语句
     turnPage.strQuerySql = Strvar;
     //设置查询起始位置
     turnPage.pageIndex = 0;
     //在查询结果数组中取出符合页面显示大小设置的数组
     arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
     //调用MULTILINE对象显示查询结果
     displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}

/**
* showCodeList 的回调函数
*/
function afterCodeSelect(sCodeListType, oCodeListField)
{
    sCodeListType = sCodeListType.toLowerCase();
    if (sCodeListType == "newaddtype")
    {
    	 //即时新增
        if (oCodeListField.value == "2")
        {
            try
            {
            	  fm.NewCvaliDate.value=fm.EdorItemAppDate.value;
            	  //document.getElementById("NewCvaliDate").readOnly=true;    

            }
            catch (ex) {}
        }
        //追溯新增
        else if(oCodeListField.value == "3")
        {
            try
            {
            	  getCValiDate(3);   

            	  document.getElementById("NewCvaliDate").readOnly=true;          
            }
            catch (ex) {}
        }
        //预约新增
       else if(oCodeListField.value == "1")
        {
            try
            {
            	  getCValiDate(1);  
            	  document.getElementById("NewCvaliDate").readOnly=true; 
            }
            catch (ex) {}
        }
    } 

}
/**查询新增类型信息*/
function showAddNewTypeInfo()
{       
       fm.NewAddTypeName.value=parent.VD.gVSwitch.getVar("NewAddTypeName");  
       fm.NewAddType.value=parent.VD.gVSwitch.getVar("NewAddType");      
       fm.NewCvaliDate.value=parent.VD.gVSwitch.getVar("NewCvaliDate");   
       //alert(fm.NewAddTypeName.value);
       if(fm.NewAddTypeName.value=='false')
       {

       			fm.NewAddTypeName.value=top.opener.parent.VD.gVSwitch.getVar("NewAddTypeName");  
            fm.NewAddType.value=top.opener.parent.VD.gVSwitch.getVar("NewAddType");      
            fm.NewCvaliDate.value=top.opener.parent.VD.gVSwitch.getVar("NewCvaliDate");   
       			if(fm.NewAddTypeName.value=='false')
       			{
            var tEdorNo = document.all('EdorNo').value;
/*	          var Strvar = "select nvl(StandbyFlag1,0),nvl(StandbyFlag2,0) from lpedoritem where edortype = 'NS' and edorno = '"
              + tEdorNo + "'";
*/            
    var Strvar = "";
    var sqlid16="PEdorTypeNSInputSql16";
	var mySql16=new SqlClass();
	mySql16.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql16.setSqlId(sqlid16);//指定使用的Sql的id
	mySql16.addSubPara(tEdorNo);//指定传入的参数
	Strvar=mySql16.getString();
            
            var aResult = easyExecSql(Strvar,1,0,1);
           if(aResult[0][0]!=0)
           {
           	       fm.NewAddType.value=aResult[0][0];
//           	       var tAppSQL="select codename from ldcode where codetype='newaddtype' and code='"+fm.NewAddType.value+"'"
    	            
    var tAppSQL = "";
    var sqlid17="PEdorTypeNSInputSql17";
	var mySql17=new SqlClass();
	mySql17.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql17.setSqlId(sqlid17);//指定使用的Sql的id
	mySql17.addSubPara(fm.NewAddType.value);//指定传入的参数
	tAppSQL=mySql17.getString();
    	            
    	             var tResultName=easyExecSql(tAppSQL,1,0,1); 
                   fm.NewAddTypeName.value=tResultName[0][0]; 
           	       fm.NewCvaliDate.value=aResult[0][1];
           	}else
           		{
  //         		 var StrSQL = "select CValiDate from lcpol  where contno ='"+fm.ContNo.value+"' and appflag='2'";
               
    var StrSQL = "";
    var sqlid18="PEdorTypeNSInputSql18";
	var mySql18=new SqlClass();
	mySql18.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql18.setSqlId(sqlid18);//指定使用的Sql的id
	mySql18.addSubPara(fm.ContNo.value);//指定传入的参数
	StrSQL=mySql18.getString();
               
               var aAppSQL = easyExecSql(StrSQL,1,0,1);
               if(aAppSQL)
                {     
           			   fm.NewCvaliDate.value=aAppSQL[0][0];
           			   fm.NewAddType.value="";
           			   fm.NewAddTypeName.value="";
                }else
                	{
           			   fm.NewCvaliDate.value="";
           			   fm.NewAddType.value="";
           			   fm.NewAddTypeName.value="";                		               		                		
                	}   
           		 }           
           }          
      }     
//      var StrAppvar = "select 'X' from lcpol  where contno ='"+fm.ContNo.value+"' and appflag='2'";
      
    var StrAppvar = "";
    var sqlid19="PEdorTypeNSInputSql19";
	var mySql19=new SqlClass();
	mySql19.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql19.setSqlId(sqlid19);//指定使用的Sql的id
	mySql19.addSubPara(fm.ContNo.value);//指定传入的参数
	StrAppvar=mySql19.getString();
      
      var aAppResult = easyExecSql(StrAppvar,1,0,1);
      if(aAppResult)
      {     
      	    tFlag=true; 
      	    document.getElementById("NewCvaliDate").disabled=true; 
       	    document.getElementById("NewAddType").disabled=true; 
       	    document.getElementById("NewAddTypeName").disabled=true; 
      }      

}


/**
根据附加险新增类型，算出生效日
不再通过前台计算期生效日，而是通过后台进行计算

*/

function getCValiDate(tFlag)
{

	   var tEdorValiDate=fm.EdorItemAppDate.value;
	   var tCurDate=fm.CureCvaliDate.value; 
	    
	   //预约新增
	   if(tFlag=='1')
	   {
	   	var tInv=dateDiff(tEdorValiDate,tCurDate,'D');
	   	if(tInv>0)
	   	{
	   			   fm.NewCvaliDate.value=tCurDate;
	   	}else
	   	{     
	   		fm.NewCvaliDate.value=fm.NextCvaliDate.value;
	   	}	
	   	//计算的生效日与交至日期取小
	   	//alert(fm.NewCvaliDate.value);
	   	//alert(document.all('PayToDate').value);
	   	if(dateDiff(fm.NewCvaliDate.value,document.all('PayToDate').value.substring(0,10),'D')<0)
	   	{
	   		fm.NewCvaliDate.value=document.all('PayToDate').value.substring(0,10);
	   	}
	   	  			   	
	   }
	   //追溯新增
	   if(tFlag=='3')
	   {
	     var tInv=dateDiff(tEdorValiDate,tCurDate,'D');
	   	if(tInv>0)
	   	{
	   		fm.NewCvaliDate.value=fm.PreCvaliDate.value;
	   	}else
	   	{     
	   			fm.NewCvaliDate.value=tCurDate;
	   	}		
	  }
	}

	