//�������ƣ�QuestQuery.jsp
//�����ܣ��������ѯ
//�������ڣ�2002-09-24 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����

//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var k = 0;
var turnPage = new turnPageClass();
var mySql = new SqlClass();
function query()
{
	// ��ʼ�����
	initQuestGrid();
	initContent();
	
	// ��дSQL���
	k++;
	var strSQL = "";
	var ifreply = fm.IfReply.value;
	var tOperatePos = fm.OperatePos.value;
  var prtNo = fm.PrtNo.value;
  
  var ManageCom = fm.IsueManageCom.value;
  /*
  if (ManageCom == "")
	{
			alert("��ѡ����������");
			return;
	}
	if(ManageCom.length<4 || ManageCom.length>8){
		alert("�������ӦΪ4λ��8λ��");
		return;
	} 
	*/
	if(!verifyInput())
	{
		return false;
	}
  if (prtNo != "") {
   // var strSql = "select proposalcontno from lcpol where prtno='" + prtNo + "'";
    //var arrResult = easyExecSql(strSql);
    
  //  if (arrResult != null) {
  //    fm.proposalcontno.value = arrResult[0][0];
  //  }
  //  else {
  //    return;
  //  }
  }
  	

	/*strSQL = "select a.proposalcontno,a.issuetype,a.issuecont,a.replyresult,a.operator,a.makedate,"
	     + " (select codename from ldcode where codetype='operatepos' and code=trim(a.OperatePos)),"
	     + " b.codename,(case when (a.backobjtype ='2' or a.backobjtype='3') then decode(needprint,'Y','�·�','���·�') else"
//	     + " '�·�'"
	     + " decode(needprint,'Y','�·�','���·�')"
	     + " end), "
	     + " SerialNo,senddate,replydate,(select decode(recordquest,'Y','��','N','��','') from ldcodemod where code =a.issuetype),"
	     + " (select signname from lccont where contno=a.contno) from lcissuepol a,ldcode b,lccont c where "+k+"="+k+""
			 + " and IsueManageCom like '" + manageCom + "%%'"
			 + "  and b.codetype='backobj' "
			 + " and a.contno=c.contno "
   		 + " and a.backobjtype=trim(b.comcode) "
		 	 + " and  a.standbyflag2=b.othersign"
			 + getWherePart( 'c.prtno','PrtNo' )
       + getWherePart( 'b.code','BackObj' )
			 + getWherePart( 'Operator','Operator' )
			 + getWherePart( 'OperatePos','OperatePos')
			 //+ getWherePart( 'needprint','needprint')
			 + getWherePart( 'IssueType','IssueType')
			 + getWherePart( 'IsueManageCom','IsueManageCom','like')
			 //add by jiaqiangli 2007-12-20
			 + getWherePart('a.makedate','StartDate','>=')
			 + getWherePart('a.makedate','EndDate','<=')	 
			 + getWherePart('c.makedate','PolStartDate','>=')
		   + getWherePart('c.makedate','PolEndDate','<=')	   
	*/		 
	mySql = new SqlClass();
	mySql.setResourceName("app.QuestQuerySql");
	mySql.setSqlId("QuestQuerySql1");
	mySql.addSubPara(manageCom); 
	mySql.addSubPara(fm.PrtNo.value ); 
	mySql.addSubPara(fm.BackObj.value ); 
	mySql.addSubPara(fm.Operator.value ); 
	mySql.addSubPara(fm.OperatePos.value ); 
	mySql.addSubPara(fm.IssueType.value ); 
	mySql.addSubPara(fm.IsueManageCom.value ); 
	mySql.addSubPara(fm.StartDate.value ); 
	mySql.addSubPara(fm.EndDate.value );
	 mySql.addSubPara(fm.PolStartDate.value ); 
	mySql.addSubPara(fm.PolEndDate.value );  
	mySql.addSubPara(fm.ErrorFlag.value );  
	
  if (ifreply == "N") {
   // strSQL = strSQL + " and ReplyMan is null";
    mySql = new SqlClass();
	mySql.setResourceName("app.QuestQuerySql");
	mySql.setSqlId("QuestQuerySql2");
	mySql.addSubPara(manageCom); 
	mySql.addSubPara(fm.PrtNo.value ); 
	mySql.addSubPara(fm.BackObj.value ); 
	mySql.addSubPara(fm.Operator.value ); 
	mySql.addSubPara(fm.OperatePos.value ); 
	mySql.addSubPara(fm.IssueType.value ); 
	mySql.addSubPara(fm.IsueManageCom.value ); 
	mySql.addSubPara(fm.StartDate.value ); 
	mySql.addSubPara(fm.EndDate.value );
	 mySql.addSubPara(fm.PolStartDate.value ); 
	mySql.addSubPara(fm.PolEndDate.value ); 
	mySql.addSubPara(fm.ErrorFlag.value );   
  }
  else if (ifreply == "Y") {
   // strSQL = strSQL + " and ReplyMan is not null";
    mySql = new SqlClass();
	mySql.setResourceName("app.QuestQuerySql");
	mySql.setSqlId("QuestQuerySql3");
	mySql.addSubPara(manageCom); 
	mySql.addSubPara(fm.PrtNo.value ); 
	mySql.addSubPara(fm.BackObj.value ); 
	mySql.addSubPara(fm.Operator.value ); 
	mySql.addSubPara(fm.OperatePos.value ); 
	mySql.addSubPara(fm.IssueType.value ); 
	mySql.addSubPara(fm.IsueManageCom.value ); 
	mySql.addSubPara(fm.StartDate.value ); 
	mySql.addSubPara(fm.EndDate.value );
	 mySql.addSubPara(fm.PolStartDate.value ); 
	mySql.addSubPara(fm.PolEndDate.value ); 
	mySql.addSubPara(fm.ErrorFlag.value );   
  }
  if(fm.ErrorFlag.value!=null&&fm.ErrorFlag.value!=""){
    //strSQL =strSQL + " and exists (select 1 from ldcodemod where code =a.issuetype and recordquest='"+fm.ErrorFlag.value+"')";
  }

  //strSQL = strSQL + " order by makedate";
  
  turnPage.queryModal(mySql.getString(), QuestGrid); 
  fm.PrintType.value = "QUEST||PRINT";
  return true;	
}

