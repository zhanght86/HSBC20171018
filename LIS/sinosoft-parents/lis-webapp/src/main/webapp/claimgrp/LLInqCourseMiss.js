//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var mDebug="1";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();  //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���

//�ύ��ɺ���������
function afterSubmit( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
        var iWidth=550;      //�������ڵĿ��; 
        var iHeight=350;     //�������ڵĸ߶�; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

    }
    else
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
        var iWidth=550;      //�������ڵĿ��; 
        var iHeight=350;     //�������ڵĸ߶�; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

    }
    tSaveFlag ="0";
}

//��ʼ��"��ȡ�����������"���
function easyQueryClick()
{
	var strSQL = "";
    //missionprop7Ϊ��ָ���ĵ����ˡ���������ȡ���У����ڻ�û�ø�����
    //���ܴ����������·�����������DefaultOperator����ѯ׼ȷ��Modify by zhaorx 2006-03-11
	strSQL = "select missionprop1,missionprop2,missionprop3,missionprop4,missionprop5,missionprop6,"
	       + "(select username from lduser where usercode = DefaultOperator),missionid,submissionid,activityid from lwmission where 1=1 "
           + " and activityid = '0000009145' "
           + " and processid = '0000000009'"
//           + " and missionprop7 = '" + fm.Operator.value+"' "
           + " and DefaultOperator = '" + fm.Operator.value + "'" 
           + " order by missionprop1";
    turnPage.pageLineNum=5;    //ÿҳ5��
    turnPage.queryModal(strSQL,LLInqApplyGrid);
}


//LLInqApplyGrid����¼�
function LLInqApplyGridClick()
{
    var i = LLInqApplyGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        var tClmNo = LLInqApplyGrid.getRowColData(i,1);
        var tInqNo = LLInqApplyGrid.getRowColData(i,2); 
        var tMissionID = LLInqApplyGrid.getRowColData(i,8);
        var tSubMissionID = LLInqApplyGrid.getRowColData(i,9);
        var tActivityid = LLInqApplyGrid.getRowColData(i,10);
        
    }
    var strUrl= "LLInqCourseInpute.jsp?ClmNo="+tClmNo+"&InqNo="+tInqNo+"&Activityid="+tActivityid+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID;    
    location.href=strUrl;
}
