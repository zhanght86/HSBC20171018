//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();
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

//��ѯ��ť
function easyQueryClick()
{
    //��ʼ�����
    //modify by niuzj,2005-11-03
    initLLAccidentGrid();
    /*var strSQL = " select a.AccNo,a.ACCDATE,a.AccDesc,a.AccPlace,"
                +" a.AccType,(select CodeName from ldcode	where codetype = 'lloccurreason' and code = a.AccType)," 
                +" b.accidentDetail,(select CodeName from ldcode	where codetype = 'accidentcode' and code = b.accidentDetail),"
                +" b.accresult1,(select ICDName from LDDisease where icdcode=b.accresult1),"
                +" b.accresult2,(select ICDName from LDDisease where icdcode=b.accresult2),"
                +" (select username from lduser where usercode = a.operator),a.MngCom,b.Remark"
                +" from llaccident a, LLSubReport b,LLCaseRela c"
                +" where a.accno=c.caserelano and  b.subrptno=c.caseno"
                +" and a.AccDate = to_date('"+tAccDate+ "','yyyy-mm-dd')"
                +" and b.CustomerNo='" + tCustomerNo + "'" ;*/
     mySql = new SqlClass();
	mySql.setResourceName("claim.LLAccidentQueryInputSql");
	mySql.setSqlId("LLAccidentQuerySql1");
	mySql.addSubPara(tAccDate); 
	mySql.addSubPara(tCustomerNo);                
    //prompt("�¼���ѯ",strSQL);
    turnPage.queryModal(mySql.getString(),LLAccidentGrid);

}

//��ӦRadioBox�ĵ���¼����
function returnParent()
{
    var i = LLAccidentGrid.getSelNo();
    var arr=new Array();
    
    if (i != 0)
    {
        i = i - 1;

        arr[0]=LLAccidentGrid.getRowColData(i,1);//�¼���
        arr[1]=LLAccidentGrid.getRowColData(i,2);//�¹�����
        arr[2]=LLAccidentGrid.getRowColData(i,3);//�¹�����
        arr[3]=LLAccidentGrid.getRowColData(i,4);//�¹ʵص�
        arr[4]=LLAccidentGrid.getRowColData(i,5);//����ԭ�����
        arr[5]=LLAccidentGrid.getRowColData(i,6);//����ԭ������
        arr[6]=LLAccidentGrid.getRowColData(i,7);//����ϸ�ڱ���
        arr[7]=LLAccidentGrid.getRowColData(i,8);//����ϸ������
        arr[8]=LLAccidentGrid.getRowColData(i,9);//���ս��1����
        arr[9]=LLAccidentGrid.getRowColData(i,10);//���ս��1����
        arr[10]=LLAccidentGrid.getRowColData(i,11);//���ս��2����
        arr[11]=LLAccidentGrid.getRowColData(i,12);//���ս��2����
        arr[12]=LLAccidentGrid.getRowColData(i,15);//��ע
 
        top.opener.afterQueryLL2(arr);
        
    }

    top.close();
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