function query2() {
  // ��ʼ�����
	initQuestGrid();
	initContent();
	
	// ��дSQL���
	k++;
	var strSQL = "";
	var ifreply = fm.IfReply.value;
	var tOperatePos = fm.OperatePos.value;
  var prtNo = fm.PrtNo.value;
  
  var ManageCom = fm.IsueManageCom.value;
  if (ManageCom == "")
	{
			alert("��ѡ����������");
			return;
	}
	if(ManageCom.length<4 || ManageCom.length>8){
		alert("�������ӦΪ4λ��8λ��");
		return;
	} 
  if (prtNo != "") {
    //var strSql = "select proposalcontno from lcpol where prtno='" + prtNo + "'";
    //var arrResult = easyExecSql(strSql);
    
   // if (arrResult != null) {
  //    fm.proposalcontno.value = arrResult[0][0];
 //   }
   // else {
  //    return;
  //  }
  }
  
  /*strSQL = "select proposalcontno,issuetype,issuecont,replyresult,operator,makedate,OperatePos,BackObjType,decode(needprint,'Y','�·�','���·�'),SerialNo from lcissuepol where "+k+"="+k+" "
			 + " and IsueManageCom like '" + manageCom + "%%'"
			 //add by jiaqiangli 2007-12-20
			 + getWherePart('makedate','StartDate','>=')
			 + getWherePart('makedate','EndDate','<=')
			 + " and proposalcontno in (select otherno from loprtmanager where prtseq='" + fm.SerialNo.value + "' and othernotype='02' and code in( 'TB89','TB90')) "
			 + " and backObjtype='2' and needprint='Y'";*/
  mySql = new SqlClass();
	mySql.setResourceName("app.QuestQuerySql");
	mySql.setSqlId("QuestQuerySql4");
	mySql.addSubPara(ManageCom); 
	mySql.addSubPara(fm.StartDate.value ); 
	mySql.addSubPara(fm.EndDate.value ); 
	mySql.addSubPara(fm.SerialNo.value );  
  turnPage.queryModal(mySql.getString(), QuestGrid); 
  fm.PrintType.value = "NOTICE||PRINT";
  return true;	
}


