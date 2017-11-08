var fieldSernum = 0;	
var timeoutID_Tab;
var oldcolor = "";
var fieldArray = new Array();
var ScrollFlag = 0;
var RollSpeedOperator = 0;
var RollSpeedSelf = 0;
var RollSpeedBase = 0;
function initAutoValue(mFieldArray,mRollSpeedOperator,mRollSpeedSelf,mRollSpeedBase)
{
    fieldArray = mFieldArray;
    RollSpeedOperator = mRollSpeedOperator;
    RollSpeedSelf = mRollSpeedSelf;
    RollSpeedBase = mRollSpeedBase;
	if(ScrollFlag==0)
	{
	    ScrollFlag=1;
	}
    else 
    {
        ScrollFlag=0;
    }
	TabAutoScroll();
}

function TabAutoScroll()
{
	var tLength = 0;
	var tRollSpeed = RollSpeedOperator;

	try 
	{
	    if(fieldSernum<fieldArray.length && ScrollFlag==1) 
		{
			try
			{
    			if (fieldSernum > 0)
    			{
    				if(oldcolor == "multiDatePickerLeft")
    				{
    					eval("fm.Show"+fieldArray[fieldSernum - 1]+".className=\"" + oldcolor + "\";");
    				}
    				 else
    				{
    			    eval("fm."+fieldArray[fieldSernum - 1]+".className=\"" + oldcolor + "\";");
    				}
    			}
    		    else if (fieldSernum == 0 && oldcolor != "")
    		    {
    		        eval("fm."+fieldArray[fieldArray.length - 1]+".className=\"" + oldcolor + "\";");
    		    }        	
    			eval("fm."+fieldArray[fieldSernum]+".focus()");
    
    	    	oldcolor=eval("fm." + fieldArray[fieldSernum] + ".className");
    			if(eval("fm."+fieldArray[fieldSernum]+".className") == "codeno")
        		{
        		 	strcolor="fm."+fieldArray[fieldSernum]+".className=\"warnno\";";	
        		}	
        	    else if(eval("fm."+fieldArray[fieldSernum]+".className") == "common3")
        	  {
        			strcolor="fm."+fieldArray[fieldSernum]+".className=\"warn3\";";	
        		}
        		  else if(eval("fm."+fieldArray[fieldSernum]+".className") == "multiDatePicker")
        	  {
        	  	oldcolor = "multiDatePickerLeft";
        			strcolor="fm.Show"+fieldArray[fieldSernum]+".className=\"warn\";";	
        		}
        		  else 
        	    {
        			strcolor="fm."+fieldArray[fieldSernum]+".className=\"warn\";";	
        		}
           		eval(strcolor);
           		tLength = eval("fm."+fieldArray[fieldSernum]+".value.length");
    			tIntervalTime = tRollSpeed * RollSpeedSelf + tLength*RollSpeedBase;
    			timeoutID_Tab=setTimeout("TabAutoScroll()",tIntervalTime);
    		    fieldSernum++; 
    		    if(fieldSernum==fieldArray.length)
    		    {
    		        fieldSernum=0;
    		    }
           	}
           	catch(ex)
           	{
           	    fieldSernum++; 
    		    if(fieldSernum==fieldArray.length)
    		    {
    		        fieldSernum=0;
    		    }
    		    TabAutoScroll();
        	}
		}
	    else
	    {
	        if (oldcolor != "" && fieldSernum > 0)
		    {
		        eval("fm."+fieldArray[fieldSernum - 1]+".className=\"" + oldcolor + "\";");
		    }
		    else if (fieldSernum == 0 && oldcolor != "")
		    {
		        eval("fm."+fieldArray[fieldArray.length - 1]+".className=\"" + oldcolor + "\";");
		    }     
	        clearTimeout(timeoutID_Tab);
	    }
	}
	catch (ex)
	{
	    alert("·¢Éú´íÎó!");
		clearTimeout(timeoutID_Tab);
	}
}