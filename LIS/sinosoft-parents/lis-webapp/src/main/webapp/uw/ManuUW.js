//�������ƣ�Underwrite.js
//�����ܣ������˹��˱�
//�������ڣ�2002-09-24 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����

//���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var flag;
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnConfirmPage = new turnPageClass();
var k = 0;
var cflag = "1";  //���������λ�� 1.�˱�
var pflag = "1";  //�������� 1.���˵�
var arrResult;
// ��Ǻ˱�ʦ�Ƿ��Ѳ鿴����Ӧ����Ϣ
var showPolDetailFlag ;
var showAppFlag ;
var showHealthFlag ;
var QuestQueryFlag ;
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
//�ύ�����水ť��Ӧ����
function submitForm(flag)
{  
  var i = 0;
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;   
  
  //tongmeng 2009-05-09
  //����и��ӻ�����,У������ձ���Ϳɻ��������ۼƱ����Ƿ�һ��.
  if(flag==0)
  {
  	//��ѯ��ͬ���Ƿ��л�����.
  	
  	var sqlid917141410="DSHomeContSql917141410";
	var mySql917141410=new SqlClass();
	mySql917141410.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
	mySql917141410.setSqlId(sqlid917141410);//ָ��ʹ�õ�Sql��id
	mySql917141410.addSubPara(ContNo);//ָ������Ĳ���
	mySql917141410.addSubPara(ContNo);//ָ������Ĳ���
	var tSQL_HM=mySql917141410.getString();
  	
//  	var tSQL_HM = "select '1' from lcpol where riskcode='121301' and contno='"+ContNo+"' "
//  	            + " and uwflag not in ('1','2','a') "
//  	            + " union "
//  	            + " select '2' from lcpol where riskcode='121506' and contno='"+ContNo+"' "
//  	            + " and uwflag not in ('1','2','a') ";
  	var tFlag_HM = easyExecSql(tSQL_HM);
  	if(tFlag_HM!=null)
  	{
  		 var tSQL_CheckHM = "";
  		 var tFlag_CheckHM = "";
  		 if(tFlag_HM[0][0]=='1')
  		 {
  		 	 //У��Ͷ���˻����ձ����Ƿ�������ձ���  
//  		 	 tSQL_CheckHM = "select decode(count(*),0,0,1) from ( "
//	                    + " select round(nvl(sum(a.prem),0),2) x,round(nvl(sum(b.amnt),0),2) y "
//	                    + " from lcpol a,lcpol b "
//	                    + " where a.contno=b.contno and a.contno='"+ContNo+"' "
//	                    + " and a.riskcode<>'121301' and b.riskcode='121301' "
//	                    + " and a.riskcode in (select code1 from ldcode1 where codetype='freerisk' "
//	                    + " and code='121301') ) A where A.x<>A.y ";
  			
  			var sqlid917141648="DSHomeContSql917141648";
			var mySql917141648=new SqlClass();
			mySql917141648.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
			mySql917141648.setSqlId(sqlid917141648);//ָ��ʹ�õ�Sql��id
			mySql917141648.addSubPara(ContNo);//ָ������Ĳ���
			mySql917141648.addSubPara(ContNo);//ָ������Ĳ���
			tSQL_CheckHM=mySql917141648.getString();
  			
//  			tSQL_CheckHM = " select 1 from dual where ((select sum(a.prem)"
//				+ " from lcpol a"
//				+ " where a.riskcode <> '121301' and a.contno = '"+ContNo+"'"
//				+ " and uwflag not in ('1','2','a') "
//				+ " and a.riskcode in (select code1"
//				+ " from ldcode1 where codetype = 'freerisk'"
//				+ " and code = '121301')) <> (select sum(b.amnt)"
//				+ " from lcpol b where b.riskcode = '121301'"
//				+ " and uwflag not in ('1','2','a') "
//				+ " and b.contno = '"+ContNo+"'))";
  		 //	prompt('tSQL_CheckHM',tSQL_CheckHM);
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
  			 var sqlid917141845="DSHomeContSql917141845";
			var mySql917141845=new SqlClass();
			mySql917141845.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
			mySql917141845.setSqlId(sqlid917141845);//ָ��ʹ�õ�Sql��id
			mySql917141845.addSubPara(ContNo);//ָ������Ĳ���
			var AMainPolPaySql=mySql917141845.getString();

  			 
//  			 var AMainPolPaySql = "select payintv from lcpol where contno = '"+ContNo+"'"
//  				 		+ " and riskcode in (select code1 from ldcode1 where codetype = 'freerisk'"
//						+ " and code = '121301') and uwflag not in ('1','2','a') ";
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
  			 var sqlid917142000="DSHomeContSql917142000";
			var mySql917142000=new SqlClass();
			mySql917142000.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
			mySql917142000.setSqlId(sqlid917142000);//ָ��ʹ�õ�Sql��id
			mySql917142000.addSubPara(ContNo);//ָ������Ĳ���
			var ACheckPayIntvSql=mySql917142000.getString();

  			 
//  			 var ACheckPayIntvSql = "select decode(count(*),0,0,1) from ( "
//		                + " select a.payyears x,b.payyears y "
//		                + " from lcpol a,lcpol b "
//		                + " where a.contno=b.contno and a.contno='"+ContNo+"' "
//		                + " and a.uwflag not in ('1','2','a') "
//		                + " and b.uwflag not in ('1','2','a') "
//		                + " and a.riskcode<>'121301' and b.riskcode='121301' "
//		                + " and a.riskcode in (select code1 from ldcode1 where codetype='freerisk' "
//		                + " and code='121301') ) A where A.x<>A.y ";
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
//  		 	 tSQL_CheckHM = "select A.z,decode(count(*),0,0,1) from ( "
//											+ " select a.insuredno z ,round(nvl(sum(a.prem),0),2) x,round(nvl(sum(b.amnt),0),2) y "
//											+ " from lcpol a,lcpol b "
//	                    + " where a.contno=b.contno and a.insuredno=b.insuredno "
//	                    + " and a.contno='"+ContNo+"' "
//	                    + " and a.riskcode<>'121506' and b.riskcode='121506' "
//	                    + " and a.riskcode in (select code1 from ldcode1 where codetype='freerisk' "
//	                    + " and code='121506') group by a.insuredno ) A where A.x<>A.y "
//											+ " group by A.z ";
  			var sqlid917142115="DSHomeContSql917142115";
			var mySql917142115=new SqlClass();
			mySql917142115.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
			mySql917142115.setSqlId(sqlid917142115);//ָ��ʹ�õ�Sql��id
			mySql917142115.addSubPara(ContNo);//ָ������Ĳ���
			mySql917142115.addSubPara(ContNo);//ָ������Ĳ���
			tSQL_CheckHM=mySql917142115.getString();
  			
//  			tSQL_CheckHM = " select 1 from dual where ((select sum(a.prem)"
//				+ " from lcpol a"
//				+ " where a.riskcode <> '121506' and a.contno = '"+ContNo+"'"
//				+ " and uwflag not in ('1','2','a') "
//				+ " and a.riskcode in (select code1"
//				+ " from ldcode1 where codetype = 'freerisk'"
//				+ " and code = '121506')) <> (select sum(b.amnt)"
//				+ " from lcpol b where b.riskcode = '121506'"
//				+ " and b.contno = '"+ContNo+"' and uwflag not in ('1','2','a'))  )";
//  		 	 	prompt('tSQL_CheckHM',tSQL_CheckHM);
  		 	 tFlag_CheckHM = easyExecSql(tSQL_CheckHM);
  			 if(tFlag_CheckHM!=null)
  		 	 {
  		 			for(i=0;i<tFlag_CheckHM.length;i++)
  		 			{
							if(tFlag_CheckHM[i][0]=='1')
							{
						 		alert('������'+tFlag_CheckHM[i][0]+'�������ֵı��������Ӧ���ֵ��ۼƱ���,���޸Ļ����ձ�������º˱�����!');
						 		return false;
							}
						}
  		 	 }
  			//05-13 ����У�����ս����ڼ�Ϊ���������ܴ��и��ӻ�����
  			 var sqlid917142229="DSHomeContSql917142229";
			var mySql917142229=new SqlClass();
			mySql917142229.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
			mySql917142229.setSqlId(sqlid917142229);//ָ��ʹ�õ�Sql��id
			mySql917142229.addSubPara(ContNo);//ָ������Ĳ���
			var IMainPolPaySql=mySql917142229.getString();

  			 
//  			 var IMainPolPaySql = "select lcpol.insuredno,lcpol.payintv from lcpol where contno = '"+ContNo+"'"
//  				 		+ " and riskcode in (select code1 from ldcode1 where codetype = 'freerisk'"
//						+ " and code = '121506') and uwflag not in ('1','2','a') ";
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
  			var sqlid917143937="DSHomeContSql917143937";
			var mySql917143937=new SqlClass();
			mySql917143937.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
			mySql917143937.setSqlId(sqlid917143937);//ָ��ʹ�õ�Sql��id
			mySql917143937.addSubPara(ContNo);//ָ������Ĳ���
			var ICheckPayIntvSql=mySql917143937.getString();
  			
//  			var ICheckPayIntvSql = "select a.insuredno from lcpol a, lcpol b where a.contno = '"+ContNo+"'"
//  						+ " and a.contno = b.contno and a.payyears != b.payyears"
//  						+ " and a.uwflag not in ('1','2','a') "
//  						+ " and b.uwflag not in ('1','2','a') "
//  						+ " and a.riskcode = '121506' and b.riskcode in (select code1"
//  						+ " from ldcode1 where codetype = 'freerisk'"
//  						+ " and code = '121506') and b.riskcode <> '121506'";
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
//  	 return false;
  }
//  alert("У��ʧ�ܣ�");
//  return false;
  
 // return ;
  //tongmeng 2009-02-06 add
  //���Ϊ7״̬,�����κ�����,ֱ�ӿ����º˱�����
  var ContUWStatus = "";
  if(flag==0)
  {
  	var sqlid917144119="DSHomeContSql917144119";
	var mySql917144119=new SqlClass();
	mySql917144119.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
	mySql917144119.setSqlId(sqlid917144119);//ָ��ʹ�õ�Sql��id
	//mySql917144119.addSubPara(MissionID);//ָ������Ĳ��� lzf
	mySql917144119.addSubPara(ContNo);//lzf
	var tSQL_UWStatus=mySql917144119.getString();

  	
//  	var tSQL_UWStatus = "select nvl(missionprop18,'1') from lwmission where missionid='"+MissionID+"'"
//  	                  + " and activityid = '0000001100' ";
  	var ContUWStatusArr=easyExecSql(tSQL_UWStatus);
  	if(ContUWStatusArr!=null)
  	{
  		ContUWStatus = ContUWStatusArr[0][0];
  		//alert(ContUWStatus);
  		//return false;
  	}
  	if(ContUWStatus!=null&&ContUWStatus=='7')
  	{
  			if(!confirm("��Ͷ��������7״̬,ǿ���º˱�����?"))
	    	{
	    			//showInfo.close();
	    			return false;
	    	}	
  	}
  	
  	//SYY
  	var sql = "select uwstate from lccuwmaster where contno = '"+ContNo+"' and uwstate in ('6')";
	var arr = easyExecSql(sql);
	if (arr) {
		 alert("�ú�ͬ��ǰ����"+arr[0][0]+"״̬,�������º˱�����");   
	     return false;   
	}
	
	/*
  	if(!checkUWState('6',MissionID,SubMissionID,'�º˱�����'))
	 	{
	 	 	return false;
	 	}
	 	*/
  }
  
    
	//prompt('ReportFlag',ReportFlag+":"+document.all('uwUpReport').value);
	//tongmeng 2009-02-07 modify
	//���ֶ༶�ϱ�.
	/*
  if(ReportFlag=="1"&document.all('uwUpReport').value=="1")
  {
  	//alert('�������Ѱ������ϱ���ֻ���ں˱�������ѡ��"�����¼�"��');
  	alert('�����ϱ��İ�����ֻ���ں˱�������ѡ��"�����¼�"��"�������"!');
   	document.all('uwUpReport').value="4";
   	document.all('uwUpReportName').value="�����¼�";
   	return;
  }*/
  
  /*if(ReportFlag=="2"&document.all('uwUpReport').value!="0")
  {
   	alert('�������Ѱ������ϱ���ֻ���ں˱�������ѡ��"�������"��');
   	document.all('uwUpReport').value="0";
   	document.all('uwUpReportName').value="�������";
   	return;

   }*/
   /*
  if(ReportFlag=="4"&document.all('uwUpReport').value=="4")
  {
   	alert('���ڷ����¼��İ���,ֻ���ں˱�������ѡ��"�ϱ�"��"�������"');
   	document.all('uwUpReport').value="0";
   	document.all('uwUpReportName').value="�������";
   	return;

   }*/

	if(flag==0)
	{
		    //������Լ����ʾ
	    var sqlid917144219="DSHomeContSql917144219";
		var mySql917144219=new SqlClass();
		mySql917144219.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
		mySql917144219.setSqlId(sqlid917144219);//ָ��ʹ�õ�Sql��id
		mySql917144219.addSubPara(ContNo);//ָ������Ĳ���
		var tSql_spec=mySql917144219.getString();
	    
//	    var tSql_spec = " select * from lcspec where contno='"+ContNo+"' and prtseq is null ";
	     turnConfirmPage.strQueryResult = easyQueryVer3(tSql_spec, 1, 0, 1);
	   
	    if (turnConfirmPage.strQueryResult) 
	    {
	    		if(!confirm("����Լ��Ϣ,�Ƿ�Ҫֱ�ӳб�?"))
	    		{
	    			//showInfo.close();
	    			return false;
	    			}
	    }
	    //���ݷ��ձ����ж��Ƿ���Ҫ�ٱ��ʱ� 2008-12-12 ln add
	    //tongmeng 2009-05-11 add
	    //���������,�ܱ�,����,��У���Ƿ��ٱ�
	    var tempUWState = document.all('uwState').value ;
	    
	    //alert(tempUWState);
	    var sqlid917144300="DSHomeContSql917144300";
		var mySql917144300=new SqlClass();
		mySql917144300.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
		mySql917144300.setSqlId(sqlid917144300);//ָ��ʹ�õ�Sql��id
		mySql917144300.addSubPara(ContNo);//ָ������Ĳ���
		var strSql=mySql917144300.getString();
	    
//	    var strSql = "select * from lccuwmaster where contno='"+ContNo+"' and SugPassFlag is not null and SugPassFlag='Y'"; 
	    var isReinsuring=easyExecSql(strSql);
	    if((document.all('UpReporFlag').value == "Y" || isReinsuring!=null)&&(tempUWState=='9'||tempUWState=='4'))
	    {
	        alert("�˵����ٱ��ʱ�!");
	    	return ;
	    }
	   // return;
	    //end 	
	    
	    //�����˹��˱��޸������Զ����̱�־У�飬�����4��9������¿ۿ���Զ�����Ҫ��ʾ
	    if(tempUWState=='9'||tempUWState=='4')
	    {
	    	var sqlid917144350="DSHomeContSql917144350";
			var mySql917144350=new SqlClass();
			mySql917144350.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
			mySql917144350.setSqlId(sqlid917144350);//ָ��ʹ�õ�Sql��id
			mySql917144350.addSubPara(ContNo);//ָ������Ĳ���
			var tNewAutoSendBankFlagSql=mySql917144350.getString();
	    	
//	    	var tNewAutoSendBankFlagSql = "select 1 from LCCont where ContNo='"+ContNo+"' and NewAutoSendBankFlag='0'";
  	    var tNewAutoSendBankFlag=easyExecSql(tNewAutoSendBankFlagSql);
  	    if(tNewAutoSendBankFlag!=null && tNewAutoSendBankFlag[0][0]=="1")
      	{
  	    	 alert("�˵����ڽɷѷ�ʽΪ���в����̣����޸�Ϊ����!");
	    	   return ;
      	}
	    }
	  }
	  if(!checkAutoUWPass('�º�ͬ�˱�����'))
    {
    	return false;
    }	
		
//  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	  var iWidth=550;      //�������ڵĿ��; 
	  var iHeight=250;     //�������ڵĸ߶�; 
	  var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	  var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	  showInfo.focus();

  if(flag==1)
  {

    var tUWState = fm.uwState.value;                  //�˱�����
    var tUWIdea = fm.UWIdea.value;
    var tUpReport = fm.uwUpReport.value                //�ϱ���־
    var tUWPopedom = fm.uwPopedom.value;              //�˱�ʦ����

    if(tUWState == "")
     {
     	showInfo.close();
      alert("����¼��б��˱�����!");
    	return ;
    }   
    //if(tUWIdea == "")
    // {
    //  showInfo.close();
    //  alert("����¼��б��˱����!");
    //  return ;
    //}

    k++;

    var sqlid917144501="DSHomeContSql917144501";
	var mySql917144501=new SqlClass();
	mySql917144501.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
	mySql917144501.setSqlId(sqlid917144501);//ָ��ʹ�õ�Sql��id
	mySql917144501.addSubPara(fm.MissionID.value);//ָ������Ĳ���
	var strsql=mySql917144501.getString();
    
//    var strsql = "select submissionid from lwmission where "+k+"="+k
//                +" and activityid = '0000001110'"
//    						+" and missionid = '"+fm.MissionID.value+"'"
//    						;

    turnConfirmPage.strQueryResult = easyQueryVer3(strsql, 1, 0, 1);
    if (!turnConfirmPage.strQueryResult) {
        showInfo.close();
    	alert("δ�鵽������SubMissionID");
    	fm.SubConfirmMissionID.value = "";
    	return;
    }
    turnConfirmPage.arrDataCacheSet = decodeEasyQueryResult(turnConfirmPage.strQueryResult);
    fm.SubConfirmMissionID.value = turnConfirmPage.arrDataCacheSet[0][0];
    //alert();
    lockScreen('lkscreen');  
    fm.action = "./UWManuNormChk.jsp";
    document.getElementById("fm").submit(); //�ύ
  	return;
  }
  else if(flag ==2)
  {
    //var tSelNo = PolAddGrid.getSelNo();
    //document.all('PrtNo').value = PolAddGrid.getRowColData(tSelNo - 1,3);
    //document.all('PolNo').value = PolAddGrid.getRowColData(tSelNo - 1,1);

    var tUWState = fm.uwState.value;                  //�˱�����
    //var tUWDelay = fm.UWDelay.value;
    var tUWIdea = fm.UWIdea.value;
    var tUpReport = fm.uwUpReport.value                //�ϱ���־
    var tUWPopedom = fm.uwPopedom.value;              //�˱�ʦ����
    //var tUWPer = fm.uwPer.value;                      //�˱�ʦ


    if(tUWState == "")
     {
     	showInfo.close();
        alert("����¼��б��˱�����!");
    	return ;
    }

  //���Ѱ������ٱ��ϱ���Ҫ¼��˱�����ͺ˱�ʦ
  //  if(tUpReport == "1" || tUpReport == "3"|| tUpReport == "2"|| tUpReport == "4")
  //  {
  //    if (tUWPopedom == "" || tUWPer == "")
  //    {
  //        showInfo.close();
  //        alert("�˱�ʦ����ͺ˱�ʦ���벻��Ϊ��!!!");
  //        return ;
  //    }
  //  }

    //if(tUWIdea == "")
    // {
    //  showInfo.close();
    //  alert("����¼��б��˱����!");
    //  return ;
    //}


    k++;

    var sqlid917144602="DSHomeContSql917144602";
	var mySql917144602=new SqlClass();
	mySql917144602.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
	mySql917144602.setSqlId(sqlid917144602);//ָ��ʹ�õ�Sql��id
	mySql917144602.addSubPara(fm.MissionID.value);//ָ������Ĳ���
	var strsql=mySql917144602.getString();
    
//    var strsql = "select submissionid from lwmission where "+k+"="+k
//                +" and activityid = '0000001110'"
//    						+" and missionid = '"+fm.MissionID.value+"'"
//    						;

    turnConfirmPage.strQueryResult = easyQueryVer3(strsql, 1, 0, 1);
    if (!turnConfirmPage.strQueryResult) {
        showInfo.close();
    	alert("δ�鵽������SubMissionID");
    	fm.SubConfirmMissionID.value = "";
    	return;
    }
    
    turnConfirmPage.arrDataCacheSet = decodeEasyQueryResult(turnConfirmPage.strQueryResult);
    fm.SubConfirmMissionID.value = turnConfirmPage.arrDataCacheSet[0][0];
  
    lockScreen('lkscreen');  
    fm.action = "./UWManuNormChk.jsp";
    document.getElementById("fm").submit(); //�ύ
    //alert("submit");
  	return;
  }
  else if(flag==0)
  {
  	 //�ж��Ƿ���ʾ��ʾ<ĿǰΪָ����Ч����ʾ 2006-10-27>
  	 if(chkIsSugInfo()==false){return;}  	
  	//add by yaory for verify 
  	//�ڽ��к˱�ȷ��ʱ�ٴ��ж��Ƿ���Ҫ�����ٱ�
      //����ٱ��ʱ����ڴ�����������κ���ת������
      
     //tongmeng 2009-02-06 add
     //���7״̬������
     if(ContUWStatus!=null&&ContUWStatus!="7")
     { 
      	var sqlid917144702="DSHomeContSql917144702";
		var mySql917144702=new SqlClass();
		mySql917144702.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
		mySql917144702.setSqlId(sqlid917144702);//ָ��ʹ�õ�Sql��id
		mySql917144702.addSubPara(MissionID);//ָ������Ĳ���
		strSql=mySql917144702.getString();
      	
//      	strSql = "select * from lwmission a where a.missionid='"+MissionID+"' and a.activityid='0000001140' "; 
      	var isReinsuring=easyExecSql(strSql);
      	//prompt('',strSql);
      	if(isReinsuring!=null){
         	showInfo.close();
         	alert("�ٱ��ʱ���û�лظ�����������б�����ת������");
         	return;
      	}
      
      	var tContNo = document.all('ContNo').value;
    	//�ж��Ƿ��мӷѣ��������Ҫ���ύʱУ���Ƿ���Ҫ�ٱ�
    		var sqlid917144801="DSHomeContSql917144801";
			var mySql917144801=new SqlClass();
			mySql917144801.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
			mySql917144801.setSqlId(sqlid917144801);//ָ��ʹ�õ�Sql��id
			mySql917144801.addSubPara(tContNo);//ָ������Ĳ���
			strSql=mySql917144801.getString();
    		
//    		strSql = "select dutycode,payplantype,paystartdate,payenddate,suppriskscore,SecInsuAddPoint,AddFeeDirect,prem from LCPrem where 1=1 "
//		 					 + " and ContNo ='"+tContNo+"'"
//							 + " and payplancode like '000000%%'";
								// + " and state = '1'";
				var tAddFeeFlag = easyExecSql(strSql);
      	if(tAddFeeFlag!=null){
      	
      	      		//�ж��Ƿ���й��ٱ��ʱ�
      						
      						var sqlid917144926="DSHomeContSql917144926";
							var mySql917144926=new SqlClass();
							mySql917144926.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
							mySql917144926.setSqlId(sqlid917144926);//ָ��ʹ�õ�Sql��id
							mySql917144926.addSubPara(MissionID);//ָ������Ĳ���
							mySql917144926.addSubPara(MissionID);//ָ������Ĳ���
							strSql=mySql917144926.getString();
      						
//      						strSql = //"select * from lbmission a where a.missionid='"+MissionID+"' and a.activityid='0000001140' and submissionid='"+SubMissionID+"' and not exists(select 'X' from lbmission a where a.missionid='"+MissionID+"' and a.activityid='0000001140' and submissionid='"+SubMissionID+"')"; 
//      		        			 " select *"+
//      		         			 " from lbmission a"+
//      		         			 " where a.missionid = '"+MissionID+"'"+
//      		               " and a.activityid = '0000001140'"+
//      		               " and not exists (select 'X'"+
//      		               " from lwmission a"+
//      		               " where a.missionid = '"+MissionID+"'"+
//      		               " and a.activityid = '0000001140')";
      		 				var hasReinsured=easyExecSql(strSql);
      						if (hasReinsured!=null)
      						{
      							document.all('AddFeeFlag').value = "N";//�ѽ��й��ʱ�������Ҫ�ٴγʱ�
      						}
      						else
      						{
										document.all('AddFeeFlag').value = "Y";
										//fm.ReDistribute.disabled="";	//��Ҫ�ٷ�
									}
      		}
      		else
      		{
      			document.all('AddFeeFlag').value = "N";
      		}
			}
    if(fm.uwUpReport.value==0){
    	if(fm.FBFlag.value=='FB')
      	{
      		//�ж��Ƿ���й��ٱ��ʱ�
      		
      		var sqlid917145058="DSHomeContSql917145058";
			var mySql917145058=new SqlClass();
			mySql917145058.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
			mySql917145058.setSqlId(sqlid917145058);//ָ��ʹ�õ�Sql��id
			mySql917145058.addSubPara(MissionID);//ָ������Ĳ���
			mySql917145058.addSubPara(MissionID);//ָ������Ĳ���
			strSql=mySql917145058.getString();
      		
//      		strSql = //"select * from lbmission a where a.missionid='"+MissionID+"' and a.activityid='0000001140' and submissionid='"+SubMissionID+"' and not exists(select 'X' from lbmission a where a.missionid='"+MissionID+"' and a.activityid='0000001140' and submissionid='"+SubMissionID+"')"; 
//      		         " select *"+
//      		         " from lbmission a"+
//      		         " where a.missionid = '"+MissionID+"'"+
//      		         " and a.activityid = '0000001140'"+
//      		         " and not exists (select 'X'"+
//      		         " from lwmission a"+
//      		         " where a.missionid = '"+MissionID+"'"+
//      		         " and a.activityid = '0000001140')";
      		var hasReinsured=easyExecSql(strSql);
      		//������й��ٱ��ʱ��������ж��ٱ����ձ���
      		if(hasReinsured==null)
      		{      		
      				showInfo.close();
      				alert("��Ҫ�����ٱ��ʱ�����������к˱�ȷ�ϣ�");
      				return;	
      		}
      	}

      }//end of if(hasReinsured!=null)
      
  } // end of if(fm.uwUpReport.value==0)
    
    //var tSelNo = PolAddGrid.getSelNo();
    //document.all('PrtNo').value = PolAddGrid.getRowColData(tSelNo - 1,3);
    //document.all('PolNo').value = PolAddGrid.getRowColData(tSelNo - 1,1);

    var tUWState = fm.uwState.value;                  //�˱�����
    //var tUWDelay = fm.UWDelay.value;
    var tUWIdea = fm.UWIdea.value;
    var tUpReport = fm.uwUpReport.value                //�ϱ���־
    var tUWPopedom = fm.uwPopedom.value;              //�˱�ʦ����
    //var tUWPer = fm.uwPer.value;                      //�˱�ʦ


    if(tUWState == "")
     {
     	showInfo.close();
        alert("����¼��б��˱�����!");
    	return ;
    }

    if(tUpReport == "")
     {
     	showInfo.close();
        alert("����ѡ��˱�����!");
    	return ;
    }



    if(tUWState =="a" && fm.UWWithDReason.value == "����" && (tUWIdea ==null || tUWIdea == ""))
     {
      showInfo.close();
      alert("����¼��б��˱����!");
      return ;
    }

    k++;

    var sqlid917145226="DSHomeContSql917145226";
	var mySql917145226=new SqlClass();
	mySql917145226.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
	mySql917145226.setSqlId(sqlid917145226);//ָ��ʹ�õ�Sql��id
	mySql917145226.addSubPara(fm.MissionID.value);//ָ������Ĳ���
	var strsql=mySql917145226.getString();
    
//    var strsql = "select submissionid from lwmission where "+k+"="+k
//                +" and activityid = '0000001110'"
//    						+" and missionid = '"+fm.MissionID.value+"'"
//    						;

    turnConfirmPage.strQueryResult = easyQueryVer3(strsql, 1, 0, 1);
    if (!turnConfirmPage.strQueryResult) {
        showInfo.close();
    	alert("δ�鵽������SubMissionID");
    	fm.SubConfirmMissionID.value = "";
    	return;
    }
    turnConfirmPage.arrDataCacheSet = decodeEasyQueryResult(turnConfirmPage.strQueryResult);
    fm.SubConfirmMissionID.value = turnConfirmPage.arrDataCacheSet[0][0];
    
    
    //tongmeng 2009-02-06 Add
    //7״̬��������
    if(ContUWStatus!=null&&ContUWStatus!="7")
    {
    		//tongmeng 2007-11-28 modify
				//�����ж��Ƿ���������޸ĸڵ������δ������
				var sqlid917145429="DSHomeContSql917145429";
				var mySql917145429=new SqlClass();
				mySql917145429.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
				mySql917145429.setSqlId(sqlid917145429);//ָ��ʹ�õ�Sql��id
				mySql917145429.addSubPara(fm.MissionID.value);//ָ������Ĳ���
				mySql917145429.addSubPara(fm.SubMissionID.value);//ָ������Ĳ���
				var tSQL=mySql917145429.getString();
				
//				var tSQL = "select * from lwmission where 1=1 "
//		         		 + " and activityid in ('0000001002') "
//		        		 + " and missionid = '"+fm.MissionID.value+"'"
//            		 + " and submissionid = '"+fm.SubMissionID.value+"'";
    				turnConfirmPage.strQueryResult = easyQueryVer3(tSQL, 1, 0, 1);         
    				if (turnConfirmPage.strQueryResult) 
    				{
    					//if(tUWState =='9'||tUWState =='4' ) 
    					{
    	  				showInfo.close();
    	  				alert("�������δ�޸����,�������º˱�����");   
    	  				return ;
    					} 	
    				}
			}
    
    //ln 2008-10-16 add
		//�����ϱ�ʱ�Ƿ�¼���˺˱���������
 		if(tUpReport == "1")
		{
	   	if(fm.UWUpperReasonCode.value == "")
	   	{
	      showInfo.close();
	      alert("��ѡ���ϱ�ԭ��");
	      return;
	   	}
	   	if(fm.TUWPopedomCode.value == "")
	   	{
	      showInfo.close();
	      alert("��ѡ���ϱ��˱�ʦ��");
	      return;
	   	}
	   
	   	var sqlid917145558="DSHomeContSql917145558";
		var mySql917145558=new SqlClass();
		mySql917145558.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
		mySql917145558.setSqlId(sqlid917145558);//ָ��ʹ�õ�Sql��id
		mySql917145558.addSubPara(ContNo);//ָ������Ĳ���
		mySql917145558.addSubPara(operator);//ָ������Ĳ���
		var tSQL=mySql917145558.getString();

	   	
//	   	var tSQL = "select * from -lcuwreport where 1=1 "
//		        	 + " and otherno = '" +ContNo+"'"
//		       	   + " and uwoperator = '"+operator+"'";
	    	turnConfirmPage.strQueryResult = easyQueryVer3(tSQL, 1, 0, 1);         
	    	if (!turnConfirmPage.strQueryResult) 
	    	{
	    	  showInfo.close();
	    	  alert("�˱������������ݲ���Ϊ��");   
	    	  return ;	
	    	}
		} 
	
	//ln 2008-10-16 add
		//�����¾ܱ������ڡ��α������ʱ�˱��������治��Ϊ��
	if(tUpReport == "0" && (tUWState == "4"||tUWState == "1"||tUWState == "2"))
	{	   
	   var sqlid917145700="DSHomeContSql917145700";
		var mySql917145700=new SqlClass();
		mySql917145700.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
		mySql917145700.setSqlId(sqlid917145700);//ָ��ʹ�õ�Sql��id
		mySql917145700.addSubPara(ContNo);//ָ������Ĳ���
		mySql917145700.addSubPara(operator);//ָ������Ĳ���
		var tSQL=mySql917145700.getString();
	   
//	   var tSQL = "select * from lcuwreport where 1=1 "
//		         + " and otherno = '" +ContNo+"'"
//		         + " and uwoperator = '"+operator+"'";
	    turnConfirmPage.strQueryResult = easyQueryVer3(tSQL, 1, 0, 1);         
	    if (!turnConfirmPage.strQueryResult) 
	    {
	    	  showInfo.close();
	    	  alert("�˱������������ݲ���Ϊ��");   
	    	  return ;	
	    }
	} 
	
	//ln 2009-9-25 add
		//�ۼƷ��ձ������100��Ԫ�ĸ߶�������б��ĺ˱����ۣ�4/9��ʱ��ϵͳ�жϲ����Ƿ����ʣ�����Ҫ������ʾ
	if(tUpReport == "0" && (tUWState == "4"||tUWState == "9"))
	{	   
		var arr1=0, tSQL="";
	  if(_DBT==_DBO){
		  var sqlid917145844="DSHomeContSql917145844";
		  var mySql917145844=new SqlClass();
		  mySql917145844.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
		  mySql917145844.setSqlId(sqlid917145844);//ָ��ʹ�õ�Sql��id
		  mySql917145844.addSubPara(ContNo);//ָ������Ĳ���
		  tSQL=mySql917145844.getString();
		  arr1=easyExecSql(tSQL);
		  //alert(arr1);
		  if(arr1 !=null){
		  arr1 = arr1[0][0];
		  }
		  //alert(arr1);
	  }else if(_DBT==_DBM){
		  var sqlid9171458441= "DSHomeContSql9171458441",
			  sqlid9171458442= "DSHomeContSql9171458442", //�洢����1
			  sqlid9171458443= "DSHomeContSql9171458443", //�洢����2
			  sqlid9171458444= "DSHomeContSql9171458444"; //�洢����3
		  var mySql9171458441=new SqlClass();
		  mySql9171458441.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
		  mySql9171458441.setSqlId(sqlid9171458441);//ָ��ʹ�õ�Sql��id
		  mySql9171458441.addSubPara(ContNo);//ָ������Ĳ���
		  tSQL=mySql9171458441.getString();
		  arr1=easyExecSql(tSQL);
		  if(arr1){
			  var rs_call_val1 = 0,rs_call_val2 = 0,rs_call_val3 = 0;
			  rs_call_val1 = mysql_call_result(sqlid9171458442,arr1);
			  rs_call_val2 = mysql_call_result(sqlid9171458443,arr1);
			  rs_call_val3 = mysql_call_result(sqlid9171458444,arr1);
			 arr1 = rs_call_val1+rs_call_val2+rs_call_val3;  //ע��������Ҫ�����Ƿ񷵻ص���^1|reg|0 ���ָ�ʽ��������������
		  }
	  }
	    
	    var sqlid917145954="DSHomeContSql917145954";
		var mySql917145954=new SqlClass();
		mySql917145954.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
		mySql917145954.setSqlId(sqlid917145954);//ָ��ʹ�õ�Sql��id
		mySql917145954.addSubPara(ContNo);//ָ������Ĳ���
		mySql917145954.addSubPara(ContNo);//ָ������Ĳ���
		tSQL=mySql917145954.getString();
	    var arr2=easyExecSql(tSQL);
//		if(arr1!=null && (arr1[0][0]-1000000)>0)
		if(arr1!=null && (Number(arr1)-1000000)>0)
		{
			if(arr2!=null &&arr2[0][0]>0)
			{
				if(!confirm("��Ͷ�����ɷѽ��㣬�Ƿ������"))
				{
					showInfo.close();  
		    	    return ;
				}
			}	    	  	
	    }
	} 
	
	 //ln 2008-10-17 add
		//У���Ƿ�¼���˽���ԭ��
	if(tUWState == "1" || tUWState == "2" || tUWState == "a")
	{
	   if((tUWState == "1" && fm.UWRefuReasonCode.value == "")
	         ||(tUWState == "2" && fm.UWDelayReasonCode.value == ""))
	   {
	      showInfo.close();
	      alert("��ѡ�����ھܱ�ԭ��");
	      return;
	   }
	   if(tUWState == "2" && fm.UWDelay.value == "")
	   {
	      showInfo.close();
	      alert("��ѡ���ӳ�ʱ�䣡");
	      return;
	   }  
	   if(tUWState == "a" && fm.UWWithDReasonCode.value == "")
	   {
	      showInfo.close();
	      alert("��ѡ�񳷵�ԭ��");
	      return;
	   }  	   
	}	
    
    //ln 2008-10-25 add ���Ӷ������ظ�δ���������
    var sqlid917150126="DSHomeContSql917150126";
	var mySql917150126=new SqlClass();
	mySql917150126.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
	mySql917150126.setSqlId(sqlid917150126);//ָ��ʹ�õ�Sql��id
	mySql917150126.addSubPara(ContNo);//ָ������Ĳ���
	mySql917150126.addSubPara(ContNo);//ָ������Ĳ���
	var tSql=mySql917150126.getString();
	    
//    var tSql = " select 1 from LCRReport l where 1=1 and ContNo = '"+ContNo+"'"
//             + " and ReplyOperator is not null and not exists(select 1 from LCScoreRReport where ContNo = '"+ContNo+"')";
    turnConfirmPage.strQueryResult = easyQueryVer3(tSql, 1, 0, 1);
   
    if (turnConfirmPage.strQueryResult) 
    {
    	  showInfo.close();
    	  alert("������δ����, �������º˱����ۣ�");   
    	  return ;	
    }
    //tongmeng 2009-02-06 add
    //7״̬��������
    if(ContUWStatus!=null&&ContUWStatus!='7')
    {
    	//�����µ�֪ͨ�������
    	var sqlid917150312="DSHomeContSql917150312";
		var mySql917150312=new SqlClass();
		mySql917150312.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
		mySql917150312.setSqlId(sqlid917150312);//ָ��ʹ�õ�Sql��id
		mySql917150312.addSubPara(fm.MissionID.value);//ָ������Ĳ���
		mySql917150312.addSubPara(fm.SubMissionID.value);//ָ������Ĳ���
		var tSql=mySql917150312.getString();
    	
    	
//    	var tSql = " select * from lwmission where  1=1 "
//      	       + " and activityid in ('0000001106','0000001107','0000001108','0000001111','0000001112','0000001113', "
//        	     + " '0000001130','0000001280','0000001290','0000001300','0000001140','0000001302','0000001107', "
//          	   + " '0000001301','0000001303','0000001020') "
//            	 + " and missionid = '"+fm.MissionID.value+"'"
//               + " and submissionid = '"+fm.SubMissionID.value+"'";
    	turnConfirmPage.strQueryResult = easyQueryVer3(tSql, 1, 0, 1);
   
    	if (turnConfirmPage.strQueryResult) 
    	{
    	//if(tUWState =='9'||tUWState =='4' ) 
    		{
    	  	showInfo.close();
    	  	alert("����֪ͨ��û�лظ�, �������º˱�����");   
    	  	return ;
    		} 	
    	}
    }
		//else 
    // add by lzf 2013-04-07 
    // У��У���Ƿ�����¼�룬��δ���ŵ�����������֪ͨ��
    //�˱����У���Ƿ��С��ٱ��ʱ�����δ���
    if(flag==0){
    	//�����¼��
    	var sql1 = "select count(1) from lcissuepol where contno = '"+ContNo+"' and (state is null or state = '')   AND needprint='Y'";
    	var count1 = 0;
    	count1 = easyExecSql(sql1);
    	//alert("count1=="+count1);
    	if(count1 != null){
    		if(count1>0){
    			showInfo.close();
    			alert("������¼�뵫δ�·�����������������º˱�����");
    			return;
    		}
    	}
    	//���֪ͨ��
    	var sql2 = "select count(1) from LCPENotice where contno = '"+ContNo+"' and (PrintFlag is null or PrintFlag = '') ";
    	count1 = easyExecSql(sql2);
    	if(count1 != null){
    		if(count1>0){
    			showInfo.close();
    			alert("������¼�뵫δ�·������֪ͨ�飬�������º˱�����");
    			return;
    		}
    	}
    	//����֪ͨ��
    	var sql3 = "select count(1) from LCRReport where contno = '"+ContNo+"' and (replyflag is null or replyflag = '') ";
    	count1 = easyExecSql(sql3);
    	if(count1 != null){
    		if(count1>0){
    			showInfo.close();
    			alert("������¼�뵫δ�·�������֪ͨ�飬�������º˱�����");
    			return;
    		}
    	}
    	//�ٱ��ʱ�δ�������
    	var sql4 = "select count(1) from LCReinsureReport where contno = '"+ContNo+"' and (ReinsuredResult is null or ReinsuredResult = '') ";
    	count1 = easyExecSql(sql4);
    	if(count1 != null){
    		if(count1>0){
    			showInfo.close();
    			alert("�����ٱ��ʱ�δ������ϣ��������º˱�����");
    			return;
    		}
    	}
    	//�˱����ʱУ���Ƿ�����¼�롢δ�·��ĺ˱�֪ͨ�飬�����δ�·��˱�֪ͨ�飬�˱�������ѡ���ϱ��򷵻��¼�����ֻ��ѡ�񡰺˱���ϡ�
    	var uwUpReport = fm.uwUpReport.value;
    	
    	if(uwUpReport=="1" || uwUpReport=="4"){
    		var sql5 = "select count(1) from lccuwmaster where contno = '"+ContNo+"' and uwstate ='4'";
        	count1 = easyExecSql(sql5);
        	if(count1 != null){
        		if(count1>0){
        			showInfo.close();
        			alert("���·��ĺ˱�֪ͨ�飬�˱�������ѡ���ϱ��򷵻��¼�����ֻ��ѡ�񡰺˱���ϡ�");
        			return;
        		}
        	}
    	}
    	
    }    
	//end by lzf 
    {   document.all('UWAuthority').value = UWAuthority;       
		lockScreen('lkscreen');  
    	fm.action = "./UWManuNormChk.jsp";
    	document.getElementById("fm").submit(); //�ύ		
		}
  
    //alert("submit");
   // return;

}

//���ô洢����  param1 = mysqlId  param2=contno
function mysql_call_result(param1,param2){
	 var sqlObj="",tSQL="",result=0;
	  sqlObj=new SqlClass();//��ջ���
	  sqlObj.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
	  sqlObj.setSqlId(param1);//ָ��ʹ�õ�Sql��id
	  sqlObj.addSubPara(param2);//ָ������Ĳ���
	  if(sqlObj){
		  tSQL=sqlObj.getString();
	  }
	 result=easyExecSql(tSQL);
	 return Number(result);  //������Ҫ��ʽ������
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
  if (FlagStr == "Fail" )
  {
    //var showInfo = showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
    //showInfo.close();
    alert(content);
    //parent.close();
  }
  else
  {
	//var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
  	//var showInfo = showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
  	//showInfo.close();
  	//alert(content);
  	//parent.close();

    //ִ����һ������
  }
}
//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit3( FlagStr, content )
{
	showInfo.close();
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;

  if (FlagStr == "Fail" )
  {
    alert(content);
  }
  else
  {
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    //ִ����һ������
    	InitClick();
  }

}
//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit2( FlagStr, content )
{
	unlockScreen('lkscreen');  
 var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
 
  if (FlagStr == "Fail" )
  {
    showInfo.close();
	alert(content);
     //parent.close();
  }
  else if(FlagStr == "FB" )
  {
    showInfo.close();
    //fm.ReDistribute.disabled="";	//��Ҫ�ٷ�
	alert(content);  	
  }
  else
  {
  	showInfo.close();
	alert(content);
	//easyQueryClick();
  	//parent.close();
    InitClick();
  }

}
//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterApply( FlagStr, content )
{
  if (FlagStr == "Fail" )
  {
    alert(content);
    	// ��ʼ�����
	HideChangeResult();
	initPolGrid();
	divLCPol1.style.display= "";
	divLCPol2.style.display= "none";
	divMain.style.display = "none";

  }
  else
  {
  		makeWorkFlow();
  }
}

function afterAddFeeApply( FlagStr, content )
{
  if (FlagStr == "Fail" )
  {
    alert(content);
    	// ��ʼ�����
 }
  else
  {
  	  var cPolNo=fm.ProposalNo.value;
	  var cMissionID =fm.MissionID.value;
	  var cSubMissionID =fm.SubMissionID.value;
	  var tSelNo = PolAddGrid.getSelNo()-1;
	  var cNo = PolAddGrid.getRowColData(tSelNo,1);
	  var cPrtNo = PolAddGrid.getRowColData(tSelNo,3);
	  var cType = PolAddGrid.getRowColData(tSelNo,7);

	 if (cPrtNo != ""&& cNo !="" && cMissionID != "" )
	  {
	  	window.open("./UWManuAddMain.jsp?PrtNo="+cPrtNo+"&PolNo="+cPolNo+"&No="+cNo+"&MissionID="+cMissionID+"&SubMissionID="+cSubMissionID+"&Type="+cType,"window1",sFeatures);
	  	showInfo.close();
	  }
	  else
	  {
	  	showInfo.close();
	  	alert("����ѡ�񱣵�!");
	  }
  }
}

//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
		parent.fraMain.rows = "0,0,50,82,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
}

/*********************************************************************
 *  ѡ��˱����Ķ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterCodeSelect( cCodeName, Field ) {

	   // alert("uwstate" + cCodeName + Field.value);
		if( cCodeName == "uwstate" ) {
			DoUWStateCodeSelect(Field.value);//loadFlag��ҳ���ʼ����ʱ������
		}
		else if(cCodeName == "contuwstate")
		{
			  var ttsql = "select riskcode,uwflag from lcpol where prtno='"+document.all("PrtNo").value+"' order by mainpolno,polno ";
			  var res = easyExecSql(ttsql);
	          if(res!=null) {
	         	 var flag1=true;
	      	     var flag2=true;
  				 for(i=0;i<res.length;i++) { 
  				   if(res[i][1] != '9'){
  					 	 flag1=false;
  				   }
  				   if(res[i][1] != '1'){
  				         flag2=false;
  				   }					 	
  				  }
  				  if(flag1==true && Field.value!='9'){  //������ȫ�Ǳ�׼�壬������ֻ���Ǳ�׼�壻
  				         document.all("uwState").value="";
  				         document.all("uwStatename").value="";
  				 	     alert("�����˱�����ֻ��Ϊ��׼");
  						 return false;
  				  }else if(flag2==true && (Field.value =="9"||Field.value =="4")){ //������ȫ���ܱ���������ֻ���Ǿܱ������ڡ��򳷵�
  				 	     document.all("uwState").value="";
  				         document.all("uwStatename").value="";
  				 	     alert("�����˱�����ֻ��Ϊ�ܱ������ڻ򳷵�");
  						 return false;
  				  }else if(flag1==false && Field.value=='9'){
  				         document.all("uwState").value="";
  				         document.all("uwStatename").value="";
  				         alert("���ڷǱ�׼�б������֣������˱����۲���Ϊ��׼");			        
  						 return false;
  				  } 
  			    }
  			  
				if(Field.value=='2')//����
				{
					UWDelaydiv.style.display = '';
					UWDeRefdiv.style.display = '';
					UWWithdrawdiv.style.display = 'none';
					UWRefdiv.style.display = 'none';
				}				
				else if(Field.value=='a')//����
				{
					UWWithdrawdiv.style.display = '';
					UWDelaydiv.style.display = 'none';
					UWRefdiv.style.display = 'none';
					UWDeRefdiv.style.display = 'none';
				}
				else if(Field.value=='1')//�ܱ�
				{
					UWRefdiv.style.display = '';
					UWDeRefdiv.style.display = '';
					UWDelaydiv.style.display = 'none';
					UWWithdrawdiv.style.display = 'none';
				}
				else
				{
					UWDelaydiv.style.display = 'none';
					UWWithdrawdiv.style.display = 'none';
					UWRefdiv.style.display = 'none';
					UWDeRefdiv.style.display = 'none';
				}			  
		}
		else if(cCodeName =="uwUpReport")
		{	
			if(Field.value=='1')
				{
					UWUpperUwer.style.display = '';
				}
			else
				{
					UWUpperUwer.style.display = 'none';
				}
		}
}

/*********************************************************************
 *  ���ݲ�ͬ�ĺ˱�����,����ͬ������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function DoUWStateCodeSelect(cSelectCode) {
	if(trim(cSelectCode) == '6')//�ϱ��˱�
	{
		 uwgrade = document.all('UWGrade').value;
         appgrade= document.all('AppGrade').value;
         if(uwgrade==null||uwgrade<appgrade)
         {
         	uwpopedomgrade = appgrade ;
         }
        else
         {
        	uwpopedomgrade = uwgrade ;
         }
        //alert(uwpopedomgrade);
        codeSql="#1# and Comcode like #"+ comcode+"%%#"+" and popedom > #"+uwpopedomgrade+"#"	;
        // alert(codeSql);
	}
	else
	codeSql="";
}


//�ж��Ƿ���Ҫ��ʾ��ʾ��Ϣ
function chkIsSugInfo()
{
	//�ж��Ƿ���Ҫ��ʾ��ָ����Ч�ղ�������ʾ
	var strPrompt="";
	var strSql = "";
	var tContNo = fm.ContNo.value;
	var sqlid917150535="DSHomeContSql917150535";
var mySql917150535=new SqlClass();
mySql917150535.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917150535.setSqlId(sqlid917150535);//ָ��ʹ�õ�Sql��id
mySql917150535.addSubPara(tContNo);//ָ������Ĳ���
mySql917150535.addSubPara(tContNo);//ָ������Ĳ���
mySql917150535.addSubPara(tContNo);//ָ������Ĳ���
mySql917150535.addSubPara(tContNo);//ָ������Ĳ���
strSql=mySql917150535.getString();
	
//	strSql = "select a.contno,a.uwno,a.uwerror,a.modifydate from lcuwerror a where 1=1 "
//			 + " and a.polno in (select distinct polno from lcpol where contno ='" +tContNo+ "')"
//			 + " and (a.uwno = (select max(b.uwno) from lcuwerror b where b.contno = '" +tContNo + "' and b.polno = a.polno))"
//			 + " and a.uwrulecode in (select calcode from lmuw where passflag='3')"
//			 + " and exists (select polno from lcpol where polno=mainpolno and specifyvalidate<>'Y' and polno=a.polno)"
//			 + " union "
//			 + "select c.contno,c.uwno,c.uwerror,c.modifydate from lccuwerror c where 1=1"
//			 + " and c.contno ='" + tContNo + "'"
//			 + " and (c.uwno = (select max(d.uwno) from lccuwerror d where d.contno = '" + tContNo + "'))"
//			 + " and c.uwrulecode in (select calcode from lmuw where passflag='3')"
//			 + " and exists (select polno from lcpol where polno=mainpolno and specifyvalidate<>'Y' and contno=c.contno)";
	//��ѯ�Ƿ���Ҫ��ʾ��ʾ��Ϣ.
	var arrUWErr=easyExecSql(strSql); 
	if(arrUWErr!=null && arrUWErr.length>0)
	{		
		 try{showInfo.close();}catch(ex){};
		 strPrompt="�õ����ܻ���Ҫ�����²���:"; 
		 for(var i=1; i<=arrUWErr.length; i++)
		 {
		 	  strPrompt=strPrompt+"\n"+i+": "+arrUWErr[i-1][2];
		 }
		 strPrompt=strPrompt+"\n\n"+"���践�ؽ��в�����������[ȷ��]��ť����������[ȡ��]��ť��";
		 if(confirm(strPrompt)==true) {return false;}
		 else {return true;}
	}
  else
	{
		return true;
	}	
}


//����Ͷ����Ϣ
function showApp(cindex)
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  var cContNo=fm.ContNo.value;
  var cAppntNo = fm.AppntNo.value;

 if (cindex == 1)
	  window.open("../uw/UWAppMain.jsp?ContNo="+cContNo+"&CustomerNo="+cAppntNo+"&type=1","",sFeatures);
 else  if (cindex == 2)
 	 window.open("../uw/UWAppMain.jsp?ContNo="+cContNo+"&CustomerNo="+document.all('InsuredNo').value+"&type=2","",sFeatures);
 else
	  window.open("../uw/UWAppFamilyMain.jsp?ContNo="+cContNo,"",sFeatures);

showInfo.close();

}

//�����˱���¼
function showOldUWSub()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  cProposalNo=fm.ProposalNo.value;
  //showModalDialog("./UWSubMain.jsp?ProposalNo1="+cProposalNo,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
  if (cProposalNo != "")
  {
  	window.open("./UWSubMain.jsp?ProposalNo1="+cProposalNo,"window1",sFeatures);
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("����ѡ�񱣵�!");
  }
}

//��ǰ�˱���¼
function showNewUWSub()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  cContNo=fm.ProposalContNo.value;

  	//window.open("./PUWErrMain.jsp?ProposalNo1="+cProposalNo,"window1",sFeatures);
  	window.open("./UWErrMain.jsp?ContNo="+cContNo,"window1",sFeatures);

  	showInfo.close();

}


// ��Ͷ�������������ѯ
function ClaimGetQuery2()
{
	var arrReturn = new Array();
	var tSel = PolAddGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    var cPolNo = PolAddGrid.getRowColData(tSel - 1,2);
		if (cPolNo == "")
		    return;
		  window.open("../sys/ClaimGetQueryMain.jsp?PolNo=" + cPolNo,"",sFeatures);
	}
}
// ���������ѯ
function ClaimGetQuery()
{
	var arrReturn = new Array();
	var tSel = PolAddGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    var cInsuredNo = fm.InsuredNo.value;
		if (cInsuredNo == "")
		    return;
		  window.open("../sys/AllClaimGetQueryMain.jsp?InsuredNo=" + cInsuredNo,"",sFeatures);
	}
}

//������ϸ��Ϣ
function showPolDetail()
{
  //var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  //var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  ////showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  //
  //cContNo=fm.ContNo.value;
  //if (cContNo != "")
  //{
  //	var tSelNo = PolAddGrid.getSelNo()-1;
  //	showPolDetailFlag[tSelNo] = 1 ;
  //	mSwitch.deleteVar( "ContNo" );
  //	mSwitch.addVar( "ContNo", "", cContNo );
  //	mSwitch.updateVar("ContNo", "", cContNo);
  //	window.open("../sys/AllProQueryMain.jsp?LoadFlag=3","window1",sFeatures);
  //
  //}
  var cContNo = fm.ProposalContNo.value;
  var cPrtNo = fm.PrtNo.value;
//    strSql="select case lmriskapp.riskprop"
//        +" when 'I' then"
//        +" '1'"
//        +" when 'G' then"
//        +" '2'"
//        +" when 'Y' then"
//        +" '3'"
//        +" end"
//        +" from lmriskapp"
//        +" where riskcode in (select riskcode"
//        +" from lcpol"
//        +" where polno = mainpolno"
//        +" and prtno = '"+cPrtNo+"')"

	//ȥ��ԭ�����ж�Ͷ�������͵��߼����޸�Ϊ��ӡˢ�����ж�Ͷ��������
	var BankFlag = "";
    var tSplitPrtNo = cPrtNo.substring(0,4);
    //alert("tSplitPrtNo=="+tSplitPrtNo); 
    //8635��8625��8615������Ͷ�������棬����Ķ��߸��ս���
    if(tSplitPrtNo=="8635"||tSplitPrtNo=="8625"||tSplitPrtNo=="8615"){
    	BankFlag="3";
    }else{
    	BankFlag="1";
    }
//  var BankFlag = document.all('SaleChnl').value;
//  arrResult = easyExecSql(strSql);
//  var BankFlag = arrResult[0][0];
  //alert("BankFlag="+BankFlag);
  
  var sqlid917150833="DSHomeContSql917150833";
var mySql917150833=new SqlClass();
mySql917150833.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917150833.setSqlId(sqlid917150833);//ָ��ʹ�õ�Sql��id
mySql917150833.addSubPara(cPrtNo);//ָ������Ĳ���
var strSql2=mySql917150833.getString();

  
//  var strSql2="select missionprop5 from lbmission where activityid='0000001099' and missionprop1='"+cPrtNo+"'";
	var crrResult = easyExecSql(strSql2);
	var SubType="";
	if(crrResult!=null)
	{
		SubType = crrResult[0][0];
	}

  window.open("../app/ProposalEasyScan.jsp?LoadFlag=6&prtNo="+cPrtNo+"&ContNo="+cContNo+"&BankFlag="+BankFlag+"&SubType="+SubType+"&QueryType=3", "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);

}

//ɨ�����ѯ
function ScanQuery()
{

	  var ContNo = fm.ContNo.value;
		if (ContNo == "")
		    return;
		  window.open("./ImageQueryMain.jsp?ContNo="+ContNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);

}

//��Ͷ��ҪԼ����������(��ӡ)��ɨ�����ѯ
function ScanQuery1()
{
	  var ContNo = fm.ContNo.value;
		if (ContNo == "")
		    return;
		  window.open("./ImageQuery1Main.jsp?ContNo="+ContNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);
}

//������ϲ�ѯ
function showHealthQ()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();


  var cContNo = fm.ContNo.value;
  var cMissionID = fm.MissionID.value;
  var cSubMissionID = fm.SubMissionID.value;
  var cPrtNo = fm.PrtNo.value;


  if (cContNo!= ""  )
  {
  	//var tSelNo = PolAddGrid.getSelNo()-1;
  	//var tNo = PolAddGrid.getRowColData(tSelNo,1);
  	//showHealthFlag[tSelNo] = 1 ;
  	window.open("./UWManuHealthQMain.jsp?ContNo="+cContNo+"&MissionID="+cMissionID+"&SubMissionID="+cSubMissionID+"&PrtNo="+cPrtNo,"window1",sFeatures);
  	showInfo.close();

  }
  else
  {
  	showInfo.close();
  	alert("����ѡ�񱣵�!");
  }
}

// ��Ŀ��ϸ��ѯ
function ItemQuery()
{
	var arrReturn = new Array();
	var tSel = PolAddGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ����ϸ��ѯ��ť��" );
	else
	{
	    var cNo = PolAddGrid.getRowColData(tSel - 1,1);
	    var cSumGetMoney = 	PolAddGrid.getRowColData(tSel - 1,8);

		if (cNo == "")
		   {
		   	alert( "����ѡ��һ�������˳б���Ŀ�ļ�¼���ٵ���б���Ŀ��ѯ��ť��" );
		   	return;
		   }
		window.open("../sys/AllPBqItemQueryMain.jsp?No=" + cNo + "&SumGetMoney=" + cSumGetMoney,"",sFeatures);

	}
}

//�鿴������Ϣ
function Prt()
{
	var tSel = PolAddGrid.getSelNo();
    if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ�������鿴��ť��" );
	else
		{
			var cNo = PolAddGrid.getRowColData(tSel - 1,1);
			if (cNo == "")
		   {
			   	alert( "����ѡ��һ�������˳б���Ŀ�ļ�¼���ٵ���б���ϸ��ѯ��ť��" );
			   	return;
		   }
			document.all('No').value = PolAddGrid.getRowColData(tSel - 1,1);
			document.all('PolNo').value = PolAddGrid.getRowColData(tSel - 1,2);

			var taction = parent.fraInterface.fm.action;
			var ttarget = parent.fraInterface.fm.target;
			parent.fraInterface.fm.action = "../f1print/EndorsementF1P.jsp";
			parent.fraInterface.fm.target="f1print";
			document.getElementById("fm").submit();

			parent.fraInterface.fm.action = taction;
			parent.fraInterface.fm.target=ttarget;
			document.all('No').value = '';
			document.all('PolNo').value = '';
		}
}


//�������
function showHealth()
{
	//SYY 
	var sql = "select uwstate from lccuwmaster where contno = '"+ContNo+"' and uwstate in ('4','6')";
	var arr = easyExecSql(sql);
	if (arr) {
		 alert("�ú�ͬ��ǰ����"+arr[0][0]+"״̬,���������֪ͨ��¼��");   
	     return false;   
	}
	
	/*
  if(!checkUWState('6',MissionID,SubMissionID,'���֪ͨ��¼��'))
	{
	 	 return false;
	}
	//tongmeng 2009-02-06 add
	//4״̬������¼�����֪ͨ��
	if(!checkUWState('4',MissionID,SubMissionID,'���֪ͨ��¼��'))
	{
	 	 return false;
	}
	*/
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  var cContNo = fm.ContNo.value;
  var cMissionID = fm.MissionID.value;
  var cSubMissionID = fm.SubMissionID.value;
  var cPrtNo = fm.PrtNo.value;
 	
	
  if (cContNo != "")
  {
  	//var tSelNo = PolAddGrid.getSelNo()-1;
  	//var tNo = PolAddGrid.getRowColData(tSelNo,1);
  	//showHealthFlag[tSelNo] = 1 ;
  	window.open("./UWManuHealthMain.jsp?ContNo1="+cContNo+"&MissionID="+cMissionID+"&SubMissionID="+cSubMissionID+"&PrtNo="+cPrtNo,"window1",sFeatures);
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("����ѡ�񱣵�!");
  }
}

