//***********************************************
//程序名称：EdorQuery.js
//程序功能：保全查询
//创建日期：2005-6-10 15:16
//创建人  ：guomy
//更新记录：  更新人    更新日期     更新原因/内容
//***********************************************

//全局变量
var showInfo;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
/*********************************************************************
 *  查询既往保全信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function queryEdor(){
	
		var sqlid1="EdorQuerySql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("uw.EdorQuerySql"); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(contNo);//指定传入的参数
	    var aSQL=mySql1.getString();	
	
//  var aSQL= "select a.edorno,(select d.appntname from lccont d where d.contno=a.contno ),a.edorappdate,a.edorvalidate,(select b.codename from ldcode b where b.codetype='edorcontuwstate' and trim(a.uwstate)=trim(b.code)) "
//          + " from LPEdorMain a "
//          + " where a.contno ='"+contNo+"'";

  turnPage.queryModal(aSQL, EdorGrid);

}


function getEdorDetail(){
    //alert();

}

function getEdorItemInfo(){

  var tSelNo = EdorGrid.getSelNo()-1;
  var tedorno= EdorGrid.getRowColData(tSelNo,1);

		var sqlid2="EdorQuerySql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("uw.EdorQuerySql"); //指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
		mySql2.addSubPara(tedorno);//指定传入的参数
	    var aSQL=mySql2.getString();	

//  var aSQL = "select a.edorno,a.contno,a.edortype,(select distinct b.edorname from lmedoritem b where b.edorcode= a.edortype and appobj='I' ),a.EdorAppDate,a.EdorValiDate from LPEdorItem a where a.edorno='"+tedorno+"'";
//  initPolGrid();
//  alert(aSQL);
  turnPage2.queryModal(aSQL, EdorItemGrid);

}

/*********************************************************************
 *  查询客户信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function queryPersonInfo(){
	
		var sqlid3="EdorQuerySql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("uw.EdorQuerySql"); //指定使用的properties文件名
		mySql3.setSqlId(sqlid3);//指定使用的Sql的id
		mySql3.addSubPara(customerNo);//指定传入的参数
	    var aSQL=mySql3.getString();	
	
//  var aSQL = "select a.customerno,a.name from ldperson a where a.customerno='"+customerNo+"'";
  var arrResult = easyExecSql(aSQL);
  document.all('CustomerNo').value = arrResult[0][0];
  document.all('CustomerName').value = arrResult[0][1];
}


/*********************************************************************
 *  返回上一页
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */

function returnParent(){
  top.close();

}


//影像资料查询
function showImage(){

     var tsel=EdorItemGrid.getSelNo()-1;

     //alert(ContNo);
     if(tsel<0){
     alert("请先选择一条批改项目!");
     return;
   }
   //alert("该批单无影像资料");
   var ContNo=EdorItemGrid.getRowColData(tsel,2);
    window.open("./ImageQueryMain.jsp?ContNo="+ContNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);

}

//<!-- XinYQ modified on 2005-12-19 : BGN -->
/*============================================================================*/

/**
 * 保全核保查询
 */
function EdorUWQuery()
{
    var nSelNo = EdorItemGrid.getSelNo() - 1;
    if (nSelNo < 0)
    {
        alert("请先选择一条批改项目！ ");
        return;
    }
    else
    {
        var sContNo = EdorItemGrid.getRowColData(nSelNo, 2);
        window.open("EdorUWQueryMain.jsp?ContNo=" + sContNo, "EdorUWQueryMain",sFeatures);
    }
}

/*============================================================================*/
//<!-- XinYQ modified on 2005-12-19 : END -->

//保全查询
function BqEdorQueryClick()
{
    var tsel=EdorItemGrid.getSelNo()-1;

     //alert(ContNo);
     if(tsel<0){
     alert("请先选择一条批改项目!");
     return;
   }
   //alert("该批单无影像资料");
   var tContNo=EdorItemGrid.getRowColData(tsel,2);
    window.open("../sys/PolBqEdorMain.jsp?ContNo=" + tContNo + "&flag=0" + "&IsCancelPolFlag=" + 0 ,"",sFeatures);
}

function EdorUWDetailQuery(){
    var tsel=EdorItemGrid.getSelNo()-1;

     //alert(ContNo);
     if(tsel<0){
     alert("请先选择一条批改项目!");
     return;
   }
   var EdorAcceptNo = EdorItemGrid.getRowColData(tsel,1);
   window.open("../bq/EdorAgoQueryInput.jsp?CustomerNo="+fm.CustomerNo.value+"&EdorAcceptNo="+EdorAcceptNo,"",sFeatures);
   
}
