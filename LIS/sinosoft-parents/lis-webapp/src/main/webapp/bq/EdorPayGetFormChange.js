/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : ����ǿ <XinYQ@sinosoft.com.cn>
 * @version  : 1.00, 1.01, 1.02, 1.03, 1.04
 * @date     : 2006-03-16, 2006-05-16, 2006-06-28, 2006-11-08, 2007-04-20
 * @direction: ��ȫ�ո��ѷ�ʽ������ű�
 ******************************************************************************/

//<!-- JavaScript Document BGN -->


/*============================================================================*/

var showInfo;                                      //ȫ�ֱ���, ������ʾ����, ������
var turnPage = new turnPageClass();                //ȫ�ֱ���, ��ѯ�����ҳ, ������
var turnPageEdorInfoGrid = new turnPageClass();    //ȫ�ֱ���, ��ȫ��Ϣ��ѯ�����ҳ
var turnPageChgTrackGrid = new turnPageClass();    //ȫ�ֱ���, ����켣��ѯ�����ҳ

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
			var iHeight=250;     //�������ڵĸ߶�; 
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
            queryEdorInfoGrid();
        }
        catch (ex) {}
    }
}

/*============================================================================*/

/**
 * showCodeList �Ļص�����, �����ʻ���Ϣ¼������ʾ������
 */
function afterCodeSelect(sCodeListType, oCodeListField)
{
    if (sCodeListType == "EdorGetPayForm")
    {
        try
        {
            if (oCodeListField.value == "4" || oCodeListField.value == "7")    //���л��������֧��
                enableBankInfo(true);
            else
                enableBankInfo(false);
        }
        catch (ex)
        {
            alert("���棺�����ʻ���Ϣ¼����ʾ/���س����쳣�� ");
        }
    } //CodeListType == EdorGetPayForm
}

/*============================================================================*/

/**
 * ����ѡ����ո��ѷ�ʽ����/��ֹ�����ʻ���Ϣ��¼��
 */
function enableBankInfo(isEnableInput)
{
    if (isEnableInput)
    {
        try
        {
            document.getElementsByName("BankCode")[0].disabled = false;
            document.getElementsByName("BankCodeName")[0].disabled = false;
            document.getElementsByName("BankAccNo")[0].disabled = false;
            document.getElementsByName("AccName")[0].disabled = false;
        }
        catch (ex) {}
    }
    else
    {
        try
        {
            document.getElementsByName("BankCode")[0].disabled = true;
            document.getElementsByName("BankCodeName")[0].disabled = true;
            document.getElementsByName("BankAccNo")[0].disabled = true;
            document.getElementsByName("AccName")[0].disabled = true;
        }
        catch (ex) {}
    }
}

/*============================================================================*/

/**
 * �س���ѯ��ȫ������Ϣ
 */
function queryOnKeyPress()
{
    if (event.keyCode == "13")
    {
        queryEdorAndTrack();
    }
}



/*============================================================================*/

/**
 * ��ѯ��ȫ������Ϣ
 */
function queryEdorInfoGrid()
{
	
	  var sEdorAcceptNo, sGetNoticeNo;
    try
    {
        sEdorAcceptNo = document.getElementsByName("EdorAcceptNo_srh")[0].value;
        sGetNoticeNo = document.getElementsByName("GetNoticeNo_srh")[0].value;
    }
    catch (ex) {}
    if ((sEdorAcceptNo == null || trim(sEdorAcceptNo) == "") && (sGetNoticeNo == null || trim(sGetNoticeNo) == ""))
    {
        alert("��������������һ����ѯ����(��ȫ����Ż�����ȡ֪ͨ���)�� ");
        return;
    }
    //��ѯӦ��ʵ��
    try
    {
        queryLJSPay();
        queryLJAGet();
        //queryBankInfo();
    }
    catch (ex) {}
    //�����ѯ�����Ϊ��
    if (EdorInfoGrid.mulLineCount > 0)
    {
        //�����ֶθ�ֵ
        try
        {
            document.getElementsByName("EdorAcceptNo")[0].value = EdorInfoGrid.getRowColData(0, 1);
            document.getElementsByName("GetNoticeNo")[0].value = EdorInfoGrid.getRowColData(0, 9);
        }
        catch (ex) {}
    }
}

/*============================================================================*/

/**
 * ��ѯӦ����Ϣ
 */
