//�������ƣ�EdorAppManuUW.js
//�����ܣ���ȫ�˹��˱�
//�������ڣ�2005-05-04 16:49:22
//������  ��zhangtao
//���¼�¼��  ������ liurongxiao   ��������   2005-06-20  ����ԭ��/���� ������Լ����ȡ��һ��;��Ӱ�ť����

var showInfo;
var turnPage = new turnPageClass();
var turnPageEdorMainGrid = new turnPageClass();
var turnPageInsured = new turnPageClass();
var cflag = "1";  //���������λ�� 1.�˱�
var tContNo="";
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
var sqlresourcename = "bq.EdorAppManuUWInputSql";

/*********************************************************************
 *  ��ѯ��ȫ������Ϣ
 *  ����: ��ѯ��ȫ������Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function initQuery()
{
    var EdorAcceptNo    = document.all('EdorAcceptNo').value;
    var EdorNo = "";
    var MissionID       = document.all('MissionID').value;
    var SubMissionID    = document.all('SubMissionID').value;
    //var ActivityStatus  = document.all('ActivityStatus').value;
    var ActivityStatus;
    var strSQL;
    if(EdorAcceptNo == null || EdorAcceptNo == "")
    {
        alert("��ȫ�����Ϊ�գ�");
        return;
    }
    if(MissionID == null || MissionID == "")
    {
        alert("�����Ϊ�գ�");
        return;
    }
    if(SubMissionID == null || SubMissionID == "")
    {
        alert("�������Ϊ�գ�");
        return;
    }
   
    //Ϊ�˱���ҳ��ˢ�±�������ǰ״ֱ̬�Ӵӿ���� --ǿ���˹��˱�ԭ��
/*    strSQL =  " select MissionProp10,MissionProp11,MissionProp12,MissionProp19 from lwmission where missionid = '" + MissionID +
              "' and submissionid = '" + SubMissionID + "' and activityid = '0000000005' " +
              " and processid = '0000000000' ";
*/    
	var sqlid1="EdorAppManuUWInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(MissionID);//ָ������Ĳ���
	mySql1.addSubPara(SubMissionID);
	strSQL=mySql1.getString();

    var vrr = easyExecSql(strSQL);
    
    if ( vrr )
    {
        vrr[0][0]==null||vrr[0][0]=='null'?'0':fm.ActivityStatus.value  = vrr[0][0];
        vrr[0][1]==null||vrr[0][1]=='null'?'0':fm.AppntNamew.value  = vrr[0][1];
        vrr[0][2]==null||vrr[0][2]=='null'?'0':fm.PaytoDate.value  = vrr[0][2];
        ActivityStatus  = document.all('ActivityStatus').value;
        vrr[0][3]==null||vrr[0][3]=='null'?'':fm.InFo1.value  = vrr[0][3];
    }
/************************************************************************
    if(ActivityStatus == null || ActivityStatus == "")
    {
        fm.AppntName.value = "";
        fm.PaytoDate.value = "";
        alert("����ǰ״̬Ϊ�գ�");
        return;
    }
*************************************************************************/
	//alert("ContNo=="+fm.ContNo.value);
	//var EdorNoSql="select edorno from lpedormain where edoracceptno='"+EdorAcceptNo+"' and contno='"++"'";
	
    //��ѯ��ȫ������Ϣ
/*    strSQL =  " select OtherNo, (select trim(code)||'-'||trim(codename) from ldcode where codetype = 'edornotype' and code = OtherNoType), "
            + " GetMoney,EdorAppName, "
            + " (select trim(code)||'-'||trim(codename) from ldcode where codetype = 'edorapptype' and trim(code) = trim(Apptype)), "
            + " (select trim(code)||'-'||trim(codename) from ldcode where codetype = 'station' and trim(code) = trim(ManageCom)),othernotype, "
            + " GetInterest,AppType,ManageCom "
            + " from LPEdorApp "
            + " where EdorAcceptNo = '" + EdorAcceptNo + "' ";
*/    
    var sqlid2="EdorAppManuUWInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(EdorAcceptNo);//ָ������Ĳ���
	strSQL=mySql2.getString();
    
    var brr = easyExecSql(strSQL);

    if ( brr )
    {
        brr[0][0]==null||brr[0][0]=='null'?'0':fm.OtherNo.value     = brr[0][0];
        brr[0][1]==null||brr[0][1]=='null'?'0':fm.OtherNoTypeName.value = brr[0][1];
        brr[0][2]==null||brr[0][2]=='null'?'0':fm.GetMoney.value    = brr[0][2];
        brr[0][3]==null||brr[0][3]=='null'?'0':fm.EdorAppName.value = brr[0][3];
        brr[0][4]==null||brr[0][4]=='null'?'0':fm.ApptypeName.value     = brr[0][4];
        brr[0][5]==null||brr[0][5]=='null'?'0':fm.ManageComName.value   = brr[0][5];
        brr[0][6]==null||brr[0][6]=='null'?'0':fm.OtherNoType.value = brr[0][6];
        brr[0][7]==null||brr[0][7]=='null'?'0':fm.GetInterest.value = brr[0][7];
        brr[0][8]==null||brr[0][8]=='null'?'0':fm.Apptype.value     = brr[0][8];
        brr[0][9]==null||brr[0][9]=='null'?'0':fm.ManageCom.value   = brr[0][9];
    }
    else
    {
        alert("��ȫ�����ѯʧ�ܣ�");
        return;
    }
    
    showEdorMainList();
    if (ActivityStatus == null || ActivityStatus.trim() == "")  //������Ļ���Ϊ1
    {
        //var showStr="���ڴ����˹��˱����룬�����Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
        //var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
        //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
        document.all('ActionFlag').value = "UWMANUAPPLY";
        
        var sqlid2="EdorAppManuUWInputSql60";
				var mySql2=new SqlClass();
				mySql2.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
				mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
				mySql2.addSubPara(document.all('MissionID').value);//ָ������Ĳ���
				mySql2.addSubPara(document.all('SubMissionID').value);//ָ������Ĳ���
				mySql2.addSubPara(EdorAcceptNo);//ָ������Ĳ���
				strSQL=mySql2.getString();
    
    		var brr = easyExecSql(strSQL);
    		if ( brr<1 )
    		{
        	//fm.action = "./PEdorAppManuUWApply.jsp";
        	//fm.submit();
        }
    }
    else
    {
        //��ѯ��ȫ�˹��˱�ȷ�Ͻڵ�
/*        strSQL =  " select submissionid "
                + " from lwmission "
                + " where activityid = '0000000006' "
                + " and missionid = '" + fm.MissionID.value + "'";
*/        
    var sqlid3="EdorAppManuUWInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
	mySql3.addSubPara(fm.MissionID.value);//ָ������Ĳ���
	strSQL=mySql3.getString();
        
        var brr = easyExecSql(strSQL, 1, 0,"","",1);

        if ( brr )
        {
            //brr[0][0]==null||brr[0][0]=='null'?'0':fm.SubMissionID.value = brr[0][0];
        }

    }
    
	//alert("document.all('SubMissionID').value=="+document.all('SubMissionID').value);
}


function showEdorMainList()
{
    document.all('UWType').value = "EdorApp";
    var EdorAcceptNo    = document.all('EdorAcceptNo').value;

    //��ѯ��ȫ������Ϣ
/*    strSQL =  " select (select edortype from lpedoritem where edorno = a.EdorNo)||(select '-'||EdorName from LMEdorItem where EdorCode=((select edortype from lpedoritem where edorno = a.EdorNo)) and AppObj='I'), a.ContNo,a.EdorNo "
                + " ,(select insuredno from lpcont where edorno=a.EdorNo and contno = a.contno )"
                + " ,nvl((select distinct riskcode from lppol where edorno=a.EdorNo and contno=a.contno and polno=mainpolno ),(select distinct riskcode from lcpol where contno=a.contno and polno=mainpolno)) "
                + " ,nvl((select distinct (select riskname from lmriskapp where riskcode=l.riskcode) from lppol l where edorno=a.EdorNo and contno=a.contno and polno=mainpolno ),(select distinct (select riskname from lmriskapp where riskcode=l.riskcode) from lcpol l where contno=a.contno and polno=mainpolno)) "
                + " , (select appntno from lpcont where edorno=a.EdorNo and contno = a.contno )"
                + " ,(select appntname from lpcont where edorno=a.EdorNo and contno = a.contno ), (select insuredno from lpinsured where edorno=a.EdorNo and contno = a.contno)"
                + " ,(select name from lpinsured where edorno=a.EdorNo and contno = a.contno ), (select cvalidate from lpcont where edorno=a.EdorNo and contno = a.contno)"
                + " ,(select max(startdate) from lccontstate where statetype='Available' and state='1' and contno=a.contno and startdate <=a.edorappdate and (enddate is null or enddate >= a.edorappdate) and polno in ('000000',(select polno from lppol where edorno=a.EdorNo and contno=a.contno and polno=mainpolno )))"
                + " ,(select decode(trim(appflag),'1','��Ч','4','��ֹ') from lpcont where edorno=a.EdorNo and contno = a.contno) "
                + " from LPEdorMain a "
                + " where 1=1 "
                + " and a.EdorAcceptNo = '" + EdorAcceptNo + "' ";
	//prompt("",strSQL);
*/    
    var sqlid4="EdorAppManuUWInputSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
	mySql4.addSubPara(EdorAcceptNo);//ָ������Ĳ���
	strSQL=mySql4.getString();
    
    var crr = easyExecSql(strSQL, 1, 0,"","",1);
    if ( !crr )
    {
        alert("��ȫ������û������������")
        return;
    }
    else
    {
        //document.all('UWType').value = "EdorMain";
        turnPageEdorMainGrid.pageDivName = "divTurnPageEdorMainGrid";
        turnPageEdorMainGrid.queryModal(strSQL, EdorMainGrid);
        //divEdorMainInfo.style.display="";
    } 
}

