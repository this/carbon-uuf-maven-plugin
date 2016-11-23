/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.uuf.maven.util;

import org.apache.maven.plugin.MojoExecutionException;
import org.codehaus.plexus.archiver.Archiver;
import org.codehaus.plexus.archiver.util.DefaultFileSet;
import org.codehaus.plexus.archiver.zip.ZipArchiver;

import java.io.File;
import java.io.IOException;

/**
 * Utility for zip archive associated operations.
 *
 * @since 1.0.0
 */
public class ZipCreator {

    /**
     * Creates a zip archive.
     *
     * @param sourceDirectoryPath path to directory which has the files and sub-directories that need to be included in
     *                            the creating zip archive
     * @param baseDirectoryName   base directory of the creating zip archive, pass {@code null} for no base directory
     * @param outputDirectoryPath path to the directory where the zip archive is created
     * @param archiveFileName     filename of the creating zip archive without the ".zip" extension
     * @throws MojoExecutionException if an error occurres during the archive creation process
     */
    public static void createArchive(String sourceDirectoryPath, String baseDirectoryName, String outputDirectoryPath,
                                     String archiveFileName) throws MojoExecutionException {
        Archiver zipArchiver = new ZipArchiver();
        DefaultFileSet fileSet = new DefaultFileSet(new File(sourceDirectoryPath));
        if (baseDirectoryName != null) {
            fileSet.setPrefix(baseDirectoryName.endsWith("/") ? baseDirectoryName : (baseDirectoryName + "/"));
        }
        fileSet.setExcludes(null);
        zipArchiver.addFileSet(fileSet);

        File outputZipFile = new File(outputDirectoryPath, (archiveFileName + ".zip"));
        zipArchiver.setDestFile(outputZipFile);
        try {
            zipArchiver.createArchive();
        } catch (IOException e) {
            throw new MojoExecutionException("Cannot create zip archive '" + outputZipFile.getPath() +
                                                     "' from directory '" + sourceDirectoryPath + "'.", e);
        }
    }
}
