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
    initLDMaxNoElement();
  }
  catch(re)
  {
    alert("ConfigNativePlaceInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initMaxNoGrid() {  
    var iArray = new Array();
      
    try {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="10px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

  
      iArray[1]=new Array();
      iArray[1][0]="���Ա���";         		//����
      iArray[1][1]="0px";            		//�п�
      iArray[1][2]=60;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��������";         		//����
      iArray[2][1]="40px";            		//�п�
      iArray[2][2]=200;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="����ֵ";         		//����
      iArray[3][1]="40px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

			iArray[4]=new Array();
      iArray[4][0]="�޸ı��";         		//����
      iArray[4][1]="0px";            		//�п�
      iArray[4][2]=60;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="��ʾ���Ա��";         		//����
      iArray[5][1]="0px";            		//�п�
      iArray[5][2]=60;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="�Ŷ�Ԫ��ID";         		//����
      iArray[6][1]="0px";            		//�п�
      iArray[6][2]=60;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


      MaxNoGrid = new MulLineEnter( "fm" , "MaxNoGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      MaxNoGrid.mulLineCount = 5;   
      MaxNoGrid.displayTitle = 1;
      MaxNoGrid.hiddenPlus = 1;
      MaxNoGrid.hiddenSubtraction = 1;
      //ImpartGrid.tableWidth   ="500px";
      MaxNoGrid.loadMulLine(iArray);  

    }
    catch(ex) {
      alert(ex);
    }
}

</script>
