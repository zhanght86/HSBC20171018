/*******************************************************************************
 * @direction: �ŵ���ȫ�˹��˱����������ű�
 ******************************************************************************/

//<!-- JavaScript Document BGN -->


/*============================================================================*/

var showInfo;                                    //ȫ�ֱ���, ��������ʾ����, ������
var turnPage = new turnPageClass();              //ȫ�ֱ���, ��ҳ������
var turnPageGrpPolGrid = new turnPageClass();    //ȫ�ֱ���, �ŵ����ֶ���, ��ҳ������
var turnPage2 = new turnPageClass();
var arrDataSet;
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes";
/*============================================================================*/

function initEdorType(cObj,dObj)
{
	mEdorType = " #1# and (not exists (select 1 from  LDUWUser b where usercode=#"+document.all("LoginOperator").value+"# and uwtype=#3# "
  +" and b.uwpopedom=#B#) or  code<>#S# and exists (select 1 from  LDUWUser b where usercode=#"+document.all("LoginOperator").value+"# and uwtype=#3# "
  +" and b.uwpopedom=#B#  ) ) ";
	showCodeList('edorgrpuwstate',[cObj,dObj],[0,1],null,mEdorType,1,1,207);
}

function actionKeyUp(cObj,dObj)
{	
	mEdorType = " #1# and (not exists (select 1 from  LDUWUser b where usercode=#"+document.all("LoginOperator").value+"# and uwtype=#3# "
  +" and b.uwpopedom=#B#) or  code<>#S# and exists (select 1 from  LDUWUser b where usercode=#"+document.all("LoginOperator").value+"# and uwtype=#3# "
  +" and b.uwpopedom=#B#  ) ) ";
	showCodeListKey('edorgrpuwstate',[cObj,dObj],[0,1],null,mEdorType,1,1,207);
}

/**
 * �ύ�����, ���������ݷ��غ�ִ�еĲ���
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
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
        case "succ":
        case "success":
            MsgPageURL = MsgPageURL + "S&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=350px");
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=350;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            //queryGrpPolGrid();
            break;
        default:
            MsgPageURL = MsgPageURL + "C&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=300;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
    }
    //���ļ������⴦��
    if (DealFlag == "succ" || DealFlag == "success")
    {
        try
        {
            top.opener.querySelfGrid();
        }
        catch (ex) {}
    }
}

/*============================================================================*/

/**
 * ��ѯ��ȫ������Ϣ
 */
function queryEdorAppInfo()
{
    var QuerySQL, arrResult;
    
    var sqlid824092232="DSHomeContSql824092232";
var mySql824092232=new SqlClass();
mySql824092232.setResourceName("bq.GEdorAppManuUWInputSql");//ָ��ʹ�õ�properties�ļ���
mySql824092232.setSqlId(sqlid824092232);//ָ��ʹ�õ�Sql��id
mySql824092232.addSubPara(fm.EdorAcceptNo.value);//ָ������Ĳ���
QuerySQL=mySql824092232.getString();
    
//    QuerySQL = "select a.OtherNo, "
//             +        "a.OtherNoType, "
//             +        "(select CodeName "
//             +           "from LDCode "
//             +          "where 1 = 1 "
//             +            "and CodeType = 'gedornotype' "
//             +            "and Code = a.OtherNoType), "
//             +        "a.EdorAppName, "
//             +        "a.AppType, "
//             +        "(select CodeName "
//             +           "from LDCode "
//             +          "where 1 = 1 "
//             +            "and CodeType = 'edorapptype' "
//             +            "and Code = a.AppType), "
//             +        "a.ManageCom, "
//             +        "(select Name from LDCom where ComCode = a.ManageCom), "
//             +        "a.GetMoney, "
//             +        "a.GetInterest "
//             +   "from LPEdorApp a "
//             +  "where 1 = 1 "
//             + getWherePart("a.EdorAcceptNo", "EdorAcceptNo");
    //alert(QuerySQL);
    try
    {
        arrResult = easyExecSql(QuerySQL, 1, 0);
    }
    catch (ex)
    {
        alert("���棺��ѯ��ȫ������Ϣ�����쳣�� ");
        return;
    }
    if (arrResult != null)
    {
        try
        {
            document.getElementsByName("OtherNo")[0].value = arrResult[0][0];
            document.getElementsByName("OtherNoType")[0].value = arrResult[0][1];
            document.getElementsByName("OtherNoTypeName")[0].value = arrResult[0][2];
            document.getElementsByName("EdorAppName")[0].value = arrResult[0][3];
            document.getElementsByName("AppType")[0].value = arrResult[0][4];
            document.getElementsByName("AppTypeName")[0].value = arrResult[0][5];
            document.getElementsByName("ManageCom")[0].value = arrResult[0][6];
            document.getElementsByName("ManageComName")[0].value = arrResult[0][7];
            document.getElementsByName("GetMoney")[0].value = arrResult[0][8];
            document.getElementsByName("GetInterest")[0].value = arrResult[0][9];
        }
        catch (ex) {}
    }
}
/*********************************************************************
 *  �˱���ѯ
 *  ����: �˱�״̬��ѯ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function UWQuery()
{
    var pEdorAcceptNo=fm.EdorAcceptNo.value;
    window.open("./EdorGrpUWQueryMain.jsp?EdorAcceptNo="+pEdorAcceptNo,"window1",sFeatures);
}
/*============================================================================*/

/**
 * ��ѯ���屣����Ϣ
 */