/*********************************************************************
 *  ��ȫ�˹��˱�
 *  ����: ��ʾ�˹��˱�����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showDetailInfo1()
{
  var tSelNo = EdorMainGrid.getSelNo()-1;
  showDetailInfo(tSelNo);
}

/*********************************************************************
 *  ��ȫ�˹��˱�
 *  ����: ��ʾ�˹��˱�����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showDetailInfo(index)
{//alert(168);
	document.all('UWType').value = "EdorMain";
	//DivDetailInfo.style.display='';
	//divContUWLable.style.display = '';
	//divAppUWLable.style.display = 'none';
	initDivDetailInfo(index);
	//UWcancel();//����º˱����������
  //���ɹ������������
//  var tSelNo = EdorMainGrid.getSelNo()-1;
//  var tContNo = EdorMainGrid.getRowColData(tSelNo,2);
 //alert(tContNo);
  
  //�������������Ҫ����0000000006�ڵ㣬�˹��˱�ֻ����һ���ڵ� modifyby huxj 20130828
  //makeWorkFlow(fm.ContNo.value);
}

/*********************************************************************
 *  ��ʼ����ȫ��������ϸ��Ϣ
 *  ����: ��ʼ����ȫ��������ϸ��Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
  */
 function initDivDetailInfo(index)
 {//alert(190);
     var tSelNo = index;
     if(tSelNo==0&&EdorMainGrid.mulLineCount>1)
 	{
 			document.all('EdorMainGridSel')[0].checked=true; 			
 	}  
 	else if(tSelNo==0&&EdorMainGrid.mulLineCount==1)
 	{	
 			document.all('EdorMainGridSel').checked=true; 			
 	} 
 	else if(EdorMainGrid.mulLineCount<1)
 		return false;   
     var tEdorNo = EdorMainGrid.getRowColData(tSelNo,3);//alert("tEdorNo=="+tEdorNo);
    // alert("tEdorNo=="+tEdorNo);
     fm.EdorNo.value = tEdorNo;
     tContNo = EdorMainGrid.getRowColData(tSelNo,2);//alert("tContNo:"+tContNo);
     var tMissionID = fm.MissionID.value;
      //-------------------------------Beg
    //������±��鿴������ʾ
    //EditBy:Chenrong; Data:2006-4-27
    checkNotePad(tMissionID);
    //-------------------------------End
    
    //-------------------------------Beg
    //����˱���������鿴������ʾ
    //EditBy:ln; Data:2008-12-3
    checkReport(tContNo);
    //-------------------------------End
     initLpcont(tEdorNo,tContNo);
     initLpAppt(tEdorNo,tContNo);
     initLppol(tEdorNo,tContNo);
     initFormerUW(tContNo,tEdorNo);//��ѯԭ�˱�����
     initRecentUW(tContNo,tEdorNo);//��ѯ�����˱�����
//     var str6 = "select edortype from lpedoritem where edorno = '"+ tEdorNo +"'";
     
    var str6 = "";
    var sqlid5="EdorAppManuUWInputSql5";
	var mySql5=new SqlClass();
	mySql5.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
	mySql5.addSubPara(tEdorNo);//ָ������Ĳ���
	str6=mySql5.getString();
     
     var tempEdorType = easyExecSql(str6, 1, 0,"","",1);
     //prompt("",str6);
     fm.EdorType.value = tempEdorType;    
     document.all('UWType').value  = "EdorMain";
     //alert(document.all('EdorAcceptNo').value);
/*     var str7 = " select nvl(approvecontent,'') from LPApproveTrack where edoracceptno='"
    	 +document.all('EdorAcceptNo').value+"'  order by approvetimes desc ";
*/     
    var str7 = "";
    var sqlid6="EdorAppManuUWInputSql6";
	var mySql6=new SqlClass();
	mySql6.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
	mySql6.addSubPara(document.all('EdorAcceptNo').value);//ָ������Ĳ���
	str7=mySql6.getString();
     
     tempEdorType=easyExecSql(str7);
     //add by jiaqiangli ���ж�tempEdorType
     if (tempEdorType != null) {
     if(tempEdorType[0][0]!=null&&tempEdorType[0][0]!="")
     {
    	 fm.InFo2.value = tempEdorType[0][0];
     }
	}
 }
 //��ʼ����ͬ��Ϣ
 function initLpcont(tEdorNo,tContNo)
 {
/*    var strSql = " select ContNo,(select trim(code)||'-'||trim(codename) from ldcode where codetype = 'station' and trim(code) = trim(ManageCom)),"
                   +"(select codename from ldcode where codetype = 'salechnl' and code = SaleChnl),"
                   +"AgentCode,Remark,PrtNo,ProposalContNo,appntno"
                   +" from lpcont where 1=1"
                   +" and ContNo='"+tContNo+"'"
                   +" and EdorNo='"+tEdorNo+"'";
*/    
    var strSql = "";
    var sqlid7="EdorAppManuUWInputSql7";
	var mySql7=new SqlClass();
	mySql7.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql7.setSqlId(sqlid7);//ָ��ʹ�õ�Sql��id
	mySql7.addSubPara(tContNo);//ָ������Ĳ���
	mySql7.addSubPara(tEdorNo);
	strSql=mySql7.getString();
    
    var brr = easyExecSql(strSql, 1, 0,"","",1);
    if(brr)
    {

         brr[0][0]==null||brr[0][0]=='null'?'0':fm.ContNo.value  = brr[0][0];
         brr[0][1]==null||brr[0][1]=='null'?'0':fm.lpManageCom.value  = brr[0][1];
         brr[0][2]==null||brr[0][2]=='null'?'0':fm.SaleChnl.value  = brr[0][2];
         brr[0][3]==null||brr[0][3]=='null'?'0':fm.AgentCode.value  = brr[0][3];
         brr[0][4]==null||brr[0][4]=='null'?'0':fm.Remark.value  = brr[0][4];
         brr[0][5]==null||brr[0][5]=='null'?'0':fm.PrtNo.value  = brr[0][5];
         brr[0][6]==null||brr[0][6]=='null'?'0':fm.ProposalContNo.value  = brr[0][6];
         brr[0][7]==null||brr[0][7]=='null'?'0':fm.AppntNo.value  = brr[0][7];
    }
    else
    {
        //���p��鲻�������c��
/*        strSql = " select ContNo,(select trim(code)||'-'||trim(codename) from ldcode where codetype = 'station' and trim(code) = trim(ManageCom)),(select codename from ldcode where codetype = 'salechnl' and trim(code) = trim(SaleChnl)),"
                   +"AgentCode,Remark,PrtNo,proposalContNo,appntno"
                   +" from lccont where 1=1"
                   +" and ContNo='"+tContNo+"'";
*/        
    var sqlid8="EdorAppManuUWInputSql8";
	var mySql8=new SqlClass();
	mySql8.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql8.setSqlId(sqlid8);//ָ��ʹ�õ�Sql��id
	mySql8.addSubPara(tContNo);//ָ������Ĳ���
	strSql=mySql8.getString();
        
        brr = easyExecSql(strSql, 1, 0,"","",1);
        if(brr)
        {
             brr[0][0]==null||brr[0][0]=='null'?'0':fm.ContNo.value  = brr[0][0];
             brr[0][1]==null||brr[0][1]=='null'?'0':fm.lpManageCom.value  = brr[0][1];
             brr[0][2]==null||brr[0][2]=='null'?'0':fm.SaleChnl.value  = brr[0][2];
             brr[0][3]==null||brr[0][3]=='null'?'0':fm.AgentCode.value  = brr[0][3];
             brr[0][4]==null||brr[0][4]=='null'?'0':fm.Remark.value  = brr[0][4];
             brr[0][5]==null||brr[0][5]=='null'?'0':fm.PrtNo.value  = brr[0][5];
             brr[0][6]==null||brr[0][6]=='null'?'0':fm.ProposalContNo.value  = brr[0][6];
             brr[0][7]==null||brr[0][7]=='null'?'0':fm.AppntNo.value  = brr[0][7];
        }
        else
        {
            alert("��ͬ��Ϣ��ѯʧ�ܣ�");
            return "";
        }
     }

 }
  //��ʼ��Ͷ������Ϣ
  function initLpAppt(tEdorNo,tContNo)
 {

/*    var strSql = " select a.AppntName,"
                     +"(select codename from ldcode where codetype = 'sex' and trim(code) = trim(a.AppntSex)),"
                     +"a.AppntBirthday,(select occupationname from ldoccupation where trim(occupationcode) = trim(a.OccupationCode)),"
                     +"(select codename from ldcode where codetype = 'occupationtype' and trim(code) = trim(a.OccupationType)),"
                     +"(select codename from ldcode where codetype = 'nativeplace' and trim(code) = trim(a.NativePlace)),"
                     +"(select trim(code)||'-'||trim(codename) from ldcode where codetype = 'vipvalue' and trim(code) = trim(b.VIPValue)),"
                     +"(select trim(code)||'-'||trim(codename) from ldcode where codetype = 'blacklistflag' and trim(code) = trim(b.blacklistFlag)),"
                     +"a.appntNo"
                     +" from lpappnt a, lpperson b where 1=1"
                     +" and a.ContNo='"+tContNo+"'"
                     +" and a.EdorNo='"+tEdorNo+"'"
                     +" and b.EdorNo='"+tEdorNo+"'"
                     +" and b.customerNo=a.AppntNo";
*/    
    var strSql = "";
    var sqlid9="EdorAppManuUWInputSql9";
	var mySql9=new SqlClass();
	mySql9.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql9.setSqlId(sqlid9);//ָ��ʹ�õ�Sql��id
	mySql9.addSubPara(tContNo);//ָ������Ĳ���
	mySql9.addSubPara(tEdorNo);
	mySql9.addSubPara(tEdorNo);
	strSql=mySql9.getString();
    
    var brr = easyExecSql(strSql, 1, 0,"","",1);

    if(brr)
    {
         brr[0][0]==null||brr[0][0]=='null'?'0':fm.AppntName.value  = brr[0][0];
         brr[0][1]==null||brr[0][1]=='null'?'0':fm.AppntSexName.value  = brr[0][1];
         brr[0][2]==null||brr[0][2]=='null'?'0':fm.AppntBirthday.value  = brr[0][2];
         fm.AppntAge.value=calAge(fm.AppntBirthday.value);
         brr[0][3]==null||brr[0][3]=='null'?'0':fm.OccupationCodeName.value  = brr[0][3];
         brr[0][4]==null||brr[0][4]=='null'?'0':fm.OccupationTypeName.value  = brr[0][4];
         brr[0][5]==null||brr[0][5]=='null'?'0':fm.NativePlace.value  = brr[0][5];
         //brr[0][6]==null||brr[0][6]=='null'?'0':fm.VIPValue.value  = brr[0][6];
         //brr[0][7]==null||brr[0][7]=='null'?'0':fm.BlacklistFlag.value  = brr[0][7];
         brr[0][8]==null||brr[0][8]=='null'?'0':fm.AppntNo.value  = brr[0][8];
    }
    else
    {
/*        strSql = " select p.AppntName,(select codename from ldcode where codetype = 'sex' and trim(code) = trim(p.AppntSex)),"
                     +"p.AppntBirthday,(select occupationname from ldoccupation where trim(occupationcode) = trim(p.OccupationCode)),"
                     +"(select codename from ldcode where codetype = 'occupationtype' and trim(code) = trim(p.OccupationType)),"
                     +"(select codename from ldcode where codetype = 'nativeplace' and trim(code) = trim(p.NativePlace)),"
                     +"(select trim(code)||'-'||trim(codename) from ldcode where codetype = 'vipvalue' and trim(code) = trim(h.VIPValue)),"
                     +"(select trim(code)||'-'||trim(codename) from ldcode where codetype = 'blacklistflag' and trim(code) = trim(h.blacklistFlag)),"
                     +"p.AppntNo"
                     +" from lcappnt p,ldperson h where 1=1"
                     +" and p.ContNo='"+tContNo+"'"
                     +" and h.CustomerNo=p.AppntNo";
*/        
    var sqlid10="EdorAppManuUWInputSql10";
	var mySql10=new SqlClass();
	mySql10.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql10.setSqlId(sqlid10);//ָ��ʹ�õ�Sql��id
	mySql10.addSubPara(tContNo);//ָ������Ĳ���
	strSql=mySql10.getString();
        
        brr = easyExecSql(strSql, 1, 0,"","",1);
        if(brr)
        {
         brr[0][0]==null||brr[0][0]=='null'?'0':fm.AppntName.value  = brr[0][0];
         brr[0][1]==null||brr[0][1]=='null'?'0':fm.AppntSexName.value  = brr[0][1];
         brr[0][2]==null||brr[0][2]=='null'?'0':fm.AppntBirthday.value  = brr[0][2];
         fm.AppntAge.value=calAge(fm.AppntBirthday.value);
         brr[0][3]==null||brr[0][3]=='null'?'0':fm.OccupationCodeName.value  = brr[0][3];
         brr[0][4]==null||brr[0][4]=='null'?'0':fm.OccupationTypeName.value  = brr[0][4];
         brr[0][5]==null||brr[0][5]=='null'?'0':fm.NativePlaceName.value  = brr[0][5];
         //brr[0][6]==null||brr[0][6]=='null'?'0':fm.VIPValue.value  = brr[0][6];
         //brr[0][7]==null||brr[0][7]=='null'?'0':fm.BlacklistFlag.value  = brr[0][7];
         brr[0][8]==null||brr[0][8]=='null'?'0':fm.AppntNo.value  = brr[0][8];
        }
        else
        {
            //���p��c���鲻������ֱ�Ӳ�ѯ�ͻ����ϱ�
/*            strSql = "select Name,(select codename from ldcode where codetype = 'sex' and trim(code) = trim(Sex)),"
                     +"Birthday,(select occupationname from ldoccupation where trim(ldoccupation.occupationcode) = trim(ldperson.OccupationCode)),"
                     +"(select codename from ldcode where codetype = 'occupationtype' and trim(code) = trim(OccupationType)),"
                     +"(select codename from ldcode where codetype = 'nativeplace' and trim(code) = trim(NativePlace)),"
                     +"(select trim(code)||'-'||trim(codename) from ldcode where codetype = 'vipvalue' and trim(code) = trim(VIPValue)),"
                     +"(select trim(code)||'-'||trim(codename) from ldcode where codetype = 'blacklistflag' and trim(code) = trim(blacklistFlag)), "
                     +" customerno "
                     +" from ldperson where customerno = '"+fm.AppntNo.value+"'";
*/
	var sqlid11="EdorAppManuUWInputSql11";
	var mySql11=new SqlClass();
	mySql11.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql11.setSqlId(sqlid11);//ָ��ʹ�õ�Sql��id
	mySql11.addSubPara(tContNo);//ָ������Ĳ���
	strSql=mySql11.getString();

            brr = easyExecSql(strSql, 1, 0,"","",1);
            if(brr)
            {
                  brr[0][0]==null||brr[0][0]=='null'?'0':fm.AppntName.value  = brr[0][0];
                  brr[0][1]==null||brr[0][1]=='null'?'0':fm.AppntSexName.value  = brr[0][1];
                  brr[0][2]==null||brr[0][2]=='null'?'0':fm.AppntBirthday.value  = brr[0][2];
                  fm.AppntAge.value=calAge(fm.AppntBirthday.value);
                  brr[0][3]==null||brr[0][3]=='null'?'0':fm.OccupationCodeName.value  = brr[0][3];
                  brr[0][4]==null||brr[0][4]=='null'?'0':fm.OccupationTypeName.value  = brr[0][4];
                  brr[0][5]==null||brr[0][5]=='null'?'0':fm.NativePlaceName.value  = brr[0][5];
                  //brr[0][6]==null||brr[0][6]=='null'?'0':fm.VIPValue.value  = brr[0][6];
                  //brr[0][7]==null||brr[0][7]=='null'?'0':fm.BlacklistFlag.value  = brr[0][7];
                  brr[0][8]==null||brr[0][8]=='null'?'0':fm.AppntNo.value  = brr[0][8];
             }
             else
             {
                  alert("Ͷ������Ϣ��ѯʧ�ܣ�");
                  return "";

             }


        }
     }
     
     if(tContNo!=null && tContNo!='')
     {
     	 //��ѯͶ���˵���ߡ����أ�������ʾ  
			 var nnPrtNo = document.all('PrtNo').value;
			if(nnPrtNo!=null && nnPrtNo!="" && nnPrtNo.length==14 && nnPrtNo.substring(2,4)=="51")//��ͥ��
			{
/*			  var sql = "select impartparamname, impartparam"
		         + " from lccustomerimpartparams"
		         + " where 1 = 1"
		         //+ " and customernotype = '1'"             
		         + " and impartcode = 'D0101'"
		         + " and impartver = 'D01'"
		         + " and impartparamno in ('1', '2')"
		         + " and contno = '"+tContNo+"'"
		         + " order by customernotype desc,customerno,impartparamno ";
*/			   
	var sql = "";
	var sqlid12="EdorAppManuUWInputSql12";
	var mySql12=new SqlClass();
	mySql12.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql12.setSqlId(sqlid12);//ָ��ʹ�õ�Sql��id
	mySql12.addSubPara(tContNo);//ָ������Ĳ���
	sql=mySql12.getString();
			   
			   var arr1 = easyExecSql(sql);
			   if(arr1!=null)
			   {
					document.all('Stature').value=arr1[0][1];
					document.all('Weight').value=arr1[1][1];
					document.all('BMI').value=Math.round(parseFloat(arr1[1][1])/parseFloat(arr1[0][1])/parseFloat(arr1[0][1])*10000);
				}
				
/*				var insql = "select impartparam"
		         + " from lccustomerimpartparams"
		         + " where 1 = 1"
		         //+ " and customernotype = '1'"             
		         + " and impartcode = 'D0119'"
		         + " and impartver = 'D02'"
		         + " and impartparamno ='1'"
		         + " and contno = '"+tContNo+"'"
		         + " order by customernotype ,customerno,impartparamno ";		
*/			    
	var insql = "";
	var sqlid13="EdorAppManuUWInputSql13";
	var mySql13=new SqlClass();
	mySql13.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql13.setSqlId(sqlid13);//ָ��ʹ�õ�Sql��id
	mySql13.addSubPara(tContNo);//ָ������Ĳ���
	insql=mySql13.getString();
			    
			    var incomeway = easyExecSql(insql);
			    if(incomeway!=null && incomeway !="")
			    {
			       document.all('income').value = incomeway;
			    }
			}
			else
			{
/*				 var sql = "select impartparamname, impartparam"
		         + " from lccustomerimpartparams"
		         + " where 1 = 1"
		         //+ " and customernotype = '0'"         
		         + " and impartcode = 'A0101'"
		         + " and impartver = 'A01'"
		         + " and impartparamno in ('3', '4')"
		         + " and contno = '"+tContNo+"'"
		         + " order by customernotype ,customerno,impartparamno ";
*/			   
	var sql = "";
	var sqlid14="EdorAppManuUWInputSql14";
	var mySql14=new SqlClass();
	mySql14.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql14.setSqlId(sqlid14);//ָ��ʹ�õ�Sql��id
	mySql14.addSubPara(tContNo);//ָ������Ĳ���
	sql=mySql14.getString();
			   
			   var arr1 = easyExecSql(sql);
			   if(arr1!=null)
			   {
					document.all('Stature').value=arr1[0][1];
					document.all('Weight').value=arr1[1][1];
					document.all('BMI').value=Math.round(parseFloat(arr1[1][1])/parseFloat(arr1[0][1])/parseFloat(arr1[0][1])*10000);
				}
				
/*				var insql = "select impartparam"
		         + " from lccustomerimpartparams"
		         + " where 1 = 1"
		         //+ " and customernotype = '0'"             
		         + " and impartcode = 'A0120'"
		         + " and impartver = 'A02'"
		         + " and impartparamno ='3'"
		         + " and contno = '"+tContNo+"'"
		         + " order by customernotype ,customerno,impartparamno ";		
*/			    
	var insql = "";
	var sqlid15="EdorAppManuUWInputSql15";
	var mySql15=new SqlClass();
	mySql15.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql15.setSqlId(sqlid15);//ָ��ʹ�õ�Sql��id
	mySql15.addSubPara(tContNo);//ָ������Ĳ���
	insql=mySql15.getString();
			    
			    var incomeway = easyExecSql(insql);
			    if(incomeway!=null && incomeway !="")
			    {
			       document.all('income').value = incomeway;//��ѯ������
			    }
			}
	 }
     
     if(document.all('AppntNo').value!=null && document.all('AppntNo').value!='')
     {
	     var tSumAmnt =0;
//	    strSQL =  "SELECT healthyamnt2('" + document.all('AppntNo').value +"','1','1') from dual ";
	    
	var sqlid16="EdorAppManuUWInputSql16";
	var mySql16=new SqlClass();
	mySql16.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql16.setSqlId(sqlid16);//ָ��ʹ�õ�Sql��id
	mySql16.addSubPara(document.all('AppntNo').value);//ָ������Ĳ���
	strSQL=mySql16.getString();
	    
	    var arr1=easyExecSql(strSQL);
	   // prompt("",strSQL);
		if(arr1!=null){
			document.all('AppntSumLifeAmnt').value=arr1[0][0];
			tSumAmnt = tSumAmnt + parseFloat(arr1[0][0]);
		}
		 
//		strSQL =  "SELECT healthyamnt2('" + document.all('AppntNo').value +"','2','1') from dual ";
	    
	var sqlid17="EdorAppManuUWInputSql17";
	var mySql17=new SqlClass();
	mySql17.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql17.setSqlId(sqlid17);//ָ��ʹ�õ�Sql��id
	mySql17.addSubPara(document.all('AppntNo').value);//ָ������Ĳ���
	strSQL=mySql17.getString();
	    
	    var arr2=easyExecSql(strSQL);
		if(arr2!=null){
			document.all('AppntSumHealthAmnt').value=arr2[0][0];
			tSumAmnt = tSumAmnt + parseFloat(arr2[0][0]);
		}
		
//		 strSQL =  "SELECT healthyamnt2('" + document.all('AppntNo').value +"','3','1') from dual ";
	    
	var sqlid18="EdorAppManuUWInputSql18";
	var mySql18=new SqlClass();
	mySql18.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql18.setSqlId(sqlid18);//ָ��ʹ�õ�Sql��id
	mySql18.addSubPara(document.all('AppntNo').value);//ָ������Ĳ���
	strSQL=mySql18.getString();
	    
	    var arr3=easyExecSql(strSQL);
		if(arr3!=null){
			document.all('AppntMedicalAmnt').value=arr3[0][0];
			//tSumAmnt = tSumAmnt + parseFloat(arr3[0][0]);
		}
		
//		strSQL =  "SELECT healthyamnt2('" + document.all('AppntNo').value +"','4','1') from dual ";
	    
	var sqlid19="EdorAppManuUWInputSql19";
	var mySql19=new SqlClass();
	mySql19.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql19.setSqlId(sqlid19);//ָ��ʹ�õ�Sql��id
	mySql19.addSubPara(document.all('AppntNo').value);//ָ������Ĳ���
	strSQL=mySql19.getString();
	    
	    var arr4=easyExecSql(strSQL);
		if(arr4!=null){
			document.all('AppntAccidentAmnt').value=arr4[0][0];
			tSumAmnt = tSumAmnt + parseFloat(arr4[0][0]);
		}
		
	    document.all('AppntSumAmnt').value=tSumAmnt;
	    
	    //�ۼƱ��� �����������Ͳ����ڽ�����   
/*	    strSQL =  "SELECT decode(round(sum(a_Prem),2),'','x',round(sum(a_Prem),2)) FROM"
	          + "(select (case"
	          + " when s_PayIntv = '1' then"
	          + " s_Prem/0.09"
	          + " when s_PayIntv = '3' then"
	          + " s_Prem/0.27"
	          + " when s_PayIntv = '6' then"
	          + " s_Prem/0.52"
	          + " when s_PayIntv = '12' then"
	          + " s_Prem"
	          + " end) a_Prem"          
	          + " FROM (select distinct payintv as s_PayIntv,"
	          + " sum(prem) as s_Prem"
	          + " from lcpol c where polno in(select polno "
		          + " from lcpol a"
		          + " where a.insuredno = '"+document.all('AppntNo').value+"'"
		          + " or (a.appntno = '"+document.all('AppntNo').value+"' and a.riskcode in ('00115000','00115001'))"
		          + " union"
		          + " select b.polno"
		          + " from lcinsuredrelated b"
		          + " where b.customerno = '"+document.all('AppntNo').value+"')"
	          + " and c.payintv in ('1', '3','6','12')"
	          + " and c.uwflag not in ('1', '2', 'a')"
	          + " and c.appflag <> '4'" 
	          + " and not exists (select 'X'"
		          + " from lccont"
		          + " where ContNo = c.contno"
		          + " and (uwflag in ('1', '2', 'a') or appflag='4' or (state is not null and substr(state,0,4) in ('1002', '1003'))  ))"
	          + " group by payintv))";
*/	    
	var sqlid20="EdorAppManuUWInputSql20";
	var mySql20=new SqlClass();
	mySql20.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql20.setSqlId(sqlid20);//ָ��ʹ�õ�Sql��id
	mySql20.addSubPara(document.all('AppntNo').value);//ָ������Ĳ���
	mySql20.addSubPara(document.all('AppntNo').value);
	mySql20.addSubPara(document.all('AppntNo').value);
	strSQL=mySql20.getString();
	    
	    var arr5=easyExecSql(strSQL);
		if(arr5!=null){	
		      // alert(arr5[0][0]);  
		      if(arr5[0][0]!='x')  
		          document.all('SumPrem').value=arr5[0][0];
		      else
		    {
		    	document.all('SumPrem').value ='0';
		    }
		}
		    
     }    

 } 

  //��ʼ����������Ϣ
  function initLppol(tEdorNo,tContNo)
 {
 	//alert(tContNo);
/*     var strSql = "select a.InsuredNo, a.Name, (select CodeName from LDCode where 1 = 1 and CodeType = 'sex' and Code = a.Sex) "
                 +" ,(select max(insuredappage) from lppol where EdorNo=a.EdorNo and contno=a.contno and insuredno=a.insuredno)"
                 +" ,(select codename from ldcode where trim(code) = trim(a.RelationToAppnt) and codetype = 'relation'),(select codename from ldcode where trim(code) = trim(RelationToMainInsured) and codetype = 'relation') "
                 +" ,(select codename from ldcode where trim(code) = trim(a.NativePlace) and codetype='nativeplace')"
                      +" from lpinsured a where 1=1"
                      +" and a.ContNo='"+tContNo+"'"
                      +" and a.EdorNo='"+tEdorNo+"'"
                      +" order by a.SequenceNo asc";
*/
	var strSql = "";
	var sqlid21="EdorAppManuUWInputSql21";
	var mySql21=new SqlClass();
	mySql21.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql21.setSqlId(sqlid21);//ָ��ʹ�õ�Sql��id
	mySql21.addSubPara(tContNo);//ָ������Ĳ���
	mySql21.addSubPara(tEdorNo);
	strSql=mySql21.getString();

    var brr = easyExecSql(strSql, 1, 0,"","",1);
    if (brr)
    {
        turnPageInsured.queryModal(strSql, PolAddGrid);
    }
    else
    {
/*        strSql = "select a.InsuredNo, a.Name, (select CodeName from LDCode where 1 = 1 and CodeType = 'sex' and Code = a.Sex) "
                 +" ,(select max(insuredappage) from lcpol where contno=a.contno and insuredno=a.insuredno)"
                 +" ,(select codename from ldcode where trim(code) = trim(a.RelationToAppnt) and codetype = 'relation'),(select codename from ldcode where trim(code) = trim(RelationToMainInsured) and codetype = 'relation') "
                 +" ,(select codename from ldcode where trim(code) = trim(a.NativePlace) and codetype='nativeplace')"
                      +" from lcinsured a where 1=1"
                      +" and a.ContNo='"+tContNo+"'"
                      +" order by a.SequenceNo asc";
*/
	var sqlid22="EdorAppManuUWInputSql22";
	var mySql22=new SqlClass();
	mySql22.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql22.setSqlId(sqlid22);//ָ��ʹ�õ�Sql��id
	mySql22.addSubPara(tContNo);//ָ������Ĳ���
	strSql=mySql22.getString();

        brr = easyExecSql(strSql, 1, 0,"","",1);
        if (brr)
        {
            turnPageInsured.queryModal(strSql, PolAddGrid);
        }
        else
        {
           alert("��������Ϣ��ѯʧ�ܣ�");
           return "";
        }
    }
    var tSelNo = 0;
    initLppolDetail(tEdorNo,tContNo,tSelNo);    

 }
 
  /*********************************************************************
 *  ��ȫ�˹��˱�
 *  ����: ��ʾ��������Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function initLppolDetailf()
{
  var tSelNo = PolAddGrid.getSelNo()-1;
  initLppolDetail(fm.EdorNo.value,fm.ContNo.value,tSelNo);
}
 
  //��ʼ����������Ϣ
  function initLppolDetail(tEdorNo,tContNo,index)
 {
 	 var tSelNo = index;
 	 if(tSelNo==0&&PolAddGrid.mulLineCount>1)
 	{
 			document.all('PolAddGridSel')[0].checked=true; 			
 	}  
 	else if(tSelNo==0&&PolAddGrid.mulLineCount==1)
 	{	
 			document.all('PolAddGridSel').checked=true; 			
 	} 
 	else if(PolAddGrid.mulLineCount<1)
 		return false;  
     var tInsuredNo = PolAddGrid.getRowColData(tSelNo,1);//alert("tInsuredNo=="+tInsuredNo);
     
     //�����˽�����֪��ѯ��ť����
/*    var sqlind = "select 1 from lccustomerimpart where customerno='"+tInsuredNo+"' and contno='"+tContNo+"' and impartver in ('101','A01','B01','C01','D01') "
        + "��union select 1 from lpcustomerimpart where customerno='"+tInsuredNo+"' and edorno='"+tEdorNo+"' and contno='"+tContNo+"' and impartver in ('101','A01','B01','C01','D01')";
       //prompt('',sqlind);
*/	   
	var sqlind = "";
	var sqlid23="EdorAppManuUWInputSql23";
	var mySql23=new SqlClass();
	mySql23.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql23.setSqlId(sqlid23);//ָ��ʹ�õ�Sql��id
	mySql23.addSubPara(tInsuredNo);//ָ������Ĳ���
	mySql23.addSubPara(tContNo);
	mySql23.addSubPara(tInsuredNo);
	mySql23.addSubPara(tEdorNo);
	mySql23.addSubPara(tContNo);
	sqlind=mySql23.getString();
	   
	   var arrind = easyExecSql(sqlind);
	   //�ж��Ƿ��ѯ�ɹ�
 	 if(arrind==null){
        document.all('indButton1').disabled='true';
      }
      else{
        document.all('indButton1').disabled='';
      }
      
      //��������������Ϣ��ѯ��ť����
