//�������ƣ�UWBack.js
//�����ܣ��˱�����
//�������ڣ�2003-03-27 15:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����

//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug = "1";
var turnPage = new turnPageClass();
var k = 0;
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
//�ύ�����水ť��Ӧ����
function submitForm()
{
    var i = 0;
    var showStr = "���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    initPolGrid();
    fm.submit();
    //�ύ
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit(FlagStr, content)
{
	unlockScreen('lkscreen');  
    showInfo.close();
    if (FlagStr == "Fail")
    {
        var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content ;

        alert(content);

    }
    else
    {

        var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
        alert(content);

    }

    initForm();
    //easyQueryClick();
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


// ��ѯ��ť
function easyQueryClick()
{
    k++;
    var strSQL = "";
    var strOperate = "like";
    /*
     strSQL = "select ProposalNo,PrtNo,RiskCode,RiskVersion,AppntName,InsuredName from LCPol where "+k+" = "+k
                  + " and VERIFYOPERATEPOPEDOM(LCPol.Riskcode,LCPol.Managecom,'"+comcode+"','Pg')=1 "
                  + " and AppFlag='0'"          //�б�
                  + " and UWFlag in ('1','2','4','9','a')"  //�˱�����״̬
                  + " and LCPol.ApproveFlag = '9' "
                  + " and grppolno = '00000000000000000000'"
                  + " and contno = '00000000000000000000'"
                  + " and polno = mainpolno"
                  + getWherePart( 'ProposalNo' )
                  + getWherePart( 'ManageCom' )
                  + getWherePart( 'AgentCode' )
                  + getWherePart( 'AgentGroup' )
                  + getWherePart( 'RiskCode' )
                  + getWherePart( 'PrtNo' )
                  + " and ManageCom like '" + comcode + "%%'";
                 */
				
		var sqlid1="UWBackSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("uw.UWBackSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(fm.ProposalNo.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.AgentCode.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.AgentGroup.value);//ָ������Ĳ���
		mySql1.addSubPara(comcode);//ָ������Ĳ���
	    strSQL=mySql1.getString();				
				
//    strSQL = "select lwmission.missionprop1,lwmission.missionprop5,lwmission.missionprop7,lwmission.missionprop2,lwmission.Missionid,lwmission.submissionid,(select username from lduser where trim(usercode)=lwmission.missionprop8) from lwmission where 1=1"
//            + " and activityid = '0000001149'"
//            + " and processid = '0000000003'"
//            + getWherePart('lwmission.MissionProp1', 'ProposalNo', strOperate)
//            + getWherePart('lwmission.MissionProp2', 'ManageCom', strOperate)
//            + getWherePart('lwmission.MissionProp3', 'AgentCode', strOperate)
//            + getWherePart('lwmission.MissionProp4', 'AgentGroup', strOperate)
//            + " and MissionProp2 like '" + comcode + "%%'"
//            ;
    //alert(strSQL);
    //��ѯSQL�����ؽ���ַ���
    turnPage.strQueryResult = easyQueryVer3(strSQL, 1, 0, 1);

    //�ж��Ƿ��ѯ�ɹ�
    if (!turnPage.strQueryResult) {
        //alert(turnPage.strQueryResult);
        alert("û�д��˱����˵���");
        return "";
    }

    //��ѯ�ɹ������ַ��������ض�ά����
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

    //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
    turnPage.pageDisplayGrid = PolGrid;

    //����SQL���
    turnPage.strQuerySql = strSQL;

    //���ò�ѯ��ʼλ��
    turnPage.pageIndex = 0;

    //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
    var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
    //alert(arrDataSet);
    //����MULTILINE������ʾ��ѯ���
    displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
    return true;
}

function displayEasyResult(arrResult)
{
    var i, j, m, n;

    if (arrResult == null)
        alert("û���ҵ���ص�����!");
    else
    {
        // ��ʼ�����
        initPolGrid();

        arrGrid = arrResult;
        // ��ʾ��ѯ���
        n = arrResult.length;
        for (i = 0; i < n; i++)
        {
            m = arrResult[i].length;
            for (j = 0; j < m; j++)
            {
                PolGrid.setRowColData(i, j + 1, arrResult[i][j]);
            }
            // end of for
        }
        // end of for
        //alert("result:"+arrResult);
    }
    // end of if
}

function back()
{
    //������ǰ̨У�������ѣ����ں�̨����У�顣
    //  //�ж��Ƿ�¼��˱�����ԭ��

    //  alert(trim(fm.UWIdea.value).length);
    //  alert("_"+trim(fm.UWIdea.value)+"_");
    //  if(trim(fm.UWIdea.value)==""){
    //      alert("��¼��˱�����ԭ��");
    //      return;
    //  }


    var i = 0;
    var showStr = "���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
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
    //showSubmitFrame(mDebug);
    fm.submit();
    //�ύ
}

/*********************************************************************
 *  ��ʾfrmSubmit��ܣ���������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showSubmitFrame(cDebug)
{
    if (cDebug == "1")
        parent.fraMain.rows = "0,0,50,82,*";
    else
        parent.fraMain.rows = "0,0,0,72,*";
}


//ѡ����ʱ���¼Ͷ������
// ��ѯ��ť
function easyQueryAddClick(param1, parm2)
{
    var tProposalNo = "";
    var tPrtNo = "";
    var tMission = "";
    var tSubMission = "";
    var tInsuredName = "";
    var tAppntName = "";
	
	var selectRowNum = param1.replace(/spanPublicWorkPoolGrid/g,"");
	
    if (document.all('InpPublicWorkPoolGridSel'+selectRowNum).value == '1')
    {
        //��ǰ�е�1�е�ֵ��Ϊ��ѡ��
        tProposalNo = document.all('PublicWorkPoolGrid5'+'r'+selectRowNum).value;
        tPrtNo = document.all('PublicWorkPoolGrid1'+'r'+selectRowNum).value;
        tAppntName = document.all('PublicWorkPoolGrid2'+'r'+selectRowNum).value;
        
        var sqlid3="UWBackSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("uw.UWBackSql"); //ָ��ʹ�õ�properties�ļ���
		mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
		mySql3.addSubPara(tPrtNo);
		strSQL=mySql3.getString();	
		
        tInsuredName = easyExecSql(strSQL);
        tMission = document.all('PublicWorkPoolGrid11'+'r'+selectRowNum).value;
        tSubMission = document.all('PublicWorkPoolGrid12'+'r'+selectRowNum).value;
    }

    if (tProposalNo == "")
    {
        alert("��ѡ��Ͷ������");
    }
    
	
    fm.ActivityID.value = document.all('PublicWorkPoolGrid14'+'r'+selectRowNum).value;
    fm.ProposalNoHide.value = tProposalNo;
    fm.PrtNo.value = tPrtNo;
    fm.AppntName.value = tAppntName;
    fm.InsuredName.value = tInsuredName;
    fm.MissionId.value = tMission;
    fm.SubMissionId.value = tSubMission;
    divUWIdea.style.display = "";

    return true;
}

//��ѯ�����˵ķ�ʽ
function queryAgent(comcode)
{
	//alert("ok");
  if(document.all('AgentCode').value == "")	
  {  
		var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+comcode,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	  
  }
  
	if(document.all('AgentCode').value != "")	 
	{
	var cAgentCode = fm.AgentCode.value;  //�����˱���	
	
		var sqlid2="UWBackSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("uw.UWBackSql"); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(cAgentCode);//ָ������Ĳ���
	    var strSql=mySql2.getString();		
	
//	var strSql = "select AgentCode,Name from LAAgent where AgentCode='" + cAgentCode +"'";// and ManageCom = '"+document.all('ManageCom').value+"'";
    var arrResult = easyExecSql(strSql);
    //alert(arrResult);
    if (arrResult != null) 
    {
			alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"] ����������Ϊ:["+arrResult[0][1]+"]");
    }
    else
    {
			
			alert("����Ϊ:["+document.all('AgentCode').value+"]�Ĵ����˲����ڣ���ȷ��!");
    }
	}	
}

