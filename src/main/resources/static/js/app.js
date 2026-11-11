const API = "/api"

function campoVacio(valor) {
    return valor === null ||
           valor === undefined ||

           String(valor).trim() === "";
}

// =====================================================
// FUNCIÓN PARA VALIDAR CAMPOS VACÍOS
// =====================================================

function validarCampos(campos) {

    let formularioValido = true;

    campos.forEach(campo => {

        const elemento = document.getElementById(campo.id);
        const error = document.getElementById(campo.errorId);

        if (!elemento) {
            console.error(`No se encontró el campo: ${campo.id}`);
            formularioValido = false;
            return;
        }

        const valor = elemento.value;

        if (campoVacio(valor)) {
            if (error) {
                error.style.display = "block";
            }

            formularioValido = false;

        } else {
            if (error) {
                error.style.display = "none";
            }
        }
    });

    return formularioValido;
}

// =====================================================
// VALIDACIÓN INDIVIDUAL DE CAMPOS - CONTACTO
// =====================================================

const camposContactoValidacion = [
    {
        id: "nombre",
        errorId: "error-nombre",
        evento: "input"
    },
    {
        id: "apellido",
        errorId: "error-apellido",
        evento: "input"
    },
    {
        id: "email",
        errorId: "error-email",
        evento: "input"
    },
    {
        id: "telefono",
        errorId: "error-telefono",
        evento: "input"
    },
    {
        id: "programa",
        errorId: "error-programa",
        evento: "change"
    },
    {
        id: "estado",
        errorId: "error-estado",
        evento: "change"
    }
];

camposContactoValidacion.forEach(campo => {

    const elemento = document.getElementById(campo.id);
    const error = document.getElementById(campo.errorId);

    elemento.addEventListener(campo.evento, function() {

        if (campoVacio(elemento.value)) {
            error.style.display = "block";
        } else {
            error.style.display = "none";
        }

    });

});


// =====================================================
// MENSAJES
// =====================================================

function mostrarMensaje(texto, tipo = "exito") {
    const mensaje = document.getElementById("mensaje");

    mensaje.textContent = texto;
    mensaje.className = tipo;
    mensaje.style.display = "block";

    setTimeout(() => {
        mensaje.style.display = "none";
    }, 3000);
}

// =====================================================
// MOSTRAR SECCIÓN
// =====================================================

function mostrarSeccion(id) {
    document
        .getElementById(id)
        .scrollIntoView({
            behavior: "smooth"
        });
}

// =====================================================
// CONTACTOS - LISTAR
// =====================================================

async function cargarContactos() {
    try {
        const respuesta = await fetch(`${API}/contactos`);

        if (!respuesta.ok) {
            throw new Error(
                "No se pudieron obtener los contactos"
            );
        }

        const contactos = await respuesta.json();

        const tabla = document.getElementById(
            "tablaContactos"
        );

        tabla.innerHTML = "";

        contactos.forEach(contacto => {
            tabla.innerHTML += `
                <tr>
                    <td>${contacto.id ?? ""}</td>
                    <td>${contacto.nombre ?? ""}</td>
                    <td>${contacto.apellido ?? ""}</td>
                    <td>${contacto.email ?? ""}</td>
                    <td>${contacto.telefono ?? ""}</td>
                    <td>${contacto.programa ?? ""}</td>
                    <td>${contacto.estado ?? ""}</td>

                    <td class="acciones">
                        <button
                            class="boton editar"
                            onclick="editarContacto(${contacto.id})"
                        >
                        Editar
                        </button>

                        <button
                            class="boton eliminar"
                            onclick="eliminarContacto(${contacto.id})"
                        >
                        Eliminar
                        </button>
                    </td>
                </tr>
            `;
        });

        document.getElementById(
            "totalContactos"
        ).textContent = contactos.length;

    } catch (error) {
        console.error(error);

        mostrarMensaje(
            "No se pudieron cargar los contactos",
            "error"
        );
    }
}

// =====================================================
// CONTACTOS - CREAR / ACTUALIZAR
// =====================================================

