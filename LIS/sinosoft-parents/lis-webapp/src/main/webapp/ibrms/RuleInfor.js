
function formSubmit(flag)
{
    var RuleName=fm.RuleName.value;
    var RuleDes=fm.RuleDes.value;
    var Creator=fm.Creator.value;
    var RuleStartDate=fm.RuleStartDate.value;
    var RuleEndDate=fm.RuleEndDate.value;
    var TemplateLevel=fm.TemplateLevel.value;
    var Business=fm.Business.value;
    var State=fm.State.value;
    var Valid=fm.Valid.value;
    var RuleType = fm.RuleType.value;
    var errorMessage=''
    if(!(!!RuleName.trim()))
    {
    	errorMessage+='����<����>û����д,�뽫��������д������';
    }
    else
    {
    	if(Length(RuleName.trim())>100)
    	{
    		errorMessage+='����<����>����̫����󳤶���100����������д��';
    	}
    }
    if(!(!!RuleStartDate.trim()))
    {
    	errorMessage+='\n����<��Ч����>û����д,�뽫��Ч������д������';
    }
    if(!(!!Creator.trim()))
    {
    	errorMessage+='\n����<������>��ʼ����Ϣ����';
    }
    if(!(!!TemplateLevel.trim()))
    {
    	errorMessage+='\n����<����>��ʼ����Ϣ����';
    }
    if(!(!!Business.trim()))
    {
    	errorMessage+='\n����<ҵ��ģ��>��ʼ����Ϣ����';
    }
    if(!(!!State.trim()))
    {
    	errorMessage+='\n����<״̬>��ʼ����Ϣ����';
    }
    if(!(!!Valid.trim()))
    {
    	errorMessage+='\n����<��Ч��־>��ʼ����Ϣ����';
    }
    
     if(!(!!RuleType.trim()))
    {
    	errorMessage+='\n��ѡ��<��������>';
    }
    
   if(!!RuleDes)
    {
    	if(Length(RuleDes.trim())>500)
    	{
    		errorMessage+='\n���������ĳ���̫������󳤶���500����������д��';
    	}
    }
    if(errorMessage==''&&RuleType=='0')
    {
    	var url="./RuleMake.jsp?"+
    	"flag="+flag+
    	"&RuleName="+RuleName+
    	"&Creator="+Creator+
    	"&RuleStartDate="+RuleStartDate+
    	"&RuleEndDate="+RuleEndDate+
    	"&TempalteLevel="+TemplateLevel+
    	"&Business="+Business+
    	"&State="+State+
    	"&Valid="+Valid+
    	"&LRTemplate_ID="+LRTemplate_ID+
    	"&LRTemplateName="+LRTemplateName;
    	if(!!RuleDes)
    		url+="&RuleDes="+RuleDes;
    	window.location.href=url;
    }
    else if(errorMessage==''&&RuleType=='1')
    {
    	var url="./RuleMakeNew.jsp?"+
    	"flag="+flag+
    	"&RuleName="+RuleName+
    	"&Creator="+Creator+
    	"&RuleStartDate="+RuleStartDate+
    	"&RuleEndDate="+RuleEndDate+
    	"&TempalteLevel="+TemplateLevel+
    	"&Business="+Business+
    	"&State="+State+
    	"&Valid="+Valid+
    	"&LRTemplate_ID="+LRTemplate_ID+
    	"&LRTemplateName="+LRTemplateName;
    	if(!!RuleDes)
    		url+="&RuleDes="+RuleDes;
    	window.location.href=url;
    }
    else
    {
    	alert(errorMessage);
    }
    
}

function Length(str)
{
     var i,sum;
     sum=0;
     for(i=0;i<str.length;i++)
     {
         if ((str.charCodeAt(i)>=0) && (str.charCodeAt(i)<=255))
             sum=sum+1;
         else
             sum=sum+2;
     }
     return sum;
}

