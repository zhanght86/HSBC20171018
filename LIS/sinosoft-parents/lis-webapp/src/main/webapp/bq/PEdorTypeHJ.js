/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : �޻� <luohui@sinosoft.com.cn>
 * @version  : 1.00, 1.01, 1.02, 1.03
 * @direction: �ָ����ѳ�����
 ******************************************************************************/

//<!-- JavaScript Document BGN -->


/*============================================================================*/

var showInfo;                                      //ȫ�ֱ���, ������ʾ����, ������
var turnPage = new turnPageClass();                //ȫ�ֱ���, ��ѯ�����ҳ, ������
var turnPageCustomerGrid = new turnPageClass();    //ȫ�ֱ���, �����ͻ���ѯ�����ҳ

/*============================================================================*/
function getCustomerGrid()
{
    var tContNo=document.all("ContNo").value;
    if(tContNo!=null&&tContNo!="")
    {
		var strSQL = "";
		var sqlid1="PEdorTypeIPInputInputSql8";
		var mySql1=new SqlClass();
		var sqlresourcename = "bq.PEdorTypeIPInputInputSql";  
		mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(tContNo);
		mySql1.addSubPara(tContNo);
		strSQL=mySql1.getString();
          
        arrResult = easyExecSql(strSQL);
        if (arrResult)
        {
            displayMultiline(arrResult,CustomerGrid);
        }
    }
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
            queryBackFee();
            top.opener.getEdorItemGrid();
        }
        catch (ex) {}
    }
}

/*============================================================================*/

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
function saveEdorTypeHJ()
{
    var nNewBnfCount;
    
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
    document.forms(0).action = "PEdorTypeHJSubmit.jsp";
    document.forms(0).submit();
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

function queryEdorReason()
{
    var sEdorNo, sEdorType;
    try
    {
        sEdorNo = document.getElementsByName("EdorNo")[0].value;
        sEdorType = document.getElementsByName("EdorType")[0].value;
    }
    catch (ex) {}
    var QuerySQL, arrResult;
    var mySql=new SqlClass();
        mySql.setJspName("../../bq/PEdorTypeHJInputSql.jsp");
    mySql.setSqlId("PEdorTypeHJInputSql_3");
    mySql.addPara('EdorNo', sEdorNo);
    mySql.addPara('EdorType', sEdorType);
    try
    {
        QuerySQL = mySql.getString();
        arrResult = easyExecSql(QuerySQL);
    }
    catch (ex) {}
    if (arrResult != null && arrResult[0][0] != null)
    {
        try
        {
            document.getElementsByName("Remark")[0].value = arrResult[0][0];
        }
        catch (ex) {}
    }
}

function queryCustomerInfo()
{
    var ContNo=document.all("ContNo").value;
    if(ContNo!=null&&ContNo!="")
    {
        var mySql=new SqlClass();
        mySql.setJspName("../../bq/PEdorTypeHJInputSql.jsp");
        mySql.setSqlId("PEdorTypeHJInputSql_1");
        mySql.addPara('tContNo',ContNo);
        try
       {
           turnPageCustomerGrid.pageDivName = "divTurnPageCustomerGrid";
           turnPageCustomerGrid.queryModal(mySql.getSQL(), CustomerGrid);
       }
       catch (ex)
       {
           alert("���棺��ѯ�����ͻ���Ϣ�����쳣�� ");
           return;
       }
    }
}



/**
 * ��ѯ����������Ϣ
 */
function queryPolInfo()
{
    var sContNo, sPolNo;
    try
    {
        sContNo = document.getElementsByName("ContNo")[0].value;
        sPolNo = document.getElementsByName("PolNo")[0].value;
    }
    catch (ex) {}

    var QuerySQL, arrResult;
    var mySql = new SqlClass();
    mySql.setJspName("../../bq/PEdorTypeHJInputSql.jsp");
    mySql.setSqlId("PEdorTypeHJIInputSql_2");
    mySql.addPara("ContNo", sContNo);
    mySql.addPara("PolNo", sPolNo);
    try
    {
        arrResult = easyExecSql(mySql.getSQL(), 1, 0);
    }
    catch (ex)
    {
        alert("���棺��ѯ����������Ϣ�����쳣�� ");
        return;
    }
    if (arrResult != null)
    {
        try
        {
            document.getElementsByName("RiskCode")[0].value = arrResult[0][0];
            document.getElementsByName("RiskName")[0].value = arrResult[0][1];
            document.getElementsByName("CValiDate")[0].value = arrResult[0][2];
            document.getElementsByName("PayToDate")[0].value = arrResult[0][3];
            document.getElementsByName("Prem")[0].value = arrResult[0][4];
        }
        catch (ex) {}
    } //arrResult != null
}