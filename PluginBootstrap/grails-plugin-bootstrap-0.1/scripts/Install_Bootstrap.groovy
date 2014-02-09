includeTargets << grailsScript("_GrailsInit")

def code = "confirm.PluginBootstrap"
def confirmCount = 0
def confirmAll = false
def deleteAll = false

target(PluginBootstrap: "Installs the PluginBootstrap scaffolding templates and other files") {
	depends(checkVersion, parseArguments)
	
	
	
	sourceDir = "${PluginBootstrapPluginDir}/src"
	targetDir = "${basedir}/grails-app/conf/"
	
	event "StatusUpdate", ['Source: ' + sourceDir + ', target: ' + targetDir]
	
	// copy scaffolding files into project
	sourceDir = "${PluginBootstrapPluginDir}/src/templates/"
	targetDir = "${basedir}/src/templates/"
	copy(sourceDir, targetDir, "scaffolding templates", code)
	
	
	// copy view files into project
	sourceDir = "${PluginBootstrapPluginDir}/grails-app/views"
	targetDir = "${basedir}/grails-app/views"
	ant.move(file: targetDir+'/index.gsp', tofile: targetDir+'/old_index.gsp', quiet: true, failonerror: false)
	ant.move(file: targetDir+'/error.gsp', tofile: targetDir+'/old_error.gsp', quiet: true, failonerror: false)
	ant.move(file: targetDir+'/layouts/main.gsp', tofile: targetDir+'/layouts/old_main.gsp', quiet: true, failonerror: false)
	// ant.move(file: targetDir+'/layouts/main.gsp', tofile: targetDir+'/old_main.gsp')
	copy(sourceDir, targetDir, "layouts & base GSPs files", code)
	
	event "StatusUpdate", ["PluginBootstrap install ended!"]
	
}





/****************************************************
 * Helper methods to copy directories, files, etc. *
 ****************************************************/
 
 copy = {String source, String target, String confirmText, String confirmCode ->
 def overwrite = confirmAll ? true : false
 // def newCode = confirmCode + confirmCount++
 def input = ""
 
 // only if directory already exists, ask to overwrite it
 if (new File(target).exists()) {
 if (isInteractive && !overwrite) input = grailsConsole.userInput('Overwrite '+confirmText+'? ', ["y","n","a"] as String[])
 if (!isInteractive || input == "y" || input == "a" )	overwrite = true
 if (input == "a")	confirmAll = true
 } else {
 ant.mkdir(dir: target)
 overwrite = true	// nothing to overwrite but will be copied (state this in the event message)
 }
 
 if (new File(source).isDirectory()) ant.copy(todir: "$target", overwrite: overwrite) { fileset dir: "$source" }
 else ant.copy(todir: "$target", overwrite: overwrite) { fileset file: "$source" }
 
 event "StatusUpdate", ["... ${confirmText} ${overwrite ? '' : 'not '}installed!"]
 }
 
 delete = {String file, String confirmText, String confirmCode ->
	 def deleteFile = deleteAll ? true : false
	 // def newCode = confirmCode + confirmCount++
	 def input = ""
	 
	 if (new File(file).exists()) {
		 if (isInteractive && !deleteFile) input = grailsConsole.userInput('Delete '+confirmText+'? ', ["y","n","a"] as String[])
		 if (!isInteractive || input == "y" || input == "a" )	deleteFile = true
		 if (input == "a")	deleteAll = true
		 if (deleteFile)	ant.delete(file: file)
		 event "StatusUpdate", ["... ${confirmText} was ${delete ? '' : 'not '}deleted!"]
	 }
 }