//    var sqlind1 = "select 1 from llregister a, llcase b, LLClaimPolicy c where a.rgtno = b.caseno and c.clmno = a.rgtno and c.contno = '"+tContNo+"' and b.CustomerNo = '"+tInsuredNo+"'";
      
    var sqlind1 = "";
	var sqlid24="EdorAppManuUWInputSql24";
	var mySql24=new SqlClass();
	mySql24.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql24.setSqlId(sqlid24);//ָ��ʹ�õ�Sql��id
	mySql24.addSubPara(tContNo);//ָ������Ĳ���
	mySql24.addSubPara(tInsuredNo);
	sqlind1=mySql24.getString();
      
       //prompt('',sqlind1);
	   var arrind1 = easyExecSql(sqlind1);
	   //�ж��Ƿ��ѯ�ɹ�
 	 if(arrind1==null){
        document.all('indButton3').disabled='true';
      }
      else{
        document.all('indButton3').disabled='';
      }
      
      //
/*      sqlind = "select 1 from lcpol where contno<> '"+tContNo+"' " 
			  + " and insuredno = '"+tInsuredNo+"' "
			  + " and conttype='1' and appflag in ('1','4') "
			  + " union "
			  + " select 1 from lcpol where contno<> '"+tContNo+"' "
			  + " and insuredno = '"+tInsuredNo+"' "
			  + " and conttype='1' and appflag ='0' "
			  + " union "
			  + " select 1 from lcpol where contno<> '"+tContNo+"' "
			  + " and (appntno = '"+tInsuredNo+"'"
			  + " or insuredno = '"+tInsuredNo+"' ) "
			  + " and conttype='2' ";
       //prompt('',sqlind);
*/	   
	var sqlid25="EdorAppManuUWInputSql25";
	var mySql25=new SqlClass();
	mySql25.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql25.setSqlId(sqlid25);//ָ��ʹ�õ�Sql��id
	mySql25.addSubPara(tContNo);//ָ������Ĳ���
	mySql25.addSubPara(tInsuredNo);
	mySql25.addSubPara(tContNo);
	mySql25.addSubPara(tInsuredNo);
	mySql25.addSubPara(tContNo);
	mySql25.addSubPara(tInsuredNo);
	mySql25.addSubPara(tInsuredNo);
	sqlind=mySql25.getString();
	   
	   var arrind1 = easyExecSql(sqlind);
	   //�ж��Ƿ��ѯ�ɹ�
 	 if(arrind1==null){
        document.all('indButton2').disabled='true';
      }
      else{
        document.all('indButton2').disabled='';
      }
     
