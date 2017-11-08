//***********************************************
//程序名称  ImageQueryGrp.js
//程序功能：影像资料查询
//创建日期：2007-03-20 11:10:36
//创建人  ：pz
//更新记录：  更新人    更新日期     更新原因/内容
//***********************************************

//               该文件中包含客户端需要处理的函数和事件


//全局变量
var showInfo;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();


/*********************************************************************
 *  返回上一页
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */

function returnParent(){
  top.close();	
	
}


/*********************************************************************
 *  查询合同信息 
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
 

function  QueryImage(){ 
	
	var subtype = fm.subtype.value;
	var contno=fm.ContNo.value;
	var scanno = fm.ScanNo.value;
	//alert(subtype);单证类型
	//alert(fm.ContNo.value);
	var aSQL="";
	if(subtype!="")
	{
	  if(scanno!=""){
	  
//	    aSQL="select distinct a.bussno,'新契约',"+
//	          "(select b.SubTypeName from es_doc_def b where b.busstype='TB' and b.subtype=a.subtype),a.docid,"+
//	          "(select c.scanno from es_doc_main c where c.busstype = 'TB' and c.subtype =a.subtype and c.doccode in (select trim(prtno) from lccont where contno = '"+contno+"'))"+
//	          " from es_doc_relation a where subtype='"+subtype+"' and bussno='"+contno+"' and busstype='TB' and exists(select 'x' from es_doc_main where docid=a.docid and scanno like '%%"+scanno+"%%')";					
	  
      var sqlid825175100="DSHomeContSql825175100";
var mySql825175100=new SqlClass();
mySql825175100.setResourceName("uw.ImageQueryGrpSql");//指定使用的properties文件名
mySql825175100.setSqlId(sqlid825175100);//指定使用的Sql的id
mySql825175100.addSubPara(subtype);//指定传入的参数
mySql825175100.addSubPara(contno);//指定传入的参数
mySql825175100.addSubPara("%%"+scanno+"%%");//指定传入的参数
aSQL=mySql825175100.getString();
      
//      aSQL = " select tempa.bussno,"
//           + " '新契约',"
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
//	    aSQL="select distinct a.bussno,'新契约',"+
//	          "(select b.SubTypeName from es_doc_def b where b.busstype='TB' and b.subtype=a.subtype),a.docid,"+
//	          "(select c.scanno from es_doc_main c where c.busstype = 'TB' and c.subtype =a.subtype and c.doccode in (select trim(prtno) from lccont where contno = '"+contno+"'))"+
//	          " from es_doc_relation a where subtype='"+subtype+"' and bussno='"+contno+"' and busstype='TB'";					
      
      var sqlid825175501="DSHomeContSql825175501";
var mySql825175501=new SqlClass();
mySql825175501.setResourceName("uw.ImageQueryGrpSql");//指定使用的properties文件名
mySql825175501.setSqlId(sqlid825175501);//指定使用的Sql的id
mySql825175501.addSubPara(subtype);//指定传入的参数
mySql825175501.addSubPara(contno);//指定传入的参数
aSQL=mySql825175501.getString();
      
//      aSQL = " select tempa.bussno,"
//           + " '新契约',"
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
//      aSQL="select distinct a.bussno,'新契约',"+
//          "(select b.SubTypeName from es_doc_def b where b.busstype='TB' and b.subtype=a.subtype),a.docid,"+
//          "(select c.scanno from es_doc_main c where c.busstype = 'TB' and c.subtype =a.subtype and c.doccode in (select trim(prtno) from lccont where contno = '"+contno+"'))"+ 
//          " from es_doc_relation a where  bussno='"+contno+"' and busstype='TB' and exists(select 'x' from es_doc_main where docid=a.docid and scanno like '%%"+scanno+"%%')";	
      
      var sqlid825175655="DSHomeContSql825175655";
var mySql825175655=new SqlClass();
mySql825175655.setResourceName("uw.ImageQueryGrpSql");//指定使用的properties文件名
mySql825175655.setSqlId(sqlid825175655);//指定使用的Sql的id
mySql825175655.addSubPara(contno);//指定传入的参数
mySql825175655.addSubPara("%%"+scanno+"%%");//指定传入的参数
aSQL=mySql825175655.getString();
      
//      aSQL = " select tempa.bussno,"
//           + " '新契约',"
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
//      aSQL="select distinct a.bussno,'新契约',"+
//          "(select b.SubTypeName from es_doc_def b where b.busstype='TB' and b.subtype=a.subtype),a.docid,"+
//          "(select c.scanno from es_doc_main c where c.busstype = 'TB' and c.subtype =a.subtype and c.doccode in (select trim(prtno) from lccont where contno = '"+contno+"'))"+ 
//          " from es_doc_relation a where  bussno='"+contno+"' and busstype='TB'";	
      
      
      
      var sqlid825175951="DSHomeContSql825175951";
var mySql825175951=new SqlClass();
mySql825175951.setResourceName("uw.ImageQueryGrpSql");//指定使用的properties文件名
mySql825175951.setSqlId(sqlid825175951);//指定使用的Sql的id
mySql825175951.addSubPara(contno);//指定传入的参数
aSQL=mySql825175951.getString();
      
//      aSQL = " select tempa.bussno,"
//           + " '新契约',"
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
 //调用图片
 	var tSelNo = ImageGrid.getSelNo()-1;
 	var tdocid = ImageGrid.getRowColData(tSelNo,4);	
 	//alert("tdocid first="+tdocid);
 	fm.DocID.value=tdocid ;
 	//alert("tdocid second="+fm.DocID.value);  
 	top.fraPic.centerPic.innerHTML = "";
 	document.getElementById("fm").submit(); //提交    
 
} 
 

 