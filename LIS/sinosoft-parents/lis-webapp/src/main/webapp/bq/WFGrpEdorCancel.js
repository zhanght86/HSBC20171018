//�������ƣ�WFEdorCancel.js
//�����ܣ���ȫ������-��ȫ����
//�������ڣ�2005-04-30 11:49:22
//������  ��zhangtao
//���¼�¼��  ������    ��������     ����ԭ��/����
//

var turnPage = new turnPageClass();
var turnPageAllGrid = new turnPageClass();
var divTurnPageSelfGrid = new turnPageClass();


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
            //MsgPageURL = MsgPageURL + "S&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=350px");
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
            queryAllGrid();
            querySelfGrid();
        }
        catch (ex) {}
    }
}

/*********************************************************************
 *  ��ѯ�����������
 *  ����:��ѯ�����������
 *********************************************************************
 */
function queryAllGrid()
{
    // ��дSQL���
    var strSQL = "";
    
    var sqlid1="DSHomeContSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("bq.WFGrpEdorCancelInputSql");//ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(manageCom+"%%");//ָ������Ĳ���
		mySql1.addSubPara(fm.OtherNo.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.OtherNoType.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.EdorAppName.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.AppType.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.MakeDate.value);//ָ������Ĳ���
		
		
		mySql1.addSubPara(fm.EdorAcceptNo.value);//ָ������Ĳ���
		
		strSQL=mySql1.getString();
    
//    strSQL =  " select missionprop1, missionprop2, "
//            + " (select codename from ldcode d1 where trim(d1.codetype) = 'gedornotype' and trim(d1.code) = trim(missionprop3) ), "
//            + " missionprop11, "
//            + " (select codename from ldcode d2 where trim(d2.codetype) = 'edorapptype' and trim(d2.code) = trim(missionprop5) ), "
//            //+ " (select codename from ldcode d3 where trim(d3.codetype) = 'station' and trim(d3.code) = trim(missionprop7) ), "
//            + " missionprop7, "
//            + " createoperator, makedate, missionid, submissionid, activityid "
//            + " from lwmission a where 1=1  "
//            + " and activityid = '0000008008' "  //��������ȫ-��ȫ����
//            + " and processid = '0000000000' "
//            + " and defaultoperator is null "
//            + getWherePart('missionprop1', 'EdorAcceptNo')
//            + getWherePart('missionprop2', 'OtherNo')
//            + getWherePart('missionprop3', 'OtherNoType')
//            + getWherePart('missionprop4', 'EdorAppName')
//            + getWherePart('missionprop5', 'AppType')
//            + getWherePart('missionprop7', 'ManageCom')
//            + getWherePart('MakeDate', 'MakeDate')
//            + " and missionprop7 like '" + manageCom + "%%'"
//            //XinYQ added on 2007-05-17 : ������ֹ �˱���ֹ ������ֹ �ı�ȫ����ʾ
//            + " and not exists (select 'X' from LPEdorApp where 1 = 1 and EdorAcceptNo = a.MissionProp1 and EdorState in ('4', '8', '9'))"
//            + " order by ModifyDate desc, ModifyTime desc";
    turnPageAllGrid.pageDivName = "divTurnPageAllGrid";
    turnPageAllGrid.queryModal(strSQL, AllGrid);
}

/*********************************************************************
 *  ��ѯ�ҵ��������
 *  ����: ��ѯ�ҵ��������
 *********************************************************************
 */
function querySelfGrid()
{
    // ��дSQL���
    var strSQL = "";
    
    var sqlid2="DSHomeContSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("bq.WFGrpEdorCancelInputSql");//ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(operator);//ָ������Ĳ���
		strSQL=mySql2.getString();
    
//    strSQL =  " select missionprop1, missionprop2, "
//            + " (select codename from ldcode d1 where trim(d1.codetype) = 'gedornotype' and trim(d1.code) = trim(missionprop3) ), "
//            + " missionprop11, "
//            + " (select codename from ldcode d2 where trim(d2.codetype) = 'edorapptype' and trim(d2.code) = trim(missionprop5) ), "
//            //+ " (select codename from ldcode d3 where trim(d3.codetype) = 'station' and trim(d3.code) = trim(missionprop7) ), "
//            + " missionprop7, "
//            + " createoperator, makedate, missionid, submissionid, activityid "
//            + " from lwmission where 1=1  "
//            + " and activityid = '0000008008' "  //��������ȫ-��ȫ����
//            + " and processid = '0000000000' "
//            + " and defaultoperator ='" + operator + "'"
//            + " order by ModifyDate desc, ModifyTime desc";
    divTurnPageSelfGrid.pageDivName = "divTurnPageSelfGrid";
    divTurnPageSelfGrid.queryModal(strSQL, SelfGrid);
}

/*********************************************************************
 *  ��ת�������ҵ����ҳ��
 *  ����: ��ת�������ҵ����ҳ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function GoToBusiDeal()
{
    var selno = SelfGrid.getSelNo()-1;
    if (selno <0)
    {
          alert("��ѡ��Ҫ���������");
          return;
    }
    var tEdorAcceptNo = SelfGrid.getRowColData(selno, 1);
    var tMissionID = SelfGrid.getRowColData(selno, 9);
    var tSubMissionID = SelfGrid.getRowColData(selno, 10);
    var varSrc="&EdorAcceptNo=" + tEdorAcceptNo + "&MissionID=" + tMissionID + "&SubMissionID=" + tSubMissionID;
    var newWindow = OpenWindowNew("../bq/EdorCancelFrame.jsp?Interface= ../bq/GEdorCancelInput.jsp" + varSrc,"��ȫ���볷��","left");
}

/*********************************************************************
 *  ��������
 *  ����: ��������
 *********************************************************************
 */
function applyMission()
{

    var selno = AllGrid.getSelNo()-1;
    if (selno <0)
    {
          alert("��ѡ��Ҫ���������");
          return;
    }
    fm.MissionID.value = AllGrid.getRowColData(selno, 9);
    fm.SubMissionID.value = AllGrid.getRowColData(selno, 10);
    fm.ActivityID.value = AllGrid.getRowColData(selno, 11);
    if (fm.MissionID.value == null || fm.MissionID.value == "")
    {
          alert("ѡ������Ϊ�գ�");
          return;
    }
    var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    fm.action = "../bq/MissionApply.jsp";
    document.getElementById("fm").submit(); //�ύ
}

/*********************************************************************
 *  �򿪹��������±��鿴ҳ��
 *  ����: �򿪹��������±��鿴ҳ��
 *********************************************************************
 */
function showNotePad()
{
    var selno = SelfGrid.getSelNo()-1;
    if (selno <0)
    {
          alert("��ѡ��Ҫ���������");
          return;
    }
    var MissionID = SelfGrid.getRowColData(selno, 9);
    var SubMissionID = SelfGrid.getRowColData(selno, 10);
    var ActivityID = SelfGrid.getRowColData(selno, 11);
    var OtherNo = SelfGrid.getRowColData(selno, 1);
    var OtherNoType = "1";
    if(MissionID == null || MissionID == "")
    {
        alert("MissionIDΪ�գ�");
        return;
    }
    var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
    var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface= ../uw/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");
}
