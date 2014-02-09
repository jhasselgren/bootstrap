/*
 * Copyright 2004-2013 SpringSource.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

eventCreateWarStart = { String name, File stagingDir ->

	String stagingTemplatesDir = "$stagingDir/WEB-INF/templates"
	String stagingScaffoldDir = "$stagingTemplatesDir/scaffolding"

	system.println ( "StatusUpdate", ["... ${stagingTemplatesDir},  ${stagingScaffoldDir} "])
	
	// copy the default templates
	copyTemplates stagingTemplatesDir, true

	// then overwrite with customized templates if they exist
	if (new File(basedir, "src/templates/scaffolding").exists()) {
		ant.mkdir(dir: stagingScaffoldDir)
		ant.copy(todir: stagingScaffoldDir, overwrite: true) {
			fileset(dir: "$basedir/src/templates/scaffolding")
		}
	}
}

eventPluginInstalled = { String pluginName ->

		if(pluginName.toLowerCase().contains("plugin-bootstrap")){
			println "Installing plugin-bootstrap"
			
			String templatesDir = "$basedir/src/templates"
			
			println "templatesDir = $templatesDir"
			copyTemplates templatesDir, true
		}
}

eventInstallTemplates = { boolean overwrite ->

	String templatesDir = "$basedir/src/templates"

	copyTemplates templatesDir, overwrite
	/*
	ant.mkdir(dir: "$templatesDir/artifacts")
	ant.copy(todir: "$templatesDir/artifacts", verbose: true) {
		fileset(dir: "$pluginBootstrapPluginDir/src/templates/artifacts")
	}
	*/
}

void copyTemplates(String templatesDir, boolean overwrite) {
	println "creates dir $templatesDir/scaffolding"
	ant.mkdir(dir: "$templatesDir/scaffolding")
	
	println "copy dir $pluginBootstrapPluginDir/src/templates/scaffolding"
	
	ant.copy(todir: "$templatesDir/scaffolding", verbose: true, overwrite: overwrite) {
		fileset(dir: "$pluginBootstrapPluginDir/src/templates/scaffolding")
	}
}