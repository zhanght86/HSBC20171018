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
	
  tSQL_SubMissionId="select (case when uwstate is not null then uwstate else '1' end) from lccuwmaster where contno='"+fm.ContNo.value+"'";
  var tempSubMissionID = "";
  var brr = easyExecSql(tSQL_SubMissionId);
  tempSubMissionID = brr[0][0];
  if(tempSubMissionID == '4' )
  {
  	alert(" �����ѷ���δ���յĺ˱�֪ͨ��,�������ٷ��ͺ˱�֪ͨ��! ");
  	return false;
  }
  //tongmeng 2009-05-11 add
  //�Ի����յ�У��
  
  
  
  var ContNo = document.all('ContNo').value;
  //alert(ContNo);
  //��ѯ��ͬ���Ƿ��л�����.
//  	var tSQL_HM = "select '1' from lcpol where riskcode='121301' and contno='"+ContNo+"' "
//  	            + " and uwflag not in ('1','2','a') "
//  	            + " union "
//  	            + " select '2' from lcpol where riskcode='121506' and contno='"+ContNo+"' "
//  	            + " and uwflag not in ('1','2','a') ";
  	var  sqlid1="MSSendAllNoticeSql0";
 	var  mySql1=new SqlClass();
 	mySql1.setResourceName("uw.MSSendAllNoticeSql"); //ָ��ʹ�õ�properties�ļ���
 	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
 	mySql1.addSubPara(ContNo);//ָ������Ĳ���
 	mySql1.addSubPara(ContNo);//ָ������Ĳ���
 	var tSQL_HM=mySql1.getString();
  	var tFlag_HM = easyExecSql(tSQL_HM);
  	if(tFlag_HM!=null)
  	{
  		 var tSQL_CheckHM = "";
  		 var tFlag_CheckHM = "";
  		 if(tFlag_HM[0][0]=='1')
  		 {
  		 	 //У��Ͷ���˻�����
//  		 	 tSQL_CheckHM = "select decode(count(*),0,0,1) from ( "
//	                    + " select round(nvl(sum(a.prem),0),2) x,round(nvl(sum(b.amnt),0),2) y "
//	                    + " from lcpol a,lcpol b "
//	                    + " where a.contno=b.contno and a.contno='"+ContNo+"' "
//	                    + " and a.riskcode<>'121301' and b.riskcode='121301' "
//	                    + " and a.riskcode in (select code1 from ldcode1 where codetype='freerisk' "
////	                    + " and code='121301') ) A where A.x<>A.y ";
//  			tSQL_CheckHM = " select 1 from dual where ((select sum(a.prem)"
//				+ " from lcpol a"
//				+ " where a.riskcode <> '121301' and a.contno = '"+ContNo+"'"
//				+ " and a.uwflag not in ('1','2','a') "
//				+ " and a.riskcode in (select code1"
//				+ " from ldcode1 where codetype = 'freerisk'"
//				+ " and code = '121301')) <> (select sum(b.amnt)"
//				+ " from lcpol b where b.riskcode = '121301'"
//				+ " and b.contno = '"+ContNo+"' and uwflag not in ('1','2','a') )) ";
//  		 	prompt('tSQL_CheckHM',tSQL_CheckHM);
  			
  			var  sqlid2="MSSendAllNoticeSql1";
  		 	var  mySql2=new SqlClass();
  		 	mySql2.setResourceName("uw.MSSendAllNoticeSql"); //ָ��ʹ�õ�properties�ļ���
  		 	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
  		 	mySql2.addSubPara(ContNo);//ָ������Ĳ���
  		 	mySql2.addSubPara(ContNo);//ָ������Ĳ���
  		 	tSQL_CheckHM=mySql2.getString();
  		 	 tFlag_CheckHM = easyExecSql(tSQL_CheckHM);
  			 if(tFlag_CheckHM!=null)
  		 		{
						if(tFlag_CheckHM[0][0]=='1')
						{
						 	alert('��ͬ�»������ֵı��������Ӧ���ֵ��ۼƱ���,���޸Ļ����ձ�������º˱�����!');
						 	return false;
						}
  		 		}
  			//05-13 ����У�����ս����ڼ�Ϊ���������ܴ��и��ӻ�����
//  			 var AMainPolPaySql = "select lcpol.payintv from lcpol where contno = '"+ContNo+"'"
//  				 		+ " and riskcode in (select code1 from ldcode1 where codetype = 'freerisk'"
//						+ " and code = '121301') and uwflag not in ('1','2','a') ";
  			var  sqlid3="MSSendAllNoticeSql2";
  		 	var  mySql3=new SqlClass();
  		 	mySql3.setResourceName("uw.MSSendAllNoticeSql"); //ָ��ʹ�õ�properties�ļ���
  		 	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
  		 	mySql3.addSubPara(ContNo);//ָ������Ĳ���
  		 	var AMainPolPaySql=mySql3.getString();
  			 var AMainPolPay = easyExecSql(AMainPolPaySql);
  			 if(AMainPolPay!=null) {
  				 for(i=0;i<AMainPolPay.length;i++) {
  					 if(AMainPolPay[i][0]=='0') {
  						 alert("���յĽ����ڼ�Ϊ���������ܴ��и��ӻ����գ�");
  						 return false;
  					 }
  				 }
  			 }
  			 
  			 //05-13  ����У�����ս����ڼ��븽�ӻ����ս����ڼ��Ƿ�һ��
//  			 var ACheckPayIntvSql = "select (case count(*) when 0 then 0 else 1 end) from ( "
//		                + " select a.payyears x,b.payyears y "
//		                + " from lcpol a,lcpol b "
//		                + " where a.contno=b.contno and a.contno='"+ContNo+"' "
//		                + " and a.riskcode<>'121301' and b.riskcode='121301' "
//		                + " and a.uwflag not in ('1','2','a') "
//		                + " and b.uwflag not in ('1','2','a') "
//		                + " and a.riskcode in (select code1 from ldcode1 where codetype='freerisk' "
//		                + " and code='121301') ) A where A.x<>A.y ";
  			var  sqlid4="MSSendAllNoticeSql3";
  		 	var  mySql4=new SqlClass();
  		 	mySql4.setResourceName("uw.MSSendAllNoticeSql"); //ָ��ʹ�õ�properties�ļ���
  		 	mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
  		 	mySql4.addSubPara(ContNo);//ָ������Ĳ���
  		    var ACheckPayIntvSql=mySql4.getString();
  			 var ACheckPayIntv = easyExecSql(ACheckPayIntvSql);
  			 if(ACheckPayIntv!=null) {
				if(ACheckPayIntv[0][0]=='1') {
					 alert('���յĽ����ڼ��븽�ӻ����յĽ����ڼ䲻һ�£�');
					 return false;
				}
  			 }
  		 }
  		 else  if(tFlag_HM[0][0]=='2')
  		 {
  		 	 //У�鱻���˻�����
//  		 	 tSQL_CheckHM = " select 1 from dual where ((select sum(a.prem)"
//							+ " from lcpol a"
//							+ " where a.riskcode <> '121506' and a.contno = '"+ContNo+"'"
//							+ " and uwflag not in ('1','2','a') "
//							+ " and a.riskcode in (select code1"
//							+ " from ldcode1 where codetype = 'freerisk'"
//							+ " and code = '121506')) <> (select sum(b.amnt)"
//							+ " from lcpol b where b.riskcode = '121506'"
//							+ " and b.contno = '"+ContNo+"' and uwflag not in ('1','2','a')))";
//  		 	 	prompt('tSQL_CheckHM',tSQL_CheckHM);
  		 	var  sqlid5="MSSendAllNoticeSql4";
  		 	var  mySql5=new SqlClass();
  		 	mySql5.setResourceName("uw.MSSendAllNoticeSql"); //ָ��ʹ�õ�properties�ļ���
  		 	mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
  		 	mySql5.addSubPara(ContNo);//ָ������Ĳ���
  		 	mySql5.addSubPara(ContNo);//ָ������Ĳ���
  		    tSQL_CheckHM=mySql5.getString();
  		 	 tFlag_CheckHM = easyExecSql(tSQL_CheckHM);
  			 if(tFlag_CheckHM!=null)
  		 	 {
  		 			for(i=0;i<tFlag_CheckHM.length;i++)
  		 			{
							if(tFlag_CheckHM[i][0]=='1')
							{
						 		alert('�����˻������ֵı��������Ӧ���ֵ��ۼƱ���,���޸Ļ����ձ�������º˱�����!');
						 		return false;
							}
						}
  		 	 }
  			//05-13 ����У�����ս����ڼ�Ϊ���������ܴ��и��ӻ�����
//  			 var IMainPolPaySql = "select lcpol.insuredno,lcpol.payintv from lcpol where contno = '"+ContNo+"'"
//  				 		+ " and riskcode in (select code1 from ldcode1 where codetype = 'freerisk'"
//						+ " and code = '121506') and uwflag not in ('1','2','a') ";
  			var  sqlid6="MSSendAllNoticeSql5";
  		 	var  mySql6=new SqlClass();
  		 	mySql6.setResourceName("uw.MSSendAllNoticeSql"); //ָ��ʹ�õ�properties�ļ���
  		 	mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
  		 	mySql6.addSubPara(ContNo);//ָ������Ĳ���
  		 	var IMainPolPaySql=mySql6.getString();
  			 var IMainPolPay = easyExecSql(IMainPolPaySql);
  			 if(IMainPolPay!=null) {
  				 for(i=0;i<IMainPolPay.length;i++) {
  					 if(IMainPolPay[i][1]=='0') {
  						 alert("�����ˣ�"+IMainPolPay[i][1]+" �µ����յĽ����ڼ�Ϊ���������ܴ��и��ӻ����գ�");
  						 return false;
  					 }
  				 }
  			 }
  			 
  			 //05-13 ��������У�����ս����ڼ��븽�ӻ����ս����ڼ��Ƿ�һ��
//  			var ICheckPayIntvSql = "select a.insuredno from lcpol a, lcpol b where a.contno = '"+ContNo+"'"
//  						+ " and a.contno = b.contno and a.payyears != b.payyears"
//  						+ " and a.riskcode = '121506' and b.riskcode in (select code1"
//  						+ " from ldcode1 where codetype = 'freerisk'"
//  						+ " and code = '121506') and b.riskcode <> '121506'"
//  						+ " and a.uwflag not in ('1','2','a') "
//  						+ " and b.uwflag not in ('1','2','a') ";
  			var  sqlid7="MSSendAllNoticeSql6";
  		 	var  mySql7=new SqlClass();
  		 	mySql7.setResourceName("uw.MSSendAllNoticeSql"); //ָ��ʹ�õ�properties�ļ���
  		 	mySql7.setSqlId(sqlid7);//ָ��ʹ�õ�Sql��id
  		 	mySql7.addSubPara(ContNo);//ָ������Ĳ���
  		 	var ICheckPayIntvSql=mySql7.getString();
  			var ICheckPayIntv = easyExecSql(ICheckPayIntvSql);
 			 if(ICheckPayIntv!=null) {
				for(i=0;i<ICheckPayIntv.length;i++) {
					alert("�����ˣ�"+ICheckPayIntv[i][0]+" �µ����յĽ����ڼ��븽�ӻ����յĽ����ڼ䲻һ�£�");
					return false;
				}
 			 }
  		 }
  		}
