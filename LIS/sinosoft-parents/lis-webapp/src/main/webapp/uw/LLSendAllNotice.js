//�������ƣ�SendAllnotice.js
//�����ܣ������¼��
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����

//���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var flag;
var k = 0;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPageQuest = new turnPageClass();
var turnPageQuestD = new turnPageClass();
var brrResult;
var strSQL = "";
//�ύ�����水ť��Ӧ����
function submitForm()
{
	lockScreen('lkscreen');
/*	 if(document.all('NoticeType').value=="82")
		{
			if (checkHospitalGridValue() == false)
   		{
    		  return;
   		}
  	}*/
  //tongmeng 2007-11-26 add
  //���ӶԹ���������У��,�Լ�ȡ�����Ļ���Ϊ�˱�֪ͨ�鷢�ŵ�subMissionId
//  var tSQL_SubMissionId = "select nvl(max(submissionid),'x') from lwmission where missionid='"+tMissionID+"' "
//                           + " and activityid='0000005510' ";
//  var tempSubMissionID = "";
//  var brr = easyExecSql(tSQL_SubMissionId);prompt("",tSQL_SubMissionId);return false;
//  tempSubMissionID = brr[0][0];
//  if(tempSubMissionID == 'x' )
//  {
//  	alert(" �����ѷ���δ���յĺ˱�֪ͨ��,�������ٷ��ͺ˱�֪ͨ��! ");
//  	return false;
//  }
  //alert(document.all('SubMissionID').value);
  //document.all('SubMissionID').value = tempSubMissionID;
//	alert(fm.ContNo.value);return false;
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
  fm.submit(); //�ύ 
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
	unlockScreen('lkscreen');
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
	showInfo1 = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo1.focus();
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
		parent.close();
		top.close(); 
   
  	

    //ִ����һ������
  }
}

function QuestQuery()
{	
	
	// ��дSQL���

	k++;
   tCode = document.all('NoticeType').value;
	
//		var	strSQL = "select cont from ldcodemod where "+k+"="+k				 	
//				 + " and codetype = 'uwnoticetype' and Code = '"+tCode+"'";

		var sqlid1="LLSendAllNoticeSql1";
   	 	var mySql1=new SqlClass();
   	 	mySql1.setResourceName("uw.LLSendAllNoticeSql");
   	 	mySql1.setSqlId(sqlid1); //ָ��ʹ��SQL��id
   	 	mySql1.addSubPara(k);//ָ���������
   	    mySql1.addSubPara(k);
   	    mySql1.addSubPara(tCode);
   	 	var strSQL = mySql1.getString();

	
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
   // alert("û��¼����������");
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  var returnstr = "";
  var n = turnPage.arrDataCacheSet.length;
  //alert("N:"+n);
  if (n > 0)
  {
  	m = turnPage.arrDataCacheSet[0].length;
  		//alert("M:"+m);
  		if (m > 0)
  		{
  			//alert("turnPage:"+turnPage.arrDataCacheSet[0][0]);
			returnstr = turnPage.arrDataCacheSet[0][0];
			
			document.all('Content').value = returnstr
  		}
  		else
  		{
  			alert("û��¼����������");
  			return "";
  		}
  	
  }
  else
  {
  	alert("û��¼����������");
	return "";
  }

  if (returnstr == "")
  {
  	alert("û��¼����������");
  }
  
   
 
  return returnstr;
}

