<script language=javascript>

//��null���ַ���תΪ��
function nullToEmpty(string)
{
	if ((string == "null") || (string == "undefined"))
	{
		string = "";
	}
	return string;
}

function initHiddenBox()
{
    fm.LoadFlag.value = nullToEmpty("<%=request.getParameter("LoadFlag")%>");
    fm.GrpContNoIn.value = nullToEmpty("<%=request.getParameter("GrpContNo")%>");
}

function initInpBox()
{

} 

function initForm()
{
    try
    {
        initHiddenBox();        
        var tLoadFlag = fm.LoadFlag.value;
        if(tLoadFlag=="4"||tLoadFlag=="16"||tLoadFlag=="14")
        {
            divTiggerSave.style.display="none";
        }
        initInpBox();
        
        initAccTriggerGrid();
        //getRiskAcc(); //��ѯ�����ʻ�����
        GrpPerPolDefine();
        //GrpPerPolDefineOld();
    }
    catch(ex)
    {
        alert("��LCGrpAccTriggerInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
}

var AccTriggerGrid;
function initAccTriggerGrid()
{
    var iArray=new Array()
    try
    {
        iArray[0] = new Array();
        iArray[0][0] = "���";         			 //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1] = "30px";            		 //�п�
        iArray[0][2] = 10;            			   //�����ֵ
        iArray[0][3] = 0;              			 //�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[1]=new Array();
        iArray[1][0]="�����ʻ�����";
        iArray[1][1]="00px";
        iArray[1][2]=100;
        iArray[1][3]=0;
        
        iArray[2]=new Array();
        iArray[2][0]="�ɷѼƻ�����";
        iArray[2][1]="00px";
        iArray[2][2]=100;
        iArray[2][3]=0;       
   
   
        iArray[3]=new Array();
        iArray[3][0]="�ʻ�����";
        iArray[3][1]="50px";
        iArray[3][2]=100;
        iArray[3][3]=1;
        
        iArray[4]=new Array();
        iArray[4][0]="�������䷽ʽ";
        iArray[4][1]="50px";
        iArray[4][2]=80;
        iArray[4][3]=2;
        iArray[4][4]="subriskacccodepayname";
        iArray[4][15]="PayPlanCode";
        iArray[4][17]="2";

        AccTriggerGrid = new MulLineEnter( "fm" , "AccTriggerGrid" );
        AccTriggerGrid.mulLineCount=0;
        AccTriggerGrid.hiddenPlus=1;
        AccTriggerGrid.hiddenSubtraction=1;
        AccTriggerGrid.displayTitle=1;
        AccTriggerGrid.loadMulLine(iArray);
    
    }
    catch(ex)
    {
        alert("��LCGrpAccTriggerInit.jsp-->InitAccTriggerGrid�����з����쳣:��ʼ���������!");
    }
}
  
</script> 
