//�������ƣ�EdorApprove.js
//�����ܣ���ȫ����

var showInfo;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var arrDataSet;
/**
 *  ��ѯ��ȫ������Ϣ
 *  ����: ��ѯ��ȫ������Ϣ
 */
function initQuery()
{
    var EdorAcceptNo    = document.all('EdorAcceptNo').value;
    var MissionID       = document.all('MissionID').value
    var SubMissionID    = document.all('SubMissionID').value

    if(EdorAcceptNo == null || EdorAcceptNo == "")
    {
        alert("��ȫ�����Ϊ�գ�");
        return;
    }
    if(MissionID == null || MissionID == "")
    {
        alert("�����Ϊ�գ�");
        return;
    }
    if(SubMissionID == null || SubMissionID == "")
    {
        alert("�������Ϊ�գ�");
        return;
    }

    var strSQL;

    //��ѯ��ȫ������Ϣ
    var sqlid902160512="DSHomeContSql902160512";
var mySql902160512=new SqlClass();
mySql902160512.setResourceName("bq.GEdorApproveInputSql");//ָ��ʹ�õ�properties�ļ���
mySql902160512.setSqlId(sqlid902160512);//ָ��ʹ�õ�Sql��id
mySql902160512.addSubPara(EdorAcceptNo);//ָ������Ĳ���
strSQL=mySql902160512.getString();
    
//    strSQL =  " select OtherNo, (select trim(code)||'-'||trim(codename) from ldcode where codetype = 'gedornotype' and code = OtherNoType), "
//            + " GetMoney,EdorAppName, "
//            + " (select trim(code)||'-'||trim(codename) from ldcode where codetype = 'edorapptype' and code = Apptype), "
//            + " (select trim(code)||'-'||trim(codename) from ldcode where codetype = 'station' and code = ManageCom),edorstate,othernotype, "
//            + " GetInterest,Apptype,ManageCom "
//            + " from LPEdorApp "
//            + " where EdorAcceptNo = '" + EdorAcceptNo + "' ";
    var brr = easyExecSql(strSQL);
    if ( brr )
    {
        brr[0][0]==null||brr[0][0]=='null'?'0':fm.OtherNo.value     = brr[0][0];
        brr[0][1]==null||brr[0][1]=='null'?'0':fm.OtherNoTypeName.value = brr[0][1];
        brr[0][2]==null||brr[0][2]=='null'?'0':fm.GetMoney.value    = brr[0][2];
        brr[0][3]==null||brr[0][3]=='null'?'0':fm.EdorAppName.value = brr[0][3];
        brr[0][4]==null||brr[0][4]=='null'?'0':fm.ApptypeName.value     = brr[0][4];
        brr[0][5]==null||brr[0][5]=='null'?'0':fm.ManageComName.value   = brr[0][5];
        brr[0][6]==null||brr[0][6]=='null'?'0':fm.EdorState.value   = brr[0][6];
        brr[0][7]==null||brr[0][7]=='null'?'0':fm.OtherNoType.value = brr[0][7];
        brr[0][8]==null||brr[0][8]=='null'?'0':fm.GetInterest.value = brr[0][8];
        brr[0][9]==null||brr[0][9]=='null'?'0':fm.Apptype.value     = brr[0][9];
        brr[0][10]==null||brr[0][10]=='null'?'0':fm.ManageCom.value   = brr[0][10];
    }
    else
    {
        alert("��ȫ�����ѯʧ�ܣ�");
        return;
    }

    //��ѯ��ȫ������Ϣ
    var sqlid902160633="DSHomeContSql902160633";
var mySql902160633=new SqlClass();
mySql902160633.setResourceName("bq.GEdorApproveInputSql");//ָ��ʹ�õ�properties�ļ���
mySql902160633.setSqlId(sqlid902160633);//ָ��ʹ�õ�Sql��id
mySql902160633.addSubPara(MissionID);//ָ������Ĳ���
mySql902160633.addSubPara(SubMissionID);//ָ������Ĳ���
strSQL=mySql902160633.getString();
    
//    strSQL =  " select missionprop11, missionprop12 from lwmission where activityid = '0000008007' and missionid = '" + MissionID + "' and submissionid = '" + SubMissionID + "'  ";
    var drr = easyExecSql(strSQL);

    if ( drr )
    {
        drr[0][0]==null||drr[0][0]=='null'?'0':fm.AppntName.value     = drr[0][0];
        drr[0][1]==null||drr[0][1]=='null'?'0':fm.PaytoDate.value = drr[0][1];
    }
    else
    {
        fm.AppntName.value = "";
        fm.PaytoDate.value = "";
    }
    showEdorMainList();

}

