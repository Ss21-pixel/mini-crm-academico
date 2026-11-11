/* =========================================
   MINI CRM ACADEMICO - CONTACTOS
   ========================================= */

document.addEventListener("DOMContentLoaded", function () {

    // Confirmar antes de eliminar un contacto.
    const formulariosEliminar = document.querySelectorAll(
        ".formulario-eliminar-contacto"
    );

    formulariosEliminar.forEach(function (formulario) {
        formulario.addEventListener("submit", function (evento) {
            const confirmado = window.confirm(
                "¿Estás seguro de que deseas eliminar este contacto? Esta acción no se puede deshacer."
            );

            if (!confirmado) {
                evento.preventDefault();
            }
        });
    });

    // Evitar envíos repetidos al guardar el formulario.
    const formularioContacto = document.querySelector(
        "#formularioContacto"
    );

    if (formularioContacto) {
        formularioContacto.addEventListener("submit", function (evento) {
            if (!formularioContacto.checkValidity()) {
                evento.preventDefault();
                formularioContacto.reportValidity();
                return;
            }

            const botonGuardar = formularioContacto.querySelector(
                'button[type="submit"]'
            );

            if (botonGuardar) {
                botonGuardar.disabled = true;
                botonGuardar.textContent = "Guardando...";
            }
        });
    }

    // Buscador local de la tabla de contactos.
    const buscador = document.querySelector("#buscadorContactos");
    const filas = document.querySelectorAll(
        "#tablaContactos tbody tr[data-contacto]"
    );
    const mensajeSinResultados = document.querySelector(
        "#mensajeSinResultados"
    );

    if (buscador && filas.length > 0) {
        buscador.addEventListener("input", function () {
            const texto = buscador.value
                .trim()
                .toLocaleLowerCase("es");

            let visibles = 0;

            filas.forEach(function (fila) {
                const contenido = fila.textContent.toLocaleLowerCase("es");
                const coincide = contenido.includes(texto);

                fila.hidden = !coincide;

                if (coincide) {
                    visibles++;
                }
            });

            if (mensajeSinResultados) {
                mensajeSinResultados.hidden = visibles > 0;
            }
        });
    }

});
