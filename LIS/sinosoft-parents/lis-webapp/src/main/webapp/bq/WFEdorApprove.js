
//�������ƣ�WFEdorApprove.js
//�����ܣ���ȫ������-��ȫ����
//�������ڣ�2005-04-30 11:49:22
//������  ��sinosoft
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
            //easyQueryClickAll();
            //easyQueryClickSelf(true);
            jQuery("#publicSearch").click();
            jQuery("#privateSearch").click();
        }
        catch (ex) {}
    }
}
function HighlightAllRow(){
		for(var i=0; i<PublicWorkPoolGrid.mulLineCount; i++){
  	var days = PublicWorkPoolGrid.getRowColData(i,15);  	
  	if(days != null && days != ""){	
    	var ret = Arithmetic(days,'-','5','2');
    	if(ret>=0)
    	{
    		AllGrid.setRowClass(i,'warn');
    	}
    
  	}
  }
}
function jQueryClickSelf(){
jQuery("#privateSearch").click();
}
/*
function HighlightAllRow(){
		for(var i=0; i<AllGrid.mulLineCount; i++){
  	var days = AllGrid.getRowColData(i,15);  	
  	if(days != null && days != ""){	
    	var ret = Arithmetic(days,'-','5','2');
    	if(ret>=0)
    	{
    		AllGrid.setRowClass(i,'warn');
    	}
    
  	}
  }
}
*/
function HighlightSelfRow(){
		for(var i=0; i<PrivateWorkPoolGrid.mulLineCount; i++){
  	var days = PrivateWorkPoolGrid.getRowColData(i,15);  	
  	if(days != null && days != ""){	
    	var ret = Arithmetic(days,'-','5','2');
    	if(ret>=0)
    	{
    		SelfGrid.setRowClass(i,'warn');
    	}
    
  	}
  }
}
/*
function HighlightSelfRow(){
		for(var i=0; i<SelfGrid.mulLineCount; i++){
  	var days = SelfGrid.getRowColData(i,15);  	
  	if(days != null && days != ""){	
    	var ret = Arithmetic(days,'-','5','2');
    	if(ret>=0)
    	{
    		SelfGrid.setRowClass(i,'warn');
    	}
    
  	}
  }
}
*/
/*********************************************************************
 *  ��ѯ�����������
 *  ����:��ѯ�����������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
 /*
function easyQueryClickAll()
{
    // ��дSQL���
    var strSQL = "";
//    if(fm.EdorPopedom.value==null||fm.EdorPopedom.value=="")
//    {
//    	alert("��¼����͸��˼��𣡣�");
//    	return ;
//    }
/*
    strSQL =  " select missionprop1, missionprop2, "
            + " (select codename from ldcode d1 where trim(d1.codetype) = 'edornotype' and trim(d1.code) = trim(missionprop3) ), "
            + " missionprop11, "
            //+ " missionprop12, "
            + " (select min( paytodate) from lcpol c where polno = mainpolno and contno = missionprop2 ) paytodate, "
            + " (select codename from ldcode d2 where trim(d2.codetype) = 'edorapptype' and trim(d2.code) = trim(missionprop5) ), "
            + " missionprop7, "
            //+ " (select codename from ldcode d3 where trim(d3.codetype) = 'station' and trim(d3.code) = trim(missionprop7) ), "
            + " createoperator, a.makedate,b.apppregrade||' ����ȫԱ', missionid, submissionid, activityid, "
            + " (select nvl(edorappdate,'') from lpedoritem where edoracceptno = a.missionprop1 and rownum = 1),(select count(*) from ldcalendar where commondate > (select edorappdate from lpedoritem where edoracceptno = a.missionprop1 and rownum = 1) and commondate <= '"+curDay+"' and workdateflag = 'Y') "
            + " from lwmission a,lpedorapp b where 1=1  and a.missionprop1=b.EdorAcceptNo"
            + " and activityid = '0000000007' "  //��������ȫ-��ȫ����
            + " and processid = '0000000000' "
            + " and defaultoperator is null "
            + getWherePart('b.apppregrade','EdorPopedom')
            + getWherePart('missionprop1', 'EdorAcceptNo')
            + getWherePart('missionprop2', 'OtherNo')
            + getWherePart('missionprop3', 'OtherNoType')
            + getWherePart('missionprop4', 'EdorAppName')
            + getWherePart('missionprop5', 'AppType')
            + getWherePart('missionprop7', 'ManageCom','like')
            + getWherePart('a.MakeDate', 'MakeDate')
            + " and missionprop7 like '" + manageCom + "%%'"
            + " order by (select edorappdate from lpedoritem where EdorAcceptNo = a.missionprop1 and rownum = 1),a.MakeDate, a.MakeTime ";
    //prompt("",strSQL);
*/
    /*
    var sqlid1="WFEdorApproveSql1";
    var mySql1=new SqlClass();
    mySql1.setResourceName("bq.WFEdorApproveInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(curDay);//ָ������Ĳ���
	mySql1.addSubPara(fm.EdorPopedom.value);//ָ������Ĳ���
	mySql1.addSubPara(fm.EdorAcceptNo.value);
	mySql1.addSubPara(fm.OtherNo.value);
	mySql1.addSubPara(fm.OtherNoType.value);
	mySql1.addSubPara(fm.EdorAppName.value);
	mySql1.addSubPara(fm.AppType.value);
	mySql1.addSubPara(fm.ManageCom.value);
	mySql1.addSubPara(fm.MakeDate.value);
	mySql1.addSubPara(manageCom);
	strSQL=mySql1.getString();
    turnPageAllGrid.pageDivName = "divTurnPageAllGrid";
    turnPageAllGrid.queryModal(strSQL, AllGrid);// mySql.getString()�Ϳ��Ի�ö�Ӧ��SQL��
    
		HighlightAllRow();
    return true;
}

/*********************************************************************
 *  ��ѯ�ҵ��������
 *  ����: ��ѯ�ҵ��������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
 /*
function easyQueryClickSelf(isClickedApply)
{
    // ��дSQL���
    var strSQL = "";
/*
    strSQL =  " select missionprop1, missionprop2, "
            + " (select codename from ldcode d1 where trim(d1.codetype) = 'edornotype' and trim(d1.code) = trim(missionprop3) ), "
            + " missionprop11, "
            //+ " missionprop12, "
            + " (select min( paytodate ) from lcpol c where polno = mainpolno and contno = missionprop2 ) paytodate, "
            + " (select codename from ldcode d2 where trim(d2.codetype) = 'edorapptype' and trim(d2.code) = trim(missionprop5) ), "
            + " missionprop7, "
            //+ " (select codename from ldcode d3 where trim(d3.codetype) = 'station' and trim(d3.code) = trim(missionprop7) ), "
            + " createoperator, a.makedate,b.apppregrade||'����ȫԱ', missionid, submissionid, activityid, "
            + " (select nvl(edorappdate,'') from lpedoritem where edoracceptno = a.missionprop1 and rownum = 1),(select count(*) from ldcalendar where commondate > (select edorappdate from lpedoritem where edoracceptno = a.missionprop1 and rownum=1) and commondate <= '"+curDay+"' and workdateflag = 'Y'), "
            + " case (select count(distinct StateFlag) from LOPRTManager where othernotype='02' and StandbyFlag1 = a.missionprop1 and StandbyFlag3 = '3' and Code = 'BQ38') when 0 then '�����δ�·�' when 2 then '��������·�' "
            + " else decode((select distinct StateFlag from LOPRTManager where othernotype='02' and StandbyFlag1 = a.missionprop1 and StandbyFlag3 = '3' and Code = 'BQ38'),'A','��������·�','������ѻظ�') end "
            + " from lwmission a,lpedorapp b where 1=1  and a.missionprop1=b.EdorAcceptNo "
            + " and activityid = '0000000007' "  //��������ȫ-��ȫ����
            + " and processid = '0000000000' "
            + " and defaultoperator ='" + operator + "'"
            + " order by (select edorappdate from lpedoritem where EdorAcceptNo = a.missionprop1 and rownum=1),a.MakeDate,a.ModifyTime";
            //+ " order by MakeDate, MakeTime ";
*/
/*
    var sqlid2="WFEdorApproveSql2";
    var mySql2=new SqlClass();
    mySql2.setResourceName("bq.WFEdorApproveInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(curDay);//ָ������Ĳ���
	mySql2.addSubPara(operator);
	strSQL=mySql2.getString();
    
    turnPageSelfGrid.pageDivName = "divTurnPageSelfGrid";
    turnPageSelfGrid.queryModal(strSQL, SelfGrid);// mySql.getString()�Ϳ��Ի�ö�Ӧ��SQL��

    if (SelfGrid.mulLineCount > 0 && isClickedApply)
    {
        SelfGrid.selOneRow(1);
    }
    
    HighlightSelfRow();

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
    var selno = PrivateWorkPoolGrid.getSelNo()-1;
    if (selno <0)
    {
          alert("��ѡ��Ҫ���������");
          return;
    }
    var tEdorAcceptNo = PrivateWorkPoolGrid.getRowColData(selno, 1);
    var tMissionID = PrivateWorkPoolGrid.getRowColData(selno, 19);
    var tSubMissionID = PrivateWorkPoolGrid.getRowColData(selno, 20);
    var ActivityID = PrivateWorkPoolGrid.getRowColData(selno, 22);
    //ȡ�Ӵ� modify by jiaqiangli ��������������ַ�
    var tEdorPopedom = PrivateWorkPoolGrid.getRowColData(selno, 10).substr(0,1);
    
    var varSrc="&EdorAcceptNo=" + tEdorAcceptNo + "&ActivityID="+ ActivityID + "&MissionID=" + tMissionID + "&SubMissionID=" + tSubMissionID+"&EdorPopedom="+tEdorPopedom;
    var newWindow = OpenWindowNew("../bq/EdorApproveFrame.jsp?Interface= ../bq/EdorApproveInput.jsp" + varSrc,"��ȫ����","left");
}
 /*
function GoToBusiDeal()
{
    var selno = SelfGrid.getSelNo()-1;
    if (selno <0)
    {
          alert("��ѡ��Ҫ���������");
          return;
    }
    var tEdorAcceptNo = SelfGrid.getRowColData(selno, 1);
    var tMissionID = SelfGrid.getRowColData(selno, 11);
    var tSubMissionID = SelfGrid.getRowColData(selno, 12);
    var ActivityID = SelfGrid.getRowColData(selno, 13);
    //ȡ�Ӵ� modify by jiaqiangli ��������������ַ�
    var tEdorPopedom = SelfGrid.getRowColData(selno, 10).substr(0,1);
    
    var varSrc="&EdorAcceptNo=" + tEdorAcceptNo + "&ActivityID="+ ActivityID + "&MissionID=" + tMissionID + "&SubMissionID=" + tSubMissionID+"&EdorPopedom="+tEdorPopedom;
    var newWindow = OpenWindowNew("../bq/EdorApproveFrame.jsp?Interface= ../bq/EdorApproveInput.jsp" + varSrc,"��ȫ����","left");
}

/*********************************************************************
 *  ��������
 *  ����: ��������
 *  XinYQ rewrote on 2007-04-03
 *********************************************************************
 */
 
 function applyMission()
{
    var nSelNo = PublicWorkPoolGrid.getSelNo() - 1;
    if (nSelNo == null || nSelNo < 0)
    {
        alert("����ѡ����Ҫ��������� ");
        return;
    }
    else
    {
	    //alert(PublicWorkPoolGrid.getRowData(nSelNo));
        var sEdorAcceptNo = PublicWorkPoolGrid.getRowColData(nSelNo, 1);
        if (sEdorAcceptNo == null || trim(sEdorAcceptNo) == "")
        {
            alert("���Ȳ�ѯ��ѡ��һ������ ");
            return false;;
        }
        else
        {
            var sMissionID = PublicWorkPoolGrid.getRowColData(nSelNo, 19);
            var sSubMissionID = PublicWorkPoolGrid.getRowColData(nSelNo, 20);
            var sActivityID = PublicWorkPoolGrid.getRowColData(nSelNo, 22);
            if (sMissionID == null || trim(sMissionID) == "" || sSubMissionID == null || trim(sSubMissionID) == "")
            {
                alert("���棺�޷���ȡ����������ڵ���Ϣ�� ");
                return false;
            }
            //ϵͳ�����𼶸��ˣ�Ȩ�޲������Դ���
            else if(isCanAppliMission(sEdorAcceptNo)==false)
            {
            	  
                return false;
            	}
            else
            {
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
                document.forms[0].action = "WFEdorApproveSave.jsp?EdorAcceptNo=" + sEdorAcceptNo + "&MissionID=" + sMissionID + "&SubMissionID=" + sSubMissionID + "&ActivityID=" + sActivityID;
                document.forms[0].submit();
            }
        }
    } //nSelNo > 0
}
 /*
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
            return false;;
        }
        else
        {
            var sMissionID = AllGrid.getRowColData(nSelNo, 11);
            var sSubMissionID = AllGrid.getRowColData(nSelNo, 12);
            var sActivityID = AllGrid.getRowColData(nSelNo, 13);
            if (sMissionID == null || trim(sMissionID) == "" || sSubMissionID == null || trim(sSubMissionID) == "")
            {
                alert("���棺�޷���ȡ����������ڵ���Ϣ�� ");
                return false;
            }
            //ϵͳ�����𼶸��ˣ�Ȩ�޲������Դ���
            else if(isCanAppliMission(sEdorAcceptNo)==false)
            {
            	  
                return false;
            	}
            else
            {
                var MsgContent = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
                var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=C&content=" + MsgContent;
                showInfo = showModelessDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
                document.forms[0].action = "WFEdorApproveSave.jsp?EdorAcceptNo=" + sEdorAcceptNo + "&MissionID=" + sMissionID + "&SubMissionID=" + sSubMissionID + "&ActivityID=" + sActivityID;
                document.forms[0].submit();
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
    var selno = PrivateWorkPoolGrid.getSelNo()-1;
    if (selno <0)
    {
          alert("��ѡ��Ҫ���������");
          return;
    }
	//begin zhangbx 20110511
 //   var MissionID = SelfGrid.getRowColData(selno, 10);
  //  var SubMissionID = SelfGrid.getRowColData(selno, 11);
//    var ActivityID = SelfGrid.getRowColData(selno, 12);
    var MissionID = PrivateWorkPoolGrid.getRowColData(selno, 19);
    var SubMissionID = PrivateWorkPoolGrid.getRowColData(selno, 20);
    var ActivityID = PrivateWorkPoolGrid.getRowColData(selno, 22);
    //end zhangbx 20110511
    var OtherNo = PrivateWorkPoolGrid.getRowColData(selno, 2);
    var OtherNoType = "1";
    if(MissionID == null || MissionID == "")
    {
        alert("MissionIDΪ�գ�");
        return;
    }
    var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
    var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface= ../uw/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");
}
function isCanAppliMission(tEdorAcceptNo)
{
	//var tUserGradeSQL="select edorpopedom  from ldedoruser where usercode='"+operator+"' and usertype='1'";
	var sqlid1 = "WFEdorApproveSql1";
	var mySql1 = new SqlClass();
	mySql1.setResourceName("bq.WFEdorApproveSql"); // ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);// ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(operator);// ָ������Ĳ���
	var tUserGradeSQL = mySql1.getString();
	var arrResult;
	var tUserGrade;
  arrResult= easyExecSql(tUserGradeSQL, 1, 0,1);
  //alert(arrResult);
   tUserGrade=arrResult[0][0];
  //alert(tUserGrade);

	//var tAppGradeSQL="select apppregrade  from lpedorapp where EdorAcceptNo='"+tEdorAcceptNo+"'";
	var sqlid2 = "WFEdorApproveSql2";
	var mySql2 = new SqlClass();
	mySql2.setResourceName("bq.WFEdorApproveSql"); // ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);// ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(tEdorAcceptNo);// ָ������Ĳ���
	var tAppGradeSQL = mySql2.getString();
	var arrAppResult;
	var tAppGrade='';
  arrAppResult= easyExecSql(tAppGradeSQL, 1, 0,1);

  tAppGrade=arrAppResult[0][0];

  if(tAppGrade>tUserGrade)
  {
  	   alert("����Ȩ����"+tUserGrade+",Ŀǰ��Ҫ���˵�Ȩ����С��"+tAppGrade+"!!!") ;
       return false;
  	}	
}
 /*
function showNotePad()
{
    var selno = SelfGrid.getSelNo()-1;
    if (selno <0)
    {
          alert("��ѡ��Ҫ���������");
          return;
    }
	//begin zhangbx 20110511
 //   var MissionID = SelfGrid.getRowColData(selno, 10);
  //  var SubMissionID = SelfGrid.getRowColData(selno, 11);
//    var ActivityID = SelfGrid.getRowColData(selno, 12);
    var MissionID = SelfGrid.getRowColData(selno, 19);
    var SubMissionID = SelfGrid.getRowColData(selno, 20);
    var ActivityID = SelfGrid.getRowColData(selno, 22);
    //end zhangbx 20110511
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

function isCanAppliMission(tEdorAcceptNo)
{
	var tUserGradeSQL="select edorpopedom  from ldedoruser where usercode='"+operator+"' and usertype='1'";
	var arrResult;
	var tUserGrade;
  arrResult= easyExecSql(tUserGradeSQL, 1, 0,1);
  //alert(arrResult);
   tUserGrade=arrResult[0][0];
  //alert(tUserGrade);

	var tAppGradeSQL="select apppregrade  from lpedorapp where EdorAcceptNo='"+tEdorAcceptNo+"'";
	var arrAppResult;
	var tAppGrade='';
  arrAppResult= easyExecSql(tAppGradeSQL, 1, 0,1);

  tAppGrade=arrAppResult[0][0];

  if(tAppGrade>tUserGrade)
  {
  	   alert("����Ȩ����"+tUserGrade+",Ŀǰ��Ҫ���˵�Ȩ����С��"+tAppGrade+"!!!") ;
       return false;
  	}	
}
*/