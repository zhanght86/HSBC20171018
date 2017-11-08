//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="1";
var mFlag;
var sqlresourcename = "bq.PEdorTypeIOInputSql";

//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var turnPage1 = new turnPageClass();
var turnPagePolOldGrid = new turnPageClass();
var turnPagePolNewGrid = new turnPageClass();

function resetForm()
{
    //initForm();
    parent.location.reload();
}

function saveEdorTypeIO()
{
    if ((fm.OccupationCode.value == fm.OccupationCode_S.value) && (fm.OccupationType.value == fm.OccupationType_S.value))
    {
        alert('被保人资料未发生变更！');
        return;
    }
    if(fm.OccupationType.value == '拒保')
    {
        //alert('职业类别拒保,不能保存申请！');
        //return;
    }
    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:300px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    document.all('fmtransact').value = "QUERY||DETAIL";
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
            querynewPol();
            queryBackFee();
            top.opener.getEdorItemGrid();
        }
        catch (ex) {}
    }
}

//查询 该被保人还有其它保单下的职业类型需要变更
function queryOccupationInfo()
{
    var tContNo=  document.all('ContNo').value;
    var tInsuredNo = document.all('InsuredNo').value;

    var strSQL;

/*    strSQL =  " Select 1 From lccont Where contno <> '"
                        + tContNo + "' And insuredno='"
                        + tInsuredNo + "'";
            //alert(strSQL);
*/    
	var sqlid1="PEdorTypeIOInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(tContNo);//指定传入的参数
	mySql1.addSubPara(tInsuredNo);
	strSQL=mySql1.getString();
    
    var brr = easyExecSql(strSQL);

    if ( brr )
    {
        var showStr="提醒：该被保人还有其它保单下的职业类型需要变更！" ;
            var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr;
            //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
  }
}

/*********************************************************************
 *  查询返回明细信息时执行的函数,查询返回一个2维数组，数组下标从[0][0]开始
 *  参数  ：  查询返回的二维数组
 *  返回值：  无
 *********************************************************************
 */

