
//程序名称：EdorNoticePrint.js
//程序功能：保全通知书在线打印控制台
//创建日期：2005-08-02 16:20:22
//创建人  ：liurx
//更新记录：  更新人    更新日期      更新原因/内容 


//该文件中包含客户端需要处理的函数和事件

var showInfo;
var turnPage = new turnPageClass();



//提交，保存按钮对应操作
function printCheck()
{
  //界面校验
  if (!verifyInput2())
  {
     return false;
  }
   
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  
  var tSel = NoticeGrid.getSelNo();

  if( tSel == 0 || tSel == null )
  {
  	    showInfo.close();	
		alert( "请先选择一条记录!" );
  }
  else
  {
		if(!verifyCheck())
		{
			return;
		}
		PrtSeq = NoticeGrid.getRowColData(tSel-1,1);
		showInfo.close();	
		if(PrtSeq != null && PrtSeq != "")
		{
			fm.PrtSeq.value = PrtSeq;
			fm.SelEdorAcceptNo.value = NoticeGrid.getRowColData(tSel-1,2);
	    	fm.fmtransact.value = "PRINT";
	    	fm.target = "f1print";		
		    fm.submit();
		}	
  }
}


function verifyCheck()
{	
	var StrSQL = "SELECT * FROM LZCard WHERE 1=1"
	           + " and CertifyCode = '" + fm.CertifyCode.value + "'"
	           + " and StateFlag = '0'"
	           + " and ReceiveCom = 'A" + comcode + "'"
	           + " and StartNo <= '" + fm.CheckNo.value + "'"
	           + " and EndNo >= '" + fm.CheckNo.value + "'";
	var tResult = easyExecSql(StrSQL);
	if(tResult == null)
	{
		showInfo.close();
		alert("输入的收据号无效！");
		return false;
	}
	return true;
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
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
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
}