/**
 *  ��ѯ��ȫ��Ŀ�б�
 *  ����: ��ѯ��ȫ��Ŀ�б�
 */
function showEdorItemListAuto()
{
    var tSel = 0;

    var EdorNo = EdorMainGrid.getRowColData(tSel, 1);
    var GrpContNo = EdorMainGrid.getRowColData(tSel, 2);
    var EdorMainState = EdorMainGrid.getRowColData(tSel, 9);
    document.all('EdorNo').value = EdorNo;
    document.all('GrpContNo').value = GrpContNo;
    document.all('EdorMainState').value = EdorMainState;

    var strSQL;
    
    var sqlid902160722="DSHomeContSql902160722";
var mySql902160722=new SqlClass();
mySql902160722.setResourceName("bq.GEdorApproveInputSql");//ָ��ʹ�õ�properties�ļ���
mySql902160722.setSqlId(sqlid902160722);//ָ��ʹ�õ�Sql��id
mySql902160722.addSubPara(EdorNo);//ָ������Ĳ���
strSQL=mySql902160722.getString();

    
//    strSQL =  " select EdorNo, "
//            + " (select distinct edorcode||'-'||edorname from lmedoritem m where m.appobj = 'G' and  trim(m.edorcode) = trim(edortype)), "
//            + " GrpContNo, "
//            + " EdorValiDate, nvl(GetMoney,0.00), nvl(GetInterest,0.00), "
//            + " (select c.codename from ldcode c where c.codetype = 'edorstate' and trim(c.code)=trim(EdorState)), "
//            + " EdorState, EdorAppDate, EdorType,EdorTypeCal "
//            + " from LPGrpEdorItem "
//            + " where EdorNo = '" + EdorNo + "'" ;
    var drr = easyExecSql(strSQL);
    if ( !drr )
    {
        alert("����������û�б�ȫ��Ŀ��");
        return;
    }
    else
    {
        turnPage.queryModal(strSQL, EdorItemGrid);
        divEdorItemInfo.style.display='';
    }
}

/**
 *  ��ѯ��ȫ��Ŀ�б�
 *  ����: ��ѯ��ȫ��Ŀ�б�
 */
function showEdorItemList()
{
    var tSel = EdorMainGrid.getSelNo() - 1;

    var EdorNo = EdorMainGrid.getRowColData(tSel, 1);
    var GrpContNo = EdorMainGrid.getRowColData(tSel, 2);
    var EdorMainState = EdorMainGrid.getRowColData(tSel, 9);
    document.all('EdorNo').value = EdorNo;
    document.all('GrpContNo').value = GrpContNo;
    document.all('EdorMainState').value = EdorMainState;

    var strSQL;
    
    var sqlid902160800="DSHomeContSql902160800";
var mySql902160800=new SqlClass();
mySql902160800.setResourceName("bq.GEdorApproveInputSql");//ָ��ʹ�õ�properties�ļ���
mySql902160800.setSqlId(sqlid902160800);//ָ��ʹ�õ�Sql��id
mySql902160800.addSubPara(EdorNo);//ָ������Ĳ���
strSQL=mySql902160800.getString();
    
//    strSQL =  " select EdorNo, "
//            + " (select distinct edorcode||'-'||edorname from lmedoritem m where m.appobj = 'G' and  trim(m.edorcode) = trim(edortype)), "
//            + " GrpContNo, "
//            + " EdorValiDate, nvl(GetMoney,0.00), nvl(GetInterest,0.00), "
//            + " (select c.codename from ldcode c where c.codetype = 'edorstate' and trim(c.code)=trim(EdorState)), "
//            + " EdorState "
//            + " from LPGrpEdorItem "
//            + " where EdorNo = '" + EdorNo + "'" ;
    var drr = easyExecSql(strSQL);
    if ( !drr )
    {
        alert("����������û�б�ȫ��Ŀ��");
        return;
    }
    else
    {
        turnPage.queryModal(strSQL, EdorItemGrid);
        divEdorItemInfo.style.display='';
    }
}

