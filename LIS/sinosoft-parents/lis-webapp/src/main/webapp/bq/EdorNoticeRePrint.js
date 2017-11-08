
//程序名称：EdorNoticePrint.js
//程序功能：保全通知书在线打印控制台
//创建日期：2005-08-02 16:20:22
//创建人  ：liurx
//更新记录：  更新人    更新日期      更新原因/内容


//该文件中包含客户端需要处理的函数和事件

var showInfo;
var turnPage = new turnPageClass();



//提交，保存按钮对应操作
function printNotice()
{
  var i = 0;
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
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
        PrtSeq = NoticeGrid.getRowColData(tSel-1,1);
        fm.PrtSeq.value = PrtSeq;
        fm.fmtransact.value = "PRINT";
        fm.target = "f1print";
        document.getElementById("fm").submit();
        showInfo.close();
  }
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
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
}

// 查询通知书任务列表
function easyQueryClick()
{
    var tNoticeType = fm.NoticeType.value;
    var strSql = "";
    if(tNoticeType == null || tNoticeType == "")
    {
        alert("请选择通知书类型！");
        fm.NoticeType.focus();
        return;
    }
    if(trim(fm.OtherNo.value) == "" && trim(fm.NoticeNo.value) == "")
    {
      if(fm.StartDate.value == null || trim(fm.StartDate.value) == "")
      {
        alert("请选择统计起期！ ");
        fm.StartDate.focus();
        return;
      }
      if(fm.EndDate.value == null || trim(fm.EndDate.value) == "")
      {
        alert("请选择统计止期！ ");
        fm.EndDate.focus();
        return;
      }
    }
    strSql = getSql(tNoticeType);
    var brr = easyExecSql(strSql);
    if(brr)
    {
        initNoticeGrid(tNoticeType);
        turnPage.queryModal(strSql,NoticeGrid);
    }
    else
    {
        var tNoticeTypeName = fm.NoticeTypeName.value;
        if(tNoticeTypeName == null||tNoticeTypeName=="")
        {
            alert("没有要补打的保全通知书！");
            initNoticeGrid(tNoticeType);
        }
        else
        {
           alert("没有要补打的保全"+tNoticeTypeName);
           initNoticeGrid(tNoticeType);
        }
        return ;
    }

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
//校验分红会计年度的输入
function isYearNum()
{
    var tYear = fm.FiscalYear.value;
    if(tYear!=null && tYear!="")
    {
        if (!isNumeric(tYear))
        {
            alert("会计年度值必须是有效数字！");
            fm.FiscalYear.value = "";
            fm.FiscalYear.focus();
            return;
        }
        if(tYear<1900||tYear>9999)
        {
            alert("会计年度输入有误：请输入有效的年度值(1900-9999)！");
            fm.FiscalYear.value = "";
            fm.FiscalYear.focus();
            return;
        }
    }
}
//选择通知书类型后，显示其特定检索条件，初始化其任务列表
function noticeSel()
{
    var NoticeType = fm.NoticeType.value;
    initNoticeInp(NoticeType);
    initNoticeGrid(NoticeType);
}
//跟据通知书类型显示其需要的检索条件
function initNoticeInp(tNoticeType)
{
    switch(tNoticeType)
    {
        case   "":
        case "30":         divContTitle.style.display = '';
                           divEdorAcceptTitle.style.display = 'none';
                           divCont.style.display = '';
                           divBonus.style.display = '';
                           divCustomerTitle.style.display = '';
                           divCustomer.style.display = '';
                           break;
        case "34":         divContTitle.style.display = '';
                           divContTitle.innerText='团体保单号'
                           divEdorAcceptTitle.style.display = 'none';
                           divCont.style.display = 'none';
                           divBonus.style.display = '';
                           divCustomerTitle.style.display = 'none';
                           divCustomer.style.display = 'none';
                           break;
        case "35":         divContTitle.style.display = '';
                           divContTitle.innerText='团体保单号'
                           divEdorAcceptTitle.style.display = 'none';
                           divCont.style.display = 'none';
                           divBonus.style.display = '';
                           divCustomerTitle.style.display = 'none';
                           divCustomer.style.display = 'none';
                           break;
        case "BQ31":
        case "BQ32":
        case "BQ51":
        case "BQ52":
        case "BQ27":
        case "BQ28":       divCont.style.display = 'none';
                           divBonus.style.display = 'none';
                           divCustomerTitle.style.display = '';
                           divCustomer.style.display = '';
                           divContTitle.style.display = 'none';
                           divEdorAcceptTitle.style.display = '';
                           break;
        case "BQ48":
        case "BQ49":       divCont.style.display = 'none';
                           divBonus.style.display = 'none';
                           divCustomerTitle.style.display = 'none';
                           divCustomer.style.display = 'none';
                           divContTitle.style.display = 'none';
                           divEdorAcceptTitle.style.display = '';
                           break;
        case "41":
        case "42":
        case "BQ21":
        case "BQ22":
        case "BQ29":
        case "BQ30":
        case "BQ34":
        case "BQ38":
        case "BQ39":
        case "BQ10":
        case "BQ17":       divCont.style.display = '';
                           divBonus.style.display = 'none';
                           divCustomerTitle.style.display = '';
                           divCustomer.style.display = '';
                           divContTitle.style.display = '';
                           divEdorAcceptTitle.style.display = 'none';
                           break;
        default:
                           divContTitle.style.display = '';
                           divEdorAcceptTitle.style.display = 'none';
                           divCont.style.display = 'none';
                           divBonus.style.display = 'none';
                           divCustomerTitle.style.display = 'none';
                           divCustomer.style.display = 'none';
                           break;
    }
}
//根据通知书类型返回其特定的sql查询语句
function getSql(tNoticeType)
{
   var strSql = "";
   switch(tNoticeType)
   {
     //团体分红业绩报告书人名清单
     case "35":     //     strSql = "select a.prtseq,a.otherno, "
//                                 + " (select codename from ldcode where codetype = 'station' and code = a.managecom ), "
//                                 + " b.grpname,"
//                                 + " (select name from laagent where agentcode = trim(a.agentcode)), "
//                                 + " b.cvalidate,"
//                                 + " (case when b.appflag = '4' then '终止' else '承保' end), "
//                                 + " a.standbyflag1 "
//                                 + " from lcgrppol b,loprtmanager a where 1 = 1 "
//                                 + " and a.StateFlag = '0' "
//                                 + getWherePart('a.prtseq', 'NoticeNo')
//                                 + getWherePart('a.otherno', 'OtherNo')
//                                 + getWherePart('a.Code', 'NoticeType')
//                                 + getWherePart('a.standbyflag1', 'FiscalYear')
//                                   + " and a.standbyflag3='bqnotice' and b.grpcontno = a.otherno "
//                                   + getWherePart('a.ManageCom', 'ManageCom', 'like')
//                                 + " and a.ManageCom LIKE '" + comcode.substring(0,6) + "%'";//登陆机构权限控制;
     
						    var sqlid1="EdorNoticeRePrintSql1";
						  	var mySql1=new SqlClass();
						  	mySql1.setResourceName("bq.EdorNoticeRePrintSql");
						  	mySql1.setSqlId(sqlid1); //指定使用SQL的id
						  	mySql1.addSubPara( window.document.getElementsByName(trim("NoticeNo"))[0].value);//指定传入参数
						  	mySql1.addSubPara( window.document.getElementsByName(trim("OtherNo"))[0].value);
						  	mySql1.addSubPara( window.document.getElementsByName(trim("NoticeType"))[0].value);
						  	mySql1.addSubPara( window.document.getElementsByName(trim("FiscalYear"))[0].value);
						  	mySql1.addSubPara( window.document.getElementsByName(trim("ManageCom"))[0].value);
						  	mySql1.addSubPara(comcode.substring(0,6));
						  	strSql = mySql1.getString();
     
     
                          break;
     case "34":      //    strSql = "select a.prtseq,a.otherno, "
//                                 + " (select codename from ldcode where codetype = 'station' and code = a.managecom ), "
//                                 + " b.grpname,"
//                                 + " (select name from laagent where agentcode = trim(a.agentcode)), "
//                                 + " b.cvalidate,"
//                                 + " (case when b.appflag = '4' then '终止' else '承保' end), "
//                                 + " a.standbyflag1 "
//                                 + " from lcgrppol b,loprtmanager a where 1 = 1 "
//                                 + " and a.StateFlag = '0' "
//                                 + getWherePart('a.prtseq', 'NoticeNo')
//                                 + getWherePart('a.otherno', 'OtherNo')
//                                 + getWherePart('a.Code', 'NoticeType')
//                                 + getWherePart('a.standbyflag1', 'FiscalYear')
//                                 + " and a.standbyflag3='bqnotice' and b.grpcontno = a.otherno "
//                                 + getWherePart('a.ManageCom', 'ManageCom', 'like')
//                                 + " and a.ManageCom LIKE '" + comcode.substring(0,6) + "%'";//登陆机构权限控制;
     
					        var sqlid2="EdorNoticeRePrintSql2";
						  	var mySql2=new SqlClass();
						  	mySql2.setResourceName("bq.EdorNoticeRePrintSql");
						  	mySql2.setSqlId(sqlid2); //指定使用SQL的id
						  	mySql2.addSubPara( window.document.getElementsByName(trim("NoticeNo"))[0].value);//指定传入参数
						  	mySql2.addSubPara( window.document.getElementsByName(trim("OtherNo"))[0].value);
						  	mySql2.addSubPara( window.document.getElementsByName(trim("NoticeType"))[0].value);
						  	mySql2.addSubPara( window.document.getElementsByName(trim("FiscalYear"))[0].value);
						  	mySql2.addSubPara( window.document.getElementsByName(trim("ManageCom"))[0].value);
						  	mySql2.addSubPara(comcode.substring(0,6));
						  	strSql = mySql2.getString();
     
                            break;
      //分红业绩报告书
      //其它号码：polno；发送对象：投保人；特殊检索条件：分红会计年度
      case "30":      //    strSql = "select a.prtseq,b.contno, "
//                                 + " (select codename from ldcode where codetype = 'station' and code = a.managecom ), "
//                                 + " b.appntname,"
//                                 + " (select name from laagent where agentcode = trim(a.agentcode)), "
//                                 + " b.cvalidate,"
//                                 + " (case when b.appflag = '4' then '终止' else '承保' end), "
//                                 + " a.standbyflag1 "
//                                 + " from lcpol b,loprtmanager a where 1 = 1 "
//                                 + getWherePart('a.ManageCom', 'ManageCom', 'like')
//                                 + getWherePart('a.prtseq', 'NoticeNo')
//                                 + getWherePart('b.contno', 'OtherNo')
//                                 + getWherePart('a.Code', 'NoticeType')
//                                 + getWherePart('a.AgentCode', 'AgentCode')
//                                 + getWherePart('b.SaleChnl', 'SaleChnl')
//                                 + getWherePart('a.standbyflag1', 'FiscalYear')
//                                 + getWherePart('b.appntname', 'CustomerName')
//                                 + " and a.otherno = b.polno "
//                                 + " and a.ManageCom LIKE '" + comcode.substring(0,6) + "%%'" //登陆机构权限控制
//                                 + " and a.StateFlag = '1' "
//                                 + " union "
//                                 + "select a.prtseq,b.contno, "
//                                 + " (select codename from ldcode where codetype = 'station' and code = a.managecom ), "
//                                 + " b.appntname,"
//                                 + " (select name from laagent where agentcode = trim(a.agentcode)), "
//                                 + " b.cvalidate,"
//                                 + " (case when b.appflag = '4' then '终止' else '承保' end), "
//                                 + " a.standbyflag1 "
//                                 + " from lcpol b,loprtmanager a where 1 = 1 "
//                                 + getWherePart('a.ManageCom', 'ManageCom', 'like')
//                                 + getWherePart('a.prtseq', 'NoticeNo')
//                                 + getWherePart('b.contno', 'OtherNo')
//                                 + getWherePart('a.Code', 'NoticeType')
//                                 + getWherePart('a.AgentCode', 'AgentCode')
//                                 + getWherePart('b.SaleChnl', 'SaleChnl')
//                                 + getWherePart('a.standbyflag1', 'FiscalYear')
//                                 + getWherePart('b.appntname', 'CustomerName')
//                                 + " and a.otherno = b.polno "
//                                 + " and a.ManageCom LIKE '" + comcode.substring(0,6) + "%%'" //登陆机构权限控制
//                                 + " and b.appflag = '4' ";
      
					        var sqlid3="EdorNoticeRePrintSql3";
						  	var mySql3=new SqlClass();
						  	mySql3.setResourceName("bq.EdorNoticeRePrintSql");
						  	mySql3.setSqlId(sqlid3); //指定使用SQL的id
						  	mySql3.addSubPara(window.document.getElementsByName(trim("ManageCom"))[0].value);//指定传入参数
						  	mySql3.addSubPara(window.document.getElementsByName(trim("NoticeNo"))[0].value);
						  	mySql3.addSubPara(window.document.getElementsByName(trim("OtherNo"))[0].value);
						  	mySql3.addSubPara(window.document.getElementsByName(trim("NoticeType"))[0].value);
						  	mySql3.addSubPara(window.document.getElementsByName(trim("AgentCode"))[0].value);
						  	mySql3.addSubPara(window.document.getElementsByName(trim("SaleChnl"))[0].value);
						  	mySql3.addSubPara(window.document.getElementsByName(trim("FiscalYear"))[0].value);
						  	mySql3.addSubPara(window.document.getElementsByName(trim("CustomerName"))[0].value);
							mySql3.addSubPara(comcode.substring(0,6));
						  	mySql3.addSubPara(window.document.getElementsByName(trim("ManageCom"))[0].value);
						  	mySql3.addSubPara(window.document.getElementsByName(trim("NoticeNo"))[0].value);
						  	mySql3.addSubPara(window.document.getElementsByName(trim("OtherNo"))[0].value);
						  	mySql3.addSubPara(window.document.getElementsByName(trim("NoticeType"))[0].value);
						  	mySql3.addSubPara(window.document.getElementsByName(trim("AgentCode"))[0].value);
						  	mySql3.addSubPara(window.document.getElementsByName(trim("SaleChnl"))[0].value);
						  	mySql3.addSubPara(window.document.getElementsByName(trim("FiscalYear"))[0].value);
						  	mySql3.addSubPara(window.document.getElementsByName(trim("CustomerName"))[0].value);
						  	mySql3.addSubPara(comcode.substring(0,6));
						  	strSql = mySql3.getString();
      
                           break;
      //信息通知书，付费通知书
      //其它号码：edoracceptno，发送对象：申请人
      case "BQ32":
      case "BQ27":
      case "BQ28":    //    strSql = "select a.prtseq,a.otherno,"
//                                 + " (select codename from ldcode where codetype = 'station' and code = a.managecom ), "
//                                 + " b.edorappname "
//                                 + " from lpedorapp b,loprtmanager a  where 1 = 1 "
//                                 + getWherePart('a.ManageCom', 'ManageCom', 'like')
//                                 + getWherePart('a.prtseq', 'NoticeNo')
//                                 + getWherePart('a.OtherNo', 'OtherNo')
//                                 + getWherePart('a.Code', 'NoticeType')
//                                 + getWherePart('b.edorappname', 'CustomerName')
//                                 + " and b.edoracceptno = a.otherno "
//                                 + " and a.ManageCom LIKE '" + comcode.substring(0,6) + "%%'" //登陆机构权限控制
//                                 + " and a.StateFlag = '1' ";
      
					        var sqlid4="EdorNoticeRePrintSql4";
						  	var mySql4=new SqlClass();
						  	mySql4.setResourceName("bq.EdorNoticeRePrintSql");
						  	mySql4.setSqlId(sqlid4); //指定使用SQL的id
						  	mySql4.addSubPara(window.document.getElementsByName(trim("ManageCom"))[0].value);//指定传入参数
						  	mySql4.addSubPara(window.document.getElementsByName(trim("NoticeNo"))[0].value);
						  	mySql4.addSubPara(window.document.getElementsByName(trim("OtherNo"))[0].value);
						  	mySql4.addSubPara(window.document.getElementsByName(trim("NoticeType"))[0].value);
						  	mySql4.addSubPara(window.document.getElementsByName(trim("CustomerName"))[0].value);
						  	mySql4.addSubPara(comcode.substring(0,6));
						  	strSql = mySql4.getString();
      
      

                           break;
      //补费通知书
      //其它号码：edoracceptno，发送对象：申请人
      case "BQ31":   //     strSql = "select a.prtseq,a.otherno,"
//                                 + " (select codename from ldcode where codetype = 'station' and ldcode.code = a.managecom ), "
//                                 + " b.edorappname "
//                                 + " from lpedorapp b,loprtmanager a  where 1 = 1 "
//                                 + getWherePart('a.ManageCom', 'ManageCom', 'like')
//                                 + getWherePart('a.prtseq', 'NoticeNo')
//                                 + getWherePart('a.OtherNo', 'OtherNo')
//                                 + getWherePart('a.Code', 'NoticeType')
//                                 + getWherePart('b.edorappname', 'CustomerName')
//                                 + " and b.edoracceptno = a.otherno and b.edorstate <> '0'"
//                                 + " and a.ManageCom LIKE '" + comcode.substring(0,6) + "%%'" //登陆机构权限控制
//                                 + " and a.StateFlag = '1' ";
      
					        var sqlid5="EdorNoticeRePrintSql5";
						  	var mySql5=new SqlClass();
						  	mySql5.setResourceName("bq.EdorNoticeRePrintSql");
						  	mySql5.setSqlId(sqlid5); //指定使用SQL的id
						  	mySql5.addSubPara( window.document.getElementsByName(trim("ManageCom"))[0].value);//指定传入参数
						  	mySql5.addSubPara( window.document.getElementsByName(trim("NoticeNo"))[0].value);
						  	mySql5.addSubPara( window.document.getElementsByName(trim("OtherNo"))[0].value);
						  	mySql5.addSubPara( window.document.getElementsByName(trim("NoticeType"))[0].value);
						  	mySql5.addSubPara( window.document.getElementsByName(trim("CustomerName"))[0].value);
						  	mySql5.addSubPara(comcode.substring(0,6));
						  	strSql = mySql5.getString();
      

                           break;
      case "BQ48":
      case "BQ49":     //   strSql = "select a.prtseq,a.otherno,"
//                                 + " (select codename from ldcode where codetype = 'station' and code = a.managecom )  "
//                                 + " from loprtmanager a where 1 = 1 "
//                                 + getWherePart('a.ManageCom', 'ManageCom', 'like')
//                                 + getWherePart('a.prtseq', 'NoticeNo')
//                                 + getWherePart('a.OtherNo', 'OtherNo')
//                                 + getWherePart('a.Code', 'NoticeType')
//                                 + " and a.ManageCom LIKE '" + comcode.substring(0,6) + "%%'" //登陆机构权限控制
//                                 + " and a.StateFlag = '1' ";
      
					        var sqlid6="EdorNoticeRePrintSql6";
						  	var mySql6=new SqlClass();
						  	mySql6.setResourceName("bq.EdorNoticeRePrintSql");
						  	mySql6.setSqlId(sqlid6); //指定使用SQL的id
						  	mySql6.addSubPara( window.document.getElementsByName(trim("ManageCom"))[0].value);//指定传入参数
						  	mySql6.addSubPara( window.document.getElementsByName(trim("NoticeNo"))[0].value);
						  	mySql6.addSubPara( window.document.getElementsByName(trim("OtherNo"))[0].value);
						  	mySql6.addSubPara( window.document.getElementsByName(trim("NoticeType"))[0].value);
						  	mySql6.addSubPara(comcode.substring(0,6));
						  	strSql = mySql6.getString();
      

                           break;
      //领取通知书，其它号码：polno，发送对象：被保人
      case "BQ17":    //    strSql = "select a.prtseq,b.contno,"
//                                 + " (select codename from ldcode where codetype = 'station' and code = a.managecom ), "
//                                 + " b.insuredname,"
//                                 + " (select name from laagent where agentcode = trim(a.agentcode)), "
//                                 + " b.cvalidate,"
//                                 + " (case when exists(select * from lccontstate where statetype = 'Available' and enddate is null and state = '1' and polno = a.otherno ) then '失效' else '有效' end)  "
//                                 + " from lcpol b,loprtmanager a where 1 = 1 "
//                                 + getWherePart('a.ManageCom', 'ManageCom', 'like')
//                                 + getWherePart('a.prtseq', 'NoticeNo')
//                                 + getWherePart('b.contno', 'OtherNo')
//                                 + getWherePart('a.Code', 'NoticeType')
//                                 + getWherePart('a.AgentCode', 'AgentCode')
//                                 + getWherePart('b.SaleChnl', 'SaleChnl')
//                                 + getWherePart('b.insuredname', 'CustomerName')
//                                 + " and a.otherno = b.polno "
//                                 + " and a.ManageCom LIKE '" + comcode.substring(0,6) + "%%'" //登陆机构权限控制
//                                 + " and a.StateFlag = '1' ";
      
					        var sqlid7="EdorNoticeRePrintSql7";
						  	var mySql7=new SqlClass();
						  	mySql7.setResourceName("bq.EdorNoticeRePrintSql");
						  	mySql7.setSqlId(sqlid7); //指定使用SQL的id
						  	mySql7.addSubPara( window.document.getElementsByName(trim("ManageCom"))[0].value);//指定传入参数
						  	mySql7.addSubPara( window.document.getElementsByName(trim("NoticeNo"))[0].value);
						  	mySql7.addSubPara( window.document.getElementsByName(trim("OtherNo"))[0].value);
						  	mySql7.addSubPara( window.document.getElementsByName(trim("NoticeType"))[0].value);
						  	mySql7.addSubPara( window.document.getElementsByName(trim("AgentCode"))[0].value);
						  	mySql7.addSubPara( window.document.getElementsByName(trim("SaleChnl"))[0].value);
						  	mySql7.addSubPara( window.document.getElementsByName(trim("CustomerName"))[0].value);
						  	mySql7.addSubPara(comcode.substring(0,6));
						  	strSql = mySql7.getString();
      
      
                           break;
      //付费通知书(续期领取形式变更)
      case "BQ92":     //   strSql = "select a.PrtSeq, "
//                                 +        "a.StandbyFlag2, "
//                                 +        "(select Name from LDCom where ComCode = a.ManageCom) "
//                                 +   "from LOPrtManager a "
//                                 +  "where 1 = 1 "
//                                 +    "and a.Code = 'BQ92' "
//                                 +    "and a.OtherNoType = '06' "
//                                 + getWherePart('a.PrtSeq', 'NoticeNo')
//                                 + getWherePart('a.ManageCom', 'ManageCom', 'like')
//                                 + getWherePart('a.StandbyFlag2', 'OtherNo')
//                                 +    "and a.ManageCom like '" + comcode.substring(0,6) + "%%' "    //登陆机构权限控制
//                                 +    "and a.StateFlag = '1'";
      
      
					        var sqlid8="EdorNoticeRePrintSql8";
						  	var mySql8=new SqlClass();
						  	mySql8.setResourceName("bq.EdorNoticeRePrintSql");
						  	mySql8.setSqlId(sqlid8); //指定使用SQL的id
						  	mySql8.addSubPara( window.document.getElementsByName(trim("NoticeNo"))[0].value);//指定传入参数
						  	mySql8.addSubPara( window.document.getElementsByName(trim("ManageCom"))[0].value);
						  	mySql8.addSubPara( window.document.getElementsByName(trim("OtherNo"))[0].value);
						  	mySql8.addSubPara(comcode.substring(0,6));
						  	strSql = mySql8.getString(); 
	  	
                           break;
      //收付费方式变更通知书
      case "BQ93":     //   strSql = "select a.PrtSeq, "
//                                 +        "a.StandbyFlag2, "
//                                 +        "(select Name from LDCom where ComCode = a.ManageCom) "
//                                 +   "from LOPrtManager a "
//                                 +  "where 1 = 1 "
//                                 +    "and a.Code = 'BQ93' "
//                                 +    "and a.OtherNoType = '06' "
//                                 + getWherePart('a.PrtSeq', 'NoticeNo')
//                                 + getWherePart('a.ManageCom', 'ManageCom', 'like')
//                                 + getWherePart('a.StandbyFlag2', 'OtherNo')
//                                 +    "and a.ManageCom like '" + comcode.substring(0,6) + "%%' "    //登陆机构权限控制
//                                 +    "and a.StateFlag = '1'";
      
					        var sqlid9="EdorNoticeRePrintSql9";
						  	var mySql9=new SqlClass();
						  	mySql9.setResourceName("bq.EdorNoticeRePrintSql");
						  	mySql9.setSqlId(sqlid9); //指定使用SQL的id
						  	mySql9.addSubPara( window.document.getElementsByName(trim("NoticeNo"))[0].value);//指定传入参数
						  	mySql9.addSubPara( window.document.getElementsByName(trim("ManageCom"))[0].value);
						  	mySql9.addSubPara( window.document.getElementsByName(trim("OtherNo"))[0].value);
						  	mySql9.addSubPara(comcode.substring(0,6));
						  	strSql = mySql9.getString(); 
      
                           break;
      //满期终止通知书
      //其它号码：polno，发送对象：投保人
      case "BQ34":    //    strSql = "select a.prtseq,b.contno,"
//                                 + " (select codename from ldcode where codetype = 'station' and code = a.managecom ), "
//                                 + " b.appntname,"
//                                 + " (select name from laagent where agentcode = trim(a.agentcode)), "
//                                 + " b.cvalidate,"
//                                 + " '终止'  "
//                                 + " from lcpol b,loprtmanager a where 1 = 1 "
//                                 + getWherePart('a.ManageCom', 'ManageCom', 'like')
//                                 + getWherePart('b.ManageCom', 'ManageCom', 'like')
//                                 + getWherePart('a.prtseq', 'NoticeNo')
//                                 + getWherePart('b.contno', 'OtherNo')
//                                 + getWherePart('a.Code', 'NoticeType')
//                                 + getWherePart('a.AgentCode', 'AgentCode')
//                                 + getWherePart('b.SaleChnl', 'SaleChnl')
//                                 + getWherePart('b.appntname', 'CustomerName')
//                                 + " and a.otherno = b.polno  and b.appflag = '4' and b.polno = b.mainpolno "
//                                 + " and a.ManageCom LIKE '" + comcode.substring(0,6) + "%%'" //登陆机构权限控制
//                                 + " and a.StateFlag = '1' ";
      
					        var sqlid10="EdorNoticeRePrintSql10";
						  	var mySql10=new SqlClass();
						  	mySql10.setResourceName("bq.EdorNoticeRePrintSql");
						  	mySql10.setSqlId(sqlid10); //指定使用SQL的id
						  	mySql10.addSubPara( window.document.getElementsByName(trim("ManageCom"))[0].value);//指定传入参数
						  	mySql10.addSubPara( window.document.getElementsByName(trim("ManageCom"))[0].value);
						  	mySql10.addSubPara( window.document.getElementsByName(trim("NoticeNo"))[0].value);
						  	mySql10.addSubPara( window.document.getElementsByName(trim("OtherNo"))[0].value);
						  	mySql10.addSubPara( window.document.getElementsByName(trim("NoticeType"))[0].value);
						  	mySql10.addSubPara( window.document.getElementsByName(trim("AgentCode"))[0].value);
						  	mySql10.addSubPara( window.document.getElementsByName(trim("SaleChnl"))[0].value);
						  	mySql10.addSubPara( window.document.getElementsByName(trim("CustomerName"))[0].value);
						  	mySql10.addSubPara(comcode.substring(0,6));
						  	strSql = mySql10.getString();
      
                           break;
      //失效预终止通知书
      //其它号码：polno，发送对象：投保人
      case "BQ29":    //    strSql = "select a.prtseq,b.contno,"
//                                 + " (select codename from ldcode where codetype = 'station' and code = a.managecom ), "
//                                 + " b.appntname,"
//                                 + " (select name from laagent where agentcode = trim(a.agentcode)), "
//                                 + " b.cvalidate,"
//                                 + " '失效' "
//                                 + " from lcpol b,loprtmanager a where 1 = 1 "
//                                 + " and exists(select 'X' from lccontstate where statetype='Available' and enddate is null and state = '1' and polno = b.polno)"
//                                 + getWherePart('a.ManageCom', 'ManageCom', 'like')
//                                 + getWherePart('a.prtseq', 'NoticeNo')
//                                 + getWherePart('b.contno', 'OtherNo')
//                                 + getWherePart('a.Code', 'NoticeType')
//                                 + getWherePart('a.AgentCode', 'AgentCode')
//                                 + getWherePart('b.SaleChnl', 'SaleChnl')
//                                 + getWherePart('b.appntname', 'CustomerName')
//                                 + " and a.otherno = b.polno "
//                                 + " and a.ManageCom LIKE '" + comcode.substring(0,6) + "%%'" //登陆机构权限控制
//                                 + " and a.StateFlag = '1' ";
      
					        var sqlid11="EdorNoticeRePrintSql11";
						  	var mySql11=new SqlClass();
						  	mySql11.setResourceName("bq.EdorNoticeRePrintSql");
						  	mySql11.setSqlId(sqlid11); //指定使用SQL的id
						  	mySql11.addSubPara( window.document.getElementsByName(trim("ManageCom"))[0].value);//指定传入参数
						  	mySql11.addSubPara( window.document.getElementsByName(trim("NoticeNo"))[0].value);
						  	mySql11.addSubPara( window.document.getElementsByName(trim("OtherNo"))[0].value);
						  	mySql11.addSubPara( window.document.getElementsByName(trim("NoticeType"))[0].value);
						  	mySql11.addSubPara( window.document.getElementsByName(trim("AgentCode"))[0].value);
						  	mySql11.addSubPara( window.document.getElementsByName(trim("SaleChnl"))[0].value);
						  	mySql11.addSubPara( window.document.getElementsByName(trim("CustomerName"))[0].value);
						  	mySql11.addSubPara(comcode.substring(0,6));
						  	strSql = mySql11.getString();
      
      
                           break;
      //保单失效通知书
      //其它号码：contno，发送对象：投保人
          case "42":
//                          strSql = "select a.prtseq,a.otherno,"
//                                 + " (select codename from ldcode where codetype = 'station' and code = a.managecom ), "
//                                 + " b.appntname,"
//                                 + " (select name from laagent where agentcode = trim(a.agentcode)), "
//                                 + " b.cvalidate,"
//                                 + " '失效'  "
//                                 + " from lccont b,loprtmanager a where 1 = 1 "
//                                 + " and exists(select 'X' from lccontstate where statetype='Available' and enddate is null and state = '1' and contno = b.contno)"
//                                 + getWherePart('a.ManageCom', 'ManageCom', 'like')
//                                 + getWherePart('a.prtseq', 'NoticeNo')
//                                 + getWherePart('a.OtherNo', 'OtherNo')
//                                 + getWherePart('a.Code', 'NoticeType')
//                                 + getWherePart('a.AgentCode', 'AgentCode')
//                                 + getWherePart('b.SaleChnl', 'SaleChnl')
//                                 + getWherePart('b.appntname', 'CustomerName')
//                                 + " and a.otherno = b.contno "
//                                 + " and a.ManageCom LIKE '" + comcode.substring(0,6) + "%%'" //登陆机构权限控制
//                                 + " and a.StateFlag = '1' ";
                          
                            var sqlid12="EdorNoticeRePrintSql12";
						  	var mySql12=new SqlClass();
						  	mySql12.setResourceName("bq.EdorNoticeRePrintSql");
						  	mySql12.setSqlId(sqlid12); //指定使用SQL的id
						  	mySql12.addSubPara( window.document.getElementsByName(trim("ManageCom"))[0].value);//指定传入参数
						  	mySql12.addSubPara( window.document.getElementsByName(trim("NoticeNo"))[0].value);
						  	mySql12.addSubPara( window.document.getElementsByName(trim("OtherNo"))[0].value);
						  	mySql12.addSubPara( window.document.getElementsByName(trim("NoticeType"))[0].value);
						  	mySql12.addSubPara( window.document.getElementsByName(trim("AgentCode"))[0].value);
						  	mySql12.addSubPara( window.document.getElementsByName(trim("SaleChnl"))[0].value);
						  	mySql12.addSubPara( window.document.getElementsByName(trim("CustomerName"))[0].value);
						  	mySql12.addSubPara(comcode.substring(0,6));
						  	strSql = mySql12.getString();
                          
                          
                           break;

      //自垫预终止通知书、自垫终止通知书、贷款自垫预终止通知书
      //保费自垫通知书、保单失效通知书、保单质押贷款还款通知书
      //其它号码：contno，发送对象：投保人
      case "41":
      case "BQ21":
      case "BQ22":
      case "BQ38":
      case "BQ39":
      case "BQ10":
//                          strSql = "select a.prtseq,a.otherno,"
//                                 + " (select codename from ldcode where codetype = 'station' and code = a.managecom ), "
//                                 + " b.appntname,"
//                                 + " (select name from laagent where agentcode = trim(a.agentcode)), "
//                                 + " b.cvalidate,"
//                                 + " (case when exists(select * from lccontstate where statetype = 'Available' and enddate is null and state = '1' and contno = a.otherno ) then '失效' else '有效' end)  "
//                                 + " from lccont b,loprtmanager a where 1 = 1 "
//                                 + getWherePart('a.ManageCom', 'ManageCom', 'like')
//                                 + getWherePart('a.prtseq', 'NoticeNo')
//                                 + getWherePart('a.OtherNo', 'OtherNo')
//                                 + getWherePart('a.Code', 'NoticeType')
//                                 + getWherePart('a.AgentCode', 'AgentCode')
//                                 + getWherePart('b.SaleChnl', 'SaleChnl')
//                                 + getWherePart('b.appntname', 'CustomerName')
//                                 + " and a.otherno = b.contno "
//                                 + " and a.ManageCom LIKE '" + comcode.substring(0,6) + "%%'" //登陆机构权限控制
//                                 + " and a.StateFlag = '1' ";
                          
                            var sqlid13="EdorNoticeRePrintSql13";
						  	var mySql13=new SqlClass();
						  	mySql13.setResourceName("bq.EdorNoticeRePrintSql");
						  	mySql13.setSqlId(sqlid13); //指定使用SQL的id
						  	mySql13.addSubPara( window.document.getElementsByName(trim("ManageCom"))[0].value);//指定传入参数
						  	mySql13.addSubPara( window.document.getElementsByName(trim("NoticeNo"))[0].value);
						  	mySql13.addSubPara( window.document.getElementsByName(trim("OtherNo"))[0].value);
						  	mySql13.addSubPara( window.document.getElementsByName(trim("NoticeType"))[0].value);
						  	mySql13.addSubPara( window.document.getElementsByName(trim("AgentCode"))[0].value);
						  	mySql13.addSubPara( window.document.getElementsByName(trim("SaleChnl"))[0].value);
						  	mySql13.addSubPara( window.document.getElementsByName(trim("CustomerName"))[0].value);
						  	mySql13.addSubPara(comcode.substring(0,6));
						  	strSql = mySql13.getString();
                          
                          
                           break;
      //公司解约通知书
      //其它号码：contno，发送对象：投保人
       case "BQ30":
//                          strSql = "select a.prtseq,a.otherno,"
//                                 + " (select codename from ldcode where codetype = 'station' and code = a.managecom ), "
//                                 + " b.appntname,"
//                                 + " (select name from laagent where agentcode = trim(a.agentcode)), "
//                                 + " b.cvalidate,"
//                                 + " '终止'  "
//                                 + " from lccont b,loprtmanager a where 1 = 1 "
//                                 + getWherePart('a.ManageCom', 'ManageCom', 'like')
//                                 + getWherePart('a.prtseq', 'NoticeNo')
//                                 + getWherePart('a.OtherNo', 'OtherNo')
//                                 + getWherePart('a.Code', 'NoticeType')
//                                 + getWherePart('a.AgentCode', 'AgentCode')
//                                 + getWherePart('b.SaleChnl', 'SaleChnl')
//                                 + getWherePart('b.appntname', 'CustomerName')
//                                 + " and a.otherno = b.contno and b.appflag = '4' "
//                                 + " and a.ManageCom LIKE '" + comcode.substring(0,6) + "%%'" //登陆机构权限控制
//                                 + " and a.StateFlag = '1' ";
                          
                            var sqlid14="EdorNoticeRePrintSql14";
						  	var mySql14=new SqlClass();
						  	mySql14.setResourceName("bq.EdorNoticeRePrintSql");
						  	mySql14.setSqlId(sqlid14); //指定使用SQL的id
						  	mySql14.addSubPara( window.document.getElementsByName(trim("ManageCom"))[0].value);//指定传入参数
						  	mySql14.addSubPara( window.document.getElementsByName(trim("NoticeNo"))[0].value);
						  	mySql14.addSubPara( window.document.getElementsByName(trim("OtherNo"))[0].value);
						  	mySql14.addSubPara( window.document.getElementsByName(trim("NoticeType"))[0].value);
						  	mySql14.addSubPara( window.document.getElementsByName(trim("AgentCode"))[0].value);
						  	mySql14.addSubPara( window.document.getElementsByName(trim("SaleChnl"))[0].value);
						  	mySql14.addSubPara( window.document.getElementsByName(trim("CustomerName"))[0].value);
						  	mySql14.addSubPara(comcode.substring(0,6));
						  	strSql = mySql14.getString();
                          
                           break;
   	  case "BQ00":      //     strSql = "select a.prtseq,a.otherno,"
//		 	                         + " (select codename from ldcode where codetype = 'station' and code = a.managecom ) "
//	                             + ",a.StandByflag2 from loprtmanager a where 1 = 1 "
//	                             + getWherePart('a.ManageCom', 'ManageCom', 'like')
//	                             + getWherePart('a.prtseq', 'NoticeNo')
//	                             + getWherePart('a.OtherNo', 'OtherNo')
//	                             + getWherePart('a.Code', 'NoticeType')
//	                             + " and a.ManageCom LIKE '" + comcode.substring(0,6) + "%%'" //登陆机构权限控制
//	                             + " and a.StateFlag = '1' ";
   	  
					   	    var sqlid15="EdorNoticeRePrintSql15";
						  	var mySql15=new SqlClass();
						  	mySql15.setResourceName("bq.EdorNoticeRePrintSql");
						  	mySql15.setSqlId(sqlid15); //指定使用SQL的id
						  	mySql15.addSubPara( window.document.getElementsByName(trim("ManageCom"))[0].value);//指定传入参数
						  	mySql15.addSubPara( window.document.getElementsByName(trim("NoticeNo"))[0].value);
						  	mySql15.addSubPara( window.document.getElementsByName(trim("OtherNo"))[0].value);
						  	mySql15.addSubPara( window.document.getElementsByName(trim("NoticeType"))[0].value);
						  	mySql15.addSubPara(comcode.substring(0,6));
						  	strSql = mySql15.getString();
   	  
	                       break;    
   	  case "BQ01":       //    strSql = "select a.prtseq,a.otherno,"
//		 	                       + " (select codename from ldcode where codetype = 'station' and code = a.managecom ) "
//	                             + ",a.StandByflag2 from loprtmanager a where 1 = 1 "
//	                             + getWherePart('a.ManageCom', 'ManageCom', 'like')
//	                             + getWherePart('a.prtseq', 'NoticeNo')
//	                             + getWherePart('a.OtherNo', 'OtherNo')
//	                             + getWherePart('a.Code', 'NoticeType')                             
//	                             + " and a.ManageCom LIKE '" + comcode.substring(0,6) + "%%'" //登陆机构权限控制
//	                             + " and a.StateFlag = '1' ";
//   	  
						   	var sqlid16="EdorNoticeRePrintSql16";
						  	var mySql16=new SqlClass();
						  	mySql16.setResourceName("bq.EdorNoticeRePrintSql");
						  	mySql16.setSqlId(sqlid16); //指定使用SQL的id
						  	mySql16.addSubPara( window.document.getElementsByName(trim("ManageCom"))[0].value);//指定传入参数
						  	mySql16.addSubPara( window.document.getElementsByName(trim("NoticeNo"))[0].value);
						  	mySql16.addSubPara( window.document.getElementsByName(trim("OtherNo"))[0].value);
						  	mySql16.addSubPara( window.document.getElementsByName(trim("NoticeType"))[0].value);
						  	mySql16.addSubPara(comcode.substring(0,6));
						  	strSql = mySql16.getString();
   	  
	                       break;   
      default:      //      strSql = "select a.prtseq,a.otherno,"
//                                 + " (select codename from ldcode where codetype = 'station' and code = a.managecom) "
//                                 + " from loprtmanager a where 1 = 1 "
//                                 + getWherePart('a.ManageCom', 'ManageCom', 'like')
//                                 + getWherePart('a.prtseq', 'NoticeNo')
//                                 + getWherePart('a.OtherNo', 'OtherNo')
//                                 + getWherePart('a.Code', 'NoticeType')
//                                 + " and a.ManageCom LIKE '" + comcode.substring(0,6) + "%%'" //登陆机构权限控制
//                                 + " and a.StateFlag = '1' ";
      
					        var sqlid17="EdorNoticeRePrintSql17";
						  	var mySql17=new SqlClass();
						  	mySql17.setResourceName("bq.EdorNoticeRePrintSql");
						  	mySql17.setSqlId(sqlid17); //指定使用SQL的id
						  	mySql17.addSubPara( window.document.getElementsByName(trim("ManageCom"))[0].value);//指定传入参数
						  	mySql17.addSubPara( window.document.getElementsByName(trim("NoticeNo"))[0].value);
						  	mySql17.addSubPara( window.document.getElementsByName(trim("OtherNo"))[0].value);
						  	mySql17.addSubPara( window.document.getElementsByName(trim("NoticeType"))[0].value);
						  	mySql17.addSubPara(comcode.substring(0,6));
						  	strSql = mySql17.getString();
      
                           break;
   } 
   if(trim(fm.StartDate.value) != "" && trim(fm.EndDate.value) != "")
   {
       strSql += getDateCondition(tNoticeType);//统计起止期的条件
   }
   return strSql;
}

//初始化管理机构，最长截取六位
function initManageCom()
{
    if(comcode.substring(0,6) !=null && comcode.substring(0,6) != "")
    {
        var i,j,m,n;
        var returnstr;
        var tTurnPage = new turnPageClass();
//        var strSQL = "select comcode,name from ldcom where comcode like '"+comcode.substring(0,6)+"%%' order by comcode";
        
        var sqlid18="EdorNoticeRePrintSql18"
        var mySql18=new SqlClass
        mySql18.setResourceName("bq.EdorNoticeRePrintSql");
	  	mySql18.setSqlId(sqlid18); //指定使用SQL的id
        mySql18.addSubPara(comcode.substring(0,6));
        var strSQL = mySql18.getString();
        	
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
function getDateCondition(tNoticeType)
{
   var strSql = "";
   switch(tNoticeType)
   {
       case "30":
                          strSql =" and exists (select 'x' from lobonuspol c where c.contno = a.otherno and c.fiscalyear=a.remark and c.agetdate is not null and c.sgetdate>='"+fm.StartDate.value+"' and c.sgetdate<='"+fm.EndDate.value+"')" ;//应按分红的会计年度的生效对应日查询
                          break;
       case "BQ17":
                          strSql = " And to_date(a.standbyflag7,'yyyy-mm-dd') Between '"+fm.StartDate.value+"' And '"+fm.EndDate.value+"'";
                          break;
       case "BQ10":
       case "BQ21":
       case "BQ22":
       case "BQ38":
       case "BQ39":
       case "BQ29":
       case "42":
                          strSql = " And to_date(a.StandByflag1,'yyyy-mm-dd') Between '"+fm.StartDate.value+"' And '"+fm.EndDate.value+"'";
                          break;
    	case "BQ00":
   	                      strSql = " And to_date(a.StandByflag2,'yyyy-mm-dd') Between '"+fm.StartDate.value+"' And '"+fm.EndDate.value+"'";
   	                      break;  	                      
   	  case "BQ01":
   	                      strSql = " And to_date(a.StandByflag2,'yyyy-mm-dd') Between '"+fm.StartDate.value+"' And '"+fm.EndDate.value+"'";
   	                      break;
      default:
                          strSql = " And a.makedate Between '"+fm.StartDate.value+"' And '"+fm.EndDate.value+"'";
                          break;

   }
   return strSql;
}