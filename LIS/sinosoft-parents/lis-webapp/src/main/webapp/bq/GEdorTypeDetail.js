//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var GEdorFlag = true;
var selGridFlag = 0;            //��ʶ��ǰѡ�м�¼��GRID

//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage1 = new turnPageClass();
var turnPage = new turnPageClass();           //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var turnPage2 = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
//�ض����ȡ���㴦���¼�
//window.onfocus = initFocus;

//��ѯ��ť
function queryClick()
{
//		var showStr = "���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
//		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//		showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	  var strSql   ="";
	  var tEdorType = fm.EdorType.value;
	  //���������Ҫֻ��ʾ�����������ļ�¼ modify by wanzh 2006-07-29
	  if(tEdorType == "AG")
	  {
	  	  //strSql = "select ContNo, InsuredNo, Name, Sex, Birthday, IDType, decode(IDType,'0',decode(length(IDNo),15,trans1(IDNo),IDNo),IDNo) from LCInsured where GrpContNo='" 
	  	  
	  	  var sqlid830102900="DSHomeContSql830102900";
var mySql830102900=new SqlClass();
mySql830102900.setResourceName("bq.GEdorTypeDetailSql");//ָ��ʹ�õ�properties�ļ���
mySql830102900.setSqlId(sqlid830102900);//ָ��ʹ�õ�Sql��id
mySql830102900.addSubPara(fm.GrpContNo.value);//ָ������Ĳ���
mySql830102900.addSubPara(fm.EdorType.value);//ָ������Ĳ���
mySql830102900.addSubPara(fm.EdorNo.value);//ָ������Ĳ���
mySql830102900.addSubPara(fm.GrpContNo.value);//ָ������Ĳ���
mySql830102900.addSubPara(fm.GrpContNo.value);//ָ������Ĳ���
mySql830102900.addSubPara(fm.ContNo2.value);//ָ������Ĳ���
mySql830102900.addSubPara(fm.CustomerNo2.value);//ָ������Ĳ���
mySql830102900.addSubPara(fm.Name2.value);//ָ������Ĳ���
strSql=mySql830102900.getString();

	  	  
//	  	  strSql = "select ContNo, InsuredNo, Name, Sex, Birthday, IDType, IDNo from LCInsured where GrpContNo='" 
//			   + fm.GrpContNo.value + "'" 
//			   + " and ContNo not in (select ContNo from LPEdorItem where edortype='" + fm.EdorType.value + "' and edorno='" + fm.EdorNo.value + "')"
//			   + " and ContNo in (select ContNo from LCpol where appflag='1' and GrpContNo='" + fm.GrpContNo.value + "')"
//			   + " and ContNo in (select ContNo from ljsgetdraw where GrpContNo='" + fm.GrpContNo.value + "')"
//			   + getWherePart('ContNo', 'ContNo2')
//			   + getWherePart('InsuredNo', 'CustomerNo2')
//			   + getWherePart('Name', 'Name2', 'like', '0');
	  }else if (tEdorType == "AC"||tEdorType == "BB" ||tEdorType == "BC" ||tEdorType == "IC")
		{
		    //strSql = "select ContNo, InsuredNo, Name, Sex, Birthday, IDType,  decode(IDType,'0',decode(length(IDNo),15,trans1(IDNo),IDNo),IDNo)  from LCInsured where GrpContNo='" 
		    
		    var sqlid830103326="DSHomeContSql830103326";
var mySql830103326=new SqlClass();
mySql830103326.setResourceName("bq.GEdorTypeDetailSql");//ָ��ʹ�õ�properties�ļ���
mySql830103326.setSqlId(sqlid830103326);//ָ��ʹ�õ�Sql��id
mySql830103326.addSubPara(fm.GrpContNo.value);//ָ������Ĳ���
mySql830103326.addSubPara(fm.EdorType.value);//ָ������Ĳ���
mySql830103326.addSubPara(fm.EdorNo.value);//ָ������Ĳ���
mySql830103326.addSubPara(fm.GrpContNo.value);//ָ������Ĳ���
mySql830103326.addSubPara(fm.ContNo2.value);//ָ������Ĳ���
mySql830103326.addSubPara(fm.CustomerNo2.value);//ָ������Ĳ���
mySql830103326.addSubPara(fm.Name2.value);//ָ������Ĳ���
strSql=mySql830103326.getString();
		    
//		    strSql = "select ContNo, InsuredNo, Name, Sex, Birthday, IDType, IDNo from LCInsured where GrpContNo='" 
//				   + fm.GrpContNo.value + "'" 
//				   + " and not exists (select 1 from LPEdorItem where edortype='" + fm.EdorType.value + "' and edorno='" + fm.EdorNo.value + "' and ContNo = LCInsured.ContNo)"
//				   + " and not exists (select 1 from LPEdorItem where edortype in ('GT','XT','ZT','AT','AX','WT','AZ') and ContNo = LCInsured.ContNo and edorstate = '0')"
//				   + " and exists (select 1 from LCpol where GrpContNo='" + fm.GrpContNo.value + "' and ContNo = LCInsured.ContNo and InsuredNo = LCInsured.InsuredNo)"
//				   + getWherePart('ContNo', 'ContNo2')
//				   + getWherePart('InsuredNo', 'CustomerNo2')
//				   + getWherePart('Name', 'Name2', 'like', '0');
	  }else
		{
		   // strSql = "select ContNo, InsuredNo, Name, Sex, Birthday, IDType,  decode(IDType,'0',decode(length(IDNo),15,trans1(IDNo),IDNo),IDNo)  from LCInsured where GrpContNo='" 
				  
				  var sqlid830103713="DSHomeContSql830103713";
var mySql830103713=new SqlClass();
mySql830103713.setResourceName("bq.GEdorTypeDetailSql");//ָ��ʹ�õ�properties�ļ���
mySql830103713.setSqlId(sqlid830103713);//ָ��ʹ�õ�Sql��id
mySql830103713.addSubPara(fm.GrpContNo.value);//ָ������Ĳ���
mySql830103713.addSubPara(fm.EdorType.value);//ָ������Ĳ���
mySql830103713.addSubPara(fm.EdorNo.value);//ָ������Ĳ���
mySql830103713.addSubPara(fm.GrpContNo.value);//ָ������Ĳ���
mySql830103713.addSubPara(fm.ContNo2.value);//ָ������Ĳ���
mySql830103713.addSubPara(fm.CustomerNo2.value);//ָ������Ĳ���
mySql830103713.addSubPara(fm.Name2.value);//ָ������Ĳ���
strSql=mySql830103713.getString();
				  
//				  strSql = "select ContNo, InsuredNo, Name, Sex, Birthday, IDType, IDNo from LCInsured where GrpContNo='" 
//				   + fm.GrpContNo.value + "'" 
//				   + " and not exists (select 1 from LPEdorItem where edortype='" + fm.EdorType.value + "' and edorno='" + fm.EdorNo.value + "' and ContNo = LCInsured.ContNo)"
//				   + " and exists (select 1 from LCpol where appflag='1' and GrpContNo='" + fm.GrpContNo.value + "' and ContNo = LCInsured.ContNo and InsuredNo = LCInsured.InsuredNo)"
//				   + getWherePart('ContNo', 'ContNo2')
//				   + getWherePart('InsuredNo', 'CustomerNo2')
//				   + getWherePart('Name', 'Name2', 'like', '0');
	  }	 
	  

		turnPage1.queryModal(strSql, LCInsuredGrid);
		if (selGridFlag == 1)
		{
			fm.CustomerNo.value = "";
			selGridFlag = 0;
		}
		//showInfo.close();	 
}