/**
 *  ��ѯ��ȫ�����б�
 *  ����: ��ѯ��ȫ�����б�
 */
function showEdorMainList()
{
    var EdorAcceptNo    = document.all('EdorAcceptNo').value;

    //��ѯ��ȫ������Ϣ
    
    var sqlid902160848="DSHomeContSql902160848";
var mySql902160848=new SqlClass();
mySql902160848.setResourceName("bq.GEdorApproveInputSql");//ָ��ʹ�õ�properties�ļ���
mySql902160848.setSqlId(sqlid902160848);//ָ��ʹ�õ�Sql��id
mySql902160848.addSubPara(EdorAcceptNo);//ָ������Ĳ���
strSQL=mySql902160848.getString();
    
//    strSQL =  " select a.EdorNo, a.GrpContNo, '',"
//                + " a.EdorAppDate, a.EdorValiDate, nvl(a.GetMoney,0), nvl(GetInterest,0), "
//                + " (select c.codename from ldcode c where c.codetype = 'edorstate' and trim(c.code)=trim(a.EdorState)), "
//                + " a.EdorState "
//                + " from LPGrpEdorMain a "
//                + " where a.EdorAcceptNo = '" + EdorAcceptNo + "' ";
    var crr = easyExecSql(strSQL);
    if ( !crr )
    {
        alert("��ȫ������û��������");
        return;
    }
    else
    {
        turnPage.queryModal(strSQL, EdorMainGrid);
        divEdorMainInfo.style.display='';
        showEdorItemListAuto();
    }
}

/**
 *  ȡ����ȫ��Ŀ��Ϣ
 *  ����: ȡ����ȫ��Ŀ��Ϣ
 */
function getEdorItemInfo()
{
    var tSel = EdorItemGrid.getSelNo() - 1;

    fm.EdorNo.value         = EdorItemGrid.getRowColData(tSel, 1);
    fm.EdorType.value       = EdorItemGrid.getRowColData(tSel, 10);
    fm.ContNo.value         = EdorItemGrid.getRowColData(tSel, 3);
    //alert(fm.EdorType.value);
    //fm.InsuredNo.value      = EdorItemGrid.getRowColData(tSel, 4);
    //fm.PolNo.value          = EdorItemGrid.getRowColData(tSel, 5);
    fm.EdorItemState.value  = EdorItemGrid.getRowColData(tSel, 8);
    fm.EdorItemAppDate.value       = EdorItemGrid.getRowColData(tSel, 9);
    fm.EdorAppDate.value           = EdorItemGrid.getRowColData(tSel, 9);
    fm.EdorValiDate.value          = EdorItemGrid.getRowColData(tSel, 4);
    fm.EdorTypeCal.value          = EdorItemGrid.getRowColData(tSel, 11);
    fm.ContNoApp.value            = EdorItemGrid.getRowColData(tSel, 3);
    
    var sqlid902160938="DSHomeContSql902160938";
var mySql902160938=new SqlClass();
mySql902160938.setResourceName("bq.GEdorApproveInputSql");//ָ��ʹ�õ�properties�ļ���
mySql902160938.setSqlId(sqlid902160938);//ָ��ʹ�õ�Sql��id
mySql902160938.addSubPara(fm.EdorType.value);//ָ������Ĳ���
var strSQL=mySql902160938.getString();
    
//    var strSQL =  " select edorname from lmedoritem where appobj='G' and edorcode='"+fm.EdorType.value+"' ";
    var tResult = easyExecSql(strSQL);
    if(!tResult)
    {
    	alert("û�в�ѯ����Ŀ����") ;
    	return;
    }
    fm.EdorTypeName.value= tResult[0][0] ;
    
    //fm.MakeDate.value       = EdorItemGrid.getRowColData(tSel, 11);
    //fm.MakeTime.value       = EdorItemGrid.getRowColData(tSel, 12);

    //���Ӳ��˷�����
    
}

/**
 *  ��ȫ�����ύ
 *  ����: ��ȫ�����ύ
 */
