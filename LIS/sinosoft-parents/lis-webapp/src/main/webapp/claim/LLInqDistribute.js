//�������ƣ�LLInqDistribute.js
//�����ܣ������������
//�������ڣ�2003-03-27 15:10:36
//������  ��WHN
//���¼�¼��  ������:yuejw    ��������     ����ԭ��/����
//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();
var k = 0;
var mySql = new SqlClass();
//��ʾdiv����һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
function showDiv(cDiv,cShow)
{
  if (cShow=="true")
  {
    cDiv.style.display="";
  }
  else
  {
    cDiv.style.display="none";  
  }
}

//��ѯ��ǰ���������ڻ���
function qurycomcode()
{
	/*var strSQL="select usercode,username,comcode from lduser "
			+"where usercode ='"+document.all('Operator').value+"'";*/
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLInqDistributeInputSql");
	mySql.setSqlId("LLInqDistributeSql1");
	mySql.addSubPara(document.all('Operator').value ); 
	var arr=easyExecSql(mySql.getString());
	document.all('tComCode').value =arr[0][2]+"%";//���ڸ��ݻ�����ѯ������		
//	alert(document.all('tComCode').value);
}

// ��ѯ��ť
//function InqApplyGridQueryClick()
//{	
//	divInqPer.style.display = 'none';
//	/*var strSQL = "";
//	strSQL = "select MISSIONPROP1,MISSIONPROP2,MISSIONPROP3,MISSIONPROP4,MISSIONPROP5, "
//	         +" (select codename from ldcode where codetype='llinitphase' and trim(code)=missionprop6), "
//	         +" MISSIONPROP7,MISSIONPROP8,MissionID,SubMissionID,ActivityID,MISSIONPROP6, "
//	         +" (select accepteddate from llregister where rgtno=lwmission.missionprop1) AcceptedDate from lwmission where 1=1 " 		
//           + " and (activityid = '0000005125' or activityid='0000009125')"
//           + " and processid in ('0000000005','0000000009')"
//           + getWherePart("MISSIONPROP1", "ClmNo")
//           + getWherePart("MISSIONPROP4", "CustomerNo")     
//           + getWherePart("MISSIONPROP5", "CustomerName")                   
//           + " and missionprop8 like '" + document.all('ComCode').value+"%'" //��������
//           + " and createoperator='"+document.all('Operator').value+"'"
//           + " order by AcceptedDate,missionprop1";	*/
//    mySql = new SqlClass();
//	mySql.setResourceName("claim.LLInqDistributeInputSql");
//	mySql.setSqlId("LLInqDistributeSql2");
//	mySql.addSubPara(fm.ClmNo.value ); 
//	mySql.addSubPara(fm.CustomerNo.value ); 
//	mySql.addSubPara(fm.CustomerName.value ); 
//	mySql.addSubPara(document.all('ComCode').value ); 
//	mySql.addSubPara(document.all('Operator').value ); 
//    turnPage.pageLineNum=10;    //ÿҳ5��
//    turnPage.queryModal(mySql.getString(),InqApplyGrid);//prompt("",strSQL);
//    HighlightByRow();
//}

//���ð������������������������Ѵﵽ20�ջ򳬹�20�յģ���ð�������ʾ��Ŀ��Ϊ��ɫ
//function HighlightByRow(){
//    for(var i=0; i<InqApplyGrid.mulLineCount; i++){
//    	var accepteddate = InqApplyGrid.getRowColData(i,13); //��������
//    	if(accepteddate != null && accepteddate != "")
//    	{
//    	   if(dateDiff(accepteddate,fm.CurDate.value,"D")>=20)		//�������ڳ���20��
//    	   {
//    		   InqApplyGrid.setRowClass(i,'warn'); 
//    	   }
//    	}
//    }  
//}


