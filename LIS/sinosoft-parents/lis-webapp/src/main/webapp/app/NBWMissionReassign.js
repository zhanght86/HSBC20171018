//�������ƣ�NBWMissionReassign.js
//�����ܣ�����Լ�������
//�������ڣ�2006-2-20 14:50
//������  ��chenrong
//���¼�¼��  ������    ��������     ����ԭ��/����

var showInfo;
var mDebug = "0";
var flag;
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();
//var DesignateOperatorName = "";

//��ѯ��Ӧ�����ŵ��������
function easyQuery()
{
    var sSQL = "";
//    alert(12);
    emptyTextValue();
/*
    sSQL = "select lm.missionprop1,la.activityname,lm.defaultoperator,(select PolApplyDate from lccont where prtno=trim(lm.missionprop1)) ApplyDate,1,(select decode(min(c.makedate),null,'��ɨ���',to_char(min(c.makedate),'yyyy-mm-dd')) from es_doc_main c where c.doccode=trim(lm.missionprop1)) ScanDate,lu.username,lm.missionid,lm.submissionid,lm.activityid "
             + " from lwmission lm,LWActivity la ,lduser lu "
             + " where la.activityid = lm.activityid "
           + " and lu.usercode = lm.defaultoperator "
//           + " and lu.comcode like '" + document.all('ComCode').value + "%%'"
//           + " and lu.comcode like '" + document.all('ComCode').value + "%%'"
           + " and lm.processid = '0000000003' "
           + " and lm.activityid in ('0000001090', '0000001091', '0000001001', '0000001094', '0000001020', '0000001099', '0000001002')"
           + getWherePart('lm.MissionProp1','PrtNo')
           + getWherePart('lm.activityid','ActivityID1')
           + getWherePart('lm.defaultoperator','OperatorQ')
           + " and exists "
           + " (select * from es_doc_main c where c.doccode=trim(lm.missionprop1) and makedate='"+document.all('ScanDate').value+"')";
*/
    
    var wherePartStr = getWherePart('lm.MissionProp1','PrtNo')
           						+ getWherePart('lm.activityid','ActivityID1')
           						+ getWherePart('lm.defaultoperator','OperatorQ');
    var sqlid915160403="DSHomeContSql915160403";
var mySql915160403=new SqlClass();
mySql915160403.setResourceName("app.NBWMissionReassignSql");//ָ��ʹ�õ�properties�ļ���
mySql915160403.setSqlId(sqlid915160403);//ָ��ʹ�õ�Sql��id
mySql915160403.addSubPara(wherePartStr);//ָ������Ĳ���
var subSQL1 = " and 1=1 " ;
var subSQL2 = " and 1=1 " ;
//alert(11);
    
//    sSQL = "select lm.missionprop1,la.activityname,lm.defaultoperator,"
////         + " (select PolApplyDate from lccont where trim(prtno)=trim(lm.missionprop1)) applydate,"
////         + " to_char(lm.modifydate,'yyyy-mm-dd')||' '||lm.modifytime)
//           + " to_char(lm.modifydate,'yyyy-mm-dd'),"
//           + " lm.modifytime,"
//           + "(select decode(min(c.makedate),null,'��ɨ���',to_char(min(c.makedate),'yyyy-mm-dd')) from es_doc_main c where c.doccode=trim(lm.missionprop1)) ScanDate,lu.username,lm.missionid,lm.submissionid,lm.activityid "
//           +" from lwmission lm,LWActivity la ,lduser lu "
//
//           +"  where la.activityid = lm.activityid "
//           +" and lu.usercode = lm.defaultoperator  "        
//           +" and lm.processid = '0000000003' " 
//           +" and lm.activityid in ('0000001090', '0000001091', '0000001001', '0000001094', '0000001020', '0000001099', '0000001002') "
//           + getWherePart('lm.MissionProp1','PrtNo')
//           + getWherePart('lm.activityid','ActivityID1')
//           + getWherePart('lm.defaultoperator','OperatorQ');
  if (trim(fm.ScanStartDate.value) == "" || fm.ScanStartDate.value == null){
  }
  else{
	  subSQL1 =" and exists  (select * from es_doc_main where doccode=lm.missionprop1 and makedate>='"+fm.ScanStartDate.value+"')";
  }
  if (trim(fm.ScanEndDate.value) == "" || fm.ScanEndDate.value == null){
     }
    else{
    	
    	subSQL2 =" and exists "
    		+" (select * from es_doc_main where doccode=lm.missionprop1 and makedate<='"+document.all('ScanEndDate').value+"')";     
    	
    }
        var subSQL3 =" order by  ScanDate"   ;
        mySql915160403.addSubPara(subSQL1);//ָ������Ĳ���
        mySql915160403.addSubPara(subSQL2);//ָ������Ĳ���
        mySql915160403.addSubPara(subSQL3);//ָ������Ĳ���
				sSQL=mySql915160403.getString();
    turnPage.queryModal(sSQL, AllPolGrid);
}

//���Radioʱ���������ؿؼ���ֵ
function setTextValue()
{
    var sno = AllPolGrid.getSelNo()-1;
    document.all('MissionID').value = AllPolGrid.getRowColData(sno, 8);
	document.all('SubMissionID').value = AllPolGrid.getRowColData(sno, 9);
	document.all('ActivityID').value = AllPolGrid.getRowColData(sno, 10);
	document.all('DefaultOperator').value = AllPolGrid.getRowColData(sno, 3);
	document.all('DefaultOperatorName').value = AllPolGrid.getRowColData(sno, 7);
	document.all('DesignateOperator').value = "";
}

