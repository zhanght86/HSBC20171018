//�������ƣ�ScanContInput.js
//�����ܣ���������Լɨ�������¼��
//�������ڣ�2004-12-22 11:10:36
//������  ��HYQ
//���¼�¼��  ������    ��������     ����ԭ��/����

var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var k = 0;
var strOperate = "like";
var sqlresourcename = "appgrp.ScanContInputSql";
/*********************************************************************
 *  ִ������Լɨ���EasyQuery
 *  ����:��ѯ��ʾ������ɨ���.��ʾ����:ɨ��������سɹ�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function easyQueryClick()
{
	// ��ʼ�����
	initGrpGrid();

	// ��дSQL���
	var strSQL = "";
 //alert(type);
	if(type =="1")
	{
		/*
		strSQL = "select lwmission.missionprop1,lwmission.missionprop3,lwmission.missionprop2,lwmission.missionid,lwmission.submissionid,lwmission.activityid,lwmission.missionprop5 from lwmission where 1=1 "
				 + " and activityid = '0000001099' "
				 + " and processid = '0000000003'"
				 + getWherePart('missionprop1','PrtNo',strOperate)
				 + getWherePart('missionprop2','InputDate',strOperate)
				 + getWherePart('missionprop3','ManageCom',strOperate)
				 //+ getWherePart('missionprop4','ScanOperator')
				 + " and LWMission.missionprop3 like '" + comcode + "%%'"  //����Ȩ�޹�������
				 + " and defaultoperator is null "
				 + " and lwmission.missionprop5 in ( '01','07','08','09','10')"
				 + " order by lwmission.missionprop1";
				 ;*/
var sqlid1="ScanContInputSql1";
var mySql1=new SqlClass();
mySql1.setResourceName(sqlresourcename);
mySql1.setSqlId(sqlid1);
mySql1.addSubPara(fm.PrtNo.value);			 
mySql1.addSubPara(fm.InputDate.value);	
mySql1.addSubPara(fm.ManageCom.value);	
mySql1.addSubPara(comcode);	
strSQL =  mySql1.getString();
	}
	if(type == "2")
	{
		if(comcode=="%")
		{/*
			strSQL = "select lwmission.missionprop1,lwmission.missionprop3,lwmission.missionprop2,lwmission.missionid,lwmission.submissionid,lwmission.activityid,lwmission.missionprop5 from lwmission where 1=1 "
				 + " and activityid = '0000002099' "
				 + " and processid = '0000000004'"
				 + getWherePart('missionprop1','PrtNo',strOperate)
				 + getWherePart('missionprop2','InputDate',strOperate)
				 + getWherePart('missionprop3','ManageCom',strOperate)
				 //+ getWherePart('missionprop4','ScanOperator')
				 //+ " and LWMission.missionprop3 like '" + comcode + "%%'"  //����Ȩ�޹�������
				 + " and defaultoperator is null "
				 + " order by lwmission.missionprop1";
				 ;
				 */
var sqlid2="ScanContInputSql2";
var mySql2=new SqlClass();
mySql2.setResourceName(sqlresourcename);
mySql2.setSqlId(sqlid2);
mySql2.addSubPara(fm.PrtNo.value);			 
mySql2.addSubPara(fm.InputDate.value);	
mySql2.addSubPara(fm.ManageCom.value);	
strSQL =  mySql2.getString();	 
				 
		}else{
			/*
		strSQL = "select lwmission.missionprop1,lwmission.missionprop3,lwmission.missionprop2,lwmission.missionid,lwmission.submissionid,lwmission.activityid,lwmission.missionprop5 from lwmission where 1=1 "
				 + " and activityid = '0000002099' "
				 + " and processid = '0000000004'"
				 + getWherePart('missionprop1','PrtNo',strOperate)
				 + getWherePart('missionprop2','InputDate',strOperate)
				 + getWherePart('missionprop3','ManageCom',strOperate)
				 //+ getWherePart('missionprop4','ScanOperator')
				 + " and LWMission.missionprop3 like '" + comcode + "%%'"  //����Ȩ�޹�������
				 + " and defaultoperator is null "
				 + " order by lwmission.missionprop1";
				 ;
				 
				*/ 
				 
var sqlid3="ScanContInputSql3";
var mySql3=new SqlClass();
mySql3.setResourceName(sqlresourcename);
mySql3.setSqlId(sqlid3);
mySql3.addSubPara(fm.PrtNo.value);			 
mySql3.addSubPara(fm.InputDate.value);	
mySql3.addSubPara(fm.ManageCom.value);	
mySql3.addSubPara(comcode);
strSQL =  mySql3.getString();	 
				}
				 	 	
	}
	
	turnPage.queryModal(strSQL, GrpGrid);
	return true;
}


