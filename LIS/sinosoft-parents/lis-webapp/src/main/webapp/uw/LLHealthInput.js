
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
	document.getElementById("fm").submit();
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
	
	//easyQueryClick();
	jQuery("#publicSearch").click();
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
	
//	strSQL = "select lwmission.missionprop2,lwmission.missionprop3,lwmission.missionprop6,lwmission.missionprop5,lwmission.missionid,lwmission.submissionid,lwmission.activityid,lwmission.missionprop4  from lwmission,lpcont where 1=1 "
//				 + " and lwmission.activityid ='0000005534' "
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
//	turnPage.strQueryResult = easyQueryVer3(strSQL, 1, 1, 1);  
//	if(!turnPage.strQueryResult){
	//modify by lzf 2013-05-08
//		var wherepartStr = getWherePart('missionprop2','QContNo')
//				 + getWherePart('missionprop3','QPrtSeq')
//				 + getWherePart('missionprop6','QHandleDate')
//				 + getWherePart('missionprop5','QHandler');
		var sqlid915150902="DSHomeContSql915150902";
var mySql915150902=new SqlClass();
mySql915150902.setResourceName("uw.LLHealthInputSql");//ָ��ʹ�õ�properties�ļ���
mySql915150902.setSqlId(sqlid915150902);//ָ��ʹ�õ�Sql��id
//mySql915150902.addSubPara(wherepartStr);//ָ������Ĳ���
mySql915150902.addSubPara(fm.QContNo.value);
mySql915150902.addSubPara(fm.QPrtSeq.value);
mySql915150902.addSubPara(fm.QHandleDate.value);
mySql915150902.addSubPara(fm.QHandler.value);
mySql915150902.addSubPara(comcode+"%%");//ָ������Ĳ���
strSQL=mySql915150902.getString();
		
//		strSQL = "select lwmission.missionprop2,lwmission.missionprop3,lwmission.missionprop6,lwmission.missionprop5,lwmission.missionid,lwmission.submissionid,lwmission.activityid,lwmission.missionprop4  from lwmission,lccont where 1=1 "
//				 + " and lwmission.activityid ='0000005534' "
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
//	}
	turnPage.queryModal(strSQL, GrpGrid);
	return true;
}
*/

//function showHealthQ()
//{	
//	
//  var i = 0;
//  var checkFlag = 0;
//  var state = "0";
//  
//  for (i=0; i<GrpGrid.mulLineCount; i++) 
//   {
//    if (GrpGrid.getSelNo(i)) 
//      { 
//      checkFlag = GrpGrid.getSelNo();
//      break;
//      }
//   }
//  
//  if (checkFlag) 
//    { 
//    var	ContNo = GrpGrid.getRowColData(checkFlag - 1, 1);  
//    var PrtNo = GrpGrid.getRowColData(checkFlag - 1, 1);
//    var PrtSeq = GrpGrid.getRowColData(checkFlag - 1, 2);
//    var MissionID = GrpGrid.getRowColData(checkFlag - 1, 5);
//    var SubMissionID = GrpGrid.getRowColData(checkFlag - 1, 6); 
//    var sqlid915151204="DSHomeContSql915151204";
//var mySql915151204=new SqlClass();
//mySql915151204.setResourceName("uw.LLHealthInputSql");//ָ��ʹ�õ�properties�ļ���
//mySql915151204.setSqlId(sqlid915151204);//ָ��ʹ�õ�Sql��id
//mySql915151204.addSubPara(PrtSeq);//ָ������Ĳ���
//var tDocIDSql=mySql915151204.getString();
//      
////    var tDocIDSql = "select docid from es_doc_main where busstype='LP' and doccode='"+PrtSeq+"'";
//    var tDocID = easyExecSql(tDocIDSql);
//    //���ڿͻ��ϲ����¹���������customerno��ţ�������Ҫ�޸�
//    var sqlid915151329="DSHomeContSql915151329";
//var mySql915151329=new SqlClass();
//mySql915151329.setResourceName("uw.LLHealthInputSql");//ָ��ʹ�õ�properties�ļ���
//mySql915151329.setSqlId(sqlid915151329);//ָ��ʹ�õ�Sql��id
//mySql915151329.addSubPara(ContNo);//ָ������Ĳ���
//mySql915151329.addSubPara(PrtSeq);//ָ������Ĳ���
//var tSql=mySql915151329.getString();
//    
////    var tSql = " select customerno from lluwpenotice where 1 = 1"
////             + " and contno='"+ContNo+"' and prtseq = '"+PrtSeq+"'";
//             
//    turnPage.strQueryResult = easyQueryVer3(tSql, 1, 0, 1);
//    if (!turnPage.strQueryResult)
//    {
//    	  alert("δ�鵽�ͻ�����");
//    
//    	  return;
//    }
//    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
//            
//    var CustomerNo = turnPage.arrDataCacheSet[0][0];    
//	
//    var strReturn="1";
//    //��ɨ���¼�����
//    sFeatures = "";
//    //sFeatures = "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1";
//    if (strReturn == "1")  
//          		     
//    window.open("./LLManuHealthQMain.jsp?ContNo="+ContNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&PrtNo="+PrtNo+"&PrtSeq="+PrtSeq+"&CustomerNo="+CustomerNo+"&DocID="+tDocID,"window1","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
//  	
//    }
//   else 
//  	{
//    alert("����ѡ��һ��������Ϣ��"); 
//    }
//}

