var showInfo;

var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var mLostTimes;
function getPolGrid(tContNo)
{
    var EdorAppDate = fm.EdorItemAppDate.value;
    var tContno=document.all('ContNo').value;
   /* var strSQL = " select RiskCode," +
                 " (select RiskName from LMRisk where LMRisk.RiskCode = c.RiskCode)," +
                 " InsuredName, Amnt, " +
                 " nvl((select sum(p.prem) from lcprem p where paystartdate <= '" + EdorAppDate + "' and payenddate >= '" + EdorAppDate + "' and p.polno = c.polno and p.payplantype in ('0')), 0), " +
                 " nvl((select sum(p.prem) from lcprem p where paystartdate <= '" + EdorAppDate + "' and payenddate >= '" + EdorAppDate + "' and p.polno = c.polno and p.payplantype in ('01', '03')), 0), " +
                 " nvl((select sum(p.prem) from lcprem p where paystartdate <= '" + EdorAppDate + "' and payenddate >= '" + EdorAppDate + "' and p.polno = c.polno and p.payplantype in ('02', '04')), 0), " +
                 " polno " +
                 " from LCPol c where ContNo='" + tContNo + "' and appflag = '1' ";*/

    
    var sqlid1 = "PEdorTypeWTSql1";
	var mySql1 = new SqlClass();
	mySql1.setResourceName("bqs.PEdorTypeWTSql"); // 指定使用的properties文件名
	mySql1.setSqlId(sqlid1);// 指定使用的Sql的id
	mySql1.addSubPara(EdorAppDate);// 指定传入的参数
	mySql1.addSubPara(EdorAppDate);// 指定传入的参数
	mySql1.addSubPara(EdorAppDate);// 指定传入的参数
	mySql1.addSubPara(EdorAppDate);// 指定传入的参数
	mySql1.addSubPara(EdorAppDate);// 指定传入的参数
	mySql1.addSubPara(EdorAppDate);// 指定传入的参数
	mySql1.addSubPara(tContNo);// 指定传入的参数
	var strSQL = mySql1.getString();
    brrResult = easyExecSql(strSQL);
    if (brrResult)
    {
        displayMultiline(brrResult,PolGrid);
    }
}

function getCustomerGrid()
{

    var tContNo=document.all("ContNo").value;
    if(tContNo!=null&&tContNo!="")
    {
       /* var strSQL = " Select a.appntno, '投保人', a.appntname, "
                    +" (select trim(n.code)||'-'||trim(n.CodeName) from LDCode n where trim(n.codetype) = 'sex' and trim(n.code) = trim(appntsex)),"
                    +" a.appntbirthday, "
                    +" (select trim(m.code)||'-'||trim(m.CodeName) from LDCode m where trim(m.codetype) = 'idtype' and trim(m.code) = trim(idtype)), "
                    +" a.idno "
                    +" From lcappnt a Where contno='" + tContNo+"'"
                    +" Union"
                    +" Select i.insuredno, '被保人', i.name, "
                    +" (select trim(u.code)||'-'||trim(u.CodeName) from LDCode u where trim(u.codetype) = 'sex' and trim(u.code) = trim(sex)),"
                    +" i.Birthday,"
                    +" (select trim(y.code)||'-'||trim(y.CodeName) from LDCode y where trim(y.codetype) = 'idtype' and trim(y.code) = trim(idtype)), "
                    +" i.IDNo "
                    +" From lcinsured i Where contno='" + tContNo+"'";*/
        
        
        var sqlid2 = "PEdorTypeWTSql2";
    	var mySql2 = new SqlClass();
    	mySql2.setResourceName("bqs.PEdorTypeWTSql"); // 指定使用的properties文件名
    	mySql2.setSqlId(sqlid2);// 指定使用的Sql的id
    	mySql2.addSubPara(tContNo);// 指定传入的参数
    	mySql2.addSubPara(tContNo);// 指定传入的参数
    	var strSQL = mySql2.getString();
        arrResult = easyExecSql(strSQL);
        if (arrResult)
        {
            displayMultiline(arrResult,CustomerGrid);
        }
    }
}

