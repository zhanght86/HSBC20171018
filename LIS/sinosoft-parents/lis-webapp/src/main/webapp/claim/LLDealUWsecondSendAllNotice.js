//�������ƣ�LLDealUWsecondSendAllNotice.js
//�����ܣ������¼��
//�������ڣ�2009-01-15 11:10:36
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
var str_sql = "",sql_id = "", my_sql = "";   //�󶨱����������ݿ�
//�ύ�����水ť��Ӧ����
function submitForm()
{
	
/*	 if(document.all('NoticeType').value=="82")
		{
			if (checkHospitalGridValue() == false)
   		{
    		  return;
   		}
  	}*/
	
  //tongmeng 2007-11-26 add
	
  //���ӶԹ���������У��,�Լ�ȡ�����Ļ���Ϊ�˱�֪ͨ�鷢�ŵ�subMissionId
//  var tSQL_SubMissionId = "select (case when max(submissionid) is not null then max(submissionid) else 'x' end) from lwmission where missionid='"+tMissionID+"' "
//                           + " and activityid='0000000100' ";
  var tempSubMissionID = "";
  sql_id = "LLDealUWsecondSendAllNoticeSql0";
 	my_sql = new SqlClass();
 	my_sql.setResourceName("claim.LLDealUWsecondSendAllNoticeSql"); //ָ��ʹ�õ�properties�ļ���
 	my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
 	my_sql.addSubPara(tMissionID);//ָ������Ĳ���
 	str_sql = my_sql.getString();
  var brr = easyExecSql(str_sql);
  //prompt("",tSQL_SubMissionId);return false;
  
  tempSubMissionID = brr[0][0];
  if(tempSubMissionID == 'x' )
  {
  	alert(" �����ѷ���δ���յĺ˱�֪ͨ��,�������ٷ��ͺ˱�֪ͨ��! ");
  	return false;
  }

  var i = 0;
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
	var showInfo1 = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

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

	sql_id = "LLDealUWsecondSendAllNoticeSql1";
	my_sql = new SqlClass();
	my_sql.setResourceName("claim.LLDealUWsecondSendAllNoticeSql"); //ָ��ʹ�õ�properties�ļ���
	my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
	my_sql.addSubPara(tCode);//ָ������Ĳ���
	str_sql = my_sql.getString();

	
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(str_sql, 1, 0, 1);  
  
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
		sql_id = "LLDealUWsecondSendAllNoticeSql2";
	 	my_sql = new SqlClass();
	 	my_sql.setResourceName("claim.LLDealUWsecondSendAllNoticeSql"); //ָ��ʹ�õ�properties�ļ���
	 	my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
	 	my_sql.addSubPara(document.all('ContNo').value);//ָ������Ĳ���
	 	str_sql = my_sql.getString();
		if(tCode=="BQ87")
		{
//		    tsql = "select riskcode,uwflag from lppol where contno = '"+document.all('ContNo').value+"' and mainpolno!=polno and uwflag in ('1','2') and edorno = '" + fm.EdorNo.value + "' and edortype = '" + fm.EdorType.value + "'";
		    sql_id = "LLDealUWsecondSendAllNoticeSql3";
		 	my_sql = new SqlClass();
		 	my_sql.setResourceName("claim.LLDealUWsecondSendAllNoticeSql"); //ָ��ʹ�õ�properties�ļ���
		 	my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
		 	my_sql.addSubPara(document.all('ContNo').value);//ָ������Ĳ���
		 	my_sql.addSubPara(fm.EdorNo.value);//ָ������Ĳ���
		 	my_sql.addSubPara(fm.EdorType.value);//ָ������Ĳ���
		 	str_sql = my_sql.getString();
		}
		brrResult=easyExecSql(str_sql);
		if(brrResult==null)
		{
			QuestQuery(tCode);
		}
	else{
		var str = "���˱���";
		for (var p = 0;p<brrResult.length;p++)
		  {
//		  	tsql = "select riskstatname from lmrisk where riskcode = '"+brrResult[p][0]+"'";
		  	sql_id = "LLDealUWsecondSendAllNoticeSql4";
		 	my_sql = new SqlClass();
		 	my_sql.setResourceName("claim.LLDealUWsecondSendAllNoticeSql"); //ָ��ʹ�õ�properties�ļ���
		 	my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
		 	my_sql.addSubPara(brrResult[p][0]);//ָ������Ĳ���
		 	str_sql = my_sql.getString();
			  var crrResult = easyExecSql(str_sql);
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
		sql_id = "LLDealUWsecondSendAllNoticeSql5";
	 	my_sql = new SqlClass();
	 	my_sql.setResourceName("claim.LLDealUWsecondSendAllNoticeSql"); //ָ��ʹ�õ�properties�ļ���
	 	my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
	 	my_sql.addSubPara(MissionID);//ָ������Ĳ���
	 	str_sql = my_sql.getString();						
 var brr = easyExecSql(str_sql);//prompt("",strSQL);
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
	sql_id = "LLDealUWsecondSendAllNoticeSql6";
 	my_sql = new SqlClass();
 	my_sql.setResourceName("claim.LLDealUWsecondSendAllNoticeSql"); //ָ��ʹ�õ�properties�ļ���
 	my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
 	my_sql.addSubPara(tContNo);//ָ������Ĳ���
 	str_sql = my_sql.getString();		
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
 turnPage2.queryModal(str_sql, PolGrid); 
	        
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
      tSQL = "select remark from loprtmanager where prtseq = '"+tPrtseq+"'";
      arrResult=easyExecSql(tSQL);
      document.all('result').value = arrResult[0][0];
    	return;
    	} 
    if(tCode == "89")
    {
    	//alert(1);
      divresult.style.display = '';
//      tSQL = "select askcode,(select CodeName from LDcode where LDcode.Code = LCRReportPrt.askcode and CodeType = 'rreporttype' ) from LCRReportPrt where prtseq = '"+tPrtseq+"'";
      sql_id = "LLDealUWsecondSendAllNoticeSql7";
	 my_sql = new SqlClass();
   	 my_sql.setResourceName("claim.LLDealUWsecondSendAllNoticeSql"); //ָ��ʹ�õ�properties�ļ���
   	 my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
   	 my_sql.addSubPara(tPrtseq);//ָ������Ĳ���
   	 str_sql = my_sql.getString();		
      arrResult=easyExecSql(str_sql);
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
function queryUWSpecD(tContNo)
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
//   + "   from lccspec s "
//   + "   where contno = '"+tContNo+"' and prtflag is not null and needprint='Y') ";

	    sql_id = "LLDealUWsecondSendAllNoticeSql8";
		 my_sql = new SqlClass();
	   	 my_sql.setResourceName("claim.LLDealUWsecondSendAllNoticeSql"); //ָ��ʹ�õ�properties�ļ���
	   	 my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
	   	 my_sql.addSubPara(tContNo);//ָ������Ĳ���
	   	 str_sql = my_sql.getString();	
	}else if(_DBT==DBM){
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
//   + "   from lccspec s "
//   + "   where contno = '"+tContNo+"' and prtflag is not null and prtflag!='' and needprint='Y') ";
		 sql_id = "LLDealUWsecondSendAllNoticeSql9";
		 my_sql = new SqlClass();
	   	 my_sql.setResourceName("claim.LLDealUWsecondSendAllNoticeSql"); //ָ��ʹ�õ�properties�ļ���
	   	 my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
	   	 my_sql.addSubPara(tContNo);//ָ������Ĳ���
	   	 str_sql = my_sql.getString();	
	}
			
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(str_sql, 1, 0, 1); 

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
function querySpec(tContNo)
{	
//			strSQL = "select speccontent"
//                         + " ,operator ,makedate,'' "
//                         + " ,(select codename from ldcode where codetype='question' and code=(select sequenceno from lcinsured where contno=cNo and insuredno=iNo))"
//                         + " ,'Y',iNo"             
//                + "   from"
//				+ "(select speccontent"
//                         + " ,operator ,makedate,contno cNo "                         
//                         + " ,s.customerno iNo"             
//                + "   from lccspec s "
//                + "   where contno = '"+tContNo+"' and prtflag is null and needprint='Y') ";
	 sql_id = "LLDealUWsecondSendAllNoticeSql10";
	 my_sql = new SqlClass();
   	 my_sql.setResourceName("claim.LLDealUWsecondSendAllNoticeSql"); //ָ��ʹ�õ�properties�ļ���
   	 my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
   	 my_sql.addSubPara(tContNo);//ָ������Ĳ���
   	 str_sql = my_sql.getString();	
			
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(str_sql, 1, 0, 1); 

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
function queryAddFee(tContNo)
{     
//			strSQL = "select rc ,(select riskname from lmriskapp where riskcode=rc) riskname"
//			       + " ,a,payplancode,prem,polno,SuppRiskScore,operator,makedate,'' "
//			       + " ,(select codename from ldcode where codetype='question' and code=(select sequenceno from lcinsured where contno=cNo and insuredno=iNo))"
//			       + " ,'Y',iNo"
//			       + " from "
//			       + " (select (select riskcode from lcpol where polno=p.polno) rc,payplantype,contno cNo"
//			       + " ,(case trim(payplantype) when '01' then '�����ӷ�' when '02' then 'ְҵ�ӷ�' when '03' then '��ס�ؼӷ�' when '04' then '���üӷ�' end) a,payplancode,prem,polno,operator,makedate"
//			       + " ,(select insuredno from lcpol where polno=p.polno) iNo,SuppRiskScore"
//			       + " from lcprem p where  payplancode like '000000%%' "
//                + " and contno = '"+tContNo+"') ";
                
  sql_id = "LLDealUWsecondSendAllNoticeSql11";
  my_sql = new SqlClass();
  my_sql.setResourceName("claim.LLDealUWsecondSendAllNoticeSql"); //ָ��ʹ�õ�properties�ļ���
  my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
  my_sql.addSubPara(tContNo);//ָ������Ĳ���
  str_sql = my_sql.getString();
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(str_sql, 1, 0, 1); 

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
//			strSQL = "select riskcode,(select riskname from lmriskapp where riskcode=a.riskcode),amnt, mult,prem, concat(payendyear,payendyearflag),concat(insuyear,insuyearflag),payintv,"
//			    + " b.operator,b.modifydate,'','Ͷ����','Y'  "
//			    + " from lcpol a,lcuwmaster b  "
//			    + " where a.contno = '"+tContNo+"' and a.proposalno=b.proposalno and b.changepolflag is not null and b.changepolflag='1' "
//			    + " order by a.mainpolno,a.polno  ";
			
			  sql_id = "LLDealUWsecondSendAllNoticeSql12";
			  my_sql = new SqlClass();
			  my_sql.setResourceName("claim.LLDealUWsecondSendAllNoticeSql"); //ָ��ʹ�õ�properties�ļ���
			  my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
			  my_sql.addSubPara(tContNo);//ָ������Ĳ���
			  str_sql = my_sql.getString();
		}else if(_DBT==_DBM){
//			strSQL = "select riskcode,(select riskname from lmriskapp where riskcode=a.riskcode),amnt, mult,prem, concat(payendyear,payendyearflag),concat(insuyear,insuyearflag),payintv,"
//			    + " b.operator,b.modifydate,'','Ͷ����','Y'  "
//			    + " from lcpol a,lcuwmaster b  "
//			    + " where a.contno = '"+tContNo+"' and a.proposalno=b.proposalno and b.changepolflag is not null and b.changepolflag!='' and b.changepolflag='1' "
//			    + " order by a.mainpolno,a.polno  ";
			
			  sql_id = "LLDealUWsecondSendAllNoticeSql13";
			  my_sql = new SqlClass();
			  my_sql.setResourceName("claim.LLDealUWsecondSendAllNoticeSql"); //ָ��ʹ�õ�properties�ļ���
			  my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
			  my_sql.addSubPara(tContNo);//ָ������Ĳ���
			  str_sql = my_sql.getString();
		}
			
                
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(str_sql, 1, 0, 1); 

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
function queryUWExistD(tContNo)
{			
	if(_DBT==_DBO){
//		strSQL = "select Contente,(select username from lduser where usercode=l.operator),makedate"
//	        + " ,'Ͷ����'"
//	        + " ,(select codename from ldcode where codetype='printstate' and code=l.replyflag)"
//	       + " ,(case l.ReplyOperator when null then 'δ�ظ�' else '�ѻظ�' end)"			      
//	       + " from LPRReport l where 1=1 "
//        + " and contno = '"+tContNo+"' and replyflag is not null";
		
		  sql_id = "LLDealUWsecondSendAllNoticeSql14";
		  my_sql = new SqlClass();
		  my_sql.setResourceName("claim.LLDealUWsecondSendAllNoticeSql"); //ָ��ʹ�õ�properties�ļ���
		  my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
		  my_sql.addSubPara(tContNo);//ָ������Ĳ���
		  str_sql = my_sql.getString();
	}else if(_DBT==_DBM){
//		strSQL = "select Contente,(select username from lduser where usercode=l.operator),makedate"
//	        + " ,'Ͷ����'"
//	        + " ,(select codename from ldcode where codetype='printstate' and code=l.replyflag)"
//	       + " ,(case l.ReplyOperator when null then 'δ�ظ�' else '�ѻظ�' end)"			      
//	       + " from LPRReport l where 1=1 "
//        + " and contno = '"+tContNo+"' and replyflag is not null and replyflag!=''";
		
		  sql_id = "LLDealUWsecondSendAllNoticeSql15";
		  my_sql = new SqlClass();
		  my_sql.setResourceName("claim.LLDealUWsecondSendAllNoticeSql"); //ָ��ʹ�õ�properties�ļ���
		  my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
		  my_sql.addSubPara(tContNo);//ָ������Ĳ���
		  str_sql = my_sql.getString();
	}
			
                
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(str_sql, 1, 0, 1); 

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

  sql_id = "LLDealUWsecondSendAllNoticeSql16";
  my_sql = new SqlClass();
  my_sql.setResourceName("claim.LLDealUWsecondSendAllNoticeSql"); //ָ��ʹ�õ�properties�ļ���
  my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
  my_sql.addSubPara(tContNo);//ָ������Ĳ���
  str_sql = my_sql.getString();
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(str_sql, 1, 0, 1); 

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
function queryUWHealthD()
{
	if(_DBT==_DBO){
//		strSQL = "select '', operator, makedate, '', (select codename from ldcode where codetype='question' and code=customercode), printstate, replystate, prtseq"
//            +" from (select operator, makedate, "
//            +" (case CustomerType when 'A' then '0' else (select sequenceno from lcinsured where  insuredno=customerno) end) customercode,"
//            +" (select codename from ldcode where codetype='printstate' and code=printflag) printstate,"
//	        + " (case printflag when '1' then 'δ�ظ�' else '�ѻظ�' end) replystate, "
//            +" prtseq "
//		    + " from LLUWPENotice where 1=1 "
//            + " and ClmNo = '"+document.all('CaseNo').value+"' and printflag is not null)";
		
		sql_id = "LLDealUWsecondSendAllNoticeSql17";
		  my_sql = new SqlClass();
		  my_sql.setResourceName("claim.LLDealUWsecondSendAllNoticeSql"); //ָ��ʹ�õ�properties�ļ���
		  my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
		  my_sql.addSubPara(document.all('CaseNo').value);//ָ������Ĳ���
		  str_sql = my_sql.getString();
	}else if(_DBT==_DBM){
//		strSQL = "select '', operator, makedate, '', (select codename from ldcode where codetype='question' and code=customercode), printstate, replystate, prtseq"
//            +" from (select operator, makedate, "
//            +" (case CustomerType when 'A' then '0' else (select sequenceno from lcinsured where  insuredno=customerno) end) customercode,"
//            +" (select codename from ldcode where codetype='printstate' and code=printflag) printstate,"
//	        + " (case printflag when '1' then 'δ�ظ�' else '�ѻظ�' end) replystate, "
//            +" prtseq "
//		    + " from LLUWPENotice where 1=1 "
//            + " and ClmNo = '"+document.all('CaseNo').value+"' and printflag is not null and printflag!='')";
		
		sql_id = "LLDealUWsecondSendAllNoticeSql18";
		  my_sql = new SqlClass();
		  my_sql.setResourceName("claim.LLDealUWsecondSendAllNoticeSql"); //ָ��ʹ�õ�properties�ļ���
		  my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
		  my_sql.addSubPara(document.all('CaseNo').value);//ָ������Ĳ���
		  str_sql = my_sql.getString();
	}
  prompt("��ѯ�ѷ����",str_sql);
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(str_sql, 1, 0, 1); 

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
//    tSQL = "select peitemname from LLUWPENoticeItem where 1=1 "
//	       + " and clmno = '"+ document.all('CaseNo').value +"' and prtseq = '"+turnPage.arrDataCacheSet[i][7]+"' order by peitemcode" ;
    
    sql_id = "LLDealUWsecondSendAllNoticeSql19";
	  my_sql = new SqlClass();
	  my_sql.setResourceName("claim.LLDealUWsecondSendAllNoticeSql"); //ָ��ʹ�õ�properties�ļ���
	  my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
	  my_sql.addSubPara(document.all('CaseNo').value);//ָ������Ĳ���
	  my_sql.addSubPara(turnPage.arrDataCacheSet[i][7]);
	  str_sql = my_sql.getString();
	arrResult=easyExecSql(str_sql);//prompt("",tSQL);
	for(var j=0; j<arrResult.length; j++)
	   tContent = tContent + arrResult[j][0]+" ;";
	turnPage.arrDataCacheSet[i][0] = tContent;
  }    

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
function queryUWHealth()
{
//			strSQL = "select '', operator, makedate, '', (select codename from ldcode where codetype='question' and code=customercode), '', prtseq"
//                +" from (select operator, makedate, (case CustomerType when 'A' then '0' else (select sequenceno from lcinsured where  insuredno=customerno) end) customercode, prtseq"
//			                   + " from LLUWPENotice where 1=1 "
//                               + " and clmno = '"+document.all('CaseNo').value+"' and (printflag is null or printflag=''))";
	  sql_id = "LLDealUWsecondSendAllNoticeSql20";
	  my_sql = new SqlClass();
	  my_sql.setResourceName("claim.LLDealUWsecondSendAllNoticeSql"); //ָ��ʹ�õ�properties�ļ���
	  my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
	  my_sql.addSubPara(document.all('CaseNo').value);
	  str_sql = my_sql.getString();
	prompt("��ѯ�������",str_sql);
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(str_sql, 1, 0, 1); 

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
//    tSQL = "select peitemname from LLUWPENoticeItem where 1=1 "
//	       + " and clmno = '"+ document.all('CaseNo').value +"' and prtseq = '"+turnPage.arrDataCacheSet[i][6]+"' order by peitemcode" ;
    
    sql_id = "LLDealUWsecondSendAllNoticeSql21";
	my_sql = new SqlClass();
	my_sql.setResourceName("claim.LLDealUWsecondSendAllNoticeSql"); //ָ��ʹ�õ�properties�ļ���
	my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
    my_sql.addSubPara(document.all('CaseNo').value);
    my_sql.addSubPara(turnPage.arrDataCacheSet[i][6]);
	str_sql = my_sql.getString();
	arrResult=easyExecSql(str_sql);
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
function queryAllNeedQuestion(tContNo)
{	
	//initQuestGrid();
//	strSQL = " select proposalcontno,issuetype,issuecont,replyresult,(select username from lduser where usercode=a.operator),makedate,operatepos, "
//	       + " backobj,StandbyFlag2,serialno,(select codename from ldcode where codetype='backobj' and comcode=a.BackObjType and othersign=a.standbyflag2) "
//	       + " ,'','',needprint from lcissuepol a where contno='"+tContNo+"' "
//	       //+ " and a.prtseq is null "
//	       + " and (state is null or state='') "
//	       + " and backobjtype in ('3','4') and needprint='Y' "
//	       + " union "
//	       + " select proposalcontno,issuetype,issuecont,replyresult,(select username from lduser where usercode=a.operator),makedate,operatepos, "
//	       + " backobj,StandbyFlag2,serialno,(select codename from ldcode where codetype='backobj' and comcode=a.BackObjType and othersign=a.standbyflag2) "
//	       + " ,(select codename from ldcode where codetype='question' and code=a.questionobjtype),questionobj,needprint from lcissuepol a where contno='"+tContNo+"' "
//	       //+ " and a.prtseq is null "
//	       + " and (state is null or state='') "
//	       + " and backobjtype in ('1','2') and needprint='Y'  ";

	
	 sql_id = "LLDealUWsecondSendAllNoticeSql22";
		my_sql = new SqlClass();
		my_sql.setResourceName("claim.LLDealUWsecondSendAllNoticeSql"); //ָ��ʹ�õ�properties�ļ���
		my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
	    my_sql.addSubPara(tContNo);
	    my_sql.addSubPara(tContNo);
		str_sql = my_sql.getString();
  //��ѯSQL�����ؽ���ַ���
  turnPageQuest.strQueryResult  = easyQueryVer3(str_sql, 1, 0, 1); 

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
function queryQuestD(tContNo)
{
	
	if(_DBT==_DBO){
//		strSQL = " select proposalcontno,issuetype,issuecont,(select username from lduser where usercode=a.operator),makedate, "
//		       + " (select codename from ldcode where codetype='backobj' and comcode=a.BackObjType and othersign=a.standbyflag2), "
//		       + " '', (select codename from ldcode where codetype='printstate' and code=state),"
//			   + " (case a.ReplyMan when null then 'δ�ظ�' else '�ѻظ�' end),needprint "
//		       + "  from lcissuepol a where contno='"+tContNo+"' "
//		       + " and state is not null "
//		       + " and backobjtype in ('3','4') and needprint='Y' "
//		       + " union "
//		       + " select proposalcontno,issuetype,issuecont,(select username from lduser where usercode=a.operator),makedate, "
//		       + " (select codename from ldcode where codetype='backobj' and comcode=a.BackObjType and othersign=a.standbyflag2), "
//		       + " (select codename from ldcode where codetype='question' and code=questionobjtype), (select codename from ldcode where codetype='printstate' and code=state),"
//			   + " (case a.ReplyMan when null then 'δ�ظ�' else '�ѻظ�' end),needprint "
//		       + "  from lcissuepol a where contno='"+tContNo+"' "
//		       + " and state is not null "
//		       + " and backobjtype in ('1','2') and needprint='Y'  " ;
		
		 sql_id = "LLDealUWsecondSendAllNoticeSql23";
			my_sql = new SqlClass();
			my_sql.setResourceName("claim.LLDealUWsecondSendAllNoticeSql"); //ָ��ʹ�õ�properties�ļ���
			my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
		    my_sql.addSubPara(tContNo);
		    my_sql.addSubPara(tContNo);
			str_sql = my_sql.getString();
	}else if(_DBT==_DBM){
//		strSQL = " select proposalcontno,issuetype,issuecont,(select username from lduser where usercode=a.operator),makedate, "
//		       + " (select codename from ldcode where codetype='backobj' and comcode=a.BackObjType and othersign=a.standbyflag2), "
//		       + " '', (select codename from ldcode where codetype='printstate' and code=state),"
//			   + " (case a.ReplyMan when null then 'δ�ظ�' else '�ѻظ�' end),needprint "
//		       + "  from lcissuepol a where contno='"+tContNo+"' "
//		       + " and state is not null and state!='' "
//		       + " and backobjtype in ('3','4') and needprint='Y' "
//		       + " union "
//		       + " select proposalcontno,issuetype,issuecont,(select username from lduser where usercode=a.operator),makedate, "
//		       + " (select codename from ldcode where codetype='backobj' and comcode=a.BackObjType and othersign=a.standbyflag2), "
//		       + " (select codename from ldcode where codetype='question' and code=questionobjtype), (select codename from ldcode where codetype='printstate' and code=state),"
//			   + " (case a.ReplyMan when null then 'δ�ظ�' else '�ѻظ�' end),needprint "
//		       + "  from lcissuepol a where contno='"+tContNo+"' "
//		       + " and state is not null and state!='' "
//		       + " and backobjtype in ('1','2') and needprint='Y'  " ;
		
		 sql_id = "LLDealUWsecondSendAllNoticeSql24";
			my_sql = new SqlClass();
			my_sql.setResourceName("claim.LLDealUWsecondSendAllNoticeSql"); //ָ��ʹ�õ�properties�ļ���
			my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
		    my_sql.addSubPara(tContNo);
		    my_sql.addSubPara(tContNo);
			str_sql = my_sql.getString();
	}
	      
  //��ѯSQL�����ؽ���ַ���
  turnPageQuestD.strQueryResult  = easyQueryVer3(str_sql, 1, 0, 1); 

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


