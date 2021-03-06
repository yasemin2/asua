/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2015 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

package net.opengis.gml.v32;


/**
 * POJO class for XML type LineStringType(@http://www.opengis.net/gml/3.2).
 *
 * This is a complex type.
 */
public interface LineString extends AbstractCurve
{
    
    /**
     * Gets the posList property
     * @return double array contanining all coordinates
     */
    public double[] getPosList();
    
    
    /**
     * Sets the posList property
     * @param posList double array containing all coordinates
     */
    public void setPosList(double[] posList); 
    
    
    /**
     * Checks if posList property is set
     * @return true is set 
     */
    public boolean isSetPosList();
}
