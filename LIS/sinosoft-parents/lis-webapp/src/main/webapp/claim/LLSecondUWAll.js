//�������ƣ�LLSecondUWAllAll.js
//�����ܣ������˹��˱���ȡ����
//�������ڣ�2005-1-28 11:10:36
//������  ��zhangxing
//���¼�¼��  ������ yuejw   ��������     ����ԭ��/����
var showInfo;
var mDebug="0";
var flag;  
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";

//��ѯ��ť------��ѯ[�����˹��˱�����]
function queryClick()
{
	initLLCUWBatchGridQuery();
}
//���ð�ť------������д��ѯ����
//function cancleClick()
//{
//	fm.QCaseNo.value="";
//	fm.QInsuredNo.value="";
//	fm.QInsuredName.value="";
//	fm.QClaimRelFlag.value="";
//	fm.QClaimRelFlagName.value="";
//	
//}

//���ð�ť------������д��ѯ����-----20130608 lzf
function cancleClick()
{
	PublicWorkPoolQueryGrid.setRowColData(0, 2,"");
	PublicWorkPoolQueryGrid.setRowColData(0, 4,"");
	PublicWorkPoolQueryGrid.setRowColData(0, 5,"");
	PublicWorkPoolQueryGrid.setRowColData(0, 7,"");
}

