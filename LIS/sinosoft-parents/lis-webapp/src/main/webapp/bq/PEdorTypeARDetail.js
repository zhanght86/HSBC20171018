//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var GEdorFlag = true;
var selGridFlag = 0;            //��ʶ��ǰѡ�м�¼��GRID
var sqlresourcename = "bq.PEdorTypeARDetailInputSql";

//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage1 = new turnPageClass();
var turnPage = new turnPageClass();           //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var turnPage3 = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
//�ض����ȡ���㴦���¼�
//window.onfocus = initFocus;

//��ѯ��ť
function queryClick()
{
	var tPolNo=document.all('PolNo').value;
	var strSql="";
	var tStartDate="";
////	strSql="select min(startdate) from loaccunitprice where riskcode in(select riskcode from lcpol where polno='"+tPolNo+"') and startdate>='"+fm.EdorItemAppDate.value+"'";
///////////////////////add by luzhe on 2007-09-17///////////////////////
    /*      var mySql=new SqlClass();
              mySql.setJspName("../../bq/PEdorTypeARDetailInputSql.jsp");
              mySql.setSqlId("PEdorTypeARDetailInputSql_0");
              mySql.addPara('tPolNo',tPolNo);
              mySql.addPara('EdorItemAppDate',fm.EdorItemAppDate.value); */
//              mySql.addPara('"'";',"'";);
//              turnPage.queryModal(mySql.getString(), XXXXGrid);
///////////////////////end                 add///////////////////////
  var sqlid1="PEdorTypeARDetailInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(document.all('PolNo').value);//ָ������Ĳ���
	mySql1.addSubPara(document.all('EdorItemAppDate').value);//ָ������Ĳ���
	strSql=mySql1.getString();
	var arrResult=easyExecSql(strSql);
	if(arrResult==null||arrResult=="")
	{
////		strSql="select max(startdate) from loaccunitprice where riskcode in(select riskcode from lcpol where polno='"+tPolNo+"') and startdate<'"+fm.EdorItemAppDate.value+"'";
///////////////////////add by luzhe on 2007-09-17///////////////////////
      /*    var mySql=new SqlClass();
              mySql.setJspName("../../bq/PEdorTypeARDetailInputSql.jsp");
              mySql.setSqlId("PEdorTypeARDetailInputSql_1");
              mySql.addPara('tPolNo',tPolNo);
              mySql.addPara('EdorItemAppDate',fm.EdorItemAppDate.value); */
//              mySql.addPara('"'";',"'";);
//              turnPage.queryModal(mySql.getString(), XXXXGrid);
///////////////////////end                 add///////////////////////
          var tSQL = "";
          var sqlid2="PEdorTypeARDetailInputSql2";
					var mySql2=new SqlClass();
					mySql2.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
					mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
					mySql2.addSubPara(document.all('PolNo').value);//ָ������Ĳ���
					mySql2.addSubPara(document.all('EdorItemAppDate').value);//ָ������Ĳ���
					tSQL=mySql2.getString();
					
		arrResult=easyExecSql(tSQL);
	}
	tStartDate=arrResult[0][0];
	
	//var strSql="select a.InsuredNo,a.PolNo,a.InsuAccNo,b.InsuAccName,a.PayPlanCode,(select payplanname from lmriskaccpay where riskcode=a.riskcode and insuaccno=a.insuaccno and payplancode=a.PayPlanCode),a.UnitCount,a.UnitCount-(select nvl(sum(accoutunit),0) from lpinsuaccout where trim(outpolno)=trim(a.polno) and trim(outinsuaccno)=trim(a.insuaccno) and trim(outpayplancode)=trim(a.payplancode) and state!='0' and edorno in (select edorno from lpedoritem where contno = a.contno and edorstate in ('0','1','2','3'))),(select nvl(sum(accoutunit),0) from lpinsuaccout where trim(outpolno)=trim(a.polno) and trim(outinsuaccno)=trim(a.insuaccno) and trim(outpayplancode)=trim(a.payplancode) and state!='0' and edorno in (select edorno from lpedoritem where contno = a.contno and edorstate in ('0','1','2','3'))),nvl((select UnitPriceSell from loaccunitprice where riskcode=a.riskcode and insuaccno=a.insuaccno and startdate='"+tStartDate+"'),0) from lcinsureaccclass a,lmriskinsuacc b where a.insuaccno=b.insuaccno and PolNo='"+fm.PolNo.value+"' and b.acctype='002' order by a.appntno,a.polno,a.insuaccno,a.payplancode";
////	  var strSql="select a.InsuredNo,a.PolNo,a.InsuAccNo,b.InsuAccName,a.UnitCount,a.UnitCount-(select nvl(sum(accoutunit),0) from lpinsuaccout where trim(outpolno)=trim(a.polno) and trim(outinsuaccno)=trim(a.insuaccno) and state!='0' and edorno in (select edorno from lpedoritem where contno = a.contno and edorstate in ('0','1','2','3'))),(select nvl(sum(accoutunit),0) from lpinsuaccout where trim(outpolno)=trim(a.polno) and trim(outinsuaccno)=trim(a.insuaccno) and state!='0' and edorno in (select edorno from lpedoritem where contno = a.contno and edorstate in ('0','1','2','3'))),nvl((select UnitPriceSell from loaccunitprice where riskcode=a.riskcode and insuaccno=a.insuaccno and startdate='"+tStartDate+"'),0) from lcinsureacc a,lmriskinsuacc b where a.insuaccno=b.insuaccno and PolNo='"+fm.PolNo.value+"' and b.acctype='002' order by a.appntno,a.polno,a.insuaccno";
///////////////////////add by luzhe on 2007-09-17///////////////////////
      /*    var mySql2=new SqlClass();
              mySql2.setJspName("../../bq/PEdorTypeARDetailInputSql.jsp");
              mySql2.setSqlId("PEdorTypeARDetailInputSql_2");
              mySql2.addPara('tStartDate',tStartDate);
              mySql2.addPara('PolNo',fm.PolNo.value); */
//              turnPage.queryModal(mySql.getString(), XXXXGrid);
///////////////////////end                 add///////////////////////

          var sqlid3="PEdorTypeARDetailInputSql3";
					var mySql3=new SqlClass();
					mySql3.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
					mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
					mySql3.addSubPara(tStartDate);//ָ������Ĳ���
					mySql3.addSubPara(document.all('PolNo').value);//ָ������Ĳ���
					

	turnPage1.pageDivName = "divPage1";
	turnPage1.queryModal(mySql3.getString(), LCGrpInsuAccGrid);
	
	
	//�ж���ԥ��
//	arrResult = easyExecSql(strSql);
//	var strpolno = arrResult[0][1];
//	
//	
//	var SQL = "select a.hesitateend,c.CustomGetPolDate from lmedorwt a,lcpol b,lccont c where b.polno = '"+strpolno+"' and c.contno = b.contno and a.riskcode = b.riskcode ";
//	var result = easyExecSql(SQL);
//	var datecount = result[0][0];
//	var CustomGetPolDate = result[0][1];
//	
//	var EdorAcceptNo = document.all('EdorAcceptNo').value;
//	var SQL2 = "select EdorValiDate from lpedoritem where EdorAcceptNo = '"+EdorAcceptNo+"'";
//	var result = easyExecSql(SQL2);
//	var EdorValiDate = result[0][0];
//	//alert(CustomGetPolDate);
//	
//	if(dateDiff(CustomGetPolDate,EdorValiDate,'D')-10>=0)
//				{
//					
//					}
//				else
//					{				
//				document.all("userinputbutton").style.display = "none";
//				alert("��ԥ���ڲ������");
//			}
//	
	//strSql="select a.outinsuredno,a.outpolno,a.outinsuaccno,a.outpayplancode,a.accoutunit,a.accoutbala,a.modifydate,a.modifytime,a.serialno from lpinsuaccout a where a.edorno='"+fm.EdorNo.value+"' and a.contno='"+fm.ContNo.value+"' and a.edortype='"+fm.EdorType.value+"' order by a.serialno ";
////	  strSql=" select outinsuredno,outpolno,outinsuaccno,'',sum(accoutunit),'',max(modifydate),max(modifytime),'' from lpinsuaccout a where edorno='"+fm.EdorNo.value+"' group by outinsuredno,outpolno,outinsuaccno";
///////////////////////add by luzhe on 2007-09-17///////////////////////
    /*      var mySql3=new SqlClass();
              mySql3.setJspName("../../bq/PEdorTypeARDetailInputSql.jsp");
              mySql3.setSqlId("PEdorTypeARDetailInputSql_3");  
              mySql3.addPara('EdorNo',fm.EdorNo.value);  */
//              mySql.addPara('"' group by outinsuredno,outpolno,outinsuaccno";',"' group by outinsuredno,outpolno,outinsuaccno";);
//              turnPage.queryModal(mySql.getString(), XXXXGrid);
///////////////////////end                 add///////////////////////
          var sqlid4="PEdorTypeARDetailInputSql4";
					var mySql4=new SqlClass();
					mySql4.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
					mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
					mySql4.addSubPara(document.all('EdorNo').value);//ָ������Ĳ���
					
	turnPage3.pageDivName = "divPage3";
	turnPage3.queryModal(mySql4.getString(), LPInsuAccGrid); 
}