function chkPol()
{
    var tContno   = document.all('ContNo').value;
    var tEdorNo   = document.all('EdorNo').value;
    var tEdorType = document.all('EdorType').value;
   /* var strSQL = " select polno from lppol " +
                 " where edorno='" + tEdorNo +
                 "' and edortype='" + tEdorType +
                 "' and contno='"+tContno+"'";*/
    
    
    var sqlid3 = "PEdorTypeWTSql3";
	var mySql3 = new SqlClass();
	mySql3.setResourceName("bqs.PEdorTypeWTSql"); // 指定使用的properties文件名
	mySql3.setSqlId(sqlid3);// 指定使用的Sql的id
	mySql3.addSubPara(tEdorNo);// 指定传入的参数
	mySql3.addSubPara(tEdorType);// 指定传入的参数
	mySql3.addSubPara(tContno);// 指定传入的参数
	var strSQL = mySql3.getString();
    var arrResult2=easyExecSql(strSQL);
    var m=0;
    var n=0;

    if(arrResult2!=null)
    {
        var q = arrResult2.length;
        for(m = 0; m < PolGrid.mulLineCount; m++)
        {
            for(n = 0; n < q; n++)
           {   
                if(PolGrid.getRowColData(m, 8) == arrResult2[n][0])
                {
                    PolGrid.checkBoxSel(m+1);
                }
            }
        }
    }
}