function afterCodeSelect( cCodeName, Field )
{

	var tCode = document.all('NoticeType').value;


	if(cCodeName=="bquwnotice" || cCodeName=="uwnoticetype")
	{
	if(tCode=="84"||tCode=="87"||tCode=="BQ83"||tCode=="BQ82")
	{
		//divUWSpec1.style.display = 'none';
		//divUWSpec.style.display = 'none';
		divnoticecontent.style.display = '';
		QuestQuery(tCode);
		divLCPol.style.display = 'none';
	}
else if(tCode=="86"||tCode=="88"||tCode=="BQ87")
	{
		//divUWSpec1.style.display = 'none';
		//divUWSpec.style.display = 'none';
		divnoticecontent.style.display = '';
		//alert(document.all('ContNo').value);
//		var tsql = "select riskcode,uwflag from lcpol where contno = '"+document.all('ContNo').value+"' and mainpolno!=polno and uwflag in ('1','2')";
		
		var sqlid2="LLSendAllNoticeSql2";
   	 	var mySql2=new SqlClass();
   	 	mySql2.setResourceName("uw.LLSendAllNoticeSql");
   	 	mySql2.setSqlId(sqlid2); //ָ��ʹ��SQL��id
   	 	mySql2.addSubPara(document.all('ContNo').value);//ָ���������
   	 	var tsql = mySql2.getString();
		
		if(tCode=="BQ87")
		{
//		    tsql = "select riskcode,uwflag from lppol where contno = '"+document.all('ContNo').value+"' and mainpolno!=polno and uwflag in ('1','2') and edorno = '" + fm.EdorNo.value + "' and edortype = '" + fm.EdorType.value + "'";
		    
		    var sqlid3="LLSendAllNoticeSql3";
	   	 	var mySql3=new SqlClass();
	   	 	mySql3.setResourceName("uw.LLSendAllNoticeSql");
	   	 	mySql3.setSqlId(sqlid3); //ָ��ʹ��SQL��id
	   	 	mySql3.addSubPara(document.all('ContNo').value);//ָ���������
	   	    mySql3.addSubPara(fm.EdorNo.value);
	   	    mySql3.addSubPara(fm.EdorType.value);
	   	 	tsql = mySql3.getString();
		    
		}
		brrResult=easyExecSql(tsql);
		if(brrResult==null)
		{
			QuestQuery(tCode);
		}
	else{
		var str = "���˱���";
		for (var p = 0;p<brrResult.length;p++)
		  {
//		  	tsql = "select riskstatname from lmrisk where riskcode = '"+brrResult[p][0]+"'";
		  	
		  	var sqlid4="LLSendAllNoticeSql4";
	   	 	var mySql4=new SqlClass();
	   	 	mySql4.setResourceName("uw.LLSendAllNoticeSql");
	   	 	mySql4.setSqlId(sqlid4); //ָ��ʹ��SQL��id
	   	 	mySql4.addSubPara(brrResult[p][0]);//ָ���������
	   	 	tsql = mySql4.getString();
		  	
			  var crrResult = easyExecSql(tsql);
			  if(crrResult!=null)
			    {
			      if(brrResult[p][1]=="1")
			        {
			      	str = str + "�ܱ�" + crrResult[0][0];
			      	}	
			      else if (brrResult[p][1]=="2")
			      	{
			      	str = str + "����" + crrResult[0][0];
			      	}
			    }
			    if(p+1<brrResult.length)
			    {
			    	str += "��";
			    }
			    else 
			    {
			        if(tCode!="BQ87")
			        {
			    	   str += "��";
			        }
			    }
			}

			
			document.all('Content').value = str;
		}
		divnoticecontent.style.display = 'none'; 
		divLCPol.style.display = 'none';
	}
else if(tCode=="81"||tCode=="BQ86")
	{

		//divUWSpec1.style.display = 'none';
		//divUWSpec.style.display = 'none';
		divnoticecontent.style.display = '';
		fm.Content.value = "";
		divLCPol.style.display = 'none';
	}
else if(tCode=="89"||tCode=="BQ88")
	{
		divnoticecontent.style.display = 'none';
		//divUWSpec1.style.display = '';
		//divUWSpec.style.display = '';
		fm.Content.value = "";
		divLCPol.style.display = 'none';
	}
else if(tCode=="82")
	{
		//divUWSpec1.style.display = 'none';
		//divUWSpec.style.display = 'none';
		divnoticecontent.style.display = 'none';
		divLCPol.style.display = '';
		
		}
else
	{
		//divUWSpec1.style.display = 'none';
		//divUWSpec.style.display = 'none';
		divnoticecontent.style.display = 'none';
		divLCPol.style.display = 'none';
	}
}
}

function initLWMission()
{
	var MissionID = fm.MissionID.value;
//		var strSQL = "select submissionid from lwmission where missionid ='"+MissionID+"'"
//							 + " and activityid = '0000000100'"
//								;
		
		var sqlid5="LLSendAllNoticeSql5";
   	 	var mySql5=new SqlClass();
   	 	mySql5.setResourceName("uw.LLSendAllNoticeSql");
   	 	mySql5.setSqlId(sqlid5); //ָ��ʹ��SQL��id
   	 	mySql5.addSubPara(MissionID);//ָ���������
   	    var strSQL = mySql5.getString();
		
							
 var brr = easyExecSql(strSQL);//prompt("",strSQL);
	if ( brr )  //�Ѿ����뱣���
 	{
 		var tSubMissionID = brr[0][0];
 		fm.SubMissionID.value = tSubMissionID;
	}

}


function initLoprtManager()
{
	//tongmeng 2007-11-12 modify
	//����ҵ��Ա֪ͨ���ѯ
	var tContNo = document.all('ContNo').value;
//	var strSQL = "select prtseq,otherno,othernotype,agentcode,code,(select codename from ldcode where codetype='printstate' and code=stateflag) from loprtmanager where otherno = '"+tContNo+"'"
//	        + " and Code in ('TB90','TB89','14','00','06','81','82','83','84','85','86','87','88','89', 'BQ80', 'BQ81', 'BQ82', 'BQ83', 'BQ84', 'BQ85', 'BQ86', 'BQ87', 'BQ88', 'BQ89')"
//	        + " and (oldprtseq is null or prtseq = oldprtseq)";
//	
	    var sqlid6="LLSendAllNoticeSql6";
	 	var mySql6=new SqlClass();
	 	mySql6.setResourceName("uw.LLSendAllNoticeSql");
	 	mySql6.setSqlId(sqlid6); //ָ��ʹ��SQL��id
	 	mySql6.addSubPara(tContNo);//ָ���������
	    var strSQL = mySql6.getString();
	
	initPolGrid();       
	        
	        //��ѯSQL�����ؽ���ַ���
//  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 
//    
// initPolGrid();
//
//  //�ж��Ƿ��ѯ�ɹ�
//  if (!turnPage.strQueryResult) {
//  	PolGrid.clearData();
//  	alert("���ݿ���û���������������ݣ�");
// 
//    return false;
//  }
// 
//  //��ѯ�ɹ������ַ��������ض�ά����
//  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
// 
//  //���ó�ʼ������MULTILINE����
//  turnPage.pageDisplayGrid = PolGrid;   
//         
//  //����SQL���
//  turnPage.strQuerySql   = strSQL;
// 
//  //���ò�ѯ��ʼλ��
//  turnPage.pageIndex = 0; 
// 
//  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
//  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex,MAXSCREENLINES);
// 
//  //����MULTILINE������ʾ��ѯ���
//      displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
 turnPage2.queryModal(strSQL, PolGrid); 
	        
}