function ApproveSubmit()
{
    if (fm.EdorAcceptNo.value == "" )
    {
        alert("��ȫ�����Ϊ�գ�");
        return;
    }
		var sqlid902161051="DSHomeContSql902161051";
var mySql902161051=new SqlClass();
mySql902161051.setResourceName("bq.GEdorApproveInputSql");//ָ��ʹ�õ�properties�ļ���
mySql902161051.setSqlId(sqlid902161051);//ָ��ʹ�õ�Sql��id
mySql902161051.addSubPara(fm.OtherNo.value);//ָ������Ĳ���
mySql902161051.addSubPara(fm.EdorAcceptNo.value);//ָ������Ĳ���
var tSQL=mySql902161051.getString();
		
//		var tSQL = "select distinct 1 from LOPRTManager where otherno = '"+fm.OtherNo.value+"' and StandbyFlag3='2' and othernotype='04' and StateFlag = 'A' and StandByFlag1 = '"+fm.EdorAcceptNo.value+"'";
		var arrResult = easyExecSql(tSQL, 1, 0, 1);
		if(arrResult!=null&&arrResult[0][0]=="1")
  	{
  		alert("��������������δ�ظ��������,��ȴ��ظ������º˱�����!");
  		return;
    }
    var tApproveFlag        = fm.ApproveFlag.value;
    var tApproveContent     = fm.ApproveContent.value;
    var sModifyReason       = fm.ModifyReason.value;

    if(tApproveFlag == null || tApproveFlag == "")
    {
        alert("��¼�뱣ȫ��������!");
        return ;
    }

    var tApproveStateName = fm.edorapproveideaName.value;

    if(!confirm("ȷ���˱�����Ϊ��"+tApproveFlag+"-"+tApproveStateName+"��\nһ��ȷ�Ͻ����ɽ����޸ģ�"))
    {
    	return
    }
    //<!-- XinYQ added on 2005-11-28 : �����޸�ԭ�� : BGN -->
    if (tApproveFlag == "2" && (sModifyReason == null || sModifyReason == ""))
    {
        alert("��¼�������޸�ԭ�� ");
        return;
    }
    
    if (tApproveFlag == "2" && sModifyReason=="2" && (tApproveContent== null || tApproveContent == ""))
    {
        alert("��¼�������޸�ԭ�� ");
        return;
    }
    if (!verifyInput2()) return;
    //<!-- XinYQ added on 2005-11-28 : �����޸�ԭ�� : END -->

    //if (tApproveContent == null || tApproveContent == "")
    //{
    //  alert("��¼�뱣ȫ�������!");
    //  return ;
    //}

    var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    document.all('ActionFlag').value = "APPROVESUBMIT";
    fm.action = "GEdorApproveSave.jsp";
    fm.target="fraSubmit";
    fm.submit();
}


