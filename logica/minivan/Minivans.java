package logica.minivan;

import logica.exception.EntidadYaExisteException;
import logica.paseo.Paseos;

class NodoAVL {
    String matricula;
    Minivan minivan;
    int altura;
    NodoAVL izquierda, derecha;

    public NodoAVL(Minivan minivan) {
        this.matricula = minivan.getMatricula();
        this.minivan = minivan;
        this.altura = 1;
    }
}

public class Minivans {
    private NodoAVL raiz;

    private int altura(NodoAVL nodo) {
        return (nodo == null) ? 0 : nodo.altura;
    }

    private int balance(NodoAVL nodo) {
        return (nodo == null) ? 0 : altura(nodo.izquierda) - altura(nodo.derecha);
    }

    private NodoAVL rotacionDerecha(NodoAVL y) {
        NodoAVL x = y.izquierda;
        y.izquierda = x.derecha;
        x.derecha = y;
        y.altura = Math.max(altura(y.izquierda), altura(y.derecha)) + 1;
        x.altura = Math.max(altura(x.izquierda), altura(x.derecha)) + 1;
        return x;
    }

    private NodoAVL rotacionIzquierda(NodoAVL x) {
        NodoAVL y = x.derecha;
        x.derecha = y.izquierda;
        y.izquierda = x;
        x.altura = Math.max(altura(x.izquierda), altura(x.derecha)) + 1;
        y.altura = Math.max(altura(y.izquierda), altura(y.derecha)) + 1;
        return y;
    }

    private NodoAVL insertar(NodoAVL nodo, Minivan minivan) throws EntidadYaExisteException {
        if (nodo == null) return new NodoAVL(minivan);

        int cmp = minivan.getMatricula().compareTo(nodo.matricula);
        if (cmp < 0)
            nodo.izquierda = insertar(nodo.izquierda, minivan);
        else if (cmp > 0)
            nodo.derecha = insertar(nodo.derecha, minivan);
        else
            throw new EntidadYaExisteException("La minivan con matrícula " + minivan.getMatricula() + " ya existe.");

        nodo.altura = 1 + Math.max(altura(nodo.izquierda), altura(nodo.derecha));

        int balance = balance(nodo);
        if (balance > 1 && minivan.getMatricula().compareTo(nodo.izquierda.matricula) < 0) return rotacionDerecha(nodo);
        if (balance < -1 && minivan.getMatricula().compareTo(nodo.derecha.matricula) > 0) return rotacionIzquierda(nodo);
        if (balance > 1 && minivan.getMatricula().compareTo(nodo.izquierda.matricula) > 0) {
            nodo.izquierda = rotacionIzquierda(nodo.izquierda);
            return rotacionDerecha(nodo);
        }
        if (balance < -1 && minivan.getMatricula().compareTo(nodo.derecha.matricula) < 0) {
            nodo.derecha = rotacionDerecha(nodo.derecha);
            return rotacionIzquierda(nodo);
        }

        return nodo;
    }

    public void insertarMinivan(VOMinivan voMinivan) throws EntidadYaExisteException {
        Minivan nuevaMinivan = new Minivan(voMinivan);
        raiz = insertar(raiz, nuevaMinivan);
    }

    private void listarMinivans(NodoAVL nodo, VOMinivan[] lista, int[] index) {
        if (nodo != null) {
            listarMinivans(nodo.izquierda, lista, index);
            lista[index[0]++] = nodo.minivan.getVO();
            listarMinivans(nodo.derecha, lista, index);
        }
    }

    public VOMinivan[] listarMinivans() {
        VOMinivan[] lista = new VOMinivan[getSize(raiz)];
        listarMinivans(raiz, lista, new int[]{0});
        return lista;
    }

    private int getSize(NodoAVL nodo) {
        return (nodo == null) ? 0 : 1 + getSize(nodo.izquierda) + getSize(nodo.derecha);
    }

    public Minivan buscarMinivan(String matricula) {
        NodoAVL actual = raiz;
        while (actual != null) {
            int cmp = matricula.compareTo(actual.matricula);
            if (cmp == 0) return actual.minivan;
            actual = (cmp < 0) ? actual.izquierda : actual.derecha;
        }
        return null;
    }
}
