/*******************************************************************************

 ******************************************************************************/

//<!-- JavaScript Document BGN -->


/*============================================================================*/
var showInfo;                                      //全局变量, 弹出提示窗口, 必须有
var turnPage = new turnPageClass();                //全局变量, 查询结果翻页, 必须有
var turnPageEdorItemGrid = new turnPageClass();    //全局变量, 既往保全批改项目信息查询结果翻页
/*============================================================================*/

/*============================================================================*/

/**
 * 客户信息查询
 */
function queryCustomerInfo()
{
    var sCustomerNo;
    var brr;
    var QuerySQL;
    try
    {
        sCustomerNo = document.all("CustomerNo").value;
    }
    catch (ex) {}
    if (sCustomerNo == null || trim(sCustomerNo) == "")
    {
        alert("无法获取客户号。查询客户信息失败！ ");
        return;
    }
    else
    {
        var sqlid826143228="DSHomeContSql826143228";
var mySql826143228=new SqlClass();
mySql826143228.setResourceName("bq.GEdorAgoQueryInputSql");//指定使用的properties文件名
mySql826143228.setSqlId(sqlid826143228);//指定使用的Sql的id
mySql826143228.addSubPara(sCustomerNo);//指定传入的参数
QuerySQL=mySql826143228.getString();
        
//        QuerySQL = "select GrpName"
//                     +  " from LDGrp"
//                     +  " where CustomerNo ='" + sCustomerNo + "'";
        try
        {
          brr = easyExecSql(QuerySQL);
          //alert(brr);
        }
        catch (ex)
        {
            alert("警告：查询客户信息出现异常！ ");
            return;
        }
        if (brr != null)
        {
           document.all("GrpName").value = brr[0][0];
        }
    }
}

/*============================================================================*/

/**
 * 既往保全批改项目信息查询
 */
function queryEdorItem()
{
	   try{
	      var customerNo = document.all("CustomerNo").value;
	   }catch(ex){}  	 
       
       	var sqlid826143338="DSHomeContSql826143338";
var mySql826143338=new SqlClass();
mySql826143338.setResourceName("bq.GEdorAgoQueryInputSql");//指定使用的properties文件名
mySql826143338.setSqlId(sqlid826143338);//指定使用的Sql的id
mySql826143338.addSubPara(customerNo);//指定传入的参数
var sql=mySql826143338.getString();
        
//        var sql = "select edoracceptno,"
//                +" edorno,"
//                +" edortype,"
//                +" grpcontno,"
//                +" edorappdate,"
//                +" edorvalidate,"
//                +" getmoney,"
//                +" getinterest"
//                +" from lpgrpedoritem p"
//                +" where '" + customerNo+ "' in "
//                +" (select appntno from lcgrpcont where grpcontno = p.grpcontno)"
//                +" and edorstate = '0'"
//                +" order by makedate asc"
//                +"";
        try
        {
            turnPageEdorItemGrid.pageDivName = "divTurnPageEdorItemGrid";
            turnPageEdorItemGrid.queryModal(sql, EdorItemGrid);
        }
        catch (ex)
        {
            alert("警告：查询客户既往保全批改项目信息出现异常！ ");
            return;
        }
} 
/*============================================================================*/
     
/**
 * 影像资料查询
 */
function showImage()
{
        var sEdorAcceptNo;
        try
        {
            sEdorAcceptNo = document.all("EdorAcceptNo").value;
        }
        catch (ex) {}
        if (sEdorAcceptNo == null || trim(sEdorAcceptNo) == "")
        {
            alert("请先选择要查看的保全项目！");
            return;
        }
        else
        {
        	var tUrl="../bq/ImageQueryMain.jsp?BussNo="+sEdorAcceptNo+"&BussType=BQ";
        	OpenWindowNew(tUrl,"保全扫描影像","left");
        } //sEdorAcceptNo != null
}

/**
 *  返回主界面
 */
function returnParent()
{
    try
    {
        top.close();
        top.opener.focus();
    }
    catch (ex) {}
}

/*****************************************************************************
 *  根据EdorAcceptNo查阅批单信息
 *****************************************************************************/
function EndorseDetail()
{
    var nSelNo;
    try
    {
        nSelNo = EdorItemGrid.getSelNo() - 1;
    }
    catch (ex) {}
    if (nSelNo == null || nSelNo < 0)
    {
        alert("请先选择您要查看的申请项目！ ");
        return;
    }
    else
    {
        document.forms(0).action = "../f1print/AppEndorsementF1PJ1.jsp";
        document.forms(0).target = "_blank";
        document.forms(0).submit();
    }
}

//点选保全项目后给隐藏域赋值
function initHide()
{
    var nSelNo;
    try
    {
        nSelNo = EdorItemGrid.getSelNo() - 1;
    }
    catch (ex) {}
    if (nSelNo == null || nSelNo < 0)
    {
        alert("请先选择您要查看的申请项目！ ");
        return;
    }
    else
    {
      document.all("EdorAcceptNo").value = EdorItemGrid.getRowColData(nSelNo, 1);
      document.all("EdorNo").value = EdorItemGrid.getRowColData(nSelNo, 2);
    }
}

/**
 * 根据 EdorNo 查阅人名清单信息
 * Added by XinYQ on 2006-09-21
 */
function NamesBill()
{
    var nSelNo;
    try
    {
        nSelNo = EdorItemGrid.getSelNo() - 1;
    }
    catch (ex) {}
    if (nSelNo == null || nSelNo < 0)
    {
        alert("请先选择您要查看的申请项目！ ");
        return;
    }
    else
    {
        var sEdorNo;
        try
        {
            sEdorNo = document.getElementsByName("EdorNo")[0].value;
        }
        catch (ex) {}
        if (sEdorNo == null || trim(sEdorNo) == "")
        {
            alert("无法获取批单号。查询人名清单失败！ ");
            return;
        }
        else
        {
            var QuerySQL, arrResult;
            
            var sqlid826143523="DSHomeContSql826143523";
var mySql826143523=new SqlClass();
mySql826143523.setResourceName("bq.GEdorAgoQueryInputSql");//指定使用的properties文件名
mySql826143523.setSqlId(sqlid826143523);//指定使用的Sql的id
mySql826143523.addSubPara(sEdorNo);//指定传入的参数
QuerySQL=mySql826143523.getString();
            
//            QuerySQL = "select 'X' from LPEdorPrint2 where EdorNo = '" + sEdorNo + "'";
            //alert(QuerySQL);
            try
            {
                arrResult = easyExecSql(QuerySQL, 1, 0);
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
                document.forms[0].action = "../f1print/ReEndorsementF1PJ1.jsp?EdorNo=" + sEdorNo + "&type=Bill";
                document.forms[0].target = "_blank";
                document.forms[0].submit();
            }
        }
    } //nSelNo != null
}

/*============================================================================*/


//<!-- JavaScript Document END -->