function sendAskMsg()
{
		var sqlid902161152="DSHomeContSql902161152";
var mySql902161152=new SqlClass();
mySql902161152.setResourceName("bq.GEdorApproveInputSql");//ָ��ʹ�õ�properties�ļ���
mySql902161152.setSqlId(sqlid902161152);//ָ��ʹ�õ�Sql��id
mySql902161152.addSubPara(fm.OtherNo.value);//ָ������Ĳ���
mySql902161152.addSubPara(fm.EdorAcceptNo.value);//ָ������Ĳ���
var tSQL=mySql902161152.getString();
		
//		var tSQL = "select distinct 1 from LOPRTManager where otherno = '"+fm.OtherNo.value+"' and StandbyFlag3='2' and othernotype='04' and StateFlag = 'A' and StandByFlag1 = '"+fm.EdorAcceptNo.value+"'";
		var arrResult = easyExecSql(tSQL, 1, 0, 1);
		if(arrResult!=null&&arrResult[0][0]=="1")
  	{
  		alert("�������������Ѿ����·��������,�����ٴ��·�!");
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
	var iHeight=300;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (MagPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    document.forms(0).action = "GEdorNoticeSave.jsp?AskOperate=INSERT";
    document.forms(0).target = "fraSubmit";
    document.forms(0).submit();
}

function queryAskMsg()
{
		// ��ʼ�����
	fm.AskInfo.value="";        
  fm.ReplyInfo.value="";
	initAgentGrid();
	
	// ��дSQL���
	var strSQL = "";

	
	var sqlid902161248="DSHomeContSql902161248";
var mySql902161248=new SqlClass();
mySql902161248.setResourceName("bq.GEdorApproveInputSql");//ָ��ʹ�õ�properties�ļ���
mySql902161248.setSqlId(sqlid902161248);//ָ��ʹ�õ�Sql��id
mySql902161248.addSubPara(fm.EdorAcceptNo.value);//ָ������Ĳ���
strSQL=mySql902161248.getString();
	
//	strSQL = "select PrtSeq,OtherNo,StandbyFlag1,StandbyFlag2,StandbyFlag5,decode(StandbyFlag3,'1','�˹��˱������','2','��ȫ���������','����'),MakeDate,StandbyFlag7,StandbyFlag4,StandbyFlag6 from LOPRTManager where 1=1 "
//					+ " and othernotype='04' and StandbyFlag1 = '"+fm.EdorAcceptNo.value+"' and StandbyFlag3 = '2' order by PrtSeq";
					
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
/**
 *  ��ȫ����ȡ��
 *  ����: ��ȫ����ȡ��
 */
function ApproveCancel()
{
    document.all('ApproveFlag').value = "";
    document.all('ApproveContent').value = "";
}

/**
 * �ύ�����, ���������ݷ��غ�ִ�еĲ���
 */
function afterSubmit(DealFlag, MsgContent, OtherFlag)
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
            top.opener.easyQueryClickSelf();
            showEdorMainList();
            if (OtherFlag == "1")//��ֱ��ȷ�ϣ��ж���ʾ�ĸ���ӡ֪ͨ��
            {
                //checkNotice();
                //checkEdorPrint();
                divGetNotice.style.display = '';
            }
            else
            {
                //document.all("divPayNotice").style.display = "";
            }
        }
        catch (ex) {}
    }
}

/*============================================================================*/

/**
* showCodeList �Ļص�����
*/
function afterCodeSelect(sCodeListType, oCodeListField)
{
    sCodeListType = sCodeListType.toLowerCase();
    if (sCodeListType == "edorapproveidea")
    {
        if (oCodeListField.value == "2")
        {
            try
            {
                document.all("divApproveMofiyReasonTitle").style.display = "";
                document.all("divApproveMofiyReasonInput").style.display = "";
            }
            catch (ex) {}
        }
        else
        {
            try
            {
                document.all("divApproveMofiyReasonTitle").style.display = "none";
                document.all("divApproveMofiyReasonInput").style.display = "none";
            }
            catch (ex) {}
        }
    } //EdorApproveReason
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

/**
 * ɨ�����ϸ
 */
function ScanDetail()
{
	var EdorAcceptNo    = document.all('EdorAcceptNo').value;
	var tUrl="../bq/ImageQueryMain.jsp?BussNo="+EdorAcceptNo+"&BussType=BQ";
	OpenWindowNew(tUrl,"��ȫɨ��Ӱ��","left");
}

/*****************************************************************************
 *  ����EdorAcceptNo����������Ϣ
 *****************************************************************************/
function EndorseDetail()
{
    var nSelNo;
    try
    {
        nSelNo = EdorItemGrid.getSelNo() - 1;
    }
    catch (ex) {}
    if (nSelNo == null || nSelNo < 0)
    {
        alert("����ѡ����Ҫ�鿴��������Ŀ�� ");
        return;
    }
    else
    {
        document.forms[0].action = "../f1print/AppEndorsementF1PJ1.jsp";
        document.forms[0]).target = "_blank";
        document.forms[0].submit();
    }
}

/**
 * ���� EdorNo ���������嵥��Ϣ
 * Added by XinYQ on 2006-09-21
 */
