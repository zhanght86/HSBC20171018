//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug = "0";
var turnPage = new turnPageClass();

//�ύ�����水ť��Ӧ����
function submitForm()
{
    var i = 0;

    var selno = PolGrid.getSelNo() - 1;
    if (selno < 0)
    {
        alert("��ѡ��Ҫ������Ͷ������");
        return;
    }

    fm.ContNoH.value = PolGrid.getRowColData(selno, 1);
    fm.PrtNo.value = PolGrid.getRowColData(selno, 2);
    if (fm.PrtNo.value == null || fm.PrtNo.value == "")
    {
        alert("ӡˢ��Ϊ�գ�");
        return;
    }
    if (fm.Content.value == null || fm.Content.value == "")
    {
        alert("�����볷��˵����");
        return;
    }
    //�ж�֧Ʊ�Ƿ���----add by haopan --2007-2-1
//    var strSQL="select * from ljtempfeeclass where paymode='3' and otherno='"+PolGrid.getRowColData(selno, 1)+"'  and enteraccdate is null";
   
     var rowColData1 = PolGrid.getRowColData(selno, 1);
     var sqlid1="ApplyRecallSql1";
	 var mySql1=new SqlClass();
	 mySql1.setResourceName("uw.ApplyRecallSql");
	 mySql1.setSqlId(sqlid1);//ָ��ʹ��SQL��id
	 mySql1.addSubPara(rowColData1);//ָ���������
	 var strSQL = mySql1.getString();
    
    var arrResult = easyExecSql(strSQL);
		if (arrResult!=null) 
		{
			alert("֧Ʊ��δ���ʣ���������г�������");
			return;
		}
		
    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    lockScreen('lkscreen');  
    fm.submit();
    //�ύ
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit(FlagStr, content)
{
	unlockScreen('lkscreen');  
    showInfo.close();
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    if (FlagStr == 'Succ')
    {
        easyQueryClick();
    }
}


//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
    if (cDebug == "1")
    {
        parent.fraMain.rows = "0,0,50,82,*";
    }
    else
    {
        parent.fraMain.rows = "0,0,0,82,*";
    }
}


//��ʾdiv����һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
function showDiv(cDiv, cShow)
{
    if (cShow == "true")
    {
        cDiv.style.display = "";
    }
    else
    {
        cDiv.style.display = "none";
    }
}

function queryPol()
{
    //���ò�ѯͶ�������ļ�
    window.open("./QuestionAdditionalRiskQuery.html");
}

