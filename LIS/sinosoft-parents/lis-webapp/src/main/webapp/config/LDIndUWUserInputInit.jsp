<%
//�������ƣ�LDUWUserInput.jsp
//�����ܣ�
//�������ڣ�2005-01-24 18:15:01
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            
<script language="JavaScript">
function initInpBox()
{ 
  try
  {                                   
    document.all('UserCode').value = "";
    document.all('UserCode1').value = "";
   // document.all('UWType').value = "";
    document.all('UWType1').value = "";
    document.all('UpUwPopedom').value = "";
    document.all('UwPopedom1').value = "";
    
   // document.all('OtherUserCode').value = "";
   // document.all('OtherUpUWPopedom').value = "";
   // document.all('UWBranchCode').value = "";
    document.all('UWPopedom').value = "";
     
   // document.all('UserDescription').value = "";
   // document.all('UserState').value = "";
  //  document.all('PopUWFlag').value = "";
  //  document.all('ValidStartDate').value = "";
  //  document.all('ValidEndDate').value = "";
 //   document.all('IsPendFlag').value = "";
//    document.all('PendReason').value = "";
    document.all('Remark').value = "";
    document.all('Operator').value = "";
    document.all('ManageCom').value = "";
    document.all('MakeDate').value = "";
    document.all('MakeTime').value = "";
    document.all('ModifyDate').value = "";
    document.all('ModifyTime').value = "";
    document.all('edpopedom').value = "";
    //document.all('UWRate').value = "";

  }
  catch(ex)
  {
    alert("��LDUWUserInputInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}
function initSelBox()
{  
  try                 
  {
//    setOption("t_sex","0=��&1=Ů&2=����");      
//    setOption("sex","0=��&1=Ů&2=����");        
  }
  catch(ex)
  {
    alert("��LDUWUserInputInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        
function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    //alert(1);
    hh();
    initUWResultGrid();
    // alert(2);
    initUWMaxAmountGrid();    
  }
  catch(re)
  {
    alert("LDUWUserInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initUWResultGrid()
{                               
  var iArray = new Array();
    
  try {
    iArray[0]=new Array();
    iArray[0][0]="���";         		//����
    iArray[0][1]="30px";         		//����
    iArray[0][3]=0;         		//����
    //iArray[0][4]="station";         		//����
    
    iArray[1]=new Array();
    iArray[1][0]="�˱����۴���";  
    iArray[1][1]="80px";  
    iArray[1][3]=2;
    iArray[1][4]="hebaoquanxian";
    //iArray[10][5]="1|2|3";
    iArray[1][5]="1|2";
    iArray[1][6]="0|1";
    iArray[1][15]="codetype";
    //alert(document.all('tt').value);
    iArray[1][16]="#"+tt+"#";
    //iArray[1][4]='hebaoquanxian';
    //iArray[1][9]="�˱����۴���|code:contuwstate&NOTNULL";
    //iArray[1][5]="1|2";  //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
    //iArray[1][6]="0|1"; //��������з������ô����еڼ�λֵ
    //edit by yaory
    //iArray[1][10] = "uwstate';
    //iArray[1][11] = "0|^1|�ܱ�^2|����^4|��׼��^9|�α�׼��";
    //iArray[1][12]="1|2";  //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
    //iArray[1][13]="0|1"; //��������з������ô����еڼ�λֵ
    iArray[1][18] = 150;
   

    iArray[2]=new Array();
    iArray[2][0]="�˱�����";  
    iArray[2][1]="100px";  
    iArray[2][3]=0;       
    iArray[2][9]="�˱�����|NOTNULL";     

    
    UWResultGrid = new MulLineEnter( "document" , "UWResultGrid" ); 
    //��Щ���Ա�����loadMulLineǰ

    UWResultGrid.mulLineCount = 5;   
    UWResultGrid.displayTitle = 1;
    //LDUWUserGrid.selBoxEventFuncName = "showOne";

    UWResultGrid.loadMulLine(iArray);  
    //��Щ����������loadMulLine����
    //LDUWUserGrid.setRowColData(1,1,"asdf");
  }
  catch(ex) {
    alert(ex);
  }
}

function initUWMaxAmountGrid()
{                               
  var iArray = new Array();
    
  try {
    iArray[0]=new Array();
    iArray[0][0]="���";         		
    iArray[0][1]="30px";         	
    iArray[0][3]=0;         		

    iArray[1]=new Array();
	iArray[1][0]="���ִ���";  
	iArray[1][1]="40px";  
	iArray[1][3]=1; 
	iArray[1][9]="���ִ���|NOTNULL"; 
  
    iArray[2]=new Array();
  	iArray[2][0]="��������";  
  	iArray[2][1]="80px";  
  	iArray[2][3]=1; 
  	iArray[2][9]="��������|NOTNULL";
  		
  	iArray[3]=new Array();
  	iArray[3][0]="�������ޣ����գ�-�������ޣ����գ�";  
  	iArray[3][1]="100px";  
  	iArray[3][3]=1;   
  	iArray[3][9]="��������|NOTNULL"; 
  	

		

	    
    UWMaxAmountGrid = new MulLineEnter( "document" , "UWMaxAmountGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    UWMaxAmountGrid. hiddenPlus=1;
    UWMaxAmountGrid. hiddenSubtraction=1;
    UWMaxAmountGrid.mulLineCount = 5;   
    UWMaxAmountGrid.displayTitle = 1;
    //LDUWUserGrid.selBoxEventFuncName = "showOne";

	UWMaxAmountGrid.loadMulLine(iArray);  
    //��Щ����������loadMulLine����
    //LDUWUserGrid.setRowColData(1,1,"asdf");
  }
  catch(ex) {
    alert(ex);
  }
}


</script>
