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
	if(subtype!="")
	{
	  if(scanno!=""){
	  

	var sqlid49="QuestInputSql49";
var mySql49=new SqlClass();
mySql49.setResourceName("uw.QuestInputSql"); //ָ��ʹ�õ�properties�ļ���
mySql49.setSqlId(sqlid49);//ָ��ʹ�õ�Sql��id
mySql49.addSubPara(subtype);//ָ������Ĳ���
mySql49.addSubPara(contno);//ָ������Ĳ���
mySql49.addSubPara(contno);//ָ������Ĳ���
mySql49.addSubPara(scanno);//ָ������Ĳ���

aSQL=mySql49.getString();		 

//      aSQL = " select tempa.bussno,"
//    	  	+ " tempa.subtype,"
//           + " (select b.SubTypeName"
//           + " from es_doc_def b"
//           + " where b.subtype = tempa.subtype),"
//           + " tempa.docid,"
//           + " (select c.scanno"
//           + " from es_doc_main c"
//           + " where c.busstype like 'TB%'"
//           + " and c.subtype = tempa.subtype"
//           + " and c.docid = tempa.docid),"
//           + " (select c.numpages"
//           + " from es_doc_main c"
//           + " where c.busstype like 'TB%'"
//           + " and c.subtype = tempa.subtype"
//           + " and c.docid = tempa.docid),"
//           //ɨ��ʱ��
//           + " (select c.makedate"
//           + " from es_doc_main c"
//           + " where c.busstype like 'TB%'"
//           + " and c.subtype = tempa.subtype"
//           + " and c.docid = tempa.docid),"
//           + " (select c.maketime"
//           + " from es_doc_main c"
//           + " where c.busstype like 'TB%'"
//           + " and c.subtype = tempa.subtype"
//           + " and c.docid = tempa.docid),"
//           //ɨ��Ա����
//           + " (select c.scanoperator"
//           + " from es_doc_main c"
//           + " where c.busstype like 'TB%'"
//           + " and c.subtype = tempa.subtype"
//           + " and c.docid = tempa.docid)"
//           + " from (select (select bussno"
//           + " from es_doc_relation b"
//           + " where b.docid = a.docid"
//           + " and b.bussnotype = a.bussnotype) as bussno,"
//           + " (select subtype"
//           + " from es_doc_relation b"
//           + " where b.docid = a.docid"
//           + " and b.bussnotype = a.bussnotype) as subtype,"
//           + " a.docid"
//           + " from (select docid,"
//           + " (select max(bussnotype)"
//           + " from es_doc_relation"
//           + " where docid = c.docid) as bussnotype"
//           + " from (select distinct docid"
//           + " from es_doc_relation"
//           + " where 1 = 1"
//           + " and subtype = '"+subtype+"'"
//           + " and bussno in (select '"+contno+"' from dual union all select trim(contno) from lccont where prtno = '"+contno+"')"
//           + " and busstype like 'TB%'"
//           + " and exists"
//           + " (select 'x'"
//           + " from es_doc_main"
//           + " where docid = es_doc_relation.docid"
//           + " and scanno like '%%"+scanno+"%%')) c) a) tempa";
	  
	  }
	  else{
			
			
	var sqlid50="QuestInputSql50";
var mySql50=new SqlClass();
mySql50.setResourceName("uw.QuestInputSql"); //ָ��ʹ�õ�properties�ļ���
mySql50.setSqlId(sqlid50);//ָ��ʹ�õ�Sql��id
mySql50.addSubPara(subtype);//ָ������Ĳ���
mySql50.addSubPara(contno);//ָ������Ĳ���
mySql50.addSubPara(contno);//ָ������Ĳ���

aSQL=mySql50.getString();	
			
//      aSQL = " select tempa.bussno,"
//           + " tempa.subtype,"
//           + " (select b.SubTypeName"
//           + " from es_doc_def b"
//           + " where b.subtype = tempa.subtype),"
//           + " tempa.docid,"
//           + " (select c.scanno"
//           + " from es_doc_main c"
//           + " where c.busstype like 'TB%'"
//           + " and c.subtype = tempa.subtype"
//           + " and c.docid = tempa.docid),"
//           + " (select c.numpages"
//           + " from es_doc_main c"
//           + " where c.busstype like 'TB%'"
//           + " and c.subtype = tempa.subtype"
//           + " and c.docid = tempa.docid),"
//           //ɨ��ʱ��
//           + " (select c.makedate"
//           + " from es_doc_main c"
//           + " where c.busstype like 'TB%'"
//           + " and c.subtype = tempa.subtype"
//           + " and c.docid = tempa.docid),"
//           //ɨ��ʱ��
//           + " (select c.maketime"
//           + " from es_doc_main c"
//           + " where c.busstype like 'TB%'"
//           + " and c.subtype = tempa.subtype"
//           + " and c.docid = tempa.docid),"
//           //ɨ��Ա����
//           + " (select c.scanoperator"
//           + " from es_doc_main c"
//           + " where c.busstype like 'TB%'"
//           + " and c.subtype = tempa.subtype"
//           + " and c.docid = tempa.docid)"
//           + " from (select (select bussno"
//           + " from es_doc_relation b"
//           + " where b.docid = a.docid"
//           + " and b.bussnotype = a.bussnotype) as bussno,"
//           + " (select subtype"
//           + " from es_doc_relation b"
//           + " where b.docid = a.docid"
//           + " and b.bussnotype = a.bussnotype) as subtype,"
//           + " a.docid"
//           + " from (select docid,"
//           + " (select max(bussnotype)"
//           + " from es_doc_relation"
//           + " where docid = c.docid) as bussnotype"
//           + " from (select distinct docid"
//           + " from es_doc_relation"
//           + " where 1 = 1"
//           + " and subtype = '"+subtype+"'"
////           + " and bussno = '"+contno+"'"  //��������洢���Ǻ�ͬ�ŷ�ӡˢ�Ź��޸Ĵ˴���ѯ ���� 06-07-20
//           + " and bussno in (select '"+contno+"' from dual union all select trim(contno) from lccont where prtno = '"+contno+"')"
//           + " and busstype = 'TB') c) a) tempa";
	          
	          
	  }
  }else{
	  if(scanno!=""){
	  	
			var sqlid51="QuestInputSql51";
var mySql51=new SqlClass();
mySql51.setResourceName("uw.QuestInputSql"); //ָ��ʹ�õ�properties�ļ���
mySql51.setSqlId(sqlid51);//ָ��ʹ�õ�Sql��id

mySql51.addSubPara(contno);//ָ������Ĳ���
mySql51.addSubPara(contno);//ָ������Ĳ���
mySql51.addSubPara(subtype);//ָ������Ĳ���
aSQL=mySql51.getString();	
		
//      aSQL = " select tempa.bussno,"
//    	  	+ " tempa.subtype,"
//           + " (select b.SubTypeName"
//           + " from es_doc_def b"
//           + " where b.busstype like 'TB%'"
//           + " and b.subtype = tempa.subtype),"
//           + " tempa.docid,"
//           + " (select c.scanno"
//           + " from es_doc_main c"
//           + " where c.busstype like 'TB%'"
//           + " and c.subtype = tempa.subtype"
//           + " and c.docid = tempa.docid),"
//           + " (select c.numpages"
//           + " from es_doc_main c"
//           + " where c.busstype like 'TB%'"
//           + " and c.subtype = tempa.subtype"
//           + " and c.docid = tempa.docid),"
//           //ɨ��ʱ��
//           + " (select c.makedate"
//           + " from es_doc_main c"
//           + " where c.busstype like 'TB%'"
//           + " and c.subtype = tempa.subtype"
//           + " and c.docid = tempa.docid),"
//           //ɨ��ʱ��
//           + " (select c.maketime"
//           + " from es_doc_main c"
//           + " where c.busstype like 'TB%'"
//           + " and c.subtype = tempa.subtype"
//           + " and c.docid = tempa.docid),"
//           //ɨ��Ա����
//           + " (select c.scanoperator"
//           + " from es_doc_main c"
//           + " where c.busstype like 'TB%'"
//           + " and c.subtype = tempa.subtype"
//           + " and c.docid = tempa.docid)"
//           + " from (select (select bussno"
//           + " from es_doc_relation b"
//           + " where b.docid = a.docid"
//           + " and b.bussnotype = a.bussnotype) as bussno,"
//           + " (select subtype"
//           + " from es_doc_relation b"
//           + " where b.docid = a.docid"
//           + " and b.bussnotype = a.bussnotype) as subtype,"
//           + " a.docid"
//           + " from (select docid,"
//           + " (select max(bussnotype)"
//           + " from es_doc_relation"
//           + " where docid = c.docid) as bussnotype"
//           + " from (select distinct docid"
//           + " from es_doc_relation"
//           + " where 1 = 1"
////           + " and bussno = '"+contno+"'"  //��������洢���Ǻ�ͬ�ŷ�ӡˢ�Ź��޸Ĵ˴���ѯ ���� 06-07-20
//           + " and bussno in (select '"+contno+"' from dual union all select trim(contno) from lccont where prtno = '"+contno+"')"
//           + " and busstype like 'TB%'"
//           + " and exists"
//           + " (select 'x'"
//           + " from es_doc_main"
//           + " where docid = es_doc_relation.docid"
//           + " and scanno like '%%"+scanno+"%%')) c) a) tempa";
          
          
	  }
	  else{
	  	
var sqlid52="QuestInputSql52";
var mySql52=new SqlClass();
mySql52.setResourceName("uw.QuestInputSql"); //ָ��ʹ�õ�properties�ļ���
mySql52.setSqlId(sqlid52);//ָ��ʹ�õ�Sql��id

mySql52.addSubPara(contno);//ָ������Ĳ���
mySql52.addSubPara(contno);//ָ������Ĳ���
aSQL=mySql52.getString();	
		
		
//      aSQL="select distinct a.bussno,'����Լ',"+
//          "(select b.SubTypeName from es_doc_def b where b.busstype='TB' and b.subtype=a.subtype),a.docid,"+
//          "(select c.scanno from es_doc_main c where c.busstype = 'TB' and c.subtype =a.subtype and c.doccode in (select trim(prtno) from lccont where contno = '"+contno+"'))"+ 
//          " from es_doc_relation a where  bussno='"+contno+"' and busstype='TB'";	
//      aSQL = " select tempa.bussno,"
//    	  + " tempa.subtype,"
//           + " (select b.SubTypeName"
//           + " from es_doc_def b"
//           + " where b.busstype like 'TB%'"
//           + " and b.subtype = tempa.subtype),"
//           + " tempa.docid,"
//           + " (select c.scanno"
//           + " from es_doc_main c"
//           + " where c.busstype like 'TB%'"
//           + " and c.subtype = tempa.subtype"
//           + " and c.docid = tempa.docid),"
//           + " (select c.numpages"
//           + " from es_doc_main c"
//           + " where c.busstype like 'TB%'"
//           + " and c.subtype = tempa.subtype"
//           + " and c.docid = tempa.docid),"
//           //ɨ��ʱ��
//           + " (select c.makedate"
//           + " from es_doc_main c"
//           + " where c.busstype like 'TB%'"
//           + " and c.subtype = tempa.subtype"
//           + " and c.docid = tempa.docid),"
//           //ɨ��ʱ��
//           + " (select c.maketime"
//           + " from es_doc_main c"
//           + " where c.busstype like 'TB%'"
//           + " and c.subtype = tempa.subtype"
//           + " and c.docid = tempa.docid),"
//           //ɨ��Ա����
//           + " (select c.scanoperator"
//           + " from es_doc_main c"
//           + " where c.busstype like 'TB%'"
//           + " and c.subtype = tempa.subtype"
//           + " and c.docid = tempa.docid)"
//           + " from (select (select bussno"
//           + " from es_doc_relation b"
//           + " where b.docid = a.docid"
//           + " and b.bussnotype = a.bussnotype) as bussno,"
//           + " (select subtype"
//           + " from es_doc_relation b"
//           + " where b.docid = a.docid"
//           + " and b.bussnotype = a.bussnotype) as subtype,"
//           + " a.docid"
//           + " from (select docid,"
//           + " (select max(bussnotype)"
//           + " from es_doc_relation"
//           + " where docid = c.docid) as bussnotype"
//           + " from (select distinct docid"
//           + " from es_doc_relation"
//           + " where 1 = 1"
////           + " and bussno = '"+contno+"'"  //��������洢���Ǻ�ͬ�ŷ�ӡˢ�Ź��޸Ĵ˴���ѯ ���� 06-07-20
//           + " and bussno in (select '"+contno+"' from dual union all select trim(contno) from lccont where prtno = '"+contno+"')"
//           + " and busstype = 'TB') c) a) tempa";
          
          
	  }
          
  }
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
 

 