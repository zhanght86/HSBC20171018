/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : 辛玉强 <XinYQ@sinosoft.com.cn>
 * @version  : 1.00, 1.01
 * @date     : 2006-07-04, 2006-12-05
 * @direction: 团单保全人工核保整单层主脚本
 ******************************************************************************/

//<!-- JavaScript Document BGN -->


/*============================================================================*/

var showInfo;
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();               //全局变量, 翻页必须有
var turnPageInsuredGrid = new turnPageClass();    //全局变量, 被保人队列, 查询结果翻页
var turnPagePolGrid = new turnPageClass();        //全局变量, 分单险种队列, 查询结果翻页

/*============================================================================*/

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
            queryPolGrid();
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
            //nothing
        }
        catch (ex) {}
    }
}

/*============================================================================*/

/**
 * 查询团单下的分单列表
 */
function queryInsuredGrid()
{
//    var QuerySQL = "select a.EdorNo, "
//                 +        "a.EdorType, "
//                 +        "a.ContNo, "
//                 +        "a.InsuredNo, "
//                 +        "a.InsuredName, "
//                 +        "concat(concat(a.InsuredSex , '-') , "
//                 +          "(select CodeName "
//                 +             "from LDCode "
//                 +            "where 1 = 1 "
//                 +              "and CodeType = 'sex' "
//                 +              "and Code = a.InsuredSex)), "
//                 +        "a.InsuredBirthday, "
//                 +        "concat(concat(a.InsuredIDType , '-' , "
//                 +          "(select CodeName "
//                 +             "from LDCode "
//                 +            "where 1 = 1 "
//                 +              "and CodeType = 'idtype' "
//                 +              "and Code = a.InsuredIDType)), "
//                 +        "a.InsuredIDNo, "
//                 +        "a.UWFlag "
//                 +   "from LPCont a "
//                 +  "where 1 = 1 "
//                 +    "and a.EdorNo in "
//                 +        "(select EdorNo "
//                 +           "from LPGrpEdorItem "
//                 +          "where 1 = 1 "
//                 +             getWherePart("EdorAcceptNo", "EdorAcceptNo")
//                 +        ") "
//                 +  getWherePart("a.GrpContNo", "GrpContNo")
//                 +    "and exists "
//                 +        "(select 'X' "
//                 +           "from LCInsured "
//                 +          "where 1 = 1 "
//                 +            "and ContNo = a.ContNo "
//                 +            "and InsuredNo = a.InsuredNo "
//                 +             getWherePart("ContNo", "ContNo_Srh")
//                 +             getWherePart("InsuredNo", "InsuredNo_Srh")
//                 +             getWherePart("CustomerSeqNo", "CustomerSeqNo_Srh")
//                 +             getWherePart("Name", "InsuredName_Srh", "like")
//                 +             getWherePart("Sex", "Sex_Srh")
//                 +             getWherePart("Birthday", "Birthday_Srh")
//                 +             getWherePart("IDType", "IDType_Srh")
//                 +             getWherePart("IDNo", "IDNo_Srh", "like", "0")
//                 +        ") "
//                 +      "and a.PolType <> '2' "
//                 +      "and a.PolType <> '3' "
//                 +    "order by a.ContNo asc, a.InsuredNo asc";
//    alert(QuerySQL);
    var QuerySQL = "";
    var sqlid1="GEdorManuUWInsuredSql1";
    var mySql1=new SqlClass();
    mySql1.setResourceName("bq.GEdorManuUWInsuredSql"); //指定使用的properties文件名
    mySql1.setSqlId(sqlid1);//指定使用的Sql的id
    mySql1.addSubPara(window.document.getElementsByName("EdorAcceptNo")[0].value);//指定传入的参数
    mySql1.addSubPara(window.document.getElementsByName("GrpContNo")[0].value);//指定传入的参数
    mySql1.addSubPara(window.document.getElementsByName("ContNo_Srh")[0].value);//指定传入的参数
    mySql1.addSubPara(window.document.getElementsByName("InsuredNo_Srh")[0].value);//指定传入的参数
    mySql1.addSubPara(window.document.getElementsByName("CustomerSeqNo_Srh")[0].value);//指定传入的参数
    mySql1.addSubPara(window.document.getElementsByName("InsuredName_Srh")[0].value);//指定传入的参数
    mySql1.addSubPara(window.document.getElementsByName("Sex_Srh")[0].value);//指定传入的参数
    mySql1.addSubPara(window.document.getElementsByName("Birthday_Srh")[0].value);//指定传入的参数
    mySql1.addSubPara(window.document.getElementsByName("IDType_Srh")[0].value);//指定传入的参数
    mySql1.addSubPara(window.document.getElementsByName("IDNo_Srh")[0].value);//指定传入的参数
    QuerySQL=mySql1.getString();    
    try
    {
        turnPageInsuredGrid.pageDivName = "divTurnPageInsuredGrid";
        turnPageInsuredGrid.queryModal(QuerySQL, InsuredGrid);
    }
    catch (ex)
    {
        alert("警告：查询保全团单下的分单信息出现异常！ ");
        return;
    }
    //查询分单列表失败则清空信息
    if (InsuredGrid.mulLineCount <= 0)
    {
        clearInsuredAndPol();
        resetContUW();
    }
}