//��Լ�б�
function showSpec()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  var cContNo=fm.ContNo.value;
  var cMissionID =fm.MissionID.value;
  var cSubMissionID =fm.SubMissionID.value;
  var tSelNo = PolAddGrid.getSelNo()-1;
  tUWIdea = document.all('UWIdea').value;
  var cPrtNo = PolAddGrid.getRowColData(tSelNo,3);
  if (cContNo != ""&& cPrtNo !="" && cMissionID != "" )
  {
  	window.open("./UWManuSpecMain.jsp?ContNo="+cContNo+"&PrtNo="+cPrtNo+"&MissionID="+cMissionID+"&SubMissionID="+cSubMissionID,"window1",sFeatures);
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("����ѡ�񱣵�!");
  }
}

//�ӷѳб�
function showAdd()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  var cContNo=fm.ContNo.value;
  if (cContNo != "")
  {
	window.open("./UWManuAddMain.jsp?ContNo="+cContNo,"window1",sFeatures);
  }
  else
  {
  alert("����ѡ�񱣵�!");
  }
}

//������鱨��
function showRReport()
{	
	//SYY BEGIN
	var sql = "select uwstate from lccuwmaster where contno = '"+ContNo+"' and uwstate in ('4','6')";
	var arr = easyExecSql(sql);
	if (arr) {
		 alert("�ú�ͬ��ǰ����"+arr[0][0]+"״̬,�����������������¼��");   
	     return false;   
	}
	//SYY END
	/*
	if(!checkUWState('6',MissionID,SubMissionID,'�����������¼��'))
	{
	 	 return false;
	}
	//tongmeng 2009-02-06 add
	//4״̬������¼������֪ͨ��
	if(!checkUWState('4',MissionID,SubMissionID,'�����������¼��'))
	{
	 	 return false;
	}
	*/
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  var cContNo=fm.ContNo.value;

  var cMissionID =fm.MissionID.value;
  var cSubMissionID =fm.SubMissionID.value;
  var cPrtNo = fm.PrtNo.value;

  window.open("./UWManuRReportMain.jsp?ContNo="+cContNo+"&MissionID="+cMissionID+"&PrtNo="+cPrtNo+"&SubMissionID="+cSubMissionID+"&Flag="+pflag,"window2",sFeatures);
  showInfo.close();
}