function queryGrpContInfo()
{
    var QuerySQL, arrResult;
    
    var sqlid824092352="DSHomeContSql824092352";
var mySql824092352=new SqlClass();
mySql824092352.setResourceName("bq.GEdorAppManuUWInputSql");//ָ��ʹ�õ�properties�ļ���
mySql824092352.setSqlId(sqlid824092352);//ָ��ʹ�õ�Sql��id
mySql824092352.addSubPara(fm.OtherNo.value);//ָ������Ĳ���
QuerySQL=mySql824092352.getString();
    
//    QuerySQL = "select a.GrpContNo, "
//             +        "a.AppntNo, "
//             +        "a.GrpName, "
//             +        "a.Peoples2, "
//             +        "a.ManageCom, "
//             +        "(select Name from LDCom where ComCode = a.ManageCom), "
//             +        "a.SaleChnl, "
//             +        "(select CodeName "
//             +           "from LDCode "
//             +          "where CodeType = 'salechnl' "
//             +            "and Code = a.SaleChnl), "
//             +        "a.AgentCode, "
//             +        "(select Name from LAAgent where AgentCode = trim(a.AgentCode)), "
//             +        "a.AgentGroup, "
//             +        "(select name from labranchgroup where agentgroup=a.agentgroup), "
//             +        "(select VIPValue from LDGrp where CustomerNo = a.AppntNo), "
//             +        "decode((select VIPValue from LDGrp where CustomerNo = a.AppntNo), "
//             +               "'0', "
//             +               "'��VIP�ͻ�', "
//             +               "'1', "
//             +               "'VIP�ͻ�', "
//             +               "''), "
//             +        "(select BlacklistFlag from LDGrp where CustomerNo = a.AppntNo), "
//             +        "decode((select BlacklistFlag from LDGrp where CustomerNo = a.AppntNo), "
//             +               "'0', "
//             +               "'����', "
//             +               "'1', "
//             +               "'������', "
//             +               "''), "
//             +         "a.prtno"
//             +  " from LCGrpCont a"
//             +  " where 1 = 1 "
//             + getWherePart("a.GrpContNo", "OtherNo");
    //alert(QuerySQL);
    try
    {
        arrResult = easyExecSql(QuerySQL, 1, 0);
    }
    catch (ex)
    {
        alert("���棺��ѯ���屣����Ϣ�����쳣�� ");
        return;
    }
    if (arrResult != null)
    {
        try
        {
            document.getElementsByName("GrpContNo")[0].value = arrResult[0][0];
            document.getElementsByName("AppntNo")[0].value = arrResult[0][1];
            document.getElementsByName("AppntName")[0].value = arrResult[0][2];
            document.getElementsByName("Peoples2")[0].value = arrResult[0][3];
            document.getElementsByName("ManageCom")[1].value = arrResult[0][4];
            document.getElementsByName("ManageComName")[1].value = arrResult[0][5];
            document.getElementsByName("SaleChnl")[0].value = arrResult[0][6];
            document.getElementsByName("SaleChnlName")[0].value = arrResult[0][7];
            document.getElementsByName("AgentCode")[0].value = arrResult[0][8];
            document.getElementsByName("AgentName")[0].value = arrResult[0][9];
            document.getElementsByName("AgentGroup")[0].value = arrResult[0][10];
            document.getElementsByName("AgentGroupName")[0].value = arrResult[0][11];
            document.getElementsByName("VIPValue")[0].value = arrResult[0][12];
            document.getElementsByName("VIPValueName")[0].value = arrResult[0][13];
            document.getElementsByName("BlacklistFlag")[0].value = arrResult[0][14];
            document.getElementsByName("BlacklistFlagName")[0].value = arrResult[0][15];
            document.getElementsByName("PrtNo")[0].value = arrResult[0][16];
        }
        catch (ex) {}
    }
}

/*============================================================================*/

/**
 * ��ѯ�ŵ����ֶ���
 */
function queryGrpPolGrid()
{
    
    var sqlid824092636="DSHomeContSql824092636";
var mySql824092636=new SqlClass();
mySql824092636.setResourceName("bq.GEdorAppManuUWInputSql");//ָ��ʹ�õ�properties�ļ���
mySql824092636.setSqlId(sqlid824092636);//ָ��ʹ�õ�Sql��id
mySql824092636.addSubPara(fm.EdorAcceptNo.value);//ָ������Ĳ���
mySql824092636.addSubPara(fm.OtherNo.value);//ָ������Ĳ���
var QuerySQL=mySql824092636.getString();
    
//    var QuerySQL = "select a.GrpPolNo, "
//                 +        "a.EdorNo, "
//                 +        "a.EdorType, "
//                 +        "a.RiskCode, "
//                 +        "(select distinct RiskName from LMRisk where RiskCode = a.RiskCode), "
//                 +        "(select CodeName "
//                 +           "from LDCode "
//                 +          "where CodeType = 'payintv' "
//                 +            "and Code = a.PayIntv), "
//                 +        "a.Peoples2, "
//                 +        "a.PayToDate, "
//                 +        "a.CValiDate, "
//                 +        "a.UWFlag "
//                 +   "from LPGrpPol a "
//                 +  "where 1 = 1 "
//                 +    "and a.EdorNo in (select EdorNo from LPGrpEdorItem where 1 = 1 " + getWherePart("EdorAcceptNo", "EdorAcceptNo") + ") "
//                 + getWherePart("a.GrpContNo", "OtherNo")
//                 +  "order by a.GrpPolNo asc, a.RiskCode asc";
    //alert(QuerySQL);
    try
    {
    	  turnPageGrpPolGrid.pageDivName = "divGrpPolTP";
        turnPageGrpPolGrid.queryModal(QuerySQL, GrpPolGrid);
    }
    catch (ex)
    {
        alert("���棺��ѯ��ȫ�ŵ�������Ϣ�����쳣�� ");
        return;
    }
}

/*============================================================================*/

/**
 * ��ѯ�ϴκ˱��Ľ���
 */
