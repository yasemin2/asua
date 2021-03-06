/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are subject to the Mozilla Public License Version
 1.1 (the "License"); you may not use this file except in compliance with
 the License. You may obtain a copy of the License at
 http://www.mozilla.org/MPL/MPL-1.1.html
 
 Software distributed under the License is distributed on an "AS IS" basis,
 WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 for the specific language governing rights and limitations under the License.
 
 The Original Code is the "SensorML DataProcessing Engine".
 
 The Initial Developer of the Original Code is the VAST team at the University of Alabama in Huntsville (UAH). <http://vast.uah.edu> Portions created by the Initial Developer are Copyright (C) 2007 the Initial Developer. All Rights Reserved. Please Contact Mike Botts <mike.botts@uah.edu> for more information.
 
 Contributor(s): 
    Alexandre Robin <robin@nsstc.uah.edu>
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.cdm.common;

import net.opengis.swe.v20.DataBlock;
import net.opengis.swe.v20.DataComponent;



/**
 * <p>
 * Implement this interface to catch events coming from a DataParser
 * and sort, archive, display or process the decoded data.
 * </p>
 *
 * @author Alex Robin
 * @since Aug 12, 2005
 * */
public interface DataHandler
{
    public void beginDataAtom(DataComponent info);
    
    
    public void endDataAtom(DataComponent info, DataBlock data);


	public void startDataBlock(DataComponent info);


	public void endDataBlock(DataComponent info, DataBlock data);


	public void startData(DataComponent info);


	public void endData(DataComponent info, DataBlock data);
}
