//�������ƣ�QuestQuery.js
//�����ܣ��������ѯ
//�������ڣ�2002-09-24 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����

//���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var flag;
var k = 0;
var turnPage = new turnPageClass();
var cflag = "";  //���������λ�� 1.�˱�
var canReplyFlag = false;
var str_sql = "",sql_id = "", my_sql = "";   //�󶨱����������ݿ�
//�ύ,���水ť��Ӧ����
function submitForm()
{
  var i = 0;
  var showStr="���ڱ�������,�����Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");

  document.getElementById("fm").submit(); //�ύ
}

//�ύ,���水ť��Ӧ����
function IfPrint()
{
  var i = 0;
  var showStr="���ڱ�������,�����Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
��cContNo = fm.ContNo.value;  //��������	
//��var strSql = "select * from LCPol where PolNo='" + cContNo + "' and  approveflag = '2'";
sql_id = "RnewQuestQuerySql0";
my_sql = new SqlClass();
my_sql.setResourceName("uw.RnewQuestQuerySql"); //ָ��ʹ�õ�properties�ļ���
my_sql.setSqlId(cContNo);//ָ��ʹ�õ�Sql��id
my_sql.addSubPara(idstr);//ָ������Ĳ���
str_sql = my_sql.getString();
    var arrResult = easyExecSql(str_sql);
       //alert(arrResult);
    if (arrResult != null) {     
   ����alert("�ѷ��˱�֪ͨ��,��δ��ӡ,�������ڴ�ʱ�޸�������Ĵ�ӡ��־��");
          return;
      }      
  fm.action = "./QuestQueryUpdatePrintFlag.jsp";
  document.getElementById("fm").submit(); //�ύ
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    //showInfo.close();
    alert(content);
  }
  else if (FlagStr == "Succ") {
    content = "�����ɹ���";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px"); 
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    if(document.all('hiddenOperate').value!=null&&document.all('hiddenOperate').value=='finishMission')
    {
    	showInfo.close();
    	top.close();
    }
  }
  else
  { 
	var showStr="��ӡ��Ǹ��³ɹ�";
  	alert(showStr);
  }
}

//��ʾfrmSubmit���,��������
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
	parent.fraMain.rows = "0,0,50,82,*";
  }
  else 
  {
  	parent.fraMain.rows = "0,0,0,82,*";
  }
}
         

//��ʾdiv,��һ������Ϊһ��div������,�ڶ�������Ϊ�Ƿ���ʾ,���Ϊ"true"����ʾ,������ʾ
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

function manuchkspecmain()
{
	document.getElementById("fm").submit();
}