function queryresult() 
{
	  var tSelNo = PolGrid.getSelNo();
    var tCode = PolGrid.getRowColData(tSelNo - 1,5);
    var tPrtseq = PolGrid.getRowColData(tSelNo - 1,1);
    var tResult = "";
    //alert(tCode);
    
    if(tCode == "81")
    {
      divresult.style.display = '';
 //     tSQL = "select remark from loprtmanager where prtseq = '"+tPrtseq+"'";
      
        var sqlid7="LLSendAllNoticeSql7";
	 	var mySql7=new SqlClass();
	 	mySql7.setResourceName("uw.LLSendAllNoticeSql");
	 	mySql7.setSqlId(sqlid7); //ָ��ʹ��SQL��id
	 	mySql7.addSubPara(tPrtseq);//ָ���������
	 	tSQL = mySql7.getString();
      
      arrResult=easyExecSql(tSQL);
      document.all('result').value = arrResult[0][0];
    	return;
    	} 
    if(tCode == "89")
    {
    	//alert(1);
      divresult.style.display = '';
//      tSQL = "select askcode,(select CodeName from LDcode where LDcode.Code = LCRReportPrt.askcode and CodeType = 'rreporttype' ) from LCRReportPrt where prtseq = '"+tPrtseq+"'";
     
        var sqlid8="LLSendAllNoticeSql8";
	 	var mySql8=new SqlClass();
	 	mySql8.setResourceName("uw.LLSendAllNoticeSql");
	 	mySql8.setSqlId(sqlid8); //ָ��ʹ��SQL��id
	 	mySql8.addSubPara(tPrtseq);//ָ���������
	 	tSQL = mySql8.getString();
      
      arrResult=easyExecSql(tSQL);
      //alert(arrResult.length);
      for(var i=1;i<=arrResult.length;i++ )
       { 
         tResult = tResult+i+":"+arrResult[i-1][1]+" ";
       }
      document.all('result').value = tResult;
    	return;
    	}
    else 
    	divresult.style.display = 'none';

	}

//�ѷ���Լ 
function queryUWSpecD(tClmNo,tBatNo)
{
	if(_DBT==_DBO){
//		strSQL = "select speccontent"
//            + " ,operator ,makedate,'' "
//            + " ,(select codename from ldcode where codetype='question' and code=(select sequenceno from lcinsured where contno=cNo and insuredno=iNo))"
//            + " ,a,''"
//            + " ,iNo"             
//   + "   from"
//   + "(select speccontent"
//            + " ,operator ,makedate,contno cNo"                         
//            + " ,(select codename from ldcode where codetype='printstate' and code=s.prtflag) a"
//            + " ,s.customerno iNo"             
//   + "   from lluwspecmaster s "
//   + "   where clmno = '"+tClmNo+"' and batno = '"+tBatNo
//   +"' and prtflag is not null and needprint='Y') s";
//		
		var sqlid9="LLSendAllNoticeSql9";
	 	var mySql9=new SqlClass();
	 	mySql9.setResourceName("uw.LLSendAllNoticeSql");
	 	mySql9.setSqlId(sqlid9); //ָ��ʹ��SQL��id
	 	mySql9.addSubPara(tClmNo);//ָ���������
	 	mySql9.addSubPara(tBatNo);
	 	strSQL = mySql9.getString();
		
	}else if(_DBT==_DBM){
//		strSQL = "select speccontent"
//            + " ,operator ,makedate,'' "
//            + " ,(select codename from ldcode where codetype='question' and code=(select sequenceno from lcinsured where contno=cNo and insuredno=iNo))"
//            + " ,a,''"
//            + " ,iNo"             
//   + "   from"
//   + "(select speccontent"
//            + " ,operator ,makedate,contno cNo"                         
//            + " ,(select codename from ldcode where codetype='printstate' and code=s.prtflag) a"
//            + " ,s.customerno iNo"             
//   + "   from lluwspecmaster s "
//   + "   where clmno = '"+tClmNo+"' and batno = '"+tBatNo
//   +"' and prtflag is not null and prtflag!='' and needprint='Y') s";
		
		var sqlid10="LLSendAllNoticeSql10";
	 	var mySql10=new SqlClass();
	 	mySql10.setResourceName("uw.LLSendAllNoticeSql");
	 	mySql10.setSqlId(sqlid10); //ָ��ʹ��SQL��id
	 	mySql10.addSubPara(tClmNo);//ָ���������
	 	mySql10.addSubPara(tBatNo);
	 	strSQL = mySql10.getString();
		
		
	}		
	
	//prompt("",strSQL);
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
  	divUWSpecD.style.display = 'none';
    return false;
 }
 
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
 
  //���ó�ʼ������MULTILINE����
  turnPage.pageDisplayGrid = UWSpecDGrid;   
         
  //����SQL���
  turnPage.strQuerySql   = strSQL;
 
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex = 0; 
 
 //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex,MAXSCREENLINES);
 
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

  return true;	
}

