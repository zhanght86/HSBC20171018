<%
/**
 * Created by IntelliJ IDEA.
 * User: jinsh
 * Date: 2009-1-7
 * Time: 15:32:15
 * To change this template use File | Settings | File Templates.
 */

%>

<!--�û�У����-->

<script language="JavaScript">  

function initInpBox()
{

    try
    {
        fm.ActivityIDQ.value=QactivityID;
        fm.ActivityID.value=QactivityID;
        //showOneCodeName('queryactivityid','ActivityIDQ','ActivityNameQ');
        //showOneCodeName('queryactivityid','ActivityID','ActivityName');
    }
    catch(ex)
    {
        alert("��InvAssOrderbuildDetailInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
    }
}

function initForm()
{
    try
    {
        initInpBox();
        initTICollectGrid();
        queryClick();
        initQuery();
    }
    catch(re)
    {
        alert("InvAssOrderbuildDetailInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
} //�ɽ�����ܱ�
function initTICollectGrid()
{
    var iArray = new Array();
    var i = 0;
    try
    {
        iArray[i] = new Array();
        iArray[i][0] = "���";
        iArray[i][1] = "30px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;

        iArray[i] = new Array();
        iArray[i][0] = "Դ�ֶ�";
        iArray[i][1] = "80px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;

        iArray[i] = new Array();
        iArray[i][0] = "Դ�ֶ�����";
        iArray[i][1] = "80px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;

        iArray[i] = new Array();
        iArray[i][0] = "Ŀ���ֶ�";
        iArray[i][1] = "80px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;

        iArray[i] = new Array();
        iArray[i][0] = "Ŀ���ֶ�����";
        iArray[i][1] = "80px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;

        iArray[i] = new Array();
        iArray[i][0] = "ActivityID";
        iArray[i][1] = "0px";
        iArray[i][2] = 10;
        iArray[i++][3] = 3;

        iArray[i] = new Array();
        iArray[i][0] = "FieldOrder";
        iArray[i][1] = "0px";
        iArray[i][2] = 10;
        iArray[i++][3] = 3;

        iArray[i] = new Array();
        iArray[i][0] = "ActivityName";
        iArray[i][1] = "0px";
        iArray[i][2] = 10;
        iArray[i++][3] = 3;

        TICollectGrid = new MulLineEnter("fm", "TICollectGrid");
        //��Щ���Ա�����loadMulLineǰ
        TICollectGrid.mulLineCount = 3;
        TICollectGrid.displayTitle = 1;
        TICollectGrid.hiddenPlus = 1;
        TICollectGrid.hiddenSubtraction = 1;
        TICollectGrid.canSel = 1;
        TICollectGrid.selBoxEventFuncName = "showData";
        TICollectGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}


</script>
