//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var mDebug = "0";
var mOperate = "";
var showInfo;
var arrDataSet;
var turnPage = new turnPageClass();
var QueryResult = "";
var QueryCount = 0;
var mulLineCount = 0;
var QueryWhere = "";
//window.onfocus=myonfocus;
var mySql=new SqlClass();
function updateClick()
{
    if (WorkDayGrid.getRowColData(0, 1) == null || WorkDayGrid.getRowColData(0, 1) == "")
    {
        alert("���Ȳ�ѯ!!");
        return;
    }
    SaveWorkDayGrid.clearData();
    var isCheck = "";
    var iCount = 0;
    var rowNum = WorkDayGrid.mulLineCount;    //��ǰ�б��е�����
    var arr = new Array();

    for (var i = 0; i < rowNum; i++)
    {
        isCheck = WorkDayGrid.getChkNo(i); //��Ϊ�Ǹ�ѡ��,������getChkNo()����
        if (isCheck)  //���ֵΪtrue
        {
            iCount = iCount + 1;
            arr[iCount - 1] = new Array();
            arr[iCount - 1][0] = WorkDayGrid.getRowColData(i, 1);     //
            arr[iCount - 1][1] = WorkDayGrid.getRowColData(i, 2);     //
            arr[iCount - 1][2] = WorkDayGrid.getRowColData(i, 3);     //
            arr[iCount - 1][3] = WorkDayGrid.getRowColData(i, 4);     //
            arr[iCount - 1][4] = WorkDayGrid.getRowColData(i, 5);     //
            arr[iCount - 1][5] = WorkDayGrid.getRowColData(i, 6);     //
            arr[iCount - 1][6] = WorkDayGrid.getRowColData(i, 7);     //
            arr[iCount - 1][7] = WorkDayGrid.getRowColData(i, 8);     //
            arr[iCount - 1][8] = WorkDayGrid.getRowColData(i, 9);     //
            arr[iCount - 1][9] = WorkDayGrid.getRowColData(i, 10);     //
            arr[iCount - 1][10] = WorkDayGrid.getRowColData(i, 11); 
            arr[iCount - 1][11] = WorkDayGrid.getRowColData(i, 12); 
        }
    }
    if (iCount == 0)
    {
        alert("��ѡ��Ҫ�������ڣ�");
        return;
    }
    var tSelfCount = SaveWorkDayGrid.mulLineCount;  //��¼SaveWorkDayGrid�����е�����
    var Count = iCount + tSelfCount;
    //��ֵʱ,�Ǵ�SaveWorkDayGrid�����������ĺ�һ�п�ʼ��ֵ��
    for (var i = tSelfCount; i < Count; i++)
    {
        var j = i - tSelfCount;   //Ϊ��ȡarr�����е�ֵ
        SaveWorkDayGrid.addOne();
        //����һ�и�ֵ
        SaveWorkDayGrid.setRowColData(j, 1, arr[j][0]);    //
        SaveWorkDayGrid.setRowColData(j, 2, arr[j][1]);    //
        SaveWorkDayGrid.setRowColData(j, 3, arr[j][2]);    //
        SaveWorkDayGrid.setRowColData(j, 4, arr[j][3]);    //
        SaveWorkDayGrid.setRowColData(j, 5, arr[j][4]);    //
        SaveWorkDayGrid.setRowColData(j, 6, arr[j][5]);    //
        SaveWorkDayGrid.setRowColData(j, 7, arr[j][6]);    //
        SaveWorkDayGrid.setRowColData(j, 8, arr[j][7]);    //
        SaveWorkDayGrid.setRowColData(j, 9, arr[j][8]);    //
        SaveWorkDayGrid.setRowColData(j, 10, arr[j][9]);    //
        SaveWorkDayGrid.setRowColData(j, 11, arr[j][10]); 
        SaveWorkDayGrid.setRowColData(j, 12, arr[j][11]); 
    }
    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
    
    showInfo.focus();
    fm.action = "./WorkDayUpdateSave.jsp";
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
        var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//        showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
        var iWidth=550;      //�������ڵĿ��; 
        var iHeight=250;     //�������ڵĸ߶�; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
        window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    }
    else
    {
        content = "�����ɹ���";
        var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
//        showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
        var iWidth=550;      //�������ڵĿ��; 
        var iHeight=250;     //�������ڵĸ߶�; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
        window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    }
    easyQueryClick();
    SaveWorkDayGrid.clearData();
}
function afterCodeSelect(cCodeName, Field)
{
}
//wangjm -add 20070827
function returnparent()
{
    parent.close();
}
function checkDa(Begin, AmEnd)
{
    if (Begin >= AmEnd)
    {
        return false;
    }
    return true;
}
function submitForm()
{
    var str = "";
    if (!verifyInput2())
    {
        return;
    }
    var check = /^[0-2][0-9]:[0-6][0-9]:[0-6][0-9]/;
    if (!check.test(fm.AmBegin.value))
    {
        
        fm.AmBegin.className = "warn";
        fm.AmBegin.value = "";
		//fm.AmBegin.setAttribute("err", "1");

        alert("���繤����ʼʱ���ʽӦ��ΪHH:MM:SS");
		fm.AmBegin.focus();
        return;
    }
    else
    {
        str = fm.AmBegin.value;
        var a = str.match(/^(\d{1,2})(:)?(\d{1,2})\2(\d{1,2})$/);
        if (a == null)
        {

            alert('����Ĳ�������ʱ���ʽ');
            return false;
        }
        if (a[1] >= 24 || a[3] > 60 || a[4] > 60)
        {
            alert("���繤����ʼʱ���ʽ����");
            return false
        }
    }
    if (!check.test(fm.AmEnd.value))
    {
        fm.AmEnd.focus();
        fm.AmEnd.className = "warn";
        fm.AmEnd.value = "";
        alert("���繤������ʱ���ʽӦ��ΪHH:MM:SS");
        return;
    }
    else
    {
        str = fm.AmEnd.value;
        var a = str.match(/^(\d{1,2})(:)?(\d{1,2})\2(\d{1,2})$/);
        if (a == null)
        {
            alert('����Ĳ�������ʱ���ʽ');
            return false;
        }
        if (a[1] >= 24 || a[3] > 60 || a[4] > 60)
        {
            alert("���繤������ʱ���ʽ����");
            return false
        }
    }
    if (!checkDa(fm.AmBegin.value, fm.AmEnd.value))
    {
        alert("���繤����ʼʱ�䲻�����ڽ���ʱ�䣡");
        return false;
    }
    if (!check.test(fm.PmBegin.value))
    {
        fm.PmBegin.focus();
        fm.PmBegin.className = "warn";
        fm.PmBegin.value = "";
        alert("���繤����ʼʱ���ʽӦ��ΪHH:MM:SS");
        return;
    }
    else
    {
        str = fm.PmBegin.value;
        var a = str.match(/^(\d{1,2})(:)?(\d{1,2})\2(\d{1,2})$/);
        if (a == null)
        {
            alert('����Ĳ�������ʱ���ʽ');
            return false;
        }
        if (a[3] > 60 || a[4] > 60 || a[1] >= 24)
        {
            alert("���繤����ʼʱ���ʽ����");
            return false
        }
    }
    if (!check.test(fm.PmEnd.value))
    {
        fm.PmEnd.focus();
        fm.PmEnd.className = "warn";
        fm.PmEnd.value = "";
        alert("���繤������ʱ���ʽӦ��ΪHH:MM:SS");
        return;
    }
    else
    {
        str = fm.PmEnd.value;
        var a = str.match(/^(\d{1,2})(:)?(\d{1,2})\2(\d{1,2})$/);
        if (a == null)
        {
            alert('����Ĳ�������ʱ���ʽ');
            return false;
        }
        if (a[3] > 60 || a[4] > 60 || a[1] >= 24)
        {
            alert("���繤������ʱ���ʽ����");
            return false
        }
    }
    if (!checkDa(fm.PmBegin.value, fm.PmEnd.value))
    {
        alert("���繤����ʼʱ�䲻�����ڽ���ʱ�䣡");
        return false;
    }
    if(fm.CalendarType==""){
    	alert("����ѡ����������");
    	return false;
    }
    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();
    
    fm.action = "./WorkDayDefSave.jsp";
    document.getElementById("fm").submit();
}
function easyQueryClick()
{
    mySql = new SqlClass();
    mySql.setResourceName("workflow.WorkDayDefSql");
    mySql.setSqlId("WorkDayDefSql1");
    mySql.addSubPara(fm.Year.value);
    mySql.addSubPara(fm.AmBegin.value);
    mySql.addSubPara(fm.AmEnd.value);
    mySql.addSubPara(fm.PmBegin.value);
    mySql.addSubPara(fm.PmEnd.value);
    mySql.addSubPara(fm.Month.value);
    mySql.addSubPara(fm.OtherProp.value);
    mySql.addSubPara(fm.CalendarType.value);
    turnPage.queryModal(mySql.getString(), WorkDayGrid);
}