function query()
{
	// ��ʼ�����
	initQuestGrid();
	initContent();

	// ��дSQL���
	k++;
	var strSQL = "";
	var ifreply = fm.IfReply.value;
	var tOPos = fm.OperatePos.value;
	var tFlag = fm.Flag.value;
	var tContNo = fm.ContNo.value;

	if (tFlag == "")
	{
		alert("����λ�ô���ʧ��!");
		return "";
	}
	
	if (tFlag == "1"||tFlag == "3"||tFlag == "5")
	{
		if(tContNo == "")
		{
			alert("�����Ų���Ϊ��!");
			return "";
		}
	}
	//alert(ifreply);
	//tongmeng 2007-10-22 modify
	//״̬�ж�����б�¼��posΪ1
	//�˹��˱������������
	if (ifreply == "Y")
	{
	  if (tFlag == "1")
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(case OperatePos when '0' then '�µ�¼��/�����޸�/�����޸�' when '1' then '�˹��˱�' when '5' then '�µ�����' when '3' then '������޸ĸ�' when '3' then '������޸ĸ�'  when 'A' then '�µ�����' when 'I' then '�µ�����' else OperatePos end ),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,modifydate,OperatePos from Rnewissuepol where "+k+"="+k+" "				 	
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,modifydate,OperatePos from Rnewissuepol where "+k+"="+k+" "				 	
//				 + " and OperatePos in ('0','1','3','5','A','I','6')"
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and ReplyMan is not null "
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');
		sql_id = "RnewQuestQuerySql1";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewQuestQuerySql"); //ָ��ʹ�õ�properties�ļ���
		my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
		my_sql.addSubPara(manageCom);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('ContNo'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('BackObj'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('ManageCom'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('OManageCom'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('OperatePos'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('Quest'))[0].value);//ָ������Ĳ���
		str_sql = my_sql.getString();
	  }		
	  else if(tFlag == "2")
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,modifydate,OperatePos from Rnewissuepol where "+k+"="+k+" "				 	
//				 + " and OperatePos in ('1','5')"				
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				  + " and ReplyMan is not null "
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest')
//				 + " and replyresult is null";
		sql_id = "RnewQuestQuerySql2";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewQuestQuerySql"); //ָ��ʹ�õ�properties�ļ���
		my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
		my_sql.addSubPara(manageCom);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('ContNo'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('BackObj'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('ManageCom'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('OManageCom'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('OperatePos'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('Quest'))[0].value);//ָ������Ĳ���
		str_sql = my_sql.getString();
	  }
	  else if(tFlag == "3")
	  {
	  
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,modifydate,OperatePos from Rnewissuepol where "+k+"="+k+" "				 	
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and ReplyMan is not null "
//				 + " and ReplyResult is not null"
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');
		sql_id = "RnewQuestQuerySql3";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewQuestQuerySql"); //ָ��ʹ�õ�properties�ļ���
		my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
		my_sql.addSubPara(manageCom);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('ContNo'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('BackObj'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('ManageCom'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('OManageCom'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('OperatePos'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('Quest'))[0].value);//ָ������Ĳ���
		str_sql = my_sql.getString();
	  }
	  else if(tFlag == "4")
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,modifydate,OperatePos from Rnewissuepol where "+k+"="+k+" "				 	
//				 + " and OperatePos in ('0','1','5','A','I','6')"				 
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and ReplyMan is not null "
//				 + " and BackObjType = '4' "
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest')
//				 + " and replyresult is null";	
		sql_id = "RnewQuestQuerySql4";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewQuestQuerySql"); //ָ��ʹ�õ�properties�ļ���
		my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
		my_sql.addSubPara(manageCom);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('ContNo'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('ManageCom'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('OManageCom'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('OperatePos'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('Quest'))[0].value);//ָ������Ĳ���
		str_sql = my_sql.getString();
	  }
	  else if(tFlag == "5")
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,modifydate,OperatePos from Rnewissuepol where "+k+"="+k+" "				 	
//				 + " and OperatePos in ('0','1','5','A','I','6')"
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and ReplyMan is not null "
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');	
		sql_id = "RnewQuestQuerySql5";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewQuestQuerySql"); //ָ��ʹ�õ�properties�ļ���
		my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
		my_sql.addSubPara(manageCom);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('ContNo'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('BackObj'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('ManageCom'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('OManageCom'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('OperatePos'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('Quest'))[0].value);//ָ������Ĳ���
		str_sql = my_sql.getString();
	  }
	  else if(tFlag == "16")
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,modifydate,OperatePos from Rnewissuepol where "+k+"="+k+" "				 	
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and GrpContNo = (select grpcontno from lccont where contno = '"+tContNo+"')"
//				 + " and ReplyMan is not null "
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');	  	
		divButton.style.display = "none";
		sql_id = "RnewQuestQuerySql6";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewQuestQuerySql"); //ָ��ʹ�õ�properties�ļ���
		my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
		my_sql.addSubPara(manageCom);//ָ������Ĳ���
		my_sql.addSubPara(tContNo);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('BackObj'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('ManageCom'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('OManageCom'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('OperatePos'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('Quest'))[0].value);//ָ������Ĳ���
		str_sql = my_sql.getString();
	  }
	  else
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,modifydate,OperatePos from Rnewissuepol where "+k+"="+k+" "				 	
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and ReplyMan is not null "
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');
		sql_id = "RnewQuestQuerySql7";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewQuestQuerySql"); //ָ��ʹ�õ�properties�ļ���
		my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
		my_sql.addSubPara(manageCom);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('ContNo'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('BackObj'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('ManageCom'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('OManageCom'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('OperatePos'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('Quest'))[0].value);//ָ������Ĳ���
		str_sql = my_sql.getString();
	  }	  	  	
}
 else if (ifreply == "N")
	{
	  if (tFlag == "1")
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,'',OperatePos from Rnewissuepol where "+k+"="+k+" "				 	
//				 + " and OperatePos in ('0','1','3','5','A','I','6')"
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and ReplyMan is  null "
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');
		sql_id = "RnewQuestQuerySql8";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewQuestQuerySql"); //ָ��ʹ�õ�properties�ļ���
		my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
		my_sql.addSubPara(manageCom);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('ContNo'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('BackObj'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('ManageCom'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('OManageCom'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('OperatePos'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('Quest'))[0].value);//ָ������Ĳ���
		str_sql = my_sql.getString();
	  }
	  else if(tFlag == "2")
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,'',OperatePos from Rnewissuepol where "+k+"="+k+" "				 	
//				 + " and OperatePos in ('1','5')"				
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				  + " and ReplyMan is null "
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest')
//				 + " and replyresult is null";
		sql_id = "RnewQuestQuerySql9";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewQuestQuerySql"); //ָ��ʹ�õ�properties�ļ���
		my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
		my_sql.addSubPara(manageCom);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('ContNo'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('BackObj'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('ManageCom'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('OManageCom'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('OperatePos'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('Quest'))[0].value);//ָ������Ĳ���
		str_sql = my_sql.getString();
	  }
	  else if(tFlag == "3")
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,'',OperatePos from Rnewissuepol where "+k+"="+k+" "				 	
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and ReplyMan is  null "
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');
		sql_id = "RnewQuestQuerySql10";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewQuestQuerySql"); //ָ��ʹ�õ�properties�ļ���
		my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
		my_sql.addSubPara(manageCom);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('ContNo'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('BackObj'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('ManageCom'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('OManageCom'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('OperatePos'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('Quest'))[0].value);//ָ������Ĳ���
		str_sql = my_sql.getString();
	  }
	  else if(tFlag == "4")
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,'',OperatePos from Rnewissuepol where "+k+"="+k+" "				 	
//				 + " and OperatePos in ('0','1','5','A','I','6')"				 
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and ReplyMan is  null "
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest')
//				 + " and replyresult is null";	 
		sql_id = "RnewQuestQuerySql11";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewQuestQuerySql"); //ָ��ʹ�õ�properties�ļ���
		my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
		my_sql.addSubPara(manageCom);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('ContNo'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('BackObj'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('ManageCom'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('OManageCom'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('OperatePos'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('Quest'))[0].value);//ָ������Ĳ���
		str_sql = my_sql.getString();
	  }
	  else if(tFlag == "5")
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,'',OperatePos from Rnewissuepol where "+k+"="+k+" "				 	
//				 + " and OperatePos in ('0','1','5','A','I','6')"
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and ReplyMan is  null "
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');
		sql_id = "RnewQuestQuerySql12";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewQuestQuerySql"); //ָ��ʹ�õ�properties�ļ���
		my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
		my_sql.addSubPara(manageCom);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('ContNo'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('BackObj'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('ManageCom'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('OManageCom'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('OperatePos'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('Quest'))[0].value);//ָ������Ĳ���
		str_sql = my_sql.getString();
	  }
	  else if(tFlag == "16")
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,'',OperatePos from Rnewissuepol where "+k+"="+k+" "				 	
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and GrpContNo = (select grpcontno from lccont where contno = '"+tContNo+"')"
//				  + " and ReplyMan is  null "
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');	  	
		divButton.style.display = "none";
		sql_id = "RnewQuestQuerySql13";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewQuestQuerySql"); //ָ��ʹ�õ�properties�ļ���
		my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
		my_sql.addSubPara(manageCom);//ָ������Ĳ���
		my_sql.addSubPara(tContNo);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('BackObj'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('ManageCom'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('OManageCom'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('OperatePos'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('Quest'))[0].value);//ָ������Ĳ���
		str_sql = my_sql.getString();
	  }
	  else
	  {
	    //alert(1);
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,'',OperatePos from Rnewissuepol where "+k+"="+k+" "				 	
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and ReplyMan is  null "
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');
		sql_id = "RnewQuestQuerySql14";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewQuestQuerySql"); //ָ��ʹ�õ�properties�ļ���
		my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
		my_sql.addSubPara(manageCom);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('ContNo'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('BackObj'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('ManageCom'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('OManageCom'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('OperatePos'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('Quest'))[0].value);//ָ������Ĳ���
		str_sql = my_sql.getString();
	  }	  	  	
}
//�������֧
else
	{
		//alert(tOperatePos);
		//tongmeng 
	  if (tFlag == "1")
	  {
	    
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,(case ReplyMan when '' then '' else to_char(modifydate,'yyyy-mm-dd') end),OperatePos from Rnewissuepol where "+k+"="+k+" "				 	
//				 + " and OperatePos in ('0','1','3','5','A','I','6')"
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');
		sql_id = "RnewQuestQuerySql15";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewQuestQuerySql"); //ָ��ʹ�õ�properties�ļ���
		my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
		my_sql.addSubPara(manageCom);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('ContNo'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('BackObj'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('ManageCom'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('OManageCom'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('OperatePos'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('Quest'))[0].value);//ָ������Ĳ���
		str_sql = my_sql.getString();
	  }		
	  else if(tFlag == "2")
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,(case ReplyMan when '' then '' else to_char(modifydate,'yyyy-mm-dd') end),OperatePos from Rnewissuepol where "+k+"="+k+" "				 	
//				 + " and OperatePos in ('1','5')"
//				 + " and backobjtype = '1'"
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest')
//				 + " and replyresult is null";
		sql_id = "RnewQuestQuerySql16";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewQuestQuerySql"); //ָ��ʹ�õ�properties�ļ���
		my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
		my_sql.addSubPara(manageCom);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('ContNo'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('BackObj'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('ManageCom'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('OManageCom'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('OperatePos'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('Quest'))[0].value);//ָ������Ĳ���
		str_sql = my_sql.getString();
	  }
	  else if(tFlag == "3")
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,(case ReplyMan when '' then '' else to_char(modifydate,'yyyy-mm-dd') end),OperatePos from Rnewissuepol where "+k+"="+k+" "				 	
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and state is not null "
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');
		sql_id = "RnewQuestQuerySql17";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewQuestQuerySql"); //ָ��ʹ�õ�properties�ļ���
		my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
		my_sql.addSubPara(manageCom);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('ContNo'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('BackObj'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('ManageCom'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('OManageCom'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('OperatePos'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('Quest'))[0].value);//ָ������Ĳ���
		str_sql = my_sql.getString();
	  }
	  else if(tFlag == "4")
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos) ),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,(case ReplyMan when '' then '' else to_char(modifydate,'yyyy-mm-dd') end),OperatePos from Rnewissuepol where "+k+"="+k+" "				 	
//				 + " and OperatePos in ('0','1','3','5','6')"				 
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');
		sql_id = "RnewQuestQuerySql18";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewQuestQuerySql"); //ָ��ʹ�õ�properties�ļ���
		my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
		my_sql.addSubPara(manageCom);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('ContNo'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('BackObj'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('ManageCom'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('OManageCom'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('OperatePos'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('Quest'))[0].value);//ָ������Ĳ���
		str_sql = my_sql.getString();
	  }
	  else if(tFlag == "5")
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,(case ReplyMan when '' then '' else to_char(modifydate,'yyyy-mm-dd') end),OperatePos from Rnewissuepol where "+k+"="+k+" "				 	
//				 + " and OperatePos in ('0','1','5','A','I','6')"
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');	  
		sql_id = "RnewQuestQuerySql19";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewQuestQuerySql"); //ָ��ʹ�õ�properties�ļ���
		my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
		my_sql.addSubPara(manageCom);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('ContNo'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('BackObj'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('ManageCom'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('OManageCom'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('OperatePos'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('Quest'))[0].value);//ָ������Ĳ���
		str_sql = my_sql.getString();
	  }
	  else if(tFlag == "16")
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,(case ReplyMan when '' then '' else to_char(modifydate,'yyyy-mm-dd') end),OperatePos from Rnewissuepol where "+k+"="+k+" "				 	
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and GrpContNo = (select grpcontno from lccont where contno = '"+tContNo+"')"
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');	  	
		divButton.style.display = "none";
		sql_id = "RnewQuestQuerySql20";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewQuestQuerySql"); //ָ��ʹ�õ�properties�ļ���
		my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
		my_sql.addSubPara(manageCom);//ָ������Ĳ���
		my_sql.addSubPara(tContNo);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('BackObj'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('ManageCom'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('OManageCom'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('OperatePos'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('Quest'))[0].value);//ָ������Ĳ���
		str_sql = my_sql.getString();
	  }
	  else
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,(case ReplyMan when '' then '' else to_char(modifydate,'yyyy-mm-dd') end),OperatePos from Rnewissuepol where "+k+"="+k+" "				 	
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');
		sql_id = "RnewQuestQuerySql21";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewQuestQuerySql"); //ָ��ʹ�õ�properties�ļ���
		my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
		my_sql.addSubPara(manageCom);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('ContNo'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('BackObj'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('ManageCom'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('OManageCom'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('OperatePos'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('Quest'))[0].value);//ָ������Ĳ���
		str_sql = my_sql.getString();
	  }	  	  	
}
	
  //��ѯSQL,���ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(str_sql, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (turnPage.strQueryResult == false) {
    alert("û�������");
    return "";
  }
  
  //��ѯ�ɹ������ַ���,���ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����,HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = QuestGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  return true;	
}

function queryone(parm1,parm2)
{	
	k++;
	var strSQL = "";
	var tcho;
	var tOperatePos = fm.Flag.value;
	var tselno=QuestGrid.getSelNo()-1;
	//tongmeng 2007-10-22 modify
	//ֱ��ȡ����λ�á�
	//tPos=QuestGrid.getRowColData(tselno,7);
	tPos=QuestGrid.getRowColData(tselno,13);
	//�������ַ���ԭΪ����,������sql��ѯʱ����
	if(tPos == '�µ�¼��/�����޸�/�����޸�')
	{
		tPos = 0;
	}
    else if(tPos == '�˹��˱�')
    {
    	tPos = 1;
    }
    else if(tPos == '�µ�����') 
    {
    	tPos = 5;
    }	
	tQuest = QuestGrid.getRowColData(tselno,2);;
	tSerialNo = QuestGrid.getRowColData(tselno,10);;
	
	document.all('HideOperatePos').value=tPos;
	document.all('HideQuest').value=tQuest;
	document.all('HideSerialNo').value=tSerialNo;
	document.all('SerialNo').value=tSerialNo;
	tContNo = fm.ContNo.value;
	if (tPos == "")
	{
		alert("��ѡ�������!")
		return "";
	}	
	
	if (tContNo == "")
	{
		alert("�����Ų���Ϊ�գ�");
		return "";
	}
	if (tQuest == "")
	{
		alert("���������Ϊ�գ�");
		return "";
	}
	if (tSerialNo == "")
	{
		alert("��������벻��Ϊ�գ�");
		return "";
	}
	
	if (tOperatePos == "16")
	{
	
//		strSQL = "select issuecont,substr(replyresult,1,76),issuetype,OperatePos from lcgrpissuepol where "+k+"="+k+" "
//						+ " and grpcontno = (select grpcontno from lccont where contno = '"+tContNo+"')";
		sql_id = "RnewQuestQuerySql22";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewQuestQuerySql"); //ָ��ʹ�õ�properties�ļ���
		my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
		my_sql.addSubPara(tContNo);//ָ������Ĳ���
		str_sql = my_sql.getString();
	}
	else
	{
//		strSQL = "select issuecont,substr(replyresult,1,76),issuetype,OperatePos,operator,makedate,modifydate from Rnewissuepol where "+k+"="+k+" "
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'issuetype','HideQuest' )
//				 + getWherePart( 'SerialNo','HideSerialNo');
				 if(document.all('HideOperatePos').value=="5")
				 {
//					strSQL = strSQL + " and OperatePos in ('5','I','A')";
					sql_id = "RnewQuestQuerySql23";
					my_sql = new SqlClass();
					my_sql.setResourceName("uw.RnewQuestQuerySql"); //ָ��ʹ�õ�properties�ļ���
					my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
					my_sql.addSubPara(document.getElementsByName(trim('ContNo'))[0].value);//ָ������Ĳ���
					my_sql.addSubPara(document.getElementsByName(trim('HideQuest'))[0].value);//ָ������Ĳ���
					my_sql.addSubPara(document.getElementsByName(trim('HideSerialNo'))[0].value);//ָ������Ĳ���
					str_sql = my_sql.getString();
				 }
				 else
				 {
//					strSQL = strSQL + getWherePart( 'OperatePos','HideOperatePos');
					 sql_id = "RnewQuestQuerySql24";
						my_sql = new SqlClass();
						my_sql.setResourceName("uw.RnewQuestQuerySql"); //ָ��ʹ�õ�properties�ļ���
						my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
						my_sql.addSubPara(document.getElementsByName(trim('ContNo'))[0].value);//ָ������Ĳ���
						my_sql.addSubPara(document.getElementsByName(trim('HideQuest'))[0].value);//ָ������Ĳ���
						my_sql.addSubPara(document.getElementsByName(trim('HideSerialNo'))[0].value);//ָ������Ĳ���
						my_sql.addSubPara(document.getElementsByName(trim('HideOperatePos'))[0].value);//ָ������Ĳ���
						str_sql = my_sql.getString();
				 }
				 
	}
  //��ѯSQL,���ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(my_sql, 1, 0, 1);  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("û��¼����������");
    return "";
  }
  
  //��ѯ�ɹ������ַ���,���ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  var returnstr = "";
  var tcont = "";
  var treply = "";
  var ttype = "";
  var tOperatePos = "";
  var tOperator="";
  var tMakeDate = "";
  var tModifyDate="";
  var n = turnPage.arrDataCacheSet.length;
  //alert("N:"+n);
  if (n > 0)
  {
  	m = turnPage.arrDataCacheSet[0].length;
  		//alert("M:"+m);
  		if (m > 1)
  		{
  			//alert("turnPage:"+turnPage.arrDataCacheSet[0][0]);
			tcont = turnPage.arrDataCacheSet[0][0];
			treply = turnPage.arrDataCacheSet[0][1];
			ttype = turnPage.arrDataCacheSet[0][2];
			tOperatePos = turnPage.arrDataCacheSet[0][3];
			tOperator= turnPage.arrDataCacheSet[0][4];
			tMakeDate = turnPage.arrDataCacheSet[0][5];
			tModifyDate= turnPage.arrDataCacheSet[0][6];
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
  
  if (treply == "") {
    document.all('ReplyResult').readOnly = false;
    canReplyFlag = true;
  }
  else {
    document.all('ReplyResult').readOnly = true;
    canReplyFlag = false;
  }
  document.all('ReplyResult').value = treply;
  
  document.all('Type').value = ttype;
  document.all('OperatePos').value = tOperatePos;
   document.all('Operator').value = tOperator;
  
  document.all('MakeDate').value = tMakeDate;
  //document.all('ReplyDate').value = tModifyDate;
  return returnstr;
}

function input()
{
	cContNo = fm.ContNo.value;  //��������
	
	//showModalDialog("./QuestInputMain.jsp?ContNo1="+cContNo+"&Flag="+cFlag,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
	window.open("./QuestInputMain.jsp?ContNo1="+cContNo+"&Flag="+cflag,"window1");
	
}

function quickReply() {
  if (QuestGrid.getSelNo() == "0") {
    alert("����ѡ��һ���������");
    return;
  }
  
  fm.ReplyResult.value = "�����޸���ɣ�";
  reply();
}
function replySave()
{
		fm.QuesFlag.value = "allover";   //��"�ظ����"��־
		fm.action = "./QuestReplyChk.jsp";
		document.getElementById("fm").submit();	
}
function reply()
{ 
	fm.QuesFlag.value = "";
  if (fm.ReplyResult.value == "") {
    alert("û�лظ�����,���ܻظ���");  
    return;
  }
  
  if (!canReplyFlag) {
    alert("��������ѻظ�,�����ٴλظ���");  
    return;
  }
  
	cContNo = fm.ContNo.value;  //��������
	cQuest = fm.HideQuest.value;            //���������
	cflag = fm.HideOperatePos.value;        //���������λ�� 
	
	fm.ContNoHide.value = fm.ContNo.value;  //��������
	fm.Quest.value = fm.HideQuest.value;            //���������
	fm.Flag.value = fm.HideOperatePos.value;        //���������λ�� 
	fm.SerialNo.value = fm.HideSerialNo.value;
	if(cQuest == "")
	{
		alert("��ѡ��Ҫ�ظ������!");
	}
	else
	{
		//showModalDialog("./QuestInputMain.jsp?ContNo1="+cContNo+"&Flag="+cFlag,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
		//window.open("./QuestReplyMain.jsp?ContNo1="+cContNo+"&Flag="+cflag+"&Quest="+cQuest,"window2");
		fm.action = "./RnewQuestReplyChk.jsp";
		document.getElementById("fm").submit();
	}
	
}

function QuestQuery(tContNo, tFlag)
{
	// ��ʼ�����
	var i,j,m,n;
	//initQuestGrid();
	
	
	// ��дSQL���
	k++;
	var strSQL = "";
//		strSQL = "select code,cont from ldcodemod where "+k+"="+k				 	
//				 + " and codetype = 'Question'";
		 sql_id = "RnewQuestQuerySql25";
			my_sql = new SqlClass();
			my_sql.setResourceName("uw.RnewQuestQuerySql"); //ָ��ʹ�õ�properties�ļ���
			my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
			str_sql = my_sql.getString();
  //��ѯSQL,���ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(str_sql, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("û�����������");
    return "";
  }
  
  //��ѯ�ɹ������ַ���,���ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����,HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  //turnPage.pageDisplayGrid = QuestGrid;    
          
  //����SQL���
  //turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  //turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  //var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //����MULTILINE������ʾ��ѯ���
  //displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  var returnstr = "";
  var n = turnPage.arrDataCacheSet.length;  
    if (n > 0)
  {
  	for( i = 0;i < n; i++)
  	{
  		m = turnPage.arrDataCacheSet[i].length;
  		//alert("M:"+m);
  		if (m > 0)
  		{
  			for( j = 0; j< m; j++)
  			{
  				if (i == 0 && j == 0)
  				{
  					returnstr = "0|^"+turnPage.arrDataCacheSet[i][j];
  				}
  				if (i == 0 && j > 0)
  				{
  					returnstr = returnstr + "|" + turnPage.arrDataCacheSet[i][j];
  				}
  				if (i > 0 && j == 0)
  				{
  					returnstr = returnstr+"^"+turnPage.arrDataCacheSet[i][j];
  				}
  				if (i > 0 && j > 0)
  				{
  					returnstr = returnstr+"|"+turnPage.arrDataCacheSet[i][j];
  				}
  				
  			}
  		}
  		else
  		{
  			alert("��ѯʧ��!!");
  			return "";
  		}
  	}
}
else
{
	alert("��ѯʧ��!");
	return "";
}
  //alert("returnstr:"+returnstr);		
  fm.Quest.CodeData = returnstr;
  return "";	
}


function QuestPicQuery(){
	//alert("�����С���");
	 var tsel=QuestGrid.getSelNo()-1; 
	 //alert(ContNo); 
	 if(tsel<0){
     alert("��ѡ�񱣵�!");
     retutn;	 
   }
   var ContNo=QuestGrid.getRowColData(tsel,1); 
	window.open("./ImageQueryMain.jsp?ContNo="+ContNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");								
 	
}

//������ظ���Ϻ���
function finishMission()
{
	//�ж��Ƿ�������Ҫ�ظ�������������˻ظ�
//	var		strSQL = "select * from Rnewissuepol where 1=1 "				 			 
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + " and replyresult is not null "
//				 + " and state is not null and state='1' ";
	 sql_id = "RnewQuestQuerySql26";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewQuestQuerySql"); //ָ��ʹ�õ�properties�ļ���
		my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
		my_sql.addSubPara(manageCom);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('ContNo'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('BackObj'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('ManageCom'))[0].value);//ָ������Ĳ���
		my_sql.addSubPara(document.getElementsByName(trim('OManageCom'))[0].value);//ָ������Ĳ���
		str_sql = my_sql.getString();
	var brr = easyExecSql(str_sql);
	if ( brr )  //���еĶ�������.
 	{
 		var showStr="���ڱ�������,�����Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.QuesFlag.value = "allover";
 		fm.action = "./RnewQuestReplyChk.jsp";
 		document.all('hiddenOperate').value="finishMission";
 		document.getElementById("fm").submit();
	}
	else
	{
		alert("��δ�ظ��Ļ��������,���Ȼظ�");
		return false;
	}
}
