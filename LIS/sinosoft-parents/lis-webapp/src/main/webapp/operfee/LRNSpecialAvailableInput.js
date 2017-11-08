/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : 辛玉强 <XinYQ@sinosoft.com.cn>
 * @version  : 1.00, 1.01, 1.02, 1.03, 1.04
 * @date     : 2005-11-29, 2005-12-03, 2006-02-15, 2006-02-28, 2006-03-23
 * @direction: 保单特殊复效录入主脚本
/*============================================================================*/

var showInfo;                          //全局变量, 弹出提示窗口, 必须有
var turnPage = new turnPageClass();    //全局变量, 查询结果翻页, 必须有
var isThisSaved = false;               //全局变量, 标记是否已成功保存过

/*============================================================================*/
/**
 * 查询失效合同信息和险种队列
 */
function getAvailableInfoGrid()
{
    try
    {
        queryAvailableInfo();
        queryAvailableGrid();
    }
    catch (ex) {}
    if (AvailableGrid.mulLineCount > 0) //如果查询结果不为空,允许另一次保存
    {
        isThisSaved = false;
    }
}
/*============================================================================*/
/**
 * 查询失效合同信息
 */
function queryAvailableInfo()
{
    var sContNo = document.getElementsByName("ContNo_srh")[0].value; 
    if (sContNo == null || trim(sContNo) == "")
    {
        alert("请输入您要进行特殊复效的合同号以查询！ ");
        return;
    }
    var QuerySQL, arrResult;
    //QuerySQL = "select  a.ContNo, a.PayToDate x, "
    //		 + " (select  StartDate from LCContState where PolNo = a.PolNo "
    //         + " and StateType ='Available' and State = '1' and EndDate is null and contno='"+sContNo+"' and statereason in ('01','02','03','04')), "
    //         + " (select  max(EnterAccDate) from LJAPayPerson  where ContNo = a.ContNo and contno='"+sContNo+"') "
    //         + " from LCPol a "
    //         + " where polno=mainpolno and appflag='1' and conttype='1' and payintv > 0 " //1- 主险长险失效
    //         + getWherePart("a.ContNo", "ContNo_srh")
    //         + getWherePart("a.ManageCom", "ManageCom", "like")
    //         + " and exists "
    //         + " (select 'X' from LCContState where contno = a.contno and polno=a.polno and StateType ='Available' "
    //         +" and State = '1' and EndDate is null and statereason in ('01','02','03','04') ) "
    //         //增加校验，长险lccont.appflag='1'才能进行复效
    //         +" and exists(select 1 from lccont where contno=a.contno and appflag='1')"
    //         +" union "
    //         + " select * from ("
    //         + "select distinct a.ContNo, a.PayToDate x, "
    //		 + " (select  StartDate from LCContState where  PolNo = a.PolNo "
    //         + " and StateType ='Terminate' and State = '1' and EndDate is null and contno='"+sContNo+"' and statereason in ('01','07')), "
    //         + " (select  max(EnterAccDate) from LJAPayPerson  where ContNo = a.ContNo and contno='"+sContNo+"') "
    //         + " from LCPol a "
    //         + " where polno=mainpolno and appflag='4' and conttype='1' and payintv = 0 " //4- 主险续保终止
    //         + getWherePart("a.ContNo", "ContNo_srh")
    //         + getWherePart("a.ManageCom", "ManageCom", "like")
    //         +" and not exists(select 1 from lcpol b where b.contno=a.contno and b.polno=b.mainpolno and b.appflag='1') "
    //         + " and exists "
    //         + " (select 'X' from LCContState where contno = a.contno and polno=a.polno and StateType ='Terminate' "
    //         +" and State = '1' and EndDate is null and statereason in ('01','07')) "
    //         + " order by x desc )where rownum <= 1 ";       
    
    
    QuerySQL=wrapSql('LCPol1',[sContNo,sContNo,sContNo,document.all("ManageCom").value,
    													 sContNo,sContNo,sContNo,document.all("ManageCom").value]);
    try
    {
        arrResult = easyExecSql(QuerySQL, 1, 0);
    }catch (ex)
    {
        alert("警告：查询失效合同信息出现异常！ ");
        return;
    }
    if (arrResult != null)
    {
        try
        {
            document.getElementsByName("ContNo")[0].value = arrResult[0][0];
            document.getElementsByName("LastPayToDate")[0].value = arrResult[0][1];
            document.getElementsByName("UnAvailableDate")[0].value = arrResult[0][2];
            document.getElementsByName("LastEnterAccDate")[0].value = arrResult[0][3];
        }
        catch (ex) {}
    }
    else
    {
        alert("没有符合条件的失效合同纪录！ ");
      	return;
    }
   // alert("最近交至日期"+document.all('LastPayToDate').value);
}
/*============================================================================*/

