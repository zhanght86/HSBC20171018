<%
//�������ƣ�EsDocManageInit.jsp
//�����ܣ�
//�������ڣ�2004-06-02
//������  ��LiuQiang
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">
function initInpBox()
{ 
  try
  {                                   
  	document.all('DOC_ID').value = '' 	;
    document.all('DOC_CODE').value = '' 	;
    document.all('NUM_PAGES').value = ''	;
    document.all('INPUT_DATE').value = ''	;
    //document.all('Input_Time').value = ''	;
    document.all('ScanOperator').value = ''	;
    document.all('ManageCom').value = ''	;
    document.all('InputState').value = ''	;
    document.all('Operator').value = ''	;
    document.all('InputStartDate').value = ''	;
    document.all('InputStartTime').value = ''	;
	document.all('DOC_FLAGE').value = 	''	;
	document.all('DOC_REMARK').value = 	''	;
	//document.all('Doc_Ex_Flag').value = 	''	;
	document.all('InputEndDate').value = 	''	;
	document.all('InputEndTime').value = 	''	;
	fm.addbutton.disabled=true;
	//fm.deletebutton.disabled=true;	
	fm.updatebutton.disabled=true;

  }
  catch(ex)
  {
    alert("��CodeInputInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initInpBox1()
{ 
  try
  {                                   
  	document.all('DOC_ID').value = '' 	;
    //document.all('DOC_CODE').value = '' 	;
    document.all('NUM_PAGES').value = ''	;
    document.all('INPUT_DATE').value = ''	;
    //document.all('Input_Time').value = ''	;
    document.all('ScanOperator').value = ''	;
    document.all('ManageCom').value = ''	;
    document.all('InputState').value = ''	;
    document.all('Operator').value = ''	;
    document.all('InputStartDate').value = ''	;
    document.all('InputStartTime').value = ''	;
	document.all('DOC_FLAGE').value = 	''	;
	document.all('DOC_REMARK').value = 	''	;
	//document.all('Doc_Ex_Flag').value = 	''	;
	document.all('InputEndDate').value = 	''	;
	document.all('InputEndTime').value = 	''	;
	fm.addbutton.disabled=true;
	fm.deletebutton.disabled=true;

  }
  catch(ex)
  {
    alert("��CodeInputInit.jsp-->InitInpBox1�����з����쳣:��ʼ���������!");
  }      
}

function initSelBox()
{  
  try                 
  {
//    setOption("sex","0=��&1=Ů&2=����");        
  }
  catch(ex)
  {
    alert("��CodeInputInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
    initCodeGrid();
  }
  catch(re)
  {
    alert("CodeInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}


/************************************************************
 *               
 *���룺          û��
 *�����          û��
 *���ܣ�          ��ʼ��CodeGrid
 ************************************************************
 */
  var CodeGrid;          //����Ϊȫ�ֱ������ṩ��displayMultilineʹ��
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
        iArray[1][0]="ҳ�ڲ���";         //����
        iArray[1][1]="70px";         //�п�
        iArray[1][2]=100;            //�����ֵ
        iArray[1][3]=0;              //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[2]=new Array();
        iArray[2][0]="��֤�ڲ���";         //����
        iArray[2][1]="70px";         //���
        iArray[2][2]=100;         //��󳤶�
        iArray[2][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����

        iArray[3]=new Array();
        iArray[3][0]="ҳ��";         //����
        iArray[3][1]="30px";         //���
        iArray[3][2]=100;         //��󳤶�
        iArray[3][3]=1;         //�Ƿ�����¼�룬0--���ܣ�1--����

        iArray[4]=new Array();
        iArray[4][0]="��������";         //����
        iArray[4][1]="0px";         //���
        iArray[4][2]=100;         //��󳤶�
        iArray[4][3]=1;         //�Ƿ�����¼�룬0--���ܣ�1--����
    
        iArray[5]=new Array();
        iArray[5][0]="�ļ���";         //����
        iArray[5][1]="70px";         //���
        iArray[5][2]=100;         //��󳤶�
        iArray[5][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����

		iArray[6]=new Array();
        iArray[6][0]="״̬";         //����
        iArray[6][1]="30px";         //���
        iArray[6][2]=100;         //��󳤶�
        iArray[6][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
        
        iArray[7]=new Array();
        iArray[7][0]="�ļ�FTP·��";         //����
        iArray[7][1]="80px";         //���
        iArray[7][2]=100;         //��󳤶�
        iArray[7][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����

		iArray[8]=new Array();
        iArray[8][0]="�ļ�HTTP·��";         //����
        iArray[8][1]="80px";         //���
        iArray[8][2]=100;         //��󳤶�
        iArray[8][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
        
		iArray[9]=new Array();
        iArray[9][0]="ɨ�����Ա";         //����
        iArray[9][1]="70px";         //���
        iArray[9][2]=100;         //��󳤶�
        iArray[9][3]=1;         //�Ƿ�����¼�룬0--���ܣ�1--����
        
        iArray[10]=new Array();
        iArray[10][0]="ɨ������";         //����
        iArray[10][1]="70px";         //���
        iArray[10][2]=100;         //��󳤶�
        iArray[10][3]=1;         //�Ƿ�����¼�룬0--���ܣ�1--����
        
        iArray[11]=new Array();
        iArray[11][0]="ɨ��ʱ��";         //����
        iArray[11][1]="70px";         //���
        iArray[11][2]=100;         //��󳤶�
        iArray[11][3]=1;         //�Ƿ�����¼�룬0--���ܣ�1--����
        
        iArray[12]=new Array();
        iArray[12][0]="��֤����";         //����
        iArray[12][1]="70px";         //���
        iArray[12][2]=100;         //��󳤶�
        iArray[12][3]=2;         //�Ƿ�����¼�룬0--���ܣ�1--����
        iArray[12][4]="imagetype";  
        
        iArray[13]=new Array();
        iArray[13][0]="��֤ɨ�����";         //����
        iArray[13][1]="0px";         //���
        iArray[13][2]=100;         //��󳤶�
        iArray[13][3]=2;         //�Ƿ�����¼�룬0--���ܣ�1--����
        iArray[13][4]="scanno";                     
  
        CodeGrid = new MulLineEnter( "document" , "CodeGrid" ); 

        //��Щ���Ա�����loadMulLineǰ
        CodeGrid.mulLineCount = 5;   
        CodeGrid.displayTitle = 1;
        CodeGrid.canSel=0;
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
