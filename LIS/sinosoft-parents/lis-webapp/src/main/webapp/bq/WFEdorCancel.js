//�������ƣ�WFEdorCancel.js
//�����ܣ���ȫ������-��ȫ����
//�������ڣ�2005-04-30 11:49:22
//������  ��zhangtao
//���¼�¼��  ������    ��������     ����ԭ��/����
//

var turnPage = new turnPageClass();
var turnPageAllGrid = new turnPageClass();
var divTurnPageSelfGrid = new turnPageClass();
var tCustomerEdorFlag = 0;
var tFirstEdorAcceptNo="";
var sqlresourcename = "bq.WFEdorCancelInputSql";

/**
 * �ύ�����, ���������ݷ��غ�ִ�еĲ���
 */
function afterSubmit(DealFlag, MsgContent)
{
    try { showInfo.close(); } catch (ex) {}
    DealFlag = DealFlag.toLowerCase();
    var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=";
    switch (DealFlag)
    {
        case "fail":
            MsgPageURL = MsgPageURL + "F&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=250px");
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
        case "succ":
        case "success":
            //MsgPageURL = MsgPageURL + "S&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=350px");
            break;
        default:
            MsgPageURL = MsgPageURL + "C&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=300;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
    }
    //���ļ������⴦��
    if (DealFlag == "succ" || DealFlag == "success")
    {
        try
        {
            //queryAllGrid();
            //querySelfGrid();
            jQuery("#publicSearch").click();
            jQuery("#privateSearch").click();
        }
        catch (ex) {}
    }
}
function jquerySelfGrid(){
jQuery("#privateSearch").click();
}
/*********************************************************************
 *  ��ѯ�����������
 *  ����:��ѯ�����������
 *********************************************************************
 */
function queryAllGrid()
{
    // ��дSQL���
    var strSQL = "";
/*    strSQL =  " select missionprop1, missionprop2, "
            + " (select codename from ldcode d1 where trim(d1.codetype) = 'edornotype' and trim(d1.code) = trim(missionprop3) ), "
            + " (select appntname from lccont where contno=a.missionprop2), "
            + " (select codename from ldcode d2 where trim(d2.codetype) = 'edorapptype' and trim(d2.code) = trim(missionprop5) ), "
            //+ " (select codename from ldcode d3 where trim(d3.codetype) = 'station' and trim(d3.code) = trim(missionprop7) ), "
            + " missionprop7, "
            + " createoperator, makedate, missionid, submissionid, activityid, "
            + " (select nvl(edorappdate,'') from lpedoritem where edoracceptno = a.missionprop1 and rownum = 1),(select count(*) from ldcalendar where commondate > (select edorappdate from lpedoritem where edoracceptno = a.missionprop1  and rownum = 1) and commondate <= '"+curDay+"' and workdateflag = 'Y') "
            + " from lwmission a where 1=1  "
            + " and activityid = '0000000008' "  //��������ȫ-��ȫ����
            + " and processid = '0000000000' "
            + " and defaultoperator is null "
            + getWherePart('missionprop1', 'EdorAcceptNo')
            + getWherePart('missionprop2', 'OtherNo')
            + getWherePart('missionprop3', 'OtherNoType')
            + getWherePart('missionprop4', 'EdorAppName')
            + getWherePart('missionprop5', 'AppType')
            + getWherePart('missionprop7', 'ManageCom')
            + getWherePart('MakeDate', 'MakeDate')
            + " and missionprop7 like '" + manageCom + "%%' "
            + " and createoperator = '" + operator + "' "
            //XinYQ added on 2007-05-17 : ������ֹ �˱���ֹ ������ֹ �ı�ȫ����ʾ
            + " and not exists (select 'X' from LPEdorApp where 1 = 1 and EdorAcceptNo = a.MissionProp1 and EdorState in ('4', '8', '9'))"
            + " order by (select 1 from lpedorapp where EdorAcceptNo = EdorAcceptNo), (select edorappdate from lpedoritem where EdorAcceptNo = a.missionprop1  and rownum = 1),makedate,maketime";
*/    
    var sqlid1="WFEdorCancelInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(curDay);//ָ������Ĳ���
	mySql1.addSubPara(window.document.getElementsByName(trim('EdorAcceptNo'))[0].value);
	mySql1.addSubPara(window.document.getElementsByName(trim('OtherNo'))[0].value);
	mySql1.addSubPara(window.document.getElementsByName(trim('OtherNoType'))[0].value);
	mySql1.addSubPara(window.document.getElementsByName(trim('EdorAppName'))[0].value);
	mySql1.addSubPara(window.document.getElementsByName(trim('AppType'))[0].value);//ָ������Ĳ���
	mySql1.addSubPara(window.document.getElementsByName(trim('ManageCom'))[0].value);
	mySql1.addSubPara(window.document.getElementsByName(trim('MakeDate'))[0].value);
	mySql1.addSubPara(manageCom);
	mySql1.addSubPara(operator);
	strSQL=mySql1.getString();
    
    turnPageAllGrid.pageDivName = "divTurnPageAllGrid";
    turnPageAllGrid.queryModal(strSQL, AllGrid);
    HighlightAllRow();
}

