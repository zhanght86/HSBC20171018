var showInfo;

var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var turnPage1 = new turnPageClass();  //使用翻页功能，必须建立为全局变量
var sqlresourcename = "bq.PEdorTypeIPInputInputSql";        
function getPolGrid(tContNo)
{
    var EdorAppDate = fm.EdorItemAppDate.value;
    var tContno=document.all('ContNo').value;
		var strSQL = "";
		var sqlid1="PEdorTypeIPInputInputSql1";
	
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(EdorAppDate);
		mySql1.addSubPara(EdorAppDate);
		mySql1.addSubPara(EdorAppDate);
		mySql1.addSubPara(EdorAppDate);
		mySql1.addSubPara(EdorAppDate);
		mySql1.addSubPara(EdorAppDate);
		mySql1.addSubPara(tContNo);//指定传入的参数
		mySql1.addSubPara(fm.EdorItemAppDate.value);
		mySql1.addSubPara(fm.EdorItemAppDate.value);
		mySql1.addSubPara(fm.EdorItemAppDate.value);
		mySql1.addSubPara(fm.EdorItemAppDate.value);
		mySql1.addSubPara(fm.EdorItemAppDate.value);
		mySql1.addSubPara(fm.EdorItemAppDate.value);
		strSQL=mySql1.getString();
	
    brrResult = easyExecSql(strSQL);
    if (brrResult)
    {
        displayMultiline(brrResult,PolGrid);
        
        arrResult = easyExecSql(strSQL);
        var SQL = "";
				var sqlid1="PEdorTypeIPInputInputSql2";
	
				var mySql2=new SqlClass();
				mySql2.setResourceName(sqlresourcename); //指定使用的properties文件名
				mySql2.setSqlId(sqlid1);//指定使用的Sql的id
				mySql2.addSubPara(arrResult[0][7]);
				SQL=mySql2.getString();

				var result = easyExecSql(SQL);
				var datecount = result[0][0];
				var CustomGetPolDate = result[0][1];
				var EdorAcceptNo = document.all('EdorAcceptNo').value;
				
				var SQL2 = "";
				var sqlid1="PEdorTypeIPInputInputSql3";
	
				var mySql3=new SqlClass();
				mySql3.setResourceName(sqlresourcename); //指定使用的properties文件名
				mySql3.setSqlId(sqlid1);//指定使用的Sql的id
				mySql3.addSubPara(EdorAcceptNo);
				SQL2=mySql3.getString();

				var result = easyExecSql(SQL2);
				var EdorValiDate = result[0][0];
				//alert(dateDiff(CValiDate,EdorValiDate,'D'));
				if(dateDiff(CustomGetPolDate,EdorValiDate,'D')-10>=0)
				{
					document.all('CTType').value = "否";
					}
				else
					{
				
				document.all('CTType').value = "是";

			}
    }
}

function getInsuAcc()
{
	if (PolGrid.getSelNo()==0)
	{
	  alert("请选择需要进行退保的保单!");
	  return;
	}		
	initInsuAccGrid();
	var tPolNo=PolGrid.getRowColData(PolGrid.getSelNo()-1,8);
	document.all('PolNo').value = tPolNo;
	//alert(document.all('PolNo').value);
	var tRiskCode=PolGrid.getRowColData(PolGrid.getSelNo()-1,1);
	var strSql="";
	var tStartDate="";
	var strSql = "";
		var sqlid1="PEdorTypeIPInputInputSql4";
	
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(tRiskCode);
		mySql1.addSubPara(fm.EdorItemAppDate.value);//指定传入的参数
		strSql=mySql1.getString();
		
	var arrResult=easyExecSql(strSql);
	
	if(arrResult==null||arrResult=="")
	{
		var strSql = "";
				var sqlid1="PEdorTypeIPInputInputSql5";
	
				var mySql2=new SqlClass();
				mySql2.setResourceName(sqlresourcename); //指定使用的properties文件名
				mySql2.setSqlId(sqlid1);//指定使用的Sql的id
				mySql2.addSubPara(tRiskCode);
				mySql2.addSubPara(fm.EdorItemAppDate.value);
				strSql=mySql2.getString();
          
		arrResult=easyExecSql(strSql);
	}
	
	tStartDate=arrResult[0][0];
	var strSql = "";
				var sqlid1="PEdorTypeIPInputInputSql6";
	
				var mySql3=new SqlClass();
				mySql3.setResourceName(sqlresourcename); //指定使用的properties文件名
				mySql3.setSqlId(sqlid1);//指定使用的Sql的id
				mySql3.addSubPara(tStartDate);
				mySql3.addSubPara(tStartDate);
				mySql3.addSubPara(fm.EdorNo.value);
				mySql3.addSubPara(fm.EdorNo.value);
				mySql3.addSubPara(tPolNo);
				strSql=mySql3.getString();
      
	//alert(strSql);
	turnPage1.pageDivName = "divPage1";
	turnPage1.queryModal(strSql, InsuAccGrid);

	
	//fm.GetMoney.value=Math.round(getMoney*100)/100.00;
	
}




