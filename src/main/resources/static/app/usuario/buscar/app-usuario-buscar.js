export class AppUsuarioBuscar {

    constructor(ui, api) {
        //
        this.ui = ui;
        this.api = api;
        //
        this.comuna = document.getElementById("comuna");
        this.servicioSalud = document.getElementById("servicioSalud");
        // this.enabled = document.getElementById('enabled');
        // this.badgeEnabled = document.getElementById('badge-enabled');
    }

    initApp() {
    }

    domIsReadyApp() {
        //
        if (document.readyState === 'complete') {
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

    /*
    enabledOrDisableUser() {
        //
        this.enabled.addEventListener('change', (e) => {
            //
            if (this.enabled.checked) {
                //
                this.badgeEnabled.innerHTML = 'Habilitado';
                $("#badge-enabled").removeClass('badge badge-pill badge-danger').addClass('badge badge-pill badge-success');
            } else {
                //
                this.badgeEnabled.innerHTML = 'Deshabilitado';
                $("#badge-enabled").removeClass('badge badge-pill badge-success').addClass('badge badge-pill badge-danger');
            }
        });
    }
     */

}