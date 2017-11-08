
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
    	errorMessage+='规则<名称>没有填写,请将规则名填写完整！';
    }
    else
    {
    	if(Length(RuleName.trim())>100)
    	{
    		errorMessage+='规则<名称>长度太大，最大长度是100，请重新填写！';
    	}
    }
    if(!(!!RuleStartDate.trim()))
    {
    	errorMessage+='\n规则<生效日期>没有填写,请将生效日期填写完整！';
    }
    if(!(!!Creator.trim()))
    {
    	errorMessage+='\n规则<创建者>初始化信息出错！';
    }
    if(!(!!TemplateLevel.trim()))
    {
    	errorMessage+='\n规则<级别>初始化信息出错！';
    }
    if(!(!!Business.trim()))
    {
    	errorMessage+='\n规则<业务模块>初始化信息出错！';
    }
    if(!(!!State.trim()))
    {
    	errorMessage+='\n规则<状态>初始化信息出错！';
    }
    if(!(!!Valid.trim()))
    {
    	errorMessage+='\n规则<有效标志>初始化信息出错！';
    }
    
     if(!(!!RuleType.trim()))
    {
    	errorMessage+='\n请选择<规则类型>';
    }
    
   if(!!RuleDes)
    {
    	if(Length(RuleDes.trim())>500)
    	{
    		errorMessage+='\n规则描述的长度太长，最大长度是500，请重新填写！';
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

