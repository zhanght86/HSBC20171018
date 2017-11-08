/*******************************************************************************
* <p>Title: 综合查询-集体保全查询</p>
* <p>Description: 集体保全查询-js页面脚本文件</p>
* <p>Copyright: Copyright (c) 2006 Sinosoft, Co.,Ltd. All Rights Reserved</p>
* <p>Company: 中科软科技股份有限公司</p>
* <p>WebSite: http://www.sinosoft.com.cn</p>
*
* @todo     : 保全-VIP客户查询
* @author   : liuxiaosong
* @version  : 1.00
* @date     : 2006-10-16
* 更新记录：  更新人    更新日期     更新原因/内容
******************************************************************************/

var showInfo;  //弹出界面提示信息
var turnPage = new turnPageClass(); //查询结果翻页器，必须有
var mySql = new SqlClass();

/**
 * 查询核心逻辑
 */
function grpBqQuery()
{
    //数据前台校验，不建议这样做，应该使用verify
    if(fm.EdorAcceptNo.value == "" && fm.OtherNo.value == ""&& fm.EdorAppName.value == ""&& fm.EdorNo.value == ""&& fm.EdorAppDate.value == "")
    {
        alert("请您至少输入或选择如下查询条件中的一个：保全受理号，保单号/客户号，申请人姓名，保全申请日期，批单号。");
        fm.EdorAcceptNo.focus();
        return;
    }

    //查询核心SQL
    var strSQL = "";

    //由于加上union后，操作人这个字段造成js解析不了此sql，所以一开始
    //先不查此字段及与此字段查询同一表的操作岗位字段，等Mulline赋值后再逐条赋此两个字段
   /* strSQL = "select a,o,b,c,d,e,f,g,h,i,j,k,l,m,n,p,q from ("
    +" select EdorAcceptNo a,EdorConfNo o,otherno b,(select codename from ldcode where codetype = 'gedornotype' and  (code) =  (othernotype)) c,"
    +" EdorAppName d,nvl(to_char(a.GetMoney),' ') e,Operator f,ConfOperator g,"
    +" (select CodeName from LDCode where codetype = 'edorstate' and trim(code) =  (edorstate)) h,"
    //+" (select (select codename from ldcode where codetype = 'bqactivityname' and code = activityid) from lwmission where  (missionprop1) = trim(edoracceptno) and activityid in('0000000001','0000000002','0000000004','0000000005','0000000007','0000000009','0000000010','0000000092')), "
    //+"(select username from lduser where usercode = (select  defaultoperator from lwmission where  (missionprop1) = trim(edoracceptno) and activityid in('0000000001','0000000002','0000000004','0000000005','0000000007','0000000009','0000000010','0000000092'))), "
    +"'' i,a.operator j,"
    +"(select edorname from lmedoritem where appobj in('G') and rownum = 1 and edorcode = (select edortype  from lpgrpedoritem where edoracceptno = a.edoracceptno and rownum = 1)) k,"
    +" nvl((select edorvalidate from lpgrpedoritem where edoracceptno = a.edoracceptno and rownum = 1),'') l,  "
    +" makedate m,maketime n,trim(a.managecom)||'-'||(select name from ldcom where comcode = a.managecom) p,  "
    +"(select edorno from lpgrpedoritem where edoracceptno = a.edoracceptno and rownum = 1) q  "
    +" from LPEdorApp a "
    +" Where 1 = 1   "
    +" and a.OtherNoType in ('2', '4') " //XinYQ added on 2007-04-11
    + getWherePart('EdorAcceptNo','EdorAcceptNo')
    + getWherePart('OtherNo','OtherNo')
    + getWherePart('OtherNoType','OtherNoType')
    + getWherePart('EdorAppName','EdorAppName')
    + getWherePart('ManageCom','ManageCom','like')
    + getWherePart('AppType','AppType')
    + getWherePart('EdorAppDate','EdorAppDate')
    + getWherePart('EdorConfNo','EdorNo')
    //+ " and managecom like '"+comCode.substring(0,6)+"%%' ";
    strSQL += " ) order by l,m desc ";
    // for oracle sql
    //    strSQL=" select a.EdorAcceptNo,a.otherno,a.othernotype,a.EdorAppName, a.GetMoney,   decode( (select max(c.edorstate)|| min(c.edorstate) from lpedoritem c  where 1 = 1 and c.edoracceptno = a.edoracceptno "

    //             +" ), '00','保全确认','11','正在申请','22','申请确认','21','部分申请确认','20', '部分保全确认','正在申请') from LPEdorApp a where 1 = 1  "
    //             + getWherePart('a.EdorAcceptNo','EdorAcceptNo')
    //             + getWherePart('a.OtherNo','OtherNo')
    //             + getWherePart('a.OtherNoType','OtherNoType')
    //             + getWherePart('a.EdorAppName','EdorAppName')
    //             + getWherePart('a.AppType','AppType')
    //             + getWherePart('a.EdorAppDate','EdorAppDate');
    //}
    //查询SQL，返回结果字符串*/
    mySql = new SqlClass();
	mySql.setResourceName("sys.AllGBqQuerySql");
	mySql.setSqlId("AllGBqQuerySql1");
	mySql.addSubPara(fm.EdorAcceptNo.value ); 
	mySql.addSubPara(fm.OtherNo.value ); 
	mySql.addSubPara(fm.OtherNoType.value ); 
	mySql.addSubPara(fm.EdorAppName.value ); 
	mySql.addSubPara(fm.ManageCom.value ); 
	mySql.addSubPara(fm.AppType.value ); 
	mySql.addSubPara(fm.EdorAppDate.value ); 
	mySql.addSubPara(fm.EdorNo.value ); 
	
    turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);

    //判断是否查询成功
    if (!turnPage.strQueryResult) {
        PolGrid.clearData();
        alert("查询失败！");
        return false;
    }

    //查询成功则拆分字符串，返回二维数?
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

    //设置初始化过的MULTILINE对象
    turnPage.pageDisplayGrid = PolGrid;

    //保存SQL语句
    turnPage.strQuerySql = strSQL;

    //设置查询起始位置
    turnPage.pageIndex = 0;

    //在查询结果数组中取出符合页面显示大小设置的数?
    var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
    arrGrid = arrDataSet;
    //调用MULTILINE对象显示查询结果
    displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
    //给操作岗位、操作人赋值；注意此处只是赋一页的值，所以要在翻页按钮处添加相同的函数
    queryLWMission();
}

