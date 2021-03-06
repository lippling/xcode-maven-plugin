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

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProjectHelper;
import org.codehaus.plexus.archiver.manager.ArchiverManager;

/**
 * Packages all the artifacts for Xcode libraries and prepares the generated artifacts for deployment.
 * 
 * @goal xcode-package
 * 
 */
public class XCodePackageMojo extends AbstractXCodeMojo
{

  /**
   * @component role="org.codehaus.plexus.archiver.manager.ArchiverManager"
   * @required
   */
  private ArchiverManager archiverManager;

  /**
   * @component
   */
  private MavenProjectHelper projectHelper;

  /**
   * @parameter
   */
  private Set<String> bundles;

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException
  {

    if (bundles == null)
      bundles = new HashSet<String>();

    bundles.add(project.getArtifactId());

    try {

      new XCodePackageManager(getLog(), archiverManager, projectHelper).packageArtifacts(getXCodeCompileDirectory(),
            getConfigurations(),
            getSDKs(), project, bundles);
    }
    catch (IOException ex) {
      throw new MojoExecutionException("", ex);
    }
  }
}
