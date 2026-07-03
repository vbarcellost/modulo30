/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package execption;

public class TipoChaveNaoEncontradaException extends Exception {

    public TipoChaveNaoEncontradaException(String message) {
        super(message);
    }

    public TipoChaveNaoEncontradaException(String message, Exception e) {
        super(message, e);
    }
}
 