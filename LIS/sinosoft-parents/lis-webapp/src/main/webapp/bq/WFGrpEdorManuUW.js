/*******************************************************************************
 * @direction: �ŵ���ȫ�˹��˱������������ű�
 ******************************************************************************/

//<!-- JavaScript Document BGN -->


/*============================================================================*/

var showInfo;                                  //ȫ�ֱ���, ������ʾ����, ������
var turnPage = new turnPageClass();            //ȫ�ֱ���, ��ѯ�����ҳ, ������
var turnPageAllGrid = new turnPageClass();     //ȫ�ֱ���, ���������в�ѯ�����ҳ
var turnPageSelfGrid = new turnPageClass();    //ȫ�ֱ���, �ҵ�������в�ѯ�����ҳ
var sqlresourcename = "bq.WFGrpEdorManuUWInputSql";
/*============================================================================*/

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
            break;
        default:
            //MsgPageURL = MsgPageURL + "C&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
            break;
    }
    //���ļ������⴦��
    if (DealFlag == "succ" || DealFlag == "success")
    {
        try
        {
            queryAllGrid();
            querySelfGrid();
        }
        catch (ex) {}
    }
}

/*============================================================================*/

/**
 * ��ѯ����������
 */
function queryAllGrid()
{
/*
    var QuerySQL = "select a.MissionID, "
                 +        "a.SubMissionID, "
                 +        "a.MissionProp1, "
                 +        "a.MissionProp2, "
                 +        "(select CodeName "
                 +           "from LDCode "
                 +          "where 1 = 1 "
                 +            "and CodeType = 'gedornotype' "
                 +            "and trim(Code) = a.MissionProp3), "
                 +        "a.MissionProp4, "
                 +        "a.MissionProp7, "
                 +        "a.ActivityStatus, "
                 +        "(select CodeName "
                 +           "from LDCode "
                 +          "where 1 = 1 "
                 +            "and CodeType = 'uwactivitystatus' "
                 +            "and trim(Code) = a.ActivityStatus), "
                 +        "a.CreateOperator, "
                 +        "a.MakeDate "
                 +   "from LWMission a "
                 +  "where 1 = 1 "
                 +    "and a.ProcessID = '0000000000' "
                 +    "and a.DefaultOperator is null "    //û�в���Ա
                 //�жϵ�ǰ����Ա�Ƿ��е�������ĺ˱�Ȩ�ޣ����������ѯ
                 +" and exists (select 1 from LDUWUser b, LPEdorApp c where b.usercode = '"+document.all("LoginOperator").value+"' "
					       +" and b.UWtype = '3' and c.edoracceptno=a.missionprop1 and b.UWPopedom =c.uwgrade) "
                 +  getWherePart("a.ActivityID", "ActivityID")
                 +  getWherePart("a.MissionProp1", "EdorAcceptNo1")
                 +  getWherePart("a.MissionProp2", "OtherNo1")
                 +  getWherePart("a.MissionProp3", "OtherNoType1")
                 +  getWherePart("a.MissionProp4", "EdorAppName1")
                 +  getWherePart("a.MissionProp7", "ManageCom1", "like")
                 +  getWherePart("a.MissionProp7", "LoginManageCom", "like")
                 +  getWherePart("a.ActivityStatus", "UWState1")
                 +  getWherePart("a.MakeDate", "MakeDate1")
                 +  "order by a.MissionProp1 desc, a.MakeDate desc";
                 
              */   
        var sqlid1="WFGrpEdorManuUWInputSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(document.all("LoginOperator").value);
		mySql1.addSubPara(fm.ActivityID.value);
		mySql1.addSubPara(fm.EdorAcceptNo1.value);
		mySql1.addSubPara(fm.OtherNo1.value);
		mySql1.addSubPara(fm.OtherNoType1.value);
		mySql1.addSubPara(fm.EdorAppName1.value);
		mySql1.addSubPara(fm.ManageCom1.value);
		mySql1.addSubPara(fm.LoginManageCom.value);
		mySql1.addSubPara(fm.UWState1.value);
		mySql1.addSubPara(fm.MakeDate1.value);
                 
         var QuerySQL =   mySql1.getString();      
                 
                 
    //alert(QuerySQL);
    try
    {
        turnPageAllGrid.pageDivName = "divTurnPageAllGrid";
        turnPageAllGrid.queryModal(QuerySQL, AllGrid);
    }
    catch (ex)
    {
        alert("���棺��ѯ���������б�ȫ��Ϣ�����쳣�� ");
        return;
    }
}

