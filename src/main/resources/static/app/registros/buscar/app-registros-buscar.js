export class AppRegistrosBuscar {

    constructor(ui, api) {
        //
        this.ui = ui;
        this.api = api;
        //
        this.roleUser = document.getElementById("roleUser");
        this.servicioUser = document.getElementById("servicioUser");
        this.comunaUser = document.getElementById("comunaUser");
        //
        this.comuna = document.getElementById("comuna");
        this.servicioSalud = document.getElementById("servicioSalud");
        this.establecimiento = document.getElementById("establecimiento");
    }

    initApp() {
        console.log("Role del Usuario: " + this.roleUser.value);
        console.log("Servicio de Salud del Usuario: " + this.servicioUser.value);
    }

    domIsReadyApp() {
        //
        if (document.readyState === 'complete') {
            console.log("Comuna del Usuario: " + this.comunaUser.value);

            if (this.roleUser.value === "[ROLE_SERVICIO]") {
                this.api.fetchServicioByIdServicio(this.servicioUser.value)
                    .then(data => {
                        let dataObject = data.resultado;
                        console.log(dataObject);
                        this.servicioByServicioReady(dataObject, this.servicioSalud);

                        this.api.fetchComunasByIdServicioRole(this.servicioUser.value)
                            .then(data => {
                                let dataObject = data.resultado;
                                console.log(dataObject);
                                this.comunasByServicioChangeSelectList(dataObject, this.comuna);

                            });
                    });
            } else if (this.roleUser.value === "[ROLE_COMUNA]") {
                this.api.fetchServicioByIdServicio(this.servicioUser.value)
                    .then(data => {
                        let dataObject = data.resultado;
                        console.log(dataObject);
                        this.servicioByServicioReady(dataObject, this.servicioSalud);

                        this.api.fetchComunaByIdComunaRoleComuna(this.comunaUser.value)
                            .then(data => {
                                let dataObject = data.resultado;
                                console.log(dataObject);
                                this.comunaByComunaReady(dataObject, this.comuna)

                                this.api.fetchEstablecimientosByIdComunaRoleServicio(this.comunaUser.value)
                                    .then(data => {
                                        let dataObject = data.resultado;
                                        console.log(dataObject);
                                        this.establecimientosByComunaChangeSelectList(dataObject, this.establecimiento)
                                    });
                            });
                    });
            } else if (this.roleUser.value === "[ROLE_LA_GRANJA]") {
                this.api.fetchServiciosLaGranjaRoleLaGranja()
                    .then(data => {
                        let dataObject = data.resultado;
                        console.log(dataObject);
                        this.servicioLaGranjaReady(dataObject, this.servicioSalud)
                    });
            }
        }
    }

    getComunaByServicioChange() {
        if (this.roleUser.value === "[ROLE_MINSAL]" || this.roleUser.value === "[ROLE_LA_GRANJA]") {
            this.servicioSalud.addEventListener('change', e => {
                let servicioTargetValue = e.target.value;
                console.log(servicioTargetValue)
                this.api.fetchComunasByIdSarvicio(servicioTargetValue)
                    .then(data => {
                        let dataObject = data.resultado;
                        console.log(dataObject);
                        this.comunasByServicioChangeSelectList(dataObject, this.comuna);
                    });
            });
        }
    }

    getEstablecimientoByComunaChange() {
        if (this.roleUser.value === "[ROLE_MINSAL]" || this.roleUser.value === "[ROLE_SERVICIO]" || this.roleUser.value === "[ROLE_LA_GRANJA]") {
            this.comuna.addEventListener('change', e => {
                let comunaTargetValue = e.target.value;
                console.log(comunaTargetValue);
                this.api.fetchEstablecimientosByIdComuna(comunaTargetValue)
                    .then(data => {
                        let dataObject = data.resultado;
                        console.log(dataObject);
                        this.establecimientosByComunaChangeSelectList(dataObject, this.establecimiento)
                    });
            });
        }
    }

    comunasByServicioChangeSelectList(dataObject, objectHtmlSelect) {
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
            option.text = dataObject[key].comuna;
            option.value = dataObject[key].codigoComuna;
            objectHS.add(option);
        });
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

    servicioLaGranjaReady(dataObject, objectHtmlSelect) {
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
            option.text = dataObject[key].servicioSalud;
            option.value = dataObject[key].id;
            objectHS.add(option);
        });
    }
}