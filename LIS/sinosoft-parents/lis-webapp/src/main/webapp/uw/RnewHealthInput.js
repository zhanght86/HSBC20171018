
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
/*	
	var wherePartStr = getWherePart('missionprop2','QContNo')
				 + getWherePart('missionprop3','QPrtSeq')
				 + getWherePart('missionprop6','QHandleDate')
				 + getWherePart('missionprop5','QHandler');*/
	var sqlid915152743="DSHomeContSql915152743";
var mySql915152743=new SqlClass();
mySql915152743.setResourceName("uw.RnewHealthInputSql");//ָ��ʹ�õ�properties�ļ���
mySql915152743.setSqlId(sqlid915152743);//ָ��ʹ�õ�Sql��id
mySql915152743.addSubPara(fm.QContNo.value);//ָ������Ĳ���
mySql915152743.addSubPara(fm.QPrtSeq.value);
mySql915152743.addSubPara(fm.QHandleDate.value);
mySql915152743.addSubPara(fm.QHandler.value);
mySql915152743.addSubPara(comcode+"%%");//ָ������Ĳ���
strSQL=mySql915152743.getString();
	
//	strSQL = "select lwmission.missionprop2,lwmission.missionprop3,lwmission.missionprop6,lwmission.missionprop5,lwmission.missionid,lwmission.submissionid,lwmission.activityid,lwmission.missionprop4  from lwmission,lccont where 1=1 "
//				 + " and lwmission.activityid ='0000007013' "
//				 + " and lwmission.processid ='0000000007'"
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
    
    //���ڿͻ��ϲ����¹���������customerno��ţ�������Ҫ�޸�
    //modify by zhangxing 
    var sqlid915153030="DSHomeContSql915153030";
var mySql915153030=new SqlClass();
mySql915153030.setResourceName("uw.RnewHealthInputSql");//ָ��ʹ�õ�properties�ļ���
mySql915153030.setSqlId(sqlid915153030);//ָ��ʹ�õ�Sql��id
mySql915153030.addSubPara(ContNo);//ָ������Ĳ���
mySql915153030.addSubPara(PrtSeq);//ָ������Ĳ���
var tSql=mySql915153030.getString();
    
//    var tSql = " select customerno from Rnewpenotice where 1 = 1"
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
          		     
    window.open("./RnewManuHealthQMain.jsp?ContNo="+ContNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&PrtNo="+PrtNo+"&PrtSeq="+PrtSeq+"&CustomerNo="+CustomerNo,"window1","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
  	
    }
   else 
  	{
    alert("����ѡ��һ��������Ϣ��"); 
    }
}



function beforeSubmit()
{
	var strSQL = "";
	var sqlid915153202="DSHomeContSql915153202";
var mySql915153202=new SqlClass();
mySql915153202.setResourceName("uw.RnewHealthInputSql");//ָ��ʹ�õ�properties�ļ���
mySql915153202.setSqlId(sqlid915153202);//ָ��ʹ�õ�Sql��id
mySql915153202.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
strSQL=mySql915153202.getString();
	
//	strSQL = "select missionprop1 from lwmission where 1=1 "
//				 + " and activityid = '0000001098' "
//				 + " and processid = '0000000003'" 
//				 + " and missionprop1='"+fm.PrtNo.value+"'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
	//�ж��Ƿ��ѯ�ɹ�
	if (turnPage.strQueryResult) {
	    return false;
	}
	
	var sqlid915153245="DSHomeContSql915153245";
var mySql915153245=new SqlClass();
mySql915153245.setResourceName("uw.RnewHealthInputSql");//ָ��ʹ�õ�properties�ļ���
mySql915153245.setSqlId(sqlid915153245);//ָ��ʹ�õ�Sql��id
mySql915153245.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
strSQL=mySql915153245.getString();
	
//	strSQL = "select missionprop1 from lwmission where 1=1 "
//				 + " and activityid = '0000001062' "
//				 + " and processid = '0000000003'" 
//				 + " and missionprop1='"+fm.PrtNo.value+"'";	
	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
  if(turnPage.strQueryResult)
  {
  	 return true;
  }
   
	var sqlid915153400="DSHomeContSql915153400";
var mySql915153400=new SqlClass();
mySql915153400.setResourceName("uw.RnewHealthInputSql");//ָ��ʹ�õ�properties�ļ���
mySql915153400.setSqlId(sqlid915153400);//ָ��ʹ�õ�Sql��id
mySql915153400.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
mySql915153400.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
strSQL=mySql915153400.getString();
	
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
	
	var sqlid915153538="DSHomeContSql915153538";
var mySql915153538=new SqlClass();
mySql915153538.setResourceName("uw.RnewHealthInputSql");//ָ��ʹ�õ�properties�ļ���
mySql915153538.setSqlId(sqlid915153538);//ָ��ʹ�õ�Sql��id
mySql915153538.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
strSQL=mySql915153538.getString();
	
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

	var sqlid915153652="DSHomeContSql915153652";
var mySql915153652=new SqlClass();
mySql915153652.setResourceName("uw.RnewHealthInputSql");//ָ��ʹ�õ�properties�ļ���
mySql915153652.setSqlId(sqlid915153652);//ָ��ʹ�õ�Sql��id
mySql915153652.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
mySql915153652.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
strSQL=mySql915153652.getString();
	
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
	var sqlid915153849="DSHomeContSql915153849";
var mySql915153849=new SqlClass();
mySql915153849.setResourceName("uw.RnewHealthInputSql");//ָ��ʹ�õ�properties�ļ���
mySql915153849.setSqlId(sqlid915153849);//ָ��ʹ�õ�Sql��id
mySql915153849.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
strSQL=mySql915153849.getString();
	
//	strSQL = "select missionprop1 from lwmission where 1=1 "
//				 + " and activityid = '0000001060' "
//				 + " and processid = '0000000003'" 
//				 + " and missionprop1='"+fm.PrtNo.value+"'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
	//�ж��Ƿ��ѯ�ɹ�
	if (turnPage.strQueryResult) {
	    return false;
	}

	var sqlid915154004="DSHomeContSql915154004";
var mySql915154004=new SqlClass();
mySql915154004.setResourceName("uw.RnewHealthInputSql");//ָ��ʹ�õ�properties�ļ���
mySql915154004.setSqlId(sqlid915154004);//ָ��ʹ�õ�Sql��id
mySql915154004.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
mySql915154004.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
strSQL=mySql915154004.getString();

	
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