//�������ƣ�WFEdorTest.js
//�����ܣ���ȫ������-��ȫ��������
//�������ڣ�2005-11-10 12:13:22
//������  ��zhangtao
//���¼�¼��  ������    ��������     ����ԭ��/����
//

var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();


/*********************************************************************
 *  ��ѯ�ҵ��������
 *  ����: ��ѯ�ҵ��������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function easyQueryClickSelf()
{
    // ��ʼ�����
    initSelfGrid();

    // ��дSQL���
    var strSQL = "";
    
    var sqlid1="DSHomeContSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("bq.WFGrpEdorTestInputSql");//ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(fm.EdorAcceptNo_ser.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.ManageCom_ser.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.OtherNo.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.OtherNoType.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.EdorAppName.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.AppType.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.MakeDate.value);//ָ������Ĳ���
		strSQL=mySql1.getString();

    
//    strSQL =  " select missionprop1, missionprop7, "
//            + " (select codename from ldcode d1 where trim(d1.codetype) = 'edornotype' and trim(d1.code) = trim(missionprop3)), "
//            + " MissionProp11, MissionProp12, " 
//            + " (select codename from ldcode d2 where trim(d2.codetype) = 'edorapptype' and trim(d2.code) = trim(missionprop5)), " 
//            + " (select codename from ldcode d3 where trim(d3.codetype) = 'station' and trim(d3.code) = trim(missionprop2)), "
//            + " createoperator, makedate, "
//            + " missionid, submissionid, activityid "
//            + " from lwmission where 1=1  "
//            + " and activityid = '0000008092' "
//            + " and processid = '0000000000' "
//            + " and defaultoperator ='" + operator + "'"
//            + " and missionid not in (select missionid from lwmission where activityid = '0000000000')"
//            + getWherePart('missionprop1', 'EdorAcceptNo_ser')
//            + getWherePart('missionprop2', 'ManageCom_ser') 
//            + getWherePart('missionprop7', 'OtherNo')   
//            + getWherePart('missionprop3', 'OtherNoType')   
//            + getWherePart('missionprop4', 'EdorAppName')   
//            + getWherePart('missionprop5', 'AppType')   
//            + getWherePart('MakeDate', 'MakeDate')  
//            + " order by MakeDate, MakeTime ";      
    turnPage2.queryModal(strSQL, SelfGrid);
    
    return true;
}

/*********************************************************************
 *  ��ת�������ҵ����ҳ��
 *  ����: ��ת�������ҵ����ҳ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function GoToBusiDeal()
{
    var selno = SelfGrid.getSelNo()-1;
    if (selno <0)
    {
          alert("��ѡ��Ҫ���������");
          return;
    }   

    var tEdorAcceptNo = SelfGrid.getRowColData(selno, 1);
    var tMissionID = SelfGrid.getRowColData(selno, 10);
    var tSubMissionID = SelfGrid.getRowColData(selno, 11);
    var tLoadFlag = "edorTest";
    
    var varSrc="&EdorAcceptNo=" + tEdorAcceptNo + "&MissionID=" + tMissionID + "&SubMissionID=" + tSubMissionID + "&LoadFlag=" + tLoadFlag;
    var newWindow = OpenWindowNew("../bq/GEdorTestFrame.jsp?Interface= ../bq/GEdorAppInput.jsp" + varSrc,"�ŵ���ȫ��������","left");    
}

/*********************************************************************
 *  ��������
 *  ����: ��������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function applyMission()
{
	fm.Transact.value = "EDORTEST||APPLY";
    var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    
    fm.action = "../bq/WFGrpEdorTestSave.jsp";
    document.getElementById("fm").submit(); //�ύ
}

/*********************************************************************
 *  ��ִ̨����Ϸ�����Ϣ
 *  ����: ��ִ̨����Ϸ�����Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{
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

    if (FlagStr == "Succ" ) 
    {
    	if (fm.Transact.value == "EDORTEST||APPLY")
    	{
            //ֱ�ӽ��뱣ȫ�������
            var tEdorAcceptNo = fm.EdorAcceptNo.value;
            var tMissionID;
            var tSubMissionID;
                        
            var strSQL;
            
            var sqlid2="DSHomeContSql2";
						var mySql2=new SqlClass();
						mySql2.setResourceName("bq.WFGrpEdorTestInputSql");//ָ��ʹ�õ�properties�ļ���
						mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
						mySql2.addSubPara(tEdorAcceptNo);//ָ������Ĳ���
						strSQL=mySql2.getString();

        
//            strSQL =  " select  missionid,submissionid " 
//                    + "   from  lwmission " 
//                    + "  where  activityid = '0000008092' " 
//                    + " and missionprop1 = '" + tEdorAcceptNo + "'";
            var brr = easyExecSql(strSQL);
            
            if ( brr )
            {
                //alert("�Ѿ����뱣���");
                brr[0][0]==null||brr[0][0]=='null'?'0':tMissionID    = brr[0][0];
                brr[0][1]==null||brr[0][1]=='null'?'0':tSubMissionID = brr[0][1];
            }
            else            
            {
                alert("�����������ѯʧ�ܣ�");
                return;
            }

            var tLoadFlag = "edorTest";
            
            var varSrc="&EdorAcceptNo=" + tEdorAcceptNo + "&MissionID=" + tMissionID + "&SubMissionID=" + tSubMissionID + "&LoadFlag=" + tLoadFlag;
            var newWindow = OpenWindowNew("../bq/GEdorTestFrame.jsp?Interface= ../bq/GEdorAppInput.jsp" + varSrc,"�ŵ���ȫ����","left");    
        }
        if (fm.Transact.value == "EDORTEST||FINISH")
        {
        	initForm();
        }
    }

}


/*********************************************************************
 *  �������
 *  ����: ��������ύ��̨�����������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function EdorTestFinish()
{
    fm.Transact.value = "EDORTEST||FINISH";
    
    var selno = SelfGrid.getSelNo()-1;
    if (selno <0)
    {
          alert("��ѡ��һ������");
          return;
    }
    
    fm.MissionID.value    = SelfGrid.getRowColData(selno, 10);
    fm.SubMissionID.value = SelfGrid.getRowColData(selno, 11);
    fm.ActivityID.value   = SelfGrid.getRowColData(selno, 12);
    fm.EdorAcceptNo.value = SelfGrid.getRowColData(selno, 1);
	var OtherNo =  SelfGrid.getRowColData(selno, 2);
	if (OtherNo == null || OtherNo == "")
	{
		alert("����������δ����,���ó���!");
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
    fm.action= "./GEdorTestFinishSubmit.jsp";
    document.getElementById("fm").submit();
}
