//�������ƣ�CutBonus.js
//�����ܣ��ֺ촦��
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����

//���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
var k = 0;
var turnPage = new turnPageClass();

//�ύ�����水ť��Ӧ����
function submitForm()
{
  var i = 0;
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

  initGrpPolGrid();

  document.getElementById("fm").submit(); //�ύ
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  document.all("assignbtn").disabled = false;
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  

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
  }
  else
  { 
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
    alert(content);
  }
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

// ��ѯ��ť
function queryGrpPol()
{
		// ��ʼ�����
		initGrpPolGrid();
		
		
		var sqlid831144718="DSHomeContSql831144718";
var mySql831144718=new SqlClass();
mySql831144718.setResourceName("uwgrp.GrpCutPersonBonusSql");//ָ��ʹ�õ�properties�ļ���
mySql831144718.setSqlId(sqlid831144718);//ָ��ʹ�õ�Sql��id
mySql831144718.addSubPara(document.all("FiscalYear").value);//ָ������Ĳ���
mySql831144718.addSubPara(document.all("GrpContNo").value);//ָ������Ĳ���
mySql831144718.addSubPara(fm.FiscalYear.value);//ָ������Ĳ���
mySql831144718.addSubPara(fm.ContNo.value);//ָ������Ĳ���
var strSQL=mySql831144718.getString();

		
		// ��дSQL���
//		var strSQL = "select distinct(b.ContNo),b.GrpContNo,a.FiscalYear,a.InsuAccNo,(select sum(bonusmoney) from "
//		            +"lobonusgrppol where polno =a.polno and bonusflag='0' and fiscalyear='"+document.all("FiscalYear").value+"'),"
//		            +"a.SGetDate from LOBonusGrpPol a,lcpol b "
//  					+ "where a.polno = b.polno and exists (select 1 from lcgrppol where appflag = '1' and grppolno = a.grppolno and GrpContNo = '" + document.all("GrpContNo").value + "') and bonusflag = '0' "
//					+ getWherePart('a.FiscalYear','FiscalYear')
// 					+ getWherePart('b.ContNo','ContNo');
					
		
		//alert(strSQL);
		//��ѯSQL�����ؽ���ַ���
		turnPage.queryModal(strSQL, GrpPolGrid); 
}

function assignPerson()
{
		var tSel = GrpPolGrid.getSelNo();
		if( tSel != null && tSel > 0)
		{
			document.all('ContNo').value = GrpPolGrid.getRowColData(tSel-1,1);
		}

		var showStr="���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
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
		
		//showSubmitFrame(mDebug);
		document.all("assignbtn").disabled=true;
		document.getElementById("fm").submit(); //�ύ
}

/*********************************************************************
 *  ��ʾfrmSubmit��ܣ���������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showSubmitFrame(cDebug)
{
	if( cDebug == "1" )
		parent.fraMain.rows = "0,0,50,82,*";
	else 
		parent.fraMain.rows = "0,0,0,72,*";
}

function viewErrLog()
{
	window.open("./ViewErrLogGrpBonusAssign.jsp","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");		
}