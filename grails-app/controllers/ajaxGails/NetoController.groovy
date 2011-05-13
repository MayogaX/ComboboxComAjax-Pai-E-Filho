package ajaxGails
import grails.converters.JSON

class NetoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }
    
    def chamaFilho = {
        def pai = Pai.id = params.pai
        pai = Pai.findById(pai)
        //def pai = Pai,i.get(params.pai)
        def pais = Filho.findAllByPai(pai)
        pais = pais.collect {
        [id:it.id, name:it.nome]
        }
        render pais as JSON
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [netoInstanceList: Neto.list(params), netoInstanceTotal: Neto.count()]
    }

    def create = {
        def netoInstance = new Neto()
        netoInstance.properties = params
        return [netoInstance: netoInstance]
    }

    def save = {
        def netoInstance = new Neto(params)
        if (netoInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'neto.label', default: 'Neto'), netoInstance.id])}"
            redirect(action: "show", id: netoInstance.id)
        }
        else {
            render(view: "create", model: [netoInstance: netoInstance])
        }
    }

    def show = {
        def netoInstance = Neto.get(params.id)
        if (!netoInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'neto.label', default: 'Neto'), params.id])}"
            redirect(action: "list")
        }
        else {
            [netoInstance: netoInstance]
        }
    }

    def edit = {
        def netoInstance = Neto.get(params.id)
        if (!netoInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'neto.label', default: 'Neto'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [netoInstance: netoInstance]
        }
    }

    def update = {
        def netoInstance = Neto.get(params.id)
        if (netoInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (netoInstance.version > version) {
                    
                    netoInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'neto.label', default: 'Neto')] as Object[], "Another user has updated this Neto while you were editing")
                    render(view: "edit", model: [netoInstance: netoInstance])
                    return
                }
            }
            netoInstance.properties = params
            if (!netoInstance.hasErrors() && netoInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'neto.label', default: 'Neto'), netoInstance.id])}"
                redirect(action: "show", id: netoInstance.id)
            }
            else {
                render(view: "edit", model: [netoInstance: netoInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'neto.label', default: 'Neto'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def netoInstance = Neto.get(params.id)
        if (netoInstance) {
            try {
                netoInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'neto.label', default: 'Neto'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'neto.label', default: 'Neto'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'neto.label', default: 'Neto'), params.id])}"
            redirect(action: "list")
        }
    }
}