document
    .getElementById("formContacto")
    .addEventListener(
        "submit",
        async function(event) {

            event.preventDefault();

            const contacto = {
                nombre:
                    document.getElementById(
                        "nombre"
                    ).value,

                apellido:
                    document.getElementById(
                        "apellido"
                    ).value,

                email:
                    document.getElementById(
                        "email"
                    ).value,

                telefono:
                    document.getElementById(
                        "telefono"
                    ).value,

                programa:
                    document.getElementById(
                        "programa"
                    ).value,

                estado:
                    document.getElementById(
                        "estado"
                    ).value

                };

                const camposContacto = [
                    {
                        nombre: "Nombre",
                        valor: contacto.nombre,
                        id: "nombre",
                        errorId: "error-nombre"
                    },
                    {
                        nombre: "Apellido",
                        valor: contacto.apellido,
                        id: "apellido",
                        errorId: "error-apellido"
                    },
                    {
                        nombre: "Correo electrónico",
                        valor: contacto.email,
                        id: "email",
                        errorId: "error-email"
                    },
                    {
                        nombre: "Teléfono",
                        valor: contacto.telefono,
                        id: "telefono",
                        errorId: "error-telefono"
                    },
                    {
                        nombre: "Programa",
                        valor: contacto.programa,
                        id: "programa",
                        errorId: "error-programa"
                    },
                    {
                        nombre: "Estado",
                        valor: contacto.estado,
                        id: "estado",
                        errorId: "error-estado"
                    }
                ];

                if (!validarCampos(camposContacto)) {
                return;
                }

                try {
                let respuesta;

                 if (contactoEditando !== null) {

                respuesta = await fetch(
                    `${API}/contactos/${contactoEditando}`,
                    {
                            method: "PUT",

                            headers: {
                                "Content-Type":
                                    "application/json"
                            },

                            body:
                                JSON.stringify(contacto)
                        }
                    );

                } else {

                    respuesta = await fetch(
                        `${API}/contactos`,
                        {
                            method: "POST",

                            headers: {
                                "Content-Type":
                                    "application/json"
                            },

                            body:
                                JSON.stringify(contacto)
                        }
                    );
                }

                if (!respuesta.ok) {
                    throw new Error(
                        "Error al guardar contacto"
                    );
                }

                if (contactoEditando !== null) {

                    mostrarMensaje(
                        "Contacto actualizado correctamente"
                    );

                } else {

                    mostrarMensaje(
                        "Contacto guardado correctamente"
                    );
                }

                cancelarEdicionContacto();
                cargarContactos();

            } catch (error) {
                console.error(error);

                mostrarMensaje(
                    "No se pudo guardar el contacto",
                    "error"
                );
            }
        }
    );

// =====================================================
// CONTACTOS - EDITAR
// =====================================================

async function editarContacto(id) {
    try {
        const respuesta =
            await fetch(
                `${API}/contactos/${id}`
            );

        if (!respuesta.ok) {
            throw new Error(
                "No se pudo obtener el contacto"
            );
        }

        const contacto =
            await respuesta.json();

        contactoEditando = id;

        document.getElementById(
            "nombre"
        ).value =
            contacto.nombre ?? "";

        document.getElementById(
            "apellido"
        ).value =
            contacto.apellido ?? "";

        document.getElementById(
            "email"
        ).value =
            contacto.email ?? "";

        document.getElementById(
            "telefono"
        ).value =
            contacto.telefono ?? "";

        document.getElementById(
            "programa"
        ).value =
            contacto.programa ?? "";

        document.getElementById(
            "estado"
        ).value =
            contacto.estado ?? "Activo";

        document.getElementById(
            "tituloFormularioContacto"
        ).textContent =
            "Editar contacto";

        document.getElementById(
            "botonContacto"
        ).textContent =
            "Actualizar contacto";

        document.getElementById(
            "cancelarContacto"
        ).style.display =
            "inline-block";

        document.getElementById(
            "formContacto"
        ).classList.add(
            "modo-edicion"
        );

        mostrarSeccion("contactos");

    } catch (error) {
        console.error(error);

        mostrarMensaje(
            "No se pudo cargar el contacto",
            "error"
        );
    }
}

// =====================================================
// CONTACTOS - CANCELAR EDICIÓN
// =====================================================

function cancelarEdicionContacto() {
    contactoEditando = null;

    document
        .getElementById("formContacto")
        .reset();

    document.getElementById(
        "tituloFormularioContacto"
    ).textContent =
        "Registrar contacto";

    document.getElementById(
        "botonContacto"
    ).textContent =
        "Guardar contacto";

    document.getElementById(
        "cancelarContacto"
    ).style.display =
        "none";

    document.getElementById(
        "formContacto"
    ).classList.remove(
        "modo-edicion"
    );
}

// =====================================================
// CONTACTOS - ELIMINAR
// =====================================================

