var showInfo;
var turnPage = new turnPageClass(); 

// ��ȫ��ѯ��ť
function easyQueryClick()
{
	initQueryResultGrid();
	
    if(fm.RgtNo.value == null||fm.RgtNo.value == "")
     {
    	    if(fm.ManageCom.value == null||fm.ManageCom.value == "")
    	     {
    	         alert("��������Ҫ��ѯ�Ĺ������");
    	         return false;
    	     }
    	    
    	    if(fm.StartDate.value == null||fm.StartDate.value == "")
   	     	{
   	         	alert("��������Ҫ��ѯ��ͳ������");
   	         	return false;
   	     	}
    	    
    	    if(fm.EndDate.value == null||fm.EndDate.value == "")
   	     	{
   	         	alert("��������Ҫ��ѯ��ͳ��ֹ��");
   	         	return false;
   	     	}
     }
    
//	var strSQL = "select a.RgtNo, "
//		+" b.customerno, (select name from ldperson where customerno = b.customerno) customername,"
//		+" a.rgtconclusion, (case a.rgtconclusion when '02' then '��������' when '03' then '�ӳ�����'),"
//		+" a.EndCaseDate, a.grpname"
//		+" from LLregister a, LLCase b"
// 		+" where a.rgtno = b.caseno"
//	 	+" and a.rgtconclusion = '02'"	//02��������
//        + getWherePart('a.rgtno','RgtNo')
//        + getWherePart('a.EndCaseDate','StartDate','>=')
//        + getWherePart('a.EndCaseDate','EndDate','<=')
//        + getWherePart('a.mngcom','ManageCom','like')
//		+ " order by a.RgtNo, a.EndCaseDate";
	
	var  RgtNo0 = window.document.getElementsByName(trim("RgtNo"))[0].value;
  	var  StartDate0 = window.document.getElementsByName(trim("StartDate"))[0].value;
  	var  EndDate0 = window.document.getElementsByName(trim("EndDate"))[0].value;
  	var  ManageCom0 = window.document.getElementsByName(trim("ManageCom"))[0].value;
	var sqlid0="ClaimRgtConclusionSql0";
	var mySql0=new SqlClass();
	mySql0.setResourceName("claimprt.ClaimRgtConclusionSql"); //ָ��ʹ�õ�properties�ļ���
	mySql0.setSqlId(sqlid0);//ָ��ʹ�õ�Sql��id
	mySql0.addSubPara(RgtNo0);//ָ������Ĳ���
	mySql0.addSubPara(StartDate0);//ָ������Ĳ���
	mySql0.addSubPara(EndDate0);//ָ������Ĳ���
	mySql0.addSubPara(ManageCom0);//ָ������Ĳ���
	var strSQL=mySql0.getString();

   	turnPage.queryModal(strSQL, QueryResultGrid);
   	//alert("��ѯ���ļ�¼������"+QueryResultGrid.mulLineCount);
   	if(QueryResultGrid.mulLineCount==0)
   	{
   		alert("û�в�ѯ���κε�֤��Ϣ��");
   		return false;
   	}
}

//�����������۴�ӡ:02��������
function PrintClaimRgtPdf()
{
	var selno = QueryResultGrid.getSelNo()-1;
	if (selno <0)
	{
		alert("��ѡ��Ҫ��ӡ���ⰸ��");
		return;
	}
    document.all('RgtNo').value = QueryResultGrid. getRowColData(selno,1);   
    document.all('CustomerNo').value = QueryResultGrid. getRowColData(selno,2); 
    //alert(PolGrid. getRowColData(tSel-1,5));
    
	fm.action = './ClaimRgtConclusionSave.jsp';  
    fm.target="../f1print";
	document.getElementById("fm").submit();
}

