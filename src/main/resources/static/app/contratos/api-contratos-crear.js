export class ApiContratoCrear {

    constructor(urlGetComunas, urlGetEstablecimientos, urlGetServicioByIdServicio, urlGetComunasByIdServicioRole, urlGetEstablecimientoByIdComunaServicioRole, urlGetComunaByIdComunaRoleComuna, urlGetServiciosLaGranjaRoleLaGranja,
                urlGetTipoContrato, urlGetCategoriaProfesionLey19378, urlGetNivelCarreraLey19378, urlGetProfesionLey19378,
                urlGetespecialidad, urlGetAsignacionChofer, urlGetCategoriaProfesionHonorarioCodigo, urlGetNivelCarreraLeyHonorarioCodigo,
                urlGetProfesionLeyHonorarioCodigo) {
        //
        this.urlGetComunas = urlGetComunas;
        this.urlGetEstablecimientos = urlGetEstablecimientos;
        this.urlGetServicioByIdServicio = urlGetServicioByIdServicio;
        this.urlGetComunasByIdServicioRole = urlGetComunasByIdServicioRole;
        this.urlGetEstablecimientoByIdComunaServicioRole = urlGetEstablecimientoByIdComunaServicioRole;
        this.urlGetComunaByIdComunaRoleComuna = urlGetComunaByIdComunaRoleComuna;
        this.urlGetServiciosLaGranjaRoleLaGranja = urlGetServiciosLaGranjaRoleLaGranja;

        this.urlGetTipoContrato = urlGetTipoContrato;
        this.urlGetCategoriaProfesionLey19378 = urlGetCategoriaProfesionLey19378;
        this.urlGetNivelCarreraLey19378 = urlGetNivelCarreraLey19378;
        this.urlGetProfesionLey19378 = urlGetProfesionLey19378;
        this.urlGetespecialidad = urlGetespecialidad;
        this.urlGetAsignacionChofer = urlGetAsignacionChofer;
        this.urlGetCategoriaProfesionHonorarioCodigo = urlGetCategoriaProfesionHonorarioCodigo;
        this.urlGetNivelCarreraLeyHonorarioCodigo = urlGetNivelCarreraLeyHonorarioCodigo;
        this.urlGetProfesionLeyHonorarioCodigo = urlGetProfesionLeyHonorarioCodigo;
        this.token = document.querySelector('input[name=_csrf]').value;
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

    async fetchTipoContratosByLeyId(idLey) {
        let enviarDatos = {
            idLey: idLey
        };
        const obtenerDatos = await fetch(this.urlGetTipoContrato, {
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

    async fetchCategoriaProfesionLey19378() {
        let enviarDatos = {
            data: null
        };
        const obtenerDatos = await fetch(this.urlGetCategoriaProfesionLey19378, {
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

    async fetchNivelCarreraLey19387() {
        let enviarDatos = {
            data: null
        };
        const obtenerDatos = await fetch(this.urlGetNivelCarreraLey19378, {
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

    async fetchProfesionLey19378(categoriaProfesionId) {
        let enviarDatos = {
            categoriaProfesionId: categoriaProfesionId
        };
        const obtenerDatos = await fetch(this.urlGetProfesionLey19378, {
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

    async fetchEspecialidad(profesionId) {
        let enviarDatos = {
            profesionId: profesionId
        };
        const obtenerDatos = await fetch(this.urlGetespecialidad, {
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

    async fetchAsignacionChofer(cargoId) {
        let enviarDatos = {
            cargoId: cargoId
        };
        const obtenerDatos = await fetch(this.urlGetAsignacionChofer, {
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

    async fetchCategoriaProfesionLeyHonorariosCodigoDelTrabajo() {
        let enviarDatos = {
            data: null
        };
        const obtenerDatos = await fetch(this.urlGetCategoriaProfesionHonorarioCodigo, {
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

    async fetchNivelCarreraLeyHonorariosCodigoDelTrabajo() {
        let enviarDatos = {
            data: null
        };
        const obtenerDatos = await fetch(this.urlGetNivelCarreraLeyHonorarioCodigo, {
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

    async fetchProfesionLeyHonorariosCodigoDelTrabajo() {
        let enviarDatos = {
            data: null
        };
        const obtenerDatos = await fetch(this.urlGetProfesionLeyHonorarioCodigo, {
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