async function eliminarContacto(id) {
    const confirmar =
        confirm(
            "¿Está seguro de eliminar este contacto?"
        );

    if (!confirmar) {
        return;
    }

    try {
        const respuesta =
            await fetch(
                `${API}/contactos/${id}`,
                {
                    method: "DELETE"
                }
            );

        if (!respuesta.ok) {
            throw new Error(
                "Error al eliminar contacto"
            );
        }

        mostrarMensaje(
            "Contacto eliminado correctamente"
        );

        cargarContactos();

    } catch (error) {
        console.error(error);

        mostrarMensaje(
            "No se pudo eliminar el contacto",
            "error"
        );
    }
}

// =====================================================
// OPORTUNIDADES - LISTAR
// =====================================================

async function cargarOportunidades() {
    try {
        const respuesta =
            await fetch(
                `${API}/oportunidades`
            );

        if (!respuesta.ok) {
            throw new Error(
                "No se pudieron obtener las oportunidades"
            );
        }

        const oportunidades =
            await respuesta.json();

        const tabla =
            document.getElementById(
                "tablaOportunidades"
            );

        tabla.innerHTML = "";

        oportunidades.forEach(oportunidad => {

            const contactoId =
                oportunidad.contacto
                    ? oportunidad.contacto.id
                    : "";

            tabla.innerHTML += `
                <tr>

                    <td>
                        ${oportunidad.id ?? ""}
                    </td>

                    <td>
                        ${oportunidad.titulo ?? ""}
                    </td>

                    <td>
                        ${oportunidad.descripcion ?? ""}
                    </td>

                    <td>
                        ${oportunidad.estado ?? ""}
                    </td>

                    <td>
                        ${oportunidad.prioridad ?? ""}
                    </td>

                    <td>
                        ${contactoId}
                    </td>

                    <td>
                        ${oportunidad.fechaCreacion ?? ""}
                    </td>

                    <td>
                        ${oportunidad.fechaSeguimiento ?? ""}
                    </td>

                    <td class="acciones">

                        <button
                            class="boton editar"
                            onclick="editarOportunidad(${oportunidad.id})"
                        >
                            Editar
                        </button>

                        <button
                            class="boton eliminar"
                            onclick="eliminarOportunidad(${oportunidad.id})"
                        >
                            Eliminar
                        </button>

                    </td>

                </tr>
            `;
        });

        document.getElementById(
            "totalOportunidades"
        ).textContent =
            oportunidades.length;

    } catch (error) {
        console.error(error);

        mostrarMensaje(
            "No se pudieron cargar las oportunidades",
            "error"
        );
    }
}

// =====================================================
// OPORTUNIDADES - CREAR / ACTUALIZAR
// =====================================================