//������Լ
function querySpec(tClmNo,tBatNo)
{	
//			strSQL = "select speccontent"
//                         + " ,operator ,makedate,'' "
//                         + " ,(select codename from ldcode where codetype='question' and code=(select sequenceno from lcinsured where contno=cNo and insuredno=iNo))"
//                         + " ,'Y',iNo"             
//                + "   from"
//				+ "(select speccontent"
//                         + " ,operator ,makedate,contno cNo "                         
//                         + " ,s.customerno iNo"             
//                + "   from lluwspecmaster s "
//                + "   where clmno = '"+tClmNo+"' and batno='"+tBatNo
//                +"' and (prtflag is null or prtflag='')and needprint='Y') s";
//			
			var sqlid11="LLSendAllNoticeSql11";
		 	var mySql11=new SqlClass();
		 	mySql11.setResourceName("uw.LLSendAllNoticeSql");
		 	mySql11.setSqlId(sqlid11); //ָ��ʹ��SQL��id
		 	mySql11.addSubPara(tClmNo);//ָ���������
		 	mySql11.addSubPara(tBatNo);
		 	strSQL = mySql11.getString();
			
	//prompt("",strSQL);
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
  	divUWSpec.style.display = 'none';
    return false;
 }
 
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
 
  //���ó�ʼ������MULTILINE����
  turnPage.pageDisplayGrid = UWSpecGrid;   
         
  //����SQL���
  turnPage.strQuerySql   = strSQL;
 
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex = 0; 
 
 //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex,MAXSCREENLINES);
 
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
	
  return true;	
}

//��ѯ�ӷ�
function queryAddFee(tClmNo,tBatNo)
{     
//			strSQL = "select rc ,(select riskname from lmriskapp where riskcode=rc) riskname"
//			       + " ,a,payplancode,prem,polno,SuppRiskScore,operator,makedate,'' "
//			       + " ,(select codename from ldcode where codetype='question' and code=(select sequenceno from lcinsured where contno=cNo and insuredno=iNo))"
//			       + " ,'Y',iNo"
//			       + " from "
//			       + " (select (select riskcode from lcpol where polno=p.polno) rc,payplantype,contno cNo"
//			       + " ,(case trim(payplantype) when '01' then '�����ӷ�' when '02' then 'ְҵ�ӷ�' when '03' then '��ס�ؼӷ�' when '04' then '���üӷ�' end) a,payplancode,prem,polno,operator,makedate"
//			       + " ,(select insuredno from lcpol where polno=p.polno) iNo,SuppRiskScore"
//			       + " from lluwpremmaster p where  payplancode like '000000%%' "
//                + " and clmno = '"+tClmNo+"' and batno='"+tBatNo+"') s";
//			
			var sqlid12="LLSendAllNoticeSql12";
		 	var mySql12=new SqlClass();
		 	mySql12.setResourceName("uw.LLSendAllNoticeSql");
		 	mySql12.setSqlId(sqlid12); //ָ��ʹ��SQL��id
		 	mySql12.addSubPara(tClmNo);//ָ���������
		 	mySql12.addSubPara(tBatNo);
		 	strSQL = mySql12.getString();
			
                //prompt("",strSQL);
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
  	divUWAddFee.style.display = 'none';
    return false;
 }
 
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
 
  //���ó�ʼ������MULTILINE����
  turnPage.pageDisplayGrid = UWAddFeeGrid;   
         
  //����SQL���
  turnPage.strQuerySql   = strSQL;
 
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex = 0; 
 
 //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex,MAXSCREENLINES);
 
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

}

