//
//�������ƣ�NotBackQuery.js
//�����ܣ�δ¼�����ݲ�ѯ
//�������ڣ�2008-09-03 10:10
//������  ��HULY
//���¼�¼��  ������    ��������     ����ԭ��/����

//var arrDataSet;
var turnPage = new turnPageClass();

//�򵥲�ѯ

function easyPrint()
{
	easyQueryPrint(2,'CodeGrid','turnPage');
}

function easyQuery()
{	
	if (fm.StartDate.value == "" || fm.EndDate.value == "" )
	{
			alert("��ʼʱ��ͽ���ʱ�䲻��Ϊ��!");
			return false;
	}
	
	initCodeGrid();
	
	// ��дSQL���
	if(fm.BPOID.value == null||fm.BPOID.value == ""){
//	var strSQL = "select doccode,managecom,to_char(makedate,'yyyy-mm-dd'),maketime "
//             + " from es_doc_main "
//             + " where inputstate is null  and (substr(doccode, 3, 2) in ('32', '31') or doccode like '532002%') "
//             + " and busstype ='OF' "
//             + getWherePart('makedate','StartDate','>=')
//             + getWherePart('makedate','EndDate','<=')
//             + getWherePart('ManageCom','ManageCom','like')
//             + " and managecom like '"+comCode+"%%' "
//             +  "order by makedate,maketime";
		
		var  StartDate= window.document.getElementsByName(trim("StartDate"))[0].value;
	  	var  EndDate = window.document.getElementsByName(trim("EndDate"))[0].value;
	  	var  ManageCom = window.document.getElementsByName(trim("ManageCom"))[0].value;
		var sqlid1="NotBackQuerySql0";
		var mySql1=new SqlClass();
		mySql1.setResourceName("app.NotBackQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(StartDate);//ָ������Ĳ���
		mySql1.addSubPara(EndDate);//ָ������Ĳ���
		mySql1.addSubPara(ManageCom);//ָ������Ĳ���
		mySql1.addSubPara(comCode);//ָ������Ĳ���
		var strSQL=mySql1.getString();
       }	    
       else{
       var tBPOID = fm.BPOID.value;
//        var strSQL = "select doccode,managecom,to_char(makedate,'yyyy-mm-dd'),maketime "
//             + " from es_doc_main "
//             + " where inputstate is null  and (substr(doccode, 3, 2) in ('32', '31') or doccode like '532002%') "
//             + " and busstype ='OF' "
//             + getWherePart('makedate','StartDate','>=')
//             + getWherePart('makedate','EndDate','<=')
//             + getWherePart('ManageCom','ManageCom','like')
//             + " and managecom like '"+comCode+"%%' "
//             + " and exists (select 1 from bpoallotrate where trim(bpoallotrate.managecom)=substr(es_doc_main.managecom,1,char_length(trim(bpoallotrate.managecom))) and BPOID='"+tBPOID+"')"
//             +  "order by makedate,maketime";
        
        var  StartDate= window.document.getElementsByName(trim("StartDate"))[0].value;
	  	var  EndDate = window.document.getElementsByName(trim("EndDate"))[0].value;
	  	var  ManageCom = window.document.getElementsByName(trim("ManageCom"))[0].value;
		var sqlid2="NotBackQuerySql1";
		var mySql2=new SqlClass();
		mySql2.setResourceName("app.NotBackQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(StartDate);//ָ������Ĳ���
		mySql2.addSubPara(EndDate);//ָ������Ĳ���
		mySql2.addSubPara(ManageCom);//ָ������Ĳ���
		mySql2.addSubPara(comCode);//ָ������Ĳ���
		mySql2.addSubPara(tBPOID);//ָ������Ĳ���
		var strSQL=mySql2.getString();
       }
//  prompt('',strSQL);
	turnPage.queryModal(strSQL, CodeGrid);

}


