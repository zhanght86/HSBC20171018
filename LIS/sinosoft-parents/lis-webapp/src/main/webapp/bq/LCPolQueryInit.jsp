<%
//�������ƣ�ReportQueryInit.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">
//����ʱ��ѯ
function reportDetailClick(cObj)
{
	  var ex,ey;
	  ex = window.event.clientX+document.body.scrollLeft;  //�õ��¼�������x
	  ey = window.event.clientY+document.body.scrollTop;   //�õ��¼�������y
	  divLCPol2.style.left=ex;
	  divLCPol2.style.top =ey;
	  divLCPol2.style.display ='';
}

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 

  try
  {                                   
	document.all('PolNo').value='';
  }
  catch(ex)
  {
    alert("��LCPolQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

// ������ĳ�ʼ��
function initSelBox()
{  
  try                 
  {
  //123
  }
  catch(ex)
  {
    alert("��LCPolQueryInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
     
    initInpBox();
    initSelBox();  
    initLCPolGrid();
  }
  catch(ex)
  {
    alert("PEdorQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б�ĳ�ʼ��
function initLCPolGrid()
{
    var iArray = new Array();
      
    try
     {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��             
      iArray[0][1]="30px";         			//�п�                                                     
      iArray[0][2]=10;          			//�����ֵ                                                 
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������                      
                                                                                                                   
      iArray[1]=new Array();                                                                                       
      iArray[1][0]="������";    			//����                                                     
      iArray[1][1]="150px";            			//�п�                                                     
      iArray[1][2]=100;            			//�����ֵ                                                 
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������                      
                                                                                                                   
      iArray[2]=new Array();                                                                                       
      iArray[2][0]="�ܵ���ͬ��";         		//����                                                     
      iArray[2][1]="150px";            			//�п�                                                     
      iArray[2][2]=100;            			//�����ֵ                                                 
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������                      
                                                                                                                   
      iArray[3]=new Array();                                                                                       
      iArray[3][0]="���屣����";         		//����                                                     
      iArray[3][1]="150px";            			//�п�                                                     
      iArray[3][2]=100;            			//�����ֵ                                                 
      iArray[3][3]=0;             			//�Ƿ���������,1��ʾ����0��ʾ������                      
                                                                                                                   
      iArray[4]=new Array();                                                                                       
      iArray[4][0]="Ͷ��������";         		//����                                                     
      iArray[4][1]="150px";            			//�п�                                                     
      iArray[4][2]=100;            			//�����ֵ                                                 
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������        
                                                                                                                   
      iArray[5]=new Array();                                                                                       
      iArray[5][0]="Ͷ��������";         		//����                                                     
      iArray[5][1]="80px";            			//�п�                                                     
      iArray[5][2]=100;            			//�����ֵ                                                 
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������        
                                                                                                                   
      iArray[6]=new Array();                                                                                       
      iArray[6][0]="���ֱ���";         			//����                                                     
      iArray[6][1]="100px";            			//�п�                                                     
      iArray[6][2]=100;            			//�����ֵ                                                 
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������        
     
      iArray[7]=new Array();                                                                                       
      iArray[7][0]="������";         			//����                                                     
      iArray[7][1]="100px";            			//�п�                                                     
      iArray[7][2]=100;            			//�����ֵ                                                 
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ�������� 
      
       iArray[8]=new Array();                                                                                       
      iArray[8][0]="�ʹ�����";         			//����                                                     
      iArray[8][1]="100px";            			//�п�                                                     
      iArray[8][2]=100;            			//�����ֵ                                                 
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ�������� 
      LCPolGrid = new MulLineEnter( "fm" , "LCPolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
     
      LCPolGrid.mulLineCount = 10;   
      LCPolGrid.displayTitle = 1;
      LCPolGrid.canSel=1;
      LCPolGrid.loadMulLine(iArray);  
      LCPolGrid.detailInfo="������ʾ��ϸ��Ϣ";
      LCPolGrid.detailClick=reportDetailClick;
      //��Щ����������loadMulLine����
      //SubReportGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>