<%
//�������ƣ�LDCodeInput.jsp
//�����ܣ�
//�������ڣ�2005-01-26 13:18:17
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
    document.all('CurCountry').value = "";
  }
  catch(ex)
  {
    alert("ConfigNativePlaceInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("ConfigNativePlaceInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        
function initForm()
{
  try
  {
   // initInpBox();
   // initSelBox();   
    initMulCalendarGrid();
    queryNativePlaceCalendar();
    //queryCurrentCountry(); 
  }
  catch(re)
  {
    alert("ConfigNativePlaceInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initMulCalendarGrid() {  
    var iArray = new Array();
      
    try {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

  
      iArray[1]=new Array();
      iArray[1][0]="���ұ���";         		//����
      iArray[1][1]="60px";            		//�п�
      iArray[1][2]=60;            			//�����ֵ
      iArray[1][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[1][4]="nativeplace";
      iArray[1][9]="���ұ���|notnull&code:nativeplace";
			iArray[1][19]="1";

      iArray[2]=new Array();
      iArray[2][0]="������ʽ";         		//����
      iArray[2][1]="100px";            		//�п�
      iArray[2][2]=200;            			//�����ֵ
      iArray[2][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[2][4]="dateformat";
      iArray[2][9]="������ʽ|notnull";
      iArray[2][5]="2";
      iArray[2][6]="1";
      iArray[2][19]="1";

      MulCalendarGrid = new MulLineEnter( "document" , "MulCalendarGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      MulCalendarGrid.mulLineCount = 5;   
      MulCalendarGrid.displayTitle = 1;
      //ImpartGrid.tableWidth   ="500px";
      MulCalendarGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      //ImpartGrid.setRowColData(1,1,"asdf");
    }
    catch(ex) {
      alert(ex);
    }
}

</script>
