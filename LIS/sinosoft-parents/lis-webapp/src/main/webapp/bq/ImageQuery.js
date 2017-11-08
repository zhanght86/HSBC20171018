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
	var bussno=fm.BussNo.value;
	var scanno = fm.ScanNo.value;
	
	
	
		var aSQL = "";
		if(subtype!=null && subtype!=""){
			var sqlid902174847="DSHomeContSql902174847";
			var mySql902174847=new SqlClass();
			mySql902174847.setResourceName("bq.ImageQuerySql");//指定使用的properties文件名
			mySql902174847.setSqlId(sqlid902174847);//指定使用的Sql的id
			mySql902174847.addSubPara(bussno);//指定传入的参数
			mySql902174847.addSubPara(subtype);//指定传入的参数
			aSQL=mySql902174847.getString();
			
		}else{
			var sqlid1="ImageQuerySql1";
			var mySql1=new SqlClass();
			mySql1.setResourceName("bq.ImageQuerySql"); //指定使用的properties文件名
			mySql1.setSqlId(sqlid1);//指定使用的Sql的id
			mySql1.addSubPara(bussno);//指定传入的参数
		  aSQL=mySql1.getString();	
		}
	
		
//		if(subtype!=null && subtype!=""){
//			aSQL+= " and a.subtype=''"+subtype+"''";
//		}
//	
//	var aSQL="select a.doccode,a.subtype,(select subtypename from es_doc_def where subtype=a.subtype),a.docid,a.scanno,"
//		+"(select count(1) from es_doc_pages where docid=a.docid),a.makedate,a.maketime,a.scanoperator"
//		+" from es_doc_relation b,es_doc_main a where a.DocID = b.DocID and b.bussno='"+bussno+"'";

	
	
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
 
function initImageType(){
	
			var sqlid2="ImageQuerySql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("bq.ImageQuerySql"); //指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
		mySql2.addSubPara(BussType);//指定传入的参数
	    var strSql=mySql2.getString();	
	
//	 var strSql = "select subtype,subtypename from es_doc_def where busstype like '"+BussType+"%' and validflag in ('0','2') order by subtype"; 
	  
	  var strResult = easyQueryVer3(strSql);
	  //alert(strResult);

	  if (strResult) {
	    document.all("subtype").CodeData = strResult;
	  }
}
 