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
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var cflag = "";  //���������λ�� 1.�˱�
var canReplyFlag = false;
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";

//�ύ,���水ť��Ӧ����
function submitForm()
{
  var i = 0;
  var showStr="���ڱ�������,�����Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   

  document.getElementById("fm").submit(); //�ύ
}

//�ύ,���水ť��Ӧ����
function IfPrint()
{
  var i = 0;
  var showStr="���ڱ�������,�����Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
��cContNo = fm.ContNo.value;  //��������	

	var sqlid19="QuestInputSql19";
var mySql19=new SqlClass();
mySql19.setResourceName("uw.QuestInputSql"); //ָ��ʹ�õ�properties�ļ���
mySql19.setSqlId(sqlid19);//ָ��ʹ�õ�Sql��id
mySql19.addSubPara(cContNo);//ָ������Ĳ���
��var strSql=mySql19.getString();		 

//��var strSql = "select * from LCPol where PolNo='" + cContNo + "' and  approveflag = '2'";
    var arrResult = easyExecSql(strSql);
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
	unlockScreen('lkscreen');  
	fm.ErrorFlagAction.value = "0";
	//query();
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
	var iHeight=250;     //�������ڵĸ߶�; 
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
  document.all('Quest').value="";
  initForm(document.all('ContNo').value,document.all('Flag').value,document.all('MissionID').value,document.all('SubMissionID').value);
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
	var tFlag = fm.Flag.value;
	// ��ʼ�����
	initQuestGrid(tFlag);
	initContent();

	// ��дSQL���
	k++;
	var strSQL = "";
	var ifreply = fm.IfReply.value;
	var tOPos = fm.OperatePos.value;
	
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
//	alert(mMasterCenter);
	if (ifreply == "Y")
	{

	  if (tFlag == "1")
	  {
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(case OperatePos when '0' then '�µ�¼��/�����޸�/�����޸�' when '1' then '�˹��˱�' when '5' then '�µ�����' when '3' then '������޸ĸ�' when '3' then '������޸ĸ�'  when 'A' then '�µ�����' when 'I' then '�µ�����' else OperatePos end ),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,modifydate,OperatePos from lcissuepol where "+k+"="+k+" "				 	
		
			var sqlid20="QuestInputSql20";
var mySql20=new SqlClass();
mySql20.setResourceName("uw.QuestInputSql"); //ָ��ʹ�õ�properties�ļ���
mySql20.setSqlId(sqlid20);//ָ��ʹ�õ�Sql��id
mySql20.addSubPara('1');//ָ������Ĳ���
mySql20.addSubPara(manageCom);//ָ������Ĳ���
mySql20.addSubPara(fm.ContNo.value);//ָ������Ĳ���
mySql20.addSubPara(fm.BackObj.value);//ָ������Ĳ���
mySql20.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
mySql20.addSubPara(fm.OManageCom.value);//ָ������Ĳ���
mySql20.addSubPara(fm.OperatePos.value);//ָ������Ĳ���
mySql20.addSubPara(fm.Quest.value);//ָ������Ĳ���
strSQL=mySql20.getString();	

//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,modifydate,OperatePos from lcissuepol where "+k+"="+k+" "				 	
//
//				 + " and OperatePos in ('0','1','3','5','A','I','6')"
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and ReplyMan is not null "
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');
	  }		
	  else if(tFlag == "2")
	  {
	  	
		var sqlid21="QuestInputSql21";
var mySql21=new SqlClass();
mySql21.setResourceName("uw.QuestInputSql"); //ָ��ʹ�õ�properties�ļ���
mySql21.setSqlId(sqlid21);//ָ��ʹ�õ�Sql��id
mySql21.addSubPara('1');//ָ������Ĳ���
mySql21.addSubPara(manageCom);//ָ������Ĳ���
mySql21.addSubPara(fm.ContNo.value);//ָ������Ĳ���
mySql21.addSubPara(fm.BackObj.value);//ָ������Ĳ���
mySql21.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
mySql21.addSubPara(fm.OManageCom.value);//ָ������Ĳ���
mySql21.addSubPara(fm.OperatePos.value);//ָ������Ĳ���
mySql21.addSubPara(fm.Quest.value);//ָ������Ĳ���
strSQL=mySql21.getString();	
		
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,modifydate,OperatePos from lcissuepol where "+k+"="+k+" "				 	
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
	  }
	  else if(tFlag == "3")
	  {
	  
	  		var sqlid22="QuestInputSql22";
var mySql22=new SqlClass();
mySql22.setResourceName("uw.QuestInputSql"); //ָ��ʹ�õ�properties�ļ���
mySql22.setSqlId(sqlid22);//ָ��ʹ�õ�Sql��id
mySql22.addSubPara('1');//ָ������Ĳ���
mySql22.addSubPara(manageCom);//ָ������Ĳ���
mySql22.addSubPara(fm.ContNo.value);//ָ������Ĳ���
mySql22.addSubPara(fm.BackObj.value);//ָ������Ĳ���
mySql22.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
mySql22.addSubPara(fm.OManageCom.value);//ָ������Ĳ���
mySql22.addSubPara(fm.OperatePos.value);//ָ������Ĳ���
mySql22.addSubPara(fm.Quest.value);//ָ������Ĳ���
strSQL=mySql22.getString();	
	  
	  
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,modifydate,OperatePos from lcissuepol where "+k+"="+k+" "				 	
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and ReplyMan is not null "
//				 + " and ReplyResult is not null"
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');
	  }
	  else if(tFlag == "4")
	  {
	  	
		var mySql23=new SqlClass();
		var sqlid23="QuestInputSql23";
mySql23.setResourceName("uw.QuestInputSql"); //ָ��ʹ�õ�properties�ļ���
mySql23.setSqlId(sqlid23);//ָ��ʹ�õ�Sql��id
mySql23.addSubPara('1');//ָ������Ĳ���
mySql23.addSubPara(manageCom);//ָ������Ĳ���
mySql23.addSubPara(fm.ContNo.value);//ָ������Ĳ���
mySql23.addSubPara(fm.BackObj.value);//ָ������Ĳ���
mySql23.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
mySql23.addSubPara(fm.OManageCom.value);//ָ������Ĳ���
mySql23.addSubPara(fm.OperatePos.value);//ָ������Ĳ���
mySql23.addSubPara(fm.Quest.value);//ָ������Ĳ���
strSQL=mySql23.getString();	
		
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,modifydate,OperatePos from lcissuepol where "+k+"="+k+" "				 	
//				 + " and OperatePos in ('0','1','5','A','I','6')"				 
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and ReplyMan is not null "
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest')
//				 + " and replyresult is null";	  
				 if(mMasterCenter=='Y')
				 {
				 	 strSQL = strSQL + " and needprint='Y' ";
				 }
	  }
	  else if(tFlag == "5")
	  {
	  	
				var mySql24=new SqlClass();
				var sqlid24="QuestInputSql24";
mySql24.setResourceName("uw.QuestInputSql"); //ָ��ʹ�õ�properties�ļ���
mySql24.setSqlId(sqlid24);//ָ��ʹ�õ�Sql��id
mySql24.addSubPara('1');//ָ������Ĳ���
mySql24.addSubPara(manageCom);//ָ������Ĳ���
mySql24.addSubPara(fm.BackObj.value);//ָ������Ĳ���
mySql24.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
mySql24.addSubPara(fm.OManageCom.value);//ָ������Ĳ���
mySql24.addSubPara(fm.OperatePos.value);//ָ������Ĳ���
mySql24.addSubPara(fm.Quest.value);//ָ������Ĳ���
mySql24.addSubPara(fm.ContNo.value);//ָ������Ĳ���
strSQL=mySql24.getString();	
		
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,modifydate,OperatePos from lcissuepol where "+k+"="+k+" "				 	
//				 + " and OperatePos in ('0','1','5','A','I','6')"
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and ReplyMan is not null "
//				 
////				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');	  	
//				 if(fm.ContNo.value!=null||fm.ContNo.value!=""){
//					 strSQL = strSQL+" and contno in (select contno from lccont where  prtno='"+fm.ContNo.value+"') " ;
//				 }
	  }
	  else if(tFlag == "16")
	  {
	  	
						var mySql25=new SqlClass();
						var sqlid25="QuestInputSql25";
mySql25.setResourceName("uw.QuestInputSql25"); //ָ��ʹ�õ�properties�ļ���
mySql25.setSqlId(sqlid25);//ָ��ʹ�õ�Sql��id
mySql25.addSubPara('1');//ָ������Ĳ���
mySql25.addSubPara(manageCom);//ָ������Ĳ���
mySql25.addSubPara(fm.ContNo.value);//ָ������Ĳ���
mySql25.addSubPara(fm.BackObj.value);//ָ������Ĳ���
mySql25.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
mySql25.addSubPara(fm.OManageCom.value);//ָ������Ĳ���
mySql25.addSubPara(fm.OperatePos.value);//ָ������Ĳ���
mySql25.addSubPara(fm.Quest.value);//ָ������Ĳ���

strSQL=mySql25.getString();	
		
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,modifydate,OperatePos from lcissuepol where "+k+"="+k+" "				 	
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and GrpContNo = (select grpcontno from lccont where contno = '"+tContNo+"')"
//				 + " and ReplyMan is not null "
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');	  	
		divButton.style.display = "none";
	  }
	  else
	  {
	  	
								var mySql26=new SqlClass();
								var sqlid26="QuestInputSql26";
mySql26.setResourceName("uw.QuestInputSql"); //ָ��ʹ�õ�properties�ļ���
mySql26.setSqlId(sqlid26);//ָ��ʹ�õ�Sql��id
mySql26.addSubPara('1');//ָ������Ĳ���
mySql26.addSubPara(manageCom);//ָ������Ĳ���
mySql26.addSubPara(fm.ContNo.value);//ָ������Ĳ���
mySql26.addSubPara(fm.BackObj.value);//ָ������Ĳ���
mySql26.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
mySql26.addSubPara(fm.OManageCom.value);//ָ������Ĳ���
mySql26.addSubPara(fm.OperatePos.value);//ָ������Ĳ���
mySql26.addSubPara(fm.Quest.value);//ָ������Ĳ���

strSQL=mySql26.getString();	
		
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,modifydate,OperatePos from lcissuepol where "+k+"="+k+" "				 	
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and ReplyMan is not null "
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');
	  }	  	  	
}
 else if (ifreply == "N")
	{
	  if (tFlag == "1")
	  {
	  	
										var mySql27=new SqlClass();
										var sqlid27="QuestInputSql27";
mySql27.setResourceName("uw.QuestInputSql"); //ָ��ʹ�õ�properties�ļ���
mySql27.setSqlId(sqlid27);//ָ��ʹ�õ�Sql��id
mySql27.addSubPara('1');//ָ������Ĳ���
mySql27.addSubPara(manageCom);//ָ������Ĳ���
mySql27.addSubPara(fm.ContNo.value);//ָ������Ĳ���
mySql27.addSubPara(fm.BackObj.value);//ָ������Ĳ���
mySql27.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
mySql27.addSubPara(fm.OManageCom.value);//ָ������Ĳ���
mySql27.addSubPara(fm.OperatePos.value);//ָ������Ĳ���
mySql27.addSubPara(fm.Quest.value);//ָ������Ĳ���

strSQL=mySql27.getString();	
		
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,'',OperatePos from lcissuepol where "+k+"="+k+" "				 	
//				 + " and OperatePos in ('0','1','3','5','A','I','6')"
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and ReplyMan is  null "
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');
	  }
	  else if(tFlag == "2")
	  {
	  	
var mySql28=new SqlClass();
var sqlid28="QuestInputSql28";
mySql28.setResourceName("uw.QuestInputSql"); //ָ��ʹ�õ�properties�ļ���
mySql28.setSqlId(sqlid28);//ָ��ʹ�õ�Sql��id
mySql28.addSubPara('1');//ָ������Ĳ���
mySql28.addSubPara(manageCom);//ָ������Ĳ���
mySql28.addSubPara(fm.ContNo.value);//ָ������Ĳ���
mySql28.addSubPara(fm.BackObj.value);//ָ������Ĳ���
mySql28.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
mySql28.addSubPara(fm.OManageCom.value);//ָ������Ĳ���
mySql28.addSubPara(fm.OperatePos.value);//ָ������Ĳ���
mySql28.addSubPara(fm.Quest.value);//ָ������Ĳ���

strSQL=mySql28.getString();	
		
		
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,'',OperatePos from lcissuepol where "+k+"="+k+" "				 	
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
	  }
	  else if(tFlag == "3")
	  {
	  	
		var mySql29=new SqlClass();
		var sqlid29="QuestInputSql29";
mySql29.setResourceName("uw.QuestInputSql"); //ָ��ʹ�õ�properties�ļ���
mySql29.setSqlId(sqlid29);//ָ��ʹ�õ�Sql��id
mySql29.addSubPara('1');//ָ������Ĳ���
mySql29.addSubPara(manageCom);//ָ������Ĳ���
mySql29.addSubPara(fm.ContNo.value);//ָ������Ĳ���
mySql29.addSubPara(fm.BackObj.value);//ָ������Ĳ���
mySql29.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
mySql29.addSubPara(fm.OManageCom.value);//ָ������Ĳ���
mySql29.addSubPara(fm.OperatePos.value);//ָ������Ĳ���
mySql29.addSubPara(fm.Quest.value);//ָ������Ĳ���

strSQL=mySql29.getString();	
		
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,'',OperatePos from lcissuepol where "+k+"="+k+" "				 	
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and ReplyMan is  null "
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');
	  }
	  else if(tFlag == "4")
	  {
	  	
				var mySql30=new SqlClass();
				var sqlid30="QuestInputSql30";
mySql30.setResourceName("uw.QuestInputSql"); //ָ��ʹ�õ�properties�ļ���
mySql30.setSqlId(sqlid30);//ָ��ʹ�õ�Sql��id
mySql30.addSubPara('1');//ָ������Ĳ���
mySql30.addSubPara(manageCom);//ָ������Ĳ���
mySql30.addSubPara(fm.ContNo.value);//ָ������Ĳ���
mySql30.addSubPara(fm.BackObj.value);//ָ������Ĳ���
mySql30.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
mySql30.addSubPara(fm.OManageCom.value);//ָ������Ĳ���
mySql30.addSubPara(fm.OperatePos.value);//ָ������Ĳ���
mySql30.addSubPara(fm.Quest.value);//ָ������Ĳ���

strSQL=mySql30.getString();	
		
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,'',OperatePos from lcissuepol where "+k+"="+k+" "				 	
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
				 if(mMasterCenter=='Y')
				 {
				 	 strSQL = strSQL + " and needprint='Y' ";
				 }
	  }
	  else if(tFlag == "5")
	  {
	  	
						var mySql31=new SqlClass();
						var sqlid31="QuestInputSql31";
mySql31.setResourceName("uw.QuestInputSql"); //ָ��ʹ�õ�properties�ļ���
mySql31.setSqlId(sqlid31);//ָ��ʹ�õ�Sql��id
mySql31.addSubPara('1');//ָ������Ĳ���
mySql31.addSubPara(manageCom);//ָ������Ĳ���
mySql31.addSubPara(fm.ContNo.value);//ָ������Ĳ���
mySql31.addSubPara(fm.BackObj.value);//ָ������Ĳ���
mySql31.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
mySql31.addSubPara(fm.OManageCom.value);//ָ������Ĳ���
mySql31.addSubPara(fm.OperatePos.value);//ָ������Ĳ���
mySql31.addSubPara(fm.Quest.value);//ָ������Ĳ���
strSQL=mySql31.getString();	
		
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,'',OperatePos from lcissuepol where "+k+"="+k+" "				 	
//				 + " and OperatePos in ('0','1','5','A','I','6')"
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and ReplyMan is  null "
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');	  	
	  }
	  else if(tFlag == "16")
	  {
	  	
								var mySql32=new SqlClass();
								var sqlid32="QuestInputSql32";
mySql32.setResourceName("uw.QuestInputSql"); //ָ��ʹ�õ�properties�ļ���
mySql32.setSqlId(sqlid32);//ָ��ʹ�õ�Sql��id
mySql32.addSubPara('1');//ָ������Ĳ���
mySql32.addSubPara(manageCom);//ָ������Ĳ���
mySql32.addSubPara(fm.ContNo.value);//ָ������Ĳ���
mySql32.addSubPara(fm.BackObj.value);//ָ������Ĳ���
mySql32.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
mySql32.addSubPara(fm.OManageCom.value);//ָ������Ĳ���
mySql32.addSubPara(fm.OperatePos.value);//ָ������Ĳ���
mySql32.addSubPara(fm.Quest.value);//ָ������Ĳ���
		strSQL=mySql32.getString();	
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,'',OperatePos from lcissuepol where "+k+"="+k+" "				 	
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and GrpContNo = (select grpcontno from lccont where contno = '"+tContNo+"')"
//				  + " and ReplyMan is  null "
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');	  	
		divButton.style.display = "none";
	  }
	  else
	  {
	    //alert(1);
		
var mySql33=new SqlClass();
var sqlid33="QuestInputSql33";
mySql33.setResourceName("uw.QuestInputSql"); //ָ��ʹ�õ�properties�ļ���
mySql33.setSqlId(sqlid33);//ָ��ʹ�õ�Sql��id
mySql33.addSubPara('1');//ָ������Ĳ���
mySql33.addSubPara(manageCom);//ָ������Ĳ���
mySql33.addSubPara(fm.ContNo.value);//ָ������Ĳ���
mySql33.addSubPara(fm.BackObj.value);//ָ������Ĳ���
mySql33.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
mySql33.addSubPara(fm.OManageCom.value);//ָ������Ĳ���
mySql33.addSubPara(fm.OperatePos.value);//ָ������Ĳ���
mySql33.addSubPara(fm.Quest.value);//ָ������Ĳ���
strSQL=mySql33.getString();	
		
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,'',OperatePos from lcissuepol where "+k+"="+k+" "				 	
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and ReplyMan is  null "
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');
	  }	  	  	
}
//�������֧
else
	{
//		alert(355);
		//tongmeng 
	  if (tFlag == "1")
	  {
	  	
		var mySql34=new SqlClass();
		var sqlid34="QuestInputSql34";
mySql34.setResourceName("uw.QuestInputSql"); //ָ��ʹ�õ�properties�ļ���
mySql34.setSqlId(sqlid34);//ָ��ʹ�õ�Sql��id
mySql34.addSubPara('1');//ָ������Ĳ���
//mySql34.addSubPara(k);//ָ������Ĳ���
//alert(fm.BackObj.value);
mySql34.addSubPara(manageCom);//ָ������Ĳ���
mySql34.addSubPara(fm.ContNo.value);//ָ������Ĳ���
mySql34.addSubPara(fm.BackObj.value);//ָ������Ĳ���
mySql34.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
mySql34.addSubPara(fm.OManageCom.value);//ָ������Ĳ���
mySql34.addSubPara(fm.OperatePos.value);//ָ������Ĳ���
mySql34.addSubPara(fm.Quest.value);//ָ������Ĳ���
		strSQL=mySql34.getString();	
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,decode(ReplyMan,'','',modifydate),OperatePos,nvl(errflag,'N') from lcissuepol where "+k+"="+k+" "				 	
//				 + " and OperatePos in ('0','1','3','5','A','I','6')"
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');
	  }		
	  else if(tFlag == "2")
	  {
	  	
var mySql35=new SqlClass();
var sqlid35="QuestInputSql35";
mySql35.setResourceName("uw.QuestInputSql"); //ָ��ʹ�õ�properties�ļ���
mySql35.setSqlId(sqlid35);//ָ��ʹ�õ�Sql��id
mySql35.addSubPara('1');//ָ������Ĳ���
mySql35.addSubPara(manageCom);//ָ������Ĳ���
mySql35.addSubPara(fm.ContNo.value);//ָ������Ĳ���
mySql35.addSubPara(fm.BackObj.value);//ָ������Ĳ���
mySql35.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
mySql35.addSubPara(fm.OManageCom.value);//ָ������Ĳ���
mySql35.addSubPara(fm.OperatePos.value);//ָ������Ĳ���
mySql35.addSubPara(fm.Quest.value);//ָ������Ĳ���
strSQL=mySql35.getString();			
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,decode(ReplyMan,'','',modifydate),OperatePos,nvl(errflag,'N') from lcissuepol where "+k+"="+k+" "				 	
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
	  }
	  else if(tFlag == "3")
	  {
	  	
		var mySql36=new SqlClass();
		var sqlid36="QuestInputSql36";
mySql36.setResourceName("uw.QuestInputSql"); //ָ��ʹ�õ�properties�ļ���
mySql36.setSqlId(sqlid36);//ָ��ʹ�õ�Sql��id
mySql36.addSubPara('1');//ָ������Ĳ���
//mySql36.addSubPara(k);//ָ������Ĳ���
mySql36.addSubPara(manageCom);//ָ������Ĳ���
mySql36.addSubPara(fm.ContNo.value);//ָ������Ĳ���
mySql36.addSubPara(fm.BackObj.value);//ָ������Ĳ���
mySql36.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
mySql36.addSubPara(fm.OManageCom.value);//ָ������Ĳ���
mySql36.addSubPara(fm.OperatePos.value);//ָ������Ĳ���
mySql36.addSubPara(fm.Quest.value);//ָ������Ĳ���
strSQL=mySql36.getString();	
		
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,decode((ReplyMan||decode(state,'','','2','2','')),'','',modifydate),OperatePos,nvl(errflag,'N') from lcissuepol where "+k+"="+k+" "				 	
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and state is not null "
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');
	  }
	  else if(tFlag == "4")
	  {
	  	
var mySql37=new SqlClass();
var sqlid37="QuestInputSql37";
mySql37.setResourceName("uw.QuestInputSql"); //ָ��ʹ�õ�properties�ļ���
mySql37.setSqlId(sqlid37);//ָ��ʹ�õ�Sql��id
mySql37.addSubPara('1');//ָ������Ĳ���
//mySql37.addSubPara(k);//ָ������Ĳ���
mySql37.addSubPara(manageCom);//ָ������Ĳ���
mySql37.addSubPara(fm.ContNo.value);//ָ������Ĳ���
mySql37.addSubPara(fm.BackObj.value);//ָ������Ĳ���
mySql37.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
mySql37.addSubPara(fm.OManageCom.value);//ָ������Ĳ���
mySql37.addSubPara(fm.OperatePos.value);//ָ������Ĳ���
//alert(fm.Quest.value);
mySql37.addSubPara(fm.Quest.value);//ָ������Ĳ���
mySql37.addSubPara(mMasterCenter);//ָ������Ĳ���
mySql37.addSubPara(mMasterCenter);//ָ������Ĳ���
		strSQL=mySql37.getString();	
		//prompt('',strSQL);
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos) ),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,decode(ReplyMan,'','',modifydate),OperatePos,nvl(errflag,'N') from lcissuepol where "+k+"="+k+" "				 	
//				 + " and OperatePos in ('0','1','3','5','6')"				 
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest')
//				 //+ " and replyresult is null"
//				 ;	  
				 //if(mMasterCenter=='Y')
				// {
				// 	 strSQL = strSQL + " and needprint=''Y' ";
				// }
				// prompt("",strSQL);
	  }
	  else if(tFlag == "5")
	  {
	  	
		var mySql38=new SqlClass();
		var sqlid38="QuestInputSql38";
mySql38.setResourceName("uw.QuestInputSql"); //ָ��ʹ�õ�properties�ļ���
mySql38.setSqlId(sqlid38);//ָ��ʹ�õ�Sql��id
mySql38.addSubPara('1');//ָ������Ĳ���
//mySql38.addSubPara(k);//ָ������Ĳ���
mySql38.addSubPara(manageCom);//ָ������Ĳ���
mySql38.addSubPara(fm.BackObj.value);//ָ������Ĳ���
mySql38.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
mySql38.addSubPara(fm.OManageCom.value);//ָ������Ĳ���
mySql38.addSubPara(fm.OperatePos.value);//ָ������Ĳ���
mySql38.addSubPara(fm.Quest.value);//ָ������Ĳ���
mySql38.addSubPara(fm.ContNo.value);//ָ������Ĳ���
		strSQL=mySql38.getString();	
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,decode(ReplyMan,'','',modifydate),OperatePos,nvl(errflag,'N') from lcissuepol where "+k+"="+k+" "				 	
//				 + " and OperatePos in ('0','1','5','A','I','6')"
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 
////				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');	
//				 if(fm.ContNo.value!=null||fm.ContNo.value!=""){
//					 strSQL = strSQL+" and contno in (select contno from lccont where  prtno='"+fm.ContNo.value+"') "; 
//				 }  	
	  }
	  else if(tFlag == "16")
	  {
	  	
var mySql39=new SqlClass();
var sqlid39="QuestInputSql39";
mySql39.setResourceName("uw.QuestInputSql"); //ָ��ʹ�õ�properties�ļ���
mySql39.setSqlId(sqlid39);//ָ��ʹ�õ�Sql��id
mySql39.addSubPara('1');//ָ������Ĳ���
mySql39.addSubPara(manageCom);//ָ������Ĳ���
mySql39.addSubPara(tContNo);//ָ������Ĳ���
mySql39.addSubPara(fm.BackObj.value);//ָ������Ĳ���
mySql39.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
mySql39.addSubPara(fm.OManageCom.value);//ָ������Ĳ���
mySql39.addSubPara(fm.OperatePos.value);//ָ������Ĳ���
mySql39.addSubPara(fm.Quest.value);//ָ������Ĳ���
		strSQL=mySql39.getString();	
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,decode(ReplyMan,'','',modifydate),OperatePos,nvl(errflag,'N') from lcissuepol where "+k+"="+k+" "				 	
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + " and GrpContNo = (select grpcontno from lccont where contno = '"+tContNo+"')"
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');	  	
		divButton.style.display = "none";
	  }
	  else
	  {
	  	
		var mySql40=new SqlClass();
		var sqlid40="QuestInputSql40";
mySql40.setResourceName("uw.QuestInputSql"); //ָ��ʹ�õ�properties�ļ���
mySql40.setSqlId(sqlid40);//ָ��ʹ�õ�Sql��id
mySql40.addSubPara('1');//ָ������Ĳ���
mySql40.addSubPara(manageCom);//ָ������Ĳ���
mySql40.addSubPara(tContNo);//ָ������Ĳ���
mySql40.addSubPara(fm.BackObj.value);//ָ������Ĳ���
mySql40.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
mySql40.addSubPara(fm.OManageCom.value);//ָ������Ĳ���
mySql40.addSubPara(fm.OperatePos.value);//ָ������Ĳ���
mySql40.addSubPara(fm.Quest.value);//ָ������Ĳ���
		strSQL=mySql40.getString();	
		
//		strSQL = "select ContNo,issuetype,issuecont,substr(replyresult,1,76),operator,makedate,(select codename from ldcode where codetype='operatepos' and trim(code)=trim(OperatePos)),(case when BackObjType='1' or BackObjType='3' or BackObjType='4' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType) when BackObjType='2' then (select codename from ldcode where codetype='backobj' and comcode=BackObjType and othersign=standbyflag2) end ),needprint,serialno,makedate,decode(ReplyMan,'','',modifydate),OperatePos,nvl(errflag,'N') from lcissuepol where "+k+"="+k+" "				 	
//				 + " and IsueManageCom like '" + manageCom + "%%'"
//				 + getWherePart( 'ContNo','ContNo' )
//				 + getWherePart( 'BackObjType','BackObj' )
//				 + getWherePart( 'ManageCom','ManageCom' )
//				 + getWherePart( 'IsueManageCom','OManageCom')
//				 + getWherePart( 'OperatePos','OperatePos')
//				 + getWherePart( 'IssueType','Quest');
	  }	  	  	
}
	
	turnPage1.queryModal(strSQL,QuestGrid);
  //��ѯSQL,���ؽ���ַ���
 /* turnPage1.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (turnPage1.strQueryResult == false) {
    alert("û�������");
    return "";
  }
  
  //��ѯ�ɹ������ַ���,���ض�ά����
  turnPage1.arrDataCacheSet = decodeEasyQueryResult(turnPage1.strQueryResult);
  
  //���ó�ʼ������MULTILINE����,HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage1.pageDisplayGrid = QuestGrid;    
          
  //����SQL���
  turnPage1.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage1.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage1.getData(turnPage1.arrDataCacheSet, turnPage1.pageIndex, MAXSCREENLINES);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage1.pageDisplayGrid);
  */
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
	
	var tErrFlag = "";
	tErrFlag = QuestGrid.getRowColData(tselno,14);
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
	//alert(tSerialNo);
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
	
		var mySql41=new SqlClass();
		var sqlid41="QuestInputSql41";
