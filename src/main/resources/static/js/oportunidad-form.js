document.addEventListener("DOMContentLoaded", function () {
    const formulario = document.getElementById("formOportunidad");

    if (!formulario) {
        return;
    }

    const campos = [
        {
            id: "titulo",
            errorId: "tituloError",
            mensaje: "El título es obligatorio.",
            validar: function (campo) {
                const valor = campo.value.trim();

                if (valor.length === 0) {
                    return "El título es obligatorio.";
                }

                if (valor.length < 2 || valor.length > 100) {
                    return "El título debe tener entre 2 y 100 caracteres.";
                }

                return "";
            }
        },
        {
            id: "descripcion",
            errorId: "descripcionError",
            validar: function (campo) {
                const valor = campo.value.trim();

                if (valor.length === 0) {
                    return "La descripción es obligatoria.";
                }

                if (valor.length < 2 || valor.length > 500) {
                    return "La descripción debe tener entre 2 y 500 caracteres.";
                }

                return "";
            }
        },
        {
            id: "estado",
            errorId: "estadoError",
            validar: function (campo) {
                return campo.value.trim() === ""
                    ? "Debes seleccionar un estado."
                    : "";
            }
        },
        {
            id: "prioridad",
            errorId: "prioridadError",
            validar: function (campo) {
                return campo.value.trim() === ""
                    ? "Debes seleccionar una prioridad."
                    : "";
            }
        },
        {
            id: "contacto.id",
            errorId: "contactoError",
            validar: function (campo) {
                return campo.value.trim() === ""
                    ? "Debes seleccionar un contacto."
                    : "";
            }
        },
        {
            id: "fechaCreacion",
            errorId: "fechaCreacionError",
            validar: function (campo) {
                return campo.value === ""
                    ? "La fecha de creación es obligatoria."
                    : "";
            }
        },
        {
            id: "fechaSeguimiento",
            errorId: "fechaSeguimientoError",
            validar: function (campo) {
                if (campo.value === "") {
                    return "La fecha de seguimiento es obligatoria.";
                }

                const creacion = document.getElementById("fechaCreacion");

                if (creacion && creacion.value !== ""
                    && campo.value < creacion.value) {
                    return "La fecha de seguimiento no puede ser anterior a la fecha de creación.";
                }

                return "";
            }
        }
    ];

    function validarCampo(configuracion) {
        const campo = document.getElementById(configuracion.id);
        const mensaje = document.getElementById(configuracion.errorId);

        if (!campo || !mensaje) {
            return true;
        }

        const error = configuracion.validar(campo);

        mensaje.textContent = error;
        campo.classList.toggle("invalido", error !== "");
        campo.classList.toggle("valido", error === "" && campo.value.trim() !== "");

        if (error) {
            campo.setAttribute("aria-invalid", "true");
        } else {
            campo.removeAttribute("aria-invalid");
        }

        return error === "";
    }

    campos.forEach(function (configuracion) {
        const campo = document.getElementById(configuracion.id);

        if (!campo) {
            return;
        }

        campo.addEventListener("blur", function () {
            validarCampo(configuracion);
        });

        campo.addEventListener("input", function () {
            validarCampo(configuracion);
        });

        campo.addEventListener("change", function () {
            validarCampo(configuracion);
        });
    });

    formulario.addEventListener("submit", function (evento) {
        let formularioValido = true;
        let primerCampoInvalido = null;

        campos.forEach(function (configuracion) {
            const valido = validarCampo(configuracion);

            if (!valido) {
                formularioValido = false;

                if (!primerCampoInvalido) {
                    primerCampoInvalido = document.getElementById(
                        configuracion.id
                    );
                }
            }
        });

        if (!formularioValido) {
            evento.preventDefault();

            if (primerCampoInvalido) {
                primerCampoInvalido.focus();
            }
        }
    });
});