/*============================================================================*/
//生存调查报告
function showRReport()
{
    var cGrpContNo,cContNo,cPrtNo,pEdorNo,pEdorType;
    try{
       cGrpContNo = fm.GrpContNo.value;
     cContNo = fm.ContNo.value;
     cPrtNo = fm.PrtNo.value;
     pEdorNo = fm.EdorNo.value;
     pEdorType = fm.EdorType.value;
    }catch(ex){}
    if (cContNo == null || trim(cContNo) == "" || pEdorNo == null || trim(pEdorNo) == ""){
        alert("请先查询并选择您要操作的分单！ ");
        return;
    }
  if (cContNo != "")
  {
    window.open("../uw/GrpUWManuRReportMain.jsp?GrpContNo="+cGrpContNo+"&ContNo1="+cContNo+"&PrtNo="+cPrtNo+"&EdorNo="+pEdorNo+"&EdorType="+pEdorType,"window1");
  }
}

//保单明细信息
function showPolDetail()
{
    var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    try{
    cContNo=document.all("ContNo").value ;
    cPrtNo=document.all("PrtNo").value;
    cGrpContNo=document.all("GrpContNo").value;
    }
    catch(ex){}
    if (cContNo != "")
    {
        mSwitch.deleteVar("ContNo");
        mSwitch.addVar("ContNo", "", cContNo );
        mSwitch.updateVar("ContNo", "", cContNo);
        mSwitch.deleteVar("ProposalContNo");
        mSwitch.addVar("ProposalContNo", "", cContNo );
        mSwitch.updateVar("ProposalContNo", "", cContNo);
        mSwitch.deleteVar("PrtNo");
        mSwitch.addVar("PrtNo", "", cPrtNo );
        mSwitch.updateVar("PrtNo", "", cPrtNo);
        mSwitch.deleteVar("GrpContNo");
        mSwitch.addVar("GrpContNo", "", cGrpContNo );
        mSwitch.updateVar("GrpContNo", "", cGrpContNo);
        window.open("../sys/AllProQueryGMain.jsp?LoadFlag=16&checktype=2&ContType=2&Auditing=1&ProposalGrpContNo="+cGrpContNo+"&ContNo="+cContNo+"&NameFlag=0","window1");
    }
    else
    {
        alert("请先选择保单!");
    }
}

/**
 * 团体分单特约录入
 */
