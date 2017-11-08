//该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
var sqlresourcename = "bq.PEdorTypeREInputSql";

function saveEdorTypeRE()
{

  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  var name='提示';   //网页名称，可为空; 
var iWidth=550;      //弹出窗口的宽度; 
var iHeight=250;     //弹出窗口的高度; 
var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

showInfo.focus();
  fm.fmtransact.value="";
  document.getElementById("fm").submit();
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit(FlagStr, content, tMulArray )
{
    showInfo.close();
    //alert(tMulArray);

    var urlStr;
    if (FlagStr == "Success")
    {
        urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    }
    else if (FlagStr == "Fail")
    {
        urlStr="../common/jsp/MessagePage.jsp?picture=F&content=" + content;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    }
    else
    {
        urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:300px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=300;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    }

    if (FlagStr == "Success")
    {
        //displayMultiline(tMulArray,PolInsuredGrid,turnPage);
        try
        {
            queryBackFee();
            top.opener.getEdorItemGrid();
        } catch (ex) {}
    }

    /*
    var tTransact=document.all('fmtransact').value;
//      alert(tTransact);
        if (tTransact=="QUERY||MAIN")
        {
            var iArray;
            //清空数据容器，两个不同查询共用一个turnPage对象时必须使用，最好加上，容错
            turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
            //保存查询结果字符串
            turnPage.strQueryResult  = Result;

            //查询成功则拆分字符串，返回二维数组
            var tArr   = decodeEasyQueryResult(turnPage.strQueryResult,0);
//          alert(tArr[0]);
//          alert(tArr[0].length);
            document.all('Amnt').value = tArr[0][45];
            document.all('Prem').value = tArr[0][42];
            document.all('AppntNo').value = tArr[0][28];
            document.all('AppntName').value = tArr[0][29];
            document.all('Multi').value = tArr[0][40];
            document.all('CalMode').value = tArr[0][134];

            //calMode是付加在保单的字符串传过来的，并放在了最后
            var calMode = tArr[0][134];
            //alert(tArr[0][tArr[0].length-2]);

            if (calMode == 'P') {
                var urlStr="../common/jsp/MessagePage.jsp?picture=S&content= 此保单无法进行部分退保计算" ;
                showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
                initForm();
                return;
            } else if (calMode == 'O') {   //利用分数变化进行计算

                //document.all('RemainMulti').value = tArr[0][132];
                RemainAmntDiv.style.display = "none";
                RemainMultiDiv.style.display = "";
            } else {                      //利用保额变化进行计算

                //document.all('RemainAmnt').value = tArr[0][132];
                RemainMultiDiv.style.display = 'none';
                RemainAmntDiv.style.display = '';
            }

            //团险保全复用，自动填写数值，并提交
        if (typeof(top.opener.GrpBQ)=="boolean" && top.opener.GrpBQ==true) {
          fm.RemainAmnt.value = top.opener.GTArr.pop();

          edorTypePTSave();
        }
        //***********************************

        } else {

      //团险保全复用，提交成功后，再次调用，以循环
        if (typeof(top.opener.GrpBQ)=="boolean" && top.opener.GrpBQ==true) {
              top.opener.PEdorDetail();
              top.close();
            }
            else {
              var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
          showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
          initForm();
        }
    }
    */

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


function personQuery()
{
    //window.open("./LCPolQuery.html");
//    window.open("./LPTypeIAPersonQuery.html");
    window.open("./LPTypeIAPersonQuery.html","",sFeatures);
}

function afterPersonQuery(arrResult)
{
    if (arrResult == null ||arrResult[0] == null || arrResult[0][0] == "" )
        return;

    //选择了一个投保人,显示详细细节
    document.all("QueryCustomerNo").value = arrResult[0][0];

//    var strSql = "select * from ldperson where customerNo = " + arrResult[0][0];

	var strSql = "";
	var sqlid1="PEdorTypeREInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(arrResult[0][0]);//指定传入的参数
	strSql=mySql1.getString();

    //查询SQL，返回结果字符串
    turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);

 //   alert(turnPage.strQueryResult);
    //判断是否查询成功
    if (!turnPage.strQueryResult) {
        //清空MULTILINE，使用方法见MULTILINE使用说明
        VarGrid.clearData('VarGrid');
        alert("查询失败！");
        return false;
    }


     //清空数据容器，两个不同查询共用一个turnPage对象时必须使用，最好加上，容错
    turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);

    //查询成功则拆分字符串，返回二维数组
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

    fillPersonDetail();

  divLPAppntIndDetail.style.display = "";



}