function easyQueryClickSelf()
{
	// ��ʼ�����
	initSelfGrpGrid();

	// ��дSQL���
	var strSQL = "";
	if(type =="1")
	{
		/*
		strSQL = "select lwmission.missionprop1,lwmission.missionprop3,lwmission.missionprop2,lwmission.missionid,lwmission.submissionid,lwmission.activityid,lwmission.missionprop5 from lwmission where 1=1 "
				 + " and activityid = '0000001099' "
				 + " and processid = '0000000003'"
				 + " and defaultoperator ='" + operator + "'"
				 + " and LWMission.missionprop3 like '" + comcode + "%%'" //����Ȩ�޹�������
				 + " and lwmission.missionprop5 in ( '01','07','08','09','10')"
				 + " order by lwmission.missionprop1";	 
*/
var sqlid4="ScanContInputSql4";
var mySql4=new SqlClass();
mySql4.setResourceName(sqlresourcename);
mySql4.setSqlId(sqlid4);
mySql4.addSubPara(operator);			 
mySql4.addSubPara(comcode);	
strSQL =  mySql4.getString();	 
	
	}
	if(type == "2")
	{/*
		strSQL = "select lwmission.missionprop1,lwmission.missionprop3,lwmission.missionprop2,lwmission.missionid,lwmission.submissionid,lwmission.activityid,lwmission.missionprop5 from lwmission where 1=1 "
				 + " and activityid = '0000002099' "
				 + " and processid = '0000000004'"
				 + " and defaultoperator ='" + operator + "'"
				 + " and LWMission.missionprop3 like '" + comcode + "%%'"  //����Ȩ�޹�������
				 + " order by lwmission.missionprop1";
	*/
var sqlid5="ScanContInputSql5";
var mySql5=new SqlClass();
mySql5.setResourceName(sqlresourcename);
mySql5.setSqlId(sqlid5);
mySql5.addSubPara(operator);			 
mySql5.addSubPara(comcode);	
strSQL =  mySql5.getString();	 
	
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
    var	ManageCom = GrpGrid.getRowColData(checkFlag, 2);
    var MissionID =GrpGrid.getRowColData(checkFlag, 4);
    var SubMissionID =GrpGrid.getRowColData(checkFlag, 5);

    var SubType =GrpGrid.getRowColData(checkFlag, 7);
    
    var urlStr = "./ProposalScanApply.jsp?prtNo=" + prtNo + "&operator=" + operator + "&state=" + state;
    var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;dialogWidth:400px;dialogHeight:200px;resizable=1";
    //�����ӡˢ��
    var strReturn = "1";
    //var strReturn = window.showModalDialog(urlStr, "", sFeatures);

    //��ɨ���¼�����
    //sFeatures = "";
    //sFeatures = "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1";
    if (strReturn == "1") 
    	if(type=="1")
    	{
    //		sql = "select missionprop5 from lwmission where activityid = '0000001099' and missionprop1 = '"+prtNo+"'";
		
var sqlid6="ScanContInputSql6";
var mySql6=new SqlClass();
mySql6.setResourceName(sqlresourcename);
mySql6.setSqlId(sqlid6);
mySql6.addSubPara(prtNo);			 	
		
			var brr = easyExecSql(mySql6.getString());

    		if(!brr)
    		{
    			alert("ɨ���ϴ�����");
    			return;
    		}
    		if(brr[0][0]=='01')//����¼��
    		{
    			window.open("./ContInputScanMain.jsp?ScanFlag=1&prtNo="+prtNo+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&SubType="+SubType+"&scantype=scan", "", sFeatures);        
    		}else if(brr[0][0]=='05')//����¼��
    		{
    			window.open("./BankContInputScanMain.jsp?ScanFlag=1&prtNo="+prtNo+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&scantype=scan", "", sFeatures);        
    		}
 			}
 			if(type=="2")
 			{
 				//var tsql="select subtype From es_doc_main where doccode='"+prtNo+"' order by subtype";
 				//var crr = easyExecSql(tsql);
 				//var ttype="";
 				//alert(crr);
 				//if(crr[0][0]=="1000")
 				//{
 				// ttype="TB1002";
 				//}else{
 				// ttype="TB1003";
 				//}
 				//alert("SubType12:"+SubType);
 				window.open("./GrpContInputScanMain.jsp?ScanFlag=1&SubType="+SubType+"&prtNo="+prtNo+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&scantype=scan", "", sFeatures); 
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
  //	alert("ӡˢ��:"+prtNo);
    var	ManageCom = SelfGrpGrid.getRowColData(checkFlag, 2);
    var MissionID =SelfGrpGrid.getRowColData(checkFlag, 4);
    var SubMissionID =SelfGrpGrid.getRowColData(checkFlag, 5);
    
    var SubType =SelfGrpGrid.getRowColData(checkFlag, 7);
    
    var ActivityID = SelfGrpGrid.getRowColData(checkFlag, 6);

	  var NoType = type;

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
    //		sql = "select missionprop5 from lwmission where activityid = '0000001099' and missionprop1 = '"+prtNo+"'";

var sqlid7="ScanContInputSql7";
var mySql7=new SqlClass();
mySql7.setResourceName(sqlresourcename);
mySql7.setSqlId(sqlid7);
mySql7.addSubPara(prtNo);

			var brr = easyExecSql(mySql7.getString() );

    		if(!brr)
    		{
    			alert("ɨ���ϴ�����");
    			return;
    		}
    		//alert(brr[0][0]);
    		if(brr[0][0]=='01')//����¼��
    		{  //alert(111);
    			window.open("./ContInputScanMain.jsp?ScanFlag=1&prtNo="+prtNo+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType+"&SubType="+SubType+"&scantype=scan", "", sFeatures);        
    		}else if(brr[0][0]=='05')//����¼��
    		{
    			window.open("./BankContInputScanMain.jsp?ScanFlag=1&prtNo="+prtNo+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&scantype=scan", "", sFeatures);        
    		}
 			}
 			
 			if(type=="2")
 			{
 				//var tsql="select subtype From es_doc_main where doccode='"+prtNo+"' order by subtype";
 				//var crr = easyExecSql(tsql);
 				//var ttype="";
 				//alert(crr);
 				//ttype="TB1002";
 				//alert("SubType:"+SubType);
 				window.open("./GrpContInputScanMain.jsp?ScanFlag=1&SubType="+SubType+"&prtNo="+prtNo+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&scantype=scan", "", sFeatures); 
 			}
 }
}

function ApplyUW()
{
	//ֻ������λ��վ����
	var tLine=manageCom.length;
	//if(tLine<4)
	//{
	//	alert("ֻ������λ����Ļ�������ϵͳ������");
	//	return false;
	//}

	var selno = GrpGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ�����Ͷ������");
	      return;
	}

	fm.MissionID.value = GrpGrid.getRowColData(selno, 4);
	fm.SubMissionID.value = GrpGrid.getRowColData(selno, 5);
	fm.ActivityID.value = GrpGrid.getRowColData(selno, 6);
	fm.PrtNo1.value = GrpGrid.getRowColData(selno,1);
	
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
	GoToInput();
}

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
	}
	
  // ˢ�²�ѯ���
	easyQueryClick();
	easyQueryClickSelf();		
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
	var newWindow = OpenWindowNew("../uwgrp/WorkFlowNotePadFrame.jsp?Interface=../uwgrp/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");	
		
}
