var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var k = 0;
var strOperate = "like";
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
var sqlresourcename = "bq.BQUWConfirmPoolSql";

/*********************************************************************
 *  ִ������Լɨ���EasyQuery
 *  ����:��ѯ��ʾ������ɨ���.��ʾ����:ɨ��������سɹ�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function easyQueryClick()
{
	 initGrpGrid();//��ʼ�����
	//��������<���û��ѡ����������ʹ�õ�½ʱ�ĵ�½����>
	if(trim(fm.ManageCom.value)=="")
	{
		//alert("��½�������룺"+manageCom);
		fm.ManageCom.value=manageCom;
	}
	// ��дSQL���
	var strSQL = "";
/*		
		strSQL = "select missionprop6,missionprop5,missionprop2,missionprop1,missionprop3,missionid,submissionid,activityid,missionprop4,"
				 + " (select nvl(edorappdate,'') from lpedoritem where edoracceptno = lwmission.missionprop6),(select count(*) from ldcalendar where commondate > (select edorappdate from lpedoritem where edoracceptno = lwmission.missionprop6) and commondate <= '"+curDay+"' and workdateflag = 'Y') "
				 + " from lwmission where 1=1 "
				 + " and activityid = '0000000330' and processid = '0000000000'"
				 + getWherePart('missionprop4','EdorNo')
				 + getWherePart('missionprop2','ContNo')
				 + getWherePart('missionprop3','ManageCom',strOperate)
				 + getWherePart('missionprop5','EdorType')
				 + " and defaultoperator is null "
				 + " order by (select edorappdate from lpedoritem where EdorAcceptNo = lwmission.missionprop6),missionprop2,missionprop1";
				 	//prompt("",strSQL);
				 	 //alert(strSQL);
				 	 //alert(strOperate);
*/	
	var sqlid1="BQUWConfirmPoolSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(curDay);//ָ������Ĳ���
	mySql1.addSubPara(fm.EdorNo.value);
	mySql1.addSubPara(fm.ContNo.value);
	mySql1.addSubPara(fm.ManageCom.value);
	mySql1.addSubPara(fm.EdorType.value);
	strSQL=mySql1.getString();
	//turnPage.queryModal(mySql.getString(),ManagerDataInfoGrid);// mySql.getString()�Ϳ��Ի�ö�Ӧ��SQL��
				 	 
	turnPage.queryModal(strSQL, GrpGrid);
	HighlightAllRow();
	return true;
}


function easyQueryClickSelf()
{
	// ��дSQL���
	var strSQL = "";
/*		strSQL = "select missionprop6,missionprop5,missionprop2,missionprop1,missionprop3,missionid,submissionid,activityid,missionprop4, "
				 + " (select nvl(edorappdate,'') from lpedoritem where edoracceptno = lwmission.missionprop6),(select count(*) from ldcalendar where commondate > (select edorappdate from lpedoritem where edoracceptno = lwmission.missionprop6) and commondate <= '"+curDay+"' and workdateflag = 'Y') "
				 + " from lwmission where 1=1 "
				 + " and activityid = '0000000330' and processid = '0000000000'"
				 + " and defaultoperator ='" + operator + "'"
				 + " and missionprop3 like '" + comcode + "%%'" //����Ȩ�޹�������
				 //+ " and missionprop6='"+trim(InputTime)+"' "
				 + " order by (select edorappdate from lpedoritem where EdorAcceptNo = lwmission.missionprop6),missionprop2,missionprop1";	
				 //alert(strSQL) ;
				 //prompt("",strSQL);
*/
	var sqlid2="BQUWConfirmPoolSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(curDay);//ָ������Ĳ���
	mySql2.addSubPara(operator);
	mySql2.addSubPara(comcode);
	strSQL=mySql2.getString();

	turnPage2.queryModal(strSQL, SelfGrpGrid);
	HighlightSelfRow();
	return true;
}
function HighlightAllRow(){
		for(var i=0; i<PublicWorkPoolGrid.mulLineCount; i++){
  	var days = PublicWorkPoolGrid.getRowColData(i,11);  	
  	if(days != null && days != ""){	
    	var ret = Arithmetic(days,'-','5','2');
    	if(ret>=0)
    	{
    		PublicWorkPoolGrid.setRowClass(i,'warn');
    	}
    
  	}
  }
}

