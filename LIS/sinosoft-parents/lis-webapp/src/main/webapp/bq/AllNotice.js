//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var arrDataSet;
var turnPage = new turnPageClass();
var turnPageSelf = new turnPageClass();
var turnPageAll = new turnPageClass();




//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
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
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    //ִ����һ������
  }
  easyQueryClickSelf();
  easyQueryClickAll();
}




function replyApproveAsk()
{
		var tMyReply = fm.MyReply.value;
		if(tMyReply==null ||trim(tMyReply) =="")
		{
			alert("��¼��ظ�����!");
			return;
		}
		if(fm.PrtSeq.value == null ||fm.PrtSeq.value == ""){
			alert("֪ͨ������󣬻ظ�ʧ�ܣ�");
			return;	
		}
		var MsgContent = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=C&content=" + MsgContent;
    //showInfo = showModelessDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=300;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action = "EdorApproveNoticeSave.jsp?AskOperate=REPLY";
    fm.target = "fraSubmit";
    document.getElementById("fm").submit();
}


// ��ѯ��ť
function easyQueryClickAll()
{
	// ��ʼ�����
	initAllNoticeGrid();
	
	// ��дSQL���
	var strSQL = "";
	var strLike = "like";
	var strEqual="=";
	//��ѯȫ��
//	strSQL = " select EdorAcceptNo,ContNo,InsuredNo,NoticeType,NoticeTypeName,CreateOperator,ManageCom,MakeDate,MissionId,SubMissionId,ActivityId,EdorAppDate,OverTime from ( "
//				 + " select b.EdorAcceptNo EdorAcceptNo,c.ContNo ContNo,c.InsuredNo InsuredNo,'1' NoticeType,'�˹��˱������' NoticeTypeName,a.CreateOperator CreateOperator,b.ManageCom ManageCom, "
//         + " a.MakeDate MakeDate,a.MissionId MissionId,a.SubMissionId SubMissionId,a.ActivityId ActivityId,b.EdorAppDate EdorAppDate,(select count(*) from LDCalendar where CommonDate > b.EdorAppdate and commondate <= '"+curDay+"' and workdateflag = 'Y') OverTime "
//         + " from LWMission a,LPEdorItem b,LCCont c where a.MissionProp8 = b.EdorNo and a.MissionProp2 = b.ContNo and a.ActivityId in(select activityid from lwactivity where functionid='10020332')  and a.DefaultOperator is null "
//         + " and c.ContNo = b.ContNo and exists (select 1 from LPIssuePol where EdorNo = a.MissionProp8) and b.Managecom like '"+manageCom+"%') l where 1 = 1 "
//				 + getWherePart('EdorAcceptNo','EdorAcceptNo_all',strEqual)
//	  		     + getWherePart('ContNo','ContNo_all',strEqual)
//				 + getWherePart('InsuredNo','InsuredNo_all',strEqual)
//				 + getWherePart('NoticeType','AllNoticeType',strEqual)
//				 + getWherePart('CreateOperator','CreateOperator_all',strEqual)
//				 + getWherePart('ManageCom','ManageCom_all',strLike);
	
	var  EdorAcceptNo_all1 = window.document.getElementsByName(trim("EdorAcceptNo_all"))[0].value;
	var  ContNo_all1 = window.document.getElementsByName(trim("ContNo_all"))[0].value;
	var  InsuredNo_all1 = window.document.getElementsByName(trim("InsuredNo_all"))[0].value;
	var  AllNoticeType1 = window.document.getElementsByName(trim("AllNoticeType"))[0].value;
	var  CreateOperator_all1 = window.document.getElementsByName(trim("CreateOperator_all"))[0].value;
	var  ManageCom_all1 = window.document.getElementsByName(trim("ManageCom_all"))[0].value;
	var  StartDate_all1 = window.document.getElementsByName(trim("StartDate_all"))[0].value;
	var  EndEate_all1= window.document.getElementsByName(trim("EndEate_all"))[0].value;
	
	var sqlid1="AllNoticeSql1";
	var mySql1=new SqlClass();
    mySql1.setResourceName("bq.AllNoticeSql");
	mySql1.setSqlId(sqlid1);//ָ��ʹ��SQL��id
	mySql1.addSubPara(curDay);//ָ���������
	mySql1.addSubPara(manageCom);//ָ���������
	mySql1.addSubPara(EdorAcceptNo_all1);//ָ���������
	mySql1.addSubPara(ContNo_all1);//ָ���������
	mySql1.addSubPara(InsuredNo_all1);//ָ���������
	mySql1.addSubPara(AllNoticeType1);//ָ���������
	mySql1.addSubPara(CreateOperator_all1);//ָ���������
	mySql1.addSubPara(ManageCom_all1);//ָ���������
	mySql1.addSubPara(StartDate_all1);//ָ���������
	mySql1.addSubPara(EndEate_all1);//ָ���������
	strSQL = mySql1.getString();
	
//	
//	if(fm.StartDate_all.value != null && fm.StartDate_all.value != ""){
//		 	strSQL+= " and MakeDate >= '"+fm.StartDate_all.value+"'";
//	}
//	
//	if(fm.EndEate_all.value != null && fm.EndEate_all.value != ""){
//		 	strSQL+= " and MakeDate <= '"+fm.EndEate_all.value+"'";
//	}				 
//	strSQL+= " order by EdorAppDate ";   
	
	turnPageAll.queryModal(strSQL, AllNoticeGrid); 
	HighlightAllRow();
}

