var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();

function LLInqConclusionGridQuery()
{
	//���ݡ����������missionprop5��==��ǰ��¼��������ѯ
	var strSQL = "select missionprop1,missionprop2,missionprop3,missionprop4,missionprop5,missionid,submissionid,activityid from lwmission where 1=1 "
           + " and activityid = '0000009165' "
           + " and processid = '0000000009'"
           + " and missionprop5 like '"+document.all('ComCode').value+"%%'"
           + " order by missionprop1";
    turnPage.pageLineNum=10;    //ÿҳ10��
    turnPage.queryModal(strSQL,LLInqConclusionGrid);
}
//�б���Ӧ�¼�
function LLInqConclusionGridClick()
{

    var i = LLInqConclusionGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        var tClmNo = LLInqConclusionGrid.getRowColData(i,1);
        var tConNo = LLInqConclusionGrid.getRowColData(i,2); 
        var tBatNo = LLInqConclusionGrid.getRowColData(i,3); 
        var tInqDept = LLInqConclusionGrid.getRowColData(i,5);       
        var tMissionID = LLInqConclusionGrid.getRowColData(i,6);
        var tSubMissionID = LLInqConclusionGrid.getRowColData(i,7);
        var tActivityid = LLInqConclusionGrid.getRowColData(i,8);       
    }
    var strUrl= "LLInqConclusionInput.jsp?ClmNo="+tClmNo+"&ConNo="+tConNo+"&Activityid="+tActivityid+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID;    
    location.href=strUrl;
}
