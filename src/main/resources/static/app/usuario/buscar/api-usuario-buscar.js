export class ApiUsuarioBuscar {

    constructor(urlGetComunas) {
        //
        this.urlGetComunas = urlGetComunas;
        this.token = document.querySelector('input[name=_csrf]').value;
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