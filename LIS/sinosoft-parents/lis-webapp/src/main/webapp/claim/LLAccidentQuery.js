//该文件中包含客户端需要处理的函数和事件
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();
//重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm()
{
    try
    {
        initForm();
    }
    catch(re)
    {
        alert("在LLLdPersonQuery.js-->resetForm函数中发生异常:初始化界面错误!");
    }
}

//取消按钮对应操作
function cancelForm()
{

}

//提交前的校验、计算
function beforeSubmit()
{
  //添加操作
}

//查询按钮
function easyQueryClick()
{
    //初始化表格
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
    //prompt("事件查询",strSQL);
    turnPage.queryModal(mySql.getString(),LLAccidentGrid);

}

//对应RadioBox的单记录返回
function returnParent()
{
    var i = LLAccidentGrid.getSelNo();
    var arr=new Array();
    
    if (i != 0)
    {
        i = i - 1;

        arr[0]=LLAccidentGrid.getRowColData(i,1);//事件号
        arr[1]=LLAccidentGrid.getRowColData(i,2);//事故日期
        arr[2]=LLAccidentGrid.getRowColData(i,3);//事故描述
        arr[3]=LLAccidentGrid.getRowColData(i,4);//事故地点
        arr[4]=LLAccidentGrid.getRowColData(i,5);//出险原因编码
        arr[5]=LLAccidentGrid.getRowColData(i,6);//出险原因名称
        arr[6]=LLAccidentGrid.getRowColData(i,7);//意外细节编码
        arr[7]=LLAccidentGrid.getRowColData(i,8);//意外细节名称
        arr[8]=LLAccidentGrid.getRowColData(i,9);//出险结果1编码
        arr[9]=LLAccidentGrid.getRowColData(i,10);//出险结果1名称
        arr[10]=LLAccidentGrid.getRowColData(i,11);//出险结果2编码
        arr[11]=LLAccidentGrid.getRowColData(i,12);//出险结果2名称
        arr[12]=LLAccidentGrid.getRowColData(i,15);//备注
 
        top.opener.afterQueryLL2(arr);
        
    }

    top.close();
}

//
////参数为出生年月,如1980-5-9
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
