
const API_SALAS = "/api/salas";
const API_RESERVAS = "/api/reservas";

// ==========================================
// 1. INICIALIZACION
// ==========================================

document.addEventListener("DOMContentLoaded", () => {

    cargarSalas();
    cargarReservas();
    cargarIndicadores();

    document.getElementById("form-reserva")
        .addEventListener("submit", registrarReserva);

    document.getElementById("btn-buscar")
        .addEventListener("click", filtrarReservas);

    document.getElementById("btn-mostrar-todas")
        .addEventListener("click", () => {
            document.getElementById("filtro-cliente").value = "";
            document.getElementById("filtro-fecha").value = "";
            cargarReservas();
        });
});


// ==========================================
// 2. CONSULTAR SALAS ACTIVAS
// ==========================================

async function cargarSalas() {

    const contenedor = document.getElementById("contenedor-salas");
    const selector = document.getElementById("sala");

    try {

        const respuesta = await fetch(`${API_SALAS}/activas`);

        if (!respuesta.ok) {
            throw new Error("No se pudieron consultar las salas");
        }

        const salas = await respuesta.json();

        contenedor.innerHTML = "";
        selector.innerHTML =
            '<option value="">Seleccione una sala</option>';

        if (salas.length === 0) {
            contenedor.textContent =
                "No hay salas activas disponibles.";
            return;
        }

        salas.forEach(sala => {

            const tarjeta = document.createElement("div");
            tarjeta.className = "tarjeta-sala";

            const titulo = document.createElement("h3");
            titulo.textContent = sala.nombre;

            const capacidad = document.createElement("p");
            capacidad.textContent =
                `Capacidad: ${sala.capacidad} personas`;

            const tarifa = document.createElement("p");
            tarifa.textContent =
                `Tarifa por hora: Q${Number(sala.tarifaHora).toFixed(2)}`;

            const estado = document.createElement("p");
            estado.textContent = `Estado: ${sala.estado}`;

            tarjeta.append(titulo, capacidad, tarifa, estado);
            contenedor.appendChild(tarjeta);

            const opcion = document.createElement("option");
            opcion.value = sala.idSala;
            opcion.textContent =
                `${sala.nombre} - Q${Number(sala.tarifaHora).toFixed(2)}/hora`;

            selector.appendChild(opcion);
        });

    } catch (error) {
        contenedor.textContent =
            "Error al cargar las salas: " + error.message;
        console.error(error);
    }
}


// ==========================================
// 3. REGISTRAR RESERVA
// ==========================================

async function registrarReserva(evento) {

    evento.preventDefault();

    const formulario = document.getElementById("form-reserva");
    const mensaje = document.getElementById("mensaje");
    const boton = formulario.querySelector('button[type="submit"]');

    const datos = {
        idSala: Number(document.getElementById("sala").value),
        cliente: document.getElementById("cliente").value.trim(),
        fecha: document.getElementById("fecha").value,
        horaInicio: document.getElementById("horaInicio").value,
        horaFin: document.getElementById("horaFin").value
    };

    mensaje.textContent = "Procesando reserva...";
    mensaje.className = "";
    boton.disabled = true;

    try {

        const respuesta = await fetch(API_RESERVAS, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(datos)
        });

        const resultado = await respuesta.json();

        if (!respuesta.ok) {
            let detalle = resultado.mensaje;

            if (!detalle && resultado.detalles) {
                detalle = Object.values(resultado.detalles).join(", ");
            }

            throw new Error(
                detalle || "No se pudo registrar la reserva"
            );
        }

        mensaje.textContent =
            `Reserva registrada correctamente. ` +
            `Número: ${resultado.idReserva}. ` +
            `Costo total: Q${Number(resultado.costoTotal).toFixed(2)}`;

        mensaje.className = "mensaje-exito";

        formulario.reset();

        await cargarReservas();
        await cargarIndicadores();

    } catch (error) {

        mensaje.textContent = "Error: " + error.message;
        mensaje.className = "mensaje-error";
        console.error(error);

    } finally {
        boton.disabled = false;
    }
}


// ==========================================
// 4. CONSULTAR TODAS LAS RESERVAS
// ==========================================

async function cargarReservas() {

    try {

        const respuesta = await fetch(API_RESERVAS);

        if (!respuesta.ok) {
            throw new Error("No se pudieron consultar las reservas");
        }

        const reservas = await respuesta.json();

        mostrarReservas(reservas);

    } catch (error) {

        document.getElementById("contenedor-reservas")
            .textContent = "Error: " + error.message;

        console.error(error);
    }
}


// ==========================================
// 5. MOSTRAR TABLA DE RESERVAS
// ==========================================

