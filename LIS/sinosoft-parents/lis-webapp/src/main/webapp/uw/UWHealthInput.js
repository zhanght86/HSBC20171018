
//�������ƣ�UWHealthInput.js
//�����ܣ���������Լɨ�������¼��
//�������ڣ�
//������  ��
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
		alert("��¼��ӡˢ�ţ�");
		return;
	}
	if(cManageCom == "")
	{
		alert("��¼����������");
		return;		
	}
	indx = cManageCom.indexOf(comcode);
	if(indx != 0){
		alert("��û������˹��������Ȩ��");
		return;
	}
	if(type=='2')//���ڼ��屣��������
	{
		if (GrpbeforeSubmit() == false)
		{
		    alert("�Ѵ��ڸ�ӡˢ�ţ���ѡ������ֵ!");
		    return false;		
		}
	}
	else if(type=='1')//���ڸ��˱���������
	{
		if (beforeSubmit() == false)
		{
		    alert("�Ѵ��ڸ�ӡˢ�ţ���ѡ������ֵ!");
		    return false;		
		}	
  	
	}else if(type=='5')
	{ 
		if (TempbeforeSubmit() == false)
		{
		    alert("�Ѵ��ڸ�ӡˢ�ţ���ѡ������ֵ!");
		    return false;		
		}			 
	}
	document.getElementById("fm").submit();
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
	
	easyQueryClick();
}

