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

//��ʼ��"��ȡ�����������"���
//function easyQueryClick()
//{
//  /*  var strSQL = "";
//    //missionprop7Ϊ��ָ���ĵ����ˡ���������ȡ���У����ڻ�û�ø�����
//    //���ܴ����������·�����������DefaultOperator����ѯ׼ȷ��Modify by zhaorx 2006-03-11
//	strSQL = "select missionprop1,missionprop2,missionprop3,missionprop4,missionprop5,missionprop6,"
//	       + "(select username from lduser where usercode = DefaultOperator),missionid,submissionid,activityid,"
//	       +" (select accepteddate from llregister where rgtno=lwmission.missionprop1) AcceptedDate from lwmission where 1=1 " 	
//           + " and activityid in ('0000005145','0000009145') "
//           + " and processid in ('0000000005','0000000009')"
////           + " and missionprop7 = '" + fm.Operator.value+"' "
//           + " and DefaultOperator = '" + fm.Operator.value + "'" 
//           + " order by AcceptedDate,missionprop1";*/
//    mySql = new SqlClass();
//	mySql.setResourceName("claim.LLInqCourseMissInputeSql");
//	mySql.setSqlId("LLInqCourseMissSql1");
//	mySql.addSubPara(fm.Operator.value ); 
//    turnPage.pageLineNum=5;    //ÿҳ5��
//    turnPage.queryModal(mySql.getString(),LLInqApplyGrid);
//    HighlightByRow();
//}

//���ð������������������������Ѵﵽ20�ջ򳬹�20�յģ���ð�������ʾ��Ŀ��Ϊ��ɫ
//function HighlightByRow(){
//    for(var i=0; i<LLInqApplyGrid.mulLineCount; i++){
//    	var accepteddate = LLInqApplyGrid.getRowColData(i,11); //��������
//    	if(accepteddate != null && accepteddate != "")
//    	{
//    	   if(dateDiff(accepteddate,fm.CurDate.value,"D")>=20)		//�������ڳ���20��
//    	   {
//    		   LLInqApplyGrid.setRowClass(i,'warn'); 
//    	   }
//    	}
//    }  
//}



//LLInqApplyGrid����¼�
//function LLInqApplyGridClick()
//{
//    HighlightByRow();
//    var i = LLInqApplyGrid.getSelNo();
//    if (i != '0')
//    {
//        i = i - 1;
//        var tClmNo = LLInqApplyGrid.getRowColData(i,1);
//        var tInqNo = LLInqApplyGrid.getRowColData(i,2); 
//        var tMissionID = LLInqApplyGrid.getRowColData(i,8);
//        var tSubMissionID = LLInqApplyGrid.getRowColData(i,9);
//        var tActivityid = LLInqApplyGrid.getRowColData(i,10);
//        
//    }
//    var strUrl= "LLInqCourseInpute.jsp?ClmNo="+tClmNo+"&InqNo="+tInqNo+"&Activityid="+tActivityid+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID;    
//    //location.href="LLInqCourseInpute.jsp?";
//    location.href=strUrl;
//}

//modify by lzf 
function LLInqApplyGridClick()
{
    var i = PrivateWorkPoolGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        var tClmNo = PrivateWorkPoolGrid.getRowColData(i,1);
        var tInqNo = PrivateWorkPoolGrid.getRowColData(i,2); 
        var tMissionID = PrivateWorkPoolGrid.getRowColData(i,12);
        var tSubMissionID = PrivateWorkPoolGrid.getRowColData(i,13);
        var tActivityid = PrivateWorkPoolGrid.getRowColData(i,15);
        
    }
    var strUrl= "LLInqCourseInpute.jsp?ClmNo="+tClmNo+"&InqNo="+tInqNo+"&Activityid="+tActivityid+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID;    
    //location.href="LLInqCourseInpute.jsp?";
    location.href=strUrl;
}