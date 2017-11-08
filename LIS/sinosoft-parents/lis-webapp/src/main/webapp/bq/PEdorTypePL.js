//程序名称：PEdorTypePL.js
//程序功能：保单挂失与解挂
//更新记录：  更新人    更新日期     更新原因/内容
//             liurx    2005-06-28
//该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var sqlresourcename = "bq.PEdorTypePLInputSql";


function getPolInfo()
{
    //alert(tContNo);
    var tContNo=document.all("ContNo").value;
    // 书写SQL语句
//    var strSQL ="select InsuredNo,InsuredName,RiskCode,(select RiskName from LMRisk where LMRisk.RiskCode = LCPol.RiskCode),Prem,Amnt,CValiDate,contno,grpcontno from LCPol where ContNo='"+tContNo+"' and appflag <> '4'";

	var strSQL = "";
	var sqlid1="PEdorTypePLInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(tContNo);//指定传入的参数
	strSQL=mySql1.getString();

    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
    //判断是否查询成功
    if (!turnPage.strQueryResult)
    {
        return false;
    }

    //查询成功则拆分字符串，返回二维数组

    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    //设置初始化过的MULTILINE对象

    turnPage.pageDisplayGrid = PolGrid;
    //保存SQL语句
    turnPage.strQuerySql = strSQL;
    //设置查询起始位置
    turnPage.pageIndex = 0;
    //在查询结果数组中取出符合页面显示大小设置的数组
    arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
    //调用MULTILINE对象显示查询结果
    displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}

function chkPol()
{
    var tContno=document.all('ContNo').value;
    var tEdorNo=document.all('EdorNo').value;
    var tEdorType=document.all('EdorType').value;
//    var strSQL="select polno from lppol where edorno='"+tEdorNo+"' and edortype='"+tEdorType+"' and contno='"+tContno+"'";
	
	var strSQL = "";
	var sqlid2="PEdorTypePLInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //指定使用的properties文件名
		
	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	
	mySql2.addSubPara(tEdorNo);//指定传入的参数
	mySql2.addSubPara(tEdorType);
	//begin zbx 20110512
	//mySql2.addSubPara(tContNo);
	mySql2.addSubPara(tContno);
	//end zbx 20110512
	strSQL=mySql2.getString();
    var arrResult2=easyExecSql(strSQL);

    var m=0;
    var n=0;

    if(arrResult2!=null)
    {
        var q=arrResult2.length;

        for(m=0;m<PolGrid.mulLineCount;m++)
        {

            for(n=0;n<q;n++)
           {
                if(PolGrid.getRowColData(m,3)==arrResult2[n][0])
                {
                    PolGrid.checkBoxSel(m+1);
                }
            }
        }
    }
}
//挂失或解挂提交
function edorTypePLSave()
{
  var vLostFlag = fm.LostFlag.value;

  if(vLostFlag=="1")
  {
     if(!fm.ReportByLoseForm.value)
     {
       alert("挂失类型不能为空！");
       fm.ReportByLoseForm.focus();
       return;
     }
    var tReportByLoseDate = fm.ReportByLoseDate.value;
    if(tReportByLoseDate!="")
    {
      if (!isDate(tReportByLoseDate)&&!isDateN(tReportByLoseDate))
      {
            alert("丢失时间不是有效的日期格式(YYYY-MM-DD)或者(YYYYMMDD)!");
            return false;
      }
    }

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
  fm.fmtransact.value="INSERT||MAIN";
  fm.submit();
}


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
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  }
  initForm();
}