//��ѯ��ť
function queryClick2()
{
//	var showStr = "���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
//	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//	showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   

	//var strSql = "select ContNo, InsuredNo, Name, Sex, Birthday, IDType,  decode(IDType,'0',decode(length(IDNo),15,trans1(IDNo),IDNo),IDNo)  from lcinsured where ContNo in "
	
	var sqlid830104018="DSHomeContSql830104018";
var mySql830104018=new SqlClass();
mySql830104018.setResourceName("bq.GEdorTypeDetailSql");//ָ��ʹ�õ�properties�ļ���
mySql830104018.setSqlId(sqlid830104018);//ָ��ʹ�õ�Sql��id
mySql830104018.addSubPara(fm.EdorType.value);//ָ������Ĳ���
mySql830104018.addSubPara(fm.EdorNo.value);//ָ������Ĳ���
mySql830104018.addSubPara(fm.GrpContNo.value);//ָ������Ĳ���
mySql830104018.addSubPara(fm.ContNo3.value);//ָ������Ĳ���
mySql830104018.addSubPara(fm.CustomerNo3.value);//ָ������Ĳ���
mySql830104018.addSubPara(fm.Name3.value);//ָ������Ĳ���
var strSql=mySql830104018.getString();
	
//	var strSql = "select ContNo, InsuredNo, Name, Sex, Birthday, IDType, IDNo  from lcinsured where ContNo in "
//			   + " (select ContNo from LPEdorItem where edortype='" + fm.EdorType.value 
//			   + "' and edorno='" + fm.EdorNo.value + "')"
//			   + " and exists (select 1 from LCpol where GrpContNo='" + fm.GrpContNo.value + "' and ContNo = LCInsured.ContNo and InsuredNo = LCInsured.InsuredNo)"
//			   + getWherePart('ContNo', 'ContNo3')
//			   + getWherePart('InsuredNo', 'CustomerNo3')
//			   + getWherePart('Name', 'Name3', 'like', '0');

	turnPage2.pageDivName = "divPage2";
	turnPage2.queryModal(strSql, LCInsured2Grid);
	if (selGridFlag == 2)
	{
		//fm.ContNo.value = "";
		fm.CustomerNo.value = "";
		selGridFlag = 0;
	}

	//showInfo.close();	 
}