/*     var strSql = "select a.Name"
                  +" ,(select occupationcode||'-'||occupationname from ldoccupation where trim(occupationcode) = trim(a.OccupationCode))"
                  +" ,(select codename from ldcode where codetype = 'occupationtype' and trim(code) = trim(a.OccupationType))"
                  +" ,a.InsuredNo "
                      +" from lpinsured a where 1=1"
                      +" and a.ContNo='"+tContNo+"'"
                      +" and a.EdorNo='"+tEdorNo+"'"
                      +" and a.InsuredNo='"+tInsuredNo+"'";
*/
	var strSql = "";
	var sqlid26="EdorAppManuUWInputSql26";
	var mySql26=new SqlClass();
	mySql26.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql26.setSqlId(sqlid26);//ָ��ʹ�õ�Sql��id
	mySql26.addSubPara(tContNo);//ָ������Ĳ���
	mySql26.addSubPara(tEdorNo);
	mySql26.addSubPara(tInsuredNo);
	strSql=mySql26.getString();

	//prompt('',strSql);
    var brr = easyExecSql(strSql, 1, 0,"","",1);
    if (brr)
    {
        brr[0][0]==null||brr[0][0]=='null'?'0':fm.InsuredName.value  = brr[0][0];        
        brr[0][1]==null||brr[0][1]=='null'?'0':fm.InsuredOccupationCodeName.value  = brr[0][1];
	    brr[0][2]==null||brr[0][2]=='null'?'0':fm.InsuredOccupationTypeName.value  = brr[0][2];
        brr[0][3]==null||brr[0][3]=='null'?'0':fm.InsuredNo.value  = brr[0][3];
    }
    else
    {
/*        strSql = "select a.Name"
                  +" ,(select occupationcode||'-'||occupationname from ldoccupation where trim(occupationcode) = trim(a.OccupationCode))"
                  +" ,(select codename from ldcode where codetype = 'occupationtype' and trim(code) = trim(a.OccupationType))"
                  +" ,a.InsuredNo "
                      +" from lcinsured a where 1=1"
                      +" and a.ContNo='"+tContNo+"'"
                      +" and a.InsuredNo='"+tInsuredNo+"'";
		//prompt('',strSql);
*/        
	var sqlid27="EdorAppManuUWInputSql27";
	var mySql27=new SqlClass();
	mySql27.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql27.setSqlId(sqlid27);//ָ��ʹ�õ�Sql��id
	mySql27.addSubPara(tContNo);//ָ������Ĳ���
	mySql27.addSubPara(tInsuredNo);
	strSql=mySql27.getString();
        
        brr = easyExecSql(strSql, 1, 0,"","",1);
        if (brr)
        {
            brr[0][0]==null||brr[0][0]=='null'?'0':fm.InsuredName.value  = brr[0][0];        
	        brr[0][1]==null||brr[0][1]=='null'?'0':fm.InsuredOccupationCodeName.value  = brr[0][1];
		    brr[0][2]==null||brr[0][2]=='null'?'0':fm.InsuredOccupationTypeName.value  = brr[0][2];
	        brr[0][3]==null||brr[0][3]=='null'?'0':fm.InsuredNo.value  = brr[0][3];
        }
        else
        {
           alert("��������Ϣ��ѯʧ�ܣ�");
           return "";
        }
    }
    
    //�������������
	 var nnPrtNo = document.all('PrtNo').value;
	if(nnPrtNo!=null && nnPrtNo!="" && nnPrtNo.length==14 && nnPrtNo.substring(2,4)=="51")//��ͥ��
	{
	  var tSequenceNo = PolAddGrid.getSelNo();
	  //if(!confirm("tSequenceNo="+tSequenceNo)) return false;
	  if(tSequenceNo == "1")
	  {
/*	  	var sql = "select impartparamname, impartparam"
         + " from lccustomerimpartparams"
         + " where 1 = 1"
         //+ " and customernotype = '1'" 
         //+ " and customerno='"+InsuredNo+"' "            
         + " and impartcode = 'D0101'"
         + " and impartver = 'D01'"
         + " and impartparamno in ('1', '2')"
         + " and contno = '"+tContNo+"' "
         + " order by customernotype desc,customerno,impartparamno ";
       //prompt('',sql);
*/	   
	var sql = "";
	var sqlid28="EdorAppManuUWInputSql28";
	var mySql28=new SqlClass();
	mySql28.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql28.setSqlId(sqlid28);//ָ��ʹ�õ�Sql��id
	mySql28.addSubPara(tContNo);//ָ������Ĳ���
	sql=mySql28.getString();
	   
	   var arr1 = easyExecSql(sql);
	   if(arr1!=null)
	   {
			document.all('InsuredStature').value=arr1[0][1];
			document.all('InsuredWeight').value=arr1[1][1];
			document.all('InsuredBMI').value=Math.round(parseFloat(arr1[1][1])/parseFloat(arr1[0][1])/parseFloat(arr1[0][1])*10000);
		}
		
		//��ѯ������
/*	    var insql = "select impartparam"
         + " from lccustomerimpartparams"
         + " where 1 = 1"
         //+ " and customernotype = '1'"  
         //+ " and customerno='"+InsuredNo+"' "        
         + " and impartcode = 'D0119'"
         + " and impartver = 'D02'"
         + " and impartparamno ='1'"
         + " and contno = '"+tContNo+"' "
         + " order by customernotype desc,customerno,impartparamno ";
	    //prompt("",insql);
*/	    
	var insql = "";
	var sqlid29="EdorAppManuUWInputSql29";
	var mySql29=new SqlClass();
	mySql29.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql29.setSqlId(sqlid29);//ָ��ʹ�õ�Sql��id
	mySql29.addSubPara(tContNo);//ָ������Ĳ���
	insql=mySql29.getString();
	    
	    var incomeway = easyExecSql(insql);
	    if(incomeway!=null && incomeway[0][0] !="")
	    {
	       document.all('Insuredincome').value = incomeway[0][0];
	    }	    
	  }
	  else if(tSequenceNo == "2")
	  {
/*	  	var sql = "select impartparamname, impartparam"
         + " from lccustomerimpartparams"
         + " where 1 = 1"
         //+ " and customernotype = '1'" 
         //+ " and customerno='"+InsuredNo+"' "            
         + " and impartcode = 'D0101'"
         + " and impartver = 'D01'"
         + " and impartparamno in ('3', '4')"
         + " and contno = '"+tContNo+"' "
         + " order by customernotype desc,customerno,impartparamno ";
*/	   
	var sql = "";
	var sqlid30="EdorAppManuUWInputSql30";
	var mySql30=new SqlClass();
	mySql30.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql30.setSqlId(sqlid30);//ָ��ʹ�õ�Sql��id
	mySql30.addSubPara(tContNo);//ָ������Ĳ���
	sql=mySql30.getString();
	   
	   var arr1 = easyExecSql(sql);
	   if(arr1!=null)
	   {
			document.all('InsuredStature').value=arr1[0][1];
			document.all('InsuredWeight').value=arr1[1][1];
			document.all('InsuredBMI').value=Math.round(parseFloat(arr1[1][1])/parseFloat(arr1[0][1])/parseFloat(arr1[0][1])*10000);
		}
		
		//��ѯ������
/*	    var insql = "select impartparam"
         + " from lccustomerimpartparams"
         + " where 1 = 1"
         //+ " and customernotype = '1'"  
         //+ " and customerno='"+InsuredNo+"' "        
         + " and impartcode = 'D0119'"
         + " and impartver = 'D02'"
         + " and impartparamno ='3'"
         + " and contno = '"+tContNo+"' "
         + " order by customernotype desc,customerno,impartparamno ";
	    //prompt("",insql);
*/	    
	var insql = "";
	var sqlid31="EdorAppManuUWInputSql31";
	var mySql31=new SqlClass();
	mySql31.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql31.setSqlId(sqlid31);//ָ��ʹ�õ�Sql��id
	mySql31.addSubPara(tContNo);//ָ������Ĳ���
	insql=mySql31.getString();
	    
	    var incomeway = easyExecSql(insql);
	    if(incomeway!=null && incomeway[0][0] !="")
	    {
	       document.all('Insuredincome').value = incomeway[0][0];
	    }	    
	  }
	  else if(tSequenceNo == "3")
	  {
/*	  	var sql = "select impartparamname, impartparam"
         + " from lccustomerimpartparams"
         + " where 1 = 1"
        // + " and customernotype = '1'" 
        // + " and customerno='"+InsuredNo+"' "            
         + " and impartcode = 'D0101'"
         + " and impartver = 'D01'"
         + " and impartparamno in ('5', '6')"
         + " and contno = '"+tContNo+"' "
         + " order by customernotype desc,customerno,impartparamno ";
*/	   
	var sql = "";
	var sqlid32="EdorAppManuUWInputSql32";
	var mySql32=new SqlClass();
	mySql32.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql32.setSqlId(sqlid32);//ָ��ʹ�õ�Sql��id
	mySql32.addSubPara(tContNo);//ָ������Ĳ���
	sql=mySql32.getString();
	   
	   var arr1 = easyExecSql(sql);
	   if(arr1!=null)
	   {
			document.all('InsuredStature').value=arr1[0][1];
			document.all('InsuredWeight').value=arr1[1][1];
			document.all('InsuredBMI').value=Math.round(parseFloat(arr1[1][1])/parseFloat(arr1[0][1])/parseFloat(arr1[0][1])*10000);
		}
	  }
	  
	}
	else
	{
/*		 var sql = "select impartparamname, impartparam"
         + " from lccustomerimpartparams"
         + " where 1 = 1"
         //+ " and customernotype = '1'"  
         //+ " and customerno='"+InsuredNo+"' "        
         + " and impartcode = 'A0101'"
         + " and impartver = 'A01'"
         + " and impartparamno in ('1', '2')"
         + " and contno = '"+tContNo+"' "
         + " order by customernotype desc,customerno,impartparamno ";
       //  prompt('',sql);
*/	   
	var sql = "";
	var sqlid33="EdorAppManuUWInputSql33";
	var mySql33=new SqlClass();
	mySql33.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql33.setSqlId(sqlid33);//ָ��ʹ�õ�Sql��id
	mySql33.addSubPara(tContNo);//ָ������Ĳ���
	sql=mySql33.getString();
	   
	   var arr1 = easyExecSql(sql);
	   if(arr1!=null)
	   {
			document.all('InsuredStature').value=arr1[0][1];
			document.all('InsuredWeight').value=arr1[1][1];
			document.all('InsuredBMI').value=Math.round(parseFloat(arr1[1][1])/parseFloat(arr1[0][1])/parseFloat(arr1[0][1])*10000);
		}
		
		//��ѯ������
/*	    var insql = "select impartparam"
         + " from lccustomerimpartparams"
         + " where 1 = 1"
         //+ " and customernotype = '1'"  
         //+ " and customerno='"+InsuredNo+"' "        
         + " and impartcode = 'A0120'"
         + " and impartver = 'A02'"
         + " and impartparamno ='1'"
         + " and contno = '"+tContNo+"' "
         + " order by customernotype desc,customerno,impartparamno ";
	    //prompt("",insql);
*/	    
	var insql = "";
	var sqlid34="EdorAppManuUWInputSql34";
	var mySql34=new SqlClass();
	mySql34.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql34.setSqlId(sqlid34);//ָ��ʹ�õ�Sql��id
	mySql34.addSubPara(tContNo);//ָ������Ĳ���
	insql=mySql34.getString();
	    
	    var incomeway = easyExecSql(insql);
	    if(incomeway!=null && incomeway[0][0] !="")
	    {
	       document.all('Insuredincome').value = incomeway[0][0];
	    }	    
	}
	
	//���㱻���˷��ձ���
    var tSumAmnt =0;
//    var strSQL =  "SELECT healthyamnt2('" + tInsuredNo +"','1','1') from dual ";   
    
    var strSQL = "";
	var sqlid35="EdorAppManuUWInputSql35";
	var mySql35=new SqlClass();
	mySql35.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql35.setSqlId(sqlid35);//ָ��ʹ�õ�Sql��id
	mySql35.addSubPara(tInsuredNo);//ָ������Ĳ���
	strSQL=mySql35.getString();
    
    var arr1=easyExecSql(strSQL);
   // prompt("",strSQL);
	if(arr1!=null){
		document.all('InsuredSumLifeAmnt').value=arr1[0][0];
		tSumAmnt = tSumAmnt + parseFloat(arr1[0][0]);
	}
	 
//	 strSQL =  "SELECT healthyamnt2('" + tInsuredNo +"','2','1') from dual ";
    
	var sqlid36="EdorAppManuUWInputSql36";
	var mySql36=new SqlClass();
	mySql36.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql36.setSqlId(sqlid36);//ָ��ʹ�õ�Sql��id
	mySql36.addSubPara(tInsuredNo);//ָ������Ĳ���
	strSQL=mySql36.getString();
    
    var arr2=easyExecSql(strSQL);
	if(arr2!=null){
		document.all('InsuredSumHealthAmnt').value=arr2[0][0];
		tSumAmnt = tSumAmnt + parseFloat(arr2[0][0]);
	}
	
//	 strSQL =  "SELECT healthyamnt2('" + tInsuredNo +"','3','1') from dual ";
    
    var sqlid37="EdorAppManuUWInputSql37";
	var mySql37=new SqlClass();
	mySql37.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql37.setSqlId(sqlid37);//ָ��ʹ�õ�Sql��id
	mySql37.addSubPara(tInsuredNo);//ָ������Ĳ���
	strSQL=mySql37.getString();
    
    var arr3=easyExecSql(strSQL);
	if(arr3!=null){
		document.all('InsuredMedicalAmnt').value=arr3[0][0];
		//tSumAmnt = tSumAmnt + parseFloat(arr3[0][0]);
	}
	
//	strSQL =  "SELECT healthyamnt2('" + tInsuredNo +"','4','1') from dual ";
    
    var sqlid38="EdorAppManuUWInputSql38";
	var mySql38=new SqlClass();
	mySql38.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql38.setSqlId(sqlid38);//ָ��ʹ�õ�Sql��id
	mySql38.addSubPara(tInsuredNo);//ָ������Ĳ���
	strSQL=mySql38.getString();
    
    var arr4=easyExecSql(strSQL);
	if(arr4!=null){
		document.all('InsuredAccidentAmnt').value=arr4[0][0];
		tSumAmnt = tSumAmnt + parseFloat(arr4[0][0]);
	}
	document.all('InsuredSumAmnt').value=tSumAmnt;
	
    //�ۼƱ��� �����������Ͳ����ڽ�����   
/*    strSQL =  "SELECT decode(round(sum(a_Prem),2),'','x',round(sum(a_Prem),2)) FROM"
          + "(select (case"
          + " when s_PayIntv = '1' then"
          + " s_Prem/0.09"
          + " when s_PayIntv = '3' then"
          + " s_Prem/0.27"
          + " when s_PayIntv = '6' then"
          + " s_Prem/0.52"
          + " when s_PayIntv = '12' then"
          + " s_Prem"
          + " end) a_Prem"          
          + " FROM (select distinct payintv as s_PayIntv,"
          + " sum(prem) as s_Prem"
          + " from lcpol c where polno in(select polno "
	          + " from lcpol a"
	          + " where a.insuredno = '"+tInsuredNo+"'"
	          + " or (a.appntno = '"+tInsuredNo+"' and a.riskcode in ('00115000','00115001'))"
	          + " union"
	          + " select b.polno"
	          + " from lcinsuredrelated b"
	          + " where b.customerno = '"+tInsuredNo+"')"
          + " and c.payintv in ('1', '3','6','12')"
          + " and c.uwflag not in ('1', '2', 'a')"
          + " and c.appflag <> '4'" 
          + " and not exists (select 'X'"
	          + " from lccont"
	          + " where ContNo = c.contno"
	          + " and (uwflag in ('1', '2', 'a') or appflag='4' or (state is not null and substr(state,0,4) in ('1002', '1003'))  ))"
          + " group by payintv))";
    //prompt('',strSQL);
*/    
    var sqlid39="EdorAppManuUWInputSql39";
	var mySql39=new SqlClass();
	mySql39.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql39.setSqlId(sqlid39);//ָ��ʹ�õ�Sql��id
	mySql39.addSubPara(tInsuredNo);//ָ������Ĳ���
	mySql39.addSubPara(tInsuredNo);
	mySql39.addSubPara(tInsuredNo);
	strSQL=mySql39.getString();
    
    var arr5=easyExecSql(strSQL);
	if(arr5!=null){	
	       //alert(arr5[0][0]);    
	       if(arr5[0][0]!='x')
	       	  document.all('InsuredSumPrem').value=arr5[0][0];
	       else
		    {
		    	document.all('InsuredSumPrem').value ='0';
		    }
	    }	    
	        
    queryRiskInfo();

 }
 
 /*********************************************************************
 *  ��ѯ�˱�������������Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function queryRiskInfo()
{
		
	var tEdorNo=fm.EdorNo.value;
	var tInsuredNo=fm.InsuredNo.value;
	var tContNo=fm.ContNo.value;
	
/*	var strSql = "select edorno,edortype,polno,mainpolno,riskcode,riskname,amnt,standprem,addfee01,addfee02,addfee03,addfee04,"
	                        + " cvalidate,enddate,payintv,payyears,appflag,uwflag,uwflagold from(";
	strSql = strSql + "select a.EdorNo edorno,a.EdorType edortype,a.polno polno,a.MainPolNo mainpolno,a.riskcode riskcode,b.riskname riskname,a.Amnt amnt,a.standprem standprem,"
	                        + " (select subriskflag from lmriskapp where riskcode=a.riskcode) riskflag,"
	                        + " nvl((select sum(prem) from lpprem where edorno=a.edorno and polno=a.polno and payplancode like '000000%%' and payplantype='01'),0) addfee01,"
	                        + " nvl((select sum(prem) from lpprem where edorno=a.edorno and polno=a.polno and payplancode like '000000%%' and payplantype='02'),0) addfee02,"
	                        + " nvl((select sum(prem) from lpprem where edorno=a.edorno and polno=a.polno and payplancode like '000000%%' and payplantype='03'),0) addfee03,"
	                        + " nvl((select sum(prem) from lpprem where edorno=a.edorno and polno=a.polno and payplancode like '000000%%' and payplantype='04'),0) addfee04,"
	                        + " a.CValiDate cvalidate,a.EndDate enddate,a.PayIntv payintv,a.PayYears payyears,"
	                        + " (select decode(trim(appflag),'1','��Ч','4','��ֹ') from lpcont where edorno=a.EdorNo and contno = a.contno) appflag"
	                        //+ "(select c.codename from ldcode c where c.codetype = 'edoruwstate' and trim(c.code)=trim(a.UWFlag))"
	                        + " , decode((select uwflag from lcpol where polno=a.polno),'0','',(select uwflag from lcpol where polno=a.polno)) uwflag,"	
	                        + " decode((select count(*) from lpuwsub where edorno=a.edorno and contno=a.contno and polno=a.polno and passflag not in ('8','y')),1,'',a.UWFlag) uwflagold "   //�ų��ӷѺͷ��˱�֪ͨ���lpuwsub���޸�                     
	                        + " from LPPol a,lmrisk b where 1=1 and a.appflag not in ('4','9') "
							+ " and edorno='"+tEdorNo+"'"
							+ " and contno='"+tContNo+"'"
							//XinYQ modified on 2006-07-31 : ֧������������ : OLD : + " and insuredno ='"+tInsuredNo+"'"
							+ " and InsuredNo in ('" + tInsuredNo + "', (select MainCustomerNo from LCInsuredRelated where CustomerNo = '" + tInsuredNo + "'))"
							+ " and b.riskcode = a.riskcode";
*/	
	var strSql = "";
	var sqlid40="EdorAppManuUWInputSql40";
	var mySql40=new SqlClass();
	mySql40.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql40.setSqlId(sqlid40);//ָ��ʹ�õ�Sql��id
	mySql40.addSubPara(tEdorNo);//ָ������Ĳ���
	mySql40.addSubPara(tContNo);
	mySql40.addSubPara(tInsuredNo);
	mySql40.addSubPara(tInsuredNo);
	strSql=mySql40.getString();
	
	var cEdorType = fm.EdorType.value.substring(0,2);
	//alert(cEdorType);
	if(cEdorType=='NS')
	{
		strSql = strSql + " union all "
		                + " select '' eodrno,'' edortype,a.polno polno,a.MainPolNo mainpolno,a.riskcode riskcode,b.riskname riskname,a.Amnt amnt,a.standprem standprem, "
		                    + " (select subriskflag from lmriskapp where riskcode=a.riskcode) riskflag,"
		                    + " (case when (select sum(prem) from lcprem where polno=a.polno and payplancode like '000000%%' and payplantype='01') is not null then  (select sum(prem) from lcprem where polno=a.polno and payplancode like '000000%%' and payplantype='01') else 0 end) addfee01,"
	                        + " (case when (select sum(prem) from lcprem where polno=a.polno and payplancode like '000000%%' and payplantype='02') is not null then (select sum(prem) from lcprem where polno=a.polno and payplancode like '000000%%' and payplantype='02') else 0 end) addfee02,"
	                        + " (case when (select sum(prem) from lcprem where polno=a.polno and payplancode like '000000%%' and payplantype='03') is not null then (select sum(prem) from lcprem where polno=a.polno and payplancode like '000000%%' and payplantype='03') else 0 end) addfee03,"
	                        + " (case when (select sum(prem) from lcprem where polno=a.polno and payplancode like '000000%%' and payplantype='04') is not null then (select sum(prem) from lcprem where polno=a.polno and payplancode like '000000%%' and payplantype='04') else 0 end) addfee04,"
	                        + " a.CValiDate cvalidate,a.EndDate enddate,a.PayIntv payintv,a.PayYears payyears,"
	                        + " (select (case trim(appflag) when '1' then '��Ч' when '4' then '��ֹ' end) from lccont where contno = a.contno)"
	                        //+ "(select c.codename from ldcode c where c.codetype = 'edoruwstate' and trim(c.code)=trim(a.UWFlag))"
	                        + " , a.UWFlag uwflag,"	
	                        + " a.UWFlag uwflagold "                        
	                        + " from LCPol a,lmrisk b where 1=1 and a.appflag not in ('2','4','9') "
							+ " and a.contno='"+tContNo+"'"
							//XinYQ modified on 2006-07-31 : ֧������������ : OLD : + " and insuredno ='"+tInsuredNo+"'"
							+ " and a.InsuredNo in ('" + tInsuredNo + "', (select MainCustomerNo from LCInsuredRelated where CustomerNo = '" + tInsuredNo + "'))"
							+ " and b.riskcode = a.riskcode ";
	}
