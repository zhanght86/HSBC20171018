
//�������ƣ�NoScanBankContInput.js
//�����ܣ���������Լ��ɨ�豣��¼��
//�������ڣ�2005-07-18 11:10:36
//������  ��ccvip 
//���¼�¼��  ������    ��������     ����ԭ��/����

var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var k = 0;
 var sFeatures1 = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
 
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
	if(indx != 0){
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
	else if(type=='1'||type=='3')//���ڸ��˱���������
	{
		var PrtNo = fm.PrtNo.value;
		var tSplitPrtNo = PrtNo.substring(0,4);
		if(!(tSplitPrtNo=="8635"||tSplitPrtNo=="8625"||tSplitPrtNo=="8615"))
		{
			alert("ӡˢ�Ų���������ӡˢ�Ÿ�ʽ��"); 
			return false;
		}
		if (beforeSubmit() == false)
		{
		    alert("�Ѵ��ڸ�Ͷ�����ţ���ѡ������ֵ!");
		    return false;	 	
		}	
  	
	} 
	else if(type=='5')
	{ 
		if (TempbeforeSubmit() == false)
		{
		    alert("�Ѵ��ڸ�Ͷ�����ţ���ѡ������ֵ!");
		    return false;		
		}			 
	}
	document.getElementById("fm").submit();
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
	
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
 	//������Ĳ�ѯ��������У��
	if( verifyInput2() == false ) 
	{
		return false;
	}
	// ��дSQL���
	var strSQL = "";
	var strOperate="like";
	if(type=='1')
	{
		
		var sqlid1="NoScanBankContInputSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("app.NoScanBankContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.InputDate.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
		
		mySql1.addSubPara(fm.Operator.value);//ָ������Ĳ���
		mySql1.addSubPara( trim(comcode) );//ָ������Ĳ���
	    strSQL=mySql1.getString();	
		
//	strSQL = "select lwmission.missionprop1,lwmission.missionprop3,lwmission.missionprop2,lwmission.missionprop4,lwmission.missionid,lwmission.submissionid,lwmission.activityid  from lwmission where 1=1 "
//				 + " and activityid = '0000001098' "
//				 + " and processid = '0000000003'"
////				 + " and trim(defaultoperator) =trim('" + operator + "')"
//				 + getWherePart('missionprop1','PrtNo',strOperate)
//				 + getWherePart('missionprop2','InputDate',strOperate)
//				 + getWherePart('missionprop3','ManageCom',strOperate)
//				 + getWherePart('missionprop4','Operator',strOperate)
//				 + " and lwmission.MissionProp3 like '" + trim(comcode) + "%%'"  //����Ȩ�޹�������
//				 + " order by lwmission.missionprop1"
//				 ;	 
	}
	else if (type=='2')
 	{
		
		var sqlid2="NoScanBankContInputSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("app.NoScanBankContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.InputDate.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
		
		mySql2.addSubPara(fm.Operator.value);//ָ������Ĳ���
		mySql2.addSubPara( trim(comcode) );//ָ������Ĳ���
	    strSQL=mySql2.getString();	
		
// 	strSQL = "select lwmission.missionprop1,lwmission.missionprop3,lwmission.missionprop2,lwmission.missionprop4,lwmission.missionid,lwmission.submissionid,lwmission.activityid  from lwmission where 1=1 "
//				 + " and activityid = '0000002098' "
//				 + " and processid = '0000000004'"
////				 + " and trim(defaultoperator)=trim('" + operator + "')"
//				 + getWherePart('missionprop1','PrtNo',strOperate)
//				 + getWherePart('missionprop2','InputDate',strOperate)
//				 + getWherePart('missionprop3','ManageCom',strOperate)
//				 + getWherePart('missionprop4','Operator',strOperate)
//				 + " and lwmission.MissionProp3 like '" + trim(comcode) + "%%'"  //����Ȩ�޹�������
//				 + " order by lwmission.missionprop1"
//				 ;	 		 
	}
//����¼��
  else if(type=='3')
  {
  	var tempfeeSQL="";//�����ƴ�� �������� ��ѯ������
	if(trim(fm.PayDate.value)!="")
	{
		tempfeeSQL=" and exists (select 'X' from ljtempfee where tempfeetype='1' and confdate is null and otherno =lwmission.missionprop1 and paydate='"+fm.PayDate.value+"')";
	}
	
		var sqlid3="NoScanBankContInputSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("app.NoScanBankContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
		mySql3.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
		mySql3.addSubPara(fm.InputDate.value);//ָ������Ĳ���
		mySql3.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
		
		mySql3.addSubPara(fm.Operator.value);//ָ������Ĳ���
		mySql3.addSubPara( trim(comcode) );//ָ������Ĳ���
		mySql3.addSubPara( tempfeeSQL);//ָ������Ĳ���
	    strSQL=mySql3.getString();	
	
//  	strSQL = "select lwmission.missionprop1,lwmission.missionprop3,lwmission.missionprop2,lwmission.missionprop4,lwmission.missionid,lwmission.submissionid,lwmission.activityid  from lwmission where 1=1 "
//				 + " and activityid = '0000001098' "
//				 + " and processid = '0000000003'"
//				 + " and MissionProp5='TB05'" 
////				 + " and trim(defaultoperator) =trim('" + operator + "')"
//				 + getWherePart('missionprop1','PrtNo',strOperate)
//				 + getWherePart('missionprop2','InputDate',strOperate)
//				 + getWherePart('missionprop3','ManageCom',strOperate)
//				 + getWherePart('missionprop4','Operator',strOperate)
//				 + " and lwmission.MissionProp3 like '" + trim(comcode) + "%%'"  //����Ȩ�޹�������
//				 + tempfeeSQL
//				 + " order by lwmission.missionprop1" ;	 
  }  
	else if(type=='5')
	{
		
		var sqlid4="NoScanBankContInputSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName("app.NoScanBankContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
		mySql4.addSubPara(operator);//ָ������Ĳ���
		mySql4.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
		mySql4.addSubPara(fm.InputDate.value);//ָ������Ĳ���
		mySql4.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
		
		mySql4.addSubPara(fm.Operator.value);//ָ������Ĳ���
		mySql4.addSubPara( trim(comcode) );//ָ������Ĳ���
	    strSQL=mySql4.getString();	
		
// 	strSQL = "select lwmission.missionprop1,lwmission.missionprop3,lwmission.missionprop2,lwmission.missionprop4,lwmission.missionid,lwmission.submissionid,lwmission.activityid  from lwmission where 1=1 "
//				 + " and activityid = '0000001061' "
//				 + " and processid = '0000000003'"
//				 + " and defaultoperator ='" + operator + "'"
//				 + getWherePart('missionprop1','PrtNo',strOperate)
//				 + getWherePart('missionprop2','InputDate',strOperate)
//				 + getWherePart('missionprop3','ManageCom',strOperate)
//				 + getWherePart('missionprop4','Operator',strOperate)
//				 + " order by lwmission.missionprop1"
//				 ;	 			
	}

	turnPage1.queryModal(strSQL, GrpGrid);
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
	if(type=='3')
	{
		
		var sqlid5="NoScanBankContInputSql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName("app.NoScanBankContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
		mySql5.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
		mySql5.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
		
		mySql5.addSubPara(fm.Operator.value);//ָ������Ĳ���
		mySql5.addSubPara( trim(comcode) );//ָ������Ĳ���
	    strSQL=mySql5.getString();	
		
//	strSQL = "select lwmission.missionprop1,lwmission.missionprop3,lwmission.missionprop2,lwmission.missionprop4,lwmission.missionid,lwmission.submissionid,lwmission.activityid  from lwmission where 1=1 "
//				 + " and activityid = '0000001098' "
//				 + " and processid = '0000000003'"
//				 + " and missionprop5 = 'TB05'"  
////				 + " and trim(defaultoperator) =trim('" + operator + "')"
//				 + getWherePart('missionprop1','PrtNo',strOperate)
//				
//				 + getWherePart('missionprop3','ManageCom',strOperate)
//				 + getWherePart('missionprop4','Operator',strOperate)
//				 + " and lwmission.MissionProp3 like '" + trim(comcode) + "%%'"  //����Ȩ�޹�������
//				 + " order by lwmission.missionprop1"
//				 ;	  
	}
	else if (type=='2')
 	{
		
		var sqlid6="NoScanBankContInputSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName("app.NoScanBankContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
		mySql6.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
		mySql6.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
		
		mySql6.addSubPara(fm.Operator.value);//ָ������Ĳ���
		mySql6.addSubPara( trim(comcode) );//ָ������Ĳ���
	    strSQL=mySql6.getString();	
		
// 	strSQL = "select lwmission.missionprop1,lwmission.missionprop3,lwmission.missionprop2,lwmission.missionprop4,lwmission.missionid,lwmission.submissionid,lwmission.activityid  from lwmission where 1=1 "
//				 + " and activityid = '0000002098' "
//				 + " and processid = '0000000004'"
//				 + getWherePart('missionprop1','PrtNo',strOperate)
//				 
//				 + getWherePart('missionprop3','ManageCom',strOperate)
//				 + getWherePart('missionprop4','Operator',strOperate)
//				 + " and lwmission.MissionProp3 like '" + trim(comcode) + "%%'"  //����Ȩ�޹�������
//				 + " order by lwmission.missionprop1"
//				 ;	 		 
	}
	else if(type=='5')
	{
		
		var sqlid7="NoScanBankContInputSql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName("app.NoScanBankContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql7.setSqlId(sqlid7);//ָ��ʹ�õ�Sql��id
		mySql7.addSubPara(operator);//ָ������Ĳ���
		mySql7.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
		mySql7.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
		
		mySql7.addSubPara(fm.Operator.value);//ָ������Ĳ���
	    strSQL=mySql7.getString();	
		
// 	strSQL = "select lwmission.missionprop1,lwmission.missionprop3,lwmission.missionprop2,lwmission.missionprop4,lwmission.missionid,lwmission.submissionid,lwmission.activityid  from lwmission where 1=1 "
//				 + " and activityid = '0000001061' "
//				 + " and processid = '0000000003'"
//				 + " and defaultoperator ='" + operator + "'"
//				 + getWherePart('missionprop1','PrtNo',strOperate)
//				
//				 + getWherePart('missionprop3','ManageCom',strOperate)
//				 + getWherePart('missionprop4','Operator',strOperate)
//				 + " order by lwmission.missionprop1"
//				 ;	 			
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
  //alert(checkFlag);
  if (checkFlag) {
  	//alert("ok"); 
    var	prtNo = GrpGrid.getRowColData(checkFlag - 1, 1); 	
    var ManageCom = GrpGrid.getRowColData(checkFlag - 1, 2); 
    var MissionID = GrpGrid.getRowColData(checkFlag - 1, 5);
    var SubMissionID = GrpGrid.getRowColData(checkFlag - 1, 6);
  
    var ActivityID = GrpGrid.getRowColData(checkFlag - 1, 7);
    //alert(ActivityID); 
	  var NoType = "1"; 
	  
    //var urlStr = "./ProposalScanApply.jsp?prtNo=" + prtNo + "&operator=" + operator + "&state=" + state;
    //var sFeatures = "status:no;help:0;close:0;dialogWidth:400px;dialogHeight:200px;resizable=1";
    //�����Ͷ������
    //var strReturn = window.showModalDialog(urlStr, "", sFeatures);
    var strReturn="1";
    //����ɨ��¼����� 
    sFeatures = "";
    //sFeatures = "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;";
    if (strReturn == "1") 
     if(type=='3') 
     {    		 
     	  sql = "select missionprop5 from lwmission where activityid = '0000001098' and missionprop1 = '"+prtNo+"'";
    		turnPage.strQueryResult  = easyQueryVer3(sql, 1, 0, 1);
    		if(!turnPage.strQueryResult)
    		{
    			alert("��ѯ��Ͷ��������"); 
    			return;
    		}
    		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);     
         if(turnPage.arrDataCacheSet[0][0]=='TB05')//����¼��
    		{
    			window.open("./BankContInputNoScanMain.jsp?ScanFlag=0&LoadFlag=1&prtNo="+prtNo+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType+"&scantype=scan", "", sFeatures+sFeatures1);        
    		}else
    		{
    			window.open("./ContInputNoScanMain.jsp?    ScanFlag=0&LoadFlag=1&prtNo="+prtNo+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType+"&scantype=scan", "", sFeatures+sFeatures1);        
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
 	  	
     	window.open("./ContPolInputNoScanMain.jsp?ScanFlag=0&prtNo="+prtNo+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType+"&scantype=scan", "", sFeatures+sFeatures1);
    }	
    else if(type=='5')
    {
     	window.open("./FirstTrialMain.jsp?prtNo="+prtNo+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID, "", sFeatures+sFeatures1);
    }	
  }
  else {
    alert("����ѡ��һ��������Ϣ��"); 
  }
  
}
function beforeSubmit()
{
		var strSQL = "";
	
		var sqlid8="NoScanBankContInputSql8";
		var mySql8=new SqlClass();
		mySql8.setResourceName("app.NoScanBankContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql8.setSqlId(sqlid8);//ָ��ʹ�õ�Sql��id
		mySql8.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
	    strSQL=mySql8.getString();	
		
//	strSQL = "select missionprop1 from lwmission where 1=1 "
//				 + " and activityid = '0000001098' "
//				 + " and processid = '0000000003'" 
//				 + " and missionprop1='"+fm.PrtNo.value+"'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
	//�ж��Ƿ��ѯ�ɹ�
	if (turnPage.strQueryResult) {
	    return false;
	}
	
			var sqlid9="NoScanBankContInputSql9";
		var mySql9=new SqlClass();
		mySql9.setResourceName("app.NoScanBankContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql9.setSqlId(sqlid9);//ָ��ʹ�õ�Sql��id
		mySql9.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
	    strSQL=mySql9.getString();	
	
//	strSQL = "select missionprop1 from lwmission where 1=1 "
//				 + " and activityid = '0000001062' "
//				 + " and processid = '0000000003'" 
//				 + " and missionprop1='"+fm.PrtNo.value+"'";	
	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
  if(turnPage.strQueryResult)
  {
  	 return true;
  }
   
   		var sqlid10="NoScanBankContInputSql10";
		var mySql10=new SqlClass();
		mySql10.setResourceName("app.NoScanBankContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql10.setSqlId(sqlid10);//ָ��ʹ�õ�Sql��id
		mySql10.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
		mySql10.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
	    strSQL=mySql10.getString();	
   
//	strSQL = "select prtno from lccont where prtno = '" + fm.PrtNo.value + "'"
//			 "union select prtno from lbcont where prtno = '" + fm.PrtNo.value + "'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	//�ж��Ƿ��ѯ�ɹ�
	if (turnPage.strQueryResult) {
	    return false;
	}	   	
}
function GrpbeforeSubmit()
{
	var strSQL = "";
	
	   	var sqlid11="NoScanBankContInputSql11";
		var mySql11=new SqlClass();
		mySql11.setResourceName("app.NoScanBankContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql11.setSqlId(sqlid11);//ָ��ʹ�õ�Sql��id
		mySql11.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
	    strSQL=mySql11.getString();	
	
//	strSQL = "select missionprop1 from lwmission where 1=1 "
//				 + " and activityid = '0000002098' "
//				 + " and processid = '0000000004'" 
//				 + " and missionprop1='"+fm.PrtNo.value+"'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 
	//�ж��Ƿ��ѯ�ɹ�
	if (turnPage.strQueryResult) {
		  //alert('1');
	    return false;
	}

	   	var sqlid12="NoScanBankContInputSql12";
		var mySql12=new SqlClass();
		mySql12.setResourceName("app.NoScanBankContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql12.setSqlId(sqlid12);//ָ��ʹ�õ�Sql��id
		mySql12.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
		mySql12.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
	    strSQL=mySql12.getString();	

//	strSQL = "select prtno from lccont where prtno = '" + fm.PrtNo.value + "'"
//			 "union select prtno from lbcont where prtno = '" + fm.PrtNo.value + "'";
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
	 	document.all['SubTitle'].style.display = "none";
	 }
}
function TempbeforeSubmit()
{
	var strSQL = "";
	
		var sqlid13="NoScanBankContInputSql13";
		var mySql13=new SqlClass();
		mySql13.setResourceName("app.NoScanBankContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql13.setSqlId(sqlid13);//ָ��ʹ�õ�Sql��id
		mySql13.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
	    strSQL=mySql13.getString();	
	
//	strSQL = "select missionprop1 from lwmission where 1=1 "
//				 + " and activityid = '0000001060' "
//				 + " and processid = '0000000003'" 
//				 + " and missionprop1='"+fm.PrtNo.value+"'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
	//�ж��Ƿ��ѯ�ɹ�
	if (turnPage.strQueryResult) {
	    return false;
	}

		var sqlid14="NoScanBankContInputSql14";
		var mySql14=new SqlClass();
		mySql14.setResourceName("app.NoScanBankContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql14.setSqlId(sqlid14);//ָ��ʹ�õ�Sql��id
		mySql14.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
		mySql14.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
	    strSQL=mySql14.getString();	

//	strSQL = "select prtno from lccont where prtno = '" + fm.PrtNo.value + "'"
//			 "union select prtno from lbcont where prtno = '" + fm.PrtNo.value + "'";
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
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");	
		
}