function getContZTInfo()
{
    var MainPolNo;
    var MainPayIntv;
    var MainPayToDate;
    var MainPayEndDate;
    var MainRiskCode;
   /* strSQL = " select polno, paytodate, payintv, payenddate, riskcode from lcPol " +
             " where contno = '" + fm.ContNo.value +
             "' and polno = mainpolno";*/
    
    var sqlid4 = "PEdorTypeWTSql4";
	var mySql4 = new SqlClass();
	mySql4.setResourceName("bqs.PEdorTypeWTSql"); // 指定使用的properties文件名
	mySql4.setSqlId(sqlid4);// 指定使用的Sql的id
	mySql4.addSubPara(fm.ContNo.value);// 指定传入的参数
    strSQL = mySql4.getString();
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
    //alert(4.1)
   /* strSQL = " select customgetpoldate, ReceiveDate, cvalidate from lccont " +
             " where contno = '" + fm.ContNo.value + "' ";*/
    var sqlid5 = "PEdorTypeWTSql5";
	var mySql5 = new SqlClass();
	mySql5.setResourceName("bqs.PEdorTypeWTSql"); // 指定使用的properties文件名
	mySql5.setSqlId(sqlid5);// 指定使用的Sql的id
	mySql5.addSubPara(fm.ContNo.value);// 指定传入的参数
    strSQL = mySql5.getString();
    var drr = easyExecSql(strSQL);  
    if ( drr )
    {
        drr[0][0]==null||drr[0][0]=='null'?'0':fm.CustomGetPolDate.value = drr[0][0];
        //drr[0][1]==null||drr[0][1]=='null'?'0':fm.ReceiveDate.value = drr[0][1];
        drr[0][2]==null||drr[0][2]=='null'?'0':fm.CvaliDate.value = drr[0][2];

        /*strSQL = " select count(*) from lccontstate  " +
                 " where statetype in('Available') and state = '1' " +
                 " and  ( (startdate <= '" + fm.EdorItemAppDate.value + "' and enddate >= '" + fm.EdorItemAppDate.value + "' ) " +
                 " or (startdate <= '" + fm.EdorItemAppDate.value + "' and enddate is null )) " +
                 " and polno = '" + MainPolNo + "' ";*/
        
        var sqlid6 = "PEdorTypeWTSql6";
    	var mySql6 = new SqlClass();
    	mySql6.setResourceName("bqs.PEdorTypeWTSql"); // 指定使用的properties文件名
    	mySql6.setSqlId(sqlid6);// 指定使用的Sql的id
    	mySql6.addSubPara(fm.EdorItemAppDate.value);// 指定传入的参数
    	mySql6.addSubPara(fm.EdorItemAppDate.value);// 指定传入的参数
    	mySql6.addSubPara(fm.EdorItemAppDate.value);// 指定传入的参数
    	mySql6.addSubPara(fm.EdorItemAppDate.value);// 指定传入的参数
        strSQL = mySql6.getString();
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
                    if (MainPayIntv == 0 || MainPayToDate == MainPayEndDate)
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

           // alert("期交1");
            //判断期满
           // alert(MainPayEndDate);
//alert(MainPayToDate);

       /* strSQL = " select riskcode from lmrisksort " +
                 " where riskcode = '" + MainRiskCode +
                 "' and risksorttype = '30' ";*/
        
        var sqlid7 = "PEdorTypeWTSql7";
    	var mySql7 = new SqlClass();
    	mySql7.setResourceName("bqs.PEdorTypeWTSql"); // 指定使用的properties文件名
    	mySql7.setSqlId(sqlid7);// 指定使用的Sql的id
    	mySql7.addSubPara(MainRiskCode);// 指定传入的参数
        strSQL = mySql7.getString();
        var srr = easyExecSql(strSQL);
        if ( srr )  //如果是不定期交费，保单年度显示实际已过年度
        {
            CalDate = fm.EdorItemAppDate.value;
        }

        var intval = dateDiffCT(fm.CvaliDate.value, CalDate, "M");

        var year = (intval- intval%12)/12;
        var month = intval%12;
        fm.Inteval.value = year + " 年 零 " + month + " 月";

        if (MainPayIntv == 0)
        {
            //alert("趸交");
            //判断有无附加险
           /* strSQL = " select paytodate from lcPol " +
                     " where contno = '" + fm.ContNo.value +
                     "' and polno <> mainpolno and rownum = 1 and appflag = '1' ";*/
            var sqlid8 = "PEdorTypeWTSql8";
        	var mySql8 = new SqlClass();
        	mySql8.setResourceName("bqs.PEdorTypeWTSql"); // 指定使用的properties文件名
        	mySql8.setSqlId(sqlid8);// 指定使用的Sql的id
        	mySql8.addSubPara(fm.ContNo.value);// 指定传入的参数
            strSQL = mySql8.getString();
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
               /* strSQL = " select paytodate from lcPol " +
                         " where contno = '" + fm.ContNo.value +
                         "' and polno <> mainpolno and rownum = 1  and appflag = '1' ";*/
                var sqlid9 = "PEdorTypeWTSql9";
            	var mySql9 = new SqlClass();
            	mySql9.setResourceName("bqs.PEdorTypeWTSql"); // 指定使用的properties文件名
            	mySql9.setSqlId(sqlid9);// 指定使用的Sql的id
            	mySql9.addSubPara(fm.ContNo.value);// 指定传入的参数
                strSQL = mySql9.getString();
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
                fm.PayToDate.value = MainPayToDate;
            }
        }
    }
}