function queryLastUWState()
{
    var QuerySQL, arrResult;

    //��ȫ����˱�����
    var sqlid824092731="DSHomeContSql824092731";
var mySql824092731=new SqlClass();
mySql824092731.setResourceName("bq.GEdorAppManuUWInputSql");//ָ��ʹ�õ�properties�ļ���
mySql824092731.setSqlId(sqlid824092731);//ָ��ʹ�õ�Sql��id
mySql824092731.addSubPara(fm.EdorAcceptNo.value);//ָ������Ĳ���
QuerySQL=mySql824092731.getString();
    
    
//    QuerySQL = "select a.PassFlag, "
//             +        "(select CodeName "
//             +           "from LDCode "
//             +          "where 1 = 1 "
//             +            "and CodeType = 'edorappuwstate' "
//             +            "and Code = a.PassFlag), "
//             +        "a.UWIdea "
//             +   "from LPAppUWMasterMain a "
//             +  "where 1 = 1 "
//             +  getWherePart("a.EdorAcceptNo", "EdorAcceptNo");
    //alert(QuerySQL);
    try
    {
        arrResult = easyExecSql(QuerySQL, 1, 0);
    }
    catch (ex)
    {
        alert("���棺��ѯ�ϴα���ı�ȫ����˱����۳����쳣�� ");
        return;
    }
    if (arrResult != null)
    {
        try
        {
            document.getElementsByName("AppUWState")[0].value = arrResult[0][0];
            document.getElementsByName("AppUWStateName")[0].value = arrResult[0][1];
            document.getElementsByName("AppUWIdea")[0].value = arrResult[0][2];
        }
        catch (ex) {}
    }
}

/*============================================================================*/

/**
 * �����ŵ��µĸ����˹��˱�
 */
function gotoContUW()
{
    var sMissionID, sSubMissionID, sActivityStatus, sEdorAcceptNo, sGrpContNo;
    try
    {
        sMissionID = document.getElementsByName("MissionID")[0].value;
        sSubMissionID = document.getElementsByName("SubMissionID")[0].value;
        sActivityStatus = document.getElementsByName("ActivityStatus")[0].value;
        sEdorAcceptNo = document.getElementsByName("EdorAcceptNo")[0].value;
        sGrpContNo = document.getElementsByName("GrpContNo")[0].value;
    }
    catch (ex) {}
    if (sEdorAcceptNo == null || trim(sEdorAcceptNo) == "")
    {
        alert("�޷���ȡ��ȫ����š������ŵ��µĸ����˹��˱�ʧ�ܣ� ");
        return;
    }
    var sOpenWinURL = "GEdorManuUWInsuredFrame.jsp?Interface=GEdorManuUWInsuredInput.jsp";
    var sParameters = "MissionID="+ sMissionID + "&SubMissionID=" + sSubMissionID + "&ActivityStatus=" + sActivityStatus + "&EdorAcceptNo=" + sEdorAcceptNo + "&GrpContNo=" + sGrpContNo;
    OpenWindowNew(sOpenWinURL + "&" + sParameters, "�ŵ��ֵ��˹��˱�", "left");
}

/*============================================================================*/

/**
 * ���������֪ͨ��
 */
function noticeHealthInspect()
{
    if (!isEdorPopedom())    return;
    var sGrpContNo;
    try
    {
        sGrpContNo = document.getElementsByName("GrpContNo")[0].value;
    }
    catch (ex) {}
    if (sGrpContNo == null || sGrpContNo == "")
    {
        alert("�޷���ȡ�����ͬ�š����������֪ͨ��ʧ�ܣ� ");
        return;
    }
    var MsgContent = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=C&content=" + MsgContent;
    //showInfo = showModelessDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    document.forms[0].action = "../uw/UWManuSendBodyCheckChk.jsp?GrpContNo=" + sGrpContNo;
    document.forms[0].target = "fraSubmit";
    document.forms[0].submit();
}

/*============================================================================*/

/**
 * ����������֪ͨ��
 */
function noticeLiveInquiry()
{
    if (!isEdorPopedom())    return;
    var sGrpContNo;
    try
    {
        sGrpContNo = document.getElementsByName("GrpContNo")[0].value;
    }
    catch (ex) {}
    if (sGrpContNo == null || sGrpContNo == "")
    {
        alert("�޷���ȡ�����ͬ�š�����������֪ͨ��ʧ�ܣ� ");
        return;
    }
    var MsgContent = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=C&content=" + MsgContent;
    //showInfo = showModelessDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    document.forms[0].action = "../uw/UWManuSendRReportChk.jsp?GrpContNo=" + sGrpContNo;
    document.forms[0].target = "fraSubmit";
    document.forms[0].submit();
}

/*============================================================================*/

/**
 * ���˱�����֪ͨ��
 */
function noticeEdorUWResult()
{
    if (!isEdorPopedom())    return;
    var sMissionID, sSubMissionID, sEdorAcceptNo, sGrpContNo;
    try
    {
        sMissionID = document.getElementsByName("MissionID")[0].value;
        sSubMissionID = document.getElementsByName("SubMissionID")[0].value;
        sEdorAcceptNo = document.getElementsByName("EdorAcceptNo")[0].value;
        sGrpContNo = document.getElementsByName("GrpContNo")[0].value;
    }
    catch (ex) {}
    if (sGrpContNo == null || sGrpContNo == "")
    {
        alert("�޷���ȡ�����ͬ�š����˱�����֪ͨ��ʧ�ܣ� ");
        return;
    }
    var MsgContent = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=C&content=" + MsgContent;
    //showInfo = showModelessDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    document.forms[0].action = "../uw/UWSendNoticeChk.jsp?MissionID=" + sMissionID + "&SubMissionID=" + sSubMissionID + "&GrpContNo=" + sGrpContNo;
    document.forms[0].target = "fraSubmit";
    document.forms[0].submit();
}

/*============================================================================*/

/**
 * ������Ա�Ƿ��������˱�Ȩ��
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
        alert("�޷���ȡ��ǰ��¼�û���Ϣ����鱣ȫ�˱�Ȩ��ʧ�ܣ� ");
        return false;
    }
    else
    {

        var QuerySQL, arrResult;
        
        var sqlid824092901="DSHomeContSql824092901";
var mySql824092901=new SqlClass();
mySql824092901.setResourceName("bq.GEdorAppManuUWInputSql");//ָ��ʹ�õ�properties�ļ���
mySql824092901.setSqlId(sqlid824092901);//ָ��ʹ�õ�Sql��id
mySql824092901.addSubPara(sOperator);//ָ������Ĳ���
QuerySQL=mySql824092901.getString();
        
//        QuerySQL = "select EdorPopedom "
//                 +   "from LDUWUser "
//                 +  "where UserCode = '" + sOperator + "'";
        //alert(QuerySQL);
        try
        {
            arrResult = easyExecSql(QuerySQL, 1, 0);
        }
        catch (ex)
        {
            alert("���棺��ѯ��ǰ��¼�û���ȫ�˱�Ȩ�޳����쳣�� ");
            return false;
        }
        if (arrResult == null || trim(arrResult[0][0]) != "1")
        {
            alert("�Բ�����û�б�ȫ�˱�Ȩ�ޣ� ");
            return false;
        }
    }
    return true;
}

/*============================================================================*/

