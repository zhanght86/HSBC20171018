//***********************************************
//��������  ImageQueryGrp.js
//�����ܣ�Ӱ�����ϲ�ѯ
//�������ڣ�2007-03-20 11:10:36
//������  ��pz
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
	  
//	    aSQL="select distinct a.bussno,'����Լ',"+
//	          "(select b.SubTypeName from es_doc_def b where b.busstype='TB' and b.subtype=a.subtype),a.docid,"+
//	          "(select c.scanno from es_doc_main c where c.busstype = 'TB' and c.subtype =a.subtype and c.doccode in (select trim(prtno) from lccont where contno = '"+contno+"'))"+
//	          " from es_doc_relation a where subtype='"+subtype+"' and bussno='"+contno+"' and busstype='TB' and exists(select 'x' from es_doc_main where docid=a.docid and scanno like '%%"+scanno+"%%')";					
	  
      var sqlid825175100="DSHomeContSql825175100";
var mySql825175100=new SqlClass();
mySql825175100.setResourceName("uw.ImageQueryGrpSql");//ָ��ʹ�õ�properties�ļ���
mySql825175100.setSqlId(sqlid825175100);//ָ��ʹ�õ�Sql��id
mySql825175100.addSubPara(subtype);//ָ������Ĳ���
mySql825175100.addSubPara(contno);//ָ������Ĳ���
mySql825175100.addSubPara("%%"+scanno+"%%");//ָ������Ĳ���
aSQL=mySql825175100.getString();
      
