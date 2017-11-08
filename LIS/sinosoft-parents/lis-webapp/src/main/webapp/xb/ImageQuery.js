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
	/*if(subtype!="")
	{
	  if(scanno!=""){
	  
//	    aSQL="select distinct a.bussno,'新契约',"+
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
           //扫描时间
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
           //扫描员工号
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
//           + " and bussno = '"+contno+"'"  //由于理赔存储的是合同号非印刷号故修改此处查询 周磊 06-07-20
           + " and bussno in ('"+contno+"',(select trim(contno) from lccont where prtno = '"+contno+"'))"
           + " and busstype like 'XB%'"
           + " and exists"
           + " (select 'x'"
           + " from es_doc_main"
           + " where docid = es_doc_relation.docid"
           + " and scanno like '%%"+scanno+"%%')) c) a) tempa";
	  
	  }
	  else{
//	    aSQL="select distinct a.bussno,'新契约',"+
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
           //扫描时间
           + " (select c.makedate"
           + " from es_doc_main c"
           + " where c.busstype like 'XB%'"
           + " and c.subtype = tempa.subtype"
           + " and c.docid = tempa.docid),"
           //扫描时间
           + " (select c.maketime"
           + " from es_doc_main c"
           + " where c.busstype like 'XB%'"
           + " and c.subtype = tempa.subtype"
           + " and c.docid = tempa.docid),"
           //扫描员工号
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
//           + " and bussno = '"+contno+"'"  //由于理赔存储的是合同号非印刷号故修改此处查询 周磊 06-07-20
           + " and bussno in ('"+contno+"',(select trim(contno) from lccont where prtno = '"+contno+"'))"
           + " and busstype = 'XB') c) a) tempa";
	          
	          
	  }
  }else{
	  if(scanno!=""){
//      aSQL="select distinct a.bussno,'新契约',"+
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
           //扫描时间
           + " (select c.makedate"
           + " from es_doc_main c"
           + " where c.busstype like 'XB%'"
           + " and c.subtype = tempa.subtype"
           + " and c.docid = tempa.docid),"
           //扫描时间
           + " (select c.maketime"
           + " from es_doc_main c"
           + " where c.busstype like 'XB%'"
           + " and c.subtype = tempa.subtype"
           + " and c.docid = tempa.docid),"
           //扫描员工号
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
//           + " and bussno = '"+contno+"'"  //由于理赔存储的是合同号非印刷号故修改此处查询 周磊 06-07-20
           + " and bussno in ('"+contno+"',(select trim(contno) from lccont where prtno = '"+contno+"'))"
           + " and busstype like 'XB%'"
           + " and exists"
           + " (select 'x'"
           + " from es_doc_main"
           + " where docid = es_doc_relation.docid"
           + " and scanno like '%%"+scanno+"%%')) c) a) tempa";
          
          
	  }
	  else{
      aSQL="select distinct a.bussno,'新契约',"+
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
           //扫描时间
           + " (select c.makedate"
           + " from es_doc_main c"
           + " where c.busstype like 'XB%'"
           + " and c.subtype = tempa.subtype"
           + " and c.docid = tempa.docid),"
           //扫描时间
           + " (select c.maketime"
           + " from es_doc_main c"
           + " where c.busstype like 'XB%'"
           + " and c.subtype = tempa.subtype"
           + " and c.docid = tempa.docid),"
           //扫描员工号
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
//           + " and bussno = '"+contno+"'"  //由于理赔存储的是合同号非印刷号故修改此处查询 周磊 06-07-20
           + " and bussno in ('"+contno+"',(select trim(contno) from lccont where prtno = '"+contno+"'))"
           + " and busstype = 'XB') c) a) tempa";
	  }
  }*/
	
	var sqlid1="";
	var mySql1 = new SqlClass();
	mySql1.setResourceName("xb.ImageQuerySql"); // 指定使用的properties文件名
	if (subtype != "") {
		if (scanno != "") {
			sqlid1="ImageQuerySql1";
			mySql1.setSqlId(sqlid1);// 指定使用的Sql的id
			mySql1.addSubPara(subtype);// 指定传入的参数
			mySql1.addSubPara(contno);// 指定传入的参数
			mySql1.addSubPara(contno);// 指定传入的参数
			mySql1.addSubPara(scanno);// 指定传入的参数
		} else {
			sqlid1="ImageQuerySql2";
			mySql1.setSqlId(sqlid1);// 指定使用的Sql的id
			mySql1.addSubPara(subtype);// 指定传入的参数
			mySql1.addSubPara(contno);// 指定传入的参数
			mySql1.addSubPara(contno);// 指定传入的参数
		}
	} else {
		if (scanno != "") {
			sqlid1="ImageQuerySql3";
			mySql1.setSqlId(sqlid1);// 指定使用的Sql的id
			mySql1.addSubPara(contno);// 指定传入的参数
			mySql1.addSubPara(contno);// 指定传入的参数
			mySql1.addSubPara(scanno);// 指定传入的参数
		} else {
			sqlid1="ImageQuerySql4";
			mySql1.setSqlId(sqlid1);// 指定使用的Sql的id
			mySql1.addSubPara(contno);// 指定传入的参数
			mySql1.addSubPara(contno);// 指定传入的参数
		}
	}
	aSQL = mySql1.getString();
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
 

 