function getPolGridb(tContNo)
{
    var EdorAppDate = fm.EdorItemAppDate.value;
    var tContno=document.all('ContNo').value;
    var tEdorNo = document.all('EdorNo').value;
    var strSQL = "";
		var sqlid1="PEdorTypeIPInputInputSql7";
	
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(tContno);
		mySql1.addSubPara(tEdorNo);//指定传入的参数
		strSQL=mySql1.getString();
          
    brrResult = easyExecSql(strSQL);
    if (brrResult)
    {
        displayMultiline(brrResult,PolGridb);
        //document.all("DivAddMoney").style.display = "none";
    }
  else
  	{
  		//document.all("DivAddMoney").style.display = "";
  		}
}





function getCustomerGrid()
{

    var tContNo=document.all("ContNo").value;
    if(tContNo!=null&&tContNo!="")
    {
		var strSQL = "";
		var sqlid1="PEdorTypeIPInputInputSql8";
	
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(tContNo);
		mySql1.addSubPara(tContNo);
		strSQL=mySql1.getString();
          
        arrResult = easyExecSql(strSQL);
        if (arrResult)
        {
            displayMultiline(arrResult,CustomerGrid);
        }
    }
}

function edorTypeCTSave()
{
	
	    var chkFlag = false;
    for (i = 0; i < InsuAccGrid.mulLineCount; i++ )
    {
        if (InsuAccGrid.getChkNo(i))
        {
          chkFlag = true;
          var tMoney = InsuAccGrid.getRowColData(i,13);
          if(!isNumeric(tMoney)){
          	alert("追加金额的录入格式有误！");
          	return;
          }
          if(tMoney%100!=0){
          	alert("追加的金额必须为100的整数倍!");
          	return;
          }
        }
        
    }
		if (!chkFlag)
		{
	  	alert("请选择需要追加金额的账户!");
	  	return;
		}
    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.fmtransact.value = "EDORITEM|INPUT";
    fm.submit();
}



//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{

    showInfo.close();
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    if (FlagStr == "Succ")
    {
        try
        {
            chkPol();
            //getZTMoney();
            top.opener.getEdorItemGrid();
        } catch (ex) {}
    }
    initForm();
    //document.all("DivAddMoney").style.display = "";
}


function returnParent()
{
    top.close();
    top.opener.focus();
}

function showNotePad()
{
    var MissionID = top.opener.document.all("MissionID").value;
    var SubMissionID = top.opener.document.all("SubMissionID").value;;
    var ActivityID = "0000000003";
    var OtherNo = top.opener.document.all("OtherNo").value;;

    var OtherNoType = "1";
    if(MissionID == null || MissionID == "")
    {
        alert("MissionID为空！");
        return;
    }
    var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
    var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface= ../uw/WorkFlowNotePadInput.jsp" + varSrc,"工作流记事本查看","left");
}


