//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var arrDataSet;
var turnPage = new turnPageClass();
var turnPageAll = new turnPageClass();
// ��ѯ��ť
function easyQueryClickAll()
{
	// ��ʼ�����
	initAllNoticeGrid();
	//if((fm.EdorAcceptNo.value == null || fm.EdorAcceptNo.value == "")&& (fm.ContNo.value == null || fm.ContNo.value == "")){
	//	alert("��ѯ��������Ҫ¼�뱣ȫ����Ż���˱����ţ�");
	//	return;
	//}
	// ��дSQL���
	//if((fm.StartDate.value == null || trim(fm.StartDate.value) == "")||(fm.EndEate.value == null || trim(fm.EndEate.value) == "")){
	//	 	alert("��¼���·����ڵ�ͳ������!");
	//	 	return;
	//}
	//
	//var tManageCom = fm.ManageCom.value;
	//if(tManageCom.length<4){
	//	alert("���Էֹ�˾���ֹ�˾���µĻ������в�ѯ��");
	//	return;	
	//}
	
	var strSQL = "";
	var strLike = "like";
	var strEqual="=";
//	var sEdorPart = getWherePart('b.EdorAcceptNo','EdorAcceptNo',strEqual);
	
  		
	//��ѯȫ��
//	strSQL = " select EdorAcceptNo,ContNo,InsuredNo,NoticeType,NoticeTypeName,CreateOperator,ManageCom,MakeDate,AskContent,ReplyContent from ( "
//				 + " select b.EdorAcceptNo EdorAcceptNo,c.ContNo ContNo,c.InsuredNo InsuredNo,'0' NoticeType,'��ȫ���������' NoticeTypeName, a.StandbyFlag2 CreateOperator,b.ManageCom ManageCom, "
//         + " a.MakeDate MakeDate,a.StandbyFlag5 AskContent,a.StandbyFlag6 ReplyContent from LOPrtManager a, LPEdorItem b, LCCont c where a.Code = 'BQ38' "
//         + " and a.OtherNo = c.ContNo and a.standbyflag1 = b.EdorAcceptNo and b.ContNo = c.ContNo and b.Managecom like '"+manageCom+"%'"
//				 + sEdorPart 
//				 + " union "
//				 + " select b.EdorAcceptNo EdorAcceptNo,c.ContNo ContNo,c.InsuredNo InsuredNo,(case a.Code when 'BQ33' then '4' when 'BQ37' then '5' end) NoticeType,(case a.Code when 'BQ33' then '��ȫ�ܾ�֪ͨ��' when 'BQ37' then '��ȫ���֪ͨ��' else '' end) NoticeTypeName, "
//				 + " a.StandbyFlag3 CreateOperator,b.ManageCom ManageCom, a.MakeDate MakeDate,a.StandbyFlag2 AskContent,'' ReplyContent from LOPrtmanager a,LPEdorItem b,LCCont c where a.standbyflag1 = b.EdorAcceptNo "
//     	   + " and a.OtherNo = b.ContNo and b.ContNo = c.ContNo and a.Code in ('BQ37','BQ33') and b.ManageCom like '"+manageCom+"%'"
//				 + sEdorPart 
//				 + " union "
//				 + " select b.EdorAcceptNo EdorAcceptNo,c.ContNo ContNo,c.InsuredNo InsuredNo,'1' NoticeType,'�˹��˱������' NoticeTypeName,a.Operator CreateOperator,b.ManageCom ManageCom, "
//         + " a.MakeDate MakeDate,a.Issuecont AskContent,a.ReplyResult ReplyContent "
//         + " from LPIssuePol a,LPEdorItem b,LCCont c where a.EdorNo = b.EdorNo and a.ContNo = b.ContNo and   "
//         + " c.ContNo = b.ContNo and b.Managecom like '"+manageCom+"%' "
//         + sEdorPart +  ") aa where 1 = 1 ";
	//if(fm.StartDate.value != null && fm.StartDate.value != ""){
	//	 	strSQL+= " and MakeDate >= '"+fm.StartDate.value+"'";
	//}
	//
	//if(fm.EndEate.value != null && fm.EndEate.value != ""){
	//	 	strSQL+= " and MakeDate <= '"+fm.EndEate.value+"'";
	//}				 
//	strSQL+= " order by MakeDate "; 
	
	var sqlid1="EdorNoticeQuerySql1";
 	var mySql1=new SqlClass();
 	mySql1.setResourceName("bq.EdorNoticeQuerySql");
 	mySql1.setSqlId(sqlid1); //ָ��ʹ��SQL��id
 	mySql1.addSubPara(manageCom);//ָ���������
 	mySql1.addSubPara(window.document.getElementsByName(trim("EdorAcceptNo"))[0].value);
 	mySql1.addSubPara(manageCom);
 	mySql1.addSubPara(window.document.getElementsByName(trim("EdorAcceptNo"))[0].value);
 	mySql1.addSubPara(manageCom);
 	mySql1.addSubPara(window.document.getElementsByName(trim("EdorAcceptNo"))[0].value);
 	strSQL = mySql1.getString();
	
    
	turnPageAll.queryModal(strSQL, AllNoticeGrid); 
	var rowNum=AllNoticeGrid.mulLineCount;
	if(rowNum < 1)
	{
		alert("û�в鵽��ص��������¼!");
	}

}

function returnParent()
{
    
	top.close();

  
}

function ShowInfo()
{
    var i = AllNoticeGrid.getSelNo();

    if (i != '0')
    {
        i = i - 1;
        
        var tAskInfo = AllNoticeGrid.getRowColData(i,9); 

        fm.AskInfo.value=tAskInfo;
        var tReplyInfo = AllNoticeGrid.getRowColData(i,10);
         fm.ReplyInfo.value=tReplyInfo;
    }	
}