document
    .getElementById("formOportunidad")
    .addEventListener(
        "submit",
        async function(event) {

            event.preventDefault();

            const contactoId =
                Number(
                    document.getElementById(
                        "contactoOportunidad"
                    ).value
                );

            const oportunidad = {

                titulo:
                    document.getElementById(
                        "titulo"
                    ).value,

                descripcion:
                    document.getElementById(
                        "descripcion"
                    ).value,

                estado:
                    document.getElementById(
                        "estadoOportunidad"
                    ).value,

                prioridad:
                    document.getElementById(
                        "prioridad"
                    ).value,

                fechaCreacion:
                    document.getElementById(
                        "fechaCreacion"
                    ).value,

                fechaSeguimiento:
                    document.getElementById(
                        "fechaSeguimiento"
                    ).value,

                contacto: {
                    id: contactoId
                }
            };


            const camposOportunidad = [

                {
                    nombre: "Título",
                    valor: oportunidad.titulo,
                    id: "titulo",
                    errorId: "error-titulo"
                },

                {
                    nombre: "Descripción",
                    valor: oportunidad.descripcion,
                    id: "descripcion",
                    errorId: "error-descripcion"
                },

                {
                    nombre: "Estado",
                    valor: oportunidad.estado,
                    id: "estadoOportunidad",
                    errorId: "error-estadoOportunidad"
                },

                {
                    nombre: "Prioridad",
                    valor: oportunidad.prioridad,
                    id: "prioridad",
                    errorId: "error-prioridad"
                },

                {
                    nombre: "Contacto",
                    valor:
                        contactoId > 0
                            ? contactoId
                            : "",
                    id: "contactoOportunidad",
                    errorId: "error-contactoOportunidad"
                },

                {
                    nombre: "Fecha de creación",
                    valor:
                        oportunidad.fechaCreacion,
                    id: "fechaCreacion",
                    errorId: "error-fechaCreacion"
                },

                {
                    nombre: "Fecha de seguimiento",
                    valor:
                        oportunidad.fechaSeguimiento,
                    id: "fechaSeguimiento",
                    errorId: "error-fechaSeguimiento"
                }

            ];


            if (
                !validarCampos(
                    camposOportunidad
                )
            ) {
                return;
            }


            try {

                let respuesta;


                if (
                    oportunidadEditando !== null
                ) {

                    respuesta =
                        await fetch(
                            `${API}/oportunidades/${oportunidadEditando}`,
                            {
                                method: "PUT",

                                headers: {
                                    "Content-Type":
                                        "application/json"
                                },

                                body:
                                    JSON.stringify(
                                        oportunidad
                                    )
                            }
                        );

                } else {

                    respuesta =
                        await fetch(
                            `${API}/oportunidades`,
                            {
                                method: "POST",

                                headers: {
                                    "Content-Type":
                                        "application/json"
                                },

                                body:
                                    JSON.stringify(
                                        oportunidad
                                    )
                            }
                        );
                }


                if (!respuesta.ok) {

                    const mensajeError =
                        await respuesta.text();

                    console.error(
                        "Respuesta del servidor:",
                        mensajeError
                    );

                    throw new Error(
                        "Error al guardar oportunidad"
                    );
                }


                if (
                    oportunidadEditando !== null
                ) {

                    mostrarMensaje(
                        "Oportunidad actualizada correctamente"
                    );

                } else {

                    mostrarMensaje(
                        "Oportunidad guardada correctamente"
                    );
                }


                cancelarEdicionOportunidad();

                cargarOportunidades();


            } catch (error) {

                console.error(error);

                mostrarMensaje(
                    "No se pudo guardar la oportunidad",
                    "error"
                );
            }
        }
    );
// =====================================================
// OPORTUNIDADES - EDITAR
// =====================================================

async function editarOportunidad(id) {
    try {
        const respuesta =
            await fetch(
                `${API}/oportunidades/${id}`
            );

        if (!respuesta.ok) {
            throw new Error(
                "No se pudo obtener la oportunidad"
            );
        }

        const oportunidad =
            await respuesta.json();

        oportunidadEditando = id;

        document.getElementById(
            "titulo"
        ).value =
            oportunidad.titulo ?? "";

        document.getElementById(
            "descripcion"
        ).value =
            oportunidad.descripcion ?? "";

        document.getElementById(
            "estadoOportunidad"
        ).value =
            oportunidad.estado ?? "Nueva";

        document.getElementById(
            "prioridad"
        ).value =
            oportunidad.prioridad ?? "Alta";

        document.getElementById(
            "contactoOportunidad"
        ).value =
            oportunidad.contacto
                ? oportunidad.contacto.id
                : "";

        document.getElementById(
            "fechaCreacion"
        ).value =
            oportunidad.fechaCreacion ?? "";

        document.getElementById(
            "fechaSeguimiento"
        ).value =
            oportunidad.fechaSeguimiento ?? "";

        document.getElementById(
            "tituloFormularioOportunidad"
        ).textContent =
            "Editar oportunidad";

        document.getElementById(
            "botonOportunidad"
        ).textContent =
            "Actualizar oportunidad";

        document.getElementById(
            "cancelarOportunidad"
        ).style.display =
            "inline-block";

        document.getElementById(
            "formOportunidad"
        ).classList.add(
            "modo-edicion"
        );

        mostrarSeccion(
            "oportunidades"
        );

    } catch (error) {
        console.error(error);

        mostrarMensaje(
            "No se pudo cargar la oportunidad",
            "error"
        );
    }
}

// =====================================================
// OPORTUNIDADES - CANCELAR
// =====================================================

function cancelarEdicionOportunidad() {
    oportunidadEditando = null;

    document
        .getElementById("formOportunidad")
        .reset();

    document.getElementById(
        "tituloFormularioOportunidad"
    ).textContent =
        "Registrar oportunidad";

    document.getElementById(
        "botonOportunidad"
    ).textContent =
        "Guardar oportunidad";

    document.getElementById(
        "cancelarOportunidad"
    ).style.display =
        "none";

    document.getElementById(
        "formOportunidad"
    ).classList.remove(
        "modo-edicion"
    );
}