function HighlightSelfRow(){
		for(var i=0; i<PrivateWorkPoolGrid.mulLineCount; i++){
  	var days = PrivateWorkPoolGrid.getRowColData(i,11);  	
  	if(days != null && days != ""){	
    	var ret = Arithmetic(days,'-','5','2');
    	if(ret>=0)
    	{
    		PrivateWorkPoolGrid.setRowClass(i,'warn');
    	}
    
  	}
  }
}
/*
function HighlightAllRow(){
		for(var i=0; i<GrpGrid.mulLineCount; i++){
  	var days = GrpGrid.getRowColData(i,11);  	
  	if(days != null && days != ""){	
    	var ret = Arithmetic(days,'-','5','2');
    	if(ret>=0)
    	{
    		GrpGrid.setRowClass(i,'warn');
    	}
    
  	}
  }
}

function HighlightSelfRow(){
		for(var i=0; i<SelfGrpGrid.mulLineCount; i++){
  	var days = SelfGrpGrid.getRowColData(i,11);  	
  	if(days != null && days != ""){	
    	var ret = Arithmetic(days,'-','5','2');
    	if(ret>=0)
    	{
    		SelfGrpGrid.setRowClass(i,'warn');
    	}
    
  	}
  }
}
*/
function GoToInput()
{
  var i = 0;
  var checkFlags;
  var state = "0";
  

	var checkFlag = GrpGrid.getSelNo() - 1;
	if (checkFlag < 0){
	      alert("��ѡ��Ҫ����ı�����");
	      return;
	}
	//alert("checkFlag=="+checkFlag);
	var tEdorAcceptNo = SelfGrpGrid.getRowColData(checkFlag, 1);
  	var tEdorNo = SelfGrpGrid.getRowColData(checkFlag, 9); 	
  	var tEdorType = SelfGrpGrid.getRowColData(checkFlag, 2);
  	var tContNo = SelfGrpGrid.getRowColData(checkFlag, 3); 	
  	var tPrtSeq = SelfGrpGrid.getRowColData(checkFlag, 4); 	
  	//alert("ӡˢ��:"+prtNo);
    var	ManageCom = SelfGrpGrid.getRowColData(checkFlag, 5);
    var MissionID =SelfGrpGrid.getRowColData(checkFlag, 6);
    var SubMissionID =SelfGrpGrid.getRowColData(checkFlag, 7);
    
    var ActivityID = SelfGrpGrid.getRowColData(checkFlag, 8);
    //var InputTime ="";
        //InputTime = GrpGrid.getRowColData(checkFlag, 8);
    
//    var JudgeSql="Select * from lwmission where missionprop2='"+ tContNo +"' and Activityid='"+ActivityID+"'";
	
	var sqlid3="BQUWConfirmPoolSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
	mySql3.addSubPara(tContNo);//ָ������Ĳ���
	mySql3.addSubPara(ActivityID);
	//strSQL=mySql3.getString();

    var arrJudge = easyExecSql(mySql3.getString());
    if(arrJudge==null){
    	alert("��ѯ�ڵ�����ʧ�ܣ�");
    	return false;
    }
    
    var urlStr = "./ProposalScanApply.jsp?prtNo=" + prtNo + "&operator=" + operator + "&state=" + state;

//    var sFeatures = "status:no;help:0;close:0;dialogWidth:400px;dialogHeight:200px;resizable=1";
    //�����ӡˢ��
	var NoType = type;
    //��ɨ���¼�����
//    sFeatures = "";
    //sFeatures = "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1";
    window.open("./BQUWConfirmMain.jsp?ScanFlag=1&EdorAcceptNo="+tEdorAcceptNo+"&ContNo="+tContNo+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID, "", sFeatures);        
}
function InitGoToInput()
{ 
  var i = 0;
  var checkFlags;
  var state = "0";
  

	var checkFlag = PrivateWorkPoolGrid.getSelNo() - 1;
	if (checkFlag < 0)
	{
	      alert("��ѡ��Ҫ����ı�����");
	      return;
	}
	var tEdorAcceptNo = PrivateWorkPoolGrid.getRowColData(checkFlag, 1);
	 
  	var tEdorNo = PrivateWorkPoolGrid.getRowColData(checkFlag, 6); 	//alert("tEdorNo=="+tEdorNo);
  	var tEdorType = PrivateWorkPoolGrid.getRowColData(checkFlag, 2);//alert("tEdorType=="+tEdorType);
  	var tContNo = PrivateWorkPoolGrid.getRowColData(checkFlag, 3); 	
  	var tPrtSeq = PrivateWorkPoolGrid.getRowColData(checkFlag, 4); 	
  	//alert("ӡˢ��:"+prtNo);
    var	ManageCom = PrivateWorkPoolGrid.getRowColData(checkFlag, 5);
    var MissionID =PrivateWorkPoolGrid.getRowColData(checkFlag, 10);
    var SubMissionID =PrivateWorkPoolGrid.getRowColData(checkFlag, 11);
    
    var ActivityID = PrivateWorkPoolGrid.getRowColData(checkFlag,13);

//	var JudgeSql="Select * from lwmission where missionprop2='"+ tContNo +"' and Activityid='"+ActivityID+"' ";
 
	var sqlid4="BQUWConfirmPoolSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
	mySql4.addSubPara(tContNo);//ָ������Ĳ���
	mySql4.addSubPara(ActivityID);
	//strSQL=mySql4.getString();
 
    var arrJudge = easyExecSql(mySql4.getString());
    if(arrJudge==null){
    	alert("��ѯ�ڵ�����ʧ�ܣ�");
    	return false;
    }

    //var urlStr = "./ProposalScanApply.jsp?prtNo=" + prtNo + "&operator=" + operator + "&state=" + state;

//    var sFeatures = "status:no;help:0;close:0;dialogWidth:400px;dialogHeight:200px;resizable=1";
    //�����ӡˢ��
    //var strReturn = "1";
    //var strReturn = window.showModalDialog(urlStr, "", sFeatures);

    //��ɨ���¼�����
//    sFeatures = "";
    //sFeatures = "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1";
    		//alert(prtNo);
    window.open("./BQUWConfirmMain.jsp?ScanFlag=1&EdorAcceptNo="+tEdorAcceptNo+"&ContNo="+tContNo+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&EdorNo="+tEdorNo+"&EdorType="+tEdorType, "", sFeatures);        
	  HighlightSelfRow();
}
/*
function InitGoToInput()
{ 
  var i = 0;
  var checkFlags;
  var state = "0";
  

	var checkFlag = SelfGrpGrid.getSelNo() - 1;
	if (checkFlag < 0)
	{
	      alert("��ѡ��Ҫ����ı�����");
	      return;
	}
	var tEdorAcceptNo = SelfGrpGrid.getRowColData(checkFlag, 1);
  	var tEdorNo = SelfGrpGrid.getRowColData(checkFlag, 9); 	//alert("tEdorNo=="+tEdorNo);
  	var tEdorType = SelfGrpGrid.getRowColData(checkFlag, 2);//alert("tEdorType=="+tEdorType);
  	var tContNo = SelfGrpGrid.getRowColData(checkFlag, 3); 	
  	var tPrtSeq = SelfGrpGrid.getRowColData(checkFlag, 4); 	
  	//alert("ӡˢ��:"+prtNo);
    var	ManageCom = SelfGrpGrid.getRowColData(checkFlag, 5);
    var MissionID =SelfGrpGrid.getRowColData(checkFlag, 6);
    var SubMissionID =SelfGrpGrid.getRowColData(checkFlag, 7);
    
    var ActivityID = SelfGrpGrid.getRowColData(checkFlag, 8);

//	var JudgeSql="Select * from lwmission where missionprop2='"+ tContNo +"' and Activityid='"+ActivityID+"' ";
 
	var sqlid4="BQUWConfirmPoolSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
	mySql4.addSubPara(tContNo);//ָ������Ĳ���
	mySql4.addSubPara(ActivityID);
	//strSQL=mySql4.getString();
 
    var arrJudge = easyExecSql(mySql4.getString());
    if(arrJudge==null){
    	alert("��ѯ�ڵ�����ʧ�ܣ�");
    	return false;
    }

    //var urlStr = "./ProposalScanApply.jsp?prtNo=" + prtNo + "&operator=" + operator + "&state=" + state;

//    var sFeatures = "status:no;help:0;close:0;dialogWidth:400px;dialogHeight:200px;resizable=1";
    //�����ӡˢ��
    //var strReturn = "1";
    //var strReturn = window.showModalDialog(urlStr, "", sFeatures);

    //��ɨ���¼�����
//    sFeatures = "";
    //sFeatures = "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1";
    		//alert(prtNo);
    window.open("./BQUWConfirmMain.jsp?ScanFlag=1&EdorAcceptNo="+tEdorAcceptNo+"&ContNo="+tContNo+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&EdorNo="+tEdorNo+"&EdorType="+tEdorType, "", sFeatures);        
	  HighlightSelfRow();
}
*/
//���밴ť
function ApplyUW()
{

	var selno = PublicWorkPoolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ�����Ͷ������");
	      return;
	}
	//alert(PublicWorkPoolGrid.getRowColData(selno, 10));
	fm.MissionID.value = PublicWorkPoolGrid.getRowColData(selno, 10);
	fm.SubMissionID.value = PublicWorkPoolGrid.getRowColData(selno, 11);
	fm.ActivityID.value = PublicWorkPoolGrid.getRowColData(selno, 13);
	
	//alert(156);
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
	fm.action = "../bq/MissionApply.jsp";
	document.getElementById("fm").submit(); //�ύ
}
/*
function ApplyUW()
{

	var selno = GrpGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ�����Ͷ������");
	      return;
	}
	fm.MissionID.value = GrpGrid.getRowColData(selno, 6);
	fm.SubMissionID.value = GrpGrid.getRowColData(selno, 7);
	fm.ActivityID.value = GrpGrid.getRowColData(selno, 8);
	
	//alert(156);
	var i = 0;
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   

	fm.action = "../bq/MissionApply.jsp";
	document.getElementById("fm").submit(); //�ύ
}
*/
//�����ύ�󷵻أ�ͬʱˢ��ҳ��,ʧ����ֻ���ش�����Ϣ���ɹ�����ɺ�������
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
//		showInfo = window.open (urlStr,name, 
//
//		"status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,ti
//
//		tlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight
//
//		+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		//wyc modify
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();

		//easyQueryClick();// ˢ��ҳ��
		jQuery("#publicSearch").click();
		jQuery("#privateSearch").click();
		//easyQueryClickSelf();// ˢ��ҳ��
	}
	else
	{
		jQuery("#publicSearch").click();
		jQuery("#privateSearch").click();
		//easyQueryClick();// ˢ��ҳ��
		//easyQueryClickSelf();// ˢ��ҳ��
	    //GoToInput();//����¼��ҳ��
	}

}


function showNotePad()
{

	var selno = SelfGrpGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��һ������");
	      return;
	}
	
	var MissionID = SelfGrpGrid.getRowColData(selno, 4);
	var SubMissionID = SelfGrpGrid.getRowColData(selno, 5);
	var ActivityID = SelfGrpGrid.getRowColData(selno, 6);
	var PrtNo = SelfGrpGrid.getRowColData(selno, 2);
	var NoType = type;
	if(PrtNo == null || PrtNo == "")
	{
		alert("ӡˢ��Ϊ�գ�");
		return;
	}		
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ PrtNo + "&NoType="+ NoType;
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");	
		
}
