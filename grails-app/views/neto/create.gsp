

<%@ page import="ajaxGails.Neto" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'neto.label', default: 'Neto')}" />
        <g:javascript library="jquery-1.5.1.min" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
        <script type="text/javascript">
          chamaFilhos = function() {
            //var parametros = $(form).find('INPUT,SELECT').serialize();
            var parametros = $("#selectPai").val();
            //alert(parametros);
            var option = "<option value='null'>-- SELECIONE --</option>";
            $.post('http://localhost:8080/ComboboxComAjax-Pai-E-Filho/neto/chamaFilho' function(data){
              alert("post");
              //$.each(eval("("+data +")"),function(tmp,obj) {
                //option += "<option value ='" + obj.id + "'>" + obj.name + "</option>";
              //});
                //$("#filho").html(option);
              });
          }
          </script>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${netoInstance}">
            <div class="errors">
                <g:renderErrors bean="${netoInstance}" as="list" />
            </div>
            </g:hasErrors>
            <form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="filho"><g:message code="neto.filho.label" default="Filho" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: netoInstance, field: 'filho', 'errors')}">
                                    <select name="filho.id" id="filho" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="pai"><g:message code="filho.pai.label" default="Pai" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: filhoInstance, field: 'pai', 'errors')}">
                                    <g:select name="pai.id" from="${ajaxGails.Pai.list()}" optionKey="id" id="selectPai" value="${filhoInstance?.pai?.id}" onChange="chamaFilhos();" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="nome"><g:message code="neto.nome.label" default="Nome" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: netoInstance, field: 'nome', 'errors')}">
                                    <g:textField name="nome" value="${netoInstance?.nome}"  />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </form>
        </div>
    </body>
</html>
