//�������ƣ�PEdorTypeRB.js
//�����ܣ����˱�ȫ-��ȫ����
//�������ڣ�2005-09-20 09:05:22
//������  ��zhangtao
//���¼�¼��  ������    ��������     ����ԭ��/����
//

var turnPage = new turnPageClass();
var sqlresourcename = "bq.PEdorTypeRBInputSql";

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
            top.opener.getEdorItemGrid();
            queryBackFee();
        }
        catch (ex) {}
    }
}

/**
 * ���ݲ�������(¼����ѯ)����������ť�Ƿ���ʾ
 */
function EdorQuery()
{
    var sButtonFlag;
    try
    {
        sButtonFlag = top.opener.document.getElementsByName("ButtonFlag")[0].value;
    }
    catch (ex) {}
    if (sButtonFlag != null && trim(sButtonFlag) == "1")
    {
        try
        {
            document.all("divEdorQuery").style.display = "none";
        }
        catch (ex) {}
    }
    else
    {
        try
        {
            document.all("divEdorQuery").style.display = "";
        }
        catch (ex) {}
    }
}

/**
 * �ύ��ȫ��Ŀ���
 */
function saveEdorTypeRB()
{
    if (!verifyInput2())    return;
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
    document.forms[0].action = "PEdorTypeRBSubmit.jsp";
    document.forms[0].submit();
}

/**
 *  ��ѯ��ȫ��ʷ
 */
function getEdorItemGrid()
{
    var tContNo = document.all('ContNo').value;
    if (tContNo != null && tContNo.trim() != "")
    {
         //var strSQL =  " select EdorAcceptNo, EdorNo, EdorType,"
/*                    //+ " (select distinct edorcode||'-'||edorname from lmedoritem m where m.appobj in ('I', 'B') and trim(m.edorcode) = trim(edortype)), "
                    + " DisplayType, "
                    + " GrpContNo, ContNo, InsuredNo, PolNo, EdorAppDate, EdorValiDate, "
                    + " (select CodeName from LDCode a where a.codetype = 'edorappreason' and trim(a.code) = trim(appreason)), appreason, "
                    + " nvl(GetMoney, 0), nvl(GetInterest, 0), MakeTime, ModifyDate, Operator, "
                    + " (select CodeName from LDCode b where b.codetype = 'edorstate' and trim(b.code) = trim(edorstate)),"
                    + " EdorState,EdorType "
                    + " from LPEdorItem "
                    + " where EdorType not in ('EB', 'RB') and edorstate = '0' and contno='" + tContNo + "' "
                    + " order by approvedate, approvetime ";
*/         
    var strSQL = "";
	var sqlid1="PEdorTypeRBInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(tContNo);//ָ������Ĳ���
	strSQL=mySql1.getString();

         
         arrResult = easyExecSql(strSQL);
         if (arrResult)
         {
             displayMultiline(arrResult,EdorItemGrid);
         }
    }
}

/**
 *  ��ѯ���һ�α�ȫ
 */