function fillPersonDetail()
{
    try {
        document.all("AppntCustomerNo").value = turnPage.arrDataCacheSet[0][0];
        document.all("AppntName").value = turnPage.arrDataCacheSet[0][2];
        document.all("AppntSex").value = turnPage.arrDataCacheSet[0][3];
        document.all("AppntBirthday").value = turnPage.arrDataCacheSet[0][4];

        document.all("AppntIDType").value = turnPage.arrDataCacheSet[0][16];
        document.all("AppntIDNo").value = turnPage.arrDataCacheSet[0][18];
        document.all("AppntNativePlace").value = turnPage.arrDataCacheSet[0][5];

        document.all("AppntPostalAddress").value = turnPage.arrDataCacheSet[0][24];
        document.all("AppntZipCode").value = turnPage.arrDataCacheSet[0][25];
        document.all("AppntHomeAddress").value = turnPage.arrDataCacheSet[0][23];
        document.all("AppntHomeZipCode").value = turnPage.arrDataCacheSet[0][22];

        document.all("AppntPhone").value = turnPage.arrDataCacheSet[0][26];
        document.all("AppntPhone2").value = turnPage.arrDataCacheSet[0][56];
        document.all("AppntMobile").value = turnPage.arrDataCacheSet[0][28];
        document.all("AppntEMail").value = turnPage.arrDataCacheSet[0][29];
        document.all("AppntGrpName").value = turnPage.arrDataCacheSet[0][38];

        document.all("AppntWorkType").value = turnPage.arrDataCacheSet[0][48];
        document.all("AppntPluralityType").value = turnPage.arrDataCacheSet[0][49];
        document.all("AppntOccupationCode").value = turnPage.arrDataCacheSet[0][50];
        document.all("AppntOccupationType").value = turnPage.arrDataCacheSet[0][9];
    } catch(ex) {
        alert("在PEdorTypePT.js-->fillPersonDetail函数中发生异常:初始化界面错误!");
    }
}
function getPolInfo(tContNo)
{
      var tContno=document.all('ContNo').value;
    //alert(tContNo);
    //var tContNo;
    // 书写SQL语句
    //add by jiaqiangli 2009-03-10 已经终止的附加险不能复效，此处不显示出来
//    var strSQL ="select InsuredNo,InsuredName,RiskCode,(select RiskName from LMRisk where LMRisk.RiskCode = LCPol.RiskCode),Prem,Amnt,CValiDate,contno,grpcontno from LCPol where ContNo='"+tContNo+"' and appflag = '1' ";

	var strSQL = "";
	var sqlid2="PEdorTypeREInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	mySql2.addSubPara(tContno);//指定传入的参数
	strSQL=mySql2.getString();

    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
    //判断是否查询成功
    if (!turnPage.strQueryResult)
    {
        return false;
    }
    //alert(turnPage.strQueryResult);
    //查询成功则拆分字符串，返回二维数组

    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    //设置初始化过的MULTILINE对象

    turnPage.pageDisplayGrid = LCInsuredGrid;
    //保存SQL语句
    turnPage.strQuerySql = strSQL;
    //设置查询起始位置
    turnPage.pageIndex = 0;
    //在查询结果数组中取出符合页面显示大小设置的数组
    arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
    //调用MULTILINE对象显示查询结果
    displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}

function showCustomerImpart(){
    var tContNo = document.all('ContNo').value;
//    var strsql = "select 1 from dual where '00' in (select substr(d.casepoltype,0,2) from lmdutygetclm d where d.getdutycode in (select getdutycode from lcget where contno = '"+tContNo+"'))";
    
    var strsql = "";
	var sqlid3="PEdorTypeREInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql3.setSqlId(sqlid3);//指定使用的Sql的id
	mySql3.addSubPara(tContNo);//指定传入的参数
	strsql=mySql3.getString();
    
    var aResult = easyExecSql(strsql,1,0);
    //alert(aResult[0][0]);
    if(aResult != null){
         if(aResult[0][0] == "1"){
              document.all('ReFlag').value = "App";
              divAppnt.style.display = '';
              divImpart.style.display = '';
         }
    }
}

function queryCalInterest() {
//	var strsql = "select nvl(standbyflag3,'none') from lpedoritem where edoracceptno = '"+document.all('EdorAcceptNo').value+"' and contno = '"+document.all('ContNo').value+"' and edortype = '"+document.all('EdorType').value+"'";
    
    var strsql = "";
	var sqlid4="PEdorTypeREInputSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql4.setSqlId(sqlid4);//指定使用的Sql的id
	mySql4.addSubPara(document.all('EdorAcceptNo').value);//指定传入的参数
	mySql4.addSubPara(document.all('ContNo').value);
	mySql4.addSubPara(document.all('EdorType').value);
	strsql=mySql4.getString();
    
    var aResult = easyExecSql(strsql,1,0);
    if (aResult[0][0] == "off") {
    	document.all('isCalInterest').checked = false;
    }
}

