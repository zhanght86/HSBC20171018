//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass(); 

//批单打印下拉框险种条件已经被写死
function initEdorType(cObj)
{
	var tRiskCode = document.all('RiskCode').value;
	mEdorType = " 1 and RiskCode=#112103# and AppObj=#I#";
	//alert(mEdorType);
	showCodeList('EdorCode',[cObj], null, null, mEdorType, "1");
	//alert('bbb');
}

function actionKeyUp(cObj)
{
	var tRiskCode = document.all('RiskCode').value;
	mEdorType = " 1 and RiskCode=#112103# and AppObj=#I#";
	//alert(mEdorType);
	showCodeListKey('EdorCode',[cObj], null, null, mEdorType, "1");
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
			parent.fraMain.rows = "0,0,0,0,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
	
	parent.fraMain.rows = "0,0,0,0,*";
}


/*********************************************************************
 *  查询保单下所有保全项目，写入MulLine
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function getBqEdorGrid()
{
	initBqEdorGrid();
   // var tEdorAcceptNo = document.all('EdorAcceptNo').value;
   // alert("tOtherNoType="+tOtherNoType);
    if(tContNo!=null)
    {var   strSQL = "";

      /*   var   strSQL = " select edoracceptno , (select edorconfno from lpedorapp where edoracceptno = lpedoritem.edoracceptno), "+
                         " (select edorname from lmedoritem where edorcode = edortype and appobj in('I','B') and rownum = 1) , edorappdate, edorvalidate, " +
         				" (select CodeName from LDCode b where b.codetype = 'edorstate' and trim(b.code) = trim(edorstate)),"  +
         				" (select operator from lpedorapp where edoracceptno=lpedoritem.edoracceptno ) from lpedoritem " +
         				" where contno = '" + tContNo + "' order by edorappdate";*/
       mySql = new SqlClass();
		mySql.setResourceName("sys.PolBqEdorSql");
		mySql.setSqlId("PolBqEdorSql1");
		mySql.addSubPara(tContNo);   
     //turnPage.queryModal(strSQL, BqEdorGrid);
	 //arrResult = easyExecSql(strSQL);
     //alert(arrResult);
     //displayMultiline(arrResult,BqEdorGrid); 
     
  turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1); 
// alert(turnPage.strQueryResult);
     
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
  	BqEdorGrid.clearData();
    alert("该保单没有保全历史！");
    return false;
  }
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  //设置初始化过的MULTILINE对象
  turnPage.pageDisplayGrid = BqEdorGrid;    
       
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage.pageIndex = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  arrGrid = arrDataSet;
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

    }
}


// 查询按钮
function QueryClick()
{
	var tContNo1 = document.all('ContNo1').value;
	//alert('Contno1:'+tContNo1 );
	var strSQL="";
	if (tflag=="0")
		{
			/*strSQL = " select EdorAcceptNo,OtherNo,"
			       + " OthernoType ,EdorAppName,AppType,EdorAppDate,"
			       + " (select CodeName from LDCode b where b.codetype = 'edorstate' and trim(b.code) = trim(edorstate))," 
			       + " operator from lpedorapp "
			       + " where OtherNo='" + tContNo1 + "'"
	               + " order by makedate asc, maketime asc";*/
//			         
//			  strSQL =  " select EdorAcceptNo, EdorNo, " 
//	        		//	+ " (select distinct edorcode||'-'||edorname from lmedoritem m where  trim(m.edorcode) = trim(edortype)), " 
//	                    + " InsuredNo, PolNo,"
//	                    + " (select m.riskname from lcpol b ,lmrisk m  where b.polno =LPEdorItem.polno and b.riskcode=m.riskcode),"
//	                    + " EdorAppDate, EdorValiDate, "
//	                    + " (select CodeName from LDCode a where a.codetype = 'edorappreason' and trim(a.code) = trim(appreason)), " 
//	                    + " (select CodeName from LDCode b where b.codetype = 'edorstate' and trim(b.code) = trim(edorstate))" 
//	                    + " from LPEdorItem " 
//	                    + " where ContNo='" + tContNo1 + "'"
//	                    + " order by makedate asc, maketime asc";
 		mySql = new SqlClass();
		mySql.setResourceName("sys.PolBqEdorSql");
		mySql.setSqlId("PolBqEdorSql2");
		mySql.addSubPara(tContNo1);   
		}
	else
		{
			
			/*strSQL = " select EdorAcceptNo,OtherNo,"
			       + " OthernoType ,EdorAppName,AppType,EdorAppDate,"
			       + " (select CodeName from LDCode b where b.codetype = 'edorstate' and trim(b.code) = trim(edorstate))," 
			       + " Operator from LPEdorApp "
	               + " where 1=1"
	               + getWherePart( 'LPEdorApp.OtherNo','ContNo1')
	               + " order by makedate asc, maketime asc";*/
		mySql = new SqlClass();
		mySql.setResourceName("sys.PolBqEdorSql");
		mySql.setSqlId("PolBqEdorSql3");
		mySql.addSubPara(tContNo1);  
// 	         strSQL =  " select EdorAcceptNo, EdorNo, " 
//	        		//	+ " (select distinct edorcode||'-'||edorname from lmedoritem m where  trim(m.edorcode) = trim(edortype)), " 
//	                    + " InsuredNo, PolNo,"
//	                    + " (select m.riskname from lcpol b ,lmrisk m  where b.polno =LPEdorItem.polno and b.riskcode=m.riskcode),"
//	                    + " EdorAppDate, EdorValiDate, "
//	                    + " (select CodeName from LDCode a where a.codetype = 'edorappreason' and trim(a.code) = trim(appreason)), " 
//	                    + " (select CodeName from LDCode b where b.codetype = 'edorstate' and trim(b.code) = trim(edorstate))" 
//	                    + " from LPEdorItem " 
//	                    + " where 1=1"
//	                    + getWherePart( 'LPEdorItem.ContNo','ContNo1')
//	                    + " order by makedate asc, maketime asc";
			}	
	   //查询SQL，返回结果字符串
        turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);  
  
  		//判断是否查询成功
       if (!turnPage.strQueryResult)
  	   {
  		   BqEdorGrid.clearData();
       	   alert("未查到该保单保全受理信息！");
           return false;
  	   }
  		//查询成功则拆分字符串，返回二维数组
  		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  		//设置初始化过的MULTILINE对象
  		turnPage.pageDisplayGrid = BqEdorGrid;    
       
  		//保存SQL语句
  		turnPage.strQuerySql     = strSQL; 
  
  		//设置查询起始位置
  		turnPage.pageIndex = 0;  
  
 		//在查询结果数组中取出符合页面显示大小设置的数组
  		var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  		arrGrid = arrDataSet;
  		//调用MULTILINE对象显示查询结果
  		displayMultiline(arrDataSet, turnPage.pageDisplayGrid);	
}