function ApplyMission()
{

	var selno = AllNoticeGrid.getSelNo()-1;
	if (selno <0)
	{
	  alert("��ѡ��Ҫ���������");
	  return;
	}
	var tMissionID = AllNoticeGrid.getRowColData(selno, 9);
	var tSubMissionID = AllNoticeGrid.getRowColData(selno, 10);
	var tActivityID = AllNoticeGrid.getRowColData(selno, 11);

	var i = 0;
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	//fm.action = "BQMasterCenterChk.jsp";
	fm.action = "../bq/MissionApply.jsp?MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID+"";
	document.getElementById("fm").submit(); //�ύ
}

// ��ѯ��ť
function easyQueryClickSelf()
{
	// ��ʼ�����
	initSelfNoticeGrid();
	divApproveNotice.style.display = "none";
	// ��дSQL���
	var strSQL = "";
	var strLike = "like";
	var strEqual="=";
	//��ѯȫ��
//	strSQL = " select EdorAcceptNo,ContNo,InsuredNo,NoticeType,NoticeTypeName,CreateOperator,ManageCom,MakeDate,MissionId,SubMissionId,ActivityId,EdorAppDate,OverTime,AskContent,EdorNo,EdorType,PrtSeq from ( "
//				 + " select b.EdorAcceptNo EdorAcceptNo,c.ContNo ContNo,c.InsuredNo InsuredNo,'0' NoticeType,'��ȫ���������' NoticeTypeName, a.StandbyFlag2 CreateOperator,b.ManageCom ManageCom, "
//         + " a.MakeDate MakeDate,'000000' MissionId,'000000' SubMissionId,'000000' ActivityId,b.EdorAppDate EdorAppDate, (select count(*) from LDCalendar where CommonDate > b.EdorAppdate and commondate <= '"+curDay+"' and workdateflag = 'Y') OverTime, a.StandbyFlag5 AskContent,b.EdorNo,b.EdorType,a.PrtSeq PrtSeq"
//         + " from LOPrtManager a, LPEdorItem b, LCCont c where a.Code = 'BQ38' and a.OtherNo = c.ContNo and a.standbyflag1 = b.EdorAcceptNo and b.ContNo = c.ContNo and a.StateFlag = 'A' and a.StandbyFlag4 = '"+operator+"' and b.Managecom like '"+manageCom+"%'"
//				 + " union "
//				 + " select b.EdorAcceptNo EdorAcceptNo,c.ContNo ContNo,c.InsuredNo InsuredNo,'1' NoticeType,'�˹��˱������' NoticeTypeName,a.CreateOperator CreateOperator,b.ManageCom ManageCom, "
//         + " a.MakeDate MakeDate,a.MissionId MissionId,a.SubMissionId SubMissionId,a.ActivityId ActivityId,b.EdorAppDate EdorAppDate,(select count(*) from LDCalendar where CommonDate > b.EdorAppdate and commondate <= '"+curDay+"' and workdateflag = 'Y') OverTime,'' AskContent,b.EdorNo,b.EdorType,a.MissionProp4 PrtSeq"
//         + " from LWMission a,LPEdorItem b,LCCont c where a.MissionProp8 = b.EdorNo and a.MissionProp2 = b.ContNo and  a.ActivityId in(select activityid from lwactivity where functionid='10020332')  and a.DefaultOperator ='" + operator + "' "
//         + " and c.ContNo = b.ContNo and exists (select 1 from LPIssuePol where EdorNo = a.MissionProp8) and b.Managecom like '"+manageCom+"%') l where 1 = 1 "
//				 + getWherePart('EdorAcceptNo','EdorAcceptNo',strEqual)
//	  		     + getWherePart('ContNo','ContNo',strEqual)
//				 + getWherePart('InsuredNo','InsuredNo',strEqual)
//				 + getWherePart('NoticeType','SelfNoticeType',strEqual)
//				 + getWherePart('CreateOperator','CreateOperator',strEqual)
//				 + getWherePart('ManageCom','ManageCom',strLike);
	
	var  EdorAcceptNo2 = window.document.getElementsByName(trim("EdorAcceptNo"))[0].value;
	var  ContNo2 = window.document.getElementsByName(trim("ContNo"))[0].value;
	var  InsuredNo2 = window.document.getElementsByName(trim("InsuredNo"))[0].value;
	var  SelfNoticeType2 = window.document.getElementsByName(trim("SelfNoticeType"))[0].value;
	var  CreateOperator2 = window.document.getElementsByName(trim("CreateOperator"))[0].value;
	var  ManageCom2 = window.document.getElementsByName(trim("ManageCom"))[0].value;
	var  StartDate2 = window.document.getElementsByName(trim("StartDate"))[0].value;
	var  EndEate2= window.document.getElementsByName(trim("EndEate"))[0].value;
	
	var sqlid2="AllNoticeSql2";
	var mySql2=new SqlClass();
    mySql2.setResourceName("bq.AllNoticeSql");
	mySql2.setSqlId(sqlid2);//ָ��ʹ��SQL��id
	mySql2.addSubPara(curDay);//ָ���������
	mySql2.addSubPara(operator);//ָ���������
	mySql2.addSubPara(manageCom);//ָ���������
	mySql2.addSubPara(curDay);//ָ���������
	mySql2.addSubPara(operator);//ָ���������
	mySql2.addSubPara(manageCom);//ָ���������
	mySql2.addSubPara(EdorAcceptNo2);//ָ���������
	mySql2.addSubPara(ContNo2);//ָ���������
	mySql2.addSubPara(InsuredNo2);//ָ���������
	mySql2.addSubPara(SelfNoticeType2);//ָ���������
	mySql2.addSubPara(CreateOperator2);//ָ���������
	mySql2.addSubPara(ManageCom2);//ָ���������
	mySql2.addSubPara(StartDate2);//ָ���������
	mySql2.addSubPara(EndEate2);//ָ���������
	strSQL = mySql2.getString();
	
//	if(fm.StartDate.value != null && fm.StartDate.value != ""){
//		 	strSQL+= " and MakeDate >= '"+fm.StartDate.value+"'";
//	}
//	
//	if(fm.EndEate.value != null && fm.EndEate.value != ""){
//		 	strSQL+= " and MakeDate <= '"+fm.EndEate.value+"'";
//	}				 
//	strSQL+= " order by EdorAppDate ";         
	turnPageSelf.queryModal(strSQL, SelfNoticeGrid); 
	HighlightSelfRow();
	
}

