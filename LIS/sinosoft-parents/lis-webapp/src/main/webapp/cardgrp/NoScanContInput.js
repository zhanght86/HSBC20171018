//�������ƣ�ScanContInput.js
//�����ܣ���������Լɨ�������¼��
//�������ڣ�2004-12-22 11:10:36
//������  ��HYQ
//���¼�¼��  ������    ��������     ����ԭ��/����

var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var k = 0;

/*********************************************************************
 *  ִ������Լɨ��ġ���ʼ¼�롱
 *  ����:������ɨ��¼��ҳ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function ApplyInput()
{
	cPrtNo = fm.PrtNo.value;
	cManageCom = fm.ManageCom.value;

	if(cPrtNo == "")
	{
		alert("��¼��Ͷ�����ţ�");
		return;
	}
	if(cManageCom == "")
	{
		alert("��¼����������");
		return;		
	}
	//����У��
  if( verifyInput2() == false ) return false;
	
	indx = cManageCom.indexOf(comcode);
	//alert(comcode);
	if(indx != 0 && comcode!="%"){
		alert("��û������˹��������Ȩ��");
		return;
	}
	if(type=='2')//���ڼ��屣��������
	{
		if (GrpbeforeSubmit() == false)
		{
		    alert("�Ѵ��ڸ�Ͷ�����ţ���ѡ������ֵ!");
		    return false;		
		}
		//var tsql="select * from ljtempfee where othernotype = '1' and otherno="+cPrtNo;
 	  //var crr = easyExecSql(tsql);
 		//if(!crr)
 		//{
 		//  alert("δ���ѱ�����������!");
 		//  fm.PrtNo.value=""; 
 		//	return; 
 		//} 		
	}
	else if(type=='1')//���ڸ��˱���������
	{
		if (beforeSubmit() == false)
		{
		    alert("�Ѵ��ڸ�Ͷ�����ţ���ѡ������ֵ!");
		    return false;		
		}	
  	
	}else if(type=='5')
	{ 
		if (TempbeforeSubmit() == false)
		{
		    alert("�Ѵ��ڸ�Ͷ�����ţ���ѡ������ֵ!");
		    return false;		
		}			 
	}
	lockScreen('lkscreen');  
	document.getElementById("fm").submit();
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
	unlockScreen('lkscreen');  
	easyQueryClick1();
}

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
//alert("operator=="+operator);
	// ��дSQL���
	var strSQL = "";
	var strOperate="like";
	lockScreen('lkscreen');
	//alert(type);
	//alert(comcode);
	if(type=='1')
	{
	strSQL = "select lwmission.missionprop1,lwmission.missionprop3,lwmission.missionprop2,lwmission.missionprop4,lwmission.missionid,lwmission.submissionid,lwmission.activityid  from lwmission where 1=1 "
//				 + " and activityid = '0000001098' "
				 + " and activityid  in (select activityid from lwactivity  where functionid ='10010001')"
				 + " and missionprop5 = 'TB01'"
//				 + " and processid = '0000000003'"
				 + " and trim(defaultoperator) =trim('" + operator + "')"
				 + getWherePart('missionprop1','PrtNo',strOperate)
				 + getWherePart('missionprop2','InputDate',strOperate)
				 + getWherePart('missionprop3','ManageCom',strOperate)
				 + getWherePart('missionprop4','Operator',strOperate)
				 //+ " and lwmission.MissionProp3 like '" + trim(comcode) + "%'"  //����Ȩ�޹�������
				 + " order by lwmission.missionprop1"
				 ;	 
	}
	else if (type=='2')
 	{
 		if(comcode=="%")
 		{
 			strSQL = "select lwmission.missionprop1,lwmission.missionprop3,lwmission.missionprop2,lwmission.missionprop4,lwmission.missionid,lwmission.submissionid,lwmission.activityid  from lwmission where 1=1 "
				 + " and activityid = '0000011098' "
				 + " and processid = '0000000011'"
				 + " and trim(defaultoperator)=trim('" + operator + "')"
				 + getWherePart('missionprop1','PrtNo',strOperate)
				 + getWherePart('missionprop2','InputDate',strOperate)
				 + getWherePart('missionprop3','ManageCom',strOperate)
				 + getWherePart('missionprop4','Operator',strOperate)		 
				 + " order by lwmission.missionprop1"
				 ;	 
 		}else
 			{
 	strSQL = "select lwmission.missionprop1,lwmission.missionprop3,lwmission.missionprop2,lwmission.missionprop4,lwmission.missionid,lwmission.submissionid,lwmission.activityid  from lwmission where 1=1 "
				 + " and activityid = '0000011098' "
				 + " and processid = '0000000011'"
				 + " and trim(defaultoperator)=trim('" + operator + "')"
				 + getWherePart('missionprop1','PrtNo',strOperate)
				 + getWherePart('missionprop2','InputDate',strOperate)
				 + getWherePart('missionprop3','ManageCom',strOperate)
				 + getWherePart('missionprop4','Operator',strOperate)	
				  + " and lwmission.MissionProp3 like '" + trim(comcode) + "%%'"  //����Ȩ�޹�������		 
				 + " order by lwmission.missionprop1"
				 ;	 
				 
				}
				 //alert(strSQL);
	}
	else if(type=='5')
	{
 	strSQL = "select lwmission.missionprop1,lwmission.missionprop3,lwmission.missionprop2,lwmission.missionprop4,lwmission.missionid,lwmission.submissionid,lwmission.activityid  from lwmission where 1=1 "
				 + " and activityid = '0000001061' "
				 + " and processid = '0000000003'"
				 + " and defaultoperator ='" + operator + "'"
				 + getWherePart('missionprop1','PrtNo',strOperate)
				 + getWherePart('missionprop2','InputDate',strOperate)
				 + getWherePart('missionprop3','ManageCom',strOperate)
				 + getWherePart('missionprop4','Operator',strOperate)
				 + " order by lwmission.missionprop1"
				 ;	 			
	}

	turnPage1.queryModal(strSQL, GrpGrid,1,1);
	unlockScreen('lkscreen');  
	//GrpGrid.setPageMark(turnPage1);
	//alert("ok");
	return true;
}

function easyQueryClick1()
{
	// ��ʼ�����
	initGrpGrid();
//alert("operator=="+operator);
	// ��дSQL���
	var strSQL = "";
	var strOperate="like";
	if(type=='1')
	{
	strSQL = "select lwmission.missionprop1,lwmission.missionprop3,lwmission.missionprop2,lwmission.missionprop4,lwmission.missionid,lwmission.submissionid,lwmission.activityid  from lwmission where 1=1 "
//				 + " and activityid = '0000001098' "
//				 + " and processid = '0000000003'"
		         + " and activityid  in (select activityid from lwactivity  where functionid ='10010001') "
				 + " and trim(defaultoperator) =trim('" + operator + "')"
				 + getWherePart('missionprop1','PrtNo',strOperate)
				
				 
				 + getWherePart('missionprop4','Operator',strOperate)
				 //+ " and lwmission.MissionProp3 like '" + trim(comcode) + "%%'"  //����Ȩ�޹�������
				 + " order by lwmission.missionprop1"
				 ;	 
	}
	else if (type=='2')
 	{
 	strSQL = "select lwmission.missionprop1,lwmission.missionprop3,lwmission.missionprop2,lwmission.missionprop4,lwmission.missionid,lwmission.submissionid,lwmission.activityid  from lwmission where 1=1 "
				 + " and activityid = '0000011098' "
				 + " and processid = '0000000011'"
//				 + " and trim(defaultoperator)=trim('" + operator + "')"
				 + getWherePart('missionprop1','PrtNo',strOperate)
				 
				
				 + getWherePart('missionprop4','Operator',strOperate)
//				 + " and lwmission.MissionProp3 like '" + trim(comcode) + "%'"  //����Ȩ�޹�������
				 + " order by lwmission.missionprop1"
				 ;	 
				 
				 //alert(	strSQL);	 
	}
	else if(type=='5')
	{
 	strSQL = "select lwmission.missionprop1,lwmission.missionprop3,lwmission.missionprop2,lwmission.missionprop4,lwmission.missionid,lwmission.submissionid,lwmission.activityid  from lwmission where 1=1 "
				 + " and activityid = '0000001061' "
				 + " and processid = '0000000003'"
				 + " and defaultoperator ='" + operator + "'"
				 + getWherePart('missionprop1','PrtNo',strOperate)
				
				 + getWherePart('missionprop3','ManageCom',strOperate)
				 + getWherePart('missionprop4','Operator',strOperate)
				 + " order by lwmission.missionprop1"
				 ;	 			
	}

	turnPage1.queryModal(strSQL, GrpGrid);
	return true;
}

function GoToInput()
{
  var i = 0;
  var checkFlag = 0;
  var state = "0";
  
  for (i=0; i<GrpGrid.mulLineCount; i++) {
    if (GrpGrid.getSelNo(i)) { 
      checkFlag = GrpGrid.getSelNo();
      break;
    }
  }
  
  if (checkFlag) { 
    var	prtNo = GrpGrid.getRowColData(checkFlag - 1, 1); 	
    var ManageCom = GrpGrid.getRowColData(checkFlag - 1, 2); 
    var MissionID = GrpGrid.getRowColData(checkFlag - 1, 5);
    var SubMissionID = GrpGrid.getRowColData(checkFlag - 1, 6);
  
    var ActivityID = GrpGrid.getRowColData(checkFlag - 1, 7);
	  var NoType = type;
	  
    //var urlStr = "./ProposalScanApply.jsp?prtNo=" + prtNo + "&operator=" + operator + "&state=" + state;
    //var sFeatures = "status:no;help:0;close:0;dialogWidth:400px;dialogHeight:200px;resizable=1";
    //�����Ͷ������
    //var strReturn = window.showModalDialog(urlStr, "", sFeatures);
    var strReturn="1";
    //��ɨ���¼�����
	//sFeatures="";
    sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes";
    //sFeatures = "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1";
    if (strReturn == "1") 
     if(type=='1')
     {    		
     	  sql = "select missionprop5 from lwmission where activityid = '0000001098' and missionprop1 = '"+prtNo+"'";
    		turnPage.strQueryResult  = easyQueryVer3(sql, 1, 0, 1);
    		if(!turnPage.strQueryResult)
    		{
    			alert("ɨ���ϴ�����");
    			return;
    		}
    		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);     
         if(turnPage.arrDataCacheSet[0][0]=='05')//����¼��
    		{
    			window.open("./BankContInputNoScanMain.jsp?ScanFlag=1&LoadFlag=1&prtNo="+prtNo+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&scantype=scan", "", sFeatures);        
    		}else
    		{
    			window.open("./ContInputNoScanMain.jsp?ScanFlag=0&LoadFlag=1&prtNo="+prtNo+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType+"&scantype=scan", "", sFeatures);        
    		}
    }
    else if(type=='2')
    {
    	//var tsql="select * from ljtempfee where othernotype = '1' and otherno="+prtNo;
 	    //var crr = easyExecSql(tsql);
 		  //if(!crr)
 		  //{
 		  //alert("δ���ѱ������ܽ���¼��!"); 
 			//return; 
 	  	//} 
 	  	
     	window.open("./ContPolInputNoScanMain.jsp?ScanFlag=0&prtNo="+prtNo+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType+"&scantype=scan", "", sFeatures);
    }	
    else if(type=='5')
    {
     	window.open("./FirstTrialMain.jsp?prtNo="+prtNo+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID, "", sFeatures);
    }	
  }
  else {
    alert("����ѡ��һ��������Ϣ��"); 
  }
  
}
function beforeSubmit()
{
	var strSQL = "";
	strSQL = "select missionprop1 from lwmission where 1=1 "
//				 + " and activityid = '0000001098' "
//				 + " and processid = '0000000003'" 
		         + " and activityid  in (select activityid from lwactivity  where functionid ='10010001') "
				 + " and missionprop1='"+fm.PrtNo.value+"'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
	//�ж��Ƿ��ѯ�ɹ�
	if (turnPage.strQueryResult) {
	    return false;
	}
	
	strSQL = "select missionprop1 from lwmission where 1=1 "
				 + " and activityid = '0000001062' "
				 + " and processid = '0000000003'" 
				 + " and missionprop1='"+fm.PrtNo.value+"'";	
	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
  if(turnPage.strQueryResult)
  {
  	 return true;
  }
   
	strSQL = "select prtno from lccont where prtno = '" + fm.PrtNo.value + "'"
			 "union select prtno from lbcont where prtno = '" + fm.PrtNo.value + "'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	//�ж��Ƿ��ѯ�ɹ�
	if (turnPage.strQueryResult) {
	    return false;
	}	   	
}
function GrpbeforeSubmit()
{
	var strSQL = "";
	strSQL = "select missionprop1 from lwmission where 1=1 "
				 + " and activityid = '0000011098' "
				 + " and processid = '0000000011'" 
				 + " and missionprop1='"+fm.PrtNo.value+"'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 
	//�ж��Ƿ��ѯ�ɹ�
	if (turnPage.strQueryResult) {
		  //alert('1');
	    return false;
	}

	strSQL = "select prtno from lcgrpcont where prtno = '" + fm.PrtNo.value + "'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 
	//�ж��Ƿ��ѯ�ɹ�
	if (turnPage.strQueryResult) {
		  //alert('2');
	    return false;
	}	   	
}
//����Ͷ������ʾ��ʽ
function initTr()
{
	 if(type=='1' || type=='2')
	 {
	 	 document.all['SubTitle'].style.display = '';
	 }
	 else if(type=='5')
	 {
	 	document.all['SubTitle'].style.display = 'none';
	 }
}
function TempbeforeSubmit()
{
	var strSQL = "";
	strSQL = "select missionprop1 from lwmission where 1=1 "
				 + " and activityid = '0000001060' "
				 + " and processid = '0000000003'" 
				 + " and missionprop1='"+fm.PrtNo.value+"'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
	//�ж��Ƿ��ѯ�ɹ�
	if (turnPage.strQueryResult) {
	    return false;
	}

	strSQL = "select prtno from lccont where prtno = '" + fm.PrtNo.value + "'"
			 "union select prtno from lbcont where prtno = '" + fm.PrtNo.value + "'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
	//�ж��Ƿ��ѯ�ɹ�
	if (turnPage.strQueryResult) {
	    return false;
	}	 	 
}

function showNotePad()
{

	var selno = GrpGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��һ������");
	      return;
	}
	
	var MissionID = GrpGrid.getRowColData(selno, 5);
	var SubMissionID = GrpGrid.getRowColData(selno, 6);
	var ActivityID = GrpGrid.getRowColData(selno, 7);
	var PrtNo = GrpGrid.getRowColData(selno, 1);
	var NoType = type;
	if(PrtNo == null || PrtNo == "")
	{
		alert("Ͷ������Ϊ�գ�");
		return;
	}			
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ PrtNo + "&NoType="+ NoType;
	var newWindow = OpenWindowNew("../uwgrp/WorkFlowNotePadFrame.jsp?Interface=../uwgrp/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");	
		
}