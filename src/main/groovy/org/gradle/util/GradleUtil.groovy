/*
 * Copyright 2007 the original author or authors.
 *
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
 */

package org.gradle.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * @author Hans Dockter
 */
class GradleUtil {
    private static Logger logger = LoggerFactory.getLogger(GradleUtil)
    
    static def configure(Closure configureClosure, def delegate, int resolveStrategy = Closure.DELEGATE_FIRST) {
        if (!configureClosure) { return delegate}
        configureClosure.resolveStrategy = resolveStrategy
        configureClosure.delegate = delegate
        configureClosure.call()
        delegate
    }

    static void deleteDir(File dir) {
        assert !dir.isFile()
        if (dir.isDirectory()) {new AntBuilder().delete(dir: dir)}
    }

    static File makeNewDir(File dir) {
        deleteDir(dir)
        dir.mkdir()
        dir.deleteOnExit()
        dir
    }

    static File[] getGradleClasspath() {
        File gradleHomeLib = new File(System.properties["gradle.home"] + "/lib")
        if (gradleHomeLib.isDirectory()) {
            return gradleHomeLib.listFiles()
        }
        []
    }
}
