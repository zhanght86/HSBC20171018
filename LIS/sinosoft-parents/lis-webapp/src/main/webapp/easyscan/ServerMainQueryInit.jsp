<%
//�������ƣ�EsDocMainQuery.js
//�����ܣ�
//�������ڣ�2004-06-02
//������  ��LiuQiang
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">
function initForm()
{
  try
  {
	initInpBox();
//	initSelBox();    
	initCodeGrid();
  }
  catch(re)
  {
	alert("ServerMainQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 

  try
  {                                   
	document.all('ManageCom').value = '';
  }
  catch(ex)
  {
	alert("��ServerMainQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

// ������ĳ�ʼ��
function initSelBox()
{  
  try                 
  {
  }
  catch(ex)
  {
    alert("��ServerMainQueryInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        


/************************************************************
 *               
 *���룺          û��
 *�����          û��
 *���ܣ�          ��ʼ��CodeGrid
 ************************************************************
 */
var CodeGrid;		       //����Ϊȫ�ֱ������ṩ��displayMultilineʹ��
function initCodeGrid()
{                               
      var iArray = new Array();
      
      try
      {
      	iArray[0]=new Array();
        iArray[0][0]="���";         //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1]="30px";         //�п�
        iArray[0][2]=100;            //�����ֵ
        iArray[0][3]=0;              //�Ƿ���������,1��ʾ����0��ʾ������
        
		iArray[1]=new Array();
        iArray[1][0]="��˾����";         //����
        iArray[1][1]="60px";         //�п�
        iArray[1][2]=100;            //�����ֵ
        iArray[1][3]=0;              //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[2]=new Array();
        iArray[2][0]="����������";    //����
        iArray[2][1]="150px";         //���
        iArray[2][2]=100;         //��󳤶�
        iArray[2][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
 
        CodeGrid = new MulLineEnter( "fm" , "CodeGrid" ); 

        //��Щ���Ա�����loadMulLineǰ
        CodeGrid.mulLineCount = 5;   
        CodeGrid.displayTitle = 1;
        CodeGrid.canSel=1;
	//�����ʼ���������ö����ʼ�����������Ա����ڴ�ǰ����
	CodeGrid.hiddenSubtraction=1;
	CodeGrid.hiddenPlus=1;
	CodeGrid.canChk=1;
	CodeGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert("��ʼ��CodeGridʱ����"+ ex);
      }
}

</script>
