package com.sap.prd.mobile.ios.mios;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.apache.commons.io.IOUtils;
import org.apache.maven.it.Verifier;
import org.junit.Test;

public class SpecificTargetTest extends XCodeTest
{
  @Test
  public void buildSpecificTarget() throws Exception
  {
    final String testName = Thread.currentThread().getStackTrace()[1].getMethodName();
    final File remoteRepositoryDirectory = getRemoteRepositoryDirectory(getClass()
      .getName());
    prepareRemoteRepository(remoteRepositoryDirectory);
    
    Map<String, String> additionalSystemProperties = new HashMap<String, String>();
    additionalSystemProperties.put("xcode.app.defaultConfigurations", "Release");
    additionalSystemProperties.put("xcode.app.defaultSdks", "iphoneos");
    additionalSystemProperties.put("xcode.target", "Target2");
    
    Verifier verifier = test(testName, new File(getTestRootDirectory(), "multiple-targets/MultipleTargets"), "pom.xml", "compile", THE_EMPTY_LIST, additionalSystemProperties,
          remoteRepositoryDirectory);
    assertCorrectTargetBuild(new File(verifier.getBasedir(),
          verifier.getLogFileName()));
  }
  
  private void assertCorrectTargetBuild(File logFile) throws IOException
  {
    BufferedReader reader = new BufferedReader(new FileReader(logFile));
    try {
      String line;
      boolean target1Built = false;
      boolean target2Built = false;
      while ((line = reader.readLine()) != null)
      {
        target1Built |= line.equals("=== BUILD NATIVE TARGET Target1 OF PROJECT MultipleTargets WITH CONFIGURATION Release ===");
        target2Built |= line.equals("=== BUILD NATIVE TARGET Target2 OF PROJECT MultipleTargets WITH CONFIGURATION Release ===");
      }
      Assert.assertFalse("Target1 must not be built", target1Built);
      Assert.assertTrue("Target2 must be built", target2Built);
    } finally {
      IOUtils.closeQuietly(reader);
    }
  }
}
