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
    
    var tGrpContNo = fm.GrpContNo.value;
    
    var strSQL = "select a.insuredno,a.Name,a.sex,decode(a.sex,'0','��','1','Ů',''),a.Birthday,(select codename	from ldcode where '1225101583000' = '1225101583000' and codetype = 'idtype' and code = a.IDType),"
	      +" a.IDNo, a.IDType,b.appntno,b.grpname,b.grpcontno"
        +" from lcinsured a,lcgrpcont b"
        +" where a.grpcontno=b.grpcontno "
        +" and a.grpcontno='"+tGrpContNo+"' " ;
    
//    var strSQL = "select a.insuredno,a.Name,a.sex,decode(a.sex,'0','��','1','Ů',''),a.Birthday,(select codename	from ldcode where '1225101583000' = '1225101583000' and codetype = 'idtype' and code = a.IDType),"
//    	      +" a.IDNo, a.IDType,b.appntno,b.grpname,b.grpcontno"
//              +" from lcinsured a,lcgrpcont b,lccont c "
//              +" where a.grpcontno=b.grpcontno "
//              +" and c.insuredno = a.insuredno "
//              +" and c.poltype<>'1' "
//              +" and c.grpcontno = b.grpcontno "
//              +" and a.grpcontno='"+tGrpContNo+"' " ;
    
    //if(fm.ManageCom.value != "%")
    //{
        //strSQL += " and a.ManageCom like '"+fm.ManageCom.value+"%%'"
    //}
   /* mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpLdPersonQueryInputSql");
	mySql.setSqlId("LLGrpLdPersonQuerySql1");
	mySql.addSubPara(tGrpContNo );   */  
    if(fm.InsuredNo.value!="")
    {
         strSQL=strSQL+"  and a.insuredno='"+fm.InsuredNo.value+"'";
        /* mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpLdPersonQueryInputSql");
		mySql.setSqlId("LLGrpLdPersonQuerySql2");
		mySql.addSubPara(tGrpContNo );    
		mySql.addSubPara(fm.InsuredNo.value );  */
    }
  
    if(fm.Name.value!="")
    {
      strSQL=strSQL+"  and a.name like '%%"+fm.Name.value+"%%'";
      /*mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpLdPersonQueryInputSql");
		mySql.setSqlId("LLGrpLdPersonQuerySql3");
		mySql.addSubPara(tGrpContNo );    
		mySql.addSubPara(fm.InsuredNo.value );  
		mySql.addSubPara(fm.Name.value );  */
    }
    
    //���������������˲�ѯ
    strSQL = strSQL + " union "
    	+" select d.customerno,d.Name,d.sex,decode(d.sex,'0','��','1','Ů',''),d.Birthday,(select codename	from ldcode where '1225101583000' = '1225101583000' and codetype = 'idtype' and code = d.IDType),"
	    +" d.IDNo, d.IDType,e.appntno,e.grpname, e.grpcontno"
        +" from lcinsuredrelated d, lcgrpcont e, lcpol f "
        +" where e.grpcontno = f.grpcontno "
        +" and d.polno = f.polno "
        +" and e.grpcontno='"+tGrpContNo+"' " ;
   /* mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpLdPersonQueryInputSql");
		mySql.setSqlId("LLGrpLdPersonQuerySql4");
		mySql.addSubPara(tGrpContNo );    
		mySql.addSubPara(fm.InsuredNo.value );  
		mySql.addSubPara(fm.Name.value );  
		mySql.addSubPara(tGrpContNo ); */   
    
    //if(fm.ManageCom.value != "%")
    //{
        //strSQL += " and e.ManageCom like '"+fm.ManageCom.value+"%%'"
    //}
    
    if(fm.InsuredNo.value!="")
    {
         strSQL=strSQL+"  and d.customerno='"+fm.InsuredNo.value+"'";
         /*mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpLdPersonQueryInputSql");
		mySql.setSqlId("LLGrpLdPersonQuerySql5");
		mySql.addSubPara(tGrpContNo );    
		mySql.addSubPara(fm.InsuredNo.value );  
		mySql.addSubPara(fm.Name.value );  
		mySql.addSubPara(tGrpContNo );  
		mySql.addSubPara(fm.InsuredNo.value );  */
    }
  
    if(fm.Name.value!="")
    {
      strSQL=strSQL+"  and d.name like '%%"+fm.Name.value+"%%'";
     /* mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpLdPersonQueryInputSql");
		mySql.setSqlId("LLGrpLdPersonQuerySql6");
		mySql.addSubPara(tGrpContNo );    
		mySql.addSubPara(fm.InsuredNo.value );  
		mySql.addSubPara(fm.Name.value );  
		mySql.addSubPara(tGrpContNo );  
		mySql.addSubPara(fm.InsuredNo.value );
		mySql.addSubPara(fm.Name.value );    */
    }
    
    var arrResult = new Array();
//    prompt("���屨���ͻ���ѯ:",strSQL);
    
    arrResult=easyExecSql(strSQL);
    if(arrResult == null){

     alert("��ѯ�������������ļ�¼!");
     return false;
  }

    turnPage.queryModal(strSQL, PersonGrid);
}



//��ӦRadioBox�ĵ���¼����
function returnParent()
{
  
   var i = PersonGrid.getSelNo();

   if (i != 0)
   {    
      i = i - 1;
      var arr = new Array();
      arr[0] = PersonGrid.getRowColData(i,1);  //�������˿ͻ���
      arr[1] = PersonGrid.getRowColData(i,2);  //������������
      arr[2] = PersonGrid.getRowColData(i,3);  //���������Ա�
      arr[3] = PersonGrid.getRowColData(i,4);  //���������Ա�����
      arr[4] = getAge(PersonGrid.getRowColData(i,5));  //������������
      arr[5] = PersonGrid.getRowColData(i,6);  //��������֤������
      arr[6] = PersonGrid.getRowColData(i,7);  //��������֤������
      arr[7] = PersonGrid.getRowColData(i,8);  //��������֤������ֵ
      arr[8] = PersonGrid.getRowColData(i,9);  //Ͷ���˿ͻ���
      arr[9] = PersonGrid.getRowColData(i,10);  //Ͷ��������
      arr[10] = PersonGrid.getRowColData(i,11);  //�����ͬ��

   }
   if (arr)
   {
       top.opener.afterQueryLL(arr);
   }
   top.close();
}

//����Ϊ��������,��1980-5-9 
function getAge(birth)
{
  var now = new Date();
  var nowYear = now.getFullYear();
  var oneYear = birth.substring(0,4);
  var age = nowYear - oneYear;
  if (age <= 0)
  {
    age = 0
  }
  return age;
}