//��ѯ[�����˹��˱�����]------�����
function initLLCUWBatchGridQuery()
{

//	var strSQL = "select missionprop1,missionprop2,"
//		   + " (select decode(sum(count(distinct batno)),"
//		   + "	'',"
//		   + "' ',"
//		   + " sum(count(distinct batno)))"
//		   + " from llcuwbatch"
//		   + " where caseno = a.missionprop1"
//		   + " group by batno),"
//		   + " '','',missionprop9,missionprop4,"
//	       + " makedate,maketime,missionprop3,missionprop20, "
//	       + " missionid,submissionid,activityid,missionprop5"
//		   + "  from lwmission a where 1=1 "
//           + " and activityid = '0000005505' "
//           + " and processid = '0000000005'"
//           + " and DefaultOperator is null"
//           + " and activitystatus = '1' "
//           + getWherePart( 'missionprop1' ,'QCaseNo')
//           + getWherePart( 'missionprop2' ,'BatNo')
//           + getWherePart( 'missionprop3' ,'QInsuredNo')
//           + getWherePart( 'missionprop4' ,'QInsuredName','like')
//           + getWherePart( 'missionprop5' ,'QClaimRelFlag')
//           + getWherePart( 'makedate' ,'theCurrentDate','<=')
//           + getWherePart( 'missionprop20' ,'ManageCom','like')  //<��ǰ����>
//           + " order by missionprop2";	
		   
		   
		var sqlid1="LLSecondUWAllSQL1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("claim.LLSecondUWAllSQL"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(fm.QCaseNo.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.BatNo.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.QInsuredNo.value);//ָ������Ĳ���
		
		mySql1.addSubPara(fm.QInsuredName.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.QClaimRelFlag.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.theCurrentDate.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
	    var strSQL =mySql1.getString();	
		   
		   
		   
		   
    turnPage.pageLineNum=10;
	turnPage.queryModal(strSQL, LLCUWBatchGrid);
}

//��ѯ[�����˹��˱�����]------���˶���
function initSelfLLCUWBatchGridQuery() {
	
		var sqlid2="LLSecondUWAllSQL2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("claim.LLSecondUWAllSQL"); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(fm.Operator.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.QCaseNo2.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.BatNo2.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.QInsuredNo2.value);//ָ������Ĳ���
		
		mySql2.addSubPara(fm.QInsuredName2.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.QClaimRelFlag2.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.theCurrentDate2.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.ManageCom2.value);//ָ������Ĳ���
	    var strSQL =mySql2.getString();	
	
	
//	var strSQL = "select missionprop1,missionprop2,"
//		   + " (select decode(sum(count(distinct batno)),"
//		   + "	'',"
//		   + "' ',"
//		   + " sum(count(distinct batno)))"
//		   + " from llcuwbatch"
//		   + " where caseno = a.missionprop1"
//		   + " group by batno),"
//		   + " '','',missionprop9,missionprop4,"
//	       + " makedate,maketime,missionprop3,missionprop20, "
//	       + " missionid,submissionid,activityid,missionprop5"
//		   + "  from lwmission a where 1=1 "
//           + " and activityid = '0000005505' "
//           + " and processid = '0000000005'"
//           + " and activitystatus = '1' "
//           + " and DefaultOperator ='"+fm.Operator.value+"'"
//           + getWherePart( 'missionprop1' ,'QCaseNo2')
//           + getWherePart( 'missionprop2' ,'BatNo2')
//           + getWherePart( 'missionprop3' ,'QInsuredNo2')
//           + getWherePart( 'missionprop4' ,'QInsuredName2','like')
//           + getWherePart( 'missionprop5' ,'QClaimRelFlag2')
//           + getWherePart( 'makedate' ,'theCurrentDate2','<=')
//           + getWherePart( 'missionprop20' ,'ManageCom2','like')  //<��ǰ����>
//           + " order by missionprop2";	//prompt("",strSQL);
    turnPage2.pageLineNum=10;
	turnPage2.queryModal(strSQL, SelfLLCUWBatchGrid);
}

//[��������]��ť
//function applyClick()
//{
//	//09-06-02  ���ӵ�ǰ����Ա�Ƿ��к˱�Ȩ�޵�У��
//	
//		var sqlid3="LLSecondUWAllSQL3";
//		var mySql3=new SqlClass();
//		mySql3.setResourceName("claim.LLSecondUWAllSQL"); //ָ��ʹ�õ�properties�ļ���
//		mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
//		mySql3.addSubPara(fm.Operator.value);//ָ������Ĳ���
//
//	    var tSql =mySql3.getString();	
//	
////	var tSql = "select 1 from lduwuser where usercode='"+fm.Operator.value+"' and uwtype='1'";
//	var arr = easyExecSql(tSql);
//	if(!arr){
//		alert("��ǰ�û�û�к˱�Ȩ�ޣ�");
//		return false;
//	}
//	var tSel = LLCUWBatchGrid.getSelNo()-1;
//	if(tSel<0)
//	{
//		alert("��ѡ��һ�������¼");
//		return;	
//	}
//	fm.MissionID.value = LLCUWBatchGrid.getRowColData(tSel, 12);//alert("fm.MissionID.value"+fm.MissionID.value);
//	fm.SubMissionID.value=LLCUWBatchGrid.getRowColData(tSel, 13);//alert("fm.SubMissionID.value"+fm.SubMissionID.value);
//	fm.ActivityID.value= LLCUWBatchGrid.getRowColData(tSel, 14);//alert("fm.ActivityID.value"+fm.ActivityID.value);
//	fm.SBatNo.value=LLCUWBatchGrid.getRowColData(tSel, 2);//alert("fm.ActivityID.value"+fm.ActivityID.value);
//	fm.SCaseNo.value=LLCUWBatchGrid.getRowColData(tSel, 1);//alert("fm.ActivityID.value"+fm.ActivityID.value);
//	//09-06-06  ����У����������ѱ����뵽���˳��򷵻ش�����ʾ ˢ�½���
//	
//		var sqlid4="LLSecondUWAllSQL4";
//		var mySql4=new SqlClass();
//		mySql4.setResourceName("claim.LLSecondUWAllSQL"); //ָ��ʹ�õ�properties�ļ���
//		mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
//		mySql4.addSubPara(fm.MissionID.value);//ָ������Ĳ���
//		mySql4.addSubPara(fm.SubMissionID.value);//ָ������Ĳ���
//
//	    var tOperatorSql =mySql4.getString();	
//	
////	var tOperatorSql = "select * from lwmission where missionid='"+fm.MissionID.value+"'"
////					+ " and submissionid='"+fm.SubMissionID.value+"' and activityid='0000005505'"
////					+ " and defaultoperator is not null";
//	var tOperator = easyExecSql(tOperatorSql);
//	if(tOperator){
//		alert("�����ѱ�������Ա���뵽���˹����أ�");
//		initLLCUWBatchGridQuery();
//		initSelfLLCUWBatchGridQuery();
//		return false;
//	}
//	fm.action = "./LLSecondUWAllSave.jsp";
//	submitForm(); //�ύ
//}

//modify by lzf 20130608
function applyClick()
{
	//09-06-02  ���ӵ�ǰ����Ա�Ƿ��к˱�Ȩ�޵�У��
	
		var sqlid3="LLSecondUWAllSQL3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("claim.LLSecondUWAllSQL"); //ָ��ʹ�õ�properties�ļ���
		mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
		mySql3.addSubPara(fm.Operator.value);//ָ������Ĳ���

	    var tSql =mySql3.getString();	

	var arr = easyExecSql(tSql);
	if(!arr){
		alert("��ǰ�û�û�к˱�Ȩ�ޣ�");
		return false;
	}
	var tSel = PublicWorkPoolGrid.getSelNo()-1;
	if(tSel<0)
	{
		alert("��ѡ��һ�������¼");
		return;	
	}
	fm.MissionID.value = PublicWorkPoolGrid.getRowColData(tSel, 12);//alert("fm.MissionID.value"+fm.MissionID.value);
	fm.SubMissionID.value=PublicWorkPoolGrid.getRowColData(tSel, 13);//alert("fm.SubMissionID.value"+fm.SubMissionID.value);
	fm.ActivityID.value= PublicWorkPoolGrid.getRowColData(tSel, 15);//alert("fm.ActivityID.value"+fm.ActivityID.value);
	fm.SBatNo.value=PublicWorkPoolGrid.getRowColData(tSel, 2);//alert("fm.SBatNo.value"+fm.SBatNo.value);
	fm.SCaseNo.value=PublicWorkPoolGrid.getRowColData(tSel, 1);//alert("fm.SCaseNo.value"+fm.SCaseNo.value);return false;
	//09-06-06  ����У����������ѱ����뵽���˳��򷵻ش�����ʾ ˢ�½���
	
		var sqlid4="LLSecondUWAllSQL4";
		var mySql4=new SqlClass();
		mySql4.setResourceName("claim.LLSecondUWAllSQL"); //ָ��ʹ�õ�properties�ļ���
		mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
		mySql4.addSubPara(fm.MissionID.value);//ָ������Ĳ���
		mySql4.addSubPara(fm.SubMissionID.value);//ָ������Ĳ���

	    var tOperatorSql =mySql4.getString();	

	var tOperator = easyExecSql(tOperatorSql);
	if(tOperator){
		alert("�����ѱ�������Ա���뵽���˹����أ�");
		jQuery("#privateSearch").click();
		jQuery("#publicSearch").click();
		return false;
	}
	fm.action = "./LLSecondUWAllSave.jsp"; 
	submitForm(); //�ύ
}

/*******************ѡ�и��˶��е�һ����¼��  ����:����˱�����**************** */
//function SelfLLCUWBatchGridClick()
//{
//	var tSel = SelfLLCUWBatchGrid.getSelNo()-1;
//	var tCaseNo = SelfLLCUWBatchGrid.getRowColData(tSel,1); //�ⰸ��
//	var tBatNo = SelfLLCUWBatchGrid.getRowColData(tSel,2); 	//���κ�
//	var tInsuredNo = SelfLLCUWBatchGrid.getRowColData(tSel,10);   //�����˿ͻ��� 
//	var tClaimRelFlag = "1";//SelfLLCUWBatchGrid.getRowColData(tSel,11); 	//�ⰸ��ر�־
//	var tMissionid = SelfLLCUWBatchGrid.getRowColData(tSel,12);  	//����ID 
//	var tSubmissionid = SelfLLCUWBatchGrid.getRowColData(tSel,13);  	 //������ID 
//	var tActivityid = SelfLLCUWBatchGrid.getRowColData(tSel,14); 	 //�ڵ�� 	
//	//alert(tMissionid);
//	var strURL="./LLSecondUWALLInputMain.jsp";
//	strURL += "?CaseNo=" + tCaseNo ;	//�ⰸ��
//    strURL += "&BatNo=" + tBatNo ; //���κ�			    
//	strURL += "&InsuredNo=" + tInsuredNo ;   //�����˿ͻ��� 
//	strURL += "&ClaimRelFlag=" + tClaimRelFlag ;   //�ⰸ��ر�־ 	
//	strURL += "&Missionid=" + tMissionid ;   //����ID 
//	strURL += "&Submissionid=" + tSubmissionid ;   //������ID 
//	strURL += "&Activityid=" + tActivityid ;   //�ڵ�� 	
//	//prompt("",strURL);
//	window.open(strURL,"",sFeatures);
//}


//�ύ����
function submitForm()
{
    var i = 0;
    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    document.getElementById("fm").submit(); //�ύ
    tSaveFlag ="0";
}

/*******  �ύ�����,���������ݷ��غ�ִ�еĲ��������� �˱�����ҳ�� ****************************/
function afterSubmit( FlagStr, content )
{
	showInfo.close();
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
	
	if (FlagStr == "Fail" )
	{                 
		alert(content);
		//initLLCUWBatchGridQuery();
		//initSelfLLCUWBatchGridQuery();
		jQuery("#privateSearch").click();
		jQuery("#publicSearch").click();
	}
	else
	{ 
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");     
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	//	applySucc();  //modify wyc
		//initLLCUWBatchGridQuery();
		//initSelfLLCUWBatchGridQuery();
		jQuery("#privateSearch").click();
		jQuery("#publicSearch").click();
	}

}
//modify by lzf 20130608
function applySucc()
{
	var tSel = PublicWorkPoolGrid.getSelNo()-1;
	var tCaseNo = PublicWorkPoolGrid.getRowColData(tSel,1); //�ⰸ��
	var tBatNo = PublicWorkPoolGrid.getRowColData(tSel,2); 	//���κ�
	var tInsuredNo = PublicWorkPoolGrid.getRowColData(tSel,9);   //�����˿ͻ��� 
	var tClaimRelFlag = "1";//LLCUWBatchGrid.getRowColData(tSel,11); 	//�ⰸ��ر�־
	var tMissionid =PublicWorkPoolGrid.getRowColData(tSel,12);  	//����ID 
	var tSubmissionid =PublicWorkPoolGrid.getRowColData(tSel,13);  	 //������ID 
	var tActivityid = PublicWorkPoolGrid.getRowColData(tSel,15); 	 //�ڵ�� 	
	
	var strURL="./LLSecondUWALLInputMain.jsp";
	strURL += "?CaseNo=" + tCaseNo ;	//�ⰸ��
    strURL += "&BatNo=" + tBatNo ; //���κ�			    
	strURL += "&InsuredNo=" + tInsuredNo ;   //�����˿ͻ��� 
	strURL += "&ClaimRelFlag=" + tClaimRelFlag ;   //�ⰸ��ر�־ 	
	strURL += "&Missionid=" + tMissionid ;   //����ID 
	strURL += "&Submissionid=" + tSubmissionid ;   //������ID 
	strURL += "&Activityid=" + tActivityid ;   //�ڵ�� 	
	window.open(strURL,"",sFeatures);
}
function SelfLLCUWBatchGridClick()
{
	var tSel = PrivateWorkPoolGrid.getSelNo()-1;
	var tCaseNo = PrivateWorkPoolGrid.getRowColData(tSel,1); //�ⰸ��
	var tBatNo = PrivateWorkPoolGrid.getRowColData(tSel,2); 	//���κ�
	var tInsuredNo = PrivateWorkPoolGrid.getRowColData(tSel,9);   //�����˿ͻ��� 
	var tClaimRelFlag = "1";//SelfLLCUWBatchGrid.getRowColData(tSel,11); 	//�ⰸ��ر�־
	var tMissionid = PrivateWorkPoolGrid.getRowColData(tSel,12);  	//����ID 
	var tSubmissionid = PrivateWorkPoolGrid.getRowColData(tSel,13);  	 //������ID 
	var tActivityid = PrivateWorkPoolGrid.getRowColData(tSel,15); 	 //�ڵ�� 	
	//alert(tMissionid);
	var strURL="./LLSecondUWALLInputMain.jsp";
	strURL += "?CaseNo=" + tCaseNo ;	//�ⰸ��
    strURL += "&BatNo=" + tBatNo ; //���κ�			    
	strURL += "&InsuredNo=" + tInsuredNo ;   //�����˿ͻ��� 
	strURL += "&ClaimRelFlag=" + tClaimRelFlag ;   //�ⰸ��ر�־ 	
	strURL += "&Missionid=" + tMissionid ;   //����ID 
	strURL += "&Submissionid=" + tSubmissionid ;   //������ID 
	strURL += "&Activityid=" + tActivityid ;   //�ڵ�� 	
	//prompt("",strURL);
	window.open(strURL,"",sFeatures);
}

//end by lzf
/*******************�������������ɹ�, ����:����˱�����**************** */
//function applySucc()
//{
//	var tSel = LLCUWBatchGrid.getSelNo()-1;
//	var tCaseNo = LLCUWBatchGrid.getRowColData(tSel,1); //�ⰸ��
//	var tBatNo = LLCUWBatchGrid.getRowColData(tSel,2); 	//���κ�
//	var tInsuredNo = LLCUWBatchGrid.getRowColData(tSel,10);   //�����˿ͻ��� 
//	var tClaimRelFlag = "1";//LLCUWBatchGrid.getRowColData(tSel,11); 	//�ⰸ��ر�־
//	var tMissionid =LLCUWBatchGrid.getRowColData(tSel,12);  	//����ID 
//	var tSubmissionid =LLCUWBatchGrid.getRowColData(tSel,13);  	 //������ID 
//	var tActivityid = LLCUWBatchGrid.getRowColData(tSel,14); 	 //�ڵ�� 	
//	
//	var strURL="./LLSecondUWALLInputMain.jsp";
//	strURL += "?CaseNo=" + tCaseNo ;	//�ⰸ��
//    strURL += "&BatNo=" + tBatNo ; //���κ�			    
//	strURL += "&InsuredNo=" + tInsuredNo ;   //�����˿ͻ��� 
//	strURL += "&ClaimRelFlag=" + tClaimRelFlag ;   //�ⰸ��ر�־ 	
//	strURL += "&Missionid=" + tMissionid ;   //����ID 
//	strURL += "&Submissionid=" + tSubmissionid ;   //������ID 
//	strURL += "&Activityid=" + tActivityid ;   //�ڵ�� 	
//	window.open(strURL,"",sFeatures);
//}

//function queryClick2(){
//		var strSQL = "select missionprop1,missionprop2,'','',missionprop9,missionprop4,"
//	       + " makedate,missionprop3,missionprop20, "
//	       + " missionid,submissionid,activityid,missionprop5,"
//	       + " decode((select sum(count(distinct batno))"
//	       + " from llcuwbatch"
//	       + " where '1236998690000' = '1236998690000'"
//	       + " and caseno = a.missionprop1"
//	       + " group by batno),1,'',(select sum(count(distinct batno))"
//	       + " from llcuwbatch"
//	       + " where '1236998690000' = '1236998690000'"
//	       + " and caseno = a.missionprop1"
//	       + " group by batno),'')"
//	       //+ " case activitystatus when '1' then 'δ�˹��˱�' when '3' then '�˱��ѻظ�' "
//	       //+ " when '2' then '�˱�δ�ظ�'  when '4' then '�ٱ�δ�ظ�' when '5' then '�ٱ��ѻظ�' end,"
//	       //+ " missionprop3,missionprop4,"
//		   //+ " case missionprop5 when '0' then '���' when '1' then '�����' end,"
//		   //+ " missionprop20,missionid,submissionid,activityid, missionprop5 from lwmission where 1=1 "
//		   + "  from lwmission a where 1=1 "
//           + " and activityid = '0000005505' "
//           + " and processid = '0000000005'"
//           + " and activitystatus = '1' "
//           + " and DefaultOperator ='"+fm.Operator.value+"'"
//           + getWherePart( 'missionprop1' ,'QCaseNo2')
//           + getWherePart( 'missionprop2' ,'BatNo2')
//           + getWherePart( 'missionprop3' ,'QInsuredNo2')
//           + getWherePart( 'missionprop4' ,'QInsuredName2','like')
//           + getWherePart( 'missionprop5' ,'QClaimRelFlag2')
//           + getWherePart( 'makedate' ,'theCurrentDate2','<=')
//           + getWherePart( 'missionprop20' ,'ManageCom2','like')  //<��ǰ����>
//           + " order by missionprop1,missionprop2";	
//	turnPage2.pageLineNum=5;
//	turnPage2.queryModal(strSQL, SelfLLCUWBatchGrid);
//}