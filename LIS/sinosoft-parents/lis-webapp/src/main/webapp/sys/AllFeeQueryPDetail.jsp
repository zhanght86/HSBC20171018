<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
  <%
  //�������ƣ�
  //�����ܣ�
  //�������ڣ�2002-12-19 11:10:36
  //������  ��lh
  //���¼�¼��  ������    ��������     ����ԭ��/����
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

            <title>������ϸ��ѯ </title>
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
                        ����������Ϣ
                      </td>
                    </tr>
                  </table>
                  <div  id= "divLJAPay1" style= "display: ''">
                    <table  class= common align=center>
                      <TR  class= common>
                        <TD  class= title>
                          �������ֺ���
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
                          ���嵥����
                        </TD>
                        <TD  class= input>
                          <Input class= readonly name=GrpPolNo >
                          </TD>
                        </TR-->
                      </table>
                    </Div>

                    <TR  class= common>
                      <TD  class= title>
                        �����վݺ�
                      </TD>
                      <TD  class= input>
                        <Input class "common wid" name=PayNo id="PayNo" >
                        </TD>
                        <TD  class= title>
                          ��ʵ�����
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
                              ������ϸ��Ϣ
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
                              <td CLASS="title5">��ǰ��Ч���ֱ��Ѻϼ�</td>
                              <td CLASS="input5" COLSPAN="1">
                                <input NAME="ValidPrem" id="ValidPrem" readonly   CLASS="readonly wid">
                                </td>
                                <td CLASS="title5">����ȱ��Ѻϼ�</td>
                                <td CLASS="input5" COLSPAN="1">
                                  <input NAME="CurrentPrem" id="CurrentPrem" CLASS="readonly wid" >
                                  </td>
                                </tr>
                              </table>
                              <br>
                                <INPUT VALUE="��  ҳ" class= cssButton90 TYPE=button onClick="turnPage.firstPage();">
                                  <INPUT VALUE="��һҳ" class= cssButton91 TYPE=button onClick="turnPage.previousPage();">
                                    <INPUT VALUE="��һҳ" class= cssButton92 TYPE=button onClick="turnPage.nextPage();">
                                      <INPUT VALUE="β  ҳ" class= cssButton93 TYPE=button onClick="turnPage.lastPage();">
                                      </div>

                                      <INPUT class=cssButton VALUE=" ���� " TYPE=button onClick="returnParent();">
                                      </form>
                                      <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
                                    </body>
                                    <script language="JavaScript" >
                                      var turnPage = new turnPageClass();

                                      function easyQueryClick()
                                      {
                                        // ��дSQL���
                                        var strSQL = "";

var sqlid29="ProposalQuerySql29";
var mySql29=new SqlClass();
mySql29.setResourceName("sys.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
mySql29.setSqlId(sqlid29); //ָ��ʹ�õ�Sql��id
mySql29.addSubPara(tPolNo); //ָ������Ĳ���
strSQL=mySql29.getString();

                                     /*   strSQL = "select PayNo,"+
                                        " (case PayAimClass when '1' then '���˽���' when '2' then '���彻��' end) as PayAimClass,"+
                                        " SumActuPayMoney,"+
                                        " (select codename from ldcode where codetype = 'payintv' and code = PayIntv) as PayIntv,"+
                                        " MakeDate,"+
                                        "(case PayType when 'ZC' then '����' when 'NS' then '����������' when 'RB' then '��ȫ����' when 'RE' then '������Ч'end)as paytype  ,"+
                                        " CurPayToDate,paycount from LJAPayPerson where Polno='" + tPolNo + "' "+
                                        " order by paycount ";
*/
                                        //��ѯSQL�����ؽ���ַ���
                                        turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1, "", 0);

                                        //�ж��Ƿ��ѯ�ɹ�
                                        if (!turnPage.strQueryResult) {
                                          PolGrid.clearData();
                                          alert("���ݿ���û���������������ݣ�");
                                          return false;
                                        }

                                        //��ѯ�ɹ������ַ��������ض�ά����
                                        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

                                        //���ó�ʼ������MULTILINE����
                                        turnPage.pageDisplayGrid = PolGrid;

                                        //����SQL���
                                        turnPage.strQuerySql     = strSQL;

                                        //���ò�ѯ��ʼλ��
                                        turnPage.pageIndex = 0;

                                        //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
                                        var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);

                                        //����MULTILINE������ʾ��ѯ���
                                        displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

                                      }

                                      //��ǰ��Ч���Ѻϼ�
                                      function ValidPremCal()
                                      {

var sqlid30="ProposalQuerySql30";
var mySql30=new SqlClass();
mySql30.setResourceName("sys.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
mySql30.setSqlId(sqlid30); //ָ��ʹ�õ�Sql��id
mySql30.addSubPara(tContNo); //ָ������Ĳ���
mySql30.addSubPara(tRiskCode); //ָ������Ĳ���
var vSql0=mySql30.getString();

                                      // var vSql0 = "select * from LCPol where ContNo = '" + tContNo +"'"+
                                       // "   and riskCode = '" + tRiskCode + "'" +
                                       // "   and PolNo =MainPolNo";
                                        var arrResult0 = easyExecSql(vSql0);
                                        if(arrResult0  != null && arrResult0 !="" && arrResult0 !="null")
                                        {

var sqlid31="ProposalQuerySql31";
var mySql31=new SqlClass();
mySql31.setResourceName("sys.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
mySql31.setSqlId(sqlid31); //ָ��ʹ�õ�Sql��id
mySql31.addSubPara(tContNo); //ָ������Ĳ���
mySql31.addSubPara(tRiskCode); //ָ������Ĳ���
mySql31.addSubPara(tContNo); //ָ������Ĳ���
mySql31.addSubPara(tContNo); //ָ������Ĳ���
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
mySql32.setResourceName("sys.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
mySql32.setSqlId(sqlid32); //ָ��ʹ�õ�Sql��id
mySql32.addSubPara(tContNo); //ָ������Ĳ���
mySql32.addSubPara(tRiskCode); //ָ������Ĳ���
mySql32.addSubPara(tFirstDate); //ָ������Ĳ���
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

                                      //����ȱ��Ѻϼ�
                                      function CurrentPremCal()
                                      { 

var sqlid33="ProposalQuerySql33";
var mySql33=new SqlClass();
mySql33.setResourceName("sys.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
mySql33.setSqlId(sqlid33); //ָ��ʹ�õ�Sql��id
mySql33.addSubPara(tContNo); //ָ������Ĳ���
mySql33.addSubPara(tRiskCode); //ָ������Ĳ���
mySql33.addSubPara(tFirstDate); //ָ������Ĳ���
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

                                      //���ذ�ť
                                      function returnParent()
                                      {
                                        top.close();
                                      }
                                      </script>
                                      </html>
