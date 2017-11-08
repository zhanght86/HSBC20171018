//程序名称：UWBack.js
//程序功能：核保订正
//创建日期：2003-03-27 15:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容

//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug = "1";
var turnPage = new turnPageClass();
var k = 0;
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
//提交，保存按钮对应操作
function submitForm()
{
    var i = 0;
    var showStr = "正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    initPolGrid();
    fm.submit();
    //提交
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit(FlagStr, content)
{
	unlockScreen('lkscreen');  
    showInfo.close();
    if (FlagStr == "Fail")
    {
        var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content ;

        alert(content);

    }
    else
    {

        var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
        alert(content);

    }

    initForm();
    //easyQueryClick();
}

//显示div，第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
function showDiv(cDiv, cShow)
{
    if (cShow == "true")
    {
        cDiv.style.display = "";
    }
    else
    {
        cDiv.style.display = "none";
    }
}


// 查询按钮
function easyQueryClick()
{
    k++;
    var strSQL = "";
    var strOperate = "like";
    /*
     strSQL = "select ProposalNo,PrtNo,RiskCode,RiskVersion,AppntName,InsuredName from LCPol where "+k+" = "+k
                  + " and VERIFYOPERATEPOPEDOM(LCPol.Riskcode,LCPol.Managecom,'"+comcode+"','Pg')=1 "
                  + " and AppFlag='0'"          //承保
                  + " and UWFlag in ('1','2','4','9','a')"  //核保最终状态
                  + " and LCPol.ApproveFlag = '9' "
                  + " and grppolno = '00000000000000000000'"
                  + " and contno = '00000000000000000000'"
                  + " and polno = mainpolno"
                  + getWherePart( 'ProposalNo' )
                  + getWherePart( 'ManageCom' )
                  + getWherePart( 'AgentCode' )
                  + getWherePart( 'AgentGroup' )
                  + getWherePart( 'RiskCode' )
                  + getWherePart( 'PrtNo' )
                  + " and ManageCom like '" + comcode + "%%'";
                 */
				
		var sqlid1="UWBackSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("uw.UWBackSql"); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(fm.ProposalNo.value);//指定传入的参数
		mySql1.addSubPara(fm.ManageCom.value);//指定传入的参数
		mySql1.addSubPara(fm.AgentCode.value);//指定传入的参数
		mySql1.addSubPara(fm.AgentGroup.value);//指定传入的参数
		mySql1.addSubPara(comcode);//指定传入的参数
	    strSQL=mySql1.getString();				
				
//    strSQL = "select lwmission.missionprop1,lwmission.missionprop5,lwmission.missionprop7,lwmission.missionprop2,lwmission.Missionid,lwmission.submissionid,(select username from lduser where trim(usercode)=lwmission.missionprop8) from lwmission where 1=1"
//            + " and activityid = '0000001149'"
//            + " and processid = '0000000003'"
//            + getWherePart('lwmission.MissionProp1', 'ProposalNo', strOperate)
//            + getWherePart('lwmission.MissionProp2', 'ManageCom', strOperate)
//            + getWherePart('lwmission.MissionProp3', 'AgentCode', strOperate)
//            + getWherePart('lwmission.MissionProp4', 'AgentGroup', strOperate)
//            + " and MissionProp2 like '" + comcode + "%%'"
//            ;
    //alert(strSQL);
    //查询SQL，返回结果字符串
    turnPage.strQueryResult = easyQueryVer3(strSQL, 1, 0, 1);

    //判断是否查询成功
    if (!turnPage.strQueryResult) {
        //alert(turnPage.strQueryResult);
        alert("没有待核保个人单！");
        return "";
    }

    //查询成功则拆分字符串，返回二维数组
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

    //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
    turnPage.pageDisplayGrid = PolGrid;

    //保存SQL语句
    turnPage.strQuerySql = strSQL;

    //设置查询起始位置
    turnPage.pageIndex = 0;

    //在查询结果数组中取出符合页面显示大小设置的数组
    var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
    //alert(arrDataSet);
    //调用MULTILINE对象显示查询结果
    displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
    return true;
}

function displayEasyResult(arrResult)
{
    var i, j, m, n;

    if (arrResult == null)
        alert("没有找到相关的数据!");
    else
    {
        // 初始化表格
        initPolGrid();

        arrGrid = arrResult;
        // 显示查询结果
        n = arrResult.length;
        for (i = 0; i < n; i++)
        {
            m = arrResult[i].length;
            for (j = 0; j < m; j++)
            {
                PolGrid.setRowColData(i, j + 1, arrResult[i][j]);
            }
            // end of for
        }
        // end of for
        //alert("result:"+arrResult);
    }
    // end of if
}

function back()
{
    //由于在前台校验有困难，故在后台进行校验。
    //  //判断是否录入核保订正原因

    //  alert(trim(fm.UWIdea.value).length);
    //  alert("_"+trim(fm.UWIdea.value)+"_");
    //  if(trim(fm.UWIdea.value)==""){
    //      alert("请录入核保订正原因！");
    //      return;
    //  }


    var i = 0;
    var showStr = "正在传送数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
lockScreen('lkscreen');  
    //showSubmitFrame(mDebug);
    fm.submit();
    //提交
}

/*********************************************************************
 *  显示frmSubmit框架，用来调试
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showSubmitFrame(cDebug)
{
    if (cDebug == "1")
        parent.fraMain.rows = "0,0,50,82,*";
    else
        parent.fraMain.rows = "0,0,0,72,*";
}


//选保单时候记录投保单号
// 查询按钮
function easyQueryAddClick(param1, parm2)
{
    var tProposalNo = "";
    var tPrtNo = "";
    var tMission = "";
    var tSubMission = "";
    var tInsuredName = "";
    var tAppntName = "";
	
	var selectRowNum = param1.replace(/spanPublicWorkPoolGrid/g,"");
	
    if (document.all('InpPublicWorkPoolGridSel'+selectRowNum).value == '1')
    {
        //当前行第1列的值设为：选中
        tProposalNo = document.all('PublicWorkPoolGrid5'+'r'+selectRowNum).value;
        tPrtNo = document.all('PublicWorkPoolGrid1'+'r'+selectRowNum).value;
        tAppntName = document.all('PublicWorkPoolGrid2'+'r'+selectRowNum).value;
        
        var sqlid3="UWBackSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("uw.UWBackSql"); //指定使用的properties文件名
		mySql3.setSqlId(sqlid3);//指定使用的Sql的id
		mySql3.addSubPara(tPrtNo);
		strSQL=mySql3.getString();	
		
        tInsuredName = easyExecSql(strSQL);
        tMission = document.all('PublicWorkPoolGrid11'+'r'+selectRowNum).value;
        tSubMission = document.all('PublicWorkPoolGrid12'+'r'+selectRowNum).value;
    }

    if (tProposalNo == "")
    {
        alert("请选择投保单！");
    }
    
	
    fm.ActivityID.value = document.all('PublicWorkPoolGrid14'+'r'+selectRowNum).value;
    fm.ProposalNoHide.value = tProposalNo;
    fm.PrtNo.value = tPrtNo;
    fm.AppntName.value = tAppntName;
    fm.InsuredName.value = tInsuredName;
    fm.MissionId.value = tMission;
    fm.SubMissionId.value = tSubMission;
    divUWIdea.style.display = "";

    return true;
}

//查询代理人的方式
function queryAgent(comcode)
{
	//alert("ok");
  if(document.all('AgentCode').value == "")	
  {  
		var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+comcode,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	  
  }
  
	if(document.all('AgentCode').value != "")	 
	{
	var cAgentCode = fm.AgentCode.value;  //代理人编码	
	
		var sqlid2="UWBackSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("uw.UWBackSql"); //指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
		mySql2.addSubPara(cAgentCode);//指定传入的参数
	    var strSql=mySql2.getString();		
	
//	var strSql = "select AgentCode,Name from LAAgent where AgentCode='" + cAgentCode +"'";// and ManageCom = '"+document.all('ManageCom').value+"'";
    var arrResult = easyExecSql(strSql);
    //alert(arrResult);
    if (arrResult != null) 
    {
			alert("查询结果:  代理人编码:["+arrResult[0][0]+"] 代理人名称为:["+arrResult[0][1]+"]");
    }
    else
    {
			
			alert("编码为:["+document.all('AgentCode').value+"]的代理人不存在，请确认!");
    }
	}	
}