function easyQueryClick()
{
    // ��ʼ�����
    initPolGrid();

    Array
    tArray = new Array();
    tArray[0] = "ContNo";
    tArray[1] = "PrtNoSearch";
    tArray[2] = "ManageCom";
    tArray[3] = "AppntName";
    tArray[4] = "InsuredName";
    tArray[5] = "InsuredSex";
    tArray[6] = "UWStateQuery";
    tArray[7] = "AgentCode";
    tArray[8] = "AgentGroup";
    var j = 0;
    for (var i = 0; i < tArray.length; i++) {
        if (eval("fm." + tArray[i] + ".value") == "") {
            j++;
        }
    }
    if (j == tArray.length) {
        alert("�����������⣬����������һ����ѯ������");
        return;
    }

    // ��дSQL���
    var tContType = fm.ContType.value;
    fm.PolType.value = tContType;

    if (tContType == "1")
    {
//        var strSql = " select l.ContNo,l.PrtNo,l.AppntName,l.Operator,l.MakeDate,l.ApproveCode,l.ApproveDate,l.UWOperator,l.UWDate,l.UWFlag,l.State,"
//        		+ " (select (case when sum(paymoney) is not null then sum(paymoney) else 0 end) from ljtempfee where otherno = l.contno and (enteraccdate is not null and enteraccdate<>'3000-01-01') and confdate is null),"
//        		+ " (case when exists(select 'x' from lcpenotice where contno = l.contno) then '��' else '��' end ) "
//        		+ " from LCCont l where 1=1 "
//                + " and grpcontno='00000000000000000000' and appflag = '0'"
//                + " and (((uwflag <> 'a') and (uwflag <> '1') and (uwflag <> '2'))"
//                + " or (uwflag is null))"
//                + " and (("
//                + " (substr(state, 1, 4)) <> '1002' and"
//                + " (substr(state, 1, 4)) <> '1003') or state is null) "
//                + getWherePart('l.ContNo', 'ContNo')
//                + getWherePart('l.PrtNo', 'PrtNoSearch')
//                + getWherePart('l.ManageCom', 'ManageCom', 'like')
//                + getWherePart('l.AppntName', 'AppntName')
//                + getWherePart('l.InsuredName', 'InsuredName')
//                + getWherePart('l.InsuredSex', 'InsuredSex')
//                + getWherePart('l.UWFlag', 'UWStateQuery')
//                + getWherePart('l.AgentCode', 'AgentCode')
//                + getWherePart('l.AgentGroup', 'AgentGroup')
//                + " order by l.MakeDate ";
        

   	   var  ContNo2 = window.document.getElementsByName(trim("ContNo"))[0].value;
   	   var  PrtNoSearch2 = window.document.getElementsByName(trim("PrtNoSearch"))[0].value;
   	   var  ManageCom2 = window.document.getElementsByName(trim("ManageCom"))[0].value;
   	   var  AppntName2 = window.document.getElementsByName(trim("AppntName"))[0].value;
   	   var  InsuredName2 = window.document.getElementsByName(trim("InsuredName"))[0].value;
   	   var  InsuredSex2 = window.document.getElementsByName(trim("InsuredSex"))[0].value;
 	   var  UWStateQuery2 = window.document.getElementsByName(trim("UWStateQuery"))[0].value;
 	   var  AgentCode2 = window.document.getElementsByName(trim("AgentCode"))[0].value;
 	   var  AgentGroup2 = window.document.getElementsByName(trim("AgentGroup"))[0].value;
       var sqlid2="ApplyRecallSql2";
   	   var mySql2=new SqlClass();
   	   mySql2.setResourceName("uw.ApplyRecallSql");
   	   mySql2.setSqlId(sqlid2);//ָ��ʹ��SQL��id
   	   mySql2.addSubPara(ContNo2);//ָ���������
   	   mySql2.addSubPara(PrtNoSearch2);//ָ���������
   	   mySql2.addSubPara(ManageCom2);//ָ���������
   	   mySql2.addSubPara(AppntName2);//ָ���������
   	   mySql2.addSubPara(InsuredName2);//ָ���������
   	   mySql2.addSubPara(InsuredSex2);//ָ���������
   	   mySql2.addSubPara(UWStateQuery2);//ָ���������
   	   mySql2.addSubPara(AgentCode2);//ָ���������
   	   mySql2.addSubPara(AgentGroup2);//ָ���������
   	   var strSql = mySql2.getString();
        
    }

    else if (tContType == "2")
    {
//        var strSql = " select GrpContNo,PrtNo,GrpName,Operator,MakeDate,ApproveCode,ApproveDate,UWOperator,UWDate,UWFlag,State from LCGrpCont  where 1=1 "
//                + " and appflag = '0' "
//                + " and (((uwflag <> 'a') and (uwflag <> '1') and (uwflag <> '2'))"
//                + " or (uwflag is null))"
//                + " and (((substr(state, 1, 4)) <> '1001' and"
//                + " (substr(state, 1, 4)) <> '1002' and"
//                + " (substr(state, 1, 4)) <> '1003') or state is null) "
//                + getWherePart('GrpContNo', 'ContNo')
//                + getWherePart('PrtNo', 'PrtNoSearch')
//                + getWherePart('ManageCom', 'ManageCom', 'like')
//                + getWherePart('GrpName', 'AppntName')
//                + getWherePart('InsuredName', 'InsuredName')
//                + getWherePart('InsuredSex', 'InsuredSex')
//                + getWherePart('UWFlag', 'UWStateQuery')
//                + getWherePart('AgentCode', 'AgentCode')
//                + getWherePart('AgentGroup', 'AgentGroup')
//                + " order by MakeDate ";

           var  ContNo3 = window.document.getElementsByName(trim("ContNo"))[0].value;
    	   var  PrtNoSearch3 = window.document.getElementsByName(trim("PrtNoSearch"))[0].value;
    	   var  ManageCom3 = window.document.getElementsByName(trim("ManageCom"))[0].value;
    	   var  AppntName3 = window.document.getElementsByName(trim("AppntName"))[0].value;
    	   var  InsuredName3 = window.document.getElementsByName(trim("InsuredName"))[0].value;
    	   var  InsuredSex3 = window.document.getElementsByName(trim("InsuredSex"))[0].value;
  	       var  UWStateQuery3 = window.document.getElementsByName(trim("UWStateQuery"))[0].value;
  	       var  AgentCode3 = window.document.getElementsByName(trim("AgentCode"))[0].value;
  	       var  AgentGroup3 = window.document.getElementsByName(trim("AgentGroup"))[0].value;
           var sqlid3="ApplyRecallSql3";
    	   var mySql3=new SqlClass();
    	   mySql3.setResourceName("uw.ApplyRecallSql");
    	   mySql3.setSqlId(sqlid3);//ָ��ʹ��SQL��id
    	   mySql3.addSubPara(ContNo3);//ָ���������
    	   mySql3.addSubPara(PrtNoSearch3);//ָ���������
    	   mySql3.addSubPara(ManageCom3);//ָ���������
    	   mySql3.addSubPara(AppntName3);//ָ���������
    	   mySql3.addSubPara(InsuredName3);//ָ���������
    	   mySql3.addSubPara(InsuredSex3);//ָ���������
    	   mySql3.addSubPara(UWStateQuery3);//ָ���������
    	   mySql3.addSubPara(AgentCode3);//ָ���������
    	   mySql3.addSubPara(AgentGroup3);//ָ���������
    	   var strSql = mySql3.getString();
        
    }
    else
    {
        alert("��ѡ�񱣵�����")
        return;
    }

    turnPage.queryModal(strSql, PolGrid);
}