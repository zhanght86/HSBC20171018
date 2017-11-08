//
//程序名称：NoBackPolQuery.js
//程序功能：未录入数据查询
//创建日期：2007-10-16 15:10
//创建人  ：HULY
//更新记录：  更新人    更新日期     更新原因/内容

//var arrDataSet;
var turnPage = new turnPageClass();

//简单查询

function easyPrint()
{
	//easyQueryPrint(2,'CodeGrid','turnPage');
	   var i = 0;
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  var arrReturn = new Array();
		fm.target = "../f1print";
		//fm.action = "";
		document.getElementById("fm").submit();
		showInfo.close();
}

function easyQuery()
{	
	if (fm.StartDate.value == "" || fm.EndDate.value == "" )
	{
			alert("开始时间和结束时间不能为空!");
			return false;
	}
	
	initCodeGrid();
	var tBPOID = fm.BPOID.value;
	// 书写SQL语句
//	var strSQL = "select doccode,managecom,to_char(makedate,'yyyy-mm-dd'),maketime "
//             + " from es_doc_main "
//             + " where inputstate is null  and substr(doccode,3,2)!= '12' "
//             + "and subtype='UA001' and busstype='TB' "
//             + getWherePart('makedate','StartDate','>=')
//             + getWherePart('makedate','EndDate','<=')
//             + getWherePart('ManageCom','ManageCom','like')
//             + " and managecom like '"+comCode+"%' "
//             //+  "order by makedate,maketime";
//     
//     if(tBPOID!=null&&tBPOID !=""){
//        strSQL= strSQL+ " and substr(managecom,1,4) in (select (managecom) from bpoallotrate"
//                      + " where trim(BPOID) = '"+tBPOID+"') order by makedate,maketime";
//     }
	
  	var  StartDate1 = window.document.getElementsByName(trim("StartDate"))[0].value;
  	var  EndDate1 = window.document.getElementsByName(trim("EndDate"))[0].value;
  	var  ManageCom1 = window.document.getElementsByName(trim("ManageCom"))[0].value;
	var sqlid1="BPOWorkAmountSql0";
	var mySql1=new SqlClass();
	mySql1.setResourceName("app.BPOWorkAmountSql"); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(StartDate1);//指定传入的参数
	mySql1.addSubPara(EndDate1);//指定传入的参数
	mySql1.addSubPara(ManageCom1);//指定传入的参数
	mySql1.addSubPara(comCode);//指定传入的参数
	mySql1.addSubPara(tBPOID);//指定传入的参数
	var strSQL=mySql1.getString();

	turnPage.queryModal(strSQL, CodeGrid);    

}


