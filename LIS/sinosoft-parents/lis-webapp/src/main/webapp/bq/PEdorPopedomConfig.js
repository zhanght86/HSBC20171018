//�������ƣ�PEdorPopedomConfig.js
//�����ܣ���ȫȨ������
//�������ڣ�2006-01-09 15:13:22
//������  ��liurx
//���¼�¼��  ������    ��������     ����ԭ��/����
//
var showInfo;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var mySql=new SqlClass();
/**
 * �ύȨ����������
 * XinYQ rewrote on 2006-12-23
 */

function saveGradePopedom()
{

    if (!verifyInput2())            return;
    
    if (!SelfGrid.checkValue2())    return;
    

    var tManageCom = fm.ManageCom.value;
    var tLimitMoneyI = fm.LimitGetMoneyI.value;
    var tLimitMoneyY = fm.LimitGetMoneyY.value;
    var tLimitMoneyL = fm.LimitGetMoneyL.value;
    var tLimitMoneyM = fm.LimitGetMoneyM.value;
    var tModifyAppDateNum = fm.ModifyAppDateNum.value;

    //if(tManageCom == null || tManageCom == "")
    //{
    //  alert("��ѡ����������");
    //  return;
    //}
    
    if(tLimitMoneyI != null && trim(tLimitMoneyI) != "" && (isNaN(parseFloat(tLimitMoneyI)) || !isNumeric(tLimitMoneyI)))
    {
        alert("���˸��ѽ�����޲�����Ч���֣�");
        fm.LimitMoneyI.focus();
        return;
    }
    if(tLimitMoneyY != null && trim(tLimitMoneyY) != "" && (isNaN(parseFloat(tLimitMoneyY)) || !isNumeric(tLimitMoneyY)))
    {
        alert("�����ѽ�����޲�����Ч���֣�");
        fm.LimitMoneyY.focus();
        return;
    }
    if(tLimitMoneyL != null && trim(tLimitMoneyL) != "" && (isNaN(parseFloat(tLimitMoneyL)) || !isNumeric(tLimitMoneyL)))
    {
        alert("�����ѽ�����޲�����Ч���֣�");
        fm.LimitMoneyY.focus();
        return;
    }
    if(tLimitMoneyM != null && trim(tLimitMoneyM) != "" && (isNaN(parseFloat(tLimitMoneyM)) || !isNumeric(tLimitMoneyM)))
    {
        alert("�����ѽ�����޲�����Ч���֣�");
        fm.LimitMoneyY.focus();
        return;
    }
    if(tModifyAppDateNum != null && trim(tModifyAppDateNum) != "" && (!isNumeric(tModifyAppDateNum) || !isInteger(tModifyAppDateNum)))
    {
        alert("�޸Ĺ�����������Ȩ�޲�����Ч������");
        fm.ModifyAppDateNum.focus();
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
    document.all("fm").action = "PEdorPopedomConfigSave.jsp";
    document.all("fm").submit();
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
            //nothing
        }
        catch (ex) {}
    }
}

function queryEdorPopedom()
{
    var tGrade = fm.EdorPopedom.value;
    var tManageCom = fm.ManageCom.value;
    var brr;

	  mySql=new SqlClass();
	  
 mySql.setResourceName("bq.PEdorPopedomConfig");
 mySql.setSqlId("PEdorPopedomConfigSql1");
 mySql.addSubPara(tManageCom);
 mySql.addSubPara(tGrade);
 mySql.addSubPara(tManageCom);
 mySql.addSubPara(tGrade);
 mySql.addSubPara(tManageCom);
 mySql.addSubPara(tGrade);

    brr = easyExecSql(mySql.getString());
    if (brr)
    {
        displayMultiline(brr, SelfGrid);
    }
}

function queryGEdorPopedom()
{
    var tGrade = fm.GEdorPopedom.value;
    var tManageCom = fm.ManageCom.value;
    var brr;
    mySql=new SqlClass();
    mySql.setResourceName("bq.PEdorPopedomConfig");
    mySql.setSqlId("PEdorPopedomConfigSql2");
    mySql.addSubPara(tManageCom);
    mySql.addSubPara(tGrade);
    mySql.addSubPara(tManageCom);
    mySql.addSubPara(tGrade);
    mySql.addSubPara(tManageCom);
    mySql.addSubPara(tGrade);

    brr = easyExecSql(mySql.getString());
    if (brr)
    {
        displayMultiline(brr, SelfGrid);
    }
}

/**
 * showCodeList �Ļص�����, Ȩ���������ʾ������
 * XinYQ modified on 2006-12-23
 */
function afterCodeSelect(sCodeListType, oCodeListField)
{
    sCodeListType = sCodeListType.toLowerCase();
    if (sCodeListType == "edorpopedomtype")
    {
        if (oCodeListField.value == "1")
        {
            try
            {
                document.all("divEdorPopedomTitle").style.display = "block";
                document.all("divEdorPopedomInput").style.display = "block";
                document.all("divGEdorPopedomTitle").style.display = "none";
                document.all("divGEdorPopedomInput").style.display = "none";
                document.all("divEdorPopedomMoney").style.display = "none";
                document.all("divGEdorPopedomMoney").style.display = "none";
            }
            catch (ex) {}
        }
        else if (oCodeListField.value == "2")
        {
            try
            {
                document.all("divEdorPopedomTitle").style.display = "none";
                document.all("divEdorPopedomInput").style.display = "none";
                document.all("divGEdorPopedomTitle").style.display = "block";
                document.all("divGEdorPopedomInput").style.display = "block";
                document.all("divEdorPopedomMoney").style.display = "none";
                document.all("divGEdorPopedomMoney").style.display = "none";
            }
            catch (ex) {}
        }
        
        //���Ȩ������
        fm.LimitGetMoneyI.value = "";
        fm.LimitGetMoneyY.value = "";
        fm.LimitGetMoneyL.value = "";
        fm.LimitGetMoneyM.value = "";
        fm.ModifyAppDateNum.value = "";
        
        SelfGrid.clearData();
    }
    else if (sCodeListType == "edorpopedom")
    {
        queryEdorPopedom();
    }
    else if (sCodeListType == "gedorpopedom")
    {
        queryGEdorPopedom();
    }
}
