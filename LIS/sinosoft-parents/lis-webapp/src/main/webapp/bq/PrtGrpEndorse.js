//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  else
  { 
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
}        


function queryEndorse()
{
	var EdorState;
    if(trim(fm.ManageCom.value).length == 2 && trim(fm.EdorAcceptNo.value) == "" && trim(fm.GrpContNo.value) == "" && trim(fm.EdorNo.value) == "")
    {
	    alert("查询机构为总公司，请选择其他查询机构，\n或输入保全受理号，保单号，批单号！");
	    return;
	}
	
	if(fm.GrpContNo.value == "" && fm.EdorNo.value == "" && fm.EdorAcceptNo.value == "")
	{
	    alert("在线打印前，请输入保单号，保全受理号或批单号！\n或者到保全批量打印页面进行打印！");
	    return;
	}
		
	// 初始化表格
	initPEdorMainGrid();
	//var tReturn = parseManageComLimit();

	// 书写SQL语句
	var strSQL = "";
	strSQL = " select distinct EdorConfNo,     "           
								+" otherno,       "            
								+" a.edorappname, "            
								+" a.EdorAppDate, "            
								+" a.modifytime,  "            
								+" a.edoracceptno,"            
								+" b.edorno       "            
                +" from LPEdorApp a,lpgrpedoritem b  "          
                +" where a.edoracceptno=b.edoracceptno "   
                +" and exists (select 'Y' "                 
								+"	from lpedorprint c "      
								+" where c.prttimes = 0 "     
								+"	 and c.edorno = b.edorno) "
								 +" and a.ManageCom LIKE '" + comcode.substring(0,6) + "%%'" //登陆机构权限控制
	              +" and a.EdorState in ('0', '6') "   
					      + getWherePart( 'a.otherno','GrpContNo' )
				        + getWherePart( 'a.EdorConfNo','EdorNo' )
				        + getWherePart('a.EdorAcceptNo','EdorAcceptNo')
				        + getWherePart('a.ManageCom', 'ManageCom', 'like')
	              +" order by a.EdorAppDate,a.modifytime";
	 //alert(strSQL);
	 
				 
				 
//查询SQL，返回结果字符串
	  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
	  
	  //判断是否查询成功
	  if (!turnPage.strQueryResult) 
	  {
	    alert("无满足查询条件的批单或该批单已打印!");
	    initPEdorMainGrid();
	    return;
	  }
	  else
	  {
	  	//查询成功则拆分字符串，返回二维数组
	  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	  
	  //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
	  turnPage.pageDisplayGrid = PEdorMainGrid;    
	          
	  //保存SQL语句
	  turnPage.strQuerySql     = strSQL; 
	  
	  //设置查询起始位置
	  turnPage.pageIndex       = 0;  
	  
	  //在查询结果数组中取出符合页面显示大小设置的数组
	  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
	  arrGrid = arrDataSet;
	  //调用MULTILINE对象显示查询结果
	  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
		}
}
function print()
{
	var selno = PEdorMainGrid.getSelNo()-1;
	if (selno <0)
	{
		alert("请选择要处理的任务！");
	    return;
	}	
	var EdorNo = PEdorMainGrid.getRowColData(selno, 7);
	fm.action = "../f1print/EndorsementF1PJ1.jsp?EdorNo=" + EdorNo;
	fm.target="f1print";
	document.getElementById("fm").submit();
}


//初始化管理机构，最长截取六位
function initManageCom()
{
	if(comcode.substring(0,6) !=null && comcode.substring(0,6) != "")
	{
    	var i,j,m,n;
	    var returnstr;
	    var tTurnPage = new turnPageClass();
	    var strSQL = "select comcode,name from ldcom where comcode like '"+comcode.substring(0,6)+"%%'";
	    tTurnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1); 
        if (tTurnPage.strQueryResult == "")
        {
          return "";
        }
        tTurnPage.arrDataCacheSet = decodeEasyQueryResult(tTurnPage.strQueryResult);  
        var returnstr = "";
        var n = tTurnPage.arrDataCacheSet.length;
        if (n > 0)
        {
        	for( i = 0;i < n; i++)
        	{
  	        	m = tTurnPage.arrDataCacheSet[i].length;
        		if (m > 0)
  		        {
  		        	for( j = 0; j< m; j++)
  			        {
  		 		        if (i == 0 && j == 0)
  			        	{
  				        	returnstr = "0|^"+tTurnPage.arrDataCacheSet[i][j];
  			        	}
  			        	if (i == 0 && j > 0)
  			        	{
  					        returnstr = returnstr + "|" + tTurnPage.arrDataCacheSet[i][j];
  				        }
  			         	if (i > 0 && j == 0)
  				        {
  					        returnstr = returnstr+"^"+tTurnPage.arrDataCacheSet[i][j];
  				        }
  			        	if (i > 0 && j > 0)
  				        {
  					        returnstr = returnstr+"|"+tTurnPage.arrDataCacheSet[i][j];
  				        }
     		        }
  		        }
  	            else
  	            {
  		          	alert("查询失败!!");
  			        return "";
  		        }
             }
         }
         else
         {
	         alert("查询失败!");
	         return "";
         }
         fm.ManageCom.CodeData = returnstr;
         return returnstr;
	}	
}