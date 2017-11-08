/**
 * Copyright (c) 2008 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.ibrms.bom;
import org.apache.log4j.Logger;
  
public class AbstractBOM {
private static Logger logger = Logger.getLogger(AbstractBOM.class);
  
  public String getV(String code){
      return null;
    }

  public boolean setV(String code,String value){
      return true;
    }

  public AbstractBOM getFatherBOM(){
      return null;
    }

}