function NamesBill()
{
    var nSelNo;
    try
    {
        nSelNo = EdorItemGrid.getSelNo() - 1;
    }
    catch (ex) {}
    if (nSelNo == null || nSelNo < 0)
    {
        alert("����ѡ����Ҫ�鿴��������Ŀ�� ");
        return;
    }
    else
    {
        var sEdorNo;
        try
        {
            sEdorNo = document.getElementsByName("EdorNo")[0].value;
        }
        catch (ex) {}
        if (sEdorNo == null || trim(sEdorNo) == "")
        {
            alert("�޷���ȡ�����š���ѯ�����嵥ʧ�ܣ� ");
            return;
        }
        else
        {
            var QuerySQL, arrResult;
            
            var sqlid902161353="DSHomeContSql902161353";
var mySql902161353=new SqlClass();
mySql902161353.setResourceName("bq.GEdorApproveInputSql");//ָ��ʹ�õ�properties�ļ���
mySql902161353.setSqlId(sqlid902161353);//ָ��ʹ�õ�Sql��id
mySql902161353.addSubPara(sEdorNo);//ָ������Ĳ���
QuerySQL=mySql902161353.getString();
            
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
        }
    } //nSelNo != null
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
    window.open("./EdorGrpUWQueryMain.jsp?EdorAcceptNo="+pEdorAcceptNo,"window1");
}

//<!-- XinYQ modified on 2006-09-18 : ֱ�ӽ��뱣ȫ��Ŀ��ϸ : BGN -->
function EdorDetailQuery()
{
    var nSelNo, sEdorType, sEdorState;
    try
    {
        nSelNo = EdorItemGrid.getSelNo() - 1;
    }
    catch (ex) {}
    if (nSelNo == null || nSelNo < 0)
    {
        alert("����ѡ����Ҫ�鿴��������Ŀ�� ");
        return;
    }
    else
    {
        var sEdorType, sEdorState;
        try
        {
            sEdorType = document.getElementsByName("EdorType")[0].value;
            sEdorState = document.getElementsByName("EdorState")[0].value;
        }
        catch (ex) {}
        if (sEdorType == null || trim(sEdorType) == "")
        {
            alert("���棺�޷���ȡ��ȫ������Ŀ������Ϣ����ѯ��ȫ��ϸʧ�ܣ� ");
            return;
        }
        //if (sEdorState != null && trim(sEdorState) == "0")
        //{
        //    OpenWindowNew("../bqs/GEdorType" + trim(sEdorType) + ".jsp", "��ȫ��Ŀ��ϸ��ѯ", "left");    //bqs
        //}
        //else
        //{
        //    OpenWindowNew("../bq/GEdorType" + trim(sEdorType) + ".jsp", "��ȫ��Ŀ��ϸ��ѯ", "left");    //bq
        //}
 
        detailEdorType();
    }
}
//<!-- XinYQ modified on 2006-09-18 : ֱ�ӽ��뱣ȫ��Ŀ��ϸ : END -->

function GetNotice()
{
   var EdorAcceptNo    = document.all('EdorAcceptNo').value;
   
   var sqlid902161448="DSHomeContSql902161448";
var mySql902161448=new SqlClass();
mySql902161448.setResourceName("bq.GEdorApproveInputSql");//ָ��ʹ�õ�properties�ļ���
mySql902161448.setSqlId(sqlid902161448);//ָ��ʹ�õ�Sql��id
mySql902161448.addSubPara(EdorAcceptNo);//ָ������Ĳ���
var strSQL=mySql902161448.getString();
   
//   var strSQL = "select prtseq from loprtmanager where code = 'BQ51' and otherno = '"+EdorAcceptNo+"'";
   var sResult;
   sResult = easyExecSql(strSQL);
   if(sResult == null){
       alert("��ѯ����֪ͨ����Ϣʧ�ܣ�");
       return;
   }
   document.all('PrtSeq').value = sResult[0][0];
   fm.action = "./EdorNoticePrintSave.jsp";
   fm.target = "f1print";
   fm.submit();
}

function PayNotice()
{
     var EdorAcceptNo    = document.all('EdorAcceptNo').value;
     
     var sqlid902161539="DSHomeContSql902161539";
var mySql902161539=new SqlClass();
mySql902161539.setResourceName("bq.GEdorApproveInputSql");//ָ��ʹ�õ�properties�ļ���
mySql902161539.setSqlId(sqlid902161539);//ָ��ʹ�õ�Sql��id
mySql902161539.addSubPara(EdorAcceptNo);//ָ������Ĳ���
var strSQL=mySql902161539.getString();

     
//   var strSQL = "select prtseq from loprtmanager where code = 'BQ51' and otherno = '"+EdorAcceptNo+"'";
   var sResult;
   sResult = easyExecSql(strSQL);
   if(sResult == null){
       alert("��ѯ���ѷ�֪ͨ����Ϣʧ�ܣ�");
       return;
   }
   document.all('PrtSeq').value = sResult[0][0];
   fm.action = "./EdorNoticePrintSave.jsp?";
   fm.target = "f1print";
   fm.submit();
}