/*============================================================================*/

/**
 * ��ѯ�ҵ��������
 */
function querySelfGrid()
{
/*
    var QuerySQL = "select a.MissionID, "
                 +        "a.SubMissionID, "
                 +        "a.MissionProp1, "
                 +        "a.MissionProp2, "
                 +        "(select CodeName "
                 +           "from LDCode "
                 +          "where 1 = 1 "
                 +            "and CodeType = 'gedornotype' "
                 +            "and trim(Code) = a.MissionProp3), "
                 +        "a.MissionProp4, "
                 +        "a.MissionProp7, "
                 +        "a.ActivityStatus, "
                 +        "(select CodeName "
                 +           "from LDCode "
                 +          "where 1 = 1 "
                 +            "and CodeType = 'uwactivitystatus' "
                 +            "and trim(Code) = a.ActivityStatus), "
                 +        "a.CreateOperator, "
                 +        "a.MakeDate "
                 +   "from LWMission a "
                 +  "where 1 = 1 "
                 +    "and a.ProcessID = '0000000000' "
                 +  getWherePart("a.DefaultOperator", "LoginOperator")    //��ǰ����Ա
                 +  getWherePart("a.ActivityID", "ActivityID")
                 +  getWherePart("a.MissionProp1", "EdorAcceptNo2")
                 +  getWherePart("a.MissionProp2", "OtherNo2")
                 +  getWherePart("a.MissionProp3", "OtherNoType2")
                 +  getWherePart("a.MissionProp4", "EdorAppName2")
                 +  getWherePart("a.MissionProp7", "ManageCom2", "like")
                 +  getWherePart("a.MissionProp7", "LoginManageCom", "like")
                 +  getWherePart("a.ActivityStatus", "UWState2")
                 +  getWherePart("a.MakeDate", "MakeDate2")
                 +  "order by a.MissionProp1 desc, a.MakeDate desc";
   */
   var sqlid2="WFGrpEdorManuUWInputSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(fm.LoginOperator.value);
		mySql2.addSubPara(fm.ActivityID.value);
		mySql2.addSubPara(fm.EdorAcceptNo2.value);
		mySql2.addSubPara(fm.OtherNo2.value);
		mySql2.addSubPara(fm.OtherNoType2.value);
		mySql2.addSubPara(fm.EdorAppName2.value);
		mySql2.addSubPara(fm.ManageCom2.value);
		mySql2.addSubPara(fm.LoginManageCom.value);
		mySql2.addSubPara(fm.UWState2.value);
		mySql2.addSubPara(fm.MakeDate2.value);
                 
         var QuerySQL =   mySql2.getString();  
   
    //alert(QuerySQL);
    try
    {
        turnPageSelfGrid.pageDivName = "divTurnPageSelfGrid";
        turnPageSelfGrid.queryModal(QuerySQL, SelfGrid);
    }
    catch (ex)
    {
        alert("���棺��ѯ�ҵ�������б�ȫ��Ϣ�����쳣�� ");
        return;
    }
}

/*============================================================================*/

/**
 * ������Ա�Ƿ��������˱�Ȩ��
 */
function hasEdorPopedom()
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
        /*
        QuerySQL = "select EdorPopedom "
                 +   "from LDUWUser "
                 +  "where UserCode = '" + sOperator + "' and uwtype = '3'";
       */
       var sqlid3="WFGrpEdorManuUWInputSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName(sqlresourcename);
		mySql3.setSqlId(sqlid3);
		mySql3.addSubPara(sOperator);

       QuerySQL =mySql3.getString();
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
 * ���빲�������ҵ�����
 */
function applyTheMission()
{
    if (!hasEdorPopedom()) return;
    //������б�ȫ�˱�Ȩ��,��������
    var nSelNo;
    try
    {
        nSelNo = AllGrid.getSelNo() - 1;
    }
    catch (ex) {}
    if (nSelNo == null || nSelNo < 0)
    {
        alert("����ѡ����Ҫ��������� ");
        return;
    }
    else
    {
        var sMissionID, sSubMissionID, sEdorAcceptNo, sActivityID;
        try
        {
            sMissionID = AllGrid.getRowColData(nSelNo, 1);
            sSubMissionID = AllGrid.getRowColData(nSelNo, 2);
            sEdorAcceptNo = AllGrid.getRowColData(nSelNo, 3);
            sActivityID = document.getElementsByName("ActivityID")[0].value;
        }
        catch (ex) {}
        if (sEdorAcceptNo == null || trim(sEdorAcceptNo) == "")
        {
            alert("���Ȳ�ѯ��ѡ��һ������ ");
            return;
        }
        var MsgContent = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
        var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=C&content=" + MsgContent;
        //showInfo = showModelessDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=300;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
        //����ͨ������ Mission ҳ��
        document.forms[0].action = "MissionApply.jsp?MissionID=" + sMissionID + "&SubMissionID=" + sSubMissionID + "&ActivityID=" + sActivityID;
        document.forms[0].submit();
    } //nSelNo > 0
}