/**
 * �������屣���˱�����
 */
function saveGrpContUW()
{
    if (!isEdorPopedom())    return;
    var MsgContent = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=C&content=" + MsgContent;
    //showInfo = showModelessDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    try
    {
        document.getElementsByName("ActionFlag")[0].value = "UWMANUSUBMIT";
        document.getElementsByName("UWType")[0].value = "GrpCont";
    }
    catch (ex) {}
    document.forms[0].action = "GEdorAppManuUWSave.jsp";
    document.forms[0].target = "fraSubmit";
    document.forms[0].submit();
}

/*============================================================================*/

/**
 * �������屣���˱�����
 */
function resetGrpContUW()
{
    try
    {
        document.getElementsByName("GrpUWState")[0].value = "";
        document.getElementsByName("GrpUWStateName")[0].value = "";
        document.getElementsByName("GrpUWIdea")[0].value = "";
    }
    catch (ex) {}
}

/*============================================================================*/

/**
 * ���汣ȫ����˱�����
 */
function saveEdorAppUW()
{
		var sqlid824093114="DSHomeContSql824093114";
var mySql824093114=new SqlClass();
mySql824093114.setResourceName("bq.GEdorAppManuUWInputSql");//ָ��ʹ�õ�properties�ļ���
mySql824093114.setSqlId(sqlid824093114);//ָ��ʹ�õ�Sql��id
mySql824093114.addSubPara(fm.OtherNo.value);//ָ������Ĳ���
mySql824093114.addSubPara(fm.EdorAcceptNo.value);//ָ������Ĳ���
var tSQL=mySql824093114.getString();
		
//		var tSQL = "select distinct 1 from LOPRTManager where otherno = '"+fm.OtherNo.value+"' and StandbyFlag3='1' and othernotype='04' and StateFlag = 'A' and StandByFlag1 = '"+fm.EdorAcceptNo.value+"'";
		var arrResult = easyExecSql(tSQL, 1, 0, 1);
		if(arrResult!=null&&arrResult[0][0]=="1")
  	{
  		alert("���κ˱�������δ�ظ��������,��ȴ��ظ������º˱�����!");
  		return;
    }
    if (!isEdorPopedom())    return;
    var tAppUWState = fm.AppUWState.value;
    var tAppUWStateName = fm.AppUWStateName.value;
    if(tAppUWState == null || tAppUWState == "")
    {
    	alert("��¼��˱����ۣ�");
    	return;
    }
    if(!confirm("ȷ���˱�����Ϊ��"+tAppUWState+"-"+tAppUWStateName+"��\nһ��ȷ�Ͻ����ɽ����޸ģ�"))
    {
    	return
    }
    var MsgContent = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=C&content=" + MsgContent;
    //showInfo = showModelessDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    try
    {
        document.getElementsByName("ActionFlag")[0].value = "UWMANUSUBMIT";
        document.getElementsByName("UWType")[0].value = "GEdorApp";
    }
    catch (ex) {}
    document.forms[0].action = "GEdorAppManuUWSave.jsp";
    document.forms[0].target = "fraSubmit";
    document.forms[0].submit();
}

/*============================================================================*/

/**
 * ���ñ�ȫ����˱�����
 */
function resetEdorAppUW()
{
    try
    {
        document.getElementsByName("AppUWState")[0].value = "";
        document.getElementsByName("AppUWStateName")[0].value = "";
        document.getElementsByName("AppUWIdea")[0].value = "";
    }
    catch (ex) {}
}

/*============================================================================*/

/**
 * ���±��鿴
 */
function showNotePad()
{
    var sMissionID, sSubMissionID, sOtherNo, sActivityID;
    try
    {
        sMissionID = document.getElementsByName("MissionID")[0].value;
        sSubMissionID = document.getElementsByName("SubMissionID")[0].value;
        sOtherNo = document.getElementsByName("OtherNo")[0].value;
        sActivityID = document.getElementsByName("ActivityID")[0].value;
    }
    catch (ex) {}
    if (sOtherNo == null || trim(sOtherNo) == "")
    {
        alert("�޷���ȡ��ȫ����š��鿴���±�ʧ�ܣ� ");
        return;
    }
    var sOpenWinURL = "../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp";
    var sParameters = "MissionID="+ sMissionID + "&SubMissionID=" + sSubMissionID + "&ActivityID=" + sActivityID + "&PrtNo="+ sOtherNo + "&NoType=1";
    OpenWindowNew(sOpenWinURL + "&" + sParameters, "���������±��鿴", "left");
}

/*============================================================================*/

/**
 * ����������
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

//==============add================liuxiaosong==========2006-10-25=============start==============
/*
 *���ͺ˱�����֪ͨ��
 */
