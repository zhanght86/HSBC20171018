<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="java.util.*" %>
<%@page import="java.io.*" %>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>

<%
    //�ڴ����õ���Excel��������Ӧ��sql���ȡ���������Ӧ

    ExportExcel.Format format = new ExportExcel.Format();
    ArrayList listCell = new ArrayList();
    ArrayList listLB = new ArrayList();
    ArrayList listColWidth = new ArrayList();
    format.mListCell=listCell;
    format.mListBL=listLB;
    format.mListColWidth=listColWidth;

    ExportExcel.Cell tCell=null;
    ExportExcel.ListBlock tLB=null;

       //�������
       String tManageCom = request.getParameter("sManageCom");
       loggerDebug("RNNCDetailExecelsave","�������=============="+tManageCom);
       //���ѿ�ʼ����
       String tStartPayDate = request.getParameter("StartDay");
       loggerDebug("RNNCDetailExecelsave","���ѿ�ʼ����=============="+tStartPayDate);
       //���ѽ�������
       String tEndPayDate = request.getParameter("EndDay");
       loggerDebug("RNNCDetailExecelsave","���ѽ�������==============="+tEndPayDate);

    listColWidth.add(new String[]{"0","5000"});

    String sql =  "(select c.contno, c.managecom, c.riskname, c.signdate, "+
    							"sum(sumactupaymoney) from (select "+
    							"(select name from ldcom where comcode = b.managecom) managecom,"+
		              "a.contno,(select riskshortname from lmrisk where riskcode = a.riskcode) riskname,"+
		              " signdate, b.sumactupaymoney from lcpol a, ljapayperson b "+
		              " where a.polno = b.polno and a.renewcount = 0 and b.paycount = 1 "+
		              "   and b.paytype = 'ZC' "+
	                "		and a.signdate >= '"+tStartPayDate+"' "+
           				"		and a.signdate <= '"+tEndPayDate+"' "+
		           		"   and b.managecom like '"+tManageCom+"%%'"+
           				"   and a.contno not like 'HB%' and not exists (select 1 from lmriskapp where riskcode=b.riskcode and lmriskapp.mngcom = 'G')) c "+
									" group by c.contno, c.managecom, c.riskname, c.signdate "+
									" union select d.contno, d.managecom, d.riskname, d.signdate, "+
									"sum(sumactupaymoney) from (select "+
									"(select name from ldcom where comcode = a.managecom) managecom,"+
	               	"a.contno contno,(select riskshortname from lmrisk where riskcode = a.riskcode) riskname,"+
               		" signdate, b.sumactupaymoney from lbpol a, ljapayperson b "+
         					" where a.polno = b.polno and a.signdate = b.confdate and a.renewcount = 0"+
           				"		and b.paycount = 1 and b.paytype = 'ZC' "+
	                "		and a.signdate >= '"+tStartPayDate+"' "+
           				"		and a.signdate <= '"+tEndPayDate+"' "+
		           		"   and b.managecom like '"+tManageCom+"%%'"+
	           			"		and a.contno not like 'HB%' and not exists (select 1 from lmriskapp where riskcode=b.riskcode and lmriskapp.mngcom = 'G')) d "+
 									"	group by d.contno, d.managecom, d.riskname, d.signdate) "+
 									"	order by managecom,contno,riskname ,signdate ";
    loggerDebug("RNNCDetailExecelsave","����Լ�б���ϸsql��"+sql);
    tLB = new ExportExcel.ListBlock("001");
    tLB.colName=new String[]{"������","�������","����","�б�����","���"};
    tLB.sql=sql;
    tLB.row1=0;
    tLB.col1=0;
    tLB.InitData();
    listLB.add(tLB);

    try{

      response.reset();
      response.setContentType("application/octet-stream");
      response.setHeader("Content-Disposition","attachment; filename=NCDetailExecel_SignDate"+tStartPayDate+"_"+tEndPayDate+"_List.xls");
      OutputStream outOS=response.getOutputStream();

      BufferedOutputStream bos=new BufferedOutputStream(outOS);

      ExportExcel excel = new ExportExcel();

      excel.write(format,bos);

      bos.flush();
      bos.close();
    }
    catch(Exception e){
      loggerDebug("RNNCDetailExecelsave","����Excelʧ�ܣ�");
    };

%>
