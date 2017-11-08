//***********************************************
//程序名称：EdorUWImageQuery.js
//程序功能：保全核保影像资料查询
//创建日期：2005-11-01
//创 建 人：XinYQ
//更新记录：更新人     更新日期     更新原因/内容
//***********************************************

//该文件中包含客户端需要处理的函数和事件


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

function returnParent()
{
  top.close();
}


/*********************************************************************
 *  查询合同信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */


function  QueryImage()
{
	var subtype = fm.subtype.value;
	var contno = fm.ContNo.value;
	//alert(subtype);单证类型
	//alert(fm.ContNo.value);
//	var aSQL = "select a.bussno, ";
	var aSQL = "";
	if(subtype != "")
	{
//    aSQL = aSQL + "'" + document.getElementsByName("subtypname")[0].value + "', ";
//    aSQL = aSQL + "(select b.SubTypeName from es_doc_def b where b.busstype='TB' and b.subtype=a.subtype),a.docid from es_doc_relation a where subtype='"+subtype+"' and bussno='"+contno+"' and busstype='TB'";
    
     var subtypname1 = document.getElementsByName("subtypname")[0].value;
     var sqlid1="EdorUWImageQuerySql1";
	 var mySql1=new SqlClass();
	 mySql1.setResourceName("uw.EdorUWImageQuerySql");
	 mySql1.setSqlId(sqlid1);//指定使用SQL的id
	 mySql1.addSubPara(subtypname1);//指定传入参数
	 mySql1.addSubPara(subtype);//指定传入参数
	 mySql1.addSubPara(contno);//指定传入参数
	 aSQL = mySql1.getString();
  }
  else
  {
//    aSQL = aSQL + "'保全核保', "
//    aSQL = aSQL + "(select b.SubTypeName from es_doc_def b where b.busstype='TB' and b.subtype=a.subtype),a.docid from es_doc_relation a where  bussno='"+contno+"' and busstype='TB'";
    
     var sqlid2="EdorUWImageQuerySql2";
	 var mySql2=new SqlClass();
	 mySql2.setResourceName("uw.EdorUWImageQuerySql");
	 mySql2.setSqlId(sqlid2);//指定使用SQL的id
	 mySql2.addSubPara(contno);//指定传入参数
	 aSQL = mySql2.getString();
  }
	turnPage.queryModal(aSQL, ImageGrid);
}


function  showImage()
{
 //调用图片
 	var tSelNo = ImageGrid.getSelNo()-1;
 	var tdocid = ImageGrid.getRowColData(tSelNo,4);
 	//alert("tdocid first="+tdocid);
 	fm.DocID.value=tdocid ;
 	//alert("tdocid second="+fm.DocID.value);
 	top.fraPic.centerPic.innerHTML = "";
 	fm.submit(); //提交
}