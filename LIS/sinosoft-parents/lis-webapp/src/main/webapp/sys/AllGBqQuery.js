/*******************************************************************************
* <p>Title: �ۺϲ�ѯ-���屣ȫ��ѯ</p>
* <p>Description: ���屣ȫ��ѯ-jsҳ��ű��ļ�</p>
* <p>Copyright: Copyright (c) 2006 Sinosoft, Co.,Ltd. All Rights Reserved</p>
* <p>Company: �п���Ƽ��ɷ����޹�˾</p>
* <p>WebSite: http://www.sinosoft.com.cn</p>
*
* @todo     : ��ȫ-VIP�ͻ���ѯ
* @author   : liuxiaosong
* @version  : 1.00
* @date     : 2006-10-16
* ���¼�¼��  ������    ��������     ����ԭ��/����
******************************************************************************/

var showInfo;  //����������ʾ��Ϣ
var turnPage = new turnPageClass(); //��ѯ�����ҳ����������
var mySql = new SqlClass();

/**
 * ��ѯ�����߼�
 */
function grpBqQuery()
{
    //����ǰ̨У�飬��������������Ӧ��ʹ��verify
    if(fm.EdorAcceptNo.value == "" && fm.OtherNo.value == ""&& fm.EdorAppName.value == ""&& fm.EdorNo.value == ""&& fm.EdorAppDate.value == "")
    {
        alert("�������������ѡ�����²�ѯ�����е�һ������ȫ����ţ�������/�ͻ��ţ���������������ȫ�������ڣ������š�");
        fm.EdorAcceptNo.focus();
        return;
    }

    //��ѯ����SQL
    var strSQL = "";

    //���ڼ���union�󣬲���������ֶ����js�������˴�sql������һ��ʼ
    //�Ȳ�����ֶμ�����ֶβ�ѯͬһ��Ĳ�����λ�ֶΣ���Mulline��ֵ�����������������ֶ�
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

    //             +" ), '00','��ȫȷ��','11','��������','22','����ȷ��','21','��������ȷ��','20', '���ֱ�ȫȷ��','��������') from LPEdorApp a where 1 = 1  "
    //             + getWherePart('a.EdorAcceptNo','EdorAcceptNo')
    //             + getWherePart('a.OtherNo','OtherNo')
    //             + getWherePart('a.OtherNoType','OtherNoType')
    //             + getWherePart('a.EdorAppName','EdorAppName')
    //             + getWherePart('a.AppType','AppType')
    //             + getWherePart('a.EdorAppDate','EdorAppDate');
    //}
    //��ѯSQL�����ؽ���ַ���*/
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

    //�ж��Ƿ��ѯ�ɹ�
    if (!turnPage.strQueryResult) {
        PolGrid.clearData();
        alert("��ѯʧ�ܣ�");
        return false;
    }

    //��ѯ�ɹ������ַ��������ض�ά��?
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

    //���ó�ʼ������MULTILINE����
    turnPage.pageDisplayGrid = PolGrid;

    //����SQL���
    turnPage.strQuerySql = strSQL;

    //���ò�ѯ��ʼλ��
    turnPage.pageIndex = 0;

    //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ���?
    var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
    arrGrid = arrDataSet;
    //����MULTILINE������ʾ��ѯ���
    displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
    //��������λ�������˸�ֵ��ע��˴�ֻ�Ǹ�һҳ��ֵ������Ҫ�ڷ�ҳ��ť�������ͬ�ĺ���
    queryLWMission();
}