//��ѯ�б��ƻ����
function queryUWPlan(tContNo)
{     
	if(_DBT==_DBO){
//		strSQL = "select riskcode,(select riskname from lmriskapp where riskcode=a.riskcode),amnt, mult,prem, concat(payendyear,payendyearflag),concat(insuyear,insuyearflag),payintv,"
//		    + " b.operator,b.modifydate,'','Ͷ����','Y'  "
//		    + " from lcpol a,lcuwmaster b  "
//		    + " where a.contno = '"+tContNo+"' and a.proposalno=b.proposalno and b.changepolflag is not null and b.changepolflag='1' "
//		    + " order by a.mainpolno,a.polno  ";
		
		var sqlid13="LLSendAllNoticeSql13";
	 	var mySql13=new SqlClass();
	 	mySql13.setResourceName("uw.LLSendAllNoticeSql");
	 	mySql13.setSqlId(sqlid13); //ָ��ʹ��SQL��id
	 	mySql13.addSubPara(tContNo);//ָ���������
	 	strSQL = mySql13.getString();
		
	}else if(_DBT==_DBM){
//		strSQL = "select riskcode,(select riskname from lmriskapp where riskcode=a.riskcode),amnt, mult,prem, concat(payendyear,payendyearflag),concat(insuyear,insuyearflag),payintv,"
//		    + " b.operator,b.modifydate,'','Ͷ����','Y'  "
//		    + " from lcpol a,lcuwmaster b  "
//		    + " where a.contno = '"+tContNo+"' and a.proposalno=b.proposalno and b.changepolflag is not null and b.changepolflag!='' and b.changepolflag='1' "
//		    + " order by a.mainpolno,a.polno  ";
		
		var sqlid14="LLSendAllNoticeSql14";
	 	var mySql14=new SqlClass();
	 	mySql14.setResourceName("uw.LLSendAllNoticeSql");
	 	mySql14.setSqlId(sqlid14); //ָ��ʹ��SQL��id
	 	mySql14.addSubPara(tContNo);//ָ���������
	 	strSQL = mySql14.getString();
		
	}
			
                
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
  	divUWPlan.style.display = 'none';
    return false;
 }
 
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
 
  //���ó�ʼ������MULTILINE����
  turnPage.pageDisplayGrid = UWPlanGrid;   
         
  //����SQL���
  turnPage.strQuerySql   = strSQL;
 
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex = 0; 
 
 //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex,MAXSCREENLINES);
 
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

}

//��ѯ�ѷ�����
function queryUWExistD(tClmNo,tBatNo)
{			
	if(_DBT==_DBO){
//		strSQL = "select Contente,(select username from lduser where usercode=l.operator),makedate"
//	        + " ,'Ͷ����'"
//	        + " ,(select codename from ldcode where codetype='printstate' and code=l.replyflag)"
//	       + " ,(case l.ReplyOperator when null then 'δ�ظ�' else '�ѻظ�' end)"			      
//	       + " from LLUWRReport l where 1=1 "
//        + " and clmno = '"+tClmNo+"' and  and replyflag is not null";
//		
		var sqlid15="LLSendAllNoticeSql15";
	 	var mySql15=new SqlClass();
	 	mySql15.setResourceName("uw.LLSendAllNoticeSql");
	 	mySql15.setSqlId(sqlid15); //ָ��ʹ��SQL��id
	 	mySql15.addSubPara(tClmNo);//ָ���������
	 	strSQL = mySql15.getString();
		
	}else if(_DBT==_DBM){
//		strSQL = "select Contente,(select username from lduser where usercode=l.operator),makedate"
//	        + " ,'Ͷ����'"
//	        + " ,(select codename from ldcode where codetype='printstate' and code=l.replyflag)"
//	       + " ,(case l.ReplyOperator when null then 'δ�ظ�' else '�ѻظ�' end)"			      
//	       + " from LLUWRReport l where 1=1 "
//        + " and clmno = '"+tClmNo+"' and  and replyflag is not null and replyflag!=''";
		
		var sqlid16="LLSendAllNoticeSql16";
	 	var mySql16=new SqlClass();
	 	mySql16.setResourceName("uw.LLSendAllNoticeSql");
	 	mySql16.setSqlId(sqlid16); //ָ��ʹ��SQL��id
	 	mySql16.addSubPara(tClmNo);//ָ���������
	 	strSQL = mySql16.getString();
		
	}
			
                
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
  	divUWExistD.style.display = 'none';
    return false;
 }
 
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
 
  //���ó�ʼ������MULTILINE����
  turnPage.pageDisplayGrid = UWExistDGrid;   
         
  //����SQL���
  turnPage.strQuerySql   = strSQL;
 
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex = 0; 
 
 //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex,MAXSCREENLINES);
 
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);	

}

//��ѯ��������
function queryUWExist(tContNo)
{
//			strSQL = "select Contente,(select username from lduser where usercode=l.operator),makedate"
//			        + " ,'Ͷ����'"
//			       + " ,'Y'"
//			       + " from LPRReport l where 1=1 "
//                + " and contno = '"+tContNo+"' and (replyflag is null or replyflag='')";
			
			var sqlid17="LLSendAllNoticeSql17";
		 	var mySql17=new SqlClass();
		 	mySql17.setResourceName("uw.LLSendAllNoticeSql");
		 	mySql17.setSqlId(sqlid17); //ָ��ʹ��SQL��id
		 	mySql17.addSubPara(tContNo);//ָ���������
		 	strSQL = mySql17.getString();
			

  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
  	divUWExist.style.display = 'none';
    return false;
 }
 
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
 
  //���ó�ʼ������MULTILINE����
  turnPage.pageDisplayGrid = UWExistGrid;   
         
  //����SQL���
  turnPage.strQuerySql   = strSQL;
 
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex = 0; 
 
 //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex,MAXSCREENLINES);
 
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);	

}

