package ajaxGails

class Filho {
    int id
    String nome
    Pai pai
    static constraints = {
    }
    String toString()
    {
        return nome
    }
}