function queryLJSPay()
{
    var sqlid1="DSHomeContSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("bq.EdorPayGetFormChangeInputSql");//ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(fm.GetNoticeNo_srh.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.EdorAcceptNo_srh.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.Drawer_srh.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.DrawerIDNo_srh.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.EdorAcceptNo_srh.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.CurrentManageCom.value);//ָ������Ĳ���
		
		
		var QuerySQL=mySql1.getString();
    
//    var QuerySQL = "select b.EdorAcceptNo, "
//                 +        "b.GrpContNo, "
//                 +        "b.ManageCom, "
//                 +        "b.EdorType, "
//                 +        "a.SUMDUEPAYMONEY, "
//                 +        "b.GetInterest, "
//                 +        "a.PayDate, "
//                 +        "b.EdorValiDate, "
//                 +        "a.GetNoticeNo, "
//                 +        "c.PayForm,c.PayGetName,c.PersonID,c.BankCode,c.BankAccNo,c.AccName,'' "
//                 +   "from LJSPay a, LPGrpEdorItem b,lpedorapp c "
//                 +  "where 1 = 1 "
//                 +     getWherePart("a.GetNoticeNo", "GetNoticeNo_srh")
//                 +     getWherePart("a.OtherNo", "EdorAcceptNo_srh")
//                 +     getWherePart("c.PayGetName", "Drawer_srh","like")
//                 +     getWherePart("c.PersonID", "DrawerIDNo_srh","like")
//                 +     getWherePart("a.OtherNo", "EdorAcceptNo_srh")
//                 +     getWherePart("b.ManageCom", "CurrentManageCom", "like")
//                 +    "and a.OtherNoType = '10' "
//                 +    "and b.EdorAcceptNo = a.OtherNo "
//                 + " and c.EdorAcceptNo = a.otherno and c.edoracceptno = b.edoracceptno "
//                 +  "order by b.EdorAcceptNo asc, b.EdorValiDate asc";
    //alert(QuerySQL);
    try
    {
        turnPageEdorInfoGrid.pageDivName = "divTurnPageEdorInfoGrid";
        turnPageEdorInfoGrid.queryModal(QuerySQL, EdorInfoGrid);
    }
    catch (ex)
    {
        alert("���棺��ѯ��ȫ������Ϣ�����쳣�� ");
        return;
    }
    //�趨�����ʽ
    if (EdorInfoGrid.mulLineCount > 0)
    {
        try
        {
            document.getElementsByName("ChangeType")[0].value = "1";
            //document.getElementsByName("ChangeTypeName")[0].value = "�շ�";
            //document.getElementsByName("ChangeType")[0].ondblclick = "";
            //document.getElementsByName("ChangeType")[0].onkeyup = "";
            //document.getElementsByName("ChangeType")[0].readOnly = true;
        }
        catch (ex) {}
    }
}

/*============================================================================*/

/**
 * ��ѯʵ����Ϣ
 */
function queryLJAGet()
{
    //�Ƿ���Ҫ��ѯ
    if (EdorInfoGrid.mulLineCount > 0) return;
    //ƴд SQL ���
    
    var sqlid2="DSHomeContSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("bq.EdorPayGetFormChangeInputSql");//ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(fm.CurrentManageCom.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.GetNoticeNo_srh.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.Drawer_srh.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.DrawerIDNo_srh.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.EdorAcceptNo_srh.value);//ָ������Ĳ���
		
		var QuerySQL=mySql2.getString();
    
//    var QuerySQL = "select b.EdorAcceptNo, "
//                 +        "b.GrpContNo, "
//                 +        "b.ManageCom, "
//                 +        "b.EdorType, "
//                 +        "a.SUMGETMONEY, "
//                 +        "b.GetInterest, "
//                 +        "a.EnterAccDate, "
//                 +        "b.EdorValiDate, "
//                 +        "a.GetNoticeNo, "
//                 +        "a.PayMode, "
//                 +        "a.Drawer, "
//                 +        "a.DrawerID, "
//                 +        "a.BankCode, "
//                 +        "a.BankAccNo, "
//                 +        "a.AccName, "
//                 +        "a.ActuGetNo "
//                 +   "from LJAGet a, LPGrpEdorItem b,lpedorapp c "
//                 +  "where 1 = 1 "
//                 +     getWherePart("a.GetNoticeNo", "GetNoticeNo_srh")
//                 +     getWherePart("c.EdorAcceptNo", "EdorAcceptNo_srh")
//                 +     getWherePart("a.Drawer", "Drawer_srh","like")
//                 +     getWherePart("a.DrawerId", "DrawerIDNo_srh","like")
//                 +     getWherePart("b.ManageCom", "CurrentManageCom", "like")
//                 +    "and a.OtherNoType = '10' "
//                 +    "and c.EDORCONFNO = a.OtherNo "
//                 //modidfy by jiaqiangli 2009-02-22 ljaget.otherno=lpedorapp.edorconfno Ϊ�鵵��
//                 +    "and b.EdorAcceptNo = c.EdorAcceptNo "
//                 +  "order by b.EdorAcceptNo asc, b.EdorValiDate asc";
    //ִ�� SQL ��ѯ
    try
    {
        turnPageEdorInfoGrid.pageDivName = "divTurnPageEdorInfoGrid";
        turnPageEdorInfoGrid.queryModal(QuerySQL, EdorInfoGrid);
    }
    catch (ex)
    {
        alert("���棺��ѯ��ȫ������Ϣ�����쳣�� ");
        return;
    }
    //�趨�����ʽ
    if (EdorInfoGrid.mulLineCount > 0)
    {
        try
        {
            document.getElementsByName("ChangeType")[0].value = "2";
            //document.getElementsByName("ChangeTypeName")[0].value = "����";
            //document.getElementsByName("ChangeType")[0].ondblclick = "";
            //document.getElementsByName("ChangeType")[0].onkeyup = "";
            //document.getElementsByName("ChangeType")[0].readOnly = true;
        }
        catch (ex) {}
    }
}






