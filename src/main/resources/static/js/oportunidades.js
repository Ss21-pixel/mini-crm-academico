/* =========================================
   MINI CRM ACADEMICO - OPORTUNIDADES
   ========================================= */

   const API_OPORTUNIDADES = "/api/oportunidades";
   const API_CONTACTOS = "/api/contactos";

   const tabla = document.getElementById("tablaOportunidades");
   const formulario = document.getElementById("formOportunidad");
   const panelFormulario = document.getElementById("panelFormulario");
   const mensaje = document.getElementById("mensaje");

   let oportunidades = [];
   let contactos = [];
   let oportunidadesFiltradas = [];
   let paginaActual = 1;
   const registrosPorPagina = 5;

   // ESCAPAR TEXTO PARA EVITAR INSERTAR HTML NO DESEADO
   function escapar(texto) {
       return String(texto ?? "").replace(/[&<>"']/g, caracter => ({
           "&": "&amp;",
           "<": "&lt;",
           ">": "&gt;",
           '"': "&quot;",
           "'": "&#039;"
       })[caracter]);
   }

   // MOSTRAR MENSAJES
   function mostrarMensaje(texto, error = false) {
       mensaje.textContent = texto;
       mensaje.className = error
           ? "oportunidades-mensaje error"
           : "oportunidades-mensaje";

       mensaje.hidden = false;

       window.scrollTo({ top: 0, behavior: "smooth" });
   }

   // CONSULTAR API
   async function consultar(url, opciones = {}) {
       const respuesta = await fetch(url, {
           ...opciones,
           headers: {
               "Content-Type": "application/json",
               ...(opciones.headers || {})
           }
       });

       if (!respuesta.ok) {
           let detalle = "";

           try {
               const datos = await respuesta.json();
               detalle = Object.values(datos).join(". ");
           } catch {
               detalle = await respuesta.text();
           }

           throw new Error(
               detalle || `Error en la solicitud: ${respuesta.status}`
           );
       }

       if (respuesta.status === 204) {
           return null;
       }

       return respuesta.json();
   }

   // CARGAR CONTACTOS
   async function cargarContactos() {
       contactos = await consultar(API_CONTACTOS);

       const selector = document.getElementById("contacto");

       selector.innerHTML =
           '<option value="">Seleccione un contacto</option>';

       contactos.forEach(contacto => {
           const opcion = document.createElement("option");
           opcion.value = contacto.id;
           opcion.textContent =
               `${contacto.nombre ?? ""} ${contacto.apellido ?? ""}`.trim();

           selector.appendChild(opcion);
       });
   }

   // CARGAR OPORTUNIDADES
   async function cargarOportunidades() {
       try {
           oportunidades = await consultar(API_OPORTUNIDADES);
           oportunidadesFiltradas = [...oportunidades];

           document.getElementById("totalOportunidades").textContent =
               oportunidades.length;

           paginaActual = 1;
           mostrarTabla();

       } catch (error) {
           tabla.innerHTML = `
               <tr>
                   <td colspan="9" class="oportunidades-sin-registros">
                       No fue posible cargar las oportunidades:
                       ${escapar(error.message)}
                   </td>
               </tr>
           `;
       }
   }

   // FORMATEAR FECHAS
   function formatearFecha(fecha) {
       if (!fecha) return "No registrada";

       const partes = fecha.split("-");

       if (partes.length !== 3) return fecha;

       return `${partes[2]}/${partes[1]}/${partes[0]}`;
   }

   // CLASE DEL ESTADO
   function claseEstado(estado) {
       switch (estado) {
           case "GANADA":
               return "estado-ganada";
           case "PERDIDA":
               return "estado-perdida";
           case "EN_PROCESO":
               return "estado-proceso";
           default:
               return "estado-abierta";
       }
   }

   // CLASE DE PRIORIDAD
   function clasePrioridad(prioridad) {
       switch (prioridad) {
           case "ALTA":
               return "prioridad-alta";
           case "MEDIA":
               return "prioridad-media";
           default:
               return "prioridad-baja";
       }
   }

   // MOSTRAR TABLA
   function mostrarTabla() {
       const inicio = (paginaActual - 1) * registrosPorPagina;

       const registros = oportunidadesFiltradas.slice(
           inicio,
           inicio + registrosPorPagina
       );

       if (registros.length === 0) {
           tabla.innerHTML = `
               <tr>
                   <td colspan="9" class="oportunidades-sin-registros">
                       No hay oportunidades registradas.
                   </td>
               </tr>
           `;
       } else {
           tabla.innerHTML = registros.map(oportunidad => {
               const contacto = oportunidad.contacto
                   ? `${oportunidad.contacto.nombre ?? ""} ${oportunidad.contacto.apellido ?? ""}`.trim()
                   : "Sin contacto";

               return `
                   <tr>
                       <td>${escapar(oportunidad.id)}</td>
                       <td>${escapar(oportunidad.titulo)}</td>
                       <td>${escapar(oportunidad.descripcion)}</td>

                       <td>
                           <span class="oportunidades-estado ${claseEstado(oportunidad.estado)}">
                               ${escapar(oportunidad.estado)}
                           </span>
                       </td>

                       <td>
                           <span class="oportunidades-prioridad ${clasePrioridad(oportunidad.prioridad)}">
                               ${escapar(oportunidad.prioridad)}
                           </span>
                       </td>

                       <td>${escapar(contacto)}</td>
                       <td>${escapar(formatearFecha(oportunidad.fechaCreacion))}</td>
                       <td>${escapar(formatearFecha(oportunidad.fechaSeguimiento))}</td>

                       <td class="oportunidades-acciones-tabla">
                           <button type="button"
                                   class="oportunidades-boton-tabla oportunidades-editar"
                                   data-accion="editar"
                                   data-id="${escapar(oportunidad.id)}">
                               Editar
                           </button>

                           <button type="button"
                                   class="oportunidades-boton-tabla oportunidades-eliminar"
                                   data-accion="eliminar"
                                   data-id="${escapar(oportunidad.id)}">
                               Eliminar
                           </button>
                       </td>
                   </tr>
               `;
           }).join("");
       }

       actualizarPaginacion();
   }

   // PAGINACIÓN
   function actualizarPaginacion() {
       const totalPaginas = Math.max(
           1,
           Math.ceil(oportunidadesFiltradas.length / registrosPorPagina)
       );

       document.getElementById("paginaInfo").textContent =
           `Página ${paginaActual} de ${totalPaginas}`;

       document.getElementById("btnAnterior").disabled =
           paginaActual <= 1;

       document.getElementById("btnSiguiente").disabled =
           paginaActual >= totalPaginas;
   }

   // BUSCAR
   function buscarOportunidades() {
       const busqueda = document.getElementById("buscarOportunidad")
           .value.trim().toLowerCase();

       oportunidadesFiltradas = oportunidades.filter(oportunidad => {
           const contacto = oportunidad.contacto
               ? `${oportunidad.contacto.nombre ?? ""} ${oportunidad.contacto.apellido ?? ""}`
               : "";

           return [
               oportunidad.titulo,
               oportunidad.descripcion,
               oportunidad.estado,
               oportunidad.prioridad,
               contacto
           ].some(valor => String(valor ?? "").toLowerCase().includes(busqueda));
       });

       paginaActual = 1;
       mostrarTabla();
   }

   // ABRIR FORMULARIO NUEVO
   function nuevaOportunidad() {
       formulario.reset();

       document.getElementById("oportunidadId").value = "";
       document.getElementById("tituloFormulario").textContent =
           "Registrar oportunidad";

       document.getElementById("fechaCreacion").value =
           new Date().toISOString().slice(0, 10);

       panelFormulario.hidden = false;

       panelFormulario.scrollIntoView({
           behavior: "smooth"
       });
   }

   // EDITAR
   function editarOportunidad(id) {
       const oportunidad = oportunidades.find(
           item => item.id === Number(id)
       );

       if (!oportunidad) return;

       formulario.reset();

       document.getElementById("oportunidadId").value = oportunidad.id;
       document.getElementById("titulo").value = oportunidad.titulo ?? "";
       document.getElementById("descripcion").value =
           oportunidad.descripcion ?? "";
       document.getElementById("estado").value = oportunidad.estado ?? "";
       document.getElementById("prioridad").value =
           oportunidad.prioridad ?? "";
       document.getElementById("fechaCreacion").value =
           oportunidad.fechaCreacion ?? "";
       document.getElementById("fechaSeguimiento").value =
           oportunidad.fechaSeguimiento ?? "";
       document.getElementById("contacto").value =
           oportunidad.contacto?.id ?? "";

       document.getElementById("tituloFormulario").textContent =
           "Editar oportunidad";

       panelFormulario.hidden = false;

       panelFormulario.scrollIntoView({
           behavior: "smooth"
       });
   }

   // ELIMINAR
   async function eliminarOportunidad(id) {
       if (!confirm("¿Estás seguro de eliminar esta oportunidad?")) {
           return;
       }

       try {
           await consultar(`${API_OPORTUNIDADES}/${id}`, {
               method: "DELETE"
           });

           mostrarMensaje("Oportunidad eliminada correctamente.");
           await cargarOportunidades();

       } catch (error) {
           mostrarMensaje(error.message, true);
       }
   }

   // GUARDAR: CREAR O ACTUALIZAR
   formulario.addEventListener("submit", async function(evento) {
       evento.preventDefault();

       const id = document.getElementById("oportunidadId").value;

       const datos = {
           titulo: document.getElementById("titulo").value.trim(),
           descripcion: document.getElementById("descripcion").value.trim(),
           estado: document.getElementById("estado").value,
           prioridad: document.getElementById("prioridad").value,
           fechaCreacion: document.getElementById("fechaCreacion").value,
           fechaSeguimiento: document.getElementById("fechaSeguimiento").value,
           contacto: {
               id: Number(document.getElementById("contacto").value)
           }
       };

       try {
           if (id) {
               await consultar(`${API_OPORTUNIDADES}/${id}`, {
                   method: "PUT",
                   body: JSON.stringify(datos)
               });

               mostrarMensaje("Oportunidad actualizada correctamente.");

           } else {
               await consultar(API_OPORTUNIDADES, {
                   method: "POST",
                   body: JSON.stringify(datos)
               });

               mostrarMensaje("Oportunidad registrada correctamente.");
           }

           panelFormulario.hidden = true;
           formulario.reset();

           await cargarOportunidades();

       } catch (error) {
           mostrarMensaje(error.message, true);
       }
   });

   // EVENTOS DE LA TABLA
   tabla.addEventListener("click", function(evento) {
       const boton = evento.target.closest("button[data-accion]");

       if (!boton) return;

       const id = boton.dataset.id;

       if (boton.dataset.accion === "editar") {
           editarOportunidad(id);
       }

       if (boton.dataset.accion === "eliminar") {
           eliminarOportunidad(id);
       }
   });

   // EVENTOS DE BOTONES
   document.getElementById("btnNuevaOportunidad")
       .addEventListener("click", nuevaOportunidad);

   document.getElementById("btnCancelar")
       .addEventListener("click", function() {
           panelFormulario.hidden = true;
           formulario.reset();
       });

   document.getElementById("btnBuscar")
       .addEventListener("click", buscarOportunidades);

   document.getElementById("btnLimpiar")
       .addEventListener("click", function() {
           document.getElementById("buscarOportunidad").value = "";
           buscarOportunidades();
       });

   document.getElementById("buscarOportunidad")
       .addEventListener("keydown", function(evento) {
           if (evento.key === "Enter") {
               buscarOportunidades();
           }
       });

   document.getElementById("btnAnterior")
       .addEventListener("click", function() {
           if (paginaActual > 1) {
               paginaActual--;
               mostrarTabla();
           }
       });

   document.getElementById("btnSiguiente")
       .addEventListener("click", function() {
           const totalPaginas = Math.ceil(
               oportunidadesFiltradas.length / registrosPorPagina
           );

           if (paginaActual < totalPaginas) {
               paginaActual++;
               mostrarTabla();
           }
       });

   // INICIAR
   async function iniciarOportunidades() {
       try {
           await cargarContactos();
           await cargarOportunidades();
       } catch (error) {
           mostrarMensaje(
               "No fue posible cargar los contactos. Verifica el servicio /api/contactos.",
               true
           );
       }
   }

   iniciarOportunidades();