/*********************************************************************
 *  显示frmSubmit框架，用来调试
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showSubmitFrame(cDebug)
{
    if( cDebug == "1" )
        parent.fraMain.rows = "0,0,50,82,*";
    else
        parent.fraMain.rows = "0,0,0,72,*";
}

/*********************************************************************
 *  查询客户信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function ShowCustomerInfo()
{

    var tContNo=document.all("ContNo").value;

    if(tContNo!=null && tContNo!="")
    {
/*        var strSQL = " Select a.appntno,'投保人',a.appntname,a.appntsex||'-'||sex.codename,a.appntbirthday,a.idtype||'-'||x.codename,a.idno From lcappnt a "
                                        +" Left Join (Select code,codename From ldcode Where codetype='idtype') x On x.code = a.idtype "
                                        +" Left Join (Select code,codename From ldcode Where codetype='sex') sex On sex.code = a.appntsex  Where contno='"+tContNo+"'"
                                        +" Union"
                                        +" Select i.insuredno,'被保人',i.name,i.Sex||'-'||sex.codename,i.Birthday,i.IDType||'-'||xm.codename,i.IDNo From lcinsured i "
                                        +" Left Join (Select code,codename From ldcode Where codetype='idtype') xm On xm.code = i.idtype "
                                        +" Left Join (Select code,codename From ldcode Where codetype='sex') sex On sex.code = i.sex Where contno='"+tContNo+"'";
                        + " birthday from lcinsured b where contno = '"+tContNo+"' ";
*/        
    var strSQL = "";
	var sqlid3="PEdorTypePLInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql3.setSqlId(sqlid3);//指定使用的Sql的id
	mySql3.addSubPara(tContNo);//指定传入的参数
	mySql3.addSubPara(tContNo);
	mySql3.addSubPara(tContNo);
	strSQL=mySql3.getString();
        
        var crr = easyExecSql(strSQL);
        if(crr)
        {
           initCustomerGrid();
           displayMultiline(crr,CustomerGrid);
        }
        else
        {
            return"";
        }
    }
}


/********************************************************
 * 判断是解挂还是挂失
 * 描述：先去保单状态表，即lccontstate中查询保单是否已挂失
 *       若保单已挂失，且挂失状态未终结，则显示"解挂"按钮；否则显示"挂失"按钮。
 * 参数：无
 * 返回值：无
 *********************************************************
*/
function ReportByLoseFormV(tCurrentDate)
{
    var vState = "";
    var vStateType = "";
    var lostFlag = false;//挂失标志，保单已挂失时置为真，未挂失置为假
    
//    var strsql="select standbyflag1,decode(standbyflag1,'1','挂失','0','解除'),edorreasoncode,edorreason,standbyflag2 from lpedoritem where edortype='PL' and contno='"+fm.ContNo.value+"' and edoracceptno='"+fm.EdorAcceptNo.value+"'";
    
    var strsql = "";
	var sqlid4="PEdorTypePLInputSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql4.setSqlId(sqlid4);//指定使用的Sql的id
	mySql4.addSubPara(fm.ContNo.value);//指定传入的参数
	mySql4.addSubPara(fm.EdorAcceptNo.value);
	strsql=mySql4.getString();
    
    var brr = easyExecSql(strsql);
    
    //保存过明细数据
    if (brr) {
    	//挂失
    	if (brr[0][0] != null && brr[0][0] != '' && brr[0][0] == '1') {
    		document.all('LostFlag').value=brr[0][0];
    		document.all('LostFlagName').value=brr[0][1];
    		divLostDiv.style.display='';
    		document.all('ReportByLoseReason').value=brr[0][2];
    		document.all('ReportByLoseReasonName').value=brr[0][3];
    		document.all('ReportByLoseDate').value=brr[0][4];
    	}
    	//解除
    	else {
    		document.all('LostFlag').value=brr[0][0];
    		document.all('LostFlagName').value=brr[0][1];
    		divLostDiv.style.display='none';
    	}
    }

//    var strSql = "select contno from lccontstate where contno='"+fm.ContNo.value
//              +"' and statetype='Lost' and state='1' and startdate <= '"
//              +tCurrentDate+"' and (enddate is null or enddate >='"+tCurrentDate+"')";
//    var brr = easyExecSql(strSql);
//
//    if(brr)
//    {
//       lostFlag = true; //已挂失
//    }
//    if(!lostFlag)
//    {
//        strSql = "select distinct trim(statereason),(select codename from ldcode where trim(code) = trim(statereason) and codetype = 'reportlosttype'),trim(remark) "
//                   + " from lpcontstate where edorno = '"+fm.EdorNo.value
//                   + " ' and contno = '"+fm.ContNo.value+"' and edortype = '"+fm.EdorType.value+"'";
//        brr = easyExecSql(strSql);
//        if(brr)
//        {
//            document.all('ReportByLoseForm').value=brr[0][0];
//            document.all('ReportByLoseFormName').value=brr[0][1];
//            document.all('ReportByLoseDate').value=brr[0][2].substring(5);
//        }
//        strSql = "select edorreasoncode,(select codename from ldcode where codetype = 'lostreason' and trim(code) = trim(edorreasoncode)) from lpedoritem "
//                   + " where edorno = '"+fm.EdorNo.value+"' "
//                   + " and contno = '"+fm.ContNo.value+"' "
//                   + " and edortype = '"+fm.EdorType.value+"' ";
//        brr = easyExecSql(strSql);
//        if(brr)
//        {
//            document.all('ReportByLoseReason').value=brr[0][0];
//            document.all('ReportByLoseReasonName').value=brr[0][1];
//        }
//        //fm.ReportByLose.value="挂  失";
//        document.all('LostFlag').value="1";
//        //document.all('EdorTypeName').value = "保单挂失";
//    }
//    else
//    {
//        divReportLostInfo.style.display='none';
//        //fm.ReportByLose.value="取消挂失";
//        document.all('LostFlag').value="0";
//        //document.all('EdorTypeName').value = "解除挂失";
//    }

}
//记事本查看
function showNotePad()
{
    var MissionID = top.opener.document.all("MissionID").value;
    var SubMissionID = top.opener.document.all("SubMissionID").value;
    var ActivityID = "0000000003";
    var OtherNo = top.opener.document.all("OtherNo").value;

    var OtherNoType = "1";
    if(MissionID == null || MissionID == "")
    {
        alert("MissionID为空！");
        return;
    }
    var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
    var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface= ../uw/WorkFlowNotePadInput.jsp" + varSrc,"工作流记事本查看","left");
}

