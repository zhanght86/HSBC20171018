//***********************************************
//�������ƣ�ClaimQueryInit.js
//�����ܣ����������ѯ
//�������ڣ�2005-06-01 11:10:36
//������  ��HL
//���¼�¼��  ������    ��������     ����ԭ��/����
//***********************************************

//               ���ļ��а����ͻ�����Ҫ����ĺ������¼� 


//ȫ�ֱ���
var showInfo;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";


/*********************************************************************
 *  Ͷ�������˵��ύ��Ĳ���,���������ݷ��غ�ִ�еĲ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
//function afterSubmit( FlagStr, content )
//{
//	if (FlagStr == "Fail" )
//	{             
//  	showInfo.close();  
//		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
//		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
//
//	}
//	if (FlagStr == "Succ")
//	{
//  	showInfo.close();  
//		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
//		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
//
//	}
//	
//}
//


/*********************************************************************
 *  ������һҳ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */

function returnParent(){
  top.close();	
	
}



/*********************************************************************
 *  ��ѯ�ѳб�����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */

function queryClaim(){
	
	   var strSQL = "";
	   //contFlag="1"  �����˱���ѯ�������
//	   alert(contFlag);
    if(contFlag=="1"){
		
		var sqlid1="ClaimQueryCusSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("uw.ClaimQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(customerNo);//ָ������Ĳ���
	    strSQL=mySql1.getString();	
		/**
    	strSQL = " select a.rptno, c.name, b.MedAccDate, b.AccDate, (case (select clmstate from llregister where rgtno=a.rptno)"
			+ " when '10' then '����' when '20' then '����' when '30' then"
			+ " '���' when '40' then '����' when '50' then '�᰸' when '60' then"
			+ " '���' when '70' then '�ر�' else '����' end)"
			+ " from llreport a, llsubreport b, ldperson c"
			+ " where a.rptno = b.subrptno"
			+ " and b.customerno = c.customerno"
			//�¼Ӱ��ͻ�����
			+ "and b.customerno='"+customerNo +"'"
			+ " and exists ("
			//+ " select 1 from llclaimpolicy where contno='"+contNo+"' and clmno=a.rptno"
			+ " select 1 from llclaimpolicy where clmno=a.rptno"
			+ " )";
			*/
    	turnPage.queryModal(strSQL, ClaimGrid); //prompt("",strSQL);
    }else{
    	//09-11-11���˴�SQL�߼���������
		
		var sqlid2="ClaimQuerySql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("uw.ClaimQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(customerNo);//ָ������Ĳ���
	    strSQL=mySql2.getString();	
		
//    	strSQL = " select a.rptno, c.name, b.MedAccDate, b.AccDate, (case (select clmstate from llregister where rgtno=a.rptno)"
//    					+ " when '10' then '����' when '20' then '����' when '30' then"
//    					+ " '���' when '40' then '����' when '50' then '�᰸' when '60' then"
//    					+ " '���' when '70' then '�ر�' else '����' end)"
//    					+ " from llreport a, llsubreport b, ldperson c"
//    					+ " where a.rptno = b.subrptno"
//    					+ " and b.customerno = c.customerno"
//    					+ " and b.customerno = '"+customerNo+"'";
    //��ѯ�����Ժ���Ϣ
//    strSQL = "select a.rgtno,(select c.name from ldperson c where c.customerno = b.customerno),b.AccDate,(case a.clmstate when '10' then '����' when '20' then '����' when '30' then '���' when '40' then '����' when '50' then '�᰸' when '60' then '���' when '70' then '�ر�' end)"
//           + " from llregister a,llcase b,LLClaimPolicy c "
//           + " where a.rgtno = b.caseno "
//           + " and c.clmno = a.rgtno"
//           + " and c.contno = '"+contNo+"'" //�����˱���
//    strSQL = "select a.rgtno,(select c.name from ldperson c where c.customerno = b.customerno),b.MedAccDate,b.AccDate,(case a.clmstate when '10' then '����' when '20' then '����' when '30' then '���' when '40' then '����' when '50' then '�᰸' when '60' then '���' when '70' then '�ر�' end)"
//               + " from llregister a,llcase b "
//               + " where a.rgtno = b.caseno "
//               + " and b.customerno = '"+customerNo+"'" //�����˱���
//
//          
//    //���ϲ�ѯ������Ϣ
//    strSQL = strSQL + " union "
//           + "(select a.rptno,c.name,b.MedAccDate,b.AccDate,'����'"
//           + " from llreport a,llsubreport b,ldperson c "
//           + " where a.rptno = b.subrptno and b.customerno = c.customerno and a.rgtflag = '10' "
//           + " and b.CustomerNo = '"+customerNo+"')" //�����˱���
    
    //prompt("��������������Ϣ��ѯ",strSQL);
//  var aSQL="select caseno,customername,endcasedate from llcase where customerno='"+customerNo+"'";
// //alert(aSQL);
//  var arrResult = easyExecSql(aSQL);
//  //fm.CustomerNo.value=aSQL;
//  if(arrResult!=null)
//  {
//  	var clmno = arrResult[0][0];
//  }
//  
//  var sql="select rgtno,rgtantname,rgtdate,(case when clmstate='20' then '����' when clmstate='30' then '���' when clmstate='40' then '����' when clmstate='50' then '�᰸' when clmstate='60' then '���' when clmstate='70' then '�ر�' end) from llregister where rgtno='"+clmno
//  	+"' union select rptno,rptorname,rptdate,'����' from llreport where rptno='"+clmno+"'";
//  //alert(sql);
//  
//            "select a.contno,a.appntname,a.CValiDate,a.state,"
//         +" (case when exists(select 'X' from LCIssuePol where contno=a.contno)"
//         +" then '��'	else '��'"
//			  +" end),"
//			  +" (case when exists(select 'X' from LCPENotice where contno=a.contno)"
//			  +" then '��' else '��'"
//			  +" end),"
//			  +" (case when exists(select 'X' from LCCustomerImpart where contno=a.contno) "
//			  +" then '��' else '��'"
//			  +" end)"
//			  +" from lccont a,lcinsured b where b.insuredno='"+customerNo+"' and a.contno=b.contno and a.appflag='1'";	
// 
  turnPage.queryModal(strSQL, ClaimGrid); //prompt("",strSQL);
    }
}

 

