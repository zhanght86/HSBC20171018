
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var showInfo;
var queryflag=0;////��ѯ��־�������ڵ�����ذ�ťǰ��������ѯ��ť���鿴һ�����صļ�¼:0��ʾδ�����1��ʾ����˼�¼�����Ҳ�ѯ���˼�¼


// ������ϸ��ѯ
function PolClick()
{
	var arrReturn = new Array();
	//alert("**");
	var tSel = CardInfo.getSelNo();
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{
	    var cContNo = CardInfo.getRowColData(tSel-1,1);				
		if (cContNo == "")
		    return;		
		
	    window.open("../sys/PolDetailQueryMain.jsp?ContNo=" + cContNo +"&IsCancelPolFlag=0");	
	}
}

//�ύǰ��У�顢����  
function beforeSubmit()
{
    //��������У��
    if(fm.CertifyNo.value == ""
       && fm.Name.value == ""
       //&& fm.Birthday.value == ""
      // && fm.Sex.value == ""
       //&& fm.IDType.value == ""
       && fm.IDNo.value == ""
       && fm.CustomerNo.value == "")
    {
        alert("���������뿨�š��ͻ����롢������֤�������е�һ������!");
        return false;
    }
    return true;
}  

// ��ѯ��ť
function easyQueryClick()
{

	initCardInfo();
	
	//��������У��
    if (!beforeSubmit())
    {
        return;
    }
    
    //alert("111");
    //return false;


	// ��дSQL���
	var str="";
	var ttsql="";
	if(fm.IDNo.value!=""&&fm.IDNo.value!=null)
	{
		 ttsql = " and exists(select 1 from ldperson where idno='"+fm.IDNo.value+"')";
	}
	//�ͻ���Ϣ��ѯ,ֻ�м���Ŀ����Ż��пͻ���Ϣ������ֻ��ѯ�Ѽ���Ŀ���
//	var strSQL = "select t1, t2, t3, t4, t5, t6, t7, t8, t9, t10,t11, t12, t13, t14, t15, t16 from (select g.contno t1, (select riskname	from lmrisk where g.riskcode = riskcode) t2,"
//					 + " g.cvalidate t3, g.enddate t4, (select customgetpoldate from lccont where contno=g.contno) t5, case when now() > g.enddate then 'ʧЧ'	 else	'��Ч' end t6,"
//					 + " d.AppntName t7,d.AppntNo t8,d.AppntBirthday t9,d.idtype t10,d.idno t11,a.phone t12,a.postaladdress t13,a.zipcode t14, (select LDOccupation.Occupationname"
//					 + " from LDOccupation where LDOccupation.Occupationcode = d.occupationcode and LDOccupation.Occupationtype = d.Occupationtype) t15, 'Ͷ����' t16"
//					 + " from lcappnt d, lcpol g,LCAddress a where d.contno = g.contno and d.appntno = g.appntno and a.customerno = d.appntno and a.addressno=d.addressno"
//					 + " and exists (select 1 from lccont where contno=g.contno and customgetpoldate is not null) "
//					 + getWherePart( 'g.contno','CertifyNo' )
//                          + getWherePart( 'd.AppntBirthday','Birthday' )
//                          + getWherePart( 'd.idtype','IDType' )
//                          //+ getWherePart( 'd.idno','IDNo' )
//                          + getWherePart( 'd.AppntName','Name' )
//                          + getWherePart( 'd.AppntSex','Sex' )
//                          + getWherePart( 'd.AppntNo','CustomerNo' )     
//                          + ttsql      
//				      + " union all"
//				      + " select g.contno t1,(select riskname from lmrisk where g.riskcode = riskcode) t2,g.cvalidate t3,g.enddate t4,(select customgetpoldate from lccont where contno=g.contno) t5,"
//					 + " case when now() > g.enddate then 'ʧЧ' else '��Ч' end t6, e.name t7,e.insuredno t8,e.birthday t9,e.idtype t10,e.idno t11,b.phone t12,b.postaladdress t13,b.zipcode t14,"
//					 + " (select LDOccupation.Occupationname from LDOccupation where LDOccupation.Occupationcode = e.occupationcode and LDOccupation.Occupationtype = e.Occupationtype) t15,'������' t16"
//					 + " from lcinsured e, lcpol g, lcappnt d,LCAddress b where e.contno = g.contno and e.insuredno = g.insuredno and e.contno = d.contno and b.customerno = e.insuredno"
//                          + " and b.addressno=e.addressno and exists (select 1 from lccont where contno=g.contno and customgetpoldate is not null) and d.relationtoinsured <> '00'"
//					 + getWherePart( 'g.contno','CertifyNo' )
//                          + getWherePart( 'e.birthday','Birthday' )
//                          + getWherePart( 'e.idtype','IDType' )
//                          //+ getWherePart( 'e.idno','IDNo' )
//                          + getWherePart( 'e.name','Name' )
//                          + getWherePart( 'e.sex','Sex' )
//                          + getWherePart( 'e.insuredno','CustomerNo' )
//                          + ttsql
//                          + ") order by t8, t5 desc";	
	
		var sqlid1="CustomerCertifyQuerySql1";
	 	var mySql1=new SqlClass();
	 	mySql1.setResourceName("selflist.CustomerCertifyQuerySql");
	 	mySql1.setSqlId(sqlid1); //ָ��ʹ��SQL��id
	 	mySql1.addSubPara(window.document.getElementsByName(trim("CertifyNo"))[0].value);//ָ���������
	 	mySql1.addSubPara(window.document.getElementsByName(trim("Birthday"))[0].value);
	 	mySql1.addSubPara(window.document.getElementsByName(trim("IDType"))[0].value);
	 	mySql1.addSubPara(window.document.getElementsByName(trim("Name"))[0].value);
	 	mySql1.addSubPara(window.document.getElementsByName(trim("Sex"))[0].value);
	 	mySql1.addSubPara(window.document.getElementsByName(trim("CustomerNo"))[0].value);
	 	mySql1.addSubPara(fm.IDNo.value);
	 	mySql1.addSubPara(window.document.getElementsByName(trim("CertifyNo"))[0].value);
	 	mySql1.addSubPara(window.document.getElementsByName(trim("Birthday"))[0].value);
	 	mySql1.addSubPara(window.document.getElementsByName(trim("IDType"))[0].value);
	 	mySql1.addSubPara(window.document.getElementsByName(trim("Name"))[0].value);
	 	mySql1.addSubPara(window.document.getElementsByName(trim("Sex"))[0].value);
	 	mySql1.addSubPara(window.document.getElementsByName(trim("CustomerNo"))[0].value);	 	
	 	mySql1.addSubPara(fm.IDNo.value);
	 	var strSQL = mySql1.getString();
	

 // prompt("",strSQL);
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1); 
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) 
  {
  	queryflag=0;
    alert("û����Ҫ��ѯ�Ŀͻ���Ϣ��");
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