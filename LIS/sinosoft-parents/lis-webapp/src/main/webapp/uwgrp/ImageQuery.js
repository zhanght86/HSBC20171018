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
var sqlresourcename = "uwgrp.ImageQuerySql";

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
	//alert(subtype);单证类型
	//alert(fm.ContNo.value);
	var aSQL="";
	if(subtype!="")
	{
	//aSQL="select a.bussno,'新契约',(select b.SubTypeName from es_doc_def b where b.subtype=a.subtype),a.docid from es_doc_relation a where subtype='"+subtype+"' and bussno='"+contno+"' and busstype='TB'";					
        var sqlid1="ImageQuerySql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(subtype);
		mySql1.addSubPara(contno);
		aSQL = mySql1.getString();
		
 }else{

 //aSQL="select a.bussno,'新契约',(select b.SubTypeName from es_doc_def b where b.subtype=a.subtype),a.docid from es_doc_relation a where  bussno='"+contno+"' and busstype='TB'";	
        var sqlid2="ImageQuerySql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(contno);
	
		aSQL = mySql2.getString();

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
 	fm.submit(); //提交    
 
} 
 

 