/**
*承上，查询操作岗位、操作人
*/
function queryLWMission()
{
    var i = 0;
    for(i;i<PolGrid.mulLineCount;i++)
    {
        var tEdorAcceptNo = PolGrid.getRowColData(i,1);
       /* strSQL = "select (select codename from ldcode where codetype = 'bqactivityname' and code = activityid),"
        + " (select username from lduser where usercode = defaultoperator) "
        + " from lwmission where 1=1 "
        + " and exists(select 'X' from ldcode where codetype = 'bqactivityname' and othersign = 'mainnote' and code = activityid) "
        + " and missionprop1 = '"+tEdorAcceptNo+"' ";*/
        mySql = new SqlClass();
	mySql.setResourceName("sys.AllGBqQuerySql");
	mySql.setSqlId("AllGBqQuerySql2");
	mySql.addSubPara(tEdorAcceptNo );  
        var brr = easyExecSql(mySql.getString(), 1, 0,"","",1);//注意此处第6个参数应设为1，不使用翻页功能，否则可能会把全局变量turnPage覆盖
        if(brr)
        {
            PolGrid.setRowColData(i, 9, brr[0][0]);
            PolGrid.setRowColData(i, 10, brr[0][1]);
        }
    }
}


//打印批单，没用

function PrtEdor()
{
	var arrReturn = new Array();
	
	var tSel = PolGrid.getSelNo();
	
	
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录，再点击查看按钮。" );
	else
	{
		var state =PolGrid. getRowColData(tSel-1,9) ;
		//alert(state);
		//return;
		if (state!="确认生效")
		alert ("所选批单暂未生效，不能打印！");
		else{
			var EdorNo=PolGrid. getRowColData(tSel-1,17) ;
			//var varSrc="&EdorNo=" + EdorNo;
			//var newWindow = OpenWindowNew("../sys/BqDetailQueryFrame.jsp?Interface= ../sys/BqDetailQuery.jsp" + varSrc,"保全查询明细","left");
			fm.action = "../f1print/EndorsementF1PJ1.jsp?EdorNo=" + EdorNo;
			fm.target="f1print";
			document.getElementById("fm").submit();
		}
	}
}

function PrtEdorBill()
{
	var arrReturn = new Array();
	
	var tSel = PolGrid.getSelNo();
	
	
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录，再点击查看按钮。" );
	else
	{
		var state =PolGrid. getRowColData(tSel-1,9) ;
		//alert(state);
		//return;
		if (state!="确认生效")
		alert ("所选批单暂未生效，不能打印！");
		else{
			var EdorNo=PolGrid. getRowColData(tSel-1,17) ;
			
			var QuerySQL, arrResult;
     // QuerySQL = "select 'X' from LPEdorPrint2 where EdorNo = '" + EdorNo + "'";
            //alert(QuerySQL);
            mySql = new SqlClass();
	mySql.setResourceName("sys.AllGBqQuerySql");
	mySql.setSqlId("AllGBqQuerySql3");
	mySql.addSubPara(EdorNo ); 
      try
      {
          arrResult = easyExecSql(mySql.getString(), 1, 0);
      }
      catch (ex)
      {
          alert("警告：查询人名清单信息出现异常！ ");
          return;
      }
      if (arrResult == null)
      {
          alert("该保单此次批改项目没有人名清单信息！ ");
          return;
      }
      else
      {
          fm.action = "../f1print/ReEndorsementF1PJ1.jsp?EdorNo=" + EdorNo + "&type=Bill";
          fm.target = "_blank";
          document.getElementById("fm").submit();
      }
			//var varSrc="&EdorNo=" + EdorNo;
			//var newWindow = OpenWindowNew("../sys/BqDetailQueryFrame.jsp?Interface= ../sys/BqDetailQuery.jsp" + varSrc,"保全查询明细","left");
			//fm.action = "../f1print/EndorsementF1PJ1.jsp?EdorNo=" + EdorNo;
			//fm.target="f1print";
			//document.getElementById("fm").submit();
		}
	}
}