/**
* 计算两个日期的差,返回差的月数(M)或天数(D) (其中天数除2.29这一天)
* <p><b>Example: </b><p>
* <p>dateDiff("2002-10-1", "2002-10-3", "D") returns "2"<p>
* <p>dateDiff("2002-1-1", "2002-10-3", "M") returns "9"<p>
* @param dateStart 减日期
* @param dateEnd 被减日期
* @param MD 标记，“M”为要求返回差的月数；“D”为要求返回差的天数
* @return 返回两个日期差的月数(M)或天数(D)
*/
function dateDiffCT(dateStart,dateEnd,MD)
{
  if(dateStart==""||dateEnd=="")
  {
    return false;
  }
  if (typeof(dateStart) == "string") {
    dateStart = getDate(dateStart);
  }

  if (typeof(dateEnd) == "string") {
    dateEnd = getDate(dateEnd);
  }

  var i;
  if(MD=="D") //按天计算差
  {
    var endD = dateEnd.getDate();
    var endM = dateEnd.getMonth();
    var endY = dateEnd.getFullYear();
    var startD = dateStart.getDate();
    var startM = dateStart.getMonth();
    var startY = dateStart.getFullYear();
    var startT=new Date(startY,startM-1,startD);
    var endT=new Date(endY,endM-1,endD);
    var diffDay=(endT.valueOf()-startT.valueOf())/86400000;
    return diffDay;
  }
  else //按月计算差
  {
    var endD = dateEnd.getDate();
    var endM = dateEnd.getMonth();
    var endY = dateEnd.getFullYear();
    var startD = dateStart.getDate();
    var startM = dateStart.getMonth();
    var startY = dateStart.getFullYear();

    if(endD<startD)
    {
      return (endY-startY)*12 + (endM-startM) -1;  //只算整月数
    }
    else
    {
      return (endY-startY)*12 + (endM-startM);
    }
  }
}

/**
 * 根据操作类型(录入或查询)决定操作按钮是否显示
 */
function Edorquery()
{
    var sButtonFlag;
    try
    {
        sButtonFlag = top.opener.document.getElementsByName("ButtonFlag")[0].value;
    }
    catch (ex) {}
    if (sButtonFlag != null && trim(sButtonFlag) == "1")
    {
        try
        {
            document.all("divEdorQuery").style.display = "none";
        }
        catch (ex) {}
    }
    else
    {
        try
        {
            document.all("divEdorQuery").style.display = "";
        }
        catch (ex) {}
    }
}

/**
* 获取日期对象
* @param strDate 日期字符串
* @param splitOp 分割符
* @return 返回日期对象
*/
function getDate(strDate, splitOp) {
  if (splitOp == null) splitOp = "-";

  var arrDate = strDate.split(splitOp);
  if (arrDate[2] == "31")
  {
    arrDate[2] = "30";
  }
  if (arrDate[1].length == 1) arrDate[1] = "0" + arrDate[1];
  if (arrDate[2].length == 1) arrDate[2] = "0" + arrDate[2];

  return new Date(arrDate[0], arrDate[1], arrDate[2]);

}



//删除追加记录
function deleterecord()
{


    var chkFlag = false;
    for (i = 0; i < PolGridb.mulLineCount; i++ )
    {
        if (PolGridb.getChkNo(i))
        {
          chkFlag = true;
          break;
        }
        
    }
		if (!chkFlag)
		{
	  	alert("请选择需要删除的纪录!");
	  	return;
		}
	

  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='提示';   //网页名称，可为空; 
var iWidth=550;      //弹出窗口的宽度; 
var iHeight=250;     //弹出窗口的高度; 
var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

showInfo.focus();
  fm.fmtransact.value = "EDORITEM|DELETE";
  fm.submit();
}


//删除追加记录
function edorIPSave()
{


    var chkFlag = false;
    var rec= PolGridb.mulLineCount;

		if (rec<1)
		{
	  	alert("没有要保存的追加记录！");
	  	return;
		}
	

  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='提示';   //网页名称，可为空; 
var iWidth=550;      //弹出窗口的宽度; 
var iHeight=250;     //弹出窗口的高度; 
var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

showInfo.focus();
  fm.fmtransact.value = "EDORITEM|SAVE";
  fm.submit();
}

