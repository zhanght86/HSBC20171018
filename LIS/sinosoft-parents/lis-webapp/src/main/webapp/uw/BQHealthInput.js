
//�������ƣ�UWHealthInput.js
//�����ܣ���������Լɨ�������¼��
//�������ڣ�
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����

var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();
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
	fm.submit();
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
function easyQueryClick()
{
	// ��ʼ�����
	initGrpGrid();

	// ��дSQL���
	var strSQL = "";
	//���ַ�ʽ����������󣬹ʲ���mySql915143623.addSubPara(fm.QHandler.value)��ʽ���� --edit by ningyc 20111012
//	var wherePartStr = getWherePart('missionprop2','QContNo')
//				 + getWherePart('missionprop3','QPrtSeq')
//				 + getWherePart('missionprop6','QHandleDate')
//				 + getWherePart('missionprop5','QHandler');

	var sqlid915143623="DSHomeContSql915143623";
var mySql915143623=new SqlClass();
mySql915143623.setResourceName("uw.BQHealthInputSql");//ָ��ʹ�õ�properties�ļ���
mySql915143623.setSqlId(sqlid915143623);//ָ��ʹ�õ�Sql��id
mySql915143623.addSubPara(fm.QContNo.value);//ָ������Ĳ���
mySql915143623.addSubPara(fm.QPrtSeq.value);//ָ������Ĳ���
mySql915143623.addSubPara(fm.QHandleDate.value);//ָ������Ĳ���
mySql915143623.addSubPara(fm.QHandler.value);//ָ������Ĳ���
mySql915143623.addSubPara(comcode+"%%");//ָ������Ĳ���
strSQL=mySql915143623.getString();
	
//	strSQL = "select lwmission.missionprop2,lwmission.missionprop3,lwmission.missionprop6,lwmission.missionprop5,lwmission.missionid,lwmission.submissionid,lwmission.activityid,lwmission.missionprop4  from lwmission,lpcont,lppenotice where 1=1 "
//				 + " and lwmission.activityid ='0000000305' "
//				 + " and lwmission.processid in ('0000000003', '0000000000','0000000005')"
//				 //+ " and trim(lccont.contno) = trim(lwmission.missionprop2)"   modify by yinhl 2005-12-22 �����Ż�#263��ȥ��trim
//				 + " and lpcont.contno = lwmission.missionprop2 "	//modify by yinhl 2005-12-22 �����Ż�#263��ȥ��trim
//				 + " and lpcont.edorno = lppenotice.edorno "
//				 + " and lwmission.missionprop3 = lppenotice.prtseq "
//				 + " and exists(select 1 from lppenotice where contno=lpcont.contno and edorno=lpcont.edorno) "
//				 //+ " and defaultoperator ='" + operator + "'"
//				 + getWherePart('missionprop2','QContNo')
//				 + getWherePart('missionprop3','QPrtSeq')
//				 + getWherePart('missionprop6','QHandleDate')
//				 + getWherePart('missionprop5','QHandler')
//			   + " and lpcont.managecom like '" + comcode + "%%'"  //����Ȩ�޹�������"
//				 + " order by lwmission.missionprop1" ;	 
	turnPage.strQueryResult = easyQueryVer3(strSQL, 1, 1, 1); 
	
	if(!turnPage.strQueryResult){
		//���ַ�ʽ����������󣬹ʲ���mySql915143623.addSubPara(fm.QHandler.value)��ʽ���� --edit by ningyc 20111012
//		var wherePartStr = getWherePart('missionprop2','QContNo')
//				 + getWherePart('missionprop3','QPrtSeq')
//				 + getWherePart('missionprop6','QHandleDate')
//				 + getWherePart('missionprop5','QHandler');
		var sqlid915143950="DSHomeContSql915143950";
var mySql915143950=new SqlClass();
mySql915143950.setResourceName("uw.BQHealthInputSql");//ָ��ʹ�õ�properties�ļ���
mySql915143950.setSqlId(sqlid915143950);//ָ��ʹ�õ�Sql��id
mySql915143950.addSubPara(fm.QContNo.value);//ָ������Ĳ���
mySql915143950.addSubPara(fm.QPrtSeq.value);//ָ������Ĳ���
mySql915143950.addSubPara(fm.QHandleDate.value);//ָ������Ĳ���
mySql915143950.addSubPara(fm.QHandler.value);//ָ������Ĳ���
mySql915143950.addSubPara(comcode+"%%");//ָ������Ĳ���
strSQL=mySql915143950.getString();
		
//		strSQL = "select lwmission.missionprop2,lwmission.missionprop3,lwmission.missionprop6,lwmission.missionprop5,lwmission.missionid,lwmission.submissionid,lwmission.activityid,lwmission.missionprop4  from lwmission,lccont where 1=1 "
//				 + " and lwmission.activityid ='0000000305' "
//				 + " and lwmission.processid in ('0000000003', '0000000000','0000000005')"
//				 //+ " and trim(lccont.contno) = trim(lwmission.missionprop2)"   modify by yinhl 2005-12-22 �����Ż�#263��ȥ��trim
//				 + " and lccont.contno = lwmission.missionprop2"	//modify by yinhl 2005-12-22 �����Ż�#263��ȥ��trim
//				 //+ " and defaultoperator ='" + operator + "'"
//				 + getWherePart('missionprop2','QContNo')
//				 + getWherePart('missionprop3','QPrtSeq')
//				 + getWherePart('missionprop6','QHandleDate')
//				 + getWherePart('missionprop5','QHandler')
//			     + " and lccont.managecom like '" + comcode + "%%'"  //����Ȩ�޹�������"
//				 + " order by lwmission.missionprop1" ;	 
	}
	turnPage.queryModal(strSQL, GrpGrid);
	return true;
}