//[���ѡť��Ӧ����]
//function InqApplyGridSelClick()
//{
//  	var i = InqApplyGrid.getSelNo()-1;
//	fm.tClmNo.value = InqApplyGrid.getRowColData(i,1);
//	fm.tInqNo.value = InqApplyGrid.getRowColData(i,2); 
//	fm.tCustomerNo.value = InqApplyGrid.getRowColData(i,4); 
//	fm.tCustomerName.value = InqApplyGrid.getRowColData(i,5); 
//	fm.tInitDept.value = InqApplyGrid.getRowColData(i,8);    
//	fm.MissionID.value = InqApplyGrid.getRowColData(i,9);    
//	fm.SubMissionID.value = InqApplyGrid.getRowColData(i,10);    
//	fm.ActivityID.value = InqApplyGrid.getRowColData(i,11);            
//	divInqPer.style.display = '';
//	
//	//2009-04-22 zhangzheng
//	if(fm.ActivityID.value=='0000009125')
//	{
//		fm.claimUserTable.value='llgrpclaimuser';
//	}
//	else
//	{
//		fm.claimUserTable.value='llclaimuser';
//	}
//	
//	//����������Ϣ�Ĳ�ѯ 2006-01-13 ZHaoRx
//	fm.SavedCustomerName.value = "";
//	fm.SavedBatNo.value        = "";
//	fm.SavedMoreInq.value      = "";
////	fm.SavedVIPFlag.value      = "";
//	fm.SavedInqReason.value    = "";
//	fm.SavedInqOrg.value       = "";
//	fm.SavedInqItem.value      = "";
//	fm.SavedInqDesc.value      = "";
//	/*var tInqSavedSQL = " select CustomerName,BatNo,"
//	                 + " case LocFlag when '0' then '��' when '1' then '��' else '' end,"
//	                 + " case VIPFlag when '0' then '��' when '1' then '��' else '' end,"
//	                 + " InqRCode,InqDept,InqItem,InqDesc from llinqapply "
//	                 + " where clmno='"+fm.tClmNo.value+"' and inqno='"+fm.tInqNo.value+"'";*/
//	mySql = new SqlClass();
//	mySql.setResourceName("claim.LLInqDistributeInputSql");
//	mySql.setSqlId("LLInqDistributeSql3");
//	mySql.addSubPara(fm.tClmNo.value ); 
//	mySql.addSubPara(fm.tInqNo.value ); 
//	//prompt("����������Ϣ�Ĳ�ѯSQL",tInqSavedSQL);                 
//	var tSavedRuselt = new Array;
//	tSavedRuselt = easyExecSql(mySql.getString());
//	fm.SavedCustomerName.value = tSavedRuselt[0][0];
//	fm.SavedBatNo.value        = tSavedRuselt[0][1];
//	fm.SavedMoreInq.value      = tSavedRuselt[0][2];
////	fm.SavedVIPFlag.value      = tSavedRuselt[0][3];
//	fm.SavedInqReason.value    = tSavedRuselt[0][4];
//	fm.SavedInqOrg.value       = tSavedRuselt[0][5];  
//	fm.SavedInqItem.value      = tSavedRuselt[0][6]; 
//	fm.SavedInqDesc.value      = tSavedRuselt[0][7]; 
//	showOneCodeName('llinqreason','SavedInqReason','SavedInqReasonName');   
//	showOneCodeName('stati','SavedInqOrg','SavedInqOrgName');       
//	
//	
//}
//
////[ָ��ȷ��]��ť��Ӧ����
//function Designate()
//{
//  var i = InqApplyGrid.getSelNo();
//  if (i<1)
//  {
//  	alert("��ѡ��һ�����������¼��");
//  	return;
//  }	
//  if(fm.InqPer.value=="" ||fm.InqPer.value==null)
//  {
//  	alert("��ѡ������ˣ�");
//  	return;
//  }
//  fm.fmtransact.value="update";
//  fm.action = './LLInqDistributeSave.jsp';
//  submitForm(); //�ύ
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
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    }
    else
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");       
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    }
    tSaveFlag ="0";
   // InqApplyGridQueryClick();//�����б�
    jQuery("#publicSearch").click();
}
//modify lzf
function InqApplyGridSelClick()
{
  	var i = PublicWorkPoolGrid.getSelNo()-1;
	fm.tClmNo.value = PublicWorkPoolGrid.getRowColData(i,1);
	fm.tInqNo.value = PublicWorkPoolGrid.getRowColData(i,2); 
	fm.tCustomerNo.value = PublicWorkPoolGrid.getRowColData(i,3); 
	fm.tCustomerName.value = PublicWorkPoolGrid.getRowColData(i,4); 
	fm.tInitDept.value = PublicWorkPoolGrid.getRowColData(i,6);    
	fm.MissionID.value = PublicWorkPoolGrid.getRowColData(i,14);    
	fm.SubMissionID.value = PublicWorkPoolGrid.getRowColData(i,15);    
	fm.ActivityID.value = PublicWorkPoolGrid.getRowColData(i,17);            
	divInqPer.style.display = '';
	
	//2009-04-22 zhangzheng
	if(fm.ActivityID.value=='0000009125')
	{
		fm.claimUserTable.value='llgrpclaimuser';
	}
	else
	{
		fm.claimUserTable.value='llclaimuser';
	}
	
	//����������Ϣ�Ĳ�ѯ 2006-01-13 ZHaoRx
	fm.SavedCustomerName.value = "";
	fm.SavedBatNo.value        = "";
	fm.SavedMoreInq.value      = "";
//	fm.SavedVIPFlag.value      = "";
	fm.SavedInqReason.value    = "";
	fm.SavedInqOrg.value       = "";
	fm.SavedInqItem.value      = "";
	fm.SavedInqDesc.value      = "";
	/*var tInqSavedSQL = " select CustomerName,BatNo,"
	                 + " case LocFlag when '0' then '��' when '1' then '��' else '' end,"
	                 + " case VIPFlag when '0' then '��' when '1' then '��' else '' end,"
	                 + " InqRCode,InqDept,InqItem,InqDesc from llinqapply "
	                 + " where clmno='"+fm.tClmNo.value+"' and inqno='"+fm.tInqNo.value+"'";*/
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLInqDistributeInputSql");
	mySql.setSqlId("LLInqDistributeSql3");
	mySql.addSubPara(fm.tClmNo.value ); 
	mySql.addSubPara(fm.tInqNo.value ); 
	//prompt("����������Ϣ�Ĳ�ѯSQL",tInqSavedSQL);                 
	var tSavedRuselt = new Array;
	tSavedRuselt = easyExecSql(mySql.getString());
	fm.SavedCustomerName.value = tSavedRuselt[0][0];
	fm.SavedBatNo.value        = tSavedRuselt[0][1];
	fm.SavedMoreInq.value      = tSavedRuselt[0][2];
//	fm.SavedVIPFlag.value      = tSavedRuselt[0][3];
	fm.SavedInqReason.value    = tSavedRuselt[0][4];
	fm.SavedInqOrg.value       = tSavedRuselt[0][5];  
	fm.SavedInqItem.value      = tSavedRuselt[0][6]; 
	fm.SavedInqDesc.value      = tSavedRuselt[0][7]; 
	showOneCodeName('llinqreason','SavedInqReason','SavedInqReasonName');   
	showOneCodeName('stati','SavedInqOrg','SavedInqOrgName');       
	
	
}

//[ָ��ȷ��]��ť��Ӧ����
function Designate()
{
  var i = PublicWorkPoolGrid.getSelNo();
  if (i<1)
  {
  	alert("��ѡ��һ�����������¼��");
  	return;
  }	
  if(fm.InqPer.value=="" ||fm.InqPer.value==null)
  {
  	alert("��ѡ������ˣ�");
  	return;
  }
  fm.fmtransact.value="update";
  fm.action = './LLInqDistributeSave.jsp';
  submitForm(); //�ύ
}