//
/////function initCTType()
/////{
/////	var SQL = "select hesitateend from lmedorwt a,lcpol b where b.contno = '"+document.all('ContNo').value+"' and a.riskcode = b.riskcode ";
////////////////////////////add by luzhe on 2007-09-17///////////////////////
///////          var mySql=new SqlClass();
///////              mySql.setJspName("../../bq/PEdorTypeIPInputInputSql.jsp");
///////              mySql.setSqlId("PEdorTypeIPInputInputSql_13");
///////              mySql.addPara('document.all('ContNo').value',document.all('ContNo').value);
///////              turnPage.queryModal(mySql.getString(), XXXXGrid);
////////////////////////////end                 add///////////////////////
/////	var result = easyExecSql(strSQL);
/////	alert(result);
/////	//document.all('CTType').value = result[1][1];
/////	}
/////	
/////	function chkPol()
/////{
/////    var tContno   = document.all('ContNo').value;
/////    var tEdorNo   = document.all('EdorNo').value;
/////    var tEdorType = document.all('EdorType').value;
/////    var strSQL = " select polno from lppol " +
/////                 " where edorno='" + tEdorNo +
/////                 "' and edortype='" + tEdorType +
/////                 "' and contno='"+tContno+"'";
////////////////////////////add by luzhe on 2007-09-17///////////////////////
///////          var mySql=new SqlClass();
///////              mySql.setJspName("../../bq/PEdorTypeIPInputInputSql.jsp");
///////              mySql.setSqlId("PEdorTypeIPInputInputSql_14");
///////              mySql.addPara('tContno',tContno);
///////              mySql.addPara('"'";',"'";);
///////              turnPage.queryModal(mySql.getString(), XXXXGrid);
////////////////////////////end                 add///////////////////////
/////    var arrResult2=easyExecSql(strSQL);
/////    var m=0;
/////    var n=0;
/////
/////    if(arrResult2!=null)
/////    {
/////        var q = arrResult2.length;
/////        for(m = 0; m < PolGrid.mulLineCount; m++)
/////        {
/////            for(n = 0; n < q; n++)
/////           {
/////                if(PolGrid.getRowColData(m, 8) == arrResult2[n][0])
/////                {
/////                  //  PolGrid.checkBoxSel(m+1);
/////                }
/////            }
/////        }
/////    }
/////}
/////
/////
function getContZTInfo()
{
    var MainPolNo;
    var MainPayIntv;
    var MainPayToDate;
    var MainPayEndDate;
    var MainRiskCode;
    var strSQL = "";
		var sqlid1="PEdorTypeIPInputInputSql9";
	
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(fm.ContNo.value);
		strSQL=mySql1.getString();
    
    var hrr = easyExecSql(strSQL);
    if ( hrr )
    {
        hrr[0][0]==null||hrr[0][0]=='null'?'0':MainPolNo = hrr[0][0];
        hrr[0][1]==null||hrr[0][1]=='null'?'0':MainPayToDate = hrr[0][1];
        hrr[0][2]==null||hrr[0][2]=='null'?'0':MainPayIntv = hrr[0][2];
        hrr[0][3]==null||hrr[0][3]=='null'?'0':MainPayEndDate = hrr[0][3];
        hrr[0][4]==null||hrr[0][4]=='null'?'0':MainRiskCode = hrr[0][4];
    }
    else
    {
        alert("查找主险失败");
        return;
    }
var strSQL = "";
		var sqlid1="PEdorTypeIPInputInputSql10";
	
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename); //指定使用的properties文件名
		mySql2.setSqlId(sqlid1);//指定使用的Sql的id
		mySql2.addSubPara(fm.ContNo.value);
		
		strSQL=mySql2.getString();
    
    var drr = easyExecSql(strSQL);

    if ( drr )
    {
        drr[0][0]==null||drr[0][0]=='null'?'0':fm.CustomGetPolDate.value = drr[0][0];
        //drr[0][1]==null||drr[0][1]=='null'?'0':fm.ReceiveDate.value = drr[0][1];
        drr[0][2]==null||drr[0][2]=='null'?'0':fm.CvaliDate.value = drr[0][2];
var strSQL = "";
		var sqlid1="PEdorTypeIPInputInputSql11";
	
		var mySql3=new SqlClass();
		mySql3.setResourceName(sqlresourcename); //指定使用的properties文件名
		mySql3.setSqlId(sqlid1);//指定使用的Sql的id
		mySql3.addSubPara(fm.EdorItemAppDate.value);
		mySql3.addSubPara(fm.EdorItemAppDate.value);
		mySql3.addSubPara(fm.EdorItemAppDate.value);
		mySql3.addSubPara(MainPolNo);
		
		strSQL=mySql3.getString();
      
        var frr = easyExecSql(strSQL);
        var CalDate;
        if ( frr )
        {
            frr[0][0]==null||frr[0][0]=='null'?'0':disAvailable = frr[0][0];
            //alert(disAvailable);
            //判断主险是否失效
            if (disAvailable == 0)
            {
                //没有失效

                //判断交至日期

                var intv = dateDiffCT(fm.EdorItemAppDate.value, MainPayToDate, "D");
                //alert(intv);
                if (intv > 0)
                {
                    CalDate = fm.EdorItemAppDate.value;
                }
                else
                {
                    if (MainPayIntv == 0 || MainPayIntv == -1 || MainPayToDate == MainPayEndDate)
                    {
                        CalDate = fm.EdorItemAppDate.value;
                    }
                    else
                    {
                        CalDate = MainPayToDate;
                    }
                }

            }
            else
            {
                CalDate = MainPayToDate;
            }
        }

        var intval = dateDiffCT(fm.CvaliDate.value, CalDate, "M");

        var year = (intval- intval%12)/12;
        var month = intval%12;
        //fm.Inteval.value = year + " 年 零 " + month + " 月";
        var p=0;
        if (year ==0 && month <=3 && fm.CTType.value=="犹豫期外")
        {
        	  p=3;        	
        }
        if (month<=24 && month >=4 && fm.CTType.value=="犹豫期外")
        {
        	  p=0.5;        	
        }
        if (intval >=2 || fm.CTType.value=="犹豫期内")
        {
            p=0;	
        }
        //fm.Poundage.value = "账户价值的" + p +"%";
        if (MainPayIntv == 0)
        {
        	var strSQL = "";
					var sqlid1="PEdorTypeIPInputInputSql12";
	
					var mySql4=new SqlClass();
					mySql4.setResourceName(sqlresourcename); //指定使用的properties文件名
					mySql4.setSqlId(sqlid1);//指定使用的Sql的id
					mySql4.addSubPara(fm.ContNo.value);
		
					strSQL=mySql4.getString();
            
            var mrr = easyExecSql(strSQL);
            if ( mrr )
            {
                mrr[0][0]==null||mrr[0][0]=='null'?'0':fm.PayToDate.value = mrr[0][0];
            }
            else
            {
                fm.PayToDate.value = "";
            }
        }
        else
        {
            //alert("期交");
            //判断期满
            //alert(MainPayEndDate);
            //alert(MainPayToDate);
            if (MainPayEndDate == MainPayToDate)
            {
                //判断有无附加险
                var strSQL = "";
								var sqlid1="PEdorTypeIPInputInputSql13";
	
								var mySql5=new SqlClass();
								mySql5.setResourceName(sqlresourcename); //指定使用的properties文件名
					      mySql5.setSqlId(sqlid1);//指定使用的Sql的id
								mySql5.addSubPara(fm.ContNo.value);
		
					 			strSQL=mySql5.getString();
               
                var mrr = easyExecSql(strSQL);
                if ( mrr )
                {
                    mrr[0][0]==null||mrr[0][0]=='null'?'0':fm.PayToDate.value = mrr[0][0];
                }
                else
                {
                    fm.PayToDate.value = MainPayToDate;
                }
            }
            else
            {
                fm.PayToDate.value = MainPayToDate;
            }
        }
    }
    sqlid1="PEdorTypeIPInputInputSql14";
    mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename); //指定使用的properties文件名
		mySql2.setSqlId(sqlid1);//指定使用的Sql的id
		mySql2.addSubPara(fm.EdorNo.value);
		strSQL=mySql2.getString();
    
    var sErr = easyExecSql(strSQL);
    var eStart = sErr[0][0]
    if(eStart == "1"){
    		mgVar = 60;
    }
		
}
