var strOperate ="like";
var strOperate2 = "and";
var turnPage = new turnPageClass();

//��ѯ
function easyQueryClick(){
	initQuestQueryGrid();
	var BackObj1 = trim(fm.BackObj1.value);//alert("BackObj1:"+BackObj1+"BackObj1");
	var QuestCode1 = trim(fm.QuestCode1.value);//alert("QuestCode1:"+QuestCode1+"QuestCode1");
/*	if((BackObj1==null||BackObj1=="")&&(!(QuestCode1==null||QuestCode1==""))){
		alert("��ѡ���Ͷ���");
		return false;
	}*/
	/*if((BackObj1==null||BackObj1=="")&&(!(QuestCode1==null||QuestCode1==""))){
		fm.tCode1.value = "a"+QuestCode1;
	}else{
		fm.tCode1.value = trim(BackObj1+QuestCode1);
	}*/
//	var tQuerySql="select a.code,a.cont,a.sendobj,a.recordquest,a.operator,a.modifydate "
//				  +"from ldcodemod a where 1=1 and codetype = 'Question' "
//	              +getWherePart('code','QuestCode1',strOperate)+" "
//	              +getWherePart('RecordQuest','RecordQuest1',strOperate)
//	              +getWherePart('sendobj','BackObj1',strOperate)
//	              +" order by sendobj,code ";
	var tQuerySql="";
	var sqlid1="QuestContentQuerySql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("app.QuestContentQuerySql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(window.document.getElementsByName(trim('QuestCode1'))[0].value);//ָ������Ĳ���
	mySql1.addSubPara(window.document.getElementsByName(trim('RecordQuest1'))[0].value);//ָ������Ĳ���
	mySql1.addSubPara(window.document.getElementsByName(trim('BackObj1'))[0].value);//ָ������Ĳ���
	tQuerySql = mySql1.getString();
	var ssArr=easyExecSql(tQuerySql);//prompt("",tQuerySql);
	if(ssArr!=null){
		//fm.QuestCode.value=ssArr[0][0];
		//fm.Content.value=ssArr[0][1];
	}else{
		alert("δ�鵽���������������Ӧ�������");
		return false;
	}
	turnPage.queryModal(tQuerySql, QuestQueryGrid);
}

// ���ݷ��ظ�����
function returnParent()
{
	//alert("aaa="+top.opener.location);
	tRow = QuestQueryGrid.getSelNo() - 1;
	var tSel = QuestQueryGrid.getSelNo();
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{
		try
		{
			//top.opener.afterQuery();
			top.opener.fm.RecordQuest.value=fm.RecordQuest1.value;
			top.opener.fm.BackObj.value=fm.BackObj1.value;
			top.opener.fm.QuestCode.value=fm.QuestCode1.value;
			top.opener.fm.Content.value=fm.Content1.value;
			//tongmeng 2009-02-09 add
			//����ǲ�ѯ���صĻ�,�������޸ı���
			top.opener.fm.QuestCode.disabled = true;
			top.opener.fm.BackObj.disabled = true;
			top.close();
		}
		catch(ex)
		{
			alert( "����ѡ��һ���ǿռ�¼���ٵ�����ذ�ť��");
			//alert( "û�з��ָ����ڵ�afterQuery�ӿڡ�" + ex );
		}
	}
}

function displayQuestContent(){
	var tBackObj="";//���Ͷ���
	var tQuestCode="";//���������
	var checkFlag = QuestQueryGrid.getSelNo() - 1;
	
	var tCode=QuestQueryGrid.getRowColData(checkFlag, 1);
	//alert("1tCode:"+tCode.substring(1,3));
	//alert("2tCode:"+tCode.substring(2,4));
	fm.RecordQuest1.value=QuestQueryGrid.getRowColData(checkFlag, 4);
	fm.BackObj1.value=QuestQueryGrid.getRowColData(checkFlag, 3);
	//fm.QuestCode1.value=tCode.substring(1,4)
	fm.QuestCode1.value=tCode;
	fm.Content1.value=QuestQueryGrid.getRowColData(checkFlag, 2);
}

