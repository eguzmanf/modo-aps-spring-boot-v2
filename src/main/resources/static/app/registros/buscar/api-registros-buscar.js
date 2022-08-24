export class ApiRegistrosBuscar {

    constructor(urlGetComunas, urlGetEstablecimientos, urlGetServicioByIdServicio, urlGetComunasByIdServicioRole, urlGetComunaByIdComunaRoleComuna, urlGetEstablecimientoByIdComunaServicioRole,
                urlGetServiciosLaGranjaRoleLaGranja) {
        //
        this.urlGetComunas = urlGetComunas;
        this.urlGetEstablecimientos = urlGetEstablecimientos
        this.urlGetServicioByIdServicio = urlGetServicioByIdServicio;
        this.urlGetComunasByIdServicioRole = urlGetComunasByIdServicioRole;
        this.urlGetComunaByIdComunaRoleComuna = urlGetComunaByIdComunaRoleComuna;
        this.urlGetEstablecimientoByIdComunaServicioRole = urlGetEstablecimientoByIdComunaServicioRole;
        this.urlGetServiciosLaGranjaRoleLaGranja = urlGetServiciosLaGranjaRoleLaGranja;
        this.token = document.querySelector('input[name=_csrf]').value;
    }

    async fetchComunasByIdSarvicio(servicioTargetValue) {
        let enviarDatos = {
            idServicioSalud: servicioTargetValue,
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

    async fetchEstablecimientosByIdComuna(comunaTargetValue) {
        let enviarDatos = {
            idComuna: comunaTargetValue
        };
        const obtenerDatos = await fetch(this.urlGetEstablecimientos, {
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

    async fetchServicioByIdServicio(idServicio) {
        let enviarDatos = {
            idServicio: idServicio
        };
        const obtenerDatos = await fetch(this.urlGetServicioByIdServicio, {
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

    async fetchComunasByIdServicioRole(idServicio) {
        let enviarDatos = {
            idServicio: idServicio
        };
        const obtenerDatos = await fetch(this.urlGetComunasByIdServicioRole, {
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

    async fetchComunaByIdComunaRoleComuna(idComuna) {
        let enviarDatos = {
            idComuna: idComuna
        };
        const obtenerDatos = await fetch(this.urlGetComunaByIdComunaRoleComuna, {
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

    async fetchEstablecimientosByIdComunaRoleServicio(idComuna) {
        let enviarDatos = {
            idComuna: idComuna
        };
        const obtenerDatos = await fetch(this.urlGetEstablecimientoByIdComunaServicioRole, {
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

    async fetchServiciosLaGranjaRoleLaGranja() {
        let enviarDatos = {
            none: "None"
        };
        const obtenerDatos = await fetch(this.urlGetServiciosLaGranjaRoleLaGranja, {
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