//�˱�������
function showReport()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  var cContNo=fm.ContNo.value;
  tUWIdea = document.all('UWIdea').value;
  if (cContNo != "")
  {
  	window.open("../uw/UWManuReportMain.jsp?ContNo="+cContNo,"window2",sFeatures);
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("����ѡ�񱣵�!");
  }
}

//���˱�֪ͨ��
function SendNotice()
{
  cContNo = fm.ContNo.value;
  fm.uwState.value = "8";

  if (cContNo != "")
  {
	//manuchk();
	 var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
     var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
     //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	 var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	  var cMissionID =fm.MissionID.value;

	  var cPrtNo = fm.PrtNo.value;

	  fm.PrtNoHide.value = cPrtNo ;

	  var sqlid917151026="DSHomeContSql917151026";
var mySql917151026=new SqlClass();
mySql917151026.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917151026.setSqlId(sqlid917151026);//ָ��ʹ�õ�Sql��id
mySql917151026.addSubPara(cPrtNo);//ָ������Ĳ���
mySql917151026.addSubPara(cContNo);//ָ������Ĳ���
mySql917151026.addSubPara(cMissionID);//ָ������Ĳ���
strsql=mySql917151026.getString();
	  
//	  strsql = "select LWMission.SubMissionID from LWMission where 1=1"
//				 + " and LWMission.MissionProp1 = '" + cPrtNo +"'"
//				 + " and LWMission.MissionProp2 = '"+ cContNo + "'"
//				 + " and LWMission.ActivityID = '0000001105'"
//				 + " and LWMission.ProcessID = '0000000003'"
//				 + " and LWMission.MissionID = '" +cMissionID +"'";
	turnPage.strQueryResult = easyQueryVer3(strsql, 1, 1, 1);

    //�ж��Ƿ��ѯ�ɹ�
    if (!turnPage.strQueryResult)
    {
    	showInfo.close();
     	alert("���������µĺ˱�֪ͨ��,ԭ�������:1.�ѷ��˱�֪ͨ��,��δ��ӡ.2.δ¼��˱�֪ͨ������.");
        fm.SubNoticeMissionID.value = "";
        return ;
    }
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    fm.SubNoticeMissionID.value = turnPage.arrDataCacheSet[0][0];
    var tSubNoticeMissionID =   fm.SubNoticeMissionID.value ;
    if (cContNo != "" && cPrtNo != "" && cMissionID != "" && tSubNoticeMissionID != "" )
	  {
	  	fm.action = "./UWManuSendNoticeChk.jsp";
	    document.getElementById("fm").submit();
	  }
	  else
	  {
  	   showInfo.close();
  	   alert("����ѡ�񱣵�!");
    }
  }
  else
  {
  	alert("����ѡ�񱣵�!");
  }
}

