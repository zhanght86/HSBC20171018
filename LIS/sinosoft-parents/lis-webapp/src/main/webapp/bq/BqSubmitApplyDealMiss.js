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
		var iHeight=250;     //�������ڵĸ߶�; 
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
		var iHeight=250;     //�������ڵĸ߶�; 
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
	var sqlid1="DSHomeContSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("bq.BqSubmitApplyDealMissInputSql");//ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(usercode);//ָ������Ĳ���
	var strSQL=mySql1.getString();
	
//	var strSQL ="select a.SubNo,'',a.GrpContNo,(select edorname from lmedoritem where edorcode = a.EdorType and appobj ='G'),a.SubPer,a.substate,(select codename from ldcode where codetype = 'substate' and code = a.substate),a.SubDate,a.subtimes,(select subper from lpsubmitapplytrace where subno = a.subno and serialno = (select max(serialno) from lpsubmitapplytrace where subno = a.subno)),a.ModifyDate from LPSubmitApply a where a.DispPer='"+usercode+"' and substate <> 'C' order by a.SubNo";
    turnPage.pageLineNum=5;    //ÿҳ5��
    turnPage.queryModal(strSQL,LPSubmitApplyGrid);
}


//LLSubmitApplyGrid����¼�
function LPSubmitApplyGridClick()
{
    var i = LPSubmitApplyGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        
        var tSubNo = LPSubmitApplyGrid.getRowColData(i,1); 
        
        
    }
    //LoadFlag=1:���ճʱ���������
    var strUrl= "BqSubmitApplyDealInput.jsp?LoadFlag=1&SubNo="+tSubNo;    
    //location.href="LLSubmitApplyDealInput.jsp?";
    location.href=strUrl;
}
//��ʼ�����������С����  �ʱ����� missionprop6 = '2'--�ܹ�˾���ֹ�˾
function ReQueryClick()
{
	var sqlid2="DSHomeContSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName("bq.BqSubmitApplyDealMissInputSql");//ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(usercode);//ָ������Ĳ���
	var strSQL=mySql2.getString();
	
//	var strSQL ="select a.SubNo,'',a.GrpContNo,(select edorname from lmedoritem where edorcode = a.EdorType and appobj ='G'),a.DispPer,a.substate,(select codename from ldcode where codetype = 'substate' and code = a.substate),a.SubDate,a.ModifyDate from LPSubmitApply a where a.SubPer='"+usercode+"' and a.SubState in ('1','2','4') order by a.SubNo";
    returnPage.pageLineNum=5;    //ÿҳ5��
    returnPage.queryModal(strSQL,ReLPSubmitApplyGrid);
}

//LLSubmitApplyGrid����¼�
function ReLPSubmitApplyGridClick()
{
    var i = ReLPSubmitApplyGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        
        var tSubNo = ReLPSubmitApplyGrid.getRowColData(i,1); 
        
        
        
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