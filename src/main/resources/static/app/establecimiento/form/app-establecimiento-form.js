export class AppEstablecimientoForm {

    constructor(metodos, ui, api) {
        this.metodos = metodos;
        this.ui = ui;
        this.api = api;
        this.region = document.getElementById("region.id");
        this.servicioSalud = document.getElementById("servicio.id");
        this.comuna = document.getElementById("comuna.codigoComuna");
        this.codigoDeis = document.getElementById("codigoNuevo");
        this.establecimientoNombre = document.getElementById("establecimientoNombre");
        this.tipoEstablecimiento = document.getElementById("tipoEstablecimiento");
        this.elimID = document.getElementById('elimID'); // Habilitado  / Inhabilitado
        this.idEstablecimiento = document.getElementById('idEstablecimiento');
        this.isCreateOrEdit = document.getElementById('isCreateOrEdit');
        this.badgeEnabled = document.getElementById('badge-enabled');
        this.elimIDInteger = document.getElementById('elimIDInteger');
        this.idTipoEstablecimiento = document.getElementById('idTipoEstablecimiento');
        this.tipoEstablecimientoValue = document.getElementById('tipoEstablecimientoValue');
    }

    initApp() {
        if(this.elimIDInteger.value == 0) {
            this.elimID.checked = true;
            this.badgeEnabled.innerHTML = 'Habilitado';
            $("#badge-enabled").removeClass('badge badge-pill badge-danger').addClass('badge badge-pill badge-success');
        } else {
            this.elimID.checked = false;
            this.badgeEnabled.innerHTML = 'Inhabilitado';
            $("#badge-enabled").removeClass('badge badge-pill badge-success').addClass('badge badge-pill badge-danger');
        }
        console.log("---------------- Log initApp ----------------")
        console.log("isChecked: " + this.elimID.checked);
        console.log("Is Create or Edit: " + this.isCreateOrEdit.value);
    }

    domIsReadyApp() {
        if (document.readyState === 'complete') {
            // console.log(this.metodos.consoleLogTest())
            if(this.isCreateOrEdit.value === "false") {
                let idRegionValue = this.region.value;
                let idServicioSaludValue = this.servicioSalud.value;
                let idComunaValue = this.comuna.value;
                this.api.fetchServicioByRegionChange(idRegionValue)
                    .then( data => {
                        let dataObject = data.resultado;
                        this.ServicioByRegionChangeSelectList(dataObject, this.servicioSalud, idServicioSaludValue)
                        this.api.fetchComunaByServicioChange(idServicioSaludValue)
                            .then( data => {
                                let dataObject = data.resultado;
                                this.ComunaByServicioChangeSelectList(dataObject, this.comuna, idComunaValue);
                                this.tipoEstablecimiento.value = this.idTipoEstablecimiento.value;
                            });
                    });
            }
        }
    }

    getServicioByRegionChange() {
        this.region.addEventListener( 'change', e => {
            let idRegion = e.target.value;
            this.api.fetchServicioByRegionChange(idRegion)
                .then( data => {
                    let dataObject = data.resultado;
                    this.ServicioByRegionChangeSelectList(dataObject, this.servicioSalud)
                });
        });
    }

    getComunaByServicioChange() {
        this.servicioSalud.addEventListener( 'change', e => {
            let idServicioSalud = e.target.value;
            this.api.fetchComunaByServicioChange(idServicioSalud)
                .then( data => {
                    let dataObject = data.resultado;
                    this.ComunaByServicioChangeSelectList(dataObject, this.comuna);
                });
        });
    }

    tipoDeEstablecimientoChangeSelectList() {
        this.tipoEstablecimiento.addEventListener('change', e => {
            this.tipoEstablecimientoValue.value = this.tipoEstablecimiento.options[this.tipoEstablecimiento.selectedIndex].text;
        });
    }

    ServicioByRegionChangeSelectList(dataObject, objectHtmlSelect, idServicioSaludValue) {
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
            if(idServicioSaludValue == dataObject[key].id) {
                keyPlus = (parseInt(key) + 1).toString()
            }
            objectHS.add(option);
        });
        objectHS.selectedIndex = keyPlus;
    }

    ComunaByServicioChangeSelectList(dataObject, objectHtmlSelect, idComunaValue) {
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
            if(idComunaValue == dataObject[key].codigoComuna) {
                keyPlus = (parseInt(key) + 1).toString()
            }
            objectHS.add(option);
        });
        objectHS.selectedIndex = keyPlus;
    }

    enabledOrDisableEstablecimiento() {
        this.elimID.addEventListener('change', (e) => {
            if (this.elimID.checked) {
                this.elimIDInteger.value = 0;
                this.badgeEnabled.innerHTML = 'Habilitado';
                $("#badge-enabled").removeClass('badge badge-pill badge-danger').addClass('badge badge-pill badge-success');
            } else {
                this.elimIDInteger.value = 1;
                this.badgeEnabled.innerHTML = 'Inhabilitado';
                $("#badge-enabled").removeClass('badge badge-pill badge-success').addClass('badge badge-pill badge-danger');
            }
        });
    }

    isEnabledEstablecimiento() {
        if (this.elimID.checked) {
            this.badgeEnabled.innerHTML = 'Habilitado';
            $("#badge-enabled").removeClass('badge badge-pill badge-danger').addClass('badge badge-pill badge-success');
        } else {
            this.badgeEnabled.innerHTML = 'Inhabilitado';
            $("#badge-enabled").removeClass('badge badge-pill badge-success').addClass('badge badge-pill badge-danger');
        }
    }
}