// 查询通知书任务列表
function easyQueryClick()
{ 
    var strSql = "";
    var invoiceType = document.all("InvoiceType").value;
    document.all("ChequeType").value = invoiceType;    //将隐藏字段赋值为选择发票的类型
    

	
    if(invoiceType == null || invoiceType == "BQ35"){//默认打个人发票
    	if(_DBT==_DBO){
//    		 strSql = "select a.prtseq,a.standbyflag1,a.standbyflag2,"
//	 	         + " (select edorname from lmedoritem where (edorcode) = trim(a.standbyflag3) and appobj in('I','B') and rownum = 1),"
//	 	         + " (select name from ldcom where comcode = a.managecom),"
//	 	         + " (select name from laagent where agentcode =trim(a.agentcode)) "
//	 	         + " from loprtmanager a where 1 = 1 "
//                 + getWherePart('a.ManageCom', 'ManageCom', 'like')
//                 + getWherePart('a.standbyflag1', 'EdorAcceptNo')
//                 + getWherePart('a.standbyflag2', 'ContNo') 
//                 + getWherePart('a.AgentCode', 'AgentCode') 
//                 + " and (a.standbyflag1) in(select (incomeno) from ljapay where incometype = '10')"
//                 + " and a.ManageCom like '" + comcode.substring(1,4) + "%'" //登陆机构权限控制 
//                 + " and a.StateFlag = '0' and a.code = 'BQ35'";
    		 
    		 var  ManageCom1 = window.document.getElementsByName(trim("ManageCom"))[0].value;
    		 var  EdorAcceptNo1 = window.document.getElementsByName(trim("EdorAcceptNo"))[0].value;
    		 var  ContNo1 = window.document.getElementsByName(trim("ContNo"))[0].value; 
    		 var  AgentCode1 = window.document.getElementsByName(trim("AgentCode"))[0].value;
    		 var  comcode1 = comcode.substring(1,4);
    		 var sqlid1="BqCheckPrintSql1";
    		 var mySql1=new SqlClass();
    		 mySql1.setResourceName("bq.BqCheckPrintSql");
    		 mySql1.setSqlId(sqlid1);//指定使用SQL的id
    		 mySql1.addSubPara(ManageCom1);//指定传入参数
    		 mySql1.addSubPara(EdorAcceptNo1);//指定传入参数
    		 mySql1.addSubPara(ContNo1);//指定传入参数
    		 mySql1.addSubPara(AgentCode1);//指定传入参数
    		 mySql1.addSubPara(comcode1);//指定传入参数
    		 strSql = mySql1.getString();
    		 
    	}else if(_DBT==_DBM){
//    		 strSql = "select a.prtseq,a.standbyflag1,a.standbyflag2,"
//	 	         + " (select edorname from lmedoritem where (edorcode) = trim(a.standbyflag3) and appobj in('I','B') limit 1),"
//	 	         + " (select name from ldcom where comcode = a.managecom),"
//	 	         + " (select name from laagent where agentcode =trim(a.agentcode)) "
//	 	         + " from loprtmanager a where 1 = 1 "
//                 + getWherePart('a.ManageCom', 'ManageCom', 'like')
//                 + getWherePart('a.standbyflag1', 'EdorAcceptNo')
//                 + getWherePart('a.standbyflag2', 'ContNo') 
//                 + getWherePart('a.AgentCode', 'AgentCode') 
//                 + " and (a.standbyflag1) in(select (incomeno) from ljapay where incometype = '10')"
//                 + " and a.ManageCom like '" + comcode.substring(1,4) + "%'" //登陆机构权限控制 
//                 + " and a.StateFlag = '0' and a.code = 'BQ35'";
    		 
    		 var  ManageCom2 = window.document.getElementsByName(trim("ManageCom"))[0].value;
    		 var  EdorAcceptNo2 = window.document.getElementsByName(trim("EdorAcceptNo"))[0].value;
    		 var  ContNo2 = window.document.getElementsByName(trim("ContNo"))[0].value; 
    		 var  AgentCode2 = window.document.getElementsByName(trim("AgentCode"))[0].value;
    		 var  comcode2 = comcode.substring(1,4);
    		 var sqlid2="BqCheckPrintSql2";
    		 var mySql2=new SqlClass();
    		 mySql2.setResourceName("bq.BqCheckPrintSql");
    		 mySql2.setSqlId(sqlid2);//指定使用SQL的id
    		 mySql2.addSubPara(ManageCom2);//指定传入参数
    		 mySql2.addSubPara(EdorAcceptNo2);//指定传入参数
    		 mySql2.addSubPara(ContNo2);//指定传入参数
    		 mySql2.addSubPara(AgentCode2);//指定传入参数
    		 mySql2.addSubPara(comcode2);//指定传入参数
    		 strSql = mySql2.getString();
    	}
	   
	}else if(invoiceType != null && invoiceType == "BQ36"){
		if(_DBT==_DBO){
//			   strSql = "select a.prtseq,a.standbyflag1,a.standbyflag2,"
//		 	         + " (select edorname from lmedoritem where (edorcode) = trim(a.standbyflag3) and appobj ='G' and rownum = 1),"
//		 	         + " (select name from ldcom where comcode = a.managecom),"
//		 	         + " (select name from laagent where agentcode =trim(a.agentcode)) "
//		 	         + " from loprtmanager a where 1 = 1 "
//	                 + getWherePart('a.ManageCom', 'ManageCom', 'like')
//	                 + getWherePart('a.standbyflag1', 'EdorAcceptNo')
//	                 + getWherePart('a.standbyflag2', 'ContNo') 
//	                 + getWherePart('a.AgentCode', 'AgentCode') 
//	                 + " and (a.standbyflag1) in(select (incomeno) from ljapay where incometype = '10')"
//	                 + " and a.ManageCom like '" + comcode.substring(1,4) + "%'" //登陆机构权限控制 
//	                 + " and a.StateFlag = '0' and a.code = 'BQ36'";
			   
			     var  ManageCom3 = window.document.getElementsByName(trim("ManageCom"))[0].value;
	    		 var  EdorAcceptNo3 = window.document.getElementsByName(trim("EdorAcceptNo"))[0].value;
	    		 var  ContNo3 = window.document.getElementsByName(trim("ContNo"))[0].value; 
	    		 var  AgentCode3 = window.document.getElementsByName(trim("AgentCode"))[0].value;
	    		 var  comcode3 = comcode.substring(1,4);
	    		 var sqlid3="BqCheckPrintSql3";
	    		 var mySql3=new SqlClass();
	    		 mySql3.setResourceName("bq.BqCheckPrintSql");
	    		 mySql3.setSqlId(sqlid3);//指定使用SQL的id
	    		 mySql3.addSubPara(ManageCom3);//指定传入参数
	    		 mySql3.addSubPara(EdorAcceptNo3);//指定传入参数
	    		 mySql3.addSubPara(ContNo3);//指定传入参数
	    		 mySql3.addSubPara(AgentCode3);//指定传入参数
	    		 mySql3.addSubPara(comcode3);//指定传入参数
	    		 strSql = mySql3.getString();
			   
		}else if(_DBT==_DBM){
//			   strSql = "select a.prtseq,a.standbyflag1,a.standbyflag2,"
//		 	         + " (select edorname from lmedoritem where (edorcode) = trim(a.standbyflag3) and appobj ='G' and limit 1),"
//		 	         + " (select name from ldcom where comcode = a.managecom),"
//		 	         + " (select name from laagent where agentcode =trim(a.agentcode)) "
//		 	         + " from loprtmanager a where 1 = 1 "
//	                 + getWherePart('a.ManageCom', 'ManageCom', 'like')
//	                 + getWherePart('a.standbyflag1', 'EdorAcceptNo')
//	                 + getWherePart('a.standbyflag2', 'ContNo') 
//	                 + getWherePart('a.AgentCode', 'AgentCode') 
//	                 + " and (a.standbyflag1) in(select (incomeno) from ljapay where incometype = '10')"
//	                 + " and a.ManageCom like '" + comcode.substring(1,4) + "%'" //登陆机构权限控制 
//	                 + " and a.StateFlag = '0' and a.code = 'BQ36'";
			   
			     var  ManageCom4 = window.document.getElementsByName(trim("ManageCom"))[0].value;
	    		 var  EdorAcceptNo4 = window.document.getElementsByName(trim("EdorAcceptNo"))[0].value;
	    		 var  ContNo4 = window.document.getElementsByName(trim("ContNo"))[0].value; 
	    		 var  AgentCode4 = window.document.getElementsByName(trim("AgentCode"))[0].value;
	    		 var  comcode4 = comcode.substring(1,4);
	    		 var sqlid4="BqCheckPrintSql4";
	    		 var mySql4=new SqlClass();
	    		 mySql4.setResourceName("bq.BqCheckPrintSql");
	    		 mySql4.setSqlId(sqlid4);//指定使用SQL的id
	    		 mySql4.addSubPara(ManageCom4);//指定传入参数
	    		 mySql4.addSubPara(EdorAcceptNo4);//指定传入参数
	    		 mySql4.addSubPara(ContNo4);//指定传入参数
	    		 mySql4.addSubPara(AgentCode4);//指定传入参数
	    		 mySql4.addSubPara(comcode4);//指定传入参数
	    		 strSql = mySql4.getString();
		}
	 
	                 
//	var QueryCode = "select code from ldcode where codetype = 'bqgrpcheck' and trim(comcode) = '" + comcode.substring(1,4) + "'";
	
	 var  comcode5 = comcode.substring(1,4);
	 var sqlid5="BqCheckPrintSql5";
	 var mySql5=new SqlClass();
	 mySql5.setResourceName("bq.BqCheckPrintSql");
	 mySql5.setSqlId(sqlid5);//指定使用SQL的id
	 mySql5.addSubPara(comcode5);//指定传入参数
	 var QueryCode = mySql5.getString();
	
	var tResult = easyExecSql(QueryCode);
	if(tResult == null)
	{
	    alert("查询该登陆机构所用保全发票代码失败！");
	    return false;
	}
	document.all('CertifyCode').value = tResult[0][0];	                 
	               
	}			 	         

    turnPage.queryModal(strSql,NoticeGrid);
}
//进入业务员信息查询页面
function queryAgent()
{
	if(document.all('ManageCom').value=="")
	{
		 alert("请先选择管理机构！");
		 return;
	}
	var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+"&branchtype=1","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

}
//选择业务员，返回其姓名
function afterQuery2(arrResult)
{
  if(arrResult!=null)
  {
    fm.AgentCode.value = arrResult[0][0];
  }
}

//初始化管理机构，最长截取六位
function initManageCom()
{
	if(comcode.substring(0,6) !=null && comcode.substring(0,6) != "")
	{
    	var i,j,m,n;
	    var returnstr;
	    var tTurnPage = new turnPageClass();
//	    var strSQL = "select comcode,name from ldcom where comcode like '"+comcode.substring(1,6)+"%%'";
	    
         var  comcode6 = comcode.substring(1,6);
		 var sqlid6="BqCheckPrintSql6";
		 var mySql6=new SqlClass();
		 mySql6.setResourceName("bq.BqCheckPrintSql");
		 mySql6.setSqlId(sqlid6);//指定使用SQL的id
		 mySql6.addSubPara(comcode6);//指定传入参数
		 var strSQL = mySql6.getString();
	    
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
