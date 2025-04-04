export class AppContratoCrear {

    constructor(uiCrear, apiCrear) {
        //
        this.uiCrear = uiCrear;
        this.apiCrear = apiCrear;
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
            const servicioId = this.servicioId.value;
            const comunaCodigo = this.comunaCodigo.value;
            const establecimientoId = this.establecimientoId.value;

            // console.log("Acción: " + this.accion.value)
            // console.log("Establecimiento Id: " + establecimientoId);
            let role = this.getRole(this.roleName.value);
            // console.log(role);
            // console.log("Su Rol es: " + this.roleUser.value);
            // console.log("Su Id de Servicio: " + this.servicioUser.value);
            // console.log("Su Id de Comuna: " + this.comunaUser.value)
            //
            if (this.accion.value === "crear") {
                if (this.roleUser.value === "[ROLE_SERVICIO]") {
                    this.apiCrear.fetchServicioByIdServicio(this.servicioUser.value)
                        .then(data => {
                            let dataObject = data.resultado;
                            console.log(dataObject);
                            this.servicioByServicioReady(dataObject, this.servicioId);

                            this.apiCrear.fetchComunasByIdServicioRole(this.servicioUser.value)
                                .then(data => {
                                    let dataObject = data.resultado;
                                    console.log(dataObject);
                                    this.comunasByServicioChangeSelectList(dataObject, this.comunaCodigo, this.comunaUser.value);

                                    this.apiCrear.fetchEstablecimientosByIdComunaRoleServicio(this.comunaUser.value)
                                        .then(data => {
                                            let dataObject = data.resultado;
                                            console.log(dataObject);
                                            this.establecimientosByComunaChangeSelectList(dataObject, this.establecimientoId)
                                        });
                                });
                        });
                } else if (this.roleUser.value === "[ROLE_COMUNA]") {
                    this.apiCrear.fetchServicioByIdServicio(this.servicioUser.value)
                        .then(data => {
                            let dataObject = data.resultado;
                            console.log(dataObject);
                            this.servicioByServicioReady(dataObject, this.servicioId);

                            this.apiCrear.fetchComunaByIdComunaRoleComuna(this.comunaUser.value)
                                .then(data => {
                                    let dataObject = data.resultado;
                                    console.log(dataObject);
                                    this.comunaByComunaReady(dataObject, this.comunaCodigo)

                                    this.apiCrear.fetchEstablecimientosByIdComunaRoleServicio(this.comunaUser.value)
                                        .then(data => {
                                            let dataObject = data.resultado;
                                            console.log(dataObject);
                                            this.establecimientosByComunaChangeSelectList(dataObject, this.establecimientoId, establecimientoId)
                                        });
                                });
                        });
                } else if (this.roleUser.value === "[ROLE_LA_GRANJA]") {
                    this.apiCrear.fetchServiciosLaGranjaRoleLaGranja()
                        .then(data => {
                            let dataObject = data.resultado;
                            console.log(dataObject);
                            this.servicioLaGranjaReady(dataObject, this.servicioId, this.servicioUser.value)

                            this.apiCrear.fetchComunasByIdServicioRole(this.servicioUser.value)
                                .then(data => {
                                    let dataObject = data.resultado;
                                    console.log(dataObject);
                                    this.comunasByServicioChangeSelectList(dataObject, this.comunaCodigo, this.comunaUser.value);

                                    this.apiCrear.fetchEstablecimientosByIdComunaRoleServicio(this.comunaUser.value)
                                        .then(data => {
                                            let dataObject = data.resultado;
                                            console.log(dataObject);
                                            this.establecimientosByComunaChangeSelectList(dataObject, this.establecimientoId)
                                        });
                                });
                        });
                }
            }

            if(this.accion.value === "editar") {
                //
                if (this.roleUser.value === "[ROLE_MINSAL]") {
                    this.apiCrear.fetchComunasByIdSarvicio(servicioId)
                        .then(data => {
                            let dataObject = data.resultado;
                            console.log(dataObject)
                            this.comunasByServicioChangeSelectList(dataObject, this.comunaCodigo, comunaCodigo);

                            this.apiCrear.fetchEstablecimientosByIdComuna(comunaCodigo)
                                .then(data => {
                                    let dataObject = data.resultado;
                                    console.log(dataObject);
                                    console.log(establecimientoId);
                                    this.establecimientosByComunaChangeSelectList(dataObject, this.establecimientoId, establecimientoId)
                                });
                        });
                } else if (this.roleUser.value === "[ROLE_SERVICIO]") {
                    this.apiCrear.fetchServicioByIdServicio(servicioId)
                        .then(data => {
                            let dataObject = data.resultado;
                            console.log(dataObject);
                            this.servicioByServicioReady(dataObject, this.servicioId);

                            this.apiCrear.fetchComunasByIdServicioRole(servicioId)
                                .then(data => {
                                    let dataObject = data.resultado;
                                    console.log(dataObject);
                                    this.comunasByServicioChangeSelectList(dataObject, this.comunaCodigo, comunaCodigo);

                                    this.apiCrear.fetchEstablecimientosByIdComunaRoleServicio(comunaCodigo)
                                        .then(data => {
                                            let dataObject = data.resultado;
                                            console.log(dataObject);
                                            this.establecimientosByComunaChangeSelectList(dataObject, this.establecimientoId, establecimientoId)
                                        });
                                });
                        });
                } else if (this.roleUser.value === "[ROLE_COMUNA]") {
                    this.apiCrear.fetchServicioByIdServicio(servicioId)
                        .then(data => {
                            let dataObject = data.resultado;
                            console.log(dataObject);
                            this.servicioByServicioReady(dataObject, this.servicioId);

                            this.apiCrear.fetchComunaByIdComunaRoleComuna(comunaCodigo)
                                .then(data => {
                                    let dataObject = data.resultado;
                                    console.log(dataObject);
                                    this.comunaByComunaReady(dataObject, this.comunaCodigo)

                                    this.apiCrear.fetchEstablecimientosByIdComunaRoleServicio(comunaCodigo)
                                        .then(data => {
                                            let dataObject = data.resultado;
                                            console.log(dataObject);
                                            this.establecimientosByComunaChangeSelectList(dataObject, this.establecimientoId, establecimientoId)
                                        });
                                });
                        });
                } else if (this.roleUser.value === "[ROLE_LA_GRANJA]") {
                    this.apiCrear.fetchServiciosLaGranjaRoleLaGranja()
                        .then(data => {
                            let dataObject = data.resultado;
                            console.log(dataObject);
                            this.servicioLaGranjaReady(dataObject, this.servicioId, servicioId)

                            this.apiCrear.fetchComunasByIdServicioRole(servicioId)
                                .then(data => {
                                    let dataObject = data.resultado;
                                    console.log(dataObject);
                                    this.comunasByServicioChangeSelectList(dataObject, this.comunaCodigo, comunaCodigo);

                                    this.apiCrear.fetchEstablecimientosByIdComunaRoleServicio(comunaCodigo)
                                        .then(data => {
                                            let dataObject = data.resultado;
                                            console.log(dataObject);
                                            this.establecimientosByComunaChangeSelectList(dataObject, this.establecimientoId, establecimientoId)
                                        });
                                });
                        });
                }
            }

                console.log("Acción: " + this.accion.value);
                console.log("Su Rol es: " + this.roleUser.value);
                console.log("Su Id de Servicio: " + this.servicioUser.value);
                console.log("Su Id de Comuna: " + this.comunaUser.value);

                this.getDatosContratosInit();

            // if (this.accion.value === "crear") {
                this.leyId.addEventListener("change", e => {
                    let leyTarget = e.target.value;
                    console.log(leyTarget);
                    if(leyTarget == "1") {
                        this.apiCrear.fetchTipoContratosByLeyId(leyTarget)
                            .then(data => {
                                let dataObject = data.resultado;
                                console.log(dataObject);
                                this.tipoContratoByLeyIdChangeSelectList(dataObject, this.tipoContratoId);

                                this.apiCrear.fetchCategoriaProfesionLey19378()
                                    .then(data => {
                                        let dataObject = data.resultado;
                                        console.log(dataObject);
                                        this.categoriaProfesionChangeSelectList(dataObject, this.categoriaProfesionId)

                                        this.apiCrear.fetchNivelCarreraLey19387()
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
                            this.apiCrear.fetchProfesionLey19378(categoriaTarget)
                                .then(data => {
                                    let dataObject = data.resultado;
                                    console.log(dataObject);
                                    this.profesionChangeSelectList(dataObject, this.profesionId)
                                });
                        });

                        this.profesionId.addEventListener("change", e => {
                            let profesionTarget = e.target.value;
                            console.log(profesionTarget);
                            this.apiCrear.fetchEspecialidad(profesionTarget)
                                .then(data => {
                                    let dataObject = data.resultado;
                                    console.log(dataObject);
                                    this.especialidadChangeSelectList(dataObject, this.especialidadId, profesionTarget, leyTarget)
                                });
                        });

                        this.cargoId.addEventListener("change", e => {
                            let cargoTarget = e.target.value;
                            console.log(cargoTarget);
                            this.apiCrear.fetchAsignacionChofer(cargoTarget)
                                .then(data => {
                                    let dataObject = data.resultado;
                                    console.log(dataObject);
                                    this.asignacionChoferChangeSelectList(dataObject, this.asignacionChoferId, cargoTarget)
                                });
                        });
                    } else if(leyTarget == "2" || leyTarget == "3") {
                        this.apiCrear.fetchTipoContratosByLeyId(leyTarget)
                            .then(data => {
                                let dataObject = data.resultado;
                                console.log(dataObject);
                                this.tipoContratoByLeyIdChangeSelectList(dataObject, this.tipoContratoId, leyTarget);

                                this.apiCrear.fetchCategoriaProfesionLeyHonorariosCodigoDelTrabajo()
                                    .then(data => {
                                        let dataObject = data.resultado;
                                        console.log(dataObject);
                                        this.categoriaProfesionChangeSelectList(dataObject, this.categoriaProfesionId, leyTarget)

                                        this.apiCrear.fetchNivelCarreraLeyHonorariosCodigoDelTrabajo()
                                            .then(data => {
                                                let dataObject = data.resultado;
                                                console.log(dataObject);
                                                this.nivelCarreraChangeSelectList(dataObject, this.nivelCarreraId, leyTarget)

                                                this.apiCrear.fetchProfesionLeyHonorariosCodigoDelTrabajo()
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
                            this.apiCrear.fetchEspecialidad(profesionTarget)
                                .then(data => {
                                    let dataObject = data.resultado;
                                    console.log(dataObject);
                                    this.especialidadChangeSelectList(dataObject, this.especialidadId, profesionTarget, leyTarget)
                                });
                        });

                        this.cargoId.addEventListener("change", e => {
                            let cargoTarget = e.target.value;
                            console.log(cargoTarget);
                            this.apiCrear.fetchAsignacionChofer(cargoTarget)
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

    getDatosContratosInit() {
        if (this.accion.value === "editar") {
            if(this.leyId.value == "1") {
                this.apiCrear.fetchTipoContratosByLeyId(this.leyId.value)
                    .then(data => {
                        let dataObject = data.resultado;
                        this.tipoContratoByLeyIdChangeSelectList(dataObject, this.tipoContratoId, null, this.tipoContratoId.value);

                        this.apiCrear.fetchCategoriaProfesionLey19378()
                            .then(data => {
                                let dataObject = data.resultado;
                                this.categoriaProfesionChangeSelectList(dataObject, this.categoriaProfesionId, null, this.categoriaProfesionId.value)

                                this.apiCrear.fetchNivelCarreraLey19387()
                                    .then(data => {
                                        let dataObject = data.resultado;
                                        this.nivelCarreraChangeSelectList(dataObject, this.nivelCarreraId, null, this.nivelCarreraId.value)

                                        this.apiCrear.fetchProfesionLey19378(this.categoriaProfesionId.value)
                                            .then(data => {
                                                let dataObject = data.resultado;
                                                this.profesionChangeSelectList(dataObject, this.profesionId, this.profesionId.value)

                                                this.apiCrear.fetchEspecialidad(this.profesionId.value)
                                                    .then(data => {
                                                        let dataObject = data.resultado;
                                                        this.especialidadChangeSelectList(dataObject, this.especialidadId, null, null, this.especialidadId.value)

                                                        this.apiCrear.fetchAsignacionChofer(this.cargoId.value)
                                                            .then(data => {
                                                                let dataObject = data.resultado;
                                                                this.asignacionChoferChangeSelectList(dataObject, this.asignacionChoferId, null, this.asignacionChoferId.value)
                                                            });
                                                    });
                                            });
                                    });
                            });
                    });

                this.categoriaProfesionId.addEventListener("change", e => {
                    let categoriaTarget = e.target.value;
                    this.apiCrear.fetchProfesionLey19378(categoriaTarget)
                        .then(data => {
                            let dataObject = data.resultado;
                            this.profesionChangeSelectList(dataObject, this.profesionId)
                        });
                });

                this.profesionId.addEventListener("change", e => {
                    let profesionTarget = e.target.value;
                    this.apiCrear.fetchEspecialidad(profesionTarget)
                        .then(data => {
                            let dataObject = data.resultado;
                            this.especialidadChangeSelectList(dataObject, this.especialidadId, profesionTarget, this.leyId.value)
                        });
                });

                this.cargoId.addEventListener("change", e => {
                    let cargoTarget = e.target.value;
                    this.apiCrear.fetchAsignacionChofer(cargoTarget)
                        .then(data => {
                            let dataObject = data.resultado;
                            this.asignacionChoferChangeSelectList(dataObject, this.asignacionChoferId, cargoTarget)
                        });
                });
            } else if(this.leyId.value == "2" || this.leyId.value == "3") {
                //
                this.apiCrear.fetchTipoContratosByLeyId(this.leyId.value)
                    .then(data => {
                        let dataObject = data.resultado;
                        this.tipoContratoByLeyIdChangeSelectList(dataObject, this.tipoContratoId, this.leyId.value);

                        this.apiCrear.fetchCategoriaProfesionLeyHonorariosCodigoDelTrabajo()
                            .then(data => {
                                let dataObject = data.resultado;
                                this.categoriaProfesionChangeSelectList(dataObject, this.categoriaProfesionId, this.leyId.value)

                                this.apiCrear.fetchNivelCarreraLeyHonorariosCodigoDelTrabajo()
                                    .then(data => {
                                        let dataObject = data.resultado;
                                        this.nivelCarreraChangeSelectList(dataObject, this.nivelCarreraId, this.leyId.value, this.nivelCarreraId.value)

                                        this.apiCrear.fetchProfesionLeyHonorariosCodigoDelTrabajo()
                                            .then(data => {
                                                let dataObject = data.resultado;
                                                this.profesionChangeSelectList(dataObject, this.profesionId, this.profesionId.value)

                                                this.apiCrear.fetchEspecialidad(this.profesionId.value)
                                                    .then(data => {
                                                        let dataObject = data.resultado;
                                                        this.especialidadChangeSelectList(dataObject, this.especialidadId, this.profesionId.value, this.leyId.value, this.especialidadId.value)

                                                        this.apiCrear.fetchAsignacionChofer(this.cargoId.value)
                                                            .then(data => {
                                                                let dataObject = data.resultado;
                                                                this.asignacionChoferChangeSelectList(dataObject, this.asignacionChoferId, null, this.asignacionChoferId.value)
                                                            });
                                                    });
                                            });
                                    });
                            });
                    });

                this.profesionId.addEventListener("change", e => {
                    let profesionTarget = e.target.value;
                    this.apiCrear.fetchEspecialidad(profesionTarget)
                        .then(data => {
                            let dataObject = data.resultado;
                            this.especialidadChangeSelectList(dataObject, this.especialidadId, profesionTarget, this.leyId.value)
                        });
                });

                this.cargoId.addEventListener("change", e => {
                    let cargoTarget = e.target.value;
                    this.apiCrear.fetchAsignacionChofer(cargoTarget)
                        .then(data => {
                            let dataObject = data.resultado;
                            this.asignacionChoferChangeSelectList(dataObject, this.asignacionChoferId, cargoTarget)
                        });
                });
            }
        }
    }

    getComunasSelect() {
        //
        if (this.accion.value === "crear") {
            if (this.roleUser.value === "[ROLE_MINSAL]") {
                this.servicioId.addEventListener('change', e => {
                    let servicioTargetValue = e.target.value;
                    console.log("Id Servicio: " + servicioTargetValue);
                    this.apiCrear.fetchComunasByIdSarvicio(servicioTargetValue)
                        .then(data => {
                            let dataObject = data.resultado;
                            console.log(dataObject);
                            this.comunasByServicioChangeSelectList(dataObject, this.comunaCodigo);
                        });
                });
            } else if (this.roleUser.value === "[ROLE_LA_GRANJA]") {
                this.servicioId.addEventListener('change', e => {
                    this.establecimientoId.selectedIndex = 0;
                    let servicioTargetValue = e.target.value;
                    console.log(servicioTargetValue);
                    this.apiCrear.fetchComunasByIdSarvicio(servicioTargetValue)
                        .then(data => {
                            let dataObject = data.resultado;
                            console.log(dataObject);
                            this.comunasByServicioChangeSelectList(dataObject, this.comunaCodigo);
                        });
                });
            }
        }

        if(this.accion.value === "editar") {
            this.servicioId.addEventListener('change', e => {
                let servicioTargetValue = e.target.value;
                console.log(servicioTargetValue);
                this.apiCrear.fetchComunasByIdSarvicio(servicioTargetValue)
                    .then(data => {
                        let dataObject = data.resultado;
                        console.log(dataObject);
                        this.comunasByServicioChangeSelectList(dataObject, this.comunaCodigo);
                    });
            });
        }
    }

    getEstablecimientoSelect() {
        //
        const establecimientoId = this.establecimientoId.value;
        if (this.accion.value === "crear") {
            if (this.roleUser.value === "[ROLE_MINSAL]" || this.roleUser.value === "[ROLE_SERVICIO]" || this.roleUser.value === "[ROLE_LA_GRANJA]") {
                this.comunaCodigo.addEventListener('change', e => {
                    let comunaTargetValue = e.target.value;
                    console.log(comunaTargetValue);
                    this.apiCrear.fetchEstablecimientosByIdComuna(comunaTargetValue)
                        .then(data => {
                            let dataObject = data.resultado;
                            console.log(dataObject);
                            this.establecimientosByComunaChangeSelectList(dataObject, this.establecimientoId)
                        });
                });
            }
        }

        if(this.accion.value === "editar") {
            this.comunaCodigo.addEventListener('change', e => {
                let comunaTargetValue = e.target.value;
                console.log(comunaTargetValue);
                this.apiCrear.fetchEstablecimientosByIdComuna(comunaTargetValue)
                    .then(data => {
                        let dataObject = data.resultado;
                        console.log(dataObject);
                        this.establecimientosByComunaChangeSelectList(dataObject, this.establecimientoId)
                    });
            });
        }
    }

    comunasByServicioChangeSelectList(dataObject, objectHtmlSelect, codigoComunaValue) {
        //
        let objectHS = objectHtmlSelect;
        objectHS.length = 0;

        let defaultOption = document.createElement('option');
        defaultOption.value = '';
        defaultOption.text = '-- Seleccionar --';
        objectHS.add(defaultOption);
        objectHS.selectedIndex = '';

        let option;
        let keyPlus;
        Object.keys(dataObject).forEach(function (key) {
            option = document.createElement('option');
            option.text = dataObject[key].comuna;
            option.value = dataObject[key].codigoComuna;

            if(codigoComunaValue == dataObject[key].codigoComuna) {
                keyPlus = (parseInt(key) + 1).toString()
            }

            objectHS.add(option);
        });
        objectHS.selectedIndex = keyPlus;
    }

    establecimientosByComunaChangeSelectList(dataObject, objectHtmlSelect, establecimientoId) {
        let objectHS = objectHtmlSelect;
        objectHS.length = 0;

        let defaultOption = document.createElement('option');
        defaultOption.value = '';
        defaultOption.text = '-- Seleccionar --';
        objectHS.add(defaultOption);
        objectHS.selectedIndex = '';

        let option;
        let keyPlus;
        Object.keys(dataObject).forEach(function (key) {
            option = document.createElement('option');
            option.text = dataObject[key].establecimientoNombre;
            option.value = dataObject[key].codigoNuevo;
            if(establecimientoId == dataObject[key].codigoNuevo) {
                keyPlus = (parseInt(key) + 1).toString()
            }
            objectHS.add(option);
        });
        objectHS.selectedIndex = keyPlus;
    }

    servicioByServicioReady(dataObject, objectHtmlSelect) {
        let objectHS = objectHtmlSelect;
        objectHS.length = 0;

        let defaultOption = document.createElement('option');
        defaultOption.value = '';
        defaultOption.text = '-- Seleccionar --';
        objectHS.add(defaultOption);
        objectHS.selectedIndex = '';

        let option;
        option = document.createElement('option');
        option.text = dataObject.servicioSalud;
        option.value = dataObject.id;
        objectHS.add(option);
        objectHS.selectedIndex = 1;
    }

    servicioLaGranjaReady(dataObject, objectHtmlSelect, idServicio) {
        let objectHS = objectHtmlSelect;
        objectHS.length = 0;

        let defaultOption = document.createElement('option');
        defaultOption.value = '';
        defaultOption.text = '-- Seleccionar --';
        objectHS.add(defaultOption);
        objectHS.selectedIndex = '';

        let option;
        let keyPlus;
        Object.keys(dataObject).forEach(function (key) {
            option = document.createElement('option');
            option.text = dataObject[key].servicioSalud;
            option.value = dataObject[key].id;
            if(idServicio == dataObject[key].id) {
                keyPlus = (parseInt(key) + 1).toString()
            }
            objectHS.add(option);
        });
        objectHS.selectedIndex = keyPlus;
    }

    comunaByComunaReady(dataObject, objectHtmlSelect) {
        let objectHS = objectHtmlSelect;
        objectHS.length = 0;

        let defaultOption = document.createElement('option');
        defaultOption.value = '';
        defaultOption.text = '-- Seleccionar --';
        objectHS.add(defaultOption);
        objectHS.selectedIndex = '';

        let option;
        option = document.createElement('option');
        option.text = dataObject.comuna;
        option.value = dataObject.codigoComuna;
        objectHS.add(option);
        objectHS.selectedIndex = 1;
    }

    getRole(roleSpan) {
        const roleArray = roleSpan.split("Granted Authorities=");
        return roleArray[1].slice(0, -1)
    }

    tipoContratoByLeyIdChangeSelectList(dataObject, objectHtmlSelect, leyTarget, tipoContratoId) {
        //
        let objectHS = objectHtmlSelect;
        objectHS.length = 0;

        let defaultOption = document.createElement('option');
        defaultOption.value = '';
        defaultOption.text = '-- Seleccionar --';
        objectHS.add(defaultOption);
        objectHS.selectedIndex = '';

        let option;
        let keyPlus;
        Object.keys(dataObject).forEach(function (key) {
            option = document.createElement('option');
            option.text = dataObject[key].tipoContrato;
            option.value = dataObject[key].id;
            if(tipoContratoId == dataObject[key].id) {
                keyPlus = (parseInt(key) + 1).toString()
            }
            objectHS.add(option);
        });

        objectHS.selectedIndex = keyPlus;

        if(leyTarget == 2 || leyTarget == 3) {
            objectHS.selectedIndex = 1;
        }
    }

    categoriaProfesionChangeSelectList(dataObject, objectHtmlSelect, leyTarget, categoriaProfesionId) {
        //
        let objectHS = objectHtmlSelect;
        objectHS.length = 0;

        let defaultOption = document.createElement('option');
        defaultOption.value = '';
        defaultOption.text = '-- Seleccionar --';
        objectHS.add(defaultOption);
        objectHS.selectedIndex = '';

        let option;
        let keyPlus;
        Object.keys(dataObject).forEach(function (key) {
            option = document.createElement('option');
            option.text = dataObject[key].categoriaProfesion;
            option.value = dataObject[key].id;
            if(categoriaProfesionId == dataObject[key].id) {
                keyPlus = (parseInt(key) + 1).toString()
            }
            objectHS.add(option);
        });

        objectHS.selectedIndex = keyPlus;

        if(leyTarget == 2 || leyTarget == 3) {
            objectHS.selectedIndex = 1;
        }
    }

    nivelCarreraChangeSelectList(dataObject, objectHtmlSelect, leyTarget, nivelCarreraId) {
        //
        let objectHS = objectHtmlSelect;
        objectHS.length = 0;

        let defaultOption = document.createElement('option');
        defaultOption.value = '';
        defaultOption.text = '-- Seleccionar --';
        objectHS.add(defaultOption);
        objectHS.selectedIndex = '';

        let option;
        let keyPlus;
        Object.keys(dataObject).forEach(function (key) {
            option = document.createElement('option');
            if (dataObject[key].id == 16) {
                option.text = "N/A";
                option.value = 16;
            } else {
                option.text = dataObject[key].nivelCarrera;
                option.value = dataObject[key].id;
                if(nivelCarreraId == dataObject[key].id) {
                    keyPlus = (parseInt(key) + 1).toString()
                }
            }
            objectHS.add(option);
        });

        objectHS.selectedIndex = keyPlus;

        if(leyTarget == 2 || leyTarget == 3) {
            objectHS.selectedIndex = 1;
        }
    }

    profesionChangeSelectList(dataObject, objectHtmlSelect, categoriaProfesionId) {
        //
        let objectHS = objectHtmlSelect;
        objectHS.length = 0;

        let defaultOption = document.createElement('option');
        defaultOption.value = '';
        defaultOption.text = '-- Seleccionar --';
        objectHS.add(defaultOption);
        objectHS.selectedIndex = '';

        let option;
        let keyPlus;
        Object.keys(dataObject).forEach(function (key) {
            option = document.createElement('option');
            option.text = dataObject[key].profesion;
            option.value = dataObject[key].id;
            if(categoriaProfesionId == dataObject[key].id) {
                keyPlus = (parseInt(key) + 1).toString()
            }
            objectHS.add(option);
        });

        objectHS.selectedIndex = keyPlus;
    }

    especialidadChangeSelectList(dataObject, objectHtmlSelect, profesionTarget, leyTarget, especialidadId) {
        //
        let objectHS = objectHtmlSelect;
        objectHS.length = 0;

        let defaultOption = document.createElement('option');
        defaultOption.value = '';
        defaultOption.text = '-- Seleccionar --';
        objectHS.add(defaultOption);
        objectHS.selectedIndex = '';

        let option;
        let keyPlus;
        Object.keys(dataObject).forEach(function (key) {
            option = document.createElement('option');
            option.text = dataObject[key].especialidad;
            option.value = dataObject[key].id;
            if(especialidadId == dataObject[key].id) {
                keyPlus = (parseInt(key) + 1).toString()
            }
            objectHS.add(option);
        });

        objectHS.selectedIndex = keyPlus;

        if(profesionTarget != 2 && leyTarget == 1) {
            objectHS.selectedIndex = 1;
        } else if(profesionTarget != 24 && (leyTarget == 2 || leyTarget == 3)) {
            objectHS.selectedIndex = 1;
        }
    }

    asignacionChoferChangeSelectList(dataObject, objectHtmlSelect, cargoTarget, asignacionChoferId) {
        //
        let objectHS = objectHtmlSelect;
        objectHS.length = 0;

        let defaultOption = document.createElement('option');
        defaultOption.value = '';
        defaultOption.text = '-- Seleccionar --';
        objectHS.add(defaultOption);
        objectHS.selectedIndex = '';

        let option;
        let keyPlus;
        Object.keys(dataObject).forEach(function (key) {
            option = document.createElement('option');
            option.text = dataObject[key].asignacionChofer;
            option.value = dataObject[key].id;
            if(asignacionChoferId == dataObject[key].id) {
                keyPlus = (parseInt(key) + 1).toString()
            }
            objectHS.add(option);
        });

        objectHS.selectedIndex = keyPlus;

        if(cargoTarget != 5) {
            objectHS.selectedIndex = 1;
        }
    }
}