/*============================================================================*/

/**
 * �����˹��˱�
 */
function gotoManuUWDeal()
{
    var nSelNo;
    try
    {
        nSelNo = SelfGrid.getSelNo() - 1;
    }
    catch (ex) {}
    if (nSelNo == null || nSelNo < 0)
    {
        alert("����ѡ����Ҫ��������� ");
        return;
    }
    else
    {
        var sMissionID, sSubMissionID, sEdorAcceptNo, sActivityStatus;
        try
        {
            sMissionID = SelfGrid.getRowColData(nSelNo, 1);
            sSubMissionID = SelfGrid.getRowColData(nSelNo, 2);
            sEdorAcceptNo = SelfGrid.getRowColData(nSelNo, 3);
            sActivityStatus = SelfGrid.getRowColData(nSelNo, 8);
        }
        catch (ex) {}
        if (sEdorAcceptNo == null || trim(sEdorAcceptNo) == "")
        {
            alert("���Ȳ�ѯ��ѡ��һ������ ");
            return;
        }
        var sOpenWinURL = "GEdorAppManuUWFrame.jsp?Interface=GEdorAppManuUWInput.jsp";
        var sParameters = "EdorAcceptNo=" + sEdorAcceptNo + "&MissionID=" + sMissionID + "&SubMissionID=" + sSubMissionID + "&ActivityStatus=" + sActivityStatus;
        OpenWindowNew(sOpenWinURL + "&" + sParameters, "�ŵ���ȫ�˹��˱�����", "left");
    }
}

/*============================================================================*/

/**
 * ��ȫ��ϸ��ѯ
 */
function showEdorDetail()
{
    var nSelNo;
    try
    {
        nSelNo = SelfGrid.getSelNo() - 1;
    }
    catch (ex) {}
    if (nSelNo == null || nSelNo < 0)
    {
        alert("����ѡ����Ҫ��ѯ������ ");
        return;
    }
    else
    {
        var sEdorAcceptNo;
        try
        {
            sEdorAcceptNo = SelfGrid.getRowColData(nSelNo, 3);
        }
        catch (ex) {}
        if (sEdorAcceptNo == null || trim(sEdorAcceptNo) == "")
        {
            alert("���Ȳ�ѯ��ѡ��һ������ ");
            return;
        }
        //var sOpenWinURL = "../sys/BqDetailQueryFrame.jsp?Interface=../sys/BqDetailQuery.jsp";
        //var sParameters = "EdorAcceptNo=" + sEdorAcceptNo;
        //OpenWindowNew(sOpenWinURL + "&" + sParameters, "��ȫ��ϸ��ѯ", "left");
    }
}

/*============================================================================*/

/**
 * ���±��鿴
 */
function showNotePad()
{
    var nSelNo;
    try
    {
        nSelNo = SelfGrid.getSelNo() - 1;
    }
    catch (ex) {}
    if (nSelNo == null || nSelNo < 0)
    {
        alert("����ѡ����Ҫ�鿴������ ");
        return;
    }
    else
    {
        var sMissionID, sSubMissionID, sOtherNo, sActivityID;
        try
        {
            sMissionID = SelfGrid.getRowColData(nSelNo, 1);
            sSubMissionID = SelfGrid.getRowColData(nSelNo, 2);
            sOtherNo = SelfGrid.getRowColData(nSelNo, 4);
            sActivityID = document.getElementsByName("ActivityID")[0].value;
        }
        catch (ex) {}
        if (sOtherNo == null || trim(sOtherNo) == "")
        {
            alert("���Ȳ�ѯ��ѡ��һ������ ");
            return;
        }
        var sOpenWinURL = "../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp";
        var sParameters = "MissionID="+ sMissionID + "&SubMissionID=" + sSubMissionID + "&ActivityID=" + sActivityID + "&PrtNo="+ sOtherNo + "&NoType=1";
        OpenWindowNew(sOpenWinURL + "&" + sParameters, "���������±��鿴", "left");
    }
}

/*============================================================================*/


//<!-- JavaScript Document END -->