function showRelaInsured(){
    var tContNo = document.all('ContNo').value;
//    var str5  = "select customerno from lcinsuredrelated where polno in (select polno from lcpol where contno = '" + tContNo +"' and appflag = '1')";
    
    var str5 = "";
	var sqlid5="PEdorTypeREInputSql5";
	var mySql5=new SqlClass();
	mySql5.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql5.setSqlId(sqlid5);//指定使用的Sql的id
	mySql5.addSubPara(tContNo);//指定传入的参数
	str5=mySql5.getString();
    
    var aResult = easyExecSql(str5,1,0);
    if(aResult != null && aResult != ""){
         divInsured.style.display = '';
         divImpart.style.display = '';
       document.all('ReFlag').value = "Yes";
     document.all('CustomerNo').value = aResult[0][0];
    }
}

function Edorquery()
{
    var ButtonFlag = top.opener.document.all('ButtonFlag').value;
    if(ButtonFlag!=null && ButtonFlag=="1")
    {
       try { divEdorquery.style.display = "none"; } catch (ex) {}
    }
    else
    {
        try { divEdorquery.style.display = ""; } catch (ex) {}
    }
}


function showimpart(){
     var tEdorNo = document.all('EdorNo').value;
     var tContNo = document.all('ContNo').value;

    var strre = "";
	var sqlid6="PEdorTypeREInputSql6";
	var mySql6=new SqlClass();
	mySql6.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql6.setSqlId(sqlid6);//指定使用的Sql的id
	mySql6.addSubPara(tContNo);//指定传入的参数
	strre=mySql6.getString();
     
//     var aResult = easyExecSql("select insuredno from lcpol where contno = '" + tContNo +"' and mainpolno = polno and appflag = '1'");
    var aResult = easyExecSql(strre);
     if(aResult == null){
           alert("查询主险信息失败！");
           return false;
     }
/*     var Strvar = "select impartver,impartcode,impartcontent,impartparammodle from lpcustomerimpart where edortype = 'RE' and edorno = '"
         + tEdorNo + "' and customerno = '" + aResult[0][0] + "'";
*/     
    var Strvar = "";
	var sqlid7="PEdorTypeREInputSql7";
	var mySql7=new SqlClass();
	mySql7.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql7.setSqlId(sqlid7);//指定使用的Sql的id
	mySql7.addSubPara(tEdorNo);//指定传入的参数
	mySql7.addSubPara(aResult[0][0]);
	Strvar=mySql7.getString();
     
     var arrSelected = new Array();
     turnPage.strQueryResult  = easyQueryVer3(Strvar, 1, 0, 1);
     //判断是否查询成功
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

function showotherimpart()
{
     var ReFlag = document.all('ReFlag').value;
     var tEdorNo = document.all('EdorNo').value;
     //alert(ReFlag);
     var CustomerNo;
     if(ReFlag == "Yes"){
          CustomerNo = document.all('CustomerNo').value;
          //alert(CustomerNo);
     }
     else if(ReFlag== "App"){
          var tContNo = document.all('ContNo').value;

    var strSQLre = "";
	var sqlid8="PEdorTypeREInputSql8";
	var mySql8=new SqlClass();
	mySql8.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql8.setSqlId(sqlid8);//指定使用的Sql的id
	mySql8.addSubPara(tContNo);//指定传入的参数
	strSQLre=mySql8.getString();
	
//          var aResult = easyExecSql("select appntno from lcpol where contno = '" + tContNo +"' and mainpolno = polno and appflag = '1'");
    var aResult = easyExecSql(strSQLre);

        if(aResult == null){
               alert("查询主险信息失败！");
               return false;
        }
          CustomerNo = aResult[0][0];
     }
     else{
          return false;
     }
/*     var Strvar = "select impartver,impartcode,impartcontent,impartparammodle from lpcustomerimpart where edortype = 'RE' and edorno = '"
         + tEdorNo + "' and customerno = '" + CustomerNo + "'";
*/     
    var Strvar = "";
	var sqlid9="PEdorTypeREInputSql9";
	var mySql9=new SqlClass();
	mySql9.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql9.setSqlId(sqlid9);//指定使用的Sql的id
	mySql9.addSubPara(tEdorNo);//指定传入的参数
	mySql9.addSubPara(CustomerNo);
	Strvar=mySql9.getString();
     
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
     turnPage.pageDisplayGrid = AppntImpartGrid;
     //保存SQL语句
     turnPage.strQuerySql = Strvar;
     //设置查询起始位置
     turnPage.pageIndex = 0;
     //在查询结果数组中取出符合页面显示大小设置的数组
     arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
     //调用MULTILINE对象显示查询结果
     displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

}