//����MultiLine�ĵ�ѡ��ťʱ����
function reportDetailClick(parm1, parm2)
{	
	fm.ContNo.value = LCInsuredGrid.getRowColData(LCInsuredGrid.getSelNo()-1, 1);
	fm.CustomerNo.value = LCInsuredGrid.getRowColData(LCInsuredGrid.getSelNo()-1, 2);
	//queryGEdorList();
	selGridFlag = 1;
}

//������˱�ȫ
function GEdorDetail()
{
	
	if (selGridFlag == 0)
	{
		alert("����ѡ��һ�������ˣ�");
		return;
	}
	//alert(fm.EdorType.value);
	var tEdorType = fm.EdorType.value;
	if(tEdorType == "BB"||tEdorType == "IC"||tEdorType =="GA"||tEdorType =="GM"||tEdorType =="GB"||tEdorType =="AA"||tEdorType =="PT")
	{
		
		var sqlid830104233="DSHomeContSql830104233";
var mySql830104233=new SqlClass();
mySql830104233.setResourceName("bq.GEdorTypeDetailSql");//ָ��ʹ�õ�properties�ļ���
mySql830104233.setSqlId(sqlid830104233);//ָ��ʹ�õ�Sql��id
mySql830104233.addSubPara(fm.ContNo.value);//ָ������Ĳ���
var tSQL=mySql830104233.getString();
		
//		var tSQL = "select poltype from lccont where contno = '"+fm.ContNo.value+"'";
		var tArr = easyExecSql(tSQL, 1, 0, 1);
		var tPolType;
		if (tArr != null)
		{
		  tPolType = tArr[0][0];
		  if(tPolType == "2")
		  {
		  	alert("���ʻ�Ϊ�����ʻ�����������"+fm.EdorTypeName.value+"������");
		  	return;
		  }
		  if(tPolType == "1")
		  {
		  	alert("�ñ�����Ϊ����������������"+fm.EdorTypeName.value+"������");
		  	return;
		  }
		  if(tEdorType =="PT"||tEdorType =="AA")
		  {
		  	
		  	var sqlid830104348="DSHomeContSql830104348";
var mySql830104348=new SqlClass();
mySql830104348.setResourceName("bq.GEdorTypeDetailSql");//ָ��ʹ�õ�properties�ļ���
mySql830104348.setSqlId(sqlid830104348);//ָ��ʹ�õ�Sql��id
mySql830104348.addSubPara(fm.GrpContNo.value);//ָ������Ĳ���
mySql830104348.addSubPara(fm.CustomerNo.value);//ָ������Ĳ���
var tsql=mySql830104348.getString();
		  	
//		  	var tsql = "select 1 from llreport a, llsubreport b where a.rptno = b.subrptno and a.grpcontno = '"+fm.GrpContNo.value+"' and b.customerno = '"+fm.CustomerNo.value+"'";
		  	//alert(fm.CustomerNo.value);
		  	var tGetArr = easyExecSql(tsql, 1, 0, 1);
		  	if (tGetArr != null)
				{
					var ret =  tGetArr[0][0];
					if(ret == "1")
					{
						alert("�ñ����������������������,���ܼ�������!");
						return;
					}
				}
		  }
		  if(tEdorType =="GA")
		  {
		  	//alert(fm.EdorItemAppDate.value);
		  	
		  	var sqlid830104432="DSHomeContSql830104432";
var mySql830104432=new SqlClass();
mySql830104432.setResourceName("bq.GEdorTypeDetailSql");//ָ��ʹ�õ�properties�ļ���
mySql830104432.setSqlId(sqlid830104432);//ָ��ʹ�õ�Sql��id
mySql830104432.addSubPara(fm.ContNo.value);//ָ������Ĳ���
var tsql=mySql830104432.getString();
		  	
//		  	var tsql = "select max(getstartdate) from lcget where contno = '"+fm.ContNo.value+"' and LiveGetType = '0'";
		  	var tGetArr = easyExecSql(tsql, 1, 0, 1);
		  	if (tGetArr != null)
				{
					var tGetStartDate = tGetArr[0][0];
					//alert(tGetStartDate);
					var retCPDate = compareDate(fm.EdorItemAppDate.value,tGetStartDate);
						
					if(retCPDate == "2")
					{
						if(!confirm("�ñ�����δ����Լ����ȡ��!ȷ���������ת��?"))
						{
							return;
						}
  				}
					
				}else {
				  alert("��ѯ��������ʧ��!���ܼ��������ñ���!");	
				  return;
				}
		  	//return;
		  }
		}
	}
	//return;
	var showStr = "�������뼯���¸��˱�ȫ�������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	if (selGridFlag == 1)
	{
		document.all("Transact").value = "INSERT||EDOR";
	}
	fm.submit();
	document.all("Transact").value = "";
}

