//�������ƣ�SendAllnotice.js
//�����ܣ��˱��ȴ�
//�������ڣ�2008-09-27 11:10:36
//������  ��ln
//���¼�¼��  ������    ��������     ����ԭ��/����

//���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnConfirmPage = new turnPageClass();
//�ύ�����水ť��Ӧ����
function submitForm()
{
    var SelNo = WaitContGrid.getSelNo();
    if(SelNo==0)
    {
        alert("����ѡ����Ҫ�ȴ���δ�б�Ͷ������");
        return;
    }
    //2009-2-14 ln add
    var mUWState = WaitContGrid.getRowColData( SelNo- 1, 6);
    //tongmeng 2009-05-14 modify
    //ֻ��4״̬��ѡ��ȴ�
    //if(mUWState==null || mUWState>'4')
    if(mUWState==null || mUWState!='4')
    {
    	//alert("��Ͷ���������Ϻ˱��ȴ���������");
    	alert("ֻ��ѡ��4״̬��Ͷ����!");
    	return;
    }    	
    
    /*var mMissionIDH = WaitContGrid.getRowColData( SelNo- 1, 10);
    var mSubMissionIDH = WaitContGrid.getRowColData( SelNo- 1, 11);*/
    var mMissionIDH = tMissionID;
    var mSubMissionIDH = tSubMissionID;
    document.all('MissionIDH').value = mMissionIDH;
    document.all('SubMissionIDH').value = mSubMissionIDH;
    
	if(document.all('WaitReason').value=="")
	{
		alert("�˱��ȴ�ԭ����Ϊ�գ�");
    	return;
  	}
	if(document.all('WaitReason').value=="05"&&(document.all('Content').value==null||document.all('Content').value==""))
	{
		alert("��¼��˱�Ա˵����");
    	return;
  	}  	

  //alert(tempSubMissionID);
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  fm.submit(); //�ύ 
}

function initClick()
{
	var SelNo = WaitContGrid.getSelNo();
    var mUniteNo = WaitContGrid.getRowColData( SelNo- 1, 1);
    document.all('UniteNo').value = mUniteNo;//��ʾ������--Ϊ�ȴ�Ͷ�����ĺ�ͬ��
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
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    showInfo.close();
       
  }
  else
  { 
		var content="�����ɹ���";
  	var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
  	//parent.close();
    top.close();   
   //queryWaitCont(document.all('InsuredNoH').value);
  }
}