function showSpec()
{
    try{
    var tContNo = fm.ContNo.value;
    var tPrtNo = fm.PrtNo.value;
    var tEdorNo = fm.EdorNo.value;
    var tEdorType = fm.EdorType.value;
    var tMissionID = fm.MissionID.value;
    var tSubMissionID = fm.SubMissionID.value;
    var tPolNo = fm.PolNo.value;
  }catch (ex) {}
    if (tContNo == null || trim(tContNo) == "" || tEdorNo == null || trim(tEdorNo) == "" || tPolNo == null || trim(tPolNo) == "")
    {
        alert("请先查询并选择您要操作的分单并选择险种！ ");
        return;
    }

  if (tContNo != ""&& tEdorNo !="" && tMissionID != "" )
  {
    var strTran = "ContNo="+tContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID
                             +"&EdorNo="+tEdorNo+"&EdorType="+tEdorType+"&PrtNo="+tPrtNo+"&PolNo="+tPolNo;
   OpenWindowNew("./EdorUWManuSpecMain.jsp?"+strTran, "团体保全特约信息录入", "left");
    //window.open("./EdorUWManuSpecMain.jsp?"+strTran,"status:no;help:0;close:0;dialogWidth:1024px;dialogHeight:768px");
  }
  else
  {
    alert("数据传输错误!");
  }
}