function showHealthQ()
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
    var ActivityID = GrpGrid.getRowColData(checkFlag - 1, 7);
    
    //���ڿͻ��ϲ����¹���������customerno��ţ�������Ҫ�޸�
    //modify by zhangxing 
    var sqlid915144240="DSHomeContSql915144240";
var mySql915144240=new SqlClass();
mySql915144240.setResourceName("uw.BQHealthInputSql");//ָ��ʹ�õ�properties�ļ���
mySql915144240.setSqlId(sqlid915144240);//ָ��ʹ�õ�Sql��id
mySql915144240.addSubPara(ContNo);//ָ������Ĳ���
mySql915144240.addSubPara(PrtSeq);//ָ������Ĳ���
var tSql=mySql915144240.getString();
    
//    var tSql = " select customerno from lppenotice where 1 = 1"
//             + " and contno='"+ContNo+"' and prtseq = '"+PrtSeq+"'";
             
    turnPage.strQueryResult = easyQueryVer3(tSql, 1, 0, 1);
    if (!turnPage.strQueryResult)
    {
    	  alert("δ�鵽�ͻ�����");
    
    	  return;
    }
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
            
    var CustomerNo = turnPage.arrDataCacheSet[0][0];    
	
    var strReturn="1";
    //��ɨ���¼�����
    sFeatures = "";
    //sFeatures = "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1";
    if (strReturn == "1")  
          		     
    window.open("./BQManuHealthQMain.jsp?ActivityID="+ActivityID+"&ContNo="+ContNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&PrtNo="+PrtNo+"&PrtSeq="+PrtSeq+"&CustomerNo="+CustomerNo,"window1","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
  	
    }
   else 
  	{
    alert("����ѡ��һ��������Ϣ��"); 
    }
}