function showHealthQ()
{	
	
  var i = 0;
  var checkFlag = 0;
  var state = "0";
  
  for (i=0; i<PublicWorkPoolGrid.mulLineCount; i++) 
   {
    if (PublicWorkPoolGrid.getSelNo(i)) 
      { 
      checkFlag = PublicWorkPoolGrid.getSelNo();
      break;
      }
   }
  
  if (checkFlag) 
    { 
    var	ContNo = PublicWorkPoolGrid.getRowColData(checkFlag - 1, 1);  
    var PrtNo = PublicWorkPoolGrid.getRowColData(checkFlag - 1, 5);
    var PrtSeq = PublicWorkPoolGrid.getRowColData(checkFlag - 1, 2);
    var MissionID = PublicWorkPoolGrid.getRowColData(checkFlag - 1,10);
    var SubMissionID = PublicWorkPoolGrid.getRowColData(checkFlag - 1, 11); 
    var ActivityID = PublicWorkPoolGrid.getRowColData(checkFlag - 1, 13);
    var ClmNo = PublicWorkPoolGrid.getRowColData(checkFlag - 1, 7);
    var BatNo = PublicWorkPoolGrid.getRowColData(checkFlag - 1, 8);
    var sqlid915151204="DSHomeContSql915151204";
	var mySql915151204=new SqlClass();
	mySql915151204.setResourceName("uw.LLHealthInputSql");//ָ��ʹ�õ�properties�ļ���
	mySql915151204.setSqlId(sqlid915151204);//ָ��ʹ�õ�Sql��id
	mySql915151204.addSubPara(PrtSeq);//ָ������Ĳ���
	var tDocIDSql=mySql915151204.getString();
      
//    var tDocIDSql = "select docid from es_doc_main where busstype='LP' and doccode='"+PrtSeq+"'";
    var tDocID = easyExecSql(tDocIDSql);
    //���ڿͻ��ϲ����¹���������customerno��ţ�������Ҫ�޸�
    var sqlid915151329="DSHomeContSql915151329";
	var mySql915151329=new SqlClass();
	mySql915151329.setResourceName("uw.LLHealthInputSql");//ָ��ʹ�õ�properties�ļ���
	mySql915151329.setSqlId(sqlid915151329);//ָ��ʹ�õ�Sql��id
	mySql915151329.addSubPara(ContNo);//ָ������Ĳ���
	mySql915151329.addSubPara(PrtSeq);//ָ������Ĳ���
	var tSql=mySql915151329.getString();
    
//    var tSql = " select customerno from lluwpenotice where 1 = 1"
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
          		     
    window.open("./LLManuHealthQMain.jsp?ContNo="+ContNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&PrtNo="+PrtNo+"&PrtSeq="+PrtSeq+"&CustomerNo="+CustomerNo+"&DocID="+tDocID+"&ActivityID="+ActivityID+"&ClmNo="+ClmNo+"&BatNo="+BatNo,"window1","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
  	
    }
   else 
  	{
    alert("����ѡ��һ��������Ϣ��"); 
    }
}

function beforeSubmit()
{
	var strSQL = "";
	var sqlid915151429="DSHomeContSql915151429";
	var mySql915151429=new SqlClass();
	mySql915151429.setResourceName("uw.LLHealthInputSql");//ָ��ʹ�õ�properties�ļ���
	mySql915151429.setSqlId(sqlid915151429);//ָ��ʹ�õ�Sql��id
	mySql915151429.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
	strSQL=mySql915151429.getString();
	
//	strSQL = "select missionprop1 from lwmission where 1=1 "
//				 + " and activityid = '0000001098' "
//				 + " and processid = '0000000003'" 
//				 + " and missionprop1='"+fm.PrtNo.value+"'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
	//�ж��Ƿ��ѯ�ɹ�
	if (turnPage.strQueryResult) {
	    return false;
	}
	
	var sqlid915151523="DSHomeContSql915151523";
	var mySql915151523=new SqlClass();
	mySql915151523.setResourceName("uw.LLHealthInputSql");//ָ��ʹ�õ�properties�ļ���
	mySql915151523.setSqlId(sqlid915151523);//ָ��ʹ�õ�Sql��id
	mySql915151523.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
	strSQL=mySql915151523.getString();

	
//	strSQL = "select missionprop1 from lwmission where 1=1 "
//				 + " and activityid = '0000001062' "
//				 + " and processid = '0000000003'" 
//				 + " and missionprop1='"+fm.PrtNo.value+"'";	
	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
  if(turnPage.strQueryResult)
  {
  	 return true;
  }
   
	var sqlid915151628="DSHomeContSql915151628";
	var mySql915151628=new SqlClass();
	mySql915151628.setResourceName("uw.LLHealthInputSql");//ָ��ʹ�õ�properties�ļ���
	mySql915151628.setSqlId(sqlid915151628);//ָ��ʹ�õ�Sql��id
	mySql915151628.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
	mySql915151628.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
	strSQL=mySql915151628.getString();
	
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
	
	var sqlid915151740="DSHomeContSql915151740";
var mySql915151740=new SqlClass();
mySql915151740.setResourceName("uw.LLHealthInputSql");//ָ��ʹ�õ�properties�ļ���
mySql915151740.setSqlId(sqlid915151740);//ָ��ʹ�õ�Sql��id
mySql915151740.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
strSQL=mySql915151740.getString();
	
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

	var sqlid915152130="DSHomeContSql915152130";
var mySql915152130=new SqlClass();
mySql915152130.setResourceName("uw.LLHealthInputSql");//ָ��ʹ�õ�properties�ļ���
mySql915152130.setSqlId(sqlid915152130);//ָ��ʹ�õ�Sql��id
mySql915152130.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
mySql915152130.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
strSQL=mySql915152130.getString();
	
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
	var sqlid915152251="DSHomeContSql915152251";
var mySql915152251=new SqlClass();
mySql915152251.setResourceName("uw.LLHealthInputSql");//ָ��ʹ�õ�properties�ļ���
mySql915152251.setSqlId(sqlid915152251);//ָ��ʹ�õ�Sql��id
mySql915152251.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
strSQL=mySql915152251.getString();
	
//	strSQL = "select missionprop1 from lwmission where 1=1 "
//				 + " and activityid = '0000001060' "
//				 + " and processid = '0000000003'" 
//				 + " and missionprop1='"+fm.PrtNo.value+"'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
	//�ж��Ƿ��ѯ�ɹ�
	if (turnPage.strQueryResult) {
	    return false;
	}

	var sqlid915152351="DSHomeContSql915152351";
var mySql915152351=new SqlClass();
mySql915152351.setResourceName("uw.LLHealthInputSql");//ָ��ʹ�õ�properties�ļ���
mySql915152351.setSqlId(sqlid915152351);//ָ��ʹ�õ�Sql��id
mySql915152351.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
mySql915152351.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
strSQL=mySql915152351.getString();

	
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