<%
//�������ƣ�EdorCancelInit.jsp
//�����ܣ���ȫ����
//�������ڣ�2005-05-08 09:20:22
//������  ��zhangtao
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<script language="JavaScript">

    var AppItemGrid;     //ȫ�ֱ���, ������Ŀ����

//ҳ���ʼ��
function initForm()
{
  try
  {
    initParam();
    initInputBox();
    initQuery();
    initEdorItemGrid();
    queryAppItemGrid();
  }
  catch(re)
  {
    alert("PEdorInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

//��null���ַ���תΪ��
function nullToEmpty(string)
{
    if ((string == "null") || (string == "undefined"))
    {
        string = "";
    }
    return string;
}

//���մӹ�������ȫ����ҳ�洫�ݹ����Ĳ���
function initParam()
{
   document.all('EdorAcceptNo').value     = nullToEmpty("<%= tEdorAcceptNo %>");
   document.all('MissionID').value        = nullToEmpty("<%= tMissionID %>");
   document.all('SubMissionID').value     = nullToEmpty("<%= tSubMissionID %>");
}

//��ʼ��ҳ��Ԫ��
function initInputBox()
{
    try
    {
        document.getElementsByName("DelFlag")[0].value = "GEdorApp";    //XinYQ added on 2006-02-09 : Ĭ�ϳ���ȫ����ȫ����
    }
    catch(ex)
    {
        alert("�� PEdorInputInit.jsp --> initInputBox �����з����쳣:��ʼ��������� ");
    }
}

function initEdorItemGrid()
{                               
    var iArray = new Array();
      
	  try
	  {
	  	  iArray[0]=new Array();
	  	  iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
	  	  iArray[0][1]="30px";            		//�п�
	  	  iArray[0][2]=30;            			//�����ֵ
	  	  iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
	  	  iArray[1]=new Array();
	  	  iArray[1][0]="����������";         		//����
	  	  iArray[1][1]="100px";            		//�п�
	  	  iArray[1][2]=100;            			//�����ֵ
	  	  iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
	  	  iArray[2]=new Array();
	  	  iArray[2][0]="��������";         		//����
	  	  iArray[2][1]="120px";            		//�п�
	  	  iArray[2][2]=100;            			//�����ֵ
	  	  iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	  	  
	  	  iArray[3]=new Array();
	  	  iArray[3][0]="���屣����";         		//����
	  	  iArray[3][1]="110px";            		//�п�
	  	  iArray[3][2]=100;            			//�����ֵ
	  	  iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
	  	  iArray[4]=new Array();
	  	  iArray[4][0]="��Ч����";         		//����
	  	  iArray[4][1]="70px";            		//�п�
	  	  iArray[4][2]=100;            			//�����ֵ
	  	  iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
	  	  iArray[5]=new Array();
	  	  iArray[5][0]="���˷ѽ��ϼ�";         	//����
	  	  iArray[5][1]="70px";            		//�п�
	  	  iArray[5][2]=100;            			//�����ֵ
	  	  iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
	  	  iArray[6]=new Array();
	  	  iArray[6][0]="���˷���Ϣ";         	//����
	  	  iArray[6][1]="70px";            		//�п�
	  	  iArray[6][2]=100;            			//�����ֵ
	  	  iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	  	  		  
	  	  iArray[7]=new Array();
	  	  iArray[7][0]="����״̬";
	  	  iArray[7][1]="70px";
	  	  iArray[7][2]=100;
	  	  iArray[7][3]=0;
                  
	  	  iArray[8]=new Array();
	  	  iArray[8][0]="�˱�״̬";
	  	  iArray[8][1]="70px";
	  	  iArray[8][2]=100;
	  	  iArray[8][3]=0; 
	  	  
	  	  iArray[9]=new Array();
		  iArray[9][0]="����";          
		  iArray[9][1]="50px";              
	      iArray[9][2]=60;             
		  iArray[9][3]=2;               
    	  iArray[9][4]="Currency";                 
          iArray[9][9]="����|code:Currency";
	  	  
	  	  
	  	  EdorItemGrid = new MulLineEnter( "document" , "EdorItemGrid" ); 
	  	  //��Щ���Ա�����loadMulLineǰ
	  	  EdorItemGrid.mulLineCount = 3;   
	  	  EdorItemGrid.displayTitle = 1;
	  	  EdorItemGrid.locked = 1;
	  	  //EdorItemGrid.canSel = 1;
	  	  EdorItemGrid.hiddenPlus = 1;
	  	  EdorItemGrid.hiddenSubtraction=1;
	  	  EdorItemGrid.loadMulLine(iArray);       
	  }
	  catch(ex)
	  {
	  	alert(ex);
	  }
}

</script>
