//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug = "0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();
//ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var turnPage2 = new turnPageClass();

//modify by lzf
function ApplyUW()
{

    var selno = PublicWorkPoolGrid.getSelNo() - 1;
    if (selno < 0)
    {
        alert("��ѡ��Ҫ�����Ͷ������");
        return;
    }
    fm.MissionID.value = PublicWorkPoolGrid.getRowColData(selno, 10);
    fm.SubMissionID.value = PublicWorkPoolGrid.getRowColData(selno, 11);
    fm.ActivityID.value = PublicWorkPoolGrid.getRowColData(selno, 13);

    var i = 0;
    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

   showInfo.focus();

    fm.action = "../uw/ManuUWAllChk.jsp";
    document.getElementById("fm").submit();
    //�ύ
    showApproveDetail();
}

function showApproveDetail() {

    var selno = PublicWorkPoolGrid.getSelNo() - 1;
    if (selno < 0)
    {
        alert("��ѡ��Ҫ���˵�Ͷ������");
        return;
    }
    var tMissionID = PublicWorkPoolGrid.getRowColData(selno, 10);
    var tSubMissionID = PublicWorkPoolGrid.getRowColData(selno, 11);
    var tActivityID = PublicWorkPoolGrid.getRowColData(selno, 13);
    var tPreSeq = PublicWorkPoolGrid.getRowColData(selno, 4);
    var polNo = PublicWorkPoolGrid.getRowColData(selno, 1);
    
    var sqlid901100508="DSHomeContSql901100508";
	var mySql901100508=new SqlClass();
	mySql901100508.setResourceName("uw.UWUpReportDealInputSql");//ָ��ʹ�õ�properties�ļ���
	mySql901100508.setSqlId(sqlid901100508);//ָ��ʹ�õ�Sql��id
	mySql901100508.addSubPara(polNo);//ָ������Ĳ���
	var strSql=mySql901100508.getString();
    
    var arrResult = easyExecSql(strSql);
    var urlStr = "../common/jsp/UnLockTable.jsp?PrtNo=" + polNo + "&CreatePos=�б�����&PolState=1003&Action=INSERT";
    showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;resizable:1");

    //�����ǰʵ�ֹ���ҳ�湦�ܣ�Դ��ProposalMain.jsp
    mSwitch.deleteVar("PolNo");
    mSwitch.addVar("PolNo", "", polNo);
    mSwitch.updateVar("PolNo", "", polNo);

    mSwitch.deleteVar("ApprovePolNo");
    mSwitch.addVar("ApprovePolNo", "", polNo);
    mSwitch.updateVar("ApprovePolNo", "", polNo);
    easyScanWin = window.open("./UWUpReportDealEachMain.jsp?ContNo=" + polNo + "&MissionID=" + tMissionID + "&SubMissionID=" + tSubMissionID + "&ActivityID="+ tActivityID +"&PrtSeq=" + tPreSeq, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");

}


function afterSubmit(FlagStr, content)
{
    showInfo.close();

    //���ӡˢ�ŵ�����
  //  var prtNo = PolGrid.getRowColData(PolGrid.getSelNo() - 1, 2);
    //var urlStr = "../common/jsp/UnLockTable.jsp?PrtNo=" + prtNo + "&CreatePos=�б�����&PolState=1003&Action=DELETE";
   // showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;resizable:1");

    if (FlagStr == "Fail")
    {
        var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    }

    // ˢ�²�ѯ���
    jQuery("#privateSearch").click();
    jQuery("#publicSearch").click();
}

function InitshowApproveDetail() {

    var selno = PrivateWorkPoolGrid.getSelNo() - 1;
    if (selno < 0)
    {
        alert("��ѡ��Ҫ���˵�Ͷ������");
        return;
    }
   
    var tMissionID = PrivateWorkPoolGrid.getRowColData(selno, 10);
    var tSubMissionID = PrivateWorkPoolGrid.getRowColData(selno, 11);
    var tActivityID = PrivateWorkPoolGrid.getRowColData(selno, 13);
    var tPrtSeq = PrivateWorkPoolGrid.getRowColData(selno, 4);
    var polNo = PrivateWorkPoolGrid.getRowColData(selno, 1);
   // alert("tActivityID=="+tActivityID);
		
	var sqlid901100628="DSHomeContSql901100628";
	var mySql901100628=new SqlClass();
	mySql901100628.setResourceName("uw.UWUpReportDealInputSql");//ָ��ʹ�õ�properties�ļ���
	mySql901100628.setSqlId(sqlid901100628);//ָ��ʹ�õ�Sql��id
	mySql901100628.addSubPara(polNo);//ָ������Ĳ���
	var strSql=mySql901100628.getString();		
    var arrResult = easyExecSql(strSql);

    //�����ǰʵ�ֹ���ҳ�湦�ܣ�Դ��ProposalMain.jsp
    mSwitch.deleteVar("PolNo");
    mSwitch.addVar("PolNo", "", polNo);
    mSwitch.updateVar("PolNo", "", polNo);
     mSwitch.deleteVar("ApprovePolNo");
    mSwitch.addVar("ApprovePolNo", "", polNo);
    mSwitch.updateVar("ApprovePolNo", "", polNo);
   // alert(mSwitch.getVar("ApprovePolNo"));

    easyScanWin = window.open("./UWUpReportDealEachMain.jsp?ContNo=" + polNo + "&MissionID=" + tMissionID + "&SubMissionID=" + tSubMissionID + "&ActivityID="+ tActivityID + "&PrtSeq=" + tPrtSeq);
    //easyScanWin = window.open("./RReportDealEachMain.jsp?ContNo="+polNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID);   

}

function queryAgent(gridName,row,col){
	if (gridName.indexOf("Public") != -1) {
		flag = "public";
	} else if (gridName.indexOf("Private") != -1) {
		flag = "private";
	}
	if (document.all('AgentCode').value == "") {
		var newWindow = window
				.open(
						"../sys/AgentCommonQueryMain.jsp?queryflag=" + flag
								+ "&ManageCom=" + document.all('ManageCom').value
								+ "&row="+row+"&col="+col  );
	}
}

	function afterQuery2(arrResult, queryFlag,row,col) {
		if (arrResult != null) {
			if (queryFlag == "public") {
				PublicWorkPoolQueryGrid.setRowColData(row, col, arrResult[0][0]);
			} else if (queryFlag == "private") {
				PrivateWorkPoolQueryGrid.setRowColData(row, col, arrResult[0][0]);
			}
		}
	}

//end by lzf
/*********************************************************************
 *  ����Ͷ���������ύ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function approvePol1()
{
    var tSel = PolGrid.getSelNo();
    if (tSel == null || tSel == 0)
        alert("��ѡ��һ��Ͷ�������ٽ��и��˲���");
    else
    {
        var i = 0;
        var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
        var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
        //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	   showInfo.focus();

        showSubmitFrame(mDebug);
        document.getElementById("fm").submit();
        //�ύ
    }
}

/*********************************************************************
 *  Ͷ�������˵��ύ��Ĳ���,���������ݷ��غ�ִ�еĲ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
/*function afterSubmit(FlagStr, content)
{
    showInfo.close();

    //���ӡˢ�ŵ�����
  //  var prtNo = PolGrid.getRowColData(PolGrid.getSelNo() - 1, 2);
    //var urlStr = "../common/jsp/UnLockTable.jsp?PrtNo=" + prtNo + "&CreatePos=�б�����&PolState=1003&Action=DELETE";
   // showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;resizable:1");

    if (FlagStr == "Fail")
    {
        var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    }

    // ˢ�²�ѯ���
    easyQueryClick();
    easyQueryClickSelf();
}

/*********************************************************************
 *  ��ʾdiv
 *  ����  ��  ��һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
 *  ����ֵ��  ��
 *********************************************************************
 */
function showDiv(cDiv, cShow)
{
    if (cShow == "true")
        cDiv.style.display = "";
    else
        cDiv.style.display = "none";
}

/*********************************************************************
 *  ��ʾfrmSubmit��ܣ���������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showSubmitFrame(cDebug)
{
    if (cDebug == "1")
        parent.fraMain.rows = "0,0,50,82,*";
    else
        parent.fraMain.rows = "0,0,0,72,*";
}

/*********************************************************************
 *  ��ʾͶ������ϸ��Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showPolDetail()
{
    var i = 0;
    var checkFlag = 0;

    for (i = 0; i < PolGrid.mulLineCount; i++) {
        if (PolGrid.getSelNo(i)) {
            checkFlag = PolGrid.getSelNo();
            break;
        }
    }

    if (checkFlag) {
        var cPolNo = PolGrid.getRowColData(checkFlag - 1, 1);

        mSwitch.deleteVar("PolNo");
        mSwitch.addVar("PolNo", "", cPolNo);

        window.open("./ProposalMain.jsp?LoadFlag=6", "window1","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
    }
    else {
        alert("����ѡ��һ��������Ϣ��");
    }
}

/*********************************************************************
*  ����EasyQuery��ѯ����
*  ����  ��  ��
*  ����ֵ��  ��
*********************************************************************
*/

function easyQueryClick()
{
    // ��ʼ�����
    initPolGrid();

    // ��дSQL���
    var strSql = "";

    //strSql = "select lwmission.missionprop1,'',lwmission.missionprop2,lwmission.makedate,lwmission.Missionid,lwmission.submissionid,lwmission.activityid,lwmission.missionprop8,lwmission.missionprop6 from lwmission where 1=1"
//    strSql = "select (select prtno from lccont where contno=LCReinsureReport.contno),"
//            + " '',"
//            + " lwmission.missionprop2,"
//            + " lwmission.makedate,"
//            + " lwmission.Missionid,"
//            + " lwmission.submissionid,"
//            + " lwmission.activityid,"
//            + " LCReinsureReport.usercode,"
//            + " lwmission.missionprop6,"            
//            + " (select insuredname from lccont where contno=LCReinsureReport.contno),"
//           // + " (select riskcode from lcpol where polno=mainpolno and prtno=LCReinsureReport.contno),"
//            + "'',"
//            + " (select decode(min(c.makedate),null,'��ɨ���',to_char(min(c.makedate),'yyyy-mm-dd')) from es_doc_main c where c.doccode=(select prtno from lccont where contno=LCReinsureReport.contno)) ScanDate,"//ɨ������
//            + " lwmission.missionprop1"
//            + " from lwmission, LCReinsureReport"
//            + " where 1 = 1"
//            + " and lwmission.missionprop1 = trim(LCReinsureReport.contno)"
//            + " and lwmission.activityid = '0000001140'"
//            + " and lwmission.processid = '0000000003'"
//           // + getWherePart('lwmission.MissionProp1', 'ProposalNo')                       
//            + getWherePart('lwmission.MissionProp2', 'AgentCode')
//    //+ getWherePart('lwmission.MissionProp3','AgentCode')
//            + getWherePart('lwmission.MissionProp6', 'ManageCom')
//            + getWherePart('lwmission.MakeDate', 'MakeDate', '=')
//            + " and LWMission.MissionProp6 like '" + ComCode + "%%'"  //����Ȩ�޹�������
//            + " and lwmission.defaultoperator is null ";
//            if(fm.PrtNo.value!=null && fm.PrtNo.value!='')
//              strSql = strSql+ " and exists(select 1 from lccont where prtno='"+fm.PrtNo.value+"' and contno=LCReinsureReport.contno)";
//            strSql = strSql+ " order by lwmission.modifydate asc,lwmission.modifytime asc";

    //SQL����
    if(fm.PrtNo.value!=null && fm.PrtNo.value!=''){
    	var sqlid901095819="DSHomeContSql901095819";
		var mySql901095819=new SqlClass();
		mySql901095819.setResourceName("uw.UWUpReportDealInputSql");//ָ��ʹ�õ�properties�ļ���
		mySql901095819.setSqlId(sqlid901095819);//ָ��ʹ�õ�Sql��id
		mySql901095819.addSubPara(fm.AgentCode.value );//ָ������Ĳ���
		mySql901095819.addSubPara(fm.ManageCom.value );//ָ������Ĳ���
		mySql901095819.addSubPara(fm.MakeDate.value);//ָ������Ĳ���
		mySql901095819.addSubPara(ComCode+"%%");//ָ������Ĳ���
		mySql901095819.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
		strSql=mySql901095819.getString();
    }else{
    	var sqlid901100219="DSHomeContSql901100219";
		var mySql901100219=new SqlClass();
		mySql901100219.setResourceName("uw.UWUpReportDealInputSql");//ָ��ʹ�õ�properties�ļ���
		mySql901100219.setSqlId(sqlid901100219);//ָ��ʹ�õ�Sql��id
		mySql901100219.addSubPara(fm.AgentCode.value );//ָ������Ĳ���
		mySql901100219.addSubPara(fm.ManageCom.value );//ָ������Ĳ���
		mySql901100219.addSubPara(fm.MakeDate.value);//ָ������Ĳ���
		mySql901100219.addSubPara(ComCode+"%%");//ָ������Ĳ���
		strSql=mySql901100219.getString();
    }
    
    turnPage.queryModal(strSql, PolGrid);
}

function easyQueryClickSelf()
{

    initSelfPolGrid();
    var strSql = "";
    //strSql = "select lwmission.missionprop1,'',lwmission.missionprop2,lwmission.makedate,lwmission.Missionid,lwmission.submissionid,lwmission.activityid,lwmission.missionprop7,lwmission.missionprop6 from lwmission where 1=1"
    
    var sqlid901100348="DSHomeContSql901100348";
	var mySql901100348=new SqlClass();
	mySql901100348.setResourceName("uw.UWUpReportDealInputSql");//ָ��ʹ�õ�properties�ļ���
	mySql901100348.setSqlId(sqlid901100348);//ָ��ʹ�õ�Sql��id
	mySql901100348.addSubPara(Operator);//ָ������Ĳ���
	strSql=mySql901100348.getString();
    
//    strSql = "select (select prtno from lccont where contno=LCReinsureReport.contno),"
//            + " '',"
//            + " lwmission.missionprop2,"
//            + " lwmission.makedate,"
//            + " lwmission.Missionid,"
//            + " lwmission.submissionid,"
//            + " lwmission.activityid,"
//            + " LCReinsureReport.usercode,"
//            + " lwmission.missionprop6,"
//          //  + " '',''"
//            + " (select insuredname from lccont where contno=LCReinsureReport.contno),"
//          //  + " (select riskcode from lcpol where polno=mainpolno and prtno=LCReinsureReport.contno),"
//            + "'',"
//            + " (select decode(min(c.makedate),null,'��ɨ���',to_char(min(c.makedate),'yyyy-mm-dd')) from es_doc_main c where c.doccode=(select prtno from lccont where contno=LCReinsureReport.contno)) ScanDate,"//ɨ������
//            + " lwmission.missionprop1"
//            + " from lwmission, LCReinsureReport"
//            + " where 1 = 1"
//            + " and lwmission.missionprop1 = trim(LCReinsureReport.contno)"
//            + " and lwmission.activityid = '0000001140'"
//            + " and lwmission.processid = '0000000003'"
//            + " and lwmission.defaultoperator ='" + Operator + "'"
//            + " order by lwmission.modifydate asc,lwmission.modifytime asc";

   // alert(strSql);
    turnPage2.queryModal(strSql, SelfPolGrid);
}
/*********************************************************************
 *  ��ʾEasyQuery�Ĳ�ѯ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayEasyResult(arrResult)
{
    var i, j, m, n;

    if (arrResult == null)
        alert("û���ҵ���ص�����!");
    else
    {
        // ��ʼ�����
        initPolGrid();
        //HZM �����޸�
        PolGrid.recordNo = (currBlockIndex - 1) * MAXMEMORYPAGES * MAXSCREENLINES + (currPageIndex - 1) * MAXSCREENLINES;
        PolGrid.loadMulLine(PolGrid.arraySave);
        //HZM �����޸�

        arrGrid = arrResult;
        // ��ʾ��ѯ���
        n = arrResult.length;
        for (i = 0; i < n; i++)
        {
            m = arrResult[i].length;
            for (j = 0; j < m; j++)
            {
                PolGrid.setRowColData(i, j + 1, arrResult[i][j]);
            }
            // end of for
        }
        // end of for
        //alert("result:"+arrResult);

        PolGrid.delBlankLine();
    }
    // end of if
}

/*function ApplyUW()
{

    var selno = PolGrid.getSelNo() - 1;
    if (selno < 0)
    {
        alert("��ѡ��Ҫ�����Ͷ������");
        return;
    }

    fm.MissionID.value = PolGrid.getRowColData(selno, 5);
    fm.SubMissionID.value = PolGrid.getRowColData(selno, 6);
    fm.ActivityID.value = PolGrid.getRowColData(selno, 7);

    var i = 0;
    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

    fm.action = "../uw/ManuUWAllChk.jsp";
    document.getElementById("fm").submit();
    //�ύ
    showApproveDetail();
}

function showApproveDetail() {

    var selno = PolGrid.getSelNo() - 1;
    if (selno < 0)
    {
        alert("��ѡ��Ҫ���˵�Ͷ������");
        return;
    }
    var tMissionID = PolGrid.getRowColData(selno, 6);
    var tSubMissionID = PolGrid.getRowColData(selno, 7);
    var tPreSeq = PolGrid.getRowColData(selno, 8);
    var tCustomerNo = PolGrid.getRowColData(selno, 2);
    var polNo = PolGrid.getRowColData(selno, 1);

    
    var sqlid901100508="DSHomeContSql901100508";
var mySql901100508=new SqlClass();
mySql901100508.setResourceName("uw.UWUpReportDealInputSql");//ָ��ʹ�õ�properties�ļ���
mySql901100508.setSqlId(sqlid901100508);//ָ��ʹ�õ�Sql��id
mySql901100508.addSubPara(polNo);//ָ������Ĳ���
var strSql=mySql901100508.getString();
    
//    var strSql = "select * from ldsystrace where PolNo='" + prtNo + "' and (CreatePos='�б�¼��' or CreatePos='�б�����') and (PolState='1002' or PolState='1003')";
    var arrResult = easyExecSql(strSql);
    //if (arrResult!=null && arrResult[0][1]!=Operator) {
    //  alert("��ӡˢ�ŵ�Ͷ�����Ѿ�������Ա��" + arrResult[0][1] + "���ڣ�" + arrResult[0][5] + "��λ�������������ܲ�������ѡ������ӡˢ�ţ�");
    //  return;
    //}
    //������ӡˢ��
    var urlStr = "../common/jsp/UnLockTable.jsp?PrtNo=" + polNo + "&CreatePos=�б�����&PolState=1003&Action=INSERT";
    showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;resizable:1");


    //�����ǰʵ�ֹ���ҳ�湦�ܣ�Դ��ProposalMain.jsp
    mSwitch.deleteVar("PolNo");
    mSwitch.addVar("PolNo", "", polNo);
    mSwitch.updateVar("PolNo", "", polNo);


    mSwitch.deleteVar("ApprovePolNo");
    mSwitch.addVar("ApprovePolNo", "", polNo);
    mSwitch.updateVar("ApprovePolNo", "", polNo);


    easyScanWin = window.open("./UWUpReportDealEachMain.jsp?ContNo=" + polNo + "&MissionID=" + tMissionID + "&SubMissionID=" + tSubMissionID + "&CustomerNo=" + tCustomerNo + "&PrtSeq" + tPreSeq, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");

}

/*function InitshowApproveDetail() {

    var selno = SelfPolGrid.getSelNo() - 1;
    if (selno < 0)
    {
        alert("��ѡ��Ҫ���˵�Ͷ������");
        return;
    }
    var tMissionID = SelfPolGrid.getRowColData(selno, 5);
    var tSubMissionID = SelfPolGrid.getRowColData(selno, 6);
    var tCustomerNo = SelfPolGrid.getRowColData(selno, 2);
    var tPrtSeq = SelfPolGrid.getRowColData(selno, 8);

    var polNo = SelfPolGrid.getRowColData(selno, 1);
		
		var sqlid901100628="DSHomeContSql901100628";
var mySql901100628=new SqlClass();
mySql901100628.setResourceName("uw.UWUpReportDealInputSql");//ָ��ʹ�õ�properties�ļ���
mySql901100628.setSqlId(sqlid901100628);//ָ��ʹ�õ�Sql��id
mySql901100628.addSubPara(polNo);//ָ������Ĳ���
var strSql=mySql901100628.getString();
		
		
//    var strSql = "select * from ldsystrace where PolNo='" + polNo + "' and (CreatePos='�б�¼��' or CreatePos='�б�����') and (PolState='1002' or PolState='1003')";
    var arrResult = easyExecSql(strSql);
    // if (arrResult!=null && arrResult[0][1]!=Operator) {
    //   alert("��Ͷ�����Ѿ�������Ա��" + arrResult[0][1] + "���ڣ�" + arrResult[0][5] + "��λ�������������ܲ�������ѡ������ӡˢ�ţ�");
    //   return;
    // }
    //������ӡˢ��
   // var urlStr = "../common/jsp/UnLockTable.jsp?PolNo=" + polNo + "&CreatePos=�б�����&PolState=1003&Action=INSERT";
  //  showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;resizable:1");


    //�����ǰʵ�ֹ���ҳ�湦�ܣ�Դ��ProposalMain.jsp
    mSwitch.deleteVar("PolNo");
    mSwitch.addVar("PolNo", "", polNo);
    mSwitch.updateVar("PolNo", "", polNo);
    //alert(polNo);

    mSwitch.deleteVar("ApprovePolNo");
    mSwitch.addVar("ApprovePolNo", "", polNo);
    mSwitch.updateVar("ApprovePolNo", "", polNo);
    //alert(mSwitch.getVar("ApprovePolNo"));

    easyScanWin = window.open("./UWUpReportDealEachMain.jsp?ContNo=" + polNo + "&MissionID=" + tMissionID + "&SubMissionID=" + tSubMissionID + "&CustomerNo=" + tCustomerNo + "&PrtSeq=" + tPrtSeq);
    //easyScanWin = window.open("./RReportDealEachMain.jsp?ContNo="+polNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID);   

}

/*********************************************************************
 *  ����Ͷ���������ύ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function passApprovePol() {
    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

   showInfo.focus();

    //�ύ
    var polNo = mSwitch.getVar("ApprovePolNo");
    //alert(polNo);
    window.top.fraSubmit.window.location = "./ProposalApproveSave.jsp?polNo=" + polNo + "&approveFlag=9";
}

function refuseApprovePol() {
    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

   showInfo.focus();

    showSubmitFrame(mDebug);
    //�ύ
    var polNo = mSwitch.getVar("ApprovePolNo");
    window.top.fraSubmit.window.location = "./ProposalApproveSave.jsp?polNo=" + polNo + "&approveFlag=1";
}

//************************
var cflag = "5";
//���������λ�� 5.����

function QuestInput()
{
    cProposalNo = PolGrid.getRowColData(PolGrid.getSelNo() - 1, 1);
    //��������

    //showModalDialog("./QuestInputMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cFlag,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
    window.open("../uw/QuestInputMain.jsp?ContNo=" + cProposalNo + "&Flag=" + cflag, "window1");

    //initInpBox();
    //initPolBox();
    //initPolGrid();

}

function QuestReply()
{
    cProposalNo = PolGrid.getRowColData(PolGrid.getSelNo() - 1, 1);
    //��������

    //showModalDialog("./QuestInputMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cFlag,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
    window.open("../uw/QuestReplyMain.jsp?ProposalNo1=" + cProposalNo + "&Flag=" + cflag, "window1");

    //initInpBox();
    //initPolBox();
    //initPolGrid();

}

function QuestQuery()
{
    cProposalNo = PolGrid.getRowColData(PolGrid.getSelNo() - 1, 1);
    //��������

    //showModalDialog("./QuestInputMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cFlag,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
    window.open("../uw/QuestQueryMain.jsp?ProposalNo1=" + cProposalNo + "&Flag=" + cflag, "window1");

    //initInpBox();
    //initPolBox();
    //initPolGrid();

}


function returnparent()
{
    var backstr = document.all("ContNo").value;
    //alert(backstr+"backstr");
    mSwitch.deleteVar("ContNo");
    mSwitch.addVar("ContNo", "", backstr);
    mSwitch.updateVar("ContNo", "", backstr);
    location.href = "ContInsuredInput.jsp?LoadFlag=5&ContType=" + ContType;
}


/*function queryAgent()
{
    var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom=" + document.all('ManageCom').value + "&branchtype=1", "AgentCommonQueryMain", 'width=' + screen.availWidth + ',height=' + screen.availHeight + ',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

function afterQuery2(arrResult)
{

    if (arrResult != null)
    {
        fm.AgentCode.value = arrResult[0][0];
    }
}*/