function beforeSubmit()
{
	var strSQL = "";
	var sqlid915145721="DSHomeContSql915145721";
var mySql915145721=new SqlClass();
mySql915145721.setResourceName("uw.BQHealthInputSql");//ָ��ʹ�õ�properties�ļ���
mySql915145721.setSqlId(sqlid915145721);//ָ��ʹ�õ�Sql��id
mySql915145721.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
strSQL=mySql915145721.getString();
	
//	strSQL = "select missionprop1 from lwmission where 1=1 "
//				 + " and activityid = '0000001098' "
//				 + " and processid = '0000000003'" 
//				 + " and missionprop1='"+fm.PrtNo.value+"'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
	//�ж��Ƿ��ѯ�ɹ�
	if (turnPage.strQueryResult) {
	    return false;
	}
	
	var sqlid915145812="DSHomeContSql915145812";
var mySql915145812=new SqlClass();
mySql915145812.setResourceName("uw.BQHealthInputSql");//ָ��ʹ�õ�properties�ļ���
mySql915145812.setSqlId(sqlid915145812);//ָ��ʹ�õ�Sql��id
mySql915145812.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
strSQL=mySql915145812.getString();
	
//	strSQL = "select missionprop1 from lwmission where 1=1 "
//				 + " and activityid = '0000001062' "
//				 + " and processid = '0000000003'" 
//				 + " and missionprop1='"+fm.PrtNo.value+"'";	
	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
  if(turnPage.strQueryResult)
  {
  	 return true;
  }
   
	var sqlid915150013="DSHomeContSql915150013";
var mySql915150013=new SqlClass();
mySql915150013.setResourceName("uw.BQHealthInputSql");//ָ��ʹ�õ�properties�ļ���
mySql915150013.setSqlId(sqlid915150013);//ָ��ʹ�õ�Sql��id
mySql915150013.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
mySql915150013.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
strSQL=mySql915150013.getString();

	
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
	var sqlid915150148="DSHomeContSql915150148";
var mySql915150148=new SqlClass();
mySql915150148.setResourceName("uw.BQHealthInputSql");//ָ��ʹ�õ�properties�ļ���
mySql915150148.setSqlId(sqlid915150148);//ָ��ʹ�õ�Sql��id
mySql915150148.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
strSQL=mySql915150148.getString();
	
//	strSQL = "select missionprop1 from lwmission where 1=1 "
//				 + " and activityid = '0000002098' "
//				 + " and processid = '0000000004'" 
//				 + " and missionprop1='"+fm.PrtNo.value+"'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 
	//�ж��Ƿ��ѯ�ɹ�
	if (turnPage.strQueryResult) {
		  alert('1');
	    return false;
	}

	var sqlid915150329="DSHomeContSql915150329";
var mySql915150329=new SqlClass();
mySql915150329.setResourceName("uw.BQHealthInputSql");//ָ��ʹ�õ�properties�ļ���
mySql915150329.setSqlId(sqlid915150329);//ָ��ʹ�õ�Sql��id
mySql915150329.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
mySql915150329.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
strSQL=mySql915150329.getString();

	
//	strSQL = "select prtno from lccont where prtno = '" + fm.PrtNo.value + "'"
//			 "union select prtno from lbcont where prtno = '" + fm.PrtNo.value + "'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 
	//�ж��Ƿ��ѯ�ɹ�
	if (turnPage.strQueryResult) {
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
	var sqlid915150440="DSHomeContSql915150440";
var mySql915150440=new SqlClass();
mySql915150440.setResourceName("uw.BQHealthInputSql");//ָ��ʹ�õ�properties�ļ���
mySql915150440.setSqlId(sqlid915150440);//ָ��ʹ�õ�Sql��id
mySql915150440.addSubPara(none.PrtNo.value);//ָ������Ĳ���
var strSQL=mySql915150440.getString();
	
//	strSQL = "select missionprop1 from lwmission where 1=1 "
//				 + " and activityid = '0000001060' "
//				 + " and processid = '0000000003'" 
//				 + " and missionprop1='"+fm.PrtNo.value+"'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
	//�ж��Ƿ��ѯ�ɹ�
	if (turnPage.strQueryResult) {
	    return false;
	}

	var sqlid915150558="DSHomeContSql915150558";
var mySql915150558=new SqlClass();
mySql915150558.setResourceName("uw.BQHealthInputSql");//ָ��ʹ�õ�properties�ļ���
mySql915150558.setSqlId(sqlid915150558);//ָ��ʹ�õ�Sql��id
mySql915150558.addSubPara(none.PrtNo.value);//ָ������Ĳ���
mySql915150558.addSubPara(none.PrtNo.value);//ָ������Ĳ���
strSQL=mySql915150558.getString();
	
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
		alert("ӡˢ��Ϊ�գ�");
		return;
	}			
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ PrtNo + "&NoType="+ NoType;
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");	
		
}