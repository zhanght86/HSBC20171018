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
		//if(trim(fm.InputDate.value)!="")
		//{
		//	tempfeeSQL=" and exists (select 'X' from ljtempfee where tempfeetype='1' and confdate is null and otherno =lwmission.missionprop1 and paydate='"+fm.PayDate.value+"')";
		//}
		
//		strSQL = "select missionprop1,'','',missionprop9,MissionProp7,missionid,submissionid,activityid,missionprop5 from lwmission where 1=1 "
//				 + " and activityid in (select a.activityid from lwactivity a where a.functionid = '10010053') "
//				 + getWherePart('missionprop1','PrtNo',strOperate)
//				 + getWherePart('missionprop7','InputDate')
//				 + getWherePart('missionprop9','ManageCom',strOperate)
//				 + " and missionprop9 like '" + comcode + "%%'" //����Ȩ�޹�������
//				 //+ getWherePart('missionprop8','coolDatePicker')
//				 //+ " and missionprop3 like '" + comcode + "%%'"  //����Ȩ�޹�������
//				 + " and defaultoperator is null "
//				 + " and missionprop6='"+trim(InputTime)+"' "
//				 + kindSql
//				 + " order by missionprop2,missionprop1";
//				 	//prompt("",strSQL);
//				 	 //alert(strSQL);
//				 	 //alert(strOperate);
		
		    var  PrtNo = window.document.getElementsByName(trim("PrtNo"))[0].value;
		  	var  InputDate = window.document.getElementsByName(trim("InputDate"))[0].value;
		  	var  ManageCom = window.document.getElementsByName(trim("ManageCom"))[0].value;
			var sqlid1="DSScanContSql0";
			var mySql1=new SqlClass();
			mySql1.setResourceName("app.DSScanContSql"); //ָ��ʹ�õ�properties�ļ���
			mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
			mySql1.addSubPara(PrtNo);//ָ������Ĳ���
			mySql1.addSubPara(InputDate);//ָ������Ĳ���
			mySql1.addSubPara(ManageCom);//ָ������Ĳ���
			mySql1.addSubPara(comcode);//ָ������Ĳ���
			mySql1.addSubPara(trim(InputTime));//ָ������Ĳ���
			mySql1.addSubPara(kindSql);//ָ������Ĳ���
			 strSQL=mySql1.getString();
	}
	if(type == "2")
	{
//		strSQL = "select lwmission.missionprop1,lwmission.missionprop3,lwmission.missionprop2,lwmission.missionid,lwmission.submissionid,lwmission.activityid,lwmission.missionprop5 from lwmission where 1=1 "
//				 + " and activityid in (select a.activityid from lwactivity a where a.functionid = '20010011') "
//				 + getWherePart('missionprop1','PrtNo',strOperate)
//				 + getWherePart('missionprop2','InputDate',strOperate)
//				 + getWherePart('missionprop3','ManageCom',strOperate)
//				 //+ getWherePart('missionprop4','ScanOperator')
//				 + " and LWMission.missionprop3 like '" + comcode + "%%'"  //����Ȩ�޹�������
//				 + " and defaultoperator is null "
//				 + " order by lwmission.missionprop1";
		
	    var  PrtNo = window.document.getElementsByName(trim("PrtNo"))[0].value;
	  	var  InputDate = window.document.getElementsByName(trim("InputDate"))[0].value;
	  	var  ManageCom = window.document.getElementsByName(trim("ManageCom"))[0].value;
		var sqlid2="DSScanContSql1";
		var mySql2=new SqlClass();
		mySql2.setResourceName("app.DSScanContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(PrtNo);//ָ������Ĳ���
		mySql2.addSubPara(InputDate);//ָ������Ĳ���
		mySql2.addSubPara(ManageCom);//ָ������Ĳ���
		mySql2.addSubPara(comcode);//ָ������Ĳ���
		 strSQL=mySql2.getString();
	}
	
	turnPage.queryModal(strSQL, GrpGrid);
	return true;
}