function noticeEdorUWResult(){document.getElementsByName("MissionID")[0].value
	
	var missionID=document.getElementsByName("MissionID")[0].value; //���missionID��
	var grpcontno=fm.GrpContNo.value;//���屣����
	var EdorNo=fm.EdorNo.value;//������
	var EdorType=GrpPolGrid.getRowColData(0,3);//��������
	
	
	var sqlid824093243="DSHomeContSql824093243";
var mySql824093243=new SqlClass();
mySql824093243.setResourceName("bq.GEdorAppManuUWInputSql");//ָ��ʹ�õ�properties�ļ���
mySql824093243.setSqlId(sqlid824093243);//ָ��ʹ�õ�Sql��id
mySql824093243.addSubPara(grpcontno);//ָ������Ĳ���
var QuerySQL=mySql824093243.getString();
	
//	var QuerySQL="select prtno from lcgrpcont where grpcontno='"+grpcontno+"'";
	var brr = easyExecSql(QuerySQL, 1, 0,"","",1);
	var PrtNo=brr[0][0];//ӡˢ��prtNo
	fm.PrtNo.value=(PrtNo);
 
	var sqlid824093416="DSHomeContSql824093416";
var mySql824093416=new SqlClass();
mySql824093416.setResourceName("bq.GEdorAppManuUWInputSql");//ָ��ʹ�õ�properties�ļ���
mySql824093416.setSqlId(sqlid824093416);//ָ��ʹ�õ�Sql��id
mySql824093416.addSubPara(PrtNo);//ָ������Ĳ���
mySql824093416.addSubPara(missionID);//ָ������Ĳ���
var strsql=mySql824093416.getString();
	
//	var strsql = "select LWMission.SubMissionID from LWMission where "
//	+ " LWMission.MissionProp1 = '" +PrtNo+"'"
//	+ " and LWMission.ActivityID = '0000002101'"
//	+ " and LWMission.ProcessID = '0000000000'"
//	+ " and LWMission.MissionID = '" +missionID +"'";

	turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);

	//�ж��Ƿ��Ѿ����ڸõ��ĺ˱�֪ͨ��
	if (!turnPage.strQueryResult) {

		alert("���������µĺ˱�����֪ͨ��,ԭ����:1.�ѷ��˱�֪ͨ��,��δ��ӡ.");
		fm.SubMissionID.value = "";
		return ;
	}
    
	var arrSelected = new Array();
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
	fm.SubMissionID.value = arrSelected[0][0];
	//alert(fm.SubMissionID.value);
	cProposalNo=fm.GrpContNo.value;
	//alert(cProposalNo);

	var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();    //alert(001);
    
	if (cProposalNo != "")
	{   
		//��������Լ�� ���˱�֪ͨ��ҳ��,ע������Լ��ʹ��ʱ����Ҫ��������ȥ����ȫ���봫
		//alert(EdorNo);
		//alert(EdorType);
		fm.action = "../uw/UWSendNoticeChk.jsp?EdorNo="+EdorNo+"&EdorType="+EdorType;
		
		document.getElementById("fm").submit();
	}
	Else
	{
		showInfo.close();
		alert("����ѡ�񱣵�!");
	}

}
//==============add================liuxiaosong==========2006-10-25=============end==============
//==============add================liuxiaosong==========2006-10-27=============start============
/********************************
 *���ͺ˱�Ҫ��֪ͨ��
 *
 ********************************/
function sendUWRequest(){
	var grpcontno=fm.GrpContNo.value;
	
	var sqlid824093637="DSHomeContSql824093637";
var mySql824093637=new SqlClass();
mySql824093637.setResourceName("bq.GEdorAppManuUWInputSql");//ָ��ʹ�õ�properties�ļ���
mySql824093637.setSqlId(sqlid824093637);//ָ��ʹ�õ�Sql��id
mySql824093637.addSubPara(grpcontno);//ָ������Ĳ���
var QuerySQL=mySql824093637.getString();
	
//  var QuerySQL="select prtno from lcgrpcont where grpcontno='"+grpcontno+"'";
	var brr = easyExecSql(QuerySQL, 1, 0,"","",1);
	var PrtNo=brr[0][0];//ӡˢ��prtNo
	fm.PrtNo.value=(PrtNo);
	
var GrpContNo=fm.GrpContNo.value;
//alert(GrpContNo);
var MissionID=fm.MissionID.value;
//alert(MissionID);
var EdorNo=fm.EdorNo.value;

var EdorType=fm.EdorType.value;


 //��������Լ�еġ��޸�������Ҫ����¼�롰
window.open("../uw/UWGChangeResultMain.jsp?GrpContNo=" +GrpContNo+ "&MissionID="+MissionID+ "&EdorNo="+EdorNo+ "&EdorType="+EdorType+"&PrtNo="+PrtNo,"",sFeatures);
}
//==============add================liuxiaosong==========2006-10-27=============end==============

//==============add================liuxiaosong==========2006-10-30=============start============
/***
 *Ͷ����λ�������
 *����Ҫ���鿴
 *˵�����ù��ܸ�������Լ�˹��˱��ġ�����Ҫ������������Ҫ���鿴��������ҳ�棬����ڱ�ҳ�д���
 *      ҵ������Ϊ����ȫ�����Ա��ڸ��õ�ҳ����ʵ�ֲ�ͬ�߼�
 ***/
function grpRiskElementView()
{
	
	var selno = GrpPolGrid.getSelNo()-1;
	if (selno <0)
	{
		alert("��ѡ�������ֵ�");
		return;
	}
	var tGrpContNo = fm.GrpContNo.value;//�ŵ���
	/*//��lcgrppol�в��
	var QuerySQL="select RiskCode,GrpPolNo from lcgrppol where GrpContNo='"+tGrpContNo+"'";
	var brr=easyExecSql(QuerySQL, 1, 0,"","",1);

	var RiskCode=brr[0][0];//���ֱ���
	var tGrpPolNo=brr[0][1];//�������ֺ���
	*/
	var tRiskCode=GrpPolGrid.getRowColData(selno,4);//���ֱ���
	var tGrpPolNo=GrpPolGrid.getRowColData(selno,1);//�������ֺ���	
	var tBusinessType="bq";//��������Լҳ�棬�˱�־Ϊ��ȫӦ��
							
	window.open("../uw/GrpRiskElenemtMain.jsp?GrpContNo="+tGrpContNo+"&riskcode="+tRiskCode+"&businesstype="+tBusinessType,"window1",sFeatures);
}

//==============add=================liuxiaosong===========2006-10-30============end===============

/**
 * ���屣����ϸ��ѯ
 */
function queryContDetail(){
    var sGrpContNo, sPrtNo;
    try
    {
        sGrpContNo = document.getElementsByName("GrpContNo")[0].value;
        sPrtNo = document.getElementsByName("PrtNo")[0].value;
    }
    catch (ex) {}
    var sOpenWinURL = "../sys/GrpPolDetailQueryMain.jsp";
    var sParameters ="GrpContNo=" + sGrpContNo + "&PrtNo=" + sPrtNo;
    OpenWindowNew(sOpenWinURL + "?" + sParameters, "���屣����ϸ��ѯ", "left");
}

