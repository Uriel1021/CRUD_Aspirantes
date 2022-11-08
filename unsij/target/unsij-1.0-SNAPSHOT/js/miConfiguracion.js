/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */
$(document).ready(function () {
    $("#eliminarBtn").click(function () {
        eliminarAspirante(id);
    });
    $("#nuevo").click(function () {
        envia();
    });
    $("#modificarBtn").click(function () {
        actualizarAspirante(idA);
    });
});

const formulario = document.getElementById('formulario');
const inputs = document.querySelectorAll('#formulario input');
const expresiones = {
    nombre: /^[a-zA-ZÀ-ÿ\s]{1,40}$/,
    apPaterno: /^[a-zA-ZÀ-ÿ\s]{1,40}$/,
    apMaterno: /^[a-zA-ZÀ-ÿ\s]{1,40}$/,
    procedencia: /^[a-zA-ZÀ-ÿ\s]{1,40}$/,
    correo: /^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/,
    telefono: /^\d{7,10}$/, // 7 a 10 numeros
    especialidad: /^[a-zA-ZÀ-ÿ\s]{4,20}$/,
    direccion: /^[a-zA-Z0-9\_\-]{10,30}$/
}
const campos = {
    nombre: false,
    apPaterno: false,
    apMaterno: false,
    procedencia: false,
    correo: false,
    telefono: false,
    especialidad: false,
    direccion: false
}
const validarFormulario = (e) => {
    switch (e.target.name) {
        case "nombre":
            validarCampo(expresiones.nombre, e.target, 'nombre');
            break;
        case "apPaterno":
            validarCampo(expresiones.nombre, e.target, 'apPaterno');
            break;
        case "apMaterno":
            validarCampo(expresiones.apMaterno, e.target, 'apMaterno');
            break;
        case "procedencia":
            validarCampo(expresiones.procedencia, e.target, 'procedencia');
            break;
        case "correo":
            validarCampo(expresiones.correo, e.target, 'correo');
            break;
        case "telefono":
            validarCampo(expresiones.telefono, e.target, 'telefono');
            break;
        case "especialidad":
            validarCampo(expresiones.especialidad, e.target, 'especialidad');
            break;
        case "direccion":
            validarCampo(expresiones.direccion, e.target, 'direccion');
            break;
    }
}
const validarCampo = (expresion, input, campo) => {
    if (expresion.test(input.value)) {
        document.getElementById(`grupo__${campo}`).classList.remove('formulario__grupo-incorrecto');
        document.getElementById(`grupo__${campo}`).classList.add('formulario__grupo-correcto');
        document.querySelector(`#grupo__${campo} i`).classList.add('fa-check-circle');
        document.querySelector(`#grupo__${campo} i`).classList.remove('fa-times-circle');
        document.querySelector(`#grupo__${campo} .formulario__input-error`).classList.remove('formulario__input-error-activo');
        campos[campo] = true;
    } else {
        document.getElementById(`grupo__${campo}`).classList.add('formulario__grupo-incorrecto');
        document.getElementById(`grupo__${campo}`).classList.remove('formulario__grupo-correcto');
        document.querySelector(`#grupo__${campo} i`).classList.add('fa-times-circle');
        document.querySelector(`#grupo__${campo} i`).classList.remove('fa-check-circle');
        document.querySelector(`#grupo__${campo} .formulario__input-error`).classList.add('formulario__input-error-activo');
        campos[campo] = false;
    }
}
function preguntaEliminar(idAspirante) {
    id = idAspirante;
    $('#eliminarAspirante').modal('show');
}
function mostrarDatos(idAsp) {
    idA = idAsp;
    $('#modificarAspirante').modal('show');
    formularioAsp(idA);
}

//mostrarDatos
function loadDoc() {
    const xhttp = new XMLHttpRequest();
    xhttp.onload = function () {
        myFunction(this);
    }
    xhttp.open("GET", "Aspirante?action=list");
    xhttp.send();
}
//MostrarTabla
function myFunction(xml) {
    const myArray = JSON.parse(xml.responseText);
    //Cabecera de mi tabla
    let table = "<tr><th>ID</th><th>Nombre Completo</th><th>Email</th><th>Telefono</th><th>Especialidad</th><th>Acciones</th></tr>";
    //Recorrer todos los elementos y meterlos en filas y columnas
    for (var i = 0; i < myArray.length; i++) {
        table += "<tr><td>" +
                myArray[i].id_aspirante +
                "</td><td>" +
                myArray[i].nombre + " " + myArray[i].ap_paterno + " " + myArray[i].ap_materno +
                "</td><td>" +
                myArray[i].correo +
                "</td><td>" +
                myArray[i].telefono +
                "</td><td>" +
                myArray[i].especialidad +
                "</td><td>" +
                ' <a href="javascript:detalleAspirante(' + myArray[i].id_aspirante + ')" class="btn btn-outline-primary" role="button">Detalle</a> ' +
                ' <a href="javascript:preguntaEliminar(' + myArray[i].id_aspirante + ')" class="btn btn-outline-danger" role="button">Eliminar</a> ' +
                ' <a href="javascript:mostrarDatos(' + myArray[i].id_aspirante + ')" class="btn btn-outline-success" role="button">Modificar</a> ' +
                "</td></tr>";
    }
    // Mandar la tabla a lista
    document.getElementById("tablaAspirantes").innerHTML = table;
}

