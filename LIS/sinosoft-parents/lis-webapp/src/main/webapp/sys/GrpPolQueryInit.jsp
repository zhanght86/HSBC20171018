<%
//�������ƣ�GrpPolQueryInit.jsp
//�����ܣ�
//�������ڣ�2003-03-14 
//������  ��lh
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">

//���ذ�ť��ʼ��
var str = "";
function initDisplayButton()
{
	//alert("Dis:" + tDisplay);
 	//if(tDisplay != null && tDisplay != "")
 	//{	
 	//  fm.Return.style.display='none';
 	//  return;
 	//}
 	
 	//���屣ȫ��ѯ�Ḵ�øý��棬�����ݲ���4
 	if (tDisplay=="4")
	{
		fm.Return.style.display='';
	}
	else if (tDisplay=="1")
	{
		fm.Return.style.display='';
	}
	else if (tDisplay=="0")
	{
		fm.Return.style.display='none';
	}
	else if (tDisplay=="3")
	{
		return;
	}
	
}

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 

  try
  {                                   
	// ������ѯ����
    document.all('PrtNo').value = '';
    document.all('GrpContNo').value = '';
    document.all('ManageCom').value = '';
    document.all('AgentCode').value = '';
    document.all('AgentGroup').value = '';
     
  }
  catch(ex)
  {
    alert("��GroupPolQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

// ������ĳ�ʼ��
function initSelBox()
{  
  try                 
  {
//    setOption("t_sex","0=��&1=Ů&2=����");      
//    setOption("sex","0=��&1=Ů&2=����");        
//    setOption("reduce_flag","0=����״̬&1=�����");
//    setOption("pad_flag","0=����&1=�潻");   
  }
  catch(ex)
  {
    alert("��GroupPolQueryInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
  
    initInpBox();
    
    initSelBox(); 
    initGrpPolGrid();
  
    initDisplayButton();
  }
  catch(re)
  {
    alert("GroupPolQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б�ĳ�ʼ��
function initGrpPolGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�����ͬ��";         		//����
      iArray[1][1]="150px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="Ͷ������";         		//����
      iArray[2][1]="100px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      
      iArray[3]=new Array();
      iArray[3][0]="Ͷ��������";         		//����
      iArray[3][1]="80px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="��Ч����";         		//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="�б�����";         		//����
      iArray[5][1]="50px";            		//�п�
      iArray[5][2]=200;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="�ܱ���";         		//����
      iArray[6][1]="80px";            		//�п�
      iArray[6][2]=200;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="�ܱ���";         		//����
      iArray[7][1]="100px";            		//�п�
      iArray[7][2]=200;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      
      iArray[8]=new Array();
      iArray[8][0]="�б�����";         		//����
      iArray[8][1]="80px";            		//�п�
      iArray[8][2]=200;            			//�����ֵ
      iArray[8][3]=0; 

      iArray[9]=new Array();
      iArray[9][0]="����״̬";         		//����
      iArray[9][1]="80px";            		//�п�
      iArray[9][2]=200;            			//�����ֵ
      iArray[9][3]=0; 
    
      GrpPolGrid = new MulLineEnter( "fm" , "GrpPolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      GrpPolGrid.mulLineCount = 0;   
      GrpPolGrid.displayTitle = 1;
      GrpPolGrid.locked = 1;
      GrpPolGrid.canSel = 1;
      GrpPolGrid.hiddenPlus = 1;
      GrpPolGrid.hiddenSubtraction = 1;
      GrpPolGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      //GrpPolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
