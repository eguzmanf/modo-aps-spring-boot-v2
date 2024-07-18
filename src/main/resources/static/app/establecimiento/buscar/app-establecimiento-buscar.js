export class AppEstablecimientoBuscar {

    constructor(metodos, ui, api) {
        //
        this.metodos = metodos;
        this.ui = ui;
        this.api = api;
        //
        this.region = document.getElementById("region");
        this.servicio = document.getElementById("servicio");
        this.comuna = document.getElementById("comuna");
        this.tipoEstablecimiento = document.getElementById("tipoEstablecimiento");
        this.tipoEstablecimientoText = document.getElementById("tipoEstablecimientoText");
        this.regionText = document.getElementById("regionText");
        this.servicioText = document.getElementById("servicioText");
        this.comunaText = document.getElementById("comunaText");
    }

    initApp() {
        console.log("--- Log initApp ---")
    }

    domIsReadyApp() {
        //
        if (document.readyState === 'complete') {
            console.log("--- Log domIsReadyApp ---")
        }
    }

    getServicioByRegionChange() {
        this.region.addEventListener( 'change', e => {
            let idRegion = e.target.value;
            this.api.fetchServicioByRegionChange(idRegion)
                .then( data => {
                    let dataObject = data.resultado;
                    this.ServicioByRegionChangeSelectList(dataObject, this.servicio)
                });
        });
    }

    getComunaByServicioChange() {
        this.servicio.addEventListener( 'change', e => {
            let idServicioSalud = e.target.value;
            this.api.fetchComunaByServicioChange(idServicioSalud)
                .then( data => {
                    let dataObject = data.resultado;
                    this.ComunaByServicioChangeSelectList(dataObject, this.comuna);
                });
        });
    }

    ServicioByRegionChangeSelectList(dataObject, objectHtmlSelect) {
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

    ComunaByServicioChangeSelectList(dataObject, objectHtmlSelect) {
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

    tipoDeEstablecimientoChangeSelectList() {
        this.tipoEstablecimiento.addEventListener('change', e => {
            this.tipoEstablecimientoText.value = this.tipoEstablecimiento.options[this.tipoEstablecimiento.selectedIndex].text;
        });
    }

    getRegionTextChangeSelectList() {
        this.region.addEventListener('change', e => {
            this.regionText.value = this.region.options[this.region.selectedIndex].text;
        });
    }

    getServicioTextChangeSelectList() {
        this.servicio.addEventListener('change', e => {
            this.servicioText.value = this.servicio.options[this.servicio.selectedIndex].text;
        });
    }

    getComunaTextChangeSelectList() {
        this.comuna.addEventListener('change', e => {
            this.comunaText.value = this.comuna.options[this.comuna.selectedIndex].text;
        });
    }

}