//Enviar formulario...AgregaNuevo
function envia() {
    let datos = document.getElementById('formulario');
    var url = "Aspirante?action=store";
    $.ajax({
        url: url,
        type: "POST",
        data: {
            nombre: datos.elements[0].value,
            appaterno: datos.elements[1].value,
            apmaterno: datos.elements[2].value,
            procedencia: datos.elements[3].value,
            correo: datos.elements[4].value,
            telefono: datos.elements[5].value,
            especialidad: datos.elements[6].value,
            direccion: datos.elements[7].value
        },
        success: function (data) {

            var respuestaJson = JSON.stringify(data);
            var respuestaObject = JSON.parse(respuestaJson);
            if (respuestaObject.respuesta == "true") {
                alert("Se agrego correctamente");
            } else {
                alert("No se pudo agregar");
            }
            $('#nuevoAspirante').modal('hide') 
            loadDoc();
            formulario.reset();
        },
        dataType: "JSON"
    });
}
//MostrarDatosEnFormulario
function detalleAspirante(id) {
    var url = "Aspirante?action=details&id=" + id;
    $.ajax({
        url: url,
        type: "GET",
        success: function (data) {
            var respuestaJson = JSON.stringify(data);
            var respuestaObject = JSON.parse(respuestaJson);
            $("#nombre").text("Nombre: " + respuestaObject.nombre);
            $("#apPaterno").text("Apellido Paterno: " + respuestaObject.ap_paterno);
            $("#apMaterno").text("Apellido Materno: " + respuestaObject.ap_materno);
            $("#procedencia").text("Procedencia: " + respuestaObject.procedencia);
            $("#email").text("Correo: " + respuestaObject.correo);
            $("#telefono").text("Telefono: " + respuestaObject.telefono);
            $("#especialidad").text("Especialidad: " + respuestaObject.especialidad);
            $("#direccion").text("Dirección: " + respuestaObject.direccion);
            $('#detalleAspirante').modal('show');
        },
        dataType: "JSON"
    });
}
//EliminarDatos
function eliminarAspirante(id) {
    var url = "Aspirante?action=delete&id=" + id;
    $.ajax({
        url: url,
        type: "GET",
        success: function (data) {
            var respuestaJson = JSON.stringify(data);
            var respuestaObject = JSON.parse(respuestaJson);
            if (respuestaObject.respuesta = "true") {
                alert("Se eliminó correctamente");
            } else {
                alert("No se pudo eliminar");
            }
            loadDoc();
            $('#eliminarAspirante').modal('hide')
        },
        dataType: "JSON"
    });
}

//RecibirDatos
function formularioAsp(id) {
    var url = "Aspirante?action=details&id=" + id;
    $.ajax({
        url: url,
        type: "GET",
        success: function (data) {
            var respuestaJson = JSON.stringify(data);
            var respuestaObject = JSON.parse(respuestaJson);
            $('#idTxt').val(respuestaObject.id_aspirante);
            $("#nombreTxt").val(respuestaObject.nombre);
            $("#apPaternoTxt").val(respuestaObject.ap_paterno);
            $("#apMaternoTxt").val(respuestaObject.ap_materno);
            $("#procedenciaTxt").val(respuestaObject.procedencia);
            $("#correoTxt").val(respuestaObject.correo);
            $("#telefonoTxt").val(respuestaObject.telefono);
            $("#especialidadTxt").val(respuestaObject.especialidad);
            $("#direccionTxt").val(respuestaObject.direccion);
            $('#detalleAspiranteTxt').modal('show');
        },
        dataType: "JSON"
    });
}
//Validar formulario y actualizar
function actualizarAspirante(id) {
    var reg = /^[A-Z0-9._%+-]+@([A-Z0-9-]+.)+[A-Z]{2,4}$/i;
    if (document.fvalid.Nombre.value.length == 0) {
        alert("Tienes que escribir tu nombre")
        document.fvalid.Nombre.focus()
        return 0;
    } else if (document.fvalid.ApPaterno.value.length == 0) {
        alert("Tienes que escribir tu apellido paterno")
        document.fvalida.ApPaterno.focus()
        return 0;
    } else if (document.fvalid.ApMaterno.value.length == 0) {
        alert("Tienes que escribir tu apellido materno")
        document.fvalida.ApMaterno.focus()
        return 0;
    } else if (document.fvalid.Procedencia.value.length == 0) {
        alert("Tienes que escribir tu procedencia")
        document.fvalida.Procedencia.focus()
        return 0;
    } else if (document.fvalid.Correo.value.length == 0) {
        alert("Tienes que escribir tu correo electronico")
        document.fvalida.Correo.focus()
        return 0;
    } else if (document.fvalid.Telefono.value.length == 0) {
        alert("Tienes que escribir tu telefono")
        document.fvalida.Telefono.focus()
        return 0;
    } else if (document.fvalid.Especialidad.value.length == 0) {
        alert("Tienes que escribir tu especialidad")
        document.fvalida.Especialidad.focus()
        return 0;
    } else if (document.fvalid.Direccion.value.length == 0) {
        alert("Tienes que escribir tu dirección")
        document.fvalida.Direccion.focus()
        return 0;
    } else {
        let datos = document.getElementById('dat');
        var url = "Aspirante?action=update";
        $.ajax({
            url: url,
            type: "POST",
            data: {
                id_aspirante: datos.elements[0].value,
                nombre: datos.elements[1].value,
                appaterno: datos.elements[2].value,
                apmaterno: datos.elements[3].value,
                procedencia: datos.elements[4].value,
                correo: datos.elements[5].value,
                telefono: datos.elements[6].value,
                especialidad: datos.elements[7].value,
                direccion: datos.elements[8].value
            },
            success: function (data) {
                var respuestaJson = JSON.stringify(data);
                var respuestaObject = JSON.parse(respuestaJson);
                if (respuestaObject.respuesta = "true") {
                    alert("Se actualizó correctamente")
                    loadDoc();
                } else {
                    alert("No se pudo actualizar correctamente")
                }
                $('#modificarAspirante').modal('hide')
            },
            dataType: "JSON"
        });
    }
}