function SendDanNotice()
{
	var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  cProposalNo=fm.ProposalContNo.value;
  cOtherNoType="02"; //������������
  cCode="06";        //��������

  if (cProposalNo != "")
  {
  	//showModalDialog("./UWSendPrintMain.jsp?ProposalNo1="+cProposalNo+"&OtherNoType="+cOtherNoType+"&Code="+cCode,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
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
  	showInfo.close();
  	alert("����ѡ�񱣵�!");
  }

}
function SendRanNotice()
{
	var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  cProposalNo=fm.ProposalContNo.value;
  cOtherNoType="02"; //������������
  cCode="00";        //��������

  if (cProposalNo != "")
  {
  	//showModalDialog("./UWSendPrintMain.jsp?ProposalNo1="+cProposalNo+"&OtherNoType="+cOtherNoType+"&Code="+cCode,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
	var url1="./UWSendPrintMain.jsp?ProposalNo1="+cProposalNo+"&OtherNoType="+cOtherNoType+"&Code="+cCode
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	var showInfo1 = window.open (url1,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo1.focus();
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("����ѡ�񱣵�!");
  }

}


function SendAddNotice()
{
	var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  cProposalNo=fm.ProposalContNo.value;
  cOtherNoType="02"; //������������
  cCode="83";        //��������

  if (cProposalNo != "")
  {
  	//showModalDialog("./UWSendPrintMain.jsp?ProposalNo1="+cProposalNo+"&OtherNoType="+cOtherNoType+"&Code="+cCode,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
	var url1="./UWSendPrintMain.jsp?ProposalNo1="+cProposalNo+"&OtherNoType="+cOtherNoType+"&Code="+cCode
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	var showInfo1 = window.open (url1,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo1.focus();
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("����ѡ�񱣵�!");
  }

}

function SendHealth()
{
	//
	}
//���׽�֪ͨ��
function SendFirstNotice()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();


  cProposalNo=fm.ProposalContNo.value;
  cOtherNoType="02"; //������������
  cCode="07";        //��������

  if (cProposalNo != "")
  {
  	lockScreen('lkscreen');  
  	//showModalDialog("./UWSendPrintMain.jsp?ProposalNo1="+cProposalNo+"&OtherNoType="+cOtherNoType+"&Code="+cCode,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
	var url1="./UWSendPrintMain.jsp?ProposalNo1="+cProposalNo+"&OtherNoType="+cOtherNoType+"&Code="+cCode
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	var showInfo1 = window.open (url1,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	//showInfo1.focus();
	showInfo.close();
	//showInfo1.close();
  	unlockScreen('lkscreen');  
  }	
  else
  {
  	showInfo.close();
  	alert("����ѡ�񱣵�!");
  }
  
}

//���߰�֪ͨ��
function SendPressNotice()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();


  cProposalNo=fm.ProposalNo.value;
  cOtherNoType="00"; //������������
  cCode="06";        //��������

  if (cProposalNo != "")
  {
  	//showModalDialog("./UWSendPrintMain.jsp?ProposalNo1="+cProposalNo+"&OtherNoType="+cOtherNoType+"&Code="+cCode,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
	var url1="./UWSendPrintMain.jsp?ProposalNo1="+cProposalNo+"&OtherNoType="+cOtherNoType+"&Code="+cCode
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	var showInfo1 = window.open (url1,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo1.focus();
  	showInfo.close();
  	 }
  else

  {
  	showInfo.close();
  	alert("����ѡ�񱣵�!");
  }
}


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

var withdrawFlag = false;
//���������ѯ,add by Minim
function withdrawQueryClick() {
  withdrawFlag = true;
  easyQueryClick();
}

/*********************************************************************
 *  ִ������Լ�˹��˱���EasyQuery
 *  ����:��ѯ��ʾ�����Ǻ�ͬ����.��ʾ����:��ͬδ�����˹��˱�����״̬Ϊ���˱�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function easyQueryClick()
{
	// ��ʼ�����

	HideChangeResult();
	initPolGrid();
	divLCPol1.style.display= "";
    divLCPol2.style.display= "none";
    divMain.style.display = "none";

	// ��дSQL���
	k++;
	var uwgradestatus = fm.UWGradeStatus.value;
	var mOperate = fm.Operator.value;
	var state = fm.State.value;       //��������״̬
	var strSQL = "";
	if (uwgradestatus == "1")//��������
	{
		var wherePartStr = getWherePart( 'LWMission.MissionProp1','QPrtNo' )
				 + getWherePart( 'LWMission.MissionProp9','QAppntName' )
				 + getWherePart( 'LWMission.MissionProp2','QProposalNo')
				 + getWherePart( 'LWMission.MissionProp10','QManageCom' );
		var sqlid917153901="DSHomeContSql917153901";
var mySql917153901=new SqlClass();
mySql917153901.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917153901.setSqlId(sqlid917153901);//ָ��ʹ�õ�Sql��id
mySql917153901.addSubPara(wherePartStr);//ָ������Ĳ���
mySql917153901.addSubPara(mOperate);//ָ������Ĳ���
mySql917153901.addSubPara(comcode+"%%");//ָ������Ĳ���
strSQL=mySql917153901.getString();
		
//		strSQL = "select LWMission.MissionProp1,LWMission.MissionProp2,LWMission.MissionProp7,LWMission.MissionProp5,LWMission.MissionProp8,LWMission.MissionID ,LWMission.SubMissionID from LWMission where "+k+"="+k
//				 //+ " and VERIFYOPERATEPOPEDOM(LCPol.Riskcode,LCPol.Managecom,'"+comcode+"','Pe')=1"
//				 + " and LWMission.ProcessID = '0000000003' " //�б��˱�������
//				 + " and LWMission.ActivityID = '0000001100' " //�б��˱��������еĴ��˹��˱�����ڵ�
//				 //+ getWherePart( 'LWMission.DefaultOperator','QOperator' )
//				 + getWherePart( 'LWMission.MissionProp1','QPrtNo' )
//				 + getWherePart( 'LWMission.MissionProp9','QAppntName' )
//				 + getWherePart( 'LWMission.MissionProp2','QProposalNo')
//				 + getWherePart( 'LWMission.MissionProp10','QManageCom' )
//				 + " and ((select UWPopedom from LDUser where usercode = '"+mOperate+"') = (select AppGrade from LccUWMaster where trim(ContNo) = LWMission.MissionProp2 ))"
//				 + " and LWMission.MissionProp10 like '" + comcode + "%%'";  //����Ȩ�޹�������
	}
	else
	  if (uwgradestatus == "2")//�¼�����
	  {
		
		var wherePartStr = getWherePart( 'LWMission.MissionProp1','QPrtNo' )
				 + getWherePart( 'LWMission.MissionProp9','QAppntName' )
				 + getWherePart( 'LWMission.MissionProp3','QProposalNo')
				 + getWherePart( 'LWMission.MissionProp10','QManageCom' );
		var sqlid917154338="DSHomeContSql917154338";
var mySql917154338=new SqlClass();
mySql917154338.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917154338.setSqlId(sqlid917154338);//ָ��ʹ�õ�Sql��id
mySql917154338.addSubPara(wherePartStr);//ָ������Ĳ���
mySql917154338.addSubPara(mOperate);//ָ������Ĳ���
mySql917154338.addSubPara(comcode+"%%");//ָ������Ĳ���
strSQL=mySql917154338.getString();
		
//		strSQL = "select LWMission.MissionProp1,LWMission.MissionProp2,LWMission.MissionProp7,LWMission.MissionProp5,LWMission.MissionProp8,LWMission.MissionID ,LWMission.SubMissionID from LWMission where "+k+"="+k
//				 //+ " and VERIFYOPERATEPOPEDOM(LCPol.Riskcode,LCPol.Managecom,'"+comcode+"','Pe')=1"
//				 + " and LWMission.ProcessID = '0000000003' " //�б��˱�������
//				 + " and LWMission.ActivityID = '0000001100' " //�б��˱��������еĴ��˹��˱�����ڵ�
//				 //+ getWherePart( 'LWMission.DefaultOperator','QOperator' )
//				 + getWherePart( 'LWMission.MissionProp1','QPrtNo' )
//				 + getWherePart( 'LWMission.MissionProp9','QAppntName' )
//				 + getWherePart( 'LWMission.MissionProp3','QProposalNo')
//				 + getWherePart( 'LWMission.MissionProp10','QManageCom' )
//				 + " and ((select UWPopedom from LDUser where usercode = '"+mOperate+"') > (select AppGrade from LCCUWMaster where trim(ContNo) = LWMission.MissionProp2 ))"
//				 + " and LWMission.MissionProp10 like '" + comcode + "%%'";  //����Ȩ�޹�������
	   }
	   else //����+�¼�����
	   {
		var wherePartStr = getWherePart( 'LWMission.MissionProp1','QPrtNo' )
				 + getWherePart( 'LWMission.MissionProp9','QAppntName' )
				 + getWherePart( 'LWMission.MissionProp3','QProposalNo')
				 + getWherePart( 'LWMission.MissionProp10','QManageCom' );
		var sqlid917161800="DSHomeContSql917161800";
var mySql917161800=new SqlClass();
mySql917161800.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917161800.setSqlId(sqlid917161800);//ָ��ʹ�õ�Sql��id
mySql917161800.addSubPara(wherePartStr);//ָ������Ĳ���
mySql917161800.addSubPara(mOperate);//ָ������Ĳ���
mySql917161800.addSubPara(comcode+"%%");//ָ������Ĳ���

var subSQL = " and 1=1 ";

		
//		strSQL = "select LWMission.MissionProp1,LWMission.MissionProp2,LWMission.MissionProp7,LWMission.MissionProp5,LWMission.MissionProp8,LWMission.MissionID ,LWMission.SubMissionID from LWMission where "+k+"="+k
//				 //+ " and VERIFYOPERATEPOPEDOM(LCPol.Riskcode,LCPol.Managecom,'"+comcode+"','Pe')=1"
//				 + " and LWMission.ProcessID = '0000000003' " //�б��˱�������
//				 + " and LWMission.ActivityID = '0000001100' " //�б��˱��������еĴ��˹��˱�����ڵ�
//				 //+ getWherePart( 'LWMission.DefaultOperator','QOperator' )
//				 + getWherePart( 'LWMission.MissionProp1','QPrtNo' )
//				 + getWherePart( 'LWMission.MissionProp9','QAppntName' )
//				 + getWherePart( 'LWMission.MissionProp3','QProposalNo')
//				 + getWherePart( 'LWMission.MissionProp10','QManageCom' )
//				 + " and ((select UWPopedom from LDUser where usercode = '"+mOperate+"') >= (select AppGrade from LccUWMaster where trim(ContNo) = LWMission.MissionProp2 ))"
//				 + " and LWMission.MissionProp10 like '" + comcode + "%%'";  //����Ȩ�޹�������
	}

     //alert(strSQL);
     var tOperator = fm.QOperator.value;
	if(state == "1")
	{
			subSQL =  " and  LWMission.ActivityStatus = '1'"
		      + " and ( LWMission.DefaultOperator is null or LWMission.DefaultOperator = '" + tOperator + "')";
	}
	if(state == "2")
	{
		subSQL = " and  LWMission.ActivityStatus = '3'"
		      + "and LWMission.DefaultOperator = '" + tOperator + "'" ;
	}
	if(state == "3")
	{
		subSQL =  " and  LWMission.ActivityStatus = '2'"
		    + "and LWMission.DefaultOperator = '" + tOperator + "'" ;
	}
	
	mySql917161800.addSubPara(subSQL);//ָ������Ĳ���
	strSQL=mySql917161800.getString();


	if (withdrawFlag) {
	  //strSQL = strSQL + " and LCPol.PrtNo in (select prtno from LCApplyRecallPol where ApplyType='0') ";
	  var wherePartStr = getWherePart( 'LCUWMaster.Operator','QOperator');
	  var sqlid917162530="DSHomeContSql917162530";
var mySql917162530=new SqlClass();
mySql917162530.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917162530.setSqlId(sqlid917162530);//ָ��ʹ�õ�Sql��id
mySql917162530.addSubPara(mOperate);//ָ������Ĳ���
mySql917162530.addSubPara(manageCom+"%%");//ָ������Ĳ���
mySql917162530.addSubPara(wherePartStr);//ָ������Ĳ���
strSQL=mySql917162530.getString();
	  
//	  strSQL = "select LCPol.ProposalNo,LCPol.PrtNo,LMRisk.RiskName,LCPol.AppntName,LCPol.InsuredName "
//           + " from LCPol,LCUWMaster,LMRisk where 10=10 "
//           + " and LCPol.AppFlag='0'  "
//           + " and LCPol.UWFlag not in ('1','2','a','4','9')  "
//           + " and LCPol.grppolno = '00000000000000000000' and LCPol.contno = '00000000000000000000' "
//           + " and LCPol.ProposalNo = LCPol.MainPolNo  and LCPol.ProposalNo= LCUWMaster.ProposalNo  "
//           + " and LCUWMaster.appgrade <= (select UWPopedom from LDUser where usercode = '"+mOperate+"') "
//           + " and LCPol.ManageCom like '" + manageCom + "%%'"
//           + " and LMRisk.RiskCode = LCPol.RiskCode "
//           + getWherePart( 'LCUWMaster.Operator','QOperator')
//           + " and LCPol.PrtNo in (select prtno from LCApplyRecallPol where ApplyType='0')";

	  withdrawFlag = false;
	}

	//execEasyQuery( strSQL );
	  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    //alert(turnPage.strQueryResult);
    alert("û��ûͨ���б��˱��ĸ��˵���");
    return "";
  }

  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = PolGrid;

  //����SQL���
  turnPage.strQuerySql     = strSQL;

  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;

  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //alert(arrDataSet);
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

  return true;
}

/*********************************************************************
 *  ִ��multline�ĵ���¼�
 *  ����:�˴����ɹ������ķ���졢�������˱�֪ͨ��Ƚ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function easyQueryAddClick(parm1,parm2)
{
	// ��дSQL���
	k++;
	var uwgrade = fm.UWGradeStatus.value;
	var mOperate = fm.Operator.value;
	var state = fm.State.value;       //Ͷ��������״̬
	var tProposalNo = "";
	var tPNo = "";
	var strSQL = "";

	if(document.all(parm1).all('InpPolGridSel').value == '1' ){
		//��ǰ�е�1�е�ֵ��Ϊ��ѡ��
		tProposalNo = document.all(parm1).all('PolGrid2').value;
		tPNo = document.all(parm1).all('PolGrid1').value;
	}

	document.all('ContNo').value = document.all(parm1).all('PolGrid2').value;
	document.all('MissionID').value = document.all(parm1).all('PolGrid6').value;
	document.all('PrtNo').value = document.all(parm1).all('PolGrid1').value;
	document.all('SubMissionID').value = document.all(parm1).all('PolGrid7').value;
	var ContNo = document.all('ContNo').value;

	//alert("contno11="+document.all('ContNo').value);


	if(state == "1")
	{
		checkDouble(tProposalNo);
	//���ɹ������½ڵ�
	}
	else
		{
			makeWorkFlow();
			}

	// ��ʼ�����
	initPolAddGrid();
	initPolBox();
	DivLCContInfo.style.display = "none";
	divLCPol1.style.display= "none";
	divSearch.style.display= "none";
	divLCPol2.style.display= "";
	divMain.style.display = "";
	DivLCContButton.style.display="";
	DivLCCont.style.display="";
	DivLCAppntInd.style.display="";
	DivLCAppntIndButton.style.display="";
	if (uwgrade == "1")
	{
		var sqlid917162808="DSHomeContSql917162808";
var mySql917162808=new SqlClass();
mySql917162808.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917162808.setSqlId(sqlid917162808);//ָ��ʹ�õ�Sql��id
mySql917162808.addSubPara(ContNo);//ָ������Ĳ���
strSQL=mySql917162808.getString();
		
//		strSQL = "select LCInsured.InsuredNo,LCInsured.Name,LCInsured.IDType,LCInsured.IDNo,case when LCInsured.INSUREDSTAT='1' then '����ͣ' end  from LCInsured where "+k+"="+k
//				  + " and LCInsured.Contno = '" + ContNo + "'";
				  //+ " union"
				  //+ " select '  ',LCPol.PolNo,LCPol.PrtNo,LCPol.RiskCode,LCPol.AppntName,LCPol.InsuredName,' ',' ' from LCPol where "+k+"="+k
				  //+ " and LCPol.prtno = (select prtno from lcpol where polno ='" + tProposalNo + "')"
				  //+ " and LCPol.polno <> '" + tProposalNo + "'"
				  //+ " order by 2 ";
	}
	else
	{
		var sqlid917162858="DSHomeContSql917162858";
var mySql917162858=new SqlClass();
mySql917162858.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917162858.setSqlId(sqlid917162858);//ָ��ʹ�õ�Sql��id
mySql917162858.addSubPara(ContNo);//ָ������Ĳ���
strSQL=mySql917162858.getString();
		
//		strSQL = "select LCInsured.InsuredNo,LCInsured.Name,LCInsured.IDType,LCInsured.IDNo,case when LCInsured.INSUREDSTAT='1' then '����ͣ' end from LCInsured where "+k+"="+k
//				  + " and LCInsured.Contno = '" + ContNo + "'";

	}
	  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    //alert(turnPage.strQueryResult);
    alert("û��ûͨ���б��˱��ĸ��˵���");
 		window.location.href("./ManuUWInput.jsp");
    return "";
  }

  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = PolAddGrid;

  //����SQL���
  turnPage.strQuerySql     = strSQL;

  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;

  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet   = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //alert(arrDataSet);
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  //alert("��ѯeasyQueryAddClick"+arrDataSet.length);
  initFlag(  arrDataSet.length );

  //alert("contno21="+document.all(parm1).all('PolGrid2').value);
  //alert("PrtNo="+document.all('PrtNo').value);
  	var arrSelected = new Array();

	var sqlid917162959="DSHomeContSql917162959";
var mySql917162959=new SqlClass();
mySql917162959.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917162959.setSqlId(sqlid917162959);//ָ��ʹ�õ�Sql��id
mySql917162959.addSubPara(ContNo);//ָ������Ĳ���
strSQL=mySql917162959.getString();
	
//	strSQL = "select ProposalContNo,PrtNo,ManageCom,SaleChnl,AgentCode,AgentGroup,AgentCode1,AgentCom,AgentType,ReMark from lccont where contno='"+ContNo+"'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);

	document.all('ProposalContNo').value = arrSelected[0][0];
	document.all('PrtNo').value = arrSelected[0][1];
	document.all('ManageCom').value = arrSelected[0][2];
	document.all('SaleChnl').value = arrSelected[0][3];
	document.all('AgentCode').value = arrSelected[0][4];
	document.all('AgentGroup').value = arrSelected[0][5];
	document.all('AgentCode1').value = arrSelected[0][6];
	document.all('AgentCom').value = arrSelected[0][7];
	document.all('AgentType').value = arrSelected[0][8];
	document.all('ReMark').value = arrSelected[0][9];

	var sqlid917163049="DSHomeContSql917163049";
var mySql917163049=new SqlClass();
mySql917163049.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917163049.setSqlId(sqlid917163049);//ָ��ʹ�õ�Sql��id
mySql917163049.addSubPara(ContNo);//ָ������Ĳ���
strSQL=mySql917163049.getString();
	
//	strSQL = "select AppntName,AppntSex,AppntBirthday,AppntNo,AddressNo,VIPValue,BlacklistFlag from lcappnt,LDPerson where contno='"+ContNo+"'"
//		+ "and LDPerson.CustomerNo = LCAppnt.AppntNo";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);

	document.all('AppntName').value = arrSelected[0][0];
	document.all('AppntSex').value = arrSelected[0][1];
	document.all('AppntBirthday').value = arrSelected[0][2];
	document.all('AppntNo').value = arrSelected[0][3];
	document.all('AddressNo').value = arrSelected[0][4];
	document.all('VIPValue').value = arrSelected[0][5];
	document.all('BlacklistFlag').value = arrSelected[0][6];

  return true;
}


function displayEasyResult( arrResult )
{
	var i, j, m, n;

	if( arrResult == null )
		alert( "û���ҵ���ص�����!" );
	else
	{
		// ��ʼ�����
		initPolGrid();

		arrGrid = arrResult;
		// ��ʾ��ѯ���
		n = arrResult.length;
		for( i = 0; i < n; i++ )
		{
			m = arrResult[i].length;
			for( j = 0; j < m; j++ )
			{
				PolGrid.setRowColData( i, j+1, arrResult[i][j] );
			}// end of for
		} // end of for
		//alert("result:"+arrResult);
	} // end of if
}

//����Ҫ�˹��˱�����
function checkDouble(tProposalNo)
{
	fm.PolNoHide.value = tProposalNo;
	fm.action = "./ManuUWApply.jsp";
	document.getElementById("fm").submit();
}

//ѡ��Ҫ�˹��˱�����
function getPolGridCho()
{
	//setproposalno();
	var cSelNo = PolAddGrid.getSelNo()-1;

	codeSql = "code";
	fm.UWPopedomCode.value = "";
	fm.action = "./ManuUWCho.jsp";
	document.getElementById("fm").submit();
}

/*********************************************************************
 *  ���ɹ������������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function makeWorkFlow()
{
	fm.action = "./ManuUWChk.jsp";
	document.getElementById("fm").submit();
	}

function checkBackPol(ProposalNo) {
  var sqlid917163233="DSHomeContSql917163233";
var mySql917163233=new SqlClass();
mySql917163233.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917163233.setSqlId(sqlid917163233);//ָ��ʹ�õ�Sql��id
mySql917163233.addSubPara(ProposalNo);//ָ������Ĳ���
var strSql=mySql917163233.getString();
  
//  var strSql = "select * from LCApplyRecallPol where ProposalNo='" + ProposalNo + "' and InputState='0'";
  var arrResult = easyExecSql(strSql);
  //alert(arrResult);

  if (arrResult != null) {
    return false;
  }
  return true;
}

//-----------------------------------Beg
//EditBy:Chenrong
//Date:2006-4-27
//��ѯ���±�
function checkNotePad(tMissionID){
	
	var sqlid917163421="DSHomeContSql917163421";
var mySql917163421=new SqlClass();
mySql917163421.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917163421.setSqlId(sqlid917163421);//ָ��ʹ�õ�Sql��id
mySql917163421.addSubPara(tMissionID);//ָ������Ĳ���
var strSQL=mySql917163421.getString();
	
//	var strSQL="select count(*) from LWNotePad where MissionID='"+tMissionID+"'";
	
	var arrResult = easyExecSql(strSQL);
	
	eval("document.all('NotePadButton').value='���±��鿴 ����"+arrResult[0][0]+"����'");
	
}

function checkBankSendFlag(tPrtNo){
	
	//var strSQL="select count(*) from LWNotePad where MissionID='"+tMissionID+"'";
	var sqlid917163533="DSHomeContSql917163533";
var mySql917163533=new SqlClass();
mySql917163533.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917163533.setSqlId(sqlid917163533);//ָ��ʹ�õ�Sql��id
mySql917163533.addSubPara(tPrtNo);//ָ������Ĳ���
var strSQL=mySql917163533.getString();
	
//	var strSQL = "select decode(nvl(NewAutoSendBankFlag,'1'),'0','N','Y') from LCCont where ContNo='"+tPrtNo+"'";
	var arrResult = easyExecSql(strSQL);
	eval("document.all('SendBankFlagButton').value='�޸����з��̷�ʽ("+arrResult[0][0]+")'");
}

//---------------------------------End

//-----------------------------------Beg
//EditBy:ln
//Date:2008-12-3
//��ѯ�˱���������
function checkReport(ContNo){
	//tongmeng 2009-06-18 modify
	//����Ч��
	/*var strSQL="select count(*) from LCUWReport where 1=1"
		+ " and (otherno in ((select prtno from lccont where contno='"+ContNo+"'),'"+ContNo+"' )"
		+ " or otherno in (select proposalno from lcpol where contno='"+ContNo+"'))"
		*/
	var sqlid917163711="DSHomeContSql917163711";
var mySql917163711=new SqlClass();
mySql917163711.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917163711.setSqlId(sqlid917163711);//ָ��ʹ�õ�Sql��id
mySql917163711.addSubPara(ContNo);//ָ������Ĳ���
mySql917163711.addSubPara(ContNo);//ָ������Ĳ���
var strSQL=mySql917163711.getString();

	
//	var strSQL = "select count(*) from LCUWReport where 1=1"
//	           + "and otherno in ( "
//	           + " select prtno from lccont where contno = '"+ContNo+"' "
//  					 + " union "
//             + " select proposalno from lcpol where contno = '"+ContNo+"' "
//	           + " ) " ;
	var arrResult = easyExecSql(strSQL);
	
	eval("document.all('ReportButton').value='�˱���������鿴 ����"+arrResult[0][0]+"����'");
	
}
//---------------------------------End

//  ��ʼ���˱�ʦ�Ƿ��Ѳ鿴����Ӧ����Ϣ�������
function initFlag(  tlength )
{
	// ��Ǻ˱�ʦ�Ƿ��Ѳ鿴����Ӧ����Ϣ
      showPolDetailFlag =  new Array() ;
      showAppFlag = new Array() ;
      showHealthFlag = new Array() ;
      QuestQueryFlag = new Array() ;

     var i=0;
	  for( i = 0; i < tlength ; i++ )
		{
			showPolDetailFlag[i] = 0;
			showAppFlag[i] = 0;
			showHealthFlag[i] = 0;
			QuestQueryFlag[i] = 0;
		}

	}
//�º˱�����
function manuchk()
{

	flag = document.all('UWState').value;
	var ProposalNo = document.all('ProposalNo').value;
	var MainPolNo = document.all('MainPolNoHide').value;

	if (trim(fm.UWState.value) == "") {
    alert("������¼��˱����ۣ�");
    return;
  }

	if (!checkBackPol(ProposalNo)) {
	  if (!confirm("��Ͷ�����г������룬�����´˽�����")) return;
	}

    if (trim(fm.UWState.value) == "6") {
      if(trim(fm.UWPopedomCode.value) !="")
         fm.UWOperater.value = fm.UWPopedomCode.value
      else
         fm.UWOperater.value = operator;
}

    var tSelNo = PolAddGrid.getSelNo()-1;

	if(fm.State.value == "1"&&(fm.UWState.value == "1"||fm.UWState.value == "2"||fm.UWState.value =="4"||fm.UWState.value =="6"||fm.UWState.value =="9"||fm.UWState.value =="a")) {
      if( showPolDetailFlag[tSelNo] == 0 || showAppFlag[tSelNo] == 0 || showHealthFlag[tSelNo] == 0 || QuestQueryFlag[tSelNo] == 0 ){
         var tInfo = "";
         if(showPolDetailFlag[tSelNo] == 0)
            tInfo = tInfo + " [Ͷ������ϸ��Ϣ]";
         if(showAppFlag[tSelNo] == 0)
            tInfo = tInfo + " [����Ͷ����Ϣ]";
         if( PolAddGrid.getRowColData(tSelNo,1,PolAddGrid) == PolAddGrid.getRowColData(tSelNo ,2,PolAddGrid)) {
         	if(showHealthFlag[tSelNo] == 0)
              tInfo = tInfo + " [�������¼��]";
         }
         if(QuestQueryFlag[tSelNo] == 0)
            tInfo = tInfo + " [�������ѯ]";
         if ( tInfo != "" ) {
         	tInfo = "����Ҫ��Ϣ:" + tInfo + " δ�鿴,�Ƿ�Ҫ�¸ú˱�����?";
         	if(!window.confirm( tInfo ))
         	    return;
             }

        }
     }
	var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();


	fm.action = "./UWManuNormChk.jsp";
	document.getElementById("fm").submit();

	if (flag != "b"&&ProposalNo == MainPolNo)
	{
		initInpBox();
   		initPolBox();
		initPolGrid();
		initPolAddGrid();
	}
}

//function manuchk()
//{
//
//	flag = document.all('UWState').value;
//	tUWIdea = document.all('UWIdea').value;
//
//	//¼��б��ƻ��������Ҫ����������
//	if( flag == "b")
//	{
//		cProposalNo=fm.PolNoHide.value;
//	}
//	else
//	{
//		cProposalNo=fm.ProposalNo.value;
//	}
//
//	//alert("flag:"+flag);
//	if (cProposalNo == "")
//	{
//		alert("����ѡ�񱣵�!");
//	}
//	else
//	{
//		if (flag == "0"||flag == "1"||flag == "4"||flag == "6"||flag == "9"||flag == "b")
//		{
//			showModalDialog("./UWManuNormMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+flag+"&UWIdea="+tUWIdea,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
//		}
//
//		if (flag == "2") //����
//		{
//			//showModalDialog("./UWManuDateMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+flag+"&UWIdea="+tUWIdea,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
//			window.open("./UWManuDateMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+flag+"&UWIdea="+tUWIdea,"window1");
//		}
//
//		if (flag == "3") //�����б�
//		{
//			//showModalDialog("./UWManuSpecMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+flag+"&UWIdea="+tUWIdea,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
//			window.open("./UWManuSpecMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+flag+"&UWIdea="+tUWIdea,"window1");
//		}
//		if (flag == "7") //�����¼��
//		{
//			QuestInput();
//		}
//
//		if (flag != "b")
//		{
//		initInpBox();
//    		initPolBox();
//		initPolGrid();
//		initPolAddGrid();
//		}
//	}
//}
/*
*/
//������ظ�
function QuestReply()
{
	cProposalNo = fm.ProposalNo.value;  //��������

	//showModalDialog("./QuestInputMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cFlag,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
	window.open("./QuestReplyMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cflag,"window1",sFeatures);

	initInpBox();
    initPolBox();
	initPolGrid();

}

//�������ѯ
function QuestQuery()
{
	cContNo = fm.ContNo.value;  //��������


  if (cContNo != "")
  {
  	var tSelNo = PolAddGrid.getSelNo()-1;
  	QuestQueryFlag[tSelNo] = 1 ;
	window.open("./QuestQueryMain.jsp?ContNo="+cContNo+"&Flag="+cflag,"window2",sFeatures);
  }
  else
  {
  	alert("����ѡ�񱣵�!");
  }

}

//������鱨���ѯ
function RReportQuery()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();


  var cContNo = fm.ContNo.value;
  var cMissionID = fm.MissionID.value;
  var cSubMissionID = fm.SubMissionID.value;
  var cPrtNo = fm.PrtNo.value;


  if (cContNo!= ""  )
  {
  	//var tSelNo = PolAddGrid.getSelNo()-1;
  	//var tNo = PolAddGrid.getRowColData(tSelNo,1);
  	//showHealthFlag[tSelNo] = 1 ;
  	window.open("./RReportQueryMain.jsp?ContNo="+cContNo+"&MissionID="+cMissionID+"&SubMissionID="+cSubMissionID+"&PrtNo="+cPrtNo,"window1",sFeatures);
  	showInfo.close();

  }
  else
  {
  	showInfo.close();
  	alert("����ѡ�񱣵�!");
  }
}

//�������������ѯ
function BackPolQuery()
{
  cProposalNo = fm.PrtNo.value;  //��������

//alert(cProposalNo);
  if (cProposalNo != "")
  {
	window.open("./BackPolQueryMain.jsp?ProposalNo1="+cProposalNo,"",sFeatures);
  }
  else
  {
  	alert("����ѡ�񱣵�!");
  }
}

//�߰쳬ʱ��ѯ
function OutTimeQuery()
{
  cProposalNo = fm.ProposalNo.value;  //��������


  if (cProposalNo != "")
  {
	window.open("./OutTimeQueryMain.jsp?ProposalNo1="+cProposalNo,"",sFeatures);
  }
  else
  {
  	alert("����ѡ�񱣵�!");
  }
}

//���ռƻ����
function showChangePlan()
{
	/*
  cProposalNo = fm.ProposalNo.value;  //��������
  cPrtNo = fm.PrtNoHide.value; //ӡˢ��
  var cType = "ChangePlan";
  mSwitch.deleteVar( "PolNo" );
  mSwitch.addVar( "PolNo", "", cProposalNo );

  if (cProposalNo != ""&&cPrtNo != "")
  {
  	 var strSql = "select * from LCIssuepol where ProposalNo='" + cProposalNo + "' and (( backobjtype in('1','4') and replyresult is null) or ( backobjtype in('2','3') and needprint = 'Y' and replyresult is null))";
     var arrResult = easyExecSql(strSql);
     if (arrResult != null) {
       var tInfo = "��δ�ظ��������,ȷ��Ҫ���гб��ƻ����?";
       if(!window.confirm( tInfo ))
         	    return;
      }
    window.open("../app/ProposalEasyScan.jsp?LoadFlag=3&Type="+cType+"&prtNo="+cPrtNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");

   }
  else
  {
  	alert("����ѡ�񱣵�!");
  }
  */

  var cPrtNo = fm.PrtNo.value;
  
  var strSql="";
  //strSql="select salechnl from lccont where contno='"+cPrtNo+"'";
  //strSql="select case lmriskapp.riskprop"
  //       +" when 'I' then '1'"
	//	     +" when 'G' then '2'"
//		     +" when 'Y' then '3'"
//		     +" when 'T' then '5'"
//        +" end"
//        +" from lmriskapp"
//        +" where riskcode in (select riskcode"
//        +" from lcpol"
//        +" where polno = mainpolno"
//        +" and prtno = '"+cPrtNo+"')"
//  arrResult = easyExecSql(strSql);
//  var BankFlag = arrResult[0][0];
  
  	//ȥ��ԭ�����ж�Ͷ�������͵��߼����޸�Ϊ��ӡˢ�����ж�Ͷ��������
	var BankFlag = "";
    var tSplitPrtNo = cPrtNo.substring(0,4);
    //alert("tSplitPrtNo=="+tSplitPrtNo); 
    //8635��8625��8615������Ͷ�������棬����Ķ��߸��ս���
    if(tSplitPrtNo=="8635"||tSplitPrtNo=="8625"||tSplitPrtNo=="8615"){
    	BankFlag="3";
    }else{
    	BankFlag="1";
    }
  
  var sqlid917164115="DSHomeContSql917164115";
var mySql917164115=new SqlClass();
mySql917164115.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917164115.setSqlId(sqlid917164115);//ָ��ʹ�õ�Sql��id
mySql917164115.addSubPara(cPrtNo);//ָ������Ĳ���
var strSql2=mySql917164115.getString();
  
//  var strSql2="select missionprop5 from lbmission where activityid='0000001099' and missionprop1='"+cPrtNo+"'";
	var crrResult = easyExecSql(strSql2);
	var SubType="";
	if(crrResult!=null)
	{
	SubType = crrResult[0][0];
	}

  window.open("../app/ProposalEasyScan.jsp?LoadFlag=25&prtNo="+cPrtNo+"&ContNo="+cPrtNo+"&BankFlag="+BankFlag+"&SubType="+SubType, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);
}

//���ռƻ��������¼����ʾ
function showChangeResultView()
{
	var cContNo = fm.ContNo.value;
	var sqlid917164239="DSHomeContSql917164239";
var mySql917164239=new SqlClass();
mySql917164239.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917164239.setSqlId(sqlid917164239);//ָ��ʹ�õ�Sql��id
mySql917164239.addSubPara(cContNo);//ָ������Ĳ���
var strSql=mySql917164239.getString();

	
//	var strSql = "select changepolreason from LCcUWMaster where ContNo='" + cContNo + "' ";
  //  alert(strSql);
  var arrResult = easyExecSql(strSql);
 
	fm.ChangeIdea.value = arrResult[0][0];
	//fm.PolNoHide.value = fm.ProposalNo.value;  //��������
	
	divUWResult.style.display= "none";
	divChangeResult.style.display= "";
}


//��ʾ���ռƻ��������
function showChangeResult()
{
	
	fm.uwState.value = "b";
	fm.UWIdea.value = fm.ChangeIdea.value;
	var cContNo = fm.ProposalContNo.value;

	cChangeResult = fm.UWIdea.value;

	
	
		var sqlid917164324="DSHomeContSql917164324";
var mySql917164324=new SqlClass();
mySql917164324.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917164324.setSqlId(sqlid917164324);//ָ��ʹ�õ�Sql��id
mySql917164324.addSubPara(cContNo);//ָ������Ĳ���
var strSql=mySql917164324.getString();
		
//		var strSql = "select * from LCIssuepol where ContNo='" + cContNo + "' and (( backobjtype in('1','4') and replyresult is null) or ( backobjtype in('2','3') and needprint = 'Y' and replyresult is null))";
        var arrResult = easyExecSql(strSql);
        //alert(arrResult);
       if (arrResult != null) {
       var tInfo = "��δ�ظ��������,ȷ��Ҫ���гб��ƻ����?";
       if(!window.confirm( tInfo )){
       	      HideChangeResult()
         	    return;
          }
       }

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
    
  	fm.action = "./UWManuNormChk.jsp";
  
  	document.getElementById("fm").submit(); //�ύ
  
	divUWResult.style.display= "";
	divChangeResult.style.display= "none";
	fm.uwState.value = "";
	fm.UWIdea.value = "";
	fm.UWPopedomCode.value = "";
}

//���ر��ռƻ��������
function HideChangeResult()
{
	divUWResult.style.display= "";
	divChangeResult.style.display= "none";
	fm.uwState.value = "";
	fm.UWIdea.value = "";
	fm.UWPopedomCode.value = "";
}


function cancelchk()
{
	document.all('uwState').value = "";
	document.all('UWPopedomCode').value = "";
	document.all('UWIdea').value = "";
}

function setproposalno()
{
	var count = PolGrid.getSelNo();
	document.all('ProposalNo').value = PolGrid.getRowColData(count - 1,1,PolGrid);
}

//�����հ�ť���غ���
function hideAddButton()
{
	parent.fraInterface.divAddButton1.style.display= "none";
	parent.fraInterface.divAddButton2.style.display= "none";
	parent.fraInterface.divAddButton3.style.display= "none";
	parent.fraInterface.divAddButton4.style.display= "none";
	parent.fraInterface.divAddButton5.style.display= "none";
	parent.fraInterface.divAddButton6.style.display= "none";
	parent.fraInterface.divAddButton7.style.display= "none";
	parent.fraInterface.divAddButton8.style.display= "none";
	parent.fraInterface.divAddButton9.style.display= "none";
	parent.fraInterface.divAddButton10.style.display= "none";
	//parent.fraInterface.fm.UWState.CodeData = "0|^4|ͨ�ڳб�^9|�����б�";
	//parent.fraInterface.UWResult.innerHTML="�˱�����<Input class=\"code\" name=UWState CodeData = \"0|^4|ͨ��/�����б�^9|�����б�\" ondblclick= \"showCodeListEx('UWState1',[this,''],[0,1]);\" onkeyup=\"showCodeListKeyEx('UWState1',[this,''],[0,1]);\">";


}

//��ʾ���ذ�ť
function showAddButton()
{
	parent.fraInterface.divAddButton1.style.display= "";
	parent.fraInterface.divAddButton2.style.display= "";
	parent.fraInterface.divAddButton3.style.display= "";
	parent.fraInterface.divAddButton4.style.display= "";
	parent.fraInterface.divAddButton5.style.display= "";
	parent.fraInterface.divAddButton6.style.display= "";
	parent.fraInterface.divAddButton7.style.display= "";
	parent.fraInterface.divAddButton8.style.display= "";
	parent.fraInterface.divAddButton9.style.display= "";
	parent.fraInterface.divAddButton10.style.display= "";
	//parent.fraInterface.UWResult.innerHTML="�˱�����<Input class=\"code\" name=UWState CodeData = \"0|^1|�ܱ�^2|����^4|ͨ��/�����б�^6|���ϼ��˱�^9|�����б�^a|����Ͷ����\" ondblclick= \"showCodeListEx('UWState',[this,''],[0,1]);\" onkeyup=\"showCodeListKeyEx('UWState',[this,''],[0,1]);\">";
	//parent.fraInterface.fm.UWState.CodeData = "0|^1|�ܱ�^2|����^4|ͨ�ڳб�^6|���ϼ��˱�^9|�����б�^a|����Ͷ����";
}

function IssueMistake(){
	var PrtNo = document.all.PrtNo.value;
	var NoType = "1";
	if(PrtNo == null || PrtNo == "")
	{
		alert("Ͷ������Ϊ�գ�");
		return;
	}

	var varSrc= "?PrtNo="+ PrtNo ;
	var newWindow = OpenWindowNew("../uw/MSQuestMistakeMain.jsp?Interface=../uw/MSQuestMistakeInput.jsp" + varSrc,"���������¼","left");
	
}

function showNotePad()
{	
	//var ActivityID = "0000001100";

  var ActivityID = document.all('ActivityID').value;//modify by lzf 2013-04-01
 
  var PrtNo = document.all.PrtNo.value;
	var NoType = "1";
	if(PrtNo == null || PrtNo == "")
	{
		alert("Ͷ������Ϊ�գ�");
		return;
	}

	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ PrtNo + "&NoType="+ NoType;
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");

}

function InitClick()
{
	 if(confirm("ȷ�Ϲرո�Ͷ�������˹��˱����棿"))
	 {
		 top.close(); 
	 	 top.opener.NeweasyQueryClick();
    }
  else
  {
    return;
  }
}

/*********************************************************************
 *  ���뱻������Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showInsuredInfo()
{
	  var tSelNo = PolAddGrid.getSelNo();
		var tContNo = fm.ContNo.value;
		var tInsuredNo = PolAddGrid.getRowColData(tSelNo - 1,1);
		var tMissionID = fm.MissionID.value;
		var tSubMissionID = fm.SubMissionID.value;
		var tPrtNo = fm.PrtNo.value;
		var tSendFlag = fm.SendFlag.value;
		window.open("./InsuredUWInfoMain.jsp?ScanFlag=0&ContNo="+tContNo+"&InsuredNo="+tInsuredNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&PrtNo="+tPrtNo+"&SendFlag="+tSendFlag, "", sFeatures);
}

/*********************************************************************
 *  ����ϵͳ��
 *  ����:�˴����ɹ������ķ���졢�������˱�֪ͨ��Ƚ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function initUW()
{
	//alert("ok");
        //add by yaory for init �� �� �� �� button
//        alert(MissionID);
//        alert(SubMissionID);
//       alert(ActivityID);
//strSql = "select missionprop16 from lwmission a where a.missionid='"+MissionID+"' and a.activityid='0000001100' and submissionid='"+SubMissionID+"'";
//var DistriMark = easyExecSql(strSql);
//alert(strSql);
//alert(DistriMark);
//alert("ok");

//
//strSql="select healthyamntfb('"+peop+"','','1') from dual";
//var life=easyExecSql(strSql);
//strSql="select healthyamntfb('"+peop+"','','7') from dual";
//var acci=easyExecSql(strSql);
//strSql="select healthyamntfb('"+peop+"','','2') from dual";
//var nur=easyExecSql(strSql);
//var add=life+acci;
////alert(add);
////alert(nur);
////return;
//var re;
//var fb="0";
//if(add>3000000 || nur>1200000)
//{
//	fb="1";
//}
//if(add>500000 || nur>100000)
//{
//	re="1";
//}else{
//re="0";
//}
//alert("ok");
//alert(strSql);
//if(DistriMark!="1")
//{
//   //fm.ReDistribute.style.display='none';
//   fm.ReDistribute.disabled=true;
//}

    //�жϺ˱�״̬�������5������ʾ����ԭ�� ������ʾ
    if(UWState == "5")
    {
      var sqlid917164421="DSHomeContSql917164421";
var mySql917164421=new SqlClass();
mySql917164421.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917164421.setSqlId(sqlid917164421);//ָ��ʹ�õ�Sql��id
mySql917164421.addSubPara(ContNo);//ָ������Ĳ���
var tSql=mySql917164421.getString();
      
//      var tSql = "select uwidea from lccuwmaster where 1=1 "
//					+ " and contno = '"+ContNo+"' "
//					;
		 var arr=easyExecSql(tSql);
	    if(arr!=null){
	    	document.all('Revise').value = arr[0][0];
	    }		
      DivRevise.style.display = "";
    }
    
	// ��дSQL���
	k++;
	var uwgrade = fm.UWGradeStatus.value;
	var mOperate = fm.Operator.value;
	var state = fm.State.value;       //Ͷ��������״̬
	var tProposalNo = "";
	var tPNo = "";
	var strSQL = "";

	document.all('ContNo').value = ContNo;
	document.all('MissionID').value = MissionID;
	document.all('PrtNo').value = PrtNo;
	document.all('SubMissionID').value = SubMissionID;
	document.all('ActivityID').value = ActivityID; // modify by lzf 2013-04-01

  //  makeWorkFlow(); // modify by lzf 2013-04-01
    
    //-------------------------------Beg
    //������±��鿴������ʾ
    //EditBy:Chenrong; Data:2006-4-27
    checkNotePad(MissionID);
    //-------------------------------End
    //�������з��̷�ʽ��ʾ
    checkBankSendFlag(PrtNo);
    //-------------------------------Beg
    //����˱���������鿴������ʾ
    //EditBy:ln; Data:2008-12-3
    checkReport(ContNo);
    //-------------------------------End

	// ��ʼ�����
	initPolAddGrid();
	initPolBox();
	initPolRiskGrid();
//	initSendTrace();
	initUpper();
	//tongmeng 2008-10-06 add �Զ��˱���ʾ��Ϣ
	easyQueryClickUW();
	
	DivLCContInfo.style.display = "none";
	divLCPol1.style.display= "none";
	divSearch.style.display= "none";
	divLCPol2.style.display= "";
	divMain.style.display = "";
	DivLCContButton.style.display="";
	DivLCCont.style.display="";
	DivLCAppntInd.style.display="";
	DivLCAppntIndButton.style.display="";
/*
	if (uwgrade == "1")
	{
	//	strSQL = "select LCInsured.InsuredNo,LCInsured.Name,(select codename from ldcode where code = LCInsured.IDType and codetype = 'idtype'),LCInsured.IDNo from LCInsured where "+k+"="+k
	//			  + " and LCInsured.Contno = '" + ContNo + "'"
	//			  + " order by LCInsured.SequenceNo "
	//			  ;
				  //+ " union"
				  //+ " select '  ',LCPol.PolNo,LCPol.PrtNo,LCPol.RiskCode,LCPol.AppntName,LCPol.InsuredName,' ',' ' from LCPol where "+k+"="+k
				  //+ " and LCPol.prtno = (select prtno from lcpol where polno ='" + tProposalNo + "')"
				  //+ " and LCPol.polno <> '" + tProposalNo + "'"
				  //+ " order by 2 ";
			strSQL = "select insuredno,name,(select insuredappage from lcpol where contno=a.contno and insuredno=a.insuredno), "
			       + " relationtoappnt,relationtomaininsured,nativeplace,(select codename from ldcode where code = LCInsured.IDType and codetype = 'idtype'),idno from lcinsured a where "+k+"="+k
			       + " and contno='" + ContNo + "'"
			       + " order by a.SequenceNo ";
	}
	else
	{
		strSQL = "select LCInsured.InsuredNo,LCInsured.Name,(select codename from ldcode where code = LCInsured.IDType and codetype = 'idtype'),LCInsured.IDNo from LCInsured where "+k+"="+k
				  + " and LCInsured.Contno = '" + ContNo + "'"
				  + " order by LCInsured.SequenceNo "
				  ;

	}
	*/
		var sqlid917164515="DSHomeContSql917164515";
var mySql917164515=new SqlClass();
mySql917164515.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917164515.setSqlId(sqlid917164515);//ָ��ʹ�õ�Sql��id
mySql917164515.addSubPara(ContNo);//ָ������Ĳ���
strSQL=mySql917164515.getString();

		
//		strSQL = "select insuredno,name,sex,(select max(insuredappage) from lcpol where contno=a.contno and insuredno=a.insuredno), "
//			       + " relationtoappnt,relationtomaininsured,nativeplace,(select codename from ldcode where code = a.IDType and codetype = 'idtype'),idno from lcinsured a where "+k+"="+k
//			       + " and contno='" + ContNo + "'"
//			       + " order by a.SequenceNo ";

	  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    //alert(turnPage.strQueryResult);
    alert("û��δͨ������Լ�˱��ĸ��˺�ͬ����");
    return;
  }
	
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = PolAddGrid;

  //����SQL���
  turnPage.strQuerySql     = strSQL;

  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;

  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet   = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //alert(arrDataSet);
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  //alert("��ѯeasyQueryAddClick"+arrDataSet.length);
  initFlag(  arrDataSet.length );

