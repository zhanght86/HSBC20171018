//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var tDisplay;
var turnPage = new turnPageClass();
var turnPagePolGrid = new turnPageClass();
var turnPageCusGrid = new turnPageClass();
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";

// ���ݷ��ظ�����
function getQueryDetail()
{
    var arrReturn = new Array();
    var tSel = PolGrid.getSelNo();

    if( tSel == 0 || tSel == null )
        alert( "����ѡ��һ����¼��" );
    else
    {
      var cPolNo = PolGrid.getRowColData(tSel - 1,2);
        parent.VD.gVSwitch.deleteVar("PolNo");
        parent.VD.gVSwitch.addVar("PolNo","",cPolNo);

        if (cPolNo == "")
            return;

        var GrpPolNo = PolGrid.getRowColData(tSel-1,1);
        var prtNo = PolGrid.getRowColData(tSel-1,3);
        //alert("dfdf");
        if( tIsCancelPolFlag == "0")
        {
            if (GrpPolNo =="00000000000000000000")
            {
                window.open("./AllProQueryMain.jsp?LoadFlag=6&prtNo="+prtNo,"window1",sFeatures);
            }
            else
            {
                window.open("./AllProQueryMain.jsp?LoadFlag=4",sFeatures);
            }
        }
        else
        {
            if ( tIsCancelPolFlag == "1")
            {
                //����������ѯ
                if (GrpPolNo =="00000000000000000000")
                {
                    window.open("./AllProQueryMain_B.jsp?LoadFlag=6","window1",sFeatures);
                }
                else
                {
                    window.open("./AllProQueryMain_B.jsp?LoadFlag=7","",sFeatures);
                }
            }
            else
            {
                alert("�������ʹ������!");
                return;
            }
        }
 }
}

//���������Ĳ�ѯ����
function getQueryDetail_B()
{
    var arrReturn = new Array();
    var tSel = PolGrid.getSelNo();

    if( tSel == 0 || tSel == null )
        alert( "����ѡ��һ����¼��" );
    else
    {
      var cPolNo = PolGrid.getRowColData(tSel - 1,1);
        parent.VD.gVSwitch.deleteVar("PolNo");
        parent.VD.gVSwitch.addVar("PolNo","",cPolNo);
        if (cPolNo == "")
            return;
        var GrpPolNo = PolGrid.getRowColData(tSel-1,6);
        if (GrpPolNo =="00000000000000000000")
        {
                window.open("./AllProQueryMain_B.jsp?LoadFlag=6","window1",sFeatures);
            }
            else
            {
                window.open("./AllProQueryMain_B.jsp?LoadFlag=7","",sFeatures);
            }
    }
}