/**
* 查询保全操作轨迹
*/

function MissionQuery()
{
    var selno = PolGrid.getSelNo()-1;
    if (selno <0)
    {
        alert("请选择要处理的任务！");
        return;
    }
    var EdorAcceptNo = PolGrid.getRowColData(selno, 1);
    //var strSql = "select missionid from lbmission where missionprop1 = '"+EdorAcceptNo+"' union select missionid from lwmission where missionprop1 = '"+EdorAcceptNo+"' ";
    mySql = new SqlClass();
	mySql.setResourceName("sys.AllGBqQuerySql");
	mySql.setSqlId("AllGBqQuerySql4");
	mySql.addSubPara(EdorAcceptNo ); 
	mySql.addSubPara(EdorAcceptNo ); 
    var brr =  easyExecSql(mySql.getString());
    if(!brr)
    {
        alert("该保全受理轨迹信息不存在！");
    }
    var pMissionID = brr[0][0];
    window.open("../bq/EdorMissionFrame.jsp?MissionID="+pMissionID,"window3","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}

/**
*用记事本查看
*/
function showNotePad()
{

    var selno = PolGrid.getSelNo()-1;
    if (selno <0)
    {
        alert("请选择要处理的任务！");
        return;
    }
    var EdorAcceptNo = PolGrid.getRowColData(selno, 1);
    //var strSql = "select missionid from lbmission where missionprop1 = '"+EdorAcceptNo+"' union select missionid from lwmission where missionprop1 = '"+EdorAcceptNo+"' ";
    mySql = new SqlClass();
	mySql.setResourceName("sys.AllGBqQuerySql");
	mySql.setSqlId("AllGBqQuerySql5");
	mySql.addSubPara(EdorAcceptNo ); 
	mySql.addSubPara(EdorAcceptNo ); 
    var brr =  easyExecSql(mySql.getString());
    if(!brr)
    {
        alert("该保全受理工作流信息不存在！");
        return;
    }
    var MissionID = brr[0][0];

    var OtherNoType = "1";
    var UnSaveFlag = "1";//隐藏保存按钮
    if(MissionID == null || MissionID == "")
    {
        alert("MissionID为空！");
        return;
    }
    var varSrc= "&MissionID="+ MissionID + "&NoType="+ OtherNoType + "&UnSaveFlag="+ UnSaveFlag;
    var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface= ../uw/WorkFlowNotePadInput.jsp" + varSrc,"工作流记事本查看","left");
}
// end function showNotePad

/*
*显示div，第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
*/
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

//保全扫描影像查询
function scanDetail()
{
    var nSelNo;
    try
    {
        nSelNo = PolGrid.getSelNo() - 1;
    }
    catch (ex) {}
    if (nSelNo != null && nSelNo >= 0)
    {
        var EdorAcceptNo = PolGrid.getRowColData(nSelNo, 1);
       // var str = "select docid from es_doc_relation where bussno = '" + EdorAcceptNo + "' and busstype = 'BQ' and relaflag = '0'";
        mySql = new SqlClass();
	mySql.setResourceName("sys.AllGBqQuerySql");
	mySql.setSqlId("AllGBqQuerySql6");
	mySql.addSubPara(EdorAcceptNo );  
        var arrResult = easyExecSql(mySql.getString(), 1, 0,"","",1);
        if (arrResult == null)
        {
             alert("此次保全申请没有相关扫描影像资料！");
             return;
        }
        var PrtNo = arrResult[0][0];
        //var tResult = easyExecSql("select a.codealias from ldcode a,es_doc_relation b where a.codetype = 'bqscan' and trim(a.code) = trim(b.subtype) and b.busstype = 'BQ' and b.bussno = '"+EdorAcceptNo+"'", 1, 0,"","",1);
        mySql = new SqlClass();
	mySql.setResourceName("sys.AllGBqQuerySql");
	mySql.setSqlId("AllGBqQuerySql7");
	mySql.addSubPara(EdorAcceptNo );  
	var tResult = easyExecSql( mySql.getString(), 1, 0,"","",1);
        if (tResult == null)
        {
             alert("查询保全扫描子业务类型编码失败！");
             return;
        }
        var varSrc = "ScanFlag = 1&prtNo=" + PrtNo + "&SubType=" + tResult[0][0]+ "&EdorAcceptNo="+EdorAcceptNo;
        var newWindow = OpenWindowNew("../bq/EdorScan.jsp?" + varSrc,"保全扫描影像","left");
    }
}