//�����˻����
function GEdorRDDetail()
{
  if (LCGrpInsuAccGrid.getSelNo()==0)
  {
    alert("��ѡ������˻�!");
    return;
  }
  //ת����λ��
  try {
  	var OutUnit=LCGrpInsuAccGrid.getRowColData(LCGrpInsuAccGrid.getSelNo()-1,9);
  	var OutBala=LCGrpInsuAccGrid.getRowColData(LCGrpInsuAccGrid.getSelNo()-1,10);
  	//alert(OutUnit+"222"+OutBala);
	  if((OutUnit==null||OutUnit=='')&&(OutBala==null||OutBala==''))
	  {
		alert("��������ص�λ����ؽ��!");
		return;	
	}else if(OutUnit-100<0&&OutUnit!=LCGrpInsuAccGrid.getRowColData(LCGrpInsuAccGrid.getSelNo()-1,6))
		{
			alert("ÿ����ȡ��Ͷ�ʵ�λ����������100!");
			//alert(LCGrpInsuAccGrid.getRowColData(LCGrpInsuAccGrid.getSelNo()-1,8));
			return false;
		}else if(OutUnit%100!=0&&OutUnit!=LCGrpInsuAccGrid.getRowColData(LCGrpInsuAccGrid.getSelNo()-1,6))
			{
				alert("ÿ����ȡ��Ͷ�ʵ�λ������Ϊ100��������!");
				return false;
				}
			else if(OutUnit -0<= 0)
				{
					alert("������ȡ0����0�����µ�λ!");
				return false;
					}
				else if(OutUnit-LCGrpInsuAccGrid.getRowColData(LCGrpInsuAccGrid.getSelNo()-1,6)>0)
					{
						alert("��ȡ��λ�����ܴ��ڿ���ص�λ��!");
				    return false;
						}
  } catch(ex) { 
  	alert("��������ȷ��λ��!");
	return;	
  	}
  //return false;
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
  document.all('Transact').value = "INSERT||PMAIN";
  fm.action = "./PEdorTypeARDetailSubmit.jsp";
  fm.submit();
}