function mostrarReservas(reservas) {

    const contenedor = document.getElementById("contenedor-reservas");
    contenedor.innerHTML = "";

    if (reservas.length === 0) {
        contenedor.textContent = "No se encontraron reservas.";
        return;
    }

    const tabla = document.createElement("table");

    const encabezado = document.createElement("thead");
    const filaEncabezado = document.createElement("tr");

    const columnas = [
        "ID",
        "Sala",
        "Cliente",
        "Fecha",
        "Inicio",
        "Fin",
        "Costo",
        "Estado",
        "Acciones"
    ];

    columnas.forEach(nombre => {

        const th = document.createElement("th");
        th.textContent = nombre;
        filaEncabezado.appendChild(th);

    });

    encabezado.appendChild(filaEncabezado);
    tabla.appendChild(encabezado);

    const cuerpo = document.createElement("tbody");

    reservas.forEach(reserva => {

        const fila = document.createElement("tr");

        const valores = [
            reserva.idReserva,
            reserva.sala?.nombre ?? "Sin sala",
            reserva.cliente,
            reserva.fecha,
            reserva.horaInicio,
            reserva.horaFin,
            `Q${Number(reserva.costoTotal).toFixed(2)}`,
            reserva.estado
        ];

        valores.forEach(valor => {

            const td = document.createElement("td");
            td.textContent = valor;
            fila.appendChild(td);

        });

        const celdaAcciones = document.createElement("td");

        if (reserva.estado === "ACTIVA") {

            const botonCancelar = document.createElement("button");
            botonCancelar.type = "button";
            botonCancelar.textContent = "Cancelar";
            botonCancelar.className = "boton-cancelar";

            botonCancelar.addEventListener("click", () => {
                cancelarReserva(reserva.idReserva);
            });

            celdaAcciones.appendChild(botonCancelar);

        } else {

            const etiqueta = document.createElement("span");
            etiqueta.textContent = "Cancelada";
            etiqueta.className = "estado-cancelado";
            celdaAcciones.appendChild(etiqueta);

        }

        fila.appendChild(celdaAcciones);
        cuerpo.appendChild(fila);
    });

    tabla.appendChild(cuerpo);
    contenedor.appendChild(tabla);
}


// ==========================================
// 6. CANCELAR RESERVA
// ==========================================

async function cancelarReserva(idReserva) {

    const confirmar = confirm(
        `¿Está seguro de cancelar la reserva número ${idReserva}?`
    );

    if (!confirmar) {
        return;
    }

    try {

        const respuesta = await fetch(
            `${API_RESERVAS}/${idReserva}/cancelar`,
            { method: "PATCH" }
        );

        const resultado = await respuesta.json();

        if (!respuesta.ok) {
            throw new Error(
                resultado.mensaje || "No se pudo cancelar la reserva"
            );
        }

        alert("Reserva cancelada correctamente.");

        await cargarReservas();
        await cargarIndicadores();

    } catch (error) {

        alert("Error: " + error.message);
        console.error(error);
    }
}


// ==========================================
// 7. INDICADOR DE RESERVAS ACTIVAS
// ==========================================

async function cargarIndicadores() {

    const indicador = document.getElementById(
        "total-reservas-activas"
    );

    try {

        const respuesta = await fetch(
            `${API_RESERVAS}/indicadores/activas`
        );

        if (!respuesta.ok) {
            throw new Error("No se pudo consultar el indicador");
        }

        const datos = await respuesta.json();

        indicador.textContent = datos.reservasActivas;

    } catch (error) {

        indicador.textContent = "No disponible";
        console.error(error);
    }
}


// ==========================================
// 8. FILTRAR RESERVAS
// ==========================================

async function filtrarReservas() {

    const cliente = document.getElementById(
        "filtro-cliente"
    ).value.trim();

    const fecha = document.getElementById(
        "filtro-fecha"
    ).value;

    let url = API_RESERVAS;

    try {

        if (cliente && fecha) {
            // Ambos filtros: combinar resultados
            // usando las consultas existentes de la API.
            const respuesta = await fetch(
                `${API_RESERVAS}/cliente?nombre=${encodeURIComponent(cliente)}`
            );

            if (!respuesta.ok) {
                throw new Error("Error al buscar por cliente");
            }

            const reservas = await respuesta.json();

            mostrarReservas(
                reservas.filter(reserva => reserva.fecha === fecha)
            );

            return;
        }

        if (cliente) {
            url = `${API_RESERVAS}/cliente?nombre=${encodeURIComponent(cliente)}`;
        } else if (fecha) {
            url = `${API_RESERVAS}/fecha?fecha=${encodeURIComponent(fecha)}`;
        }

        const respuesta = await fetch(url);

        if (!respuesta.ok) {
            throw new Error("No se pudieron filtrar las reservas");
        }

        const reservas = await respuesta.json();

        mostrarReservas(reservas);

    } catch (error) {

        document.getElementById("contenedor-reservas")
            .textContent = "Error al filtrar: " + error.message;

        console.error(error);
    }
}