/*********************************************************************
 *  ��ѯ�ҵ��������
 *  ����: ��ѯ�ҵ��������
 *********************************************************************
 */
function querySelfGrid()
{
    // ��дSQL���
    var strSQL = "";
/*    strSQL =  " select missionprop1, missionprop2, "
            + " (select codename from ldcode d1 where trim(d1.codetype) = 'edornotype' and trim(d1.code) = trim(missionprop3) ), "
            + " (select appntname from lccont where contno=a.missionprop2), "
            + " (select codename from ldcode d2 where trim(d2.codetype) = 'edorapptype' and trim(d2.code) = trim(missionprop5) ), "
            //+ " (select codename from ldcode d3 where trim(d3.codetype) = 'station' and trim(d3.code) = trim(missionprop7) ), "
            + " missionprop7, "
            + " createoperator, makedate, missionid, submissionid, activityid, "
            + " (select nvl(edorappdate,'') from lpedoritem where edoracceptno = a.missionprop1 and rownum = 1),(select count(*) from ldcalendar where commondate > (select edorappdate from lpedoritem where edoracceptno = a.missionprop1 and rownum = 1) and commondate <= '"+curDay+"' and workdateflag = 'Y') "
            + " from lwmission a where 1=1  "
            + " and activityid = '0000000008' "  //��������ȫ-��ȫ����
            + " and processid = '0000000000' "
            + " and defaultoperator ='" + operator + "'"
            //����У�飬�ͻ��㱣ȫ��������ʾԭʼ����ż�¼
            + " and (exists( select 1 from lpedorapp b where b.edoracceptno=a.missionprop1  and b.othernotype='1' "
            + " and (exists(select 1 from lpconttempinfo c where  c.icedoracceptno=a.missionprop1 and c.edoracceptno=c.icedoracceptno) "
            + "      or not exists(select 1 from lpconttempinfo c where  c.icedoracceptno=a.missionprop1)))"
            + " or exists (select 1 from lpedorapp b where  b.edoracceptno=a.missionprop1 and b.othernotype='3' ))"
            + " order by (select 1 from lpedorapp where EdorAcceptNo = a.missionprop1), (select edorappdate from lpedoritem where EdorAcceptNo = a.missionprop1 and rownum = 1),makedate,maketime";
*/    
    var sqlid2="WFEdorCancelInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(curDay);//ָ������Ĳ���
	mySql2.addSubPara(operator);
	strSQL=mySql2.getString();
    
    divTurnPageSelfGrid.pageDivName = "divTurnPageSelfGrid";
    divTurnPageSelfGrid.queryModal(strSQL, SelfGrid);
    HighlightSelfRow();
}
function HighlightAllRow(){
		for(var i=0; i<PublicWorkPoolGrid.mulLineCount; i++){
  	var days = PublicWorkPoolGrid.getRowColData(i,13);  	
  	if(days != null && days != ""){	
    	var ret = Arithmetic(days,'-','5','2');
    	if(ret>=0)
    	{
    		PublicWorkPoolGrid.setRowClass(i,'warn');
    	}
    
  	}
  }
}
/*
function HighlightAllRow(){
		for(var i=0; i<AllGrid.mulLineCount; i++){
  	var days = AllGrid.getRowColData(i,13);  	
  	if(days != null && days != ""){	
    	var ret = Arithmetic(days,'-','5','2');
    	if(ret>=0)
    	{
    		AllGrid.setRowClass(i,'warn');
    	}
    
  	}
  }
}
*/
function HighlightSelfRow(){
		for(var i=0; i<PrivateWorkPoolGrid.mulLineCount; i++){
  	var days = PrivateWorkPoolGrid.getRowColData(i,13);  	
  	if(days != null && days != ""){	
    	var ret = Arithmetic(days,'-','5','2');
    	if(ret>=0)
    	{
    		PrivateWorkPoolGrid.setRowClass(i,'warn');
    	}
    
  	}
  }
}
/*
function HighlightSelfRow(){
		for(var i=0; i<SelfGrid.mulLineCount; i++){
  	var days = SelfGrid.getRowColData(i,13);  	
  	if(days != null && days != ""){	
    	var ret = Arithmetic(days,'-','5','2');
    	if(ret>=0)
    	{
    		SelfGrid.setRowClass(i,'warn');
    	}
    
  	}
  }
}

/*********************************************************************
 *  ��ת�������ҵ����ҳ��
 *  ����: ��ת�������ҵ����ҳ��
 *********************************************************************
 */