//��ѯ�ѷ����
function queryUWHealthD(tClmNo,tBatNo)
{
	if(_DBT==_DBO){
//		strSQL = "select '', operator, makedate, '', (select codename from ldcode where codetype='question' and code=customercode), printstate, replystate, prtseq"
//            +" from (select operator, makedate, "
//            +" (case customertype when 'A' then '0' else (select sequenceno from lcinsured where contno=a.contno and insuredno=customerno) end) customercode,"
//            +" (select codename from ldcode where codetype='printstate' and code=printflag) printstate,"
//	        +" ( case peresult when null then 'δ�ظ�' else '�ѻظ�' end) replystate, "
//            +" prtseq "
//		    +" from lluwpenotice a where 1=1 "
//            +" and a.clmno = '"+tClmNo+"' and a.batno='"+tBatNo
//            +"' and a.printflag is not null) s";
		
		var sqlid18="LLSendAllNoticeSql18";
	 	var mySql18=new SqlClass();
	 	mySql18.setResourceName("uw.LLSendAllNoticeSql");
	 	mySql18.setSqlId(sqlid18); //ָ��ʹ��SQL��id
	 	mySql18.addSubPara(tClmNo);//ָ���������
	 	mySql18.addSubPara(tBatNo);
	 	strSQL = mySql18.getString();
		
	}else if(_DBT==_DBM){
//		strSQL = "select '', operator, makedate, '', (select codename from ldcode where codetype='question' and code=customercode), printstate, replystate, prtseq"
//            +" from (select operator, makedate, "
//            +" (case customertype when 'A' then '0' else (select sequenceno from lcinsured where contno=a.contno and insuredno=customerno) end) customercode,"
//            +" (select codename from ldcode where codetype='printstate' and code=printflag) printstate,"
//	        +" ( case peresult when null then 'δ�ظ�' else '�ѻظ�' end) replystate, "
//            +" prtseq "
//		    +" from lluwpenotice a where 1=1 "
//            +" and a.clmno = '"+tClmNo+"' and a.batno='"+tBatNo
//            +"' and a.printflag is not null and a.printflag!='') s";
		
		var sqlid19="LLSendAllNoticeSql19";
	 	var mySql19=new SqlClass();
	 	mySql19.setResourceName("uw.LLSendAllNoticeSql");
	 	mySql19.setSqlId(sqlid19); //ָ��ʹ��SQL��id
	 	mySql19.addSubPara(tClmNo);//ָ���������
	 	mySql19.addSubPara(tBatNo);
	 	strSQL = mySql19.getString();
		
	}
			
  //prompt("",strSQL);
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
  	divUWHealthD.style.display = 'none';
    return false;
 }
 
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  var m = turnPage.arrDataCacheSet.length;
  var tContent ="";
  var arrResult = "";
  var tSQL;
  //alert(m);
  //��ѯ�������
  for(var i=0; i<m; i++)
  {
//    tSQL = "select peitemname from lluwpenoticeitem where 1=1 "
//	       + " and clmno = '"+tClmNo+"' and batno='"+tBatNo+"' and prtseq = '"+turnPage.arrDataCacheSet[i][7]+"' order by peitemcode" ;
//    
    var sqlid20="LLSendAllNoticeSql20";
 	var mySql20=new SqlClass();
 	mySql20.setResourceName("uw.LLSendAllNoticeSql");
 	mySql20.setSqlId(sqlid20); //ָ��ʹ��SQL��id
 	mySql20.addSubPara(tClmNo);//ָ���������
 	mySql20.addSubPara(tBatNo);
 	mySql20.addSubPara(turnPage.arrDataCacheSet[i][7]);
 	tSQL = mySql20.getString();
    
	arrResult=easyExecSql(tSQL);//prompt("",tSQL);
	for(var j=0; j<arrResult.length; j++)
	   tContent = tContent + arrResult[j][0]+" ;";
	turnPage.arrDataCacheSet[i][0] = tContent;
  }    
 //alert(627);
  //���ó�ʼ������MULTILINE����
  turnPage.pageDisplayGrid = UWHealthDGrid;   
         
  //����SQL���
  turnPage.strQuerySql   = strSQL;
 
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex = 0; 
 
 //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex,MAXSCREENLINES);
 
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);	

}

