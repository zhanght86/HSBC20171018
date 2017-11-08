//程序名称：WFEdorTest.js
//程序功能：保全工作流-保全试算申请
//创建日期：2005-11-10 12:13:22
//创建人  ：zhangtao
//更新记录：  更新人    更新日期     更新原因/内容
//

var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();


/*********************************************************************
 *  查询我的任务队列
 *  描述: 查询我的任务队列
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function easyQueryClickSelf()
{
    // 初始化表格
    initSelfGrid();

    // 书写SQL语句
    var strSQL = "";
    
    var sqlid1="DSHomeContSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("bq.WFGrpEdorTestInputSql");//指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(fm.EdorAcceptNo_ser.value);//指定传入的参数
		mySql1.addSubPara(fm.ManageCom_ser.value);//指定传入的参数
		mySql1.addSubPara(fm.OtherNo.value);//指定传入的参数
		mySql1.addSubPara(fm.OtherNoType.value);//指定传入的参数
		mySql1.addSubPara(fm.EdorAppName.value);//指定传入的参数
		mySql1.addSubPara(fm.AppType.value);//指定传入的参数
		mySql1.addSubPara(fm.MakeDate.value);//指定传入的参数
		strSQL=mySql1.getString();

    
//    strSQL =  " select missionprop1, missionprop7, "
//            + " (select codename from ldcode d1 where trim(d1.codetype) = 'edornotype' and trim(d1.code) = trim(missionprop3)), "
//            + " MissionProp11, MissionProp12, " 
//            + " (select codename from ldcode d2 where trim(d2.codetype) = 'edorapptype' and trim(d2.code) = trim(missionprop5)), " 
//            + " (select codename from ldcode d3 where trim(d3.codetype) = 'station' and trim(d3.code) = trim(missionprop2)), "
//            + " createoperator, makedate, "
//            + " missionid, submissionid, activityid "
//            + " from lwmission where 1=1  "
//            + " and activityid = '0000008092' "
//            + " and processid = '0000000000' "
//            + " and defaultoperator ='" + operator + "'"
//            + " and missionid not in (select missionid from lwmission where activityid = '0000000000')"
//            + getWherePart('missionprop1', 'EdorAcceptNo_ser')
//            + getWherePart('missionprop2', 'ManageCom_ser') 
//            + getWherePart('missionprop7', 'OtherNo')   
//            + getWherePart('missionprop3', 'OtherNoType')   
//            + getWherePart('missionprop4', 'EdorAppName')   
//            + getWherePart('missionprop5', 'AppType')   
//            + getWherePart('MakeDate', 'MakeDate')  
//            + " order by MakeDate, MakeTime ";      
    turnPage2.queryModal(strSQL, SelfGrid);
    
    return true;
}

/*********************************************************************
 *  跳转到具体的业务处理页面
 *  描述: 跳转到具体的业务处理页面
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function GoToBusiDeal()
{
    var selno = SelfGrid.getSelNo()-1;
    if (selno <0)
    {
          alert("请选择要试算的任务！");
          return;
    }   

    var tEdorAcceptNo = SelfGrid.getRowColData(selno, 1);
    var tMissionID = SelfGrid.getRowColData(selno, 10);
    var tSubMissionID = SelfGrid.getRowColData(selno, 11);
    var tLoadFlag = "edorTest";
    
    var varSrc="&EdorAcceptNo=" + tEdorAcceptNo + "&MissionID=" + tMissionID + "&SubMissionID=" + tSubMissionID + "&LoadFlag=" + tLoadFlag;
    var newWindow = OpenWindowNew("../bq/GEdorTestFrame.jsp?Interface= ../bq/GEdorAppInput.jsp" + varSrc,"团单保全试算申请","left");    
}

/*********************************************************************
 *  申请任务
 *  描述: 申请任务
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function applyMission()
{
	fm.Transact.value = "EDORTEST||APPLY";
    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    
    fm.action = "../bq/WFGrpEdorTestSave.jsp";
    document.getElementById("fm").submit(); //提交
}

/*********************************************************************
 *  后台执行完毕反馈信息
 *  描述: 后台执行完毕反馈信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
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

    if (FlagStr == "Succ" ) 
    {
    	if (fm.Transact.value == "EDORTEST||APPLY")
    	{
            //直接进入保全受理界面
            var tEdorAcceptNo = fm.EdorAcceptNo.value;
            var tMissionID;
            var tSubMissionID;
                        
            var strSQL;
            
            var sqlid2="DSHomeContSql2";
						var mySql2=new SqlClass();
						mySql2.setResourceName("bq.WFGrpEdorTestInputSql");//指定使用的properties文件名
						mySql2.setSqlId(sqlid2);//指定使用的Sql的id
						mySql2.addSubPara(tEdorAcceptNo);//指定传入的参数
						strSQL=mySql2.getString();

        
//            strSQL =  " select  missionid,submissionid " 
//                    + "   from  lwmission " 
//                    + "  where  activityid = '0000008092' " 
//                    + " and missionprop1 = '" + tEdorAcceptNo + "'";
            var brr = easyExecSql(strSQL);
            
            if ( brr )
            {
                //alert("已经申请保存过");
                brr[0][0]==null||brr[0][0]=='null'?'0':tMissionID    = brr[0][0];
                brr[0][1]==null||brr[0][1]=='null'?'0':tSubMissionID = brr[0][1];
            }
            else            
            {
                alert("工作流任务查询失败！");
                return;
            }

            var tLoadFlag = "edorTest";
            
            var varSrc="&EdorAcceptNo=" + tEdorAcceptNo + "&MissionID=" + tMissionID + "&SubMissionID=" + tSubMissionID + "&LoadFlag=" + tLoadFlag;
            var newWindow = OpenWindowNew("../bq/GEdorTestFrame.jsp?Interface= ../bq/GEdorAppInput.jsp" + varSrc,"团单保全试算","left");    
        }
        if (fm.Transact.value == "EDORTEST||FINISH")
        {
        	initForm();
        }
    }

}


/*********************************************************************
 *  试算完毕
 *  描述: 试算完毕提交后台清除试算数据
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function EdorTestFinish()
{
    fm.Transact.value = "EDORTEST||FINISH";
    
    var selno = SelfGrid.getSelNo()-1;
    if (selno <0)
    {
          alert("请选择一条任务");
          return;
    }
    
    fm.MissionID.value    = SelfGrid.getRowColData(selno, 10);
    fm.SubMissionID.value = SelfGrid.getRowColData(selno, 11);
    fm.ActivityID.value   = SelfGrid.getRowColData(selno, 12);
    fm.EdorAcceptNo.value = SelfGrid.getRowColData(selno, 1);
	var OtherNo =  SelfGrid.getRowColData(selno, 2);
	if (OtherNo == null || OtherNo == "")
	{
		alert("该条任务尚未试算,不用撤销!");
		return;
	}
    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();    
    fm.action= "./GEdorTestFinishSubmit.jsp";
    document.getElementById("fm").submit();
}