// =====================================================
// OPORTUNIDADES - ELIMINAR
// =====================================================

async function eliminarOportunidad(id) {
    const confirmar =
        confirm(
            "¿Está seguro de eliminar esta oportunidad?"
        );

    if (!confirmar) {
        return;
    }

    try {
        const respuesta =
            await fetch(
                `${API}/oportunidades/${id}`,
                {
                    method: "DELETE"
                }
            );

        if (!respuesta.ok) {
            throw new Error(
                "Error al eliminar oportunidad"
            );
        }

        mostrarMensaje(
            "Oportunidad eliminada correctamente"
        );

        cargarOportunidades();

    } catch (error) {
        console.error(error);

        mostrarMensaje(
            "No se pudo eliminar la oportunidad",
            "error"
        );
    }
}

// =====================================================
// COMUNICACIONES - LISTAR
// =====================================================

async function cargarComunicaciones() {
    try {
        const respuesta =
            await fetch(
                `${API}/comunicaciones`
            );

        if (!respuesta.ok) {
            throw new Error(
                "No se pudieron obtener las comunicaciones"
            );
        }

        const comunicaciones =
            await respuesta.json();

        const tabla =
            document.getElementById(
                "tablaComunicaciones"
            );

        tabla.innerHTML = "";

        comunicaciones.forEach(
            comunicacion => {

                const contactoId =
                    comunicacion.contacto
                        ? comunicacion.contacto.id
                        : "";

                tabla.innerHTML += `
                    <tr>

                        <td>
                            ${comunicacion.id ?? ""}
                        </td>

                        <td>
                            ${comunicacion.tipo ?? ""}
                        </td>

                        <td>
                            ${comunicacion.asunto ?? ""}
                        </td>

                        <td>
                            ${comunicacion.mensaje ?? ""}
                        </td>

                        <td>
                            ${comunicacion.fecha ?? ""}
                        </td>

                        <td>
                            ${contactoId}
                        </td>

                        <td class="acciones">

                            <button
                                class="boton editar"
                                onclick="editarComunicacion(${comunicacion.id})"
                            >
                                Editar
                            </button>

                            <button
                                class="boton eliminar"
                                onclick="eliminarComunicacion(${comunicacion.id})"
                            >
                                Eliminar
                            </button>

                        </td>

                    </tr>
                `;
            }
        );

    } catch (error) {

        console.error(error);

        mostrarMensaje(
            "No se pudieron cargar las comunicaciones",
            "error"
        );
    }
}
// =====================================================
// COMUNICACIONES - CREAR / ACTUALIZAR
// =====================================================

document
    .getElementById("formComunicacion")
    .addEventListener(
        "submit",
        async function(event) {

            event.preventDefault();

            const contactoId =
                Number(
                    document.getElementById(
                        "contactoComunicacion"
                    ).value
                );

            const comunicacion = {

                tipo:
                    document.getElementById(
                        "tipo"
                    ).value,

                asunto:
                    document.getElementById(
                        "asunto"
                    ).value,

                mensaje:
                    document.getElementById(
                        "mensajeTexto"
                    ).value,

                fecha:
                    document.getElementById(
                        "fecha"
                    ).value,

                contacto: {
                    id: contactoId
                }
            };


            const camposComunicacion = [

                {
                    nombre: "Tipo",
                    valor: comunicacion.tipo,
                    id: "tipo",
                    errorId: "error-tipo"
                },

                {
                    nombre: "Asunto",
                    valor: comunicacion.asunto,
                    id: "asunto",
                    errorId: "error-asunto"
                },

                {
                    nombre: "Mensaje",
                    valor: comunicacion.mensaje,
                    id: "mensajeTexto",
                    errorId: "error-mensajeTexto"
                },

                {
                    nombre: "Fecha",
                    valor: comunicacion.fecha,
                    id: "fecha",
                    errorId: "error-fecha"
                },

                {
                    nombre: "Contacto",
                    valor:
                        contactoId > 0
                            ? contactoId
                            : "",
                    id: "contactoComunicacion",
                    errorId: "error-contactoComunicacion"
                }

            ];


            if (
                !validarCampos(
                    camposComunicacion
                )
            ) {
                return;
            }


            try {

                let respuesta;


                if (
                    comunicacionEditando !== null
                ) {

                    respuesta =
                        await fetch(
                            `${API}/comunicaciones/${comunicacionEditando}`,
                            {
                                method: "PUT",

                                headers: {
                                    "Content-Type":
                                        "application/json"
                                },

                                body:
                                    JSON.stringify(
                                        comunicacion
                                    )
                            }
                        );

                } else {

                    respuesta =
                        await fetch(
                            `${API}/comunicaciones`,
                            {
                                method: "POST",

                                headers: {
                                    "Content-Type":
                                        "application/json"
                                },

                                body:
                                    JSON.stringify(
                                        comunicacion
                                    )
                            }
                        );
                }


                if (!respuesta.ok) {

                    const mensajeError =
                        await respuesta.text();

                    console.error(
                        "Respuesta del servidor:",
                        mensajeError
                    );

                    throw new Error(
                        "Error al guardar comunicación"
                    );
                }


                if (
                    comunicacionEditando !== null
                ) {

                    mostrarMensaje(
                        "Comunicación actualizada correctamente"
                    );

                } else {

                    mostrarMensaje(
                        "Comunicación guardada correctamente"
                    );
                }


                cancelarEdicionComunicacion();

                cargarComunicaciones();


            } catch (error) {

                console.error(error);

                mostrarMensaje(
                    "No se pudo guardar la comunicación",
                    "error"
                );
            }
        }
    );

