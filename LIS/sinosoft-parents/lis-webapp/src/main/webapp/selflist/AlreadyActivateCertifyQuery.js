
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var showInfo;
var queryflag=0;////��ѯ��־�������ڵ�����ذ�ťǰ��������ѯ��ť���鿴һ�����صļ�¼:0��ʾδ�����1��ʾ����˼�¼�����Ҳ�ѯ���˼�¼

// ��ѯ��ť
function easyQueryClick()
{
	//У���¼�����¼��
	if(vertifyInput() == false)
	{
		  return;
	}
	if(dateDiff(document.all('MakeDateB').value,document.all('MakeDateE').value,"D")>10 )
	{
		alert("�ɷ���ֹ�ڼ��������ʮ���ڣ���ֹ����������");
		return ;
	}
	if(fm.Portfolio.value.length>0){
		initFamilyCardInfo();
		divFamilyCardInfo.style.display="";	
		divContInfo.style.display="";	
		divCardInfo.style.display="none";
//		var strSQL = "select c.familyid, max(d.contplanname),max(c.appntno), "
//				+ " max(c.appntname), max(c.makedate), max(c.customgetpoldate),  max(g.cvalidate) ,  max(g.enddate), max(g.standbyflag1)  "
//				+ " from lcpol g, lccont c, ldplan d,lmcardrisk lm "
//				+ "  where  d.portfolioflag=1 and lm.riskcode=d.contplancode and g.contno=c.contno"
//                + getWherePart( 'c.familyid','CertifyNo' )
//                + getWherePart( 'd.contplancode','Portfolio' )
//		        + " and c.familyid is not null and c.customgetpoldate is not null ";
		
		
		
		if(!(document.all('MakeDateB').value==null||document.all('MakeDateB').value==""))
	   	{
	   		strSQL=strSQL+" and c.customgetpoldate >= '"+document.all('MakeDateB').value+"'";
	   	}
   
		if(!(document.all('MakeDateE').value==null||document.all('MakeDateE').value==""))
		{
			strSQL=strSQL+" and c.customgetpoldate <= '"+document.all('MakeDateE').value+"'";
		}
//		strSQL=strSQL + "  and  exists(select 1 from lzcard lz where lz.certifycode=lm.certifycode and c.familyid=startno  union select 1 from lzcardb lzb where lzb.certifycode=lm.certifycode and c.familyid=startno ) group by c.familyid";
				
		
		
		var sqlid1="AlreadyActivateCertifyQuerySql1";
	 	var mySql1=new SqlClass();
	 	mySql1.setResourceName("selflist.AlreadyActivateCertifyQuerySql");
	 	mySql1.setSqlId(sqlid1); //ָ��ʹ��SQL��id
	 	mySql1.addSubPara(window.document.getElementsByName(trim("CertifyNo"))[0].value);//ָ���������
	 	mySql1.addSubPara(window.document.getElementsByName(trim("Portfolio"))[0].value);
	 	mySql1.addSubPara(document.all('MakeDateB').value);
	 	mySql1.addSubPara(document.all('MakeDateE').value);
	 	var strSQL = mySql1.getString();
		
		
		turnPage.queryModal(strSQL,FamilyCardInfo);
	}else if (fm.RiskCode.value.length>0){
		initCardInfo();
		divFamilyCardInfo.style.display="none";	
		divContInfo.style.display="none";	
		divCardInfo.style.display="";
		// ��дSQL���
		var str="";
		//�Ѽ���Ŀ���
//		var strSQL =" select g.contno,(select riskname from lmrisk where g.riskcode = riskcode),g.appntno,g.appntname,g.insuredno,g.insuredname,"
//                          +" g.makedate,c.customgetpoldate,g.cvalidate,g.enddate,g.standbyflag1 from lcpol g, lccont c,ratecard k where g.contno=c.contno"
//                          +" and c.customgetpoldate is not null and  g.riskcode = k.riskcode and g.prem = k.prem"
//	                     + getWherePart( 'g.contno','CertifyNo' )
//  	                     + getWherePart( 'g.riskcode','RiskCode' );
	                     
				
             
	   if(!(document.all('MakeDateB').value==null||document.all('MakeDateB').value==""))
	   {
	   		strSQL=strSQL+" and c.customgetpoldate >= '"+document.all('MakeDateB').value+"'";
	   }
   
		if(!(document.all('MakeDateE').value==null||document.all('MakeDateE').value==""))
		{
			strSQL=strSQL+" and c.customgetpoldate <= '"+document.all('MakeDateE').value+"'";
		}
   
//	   strSQL=strSQL+" order by c.customgetpoldate desc ";
	   
	   
	    var sqlid2="AlreadyActivateCertifyQuerySql2";
	 	var mySql2=new SqlClass();
	 	mySql2.setResourceName("selflist.AlreadyActivateCertifyQuerySql");
	 	mySql2.setSqlId(sqlid2); //ָ��ʹ��SQL��id
	 	mySql2.addSubPara(window.document.getElementsByName(trim("CertifyNo"))[0].value);//ָ���������
	 	mySql2.addSubPara(window.document.getElementsByName(trim("RiskCode"))[0].value);
	 	mySql2.addSubPara(document.all('MakeDateB').value);
	 	mySql2.addSubPara(document.all('MakeDateE').value);
	 	var strSQL = mySql2.getString();
	   
	   
		turnPage.queryModal(strSQL,CardInfo);
		return;
	}
}