function GoToBusiDeal()
{
    var selno = PrivateWorkPoolGrid.getSelNo()-1;
    if (selno <0)
    {
          alert("��ѡ��Ҫ���������");
          return;
    }
    var tEdorAcceptNo = PrivateWorkPoolGrid.getRowColData(selno, 1);
    var tContNo = PrivateWorkPoolGrid.getRowColData(selno, 2);
    var tMissionID = PrivateWorkPoolGrid.getRowColData(selno, 19);
    var tSubMissionID = PrivateWorkPoolGrid.getRowColData(selno, 20);
    var tActivityID = PrivateWorkPoolGrid.getRowColData(selno, 22);
    
        //����Ѿ����б�ȫ�����������������б�ȫ����
    var strSQL = "";
//    var strSQL = "select prtseq from loprtmanager where code in ('BQ37') and Stateflag !='2' and otherno = '"+tContNo+"' and standbyflag1='"+tEdorAcceptNo+"'";
    var sResult;
    
    var sqlid3="WFEdorCancelInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
	mySql3.addSubPara(tContNo);//ָ������Ĳ���
	mySql3.addSubPara(tEdorAcceptNo);
	strSQL=mySql3.getString();
    
    sResult = easyExecSql(strSQL);
    if(sResult !=null){
        alert("������δ�����ı�ȫ���֪ͨ�飬��������б�ȫ����");
        return;
    }
    if(!CustomerBQ_Check2(tEdorAcceptNo))
    {
      alert("�жϵ�ǰ��ȫ�Ƿ����ڿͻ��㱣ȫ��Ŀʱʧ�ܣ�");
      return false;
    }
    if(tCustomerEdorFlag>0) //�ͻ��㱣ȫ
    {   
        //�ж��Ƿ��к˱�����Ϊ�ܱ��ģ���������
        var strUwSQL="";
/*        var strUwSQL=" select count(*) from LPAppUWMasterMain a"
	                +" where a.EdorAcceptNo in ( select b.icedoracceptno from lpconttempinfo b where b.edoracceptno='"+tFirstEdorAcceptNo+"' union select '"+tFirstEdorAcceptNo+"' from dual)"
	                +" and a.state='3' "; 
*/	    
	    var checkcount=0;
	    
	    var sqlid4="WFEdorCancelInputSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
		mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
		mySql4.addSubPara(tFirstEdorAcceptNo);//ָ������Ĳ���
		mySql4.addSubPara(tFirstEdorAcceptNo);
		strUwSQL=mySql4.getString();
	    
	    checkcount = easyExecSql(strUwSQL, 1, 1, 1); 
	    if(checkcount>0)
	    {
	        alert("��ǰ��ȫΪ�ͻ��㱣ȫ��Ҵ��ڱ�����ȫ�˱�����Ϊ�ܱ�����������б�ȫ����");
	        return;
	    }  
	    //�ж��Ƿ���������������ȫȷ�ϣ��еĻ���������
        var strUwSQL2="";
/*        var strUwSQL2=" select count(*) from lpedoritem a"
            +" where a.EdorAcceptNo in ( select b.icedoracceptno from lpconttempinfo b where b.edoracceptno='"+tFirstEdorAcceptNo+"' union select '"+tFirstEdorAcceptNo+"' from dual)"
            +" and a.edorstate='0' "; 
*/	    
	    var sqlid5="WFEdorCancelInputSql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
		mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
		mySql5.addSubPara(tFirstEdorAcceptNo);//ָ������Ĳ���
		mySql5.addSubPara(tFirstEdorAcceptNo);
		strUwSQL2=mySql5.getString();
	    
	    var checkcount2=0;
	    checkcount2 = easyExecSql(strUwSQL2, 1, 1, 1); 
	    if(checkcount2>0)
	    {
	        alert("��ǰ��ȫΪ�ͻ��㱣ȫ��Ҵ��ڱ����Ѿ����б���ȷ�ϲ�������������б�ȫ����");
	        return;
	    }  
    }
    else //��ͨ��ȫ
    {
	    var strUwSQL="";
/*	    var strUwSQL=" select State, "
	                +" (select codename "
	                +" from ldcode "
	                +" where '1242813742000' = '1242813742000' "
	                +" and codetype = 'edorappstate'"
	                +" and code = state), "
	                +" UWIdea "
	                +" from LPAppUWMasterMain "
	                +" where EdorAcceptNo ='"+tEdorAcceptNo+"'"; 
*/	    
	    var sqlid6="WFEdorCancelInputSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
		mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
		mySql6.addSubPara(tEdorAcceptNo);//ָ������Ĳ���
		strUwSQL=mySql6.getString();
	    
	    var sUWResult;
	    sUWResult = easyExecSql(strUwSQL); 
	    //alert(sUWResult);
	    if(sUWResult!=null&&sUWResult[0][0]=='3'){
	        alert("������ȫ�˱�����Ϊ�ܱ�����������б�ȫ����");
	        return;
	    }      
    }     
    
    var varSrc="&EdorAcceptNo=" + tEdorAcceptNo + "&ActivityID=" + tActivityID + "&MissionID=" + tMissionID + "&SubMissionID=" + tSubMissionID;
    //alert(tEdorAcceptNo);
    var newWindow = OpenWindowNew("../bq/EdorCancelFrame.jsp?Interface= ../bq/EdorCancelInput.jsp" + varSrc,"","left");
}

