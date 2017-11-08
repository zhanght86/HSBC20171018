var PassFlag = '0';
var ComLength= 8;
var ScreenWidth=640;
var ScreenHeight=480;
//var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量

//根据不同的保全项目进入不同的保全明细
function detailEdorType()
{
	//alert(start);	
	if (!needDetail())
	{
	    return;
	}
	
	detailQueryClick();
	
	if (PassFlag == '1')
	{
		switch(document.all('EdorType').value) 
		{
			case "WT":
				if (document.all('ContType').value =='1')
				{
					var newWindow = window.open("./PEdorTypeWT.html","PEdorTypeWT",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
  					newWindow.focus();
				}
				else
				{
					var newWindow = window.open("./GEdorTypeWT.html","GEdorTypeWT",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
  					newWindow.focus();
				}
				break;
				
			default:

				if (document.all('ContType').value =='1') //打开默认个体保全项目的明细界面
				{
			 		//alert("EdorType");
			 		window.open("./PEdorType" + trim(document.all('EdorType').value) + ".jsp", "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
			 	}
				else  //打开团险保全明细
				{
				  //打开团险单选保全明细综合页面
				  if (document.all('EdorType').value=="BB" || 
				  	  document.all('EdorType').value=="IC" || 
				  	  document.all('EdorType').value=="GT" || 
				  	  document.all('EdorType').value=="IO" || 
				  	  document.all('EdorType').value=="GC" || 
				  	  document.all('EdorType').value=="YC" || 
				  	  document.all('EdorType').value=="PT" ||
				  	  document.all('EdorType').value=="GA") 
				  {
					  var newWindow = window.open("./GEdorTypeDetail.jsp", "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
				      newWindow.focus();
				  }
				  else if (document.all('EdorType').value=="BC" ||
				  		   document.all('EdorType').value=="RC" ||
				  		   document.all('EdorType').value=="GC")
				  {
				  	  window.open("./GEdorTypeRiskDetail.jsp", "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
				  }
				  //打开团险复选保全明细综合页面
				  else if (document.all('EdorType').value=="ZT" ) 
				  {
				      window.open("./GEdorTypeMultiDetail.jsp", "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
				  }
				  //打开团险复选保全明细综合页面
				  else if (document.all('EdorType').value=="LT" ) 
				  {
				      window.open("./GEdorTypeMultiRisk.jsp", "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
				  }
				  else //打开默认团险保全项目的明细界面
				  {				  
					  window.open("./GEdorType" + trim(document.all('EdorType').value) + ".jsp", "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
				  }
			    }
				break;
		}
	}		
}
function GetEndorseQuery()
{
	window.open("./LJSGetEndorse.html");
}
function LPPolQuery()
{
	window.open("./LPPolQuery.jsp");
}

// 查询按钮
function detailQueryClick()
{
	var tEdorAcceptNo = document.all('EdorAcceptNo').value;
	var tEdorType = document.all('EdorType').value;
	var tContType = document.all('ContType').value;
	
	// 书写SQL语句
	var strSQL = "";
	//alert("conttype:"+tContType);
	if (tContType=='1')
		
	//strSQL = "select count(*) from lpedorItem where edoracceptno='"+tEdorAcceptNo+"' and edortype='"+tEdorType+"'";
	var sqlid1 = "PEdorSql1";
	var mySql1 = new SqlClass();
	mySql1.setResourceName("bqs.PEdorSql"); // 指定使用的properties文件名
	mySql1.setSqlId(sqlid1);// 指定使用的Sql的id
	mySql1.addSubPara(tEdorAcceptNo);// 指定传入的参数
	mySql1.addSubPara(tEdorType);// 指定传入的参数
	strSQL = mySql1.getString();
		
//		strSQL = "select count(*) from lpedorItem a left join lpedorapp b on a.EdorAcceptNo = b.EdorAcceptNo where (edorno='"+tEdorNo+"' and edortype='"+tEdorType+"' and b.OtherNoType = 3) or (edortype='"+tEdorType+"' and b.OtherNoType = 1)";
	else
	//strSQL = "select count(*) from LPGrpEdorItem where edoracceptno='"+tEdorAcceptNo+"' and edortype='"+tEdorType+"'";
	var sqlid2 = "PEdorSql2";
	var mySql2 = new SqlClass();
	mySql2.setResourceName("bqs.PEdorSql"); // 指定使用的properties文件名
	mySql2.setSqlId(sqlid2);// 指定使用的Sql的id
	mySql2.addSubPara(tEdorAcceptNo);// 指定传入的参数
	mySql2.addSubPara(tEdorType);// 指定传入的参数
	strSQL = mySql2.getString();

	  //alert(strSQL);			 
	  //查询SQL，返回结果字符串
	  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
	  
	  //判断是否查询成功
	  if (!turnPage.strQueryResult) 
	  {
	      alert("查询失败！");
	   	  PassFlag='0';
	      return;
	  }
	  else
	  {
		    //查询成功则拆分字符串，返回二维数组
		    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);  	  
		    if (turnPage.arrDataCacheSet[0][0]<=0)
		    {
				 alert("请保存申请批改项目！");
				 PassFlag='0';
				 return;
		    }
		    PassFlag ='1';
		}
}

//控制费用明细按钮
function ctrlGetEndorse()
{
	try
	{
		var tEdorNo = 	document.all('EdorNo').value;
		var tEdorType = document.all('EdorType').value;
		
		/*strSQL = " select count(*) from LJSGetEndorse " +
				 " where EndorsementNo = '" + tEdorNo + "' and FeeOperationType = '" + tEdorType + "'";*/
		
		var sqlid3 = "PEdorSql3";
		var mySql3 = new SqlClass();
		mySql3.setResourceName("bqs.PEdorSql"); // 指定使用的properties文件名
		mySql3.setSqlId(sqlid3);// 指定使用的Sql的id
		mySql3.addSubPara(tEdorNo);// 指定传入的参数
		mySql3.addSubPara(tEdorType);// 指定传入的参数
		strSQL = mySql3.getString();
		//查询SQL，返回结果字符串
	    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
	  
	    //判断是否查询成功
	    if (!turnPage.strQueryResult) 
	    {
	        alert("查询失败！");
	    	return;
	    }
	    else
	    {
		    //查询成功则拆分字符串，返回二维数组
		    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);  	  
			if (turnPage.arrDataCacheSet[0][0]<=0)
			{
				 divGetEndorse.style.display='none';
				 return;
			}
		}
	}
	catch(ex)
	{
	}
}

/*
function getScreenSize()
{
	ScreenWidth = screen.availWidth;
	ScreenHeight = screen.availHeight;
}
*/

//校验该项目是否需要明细
function needDetail()
{
   /* var strSQL = " select NeedDetail from LMEdorItem " +
    			 " where edorcode = '" + document.all('EdorType').value + "' ";
    */
    
    var sqlid4 = "PEdorSql4";
	var mySql4 = new SqlClass();
	mySql4.setResourceName("bqs.PEdorSql"); // 指定使用的properties文件名
	mySql4.setSqlId(sqlid4);// 指定使用的Sql的id
	mySql4.addSubPara(document.all('EdorType').value);// 指定传入的参数
	var strSQL = mySql4.getString();
	
    if (fm.OtherNoType.value == '1')
    {
        if (document.all('ContType').value =='1')
        	
            strSQL = strSQL + " and APPOBJ = 'B'"
           // mySql4.addSubPara(" and APPOBJ = 'B'");// 指定传入的参数
        else
            strSQL = strSQL + " and APPOBJ = 'A'"
        	// mySql4.addSubPara(" and APPOBJ = 'A'");// 指定传入的参数
    }
    else
    {
        if (document.all('ContType').value =='1')
            strSQL=strSQL+" and APPOBJ='I'"
           // mySql4.addSubPara(" and APPOBJ='I'");// 指定传入的参数
        else
           strSQL=strSQL+" and APPOBJ='G'"
           // mySql4.addSubPara(" and APPOBJ='G'");// 指定传入的参数
    }
    
    var arrResult = easyExecSql(strSQL);
	if (arrResult != null) 
	{
		if (arrResult[0][0]==0)
		{
			alert("该项目不需要录入明细信息！")
			return false;
		}
	    else
			return true;
	}
	else
	{
		alert("该项目定义不完整！")
		return false;
	}
}


//<!-- XinYQ added on 2006-07-25 : 获取业务员和投保人的关系 : BGN -->
/*============================================================================*/

function getAgentToAppntRelation(sEdorType)
{
    //检查参数
    if (sEdorType == null || trim(sEdorType) == "")
    {
        alert("无法获取保全项目类型。查询业务员和投保人的关系失败！ ");
        return;
    }
    //查询条件
    var sEdorAcceptNo;
    try
    {
        sEdorAcceptNo = document.getElementsByName("EdorAcceptNo")[0].value;
    }
    catch (ex) {}
    if (sEdorAcceptNo == null || trim(sEdorAcceptNo) == "")
    {
        //alert("无法获取保全受理号。查询业务员和投保人的关系失败！ ");
        return;
    }
    //先查询 LPEdorItem
    var QuerySQL, arrResult;
   /* QuerySQL = "select a.StandByFlag2, "
             +        "(select CodeName "
             +           "from LDCode "
             +          "where CodeType = 'relationtoappnt' "
             +            "and Code = a.StandByFlag2) "
             +   "from LPEdorItem a "
             +  "where 1 = 1 "
             +    "and a.EdorAcceptNo = '" + trim(sEdorAcceptNo) + "' "
             +    "and a.EdorType = '" + trim(sEdorType) + "'";
    */
    var sqlid5 = "PEdorSql5";
	var mySql5 = new SqlClass();
	mySql5.setResourceName("bqs.PEdorSql"); // 指定使用的properties文件名
	mySql5.setSqlId(sqlid5);// 指定使用的Sql的id
	mySql5.addSubPara(trim(sEdorAcceptNo));// 指定传入的参数
	mySql5.addSubPara(trim(sEdorType));// 指定传入的参数
	QuerySQL = mySql5.getString();
    
    
    //alert(QuerySQL);
    try
    {
        arrResult = easyExecSql(QuerySQL, 1, 0);
    }
    catch (ex)
    {
        alert("警告：查询业务员和投保人的关系出现异常！ ");
        return;
    }
    if (arrResult != null && trim(arrResult[0][0]) != "")
    {
        try
        {
            document.getElementsByName("RelationToAppnt")[0].value = arrResult[0][0];
            document.getElementsByName("RelationToAppntName")[0].value = arrResult[0][1];
        }
        catch (ex) {}
    }
    //查询 LACommisionDetail
    else
    {
        var sContNo;
        try
        {
            sContNo = document.getElementsByName("ContNo")[0].value;
        }
        catch (ex) {}
        if (sContNo == null || trim(sContNo) == "")
        {
            //alert("无法获取合同号。查询业务员和投保人的关系失败！ ");
            return;
        }
       /* QuerySQL = "select a.RelationShip, "
                 +        "(select CodeName "
                 +           "from LDCode "
                 +          "where CodeType = 'relationtoappnt' "
                 +            "and Code = a.RelationShip) "
                 +   "from LACommisionDetail a "
                 +  "where 1 = 1 "
                 +    "and a.GrpContNo = '" + trim(sContNo) + "' "
                 +    "and a.AgentCode = "
                 +        "(select trim(AgentCode) "
                 +           "from LCCont "
                 +          "where ContNo = '" + trim(sContNo) + "')";*/
        
        var sqlid6 = "PEdorSql6";
    	var mySql6 = new SqlClass();
    	mySql6.setResourceName("bqs.PEdorSql"); // 指定使用的properties文件名
    	mySql6.setSqlId(sqlid6);// 指定使用的Sql的id
    	mySql6.addSubPara(trim(sContNo));// 指定传入的参数
    	mySql6.addSubPara(trim(sContNo));// 指定传入的参数
    	QuerySQL = mySql6.getString();
        
        
        //alert(QuerySQL);
        try
        {
            arrResult = easyExecSql(QuerySQL, 1, 0);
        }
        catch (ex)
        {
            alert("警告：查询业务员和投保人的关系出现异常！ ");
            return;
        }
        if (arrResult != null && trim(arrResult[0][0]) != "")
        {
            try
            {
                document.getElementsByName("RelationToAppnt")[0].value = arrResult[0][0];
                document.getElementsByName("RelationToAppntName")[0].value = arrResult[0][1];
            }
            catch (ex) {}
        }
    }
}

/*============================================================================*/
//<!-- XinYQ added on 2006-07-25 : 获取业务员和投保人的关系 : END -->