//����������ѯ
function QueryRecord(){

    var SelNo = WaitContGrid.getSelNo();
    if(SelNo==0)
    {
        alert("����ѡ��δ�б�Ͷ������");
        return;
    }
    var mContNo = WaitContGrid.getRowColData( SelNo- 1, 1);
    
	window.open("./RecordQueryMain.jsp?ContNo="+mContNo,"window1","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}

//��ѯ����δ�б�Ͷ����
function queryWaitCont(tInsuredNo)
{	
	var sqlid917160057="DSHomeContSql917160057";
var mySql917160057=new SqlClass();
mySql917160057.setResourceName("uw.WaitReasonSql");//ָ��ʹ�õ�properties�ļ���
mySql917160057.setSqlId(sqlid917160057);//ָ��ʹ�õ�Sql��id
mySql917160057.addSubPara(tContNo);//ָ������Ĳ���
mySql917160057.addSubPara(tInsuredNo);//ָ������Ĳ���
strsql=mySql917160057.getString();
	
//	strsql = " select a.contno, a.prtno, a.appntno, a.appntname, a.polapplydate "
//	       + " ,w.missionprop18 ,(select codename from ldcode where codetype='uwstatus' and code=w.missionprop18),nvl(w.lastoperator,'AUTO')"
//	       + " ,nvl((select nvl(defaultoperator,missionprop14) from lwmission where MissionID=w.MissionID and submissionid=w.submissionid and activityid='0000001100' and (defaultoperator is not null or (missionprop14 is not null and missionprop14<>'0000000000'))),'������')"
//	       //+ "  ,nvl((select Operator from LWLock where MissionID=w.MissionID and submissionid=w.submissionid),'������')"
//	       + " ,w.missionid,w.submissionid "
//	       + " from lwmission w,lccont a,lcinsured b where w.activityid='0000001100' and w.missionprop18>='1' and w.missionprop18<='8' and w.MissionProp2<>'"+tContNo+"' "
//	       + " and a.contno = w.MissionProp2 and a.contno=b.contno and b.insuredno='" +tInsuredNo+ "' ";    
	
  turnPage.queryModal(strsql,WaitContGrid); 
}

//��ʼ��������
function initUniteNo()
{	
	var sqlid917160147="DSHomeContSql917160147";
var mySql917160147=new SqlClass();
mySql917160147.setResourceName("uw.WaitReasonSql");//ָ��ʹ�õ�properties�ļ���
mySql917160147.setSqlId(sqlid917160147);//ָ��ʹ�õ�Sql��id
mySql917160147.addSubPara(tContNo);//ָ������Ĳ���
strsql=mySql917160147.getString();
	
//	strsql = " select w.missionprop21 "
//	       + " from lwmission w,lcinsured b "
//	       + " where w.activityid='0000001100' and w.missionprop18='6' "
//	       + " and w.MissionProp2=b.contno and w.missionprop21 is not null "
//	       + " and b.insuredno in (select insuredno from lcinsured where contno='"+tContNo+"') ";    
	
    turnConfirmPage.strQueryResult = easyQueryVer3(strsql, 1, 0, 1);
    if (!turnConfirmPage.strQueryResult) {
    		//��ͬ����Ϊ������
    		document.all('UniteNo').value = tContNo;
    	}
    	else
    	{
    		turnConfirmPage.arrDataCacheSet = decodeEasyQueryResult(turnConfirmPage.strQueryResult);      
		      document.all('UniteNo').value = turnConfirmPage.arrDataCacheSet[0][0]; 
    	}
    		 
}

//��ѯ�����˿ͻ���
function queryInsuredNo()
{
		var sqlid917160238="DSHomeContSql917160238";
var mySql917160238=new SqlClass();
mySql917160238.setResourceName("uw.WaitReasonSql");//ָ��ʹ�õ�properties�ļ���
mySql917160238.setSqlId(sqlid917160238);//ָ��ʹ�õ�Sql��id
mySql917160238.addSubPara(tContNo);//ָ������Ĳ���
strsql=mySql917160238.getString();

		
//		strsql = "select LCInsured.InsuredNo,LCInsured.Name,LCInsured.IDType,LCInsured.IDNo,case when LCInsured.INSUREDSTAT='1' then '����ͣ' end from LCInsured where 1=1"
//					  + " and LCInsured.Contno = '" + tContNo + "'";
  turnPage1.queryModal(strsql,PolAddGrid);
}

/*********************************************************************
 *  ��ѯ�����˿ͻ��ţ����뱻���˲�����Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showInsuredInfo()
{
	  var tSelNo = PolAddGrid.getSelNo();
		var tInsuredNo = PolAddGrid.getRowColData(tSelNo - 1,1);
         document.all('InsuredNoH').value = tInsuredNo;	
        // document.all('UniteNo').value = tInsuredNo;
         queryWaitCont(tInsuredNo);	
}

/*********************************************************************
 *  ��ѯͶ�����˱��ȴ�ԭ�򣬲���ʾ����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function iniReason()
{	  
     //initial
	//alert(tOtherFlag);
     if(tType == '2')
	 {	    
	    document.all('uwButton6').disabled=true;
	     sure.disabled=true;
	    // alert(2);
	     document.all('Content').readOnly = true;
	     document.all('WaitReason').readOnly = true;
	     divQuery.style.display='';
	 }         
     //query
     var strsql="";
     if(tOtherFlag=="1"){//alert(183);
    	 
    	 var sqlid917160322="DSHomeContSql917160322";
var mySql917160322=new SqlClass();
mySql917160322.setResourceName("uw.WaitReasonSql");//ָ��ʹ�õ�properties�ļ���
mySql917160322.setSqlId(sqlid917160322);//ָ��ʹ�õ�Sql��id
mySql917160322.addSubPara(tContNo);//ָ������Ĳ���
strsql=mySql917160322.getString();

    	 
//    	 strsql = " select MissionProp1,MissionProp10,MissionProp4 "
//		       + " from lbmission w where w.activityid='0000001100' and w.MissionProp2 in (select prtno from lccont where contno='"+tContNo+"') ";
     }else{//alert(186);
		  
		  var sqlid917160404="DSHomeContSql917160404";
var mySql917160404=new SqlClass();
mySql917160404.setResourceName("uw.WaitReasonSql");//ָ��ʹ�õ�properties�ļ���
mySql917160404.setSqlId(sqlid917160404);//ָ��ʹ�õ�Sql��id
mySql917160404.addSubPara(tContNo);//ָ������Ĳ���
strsql=mySql917160404.getString();
		  
//		  strsql = " select MissionProp1,MissionProp10,MissionProp4 "
//		       + " from lwmission w where w.activityid='0000001100' and w.MissionProp2='"+tContNo+"' ";
     }//prompt("",strsql);
	  turnConfirmPage.strQueryResult = easyQueryVer3(strsql, 1, 0, 1);
      turnConfirmPage.arrDataCacheSet = decodeEasyQueryResult(turnConfirmPage.strQueryResult);
      document.all('PrtNo').value = turnConfirmPage.arrDataCacheSet[0][0];
      document.all('ManageCom').value = turnConfirmPage.arrDataCacheSet[0][1];
      document.all('AgentCode').value = turnConfirmPage.arrDataCacheSet[0][2];     
	  
}  

/*********************************************************************
 *  ��ѯͶ�����˱��ȴ�ԭ�򣬲���ʾ����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showReasonInfo()
{    
     //query
    // alert(tOtherFlag);
	if(tOtherFlag=='1'){
		var sqlid917160521="DSHomeContSql917160521";
var mySql917160521=new SqlClass();
mySql917160521.setResourceName("uw.WaitReasonSql");//ָ��ʹ�õ�properties�ļ���
mySql917160521.setSqlId(sqlid917160521);//ָ��ʹ�õ�Sql��id
mySql917160521.addSubPara(tContNo);//ָ������Ĳ���
mySql917160521.addSubPara(tContNo);//ָ������Ĳ���
strsql=mySql917160521.getString();
		
//		strsql = " select w.missionprop22,(select codename from ldcode where codetype='waitreason' and code=w.missionprop22),w.missionprop23,w.missionprop21 "
//		       + " from lwmission w where w.activityid='0000001100' and w.missionprop18 ='6' and w.MissionProp2 in (select prtno from lccont where contno='"+tContNo+"') "
//		       + " union "
//		       + " select w.missionprop22,(select codename from ldcode where codetype='waitreason' and code=w.missionprop22),w.missionprop23,w.missionprop21 "
//		       + " from lbmission w where w.activityid='0000001100' and w.missionprop18 ='6' and w.MissionProp2 in (select prtno from lccont where contno='"+tContNo+"') ";
	}else{
	  var sqlid917160726="DSHomeContSql917160726";
var mySql917160726=new SqlClass();
mySql917160726.setResourceName("uw.WaitReasonSql");//ָ��ʹ�õ�properties�ļ���
mySql917160726.setSqlId(sqlid917160726);//ָ��ʹ�õ�Sql��id
mySql917160726.addSubPara(tContNo);//ָ������Ĳ���
strsql=mySql917160726.getString();

	  
	  
//	  strsql = " select w.missionprop22,(select codename from ldcode where codetype='waitreason' and code=w.missionprop22),w.missionprop23,w.missionprop21 "
//	       + " from lwmission w where w.activityid='0000001100' and w.missionprop18 ='6' and w.MissionProp2='"+tContNo+"' ";
	}
	  turnConfirmPage.strQueryResult = easyQueryVer3(strsql, 1, 0, 1);
      if (!turnConfirmPage.strQueryResult) {
    		alert("û�к˱��ȴ���Ϣ��");
    		return false;
    	}    	
    	      turnConfirmPage.arrDataCacheSet = decodeEasyQueryResult(turnConfirmPage.strQueryResult);      
		      document.all('WaitReason').value = turnConfirmPage.arrDataCacheSet[0][0];
		      document.all('Reason').value = turnConfirmPage.arrDataCacheSet[0][1];
		      document.all('Content').value = turnConfirmPage.arrDataCacheSet[0][2];  
		      document.all('UniteNo').value = turnConfirmPage.arrDataCacheSet[0][3];    
			  //alert(5);
      
}   