//	strSql = strSql + " )D order by D.riskflag,D.riskcode ";
	
    var brr=easyExecSql(strSql);
   // console.log(strSql);
    if(brr)
    {
        turnPage.queryModal(strSql, RiskGrid);
      	
    }
    else
    {
    	
    	alert("�ñ�����û��������Ҫ��ȫ�˱���");
    	
    	//divEdorManuUWResultInfo.style.display='none'; //�����º˱����۵������
    	//divEdorAppUWInfo.style.display='none'; //�����º˱����۵������
    	//DivLPPol.style.display = 'none';
    	//divReturn.style.display='';
        return;
    	
    }


  return true;  					
}

 /*********************************************************************
 *  ���뱻������Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showInsuredInfo()
{

//      var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
//        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//        showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

        var tSelNo = PolAddGrid.getSelNo()-1;
        var tContNo = fm.ContNo.value;
        var tInsuredNo = PolAddGrid.getRowColData(tSelNo,1);
        var tMissionID = fm.MissionID.value;
        var tSubMissionID = fm.SubMissionID.value;
        var tEdorNo = fm.EdorNo.value;

        var tEdorAcceptNo = fm.EdorAcceptNo.value;
        var tOtherNo = fm.OtherNo.value;
        var tOtherNoType = fm.OtherNoType.value;
        var tEdorAppName = fm.EdorAppName.value;
        var tApptype     = fm.Apptype.value;
        var tManageCom   = fm.ManageCom.value;
        var tPrtNo       = fm.PrtNo.value;
        var tEdorType    = fm.EdorType.value;

        var strTran = "ContNo="+tContNo+"&InsuredNo="+tInsuredNo+"&MissionID="
                               +tMissionID+"&SubMissionID="+tSubMissionID+"&EdorNo="
                               +tEdorNo+"&EdorAcceptNo="+tEdorAcceptNo+"&PrtNo="+tPrtNo+"&OtherNo="
                               +tOtherNo+"&OtherNoType="+tOtherNoType+"&EdorAppName="
                               +tEdorAppName+"&Apptype="+tApptype+"&ManageCom="+tManageCom+"&EdorType="+tEdorType;
        window.open("./EdorManuUWInsuredMain.jsp?"+strTran, "EdorManuUWInsured", "top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
//      showInfo.close();
}

 /*********************************************************************
 *  ���˫����ʼ���˱�����ѡ��
 *  ����: ��ʼ���˱�����ѡ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function initUWState(cObjCode, cObjName)
{
    var UWType = document.all('UWType').value;
    //alert(UWType);
    var UWStateCode;
    if (UWType == "EdorApp")               //���뼶��������Ŀǰ����ϵͳ�Զ������뼶�˱����ۣ������û�
    {
        UWStateCode = "edorcontuw";
    }
    else if (UWType == "EdorMain")         //������
    {
        UWStateCode = "edorcontuw";
    }
    initUWCodeData(UWStateCode);
    return showCodeListEx('nothis',[cObjCode,cObjName],[0,1],null,null,null,1);
}

/*********************************************************************
 *  ��ʼ���˱�����ѡ��
 *  ����: ��ʼ���˱�����ѡ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function onKeyUpUWState(cObjCode, cObjName)
{
    var UWType = document.all('UWType').value;
    var UWStateCode;

    if (UWType == "EdorApp")
    {
        UWStateCode = "edorappuwstate";
    }
    else if (UWType == "EdorMain")
    {
        UWStateCode = "edorcontuw";
    }
    initUWCodeData(UWStateCode);
    return showCodeListKeyEx('nothis',[cObjCode,cObjName],[0,1],null,null,null,1);
}

//��ʼ���˱����۴��룬����Ҫ��othersign���򣬹�д�˺���
function initUWCodeData(tCodeType)
{
        var i,j,m,n;
        var returnstr;
        var tTurnPage = new turnPageClass();

//        var strSQL = " select code,codename from ldcode where codetype = '"+tCodeType+"' order by othersign ";
        
    var strSQL = "";
	var sqlid41="EdorAppManuUWInputSql41";
	var mySql41=new SqlClass();
	mySql41.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql41.setSqlId(sqlid41);//ָ��ʹ�õ�Sql��id
	mySql41.addSubPara(tCodeType);//ָ������Ĳ���
	strSQL=mySql41.getString();
        
        tTurnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
        if (tTurnPage.strQueryResult == "")
        {
          return "";
        }
        tTurnPage.arrDataCacheSet = decodeEasyQueryResult(tTurnPage.strQueryResult);
        var returnstr = "";
        var n = tTurnPage.arrDataCacheSet.length;
        if (n > 0)
        {
            for( i = 0;i < n; i++)
            {
                m = tTurnPage.arrDataCacheSet[i].length;
                if (m > 0)
                {
                    for( j = 0; j< m; j++)
                    {
                        if (i == 0 && j == 0)
                        {
                            returnstr = "0|^"+tTurnPage.arrDataCacheSet[i][j];
                        }
                        if (i == 0 && j > 0)
                        {
                            returnstr = returnstr + "|" + tTurnPage.arrDataCacheSet[i][j];
                        }
                        if (i > 0 && j == 0)
                        {
                            returnstr = returnstr+"^"+tTurnPage.arrDataCacheSet[i][j];
                        }
                        if (i > 0 && j > 0)
                        {
                            returnstr = returnstr+"|"+tTurnPage.arrDataCacheSet[i][j];
                        }
                    }
                }
                else
                {
                    alert("��ѯ�˱���������ʧ��!!");
                    return "";
                }
             }
         }
         else
         {
             alert("��ѯ�˱���������ʧ��!");
             return "";
         }
         fm.EdorUWState.setAttribute('CodeData', returnstr);
         //return returnstr;
}
/*********************************************************************
 *  ��ȫ�˱�ȡ��
 *  ����: ��ȫ�˱�ȡ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function UWcancel()
{
    document.all('EdorUWState').value = "";
    document.all('EdorUWStateName').value = "";
    document.all('UWDelay').value = "";
    document.all('UWPopedomCode').value = "";
    document.all('UWIdea').value = "";
}
/*********************************************************************
 *  ����
 *  ����: ҳ�����ʾ����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function returnParent()
{
    //top.opener.initSelfGrid();
    top.opener.privateWorkPoolQuery();
    top.close();
    top.opener.focus();
}

function afterCodeSelect( cCodeName, Field )
{
    if (cCodeName == "edorappstate") {
    	//alert(1);
     var tSendNoticeFlag = 0;    	
     if (Field.value == "9") {
     //��ѯ��ͬ�˱�����
	 //		    var strSQL =  "select state from LPUWMasterMain where edorno='"+fm.EdorNo.value+"' and contno='"+fm.ContNo.value+"'";
	        
	var strSQL = "";
	var sqlid42="EdorAppManuUWInputSql42";
	var mySql42=new SqlClass();
	mySql42.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql42.setSqlId(sqlid42);//ָ��ʹ�õ�Sql��id
	mySql42.addSubPara(fm.EdorNo.value);//ָ������Ĳ���
	mySql42.addSubPara(fm.ContNo.value);
	strSQL=mySql42.getString();
	        
	var brr = easyExecSql(strSQL, 1, 0,"","",1);
	
	    if ( brr!=null && brr[0][0]!=null && brr[0][0]!='' && brr[0][0] == "4")
	    {
		      //20130829 modify by huxj �˱�֪ͨ�鲻���º˱�����ʱ���ţ��޸�Ϊ���������һ�𷢷�
		      //  tSendNoticeFlag = 1;
		      tSendNoticeFlag = 0;
		      fm.SendNoticeFlag.value="0";
    	}
    	if ( brr!=null && brr[0][0]!=null && brr[0][0]!='' && brr[0][0] == "1")
    	{
		      tSendNoticeFlag = 1;
		      fm.SendNoticeFlag.value="1";
    	}
    	//20130829 modify by huxj �˱�֪ͨ�鲻���º˱�����ʱ���ţ��޸�Ϊ���������һ�𷢷�
    	if(tSendNoticeFlag == 1)
    		// divSendNotice.style.display='';
    		divSendNotice.style.display='none';
    	else
    		divSendNotice.style.display='none';	
    }
   }
}

/*********************************************************************
 *  ��ȫ�������˱������ύ
 *  ����: ��ȫ�������˱������ύ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function UWSubmit()
{
	if(!checkUWState('3',fm.MissionID.value,fm.SubMissionID.value,"�º˱����ۣ�"))
    	return false;
    	
    UWType = document.all('UWType').value;
    
        var tEdorUWState    = fm.EdorUWState.value;	    
	    if(tEdorUWState == "")
	    {	    	
	        alert("��¼���ͬ�˱�����!");
	        fm.uwconfirm.disabled=false;
    		fm.UWType.value="EdorMain";
	        return ;
	    }
	//alert("UWType=="+UWType);//return false;   
		
    if (UWType == "EdorApp")
    {
		if (fm.EdorAcceptNo.value == "" )
        {
            alert("��ȫ�����Ϊ�գ�");
            return;
        }	
        
        var tAppUWState    = fm.AppUWState.value;	   
	    if(tAppUWState == "")
	    {
	        alert("��¼�뱣ȫ�˱�����!");
	        return ;
	    }
	    
	    if(checkNotice())
	    {
	    	sendAllForNotice();
	    	return;
	    }
	    
	    if(divSendNotice.style.display == '')
	    {
	    	var tSendNoticeFlag = fm.SendNoticeFlag.value;//'1';//��ӡ  '0';//����ӡ
	    	if(tSendNoticeFlag == null || tSendNoticeFlag == '')
		    {
		    	alert("��ѡ���Ƿ��ӡ�˱�֪ͨ�飡");
		    	return false;
		   	}		    		    	
	    }
	    
	    //alert(fm.SendNoticeFlag.value);return false;	    
    }
    else if (UWType == "EdorMain")
    {
    	    for(var i =0 ;i<RiskGrid.mulLineCount;i++)
    	    {
    	    	var tflag = RiskGrid.getRowColData(i,19);
    	    	if(tflag == null || tflag == '')
    	    	{
    	    		alert("������δ�º˱����ۣ�");
					return false;
				}
    	    }
    	    //alert(111);
    	
    	if(fm.EdorUWState.value=="9"){
		//У������мӷ���Ϣ�����±�׼��
/*		var strSql="select 1 from ("
		            +"select 1 from lpprem where contno='"+fm.ContNo.value+"'"
					+" and edorno='"+fm.EdorNo.value+"' and edortype='"+document.all('EdorType').value
					+"' and payplancode like '000000%%' and (addform is null or addform <>'3')"
					+ " union all "
					+" select 1 from lpcspec where contno='"+fm.ContNo.value+"'"
					+" and edorno='"+fm.EdorNo.value+"' and edortype='"+document.all('EdorType').value
					+"' and needprint='Y')";
		//prompt('',strSql);
*/		
	var strSql = "";
	var sqlid43="EdorAppManuUWInputSql43";
	var mySql43=new SqlClass();
	mySql43.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql43.setSqlId(sqlid43);//ָ��ʹ�õ�Sql��id
	mySql43.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	mySql43.addSubPara(fm.EdorNo.value);
	mySql43.addSubPara(document.all('EdorType').value);//ָ������Ĳ���
	mySql43.addSubPara(fm.ContNo.value);
	mySql43.addSubPara(fm.EdorNo.value);//ָ������Ĳ���
	mySql43.addSubPara(document.all('EdorType').value);
	strSql=mySql43.getString();
		
		turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1); 
		if(turnPage.strQueryResult){
			alert("�мӷѻ���Լ�������±�׼��Ľ��ۣ�");
			return false;
			}
		}	
        if (fm.EdorNo.value == null || fm.EdorNo.value == "" ||
            fm.ContNo.value == null || fm.ContNo.value == "" )
        {
            alert("��ѡ��Ҫ�˱��ı�����");
            return false;
        }
        
        var tEdorUWState    = fm.EdorUWState.value;
	    var tUWDelay        = fm.UWDelay.value;
	    var tUWIdea         = fm.UWIdea.value;
	    var tUWPopedomCode  = fm.UWPopedomCode.value;
	
	    if(tEdorUWState == "")
	    {	    	
	        alert("��¼���ͬ�˱�����!");
	        return ;
	    }
	    if(tEdorUWState == "6" && tUWPopedomCode == "")
	    {
	        alert("��ѡ���ϱ�����!");
	        return ;
	    }
