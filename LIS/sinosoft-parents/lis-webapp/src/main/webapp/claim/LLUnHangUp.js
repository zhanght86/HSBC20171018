//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug = "0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();
//�ύ�����水ť��Ӧ����
function submitForm()
{
    var i = 0;

    var selno = PolGrid.getSelNo() - 1;
    if (selno < 0)
    {
        alert("��ѡ��һ����¼��");
        return;
    }

//    fm.PrtNo.value = PolGrid.getRowColData(selno, 2);
//    if (fm.PrtNo.value == null || fm.PrtNo.value == "")
//    {
//        alert("ӡˢ��Ϊ�գ�");
//        return;
//    }
    if (fm.Content.value == null || fm.Content.value == "")
    {
        alert("�������޸�ԭ��");
        return;
    }
    var tContent = fm.Content.value;
    var tCustomerNo = fm.CustomerNo1.value;
    //alert(tContent);
    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var strUrl="LLUnHangUpApplyMain.jsp?InsuredNo="+tCustomerNo+"&ClmNo="+fm.RptNo.value+"&Content="+tContent;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    document.getElementById("fm").submit();
    //�ύ
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit(FlagStr, content)
{
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
   // window.open("./QuestionAdditionalRiskQuery.html");
   window.open("./QuestionAdditionalRiskQuery.html","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}

function easyQueryClick()
{
    // ��ʼ�����
    initPolGrid();

    if (fm.RptNo.value==""&&fm.CustomerNo.value==""&&fm.Name.value=="") {
    	alert("���������뱨���š������˿ͻ��š���������������һ���ѯ������");
    	return;
    }
//    Array
//    tArray = new Array();
//    tArray[0] = "RptNo";
//    tArray[1] = "CustomerNo";
//    tArray[2] = "Name";
//    tArray[3] = "AppntName";
//    tArray[4] = "InsuredName";
//    tArray[5] = "InsuredSex";
//    tArray[6] = "UWStateQuery";
//    tArray[7] = "AgentCode";
//    tArray[8] = "AgentGroup";
//    var j = 0;
//    for (var i = 0; i < tArray.length; i++) {
//        if (eval("fm." + tArray[i] + ".value") == "") {
//            j++;
//        }
//    }

    // ��дSQL���
//    var tContType = fm.ContType.value;
//    fm.PolType.value = tContType;

       /* var strSql = "select a.rptno,"
        			+ " a.rptorname,"
        			+ " a.makedate,"
        			+ " b.customerno,"
        			+ " b.customername,"
        			+ " b.sex,"
        			+ " b.accdate"
        			+ " from llreport a, LLSubReport b"
        			+ " where rgtflag = '10'"
        			+ " and a.rptno=b.subrptno"
        			+ getWherePart('a.rptno', 'RptNo')
        			+ getWherePart('b.customerno', 'CustomerNo')
        			+ getWherePart('b.customername', 'Name')
        			+ getWherePart('b.sex', 'Sex')
        			+ getWherePart('a.makedate', 'RptDate')
        			+ getWherePart('b.accdate', 'AccdentDate')
        			+ " order by a.MakeDate ";*/
			
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLUnHangUpSql");
	mySql.setSqlId("LLUnHangUpSql1");
	mySql.addSubPara(fm.RptNo.value ); 
	mySql.addSubPara(fm.CustomerNo.value ); 
	mySql.addSubPara(fm.Name.value ); 
	mySql.addSubPara(fm.Sex.value ); 
	mySql.addSubPara(fm.RptDate.value ); 
	mySql.addSubPara(fm.AccdentDate.value ); 
    turnPage.queryModal(mySql.getString(), PolGrid);//prompt("",strSql);
}

/**
 * ���multLineʱ�����ĺ���
 * */
function displayHidden(){
	var i = PolGrid.getSelNo();
    i = i - 1;
    fm.CustomerNo1.value=PolGrid.getRowColData(i,4);//�����˿ͻ���
    var rptno = PolGrid.getRowColData(i,1);
    //var strSql = "select assigneeaddr from llreport where rptno='"+rptno+"'";
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLUnHangUpSql");
	mySql.setSqlId("LLUnHangUpSql2");
	mySql.addSubPara(rptno);  
    var arrResult = easyExecSql(mySql.getString());
    if(arrResult){
    	fm.Content.value=arrResult;
    }
}