function checkNotice()
{
   var EdorAcceptNo    = document.all('EdorAcceptNo').value;
   
   var sqlid902161626="DSHomeContSql902161626";
var mySql902161626=new SqlClass();
mySql902161626.setResourceName("bq.GEdorApproveInputSql");//ָ��ʹ�õ�properties�ļ���
mySql902161626.setSqlId(sqlid902161626);//ָ��ʹ�õ�Sql��id
mySql902161626.addSubPara(EdorAcceptNo);//ָ������Ĳ���
var strSQL=mySql902161626.getString();
   
//   var strSQL = "select getmoney from lpgrpedoritem where getmoney <> 0 and getmoney is not null and edoracceptno = '" + EdorAcceptNo + "'";
   var sResult;
   sResult = easyExecSql(strSQL);
   if (sResult != null)
   {
      divGetNotice.style.display = '';
   }
}

/**
 *  ��ȫ�����켣��ѯ
 *  ����: ��ȫ�����켣��ѯ
 */
function MissionQuery()
{
    var pMissionID=fm.MissionID.value;
    window.open("./EdorMissionFrame.jsp?MissionID="+pMissionID,"window3","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}

/**
 * ����Ƿ���Ҫ��ӡ����
 * XinYQ added on 2005-11-09
 */
function checkEdorPrint()
{
    var QuerySQL, arrResult;
    
    var sqlid902161727="DSHomeContSql902161727";
var mySql902161727=new SqlClass();
mySql902161727.setResourceName("bq.GEdorApproveInputSql");//ָ��ʹ�õ�properties�ļ���
mySql902161727.setSqlId(sqlid902161727);//ָ��ʹ�õ�Sql��id
mySql902161727.addSubPara(fm.EdorAcceptNo.value);//ָ������Ĳ���
QuerySQL=mySql902161727.getString();

    
//    QuerySQL = "select EdorNo "
//             +   "from LPEdorPrint "
//             +  "where 1 = 1 "
//             +    "and EdorNo in "
//             +        "(select EdorNo "
//             +           "from LPGrpEdorItem "
//             +          "where 1 = 1 "
//             +             getWherePart("EdorAcceptNo", "EdorAcceptNo")
//             +        ")";
    //alert(QuerySQL);
    try
    {
        arrResult = easyExecSql(QuerySQL, 1, 0);
    }
    catch (ex)
    {
        alert("���棺����Ƿ���Ҫ��ӡ������Ϣ�����쳣�� ");
        return;
    }
    if (arrResult != null)
    {
        if (confirm("�Ƿ��ӡ���α�ȫ�������� "))
        {
            document.forms(0).action = "../f1print/AppEndorsementUptF1PJ1.jsp";
            document.forms(0).target="f1print";
            document.forms(0).submit();
        }
    }
}

function GrpContInfoQuery()
{
		var cPrtNo = "";
		var cGrpContNo=fm.OtherNo.value;
		var QuerySQL, arrResult;
		
		var sqlid902161824="DSHomeContSql902161824";
var mySql902161824=new SqlClass();
mySql902161824.setResourceName("bq.GEdorApproveInputSql");//ָ��ʹ�õ�properties�ļ���
mySql902161824.setSqlId(sqlid902161824);//ָ��ʹ�õ�Sql��id
mySql902161824.addSubPara(cGrpContNo);//ָ������Ĳ���
QuerySQL=mySql902161824.getString();
		
//    QuerySQL = "select PrtNo "
//             +   "from LCGrpCont "
//             +  "where 1 = 1 "
//             +    "and GrpContNo = '"+cGrpContNo+"'";
    arrResult = easyExecSql(QuerySQL, 1, 0);
    cPrtNo = arrResult[0][0];
		try
		{
			window.open("../sys/GrpPolDetailQueryMain.jsp?PrtNo=" + cPrtNo + "&GrpContNo=" + cGrpContNo ,'','width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
		}
		catch(ex)
		{
			alert( "û�з��ָ����ڵ�afterQuery�ӿڡ�" + ex );
		}
}
