var strOperate ="like";
var strOperate2 = "and";
var turnPage = new turnPageClass();

//查询
function easyQueryClick(){
	initQuestQueryGrid();
	var BackObj1 = trim(fm.BackObj1.value);//alert("BackObj1:"+BackObj1+"BackObj1");
	var QuestCode1 = trim(fm.QuestCode1.value);//alert("QuestCode1:"+QuestCode1+"QuestCode1");
/*	if((BackObj1==null||BackObj1=="")&&(!(QuestCode1==null||QuestCode1==""))){
		alert("请选择发送对象");
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
	mySql1.setResourceName("app.QuestContentQuerySql"); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(window.document.getElementsByName(trim('QuestCode1'))[0].value);//指定传入的参数
	mySql1.addSubPara(window.document.getElementsByName(trim('RecordQuest1'))[0].value);//指定传入的参数
	mySql1.addSubPara(window.document.getElementsByName(trim('BackObj1'))[0].value);//指定传入的参数
	tQuerySql = mySql1.getString();
	var ssArr=easyExecSql(tQuerySql);//prompt("",tQuerySql);
	if(ssArr!=null){
		//fm.QuestCode.value=ssArr[0][0];
		//fm.Content.value=ssArr[0][1];
	}else{
		alert("未查到该问题件编码所对应的问题件");
		return false;
	}
	turnPage.queryModal(tQuerySql, QuestQueryGrid);
}

// 数据返回父窗口
function returnParent()
{
	//alert("aaa="+top.opener.location);
	tRow = QuestQueryGrid.getSelNo() - 1;
	var tSel = QuestQueryGrid.getSelNo();
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录，再点击返回按钮。" );
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
			//如果是查询返回的话,不允许修改编码
			top.opener.fm.QuestCode.disabled = true;
			top.opener.fm.BackObj.disabled = true;
			top.close();
		}
		catch(ex)
		{
			alert( "请先选择一条非空记录，再点击返回按钮。");
			//alert( "没有发现父窗口的afterQuery接口。" + ex );
		}
	}
}

function displayQuestContent(){
	var tBackObj="";//发送对像
	var tQuestCode="";//问题件编码
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
         alert( "要打印该表，您必须安装Excel电子表格软件，同时浏览器须使用“ActiveX 控件”，您的浏览器须允许执行控件。 请点击【帮助】了解浏览器设置方法！");
              return "";
     }
		//alert('1');
    xls.visible =true;  //设置excel为可见

    var xlBook = xls.Workbooks.Add;
    var xlsheet = xlBook.Worksheets(1);
    <!--合并-->
      xlsheet.Range(xlsheet.Cells(1,1),xlsheet.Cells(1,7)).mergecells=true;
      xlsheet.Range(xlsheet.Cells(1,1),xlsheet.Cells(1,7)).value="问题件内容";
     //  xlsheet.Range(xlsheet.Cells(1,1),xlsheet.Cells(1,6)).Interior.ColorIndex=5;//设置底色为蓝色 
                //   xlsheet.Range(xlsheet.Cells(1,1),xlsheet.Cells(1,6)).Font.ColorIndex=4;//设置字体色         
   // xlsheet.Rows(1). Interior .ColorIndex = 5 ;//设置底色为蓝色  设置背景色 Rows(1).Font.ColorIndex=4  

 //   <!--设置行高-->
    xlsheet.Rows(1).RowHeight = 25;
  //  <!--设置字体 ws.Range(ws.Cells(i0+1,j0), ws.Cells(i0+1,j1)).Font.Size = 13 -->
    xlsheet.Rows(1).Font.Size=14;
  //  <!--设置字体 设置选定区的字体  xlsheet.Range(xlsheet.Cells(i0,j0), ws.Cells(i0,j0)).Font.Name = "黑体" -->
    xlsheet.Rows(1).Font.Name="黑体";
  //  <!--设置列宽 xlsheet.Columns(2)=14;-->

    xlsheet.Columns("A:D").ColumnWidth =18;
     <!--设置显示字符而不是数字-->
    xlsheet.Columns(2).NumberFormatLocal="@";
    xlsheet.Columns(7).NumberFormatLocal="@";


     //设置单元格内容自动换行 range.WrapText  =  true  ;
     //设置单元格内容水平对齐方式 range.HorizontalAlignment  =  Excel.XlHAlign.xlHAlignCenter;//设置单元格内容竖直堆砌方式
      //range.VerticalAlignment=Excel.XlVAlign.xlVAlignCenter
    //range.WrapText  =  true;  xlsheet.Rows(3).WrapText=true  自动换行
   
    //设置标题栏

     xlsheet.Cells(2,1).Value="序号";
     xlsheet.Cells(2,2).Value="问题件编码";
     xlsheet.Cells(2,3).Value="问题件内容";
     xlsheet.Cells(2,4).Value="发送对象";
     xlsheet.Cells(2,5).Value="是否计入问题件";
     xlsheet.Cells(2,6).Value="定义人";
     xlsheet.Cells(2,7).Value="修改日期";

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
 	mySql2.setResourceName("app.QuestContentQuerySql"); //指定使用的properties文件名
 	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
 	mySql2.addSubPara(window.document.getElementsByName(trim('QuestCode1'))[0].value);//指定传入的参数
 	mySql2.addSubPara(window.document.getElementsByName(trim('RecordQuest1'))[0].value);//指定传入的参数
 	mySql2.addSubPara(window.document.getElementsByName(trim('BackObj1'))[0].value);//指定传入的参数
 	tQuerySql = mySql2.getString();
	var ssArr=easyExecSql(tQuerySql);//prompt("",tQuerySql);
	if(ssArr!=null){
		//fm.QuestCode.value=ssArr[0][0];
		//fm.Content.value=ssArr[0][1];
	}else{
		alert("未查到该问题件编码所对应的问题件");
		return false;
	}
	var rowNum=ssArr.length;
	//alert(ssArr.length+":"+ssArr[0].length);
     for(i=2;i<=rowNum;i++){
     			for (j=1;j<7;j++)
     			{
							//html table类容写到excel
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
             //    xlsheet.Range( xlsheet.Cells(1,1),xlsheet.Cells(rowNum+1,7)).HorizontalAlignment =-4108;//居中
             //      xlsheet.Range( xlsheet.Cells(1,1),xlsheet.Cells(1,7)).VerticalAlignment =-4108;
      xlsheet.Range( xlsheet.Cells(2,1),xlsheet.Cells(rowNum+1,7)).Font.Size=10;

      xlsheet.Range( xlsheet.Cells(2,1),xlsheet.Cells(rowNum+1,7)).Borders(3).Weight = 2; //设置左边距
       xlsheet.Range( xlsheet.Cells(2,1),xlsheet.Cells(rowNum+1,7)).Borders(4).Weight = 2;//设置右边距
             xlsheet.Range( xlsheet.Cells(2,1),xlsheet.Cells(rowNum+1,7)).Borders(1).Weight = 2;//设置顶边距
       xlsheet.Range( xlsheet.Cells(2,1),xlsheet.Cells(rowNum+1,7)).Borders(2).Weight = 2;//设置底边距
        xls.UserControl = true;  //很重要,不能省略,不然会出问题 意思是excel交由用户控制
       xls=null;
       xlBook=null;
       xlsheet=null;

}