package com.sinosoft.lis.acc;
import org.apache.log4j.Logger;
/**
*投连保全帐户备份
 */
import com.sinosoft.utility.CError;
import com.sinosoft.utility.Reflections;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;

public class AccAndClassBak {
private static Logger logger = Logger.getLogger(AccAndClassBak.class);
	

    
	LCInsureAccSet _LCInsureAccSet = new LCInsureAccSet();
    LPInsureAccSet _LPInsureAccSet = new LPInsureAccSet();
    
    LCInsureAccClassSet _LCInsureAccClassSet = new LCInsureAccClassSet();
    LPInsureAccClassSet _LPInsureAccClassSet = new LPInsureAccClassSet();
    
    LCInsureAccFeeSet _LCInsureAccFeeSet = new LCInsureAccFeeSet();
    LPInsureAccFeeSet _LPInsureAccFeeSet = new LPInsureAccFeeSet();
    
    LCInsureAccClassFeeSet _LCInsureAccClassFeeSet = new LCInsureAccClassFeeSet();
    LPInsureAccClassFeeSet _LPInsureAccClassFeeSet = new LPInsureAccClassFeeSet();
    
    private MMap map = new MMap();
    String CurrentDate = PubFun.getCurrentDate();
    String CurrentTime = PubFun.getCurrentTime();
    
    LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
    

    Reflections tReflections = new Reflections();

    public AccAndClassBak() {
    }

    public void setEdorNo(String tEdorNo) {

    	LPEdorItemDB lzLPEdorItemDB = new LPEdorItemDB();
    	lzLPEdorItemDB.setEdorNo(tEdorNo);
    	mLPEdorItemSchema = lzLPEdorItemDB.query().get(1);
    }
    public LPInsureAccSet getLPInsureAccSet()
    {
    	LCInsureAccDB lzLCInsureAccDB = new LCInsureAccDB();
    	lzLCInsureAccDB.setContNo(mLPEdorItemSchema.getContNo());
    	_LCInsureAccSet = lzLCInsureAccDB.query();
    	for(int i=1;i<=_LCInsureAccSet.size();i++)
    	{
    		LPInsureAccSchema lzLPInsureAccSchema = new LPInsureAccSchema();
    		tReflections.transFields(lzLPInsureAccSchema, _LCInsureAccSet.get(i));
    		lzLPInsureAccSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
    		lzLPInsureAccSchema.setEdorType(mLPEdorItemSchema.getEdorType());
    		lzLPInsureAccSchema.setMakeDate(CurrentDate);
    		lzLPInsureAccSchema.setMakeTime(CurrentTime);
    		lzLPInsureAccSchema.setModifyDate(CurrentDate);
    		lzLPInsureAccSchema.setModifyTime(CurrentTime);
    		_LPInsureAccSet.add(lzLPInsureAccSchema);   
    		logger.debug("lzLPInsureAccSchema"+lzLPInsureAccSchema.encode());
    	}
    	return _LPInsureAccSet;
    }
    
    public LPInsureAccClassSet getLPInsureAccClassSet()
    {
    	LCInsureAccClassDB lzLCInsureAccClassDB = new LCInsureAccClassDB();
    	lzLCInsureAccClassDB.setContNo(mLPEdorItemSchema.getContNo());
    	_LCInsureAccClassSet = lzLCInsureAccClassDB.query();
    	for(int i=1;i<=_LCInsureAccClassSet.size();i++)
    	{
    		LPInsureAccClassSchema lzLPInsureAccClassSchema = new LPInsureAccClassSchema();
    		tReflections.transFields(lzLPInsureAccClassSchema, _LCInsureAccClassSet.get(i));
    		lzLPInsureAccClassSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
    		lzLPInsureAccClassSchema.setEdorType(mLPEdorItemSchema.getEdorType());
    		lzLPInsureAccClassSchema.setMakeDate(CurrentDate);
    		lzLPInsureAccClassSchema.setMakeTime(CurrentTime);
    		lzLPInsureAccClassSchema.setModifyDate(CurrentDate);
    		lzLPInsureAccClassSchema.setModifyTime(CurrentTime);
    		_LPInsureAccClassSet.add(lzLPInsureAccClassSchema);    	
    		logger.debug("lzLPInsureAccClassSchema"+lzLPInsureAccClassSchema.encode());
        	
    	}   
    	return _LPInsureAccClassSet;
    }
  