function queryone(parm1, parm2)
{	
	var strSQL = "";
	var tcho;
	
	var tselno=QuestGrid.getSelNo()-1;
	
	tPos = QuestGrid.getRowColData(tselno,1);;
	tQuest = QuestGrid.getRowColData(tselno,2);;
	tProposalNo = QuestGrid.getRowColData(tselno,1);;
	tSerialNo = QuestGrid.getRowColData(tselno,10);;

	if (tPos == "")
	{
		alert("��ѡ�������!")
		return "";
	}	
	
	if (tProposalNo == "")
	{
		alert("�����Ų���Ϊ�գ�");
		return "";
	}
	if (tQuest == "")
	{
		alert("���������Ϊ�գ�");
		return "";
	}

	/*strSQL = "select issuecont,replyresult,issuetype,OperatePos from lcissuepol where "
	       + " proposalcontno='" + tProposalNo + "'"
	       + " and issuetype='" + tQuest + "'"
	      // + " and OperatePos='" + tPos + "'"
	       + " and SerialNo='" + tSerialNo + "'";*/
	//alert(strSQL);
	mySql = new SqlClass();
	mySql.setResourceName("app.QuestQuerySql");
	mySql.setSqlId("QuestQuerySql5");
	mySql.addSubPara(tProposalNo); 
	mySql.addSubPara(tQuest); 
	mySql.addSubPara(tSerialNo );   
  //��ѯSQL�����ؽ���ַ���
  var strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("û��¼����������");
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  var arrDataCacheSet = decodeEasyQueryResult(strQueryResult);
  
  var returnstr = "";
  var tcont = "";
  var treply = "";
  var ttype = "";
  var tOperatePos = "";
  var n = arrDataCacheSet.length;
  //alert("N:"+n);
  if (n > 0)
  {
  	m = arrDataCacheSet[0].length;
  		//alert("M:"+m);
  		if (m > 1)
  		{
  			//alert("turnPage:"+arrDataCacheSet[0][0]);
			tcont = arrDataCacheSet[0][0];
			treply = arrDataCacheSet[0][1];
			ttype = arrDataCacheSet[0][2];
			tOperatePos = arrDataCacheSet[0][3];
  		}
  		else
  		{
  			alert("û��¼����������");
  			return "";
  		}
  	
  }
  else
  {
  	alert("û��¼����������");
	return "";
  }
 
  if (tcont == "")
  {
  	alert("û��¼����������");
  	return "";
  }
  
  document.all('Content').value = tcont;
  document.all('ReplyResult').value = treply;
  document.all('Type').value = ttype;
  return returnstr;
}

function QuestQuery() {
  //var strSQL = "select code, cont from ldcodemod where codetype = 'Question'";
  mySql = new SqlClass();
	mySql.setResourceName("app.QuestQuerySql");
	mySql.setSqlId("QuestQuerySql6");
  var strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);
  
  fm.IssueType.CodeData = strQueryResult;
}

//see also EasyQueryPrint.js
function print() {
  //alert(fm.PrintType.value);
	//�ڶ�������Ϊ�ؼ���
     var i = 0;
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
  var iWidth=550;      //�������ڵĿ��; 
  var iHeight=250;     //�������ڵĸ߶�; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();

  var arrReturn = new Array();
		fm.target = "../f1print";
		//fm.action = "";
		document.getElementById("fm").submit();
		showInfo.close();   
}