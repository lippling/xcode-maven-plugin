/*
 * #%L
 * xcode-maven-plugin
 * %%
 * Copyright (C) 2012 SAP AG
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.sap.prd.mobile.ios.mios;

import java.io.File;

import junit.framework.Assert;

import org.junit.Test;

import com.sap.prd.mobile.ios.mios.FileUtils;

public class FileUtilsTest
{

  @Test
  public void testStraightForward() throws Exception
  {

    Assert.assertEquals("source/dir",
          FileUtils.getDelta(new File("/home/abc/def"), new File("/home/abc/def/source/dir")));
  }

  @Test(expected = IllegalStateException.class)
  public void testNoCommonPath() throws Exception
  {
    FileUtils.getDelta(new File("/home/abc"), new File("/home/def"));
  }
}
