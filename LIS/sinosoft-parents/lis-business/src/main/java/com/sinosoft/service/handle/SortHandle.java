package com.sinosoft.service.handle;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sinosoft.service.Handle;
import com.sinosoft.service.IMessage;
import com.sinosoft.utility.TransferData;
/**
 * <p>Title: 组合模式,实现责任</p>
 *
 * <p>Description: ejb转发设计</p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: sinosoft</p>
 *
 * @author litao
 * @version 1.0
 */
public class SortHandle implements Handle {
private static Logger logger = Logger.getLogger(SortHandle.class);

    private List handles = new LinkedList();
    public SortHandle() {
    }

    public void add(Handle handle)
    {
        handles.add(handle);
    }

    public void remove(Handle handle)
    {
        handles.remove(handle);
    }

    /**
     * invoke
     *
     * @todo Implement this com.sinosoft.ejb.Handle method
     */
    public void invoke(IMessage message) {
        Iterator  tIterator = handles.iterator();
        while(tIterator.hasNext())
        {
            Handle handle = (Handle)tIterator.next();
            handle.invoke(message);
        }
    }

	public void init(TransferData transfer) {		
	}
}