/*	    if(checkNotice()){   	
	    	if(confirm('�˵���֪ͨ��δ�·�,ȷ��Ҫ����˱�������')){
	    	}else{
	    		return;
	    	}
	    }*/
    }

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

    document.all('ActionFlag').value = "UWMANUSUBMIT";
    fm.action = "./EdorAppManuUWSave.jsp";
    //alert(1);
    lockScreen('lkscreen');
    fm.submit();
}
function sendAllForNotice()
{
//	var tSQL_SubMissionId = "select (case when max(submissionid) is not null then max(submissionid) else 'x' end) from lwmission where missionid='"+MissionID+"' "
    // + " and activityid in (select activityid from lwactivity where functionid='10020004') ";
//      + " and activityid in(select activityid from lwactivity where functionid in( '10020004','10020305','10020314','10020332','10020330')) ";
	
	var sqlid63="EdorAppManuUWInputSql63";
	var mySql63=new SqlClass();
	mySql63.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql63.setSqlId(sqlid63);//ָ��ʹ�õ�Sql��id
	mySql63.addSubPara(MissionID);//ָ������Ĳ���
	var tSQL_SubMissionId=mySql63.getString();
	
	
	var tempSubMissionID = "";
	var brr = easyExecSql(tSQL_SubMissionId);//prompt("",tSQL_SubMissionId);return false;
	tempSubMissionID = brr[0][0];
	if(tempSubMissionID == 'x' )
	{
		//alert(" �����ѷ���δ���յĺ˱�֪ͨ��,�������ٷ��ͺ˱�֪ͨ��! ");
		alert(" ���˳��˹��˱����沢���½�����ٴν��в���! ");
		return false;
	}
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
    fm.action = "../uw/BQSendAllNoticeChk.jsp";
    fm.submit();	
}
/*********************************************************************
 *  ��ִ̨����Ϸ�����Ϣ
 *  ����: ��ִ̨����Ϸ�����Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterSubmit1(FlagStr, content)
{
    try{showInfo.close();}catch(ex){}

    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	
unlockScreen('lkscreen');
    if (FlagStr == "Succ" )
    {
        if (document.all('ActionFlag').value == "UWMANUSUBMIT")  //�˹��˱�ȷ��
        {
            var UWType = fm.UWType.value;

            if (UWType == "EdorApp")
            {//alert(360);
                //���뼶 �����ϼ�ҳ��
                returnParent();

            }
            else if (UWType == "EdorMain")
            {
                //������
                initForm();
                DivDetailInfo.style.display='none';
                //divContUWLable.style.display = 'none';
                //divFormerContUWInfo.style.display = 'none';//ԭ�˱�����ͬʱ����
                divAppUWLable.style.display = '';
                divAppUWInfo.style.display = '';
                //fm.UWType.value="EdorApp";
                fm.uwconfirm.disabled=false;
                //showEdorMainList();
            }
            //fm.EdorUWState.value = "";
            //fm.edoruwstateName.value = "";
            //fm.UWIdea.value = "";
        }

        if (document.all('ActionFlag').value == "UWMANUAPPLY")
        {
            top.opener.easyQueryClickSelf();
            //�˹��˱�����ɹ�����Ҫ���²�ѯ�˹��˱�ȷ�Ͻڵ��MissionID��SubMissionID
            //��ѯ��ȫ�˹��˱�ȷ�Ͻڵ�
/*            strSQL =  " select submissionid "
                    + " from lwmission "
                    + " where activityid = '0000000006' "
                    + " and missionid = '" + fm.MissionID.value + "'";
*/            
	var sqlid44="EdorAppManuUWInputSql44";
	var mySql44=new SqlClass();
	mySql44.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql44.setSqlId(sqlid44);//ָ��ʹ�õ�Sql��id
	mySql44.addSubPara(fm.MissionID.value);//ָ������Ĳ���
	strSQL=mySql44.getString();
            
            var brr = easyExecSql(strSQL, 1, 0,"","",1);

            if ( brr )
            {
                brr[0][0]==null||brr[0][0]=='null'?'0':fm.SubMissionID.value = brr[0][0];
            }

        }
    }

    if (FlagStr == "Fail" )
    {
        if (document.all('ActionFlag').value == "UWMANUAPPLY")
        {
            //�˹��˱�����
            divDetailInfo.style.display='none';
            divEdorAppUWResultInfo.style.display='none';

        }
        if (document.all('ActionFlag').value == "UWMANUSUBMIT")
        {
            //�˹��˱�ȷ��
            var UWType = fm.UWType.value;

            if (UWType == "EdorMain")
            {
				fm.uwconfirm.disabled=false;
            }
        }
    }
	//unlockScreen('lkscreen');
}

//��������Լ�����ڵ㷵�ؽӺ���
function afterSubmit( FlagStr, content )
{
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content;
  if (FlagStr == "Fail" )
  {
    alert(content);
  }
  else
  {
  }
}

function afterSubmitSendNotice( FlagStr, content )
{
	try{showInfo.close();}catch(ex){}

    alert(content);

}

function uwSelected()
{
    var uw = fm.EdorUWState.value;
    switch(uw)
    {
        case "":
        case "2": divUwDelayTitle.style.display = '';
                  divUwDelay.style.display = '';
                  break;
        default:  divUwDelayTitle.style.display = 'none';
                  divUwDelay.style.display = 'none';
                  break;
    }
}

/*************��ť��������*******Begin***************Added by liurx************2005-06-22******************/

//Ͷ���˽�����֪��ѯ
function queryHealthImpart()
{

    window.open("../uw/BQHealthImpartQueryMain.jsp?CustomerNo="+document.all('AppntNo').value,"window1", "top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");

}

//����Ͷ����Ϣ
function showApp(cindex)
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
  var iWidth=550;      //�������ڵĿ��; 
  var iHeight=250;     //�������ڵĸ߶�; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();

  var cContNo=fm.ContNo.value;
  var cAppntNo = fm.AppntNo.value;
 // alert(cAppntNo);

 if (cindex == 1)
	  window.open("../uw/UWAppMain.jsp?ContNo="+cContNo+"&CustomerNo="+cAppntNo+"&type=1","",sFeatures);
 else  if (cindex == 2)
 	 window.open("../uw/UWAppMain.jsp?ContNo="+cContNo+"&CustomerNo="+document.all('InsuredNo').value+"&type=2","",sFeatures);
 else
	  window.open("../uw/UWAppFamilyMain.jsp?ContNo="+cContNo,"",sFeatures);

showInfo.close();

}