function QueryClick1()
{
	
	document.all('Transact').value ="QUERY|EDOR";
  var i = 0;
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//  showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");  
  var name='提示';   //网页名称，可为空; 
  var iWidth=550;      //弹出窗口的宽度; 
  var iHeight=250;     //弹出窗口的高度; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();

  document.getElementById("fm").submit(); //提交 
}

function getQueryResult()
{
	var arrSelected = null;
	tRow = BqEdorGrid.getSelNo();
	if( tRow == 0 || tRow == null || arrGrid == null )
		return arrSelected;
	arrSelected = new Array();
	//设置需要返回的数组
	//arrSelected[0] = arrGrid[tRow-1];
	arrSelected[0] = BqEdorGrid.getRowData(tRow-1);
	//alert(arrSelected[0][0]);
	return arrSelected;
}

	
function returnParent()
{
	//top.opener.initSelfGrid();
	//top.opener.easyQueryClickSelf();
	top.close();
}

	//打印批单
function PrtEdor()
{
	var arrReturn = new Array();
				
		var tSel = BqEdorGrid.getSelNo();
	
			
		if( tSel == 0 || tSel == null )
			alert( "请先选择一条记录，再点击查看按钮。" );
		else
		{
			var state =BqEdorGrid. getRowColData(tSel-1,6) ;
	        
			if (state=="正在申请")
				alert ("所选批单处于正在申请状态，不能打印！");
			else{
			  var EdorAcceptNo=BqEdorGrid. getRowColData(tSel-1,1) ;
			  
			    //var tEdorAcceptNo = SelfGrid.getRowColData(selno, 1);
	            //var tMissionID = SelfGrid.getRowColData(selno, 5);
	            //var tSubMissionID = SelfGrid.getRowColData(selno, 6);
	            //var tLoadFlag = "edorApp";
	            
				var varSrc="&EdorAcceptNo=" + EdorAcceptNo;
				var newWindow = OpenWindowNew("../sys/BqDetailQueryFrame.jsp?Interface= ../sys/BqDetailQuery.jsp" + varSrc,"保全查询明细","left");	
				//window.open("./BqDetailQuery.jsp?EdorAcceptNo="+EdorAcceptNo+"");
				//fm.target="f1print";	
			
			//	fm.submit();

			}
		}
}


/*********************************************************************
 *  MulLine的RadioBox点击事件，显示项目明细按钮
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function getBqEdorDetail()
{
    //alert(fm.ContNo.value);
    var tSelNo = BqEdorGrid.getSelNo();
    document.all('EdorAcceptNo').value = BqEdorGrid.getRowColData(tSelNo-1, 2);
}

//保全受理明细按钮
function gotoDetail()
{
	var tSelNo = BqEdorGrid.getSelNo();
	if( tSelNo == 0 || tSelNo == null )
	{
	    alert( "请先选择一条记录，再点击明细查询按钮。" );
	    return;      
	}
	var tEdorAcceptNo = BqEdorGrid.getRowColData(tSelNo-1, 1);
    //alert(tEdorAcceptNo);
    var tContNo1 = document.all('ContNo1').value;
	var varSrc="&EdorAcceptNo=" + tEdorAcceptNo+"&OtherNoType=3&tContNo="+tContNo1;
	var newWindow = OpenWindowNew("../sys/BqDetailQueryFrame.jsp?Interface= ../sys/BqDetailQuery.jsp" + varSrc,"保全查询明细","left");	
}
