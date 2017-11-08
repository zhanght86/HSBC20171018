//***********************************************
//程序名称  ImageQuery.js
//程序功能：影像资料查询
//创建日期：2005-07-07 11:10:36
//创建人  ：ccvip
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
	  

	var sqlid49="QuestInputSql49";
var mySql49=new SqlClass();
mySql49.setResourceName("uw.QuestInputSql"); //指定使用的properties文件名
mySql49.setSqlId(sqlid49);//指定使用的Sql的id
mySql49.addSubPara(subtype);//指定传入的参数
mySql49.addSubPara(contno);//指定传入的参数
mySql49.addSubPara(contno);//指定传入的参数
mySql49.addSubPara(scanno);//指定传入的参数

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
//           //扫描时间
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
//           //扫描员工号
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
mySql50.setResourceName("uw.QuestInputSql"); //指定使用的properties文件名
mySql50.setSqlId(sqlid50);//指定使用的Sql的id
mySql50.addSubPara(subtype);//指定传入的参数
mySql50.addSubPara(contno);//指定传入的参数
mySql50.addSubPara(contno);//指定传入的参数

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
//           //扫描时间
//           + " (select c.makedate"
//           + " from es_doc_main c"
//           + " where c.busstype like 'TB%'"
//           + " and c.subtype = tempa.subtype"
//           + " and c.docid = tempa.docid),"
//           //扫描时间
//           + " (select c.maketime"
//           + " from es_doc_main c"
//           + " where c.busstype like 'TB%'"
//           + " and c.subtype = tempa.subtype"
//           + " and c.docid = tempa.docid),"
//           //扫描员工号
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
////           + " and bussno = '"+contno+"'"  //由于理赔存储的是合同号非印刷号故修改此处查询 周磊 06-07-20
//           + " and bussno in (select '"+contno+"' from dual union all select trim(contno) from lccont where prtno = '"+contno+"')"
//           + " and busstype = 'TB') c) a) tempa";
	          
	          
	  }
  }else{
	  if(scanno!=""){
	  	
			var sqlid51="QuestInputSql51";
var mySql51=new SqlClass();
mySql51.setResourceName("uw.QuestInputSql"); //指定使用的properties文件名
mySql51.setSqlId(sqlid51);//指定使用的Sql的id

mySql51.addSubPara(contno);//指定传入的参数
mySql51.addSubPara(contno);//指定传入的参数
mySql51.addSubPara(subtype);//指定传入的参数
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
//           //扫描时间
//           + " (select c.makedate"
//           + " from es_doc_main c"
//           + " where c.busstype like 'TB%'"
//           + " and c.subtype = tempa.subtype"
//           + " and c.docid = tempa.docid),"
//           //扫描时间
//           + " (select c.maketime"
//           + " from es_doc_main c"
//           + " where c.busstype like 'TB%'"
//           + " and c.subtype = tempa.subtype"
//           + " and c.docid = tempa.docid),"
//           //扫描员工号
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
////           + " and bussno = '"+contno+"'"  //由于理赔存储的是合同号非印刷号故修改此处查询 周磊 06-07-20
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
mySql52.setResourceName("uw.QuestInputSql"); //指定使用的properties文件名
mySql52.setSqlId(sqlid52);//指定使用的Sql的id

mySql52.addSubPara(contno);//指定传入的参数
mySql52.addSubPara(contno);//指定传入的参数
aSQL=mySql52.getString();	
		
		
//      aSQL="select distinct a.bussno,'新契约',"+
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
//           //扫描时间
//           + " (select c.makedate"
//           + " from es_doc_main c"
//           + " where c.busstype like 'TB%'"
//           + " and c.subtype = tempa.subtype"
//           + " and c.docid = tempa.docid),"
//           //扫描时间
//           + " (select c.maketime"
//           + " from es_doc_main c"
//           + " where c.busstype like 'TB%'"
//           + " and c.subtype = tempa.subtype"
//           + " and c.docid = tempa.docid),"
//           //扫描员工号
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
////           + " and bussno = '"+contno+"'"  //由于理赔存储的是合同号非印刷号故修改此处查询 周磊 06-07-20
//           + " and bussno in (select '"+contno+"' from dual union all select trim(contno) from lccont where prtno = '"+contno+"')"
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
 

 