//��ȫ�����켣��ѯ
function missionQuery()
{
	try{
	var EdorAcceptNo = document.all("EdorAcceptNo").value;
	
	var sqlid824093811="DSHomeContSql824093811";
var mySql824093811=new SqlClass();
mySql824093811.setResourceName("bq.GEdorAppManuUWInputSql");//ָ��ʹ�õ�properties�ļ���
mySql824093811.setSqlId(sqlid824093811);//ָ��ʹ�õ�Sql��id
mySql824093811.addSubPara(EdorAcceptNo);//ָ������Ĳ���
mySql824093811.addSubPara(EdorAcceptNo);//ָ������Ĳ���
var strSql=mySql824093811.getString();
	
//	var strSql = "select missionid from lbmission where missionprop1 = '"+EdorAcceptNo+"' union select missionid from lwmission where missionprop1 = '"+EdorAcceptNo+"' ";
	var brr =  easyExecSql(strSql);
  }catch(ex){}
	if(!brr)
	{
		alert("�ñ�ȫ����켣��Ϣ�����ڣ�");
	}
	var pMissionID = brr[0][0];
	window.open("../bq/EdorMissionFrame.jsp?MissionID="+pMissionID,"window3",sFeatures);
}

function sendAskMsg()
{
		var sqlid824094021="DSHomeContSql824094021";
var mySql824094021=new SqlClass();
mySql824094021.setResourceName("bq.GEdorAppManuUWInputSql");//ָ��ʹ�õ�properties�ļ���
mySql824094021.setSqlId(sqlid824094021);//ָ��ʹ�õ�Sql��id
mySql824094021.addSubPara(fm.OtherNo.value);//ָ������Ĳ���
mySql824094021.addSubPara(fm.EdorAcceptNo.value);//ָ������Ĳ���
var tSQL=mySql824094021.getString();
		
//		var tSQL = "select distinct 1 from LOPRTManager where otherno = '"+fm.OtherNo.value+"' and StandbyFlag3='1' and othernotype='04' and StateFlag = 'A' and StandByFlag1 = '"+fm.EdorAcceptNo.value+"'";
		var arrResult = easyExecSql(tSQL, 1, 0, 1);
		if(arrResult!=null&&arrResult[0][0]=="1")
  	{
  		alert("���κ˱������Ѿ����·��������,�����ٴ��·�!");
  		return;
    }
    
    var tMyReply = fm.AskContent.value;
		if(tMyReply==null ||trim(tMyReply) =="")
		{
			alert("��¼�����������!");
			return;
		}
	  var MsgContent = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=C&content=" + MsgContent;
    //showInfo = showModelessDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    document.forms[0].action = "GEdorNoticeSave.jsp?AskOperate=INSERT";
    document.forms[0].target = "fraSubmit";
    document.forms[0].submit();
}

function queryAskMsg()
{
		// ��ʼ�����
	fm.AskInfo.value="";        
  fm.ReplyInfo.value="";
	initAgentGrid();
	
	// ��дSQL���
	var strSQL = "";

	
	var sqlid824094154="DSHomeContSql824094154";
var mySql824094154=new SqlClass();
mySql824094154.setResourceName("bq.GEdorAppManuUWInputSql");//ָ��ʹ�õ�properties�ļ���
mySql824094154.setSqlId(sqlid824094154);//ָ��ʹ�õ�Sql��id
mySql824094154.addSubPara(fm.EdorAcceptNo.value);//ָ������Ĳ���
strSQL=mySql824094154.getString();

	
//	strSQL = "select PrtSeq,OtherNo,StandbyFlag1,StandbyFlag2,StandbyFlag5,decode(StandbyFlag3,'1','�˹��˱������','2','��ȫ���������','����'),MakeDate,StandbyFlag7,StandbyFlag4,StandbyFlag6 from LOPRTManager where 1=1 "
//					+ " and othernotype='04' and StandbyFlag1 = '"+fm.EdorAcceptNo.value+"' and StandbyFlag3 = '1' order by PrtSeq";
					
		turnPage2.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage2.strQueryResult) {
    alert("�޼�¼!");
    divAgentGrid.style.display="none";
    return;
    }
//��ѯ�ɹ������ַ��������ض�ά����
  arrDataSet = decodeEasyQueryResult(turnPage2.strQueryResult);
  //tArr = decodeEasyQueryResult(turnPage.strQueryResult);
  turnPage2.arrDataCacheSet = arrDataSet;
  //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage2.pageDisplayGrid = AgentGrid;    
          
  //����SQL���
  turnPage2.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage2.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  //arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  var tArr = new Array();
  tArr = turnPage2.getData(turnPage2.arrDataCacheSet, turnPage2.pageIndex, MAXSCREENLINES);
  //����MULTILINE������ʾ��ѯ���
  
  //displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  displayMultiline(tArr, turnPage2.pageDisplayGrid);
   divAgentGrid.style.display="";
  
}

//Ͷ����λ�ѳб�������ѯ
function queryProposal(){
  try{
  	var appntno = document.all("AppntNo").value;
	}catch(ex){}
    window.open("../uw/GroupProposalQueryMain.jsp?AppntNo="+appntno,"window1",sFeatures);
}
//Ͷ����λδ�б�������ѯ
function queryNotProposal(){
  try{
  	var appntno = document.all("AppntNo").value;
	}catch(ex){}
	window.open("../uw/GroupNotProposalQueryMain.jsp?AppntNo="+appntno,"window1",sFeatures);
}

//Ͷ����Ӱ���ѯ
function scanQuery()
{
	try{
	   var prtNo=document.all("PrtNo").value;
	  }catch(ex){}
	if(prtNo==""||prtNo==null)
	{
	  	alert("����ѡ��һ������Ͷ����!");
  		return ;
  	}
	window.open("../uw/ImageQueryMain.jsp?ContNo="+prtNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);								
}