function easyQueryClickSelf()
{
	
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
	 initSelfGrpGrid();//��ʼ�����
	//��������<���û��ѡ����������ʹ�õ�½ʱ�ĵ�½����>
	if(trim(fm.ManageCom1.value)=="")
	{
		//alert("��½�������룺"+manageCom);
		fm.ManageCom1.value=manageCom;
	}
	
	
	// ��дSQL���
	var strSQL = "";
	if(type =="1")
	{
//		strSQL = "select missionprop1,modifyDate,modifyTime,missionprop9,MissionProp7,missionid,submissionid,activityid,missionprop5 from lwmission where 1=1 "
//				 + " and activityid in (select a.activityid from lwactivity a where a.functionid = '10010053')"
//				 + " and defaultoperator ='" + operator + "'"
//				 + getWherePart('missionprop1','PrtNo1',strOperate)
//				 + getWherePart('missionprop7','InputDate1')
//				 + getWherePart('missionprop9','ManageCom',strOperate)
//				 + " and missionprop9 like '" + comcode + "%%'" //����Ȩ�޹�������
//				 + " and missionprop6='"+trim(InputTime)+"' "
//				 + kindSql
//				 + " order by missionprop2,missionprop1";	
//				 //alert(strSQL) ;
		  
		    var  PrtNo1 = window.document.getElementsByName(trim("PrtNo1"))[0].value;
		  	var  InputDate1 = window.document.getElementsByName(trim("InputDate1"))[0].value;
		  	var  ManageCom = window.document.getElementsByName(trim("ManageCom"))[0].value;
			var sqlid3="DSScanContSql2";
			var mySql3=new SqlClass();
			mySql3.setResourceName("app.DSScanContSql"); //ָ��ʹ�õ�properties�ļ���
			mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
			mySql3.addSubPara(operator);//ָ������Ĳ���
			mySql3.addSubPara(PrtNo1);//ָ������Ĳ���
			mySql3.addSubPara(InputDate1);//ָ������Ĳ���
			mySql3.addSubPara(ManageCom);//ָ������Ĳ���
			mySql3.addSubPara(comcode);//ָ������Ĳ���
			mySql3.addSubPara(trim(InputTime));//ָ������Ĳ���
			mySql3.addSubPara(kindSql);//ָ������Ĳ���
			strSQL=mySql3.getString();
		
				
	}
	if(type == "2")
	{
//		strSQL = "select lwmission.missionprop1,lwmission.missionprop3,lwmission.missionprop2,lwmission.missionid,lwmission.submissionid,lwmission.activityid,lwmission.missionprop5 from lwmission where 1=1 "
//				 + " and activityid in (select a.activityid from lwactivity a where a.functionid = '20010011') "
//				 + " and defaultoperator ='" + operator + "'"
//				 + " and LWMission.missionprop3 like '" + comcode + "%%'"  //����Ȩ�޹�������
//				 + getWherePart('missionprop1','PrtNo1',strOperate)
//				 + getWherePart('missionprop7','InputDate1')
////				 + " and missionprop9 like '" + comcode + "%%'" //����Ȩ�޹�������
//				 + " and missionprop6='"+trim(InputTime)+"' "
//				 + kindSql
//				 + " order by lwmission.missionprop1";
		
		var  PrtNo1 = window.document.getElementsByName(trim("PrtNo1"))[0].value;
	  	var  InputDate1 = window.document.getElementsByName(trim("InputDate1"))[0].value;
		var sqlid4="DSScanContSql3";
		var mySql4=new SqlClass();
		mySql4.setResourceName("app.DSScanContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
		mySql4.addSubPara(operator);//ָ������Ĳ���
		mySql4.addSubPara(comcode);//ָ������Ĳ���
		mySql4.addSubPara(PrtNo1);//ָ������Ĳ���
		mySql4.addSubPara(InputDate1);//ָ������Ĳ���
		mySql4.addSubPara(trim(InputTime));//ָ������Ĳ���
		mySql4.addSubPara(kindSql);//ָ������Ĳ���
		strSQL=mySql4.getString();
	}
	
	// prompt("",strSQL);
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
    var	ManageCom = GrpGrid.getRowColData(checkFlag, 4);
    var MissionID =GrpGrid.getRowColData(checkFlag, 6);
    var SubMissionID =GrpGrid.getRowColData(checkFlag, 7);
    var ActivityID =GrpGrid.getRowColData(checkFlag, 8);
    var PolApplyDate=GrpGrid.getRowColData(checkFlag,3);
    var SubType =GrpGrid.getRowColData(checkFlag, 9);
    //var InputTime ="";
        //InputTime = GrpGrid.getRowColData(checkFlag, 8);
    
    //var JudgeSql="Select * from lwmission where missionprop1='"+ prtNo +"' and Activityid='"+ActivityID+"' and missionprop6='"+InputTime+"'";
    var sqlid5="DSScanContSql4";
	var mySql5=new SqlClass();
	mySql5.setResourceName("app.DSScanContSql"); //ָ��ʹ�õ�properties�ļ���
	mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
	mySql5.addSubPara(PrtNo);//ָ������Ĳ���
	mySql5.addSubPara(ActivityID);//ָ������Ĳ���
	mySql5.addSubPara(InputTime);//ָ������Ĳ���
	var JudgeSql=mySql5.getString();
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
	var NoType = type;
    //��ɨ���¼�����
    sFeatures = "";
    //sFeatures = "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1";
    if (strReturn == "1") 
    	if(type=="1")
    	{
    		//sql = "select missionprop5 from lwmission where activityid in (select a.activityid from lwactivity a where a.functionid = '10010053') and missionprop1 = '"+prtNo+"'";
    		    var sqlid6="DSScanContSql5";
    			var mySql6=new SqlClass();
    			mySql6.setResourceName("app.DSScanContSql"); //ָ��ʹ�õ�properties�ļ���
    			mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
    			mySql6.addSubPara(PrtNo);//ָ������Ĳ���
    			 sql=mySql6.getString();
			var brr = easyExecSql(sql );
    		//alert(brr[0][0]);
    		window.open("./DSContInputScanMain.jsp?ScanFlag=1&prtNo="+prtNo+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType+"&SubType="+SubType+"&scantype=scan&InputTime="+InputTime, "", sFeatures+sFeatures1);        
 		}
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
    var	ManageCom = SelfGrpGrid.getRowColData(checkFlag, 4);
    var MissionID =SelfGrpGrid.getRowColData(checkFlag, 6);
    var SubMissionID =SelfGrpGrid.getRowColData(checkFlag, 7);
    var PolApplyDate=SelfGrpGrid.getRowColData(checkFlag,3);
    var SubType =SelfGrpGrid.getRowColData(checkFlag, 9);
    
    var ActivityID = SelfGrpGrid.getRowColData(checkFlag, 8);
	//var InputTime ="";
		//InputTime =SelfGrpGrid.getRowColData(checkFlag, 8);//alert("InputTime: "+InputTime);
	var NoType = type;

	//var JudgeSql="Select * from lwmission where missionprop1='"+ prtNo +"' and Activityid='"+ActivityID+"' and missionprop6='"+InputTime+"'";
	    var sqlid7="DSScanContSql6";
		var mySql7=new SqlClass();
		mySql7.setResourceName("app.DSScanContSql"); //ָ��ʹ�õ�properties�ļ���
		mySql7.setSqlId(sqlid7);//ָ��ʹ�õ�Sql��id
		mySql7.addSubPara(PrtNo);//ָ������Ĳ���
		mySql7.addSubPara(ActivityID);//ָ������Ĳ���
		mySql7.addSubPara(InputTime);//ָ������Ĳ���
		var JudgeSql=mySql7.getString();
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
    		//sql = "select missionprop5 from lwmission where activityid in (select a.activityid from lwactivity a where a.functionid = '10010053') and missionprop1 = '"+prtNo+"'";
    		var sqlid8="DSScanContSql7";
 			var mySql8=new SqlClass();
 			mySql8.setResourceName("app.DSScanContSql"); //ָ��ʹ�õ�properties�ļ���
 			mySql8.setSqlId(sqlid8);//ָ��ʹ�õ�Sql��id
 			mySql8.addSubPara(PrtNo);//ָ������Ĳ���
 			 sql=mySql8.getString();
			var brr = easyExecSql(sql);
    		//alert(brr[0][0]);
    		window.open("./DSContInputScanMain.jsp?ScanFlag=1&prtNo="+prtNo+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType+"&SubType="+SubType+"&scantype=scan&InputTime="+InputTime, "", sFeatures+sFeatures1);        
 		}
 }
}
//���밴ť
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
	unlockScreen('lkscreen');  
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
		easyQueryClickSelf();// ˢ��ҳ��
	}
	else
	{
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