/*********************************************************************
 *  ��������
 *  ����: ��������
 *********************************************************************
 */
 function applyMission()
{
    var selno = PublicWorkPoolGrid.getSelNo()-1;
    if (selno <0)
    {
          alert("��ѡ��Ҫ���������");
          return;
    }
   // alert(PublicWorkPoolGrid.getRowData(selno));
    fm.MissionID.value = PublicWorkPoolGrid.getRowColData(selno, 19);
    fm.SubMissionID.value = PublicWorkPoolGrid.getRowColData(selno, 20);
    fm.ActivityID.value = PublicWorkPoolGrid.getRowColData(selno, 22);
    if (fm.MissionID.value == null || fm.MissionID.value == "")
    {
          alert("ѡ������Ϊ�գ�");
          return;
    }
    var tEdorAcceptNo = PublicWorkPoolGrid.getRowColData(selno, 1);
    //���ӿͻ���У�飬����ǿͻ��㱣ȫ������Ų�ֺ�����������񶼽��й��Զ��˱�����������˹��˱���
    if(!CustomerBQ_Check1(tEdorAcceptNo))
    {
      return;
    }
    var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action = "../bq/MissionApply.jsp";
    document.getElementById("fm").submit(); //�ύ
}
//У��ͻ��㱣ȫ�Ƿ��д����˹��˱�������������У����������볷��
function CustomerBQ_Check1( xEdorAcceptNo )
{
   var sql1="";
//   var sql1="select count(*) from lpedorapp where EdorAcceptNo='"+xEdorAcceptNo+"' and othernotype='1' ";
   
   		var sqlid7="WFEdorCancelInputSql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
		mySql7.setSqlId(sqlid7);//ָ��ʹ�õ�Sql��id
		mySql7.addSubPara(xEdorAcceptNo);//ָ������Ĳ���
		sql1=mySql7.getString();
   
   var CustomerBQ_flag= 0;
   CustomerBQ_flag= easyExecSql(sql1, 1, 1, 1); 
   if(CustomerBQ_flag>0) //˵���ǿͻ��㱣ȫ
   {
       //�жϵ�ǰ������Ƿ���ԭʼ����ţ��ͻ��㱣ȫֻ������ԭʼ����Ž��г���
        var checkcount2=0;
	    var sql2="";
//	    var sql2=" select count(*) from lpconttempinfo where icedoracceptno='"+xEdorAcceptNo+"' ";
	    
	    var sqlid8="WFEdorCancelInputSql8";
		var mySql8=new SqlClass();
		mySql8.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
		mySql8.setSqlId(sqlid8);//ָ��ʹ�õ�Sql��id
		mySql8.addSubPara(xEdorAcceptNo);//ָ������Ĳ���
		sql2=mySql8.getString();
	    
	    checkcount2= easyExecSql(sql2, 1, 1, 1); 
	    if(checkcount2>0)
	    {//˵������������Ų�֣��жϵ�ǰ������Ƿ���ԭ�������
	      var sql3="";
//	      var sql3=" select distinct(edoracceptno) from lpconttempinfo where icedoracceptno='"+xEdorAcceptNo+"'  ";
	      
	      var sqlid9="WFEdorCancelInputSql9";
		  var mySql9=new SqlClass();
		  mySql9.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
		  mySql9.setSqlId(sqlid9);//ָ��ʹ�õ�Sql��id
		  mySql9.addSubPara(xEdorAcceptNo);//ָ������Ĳ���
		  sql3=mySql9.getString();
	      
	      tFirstEdorAcceptNo= easyExecSql(sql3, 1, 1, 1); 
	      if(tFirstEdorAcceptNo!=xEdorAcceptNo)
	      {
	         alert("��ǰ��ȫΪ�ͻ��㱣ȫ�ֻ����ԭʼ����ţ�"+tFirstEdorAcceptNo+"�����б�ȫ����");
	         return;
	      }
	    }
	    else  //��û������Ų�֣���ǰ����ž���ԭʼ�����
	    {
	       tFirstEdorAcceptNo=xEdorAcceptNo;
	    }
	    
     var sql4="";
/*     var sql4="select count(*) from lwmission where defaultoperator is not null "
      +" and activityid in ('0000000005','0000000007') and missionprop1 in "
      +" (select a.icedoracceptno from lpconttempinfo a where a.edoracceptno='"+tFirstEdorAcceptNo+"')";
*/     
     
     	  var sqlid10="WFEdorCancelInputSql10";
		  var mySql10=new SqlClass();
		  mySql10.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
		  mySql10.setSqlId(sqlid10);//ָ��ʹ�õ�Sql��id
		  mySql10.addSubPara(tFirstEdorAcceptNo);//ָ������Ĳ���
		  sql4=mySql10.getString();
     
     var all_flag= 0;
     all_flag= easyExecSql(sql4, 1, 1, 1); 
     if(all_flag>0)
     {
        alert("�˱�ȫΪ�ͻ��㱣ȫ��Ŀǰ�������������˹��˱�״̬���޷�������");
        return false;
     }
   }
   return true;
}
function CustomerBQ_Check2( xEdorAcceptNo )
{
   var sql1="";
//   var sql1="select count(*) from lpedorapp where EdorAcceptNo='"+xEdorAcceptNo+"' and othernotype='1' ";
   
   		  var sqlid11="WFEdorCancelInputSql11";
		  var mySql11=new SqlClass();
		  mySql11.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
		  mySql11.setSqlId(sqlid11);//ָ��ʹ�õ�Sql��id
		  mySql11.addSubPara(xEdorAcceptNo);//ָ������Ĳ���
		  sql1=mySql11.getString();
   
   var CustomerBQ_flag= 0;
   CustomerBQ_flag= easyExecSql(sql1, 1, 1, 1); 
   tCustomerEdorFlag =CustomerBQ_flag;
   return true;
}
 
 /*
function applyMission()
{
    var selno = AllGrid.getSelNo()-1;
    if (selno <0)
    {
          alert("��ѡ��Ҫ���������");
          return;
    }
    fm.MissionID.value = AllGrid.getRowColData(selno, 9);
    fm.SubMissionID.value = AllGrid.getRowColData(selno, 10);
    fm.ActivityID.value = AllGrid.getRowColData(selno, 11);
    if (fm.MissionID.value == null || fm.MissionID.value == "")
    {
          alert("ѡ������Ϊ�գ�");
          return;
    }
    var tEdorAcceptNo = AllGrid.getRowColData(selno, 1);
    //���ӿͻ���У�飬����ǿͻ��㱣ȫ������Ų�ֺ�����������񶼽��й��Զ��˱�����������˹��˱���
    if(!CustomerBQ_Check1(tEdorAcceptNo))
    {
      return;
    }
    var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    fm.action = "../bq/MissionApply.jsp";
    document.getElementById("fm").submit(); //�ύ
}
//У��ͻ��㱣ȫ�Ƿ��д����˹��˱�������������У����������볷��
function CustomerBQ_Check1( xEdorAcceptNo )
{
   var sql1="";
//   var sql1="select count(*) from lpedorapp where EdorAcceptNo='"+xEdorAcceptNo+"' and othernotype='1' ";
   
   		var sqlid7="WFEdorCancelInputSql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
		mySql7.setSqlId(sqlid7);//ָ��ʹ�õ�Sql��id
		mySql7.addSubPara(xEdorAcceptNo);//ָ������Ĳ���
		sql1=mySql7.getString();
   
   var CustomerBQ_flag= 0;
   CustomerBQ_flag= easyExecSql(sql1, 1, 1, 1); 
   if(CustomerBQ_flag>0) //˵���ǿͻ��㱣ȫ
   {
       //�жϵ�ǰ������Ƿ���ԭʼ����ţ��ͻ��㱣ȫֻ������ԭʼ����Ž��г���
        var checkcount2=0;
	    var sql2="";
//	    var sql2=" select count(*) from lpconttempinfo where icedoracceptno='"+xEdorAcceptNo+"' ";
	    
	    var sqlid8="WFEdorCancelInputSql8";
		var mySql8=new SqlClass();
		mySql8.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
		mySql8.setSqlId(sqlid8);//ָ��ʹ�õ�Sql��id
		mySql8.addSubPara(xEdorAcceptNo);//ָ������Ĳ���
		sql2=mySql8.getString();
	    
	    checkcount2= easyExecSql(sql2, 1, 1, 1); 
	    if(checkcount2>0)
	    {//˵������������Ų�֣��жϵ�ǰ������Ƿ���ԭ�������
	      var sql3="";
//	      var sql3=" select distinct(edoracceptno) from lpconttempinfo where icedoracceptno='"+xEdorAcceptNo+"'  ";
	      
	      var sqlid9="WFEdorCancelInputSql9";
		  var mySql9=new SqlClass();
		  mySql9.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
		  mySql9.setSqlId(sqlid9);//ָ��ʹ�õ�Sql��id
		  mySql9.addSubPara(xEdorAcceptNo);//ָ������Ĳ���
		  sql3=mySql9.getString();
	      
	      tFirstEdorAcceptNo= easyExecSql(sql3, 1, 1, 1); 
	      if(tFirstEdorAcceptNo!=xEdorAcceptNo)
	      {
	         alert("��ǰ��ȫΪ�ͻ��㱣ȫ�ֻ����ԭʼ����ţ�"+tFirstEdorAcceptNo+"�����б�ȫ����");
	         return;
	      }
	    }
	    else  //��û������Ų�֣���ǰ����ž���ԭʼ�����
	    {
	       tFirstEdorAcceptNo=xEdorAcceptNo;
	    }
	    
     var sql4="";
/*     var sql4="select count(*) from lwmission where defaultoperator is not null "
      +" and activityid in ('0000000005','0000000007') and missionprop1 in "
      +" (select a.icedoracceptno from lpconttempinfo a where a.edoracceptno='"+tFirstEdorAcceptNo+"')";
*/     
     /*
     	  var sqlid10="WFEdorCancelInputSql10";
		  var mySql10=new SqlClass();
		  mySql10.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
		  mySql10.setSqlId(sqlid10);//ָ��ʹ�õ�Sql��id
		  mySql10.addSubPara(tFirstEdorAcceptNo);//ָ������Ĳ���
		  sql4=mySql10.getString();
     
     var all_flag= 0;
     all_flag= easyExecSql(sql4, 1, 1, 1); 
     if(all_flag>0)
     {
        alert("�˱�ȫΪ�ͻ��㱣ȫ��Ŀǰ�������������˹��˱�״̬���޷�������");
        return false;
     }
   }
   return true;
}

//У���Ƿ��ǿͻ��㱣ȫ
function CustomerBQ_Check2( xEdorAcceptNo )
{
   var sql1="";
//   var sql1="select count(*) from lpedorapp where EdorAcceptNo='"+xEdorAcceptNo+"' and othernotype='1' ";
   
   		  var sqlid11="WFEdorCancelInputSql11";
		  var mySql11=new SqlClass();
		  mySql11.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
		  mySql11.setSqlId(sqlid11);//ָ��ʹ�õ�Sql��id
		  mySql11.addSubPara(xEdorAcceptNo);//ָ������Ĳ���
		  sql1=mySql11.getString();
   
   var CustomerBQ_flag= 0;
   CustomerBQ_flag= easyExecSql(sql1, 1, 1, 1); 
   tCustomerEdorFlag =CustomerBQ_flag;
   return true;
}


/*********************************************************************
 *  �򿪹��������±��鿴ҳ��
 *  ����: �򿪹��������±��鿴ҳ��
 *********************************************************************
 */
 function showNotePad()
{
    var selno = PrivateWorkPoolGrid.getSelNo()-1;
    if (selno <0)
    {
          alert("��ѡ��Ҫ���������");
          return;
    }

    var MissionID = PrivateWorkPoolGrid.getRowColData(selno, 19);
    var SubMissionID = PrivateWorkPoolGrid.getRowColData(selno, 20);
    var ActivityID = PrivateWorkPoolGrid.getRowColData(selno, 22);
    var OtherNo = PrivateWorkPoolGrid.getRowColData(selno, 2);
    var OtherNoType = "1";
    if(MissionID == null || MissionID == "")
    {
        alert("MissionIDΪ�գ�");
        return;
    }
    var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
    var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface= ../uw/WorkFlowNotePadInput.jsp" + varSrc,"","left");
}
 /*
function showNotePad()
{
    var selno = SelfGrid.getSelNo()-1;
    if (selno <0)
    {
          alert("��ѡ��Ҫ���������");
          return;
    }

    var MissionID = SelfGrid.getRowColData(selno, 9);
    var SubMissionID = SelfGrid.getRowColData(selno, 10);
    var ActivityID = SelfGrid.getRowColData(selno, 11);
    var OtherNo = SelfGrid.getRowColData(selno, 1);
    var OtherNoType = "1";
    if(MissionID == null || MissionID == "")
    {
        alert("MissionIDΪ�գ�");
        return;
    }
    var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
    var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface= ../uw/WorkFlowNotePadInput.jsp" + varSrc,"","left");
}
*/