function dealNotice(){
	 var tSelNo = SelfNoticeGrid.getSelNo()-1;
	 if (tSelNo <0)
	 {
	   alert("��ѡ��Ҫ���������");
	   return;
	 }
	 divApproveNotice.style.display = "none";
	 fm.DispIdeaTrace.value = "";
	 fm.PrtSeq.value = "";
	 fm.MyReply.value = "";
	 var tNoticeType = SelfNoticeGrid.getRowColData(tSelNo, 4);
	 if(tNoticeType == '0')
	 {
	 		//��ȫ���������
	 		var tAskInfo = SelfNoticeGrid.getRowColData(tSelNo,14); 
      fm.DispIdeaTrace.value=tAskInfo;
      var tPrtSeq = SelfNoticeGrid.getRowColData(tSelNo,17); 
		  fm.PrtSeq.value = tPrtSeq;
		  divApproveNotice.style.display = "";
	 }else if(tNoticeType == '1'){
	 		//�˹��˱������
	 		var tContNo = SelfNoticeGrid.getRowColData(tSelNo, 2); 	
	  	var tMissionId  = SelfNoticeGrid.getRowColData(tSelNo, 9); 	
	  	var tSubMissionId = SelfNoticeGrid.getRowColData(tSelNo, 10);	  	
	  	var tEdorNo  = SelfNoticeGrid.getRowColData(tSelNo, 15); 	
	  	var tEdorType = SelfNoticeGrid.getRowColData(tSelNo, 16);	
	  	var tActivityID = SelfNoticeGrid.getRowColData(tSelNo, 11);
	  	window.open("../uw/BQQuestQueryMain.jsp?ActivityID="+tActivityID+"&EdorNo="+tEdorNo+"&EdorType="+tEdorType+"&ContNo="+tContNo+"&Flag=4&MissionID="+tMissionId+"&SubMissionID="+tSubMissionId+"&ActivityID=0000001020","window1");
	 }else if(tNoticeType == '2'){
	 		//��ȫ���֪ͨ��
	 	  var	tContNo = SelfNoticeGrid.getRowColData(tSelNo, 2);  
    	var tPrtNo = SelfNoticeGrid.getRowColData(tSelNo, 2);
    	var tPrtSeq = SelfNoticeGrid.getRowColData(tSelNo, 17);
    	var tMissionId = SelfNoticeGrid.getRowColData(tSelNo, 9);
    	var tSubMissionId = SelfNoticeGrid.getRowColData(tSelNo, 10);
    	var tActivityID = SelfNoticeGrid.getRowColData(tSelNo, 11); 
    	var tCustomerNo = SelfNoticeGrid.getRowColData(tSelNo, 3); 
    	window.open("../uw/BQManuHealthQMain.jsp?ActivityID="+tActivityID+"&ContNo="+tContNo+"&MissionID="+tMissionId+"&SubMissionID="+tSubMissionId+"&PrtNo="+tPrtNo+"&PrtSeq="+tPrtSeq+"&CustomerNo="+tCustomerNo,"window1");    		
	 }else if(tNoticeType == '3'){
	 		//��ȫ����֪ͨ��
	 		var	tContNo = SelfNoticeGrid.getRowColData(tSelNo, 2);
	 		var tActivityID = SelfNoticeGrid.getRowColData(tSelNo, 11);
	 		var tPrtSeq = SelfNoticeGrid.getRowColData(tSelNo, 17);
	 		window.open("../uw/BQRReportReplyDetailMain.jsp?ActivityID="+tActivityID+"&PolNo=" + tContNo + "&SerialNo=" + tPrtSeq,"window1");
	 }else{
	 		
	 }
	 
}

function HighlightAllRow(){
		for(var i=0; i<AllNoticeGrid.mulLineCount; i++){
  	var days = AllNoticeGrid.getRowColData(i,13);  	
  	if(days != null && days != ""){	
    	var ret = Arithmetic(days,'-','5','2');
    	if(ret>=0)
    	{
    		AllNoticeGrid.setRowClass(i,'warn');
    	}
    
  	}
  }
}

function HighlightSelfRow(){
		for(var i=0; i<SelfNoticeGrid.mulLineCount; i++){
  	var days = SelfNoticeGrid.getRowColData(i,13);  	
  	if(days != null && days != ""){	
    	var ret = Arithmetic(days,'-','5','2');
    	if(ret>=0)
    	{
    		SelfNoticeGrid.setRowClass(i,'warn');
    	}
    
  	}
  }
}


