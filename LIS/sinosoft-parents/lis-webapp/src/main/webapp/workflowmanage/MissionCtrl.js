/**
 * Created by IntelliJ IDEA.
 * User: jinsh
 * Date: 2009-1-7
 * Time: 15:32:15
 * To change this template use File | Settings | File Templates.
 */

/*������ҳ��*/
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
/*ϵͳ��ǰ������Ա*/
var mOperate = "";
/*����mysql��ѯ��*/
var mySql = new SqlClass();
/*������Ϣ����*/
var showInfo;


/*�ύ����*/
function submitForm()
{
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
    fm.hideOperate.value = mOperate;
    document.getElementById("fm").submit();
}
function afterSubmit(FlagStr, content)
{
    if (typeof(showInfo) == "object")
    {
        showInfo.close();
        if (typeof(showInfo.parent) == "object" && typeof(showInfo.parent) != "unknown")
        {
            showInfo.parent.focus();
            if (typeof(showInfo.parent.parent) == "object" && typeof(showInfo.parent.parent) != "unknown")
            {
                showInfo.parent.parent.blur();
            }
        }
    }
    if (FlagStr == "Fail")
    {
        content = "����ʧ�ܣ�ԭ���ǣ�" + content;
        var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        //showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    }
    else
    {
        content = "�����ɹ���";
        var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
        //showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
        queryClick();
    }
}

function queryClick()
{
//    if (document.all('ActivityIDQ').value == "")
//    {
//        alert("����������!");
//        return false;
//    }
//    if (document.all('BusiTypeQ').value == "")
//    {
//        alert("������ҵ������!");
//        return false;
//    }
	try
    {
        fm.ValiFlagCode.value           = "";
        fm.ValiFlagName.value         = "";
    }
    catch(ex)
    {
    }   
    mySql = new SqlClass();
    mySql.setResourceName("workflow.MissionCtrlSql");
    mySql.setSqlId("MissionCtrlSQL1");
    mySql.addSubPara(document.all('BusiTypeQ').value);   	
	  mySql.addSubPara(document.all('ActivityIDQ').value);      
    mySql.addSubPara(document.all('BusiNoQ').value);   	
	  mySql.addSubPara(document.all('UserCodeQ').value);
	    
    turnPage.queryModal(mySql.getString(), TICollectGrid);
}

function ModifyClick()
{
    if (verifyInput2() == false) return false;
    var selno = TICollectGrid.getSelNo() - 1;
    if (selno < 0)
    {
        alert("û��ѡ������!");
        return false;
    }
    var activityid = TICollectGrid.getRowColData(selno, 3);
    if (activityid == null || "" == activityid)
    {
        alert("û�в�ѯ�����Ϣ!");
        return false;
    }
    var missionid = TICollectGrid.getRowColData(selno, 9);
    if (missionid == null || "" == missionid)
    {
        alert("û�в�ѯ�����Ϣ!");
        return false;
    }
    var submissionid = TICollectGrid.getRowColData(selno, 10);
    if (submissionid == null || "" == submissionid)
    {
        alert("û�в�ѯ�����Ϣ!");
        return false;
    }
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
    fm.action = "MissionCtrlSave.jsp?OperFlag=MODIFY&MissionID="+missionid+"&SubMissionID="+submissionid+"&ActivityID="+activityid+"&UserCode="+document.all('UserCode').value;
    document.getElementById("fm").submit();
}