/*============================================================================*/

/**
 * ��������ʻ���Ϣ
 */
function isHaveBankInfo()
{
    var sFormType, sBankCode, sBankAccNo, sAccName;
    try
    {
        sFormType = document.getElementsByName("FormType")[0].value;
        sBankCode = document.getElementsByName("BankCode")[0].value;
        sBankAccNo = document.getElementsByName("BankAccNo")[0].value;
        sAccName = document.getElementsByName("AccName")[0].value;
    }
    catch (ex) {}
    if (sFormType == "4" || sFormType == "7")    //���л��������֧��
    {
        if (sBankCode == null || trim(sBankCode)== "" || sBankAccNo == null || trim(sBankAccNo)== "" || sAccName == null || trim(sAccName)== "")
        {
            alert("���л��������֧������¼�������������ʻ���Ϣ�� ");
            return false;
        }
    }
    return true;
}

/*============================================================================*/

/**
 * ����Ƿ��Ѿ���ѯ��
 */
function isHaveQueried()
{
    var sEdorAcceptNo, sGetNoticeNo;
    try
    {
        sEdorAcceptNo = document.getElementsByName("EdorAcceptNo")[0].value;
        sGetNoticeNo = document.getElementsByName("GetNoticeNo")[0].value;
    }
    catch (ex) {}
    if ((sEdorAcceptNo == null || trim(sEdorAcceptNo) == "") && (sGetNoticeNo == null || trim(sGetNoticeNo) == ""))
    {
        alert("�������뱣ȫ����Ż���ȡ֪ͨ��Ž��в�ѯ�� ");
        return false;
    }
    if (sEdorAcceptNo == null || trim(sEdorAcceptNo) == "" || sGetNoticeNo == null || trim(sGetNoticeNo) == "")
    {
        alert("�޷���ȡ��ȫ����š���ȡ֪ͨ��ţ� ");
        return false;
    }
    return true;
}

/*============================================================================*/

/**
 * �����ո��ѷ�ʽ������
 */
function savePayGetForm()
{
		//alert(fm.GetNoticeNo.value);
    if (!isHaveQueried())     return;
    if (!verifyInput2())      return;
    if (!isHaveBankInfo())    return;
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
    document.forms[0].action = "EdorPayGetFormChangeSave.jsp";
    document.forms[0].target = "fraSubmit";
    document.forms[0].submit();
}

/*============================================================================*/




function ShowSubAccInfo()
{
		var tSelNo = EdorInfoGrid.getSelNo()-1;
		fm.EdorAcceptNo.value = EdorInfoGrid.getRowColData(tSelNo, 1);
		fm.GetNoticeNo.value = EdorInfoGrid.getRowColData(tSelNo, 9);
		fm.FormType.value = EdorInfoGrid.getRowColData(tSelNo, 10);
		showOneCodeName('EdorGetPayForm', 'FormType', 'FormTypeName');
		fm.PayGetName.value = EdorInfoGrid.getRowColData(tSelNo, 11);
		fm.PersonID.value = EdorInfoGrid.getRowColData(tSelNo, 12);
		fm.BankCode.value = EdorInfoGrid.getRowColData(tSelNo, 13);
		showOneCodeName('Bank', 'BankCode', 'BankCodeName');
		fm.BankAccNo.value = EdorInfoGrid.getRowColData(tSelNo, 14);
		fm.AccName.value = EdorInfoGrid.getRowColData(tSelNo, 15);
		fm.ActuGetNo.value = EdorInfoGrid.getRowColData(tSelNo, 16);
		if (fm.FormType.value == "4"){    //���л��������֧��
        enableBankInfo(true);
    }else{
        enableBankInfo(false);
    }

}

/*============================================================================*/


//<!-- JavaScript Document END -->
