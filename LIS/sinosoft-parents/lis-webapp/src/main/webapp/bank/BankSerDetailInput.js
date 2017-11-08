//程序名称：UWApp.js
//程序功能：既往投保信息查询
//创建日期：2002-06-19 11:10:36
//创建人  ： WHN
//更新记录：  更新人    更新日期     更新原因/内容

//该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="1";
var tPOLNO = "";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  //showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  else
  { 
    //执行下一步操作
  }
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




function displayEasyResult( arrResult )
{
	var i, j, m, n;

	if( arrResult == null )
		alert( "没有找到相关的数据!" );
	else
	{
		arrGrid = arrResult;
		// 显示查询结果
		n = arrResult.length;
		for( i = 0; i < n; i++ )
		{
			m = arrResult[i].length;
			for( j = 0; j < m; j++ )
			{
				PolGrid.setRowColData( i, j+1, arrResult[i][j] );
			} // end of for
		} // end of for
		//alert("result:"+arrResult);
	} // end of if
}

/*********************************************************************
 *  查询个人已承保险种信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */	
function queryDetailInfo()
{
//	var strSQL = "";
//  strSQL = "select DealType,PayCode,AccName,AccNo,PayMoney,IDNo,IDType,PolNo,(case NoType when '4' then '首期投保单号' when '6' then '首期投保单号' when '7','首期投保单号' then '2' when '续期保单号' then '3' when '续期保单号' when '10' then '保全受理号' when '5' then '理赔号' else '其他' end)"       		                
//              + " from LYSendToBank l where 1=1 "
//		          + " and SerialNo = '"+tSerialNo+"'"	
//              +" order by NoType,PayCode" ; 		 	 
	
  var sqlid1="BankSerDetailInputSql1";
  var mySql1=new SqlClass();
  mySql1.setResourceName("bank.BankSerDetailInputSql");
  mySql1.setSqlId(sqlid1);//指定使用SQL的id
  mySql1.addSubPara(tSerialNo);//指定传入参数
  var strSQL = mySql1.getString();
  
 turnPage.queryModal(strSQL, PolGrid);
	
}

/*********************************************************************
 *  查询客户信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function queryGolbalInfo(tCustomerNo){
  //var aSQL = "select SerialNo,BankCode,TotalNum,TotalMoney,SendBankFileState from LYBankLog a where a.SerialNo='"+tSerialNo+"'";
 
  var sqlid2="BankSerDetailInputSql2";
  var mySql2=new SqlClass();
  mySql2.setResourceName("bank.BankSerDetailInputSql");
  mySql2.setSqlId(sqlid2);//指定使用SQL的id
  mySql2.addSubPara(tSerialNo);//指定传入参数
  var aSQL = mySql2.getString();
  
  var arrResult = easyExecSql(aSQL);
  if(arrResult!=null)
  {
  	document.all('SerialNo').value = arrResult[0][0];
    document.all('BankCode').value = arrResult[0][1];
    document.all('TotalNum').value = arrResult[0][2];
    document.all('TotalMoney').value = arrResult[0][3];
    document.all('SendBankFileState').value = arrResult[0][4];
  }
}
