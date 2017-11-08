//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var tDisplay;
var turnPage = new turnPageClass();
var turnPagePolGrid = new turnPageClass();
var turnPageCusGrid = new turnPageClass();
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";

// 数据返回父窗口
function getQueryDetail()
{
    var arrReturn = new Array();
    var tSel = PolGrid.getSelNo();

    if( tSel == 0 || tSel == null )
        alert( "请先选择一条记录。" );
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
                //销户保单查询
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
                alert("保单类型传输错误!");
                return;
            }
        }
 }
}

//销户保单的查询函数
function getQueryDetail_B()
{
    var arrReturn = new Array();
    var tSel = PolGrid.getSelNo();

    if( tSel == 0 || tSel == null )
        alert( "请先选择一条记录。" );
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

// 保单查询
function PolClick()
{
    var arrReturn = new Array();
    var tSel = PolGrid.getSelNo();
    if( tSel == 0 || tSel == null )
        alert( "请先选择一条记录，再点击明细按钮。" );
    else
    {
        var cContNo = PolGrid.getRowColData(tSel - 1,1);
        if (cContNo == "")
            return;
            //alert("cContNo="+cContNo);
                OpenWindowNew("../sys/PolDetailQueryMain.jsp?ContNo=" + cContNo +"&IsCancelPolFlag=0","保单明细","left");
        //window.open("../sys/PolDetailQueryMain.jsp?ContNo=" + cContNo +"&IsCancelPolFlag=0","保单明细",'top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    }
}

// 快速查询按钮÷
function quickQueryClick()
{
    //判断是否有查询条件
    if(document.all('IdCardNo').value =="" && document.all('CustomerNo').value ==""&&document.all('PrtNo').value=="")
    {
        alert("请至少输入证件号码、客户号和印刷号中的一个！");
        document.all('IdCardNo').focus();
      return;
    }
    var strsql="";
    var strsql2="";
    if(document.all('IdCardNo').value =="" && document.all('CustomerNo').value ==""&&document.all('PrtNo').value!=""){
      //只通过印刷号查询合信息
	  
	  var sqlid10="ProposalQuerySql10";
var mySql10=new SqlClass();
mySql10.setResourceName("sys.ProposalQuerySql"); //指定使用的properties文件名
mySql10.setSqlId(sqlid10);//指定使用的Sql的id
mySql10.addSubPara(document.all('PrtNo').value );//指定传入的参数
strsql=mySql10.getString();

	  var sqlid11="ProposalQuerySql11";
var mySql11=new SqlClass();
mySql11.setResourceName("sys.ProposalQuerySql"); //指定使用的properties文件名
mySql11.setSqlId(sqlid11);//指定使用的Sql的id
mySql11.addSubPara(document.all('PrtNo').value );//指定传入的参数
mySql11.addSubPara(document.all('PrtNo').value );//指定传入的参数
strsql2=mySql11.getString();
	  
//      strsql="select ContNo,Prtno,AppntName,GrpContNo,ProposalContNo,InsuredNo,InsuredName from LCCont where PrtNo='" + document.all('PrtNo').value + "'";
//      strsql2="select a.customerno,'投保人',a.name,decode(a.sex,0,'男','女'),a.birthday,"
//      +"(select codename from ldcode where codetype='idtype' and code=a.idtype),a.idno from ldperson a,lccont b where a.customerno=b.appntno and b.prtno='" + document.all('PrtNo').value + "' "
//      +" union "
//      +"select a.customerno,'被保人',a.name,decode(a.sex,0,'男','女'),a.birthday,"
//      +"(select codename from ldcode where codetype='idtype' and code=a.idtype),a.idno from ldperson a,lccont b where a.customerno=b.insuredno and b.prtno='" + document.all('PrtNo').value + "' "
//       ;
    }
    else
    {
	    if(document.all('IdCardNo').value !="" && document.all('CustomerNo').value =="")
	    {
			
var sqlid12="ProposalQuerySql12";
var mySql12=new SqlClass();
mySql12.setResourceName("sys.ProposalQuerySql"); //指定使用的properties文件名
mySql12.setSqlId(sqlid12);//指定使用的Sql的id
mySql12.addSubPara(document.all('IdCardNo').value );//指定传入的参数
mySql12.addSubPara(document.all('PrtNo').value );//指定传入的参数
mySql12.addSubPara(document.all('IdCardNo').value );//指定传入的参数
mySql12.addSubPara(document.all('PrtNo').value );//指定传入的参数
mySql12.addSubPara(document.all('IdCardNo').value );//指定传入的参数
mySql12.addSubPara(document.all('PrtNo').value );//指定传入的参数
strsql=mySql12.getString();

var sqlid13="ProposalQuerySql13";
var mySql13=new SqlClass();
mySql13.setResourceName("sys.ProposalQuerySql"); //指定使用的properties文件名
mySql13.setSqlId(sqlid13);//指定使用的Sql的id
mySql13.addSubPara(document.all('IdCardNo').value );//指定传入的参数

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
//	       strsql2="select a.customerno,'',a.name,decode(a.sex,0,'男','女'),a.birthday,(select codename from ldcode where codetype='idtype' and code=a.idtype),a.idno "
//             +" from ldperson a where a.idno='" + document.all('IdCardNo').value + "' ";
	    }
	    if(document.all('IdCardNo').value =="" && document.all('CustomerNo').value !="")
	    {
			
			var sqlid14="ProposalQuerySql14";
var mySql14=new SqlClass();
mySql14.setResourceName("sys.ProposalQuerySql"); //指定使用的properties文件名
mySql14.setSqlId(sqlid14);//指定使用的Sql的id
mySql14.addSubPara(document.all('CustomerNo').value );//指定传入的参数
mySql14.addSubPara(document.all('PrtNo').value );//指定传入的参数
mySql14.addSubPara(document.all('CustomerNo').value );//指定传入的参数
mySql14.addSubPara(document.all('PrtNo').value );//指定传入的参数
strsql=mySql14.getString();

var sqlid15="ProposalQuerySql15";
var mySql15=new SqlClass();
mySql15.setResourceName("sys.ProposalQuerySql"); //指定使用的properties文件名
mySql15.setSqlId(sqlid15);//指定使用的Sql的id
mySql15.addSubPara(document.all('CustomerNo').value );//指定传入的参数
strsql2=mySql15.getString();
			
//	        strsql=" select ContNo,Prtno,AppntName,GrpContNo,ProposalContNo,InsuredNo,InsuredName from LCCont where AppntNo='" + document.all('CustomerNo').value + "'"
//	        	  +getWherePart('PrtNo','PrtNo')
//	              +" union"
//	              +" select ContNo,Prtno,AppntName,GrpContNo,ProposalContNo,InsuredNo,InsuredName from LCCont where InsuredNo='" + document.all('CustomerNo').value + "'"
//	              +getWherePart('PrtNo','PrtNo')
//	              +"";
//	        strsql2="select a.customerno,'',a.name,decode(a.sex,0,'男','女'),a.birthday,(select codename from ldcode where codetype='idtype' and code=a.idtype),a.idno "
//             +" from ldperson a where a.customerno='" + document.all('CustomerNo').value + "' ";
	    }
	    if(document.all('IdCardNo').value !="" && document.all('CustomerNo').value !="")
	    {
			
var sqlid16="ProposalQuerySql16";
var mySql16=new SqlClass();
mySql16.setResourceName("sys.ProposalQuerySql"); //指定使用的properties文件名
mySql16.setSqlId(sqlid16);//指定使用的Sql的id
mySql16.addSubPara(document.all('IdCardNo').value );//指定传入的参数
mySql16.addSubPara(document.all('PrtNo').value );//指定传入的参数
mySql16.addSubPara(document.all('CustomerNo').value );//指定传入的参数

mySql16.addSubPara(document.all('PrtNo').value );//指定传入的参数
mySql16.addSubPara(document.all('CustomerNo').value );//指定传入的参数
mySql16.addSubPara(document.all('PrtNo').value );//指定传入的参数
strsql=mySql16.getString();

var sqlid17="ProposalQuerySql17";
var mySql17=new SqlClass();
mySql17.setResourceName("sys.ProposalQuerySql"); //指定使用的properties文件名
mySql17.setSqlId(sqlid17);//指定使用的Sql的id
mySql17.addSubPara(document.all('CustomerNo').value );//指定传入的参数
mySql17.addSubPara(document.all('IdCardNo').value );//指定传入的参数
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
//	        strsql2="select a.customerno,'',a.name,decode(a.sex,0,'男','女'),a.birthday,(select codename from ldcode where codetype='idtype' and code=a.idtype),a.idno "
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

// 查询按钮
function easyQueryClick()
{
    //判断是否有查询条件
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
            alert("查询条件不足！请至少输入以下号码之一：保单号码、投保单号码、划款协议书号。\n\n如无法提供上述号码请输入至少三条数据作为查询条件！");
            document.all('ContNo').focus();
            return;
        }

          // 书写SQL语句
          var strSQL = "";
          var strSQL2 = "";
          var strSQL3 = "";
          var strSQLRisk="";
		  
		  var sqlid18="ProposalQuerySql18";
var mySql18=new SqlClass();
mySql18.setResourceName("sys.ProposalQuerySql"); //指定使用的properties文件名
mySql18.setSqlId(sqlid18);//指定使用的Sql的id
mySql18.addSubPara(document.all('ContNo').value );//指定传入的参数
mySql18.addSubPara(document.all('GrpContNo').value );//指定传入的参数
mySql18.addSubPara(document.all('ManageCom').value );//指定传入的参数
mySql18.addSubPara(document.all('AgentCode').value );//指定传入的参数
mySql18.addSubPara(document.all('ProposalContNo').value );//指定传入的参数
mySql18.addSubPara(document.all('CValiDate').value );//指定传入的参数

mySql18.addSubPara(document.all('InsuredNo').value );//指定传入的参数
mySql18.addSubPara(document.all('PayLocation').value );//指定传入的参数
mySql18.addSubPara(document.all('AppntIDNo').value );//指定传入的参数
mySql18.addSubPara(comCode.substring(0,6)  );//指定传入的参数
mySql18.addSubPara(document.all('AppntName').value  );//指定传入的参数
mySql18.addSubPara(document.all('InsuredName').value  );//指定传入的参数
mySql18.addSubPara(document.all('tempfeeno').value  );//指定传入的参数
mySql18.addSubPara(document.all('RiskCode').value  );//指定传入的参数
strSQL=mySql18.getString();


		  var sqlid19="ProposalQuerySql19";
var mySql19=new SqlClass();
mySql19.setResourceName("sys.ProposalQuerySql"); //指定使用的properties文件名
mySql19.setSqlId(sqlid19);//指定使用的Sql的id
mySql19.addSubPara(document.all('ContNo').value );//指定传入的参数
mySql19.addSubPara(document.all('GrpContNo').value );//指定传入的参数
mySql19.addSubPara(comCode.substring(0,6) );//指定传入的参数
mySql19.addSubPara(document.all('AgentCode').value );//指定传入的参数
mySql19.addSubPara(document.all('ProposalContNo').value );//指定传入的参数
mySql19.addSubPara(document.all('AppntName').value );//指定传入的参数
mySql19.addSubPara(document.all('CValiDate').value );//指定传入的参数
mySql19.addSubPara(document.all('InsuredNo').value );//指定传入的参数
mySql19.addSubPara(document.all('InsuredName').value );//指定传入的参数
mySql19.addSubPara(document.all('PayLocation').value );//指定传入的参数
mySql19.addSubPara(document.all('AppntIDNo').value );//指定传入的参数
mySql19.addSubPara(comCode.substring(0,6) );//指定传入的参数
mySql19.addSubPara(document.all('tempfeeno').value );//指定传入的参数
mySql19.addSubPara(document.all('RiskCode').value );//指定传入的参数

mySql19.addSubPara(document.all('ContNo').value );//指定传入的参数
mySql19.addSubPara(document.all('GrpContNo').value );//指定传入的参数
mySql19.addSubPara(comCode.substring(0,6) );//指定传入的参数
mySql19.addSubPara(document.all('AgentCode').value );//指定传入的参数
mySql19.addSubPara(document.all('ProposalContNo').value );//指定传入的参数

mySql19.addSubPara(document.all('AppntName').value );//指定传入的参数
mySql19.addSubPara(document.all('CValiDate').value );//指定传入的参数
mySql19.addSubPara(document.all('InsuredNo').value );//指定传入的参数
mySql19.addSubPara(document.all('InsuredName').value );//指定传入的参数
mySql19.addSubPara(document.all('PayLocation').value );//指定传入的参数

mySql19.addSubPara(document.all('AppntIDNo').value );//指定传入的参数
mySql19.addSubPara(comCode.substring(0,6)  );//指定传入的参数
mySql19.addSubPara(document.all('tempfeeno').value );//指定传入的参数
mySql19.addSubPara(document.all('RiskCode').value );//指定传入的参数
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
                     
//         strSQL2 = " select b.customerno,'投保人',b.name,decode(b.sex,0,'男','女'),b.birthday,"
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
//                     +" select b.customerno,'被保人',b.name,decode(b.sex,0,'男','女'),b.birthday,"
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


// 数据返回父窗口
function returnParent()
{
    //alert(tDisplay);
    if (tDisplay=="1")
    {
        var arrReturn = new Array();
        var tSel = PolGrid.getSelNo();
        //alert(tSel);
        if( tSel == 0 || tSel == null )
            alert("请先选择一条记录，再点击返回按钮！ ");
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
                alert("请先选择一条非空记录，再点击返回按钮！ ");
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
    //   alert("请先录入管理机构信息！");
    //   return;
    //}
  if(document.all('AgentCode').value == "")   {
      //var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+",AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
      var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+"&branchtype=1","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    }
    if(document.all('AgentCode').value != ""){
      var cAgentCode = fm.AgentCode.value;  //保单号码
    //alert("cAgentCode=="+cAgentCode);
    //如果业务员代码长度为8则自动查询出相应的代码名字等信息
    //alert("cAgentCode=="+cAgentCode);
    if(cAgentCode.length!=8){
        return;
    }
      //var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"' and ManageCom = '"+document.all('ManageCom').value+"'";
   
var sqlid20="ProposalQuerySql20";
var mySql20=new SqlClass();
mySql20.setResourceName("sys.ProposalQuerySql"); //指定使用的properties文件名
mySql20.setSqlId(sqlid20);//指定使用的Sql的id
mySql20.addSubPara(cAgentCode );//指定传入的参数
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
        //    //showCodeName('comcode','ManageCom','AgentManageComName');  //代码汉化
        //
      //}
      //showContCodeName();
      //alert("查询结果:  代理人编码:["+arrResult[0][0]+"] 代理人名称为:["+arrResult[0][1]+"]");
    }
    else
    {
     fm.AgentGroup.value="";
     alert("编码为:["+document.all('AgentCode').value+"]的业务员不存在，请确认!");
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
//初始化管理机构，最长截取六位
function initManageCom()
{
    if(comCode.substring(0,6) !=null && comCode.substring(0,6) != "")
    {
        var i,j,m,n;
        var returnstr;
        var tTurnPage = new turnPageClass();
		
		var sqlid21="ProposalQuerySql21";
var mySql21=new SqlClass();
mySql21.setResourceName("sys.ProposalQuerySql"); //指定使用的properties文件名
mySql21.setSqlId(sqlid21);//指定使用的Sql的id
mySql21.addSubPara(comCode.substring(0,6) );//指定传入的参数
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
                    alert("查询失败!!");
                    return "";
                }
             }
         }
         else
         {
             alert("查询失败!");
             return "";
         }
         fm.ManageCom.CodeData = returnstr;
         return returnstr;
    }

}