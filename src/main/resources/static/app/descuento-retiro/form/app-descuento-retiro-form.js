export class AppDescuentoRetiroForm {

    constructor(metodos, ui, api) {
        //
        this.metodos = metodos;
        this.ui = ui;
        this.api = api;
        this.comuna = document.getElementById("comuna.codigoComuna");
        this.servicioSalud = document.getElementById("servicioSalud.id");
        this.montoCuotaMensualMiles = document.getElementById("montoCuotaMensual");
        this.ultimaCuotaMiles = document.getElementById("ultimaCuota");
        this.totalRecursosMiles = document.getElementById("totalRecursos");
        this.deudaMiles = document.getElementById("deuda");
        this.formDescuentoRetiro = document.getElementById("form_descuento_retiro");
        this.hasErrorsScript = document.getElementById('hasErrorsScript');
        this.editar = document.getElementById("editar");
    }

    initApp() {
    }

    domIsReadyApp() {
        //
        if (document.readyState === 'complete') {
            console.log(this.metodos.consoleLogTest())
            this.metodos.formatSeparadorMiles(this.montoCuotaMensualMiles);
            this.metodos.formatSeparadorMiles(this.ultimaCuotaMiles);
            this.metodos.formatSeparadorMiles(this.totalRecursosMiles);
            this.metodos.formatSeparadorMiles(this.deudaMiles);
        }
    }

    getComunaByServicioError() {
        const comunaCodigo = this.comuna.value;
        if(this.hasErrorsScript.value === "true" || this.editar.value === "true") {
            console.log("errors")
            this.api.fetchComunaByServicioChange(this.servicioSalud.value)
                .then( data => {
                    let dataObject = data.resultado;
                    console.log(dataObject);
                    this.comunasByServicioChangeSelectList2(dataObject, this.comuna, comunaCodigo);
                });
        }
    }

    getComunaByServicioChange() {
        this.servicioSalud.addEventListener( 'change', e => {
            let idServicioSalud = e.target.value;
            this.api.fetchComunaByServicioChange(idServicioSalud)
                .then( data => {
                    let dataObject = data.resultado;
                    console.log(dataObject);
                    this.ComunaByServicioChangeSelectList(dataObject, this.comuna);
                });
        });
    }

    ComunaByServicioChangeSelectList(dataObject, objectHtmlSelect) {
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

    comunasByServicioChangeSelectList2(dataObject, objectHtmlSelect, codigoComunaValue) {
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

    montoCuotaMensual(){
        this.montoCuotaMensualMiles.addEventListener('change', e => {
            this.metodos.formatSeparadorMiles(this.montoCuotaMensualMiles);
        });
    }

    ultimaCuota(){
        this.ultimaCuotaMiles.addEventListener('change', e => {
            this.metodos.formatSeparadorMiles(this.ultimaCuotaMiles);
        });
    }

    totalRecursos() {
        this.totalRecursosMiles.addEventListener('change', e => {
            this.metodos.formatSeparadorMiles(this.totalRecursosMiles);
        });
    }

    deuda() {
        this.deudaMiles.addEventListener('change', e => {
            this.metodos.formatSeparadorMiles(this.deudaMiles);
        });
    }
    
    submitForm() {
        this.formDescuentoRetiro.addEventListener('submit', e => {
            this.montoCuotaMensualMiles.value = this.montoCuotaMensualMiles.value.replace(/\./g, "");
            // console.log(this.montoCuotaMensualMiles.value);
            this.ultimaCuotaMiles.value = this.ultimaCuotaMiles.value.replace(/\./g, "");
            // console.log(this.ultimaCuotaMiles.value);
            this.totalRecursosMiles.value = this.totalRecursosMiles.value.replace(/\./g, "");
            // console.log(this.totalRecursosMiles.value);
            this.deudaMiles.value = this.deudaMiles.value.replace(/\./g, "");
            // console.log(this.deudaMiles.value);
        });
    }
}