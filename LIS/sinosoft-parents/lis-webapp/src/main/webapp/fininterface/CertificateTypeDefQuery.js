//            该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="1";
var arrDataSet;
var turnPage = new turnPageClass();


//返回按钮对应的操作
function returnParent()
{
  var arrReturn = new Array();
	var tSel = CertTypeDefGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		//top.close();
		alert( "请您先选择一条记录，再点击返回按钮。" );
	else
	{

			try
			{
				arrReturn = getQueryResult();
				top.opener.afterQuery2( arrReturn );
			}
			catch(ex)
			{
				alert( "没有发现父窗口的afterQuery接口。" + ex );
			}
			top.close();

	}
}



function getQueryResult()
{
	var arrSelected = null;
	tRow = CertTypeDefGrid.getSelNo();
	if( tRow == 0 || tRow == null )
	{
	  return arrSelected;
	}
	arrSelected = new Array();
	//tRow = 10 * turnPage.pageIndex + tRow; //10代表multiline的行数
	//设置需要返回的数组
	//arrSelected[0] = turnPage.arrDataCacheSet[tRow-1];
		// 书写SQL语句
	var strSQL = "";
	/**
	strSQL = "select a.VersionNo,a.CertificateID,a.CertificateName,a.DetailIndexID,a.DetailIndexName,a.AcquisitionType,a.Remark";
	strSQL = strSQL + " from FICertificateTypeDef a  where a.VersionNo ='"+CertTypeDefGrid.getRowColData(tRow-1,1)+"' and a.CertificateID ='"+CertTypeDefGrid.getRowColData(tRow-1,2)+"'";
	*/
	var mySql1=new SqlClass();
		mySql1.setResourceName("fininterface.CertificateTypeDefQuerySql"); //指定使用的properties文件名
		mySql1.setSqlId("CertificateTypeDefQuerySql1");//指定使用的Sql的id
		mySql1.addSubPara(CertTypeDefGrid.getRowColData(tRow-1,1));//指定传入的参数
		mySql1.addSubPara(CertTypeDefGrid.getRowColData(tRow-1,2));//指定传入的参数
		strSQL= mySql1.getString();
	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("查询失败！");
    return false;
    }
	//查询成功则拆分字符串，返回二维数组
  arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
	return arrSelected;
}



// 查询按钮对应的操作
function easyQueryClick()
{
	// 初始化表格
	initCertTypeDefGrid();

	// 书写SQL语句
	var strSQL = "";
	/**
	strSQL = "select VersionNo,CertificateID,CertificateName,DetailIndexID,DetailIndexName,AcquisitionType,Remark from FICertificateTypeDef where 1=1 "
     + getWherePart('VersionNo')
     + getWherePart('CertificateID','CertificateID','like')
     + getWherePart('CertificateName','CertificateName','like')     
     + getWherePart('DetailIndexID')
     + getWherePart('DetailIndexName','DetailIndexName','like')
     + getWherePart('AcquisitionType')
     +"order by VersionNo,CertificateID";
	*/
	var mySql2=new SqlClass();
		mySql2.setResourceName("fininterface.CertificateTypeDefQuerySql"); //指定使用的properties文件名
		mySql2.setSqlId("CertificateTypeDefQuerySql2");//指定使用的Sql的id
		mySql2.addSubPara(fm.VersionNo.value);//指定传入的参数
		mySql2.addSubPara(fm.CertificateID.value);//指定传入的参数
		mySql2.addSubPara(fm.CertificateName.value);//指定传入的参数
		mySql2.addSubPara(fm.DetailIndexID.value);//指定传入的参数
		mySql2.addSubPara(fm.DetailIndexName.value);//指定传入的参数
		mySql2.addSubPara(fm.AcquisitionType.value);//指定传入的参数
		strSQL= mySql2.getString();
	turnPage.queryModal(strSQL,CertTypeDefGrid);
}


//显示div，第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
function showDiv(cDiv,cShow)
{
  if (cShow=="true")
  {
    cDiv.style.display="";
  }
  else
  {
    cDiv.style.display="none";
  }
}


//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,50,82,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
}