// =====================================================
// COMUNICACIONES - EDITAR
// =====================================================

async function editarComunicacion(id) {
    try {
        const respuesta =
            await fetch(
                `${API}/comunicaciones/${id}`
            );

        if (!respuesta.ok) {
            throw new Error(
                "No se pudo obtener la comunicación"
            );
        }

        const comunicacion =
            await respuesta.json();

        comunicacionEditando = id;

        document.getElementById(
            "tipo"
        ).value =
            comunicacion.tipo ?? "Email";

        document.getElementById(
            "asunto"
        ).value =
            comunicacion.asunto ?? "";

        document.getElementById(
            "mensajeTexto"
        ).value =
            comunicacion.mensaje ?? "";

        document.getElementById(
            "fecha"
        ).value =
            comunicacion.fecha ?? "";

        document.getElementById(
            "contactoComunicacion"
        ).value =
            comunicacion.contacto
                ? comunicacion.contacto.id
                : "";

        document.getElementById(
            "tituloFormularioComunicacion"
        ).textContent =
            "Editar comunicación";

        document.getElementById(
            "botonComunicacion"
        ).textContent =
            "Actualizar comunicación";

        document.getElementById(
            "cancelarComunicacion"
        ).style.display =
            "inline-block";

        document.getElementById(
            "formComunicacion"
        ).classList.add(
            "modo-edicion"
        );

        mostrarSeccion(
            "comunicaciones"
        );

    } catch (error) {
        console.error(error);

        mostrarMensaje(
            "No se pudo cargar la comunicación",
            "error"
        );
    }
}

// =====================================================
// COMUNICACIONES - CANCELAR
// =====================================================

function cancelarEdicionComunicacion() {
    comunicacionEditando = null;

    document
        .getElementById("formComunicacion")
        .reset();

    document.getElementById(
        "tituloFormularioComunicacion"
    ).textContent =
        "Registrar comunicación";

    document.getElementById(
        "botonComunicacion"
    ).textContent =
        "Guardar comunicación";

    document.getElementById(
        "cancelarComunicacion"
    ).style.display =
        "none";

    document.getElementById(
        "formComunicacion"
    ).classList.remove(
        "modo-edicion"
    );
}

// =====================================================
// COMUNICACIONES - ELIMINAR
// =====================================================

async function eliminarComunicacion(id) {
    const confirmar =
        confirm(
            "¿Está seguro de eliminar esta comunicación?"
        );

    if (!confirmar) {
        return;
    }

    try {
        const respuesta =
            await fetch(
                `${API}/comunicaciones/${id}`,
                {
                    method: "DELETE"
                }
            );

        if (!respuesta.ok) {
            throw new Error(
                "Error al eliminar comunicación"
            );
        }

        mostrarMensaje(
            "Comunicación eliminada correctamente"
        );

        cargarComunicaciones();

    } catch (error) {
        console.error(error);

        mostrarMensaje(
            "No se pudo eliminar la comunicación",
            "error"
        );
    }
}

// =====================================================
// CARGAR TODO
// =====================================================

function cargarTodo() {
    cargarContactos();
    cargarOportunidades();
    cargarComunicaciones();
}

// =====================================================
// INICIO
// =====================================================

cargarTodo();