mySql41.setResourceName("uw.QuestInputSql"); //ָ��ʹ�õ�properties�ļ���
mySql41.setSqlId(sqlid41);//ָ��ʹ�õ�Sql��id
mySql41.addSubPara('1');//ָ������Ĳ���
//mySql41.addSubPara(k);//ָ������Ĳ���
mySql41.addSubPara(tContNo);//ָ������Ĳ���

strSQL=mySql41.getString();	
	
//		strSQL = "select issuecont,substr(replyresult,1,76),issuetype,OperatePos from lcgrpissuepol where "+k+"="+k+" "
//						+ " and grpcontno = (select grpcontno from lccont where contno = '"+tContNo+"')";
	}
	else
	{
		
				var mySql42=new SqlClass();
				var sqlid42="QuestInputSql42";
				var addStr = " and 1 = 1 ";
mySql42.setResourceName("uw.QuestInputSql"); //ָ��ʹ�õ�properties�ļ���
mySql42.setSqlId(sqlid42);//ָ��ʹ�õ�Sql��id
mySql42.addSubPara('1');//ָ������Ĳ���
//mySql42.addSubPara(k);//ָ������Ĳ���
mySql42.addSubPara(fm.HideQuest.value);//ָ������Ĳ���
mySql42.addSubPara(fm.HideSerialNo.value);//ָ������Ĳ���
mySql42.addSubPara(fm.ContNo.value);//ָ������Ĳ���
		
//		strSQL = "select issuecont,substr(replyresult,1,76),issuetype,OperatePos,operator,makedate,modifydate from lcissuepol where "+k+"="+k+" "
//				 + getWherePart( 'issuetype','HideQuest' )
//				 + getWherePart( 'serialno','HideSerialNo')
//				 if(fm.ContNo.value!=null||fm.ContNo.value!=""){
//					 strSQL = strSQL+" and contno in (select contno from lccont where prtno='"+fm.ContNo.value+"')";
//				 }
				 //alert(fm.HideSerialNo.value);
				 if(document.all('HideOperatePos').value=="5")
				 {
					 addStr = " and OperatePos in ('5','I','A')";
				 }
				 else
				 {
					 addStr = getWherePart( 'OperatePos','HideOperatePos');
				 }
				 mySql42.addSubPara(addStr);
				 strSQL=mySql42.getString();	
	}
  //��ѯSQL,���ؽ���ַ���
  turnPage2.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  //prompt("",strSQL);
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage2.strQueryResult) {
var addStr1 = " and 1=1 ";
var addStr2 = " and 2=2 ";  	
var mySql43=new SqlClass();
var sqlid43="QuestInputSql43";
mySql43.setResourceName("uw.QuestInputSql"); //ָ��ʹ�õ�properties�ļ���
mySql43.setSqlId(sqlid43);//ָ��ʹ�õ�Sql��id
//mySql43.addSubPara(k);//ָ������Ĳ���
//mySql43.addSubPara(k);//ָ������Ĳ���
mySql43.addSubPara(fm.HideQuest.value);//ָ������Ĳ���
mySql43.addSubPara(fm.HideSerialNo.value);//ָ������Ĳ���

//strSQL=mySql43.getString();	
	
//	  strSQL = "select issuecont,substr(replyresult,1,76),issuetype,OperatePos,operator,makedate,modifydate from lcissuepol where "+k+"="+k+" "
//		 + getWherePart( 'issuetype','HideQuest' )
//		 + getWherePart( 'serialno','HideSerialNo')
		 if(fm.ContNo.value!=null||fm.ContNo.value!=""){
//			 strSQL = strSQL+" and contno = '"+fm.ContNo.value+"'";
             addStr1 = " and contno = '"+fm.ContNo.value+"'";
		 }
		 //alert(fm.HideSerialNo.value);
		 if(document.all('HideOperatePos').value=="5")
		 {
//			strSQL = strSQL + " and OperatePos in ('5','I','A')";
            addStr2 = " and OperatePos in ('5','I','A')";
		 }
		 else
		 {
			addStr2 = getWherePart( 'OperatePos','HideOperatePos');
		 }
mySql43.addSubPara(addStr1);//ָ������Ĳ���
mySql43.addSubPara(addStr2);//ָ������Ĳ���
strSQL=mySql43.getString();
		 turnPage2.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  //prompt("",strSQL);
		 if(!turnPage2.strQueryResult){
		    alert("û��¼����������");
		    return "";
		 }
  }
  
  //��ѯ�ɹ������ַ���,���ض�ά����
  turnPage2.arrDataCacheSet = decodeEasyQueryResult(turnPage2.strQueryResult);
  
  var returnstr = "";
  var tcont = "";
  var treply = "";
  var ttype = "";
  var tOperatePos = "";
  var tOperator="";
  var tMakeDate = "";
  var tModifyDate="";
  var n = turnPage2.arrDataCacheSet.length;
  //alert("N:"+n);
  if (n > 0)
  {
  	m = turnPage2.arrDataCacheSet[0].length;
  		//alert("M:"+m);
  		if (m > 1)
  		{
  			//alert("turnPage1:"+turnPage1.arrDataCacheSet[0][0]);
			tcont = turnPage2.arrDataCacheSet[0][0];
			treply = turnPage2.arrDataCacheSet[0][1];
			ttype = turnPage2.arrDataCacheSet[0][2];
			tOperatePos = turnPage2.arrDataCacheSet[0][3];
			tOperator= turnPage2.arrDataCacheSet[0][4];
			tMakeDate = turnPage2.arrDataCacheSet[0][5];
			tModifyDate= turnPage2.arrDataCacheSet[0][6];
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
  document.all('Type').value = ttype;
  
   document.all('ErrorFlag').value = tErrFlag;
   
   var mySql44=new SqlClass();
   var sqlid44="QuestInputSql44";
mySql44.setResourceName("uw.QuestInputSql"); //ָ��ʹ�õ�properties�ļ���
mySql44.setSqlId(sqlid44);//ָ��ʹ�õ�Sql��id
mySql44.addSubPara(tErrFlag);//ָ������Ĳ���

   var tSQL_Err=mySql44.getString();	
   
   //var tSQL_Err = "select codename from ldcode where codetype='yesno' and code='"+tErrFlag+"' ";
   var errResult = easyExecSql(tSQL_Err);
    var SubType="";
    if(errResult!=null){
      document.all('ErrorFlagName').value = errResult[0][0];
    }
  return returnstr;
}

function input()
{
	cContNo = fm.ContNo.value;  //��������
	
	//showModalDialog("./QuestInputMain.jsp?ContNo1="+cContNo+"&Flag="+cFlag,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
	window.open("./QuestInputMain.jsp?ContNo1="+cContNo+"&Flag="+cflag,"window1",sFeatures);
	
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
	var tsel=QuestGrid.getSelNo()-1; 
   if(tsel<0){
     alert("��ѡ��Ҫ�ظ������!");
     return;	 
   }
   var replydate=QuestGrid.getRowColData(tsel,12);
   //alert(replydate);
  if (replydate!='') {
    alert("��������ѻظ�,�����ٴλظ���");  
    return;
  }

	fm.QuesFlag.value = "";
  if (fm.ReplyResult.value == "") {
    alert("û�лظ�����,���ܻظ���");  
    return;
  }  
  
	cContNo = fm.ContNo.value;  //��������
	cQuest = fm.HideQuest.value;            //���������
	cflag = fm.HideOperatePos.value;        //���������λ�� 
	//add by lzf 
	ActivityID = fm.ActivityID.value;   //���
	//alert("ActivityID-------->"+ActivityID);return false;
	fm.ContNoHide.value = fm.ContNo.value;  //��������
	fm.Quest.value = fm.HideQuest.value;            //���������
	//fm.Flag.value = fm.HideOperatePos.value;        //���������λ�� 
	fm.SerialNo.value = fm.HideSerialNo.value;
	if(cQuest == "")
	{
		alert("��ѡ��Ҫ�ظ������!");
	}
	else
	{
		//showModalDialog("./QuestInputMain.jsp?ContNo1="+cContNo+"&Flag="+cFlag,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
		//window.open("./QuestReplyMain.jsp?ContNo1="+cContNo+"&Flag="+cflag+"&Quest="+cQuest,"window2");
		fm.action = "./QuestReplyChk.jsp";
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
	//if (tFlag == "1")
	//{
	
	   var mySql45=new SqlClass();
var sqlid45="QuestInputSql45";
mySql45.setResourceName("uw.QuestInputSql"); //ָ��ʹ�õ�properties�ļ���
mySql45.setSqlId(sqlid45);//ָ��ʹ�õ�Sql��id
mySql45.addSubPara("1");//ָ������Ĳ���

strSQL=mySql45.getString();	
	
//		strSQL = "select code,cont from ldcodemod where "+k+"="+k				 	
//				 + " and codetype = 'Question'";
	//}
	
	//alert(strSQL);
	
  //��ѯSQL,���ؽ���ַ���
  turnPage1.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage1.strQueryResult) {
    alert("û�����������");
    return "";
  }
  
  //��ѯ�ɹ������ַ���,���ض�ά����
  turnPage1.arrDataCacheSet = decodeEasyQueryResult(turnPage1.strQueryResult);
  
  //���ó�ʼ������MULTILINE����,HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  //turnPage1.pageDisplayGrid = QuestGrid;    
          
  //����SQL���
  //turnPage1.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  //turnPage1.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  //var arrDataSet           = turnPage1.getData(turnPage1.arrDataCacheSet, turnPage1.pageIndex, MAXSCREENLINES);
  
  //����MULTILINE������ʾ��ѯ���
  //displayMultiline(arrDataSet, turnPage1.pageDisplayGrid);
  var returnstr = "";
  var n = turnPage1.arrDataCacheSet.length;  
    if (n > 0)
  {
  	for( i = 0;i < n; i++)
  	{
  		m = turnPage1.arrDataCacheSet[i].length;
  		//alert("M:"+m);
  		if (m > 0)
  		{
  			for( j = 0; j< m; j++)
  			{
  				if (i == 0 && j == 0)
  				{
  					returnstr = "0|^"+turnPage1.arrDataCacheSet[i][j];
  				}
  				if (i == 0 && j > 0)
  				{
  					returnstr = returnstr + "|" + turnPage1.arrDataCacheSet[i][j];
  				}
  				if (i > 0 && j == 0)
  				{
  					returnstr = returnstr+"^"+turnPage1.arrDataCacheSet[i][j];
  				}
  				if (i > 0 && j > 0)
  				{
  					returnstr = returnstr+"|"+turnPage1.arrDataCacheSet[i][j];
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
//	 alert(ContNo); 
	 if(tsel<0){
     alert("��ѡ�񱣵�!");
     return;	 
   }
   var ContNo=QuestGrid.getRowColData(tsel,1); 
   if(ContNo.length!=14){
   	
		   var mySql46=new SqlClass();
		   var sqlid46="QuestInputSql46";
mySql46.setResourceName("uw.QuestInputSql"); //ָ��ʹ�õ�properties�ļ���
mySql46.setSqlId(sqlid46);//ָ��ʹ�õ�Sql��id
mySql46.addSubPara(ContNo);//ָ������Ĳ���

	   var PrtSql =mySql46.getString();	
	
	 //  var PrtSql = "select prtno from lccont where contno='"+ContNo+"'";
	   var ContNo = easyExecSql(PrtSql);
   }
	window.open("./ImageQueryMain.jsp?ContNo="+ContNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);								
 	
}

//������ظ���Ϻ���
function finishMission()
{
	var ActivityID = fm.ActivityID.value;
//	alert("ActivityID----"+ActivityID);
//	return false;
	//�ж��Ƿ�������Ҫ�ظ�������������˻ظ�
	//tongmeng 2009-05-18 modify
	//�����֧��needprint='Y'
	
			   var mySql47=new SqlClass();
			   var sqlid47="QuestInputSql47";
mySql47.setResourceName("uw.QuestInputSql"); //ָ��ʹ�õ�properties�ļ���
mySql47.setSqlId(sqlid47);//ָ��ʹ�õ�Sql��id
mySql47.addSubPara(manageCom);//ָ������Ĳ���
mySql47.addSubPara(fm.ContNo.value);//ָ������Ĳ���
mySql47.addSubPara(fm.BackObj.value);//ָ������Ĳ���
mySql47.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
mySql47.addSubPara(fm.OManageCom.value);//ָ������Ĳ���
 var strSQL =mySql47.getString();	
/*	
	var		strSQL = "select * from lcissuepol where 1=1 "				 	
				 + " and IsueManageCom like '" + manageCom + "%%'"
				 + getWherePart( 'ContNo','ContNo' )
				 + getWherePart( 'BackObjType','BackObj' )
				 + getWherePart( 'ManageCom','ManageCom' )
				 + getWherePart( 'IsueManageCom','OManageCom')
				 + " and replyresult is not null "
				 + " and state is not null and state='1' "
				 + " and needprint='Y' "
				 ;	*/  
	var brr = easyExecSql(strSQL);
	if ( brr )  //���еĶ�������.
 	{
 		var showStr="���ڱ�������,�����Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

 		fm.action = "./QuestMissionChk.jsp?MissionId="+tMissionID+"&SubMissionId="+tSubMissionID+"&ContNo="+tContNo+"&ActivtiyID="+ActivityID+" ";
 		document.all('hiddenOperate').value="finishMission";
 		lockScreen('lkscreen');  
 		document.getElementById("fm").submit();
	}
	else
	{
		alert("��δ�ظ��Ļ��������,���Ȼظ�");
		return false;
	}
}


//tongmeng 2009-03-26 add
//���Ӽ����󷢰�ť.


function UpdateErrorFlag()
{
	fm.QuesFlag.value = "";
  if (fm.ReplyResult.value == "") {
    alert("û�лظ�����,���Ȼظ����޸��󷢱��!");  
    return;
  }
  
	cContNo = fm.ContNo.value;  //��������
	cQuest = fm.HideQuest.value;            //���������
	cflag = fm.HideOperatePos.value;        //���������λ�� 
	
	fm.ContNoHide.value = fm.ContNo.value;  //��������
	fm.Quest.value = fm.HideQuest.value;            //���������
	//fm.Flag.value = fm.HideOperatePos.value;        //���������λ�� 
	fm.SerialNo.value = fm.HideSerialNo.value;
	fm.ErrorFlagAction.value = "1";
	if(cQuest == "")
	{
		alert("��ѡ��Ҫ�����󷢵������!");
	}
	else
	{
		//showModalDialog("./QuestInputMain.jsp?ContNo1="+cContNo+"&Flag="+cFlag,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
		//window.open("./QuestReplyMain.jsp?ContNo1="+cContNo+"&Flag="+cflag+"&Quest="+cQuest,"window2");
		fm.action = "./QuestReplyChk.jsp";
		document.getElementById("fm").submit();
	}
	
}
