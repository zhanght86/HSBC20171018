var showInfo;
var turnPage = new turnPageClass(); 

// ��ѯ��ť
function easyQueryClick()
{
	// ��ʼ�����
	initBonusGrid();
	
	var fiscalYear = document.all('FiscalYear').value;
	var grppolNo = document.all('GrpContNo').value;
	var addSql = "";
	if(document.all('ComputeState').value != null && document.all('ComputeState').value != "")
		addSql = " and exists(select 1 from lobonusgrppolparm where grppolno = a.grppolno and fiscalyear = "+fiscalYear+" and computestate = '"+document.all('ComputeState').value+"') ";
	
	var sqlid831102106="DSHomeContSql831102106";
var mySql831102106=new SqlClass();
mySql831102106.setResourceName("uwgrp.GrpCalPartBonusSql");//ָ��ʹ�õ�properties�ļ���
mySql831102106.setSqlId(sqlid831102106);//ָ��ʹ�õ�Sql��id
mySql831102106.addSubPara(grppolNo);//ָ������Ĳ���
mySql831102106.addSubPara(fiscalYear);//ָ������Ĳ���
mySql831102106.addSubPara(grppolNo);//ָ������Ĳ���
mySql831102106.addSubPara(fiscalYear);//ָ������Ĳ���
mySql831102106.addSubPara(grppolNo);//ָ������Ĳ���
//mySql831102106.addSubPara(fiscalYear);//ָ������Ĳ���
//mySql831102106.addSubPara(document.all('ComputeState').value);//ָ������Ĳ���
mySql831102106.addSubPara(addSql);
mySql831102106.addSubPara(fm.PolNo.value);//ָ������Ĳ���
mySql831102106.addSubPara(fm.BDate.value);//ָ������Ĳ���
mySql831102106.addSubPara(fm.EDate.value);//ָ������Ĳ���
var strSQL=mySql831102106.getString();
	
	
	// ��дSQL���
//	var strSQL = "select grppolno,polno,cvalidate,"
//						 + "(select assignrate from lobonusgrppolparm where grppolno = '"+ grppolNo +"' and fiscalyear = '"+fiscalYear+"'),"
//						 + "decode((select max(bonusflag) from lobonusgrppol where grppolno = '"+ grppolNo +"' and fiscalyear = '"+fiscalYear+"' and polno = a.polno),null,'0','0','3','1','4') "
//						 + "from lcpol a "
//						 + "where grppolno = '" + grppolNo + "' "
//						 + addSql
//						 + getWherePart('PolNo')
//						 + getWherePart('cvalidate','BDate','>=','0')
//						 + getWherePart('cvalidate','EDate','<=','0')
//						 + " order by polno "
//						 ;
	//alert(strSQL);
	
	//��ѯSQL�����ؽ���ַ���
	turnPage.queryModal(strSQL, BonusGrid);    
}

//�ύ�����水ť��Ӧ����
function submitForm()
{
	var tSel = BonusGrid.getSelNo();
	if( tSel == 0 || tSel == null )
	{
		alert( "����ѡ��һ����¼��" );
		return;
	}
	
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
  //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

	document.all("btnSubmit").disabled=true;
	document.all('PolNo').value=BonusGrid.getRowColData(tSel-1,2);
  document.getElementById("fm").submit(); //�ύ
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  document.all("btnSubmit").disabled=false;

  if (FlagStr == "Fail" )
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
  else
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  

  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
  //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
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