//  	alert("У��ʧ�ܣ�");
//  	return false;
//  	return;	 
//  alert(tempSubMissionID);
  document.all('SubMissionID').value = tempSubMissionID;
  document.all("ActivityID").value = tActivityID;//add by lzf 2013--3-11
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
lockScreen('lkscreen');  
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
	
		var	strSQL = "select cont from ldcodemod where "+k+"="+k				 	
				 + " and codetype = 'uwnoticetype' and Code = '"+tCode+"'";

	

	
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
		divUWSpec1.style.display = 'none';
		divUWSpec.style.display = 'none';
		divnoticecontent.style.display = '';
		QuestQuery(tCode);
		divLCPol.style.display = 'none';
	}
else if(tCode=="86"||tCode=="88"||tCode=="BQ87")
	{
		divUWSpec1.style.display = 'none';
		divUWSpec.style.display = 'none';
		divnoticecontent.style.display = '';
		//alert(document.all('ContNo').value);
		  //var tsql = "select riskcode,uwflag from lcpol where contno = '"+document.all('ContNo').value+"' and mainpolno!=polno and uwflag in ('1','2')";
		    var  sqlid8="MSSendAllNoticeSql7";
		 	var  mySql8=new SqlClass();
		 	mySql8.setResourceName("uw.MSSendAllNoticeSql"); //ָ��ʹ�õ�properties�ļ���
		 	mySql8.setSqlId(sqlid8);//ָ��ʹ�õ�Sql��id
		 	mySql8.addSubPara(document.all('ContNo').value);//ָ������Ĳ���
		 	var tsql=mySql8.getString();
		if(tCode=="BQ87")
		{
		   // tsql = "select riskcode,uwflag from lppol where contno = '"+document.all('ContNo').value+"' and mainpolno!=polno and uwflag in ('1','2') and edorno = '" + fm.EdorNo.value + "' and edortype = '" + fm.EdorType.value + "'";
		    var  sqlid9="MSSendAllNoticeSql8";
		 	var  mySql9=new SqlClass();
		 	mySql9.setResourceName("uw.MSSendAllNoticeSql"); //ָ��ʹ�õ�properties�ļ���
		 	mySql9.setSqlId(sqlid9);//ָ��ʹ�õ�Sql��id
		 	mySql9.addSubPara(document.all('ContNo').value);//ָ������Ĳ���
		 	mySql9.addSubPara(fm.EdorNo.value);//ָ������Ĳ���
		 	mySql9.addSubPara(fm.EdorType.value);//ָ������Ĳ���
		    tsql=mySql9.getString();
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
		  	//tsql = "select riskstatname from lmrisk where riskcode = '"+brrResult[p][0]+"'";
		  	    var  sqlid10="MSSendAllNoticeSql9";
			 	var  mySql10=new SqlClass();
			 	mySql10.setResourceName("uw.MSSendAllNoticeSql"); //ָ��ʹ�õ�properties�ļ���
			 	mySql10.setSqlId(sqlid10);//ָ��ʹ�õ�Sql��id
			 	mySql10.addSubPara(brrResult[p][0]);//ָ������Ĳ���
			    tsql=mySql10.getString();
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

		divUWSpec1.style.display = 'none';
		divUWSpec.style.display = 'none';
		divnoticecontent.style.display = '';
		fm.Content.value = "";
		divLCPol.style.display = 'none';
	}
else if(tCode=="89"||tCode=="BQ88")
	{
		divnoticecontent.style.display = 'none';
		divUWSpec1.style.display = '';
		divUWSpec.style.display = '';
		fm.Content.value = "";
		divLCPol.style.display = 'none';
	}
else if(tCode=="82")
	{
		divUWSpec1.style.display = 'none';
		divUWSpec.style.display = 'none';
		divnoticecontent.style.display = 'none';
		divLCPol.style.display = '';
		
		}
else
	{
		divUWSpec1.style.display = 'none';
		divUWSpec.style.display = 'none';
		divnoticecontent.style.display = 'none';
		divLCPol.style.display = 'none';
	}
}
}