function edorTypeCTSave()
{

    var row = PolGrid.mulLineCount;
    //用于判断在选择险种退保时，如果选择主险，则要求将附件险一起选择
    var tFlag=false,tPolFLag=false; //主险，附加险标记
    var tNoFlag=false,tPolNoFLag=false; //主险，附加险标记
    var i = 0;
      for(var m = 0; m < row; m++ )
      {
        if(PolGrid.getChkNo(m))
        {
         if(isMainPolno(PolGrid.getRowColData(m,8)))
         {
        	tFlag=true;
         }else
         	{        		
         	tPolFLag=true;	
         		}
          i = i+1;
        }
        if(!PolGrid.getChkNo(m))
        {
        if(!isMainPolno(PolGrid.getRowColData(m,8)))
         {
        	tNoFlag=true;
         }
         else
         	{        		
         	tPolNoFLag=true;	
         		}
        	}
      }
      if(i == 0)
      {
        alert("请选择需要退保的险种！");
        return false;
      }

      if(tNoFlag&&tFlag)
      {
        alert("已经选择主险，请选择附加险一起退保");
        return false;
      }
     //判断是整单退还是部分险种退
     if(tFlag)
     {
     	fm.WTContFLag.value="1";
     }else
     	{
     	fm.WTContFLag.value="0";
     		}

    if (fm.SurrReason.value == null || fm.SurrReason.value == "")
    {
        alert("请录入退保原因!");
        return;
    }
	    if(fm.SurrReason.value=='06')
      {
    	divRemarkInfo.style.display="block";
    	fm.SurrReasonName.value=fm.Remark.value;
    	}
    	if(fm.TrueLostTimes.value > 0 )
    	{
    		if(fm.LostTimes.value == null || fm.LostTimes.value == "")
    		{
    			alert("该保单有补发记录，请录入补发记录！");
    			fm.LostTimes.focus();
    			return;
    		}
    		
    		if(fm.LostTimes.value != fm.TrueLostTimes.value)
    		{
    			alert("输入的补发次数不正确请核实！");
    			return;
    		}
    		//return;
    	}
    	//alert(fm.TrueLostTimes.value);
    	//return;
    //<!-- XinYQ modified on 2006-03-01 : 补发保单退保时提示补发打印日期 : BGN -->
    //<!-- SunSX modified on 2008-08-15 ; 如有补发信息在页面初始化时提示并校验录入 END -->
    //if (!checkIsReissue()) return; 
    //<!-- XinYQ modified on 2006-03-01 : 补发保单退保时提示补发打印日期 : END -->
    //<!-- PST modified on 2007-12-01 : 添加犹豫期退保工本费扣除标记 : BGN -->
  if(fm.FeeGetFlag.checked==true)  //
  {
  	  fm.FeeGetFlag.value='1';
  	}else{
  		fm.FeeGetFlag.value='0';
  		}
   if(tFlag)
   {
	   tPolFLag=false;
	 }
 if(tPolFLag&&fm.FeeGetFlag.value=='1')
 {
 	    alert("只有选择主险进行犹豫期退保才需要扣除工本费");
    	return;
 	} 		
    //<!-- XinYQ modified on 2006-03-01 : 添加犹豫期退保工本费扣除标记 : END -->
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
  fm.submit();
}


//<!-- XinYQ added on 2006-03-01 : 补发保单退保时提示补发打印日期 : BGN -->
/*============================================================================*/

/**
 * 检查是否是补发打印保单
 */
function checkIsReissue()
{
    var QuerySQL, arrResult;
    //查询是否做过保单补发
   /* QuerySQL = "select 'X' "
             +   "from LPEdorItem "
             +  "where 1 = 1 "
             +  getWherePart("ContNo", "ContNo")
             +    "and EdorType = 'LR' "
             +    "and EdorState = '0'";*/
    
    var sqlid10 = "PEdorTypeWTSql10";
	var mySql10 = new SqlClass();
	mySql10.setResourceName("bqs.PEdorTypeWTSql"); // 指定使用的properties文件名
	mySql10.setSqlId(sqlid10);// 指定使用的Sql的id
	mySql10.addSubPara(window.document.getElementsByName(trim('ContNo'))[0].value);// 指定传入的参数
	QuerySQL = mySql10.getString();
    //alert(QuerySQL);
    try
    {
        arrResult = easyExecSql(QuerySQL, 1, 0);
    }
    catch (ex)
    {
        alert("警告：查询是否做过保单补发出现异常！ ");
        return true;
    }
    if (arrResult != null)
    {
        var sConfirmMsg = "该保单做过补发，";
        //查询补发保单打印日期
       /* QuerySQL = "select distinct max(MakeDate) "
                 +   "from LDContInvoiceMap "
                 +  "where 1 = 1 "
                 +  getWherePart("ContNo", "ContNo")
                 +    "and OperType = '4'";*/
        
        var sqlid11 = "PEdorTypeWTSql11";
    	var mySql11 = new SqlClass();
    	mySql11.setResourceName("bqs.PEdorTypeWTSql"); // 指定使用的properties文件名
    	mySql11.setSqlId(sqlid11);// 指定使用的Sql的id
    	mySql11.addSubPara(window.document.getElementsByName(trim('ContNo'))[0].value);// 指定传入的参数
    	QuerySQL = mySql11.getString();
        //alert(QuerySQL);
        try
        {
            arrResult = easyExecSql(QuerySQL, 1, 0);
        }
        catch (ex)
        {
            alert("警告：查询补发或重新出单的打印日期出现异常！ ");
            return true;
        }
        if (arrResult != null && trim(arrResult[0][0]) != "")
        {
            sConfirmMsg += "最近一次打印日期为 " + trim(arrResult[0][0]) + "，";
        }
        sConfirmMsg += "是否继续退保？ ";
        //确认提示退保
        if (!confirm(sConfirmMsg))
        {
            return false;
        }
    }
    return true;
}