function queryPersonInfo(){
	
			var sqlid3="ClaimQuerySql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("uw.ClaimQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
		mySql3.addSubPara(customerNo);//ָ������Ĳ���
	    var aSQL=mySql3.getString();	
	
//  var aSQL = "select a.customerno,a.name from ldperson a where a.customerno='"+customerNo+"'";	
  var arrResult = easyExecSql(aSQL);
  document.all('CustomerNo').value = arrResult[0][0];
  document.all('CustomerName').value = arrResult[0][1];
}
/*********************************************************************
 *  ��ʼ
 *  ˵�������������ⰸ������Ϣ���б��еġ������š���Ϊ����ͬ�š�
 *  �޸��ˣ�  �����
 *  ʱ�䣺 2005-10-26
 *********************************************************************
 */

function getPolInfo(){
	
  var tSelNo = ClaimGrid.getSelNo()-1;
  var tClmNo= ClaimGrid.getRowColData(tSelNo,1);
  var tClmState= ClaimGrid.getRowColData(tSelNo,4);
  if(tClmState=="����")
  {
  	var strUrl="../claim/LLClaimQueryReportInput.jsp?claimNo="+tClmNo+"&phase=0";
  	window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
  }
  else
  {
  	
		var sqlid4="ClaimQuerySql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName("uw.ClaimQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
		mySql4.addSubPara(tClmNo);//ָ������Ĳ���
	    var aSQL=mySql4.getString();	
	
//	var aSQL = "select unique a.clmno,a.contno,(select b.riskname from lmrisk b where trim(a.riskcode)=trim(b.riskcode)),(select c.codename from ldcode c where c.codetype='llclaimconclusion' and trim(a.givetype)=trim(c.code)),a.realpay,a.operator from LLClaimPolicy a ,llregister b where a.clmno=b.rgtno and a.ClmNo='"+tClmNo+"'";
	turnPage2.queryModal(aSQL, PolGrid);
  }
  
	
}


//Ӱ�����ϲ�ѯ
function showImage()
{ 	
	var tsel=PolGrid.getSelNo()-1; 
	//alert(ContNo); 
	if(tsel<0)
	{
       alert("��ѡ�񱣵�!");
       return;	 
    }
    var ClmNo = PolGrid.getRowColData(tsel,1);//�ⰸ�� 
//  alert(ClmNo);
//	window.open("./ImageQueryMain.jsp?ContNo="+ContNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");	
//  Modify by zhaorx 2006-03-07							
    var strUrl="../claim/LLColQueryImageMain.jsp?claimNo="+ClmNo;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');    
} 

//����������ϸ��ѯ
function showDetail(){ 
	
	 var tsel=PolGrid.getSelNo()-1;
	if(tsel<0)
	{
    alert("��ѡ���ⰸ!");
   	return;	 
  }
  var ClmNo=PolGrid.getRowColData(tsel,1); 
  if(ClmNo==null||ClmNo=="")
   {alert("û���ⰸ������Ϣ��")
   	return;
   	}
  //var ClmNo="90000013761";
else
	window.open("./ClaimDetailQueryMain.jsp?ClmNo="+ClmNo,"", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);								
    
} 

//������˽��۲�ѯ
function queryClaimUW(){
	 var tsel=PolGrid.getSelNo()-1;
	if(tsel<0)
	{
    alert("��ѡ���ⰸ!");
   	return;	 
  }
  var ClmNo=PolGrid.getRowColData(tsel,1); 
  if(ClmNo==null||ClmNo=="")
   {alert("û���ⰸ������Ϣ��")
   	return;
   	}

  window.open("../claim/LLDealUWsecondMain.jsp?CaseNo="+ClmNo+"&InsuredNo="+document.getElementById("fm").CustomerNo.value,"",sFeatures);	
	
}
