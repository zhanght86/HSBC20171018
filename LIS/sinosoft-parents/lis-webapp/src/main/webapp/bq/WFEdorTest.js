//�������ƣ�WFEdorTest.js
//�����ܣ���ȫ������-��ȫ��������
//�������ڣ�2005-11-10 12:13:22
//������  ��zhangtao
//���¼�¼��  ������    ��������     ����ԭ��/����
//

var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var mySql=new SqlClass();

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
    mySql=new SqlClass();
    
    mySql.setResourceName("bq.WFEdorTest");
    mySql.setSqlId("WFEdorTestSql1");
    mySql.addSubPara(operator);   
    mySql.addSubPara(fm.EdorAcceptNo_ser.value);  
    mySql.addSubPara(fm.ManageCom_ser.value);  
    mySql.addSubPara(fm.OtherNo.value);          
    mySql.addSubPara(fm.OtherNoType.value);   
    mySql.addSubPara(fm.EdorAppName.value);  
    mySql.addSubPara(fm.AppType.value);  
    mySql.addSubPara(fm.MakeDate.value);  
 
               
    turnPage2.queryModal(mySql.getString(), SelfGrid);
    
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
    var tActivityID=SelfGrid.getRowColData(selno, 12);
    var tLoadFlag = "edorTest";
    
    var varSrc="&EdorAcceptNo=" + tEdorAcceptNo +"&ActivityID="+tActivityID +"&MissionID=" + tMissionID + "&SubMissionID=" + tSubMissionID + "&LoadFlag=" + tLoadFlag;
    var newWindow = OpenWindowNew("../bq/PEdorTestFrame.jsp?Interface= ../bq/PEdorAppInput.jsp" + varSrc,"��ȫ��������","left");    
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
    fm.action = "../bq/WFEdorTestSave.jsp";
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
    mySql=new SqlClass();     
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
            var tActivityID;
        	//modofiy zbx 2011-05-10 begin
            //mySql.setResourceName("bq.WFEdorNoScanAppSql");
            //mySql.setSqlId("WFEdorNoScanAppSql2");
            mySql.setResourceName("bq.WFEdorTest");
            mySql.setSqlId("WFEdorTestSql2");
            //modofiy zbx 2011-05-10 end
            mySql.addSubPara(tEdorAcceptNo);        

            var brr = easyExecSql(mySql.getString());
            
            if ( brr )
            {
                //alert("�Ѿ����뱣���");
                brr[0][0]==null||brr[0][0]=='null'?'0':tMissionID    = brr[0][0];
                brr[0][1]==null||brr[0][1]=='null'?'0':tSubMissionID = brr[0][1];
                brr[0][2]==null||brr[0][2]=='null'?'0':tActivityID = brr[0][2];
            }
            else            
            {
                alert("�����������ѯʧ�ܣ�");
                return;
            }

            var tLoadFlag = "edorTest";
            
            var varSrc="&EdorAcceptNo=" + tEdorAcceptNo + "&ActivityID="+tActivityID+"&MissionID=" + tMissionID + "&SubMissionID=" + tSubMissionID + "&LoadFlag=" + tLoadFlag;
            var newWindow = OpenWindowNew("../bq/PEdorTestFrame.jsp?Interface= ../bq/PEdorAppInput.jsp" + varSrc,"��ȫ����","left");    
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
    fm.action= "./PEdorTestFinishSubmit.jsp";
    document.getElementById("fm").submit();
}