//tongmeng 2008-10-06 add
//���ֻ��һ��������,Ĭ��ѡ��
//alert("PolAddGrid:"+PolAddGrid.canSel+":"+PolAddGrid.mulLineCount);
if(PolAddGrid.canSel==1&&PolAddGrid.mulLineCount>1)
 {
 			//alert('@@');
 			document.all('PolAddGridSel')[0].checked=true;
 			//eval("document.all('MultMainRiskGridSel')[" + MultMainRiskGrid.mulLineCount + "-1].checked=true");
 			getInsuredDetail();
 }
 if(PolAddGrid.canSel==1&&PolAddGrid.mulLineCount==1)
 {
 			//alert('@@');
 			document.all('PolAddGridSel').checked=true;
 			//eval("document.all('MultMainRiskGridSel')[" + MultMainRiskGrid.mulLineCount + "-1].checked=true");
 			getInsuredDetail();
 }
 		
  //alert("contno21="+document.all(parm1).all('PolGrid2').value);
  //alert("PrtNo="+document.all('PrtNo').value);
  	var arrSelected = new Array();

	var sqlid917164623="DSHomeContSql917164623";
var mySql917164623=new SqlClass();
mySql917164623.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917164623.setSqlId(sqlid917164623);//ָ��ʹ�õ�Sql��id
mySql917164623.addSubPara(ContNo);//ָ������Ĳ���
mySql917164623.addSubPara(ContNo);//ָ������Ĳ���
strSQL=mySql917164623.getString();
	
//	strSQL = "select ProposalContNo,PrtNo,ManageCom,SaleChnl,AgentCode,AgentGroup,AgentCode1,AgentCom,AgentType,ReMark,(select nvl(sum(prem),0) from lcpol where contno=a.contno and uwflag not in ('1','2','a') ),(select decode(sum(inputprem),null,'0',sum(inputprem)) from lcpoloriginal where contno='"+ContNo+"')from lccont a where contno='"+ContNo+"'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
//prompt('',strSQL);
	document.all('ProposalContNo').value = arrSelected[0][0];
	document.all('PrtNo').value = arrSelected[0][1];
	document.all('ManageCom').value = arrSelected[0][2];
	document.all('SaleChnl').value = arrSelected[0][3];
	document.all('AgentCode').value = arrSelected[0][4];
	document.all('AgentGroup').value = arrSelected[0][5];
	document.all('AgentCode1').value = arrSelected[0][6];
	document.all('AgentCom').value = arrSelected[0][7];
	document.all('AgentType').value = arrSelected[0][8];
	document.all('Remark').value = arrSelected[0][9];
	try{
	document.all('ShouldPayPrem').value = arrSelected[0][10];
	}catch(e)
	{}
	document.all('OldPayPrem').value = arrSelected[0][11];

	var sqlid917164739="DSHomeContSql917164739";
var mySql917164739=new SqlClass();
mySql917164739.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917164739.setSqlId(sqlid917164739);//ָ��ʹ�õ�Sql��id
mySql917164739.addSubPara(ContNo);//ָ������Ĳ���
strSQL=mySql917164739.getString();
	
//	strSQL = "select AppntName,AppntSex,AppntBirthday,AppntNo,AddressNo,VIPValue,BlacklistFlag,lcappnt.OccupationCode,lcappnt.OccupationType,lcappnt.NativePlace,(select to_char(polapplydate,'yyyy-mm-dd') from lccont where contno=lcappnt.contno) from lcappnt,LDPerson where contno='"+ContNo+"'"
//		+ "and LDPerson.CustomerNo = LCAppnt.AppntNo";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);

	document.all('AppntName').value = arrSelected[0][0];
	document.all('AppntSex').value = arrSelected[0][1];
	quickGetName('Sex',fm.AppntSex,fm.AppntSexName);
	var tempPolApplyDate = arrSelected[0][10];
	if(tempPolApplyDate==null||tempPolApplyDate=='')
	{
		document.all('AppntBirthday').value = calAge(arrSelected[0][2]);
	}
	else
	{
		document.all('AppntBirthday').value = calAge(arrSelected[0][2],arrSelected[0][10]);
	}
	document.all('AppntNo').value = arrSelected[0][3];
	document.all('AddressNo').value = arrSelected[0][4];
	document.all('VIPValue').value = arrSelected[0][5];
	quickGetName('VIPValue',fm.VIPValue,fm.VIPValueName);
	document.all('BlacklistFlag').value = arrSelected[0][6];
	quickGetName('blacklistflag',fm.BlacklistFlag,fm.BlacklistFlagName);

	document.all('OccupationCode').value = arrSelected[0][7];
	quickGetName('OccupationCode',fm.OccupationCode,fm.OccupationCodeName);
	
	document.all('OccupationType').value = arrSelected[0][8];
	quickGetName('OccupationType',fm.OccupationType,fm.OccupationTypeName);
	
	document.all('NativePlace').value = arrSelected[0][9];
	quickGetName('NativePlace',fm.NativePlace,fm.NativePlaceName);

    //��ѯ�ϱ��˱�ʦ����
    var sqlid917164835="DSHomeContSql917164835";
var mySql917164835=new SqlClass();
mySql917164835.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917164835.setSqlId(sqlid917164835);//ָ��ʹ�õ�Sql��id
mySql917164835.addSubPara(ContNo);//ָ������Ĳ���
var osql=mySql917164835.getString();
    
    
//    var osql = "select missionprop19 from lwmission a where a.activityid='0000001100' and a.missionprop1 ='"+ContNo+"'";
    var upperuwuser = easyExecSql(osql);
    document.all('UpperUwUser').value = upperuwuser;
  //��ѯͶ���˵���ߡ����أ�������ʾ
 /* strSQL = "select case impartparamname"
         + " when 'stature' then"
         + " impartparam || 'cm'"
         + " when 'avoirdupois' then"
         + " impartparam || 'kg'"
         + " end"
         + " from lccustomerimpartparams"
         + " where 1 = 1"
         + " and customernotype = '0'"
         + " and impartcode = '000'"
         + " and impartver = '02'"
         + " and impartparamno in ('1', '2')"
         + " and contno = '"+ContNo+"'"
         + " order by impartparamno ";  
  var arr=easyExecSql(strSQL);
	if(arr!=null){*/
	var nnPrtNo = document.all('PrtNo').value;
	if(nnPrtNo!=null && nnPrtNo!="" && nnPrtNo.length==14 && nnPrtNo.substring(2,4)=="51")//��ͥ��
	{
	  var sqlid917164928="DSHomeContSql917164928";
var mySql917164928=new SqlClass();
mySql917164928.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917164928.setSqlId(sqlid917164928);//ָ��ʹ�õ�Sql��id
mySql917164928.addSubPara(ContNo);//ָ������Ĳ���
var sql=mySql917164928.getString();
	  
//	  var sql = "select impartparamname, impartparam"
//         + " from lccustomerimpartparams"
//         + " where 1 = 1"
//         //+ " and customernotype = '1'"             
//         + " and impartcode = 'D0101'"
//         + " and impartver = 'D01'"
//         + " and impartparamno in ('1', '2')"
//         + " and contno = '"+ContNo+"'"
//         + " order by customernotype desc,customerno,impartparamno ";
	   var arr1 = easyExecSql(sql);
	   if(arr1!=null)
	   {
			document.all('Stature').value=arr1[0][1];
			document.all('Weight').value=arr1[1][1];
			document.all('BMI').value=Math.round(parseFloat(arr1[1][1])/parseFloat(arr1[0][1])/parseFloat(arr1[0][1])*10000);
		}
		
		var sqlid917165012="DSHomeContSql917165012";
var mySql917165012=new SqlClass();
mySql917165012.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917165012.setSqlId(sqlid917165012);//ָ��ʹ�õ�Sql��id
mySql917165012.addSubPara(ContNo);//ָ������Ĳ���
var insql=mySql917165012.getString();

		
//		var insql = "select impartparam"
//         + " from lccustomerimpartparams"
//         + " where 1 = 1"
//         //+ " and customernotype = '1'"             
//         + " and impartcode = 'D0119'"
//         + " and impartver = 'D02'"
//         + " and impartparamno ='1'"
//         + " and contno = '"+ContNo+"'"
//         + " order by customernotype ,customerno,impartparamno ";		
	    var incomeway = easyExecSql(insql);
	    if(incomeway!=null && incomeway !="")
	    {
	       document.all('income').value = incomeway;
	    }
	}
	else
	{
		 var sqlid917165054="DSHomeContSql917165054";
var mySql917165054=new SqlClass();
mySql917165054.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917165054.setSqlId(sqlid917165054);//ָ��ʹ�õ�Sql��id
mySql917165054.addSubPara(ContNo);//ָ������Ĳ���
var sql=mySql917165054.getString();
		 
//		 var sql = "select impartparamname, impartparam"
//         + " from lccustomerimpartparams"
//         + " where 1 = 1"
//         //+ " and customernotype = '0'"         
//         + " and impartcode in ('A0101','A0501')"
//         + " and impartver in ('A01','A05')"
//         + " and impartparamno in ('3', '4')"
//         + " and contno = '"+ContNo+"'"
//         + " order by customernotype ,customerno,impartparamno ";
	   var arr1 = easyExecSql(sql);
	   if(arr1!=null)
	   {
			document.all('Stature').value=arr1[0][1];
			document.all('Weight').value=arr1[1][1];
			document.all('BMI').value=Math.round(parseFloat(arr1[1][1])/parseFloat(arr1[0][1])/parseFloat(arr1[0][1])*10000);
		}
		
		var sqlid917165129="DSHomeContSql917165129";
var mySql917165129=new SqlClass();
mySql917165129.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917165129.setSqlId(sqlid917165129);//ָ��ʹ�õ�Sql��id
mySql917165129.addSubPara(ContNo);//ָ������Ĳ���
var insql=mySql917165129.getString();
		
//		var insql = "select impartparam"
//         + " from lccustomerimpartparams"
//         + " where 1 = 1"
//         //+ " and customernotype = '0'"             
//         + " and impartcode in ('A0120','A0534')"
//         + " and impartver in ('A02','A06')"
//         + " and impartparamno ='3'"
//         + " and contno = '"+ContNo+"'"
//         + " order by customernotype ,customerno,impartparamno ";		
	    var incomeway = easyExecSql(insql);
	    if(incomeway!=null && incomeway !="")
	    {
	       document.all('income').value = incomeway;//��ѯ������
	    }
	}
	
	//alert(document.all('AppntNo').value);
    
    var tSumAmnt =0;
    var sqlid917165227="DSHomeContSql917165227";
var mySql917165227=new SqlClass();
mySql917165227.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917165227.setSqlId(sqlid917165227);//ָ��ʹ�õ�Sql��id
mySql917165227.addSubPara(document.all('AppntNo').value);//ָ������Ĳ���
strSQL=mySql917165227.getString();
    
//    strSQL =  "SELECT nvl(healthyamnt2('" + document.all('AppntNo').value +"','1','1'),0) from dual ";
    var arr1=easyExecSql(strSQL);
   // prompt("",strSQL);
	if(arr1!=null){
		document.all('AppntSumLifeAmnt').value=arr1[0][0];
		//tSumAmnt = tSumAmnt + parseFloat(arr1[0][0]);
		tSumAmnt = Arithmetic(tSumAmnt,'+',arr1[0][0],2);
	}
	 
	 var sqlid917165355="DSHomeContSql917165355";
var mySql917165355=new SqlClass();
mySql917165355.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917165355.setSqlId(sqlid917165355);//ָ��ʹ�õ�Sql��id
mySql917165355.addSubPara(document.all('AppntNo').value);//ָ������Ĳ���
strSQL=mySql917165355.getString();

	 
//	 strSQL =  "SELECT nvl(healthyamnt2('" + document.all('AppntNo').value +"','2','1'),0) from dual ";
    var arr2=easyExecSql(strSQL);
	if(arr2!=null){
		document.all('AppntSumHealthAmnt').value=arr2[0][0];
		//tSumAmnt = tSumAmnt + parseFloat(arr2[0][0]);
		tSumAmnt = Arithmetic(tSumAmnt,'+',arr2[0][0],2);
	}
	
	 var sqlid917165436="DSHomeContSql917165436";
var mySql917165436=new SqlClass();
mySql917165436.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917165436.setSqlId(sqlid917165436);//ָ��ʹ�õ�Sql��id
mySql917165436.addSubPara(document.all('AppntNo').value);//ָ������Ĳ���
strSQL=mySql917165436.getString();

	 
//	 strSQL =  "SELECT nvl(healthyamnt2('" + document.all('AppntNo').value +"','3','1'),0) from dual ";
    var arr3=easyExecSql(strSQL);
	if(arr3!=null){
		document.all('AppntMedicalAmnt').value=arr3[0][0];
		//tSumAmnt = tSumAmnt + parseFloat(arr3[0][0]);
	}
	
	var sqlid917165511="DSHomeContSql917165511";
var mySql917165511=new SqlClass();
mySql917165511.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917165511.setSqlId(sqlid917165511);//ָ��ʹ�õ�Sql��id
mySql917165511.addSubPara(document.all('AppntNo').value);//ָ������Ĳ���
strSQL=mySql917165511.getString();
	
//	strSQL =  "SELECT nvl(healthyamnt2('" + document.all('AppntNo').value +"','4','1'),0) from dual ";
    var arr4=easyExecSql(strSQL);
	if(arr4!=null){
		document.all('AppntAccidentAmnt').value=arr4[0][0];
		//tSumAmnt = tSumAmnt + parseFloat(arr4[0][0]);
		tSumAmnt = Arithmetic(tSumAmnt,'+',arr4[0][0],2);
	}
	
    document.all('AppntSumAmnt').value=tSumAmnt;
    
    //�ۼƱ��� �����������Ͳ����ڽ�����   
    var sqlid917165648="DSHomeContSql917165648";
var mySql917165648=new SqlClass();
mySql917165648.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917165648.setSqlId(sqlid917165648);//ָ��ʹ�õ�Sql��id
mySql917165648.addSubPara(document.all('AppntNo').value);//ָ������Ĳ���
strSQL=mySql917165648.getString();
    
//    strSQL =  "SELECT decode(sum(a_Prem),'','x',round(sum(a_Prem),2)) FROM"
//          + "(select (case"
//          + " when s_PayIntv = '1' then"
//          + " s_Prem/0.09"
//          + " when s_PayIntv = '3' then"
//          + " s_Prem/0.27"
//          + " when s_PayIntv = '6' then"
//          + " s_Prem/0.52"
//          + " when s_PayIntv = '12' then"
//          + " s_Prem"
//          + " end) a_Prem"          
//          + " FROM (select distinct payintv as s_PayIntv,"
//          + " sum(prem) as s_Prem"
//          + " from lcpol c where polno in(select polno "
//	          + " from lcpol a"
//	          + " where a.appntno = '"+document.all('AppntNo').value+"'"//��Ͷ������ΪͶ����ʱ����Ҫ�����ı����ۼ�
//	          + " )"
//          + " and c.payintv in ('1', '3','6','12')"
//          + " and c.uwflag not in ('1', '2', 'a')"
//          + " and c.appflag <> '4'" 
//          + " and not exists (select 'X'"
//	          + " from lccont"
//	          + " where ContNo = c.contno"
//	          + " and (uwflag in ('1', '2', 'a') or appflag='4' or (state is not null and substr(state,0,4) in ('1002', '1003'))  ))"
//          + " group by payintv))";
    var arr5=easyExecSql(strSQL);
	if(arr5!=null){	
	      // alert(arr5[0][0]);  
	      if(arr5[0][0]!='x')  
	          document.all('SumPrem').value=arr5[0][0];
	      else
	    {
	    	document.all('SumPrem').value ='0';
	    } 
	}	     
        
	//��ѯ���µĺ˱����ۣ����������ʾ
	var sqlid917165817="DSHomeContSql917165817";
var mySql917165817=new SqlClass();
mySql917165817.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917165817.setSqlId(sqlid917165817);//ָ��ʹ�õ�Sql��id
mySql917165817.addSubPara(ContNo);//ָ������Ĳ���
strSQL=mySql917165817.getString();
//	strSQL="select PassFlag,UWIdea from lccuwmaster where ContNo='"+ContNo+"'";
	var arr=easyExecSql(strSQL);
	//alert(strSQL);
	//alert(arr);
	//alert(arr[0][1]);
	if(arr!=null){
		document.all('uwState').value=arr[0][0];
		document.all('UWIdea').value=arr[0][1];
	}

	//�жϺ˱��ϱ�������ʾ����Ĳ�ͬdiv

  if(ReportFlag==""||ReportFlag==null){
		divSugUWResult.style.display="none";
		divUWResult.style.display="";
		fm.button1.style.display="";
		//tongmeng 2007-12-18 Add
		//ȥ���Դ˰�ť�Ŀ���
		//fm.button2.style.display="none";
  }
	else if(ReportFlag=="1"){
		divSugUWResult.style.display="none";
		divUWResult.style.display="";
		//���Ѱ����˱�����Ĭ��Ϊ�����¼�
		document.all('uwUpReport').value="4";
		//tongmeng 2007-12-18 Add
		//ȥ���Դ˰�ť�Ŀ���
		//fm.button1.style.display="none";
		//fm.button2.style.display=" 	���ѳб���������ͷ��ʾ�ڡ������������ӡ���������һ����";
	}
	else if(ReportFlag=="2"){
		divSugUWResult.style.display="none";
		divUWResult.style.display="";
		document.all('uwUpReport').value="0";
		fm.button1.style.display="";
		fm.button2.style.display="none";
	}

  //���а�ť��������ʾ����
  ctrlButtonDisabled(ContNo);
	/*
	//�ٱ��ж� yaory
	//������Ժ��Ѿ��ж���Ҫ�ٱ���ֱ�ӽ��ٱ���ť����Ϊ��
  strSql = "select missionprop16 from lwmission a where a.missionid='"+MissionID+"' and a.activityid='0000001100' and submissionid='"+SubMissionID+"'";
  var DistriMark = easyExecSql(strSql);//�ٱ���־
  if(DistriMark==1){
  	fm.ReDistribute.disabled="";
  	document.all('FBFlag').value="FB";
  }
  else{
//  	if(chkReinsureAmnt(ContNo)){
//  	  fm.ReDistribute.disabled="";	
//  	}
//    else{
//  	  fm.ReDistribute.disabled=true;
//    }
//	chkReinsureAmnt();
	fm.ReDistribute.disabled=true;
	document.all('FBFlag').value="Flag";
  }
  */
  //�ж��Ƿ��ٱ�
  initUpReportFlag();
  return true;
}