//�������˱�ȫ
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


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit(FlagStr, content, Result)
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
		initLCGrpInsuAccGrid();
    		initLPInsuAccGrid();
		queryClick();
	}
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
	top.opener.initLPInsuAccGrid();
	top.opener.QueryBussiness();
}
function QueryEdorInfo()
{
	var tEdortype=document.all('EdorType').value
	var strSQL ="";
	if(tEdortype!=null || tEdortype !='')
	{
	  /*  strSQL="select distinct edorcode, edorname from lmedoritem where edorcode = '" + tEdortype + "'";
///////////////////////add by luzhe on 2007-09-17///////////////////////
          var mySql=new SqlClass();
              mySql.setJspName("../../bq/PEdorTypeARDetailInputSql.jsp");
              mySql.setSqlId("PEdorTypeARDetailInputSql_4");
              mySql.addPara('tEdortype',tEdortype); */
//              mySql.addPara('"'";',"'";);
//              turnPage.queryModal(mySql.getString(), XXXXGrid);
///////////////////////end                 add///////////////////////
          var sqlid5="PEdorTypeARDetailInputSql5";
					var mySql5=new SqlClass();
					mySql5.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
					mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
					mySql5.addSubPara(tEdortype);//ָ������Ĳ���

					strSQL = mySql5.getString();
    }
    else
	{
		alert('δ��ѯ����ȫ������Ŀ��Ϣ��');
	}
	
	var arrSelected = new Array();	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
    //try {document.all('EdorType').value = arrSelected[0][0];} catch(ex) { }; 
    try {document.all('EdorTypeName').value = arrSelected[0][1];} catch(ex) { }; 
}
