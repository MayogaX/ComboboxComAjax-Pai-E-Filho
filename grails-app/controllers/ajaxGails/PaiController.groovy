package ajaxGails

class PaiController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [paiInstanceList: Pai.list(params), paiInstanceTotal: Pai.count()]
    }

    def create = {
        def paiInstance = new Pai()
        paiInstance.properties = params
        return [paiInstance: paiInstance]
    }

    def save = {
        def paiInstance = new Pai(params)
        if (paiInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'pai.label', default: 'Pai'), paiInstance.id])}"
            redirect(action: "show", id: paiInstance.id)
        }
        else {
            render(view: "create", model: [paiInstance: paiInstance])
        }
    }

    def show = {
        def paiInstance = Pai.get(params.id)
        if (!paiInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'pai.label', default: 'Pai'), params.id])}"
            redirect(action: "list")
        }
        else {
            [paiInstance: paiInstance]
        }
    }

    def edit = {
        def paiInstance = Pai.get(params.id)
        if (!paiInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'pai.label', default: 'Pai'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [paiInstance: paiInstance]
        }
    }

    def update = {
        def paiInstance = Pai.get(params.id)
        if (paiInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (paiInstance.version > version) {
                    
                    paiInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'pai.label', default: 'Pai')] as Object[], "Another user has updated this Pai while you were editing")
                    render(view: "edit", model: [paiInstance: paiInstance])
                    return
                }
            }
            paiInstance.properties = params
            if (!paiInstance.hasErrors() && paiInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'pai.label', default: 'Pai'), paiInstance.id])}"
                redirect(action: "show", id: paiInstance.id)
            }
            else {
                render(view: "edit", model: [paiInstance: paiInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'pai.label', default: 'Pai'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def paiInstance = Pai.get(params.id)
        if (paiInstance) {
            try {
                paiInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'pai.label', default: 'Pai'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'pai.label', default: 'Pai'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'pai.label', default: 'Pai'), params.id])}"
            redirect(action: "list")
        }
    }
}