/*********************************************************************
 *  ִ������Լɨ���EasyQuery
 *  ����:��ѯ��ʾ������ɨ���.��ʾ����:ɨ��������سɹ�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
/*function easyQueryClick()
{
	// ��ʼ�����
	initGrpGrid();

	// ��дSQL���
	var strSQL = "";
	
	var sqlid906164837="DSHomeContSql906164837";
var mySql906164837=new SqlClass();
mySql906164837.setResourceName("uw.UWHealthInputSql");//ָ��ʹ�õ�properties�ļ���
mySql906164837.setSqlId(sqlid906164837);//ָ��ʹ�õ�Sql��id
mySql906164837.addSubPara(fm.QContNo.value);//ָ������Ĳ���
mySql906164837.addSubPara(fm.QPrtSeq.value);//ָ������Ĳ���
mySql906164837.addSubPara(fm.QHandleDate.value);//ָ������Ĳ���
mySql906164837.addSubPara(fm.QHandler.value);//ָ������Ĳ���
mySql906164837.addSubPara(comcode+"%%");//ָ������Ĳ���
strSQL=mySql906164837.getString();
	
//	strSQL = "select lwmission.missionprop2,lwmission.missionprop3,lwmission.missionprop6,lwmission.missionprop5,lwmission.missionid,lwmission.submissionid,lwmission.activityid,lwmission.missionprop4  from lwmission,lccont where 1=1 "
//				 + " and lwmission.activityid ='0000001121' "
//				 + " and lwmission.processid in ('0000000003', '0000000000','0000000005')"
//				 //+ " and trim(lccont.contno) = trim(lwmission.missionprop2)"   modify by yinhl 2005-12-22 �����Ż�#263��ȥ��trim
//				 + " and lccont.contno = lwmission.missionprop2"	//modify by yinhl 2005-12-22 �����Ż�#263��ȥ��trim
//				 //+ " and defaultoperator ='" + operator + "'"
//				 + getWherePart('missionprop2','QContNo')
//				 + getWherePart('missionprop3','QPrtSeq')
//				 + getWherePart('missionprop6','QHandleDate')
//				 + getWherePart('missionprop5','QHandler')
//			   + " and lccont.managecom like '" + comcode + "%%'"  //����Ȩ�޹�������"
//				 + " order by lwmission.missionprop1" ;	 

	turnPage.queryModal(strSQL, GrpGrid);
	return true;
}


/* modify by lzf 2013-03-22
 * function showHealthQ()
{	
	
  var i = 0;
  var checkFlag = 0;
  var state = "0";
  
  for (i=0; i<GrpGrid.mulLineCount; i++) 
   {
    if (GrpGrid.getSelNo(i)) 
      { 
      checkFlag = GrpGrid.getSelNo();
      break;
      }
   }
  
  if (checkFlag) 
    { 
    var	ContNo = GrpGrid.getRowColData(checkFlag - 1, 1);  
    var PrtNo = GrpGrid.getRowColData(checkFlag - 1, 1);
    var PrtSeq = GrpGrid.getRowColData(checkFlag - 1, 2);
    var MissionID = GrpGrid.getRowColData(checkFlag - 1, 5);
    var SubMissionID = GrpGrid.getRowColData(checkFlag - 1, 6);   
    
    //���ڿͻ��ϲ����¹���������customerno��ţ�������Ҫ�޸�
    //modify by zhangxing 
    
    var sqlid906165017="DSHomeContSql906165017";
var mySql906165017=new SqlClass();
mySql906165017.setResourceName("uw.UWHealthInputSql");//ָ��ʹ�õ�properties�ļ���
mySql906165017.setSqlId(sqlid906165017);//ָ��ʹ�õ�Sql��id
mySql906165017.addSubPara(ContNo);//ָ������Ĳ���
mySql906165017.addSubPara(PrtSeq);//ָ������Ĳ���
var tSql=mySql906165017.getString();
    
//    var tSql = " select customerno from lcpenotice where 1 = 1"
//             + " and contno='"+ContNo+"' and prtseq = '"+PrtSeq+"'";
             
    turnPage1.strQueryResult = easyQueryVer3(tSql, 1, 0, 1);
    if (!turnPage1.strQueryResult)
    {
    	  alert("δ�鵽�ͻ�����");
    
    	  return;
    }
    turnPage1.arrDataCacheSet = decodeEasyQueryResult(turnPage1.strQueryResult);
            
    var CustomerNo = turnPage1.arrDataCacheSet[0][0];    
	
    var strReturn="1";
    //��ɨ���¼�����
    sFeatures = "";
    //sFeatures = "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1";
    if (strReturn == "1")  
          		     
    window.open("./UWManuHealthQMain.jsp?ContNo="+ContNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&PrtNo="+PrtNo+"&PrtSeq="+PrtSeq+"&CustomerNo="+CustomerNo+"&SubType=TB1013","window1","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
  	
    }
   else 
  	{
    alert("����ѡ��һ��������Ϣ��"); 
    }
}
*/
function showHealthQ()
{	
	
  var i = 0;
  var checkFlag = 0;
  var state = "0";
  
  for (i=0; i<UWHealthPoolGrid.mulLineCount; i++) 
   {
    if (UWHealthPoolGrid.getSelNo(i)) 
      { 
      checkFlag = UWHealthPoolGrid.getSelNo();
      break;
      }
   }
  
  if (checkFlag) 
    { 
    var	ContNo = UWHealthPoolGrid.getRowColData(checkFlag - 1, 1);  
    var PrtNo = UWHealthPoolGrid.getRowColData(checkFlag - 1, 5);
    var PrtSeq = UWHealthPoolGrid.getRowColData(checkFlag - 1, 2);
    var MissionID = UWHealthPoolGrid.getRowColData(checkFlag - 1, 8);
    var SubMissionID = UWHealthPoolGrid.getRowColData(checkFlag - 1, 9);   
    var ActivityID = UWHealthPoolGrid.getRowColData(checkFlag - 1, 11);   
    
    //���ڿͻ��ϲ����¹���������customerno��ţ�������Ҫ�޸�
    //modify by zhangxing 
    
    var sqlid906165017="DSHomeContSql906165017";
	var mySql906165017=new SqlClass();
	mySql906165017.setResourceName("uw.UWHealthInputSql");//ָ��ʹ�õ�properties�ļ���
	mySql906165017.setSqlId(sqlid906165017);//ָ��ʹ�õ�Sql��id
	mySql906165017.addSubPara(ContNo);//ָ������Ĳ���
	mySql906165017.addSubPara(PrtSeq);//ָ������Ĳ���
	var tSql=mySql906165017.getString();
    
//    var tSql = " select customerno from lcpenotice where 1 = 1"
//             + " and contno='"+ContNo+"' and prtseq = '"+PrtSeq+"'";
             
    turnPage1.strQueryResult = easyQueryVer3(tSql, 1, 0, 1);
    if (!turnPage1.strQueryResult)
    {
    	  alert("δ�鵽�ͻ�����");
    
    	  return;
    }
    turnPage1.arrDataCacheSet = decodeEasyQueryResult(turnPage1.strQueryResult);
            
    var CustomerNo = turnPage1.arrDataCacheSet[0][0];    
	
    var strReturn="1";
    //��ɨ���¼�����
    sFeatures = "";
    //sFeatures = "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1";
    if (strReturn == "1")  
          	   	
    	window.open("./UWManuHealthQMain.jsp?ContNo="+ContNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&PrtNo="+PrtNo+"&PrtSeq="+PrtSeq+"&CustomerNo="+CustomerNo+"&SubType=TB1013","window1","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
  	
    }
   else 
  	{
        alert("����ѡ��һ��������Ϣ��"); 
    }
}

//end lzf

function beforeSubmit()
{
	var strSQL = "";
	var sqlid906165122="DSHomeContSql906165122";
	var mySql906165122=new SqlClass();
	mySql906165122.setResourceName("uw.UWHealthInputSql");//ָ��ʹ�õ�properties�ļ���
	mySql906165122.setSqlId(sqlid906165122);//ָ��ʹ�õ�Sql��id
	mySql906165122.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
	strSQL=mySql906165122.getString();
	
//	strSQL = "select missionprop1 from lwmission where 1=1 "
//				 + " and activityid = '0000001098' "
//				 + " and processid = '0000000003'" 
//				 + " and missionprop1='"+fm.PrtNo.value+"'";
	turnPage1.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
	//�ж��Ƿ��ѯ�ɹ�
	if (turnPage1.strQueryResult) {
	    return false;
	}
	
	var sqlid906165204="DSHomeContSql906165204";
	var mySql906165204=new SqlClass();
	mySql906165204.setResourceName("uw.UWHealthInputSql");//ָ��ʹ�õ�properties�ļ���
	mySql906165204.setSqlId(sqlid906165204);//ָ��ʹ�õ�Sql��id
	mySql906165204.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
	strSQL=mySql906165204.getString();
	
//	strSQL = "select missionprop1 from lwmission where 1=1 "
//				 + " and activityid = '0000001062' "
//				 + " and processid = '0000000003'" 
//				 + " and missionprop1='"+fm.PrtNo.value+"'";	
	
	turnPage1.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
  if(turnPage1.strQueryResult)
  {
  	 return true;
  }
   
	var sqlid906165324="DSHomeContSql906165324";
	var mySql906165324=new SqlClass();
	mySql906165324.setResourceName("uw.UWHealthInputSql");//ָ��ʹ�õ�properties�ļ���
	mySql906165324.setSqlId(sqlid906165324);//ָ��ʹ�õ�Sql��id
	mySql906165324.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
	mySql906165324.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
	strSQL=mySql906165324.getString();
	
//	strSQL = "select prtno from lccont where prtno = '" + fm.PrtNo.value + "'"
//			 "union select prtno from lbcont where prtno = '" + fm.PrtNo.value + "'";
	turnPage1.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	//�ж��Ƿ��ѯ�ɹ�
	if (turnPage1.strQueryResult) {
	    return false;
	}	   	
}
function GrpbeforeSubmit()
{
	var strSQL = "";
	
	var sqlid906165440="DSHomeContSql906165440";
	var mySql906165440=new SqlClass();
	mySql906165440.setResourceName("uw.UWHealthInputSql");//ָ��ʹ�õ�properties�ļ���
	mySql906165440.setSqlId(sqlid906165440);//ָ��ʹ�õ�Sql��id
	mySql906165440.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
	strSQL=mySql906165440.getString();
	
//	strSQL = "select missionprop1 from lwmission where 1=1 "
//				 + " and activityid = '0000002098' "
//				 + " and processid = '0000000004'" 
//				 + " and missionprop1='"+fm.PrtNo.value+"'";
	turnPage1.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 
	//�ж��Ƿ��ѯ�ɹ�
	if (turnPage1.strQueryResult) {
		  alert('1');
	    return false;
	}

	var sqlid906165540="DSHomeContSql906165540";
	var mySql906165540=new SqlClass();
	mySql906165540.setResourceName("uw.UWHealthInputSql");//ָ��ʹ�õ�properties�ļ���
	mySql906165540.setSqlId(sqlid906165540);//ָ��ʹ�õ�Sql��id
	mySql906165540.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
	mySql906165540.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
	strSQL=mySql906165540.getString();
	
//	strSQL = "select prtno from lccont where prtno = '" + fm.PrtNo.value + "'"
//			 "union select prtno from lbcont where prtno = '" + fm.PrtNo.value + "'";
	turnPage1.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 
	//�ж��Ƿ��ѯ�ɹ�
	if (turnPage1.strQueryResult) {
		  alert('2');
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
	var sqlid906165651="DSHomeContSql906165651";
	var mySql906165651=new SqlClass();
	mySql906165651.setResourceName("uw.UWHealthInputSql");//ָ��ʹ�õ�properties�ļ���
	mySql906165651.setSqlId(sqlid906165651);//ָ��ʹ�õ�Sql��id
	mySql906165651.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
	strSQL=mySql906165651.getString();
	
	
//	strSQL = "select missionprop1 from lwmission where 1=1 "
//				 + " and activityid = '0000001060' "
//				 + " and processid = '0000000003'" 
//				 + " and missionprop1='"+fm.PrtNo.value+"'";
	turnPage1.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
	//�ж��Ƿ��ѯ�ɹ�
	if (turnPage1.strQueryResult) {
	    return false;
	}

	
	var sqlid906165749="DSHomeContSql906165749";
	var mySql906165749=new SqlClass();
	mySql906165749.setResourceName("uw.UWHealthInputSql");//ָ��ʹ�õ�properties�ļ���
	mySql906165749.setSqlId(sqlid906165749);//ָ��ʹ�õ�Sql��id
	mySql906165749.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
	mySql906165749.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
	strSQL=mySql906165749.getString();
	
//	strSQL = "select prtno from lccont where prtno = '" + fm.PrtNo.value + "'"
//			 "union select prtno from lbcont where prtno = '" + fm.PrtNo.value + "'";
	turnPage1.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
	//�ж��Ƿ��ѯ�ɹ�
	if (turnPage1.strQueryResult) {
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
		alert("ӡˢ��Ϊ�գ�");
		return;
	}			
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ PrtNo + "&NoType="+ NoType;
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");	
		
}