//体检资料录入
function showHealth()
{
    var cContNo,cPrtNo,cGrpContNo,cInsuredNo,pEdorNo,pEdorType;
    try{
      cContNo = fm.ContNo.value;
      cPrtNo = fm.PrtNo.value;
      cGrpContNo = fm.GrpContNo.value;
      cInsuredNo = fm.InsuredNo.value;
      pEdorNo = fm.EdorNo.value;
      pEdorType = fm.EdorType.value;
  }catch(ex){}
    if (cContNo == null || trim(cContNo) == "" || pEdorNo == null || trim(pEdorNo) == ""){
        alert("请先查询并选择您要操作的分单！ ");
        return;
    }
  if (cContNo != "")
  {
//        var checkSQL="select StateFlag from LOPRTManager where standbyflag3='"+cGrpContNo+"' and OtherNo='"+cContNo+"' and standbyflag1=(select insuredno from lcinsured where grpcontno='"+cGrpContNo+"' and contno='"+cContNo+"')";
        var checkSQL = "";
        var sqlid1="GEdorManuUWInsuredSql2";
        var mySql1=new SqlClass();
        mySql1.setResourceName("bq.GEdorManuUWInsuredSql"); //指定使用的properties文件名
        mySql1.setSqlId(sqlid1);//指定使用的Sql的id
        mySql1.addSubPara(cGrpContNo);//指定传入的参数
        mySql1.addSubPara(cContNo);//指定传入的参数
        mySql1.addSubPara(cGrpContNo);//指定传入的参数
        mySql1.addSubPara(cContNo);//指定传入的参数
        checkSQL=mySql1.getString();          
        turnPage.strQueryResult  = easyQueryVer3(checkSQL, 1, 0, 1);
  var arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
 if(arrSelected==null||arrSelected==2)
  {
      window.open("../uw/GrpUWManuHealthMain.jsp?ContNo1="+cContNo+"&PrtNo="+cPrtNo+"&GrpContNo="+cGrpContNo+"&InsuredNo="+cInsuredNo+"&EdorNo="+pEdorNo+"&EdorType="+pEdorType,"window1");
     }
    else
        if(arrSelected==0||arrSelected==1)
    {
        var showStr="该合同已经发送了体检通知书，不能再进行录入";
      var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
      //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    }
}
  else
  {
    showInfo.close();
    alert("请先选择保单!");
  }
}

/**
 * 查询分单列表失败则清空信息
 */
function clearInsuredAndPol()
{
    try
    {
        document.getElementsByName("ContNo")[0].value = "";
        document.getElementsByName("AppntNo")[0].value = "";
        document.getElementsByName("AppntName")[0].value = "";
        document.getElementsByName("AppntSex")[0].value = "";
        document.getElementsByName("AppntSexName")[0].value = "";
        document.getElementsByName("InsuredNo")[0].value = "";
        document.getElementsByName("InsuredName")[0].value = "";
        document.getElementsByName("InsuredSex")[0].value = "";
        document.getElementsByName("InsuredSexName")[0].value = "";
        document.getElementsByName("InsuredBirthday")[0].value = "";
        document.getElementsByName("InsuredIDType")[0].value = "";
        document.getElementsByName("InsuredIDTypeName")[0].value = "";
        document.getElementsByName("InsuredIDNo")[0].value = "";
        PolGrid.clearData();
    }
    catch (ex) {}
}

/*============================================================================*/

/**
 * 查询选中的被保人资料和险种
 */
function queryInsuredAndPol()
{
    queryInsuredInfo();
    queryPolGrid();
}

/*============================================================================*/

function initPolInfo()
{
    var nSelNo;
    try
    {
        nSelNo = PolGrid.getSelNo() - 1;
    }
    catch (ex) {}
    if (nSelNo != null && nSelNo >= 0)
    {
        var PolNo, UWState;
        try
        {
            PolNo = PolGrid.getRowColData(nSelNo, 3);
            //alert(PolNo);
            UWState = PolGrid.getRowColData(nSelNo, 11);
        }
        catch (ex) {}
        if (PolNo != null && PolNo != "")
        {
            try
            {
                document.getElementsByName("PolNo")[0].value = PolNo;
                document.getElementsByName("UWState")[0].value = UWState;
                showOneCodeName('gedorpoluwstate','UWState','UWStateName');
            }
            catch (ex) {}
            //团体险种核保意见
//            QuerySQL = "select a.UWIdea "
//                     +   "from LPUWMaster a "
//                     +  "where 1 = 1 "
//                     +    "and a.EdorNo = '" + fm.EdorNo.value + "' and a.EdorType = '" + fm.EdorType.value + "' and a.PolNo = '" + fm.PolNo.value + "' "
            //alert(QuerySQL);
            var QuerySQL = "";
            var sqlid1="GEdorManuUWInsuredSql3";
            var mySql1=new SqlClass();
            mySql1.setResourceName("bq.GEdorManuUWInsuredSql"); //指定使用的properties文件名
            mySql1.setSqlId(sqlid1);//指定使用的Sql的id
            mySql1.addSubPara(fm.EdorNo.value);//指定传入的参数
            mySql1.addSubPara(fm.EdorType.value);//指定传入的参数
            mySql1.addSubPara(fm.PolNo.value);//指定传入的参数
            QuerySQL=mySql1.getString();       
	                        
            try
            {
                arrResult = easyExecSql(QuerySQL, 1, 0);
            }
            catch (ex)
            {
                alert("警告：查询上次保存的险种核保意见出现异常！ ");
                return;
            }
            if (arrResult != null)
            {
                try
                {
                    document.getElementsByName("UWIdea")[0].value = arrResult[0][0];
                }
                catch (ex) {}
            }
        }
    }
}

/**
 * 查询选中的被保人资料信息
 */
function queryInsuredInfo()
{
    var nSelNo;
    try
    {
        nSelNo = InsuredGrid.getSelNo() - 1;
    }
    catch (ex) {}
    if (nSelNo != null && nSelNo >= 0)
    {
        var sEdorNo, SEdorType, sContNo, sInsuredNo;
        try
        {
            sEdorNo = InsuredGrid.getRowColData(nSelNo, 1);
            SEdorType = InsuredGrid.getRowColData(nSelNo, 2);
            sContNo = InsuredGrid.getRowColData(nSelNo, 3);
            sInsuredNo = InsuredGrid.getRowColData(nSelNo, 4);
        }
        catch (ex) {}
        if (sInsuredNo != null && sInsuredNo != "")
        {
            try
            {
                document.getElementsByName("EdorNo")[0].value = sEdorNo;
                document.getElementsByName("EdorType")[0].value = SEdorType;
                document.getElementsByName("ContNo")[0].value = sContNo;
                document.getElementsByName("InsuredNo")[0].value = sInsuredNo;
            }
            catch (ex) {}
        }
        //查询获取被保人其它资料
        var  arrResult;
//        QuerySQL = "select a.AppntNo, "
//                 +        "a.AppntName, "
//                 +        "a.AppntSex, "
//                 +        "(select CodeName "
//                 +           "from LDCode "
//                 +          "where 1 = 1 "
//                 +            "and CodeType = 'sex' "
//                 +            "and Code = a.AppntSex), "
//                 +        "a.InsuredName, "
//                 +        "a.InsuredSex, "
//                 +        "(select CodeName "
//                 +           "from LDCode "
//                 +          "where 1 = 1 "
//                 +            "and CodeType = 'sex' "
//                 +            "and Code = a.InsuredSex), "
//                 +        "a.InsuredBirthday, "
//                 +        "a.InsuredIDType, "
//                 +        "(select CodeName "
//                 +           "from LDCode "
//                 +          "where 1 = 1 "
//                 +            "and CodeType = 'idtype' "
//                 +            "and Code = a.InsuredIDType), "
//                 +        "a.InsuredIDNo,"
//                 +        "(select prtno from lcinsured where contno = a.contno and insuredno = a.insuredno)"
//                 +   "from LPCont a "
//                 +  "where 1 = 1 "
//                 +  getWherePart("a.EdorNo", "EdorNo")
//                 +  getWherePart("a.ContNo", "ContNo")
//                 +    "and a.PolType <> '2' "
//                 +    "and a.PolType <> '3' ";
        //alert(QuerySQL);
        var QuerySQL = "";
        var sqlid1="GEdorManuUWInsuredSql4";
        var mySql1=new SqlClass();
        mySql1.setResourceName("bq.GEdorManuUWInsuredSql"); //指定使用的properties文件名
        mySql1.setSqlId(sqlid1);//指定使用的Sql的id
        mySql1.addSubPara(window.document.getElementsByName("EdorNo")[0].value);//指定传入的参数
        mySql1.addSubPara(window.document.getElementsByName("ContNo")[0].value);//指定传入的参数
        QuerySQL=mySql1.getString();       
        
        try
        {
            arrResult = easyExecSql(QuerySQL, 1, 0);
        }
        catch (ex)
        {
            alert("警告：查询获取被保人资料信息出现异常！ ");
            return;
        }
        if (arrResult != null)
        {
            try
            {
                document.getElementsByName("AppntNo")[0].value = arrResult[0][0];
                document.getElementsByName("AppntName")[0].value = arrResult[0][1];
                document.getElementsByName("AppntSex")[0].value = arrResult[0][2];
                document.getElementsByName("AppntSexName")[0].value = arrResult[0][3];
                document.getElementsByName("InsuredName")[0].value = arrResult[0][4];
                document.getElementsByName("InsuredSex")[0].value = arrResult[0][5];
                document.getElementsByName("InsuredSexName")[0].value = arrResult[0][6];
                document.getElementsByName("InsuredBirthday")[0].value = arrResult[0][7];
                document.getElementsByName("InsuredIDType")[0].value = arrResult[0][8];
                document.getElementsByName("InsuredIDTypeName")[0].value = arrResult[0][9];
                document.getElementsByName("InsuredIDNo")[0].value = arrResult[0][10];
                document.getElementsByName("PrtNo")[0].value = arrResult[0][11];
            }
            catch (ex) {}
        }
    } //nSelNo >= 0
}

/*============================================================================*/

/**
 * 查询选中的保单的险种
 */
function queryPolGrid()
{
//    var QuerySQL = "select a.EdorNo, "
//                 +        "a.EdorType, "
//                 +        "a.PolNo, "
//                 +        "a.RiskCode, "
//                 +        "(select RiskName from LMRisk where RiskCode = a.RiskCode), "
//                 +        "a.Amnt, "
//                 +        "a.Mult, "
//                 +        "a.StandPrem, "
//                 +        "(select (case when sum(Prem) is not null then sum(Prem) else 0 end) "
//                 +           "from LPPrem "
//                 +          "where 1 = 1 "
//                 +            "and EdorNo = a.EdorNo "
//                 +            "and PolNo = a.PolNo "
//                 +            "and PayPlanType = '01'), "
//                 +        "(select (case when sum(Prem) is not null then sum(Prem) else 0 end) "
//                 +           "from LPPrem "
//                 +          "where 1 = 1 "
//                 +            "and EdorNo = a.EdorNo "
//                 +            "and PolNo = a.PolNo "
//                 +            "and PayPlanType = '02'), "
//                 +        "a.UWFlag "
//                 +   "from LPPol a "
//                 +  "where 1 = 1 "
//                 +    "and a.EdorNo in (select EdorNo from LPGrpEdorItem where 1 = 1 " + getWherePart("EdorAcceptNo", "EdorAcceptNo") + ") "
//                 +    "and PolNo = a.PolNo "
//                 +  getWherePart("a.ContNo", "ContNo")
//                 +    "and a.PolTypeFlag <> '2' "
//                 +    "and a.PolTypeFlag <> '3' "
//                 +  "order by a.PolNo asc, a.RiskCode asc";
    //alert(QuerySQL);
    var QuerySQL = "";
    var sqlid1="GEdorManuUWInsuredSql5";
    var mySql1=new SqlClass();
    mySql1.setResourceName("bq.GEdorManuUWInsuredSql"); //指定使用的properties文件名
    mySql1.setSqlId(sqlid1);//指定使用的Sql的id
    mySql1.addSubPara(window.document.getElementsByName("EdorAcceptNo")[0].value);//指定传入的参数
    mySql1.addSubPara(window.document.getElementsByName("ContNo")[0].value);//指定传入的参数
    QuerySQL=mySql1.getString();      
    try
    {
        turnPagePolGrid.pageDivName = "divTurnPagePolGrid";
        turnPagePolGrid.queryModal(QuerySQL, PolGrid);
    }
    catch (ex)
    {
        alert("警告：查询保全分单险种信息出现异常！ ");
        return;
    }
}

/*============================================================================*/

/**
 * 检查操作员是否具有申请核保权限
 */
function isEdorPopedom()
{
    var sOperator;
    try
    {
        sOperator = document.getElementsByName("LoginOperator")[0].value;
    }
    catch (ex) {}
    if (sOperator == null || trim(sOperator) == "")
    {
        alert("无法获取当前登录用户信息。检查保全核保权限失败！ ");
        return false;
    }
    else
    {

        var  arrResult;
//        QuerySQL = "select EdorPopedom "
//                 +   "from LDUWUser "
//                 +  "where UserCode = '" + sOperator + "'";
        //alert(QuerySQL);
        var QuerySQL = "";
        var sqlid1="GEdorManuUWInsuredSql6";
        var mySql1=new SqlClass();
        mySql1.setResourceName("bq.GEdorManuUWInsuredSql"); //指定使用的properties文件名
        mySql1.setSqlId(sqlid1);//指定使用的Sql的id
        mySql1.addSubPara(sOperator);//指定传入的参数
        QuerySQL=mySql1.getString();           
        try
        {
            arrResult = easyExecSql(QuerySQL, 1, 0);
        }
        catch (ex)
        {
            alert("警告：查询当前登录用户保全核保权限出现异常！ ");
            return false;
        }
        if (arrResult == null || trim(arrResult[0][0]) != "1")
        {
            alert("对不起，您没有保全核保权限！ ");
            return false;
        }
    }
    return true;
}

/*============================================================================*/

/**
 * 被保人健康告知查询
 */
function queryHealthApprize()
{
    var sInsuredNo;
    try
    {
        sInsuredNo = document.getElementsByName("InsuredNo")[0].value;
    }
    catch (ex) {}
    if (sInsuredNo == null || trim(sInsuredNo) == "")
    {
        alert("请先查询并选择您要查询的分单！ ");
        return;
    }
    var sOpenWinURL = "EdorHealthImpartQueryMain.jsp";
    var sParameters = "CustomerNo="+ sInsuredNo;
    OpenWindowNew(sOpenWinURL + "?" + sParameters, "被保人健康告知查询", "left");
}

/*============================================================================*/

/**
 * 被保人体检资料查询
 */
function queryBodyInspect()
{
    var sContNo, sInsuredNo;
    try
    {
        sContNo = document.getElementsByName("ContNo")[0].value;
        sInsuredNo = document.getElementsByName("InsuredNo")[0].value;
    }
    catch (ex) {}
    if (sContNo == null || trim(sContNo) == "" || sInsuredNo == null || trim(sInsuredNo) == "")
    {
        alert("请先查询并选择您要查询的分单！ ");
        return;
    }
    var sOpenWinURL = "../uw/UWBeforeHealthQMain.jsp";
    var sParameters = "ContNo=" + sContNo + "&CustomerNo="+ sInsuredNo;
    OpenWindowNew(sOpenWinURL + "?" + sParameters, "被保人体检资料查询", "left");
}

/*============================================================================*/

/**
 * 被保人保额累计提示
 */
function queryAccumulateAmnt()
{
    var sInsuredNo;
    try
    {
        sInsuredNo = document.getElementsByName("InsuredNo")[0].value;
    }
    catch (ex) {}
    if (sInsuredNo == null || trim(sInsuredNo) == "")
    {
        alert("请先查询并选择您要查询的分单！ ");
        return;
    }
    var sOpenWinURL = "../uw/AmntAccumulateMain.jsp";
    var sParameters = "CustomerNo="+ sInsuredNo;
    OpenWindowNew(sOpenWinURL + "?" + sParameters, "被保人保额累计提示", "left");
}

/*============================================================================*/

/**
 * 被保人已承保保单查询
 */
function queryAlreadyProposal()
{
    var sInsuredNo;
    try
    {
        sInsuredNo = document.getElementsByName("InsuredNo")[0].value;
    }
    catch (ex) {}
    if (sInsuredNo == null || trim(sInsuredNo) == "")
    {
        alert("请先查询并选择您要查询的分单！ ");
        return;
    }
    var sOpenWinURL = "../uw/ProposalQueryMain.jsp";
    var sParameters = "CustomerNo="+ sInsuredNo;
    OpenWindowNew(sOpenWinURL + "?" + sParameters, "被保人已承保保单查询", "left");
}

/*============================================================================*/

/**
 * 被保人未承保投保单查询
 */
function queryUnProposalCont()
{
    var sInsuredNo;
    try
    {
        sInsuredNo = document.getElementsByName("InsuredNo")[0].value;
    }
    catch (ex) {}
    if (sInsuredNo == null || trim(sInsuredNo) == "")
    {
        alert("请先查询并选择您要查询的分单！ ");
        return;
    }
    var sOpenWinURL = "../uw/NotProposalQueryMain.jsp";
    var sParameters = "CustomerNo="+ sInsuredNo;
    OpenWindowNew(sOpenWinURL + "?" + sParameters, "被保人未承保投保单查询", "left");
}

/*============================================================================*/

/**
 * 被保人既往保全信息查询
 */
function queryLastEdorTrack()
{
    var sEdorAcceptNo, sInsuredNo;
    try
    {
        sEdorAcceptNo = document.getElementsByName("EdorAcceptNo")[0].value;
        sInsuredNo = document.getElementsByName("InsuredNo")[0].value;
    }
    catch (ex) {}
    if (sEdorAcceptNo == null || trim(sEdorAcceptNo) == "" || sInsuredNo == null || trim(sInsuredNo) == "")
    {
        alert("请先查询并选择您要查询的分单！ ");
        return;
    }
    var sOpenWinURL = "EdorAgoQueryMain.jsp";
    var sParameters = "EdorAcceptNo=" + sEdorAcceptNo + "&CustomerNo="+ sInsuredNo;
    OpenWindowNew(sOpenWinURL + "?" + sParameters, "被保人既往保全信息查询", "left");
}

/*============================================================================*/

/**
 * 被保人既往理赔信息查询
 */
function queryLastClaimTrack()
{
    var sInsuredNo;
    try
    {
        sInsuredNo = document.getElementsByName("InsuredNo")[0].value;
    }
    catch (ex) {}
    if (sInsuredNo == null || trim(sInsuredNo) == "")
    {
        alert("请先查询并选择您要查询的分单！ ");
        return;
    }
    var sOpenWinURL = "../uw/ClaimQueryMain.jsp";
    var sParameters = "CustomerNo="+ sInsuredNo;
    OpenWindowNew(sOpenWinURL + "?" + sParameters, "被保人既往理赔信息查询", "left");
}

/*============================================================================*/

/*********************************************************************
 *  加费录入
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showAdd()
{
//  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
//  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var tContNo,tEdorNo,tMissionID,tSubMissionID,tInsuredNo,tEdorAcceptNo;
  try{
     tContNo = fm.ContNo.value;
     tEdorNo = fm.EdorNo.value;
     tMissionID = fm.MissionID.value;
     tSubMissionID = fm.SubMissionID.value;
     tInsuredNo = fm.InsuredNo.value;
     tEdorAcceptNo = fm.EdorAcceptNo.value;
  }catch(ex){}
    if (tContNo == null || trim(tContNo) == "" || tEdorNo == null || trim(tEdorNo) == ""){
        alert("请先查询并选择您要操作的分单！ ");
        return;
    }
  var strTran = "ContNo="+tContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID
               +"&EdorNo="+tEdorNo+"&InsuredNo="+tInsuredNo+"&EdorAcceptNo="+tEdorAcceptNo;

  var newWindow = window.open("./EdorUWManuAddMain.jsp?"+strTran,"EdorUWManuAdd", 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

}
/*============================================================================*/

/**
 * 批量核保通过团单下所有分单
 */
function batchUWPassAll()
{
    if (!isEdorPopedom())    return;
    if (confirm("此操作将把本团单下的所有个单置为核保通过状态。 \n\n要继续吗？"))
    {
        var MsgContent = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
        var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=C&content=" + MsgContent;
        //showInfo = showModelessDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
        try
        {
            document.getElementsByName("EdorNo")[0].value = "";
            document.getElementsByName("EdorType")[0].value = "";
        }
        catch (ex) {}
        document.forms(0).action = "GEdorManuUWInsuredSave.jsp";
        document.forms(0).target = "fraSubmit";
        document.forms(0).submit();
    }
}

/*============================================================================*/

/**
 * 检查是否选择了某个分单进行核保
 */
function isSelectOneToUW()
{
    var nSelNo;
    try
    {
        nSelNo = InsuredGrid.getSelNo() - 1;
    }
    catch (ex) {}
    if (nSelNo == null || nSelNo < 0)
    {
        alert("请先选择您要核保的个单！ ");
        return false;
    }
    else
    {
        var sEdorNo, sEdorType;
        try
        {
            sEdorNo = InsuredGrid.getRowColData(nSelNo, 1);
            sEdorType = InsuredGrid.getRowColData(nSelNo, 2);
            if (sEdorNo == null || trim(sEdorNo) == "")
            {
                alert("无法获取所选个单的批单号。单张个单核保失败！ ");
                return false;
            }
            else
            {
                document.getElementsByName("EdorNo")[0].value = sEdorNo;
                document.getElementsByName("EdorType")[0].value = sEdorType;
            }
        }
        catch (ex) {}
    }
    return true;
}

/*============================================================================*/

/**
 * 保存个人险种核保结论
 */
function saveContUW()
{
    if (!isSelectOneToUW())    return;
    if (!verifyInput2())       return;
    if (!isEdorPopedom())      return;
    var MsgContent = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=C&content=" + MsgContent;
    //showInfo = showModelessDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    document.forms(0).action = "GEdorManuUWInsuredSave.jsp";
    document.forms(0).target = "fraSubmit";
    document.forms(0).submit();
}

/*============================================================================*/

/**
 * 重置团体保单核保结论
 */
function resetContUW()
{
    try
    {
        document.getElementsByName("UWState")[0].value = "";
        document.getElementsByName("UWStateName")[0].value = "";
        document.getElementsByName("UWIdea")[0].value = "";
    }
    catch (ex) {}
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

/*============================================================================*/


//<!-- JavaScript Document END -->
