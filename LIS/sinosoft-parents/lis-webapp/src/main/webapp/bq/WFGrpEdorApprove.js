//�������ƣ�WFEdorApprove.js
//�����ܣ���ȫ������-��ȫ����
//�������ڣ�2005-04-30 11:49:22
//������  ��zhangtao
//���¼�¼��  ������    ��������     ����ԭ��/����
//

var turnPage = new turnPageClass();
var turnPageAllGrid = new turnPageClass();
var turnPageSelfGrid = new turnPageClass();


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
			var iHeight=250;     //�������ڵĸ߶�; 
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
            easyQueryClickAll();
            easyQueryClickSelf(true);
        }
        catch (ex) {}
    }
}

/*********************************************************************
 *  ��ѯ�����������
 *  ����:��ѯ�����������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function easyQueryClickAll()
{
    // ��дSQL���
    var strSQL = "";
    
    var sqlid1="DSHomeContSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("bq.WFGrpEdorApproveInputSql");//ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(manageCom+ "%%");//ָ������Ĳ���
		mySql1.addSubPara(fm.OtherNo.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.OtherNoType.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.EdorAppName.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.AppType.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.MakeDate.value);//ָ������Ĳ���
		
		mySql1.addSubPara(fm.EdorAcceptNo.value);//ָ������Ĳ���
		
		strSQL=mySql1.getString();

//    strSQL =  " select a.missionprop1, a.missionprop2, "
//            + " (select codename from ldcode d1 where trim(d1.codetype) = 'gedornotype' and trim(d1.code) = trim(a.missionprop3) ), "
//            + " a.missionprop11, a.missionprop12, "
//            + " (select codename from ldcode d2 where trim(d2.codetype) = 'edorapptype' and trim(d2.code) = trim(a.missionprop5) ), "
//            + " (select codename from ldcode d3 where trim(d3.codetype) = 'station' and trim(d3.code) = trim(a.missionprop7) ), "
//            + " b.operator, a.makedate, a.missionid, a.submissionid, a.activityid "
//            + " from lwmission a,lpedorapp b where 1=1  "
//            + " and a.activityid = '0000008007' "  //��������ȫ-��ȫ����
//            + " and a.processid = '0000000000' "
//            + " and a.missionprop1 = b.edoracceptno "
//            + " and a.defaultoperator is null "
//            + getWherePart('a.missionprop1', 'EdorAcceptNo')
//            + getWherePart('a.missionprop2', 'OtherNo')
//            + getWherePart('a.missionprop3', 'OtherNoType')
//            + getWherePart('a.missionprop4', 'EdorAppName')
//            + getWherePart('a.missionprop5', 'AppType')
//            + getWherePart('a.missionprop7', 'ManageCom')
//            + getWherePart('a.MakeDate', 'MakeDate')
//            + " and a.missionprop7 like '" + manageCom + "%%'"
//            + " order by a.MakeDate, a.MakeTime ";

    turnPageAllGrid.pageDivName = "divTurnPageAllGrid";
    turnPageAllGrid.queryModal(strSQL, AllGrid);

    return true;
}

/*********************************************************************
 *  ��ѯ�ҵ��������
 *  ����: ��ѯ�ҵ��������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function easyQueryClickSelf(isClickedApply)
{
    // ��дSQL���
    var strSQL = "";
    
    var sqlid2="DSHomeContSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("bq.WFGrpEdorApproveInputSql");//ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(operator);//ָ������Ĳ���
		strSQL=mySql2.getString();

//    strSQL =  " select missionprop1, missionprop2, "
//            + " (select codename from ldcode d1 where trim(d1.codetype) = 'gedornotype' and trim(d1.code) = trim(missionprop3) ), "
//            + " missionprop11, missionprop12, "
//            + " (select codename from ldcode d2 where trim(d2.codetype) = 'edorapptype' and trim(d2.code) = trim(missionprop5) ), "
//            + " (select codename from ldcode d3 where trim(d3.codetype) = 'station' and trim(d3.code) = trim(missionprop7) ), "
//            + " createoperator, makedate, missionid, submissionid, activityid "
//            + " from lwmission where 1=1  "
//            + " and activityid = '0000008007' "  //��������ȫ-��ȫ����
//            + " and processid = '0000000000' "
//            + " and defaultoperator ='" + operator + "'"
//            + " order by ModifyDate desc, ModifyTime desc";

            //+ " order by MakeDate, MakeTime ";

    turnPageSelfGrid.pageDivName = "divTurnPageSelfGrid";
    turnPageSelfGrid.queryModal(strSQL, SelfGrid);

    if (SelfGrid.mulLineCount > 0 && isClickedApply)
    {
        SelfGrid.selOneRow(1);
    }

    return true;
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
    var tMissionID = SelfGrid.getRowColData(selno, 10);
    var tSubMissionID = SelfGrid.getRowColData(selno, 11);

    var varSrc="&EdorAcceptNo=" + tEdorAcceptNo + "&MissionID=" + tMissionID + "&SubMissionID=" + tSubMissionID;
    var newWindow = OpenWindowNew("../bq/EdorApproveFrame.jsp?Interface=../bq/GEdorApproveInput.jsp" + varSrc,"��ȫ����","left");

}

/*********************************************************************
 *  ��������
 *  ����: ��������
 *  XinYQ rewrote on 2007-04-03
 *********************************************************************
 */
function applyMission()
{
    var nSelNo = AllGrid.getSelNo() - 1;
    if (nSelNo == null || nSelNo < 0)
    {
        alert("����ѡ����Ҫ��������� ");
        return;
    }
    else
    {
        var sEdorAcceptNo = AllGrid.getRowColData(nSelNo, 1);
        if (sEdorAcceptNo == null || trim(sEdorAcceptNo) == "")
        {
            alert("���Ȳ�ѯ��ѡ��һ������ ");
            return;
        }
        else
        {
            var sMissionID = AllGrid.getRowColData(nSelNo, 10);
            var sSubMissionID = AllGrid.getRowColData(nSelNo, 11);
            var sActivityID = AllGrid.getRowColData(nSelNo, 12);
            if (sMissionID == null || trim(sMissionID) == "" || sSubMissionID == null || trim(sSubMissionID) == "")
            {
                alert("���棺�޷���ȡ����������ڵ���Ϣ�� ");
                return;
            }
            else
            {
            	
            		
            		var tOperter = AllGrid.getRowColData(nSelNo, 8);
							  
								if(operator == tOperter)
								{
									alert("�Բ���,�㲻�������Լ�������!");
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
                document.forms(0).action = "WFGrpEdorApproveSave.jsp?EdorAcceptNo=" + sEdorAcceptNo + "&MissionID=" + sMissionID + "&SubMissionID=" + sSubMissionID + "&ActivityID=" + sActivityID;
                document.forms(0).submit();
            }
        }
    } //nSelNo > 0
}

/*********************************************************************
 *  �򿪹��������±��鿴ҳ��
 *  ����: �򿪹��������±��鿴ҳ��
 *  ����  ��  ��
 *  ����ֵ��  ��
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

    var MissionID = SelfGrid.getRowColData(selno, 10);
    var SubMissionID = SelfGrid.getRowColData(selno, 11);
    var ActivityID = SelfGrid.getRowColData(selno, 12);
    var OtherNo = SelfGrid.getRowColData(selno, 1);
    var OtherNoType = "1";
    if(MissionID == null || MissionID == "")
    {
        alert("MissionIDΪ�գ�");
        return;
    }
    var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
    var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");
}