/**
 * 查询失效险种队列
 */
function queryAvailableGrid()
{
    var sContNo, sUnAvailableDate;
    try
    {
        sContNo = document.getElementsByName("ContNo")[0].value;
        sUnAvailableDate = document.getElementsByName("UnAvailableDate")[0].value;
    }
    catch (ex) {}
    if (sContNo == null || trim(sContNo) == "" || sUnAvailableDate == null || trim(sUnAvailableDate) == "")
    {
        return;
    }
    //var QuerySQL = "select a.RiskCode, "
    //             + "(select distinct RiskName from LMRisk where RiskCode = a.RiskCode), "
    //             + "a.InsuredNo, a.InsuredName, a.CValiDate, "
    //             + "case a.AppFlag when '1' then '失效' else '失效终止' end, "
    //             + "(select codename from ldcode c,lccontstate b where 1 = 1 and c.codetype = 'contavailablereason' "
    //             +" and b.statereason=c.code and b.statetype ='Available' and b.polno=a.polno  and b.state='1' and b.statereason in ('01','02','03','04') and b.enddate is null "
    //             +"  union "
    //             +" select codename from ldcode c,lccontstate b where 1 = 1 and c.codetype = 'contterminatereason' "
    //             +" and b.statereason=c.code and b.statetype ='Terminate' and b.polno=a.polno  and b.state='1' and b.statereason in ('01','07') and b.enddate is null )"
    //             + "from LCPol a  where 1 = 1 and a.appflag in ('1','4','9') and a.paytodate='"+document.all('LastPayToDate').value+"'"
    //             +  getWherePart("a.ContNo", "ContNo")
    //             +  "order by a.riskcode,a.cvalidate asc";  
    
    	var QuerySQL = wrapSql('LCPol2',[document.all('LastPayToDate').value,sContNo]);     
    try
    {
        turnPage.queryModal(QuerySQL, AvailableGrid);
    }           
    catch (ex)
    {
        alert("警告：查询失效险种信息出现异常！ ");
        return;
    }
    //var reason_SQL =  "select codename from lcpol a,ldcode c,lccontstate b where a.polno=a.mainpolno and a.polno=b.polno  "
    //           +" and b.statereason=c.code and  c.codetype = 'contavailablereason'  and b.statetype ='Available'"
    //           +" and b.state='1' and b.statereason in ('01','02','03','04') and b.enddate is null "
    //           +  getWherePart("a.ContNo", "ContNo")
    //           + " union  "           
    //           +" select codename from lcpol a,ldcode c,lccontstate b where a.polno=a.mainpolno and a.polno=b.polno  "
    //           +" and b.statereason=c.code and  c.codetype = 'contterminatereason'  and b.statetype ='Terminate' "
    //           +" and b.state='1' and b.statereason in ('01','07')   and b.enddate is null "
    //           +  getWherePart("a.ContNo", "ContNo")
    //           ;
    //prompt("",reason_SQL);         
    
     var reason_SQL = wrapSql('LDCode1',[sContNo,sContNo]);  
     
    document.all('InvalidReason').value = easyExecSql(reason_SQL, 1, 1, 1); 
    //alert("失效原因："+document.all('InvalidReason').value); 
}
/*============================================================================*/
/**
 * 保存特殊复效结果
 */
