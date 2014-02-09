<%=packageName%>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="\${message(code: '${domainClass.propertyName}.label', default: '${className}')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      		<div class="container">
        		<div class="navbar-header">
          			<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
			            <span class="sr-only">Toggle navigation</span>
			            <span class="icon-bar"></span>
			            <span class="icon-bar"></span>
			            <span class="icon-bar"></span>
          			</button>
          			<a class="navbar-brand" href="\${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
          			<g:link class="navbar-brand" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link>
		            <g:link class="navbar-brand" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link>
        		</div>
        	</div>
    	</div>
		<div class="container">
			<div class="row">
				<div class="jumbotron">
	            	<h1><g:message code="default.edit.label" args="[entityName]" /></h1>
		            <p>This is an example to show the potential of an offcanvas layout pattern in Bootstrap. Try some responsive-range viewport sizes to see it in action.</p>
	          	</div>
	          	<div class="row">
	          		<div id="edit-${domainClass.propertyName}" class="col-xs-12" role="main">
						<g:if test="\${flash.message}">
						<div class="alert alert-danger">\${flash.message}</div>
						</g:if>
						<g:hasErrors bean="\${${propertyName}}">
							<g:eachError bean="\${${propertyName}}" var="error">
							<div class="alert alert-danger">
								<g:message error="\${error}"/>
							</div>
							</g:eachError>
						</g:hasErrors>
						<g:form url="[resource:${propertyName}, action:'update']" method="PUT" <%= multiPart ? ' enctype="multipart/form-data"' : '' %>>
							<g:hiddenField name="version" value="\${${propertyName}?.version}" />
							<fieldset class="form">
								<g:render template="form"/>
							</fieldset>
							<fieldset class="buttons">
								<g:actionSubmit class="btn btn-default" action="update" value="\${message(code: 'default.button.update.label', default: 'Update')}" />
							</fieldset>
						</g:form>
					</div>
				</div>
        	</div>
       	</div>
	</body>
</html>