//      aSQL = " select tempa.bussno,"
//           + " '����Լ',"
//           + " (select b.SubTypeName"
//           + " from es_doc_def b"
//           + " where b.busstype = 'TB'"
//           + " and b.subtype = tempa.subtype),"
//           + " tempa.docid,"
//           + " (select c.scanno"
//           + " from es_doc_main c"
//           + " where c.busstype = 'TB'"
//           + " and c.subtype = tempa.subtype"
//           + " and c.docid = tempa.docid),"
//           + " (select c.numpages"
//           + " from es_doc_main c"
//           + " where c.busstype = 'TB'"
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
//           + " and bussno = '"+contno+"'" 
//           + " and busstype = 'TB'"
//           + " and exists"
//           + " (select 'x'"
//           + " from es_doc_main"
//           + " where docid = es_doc_relation.docid"
//           + " and scanno like '%%"+scanno+"%%')) c) a) tempa";
	  
	  }
	  else{
//	    aSQL="select distinct a.bussno,'����Լ',"+
//	          "(select b.SubTypeName from es_doc_def b where b.busstype='TB' and b.subtype=a.subtype),a.docid,"+
//	          "(select c.scanno from es_doc_main c where c.busstype = 'TB' and c.subtype =a.subtype and c.doccode in (select trim(prtno) from lccont where contno = '"+contno+"'))"+
//	          " from es_doc_relation a where subtype='"+subtype+"' and bussno='"+contno+"' and busstype='TB'";					
      
      var sqlid825175501="DSHomeContSql825175501";
var mySql825175501=new SqlClass();
mySql825175501.setResourceName("uw.ImageQueryGrpSql");//ָ��ʹ�õ�properties�ļ���
mySql825175501.setSqlId(sqlid825175501);//ָ��ʹ�õ�Sql��id
mySql825175501.addSubPara(subtype);//ָ������Ĳ���
mySql825175501.addSubPara(contno);//ָ������Ĳ���
aSQL=mySql825175501.getString();
      
//      aSQL = " select tempa.bussno,"
//           + " '����Լ',"
//           + " (select b.SubTypeName"
//           + " from es_doc_def b"
//           + " where b.busstype = 'TB'"
//           + " and b.subtype = tempa.subtype),"
//           + " tempa.docid,"
//           + " (select c.scanno"
//           + " from es_doc_main c"
//           + " where c.busstype = 'TB'"
//           + " and c.subtype = tempa.subtype"
//           + " and c.docid = tempa.docid),"
//           + " (select c.numpages"
//           + " from es_doc_main c"
//           + " where c.busstype = 'TB'"
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
//           + " and bussno = '"+contno+"'" 
//           + " and busstype = 'TB') c) a) tempa";
	          
	          
	  }
  }else{
	  if(scanno!=""){
//      aSQL="select distinct a.bussno,'����Լ',"+
//          "(select b.SubTypeName from es_doc_def b where b.busstype='TB' and b.subtype=a.subtype),a.docid,"+
//          "(select c.scanno from es_doc_main c where c.busstype = 'TB' and c.subtype =a.subtype and c.doccode in (select trim(prtno) from lccont where contno = '"+contno+"'))"+ 
//          " from es_doc_relation a where  bussno='"+contno+"' and busstype='TB' and exists(select 'x' from es_doc_main where docid=a.docid and scanno like '%%"+scanno+"%%')";	
      
      var sqlid825175655="DSHomeContSql825175655";
var mySql825175655=new SqlClass();
mySql825175655.setResourceName("uw.ImageQueryGrpSql");//ָ��ʹ�õ�properties�ļ���
mySql825175655.setSqlId(sqlid825175655);//ָ��ʹ�õ�Sql��id
mySql825175655.addSubPara(contno);//ָ������Ĳ���
mySql825175655.addSubPara("%%"+scanno+"%%");//ָ������Ĳ���
aSQL=mySql825175655.getString();
      
//      aSQL = " select tempa.bussno,"
//           + " '����Լ',"
//           + " (select b.SubTypeName"
//           + " from es_doc_def b"
//           + " where b.busstype = 'TB'"
//           + " and b.subtype = tempa.subtype),"
//           + " tempa.docid,"
//           + " (select c.scanno"
//           + " from es_doc_main c"
//           + " where c.busstype = 'TB'"
//           + " and c.subtype = tempa.subtype"
//           + " and c.docid = tempa.docid),"
//           + " (select c.numpages"
//           + " from es_doc_main c"
//           + " where c.busstype = 'TB'"
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
//           + " and bussno = '"+contno+"'"  
//           + " and busstype = 'TB'"
//           + " and exists"
//           + " (select 'x'"
//           + " from es_doc_main"
//           + " where docid = es_doc_relation.docid"
//           + " and scanno like '%%"+scanno+"%%')) c) a) tempa";
          
          
	  }
	  else{
//      aSQL="select distinct a.bussno,'����Լ',"+
//          "(select b.SubTypeName from es_doc_def b where b.busstype='TB' and b.subtype=a.subtype),a.docid,"+
//          "(select c.scanno from es_doc_main c where c.busstype = 'TB' and c.subtype =a.subtype and c.doccode in (select trim(prtno) from lccont where contno = '"+contno+"'))"+ 
//          " from es_doc_relation a where  bussno='"+contno+"' and busstype='TB'";	
      
      
      
      var sqlid825175951="DSHomeContSql825175951";
var mySql825175951=new SqlClass();
mySql825175951.setResourceName("uw.ImageQueryGrpSql");//ָ��ʹ�õ�properties�ļ���
mySql825175951.setSqlId(sqlid825175951);//ָ��ʹ�õ�Sql��id
mySql825175951.addSubPara(contno);//ָ������Ĳ���
aSQL=mySql825175951.getString();
      
//      aSQL = " select tempa.bussno,"
//           + " '����Լ',"
//           + " (select b.SubTypeName"
//           + " from es_doc_def b"
//           + " where b.busstype = 'TB'"
//           + " and b.subtype = tempa.subtype),"
//           + " tempa.docid,"
//           + " (select c.scanno"
//           + " from es_doc_main c"
//           + " where c.busstype = 'TB'"
//           + " and c.subtype = tempa.subtype"
//           + " and c.docid = tempa.docid),"
//           + " (select c.numpages"
//           + " from es_doc_main c"
//           + " where c.busstype = 'TB'"
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
//           + " and bussno = '"+contno+"'"  
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
 

 