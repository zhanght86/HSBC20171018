//***********************************************
//��������  ImageQuery.js
//�����ܣ�Ӱ�����ϲ�ѯ
//�������ڣ�2005-07-07 11:10:36
//������  ��ccvip
//���¼�¼��  ������    ��������     ����ԭ��/����
//***********************************************

//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�


//ȫ�ֱ���
var showInfo;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();


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
 *  ��ѯ��ͬ��Ϣ 
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
 

function  QueryImage(){ 
	
	var subtype = fm.subtype.value;
	var contno=fm.ContNo.value;
	var scanno = fm.ScanNo.value;
	//alert(subtype);��֤����
	//alert(fm.ContNo.value);
	var aSQL="";
	/*if(subtype!="")
	{
	  if(scanno!=""){
	  
//	    aSQL="select distinct a.bussno,'����Լ',"+
//	          "(select b.SubTypeName from es_doc_def b where b.busstype='TB' and b.subtype=a.subtype),a.docid,"+
//	          "(select c.scanno from es_doc_main c where c.busstype = 'TB' and c.subtype =a.subtype and c.doccode in (select trim(prtno) from lccont where contno = '"+contno+"'))"+
//	          " from es_doc_relation a where subtype='"+subtype+"' and bussno='"+contno+"' and busstype='TB' and exists(select 'x' from es_doc_main where docid=a.docid and scanno like '%%"+scanno+"%%')";					
	  
      aSQL = " select tempa.bussno,"
    	  	+ " tempa.subtype,"
           + " (select b.SubTypeName"
           + " from es_doc_def b"
           + " where b.subtype = tempa.subtype),"
           + " tempa.docid,"
           + " (select c.scanno"
           + " from es_doc_main c"
           + " where c.busstype like 'XB%'"
           + " and c.subtype = tempa.subtype"
           + " and c.docid = tempa.docid),"
           + " (select c.numpages"
           + " from es_doc_main c"
           + " where c.busstype like 'XB%'"
           + " and c.subtype = tempa.subtype"
           + " and c.docid = tempa.docid),"
           //ɨ��ʱ��
           + " (select c.makedate"
           + " from es_doc_main c"
           + " where c.busstype like 'XB%'"
           + " and c.subtype = tempa.subtype"
           + " and c.docid = tempa.docid),"
           + " (select c.maketime"
           + " from es_doc_main c"
           + " where c.busstype like 'XB%'"
           + " and c.subtype = tempa.subtype"
           + " and c.docid = tempa.docid),"
           //ɨ��Ա����
           + " (select c.scanoperator"
           + " from es_doc_main c"
           + " where c.busstype like 'TB%'"
           + " and c.subtype = tempa.subtype"
           + " and c.docid = tempa.docid)"
           + " from (select (select bussno"
           + " from es_doc_relation b"
           + " where b.docid = a.docid"
           + " and b.bussnotype = a.bussnotype) as bussno,"
           + " (select subtype"
           + " from es_doc_relation b"
           + " where b.docid = a.docid"
           + " and b.bussnotype = a.bussnotype) as subtype,"
           + " a.docid"
           + " from (select docid,"
           + " (select max(bussnotype)"
           + " from es_doc_relation"
           + " where docid = c.docid) as bussnotype"
           + " from (select distinct docid"
           + " from es_doc_relation"
           + " where 1 = 1"
           + " and subtype = '"+subtype+"'"
//           + " and bussno = '"+contno+"'"  //��������洢���Ǻ�ͬ�ŷ�ӡˢ�Ź��޸Ĵ˴���ѯ ���� 06-07-20
           + " and bussno in ('"+contno+"',(select trim(contno) from lccont where prtno = '"+contno+"'))"
           + " and busstype like 'XB%'"
           + " and exists"
           + " (select 'x'"
           + " from es_doc_main"
           + " where docid = es_doc_relation.docid"
           + " and scanno like '%%"+scanno+"%%')) c) a) tempa";
	  
	  }
	  else{
//	    aSQL="select distinct a.bussno,'����Լ',"+
//	          "(select b.SubTypeName from es_doc_def b where b.busstype='TB' and b.subtype=a.subtype),a.docid,"+
//	          "(select c.scanno from es_doc_main c where c.busstype = 'TB' and c.subtype =a.subtype and c.doccode in (select trim(prtno) from lccont where contno = '"+contno+"'))"+
//	          " from es_doc_relation a where subtype='"+subtype+"' and bussno='"+contno+"' and busstype='TB'";					
      aSQL = " select tempa.bussno,"
           + " tempa.subtype,"
           + " (select b.SubTypeName"
           + " from es_doc_def b"
           + " where b.subtype = tempa.subtype),"
           + " tempa.docid,"
           + " (select c.scanno"
           + " from es_doc_main c"
           + " where c.busstype like 'XB%'"
           + " and c.subtype = tempa.subtype"
           + " and c.docid = tempa.docid),"
           + " (select c.numpages"
           + " from es_doc_main c"
           + " where c.busstype like 'XB%'"
           + " and c.subtype = tempa.subtype"
           + " and c.docid = tempa.docid),"
           //ɨ��ʱ��
           + " (select c.makedate"
           + " from es_doc_main c"
           + " where c.busstype like 'XB%'"
           + " and c.subtype = tempa.subtype"
           + " and c.docid = tempa.docid),"
           //ɨ��ʱ��
           + " (select c.maketime"
           + " from es_doc_main c"
           + " where c.busstype like 'XB%'"
           + " and c.subtype = tempa.subtype"
           + " and c.docid = tempa.docid),"
           //ɨ��Ա����
           + " (select c.scanoperator"
           + " from es_doc_main c"
           + " where c.busstype like 'XB%'"
           + " and c.subtype = tempa.subtype"
           + " and c.docid = tempa.docid)"
           + " from (select (select bussno"
           + " from es_doc_relation b"
           + " where b.docid = a.docid"
           + " and b.bussnotype = a.bussnotype) as bussno,"
           + " (select subtype"
           + " from es_doc_relation b"
           + " where b.docid = a.docid"
           + " and b.bussnotype = a.bussnotype) as subtype,"
           + " a.docid"
           + " from (select docid,"
           + " (select max(bussnotype)"
           + " from es_doc_relation"
           + " where docid = c.docid) as bussnotype"
           + " from (select distinct docid"
           + " from es_doc_relation"
           + " where 1 = 1"
           + " and subtype = '"+subtype+"'"
//           + " and bussno = '"+contno+"'"  //��������洢���Ǻ�ͬ�ŷ�ӡˢ�Ź��޸Ĵ˴���ѯ ���� 06-07-20
           + " and bussno in ('"+contno+"',(select trim(contno) from lccont where prtno = '"+contno+"'))"
           + " and busstype = 'XB') c) a) tempa";
	          
	          
	  }
  }else{
	  if(scanno!=""){
//      aSQL="select distinct a.bussno,'����Լ',"+
//          "(select b.SubTypeName from es_doc_def b where b.busstype='TB' and b.subtype=a.subtype),a.docid,"+
//          "(select c.scanno from es_doc_main c where c.busstype = 'TB' and c.subtype =a.subtype and c.doccode in (select trim(prtno) from lccont where contno = '"+contno+"'))"+ 
//          " from es_doc_relation a where  bussno='"+contno+"' and busstype='TB' and exists(select 'x' from es_doc_main where docid=a.docid and scanno like '%%"+scanno+"%%')";	
      aSQL = " select tempa.bussno,"
    	  	+ " tempa.subtype,"
           + " (select b.SubTypeName"
           + " from es_doc_def b"
           + " where b.busstype like 'XB%'"
           + " and b.subtype = tempa.subtype),"
           + " tempa.docid,"
           + " (select c.scanno"
           + " from es_doc_main c"
           + " where c.busstype like 'XB%'"
           + " and c.subtype = tempa.subtype"
           + " and c.docid = tempa.docid),"
           + " (select c.numpages"
           + " from es_doc_main c"
           + " where c.busstype like 'XB%'"
           + " and c.subtype = tempa.subtype"
           + " and c.docid = tempa.docid),"
           //ɨ��ʱ��
           + " (select c.makedate"
           + " from es_doc_main c"
           + " where c.busstype like 'XB%'"
           + " and c.subtype = tempa.subtype"
           + " and c.docid = tempa.docid),"
           //ɨ��ʱ��
           + " (select c.maketime"
           + " from es_doc_main c"
           + " where c.busstype like 'XB%'"
           + " and c.subtype = tempa.subtype"
           + " and c.docid = tempa.docid),"
           //ɨ��Ա����
           + " (select c.scanoperator"
           + " from es_doc_main c"
           + " where c.busstype like 'XB%'"
           + " and c.subtype = tempa.subtype"
           + " and c.docid = tempa.docid)"
           + " from (select (select bussno"
           + " from es_doc_relation b"
           + " where b.docid = a.docid"
           + " and b.bussnotype = a.bussnotype) as bussno,"
           + " (select subtype"
           + " from es_doc_relation b"
           + " where b.docid = a.docid"
           + " and b.bussnotype = a.bussnotype) as subtype,"
           + " a.docid"
           + " from (select docid,"
           + " (select max(bussnotype)"
           + " from es_doc_relation"
           + " where docid = c.docid) as bussnotype"
           + " from (select distinct docid"
           + " from es_doc_relation"
           + " where 1 = 1"
//           + " and bussno = '"+contno+"'"  //��������洢���Ǻ�ͬ�ŷ�ӡˢ�Ź��޸Ĵ˴���ѯ ���� 06-07-20
           + " and bussno in ('"+contno+"',(select trim(contno) from lccont where prtno = '"+contno+"'))"
           + " and busstype like 'XB%'"
           + " and exists"
           + " (select 'x'"
           + " from es_doc_main"
           + " where docid = es_doc_relation.docid"
           + " and scanno like '%%"+scanno+"%%')) c) a) tempa";
          
          
	  }
	  else{
      aSQL="select distinct a.bussno,'����Լ',"+
          "(select b.SubTypeName from es_doc_def b where b.busstype='XB' and b.subtype=a.subtype),a.docid,"+
          "(select c.scanno from es_doc_main c where c.busstype = 'XB' and c.subtype =a.subtype and c.doccode in (select trim(prtno) from lccont where contno = '"+contno+"'))"+ 
          " from es_doc_relation a where  bussno='"+contno+"' and busstype='XB'";	
      aSQL = " select tempa.bussno,"
    	  + " tempa.subtype,"
           + " (select b.SubTypeName"
           + " from es_doc_def b"
           + " where b.busstype like 'XB%'"
           + " and b.subtype = tempa.subtype),"
           + " tempa.docid,"
           + " (select c.scanno"
           + " from es_doc_main c"
           + " where c.busstype like 'XB%'"
           + " and c.subtype = tempa.subtype"
           + " and c.docid = tempa.docid),"
           + " (select c.numpages"
           + " from es_doc_main c"
           + " where c.busstype like 'XB%'"
           + " and c.subtype = tempa.subtype"
           + " and c.docid = tempa.docid),"
           //ɨ��ʱ��
           + " (select c.makedate"
           + " from es_doc_main c"
           + " where c.busstype like 'XB%'"
           + " and c.subtype = tempa.subtype"
           + " and c.docid = tempa.docid),"
           //ɨ��ʱ��
           + " (select c.maketime"
           + " from es_doc_main c"
           + " where c.busstype like 'XB%'"
           + " and c.subtype = tempa.subtype"
           + " and c.docid = tempa.docid),"
           //ɨ��Ա����
           + " (select c.scanoperator"
           + " from es_doc_main c"
           + " where c.busstype like 'XB%'"
           + " and c.subtype = tempa.subtype"
           + " and c.docid = tempa.docid)"
           + " from (select (select bussno"
           + " from es_doc_relation b"
           + " where b.docid = a.docid"
           + " and b.bussnotype = a.bussnotype) as bussno,"
           + " (select subtype"
           + " from es_doc_relation b"
           + " where b.docid = a.docid"
           + " and b.bussnotype = a.bussnotype) as subtype,"
           + " a.docid"
           + " from (select docid,"
           + " (select max(bussnotype)"
           + " from es_doc_relation"
           + " where docid = c.docid) as bussnotype"
           + " from (select distinct docid"
           + " from es_doc_relation"
           + " where 1 = 1"
//           + " and bussno = '"+contno+"'"  //��������洢���Ǻ�ͬ�ŷ�ӡˢ�Ź��޸Ĵ˴���ѯ ���� 06-07-20
           + " and bussno in ('"+contno+"',(select trim(contno) from lccont where prtno = '"+contno+"'))"
           + " and busstype = 'XB') c) a) tempa";
	  }
  }*/
	
	var sqlid1="";
	var mySql1 = new SqlClass();
	mySql1.setResourceName("xb.ImageQuerySql"); // ָ��ʹ�õ�properties�ļ���
	if (subtype != "") {
		if (scanno != "") {
			sqlid1="ImageQuerySql1";
			mySql1.setSqlId(sqlid1);// ָ��ʹ�õ�Sql��id
			mySql1.addSubPara(subtype);// ָ������Ĳ���
			mySql1.addSubPara(contno);// ָ������Ĳ���
			mySql1.addSubPara(contno);// ָ������Ĳ���
			mySql1.addSubPara(scanno);// ָ������Ĳ���
		} else {
			sqlid1="ImageQuerySql2";
			mySql1.setSqlId(sqlid1);// ָ��ʹ�õ�Sql��id
			mySql1.addSubPara(subtype);// ָ������Ĳ���
			mySql1.addSubPara(contno);// ָ������Ĳ���
			mySql1.addSubPara(contno);// ָ������Ĳ���
		}
	} else {
		if (scanno != "") {
			sqlid1="ImageQuerySql3";
			mySql1.setSqlId(sqlid1);// ָ��ʹ�õ�Sql��id
			mySql1.addSubPara(contno);// ָ������Ĳ���
			mySql1.addSubPara(contno);// ָ������Ĳ���
			mySql1.addSubPara(scanno);// ָ������Ĳ���
		} else {
			sqlid1="ImageQuerySql4";
			mySql1.setSqlId(sqlid1);// ָ��ʹ�õ�Sql��id
			mySql1.addSubPara(contno);// ָ������Ĳ���
			mySql1.addSubPara(contno);// ָ������Ĳ���
		}
	}
	aSQL = mySql1.getString();
	turnPage.queryModal(aSQL, ImageGrid);  
}  
 

function  showImage(){ 
 //����ͼƬ
 	var tSelNo = ImageGrid.getSelNo()-1;
 	var tdocid = ImageGrid.getRowColData(tSelNo,4);	
 	//alert("tdocid first="+tdocid);
 	fm.DocID.value=tdocid ;
 	//alert("tdocid second="+fm.DocID.value);  
 	top.fraPic.centerPic.innerHTML = "";
 	document.getElementById("fm").submit(); //�ύ    
 
} 
 

 