//��ѯ�������
function queryUWHealth(tClmNo,tBatNo)
{
//			strSQL = "select '', operator, makedate, '', (select codename from ldcode where codetype='question' and code=customercode), '', prtseq"
//                +" from (select operator, makedate, (case customertype when 'A' then '0' else (select sequenceno from lcinsured where contno=a.contno and insuredno=customerno) end) customercode, prtseq"
//			                   + " from lluwpenotice a where 1=1 "
//                               + " and a.clmno = '"+tClmNo+"' and a.batno='"+tBatNo+"' and (a.printflag is null or a.printflag='')) s";
//			
			var sqlid21="LLSendAllNoticeSql21";
		 	var mySql21=new SqlClass();
		 	mySql21.setResourceName("uw.LLSendAllNoticeSql");
		 	mySql21.setSqlId(sqlid21); //ָ��ʹ��SQL��id
		 	mySql21.addSubPara(tClmNo);//ָ���������
		 	mySql21.addSubPara(tBatNo);
		 	strSQL = mySql21.getString();
			
  //alert(strSQL);
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
  	divUWHealth.style.display = 'none';
    return false;
 }
 
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  var m = turnPage.arrDataCacheSet.length;
  var tContent ="";
  var arrResult = "";
  var tSQL;
  //alert(m);
  //��ѯ�������
  for(var i=0; i<m; i++)
  {
//    tSQL = "select peitemname from lluwpenoticeitem where 1=1 "
//	       + " and clmno = '"+tClmNo+"' and batno='"+tBatNo+"' and prtseq = '"+turnPage.arrDataCacheSet[i][6]+"' order by peitemcode" ;
//    
    var sqlid22="LLSendAllNoticeSql22";
 	var mySql22=new SqlClass();
 	mySql22.setResourceName("uw.LLSendAllNoticeSql");
 	mySql22.setSqlId(sqlid22); //ָ��ʹ��SQL��id
 	mySql22.addSubPara(tClmNo);//ָ���������
 	mySql22.addSubPara(tBatNo);
 	mySql22.addSubPara(turnPage.arrDataCacheSet[i][6]);
 	tSQL = mySql22.getString();
    
	arrResult=easyExecSql(tSQL);
	for(var j=0; j<arrResult.length; j++)
	   tContent = tContent + arrResult[j][0]+" ;";
	turnPage.arrDataCacheSet[i][0] = tContent;
  }    
 
  //���ó�ʼ������MULTILINE����
  turnPage.pageDisplayGrid = UWHealthGrid;   
         
  //����SQL���
  turnPage.strQuerySql   = strSQL;
 
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex = 0; 
 
 //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex,MAXSCREENLINES);
 
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);	
//alert(695);
}

function checkHospitalGridValue()
{
    var rowNum = UWSpecGrid.mulLineCount;
    var selCount = 0;
    for (i = 0;i < rowNum; i++)
    {
        if (UWSpecGrid.getChkNo(i) == true)
        {
            selCount = selCount + 1;       
        }
    }
    if (selCount == 0)
    {
        alert("û��ѡ����Լ��Ϣ!");
        return false;
    }
    return true;
}

//tongmeng 2007-11-08 add
//��ʼ����ѯ������Ҫ�ظ��������
function queryAllNeedQuestion(tClmNo,tBatNo)
{	
	//initQuestGrid();
//	strSQL = " select proposalcontno,issuetype,issuecont,replyresult,(select username from lduser where usercode=a.operator),makedate,operatepos, "
//	       + " backobj,StandbyFlag2,serialno,(select codename from ldcode where codetype='backobj' and comcode=a.BackObjType and othersign=a.standbyflag2) "
//	       + " ,'','',needprint from llissuepol a where clmno='"+tClmNo+"' and batno='"+tBatNo+"' "
//	       //+ " and a.prtseq is null "
//	       + " and (state is null or state='') "
//	       + " and backobjtype in ('3','4') and needprint='Y' "
//	       + " union "
//	       + " select proposalcontno,issuetype,issuecont,replyresult,(select username from lduser where usercode=a.operator),makedate,operatepos, "
//	       + " backobj,StandbyFlag2,serialno,(select codename from ldcode where codetype='backobj' and comcode=a.BackObjType and othersign=a.standbyflag2) "
//	       + " ,(select codename from ldcode where codetype='question' and code=a.questionobjtype),questionobj,needprint from llissuepol a where clmno='"+tClmNo+"' and batno='"+tBatNo+"' "
//	       //+ " and a.prtseq is null "
//	       + " and (state is null or state='') "
//	       + " and backobjtype in ('1','2') and needprint='Y'  ";
//	
	var sqlid23="LLSendAllNoticeSql23";
 	var mySql23=new SqlClass();
 	mySql23.setResourceName("uw.LLSendAllNoticeSql");
 	mySql23.setSqlId(sqlid23); //ָ��ʹ��SQL��id
 	mySql23.addSubPara(tClmNo);//ָ���������
 	mySql23.addSubPara(tBatNo);
 	mySql23.addSubPara(tClmNo);
 	mySql23.addSubPara(tBatNo);
 	strSQL = mySql23.getString();
	

  //��ѯSQL�����ؽ���ַ���
  turnPageQuest.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPageQuest.strQueryResult) {
  	divQuest.style.display = 'none';
    return false;
 }
 
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPageQuest.arrDataCacheSet = decodeEasyQueryResult(turnPageQuest.strQueryResult);
 
  //���ó�ʼ������MULTILINE����
  turnPageQuest.pageDisplayGrid = QuestGrid;   
         
  //����SQL���
  turnPageQuest.strQuerySql   = strSQL;
 
  //���ò�ѯ��ʼλ��
  turnPageQuest.pageIndex = 0; 
 
 //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet = turnPageQuest.getData(turnPageQuest.arrDataCacheSet, turnPageQuest.pageIndex,MAXSCREENLINES);
 
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPageQuest.pageDisplayGrid);
}

