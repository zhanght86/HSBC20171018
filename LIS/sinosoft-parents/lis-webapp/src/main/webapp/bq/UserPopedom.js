//�������ƣ�UserPopedom.js
//�����ܣ��û�Ȩ������
//�������ڣ�2006-01-09 15:13:22
//������  ��liurx
//���¼�¼��  ������    ��������     ����ԭ��/����
//
var showInfo;
var mySql=new SqlClass();
/**
 * �ύȨ����������
 * XinYQ rewrote on 2006-12-23
 */
function saveUserPopedom()
{
    if (!verifyInput2())      return;
    if (!sureDelPopedom())    return;
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
    document.all('fm').action = "UserPopedomSave.jsp";
    document.all('fm').submit();
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
}

/**
 * ȷ��ɾ����ȫȨ�޲���
 * XinYQ added on 2006-12-25
 */
function sureDelPopedom()
{
    var sEdorPopedom, sGEdorPopedom, sUserCode, sErrorMsg, needConfirmFlag;
    try
    {
        sUserCode = document.getElementsByName("UserCode")[0].value;
        sEdorPopedom = document.getElementsByName("EdorPopedom")[0].value;
        sGEdorPopedom = document.getElementsByName("GEdorPopedom")[0].value;
    }
    catch (ex) {}
    needConfirmFlag = false;
    sErrorMsg = "��û��Ϊ�û� " + sUserCode + " ָ��";
    if ((sEdorPopedom == null || sEdorPopedom.trim() == "") && (sGEdorPopedom != null && sGEdorPopedom.trim() != ""))
    {
        sErrorMsg += "����";
        needConfirmFlag = true;
    }
    else if ((sEdorPopedom != null && sEdorPopedom.trim() != "") && (sGEdorPopedom == null || sGEdorPopedom.trim() == ""))
    {
        sErrorMsg += "����";
        needConfirmFlag = true;
    }
    else if ((sEdorPopedom == null || sEdorPopedom.trim() == "") && (sGEdorPopedom == null || sGEdorPopedom.trim() == ""))
    {
        sErrorMsg += "�κ�";
        needConfirmFlag = true;
    }
    sErrorMsg += "��ȫȨ�޼���\n\n������û�ԭ�����и�Ȩ�ޣ�����������ɾ����ӦȨ�ޡ�\n\nҪ������";
    if (needConfirmFlag && !confirm(sErrorMsg))
    {
        return false;
    }
    return true;
}

//�����û���Ϣ��ѯҳ��
function queryUser()
{
    var newWindow = window.open("../sys/UsrCommonQueryMain.jsp?ManageCom="+manageCom+"&UserCode="+fm.UserCode.value,"UsrCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}


/*********************************************************************
 *  �س�ʵ�ֲ�ѯ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function QueryOnKeyDown()
{
    var keyCode = event.keyCode;

    //�س���ascii����13
    if (keyCode=="13")
    {
    	QueryUserInfo(fm.UserCode.value);
    }
}

function QueryUserInfo(UserCode)
{
	 mySql=new SqlClass();
			//�������
    	fm.UserCode_read.value = "";
    	fm.UserName.value = "";
    	fm.ComName.value = "";
    	fm.EdorPopedom.value = "";
    	fm.GEdorPopedom.value = "";
    	fm.EdorPopedomName.value = "";
    	fm.GEdorPopedomName.value = "";
    		
		fm.UserCode.value = UserCode;
		/*
    var strSQL = "select usercode,username," 
    				 + " a.comcode," 
    				 + " (select edorpopedom from ldedoruser e where e.usercode = a.usercode and e.usertype = '1') edorpopedom, " 
    				 + " (select edorpopedom from ldedoruser e where e.usercode = a.usercode and e.usertype = '2') gedorpopedom"
    				 + " from lduser a where usercode = '" + UserCode + "' ";
   */
 mySql.setResourceName("bq.UserPopedom");
 mySql.setSqlId("UserPopedomSql1");
 mySql.addSubPara(UserCode);

   
    var brr = easyExecSql(mySql.getString());

    if ( brr ) 
    {
        brr[0][0]==null||brr[0][0]=='null'?'0':fm.UserCode_read.value     = brr[0][0];
        brr[0][1]==null||brr[0][1]=='null'?'0':fm.UserName.value = brr[0][1];
        brr[0][2]==null||brr[0][2]=='null'?'0':fm.ComName.value = brr[0][2];
        brr[0][3]==null||brr[0][3]=='null'?'0':fm.EdorPopedom.value     = brr[0][3];
        brr[0][4]==null||brr[0][4]=='null'?'0':fm.GEdorPopedom.value = brr[0][4];
        showOneCodeName('EdorPopedom','EdorPopedom','EdorPopedomName');
        showOneCodeName('GEdorPopedom','GEdorPopedom','GEdorPopedomName');
    }  	
}