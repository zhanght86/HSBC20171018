var showInfo;
var turnPage = new turnPageClass();
//var Str="1 and codealias= #" + fm.ErrManageCom.value + "#";

function initQuery()
{

	var strSql;
	//����
//		strSql = " SELECT P.ErrManagecom,P.ErrorType, P.ErrContent, P.MakeDate, P.Operator, P.ManageCom, P.Serialno" 
//				 + " FROM LCIssueMistake P " 
//				 + "WHERE p.Proposalcontno = '"+tPrtNo+"' "
//				 + "ORDER BY P.MAKEDATE, P.MAKETIME ";
		
		    var  sqlid1="MSQuestMistakeInputSql0";
			var  mySql1=new SqlClass();
			mySql1.setResourceName("uw.MSQuestMistakeInputSql"); //ָ��ʹ�õ�properties�ļ���
			mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
			mySql1.addSubPara(tPrtNo);//ָ������Ĳ���
			strSql=mySql1.getString();
	//alert(111);
	turnPage.queryModal(strSql, QuestGrid); 	
}

function addNote()
{

	if (trim(fm.ErrManageCom.value) == "") 
	{
		alert("������д������λ��");
		return;
	}
	if (trim(fm.ErrorType.value) == "") 
	{
		alert("������д������ͣ�");
		return;
	}
	//var tSerialNo=QuestGrid.getRowColData(selno, 7);
	
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;    
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    lockScreen('lkscreen');  
	fm.maction.value =  "INSERT";
	
	document.getElementById("fm").submit(); //�ύ	
	
}

function UpdateMistake(){
	var selno = QuestGrid.getSelNo()-1;
	if (selno <0)
	{
		  alert("����ѡ��һ����¼��");
	      return false;
	}	
	if (trim(fm.ErrManageCom.value) == "") 
	{
		alert("������д������λ��");
		return;
	}
	if (trim(fm.ErrorType.value) == "") 
	{
		alert("������д������ͣ�");
		return;
	}
	//var tSerialNo=QuestGrid.getRowColData(selno, 7);
	
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;    
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    lockScreen('lkscreen');  
	fm.maction.value =  "UPDATE";
	
	document.getElementById("fm").submit(); //�ύ	
}

function DeleteMistake(){
	var selno = QuestGrid.getSelNo()-1;
	if (selno <0)
	{
		  alert("����ѡ��һ����¼��");
	      return false;
	}	
	if (trim(fm.ErrManageCom.value) == "") 
	{
		alert("������д������λ��");
		return;
	}
	if (trim(fm.ErrorType.value) == "") 
	{
		alert("������д������ͣ�");
		return;
	}
	//var tSerialNo=QuestGrid.getRowColData(selno, 7);
	
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;    
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    lockScreen('lkscreen');  
	fm.maction.value =  "DELETE";
	
	document.getElementById("fm").submit(); //�ύ	
}

function afterSubmit( FlagStr, content )
{
	unlockScreen('lkscreen');  
	//alert(0);
	showInfo.close();
            
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	if(FlagStr == "Succ")
	{
		initQuery();
		top.opener.checkNotePad(fm.PrtNo.value);
	}
	fm.Content.value="";
}

function showContent()
{
	var selno = QuestGrid.getSelNo()-1;
	if (selno <0)
	{
	      return;
	}	
	var Content = QuestGrid.getRowColData(selno, 3);
	fm.Content.value = Content;
	fm.ErrManageCom.value = QuestGrid.getRowColData(selno, 1);
	fm.ErrorType.value = QuestGrid.getRowColData(selno, 2);
	fm.ErrManageCom.value = QuestGrid.getRowColData(selno, 1);
	fm.SerialNo.value = QuestGrid.getRowColData(selno, 7);
}

function afterCodeSelect( cCodeName, Field ){
	if(cCodeName=="ErrManageCom"){
		ErrManageCom = Field.value;//alert("ErrManageCom=="+ErrManageCom);
	}
}