function initLWMission()
{
	var MissionID = fm.MissionID.value;
//		var strSQL = "select submissionid from lwmission where missionid ='"+MissionID+"'"
//							 + " and activityid = '0000001270'"
//								;
		    var  sqlid11="MSSendAllNoticeSql10";
		 	var  mySql11=new SqlClass();
		 	mySql11.setResourceName("uw.MSSendAllNoticeSql"); //ָ��ʹ�õ�properties�ļ���
		 	mySql11.setSqlId(sqlid11);//ָ��ʹ�õ�Sql��id
		 	mySql11.addSubPara(MissionID);//ָ������Ĳ���
		 	var strSQL=mySql11.getString();
								
 var brr = easyExecSql(strSQL);
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
	    var  sqlid12="MSSendAllNoticeSql11";
	 	var  mySql12=new SqlClass();
	 	mySql12.setResourceName("uw.MSSendAllNoticeSql"); //ָ��ʹ�õ�properties�ļ���
	 	mySql12.setSqlId(sqlid12);//ָ��ʹ�õ�Sql��id
	 	mySql12.addSubPara(tContNo);//ָ������Ĳ���
	 	var strSQL=mySql12.getString();
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
     // tSQL = "select remark from loprtmanager where prtseq = '"+tPrtseq+"'";
        var  sqlid13="MSSendAllNoticeSql12";
	 	var  mySql13=new SqlClass();
	 	mySql13.setResourceName("uw.MSSendAllNoticeSql"); //ָ��ʹ�õ�properties�ļ���
	 	mySql13.setSqlId(sqlid13);//ָ��ʹ�õ�Sql��id
	 	mySql13.addSubPara(tPrtseq);//ָ������Ĳ���
	 	tSQL=mySql13.getString();
      arrResult=easyExecSql(tSQL);
      document.all('result').value = arrResult[0][0];
    	return;
    	} 
    if(tCode == "89")
    {
    	//alert(1);
      divresult.style.display = '';
      //tSQL = "select askcode,(select CodeName from LDcode where LDcode.Code = LCRReportPrt.askcode and CodeType = 'rreporttype' ) from LCRReportPrt where prtseq = '"+tPrtseq+"'";
        var  sqlid14="MSSendAllNoticeSql13";
	 	var  mySql14=new SqlClass();
	 	mySql14.setResourceName("uw.MSSendAllNoticeSql"); //ָ��ʹ�õ�properties�ļ���
	 	mySql14.setSqlId(sqlid14);//ָ��ʹ�õ�Sql��id
	 	mySql14.addSubPara(tPrtseq);//ָ������Ĳ���
	 	tSQL=mySql14.getString();
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
function queryUWSpecD(tContNo)
{
//			strSQL = "select A.speccontent"
//                         + " ,(select username from lduser where usercode=A.operator) ,A.makedate,'' "
//                         + " ,(case when (select codename from ldcode where codetype='question' and code=(select sequenceno from lcinsured where contno=A.cNo and insuredno=A.iNo)) is not null then (select codename from ldcode where codetype='question' and code=(select sequenceno from lcinsured where contno=A.cNo and insuredno=A.iNo)) else '��������' end)"
//                         + " ,A.a,''"
//                         + " ,A.iNo"             
//                + "   from"
//			    + "(select speccontent"
//                         + " ,operator ,makedate,contno cNo"                         
//                         + " ,(select codename from ldcode where codetype='printstate' and code=s.prtflag) a"
//                         + " ,s.customerno iNo"             
//                + "   from lccspec s "
//                + "   where contno = '"+tContNo+"' and prtflag is not null ) A";
			
			var  sqlid15="MSSendAllNoticeSql14";
		 	var  mySql15=new SqlClass();
		 	mySql15.setResourceName("uw.MSSendAllNoticeSql"); //ָ��ʹ�õ�properties�ļ���
		 	mySql15.setSqlId(sqlid15);//ָ��ʹ�õ�Sql��id
		 	mySql15.addSubPara(tContNo);//ָ������Ĳ���
		 	strSQL=mySql15.getString();
	
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
function querySpec(tContNo)
{	
//			strSQL = "select A.speccontent"
//                         + " ,(select username from lduser where usercode=A.operator) ,A.makedate,'' "
//                         + " ,(case when (select codename from ldcode where codetype='question' and code=(select sequenceno from lcinsured where contno=A.cNo and insuredno=A.iNo)) is not null then (select codename from ldcode where codetype='question' and code=(select sequenceno from lcinsured where contno=A.cNo and insuredno=A.iNo)) else '��������' end)"
//                         + " ,'Y',A.iNo"             
//                + "   from"
//				+ "(select speccontent"
//                         + " ,operator ,makedate,contno cNo "                         
//                         + " ,s.customerno iNo"             
//                + "   from lccspec s "
//                + "   where contno = '"+tContNo+"' and prtflag is null and needprint='Y') A";
			
			var  sqlid16="MSSendAllNoticeSql15";
		 	var  mySql16=new SqlClass();
		 	mySql16.setResourceName("uw.MSSendAllNoticeSql"); //ָ��ʹ�õ�properties�ļ���
		 	mySql16.setSqlId(sqlid16);//ָ��ʹ�õ�Sql��id
		 	mySql16.addSubPara(tContNo);//ָ������Ĳ���
		 	strSQL=mySql16.getString();
	
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
function queryAddFee(tContNo)
{     
//			strSQL = "select rc ,(select riskname from lmriskapp where riskcode=A.rc) riskname"
//			       + " ,a,payplancode,prem,polno,SuppRiskScore,(select username from lduser where usercode=A.operator),makedate,'' "
//			       + " ,(case when (select codename from ldcode where codetype='question' and code=(select sequenceno from lcinsured where contno=A.cNo and insuredno=A.iNo)) is not null then (select codename from ldcode where codetype='question' and code=(select sequenceno from lcinsured where contno=A.cNo and insuredno=A.iNo)) else '��������' end)"
//			       + " ,'Y',iNo"
//			       + " from "
//			       + " (select (select riskcode from lcpol where polno=p.polno) rc,payplantype,contno cNo"
//			       + " ,(case trim(payplantype) when '01' then '�����ӷ�' when '02' then 'ְҵ�ӷ�' when '03' then '��ס�ؼӷ�' when '04' then '���üӷ�' end) a,payplancode,prem,polno,operator,makedate"
//			       + " ,(select insuredno from lcpol where polno=p.polno) iNo,SuppRiskScore"
//			       + " from lcprem p where  payplancode like '000000%%' "
//                + " and contno = '"+tContNo+"'"
//                + " and exists(select 1 from lcuwmaster where polno=p.polno and addpremflag is not null and addpremflag='1')"//��Ҫ��ӡ֪ͨ��
//                + ") A";
			
			var  sqlid17="MSSendAllNoticeSql16";
		 	var  mySql17=new SqlClass();
		 	mySql17.setResourceName("uw.MSSendAllNoticeSql"); //ָ��ʹ�õ�properties�ļ���
		 	mySql17.setSqlId(sqlid17);//ָ��ʹ�õ�Sql��id
		 	mySql17.addSubPara(tContNo);//ָ������Ĳ���
		 	strSQL=mySql17.getString();
                
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
//			strSQL = "select riskcode,(select riskname from lmriskapp where riskcode=a.riskcode),amnt, mult,prem, concat(payendyear,payendyearflag),concat(insuyear,insuyearflag),payintv"
//			    + " ,(select concat(concat(a.uwflag,'-'),codename) from ldcode where codetype='uwstate' and code=a.uwflag)"
//			    + " ,(select username from lduser where usercode=b.operator),b.modifydate,'','Ͷ����','Y'  "
//			    + " from lcpol a,lcuwmaster b  "
//			    + " where a.contno = '"+tContNo+"' and a.proposalno=b.proposalno and b.changepolflag is not null and b.changepolflag='1' "
//			    + " order by a.mainpolno,a.polno  ";
			var  sqlid18="MSSendAllNoticeSql17";
		 	var  mySql18=new SqlClass();
		 	mySql18.setResourceName("uw.MSSendAllNoticeSql"); //ָ��ʹ�õ�properties�ļ���
		 	mySql18.setSqlId(sqlid18);//ָ��ʹ�õ�Sql��id
		 	mySql18.addSubPara(tContNo);//ָ������Ĳ���
		 	strSQL=mySql18.getString();
                
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
function queryUWExistD(tContNo)
{			 
//			strSQL = "select Contente,(select username from lduser where usercode=l.operator),makedate"
//			        + " ,'Ͷ����'"
//			        + " ,(select codename from ldcode where codetype='printstate' and code=l.replyflag)"
//			       + " ,(case l.ReplyOperator when null then 'δ�ظ�' else '�ѻظ�' end)"			      
//			       + " from LCRReport l where 1=1 "
//                + " and contno = '"+tContNo+"' and replyflag is not null";
			
			var  sqlid19="MSSendAllNoticeSql18";
		 	var  mySql19=new SqlClass();
		 	mySql19.setResourceName("uw.MSSendAllNoticeSql"); //ָ��ʹ�õ�properties�ļ���
		 	mySql19.setSqlId(sqlid19);//ָ��ʹ�õ�Sql��id
		 	mySql19.addSubPara(tContNo);//ָ������Ĳ���
		 	strSQL=mySql19.getString();
                
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
//			       + " from LCRReport l where 1=1 "
//                + " and contno = '"+tContNo+"' and replyflag is null";
			
			var  sqlid20="MSSendAllNoticeSql19";
		 	var  mySql20=new SqlClass();
		 	mySql20.setResourceName("uw.MSSendAllNoticeSql"); //ָ��ʹ�õ�properties�ļ���
		 	mySql20.setSqlId(sqlid20);//ָ��ʹ�õ�Sql��id
		 	mySql20.addSubPara(tContNo);//ָ������Ĳ���
		 	strSQL=mySql20.getString();

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
function queryUWHealthD(tContNo)
{
//			strSQL = "select '', (select username from lduser where usercode=A.operator), A.makedate, '', (case when (select codename from ldcode where codetype='question' and code=A.customercode) is not null then (select codename from ldcode where codetype='question' and code=A.customercode) else '��������' end), A.printstate, A.replystate, A.prtseq"
//                +" from (select operator, makedate, "
//                +" ( case customertype when 'A' then '0' else (select sequenceno from lcinsured where contno='"+tContNo+"' and insuredno=customerno) end) customercode,"
//                +" (select codename from ldcode where codetype='printstate' and code=printflag) printstate,"
//		        + " (case peresult when null then 'δ�ظ�' else '�ѻظ�' end) replystate, "
//                +" prtseq "
//			                   + " from LCPENotice where 1=1 "
//                               + " and contno = '"+tContNo+"' and printflag is not null) A";
			
			var  sqlid21="MSSendAllNoticeSql20";
		 	var  mySql21=new SqlClass();
		 	mySql21.setResourceName("uw.MSSendAllNoticeSql"); //ָ��ʹ�õ�properties�ļ���
		 	mySql21.setSqlId(sqlid21);//ָ��ʹ�õ�Sql��id
		 	mySql21.addSubPara(tContNo);//ָ������Ĳ���
		 	mySql21.addSubPara(tContNo);//ָ������Ĳ���
		 	strSQL=mySql21.getString();
  //alert(strSQL);
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
//    tSQL = "select peitemname from lcpenoticeitem where 1=1 "
//	       + " and contno = '"+ tContNo +"' and prtseq = '"+turnPage.arrDataCacheSet[i][7]+"' order by peitemcode" ;
    
    var  sqlid22="MSSendAllNoticeSql21";
 	var  mySql22=new SqlClass();
 	mySql22.setResourceName("uw.MSSendAllNoticeSql"); //ָ��ʹ�õ�properties�ļ���
 	mySql22.setSqlId(sqlid22);//ָ��ʹ�õ�Sql��id
 	mySql22.addSubPara(tContNo);//ָ������Ĳ���
 	mySql22.addSubPara(turnPage.arrDataCacheSet[i][7]);//ָ������Ĳ���
 	tSQL=mySql22.getString();
 	
	arrResult=easyExecSql(tSQL);
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
function queryUWHealth(tContNo)
{
//			strSQL = "select '', (select username from lduser where usercode=A.operator), A.makedate, '', (case when (select codename from ldcode where codetype='question' and code=A.customercode) is not null then (select codename from ldcode where codetype='question' and code=A.customercode) else '��������' end), '', A.prtseq"
//                +" from (select operator, makedate, (case customertype when 'A' then '0' else (select sequenceno from lcinsured where contno='"+tContNo+"' and insuredno=customerno) end) customercode, prtseq"
//			                   + " from LCPENotice where 1=1 "
//                               + " and contno = '"+tContNo+"' and printflag is null) A";
			
			    var  sqlid23="MSSendAllNoticeSql22";
			 	var  mySql23=new SqlClass();
			 	mySql23.setResourceName("uw.MSSendAllNoticeSql"); //ָ��ʹ�õ�properties�ļ���
			 	mySql23.setSqlId(sqlid23);//ָ��ʹ�õ�Sql��id
			 	mySql23.addSubPara(tContNo);//ָ������Ĳ���
			 	mySql23.addSubPara(tContNo);//ָ������Ĳ���
			 	strSQL=mySql23.getString();
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
//    tSQL = "select peitemname from lcpenoticeitem where 1=1 "
//	       + " and contno = '"+ tContNo +"' and prtseq = '"+turnPage.arrDataCacheSet[i][6]+"' order by peitemcode" ;
    var  sqlid24="MSSendAllNoticeSql23";
 	var  mySql24=new SqlClass();
 	mySql24.setResourceName("uw.MSSendAllNoticeSql"); //ָ��ʹ�õ�properties�ļ���
 	mySql24.setSqlId(sqlid24);//ָ��ʹ�õ�Sql��id
 	mySql24.addSubPara(tContNo);//ָ������Ĳ���
 	mySql24.addSubPara(turnPage.arrDataCacheSet[i][6]);//ָ������Ĳ���
 	tSQL=mySql24.getString();
 	
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
//	       + " and (state is null or state='')"
//	       + " and backobjtype in ('3','4') and needprint='Y' "
//	       + " union "
//	       + " select proposalcontno,issuetype,issuecont,replyresult,(select username from lduser where usercode=a.operator),makedate,operatepos, "
//	       + " backobj,StandbyFlag2,serialno,(select codename from ldcode where codetype='backobj' and comcode=a.BackObjType and othersign=a.standbyflag2) "
//	       + " ,(case when (select codename from ldcode where codetype='question' and code=a.questionobjtype) is not null then (select codename from ldcode where codetype='question' and code=a.questionobjtype) else '��������' end),questionobj,needprint from lcissuepol a where contno='"+tContNo+"' "
//	       //+ " and a.prtseq is null "
//	       + " and (state is null or state='')"
//	       + " and backobjtype in ('1','2') and needprint='Y'  ";
	var  sqlid25="MSSendAllNoticeSql24";
 	var  mySql25=new SqlClass();
 	mySql25.setResourceName("uw.MSSendAllNoticeSql"); //ָ��ʹ�õ�properties�ļ���
 	mySql25.setSqlId(sqlid25);//ָ��ʹ�õ�Sql��id
 	mySql25.addSubPara(tContNo);//ָ������Ĳ���
 	mySql25.addSubPara(tContNo);//ָ������Ĳ���
 	strSQL=mySql25.getString();

  //��ѯSQL�����ؽ���ַ���
  initQuestGrid();
  turnPageQuest.queryModal(strSQL,QuestGrid);
 // turnPageQuest.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 
/*
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
  */
}

//��ѯ�����ѷ��������
function queryQuestD(tContNo)
{
	initQuestDGrid();
	if(_DBT==_DBO){
//		strSQL = " select proposalcontno,issuetype,issuecont,(select username from lduser where usercode=a.operator),makedate, "
//			+ " (select codename from ldcode where codetype='backobj' and comcode=a.BackObjType and othersign=a.standbyflag2), "
//			+ " '', (select codename from ldcode where codetype='printstate' and code=state),"
//			+ " (case a.ReplyMan when null then 'δ�ظ�' when '' then 'δ�ظ�' else '�ѻظ�' end),needprint "
//			+ "  from lcissuepol a where contno='"+tContNo+"' "
//			+ " and state is not null "
//			+ " and backobjtype in ('3','4') and needprint='Y' "
//			+ " union "
//			+ " select proposalcontno,issuetype,issuecont,(select username from lduser where usercode=a.operator),makedate, "
//			+ " (select codename from ldcode where codetype='backobj' and comcode=a.BackObjType and othersign=a.standbyflag2), "
//			+ " (case when (select codename from ldcode where codetype='question' and code=questionobjtype) is not null then (select codename from ldcode where codetype='question' and code=questionobjtype) else '��������' end), (select codename from ldcode where codetype='printstate' and code=state),"
//			+ " (case a.ReplyMan when null then 'δ�ظ�' when '' then 'δ�ظ�' else '�ѻظ�' end),needprint "
//			+ "  from lcissuepol a where contno='"+tContNo+"' "
//			+ " and state is not null "
//			+ " and backobjtype in ('1','2') and needprint='Y'  "
//			;
		var  sqlid26="MSSendAllNoticeSql25";
	 	var  mySql26=new SqlClass();
	 	mySql26.setResourceName("uw.MSSendAllNoticeSql"); //ָ��ʹ�õ�properties�ļ���
	 	mySql26.setSqlId(sqlid26);//ָ��ʹ�õ�Sql��id
	 	mySql26.addSubPara(tContNo);//ָ������Ĳ���
	 	mySql26.addSubPara(tContNo);//ָ������Ĳ���
	 	strSQL=mySql26.getString();
	}else if(_DBT==_DBM){
//		strSQL = " select proposalcontno,issuetype,issuecont,(select username from lduser where usercode=a.operator),makedate, "
//		       + " (select codename from ldcode where codetype='backobj' and comcode=a.BackObjType and othersign=a.standbyflag2), "
//		       + " '', (select codename from ldcode where codetype='printstate' and code=state),"
//			   + " (case a.ReplyMan when null then 'δ�ظ�' when '' then 'δ�ظ�' else '�ѻظ�' end),needprint "
//		       + "  from lcissuepol a where contno='"+tContNo+"' "
//		       + " and state is not null and state !=''"
//		       + " and backobjtype in ('3','4') and needprint='Y' "
//		       + " union "
//		       + " select proposalcontno,issuetype,issuecont,(select username from lduser where usercode=a.operator),makedate, "
//		       + " (select codename from ldcode where codetype='backobj' and comcode=a.BackObjType and othersign=a.standbyflag2), "
//		       + " (case when (select codename from ldcode where codetype='question' and code=questionobjtype) is not null then (select codename from ldcode where codetype='question' and code=questionobjtype) else '��������' end), (select codename from ldcode where codetype='printstate' and code=state),"
//			   + " (case a.ReplyMan when null then 'δ�ظ�' when '' then 'δ�ظ�' else '�ѻظ�' end),needprint "
//		       + "  from lcissuepol a where contno='"+tContNo+"' "
//		       + " and state is not null and state !='' "
//		       + " and backobjtype in ('1','2') and needprint='Y'  "
//		       ;
		var  sqlid27="MSSendAllNoticeSql26";
	 	var  mySql27=new SqlClass();
	 	mySql27.setResourceName("uw.MSSendAllNoticeSql"); //ָ��ʹ�õ�properties�ļ���
	 	mySql27.setSqlId(sqlid27);//ָ��ʹ�õ�Sql��id
	 	mySql27.addSubPara(tContNo);//ָ������Ĳ���
	 	mySql27.addSubPara(tContNo);//ָ������Ĳ���
	 	strSQL=mySql27.getString();
	}
  //��ѯSQL�����ؽ���ַ���
  turnPageQuestD.queryModal(strSQL,QuestDGrid);
  /*
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
  */
}