//��ձ����ؿؼ�ֵ
function emptyTextValue()
{
    document.all('MissionID').value = "";
	document.all('SubMissionID').value = "";
	document.all('ActivityID').value = "";
	document.all('DesignateOperator').value = "";
	document.all('DefaultOperator').value = "";
}

//����ָ������Ա
function reassignNBW()
{
    var tDesignateOperator = "";
    /*
    if (AllPolGrid.getSelNo()-1 < 0)
    {
        alert("��ѡ��Ҫ�����Ͷ������");
	    return;
	  }
	  */
	
	tDesignateOperator = document.all('DesignateOperator').value ;
	if (tDesignateOperator == "" || tDesignateOperator == null)
	{
	    alert("��ָ������Ա");
	    return;
	}
	
	//add
	var sqlid915161630="DSHomeContSql915161630";
var mySql915161630=new SqlClass();
mySql915161630.setResourceName("app.NBWMissionReassignSql");//ָ��ʹ�õ�properties�ļ���
mySql915161630.setSqlId(sqlid915161630);//ָ��ʹ�õ�Sql��id
mySql915161630.addSubPara(document.all('DesignateOperator').value);//ָ������Ĳ���
var sql=mySql915161630.getString();
	
//	var sql="select username from lduser lu "
//                 + "where usercode = '" + document.all('DesignateOperator').value + "'" ;
  arrResult = easyExecSql(sql,1,0);
  if (arrResult == null) {  
  	alert("û�в�ѯ��ָ���Ĳ���Ա��Ϣ!"); 
  	return;
  }else
  	fm.DesignateOperatorName.value = arrResult[0][0];
	lockScreen('lkscreen');  
	document.all('fmtransact').value = "User||UPDATE";
	fm.action = "NBWMissionReassignSave.jsp";
	submitForm();
	
}

//�ύ����
function submitForm()
{
    //�ύ����
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
    document.getElementById("fm").submit(); //�ύ
}

//ָ������Ա��ˢ��ҳ��
function refreshPage()
{
//	 
   var i=0 ;
	 for(i=0 ;i<AllPolGrid.mulLineCount ;i++)
	 {
    if(AllPolGrid.getChkNo(i))
      {      	
      	 AllPolGrid.setRowColData(i,3,fm.DesignateOperator.value);
      	 AllPolGrid.setRowColData(i,7,fm.DesignateOperatorName.value);
	    }	 
	 }
	    fm.DesignateOperator.value = "";
	    fm.DesignateOperatorName.value = "";
//    var tSelRow = AllPolGrid.GetChkNo()-1;
//	AllPolGrid.setRowColData(tSelRow,3,fm.DesignateOperator.value);
//	AllPolGrid.setRowColData(tSelRow,7,DesignateOperatorName);
//	fm.DefaultOperator.value = fm.DesignateOperator.value;
//	fm.DefaultOperatorName.value =  fm.DesignateOperatorName.value;
//	fm.DesignateOperator.value = "";
//	fm.DesignateOperatorName.value = "";
}

//Ԥ�������ύ�����,����
function afterSubmit(FlagStr, content)
{
	unlockScreen('lkscreen');  
    showInfo.close();
    if (FlagStr == "Fail" )
    {
        var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    }
    else
    {
        var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    }
    refreshPage();
   //easyQuery();
}


function DuserQueryClick()
{
	
	var i=0 ;
	for(i=0 ;i<AllPolGrid.mulLineCount ;i++)
    if(AllPolGrid.getChkNo(i))
      return;
	
		alert( "��ѡ��Ҫ�����Ͷ����!" );
}


function DuserNameQueryClick()
{
    var sqlid915161725="DSHomeContSql915161725";
var mySql915161725=new SqlClass();
mySql915161725.setResourceName("app.NBWMissionReassignSql");//ָ��ʹ�õ�properties�ļ���
mySql915161725.setSqlId(sqlid915161725);//ָ��ʹ�õ�Sql��id
mySql915161725.addSubPara(document.all('DesignateOperator').value);//ָ������Ĳ���
var sql=mySql915161725.getString();
    
//    var sql="select username from lduser lu "
//                 + "where usercode = '" + document.all('DesignateOperator').value + "'" ;
  arrResult = easyExecSql(sql,1,0);
  if (arrResult == null) {  
  	alert("û�в�ѯ��ָ���Ĳ���Ա��Ϣ!"); 
  	return;
  }else{
  	fm.DesignateOperatorName.value = arrResult[0][0];
  	return;
  }
}


function userQueryClick()
{
  var i=0 ;
	for(i=0 ;i<AllPolGrid.mulLineCount ;i++)
    if(AllPolGrid.getChkNo(i))
      {
      	
      	 var urlStr = "NBUserQueryMain.jsp";
	       window.open(urlStr,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	       return;
	    }
		alert( "��ѡ��Ҫ�����Ͷ����!" );	
}

function afterQuery(arrQueryResult)
{
    var arrResult = new Array();
    if (arrQueryResult != null)
    {
        arrResult = arrQueryResult ;
        fm.DesignateOperator.value = arrResult[0];
        fm.DesignateOperatorName.value = arrResult[1];
    }
}
