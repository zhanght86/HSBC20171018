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
    initCurrencyGrid();
    queryCurrencyGrid();
  }
  catch(re)
  {
    alert("ConfigNativePlaceInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initCurrencyGrid() {  
    var iArray = new Array();
      
    try {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

  
      iArray[1]=new Array();
      iArray[1][0]="��������";         		//����
      iArray[1][1]="60px";            		//�п�
      iArray[1][2]=60;            			//�����ֵ
      iArray[1][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[1][9]="��������|notnull";

      iArray[2]=new Array();
      iArray[2][0]="������д";         		//����
      iArray[2][1]="60px";            		//�п�
      iArray[2][2]=200;            			//�����ֵ
      iArray[2][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[2][9]="������д|notnull";
      
      iArray[3]=new Array();
      iArray[3][0]="���ֱ���";         		//����
      iArray[3][1]="60px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[3][9]="���ֱ���|notnull";

			iArray[4]=new Array();
      iArray[4][0]="��Ч���";         		//����
      iArray[4][1]="20px";            		//�п�
      iArray[4][2]=60;            			//�����ֵ
      iArray[4][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[4][4]="valiflag";
      iArray[4][9]="��Ч���|notnull&code:valiflag";
			iArray[4][19]="1";
			

      CurrencyGrid = new MulLineEnter( "document" , "CurrencyGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      CurrencyGrid.mulLineCount = 5;   
      CurrencyGrid.displayTitle = 1;
      //ImpartGrid.tableWidth   ="500px";
      CurrencyGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      //ImpartGrid.setRowColData(1,1,"asdf");
    }
    catch(ex) {
      alert(ex);
    }
}

</script>