    public LPInsureAccClassFeeSet getLPInsureAccClassFeeSet()
    {
    	LCInsureAccClassFeeDB lzLCInsureAccClassFeeDB = new LCInsureAccClassFeeDB();
    	lzLCInsureAccClassFeeDB.setContNo(mLPEdorItemSchema.getContNo());
    	_LCInsureAccClassFeeSet = lzLCInsureAccClassFeeDB.query();
    	for(int i=1;i<=_LCInsureAccClassFeeSet.size();i++)
    	{
    		LPInsureAccClassFeeSchema lzLPInsureAccClassFeeSchema = new LPInsureAccClassFeeSchema();
    		tReflections.transFields(lzLPInsureAccClassFeeSchema, _LCInsureAccClassFeeSet.get(i));
    		lzLPInsureAccClassFeeSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
    		lzLPInsureAccClassFeeSchema.setEdorType(mLPEdorItemSchema.getEdorType());
    		lzLPInsureAccClassFeeSchema.setMakeDate(CurrentDate);
    		lzLPInsureAccClassFeeSchema.setMakeTime(CurrentTime);
    		lzLPInsureAccClassFeeSchema.setModifyDate(CurrentDate);
    		lzLPInsureAccClassFeeSchema.setModifyTime(CurrentTime);
    		_LPInsureAccClassFeeSet.add(lzLPInsureAccClassFeeSchema);    	
    		logger.debug("lzLPInsureAccClassFeeSchema"+lzLPInsureAccClassFeeSchema.encode());
        	
    	}   
    	return _LPInsureAccClassFeeSet;
    }
  
    
    
    public LPInsureAccFeeSet getLPInsureAccFeeSet()
    {
    	LCInsureAccFeeDB lzLCInsureAccFeeDB = new LCInsureAccFeeDB();
    	lzLCInsureAccFeeDB.setContNo(mLPEdorItemSchema.getContNo());
    	_LCInsureAccFeeSet = lzLCInsureAccFeeDB.query();
    	for(int i=1;i<=_LCInsureAccFeeSet.size();i++)
    	{
    		LPInsureAccFeeSchema lzLPInsureAccFeeSchema = new LPInsureAccFeeSchema();
    		tReflections.transFields(lzLPInsureAccFeeSchema, _LCInsureAccFeeSet.get(i));
    		lzLPInsureAccFeeSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
    		lzLPInsureAccFeeSchema.setEdorType(mLPEdorItemSchema.getEdorType());
    		lzLPInsureAccFeeSchema.setMakeDate(CurrentDate);
    		lzLPInsureAccFeeSchema.setMakeTime(CurrentTime);
    		lzLPInsureAccFeeSchema.setModifyDate(CurrentDate);
    		lzLPInsureAccFeeSchema.setModifyTime(CurrentTime);
    		_LPInsureAccFeeSet.add(lzLPInsureAccFeeSchema);   
    		logger.debug("lzLPInsureAccFeeSchema"+lzLPInsureAccFeeSchema.encode());
    	}
    	return _LPInsureAccFeeSet;
    }
    
    public static void main(String arg[]) {
    	
    	LPEdorItemDB lzLPEdorItemDB = new LPEdorItemDB();
    	LPEdorItemSchema lzLPEdorItemSchema = new LPEdorItemSchema();
    	lzLPEdorItemDB.setEdorNo("6020070927000001");
    	lzLPEdorItemSchema = lzLPEdorItemDB.query().get(1);
    	AccAndClassBak tAccAndClassBak = new AccAndClassBak();
    	logger.debug(lzLPEdorItemSchema.encode());
//    	tAccAndClassBak.BAK(lzLPEdorItemSchema);
    	
    	
    } 
    
}
