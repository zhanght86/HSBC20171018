var turnPage = new turnPageClass();
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var k = 0;
var strOperate = "like";
var sFeatures1 = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes";
/*********************************************************************
 *  ִ������Լɨ���EasyQuery
 *  ����:��ѯ��ʾ������ɨ���.��ʾ����:ɨ��������سɹ�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function easyQueryClick()
{
	var tPolProperty = fm.state.value;
	var mission1="";
	var kindSql="";
	//alert(tPolProperty);
	if(tPolProperty!=null||tPolProperty!=""){
		if(trim(tPolProperty)==51){
			mission1="8651";
		}
		if(trim(tPolProperty)==11){
			mission1="8611";
		}
		if(trim(tPolProperty)==16){
			mission1="8616";
		}
		if(trim(tPolProperty)==21){
			mission1="8621";
		}if(trim(tPolProperty)==61){
			mission1="8661";
		}
		
	}
	if(mission1!=""){
		kindSql=" and missionprop1 like '"+mission1+"%%'";
	}
	if(trim(tPolProperty)==35){
		kindSql=" and (missionprop1 like '8635%%' or missionprop1 like '8625%%' or missionprop1 like '8615%%') ";
	}
	 initGrpGrid();//��ʼ�����
	 //������Ĳ�ѯ��������У��
	//if( verifyInput2() == false ) 
	//{
	//	return false;
	//}
	//��������<���û��ѡ����������ʹ�õ�½ʱ�ĵ�½����>
	if(trim(fm.ManageCom.value)=="")
	{
		//alert("��½�������룺"+manageCom);
		fm.ManageCom.value=manageCom;
	}
	// ��дSQL���
	var strSQL = "";
	if(type =="1")
	{
		//var tempfeeSQL="";//�����ƴ�� �������� ��ѯ������
		//if(trim(fm.PayDate.value)!="")
		//{
		//	tempfeeSQL=" and exists (select 'X' from ljtempfee where tempfeetype='1' and confdate is null and otherno =lwmission.missionprop1 and paydate='"+fm.PayDate.value+"')";
		//}
		
	    var sqlid1="TSWorkPoolSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("app.TSWorkPoolSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.InputDate.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
		mySql1.addSubPara(comcode);//ָ������Ĳ���
		mySql1.addSubPara(kindSql);//ָ������Ĳ���
	    strSQL=mySql1.getString();	
		
//		strSQL = "select missionprop1,modifydate,modifytime,missionprop2,missionprop5,missionprop6,missionid,submissionid,activityid,missionprop7 from lwmission where 1=1 "
//				 + " and activityid = '0000001402' and processid = '0000000003'"
//				 + getWherePart('missionprop1','PrtNo',strOperate)
//				 + getWherePart('missionprop5','InputDate')
//				 + getWherePart('missionprop8','ManageCom',strOperate)
////				 + getWherePart('missionprop8','coolDatePicker')
//				 + " and missionprop8 like '" + comcode + "%%'"  //����Ȩ�޹�������
//				 + " and defaultoperator is null "
//				 //+ " and missionprop6='"+trim(InputTime)+"' "
//				 + kindSql
//				 + " order by missionprop2,missionprop1";
//				 	 //alert(strSQL);
//				 	 //alert(strOperate);
	}
	
	turnPage.queryModal(strSQL, GrpGrid);
	return true;
}


function easyQueryClickSelf()
{
	// ��дSQL���
	var strSQL = "";
	
		var tPolProperty = fm.state1.value;
	var mission1="";
	var kindSql="";
	//alert(tPolProperty);
	if(tPolProperty!=null||tPolProperty!=""){
		if(trim(tPolProperty)==51){
			mission1="8651";
		}
		if(trim(tPolProperty)==11){
			mission1="8611";
		}
		if(trim(tPolProperty)==16){
			mission1="8616";
		}
		if(trim(tPolProperty)==21){
			mission1="8621";
		}if(trim(tPolProperty)==61){
			mission1="8661";
		}
		
	}
	if(mission1!=""){
		kindSql=" and missionprop1 like '"+mission1+"%%'";
	}
	if(trim(tPolProperty)==35){
		kindSql=" and (missionprop1 like '8635%%' or missionprop1 like '8625%%' or missionprop1 like '8615%%') ";
	}
	
	initSelfGrpGrid();
	if(type =="1")
	{
		
		var sqlid2="TSWorkPoolSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("app.TSWorkPoolSql"); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(operator);//ָ������Ĳ���
		mySql2.addSubPara(fm.PrtNo1.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
		mySql2.addSubPara(comcode);//ָ������Ĳ���
		mySql2.addSubPara(fm.InputDate1.value);//ָ������Ĳ���
		mySql2.addSubPara(kindSql);//ָ������Ĳ���
	    strSQL=mySql2.getString();	
		
//		strSQL = "select missionprop1,modifydate,modifytime,missionprop2,missionprop5,missionprop6,missionid,submissionid,activityid,missionprop7 from lwmission where 1=1 "
//				 + " and activityid = '0000001402' and processid = '0000000003'"
//				 + " and defaultoperator ='" + operator + "'"
//				 + getWherePart('missionprop1','PrtNo1',strOperate)
//				 + getWherePart('missionprop8','ManageCom',strOperate)
//				 + " and missionprop8 like '" + comcode + "%%'"  //����Ȩ�޹�������
//				 + getWherePart('missionprop5','InputDate1')
//				 + kindSql
//				// + " and missionprop3 like '" + comcode + "%%'" //����Ȩ�޹�������
//				// + " and missionprop6 ='"+ InputTime + "'"
//				 + " order by missionprop2,missionprop1";	
//				 //alert(strSQL) ;
	}
	turnPage2.queryModal(strSQL, SelfGrpGrid);
	return true;
}

/*********************************************************************
 *  ִ������Լɨ��ġ���ʼ¼�롱
 *  ����:����ɨ���涯ҳ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function GoToInput()
{
  var i = 0;
  var checkFlags;
  var state = "0";
  

	var checkFlag = GrpGrid.getSelNo() - 1;
	if (checkFlag < 0)
	{
	      alert("��ѡ��Ҫ�����ɨ�����");
	      return;
	}
  	prtNo = GrpGrid.getRowColData(checkFlag, 1); 	
    var	ManageCom = GrpGrid.getRowColData(checkFlag, 4);//alert("ManageCom"+ManageCom);
    var MissionID =GrpGrid.getRowColData(checkFlag, 7);//alert("MissionID"+MissionID);
    var SubMissionID =GrpGrid.getRowColData(checkFlag, 8);//alert("SubMissionID"+SubMissionID);
    var ActivityID =GrpGrid.getRowColData(checkFlag, 9);//alert("ActivityID"+ActivityID);
    var PolApplyDate=GrpGrid.getRowColData(checkFlag,3);
    var SubType =GrpGrid.getRowColData(checkFlag, 10);
    //var InputTime ="";
        //InputTime = GrpGrid.getRowColData(checkFlag, 8);
    
    var urlStr = "./ProposalScanApply.jsp?prtNo=" + prtNo + "&operator=" + operator + "&state=" + state;

    var sFeatures = "status:no;help:0;close:0;dialogWidth:400px;dialogHeight:200px;resizable=1";
    //�����ӡˢ��
    var strReturn = "1";
    //var strReturn = window.showModalDialog(urlStr, "", sFeatures);
	var NoType = type;
    //��ɨ���¼�����
    sFeatures = "";
    //sFeatures = "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1";
    window.open("./TSWorkPoolMain.jsp?ScanFlag=1&prtNo="+prtNo+"&ManageCom="+manageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType+"&SubType="+SubType+"&scantype=scan&InputTime="+InputTime, "", sFeatures+sFeatures1);        
}

/*********************************************************************
 *  ִ������Լɨ��ġ���ʼ¼�롱
 *  ����:����ɨ���涯ҳ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function InitGoToInput()
{ 
  var i = 0;
  var checkFlags;
  var state = "0";
  

	var checkFlag = SelfGrpGrid.getSelNo() - 1;
	if (checkFlag < 0)
	{
	      alert("��ѡ��Ҫ�����ɨ�����");
	      return;
	}
  	prtNo = SelfGrpGrid.getRowColData(checkFlag, 1); 	
  	//alert("ӡˢ��:"+prtNo);
    var	ManageCom = SelfGrpGrid.getRowColData(checkFlag, 4);//alert("ManageCom"+ManageCom);
    var MissionID =SelfGrpGrid.getRowColData(checkFlag, 7);//alert("MissionID"+MissionID);
    var SubMissionID =SelfGrpGrid.getRowColData(checkFlag, 8);//alert("SubMissionID"+SubMissionID);
    var PolApplyDate=SelfGrpGrid.getRowColData(checkFlag,3);
    var SubType =SelfGrpGrid.getRowColData(checkFlag, 10);
    
    var ActivityID = SelfGrpGrid.getRowColData(checkFlag, 9);//alert("ActivityID"+ActivityID);
	//var InputTime ="";
		//InputTime =SelfGrpGrid.getRowColData(checkFlag, 8);//alert("InputTime: "+InputTime);
	  var NoType = type;
	  
	  	var sqlid3="TSWorkPoolSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("app.TSWorkPoolSql"); //ָ��ʹ�õ�properties�ļ���
		mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
		mySql3.addSubPara(prtNo);//ָ������Ĳ���
	    var JudgeSql=mySql3.getString();	
	  
//	var JudgeSql="Select * from lwmission where missionprop1='"+ prtNo +"' and Activityid='0000001402'";
    var arrJudge = easyExecSql(JudgeSql);
    if(arrJudge==null){
    	alert("��ѯ�ڵ�����ʧ�ܣ�");
    	return false;
    }
    var urlStr = "./ProposalScanApply.jsp?prtNo=" + prtNo + "&operator=" + operator + "&state=" + state;

    var sFeatures = "status:no;help:0;close:0;dialogWidth:400px;dialogHeight:200px;resizable=1";
    //�����ӡˢ��
    var strReturn = "1";
    //var strReturn = window.showModalDialog(urlStr, "", sFeatures);

    //��ɨ���¼�����
    sFeatures = "";
    //sFeatures = "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1";
    if (strReturn == "1") {
    	if(type=="1")
    	{
    		//alert(prtNo);
    		window.open("./TSWorkPoolMain.jsp?ScanFlag=1&prtNo="+prtNo+"&ManageCom="+manageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType+"&SubType="+SubType+"&scantype=scan&InputTime="+InputTime, "", sFeatures+sFeatures1);        
 		}
 }
}

function ApplyUW()
{

	var selno = GrpGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ�����Ͷ������");
	      return;
	}

	fm.MissionID.value = GrpGrid.getRowColData(selno, 7);
	fm.SubMissionID.value = GrpGrid.getRowColData(selno, 8);
	fm.ActivityID.value = GrpGrid.getRowColData(selno, 9);
	
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
    lockScreen('lkscreen');  
	fm.action = "ProposalApproveChk.jsp";
	document.getElementById("fm").submit(); //�ύ
}

//�����ύ�󷵻أ�ͬʱˢ��ҳ��,ʧ����ֻ���ش�����Ϣ���ɹ�����ɺ�������
function afterSubmit( FlagStr, content )
{
	showInfo.close();
	unlockScreen('lkscreen');  
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
		easyQueryClickSelf();// ˢ��ҳ��
	}
	else
	{//alert(230);
	    GoToInput();//����¼��ҳ��
		easyQueryClick();// ˢ��ҳ��
		easyQueryClickSelf();// ˢ��ҳ��
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
