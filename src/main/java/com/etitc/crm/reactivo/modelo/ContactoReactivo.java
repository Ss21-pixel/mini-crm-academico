
package com.etitc.crm.reactivo.modelo;

/**
 * Modelo utilizado para demostrar programación reactiva
 * con contactos del CRM académico.
 */
public class ContactoReactivo {

    private Long id;
    private String nombre;
    private String programa;
    private String estado;

    public ContactoReactivo(Long id, String nombre,
                            String programa, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.programa = programa;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPrograma() {
        return programa;
    }

    public String getEstado() {
        return estado;
    }

    public boolean isActivo() {
        return "ACTIVO".equalsIgnoreCase(estado);
    }

    @Override
    public String toString() {
        return "ContactoReactivo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", programa='" + programa + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}
