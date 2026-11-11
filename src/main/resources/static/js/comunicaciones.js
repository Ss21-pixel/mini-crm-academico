/* =========================================
   MINI CRM ACADEMICO - COMUNICACIONES
   Adaptado al estilo de interaccion de Contactos
   ========================================= */

document.addEventListener("DOMContentLoaded", function () {

    const API_COMUNICACIONES = "/api/comunicaciones";
    const API_CONTACTOS = "/api/contactos";

    const tabla = document.querySelector("#tablaComunicaciones tbody");
    const formulario = document.querySelector("#formularioComunicacion");
    const selectorContacto = document.querySelector("#contactoId");
    const mensajeEstado = document.querySelector("#mensajeEstado");
    const botonGuardar = document.querySelector("#botonGuardar");
    const tituloFormulario = document.querySelector("#tituloFormulario");
    const botonCancelar = document.querySelector("#botonCancelar");
    const campoId = document.querySelector("#comunicacionId");
    const buscador = document.querySelector("#buscadorComunicaciones");
    const contador = document.querySelector("#contadorComunicaciones");

    let comunicaciones = [];
    let contactos = [];

    // Mostrar mensajes de exito o error.
    function mostrarMensaje(texto, tipo) {
        if (!mensajeEstado) return;

        mensajeEstado.textContent = texto;
        mensajeEstado.className = tipo === "error"
            ? "comunicaciones-error"
            : "comunicaciones-exito";
        mensajeEstado.hidden = false;
    }

    function limpiarMensaje() {
        if (mensajeEstado) {
            mensajeEstado.hidden = true;
            mensajeEstado.textContent = "";
        }
    }

    // Consultar la API y comprobar errores HTTP.
    async function solicitar(url, opciones = {}) {
        const respuesta = await fetch(url, {
            ...opciones,
            headers: {
                "Content-Type": "application/json",
                ...(opciones.headers || {})
            }
        });

        if (!respuesta.ok) {
            throw new Error(
                "La solicitud no pudo completarse (HTTP " +
                respuesta.status + ")."
            );
        }

        if (respuesta.status === 204) return null;

        const texto = await respuesta.text();
        return texto ? JSON.parse(texto) : null;
    }

    // Evitar insertar texto recibido como HTML.
    function escapar(valor) {
        return String(valor ?? "").replace(/[&<>"']/g, function (caracter) {
            return {
                "&": "&amp;",
                "<": "&lt;",
                ">": "&gt;",
                '"': "&quot;",
                "'": "&#39;"
            }[caracter];
        });
    }

    // Cargar contactos para asociarlos a cada comunicacion.
    async function cargarContactos() {
        contactos = await solicitar(API_CONTACTOS) || [];

        selectorContacto.innerHTML =
            '<option value="">Selecciona un contacto</option>';

        contactos.forEach(function (contacto) {
            const opcion = document.createElement("option");
            opcion.value = contacto.id;
            opcion.textContent =
                (contacto.nombre || "") + " " +
                (contacto.apellido || "") + " — " +
                (contacto.email || "");

            selectorContacto.appendChild(opcion);
        });
    }

    function nombreContacto(comunicacion) {
        const contacto = comunicacion.contacto;

        if (!contacto) return "Sin contacto";

        return (
            (contacto.nombre || "") + " " +
            (contacto.apellido || "")
        ).trim() || "Contacto #" + (contacto.id || "");
    }

    // Dibujar la tabla con el estilo de la pagina de Contactos.
    function renderizarComunicaciones() {
        if (!tabla) return;

        const textoBusqueda = (buscador?.value || "")
            .trim()
            .toLocaleLowerCase("es");

        const filtradas = comunicaciones.filter(function (comunicacion) {
            const contenido = [
                comunicacion.id,
                comunicacion.tipo,
                comunicacion.asunto,
                comunicacion.mensaje,
                comunicacion.fecha,
                nombreContacto(comunicacion)
            ].join(" ").toLocaleLowerCase("es");

            return contenido.includes(textoBusqueda);
        });

        tabla.innerHTML = "";

        if (contador) {
            contador.textContent = filtradas.length;
        }

        if (filtradas.length === 0) {
            const fila = document.createElement("tr");
            const celda = document.createElement("td");

            celda.colSpan = 6;
            celda.className = "comunicaciones-sin-registros";
            celda.textContent = comunicaciones.length === 0
                ? "Todavia no hay comunicaciones registradas."
                : "No hay comunicaciones que coincidan con la busqueda.";

            fila.appendChild(celda);
            tabla.appendChild(fila);
            return;
        }

        filtradas.forEach(function (comunicacion) {
            const fila = document.createElement("tr");

            fila.innerHTML = `
                <td>${escapar(comunicacion.id)}</td>
                <td>
                    <span class="comunicaciones-tipo">
                        ${escapar(comunicacion.tipo)}
                    </span>
                </td>
                <td>${escapar(comunicacion.asunto)}</td>
                <td class="comunicaciones-mensaje-celda">
                    ${escapar(comunicacion.mensaje)}
                </td>
                <td>${escapar(comunicacion.fecha)}</td>
                <td>
                    <div>${escapar(nombreContacto(comunicacion))}</div>
                    <div class="comunicaciones-acciones">
                        <button type="button"
                                class="comunicaciones-boton comunicaciones-boton-editar"
                                data-editar="${escapar(comunicacion.id)}">
                            Editar
                        </button>
                        <button type="button"
                                class="comunicaciones-boton comunicaciones-boton-eliminar"
                                data-eliminar="${escapar(comunicacion.id)}">
                            Eliminar
                        </button>
                    </div>
                </td>
            `;

            tabla.appendChild(fila);
        });
    }

    // Consultar las comunicaciones guardadas.
    async function cargarComunicaciones() {
        comunicaciones = await solicitar(API_COMUNICACIONES) || [];
        renderizarComunicaciones();
    }

    // Dejar el formulario listo para un nuevo registro.
    function limpiarFormulario() {
        formulario.reset();
        campoId.value = "";
        tituloFormulario.textContent = "Registrar comunicación";
        botonGuardar.textContent = "Guardar comunicación";
        botonCancelar.hidden = true;
        limpiarMensaje();
    }

    // Cargar los datos seleccionados en el formulario.
    function editarComunicacion(id) {
        const comunicacion = comunicaciones.find(function (elemento) {
            return String(elemento.id) === String(id);
        });

        if (!comunicacion) {
            mostrarMensaje(
                "No se encontro la comunicacion seleccionada.",
                "error"
            );
            return;
        }

        campoId.value = comunicacion.id;
        formulario.elements.tipo.value = comunicacion.tipo || "";
        formulario.elements.asunto.value = comunicacion.asunto || "";
        formulario.elements.mensaje.value = comunicacion.mensaje || "";
        formulario.elements.fecha.value = comunicacion.fecha || "";
        selectorContacto.value = comunicacion.contacto?.id || "";

        tituloFormulario.textContent = "Editar comunicación";
        botonGuardar.textContent = "Actualizar comunicación";
        botonCancelar.hidden = false;

        formulario.scrollIntoView({
            behavior: "smooth",
            block: "start"
        });
    }

    // Validar y guardar o actualizar una comunicacion.
    if (formulario) {
        formulario.addEventListener("submit", async function (evento) {
            evento.preventDefault();
            limpiarMensaje();

            if (!formulario.checkValidity()) {
                formulario.reportValidity();
                return;
            }

            const id = campoId.value;

            const datos = {
                tipo: formulario.elements.tipo.value.trim(),
                asunto: formulario.elements.asunto.value.trim(),
                mensaje: formulario.elements.mensaje.value.trim(),
                fecha: formulario.elements.fecha.value,
                contacto: {
                    id: Number(selectorContacto.value)
                }
            };

            if (!datos.contacto.id) {
                mostrarMensaje(
                    "Debes seleccionar un contacto.",
                    "error"
                );
                selectorContacto.focus();
                return;
            }

            // Evitar envios repetidos mientras se procesa la solicitud.
            botonGuardar.disabled = true;
            botonGuardar.textContent = id
                ? "Actualizando..."
                : "Guardando...";

            try {
                await solicitar(
                    id
                        ? API_COMUNICACIONES + "/" + id
                        : API_COMUNICACIONES,
                    {
                        method: id ? "PUT" : "POST",
                        body: JSON.stringify(datos)
                    }
                );

                limpiarFormulario();
                await cargarComunicaciones();

                mostrarMensaje(
                    id
                        ? "Comunicacion actualizada correctamente."
                        : "Comunicacion registrada correctamente.",
                    "exito"
                );

            } catch (error) {
                mostrarMensaje(
                    "No se pudo guardar la comunicacion. Verifica los datos y que el contacto exista. " +
                    error.message,
                    "error"
                );

            } finally {
                botonGuardar.disabled = false;
                botonGuardar.textContent = campoId.value
                    ? "Actualizar comunicación"
                    : "Guardar comunicación";
            }
        });
    }

    // Cancelar la edicion.
    if (botonCancelar) {
        botonCancelar.addEventListener("click", limpiarFormulario);
    }

    // Editar o eliminar desde la tabla.
    if (tabla) {
        tabla.addEventListener("click", async function (evento) {
            const botonEditar = evento.target.closest("[data-editar]");
            const botonEliminar = evento.target.closest("[data-eliminar]");

            if (botonEditar) {
                editarComunicacion(botonEditar.dataset.editar);
                return;
            }

            if (botonEliminar) {
                const id = botonEliminar.dataset.eliminar;

                const confirmado = window.confirm(
                    "¿Estas seguro de que deseas eliminar esta comunicacion? " +
                    "Esta accion no se puede deshacer."
                );

                if (!confirmado) return;

                try {
                    await solicitar(
                        API_COMUNICACIONES + "/" + id,
                        { method: "DELETE" }
                    );

                    await cargarComunicaciones();

                    mostrarMensaje(
                        "Comunicacion eliminada correctamente.",
                        "exito"
                    );

                } catch (error) {
                    mostrarMensaje(
                        "No se pudo eliminar la comunicacion. " +
                        error.message,
                        "error"
                    );
                }
            }
        });
    }

    // Buscar sin alterar ni eliminar los datos guardados.
    if (buscador) {
        buscador.addEventListener("input", renderizarComunicaciones);
    }

    // Cargar la informacion inicial.
    async function iniciar() {
        try {
            await cargarContactos();
            await cargarComunicaciones();

        } catch (error) {
            mostrarMensaje(
                "No se pudieron cargar los datos iniciales. " +
                error.message,
                "error"
            );
        }
    }

    iniciar();
});