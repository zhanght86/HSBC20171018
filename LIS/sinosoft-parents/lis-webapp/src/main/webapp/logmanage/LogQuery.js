//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass(); 
var arrDataSet;

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
			parent.fraMain.rows = "0,0,0,0,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
	
	parent.fraMain.rows = "0,0,0,0,*";
}

//销户日志的查询函数
function getQueryDetail_B()
{
}

// 查询按钮
function easyQueryClick()
{    
	// 初始化表格
	divLog2.style.display="none";
	initLogGrid();	
	// 书写SQL语句
	var strSQL = "";
	
//	strSQL = "select OtherNo,OtherNoType,PrtNo,MakeDate,ModifyDate from LCDelPolLog where 1=1 "
//				 
//				 + getWherePart( 'OtherNo' )
//				 + getWherePart( 'OtherNoType' )
//				 + getWherePart( 'IsPolFlag' )
//				 + getWherePart( 'PrtNo' )
//				 + getWherePart( 'ManageCom' )
//				 + getWherePart( 'Operator' )
//				 + getWherePart( 'MakeDate' )
//				 + getWherePart( 'MakeTime' )
//				 + getWherePart( 'ModifyDate' )
//				 + getWherePart( 'ModifyTime' )
//				 ;
	var mySql1=new SqlClass();
    mySql1.setResourceName("logmanage.LogQuerySql");
    mySql1.setSqlId("LogQuerySql1");
    mySql1.addSubPara(window.document.getElementsByName(trim('OtherNo'))[0].value);
    mySql1.addSubPara(window.document.getElementsByName(trim('OtherNoType'))[0].value);
    mySql1.addSubPara(window.document.getElementsByName(trim('IsPolFlag'))[0].value);
    mySql1.addSubPara(window.document.getElementsByName(trim('PrtNo'))[0].value);
    mySql1.addSubPara(window.document.getElementsByName(trim('ManageCom'))[0].value);
    mySql1.addSubPara(window.document.getElementsByName(trim('Operator'))[0].value);
    mySql1.addSubPara(window.document.getElementsByName(trim('MakeDate'))[0].value);
    mySql1.addSubPara(window.document.getElementsByName(trim('MakeTime'))[0].value);
    mySql1.addSubPara(window.document.getElementsByName(trim('ModifyDate'))[0].value);
    mySql1.addSubPara(window.document.getElementsByName(trim('ModifyTime'))[0].value);
    strSQL = mySql1.getString();
	//alert(strSQL);
	
	//查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("查询失败！");
    return false;
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象
  turnPage.pageDisplayGrid = LogGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage.pageIndex = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  
  //showCodeName();
}

function getQueryResult()
{
	var arrSelected = null;
	var tRow = LogGrid.getSelNo();
	
	if( tRow == 0 || tRow == null || arrDataSet == null )
		      return arrSelected;
	
	arrSelected = new Array();
	arrSelected[0] = new Array();
	arrSelected[0] = LogGrid.getRowData(tRow-1);
	
	return arrSelected;
}

/*********************************************************************
 *  MulLine的RadioBox点击事件，获得日志的详细信息
 *  返回值：  无
 *********************************************************************
 */
function getLogDetail(parm1,parm2)
{

    var OtherNo=fm.all(parm1).all('LogGrid1').value;
    var OtherNoType = fm.all(parm1).all('LogGrid2').value;
   
//    strSQL ="select * from LCDelPolLog where OtherNo = '"+OtherNo+"' and OtherNoType='"+OtherNoType+"'";
    var mySql2=new SqlClass();
    mySql2.setResourceName("logmanage.LogQuerySql");
    mySql2.setSqlId("LogQuerySql2");
    mySql2.addSubPara(OtherNo);
    mySql2.addSubPara(OtherNoType);
    arrResult=easyExecSql(mySql2.getString(),1,0);
    if(arrResult!=null)
    {
    	divLog2.style.display="";
      DisplayLog();
    }
}

/*********************************************************************
 *  显示日志详细信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function DisplayLog()
{
    try{fm.all('tOtherNo').value=arrResult[0][0];}catch(ex){};            
    try{fm.all('tOtherNoType').value=arrResult[0][1];}catch(ex){};               
    try{fm.all('tIsPolFlag').value=arrResult[0][2];}catch(ex){};            
    try{fm.all('tPrtNo').value=arrResult[0][3];}catch(ex){};                
    try{fm.all('tManageCom').value=arrResult[0][4];}catch(ex){};              
    try{fm.all('tOperator').value=arrResult[0][5];}catch(ex){};            
    try{fm.all('tMakeDate').value=arrResult[0][6];}catch(ex){};           
    try{fm.all('tMakeTime').value=arrResult[0][7];}catch(ex){};             
    try{fm.all('tModifyDate').value=arrResult[0][8];}catch(ex){};      
    try{fm.all('tModifyTime').value=arrResult[0][9];}catch(ex){};           
}	