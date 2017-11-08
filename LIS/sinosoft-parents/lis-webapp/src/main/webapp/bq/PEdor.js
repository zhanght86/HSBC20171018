var PassFlag = '0';
var ComLength= 8;
var ScreenWidth=640;
var ScreenHeight=480;
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var turnPage11 = new turnPageClass();   
var mySql=new SqlClass();
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
					var newWindow = window.open("./PEdorTypeWT.jsp","PEdorTypeWT",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
  					newWindow.focus();
				}
				else
				{
					var newWindow = window.open("./GEdorTypeWT.jsp","GEdorTypeWT",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
  					newWindow.focus();
				}
				break;
			case "NS":
				if (document.all('ContType').value =='1')
				{
             window.open("./PEdorType" + trim(document.all('EdorType').value) + ".jsp?ContNo="+document.getElementsByName("ContNo")[0].value, "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
				}
				break;
			default:

				if (document.all('ContType').value =='1') //打开默认个体保全项目的明细界面
				{
			 		window.open("./PEdorType" + trim(document.all('EdorType').value) + ".jsp", "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
			 	}
				else  //打开团险保全明细
				{
if (
              //document.all('EdorType').value=="PR" ||
              document.all('EdorType').value=="IC" || 
              document.all('EdorType').value=="GT" || 
              document.all('EdorType').value=="IO" || 
              document.all('EdorType').value=="GC" || 
              document.all('EdorType').value=="YC" || 
              document.all('EdorType').value=="PT" ||
              document.all('EdorType').value=="AA" ||
              document.all('EdorType').value=="GA" ||
              document.all('EdorType').value=="GB" ||
              document.all('EdorType').value=="GM" ||              
              document.all('EdorType').value=="RT" ||
              //document.all('EdorType').value=="AG" ||              
              document.all('EdorType').value=="BD") 
          {
          var newWindow = window.open("./GEdorTypeDetail.jsp", "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
              newWindow.focus();
          }
          else if (document.all('EdorType').value=="BC"                 
                   //document.all('EdorType').value=="RC" ||
                   )
          {
              window.open("./GEdorTypeRiskDetail.jsp", "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
          }          
          else if (document.all('EdorType').value=="ZT") 
          {
              window.open("./GEdorTypeMultiDetail.jsp", "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
          }
          //打开团险复选保全明细综合页面
          //else if ( document.all('EdorType').value=="LT" ) 
          //{
          //    window.open("./GEdorTypeCT.jsp", "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
              //window.open("./GEdorTypeMultiRisk.jsp", "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
          //}
          else if ( document.all('EdorType').value=="BB" ) 
          {
          	
              //alert(document.all('EdorTypeCal').value);
              if(document.all('EdorTypeCal').value=="004"){
                 window.open("./GrpEdorTypeBB.jsp", "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');          
              }else{
                var newWindow = window.open("./GEdorTypeDetail.jsp", "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
                newWindow.focus();
              }
              //window.open("./GrpEdorTypeNR.jsp", "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
              return;  
          }
          else if ( document.all('EdorType').value=="PR" ) 
          {
              //alert(document.all('EdorTypeCal').value);
              if(document.all('EdorTypeCal').value=="004"){
                 window.open("./GrpEdorTypePR.jsp", "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');          
              }else{
                var newWindow = window.open("./GEdorTypeDetail.jsp", "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
                newWindow.focus();
              }
              //window.open("./GrpEdorTypeNR.jsp", "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
              return;  
          } 
          else if ( document.all('EdorType').value=="RC" ) 
          {
              //alert(document.all('EdorTypeCal').value);
              if(document.all('EdorTypeCal').value=="004"){
                 window.open("./GrpEdorTypePR.jsp", "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');          
              }else{
                var newWindow = window.open("./GEdorTypeDetail.jsp", "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
                newWindow.focus();
              }
              //window.open("./GrpEdorTypeNR.jsp", "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
              return;  
          }                   
          //生存给付算法
          else if ( document.all('EdorType').value=="AG" ) 
          {
          	  var tGrpContNo           = fm.OtherNo.value;
              var tEdorItemAppDate     = fm.EdorItemAppDate.value;//柜面受理日期
              var tEdorValiDate        = fm.EdorValiDate.value;//生效日期
              window.open("./GrpEdorTypeAGMain.jsp?GrpContNo="+tGrpContNo+"&EdorItemAppDate="+tEdorItemAppDate+"&EdorValiDate="+tEdorValiDate, "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');          
          }
          else if ( document.all('EdorType').value=="NR" ) 
          {
              window.open("./GrpEdorTypeNR.jsp", "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
          }
          else if ( document.all('EdorType').value=="BS" ) //add by wanzh 2006-04-18 进入‘保险期间中断’操作页面
          {
              
              var tGrpContNo    = fm.OtherNo.value;
              var tEdorType     = fm.EdorType.value;
              var tEdorAcceptNo = fm.EdorAcceptNo.value;
              window.open("./GrpEdorTypeBSMain.jsp?GrpContNo="+tGrpContNo+"&EdorType="+tEdorType+"&EdorAcceptNo="+tEdorAcceptNo, "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
          }
          else if ( document.all('EdorType').value=="BR" ) //add by wanzh 2006-04-18 进入‘保险期间恢复’操作页面
          {
              var tGrpContNo    = fm.OtherNo.value;
              var tEdorType     = fm.EdorType.value;
              var tEdorAcceptNo = fm.EdorAcceptNo.value;
              window.open("./GrpEdorTypeBRMain.jsp?GrpContNo="+tGrpContNo+"&EdorType="+tEdorType+"&EdorAcceptNo="+tEdorAcceptNo, "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
          }
          else if ( document.all('EdorType').value=="VR" ) //add by wanzh 2006-04-18 进入‘保险期间恢复’操作页面
          {
              window.open("./PEdorTypeVR.jsp", "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=20,left=20,toolbar=20,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
          }
          else if(document.all('EdorType').value=='VC')
          {
            window.open("./GEdorTypeVC.jsp","PEdorType" + trim(document.all('EdorType').value),'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
          }
          else if(document.all('EdorType').value=='CR')
          {
            window.open("./GEdorTypeCR.jsp","PEdorType" + trim(document.all('EdorType').value),'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
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
	window.open("./LJSGetEndorse.html","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}
function LPPolQuery()
{
	window.open("./LPPolQuery.jsp","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}

// 查询按钮
function detailQueryClick()
{
	var tEdorAcceptNo = document.all('EdorAcceptNo').value;
	var tEdorType = document.all('EdorType').value;
	var tContType = document.all('ContType').value;

    mySql=new SqlClass();
    mySql.setResourceName("bq.PEdor");
    
	if (tContType=='1')
		//strSQL = "select count(*) from lpedorItem where edoracceptno='"+tEdorAcceptNo+"' and edortype='"+tEdorType+"'";
	  mySql.setSqlId("PEdorSql2");
	else
		//strSQL = "select count(*) from LPGrpEdorItem where edoracceptno='"+tEdorAcceptNo+"' and edortype='"+tEdorType+"'";
   mySql.setSqlId("PEdorSql3");
   mySql.addSubPara(tEdorAcceptNo); 
   mySql.addSubPara(tEdorType);
   
	  turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);

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

		var sqlid818145353="DSHomeContSql818145353";
var mySql818145353=new SqlClass();
mySql818145353.setResourceName("bq.GetLJSGetEndorseInputSql");//指定使用的properties文件名
mySql818145353.setSqlId(sqlid818145353);//指定使用的Sql的id
mySql818145353.addSubPara(tEdorNo);//指定传入的参数
mySql818145353.addSubPara(tEdorType);//指定传入的参数
strSQL=mySql818145353.getString();
		
//		strSQL = " select count(*) from LJSGetEndorse " +
//				 " where EndorsementNo = '" + tEdorNo + "' and FeeOperationType = '" + tEdorType + "'";

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
    mySql=new SqlClass();
    mySql.setResourceName("bq.PEdor");
    mySql.setSqlId("PEdorSql1");
    mySql.addSubPara(document.all('EdorType').value); 
        	
   // var strSQL = " select NeedDetail from LMEdorItem " +
   // 			 " where edorcode = '" + document.all('EdorType').value + "' ";
    if (fm.OtherNoType.value == '1')
    {
        if (document.all('ContType').value =='1')
           // strSQL = strSQL + " and APPOBJ = 'B'"
            mySql.addSubPara('B');
        else
            //strSQL = strSQL + " and APPOBJ = 'A'"
            mySql.addSubPara('A');
    }
    else
    {
        if (document.all('ContType').value =='1')
           // strSQL=strSQL+" and APPOBJ='I'"
            mySql.addSubPara('I');
        else
            //strSQL=strSQL+" and APPOBJ='G'"
            mySql.addSubPara('G');
    }
    var arrResult = easyExecSql(mySql.getString());
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


//<!-- XinYQ added on 2005-12-12 : 获取业务员和投保人的关系 : BGN -->
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
    
    var sqlid818145513="DSHomeContSql818145513";
var mySql818145513=new SqlClass();
mySql818145513.setResourceName("bq.GetLJSGetEndorseInputSql");//指定使用的properties文件名
mySql818145513.setSqlId(sqlid818145513);//指定使用的Sql的id
mySql818145513.addSubPara(trim(sEdorAcceptNo));//指定传入的参数
mySql818145513.addSubPara(trim(sEdorType));//指定传入的参数
QuerySQL=mySql818145513.getString();
    
//    QuerySQL = "select a.StandByFlag2, "
//             +        "(select CodeName "
//             +           "from LDCode "
//             +          "where CodeType = 'relationtoappnt' "
//             +            "and Code = a.StandByFlag2) "
//             +   "from LPEdorItem a "
//             +  "where 1 = 1 "
//             +    "and a.EdorAcceptNo = '" + trim(sEdorAcceptNo) + "' "
//             +    "and a.EdorType = '" + trim(sEdorType) + "'";
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
        
        var sqlid818145650="DSHomeContSql818145650";
var mySql818145650=new SqlClass();
mySql818145650.setResourceName("bq.GetLJSGetEndorseInputSql");//指定使用的properties文件名
mySql818145650.setSqlId(sqlid818145650);//指定使用的Sql的id
mySql818145650.addSubPara(trim(sContNo));//指定传入的参数
mySql818145650.addSubPara(trim(sContNo));//指定传入的参数
QuerySQL=mySql818145650.getString();
        
//        QuerySQL = "select a.RelationShip, "
//                 +        "(select CodeName "
//                 +           "from LDCode "
//                 +          "where CodeType = 'relationtoappnt' "
//                 +            "and Code = a.RelationShip) "
//                 +   "from LACommisionDetail a "
//                 +  "where 1 = 1 "
//                 +    "and a.GrpContNo = '" + trim(sContNo) + "' "
//                 +    "and a.AgentCode = "
//                 +        "(select trim(AgentCode) "
//                 +           "from LCCont "
//                 +          "where ContNo = '" + trim(sContNo) + "')";
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

/**
*费用查询 add by sunsx 2008-10-07
*/
function MoneyDetail()
{
	var tEdorType = document.all('EdorType').value;
	//alert(tEdorType);
	if(tEdorType == null || tEdorType == "")
	{
		alert("请选择保全明细或添加保全项目!");
		return;
	}
  window.open("./GetLJSGetEndorse.jsp","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");  
}

function queryMoneyDetail()
{
  //alert("ok");
  var tEdorNo = document.all('EdorNo').value;
  var tEdorType = document.all('EdorType').value;
  var strSQL = "";
  if(tEdorNo != null || tEdorNo != '')
    {
      //strSQL = "Select a.GetNoticeNo, a.FeeFinaType, a.ContNo,b.insuredname, a.GetDate, a.RiskCode, a.GetMoney From LJSGetEndorse a,lccont b where a.otherno='"+tEdorNo+"' and a.contno = b.contno and a.FeeOperationType='"+tEdorType+"' order by a.ContNo";
    
    var sqlid818145908="DSHomeContSql818145908";
var mySql818145908=new SqlClass();
mySql818145908.setResourceName("bq.GetLJSGetEndorseInputSql");//指定使用的properties文件名
mySql818145908.setSqlId(sqlid818145908);//指定使用的Sql的id
mySql818145908.addSubPara(tEdorNo);//指定传入的参数
mySql818145908.addSubPara(tEdorType);//指定传入的参数
strSQL=mySql818145908.getString();
    
//    strSQL = "Select GetNoticeNo, FeeFinaType, ContNo,(select insuredname from lccont where contno=LJSGetEndorse.contno), GetDate, RiskCode, GetMoney From LJSGetEndorse where otherno='"+tEdorNo+"' and FeeOperationType='"+tEdorType+"' order by ContNo";
    }

  turnPage11.queryModal(strSQL, MoneyDetailGrid);
  
}
/*============================================================================*/
//<!-- XinYQ added on 2005-12-12 : 获取业务员和投保人的关系 : END -->
