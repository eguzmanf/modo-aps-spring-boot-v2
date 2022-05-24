export class AppDatosContrato {
/*
    constructor(apiDatos) {
        //
        this.apiDatos = apiDatos;
        //
        this.servicioId = document.getElementById('servicioSalud.id');
        this.comunaCodigo = document.getElementById('comuna.codigoComuna');
        this.establecimientoId = document.getElementById('establecimiento.codigoNuevo');
        this.roleName = document.getElementById("roleName");
        //
        this.roleUser = document.getElementById("roleUser");
        this.servicioUser = document.getElementById("servicioUser");
        this.comunaUser = document.getElementById("comunaUser");
        this.accion = document.getElementById("accion");
        //
        this.leyId = document.getElementById("ley.id");
        this.tipoContratoId = document.getElementById("tipoContrato.id");
        this.categoriaProfesionId = document.getElementById("categoriaProfesion.id");
        this.nivelCarreraId = document.getElementById("nivelCarrera.id");
        this.profesionId = document.getElementById("profesion.id");
        this.especialidadId = document.getElementById("especialidad.id");
        this.cargoId = document.getElementById("cargo.id");
        this.asignacionChoferId = document.getElementById("asignacionChofer.id");
        //
        this.jornadaLaboral = document.getElementById("jornadaLaboral");
        this.aniosServicio = document.getElementById("aniosServicio");
        this.fechaIngreso = document.getElementById("fechaIngreso");
        this.bieniosId = document.getElementById("bienios.id");
        this.previsionId = document.getElementById("prevision.id");
        this.isapreId = document.getElementById("isapre.id");
        this.sueldoBase = document.getElementById("sueldoBase");
        this.totalHaberes = document.getElementById("totalHaberes");
    }

    initAppCrear() {

    }

    domIsReadyAppCrear() {
        //
        if (document.readyState === 'complete') {
            //
            // console.log("Acción: " + this.accion.value);
            // console.log("Su Rol es: " + this.roleUser.value);
            // console.log("Su Id de Servicio: " + this.servicioUser.value);
            // console.log("Su Id de Comuna: " + this.comunaUser.value);
            // if (this.accion.value === "crear") {
                this.leyId.addEventListener("change", e => {
                    let leyTarget = e.target.value;
                    console.log(leyTarget);
                    if(leyTarget == "1") {
                        this.apiDatos.fetchTipoContratosByLeyId(leyTarget)
                            .then(data => {
                                let dataObject = data.resultado;
                                console.log(dataObject);
                                this.tipoContratoByLeyIdChangeSelectList(dataObject, this.tipoContratoId);

                                this.apiDatos.fetchCategoriaProfesionLey19378()
                                    .then(data => {
                                        let dataObject = data.resultado;
                                        console.log(dataObject);
                                        this.categoriaProfesionChangeSelectList(dataObject, this.categoriaProfesionId)

                                        this.apiDatos.fetchNivelCarreraLey19387()
                                            .then(data => {
                                                let dataObject = data.resultado;
                                                console.log(dataObject);
                                                this.nivelCarreraChangeSelectList(dataObject, this.nivelCarreraId)
                                            });
                                    });
                            });

                        this.categoriaProfesionId.addEventListener("change", e => {
                            let categoriaTarget = e.target.value;
                            console.log(categoriaTarget);
                            this.apiDatos.fetchProfesionLey19378(categoriaTarget)
                                .then(data => {
                                    let dataObject = data.resultado;
                                    console.log(dataObject);
                                    this.profesionChangeSelectList(dataObject, this.profesionId)
                                });
                        });

                        this.profesionId.addEventListener("change", e => {
                            let profesionTarget = e.target.value;
                            console.log(profesionTarget);
                            this.apiDatos.fetchEspecialidad(profesionTarget)
                                .then(data => {
                                    let dataObject = data.resultado;
                                    console.log(dataObject);
                                    this.especialidadChangeSelectList(dataObject, this.especialidadId, profesionTarget, leyTarget)
                                });
                        });

                        this.cargoId.addEventListener("change", e => {
                            let cargoTarget = e.target.value;
                            console.log(cargoTarget);
                            this.apiDatos.fetchAsignacionChofer(cargoTarget)
                                .then(data => {
                                    let dataObject = data.resultado;
                                    console.log(dataObject);
                                    this.asignacionChoferChangeSelectList(dataObject, this.asignacionChoferId, cargoTarget)
                                });
                        });
                    } else if(leyTarget == "2" || leyTarget == "3") {
                        this.apiDatos.fetchTipoContratosByLeyId(leyTarget)
                            .then(data => {
                                let dataObject = data.resultado;
                                console.log(dataObject);
                                this.tipoContratoByLeyIdChangeSelectList(dataObject, this.tipoContratoId, leyTarget);

                                this.apiDatos.fetchCategoriaProfesionLeyHonorariosCodigoDelTrabajo()
                                    .then(data => {
                                        let dataObject = data.resultado;
                                        console.log(dataObject);
                                        this.categoriaProfesionChangeSelectList(dataObject, this.categoriaProfesionId, leyTarget)

                                        this.apiDatos.fetchNivelCarreraLeyHonorariosCodigoDelTrabajo()
                                            .then(data => {
                                                let dataObject = data.resultado;
                                                console.log(dataObject);
                                                this.nivelCarreraChangeSelectList(dataObject, this.nivelCarreraId, leyTarget)

                                                this.apiDatos.fetchProfesionLeyHonorariosCodigoDelTrabajo()
                                                    .then(data => {
                                                        let dataObject = data.resultado;
                                                        console.log(dataObject);
                                                        this.profesionChangeSelectList(dataObject, this.profesionId)
                                                    });
                                            });
                                    });
                            });

                        this.profesionId.addEventListener("change", e => {
                            let profesionTarget = e.target.value;
                            console.log(profesionTarget);
                            this.apiDatos.fetchEspecialidad(profesionTarget)
                                .then(data => {
                                    let dataObject = data.resultado;
                                    console.log(dataObject);
                                    this.especialidadChangeSelectList(dataObject, this.especialidadId, profesionTarget, leyTarget)
                                });
                        });

                        this.cargoId.addEventListener("change", e => {
                            let cargoTarget = e.target.value;
                            console.log(cargoTarget);
                            this.apiDatos.fetchAsignacionChofer(cargoTarget)
                                .then(data => {
                                    let dataObject = data.resultado;
                                    console.log(dataObject);
                                    this.asignacionChoferChangeSelectList(dataObject, this.asignacionChoferId, cargoTarget)
                                });
                        });
                    }
                });
            // }
        }
    }

    tipoContratoByLeyIdChangeSelectList(dataObject, objectHtmlSelect, leyTarget) {
        //
        let objectHS = objectHtmlSelect;
        objectHS.length = 0;

        let defaultOption = document.createElement('option');
        defaultOption.value = '';
        defaultOption.text = '-- Seleccionar --';
        objectHS.add(defaultOption);
        objectHS.selectedIndex = '';

        let option;
        Object.keys(dataObject).forEach(function (key) {
            option = document.createElement('option');
            option.text = dataObject[key].tipoContrato;
            option.value = dataObject[key].id;
            objectHS.add(option);
        });

        if(leyTarget == 2 || leyTarget == 3) {
            objectHS.selectedIndex = 1;
        }
    }

    categoriaProfesionChangeSelectList(dataObject, objectHtmlSelect, leyTarget) {
        //
        let objectHS = objectHtmlSelect;
        objectHS.length = 0;

        let defaultOption = document.createElement('option');
        defaultOption.value = '';
        defaultOption.text = '-- Seleccionar --';
        objectHS.add(defaultOption);
        objectHS.selectedIndex = '';

        let option;
        Object.keys(dataObject).forEach(function (key) {
            option = document.createElement('option');
            option.text = dataObject[key].categoriaProfesion;
            option.value = dataObject[key].id;
            objectHS.add(option);
        });

        if(leyTarget == 2 || leyTarget == 3) {
            objectHS.selectedIndex = 1;
        }
    }

    nivelCarreraChangeSelectList(dataObject, objectHtmlSelect, leyTarget) {
        //
        let objectHS = objectHtmlSelect;
        objectHS.length = 0;

        let defaultOption = document.createElement('option');
        defaultOption.value = '';
        defaultOption.text = '-- Seleccionar --';
        objectHS.add(defaultOption);
        objectHS.selectedIndex = '';

        let option;
        Object.keys(dataObject).forEach(function (key) {
            option = document.createElement('option');
            if (dataObject[key].id == 16) {
                option.text = "N/A";
                option.value = 16;
            } else {
                option.text = dataObject[key].nivelCarrera;
                option.value = dataObject[key].id;
            }
            objectHS.add(option);
        });

        if(leyTarget == 2 || leyTarget == 3) {
            objectHS.selectedIndex = 1;
        }
    }

    profesionChangeSelectList(dataObject, objectHtmlSelect) {
        //
        let objectHS = objectHtmlSelect;
        objectHS.length = 0;

        let defaultOption = document.createElement('option');
        defaultOption.value = '';
        defaultOption.text = '-- Seleccionar --';
        objectHS.add(defaultOption);
        objectHS.selectedIndex = '';

        let option;
        Object.keys(dataObject).forEach(function (key) {
            option = document.createElement('option');
            option.text = dataObject[key].profesion;
            option.value = dataObject[key].id;
            objectHS.add(option);
        });
    }

    especialidadChangeSelectList(dataObject, objectHtmlSelect, profesionTarget, leyTarget) {
        //
        let objectHS = objectHtmlSelect;
        objectHS.length = 0;

        let defaultOption = document.createElement('option');
        defaultOption.value = '';
        defaultOption.text = '-- Seleccionar --';
        objectHS.add(defaultOption);
        objectHS.selectedIndex = '';

        let option;
        Object.keys(dataObject).forEach(function (key) {
            option = document.createElement('option');
            option.text = dataObject[key].especialidad;
            option.value = dataObject[key].id;
            objectHS.add(option);
        });

        if(profesionTarget != 2 && leyTarget == 1) {
            objectHS.selectedIndex = 1;
        } else if(profesionTarget != 24 && (leyTarget == 2 || leyTarget == 3)) {
            objectHS.selectedIndex = 1;
        }
    }

    asignacionChoferChangeSelectList(dataObject, objectHtmlSelect, cargoTarget) {
        //
        let objectHS = objectHtmlSelect;
        objectHS.length = 0;

        let defaultOption = document.createElement('option');
        defaultOption.value = '';
        defaultOption.text = '-- Seleccionar --';
        objectHS.add(defaultOption);
        objectHS.selectedIndex = '';

        let option;
        Object.keys(dataObject).forEach(function (key) {
            option = document.createElement('option');
            option.text = dataObject[key].asignacionChofer;
            option.value = dataObject[key].id;
            objectHS.add(option);
        });

        if(cargoTarget != 5) {
            objectHS.selectedIndex = 1;
        }
    }
*/
}