//Ͷ���˱����ۼ���Ϣ
function amntAccumulate()
{
    window.open("../uw/AmntAccumulateMain.jsp?CustomerNo="+document.all('AppntNo').value,"window1", "top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
}

//Ͷ�����ѳб�������ѯ
function queryProposal()
{

    window.open("../uw/ProposalQueryMain.jsp?CustomerNo="+fm.AppntNo.value,"window1", "top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
}
//Ͷ����δ�б�������ѯ
function queryNotProposal(){

    window.open("../uw/NotProposalQueryMain.jsp?CustomerNo="+document.all('AppntNo').value,"window1", "top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
}
//Ͷ���˼�����ȫ��ѯ
function queryEdor()
{
    //window.open("../uw/EdorQueryMain.jsp?CustomerNo="+document.all('AppntNo').value,"window1", "top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
    var CustomerNo = fm.AppntNo.value;
    var EdorAcceptNo = fm.EdorAcceptNo.value;
    var varSrc = "CustomerNo="+CustomerNo+"&EdorAcceptNo="+EdorAcceptNo;
    window.open("../bq/EdorAgoQueryMain.jsp?"+varSrc,"window1", "top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
}

//�����˽�����֪��ѯ
function queryHealthImpartInd()
{	
	var newWindow = window.open("../uw/BQHealthImpartQueryMain.jsp?CustomerNo="+document.all('InsuredNo').value,"window1", 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

}

//���������ѯ
function queryClaim()
{
    window.open("../uw/ClaimQueryMain.jsp?CustomerNo="+document.all('InsuredNo').value,"window1", "top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
}

function NewChangeRiskPlan()
{
    var strSql="";
    var tContNo=fm.ContNo.value;//alert("contno=="+tContNo);return false;
    var tEdorNo=fm.EdorNo.value;//alert("tEdorNo=="+tEdorNo);return false;
    var MissionID=fm.MissionID.value;//alert("tEdorNo=="+tEdorNo);return false;
    var SubMissionID=fm.SubMissionID.value;//alert("tEdorNo=="+tEdorNo);return false;
    var InsuredNo=fm.InsuredNo.value;//alert("tEdorNo=="+tEdorNo);return false;
    var InsuredSumLifeAmnt=fm.InsuredSumLifeAmnt.value;//alert("tEdorNo=="+tEdorNo);return false;
    var InsuredSumHealthAmnt=fm.InsuredSumHealthAmnt.value;//alert("tEdorNo=="+tEdorNo);return false;
    var InsuredNo=fm.InsuredNo.value;//alert("tEdorNo=="+tEdorNo);return false;
    var EdorAcceptNo=fm.EdorAcceptNo.value;//alert("EdorAcceptNo=="+EdorAcceptNo);return false;
    var EdorType=fm.EdorType.value;//alert("EdorType=="+EdorType);return false;
    //strSql="select salechnl from lccont where contno='"+prtNo+"'";
/*    strSql="select case lmriskapp.riskprop"
            +" when 'I' then '1'"
	        +" when 'G' then '2'"
	        +" when 'Y' then '3'"
	        +" when 'T' then '5'"
           + " end"
           + " from lmriskapp"
           + " where riskcode in (select riskcode"
           + " from lppol"
           + " where polno = mainpolno"
           + " and contno = '"+tContNo+"' and edorNo='"+tEdorNo+"')" ;   
*/    
    var sqlid45="EdorAppManuUWInputSql45";
	var mySql=new SqlClass();
	mySql.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql.setSqlId(sqlid45);//ָ��ʹ�õ�Sql��id
	mySql.addSubPara(tContNo);//ָ������Ĳ���
	mySql.addSubPara(tEdorNo);
	strSql=mySql.getString();

    
    arrResult = easyExecSql(strSql);

    //alert(1);
    if(arrResult==null){
/*        strSql = " select * from ("
               + " select case missionprop5"
               + " when '05' then '3'"
               + " when '12' then '3'"
               + " when '13' then '5'"
               + " else '1'"
               + " end"
               + " from lbmission"
               + " where missionprop1 = '"+tContNo+"'"
               + " and activityid = '0000001099'"
               + " union"
               + " select case missionprop5"
               + " when 'TB05' then '3'"
               + " when 'TB12' then '3'"
               + " when 'TB06' then '5'"
               + " else '1'"
               + " end"
               + " from lbmission"
               + " where missionprop1 = '"+tContNo+"'"
               + " and activityid = '0000001098'"
               + ") where rownum=1";
*/       
    var sqlid46="EdorAppManuUWInputSql46";
	var mySql46=new SqlClass();
	mySql46.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql46.setSqlId(sqlid46);//ָ��ʹ�õ�Sql��id
	mySql46.addSubPara(tContNo);//ָ������Ĳ���
	mySql46.addSubPara(tContNo);
	strSql=mySql46.getString();
       
        arrResult = easyExecSql(strSql);    
      //  alert(arrResult);           
    }
    var BankFlag ="";
    if(arrResult!=null)
    	BankFlag = arrResult[0][0];
    //alert("BankFlag="+arrResult[0][0]); 
    
    
/*    var strSql2="select missionprop5 from lbmission where activityid='0000001099' "
    	+ " and missionprop1=(select prtno from lpcont where edorno='"+tEdorNo+"' and contno='"+tContNo+"')";
*/    
    var strSql2="";
    var sqlid47="EdorAppManuUWInputSql47";
	var mySql47=new SqlClass();
	mySql47.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql47.setSqlId(sqlid47);//ָ��ʹ�õ�Sql��id
	mySql47.addSubPara(tEdorNo);//ָ������Ĳ���
	mySql47.addSubPara(tContNo);
	strSql2=mySql47.getString();
    
    var crrResult = easyExecSql(strSql2);
    var SubType="";
    if(crrResult!=null){
      SubType = crrResult[0][0];
    }    
    //alert(2); 
     var NoType = "1";
	var InsuredNo = document.all('InsuredNo').value;
	window.open("../uw/BQChangeRiskPlanMain.jsp?ContNo="+tContNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&InsuredNo="+InsuredNo+"&InsuredSumLifeAmnt="+fm.InsuredSumLifeAmnt.value+"&InsuredSumHealthAmnt="+fm.InsuredSumHealthAmnt.value+"&NoType="+NoType+"&BankFlag="+BankFlag+"&SubType="+SubType+"&QueryType=3&EdorNo="+tEdorNo+"&EdorAcceptNo="+EdorAcceptNo+"&EdorType="+EdorType,"window1");  	

}

//ɨ�����ѯ
function ScanDetail()
{
     var EdorAcceptNo    = document.all('EdorAcceptNo').value;
     var tUrl="../bq/ImageQueryMain.jsp?BussNo="+EdorAcceptNo+"&BussType=BQ";
     OpenWindowNew(tUrl,"��ȫɨ��Ӱ��","left");
}
//��ǰ�˱���¼
function showNewUWSub()
{
//  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
//  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

  tContNo=fm.ContNo.value;
  tEdorNo=fm.EdorNo.value;

  window.open("./EdorUWErrMain.jsp?ContNo="+tContNo+"&EdorNo="+tEdorNo,"window1", "top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");

//  showInfo.close();

}
//   Ͷ����������ϲ�ѯ
function showBeforeHealthQ()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  var cContNo=fm.ContNo.value;
  var cProposalContNo=fm.ProposalContNo.value;
  var cAppntNo = fm.AppntNo.value;

//alert(800);
  window.open("../uw/BQBeforeHealthQMain.jsp?ContNo="+cProposalContNo+"&CustomerNo="+cAppntNo+"&type=1","",sFeatures);

  showInfo.close();

}

/*********************************************************************
 *  ��ͯ��Լ¼��
 *  ����  ��  tindex
 *  ����ֵ��  ��
 *********************************************************************
 */ 

function showSpec(tindex)
{
  var ContNo = document.all('ContNo').value;
  var InsuredNo = document.all('InsuredNo').value;
  var tEdorNo=fm.EdorNo.value;//alert("tEdorNo=="+tEdorNo);return false;
  var EdorAcceptNo=fm.EdorAcceptNo.value;
  var EdorType=fm.EdorType.value;
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  if (ContNo != "" && MissionID != "" )
  { 	
  	window.open("../uw/BQManuSpecMain.jsp?ContNo="+ContNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&InsuredNo="+InsuredNo+"&tIndex="+tindex+"&QueryFlag=0&EdorNo="+tEdorNo+"&EdorAcceptNo="+EdorAcceptNo+"&EdorType="+EdorType,"window1");  	
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("���ݴ������!");
  }
}

/*********************************************************************
 *  �鿴���±�
 *  ����: �鿴���±�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showNotePad()
{
  /*var strSql = " select prtno from lpcont where edorno='"+fm.EdorNo.value+"' and contno='"+cContNo+"'";
  var arrResult = easyExecSql(strSql);
  if(arrResult!=null)
     cPrtNo = arrResult[0][0];*/

  /*if (cContNo!="") {
    window.open("../uw/UWNotePadMain.jsp?ContNo="+cContNo+"&PrtNo="+cPrtNo+"&OperatePos=3", "window1");
  }*/
  var MissionID = fm.MissionID.value;
  var SubMissionID = fm.SubMissionID.value;
  var ActivityID = fm.ActivityID.value;
  var OtherNo = fm.PrtNo.value;
  var OtherNoType = '1';
  if (OtherNo!="") {
  	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
  	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface= ../uw/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");
  }
  
}

//��ѯ���±�
function checkNotePad(tMissionID){
//	var strSQL="select nvl(count(*),0) from LWNotePad where MissionID='"+tMissionID+"' ";
	
	var strSQL="";
    var sqlid48="EdorAppManuUWInputSql48";
	var mySql48=new SqlClass();
	mySql48.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql48.setSqlId(sqlid48);//ָ��ʹ�õ�Sql��id
	mySql48.addSubPara(tMissionID);//ָ������Ĳ���
	strSQL=mySql48.getString();
	
	var arrResult = easyExecSql(strSQL);
	eval("document.all('NotePadButton').value='���±��鿴 ����"+arrResult[0][0]+"����'");
	
}

//�˱���������
function showUWReport()
{
  var cContNo = fm.ContNo.value;
  var cEdorNo = fm.EdorNo.value;

  if (cContNo!="") {
	window.open("../uw/UWReportMain.jsp?ContNo="+cContNo+"&EdorNo="+cEdorNo+"&NoType=1&OperatePos=3","window5");
   }
}

//��ѯ�˱���������
function checkReport(ContNo){
	
//	var strSQL="select nvl(count(*),0) from LCUWReport where otherno='"+ContNo+"' and OperatePos='3'";
	
	var strSQL="";
    var sqlid49="EdorAppManuUWInputSql49";
	var mySql49=new SqlClass();
	mySql49.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql49.setSqlId(sqlid49);//ָ��ʹ�õ�Sql��id
	mySql49.addSubPara(ContNo);//ָ������Ĳ���
	strSQL=mySql49.getString();
	
	var arrResult = easyExecSql(strSQL);
	eval("document.all('ReportButton').value='�˱���������鿴 ����"+arrResult[0][0]+"����'");
	
}

//���֪ͨ¼��
function showHealth()
{
	//alert(2222);
  var pContNo = fm.ContNo.value;
  var pEdorNo=fm.EdorNo.value;
  var pEdorAcceptNo = fm.EdorAcceptNo.value;

  var pMissionID = fm.MissionID.value;
  var pSubMissionID = fm.SubMissionID.value;
  var pPrtNo = fm.PrtNo.value;
  var pFlag = "1";

  //var varSrc = "EdorAcceptNo="+pEdorAcceptNo+"&EdorNo="+pEdorNo+"&ContNo="+pContNo+"&MissionID="+pMissionID+"&SubMissionID="+pSubMissionID+"&PrtNo="+pPrtNo;
  //window.open("./EdorAppMUHealthMain.jsp?"+varSrc,"window1", "top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
  //window.open("../uw/UWManuHealthMain.jsp?ContNo2="+pContNo+"&MissionID="+pMissionID+"&SubMissionID="+pSubMissionID+"&PrtNo="+pPrtNo,"window1");

  if (pContNo != "")
  {
    window.open("../uw/BQManuHealthMain.jsp?ContNo1="+pContNo+"&MissionID="+pMissionID+"&SubMissionID="+pSubMissionID+"&PrtNo="+pPrtNo+"&Flag="+pFlag+"&EdorNo="+pEdorNo, "window1");
    //showInfo.close();
  }
  else
  {
    showInfo.close();
    alert("����ѡ�񱣵�!");
  }

}

//�˹��˱����������¼��
//�����¼��
function QuestInput()
{
    cContNo = fm.ContNo.value;  //��������
   /*if(!checkUWState('6',MissionID,SubMissionID,'�����¼��'))
	 {
	 	 return false;
	 }
	 //tongmeng 2009-02-06 add
	 //4״̬�������������¼��
	 if(!checkUWState('4',MissionID,SubMissionID,'�����¼��'))
	 {
	 	 return false;
	 }*/
	 
    {
        {
            window.open("../uw/BQQuestInputMain.jsp?ContNo="+cContNo+"&EdorNo="+fm.EdorNo.value+"&EdorType="+fm.EdorType.value+"&Flag=6&MissionID="+fm.MissionID.value+"&SubMissionID="+fm.SubMissionID.value,"window1");
        }
    }
}

function checkUWState(tState,tMissionID,tSubMissionID,tMessage)
{
/*	var tSQL = "select '1' from lwmission where missionid = '"+tMissionID+"' "
	         + " and SubMissionID = '"+tSubMissionID+"' "
	         + " and missionprop18='"+tState+"'"
	         + " and activityid='0000000005' ";
*/	
	var tSQL="";
    var sqlid50="EdorAppManuUWInputSql50";
	var mySql50=new SqlClass();
	mySql50.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql50.setSqlId(sqlid50);//ָ��ʹ�õ�Sql��id
	mySql50.addSubPara(tMissionID);//ָ������Ĳ���
	mySql50.addSubPara(tSubMissionID);
	mySql50.addSubPara(tState);
	tSQL=mySql50.getString();
	
	var arr = easyExecSql( tSQL );
	 if (arr) 
   {
         alert("�ú�ͬ��ǰ����"+tState+"״̬,������"+tMessage);   
         return false;   
   }
	 return true;
} 

//�˱���ѯ
function UWQuery()
{
    var pContNo = fm.ContNo.value;
    var pEdorNo=fm.EdorNo.value;
    window.open("./EdorUWQueryMain.jsp?ContNo="+pContNo+"&EdorNo="+pEdorNo,"window1", "top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
}
//��ѯ�����
function queryHealthReportResult()
{
    var tContNo = fm.ContNo.value;
    var tEdorNo=fm.EdorNo.value;
    var tInsuredNo = fm.InsuredNo.value;
    var tFlag = "1";
window.open("../uw/BQManuHealthQMain.jsp?ContNo="+tContNo+"&EdorNo="+tEdorNo+"&CustomerNo="+tInsuredNo+"&Flag="+tFlag,"window1");
}
//������鱨��
function showRReport()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  var cContNo=fm.ContNo.value;//alert(876);
  var cEdorNo=fm.EdorNo.value;//alert("fm.EdorNo.value=="+fm.EdorNo.value);

  var cMissionID =fm.MissionID.value;
  var cSubMissionID =fm.SubMissionID.value;//alert(cSubMissionID);
  var cPrtNo = fm.PrtNo.value;
  var cEdorType = fm.EdorType.value;//alert("fm.EdorType.value=="+fm.EdorType.value);

  //window.open("./EdorUWManuRReportMain.jsp?ContNo="+cContNo+"&EdorNo="+cEdorNo+"&MissionID="+cMissionID+"&PrtNo="+cPrtNo+"&SubMissionID="+cSubMissionID,"window2");
  window.open("../bq/EdorUWManuRReportMain.jsp?ContNo="+cContNo+"&MissionID="+cMissionID+"&PrtNo="+cPrtNo+"&SubMissionID="+cSubMissionID+"&Flag=1&EdorNo="+cEdorNo+"&EdorType="+cEdorType,"window2", "top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
  showInfo.close();
}

//��ѯ�������
function queryRReportResult()
{

    var tContNo = fm.ContNo.value;
    var tEdorNo = fm.EdorNo.value;
    var tFlag = "1";

    window.open("../uw/BQRReportQueryMain.jsp?ContNo="+tContNo+"&EdorNo="+tEdorNo+"&Flag="+tFlag,"window1", "top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
}

//�������ѯ
function QuestQuery()
{
	var cContNo = fm.ContNo.value;  //��������
	var tEdorNo = fm.EdorNo.value;
	var cflag = "4";

  if (cContNo != "")
  {
	window.open("../uw/BQQuestQueryMain.jsp?ContNo="+cContNo+"&EdorNo="+tEdorNo+"&Flag="+cflag,"window2");
  }
  else
  {
  	alert("����ѡ�񱣵�!");
  }

}
//���˽����ѯ
function QueryTwiceEdorResult()
{
	var cContNo = fm.ContNo.value;  //��������
	var tEdorNo = fm.EdorNo.value;
	var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
	var tEdorAcceptNo = fm.EdorAcceptNo.value;
	var tManageCom   = fm.ManageCom.value;
	var cContNo = fm.ContNo.value;  //��������
	var tEdorNo = fm.EdorNo.value;
	var cMissionID =fm.MissionID.value;
	var cSubMissionID =fm.SubMissionID.value;
	if (cContNo != "")
	  {
		window.open("./BQUWConfirmMain.jsp?teFlag=1&ScanFlag=1&EdorAcceptNo="+tEdorAcceptNo+"&ContNo="+tContNo+"&EdorNo="+tEdorNo+"&ManageCom="+tManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID, "", sFeatures);
	  }
	else
	  {
	  	alert("����ѡ�񱣵�!");
	  }
    
}


function IssueMistake(){
	var PrtNo = fm.PrtNo.value;
	var NoType = "1";
	if(PrtNo == null || PrtNo == "")
	{
		alert("Ͷ������Ϊ�գ�");
		return;
	}

	var varSrc= "?PrtNo="+ PrtNo ;
	var newWindow = OpenWindowNew("../uw/MSQuestMistakeMain.jsp?Interface=../uw/MSQuestMistakeInput.jsp" + varSrc,"���������¼","left");
	
}

//����������ѯ
function QueryRecord(){

    window.open("../uw/RecordQueryMain.jsp?ContNo="+document.all('ContNo').value,"window1", "top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
}

//���ҽԺƷ�ʹ���
function HospitalQuality() {
  var EdorNo = fm.EdorNo.value;
  var ContNo = fm.ContNo.value;	
  var PrtNo = fm.PrtNo.value;

  if (ContNo!="" && PrtNo!="") {
  	window.open("../uw/BQUWHospitalQualityMain.jsp?EdorNo="+EdorNo+"&ContNo="+ContNo+"&PrtNo="+PrtNo, "window1");
  }
}

//�ͻ�Ʒ�ʹ���
function CustomerQuality() {
  ContNo = document.all('ContNo').value;
  if (ContNo!="") {
    window.open("../uw/UWCustomerQualityMain.jsp?ContNo="+ContNo, "window1", "top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
  }
}

//ҵ��ԱƷ�ʹ���
function AgentQuality() {
  ContNo = document.all('ContNo').value;
  if (ContNo!="") {
    window.open("../uw/UWAgentQualityMain.jsp?ContNo="+ContNo, "window1", "top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
  }
}

//��ȫ��ϸ��ѯ
function EdorDetailQuery()
{

    var tEdorAcceptNo = fm.EdorAcceptNo.value;
    var tOtherNoType  = fm.OtherNoType.value;

    var varSrc="&EdorAcceptNo=" + tEdorAcceptNo+"&OtherNoType="+tOtherNoType;
    var newWindow = OpenWindowNew("../sys/BqDetailQueryFrame.jsp?Interface= ../sys/BqDetailQuery.jsp" + varSrc,"��ȫ��ѯ��ϸ","left");

}

function SendAllNotice(){	//alert("fm.SubMissionID.value=="+fm.SubMissionID.value);
    
    if(!checkUWState('3',fm.MissionID.value,fm.SubMissionID.value,"���˱�֪ͨ�飡"))
    	return false;
//    var str6 = "select edortype from lpedoritem where edorno = '"+ document.all('EdorNo').value +"'";
    
    var str6="";
    var sqlid51="EdorAppManuUWInputSql51";
	var mySql51=new SqlClass();
	mySql51.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql51.setSqlId(sqlid51);//ָ��ʹ�õ�Sql��id
	mySql51.addSubPara(document.all('EdorNo').value);//ָ������Ĳ���
	str6=mySql51.getString();
    
    var tEdorType = easyExecSql(str6, 1, 0,"","",1);
    window.open("../uw/BQSendAllNoticeMain.jsp?ContNo="+fm.ContNo.value+"&MissionID="+fm.MissionID.value+"&SubMissionID="+fm.SubMissionID.value+"&EdorNo="+fm.EdorNo.value+"&EdorType="+tEdorType[0][0],"window1");
}


//<!-- XinYQ added on 2005-11-01 : ��ȫ�˱�Ӱ���ѯ : BGN -->
function UWScanQuery()
{
      var ContNo = document.getElementsByName("ContNo")[0].value;
      if (ContNo == "" || ContNo == null)
        return;
      window.open("../uw/EdorUWImageQueryMain.jsp?ContNo=" + ContNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");
}
//<!-- XinYQ added on 2005-11-01 : ��ȫ�˱�Ӱ���ѯ : END -->


//Ͷ����Ӱ���ѯ
function ScanQuery()
{
      var ContNo = fm.ProposalContNo.value;
      if (ContNo == "")
        return;
      window.open("../uw/ImageQueryMain.jsp?ContNo="+ContNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");
}

//������Ϣ��ѯ
function ContInfoQuery()
{
    var tContNo = fm.ContNo.value;
    if(tContNo == null || tContNo == "")
    {
        return;
    }
    window.open("../sys/PolDetailQueryMain.jsp?ContNo=" + tContNo +"&IsCancelPolFlag=0","",sFeatures);
}
//��ȫ�����켣
function MissionQuery()
{
    var pMissionID=fm.MissionID.value;
    var tPrtNo = fm.PrtNo.value;
    window.open("./EdorMissionFrame.jsp?MissionID="+pMissionID+"&PrtNo="+tPrtNo+"&Flag=1","window3");
}
/*********************************************************************
 *  ��ʾԭ�������˱�����
 *  ����: ��ʾԭ�˱�����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function initFormerUW(tContNo,tEdorNo)
{
/*    var strSql = " select uwflag,(select codename from ldcode where codetype = 'contuwstate' and code = uwflag),'' "
               + " from lccont where contno='"+tContNo+"'";
*/    
    var strSql="";
    var sqlid52="EdorAppManuUWInputSql52";
	var mySql52=new SqlClass();
	mySql52.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql52.setSqlId(sqlid52);//ָ��ʹ�õ�Sql��id
	mySql52.addSubPara(tContNo);//ָ������Ĳ���
	strSql=mySql52.getString();
    
    var brr=easyExecSql(strSql, 1, 0,"","",1);
    if(brr)
    {
        fm.FormerUWState.value = brr[0][0];
        fm.FormerUWStateName.value = brr[0][1];
        fm.FormerUWIdea.value = brr[0][2];
        //divFormerContUWInfo.style.display = '';
    }
    else
    {
        fm.FormerUWState.value = "";
        fm.FormerUWStateName.value = "";
        fm.FormerUWIdea.value = "";
        //divFormerContUWInfo.style.display = 'none';
    }
}

/*********************************************************************
 *  ��ʾ�������˱�����
 *  ����: ��ʾ��ǰ�˱�����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function initRecentUW(tContNo,tEdorNo)
{
/*    var strSql = " select State,(select codename from ldcode where codetype = 'edorcontuw' and code = State),UWIdea "
               + " from LPUWMasterMain where contno='"+tContNo+"' and edorno='"+tEdorNo+"'";
*/    
    var strSql="";
    var sqlid53="EdorAppManuUWInputSql53";
	var mySql53=new SqlClass();
	mySql53.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql53.setSqlId(sqlid53);//ָ��ʹ�õ�Sql��id
	mySql53.addSubPara(tContNo);//ָ������Ĳ���
	mySql53.addSubPara(tEdorNo);
	strSql=mySql53.getString();
    
    var brr=easyExecSql(strSql, 1, 0,"","",1);//prompt("",strSql);
    if(brr && brr[0][0]!='5')
    {
        fm.EdorUWState.value = brr[0][0];
        fm.edoruwstateName.value = brr[0][1];
        fm.UWIdea.value = brr[0][2];
        //fm.uwconfirm.disabled=true;
        //fm.uwcancel.disabled=true;
        divAppUWLable.style.display = '';
        divAppUWInfo.style.display = '';
    }
    else
    {
        fm.EdorUWState.value = "";
        fm.edoruwstateName.value = "";
        fm.UWIdea.value = "";
    }
}

/*********************************************************************
 *  ���ɹ������������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function makeWorkFlow(tContNo)
{
		 var sqlid2="EdorAppManuUWInputSql60";
		 var mySql2=new SqlClass();
		 mySql2.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
		 mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
		 mySql2.addSubPara(document.all('MissionID').value);//ָ������Ĳ���
		 mySql2.addSubPara(document.all('SubMissionID').value);//ָ������Ĳ���
		 mySql2.addSubPara(document.all('EdorAcceptNo').value);//ָ������Ĳ���
	   strSQL=mySql2.getString();
    
    var brr = easyExecSql(strSQL);

    if ( brr<1 ){
         fm.action = "../uw/BQManuUWChk.jsp?&ContNo="+tContNo+"&ActivityID="+document.all('Activityid').value+"&MissionID="+document.all('MissionID').value+"&SubMissionID="+document.all('SubMissionID').value+"&PrtNo="+document.all('PrtNo').value;
         fm.submit();
    }
   
}

function displayDetail(){
	if(EdorMainGrid.canSel==1){
		//alert(1033);
 		//eval("document.all('EdorMainGridSel')[0].checked=true");
 		//document.all('EdorMainGridSel')[0].checked=true;
 		//alert(1035);
 		//eval("document.all('EdorMainGridSel')[0].value='1'");
 		//eval("")
 		//alert(1036);
 		//var tSelNo = PolAddGrid.getSelNo();
 		var index = 0;
 		showDetailInfo(index);
 	}
}

function returnUWApp(){
	//initForm();
    DivDetailInfo.style.display='none';
    //divContUWLable.style.display = 'none';
    //divEdorAppUWInfo.style.display = 'none';
    //divFormerContUWInfo.style.display = 'none';//ԭ�˱�����ͬʱ����    
    //��ѯ�˱������Ҫ��submissionid
/*    var strSql="select submissionid from lwmission where missionid='"+fm.MissionID.value+"'"					
					+" and activityid='0000000006'";
*/	
	var strSql="";
    var sqlid54="EdorAppManuUWInputSql54";
	var mySql54=new SqlClass();
	mySql54.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql54.setSqlId(sqlid54);//ָ��ʹ�õ�Sql��id
	mySql54.addSubPara(fm.MissionID.value);//ָ������Ĳ���
	strSql=mySql54.getString();
	
	var brr=easyExecSql(strSql, 1, 0,"","",1);//prompt("",strSql);
    if(brr)
    {
		fm.SubMissionID.value=brr[0][0];
	}
	//alert(fm.SubMissionID.value);
	fm.uwconfirm.disabled=true;
    fm.UWType.value="EdorApp"; 
    if(fm.EdorUWState.value == '1' ) 
    {
    	if(fm.AppUWState.value!='3')
    	{
    		alert("��ͬ�˱�����Ϊ���ܱ�������ȫ�˱�����ֻ���¡��ܱ�����");
	    	fm.uwconfirm.disabled=false;
	    	return;
    	}    	
    } 
    else
    {
    	if(fm.AppUWState.value=='3')
    	{
    		alert("��ͬ�˱����۲�Ϊ���ܱ�������ȫ�˱����۲����¡��ܱ�����");
	    	fm.uwconfirm.disabled=false;
	    	return;
    	} 
    } 
    UWSubmit();
}

/*********************************************************************
 *  ��ʼ����ť����
 *
 *********************************************************************
 */
function submitShow()
{
  
}

//���ƽ��水ť��������ʾ
function ctrlButtonDisabled(){
  var tContNo = fm.ContNo.value;
  var tEdorNo = fm.EdorNo.value;
  var tSQL = "";
  var arrResult;
  var arrButtonAndSQL = new Array;

  arrButtonAndSQL[0] = new Array;
  arrButtonAndSQL[0][0] = "uwButton9";
  arrButtonAndSQL[0][1] = "Ͷ���˽�����֪��ѯ";
/*  arrButtonAndSQL[0][2] = "select 1 from lccustomerimpart where customerno=(select appntno from lpcont where edorno='"+tEdorNo+"' and contno='"+tContNo+"') and contno='"+tContNo+"' and impartver in ('101','A01','B01','C01','D01') "
                  + "��union select 1 from lpcustomerimpart where customerno=(select appntno from lpcont where edorno='"+tEdorNo+"' and contno='"+tContNo+"') and edorno='"+tEdorNo+"' and contno='"+tContNo+"' and impartver in ('101','A01','B01','C01','D01')";
*/

    var sqlid55="EdorAppManuUWInputSql55";
	var mySql55=new SqlClass();
	mySql55.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql55.setSqlId(sqlid55);//ָ��ʹ�õ�Sql��id
	mySql55.addSubPara(tEdorNo);//ָ������Ĳ���
	mySql55.addSubPara(tContNo);
	mySql55.addSubPara(tContNo);
	mySql55.addSubPara(tEdorNo);
	mySql55.addSubPara(tContNo);
	mySql55.addSubPara(tEdorNo);
	mySql55.addSubPara(tContNo);
	arrButtonAndSQL[0][2]=mySql55.getString();

  arrButtonAndSQL[1] = new Array;
  arrButtonAndSQL[1][0] = "uwButton30";
  arrButtonAndSQL[1][1] = "Ͷ���˼���Ͷ�����ϲ�ѯ";
/*  arrButtonAndSQL[1][2] = "select 1 from lcpol where contno<> '"+tContNo+"' " 
			  + " and (appntno = (select appntno from lpcont where edorno='"+tEdorNo+"' and contno='"+tContNo+"') "
			  + " or insuredno = (select appntno from lpcont where edorno='"+tEdorNo+"' and contno='"+tContNo+"') ) "
			  + " and conttype='1' and appflag in ('1','4') "
			  + " union "
			  + " select 1 from lcpol where contno<> '"+tContNo+"' "
			  + " and (appntno = (select appntno from lpcont where edorno='"+tEdorNo+"' and contno='"+tContNo+"') "
			  + " or insuredno = (select appntno from lpcont where edorno='"+tEdorNo+"' and contno='"+tContNo+"') ) "
			  + " and conttype='1' and appflag ='0' "
			  + " union "
			  + " select 1 from lcpol where contno<> '"+tContNo+"' "
			  + " and (appntno = (select appntno from lpcont where edorno='"+tEdorNo+"' and contno='"+tContNo+"') "
			  + " or insuredno = (select appntno from lpcont where edorno='"+tEdorNo+"' and contno='"+tContNo+"') ) "
			  + " and conttype='2' ";
*/  
    var sqlid56="EdorAppManuUWInputSql56";
	var mySql56=new SqlClass();
	mySql56.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql56.setSqlId(sqlid56);//ָ��ʹ�õ�Sql��id
	mySql56.addSubPara(tContNo);//ָ������Ĳ���
	mySql56.addSubPara(tEdorNo);
	mySql56.addSubPara(tContNo);
	mySql56.addSubPara(tEdorNo);
	mySql56.addSubPara(tContNo);
	mySql56.addSubPara(tContNo);//ָ������Ĳ���
	mySql56.addSubPara(tEdorNo);
	mySql56.addSubPara(tContNo);
	mySql56.addSubPara(tEdorNo);
	mySql56.addSubPara(tContNo);
	mySql56.addSubPara(tContNo);//ָ������Ĳ���
	mySql56.addSubPara(tEdorNo);
	mySql56.addSubPara(tContNo);
	mySql56.addSubPara(tEdorNo);
	mySql56.addSubPara(tContNo);
	arrButtonAndSQL[1][2]=mySql56.getString();
  
  arrButtonAndSQL[2] = new Array;
  arrButtonAndSQL[2][0] = "uwButton21";
  arrButtonAndSQL[2][1] = "��ѯ�����";
//  arrButtonAndSQL[2][2] = "select 1 from lppenotice where 1 = 1 and edorno='"+tEdorNo+"' and contno='"+tContNo+"' and printflag is not null ";

	var sqlid57="EdorAppManuUWInputSql57";
	var mySql57=new SqlClass();
	mySql57.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql57.setSqlId(sqlid57);//ָ��ʹ�õ�Sql��id
	mySql57.addSubPara(tEdorNo);//ָ������Ĳ���
	mySql57.addSubPara(tContNo);
	arrButtonAndSQL[2][2]=mySql57.getString();

  arrButtonAndSQL[3] = new Array;
  arrButtonAndSQL[3][0] = "uwButton22";
  arrButtonAndSQL[3][1] = "��ѯ�������";
//  arrButtonAndSQL[3][2] = "select 1 from lprreport where 1 = 1 and edorno='"+tEdorNo+"' and contno='"+tContNo+"' and replyflag is not null ";
    
    var sqlid58="EdorAppManuUWInputSql58";
	var mySql58=new SqlClass();
	mySql58.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql58.setSqlId(sqlid58);//ָ��ʹ�õ�Sql��id
	mySql58.addSubPara(tEdorNo);//ָ������Ĳ���
	mySql58.addSubPara(tContNo);
	arrButtonAndSQL[3][2]=mySql58.getString();
                  
  arrButtonAndSQL[4] = new Array;
  arrButtonAndSQL[4][0] = "uwButtonIs";
  arrButtonAndSQL[4][1] = "��ѯ�����";
//  arrButtonAndSQL[4][2] = "select 1 from lpissuepol where edorno='"+tEdorNo+"' and contno='"+tContNo+"' ";
  
    var sqlid59="EdorAppManuUWInputSql62";
	var mySql59=new SqlClass();
	mySql59.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql59.setSqlId(sqlid59);//ָ��ʹ�õ�Sql��id
	mySql59.addSubPara(tEdorNo);//ָ������Ĳ���
	mySql59.addSubPara(tContNo);
	arrButtonAndSQL[4][2]=mySql59.getString();
	
	//�ж϶��˰�ť
	arrButtonAndSQL[5] = new Array;
  arrButtonAndSQL[5][0] = "uwButtonTe";
  arrButtonAndSQL[5][1] = "���˽����ѯ";
  
    var sqlid60="EdorAppManuUWInputSql61";
	var mySql60=new SqlClass();
	mySql60.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql60.setSqlId(sqlid60);//ָ��ʹ�õ�Sql��id
	mySql60.addSubPara(tEdorNo);//ָ������Ĳ���
	mySql60.addSubPara(tContNo);
	arrButtonAndSQL[5][2]=mySql60.getString();
	
	//var sqlid60= "select 1 from Lpcuwmaster a where a.Edorno = '"+tEdorNo+"' and a.Contno ='"+tContNo+"'"; 
	//var mySql60=new SqlClass();
	//var arrJudge = easyExecSql(mySql60.getString());
	//alert(arrJudge +"--");
	//if(arrJudge==null){
    //	fm.uwButtonTe.disabled = "true";
   // }else{
    	//fm.uwButtonTe.disabled = "";
   // }
	
  for(var i=0; i<arrButtonAndSQL.length; i++){
    if(arrButtonAndSQL[i][2]!=null&&arrButtonAndSQL[i][2]!=""){
    	//alert(arrButtonAndSQL[i][1]+":"+arrButtonAndSQL[i][2]);
      arrResult = easyExecSql(arrButtonAndSQL[i][2]);
      if(arrResult!=null){
        eval("document.all('"+arrButtonAndSQL[i][0]+"').disabled=''");
      }
      else{
        eval("document.all('"+arrButtonAndSQL[i][0]+"').disabled='true'");
      }
    }
    else{
      continue;
    }
  }
	
}

//������֪ͨ���ѯ
function checkNotice(){
	var tContNo = fm.ContNo.value;
	var tEdorNo = fm.EdorNo.value;
	var tEdorType = fm.EdorType.value;
	
//	var questionSql = " select 'y' from lpissuepol a where contno='"+tContNo+"' "
//	       + " and edorno='"+tEdorNo+"' "
//	       + " and (state is null or state='')"
//	       + " and backobjtype ='4' and needprint='Y' "
//	       + " union "
//	       + " select 'y' from lpissuepol a where contno='"+tContNo+"' "
//	       + " and edorno='"+tEdorNo+"' "
//	       + " and (state is null or state='')"
//	       + " and backobjtype ='2' and needprint='Y'  ";
	
	var sqlid64="EdorAppManuUWInputSql64";
	var mySql64=new SqlClass();
	mySql64.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql64.setSqlId(sqlid64);//ָ��ʹ�õ�Sql��id
	mySql64.addSubPara(tContNo);//ָ������Ĳ���
	mySql64.addSubPara(tEdorNo);
	mySql64.addSubPara(tContNo);
	mySql64.addSubPara(tEdorNo);
	var questionSql=mySql64.getString();
	
	var questionArr = easyExecSql(questionSql);
	
//	var penoticeSql = "select 'y' from LPPENotice where edorno='"+tEdorNo+"' and contno = '"+tContNo+"' and (printflag is null or printflag='')";
	
	var sqlid65="EdorAppManuUWInputSql65";
	var mySql65=new SqlClass();
	mySql65.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql65.setSqlId(sqlid65);//ָ��ʹ�õ�Sql��id
	mySql65.addSubPara(tEdorNo);//ָ������Ĳ���
	mySql65.addSubPara(tContNo);
	var penoticeSql=mySql65.getString();
	
	var penoticeArr = easyExecSql(penoticeSql);
	
//	var reportSql = "select 'y' from LPRReport where edorno='"+tEdorNo+"' and contno = '"+tContNo+"' and (replyflag is null or replyflag='')";
	
	var sqlid66="EdorAppManuUWInputSql66";
	var mySql66=new SqlClass();
	mySql66.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql66.setSqlId(sqlid66);//ָ��ʹ�õ�Sql��id
	mySql66.addSubPara(tEdorNo);//ָ������Ĳ���
	mySql66.addSubPara(tContNo);
	var reportSql=mySql66.getString();
	
	var reportArr = easyExecSql(reportSql);

//	var specSql = "select 'y' from lpcspec where contno = '"+tContNo+"' and (prtflag is null or prtflag='') and needprint='Y'";
	
	var sqlid67="EdorAppManuUWInputSql67";
	var mySql67=new SqlClass();
	mySql67.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql67.setSqlId(sqlid67);//ָ��ʹ�õ�Sql��id
	mySql67.addSubPara(tContNo);//ָ������Ĳ���
	var specSql=mySql67.getString();
	
	var specArr = easyExecSql(specSql);
	
//	var uwSql = "select 'y' from LPCUWMaster where contno = '"+tContNo+"' and EdorNo = '"+ tEdorNo +"' and EdorType='"+tEdorType+"' and (SugPassFlag is null or SugPassFlag='') and exists (select 1 from lpprem where contno='" + tContNo + "' and payplancode like '000000%%')";
	
	var sqlid68="EdorAppManuUWInputSql68";
	var mySql68=new SqlClass();
	mySql68.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql68.setSqlId(sqlid68);//ָ��ʹ�õ�Sql��id
	mySql68.addSubPara(tContNo);//ָ������Ĳ���
	mySql68.addSubPara(tEdorNo);
	mySql68.addSubPara(tEdorType);
	mySql68.addSubPara(tContNo);
	var uwSql=mySql68.getString();
	
	var uwArr = easyExecSql(uwSql);
	
	if((questionArr!=null&&questionArr.length>=0)||(penoticeArr!=null&&penoticeArr.length>=0)||(reportArr!=null&&reportArr.length>=0)||(specArr!=null&&specArr.length>=0)||(uwArr!=null&&uwArr.length>=0)){
		return true;
	}
	return false;
}
