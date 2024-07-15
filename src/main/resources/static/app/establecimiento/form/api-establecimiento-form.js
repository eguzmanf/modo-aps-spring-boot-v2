export class ApiEstablecimientoForm {

    constructor(urlGetServicios, urlGetComunas) {
        //
        this.urlGetServicios = urlGetServicios;
        this.urlGetComunas = urlGetComunas;
        this.token = document.querySelector('input[name=_csrf]').value;
    }

    async fetchServicioByRegionChange(idRegion) {
        let enviarDatos = {
            idRegion: idRegion
        };

        const obtenerDatos = await fetch(this.urlGetServicios, {
            method: 'POST',
            body: JSON.stringify(enviarDatos),
            headers: {
                'X-Requested-With': 'XMLHttpRequest',
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                'X-CSRF-TOKEN': this.token
            }
        });

        const resultado = await obtenerDatos.json();

        return {
            resultado
        };
    }

    async fetchComunaByServicioChange(idServicioSalud) {
        let enviarDatos = {
            idServicioSalud: idServicioSalud
        };

        const obtenerDatos = await fetch(this.urlGetComunas, {
            method: 'POST',
            body: JSON.stringify(enviarDatos),
            headers: {
                'X-Requested-With': 'XMLHttpRequest',
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                'X-CSRF-TOKEN': this.token
            }
        });

        const resultado = await obtenerDatos.json();

        return {
            resultado
        };
    }

}