package com.sinosoft.lis.pubfun;
import org.apache.log4j.Logger;

import org.jdom.Element;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author chl
 * @version 1.0
 */
public class ElementLis
    extends Element
{
private static Logger logger = Logger.getLogger(ElementLis.class);

  public ElementLis(String name)
  {
    super(name);
  }

  public Element setText(String text)
  {
    final String nullstr="null";
    if(text==null||nullstr.equalsIgnoreCase(text))text="";
    super.setText(text);
    return null;
  }

}
