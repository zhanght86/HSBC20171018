//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var mDebug="1";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();  //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var mySql = new SqlClass();
//�ύ��ɺ���������
function afterSubmit( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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

////��ʼ��"���ճʱ�����"���
//function easyQueryClick()
//{
//   /* var strSQL = "";
//    //strSQL = "select missionprop1,missionprop2,missionprop3,missionprop4,missionprop5,missionprop6,missionprop7,missionid,submissionid,activityid from Lwmission where activityid = '0000005105' and processid = '0000000005' and missionprop6 = '1'";
//	strSQL = "select missionprop1,missionprop2,missionprop3,missionprop4,missionprop5,missionprop6,missionprop7,missionid,submissionid,activityid from lwmission where 1=1 "
//           + " and (activityid = '0000005105' or activityid = '0000009105')"
//           + " and (processid = '0000000005' or processid = '0000000009')"
//           + " and missionprop6 = '1'"
//           + " order by missionprop1";*/
//    
//	mySql = new SqlClass();
//	mySql.setResourceName("claim.LLSubmitApplyHeadDealMissInputSql");
//	mySql.setSqlId("LLSubmitApplyHeadDealMissSql1");
//	mySql.addSubPara("1");
//	
//    turnPage.pageLineNum=5;    //ÿҳ5��
//    turnPage.queryModal(mySql.getString(),LLSubmitApplyGrid);
//}


//LLSubmitApplyGrid����¼�
//function LLSubmitApplyGridClick()
//{
//    var i = LLSubmitApplyGrid.getSelNo();
//    if (i != '0')
//    {
//        i = i - 1;
//        var tClmNo = LLSubmitApplyGrid.getRowColData(i,1);
//        var tSubNo = LLSubmitApplyGrid.getRowColData(i,2); 
//        var tSubCount=LLSubmitApplyGrid.getRowColData(i,3);  
//        var tMissionID = LLSubmitApplyGrid.getRowColData(i,8);
//        var tSubMissionID = LLSubmitApplyGrid.getRowColData(i,9);
//    }
//    var strUrl= "LLSubmitApplyHeadDealInput.jsp?ClmNo="+tClmNo+"&SubNo="+tSubNo+"&SubCount="+tSubCount+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID;    
//    //location.href="LLSubmitApplyDealInput.jsp?";
//    location.href=strUrl;
//}
function LLSubmitApplyGridClick()
{
    var i = PrivateWorkPoolGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        var tClmNo = PrivateWorkPoolGrid.getRowColData(i,1);
        var tSubNo = PrivateWorkPoolGrid.getRowColData(i,2); 
        var tSubCount=PrivateWorkPoolGrid.getRowColData(i,3);  
        var tMissionID = PrivateWorkPoolGrid.getRowColData(i,10);
        var tSubMissionID = PrivateWorkPoolGrid.getRowColData(i,11);
        var tActivityID = PrivateWorkPoolGrid.getRowColData(i,13);
    }
    var strUrl= "LLSubmitApplyHeadDealInput.jsp?ClmNo="+tClmNo+"&SubNo="+tSubNo+"&SubCount="+tSubCount+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;    
    //location.href="LLSubmitApplyDealInput.jsp?";
    location.href=strUrl;
}
