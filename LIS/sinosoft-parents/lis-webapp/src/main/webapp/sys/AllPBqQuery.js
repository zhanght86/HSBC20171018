//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();
var turnPage1= new turnPageClass();
var mySql = new SqlClass();

function actionKeyUp(cObj)
{
    var tRiskCode = document.all('RiskCode').value;
    mEdorType = " 1 and RiskCode=#112103# and AppObj=#I#";
    //alert(mEdorType);
    showCodeListKey('EdorCode',[cObj], null, null, mEdorType, "1");
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

//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
            parent.fraMain.rows = "0,0,0,0,*";
  }
    else {
        parent.fraMain.rows = "0,0,0,82,*";
    }

    parent.fraMain.rows = "0,0,0,0,*";
}



// 查询按钮
function easyQueryClick()
{
    if(fm.EdorAcceptNo.value == "" && fm.OtherNo.value == ""&& fm.EdorAppName.value == ""&& fm.EdorNo.value == ""&& fm.EdorAppDate.value == "")
    {
        alert("请您至少输入或选择如下查询条件中的一个：保全受理号，保单号/客户号，申请人姓名，保全申请日期，批单号。");
        fm.EdorAcceptNo.focus();
        return;
    }

    // 书写SQL语句
    var strSQL = "";
    //if (tflag=="0")
    //  {
    //      strSQL = "select LPEdorMain.EdorNo,min(LPEdorMain.PolNo),min(LPEdorMain.InsuredNo),min(LPEdorMain.InsuredName),sum(LPEdorMain.GetMoney),decode(max(LPEdorMain.EdorState)||min(LPEdorMain.EdorState),'00','保全确认','11','正在申请','22','申请确认','21','部分申请确认','20','部分保全确认') from LPEdorMain where 1=1 "
    //                   + getWherePart( 'LPEdorMain.PolNo','PolNo' )
    //                   + "group by LPEdorMain.EdorNo";
    //  }
    //else
    //  {
            // for db2 sql
            //由于加上union后，操作人这个字段造成js解析不了此sql，所以一开始
            //先不查此字段及与此字段查询同一表的操作岗位字段，等Mulline赋值后再逐条赋此两个字段
           /* strSQL = "select a,b,c,d,e,f,g,h,i,j,k,l,m,n from ("
             +" select EdorAcceptNo a,otherno b,(select codename from ldcode where codetype = 'edornotype' and  (code) =  (othernotype)) c,"
             +" EdorAppName d,nvl(to_char(a.GetMoney),' ') e,Operator f,ConfOperator g,"
             +"  decode((select count(*) from lwmission where missionprop1=a.edoracceptno and activityid='0000000007'),0,( decode((select count(*) from lwmission where missionprop1=a.edoracceptno and activityid='0000000005'),0,( select CodeName from LDCode where codetype = 'edorstate' and trim(code) =  (edorstate)),'人工核保中')),'审批中') h,"
             //+" (select (select codename from ldcode where codetype = 'bqactivityname' and code = activityid) from lwmission where  (missionprop1) = trim(edoracceptno) and activityid in('0000000001','0000000002','0000000004','0000000005','0000000007','0000000009','0000000010','0000000092')), "
             //+"(select username from lduser where usercode = (select  defaultoperator from lwmission where  (missionprop1) = trim(edoracceptno) and activityid in('0000000001','0000000002','0000000004','0000000005','0000000007','0000000009','0000000010','0000000092'))), "
             +"'' i,'' j,"
             +"(select edorname from lmedoritem where appobj in('I','B') and rownum = 1 and edorcode = (select edortype  from lpedoritem where edoracceptno = a.edoracceptno and rownum = 1)) k,"
             +"makedate l,maketime m,trim(a.managecom)||'-'||(select name from ldcom where comcode = a.managecom) n  "
             +" from LPEdorApp a "
             +" Where 1 = 1   "
             + " and OtherNoType in ('1','3')"//Add By QianLy on 2006-12-11
             + getWherePart('EdorAcceptNo','EdorAcceptNo')
             + getWherePart('OtherNo','OtherNo')
             + getWherePart('OtherNoType','OtherNoType')
             + getWherePart('EdorAppName','EdorAppName')
             + getWherePart('ManageCom','ManageCom','like')
             + getWherePart('AppType','AppType')
             + getWherePart('EdorAppDate','EdorAppDate')*/
             //+ " and managecom like '"+comCode.substring(0,6)+"%%' ";
			mySql = new SqlClass();
			mySql.setResourceName("sys.AllPBqQuerySql");
			mySql.setSqlId("AllPBqQuerySql1");
			mySql.addSubPara(fm.EdorAcceptNo.value ); 
			mySql.addSubPara(fm.OtherNo.value ); 
			mySql.addSubPara(fm.OtherNoType.value ); 
			mySql.addSubPara(fm.EdorAppName.value ); 
			mySql.addSubPara(fm.ManageCom.value ); 
			mySql.addSubPara(fm.AppType.value ); 
			mySql.addSubPara(fm.EdorAppDate.value ); 
             if(fm.EdorNo.value != null && trim(fm.EdorNo.value)!="")
             {
                //strSQL += " and exists(select 'X' from lpedoritem where edoracceptno = a.edoracceptno and edorno = '"+fm.EdorNo.value+"') ";
             	mySql = new SqlClass();
			mySql.setResourceName("sys.AllPBqQuerySql");
			mySql.setSqlId("AllPBqQuerySql2");
			mySql.addSubPara(fm.EdorAcceptNo.value ); 
			mySql.addSubPara(fm.OtherNo.value ); 
			mySql.addSubPara(fm.OtherNoType.value ); 
			mySql.addSubPara(fm.EdorAppName.value ); 
			mySql.addSubPara(fm.ManageCom.value ); 
			mySql.addSubPara(fm.AppType.value ); 
			mySql.addSubPara(fm.EdorAppDate.value ); 
			mySql.addSubPara(fm.EdorNo.value ); 
             }

             //可以利用保单号查询客户层保全
             if(fm.OtherNo.value != null && fm.OtherNo.value !="")
             {
               /* strSQL += " union ";
                strSQL +=
                " select EdorAcceptNo a,otherno b,(select codename from ldcode where codetype = 'edornotype' and  (code) =  (othernotype)) c,"
                 +" EdorAppName d,nvl(to_char(b.GetMoney),' ') e,Operator f,ConfOperator g,"
                 +" decode((select count(*) from lwmission where missionprop1=B.edoracceptno and activityid='0000000007'),0,( decode((select count(*) from lwmission where missionprop1=b.edoracceptno and activityid='0000000005'),0,( select CodeName from LDCode where codetype = 'edorstate' and trim(code) =  (edorstate)),'人工核保中')),'审批中') h,"
                 //+"(select codename from ldcode where codetype = 'bqactivityname' and code = (select activityid from lwmission where  (missionprop1) = trim(edoracceptno) and activityid in('0000000001','0000000002','0000000004','0000000005','0000000007','0000000009','0000000010','0000000092'))), "
                 //+"(select username from lduser where usercode = (select  defaultoperator from lwmission where  (missionprop1) = trim(edoracceptno) and activityid in('0000000001','0000000002','0000000004','0000000005','0000000007','0000000009','0000000010','0000000092'))), "
                 +"'' i,'' j,"
                 +"(select edorname from lmedoritem where appobj in('I','B') and rownum = 1 and edorcode = (select edortype  from lpedoritem where edoracceptno = b.edoracceptno and rownum = 1)) k,"
                 +"makedate l,maketime m,trim(b.managecom)||'-'||(select name from ldcom where comcode = b.managecom) n "
                 +" from LPEdorApp b "
                 +" Where 1 = 1   "
                 + " and OtherNoType in ('1','3')"//Add By QianLy on 2006-12-11
                 + getWherePart('EdorAcceptNo','EdorAcceptNo')
                 + getWherePart('EdorAppName','EdorAppName')
                 + getWherePart('OtherNoType','OtherNoType')
                 + getWherePart('ManageCom', 'ManageCom', 'like')
                 + getWherePart('ManageCom', 'LoginManageCom', 'like')
                 + getWherePart('AppType','AppType')
                 + getWherePart('EdorAppDate','EdorAppDate')*/
                 mySql = new SqlClass();
				mySql.setResourceName("sys.AllPBqQuerySql");
				mySql.setSqlId("AllPBqQuerySql3");
				mySql.addSubPara(fm.EdorAcceptNo.value ); 
				mySql.addSubPara(fm.OtherNo.value ); 
				mySql.addSubPara(fm.OtherNoType.value ); 
				mySql.addSubPara(fm.EdorAppName.value ); 
				mySql.addSubPara(fm.ManageCom.value ); 
				mySql.addSubPara(fm.AppType.value ); 
				mySql.addSubPara(fm.EdorAppDate.value ); 
				mySql.addSubPara(fm.EdorNo.value ); 
				
				mySql.addSubPara(fm.EdorAcceptNo.value ); 
				mySql.addSubPara(fm.EdorAppName.value ); 
				mySql.addSubPara(fm.OtherNoType.value ); 
				mySql.addSubPara(fm.ManageCom.value ); 
				mySql.addSubPara(fm.LoginManageCom.value ); 
				mySql.addSubPara(fm.AppType.value ); 
				mySql.addSubPara(fm.EdorAppDate.value ); 
                 //+ " and managecom like '"+comCode.substring(0,6)+"%%' ";

                 if(fm.EdorNo.value != null && trim(fm.EdorNo.value)!="")
                 {
                  //  strSQL += " and exists(select 'X' from lpedoritem where edoracceptno = b.edoracceptno and edorno = '"+fm.EdorNo.value+"') ";
                 mySql = new SqlClass();
				mySql.setResourceName("sys.AllPBqQuerySql");
				mySql.setSqlId("AllPBqQuerySql4");
				mySql.addSubPara(fm.EdorAcceptNo.value ); 
				mySql.addSubPara(fm.OtherNo.value ); 
				mySql.addSubPara(fm.OtherNoType.value ); 
				mySql.addSubPara(fm.EdorAppName.value ); 
				mySql.addSubPara(fm.ManageCom.value ); 
				mySql.addSubPara(fm.AppType.value ); 
				mySql.addSubPara(fm.EdorAppDate.value ); 
				mySql.addSubPara(fm.EdorNo.value ); 
				
				mySql.addSubPara(fm.EdorAcceptNo.value ); 
				mySql.addSubPara(fm.EdorAppName.value ); 
				mySql.addSubPara(fm.OtherNoType.value ); 
				mySql.addSubPara(fm.ManageCom.value ); 
				mySql.addSubPara(fm.LoginManageCom.value ); 
				mySql.addSubPara(fm.AppType.value ); 
				mySql.addSubPara(fm.EdorAppDate.value ); 
				mySql.addSubPara(fm.EdorNo.value ); 
                 }
                // strSQL += " and exists(select 'X' from lpedormain where edoracceptno = b.edoracceptno and contno = '"+fm.OtherNo.value+"') ";
				 mySql = new SqlClass();
				mySql.setResourceName("sys.AllPBqQuerySql");
				mySql.setSqlId("AllPBqQuerySql5");
				mySql.addSubPara(fm.EdorAcceptNo.value ); 
				mySql.addSubPara(fm.OtherNo.value ); 
				mySql.addSubPara(fm.OtherNoType.value ); 
				mySql.addSubPara(fm.EdorAppName.value ); 
				mySql.addSubPara(fm.ManageCom.value ); 
				mySql.addSubPara(fm.AppType.value ); 
				mySql.addSubPara(fm.EdorAppDate.value ); 
				mySql.addSubPara(fm.EdorNo.value ); 
				
				mySql.addSubPara(fm.EdorAcceptNo.value ); 
				mySql.addSubPara(fm.EdorAppName.value ); 
				mySql.addSubPara(fm.OtherNoType.value ); 
				mySql.addSubPara(fm.ManageCom.value ); 
				mySql.addSubPara(fm.LoginManageCom.value ); 
				mySql.addSubPara(fm.AppType.value ); 
				mySql.addSubPara(fm.EdorAppDate.value ); 
				mySql.addSubPara(fm.EdorNo.value );
				mySql.addSubPara(fm.OtherNo.value );  

             }
            // strSQL += " ) order by l,m desc ";
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
    //查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);
  
  turnPage1.queryModal(mySql.getString(), PolGrid);
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
      PolGrid.clearData();
      alert("查询失败！");
      return false;
  }

  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

  //设置初始化过的MULTILINE对象
  turnPage.pageDisplayGrid = PolGrid;

  //保存SQL语句
  turnPage.strQuerySql     = strSQL;

  //设置查询起始位置
  turnPage.pageIndex = 0;

  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  arrGrid = arrDataSet;
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  //给操作岗位、操作人赋值；注意此处只是赋一页的值，所以要在翻页按钮处添加相同的函数
  queryLWMission();
}
//查询操作岗位、操作人
function queryLWMission()
{
  var i = 0;
  for(i;i<PolGrid.mulLineCount;i++)
  {
    var tEdorAcceptNo = PolGrid.getRowColData(i,1);
   /* strSQL = "select (select codename from ldcode where codetype = 'bqactivityname' and code = activityid),"
           + " (select username from lduser where usercode = lastoperator) "
           + " from lwmission where 1=1 "
           + " and exists(select 'X' from ldcode where codetype = 'bqactivityname' and othersign = 'mainnote' and code = activityid) "
           + " and missionprop1 = '"+tEdorAcceptNo+"' ";*/
    mySql = new SqlClass();
				mySql.setResourceName("sys.AllPBqQuerySql");
				mySql.setSqlId("AllPBqQuerySql6");
				mySql.addSubPara(tEdorAcceptNo);  
    var brr = easyExecSql(mySql.getString(), 1, 0,"","",1);//注意此处第6个参数应设为1，不使用翻页功能，否则可能会把全局变量turnPage覆盖
    if(brr)
    {
         PolGrid.setRowColData(i, 9, brr[0][0]);
         PolGrid.setRowColData(i, 10, brr[0][1]);
    }
  }
}

