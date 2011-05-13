package ajaxGails

class FilhoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [filhoInstanceList: Filho.list(params), filhoInstanceTotal: Filho.count()]
    }

    def create = {
        def filhoInstance = new Filho()
        filhoInstance.properties = params
        return [filhoInstance: filhoInstance]
    }

    def save = {
        def filhoInstance = new Filho(params)
        if (filhoInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'filho.label', default: 'Filho'), filhoInstance.id])}"
            redirect(action: "show", id: filhoInstance.id)
        }
        else {
            render(view: "create", model: [filhoInstance: filhoInstance])
        }
    }

    def show = {
        def filhoInstance = Filho.get(params.id)
        if (!filhoInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'filho.label', default: 'Filho'), params.id])}"
            redirect(action: "list")
        }
        else {
            [filhoInstance: filhoInstance]
        }
    }

    def edit = {
        def filhoInstance = Filho.get(params.id)
        if (!filhoInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'filho.label', default: 'Filho'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [filhoInstance: filhoInstance]
        }
    }

    def update = {
        def filhoInstance = Filho.get(params.id)
        if (filhoInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (filhoInstance.version > version) {
                    
                    filhoInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'filho.label', default: 'Filho')] as Object[], "Another user has updated this Filho while you were editing")
                    render(view: "edit", model: [filhoInstance: filhoInstance])
                    return
                }
            }
            filhoInstance.properties = params
            if (!filhoInstance.hasErrors() && filhoInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'filho.label', default: 'Filho'), filhoInstance.id])}"
                redirect(action: "show", id: filhoInstance.id)
            }
            else {
                render(view: "edit", model: [filhoInstance: filhoInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'filho.label', default: 'Filho'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def filhoInstance = Filho.get(params.id)
        if (filhoInstance) {
            try {
                filhoInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'filho.label', default: 'Filho'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'filho.label', default: 'Filho'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'filho.label', default: 'Filho'), params.id])}"
            redirect(action: "list")
        }
    }
}