function MakeExcel(){
var i,j;
    try {
      var xls    = new ActiveXObject ( "Excel.Application" );
     }
    catch(e) {
         alert( "Ҫ��ӡ�ñ������밲װExcel���ӱ�������ͬʱ�������ʹ�á�ActiveX �ؼ��������������������ִ�пؼ��� �������������˽���������÷�����");
              return "";
     }
		//alert('1');
    xls.visible =true;  //����excelΪ�ɼ�

    var xlBook = xls.Workbooks.Add;
    var xlsheet = xlBook.Worksheets(1);
    <!--�ϲ�-->
      xlsheet.Range(xlsheet.Cells(1,1),xlsheet.Cells(1,7)).mergecells=true;
      xlsheet.Range(xlsheet.Cells(1,1),xlsheet.Cells(1,7)).value="���������";
     //  xlsheet.Range(xlsheet.Cells(1,1),xlsheet.Cells(1,6)).Interior.ColorIndex=5;//���õ�ɫΪ��ɫ 
                //   xlsheet.Range(xlsheet.Cells(1,1),xlsheet.Cells(1,6)).Font.ColorIndex=4;//��������ɫ         
   // xlsheet.Rows(1). Interior .ColorIndex = 5 ;//���õ�ɫΪ��ɫ  ���ñ���ɫ Rows(1).Font.ColorIndex=4  

 //   <!--�����и�-->
    xlsheet.Rows(1).RowHeight = 25;
  //  <!--�������� ws.Range(ws.Cells(i0+1,j0), ws.Cells(i0+1,j1)).Font.Size = 13 -->
    xlsheet.Rows(1).Font.Size=14;
  //  <!--�������� ����ѡ����������  xlsheet.Range(xlsheet.Cells(i0,j0), ws.Cells(i0,j0)).Font.Name = "����" -->
    xlsheet.Rows(1).Font.Name="����";
  //  <!--�����п� xlsheet.Columns(2)=14;-->

    xlsheet.Columns("A:D").ColumnWidth =18;
     <!--������ʾ�ַ�����������-->
    xlsheet.Columns(2).NumberFormatLocal="@";
    xlsheet.Columns(7).NumberFormatLocal="@";


     //���õ�Ԫ�������Զ����� range.WrapText  =  true  ;
     //���õ�Ԫ������ˮƽ���뷽ʽ range.HorizontalAlignment  =  Excel.XlHAlign.xlHAlignCenter;//���õ�Ԫ��������ֱ������ʽ
      //range.VerticalAlignment=Excel.XlVAlign.xlVAlignCenter
    //range.WrapText  =  true;  xlsheet.Rows(3).WrapText=true  �Զ�����
   
    //���ñ�����

     xlsheet.Cells(2,1).Value="���";
     xlsheet.Cells(2,2).Value="���������";
     xlsheet.Cells(2,3).Value="���������";
     xlsheet.Cells(2,4).Value="���Ͷ���";
     xlsheet.Cells(2,5).Value="�Ƿ���������";
     xlsheet.Cells(2,6).Value="������";
     xlsheet.Cells(2,7).Value="�޸�����";

     var oTable=document.all['fors:data'];
     
//     var tQuerySql="select a.code,a.cont,a.sendobj,a.recordquest,a.operator,a.modifydate "
//				  +"from ldcodemod a where 1=1 and codetype = 'Question' "
//	              +getWherePart('code','QuestCode1',strOperate)+" "
//	              +getWherePart('RecordQuest','RecordQuest1',strOperate)
//	              +getWherePart('sendobj','BackObj1',strOperate)
//	              +" order by sendobj,code ";
     var tQuerySql="";
 	var sqlid2="QuestContentQuerySql1";
 	var mySql2=new SqlClass();
 	mySql2.setResourceName("app.QuestContentQuerySql"); //ָ��ʹ�õ�properties�ļ���
 	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
 	mySql2.addSubPara(window.document.getElementsByName(trim('QuestCode1'))[0].value);//ָ������Ĳ���
 	mySql2.addSubPara(window.document.getElementsByName(trim('RecordQuest1'))[0].value);//ָ������Ĳ���
 	mySql2.addSubPara(window.document.getElementsByName(trim('BackObj1'))[0].value);//ָ������Ĳ���
 	tQuerySql = mySql2.getString();
	var ssArr=easyExecSql(tQuerySql);//prompt("",tQuerySql);
	if(ssArr!=null){
		//fm.QuestCode.value=ssArr[0][0];
		//fm.Content.value=ssArr[0][1];
	}else{
		alert("δ�鵽���������������Ӧ�������");
		return false;
	}
	var rowNum=ssArr.length;
	//alert(ssArr.length+":"+ssArr[0].length);
     for(i=2;i<=rowNum;i++){
     			for (j=1;j<7;j++)
     			{
							//html table����д��excel
							if(j==1)
							{
								xlsheet.Cells(i+1,j) = i-1;
							}
							else
							{
								//alert("i:"+i+":j:"+j+":value:"+ssArr[i-2][j-1]);
       					xlsheet.Cells(i+1,j).Value=ssArr[i-2][j-2];
            	}
					}

    }
    // xlsheet.Range(xlsheet.Cells(i, 4), xlsheet.Cells(i-1, 6)).BorderAround , 4
     // for(mn=1,mn<=6;mn++) .     xlsheet.Range(xlsheet.Cells(1, mn), xlsheet.Cells(i1, j)).Columns.AutoFit;
     // xlsheet.Columns.AutoFit;
             //    xlsheet.Range( xlsheet.Cells(1,1),xlsheet.Cells(rowNum+1,7)).HorizontalAlignment =-4108;//����
             //      xlsheet.Range( xlsheet.Cells(1,1),xlsheet.Cells(1,7)).VerticalAlignment =-4108;
      xlsheet.Range( xlsheet.Cells(2,1),xlsheet.Cells(rowNum+1,7)).Font.Size=10;

      xlsheet.Range( xlsheet.Cells(2,1),xlsheet.Cells(rowNum+1,7)).Borders(3).Weight = 2; //������߾�
       xlsheet.Range( xlsheet.Cells(2,1),xlsheet.Cells(rowNum+1,7)).Borders(4).Weight = 2;//�����ұ߾�
             xlsheet.Range( xlsheet.Cells(2,1),xlsheet.Cells(rowNum+1,7)).Borders(1).Weight = 2;//���ö��߾�
       xlsheet.Range( xlsheet.Cells(2,1),xlsheet.Cells(rowNum+1,7)).Borders(2).Weight = 2;//���õױ߾�
        xls.UserControl = true;  //����Ҫ,����ʡ��,��Ȼ������� ��˼��excel�����û�����
       xls=null;
       xlBook=null;
       xlsheet=null;

}