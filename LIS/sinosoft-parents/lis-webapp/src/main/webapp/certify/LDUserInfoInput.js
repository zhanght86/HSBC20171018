//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass(); //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var tResourceName="certify.LDUserInfoInputSql";
//��֤״̬��ѯ
function certifyQuery()
{
	var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();    
	initUserInfoGrid(); 	
	/*var strSQL="select a.comcode,(select name from ldcom where comcode=a.comcode), "
			+" a.usercode, a.username, a.validstartdate, a.validenddate "
			+" from lduser a where 1 = 1 "
			+" and exists (select 1 from ldusertomenugrp b where b.usercode = a.usercode "
			+" and exists (select 1 from ldmenugrptomenu c where c.menugrpcode = b.menugrpcode "
			+" and exists (select 1 from ldmenu d where d.nodecode = c.nodecode and d.nodename = '��֤����(��)'))) "
			+ getWherePart('a.comcode','ManageCom')
			+ getWherePart('a.validstartdate','validstartdate','<=')
			+ getWherePart('length(a.comcode)','grade')
			+" order by a.comcode, a.usercode" ;*/
	var strSQL = wrapSql(tResourceName,"querysqldes1",[document.all('ManageCom').value,document.all('validstartdate').value,document.all('grade').value]);
	
   	turnPage.pageLineNum = 10;
   	turnPage.queryModal(strSQL, UserInfoGrid);
   	//alert("��ѯ���ļ�¼������"+UserInfoGrid.mulLineCount);
   	if(UserInfoGrid.mulLineCount==0)
   	{
   	    showInfo.close();
   		alert("û�в�ѯ��Ϣ��");
   		return false;
   	}
   	showInfo.close();
}

//[��ӡ]��ť����
function certifyPrint()
{
	//alert("��ѯ���ļ�¼������"+UserInfoGrid.mulLineCount);
   	if(UserInfoGrid.mulLineCount==0)
   	{
   		alert("û�п��Դ�ӡ�����ݣ�");
   		return false;
   	}
	easyQueryPrint(2,'UserInfoGrid','turnPage');	
}
