var showInfo;
var turnPage = new turnPageClass(); 

// ��ȫ��ѯ��ť
function easyQueryClick()
{
	initPolGrid();	
//	var strSQL = "select a.RgtNo, a.insuredno, sum(a.RealPay), a.endcasedate, b.grpname "
//		+" from llclaimpolicy a, llregister b "
// 		+" where a.ClmNo = b.rgtno "
//	 	+" and b.grpcontno !='00000000000000000000' "
//	 	+" and a.GiveType = '0' " //0����
//	 	+" and a.ClmState = '60' "//���
//        + getWherePart('a.ClmNo','RgtNo')
//        + getWherePart('b.mngcom','ManageCom','like') 
//        + getWherePart('a.EndCaseDate','StartDate','>=')
//        + getWherePart('a.EndCaseDate','EndDate','<=')        
// 		+" group by a.RgtNo, a.insuredno, a.endcasedate, b.grpname "
// 		+" order by a.RgtNo, a.EndCaseDate ";
	
	
//	var strSQL = "select a.clmno,a.customerno,sum(a.realpay),"
//	    +" (select endcasedate from llclaim where clmno = a.clmno) h,"
//        +" (select grpname from llregister where rgtno=a.clmno) "
//        +" from llclaimdetail a "
//        +" where (select grpcontno from llregister where rgtno=a.clmno)!= '00000000000000000000' "
//        +" and a.GiveType = '0' "
//        +" and (select clmstate from llclaim where clmno=a.clmno)='60' "
//        +" and a.clmno = '" + document.all('RgtNo').value + "' "
//        +" and (select mngcom from llregister where rgtno=a.clmno) like '"+ document.all('ManageCom').value +"%'"
////        +" and (select endcasedate from llclaim where clmno = a.clmno) >= date'" + document.all('StartDate').value + "'"
////        +" and (select endcasedate from llclaim where clmno = a.clmno) <= date'" + document.all('EndDate').value + "'"
//        + getWherePart('(select endcasedate from llclaim where clmno = a.clmno)','StartDate','>=')
//        + getWherePart('(select endcasedate from llclaim where clmno = a.clmno)','EndDate','<=')      
//        +" group by a.clmno, a.customerno"
//        +" order by a.clmno,h ";
	
	
  	var  StartDate0 = window.document.getElementsByName(trim("StartDate"))[0].value;
  	var  EndDate0 = window.document.getElementsByName(trim("EndDate"))[0].value;
	var sqlid0="ClaimPayNoticeSql0";
	var mySql0=new SqlClass();
	mySql0.setResourceName("claimprt.ClaimPayNoticeSql"); //ָ��ʹ�õ�properties�ļ���
	mySql0.setSqlId(sqlid0);//ָ��ʹ�õ�Sql��id
	mySql0.addSubPara(document.all('RgtNo').value);//ָ������Ĳ���
	mySql0.addSubPara(document.all('ManageCom').value);//ָ������Ĳ���
	mySql0.addSubPara(StartDate0);//ָ������Ĳ���
	mySql0.addSubPara(EndDate0);//ָ������Ĳ���
	var strSQL=mySql0.getString();
 		
	turnPage.queryModal(strSQL, PolGrid);
   	//alert("��ѯ���ļ�¼������"+PolGrid.mulLineCount);
   	if(PolGrid.mulLineCount==0)
   	{
   		alert("û�в�ѯ���κε�֤��Ϣ��");
   		return false;
   	}
}


//�������֪ͨ
function  PrintCaseReceiptClass()
{
	var selno = PolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ��ӡ���ⰸ��");
	      return;
	}	
    document.all('RgtNo').value = PolGrid. getRowColData(selno,1);
	document.all('InsuredNo').value = PolGrid. getRowColData(selno,2);
	
	fm.action = "./ClaimPayNoticeSave.jsp";
    fm.target=".";
	document.getElementById("fm").submit();	
}