function saveRevalidateCont()
{
    if (!isHaveQueried()) return;
    if (isHaveSaved()) return;
    if (!verifyInput2()) return;
    var MsgContent = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=C&content=" + MsgContent;
    //showInfo = showModelessDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    document.forms(0).action = "LRNSpecialAvailableSave.jsp";
    document.forms(0).submit();
}
/*============================================================================*/
/**
 * 提交后操作, 服务器数据返回后执行的操作
 */
function afterSubmit(DealFlag, MsgContent)
{
    try { showInfo.close(); } catch (ex) {}
    DealFlag = DealFlag.toLowerCase();
    var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=";
    switch (DealFlag)
    {
        case "fail":
            MsgPageURL = MsgPageURL + "F&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=250px");
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
        case "succ":
        case "success":
            MsgPageURL = MsgPageURL + "S&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=350px");
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=350;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
        default:
            MsgPageURL = MsgPageURL + "C&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=300;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
    }
    //本文件的特殊处理
    if (DealFlag == "succ" || DealFlag == "success")
    {
        isThisSaved = true;
    }
}

/*============================================================================*/

/**
 * 如果清空了下拉列表代码, 则同时清除已显示的下拉列表代码对应的名称
 */
function clearEmptyCode(objCodeList, objCodeListName)
{
    var sCodeList = document.getElementsByName(objCodeList.name)[0].value;
    if (sCodeList == null || sCodeList == "")
    {
        try { document.getElementsByName(objCodeListName.name)[0].value = ""; } catch (ex) {}
    }
}
/*============================================================================*/

/**
 * 综合查询
 * add by guanwei 2006-3-22
 */
function gotoMultipleQuery()
{
    var sContNo;
    try { sContNo = document.getElementsByName("ContNo_srh")[0].value; } catch (ex) {}
    if (sContNo == null || trim(sContNo) == "")
    {
        alert("请先输入您要进行综合查询的合同号！ ");
        return;
    }
    else
    {
        window.open("../sys/PolDetailQueryMain.jsp?ContNo=" + trim(sContNo) +"&IsCancelPolFlag=0");
    }
}

/*============================================================================*/

/**
 * 检查是否已经查询过
 */
function isHaveQueried()
{
    var sContNo;
    try { sContNo = document.getElementsByName("ContNo")[0].value; } 
    catch (ex) {}
    if (sContNo == null || trim(sContNo) == "")
    {
        alert("请先输入合同号进行查询！ ");
        return false;
    }
    return true;
}

/*============================================================================*/

/**
 * 检查是否已经保存过
 */
function isHaveSaved()
{
    if (isThisSaved)
    {
        alert("本次操作已成功保存过。请重新查询！ ");
        return true;
    }
    return false;
}
/*============================================================================*/

/**
 * 保存成功之后清除已显示的数据
 */
function clearShowedCont()
{
    try
    {
        document.getElementsByName("ContNo")[0].value = "";
        document.getElementsByName("LastPayToDate")[0].value = "";
        document.getElementsByName("UnAvailableDate")[0].value = "";
        document.getElementsByName("LastEnterAccDate")[0].value = "";
    }
    catch (ex) {}
}

/*============================================================================*/

//<!-- JavaScript Document END -->
/**
	mysql工厂，根据传入参数生成Sql字符串
	
	sqlId:页面中某条sql的唯一标识
	param:数组类型,sql中where条件里面的参数
**/
function wrapSql(sqlId,param){
	
	var mysql=new SqlClass();
	
	mysql.setResourceName("operfee.LRNSpecialAvailableInputSql");
	mysql.setSqlId(sqlId);
	
	for(i=0;i<param.length;i++){
		 mysql.addSubPara(param[i]);
	}
	
	return mysql.getString();
	
}