//��ѯ�����ѷ��������
function queryQuestD(tClmNo,tBatNo)
{
	if(_DBT==_DBO){
//		strSQL = " select proposalcontno,issuetype,issuecont,(select username from lduser where usercode=a.operator),makedate, "
//		       + " (select codename from ldcode where codetype='backobj' and comcode=a.BackObjType and othersign=a.standbyflag2), "
//		       + " '', (select codename from ldcode where codetype='printstate' and code=state),"
//			   + " (case a.ReplyMan when null then 'δ�ظ�' else '�ѻظ�' end),needprint "
//		       + "  from llissuepol a where clmno='"+tClmNo+"' and batno='"+tBatNo+"' "
//		       + " and state is not null "
//		       + " and backobjtype in ('3','4') and needprint='Y' "
//		       + " union "
//		       + " select proposalcontno,issuetype,issuecont,(select username from lduser where usercode=a.operator),makedate, "
//		       + " (select codename from ldcode where codetype='backobj' and comcode=a.BackObjType and othersign=a.standbyflag2), "
//		       + " (select codename from ldcode where codetype='question' and code=questionobjtype), (select codename from ldcode where codetype='printstate' and code=state),"
//			   + " (case a.ReplyMan when null then 'δ�ظ�' else '�ѻظ�' end),needprint "
//		       + "  from llissuepol a where clmno='"+tClmNo+"' and batno='"+tBatNo+"' "
//		       + " and state is not null "
//		       + " and backobjtype in ('1','2') and needprint='Y'  "
//		       ;
		
		var sqlid24="LLSendAllNoticeSql24";
	 	var mySql24=new SqlClass();
	 	mySql24.setResourceName("uw.LLSendAllNoticeSql");
	 	mySql24.setSqlId(sqlid24); //ָ��ʹ��SQL��id
	 	mySql24.addSubPara(tClmNo);//ָ���������
	 	mySql24.addSubPara(tBatNo);
	 	mySql24.addSubPara(tClmNo);
	 	mySql24.addSubPara(tBatNo);
	 	strSQL = mySql24.getString();
		
	}else if(_DBT==_DBM){
		strSQL = " select proposalcontno,issuetype,issuecont,(select username from lduser where usercode=a.operator),makedate, "
		       + " (select codename from ldcode where codetype='backobj' and comcode=a.BackObjType and othersign=a.standbyflag2), "
		       + " '', (select codename from ldcode where codetype='printstate' and code=state),"
			   + " (case a.ReplyMan when null then 'δ�ظ�' else '�ѻظ�' end),needprint "
		       + "  from llissuepol a where clmno='"+tClmNo+"' and batno='"+tBatNo+"' "
		       + " and state is not null and state!='' "
		       + " and backobjtype in ('3','4') and needprint='Y' "
		       + " union "
		       + " select proposalcontno,issuetype,issuecont,(select username from lduser where usercode=a.operator),makedate, "
		       + " (select codename from ldcode where codetype='backobj' and comcode=a.BackObjType and othersign=a.standbyflag2), "
		       + " (select codename from ldcode where codetype='question' and code=questionobjtype), (select codename from ldcode where codetype='printstate' and code=state),"
			   + " (case a.ReplyMan when null then 'δ�ظ�' else '�ѻظ�' end),needprint "
		       + "  from llissuepol a where clmno='"+tClmNo+"' and batno='"+tBatNo+"' "
		       + " and state is not null and state!='' "
		       + " and backobjtype in ('1','2') and needprint='Y'  "
		       ;
		
		var sqlid25="LLSendAllNoticeSql25";
	 	var mySql25=new SqlClass();
	 	mySql25.setResourceName("uw.LLSendAllNoticeSql");
	 	mySql25.setSqlId(sqlid25); //ָ��ʹ��SQL��id
	 	mySql25.addSubPara(tClmNo);//ָ���������
	 	mySql25.addSubPara(tBatNo);
	 	mySql25.addSubPara(tClmNo);
	 	mySql25.addSubPara(tBatNo);
	 	strSQL = mySql25.getString();
		
	}
	
	       //prompt("",strSQL);
  //��ѯSQL�����ؽ���ַ���
  turnPageQuestD.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPageQuestD.strQueryResult) {
  	divQuestD.style.display = 'none';
    return false;
 }
 
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPageQuestD.arrDataCacheSet = decodeEasyQueryResult(turnPageQuestD.strQueryResult);
 
  //���ó�ʼ������MULTILINE����
  turnPageQuestD.pageDisplayGrid = QuestDGrid;   
         
  //����SQL���
  turnPageQuestD.strQuerySql   = strSQL;
 
  //���ò�ѯ��ʼλ��
  turnPageQuestD.pageIndex = 0; 
 
 //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet = turnPageQuestD.getData(turnPageQuestD.arrDataCacheSet, turnPageQuestD.pageIndex,MAXSCREENLINES);
 
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPageQuestD.pageDisplayGrid);	
}