//��ȫɨ��Ӱ���ѯ
function scanDetail()
{
	 var EdorAcceptNo    = document.all('EdorAcceptNo').value;
	 var tUrl="../bq/ImageQueryMain.jsp?BussNo="+EdorAcceptNo+"&BussType=BQ";
	 OpenWindowNew(tUrl,"��ȫɨ��Ӱ��","left");
}
//�˱�Ӱ���ѯ
function UWScanQuery()
{
      var ContNo = document.getElementsByName("ContNo")[0].value;
      if (ContNo == "" || ContNo == null){
        return;
      }
      window.open("../uw/EdorUWImageQueryMain.jsp?ContNo=" + ContNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);
}
//��ȫ��ϸ��ѯ
function edorDetailQuery()
{
      //var sEdorType, sEdorState;
      //try
      //  {
      //      sEdorType = document.getElementsByName("EdorType")[0].value;
      //      sEdorState = document.getElementsByName("EdorState")[0].value;
      //  }
      //  catch (ex) {}
      //  if (sEdorType == null || trim(sEdorType) == "")
      //  {
      //      alert("���棺�޷���ȡ��ȫ������Ŀ������Ϣ����ѯ��ȫ��ϸʧ�ܣ� ");
      //      return;
      //  }
      //  detailEdorType();
      var tEdorAcceptNo = fm.EdorAcceptNo.value;
      var tOtherNoType  = fm.OtherNoType.value;	
	    var varSrc = "&EdorAcceptNo=" + tEdorAcceptNo+"&OtherNoType=4";
	    var newWindow = OpenWindowNew("../sys/GBqDetailQueryFrame.jsp?Interface= ../sys/GBqDetailQuery.jsp" + varSrc,"��ȫ��ѯ��ϸ","left");	
      
}
//��ѯ��������±�ȫ��Ŀ��Ϣ
function queryEdorItemInfo()
{
   try{
   	var edorAcceptNo = document.all("EdorAcceptNo").value;
    var strSQL;
    
    var sqlid824094725="DSHomeContSql824094725";
var mySql824094725=new SqlClass();
mySql824094725.setResourceName("bq.GEdorAppManuUWInputSql");//ָ��ʹ�õ�properties�ļ���
mySql824094725.setSqlId(sqlid824094725);//ָ��ʹ�õ�Sql��id
mySql824094725.addSubPara(edorAcceptNo);//ָ������Ĳ���
strSQL=mySql824094725.getString();
    
//    strSQL =  " select EdoracceptNo, "
//            + " (select distinct edorcode||'-'||edorname from lmedoritem m where m.appobj = 'G' and  trim(m.edorcode) = trim(edortype)), "
//            + " GrpContNo, "
//            + " EdorValiDate, nvl(GetMoney,0.00), nvl(GetInterest,0.00), "
//            + " (select c.codename from ldcode c where c.codetype = 'edorstate' and trim(c.code)=trim(EdorState)), "
//            + " EdorState, EdorAppDate, EdorType "
//            + " from LPGrpEdorItem "
//            + " where EdorAcceptNo = '" + edorAcceptNo + "'" ;
    var drr = easyExecSql(strSQL);
  }catch(ex){}
    if ( !drr )
    {
        alert("����������û�б�ȫ��Ŀ��");
        return;
    }
    else
    {
    	try{
        turnPage.queryModal(strSQL, EdorItemGrid);
        document.getElementsByName("EdorNo")[0].value = drr[0][0];
        document.getElementsByName("EdorValiDate")[0].value = drr[0][3];
        document.getElementsByName("EdorState")[0].value = drr[0][7];
        document.getElementsByName("EdorItemAppDate")[0].value = drr[0][8];
        document.getElementsByName("EdorType")[0].value = drr[0][9];
      }catch(ex){}
    }
}

//��ѯ���嵥�˱���¼
function queryGrpAutoUWTrack()
{
	try{
      var cGrpContNo=fm.GrpContNo.value;
  }catch(ex){
    alert("�޷�����ŵ��ţ�");
    return;
  }
  if(cGrpContNo==""||cGrpContNo==null)
  {
  	alert("����ѡ��һ������Ͷ����!");
  	return ;
  	}
  	    
  	    var sqlid824094725="DSHomeContSql824094725";
var mySql824094725=new SqlClass();
mySql824094725.setResourceName("bq.GEdorAppManuUWInputSql");//ָ��ʹ�õ�properties�ļ���
mySql824094725.setSqlId(sqlid824094725);//ָ��ʹ�õ�Sql��id
mySql824094725.addSubPara(fm.EdorAcceptNo.value);//ָ������Ĳ���
var tsql=mySql824094725.getString();
  	    
//  	    var tsql="select edorno from lpgrpedoritem where edoracceptno='"+fm.EdorAcceptNo.value+"'" ;
        var tResult=easyExecSql(tsql, 1, 0);
        var sEdorNo=tResult[0][0];

  window.open("../uw/UWGErrMain.jsp?GrpContNo="+cGrpContNo+"&EdorNo="+sEdorNo+"&BqFlag=1","window1",sFeatures);
}

//Ͷ����λ������ȫ��ѯ
function queryAgoEdor()
{
	try{
      var tAppntNo=fm.AppntNo.value;
  }catch(ex){
    alert("�޷��������ͻ��ţ�");
    return;
  }
  if(tAppntNo==""||tAppntNo==null)
  {
  	alert("����ѡ��һ������Ͷ����!");
  	return ;
  	}
  window.open("./GEdorAgoQueryMain.jsp?CustomerNo="+tAppntNo,"window1",sFeatures);
}

//Ͷ����λ���������ѯ
function queryAgoClaim()
{
	try{
        var tAppntNo=fm.AppntNo.value;
    }catch(ex){
      alert("�޷��������ͻ��ţ�");
      return;
    }
    if(tAppntNo==""||tAppntNo==null)
    {
    	alert("����ѡ��һ������Ͷ����!");
    	return ;
    	}
  window.open("../uw/ClaimGrpQueryInput.jsp?CustomerNo="+tAppntNo,"window1",sFeatures);
}

//�ѷ��ͺ˱�֪ͨ���ѯ
function queryAgoNotice()
{
	try{
        var tGrpContNo=fm.GrpContNo.value;
    }catch(ex){
      alert("�޷�������屣���ţ�");
      return;
    }
    if(tGrpContNo==""||tGrpContNo==null)
    {
    	alert("����ѡ��һ������Ͷ����!");
    	return ;
    }
  window.open("../uw/QueryNotice.jsp?GrpContNo="+tGrpContNo,"window1",sFeatures);
}