/*********************************************************************
 *  ��ʼ���Ƿ��ٱ�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function initUpReportFlag()
{
    //*******���㱻���˷��ձ����Ƿ���Ҫ�ٱ��ʱ� //2008-12-12 ln add****************
	//�ж��Ƿ���й��ٱ��ʱ�
      var sqlid917165928="DSHomeContSql917165928";
var mySql917165928=new SqlClass();
mySql917165928.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917165928.setSqlId(sqlid917165928);//ָ��ʹ�õ�Sql��id
mySql917165928.addSubPara(MissionID);//ָ������Ĳ���
mySql917165928.addSubPara(MissionID);//ָ������Ĳ���
var strSql=mySql917165928.getString();
      
//      var strSql = //"select * from lbmission a where a.missionid='"+MissionID+"' and a.activityid='0000001140' and submissionid='"+SubMissionID+"' and not exists(select 'X' from lbmission a where a.missionid='"+MissionID+"' and a.activityid='0000001140' and submissionid='"+SubMissionID+"')"; 
//      		         " select 1"+
//      		         " from lbmission a"+
//      		         " where a.missionid = '"+MissionID+"'"+
//      		         " and a.activityid = '0000001140'"+
//      		         " and not exists (select 'X'"+
//      		         " from lwmission a"+
//      		         " where a.missionid = '"+MissionID+"'"+
//      		         " and a.activityid = '0000001140')";
      var hasReinsured=easyExecSql(strSql);
      		//������й��ٱ��ʱ��������ж��ٱ����ձ���
	var sqlid917170044="DSHomeContSql917170044";
var mySql917170044=new SqlClass();
mySql917170044.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917170044.setSqlId(sqlid917170044);//ָ��ʹ�õ�Sql��id
mySql917170044.addSubPara(ContNo);//ָ������Ĳ���
var zstrSQL=mySql917170044.getString();
	
//	var zstrSQL = " select insuredno from lcinsured where contno='"+ContNo+"'";
    var zarr=easyExecSql(zstrSQL);    
   // prompt("",zstrSQL);
	for(var i =0 ;i<zarr.length;i++){	   
		var zinsuredno = zarr[i][0]; 
		var tInsuredSumLifeAmnt = 0;
	    
	    var sqlid917170139="DSHomeContSql917170139";
var mySql917170139=new SqlClass();
mySql917170139.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917170139.setSqlId(sqlid917170139);//ָ��ʹ�õ�Sql��id
mySql917170139.addSubPara(zinsuredno);//ָ������Ĳ���
zstrSQL=mySql917170139.getString();

//	    zstrSQL =  "SELECT nvl(healthyamnt2('" + zinsuredno +"','1','1'),0) from dual ";
	    var zarr1=easyExecSql(zstrSQL);
	   // prompt("",zstrSQL);
		if(zarr1!=null){
			tInsuredSumLifeAmnt=zarr1[0][0];
		}
		 var tInsuredSumHealthAmnt = 0;
		 var sqlid917170222="DSHomeContSql917170222";
var mySql917170222=new SqlClass();
mySql917170222.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917170222.setSqlId(sqlid917170222);//ָ��ʹ�õ�Sql��id
mySql917170222.addSubPara(zinsuredno);//ָ������Ĳ���
zstrSQL=mySql917170222.getString();

		 
//		 zstrSQL =  "SELECT nvl(healthyamnt2('" + zinsuredno +"','2','1'),0) from dual ";
	    var zarr2=easyExecSql(zstrSQL);
		if(zarr2!=null){
			tInsuredSumHealthAmnt=zarr2[0][0];
		}	 
		var tInsuredAccidentAmnt = 0;
		
		var sqlid917170259="DSHomeContSql917170259";
var mySql917170259=new SqlClass();
mySql917170259.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917170259.setSqlId(sqlid917170259);//ָ��ʹ�õ�Sql��id
mySql917170259.addSubPara(zinsuredno);//ָ������Ĳ���
zstrSQL=mySql917170259.getString();
		
//		zstrSQL =  "SELECT nvl(healthyamnt2('" + zinsuredno +"','4','1'),0) from dual ";
	    var zarr4=easyExecSql(zstrSQL);
		if(zarr4!=null){
			tInsuredAccidentAmnt=zarr4[0][0];
		}
		
			if((parseFloat(tInsuredSumLifeAmnt)-2300000>0
		       || (parseFloat(tInsuredSumHealthAmnt)-1200000>0)
		       || (parseFloat(tInsuredAccidentAmnt)-2500000>0)) && hasReinsured==null)
		 /*   alert("tInsuredSumLifeAmnt"+tInsuredSumLifeAmnt);
		    alert("tInsuredSumHealthAmnt"+tInsuredSumHealthAmnt);
		    alert("tInsuredAccidentAmnt"+tInsuredAccidentAmnt);
		    alert("hasReinsured"+hasReinsured);
			if((parseFloat(tInsuredSumLifeAmnt)-100000>0
		       || (parseFloat(tInsuredSumHealthAmnt)-100000>0)
		       || (parseFloat(tInsuredAccidentAmnt)-100000>0)) && hasReinsured==null) //������*/
		       {
		       		 alert("�˵����ٱ��ʱ���");
		       		 document.all('UpReporFlag').value = "Y";
		       		 break;
		       }
		   
	}	
	//end****************************************************
}

/*********************************************************************
 *  ��ʼ���Ƿ��ϱ��˱�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function initSendTrace()
{
	var tSql = "";

	var sqlid917170447="DSHomeContSql917170447";
var mySql917170447=new SqlClass();
mySql917170447.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917170447.setSqlId(sqlid917170447);//ָ��ʹ�õ�Sql��id
mySql917170447.addSubPara(ContNo);//ָ������Ĳ���
mySql917170447.addSubPara(ContNo);//ָ������Ĳ���
tSql=mySql917170447.getString();
	
//	tSql = "select sendflag,uwflag,uwidea from lcuwsendtrace where 1=1 "
//					+ " and otherno = '"+ContNo+"'"
//					+ " and othernotype = '1'"
//					+ " and uwno in (select max(uwno) from lcuwsendtrace where otherno = '"+ContNo+"')"
//					;
 //prompt('',tSql);
	turnPage.strQueryResult  = easyQueryVer3(tSql, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
  //�ж��Ƿ��ѯ�ɹ�
  if (turnPage.strQueryResult) {
		document.all('SendFlag').value =arrSelected[0][0];
		document.all('SendUWFlag').value =arrSelected[0][1];
		document.all('SendUWIdea').value =arrSelected[0][2];
		DivLCSendTrance.style.display="";
	}

    divUWSave.style.display = "";
    divUWAgree.style.display = "none";

    /**
	if(document.all('SendFlag').value == '0')
	{
		divUWSave.style.display = "";
		divUWAgree.style.display = "none";
	}
	else if(document.all('SendFlag').value == '1')
	{
		divUWAgree.style.display = "";
		divUWSave.style.display = "none";
		//divUWButton2.style.display = "none";
		tSql = "select sugpassflag,suguwidea from lccuwmaster where 1=1 "
					+ " and contno = '"+ContNo+"'"
					;
		turnPage.strQueryResult  = easyQueryVer3(tSql, 1, 0, 1);
		arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);

		document.all('uwState').value = arrSelected[0][0];
		document.all('UWIdea').value = arrSelected[0][1];
	}
    **/
}

/*********************************************************************
 *  ��ʼ���Ƿ��ϱ��˱� new
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function initUpper()
{
	var tSql = "";

	var sqlid917170605="DSHomeContSql917170605";
var mySql917170605=new SqlClass();
mySql917170605.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917170605.setSqlId(sqlid917170605);//ָ��ʹ�õ�Sql��id
mySql917170605.addSubPara(ContNo);//ָ������Ĳ���
mySql917170605.addSubPara(ContNo);//ָ������Ĳ���
tSql=mySql917170605.getString();
	
//	tSql = "select sendflag,uwflag,uwidea,upreason,uwcode from lcuwsendtrace where 1=1 "
//					+ " and otherno = '"+ContNo+"'"
//					+ " and othernotype = '1'"
//					+ " and uwno in (select max(uwno) from lcuwsendtrace where otherno = '"+ContNo+"')"
//					;
 //prompt('',tSql);
	turnPage.strQueryResult  = easyQueryVer3(tSql, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
  //�ж��Ƿ��ѯ�ɹ�
  if (turnPage.strQueryResult) {
		document.all('SendFlag').value =arrSelected[0][0];
		document.all('SendUWFlag').value =arrSelected[0][1];
		document.all('SendUWIdea').value =arrSelected[0][2];
		document.all('UpperReason').value =arrSelected[0][3];
		document.all('UpperUwUser').value =arrSelected[0][4];
		//DivLCSendTrance.style.display="";
		DivUpper.style.display="";
	}

    divUWSave.style.display = "";
    divUWAgree.style.display = "none";

}

/*********************************************************************
 *  ��֪����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function ImpartToICD()
{
  var cContNo = fm.ContNo.value;//PolAddGrid.getRowColData(PolAddGrid.getSelNo()-1, 1);
  var PrtNo = fm.PrtNo.value;

  if (cContNo!="" && PrtNo!="") {
  	window.open("../uw/ImaprtToICDMain.jsp?ContNo="+cContNo+"&PrtNo="+PrtNo, "window1",sFeatures);
  }
  else {
  	alert("����ѡ�񱣵�!");
  }
}
//�������֪ͨ��
function SendIssue()
{
  cContNo = fm.ContNo.value;

  if (cContNo != "")
  {
	//manuchk();
	 var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
     var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
     //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	 var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	  var cMissionID =fm.MissionID.value;

	  var cPrtNo = fm.PrtNo.value;

	  fm.PrtNoHide.value = cPrtNo ;

	  var sqlid917170727="DSHomeContSql917170727";
var mySql917170727=new SqlClass();
mySql917170727.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917170727.setSqlId(sqlid917170727);//ָ��ʹ�õ�Sql��id
mySql917170727.addSubPara(cContNo);//ָ������Ĳ���
strsql=mySql917170727.getString();

	  
//	  strsql = "select * from lcissuepol where contno = '" +cContNo+ "' and backobjtype = '3' and (state = '0' or state is null)";

	  turnPage.strQueryResult = easyQueryVer3(strsql, 1, 1, 1);
    //�ж��Ƿ��ѯ�ɹ�
    if (!turnPage.strQueryResult) {
    	showInfo.close();
     	alert("���������µ������֪ͨ��");
        fm.SubNoticeMissionID.value = "";
        return ;
    }

    var sqlid917170835="DSHomeContSql917170835";
var mySql917170835=new SqlClass();
mySql917170835.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917170835.setSqlId(sqlid917170835);//ָ��ʹ�õ�Sql��id
mySql917170835.addSubPara(cContNo);//ָ������Ĳ���
mySql917170835.addSubPara(cPrtNo);//ָ������Ĳ���
strsql=mySql917170835.getString();
    
//    strsql = "select submissionid from lwmission where missionprop2 = '" +cContNo+ "'and missionprop1 = '"+cPrtNo+"' and activityid = '0000001022'";
    turnPage.strQueryResult = easyQueryVer3(strsql, 1, 1, 1);

        if (!turnPage.strQueryResult) {
    	showInfo.close();
     	alert("���������µ������֪ͨ��");
        fm.SubNoticeMissionID.value = "";
        return ;
    }

    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

    fm.SubNoticeMissionID.value = turnPage.arrDataCacheSet[0][0];
    fm.action = 'UWManuSendIssueCHK.jsp';
    document.getElementById("fm").submit();
  }
  else
  {
  	alert("����ѡ�񱣵�!");
  }
}
//���������
function QuestBack()
{
		cContNo = fm.ContNo.value;  //��������

  if (cContNo != "")
  {
  	var tSelNo = PolAddGrid.getSelNo()-1;
  	QuestQueryFlag[tSelNo] = 1 ;
	window.open("./UWManuQuestQMain.jsp?ContNo="+cContNo+"&Flag="+cflag,"window2",sFeatures);
  }
  else
  {
  	alert("����ѡ�񱣵�!");
  }
}

function SendBankFlagChange()
{
	//var ActivityID = "0000001100";	
	var ActivityID = fm.ActivityID.value; // modify by lzf 2013-04-01
	//alert("ActivityID   "+ActivityID);
  var PrtNo = document.all.PrtNo.value;
	var NoType = "1";
	if(PrtNo == null || PrtNo == "")
	{
		alert("Ͷ������Ϊ�գ�");
		return;
	}

	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ PrtNo;
	var newWindow = OpenWindowNew("../uw/UWBankSendFlagFrame.jsp?Interface=../uw/UWBankSendFlagInput.jsp" + varSrc,"���з��̷�ʽ�޸�","left");
}

/*********************************************************************
 *  ��ʾ�˱����𡢺˱�ʦ
 *  add by xutao 2005-04-13
 *
 *********************************************************************
 */
function showUpDIV(){

    if (fm.uwUpReport.value == '1' || fm.uwUpReport.value == '3')
    {
        //��ʾ
        //divUWup.style.display="";

    }
    else if (fm.uwUpReport.value == '2' || fm.uwUpReport.value == '4')
    {   //����
        //divUWup.style.display="none";
        //fm.uwPopedom.value = "";
        //fm.uwPopedomname.value = "";
        //fm.uwPer.value = "";
        //fm.uwPername.value = "";
    }

}
/*********************************************************************
 *  ��ʾ���շ���Ϣ
 *  add by heyq 2005-04-13
 *
 *********************************************************************
 */
function showTempFee()
{
  var Prtno =document.all('PrtNo').value;
	var AppntNo = document.all('AppntNo').value;
	var AppntName = document.all('AppntName').value;

  window.open("../uw/UWTempFeeQry.jsp?Prtno=" + Prtno + "&AppntNo=" + AppntNo + "&AppntName=" +AppntName,"",sFeatures);
}

function SendAllNotice()
{

	window.open("./SendAllNoticeMain.jsp?ContNo="+fm.ContNo.value+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID,"window1",sFeatures);
}

//tongmeng 2007-11-01 add
//����MS���ź˱�֪ͨ��Ĺ���
function SendAllNotice_MS()
{
	  if(!checkAutoUWPass('���ͺ˱�֪ͨ��'))
	  {
	  	return false;
	  }
	  
	  //SYY BEGIN
	  var sql = "select uwstate from lccuwmaster where contno = '"+ContNo+"' and uwstate in ('4','6','7')";
	  var arr = easyExecSql(sql);
	  if (arr) {
		  alert("�ú�ͬ��ǰ����"+arr[0][0]+"״̬,�������ͺ˱�֪ͨ��");   
	      return false;   
	  }
	  //SYY END
	  
	  /*
	  if(!checkUWState('6',MissionID,SubMissionID,'���ͺ˱�֪ͨ��'))
	 	{
	 	 return false;
	 	}
	 	 if(!checkUWState('4',MissionID,SubMissionID,'���ͺ˱�֪ͨ��'))
	 	{
	 	 return false;
	 	}
	 	 if(!checkUWState('7',MissionID,SubMissionID,'���ͺ˱�֪ͨ��'))
	 	{
	 	 return false;
	 	}
	 	*/
	 if(!checkUWOperate(MissionID,SubMissionID,'���ͺ˱�֪ͨ��'))
	 	{
	 	 return false;
	 	}
	 	//tongmeng 2009-03-10 add
	 	//���±�û������,���������м�˱�����
	 	if(!checkUWNotPad(fm.ContNo.value,'���ͺ˱�֪ͨ��'))
	 	{
	 		return false;
	 	}
		window.open("./MSSendAllNoticeMain.jsp?ActivityID="+fm.ActivityID.value+"&ContNo="+fm.ContNo.value+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID,"window1",sFeatures);
}

//��ѯ�����
function queryHealthReportResult(){
	var tFlag = "1"; 	
	var tUWFlag = "1";
	//09-06-11��������µ�ͬʱɨ��������϶�ɨ������Ӱ����Ĵ���
	//�µ�ʱ��������ϡ��˹��˱�ʱҲ��������ϣ������˹��˱�ʱΪ׼
	var sqlid917171646="DSHomeContSql917171646";
var mySql917171646=new SqlClass();
mySql917171646.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917171646.setSqlId(sqlid917171646);//ָ��ʹ�õ�Sql��id
mySql917171646.addSubPara(PrtNo);//ָ������Ĳ���
var tSubtypeSql=mySql917171646.getString();
	
//	var tSubtypeSql = "select docid from es_doc_main where doccode='"+PrtNo+"' and subtype='UN103'";
	var tSubType = "";
	var QueryType = "";
	var DocID=easyExecSql(tSubtypeSql);
	if(DocID!=null){
		tSubType = "TB1013";
		QueryType = "1";
	}else{
		tSubType = "TB1020";
		QueryType = "0";
		
		var sqlid917171728="DSHomeContSql917171728";
var mySql917171728=new SqlClass();
mySql917171728.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917171728.setSqlId(sqlid917171728);//ָ��ʹ�õ�Sql��id
mySql917171728.addSubPara(PrtNo);//ָ������Ĳ���
var strSQL=mySql917171728.getString();
		
		DocID = easyExecSql(strSQL);
	}
	
 window.open("./UWManuHealthQMain.jsp?ContNo="+ContNo+"&PrtNo="+PrtNo+"&Flag="+tFlag+"&UWFlag="+tUWFlag+"&QueryType="+QueryType+"&DocID="+DocID,"window1",sFeatures);
}


//��ѯ�������
function queryRReportResult(){

	var tContNo = fm.ContNo.value;
	var tPrtNo = fm.PrtNo.value;
	var tFlag = "1"; 

 window.open("./RReportQueryMain.jsp?ContNo="+tContNo+"&PrtNo="+tPrtNo+"&Flag="+tFlag+"&QueryFlag=1","window1",sFeatures);

}

//�����ۼ�
function amntAccumulate(){
	window.open("./AmntAccumulateMain.jsp?CustomerNo="+document.all('AppntNo').value,"window1",sFeatures);
}

//�ѳб���ѯ
function queryProposal(){

	window.open("./ProposalQueryMain.jsp?CustomerNo="+document.all('AppntNo').value,"window1",sFeatures);
}


//δ�б���ѯ
function queryNotProposal(){

	window.open("./NotProposalQueryMain.jsp?CustomerNo="+document.all('AppntNo').value,"window1",sFeatures);
}

//���������ѯ
function queryClaim(){

	window.open("./ClaimQueryMain.jsp?CustomerNo="+document.all('AppntNo').value,"window1",sFeatures);
}

//������ȫ��ѯ
function queryEdor(){

	window.open("./EdorQueryMain.jsp?CustomerNo="+document.all('AppntNo').value,"window1",sFeatures);
}

//�ͻ�Ʒ�ʹ���
function CustomerQuality() {

  if (ContNo!="" && PrtNo!="") {
  	window.open("../uw/UWCustomerQualityMain.jsp?ContNo="+ContNo+"&PrtNo="+PrtNo, "window1",sFeatures);
  }
}

//ҵ��ԱƷ�ʹ���
function AgentQuality() {

  if (ContNo!="" && PrtNo!="") {
  	window.open("../uw/UWAgentQualityMain.jsp?ContNo="+ContNo+"&PrtNo="+PrtNo, "window1",sFeatures);
  }
}
//���ҽԺƷ�ʹ���
function HospitalQuality() {

  if (ContNo!="" && PrtNo!="") {
  	window.open("../uw/UWHospitalQualityMain.jsp?ContNo="+ContNo+"&PrtNo="+PrtNo, "window1",sFeatures);
  }
}


//������֪��ѯ
function queryHealthImpart(){

    //alert(document.all('AppntNo').value);
	window.open("./HealthImpartQueryMain.jsp?CustomerNo="+document.all('AppntNo').value,"window1",sFeatures);
}

//�����˽�����֪��ѯ
function queryInsuredHealthImpart(){

	window.open("./HealthImpartQueryMain.jsp?CustomerNo="+document.all('InsuredNo').value,"window1",sFeatures);
}
//����������ѯ
function QueryRecord(){

	window.open("./RecordQueryMain.jsp?ContNo="+document.all('ContNo').value,"window1",sFeatures);
}

function showBeforeHealthQ()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  var cContNo=fm.ContNo.value;
  var cAppntNo = fm.AppntNo.value;


	window.open("../uw/UWBeforeHealthQMain.jsp?ContNo="+cContNo+"&CustomerNo="+cAppntNo+"&type=1","",sFeatures);

showInfo.close();

}

//�˱���ѯ
function UWQuery(){

	window.open("./UWQueryMain.jsp?ContType=1&ContNo="+document.all('ContNo').value,"window1",sFeatures);
}
//ǿ���˹��˱�ԭ���ѯ
function ForceUWReason(){

	window.open("./ForceUWReason.jsp?ContNo="+document.all('ContNo').value,"window1",sFeatures);
}
/*********************************************************************
 *  �޸ı�����Ч����
 *  add by zhangxing 2005-06-28
 *
 *********************************************************************
 */
 function ChangeCvalidate()
 {
	//SYY BEGIN
	    var sql = "select uwstate from lccuwmaster where contno = '"+ContNo+"' and uwstate in ('4')";
		var arr = easyExecSql(sql);
		if (arr) {
			 alert("�ú�ͬ��ǰ����"+arr[0][0]+"״̬,�������޸ı�����Ч����");   
		     return false;   
		}
	//SYY END	
		
 	 //tongmeng 2009-02-06 add
 	 //4״̬�������޸ı�����Ч����
		/*
 		if(!checkUWState('4',MissionID,SubMissionID,'�޸ı�����Ч����'))
	 	{
	 	 	return false;
	 	}
	 	*/
 	var tContNo = fm.ContNo.value;
 	var sqlid917171825="DSHomeContSql917171825";
var mySql917171825=new SqlClass();
mySql917171825.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917171825.setSqlId(sqlid917171825);//ָ��ʹ�õ�Sql��id
mySql917171825.addSubPara(PrtNo);//ָ������Ĳ���
var strSql2=mySql917171825.getString();

 	
// 	var strSql2="select missionprop5 from lbmission where activityid='0000001099' and missionprop1='"+PrtNo+"'";
    var crrResult = easyExecSql(strSql2);
    var SubType="";
    if(crrResult!=null){
      SubType = crrResult[0][0];
    }
    
    if(SubType==null||SubType=='')
    {
    	SubType = '01';
    }

 window.open("./UWChangeCvalidateMain.jsp?ContNo="+tContNo+"&SubType=TB10"+SubType+"&prtNo="+tContNo,"window1",sFeatures);

}


