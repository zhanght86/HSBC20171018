//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var mDebug="1";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();  //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var returnPage= new turnPageClass();
//�ύ��ɺ���������
function afterSubmit( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    }
    else
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    }
    tSaveFlag ="0";
}

////��ʼ��"���ճʱ�����"���------�ʱ����� missionprop6 = '0'--���ĵ��ֹ�˾
function easyQueryClick()
{ 
	var sqlid817145124="DSHomeContSql817145124";
var mySql817145124=new SqlClass();
mySql817145124.setResourceName("bq.BqSubmitApplyScanMissInputSql");//ָ��ʹ�õ�properties�ļ���
mySql817145124.setSqlId(sqlid817145124);//ָ��ʹ�õ�Sql��id
mySql817145124.addSubPara();//ָ������Ĳ���
var strSQL=mySql817145124.getString();
	
//	var strSQL ="select missionid,MissionProp2,CreateOperator,MakeDate,maketime from LWCPMission where ActivityStatus='1'";
    turnPage.pageLineNum=10;    //ÿҳ5��
    turnPage.queryModal(strSQL,LLSubmitApplyGrid);
}


//LLSubmitApplyGrid����¼�
function LLSubmitApplyGridClick()
{
    var i = LLSubmitApplyGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        
        var tSubNo = LLSubmitApplyGrid.getRowColData(i,1); 
        
        
    }
    //LoadFlag=1:���ճʱ���������
    var strUrl= "BqSubmitApplyDealInput.jsp?LoadFlag=5&flag=5&SubNo="+tSubNo;    
    //location.href="LLSubmitApplyDealInput.jsp?";
    location.href=strUrl;
}
//��ʼ�����������С����  �ʱ����� missionprop6 = '2'--�ܹ�˾���ֹ�˾
function ReQueryClick()
{
	var sqlid817145356="DSHomeContSql817145356";
var mySql817145356=new SqlClass();
mySql817145356.setResourceName("bq.BqSubmitApplyScanMissInputSql");//ָ��ʹ�õ�properties�ļ���
mySql817145356.setSqlId(sqlid817145356);//ָ��ʹ�õ�Sql��id
mySql817145356.addSubPara(usercode);//ָ������Ĳ���
var strSQL=mySql817145356.getString();
	
//	var strSQL ="select SubNo,SubCount,SubType,GrpContNo,SubState,CustomerName from LCSubmitApply where Operator='"+usercode+"' and HaveDealed in ('1','0')";//û�д�����ϵ�
    turnPage.pageLineNum=5;    //ÿҳ5��
    returnPage.queryModal(strSQL,ReLLSubmitApplyGrid);
}

//LLSubmitApplyGrid����¼�
function ReLLSubmitApplyGridClick()
{
    var i = ReLLSubmitApplyGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        
        var tSubNo = ReLLSubmitApplyGrid.getRowColData(i,1); 
        
        
        
    }
    var strUrl= "BqSubmitApplyDealInput.jsp?LoadFlag=3&SubNo="+tSubNo;    
//    location.href="LLSubmitApplyDealInput.jsp?";
    location.href=strUrl;
}
//���밴Ŧ�¼�
function apply()
{
	//var arrQueryResult = easyExecSql("select * from lcgrpcont where grpcontno='" + fm.PrtNo.value + "'", 1, 0);
  //
  //if(arrQueryResult==null)
  //{
  //	alert("�����ڴ˺�ͬ�ţ�");
  //	return;
  //}
	
	var strUrl= "BqSubmitApplyDealInput.jsp?LoadFlag=2";      
  location.href=strUrl;
}