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

function afterCodeSelect(cCodeName, Field)
{

}

function showData()
{
    var selno = TICollectGrid.getSelNo() - 1;
    if (selno < 0)
    {
        alert("û��ѡ������!");
        return false;
    }
    var BusiType        = TICollectGrid.getRowColData(selno, 1);
    var TransitionStart = TICollectGrid.getRowColData(selno, 3);
    var TransitionEnd   = TICollectGrid.getRowColData(selno, 5);
    
    mySql = new SqlClass();
    mySql.setResourceName("workflow.ActivityDefSql");
    mySql.setSqlId("ConditionConverSQL1");
    mySql.addSubPara(BusiType);  
    mySql.addSubPara(TransitionStart); 
    mySql.addSubPara(TransitionEnd);     
 
    var arr = new Array();
    arr = easyExecSql( mySql.getString() );
    document.all('TransitionCond').value = arr[0][0];
   // document.all('CondDesc').value = arr[0][1];
    try
    {
        fm.BusiType.value            = TICollectGrid.getRowColData(selno, 1);
        fm.BusiTypeName.value        = TICollectGrid.getRowColData(selno, 2);
        fm.StartActivityID.value     = TICollectGrid.getRowColData(selno, 3);
        fm.StartActivityName.value   = TICollectGrid.getRowColData(selno, 4);
        fm.EndActivityID.value       = TICollectGrid.getRowColData(selno, 5);
        fm.EndActivityName.value     = TICollectGrid.getRowColData(selno, 6);
        fm.TransitionCondT.value     = TICollectGrid.getRowColData(selno, 7);
        fm.TransitionCondTName.value = TICollectGrid.getRowColData(selno, 8);
        //fm.TransitionCond.value      = TICollectGrid.getRowColData(selno, 9);
        fm.CondDesc.value            = TICollectGrid.getRowColData(selno, 10);

    }
    catch(ex)
    {
    }
    return true;
}
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
        fm.BusiType.value            = "";
        fm.BusiTypeName.value        = "";
        fm.StartActivityID.value     = "";
        fm.StartActivityName.value   = "";
        fm.EndActivityID.value       = "";
        fm.EndActivityName.value     = "";
        fm.TransitionCondT.value     = "";
        fm.TransitionCondTName.value = "";
        fm.TransitionCond.value      = "";
        fm.CondDesc.value            = "";

    }
    catch(ex)
    {
    }
    
    mySql = new SqlClass();
    mySql.setResourceName("workflow.ActivityDefSql");
    mySql.setSqlId("ConditionSQL1");
    mySql.addSubPara(document.all('BusiTypeQ').value);  
    mySql.addSubPara(document.all('StartActivityIDQ').value); 
    mySql.addSubPara(document.all('EndActivityIDQ').value);         
 
    turnPage.queryModal(mySql.getString(), TICollectGrid);
}
function SaveClick()
{

    if (verifyInput2() == false) return false;
    mySql = new SqlClass();
    mySql.setResourceName("workflow.ActivityDefSql");
    mySql.setSqlId("ConditionSQL2");
    mySql.addSubPara(document.all('BusiType').value);  
    mySql.addSubPara(document.all('StartActivityID').value); 
    mySql.addSubPara(document.all('EndActivityID').value);     
 
    var arr = easyExecSql(mySql.getString());
    if (arr > 0)
    {
        alert("���ݿ��Ѿ����ڸü�¼,��ѡ���޸Ļ�ɾ������!");
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
    fm.action = "ActivityDefSave.jsp?OperFlag=INSERT||Condition";
     document.getElementById("fm").submit();
}
function ModifyClick()
{
//    if(document.all('BusiType').value==""||document.all('TransitionStart').value==""||document.all('TransitionEnd').value=="")
//    {
//        alert("��¼���¼����б������д˲�������ѡ����������!");
//        return false;
//    }
    if (verifyInput2() == false) return false;
    mySql = new SqlClass();
    mySql.setResourceName("workflow.ActivityDefSql");
    mySql.setSqlId("ConditionSQL2");
    mySql.addSubPara(document.all('BusiType').value);  
    mySql.addSubPara(document.all('StartActivityID').value); 
    mySql.addSubPara(document.all('EndActivityID').value);   
    var arr = easyExecSql(mySql.getString());
    if (arr == 0)
    {
        alert("���ݿⲻ���ڸü�¼,��ѡ�񱣴����!");
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
    fm.action = "ActivityDefSave.jsp?OperFlag=MODIFY||Condition";
     document.getElementById("fm").submit();
}
function DeleteClick()
{
//    if(document.all('BusiType').value==""||document.all('TransitionStart').value==""||document.all('TransitionEnd').value=="")
//    {
//        alert("��¼���¼����б������д˲�������ѡ����������!");
//        return false;
//    }
    if (verifyInput2() == false) return false;
    mySql = new SqlClass();
    mySql.setResourceName("workflow.ActivityDefSql");
    mySql.setSqlId("ConditionSQL2");
    mySql.addSubPara(document.all('BusiType').value);  
    mySql.addSubPara(document.all('StartActivityID').value); 
    mySql.addSubPara(document.all('EndActivityID').value);   
    var arr = easyExecSql(mySql.getString());
    if (arr == 0)
    {
        alert("���ݿⲻ���ڸü�¼,�޷�����ɾ������!");
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
    fm.action = "ActivityDefSave.jsp?OperFlag=DELETE||Condition";
     document.getElementById("fm").submit();
}
function DefParamClick()
{
    window.open("")
}
function DefConditionClick()
{
    window.open("");
}
function testQuery()
{
    mySql = new SqlClass();
    mySql.setJspName("../../workflowmanage/ActivityDefSql.jsp");
    mySql.setSqlId("TestSQL1");
    var arr = easyExecSql( mySql.getString() );
    document.all('TransitionCond').value = arr;
}