/*********************************************************************
 *  �ٱ��б�¼��
 *  add by zhangxing 2005-06-28
 *
 *********************************************************************
 */
 function UWUpReport()
 {
 		if(!checkAutoUWPass('�ٱ��ʱ�'))
	  {
	  	return false;
	  }
 		
 		//SYY BEGIN
 		 var sql = "select uwstate from lccuwmaster where contno = '"+ContNo+"' and uwstate in ('4','6')";
 		var arr = easyExecSql(sql);
 		if (arr) {
 			 alert("�ú�ͬ��ǰ����"+arr[0][0]+"״̬,�������޸ı�����Ч����");   
 		     return false;   
 		}
 		//SYY END
 		
 		/*
	  if(!checkUWState('6',MissionID,SubMissionID,'�ٱ��ʱ�'))
	 	{
	 	 return false;
	 	}
	 	//tongmeng 2009-02-06 add
	 	//4״̬���������ٱ�
	 	if(!checkUWState('4',MissionID,SubMissionID,'�ٱ��ʱ�'))
	 	{
	 	 return false;
	 	}
	 	*/
	 if(!checkUWOperate(MissionID,SubMissionID,'�ٱ��ʱ�'))
	 	{
	 	 return false;
	 	}
	 	//tongmeng 2009-03-10 add
	 	//���±�û������,���������м�˱�����
	 	if(!checkUWNotPad(fm.ContNo.value,'�ٱ��ʱ�'))
	 	{
	 		return false;
	 	}
 		var tContNo = fm.ContNo.value;
 		var tMissionID = fm.MissionID.value;
 		var tSubMissionID = fm.SubMissionID.value;
 		var tPrtNo = fm.PrtNo.value;		
           
 window.open("./UWManuUpReportMain.jsp?ContNo="+tContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&PrtNo="+tPrtNo,"window1",sFeatures);
 }
 
 
 /*********************************************************************
 *  �ٱ���Ϣ�ظ�
 *  add by zhangxing 2005-08-10
 *
 *********************************************************************
 */
function showUpReportReply()
{
	var tContNo = fm.ContNo.value;
	var tMissionID = fm.MissionID.value;
	var tSubMissionID = fm.SubMissionID.value;
	var tFlag = "1";
	var tPrtNo = fm.PrtNo.value;
	
	window.open("./UWManuUpReportReplyMain.jsp?ContNo="+tContNo+"&PrtNo="+PrtNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&Flag="+tFlag,"window1",sFeatures);
}

//�ٱ������ۼƵ��ж�
function chkReinsureAmnt()
{
//	var strSql = "";
//	var life=0;//�����ٱ������ۼ�
//	var acci=0;//�����ٱ������ۼ�
//	var nur=0;//�ؼ��ٱ������ۼ�
//	var arrAddPrem;
//	var addremark=0;//�ӷ�����
//
//	//ͨ��ContNo��ѯ�����µ����б����˵Ŀͻ���
//	strSql="select insuredno from lcinsured where contno='"+aContNo+"' order by sequenceno";
//	var arrInsruedNo=easyExecSql(strSql);
//	
//	//��ѯ���ű������������͡�
//	strSql="select risksortvalue from lmrisksort where risksorttype = '3' and riskcode = (select riskcode from lcpol where mainpolno = polno and contno = '"+aContNo+"')";
//	var brrRisksort=easyExecSql(strSql);
//	//alert("brrRisksort[0]="+brrRisksort[0]);
//	if(arrInsruedNo!=null)
//	{
//		//ѭ�����б������ж��ٱ������ۼ�
//		for(var i=0; i<arrInsruedNo.length; i++)
//		{
//			//�ؼ��ٱ������ۼ�
//			strSql="select healthyamntfb('"+arrInsruedNo[i]+"','','2') from dual";
//			nur=easyExecSql(strSql);
//			//���ش󼲲��յ�Σ�ձ����120��Ԫ,��Ҫ�����ٱ�
//			if(nur>1200000&&brrRisksort[0]==2)  { return true;}
//			//�ۼ��ش󼲲��յ�Σ�ձ����10��Ԫ������10���Ҷ��������ʴ��ڵ���100��ʱ,��Ҫ�����ٱ�
//			if(nur>100000&&brrRisksort[0]==2)
//			{
//				//�ӷ�����
//				strSql= " select nvl(sum(lcprem.suppriskscore),0), nvl(sum(lcprem.secinsuaddpoint),0)" 
//					  +  " from lcprem where polno in "
//					  +" (" 
//					  + " select polno from lcpol"
//					  + " where contno = '"+aContNo+"'"
//					  + " and polno = mainpolno "
//					  + " and exists (select 'x' from lmrisksort where riskcode = lcpol.riskcode and risksorttype = '2')"
//					  + " )" 
//					  + " and lcprem.payplantype = '01'" 
//					  + " and (lcprem.suppriskscore >=100 or lcprem.secinsuaddpoint >=100)";
//				arrAddPrem=easyExecSql(strSql);
//			    //��һ�ڶ������˷ֱ�ȡ��ͬ��ֵ
//			    if(i==0) { addremark=arrAddPrem[0][0]; }
//			    if(i==1) { addremark=arrAddPrem[0][1]; }
//				if(addremark>=100)	{ return true; }
//			}
//		
//			//�����ٱ������ۼ�
//			strSql="select healthyamntfb('"+arrInsruedNo[i]+"','','1') from dual";
//			life=easyExecSql(strSql);
//			//�����ٱ������ۼ�
//			strSql="select healthyamntfb('"+arrInsruedNo[i]+"','','3') from dual";
//			acci=easyExecSql(strSql);
//			//����+���� �ٱ������ۼƳ���300��Ԫ,��Ҫ�����ٱ�
//			if((parseInt(life)+parseInt(acci))>3000000&&(brrRisksort[0]==1||brrRisksort[0]==4))
//			{
//				return true;
//			}
//			//����+���� �ٱ������ۼƳ���300��Ԫ,�Ҷ��������ʴ��ڵ���100��ʱ,��Ҫ�����ٱ�
//			if((parseInt(life)+parseInt(acci))>500000&&(brrRisksort[0]==1||brrRisksort[0]==4))
//			{
//			    //�ӷ�����
//			    strSql=" select nvl(sum(lcprem.suppriskscore),0), nvl(sum(lcprem.secinsuaddpoint),0)" 
//						+" from lcprem where polno in "
//						+" ("
//						+" select polno from lcpol where contno = '"+aContNo+"'" 
//						+" and exists (select 'x' from lmrisksort where riskcode = lcpol.riskcode and risksorttype in('1','3'))"
//						+" )" 
//						+ " and lcprem.payplantype = '01'" 
//						+  " and (lcprem.suppriskscore >=100 or lcprem.secinsuaddpoint >=100)";
//			    arrAddPrem=easyExecSql(strSql);
//			    //��һ�ڶ������˷ֱ�ȡ��ͬ��ֵ
//			    if(arrAddPrem!=null)
//			    {
//					if(i==0) { addremark=arrAddPrem[0][0]; }
//					if(i==1) { addremark=arrAddPrem[0][1]; }
//					if(addremark>=100) { return true; }
//			    }
//			}
//		}//end of for 
//	}//end of if
	fm.action = "./ManuUWFB.jsp";
	document.getElementById("fm").submit();                  	
	
}
//�ύ�����,���������ݷ��غ�ִ�еĲ���
function CheckFB(FlagStr)
{
  if (FlagStr == "Fail" )
  {
	//fm.ReDistribute.disabled=true;	//����Ҫ�ٷ�
  }
  else
  {
	//fm.ReDistribute.disabled="";	//��Ҫ�ٷ�
  }
}
/*********************************************************************
 *  ���ظ��˽ڵ�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *  �޸��ˣ�����
 *  ʱ  �䣺2005-10-12
 *********************************************************************
 */
function returnApprove()
{

    var sqlid917172000="DSHomeContSql917172000";
var mySql917172000=new SqlClass();
mySql917172000.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917172000.setSqlId(sqlid917172000);//ָ��ʹ�õ�Sql��id
mySql917172000.addSubPara(fm.MissionID.value);//ָ������Ĳ���
mySql917172000.addSubPara(fm.SubMissionID.value);//ָ������Ĳ���
var tSql=mySql917172000.getString();
    
//    var tSql = " select * from lwmission where  1=1 "
//             + " and activityid in ('0000001106','0000001107','0000001108','0000001111','0000001112','0000001113','0000001130','0000001280','0000001290','0000001300','0000001140') "
//             + " and missionid = '"+fm.MissionID.value+"'"
//             + " and submissionid = '"+fm.SubMissionID.value+"'";

    var arr = easyExecSql( tSql );
   
    if (arr) 
    {
         alert("����֪ͨ��û�лظ�, �������ظ���")   
         return ;   
    }
    
    //------------------���븴�˷���ϵͳ��ʾ
    //------------------EditBy:Chenrong
    if (window.confirm("ȷ��Ҫ���ظ�����") == true)
    {
        var i = 0;
        var showStr="���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
        //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    
    
        fm.action = "../uw/ManuUWReturnApproveSave.jsp";
        document.getElementById("fm").submit(); //�ύ
    }

}


/*********************************************************************
 *  ִ�з��ظ��˽ڵ��Ķ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *  �޸��ˣ�����
 *  ʱ  �䣺2005-10-12
 *********************************************************************
 */
function afterReturnApprove(FlagStr,content)
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
        InitClick();
    }


}



var cacheWin=null;

function quickGetName(strCode, showObjc, showObjn) {
	showOneCodeNameOfObjEx(strCode,showObjc,showObjn);
}

//�����˹��˱����水ť��������ʾ
function ctrlButtonDisabled(tContNo){
//alert('1');
 //�޸�Ϊ��Function��ֱ��ִ�к��������ؽ������(��ά����:��һ��Ϊ��ť���ƣ��ڶ���Ϊdisabled����)��
    try
    {
	    var sqlid917172111="DSHomeContSql917172111";
var mySql917172111=new SqlClass();
mySql917172111.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917172111.setSqlId(sqlid917172111);//ָ��ʹ�õ�Sql��id
mySql917172111.addSubPara(tContNo);//ָ������Ĳ���
var strSQL=mySql917172111.getString();

//alert(strSQL);	    
	    var arrStr = easyExecSql(strSQL);
//	    alert(arrStr.length);	
	    if(arrStr!=null)
	    {
	    	for(var i=0; i<arrStr.length; i++)
	    	{
	    		//alert(arrStr[i][0]+":"+arrStr[i][1]+":"+arrStr[i][2]);
	    		try {
	    			if(arrStr[i][1]==0)
	    			{
	    				eval("document.all('"+arrStr[i][0]+"').disabled='true'");
	    			}
	    			else
	    			{  				
	    				eval("document.all('"+arrStr[i][0]+"').disabled=''");
	    			}
	    		} 
	    		catch(e) {}
	    		 
	    	}
	    }
    }
    catch(ex)
    {
    
    }     	
}