// ������ѯ
function PolClick()
{
    var arrReturn = new Array();
    var tSel = PolGrid.getSelNo();
    if( tSel == 0 || tSel == null )
        alert( "����ѡ��һ����¼���ٵ����ϸ��ť��" );
    else
    {
        var cContNo = PolGrid.getRowColData(tSel - 1,1);
        if (cContNo == "")
            return;
            //alert("cContNo="+cContNo);
                OpenWindowNew("../sys/PolDetailQueryMain.jsp?ContNo=" + cContNo +"&IsCancelPolFlag=0","������ϸ","left");
        //window.open("../sys/PolDetailQueryMain.jsp?ContNo=" + cContNo +"&IsCancelPolFlag=0","������ϸ",'top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    }
}

// ���ٲ�ѯ��ť��
function quickQueryClick()
{
    //�ж��Ƿ��в�ѯ����
    if(document.all('IdCardNo').value =="" && document.all('CustomerNo').value ==""&&document.all('PrtNo').value=="")
    {
        alert("����������֤�����롢�ͻ��ź�ӡˢ���е�һ����");
        document.all('IdCardNo').focus();
      return;
    }
    var strsql="";
    var strsql2="";
    if(document.all('IdCardNo').value =="" && document.all('CustomerNo').value ==""&&document.all('PrtNo').value!=""){
      //ֻͨ��ӡˢ�Ų�ѯ����Ϣ
	  
	  var sqlid10="ProposalQuerySql10";
var mySql10=new SqlClass();
mySql10.setResourceName("sys.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
mySql10.setSqlId(sqlid10);//ָ��ʹ�õ�Sql��id
mySql10.addSubPara(document.all('PrtNo').value );//ָ������Ĳ���
strsql=mySql10.getString();

	  var sqlid11="ProposalQuerySql11";
var mySql11=new SqlClass();
mySql11.setResourceName("sys.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
mySql11.setSqlId(sqlid11);//ָ��ʹ�õ�Sql��id
mySql11.addSubPara(document.all('PrtNo').value );//ָ������Ĳ���
mySql11.addSubPara(document.all('PrtNo').value );//ָ������Ĳ���
strsql2=mySql11.getString();
	  
//      strsql="select ContNo,Prtno,AppntName,GrpContNo,ProposalContNo,InsuredNo,InsuredName from LCCont where PrtNo='" + document.all('PrtNo').value + "'";
//      strsql2="select a.customerno,'Ͷ����',a.name,decode(a.sex,0,'��','Ů'),a.birthday,"
//      +"(select codename from ldcode where codetype='idtype' and code=a.idtype),a.idno from ldperson a,lccont b where a.customerno=b.appntno and b.prtno='" + document.all('PrtNo').value + "' "
//      +" union "
//      +"select a.customerno,'������',a.name,decode(a.sex,0,'��','Ů'),a.birthday,"
//      +"(select codename from ldcode where codetype='idtype' and code=a.idtype),a.idno from ldperson a,lccont b where a.customerno=b.insuredno and b.prtno='" + document.all('PrtNo').value + "' "
//       ;
    }
    else
    {
	    if(document.all('IdCardNo').value !="" && document.all('CustomerNo').value =="")
	    {
			
var sqlid12="ProposalQuerySql12";
var mySql12=new SqlClass();
mySql12.setResourceName("sys.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
mySql12.setSqlId(sqlid12);//ָ��ʹ�õ�Sql��id
mySql12.addSubPara(document.all('IdCardNo').value );//ָ������Ĳ���
mySql12.addSubPara(document.all('PrtNo').value );//ָ������Ĳ���
mySql12.addSubPara(document.all('IdCardNo').value );//ָ������Ĳ���
mySql12.addSubPara(document.all('PrtNo').value );//ָ������Ĳ���
mySql12.addSubPara(document.all('IdCardNo').value );//ָ������Ĳ���
mySql12.addSubPara(document.all('PrtNo').value );//ָ������Ĳ���
strsql=mySql12.getString();

var sqlid13="ProposalQuerySql13";
var mySql13=new SqlClass();
mySql13.setResourceName("sys.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
mySql13.setSqlId(sqlid13);//ָ��ʹ�õ�Sql��id
mySql13.addSubPara(document.all('IdCardNo').value );//ָ������Ĳ���

strsql2=mySql13.getString();
			
//	        strsql=" select ContNo,Prtno,AppntName,GrpContNo,ProposalContNo,InsuredNo,InsuredName from LCCont where AppntIDNo='" + document.all('IdCardNo').value + "'"
//	              +getWherePart('PrtNo','PrtNo')
//	              +" union"
//	              +" select ContNo,Prtno,AppntName,GrpContNo,ProposalContNo,InsuredNo,InsuredName from LCCont where AppntNo in (select CustomerNo from LDPerson where IdNo ='" + document.all('IdCardNo').value + "')"
//	              +getWherePart('PrtNo','PrtNo')
//	              +" union"
//	              +" select ContNo,Prtno,AppntName,GrpContNo,ProposalContNo,InsuredNo,InsuredName from LCCont where InsuredNo in (select CustomerNo from LDPerson where IdNo ='" + document.all('IdCardNo').value + "')"
//	              +getWherePart('PrtNo','PrtNo')
//	              +"";
//	       strsql2="select a.customerno,'',a.name,decode(a.sex,0,'��','Ů'),a.birthday,(select codename from ldcode where codetype='idtype' and code=a.idtype),a.idno "
//             +" from ldperson a where a.idno='" + document.all('IdCardNo').value + "' ";
	    }
	    if(document.all('IdCardNo').value =="" && document.all('CustomerNo').value !="")
	    {
			
			var sqlid14="ProposalQuerySql14";
var mySql14=new SqlClass();
mySql14.setResourceName("sys.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
mySql14.setSqlId(sqlid14);//ָ��ʹ�õ�Sql��id
mySql14.addSubPara(document.all('CustomerNo').value );//ָ������Ĳ���
mySql14.addSubPara(document.all('PrtNo').value );//ָ������Ĳ���
mySql14.addSubPara(document.all('CustomerNo').value );//ָ������Ĳ���
mySql14.addSubPara(document.all('PrtNo').value );//ָ������Ĳ���
strsql=mySql14.getString();

var sqlid15="ProposalQuerySql15";
var mySql15=new SqlClass();
mySql15.setResourceName("sys.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
mySql15.setSqlId(sqlid15);//ָ��ʹ�õ�Sql��id
mySql15.addSubPara(document.all('CustomerNo').value );//ָ������Ĳ���
strsql2=mySql15.getString();
			
//	        strsql=" select ContNo,Prtno,AppntName,GrpContNo,ProposalContNo,InsuredNo,InsuredName from LCCont where AppntNo='" + document.all('CustomerNo').value + "'"
//	        	  +getWherePart('PrtNo','PrtNo')
//	              +" union"
//	              +" select ContNo,Prtno,AppntName,GrpContNo,ProposalContNo,InsuredNo,InsuredName from LCCont where InsuredNo='" + document.all('CustomerNo').value + "'"
//	              +getWherePart('PrtNo','PrtNo')
//	              +"";
//	        strsql2="select a.customerno,'',a.name,decode(a.sex,0,'��','Ů'),a.birthday,(select codename from ldcode where codetype='idtype' and code=a.idtype),a.idno "
//             +" from ldperson a where a.customerno='" + document.all('CustomerNo').value + "' ";
	    }
	    if(document.all('IdCardNo').value !="" && document.all('CustomerNo').value !="")
	    {
			
var sqlid16="ProposalQuerySql16";
var mySql16=new SqlClass();
mySql16.setResourceName("sys.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
mySql16.setSqlId(sqlid16);//ָ��ʹ�õ�Sql��id
mySql16.addSubPara(document.all('IdCardNo').value );//ָ������Ĳ���
mySql16.addSubPara(document.all('PrtNo').value );//ָ������Ĳ���
mySql16.addSubPara(document.all('CustomerNo').value );//ָ������Ĳ���

mySql16.addSubPara(document.all('PrtNo').value );//ָ������Ĳ���
mySql16.addSubPara(document.all('CustomerNo').value );//ָ������Ĳ���
mySql16.addSubPara(document.all('PrtNo').value );//ָ������Ĳ���
strsql=mySql16.getString();

var sqlid17="ProposalQuerySql17";
var mySql17=new SqlClass();
mySql17.setResourceName("sys.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
mySql17.setSqlId(sqlid17);//ָ��ʹ�õ�Sql��id
mySql17.addSubPara(document.all('CustomerNo').value );//ָ������Ĳ���
mySql17.addSubPara(document.all('IdCardNo').value );//ָ������Ĳ���
strsql2=mySql17.getString();
			
//	        strsql=" select ContNo,Prtno,AppntName,GrpContNo,ProposalContNo,InsuredNo,InsuredName from LCCont where AppntIDNo='" + document.all('IdCardNo').value + "'"
//	        	  +getWherePart('PrtNo','PrtNo')
//	              +" union"
//	              +" select ContNo,Prtno,AppntName,GrpContNo,ProposalContNo,InsuredNo,InsuredName from LCCont where AppntNo ='" + document.all('CustomerNo').value + "'"
//	              +getWherePart('PrtNo','PrtNo')
//	              +" union"
//	              +" select ContNo,Prtno,AppntName,GrpContNo,ProposalContNo,InsuredNo,InsuredName from LCCont where InsuredNo ='" + document.all('CustomerNo').value + "'"
//	              +getWherePart('PrtNo','PrtNo')
//	              +"";
//	        strsql2="select a.customerno,'',a.name,decode(a.sex,0,'��','Ů'),a.birthday,(select codename from ldcode where codetype='idtype' and code=a.idtype),a.idno "
//             +" from ldperson a where a.customerno='" + document.all('CustomerNo').value + "' and a.idno='" + document.all('IdCardNo').value + "'";
	    }
    }
    try
    {
        turnPagePolGrid.pageDivName = "divTurnPagePolGrid";
        turnPagePolGrid.queryModal(strsql, PolGrid);
        
        turnPageCusGrid.pageDivName = "divTurnPageCustomerGrid";
        turnPageCusGrid.queryModal(strsql2, CustomerGrid);
    }
    catch (ex) {}
}

// ��ѯ��ť
function easyQueryClick()
{
    //�ж��Ƿ��в�ѯ����
    var i = 0;
    if (document.all('GrpContNo').value !="")
    i =i+1;
    if (document.all('AgentCode').value !="")
    i =i+1;
    //if (document.all('AgentGroup').value !="")
    //i =i+1;
    if (document.all('ManageCom').value !="")
    i =i+1;
    if (document.all('AppntName').value !="")
    i =i+1;
    if (document.all('CValiDate').value !="")
    i =i+1;
    if (document.all('InsuredNo').value !="")
    i =i+1;
    if (document.all('InsuredName').value !="")
    i =i+1;
    if (document.all('PayLocation').value !="")
    i =i+1;
    if(document.all('AppntIDNo').value !="")
    i =i+1;
        if(i < 3 && document.all('ContNo').value == "" && document.all('ProposalContNo').value == "" && document.all('tempfeeno').value == "")
        {
            alert("��ѯ�������㣡�������������º���֮һ���������롢Ͷ�������롢����Э����š�\n\n���޷��ṩ����������������������������Ϊ��ѯ������");
            document.all('ContNo').focus();
            return;
        }

          // ��дSQL���
          var strSQL = "";
          var strSQL2 = "";
          var strSQL3 = "";
          var strSQLRisk="";
		  
		  var sqlid18="ProposalQuerySql18";
var mySql18=new SqlClass();
mySql18.setResourceName("sys.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
mySql18.setSqlId(sqlid18);//ָ��ʹ�õ�Sql��id
mySql18.addSubPara(document.all('ContNo').value );//ָ������Ĳ���
mySql18.addSubPara(document.all('GrpContNo').value );//ָ������Ĳ���
mySql18.addSubPara(document.all('ManageCom').value );//ָ������Ĳ���
mySql18.addSubPara(document.all('AgentCode').value );//ָ������Ĳ���
mySql18.addSubPara(document.all('ProposalContNo').value );//ָ������Ĳ���
mySql18.addSubPara(document.all('CValiDate').value );//ָ������Ĳ���

mySql18.addSubPara(document.all('InsuredNo').value );//ָ������Ĳ���
mySql18.addSubPara(document.all('PayLocation').value );//ָ������Ĳ���
mySql18.addSubPara(document.all('AppntIDNo').value );//ָ������Ĳ���
mySql18.addSubPara(comCode.substring(0,6)  );//ָ������Ĳ���
mySql18.addSubPara(document.all('AppntName').value  );//ָ������Ĳ���
mySql18.addSubPara(document.all('InsuredName').value  );//ָ������Ĳ���
mySql18.addSubPara(document.all('tempfeeno').value  );//ָ������Ĳ���
mySql18.addSubPara(document.all('RiskCode').value  );//ָ������Ĳ���
strSQL=mySql18.getString();


		  var sqlid19="ProposalQuerySql19";
var mySql19=new SqlClass();
mySql19.setResourceName("sys.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
mySql19.setSqlId(sqlid19);//ָ��ʹ�õ�Sql��id
mySql19.addSubPara(document.all('ContNo').value );//ָ������Ĳ���
mySql19.addSubPara(document.all('GrpContNo').value );//ָ������Ĳ���
mySql19.addSubPara(comCode.substring(0,6) );//ָ������Ĳ���
mySql19.addSubPara(document.all('AgentCode').value );//ָ������Ĳ���
mySql19.addSubPara(document.all('ProposalContNo').value );//ָ������Ĳ���
mySql19.addSubPara(document.all('AppntName').value );//ָ������Ĳ���
mySql19.addSubPara(document.all('CValiDate').value );//ָ������Ĳ���
mySql19.addSubPara(document.all('InsuredNo').value );//ָ������Ĳ���
mySql19.addSubPara(document.all('InsuredName').value );//ָ������Ĳ���
mySql19.addSubPara(document.all('PayLocation').value );//ָ������Ĳ���
mySql19.addSubPara(document.all('AppntIDNo').value );//ָ������Ĳ���
mySql19.addSubPara(comCode.substring(0,6) );//ָ������Ĳ���
mySql19.addSubPara(document.all('tempfeeno').value );//ָ������Ĳ���
mySql19.addSubPara(document.all('RiskCode').value );//ָ������Ĳ���

mySql19.addSubPara(document.all('ContNo').value );//ָ������Ĳ���
mySql19.addSubPara(document.all('GrpContNo').value );//ָ������Ĳ���
mySql19.addSubPara(comCode.substring(0,6) );//ָ������Ĳ���
mySql19.addSubPara(document.all('AgentCode').value );//ָ������Ĳ���
mySql19.addSubPara(document.all('ProposalContNo').value );//ָ������Ĳ���

mySql19.addSubPara(document.all('AppntName').value );//ָ������Ĳ���
mySql19.addSubPara(document.all('CValiDate').value );//ָ������Ĳ���
mySql19.addSubPara(document.all('InsuredNo').value );//ָ������Ĳ���
mySql19.addSubPara(document.all('InsuredName').value );//ָ������Ĳ���
mySql19.addSubPara(document.all('PayLocation').value );//ָ������Ĳ���

mySql19.addSubPara(document.all('AppntIDNo').value );//ָ������Ĳ���
mySql19.addSubPara(comCode.substring(0,6)  );//ָ������Ĳ���
mySql19.addSubPara(document.all('tempfeeno').value );//ָ������Ĳ���
mySql19.addSubPara(document.all('RiskCode').value );//ָ������Ĳ���
strSQL2=mySql19.getString();
		  
//          strSQL = " select distinct ContNo,Prtno,AppntName,GrpContNo,ProposalContNo ,InsuredNo,InsuredName from LCCont a where 1=1 "
//                     +" and grpcontno='00000000000000000000'"
//                     + getWherePart('ContNo','ContNo')
//                     + getWherePart('GrpContNo','GrpContNo')
//                     + getWherePart('ManageCom','ManageCom','like')
//                     + getWherePart('AgentCode','AgentCode')
//                     + getWherePart('ProposalContNo','ProposalContNo')
//                     + getWherePart('CValiDate','CValiDate')
//                     + getWherePart('InsuredNo','InsuredNo')
//                     + getWherePart('PayLocation','PayLocation')
//                     + getWherePart('AppntIDNo','AppntIDNo')
//                     + " and AppFlag in ('1', '4') and ManageCom like '" + comCode.substring(0,6) + "%%' ";
                     
//         strSQL2 = " select b.customerno,'Ͷ����',b.name,decode(b.sex,0,'��','Ů'),b.birthday,"
//                   +"(select codename from ldcode where codetype='idtype' and code=b.idtype),b.idno from ldperson b,LCCont a where b.customerno=a.appntno "
//                     +" and grpcontno='00000000000000000000'"
//                     + getWherePart('ContNo','ContNo')
//                     + getWherePart('GrpContNo','GrpContNo')
//                     + getWherePart('ManageCom','ManageCom','like')
//                     + getWherePart('AgentCode','AgentCode')
//                     + getWherePart('ProposalContNo','ProposalContNo')
//                     + getWherePart('AppntName','AppntName')
//                     + getWherePart('CValiDate','CValiDate')
//                     + getWherePart('InsuredNo','InsuredNo')
//                     + getWherePart('InsuredName','InsuredName')
//                     + getWherePart('PayLocation','PayLocation')
//                     + getWherePart('AppntIDNo','AppntIDNo')
//                     + " and AppFlag in ('1', '4') and ManageCom like '" + comCode.substring(0,6) + "%%' "+strSQL3+strSQLRisk
//                     +"union"
//                     +" select b.customerno,'������',b.name,decode(b.sex,0,'��','Ů'),b.birthday,"
//                     +"(select codename from ldcode where codetype='idtype' and code=b.idtype),b.idno from ldperson b,LCCont a where b.customerno=a.insuredno "
//                     +" and grpcontno='00000000000000000000'"
//                     + getWherePart('CONTNO','ContNo')
//                     + getWherePart('GrpContNo','GrpContNo')
//                     + getWherePart('ManageCom','ManageCom','like')
//                     + getWherePart('AgentCode','AgentCode')
//                     + getWherePart('ProposalContNo','ProposalContNo')
//                     + getWherePart('AppntName','AppntName')
//                     + getWherePart('CValiDate','CValiDate')
//                     + getWherePart('InsuredNo','InsuredNo')
//                     + getWherePart('InsuredName','InsuredName')
//                     + getWherePart('PayLocation','PayLocation')
//                     + getWherePart('AppntIDNo','AppntIDNo')
//                     + " and AppFlag in ('1', '4') and ManageCom like '" + comCode.substring(0,6) + "%%' "+strSQL3+strSQLRisk;
//        if (document.all('AppntName').value !="")
//        {
//            strSQL = strSQL + "and AppntNo in (select CustomerNo from LDPerson where Name= '" + document.all('AppntName').value + "' )";
//        }
//        if (document.all('InsuredName').value !="")
//        {
//            strSQL = strSQL + "and InsuredNo in (select CustomerNo from LDPerson where Name= '" + document.all('InsuredName').value + "' )";
//        }
//        if(document.all('tempfeeno').value!="null"&&document.all('tempfeeno').value!="")
//        {
//              strSQL = strSQL+" and exists (select 'x' from ljtempfee j where j.tempfeeno = '"+document.all('tempfeeno').value+"' and j.otherno = ContNo)";
//              strSQL3 = " and exists (select 'x' from ljtempfee j where j.tempfeeno = '"+document.all('tempfeeno').value+"' and j.otherno = ContNo)";
//        }
//        //XinYQ added on 2007-07-02
//        var sRiskCode;
//        try
//        {
//            sRiskCode = document.getElementsByName("RiskCode")[0].value;
//        }
//        catch (ex) {}
//        if (sRiskCode!= null && sRiskCode.trim() != "")
//        {
//            strSQL += " and exists (select 'x' from LCPol where 1 = 1 and ContNo = a.ContNo and RiskCode = '" + sRiskCode + "') ";
//            strSQLRisk =" and exists (select 'x' from LCPol where 1 = 1 and ContNo = a.ContNo and RiskCode = '" + sRiskCode + "') ";
//        }
    try
    {
        turnPagePolGrid.pageDivName = "divTurnPagePolGrid";
        turnPagePolGrid.queryModal(strSQL, PolGrid);
        
        turnPageCusGrid.pageDivName = "divTurnPageCustomerGrid";
        turnPageCusGrid.queryModal(strSQL2, CustomerGrid);
    }
    catch (ex) {}
}


// ���ݷ��ظ�����
function returnParent()
{
    //alert(tDisplay);
    if (tDisplay=="1")
    {
        var arrReturn = new Array();
        var tSel = PolGrid.getSelNo();
        //alert(tSel);
        if( tSel == 0 || tSel == null )
            alert("����ѡ��һ����¼���ٵ�����ذ�ť�� ");
        else
        {
            try
            {
                arrReturn = getQueryResult();
                top.opener.afterQuery(arrReturn);
                top.close();
            }
            catch(ex)
            {
                alert("����ѡ��һ���ǿռ�¼���ٵ�����ذ�ť�� ");
            }
        }
     }
     else
     {
        top.close();
     }
}

function getQueryResult()
{
    var arrSelected = null;
    var nSelNo = PolGrid.getSelNo() - 1;
    if (nSelNo != null && nSelNo >= 0)
    {
        try
        {
            arrSelected = new Array();
            arrSelected[0] = new Array();
            arrSelected[0][0] = PolGrid.getRowColData(nSelNo, 1);
        }
        catch (ex) {}
    }
    return arrSelected;
}

function queryAgent()
{
    //alert("document.all('AgentCode').value==="+document.all('AgentCode').value);
    //if(document.all('ManageCom').value==""){
    //   alert("����¼����������Ϣ��");
    //   return;
    //}
  if(document.all('AgentCode').value == "")   {
      //var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+",AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
      var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+"&branchtype=1","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    }
    if(document.all('AgentCode').value != ""){
      var cAgentCode = fm.AgentCode.value;  //��������
    //alert("cAgentCode=="+cAgentCode);
    //���ҵ��Ա���볤��Ϊ8���Զ���ѯ����Ӧ�Ĵ������ֵ���Ϣ
    //alert("cAgentCode=="+cAgentCode);
    if(cAgentCode.length!=8){
        return;
    }
      //var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"' and ManageCom = '"+document.all('ManageCom').value+"'";
   
var sqlid20="ProposalQuerySql20";
var mySql20=new SqlClass();
mySql20.setResourceName("sys.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
mySql20.setSqlId(sqlid20);//ָ��ʹ�õ�Sql��id
mySql20.addSubPara(cAgentCode );//ָ������Ĳ���
var strSQL=mySql20.getString();
   
//    var strSQL = "select a.AgentCode,a.AgentGroup,a.ManageCom,a.Name,c.BranchManager,b.IntroAgency,b.AgentSeries,b.AgentGrade,c.BranchAttr,b.AscriptSeries,c.name from LAAgent a,LATree b,LABranchGroup c where 1=1 "
//             + "and a.AgentCode = b.AgentCode and a.branchtype in ('1','4') and a.agentstate!='03' and a.agentstate!='04' and a.AgentGroup = c.AgentGroup and a.AgentCode='"+cAgentCode+"'";
   //alert(strSQL);
    var arrResult = easyExecSql(strSQL);
    //alert(arrResult);
    if (arrResult != null)
    {
        fm.AgentCode.value = arrResult[0][0];
        //fm.BranchAttr.value = arrResult[0][10];
        fm.AgentGroup.value = arrResult[0][1];
      //fm.AgentName.value = arrResult[0][3];
      fm.ManageCom.value = arrResult[0][2];

      //if(fm.AgentManageCom.value != fm.ManageCom.value)
      //{
      //
        //    fm.ManageCom.value = fm.AgentManageCom.value;
        //    fm.ManageComName.value = fm.AgentManageComName.value;
        //    //showCodeName('comcode','ManageCom','AgentManageComName');  //���뺺��
        //
      //}
      //showContCodeName();
      //alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"] ����������Ϊ:["+arrResult[0][1]+"]");
    }
    else
    {
     fm.AgentGroup.value="";
     alert("����Ϊ:["+document.all('AgentCode').value+"]��ҵ��Ա�����ڣ���ȷ��!");
    }
    }
}

function afterQuery2(arrResult)
{

  if(arrResult!=null)
  {
//      fm.AgentCode.value = arrResult[0][0];
//      fm.BranchAttr.value = arrResult[0][93];
//      fm.AgentGroup.value = arrResult[0][1];
//      fm.AgentName.value = arrResult[0][5];
//      fm.AgentManageCom.value = arrResult[0][2];
        fm.AgentCode.value = arrResult[0][0];
        //fm.BranchAttr.value = arrResult[0][10];
        fm.AgentGroup.value = arrResult[0][1];
      //fm.AgentName.value = arrResult[0][3];
      fm.ManageCom.value = arrResult[0][2];

    //showContCodeName();
    //showOneCodeName('comcode','AgentManageCom','ManageComName');
  }
}
//��ʼ��������������ȡ��λ
function initManageCom()
{
    if(comCode.substring(0,6) !=null && comCode.substring(0,6) != "")
    {
        var i,j,m,n;
        var returnstr;
        var tTurnPage = new turnPageClass();
		
		var sqlid21="ProposalQuerySql21";
var mySql21=new SqlClass();
mySql21.setResourceName("sys.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
mySql21.setSqlId(sqlid21);//ָ��ʹ�õ�Sql��id
mySql21.addSubPara(comCode.substring(0,6) );//ָ������Ĳ���
 var strSQLL=mySql21.getString();
		
        //var strSQL = "select comcode,name from ldcom where comcode like '"+comCode.substring(0,6)+"%%'";
        tTurnPage.strQueryResult  = easyQueryVer3(strSQLL, 1, 1, 1);
        if (tTurnPage.strQueryResult == "")
        {
          return "";
        }
        tTurnPage.arrDataCacheSet = decodeEasyQueryResult(tTurnPage.strQueryResult);
        var returnstr = "";
        var n = tTurnPage.arrDataCacheSet.length;
        if (n > 0)
        {
            for( i = 0;i < n; i++)
            {
                m = tTurnPage.arrDataCacheSet[i].length;
                if (m > 0)
                {
                    for( j = 0; j< m; j++)
                    {
                        if (i == 0 && j == 0)
                        {
                            returnstr = "0|^"+tTurnPage.arrDataCacheSet[i][j];
                        }
                        if (i == 0 && j > 0)
                        {
                            returnstr = returnstr + "|" + tTurnPage.arrDataCacheSet[i][j];
                        }
                        if (i > 0 && j == 0)
                        {
                            returnstr = returnstr+"^"+tTurnPage.arrDataCacheSet[i][j];
                        }
                        if (i > 0 && j > 0)
                        {
                            returnstr = returnstr+"|"+tTurnPage.arrDataCacheSet[i][j];
                        }
                    }
                }
                else
                {
                    alert("��ѯʧ��!!");
                    return "";
                }
             }
         }
         else
         {
             alert("��ѯʧ��!");
             return "";
         }
         fm.ManageCom.CodeData = returnstr;
         return returnstr;
    }

}