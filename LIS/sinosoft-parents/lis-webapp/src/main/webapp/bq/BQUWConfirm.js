var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();


//����
function saveData(){
	var i = 0;
	var checkFlags;
  	var state = "0";
  

	var checkFlag = GrpGrid.getSelNo() - 1;
	if (checkFlag < 0)
	{
	      alert("��ѡ��Ҫ¼��ͻ�����ı�����");
	      return;
	}
  	var tEdorNo = GrpGrid.getRowColData(checkFlag, 10); 	//alert("tEdorNo=="+tEdorNo);
  	var tEdorType = GrpGrid.getRowColData(checkFlag, 2);//alert("tEdorType=="+tEdorType);
  	var tContNo = GrpGrid.getRowColData(checkFlag, 3); 	
  	var tPrtSeq = GrpGrid.getRowColData(checkFlag, 4); 	
  	//alert("ӡˢ��:"+prtNo);
    var	ManageCom = GrpGrid.getRowColData(checkFlag, 5);
    var MissionID =GrpGrid.getRowColData(checkFlag, 6);
    var SubMissionID =GrpGrid.getRowColData(checkFlag, 7);
    var ActivityID = GrpGrid.getRowColData(checkFlag, 8);
    
    var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    
    mAction="INSERT";
	//document.getElementById("fm").submit()( 'fmAction' ).value = mAction;
	fm.fmAction.value=mAction;
	fm.action = "BQUWConfirmSave.jsp";
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.submit(); //�ύ
}

//�޸�
function updateData(){
	saveData();
}

//���ȷ�����
function confirmOk(){
	
	var checkFlag = GrpGrid.getSelNo() - 1;
	if (checkFlag < 0)
	{
	      alert("��ѡ��Ҫ¼��ͻ�����ı�����");
	      return;
	}
  //ȡ�������ڵĹ���������д���,Ϊ��ȫȷ�Ϲ������ڵ�׼�������������
  fm.ManageCom.value = GrpGrid.getRowColData(checkFlag, 5);
	
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.action = "./BQUWConfirmConfirm.jsp";
	fm.submit(); //�ύ
}

function easyQueryClick(){
	var tSql="";
//		tSql="select missionprop6,missionprop5,missionprop2,missionprop1,missionprop3,"
//				 +"missionid,submissionid,activityid,"
//				 +"(select a.CustomerIdea from lpcuwmaster a where a.edorno=lwmission.missionprop4"
//				 +" and  a.edortype=lwmission.missionprop5 and a.contno=lwmission.missionprop2),missionprop4 "
//				 +"from lwmission where 1=1 "
//				 + " and activityid = '0000000330'  "
//				 + getWherePart('MissionID','MissionID')
//				 + getWherePart('SubMissionID','SubMissionID')
//				 + getWherePart('missionprop4','EdorNo')
//				 + getWherePart('missionprop2','ContNo')
//				 + getWherePart('missionprop5','EdorType');
		
		var  MissionID1 = window.document.getElementsByName(trim("MissionID"))[0].value;
		var  SubMissionID1 = window.document.getElementsByName(trim("SubMissionID"))[0].value;
		var  EdorNo1 = window.document.getElementsByName(trim("EdorNo"))[0].value;
		var  ContNo1 = window.document.getElementsByName(trim("ContNo"))[0].value;
		var  EdorType1 = window.document.getElementsByName(trim("EdorType"))[0].value;
		 var sqlid1="BQUWConfirmSql1";
		 var mySql1=new SqlClass();
		 mySql1.setResourceName("bq.BQUWConfirmSql");
		 mySql1.setSqlId(sqlid1);//ָ��ʹ��SQL��id
		 mySql1.addSubPara(MissionID1);//ָ���������
		 mySql1.addSubPara(SubMissionID1);//ָ���������
		 mySql1.addSubPara(EdorNo1);//ָ���������
		 mySql1.addSubPara(ContNo1);//ָ���������
		 mySql1.addSubPara(EdorType1);//ָ���������
		 tSql = mySql1.getString();
		 
	turnPage.queryModal(tSql, GrpGrid);
	return true;
}

function easyQueryClick1(){
	var tSql="";
//		tSql="select missionprop6,missionprop5,missionprop2,missionprop1,missionprop3,"
//				 +"missionid,submissionid,activityid,"
//				 +"(select a.CustomerIdea from lpcuwmaster a where a.edorno=lbmission.missionprop4"
//				 +" and  a.edortype=lbmission.missionprop5 and a.contno=lbmission.missionprop2),missionprop4 "
//				 +"from lbmission where 1=1 "
//				 + " and activityid = '0000000330'  "
//				 + getWherePart('MissionID','MissionID')
//				// + getWherePart('SubMissionID','SubMissionID')
//				 + getWherePart('missionprop4','EdorNo')
//				 + getWherePart('missionprop2','ContNo');
//				// + getWherePart('missionprop5','EdorType');

		var  MissionID2 = window.document.getElementsByName(trim("MissionID"))[0].value;
		var  EdorNo2 = window.document.getElementsByName(trim("EdorNo"))[0].value;
		var  ContNo2 = window.document.getElementsByName(trim("ContNo"))[0].value;
		 var sqlid2="BQUWConfirmSql2";
		 var mySql2=new SqlClass();
		 mySql2.setResourceName("bq.BQUWConfirmSql");
		 mySql2.setSqlId(sqlid2);//ָ��ʹ��SQL��id
		 mySql2.addSubPara(MissionID2);//ָ���������
		 mySql2.addSubPara(EndDay2);//ָ���������
		 mySql2.addSubPara(ContNo2);//ָ���������
		 tSql = mySql2.getString();
		
	turnPage.queryModal(tSql, GrpGrid);
	return true;
}

function initUWIdea(){
//	var tSql="select State,(select codename from ldcode where codetype='edorappstate' and code=state),UWIdea from LPAppUWMasterMain where EdorAcceptNo="
//	         + "'"+ tEdorAcceptNo +"'";
	
	 var sqlid3="BQUWConfirmSql3";
	 var mySql3=new SqlClass();
	 mySql3.setResourceName("bq.BQUWConfirmSql");
	 mySql3.setSqlId(sqlid3);//ָ��ʹ��SQL��id
	 mySql3.addSubPara(tEdorAcceptNo);//ָ���������
	 var tSql = mySql3.getString();
	
	
	var brr = easyExecSql(tSql);

    if ( brr )
    {
        fm.AppUWState.value = brr[0][0];
        fm.AppUWStateName.value = brr[0][1];
        fm.AppUWIdea.value = brr[0][2];
    }
}

function afterSubmit(FlagStr, content){
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
		easyQueryClick();// ˢ��ҳ��
	}
	else
	{
		content = "�����ɹ���";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		easyQueryClick();// ˢ��ҳ��
	}
}
function afterSubmit2(FlagStr, content,contract){
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
		easyQueryClick();// ˢ��ҳ��
	}
	else
	{
		content = "�����ɹ���";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		top.opener.easyQueryClickSelf();
		top.close();
	}
}