
<%@ page import="ajaxGails.Neto" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'neto.label', default: 'Neto')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'neto.id.label', default: 'Id')}" />
                        
                            <th><g:message code="neto.filho.label" default="Filho" /></th>
                        
                            <g:sortableColumn property="nome" title="${message(code: 'neto.nome.label', default: 'Nome')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${netoInstanceList}" status="i" var="netoInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${netoInstance.id}">${fieldValue(bean: netoInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: netoInstance, field: "filho")}</td>
                        
                            <td>${fieldValue(bean: netoInstance, field: "nome")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${netoInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