//�򿪸��˱�ȫ����ϸ����
function openGEdorDetail() 
{
	if (fm.ContNo.value=="")
	{
		alert("�����ǿռ�¼!");
	}
	else
	{
		fm.CustomerNoBak.value = fm.CustomerNo.value;
		fm.ContNoBak.value = fm.ContNo.value;
		if(document.all('EdorType').value=='VR')
		{
			window.open("./PEdorType" + trim(document.all('EdorType').value) + ".jsp","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
		}
	  else if (document.all('EdorType').value=='AG')
	  {
	  	  window.open("./PEdorTypeAGInput.jsp","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
	  	  //window.open("./PEdorTypeAGInput.jsp","PEdorTypeAG",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	  }
		else
		{
		    window.open("./GEdorType" + trim(document.all('EdorType').value) + ".jsp","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
	  }
		showInfo.close();	 
	}
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
		GEdorFlag = true;

		if (fm.Transact.value=="DELETE||EDOR"||fm.Transact.value=="INSERT||EY")
		{
			document.all("Transact").value = "";
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
			openGEdorDetail();
		}
	}
	initLCInsuredGrid();
	initLCInsured2Grid();
	//queryClick2();
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
	var sqlid830104623="DSHomeContSql830104623";
var mySql830104623=new SqlClass();
mySql830104623.setResourceName("bq.GEdorTypeDetailSql");//ָ��ʹ�õ�properties�ļ���
mySql830104623.setSqlId(sqlid830104623);//ָ��ʹ�õ�Sql��id
mySql830104623.addSubPara(document.all('GrpContNo').value);//ָ������Ĳ���
mySql830104623.addSubPara(fm.EdorType.value);//ָ������Ĳ���
mySql830104623.addSubPara(fm.EdorNo.value);//ָ������Ĳ���
var strSql=mySql830104623.getString();
	
//	var strSql = "select ContNo, InsuredNo, Name, Sex, Birthday, IDType, IDNo from LcInsured where GrpContNo= '" + document.all('GrpContNo').value + "' and ContNo in "
//			   + " (select ContNo from LPEdorItem where edortype='" + fm.EdorType.value + "' and edorno='" + fm.EdorNo.value + "')";
	turnPage2.pageDivName = "divPage2";
	turnPage2.queryModal(strSql, LCInsured2Grid); 	 
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
	if (selGridFlag == 0)
	{
		alert("����ѡ��һ�������ˣ�");
		return;
	}
	document.all("Transact").value = "DELETE||EDOR"

	var showStr = "���ڳ��������¸��˱�ȫ�������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
		fm.action = "./GEdorTypeDetailSubmit.jsp";
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

//=======================
function returnParent() {
	
	//alert("This is GEdorTypeDetail.js->returnParent");
	top.opener.getEdorItemGrid();
	top.close();
}

//=====================
function QueryEdorInfo()
{
	//alert("This is GEdorTyp<p align="right"></p>eDetail.js -> QueryEdorInfo");
	var tEdortype = document.all('EdorType').value
	if(tEdortype != null || tEdortype != '')
	{
		var sqlid830104818="DSHomeContSql830104818";
var mySql830104818=new SqlClass();
mySql830104818.setResourceName("bq.GEdorTypeDetailSql");//ָ��ʹ�õ�properties�ļ���
mySql830104818.setSqlId(sqlid830104818);//ָ��ʹ�õ�Sql��id
mySql830104818.addSubPara(tEdortype);//ָ������Ĳ���
var strSQL=mySql830104818.getString();
		
//		var strSQL = "select distinct edorcode, edorname from lmedoritem where edorcode = '" 
//							 + tEdortype + "'";
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

  if(tEdortype!=null&&tEdortype=="GA")
  {

  	divGABatchImport.style.display='';
  }
  else
  	{
  		divGABatchImport.style.display='none';
  	}
  	
  if(tEdortype!=null&&(tEdortype=="AA"||tEdortype=="PT"))
  {

  	divAmntBatchImport.style.display='';
  }
  else
  	{
  		divAmntBatchImport.style.display='none';
  	}
	
}

//RT��������Ľӿ�
function RTBatchImport(){
	
	//alert("This is GEdorTypeDetail.js->RTBatchImport");	
	window.open("./GEdorTypeRTImport.jsp","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}

function submitLoad()
{
    getImportPath();
    fm1.all('ImportPath').value = ImportPath;
    var tGrpContNo = fm.GrpContNo.value;
    var tEdorNo = fm.EdorNo.value;
    var tEdorType = fm.EdorType.value;
    var tEdorAcceptNo = top.opener.fm.EdorAcceptNo.value;
    var tFileName=fm1.all('FileName').value;
		//alert(tFileName);
		if ( tFileName.indexOf("\\")>0 ) tFileName=tFileName.substring(tFileName.lastIndexOf("\\")+1);
		if ( tFileName.indexOf("/")>0 ) tFileName=tFileName.substring(tFileName.lastIndexOf("/")+1);
		if ( tFileName.indexOf("_")>0) tFileName=tFileName.substring( 0,tFileName.indexOf("_"));
		if ( tFileName.indexOf(".")>0) tFileName=tFileName.substring( 0,tFileName.indexOf("."));
		if(tFileName != tGrpContNo)
		{
			alert("�ļ��������屣���Ų�һ��,�����ļ���!");
			return ;
		}
    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm1.action = "./GrpEdorTypeGALoadSubmit.jsp?GrpContNo="+tGrpContNo+"&EdorNo="+tEdorNo+"&EdorType="+tEdorType+"&EdorAcceptNo="+tEdorAcceptNo;
    //alert("grpcontno:"+tGrpContNo); 
    fm1.submit(); //�ύ
    //tSaveFlag ="0";
}
function getImportPath ()
{
	  var strSQL = "";
	  
	  var sqlid830104910="DSHomeContSql830104910";
var mySql830104910=new SqlClass();
mySql830104910.setResourceName("bq.GEdorTypeDetailSql");//ָ��ʹ�õ�properties�ļ���
mySql830104910.setSqlId(sqlid830104910);//ָ��ʹ�õ�Sql��id
mySql830104910.addSubPara();//ָ������Ĳ���
strSQL=mySql830104910.getString();
	  
//	  strSQL = "select SysvarValue from ldsysvar where sysvar ='XmlPath'";
	  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);
	  if (!turnPage.strQueryResult) 
	  {
		    alert("δ�ҵ��ϴ�·��");
		    return;
	  }
	  turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
	  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	  ImportPath = turnPage.arrDataCacheSet[0][0];
}
function submitAmntLoad()
{
    getImportPath();
    fm2.all('ImportPath').value = ImportPath;
    var tGrpContNo = fm.GrpContNo.value;
    var tEdorNo = fm.EdorNo.value;
    var tEdorType = fm.EdorType.value;
    var tEdorAcceptNo = top.opener.fm.EdorAcceptNo.value;
    var tFileName=fm2.all('FileName').value;
		//alert(tFileName);
		if ( tFileName.indexOf("\\")>0 ) tFileName=tFileName.substring(tFileName.lastIndexOf("\\")+1);
		if ( tFileName.indexOf("/")>0 ) tFileName=tFileName.substring(tFileName.lastIndexOf("/")+1);
		if ( tFileName.indexOf("_")>0) tFileName=tFileName.substring( 0,tFileName.indexOf("_"));
		if ( tFileName.indexOf(".")>0) tFileName=tFileName.substring( 0,tFileName.indexOf("."));
		if(tFileName != tGrpContNo)
		{
			alert("�ļ��������屣���Ų�һ��,�����ļ���!");
			return ;
		}
    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm2.action = "./GEdorTypeAmntLoadSubmit.jsp?GrpContNo="+tGrpContNo+"&EdorNo="+tEdorNo+"&EdorType="+tEdorType+"&EdorAcceptNo="+tEdorAcceptNo;
    //alert("grpcontno:"+tGrpContNo); 
    fm2.submit(); //�ύ
    //tSaveFlag ="0";
}
function afterSubmitForLoad( FlagStr, content)
{
	  showInfo.close();
	  if (FlagStr == 'Fail' )
	  {             
		    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
		    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
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
		var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	      //fm.BatchNo.value = BatchNo;
	      //SelectJSPayPerson();
	      //operateButton2.style.display="";
              //operateButton1.style.display="none";
	  }
}
//ʹ�ù��ʰ�ť
function doEY()
{ 
  
 	var sqlid830105022="DSHomeContSql830105022";
var mySql830105022=new SqlClass();
mySql830105022.setResourceName("bq.GEdorTypeDetailSql");//ָ��ʹ�õ�properties�ļ���
mySql830105022.setSqlId(sqlid830105022);//ָ��ʹ�õ�Sql��id
mySql830105022.addSubPara(document.all('GrpContNo').value);//ָ������Ĳ���
var strSQL=mySql830105022.getString();

 	
// 	var strSQL = "select dif from LCGrpCont where GrpContNo = '" + document.all('GrpContNo').value + "'";
 	var arrResult = easyExecSql(strSQL);
 	if(arrResult != null && arrResult[0][0] > 0)
  {
   	initEYGrid();
   	EYGrid.setRowColData(0, 1, document.all('GrpContNo').value);
   	EYGrid.setRowColData(0, 2, arrResult[0][0]);
  }else{
 	 	alert("���ʽ��Ϊ0!����������ɷѷ�ʽ.");
 	 	return;
 	}
	 	var sqlid830105200="DSHomeContSql830105200";
var mySql830105200=new SqlClass();
mySql830105200.setResourceName("bq.GEdorTypeDetailSql");//ָ��ʹ�õ�properties�ļ���
mySql830105200.setSqlId(sqlid830105200);//ָ��ʹ�õ�Sql��id
mySql830105200.addSubPara( document.all('EdorNo').value);//ָ������Ĳ���
mySql830105200.addSubPara(document.all('EdorType').value);//ָ������Ĳ���
var tSQL=mySql830105200.getString();
	 	
//	 	var tSQL = "select sum(GetMoney) from LJSGetEndorse where EndorsementNo = '" + 
//	 							 document.all('EdorNo').value + "' and FeeOperationType = '" + document.all('EdorType').value + 
//	 							 "' and FeeFinaType in ('BF','GL','LX')";
 	var arrResult1 = easyExecSql(tSQL);
 	if(arrResult1 != null && arrResult1[0][0] > 0)
 	{
 		 	
 		 //������λС���㴦��
 		 var tEYBala = arrResult1[0][0]; 		 
 		 tEYBala = Math.round(tEYBala * 100) / 100; 		  		 
 		 fm.Money.value = tEYBala;
 		 arrResult1[0][0] = fm.Money.value; 	
 	   divAAEY.style.display = "";
 	   EYGrid.setRowColData(0, 3, arrResult1[0][0]);
 	}else{
 	  //EYGrid.setRowColData(0, 3, 0);
 	  divAAEY.style.display = "none";
 	  alert("���α�ȫʹ�ý��Ϊ0������ʹ��!");   
 	  return;
 	}     
}

//======ʹ�ù��ʵ�ʹ�ð�ť
function doEYSave()
{
	//alert("!!!!!!!");
 	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
 	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
 	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
 	document.all('fmtransact').value = "INSERT||EY";
 	document.all('Transact').value= "INSERT||EY";
 	fm.action="./GEdorTypeAASubmit.jsp";
 	fm.submit();
}
function doreturn()
{
 divAAEY.style.display = "none";
}