//tongmeng 2007-11-08 add
//�˹��˱����������¼��
//�����¼��
function QuestInput(){
    cContNo = fm.ContNo.value;  //��������
    
    //SYY
    var sql = "select uwstate from lccuwmaster where contno = '"+ContNo+"' and uwstate in ('4','6')";
	var arr = easyExecSql(sql);
	if (arr) {
		 alert("�ú�ͬ��ǰ����"+arr[0][0]+"״̬,�����������¼��");   
	     return false;   
	}
	 /*
    if(!checkUWState('6',MissionID,SubMissionID,'�����¼��'))
	 {
	 	 return false;
	 }
	 //tongmeng 2009-02-06 add
	 //4״̬�������������¼��
	 if(!checkUWState('4',MissionID,SubMissionID,'�����¼��'))
	 {
	 	 return false;
	 }*/
	 
     window.open("../uw/MSQuestInputMain.jsp?ContNo="+cContNo+"&Flag=6&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID,"window1",sFeatures);
}

//tongmeng 2007-12-11 add
//���ӷ��ص������ع���
function returnPublicPool()
{
	 if (window.confirm("ȷ��Ҫ���ص���������") == true)
    {
        var i = 0;
        var showStr="���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
        //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    
    
        fm.action = "../uw/ManuUWReturnPublicPoolSave.jsp";
        document.getElementById("fm").submit(); //�ύ
    }	
}

//ln 2008-09-27 add
//���Ӻ˱��ȴ�����
function UWWait()
{
	if(!checkAutoUWPass('�˱��ȴ�'))
	  {
	  	return false;
	  }
	
	
	  //tongmeng 2009-02-06 add
	  //4״̬������˱��ȴ�
	//SYY 
	var sql = "select uwstate from lccuwmaster where contno = '"+ContNo+"' and uwstate in ('4','6','7')";
	var arr = easyExecSql(sql);
	if (arr) {
		 alert("�ú�ͬ��ǰ����"+arr[0][0]+"״̬,������˱��ȴ�");   
	     return false;   
	}
	
	/*
	 if(!checkUWState('4',MissionID,SubMissionID,'�˱��ȴ�'))
	 {
	 	 return false;
	 }
	 if(!checkUWState('6',MissionID,SubMissionID,'�˱��ȴ�'))
	 {
	 	 return false;
	 }
	 if(!checkUWState('7',MissionID,SubMissionID,'�˱��ȴ�'))
	 {
	 	 return false;
	 }
	 */
	 
	 if(!checkUWOperate(MissionID,SubMissionID,'�˱��ȴ�'))
	 	{
	 	 return false;
	 	}
	 	//tongmeng 2009-03-10 add
	 	//���±�û������,���������м�˱�����
	 	if(!checkUWNotPad(fm.ContNo.value,'�˱��ȴ�'))
	 	{
	 		return false;
	 	}
		window.open("./WaitReasonMain.jsp?ContNo="+fm.ContNo.value+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&Type=1","window11",sFeatures);

}

//ln 2008-09-27 test
function showUWReport()
{
	window.open("./UWReportMain.jsp?ContNo="+fm.ContNo.value+"&NoType=1","window5",sFeatures);

}

/**
* У���Զ��˱��Ƿ��������.
*/
function checkAutoUWPass(tMessage)
{
	//tongmeng 2009-02-06 modify
	//�޸�Ϊʹ��һ��SQL
	var sqlid917172330="DSHomeContSql917172330";
var mySql917172330=new SqlClass();
mySql917172330.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917172330.setSqlId(sqlid917172330);//ָ��ʹ�õ�Sql��id
mySql917172330.addSubPara(fm.ContNo.value);//ָ������Ĳ���
mySql917172330.addSubPara(fm.ContNo.value);//ָ������Ĳ���
mySql917172330.addSubPara(fm.ContNo.value);//ָ������Ĳ���
var tSQL=mySql917172330.getString();
	
//	var tSQL = "select '1'  from LCUWError where contno='"+fm.ContNo.value+"' "
//	         + " and (sugpassflag is null or sugpassflag='N') "
//	         + " union "
//	         + " select '1' from LCCUWError where contno='"+fm.ContNo.value+"' "
//	         + " and (sugpassflag is null or sugpassflag='N') "
//	         + " union "
//	         + " select '1' from LCIndUWError where contno='"+fm.ContNo.value+"' "
//	         + " and (sugpassflag is null or sugpassflag='N') ";
	
	var arr = easyExecSql( tSQL );
	//alert(arr);
   if (arr) 
    {
         alert("�����Զ��˱���Ϣû���������, ������"+tMessage)   
         return false;   
    }
    /*
  var tSQL1 = "select '1' from LCCUWError where contno='"+fm.ContNo.value+"' and (sugpassflag is null or sugpassflag='N') ";
	var arr1 = easyExecSql( tSQL1 );
   if (arr1) 
    {
         alert("�����Զ��˱���Ϣû���������, ������"+tMessage)   
         return false;   
    }
  var tSQL2 = "select '1' from LCIndUWError where contno='"+fm.ContNo.value+"' and (sugpassflag is null or sugpassflag='N') ";
	var arr2 = easyExecSql( tSQL1 );
   if (arr2) 
    {
         alert("�����Զ��˱���Ϣû���������, ������"+tMessage)   
         return false;   
    }
    */
    return true;
}

//tongmeng 2009-02-07 add
//�м�˱�������ҪУ����±��Ƿ�Ϊ��.


function checkUWState(tState,tMissionID,tSubMissionID,tMessage,tContNo)
{
	/*
	var sqlid917172543="DSHomeContSql917172543";
var mySql917172543=new SqlClass();
mySql917172543.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917172543.setSqlId(sqlid917172543);//ָ��ʹ�õ�Sql��id
mySql917172543.addSubPara(tMissionID);//ָ������Ĳ���
mySql917172543.addSubPara(tSubMissionID);//ָ������Ĳ���
mySql917172543.addSubPara(tState);//ָ������Ĳ���
var tSQL=mySql917172543.getString();
	
//	var tSQL = "select '1' from lwmission where missionid = '"+tMissionID+"' "
//	         + " and SubMissionID = '"+tSubMissionID+"' "
//	         + " and MissionProp18='"+tState+"'"
//	         + " and activityid='0000001100' ";
	var arr = easyExecSql( tSQL );
	 if (arr) 
   {
         alert("�ú�ͬ��ǰ����"+tState+"״̬,������"+tMessage);   
         return false;   
   }
	 return true;
	 */
} 

//tongmeng 2009-03-10 add
//������±�û������,����������м�˱�����
function checkUWNotPad(tContNo,tMessage)
{
	var sqlid917172642="DSHomeContSql917172642";
var mySql917172642=new SqlClass();
mySql917172642.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917172642.setSqlId(sqlid917172642);//ָ��ʹ�õ�Sql��id
mySql917172642.addSubPara(tContNo);//ָ������Ĳ���
var tSQL=mySql917172642.getString();
	
//	var tSQL = "select count(*) from lwnotepad where "
//	         + " otherno = '"+tContNo+"' and activityid='0000001100' "
	var arr = easyExecSql( tSQL );
	 if (arr&&arr[0][0]<=0) 
   {
         alert("�ú�ͬ��ǰ���±���û�м�¼,������"+tMessage);   
         return false;   
   }
	 return true;
} 


//У��˱�Ա�Ƿ��в���Ȩ��
function checkUWOperate(tMissionID,tSubMissionID,tMessage)
{
	var sqlid917173143="DSHomeContSql917173143";
var mySql917173143=new SqlClass();
mySql917173143.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917173143.setSqlId(sqlid917173143);//ָ��ʹ�õ�Sql��id
mySql917173143.addSubPara(tMissionID);//ָ������Ĳ���
mySql917173143.addSubPara(tSubMissionID);//ָ������Ĳ���
mySql917173143.addSubPara(operator);//ָ������Ĳ���
var tSQL=mySql917173143.getString();

	
//	var tSQL = "select '1' from lwmission where missionid = '"+tMissionID+"' "
//	         + " and SubMissionID = '"+tSubMissionID+"' "
//	         + " and activityid='0000001100' "
//	         + " and exists (select 1 from lduwuser where usercode='"+ operator 
//	         	+ "' and uwtype='1' and uwpopedom<MissionProp12 and (UWProcessFlag is null or UWProcessFlag='N')) " ;
	//prompt('',tSQL);         
	var arr = easyExecSql( tSQL );
	 if (arr) 
   {
         alert("��ǰ�˱�Ա�˱����𲻹���������"+tMessage+"��");   
         return false;   
   }
	 return true;
}

//�Զ��˱����İ�ť�ύ����
function submitFormUW()
{
  var i = 0;
  //var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  //var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  fm.action="./UWErrSave.jsp";
  fm.AutoUWButton.disabled=true;
  if(!checkChk())
	{
		fm.AutoUWButton.disabled=false;
		return false;
	}
  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	//У���Ƿ�ѡ��������.
//	lockScreen('lkscreen');  
  //showSubmitFrame(mDebug);
  document.getElementById("fm").submit(); //�ύ
  //alert("submit");
}

function checkChk()
{
	var tCount = 0;
	var i;
	for(i=0;i<UWErrGrid.mulLineCount;i++)
	{
		  var t =UWErrGrid.getChkNo(i);
		  //alert(t);
			if(t)
			{
				tCount++;
			}
	}
	if(tCount<=0)
	{
		alert("��ѡ����Ҫ���ĵĺ˱���Ϣ!");
		return false;
	}
	return true;
}

// ��ѯ��ť
function easyQueryClickUW()
{
	// ��ʼ�����
	initUWErrGrid();
	
	// ��дSQL���
	var strSQL = "";
	var ContNo = document.all('ContNo').value;
	
	//var PolNo = document.all('PolNo').value;
	
//��ͬ��,������,���ֱ���,��������,�˱���Ϣ,�޸�ʱ��,�Ƿ�����,Ͷ������,��ˮ��,�˱����к�,��ͬ���ֱ��,Ͷ������
	var sqlid917173530="DSHomeContSql917173530";
var mySql917173530=new SqlClass();
mySql917173530.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917173530.setSqlId(sqlid917173530);//ָ��ʹ�õ�Sql��id
mySql917173530.addSubPara(ContNo);//ָ������Ĳ���
mySql917173530.addSubPara(ContNo);//ָ������Ĳ���
mySql917173530.addSubPara(ContNo);//ָ������Ĳ���
mySql917173530.addSubPara(ContNo);//ָ������Ĳ���
mySql917173530.addSubPara(ContNo);//ָ������Ĳ���
mySql917173530.addSubPara(ContNo);//ָ������Ĳ���
strSQL=mySql917173530.getString();
	
//	strSQL = "select A.contno,A.insuredno,A.insuredname,A.riskcode,A.riskname,A.uwerror,A.modifydate,A.passname, "
//	       + " A.x,A.serialno,A.uwno,A.flag,A.proposalno from ( "
//         + " select a.contno,b.insuredno,b.insuredname,b.riskcode, "
//         + " (select riskname from lmriskapp where riskcode=b.riskcode) riskname,a.uwerror, "
//         + " to_char(a.modifydate,'yyyy-mm-dd')||' '||a.modifytime modifydate, "
//         + " (select codename from ldcode where codetype='autouwpassflag' and code=nvl(a.sugpassflag,'N')) passname, "
//         + " a.proposalno x,a.serialno,a.uwno,'risk' flag,b.proposalno proposalno "
//	       + " from LCUWError a,lcpol b "
//         + " where a.polno=b.polno "
//	       + " and b.contno='"+ContNo+"' "
//	   //    + " and (a.uwno = (select max(b.uwno) "
//		//		 + " from LCUWError b where b.ContNo = '"+ContNo+"' "
//		//		 + " and b.PolNo = a.PolNo)) "
//				+" and (a.uwno = (select c.batchno "
//				+" from LCUWMaster c where c.ContNo = '"+ContNo+"' "
//				+ " and c.PolNo = a.PolNo)) "
//		//��ͬ�˱���Ϣ��ѯ
//         + " union "
//         + " select c.contno,d.insuredno,d.insuredname,'000000' riskcode,'��ͬ�˱���Ϣ' riskname, c.uwerror, "
//         + " to_char(c.modifydate,'yyyy-mm-dd')||' '||c.modifytime modifydate, "
//         + " (select codename from ldcode where codetype='autouwpassflag' and code=nvl(c.sugpassflag,'N')) passname, "
//         + " c.proposalcontno x,c.serialno,c.uwno,'cont' flag,'999999999999999' proposalno "
//	       + " from LCCUWError c,lccont d "
//         + " where 1 = 1 "
//	       + " and c.ContNo = '"+ContNo+"' "
//         + " and c.contno=d.contno "
//	  //     + " and (c.uwno = (select max(d.uwno) "
//		//		 + " from LCCUWError d where d.ContNo = '"+ContNo+"')) "
//				+ " and (c.uwno = (select e.batchno "
//				 + " from LCCUWMaster e where e.ContNo = '"+ContNo+"')) "
//		//�����˺˱���Ϣ��ѯ
//		 + " union "
//         + " select c.contno,d.insuredno,d.name,'000000' riskcode,'�����˺˱���Ϣ' riskname, c.uwerror, "
//         + " to_char(c.modifydate,'yyyy-mm-dd')||' '||c.modifytime modifydate, "
//         + " (select codename from ldcode where codetype='autouwpassflag' and code=nvl(c.sugpassflag,'N')) passname, "
//         + " c.proposalcontno x,c.serialno,c.uwno,'insured' flag,'999999999999999' proposalno "
//	       + " from LCIndUWError c,lcinsured d "
//         + " where 1 = 1 "
//	       + " and c.ContNo = '"+ContNo+"' "
//         + " and c.contno=d.contno "
//         + " and c.insuredno=d.insuredno "
//				+ " and (c.uwno = (select e.batchno "
//				 + " from LCIndUWMaster e where e.ContNo = '"+ContNo+"' and e.insuredno=c.insuredno)) "
//         + " ) A order by A.contno,A.insuredno,A.proposalno,A.riskcode,A.modifydate";
	//alert(strSQL);
	//prompt('',strSQL);
	//execEasyQuery( strSQL );
//	turnPage2.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

  //�ж��Ƿ��ѯ�ɹ�
//  if (!turnPage2.strQueryResult) {
    //alert(turnPage.strQueryResult);
 //   alert("���Զ��˱���Ϣ��");
 //   return "";
//  }
  turnPage2.queryModal(strSQL, UWErrGrid);
  return true;

}
function releaseAutoUWButton(FlagStr, content)
{
	//fm.action='';
	unlockScreen('lkscreen');  
	easyQueryClickUW();
	fm.AutoUWButton.disabled=false;
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
  	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	
}

/*********************************************************************
 *  MulLine��RadioBox����¼�����ñ�������ϸ��Ϣ�����뱻������Ϣ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function getInsuredDetail(parm1,parm2)
{
    //Ͷ���˼�����ȫ��ť����
 	var customerNo =CustomerNo;
 	//var sqlind="select a.edorno,d.appntname,a.edorappdate,a.edorvalidate,(select b.codename from ldcode b where b.codetype='edorcontuwstate' and trim(a.uwstate)=trim(b.code)) " + " from LPEdorMain a,lccont d  where  d.contno=a.contno  and d.appntno='"+customerNo+"'";
    var sqlid1="EdorQueryCusSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("uw.EdorQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(customerNo);//ָ������Ĳ���
	    var sqlind=mySql1.getString();
    var arrind = easyExecSql(sqlind);
	   //�ж��Ƿ��ѯ�ɹ�
 	 if(arrind==null){
        document.all('button15').disabled='true';
      }
      else{
        document.all('button15').disabled='';
      }
  //Ͷ���˼������ⰴť����
  /**
  var sqlind= " select a.rptno, c.name, b.MedAccDate, b.AccDate, (case (select clmstate from llregister where rgtno=a.rptno)"
			+ " when '10' then '����' when '20' then '����' when '30' then"
			+ " '���' when '40' then '����' when '50' then '�᰸' when '60' then"
			+ " '���' when '70' then '�ر�' else '����' end)"
			+ " from llreport a, llsubreport b, ldperson c"
			+ " where a.rptno = b.subrptno"
			+ " and b.customerno = c.customerno"
			//�¼Ӱ��ͻ�����
			+ "and b.customerno='"+customerNo +"'";
			*/
  var customerNo =CustomerNo;
  var sqlid1="ClaimQueryCusSql1";
	var mySql1=new SqlClass();
		mySql1.setResourceName("uw.ClaimQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(customerNo);//ָ������Ĳ���
	var sqlind=mySql1.getString();		
  var arrind = easyExecSql(sqlind);
	   //�ж��Ƿ��ѯ�ɹ�
 	 if(arrind==null){
        document.all('button14').disabled='true';
      }
      else{
        document.all('button14').disabled='';
      }
  //�����˼�����ȫ��ť����
  var customerNo =PolAddGrid.getRowColData(PolAddGrid.getSelNo() - 1, 1);
 	//var sqlind="select a.edorno,d.appntname,a.edorappdate,a.edorvalidate,(select b.codename from ldcode b where b.codetype='edorcontuwstate' and trim(a.uwstate)=trim(b.code)) " + " from LPEdorMain a,lccont d  where  d.contno=a.contno  and d.appntno='"+customerNo+"'";
    var sqlid1="EdorQueryCusSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("uw.EdorQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(customerNo);//ָ������Ĳ���
	    var sqlind=mySql1.getString();
    var arrind = easyExecSql(sqlind);
	   //�ж��Ƿ��ѯ�ɹ�
 	 if(arrind==null){
        document.all('button17').disabled='true';
      }
      else{
        document.all('button17').disabled='';
      }
   //�����˼������ⰴť����
  /**
  var sqlind= " select a.rptno, c.name, b.MedAccDate, b.AccDate, (case (select clmstate from llregister where rgtno=a.rptno)"
			+ " when '10' then '����' when '20' then '����' when '30' then"
			+ " '���' when '40' then '����' when '50' then '�᰸' when '60' then"
			+ " '���' when '70' then '�ر�' else '����' end)"
			+ " from llreport a, llsubreport b, ldperson c"
			+ " where a.rptno = b.subrptno"
			+ " and b.customerno = c.customerno"
			//�¼Ӱ��ͻ�����
			+ "and b.customerno='"+customerNo +"'";
			*/
  var customerNo =PolAddGrid.getRowColData(PolAddGrid.getSelNo() - 1, 1);
  var sqlid1="ClaimQueryCusSql1";
	var mySql1=new SqlClass();
		mySql1.setResourceName("uw.ClaimQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(customerNo);//ָ������Ĳ���
	var sqlind=mySql1.getString();		
  var arrind = easyExecSql(sqlind);
	   //�ж��Ƿ��ѯ�ɹ�
 	 if(arrind==null){
        document.all('button16').disabled='true';
      }
      else{
        document.all('button16').disabled='';
      }  
      
      
  
   // var InsuredNo=document.all(parm1).all('PolAddGrid1').value;
   // alert("InsuredNo="+InsuredNo);
    var InsuredNo = PolAddGrid.getRowColData(PolAddGrid.getSelNo() - 1, 1);
    //alert("InsuredNo="+InsuredNo);
    var ContNo = fm.ContNo.value;
    document.all('InsuredNo').value=InsuredNo;
    //�����˽�����֪��ѯ��ť����
    var sqlid917173903="DSHomeContSql917173903";
var mySql917173903=new SqlClass();
mySql917173903.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917173903.setSqlId(sqlid917173903);//ָ��ʹ�õ�Sql��id
mySql917173903.addSubPara(ContNo);//ָ������Ĳ���
mySql917173903.addSubPara(InsuredNo);//ָ������Ĳ���
var sqlind=mySql917173903.getString();
    
//    var sqlind = "select * from lccustomerimpart where contno = '"+ContNo+"' and impartver in ('101','A01','B01','C01','D01')"
//        + " and customernotype='1' and customerno = '"+InsuredNo+"' ";
       //prompt('',sql);
	   var arrind = easyExecSql(sqlind);
	   //�ж��Ƿ��ѯ�ɹ�
 	 if(arrind==null){
        document.all('indButton1').disabled='true';
      }
      else{
        document.all('indButton1').disabled='';
      }
      //
      sqlind = "select 1 from lcpol where contno<> '"+ContNo+"' " 
			  + " and ((appntno = '"+InsuredNo+"' and riskcode='121301')"
			  + " or insuredno = '"+InsuredNo+"' ) "
			  + " and conttype='1' and appflag in ('1','4') "
			  + " union "
			  + " select 1 from lcpol where contno<> '"+ContNo+"' "
			  + " and (insuredno = '"+InsuredNo+"' or (appntno = '"+InsuredNo+"' and riskcode='121301'))"
			  + " and conttype='1' and appflag ='0' "
			  + " union "
			  + " select 1 from lcpol where contno<> '"+ContNo+"' "
			  + " and (appntno = '"+InsuredNo+"' or insuredno = '"+InsuredNo+"') "
			  + " and conttype='2' ";
       //prompt('',sqlind);
	   var arrind1 = easyExecSql(sqlind);
	   //�ж��Ƿ��ѯ�ɹ�
 	 if(arrind1==null){
        document.all('indButton2').disabled='true';
      }
      else{
        document.all('indButton2').disabled='';
      }
    //��������ϸ��Ϣ
	/* var ssql="select tt.impartparamname,tt.impartparam from LCCustomerImpartParams tt where tt.contno='"+ContNo+"' "
		  +" and tt.customerno='"+InsuredNo+"' and tt.customernotype='1'"
		  +" and tt.impartver='02' and tt.impartcode='000'";
	 var ssArr=easyExecSql(ssql);
	try
	{
	  if(ssArr!=null)
	  {
	  	 document.all('InsuredStature').value=ssArr[0][1];//���
		 document.all('InsuredWeight').value=ssArr[1][1];//����	
		 document.all('InsuredBMI').value=Math.round(parseFloat(ssArr[1][1])/parseFloat(ssArr[0][1])/parseFloat(ssArr[0][1])*10000);
	  }	 	
	}catch(ex){};*/
	var nnPrtNo = document.all('PrtNo').value;
	if(nnPrtNo!=null && nnPrtNo!="" && nnPrtNo.length==14 && nnPrtNo.substring(2,4)=="51")//��ͥ��
	{
	  var tSequenceNo = PolAddGrid.getSelNo();
	  //if(!confirm("tSequenceNo="+tSequenceNo)) return false;
	  if(tSequenceNo == "1")
	  {
	  	var sqlid917174121="DSHomeContSql917174121";
var mySql917174121=new SqlClass();
mySql917174121.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917174121.setSqlId(sqlid917174121);//ָ��ʹ�õ�Sql��id
mySql917174121.addSubPara(ContNo);//ָ������Ĳ���
var sql=mySql917174121.getString();
	  	
//	  	var sql = "select impartparamname, impartparam"
//         + " from lccustomerimpartparams"
//         + " where 1 = 1"
//         //+ " and customernotype = '1'" 
//         //+ " and customerno='"+InsuredNo+"' "            
//         + " and impartcode = 'D0101'"
//         + " and impartver = 'D01'"
//         + " and impartparamno in ('1', '2')"
//         + " and contno = '"+ContNo+"' "
//         + " order by customernotype desc,customerno,impartparamno ";
       //prompt('',sql);
	   var arr1 = easyExecSql(sql);
	   if(arr1!=null)
	   {
			document.all('InsuredStature').value=arr1[0][1];
			document.all('InsuredWeight').value=arr1[1][1];
			document.all('InsuredBMI').value=Math.round(parseFloat(arr1[1][1])/parseFloat(arr1[0][1])/parseFloat(arr1[0][1])*10000);
		}
		
		//��ѯ������
		var sqlid917174208="DSHomeContSql917174208";
var mySql917174208=new SqlClass();
mySql917174208.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917174208.setSqlId(sqlid917174208);//ָ��ʹ�õ�Sql��id
mySql917174208.addSubPara(ContNo);//ָ������Ĳ���
var insql=mySql917174208.getString();


		
		
//	    var insql = "select impartparam"
//         + " from lccustomerimpartparams"
//         + " where 1 = 1"
//         //+ " and customernotype = '1'"  
//         //+ " and customerno='"+InsuredNo+"' "        
//         + " and impartcode = 'D0119'"
//         + " and impartver = 'D02'"
//         + " and impartparamno ='1'"
//         + " and contno = '"+ContNo+"' "
//         + " order by customernotype desc,customerno,impartparamno ";
	    //prompt("",insql);
	    var incomeway = easyExecSql(insql);
	    if(incomeway!=null && incomeway[0][0] !="")
	    {
	       document.all('Insuredincome').value = incomeway[0][0];
	    }	    
	  }
	  else if(tSequenceNo == "2")
	  {
	  	var sqlid917174254="DSHomeContSql917174254";
var mySql917174254=new SqlClass();
mySql917174254.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917174254.setSqlId(sqlid917174254);//ָ��ʹ�õ�Sql��id
mySql917174254.addSubPara(ContNo);//ָ������Ĳ���
var sql=mySql917174254.getString();

	  	
//	  	var sql = "select impartparamname, impartparam"
//         + " from lccustomerimpartparams"
//         + " where 1 = 1"
//         //+ " and customernotype = '1'" 
//         //+ " and customerno='"+InsuredNo+"' "            
//         + " and impartcode = 'D0101'"
//         + " and impartver = 'D01'"
//         + " and impartparamno in ('3', '4')"
//         + " and contno = '"+ContNo+"' "
//         + " order by customernotype desc,customerno,impartparamno ";
	   var arr1 = easyExecSql(sql);
	   if(arr1!=null)
	   {
			document.all('InsuredStature').value=arr1[0][1];
			document.all('InsuredWeight').value=arr1[1][1];
			document.all('InsuredBMI').value=Math.round(parseFloat(arr1[1][1])/parseFloat(arr1[0][1])/parseFloat(arr1[0][1])*10000);
		}
		
		//��ѯ������
		var sqlid917174329="DSHomeContSql917174329";
var mySql917174329=new SqlClass();
mySql917174329.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917174329.setSqlId(sqlid917174329);//ָ��ʹ�õ�Sql��id
mySql917174329.addSubPara(ContNo);//ָ������Ĳ���
var insql=mySql917174329.getString();

		
//	    var insql = "select impartparam"
//         + " from lccustomerimpartparams"
//         + " where 1 = 1"
//         //+ " and customernotype = '1'"  
//         //+ " and customerno='"+InsuredNo+"' "        
//         + " and impartcode = 'D0119'"
//         + " and impartver = 'D02'"
//         + " and impartparamno ='3'"
//         + " and contno = '"+ContNo+"' "
//         + " order by customernotype desc,customerno,impartparamno ";
	    //prompt("",insql);
	    var incomeway = easyExecSql(insql);
	    if(incomeway!=null && incomeway[0][0] !="")
	    {
	       document.all('Insuredincome').value = incomeway[0][0];
	    }	    
	  }
	  else if(tSequenceNo == "3")
	  {
	  	var sqlid917174407="DSHomeContSql917174407";
var mySql917174407=new SqlClass();
mySql917174407.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917174407.setSqlId(sqlid917174407);//ָ��ʹ�õ�Sql��id
mySql917174407.addSubPara(ContNo);//ָ������Ĳ���
var sql=mySql917174407.getString();

	  	
//	  	var sql = "select impartparamname, impartparam"
//         + " from lccustomerimpartparams"
//         + " where 1 = 1"
//        // + " and customernotype = '1'" 
//        // + " and customerno='"+InsuredNo+"' "            
//         + " and impartcode = 'D0101'"
//         + " and impartver = 'D01'"
//         + " and impartparamno in ('5', '6')"
//         + " and contno = '"+ContNo+"' "
//         + " order by customernotype desc,customerno,impartparamno ";
	   var arr1 = easyExecSql(sql);
	   if(arr1!=null)
	   {
			document.all('InsuredStature').value=arr1[0][1];
			document.all('InsuredWeight').value=arr1[1][1];
			document.all('InsuredBMI').value=Math.round(parseFloat(arr1[1][1])/parseFloat(arr1[0][1])/parseFloat(arr1[0][1])*10000);
		}
	  }
	  
	}
	else
	{
		 var sqlid917174450="DSHomeContSql917174450";
var mySql917174450=new SqlClass();
mySql917174450.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917174450.setSqlId(sqlid917174450);//ָ��ʹ�õ�Sql��id
mySql917174450.addSubPara(ContNo);//ָ������Ĳ���
var sql=mySql917174450.getString();


		 
//		 var sql = "select impartparamname, impartparam"
//         + " from lccustomerimpartparams"
//         + " where 1 = 1"
//         //+ " and customernotype = '1'"  
//         //+ " and customerno='"+InsuredNo+"' "        
//         + " and impartcode in ('A0101','A0501')"
//         + " and impartver in ('A01','A05')"
//         + " and impartparamno in ('1', '2')"
//         + " and contno = '"+ContNo+"' "
//         + " order by customernotype desc,customerno,impartparamno ";
       //  prompt('',sql);
	   var arr1 = easyExecSql(sql);
	   if(arr1!=null)
	   {
			document.all('InsuredStature').value=arr1[0][1];
			document.all('InsuredWeight').value=arr1[1][1];
			document.all('InsuredBMI').value=Math.round(parseFloat(arr1[1][1])/parseFloat(arr1[0][1])/parseFloat(arr1[0][1])*10000);
		}
		
		//��ѯ������
		var sqlid917174531="DSHomeContSql917174531";
var mySql917174531=new SqlClass();
mySql917174531.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917174531.setSqlId(sqlid917174531);//ָ��ʹ�õ�Sql��id
mySql917174531.addSubPara(ContNo);//ָ������Ĳ���
var insql=mySql917174531.getString();

		
//	    var insql = "select impartparam"
//         + " from lccustomerimpartparams"
//         + " where 1 = 1"
//         //+ " and customernotype = '1'"  
//         //+ " and customerno='"+InsuredNo+"' "        
//         + " and impartcode in ('A0120','A0534')"
//         + " and impartver in ('A02','A06')"
//         + " and impartparamno ='1'"
//         + " and contno = '"+ContNo+"' "
//         + " order by customernotype desc,customerno,impartparamno ";
	    //prompt("",insql);
	    var incomeway = easyExecSql(insql);
	    if(incomeway!=null && incomeway[0][0] !="")
	    {
	       document.all('Insuredincome').value = incomeway[0][0];
	    }	    
	}
	
	//���㱻���˷��ձ���������� 2008-11-25 add ln
	//alert(InsuredNo); 
    var tSumAmnt =0;
    var sqlid917174640="DSHomeContSql917174640";
var mySql917174640=new SqlClass();
mySql917174640.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917174640.setSqlId(sqlid917174640);//ָ��ʹ�õ�Sql��id
mySql917174640.addSubPara(InsuredNo);//ָ������Ĳ���
var strSQL=mySql917174640.getString();
    
//    var strSQL =  "SELECT nvl(healthyamnt2('" + InsuredNo +"','1','1'),0) from dual ";
    var arr1=easyExecSql(strSQL);
   // prompt("",strSQL);
	if(arr1!=null){
		document.all('InsuredSumLifeAmnt').value=arr1[0][0];
		//tSumAmnt = tSumAmnt + parseFloat(arr1[0][0]);
		tSumAmnt = Arithmetic(tSumAmnt,'+',arr1[0][0],2);
	}
	 
	 var sqlid917175620="DSHomeContSql917175620";
var mySql917175620=new SqlClass();
mySql917175620.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917175620.setSqlId(sqlid917175620);//ָ��ʹ�õ�Sql��id
mySql917175620.addSubPara(InsuredNo);//ָ������Ĳ���
strSQL=mySql917175620.getString();
	 
//	 strSQL =  "SELECT nvl(healthyamnt2('" + InsuredNo +"','2','1'),0) from dual ";
    var arr2=easyExecSql(strSQL);
	if(arr2!=null){
		document.all('InsuredSumHealthAmnt').value=arr2[0][0];
		//tSumAmnt = tSumAmnt + parseFloat(arr2[0][0]);
		tSumAmnt = Arithmetic(tSumAmnt,'+',arr2[0][0],2);
	}
	
	 var sqlid917175653="DSHomeContSql917175653";
var mySql917175653=new SqlClass();
mySql917175653.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917175653.setSqlId(sqlid917175653);//ָ��ʹ�õ�Sql��id
mySql917175653.addSubPara(InsuredNo);//ָ������Ĳ���
strSQL=mySql917175653.getString();
	 
//	 strSQL =  "SELECT nvl(healthyamnt2('" + InsuredNo +"','3','1'),0) from dual ";
    var arr3=easyExecSql(strSQL);
	if(arr3!=null){
		document.all('InsuredMedicalAmnt').value=arr3[0][0];
		//tSumAmnt = tSumAmnt + parseFloat(arr3[0][0]);
	}
	
	var sqlid917175734="DSHomeContSql917175734";
var mySql917175734=new SqlClass();
mySql917175734.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917175734.setSqlId(sqlid917175734);//ָ��ʹ�õ�Sql��id
mySql917175734.addSubPara(InsuredNo);//ָ������Ĳ���
strSQL=mySql917175734.getString();
	
//	strSQL =  "SELECT nvl(healthyamnt2('" + InsuredNo +"','4','1'),0) from dual ";
    var arr4=easyExecSql(strSQL);
	if(arr4!=null){
		document.all('InsuredAccidentAmnt').value=arr4[0][0];
		//tSumAmnt = tSumAmnt + parseFloat(arr4[0][0]);
		tSumAmnt = Arithmetic(tSumAmnt,'+',arr4[0][0],2);
	}
	document.all('InsuredSumAmnt').value=tSumAmnt;
	
    //�ۼƱ��� �����������Ͳ����ڽ�����
    var sqlid917175903="DSHomeContSql917175903";
var mySql917175903=new SqlClass();
mySql917175903.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917175903.setSqlId(sqlid917175903);//ָ��ʹ�õ�Sql��id
mySql917175903.addSubPara(InsuredNo);//ָ������Ĳ���
mySql917175903.addSubPara(InsuredNo);//ָ������Ĳ���
mySql917175903.addSubPara(InsuredNo);//ָ������Ĳ���
strSQL=mySql917175903.getString();
       
//    strSQL =  "SELECT decode(sum(a_Prem),'','x',round(sum(a_Prem),2)) FROM"
//          + "(select (case"
//          + " when s_PayIntv = '1' then"
//          + " s_Prem/0.09"
//          + " when s_PayIntv = '3' then"
//          + " s_Prem/0.27"
//          + " when s_PayIntv = '6' then"
//          + " s_Prem/0.52"
//          + " when s_PayIntv = '12' then"
//          + " s_Prem"
//          + " end) a_Prem"          
//          + " FROM (select distinct payintv as s_PayIntv,"
//          + " sum(prem) as s_Prem"
//          + " from lcpol c where polno in(select polno "
//	          + " from lcpol a"
//	          + " where a.insuredno = '"+InsuredNo+"'"
//	          + " and a.riskcode <> '121301' "//�ų�Ͷ���˻�����
//	          + " union"
//	          + " select b.polno"
//	          + " from lcinsuredrelated b"
//	          + " where b.customerno = '"+InsuredNo+"'"
//	          + " union"
//	          + " select polno "
//	          + " from lcpol a"
//	          + " where a.appntno = '"+InsuredNo+"'"
//	          + " and a.riskcode = '121301'"
//	          + " )"
//          + " and c.payintv in ('1', '3','6','12')"
//          + " and c.uwflag not in ('1', '2', 'a')"
//          + " and c.appflag <> '4'" 
//          + " and not exists (select 'X'"
//	          + " from lccont"
//	          + " where ContNo = c.contno"
//	          + " and (uwflag in ('1', '2', 'a') or appflag='4' or (state is not null and substr(state,0,4) in ('1002', '1003'))  ))"
//          + " group by payintv))";
//    prompt('',strSQL);
    var arr5=easyExecSql(strSQL);
	if(arr5!=null){	
	       //alert(arr5[0][0]);    
	       if(arr5[0][0]!='x')
	       	  document.all('InsuredSumPrem').value=arr5[0][0];
	       else
	    {
	    	document.all('InsuredSumPrem').value ='0';
	    }
	}
	    	      
    var sqlid917180148="DSHomeContSql917180148";
var mySql917180148=new SqlClass();
mySql917180148.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917180148.setSqlId(sqlid917180148);//ָ��ʹ�õ�Sql��id
mySql917180148.addSubPara(ContNo);//ָ������Ĳ���
mySql917180148.addSubPara(InsuredNo);//ָ������Ĳ���
strSQL=mySql917180148.getString();
    
//    strSQL ="select a.sequenceno,a.relationtomaininsured,a.name,a.occupationcode,a.occupationtype,b.BlacklistFlag"
//	           + " from LCInsured a,LDPerson b where a.ContNo = '"+ContNo+"' and a.InsuredNo='"+InsuredNo+"'"
//	           + "and b.CustomerNo = a.InsuredNo";     
	    arrResult=easyExecSql(strSQL);
	    if(arrResult!=null)
	    {
	        DisplayInsured();
	    }
    initPolRisk(ContNo,InsuredNo);
    
}



function DisplayInsured()
{
	//InsuredName/InsuredOccupationCode/InsuredOccupationType/Insuredincome
	  //alert("asdfasfsf");
	  try{document.all('SequenceNoCode').value= arrResult[0][0]; }catch(ex){};
	  try{document.all('RelationToMainInsured').value= arrResult[0][1]; }catch(ex){};
    try{document.all('InsuredName').value= arrResult[0][2]; }catch(ex){};
    try{document.all('InsuredOccupationCode').value= arrResult[0][3]; }catch(ex){};
    try{document.all('InsuredOccupationType').value= arrResult[0][4]; }catch(ex){};
    document.all('InsuredBlacklistFlag').value = arrResult[0][5];
	quickGetName('blacklistflag',fm.InsuredBlacklistFlag,fm.InsuredBlacklistFlagName);
    quickGetName('occupationCode',fm.InsuredOccupationCode,fm.InsuredOccupationCodeName);
    quickGetName('OccupationType',fm.InsuredOccupationType,fm.InsuredOccupationTypeName);
    quickGetName('SequenceNo',fm.SequenceNoCode,fm.SequenceNoName);
    quickGetName('Relation',fm.RelationToMainInsured,fm.RelationToMainInsuredName);
}

function initPolRisk(contno,insuredno)
{
	//alert("contno:"+contno);
	var sqlid917180259="DSHomeContSql917180259";
var mySql917180259=new SqlClass();
mySql917180259.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917180259.setSqlId(sqlid917180259);//ָ��ʹ�õ�Sql��id
mySql917180259.addSubPara(contno);//ָ������Ĳ���
mySql917180259.addSubPara(insuredno);//ָ������Ĳ���
var strSQL=mySql917180259.getString();

	
//	var strSQL = "select riskcode,riskname,amnt,mult,insuyear,payyears,payintv,standprem,addfee01,addfee02,polno,addfee03,addfee04 "
//	                        + " from("
//			+ "select riskcode,(select riskname from lmrisk where riskcode=a.riskcode) riskname,"
//			+ " (select subriskflag from lmriskapp where riskcode=a.riskcode) riskflag,"
//	         + " amnt,mult,(insuyear || a.insuyearflag) insuyear,payyears, "
//           + " payintv,standprem,"
//           + " nvl((select sum(prem) from lcprem where polno=a.polno and payplancode like '000000%%' and payplantype='02'),0) addfee01,"
//           + " nvl((select sum(prem) from lcprem where polno=a.polno and payplancode like '000000%%' and payplantype='01'),0) addfee02,"
//           + " polno ,"
//           + " nvl((select sum(prem) from lcprem where polno=a.polno and payplancode like '000000%%' and payplantype='03'),0) addfee03,"
//           + " nvl((select sum(prem) from lcprem where polno=a.polno and payplancode like '000000%%' and payplantype='04'),0) addfee04"
//           + " from lcpol a "
//           + " where contno='"+contno+"' "
//           + " and insuredno='"+insuredno+"'"
//        + " )D order by D.riskflag,D.riskcode ";
   //prompt('',strSQL);
   turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    //alert(turnPage.strQueryResult);0
    alert("û��δͨ������Լ�˱��ĸ��˺�ͬ����");
    return;
  }
	
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = PolRiskGrid;

  //����SQL���
  turnPage.strQuerySql     = strSQL;
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet   = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //alert(arrDataSet);
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);        
}

function showSpec(tindex)
{
  var InsuredNo = document.all('InsuredNo').value;
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  if (ContNo != ""&& PrtNo !="" && MissionID != "" )
  { 	
  	window.open("./UWManuSpecMain.jsp?ContNo="+ContNo+"&PrtNo="+PrtNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&InsuredNo="+InsuredNo+"&tIndex="+tindex+"&QueryFlag=0","window1",sFeatures);  	
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("���ݴ������!");
  }
}

//�б����۱��¼��
//��ҪУ��ѡ���˱�����֮����ܽ���
function NewChangeRiskPlan()
{
    var strSql="";
    //strSql="select salechnl from lccont where contno='"+prtNo+"'";
    
    /*strSql = "select case lmriskapp.riskprop"
            +" when 'I' then '1'"
	        +" when 'G' then '2'"
	        +" when 'Y' then '3'"
	        +" when 'T' then '5'"
           + " end"
           + " from lmriskapp"
           + " where riskcode in (select riskcode"
           + " from lcpol"
           + " where polno = mainpolno"
           + " and prtno = '"+PrtNo+"')"    
           
    arrResult = easyExecSql(strSql);
    if(arrResult==null){
        strSql = " select * from ("
               + " select case missionprop5"
               + " when '05' then '3'"
               + " when '12' then '3'"
               + " when '13' then '5'"
               + " else '1'"
               + " end"
               + " from lbmission"
               + " where missionprop1 = '"+PrtNo+"'"
               + " and activityid = '0000001099'"
               + " union"
               + " select case missionprop5"
               + " when 'TB05' then '3'"
               + " when 'TB12' then '3'"
               + " when 'TB06' then '5'"
               + " else '1'"
               + " end"
               + " from lbmission"
               + " where missionprop1 = '"+PrtNo+"'"
               + " and activityid = '0000001098'"
               + ") where rownum=1";
        arrResult = easyExecSql(strSql);               
    }
    var BankFlag = arrResult[0][0];*/
    //alert("BankFlag="+arrResult[0][0]); 
	//ȥ��ԭ�����ж�Ͷ�������͵��߼����޸�Ϊ��ӡˢ�����ж�Ͷ��������
	var BankFlag = "";
    var tSplitPrtNo = PrtNo.substring(0,4);
    //alert("tSplitPrtNo=="+tSplitPrtNo); 
    //8635��8625��8615������Ͷ�������棬����Ķ��߸��ս���
    if(tSplitPrtNo=="8635"||tSplitPrtNo=="8625"||tSplitPrtNo=="8615"){
    	BankFlag="3";
    }else{
    	BankFlag="1";
    }
  
 	 var sqlid917180357="DSHomeContSql917180357";
var mySql917180357=new SqlClass();
mySql917180357.setResourceName("uw.ManuUWSql");//ָ��ʹ�õ�properties�ļ���
mySql917180357.setSqlId(sqlid917180357);//ָ��ʹ�õ�Sql��id
mySql917180357.addSubPara(PrtNo);//ָ������Ĳ���
var strSql2=mySql917180357.getString();
 	 
// 	 var strSql2="select missionprop5 from lbmission where activityid='0000001099' and missionprop1='"+PrtNo+"'";
	var crrResult = easyExecSql(strSql2);
	var SubType="";
	if(crrResult!=null)
	{
		SubType = crrResult[0][0];
	}
     
     var NoType = "1";
	var InsuredNo = document.all('InsuredNo').value;
	window.open("./UWChangeRiskPlanMain.jsp?ContNo="+ContNo+"&PrtNo="+PrtNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&InsuredNo="+InsuredNo+"&InsuredSumLifeAmnt="+fm.InsuredSumLifeAmnt.value+"&InsuredSumHealthAmnt="+fm.InsuredSumHealthAmnt.value+"&NoType="+NoType+"&BankFlag="+BankFlag+"&SubType="+SubType+"&QueryType=3","window1",sFeatures);  	

}
//Ͷ���˼�����ȫ
function queryEdorCus(){

	if (document.all("ContNo").value =='' )
    {    	
        alert("��ѡ��Ҫ��ѯ�ı�����");
        return;
    }
    var tContNo=fm.ContNo.value;
 	var tCustomerNo = CustomerNo;
	window.open("./EdorQueryMainCus.jsp?ContNo="+tContNo+"&CustomerNo="+tCustomerNo,"window1",sFeatures);
}

//Ͷ���˼��������ѯ
function queryClaimCus(){

	if (document.all("ContNo").value =='' )
    {    	
        alert("��ѡ��Ҫ��ѯ�ı�����");
        return;
    }
    var tContNo=fm.ContNo.value;
 	var tCustomerNo = CustomerNo;
	window.open("./ClaimQueryMainCus.jsp?ContNo="+tContNo+"&CustomerNo="+tCustomerNo,"window1",sFeatures);
}
//�����˼�����ȫ
function queryEdorCusIns(){

	if (document.all("ContNo").value =='' )
    {    	
        alert("��ѡ��Ҫ��ѯ�ı�����");
        return;
    }
    var tContNo=fm.ContNo.value;
 	var tCustomerNo = document.all('InsuredNo').value;;
	window.open("./EdorQueryMainCus.jsp?ContNo="+tContNo+"&CustomerNo="+tCustomerNo,"window1",sFeatures);
}

//�����˼��������ѯ
function queryClaimCusIns(){

	if (document.all("ContNo").value =='' )
    {    	
        alert("��ѡ��Ҫ��ѯ�ı�����");
        return;
    }
    var tContNo=fm.ContNo.value;
 	var tCustomerNo = document.all('InsuredNo').value;;
	window.open("./ClaimQueryMainCus.jsp?ContNo="+tContNo+"&CustomerNo="+tCustomerNo,"window1",sFeatures);
}




