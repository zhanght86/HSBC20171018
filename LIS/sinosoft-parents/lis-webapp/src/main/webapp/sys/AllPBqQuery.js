//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

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

//��ʾdiv����һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
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

//��ʾfrmSubmit��ܣ���������
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



// ��ѯ��ť
function easyQueryClick()
{
    if(fm.EdorAcceptNo.value == "" && fm.OtherNo.value == ""&& fm.EdorAppName.value == ""&& fm.EdorNo.value == ""&& fm.EdorAppDate.value == "")
    {
        alert("�������������ѡ�����²�ѯ�����е�һ������ȫ����ţ�������/�ͻ��ţ���������������ȫ�������ڣ������š�");
        fm.EdorAcceptNo.focus();
        return;
    }

    // ��дSQL���
    var strSQL = "";
    //if (tflag=="0")
    //  {
    //      strSQL = "select LPEdorMain.EdorNo,min(LPEdorMain.PolNo),min(LPEdorMain.InsuredNo),min(LPEdorMain.InsuredName),sum(LPEdorMain.GetMoney),decode(max(LPEdorMain.EdorState)||min(LPEdorMain.EdorState),'00','��ȫȷ��','11','��������','22','����ȷ��','21','��������ȷ��','20','���ֱ�ȫȷ��') from LPEdorMain where 1=1 "
    //                   + getWherePart( 'LPEdorMain.PolNo','PolNo' )
    //                   + "group by LPEdorMain.EdorNo";
    //  }
    //else
    //  {
            // for db2 sql
            //���ڼ���union�󣬲���������ֶ����js�������˴�sql������һ��ʼ
            //�Ȳ�����ֶμ�����ֶβ�ѯͬһ��Ĳ�����λ�ֶΣ���Mulline��ֵ�����������������ֶ�
           /* strSQL = "select a,b,c,d,e,f,g,h,i,j,k,l,m,n from ("
             +" select EdorAcceptNo a,otherno b,(select codename from ldcode where codetype = 'edornotype' and  (code) =  (othernotype)) c,"
             +" EdorAppName d,nvl(to_char(a.GetMoney),' ') e,Operator f,ConfOperator g,"
             +"  decode((select count(*) from lwmission where missionprop1=a.edoracceptno and activityid='0000000007'),0,( decode((select count(*) from lwmission where missionprop1=a.edoracceptno and activityid='0000000005'),0,( select CodeName from LDCode where codetype = 'edorstate' and trim(code) =  (edorstate)),'�˹��˱���')),'������') h,"
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

             //�������ñ����Ų�ѯ�ͻ��㱣ȫ
             if(fm.OtherNo.value != null && fm.OtherNo.value !="")
             {
               /* strSQL += " union ";
                strSQL +=
                " select EdorAcceptNo a,otherno b,(select codename from ldcode where codetype = 'edornotype' and  (code) =  (othernotype)) c,"
                 +" EdorAppName d,nvl(to_char(b.GetMoney),' ') e,Operator f,ConfOperator g,"
                 +" decode((select count(*) from lwmission where missionprop1=B.edoracceptno and activityid='0000000007'),0,( decode((select count(*) from lwmission where missionprop1=b.edoracceptno and activityid='0000000005'),0,( select CodeName from LDCode where codetype = 'edorstate' and trim(code) =  (edorstate)),'�˹��˱���')),'������') h,"
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

//             +" ), '00','��ȫȷ��','11','��������','22','����ȷ��','21','��������ȷ��','20', '���ֱ�ȫȷ��','��������') from LPEdorApp a where 1 = 1  "
//             + getWherePart('a.EdorAcceptNo','EdorAcceptNo')
//             + getWherePart('a.OtherNo','OtherNo')
//             + getWherePart('a.OtherNoType','OtherNoType')
//             + getWherePart('a.EdorAppName','EdorAppName')
//             + getWherePart('a.AppType','AppType')
//             + getWherePart('a.EdorAppDate','EdorAppDate');
        //}
    //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);
  
  turnPage1.queryModal(mySql.getString(), PolGrid);
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
      PolGrid.clearData();
      alert("��ѯʧ�ܣ�");
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
  arrGrid = arrDataSet;
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  //��������λ�������˸�ֵ��ע��˴�ֻ�Ǹ�һҳ��ֵ������Ҫ�ڷ�ҳ��ť�������ͬ�ĺ���
  queryLWMission();
}
//��ѯ������λ��������
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
    var brr = easyExecSql(mySql.getString(), 1, 0,"","",1);//ע��˴���6������Ӧ��Ϊ1����ʹ�÷�ҳ���ܣ�������ܻ��ȫ�ֱ���turnPage����
    if(brr)
    {
         PolGrid.setRowColData(i, 9, brr[0][0]);
         PolGrid.setRowColData(i, 10, brr[0][1]);
    }
  }
}

//��ѯ��ť
function QueryClick()
{
    initPolGrid();
    document.all('Transact').value ="QUERY|EDOR";
  var i = 0;
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//  showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;"); 
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
  var iWidth=550;      //�������ڵĿ��; 
  var iHeight=250;     //�������ڵĸ߶�; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();

  document.getElementById("fm").submit();//�ύ
}



// ��Ŀ��ϸ��ѯ
function ItemQuery()
{
    var arrReturn = new Array();
    var tSel = PolGrid.getSelNo();

    if( tSel == 0 || tSel == null )
        alert( "����ѡ��һ����¼���ٵ����ϸ��ѯ��ť��" );
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


    //��ӡ����
function PrtEdor()
{
    var arrReturn = new Array();

        var tSel = PolGrid.getSelNo();


        if( tSel == 0 || tSel == null )
            alert( "����ѡ��һ����¼���ٵ���鿴��ť��" );
        else
        {
            var state =PolGrid. getRowColData(tSel-1,8) ;

            if (state=="��������")
                alert ("��ѡ����������������״̬�����ܴ�ӡ��");
            else{
              var EdorAcceptNo=PolGrid. getRowColData(tSel-1,1) ;

                //var tEdorAcceptNo = SelfGrid.getRowColData(selno, 1);
                //var tMissionID = SelfGrid.getRowColData(selno, 5);
                //var tSubMissionID = SelfGrid.getRowColData(selno, 6);
                //var tLoadFlag = "edorApp";

                var varSrc="&EdorAcceptNo=" + EdorAcceptNo;
                var newWindow = OpenWindowNew("../sys/BqDetailQueryFrame.jsp?Interface= ../sys/BqDetailQuery.jsp" + varSrc,"��ȫ��ѯ��ϸ","left");
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
    //������Ҫ���ص�����
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
          alert("��ѡ��Ҫ���������");
          return;
    }

    var tEdorAcceptNo = PolGrid.getRowColData(selno, 1);
  var tOtherNoType  = PolGrid.getRowColData(selno, 3);

    var varSrc="&EdorAcceptNo=" + tEdorAcceptNo+"&OtherNoType="+tOtherNoType;
    var newWindow = OpenWindowNew("../sys/BqDetailQueryFrame.jsp?Interface= ../sys/BqDetailQuery.jsp" + varSrc,"��ȫ��ѯ��ϸ","left");

}
//��ȫ�����켣
function MissionQuery()
{
    var selno = PolGrid.getSelNo()-1;
    if (selno <0)
    {
          alert("��ѡ��Ҫ���������");
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
       alert("�ñ�ȫ����켣��Ϣ�����ڣ�");
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
          alert("��ѡ��Ҫ���������");
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

function queryNotice(){
	
	  var selno = PolGrid.getSelNo()-1;
    if (selno <0)
    {
          alert("��ѡ��Ҫ���������");
          return;
    }
    var EdorAcceptNo = PolGrid.getRowColData(selno, 1);
    if(EdorAcceptNo == null ||trim(EdorAcceptNo) == ""){
    	alert("���Ȳ�ѯ��");
    	return;	
    }
    var newWindow = OpenWindowNew("../bq/EdorNoticeQuery.jsp?EdorAcceptNo="+ EdorAcceptNo,"��ȫ�������ѯ","left");
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
//                      alert("��ѯʧ��!!");
//                      return "";
//                  }
//             }
//         }
//         else
//         {
//           alert("��ѯʧ��!");
//           return "";
//         }
//         fm.ManageCom.CodeData = returnstr;
//         return returnstr;
//  }
//}
//}