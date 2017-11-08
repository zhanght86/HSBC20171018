var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();

//function LLInqConclusionGridQuery()
//{
//	//根据“调查机构《missionprop5》==当前登录机构”查询
//	/*var strSQL = "select missionprop1,missionprop2,missionprop3,missionprop4,missionprop5,missionid,submissionid,activityid,"
//	       + " (select max(customerno) from llsubreport where trim(subrptno) = missionprop1),"//出险人代码
//	       + " (select name from ldperson where customerno in (select max(customerno) from llsubreport where trim(subrptno)= missionprop1)),"//出险人名字
//	       + " (select accepteddate from llregister where rgtno=lwmission.missionprop1) AcceptedDate from lwmission where 1=1 " 	
//           + " and activityid in ('0000005165','0000009165') "
//           + " and processid in ('0000000005','0000000009')"
////         + " and missionprop5='"+document.all('ComCode').value+"'"
//           + " and defaultoperator='"+fm.Operator.value+"'"
//           + " order by AcceptedDate,missionprop1";*/
//    
//	mySql = new SqlClass();
//	mySql.setResourceName("claim.LLInqConclusionMissInputSql");
//	mySql.setSqlId("LLInqConclusionMissSql1");
//	mySql.addSubPara(fm.Operator.value ); 
//
//    turnPage.pageLineNum=10;    //每页10条
//    turnPage.queryModal(mySql.getString(),LLInqConclusionGrid);//prompt("",strSQL);
//    HighlightByRow();
//}
//
////若该案件自受理日期至操作当日已达到20日或超过20日的，则该案件的显示条目变为红色
//function HighlightByRow(){
//    for(var i=0; i<LLInqConclusionGrid.mulLineCount; i++){
//    	var accepteddate = LLInqConclusionGrid.getRowColData(i,11); //受理日期
//    	if(accepteddate != null && accepteddate != "")
//    	{
//    	   if(dateDiff(accepteddate,fm.CurDate.value,"D")>=20)		//受理日期超过20天
//    	   {
//    		   LLInqConclusionGrid.setRowClass(i,'warn'); 
//    	   }
//    	}
//    }  
//}

//列表响应事件
//function LLInqConclusionGridClick()
//{
//    HighlightByRow();
//    var i = LLInqConclusionGrid.getSelNo();
//    if (i != '0')
//    {
//        i = i - 1;
//        var tClmNo = LLInqConclusionGrid.getRowColData(i,1);
//        var tConNo = LLInqConclusionGrid.getRowColData(i,2); 
//        var tBatNo = LLInqConclusionGrid.getRowColData(i,3); 
//        var tInqDept = LLInqConclusionGrid.getRowColData(i,5);       
//        var tMissionID = LLInqConclusionGrid.getRowColData(i,6);
//        var tSubMissionID = LLInqConclusionGrid.getRowColData(i,7);
//        var tActivityid = LLInqConclusionGrid.getRowColData(i,8); 
//        var tCustomerNo = LLInqConclusionGrid.getRowColData(i,9);
//        var tCustomerName = LLInqConclusionGrid.getRowColData(i,10);      
//    }
//    var strUrl= "LLInqConclusionInput.jsp?ClmNo="+tClmNo+"&ConNo="+tConNo+"&Activityid="+tActivityid+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID;    
//    //location.href="LLInqConclusionInput.jsp?";
//    location.href=strUrl;
//}

// modify by lzf
function LLInqConclusionGridClick()
{
    var i = PrivateWorkPoolGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        var tClmNo = PrivateWorkPoolGrid.getRowColData(i,1);
        var tConNo = PrivateWorkPoolGrid.getRowColData(i,2); 
        var tBatNo = PrivateWorkPoolGrid.getRowColData(i,3); 
        var tInqDept = PrivateWorkPoolGrid.getRowColData(i,5);       
        var tMissionID = PrivateWorkPoolGrid.getRowColData(i,12);
        var tSubMissionID = PrivateWorkPoolGrid.getRowColData(i,13);
        var tActivityid = PrivateWorkPoolGrid.getRowColData(i,15); 
        var tCustomerNo = PrivateWorkPoolGrid.getRowColData(i,6);
        var tCustomerName = PrivateWorkPoolGrid.getRowColData(i,7);      
    }
    var strUrl= "LLInqConclusionInput.jsp?ClmNo="+tClmNo+"&ConNo="+tConNo+"&Activityid="+tActivityid+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID;    
    //location.href="LLInqConclusionInput.jsp?";
    location.href=strUrl;
}
