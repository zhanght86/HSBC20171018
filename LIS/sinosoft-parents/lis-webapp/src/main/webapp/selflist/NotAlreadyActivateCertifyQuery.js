
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var showInfo;
var queryflag=0;////��ѯ��־�������ڵ�����ذ�ťǰ��������ѯ��ť���鿴һ�����صļ�¼:0��ʾδ�����1��ʾ����˼�¼�����Ҳ�ѯ���˼�¼

// ��ѯ��ť
function easyQueryClick()
{

	initCardInfo();
	
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


	// ��дSQL���
	var str="";
	var strSQL = "";
	//δ�������Ϣ
	if(fm.IsPlan.value=="0"){
	//��һ����:�ѽɷ�δ����Ŀ���
//	strSQL=" select ������, ������־, �ɷѵ��ʱ�־, �ɷ�����, �������� "
//	                    +" from (select /*+rule+*/ t.tempfeeno ������,'�Ѻ���' ������־,(case when t.confmakedate is null then 'δ����' else '�ѵ���' end) �ɷѵ��ʱ�־,"
//				     +" t.paydate �ɷ�����,t.ConfMakeDate �������� from ljtempfee t, ratecard k where t.riskcode = k.riskcode and t.paymoney = k.prem"
//					+" and exists (select 1 from lccont where contno = t.tempfeeno and customgetpoldate is null)"        
//                         +  getWherePart( 't.tempfeeno','CertifyNo' );
	
             
   	if(!(document.all('MakeDateB').value==null||document.all('MakeDateB').value==""))
   	{
   		strSQL=strSQL+" and t.PayDate>='"+document.all('MakeDateB').value+"'";
   	}
   
   	if(!(document.all('MakeDateE').value==null||document.all('MakeDateE').value==""))
   	{
   		strSQL=strSQL+" and t.PayDate<='"+document.all('MakeDateE').value+"'";
   	}
   
   //�ڶ�����:�ѽɷ�δ����
//   	strSQL=strSQL+" union select t.tempfeeno ������,'δ����' ������־,(case when t.confmakedate is null then 'δ����' else '�ѵ���' end) �ɷѵ��ʱ�־,"
//						  +" t.paydate �ɷ�����, t.confmakedate �������� from ljtempfee t, ratecard k where t.riskcode = k.riskcode and t.paymoney = k.prem"
//					       +" and not exists (select 1 from lccont where contno = t.tempfeeno)"
//                                +getWherePart( 't.tempfeeno','CertifyNo' );
//   	
   	 	
          
    if(!(document.all('MakeDateB').value==null||document.all('MakeDateB').value==""))
   	{
   		strSQL=strSQL+" and t.PayDate>='"+document.all('MakeDateB').value+"'";
   	}
   
   	if(!(document.all('MakeDateE').value==null||document.all('MakeDateE').value==""))
   	{
   		strSQL=strSQL+" and t.PayDate<='"+document.all('MakeDateE').value+"'";
   	}
   	
   	
   	var sqlid1="NotAlreadyActivateCertifyQuerySql1";
 	var mySql1=new SqlClass();
 	mySql1.setResourceName("selflist.NotAlreadyActivateCertifyQuerySql");
 	mySql1.setSqlId(sqlid1); //ָ��ʹ��SQL��id
 	mySql1.addSubPara(window.document.getElementsByName(trim("CertifyNo"))[0].value);//ָ���������
 	mySql1.addSubPara(window.document.getElementsByName(trim("CertifyNo"))[0].value);
 	strSQL = mySql1.getString();
   	
   }else if(fm.IsPlan.value=="1"){
    //��������:���� δ����

//   	strSQL=strSQL+" select ������, ������־, �ɷѵ��ʱ�־, �ɷ�����, �������� from (select t.tempfeeno ������,'δ����' ������־,(case when t.confmakedate is null then 'δ����' else '�ѵ���' end) �ɷѵ��ʱ�־,"
//						  +" t.paydate �ɷ�����, t.confmakedate �������� from ljtempfee t, ldplan d where t.riskcode = d.contplancode and d.portfolioflag=1"
//					       +" and not exists (select 1 from lccont where familyid = t.tempfeeno)"
//                                +getWherePart( 't.tempfeeno','CertifyNo' );
          
    if(!(document.all('MakeDateB').value==null||document.all('MakeDateB').value==""))
   	{
   		strSQL=strSQL+" and t.PayDate>='"+document.all('MakeDateB').value+"'";
   	}
   
   	if(!(document.all('MakeDateE').value==null||document.all('MakeDateE').value==""))
   	{
   		strSQL=strSQL+" and t.PayDate<='"+document.all('MakeDateE').value+"'";
   	}
     //���Ĳ���:���� �Ѻ���

//   	strSQL=strSQL+" union select t.tempfeeno ������,'�Ѻ���' ������־,(case when t.confmakedate is null then 'δ����' else '�ѵ���' end) �ɷѵ��ʱ�־,"
//						  +" t.paydate �ɷ�����, t.confmakedate �������� from ljtempfee t, ldplan d where t.riskcode = d.contplancode and d.portfolioflag=1"
//					       +" and not exists (select 1 from lccont where familyid = t.tempfeeno and customgetpoldate is null)"
//                                +getWherePart( 't.tempfeeno','CertifyNo' );
         
    if(!(document.all('MakeDateB').value==null||document.all('MakeDateB').value==""))
   	{
   		strSQL=strSQL+" and t.PayDate>='"+document.all('MakeDateB').value+"'";
   	}
   
   	if(!(document.all('MakeDateE').value==null||document.all('MakeDateE').value==""))
   	{
   		strSQL=strSQL+" and t.PayDate<='"+document.all('MakeDateE').value+"'";
   	}
   }
   
 //  strSQL=strSQL+" )order by ������־";
   
	var sqlid2="NotAlreadyActivateCertifyQuerySql2";
 	var mySql2=new SqlClass();
 	mySql2.setResourceName("selflist.NotAlreadyActivateCertifyQuerySql");
 	mySql2.setSqlId(sqlid2); //ָ��ʹ��SQL��id
 	mySql2.addSubPara(window.document.getElementsByName(trim("CertifyNo"))[0].value);//ָ���������
 	mySql2.addSubPara(window.document.getElementsByName(trim("CertifyNo"))[0].value);
 	strSQL = mySql2.getString();
   
   
        
   //prompt("",strSQL);
   turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1); 
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) 
  {
  	queryflag=0;
    alert("û����Ҫ��ѯ�Ŀ�����Ϣ��");
    return false;
  }
    
  queryflag=1;
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = CardInfo;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  arrDataSet= turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
 //tArr=chooseArray(arrDataSet,[0]) 
  //����MULTILINE������ʾ��ѯ���
  
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  //displayMultiline(tArr, turnPage.pageDisplayGrid);
 
  strSQL=strSQL+"order by g.makedate;";
}


//У���¼���Ϊ��
function vertifyInput()
{	
	  //alert("document.all('MakeDateB').value"+document.all('MakeDateB').value);
	  //alert("document.all('MakeDateE').value"+document.all('MakeDateE').value);
	  
	   if(fm.CertifyNo.value == "" && fm.MakeDateB.value == "" && fm.MakeDateE.value == "")
	   {
	   	     alert("��¼�뿨�Ż��߽ɷ���ֹ����");
	  	 	return false;
	   }
	  //���¼�뼤������,�����¼����ʼ����ֹ����,����ֻ¼��һ��
	  if(!(document.all('MakeDateB').value==null||document.all('MakeDateB').value==""))
	  {	
	  	 if(document.all('MakeDateE').value==null||document.all('MakeDateE').value=="")
	  	 {
	  	 	alert("��¼��ɷѽ�ֹ����");
	  	 	return false;
	  	 }
	  }
	  
	  if(!(document.all('MakeDateE').value==null||document.all('MakeDateE').value==""))
	  {	
	  	 if(document.all('MakeDateB').value==null||document.all('MakeDateB').value=="")
	  	 {
	  	 	alert("��¼��ɷ���ʼ����");
	  	 	return false;
	  	 }
	  }
	  
	  
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