/**
*���ϣ���ѯ������λ��������
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
        var brr = easyExecSql(mySql.getString(), 1, 0,"","",1);//ע��˴���6������Ӧ��Ϊ1����ʹ�÷�ҳ���ܣ�������ܻ��ȫ�ֱ���turnPage����
        if(brr)
        {
            PolGrid.setRowColData(i, 9, brr[0][0]);
            PolGrid.setRowColData(i, 10, brr[0][1]);
        }
    }
}


//��ӡ������û��

function PrtEdor()
{
	var arrReturn = new Array();
	
	var tSel = PolGrid.getSelNo();
	
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ���鿴��ť��" );
	else
	{
		var state =PolGrid. getRowColData(tSel-1,9) ;
		//alert(state);
		//return;
		if (state!="ȷ����Ч")
		alert ("��ѡ������δ��Ч�����ܴ�ӡ��");
		else{
			var EdorNo=PolGrid. getRowColData(tSel-1,17) ;
			//var varSrc="&EdorNo=" + EdorNo;
			//var newWindow = OpenWindowNew("../sys/BqDetailQueryFrame.jsp?Interface= ../sys/BqDetailQuery.jsp" + varSrc,"��ȫ��ѯ��ϸ","left");
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
		alert( "����ѡ��һ����¼���ٵ���鿴��ť��" );
	else
	{
		var state =PolGrid. getRowColData(tSel-1,9) ;
		//alert(state);
		//return;
		if (state!="ȷ����Ч")
		alert ("��ѡ������δ��Ч�����ܴ�ӡ��");
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
          alert("���棺��ѯ�����嵥��Ϣ�����쳣�� ");
          return;
      }
      if (arrResult == null)
      {
          alert("�ñ����˴�������Ŀû�������嵥��Ϣ�� ");
          return;
      }
      else
      {
          fm.action = "../f1print/ReEndorsementF1PJ1.jsp?EdorNo=" + EdorNo + "&type=Bill";
          fm.target = "_blank";
          document.getElementById("fm").submit();
      }
			//var varSrc="&EdorNo=" + EdorNo;
			//var newWindow = OpenWindowNew("../sys/BqDetailQueryFrame.jsp?Interface= ../sys/BqDetailQuery.jsp" + varSrc,"��ȫ��ѯ��ϸ","left");
			//fm.action = "../f1print/EndorsementF1PJ1.jsp?EdorNo=" + EdorNo;
			//fm.target="f1print";
			//document.getElementById("fm").submit();
		}
	}
}

/**
* ��ѯ��ȫ�����켣
*/

function MissionQuery()
{
    var selno = PolGrid.getSelNo()-1;
    if (selno <0)
    {
        alert("��ѡ��Ҫ���������");
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
        alert("�ñ�ȫ����켣��Ϣ�����ڣ�");
    }
    var pMissionID = brr[0][0];
    window.open("../bq/EdorMissionFrame.jsp?MissionID="+pMissionID,"window3","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}

/**
*�ü��±��鿴
*/
function showNotePad()
{

    var selno = PolGrid.getSelNo()-1;
    if (selno <0)
    {
        alert("��ѡ��Ҫ���������");
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
        alert("�ñ�ȫ����������Ϣ�����ڣ�");
        return;
    }
    var MissionID = brr[0][0];

    var OtherNoType = "1";
    var UnSaveFlag = "1";//���ر��水ť
    if(MissionID == null || MissionID == "")
    {
        alert("MissionIDΪ�գ�");
        return;
    }
    var varSrc= "&MissionID="+ MissionID + "&NoType="+ OtherNoType + "&UnSaveFlag="+ UnSaveFlag;
    var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface= ../uw/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");
}
// end function showNotePad

/*
*��ʾdiv����һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
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

//��ȫɨ��Ӱ���ѯ
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
             alert("�˴α�ȫ����û�����ɨ��Ӱ�����ϣ�");
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
             alert("��ѯ��ȫɨ����ҵ�����ͱ���ʧ�ܣ�");
             return;
        }
        var varSrc = "ScanFlag = 1&prtNo=" + PrtNo + "&SubType=" + tResult[0][0]+ "&EdorAcceptNo="+EdorAcceptNo;
        var newWindow = OpenWindowNew("../bq/EdorScan.jsp?" + varSrc,"��ȫɨ��Ӱ��","left");
    }
}