//返回
function returnParent()
{
 top.opener.getEdorItemGrid();
 top.close();
 top.opener.focus();
}

function querySignDate()
{
    var tContNo=document.all("ContNo").value;
//    var strSql = "select signdate from lccont where contno = '"+tContNo+"'";
    var strsql = "";
	var sqlid5="PEdorTypePLInputSql5";
	var mySql5=new SqlClass();
	mySql5.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql5.setSqlId(sqlid5);//指定使用的Sql的id
	mySql5.addSubPara(tContNo);//指定传入的参数
	strsql=mySql5.getString();
	//begin zbx 20110512
    var brr = easyExecSql(strsql);
	//end zbx 20110512
    if(brr)
    {
        document.all('SignDate').value = brr[0][0];
        if(fm.SignDate.value == null || fm.SignDate.value =="")
        {
            alert("保单数据不完全：签单日期为空！");
            return;
        }
    }
    else
    {
        alert("保单信息表中没有记录！");
        return;
    }

}

function Edorquery()
{
    var ButtonFlag = top.opener.document.all('ButtonFlag').value;
    if(ButtonFlag!=null && ButtonFlag=="1")
    {
       divEdorquery.style.display = "none";
       fm.Flag.value = "1";
    }
    else
    {
        divEdorquery.style.display = "";
        fm.Flag.value = "0";
    }
}

//add by jiaqiangli 2008-08-05
//控制挂失与解挂的不同处理
function afterCodeSelect( cCodeName, Field ) {
	try	{
		if (cCodeName == "lostflag") {
			var fieldvalue = Field.value;
			//表挂失
			if (fieldvalue == "1") {
				divLostDiv.style.display = "";
			}
			//表解挂
		        else {
		        	divLostDiv.style.display = "none";
		        }
		}
	}
	catch( ex ) {
	}
}