function getLastEdorItemGrid()
{
    var tContNo = document.all('ContNo').value;
    var EdorCount = EdorItemGrid.mulLineCount;
    if (EdorCount <= 0)
    {
        try
        {
            document.getElementsByName("btnSaveEdorRB")[0].disabled = true;
        }
        catch (ex) {}
        alert("�ñ���û�пɻ��˵ı�ȫ��Ŀ�� ");
        return;
    }
    var tLastEdorAcceptNo = EdorItemGrid.getRowColData(EdorCount -1 , 1);
    if(tLastEdorAcceptNo!=null)
    {
/*         var strSQL =  " select EdorAcceptNo, EdorNo, EdorType,"
                    //+ " (select distinct edorcode||'-'||edorname from lmedoritem m where  trim(m.edorcode) = trim(edortype)), "
                    + " DisplayType, "
                    + " GrpContNo, ContNo, InsuredNo, PolNo, EdorAppDate, EdorValiDate, "
                    + " (select CodeName from LDCode a where a.codetype = 'edorappreason' and trim(a.code) = trim(appreason)), appreason, "
                    + " nvl(GetMoney, 0), nvl(GetInterest, 0), MakeTime, ModifyDate, Operator, "
                    + " (select CodeName from LDCode b where b.codetype = 'edorstate' and trim(b.code) = trim(edorstate)),"
                    + " EdorState,EdorType "
                    + " from LPEdorItem "
                    + " where edorstate = '0' and contno = '" + tContNo + "' and edoracceptno='" + tLastEdorAcceptNo + "' "
                    + " order by approvedate, approvetime ";
*/         
    var strSQL = "";
	var sqlid2="PEdorTypeRBInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(tContNo);//ָ������Ĳ���
	mySql2.addSubPara(tLastEdorAcceptNo);
	strSQL=mySql2.getString();

         
         arrResult = easyExecSql(strSQL);
         if (arrResult)
         {
             displayMultiline(arrResult,LastEdorItemGrid);
         }
    }
}

/**
 * ��ѯ�ϴα���ı�ȫԭ��
 * XinYQ amodified on 2007-05-25
 */
function queryEdorReason()
{
    var QuerySQL, arrResult;
/*    QuerySQL = "select a.EdorReasonCode, "
             +        "(select CodeName "
             +           "from LDCode "
             +          "where 1 = 1 "
             +            "and CodeType = 'pedorrbreason' "
             +            "and Code = a.EdorReasonCode), "
             +        "a.EdorReason "
             +   "from LPEdorItem a "
             + "where 1 = 1 "
             +    getWherePart("a.EdorAcceptNo", "EdorAcceptNo")
             +    getWherePart("a.EdorNo", "EdorNo")
             +    getWherePart("a.EdorType", "EdorType");
    //alert(QuerySQL);
*/
    
	var sqlid3="PEdorTypeRBInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
	mySql3.addSubPara(fm.EdorAcceptNo.value);//ָ������Ĳ���
	mySql3.addSubPara(fm.EdorNo.value);
	mySql3.addSubPara(fm.EdorType.value);
	QuerySQL=mySql3.getString();

    
    try
    {
        arrResult = easyExecSql(QuerySQL, 1, 0);
    }
    catch (ex)
    {
        alert("���棺��ѯ�Ѿ�������ı�ȫԭ������쳣�� ");
        return;
    }
    if (arrResult != null)
    {
        try
        {
            document.getElementsByName("EdorReason")[0].value = arrResult[0][0];
            document.getElementsByName("EdorReasonName")[0].value = arrResult[0][1];
            document.getElementsByName("Remark")[0].value = arrResult[0][2];
        }
        catch (ex) {}
    }
}

/**
 * ���±��鿴
 */
function showNotePad()
{
    var sMissionID, sSubMissionID, sOtherNo;
    try
    {
        sMissionID = top.opener.document.getElementsByName("MissionID")[0].value;
        sSubMissionID = top.opener.document.getElementsByName("SubMissionID")[0].value;
        sOtherNo = top.opener.document.getElementsByName("OtherNo")[0].value;
    }
    catch (ex) {}
    if (sMissionID == null || trim(sMissionID) == "" || sSubMissionID == null || trim(sSubMissionID) == "" || sOtherNo == null || trim(sOtherNo) == "")
    {
        alert("���棺�޷���ȡ����������ڵ�����š��鿴���±�ʧ�ܣ� ");
        return;
    }
    var sOpenWinURL = "../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp";
    var sParameters = "MissionID="+ sMissionID + "&SubMissionID="+ sSubMissionID + "&ActivityID=0000000003&PrtNo="+ sOtherNo + "&NoType=1";
    OpenWindowNew(sOpenWinURL + "&" + sParameters, "���������±��鿴", "left");
}

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