/*============================================================================*/
//<!-- XinYQ added on 2006-03-01 : 补发保单退保时提示补发打印日期 : END -->


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
            //chkPol();
            getZTMoney();
            queryBackFee();
            top.opener.getEdorItemGrid();
            divBackFeeTotal.style.display="block";
        } catch (ex) {}
    }
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

function Edorquery()
{
    var ButtonFlag = top.opener.document.all('ButtonFlag').value;
    if(ButtonFlag!=null && ButtonFlag=="1")
    {
       divEdorquery.style.display = "none";
    }
    else
    {
        divEdorquery.style.display = "";
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

function getZTMoney()
{
    /*strSQL = " select getmoney,EdorReasonCode,EdorReason, standbyflag2,standbyflag1 " +
    		 " from lpedoritem " +
    		 " where edoracceptno = '" + fm.EdorAcceptNo.value +
    		 "' and edorno = '" + fm.EdorNo.value +
    		 "' and edortype = 'WT' ";*/
    
    var sqlid12 = "PEdorTypeWTSql12";
	var mySql12 = new SqlClass();
	mySql12.setResourceName("bqs.PEdorTypeWTSql"); // 指定使用的properties文件名
	mySql12.setSqlId(sqlid12);// 指定使用的Sql的id
	mySql12.addSubPara(fm.EdorAcceptNo.value);// 指定传入的参数
	mySql12.addSubPara(fm.EdorNo.value)
	strSQL = mySql12.getString();
  
    var brr = easyExecSql(strSQL);

    if ( brr )
    {
        //brr[0][0]==null||brr[0][0]=='null'?'0':fm.GetMoney.value     	= brr[0][0];
        brr[0][1]==null||brr[0][1]=='null'?'0':fm.SurrReason.value     	= brr[0][1];
        brr[0][2]==null||brr[0][2]=='null'?'0':fm.Remark.value  = brr[0][2];
        //brr[0][3]==null||brr[0][3]=='null'?'0':fm.RelationToAppnt.value  = brr[0][3];
       // brr[0][4]==null||brr[0][4]=='null'?'0':fm.CTType.value  = brr[0][4];
       brr[0][4]==0||brr[0][4]=='0'?fm.FeeGetFlag.checked = false:fm.FeeGetFlag.checked  = true;
       divBackFeeTotal.style.display="block";
       showOneCodeName('surrordereason','SurrReason','SurrReasonName');
       showInfo();
    }

}

function showInfo()
{
	    //alert(12);
	    if(fm.SurrReason.value=='06')
    {
    	divRemarkInfo.style.display="block";
    	fm.SurrReasonName.value=fm.Remark.value;
    	}else{
    	divRemarkInfo.style.display="none";
    	}
}

function queryLRInfo()
{
		//var tSQL = "select c.losttimes from lccont c where c.contno ='"+fm.ContNo.value+"'";
		    var sqlid13 = "PEdorTypeWTSql13";
			var mySql13 = new SqlClass();
			mySql13.setResourceName("bqs.PEdorTypeWTSql"); // 指定使用的properties文件名
			mySql13.setSqlId(sqlid13);// 指定使用的Sql的id
			mySql13.addSubPara(fm.ContNo.value);// 指定传入的参数
			var tSQL = mySql13.getString();
		var ret = easyExecSql(tSQL);
		if(ret)
		{
			var tLostTimes = ret[0][0];
			if(tLostTimes > 0)
			{
				fm.TrueLostTimes.value = tLostTimes;
				divLRInfo.style.display="";
			}
			else
			{
				fm.TrueLostTimes.value = 0;
			}
		}
}

function checkPolInfo(spanId)
{
	var rowLine = spanId.substr(11,1);//当前被选中的行号。
	if(document.all(spanId).all('InpPolGridChk').value=='1')
	{
		var riskcode = PolGrid.getRowColData(rowLine,1);
		var tPolNo = PolGrid.getRowColData(rowLine,8);
		//alert("险种号："+tPolNo);
		/*var tSQL =   "select distinct 1 "
  						 + " from lcpol c "
 							 + " where exists (select 1 from LLClaimPolicy l where c.polno = l.polno) "
      				 + " and exists (select 1 "
          		 + " from ljspay j "
               + " where j.othernotype in ('2', '3', '8')"
               + " and j.otherno = c.polno) and c.polno = '"+tPolNo+"'"*/
               
        var sqlid14 = "PEdorTypeWTSql14";
   	    var mySql14 = new SqlClass();
   	    mySql14.setResourceName("bqs.PEdorTypeWTSql"); // 指定使用的properties文件名
     	mySql14.setSqlId(sqlid14);// 指定使用的Sql的id
      	mySql14.addSubPara(tPolNo);// 指定传入的参数
   		var tSQL = mySql14.getString();
		var ret = easyExecSql(tSQL);
		if(ret && !confirm("该保单存在理赔或续期记录，继续操作？"))
		{
			return;
		}
		//tSQL = "select 1 from lmedorcal where riskcode = '"+riskcode+"' and caltype = 'NoteMoney'";
	        var sqlid15 = "PEdorTypeWTSql15";
	   	    var mySql15 = new SqlClass();
	   	    mySql15.setResourceName("bqs.PEdorTypeWTSql"); // 指定使用的properties文件名
	     	mySql15.setSqlId(sqlid15);// 指定使用的Sql的id
	      	mySql15.addSubPara(riskcode);// 指定传入的参数
	   	    tSQL = mySql15.getString();
		ret = easyExecSql(tSQL);
		if(ret != null && ret == 1)
		{
			if(fm.FeeGetFlag.checked==false && confirm("该险种存在工本费记录定义,是否选择扣除工本费？"))
			{
				fm.FeeGetFlag.checked=true;
			}
		}
	}
}
/**判断此险种是否是主险，*/
function isMainPolno(tPolNo)
{
	//var tSQL="select 1 from lcpol  where polno='"+tPolNo+"' and mainpolno=polno and contno='"+document.all('ContNo').value+"'";
	var sqlid16 = "PEdorTypeWTSql16";
	var mySql16 = new SqlClass();
	mySql16.setResourceName("bqs.PEdorTypeWTSql"); // 指定使用的properties文件名
  	mySql16.setSqlId(sqlid16);// 指定使用的Sql的id
   	mySql16.addSubPara(tPolNo);// 指定传入的参数
	mySql16.addSubPara(document.all('ContNo').value);// 指定传入的参数
	var tSQL = mySql16.getString();
	var tFlag=easyExecSql(tSQL);
	//alert(tPolNo);
	if(tFlag==null)
	{
		return false;
	}else
		{
			return true;
			}
	
}