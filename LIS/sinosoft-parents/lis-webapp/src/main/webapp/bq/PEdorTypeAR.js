//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var GEdorFlag = true;
var selGridFlag = 0;            //��ʶ��ǰѡ�м�¼��GRID
var sqlresourcename = "bq.PEdorTypeARInputSql";
//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage1 = new turnPageClass();
var turnPage = new turnPageClass();           //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var turnPage2 = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var turnPage3 = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
//�ض����ȡ���㴦���¼�
//window.onfocus = initFocus;

//��ѯ��ť
function queryClick()
{
	var showStr = "���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	
////	var strSql="select a.AppntNo,a.PolNo,a.InsuAccNo,b.InsuAccName,(select payplanname from LMDutyPay where payplancode=a.PayPlanCode),a.UnitCount from lcinsureaccclass a,lmriskinsuacc b where a.insuaccno=b.insuaccno and contno='"+fm.ContNo.value+"' and b.AccType='001' order by a.appntno,a.polno,a.insuaccno,a.payplancode";
///////////////////////add by luzhe on 2007-09-17///////////////////////
     /*     var mySql=new SqlClass();
              mySql.setJspName("../../bq/PEdorTypeARInputSql.jsp");
              mySql.setSqlId("PEdorTypeARInputSql_0");
              mySql.addPara(ContNo,fm.ContNo.value);
              */
//              turnPage.queryModal(mySql.getString(), XXXXGrid);
///////////////////////end                 add///////////////////////
        var tSQL = "";
		var sqlid1="PEdorTypeARInputSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(document.all('ContNo').value);//ָ������Ĳ���
		tSQL=mySql1.getString();
		
	turnPage1.pageDivName = "divPage1";
	//turnPage1.queryModal(mySql.getSQL(), LCGrpInsuAccGrid);
	turnPage1.queryModal(tSQL, LCGrpInsuAccGrid);
	showInfo.close();	
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit(FlagStr, content)
{
	showInfo.close();
	if (FlagStr == "Fail")
	{
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
	else
	{ 
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		initLPInsuAccGrid();
	    	QueryBussiness();
	}
}


/*��ѯ������Ϣ*/
function QueryBussiness()
{
////	var strSql=" select outinsuredno,outpolno,outinsuaccno,'',sum(accoutunit),'',max(modifydate),max(modifytime),'' from lpinsuaccout a where edorno='"+fm.EdorNo.value+"' group by outinsuredno,outpolno,outinsuaccno";
///////////////////////add by luzhe on 2007-09-17///////////////////////
      /*    var mySql=new SqlClass();
              mySql.setJspName("../../bq/PEdorTypeARInputSql.jsp");
              mySql.setSqlId("PEdorTypeARInputSql_1");
              mySql.addPara('EdorNo',fm.EdorNo.value);   */
//              mySql.addPara('"' group by outinsuredno,outpolno,outinsuaccno";',"' group by outinsuredno,outpolno,outinsuaccno";);
//              turnPage.queryModal(mySql.getString(), XXXXGrid);
///////////////////////end                 add///////////////////////
        var tSQL = "";
		var sqlid2="PEdorTypeARInputSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(document.all('EdorNo').value);//ָ������Ĳ���
		tSQL=mySql2.getString();
		
	turnPage3.pageDivName = "divPage3";
	//turnPage3.queryModal(mySql.getSQL(), LPInsuAccGrid); 
	turnPage3.queryModal(tSQL, LPInsuAccGrid); 
}

//��ѯ��ť
function queryPol()
{	
	var showStr = "���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	/*
	var strSql="select distinct a.polno,a.riskcode,(select riskname from LMrisk c where c.riskcode=a.riskcode),a.insuredno,a.insuredname,b.sex,b.Birthday,b.idtype,b.idno from lcpol a,lcinsured b,lmriskapp d where a.insuredno=b.insuredno(+) and a.contno='"+fm.ContNo.value+"' and a.riskcode = d.riskcode and d.risktype3 = '3'"
		+ getWherePart('a.PolNo', 'PolNo')
		+ getWherePart('a.InsuredNo', 'InsuredNo')
		+ getWherePart('a.InsuredName', 'InsuredName')
		+ getWherePart('b.IDType', 'IDType')
		+ getWherePart('b.IDNo', 'IDNo')
		+ getWherePart('b.Sex', 'Sex')
		+ getWherePart('b.Birthday', 'Birthday')
		;
///////////////////////add by luzhe on 2007-09-17///////////////////////
          var mySql=new SqlClass();
              mySql.setJspName("../../bq/PEdorTypeARInputSql.jsp");
              mySql.setSqlId("PEdorTypeARInputSql_2");
              mySql.addPara('ContNo',fm.ContNo.value);
              mySql.getWherePart('PolNo');
              mySql.getWherePart('InsuredNo');
              mySql.getWherePart('InsuredName');
              mySql.getWherePart('IDType');
              mySql.getWherePart('IDNo');
              mySql.getWherePart('Sex');
              mySql.getWherePart('Birthday');; */
//              turnPage.queryModal(mySql.getString(), XXXXGrid);
///////////////////////end                 add///////////////////////
		//alert(mySql.getSQL());
		var tSQL = "";
		var sqlid3="PEdorTypeARInputSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
		mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
		mySql3.addSubPara(document.all('ContNo').value);//ָ������Ĳ���
		mySql3.addSubPara(document.all('PolNo').value);//ָ������Ĳ���
		mySql3.addSubPara(document.all('InsuredNo').value);//ָ������Ĳ���
		mySql3.addSubPara(document.all('InsuredName').value);//ָ������Ĳ���
		mySql3.addSubPara(document.all('IDType').value);//ָ������Ĳ���
		mySql3.addSubPara(document.all('IDNo').value);//ָ������Ĳ���
		mySql3.addSubPara(document.all('Sex').value);//ָ������Ĳ���
		mySql3.addSubPara(document.all('Birthday').value);//ָ������Ĳ���
		tSQL=mySql3.getString();
		
	turnPage3.pageDivName = "divPage3";
	//turnPage3.queryModal(mySql.getSQL(), LCInsuAccGrid);
	turnPage3.queryModal(tSQL, LCInsuAccGrid);
	showInfo.close();	 
}

//����MultiLine�ĵ�ѡ��ťʱ����
function reportDetailClick(parm1, parm2)
{	
	fm.ContNo.value = LCInsuredGrid.getRowColData(LCInsuredGrid.getSelNo()-1, 1);
	fm.CustomerNo.value = LCInsuredGrid.getRowColData(LCInsuredGrid.getSelNo()-1, 2);
	//queryGEdorList();
	selGridFlag = 1;
}


//��������˻����
function EdorDetail()
{
  	//����֮ǰ��Ҫѡ�����ת���ı���
	  if (LCInsuAccGrid.getSelNo()==0)
	  {
	    alert("��ѡ����Ҫ�����˻�ת���ı���!");
	    return;
	  }
	  var tPolNo=LCInsuAccGrid.getRowColData(LCInsuAccGrid.getSelNo()-1,1);
	  if(tPolNo==null||tPolNo=='')
	  {
		alert("δ�õ���������!");
		return;	
	  }
	window.open("./PEdorTypeARDetailMain.jsp?ContNo="+fm.ContNo.value+"&PolNo="+tPolNo+"&EdorType="+fm.EdorType.value+"&EdorNo="+fm.EdorNo.value);
}

//�����ȡ�����¼�
function initFocus() 
{
  if (GEdorFlag)
  {   
    GEdorFlag = false;
    queryClick();
    queryGEdorList();
 	selGridFlag = 0;
  }
}

//��ѯ�������ĸ��˱�ȫ�б� �Ѿ�������ȫ��Ŀ�� 
function queryGEdorList() 
{
////	var strSql = "select ContNo, InsuredNo, Name, Sex, Birthday, IDType, IDNo from LcInsured where GrpContNo= '" + document.all('GrpContNo').value + "' and ContNo in "
////			   + " (select ContNo from LPEdorItem where edortype='" + fm.EdorType.value + "' and edorno='" + fm.EdorNo.value + "')";
///////////////////////add by luzhe on 2007-09-17///////////////////////
      /*    var mySql=new SqlClass();
              mySql.setJspName("../../bq/PEdorTypeARInputSql.jsp");
              mySql.setSqlId("PEdorTypeARInputSql_3");
              mySql.addPara('GrpContNo',document.all('GrpContNo').value);
              mySql.addPara('EdorType',document.all('EdorType').value);
              mySql.addPara('EdorNo',document.all('EdorNo').value);*/
//              mySql.addPara('"')";',"')";);
//              turnPage.queryModal(mySql.getString(), XXXXGrid);
///////////////////////end                 add///////////////////////
        var tSQL = "";
		var sqlid4="PEdorTypeARInputSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
		mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
		mySql4.addSubPara(document.all('GrpContNo').value);//ָ������Ĳ���
		mySql4.addSubPara(document.all('EdorType').value);//ָ������Ĳ���
		mySql4.addSubPara(document.all('EdorNo').value);//ָ������Ĳ���
		tSQL=mySql4.getString();
		
	turnPage2.pageDivName = "divPage2";
	turnPage2.queryModal(tSQL, LCInsured2Grid); 	 
	if (selGridFlag == 2)
	{
		//fm.ContNo.value = "";
		fm.CustomerNo.value = "";
		selGridFlag = 0;
	}
}

//����MultiLine�ĵ�ѡ��ťʱ����
function reportDetail2Click(parm1, parm2)
{	
	fm.ContNo.value = LCInsured2Grid.getRowColData(LCInsured2Grid.getSelNo()-1, 1);
	fm.CustomerNo.value = LCInsured2Grid.getRowColData(LCInsured2Grid.getSelNo()-1, 2);
	selGridFlag = 2;
	//queryClick();
}

//���������¸��˱�ȫ
function cancelGEdor()
{
 if (LPInsuAccGrid.getSelNo()==0)
  {
    alert("��ѡ����Ҫ�����Ĺ�������!");
    return;
  }

  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");     
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
var iWidth=550;      //�������ڵĿ��; 
var iHeight=250;     //�������ڵĸ߶�; 
var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

showInfo.focus();
  document.all('Transact').value = "DELETE||PMAIN";
  fm.action = "./PEdorTypeARDetailSubmit.jsp";
  fm.submit();
}

/*********************************************************************
 *  ��ʾfrmSubmit��ܣ���������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showSubmitFrame(cDebug) {
	if( cDebug == "1" )
		parent.fraMain.rows = "0,0,50,82,*";
	else 
		parent.fraMain.rows = "0,0,0,72,*";
}

function returnParent() {
	top.close();
	top.opener.initForm();
	top.opener.focus();

}
function QueryEdorInfo()
{
	var tEdortype=document.all('EdorType').value;
	var tSQL = "";
	if(tEdortype!=null || tEdortype !='')
	{
	/*   var strSQL="select distinct edorcode, edorname from lmedoritem where edorcode = '" + tEdortype + "'";
///////////////////////add by luzhe on 2007-09-17///////////////////////
          var mySql=new SqlClass();
              mySql.setJspName("../../bq/PEdorTypeARInputSql.jsp");
              mySql.setSqlId("PEdorTypeARInputSql_4");
              mySql.addPara('edorcode',tEdortype);  */
//              mySql.addPara('"'";',"'";);
//              turnPage.queryModal(mySql.getString(), XXXXGrid);
///////////////////////end                 add///////////////////////
        
		var sqlid5="PEdorTypeARInputSql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
		mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
		mySql5.addSubPara(document.all('EdorType').value);//ָ������Ĳ���
		tSQL=mySql5.getString();
    }
    else
	{
		alert('δ��ѯ����ȫ������Ŀ��Ϣ��');
	}
	
	var arrSelected = new Array();	
	turnPage.strQueryResult  = easyQueryVer3(tSQL, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
    //try {document.all('EdorType').value = arrSelected[0][0];} catch(ex) { }; 
    try {document.all('EdorTypeName').value = arrSelected[0][1];} catch(ex) { }; 
}
