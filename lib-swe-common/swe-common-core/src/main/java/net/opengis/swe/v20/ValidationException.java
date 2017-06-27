/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2015 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

package net.opengis.swe.v20;


/**
 * <p>
 * Used for errors occuring when validating SWE Common component values
 * </p>
 *
 * @author Alex Robin
 * @since Nov 10, 2014
 */
public class ValidationException extends Exception
{
	public final static long serialVersionUID = 0;
	protected String locator;
	
	
	public ValidationException(String message)
	{
		super(message);
	}
	
	
	public ValidationException(String locator, String message)
	{
		super(message);
		this.locator = locator;
	}
	
	
	public ValidationException(Exception e)
	{
		super(e);
	}
	
	
	public ValidationException(String message, Exception e)
	{
		super(message, e);
	}
	
	
	public String getLocator()
	{
		return locator;
	}


	public void setLocator(String locator)
	{
		this.locator = locator;
	}
}
