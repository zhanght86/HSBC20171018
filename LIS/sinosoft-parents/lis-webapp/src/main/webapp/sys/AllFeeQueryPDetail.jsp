<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
  <%
  //程序名称：
  //程序功能：
  //创建日期：2002-12-19 11:10:36
  //创建人  ：lh
  //更新记录：  更新人    更新日期     更新原因/内容
  %>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%
  String tPayNo = "";

  try
  {
    tPayNo = request.getParameter("PayNo");
  }
  catch( Exception e )
  {
    tPayNo = "";
  }
  String tSumActuPayMoney = "";
  try
  {
    tSumActuPayMoney = request.getParameter("SumActuPayMoney");
    loggerDebug("AllFeeQueryPDetail",tSumActuPayMoney);
  }
  catch( Exception e )
  {
    tSumActuPayMoney = "";
  }

  String tPolNo = "";
  tPolNo = request.getParameter("PolNo");
  String tContNo = request.getParameter("ContNo");
  String tRiskCode = request.getParameter("RiskCode");
  loggerDebug("AllFeeQueryPDetail","tContNo:" + tContNo);
  loggerDebug("AllFeeQueryPDetail","tPolNo:" + request.getParameter("PolNo"));
  String tCurrentDate = PubFun.getCurrentDate();
  String tCurrentTime = PubFun.getCurrentTime();
  String tFirstDate = StrTool.getVisaYear(tCurrentDate)+ "-01-01";
  loggerDebug("AllFeeQueryPDetail","tContNo:" + tContNo);
  loggerDebug("AllFeeQueryPDetail","tCurrentDate:" + tCurrentDate);

  %>
  <head >
    <script type="">
      var tPolNo = "<%=tPolNo%>";
      var tContNo = "<%=tContNo%>";
      var tRiskCode = "<%=tRiskCode%>";
      var tPayNo = "<%=tPayNo%>";
      var tSumActuPayMoney = "<%=tSumActuPayMoney%>";
      var tCurrentDate = "<%=tCurrentDate%>";
      var tFirstDate = "<%=tFirstDate%>";
      </script>
      <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
      <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
        <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
          <meta http-equiv="Content-Type" content="text/html; charset=GBK">
            <script src="../common/javascript/Common.js" type=""></SCRIPT>
            <script src="../common/javascript/MulLine.js" type=""></SCRIPT>
            <script language="JavaScript" src="../common/javascript/EasyQuery.js" type=""></script>
            <script src="../common/javascript/VerifyInput.js" type=""></SCRIPT>
            <SCRIPT src="../common/cvar/CCodeOperate.js" type=""></SCRIPT>
            <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
            <script  src="../common/easyQueryVer3/EasyQueryVer3.js" type=""></script>
            <script  src="../common/easyQueryVer3/EasyQueryCache.js" type=""></script >
						<SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
            <!--SCRIPT src="AllFeeQueryPDetail.js"></SCRIPT-->
            <%@include file="AllFeeQueryPDetailInit.jsp"%>

            <title>交费明细查询 </title>
          </head>
          <body  onload="initForm();" >
            <form  name=fm id="fm" action="">
              <div  id= "divLJAPPersonHidden" style= "display: none">
                <table>
                  <tr>
                    <td class=common>
                      <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLJAPay1);" alt="">
                      </td>
                      <td class= titleImg>
                        费用整体信息
                      </td>
                    </tr>
                  </table>
                  <div  id= "divLJAPay1" style= "display: ''">
                    <table  class= common align=center>
                      <TR  class= common>
                        <TD  class= title>
                          保单险种号码
                        </TD>
                        <TD  class= input>
                          <Input class= "readonly wid" name=PolNo id="PolNo" >
                          </TD>
                          <TD  class= title>
                          </TD>
                          <TD  class= input>
                          </TD>
                          <TD  class= title>
                          </TD>
                          <TD  class= input>
                          </TD>
                        </TR>

                        <!--TR  class= common>
                        <TD  class= title>
                          集体单号码
                        </TD>
                        <TD  class= input>
                          <Input class= readonly name=GrpPolNo >
                          </TD>
                        </TR-->
                      </table>
                    </Div>

                    <TR  class= common>
                      <TD  class= title>
                        交费收据号
                      </TD>
                      <TD  class= input>
                        <Input class "common wid" name=PayNo id="PayNo" >
                        </TD>
                        <TD  class= title>
                          总实交金额
                        </TD>
                        <TD  class= input>
                          <Input class= "common wid" name=SumActuPayMoney id="SumActuPayMoney" >
                          </TD>
                        </TR>
                      </Div>

                      <table>
                        <tr>
                          <td class=common>
                            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLJAPPerson1);" alt="">
                            </td>
                            <td class= titleImg>
                              费用详细信息
                            </td>
                          </tr>
                        </table>

                        <div  id= "divLJAPPerson1" style= "display: ''" align = center>
                          <table  class= common>
                            <tr  class= common>
                              <td align= "left" colSpan=1>
                                <span id="spanPolGrid" >
                                </span>
                              </td>
                            </tr>
                          </table>
                          <table class="common" id="table2">
                            <tr CLASS="common">
                              <td CLASS="title5">当前有效险种保费合计</td>
                              <td CLASS="input5" COLSPAN="1">
                                <input NAME="ValidPrem" id="ValidPrem" readonly   CLASS="readonly wid">
                                </td>
                                <td CLASS="title5">本年度保费合计</td>
                                <td CLASS="input5" COLSPAN="1">
                                  <input NAME="CurrentPrem" id="CurrentPrem" CLASS="readonly wid" >
                                  </td>
                                </tr>
                              </table>
                              <br>
                                <INPUT VALUE="首  页" class= cssButton90 TYPE=button onClick="turnPage.firstPage();">
                                  <INPUT VALUE="上一页" class= cssButton91 TYPE=button onClick="turnPage.previousPage();">
                                    <INPUT VALUE="下一页" class= cssButton92 TYPE=button onClick="turnPage.nextPage();">
                                      <INPUT VALUE="尾  页" class= cssButton93 TYPE=button onClick="turnPage.lastPage();">
                                      </div>

                                      <INPUT class=cssButton VALUE=" 返回 " TYPE=button onClick="returnParent();">
                                      </form>
                                      <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
                                    </body>
                                    <script language="JavaScript" >
                                      var turnPage = new turnPageClass();

                                      function easyQueryClick()
                                      {
                                        // 书写SQL语句
                                        var strSQL = "";

var sqlid29="ProposalQuerySql29";
var mySql29=new SqlClass();
mySql29.setResourceName("sys.ProposalQuerySql"); //指定使用的properties文件名
mySql29.setSqlId(sqlid29); //指定使用的Sql的id
mySql29.addSubPara(tPolNo); //指定传入的参数
strSQL=mySql29.getString();

                                     /*   strSQL = "select PayNo,"+
                                        " (case PayAimClass when '1' then '个人交费' when '2' then '集体交费' end) as PayAimClass,"+
                                        " SumActuPayMoney,"+
                                        " (select codename from ldcode where codetype = 'payintv' and code = PayIntv) as PayIntv,"+
                                        " MakeDate,"+
                                        "(case PayType when 'ZC' then '正常' when 'NS' then '新增附加险' when 'RB' then '保全回退' when 'RE' then '保单复效'end)as paytype  ,"+
                                        " CurPayToDate,paycount from LJAPayPerson where Polno='" + tPolNo + "' "+
                                        " order by paycount ";
*/
                                        //查询SQL，返回结果字符串
                                        turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1, "", 0);

                                        //判断是否查询成功
                                        if (!turnPage.strQueryResult) {
                                          PolGrid.clearData();
                                          alert("数据库中没有满足条件的数据！");
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

                                        //调用MULTILINE对象显示查询结果
                                        displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

                                      }

                                      //当前有效保费合计
                                      function ValidPremCal()
                                      {

var sqlid30="ProposalQuerySql30";
var mySql30=new SqlClass();
mySql30.setResourceName("sys.ProposalQuerySql"); //指定使用的properties文件名
mySql30.setSqlId(sqlid30); //指定使用的Sql的id
mySql30.addSubPara(tContNo); //指定传入的参数
mySql30.addSubPara(tRiskCode); //指定传入的参数
var vSql0=mySql30.getString();

                                      // var vSql0 = "select * from LCPol where ContNo = '" + tContNo +"'"+
                                       // "   and riskCode = '" + tRiskCode + "'" +
                                       // "   and PolNo =MainPolNo";
                                        var arrResult0 = easyExecSql(vSql0);
                                        if(arrResult0  != null && arrResult0 !="" && arrResult0 !="null")
                                        {

var sqlid31="ProposalQuerySql31";
var mySql31=new SqlClass();
mySql31.setResourceName("sys.ProposalQuerySql"); //指定使用的properties文件名
mySql31.setSqlId(sqlid31); //指定使用的Sql的id
mySql31.addSubPara(tContNo); //指定传入的参数
mySql31.addSubPara(tRiskCode); //指定传入的参数
mySql31.addSubPara(tContNo); //指定传入的参数
mySql31.addSubPara(tContNo); //指定传入的参数
var vSql=mySql31.getString();

                                          // var vSql = "select sum(SumActuPayMoney) from LJAPayPerson where ContNo = '"+tContNo+"'"+
                                          // "   and riskcode = '"+tRiskCode+"'"+
                                          // "   and exists (select * from LCContState where ContNo = '"+tContNo+"'"+
                                          //"   and PolNo in (select PolNo from LCPol where ContNo = '"+tContNo+"'"+
                                          // "   and PolNo = MainPolNo ) "+
                                          // "   and EndDate is null  and State = '0'  and StateType = 'Available')";
                                        }
                                        else{

var sqlid32="ProposalQuerySql32";
var mySql32=new SqlClass();
mySql32.setResourceName("sys.ProposalQuerySql"); //指定使用的properties文件名
mySql32.setSqlId(sqlid32); //指定使用的Sql的id
mySql32.addSubPara(tContNo); //指定传入的参数
mySql32.addSubPara(tRiskCode); //指定传入的参数
mySql32.addSubPara(tFirstDate); //指定传入的参数
var vSql=mySql32.getString();

                                     /*      var vSql = "select sum(SumActuPayMoney) from LJAPayPerson a where ContNo = '"+tContNo+"'"+
                                           " and riskcode = '"+tRiskCode+"'"+
                                           " and CurPaytoDate <=(select max(a.curpaytodate) from LJAPayPerson where ContNo = a.ContNo)"+
                                           " and CurPaytoDate >= '"+tFirstDate+"'";  */                                        
                                        }
                                        var arrResult = easyExecSql(vSql);
                                        if(arrResult!=null && arrResult!="null" && arrResult !="")
                                        {
                                          fm.ValidPrem.value=arrResult;
                                        }
                                        else {
                                          fm.ValidPrem.value = "0";
                                        }
                                      }

                                      //本年度保费合计
                                      function CurrentPremCal()
                                      { 

var sqlid33="ProposalQuerySql33";
var mySql33=new SqlClass();
mySql33.setResourceName("sys.ProposalQuerySql"); //指定使用的properties文件名
mySql33.setSqlId(sqlid33); //指定使用的Sql的id
mySql33.addSubPara(tContNo); //指定传入的参数
mySql33.addSubPara(tRiskCode); //指定传入的参数
mySql33.addSubPara(tFirstDate); //指定传入的参数
var cSql=mySql33.getString();

                                /*        var cSql = "select sum(SumActuPayMoney) from LJAPayPerson a where ContNo = '"+tContNo+"'"+
                                        " and riskcode = '"+tRiskCode+"'"+
                                        " and CurPaytoDate <=(select max(a.curpaytodate) from LJAPayPerson where ContNo = a.ContNo)"+
                                        " and CurPaytoDate >= '"+tFirstDate+"'";*/
                                        var arrResult1 = easyExecSql(cSql);
                                        if(arrResult1!=null && arrResult1!="null" && arrResult1 !="")
                                        {
                                          fm.CurrentPrem.value=arrResult1;
                                        }
                                        else {
                                          fm.CurrentPrem.value = "0";
                                        }
                                      }

                                      //返回按钮
                                      function returnParent()
                                      {
                                        top.close();
                                      }
                                      </script>
                                      </html>