//查询按钮
function QueryClick()
{
    initPolGrid();
    document.all('Transact').value ="QUERY|EDOR";
  var i = 0;
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//  showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;"); 
  var name='提示';   //网页名称，可为空; 
  var iWidth=550;      //弹出窗口的宽度; 
  var iHeight=250;     //弹出窗口的高度; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();

  document.getElementById("fm").submit();//提交
}



// 项目明细查询
function ItemQuery()
{
    var arrReturn = new Array();
    var tSel = PolGrid.getSelNo();

    if( tSel == 0 || tSel == null )
        alert( "请先选择一条记录，再点击明细查询按钮。" );
    else
    {
        var cEdorNo = PolGrid.getRowColData(tSel - 1,1);
        var cSumGetMoney =  PolGrid.getRowColData(tSel - 1,5);
        var cPolNo = PolGrid.getRowColData(tSel - 1,2);

        if (cEdorNo == ""||cPolNo=="")
            return;

        window.open("../sys/AllPBqItemQueryMain.jsp?EdorNo=" + cEdorNo + "&SumGetMoney=" + cSumGetMoney+ "&PolNo=" + cPolNo,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");

    }
}


    //打印批单
function PrtEdor()
{
    var arrReturn = new Array();

        var tSel = PolGrid.getSelNo();


        if( tSel == 0 || tSel == null )
            alert( "请先选择一条记录，再点击查看按钮。" );
        else
        {
            var state =PolGrid. getRowColData(tSel-1,8) ;

            if (state=="正在申请")
                alert ("所选批单处于正在申请状态，不能打印！");
            else{
              var EdorAcceptNo=PolGrid. getRowColData(tSel-1,1) ;

                //var tEdorAcceptNo = SelfGrid.getRowColData(selno, 1);
                //var tMissionID = SelfGrid.getRowColData(selno, 5);
                //var tSubMissionID = SelfGrid.getRowColData(selno, 6);
                //var tLoadFlag = "edorApp";

                var varSrc="&EdorAcceptNo=" + EdorAcceptNo;
                var newWindow = OpenWindowNew("../sys/BqDetailQueryFrame.jsp?Interface= ../sys/BqDetailQuery.jsp" + varSrc,"保全查询明细","left");
                //window.open("./BqDetailQuery.jsp?EdorAcceptNo="+EdorAcceptNo+"");
                //fm.target="f1print";

            //  document.getElementById("fm").submit();}
            }
        }
}


function PBqQueryClick()
{
var selno = PolGrid.getSelNo()-1;
var tOtherNoType  = PolGrid.getRowColData(selno, 3);


}

function getQueryResult()
{
    var arrSelected = null;
    tRow = PolGrid.getSelNo();
    if( tRow == 0 || tRow == null || arrGrid == null )
        return arrSelected;
    arrSelected = new Array();
    //设置需要返回的数组
    //arrSelected[0] = arrGrid[tRow-1];
    arrSelected[0] = PolGrid.getRowData(tRow-1);
    //alert(arrSelected[0][0]);
    return arrSelected;
}

function gotoDetail()
{
    var selno = PolGrid.getSelNo()-1;
    if (selno <0)
    {
          alert("请选择要处理的任务！");
          return;
    }

    var tEdorAcceptNo = PolGrid.getRowColData(selno, 1);
  var tOtherNoType  = PolGrid.getRowColData(selno, 3);

    var varSrc="&EdorAcceptNo=" + tEdorAcceptNo+"&OtherNoType="+tOtherNoType;
    var newWindow = OpenWindowNew("../sys/BqDetailQueryFrame.jsp?Interface= ../sys/BqDetailQuery.jsp" + varSrc,"保全查询明细","left");

}
//保全操作轨迹
function MissionQuery()
{
    var selno = PolGrid.getSelNo()-1;
    if (selno <0)
    {
          alert("请选择要处理的任务！");
          return;
    }
    var EdorAcceptNo = PolGrid.getRowColData(selno, 1);
   // var strSql = "select missionid from lbmission where missionprop1 = '"+EdorAcceptNo+"' union select missionid from lwmission where missionprop1 = '"+EdorAcceptNo+"' ";
     mySql = new SqlClass();
				mySql.setResourceName("sys.AllPBqQuerySql");
				mySql.setSqlId("AllPBqQuerySql7");
				mySql.addSubPara(EdorAcceptNo);  
				mySql.addSubPara(EdorAcceptNo);  
    var brr =  easyExecSql(mySql.getString());
    if(!brr)
    {
       alert("该保全受理轨迹信息不存在！");
       return;
    }
    var pMissionID = brr[0][0];
    window.open("../bq/EdorMissionFrame.jsp?MissionID="+pMissionID,"window3","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}
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
				mySql.setResourceName("sys.AllPBqQuerySql");
				mySql.setSqlId("AllPBqQuerySql8");
				mySql.addSubPara(EdorAcceptNo);  
				mySql.addSubPara(EdorAcceptNo); 
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

function queryNotice(){
	
	  var selno = PolGrid.getSelNo()-1;
    if (selno <0)
    {
          alert("请选择要处理的任务！");
          return;
    }
    var EdorAcceptNo = PolGrid.getRowColData(selno, 1);
    if(EdorAcceptNo == null ||trim(EdorAcceptNo) == ""){
    	alert("请先查询！");
    	return;	
    }
    var newWindow = OpenWindowNew("../bq/EdorNoticeQuery.jsp?EdorAcceptNo="+ EdorAcceptNo,"保全问题件查询","left");
}

//function initManageCom()
//{
//  if(comCode.substring(0,6) !=null && comCode.substring(0,6) != "")
//  {
//      var i,j,m,n;
//      var returnstr;
//      var tTurnPage = new turnPageClass();
//      var strSQL = "select comcode,name from ldcom where comcode like '"+comCode.substring(0,6)+"%%'";
//      tTurnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);
//        if (tTurnPage.strQueryResult == "")
//        {
//          return "";
//        }
//        tTurnPage.arrDataCacheSet = decodeEasyQueryResult(tTurnPage.strQueryResult);
//        var returnstr = "";
//        var n = tTurnPage.arrDataCacheSet.length;
//        if (n > 0)
//        {
//          for( i = 0;i < n; i++)
//          {
//                  m = tTurnPage.arrDataCacheSet[i].length;
//              if (m > 0)
//                  {
//                      for( j = 0; j< m; j++)
//                      {
//                          if (i == 0 && j == 0)
//                          {
//                              returnstr = "0|^"+tTurnPage.arrDataCacheSet[i][j];
//                          }
//                          if (i == 0 && j > 0)
//                          {
//                              returnstr = returnstr + "|" + tTurnPage.arrDataCacheSet[i][j];
//                          }
//                          if (i > 0 && j == 0)
//                          {
//                              returnstr = returnstr+"^"+tTurnPage.arrDataCacheSet[i][j];
//                          }
//                          if (i > 0 && j > 0)
//                          {
//                              returnstr = returnstr+"|"+tTurnPage.arrDataCacheSet[i][j];
//                          }
//                  }
//                  }
//                  else
//                  {
//                      alert("查询失败!!");
//                      return "";
//                  }
//             }
//         }
//         else
//         {
//           alert("查询失败!");
//           return "";
//         }
//         fm.ManageCom.CodeData = returnstr;
//         return returnstr;
//  }
//}
//}