function afterQuery( arrQueryResult )
{
    var arrResult = new Array();

    if( arrQueryResult != null )
    {
        arrResult = arrQueryResult;

        document.all( 'CustomerNo' ).value = arrResult[0][1];
        document.all( 'name').value = arrResult[0][2];
        /**
        alert("aa:"+arrResult[0][5]);
        document.all('Nationality').value = arrResult[0][5];
        document.all('Marriage').value=arrResult[0][6];
        document.all('Stature').value=arrResult[0][7];
        document.all('Avoirdupois').value=arrResult[0][8];
        document.all('ICNo').value=arrResult[0][9];
        document.all('HomeAddressCode').value=arrResult[0][10];
        document.all('HomeAddress').value=arrResult[0][11];
        document.all('PostalAddress').value=arrResult[0][12];
        document.all('ZipCode').value=arrResult[0][13];
        document.all('Phone').value=arrResult[0][14];
        document.all('Mobile').value=arrResult[0][15];
        document.all('EMail').value=arrResult[0][16];
        */
        // 查询保单明细
        queryInsuredDetail();
    }
}
function queryInsuredDetail()
{
    var tEdorNO;
    var tEdorType;
    var tPolNo;
    var tCustomerNo;

    tEdorNo = document.all('EdorNO').value;
    //alert(tEdorNo);
    tEdorType=document.all('EdorType').value;
    //alert(tEdorType);
    tPolNo=document.all('PolNo').value;
    //alert(tPolNo);
    tCustomerNo = document.all('CustomerNo').value;
    //alert(tCustomerNo);
    //top.location.href = "./InsuredQueryDetail.jsp?EdorNo=" + tEdorNo+"&EdorType="+tEdorType+"&PolNo="+tPolNo+"&CustomerNo="+tCustomerNo;
    parent.fraInterface.fm.action = "./InsuredQueryDetail.jsp";
    fm.submit();
    parent.fraInterface.fm.action = "./PedorTypeIOSubmit.jsp";
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


function querytImpartGrid() {
     var tEdorNo = document.all('EdorNo').value;
     var tContNo = document.all('ContNo').value;
     var tInsuredNo = document.all('InsuredNo').value;
     
//     var Strvar = "select impartver,impartcode,impartcontent,impartparammodle from lpcustomerimpart where edortype = 'IO' and edorno = '"+ tEdorNo + "' and customerno = '" + tInsuredNo + "'";
     
    var Strvar = "";
    var sqlid2="PEdorTypeIOInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	mySql2.addSubPara(tEdorNo);//指定传入的参数
	mySql2.addSubPara(tInsuredNo);
	Strvar=mySql2.getString();
    
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

/*********************************************************************
 * 前台查询客户基本信息
 *********************************************************************
 */

 function QueryCustomerInfo()
{
    var tContNo=top.opener.document.all('ContNo').value;
    var strSQL=""
    if(tContNo!=null || tContNo !='')
      {
/*      strSQL = "SELECT APPNTNAME,APPNTIDTYPE,APPNTIDNO,INSUREDNAME,INSUREDIDTYPE,INSUREDIDNO FROM LCCONT WHERE 1=1 AND "
                            +"CONTNO='"+tContNo+"'";
*/    
    var sqlid3="PEdorTypeIOInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql3.setSqlId(sqlid3);//指定使用的Sql的id
	mySql3.addSubPara(tContNo);//指定传入的参数
	strSQL=mySql3.getString();
    
    }
    else
    {
        alert('没有客户信息！');
    }
    var arrSelected = new Array();
    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
    //arrResult = easyExecSql("select * from LDGrp where GrpNo = '" + arrQueryResult[0][0] + "'", 1, 0);
  if(!turnPage.strQueryResult){
        alert("查询失败");
    }
    else
    {
  arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
  try {document.all('AppntName').value = arrSelected[0][0];} catch(ex) { }; //投保人姓名
  try {document.all('AppntIDType').value = arrSelected[0][1];} catch(ex) { }; //投保人证件类型
  try {document.all('AppntIDNo').value = arrSelected[0][2];} catch(ex) { }; //投保人证件号码
  //try {document.all('InsuredName').value = arrSelected[0][3];} catch(ex) { }; //被保人名称
  //try {document.all('InsuredIDType').value = arrSelected[0][4];} catch(ex) { }; //被保人证件类型
  //try {document.all('InsuredIDNo').value = arrSelected[0][5];} catch(ex) { }; //被保人证件号码

  var tInsuredNo=top.opener.document.all('InsuredNo').value;
    var strSQL2="";
    if(tInsuredNo!=null || tInsuredNo !='')
      {
/*      strSQL2 = "Select name,idtype,idno From lcinsured WHERE 1=1 AND "
                            +"INSUREDNO='"+tInsuredNo+"' and contno = '" +top.opener.document.all('ContNo').value+ "'";
*/    
    var sqlid4="PEdorTypeIOInputSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql4.setSqlId(sqlid4);//指定使用的Sql的id
	mySql4.addSubPara(tInsuredNo);//指定传入的参数
	mySql4.addSubPara(top.opener.document.all('ContNo').value);
	strSQL2=mySql4.getString();
    
    }
    else
    {
        alert('没有客户信息！');
    }
    var aResult = easyExecSql(strSQL2);
      if(aResult == null){
          alert("查询被保人信息失败！");
          return false;
      }
      try {document.all('InsuredName').value = aResult[0][0];} catch(ex) { }; //被保人名称
  try {document.all('InsuredIDType').value = aResult[0][1];} catch(ex) { }; //被保人证件类型
  try {document.all('InsuredIDNo').value = aResult[0][2];} catch(ex) { }; //被保人证件号码

  showOneCodeName('idtype','AppntIDType','AppntIDTypeName');
  showOneCodeName('idtype','InsuredIDType','InsuredIDTypeName');

  }
}
/*********************************************************************
 * 前台查询被保人职业类别及职业代码
 *********************************************************************
 */

 function queryInsuredOccupationInfo()
{
    var tInsuredNo=top.opener.document.all('InsuredNo').value;
    var strSQL2="";
    if(tInsuredNo!=null || tInsuredNo !='')
      {
/*      strSQL = "Select occupationtype,occupationcode,worktype From lcinsured WHERE 1=1 AND "
                            +"INSUREDNO='"+tInsuredNo+"' and contno = '" +top.opener.document.all('ContNo').value+ "'";
*/    
    var sqlid5="PEdorTypeIOInputSql5";
	var mySql5=new SqlClass();
	mySql5.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql5.setSqlId(sqlid5);//指定使用的Sql的id
	mySql5.addSubPara(tInsuredNo);//指定传入的参数
	mySql5.addSubPara(top.opener.document.all('ContNo').value);
	strSQL2=mySql5.getString();
    
    }
    else
    {
        alert('没有客户信息！');
    }
    var arrSelected = new Array();
    turnPage.strQueryResult  = easyQueryVer3(strSQL2, 1, 0, 1);
    //arrResult = easyExecSql("select * from LDGrp where GrpNo = '" + arrQueryResult[0][0] + "'", 1, 0);
  if(!turnPage.strQueryResult){
        alert("查询失败");
    }
    else
    {
  arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
  try {document.all('OccupationCode_S').value = arrSelected[0][1];} catch(ex) { }; //职业代码
  try {document.all('OccupationType_S').value = arrSelected[0][0];} catch(ex) { }; //职业类别
  try {document.all('Occupation_S').value = arrSelected[0][2];} catch(ex) { }; //职业类别
  
  //showOneCodeName('OccupationCode','OccupationCode_S','OccupationCode_SName');
  fm.OccupationCode_SName.value=getOccupationCodeName(document.all('OccupationCode_S').value);
  showOneCodeName('occupationtype','OccupationType_S','OccupationType_SName');
  }
}
/*********************************************************************
 * 前台查询某一被保人下险种信息放入Grid中
 *********************************************************************
 */

function queryPolInfo()
{

    var tContNo = top.opener.document.all('ContNo').value;
    var tInsuredNo =top.opener.document.all('InsuredNo').value;
    var tDate =top.opener.document.all('EdorValiDate').value;

//    var str33 = "select * from lcpol where contno = '" + tContNo + "' and insuredno = '" + tInsuredNo + "'";
    
    var str33 = "";
    var sqlid6="PEdorTypeIOInputSql6";
	var mySql6=new SqlClass();
	mySql6.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql6.setSqlId(sqlid6);//指定使用的Sql的id
	mySql6.addSubPara(tContNo);//指定传入的参数
	mySql6.addSubPara(tInsuredNo);
	str33=mySql6.getString();
    
    var ssResult = easyExecSql(str33,1,0);
    if (ssResult == null)
    {
//        str33 = "select insuredno from lcpol where contno = '" + tContNo + "'";
        
    var sqlid7="PEdorTypeIOInputSql7";
	var mySql7=new SqlClass();
	mySql7.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql7.setSqlId(sqlid7);//指定使用的Sql的id
	mySql7.addSubPara(tContNo);//指定传入的参数
	str33=mySql7.getString();
        
        ssResult = easyExecSql(str33,1,0);
        tInsuredNo =ssResult[0][0];
    }
    var strSQL="";

    //strSQL =  "Select distinct a.polno,m.riskname, case when u.amntflag = 1 then a.amnt else a.mult end,a.prem - nvl(f.addprem, 0),nvl(f.addprem, 0) From lcpol a Left Join lmrisk m on m.riskcode = a.riskcode left join lcduty b on b.polno = a.polno left join lmduty u on trim(u.dutycode) = substr(b.dutycode, 1, 6) left join (Select c.polno, Sum(prem) addprem From lcprem c Where payplantype = '02' and ContNo = '"+top.opener.document.all('ContNo').value+"' group by c.polno) f On f.polno = a.polno Where a.insuredno = '"+tInsuredNo+"' and a.ContNo = '"+top.opener.document.all('ContNo').value+"' and a.appflag in ('1','9') and a.cvalidate <= '" + tDate + "' and a.enddate > '" + tDate + "'";
    //modify by jiaqiangli 2009-11-10 保全与催收不共存 
    //另外也别限制了cvalidate和enddate 只需要appflag='1'就行了 前者可能多限制了预约新增附加险，后者排除了宽末申请的保全项的一年期短期险
//    strSQL =  "Select distinct a.polno,m.riskname, case when u.amntflag = 1 then a.amnt else a.mult end,a.prem - nvl(f.addprem, 0),nvl(f.addprem, 0) From lcpol a Left Join lmrisk m on m.riskcode = a.riskcode left join lcduty b on b.polno = a.polno left join lmduty u on trim(u.dutycode) = substr(b.dutycode, 1, 6) left join (Select c.polno, Sum(prem) addprem From lcprem c Where payplantype = '02' and ContNo = '"+top.opener.document.all('ContNo').value+"' group by c.polno) f On f.polno = a.polno Where a.insuredno = '"+tInsuredNo+"' and a.ContNo = '"+top.opener.document.all('ContNo').value+"' and a.appflag in ('1')";
    //alert(strSQL);
    
    var sqlid8="PEdorTypeIOInputSql8";
	var mySql8=new SqlClass();
	mySql8.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql8.setSqlId(sqlid8);//指定使用的Sql的id
	mySql8.addSubPara(top.opener.document.all('ContNo').value);//指定传入的参数
	mySql8.addSubPara(tInsuredNo);
	mySql8.addSubPara(top.opener.document.all('ContNo').value);
	strSQL=mySql8.getString();
    
    try
    {
        turnPagePolOldGrid.pageDivName = "divTurnPagePolOldGrid";
        turnPagePolOldGrid.queryModal(strSQL, PolOldGrid);
    }
    catch (ex) {}
}

/***************************************************************
 * 由职业代码查询出职业类别代码
 ***************************************************************
 */
function getdetailwork()
{
//var strSql = "select OccupationType from LDOccupation where OccupationCode='" + fm.OccupationCode.value+"'";

	var strSql = "";
	var sqlid9="PEdorTypeIOInputSql9";
	var mySql9=new SqlClass();
	mySql9.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql9.setSqlId(sqlid9);//指定使用的Sql的id
	mySql9.addSubPara(fm.OccupationCode.value);//指定传入的参数
	strSql=mySql9.getString();

var arrResult = easyExecSql(strSql);
if (arrResult != null)
{
   fm.OccupationType.value = arrResult[0][0];
   showOneCodeName('occupationtype','OccupationType','OccupationTypeName');
}
else
{
   fm.OccupationType.value = '';
}
}


function showOccupatioinInfo()
{
  var tInsuredNo=top.opener.document.all('InsuredNo').value;
  var tEdorNo=top.opener.document.all('EdorNo').value;
    var tEdorType=top.opener.document.all('EdorType').value;

  var strSQL="";

  if(tInsuredNo!=null || tInsuredNo !='')
    {
/*        strSQL = "select ";
      strSQL = "Select b.prem,b.prem1,occupationtype,occupationcode,worktype From lPinsured,(Select Sum(standprem) prem,Sum(prem-sumprem) prem1 From lppol Where edorno='"+tEdorNo+"' And edortype='IO')b Where insuredno='"+tInsuredNo+"' And contno='"+fm.ContNo.value+"' And edortype='"+fm.EdorType.value+"' and EdorNo = '"+top.opener.document.all('EdorType').value+"'";
      //alert("-----------"+strSQL+"------------");
*/      
	var sqlid10="PEdorTypeIOInputSql10";
	var mySql10=new SqlClass();
	mySql10.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql10.setSqlId(sqlid10);//指定使用的Sql的id
	mySql10.addSubPara(tEdorNo);//指定传入的参数
	mySql10.addSubPara(tInsuredNo);
	mySql10.addSubPara(fm.ContNo.value);
	mySql10.addSubPara(fm.EdorType.value);
	mySql10.addSubPara(top.opener.document.all('EdorType').value);
	strSQL=mySql10.getString();
      
    }
    else
    {
        alert('没有客户信息！');
    }
    var arrSelected = new Array();
    turnPage1.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
    //arrResult = easyExecSql("select * from LDGrp where GrpNo = '" + arrQueryResult[0][0] + "'", 1, 0);
  //alert(strSQL);
  //alert(turnPage1.strQueryResult);
  if(!turnPage1.strQueryResult){
        //alert("查询失败");
    }
    else
    {
  arrSelected = decodeEasyQueryResult(turnPage1.strQueryResult);
  try {document.all('OccupationType').value = arrSelected[0][2];} catch(ex) { }; //职业代码
  try {document.all('OccupationCode').value = arrSelected[0][3];} catch(ex) { }; //职业类别
  try {document.all('Occupation').value = arrSelected[0][4];} catch(ex) { }; //职业

  fm.OccupationCodeName.value=getOccupationCodeName(document.all('OccupationCode').value);
  //showOneCodeName('occupationcode','OccupationCode','OccupationCodeName');
  showOneCodeName('occupationtype','OccupationType','OccupationTypeName');
}

}
function showNotePad()
{
    var MissionID = top.opener.document.all("MissionID").value;
    var SubMissionID = top.opener.document.all("SubMissionID").value;;
    var ActivityID = top.opener.document.all("ActivityID").value;;
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

function querychgOcc(){
    var tInsuredNo=top.opener.document.all('InsuredNo').value;
    var tEdorNo = top.opener.document.all('EdorNo').value;
    var strSQL=""
    if(tInsuredNo!=null || tInsuredNo !='')
      {
/*      strSQL = "Select occupationtype,occupationcode,worktype From lpinsured WHERE 1=1 AND "
                            +"INSUREDNO='"+tInsuredNo+"' and EdorNo = '" + tEdorNo +"'";
    //alert("-----------"+strSQL+"------------");
*/    
    var sqlid11="PEdorTypeIOInputSql11";
	var mySql11=new SqlClass();
	mySql11.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql11.setSqlId(sqlid11);//指定使用的Sql的id
	mySql11.addSubPara(tInsuredNo);//指定传入的参数
	mySql11.addSubPara(tEdorNo);
	strSQL=mySql11.getString();
    
    }
    else
    {
        alert('没有客户信息！');
    }
    var arrSelected = new Array();
    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
    //arrResult = easyExecSql("select * from LDGrp where GrpNo = '" + arrQueryResult[0][0] + "'", 1, 0);
    if (turnPage.strQueryResult)
    {
        arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
        try {document.all('OccupationCode').value = arrSelected[0][1];} catch(ex) { }; //职业代码
        try {document.all('OccupationType').value = arrSelected[0][0];} catch(ex) { }; //职业类别
        try {document.all('Occupation').value = arrSelected[0][2];} catch(ex) { }; //职业
            
        fm.OccupationCodeName.value=getOccupationCodeName(document.all('OccupationCode').value);
      //  showOneCodeName('occupationcode','OccupationCode','OccupationCodeName');
        showOneCodeName('occupationtype','OccupationType','OccupationTypeName');
        querynewPol();
    }
}

function querynewPol()
{
    var tInsuredNo=document.all('InsuredNo').value;
    var tEdorno = document.all('EdorNo').value;
    var strSQL="";
/*    strSQL =  "Select distinct a.polno,m.riskname,case when u.amntflag = 1 then a.amnt else a.mult end,a.prem,nvl((select sum(getmoney) from LJSGetEndorse where polno = a.polno and endorsementno = a.edorno),0) From lppol a Left Join lmrisk m on m.riskcode = a.riskcode Left Join lcduty b on b.polno = a.polno Left Join LMDuty u on trim(u.dutycode) = substr(b.dutycode, 1, 6) where a.edortype = 'IO' and a.EdorNo = '"+tEdorno+"' and a.ContNo = '" + fm.ContNo.value + "'";
    //alert(strSQL);
*/    
    var sqlid12="PEdorTypeIOInputSql12";
	var mySql12=new SqlClass();
	mySql12.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql12.setSqlId(sqlid12);//指定使用的Sql的id
	mySql12.addSubPara(tEdorno);//指定传入的参数
	mySql12.addSubPara(fm.ContNo.value);
	strSQL=mySql12.getString();
    
    try
    {
        turnPagePolNewGrid.pageDivName = "divTurnPagePolNewGrid";
        turnPagePolNewGrid.queryModal(strSQL, PolNewGrid);
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

function getOccupationCodeName(OccupationCode)
{
//	var tSQL="select  OccupationName from LDOccupation where OccupationCode='"+OccupationCode+"'";
	
	var tSQL="";
	var sqlid13="PEdorTypeIOInputSql13";
	var mySql13=new SqlClass();
	mySql13.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql13.setSqlId(sqlid13);//指定使用的Sql的id
	mySql13.addSubPara(OccupationCode);//指定传入的参数
	tSQL=mySql13.getString();
	
	var ssResult = easyExecSql(tSQL,1,0);
    if (ssResult != null)
    {
        return ssResult[0][0];
    }		
	}    