//У���¼���Ϊ��
function vertifyInput()
{	
	  //alert("document.all('MakeDateB').value"+document.all('MakeDateB').value);
	  //alert("document.all('MakeDateE').value"+document.all('MakeDateE').value);
	   if(fm.CertifyNo.value == "" && fm.MakeDateB.value == "" && fm.MakeDateE.value == "")
	   {
	   	     alert("��¼�뿨�Ż��߼�����ֹ����");
	  	 	return false;
	   }
	  
	  //���¼�뼤������,�����¼����ʼ����ֹ����,����ֻ¼��һ��
	  if(!(document.all('MakeDateB').value==null||document.all('MakeDateB').value==""))
	  {	
	  	 if(document.all('MakeDateE').value==null||document.all('MakeDateE').value=="")
	  	 {
	  	 	alert("��¼�뼤���ֹ����");
	  	 	return false;
	  	 }
	  }
	  
	  if(!(document.all('MakeDateE').value==null||document.all('MakeDateE').value==""))
	  {	
	  	 if(document.all('MakeDateB').value==null||document.all('MakeDateB').value=="")
	  	 {
	  	 	alert("��¼�뼤����ʼ����");
	  	 	return false;
	  	 }
	  }
	
	  if(fm.RiskCode.value.length>0 && fm.Portfolio.value.length>0){
			alert("��Ʒ���������ִ���ֻ��¼��һ��");
	  	 	return false;	  
	  }
	  if(fm.RiskCode.value.length<=0 && fm.Portfolio.value.length<=0){
			alert("��Ʒ���������ֱ���������¼��һ��");
	  	 	return false;	  
	  }
}
function getFamilyCont(){
	var vRow = FamilyCardInfo.getSelNo();
	
	if( vRow == null || vRow == 0 ) 
	{
		return;
	}
	
	vRow = vRow - 1;
	
	//����ѡ�еļ�¼�������ְҵ��Ϣ��ֵ
	var tFamilyId	= FamilyCardInfo.getRowColData(vRow, 1);
//	var strSQL = "select c.prtno,c.contno,(select riskname from lmrisk where g.riskcode = riskcode),g.insuredno,g.insuredname "
//			+ " from lcpol g, lccont c "
//			+ " where g.contno = c.contno      and c.familyid is not null  and c.customgetpoldate is not null "
//			+ " and c.familyid='" + tFamilyId + "'";
	
	var sqlid3="AlreadyActivateCertifyQuerySql3";
 	var mySql3=new SqlClass();
 	mySql3.setResourceName("selflist.AlreadyActivateCertifyQuerySql");
 	mySql3.setSqlId(sqlid3); //ָ��ʹ��SQL��id
 	mySql3.addSubPara(tFamilyId);//ָ���������
 	var strSQL = mySql3.getString();
	
	turnPage.queryModal(strSQL,ContInfoGrid);
}
//��ӡ��ѯ���
function easyPrint()
{
	//alert(queryflag);
	if(queryflag==0)
	{
		 alert("���ȵ����ѯ��Ҫ��ӡ�ļ�¼");
		 return false;
	}
	easyQueryPrint(2,'CardInfo','turnPage');
}