/*============================================================================*/
function initGrpPolInfo()
{
    var nSelNo;
    try
    {
        nSelNo = GrpPolGrid.getSelNo() - 1;
    }
    catch (ex) {}
    if (nSelNo != null && nSelNo >= 0)
    {
        var GrpPolNo, UWState;
        try
        {
            GrpPolNo = GrpPolGrid.getRowColData(nSelNo, 1);
            UWState = GrpPolGrid.getRowColData(nSelNo, 10);
        }
        catch (ex) {}
        if (GrpPolNo != null && GrpPolNo != "")
        {
            try
            {
                document.getElementsByName("GrpPolNo")[0].value = GrpPolNo;
                document.getElementsByName("GrpUWState")[0].value = UWState;
                showOneCodeName('gedorgrppoluwstate','GrpUWState','GrpUWStateName');  
            }
            catch (ex) {}
				    //�������ֺ˱����
				    var sqlid824095813="DSHomeContSql824095813";
var mySql824095813=new SqlClass();
mySql824095813.setResourceName("bq.GEdorAppManuUWInputSql");//ָ��ʹ�õ�properties�ļ���
mySql824095813.setSqlId(sqlid824095813);//ָ��ʹ�õ�Sql��id
mySql824095813.addSubPara(fm.EdorNo.value);//ָ������Ĳ���
mySql824095813.addSubPara(fm.EdorType.value);//ָ������Ĳ���
mySql824095813.addSubPara(fm.GrpPolNo.value);//ָ������Ĳ���
QuerySQL=mySql824095813.getString();

				    
//				    QuerySQL = "select a.UWIdea "
//				             +   "from LPGUWMaster a "
//				             +  "where 1 = 1 "
//				             +    "and a.EdorNo = '" + fm.EdorNo.value + "' and a.EdorType = '" + fm.EdorType.value + "' and a.GrpPolNo = '" + fm.GrpPolNo.value + "' "
				    //alert(QuerySQL);
				    try
				    {
				        arrResult = easyExecSql(QuerySQL, 1, 0);
				    }
				    catch (ex)
				    {
				        alert("���棺��ѯ�ϴα�����������ֺ˱���������쳣�� ");
				        return;
				    }
				    if (arrResult != null)
				    {
				        try
				        {
				            document.getElementsByName("GrpUWIdea")[0].value = arrResult[0][0];
				        }
				        catch (ex) {}
				    }
        }
    }
}
/*****************************************************************************
 *  ����EdorAcceptNo����������Ϣ
 *****************************************************************************/
function EndorseDetail()
{
    //var nSelNo;
    //try
    //{
    //    nSelNo = EdorItemGrid.getSelNo() - 1;
    //}
    //catch (ex) {}
    //if (nSelNo == null || nSelNo < 0)
    //{
    //    alert("����ѡ����Ҫ�鿴��������Ŀ�� ");
    //    return;
    //}
    //else
    //{
        document.forms(0).action = "../f1print/AppEndorsementF1PJ1.jsp";
        document.forms(0).target = "_blank";
        document.forms(0).submit();
    //}
}

/**
 * ���� EdorNo ���������嵥��Ϣ
 */
function NamesBill()
{
    //var nSelNo;
    //try
    //{
    //    nSelNo = EdorItemGrid.getSelNo() - 1;
    //}
    //catch (ex) {}
    //if (nSelNo == null || nSelNo < 0)
    //{
    //    alert("����ѡ����Ҫ�鿴��������Ŀ�� ");
    //    return;
    //}
    //else
    //{
        
        var sqlid824095912="DSHomeContSql824095912";
var mySql824095912=new SqlClass();
mySql824095912.setResourceName("bq.GEdorAppManuUWInputSql");//ָ��ʹ�õ�properties�ļ���
mySql824095912.setSqlId(sqlid824095912);//ָ��ʹ�õ�Sql��id
mySql824095912.addSubPara(fm.EdorAcceptNo.value);//ָ������Ĳ���
var tsql=mySql824095912.getString();

//        var tsql="select edorno from lpgrpedoritem where edoracceptno='"+fm.EdorAcceptNo.value+"'" ;
        var tResult=easyExecSql(tsql, 1, 0);
        var sEdorNo=tResult[0][0];

            var QuerySQL, arrResult;
            
            var sqlid824095956="DSHomeContSql824095956";
var mySql824095956=new SqlClass();
mySql824095956.setResourceName("bq.GEdorAppManuUWInputSql");//ָ��ʹ�õ�properties�ļ���
mySql824095956.setSqlId(sqlid824095956);//ָ��ʹ�õ�Sql��id
mySql824095956.addSubPara(sEdorNo);//ָ������Ĳ���
QuerySQL=mySql824095956.getString();
            
//            QuerySQL = "select 'X' from LPEdorPrint2 where EdorNo = '" + sEdorNo + "'";
            //alert(QuerySQL);
            try
            {
                arrResult = easyExecSql(QuerySQL, 1, 0);
            }
            catch (ex)
            {
                alert("���棺��ѯ�����嵥��Ϣ�����쳣�� ");
                return;
            }
            if (arrResult == null)
            {
                alert("�ñ����˴�������Ŀû�������嵥��Ϣ�� ");
                return;
            }
            else
            {
                document.forms(0).action = "../f1print/ReEndorsementF1PJ1.jsp?EdorNo=" + sEdorNo + "&type=Bill";
                document.forms(0).target = "_blank";
                document.forms(0).submit();
            }
        
    //} //nSelNo != null
}

function ShowAskInfo()
{
    var i = AgentGrid.getSelNo();

    if (i != '0')
    {
        i = i - 1;
        
        var tAskInfo = AgentGrid.getRowColData(i,9); 
        fm.AskInfo.value=tAskInfo;
        var tReplyInfo = AgentGrid.getRowColData(i,10);
         fm.ReplyInfo.value=tReplyInfo;
    }	
} 
//<!-- JavaScript Document END -->
