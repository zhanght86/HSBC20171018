//���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();
//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail" )
    {             
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
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
    }
}

//���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm()
{
    try
    {
        initForm();
    }
    catch(re)
    {
        alert("��LLLdPersonQuery.js-->resetForm�����з����쳣:��ʼ���������!");
    }
} 

//ȡ����ť��Ӧ����
function cancelForm()
{
  
}
 
//�ύǰ��У�顢����  
function beforeSubmit()
{
  //��Ӳ���  
}           

// ��ѯ��ť
function easyQueryClick()
{
    // ��ʼ�����
    initLLClaimRegisterGrid();
    var strSQL = "";
//select rgtno,rgtantname,rgtantsex,relation,rgtantaddress,rgtantphone,customerno,accidentdate,RgtConclusion,accidentsite,accidentreason,operator from llregister;
   /* strSQL = "select rgtno,rgtantname,rgtantphone,rgtantaddress,relation,operator,accidentsite,accidentdate,accidentreason,RgtConclusion,ClmState,MngCom from llregister where 1=1 "
           + getWherePart( 'RptNo' )
           + getWherePart( 'RptorName' )
           + getWherePart( 'RptorPhone' )
           + getWherePart( 'RptorAddress' )
           + getWherePart( 'Relation' )
           + getWherePart( 'RgtConclusion' );*/
mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLClaimRegisterQueryInputSql");
mySql.setSqlId("LLClaimRegisterQuerySql1");
mySql.addSubPara(fm.RptNo.value ); 
mySql.addSubPara(fm.RptorName.value ); 
mySql.addSubPara(fm.RptorPhone.value ); 
mySql.addSubPara(fm.RptorAddress.value ); 
mySql.addSubPara(fm.Relation.value ); 
mySql.addSubPara(fm.RgtConclusion.value ); 
//    execEasyQuery(strSQL);
    turnPage.queryModal(mySql.getString(),LLClaimRegisterGrid);
//    alert(strSQL);
}

//��ӦRadioBox�ĵ���¼����
function returnParent()
{
    var i = LLClaimRegisterGrid.getSelNo();
    if (i != 0)
    {      
        i = i - 1;
        var arr = new Array();
//select rgtno,rgtantname,rgtantphone,rgtantaddress,relation,operator,
//       accidentsite,accidentdate,accidentreason,RgtConclusion,ClmState,MngCom from llregister
        arr[0] = LLClaimRegisterGrid.getRowColData(i,1);
        arr[1] = LLClaimRegisterGrid.getRowColData(i,2);  
        arr[2] = LLClaimRegisterGrid.getRowColData(i,3);  
        arr[3] = LLClaimRegisterGrid.getRowColData(i,4);
        arr[4] = LLClaimRegisterGrid.getRowColData(i,5);
        arr[5] = LLClaimRegisterGrid.getRowColData(i,6);
        arr[6] = LLClaimRegisterGrid.getRowColData(i,7);  
        arr[7] = LLClaimRegisterGrid.getRowColData(i,8);
        arr[8] = LLClaimRegisterGrid.getRowColData(i,9);
        arr[9] = LLClaimRegisterGrid.getRowColData(i,10);
        arr[10] = LLClaimRegisterGrid.getRowColData(i,11);
        arr[11] = LLClaimRegisterGrid.getRowColData(i,12);
//        alert(arr);         
    }
    if (arr)
    {
       top.opener.afterQueryLL2(arr);
       top.close();
    }
}
//
////����Ϊ��������,��1980-5-9 
//function getAge(birth)
//{
//    var now = new Date();
//    var nowYear = now.getFullYear();
//    var oneYear = birth.substring(0,4);
//    var age = nowYear - oneYear;
//    if (age <= 0)
//    {
//        age = 0
//    }
//    return age;
//}
