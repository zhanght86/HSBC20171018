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

var sqlresourcename = "uwgrp.ClaimQueryInitSql";

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
  //alert(111);
 // var aSQL="select caseno,customername,endcasedate from llcase where customerno='"+customerNo+"'";

var sqlid1="ClaimQueryInitSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(customerNo);

 //alert(aSQL);
  var arrResult = easyExecSql(mySql1.getString());
  //fm.CustomerNo.value=aSQL;
  if(arrResult!=null)
  {
  	var clmno = arrResult[0][0];
  }
  /*
  var sql="select rgtno,rgtantname,rgtdate,(case when clmstate='20' then '����' when clmstate='30' then '���' when clmstate='40' then '����' when clmstate='50' then '�᰸' when clmstate='60' then '���' when clmstate='70' then '�ر�' end) from llregister where rgtno='"+clmno
  	+"' union select rptno,rptorname,rptdate,'����' from llreport where rptno='"+clmno+"'";
  */
  var sqlid2="ClaimQueryInitSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(clmno);
		mySql2.addSubPara(clmno);
  
  //alert(sql);
  
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
  turnPage.queryModal(mySql2.getString(), ClaimGrid);

}

 

function queryPersonInfo(){
  //var aSQL = "select a.customerno,a.name from ldperson a where a.customerno='"+customerNo+"'";	
  
  ar sqlid3="ClaimQueryInitSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName(sqlresourcename);
		mySql3.setSqlId(sqlid3);
		mySql3.addSubPara(customerNo);
  
  var arrResult = easyExecSql(mySql3.getString());
  fm.all('CustomerNo').value = arrResult[0][0];
  fm.all('CustomerName').value = arrResult[0][1];
}


function getPolInfo(){
	
  var tSelNo = ClaimGrid.getSelNo()-1;
  var tClmNo= ClaimGrid.getRowColData(tSelNo,1);
  var tClmState= ClaimGrid.getRowColData(tSelNo,4);
  //alert("tSelNo="+tSelNo);
  //alert("tClmNo="+tClmNo);
  //alert("tClmState="+tClmState);
  if(tClmState=="����")
  {
  	alert("���ⰸ��������Ϣ��");
  	
  }
  else
  {
	//var aSQL = "select unique a.clmno,a.polno,(select b.riskname from lmrisk b where trim(a.riskcode)=trim(b.riskcode)),(select c.codename from ldcode c where c.codetype='llclaimconclusion' and trim(a.givetype)=trim(c.code)),a.realpay,a.operator from LLClaimPolicy a ,llregister b where a.clmno=b.rgtno and a.ClmNo='"+tClmNo+"'";
	
	var sqlid4="ClaimQueryInitSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName(sqlresourcename);
		mySql4.setSqlId(sqlid4);
		mySql4.addSubPara(tClmNo);
	var aSQL =mySql4.getString();
	//alert(aSQL);
  }
  turnPage2.queryModal(aSQL, PolGrid);
	
}

//Ӱ�����ϲ�ѯ
function showImage(){ 
	
	 var tsel=PolGrid.getSelNo()-1;
	 
	 //alert(ContNo); 
	 if(tsel<0){
     alert("��ѡ�񱣵�!");
     retutn;	 
   }
   var ContNo=PolGrid.getRowColData(tsel,1); 
	window.open("./ImageQueryMain.jsp?ContNo="+ContNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");								
    
} 